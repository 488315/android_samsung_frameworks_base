package com.android.systemui.p016qs.tiles;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRouter;
import android.media.projection.MediaProjectionInfo;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.android.systemui.p016qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CastTile extends SQSTileImpl {
    public final CastController mController;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final C22302 mHotspotCallback;
    public boolean mHotspotConnected;
    public final KeyguardStateController mKeyguard;
    public final C22291 mSignalCallback;
    public boolean mWifiConnected;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.qs.tiles.CastTile$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.qs.tiles.CastTile$2, java.lang.Object] */
    public CastTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, CastController castController, KeyguardStateController keyguardStateController, NetworkController networkController, HotspotController hotspotController, DialogLaunchAnimator dialogLaunchAnimator) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback(this, 0);
        ?? r2 = new SignalCallback() { // from class: com.android.systemui.qs.tiles.CastTile.1
            @Override // com.android.systemui.statusbar.connectivity.SignalCallback
            public final void setWifiIndicators(WifiIndicators wifiIndicators) {
                IconState iconState;
                boolean z = wifiIndicators.enabled && (iconState = wifiIndicators.qsIcon) != null && iconState.visible;
                CastTile castTile = CastTile.this;
                if (z != castTile.mWifiConnected) {
                    castTile.mWifiConnected = z;
                    if (castTile.mHotspotConnected) {
                        return;
                    }
                    castTile.refreshState(null);
                }
            }
        };
        this.mSignalCallback = r2;
        ?? r3 = new HotspotController.Callback() { // from class: com.android.systemui.qs.tiles.CastTile.2
            @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
            public final void onHotspotChanged(int i, boolean z) {
                boolean z2 = z && i > 0;
                CastTile castTile = CastTile.this;
                if (z2 != castTile.mHotspotConnected) {
                    castTile.mHotspotConnected = z2;
                    if (castTile.mWifiConnected) {
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
        this.mHotspotCallback = r3;
        this.mController = castController;
        this.mKeyguard = keyguardStateController;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        castController.getClass();
        castController.observe(((QSTileImpl) this).mLifecycle, callback);
        keyguardStateController.getClass();
        keyguardStateController.observe(((QSTileImpl) this).mLifecycle, callback);
        networkController.getClass();
        networkController.observe(((QSTileImpl) this).mLifecycle, r2);
        hotspotController.getClass();
        hotspotController.observe(((QSTileImpl) this).mLifecycle, r3);
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

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.CAST_SETTINGS");
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final int getMetricsCategory() {
        return 114;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_cast_title);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        if (((QSTile.BooleanState) this.mState).state == 0) {
            return;
        }
        List activeDevices = getActiveDevices();
        ArrayList arrayList = (ArrayList) getActiveDevices();
        if (arrayList.isEmpty() || (((CastController.CastDevice) arrayList.get(0)).tag instanceof MediaRouter.RouteInfo)) {
            if (((KeyguardStateControllerImpl) this.mKeyguard).mShowing) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CastTile castTile = CastTile.this;
                        castTile.getClass();
                        castTile.mUiHandler.post(new CastTile$$ExternalSyntheticLambda1(castTile, null));
                    }
                });
                return;
            } else {
                this.mUiHandler.post(new CastTile$$ExternalSyntheticLambda1(this, view));
                return;
            }
        }
        CastController.CastDevice castDevice = (CastController.CastDevice) ((ArrayList) activeDevices).get(0);
        CastControllerImpl castControllerImpl = (CastControllerImpl) this.mController;
        castControllerImpl.getClass();
        boolean z = castDevice.tag instanceof MediaProjectionInfo;
        if (CastControllerImpl.DEBUG) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("stopCasting isProjection=", z, "CastController");
        }
        if (!z) {
            castControllerImpl.mMediaRouter.getFallbackRoute().select();
            return;
        }
        MediaProjectionInfo mediaProjectionInfo = (MediaProjectionInfo) castDevice.tag;
        MediaProjectionManager mediaProjectionManager = castControllerImpl.mProjectionManager;
        if (Objects.equals(mediaProjectionManager.getActiveProjectionInfo(), mediaProjectionInfo)) {
            mediaProjectionManager.stopActiveProjection();
            return;
        }
        Log.w("CastController", "Projection is no longer active: " + mediaProjectionInfo);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleLongClick(View view) {
        handleClick(view);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (QSTileImpl.DEBUG) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("handleSetListening ", z, "CastTile");
        }
        if (z) {
            return;
        }
        CastControllerImpl castControllerImpl = (CastControllerImpl) this.mController;
        synchronized (castControllerImpl.mDiscoveringLock) {
            if (castControllerImpl.mDiscovering) {
                castControllerImpl.mDiscovering = false;
                if (CastControllerImpl.DEBUG) {
                    Log.d("CastController", "setDiscovering: false");
                }
                castControllerImpl.handleDiscoveryChangeLocked();
            }
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        Context context = this.mContext;
        String string = context.getString(R.string.quick_settings_cast_title);
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
                    str = context.getString(R.string.quick_settings_cast_device_default_name);
                }
                booleanState.secondaryLabel = str;
                booleanState.stateDescription = ((Object) booleanState.stateDescription) + "," + context.getString(R.string.accessibility_cast_name, booleanState.label);
                z = false;
            } else if (i == 1) {
                z = true;
            }
        }
        if (z && !booleanState.value) {
            booleanState.secondaryLabel = context.getString(R.string.quick_settings_connecting);
        }
        booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.value ? R.drawable.ic_cast_connected : R.drawable.ic_cast);
        if ((this.mWifiConnected || this.mHotspotConnected) || booleanState.value) {
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
            booleanState.secondaryLabel = context.getString(R.string.quick_settings_cast_no_wifi);
            booleanState.forceExpandIcon = false;
        }
        booleanState.stateDescription = ((Object) booleanState.stateDescription) + ", " + ((Object) booleanState.secondaryLabel);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        handleRefreshState(null);
        ((CastControllerImpl) this.mController).mMediaRouter.rebindAsUser(i);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.handlesLongClick = false;
        return booleanState;
    }
}
