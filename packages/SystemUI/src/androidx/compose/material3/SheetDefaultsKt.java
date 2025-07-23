package androidx.compose.material3;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SheetDefaultsKt {
    public static final TweenSpec BottomSheetAnimationSpec;
    public static final float DragHandleVerticalPadding;

    static {
        Dp.Companion companion = Dp.Companion;
        DragHandleVerticalPadding = 22;
        BottomSheetAnimationSpec = AnimationSpecKt.tween$default(300, 0, EasingKt.FastOutSlowInEasing, 2);
    }
}
