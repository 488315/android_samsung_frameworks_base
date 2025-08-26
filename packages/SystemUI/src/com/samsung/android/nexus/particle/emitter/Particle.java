package com.samsung.android.nexus.particle.emitter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.util.LruCache;
import android.view.animation.Interpolator;
import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.samsung.android.nexus.base.animator.AnimatorCore;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.base.utils.keyFrameSet.FloatKeyFrameSet;
import com.samsung.android.nexus.base.utils.range.FloatRangeable;
import com.samsung.android.nexus.base.utils.range.IntRangeable;
import com.samsung.android.nexus.base.utils.range.LongRangeable;
import com.samsung.android.nexus.base.utils.range.Rangeable;
import com.samsung.android.nexus.particle.emitter.FactorType;
import com.samsung.android.nexus.particle.emitter.texture.BitmapCache;
import com.samsung.android.nexus.particle.emitter.texture.BitmapParticleTexture;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

    public class EmitterSchedule {
        public final Emitter emitter;
        public long nextTime;

        public EmitterSchedule(Particle particle, Emitter emitter, long j) {
            this.emitter = emitter;
            this.nextTime = j;
        }
    }

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

    public class ParticleLinkedListPool extends ParticleLinkedList {
        public int createSize = 0;

        public final ParticleLinkedList retain(int i) {
            ParticleLinkedListPool particleLinkedListPool;
            int iMin;
            ParticleLinkedList particleLinkedList = new ParticleLinkedList();
            int iMin2 = Math.min(this.size, i);
            int i2 = 1;
            if (iMin2 > 0) {
                Particle particle = this.head;
                Particle particle2 = particle;
                for (int i3 = 1; i3 < iMin2; i3++) {
                    particle2 = particle2.next;
                }
                particleLinkedListPool = this;
                particleLinkedList.transferFrom(particleLinkedListPool, null, particle, particle2, iMin2);
                iMin = i - iMin2;
            } else {
                particleLinkedListPool = this;
                iMin = i;
            }
            if (iMin > 0) {
                int i4 = particleLinkedListPool.createSize;
                if (20000 <= i4 + iMin) {
                    iMin = Math.min(PluginLockInstancePolicy.DISABLED_BY_MODE - i4, i);
                }
                if (iMin != 0) {
                    ParticleLinkedList particleLinkedList2 = new ParticleLinkedList();
                    Particle particle3 = new Particle();
                    Particle particle4 = particle3;
                    while (i2 < iMin) {
                        Particle particle5 = new Particle();
                        particle4.next = particle5;
                        i2++;
                        particle4 = particle5;
                    }
                    particleLinkedList2.head = particle3;
                    particleLinkedList2.tail = particle4;
                    particleLinkedList2.size = iMin;
                    particleLinkedListPool.createSize += iMin;
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
        long jNanoTime = System.nanoTime();
        particleLinkedListPool.put(particleLinkedListPool.retain(3000));
        Log.i("Particle", "static initializer: took" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - jNanoTime) + "ms");
    }

    public Particle() {
    }

    /* JADX WARN: Removed duplicated region for block: B:166:0x04d5  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void checkEmitterSchedule(long j, Status status) {
        long j2;
        Particle particle;
        ArrayList arrayList;
        ArrayList arrayList2;
        int i;
        int i2;
        ArrayList arrayList3;
        Emitter emitter;
        int i3;
        EmitterSchedule emitterSchedule;
        float f;
        float f2;
        boolean z;
        int i4;
        float[] fArr;
        float[] fArr2;
        float[] fArr3;
        boolean[] zArr;
        float f3;
        int length;
        float[] fArr4;
        int i5;
        int i6;
        FrameController frameController;
        ParticleRule particleRule;
        World world;
        Particle particle2;
        long j3;
        Emitter emitter2;
        float f4;
        float f5;
        float f6;
        boolean z2;
        int i7;
        Particle particle3;
        long j4;
        Emitter emitter3;
        Path path;
        int i8;
        float f7;
        float f8;
        Particle particle4 = this;
        Status status2 = status;
        if (particle4.mParentEmitter.mEmitters.size() == 0) {
            return;
        }
        ArrayList arrayList4 = particle4.mEmitterSchedules;
        if (!particle4.mEnable) {
            arrayList4.clear();
            return;
        }
        particle4.mScheduleCheckLock = true;
        int size = arrayList4.size();
        int i9 = 0;
        while (i9 < size) {
            EmitterSchedule emitterSchedule2 = (EmitterSchedule) arrayList4.get(i9);
            long j5 = emitterSchedule2.nextTime;
            if (j5 <= j) {
                Emitter emitter4 = emitterSchedule2.emitter;
                EmissionRule emissionRule = emitter4.mEmissionRule;
                long j6 = particle4.mLifeTime;
                FloatRangeable floatRangeable = emissionRule.intervalFraction;
                int i10 = i9;
                emitterSchedule2.nextTime += floatRangeable != null ? (long) (floatRangeable.get() * j6) : emissionRule.intervalTime.get();
                if (particle4.mEnableEmission) {
                    World world2 = emitter4.mWorld;
                    if (!(world2 != null) || world2 == null) {
                        arrayList3 = arrayList4;
                        i = size;
                        emitterSchedule = emitterSchedule2;
                        emitter = emitter4;
                        i3 = i10;
                    } else {
                        EmissionRule emissionRule2 = emitter4.mEmissionRule;
                        boolean[] zArr2 = emissionRule2.configValues;
                        boolean z3 = zArr2[EmissionConfigType.APPLY_PARENT_ANGULAR_VELOCITY.idx];
                        ParticleRule particleRule2 = emitter4.mParticleRule;
                        i = size;
                        boolean z4 = particleRule2.configValues[ParticleConfigType.AUTO_ROTATE_ALONG_MOVE_DIRECTION.idx];
                        boolean z5 = zArr2[EmissionConfigType.APPLY_PARENT_ROTATION_TO_SHAPE.idx];
                        boolean z6 = zArr2[EmissionConfigType.APPLY_PARENT_POS_VECTOR.idx];
                        if (!z5 || status2 == null) {
                            f = 0.0f;
                            f2 = 0.0f;
                        } else {
                            f = 0.0f;
                            f2 = status2.rotation;
                        }
                        if (emissionRule2.emitterShapePath == null || emissionRule2.emitterShapeBounds == null) {
                            arrayList3 = arrayList4;
                            emitterSchedule = emitterSchedule2;
                            z = z3;
                            i3 = i10;
                        } else {
                            float f9 = emissionRule2.shapeBaseScale * emissionRule2.shapeScale.get();
                            if (f9 == 1.0f && f9 == 1.0f && f2 == f) {
                                path = emissionRule2.emitterShapePath;
                                arrayList3 = arrayList4;
                                z = z3;
                                i3 = i10;
                            } else {
                                z = z3;
                                i3 = i10;
                                emissionRule2.mRotatedEmitterShapePath.set(emissionRule2.emitterShapePath);
                                Path path2 = emissionRule2.mRotatedEmitterShapePath;
                                Matrix matrix = emissionRule2.mMatrix;
                                matrix.reset();
                                arrayList3 = arrayList4;
                                float f10 = f;
                                matrix.setScale(f9, f9, f10, f10);
                                matrix.postRotate(f2, f10, f10);
                                path2.transform(matrix);
                                path = path2;
                            }
                            PathMeasure pathMeasure = new PathMeasure(path, false);
                            float length2 = pathMeasure.getLength();
                            while (pathMeasure.nextContour()) {
                                length2 += pathMeasure.getLength();
                            }
                            pathMeasure.setPath(path, false);
                            float[] fArr5 = new float[2];
                            float[] fArr6 = new float[2];
                            IntRangeable intRangeable = emissionRule2.emitterCellCount;
                            float f11 = length2;
                            i4 = intRangeable.mIsSingleValue ? intRangeable.mMin : intRangeable.mMin + ((int) (intRangeable.mDelta * Rangeable.sRandom.get()));
                            if (i4 <= 0) {
                                emitterSchedule = emitterSchedule2;
                            } else {
                                if (emissionRule2.pointerSize < i4) {
                                    emissionRule2.pathTanXArray = new float[i4];
                                    emissionRule2.pathTanYArray = new float[i4];
                                    emissionRule2.pathPointerOffsetXArray = new float[i4];
                                    emissionRule2.pathPointerOffsetYArray = new float[i4];
                                    emissionRule2.pathPointerVelocitiesX = new float[i4];
                                    emissionRule2.pathPointerVelocitiesY = new float[i4];
                                    emissionRule2.pointerSize = i4;
                                }
                                float[] fArr7 = emissionRule2.pathTanXArray;
                                float[] fArr8 = emissionRule2.pathTanYArray;
                                float[] fArr9 = emissionRule2.pathPointerOffsetXArray;
                                float[] fArr10 = emissionRule2.pathPointerOffsetYArray;
                                float[] fArr11 = emissionRule2.pathPointerVelocitiesX;
                                float[] fArr12 = emissionRule2.pathPointerVelocitiesY;
                                float f12 = i4;
                                float f13 = f11 / f12;
                                RectF rectF = new RectF();
                                Path path3 = new Path();
                                float length3 = pathMeasure.getLength();
                                float length4 = pathMeasure.getLength();
                                path3.reset();
                                emitterSchedule = emitterSchedule2;
                                pathMeasure.getSegment(0.0f, length4, path3, true);
                                path3.computeBounds(rectF, true);
                                rectF.centerX();
                                rectF.centerY();
                                int i11 = 0;
                                float f14 = 0.0f;
                                while (i11 < i4) {
                                    float f15 = f14;
                                    float f16 = ((emissionRule2.emitterCellOffset.get() * f13) + ((i11 * f11) / f12)) % f11;
                                    if (f16 > length3) {
                                        pathMeasure.nextContour();
                                        float length5 = pathMeasure.getLength();
                                        path3.reset();
                                        i8 = i11;
                                        f7 = f16;
                                        pathMeasure.getSegment(0.0f, length5, path3, true);
                                        path3.computeBounds(rectF, true);
                                        rectF.centerX();
                                        rectF.centerY();
                                        float f17 = length3;
                                        length3 += length5;
                                        f8 = f17;
                                    } else {
                                        i8 = i11;
                                        f7 = f16;
                                        f8 = f15;
                                    }
                                    pathMeasure.getPosTan(f7 != f8 ? f7 - f8 : 0.0f, fArr5, fArr6);
                                    float f18 = fArr5[0];
                                    float f19 = fArr5[1];
                                    Path path4 = path3;
                                    float f20 = emissionRule2.initialVelocity.get();
                                    float fCos = fArr6[0];
                                    float fCos2 = fArr6[1];
                                    float f21 = f8;
                                    PathMeasure pathMeasure2 = pathMeasure;
                                    float[] fArr13 = fArr6;
                                    double radians = Math.toRadians((((-emissionRule2.emissionAngle.get()) + 360.0f) + 270.0f) % 360.0f);
                                    if (0.0d != radians) {
                                        double d = fCos;
                                        double d2 = fCos2;
                                        fCos = (float) ((Math.cos(radians) * d) - (Math.sin(radians) * d2));
                                        fCos2 = (float) ((Math.cos(radians) * d2) + (Math.sin(radians) * d));
                                    }
                                    fArr7[i8] = fCos;
                                    fArr8[i8] = fCos2;
                                    fArr9[i8] = f18;
                                    fArr10[i8] = f19;
                                    fArr11[i8] = fCos * f20;
                                    fArr12[i8] = f20 * fCos2;
                                    i11 = i8 + 1;
                                    path3 = path4;
                                    f14 = f21;
                                    pathMeasure = pathMeasure2;
                                    fArr6 = fArr13;
                                }
                                float[] fArr14 = emissionRule2.pathPointerOffsetXArray;
                                float[] fArr15 = emissionRule2.pathPointerOffsetYArray;
                                float[] fArr16 = emissionRule2.pathTanXArray;
                                float[] fArr17 = emissionRule2.pathTanYArray;
                                float[] fArr18 = emissionRule2.pathPointerVelocitiesX;
                                float[] fArr19 = emissionRule2.pathPointerVelocitiesY;
                                if (status2 == null) {
                                    fArr4 = status2.factor.values;
                                    length = fArr4.length;
                                    fArr = fArr14;
                                    float[] fArr20 = new float[fArr4.length];
                                    fArr2 = fArr15;
                                    for (int i12 : emissionRule2.applyParentFactorCheckIndexList) {
                                        if (i12 < 0) {
                                            break;
                                        }
                                        fArr20[i12] = fArr4[i12];
                                    }
                                    float f22 = fArr4[FactorType.ROTATION.speedIdx];
                                    zArr = emissionRule2.applyParentFactorCheckList;
                                    f3 = f22;
                                    fArr3 = fArr20;
                                } else {
                                    fArr = fArr14;
                                    fArr2 = fArr15;
                                    fArr3 = null;
                                    zArr = null;
                                    f3 = 0.0f;
                                    length = 0;
                                    fArr4 = null;
                                }
                                float[] fArr21 = fArr3;
                                float f23 = status2 == null ? status2.vecTanX : 0.0f;
                                boolean[] zArr3 = zArr;
                                float f24 = status2 == null ? status2.vecTanY : 0.0f;
                                int i13 = particleRule2.posMode;
                                int i14 = particleRule2.colorMode;
                                FactorRangeableKeyFrameSetList factorRangeableKeyFrameSetList = particleRule2.factorKeyFrameList;
                                boolean zIsEmpty = factorRangeableKeyFrameSetList.isEmpty();
                                ParticleLinkedList particleLinkedListRetain = sPool.retain(i4);
                                i5 = particleLinkedListRetain.size;
                                float f25 = f3;
                                Particle particle5 = particleLinkedListRetain.head;
                                i6 = 0;
                                while (i6 < i5) {
                                    int i15 = i6;
                                    Status status3 = particle5.status;
                                    int i16 = i5;
                                    Factor factor = status3.factor;
                                    float[] fArr22 = fArr4;
                                    FactorKeyFrameSetList factorKeyFrameSetList = status3.factorKeyFrameSetList;
                                    ParticleLinkedList particleLinkedList = particleLinkedListRetain;
                                    FactorRangeableList factorRangeableList = particleRule2.factorRangeableList;
                                    if (zIsEmpty) {
                                        particleRule = particleRule2;
                                        world = world2;
                                        particle2 = particle5;
                                        j3 = j5;
                                        emitter2 = emitter4;
                                        factorKeyFrameSetList.clear();
                                        factor.getClass();
                                        factor.initValues(factorRangeableList.rangeables, null);
                                    } else {
                                        FactorType factorType = FactorType.WIDTH;
                                        particleRule = particleRule2;
                                        int i17 = FactorType.Holder.sCount;
                                        world = world2;
                                        Interpolator[] interpolatorArr = factorRangeableKeyFrameSetList.mFactorInterpolator;
                                        Interpolator[][] interpolatorArr2 = factorRangeableKeyFrameSetList.mFactorInterpolators;
                                        FloatKeyFrameSet[] floatKeyFrameSetArr = factorKeyFrameSetList.list;
                                        factorKeyFrameSetList.floatKeyFrameSetSize = 0;
                                        int i18 = 0;
                                        while (i18 < i17) {
                                            int i19 = i17;
                                            FloatRangeable[] floatRangeableArr = factorRangeableKeyFrameSetList.mFactorRangeablePositions[i18];
                                            if (floatRangeableArr != null) {
                                                i7 = i18;
                                                int length6 = floatRangeableArr.length;
                                                FloatRangeable[] floatRangeableArr2 = factorRangeableKeyFrameSetList.mFactorRangeableValues[i7];
                                                float[] fArr23 = new float[length6];
                                                j4 = j5;
                                                float[] fArr24 = new float[length6];
                                                for (int i20 = 0; i20 < length6; i20++) {
                                                    fArr23[i20] = floatRangeableArr[i20].get();
                                                    fArr24[i20] = floatRangeableArr2[i20].get();
                                                }
                                                Interpolator interpolator = interpolatorArr[i7];
                                                particle3 = particle5;
                                                Interpolator[] interpolatorArr3 = interpolatorArr2[i7];
                                                FloatKeyFrameSet floatKeyFrameSet = floatKeyFrameSetArr[i7];
                                                if (floatKeyFrameSet == null) {
                                                    floatKeyFrameSet = new FloatKeyFrameSet();
                                                    floatKeyFrameSetArr[i7] = floatKeyFrameSet;
                                                }
                                                emitter3 = emitter4;
                                                FloatKeyFrameSet floatKeyFrameSet2 = floatKeyFrameSet;
                                                floatKeyFrameSet2.length = length6;
                                                floatKeyFrameSet2.fractionPositions = fArr23;
                                                floatKeyFrameSet2.interpolators = interpolatorArr3;
                                                floatKeyFrameSet2.interpolator = interpolator;
                                                int i21 = length6 - 1;
                                                floatKeyFrameSet2.mValues = fArr24;
                                                float[] fArr25 = floatKeyFrameSet2.mDeltas;
                                                if (fArr25 == null || i21 != fArr25.length) {
                                                    floatKeyFrameSet2.mDeltas = new float[i21];
                                                }
                                                float f26 = fArr24[0];
                                                int i22 = 0;
                                                while (i22 < i21) {
                                                    int i23 = i22 + 1;
                                                    float f27 = fArr24[i23];
                                                    floatKeyFrameSet2.mDeltas[i22] = f27 - f26;
                                                    i22 = i23;
                                                    f26 = f27;
                                                }
                                                factorKeyFrameSetList.floatKeyFrameSetSize++;
                                            } else {
                                                i7 = i18;
                                                particle3 = particle5;
                                                j4 = j5;
                                                emitter3 = emitter4;
                                                floatKeyFrameSetArr[i7] = factorRangeableKeyFrameSetList.list[i7];
                                            }
                                            i18 = i7 + 1;
                                            i17 = i19;
                                            particle5 = particle3;
                                            j5 = j4;
                                            emitter4 = emitter3;
                                        }
                                        particle2 = particle5;
                                        j3 = j5;
                                        emitter2 = emitter4;
                                        factor.getClass();
                                        factor.initValues(factorRangeableList.rangeables, factorKeyFrameSetList.list);
                                    }
                                    status3.colorMode = i14;
                                    status3.posMode = i13;
                                    status3.vecTanX = fArr16[i15];
                                    status3.vecTanY = fArr17[i15];
                                    float[] fArr26 = factor.values;
                                    if (fArr21 != null) {
                                        for (int i24 = 0; i24 < length; i24++) {
                                            if (zArr3[i24]) {
                                                fArr26[i24] = fArr22[i24];
                                            }
                                        }
                                    }
                                    if (status != null && z6) {
                                        status3.vecTanX = f23;
                                        status3.vecTanY = f24;
                                    }
                                    status3.onStep(0.0f, 0.0f, null);
                                    float f28 = fArr[i15];
                                    float f29 = fArr2[i15];
                                    float f30 = fArr18[i15];
                                    float f31 = fArr19[i15];
                                    if (!z || f25 == 0.0f) {
                                        f4 = f23;
                                        f5 = f24;
                                        f6 = f28;
                                    } else {
                                        f4 = f23;
                                        f5 = f24;
                                        f6 = f28;
                                        float fAbs = (Math.abs(f25) / 50.0f) * ((float) Math.hypot(f28, f29));
                                        f30 += fArr16[i15] * fAbs;
                                        f31 += fAbs * fArr17[i15];
                                    }
                                    status3.posX += f6;
                                    status3.posY += f29;
                                    FactorType factorType2 = FactorType.POS_X;
                                    int i25 = factorType2.valueIdx;
                                    float[] fArr27 = factor.values;
                                    fArr27[i25] = fArr27[i25] + f6;
                                    if (f6 != 0.0f) {
                                        z2 = true;
                                        factor.mNeedValidate = true;
                                    } else {
                                        z2 = true;
                                    }
                                    FactorType factorType3 = FactorType.POS_Y;
                                    int i26 = factorType3.valueIdx;
                                    fArr27[i26] = fArr27[i26] + f29;
                                    if (f29 != 0.0f) {
                                        factor.mNeedValidate = z2;
                                    }
                                    int i27 = factorType2.speedIdx;
                                    fArr27[i27] = fArr27[i27] + f30;
                                    if (f30 != 0.0f) {
                                        factor.mNeedValidate = z2;
                                    }
                                    int i28 = factorType3.speedIdx;
                                    float f32 = fArr27[i28] + f31;
                                    fArr27[i28] = f32;
                                    if (f31 != 0.0f) {
                                        factor.mNeedValidate = z2;
                                    }
                                    float f33 = fArr27[i27];
                                    status3.speed = (f33 == 0.0f && f32 == 0.0f) ? 0.0f : (float) Math.hypot(f33, f32);
                                    status3.acc = 0.0f;
                                    if (z4) {
                                        status3.rotation = ((status3.rotation + ((float) Math.toDegrees(Math.atan2(fArr17[i15], fArr16[i15]) + 1.5707963267948966d))) + 360.0f) % 360.0f;
                                    }
                                    Particle particle6 = particle2;
                                    Emitter emitter5 = emitter2;
                                    particle6.mParentEmitter = emitter5;
                                    particle6.mStartTime = 0L;
                                    particle6.mEndTime = 0L;
                                    long j7 = j3;
                                    particle6.start(j7);
                                    particle5 = particle6.next;
                                    i6 = i15 + 1;
                                    j5 = j7;
                                    f24 = f5;
                                    i5 = i16;
                                    fArr4 = fArr22;
                                    particleLinkedListRetain = particleLinkedList;
                                    world2 = world;
                                    emitter4 = emitter5;
                                    f23 = f4;
                                    particleRule2 = particleRule;
                                }
                                ParticleLinkedList particleLinkedList2 = particleLinkedListRetain;
                                World world3 = world2;
                                emitter = emitter4;
                                frameController = world3.mFrameController;
                                if (frameController.mIsStarted) {
                                    frameController.mIsStarted = false;
                                    Log.i("FrameController", "Stop frame control.");
                                    frameController.mFrameRateControlHandler.removeMessages(0);
                                    AnimatorCore animatorCore = frameController.mContainer.getNexusContext().mAnimatorCore;
                                    animatorCore.mFrameRate = 60;
                                    animatorCore.mFrameTime = 16393442;
                                }
                                world3.mWorldParticleLinkedList.put(particleLinkedList2);
                            }
                        }
                        i4 = 0;
                        float[] fArr142 = emissionRule2.pathPointerOffsetXArray;
                        float[] fArr152 = emissionRule2.pathPointerOffsetYArray;
                        float[] fArr162 = emissionRule2.pathTanXArray;
                        float[] fArr172 = emissionRule2.pathTanYArray;
                        float[] fArr182 = emissionRule2.pathPointerVelocitiesX;
                        float[] fArr192 = emissionRule2.pathPointerVelocitiesY;
                        if (status2 == null) {
                        }
                        float[] fArr212 = fArr3;
                        if (status2 == null) {
                        }
                        boolean[] zArr32 = zArr;
                        if (status2 == null) {
                        }
                        int i132 = particleRule2.posMode;
                        int i142 = particleRule2.colorMode;
                        FactorRangeableKeyFrameSetList factorRangeableKeyFrameSetList2 = particleRule2.factorKeyFrameList;
                        boolean zIsEmpty2 = factorRangeableKeyFrameSetList2.isEmpty();
                        ParticleLinkedList particleLinkedListRetain2 = sPool.retain(i4);
                        i5 = particleLinkedListRetain2.size;
                        float f252 = f3;
                        Particle particle52 = particleLinkedListRetain2.head;
                        i6 = 0;
                        while (i6 < i5) {
                        }
                        ParticleLinkedList particleLinkedList22 = particleLinkedListRetain2;
                        World world32 = world2;
                        emitter = emitter4;
                        frameController = world32.mFrameController;
                        if (frameController.mIsStarted) {
                        }
                        world32.mWorldParticleLinkedList.put(particleLinkedList22);
                    }
                    emitterSchedule2 = emitterSchedule;
                } else {
                    arrayList3 = arrayList4;
                    i = size;
                    emitter = emitter4;
                    i3 = i10;
                }
                if (emitterSchedule2.nextTime <= j) {
                    j2 = j;
                    emitterSchedule2.nextTime = j2;
                } else {
                    j2 = j;
                }
                particle = this;
                if (emitterSchedule2.nextTime > particle.mEndTime || emitter.mWorld == null) {
                    arrayList = arrayList3;
                    arrayList.remove(i3);
                    break;
                } else {
                    i2 = i3;
                    arrayList2 = arrayList3;
                }
            } else {
                particle = particle4;
                arrayList2 = arrayList4;
                i = size;
                i2 = i9;
            }
            i9 = i2 + 1;
            arrayList4 = arrayList2;
            particle4 = particle;
            size = i;
            status2 = status;
        }
        j2 = j;
        particle = particle4;
        arrayList = arrayList4;
        Emitter emitter6 = particle.mParentEmitter;
        String str = particle.mSubEmitterKey;
        emitter6.getClass();
        if (str == null || (str.hashCode() != emitter6.subEmitterKey.hashCode() && !emitter6.subEmitterKey.equals(str))) {
            ArrayList arrayList5 = particle.mParentEmitter.mEmitters;
            int size2 = arrayList5.size();
            for (int i29 = 0; i29 < size2; i29++) {
                Emitter emitter7 = (Emitter) arrayList5.get(i29);
                EmissionRule emissionRule3 = emitter7.mEmissionRule;
                if (emitter7.mWorld != null) {
                    ArrayList arrayList6 = particle.mEmitterSchedules;
                    int size3 = arrayList6.size();
                    int i30 = 0;
                    while (true) {
                        if (i30 >= size3) {
                            long j8 = particle.mLifeTime;
                            FloatRangeable floatRangeable2 = emissionRule3.beginFraction;
                            EmitterSchedule emitterSchedule3 = new EmitterSchedule(particle, emitter7, (floatRangeable2 != null ? (long) (floatRangeable2.get() * j8) : emissionRule3.beginTime.get()) + j2);
                            if (particle.mScheduleCheckLock) {
                                particle.mTempEmitterSchedules.add(emitterSchedule3);
                            } else {
                                particle.mEmitterSchedules.add(emitterSchedule3);
                            }
                        } else if (emitter7 == ((EmitterSchedule) arrayList6.get(i30)).emitter) {
                            break;
                        } else {
                            i30++;
                        }
                    }
                }
            }
            particle.mSubEmitterKey = particle.mParentEmitter.subEmitterKey;
        }
        particle.mScheduleCheckLock = false;
        if (!particle.mTempEmitterSchedules.isEmpty()) {
            arrayList.addAll(particle.mTempEmitterSchedules);
            particle.mTempEmitterSchedules.clear();
        }
        if (arrayList.size() > 1) {
            arrayList.sort(mEmitterScheduleComparator);
        }
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
        Bitmap bitmapLoadToCache;
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
                    bitmapLoadToCache = BitmapCache.loadToCache(context, bitmapLoader);
                } else {
                    Bitmap bitmap2 = (Bitmap) BitmapCache.sLruCache.get(Integer.valueOf(i2));
                    bitmapLoadToCache = bitmap2 == null ? BitmapCache.loadToCache(context, bitmapLoader) : bitmap2;
                }
                bitmapParticleTexture.mBitmap = bitmapLoadToCache;
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
