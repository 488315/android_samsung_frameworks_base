package com.android.internal.os;

import android.util.ArrayMap;
import android.util.Slog;
import java.util.Map;

/* loaded from: classes5.dex */
public final class RailStats {
    private static final String CELLULAR_SUBSYSTEM = "cellular";
    private static final String TAG = "RailStats";
    private static final String WIFI_SUBSYSTEM = "wifi";
    private Map<Long, RailInfoData> mRailInfoData = new ArrayMap();
    private long mCellularTotalEnergyUseduWs = 0;
    private long mWifiTotalEnergyUseduWs = 0;
    private boolean mRailStatsAvailability = true;

    public RailStats getRailStats() {
        return this;
    }

    public void updateRailData(long j, String str, String str2, long j2, long j3) {
        if (str2.equals("wifi") || str2.equals(CELLULAR_SUBSYSTEM)) {
            RailInfoData railInfoData = this.mRailInfoData.get(Long.valueOf(j));
            if (railInfoData == null) {
                this.mRailInfoData.put(Long.valueOf(j), new RailInfoData(j, str, str2, j2, j3));
                if (str2.equals("wifi")) {
                    this.mWifiTotalEnergyUseduWs += j3;
                    return;
                } else {
                    if (str2.equals(CELLULAR_SUBSYSTEM)) {
                        this.mCellularTotalEnergyUseduWs += j3;
                        return;
                    }
                    return;
                }
            }
            long j4 = j2 - railInfoData.timestampSinceBootMs;
            long j5 = j3 - railInfoData.energyUsedSinceBootuWs;
            if (j4 < 0 || j5 < 0) {
                j5 = railInfoData.energyUsedSinceBootuWs;
            }
            railInfoData.timestampSinceBootMs = j2;
            railInfoData.energyUsedSinceBootuWs = j3;
            if (str2.equals("wifi")) {
                this.mWifiTotalEnergyUseduWs += j5;
            } else if (str2.equals(CELLULAR_SUBSYSTEM)) {
                this.mCellularTotalEnergyUseduWs += j5;
            }
        }
    }

    public void resetCellularTotalEnergyUsed() {
        this.mCellularTotalEnergyUseduWs = 0L;
    }

    public void resetWifiTotalEnergyUsed() {
        this.mWifiTotalEnergyUseduWs = 0L;
    }

    public long getCellularTotalEnergyUseduWs() {
        return this.mCellularTotalEnergyUseduWs;
    }

    public long getWifiTotalEnergyUseduWs() {
        return this.mWifiTotalEnergyUseduWs;
    }

    public void reset() {
        this.mCellularTotalEnergyUseduWs = 0L;
        this.mWifiTotalEnergyUseduWs = 0L;
    }

    public void setRailStatsAvailability(boolean z) {
        this.mRailStatsAvailability = z;
    }

    public boolean isRailStatsAvailable() {
        return this.mRailStatsAvailability;
    }

    public static class RailInfoData {
        private static final String TAG = "RailInfoData";
        public long energyUsedSinceBootuWs;
        public long index;
        public String railName;
        public String subSystemName;
        public long timestampSinceBootMs;

        private RailInfoData(long j, String str, String str2, long j2, long j3) {
            this.index = j;
            this.railName = str;
            this.subSystemName = str2;
            this.timestampSinceBootMs = j2;
            this.energyUsedSinceBootuWs = j3;
        }

        public void printData() {
            Slog.d(TAG, "Index = " + this.index);
            Slog.d(TAG, "RailName = " + this.railName);
            Slog.d(TAG, "SubSystemName = " + this.subSystemName);
            Slog.d(TAG, "TimestampSinceBootMs = " + this.timestampSinceBootMs);
            Slog.d(TAG, "EnergyUsedSinceBootuWs = " + this.energyUsedSinceBootuWs);
        }
    }
}
