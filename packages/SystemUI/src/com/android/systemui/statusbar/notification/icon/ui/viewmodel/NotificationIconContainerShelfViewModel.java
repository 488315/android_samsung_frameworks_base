package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import com.android.systemui.statusbar.notification.icon.domain.interactor.NotificationIconsInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationIconContainerShelfViewModel {
    public NotificationIconContainerShelfViewModel(CoroutineContext coroutineContext, NotificationIconsInteractor notificationIconsInteractor) {
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 filteredNotifSet$default = NotificationIconsInteractor.filteredNotifSet$default(notificationIconsInteractor, false, false, 127);
        FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerShelfViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                    /*
                        r9 = this;
                        boolean r0 = r11 instanceof com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerShelfViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r11
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
                        r0.<init>(r11)
                    L18:
                        java.lang.Object r11 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r11)
                        goto L7c
                    L27:
                        java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                        java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                        r9.<init>(r10)
                        throw r9
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r11)
                        java.util.Set r10 = (java.util.Set) r10
                        kotlin.collections.builders.ListBuilder r11 = kotlin.collections.CollectionsKt__CollectionsJVMKt.createListBuilder()
                        java.util.Iterator r10 = r10.iterator()
                        r2 = 0
                    L3d:
                        boolean r4 = r10.hasNext()
                        if (r4 == 0) goto L66
                        java.lang.Object r4 = r10.next()
                        com.android.systemui.statusbar.notification.shared.ActiveNotificationModel r4 = (com.android.systemui.statusbar.notification.shared.ActiveNotificationModel) r4
                        android.graphics.drawable.Icon r5 = r4.shelfIcon
                        if (r5 == 0) goto L59
                        java.lang.String r6 = r4.groupKey
                        if (r6 == 0) goto L59
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconInfo r7 = new com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconInfo
                        java.lang.String r8 = r4.key
                        r7.<init>(r5, r8, r6)
                        goto L5a
                    L59:
                        r7 = 0
                    L5a:
                        if (r7 == 0) goto L3d
                        r11.add(r7)
                        boolean r4 = r4.isAmbient
                        if (r4 != 0) goto L3d
                        int r2 = r2 + 1
                        goto L3d
                    L66:
                        kotlin.collections.builders.ListBuilder r10 = r11.build()
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData r11 = new com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData$LimitType r4 = com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData.LimitType.MaximumIndex
                        r11.<init>(r10, r2, r4)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                        java.lang.Object r9 = r9.emit(r11, r0)
                        if (r9 != r1) goto L7c
                        return r1
                    L7c:
                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                        return r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerShelfViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineContext), -1, 2));
    }
}
