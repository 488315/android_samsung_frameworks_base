package androidx.compose.animation;

import android.view.ViewConfiguration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SplineBasedFloatDecayAnimationSpec_androidKt {
    public static final float platformFlingScrollFriction = ViewConfiguration.getScrollFriction();

    /* JADX WARN: Code restructure failed: missing block: B:7:0x002a, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.animation.core.DecayAnimationSpec rememberSplineBasedDecay(androidx.compose.runtime.Composer r3) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.animation.rememberSplineBasedDecay (SplineBasedFloatDecayAnimationSpec.android.kt:40)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.ui.platform.CompositionLocalsKt.LocalDensity
            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
            java.lang.Object r0 = r3.consume(r0)
            androidx.compose.ui.unit.Density r0 = (androidx.compose.ui.unit.Density) r0
            float r1 = r0.getDensity()
            boolean r1 = r3.changed(r1)
            java.lang.Object r2 = r3.rememberedValue()
            if (r1 != 0) goto L2c
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r1) goto L38
        L2c:
            androidx.compose.animation.SplineBasedFloatDecayAnimationSpec r1 = new androidx.compose.animation.SplineBasedFloatDecayAnimationSpec
            r1.<init>(r0)
            androidx.compose.animation.core.DecayAnimationSpec r2 = androidx.compose.animation.core.DecayAnimationSpecKt.generateDecayAnimationSpec(r1)
            r3.updateRememberedValue(r2)
        L38:
            androidx.compose.animation.core.DecayAnimationSpec r2 = (androidx.compose.animation.core.DecayAnimationSpec) r2
            boolean r3 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r3 == 0) goto L43
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L43:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.SplineBasedFloatDecayAnimationSpec_androidKt.rememberSplineBasedDecay(androidx.compose.runtime.Composer):androidx.compose.animation.core.DecayAnimationSpec");
    }
}
