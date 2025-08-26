package com.android.bouncer.ui.composable;

import androidx.compose.animation.core.AnimationSpecKt;
import com.android.compose.animation.scene.SceneTransitionsBuilderImpl;
import com.android.compose.animation.scene.TransitionBuilderImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class SecBouncerContentKt$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SecBouncerContentKt$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SecSceneKeys.INSTANCE.getClass();
                SceneTransitionsBuilderImpl.from$default((SceneTransitionsBuilderImpl) obj, SecSceneKeys.ContiguousSceneKey, SecSceneKeys.SplitSceneKey, null, null, null, new SecBouncerContentKt$$ExternalSyntheticLambda1(1), 60);
                break;
            default:
                ((TransitionBuilderImpl) obj).spec = AnimationSpecKt.tween$default(0, 0, null, 7);
                break;
        }
        return Unit.INSTANCE;
    }
}
