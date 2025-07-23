package androidx.compose.animation;

import androidx.compose.animation.core.Transition;
import androidx.compose.ui.Modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AnimatedContentScopeImpl implements AnimatedContentScope, AnimatedVisibilityScope {
    public final /* synthetic */ AnimatedVisibilityScope $$delegate_0;

    public AnimatedContentScopeImpl(AnimatedVisibilityScope animatedVisibilityScope) {
        this.$$delegate_0 = animatedVisibilityScope;
    }

    @Override // androidx.compose.animation.AnimatedVisibilityScope
    public final Modifier animateEnterExit(Modifier modifier, EnterTransition enterTransition, ExitTransition exitTransition) {
        return this.$$delegate_0.animateEnterExit(modifier, enterTransition, exitTransition);
    }

    @Override // androidx.compose.animation.AnimatedVisibilityScope
    public final Transition getTransition() {
        return this.$$delegate_0.getTransition();
    }
}
