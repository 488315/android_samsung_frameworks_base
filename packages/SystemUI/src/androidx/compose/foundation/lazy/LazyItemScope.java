package androidx.compose.foundation.lazy;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutAnimateItemElement;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.IntOffset;

/* loaded from: classes.dex */
public interface LazyItemScope {
    static Modifier animateItem$default(LazyItemScope lazyItemScope, Modifier.Companion companion) {
        SpringSpec springSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);
        IntOffset.Companion companion2 = IntOffset.Companion;
        SpringSpec springSpecSpring$default2 = AnimationSpecKt.spring$default(0.0f, 400.0f, IntOffset.m849boximpl(VisibilityThresholdsKt.getVisibilityThreshold()), 1);
        SpringSpec springSpecSpring$default3 = AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);
        ((LazyItemScopeImpl) lazyItemScope).getClass();
        LazyLayoutAnimateItemElement lazyLayoutAnimateItemElement = new LazyLayoutAnimateItemElement(springSpecSpring$default, springSpecSpring$default2, springSpecSpring$default3);
        companion.getClass();
        return lazyLayoutAnimateItemElement;
    }
}
