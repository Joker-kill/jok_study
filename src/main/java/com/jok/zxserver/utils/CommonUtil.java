package com.jok.zxserver.utils;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author JOKER
 * create time 2024/8/3 19:50
 */
public class CommonUtil {
    private static final long twepoch = 1288834974657L;  // 基准时间戳
    private static final long workerIdBits = 5L;       // 机器ID所占的位数
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);  // 支持的最大机器ID
    private static final long sequenceBits = 12L;     // 序列号所占的位数
    private static final long workerIdShift = sequenceBits;  // 机器ID左移的位数
    private static final long timestampLeftShift = sequenceBits + workerIdBits;  // 时间戳左移的位数
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);  // 序列号掩码

    private long workerId;    // 机器ID
    private long sequence = 0L;  // 序列号
    private long lastTimestamp = -1L;  // 上次生成ID的时间戳

    public CommonUtil(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("worker Id can't be greater than " + maxWorkerId + " or less than 0");
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 等待到下一个毫秒
                timestamp = waitUntilNextTime(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        long id = ((timestamp - twepoch) << timestampLeftShift) |
                (workerId << workerIdShift) |
                sequence;
        return id;
    }
    private long waitUntilNextTime(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }


    public static Map<String,String> getAppInfo() throws IOException {
        Properties properties = new Properties();
        properties.load(CommonUtil.class.getClassLoader().getResourceAsStream("wxUniappConfig.properties"));
        String appId = properties.getProperty("appId");
        String appSecret = properties.getProperty("appSecret");
        HashMap<String, String> appInfo = new HashMap<>();
        appInfo.put("appId",appId);
        appInfo.put("appSecret",appSecret);

        return  appInfo;
    }
    public static String getTimeHM(String timeHMS){
        String[] parts = timeHMS.split(":");
        String hours = parts[0];
        String minutes = parts[1];
        return hours + ":" + minutes;
    }
}
