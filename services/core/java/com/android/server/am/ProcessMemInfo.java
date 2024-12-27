package com.android.server.am;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProcessMemInfo {
    public final String adjReason;
    public final String adjType;
    public long memtrack;
    public final String name;
    public final int oomAdj;
    public final int pid;
    public final int procState;
    public long pss;
    public long swapPss;

    public ProcessMemInfo(int i, int i2, String str, String str2, String str3, int i3) {
        this.name = str;
        this.pid = i;
        this.oomAdj = i2;
        this.procState = i3;
        this.adjType = str2;
        this.adjReason = str3;
    }
}
