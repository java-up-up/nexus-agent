package com.baidu.fsg.uid.buffer;

import com.baidu.fsg.uid.utils.NamingThreadFactory;
import com.baidu.fsg.uid.utils.PaddedAtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: 企业级别深度设计 AI Agent。添加 阿星不是程序员 微信，添加时备注 super 来获取项目的完整资料
 * @description: There are two kinds of executors
 * @author: 阿星不是程序员
 **/

public class BufferPaddingExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(BufferPaddingExecutor.class);

    private static final String WORKER_NAME = "RingBuffer-Padding-Worker";
    private static final String SCHEDULE_NAME = "RingBuffer-Padding-Schedule";

    private static final long DEFAULT_SCHEDULE_INTERVAL = 5 * 60L;

    private final AtomicBoolean running;

    private final PaddedAtomicLong lastSecond;

    private final RingBuffer ringBuffer;
    private final BufferedUidProvider uidProvider;

    private final ExecutorService bufferPadExecutors;

    private final ScheduledExecutorService bufferPadSchedule;

    private long scheduleInterval = DEFAULT_SCHEDULE_INTERVAL;

    public BufferPaddingExecutor(RingBuffer ringBuffer, BufferedUidProvider uidProvider, boolean usingSchedule) {
        this.running = new AtomicBoolean(false);
        this.lastSecond = new PaddedAtomicLong(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        this.ringBuffer = ringBuffer;
        this.uidProvider = uidProvider;

        int cores = Runtime.getRuntime().availableProcessors();

        bufferPadExecutors = new ThreadPoolExecutor(cores * 2,cores * 2,0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),new NamingThreadFactory(WORKER_NAME));

        if (usingSchedule) {
            bufferPadSchedule = new ScheduledThreadPoolExecutor(1, new NamingThreadFactory(SCHEDULE_NAME));
        } else {
            bufferPadSchedule = null;
        }
    }

    public void start() {
        if (bufferPadSchedule != null) {
            bufferPadSchedule.scheduleWithFixedDelay(() -> paddingBuffer(), scheduleInterval, scheduleInterval, TimeUnit.SECONDS);
        }
    }

    public void shutdown() {
        if (!bufferPadExecutors.isShutdown()) {
            bufferPadExecutors.shutdownNow();
        }

        if (bufferPadSchedule != null && !bufferPadSchedule.isShutdown()) {
            bufferPadSchedule.shutdownNow();
        }
    }

    public boolean isRunning() {
        return running.get();
    }

    public void asyncPadding() {
        bufferPadExecutors.submit(this::paddingBuffer);
    }

    public void paddingBuffer() {
        LOGGER.info("Ready to padding buffer lastSecond:{}. {}", lastSecond.get(), ringBuffer);

        if (!running.compareAndSet(false, true)) {
            LOGGER.info("Padding buffer is still running. {}", ringBuffer);
            return;
        }

        boolean isFullRingBuffer = false;
        while (!isFullRingBuffer) {
            List<Long> uidList = uidProvider.provide(lastSecond.incrementAndGet());
            for (Long uid : uidList) {
                isFullRingBuffer = !ringBuffer.put(uid);
                if (isFullRingBuffer) {
                    break;
                }
            }
        }

        running.compareAndSet(true, false);
        LOGGER.info("End to padding buffer lastSecond:{}. {}", lastSecond.get(), ringBuffer);
    }

    public void setScheduleInterval(long scheduleInterval) {
        Assert.isTrue(scheduleInterval > 0, "Schedule interval must positive!");
        this.scheduleInterval = scheduleInterval;
    }

}
