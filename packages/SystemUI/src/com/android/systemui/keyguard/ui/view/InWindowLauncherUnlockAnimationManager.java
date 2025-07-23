package com.android.systemui.keyguard.ui.view;

import android.os.DeadObjectException;
import android.util.Log;
import com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor;
import com.android.systemui.keyguard.ui.binder.InWindowLauncherAnimationViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.InWindowLauncherAnimationViewModel;
import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController$Stub$Proxy;
import com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController;
import com.android.systemui.shared.system.smartspace.SmartspaceState;
import kotlin.Unit;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InWindowLauncherUnlockAnimationManager extends ISysuiUnlockAnimationController.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final InWindowLauncherUnlockAnimationInteractor interactor;
    public ILauncherUnlockAnimationController$Stub$Proxy launcherAnimationController;
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
        this.interactor.repository.launcherActivityClass.setValue(str);
        this.launcherAnimationController = iLauncherUnlockAnimationController$Stub$Proxy;
        InWindowLauncherAnimationViewBinder.bind(this.viewModel, this, this.scope);
    }

    public final void setUnlockAmount() {
        this.preparedForUnlock = false;
        ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy = this.launcherAnimationController;
        if (iLauncherUnlockAnimationController$Stub$Proxy != null) {
            this.manualUnlockAmount = Float.valueOf(1.0f);
            try {
                iLauncherUnlockAnimationController$Stub$Proxy.setUnlockAmount(true);
                Unit unit = Unit.INSTANCE;
            } catch (DeadObjectException e) {
                Log.e(InWindowLauncherUnlockAnimationManagerKt.TAG, "DeadObjectException in setUnlockAmount(1.0, true)", e);
            }
        }
    }

    @Override // com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController
    public final void unlockAnimationReady() {
        Log.d(InWindowLauncherUnlockAnimationManagerKt.TAG, "unlockAnimationReady called");
    }
}
