package com.wifiyou.utils;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         1/17/17
 */

public class MemorySpeedMapUtil {

    public static float memoryToSpeed(float memoryPercent) {
        //10/(1+e^(-x+5))
        float value = memoryPercent * 10;
        double y = 10 / (1 + Math.pow(Math.E, (3 - value)));
        return (float) (y / 10);
    }


    public static float commonMermoryToSpeed(float memoryPercent) {
        return memoryToSpeed(memoryPercent) * 8 / 10;
    }

}