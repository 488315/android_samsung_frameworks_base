package com.android.server.chimera.heimdall;

import com.android.server.input.KeyboardMetricsCollector;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HeimdallProcessData {
    public static HeimdallProcessData sPool;
    public static int sPoolSize;
    public static final Object sPoolSync = new Object();
    public int anonAfterGc;
    public int anonBeforeGc;
    public int countDownPhaseRetry;
    public long delayPhase;
    public String firstAppPackageName;
    public String firstAppVersionName;
    public int flags;
    public long killTime;
    public HeimdallProcessData next;
    public int pid;
    public String processName;
    public long pss;
    public int rssAnon;
    public int rssFile;
    public long scanTimeAfterGc;
    public long scanTimeBeforeGc;
    public int uid;
    public int vmRss;
    public int vmSwap;

    public static HeimdallProcessData obtainData() {
        synchronized (sPoolSync) {
            try {
                HeimdallProcessData heimdallProcessData = sPool;
                if (heimdallProcessData == null) {
                    HeimdallProcessData heimdallProcessData2 = new HeimdallProcessData();
                    heimdallProcessData2.flags |= 128;
                    return heimdallProcessData2;
                }
                sPool = heimdallProcessData.next;
                heimdallProcessData.next = null;
                sPoolSize--;
                heimdallProcessData.flags |= 128;
                return heimdallProcessData;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isAlwaysRunningSpecKill() {
        return (this.flags & 4096) == 4096;
    }

    public final boolean isGlobalKill() {
        return (this.flags & 256) == 256;
    }

    public final boolean isSpecKill() {
        return (this.flags & 512) == 512;
    }

    public final void recycle() {
        if ((this.flags & 128) == 128) {
            this.flags = 0;
            this.processName = null;
            this.pid = -1;
            this.uid = -1;
            this.pss = -1L;
            this.firstAppPackageName = null;
            this.anonBeforeGc = 0;
            this.scanTimeBeforeGc = 0L;
            this.anonAfterGc = 0;
            this.scanTimeAfterGc = 0L;
            this.vmRss = 0;
            this.rssFile = 0;
            this.rssAnon = 0;
            this.vmSwap = 0;
            this.firstAppVersionName = null;
            this.killTime = 0L;
            this.delayPhase = 0L;
            this.countDownPhaseRetry = 0;
            synchronized (sPoolSync) {
                try {
                    int i = sPoolSize;
                    if (i < 50) {
                        this.next = sPool;
                        sPool = this;
                        sPoolSize = i + 1;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void setShouldKill() {
        if (isSpecKill() || isGlobalKill() || isAlwaysRunningSpecKill()) {
            this.flags |= 1024;
        } else {
            Heimdall.log("This HPD should not kill.");
        }
    }

    public final String toDumpString() {
        StringBuilder sb = new StringBuilder("HeimdallData{");
        sb.append(" processName=" + this.processName);
        sb.append(" pid=" + this.pid);
        sb.append(" packageName=" + this.firstAppPackageName);
        sb.append(" versionName=" + this.firstAppVersionName);
        sb.append(" anonBeforeGc=" + this.anonBeforeGc);
        sb.append(" graphicsBeforeGc=0");
        sb.append(" scanTimeBeforeGc=" + this.scanTimeBeforeGc);
        sb.append(" anonAfterGc=" + this.anonAfterGc);
        sb.append(" graphicsAfterGc=0");
        sb.append(" scanTimeAfterGc=" + this.scanTimeAfterGc);
        sb.append(" vmRss=" + this.vmRss);
        sb.append(" rssFile=" + this.rssFile);
        sb.append(" rssAnon=" + this.rssAnon);
        sb.append(" vmSwap=" + this.vmSwap);
        sb.append(" memTrackGl=0 memTrackEgl=0 memTrackOther=0");
        sb.append(" killTime=" + this.killTime);
        int i = this.flags;
        int i2 = i & (-i);
        sb.append(
                " currentPhase="
                        .concat(
                                i2 != 4
                                        ? i2 != 8
                                                ? i2 != 16
                                                        ? i2 != 32
                                                                ? i2 != 64
                                                                        ? KeyboardMetricsCollector
                                                                                .DEFAULT_LANGUAGE_TAG
                                                                        : "StartPhase"
                                                                : "ScanPhase"
                                                        : "GCPhase"
                                                : "RescanPhase"
                                        : "KillAndReportPhase"));
        sb.append(" isGlobalKill=" + isGlobalKill());
        sb.append(" isSpecKill=" + isSpecKill());
        sb.append(" isAlwaysRunningSpecKill=" + isAlwaysRunningSpecKill());
        StringBuilder sb2 = new StringBuilder(" shouldKill=");
        sb2.append((this.flags & 1024) == 1024);
        sb.append(sb2.toString());
        sb.append(" }");
        return sb.toString();
    }
}
