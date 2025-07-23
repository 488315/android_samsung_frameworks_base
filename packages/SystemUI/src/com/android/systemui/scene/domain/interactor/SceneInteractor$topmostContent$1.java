package com.android.systemui.scene.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import java.util.Set;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
