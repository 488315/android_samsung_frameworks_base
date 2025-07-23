package com.samsung.android.nexus.base.utils.random;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FloatRandom extends CachedRandom {
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
            NexusRandom nexusRandom = CachedRandom.sRandom;
            float[] fArr = this.mCache;
            float f = (float) this.mMin;
            float f2 = (float) this.mMax;
            nexusRandom.getClass();
            if (fArr != null && fArr.length > 0 && 100000 <= fArr.length) {
                float f3 = f2 - f;
                long j = nexusRandom.seed;
                for (int i = 0; i < 100000; i++) {
                    j = ((j * 25214903917L) + 11) & 281474976710655L;
                    fArr[i] = ((((int) (j >>> 24)) / 1.6777216E7f) * f3) + f;
                }
                nexusRandom.seed = j;
            }
            this.mIndex = -1;
        }
        float[] fArr2 = this.mCache;
        int i2 = this.mIndex + 1;
        this.mIndex = i2;
        return fArr2[i2];
    }

    @Override // com.samsung.android.nexus.base.utils.random.CachedRandom
    public final void onCreate() {
        this.mCache = new float[100000];
    }

    public FloatRandom(float f, float f2) {
        super(f, f2);
    }
}
