package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class HeadsUpNotificationInteractor {
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 canShowHeadsUp;
    public final ChannelFlowTransformLatest hasPinnedRows;
    public final HeadsUpRepository headsUpRepository;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isHeadsUpOrAnimatingAway;
    public final ChannelFlowTransformLatest pinnedHeadsUpRows;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 showHeadsUpStatusBar;
    public final StateFlowImpl topHeadsUpRow;

    public HeadsUpNotificationInteractor(HeadsUpRepository headsUpRepository, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, NotificationsKeyguardInteractor notificationsKeyguardInteractor, ShadeInteractor shadeInteractor) {
        HeadsUpManagerPhone headsUpManagerPhone = (HeadsUpManagerPhone) headsUpRepository;
        StateFlowImpl stateFlowImpl = headsUpManagerPhone.mTopHeadsUpRow;
        FlowKt.transformLatest(headsUpManagerPhone.mHeadsUpNotificationRows, new HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$1(null));
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(headsUpManagerPhone.mHeadsUpNotificationRows, new HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$2(null));
        new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(transformLatest, headsUpManagerPhone.mHeadsUpAnimatingAway, new HeadsUpNotificationInteractor$isHeadsUpOrAnimatingAway$1(null));
        this.showHeadsUpStatusBar = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(transformLatest, FlowKt.combine(deviceEntryFaceAuthInteractor.isBypassEnabled(), ((ShadeInteractorImpl) shadeInteractor).isShadeFullyCollapsed, keyguardTransitionInteractor.currentKeyguardState, notificationsKeyguardInteractor.areNotificationsFullyHidden, new HeadsUpNotificationInteractor$canShowHeadsUp$1(null)), new HeadsUpNotificationInteractor$showHeadsUpStatusBar$1(null));
    }
}
