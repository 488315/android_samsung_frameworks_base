package com.android.systemui.shade.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class ShadeInteractorSceneContainerImpl$isQsBypassingShade$2 extends AdaptedFunctionReference implements Function3 {
    public static final ShadeInteractorSceneContainerImpl$isQsBypassingShade$2 INSTANCE = new ShadeInteractorSceneContainerImpl$isQsBypassingShade$2();

    public ShadeInteractorSceneContainerImpl$isQsBypassingShade$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair((SceneKey) obj, (SceneKey) obj2);
    }
}
