package com.android.server.biometrics.sensors.fingerprint;

import android.app.ActivityManager;
import android.app.SemStatusBarManager;
import android.app.SynchronousUserSwitchObserver;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.fingerprint.SemFpGestureCalibrator;
import com.android.server.biometrics.sensors.fingerprint.SemFpGestureManager;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class SemFpGestureManager implements SemFpHalLifecycleListener, SemFpEventListener {
    static final String ACTION_FINGERPRINT_GESTURE_SAMSUNG_PAY = "com.samsung.android.spay.gesture.fingerprint";
    static final String ACTION_SCREEN_OFF_BY_PROXIMITY = "android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY";
    static final String ACTION_SCREEN_ON_BY_PROXIMITY = "android.intent.action.ACTION_SCREEN_ON_BY_PROXIMITY";
    static final String DB_SPAY_GESTURE_MENU_VISIBLE = "fingerprint_gesture_spay_visible";
    static final String PERMISSION_SAMSUNG_PAY_SIMPLE_PAY = "com.samsung.android.spay.permission.SIMPLE_PAY";
    BroadcastReceiver mBroadCastReceiver;
    public SemFpGestureCalibrator mCalibrator;
    ContentObserver mContentObserver;
    public final Context mContext;

    /* renamed from: mH */
    public final Handler f1657mH;
    public final Injector mInjector;
    public boolean mIsEnabledGestureForSamsungPay;
    public boolean mIsEnabledGestureForStatusBar;
    public boolean mIsEnabledGestureMainSetting;
    public final ServiceProvider mServiceProvider;
    SynchronousUserSwitchObserver mUserSwitchObserver;

    public class Injector {
        public boolean isInteractive(Context context) {
            return ((PowerManager) context.getSystemService(PowerManager.class)).isInteractive();
        }

        public boolean isProximityPositive() {
            return ((PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class)).isProximityPositive();
        }

        public void userActivityTouchEvent(Context context) {
            ((PowerManager) context.getSystemService(PowerManager.class)).userActivity(SystemClock.uptimeMillis(), 2, 0);
        }

        public void registerUserSwitchObserver(SynchronousUserSwitchObserver synchronousUserSwitchObserver) {
            try {
                ActivityManager.getService().registerUserSwitchObserver(synchronousUserSwitchObserver, "FingerprintService.Gesture");
            } catch (RemoteException unused) {
                Slog.e("FingerprintService.Gesture", "Unable to register user switch observer");
            }
        }

        public void registerContentObserveForAllUser(ContentResolver contentResolver, Uri uri, ContentObserver contentObserver) {
            contentResolver.registerContentObserver(uri, false, contentObserver, -1);
        }

        public SemStatusBarManager getSemStatusBarManager(Context context) {
            return (SemStatusBarManager) context.getSystemService(SemStatusBarManager.class);
        }

        public SemFpGestureCalibrator createGestureCalibrator(Context context, ServiceProvider serviceProvider) {
            return new SemFpGestureCalibrator(context, serviceProvider);
        }

        public boolean isDexMode(Context context) {
            return Utils.isDexMode(context);
        }

        public boolean isFoldedInFlipType(Context context) {
            return Utils.isFlipFolded(context);
        }

        public boolean isEnabledGestureSettingForStatusBar(ContentResolver contentResolver) {
            return Settings.System.getIntForUser(contentResolver, "fingerprint_gesture_quick", 0, -2) == 1;
        }

        public boolean isEnabledGestureSettingForSamsungPay(ContentResolver contentResolver) {
            if (Settings.System.getIntForUser(contentResolver, "fingerprint_gesture_spay", 0, -2) == 1) {
                return !SemBiometricFeature.FP_FEATURE_SUPPORT_GESTURE_CALIBRATION || Settings.System.getIntForUser(contentResolver, SemFpGestureManager.DB_SPAY_GESTURE_MENU_VISIBLE, 1, -2) == 1;
            }
            return false;
        }

        public void sendBigDataForSamsungPay() {
            SemBioAnalyticsManager.get().fpInsertLog("FPGT", "SimpleSamsungPay", -1, 3);
        }

        public void sendBigDataForCollapseStatusBar() {
            SemBioAnalyticsManager.get().fpInsertLog("FPGT", "CollapseNotificationPanel", -1, 3);
        }

        public void sendBigDataForExpandStatusBar() {
            SemBioAnalyticsManager.get().fpInsertLog("FPGT", "ExpandNotificationPanel", -1, 3);
        }

        public void sendBigDataForExpandQuickSetting() {
            SemBioAnalyticsManager.get().fpInsertLog("FPGT", "ExpandQuickSettingsPanel", -1, 3);
        }
    }

    public SemFpGestureManager(Context context, ServiceProvider serviceProvider) {
        this(context, serviceProvider, new Injector());
    }

    public SemFpGestureManager(Context context, ServiceProvider serviceProvider, Injector injector) {
        this.mContext = context;
        this.mServiceProvider = serviceProvider;
        this.mInjector = injector;
        this.f1657mH = SemFpMainThread.get().getHandler();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpHalLifecycleListener
    public void onHalStarted(ServiceProvider serviceProvider) {
        if (this.mIsEnabledGestureMainSetting) {
            turnOnGestureMode();
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEventListener
    public void onGestureEvent(int i, int i2) {
        SemFpGestureCalibrator semFpGestureCalibrator = this.mCalibrator;
        if (semFpGestureCalibrator != null && semFpGestureCalibrator.isInverseDirection()) {
            Slog.i("FingerprintService.Gesture", "inverse action");
            if (i2 == 20001) {
                i2 = 20002;
            } else if (i2 == 20002) {
                i2 = 20001;
            }
        }
        if (canHandleGestureEvent(i2)) {
            try {
                this.mInjector.userActivityTouchEvent(this.mContext);
                if (i2 == 20001) {
                    handleSwipeUpEvent();
                } else if (i2 == 20002) {
                    handleSwipeDownEvent();
                }
            } catch (RuntimeException e) {
                Slog.w("FingerprintService.Gesture", "onGestureEvent: " + e.getMessage());
            }
        }
    }

    public final boolean canHandleGestureEvent(int i) {
        SemFpGestureCalibrator semFpGestureCalibrator = this.mCalibrator;
        if ((semFpGestureCalibrator != null && semFpGestureCalibrator.isCalibrationInProgress()) || !this.mIsEnabledGestureMainSetting) {
            return false;
        }
        if (!this.mInjector.isInteractive(this.mContext)) {
            Slog.d("FingerprintService.Gesture", "handleGestureAction: SKIP gesture action because of interactive status");
            return false;
        }
        if (this.mInjector.isDexMode(this.mContext)) {
            Slog.d("FingerprintService.Gesture", "handleGestureAction: SKIP gesture action because of DeX Mode");
            return false;
        }
        if (i != 20002 || !this.mInjector.isFoldedInFlipType(this.mContext)) {
            return true;
        }
        Slog.d("FingerprintService.Gesture", "handleGestureAction: SKIP gesture action because of flip folded status");
        return false;
    }

    public final void handleSwipeUpEvent() {
        SemStatusBarManager semStatusBarManager = this.mInjector.getSemStatusBarManager(this.mContext);
        if (this.mIsEnabledGestureForStatusBar && semStatusBarManager.isPanelExpanded()) {
            if (Utils.DEBUG) {
                Slog.v("FingerprintService.Gesture", "handleGestureAction: QuickPanel Expanded");
            }
            semStatusBarManager.collapsePanels();
            this.mInjector.sendBigDataForCollapseStatusBar();
            return;
        }
        if (this.mIsEnabledGestureForSamsungPay) {
            Intent intent = new Intent();
            intent.setAction(ACTION_FINGERPRINT_GESTURE_SAMSUNG_PAY);
            intent.addFlags(32);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT, PERMISSION_SAMSUNG_PAY_SIMPLE_PAY);
            this.mInjector.sendBigDataForSamsungPay();
        }
    }

    public final void handleSwipeDownEvent() {
        if (this.mIsEnabledGestureForStatusBar) {
            SemStatusBarManager semStatusBarManager = this.mInjector.getSemStatusBarManager(this.mContext);
            if (semStatusBarManager.isPanelExpanded()) {
                semStatusBarManager.expandQuickSettingsPanel();
                this.mInjector.sendBigDataForExpandQuickSetting();
            } else {
                semStatusBarManager.expandNotificationsPanel();
                this.mInjector.sendBigDataForExpandStatusBar();
            }
        }
    }

    public void start() {
        this.mServiceProvider.semAddHalLifecycleListener(this);
        this.mServiceProvider.semAddEventListener(this);
        updateGestureSettingValue();
        registerUserSwitchObserver();
        registerContentObserver();
        if (SemBiometricFeature.FP_FEATURE_SUPPORT_GESTURE_CALIBRATION) {
            SemFpGestureCalibrator createGestureCalibrator = this.mInjector.createGestureCalibrator(this.mContext, this.mServiceProvider);
            this.mCalibrator = createGestureCalibrator;
            createGestureCalibrator.onBootCompleted(this.mIsEnabledGestureMainSetting);
        }
    }

    public final void registerUserSwitchObserver() {
        if (this.mUserSwitchObserver != null) {
            return;
        }
        SynchronousUserSwitchObserverC09021 synchronousUserSwitchObserverC09021 = new SynchronousUserSwitchObserverC09021();
        this.mUserSwitchObserver = synchronousUserSwitchObserverC09021;
        this.mInjector.registerUserSwitchObserver(synchronousUserSwitchObserverC09021);
    }

    /* renamed from: com.android.server.biometrics.sensors.fingerprint.SemFpGestureManager$1 */
    public class SynchronousUserSwitchObserverC09021 extends SynchronousUserSwitchObserver {
        public SynchronousUserSwitchObserverC09021() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserSwitching$0() {
            SemFpGestureManager.this.turnOffGestureMode();
        }

        public void onUserSwitching(int i) {
            SemFpGestureManager.this.f1657mH.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpGestureManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SemFpGestureManager.SynchronousUserSwitchObserverC09021.this.lambda$onUserSwitching$0();
                }
            });
        }

        public void onUserSwitchComplete(final int i) {
            SemFpGestureManager.this.f1657mH.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpGestureManager$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SemFpGestureManager.SynchronousUserSwitchObserverC09021.this.lambda$onUserSwitchComplete$1(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserSwitchComplete$1(int i) {
            Slog.d("FingerprintService.Gesture", "onUserSwitchComplete: " + i);
            SemFpGestureManager.this.updateGestureSettingValue();
            if (SemFpGestureManager.this.mIsEnabledGestureMainSetting) {
                SemFpGestureManager.this.turnOnGestureMode();
            }
        }
    }

    public final void registerContentObserver() {
        if (this.mContentObserver != null) {
            return;
        }
        this.mContentObserver = new ContentObserver(this.f1657mH) { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpGestureManager.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                SemFpGestureManager.this.handleOnChangeSettingValue();
            }
        };
        try {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            this.mInjector.registerContentObserveForAllUser(contentResolver, Settings.System.getUriFor("fingerprint_gesture_quick"), this.mContentObserver);
            this.mInjector.registerContentObserveForAllUser(contentResolver, Settings.System.getUriFor("fingerprint_gesture_spay"), this.mContentObserver);
        } catch (RuntimeException e) {
            Slog.w("FingerprintService.Gesture", "Unable to register content observer" + e.getMessage());
        }
    }

    public final void handleOnChangeSettingValue() {
        SemFpGestureCalibrator semFpGestureCalibrator;
        SemFpGestureCalibrator semFpGestureCalibrator2 = this.mCalibrator;
        if (semFpGestureCalibrator2 != null && semFpGestureCalibrator2.isCalibrationInProgress()) {
            Slog.i("FingerprintService.Gesture", "calibration in progress..");
            return;
        }
        updateGestureSettingValue();
        if (SemBiometricFeature.FP_FEATURE_SUPPORT_GESTURE_CALIBRATION && this.mIsEnabledGestureMainSetting && (semFpGestureCalibrator = this.mCalibrator) != null && !semFpGestureCalibrator.hasCalibrationData()) {
            startCalibration();
        } else if (this.mIsEnabledGestureMainSetting) {
            turnOnGestureMode();
        } else {
            turnOffGestureMode();
        }
    }

    public final void startCalibration() {
        SemFpGestureCalibrator semFpGestureCalibrator = this.mCalibrator;
        if (semFpGestureCalibrator == null) {
            return;
        }
        semFpGestureCalibrator.setCallback(new SemFpGestureCalibrator.Callback() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpGestureManager.3
            @Override // com.android.server.biometrics.sensors.fingerprint.SemFpGestureCalibrator.Callback
            public void onCalibrationStarted() {
                SemFpGestureManager.this.f1657mH.removeCallbacksAndEqualMessages(null);
            }

            @Override // com.android.server.biometrics.sensors.fingerprint.SemFpGestureCalibrator.Callback
            public void onCalibrationFinished() {
                SemFpGestureManager.this.handleOnChangeSettingValue();
            }
        });
        this.mCalibrator.start(this.mIsEnabledGestureForSamsungPay);
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println(" NAVI = " + this.mIsEnabledGestureMainSetting);
    }

    public final void updateGestureSettingValue() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mIsEnabledGestureForStatusBar = this.mInjector.isEnabledGestureSettingForStatusBar(contentResolver);
        boolean isEnabledGestureSettingForSamsungPay = this.mInjector.isEnabledGestureSettingForSamsungPay(contentResolver);
        this.mIsEnabledGestureForSamsungPay = isEnabledGestureSettingForSamsungPay;
        boolean z = this.mIsEnabledGestureForStatusBar || isEnabledGestureSettingForSamsungPay;
        this.mIsEnabledGestureMainSetting = z;
        SemFpGestureCalibrator semFpGestureCalibrator = this.mCalibrator;
        if (semFpGestureCalibrator != null) {
            semFpGestureCalibrator.onGestureSettingChanged(z);
        }
        Slog.d("FingerprintService.Gesture", "updateGestureSettingValue: " + this.mIsEnabledGestureForStatusBar + ", " + this.mIsEnabledGestureForSamsungPay);
    }

    public final void turnOnGestureMode() {
        if (SemBiometricFeature.FEATURE_FINGERPRINT_JDM_HAL) {
            return;
        }
        ServiceProvider serviceProvider = this.mServiceProvider;
        serviceProvider.semRequest(((FingerprintSensorPropertiesInternal) serviceProvider.getSensorProperties().get(0)).sensorId, 15, 0, null, null);
        registerBroadcast();
    }

    public final void turnOffGestureMode() {
        if (SemBiometricFeature.FEATURE_FINGERPRINT_JDM_HAL) {
            return;
        }
        ServiceProvider serviceProvider = this.mServiceProvider;
        serviceProvider.semRequest(((FingerprintSensorPropertiesInternal) serviceProvider.getSensorProperties().get(0)).sensorId, 16, 0, null, null);
        unregisterBroadcast();
    }

    public final void registerBroadcast() {
        if (this.mBroadCastReceiver != null) {
            return;
        }
        this.mBroadCastReceiver = new BroadcastReceiver() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpGestureManager.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("android.intent.action.SCREEN_OFF".equals(action) || SemFpGestureManager.ACTION_SCREEN_OFF_BY_PROXIMITY.equals(action)) {
                    SemFpGestureManager.this.handleScreenOffBroadcast();
                } else if ("android.intent.action.SCREEN_ON".equals(action) || SemFpGestureManager.ACTION_SCREEN_ON_BY_PROXIMITY.equals(action)) {
                    SemFpGestureManager.this.handleScreenOnBroadcast();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction(ACTION_SCREEN_ON_BY_PROXIMITY);
        intentFilter.addAction(ACTION_SCREEN_OFF_BY_PROXIMITY);
        Utils.registerBroadcastAsUser(this.mContext, this.mBroadCastReceiver, intentFilter, UserHandle.ALL, this.f1657mH);
    }

    public final void handleScreenOffBroadcast() {
        if (this.mIsEnabledGestureMainSetting) {
            if (!this.mInjector.isInteractive(this.mContext) || this.mInjector.isProximityPositive()) {
                ServiceProvider serviceProvider = this.mServiceProvider;
                serviceProvider.semRequest(((FingerprintSensorPropertiesInternal) serviceProvider.getSensorProperties().get(0)).sensorId, 17, 0, null, null);
            }
        }
    }

    public final void handleScreenOnBroadcast() {
        if (this.mIsEnabledGestureMainSetting && this.mInjector.isInteractive(this.mContext) && !this.mInjector.isProximityPositive()) {
            ServiceProvider serviceProvider = this.mServiceProvider;
            serviceProvider.semRequest(((FingerprintSensorPropertiesInternal) serviceProvider.getSensorProperties().get(0)).sensorId, 17, 1, null, null);
        }
    }

    public final void unregisterBroadcast() {
        BroadcastReceiver broadcastReceiver = this.mBroadCastReceiver;
        if (broadcastReceiver == null) {
            return;
        }
        Utils.unregisterBroadcast(this.mContext, broadcastReceiver);
        this.mBroadCastReceiver = null;
    }
}
