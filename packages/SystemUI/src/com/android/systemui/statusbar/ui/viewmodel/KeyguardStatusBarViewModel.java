package com.android.systemui.statusbar.ui.viewmodel;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.statusbar.domain.interactor.KeyguardStatusBarInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class KeyguardStatusBarViewModel {
    public final Flow isBatteryCharging;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isKeyguardUserSwitcherEnabled;
    public final ReadonlyStateFlow isVisible;

    public KeyguardStatusBarViewModel(CoroutineScope coroutineScope, HeadsUpNotificationInteractor headsUpNotificationInteractor, KeyguardInteractor keyguardInteractor, KeyguardStatusBarInteractor keyguardStatusBarInteractor, BatteryController batteryController) {
        FlowKt.stateIn(FlowKt.combine(keyguardInteractor.isDozing, keyguardInteractor.statusBarState, headsUpNotificationInteractor.showHeadsUpStatusBar, new KeyguardStatusBarViewModel$isVisible$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        KeyguardStatusBarViewModel$isBatteryCharging$1 keyguardStatusBarViewModel$isBatteryCharging$1 = new KeyguardStatusBarViewModel$isBatteryCharging$1(batteryController, null);
        conflatedCallbackFlow.getClass();
        FlowConflatedKt.conflatedCallbackFlow(keyguardStatusBarViewModel$isBatteryCharging$1);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = keyguardStatusBarInteractor.isKeyguardUserSwitcherEnabled;
    }
}
