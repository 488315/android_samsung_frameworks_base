package com.android.systemui.qs.tiles;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.controls.controller.SecDeviceControlsController;
import com.android.systemui.controls.controller.SecDeviceControlsControllerImpl;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.panels.SecSelectedComponentRepositoryImpl;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.controls.ui.SecControlsUiController;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SecDeviceControlsTile extends QSTileImpl {
    public final ControlsComponent mControlsComponent;
    public final AtomicBoolean mHasControlsApps;
    public final SecDeviceControlsTile$$ExternalSyntheticLambda1 mListingCallback;
    public final SecControlsUiController mSecControlsUiController;
    public final SecDeviceControlsController mSecDeviceControlsController;

    public SecDeviceControlsTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, SecDeviceControlsController secDeviceControlsController, ControlsComponent controlsComponent) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mHasControlsApps = new AtomicBoolean(false);
        ?? r1 = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.qs.tiles.SecDeviceControlsTile$$ExternalSyntheticLambda1
            @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
            public final void onServicesUpdated(List list) {
                SecDeviceControlsTile secDeviceControlsTile = SecDeviceControlsTile.this;
                if (secDeviceControlsTile.mHasControlsApps.compareAndSet(((ArrayList) list).isEmpty(), !r1.isEmpty())) {
                    Log.d(secDeviceControlsTile.TAG, "onServiceConnected serviceInfos = " + list);
                    secDeviceControlsTile.refreshState(null);
                }
            }
        };
        this.mListingCallback = r1;
        this.mSecDeviceControlsController = secDeviceControlsController;
        this.mControlsComponent = controlsComponent;
        this.mSecControlsUiController = (SecControlsUiController) controlsComponent.secControlsUiController.get();
        ControlsListingControllerImpl controlsListingControllerImpl = (ControlsListingControllerImpl) ((ControlsListingController) controlsComponent.controlsListingController.get());
        controlsListingControllerImpl.getClass();
        controlsListingControllerImpl.addCallback((ControlsListingController.ControlsListingCallback) r1);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final void destroy() {
        super.destroy();
        ((ControlsListingControllerImpl) ((ControlsListingController) this.mControlsComponent.controlsListingController.get())).removeCallback(this.mListingCallback);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.qspanel_quickcontrol_button_text);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.SecDeviceControlsTile$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SecDeviceControlsTile secDeviceControlsTile = SecDeviceControlsTile.this;
                Expandable expandable2 = expandable;
                secDeviceControlsTile.getClass();
                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_QUICK_TILE);
                ((SecDeviceControlsControllerImpl) secDeviceControlsTile.mSecDeviceControlsController).start(expandable2 != null ? expandable2.activityTransitionController(32) : null);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        SelectedComponentRepository.SelectedComponent selectedComponent;
        SelectedComponentRepository.SelectedComponent selectedComponent2;
        String str;
        ComponentName componentName;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        booleanState.state = 1;
        booleanState.label = this.mContext.getString(R.string.qspanel_devicecontrol_button_text);
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_smartthings);
        boolean z = this.mHasControlsApps.get();
        String str2 = this.TAG;
        if (!z) {
            Log.d(str2, "handleUpdateState hasControlsApps is false");
            return;
        }
        SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) this.mSecControlsUiController;
        selectedComponent = ((SecSelectedComponentRepositoryImpl) secControlsUiControllerImpl.secSelectedComponentRepository).getSelectedComponent(UserHandle.CURRENT);
        String packageName = (selectedComponent == null || (componentName = selectedComponent.componentName) == null) ? null : componentName.getPackageName();
        String str3 = "";
        if (packageName == null) {
            packageName = "";
        }
        if (!packageName.isEmpty() && !packageName.equals("com.samsung.android.oneconnect")) {
            selectedComponent2 = ((SecSelectedComponentRepositoryImpl) secControlsUiControllerImpl.secSelectedComponentRepository).getSelectedComponent(UserHandle.CURRENT);
            if (selectedComponent2 != null && (str = selectedComponent2.name) != null) {
                str3 = str;
            }
            if (!str3.isEmpty()) {
                booleanState.label = str3;
                booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_device_control);
            }
        }
        Log.d(str2, "handleUpdateState appName = " + ((Object) booleanState.label));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
