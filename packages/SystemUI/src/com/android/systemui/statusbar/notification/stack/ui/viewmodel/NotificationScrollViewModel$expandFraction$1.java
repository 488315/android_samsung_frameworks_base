package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationScrollViewModel$expandFraction$1 extends SuspendLambda implements Function6 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ NotificationScrollViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationScrollViewModel$expandFraction$1(NotificationScrollViewModel notificationScrollViewModel, Continuation continuation) {
        super(6, continuation);
        this.this$0 = notificationScrollViewModel;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        float floatValue = ((Number) obj).floatValue();
        float floatValue2 = ((Number) obj2).floatValue();
        NotificationScrollViewModel$expandFraction$1 notificationScrollViewModel$expandFraction$1 = new NotificationScrollViewModel$expandFraction$1(this.this$0, (Continuation) obj6);
        notificationScrollViewModel$expandFraction$1.F$0 = floatValue;
        notificationScrollViewModel$expandFraction$1.F$1 = floatValue2;
        notificationScrollViewModel$expandFraction$1.L$0 = (ObservableTransitionState) obj4;
        notificationScrollViewModel$expandFraction$1.L$1 = (Set) obj5;
        return notificationScrollViewModel$expandFraction$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float f = this.F$0;
        float f2 = this.F$1;
        ObservableTransitionState observableTransitionState = (ObservableTransitionState) this.L$0;
        Set set = (Set) this.L$1;
        float f3 = 1.0f;
        if (observableTransitionState instanceof ObservableTransitionState.Idle) {
            NotificationScrollViewModel notificationScrollViewModel = this.this$0;
            SceneKey sceneKey = ((ObservableTransitionState.Idle) observableTransitionState).currentScene;
            notificationScrollViewModel.getClass();
            if (!NotificationScrollViewModel.expandedInScene(sceneKey) && !set.contains(Overlays.NotificationsShade)) {
                f3 = 0.0f;
            }
        } else if (observableTransitionState instanceof ObservableTransitionState.Transition.ChangeScene) {
            ObservableTransitionState.Transition.ChangeScene changeScene = (ObservableTransitionState.Transition.ChangeScene) observableTransitionState;
            this.this$0.getClass();
            SceneKey sceneKey2 = Scenes.Lockscreen;
            if (!ObservableTransitionState.isTransitioning$default(changeScene, sceneKey2, null, 2)) {
                SceneKey sceneKey3 = Scenes.Shade;
                if (changeScene.isTransitioning(sceneKey3, sceneKey2) || !NotificationScrollViewModel.expandedInScene(changeScene.fromScene) || !NotificationScrollViewModel.expandedInScene(changeScene.toScene)) {
                    SceneKey sceneKey4 = Scenes.Gone;
                    if (!changeScene.isTransitioning(sceneKey4, sceneKey3) && !changeScene.isTransitioning(sceneKey3, sceneKey4) && !changeScene.isTransitioning(sceneKey3, sceneKey2)) {
                        SceneKey sceneKey5 = Scenes.QuickSettings;
                        f = (changeScene.isTransitioning(sceneKey4, sceneKey5) || changeScene.isTransitioning(sceneKey5, sceneKey4)) ? RangesKt___RangesKt.coerceIn((f2 / 0.3f) - 0.5f, 0.0f, 1.0f) : 0.0f;
                    }
                    f3 = f;
                }
            }
            f = 1.0f;
            f3 = f;
        } else if (observableTransitionState instanceof ObservableTransitionState.Transition.ShowOrHideOverlay) {
            f3 = NotificationScrollViewModel.access$expandFractionDuringOverlayTransition(this.this$0, (ObservableTransitionState.Transition) observableTransitionState, ((ObservableTransitionState.Transition.ShowOrHideOverlay) observableTransitionState).currentScene, set, f);
        } else {
            if (!(observableTransitionState instanceof ObservableTransitionState.Transition.ReplaceOverlay)) {
                throw new NoWhenBranchMatchedException();
            }
            f3 = NotificationScrollViewModel.access$expandFractionDuringOverlayTransition(this.this$0, (ObservableTransitionState.Transition) observableTransitionState, ((ObservableTransitionState.Transition.ReplaceOverlay) observableTransitionState).currentScene, set, f);
        }
        return new Float(f3);
    }
}
