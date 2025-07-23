package com.google.android.msdl.data.repository;

import android.os.VibrationEffect;
import com.google.android.msdl.data.model.HapticComposition;
import com.google.android.msdl.data.model.HapticCompositionPrimitive;
import com.google.android.msdl.data.model.HapticToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MSDLRepositoryImpl implements MSDLRepository {
    public static final Map HAPTIC_DATA;
    public static final int[] SPIN_WAVEFORM_AMPLITUDES;
    public static final long[] SPIN_WAVEFORM_TIMINGS;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        long[] jArr = {20, 20, 3, 43, 20, 20, 3};
        int[] iArr = {40, 80, 40, 0, 40, 80, 40};
        long[] copyOf = Arrays.copyOf(jArr, 8);
        copyOf[7] = 56;
        int length = copyOf.length;
        long[] copyOf2 = Arrays.copyOf(copyOf, length + 7);
        System.arraycopy(jArr, 0, copyOf2, length, 7);
        copyOf2.getClass();
        int length2 = copyOf2.length;
        long[] copyOf3 = Arrays.copyOf(copyOf2, length2 + 1);
        copyOf3[length2] = 56;
        int length3 = copyOf3.length;
        long[] copyOf4 = Arrays.copyOf(copyOf3, length3 + 7);
        System.arraycopy(jArr, 0, copyOf4, length3, 7);
        copyOf4.getClass();
        SPIN_WAVEFORM_TIMINGS = copyOf4;
        int[] copyOf5 = Arrays.copyOf(iArr, 8);
        copyOf5[7] = 10;
        int[] plus = ArraysKt___ArraysJvmKt.plus(copyOf5, iArr);
        int length4 = plus.length;
        int[] copyOf6 = Arrays.copyOf(plus, length4 + 1);
        copyOf6[length4] = 10;
        SPIN_WAVEFORM_AMPLITUDES = ArraysKt___ArraysJvmKt.plus(copyOf6, iArr);
        HAPTIC_DATA = MapsKt__MapsKt.mapOf(new Pair(HapticToken.NEGATIVE_CONFIRMATION_HIGH_EMPHASIS, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$1
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Arrays.asList(new HapticCompositionPrimitive(3, 1.0f, 0), new HapticCompositionPrimitive(3, 1.0f, 56), new HapticCompositionPrimitive(3, 1.0f, 56)), VibrationEffect.createWaveform(MSDLRepositoryImpl.SPIN_WAVEFORM_TIMINGS, MSDLRepositoryImpl.SPIN_WAVEFORM_AMPLITUDES, -1));
            }
        }), new Pair(HapticToken.NEGATIVE_CONFIRMATION_MEDIUM_EMPHASIS, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$2
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Arrays.asList(new HapticCompositionPrimitive(1, 1.0f, 0), new HapticCompositionPrimitive(1, 1.0f, 114), new HapticCompositionPrimitive(1, 1.0f, 114)), VibrationEffect.createWaveform(new long[]{10, 10, 10, 114, 10, 10, 10, 114, 10, 10, 10}, new int[]{10, 255, 20, 0, 10, 255, 20, 0, 10, 255, 20}, -1));
            }
        }), new Pair(HapticToken.POSITIVE_CONFIRMATION_HIGH_EMPHASIS, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$3
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Arrays.asList(new HapticCompositionPrimitive(1, 1.0f, 0), new HapticCompositionPrimitive(1, 1.0f, 114)), VibrationEffect.createWaveform(new long[]{10, 10, 10, 114, 10, 10, 10}, new int[]{10, 255, 20, 0, 10, 255, 20}, -1));
            }
        }), new Pair(HapticToken.POSITIVE_CONFIRMATION_MEDIUM_EMPHASIS, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$4
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Arrays.asList(new HapticCompositionPrimitive(1, 1.0f, 0), new HapticCompositionPrimitive(1, 1.0f, 52)), VibrationEffect.createWaveform(new long[]{10, 10, 10, 52, 10, 10, 10}, new int[]{10, 255, 20, 0, 10, 255, 20}, -1));
            }
        }), new Pair(HapticToken.POSITIVE_CONFIRMATION_LOW_EMPHASIS, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$5
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Arrays.asList(new HapticCompositionPrimitive(7, 1.0f, 0), new HapticCompositionPrimitive(1, 1.0f, 52)), VibrationEffect.createWaveform(new long[]{5, 52, 10, 10, 10}, new int[]{100, 0, 10, 255, 20}, -1));
            }
        }), new Pair(HapticToken.NEUTRAL_CONFIRMATION_HIGH_EMPHASIS, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$6
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(2, 1.0f, 0)), VibrationEffect.createWaveform(new long[]{50, 100, 100, 50}, new int[]{5, 50, 20, 10}, -1));
            }
        }), new Pair(HapticToken.NEUTRAL_CONFIRMATION_MEDIUM_EMPHASIS, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$7
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(1, 1.0f, 0)), VibrationEffect.createPredefined(0));
            }
        }), new Pair(HapticToken.LONG_PRESS, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$8
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(1, 1.0f, 0)), VibrationEffect.createPredefined(0));
            }
        }), new Pair(HapticToken.SWIPE_THRESHOLD_INDICATOR, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$9
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(1, 0.7f, 0)), VibrationEffect.createPredefined(0));
            }
        }), new Pair(HapticToken.TAP_HIGH_EMPHASIS, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$10
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(1, 0.7f, 0)), VibrationEffect.createPredefined(0));
            }
        }), new Pair(HapticToken.TAP_MEDIUM_EMPHASIS, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$11
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(1, 0.5f, 0)), VibrationEffect.createPredefined(0));
            }
        }), new Pair(HapticToken.DRAG_THRESHOLD_INDICATOR, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$12
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(1, 1.0f, 0)), VibrationEffect.createPredefined(2));
            }
        }), new Pair(HapticToken.DRAG_INDICATOR_CONTINUOUS, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$13
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                ArrayList arrayList = new ArrayList(5);
                for (int i = 0; i < 5; i++) {
                    arrayList.add(new HapticCompositionPrimitive(8, 0.3f, 0));
                }
                return new HapticComposition(arrayList, VibrationEffect.createWaveform(new long[]{10, 20, 20, 10}, new int[]{10, 30, 50, 10}, -1));
            }
        }), new Pair(HapticToken.DRAG_INDICATOR_DISCRETE, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$14
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(7, 0.5f, 0)), VibrationEffect.createPredefined(2));
            }
        }), new Pair(HapticToken.TAP_LOW_EMPHASIS, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$15
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(1, 0.3f, 0)), VibrationEffect.createPredefined(0));
            }
        }), new Pair(HapticToken.KEYPRESS_STANDARD, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$16
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(1, 0.5f, 0)), VibrationEffect.createPredefined(0));
            }
        }), new Pair(HapticToken.KEYPRESS_SPACEBAR, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$17
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(1, 0.7f, 0)), VibrationEffect.createPredefined(0));
            }
        }), new Pair(HapticToken.KEYPRESS_RETURN, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$18
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(1, 0.7f, 0)), VibrationEffect.createPredefined(0));
            }
        }), new Pair(HapticToken.KEYPRESS_DELETE, new MSDLHapticData() { // from class: com.google.android.msdl.data.repository.MSDLRepositoryImpl$Companion$HAPTIC_DATA$19
            @Override // com.google.android.msdl.data.repository.MSDLHapticData
            public final HapticComposition get() {
                return new HapticComposition(Collections.singletonList(new HapticCompositionPrimitive(1, 1.0f, 0)), VibrationEffect.createPredefined(0));
            }
        }));
    }
}
