package com.android.systemui.scene.ui.viewmodel;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SceneContainerHapticsViewModel$isShadePullHapticsRequired$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ SceneContainerHapticsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SceneContainerHapticsViewModel$isShadePullHapticsRequired$1(SceneContainerHapticsViewModel sceneContainerHapticsViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = sceneContainerHapticsViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        SceneContainerHapticsViewModel$isShadePullHapticsRequired$1 sceneContainerHapticsViewModel$isShadePullHapticsRequired$1 = new SceneContainerHapticsViewModel$isShadePullHapticsRequired$1(this.this$0, (Continuation) obj3);
        sceneContainerHapticsViewModel$isShadePullHapticsRequired$1.Z$0 = booleanValue;
        sceneContainerHapticsViewModel$isShadePullHapticsRequired$1.L$0 = (ObservableTransitionState) obj2;
        return sceneContainerHapticsViewModel$isShadePullHapticsRequired$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        ObservableTransitionState observableTransitionState = (ObservableTransitionState) this.L$0;
        boolean z2 = false;
        if (z) {
            this.this$0.getClass();
            boolean z3 = ObservableTransitionState.isTransitioning$default(observableTransitionState, Scenes.Gone, null, 2) || ObservableTransitionState.isTransitioning$default(observableTransitionState, Scenes.Lockscreen, null, 2);
            boolean z4 = ObservableTransitionState.isTransitioning$default(observableTransitionState, null, Scenes.Shade, 1) || ObservableTransitionState.isTransitioning$default(observableTransitionState, null, Scenes.QuickSettings, 1) || ObservableTransitionState.isTransitioning$default(observableTransitionState, null, Overlays.QuickSettingsShade, 1) || ObservableTransitionState.isTransitioning$default(observableTransitionState, null, Overlays.NotificationsShade, 1);
            if (z3 && z4) {
                z2 = true;
            }
        }
        return Boolean.valueOf(z2);
    }
}
