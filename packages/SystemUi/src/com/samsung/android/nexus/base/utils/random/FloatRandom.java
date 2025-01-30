package com.samsung.android.nexus.base.utils.random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FloatRandom extends CachedRandom {
    public float[] mCache;

    public FloatRandom() {
        super(0.0d, 1.0d);
    }

    public final float get() {
        if (this.mIndex >= this.mIndexLimit) {
            this.mRewind++;
            this.mIndex = -1;
        }
        if (this.mNeedRefresh || this.mRewind >= 10) {
            this.mRewind = 0;
            this.mNeedRefresh = false;
            onRefreshCache();
            this.mIndex = -1;
        }
        float[] fArr = this.mCache;
        int i = this.mIndex + 1;
        this.mIndex = i;
        return fArr[i];
    }

    @Override // com.samsung.android.nexus.base.utils.random.CachedRandom
    public final void onCreate() {
        this.mCache = new float[100000];
    }

    public final void onRefreshCache() {
        float[] fArr = this.mCache;
        float f = (float) this.mMin;
        float f2 = (float) this.mMax;
        NexusRandom nexusRandom = CachedRandom.sRandom;
        nexusRandom.getClass();
        if (fArr == null || fArr.length <= 0) {
            return;
        }
        int i = this.mCacheSize + 0;
        if (i > fArr.length) {
            return;
        }
        float f3 = f2 - f;
        long j = nexusRandom.seed;
        for (int i2 = 0; i2 < i; i2++) {
            j = ((j * 25214903917L) + 11) & 281474976710655L;
            fArr[i2] = ((((int) (j >>> 24)) / 1.6777216E7f) * f3) + f;
        }
        nexusRandom.seed = j;
    }

    public FloatRandom(float f, float f2) {
        super(f, f2);
    }
}
