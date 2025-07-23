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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
                /* JADX WARN: Code restructure failed: missing block: B:17:0x004c, code lost:
                
                    if ((r0 != null ? ((com.android.systemui.statusbar.phone.StatusBarNotificationPresenter) r0).mPanelExpansionInteractor.isFullyCollapsed() : true) != false) goto L24;
                 */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r3, kotlin.coroutines.Continuation r4) {
                    /*
                        r2 = this;
                        java.lang.Boolean r3 = (java.lang.Boolean) r3
                        boolean r3 = r3.booleanValue()
                        com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor r2 = com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor.this
                        if (r3 == 0) goto L68
                        com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository r3 = r2.windowRootViewVisibilityRepository
                        com.android.systemui.keyguard.data.repository.KeyguardRepository r4 = r2.keyguardRepository
                        com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl r4 = (com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl) r4
                        kotlinx.coroutines.flow.ReadonlyStateFlow r4 = r4.statusBarState
                        kotlinx.coroutines.flow.StateFlow r4 = r4.$$delegate_0
                        java.lang.Object r4 = r4.getValue()
                        com.android.systemui.keyguard.shared.model.StatusBarState r4 = (com.android.systemui.keyguard.shared.model.StatusBarState) r4
                        com.android.systemui.statusbar.NotificationPresenter r0 = r2.notificationPresenter
                        r1 = 1
                        if (r0 == 0) goto L28
                        com.android.systemui.statusbar.phone.StatusBarNotificationPresenter r0 = (com.android.systemui.statusbar.phone.StatusBarNotificationPresenter) r0
                        com.android.systemui.shade.domain.interactor.PanelExpansionInteractor r0 = r0.mPanelExpansionInteractor
                        boolean r0 = r0.isFullyCollapsed()
                        goto L29
                    L28:
                        r0 = r1
                    L29:
                        if (r0 != 0) goto L35
                        com.android.systemui.keyguard.shared.model.StatusBarState r0 = com.android.systemui.keyguard.shared.model.StatusBarState.SHADE
                        if (r4 == r0) goto L33
                        com.android.systemui.keyguard.shared.model.StatusBarState r0 = com.android.systemui.keyguard.shared.model.StatusBarState.SHADE_LOCKED
                        if (r4 != r0) goto L35
                    L33:
                        r4 = r1
                        goto L36
                    L35:
                        r4 = 0
                    L36:
                        com.android.systemui.statusbar.notification.headsup.HeadsUpManager r0 = r2.headsUpManager
                        com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl r0 = (com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl) r0
                        boolean r0 = r0.mHasPinnedNotification
                        if (r0 == 0) goto L4f
                        com.android.systemui.statusbar.NotificationPresenter r0 = r2.notificationPresenter
                        if (r0 == 0) goto L4b
                        com.android.systemui.statusbar.phone.StatusBarNotificationPresenter r0 = (com.android.systemui.statusbar.phone.StatusBarNotificationPresenter) r0
                        com.android.systemui.shade.domain.interactor.PanelExpansionInteractor r0 = r0.mPanelExpansionInteractor
                        boolean r0 = r0.isFullyCollapsed()
                        goto L4c
                    L4b:
                        r0 = r1
                    L4c:
                        if (r0 == 0) goto L4f
                        goto L55
                    L4f:
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor r2 = r2.activeNotificationsInteractor
                        int r1 = r2.getAllNotificationsCountValue()
                    L55:
                        r3.getClass()
                        com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$$ExternalSyntheticLambda0 r2 = new com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$$ExternalSyntheticLambda0
                        r2.<init>()
                        java.util.concurrent.Executor r3 = r3.uiBgExecutor
                        com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$executeServiceCallOnUiBg$1 r4 = new com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$executeServiceCallOnUiBg$1
                        r4.<init>()
                        r3.execute(r4)
                        goto L7c
                    L68:
                        com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository r2 = r2.windowRootViewVisibilityRepository
                        r2.getClass()
                        com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$$ExternalSyntheticLambda1 r3 = new com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$$ExternalSyntheticLambda1
                        r3.<init>()
                        java.util.concurrent.Executor r2 = r2.uiBgExecutor
                        com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$executeServiceCallOnUiBg$1 r4 = new com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$executeServiceCallOnUiBg$1
                        r4.<init>()
                        r2.execute(r4)
                    L7c:
                        kotlin.Unit r2 = kotlin.Unit.INSTANCE
                        return r2
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
