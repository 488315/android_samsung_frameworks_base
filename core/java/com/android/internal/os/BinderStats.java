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
        public BinderStats createFromParcel(Parcel in) {
            return new BinderStats(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BinderStats[] newArray(int size) {
            return new BinderStats[size];
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

    public static class BinderStatsEntry {
        public long mEndTime;
        public long mStartTime;
        public ArrayList<BinderStatsUnit> mStats = new ArrayList<>();

        public void addUnit(BinderStatsUnit u) {
            this.mStats.add(u);
        }
    }

    public BinderStats() {
    }

    public BinderStats(Parcel in) {
        reset();
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(MAGIC);
        out.writeInt(this.mData.size());
        for (int i = 0; i < this.mData.size(); i++) {
            BinderStatsEntry e = this.mData.get(i);
            if (e != null) {
                out.writeLong(e.mStartTime);
                out.writeLong(e.mEndTime);
                out.writeInt(e.mStats.size());
                Iterator<BinderStatsUnit> it = e.mStats.iterator();
                while (it.hasNext()) {
                    BinderStatsUnit u = it.next();
                    out.writeInt(u.callingUid);
                    out.writeString(u.packageName);
                    out.writeString(u.binderClass);
                    out.writeString(u.methodName);
                    out.writeLong(u.cpuTimeMicros);
                    out.writeLong(u.callCount);
                    out.writeLong(u.recordedCallCount);
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

    private void readFromParcel(Parcel in) {
        reset();
        int mg = in.readInt();
        if (mg != MAGIC) {
            Slog.e(TAG, "MAGIC number mismatch expected=-2130369756 actual=" + mg);
            return;
        }
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            BinderStatsEntry e = new BinderStatsEntry();
            e.mStartTime = in.readLong();
            e.mEndTime = in.readLong();
            int num = in.readInt();
            if (num > 5) {
                reset();
                Slog.e(TAG, "The binder_calls_stats file seems to be broken. We discard previous stats.");
                return;
            }
            for (int j = 0; j < num; j++) {
                BinderStatsUnit u = new BinderStatsUnit();
                u.callingUid = in.readInt();
                u.packageName = in.readString();
                u.binderClass = in.readString();
                u.methodName = in.readString();
                u.cpuTimeMicros = in.readLong();
                u.callCount = in.readLong();
                u.recordedCallCount = in.readLong();
                e.addUnit(u);
            }
            this.mData.add(e);
        }
    }

    private byte[] readFully(InputStream stream, int[] outLen) throws IOException {
        int pos = 0;
        byte[] data = new byte[16384];
        while (true) {
            int amt = stream.read(data, pos, data.length - pos);
            if (amt < 0) {
                Slog.d(TAG, "**** FINISHED READING: pos=" + pos + " len=" + data.length);
                outLen[0] = pos;
                return data;
            }
            pos += amt;
            if (pos >= data.length) {
                byte[] newData = new byte[pos + 16384];
                System.arraycopy(data, 0, newData, 0, pos);
                data = newData;
            }
        }
    }

    public void read(InputStream stream) {
        Parcel in = Parcel.obtain();
        try {
            try {
                int[] outLen = new int[1];
                byte[] raw = readFully(stream, outLen);
                in.unmarshall(raw, 0, outLen[0]);
                in.setDataPosition(0);
                stream.close();
                readFromParcel(in);
            } catch (IOException e) {
                Slog.e(TAG, "Failed to read stat files", e);
            }
        } finally {
            in.recycle();
        }
    }

    public void addData(ArrayList<BinderStatsEntry> data) {
        Iterator<BinderStatsEntry> it = data.iterator();
        while (it.hasNext()) {
            BinderStatsEntry e = it.next();
            if (e.mStats.size() > 0) {
                this.mData.add(e);
            }
        }
        while (this.mData.size() > 1000) {
            try {
                this.mData.remove(0);
            } catch (IndexOutOfBoundsException e2) {
                Slog.e(TAG, "IndexOutOfBoundsException occurs.", e2);
                return;
            }
        }
    }

    public void dump(PrintWriter pw) {
        pw.println("*** History of binder_calls_stats ***");
        Iterator<BinderStatsEntry> it = this.mData.iterator();
        while (it.hasNext()) {
            BinderStatsEntry e = it.next();
            pw.print("Time Duration: ");
            pw.print(DateFormat.format("yyyy-MM-dd HH:mm:ss", e.mStartTime));
            pw.print(" to ");
            pw.println(DateFormat.format("HH:mm:ss", e.mEndTime));
            Iterator<BinderStatsUnit> it2 = e.mStats.iterator();
            while (it2.hasNext()) {
                BinderStatsUnit u = it2.next();
                if (u != null) {
                    pw.println("   " + u.packageName + NavigationBarInflaterView.KEY_CODE_START + u.callingUid + NavigationBarInflaterView.KEY_CODE_END + u.binderClass + "#" + u.methodName + "," + u.cpuTimeMicros + "," + u.callCount + NavigationBarInflaterView.KEY_CODE_START + u.recordedCallCount + NavigationBarInflaterView.KEY_CODE_END);
                }
            }
        }
    }
}
