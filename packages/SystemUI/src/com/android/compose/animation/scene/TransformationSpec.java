package com.android.compose.animation.scene;

import androidx.compose.animation.core.AnimationSpecKt;
import kotlin.collections.EmptyList;

/* loaded from: classes.dex */
public interface TransformationSpec {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final TransformationSpecImpl Empty = new TransformationSpecImpl(AnimationSpecKt.snap$default(), null, EmptyList.INSTANCE);
        public static final TransformationSpec$Companion$$ExternalSyntheticLambda0 EmptyProvider = new TransformationSpec$Companion$$ExternalSyntheticLambda0();

        private Companion() {
        }
    }
}
