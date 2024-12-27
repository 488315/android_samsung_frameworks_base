package com.android.server.power;

import android.os.PowerManager;
import android.util.SparseArray;
import android.util.TimeUtils;
import com.android.server.power.PowerManagerService;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PowerHistorian {
    public static final PowerHistorian INSTANCE = new PowerHistorian();
    public final SparseArray mRecordCache;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayGroupRecord extends Record {
        public final int mGroupId;
        public final boolean mIsAdded;

        public DisplayGroupRecord(int i, boolean z) {
            this.mIsAdded = z;
            this.mGroupId = i;
        }

        @Override // com.android.server.power.PowerHistorian.Record
        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            int i = this.mGroupId;
            sb.append(String.format(" %-10s", i != 0 ? i != 2 ? i != 4 ? Integer.toString(i) : "CarLife" : "Dex" : "Default"));
            sb.append(String.format(" %-7s", this.mIsAdded ? "ADD" : "REMOVE"));
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MessageRecord extends Record {
        public final String mMessage;

        public MessageRecord(String str) {
            this.mMessage = str;
        }

        @Override // com.android.server.power.PowerHistorian.Record
        public final String toString() {
            return super.toString() + " " + this.mMessage;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Record {
        public static final SimpleDateFormat sDumpDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        public final long mRecordedTimeMillis = System.currentTimeMillis();

        public String toString() {
            return sDumpDateFormat.format(new Date(this.mRecordedTimeMillis));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RecordBuffer {
        public final LinkedList mBuffer = new LinkedList();
        public final int mCapacity;

        public RecordBuffer(int i) {
            this.mCapacity = i;
        }

        public final void dump(PrintWriter printWriter) {
            int size = this.mBuffer.size();
            int i = 0;
            while (i < size) {
                StringBuilder sb = new StringBuilder("[");
                int i2 = i + 1;
                sb.append(i2);
                sb.append("]");
                printWriter.println(String.format("  %-5s %s", sb.toString(), this.mBuffer.get(i)));
                i = i2;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WakeLockReleaseRecord extends Record {
        public final long mElapsedTimeMillis;
        public final PowerManagerService.WakeLock mWakeLock;

        public WakeLockReleaseRecord(PowerManagerService.WakeLock wakeLock, long j) {
            this.mWakeLock = wakeLock;
            this.mElapsedTimeMillis = j;
        }

        @Override // com.android.server.power.PowerHistorian.Record
        public final String toString() {
            return super.toString() + String.format(" %-18s", TimeUtils.formatDuration(this.mElapsedTimeMillis)) + this.mWakeLock;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WakeUpRecord extends Record {
        public final String mForegroundPackageName;
        public final int mGroupId;
        public final boolean mIsOn;
        public final String mOpPackageName;
        public final int mReasonInt;
        public final String mReasonStr;
        public final int mUid;

        public WakeUpRecord(int i, int i2, String str, String str2, int i3, boolean z) {
            this.mIsOn = z;
            this.mUid = i;
            this.mGroupId = i2;
            this.mReasonInt = i3;
            this.mReasonStr = z ? PowerManager.wakeReasonToString(i3) : PowerManager.sleepReasonToString(i3);
            this.mForegroundPackageName = str == null ? "" : str;
            this.mOpPackageName = str2 == null ? "" : str2;
        }

        @Override // com.android.server.power.PowerHistorian.Record
        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append(String.format(" %-5s", Integer.valueOf(this.mUid)));
            sb.append(String.format(" %-2s", Integer.valueOf(this.mGroupId)));
            sb.append(String.format(" %-3s", this.mIsOn ? "ON" : "OFF"));
            sb.append(String.format(" %-40s", this.mReasonStr));
            sb.append(" " + this.mForegroundPackageName);
            return sb.toString();
        }
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

    public final void addRecord(int i, Record record) {
        if (this.mRecordCache.contains(i)) {
            RecordBuffer recordBuffer = (RecordBuffer) this.mRecordCache.get(i);
            if (recordBuffer.mBuffer.size() >= recordBuffer.mCapacity) {
                recordBuffer.mBuffer.removeFirst();
            }
            recordBuffer.mBuffer.add(record);
        }
    }

    public final void dump(PrintWriter printWriter) {
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
}
