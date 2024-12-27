package com.android.server.asks;

public final class RETVALUE {
    public int SA;
    public String eventNameForSA;
    public int isExecute;
    public MORERULES morerules;
    public int policy;
    public int status;
    public String tagName;

    public final void set(
            int i, int i2, int i3, int i4, String str, String str2, MORERULES morerules) {
        this.status = i;
        this.policy = i2;
        this.SA = i3;
        this.isExecute = i4;
        this.tagName = str;
        this.eventNameForSA = str2;
        this.morerules = morerules;
    }
}
