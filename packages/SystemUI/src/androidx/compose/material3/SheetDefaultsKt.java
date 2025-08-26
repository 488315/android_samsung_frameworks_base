package androidx.compose.material3;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.ui.unit.Dp;

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
