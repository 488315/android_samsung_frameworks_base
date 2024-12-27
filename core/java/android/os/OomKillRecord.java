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

    public OomKillRecord(long timeStampInMillis, int pid, int uid, String processName, short oomScoreAdj, long totalVmInKb, long anonRssInKb, long fileRssInKb, long shmemRssInKb, long pgTablesInKb) {
        this.mTimeStampInMillis = timeStampInMillis;
        this.mPid = pid;
        this.mUid = uid;
        this.mProcessName = processName;
        this.mOomScoreAdj = oomScoreAdj;
        this.mTotalVmInKb = totalVmInKb;
        this.mAnonRssInKb = anonRssInKb;
        this.mFileRssInKb = fileRssInKb;
        this.mShmemRssInKb = shmemRssInKb;
        this.mPgTablesInKb = pgTablesInKb;
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
