package com.android.systemui.qs.composefragment.ui;

import com.android.compose.animation.scene.BaseTransitionBuilder;
import com.android.compose.animation.scene.BaseTransitionBuilderImpl;
import com.android.systemui.qs.composefragment.SceneKeys;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class ToEditModeKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        SceneKeys.INSTANCE.getClass();
        BaseTransitionBuilderImpl baseTransitionBuilderImpl = (BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj);
        baseTransitionBuilderImpl.fade(SceneKeys.QuickQuickSettings.rootElementKey);
        baseTransitionBuilderImpl.fade(SceneKeys.QuickSettings.rootElementKey);
        return Unit.INSTANCE;
    }
}
