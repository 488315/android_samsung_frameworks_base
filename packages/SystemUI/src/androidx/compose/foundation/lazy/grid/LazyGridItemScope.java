package androidx.compose.foundation.lazy.grid;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutAnimateItemElement;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.IntOffset;

/* loaded from: classes.dex */
public interface LazyGridItemScope {
    static Modifier animateItem$default(LazyGridItemScope lazyGridItemScope, Modifier modifier, SpringSpec springSpec, int i) {
        SpringSpec springSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);
        if ((i & 2) != 0) {
            IntOffset.Companion companion = IntOffset.Companion;
            springSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, IntOffset.m849boximpl(VisibilityThresholdsKt.getVisibilityThreshold()), 1);
        }
        SpringSpec springSpecSpring$default2 = (i & 4) != 0 ? AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5) : null;
        ((LazyGridItemScopeImpl) lazyGridItemScope).getClass();
        return modifier.then(new LazyLayoutAnimateItemElement(springSpecSpring$default, springSpec, springSpecSpring$default2));
    }
}
