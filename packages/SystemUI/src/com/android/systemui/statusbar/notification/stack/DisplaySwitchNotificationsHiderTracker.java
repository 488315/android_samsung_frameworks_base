package com.android.systemui.statusbar.notification.stack;

import com.android.internal.util.LatencyTracker;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class DisplaySwitchNotificationsHiderTracker {
    public final LatencyTracker latencyTracker;
    public final ShadeInteractor notificationsInteractor;

    /* renamed from: com.android.systemui.statusbar.notification.stack.DisplaySwitchNotificationsHiderTracker$trackNotificationHideTimeWhenVisible$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
            AnonymousClass2 anonymousClass2 = new AnonymousClass2((Continuation) obj3);
            anonymousClass2.Z$0 = zBooleanValue;
            anonymousClass2.Z$1 = zBooleanValue2;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(this.Z$0 && this.Z$1);
        }
    }

    public DisplaySwitchNotificationsHiderTracker(ShadeInteractor shadeInteractor, LatencyTracker latencyTracker) {
        this.notificationsInteractor = shadeInteractor;
        this.latencyTracker = latencyTracker;
    }

    public final Object trackNotificationHideTimeWhenVisible(Flow flow, Continuation continuation) {
        Object objCollect = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, ((ShadeInteractorImpl) this.notificationsInteractor).baseShadeInteractor.isAnyExpanded(), new AnonymousClass2(null))).collect(new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.DisplaySwitchNotificationsHiderTracker.trackNotificationHideTimeWhenVisible.3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation2) {
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker = DisplaySwitchNotificationsHiderTracker.this;
                if (zBooleanValue) {
                    displaySwitchNotificationsHiderTracker.latencyTracker.onActionStart(27);
                } else {
                    displaySwitchNotificationsHiderTracker.latencyTracker.onActionEnd(27);
                }
                return Unit.INSTANCE;
            }
        }, continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
