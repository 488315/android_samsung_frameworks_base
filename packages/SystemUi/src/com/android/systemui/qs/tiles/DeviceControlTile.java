package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.controls.controller.CustomDeviceControlsController;
import com.android.systemui.controls.controller.CustomDeviceControlsControllerImpl;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.ui.CustomControlsUiController;
import com.android.systemui.controls.ui.CustomControlsUiControllerImpl;
import com.android.systemui.controls.panels.CustomSelectedComponentRepository;
import com.android.systemui.controls.panels.CustomSelectedComponentRepositoryImpl;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DeviceControlTile extends QSTileImpl {
    public final ControlsComponent mControlsComponent;
    public final CustomDeviceControlsController mCustomDeviceControlsController;
    public final AtomicBoolean mHasControlsApps;
    public final DeviceControlTile$$ExternalSyntheticLambda0 mListingCallback;
    public final PanelInteractor mPanelInteractor;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.qs.tiles.DeviceControlTile$$ExternalSyntheticLambda0] */
    public DeviceControlTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, Resources resources, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, SettingsHelper settingsHelper, CustomDeviceControlsController customDeviceControlsController, PanelInteractor panelInteractor, ControlsComponent controlsComponent) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mHasControlsApps = new AtomicBoolean(false);
        this.mListingCallback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.qs.tiles.DeviceControlTile$$ExternalSyntheticLambda0
            @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
            public final void onServicesUpdated(List list) {
                DeviceControlTile deviceControlTile = DeviceControlTile.this;
                if (deviceControlTile.mHasControlsApps.compareAndSet(((ArrayList) list).isEmpty(), !r1.isEmpty())) {
                    Log.d(deviceControlTile.TAG, "onServiceConnected serviceInfos = " + list);
                    deviceControlTile.refreshState(null);
                }
            }
        };
        this.mCustomDeviceControlsController = customDeviceControlsController;
        this.mPanelInteractor = panelInteractor;
        this.mControlsComponent = controlsComponent;
        controlsComponent.getControlsListingController().ifPresent(new Consumer() { // from class: com.android.systemui.qs.tiles.DeviceControlTile$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DeviceControlTile deviceControlTile = DeviceControlTile.this;
                ControlsListingController controlsListingController = (ControlsListingController) obj;
                DeviceControlTile$$ExternalSyntheticLambda0 deviceControlTile$$ExternalSyntheticLambda0 = deviceControlTile.mListingCallback;
                controlsListingController.getClass();
                controlsListingController.observe(deviceControlTile.mLifecycle, deviceControlTile$$ExternalSyntheticLambda0);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.qspanel_quickcontrol_button_text);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        this.mPanelInteractor.collapsePanels();
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.DeviceControlTile$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ((CustomDeviceControlsControllerImpl) DeviceControlTile.this.mCustomDeviceControlsController).start();
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        String str;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        String str2 = this.TAG;
        Log.d(str2, "handleUpdateState");
        booleanState.isNonBGTile = true;
        booleanState.state = 1;
        booleanState.label = this.mContext.getString(R.string.qspanel_quickcontrol_button_text);
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_device_control);
        if (!this.mHasControlsApps.get()) {
            Log.d(str2, "handleUpdateState hasControlsApps is false");
            return;
        }
        CustomSelectedComponentRepository.CustomSelectedComponent selectedComponent = ((CustomSelectedComponentRepositoryImpl) ((CustomControlsUiControllerImpl) ((CustomControlsUiController) this.mControlsComponent.getCustomControlsUiController().get())).customSelectedComponentRepository).getSelectedComponent();
        if (selectedComponent == null || (str = selectedComponent.name) == null) {
            str = "";
        }
        booleanState.secondaryLabel = str;
        Log.d(str2, "handleUpdateState appName = " + ((Object) booleanState.secondaryLabel));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
