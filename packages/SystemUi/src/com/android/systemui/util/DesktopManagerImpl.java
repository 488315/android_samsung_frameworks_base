package com.android.systemui.util;

import android.content.Context;
import android.database.ContentObserver;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.BasicRune;
import com.android.systemui.controls.controller.CustomDeviceControlsController;
import com.android.systemui.controls.controller.CustomDeviceControlsControllerImpl;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.logging.IndicatorLogger;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder;
import com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.KeyguardConstants;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DesktopManagerImpl implements DesktopManager {
    public static final Uri DESKTOP_SETTINGS_URI;
    public static final Uri DEX_SETTINGS_URI;
    public final List mCallbacks;
    public final Context mContext;
    public final Lazy mCustomDeviceControlsController;
    public final C35774 mDesktopBinderCallback;
    public SBluetoothControllerImpl.C34321 mDesktopBluetoothCallback;
    public final C35796 mDesktopModeListener;
    public final C35807 mDesktopSettingsObserver;
    public List mDesktopStatusBarIconCallback;
    public final Lazy mDesktopSystemUiBinderLazy;
    public final HandlerC35763 mHandler;
    public final BinderC35785 mIDesktopCallback;
    public final IndicatorLogger mIndicatorLogger;
    public final KeyguardSecurityModel mKeyguardSecurityModel;
    public final Lazy mKeyguardViewControllerLazy;
    public final SemDesktopModeManager mSemDesktopModeManager;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public int mDesktopMode = 710;
    public boolean mDexOccluded = false;
    public final C35741 mObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.util.DesktopManagerImpl.1
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedWakingUp() {
            DesktopManagerImpl.this.notifyScreenState(true);
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            DesktopManagerImpl.this.notifyScreenState(false);
        }
    };
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.util.DesktopManagerImpl.2
        public boolean mIsFaceAuth;
        public boolean mTrustEnabled;
        public int mUserId = KeyguardUpdateMonitor.getCurrentUser();

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
            if (biometricSourceType == BiometricSourceType.FACE && z && this.mIsFaceAuth) {
                this.mIsFaceAuth = false;
                if (this.mTrustEnabled) {
                    return;
                }
                int i = this.mUserId;
                DesktopManagerImpl desktopManagerImpl = DesktopManagerImpl.this;
                desktopManagerImpl.notifyTrustChanged(i, false);
                if (desktopManagerImpl.mUpdateMonitor.mKeyguardShowing) {
                    desktopManagerImpl.notifyShowKeyguard();
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
            DesktopManagerImpl desktopManagerImpl = DesktopManagerImpl.this;
            if (desktopManagerImpl.mUpdateMonitor.mKeyguardShowing) {
                desktopManagerImpl.notifyShowKeyguard();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustChanged(int i) {
            DesktopManagerImpl desktopManagerImpl = DesktopManagerImpl.this;
            boolean userHasTrust = desktopManagerImpl.mUpdateMonitor.getUserHasTrust(i);
            boolean isBiometricsAuthenticatedOnLock = desktopManagerImpl.mUpdateMonitor.isBiometricsAuthenticatedOnLock();
            if (this.mTrustEnabled == userHasTrust && this.mUserId == i && this.mIsFaceAuth == isBiometricsAuthenticatedOnLock) {
                return;
            }
            this.mTrustEnabled = userHasTrust;
            this.mIsFaceAuth = isBiometricsAuthenticatedOnLock;
            this.mUserId = i;
            desktopManagerImpl.notifyTrustChanged(i, userHasTrust || isBiometricsAuthenticatedOnLock);
            if (desktopManagerImpl.mUpdateMonitor.mKeyguardShowing) {
                desktopManagerImpl.notifyShowKeyguard();
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.util.DesktopManagerImpl$8 */
    public abstract /* synthetic */ class AbstractC35818 {

        /* renamed from: $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode */
        public static final /* synthetic */ int[] f391xdc0e830a;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            f391xdc0e830a = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Invalid.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f391xdc0e830a[KeyguardSecurityModel.SecurityMode.None.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f391xdc0e830a[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f391xdc0e830a[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f391xdc0e830a[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    static {
        DeviceType.isEngOrUTBinary();
        DESKTOP_SETTINGS_URI = Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
        DEX_SETTINGS_URI = Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.util.DesktopManagerImpl$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.os.Handler, com.android.systemui.util.DesktopManagerImpl$3] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.util.DesktopManagerImpl$4] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.util.DesktopManagerImpl$5] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.util.DesktopManagerImpl$6, com.samsung.android.desktopmode.SemDesktopModeManager$DesktopModeListener] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.util.DesktopManagerImpl$7] */
    public DesktopManagerImpl(Context context, Lazy lazy, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy2, IndicatorLogger indicatorLogger, Lazy lazy3) {
        ?? r1 = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.util.DesktopManagerImpl.3
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                Lazy lazy4;
                int i = message.what & (-65536);
                if (i == 65536) {
                    ((KeyguardViewController) DesktopManagerImpl.this.mKeyguardViewControllerLazy.get()).requestUnlock((String) message.obj);
                    return;
                }
                if (i == 196608) {
                    SBluetoothControllerImpl.C34321 c34321 = DesktopManagerImpl.this.mDesktopBluetoothCallback;
                    if (c34321 != null) {
                        c34321.getClass();
                        Bundle bundle = new Bundle();
                        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
                        SBluetoothControllerImpl sBluetoothControllerImpl = SBluetoothControllerImpl.this;
                        if (sBluetoothControllerImpl.getConnectedDevicesForGroup() != null) {
                            arrayList = new ArrayList<>(sBluetoothControllerImpl.getConnectedDevicesForGroup());
                        }
                        bundle.putParcelableArrayList("list", arrayList);
                        ((DesktopSystemUiBinder) ((DesktopManagerImpl) sBluetoothControllerImpl.mDesktopManager).mDesktopSystemUiBinderLazy.get()).setConnectedDeviceListForGroup(bundle);
                        return;
                    }
                    return;
                }
                if (i == 458752) {
                    if (!BasicRune.CONTROLS_DEX_SUPPORT || (lazy4 = DesktopManagerImpl.this.mCustomDeviceControlsController) == null) {
                        return;
                    }
                    ((CustomDeviceControlsControllerImpl) ((CustomDeviceControlsController) lazy4.get())).start();
                    return;
                }
                if (i == 524288) {
                    List list = DesktopManagerImpl.this.mDesktopStatusBarIconCallback;
                    if (list != null) {
                        Iterator it = ((ArrayList) list).iterator();
                        while (it.hasNext()) {
                            ((StatusBarSignalPolicy.DesktopCallback) it.next()).updateDesktopStatusBarIcons();
                        }
                        return;
                    }
                    return;
                }
                if (i != 589824) {
                    return;
                }
                DesktopManagerImpl desktopManagerImpl = DesktopManagerImpl.this;
                Uri uri = DesktopManagerImpl.DESKTOP_SETTINGS_URI;
                desktopManagerImpl.getClass();
                Log.i("DesktopManager", "handleNotifyPrivacyItemStateRequested");
                Iterator it2 = ((ArrayList) desktopManagerImpl.mCallbacks).iterator();
                while (it2.hasNext()) {
                    ((DesktopManager.Callback) it2.next()).onPrivacyItemStateRequested();
                }
            }
        };
        this.mHandler = r1;
        this.mDesktopBinderCallback = new DesktopSystemUiBinder.Callback() { // from class: com.android.systemui.util.DesktopManagerImpl.4
            @Override // com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder.Callback
            public final void onServiceConnected() {
                Log.d("DesktopManager", "onServiceConnected");
                DesktopManagerImpl desktopManagerImpl = DesktopManagerImpl.this;
                desktopManagerImpl.notifyOccluded(desktopManagerImpl.mDexOccluded);
                if (desktopManagerImpl.mUpdateMonitor.mKeyguardShowing) {
                    desktopManagerImpl.notifyShowKeyguard();
                } else {
                    desktopManagerImpl.notifyDismissKeyguard();
                }
                desktopManagerImpl.notifyKeyguardLockout(desktopManagerImpl.mUpdateMonitor.getLockoutAttemptDeadline() > 0);
                Iterator it = ((ArrayList) desktopManagerImpl.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((DesktopManager.Callback) it.next()).getClass();
                }
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder.Callback
            public final void onServiceDisconnected() {
                Iterator it = ((ArrayList) DesktopManagerImpl.this.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((DesktopManager.Callback) it.next()).getClass();
                }
            }
        };
        this.mIDesktopCallback = new IDesktopBarCallback.Stub() { // from class: com.android.systemui.util.DesktopManagerImpl.5
            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public final void getConnectedDeviceListForGroup() {
                Log.d("DesktopManager", "getConnectedDeviceListForGroup");
                removeMessages(196608);
                obtainMessage(196608).sendToTarget();
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public final int getFailedUnlockAttempt() {
                return DesktopManagerImpl.this.mUpdateMonitor.getFailedUnlockAttempts(KeyguardUpdateMonitor.getCurrentUser());
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public final long getLockoutAttemptDeadline() {
                return DesktopManagerImpl.this.mUpdateMonitor.getLockoutAttemptDeadline();
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public final int getRemainingAttemptBeforeWipe() {
                return DesktopManagerImpl.this.mUpdateMonitor.getRemainingAttemptsBeforeWipe();
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public final void requestPrivacyItems() {
                Log.d("DesktopManager", "requestPrivacyItems");
                removeMessages(589824);
                obtainMessage(589824).sendToTarget();
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public final void requestStatusIcons() {
                Log.d("DesktopManager", "requestStatusIcons");
                removeMessages(524288);
                obtainMessage(524288).sendToTarget();
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public final void requestUnlock(String str) {
                Log.d("DesktopManager", "requestUnlock called!");
                removeMessages(65536);
                obtainMessage(65536, str).sendToTarget();
            }

            @Override // com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBarCallback
            public final void showControls() {
                Log.d("DesktopManager", "showControls");
                removeMessages(458752);
                obtainMessage(458752).sendToTarget();
            }
        };
        ?? r2 = new SemDesktopModeManager.DesktopModeListener() { // from class: com.android.systemui.util.DesktopManagerImpl.6
            public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                Log.i("DesktopManager", "onDesktopModeStateChanged " + semDesktopModeState.toString());
                DesktopManagerImpl desktopManagerImpl = DesktopManagerImpl.this;
                Uri uri = DesktopManagerImpl.DESKTOP_SETTINGS_URI;
                desktopManagerImpl.updateDesktopMode(semDesktopModeState);
                DesktopManagerImpl.this.startSystemUIDesktopIfNeeded(semDesktopModeState, true);
                DesktopManagerImpl desktopManagerImpl2 = DesktopManagerImpl.this;
                desktopManagerImpl2.getClass();
                if (semDesktopModeState.getState() == 50) {
                    int i = semDesktopModeState.enabled;
                    C35807 c35807 = desktopManagerImpl2.mDesktopSettingsObserver;
                    Context context2 = desktopManagerImpl2.mContext;
                    if (i == 4) {
                        context2.getContentResolver().registerContentObserver(DesktopManagerImpl.DESKTOP_SETTINGS_URI, true, c35807);
                    } else if (i == 2) {
                        context2.getContentResolver().unregisterContentObserver(c35807);
                    }
                }
                Iterator it = ((ArrayList) DesktopManagerImpl.this.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((DesktopManager.Callback) it.next()).onDesktopModeStateChanged(semDesktopModeState);
                }
            }
        };
        this.mDesktopModeListener = r2;
        this.mCallbacks = new ArrayList();
        this.mDesktopSettingsObserver = new ContentObserver(r1) { // from class: com.android.systemui.util.DesktopManagerImpl.7
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                String lastPathSegment = uri.getLastPathSegment();
                if (lastPathSegment == null || !lastPathSegment.equals("touchpad_enabled")) {
                    return;
                }
                DesktopManagerImpl desktopManagerImpl = DesktopManagerImpl.this;
                Uri uri2 = DesktopManagerImpl.DESKTOP_SETTINGS_URI;
                desktopManagerImpl.getClass();
                Bundle bundle = new Bundle();
                bundle.putString("key", lastPathSegment);
                String str = "false";
                bundle.putString("def", "false");
                try {
                    Bundle call = desktopManagerImpl.mContext.getContentResolver().call(DesktopManagerImpl.DESKTOP_SETTINGS_URI, "getSettings", (String) null, bundle);
                    if (call != null) {
                        str = call.getString(lastPathSegment);
                    }
                } catch (IllegalArgumentException e) {
                    Log.e("DesktopManager", "Failed to get settings", e);
                }
                "true".equals(str);
                desktopManagerImpl.getClass();
            }
        };
        this.mContext = context;
        this.mKeyguardViewControllerLazy = lazy;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardSecurityModel = keyguardSecurityModel;
        this.mDesktopSystemUiBinderLazy = lazy2;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mIndicatorLogger = indicatorLogger;
        if (BasicRune.CONTROLS_DEX_SUPPORT) {
            this.mCustomDeviceControlsController = lazy3;
        } else {
            this.mCustomDeviceControlsController = null;
        }
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
        this.mSemDesktopModeManager = semDesktopModeManager;
        if (semDesktopModeManager == null || !Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            return;
        }
        semDesktopModeManager.registerListener((SemDesktopModeManager.DesktopModeListener) r2);
        SemDesktopModeState desktopModeState = semDesktopModeManager.getDesktopModeState();
        updateDesktopMode(desktopModeState);
        startSystemUIDesktopIfNeeded(desktopModeState, false);
    }

    public static Bundle getBouncerMessage(CharSequence charSequence, CharSequence charSequence2) {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(charSequence)) {
            bundle.putCharSequence(KeyguardConstants.UpdateType.BouncerTextKey.MSG, charSequence);
        }
        if (!TextUtils.isEmpty("")) {
            bundle.putCharSequence(KeyguardConstants.UpdateType.BouncerTextKey.SUB_MSG, "");
        }
        if (!TextUtils.isEmpty(charSequence2)) {
            bundle.putCharSequence(KeyguardConstants.UpdateType.BouncerTextKey.POPUP_MSG, charSequence2);
        }
        return bundle;
    }

    public final int getCurrentSecurityMode() {
        int i;
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (keyguardUpdateMonitor.is2StepVerification()) {
            return 5;
        }
        int i2 = AbstractC35818.f391xdc0e830a[this.mKeyguardSecurityModel.getSecurityMode(currentUser).ordinal()];
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
        if (i == 5 || !(keyguardUpdateMonitor.getUserHasTrust(currentUser) || keyguardUpdateMonitor.isBiometricsAuthenticatedOnLock())) {
            return i;
        }
        return 1;
    }

    public final SemDesktopModeState getSemDesktopModeState() {
        SemDesktopModeManager semDesktopModeManager = this.mSemDesktopModeManager;
        if (semDesktopModeManager != null) {
            return semDesktopModeManager.getDesktopModeState();
        }
        return null;
    }

    public final boolean isDesktopMode() {
        return this.mDesktopMode != 710;
    }

    public final boolean isDualView() {
        return this.mDesktopMode == 712;
    }

    public final boolean isStandalone() {
        return this.mDesktopMode == 711;
    }

    public final void notifyDismissKeyguard() {
        Log.i("DesktopManager", "notifyDismissKeyguard()");
        Lazy lazy = this.mDesktopSystemUiBinderLazy;
        if (((DesktopSystemUiBinder) lazy.get()).isDesktopBarConnected()) {
            ((DesktopSystemUiBinder) lazy.get()).onDismiss();
        }
    }

    public final void notifyKeyguardLockout(boolean z) {
        Lazy lazy = this.mDesktopSystemUiBinderLazy;
        if (((DesktopSystemUiBinder) lazy.get()).isDesktopBarConnected() && isDualView()) {
            ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("notifyKeyguardLockout lockout=", z, "DesktopManager");
            if (z) {
                ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).updateLockoutWarningMessage();
            } else {
                ((DesktopSystemUiBinder) lazy.get()).onLockout(z, null);
            }
        }
    }

    public final void notifyOccluded(boolean z) {
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("notifyOccluded() occluded=", z, "DesktopManager");
        Lazy lazy = this.mDesktopSystemUiBinderLazy;
        if (((DesktopSystemUiBinder) lazy.get()).isDesktopBarConnected()) {
            ((DesktopSystemUiBinder) lazy.get()).setOccluded(z);
        }
        this.mDexOccluded = z;
    }

    public final void notifyPrivacyItemsChanged(boolean z) {
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("notifyPrivacyItemsChanged() visible = ", z, " mDesktopMode = ");
        m49m.append(this.mDesktopMode);
        Log.i("DesktopManager", m49m.toString());
        if (isDesktopMode()) {
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).notifyPrivacyItemsChanged(z);
        }
    }

    public final void notifyScreenState(boolean z) {
        Lazy lazy = this.mDesktopSystemUiBinderLazy;
        if (((DesktopSystemUiBinder) lazy.get()).isDesktopBarConnected()) {
            Log.i("DesktopManager", "notifyScreenState() isScreenOn=" + z);
            DesktopSystemUiBinder desktopSystemUiBinder = (DesktopSystemUiBinder) lazy.get();
            Bundle bundle = new Bundle();
            bundle.putBoolean(KeyguardConstants.UpdateType.ScreenStateKey.IS_SCREEN_ON, z);
            desktopSystemUiBinder.onUpdate(3, bundle);
        }
    }

    public final void notifyShowKeyguard() {
        Log.i("DesktopManager", "notifyShowKeyguard() security mode=" + getCurrentSecurityMode());
        Lazy lazy = this.mDesktopSystemUiBinderLazy;
        if (((DesktopSystemUiBinder) lazy.get()).isDesktopBarConnected()) {
            ((DesktopSystemUiBinder) lazy.get()).onShow(getCurrentSecurityMode());
        }
    }

    public final void notifyTrustChanged(int i, boolean z) {
        Lazy lazy = this.mDesktopSystemUiBinderLazy;
        if (((DesktopSystemUiBinder) lazy.get()).isDesktopBarConnected() && isDualView()) {
            Log.i("DesktopManager", "notifyTrustChanged hasTrust=" + z + " userId=" + i);
            DesktopSystemUiBinder desktopSystemUiBinder = (DesktopSystemUiBinder) lazy.get();
            Bundle bundle = new Bundle();
            bundle.putBoolean(KeyguardConstants.UpdateType.TrustStateKey.TRUST, z);
            desktopSystemUiBinder.onUpdate(4, bundle);
        }
    }

    public final void notifyUpdateBouncerMessage(int i, CharSequence charSequence, CharSequence charSequence2) {
        Lazy lazy = this.mDesktopSystemUiBinderLazy;
        if (((DesktopSystemUiBinder) lazy.get()).isDesktopBarConnected() && isDualView()) {
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("notifyUpdateBouncerMessage type= ", i, "  msg=");
            m1m.append((Object) (charSequence != null ? charSequence : ""));
            m1m.append("  sub=");
            m1m.append((Object) "");
            m1m.append("  popupMsg=");
            m1m.append((Object) (charSequence2 != null ? charSequence2 : ""));
            Log.i("DesktopManager", m1m.toString());
            ((DesktopSystemUiBinder) lazy.get()).onUpdate(i, getBouncerMessage(charSequence, charSequence2));
        }
    }

    public final void registerCallback(DesktopManager.Callback callback) {
        ((ArrayList) this.mCallbacks).add(callback);
    }

    public final void setAirplaneMode(boolean z, int i) {
        if (isDesktopMode()) {
            this.mIndicatorLogger.log("setAirplaneMode - visible:" + z + ",resId:" + i);
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).setAirplaneMode(z, i);
        }
    }

    public final void setDesktopStatusBarIconCallback(StatusBarSignalPolicy.DesktopCallback desktopCallback) {
        if (this.mDesktopStatusBarIconCallback == null) {
            this.mDesktopStatusBarIconCallback = new ArrayList();
        }
        this.mDesktopStatusBarIconCallback.add(desktopCallback);
    }

    public final void setWifiIcon(boolean z, int i, int i2) {
        if (isDesktopMode()) {
            StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("setWifiIcon - visible:", z, ",resId:", i, ",activityId:");
            m66m.append(i2);
            this.mIndicatorLogger.log(m66m.toString());
            ((DesktopSystemUiBinder) this.mDesktopSystemUiBinderLazy.get()).setWifiIcon(z, i, i2);
        }
    }

    public final void startSystemUIDesktopIfNeeded(SemDesktopModeState semDesktopModeState, boolean z) {
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

    public final void startSystemUIDesktopService() {
        Log.i("DesktopManager", "startSystemUIDesktopService");
        Log.i("DesktopManager", Debug.getCallers(3, " "));
        Bundle bundle = new Bundle(2);
        bundle.putString("key", "enable_new_dex_home");
        bundle.putString("def", "false");
        Bundle call = this.mContext.getContentResolver().call(DEX_SETTINGS_URI, "getSettings", (String) null, bundle);
        if (call != null ? Boolean.valueOf(call.getString("enable_new_dex_home")).booleanValue() : false) {
            return;
        }
        Lazy lazy = this.mDesktopSystemUiBinderLazy;
        ((DesktopSystemUiBinder) lazy.get()).start(this.mIDesktopCallback);
        ((DesktopSystemUiBinder) lazy.get()).registerCallback(this.mDesktopBinderCallback);
        post(new DesktopManagerImpl$$ExternalSyntheticLambda0(this, 1));
    }

    public final void stopSystemUIDesktopService() {
        Log.i("DesktopManager", "stopSystemUIDesktopService");
        Log.i("DesktopManager", Debug.getCallers(3, " "));
        Lazy lazy = this.mDesktopSystemUiBinderLazy;
        ((DesktopSystemUiBinder) lazy.get()).stop();
        ((DesktopSystemUiBinder) lazy.get()).unregisterCallback(this.mDesktopBinderCallback);
        post(new DesktopManagerImpl$$ExternalSyntheticLambda0(this, 0));
    }

    public final void updateDesktopMode(SemDesktopModeState semDesktopModeState) {
        this.mDesktopMode = 710;
        if (semDesktopModeState.getEnabled() == 4) {
            int displayType = semDesktopModeState.getDisplayType();
            if (displayType == 101) {
                this.mDesktopMode = 711;
            } else {
                if (displayType != 102) {
                    return;
                }
                this.mDesktopMode = 712;
            }
        }
    }
}
