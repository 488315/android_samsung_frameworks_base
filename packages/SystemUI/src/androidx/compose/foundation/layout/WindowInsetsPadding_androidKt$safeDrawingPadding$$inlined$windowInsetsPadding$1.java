package androidx.compose.foundation.layout;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1 extends Lambda implements Function3 {
    public WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1() {
        super(3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0036, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    @Override // kotlin.jvm.functions.Function3
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invoke(java.lang.Object r1, java.lang.Object r2, java.lang.Object r3) {
        /*
            r0 = this;
            androidx.compose.ui.Modifier r1 = (androidx.compose.ui.Modifier) r1
            androidx.compose.runtime.Composer r2 = (androidx.compose.runtime.Composer) r2
            java.lang.Number r3 = (java.lang.Number) r3
            r3.intValue()
            androidx.compose.runtime.ComposerImpl r2 = (androidx.compose.runtime.ComposerImpl) r2
            r0 = 359872873(0x15733969, float:4.9118748E-26)
            r2.startReplaceGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L1c
            java.lang.String r0 = "androidx.compose.foundation.layout.windowInsetsPadding.<anonymous> (WindowInsetsPadding.android.kt:240)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L1c:
            androidx.compose.foundation.layout.WindowInsetsHolder$Companion r0 = androidx.compose.foundation.layout.WindowInsetsHolder.Companion
            r0.getClass()
            androidx.compose.foundation.layout.WindowInsetsHolder r0 = androidx.compose.foundation.layout.WindowInsetsHolder.Companion.current(r2)
            boolean r1 = r2.changed(r0)
            java.lang.Object r3 = r2.rememberedValue()
            if (r1 != 0) goto L38
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r1) goto L42
        L38:
            androidx.compose.foundation.layout.WindowInsets r0 = r0.safeDrawing
            androidx.compose.foundation.layout.InsetsPaddingModifier r3 = new androidx.compose.foundation.layout.InsetsPaddingModifier
            r3.<init>(r0)
            r2.updateRememberedValue(r3)
        L42:
            androidx.compose.foundation.layout.InsetsPaddingModifier r3 = (androidx.compose.foundation.layout.InsetsPaddingModifier) r3
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L4d
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L4d:
            r0 = 0
            r2.end(r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }
}
