package com.android.server.asks;

/* compiled from: UnknownStore.java */
/* loaded from: classes.dex */
public class PEMINFO {
    public int MAX;
    public int MIN;

    /* renamed from: SA */
    public int f1642SA;
    public MORERULES moreRules = null;
    public int policy;
    public int policyTarget;
    public int reportedTarget;

    public void setMoreRules(String str, int i) {
        if (this.moreRules == null) {
            this.moreRules = new MORERULES();
        }
        this.moreRules.set(str, i);
    }

    public void set(int i, int i2, int i3, int i4, int i5, int i6) {
        this.MIN = i;
        this.MAX = i2;
        this.f1642SA = i3;
        this.policy = i4;
        this.policyTarget = i5;
        this.reportedTarget = i6;
        this.moreRules = null;
    }
}
