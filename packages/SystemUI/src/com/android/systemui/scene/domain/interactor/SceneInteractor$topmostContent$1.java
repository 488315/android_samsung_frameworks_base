package com.android.systemui.scene.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import java.util.Set;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes2.dex */
final /* synthetic */ class SceneInteractor$topmostContent$1 extends AdaptedFunctionReference implements Function3 {
    public SceneInteractor$topmostContent$1(Object obj) {
        super(3, obj, SceneInteractor.class, "determineTopmostContent", "determineTopmostContent(Lcom/android/compose/animation/scene/SceneKey;Ljava/util/Set;)Lcom/android/compose/animation/scene/ContentKey;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return ((SceneInteractor) this.receiver).determineTopmostContent((SceneKey) obj, (Set) obj2);
    }
}
