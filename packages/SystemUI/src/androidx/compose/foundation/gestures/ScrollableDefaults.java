package androidx.compose.foundation.gestures;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScrollableDefaults {
    public static final ScrollableDefaults INSTANCE = new ScrollableDefaults();

    private ScrollableDefaults() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0022, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static androidx.compose.foundation.gestures.DefaultFlingBehavior flingBehavior(androidx.compose.runtime.Composer r4) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.foundation.gestures.ScrollableDefaults.flingBehavior (Scrollable.kt:537)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            androidx.compose.animation.core.DecayAnimationSpec r0 = androidx.compose.animation.SplineBasedFloatDecayAnimationSpec_androidKt.rememberSplineBasedDecay(r4)
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            boolean r1 = r4.changed(r0)
            java.lang.Object r2 = r4.rememberedValue()
            if (r1 != 0) goto L24
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r1) goto L2e
        L24:
            androidx.compose.foundation.gestures.DefaultFlingBehavior r2 = new androidx.compose.foundation.gestures.DefaultFlingBehavior
            r1 = 2
            r3 = 0
            r2.<init>(r0, r3, r1, r3)
            r4.updateRememberedValue(r2)
        L2e:
            androidx.compose.foundation.gestures.DefaultFlingBehavior r2 = (androidx.compose.foundation.gestures.DefaultFlingBehavior) r2
            boolean r4 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r4 == 0) goto L39
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L39:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.ScrollableDefaults.flingBehavior(androidx.compose.runtime.Composer):androidx.compose.foundation.gestures.DefaultFlingBehavior");
    }
}
