package com.android.server.asks;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RETVALUE {
    public int SA;
    public String eventNameForSA;
    public int isExecute;
    public MORERULES morerules;
    public int policy;
    public int status;
    public String tagName;

    public final void set(int i, int i2, int i3, int i4, String str, String str2, MORERULES morerules) {
        this.status = i;
        this.policy = i2;
        this.SA = i3;
        this.isExecute = i4;
        this.tagName = str;
        this.eventNameForSA = str2;
        this.morerules = morerules;
    }
}
