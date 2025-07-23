package android.os;

import com.android.internal.util.FrameworkStatsLog;

/* loaded from: classes3.dex */
public final class OomKillRecord {
    private long mAnonRssInKb;
    private long mFileRssInKb;
    private short mOomScoreAdj;
    private long mPgTablesInKb;
    private int mPid;
    private String mProcessName;
    private long mShmemRssInKb;
    private long mTimeStampInMillis;
    private long mTotalVmInKb;
    private int mUid;

    public OomKillRecord(long j, int i, int i2, String str, short s, long j2, long j3, long j4, long j5, long j6) {
        this.mTimeStampInMillis = j;
        this.mPid = i;
        this.mUid = i2;
        this.mProcessName = str;
        this.mOomScoreAdj = s;
        this.mTotalVmInKb = j2;
        this.mAnonRssInKb = j3;
        this.mFileRssInKb = j4;
        this.mShmemRssInKb = j5;
        this.mPgTablesInKb = j6;
    }

    public void logKillOccurred() {
        FrameworkStatsLog.write(754, this.mUid, this.mPid, this.mOomScoreAdj, this.mTimeStampInMillis, this.mProcessName, this.mTotalVmInKb, this.mAnonRssInKb, this.mFileRssInKb, this.mShmemRssInKb, this.mPgTablesInKb);
    }

    public long getTimestampMilli() {
        return this.mTimeStampInMillis;
    }

    public int getPid() {
        return this.mPid;
    }

    public int getUid() {
        return this.mUid;
    }

    public String getProcessName() {
        return this.mProcessName;
    }

    public short getOomScoreAdj() {
        return this.mOomScoreAdj;
    }
}
