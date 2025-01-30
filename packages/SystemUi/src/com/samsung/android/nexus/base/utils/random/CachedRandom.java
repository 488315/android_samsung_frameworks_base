package com.samsung.android.nexus.base.utils.random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class CachedRandom {
    public static final NexusRandom sRandom = new NexusRandom();
    public final double mMax;
    public final double mMin;
    public final int mCacheSize = 100000;
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
