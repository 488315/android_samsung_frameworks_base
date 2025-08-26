package com.android.systemui.qs.tiles;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.qs.tiles.detail.BluetoothDetailAdapter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SBluetoothController;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.android.systemui.statusbar.policy.SatelliteEnabledListener;
import com.android.systemui.statusbar.policy.SatelliteModeObserverHelper;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class SBluetoothTile extends SQSTileImpl {
    public final ActivityStarter mActivityStarter;
    public final ArrayList mAvailableItemList;
    public int mBlueToothState;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final AnonymousClass4 mCallback;
    public final SBluetoothController mController;
    public final BluetoothDetailAdapter mDetailAdapter;
    public boolean mDetailListening;
    public final DisplayLifecycle mDisplayLifecycle;
    public boolean mDoStopScan;
    public final AnonymousClass2 mFoldStateChangedListener;
    public boolean mIsSatelliteModeOn;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public boolean mListening;
    public final AnonymousClass1 mSatelliteModeCallback;
    public final SatelliteModeObserverHelper mSatelliteModeObserverHelper;
    private final SettingsHelper mSettingsHelper;
    public final QSTile.BooleanState mStateBeforeClick;
    public SubscreenBluetoothTileReceiver mSubscreenBlueotoothTileReceiver;
    public final SubscreenQsPanelController mSubscreenQsPanelController;
    public static final boolean DEBUG = Log.isLoggable("SBluetoothTile", 3);
    public static final Intent BLUETOOTH_SETTINGS = new Intent("android.settings.BLUETOOTH_SETTINGS");

    public class SubscreenBluetoothTileReceiver extends BroadcastReceiver {
        public SubscreenBluetoothTileReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("BLUETOOTH_STATE_CHANGE")) {
                SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                boolean z = SBluetoothTile.DEBUG;
                ((SQSTileImpl) sBluetoothTile).mHandler.postDelayed(new SBluetoothTile$$ExternalSyntheticLambda0(this, 2), 500L);
                SBluetoothTile.this.handleClick(null);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.tiles.SBluetoothTile$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.qs.tiles.SBluetoothTile$2, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.qs.tiles.SBluetoothTile$4, java.lang.Object] */
    public SBluetoothTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, SettingsHelper settingsHelper, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, SBluetoothController sBluetoothController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, PanelInteractor panelInteractor, SatelliteModeObserverHelper satelliteModeObserverHelper, DisplayLifecycle displayLifecycle, BroadcastDispatcher broadcastDispatcher) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mDoStopScan = true;
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        this.mStateBeforeClick = booleanState;
        ArrayList arrayList = new ArrayList();
        this.mAvailableItemList = arrayList;
        this.mIsSatelliteModeOn = false;
        this.mSatelliteModeCallback = new SatelliteEnabledListener() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.1
            @Override // com.android.systemui.statusbar.policy.SatelliteEnabledListener
            public final void onSatelliteEnabledChanged(boolean z) {
                boolean z2 = SBluetoothTile.DEBUG;
                SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                String string = Settings.Global.getString(sBluetoothTile.mContext.getContentResolver(), "satellite_mode_radios");
                if (string == null || !string.contains("bluetooth")) {
                    sBluetoothTile.mIsSatelliteModeOn = false;
                } else {
                    sBluetoothTile.mIsSatelliteModeOn = z;
                }
                sBluetoothTile.mDetailAdapter.mIsSatelliteModeOn = sBluetoothTile.mIsSatelliteModeOn;
                sBluetoothTile.refreshState(null);
            }
        };
        this.mSubscreenQsPanelController = null;
        ?? r1 = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.2
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                if (QpRune.QUICK_SUBSCREEN_PANEL) {
                    return;
                }
                SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                if (z) {
                    sBluetoothTile.mSubscreenQsPanelController.getInstance(2).registerReceiver(false);
                } else {
                    sBluetoothTile.mSubscreenQsPanelController.getInstance(2).unRegisterReceiver(false);
                }
            }
        };
        this.mFoldStateChangedListener = r1;
        ?? r2 = new SBluetoothController.SCallback() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.4
            @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
            public final void onBluetoothDevicesChanged() {
                boolean z = SBluetoothTile.DEBUG;
                ((SQSTileImpl) SBluetoothTile.this).mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.4.1
                    @Override // java.lang.Runnable
                    public final void run() throws Throwable {
                        if (SBluetoothTile.DEBUG) {
                            Log.d("SBluetoothTile", "onBluetoothDevicesChanged ");
                        }
                        SBluetoothTile.this.refreshState(null);
                        SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                        if (sBluetoothTile.mDetailListening) {
                            boolean zBooleanValue = sBluetoothTile.mDetailAdapter.getToggleState().booleanValue();
                            EmergencyButtonController$$ExternalSyntheticOutline0.m("onBluetoothDevicesChanged update: ", "SBluetoothTile", zBooleanValue);
                            if (zBooleanValue) {
                                SBluetoothTile.this.mDetailAdapter.updateItems$1();
                                if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                                    SBluetoothTile.this.mDetailAdapter.updateMusicShareItems();
                                }
                            }
                        }
                    }
                });
            }

            @Override // com.android.systemui.statusbar.policy.SBluetoothController.SCallback
            public final void onBluetoothScanStateChanged(boolean z) {
                SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                sBluetoothTile.fireScanStateChanged(z);
                if (sBluetoothTile.mDetailListening && !z) {
                    ((SQSTileImpl) sBluetoothTile).mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.4.2
                        @Override // java.lang.Runnable
                        public final void run() throws Throwable {
                            boolean zBooleanValue = SBluetoothTile.this.mDetailAdapter.getToggleState().booleanValue();
                            if (SBluetoothTile.DEBUG) {
                                EmergencyButtonController$$ExternalSyntheticOutline0.m("onBluetoothScanStateChanged update = ", "SBluetoothTile", zBooleanValue);
                            }
                            if (zBooleanValue) {
                                SBluetoothTile.this.mDetailAdapter.updateItems$1();
                            }
                        }
                    });
                }
                if (z) {
                    return;
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BT_SCAN_DEVICE_NUMBER, sBluetoothTile.mAvailableItemList.size());
            }

            @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
            public final void onBluetoothStateChange(boolean z) throws InterruptedException {
                SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                sBluetoothTile.refreshState(null);
                int i = ((SBluetoothControllerImpl) sBluetoothTile.mController).mState;
                boolean z2 = i == 12;
                if (sBluetoothTile.mDetailListening) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onBluetoothStateChange isShowingDetail bluetoothState: ", "SBluetoothTile");
                    BluetoothDetailAdapter bluetoothDetailAdapter = sBluetoothTile.mDetailAdapter;
                    bluetoothDetailAdapter.getClass();
                    if (i == 10 || i == 12) {
                        bluetoothDetailAdapter.mTransientEnabling = false;
                    }
                    if (i == 12 || i == 10 || i == 13) {
                        if (z2) {
                            ((SBluetoothControllerImpl) sBluetoothTile.mController).setScanMode(23);
                        }
                        ((SBluetoothControllerImpl) sBluetoothTile.mController).scan(z2);
                        sBluetoothTile.mDetailAdapter.setItemsVisible(z2);
                        if (i == 10) {
                            sBluetoothTile.fireScanStateChanged(false);
                        }
                    }
                }
            }

            @Override // com.android.systemui.statusbar.policy.SBluetoothController.SCallback
            public final void onMusicShareDiscoveryStateChanged(boolean z) {
                SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                if (sBluetoothTile.mDetailListening) {
                    if (SBluetoothTile.DEBUG) {
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("onMusicShareDiscoveryStateChanged() : ", "SBluetoothTile", z);
                    }
                    ((SBluetoothControllerImpl) sBluetoothTile.mController).scanMusicShareDevices(z, sBluetoothTile.mDetailListening);
                }
            }

            @Override // com.android.systemui.statusbar.policy.SBluetoothController.SCallback
            public final void onMusicShareStateChanged() {
                SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                if (sBluetoothTile.mDetailListening) {
                    if (SBluetoothTile.DEBUG) {
                        Log.d("SBluetoothTile", "onMusicShareStateChanged()");
                    }
                    sBluetoothTile.mDetailAdapter.updateMusicShareItems();
                }
            }
        };
        this.mCallback = r2;
        this.mController = sBluetoothController;
        this.mActivityStarter = activityStarter;
        this.mSettingsHelper = settingsHelper;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mBlueToothState = ((SBluetoothControllerImpl) sBluetoothController).mState;
        this.mDetailAdapter = new BluetoothDetailAdapter(this.mContext, handler, activityStarter, settingsHelper, this.mDetailListening, arrayList, panelInteractor, sBluetoothController, keyguardStateController, keyguardUpdateMonitor, this);
        sBluetoothController.observe(((QSTileImpl) this).mLifecycle, r2);
        booleanState.spec = "bluetooth";
        this.mSatelliteModeObserverHelper = satelliteModeObserverHelper;
        boolean z = QpRune.QUICK_SUBSCREEN_PANEL;
        if (z) {
            this.mDisplayLifecycle = displayLifecycle;
            this.mSubscreenQsPanelController = (SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class);
            displayLifecycle.addObserver(r1);
        } else {
            this.mFoldStateChangedListener = null;
        }
        if (z) {
            this.mBroadcastDispatcher = broadcastDispatcher;
            if (this.mSubscreenBlueotoothTileReceiver != null || broadcastDispatcher == null) {
                return;
            }
            SubscreenBluetoothTileReceiver subscreenBluetoothTileReceiver = new SubscreenBluetoothTileReceiver();
            this.mSubscreenBlueotoothTileReceiver = subscreenBluetoothTileReceiver;
            broadcastDispatcher.registerReceiver(subscreenBluetoothTileReceiver, new IntentFilter("BLUETOOTH_STATE_CHANGE"), null, UserHandle.ALL, 2, "com.samsung.systemui.permission.BLUETOOTH_STATE_CHANGE");
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isBluetoothTileBlocked() || isBlockedByEASPolicy$1()) {
            super.showItPolicyToast();
            return null;
        }
        Log.d("SBluetoothTile", " getLongClickIntent is called:++++ ");
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 113;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_bluetooth_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        Log.d("SBluetoothTile", " handleClick is called:++++ ");
        QSTile.BooleanState booleanState = (QSTile.BooleanState) this.mState;
        boolean z = booleanState.value;
        SBluetoothController sBluetoothController = this.mController;
        if (!z && booleanState.state == 2) {
            booleanState.value = ((SBluetoothControllerImpl) sBluetoothController).mEnabled;
        }
        boolean z2 = booleanState.value;
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isBluetoothTileBlocked() || isBlockedByEASPolicy$1()) {
            if (QpRune.QUICK_SUBSCREEN_PANEL) {
                showItPolicyToastOnSubScreen(getSubScreenContext());
                return;
            } else {
                super.showItPolicyToast();
                return;
            }
        }
        boolean z3 = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        ActivityStarter activityStarter = this.mActivityStarter;
        if (z3) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled()) {
                if (!QpRune.QUICK_SUBSCREEN_PANEL || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
                    activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SBluetoothTile sBluetoothTile = this.f$0;
                            Expandable expandable2 = expandable;
                            boolean z4 = SBluetoothTile.DEBUG;
                            sBluetoothTile.handleClick(expandable2);
                        }
                    });
                    return;
                } else {
                    ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).showLockscreenOnCoverScreen(this.mContext, "BLUETOOTH_STATE_CHANGE");
                    return;
                }
            }
        }
        SBluetoothControllerImpl sBluetoothControllerImpl = (SBluetoothControllerImpl) sBluetoothController;
        if (!sBluetoothControllerImpl.canConfigBluetooth()) {
            activityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.BLUETOOTH_SETTINGS"), 0);
            return;
        }
        ((QSTile.BooleanState) this.mState).copyTo(this.mStateBeforeClick);
        refreshState(z2 ? null : SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
        sBluetoothControllerImpl.setBluetoothEnabled(!z2, true);
        if (!QpRune.QUICK_SUBSCREEN_PANEL || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
            return;
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_BLUETOOTH_COVER);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        SubscreenBluetoothTileReceiver subscreenBluetoothTileReceiver;
        BroadcastDispatcher broadcastDispatcher;
        super.handleDestroy();
        Log.d("SBluetoothTile", "handleDestroy");
        try {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.3
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayLifecycle displayLifecycle;
                    SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                    AnonymousClass2 anonymousClass2 = sBluetoothTile.mFoldStateChangedListener;
                    if (anonymousClass2 == null || (displayLifecycle = sBluetoothTile.mDisplayLifecycle) == null) {
                        return;
                    }
                    displayLifecycle.removeObserver(anonymousClass2);
                }
            });
        } catch (Exception e) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("destroy exception:", Log.getStackTraceString(e), "SBluetoothTile");
        }
        if (!QpRune.QUICK_SUBSCREEN_PANEL || (subscreenBluetoothTileReceiver = this.mSubscreenBlueotoothTileReceiver) == null || (broadcastDispatcher = this.mBroadcastDispatcher) == null) {
            return;
        }
        broadcastDispatcher.unregisterReceiver(subscreenBluetoothTileReceiver);
        this.mSubscreenBlueotoothTileReceiver = null;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        handleSecondaryClick();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        AnonymousClass1 anonymousClass1 = this.mSatelliteModeCallback;
        SatelliteModeObserverHelper satelliteModeObserverHelper = this.mSatelliteModeObserverHelper;
        if (z) {
            satelliteModeObserverHelper.addCallback(anonymousClass1);
        } else {
            satelliteModeObserverHelper.removeCallback(anonymousClass1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:94:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01b3  */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleUpdateState(QSTile.State state, Object obj) {
        List connectedDevices;
        int i;
        int i2;
        List<SemBluetoothCastDevice> connectedDevices2;
        String deviceName;
        String string;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_bluetooth");
        boolean z = obj == SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        SBluetoothControllerImpl sBluetoothControllerImpl = (SBluetoothControllerImpl) this.mController;
        int i3 = sBluetoothControllerImpl.mConnectionState;
        boolean z2 = i3 == 2;
        boolean z3 = i3 == 1;
        boolean z4 = sBluetoothControllerImpl.mEnabled;
        int i4 = sBluetoothControllerImpl.mState;
        boolean z5 = booleanState.value != z4;
        boolean z6 = this.mBlueToothState != i4;
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m(" handleUpdateState enabled = ", " connected = ", " connecting = ", z4, z2);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z3, " changedState =", z6, " bluetoothState = ");
        sbM.append(i4);
        sbM.append(" enabledChanging = ");
        sbM.append(z5);
        sbM.append(" state = ");
        sbM.append(booleanState);
        Log.d("SBluetoothTile", sbM.toString());
        if ((z5 || z6) && this.mDetailListening) {
            onToggleStateChange(z4);
        }
        this.mBlueToothState = i4;
        boolean z7 = z || z3 || sBluetoothControllerImpl.mState == 11 || this.mIsSatelliteModeOn;
        booleanState.dualTarget = true;
        booleanState.value = z4;
        booleanState.label = getTileLabel();
        boolean z8 = QpRune.QUICK_BLUETOOTH_MUSIC_SHARE;
        LocalBluetoothManager localBluetoothManager = sBluetoothControllerImpl.mLocalBluetoothManager;
        if (z8) {
            try {
                connectedDevices = localBluetoothManager.mLocalCastProfileManager.mAudioCastProfile.getConnectedDevices();
            } catch (Exception unused) {
                connectedDevices = null;
            }
            if (connectedDevices == null) {
                Log.d("SBluetoothTile", "getCastDeviceConnectedList return null : count");
                i = 0;
            } else {
                Iterator it = connectedDevices.iterator();
                i = 0;
                while (it.hasNext()) {
                    if (((SemBluetoothCastDevice) it.next()).getLocalDeviceRole() == 1) {
                        i++;
                    }
                }
            }
        } else {
            i = 0;
        }
        String string2 = "";
        if (z7) {
            booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_bluetooth_connecting);
            booleanState.contentDescription = this.mContext.getString(R.string.accessibility_quick_settings_bluetooth_connecting);
            if (z || sBluetoothControllerImpl.mState == 11 || this.mIsSatelliteModeOn) {
                booleanState.state = 0;
            }
        } else if (z4) {
            if (z2 || (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE && i > 0)) {
                booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_bluetooth_connected);
                List connectedDevicesForGroup = sBluetoothControllerImpl.getConnectedDevicesForGroup();
                int size = connectedDevicesForGroup != null ? connectedDevicesForGroup.size() : 0;
                boolean z9 = QpRune.QUICK_BLUETOOTH_MUSIC_SHARE;
                if (z9) {
                    if (DEBUG) {
                        SuggestionsAdapter$$ExternalSyntheticOutline0.m(size, i, "connectedDeviceCount = ", ", musicShareConnectedCount = ", "SBluetoothTile");
                    }
                    int i5 = size - i;
                    int i6 = i + i5;
                    if (i6 > 1) {
                        string2 = this.mContext.getString(R.string.quick_settings_bluetooth_connected_devices, Integer.valueOf(i6));
                        booleanState.contentDescription = this.mContext.getString(R.string.quick_settings_bluetooth_connected_devices, Integer.valueOf(i6));
                    } else if (i == 1) {
                        if (z9) {
                            try {
                                connectedDevices2 = localBluetoothManager.mLocalCastProfileManager.mAudioCastProfile.getConnectedDevices();
                            } catch (Exception unused2) {
                                connectedDevices2 = null;
                            }
                            if (connectedDevices2 != null) {
                                for (SemBluetoothCastDevice semBluetoothCastDevice : connectedDevices2) {
                                    if (semBluetoothCastDevice.getLocalDeviceRole() == 1) {
                                        deviceName = semBluetoothCastDevice.getDeviceName();
                                        break;
                                    }
                                }
                            } else {
                                Log.d("SBluetoothTile", "getCastDeviceConnectedList return null : label");
                            }
                            deviceName = null;
                            if (deviceName != null) {
                                Log.d("SBluetoothTile", "getMusicShareLabel return null.");
                                string = this.mContext.getString(R.string.quick_settings_bluetooth_label);
                            } else {
                                string = deviceName.toString();
                            }
                            string2 = string;
                            booleanState.contentDescription = this.mContext.getString(R.string.accessibility_bluetooth_name, string2);
                        } else {
                            deviceName = null;
                            if (deviceName != null) {
                            }
                            string2 = string;
                            booleanState.contentDescription = this.mContext.getString(R.string.accessibility_bluetooth_name, string2);
                        }
                    } else if (i5 == 1) {
                        string2 = sBluetoothControllerImpl.getLastDeviceName();
                        booleanState.contentDescription = this.mContext.getString(R.string.accessibility_bluetooth_name, string2);
                    } else {
                        Log.d("SBluetoothTile", "no connected device");
                    }
                } else if (size == 1) {
                    string2 = sBluetoothControllerImpl.getLastDeviceName();
                    booleanState.contentDescription = this.mContext.getString(R.string.accessibility_bluetooth_name, string2);
                } else if (size >= 2) {
                    string2 = this.mContext.getString(R.string.quick_settings_bluetooth_connected_devices, Integer.valueOf(size));
                    booleanState.contentDescription = this.mContext.getString(R.string.quick_settings_bluetooth_connected_devices, Integer.valueOf(size));
                } else {
                    Log.d("SBluetoothTile", "no connected device");
                }
                i2 = 2;
            } else {
                booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_bluetooth_on);
                StringBuilder sb = new StringBuilder();
                sb.append(this.mContext.getString(R.string.accessibility_quick_settings_bluetooth_on));
                sb.append(",");
                booleanState.contentDescription = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.accessibility_not_connected, sb);
                i2 = 2;
            }
            booleanState.state = i2;
        } else {
            booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_bluetooth_on);
            booleanState.contentDescription = this.mContext.getString(R.string.accessibility_quick_settings_bluetooth_off);
            booleanState.state = 1;
        }
        booleanState.secondaryLabel = string2;
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_bluetooth);
        if (z2) {
            booleanState.dualLabelContentDescription = this.mContext.getString(R.string.accessibility_bluetooth_name, booleanState.secondaryLabel);
        }
        booleanState.contentDescription = this.mContext.getString(R.string.quick_settings_bluetooth_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return ((SBluetoothControllerImpl) this.mController).mLocalBluetoothManager != null;
    }

    public final boolean isBlockedByEASPolicy$1() {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getApplicationContext().getSystemService("device_policy");
        return devicePolicyManager != null && devicePolicyManager.semGetAllowBluetoothMode(null) == 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public final void onToggleStateChange(boolean z) {
        if (((SBluetoothControllerImpl) this.mController).mState != 11) {
            fireToggleStateChanged(z);
        } else {
            ((SQSTileImpl) this).mHandler.postDelayed(new SBluetoothTile$$ExternalSyntheticLambda0(this, 0), 500L);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final void setDetailListening(boolean z) throws InterruptedException {
        if (this.mDetailListening == z) {
            return;
        }
        this.mDetailListening = z;
        LocalBluetoothManager localBluetoothManager = ((SBluetoothControllerImpl) this.mController).mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            synchronized (localBluetoothManager) {
                Log.d("LocalBluetoothManager", "QP setForegroundActivity :: isForeground = " + z);
                Settings.Secure.putIntForUser(localBluetoothManager.mContext.getContentResolver(), "bluetooth_settings_foreground_qp", z ? 1 : 0, -2);
            }
        }
        SBluetoothControllerImpl sBluetoothControllerImpl = (SBluetoothControllerImpl) this.mController;
        if (sBluetoothControllerImpl.mState != 12) {
            fireScanStateChanged(false);
        } else if (this.mDetailListening) {
            sBluetoothControllerImpl.getClass();
            Log.d("SBluetoothControllerImpl", " updateListDevices ");
            LocalBluetoothManager localBluetoothManager2 = sBluetoothControllerImpl.mLocalBluetoothManager;
            if (localBluetoothManager2 != null) {
                sBluetoothControllerImpl.stopScan();
                localBluetoothManager2.mCachedDeviceManager.clearNonBondedDevices();
                localBluetoothManager2.mEventManager.readRestoredDevices();
            }
            ((SBluetoothControllerImpl) this.mController).setScanMode(23);
            ((SBluetoothControllerImpl) this.mController).scan(true);
            if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                ((SBluetoothControllerImpl) this.mController).scanMusicShareDevices(true, this.mDetailListening);
            }
        } else {
            sBluetoothControllerImpl.setScanMode(21);
            if (this.mDoStopScan) {
                LocalBluetoothManager localBluetoothManager3 = ((SBluetoothControllerImpl) this.mController).mLocalBluetoothManager;
                if (!(localBluetoothManager3 != null ? localBluetoothManager3.semIsForegroundActivity() : false)) {
                    ((SBluetoothControllerImpl) this.mController).scan(false);
                    if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                        ((SBluetoothControllerImpl) this.mController).scanMusicShareDevices(false, this.mDetailListening);
                    }
                }
            }
        }
        this.mDoStopScan = true;
    }

    public final void handleSecondaryClick() {
        if (this.mIsSatelliteModeOn) {
            return;
        }
        boolean z = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
        ActivityStarter activityStarter = this.mActivityStarter;
        if (z) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled()) {
                activityStarter.postQSRunnableDismissingKeyguard(new SBluetoothTile$$ExternalSyntheticLambda0(this, 1));
                return;
            }
        }
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isBluetoothTileBlocked() || isBlockedByEASPolicy$1()) {
            super.showItPolicyToast();
        } else if (((SBluetoothControllerImpl) this.mController).canConfigBluetooth()) {
            showDetail$1(true);
        } else {
            activityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.BLUETOOTH_SETTINGS"), 0);
        }
    }
}
