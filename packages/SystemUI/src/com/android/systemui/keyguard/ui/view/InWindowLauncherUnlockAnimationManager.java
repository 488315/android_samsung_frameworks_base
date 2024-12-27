package com.android.systemui.keyguard.ui.view;

import android.view.View;
import com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor;
import com.android.systemui.keyguard.ui.binder.InWindowLauncherAnimationViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.InWindowLauncherAnimationViewModel;
import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController$Stub$Proxy;
import com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController;
import com.android.systemui.shared.system.smartspace.SmartspaceState;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class InWindowLauncherUnlockAnimationManager extends ISysuiUnlockAnimationController.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final InWindowLauncherUnlockAnimationInteractor interactor;
    public ILauncherUnlockAnimationController$Stub$Proxy launcherAnimationController;
    public View lockscreenSmartspace;
    public Float manualUnlockAmount;
    public boolean preparedForUnlock;
    public final CoroutineScope scope;
    public final InWindowLauncherAnimationViewModel viewModel;

    public InWindowLauncherUnlockAnimationManager(InWindowLauncherUnlockAnimationInteractor inWindowLauncherUnlockAnimationInteractor, InWindowLauncherAnimationViewModel inWindowLauncherAnimationViewModel, CoroutineScope coroutineScope) {
        this.interactor = inWindowLauncherUnlockAnimationInteractor;
        this.viewModel = inWindowLauncherAnimationViewModel;
        this.scope = coroutineScope;
    }

    @Override // com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController
    public final void onLauncherSmartspaceStateUpdated(SmartspaceState smartspaceState) {
        this.interactor.repository.launcherSmartspaceState.setValue(smartspaceState);
    }

    @Override // com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController
    public final void setLauncherUnlockController(String str, ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy) {
        this.interactor.repository.launcherActivityClass.updateState(null, str);
        this.launcherAnimationController = iLauncherUnlockAnimationController$Stub$Proxy;
        InWindowLauncherAnimationViewBinder.bind(this.viewModel, this, this.scope);
    }
}
