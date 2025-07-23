package com.android.systemui.inputdevice.tutorial.ui.composable;

import androidx.compose.animation.AnimatedContentKt;
import androidx.compose.animation.AnimatedContentTransitionScope;
import androidx.compose.animation.AnimatedContentTransitionScopeImpl;
import androidx.compose.animation.ContentTransform;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.EasingKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class TutorialAnimationKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        EnterTransition.Companion.getClass();
        ContentTransform contentTransform = AnimatedContentKt.togetherWith(EnterTransition.None, EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(10, 0, EasingKt.LinearEasing, 2), 2));
        ((AnimatedContentTransitionScopeImpl) ((AnimatedContentTransitionScope) obj)).getClass();
        contentTransform.sizeTransform = null;
        return contentTransform;
    }
}
