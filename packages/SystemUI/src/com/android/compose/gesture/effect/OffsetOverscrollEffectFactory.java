package com.android.compose.gesture.effect;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.OverscrollFactory;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class OffsetOverscrollEffectFactory implements OverscrollFactory {
    public final CoroutineScope animationScope;
    public final AnimationSpec animationSpec;

    public OffsetOverscrollEffectFactory(CoroutineScope coroutineScope, AnimationSpec<Float> animationSpec) {
        this.animationScope = coroutineScope;
        this.animationSpec = animationSpec;
    }

    @Override // androidx.compose.foundation.OverscrollFactory
    public final OverscrollEffect createOverscrollEffect() {
        return new OffsetOverscrollEffect(this.animationScope, this.animationSpec);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OffsetOverscrollEffectFactory)) {
            return false;
        }
        OffsetOverscrollEffectFactory offsetOverscrollEffectFactory = (OffsetOverscrollEffectFactory) obj;
        return Intrinsics.areEqual(this.animationScope, offsetOverscrollEffectFactory.animationScope) && Intrinsics.areEqual(this.animationSpec, offsetOverscrollEffectFactory.animationSpec);
    }

    public final int hashCode() {
        return this.animationSpec.hashCode() + (this.animationScope.hashCode() * 31);
    }

    public final String toString() {
        return "OffsetOverscrollEffectFactory(animationScope=" + this.animationScope + ", animationSpec=" + this.animationSpec + ")";
    }
}
