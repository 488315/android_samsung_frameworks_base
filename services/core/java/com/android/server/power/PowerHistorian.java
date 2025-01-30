package com.android.server.power;

import android.os.PowerManager;
import android.os.SystemClock;
import android.util.SparseArray;
import android.util.TimeUtils;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.power.PowerHistorian;
import com.android.server.power.PowerManagerService;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public final class PowerHistorian {
    public static final PowerHistorian INSTANCE = new PowerHistorian();
    public final SparseArray mRecordCache;

    public static PowerHistorian getInstance() {
        return INSTANCE;
    }

    public PowerHistorian() {
        SparseArray sparseArray = new SparseArray();
        this.mRecordCache = sparseArray;
        sparseArray.put(0, new RecordBuffer(50));
        sparseArray.put(1, new RecordBuffer(100));
        sparseArray.put(2, new RecordBuffer(200));
        sparseArray.put(3, new RecordBuffer(200));
        sparseArray.put(4, new RecordBuffer(50));
    }

    public void onWakeLockReleased(PowerManagerService.WakeLock wakeLock) {
        int i = wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
        if (i == 6 || i == 10 || i == 26) {
            long uptimeMillis = SystemClock.uptimeMillis() - wakeLock.mAcquireTime;
            if (uptimeMillis < 60000) {
                return;
            }
            addRecord(0, new WakeLockReleaseRecord(wakeLock, uptimeMillis));
        }
    }

    public void onScreenOn(int i, int i2, int i3, String str, String str2) {
        addRecord(1, new WakeUpRecord(true, i, i2, i3, str, str2));
    }

    public void onScreenOff(int i, int i2, int i3, String str) {
        addRecord(1, new WakeUpRecord(false, i, i2, i3, str, null));
    }

    public void onBrightnessReasonChanged(String str) {
        addRecord(2, new MessageRecord(str));
    }

    public void onAutoBrightnessEvent(String str) {
        addRecord(3, new MessageRecord(str));
    }

    public final void addRecord(int i, Record record) {
        if (this.mRecordCache.contains(i)) {
            ((RecordBuffer) this.mRecordCache.get(i)).add(record);
        }
    }

    public void onDisplayGroupChanged(boolean z, int i) {
        addRecord(4, new DisplayGroupRecord(z, i));
    }

    public List getWakeUpRecords() {
        final Class<WakeUpRecord> cls = WakeUpRecord.class;
        return new LinkedList((List) ((RecordBuffer) this.mRecordCache.get(1)).getBuffer().stream().filter(new Predicate() { // from class: com.android.server.power.PowerHistorian$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cls.isInstance((PowerHistorian.Record) obj);
            }
        }).map(new Function() { // from class: com.android.server.power.PowerHistorian$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (PowerHistorian.WakeUpRecord) cls.cast((PowerHistorian.Record) obj);
            }
        }).collect(Collectors.toList()));
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("PowerHistorian:");
        printWriter.println("  WakeLock Release History:");
        ((RecordBuffer) this.mRecordCache.get(0)).dump(printWriter);
        printWriter.println();
        printWriter.println("  DisplayGroup History:");
        ((RecordBuffer) this.mRecordCache.get(4)).dump(printWriter);
        printWriter.println();
        printWriter.println("  WakeUp History:");
        ((RecordBuffer) this.mRecordCache.get(1)).dump(printWriter);
        printWriter.println();
        printWriter.println("  AutomaticBrightness History:");
        ((RecordBuffer) this.mRecordCache.get(3)).dump(printWriter);
        printWriter.println();
        printWriter.println("  BrightnessReason History:");
        ((RecordBuffer) this.mRecordCache.get(2)).dump(printWriter);
    }

    public final class RecordBuffer {
        public final LinkedList mBuffer = new LinkedList();
        public final int mCapacity;

        public RecordBuffer(int i) {
            this.mCapacity = i;
        }

        public void add(Record record) {
            if (this.mBuffer.size() >= this.mCapacity) {
                this.mBuffer.removeFirst();
            }
            this.mBuffer.add(record);
        }

        public LinkedList getBuffer() {
            return this.mBuffer;
        }

        public void dump(PrintWriter printWriter) {
            int size = this.mBuffer.size();
            int i = 0;
            while (i < size) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                int i2 = i + 1;
                sb.append(i2);
                sb.append("]");
                printWriter.println(String.format("  %-5s %s", sb.toString(), this.mBuffer.get(i)));
                i = i2;
            }
        }
    }

    public abstract class Record {
        public static final SimpleDateFormat sDumpDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        public final long mRecordedTimeMillis = System.currentTimeMillis();

        public long getRecordedTimeMillis() {
            return this.mRecordedTimeMillis;
        }

        public String toString() {
            return sDumpDateFormat.format(new Date(this.mRecordedTimeMillis));
        }
    }

    public final class MessageRecord extends Record {
        public final String mMessage;

        public MessageRecord(String str) {
            this.mMessage = str;
        }

        @Override // com.android.server.power.PowerHistorian.Record
        public String toString() {
            return super.toString() + " " + this.mMessage;
        }
    }

    public final class WakeLockReleaseRecord extends Record {
        public final long mElapsedTimeMillis;
        public final PowerManagerService.WakeLock mWakeLock;

        public WakeLockReleaseRecord(PowerManagerService.WakeLock wakeLock, long j) {
            this.mWakeLock = wakeLock;
            this.mElapsedTimeMillis = j;
        }

        @Override // com.android.server.power.PowerHistorian.Record
        public String toString() {
            return super.toString() + String.format(" %-18s", TimeUtils.formatDuration(this.mElapsedTimeMillis)) + this.mWakeLock;
        }
    }

    public final class WakeUpRecord extends Record {
        public final String mForegroundPackageName;
        public final int mGroupId;
        public final boolean mIsOn;
        public final String mOpPackageName;
        public final int mReasonInt;
        public final String mReasonStr;
        public final int mUid;

        public final String ensureNonNull(String str) {
            return str == null ? "" : str;
        }

        @Override // com.android.server.power.PowerHistorian.Record
        public /* bridge */ /* synthetic */ long getRecordedTimeMillis() {
            return super.getRecordedTimeMillis();
        }

        public WakeUpRecord(boolean z, int i, int i2, int i3, String str, String str2) {
            String sleepReasonToString;
            this.mIsOn = z;
            this.mUid = i;
            this.mGroupId = i2;
            this.mReasonInt = i3;
            if (z) {
                sleepReasonToString = PowerManager.wakeReasonToString(i3);
            } else {
                sleepReasonToString = PowerManager.sleepReasonToString(i3);
            }
            this.mReasonStr = sleepReasonToString;
            this.mForegroundPackageName = ensureNonNull(str);
            this.mOpPackageName = ensureNonNull(str2);
        }

        public boolean isOn() {
            return this.mIsOn;
        }

        public int getReasonInt() {
            return this.mReasonInt;
        }

        public String getReasonStr() {
            return this.mReasonStr;
        }

        public String getForegroundPackageName() {
            return this.mForegroundPackageName;
        }

        public String getOpPackageName() {
            return this.mOpPackageName;
        }

        @Override // com.android.server.power.PowerHistorian.Record
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append(String.format(" %-5s", Integer.valueOf(this.mUid)));
            sb.append(String.format(" %-2s", Integer.valueOf(this.mGroupId)));
            Object[] objArr = new Object[1];
            objArr[0] = this.mIsOn ? "ON" : "OFF";
            sb.append(String.format(" %-3s", objArr));
            sb.append(String.format(" %-40s", this.mReasonStr));
            sb.append(String.format(" %s", this.mForegroundPackageName));
            return sb.toString();
        }
    }

    public final class DisplayGroupRecord extends Record {
        public final int mGroupId;
        public final boolean mIsAdded;

        public DisplayGroupRecord(boolean z, int i) {
            this.mIsAdded = z;
            this.mGroupId = i;
        }

        @Override // com.android.server.power.PowerHistorian.Record
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append(String.format(" %-10s", getDisplayGroupName(this.mGroupId)));
            Object[] objArr = new Object[1];
            objArr[0] = this.mIsAdded ? "ADD" : "REMOVE";
            sb.append(String.format(" %-7s", objArr));
            return sb.toString();
        }

        public String getDisplayGroupName(int i) {
            return i != 0 ? i != 2 ? i != 4 ? Integer.toString(i) : "CarLife" : "Dex" : "Default";
        }
    }
}
