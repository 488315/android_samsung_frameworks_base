package androidx.compose.foundation.lazy.grid;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutAnimateItemElement;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.IntOffset;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface LazyGridItemScope {
    static Modifier animateItem$default(LazyGridItemScope lazyGridItemScope, Modifier modifier, SpringSpec springSpec, int i) {
        SpringSpec spring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);
        if ((i & 2) != 0) {
            IntOffset.Companion companion = IntOffset.Companion;
            springSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, IntOffset.m847boximpl(VisibilityThresholdsKt.getVisibilityThreshold()), 1);
        }
        SpringSpec spring$default2 = (i & 4) != 0 ? AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5) : null;
        ((LazyGridItemScopeImpl) lazyGridItemScope).getClass();
        return modifier.then(new LazyLayoutAnimateItemElement(spring$default, springSpec, spring$default2));
    }
}
