package com.android.systemui.statusbar.notification.stack;

import com.android.internal.util.LatencyTracker;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DisplaySwitchNotificationsHiderTracker {
    public final LatencyTracker latencyTracker;
    public final ShadeInteractor notificationsInteractor;

    public DisplaySwitchNotificationsHiderTracker(ShadeInteractor shadeInteractor, LatencyTracker latencyTracker) {
        this.notificationsInteractor = shadeInteractor;
        this.latencyTracker = latencyTracker;
    }

    public final Object trackNotificationHideTimeWhenVisible(Flow flow, Continuation continuation) {
        Object collect = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, ((ShadeInteractorImpl) this.notificationsInteractor).baseShadeInteractor.isAnyExpanded(), new DisplaySwitchNotificationsHiderTracker$trackNotificationHideTimeWhenVisible$2(null))).collect(new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.DisplaySwitchNotificationsHiderTracker$trackNotificationHideTimeWhenVisible$3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation2) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker = DisplaySwitchNotificationsHiderTracker.this;
                if (booleanValue) {
                    displaySwitchNotificationsHiderTracker.latencyTracker.onActionStart(27);
                } else {
                    displaySwitchNotificationsHiderTracker.latencyTracker.onActionEnd(27);
                }
                return Unit.INSTANCE;
            }
        }, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
