package com.android.internal.os;

import android.os.BatteryStats;
import android.os.Parcel;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.os.PowerStats;
import com.samsung.android.media.SemExtendedFormat;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class BatteryStatsHistoryIterator implements Iterator<BatteryStats.HistoryItem>, AutoCloseable {
    private static final boolean DEBUG = false;
    private static final String TAG = "BatteryStatsHistoryItr";
    private final BatteryStatsHistory mBatteryStatsHistory;
    private boolean mClosed;
    private final long mEndTimeMs;
    private boolean mNextItemReady;
    private final long mStartTimeMs;
    private boolean mTimeInitialized;
    private final BatteryStats.HistoryStepDetails mReadHistoryStepDetails = new BatteryStats.HistoryStepDetails();
    private final SparseArray<BatteryStats.HistoryTag> mHistoryTags = new SparseArray<>();
    private final PowerStats.DescriptorRegistry mDescriptorRegistry = new PowerStats.DescriptorRegistry();
    private BatteryStats.HistoryItem mHistoryItem = new BatteryStats.HistoryItem();

    public BatteryStatsHistoryIterator(BatteryStatsHistory history, long startTimeMs, long endTimeMs) {
        this.mBatteryStatsHistory = history;
        this.mStartTimeMs = startTimeMs;
        this.mEndTimeMs = endTimeMs != -1 ? endTimeMs : Long.MAX_VALUE;
        this.mHistoryItem.clear();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (!this.mNextItemReady) {
            advance();
        }
        return this.mHistoryItem != null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public BatteryStats.HistoryItem next() {
        if (!this.mNextItemReady) {
            advance();
        }
        this.mNextItemReady = false;
        return this.mHistoryItem;
    }

    private void advance() {
        do {
            Parcel p = this.mBatteryStatsHistory.getNextParcel(this.mStartTimeMs, this.mEndTimeMs);
            if (p != null) {
                if (!this.mTimeInitialized) {
                    this.mHistoryItem.time = this.mBatteryStatsHistory.getHistoryBufferStartTime(p);
                    this.mTimeInitialized = true;
                }
                long lastMonotonicTimeMs = this.mHistoryItem.time;
                long lastWalltimeMs = this.mHistoryItem.currentTime;
                try {
                    readHistoryDelta(p, this.mHistoryItem);
                    if (this.mHistoryItem.cmd != 5 && this.mHistoryItem.cmd != 7 && lastWalltimeMs != 0) {
                        this.mHistoryItem.currentTime = (this.mHistoryItem.time - lastMonotonicTimeMs) + lastWalltimeMs;
                    }
                    if (this.mEndTimeMs == 0 || this.mHistoryItem.time < this.mEndTimeMs) {
                    }
                } catch (Throwable t) {
                    Slog.wtf(TAG, "Corrupted battery history", t);
                }
            }
            this.mHistoryItem = null;
            this.mNextItemReady = true;
            close();
            return;
        } while (this.mHistoryItem.time < this.mStartTimeMs);
        this.mNextItemReady = true;
    }

    private void readHistoryDelta(Parcel src, BatteryStats.HistoryItem cur) {
        int batteryLevelInt;
        PowerStats.Descriptor descriptor;
        int firstToken = src.readInt();
        int deltaTimeToken = 131071 & firstToken;
        cur.cmd = (byte) 0;
        cur.numReadInts = 1;
        if (deltaTimeToken < 131069) {
            cur.time += deltaTimeToken;
        } else if (deltaTimeToken == 131069) {
            cur.readFromParcel(src);
            return;
        } else if (deltaTimeToken == 131070) {
            int delta = src.readInt();
            cur.time += delta;
            cur.numReadInts++;
        } else {
            long delta2 = src.readLong();
            cur.time += delta2;
            cur.numReadInts += 2;
        }
        if ((524288 & firstToken) != 0) {
            batteryLevelInt = src.readInt();
            readBatteryLevelInt(batteryLevelInt, cur);
            cur.numReadInts++;
        } else {
            batteryLevelInt = 0;
        }
        if ((262144 & firstToken) != 0) {
            int CurrentNTemperatureInt = src.readInt();
            readCurrentNTemperatureInt(CurrentNTemperatureInt, cur);
            cur.numReadInts++;
            int TemperatureInt2 = src.readInt();
            readTemperature2Int(TemperatureInt2, cur);
            cur.numReadInts++;
        }
        if ((firstToken & 131072) != 0) {
            cur.batterySecCurrentEvent = src.readInt();
            cur.numReadInts++;
            int secBatteryInfoInt = src.readInt();
            readSecBatteryInfoInt(secBatteryInfoInt, cur);
            cur.numReadInts++;
            cur.batterySecEvent = src.readInt();
            cur.numReadInts++;
            cur.protectBatteryMode = src.readInt();
            cur.numReadInts++;
        }
        if ((1048576 & firstToken) != 0) {
            int stateInt = src.readInt();
            cur.states = (16777215 & stateInt) | ((-33554432) & firstToken);
            cur.batteryStatus = (byte) ((stateInt >> 29) & 7);
            cur.batteryHealth = (byte) (((stateInt >> 26) & 7) | ((stateInt >> 14) & 8));
            cur.batteryPlugType = (byte) ((stateInt >> 24) & 3);
            switch (cur.batteryPlugType) {
                case 1:
                    cur.batteryPlugType = (byte) 1;
                    break;
                case 2:
                    cur.batteryPlugType = (byte) 2;
                    break;
                case 3:
                    cur.batteryPlugType = (byte) 4;
                    break;
            }
            cur.numReadInts++;
        } else {
            cur.states = (firstToken & (-33554432)) | (cur.states & 16777215);
        }
        if ((2097152 & firstToken) != 0) {
            cur.states2 = src.readInt();
        }
        if ((4194304 & firstToken) != 0) {
            int indexes = src.readInt();
            int wakeLockIndex = indexes & 65535;
            int wakeReasonIndex = (indexes >> 16) & 65535;
            if (readHistoryTag(src, wakeLockIndex, cur.localWakelockTag)) {
                cur.wakelockTag = cur.localWakelockTag;
            } else {
                cur.wakelockTag = null;
            }
            if (readHistoryTag(src, wakeReasonIndex, cur.localWakeReasonTag)) {
                cur.wakeReasonTag = cur.localWakeReasonTag;
            } else {
                cur.wakeReasonTag = null;
            }
            cur.numReadInts++;
        } else {
            cur.wakelockTag = null;
            cur.wakeReasonTag = null;
        }
        if ((8388608 & firstToken) != 0) {
            cur.eventTag = cur.localEventTag;
            int codeAndIndex = src.readInt();
            cur.eventCode = codeAndIndex & 65535;
            int index = (codeAndIndex >> 16) & 65535;
            if (readHistoryTag(src, index, cur.localEventTag)) {
                cur.eventTag = cur.localEventTag;
            } else {
                cur.eventTag = null;
            }
            cur.numReadInts++;
        } else {
            cur.eventCode = 0;
        }
        if ((batteryLevelInt & 1) != 0) {
            cur.stepDetails = this.mReadHistoryStepDetails;
            cur.stepDetails.readFromParcel(src);
        } else {
            cur.stepDetails = null;
        }
        if ((16777216 & firstToken) != 0) {
            cur.batteryChargeUah = src.readInt();
        }
        cur.modemRailChargeMah = src.readDouble();
        cur.wifiRailChargeMah = src.readDouble();
        if ((cur.states2 & 131072) != 0) {
            int extensionFlags = src.readInt();
            if ((extensionFlags & 1) != 0 && (descriptor = PowerStats.Descriptor.readSummaryFromParcel(src)) != null) {
                this.mDescriptorRegistry.register(descriptor);
            }
            if ((extensionFlags & 2) != 0) {
                cur.powerStats = PowerStats.readFromParcel(src, this.mDescriptorRegistry);
            } else {
                cur.powerStats = null;
            }
            if ((extensionFlags & 4) != 0) {
                cur.processStateChange = cur.localProcessStateChange;
                cur.processStateChange.readFromParcel(src);
                return;
            } else {
                cur.processStateChange = null;
                return;
            }
        }
        cur.powerStats = null;
        cur.processStateChange = null;
    }

    private boolean readHistoryTag(Parcel src, int index, BatteryStats.HistoryTag outTag) {
        if (index == 65535) {
            return false;
        }
        if ((32768 & index) != 0) {
            BatteryStats.HistoryTag tag = new BatteryStats.HistoryTag();
            tag.readFromParcel(src);
            tag.poolIdx = (-32769) & index;
            if (tag.poolIdx < 32766) {
                this.mHistoryTags.put(tag.poolIdx, tag);
            } else {
                tag.poolIdx = -1;
            }
            outTag.setTo(tag);
            return true;
        }
        BatteryStats.HistoryTag historyTag = this.mHistoryTags.get(index);
        if (historyTag != null) {
            outTag.setTo(historyTag);
        } else {
            outTag.string = null;
            outTag.uid = 0;
        }
        outTag.poolIdx = index;
        return true;
    }

    private static void readBatteryLevelInt(int batteryLevelInt, BatteryStats.HistoryItem out) {
        out.batteryLevel = (byte) (((-33554432) & batteryLevelInt) >>> 25);
        out.batteryTemperature = (short) ((33521664 & batteryLevelInt) >>> 15);
        int voltage = (batteryLevelInt & SemExtendedFormat.DataType.INVALID_DATA) >>> 1;
        if (voltage == 16383) {
            voltage = -1;
        }
        out.batteryVoltage = (short) voltage;
    }

    private static void readCurrentNTemperatureInt(int currentNTemperatureInt, BatteryStats.HistoryItem out) {
        out.pa_temp = (byte) (((-16777216) & currentNTemperatureInt) >>> 24);
        out.ap_temp = (byte) ((16711680 & currentNTemperatureInt) >>> 16);
        out.current = (short) ((65535 & currentNTemperatureInt) >>> 0);
    }

    private static void readTemperature2Int(int temperature2Int, BatteryStats.HistoryItem out) {
        out.subScreenDoze = (byte) ((536870912 & temperature2Int) >>> 29);
        out.subScreenOn = (byte) ((268435456 & temperature2Int) >>> 28);
        out.highSpeakerVolume = (byte) ((134217728 & temperature2Int) >>> 27);
        out.otgOnline = (byte) ((67108864 & temperature2Int) >>> 26);
        out.wifi_ap = (byte) ((33554432 & temperature2Int) >>> 25);
        out.skin_temp = (byte) ((16711680 & temperature2Int) >>> 16);
        out.sub_batt_temp = (byte) ((65280 & temperature2Int) >>> 8);
    }

    private static void readSecBatteryInfoInt(int secBatteryInfoInt, BatteryStats.HistoryItem out) {
        out.batterySecOnline = (byte) (((-16777216) & secBatteryInfoInt) >>> 24);
        out.batterySecTxShareEvent = 16777215 & secBatteryInfoInt;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (!this.mClosed) {
            this.mClosed = true;
            this.mBatteryStatsHistory.iteratorFinished();
        }
    }
}
