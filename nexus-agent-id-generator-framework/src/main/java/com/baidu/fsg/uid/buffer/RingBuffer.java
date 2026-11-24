package com.baidu.fsg.uid.buffer;

import com.baidu.fsg.uid.utils.PaddedAtomicLong;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @program: 企业级别深度设计 AI Agent。添加 阿星不是程序员 微信，添加时备注 super 来获取项目的完整资料
 * @description: Represents a ring buffer based on array
 * @author: 阿星不是程序员
 **/

public class RingBuffer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RingBuffer.class);

    private static final int START_POINT = -1;
    private static final long CAN_PUT_FLAG = 0L;
    private static final long CAN_TAKE_FLAG = 1L;
    public static final int DEFAULT_PADDING_PERCENT = 50;

    private final int bufferSize;
    private final long indexMask;
    private final long[] slots;
    private final PaddedAtomicLong[] flags;

    private final AtomicLong tail = new PaddedAtomicLong(START_POINT);

    private final AtomicLong cursor = new PaddedAtomicLong(START_POINT);

    private final int paddingThreshold;

    @Setter
    private RejectedPutBufferHandler rejectedPutHandler = this::discardPutBuffer;
    @Setter
    private RejectedTakeBufferHandler rejectedTakeHandler = this::exceptionRejectedTakeBuffer;

    @Setter
    private BufferPaddingExecutor bufferPaddingExecutor;

    public RingBuffer(int bufferSize) {
        this(bufferSize, DEFAULT_PADDING_PERCENT);
    }

    public RingBuffer(int bufferSize, int paddingFactor) {

        Assert.isTrue(bufferSize > 0L, "RingBuffer size must be positive");
        Assert.isTrue(Integer.bitCount(bufferSize) == 1, "RingBuffer size must be a power of 2");
        Assert.isTrue(paddingFactor > 0 && paddingFactor < 100, "RingBuffer size must be positive");

        this.bufferSize = bufferSize;
        this.indexMask = bufferSize - 1;
        this.slots = new long[bufferSize];
        this.flags = initFlags(bufferSize);

        this.paddingThreshold = bufferSize * paddingFactor / 100;
    }

    public synchronized boolean put(long uid) {
        long currentTail = tail.get();
        long currentCursor = cursor.get();

        long distance = currentTail - (currentCursor == START_POINT ? 0 : currentCursor);
        if (distance == bufferSize - 1) {
            rejectedPutHandler.rejectPutBuffer(this, uid);
            return false;
        }

        int nextTailIndex = calSlotIndex(currentTail + 1);
        if (flags[nextTailIndex].get() != CAN_PUT_FLAG) {
            rejectedPutHandler.rejectPutBuffer(this, uid);
            return false;
        }

        slots[nextTailIndex] = uid;
        flags[nextTailIndex].set(CAN_TAKE_FLAG);
        tail.incrementAndGet();

        return true;
    }

    public long take() {

        long currentCursor = cursor.get();
        long nextCursor = cursor.updateAndGet(old -> old == tail.get() ? old : old + 1);

        Assert.isTrue(nextCursor >= currentCursor, "Curosr can't move back");

        long currentTail = tail.get();
        if (currentTail - nextCursor < paddingThreshold) {
            LOGGER.info("Reach the padding threshold:{}. tail:{}, cursor:{}, rest:{}", paddingThreshold, currentTail,
                    nextCursor, currentTail - nextCursor);
            bufferPaddingExecutor.asyncPadding();
        }

        if (nextCursor == currentCursor) {
            rejectedTakeHandler.rejectTakeBuffer(this);
        }

        int nextCursorIndex = calSlotIndex(nextCursor);
        Assert.isTrue(flags[nextCursorIndex].get() == CAN_TAKE_FLAG, "Curosr not in can take status");

        long uid = slots[nextCursorIndex];
        flags[nextCursorIndex].set(CAN_PUT_FLAG);

        return uid;
    }

    protected int calSlotIndex(long sequence) {
        return (int) (sequence & indexMask);
    }

    protected void discardPutBuffer(RingBuffer ringBuffer, long uid) {
        LOGGER.warn("Rejected putting buffer for uid:{}. {}", uid, ringBuffer);
    }

    protected void exceptionRejectedTakeBuffer(RingBuffer ringBuffer) {
        LOGGER.warn("Rejected take buffer. {}", ringBuffer);
        throw new RuntimeException("Rejected take buffer. " + ringBuffer);
    }

    private PaddedAtomicLong[] initFlags(int bufferSize) {
        PaddedAtomicLong[] flags = new PaddedAtomicLong[bufferSize];
        for (int i = 0; i < bufferSize; i++) {
            flags[i] = new PaddedAtomicLong(CAN_PUT_FLAG);
        }

        return flags;
    }

    public long getTail() {
        return tail.get();
    }

    public long getCursor() {
        return cursor.get();
    }
    
    @Override
    public String toString() {
        return "RingBuffer [bufferSize=" + bufferSize + ", tail=" + tail + ", cursor=" + cursor + ", paddingThreshold=" + paddingThreshold + "]";
    }

}
