package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$2;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$5;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationLoggerViewModel {
    public final ActiveNotificationsInteractor$special$$inlined$map$5 activeNotificationRanks;
    public final NotificationLoggerViewModel$special$$inlined$map$1 activeNotifications;
    public final ReadonlyStateFlow isLockscreenOrShadeInteractive;

    public NotificationLoggerViewModel(ActiveNotificationsInteractor activeNotificationsInteractor, KeyguardInteractor keyguardInteractor, WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor) {
        final ActiveNotificationsInteractor$special$$inlined$map$2 activeNotificationsInteractor$special$$inlined$map$2 = activeNotificationsInteractor.allRepresentativeNotifications;
        new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.Map r5 = (java.util.Map) r5
                        java.util.Collection r5 = r5.values()
                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                        java.util.List r5 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        ActiveNotificationsInteractor$special$$inlined$map$5 activeNotificationsInteractor$special$$inlined$map$5 = activeNotificationsInteractor.activeNotificationRanks;
        ReadonlyStateFlow readonlyStateFlow = windowRootViewVisibilityInteractor.isLockscreenOrShadeVisibleAndInteractive;
        Flow flow = keyguardInteractor.isKeyguardShowing;
    }
}
