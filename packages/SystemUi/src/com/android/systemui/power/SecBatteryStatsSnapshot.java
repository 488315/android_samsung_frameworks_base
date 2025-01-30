package com.android.systemui.power;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecBatteryStatsSnapshot {
    public boolean automaticTestMode;
    public int batteryHealth;
    public int batteryLevel;
    public long chargingTime;
    public int chargingType;
    public int currentBatteryMode;
    public boolean isHiccupState;
    public String optimizationChargingFinishTime;

    public final String toString() {
        return "SecBatteryStatsSnapshot{batteryLevel=" + this.batteryLevel + ", currentBatteryMode=" + this.currentBatteryMode + ", chargingTime=" + this.chargingTime + ", chargingType=" + this.chargingType + ", batteryHealth=" + this.batteryHealth + ", automaticTestMode=" + this.automaticTestMode + ", isHiccupState=" + this.isHiccupState + ", optChargingFinishTime=" + this.optimizationChargingFinishTime + '}';
    }
}
