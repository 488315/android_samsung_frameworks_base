package com.android.systemui.bouncer.ui.composable;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.animation.graphics.vector.AnimatedImageVector;
import com.android.compose.animation.Easings;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ShapeAnimations {
    public final TweenSpec clearAllShapeSizeAnimationSpec;
    public final TweenSpec deleteShapeSizeAnimationSpec;
    public final long dismissStaggerDelay;
    public final AnimatedImageVector dotToCircle;
    public final TweenSpec inputShiftAnimationSpec;
    public final float shapeSize;

    public /* synthetic */ ShapeAnimations(float f, AnimatedImageVector animatedImageVector, List list, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, animatedImageVector, list);
    }

    private ShapeAnimations(float f, AnimatedImageVector animatedImageVector, List<AnimatedImageVector> list) {
        this.shapeSize = f;
        this.dotToCircle = animatedImageVector;
        Duration.Companion companion = Duration.Companion;
        this.dismissStaggerDelay = DurationKt.toDuration(33, DurationUnit.MILLISECONDS);
        Easings.INSTANCE.getClass();
        this.inputShiftAnimationSpec = AnimationSpecKt.tween$default(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, 0, Easings.Standard, 2);
        this.deleteShapeSizeAnimationSpec = AnimationSpecKt.tween$default(200, 0, Easings.StandardDecelerate, 2);
        this.clearAllShapeSizeAnimationSpec = AnimationSpecKt.tween$default(450, 0, Easings.Legacy, 2);
    }
}
