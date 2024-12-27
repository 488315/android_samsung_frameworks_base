package com.android.systemui.qs.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
