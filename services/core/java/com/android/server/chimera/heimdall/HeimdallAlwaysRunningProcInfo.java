package com.android.server.chimera.heimdall;

public final class HeimdallAlwaysRunningProcInfo {
    public final int abnormalType;
    public int adj;
    public long alwaysRunningStartTime;
    public boolean isForegroundApp;
    public final String packageName;
    public final int pid;
    public final String processName;
    public long reportTime;
    public final int uid;

    public HeimdallAlwaysRunningProcInfo(int i, int i2, int i3, String str, String str2) {
        this.packageName = str;
        this.processName = str2;
        this.uid = i;
        this.pid = i2;
        this.adj = i3;
    }

    public HeimdallAlwaysRunningProcInfo(HeimdallProcessData heimdallProcessData) {
        this.packageName = heimdallProcessData.firstAppPackageName;
        this.processName = heimdallProcessData.processName;
        this.uid = heimdallProcessData.uid;
        this.pid = heimdallProcessData.pid;
        this.abnormalType =
                heimdallProcessData.isGlobalKill()
                        ? 3020
                        : heimdallProcessData.isSpecKill()
                                ? 3021
                                : heimdallProcessData.isAlwaysRunningSpecKill() ? 3023 : 3022;
    }

    public final String toDumpString() {
        StringBuilder sb = new StringBuilder("HeimdallAlwaysRunningProcInfo{");
        sb.append(" processName=" + this.processName);
        sb.append(" pid=" + this.pid);
        sb.append(" packageName=" + this.packageName);
        sb.append(" reportTime=" + this.reportTime);
        sb.append(" abnormalType=" + this.abnormalType);
        sb.append(" }");
        return sb.toString();
    }
}
