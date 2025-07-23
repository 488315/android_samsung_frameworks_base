package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationIconInteractor;
import com.android.systemui.statusbar.notification.icon.domain.interactor.StatusBarNotificationIconsInteractor;
import com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor;
import com.android.systemui.util.ui.AnimatedValueKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationIconContainerStatusBarViewModel {
    public final Flow animationsEnabled;
    public final CoroutineContext bgContext;
    public final DarkIconInteractor darkIconInteractor;
    public final Flow icons;
    public final Flow isolatedIcon;
    public final Flow isolatedIconLocation;
    public final int maxIcons;

    public NotificationIconContainerStatusBarViewModel(CoroutineContext coroutineContext, DarkIconInteractor darkIconInteractor, StatusBarNotificationIconsInteractor statusBarNotificationIconsInteractor, HeadsUpNotificationIconInteractor headsUpNotificationIconInteractor, KeyguardInteractor keyguardInteractor, Resources resources, ShadeInteractor shadeInteractor) {
        this.bgContext = coroutineContext;
        this.darkIconInteractor = darkIconInteractor;
        this.maxIcons = resources.getInteger(R.integer.max_notif_static_icons);
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        this.animationsEnabled = FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.flowOn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(shadeInteractorImpl.isShadeTouchable, keyguardInteractor.isKeyguardShowing, new NotificationIconContainerStatusBarViewModel$animationsEnabled$1(null)), coroutineContext), -1, 2));
        final Flow flow = statusBarNotificationIconsInteractor.statusBarNotifs;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ NotificationIconContainerStatusBarViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, NotificationIconContainerStatusBarViewModel notificationIconContainerStatusBarViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = notificationIconContainerStatusBarViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                    /*
                        r10 = this;
                        boolean r0 = r12 instanceof com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r12
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r12)
                    L18:
                        java.lang.Object r12 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r12)
                        goto L79
                    L27:
                        java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                        java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                        r10.<init>(r11)
                        throw r10
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r12)
                        java.util.Set r11 = (java.util.Set) r11
                        java.lang.Iterable r11 = (java.lang.Iterable) r11
                        java.util.ArrayList r5 = new java.util.ArrayList
                        r5.<init>()
                        java.util.Iterator r11 = r11.iterator()
                    L3f:
                        boolean r12 = r11.hasNext()
                        if (r12 == 0) goto L62
                        java.lang.Object r12 = r11.next()
                        com.android.systemui.statusbar.notification.shared.ActiveNotificationModel r12 = (com.android.systemui.statusbar.notification.shared.ActiveNotificationModel) r12
                        android.graphics.drawable.Icon r2 = r12.statusBarIcon
                        if (r2 == 0) goto L5b
                        java.lang.String r4 = r12.groupKey
                        if (r4 == 0) goto L5b
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconInfo r6 = new com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconInfo
                        java.lang.String r12 = r12.key
                        r6.<init>(r2, r12, r4)
                        goto L5c
                    L5b:
                        r6 = 0
                    L5c:
                        if (r6 == 0) goto L3f
                        r5.add(r6)
                        goto L3f
                    L62:
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel r11 = r10.this$0
                        int r6 = r11.maxIcons
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData r4 = new com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData
                        r8 = 4
                        r9 = 0
                        r7 = 0
                        r4.<init>(r5, r6, r7, r8, r9)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r10 = r10.$this_unsafeFlow
                        java.lang.Object r10 = r10.emit(r4, r0)
                        if (r10 != r1) goto L79
                        return r1
                    L79:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineContext), -1, 2));
        this.icons = distinctUntilChanged;
        this.isolatedIcon = AnimatedValueKt.toAnimatedValueFlow(com.android.systemui.util.kotlin.FlowKt.sample(com.android.systemui.util.kotlin.FlowKt.pairwise(FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.flowOn(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(headsUpNotificationIconInteractor.isolatedNotification, distinctUntilChanged, new NotificationIconContainerStatusBarViewModel$isolatedIcon$1(null))), coroutineContext), -1, 2)), null), shadeInteractorImpl.baseShadeInteractor.getShadeExpansion(), new NotificationIconContainerStatusBarViewModel$isolatedIcon$2(null)));
        this.isolatedIconLocation = FlowKt.distinctUntilChanged(FlowKt.buffer$default(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(headsUpNotificationIconInteractor.isolatedIconLocation), -1, 2));
    }
}
