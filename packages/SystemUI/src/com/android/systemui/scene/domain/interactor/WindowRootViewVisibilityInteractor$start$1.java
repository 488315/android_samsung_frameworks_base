package com.android.systemui.scene.domain.interactor;

import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class WindowRootViewVisibilityInteractor$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ WindowRootViewVisibilityInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WindowRootViewVisibilityInteractor$start$1(WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = windowRootViewVisibilityInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WindowRootViewVisibilityInteractor$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WindowRootViewVisibilityInteractor$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = windowRootViewVisibilityInteractor.isLockscreenOrShadeVisibleAndInteractive;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor$start$1.1
                /* JADX WARN: Code restructure failed: missing block: B:18:0x004d, code lost:
                
                    if ((r0 != null ? ((com.android.systemui.statusbar.phone.StatusBarNotificationPresenter) r0).mPanelExpansionInteractor.isFullyCollapsed() : true) != false) goto L28;
                 */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r4, kotlin.coroutines.Continuation r5) {
                    /*
                        r3 = this;
                        java.lang.Boolean r4 = (java.lang.Boolean) r4
                        boolean r4 = r4.booleanValue()
                        com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor r3 = com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor.this
                        if (r4 == 0) goto L62
                        com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository r4 = r3.windowRootViewVisibilityRepository
                        com.android.systemui.keyguard.data.repository.KeyguardRepository r5 = r3.keyguardRepository
                        com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl r5 = (com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl) r5
                        kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.statusBarState
                        kotlinx.coroutines.flow.StateFlow r5 = r5.$$delegate_0
                        java.lang.Object r5 = r5.getValue()
                        com.android.systemui.keyguard.shared.model.StatusBarState r5 = (com.android.systemui.keyguard.shared.model.StatusBarState) r5
                        com.android.systemui.statusbar.NotificationPresenter r0 = r3.notificationPresenter
                        r1 = 1
                        if (r0 == 0) goto L28
                        com.android.systemui.statusbar.phone.StatusBarNotificationPresenter r0 = (com.android.systemui.statusbar.phone.StatusBarNotificationPresenter) r0
                        com.android.systemui.shade.domain.interactor.PanelExpansionInteractor r0 = r0.mPanelExpansionInteractor
                        boolean r0 = r0.isFullyCollapsed()
                        goto L29
                    L28:
                        r0 = r1
                    L29:
                        r2 = 0
                        if (r0 != 0) goto L36
                        com.android.systemui.keyguard.shared.model.StatusBarState r0 = com.android.systemui.keyguard.shared.model.StatusBarState.SHADE
                        if (r5 == r0) goto L34
                        com.android.systemui.keyguard.shared.model.StatusBarState r0 = com.android.systemui.keyguard.shared.model.StatusBarState.SHADE_LOCKED
                        if (r5 != r0) goto L36
                    L34:
                        r5 = r1
                        goto L37
                    L36:
                        r5 = r2
                    L37:
                        com.android.systemui.statusbar.policy.HeadsUpManager r0 = r3.headsUpManager
                        com.android.systemui.statusbar.policy.BaseHeadsUpManager r0 = (com.android.systemui.statusbar.policy.BaseHeadsUpManager) r0
                        boolean r0 = r0.mHasPinnedNotification
                        if (r0 == 0) goto L50
                        com.android.systemui.statusbar.NotificationPresenter r0 = r3.notificationPresenter
                        if (r0 == 0) goto L4c
                        com.android.systemui.statusbar.phone.StatusBarNotificationPresenter r0 = (com.android.systemui.statusbar.phone.StatusBarNotificationPresenter) r0
                        com.android.systemui.shade.domain.interactor.PanelExpansionInteractor r0 = r0.mPanelExpansionInteractor
                        boolean r0 = r0.isFullyCollapsed()
                        goto L4d
                    L4c:
                        r0 = r1
                    L4d:
                        if (r0 == 0) goto L50
                        goto L5e
                    L50:
                        com.android.systemui.Flags.notificationsLiveDataStoreRefactor()
                        com.android.systemui.statusbar.notification.init.NotificationsController r3 = r3.notificationsController
                        if (r3 == 0) goto L5d
                        int r3 = r3.getActiveNotificationsCount()
                        r1 = r3
                        goto L5e
                    L5d:
                        r1 = r2
                    L5e:
                        r4.onLockscreenOrShadeInteractive(r1, r5)
                        goto L67
                    L62:
                        com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository r3 = r3.windowRootViewVisibilityRepository
                        r3.onLockscreenOrShadeNotInteractive()
                    L67:
                        kotlin.Unit r3 = kotlin.Unit.INSTANCE
                        return r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor$start$1.AnonymousClass1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            };
            this.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
