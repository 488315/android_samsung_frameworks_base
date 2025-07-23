package androidx.compose.animation;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SingleValueAnimationKt {
    public static final SpringSpec colorDefaultSpring = AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7);

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0031, code lost:
    
        if (r12 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
     */
    /* renamed from: animateColorAsState-euL9pac, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.runtime.State m7animateColorAsStateeuL9pac(long r9, androidx.compose.animation.core.FiniteAnimationSpec r11, java.lang.String r12, androidx.compose.runtime.Composer r13, int r14, int r15) {
        /*
            r0 = r15 & 2
            if (r0 == 0) goto L6
            androidx.compose.animation.core.SpringSpec r11 = androidx.compose.animation.SingleValueAnimationKt.colorDefaultSpring
        L6:
            r2 = r11
            r11 = r15 & 4
            if (r11 == 0) goto Ld
            java.lang.String r12 = "ColorAnimation"
        Ld:
            r4 = r12
            boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r11 == 0) goto L19
            java.lang.String r11 = "androidx.compose.animation.animateColorAsState (SingleValueAnimation.kt:60)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r11)
        L19:
            androidx.compose.ui.graphics.colorspace.ColorSpace r11 = androidx.compose.ui.graphics.Color.m459getColorSpaceimpl(r9)
            r6 = r13
            androidx.compose.runtime.ComposerImpl r6 = (androidx.compose.runtime.ComposerImpl) r6
            boolean r11 = r6.changed(r11)
            java.lang.Object r12 = r6.rememberedValue()
            if (r11 != 0) goto L33
            androidx.compose.runtime.Composer$Companion r11 = androidx.compose.runtime.Composer.Companion
            r11.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r11 = androidx.compose.runtime.Composer.Companion.Empty
            if (r12 != r11) goto L45
        L33:
            kotlin.jvm.functions.Function1 r11 = androidx.compose.animation.ColorVectorConverterKt.ColorToVector
            androidx.compose.ui.graphics.colorspace.ColorSpace r12 = androidx.compose.ui.graphics.Color.m459getColorSpaceimpl(r9)
            androidx.compose.animation.ColorVectorConverterKt$ColorToVector$1 r11 = (androidx.compose.animation.ColorVectorConverterKt$ColorToVector$1) r11
            java.lang.Object r11 = r11.mo779invoke(r12)
            r12 = r11
            androidx.compose.animation.core.TwoWayConverter r12 = (androidx.compose.animation.core.TwoWayConverter) r12
            r6.updateRememberedValue(r12)
        L45:
            r1 = r12
            androidx.compose.animation.core.TwoWayConverter r1 = (androidx.compose.animation.core.TwoWayConverter) r1
            androidx.compose.ui.graphics.Color r0 = androidx.compose.ui.graphics.Color.m454boximpl(r9)
            r9 = r14 & 14
            int r10 = r14 << 3
            r10 = r10 & 896(0x380, float:1.256E-42)
            r9 = r9 | r10
            int r10 = r14 << 6
            r11 = 57344(0xe000, float:8.0356E-41)
            r11 = r11 & r10
            r9 = r9 | r11
            r11 = 458752(0x70000, float:6.42848E-40)
            r10 = r10 & r11
            r7 = r9 | r10
            r8 = 8
            r3 = 0
            r5 = 0
            androidx.compose.runtime.State r9 = androidx.compose.animation.core.AnimateAsStateKt.animateValueAsState(r0, r1, r2, r3, r4, r5, r6, r7, r8)
            boolean r10 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r10 == 0) goto L70
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L70:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.SingleValueAnimationKt.m7animateColorAsStateeuL9pac(long, androidx.compose.animation.core.FiniteAnimationSpec, java.lang.String, androidx.compose.runtime.Composer, int, int):androidx.compose.runtime.State");
    }
}
