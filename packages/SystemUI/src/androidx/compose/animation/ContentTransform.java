package androidx.compose.animation;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ContentTransform {
    public final ExitTransition initialContentExit;
    public SizeTransform sizeTransform;
    public final EnterTransition targetContentEnter;
    public final MutableFloatState targetContentZIndex$delegate;

    public ContentTransform(EnterTransition enterTransition, ExitTransition exitTransition, float f, SizeTransform sizeTransform) {
        this.targetContentEnter = enterTransition;
        this.initialContentExit = exitTransition;
        this.targetContentZIndex$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
        this.sizeTransform = sizeTransform;
    }

    public ContentTransform(EnterTransition enterTransition, ExitTransition exitTransition, float f, SizeTransform sizeTransform, int i, DefaultConstructorMarker defaultConstructorMarker) {
        f = (i & 4) != 0 ? 0.0f : f;
        if ((i & 8) != 0) {
            int i2 = AnimatedContentKt.$r8$clinit;
            sizeTransform = new SizeTransformImpl(true, new Function2() { // from class: androidx.compose.animation.AnimatedContentKt$SizeTransform$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    long j = ((IntSize) obj).packedValue;
                    long j2 = ((IntSize) obj2).packedValue;
                    IntSize.Companion companion = IntSize.Companion;
                    return AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m861boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
                }
            });
        }
        this(enterTransition, exitTransition, f, sizeTransform);
    }
}
