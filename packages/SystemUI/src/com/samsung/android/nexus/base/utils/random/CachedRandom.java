package com.samsung.android.nexus.base.utils.random;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class CachedRandom {
    public static final NexusRandom sRandom = new NexusRandom();
    public final double mMax;
    public final double mMin;
    public final int mIndexLimit = 99999;
    public int mIndex = 0;
    public int mRewind = 0;
    public boolean mNeedRefresh = true;

    public CachedRandom(double d, double d2) {
        this.mMin = d;
        this.mMax = d2;
        onCreate();
    }

    public abstract void onCreate();
}
