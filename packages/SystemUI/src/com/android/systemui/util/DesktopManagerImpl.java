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
import android.util.Log;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.controls.controller.SecDeviceControlsController;
import com.android.systemui.controls.controller.SecDeviceControlsControllerImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.logging.IndicatorLogger;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder;
import com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
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
    private final Lazy mKeyguardViewControllerLazy;
    private final Lazy mSecDeviceControlsController;
    private final SemDesktopModeManager mSemDesktopModeManager;
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

    /* renamed from: -$$Nest$mgetDesktopSettingsValue, reason: not valid java name */
    public static /* bridge */ /* synthetic */ String m3136$$Nest$mgetDesktopSettingsValue(DesktopManagerImpl desktopManagerImpl, String str) {
        return desktopManagerImpl.getDesktopSettingsValue(str, "false");
    }

    public DesktopManagerImpl(Context context, Lazy lazy, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy2, IndicatorLogger indicatorLogger, Lazy lazy3) {
        Handler handler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.util.DesktopManagerImpl.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what & DesktopManagerImpl.MSG_MASK;
                if (i != DesktopManagerImpl.MSG_GET_CONNECTED_DEVICES) {
                    if (i != DesktopManagerImpl.MSG_SHOW_CONTROLS) {
                        if (i != DesktopManagerImpl.MSG_REQUEST_PRIVACY_ITEM) {
                            return;
                        }
                        DesktopManagerImpl.this.handleNotifyPrivacyItemStateRequested();
                        return;
                    } else {
                        if (DesktopManagerImpl.this.mSecDeviceControlsController != null) {
                            ((SecDeviceControlsControllerImpl) ((SecDeviceControlsController) DesktopManagerImpl.this.mSecDeviceControlsController.get())).start(null);
                            return;
                        }
                        return;
                    }
                }
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
                }
            }
        };
        this.mHandler = handler;
        this.mDesktopBinderCallback = new DesktopSystemUiBinder.Callback() { // from class: com.android.systemui.util.DesktopManagerImpl.3
            public void onServiceConnected() {
                Log.d(DesktopManagerImpl.TAG, "onServiceConnected");
                DesktopManagerImpl desktopManagerImpl = DesktopManagerImpl.this;
                desktopManagerImpl.notifyOccluded(desktopManagerImpl.mDexOccluded);
                Iterator it = DesktopManagerImpl.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((DesktopManager.Callback) it.next()).onServiceConnected();
                }
            }

            public void onServiceDisconnected() {
                Iterator it = DesktopManagerImpl.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((DesktopManager.Callback) it.next()).onServiceDisconnected();
                }
            }
        };
        this.mIDesktopCallback = new IDesktopBarCallback.Stub() { // from class: com.android.systemui.util.DesktopManagerImpl.4
            public void getConnectedDeviceListForGroup() {
                Log.d(DesktopManagerImpl.TAG, "getConnectedDeviceListForGroup");
                DesktopManagerImpl.this.mHandler.removeMessages(DesktopManagerImpl.MSG_GET_CONNECTED_DEVICES);
                DesktopManagerImpl.this.mHandler.obtainMessage(DesktopManagerImpl.MSG_GET_CONNECTED_DEVICES).sendToTarget();
            }

            public int getFailedUnlockAttempt() {
                return 0;
            }

            public long getLockoutAttemptDeadline() {
                return 0L;
            }

            public int getRemainingAttemptBeforeWipe() {
                return 0;
            }

            public void requestPrivacyItems() {
                Log.d(DesktopManagerImpl.TAG, "requestPrivacyItems");
                DesktopManagerImpl.this.mHandler.removeMessages(DesktopManagerImpl.MSG_REQUEST_PRIVACY_ITEM);
                DesktopManagerImpl.this.mHandler.obtainMessage(DesktopManagerImpl.MSG_REQUEST_PRIVACY_ITEM).sendToTarget();
            }

            public void requestStatusIcons() {
                Log.d(DesktopManagerImpl.TAG, "requestStatusIcons");
                DesktopManagerImpl.this.mHandler.removeMessages(524288);
                DesktopManagerImpl.this.mHandler.obtainMessage(524288).sendToTarget();
            }

            public void requestUnlock(String str) {
                Log.d(DesktopManagerImpl.TAG, "requestUnlock called!");
                DesktopManagerImpl.this.mHandler.removeMessages(65536);
                DesktopManagerImpl.this.mHandler.obtainMessage(65536, str).sendToTarget();
            }

            public void showControls() {
                Log.d(DesktopManagerImpl.TAG, "showControls");
                DesktopManagerImpl.this.mHandler.removeMessages(DesktopManagerImpl.MSG_SHOW_CONTROLS);
                DesktopManagerImpl.this.mHandler.obtainMessage(DesktopManagerImpl.MSG_SHOW_CONTROLS).sendToTarget();
            }
        };
        this.mDesktopModeListener = new SemDesktopModeManager.DesktopModeListener() { // from class: com.android.systemui.util.DesktopManagerImpl.5
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
        this.mCallbacks = new ArrayList();
        this.mDesktopSettingsObserver = new ContentObserver(handler) { // from class: com.android.systemui.util.DesktopManagerImpl.6
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                String lastPathSegment = uri.getLastPathSegment();
                if (lastPathSegment == null || !lastPathSegment.equals(DesktopManagerImpl.DESKTOP_SETTINGS_KEY_TOUCH_PAD)) {
                    return;
                }
                DesktopManagerImpl desktopManagerImpl = DesktopManagerImpl.this;
                desktopManagerImpl.mIsTouchpadEnabled = "true".equals(DesktopManagerImpl.m3136$$Nest$mgetDesktopSettingsValue(desktopManagerImpl, lastPathSegment));
            }
        };
        Log.i(TAG, "DesktopManagerImpl started");
        this.mContext = context;
        this.mKeyguardViewControllerLazy = lazy;
        this.mDesktopSystemUiBinderLazy = lazy2;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mSecDeviceControlsController = lazy3;
        this.mSemDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
    }

    /* JADX INFO: Access modifiers changed from: private */
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

    private String getCallers() {
        return Debug.getCallers(3, " ");
    }

    private String getDesktopSettingsValue(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("key", str);
        bundle.putString("def", str2);
        try {
            Bundle bundleCall = this.mContext.getContentResolver().call(DESKTOP_SETTINGS_URI, DESKTOP_SETTINGS_METHOD_GET, (String) null, bundle);
            if (bundleCall != null) {
                return bundleCall.getString(str);
            }
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Failed to get settings", e);
        }
        return str2;
    }

    private static Bundle getScreenState(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isScreenOn", z);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
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
        Bundle bundleCall = this.mContext.getContentResolver().call(DEX_SETTINGS_URI, DESKTOP_SETTINGS_METHOD_GET, (String) null, bundle);
        if (bundleCall != null) {
            return Boolean.valueOf(bundleCall.getString(ENABLE_NEW_DEX_HOME)).booleanValue();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
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
    }

    private void stopSystemUIDesktopService() {
        Log.i(TAG, "stopSystemUIDesktopService");
        Log.i(TAG, getCallers());
    }

    /* JADX INFO: Access modifiers changed from: private */
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
        return false;
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
    public void notifyOccluded(boolean z) {
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("notifyOccluded() occluded=", TAG, z);
        if (((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).isDesktopBarConnected()) {
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).setOccluded(z);
        }
        this.mDexOccluded = z;
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyPrivacyItemsChanged(boolean z) {
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("notifyPrivacyItemsChanged() visible = ", " mDesktopMode = ", z);
        sbM.append(this.mDesktopMode);
        Log.i(TAG, sbM.toString());
        if (isDesktopMode()) {
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).notifyPrivacyItemsChanged(z);
        }
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyScreenState(boolean z) {
        if (((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).isDesktopBarConnected()) {
            AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("notifyScreenState() isScreenOn=", TAG, z);
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).onUpdate(3, getScreenState(z));
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
    public void unregisterCallback(DesktopManager.Callback callback) {
        this.mCallbacks.remove(callback);
    }

    private /* synthetic */ void lambda$startSystemUIDesktopService$0() {
    }

    private /* synthetic */ void lambda$stopSystemUIDesktopService$1() {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void notifyShowKeyguard() {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setAirplaneMode(boolean z, int i) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setBtTetherIcon(boolean z, int i) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setWifiIcon(boolean z, int i, int i2) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setMPTCPIcon(boolean z, int i, int i2, int i3) {
    }

    @Override // com.android.systemui.util.DesktopManager
    public void setMobileIcon(boolean z, int i, int i2, int i3, int i4, boolean z2, int i5, int i6) {
    }
}
