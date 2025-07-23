package androidx.compose.animation.core;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.collection.IntList;
import androidx.collection.IntObjectMap;
import androidx.collection.internal.RuntimeHelpersKt;
import androidx.compose.animation.core.AnimationVector;
import androidx.compose.animation.core.ArcSpline;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VectorizedKeyframesSpec<V extends AnimationVector> implements VectorizedDurationBasedAnimationSpec<V> {
    public ArcSpline arcSpline;
    public final Easing defaultEasing;
    public final int delayMillis;
    public final int durationMillis;
    public final int initialArcMode;
    public final IntObjectMap keyframes;
    public AnimationVector lastInitialValue;
    public AnimationVector lastTargetValue;
    public int[] modes;
    public float[] posArray;
    public float[] slopeArray;
    public float[] times;
    public final IntList timestamps;
    public AnimationVector valueVector;
    public AnimationVector velocityVector;

    public /* synthetic */ VectorizedKeyframesSpec(IntList intList, IntObjectMap intObjectMap, int i, int i2, Easing easing, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(intList, intObjectMap, i, i2, easing, i3);
    }

    public final int findEntryForTimeMillis(int i) {
        int i2;
        IntList intList = this.timestamps;
        int i3 = intList._size;
        intList.getClass();
        if (i3 <= 0 || i3 > intList._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("");
            throw null;
        }
        int i4 = i3 - 1;
        int i5 = 0;
        while (true) {
            if (i5 <= i4) {
                i2 = (i5 + i4) >>> 1;
                int i6 = intList.content[i2];
                if (i6 >= i) {
                    if (i6 <= i) {
                        break;
                    }
                    i4 = i2 - 1;
                } else {
                    i5 = i2 + 1;
                }
            } else {
                i2 = -(i5 + 1);
                break;
            }
        }
        return i2 < -1 ? -(i2 + 2) : i2;
    }

    @Override // androidx.compose.animation.core.VectorizedDurationBasedAnimationSpec
    public final int getDelayMillis() {
        return this.delayMillis;
    }

    @Override // androidx.compose.animation.core.VectorizedDurationBasedAnimationSpec
    public final int getDurationMillis() {
        return this.durationMillis;
    }

    public final float getEasedTimeFromIndex(int i, int i2, boolean z) {
        Easing easing;
        float f;
        IntList intList = this.timestamps;
        if (i >= intList._size - 1) {
            f = i2;
        } else {
            int i3 = intList.get(i);
            int i4 = intList.get(i + 1);
            if (i2 != i3) {
                int i5 = i4 - i3;
                VectorizedKeyframeSpecElementInfo vectorizedKeyframeSpecElementInfo = (VectorizedKeyframeSpecElementInfo) this.keyframes.get(i3);
                if (vectorizedKeyframeSpecElementInfo == null || (easing = vectorizedKeyframeSpecElementInfo.easing) == null) {
                    easing = this.defaultEasing;
                }
                float f2 = i5;
                float transform = easing.transform((i2 - i3) / f2);
                return z ? transform : ((f2 * transform) + i3) / 1000;
            }
            f = i3;
        }
        return f / 1000;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        AnimationVector animationVector4;
        AnimationVector animationVector5;
        ArcSpline.Arc[][] arcArr;
        boolean z;
        AnimationVector animationVector6 = animationVector;
        boolean z2 = true;
        int[] iArr = VectorizedAnimationSpecKt.EmptyIntArray;
        long j2 = (j / 1000000) - this.delayMillis;
        int i = this.durationMillis;
        long j3 = i;
        if (j2 < 0) {
            j2 = 0;
        }
        if (j2 <= j3) {
            j3 = j2;
        }
        int i2 = (int) j3;
        IntObjectMap intObjectMap = this.keyframes;
        VectorizedKeyframeSpecElementInfo vectorizedKeyframeSpecElementInfo = (VectorizedKeyframeSpecElementInfo) intObjectMap.get(i2);
        if (vectorizedKeyframeSpecElementInfo != null) {
            return vectorizedKeyframeSpecElementInfo.vectorValue;
        }
        if (i2 >= i) {
            return animationVector2;
        }
        if (i2 <= 0) {
            return animationVector6;
        }
        init(animationVector6, animationVector2, animationVector3);
        AnimationVector animationVector7 = this.valueVector;
        animationVector7.getClass();
        int i3 = 0;
        if (this.arcSpline != VectorizedAnimationSpecKt.EmptyArcSpline) {
            float easedTimeFromIndex = getEasedTimeFromIndex(findEntryForTimeMillis(i2), i2, false);
            float[] fArr = this.posArray;
            ArcSpline.Arc[][] arcArr2 = this.arcSpline.arcs;
            int length = arcArr2.length - 1;
            float f = arcArr2[0][0].time1;
            float f2 = arcArr2[length][0].time2;
            int length2 = fArr.length;
            if (easedTimeFromIndex < f || easedTimeFromIndex > f2) {
                if (easedTimeFromIndex > f2) {
                    f = f2;
                } else {
                    length = 0;
                }
                float f3 = easedTimeFromIndex - f;
                int i4 = 0;
                int i5 = 0;
                while (i4 < length2 - 1) {
                    ArcSpline.Arc arc = arcArr2[length][i5];
                    boolean z3 = arc.isLinear;
                    float f4 = arc.ellipseCenterY;
                    float f5 = arc.ellipseCenterX;
                    if (z3) {
                        float f6 = arc.time1;
                        float f7 = arc.oneOverDeltaTime;
                        z = z2;
                        float f8 = arc.x2;
                        arcArr = arcArr2;
                        float f9 = arc.x1;
                        fArr[i4] = (f5 * f3) + DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f8, f9, (f - f6) * f7, f9);
                        float f10 = (f - f6) * f7;
                        float f11 = arc.y2;
                        float f12 = arc.y1;
                        fArr[i4 + 1] = (f4 * f3) + DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f11, f12, f10, f12);
                    } else {
                        arcArr = arcArr2;
                        z = z2;
                        arc.setPoint(f);
                        fArr[i4] = (arc.calcDX() * f3) + (arc.ellipseA * arc.tmpSinAngle) + f5;
                        fArr[i4 + 1] = (arc.calcDY() * f3) + (arc.ellipseB * arc.tmpCosAngle) + f4;
                    }
                    i4 += 2;
                    i5++;
                    arcArr2 = arcArr;
                    z2 = z;
                }
            } else {
                int length3 = arcArr2.length;
                int i6 = 0;
                boolean z4 = false;
                while (i6 < length3) {
                    int i7 = i3;
                    int i8 = i7;
                    while (i7 < length2 - 1) {
                        ArcSpline.Arc arc2 = arcArr2[i6][i8];
                        if (easedTimeFromIndex <= arc2.time2) {
                            if (arc2.isLinear) {
                                float f13 = arc2.time1;
                                float f14 = arc2.oneOverDeltaTime;
                                float f15 = arc2.x2;
                                float f16 = arc2.x1;
                                fArr[i7] = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f15, f16, (easedTimeFromIndex - f13) * f14, f16);
                                float f17 = arc2.y2;
                                float f18 = arc2.y1;
                                fArr[i7 + 1] = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f17, f18, (easedTimeFromIndex - f13) * f14, f18);
                            } else {
                                arc2.setPoint(easedTimeFromIndex);
                                fArr[i7] = (arc2.ellipseA * arc2.tmpSinAngle) + arc2.ellipseCenterX;
                                fArr[i7 + 1] = (arc2.ellipseB * arc2.tmpCosAngle) + arc2.ellipseCenterY;
                            }
                            z4 = true;
                        }
                        i7 += 2;
                        i8++;
                    }
                    if (z4) {
                        break;
                    }
                    i6++;
                    i3 = 0;
                }
            }
            int length4 = fArr.length;
            for (int i9 = 0; i9 < length4; i9++) {
                animationVector7.set$animation_core(fArr[i9], i9);
            }
        } else {
            int findEntryForTimeMillis = findEntryForTimeMillis(i2);
            float easedTimeFromIndex2 = getEasedTimeFromIndex(findEntryForTimeMillis, i2, true);
            IntList intList = this.timestamps;
            VectorizedKeyframeSpecElementInfo vectorizedKeyframeSpecElementInfo2 = (VectorizedKeyframeSpecElementInfo) intObjectMap.get(intList.get(findEntryForTimeMillis));
            if (vectorizedKeyframeSpecElementInfo2 != null && (animationVector5 = vectorizedKeyframeSpecElementInfo2.vectorValue) != null) {
                animationVector6 = animationVector5;
            }
            VectorizedKeyframeSpecElementInfo vectorizedKeyframeSpecElementInfo3 = (VectorizedKeyframeSpecElementInfo) intObjectMap.get(intList.get(findEntryForTimeMillis + 1));
            if (vectorizedKeyframeSpecElementInfo3 == null || (animationVector4 = vectorizedKeyframeSpecElementInfo3.vectorValue) == null) {
                animationVector4 = animationVector2;
            }
            int size$animation_core = animationVector7.getSize$animation_core();
            for (int i10 = 0; i10 < size$animation_core; i10++) {
                animationVector7.set$animation_core((animationVector4.get$animation_core(i10) * easedTimeFromIndex2) + ((1 - easedTimeFromIndex2) * animationVector6.get$animation_core(i10)), i10);
            }
        }
        return animationVector7;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        int[] iArr = VectorizedAnimationSpecKt.EmptyIntArray;
        long j2 = (j / 1000000) - this.delayMillis;
        long j3 = this.durationMillis;
        if (j2 < 0) {
            j2 = 0;
        }
        long j4 = j2 > j3 ? j3 : j2;
        if (j4 < 0) {
            return animationVector3;
        }
        init(animationVector, animationVector2, animationVector3);
        AnimationVector animationVector4 = this.velocityVector;
        animationVector4.getClass();
        int i = 0;
        if (this.arcSpline != VectorizedAnimationSpecKt.EmptyArcSpline) {
            int i2 = (int) j4;
            float easedTimeFromIndex = getEasedTimeFromIndex(findEntryForTimeMillis(i2), i2, false);
            float[] fArr = this.slopeArray;
            ArcSpline.Arc[][] arcArr = this.arcSpline.arcs;
            float f = arcArr[0][0].time1;
            float f2 = arcArr[arcArr.length - 1][0].time2;
            if (easedTimeFromIndex < f) {
                easedTimeFromIndex = f;
            }
            if (easedTimeFromIndex <= f2) {
                f2 = easedTimeFromIndex;
            }
            int length = fArr.length;
            boolean z = false;
            for (ArcSpline.Arc[] arcArr2 : arcArr) {
                int i3 = 0;
                int i4 = 0;
                while (i3 < length - 1) {
                    ArcSpline.Arc arc = arcArr2[i4];
                    if (f2 <= arc.time2) {
                        if (arc.isLinear) {
                            fArr[i3] = arc.ellipseCenterX;
                            fArr[i3 + 1] = arc.ellipseCenterY;
                        } else {
                            arc.setPoint(f2);
                            fArr[i3] = arc.calcDX();
                            fArr[i3 + 1] = arc.calcDY();
                        }
                        z = true;
                    }
                    i3 += 2;
                    i4++;
                }
                if (z) {
                    break;
                }
            }
            int length2 = fArr.length;
            while (i < length2) {
                animationVector4.set$animation_core(fArr[i], i);
                i++;
            }
        } else {
            AnimationVector valueFromNanos = getValueFromNanos((j4 - 1) * 1000000, animationVector, animationVector2, animationVector3);
            AnimationVector valueFromNanos2 = getValueFromNanos(j4 * 1000000, animationVector, animationVector2, animationVector3);
            int size$animation_core = valueFromNanos.getSize$animation_core();
            while (i < size$animation_core) {
                animationVector4.set$animation_core((valueFromNanos.get$animation_core(i) - valueFromNanos2.get$animation_core(i)) * 1000.0f, i);
                i++;
            }
        }
        return animationVector4;
    }

    public final void init(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        float[] fArr;
        boolean z = this.arcSpline != VectorizedAnimationSpecKt.EmptyArcSpline;
        AnimationVector animationVector4 = this.valueVector;
        IntObjectMap intObjectMap = this.keyframes;
        IntList intList = this.timestamps;
        if (animationVector4 == null) {
            this.valueVector = animationVector.newVector$animation_core();
            this.velocityVector = animationVector3.newVector$animation_core();
            int i = intList._size;
            float[] fArr2 = new float[i];
            for (int i2 = 0; i2 < i; i2++) {
                fArr2[i2] = intList.get(i2) / 1000;
            }
            this.times = fArr2;
            int i3 = intList._size;
            int[] iArr = new int[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                VectorizedKeyframeSpecElementInfo vectorizedKeyframeSpecElementInfo = (VectorizedKeyframeSpecElementInfo) intObjectMap.get(intList.get(i4));
                int i5 = vectorizedKeyframeSpecElementInfo != null ? vectorizedKeyframeSpecElementInfo.arcMode : this.initialArcMode;
                ArcMode.Companion.getClass();
                if (i5 != 0) {
                    z = true;
                }
                iArr[i4] = i5;
            }
            this.modes = iArr;
        }
        if (z) {
            if (this.arcSpline != VectorizedAnimationSpecKt.EmptyArcSpline && Intrinsics.areEqual(this.lastInitialValue, animationVector) && Intrinsics.areEqual(this.lastTargetValue, animationVector2)) {
                return;
            }
            this.lastInitialValue = animationVector;
            this.lastTargetValue = animationVector2;
            int size$animation_core = animationVector.getSize$animation_core() + (animationVector.getSize$animation_core() % 2);
            this.posArray = new float[size$animation_core];
            this.slopeArray = new float[size$animation_core];
            int i6 = intList._size;
            float[][] fArr3 = new float[i6][];
            for (int i7 = 0; i7 < i6; i7++) {
                int i8 = intList.get(i7);
                VectorizedKeyframeSpecElementInfo vectorizedKeyframeSpecElementInfo2 = (VectorizedKeyframeSpecElementInfo) intObjectMap.get(i8);
                if (i8 == 0 && vectorizedKeyframeSpecElementInfo2 == null) {
                    fArr = new float[size$animation_core];
                    for (int i9 = 0; i9 < size$animation_core; i9++) {
                        fArr[i9] = animationVector.get$animation_core(i9);
                    }
                } else if (i8 == this.durationMillis && vectorizedKeyframeSpecElementInfo2 == null) {
                    fArr = new float[size$animation_core];
                    for (int i10 = 0; i10 < size$animation_core; i10++) {
                        fArr[i10] = animationVector2.get$animation_core(i10);
                    }
                } else {
                    vectorizedKeyframeSpecElementInfo2.getClass();
                    fArr = new float[size$animation_core];
                    for (int i11 = 0; i11 < size$animation_core; i11++) {
                        fArr[i11] = vectorizedKeyframeSpecElementInfo2.vectorValue.get$animation_core(i11);
                    }
                }
                fArr3[i7] = fArr;
            }
            this.arcSpline = new ArcSpline(this.modes, this.times, fArr3);
        }
    }

    private VectorizedKeyframesSpec(IntList intList, IntObjectMap intObjectMap, int i, int i2, Easing easing, int i3) {
        this.timestamps = intList;
        this.keyframes = intObjectMap;
        this.durationMillis = i;
        this.delayMillis = i2;
        this.defaultEasing = easing;
        this.initialArcMode = i3;
        this.modes = VectorizedAnimationSpecKt.EmptyIntArray;
        float[] fArr = VectorizedAnimationSpecKt.EmptyFloatArray;
        this.times = fArr;
        this.posArray = fArr;
        this.slopeArray = fArr;
        this.arcSpline = VectorizedAnimationSpecKt.EmptyArcSpline;
    }

    public /* synthetic */ VectorizedKeyframesSpec(Map map, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(map, i, (i3 & 4) != 0 ? 0 : i2);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public VectorizedKeyframesSpec(java.util.Map<java.lang.Integer, ? extends kotlin.Pair<? extends V, ? extends androidx.compose.animation.core.Easing>> r10, int r11, int r12) {
        /*
            r9 = this;
            androidx.collection.MutableIntList r1 = new androidx.collection.MutableIntList
            int r0 = r10.size()
            int r0 = r0 + 2
            r1.<init>(r0)
            java.util.Set r0 = r10.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L13:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L2d
            java.lang.Object r2 = r0.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r2 = r2.getKey()
            java.lang.Number r2 = (java.lang.Number) r2
            int r2 = r2.intValue()
            r1.add(r2)
            goto L13
        L2d:
            r0 = 0
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            boolean r2 = r10.containsKey(r2)
            if (r2 != 0) goto L3b
            r1.add()
        L3b:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r11)
            boolean r2 = r10.containsKey(r2)
            if (r2 != 0) goto L48
            r1.add(r11)
        L48:
            int r2 = r1._size
            if (r2 != 0) goto L4d
            goto L52
        L4d:
            int[] r3 = r1.content
            java.util.Arrays.sort(r3, r0, r2)
        L52:
            androidx.collection.MutableIntObjectMap r2 = new androidx.collection.MutableIntObjectMap
            r3 = 1
            r4 = 0
            r2.<init>(r0, r3, r4)
            java.util.Set r10 = r10.entrySet()
            java.util.Iterator r10 = r10.iterator()
        L61:
            boolean r3 = r10.hasNext()
            if (r3 == 0) goto L97
            java.lang.Object r3 = r10.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r5 = r3.getKey()
            java.lang.Number r5 = (java.lang.Number) r5
            int r5 = r5.intValue()
            java.lang.Object r3 = r3.getValue()
            kotlin.Pair r3 = (kotlin.Pair) r3
            androidx.compose.animation.core.VectorizedKeyframeSpecElementInfo r6 = new androidx.compose.animation.core.VectorizedKeyframeSpecElementInfo
            java.lang.Object r7 = r3.getFirst()
            androidx.compose.animation.core.AnimationVector r7 = (androidx.compose.animation.core.AnimationVector) r7
            java.lang.Object r3 = r3.getSecond()
            androidx.compose.animation.core.Easing r3 = (androidx.compose.animation.core.Easing) r3
            androidx.compose.animation.core.ArcMode$Companion r8 = androidx.compose.animation.core.ArcMode.Companion
            r8.getClass()
            r6.<init>(r7, r3, r0, r4)
            r2.set(r5, r6)
            goto L61
        L97:
            androidx.compose.animation.core.EasingKt$$ExternalSyntheticLambda0 r5 = androidx.compose.animation.core.EasingKt.LinearEasing
            androidx.compose.animation.core.ArcMode$Companion r10 = androidx.compose.animation.core.ArcMode.Companion
            r10.getClass()
            r6 = 0
            r7 = 0
            r0 = r9
            r3 = r11
            r4 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.VectorizedKeyframesSpec.<init>(java.util.Map, int, int):void");
    }
}
