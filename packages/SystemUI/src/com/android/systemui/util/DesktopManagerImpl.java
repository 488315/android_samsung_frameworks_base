package com.android.systemui.util;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.controls.controller.SecDeviceControlsController;
import com.android.systemui.controls.controller.SecDeviceControlsControllerImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.android.systemui.statusbar.logging.IndicatorLogger;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder;
import com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.KeyguardConstants;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DesktopManagerImpl implements DesktopManager {
    private static final int DESKTOP_DUALVIEW = 712;
    private static final int DESKTOP_NONE = 710;
    private static final String DESKTOP_SETTINGS_KEY_TOUCH_PAD = "touchpad_enabled";
    private static final String DESKTOP_SETTINGS_METHOD_GET = "getSettings";
    private static final int DESKTOP_STANDALONE = 711;
    private static final String ENABLE_NEW_DEX_HOME = "enable_new_dex_home";
    private static final int MSG_GET_CONNECTED_DEVICES = 196608;
    private static final int MSG_MASK = -65536;
    private static final int MSG_REQUEST_PRIVACY_ITEM = 589824;
    private static final int MSG_REQUEST_STATUS_ICONS = 524288;
    private static final int MSG_REQUEST_UNLOCK = 65536;
    private static final int MSG_SHIFT = 16;
    private static final int MSG_SHOW_CONTROLS = 458752;
    private static final int MSG_SHOW_GLOBAL_ACTION_DIALOG = 327680;
    private static final String TAG = "DesktopManager";
    private final List<DesktopManager.Callback> mCallbacks;
    private final Context mContext;
    private final DesktopSystemUiBinder.Callback mDesktopBinderCallback;
    private SBluetoothControllerImpl.BluetoothDesktopCallback mDesktopBluetoothCallback;
    private final SemDesktopModeManager.DesktopModeListener mDesktopModeListener;
    private final ContentObserver mDesktopSettingsObserver;
    private List<StatusBarSignalPolicy.DesktopCallback> mDesktopStatusBarIconCallback;
    private final Lazy mDesktopSystemUiBinderLazy;
    private final Handler mHandler;
    private final IDesktopBarCallback mIDesktopCallback;
    private IndicatorLogger mIndicatorLogger;
    private boolean mIsTouchpadEnabled;
    private final KeyguardSecurityModel mKeyguardSecurityModel;
    private final Lazy mKeyguardViewControllerLazy;
    private final Lazy mSecDeviceControlsController;
    private SelectedUserInteractor mSelectedUserInteractor;
    private final SemDesktopModeManager mSemDesktopModeManager;
    private final KeyguardUpdateMonitor mUpdateMonitor;
    private final WakefulnessLifecycle mWakefulnessLifecycle;
    private static final boolean DEBUG = DeviceType.isEngOrUTBinary();
    private static final Uri DESKTOP_SETTINGS_URI = Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    private static final Uri DEX_SETTINGS_URI = Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    private int mDesktopMode = DESKTOP_NONE;
    private boolean mDexOccluded = false;
    public final WakefulnessLifecycle.Observer mObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.util.DesktopManagerImpl.1
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public void onFinishedWakingUp() {
            DesktopManagerImpl.this.notifyScreenState(true);
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public void onStartedGoingToSleep() {
            DesktopManagerImpl.this.notifyScreenState(false);
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public /* bridge */ /* synthetic */ void onFinishedGoingToSleep() {
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public /* bridge */ /* synthetic */ void onPostFinishedWakingUp() {
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public /* bridge */ /* synthetic */ void onStartedWakingUp() {
        }
    };
    private final KeyguardUpdateMonitorCallback mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.util.DesktopManagerImpl.2
        private boolean mTrustEnabled;
        private int mUserId = 0;

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
            if (DesktopManagerImpl.this.mUpdateMonitor.mKeyguardShowing) {
                DesktopManagerImpl.this.notifyShowKeyguard();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onTrustChanged(int i) {
            boolean userHasTrust = DesktopManagerImpl.this.mUpdateMonitor.getUserHasTrust(i);
            boolean isBiometricsAuthenticatedOnLock = DesktopManagerImpl.this.mUpdateMonitor.isBiometricsAuthenticatedOnLock();
            if (this.mTrustEnabled == userHasTrust && this.mUserId == i && !isBiometricsAuthenticatedOnLock) {
                return;
            }
            this.mTrustEnabled = userHasTrust;
            this.mUserId = i;
            DesktopManagerImpl.this.notifyTrustChanged(i, userHasTrust || isBiometricsAuthenticatedOnLock);
            if (DesktopManagerImpl.this.mUpdateMonitor.mKeyguardShowing) {
                DesktopManagerImpl.this.notifyShowKeyguard();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onFailedUnlockAttemptChanged() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onLocaleChanged() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onLockModeChanged() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onOfflineStateChanged() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onOwnerInfoChanged() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onRemoteLockInfoChanged() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onSystemDialogsShowing() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onUdfpsFingerDown() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onUdfpsFingerUp() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onUnlocking() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onBiometricLockoutChanged(boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onDlsViewModeChanged(int i) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onDualDARInnerLockscreenRequirementChanged(int i) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onEmergencyStateChanged(int i) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onFaceWidgetFullscreenModeChanged(boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onLockDisabledChanged(boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onPackageAdded(String str) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onPackageChanged(String str) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onPackageDataCleared(String str) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onPrimaryBouncerVisibilityChanged(boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onRefreshBatteryInfo(KeyguardBatteryStatus keyguardBatteryStatus) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onSimulationFailToUnlock(int i) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onTableModeChanged(boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onUSBRestrictionChanged(boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onUpdateCoverState(CoverState coverState) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onDualDarInnerLockScreenStateChanged(int i, boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onPackageRemoved(String str, boolean z) {
        }
    };

    /* renamed from: com.android.systemui.util.DesktopManagerImpl$8, reason: invalid class name */
    /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Invalid.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.None.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* renamed from: -$$Nest$mgetDesktopSettingsValue, reason: not valid java name */
    public static /* bridge */ /* synthetic */ String m2277$$Nest$mgetDesktopSettingsValue(DesktopManagerImpl desktopManagerImpl, String str) {
        return desktopManagerImpl.getDesktopSettingsValue(str, "false");
    }

    public DesktopManagerImpl(Context context, Lazy lazy, KeyguardUpdateMonitor keyguardUpdateMonitor, SelectedUserInteractor selectedUserInteractor, KeyguardSecurityModel keyguardSecurityModel, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy2, IndicatorLogger indicatorLogger, Lazy lazy3) {
        Handler handler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.util.DesktopManagerImpl.3
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what & DesktopManagerImpl.MSG_MASK;
                if (i == 65536) {
                    ((KeyguardViewController) DesktopManagerImpl.this.mKeyguardViewControllerLazy.get()).requestUnlock((String) message.obj);
                    return;
                }
                if (i == DesktopManagerImpl.MSG_GET_CONNECTED_DEVICES) {
                    if (DesktopManagerImpl.this.mDesktopBluetoothCallback != null) {
                        SBluetoothControllerImpl.AnonymousClass1 anonymousClass1 = (SBluetoothControllerImpl.AnonymousClass1) DesktopManagerImpl.this.mDesktopBluetoothCallback;
                        anonymousClass1.getClass();
                        Bundle bundle = new Bundle();
                        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
                        SBluetoothControllerImpl sBluetoothControllerImpl = SBluetoothControllerImpl.this;
                        if (sBluetoothControllerImpl.getConnectedDevicesForGroup() != null) {
                            arrayList = new ArrayList<>(sBluetoothControllerImpl.getConnectedDevicesForGroup());
                        }
                        bundle.putParcelableArrayList("list", arrayList);
                        sBluetoothControllerImpl.mDesktopManager.setConnectedDeviceListForGroup(bundle);
                        return;
                    }
                    return;
                }
                if (i == DesktopManagerImpl.MSG_SHOW_CONTROLS) {
                    if (DesktopManagerImpl.this.mSecDeviceControlsController != null) {
                        ((SecDeviceControlsControllerImpl) ((SecDeviceControlsController) DesktopManagerImpl.this.mSecDeviceControlsController.get())).start(null);
                    }
                } else if (i != 524288) {
                    if (i != DesktopManagerImpl.MSG_REQUEST_PRIVACY_ITEM) {
                        return;
                    }
                    DesktopManagerImpl.this.handleNotifyPrivacyItemStateRequested();
                } else if (DesktopManagerImpl.this.mDesktopStatusBarIconCallback != null) {
                    Iterator it = DesktopManagerImpl.this.mDesktopStatusBarIconCallback.iterator();
                    while (it.hasNext()) {
                        ((StatusBarSignalPolicy.DesktopCallback) it.next()).updateDesktopStatusBarIcons();
                    }
                }
            }
        };
        this.mHandler = handler;
        this.mDesktopBinderCallback = new DesktopSystemUiBinder.Callback() { // from class: com.android.systemui.util.DesktopManagerImpl.4
            @Override // com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder.Callback
            public void onServiceConnected() {
                Log.d(DesktopManagerImpl.TAG, "onServiceConnected");
                DesktopManagerImpl desktopManagerImpl = DesktopManagerImpl.this;
                desktopManagerImpl.notifyOccluded(desktopManagerImpl.mDexOccluded);
                if (DesktopManagerImpl.this.mUpdateMonitor.mKeyguardShowing) {
                    DesktopManagerImpl.this.notifyShowKeyguard();
                } else {
                    DesktopManagerImpl.this.notifyDismissKeyguard();
                }
                DesktopManagerImpl desktopManagerImpl2 = DesktopManagerImpl.this;
                desktopManagerImpl2.notifyKeyguardLockout(desktopManagerImpl2.mUpdateMonitor.getLockoutAttemptDeadline() > 0);
                Iterator it = DesktopManagerImpl.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((DesktopManager.Callback) it.next()).onServiceConnected();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder.Callback
            public void onServiceDisconnected() {
                Iterator it = DesktopManagerImpl.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((DesktopManager.Callback) it.next()).onServiceDisconnected();
                }
            }
        };
        this.mIDesktopCallback = new IDesktopBarCallback.Stub() { // from class: com.android.systemui.util.DesktopManagerImpl.5
            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public void getConnectedDeviceListForGroup() {
                Log.d(DesktopManagerImpl.TAG, "getConnectedDeviceListForGroup");
                DesktopManagerImpl.this.mHandler.removeMessages(DesktopManagerImpl.MSG_GET_CONNECTED_DEVICES);
                DesktopManagerImpl.this.mHandler.obtainMessage(DesktopManagerImpl.MSG_GET_CONNECTED_DEVICES).sendToTarget();
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public int getFailedUnlockAttempt() {
                return DesktopManagerImpl.this.mUpdateMonitor.getFailedUnlockAttempts(DesktopManagerImpl.this.mSelectedUserInteractor.getSelectedUserId());
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public long getLockoutAttemptDeadline() {
                return DesktopManagerImpl.this.mUpdateMonitor.getLockoutAttemptDeadline();
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public int getRemainingAttemptBeforeWipe() {
                return DesktopManagerImpl.this.mUpdateMonitor.getRemainingAttemptsBeforeWipe();
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public void requestPrivacyItems() {
                Log.d(DesktopManagerImpl.TAG, "requestPrivacyItems");
                DesktopManagerImpl.this.mHandler.removeMessages(DesktopManagerImpl.MSG_REQUEST_PRIVACY_ITEM);
                DesktopManagerImpl.this.mHandler.obtainMessage(DesktopManagerImpl.MSG_REQUEST_PRIVACY_ITEM).sendToTarget();
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public void requestStatusIcons() {
                Log.d(DesktopManagerImpl.TAG, "requestStatusIcons");
                DesktopManagerImpl.this.mHandler.removeMessages(524288);
                DesktopManagerImpl.this.mHandler.obtainMessage(524288).sendToTarget();
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public void requestUnlock(String str) {
                Log.d(DesktopManagerImpl.TAG, "requestUnlock called!");
                DesktopManagerImpl.this.mHandler.removeMessages(65536);
                DesktopManagerImpl.this.mHandler.obtainMessage(65536, str).sendToTarget();
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public void showControls() {
                Log.d(DesktopManagerImpl.TAG, "showControls");
                DesktopManagerImpl.this.mHandler.removeMessages(DesktopManagerImpl.MSG_SHOW_CONTROLS);
                DesktopManagerImpl.this.mHandler.obtainMessage(DesktopManagerImpl.MSG_SHOW_CONTROLS).sendToTarget();
            }
        };
        SemDesktopModeManager.DesktopModeListener desktopModeListener = new SemDesktopModeManager.DesktopModeListener() { // from class: com.android.systemui.util.DesktopManagerImpl.6
            public void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                Log.i(DesktopManagerImpl.TAG, "onDesktopModeStateChanged " + semDesktopModeState.toString());
                DesktopManagerImpl.this.updateDesktopMode(semDesktopModeState);
                DesktopManagerImpl.this.startSystemUIDesktopIfNeeded(semDesktopModeState, true);
                DesktopManagerImpl.this.controlDesktopSettingsObserver(semDesktopModeState);
                Iterator it = DesktopManagerImpl.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((DesktopManager.Callback) it.next()).onDesktopModeStateChanged(semDesktopModeState);
                }
            }
        };
        this.mDesktopModeListener = desktopModeListener;
        this.mCallbacks = new ArrayList();
        this.mDesktopSettingsObserver = new ContentObserver(handler) { // from class: com.android.systemui.util.DesktopManagerImpl.7
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                String lastPathSegment = uri.getLastPathSegment();
                if (lastPathSegment == null || !lastPathSegment.equals(DesktopManagerImpl.DESKTOP_SETTINGS_KEY_TOUCH_PAD)) {
                    return;
                }
                DesktopManagerImpl desktopManagerImpl = DesktopManagerImpl.this;
                desktopManagerImpl.mIsTouchpadEnabled = "true".equals(DesktopManagerImpl.m2277$$Nest$mgetDesktopSettingsValue(desktopManagerImpl, lastPathSegment));
            }
        };
        Log.i(TAG, "DesktopManagerImpl started");
        this.mContext = context;
        this.mKeyguardViewControllerLazy = lazy;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mKeyguardSecurityModel = keyguardSecurityModel;
        this.mDesktopSystemUiBinderLazy = lazy2;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mIndicatorLogger = indicatorLogger;
        this.mSecDeviceControlsController = lazy3;
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
        this.mSemDesktopModeManager = semDesktopModeManager;
        if (semDesktopModeManager == null || !Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            return;
        }
        semDesktopModeManager.registerListener(desktopModeListener);
        SemDesktopModeState desktopModeState = semDesktopModeManager.getDesktopModeState();
        updateDesktopMode(desktopModeState);
        startSystemUIDesktopIfNeeded(desktopModeState, false);
    }

    public void controlDesktopSettingsObserver(SemDesktopModeState semDesktopModeState) {
        if (semDesktopModeState.getState() == 50) {
            int i = semDesktopModeState.enabled;
            if (i == 4) {
                this.mContext.getContentResolver().registerContentObserver(DESKTOP_SETTINGS_URI, true, this.mDesktopSettingsObserver);
            } else if (i == 2) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mDesktopSettingsObserver);
            }
        }
    }

    private static Bundle getBouncerMessage(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(charSequence)) {
            bundle.putCharSequence(KeyguardConstants.UpdateType.BouncerTextKey.MSG, charSequence);
        }
        if (!TextUtils.isEmpty(charSequence2)) {
            bundle.putCharSequence(KeyguardConstants.UpdateType.BouncerTextKey.SUB_MSG, charSequence2);
        }
        if (!TextUtils.isEmpty(charSequence3)) {
            bundle.putCharSequence(KeyguardConstants.UpdateType.BouncerTextKey.POPUP_MSG, charSequence3);
        }
        return bundle;
    }

    private String getCallers() {
        return Debug.getCallers(3, " ");
    }

    private int getCurrentSecurityMode() {
        int i;
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        if (this.mUpdateMonitor.is2StepVerification()) {
            return 5;
        }
        KeyguardSecurityModel.SecurityMode currentSecurityMode = this.mUpdateMonitor.getCurrentSecurityMode();
        if (currentSecurityMode == KeyguardSecurityModel.SecurityMode.Invalid) {
            currentSecurityMode = this.mKeyguardSecurityModel.getSecurityMode(selectedUserId);
        }
        int i2 = AnonymousClass8.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[currentSecurityMode.ordinal()];
        if (i2 != 1) {
            i = 2;
            if (i2 == 2) {
                i = 1;
            } else if (i2 == 3) {
                i = 4;
            } else if (i2 != 4) {
                i = i2 != 5 ? 5 : 3;
            }
        } else {
            i = 0;
        }
        if (i == 5 || !(this.mUpdateMonitor.getUserHasTrust(selectedUserId) || this.mUpdateMonitor.isBiometricsAuthenticatedOnLock())) {
            return i;
        }
        return 1;
    }

    private String getDesktopSettingsValue(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("key", str);
        bundle.putString("def", str2);
        try {
            Bundle call = this.mContext.getContentResolver().call(DESKTOP_SETTINGS_URI, DESKTOP_SETTINGS_METHOD_GET, (String) null, bundle);
            if (call != null) {
                return call.getString(str);
            }
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Failed to get settings", e);
        }
        return str2;
    }

    private static Bundle getScreenState(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KeyguardConstants.UpdateType.ScreenStateKey.IS_SCREEN_ON, z);
        return bundle;
    }

    private static Bundle getTrustState(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KeyguardConstants.UpdateType.TrustStateKey.TRUST, z);
        return bundle;
    }

    public void handleNotifyPrivacyItemStateRequested() {
        Log.i(TAG, "handleNotifyPrivacyItemStateRequested");
        Iterator<DesktopManager.Callback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onPrivacyItemStateRequested();
        }
    }

    private boolean isNewDexHomeEnabled() {
        Bundle bundle = new Bundle(2);
        bundle.putString("key", ENABLE_NEW_DEX_HOME);
        bundle.putString("def", "false");
        Bundle call = this.mContext.getContentResolver().call(DEX_SETTINGS_URI, DESKTOP_SETTINGS_METHOD_GET, (String) null, bundle);
        if (call != null) {
            return Boolean.valueOf(call.getString(ENABLE_NEW_DEX_HOME)).booleanValue();
        }
        return false;
    }

    public /* synthetic */ void lambda$startSystemUIDesktopService$0() {
        this.mWakefulnessLifecycle.addObserver(this.mObserver);
        this.mUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
    }

    public /* synthetic */ void lambda$stopSystemUIDesktopService$1() {
        this.mWakefulnessLifecycle.removeObserver(this.mObserver);
        this.mUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
    }

    public void startSystemUIDesktopIfNeeded(SemDesktopModeState semDesktopModeState, boolean z) {
        if (!z) {
            if (semDesktopModeState.getState() == 40 && semDesktopModeState.getEnabled() == 2) {
                stopSystemUIDesktopService();
                return;
            } else {
                if (isDesktopMode()) {
                    startSystemUIDesktopService();
                    return;
                }
                return;
            }
        }
        if (isStandalone()) {
            if (semDesktopModeState.getEnabled() == 1 && semDesktopModeState.getState() == 30) {
                stopSystemUIDesktopService();
                return;
            } else {
                if (semDesktopModeState.getEnabled() == 4 && semDesktopModeState.getState() == 50) {
                    startSystemUIDesktopService();
                    return;
                }
                return;
            }
        }
        if (semDesktopModeState.getState() == 40) {
            if (semDesktopModeState.getEnabled() == 2) {
                stopSystemUIDesktopService();
            } else if (isDesktopMode()) {
                startSystemUIDesktopService();
            }
        }
    }

    private void startSystemUIDesktopService() {
        Log.i(TAG, "startSystemUIDesktopService");
        Log.i(TAG, getCallers());
        if (isNewDexHomeEnabled()) {
            return;
        }
        ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).start(this.mIDesktopCallback);
        ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).registerCallback(this.mDesktopBinderCallback);
        this.mHandler.post(new DesktopManagerImpl$$ExternalSyntheticLambda0(this, 1));
    }

    private void stopSystemUIDesktopService() {
        Log.i(TAG, "stopSystemUIDesktopService");
        Log.i(TAG, getCallers());
        ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).stop();
        ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).unregisterCallback(this.mDesktopBinderCallback);
        this.mHandler.post(new DesktopManagerImpl$$ExternalSyntheticLambda0(this, 0));
    }

    public void updateDesktopMode(SemDesktopModeState semDesktopModeState) {
        this.mDesktopMode = DESKTOP_NONE;
        if (semDesktopModeState.getEnabled() == 4) {
            int displayType = semDesktopModeState.getDisplayType();
            if (displayType == 101) {
                this.mDesktopMode = DESKTOP_STANDALONE;
            } else {
                if (displayType != 102) {
                    return;
                }
                this.mDesktopMode = DESKTOP_DUALVIEW;
            }
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void destroy() {
        SemDesktopModeManager semDesktopModeManager = this.mSemDesktopModeManager;
        if (semDesktopModeManager != null) {
            semDesktopModeManager.unregisterListener(this.mDesktopModeListener);
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public SemDesktopModeState getSemDesktopModeState() {
        SemDesktopModeManager semDesktopModeManager = this.mSemDesktopModeManager;
        if (semDesktopModeManager != null) {
            return semDesktopModeManager.getDesktopModeState();
        }
        return null;
    }

    @Override // com.android.systemui.util.DesktopManager
    public boolean isDesktopBarConnected() {
        return ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).isDesktopBarConnected();
    }

    @Override // com.android.systemui.util.DesktopManager
    public boolean isDesktopMode() {
        return this.mDesktopMode != DESKTOP_NONE;
    }

    @Override // com.android.systemui.util.DesktopManager
    public boolean isDualView() {
        return this.mDesktopMode == DESKTOP_DUALVIEW;
    }

    @Override // com.android.systemui.util.DesktopManager
    public boolean isStandalone() {
        return this.mDesktopMode == DESKTOP_STANDALONE;
    }

    @Override // com.android.systemui.util.DesktopManager
    public boolean isTouchpadEnabled() {
        return this.mIsTouchpadEnabled;
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyDismissKeyguard() {
        Log.i(TAG, "notifyDismissKeyguard()");
        if (((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).isDesktopBarConnected()) {
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).onDismiss();
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyKeyguardLockout(boolean z) {
        if (((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).isDesktopBarConnected() && isDualView()) {
            SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("notifyKeyguardLockout lockout=", TAG, z);
            if (z) {
                ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).updateLockoutWarningMessage();
            } else {
                ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).onLockout(z, null);
            }
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyOccluded(boolean z) {
        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("notifyOccluded() occluded=", TAG, z);
        if (((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).isDesktopBarConnected()) {
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).setOccluded(z);
        }
        this.mDexOccluded = z;
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyPrivacyItemsChanged(boolean z) {
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("notifyPrivacyItemsChanged() visible = ", " mDesktopMode = ", z);
        m.append(this.mDesktopMode);
        Log.i(TAG, m.toString());
        if (isDesktopMode()) {
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).notifyPrivacyItemsChanged(z);
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyScreenState(boolean z) {
        if (((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).isDesktopBarConnected()) {
            SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("notifyScreenState() isScreenOn=", TAG, z);
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).onUpdate(3, getScreenState(z));
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyShowKeyguard() {
        TooltipPopup$$ExternalSyntheticOutline0.m(getCurrentSecurityMode(), TAG, new StringBuilder("notifyShowKeyguard() security mode="));
        if (((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).isDesktopBarConnected()) {
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).onShow(getCurrentSecurityMode());
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyTrustChanged(int i, boolean z) {
        if (((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).isDesktopBarConnected() && isDualView()) {
            Log.i(TAG, "notifyTrustChanged hasTrust=" + z + " userId=" + i);
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).onUpdate(4, getTrustState(z));
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyUpdateBouncerMessage(int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        if (((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).isDesktopBarConnected() && isDualView()) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "notifyUpdateBouncerMessage type= ", "  msg=");
            m.append((Object) (charSequence != null ? charSequence : ""));
            m.append("  sub=");
            m.append((Object) (charSequence2 != null ? charSequence2 : ""));
            m.append("  popupMsg=");
            m.append((Object) (charSequence3 != null ? charSequence3 : ""));
            Log.i(TAG, m.toString());
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).onUpdate(i, getBouncerMessage(charSequence, charSequence2, charSequence3));
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void registerCallback(DesktopManager.Callback callback) {
        this.mCallbacks.add(callback);
    }

    @Override // com.android.systemui.util.DesktopManager
    public void removeDesktopStatusBarIconCallback(StatusBarSignalPolicy.DesktopCallback desktopCallback) {
        List<StatusBarSignalPolicy.DesktopCallback> list = this.mDesktopStatusBarIconCallback;
        if (list != null) {
            list.remove(desktopCallback);
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setAirplaneMode(boolean z, int i) {
        if (isDesktopMode()) {
            this.mIndicatorLogger.log("setAirplaneMode - visible:" + z + ",resId:" + i);
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).setAirplaneMode(z, i);
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setBtTetherIcon(boolean z, int i) {
        if (isDesktopMode()) {
            this.mIndicatorLogger.log("setBtTetherIcon - visible:" + z + ",iconId:" + i);
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).setBtTetherIcon(z, i);
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setConnectedDeviceListForGroup(Bundle bundle) {
        ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).setConnectedDeviceListForGroup(bundle);
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setDesktopBluetoothCallback(SBluetoothControllerImpl.BluetoothDesktopCallback bluetoothDesktopCallback) {
        this.mDesktopBluetoothCallback = bluetoothDesktopCallback;
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setDesktopStatusBarIconCallback(StatusBarSignalPolicy.DesktopCallback desktopCallback) {
        if (this.mDesktopStatusBarIconCallback == null) {
            this.mDesktopStatusBarIconCallback = new ArrayList();
        }
        this.mDesktopStatusBarIconCallback.add(desktopCallback);
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setMPTCPIcon(boolean z, int i, int i2, int i3) {
        if (isDesktopMode()) {
            IndicatorLogger indicatorLogger = this.mIndicatorLogger;
            StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("setMPTCPIcon - visible:", i, ",typeId: ", z, "gigaModeId:");
            m.append(i2);
            m.append(",activityId:");
            m.append(i3);
            indicatorLogger.log(m.toString());
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).setMPTCPIcon(z, i, i2, i3);
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setMobileIcon(boolean z, int i, int i2, int i3, int i4, boolean z2, int i5, int i6) {
        if (isDesktopMode()) {
            IndicatorLogger indicatorLogger = this.mIndicatorLogger;
            StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("setMobileIcon - visible:", i2, ",subId:", z, ",stengthId:");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i3, ",typeId:", i4, ",showTriangle:");
            m.append(z2);
            m.append(",activityId:");
            m.append(i5);
            m.append(",roamingId:");
            m.append(i6);
            indicatorLogger.log(m.toString());
            Bundle bundle = new Bundle();
            bundle.putBoolean("visible", z);
            bundle.putInt("slotId", i);
            bundle.putInt("subId", i2);
            bundle.putInt("strengthId", i3);
            bundle.putInt("typeId", i4);
            bundle.putBoolean("showTriangle", z2);
            bundle.putInt("activityId", i5);
            bundle.putInt("roamingId", i6);
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).setMobileIcon(bundle);
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setWifiIcon(boolean z, int i, int i2) {
        if (isDesktopMode()) {
            IndicatorLogger indicatorLogger = this.mIndicatorLogger;
            StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("setWifiIcon - visible:", i, ",resId:", z, ",activityId:");
            m.append(i2);
            indicatorLogger.log(m.toString());
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).setWifiIcon(z, i, i2);
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void unregisterCallback(DesktopManager.Callback callback) {
        this.mCallbacks.remove(callback);
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyKeyguardLockout(boolean z, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        if (((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).isDesktopBarConnected() && isDualView()) {
            StringBuilder m = RowView$$ExternalSyntheticOutline0.m("notifyKeyguardLockout lockout=", "  msg=", z);
            m.append((Object) (charSequence != null ? charSequence : ""));
            m.append("  sub=");
            m.append((Object) (charSequence2 != null ? charSequence2 : ""));
            m.append("  popupMsg=");
            m.append((Object) (charSequence3 != null ? charSequence3 : ""));
            Log.i(TAG, m.toString());
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).onLockout(z, getBouncerMessage(charSequence, charSequence2, charSequence3));
        }
    }
}
