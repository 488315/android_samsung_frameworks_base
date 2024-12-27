package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaRouter;
import android.media.projection.MediaProjectionInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import androidx.lifecycle.LifecycleKt;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CastTile extends QSTileImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mCastTransportAllowed;
    public final CastController mController;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public final AnonymousClass2 mHotspotCallback;
    public boolean mHotspotConnected;
    public final KeyguardStateController mKeyguard;
    public final CastTile$$ExternalSyntheticLambda0 mNetworkModelConsumer;
    public final AnonymousClass1 mSignalCallback;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Callback implements CastController.Callback, KeyguardStateController.Callback {
        public /* synthetic */ Callback(CastTile castTile, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.policy.CastController.Callback
        public final void onCastDevicesChanged() {
            CastTile.this.refreshState(null);
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            CastTile.this.refreshState(null);
        }

        private Callback() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DialogHolder {
        public Dialog mDialog;

        private DialogHolder() {
        }

        public /* synthetic */ DialogHolder(int i) {
            this();
        }
    }

    static {
        new Intent("android.settings.CAST_SETTINGS");
    }

    public CastTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, CastController castController, KeyguardStateController keyguardStateController, NetworkController networkController, HotspotController hotspotController, DialogTransitionAnimator dialogTransitionAnimator, ConnectivityRepository connectivityRepository, TileJavaAdapter tileJavaAdapter, FeatureFlags featureFlags) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback(this, 0);
        Consumer consumer = new Consumer() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CastTile castTile = CastTile.this;
                DefaultConnectionModel defaultConnectionModel = (DefaultConnectionModel) obj;
                castTile.getClass();
                boolean z = (defaultConnectionModel.wifi.isDefault || defaultConnectionModel.ethernet.isDefault) && !defaultConnectionModel.mobile.isDefault;
                if (z != castTile.mCastTransportAllowed) {
                    castTile.mCastTransportAllowed = z;
                    if (castTile.mHotspotConnected) {
                        return;
                    }
                    castTile.refreshState(null);
                }
            }
        };
        SignalCallback signalCallback = new SignalCallback() { // from class: com.android.systemui.qs.tiles.CastTile.1
            @Override // com.android.systemui.statusbar.connectivity.SignalCallback
            public final void setWifiIndicators(WifiIndicators wifiIndicators) {
                IconState iconState;
                boolean z = wifiIndicators.enabled && (iconState = wifiIndicators.qsIcon) != null && iconState.visible;
                int i = CastTile.$r8$clinit;
                CastTile castTile = CastTile.this;
                if (z != castTile.mCastTransportAllowed) {
                    castTile.mCastTransportAllowed = z;
                    if (castTile.mHotspotConnected) {
                        return;
                    }
                    castTile.refreshState(null);
                }
            }
        };
        HotspotController.Callback callback2 = new HotspotController.Callback() { // from class: com.android.systemui.qs.tiles.CastTile.2
            @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
            public final void onHotspotChanged(int i, boolean z) {
                boolean z2 = z && i > 0;
                CastTile castTile = CastTile.this;
                if (z2 != castTile.mHotspotConnected) {
                    castTile.mHotspotConnected = z2;
                    if (castTile.mCastTransportAllowed) {
                        return;
                    }
                    castTile.refreshState(null);
                }
            }

            @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
            public final void onHotspotPrepared() {
            }

            @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
            public final void onUpdateConnectedDevices() {
            }
        };
        this.mController = castController;
        this.mKeyguard = keyguardStateController;
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        castController.getClass();
        castController.observe(this.mLifecycle, callback);
        keyguardStateController.getClass();
        keyguardStateController.observe(this.mLifecycle, callback);
        if (((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.SIGNAL_CALLBACK_DEPRECATION)) {
            ReadonlyStateFlow readonlyStateFlow = ((ConnectivityRepositoryImpl) connectivityRepository).defaultConnections;
            tileJavaAdapter.getClass();
            BuildersKt.launch$default(LifecycleKt.getCoroutineScope(getLifecycle()), null, null, new TileJavaAdapter$bind$1(this, readonlyStateFlow, consumer, null), 3);
        } else {
            networkController.getClass();
            networkController.observe(this.mLifecycle, signalCallback);
        }
        hotspotController.getClass();
        hotspotController.observe(this.mLifecycle, callback2);
    }

    public final List getActiveDevices() {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) ((CastControllerImpl) this.mController).getCastDevices()).iterator();
        while (it.hasNext()) {
            CastController.CastDevice castDevice = (CastController.CastDevice) it.next();
            int i = castDevice.state;
            if (i == 2 || i == 1) {
                arrayList.add(castDevice);
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.CAST_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 114;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_cast_title);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        if (((QSTile.BooleanState) this.mState).state == 0) {
            return;
        }
        List activeDevices = getActiveDevices();
        ArrayList arrayList = (ArrayList) getActiveDevices();
        if (arrayList.isEmpty() || (((CastController.CastDevice) arrayList.get(0)).tag instanceof MediaRouter.RouteInfo)) {
            if (((KeyguardStateControllerImpl) this.mKeyguard).mShowing) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CastTile castTile = CastTile.this;
                        castTile.getClass();
                        castTile.mUiHandler.post(new CastTile$$ExternalSyntheticLambda2(castTile, null));
                    }
                });
                return;
            } else {
                this.mUiHandler.post(new CastTile$$ExternalSyntheticLambda2(this, expandable));
                return;
            }
        }
        CastController.CastDevice castDevice = (CastController.CastDevice) ((ArrayList) activeDevices).get(0);
        CastControllerImpl castControllerImpl = (CastControllerImpl) this.mController;
        castControllerImpl.getClass();
        boolean z = castDevice.tag instanceof MediaProjectionInfo;
        if (CastControllerImpl.DEBUG) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("stopCasting isProjection=", "CastController", z);
        }
        if (!z) {
            castControllerImpl.mMediaRouter.getFallbackRoute().select();
            return;
        }
        MediaProjectionInfo mediaProjectionInfo = (MediaProjectionInfo) castDevice.tag;
        if (Objects.equals(castControllerImpl.mProjectionManager.getActiveProjectionInfo(), mediaProjectionInfo)) {
            castControllerImpl.mProjectionManager.stopActiveProjection();
            return;
        }
        Log.w("CastController", "Projection is no longer active: " + mediaProjectionInfo);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleLongClick(Expandable expandable) {
        handleClick(expandable);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.DEBUG) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("handleSetListening ", this.TAG, z);
        }
        if (z) {
            return;
        }
        CastControllerImpl castControllerImpl = (CastControllerImpl) this.mController;
        synchronized (castControllerImpl.mDiscoveringLock) {
            try {
                if (castControllerImpl.mDiscovering) {
                    castControllerImpl.mDiscovering = false;
                    if (CastControllerImpl.DEBUG) {
                        Log.d("CastController", "setDiscovering: false");
                    }
                    castControllerImpl.handleDiscoveryChangeLocked();
                }
            } finally {
            }
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        String string = this.mContext.getString(R.string.quick_settings_cast_title);
        booleanState.label = string;
        booleanState.contentDescription = string;
        booleanState.stateDescription = "";
        booleanState.value = false;
        Iterator it = ((ArrayList) ((CastControllerImpl) this.mController).getCastDevices()).iterator();
        boolean z = false;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            CastController.CastDevice castDevice = (CastController.CastDevice) it.next();
            int i = castDevice.state;
            if (i == 2) {
                booleanState.value = true;
                String str = castDevice.name;
                if (str == null) {
                    str = this.mContext.getString(R.string.quick_settings_cast_device_default_name);
                }
                booleanState.secondaryLabel = str;
                booleanState.stateDescription = ((Object) booleanState.stateDescription) + "," + this.mContext.getString(R.string.accessibility_cast_name, booleanState.label);
                z = false;
            } else if (i == 1) {
                z = true;
            }
        }
        if (z && !booleanState.value) {
            booleanState.secondaryLabel = this.mContext.getString(R.string.quick_settings_connecting);
        }
        booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.value ? R.drawable.ic_cast_connected : R.drawable.ic_cast);
        if ((this.mCastTransportAllowed || this.mHotspotConnected) || booleanState.value) {
            boolean z2 = booleanState.value;
            booleanState.state = z2 ? 2 : 1;
            if (!z2) {
                booleanState.secondaryLabel = "";
            }
            booleanState.expandedAccessibilityClassName = Button.class.getName();
            ArrayList arrayList = (ArrayList) getActiveDevices();
            booleanState.forceExpandIcon = arrayList.isEmpty() || (((CastController.CastDevice) arrayList.get(0)).tag instanceof MediaRouter.RouteInfo);
        } else {
            booleanState.state = 0;
            booleanState.secondaryLabel = this.mContext.getString(R.string.quick_settings_cast_no_network);
            booleanState.forceExpandIcon = false;
        }
        booleanState.stateDescription = ((Object) booleanState.stateDescription) + ", " + ((Object) booleanState.secondaryLabel);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        handleRefreshState(null);
        ((CastControllerImpl) this.mController).mMediaRouter.rebindAsUser(i);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.handlesLongClick = false;
        return booleanState;
    }
}
