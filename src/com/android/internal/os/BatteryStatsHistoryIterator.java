package com.android.internal.os;

import android.os.BatteryStats;
import android.os.Parcel;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.os.BatteryStatsHistory;
import com.android.internal.os.PowerStats;
import java.util.Iterator;
import java.util.Queue;

/* loaded from: classes5.dex */
public class BatteryStatsHistoryIterator implements Iterator<BatteryStats.HistoryItem>, AutoCloseable {
    private static final boolean DEBUG = false;
    private static final String TAG = "BatteryStatsHistoryItr";
    private long mBaseMonotonicTime;
    private long mBaseTimeUtc;
    private final BatteryStatsHistory mBatteryStatsHistory;
    private boolean mClosed;
    private final long mEndTimeMs;
    private BatteryStats.HistoryItem mHistoryItem;
    private int mItemIndex;
    private final int mMaxHistoryItems;
    private boolean mNextItemReady;
    private Queue<BatteryStatsHistory.BatteryHistoryParcelContainer> mParcelContainers;
    private int mParcelDataPosition;
    private final long mStartTimeMs;
    private boolean mTimeInitialized;
    private final BatteryStats.HistoryStepDetails mReadHistoryStepDetails = new BatteryStats.HistoryStepDetails();
    private final SparseArray<BatteryStats.HistoryTag> mHistoryTags = new SparseArray<>();
    private final PowerStats.DescriptorRegistry mDescriptorRegistry = new PowerStats.DescriptorRegistry();

    private static int extractSignedBitField(int i, int i2, int i3) {
        int i4 = i2 >>> i3;
        int i5 = (i & i2) >>> i3;
        return (((i4 >>> 1) ^ i4) & i5) != 0 ? i5 | (~i4) : i5;
    }

    public BatteryStatsHistoryIterator(BatteryStatsHistory batteryStatsHistory, long j, long j2) {
        BatteryStats.HistoryItem historyItem = new BatteryStats.HistoryItem();
        this.mHistoryItem = historyItem;
        this.mItemIndex = 0;
        this.mBatteryStatsHistory = batteryStatsHistory;
        this.mStartTimeMs = j;
        this.mEndTimeMs = j2 == -1 ? Long.MAX_VALUE : j2;
        historyItem.clear();
        this.mMaxHistoryItems = batteryStatsHistory.getEstimatedItemCount();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (!this.mNextItemReady) {
            if (!advance()) {
                this.mHistoryItem = null;
                close();
            }
            this.mNextItemReady = true;
        }
        return this.mHistoryItem != null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public BatteryStats.HistoryItem next() {
        if (!this.mNextItemReady && !advance()) {
            this.mHistoryItem = null;
            close();
        }
        this.mNextItemReady = false;
        return this.mHistoryItem;
    }

    private boolean advance() {
        if (this.mParcelContainers == null) {
            this.mParcelContainers = this.mBatteryStatsHistory.getParcelContainers(this.mStartTimeMs, this.mEndTimeMs);
        }
        while (true) {
            BatteryStatsHistory.BatteryHistoryParcelContainer peek = this.mParcelContainers.peek();
            if (peek == null) {
                return false;
            }
            Parcel parcel = peek.getParcel();
            if (parcel == null || parcel.dataPosition() >= parcel.dataSize()) {
                peek.close();
                this.mParcelContainers.remove();
                this.mParcelDataPosition = 0;
            } else {
                if (!this.mTimeInitialized) {
                    long monotonicStartTime = peek.getMonotonicStartTime();
                    this.mBaseMonotonicTime = monotonicStartTime;
                    this.mHistoryItem.time = monotonicStartTime;
                    this.mTimeInitialized = true;
                }
                try {
                    readHistoryDelta(parcel, this.mHistoryItem);
                    int dataPosition = parcel.dataPosition();
                    if (dataPosition <= this.mParcelDataPosition) {
                        Slog.wtf(TAG, "Corrupted battery history, parcel is not progressing: " + dataPosition + " of " + parcel.dataSize());
                        return false;
                    }
                    this.mParcelDataPosition = dataPosition;
                    if (this.mHistoryItem.cmd == 5 || this.mHistoryItem.cmd == 7) {
                        this.mBaseTimeUtc = this.mHistoryItem.currentTime - (this.mHistoryItem.time - this.mBaseMonotonicTime);
                    }
                    if (this.mHistoryItem.time >= this.mStartTimeMs) {
                        long j = this.mEndTimeMs;
                        if (j != 0 && j != -1 && this.mHistoryItem.time >= this.mEndTimeMs) {
                            return false;
                        }
                        int i = this.mItemIndex;
                        this.mItemIndex = i + 1;
                        if (i > this.mMaxHistoryItems) {
                            Slog.wtfStack(TAG, "Number of battery history items is too large: " + this.mItemIndex);
                            return false;
                        }
                        BatteryStats.HistoryItem historyItem = this.mHistoryItem;
                        historyItem.currentTime = this.mBaseTimeUtc + (historyItem.time - this.mBaseMonotonicTime);
                        return true;
                    }
                } catch (Throwable th) {
                    Slog.wtf(TAG, "Corrupted battery history", th);
                    return false;
                }
            }
        }
    }

    private void readHistoryDelta(Parcel parcel, BatteryStats.HistoryItem historyItem) {
        int i;
        PowerStats.Descriptor readSummaryFromParcel;
        int i2;
        int readInt = parcel.readInt();
        int i3 = 131071 & readInt;
        historyItem.cmd = (byte) 0;
        historyItem.numReadInts = 1;
        if (i3 < 131069) {
            historyItem.time += i3;
        } else if (i3 == 131069) {
            historyItem.readFromParcel(parcel);
            return;
        } else if (i3 == 131070) {
            historyItem.time += parcel.readInt();
            historyItem.numReadInts++;
        } else {
            historyItem.time += parcel.readLong();
            historyItem.numReadInts += 2;
        }
        if ((524288 & readInt) != 0) {
            i = parcel.readInt();
            historyItem.numReadInts++;
            if ((i & 2) != 0) {
                i2 = parcel.readInt();
                historyItem.numReadInts++;
            } else {
                i2 = 0;
            }
            readBatteryLevelInts(i, i2, historyItem);
        } else {
            i = 0;
        }
        if ((262144 & readInt) != 0) {
            readCurrentNTemperatureInt(parcel.readInt(), historyItem);
            historyItem.numReadInts++;
            readTemperature2Int(parcel.readInt(), historyItem);
            historyItem.numReadInts++;
        }
        if ((readInt & 131072) != 0) {
            historyItem.batterySecCurrentEvent = parcel.readInt();
            historyItem.numReadInts++;
            readSecBatteryInfoInt(parcel.readInt(), historyItem);
            historyItem.numReadInts++;
            historyItem.batterySecEvent = parcel.readInt();
            historyItem.numReadInts++;
            historyItem.protectBatteryMode = parcel.readInt();
            historyItem.numReadInts++;
        }
        if ((1048576 & readInt) != 0) {
            int readInt2 = parcel.readInt();
            historyItem.states = (16777215 & readInt2) | ((-33554432) & readInt);
            historyItem.batteryStatus = (byte) ((readInt2 >> 29) & 7);
            historyItem.batteryHealth = (byte) (((readInt2 >> 26) & 7) | ((readInt2 >> 14) & 8));
            historyItem.batteryPlugType = (byte) ((readInt2 >> 24) & 3);
            byte b = historyItem.batteryPlugType;
            if (b == 1) {
                historyItem.batteryPlugType = (byte) 1;
            } else if (b == 2) {
                historyItem.batteryPlugType = (byte) 2;
            } else if (b == 3) {
                historyItem.batteryPlugType = (byte) 4;
            }
            historyItem.numReadInts++;
        } else {
            historyItem.states = (readInt & (-33554432)) | (historyItem.states & 16777215);
        }
        if ((2097152 & readInt) != 0) {
            historyItem.states2 = parcel.readInt();
        }
        if ((4194304 & readInt) != 0) {
            int readInt3 = parcel.readInt();
            int i4 = readInt3 & 65535;
            int i5 = (readInt3 >> 16) & 65535;
            if (readHistoryTag(parcel, i4, historyItem.localWakelockTag)) {
                historyItem.wakelockTag = historyItem.localWakelockTag;
            } else {
                historyItem.wakelockTag = null;
            }
            if (readHistoryTag(parcel, i5, historyItem.localWakeReasonTag)) {
                historyItem.wakeReasonTag = historyItem.localWakeReasonTag;
            } else {
                historyItem.wakeReasonTag = null;
            }
            historyItem.numReadInts++;
        } else {
            historyItem.wakelockTag = null;
            historyItem.wakeReasonTag = null;
        }
        if ((8388608 & readInt) != 0) {
            historyItem.eventTag = historyItem.localEventTag;
            int readInt4 = parcel.readInt();
            historyItem.eventCode = readInt4 & 65535;
            if (readHistoryTag(parcel, (readInt4 >> 16) & 65535, historyItem.localEventTag)) {
                historyItem.eventTag = historyItem.localEventTag;
            } else {
                historyItem.eventTag = null;
            }
            historyItem.numReadInts++;
        } else {
            historyItem.eventCode = 0;
        }
        if ((i & 1) != 0) {
            historyItem.stepDetails = this.mReadHistoryStepDetails;
            historyItem.stepDetails.readFromParcel(parcel);
        } else {
            historyItem.stepDetails = null;
        }
        if ((readInt & 16777216) != 0) {
            historyItem.batteryChargeUah = parcel.readInt();
        }
        historyItem.modemRailChargeMah = parcel.readDouble();
        historyItem.wifiRailChargeMah = parcel.readDouble();
        if ((historyItem.states2 & 131072) != 0) {
            int readInt5 = parcel.readInt();
            if ((readInt5 & 1) != 0 && (readSummaryFromParcel = PowerStats.Descriptor.readSummaryFromParcel(parcel)) != null) {
                this.mDescriptorRegistry.register(readSummaryFromParcel);
            }
            if ((readInt5 & 2) != 0) {
                historyItem.powerStats = PowerStats.readFromParcel(parcel, this.mDescriptorRegistry);
            } else {
                historyItem.powerStats = null;
            }
            if ((readInt5 & 4) != 0) {
                historyItem.processStateChange = historyItem.localProcessStateChange;
                historyItem.processStateChange.readFromParcel(parcel);
                return;
            } else {
                historyItem.processStateChange = null;
                return;
            }
        }
        historyItem.powerStats = null;
        historyItem.processStateChange = null;
    }

    private boolean readHistoryTag(Parcel parcel, int i, BatteryStats.HistoryTag historyTag) {
        if (i == 65535) {
            return false;
        }
        if ((32768 & i) != 0) {
            BatteryStats.HistoryTag historyTag2 = new BatteryStats.HistoryTag();
            historyTag2.readFromParcel(parcel);
            historyTag2.poolIdx = (-32769) & i;
            if (historyTag2.poolIdx < 32766) {
                this.mHistoryTags.put(historyTag2.poolIdx, historyTag2);
            } else {
                historyTag2.poolIdx = -1;
            }
            historyTag.setTo(historyTag2);
            return true;
        }
        BatteryStats.HistoryTag historyTag3 = this.mHistoryTags.get(i);
        if (historyTag3 != null) {
            historyTag.setTo(historyTag3);
        } else {
            historyTag.string = null;
            historyTag.uid = 0;
        }
        historyTag.poolIdx = i;
        return true;
    }

    private static void readBatteryLevelInts(int i, int i2, BatteryStats.HistoryItem historyItem) {
        historyItem.batteryLevel = (byte) (historyItem.batteryLevel + extractSignedBitField(i, -16777216, 24));
        if ((i & 2) == 0) {
            historyItem.batteryTemperature = (short) (historyItem.batteryTemperature + extractSignedBitField(i, 16744448, 15));
            historyItem.batteryVoltage = (short) (historyItem.batteryVoltage + extractSignedBitField(i, 32764, 2));
        } else {
            historyItem.batteryTemperature = (short) extractSignedBitField(i2, -65536, 16);
            historyItem.batteryVoltage = (short) extractSignedBitField(i2, 65535, 0);
        }
    }

    private static void readCurrentNTemperatureInt(int i, BatteryStats.HistoryItem historyItem) {
        historyItem.pa_temp = (byte) (((-16777216) & i) >>> 24);
        historyItem.ap_temp = (byte) ((16711680 & i) >>> 16);
        historyItem.current = (short) (i & 65535);
    }

    private static void readTemperature2Int(int i, BatteryStats.HistoryItem historyItem) {
        historyItem.subScreenDoze = (byte) ((536870912 & i) >>> 29);
        historyItem.subScreenOn = (byte) ((268435456 & i) >>> 28);
        historyItem.highSpeakerVolume = (byte) ((134217728 & i) >>> 27);
        historyItem.otgOnline = (byte) ((67108864 & i) >>> 26);
        historyItem.wifi_ap = (byte) ((33554432 & i) >>> 25);
        historyItem.skin_temp = (byte) ((16711680 & i) >>> 16);
        historyItem.sub_batt_temp = (byte) ((i & 65280) >>> 8);
    }

    private static void readSecBatteryInfoInt(int i, BatteryStats.HistoryItem historyItem) {
        historyItem.batterySecOnline = (byte) (((-16777216) & i) >>> 24);
        historyItem.batterySecTxShareEvent = i & 16777215;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.mClosed) {
            return;
        }
        this.mClosed = true;
        this.mBatteryStatsHistory.iteratorFinished();
    }
}
