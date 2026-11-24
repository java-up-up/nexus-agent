package com.baidu.fsg.uid.buffer;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。添加 阿星不是程序员 微信，添加时备注 super 来获取项目的完整资料
 * @description: /
 * @author: 阿星不是程序员
 **/

@FunctionalInterface
public interface BufferedUidProvider {

    /**
     * 提供缓存池的id
     * @param momentInSecond 秒数
     * @return 结果
     * */
    List<Long> provide(long momentInSecond);
}
