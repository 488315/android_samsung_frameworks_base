package com.android.systemui.qrcodescanner.controller;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.DeviceConfig;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QRCodeScannerController implements CallbackController {
    public final boolean mConfigEnableLockScreenButton;
    public final Context mContext;
    public final DeviceConfigProxy mDeviceConfigProxy;
    public final Executor mExecutor;
    public boolean mQRCodeScannerEnabled;
    public final SecureSettings mSecureSettings;
    public final UserTracker mUserTracker;
    public final ArrayList mCallbacks = new ArrayList();
    public HashMap mQRCodeScannerPreferenceObserver = new HashMap();
    public QRCodeScannerController$$ExternalSyntheticLambda2 mOnDefaultQRCodeScannerChangedListener = null;
    public UserTracker.Callback mUserChangedListener = null;
    public Intent mIntent = null;
    public String mQRCodeScannerActivity = null;
    public final AtomicInteger mQRCodeScannerPreferenceChangeEvents = new AtomicInteger(0);
    public final AtomicInteger mDefaultQRCodeScannerChangeEvents = new AtomicInteger(0);
    public Boolean mIsCameraAvailable = null;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContentObserver {
        public AnonymousClass1(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            QRCodeScannerController.this.mExecutor.execute(new QRCodeScannerController$$ExternalSyntheticLambda0(this, 3));
        }
    }

    public QRCodeScannerController(Context context, Executor executor, SecureSettings secureSettings, DeviceConfigProxy deviceConfigProxy, UserTracker userTracker) {
        this.mContext = context;
        this.mExecutor = executor;
        this.mSecureSettings = secureSettings;
        this.mDeviceConfigProxy = deviceConfigProxy;
        this.mUserTracker = userTracker;
        this.mConfigEnableLockScreenButton = context.getResources().getBoolean(R.bool.config_enableQrCodeScannerOnLockScreen);
        executor.execute(new QRCodeScannerController$$ExternalSyntheticLambda0(this, 2));
    }

    public final boolean isAbleToLaunchScannerActivity() {
        Intent intent = this.mIntent;
        if (intent != null) {
            return intent.getComponent() == null ? false : this.mContext.getPackageManager().queryIntentActivities(intent, 819200).isEmpty() ^ true;
        }
        return false;
    }

    public final boolean isCameraAvailable() {
        if (this.mIsCameraAvailable == null) {
            this.mIsCameraAvailable = Boolean.valueOf(this.mContext.getPackageManager().hasSystemFeature("android.hardware.camera"));
        }
        return this.mIsCameraAvailable.booleanValue();
    }

    public final void registerQRCodePreferenceObserver() {
        if (this.mConfigEnableLockScreenButton) {
            int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
            if (this.mQRCodeScannerPreferenceObserver.getOrDefault(Integer.valueOf(userId), null) != null) {
                return;
            }
            this.mExecutor.execute(new QRCodeScannerController$$ExternalSyntheticLambda0(this, 0));
            this.mQRCodeScannerPreferenceObserver.put(Integer.valueOf(userId), new AnonymousClass1(null));
            SecureSettings secureSettings = this.mSecureSettings;
            secureSettings.registerContentObserverForUserSync(secureSettings.getUriFor("lock_screen_show_qr_code_scanner"), false, (ContentObserver) this.mQRCodeScannerPreferenceObserver.get(Integer.valueOf(userId)), userId);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [android.provider.DeviceConfig$OnPropertiesChangedListener, com.android.systemui.qrcodescanner.controller.QRCodeScannerController$$ExternalSyntheticLambda2] */
    public final void registerQRCodeScannerChangeObservers(int... iArr) {
        if (isCameraAvailable()) {
            for (int i : iArr) {
                if (i == 0) {
                    this.mDefaultQRCodeScannerChangeEvents.incrementAndGet();
                    if (this.mOnDefaultQRCodeScannerChangedListener == null) {
                        this.mExecutor.execute(new QRCodeScannerController$$ExternalSyntheticLambda0(this, 1));
                        ?? r2 = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$$ExternalSyntheticLambda2
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                QRCodeScannerController qRCodeScannerController = QRCodeScannerController.this;
                                qRCodeScannerController.getClass();
                                if ("systemui".equals(properties.getNamespace()) && properties.getKeyset().contains("default_qr_code_scanner")) {
                                    qRCodeScannerController.updateQRCodeScannerActivityDetails();
                                    qRCodeScannerController.updateQRCodeScannerPreferenceDetails(true);
                                }
                            }
                        };
                        this.mOnDefaultQRCodeScannerChangedListener = r2;
                        this.mDeviceConfigProxy.addOnPropertiesChangedListener("systemui", this.mExecutor, r2);
                    }
                } else if (i != 1) {
                    ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "Unrecognised event: ", "QRCodeScannerController");
                } else {
                    this.mQRCodeScannerPreferenceChangeEvents.incrementAndGet();
                    registerQRCodePreferenceObserver();
                    if (this.mUserChangedListener == null) {
                        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController.2
                            @Override // com.android.systemui.settings.UserTracker.Callback
                            public final void onUserChanged(int i2, Context context) {
                                QRCodeScannerController qRCodeScannerController = QRCodeScannerController.this;
                                qRCodeScannerController.registerQRCodePreferenceObserver();
                                qRCodeScannerController.updateQRCodeScannerPreferenceDetails(true);
                            }
                        };
                        this.mUserChangedListener = callback;
                        ((UserTrackerImpl) this.mUserTracker).addCallback(callback, this.mExecutor);
                    }
                }
            }
        }
    }

    public final void unregisterQRCodeScannerChangeObservers(int... iArr) {
        if (isCameraAvailable()) {
            for (int i : iArr) {
                if (i != 0) {
                    if (i != 1) {
                        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "Unrecognised event: ", "QRCodeScannerController");
                    } else {
                        UserTracker userTracker = this.mUserTracker;
                        if (userTracker != null && this.mQRCodeScannerPreferenceChangeEvents.decrementAndGet() == 0) {
                            if (this.mConfigEnableLockScreenButton) {
                                this.mQRCodeScannerPreferenceObserver.forEach(new BiConsumer() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$$ExternalSyntheticLambda3
                                    @Override // java.util.function.BiConsumer
                                    public final void accept(Object obj, Object obj2) {
                                        QRCodeScannerController.this.mSecureSettings.unregisterContentObserverSync((ContentObserver) obj2);
                                    }
                                });
                                this.mQRCodeScannerPreferenceObserver = new HashMap();
                                this.mSecureSettings.putStringForUser("show_qr_code_scanner_setting", null, ((UserTrackerImpl) userTracker).getUserId());
                            }
                            ((UserTrackerImpl) userTracker).removeCallback(this.mUserChangedListener);
                            this.mUserChangedListener = null;
                            this.mQRCodeScannerEnabled = false;
                        }
                    }
                } else if (this.mOnDefaultQRCodeScannerChangedListener != null && this.mDefaultQRCodeScannerChangeEvents.decrementAndGet() == 0) {
                    this.mDeviceConfigProxy.removeOnPropertiesChangedListener(this.mOnDefaultQRCodeScannerChangedListener);
                    this.mOnDefaultQRCodeScannerChangedListener = null;
                }
            }
        }
    }

    public final void updateQRCodeScannerActivityDetails() {
        ArrayList arrayList;
        String string = this.mDeviceConfigProxy.getString("systemui", "default_qr_code_scanner", "");
        if (Objects.equals(string, "")) {
            string = this.mContext.getResources().getString(R.string.deleteText);
        }
        String str = this.mQRCodeScannerActivity;
        Intent intent = new Intent();
        if (string != null) {
            intent.setComponent(ComponentName.unflattenFromString(string));
            intent.addFlags(335544320);
        }
        if (intent.getComponent() == null ? false : !this.mContext.getPackageManager().queryIntentActivities(intent, 537698816).isEmpty()) {
            this.mQRCodeScannerActivity = string;
            this.mIntent = intent;
        } else {
            this.mQRCodeScannerActivity = null;
            this.mIntent = null;
        }
        if (Objects.equals(this.mQRCodeScannerActivity, str)) {
            return;
        }
        synchronized (this.mCallbacks) {
            arrayList = (ArrayList) this.mCallbacks.clone();
        }
        arrayList.forEach(new QRCodeScannerController$$ExternalSyntheticLambda5(0));
    }

    public final void updateQRCodeScannerPreferenceDetails(boolean z) {
        ArrayList arrayList;
        if (this.mConfigEnableLockScreenButton) {
            boolean z2 = this.mQRCodeScannerEnabled;
            this.mQRCodeScannerEnabled = this.mSecureSettings.getIntForUser("lock_screen_show_qr_code_scanner", 0, ((UserTrackerImpl) this.mUserTracker).getUserId()) != 0;
            if (z) {
                this.mSecureSettings.putStringForUser("show_qr_code_scanner_setting", this.mQRCodeScannerActivity, ((UserTrackerImpl) this.mUserTracker).getUserId());
            }
            if (Boolean.valueOf(this.mQRCodeScannerEnabled).equals(Boolean.valueOf(z2))) {
                return;
            }
            synchronized (this.mCallbacks) {
                arrayList = (ArrayList) this.mCallbacks.clone();
            }
            arrayList.forEach(new QRCodeScannerController$$ExternalSyntheticLambda5(1));
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Callback callback) {
        if (isCameraAvailable()) {
            synchronized (this.mCallbacks) {
                this.mCallbacks.add(callback);
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Callback callback) {
        if (isCameraAvailable()) {
            synchronized (this.mCallbacks) {
                this.mCallbacks.remove(callback);
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
        void onQRCodeScannerActivityChanged();

        default void onQRCodeScannerPreferenceChanged() {
        }
    }
}
