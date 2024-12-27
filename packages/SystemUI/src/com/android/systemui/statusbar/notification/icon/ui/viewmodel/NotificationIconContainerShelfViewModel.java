package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import com.android.systemui.statusbar.notification.icon.domain.interactor.NotificationIconsInteractor;
import com.android.systemui.statusbar.notification.icon.domain.interactor.NotificationIconsInteractor$filteredNotifSet$1;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;

public final class NotificationIconContainerShelfViewModel {
    public NotificationIconContainerShelfViewModel(CoroutineContext coroutineContext, NotificationIconsInteractor notificationIconsInteractor) {
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine;
        combine = FlowKt.combine(notificationIconsInteractor.activeNotificationsInteractor.topLevelRepresentativeNotifications, notificationIconsInteractor.headsUpNotificationIconInteractor.isolatedNotification, notificationIconsInteractor.keyguardViewStateRepository.areNotificationsFullyHidden, new NotificationIconsInteractor$filteredNotifSet$1(notificationIconsInteractor, (r15 & 1) != 0 ? false : false, (r15 & 2) != 0, (r15 & 4) != 0, (r15 & 8) != 0, (r15 & 16) != 0, (r15 & 32) != 0, null));
        FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerShelfViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerShelfViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerShelfViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerShelfViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerShelfViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerShelfViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerShelfViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerShelfViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L72
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.util.Set r7 = (java.util.Set) r7
                        kotlin.collections.builders.ListBuilder r8 = new kotlin.collections.builders.ListBuilder
                        r8.<init>()
                        java.util.Iterator r7 = r7.iterator()
                        r2 = 0
                    L3e:
                        boolean r4 = r7.hasNext()
                        if (r4 == 0) goto L5c
                        java.lang.Object r4 = r7.next()
                        com.android.systemui.statusbar.notification.shared.ActiveNotificationModel r4 = (com.android.systemui.statusbar.notification.shared.ActiveNotificationModel) r4
                        android.graphics.drawable.Icon r5 = r4.shelfIcon
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconInfo r5 = com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewDataKt.toIconInfo(r4, r5)
                        if (r5 == 0) goto L3e
                        r8.add(r5)
                        boolean r4 = r4.isAmbient
                        if (r4 != 0) goto L3e
                        int r2 = r2 + 1
                        goto L3e
                    L5c:
                        kotlin.collections.builders.ListBuilder r7 = r8.build()
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData r8 = new com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData$LimitType r4 = com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData.LimitType.MaximumIndex
                        r8.<init>(r7, r2, r4)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L72
                        return r1
                    L72:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerShelfViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineContext), -1));
    }
}
