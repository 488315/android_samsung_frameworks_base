package com.android.systemui.scene.domain.resolver;

import com.android.systemui.scene.shared.model.Scenes;
import java.util.Set;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class HomeSceneFamilyResolver$resolvedScene$1 extends AdaptedFunctionReference implements Function4 {
    public HomeSceneFamilyResolver$resolvedScene$1(Object obj) {
        super(4, obj, HomeSceneFamilyResolver.class, "homeScene", "homeScene(Ljava/lang/Boolean;ZZ)Lcom/android/compose/animation/scene/SceneKey;", 4);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        HomeSceneFamilyResolver homeSceneFamilyResolver = (HomeSceneFamilyResolver) this.receiver;
        Set set = HomeSceneFamilyResolver.homeScenes;
        homeSceneFamilyResolver.getClass();
        return Intrinsics.areEqual((Boolean) obj, Boolean.TRUE) ? Scenes.Lockscreen : !booleanValue ? Scenes.Lockscreen : !booleanValue2 ? Scenes.Lockscreen : Scenes.Gone;
    }
}
