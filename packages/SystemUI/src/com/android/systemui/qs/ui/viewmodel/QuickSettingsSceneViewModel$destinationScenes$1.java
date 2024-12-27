package com.android.systemui.qs.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class QuickSettingsSceneViewModel$destinationScenes$1 extends AdaptedFunctionReference implements Function3 {
    public QuickSettingsSceneViewModel$destinationScenes$1(Object obj) {
        super(3, obj, QuickSettingsSceneViewModel.class, "destinationScenes", "destinationScenes(ZLcom/android/compose/animation/scene/SceneKey;)Ljava/util/Map;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        ((QuickSettingsSceneViewModel) this.receiver).getClass();
        return QuickSettingsSceneViewModel.destinationScenes((SceneKey) obj2, booleanValue);
    }
}
