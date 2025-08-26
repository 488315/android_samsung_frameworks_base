package com.android.systemui.samsung.quicksetting.ui;

import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.samsung.quicksetting.SecQSPanelComposeAdapter;
import com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel;
import com.android.systemui.scene.ui.composable.Scene;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import javax.inject.Provider;
import kotlin.NotImplementedError;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class SecQuickSettingScene extends ExclusiveActivatable implements Scene {
    public final SecQSPanelComposeAdapter qsAdapter;
    public final Provider scenesProvider;
    public final ReadonlyStateFlow userActions;
    public final QuickSettingSceneViewModel viewModel;

    public SecQuickSettingScene(QuickSettingSceneViewModel quickSettingSceneViewModel, CoroutineScope coroutineScope, TintedIconManager.Factory factory, BatteryMeterViewController.Factory factory2, StatusBarIconController statusBarIconController, ShadeInteractor shadeInteractor, SecQSPanelComposeAdapter secQSPanelComposeAdapter, Provider provider) {
        this.viewModel = quickSettingSceneViewModel;
        this.qsAdapter = secQSPanelComposeAdapter;
        this.scenesProvider = provider;
        this.userActions = FlowKt.stateIn(quickSettingSceneViewModel.destinationScenes, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), MapsKt__MapsKt.emptyMap());
    }

    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    public final Object onActivated(Continuation continuation) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }
}
