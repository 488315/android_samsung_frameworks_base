package androidx.compose.material3.internal;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.interaction.DragInteraction$Start;
import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;

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

    /* JADX WARN: Removed duplicated region for block: B:6:0x000e A[PHI: r1
      0x000e: PHI (r1v3 androidx.compose.animation.core.TweenSpec) = 
      (r1v0 androidx.compose.animation.core.TweenSpec)
      (r1v0 androidx.compose.animation.core.TweenSpec)
      (r1v0 androidx.compose.animation.core.TweenSpec)
      (r1v4 androidx.compose.animation.core.TweenSpec)
      (r1v4 androidx.compose.animation.core.TweenSpec)
      (r1v4 androidx.compose.animation.core.TweenSpec)
      (r1v4 androidx.compose.animation.core.TweenSpec)
     binds: [B:19:0x002c, B:22:0x0031, B:28:0x003d, B:5:0x000c, B:8:0x0012, B:11:0x0017, B:14:0x001c] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: animateElevation-rAjV9yQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m320animateElevationrAjV9yQ(Animatable animatable, float f, Interaction interaction, Interaction interaction2, SuspendLambda suspendLambda) {
        TweenSpec tweenSpec;
        TweenSpec tweenSpec2 = null;
        if (interaction2 != null) {
            ElevationDefaults.INSTANCE.getClass();
            boolean z = interaction2 instanceof PressInteraction$Press;
            tweenSpec = DefaultIncomingSpec;
            if (z || (interaction2 instanceof DragInteraction$Start) || (interaction2 instanceof HoverInteraction$Enter) || (interaction2 instanceof FocusInteraction$Focus)) {
                tweenSpec2 = tweenSpec;
            }
        } else if (interaction != null) {
            ElevationDefaults.INSTANCE.getClass();
            boolean z2 = interaction instanceof PressInteraction$Press;
            tweenSpec = DefaultOutgoingSpec;
            if (!z2 && !(interaction instanceof DragInteraction$Start)) {
                if (interaction instanceof HoverInteraction$Enter) {
                    tweenSpec2 = HoveredOutgoingSpec;
                } else if (interaction instanceof FocusInteraction$Focus) {
                }
            }
        }
        TweenSpec tweenSpec3 = tweenSpec2;
        if (tweenSpec3 != null) {
            Object objAnimateTo$default = Animatable.animateTo$default(animatable, Dp.m837boximpl(f), tweenSpec3, null, null, suspendLambda, 12);
            return objAnimateTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objAnimateTo$default : Unit.INSTANCE;
        }
        Object objSnapTo = animatable.snapTo(Dp.m837boximpl(f), suspendLambda);
        return objSnapTo == CoroutineSingletons.COROUTINE_SUSPENDED ? objSnapTo : Unit.INSTANCE;
    }
}
