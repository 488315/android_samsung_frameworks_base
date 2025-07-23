package androidx.compose.material3;

import androidx.compose.animation.core.SpringSpec;
import androidx.compose.material3.tokens.MotionSchemeKeyTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class MotionSchemeKt {
    public static final StaticProvidableCompositionLocal LocalMotionScheme = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.material3.MotionSchemeKt$LocalMotionScheme$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            MotionScheme.Companion.getClass();
            return new MotionScheme$Companion$standard$1();
        }
    });

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MotionSchemeKeyTokens.values().length];
            try {
                iArr[MotionSchemeKeyTokens.DefaultSpatial.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[MotionSchemeKeyTokens.FastSpatial.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[MotionSchemeKeyTokens.SlowSpatial.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[MotionSchemeKeyTokens.DefaultEffects.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[MotionSchemeKeyTokens.FastEffects.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[MotionSchemeKeyTokens.SlowEffects.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final SpringSpec fromToken(MotionScheme motionScheme, MotionSchemeKeyTokens motionSchemeKeyTokens) {
        switch (WhenMappings.$EnumSwitchMapping$0[motionSchemeKeyTokens.ordinal()]) {
            case 1:
                return ((MotionScheme$Companion$standard$1) motionScheme).defaultSpatialSpec;
            case 2:
                return ((MotionScheme$Companion$standard$1) motionScheme).fastSpatialSpec;
            case 3:
                return ((MotionScheme$Companion$standard$1) motionScheme).slowSpatialSpec;
            case 4:
                return ((MotionScheme$Companion$standard$1) motionScheme).defaultEffectsSpec;
            case 5:
                return ((MotionScheme$Companion$standard$1) motionScheme).fastEffectsSpec;
            case 6:
                return ((MotionScheme$Companion$standard$1) motionScheme).slowEffectsSpec;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final SpringSpec value(MotionSchemeKeyTokens motionSchemeKeyTokens, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.value (MotionScheme.kt:304)");
        }
        MaterialTheme.INSTANCE.getClass();
        SpringSpec fromToken = fromToken(MaterialTheme.getMotionScheme(composer), motionSchemeKeyTokens);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return fromToken;
    }
}
