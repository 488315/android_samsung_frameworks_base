package com.android.server.battery;

public abstract class BattFunctions {
    public static long mLastRemainingChargingTime = -1;

    public abstract class MultiSbpController {
        public static int mLastBatteryConnections = 3;
    }
}
