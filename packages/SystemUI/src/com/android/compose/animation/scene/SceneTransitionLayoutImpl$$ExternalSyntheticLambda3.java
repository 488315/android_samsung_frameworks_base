package com.android.compose.animation.scene;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final /* synthetic */ class SceneTransitionLayoutImpl$$ExternalSyntheticLambda3 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SceneTransitionLayoutImpl f$0;

    public /* synthetic */ SceneTransitionLayoutImpl$$ExternalSyntheticLambda3(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = sceneTransitionLayoutImpl;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                this.f$0.Scenes(RecomposeScopeImplKt.updateChangedFlags(1), composer);
                break;
            default:
                this.f$0.BackHandler(RecomposeScopeImplKt.updateChangedFlags(1), composer);
                break;
        }
        return Unit.INSTANCE;
    }
}
