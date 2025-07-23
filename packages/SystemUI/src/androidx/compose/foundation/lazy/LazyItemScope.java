package androidx.compose.foundation.lazy;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutAnimateItemElement;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.IntOffset;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface LazyItemScope {
    static Modifier animateItem$default(LazyItemScope lazyItemScope, Modifier.Companion companion) {
        SpringSpec spring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);
        IntOffset.Companion companion2 = IntOffset.Companion;
        SpringSpec spring$default2 = AnimationSpecKt.spring$default(0.0f, 400.0f, IntOffset.m847boximpl(VisibilityThresholdsKt.getVisibilityThreshold()), 1);
        SpringSpec spring$default3 = AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);
        ((LazyItemScopeImpl) lazyItemScope).getClass();
        LazyLayoutAnimateItemElement lazyLayoutAnimateItemElement = new LazyLayoutAnimateItemElement(spring$default, spring$default2, spring$default3);
        companion.getClass();
        return lazyLayoutAnimateItemElement;
    }
}
