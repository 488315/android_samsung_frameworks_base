package com.samsung.android.nexus.particle.emitter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.util.LruCache;
import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.base.utils.range.FloatRangeable;
import com.samsung.android.nexus.base.utils.range.LongRangeable;
import com.samsung.android.nexus.particle.emitter.texture.BitmapCache;
import com.samsung.android.nexus.particle.emitter.texture.BitmapParticleTexture;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Particle {
    public static final Particle$$ExternalSyntheticLambda0 mEmitterScheduleComparator = new Particle$$ExternalSyntheticLambda0();
    public static float sDensity;
    public static final Paint sPaint;
    public static final ParticleLinkedListPool sPool;
    public PorterDuffColorFilter mColorFilter;
    public float mFraction;
    public long mLifeTime;
    public Emitter mParentEmitter;
    public BitmapParticleTexture mParticleTexture;
    public String mSubEmitterKey;
    public Particle next;
    public boolean mEnable = true;
    public boolean mEnableEmission = true;
    public boolean mIsInSight = false;
    public final RectF mTempWorldBounds = new RectF();
    public final ArrayList mEmitterSchedules = new ArrayList();
    public final ArrayList mTempEmitterSchedules = new ArrayList();
    public boolean mScheduleCheckLock = false;
    public final Status status = new Status();
    public long mStartTime = 0;
    public long mEndTime = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class EmitterSchedule {
        public final Emitter emitter;
        public long nextTime;

        public EmitterSchedule(Particle particle, Emitter emitter, long j) {
            this.emitter = emitter;
            this.nextTime = j;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ParticleLinkedList {
        public Particle head = null;
        public Particle tail = null;
        public int size = 0;

        public final void put(ParticleLinkedList particleLinkedList) {
            int i = particleLinkedList.size;
            if (i <= 0) {
                return;
            }
            if (this.head == null) {
                this.head = particleLinkedList.head;
            } else {
                this.tail.next = particleLinkedList.head;
            }
            this.tail = particleLinkedList.tail;
            this.size += i;
        }

        public final void transferFrom(ParticleLinkedList particleLinkedList, Particle particle, Particle particle2, Particle particle3, int i) {
            if (particle != null) {
                particle.next = particle3.next;
            }
            if (particle2 == particleLinkedList.head) {
                particleLinkedList.head = particle3.next;
            }
            if (particle3 == particleLinkedList.tail) {
                particleLinkedList.tail = particle;
            }
            if (this.head == null) {
                this.head = particle2;
            } else {
                this.tail.next = particle2;
            }
            this.tail = particle3;
            particle3.next = null;
            this.size += i;
            particleLinkedList.size -= i;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ParticleLinkedListPool extends ParticleLinkedList {
        public int createSize = 0;

        public final ParticleLinkedList retain(int i) {
            ParticleLinkedListPool particleLinkedListPool;
            int i2;
            ParticleLinkedList particleLinkedList = new ParticleLinkedList();
            int min = Math.min(this.size, i);
            int i3 = 1;
            if (min > 0) {
                Particle particle = this.head;
                Particle particle2 = particle;
                for (int i4 = 1; i4 < min; i4++) {
                    particle2 = particle2.next;
                }
                particleLinkedListPool = this;
                particleLinkedList.transferFrom(particleLinkedListPool, null, particle, particle2, min);
                i2 = i - min;
            } else {
                particleLinkedListPool = this;
                i2 = i;
            }
            if (i2 > 0) {
                int i5 = particleLinkedListPool.createSize;
                if (20000 <= i5 + i2) {
                    i2 = Math.min(PluginLockInstancePolicy.DISABLED_BY_MODE - i5, i);
                }
                if (i2 != 0) {
                    ParticleLinkedList particleLinkedList2 = new ParticleLinkedList();
                    Particle particle3 = new Particle();
                    Particle particle4 = particle3;
                    while (i3 < i2) {
                        Particle particle5 = new Particle();
                        particle4.next = particle5;
                        i3++;
                        particle4 = particle5;
                    }
                    particleLinkedList2.head = particle3;
                    particleLinkedList2.tail = particle4;
                    particleLinkedList2.size = i2;
                    particleLinkedListPool.createSize += i2;
                    particleLinkedList.put(particleLinkedList2);
                }
            }
            return particleLinkedList;
        }
    }

    static {
        ParticleLinkedListPool particleLinkedListPool = new ParticleLinkedListPool();
        sPool = particleLinkedListPool;
        Paint paint = new Paint();
        Paint paint2 = new Paint(1);
        sPaint = paint2;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        paint.setTextSize(20.0f);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setDither(true);
        long nanoTime = System.nanoTime();
        particleLinkedListPool.put(particleLinkedListPool.retain(3000));
        Log.i("Particle", "static initializer: took" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime) + "ms");
    }

    public Particle() {
    }

    /* JADX WARN: Removed duplicated region for block: B:147:0x04d5  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x02d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkEmitterSchedule(long r57, com.samsung.android.nexus.particle.emitter.Status r59) {
        /*
            Method dump skipped, instructions count: 1528
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.nexus.particle.emitter.Particle.checkEmitterSchedule(long, com.samsung.android.nexus.particle.emitter.Status):void");
    }

    public final void release() {
        this.mEnable = false;
        this.mEnableEmission = false;
        this.mIsInSight = false;
        this.mStartTime = -1L;
        this.mEndTime = -1L;
        this.mLifeTime = -1L;
        this.mFraction = 0.0f;
        this.mParentEmitter = null;
        this.mSubEmitterKey = "";
        this.status.reset();
        BitmapParticleTexture bitmapParticleTexture = this.mParticleTexture;
        if (bitmapParticleTexture != null) {
            bitmapParticleTexture.onRelease();
            this.mParticleTexture = null;
        }
        this.mEmitterSchedules.clear();
        this.mTempEmitterSchedules.clear();
        this.mScheduleCheckLock = false;
        this.mTempWorldBounds.setEmpty();
    }

    public final void start(long j) {
        Bitmap loadToCache;
        ParticleRule particleRule = this.mParentEmitter.mParticleRule;
        if (particleRule == null) {
            throw new IllegalStateException("can not start with null rule");
        }
        Status status = this.status;
        int i = status.color;
        status.color = i;
        this.mColorFilter = new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        BitmapParticleTexture bitmapParticleTexture = particleRule.particleTexture;
        BitmapParticleTexture bitmapParticleTexture2 = this.mParticleTexture;
        if (bitmapParticleTexture2 != null) {
            bitmapParticleTexture2.onRelease();
        }
        this.mParticleTexture = bitmapParticleTexture;
        if (bitmapParticleTexture != null) {
            Bitmap bitmap = bitmapParticleTexture.mBitmap;
            BitmapCache.BitmapLoader bitmapLoader = bitmapParticleTexture.mBitmapLoader;
            if (bitmap == null) {
                Context context = bitmapParticleTexture.mContext;
                LruCache lruCache = BitmapCache.sLruCache;
                if (bitmapLoader == null) {
                    throw new IllegalArgumentException("null loader");
                }
                int i2 = bitmapLoader.id;
                if (i2 < 0) {
                    int i3 = BitmapCache.uid;
                    BitmapCache.uid = i3 + 1;
                    bitmapLoader.id = i3;
                    loadToCache = BitmapCache.loadToCache(context, bitmapLoader);
                } else {
                    Bitmap bitmap2 = (Bitmap) BitmapCache.sLruCache.get(Integer.valueOf(i2));
                    loadToCache = bitmap2 == null ? BitmapCache.loadToCache(context, bitmapLoader) : bitmap2;
                }
                bitmapParticleTexture.mBitmap = loadToCache;
            }
            LruCache lruCache2 = BitmapCache.sLruCache;
            bitmapLoader.retainCount++;
        }
        this.mEnable = true;
        this.mEnableEmission = true;
        this.mIsInSight = false;
        this.mStartTime = j;
        LongRangeable longRangeable = particleRule.lifeTime;
        if (longRangeable.get() < 0) {
            this.mEndTime = Long.MAX_VALUE;
        } else {
            this.mEndTime = longRangeable.get() + j;
        }
        this.mLifeTime = this.mEndTime - this.mStartTime;
        status.factor.validate();
        if (this.mParentEmitter.mEmitters.isEmpty()) {
            return;
        }
        ArrayList arrayList = this.mParentEmitter.mEmitters;
        int size = arrayList.size();
        long j2 = this.mStartTime;
        for (int i4 = 0; i4 < size; i4++) {
            Emitter emitter = (Emitter) arrayList.get(i4);
            EmissionRule emissionRule = emitter.mEmissionRule;
            if (emitter.mWorld != null) {
                long j3 = this.mLifeTime;
                FloatRangeable floatRangeable = emissionRule.beginFraction;
                EmitterSchedule emitterSchedule = new EmitterSchedule(this, emitter, (floatRangeable != null ? (long) (floatRangeable.get() * j3) : emissionRule.beginTime.get()) + j2);
                if (this.mScheduleCheckLock) {
                    this.mTempEmitterSchedules.add(emitterSchedule);
                } else {
                    this.mEmitterSchedules.add(emitterSchedule);
                }
            }
        }
        this.mSubEmitterKey = this.mParentEmitter.subEmitterKey;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Particle{mEnable=");
        sb.append(this.mEnable);
        sb.append(", mEnableEmission=");
        sb.append(this.mEnableEmission);
        sb.append(", mIsInSight=");
        sb.append(this.mIsInSight);
        sb.append(", mStartTime=");
        sb.append(this.mStartTime);
        sb.append(", mEndTime=");
        sb.append(this.mEndTime);
        sb.append(", mLifeTime=");
        sb.append(this.mLifeTime);
        sb.append(", mFraction=");
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.mFraction, '}');
    }

    public Particle(Emitter emitter) {
        this.mParentEmitter = emitter;
    }
}
