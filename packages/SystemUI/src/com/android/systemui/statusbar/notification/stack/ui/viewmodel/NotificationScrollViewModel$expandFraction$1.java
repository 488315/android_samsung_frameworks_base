package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

final class NotificationScrollViewModel$expandFraction$1 extends SuspendLambda implements Function6 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;
    final /* synthetic */ NotificationScrollViewModel this$0;

    public NotificationScrollViewModel$expandFraction$1(NotificationScrollViewModel notificationScrollViewModel, Continuation continuation) {
        super(6, continuation);
        this.this$0 = notificationScrollViewModel;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        float floatValue = ((Number) obj).floatValue();
        float floatValue2 = ((Number) obj3).floatValue();
        NotificationScrollViewModel$expandFraction$1 notificationScrollViewModel$expandFraction$1 = new NotificationScrollViewModel$expandFraction$1(this.this$0, (Continuation) obj6);
        notificationScrollViewModel$expandFraction$1.F$0 = floatValue;
        notificationScrollViewModel$expandFraction$1.L$0 = (ShadeMode) obj2;
        notificationScrollViewModel$expandFraction$1.F$1 = floatValue2;
        notificationScrollViewModel$expandFraction$1.L$1 = (ObservableTransitionState) obj4;
        notificationScrollViewModel$expandFraction$1.L$2 = (SceneKey) obj5;
        return notificationScrollViewModel$expandFraction$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0060, code lost:
    
        if (r9.this$0.sceneInteractor.isSceneInFamily(r2.toScene, r6) == false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0076, code lost:
    
        if (r9.this$0.sceneInteractor.isSceneInFamily(r2.toScene, r6) == false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0029, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(((com.android.compose.animation.scene.ObservableTransitionState.Idle) r2).currentScene, com.android.systemui.scene.shared.model.Scenes.Lockscreen) != false) goto L8;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r9.label
            if (r0 != 0) goto Lc7
            kotlin.ResultKt.throwOnFailure(r10)
            float r10 = r9.F$0
            java.lang.Object r0 = r9.L$0
            com.android.systemui.shade.shared.model.ShadeMode r0 = (com.android.systemui.shade.shared.model.ShadeMode) r0
            float r1 = r9.F$1
            java.lang.Object r2 = r9.L$1
            com.android.compose.animation.scene.ObservableTransitionState r2 = (com.android.compose.animation.scene.ObservableTransitionState) r2
            java.lang.Object r3 = r9.L$2
            com.android.compose.animation.scene.SceneKey r3 = (com.android.compose.animation.scene.SceneKey) r3
            boolean r4 = r2 instanceof com.android.compose.animation.scene.ObservableTransitionState.Idle
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r4 == 0) goto L2e
            com.android.compose.animation.scene.ObservableTransitionState$Idle r2 = (com.android.compose.animation.scene.ObservableTransitionState.Idle) r2
            com.android.compose.animation.scene.SceneKey r9 = r2.currentScene
            com.android.compose.animation.scene.SceneKey r0 = com.android.systemui.scene.shared.model.Scenes.Lockscreen
            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r0)
            if (r9 == 0) goto Lbb
        L2b:
            r10 = r5
            goto Lbb
        L2e:
            boolean r4 = r2 instanceof com.android.compose.animation.scene.ObservableTransitionState.Transition
            if (r4 == 0) goto Lc1
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel r4 = r9.this$0
            com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.scene.shared.model.SceneFamilies.NotifShade
            com.android.compose.animation.scene.ObservableTransitionState$Transition r2 = (com.android.compose.animation.scene.ObservableTransitionState.Transition) r2
            com.android.compose.animation.scene.SceneKey r7 = r2.fromScene
            com.android.systemui.scene.domain.interactor.SceneInteractor r4 = r4.sceneInteractor
            boolean r4 = r4.isSceneInFamily(r7, r6)
            if (r4 == 0) goto L4a
            com.android.compose.animation.scene.SceneKey r4 = r2.toScene
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r3)
            if (r4 != 0) goto L2b
        L4a:
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel r4 = r9.this$0
            com.android.compose.animation.scene.SceneKey r7 = r2.fromScene
            com.android.systemui.scene.domain.interactor.SceneInteractor r4 = r4.sceneInteractor
            boolean r4 = r4.isSceneInFamily(r7, r3)
            if (r4 == 0) goto L62
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel r4 = r9.this$0
            com.android.compose.animation.scene.SceneKey r7 = r2.toScene
            com.android.systemui.scene.domain.interactor.SceneInteractor r4 = r4.sceneInteractor
            boolean r4 = r4.isSceneInFamily(r7, r6)
            if (r4 != 0) goto L2b
        L62:
            com.android.compose.animation.scene.SceneKey r4 = r2.fromScene
            com.android.compose.animation.scene.SceneKey r7 = com.android.systemui.scene.shared.model.Scenes.Lockscreen
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r7)
            if (r4 == 0) goto L78
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel r4 = r9.this$0
            com.android.compose.animation.scene.SceneKey r8 = r2.toScene
            com.android.systemui.scene.domain.interactor.SceneInteractor r4 = r4.sceneInteractor
            boolean r4 = r4.isSceneInFamily(r8, r6)
            if (r4 != 0) goto L2b
        L78:
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel r4 = r9.this$0
            com.android.compose.animation.scene.SceneKey r8 = r2.fromScene
            com.android.systemui.scene.domain.interactor.SceneInteractor r4 = r4.sceneInteractor
            boolean r4 = r4.isSceneInFamily(r8, r6)
            if (r4 == 0) goto L8d
            com.android.compose.animation.scene.SceneKey r4 = r2.toScene
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r7)
            if (r4 == 0) goto L8d
            goto L2b
        L8d:
            com.android.systemui.shade.shared.model.ShadeMode$Split r4 = com.android.systemui.shade.shared.model.ShadeMode.Split.INSTANCE
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r4)
            if (r0 != 0) goto Lbb
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel r0 = r9.this$0
            com.android.compose.animation.scene.SceneKey r4 = com.android.systemui.scene.shared.model.SceneFamilies.Home
            com.android.compose.animation.scene.SceneKey r6 = r2.fromScene
            com.android.systemui.scene.domain.interactor.SceneInteractor r0 = r0.sceneInteractor
            boolean r0 = r0.isSceneInFamily(r6, r4)
            if (r0 == 0) goto Lbb
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel r9 = r9.this$0
            com.android.compose.animation.scene.SceneKey r0 = r2.toScene
            com.android.systemui.scene.domain.interactor.SceneInteractor r9 = r9.sceneInteractor
            boolean r9 = r9.isSceneInFamily(r0, r3)
            if (r9 == 0) goto Lbb
            r9 = 1050253722(0x3e99999a, float:0.3)
            float r1 = r1 / r9
            r9 = 1056964608(0x3f000000, float:0.5)
            float r1 = r1 - r9
            r9 = 0
            float r10 = kotlin.ranges.RangesKt___RangesKt.coerceIn(r1, r9, r5)
        Lbb:
            java.lang.Float r9 = new java.lang.Float
            r9.<init>(r10)
            return r9
        Lc1:
            kotlin.NoWhenBranchMatchedException r9 = new kotlin.NoWhenBranchMatchedException
            r9.<init>()
            throw r9
        Lc7:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$expandFraction$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
