package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SeenNotificationsInteractor {
    public final CoroutineDispatcher bgDispatcher;
    public final StateFlowImpl hasFilteredOutSeenNotifications;
    public final ActiveNotificationListRepository notificationListRepository;
    public final SecureSettings secureSettings;

    public SeenNotificationsInteractor(CoroutineDispatcher coroutineDispatcher, ActiveNotificationListRepository activeNotificationListRepository, SecureSettings secureSettings) {
        this.bgDispatcher = coroutineDispatcher;
        this.notificationListRepository = activeNotificationListRepository;
        this.secureSettings = secureSettings;
        this.hasFilteredOutSeenNotifications = activeNotificationListRepository.hasFilteredOutSeenNotifications;
    }

    public final Flow isLockScreenShowOnlyUnseenNotificationsEnabled() {
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SeenNotificationsInteractor$isLockScreenShowOnlyUnseenNotificationsEnabled$1(null), SettingsProxyExt.INSTANCE.observerFlow(this.secureSettings, -1, "lock_screen_show_only_unseen_notifications"));
        return FlowKt.buffer$default(FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor$isLockScreenShowOnlyUnseenNotificationsEnabled$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor$isLockScreenShowOnlyUnseenNotificationsEnabled$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SeenNotificationsInteractor this$0;

                /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor$isLockScreenShowOnlyUnseenNotificationsEnabled$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SeenNotificationsInteractor seenNotificationsInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = seenNotificationsInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor$isLockScreenShowOnlyUnseenNotificationsEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor$isLockScreenShowOnlyUnseenNotificationsEnabled$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor$isLockScreenShowOnlyUnseenNotificationsEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor$isLockScreenShowOnlyUnseenNotificationsEnabled$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor$isLockScreenShowOnlyUnseenNotificationsEnabled$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L52
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlin.Unit r6 = (kotlin.Unit) r6
                        com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor r6 = r5.this$0
                        com.android.systemui.util.settings.SecureSettings r6 = r6.secureSettings
                        java.lang.String r7 = "lock_screen_show_only_unseen_notifications"
                        r2 = -2
                        r4 = 0
                        int r6 = r6.getIntForUser(r7, r4, r2)
                        if (r6 != r3) goto L43
                        r4 = r3
                    L43:
                        java.lang.Boolean r6 = java.lang.Boolean.valueOf(r4)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L52
                        return r1
                    L52:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor$isLockScreenShowOnlyUnseenNotificationsEnabled$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), this.bgDispatcher), -1, 2);
    }
}
