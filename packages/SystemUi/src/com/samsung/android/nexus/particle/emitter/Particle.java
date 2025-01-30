package com.samsung.android.nexus.particle.emitter;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.view.animation.Interpolator;
import com.samsung.android.nexus.base.animator.AnimatorCore;
import com.samsung.android.nexus.base.context.NexusContext;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.base.utils.keyFrameSet.FloatKeyFrameSet;
import com.samsung.android.nexus.base.utils.range.FloatRangeable;
import com.samsung.android.nexus.base.utils.range.IntRangeable;
import com.samsung.android.nexus.base.utils.range.LongRangeable;
import com.samsung.android.nexus.base.utils.range.Rangeable;
import com.samsung.android.nexus.particle.emitter.FactorType;
import com.samsung.android.nexus.particle.emitter.texture.ParticleTexture;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Particle {
    public static final Particle$$ExternalSyntheticLambda0 mEmitterScheduleComparator = new Particle$$ExternalSyntheticLambda0();
    public static float sDensity;
    public static final Paint sPaint;
    public static final ParticleLinkedListPool sPool;
    public PorterDuffColorFilter mColorFilter;
    public float mFraction;
    public long mLifeTime;
    public Emitter mParentEmitter;
    public ParticleTexture mParticleTexture;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EmitterSchedule {
        public final Emitter emitter;
        public long nextTime;

        public EmitterSchedule(Particle particle, Emitter emitter, long j) {
            this.emitter = emitter;
            this.nextTime = j;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ParticleLinkedListPool extends ParticleLinkedList {
        public int createSize = 0;

        public final ParticleLinkedList retain(int i) {
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
                particleLinkedList.transferFrom(this, null, particle, particle2, min);
                i2 = i - min;
            } else {
                i2 = i;
            }
            if (i2 > 0) {
                int i5 = this.createSize;
                if (20000 <= i5 + i2) {
                    i2 = Math.min(20000 - i5, i);
                }
                if (i2 == 0) {
                    return particleLinkedList;
                }
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
                this.createSize += i2;
                particleLinkedList.put(particleLinkedList2);
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
        Log.m262i("Particle", "static initializer: took" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime) + "ms");
    }

    public Particle() {
    }

    /* JADX WARN: Removed duplicated region for block: B:152:0x0599  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0358  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0344  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0325  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x034b  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0351  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0389  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void checkEmitterSchedule(long j, Status status) {
        ArrayList arrayList;
        Particle particle;
        long j2;
        boolean z;
        int i;
        int i2;
        ArrayList arrayList2;
        ArrayList arrayList3;
        boolean z2;
        Emitter emitter;
        EmitterSchedule emitterSchedule;
        World world;
        boolean z3;
        World world2;
        boolean z4;
        EmissionRule emissionRule;
        boolean z5;
        Emitter emitter2;
        ParticleRule particleRule;
        long j3;
        EmissionRule emissionRule2;
        int i3;
        float f;
        float[] fArr;
        int i4;
        float[] fArr2;
        boolean[] zArr;
        ParticleRule particleRule2;
        float f2;
        int i5;
        int i6;
        FrameController frameController;
        int i7;
        float[] fArr3;
        float[] fArr4;
        ParticleRule particleRule3;
        float[] fArr5;
        int i8;
        float[] fArr6;
        boolean[] zArr2;
        float f3;
        int i9;
        int i10;
        float[] fArr7;
        float[] fArr8;
        int i11;
        float[] fArr9;
        float f4;
        float f5;
        float f6;
        float f7;
        PathMeasure pathMeasure;
        float[] fArr10;
        RectF rectF;
        float[] fArr11;
        float f8;
        float f9;
        float f10;
        Path path;
        EmissionRule emissionRule3;
        float f11;
        float f12;
        long j4 = j;
        Status status2 = status;
        if (this.mParentEmitter.mEmitters.size() == 0) {
            return;
        }
        ArrayList arrayList4 = this.mEmitterSchedules;
        if (!this.mEnable) {
            arrayList4.clear();
            return;
        }
        this.mScheduleCheckLock = true;
        int size = arrayList4.size();
        Particle particle2 = this;
        long j5 = j4;
        int i12 = 0;
        while (i12 < size) {
            EmitterSchedule emitterSchedule2 = (EmitterSchedule) arrayList4.get(i12);
            long j6 = emitterSchedule2.nextTime;
            if (j6 <= j5) {
                Emitter emitter3 = emitterSchedule2.emitter;
                EmissionRule emissionRule4 = emitter3.mEmissionRule;
                i = size;
                long j7 = particle2.mLifeTime;
                FloatRangeable floatRangeable = emissionRule4.intervalFraction;
                int i13 = i12;
                emitterSchedule2.nextTime += floatRangeable != null ? (long) (floatRangeable.get() * j7) : emissionRule4.intervalTime.get();
                boolean z6 = particle2.mEnableEmission;
                boolean z7 = emitter3.mEnable;
                if (z6) {
                    if (!(z7 && emitter3.mWorld != null) || (world = emitter3.mWorld) == null) {
                        arrayList3 = arrayList4;
                        z2 = z7;
                        emitter = emitter3;
                        emitterSchedule = emitterSchedule2;
                    } else {
                        EmissionRule emissionRule5 = emitter3.mEmissionRule;
                        boolean[] zArr3 = emissionRule5.configValues;
                        boolean z8 = zArr3[EmissionConfigType.APPLY_PARENT_ANGULAR_VELOCITY.idx];
                        ParticleRule particleRule4 = emitter3.mParticleRule;
                        arrayList3 = arrayList4;
                        boolean z9 = particleRule4.configValues[ParticleConfigType.AUTO_ROTATE_ALONG_MOVE_DIRECTION.idx];
                        boolean z10 = zArr3[EmissionConfigType.APPLY_PARENT_ROTATION_TO_SHAPE.idx];
                        z2 = z7;
                        boolean z11 = zArr3[EmissionConfigType.APPLY_PARENT_POS_VECTOR.idx];
                        float f13 = (!z10 || status2 == null) ? 0.0f : status2.rotation;
                        Path path2 = emissionRule5.emitterShapePath;
                        if (path2 == null || emissionRule5.emitterShapeBounds == null) {
                            z3 = z9;
                            world2 = world;
                            z4 = z11;
                            emissionRule = emissionRule5;
                            z5 = z8;
                            emitter2 = emitter3;
                            particleRule = particleRule4;
                            emitterSchedule = emitterSchedule2;
                        } else {
                            if (emissionRule5.mShapeScaleMode == 0) {
                                f4 = emissionRule5.shapeScale.get() * emissionRule5.shapeBaseScale;
                                f5 = f4;
                            } else {
                                f4 = emissionRule5.shapeScaleX.get() * emissionRule5.shapeBaseScale;
                                f5 = emissionRule5.shapeScaleY.get() * emissionRule5.shapeBaseScale;
                            }
                            if (f4 == 1.0f && f5 == 1.0f && f13 == 0.0f) {
                                emitterSchedule = emitterSchedule2;
                            } else {
                                Path path3 = emissionRule5.mRotatedEmitterShapePath;
                                path3.set(path2);
                                Matrix matrix = emissionRule5.mMatrix;
                                matrix.reset();
                                emitterSchedule = emitterSchedule2;
                                matrix.setScale(f4, f5, 0.0f, 0.0f);
                                matrix.postRotate(f13, 0.0f, 0.0f);
                                path3.transform(matrix);
                                path2 = path3;
                            }
                            PathMeasure pathMeasure2 = new PathMeasure(path2, false);
                            float length = pathMeasure2.getLength();
                            while (pathMeasure2.nextContour()) {
                                length += pathMeasure2.getLength();
                            }
                            pathMeasure2.setPath(path2, false);
                            float[] fArr12 = new float[2];
                            float[] fArr13 = new float[2];
                            IntRangeable intRangeable = emissionRule5.emitterCellCount;
                            int i14 = intRangeable.mIsSingleValue ? intRangeable.mMin : intRangeable.mMin + ((int) (intRangeable.mDelta * Rangeable.sRandom.get()));
                            if (i14 <= 0) {
                                z3 = z9;
                                world2 = world;
                                z4 = z11;
                                emissionRule = emissionRule5;
                                z5 = z8;
                                emitter2 = emitter3;
                                particleRule = particleRule4;
                            } else {
                                int i15 = emissionRule5.mDirectionMode;
                                if (emissionRule5.pointerSize < i14) {
                                    emissionRule5.pathTanXArray = new float[i14];
                                    emissionRule5.pathTanYArray = new float[i14];
                                    emissionRule5.pathPointerOffsetXArray = new float[i14];
                                    emissionRule5.pathPointerOffsetYArray = new float[i14];
                                    emissionRule5.pathPointerVelocitiesX = new float[i14];
                                    emissionRule5.pathPointerVelocitiesY = new float[i14];
                                    emissionRule5.pointerSize = i14;
                                }
                                float[] fArr14 = emissionRule5.pathTanXArray;
                                float[] fArr15 = emissionRule5.pathTanYArray;
                                world2 = world;
                                float[] fArr16 = emissionRule5.pathPointerOffsetXArray;
                                j3 = j6;
                                float[] fArr17 = emissionRule5.pathPointerOffsetYArray;
                                float[] fArr18 = emissionRule5.pathPointerVelocitiesX;
                                emitter2 = emitter3;
                                float[] fArr19 = emissionRule5.pathPointerVelocitiesY;
                                z3 = z9;
                                float f14 = i14;
                                float f15 = length / f14;
                                z5 = z8;
                                RectF rectF2 = new RectF();
                                z4 = z11;
                                Path path4 = new Path();
                                float length2 = pathMeasure2.getLength();
                                particleRule = particleRule4;
                                float length3 = pathMeasure2.getLength();
                                path4.reset();
                                pathMeasure2.getSegment(0.0f, length3, path4, true);
                                path4.computeBounds(rectF2, true);
                                float centerX = rectF2.centerX();
                                float centerY = rectF2.centerY();
                                float f16 = length2;
                                int i16 = 0;
                                float f17 = 0.0f;
                                float f18 = 0.0f;
                                float f19 = 0.0f;
                                float f20 = 0.0f;
                                while (i16 < i14) {
                                    int i17 = i14;
                                    float[] fArr20 = fArr18;
                                    float f21 = ((emissionRule5.emitterCellOffset.get() * f15) + ((i16 * length) / f14)) % length;
                                    if (f21 > f16) {
                                        pathMeasure2.nextContour();
                                        float length4 = pathMeasure2.getLength();
                                        path4.reset();
                                        f6 = length;
                                        f7 = f14;
                                        pathMeasure2.getSegment(0.0f, length4, path4, true);
                                        path4.computeBounds(rectF2, true);
                                        centerX = rectF2.centerX();
                                        centerY = rectF2.centerY();
                                        f17 = f16;
                                        f16 += length4;
                                    } else {
                                        f6 = length;
                                        f7 = f14;
                                    }
                                    pathMeasure2.getPosTan(f21 != f17 ? f21 - f17 : 0.0f, fArr12, fArr13);
                                    float f22 = fArr12[0];
                                    float f23 = fArr12[1];
                                    float f24 = emissionRule5.initialVelocity.get();
                                    if (i15 == 0) {
                                        pathMeasure = pathMeasure2;
                                        fArr10 = fArr12;
                                        rectF = rectF2;
                                        fArr11 = fArr17;
                                        f8 = f24;
                                        f9 = fArr13[0];
                                        f10 = fArr13[1];
                                        f20 = 270.0f;
                                    } else if (i15 == 1) {
                                        pathMeasure = pathMeasure2;
                                        fArr10 = fArr12;
                                        rectF = rectF2;
                                        fArr11 = fArr17;
                                        f8 = f24;
                                        float hypot = (float) Math.hypot(f22, f23);
                                        if (0.0f != hypot) {
                                            f9 = f22 / hypot;
                                            f12 = f23 / hypot;
                                            f10 = f12;
                                        }
                                        f9 = 0.0f;
                                        f10 = 0.0f;
                                    } else if (i15 == 2) {
                                        pathMeasure = pathMeasure2;
                                        fArr10 = fArr12;
                                        rectF = rectF2;
                                        fArr11 = fArr17;
                                        f8 = f24;
                                        f10 = 0.0f;
                                        f9 = 1.0f;
                                    } else if (i15 != 3) {
                                        pathMeasure = pathMeasure2;
                                        fArr11 = fArr17;
                                        f8 = f24;
                                        f9 = f19;
                                        rectF = rectF2;
                                        float f25 = f18;
                                        fArr10 = fArr12;
                                        f10 = f25;
                                    } else {
                                        float f26 = f22 - centerX;
                                        fArr10 = fArr12;
                                        rectF = rectF2;
                                        pathMeasure = pathMeasure2;
                                        float f27 = f23 - centerY;
                                        fArr11 = fArr17;
                                        f8 = f24;
                                        float hypot2 = (float) Math.hypot(f26, f27);
                                        if (0.0f != hypot2) {
                                            f9 = f26 / hypot2;
                                            f12 = f27 / hypot2;
                                            f10 = f12;
                                        }
                                        f9 = 0.0f;
                                        f10 = 0.0f;
                                    }
                                    double radians = Math.toRadians((((-emissionRule5.emissionAngle.get()) + 360.0f) + f20) % 360.0f);
                                    if (0.0d != radians) {
                                        double d = f9;
                                        double d2 = f10;
                                        path = path4;
                                        emissionRule3 = emissionRule5;
                                        float cos = (float) ((Math.cos(radians) * d) - (Math.sin(radians) * d2));
                                        f11 = (float) ((Math.cos(radians) * d2) + (Math.sin(radians) * d));
                                        f9 = cos;
                                    } else {
                                        path = path4;
                                        emissionRule3 = emissionRule5;
                                        f11 = f10;
                                    }
                                    fArr14[i16] = f9;
                                    fArr15[i16] = f11;
                                    fArr16[i16] = f22;
                                    fArr11[i16] = f23;
                                    fArr20[i16] = f8 * f9;
                                    fArr19[i16] = f8 * f11;
                                    i16++;
                                    fArr12 = fArr10;
                                    rectF2 = rectF;
                                    f14 = f7;
                                    fArr18 = fArr20;
                                    length = f6;
                                    fArr17 = fArr11;
                                    emissionRule5 = emissionRule3;
                                    path4 = path;
                                    f18 = f11;
                                    f19 = f9;
                                    i14 = i17;
                                    pathMeasure2 = pathMeasure;
                                }
                                int i18 = i14;
                                emissionRule2 = emissionRule5;
                                i3 = i18;
                                float[] fArr21 = emissionRule2.pathPointerOffsetXArray;
                                float[] fArr22 = emissionRule2.pathPointerOffsetYArray;
                                float[] fArr23 = emissionRule2.pathTanXArray;
                                float[] fArr24 = emissionRule2.pathTanYArray;
                                float[] fArr25 = emissionRule2.pathPointerVelocitiesX;
                                float[] fArr26 = emissionRule2.pathPointerVelocitiesY;
                                if (status == null) {
                                    fArr = status.factor.values;
                                    i4 = fArr.length;
                                    fArr2 = new float[fArr.length];
                                    int i19 = 0;
                                    while (true) {
                                        zArr = emissionRule2.applyParentFactorCheckList;
                                        if (i19 >= i4) {
                                            break;
                                        }
                                        if (zArr[i19]) {
                                            fArr2[i19] = fArr[i19];
                                        }
                                        i19++;
                                    }
                                    f = fArr[FactorType.ROTATION.speedIdx];
                                } else {
                                    f = 0.0f;
                                    fArr = null;
                                    i4 = 0;
                                    fArr2 = null;
                                    zArr = null;
                                }
                                float f28 = status == null ? status.vecTanX : 0.0f;
                                if (status == null) {
                                    f2 = status.vecTanY;
                                    particleRule2 = particleRule;
                                } else {
                                    particleRule2 = particleRule;
                                    f2 = 0.0f;
                                }
                                int i20 = particleRule2.posMode;
                                float f29 = f;
                                int i21 = particleRule2.scaleMode;
                                int i22 = particleRule2.colorMode;
                                FactorRangeableKeyFrameSetList factorRangeableKeyFrameSetList = particleRule2.factorKeyFrameList;
                                boolean z12 = !factorRangeableKeyFrameSetList.isEmpty();
                                ParticleLinkedList retain = sPool.retain(i3);
                                i5 = retain.size;
                                float f30 = f2;
                                Particle particle3 = retain.head;
                                i6 = 0;
                                while (i6 < i5) {
                                    int i23 = i5;
                                    Status status3 = particle3.status;
                                    Particle particle4 = particle3;
                                    Factor factor = status3.factor;
                                    float f31 = f28;
                                    FactorKeyFrameSetList factorKeyFrameSetList = status3.factorKeyFrameSetList;
                                    FactorRangeableList factorRangeableList = particleRule2.factorRangeableList;
                                    if (z12) {
                                        FactorType factorType = FactorType.WIDTH;
                                        particleRule3 = particleRule2;
                                        int i24 = FactorType.Holder.sCount;
                                        fArr5 = fArr;
                                        FloatKeyFrameSet[] floatKeyFrameSetArr = factorKeyFrameSetList.list;
                                        zArr2 = zArr;
                                        factorKeyFrameSetList.floatKeyFrameSetSize = 0;
                                        int i25 = 0;
                                        while (i25 < i24) {
                                            int i26 = i24;
                                            FloatRangeable[] floatRangeableArr = factorRangeableKeyFrameSetList.mFactorRangeablePositions[i25];
                                            if (floatRangeableArr != null) {
                                                i11 = i4;
                                                int length5 = floatRangeableArr.length;
                                                fArr9 = fArr2;
                                                FloatRangeable[] floatRangeableArr2 = factorRangeableKeyFrameSetList.mFactorRangeableValues[i25];
                                                fArr8 = fArr24;
                                                float[] fArr27 = new float[length5];
                                                i10 = i6;
                                                float[] fArr28 = new float[length5];
                                                fArr7 = fArr23;
                                                for (int i27 = 0; i27 < length5; i27++) {
                                                    fArr27[i27] = floatRangeableArr[i27].get();
                                                    fArr28[i27] = floatRangeableArr2[i27].get();
                                                }
                                                Interpolator interpolator = factorRangeableKeyFrameSetList.mFactorInterpolator[i25];
                                                Interpolator[] interpolatorArr = factorRangeableKeyFrameSetList.mFactorInterpolators[i25];
                                                FloatKeyFrameSet floatKeyFrameSet = floatKeyFrameSetArr[i25];
                                                if (floatKeyFrameSet == null) {
                                                    floatKeyFrameSet = new FloatKeyFrameSet();
                                                    floatKeyFrameSetArr[i25] = floatKeyFrameSet;
                                                }
                                                floatKeyFrameSet.length = length5;
                                                floatKeyFrameSet.fractionPositions = fArr27;
                                                floatKeyFrameSet.interpolators = interpolatorArr;
                                                floatKeyFrameSet.interpolator = interpolator;
                                                int i28 = length5 - 1;
                                                floatKeyFrameSet.mValues = fArr28;
                                                float[] fArr29 = floatKeyFrameSet.mDeltas;
                                                if (fArr29 == null || i28 != fArr29.length) {
                                                    floatKeyFrameSet.mDeltas = new float[i28];
                                                }
                                                float f32 = fArr28[0];
                                                int i29 = 0;
                                                while (i29 < i28) {
                                                    int i30 = i29 + 1;
                                                    float f33 = fArr28[i30];
                                                    floatKeyFrameSet.mDeltas[i29] = f33 - f32;
                                                    i29 = i30;
                                                    f32 = f33;
                                                    fArr28 = fArr28;
                                                }
                                                factorKeyFrameSetList.floatKeyFrameSetSize++;
                                            } else {
                                                i10 = i6;
                                                fArr7 = fArr23;
                                                fArr8 = fArr24;
                                                i11 = i4;
                                                fArr9 = fArr2;
                                                floatKeyFrameSetArr[i25] = factorRangeableKeyFrameSetList.list[i25];
                                            }
                                            i25++;
                                            i24 = i26;
                                            i4 = i11;
                                            fArr2 = fArr9;
                                            fArr24 = fArr8;
                                            i6 = i10;
                                            fArr23 = fArr7;
                                        }
                                        i7 = i6;
                                        fArr3 = fArr23;
                                        fArr4 = fArr24;
                                        i8 = i4;
                                        fArr6 = fArr2;
                                        factor.getClass();
                                        factor.initValues(factorRangeableList.rangeables, factorKeyFrameSetList.list);
                                    } else {
                                        i7 = i6;
                                        fArr3 = fArr23;
                                        fArr4 = fArr24;
                                        particleRule3 = particleRule2;
                                        fArr5 = fArr;
                                        i8 = i4;
                                        fArr6 = fArr2;
                                        zArr2 = zArr;
                                        factorKeyFrameSetList.clear();
                                        factor.getClass();
                                        factor.initValues(factorRangeableList.rangeables, null);
                                    }
                                    status3.colorMode = i22;
                                    status3.scaleMode = i21;
                                    status3.posMode = i20;
                                    status3.vecTanX = fArr3[i7];
                                    status3.vecTanY = fArr4[i7];
                                    float[] fArr30 = factor.values;
                                    i4 = i8;
                                    if (fArr6 != null) {
                                        for (int i31 = 0; i31 < i4; i31++) {
                                            if (zArr2[i31]) {
                                                fArr30[i31] = fArr5[i31];
                                            }
                                        }
                                    }
                                    if (status == null || !z4) {
                                        f3 = f30;
                                        f28 = f31;
                                    } else {
                                        f28 = f31;
                                        status3.vecTanX = f28;
                                        f3 = f30;
                                        status3.vecTanY = f3;
                                    }
                                    status3.onStep(0.0f, 0.0f, null);
                                    float f34 = fArr21[i7];
                                    float f35 = fArr22[i7];
                                    float f36 = fArr25[i7];
                                    float f37 = fArr26[i7];
                                    if (!z5 || f29 == 0.0f) {
                                        f30 = f3;
                                        i9 = i21;
                                    } else {
                                        f30 = f3;
                                        i9 = i21;
                                        float abs = (Math.abs(f29) / 50.0f) * ((float) Math.hypot(f34, f35));
                                        f36 += fArr3[i7] * abs;
                                        f37 += abs * fArr4[i7];
                                    }
                                    status3.posX += f34;
                                    status3.posY += f35;
                                    FactorType factorType2 = FactorType.POS_X;
                                    int i32 = factorType2.valueIdx;
                                    float[] fArr31 = factor.values;
                                    fArr31[i32] = fArr31[i32] + f34;
                                    if (f34 != 0.0f) {
                                        factor.mNeedValidate = true;
                                    }
                                    FactorType factorType3 = FactorType.POS_Y;
                                    int i33 = factorType3.valueIdx;
                                    fArr31[i33] = fArr31[i33] + f35;
                                    if (f35 != 0.0f) {
                                        factor.mNeedValidate = true;
                                    }
                                    int i34 = factorType2.speedIdx;
                                    fArr31[i34] = fArr31[i34] + f36;
                                    if (f36 != 0.0f) {
                                        factor.mNeedValidate = true;
                                    }
                                    int i35 = factorType3.speedIdx;
                                    float f38 = fArr31[i35] + f37;
                                    fArr31[i35] = f38;
                                    if (f37 != 0.0f) {
                                        factor.mNeedValidate = true;
                                    }
                                    float f39 = fArr31[i34];
                                    status3.speed = (f39 == 0.0f && f38 == 0.0f) ? 0.0f : (float) Math.hypot(f39, f38);
                                    status3.acc = 0.0f;
                                    if (z3) {
                                        status3.rotation = ((status3.rotation + ((float) Math.toDegrees(Math.atan2(fArr4[i7], fArr3[i7]) + 1.5707963267948966d))) + 360.0f) % 360.0f;
                                    }
                                    Emitter emitter4 = emitter2;
                                    particle4.mParentEmitter = emitter4;
                                    particle4.mStartTime = 0L;
                                    particle4.mEndTime = 0L;
                                    long j8 = j3;
                                    particle4.start(j8);
                                    particle3 = particle4.next;
                                    emitter2 = emitter4;
                                    j3 = j8;
                                    i21 = i9;
                                    i6 = i7 + 1;
                                    i5 = i23;
                                    particleRule2 = particleRule3;
                                    fArr = fArr5;
                                    zArr = zArr2;
                                    fArr2 = fArr6;
                                    fArr24 = fArr4;
                                    fArr23 = fArr3;
                                }
                                World world3 = world2;
                                emitter = emitter2;
                                frameController = world3.mFrameController;
                                if (frameController.mIsStarted) {
                                    frameController.mIsStarted = false;
                                    Log.m262i("FrameController", "Stop frame control.");
                                    frameController.mFrameRateControlHandler.removeMessages(0);
                                    NexusContext nexusContext = frameController.mContainer.getNexusContext();
                                    int i36 = frameController.mMaxFrameRate;
                                    if (i36 <= 0) {
                                        nexusContext.getClass();
                                        Log.m261e("NexusContext", "setFrameRate() : Do NOT set a negative value.");
                                    }
                                    AnimatorCore animatorCore = nexusContext.mAnimatorCore;
                                    animatorCore.mFrameRate = i36;
                                    animatorCore.mFrameTime = 1000000000 / (i36 + 1);
                                }
                                world3.mWorldParticleLinkedList.put(retain);
                            }
                        }
                        j3 = j6;
                        emissionRule2 = emissionRule;
                        i3 = 0;
                        float[] fArr212 = emissionRule2.pathPointerOffsetXArray;
                        float[] fArr222 = emissionRule2.pathPointerOffsetYArray;
                        float[] fArr232 = emissionRule2.pathTanXArray;
                        float[] fArr242 = emissionRule2.pathTanYArray;
                        float[] fArr252 = emissionRule2.pathPointerVelocitiesX;
                        float[] fArr262 = emissionRule2.pathPointerVelocitiesY;
                        if (status == null) {
                        }
                        if (status == null) {
                        }
                        if (status == null) {
                        }
                        int i202 = particleRule2.posMode;
                        float f292 = f;
                        int i212 = particleRule2.scaleMode;
                        int i222 = particleRule2.colorMode;
                        FactorRangeableKeyFrameSetList factorRangeableKeyFrameSetList2 = particleRule2.factorKeyFrameList;
                        boolean z122 = !factorRangeableKeyFrameSetList2.isEmpty();
                        ParticleLinkedList retain2 = sPool.retain(i3);
                        i5 = retain2.size;
                        float f302 = f2;
                        Particle particle32 = retain2.head;
                        i6 = 0;
                        while (i6 < i5) {
                        }
                        World world32 = world2;
                        emitter = emitter2;
                        frameController = world32.mFrameController;
                        if (frameController.mIsStarted) {
                        }
                        world32.mWorldParticleLinkedList.put(retain2);
                    }
                    emitterSchedule2 = emitterSchedule;
                } else {
                    arrayList3 = arrayList4;
                    z2 = z7;
                    emitter = emitter3;
                }
                j2 = j;
                if (emitterSchedule2.nextTime <= j2) {
                    emitterSchedule2.nextTime = j2;
                }
                particle = this;
                if (emitterSchedule2.nextTime <= particle.mEndTime) {
                    if (z2 && emitter.mWorld != null) {
                        particle2 = particle;
                        j5 = j2;
                        i2 = i13;
                        arrayList2 = arrayList3;
                    }
                }
                arrayList = arrayList3;
                arrayList.remove(i13);
                break;
            }
            i = size;
            i2 = i12;
            j2 = j4;
            arrayList2 = arrayList4;
            i12 = i2 + 1;
            status2 = status;
            arrayList4 = arrayList2;
            j4 = j2;
            size = i;
        }
        arrayList = arrayList4;
        particle = particle2;
        j2 = j5;
        Emitter emitter5 = particle.mParentEmitter;
        String str = particle.mSubEmitterKey;
        emitter5.getClass();
        boolean z13 = str != null && (str.hashCode() == emitter5.subEmitterKey.hashCode() || emitter5.subEmitterKey.equals(str));
        ArrayList arrayList5 = particle.mTempEmitterSchedules;
        if (!z13) {
            ArrayList arrayList6 = particle.mParentEmitter.mEmitters;
            int size2 = arrayList6.size();
            for (int i37 = 0; i37 < size2; i37++) {
                Emitter emitter6 = (Emitter) arrayList6.get(i37);
                EmissionRule emissionRule6 = emitter6.mEmissionRule;
                if (emitter6.mEnable && emitter6.mWorld != null) {
                    int size3 = arrayList.size();
                    int i38 = 0;
                    while (true) {
                        if (i38 >= size3) {
                            z = false;
                            break;
                        } else {
                            if (emitter6 == ((EmitterSchedule) arrayList.get(i38)).emitter) {
                                z = true;
                                break;
                            }
                            i38++;
                        }
                    }
                    if (!z) {
                        long j9 = particle.mLifeTime;
                        FloatRangeable floatRangeable2 = emissionRule6.beginFraction;
                        EmitterSchedule emitterSchedule3 = new EmitterSchedule(particle, emitter6, (floatRangeable2 != null ? (long) (floatRangeable2.get() * j9) : emissionRule6.beginTime.get()) + j2);
                        if (particle.mScheduleCheckLock) {
                            arrayList5.add(emitterSchedule3);
                        } else {
                            particle.mEmitterSchedules.add(emitterSchedule3);
                        }
                    }
                }
            }
            particle.mSubEmitterKey = particle.mParentEmitter.subEmitterKey;
        }
        particle.mScheduleCheckLock = false;
        if (!arrayList5.isEmpty()) {
            arrayList.addAll(arrayList5);
            arrayList5.clear();
        }
        if (arrayList.size() > 1) {
            arrayList.sort(mEmitterScheduleComparator);
        }
    }

    public final void start(long j) {
        ParticleRule particleRule = this.mParentEmitter.mParticleRule;
        if (particleRule == null) {
            throw new IllegalStateException("can not start with null rule");
        }
        Status status = this.status;
        Integer valueOf = Integer.valueOf(status.color);
        if (valueOf == null) {
            status.color = 0;
            this.mColorFilter = null;
        } else {
            status.color = valueOf.intValue();
            this.mColorFilter = new PorterDuffColorFilter(valueOf.intValue(), PorterDuff.Mode.SRC_ATOP);
        }
        ParticleTexture particleTexture = particleRule.particleTexture;
        this.mParticleTexture = particleTexture;
        if (particleTexture != null) {
            particleTexture.onCreate();
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
        for (int i = 0; i < size; i++) {
            Emitter emitter = (Emitter) arrayList.get(i);
            EmissionRule emissionRule = emitter.mEmissionRule;
            if (emitter.mEnable && emitter.mWorld != null) {
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
        return "Particle{mEnable=" + this.mEnable + ", mEnableEmission=" + this.mEnableEmission + ", mIsInSight=" + this.mIsInSight + ", mStartTime=" + this.mStartTime + ", mEndTime=" + this.mEndTime + ", mLifeTime=" + this.mLifeTime + ", mFraction=" + this.mFraction + '}';
    }

    public Particle(Emitter emitter) {
        this.mParentEmitter = emitter;
    }
}
