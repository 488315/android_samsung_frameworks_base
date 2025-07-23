package com.android.compose.gesture.effect;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class OffsetOverscrollEffect extends BaseContentOverscrollEffect {
    public static final Companion Companion = new Companion(null);
    public static final float MaxDistance;
    public final OffsetOverscrollEffect$node$1 node;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Dp.Companion companion = Dp.Companion;
        MaxDistance = 400;
    }

    public OffsetOverscrollEffect(CoroutineScope coroutineScope, AnimationSpec<Float> animationSpec) {
        super(coroutineScope, animationSpec);
        this.node = new OffsetOverscrollEffect$node$1(this);
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    public final DelegatableNode getNode() {
        return this.node;
    }
}
