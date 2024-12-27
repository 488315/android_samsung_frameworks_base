package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

public final class KeyguardSecBottomAreaViewBinder$bind$1 implements KeyguardBottomAreaViewBinder.Binding {
    public final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
    public final /* synthetic */ DisposableHandle $disposableHandle;
    public final /* synthetic */ KeyguardSecBottomAreaView $view;
    public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;

    public KeyguardSecBottomAreaViewBinder$bind$1(MutableStateFlow mutableStateFlow, KeyguardSecBottomAreaView keyguardSecBottomAreaView, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, DisposableHandle disposableHandle) {
        this.$configurationBasedDimensions = mutableStateFlow;
        this.$view = keyguardSecBottomAreaView;
        this.$viewModel = keyguardBottomAreaViewModel;
        this.$disposableHandle = disposableHandle;
    }

    @Override // com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.Binding
    public final void destroy() {
        this.$disposableHandle.dispose();
    }

    @Override // com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.Binding
    public final void onConfigurationChanged() {
        StateFlowImpl stateFlowImpl = (StateFlowImpl) this.$configurationBasedDimensions;
        KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions copy$default = KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions.copy$default((KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions) stateFlowImpl.getValue());
        KeyguardSecBottomAreaView keyguardSecBottomAreaView = this.$view;
        keyguardSecBottomAreaView.updateShortcutPosition(copy$default);
        keyguardSecBottomAreaView.updateIndicationPosition(copy$default);
        stateFlowImpl.updateState(null, copy$default);
    }

    @Override // com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.Binding
    public final boolean shouldConstrainToTopOfLockIcon() {
        return ((KeyguardRepositoryImpl) this.$viewModel.bottomAreaInteractor.repository).keyguardUpdateMonitor.isUdfpsSupported();
    }

    public final void updateIndicationPosition() {
        StateFlowImpl stateFlowImpl = (StateFlowImpl) this.$configurationBasedDimensions;
        KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions copy$default = KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions.copy$default((KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions) stateFlowImpl.getValue());
        this.$view.updateIndicationPosition(copy$default);
        stateFlowImpl.updateState(null, copy$default);
    }

    public final void updateShortcutPosition() {
        StateFlowImpl stateFlowImpl = (StateFlowImpl) this.$configurationBasedDimensions;
        KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions copy$default = KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions.copy$default((KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions) stateFlowImpl.getValue());
        this.$view.updateShortcutPosition(copy$default);
        stateFlowImpl.updateState(null, copy$default);
    }
}
