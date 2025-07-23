package com.android.systemui.qs.composefragment.ui;

import com.android.compose.animation.scene.BaseTransitionBuilder;
import com.android.compose.animation.scene.BaseTransitionBuilderImpl;
import com.android.systemui.qs.composefragment.SceneKeys;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ToEditModeKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        SceneKeys.INSTANCE.getClass();
        BaseTransitionBuilderImpl baseTransitionBuilderImpl = (BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj);
        baseTransitionBuilderImpl.fade(SceneKeys.QuickQuickSettings.rootElementKey);
        baseTransitionBuilderImpl.fade(SceneKeys.QuickSettings.rootElementKey);
        return Unit.INSTANCE;
    }
}
