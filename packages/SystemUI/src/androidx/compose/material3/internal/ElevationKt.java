package androidx.compose.material3.internal;

import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.TweenSpec;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ElevationKt {
    public static final TweenSpec DefaultIncomingSpec;
    public static final TweenSpec DefaultOutgoingSpec;
    public static final TweenSpec HoveredOutgoingSpec;
    public static final CubicBezierEasing OutgoingSpecEasing = null;

    static {
        CubicBezierEasing cubicBezierEasing = new CubicBezierEasing(0.4f, 0.0f, 0.6f, 1.0f);
        DefaultIncomingSpec = new TweenSpec(120, 0, EasingKt.FastOutSlowInEasing, 2, null);
        DefaultOutgoingSpec = new TweenSpec(150, 0, cubicBezierEasing, 2, null);
        HoveredOutgoingSpec = new TweenSpec(120, 0, cubicBezierEasing, 2, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x001c, code lost:
    
        if ((r11 instanceof androidx.compose.foundation.interaction.FocusInteraction$Focus) != false) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x003d, code lost:
    
        if ((r10 instanceof androidx.compose.foundation.interaction.FocusInteraction$Focus) != false) goto L6;
     */
    /* renamed from: animateElevation-rAjV9yQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m319animateElevationrAjV9yQ(androidx.compose.animation.core.Animatable r8, float r9, androidx.compose.foundation.interaction.Interaction r10, androidx.compose.foundation.interaction.Interaction r11, kotlin.coroutines.jvm.internal.SuspendLambda r12) {
        /*
            r0 = 0
            if (r11 == 0) goto L21
            androidx.compose.material3.internal.ElevationDefaults r10 = androidx.compose.material3.internal.ElevationDefaults.INSTANCE
            r10.getClass()
            boolean r10 = r11 instanceof androidx.compose.foundation.interaction.PressInteraction$Press
            androidx.compose.animation.core.TweenSpec r1 = androidx.compose.material3.internal.ElevationKt.DefaultIncomingSpec
            if (r10 == 0) goto L10
        Le:
            r0 = r1
            goto L1f
        L10:
            boolean r10 = r11 instanceof androidx.compose.foundation.interaction.DragInteraction$Start
            if (r10 == 0) goto L15
            goto Le
        L15:
            boolean r10 = r11 instanceof androidx.compose.foundation.interaction.HoverInteraction$Enter
            if (r10 == 0) goto L1a
            goto Le
        L1a:
            boolean r10 = r11 instanceof androidx.compose.foundation.interaction.FocusInteraction$Focus
            if (r10 == 0) goto L1f
            goto Le
        L1f:
            r3 = r0
            goto L40
        L21:
            if (r10 == 0) goto L1f
            androidx.compose.material3.internal.ElevationDefaults r11 = androidx.compose.material3.internal.ElevationDefaults.INSTANCE
            r11.getClass()
            boolean r11 = r10 instanceof androidx.compose.foundation.interaction.PressInteraction$Press
            androidx.compose.animation.core.TweenSpec r1 = androidx.compose.material3.internal.ElevationKt.DefaultOutgoingSpec
            if (r11 == 0) goto L2f
        L2e:
            goto Le
        L2f:
            boolean r11 = r10 instanceof androidx.compose.foundation.interaction.DragInteraction$Start
            if (r11 == 0) goto L34
            goto L2e
        L34:
            boolean r11 = r10 instanceof androidx.compose.foundation.interaction.HoverInteraction$Enter
            if (r11 == 0) goto L3b
            androidx.compose.animation.core.TweenSpec r0 = androidx.compose.material3.internal.ElevationKt.HoveredOutgoingSpec
            goto L1f
        L3b:
            boolean r10 = r10 instanceof androidx.compose.foundation.interaction.FocusInteraction$Focus
            if (r10 == 0) goto L1f
            goto L2e
        L40:
            if (r3 == 0) goto L58
            androidx.compose.ui.unit.Dp r2 = androidx.compose.ui.unit.Dp.m835boximpl(r9)
            r4 = 0
            r5 = 0
            r7 = 12
            r1 = r8
            r6 = r12
            java.lang.Object r8 = androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, r3, r4, r5, r6, r7)
            kotlin.coroutines.intrinsics.CoroutineSingletons r9 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            if (r8 != r9) goto L55
            return r8
        L55:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L58:
            r1 = r8
            r6 = r12
            androidx.compose.ui.unit.Dp r8 = androidx.compose.ui.unit.Dp.m835boximpl(r9)
            java.lang.Object r8 = r1.snapTo(r8, r6)
            kotlin.coroutines.intrinsics.CoroutineSingletons r9 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            if (r8 != r9) goto L67
            return r8
        L67:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.internal.ElevationKt.m319animateElevationrAjV9yQ(androidx.compose.animation.core.Animatable, float, androidx.compose.foundation.interaction.Interaction, androidx.compose.foundation.interaction.Interaction, kotlin.coroutines.jvm.internal.SuspendLambda):java.lang.Object");
    }
}
