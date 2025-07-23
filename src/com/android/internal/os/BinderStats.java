package com.android.internal.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.util.Slog;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class BinderStats implements Parcelable {
    public static final Parcelable.Creator<BinderStats> CREATOR = new Parcelable.Creator<BinderStats>() { // from class: com.android.internal.os.BinderStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BinderStats createFromParcel(Parcel parcel) {
            return new BinderStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BinderStats[] newArray(int i) {
            return new BinderStats[i];
        }
    };
    private static final int MAGIC = -2130369756;
    private static final int MAX_ENTRY_NUMBER = 1000;
    private static final String TAG = "BinderStats";
    private final ArrayList<BinderStatsEntry> mData = new ArrayList<>();

    public static class BinderStatsUnit {
        public String binderClass;
        public long callCount;
        public int callingUid;
        public long cpuTimeMicros;
        public String methodName;
        public String packageName;
        public long recordedCallCount;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class BinderStatsEntry {
        public long mEndTime;
        public long mStartTime;
        public ArrayList<BinderStatsUnit> mStats = new ArrayList<>();

        public void addUnit(BinderStatsUnit binderStatsUnit) {
            this.mStats.add(binderStatsUnit);
        }
    }

    public BinderStats() {
    }

    public BinderStats(Parcel parcel) {
        reset();
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(MAGIC);
        parcel.writeInt(this.mData.size());
        for (int i2 = 0; i2 < this.mData.size(); i2++) {
            BinderStatsEntry binderStatsEntry = this.mData.get(i2);
            if (binderStatsEntry != null) {
                parcel.writeLong(binderStatsEntry.mStartTime);
                parcel.writeLong(binderStatsEntry.mEndTime);
                parcel.writeInt(binderStatsEntry.mStats.size());
                Iterator<BinderStatsUnit> it = binderStatsEntry.mStats.iterator();
                while (it.hasNext()) {
                    BinderStatsUnit next = it.next();
                    parcel.writeInt(next.callingUid);
                    parcel.writeString(next.packageName);
                    parcel.writeString(next.binderClass);
                    parcel.writeString(next.methodName);
                    parcel.writeLong(next.cpuTimeMicros);
                    parcel.writeLong(next.callCount);
                    parcel.writeLong(next.recordedCallCount);
                }
            }
        }
    }

    public int getSize() {
        return this.mData.size();
    }

    public void reset() {
        this.mData.clear();
    }

    private void readFromParcel(Parcel parcel) {
        reset();
        int readInt = parcel.readInt();
        if (readInt != MAGIC) {
            Slog.e(TAG, "MAGIC number mismatch expected=-2130369756 actual=" + readInt);
            return;
        }
        int readInt2 = parcel.readInt();
        for (int i = 0; i < readInt2; i++) {
            BinderStatsEntry binderStatsEntry = new BinderStatsEntry();
            binderStatsEntry.mStartTime = parcel.readLong();
            binderStatsEntry.mEndTime = parcel.readLong();
            int readInt3 = parcel.readInt();
            if (readInt3 > 5) {
                reset();
                Slog.e(TAG, "The binder_calls_stats file seems to be broken. We discard previous stats.");
                return;
            }
            for (int i2 = 0; i2 < readInt3; i2++) {
                BinderStatsUnit binderStatsUnit = new BinderStatsUnit();
                binderStatsUnit.callingUid = parcel.readInt();
                binderStatsUnit.packageName = parcel.readString();
                binderStatsUnit.binderClass = parcel.readString();
                binderStatsUnit.methodName = parcel.readString();
                binderStatsUnit.cpuTimeMicros = parcel.readLong();
                binderStatsUnit.callCount = parcel.readLong();
                binderStatsUnit.recordedCallCount = parcel.readLong();
                binderStatsEntry.addUnit(binderStatsUnit);
            }
            this.mData.add(binderStatsEntry);
        }
    }

    private byte[] readFully(InputStream inputStream, int[] iArr) throws IOException {
        byte[] bArr = new byte[16384];
        int i = 0;
        while (true) {
            int read = inputStream.read(bArr, i, bArr.length - i);
            if (read < 0) {
                Slog.d(TAG, "**** FINISHED READING: pos=" + i + " len=" + bArr.length);
                iArr[0] = i;
                return bArr;
            }
            i += read;
            if (i >= bArr.length) {
                byte[] bArr2 = new byte[i + 16384];
                System.arraycopy(bArr, 0, bArr2, 0, i);
                bArr = bArr2;
            }
        }
    }

    public void read(InputStream inputStream) {
        Parcel obtain = Parcel.obtain();
        try {
            int[] iArr = new int[1];
            obtain.unmarshall(readFully(inputStream, iArr), 0, iArr[0]);
            obtain.setDataPosition(0);
            inputStream.close();
            readFromParcel(obtain);
        } catch (IOException e) {
            Slog.e(TAG, "Failed to read stat files", e);
        } finally {
            obtain.recycle();
        }
    }

    public void addData(ArrayList<BinderStatsEntry> arrayList) {
        Iterator<BinderStatsEntry> it = arrayList.iterator();
        while (it.hasNext()) {
            BinderStatsEntry next = it.next();
            if (next.mStats.size() > 0) {
                this.mData.add(next);
            }
        }
        while (this.mData.size() > 1000) {
            try {
                this.mData.remove(0);
            } catch (IndexOutOfBoundsException e) {
                Slog.e(TAG, "IndexOutOfBoundsException occurs.", e);
                return;
            }
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("*** History of binder_calls_stats ***");
        Iterator<BinderStatsEntry> it = this.mData.iterator();
        while (it.hasNext()) {
            BinderStatsEntry next = it.next();
            printWriter.print("Time Duration: ");
            printWriter.print(DateFormat.format("yyyy-MM-dd HH:mm:ss", next.mStartTime));
            printWriter.print(" to ");
            printWriter.println(DateFormat.format("HH:mm:ss", next.mEndTime));
            Iterator<BinderStatsUnit> it2 = next.mStats.iterator();
            while (it2.hasNext()) {
                BinderStatsUnit next2 = it2.next();
                if (next2 != null) {
                    printWriter.println("   " + next2.packageName + NavigationBarInflaterView.KEY_CODE_START + next2.callingUid + NavigationBarInflaterView.KEY_CODE_END + next2.binderClass + "#" + next2.methodName + "," + next2.cpuTimeMicros + "," + next2.callCount + NavigationBarInflaterView.KEY_CODE_START + next2.recordedCallCount + NavigationBarInflaterView.KEY_CODE_END);
                }
            }
        }
    }
}
