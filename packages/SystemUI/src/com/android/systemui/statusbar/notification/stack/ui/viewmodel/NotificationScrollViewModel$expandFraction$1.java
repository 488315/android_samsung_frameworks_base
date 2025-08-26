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
        float fFloatValue = ((Number) obj).floatValue();
        float fFloatValue2 = ((Number) obj2).floatValue();
        NotificationScrollViewModel$expandFraction$1 notificationScrollViewModel$expandFraction$1 = new NotificationScrollViewModel$expandFraction$1(this.this$0, (Continuation) obj6);
        notificationScrollViewModel$expandFraction$1.F$0 = fFloatValue;
        notificationScrollViewModel$expandFraction$1.F$1 = fFloatValue2;
        notificationScrollViewModel$expandFraction$1.L$0 = (ObservableTransitionState) obj4;
        notificationScrollViewModel$expandFraction$1.L$1 = (Set) obj5;
        return notificationScrollViewModel$expandFraction$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0067  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float fCoerceIn = this.F$0;
        float f = this.F$1;
        ObservableTransitionState observableTransitionState = (ObservableTransitionState) this.L$0;
        Set set = (Set) this.L$1;
        float fAccess$expandFractionDuringOverlayTransition = 1.0f;
        if (observableTransitionState instanceof ObservableTransitionState.Idle) {
            NotificationScrollViewModel notificationScrollViewModel = this.this$0;
            SceneKey sceneKey = ((ObservableTransitionState.Idle) observableTransitionState).currentScene;
            notificationScrollViewModel.getClass();
            if (!NotificationScrollViewModel.expandedInScene(sceneKey) && !set.contains(Overlays.NotificationsShade)) {
                fAccess$expandFractionDuringOverlayTransition = 0.0f;
            }
        } else if (observableTransitionState instanceof ObservableTransitionState.Transition.ChangeScene) {
            ObservableTransitionState.Transition.ChangeScene changeScene = (ObservableTransitionState.Transition.ChangeScene) observableTransitionState;
            this.this$0.getClass();
            SceneKey sceneKey2 = Scenes.Lockscreen;
            if (ObservableTransitionState.isTransitioning$default(changeScene, sceneKey2, null, 2)) {
                fCoerceIn = 1.0f;
                fAccess$expandFractionDuringOverlayTransition = fCoerceIn;
            } else {
                SceneKey sceneKey3 = Scenes.Shade;
                if (changeScene.isTransitioning(sceneKey3, sceneKey2) || !NotificationScrollViewModel.expandedInScene(changeScene.fromScene) || !NotificationScrollViewModel.expandedInScene(changeScene.toScene)) {
                    SceneKey sceneKey4 = Scenes.Gone;
                    if (!changeScene.isTransitioning(sceneKey4, sceneKey3) && !changeScene.isTransitioning(sceneKey3, sceneKey4) && !changeScene.isTransitioning(sceneKey3, sceneKey2)) {
                        SceneKey sceneKey5 = Scenes.QuickSettings;
                        fCoerceIn = (changeScene.isTransitioning(sceneKey4, sceneKey5) || changeScene.isTransitioning(sceneKey5, sceneKey4)) ? RangesKt___RangesKt.coerceIn((f / 0.3f) - 0.5f, 0.0f, 1.0f) : 0.0f;
                    }
                }
                fAccess$expandFractionDuringOverlayTransition = fCoerceIn;
            }
        } else if (observableTransitionState instanceof ObservableTransitionState.Transition.ShowOrHideOverlay) {
            fAccess$expandFractionDuringOverlayTransition = NotificationScrollViewModel.access$expandFractionDuringOverlayTransition(this.this$0, (ObservableTransitionState.Transition) observableTransitionState, ((ObservableTransitionState.Transition.ShowOrHideOverlay) observableTransitionState).currentScene, set, fCoerceIn);
        } else {
            if (!(observableTransitionState instanceof ObservableTransitionState.Transition.ReplaceOverlay)) {
                throw new NoWhenBranchMatchedException();
            }
            fAccess$expandFractionDuringOverlayTransition = NotificationScrollViewModel.access$expandFractionDuringOverlayTransition(this.this$0, (ObservableTransitionState.Transition) observableTransitionState, ((ObservableTransitionState.Transition.ReplaceOverlay) observableTransitionState).currentScene, set, fCoerceIn);
        }
        return new Float(fAccess$expandFractionDuringOverlayTransition);
    }
}
