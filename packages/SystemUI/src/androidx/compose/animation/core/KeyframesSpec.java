package androidx.compose.animation.core;

import androidx.collection.MutableIntList;
import androidx.collection.MutableIntObjectMap;
import androidx.compose.animation.core.ArcMode;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class KeyframesSpec<T> implements DurationBasedAnimationSpec<T> {
    public final KeyframesSpecConfig config;

    public final class KeyframeEntity<T> extends KeyframeBaseEntity<T> {
        public final int arcMode;

        public /* synthetic */ KeyframeEntity(Object obj, Easing easing, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(obj, easing, i);
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof KeyframeEntity)) {
                return false;
            }
            KeyframeEntity keyframeEntity = (KeyframeEntity) obj;
            if (Intrinsics.areEqual(keyframeEntity.value, this.value) && Intrinsics.areEqual(keyframeEntity.easing, this.easing)) {
                int i = keyframeEntity.arcMode;
                ArcMode.Companion companion = ArcMode.Companion;
                if (i == this.arcMode) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            Object obj = this.value;
            int iHashCode = obj != null ? obj.hashCode() : 0;
            ArcMode.Companion companion = ArcMode.Companion;
            return this.easing.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.arcMode, iHashCode * 31, 31);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public KeyframeEntity(Object obj, Easing easing, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            easing = (i2 & 2) != 0 ? EasingKt.LinearEasing : easing;
            if ((i2 & 4) != 0) {
                ArcMode.Companion.getClass();
                i = 0;
            }
            this(obj, easing, i, null);
        }

        private KeyframeEntity(T t, Easing easing, int i) {
            super(t, easing, null);
            this.arcMode = i;
        }
    }

    public final class KeyframesSpecConfig<T> extends KeyframesSpecBaseConfig<T, KeyframeEntity<T>> {
        public KeyframesSpecConfig() {
            super(null);
        }

        public final KeyframeEntity at(int i, Object obj) {
            KeyframeEntity keyframeEntity = new KeyframeEntity(obj, null, 0, 6, null);
            this.keyframes.set(i, keyframeEntity);
            return keyframeEntity;
        }
    }

    public KeyframesSpec(KeyframesSpecConfig<T> keyframesSpecConfig) {
        this.config = keyframesSpecConfig;
    }

    @Override // androidx.compose.animation.core.FiniteAnimationSpec, androidx.compose.animation.core.AnimationSpec
    public final VectorizedKeyframesSpec vectorize(TwoWayConverter twoWayConverter) {
        int[] iArr;
        Object[] objArr;
        long[] jArr;
        int[] iArr2;
        Object[] objArr2;
        long[] jArr2;
        int i;
        KeyframesSpecConfig keyframesSpecConfig = this.config;
        MutableIntList mutableIntList = new MutableIntList(keyframesSpecConfig.keyframes._size + 2);
        MutableIntObjectMap mutableIntObjectMap = keyframesSpecConfig.keyframes;
        MutableIntObjectMap mutableIntObjectMap2 = new MutableIntObjectMap(mutableIntObjectMap._size);
        int[] iArr3 = mutableIntObjectMap.keys;
        Object[] objArr3 = mutableIntObjectMap.values;
        long[] jArr3 = mutableIntObjectMap.metadata;
        int length = jArr3.length - 2;
        if (length >= 0) {
            int i2 = 0;
            while (true) {
                long j = jArr3[i2];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i3 = 8;
                    int i4 = 8 - ((~(i2 - length)) >>> 31);
                    int i5 = 0;
                    while (i5 < i4) {
                        if ((255 & j) < 128) {
                            int i6 = (i2 << 3) + i5;
                            int i7 = iArr3[i6];
                            KeyframeEntity keyframeEntity = (KeyframeEntity) objArr3[i6];
                            mutableIntList.add(i7);
                            i = i3;
                            iArr2 = iArr3;
                            objArr2 = objArr3;
                            jArr2 = jArr3;
                            mutableIntObjectMap2.set(i7, new VectorizedKeyframeSpecElementInfo((AnimationVector) ((TwoWayConverterImpl) twoWayConverter).convertToVector.mo781invoke(keyframeEntity.value), keyframeEntity.easing, keyframeEntity.arcMode, null));
                        } else {
                            iArr2 = iArr3;
                            objArr2 = objArr3;
                            jArr2 = jArr3;
                            i = i3;
                        }
                        j >>= i;
                        i5++;
                        i3 = i;
                        iArr3 = iArr2;
                        objArr3 = objArr2;
                        jArr3 = jArr2;
                    }
                    iArr = iArr3;
                    objArr = objArr3;
                    jArr = jArr3;
                    if (i4 != i3) {
                        break;
                    }
                } else {
                    iArr = iArr3;
                    objArr = objArr3;
                    jArr = jArr3;
                }
                if (i2 == length) {
                    break;
                }
                i2++;
                iArr3 = iArr;
                objArr3 = objArr;
                jArr3 = jArr;
            }
        }
        if (!mutableIntObjectMap.containsKey(0)) {
            mutableIntList.add();
        }
        if (!mutableIntObjectMap.containsKey(keyframesSpecConfig.durationMillis)) {
            mutableIntList.add(keyframesSpecConfig.durationMillis);
        }
        int i8 = mutableIntList._size;
        if (i8 != 0) {
            Arrays.sort(mutableIntList.content, 0, i8);
        }
        int i9 = keyframesSpecConfig.durationMillis;
        EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda0 = EasingKt.LinearEasing;
        ArcMode.Companion.getClass();
        return new VectorizedKeyframesSpec(mutableIntList, mutableIntObjectMap2, i9, 0, easingKt$$ExternalSyntheticLambda0, 0, null);
    }
}
