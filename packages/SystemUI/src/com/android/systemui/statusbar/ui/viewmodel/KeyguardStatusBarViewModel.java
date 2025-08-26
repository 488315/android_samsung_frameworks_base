package com.android.systemui.statusbar.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.statusbar.domain.interactor.KeyguardStatusBarInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class KeyguardStatusBarViewModel {
    public final Flow isBatteryCharging;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isKeyguardUserSwitcherEnabled;
    public final ReadonlyStateFlow isVisible;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 showingHeadsUpStatusBar;

    public KeyguardStatusBarViewModel(CoroutineScope coroutineScope, HeadsUpNotificationInteractor headsUpNotificationInteractor, SceneInteractor sceneInteractor, KeyguardInteractor keyguardInteractor, KeyguardStatusBarInteractor keyguardStatusBarInteractor, BatteryController batteryController) {
        Boolean bool = Boolean.FALSE;
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        this.showingHeadsUpStatusBar = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        this.isVisible = FlowKt.stateIn(FlowKt.combine(sceneInteractor.currentScene, sceneInteractor.currentOverlays, keyguardInteractor.isDozing, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, new KeyguardStatusBarViewModel$isVisible$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), bool);
        this.isBatteryCharging = FlowConflatedKt.conflatedCallbackFlow(new KeyguardStatusBarViewModel$isBatteryCharging$1(batteryController, null));
        this.isKeyguardUserSwitcherEnabled = keyguardStatusBarInteractor.isKeyguardUserSwitcherEnabled;
    }
}
