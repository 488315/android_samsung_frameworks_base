package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.DecayAnimationSpecKt;
import androidx.compose.animation.core.TweenSpec;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AnchoredDraggableDefaults {
    public static final AnchoredDraggableDefaults INSTANCE = new AnchoredDraggableDefaults();
    public static final TweenSpec SnapAnimationSpec = AnimationSpecKt.tween$default(0, 0, null, 7);
    public static final Function1 PositionalThreshold = new Function1() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableDefaults$PositionalThreshold$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            return Float.valueOf(((Number) obj).floatValue() / 2.0f);
        }
    };

    static {
        DecayAnimationSpecKt.exponentialDecay$default();
    }

    private AnchoredDraggableDefaults() {
    }
}
