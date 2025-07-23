package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaRouter;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import androidx.lifecycle.LifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
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
import com.android.systemui.qs.flags.QsInCompose;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tiles.dialog.CastDetailsViewModel;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import com.android.systemui.statusbar.policy.CastDevice;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class CastTile extends QSTileImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CastDetailsViewModel.Factory mCastDetailsViewModelFactory;
    public boolean mCastTransportAllowed;
    public final CastController mController;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public final AnonymousClass2 mHotspotCallback;
    public boolean mHotspotConnected;
    public final KeyguardStateController mKeyguard;
    public final CastTile$$ExternalSyntheticLambda0 mNetworkModelConsumer;
    public final ShadeDialogContextInteractor mShadeDialogContextInteractor;
    public final AnonymousClass1 mSignalCallback;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DialogHolder {
        public Dialog mDialog;

        public /* synthetic */ DialogHolder(int i) {
            this();
        }

        private DialogHolder() {
        }
    }

    static {
        new Intent("android.settings.CAST_SETTINGS");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda0, java.util.function.Consumer] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.qs.tiles.CastTile$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.qs.tiles.CastTile$2, java.lang.Object] */
    public CastTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, CastController castController, KeyguardStateController keyguardStateController, NetworkController networkController, HotspotController hotspotController, DialogTransitionAnimator dialogTransitionAnimator, ConnectivityRepository connectivityRepository, TileJavaAdapter tileJavaAdapter, FeatureFlags featureFlags, ShadeDialogContextInteractor shadeDialogContextInteractor, CastDetailsViewModel.Factory factory) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback(this, 0);
        ?? r2 = new Consumer() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CastTile castTile = CastTile.this;
                DefaultConnectionModel defaultConnectionModel = (DefaultConnectionModel) obj;
                int i = CastTile.$r8$clinit;
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
        this.mNetworkModelConsumer = r2;
        ?? r3 = new SignalCallback() { // from class: com.android.systemui.qs.tiles.CastTile.1
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
        this.mSignalCallback = r3;
        ?? r4 = new HotspotController.Callback() { // from class: com.android.systemui.qs.tiles.CastTile.2
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
        this.mHotspotCallback = r4;
        this.mController = castController;
        this.mKeyguard = keyguardStateController;
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mShadeDialogContextInteractor = shadeDialogContextInteractor;
        this.mCastDetailsViewModelFactory = factory;
        castController.getClass();
        castController.observe(this.mLifecycle, callback);
        keyguardStateController.getClass();
        keyguardStateController.observe(this.mLifecycle, callback);
        if (((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.SIGNAL_CALLBACK_DEPRECATION)) {
            ReadonlyStateFlow readonlyStateFlow = ((ConnectivityRepositoryImpl) connectivityRepository).defaultConnections;
            tileJavaAdapter.getClass();
            CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(getLifecycle()), null, null, new TileJavaAdapter$bind$1(this, readonlyStateFlow, r2, null), 7);
        } else {
            networkController.getClass();
            networkController.observe(this.mLifecycle, r3);
        }
        hotspotController.getClass();
        hotspotController.observe(this.mLifecycle, r4);
    }

    public final List getActiveDevices() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) ((CastControllerImpl) this.mController).getCastDevices();
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            CastDevice castDevice = (CastDevice) obj;
            if (castDevice.isCasting) {
                arrayList.add(castDevice);
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final boolean getDetailsViewModel(Consumer consumer) {
        handleClick$1(new CastTile$$ExternalSyntheticLambda2(this, consumer, this.mCastDetailsViewModelFactory.create(((ShadeDialogContextInteractorImpl) this.mShadeDialogContextInteractor).getContext(), 4), 0));
        return true;
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
        handleClick$1(new CastTile$$ExternalSyntheticLambda1(this, expandable, 0));
    }

    public final void handleClick$1(Runnable runnable) {
        if (((QSTile.BooleanState) getState()).state == 0) {
            return;
        }
        List activeDevices = getActiveDevices();
        ArrayList arrayList = (ArrayList) getActiveDevices();
        if (arrayList.isEmpty() || (((CastDevice) arrayList.get(0)).tag instanceof MediaRouter.RouteInfo)) {
            runnable.run();
        } else {
            ((CastControllerImpl) this.mController).stopCasting((CastDevice) ((ArrayList) activeDevices).get(0), 5);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleLongClick(Expandable expandable) {
        handleClick(expandable);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.DEBUG) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("handleSetListening ", this.TAG, z);
        }
        if (z) {
            return;
        }
        synchronized (((CastControllerImpl) this.mController).mDiscoveringLock) {
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        boolean z = true;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        String string = this.mContext.getString(R.string.quick_settings_cast_title);
        booleanState.label = string;
        booleanState.contentDescription = string;
        booleanState.stateDescription = "";
        booleanState.value = false;
        ArrayList arrayList = (ArrayList) ((CastControllerImpl) this.mController).getCastDevices();
        int size = arrayList.size();
        boolean z2 = false;
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            Object obj2 = arrayList.get(i);
            i++;
            CastDevice castDevice = (CastDevice) obj2;
            CastDevice.CastState castState = castDevice.state;
            if (castState == CastDevice.CastState.Connected) {
                booleanState.value = true;
                String str = castDevice.name;
                if (str == null) {
                    str = this.mContext.getString(R.string.quick_settings_cast_device_default_name);
                }
                booleanState.secondaryLabel = str;
                booleanState.stateDescription = ((Object) booleanState.stateDescription) + "," + this.mContext.getString(R.string.accessibility_cast_name, booleanState.label);
                z2 = false;
            } else if (castState == CastDevice.CastState.Connecting) {
                z2 = true;
            }
        }
        if (z2 && !booleanState.value) {
            booleanState.secondaryLabel = this.mContext.getString(R.string.quick_settings_connecting);
        }
        int i2 = booleanState.value ? R.drawable.ic_cast_connected : R.drawable.ic_cast;
        int i3 = QsInCompose.$r8$clinit;
        booleanState.icon = QSTileImpl.ResourceIcon.get(i2);
        if ((this.mCastTransportAllowed || this.mHotspotConnected) || booleanState.value) {
            boolean z3 = booleanState.value;
            booleanState.state = z3 ? 2 : 1;
            if (!z3) {
                booleanState.secondaryLabel = "";
            }
            booleanState.expandedAccessibilityClassName = Button.class.getName();
            ArrayList arrayList2 = (ArrayList) getActiveDevices();
            if (!arrayList2.isEmpty() && !(((CastDevice) arrayList2.get(0)).tag instanceof MediaRouter.RouteInfo)) {
                z = false;
            }
            booleanState.forceExpandIcon = z;
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
