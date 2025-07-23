package com.android.compose.animation.scene;

import androidx.compose.animation.core.AnimationSpecKt;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface TransformationSpec {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final TransformationSpecImpl Empty = new TransformationSpecImpl(AnimationSpecKt.snap$default(), null, EmptyList.INSTANCE);
        public static final TransformationSpec$Companion$$ExternalSyntheticLambda0 EmptyProvider = new TransformationSpec$Companion$$ExternalSyntheticLambda0();

        private Companion() {
        }
    }
}
