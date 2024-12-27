package com.android.server.knox.dar.ddar.core;

import android.app.AlarmManager;
import android.app.trust.TrustManager;
import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.ddar.DDLog;

import com.samsung.android.knox.dar.VirtualLockUtils;
import com.samsung.android.knox.dar.ddar.DualDARController;
import com.samsung.android.knox.dar.ddar.DualDarAuthUtils;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManager;

import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DualDarManagerImpl {
    public static boolean delayDataLockOnceForDeviceOwner;
    public final AlarmManager mAlarmManager;
    public final Context mContext;
    public final DualDarAuthUtils mDualDarAuthUtils;
    public final Handler mHandler;
    public final Injector mInjector;
    public final LockPatternUtils mLockPatternUtils;
    public final VirtualLockUtils mVirtualLockUtils;
    public final ArrayMap mLicenseExpiryAlarmListenerForUser = new ArrayMap();
    public final DualDarManagerImpl$$ExternalSyntheticLambda2 hardResetTask =
            new Runnable() { // from class:
                             // com.android.server.knox.dar.ddar.core.DualDarManagerImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    DualDarManagerImpl.this.handleDataLock(0);
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LicenseExpiryAlarmListener implements AlarmManager.OnAlarmListener {
        public final Context mContext;
        public final int mUserId;

        public LicenseExpiryAlarmListener(Context context, int i) {
            this.mContext = context;
            this.mUserId = i;
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            DDLog.d(
                    "DualDarManagerImpl",
                    "LicenseExpiryAlarmListener onAlarm() " + this.mUserId,
                    new Object[0]);
            Bundle bundle = new Bundle();
            bundle.putInt("user_id", this.mUserId);
            KnoxProxyManager.getInstance(this.mContext)
                    .relayMessage(
                            "KNOXCORE_PROXY_AGENT",
                            "DUALDAR_CONTROLLER_SERVICE",
                            "EXPIRE_TRIAL_PERIOD_TIME",
                            bundle);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.knox.dar.ddar.core.DualDarManagerImpl$$ExternalSyntheticLambda2] */
    public DualDarManagerImpl(Injector injector) {
        this.mInjector = injector;
        Context context = injector.mContext;
        this.mContext = context;
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mDualDarAuthUtils = new DualDarAuthUtils(context);
        this.mVirtualLockUtils = new VirtualLockUtils();
        this.mAlarmManager = (AlarmManager) injector.mContext.getSystemService(AlarmManager.class);
        this.mHandler =
                new Handler(
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("DualDarManagerImpl")
                                .getLooper());
    }

    public final boolean handleClearDualDarTrialExpiryTimer(Bundle bundle) {
        int i = bundle.getInt("user_id");
        LicenseExpiryAlarmListener licenseExpiryAlarmListener =
                (LicenseExpiryAlarmListener)
                        this.mLicenseExpiryAlarmListenerForUser.get(Integer.valueOf(i));
        if (licenseExpiryAlarmListener == null) {
            return false;
        }
        DDLog.d(
                "DualDarManagerImpl",
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        i, "handleRemoveLicenseExpiryTimer cancelled alarm successful for user : "),
                new Object[0]);
        this.mLicenseExpiryAlarmListenerForUser.remove(Integer.valueOf(i));
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mAlarmManager.cancel(licenseExpiryAlarmListener);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void handleDataLock(int i) {
        if (DualDarManager.isOnDeviceOwner(i)) {
            this.mInjector.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
                TelecomManager telecomManager =
                        (TelecomManager) this.mContext.getSystemService("telecom");
                Handler handler = this.mHandler;
                DualDarManagerImpl$$ExternalSyntheticLambda2
                        dualDarManagerImpl$$ExternalSyntheticLambda2 = this.hardResetTask;
                if (telecomManager != null && telecomManager.isInCall()) {
                    DDLog.d(
                            "DualDarManagerImpl",
                            "User is on the phone now. Check in 60 seconds again.",
                            new Object[0]);
                    handler.postDelayed(dualDarManagerImpl$$ExternalSyntheticLambda2, 60000L);
                } else if (powerManager != null
                        && powerManager.isInteractive()
                        && !delayDataLockOnceForDeviceOwner) {
                    DDLog.d(
                            "DualDarManagerImpl",
                            "User is interactive now. Delay once for 60 seconds.",
                            new Object[0]);
                    delayDataLockOnceForDeviceOwner = true;
                    handler.postDelayed(dualDarManagerImpl$$ExternalSyntheticLambda2, 60000L);
                } else if (powerManager != null) {
                    DDLog.d(
                            "DualDarManagerImpl",
                            "Perform hard reboot when datalock in DO case",
                            new Object[0]);
                    powerManager.reboot("data lock timeout for dualdar");
                } else {
                    DDLog.e("DualDarManagerImpl", "Failed to get PowerManager", new Object[0]);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final boolean handleDeviceOwnerProvisioning(Bundle bundle) {
        Log.d("DualDarManagerImpl", "handleDeviceOwnerProvisioning");
        SystemProperties.set("persist.sys.dualdar.do", "1");
        if (bundle != null) {
            final String string = bundle.getString("DUAL_DAR_ADMIN_PACKAGE");
            final String string2 = bundle.getString("dualdar-config-client-package");
            if (!TextUtils.isEmpty(string)) {
                final int i = 0;
                Optional.ofNullable((DarManagerService) ServiceManager.getService("dar"))
                        .ifPresent(
                                new Consumer() { // from class:
                                                 // com.android.server.knox.dar.ddar.core.DualDarManagerImpl$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i2 = i;
                                        String str = string;
                                        DarManagerService darManagerService =
                                                (DarManagerService) obj;
                                        switch (i2) {
                                            case 0:
                                                darManagerService.addBlockedClearablePackages(
                                                        0, str);
                                                break;
                                            default:
                                                darManagerService.addBlockedClearablePackages(
                                                        0, str);
                                                break;
                                        }
                                    }
                                });
            }
            if (!TextUtils.isEmpty(string2)) {
                final int i2 = 1;
                Optional.ofNullable((DarManagerService) ServiceManager.getService("dar"))
                        .ifPresent(
                                new Consumer() { // from class:
                                                 // com.android.server.knox.dar.ddar.core.DualDarManagerImpl$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i22 = i2;
                                        String str = string2;
                                        DarManagerService darManagerService =
                                                (DarManagerService) obj;
                                        switch (i22) {
                                            case 0:
                                                darManagerService.addBlockedClearablePackages(
                                                        0, str);
                                                break;
                                            default:
                                                darManagerService.addBlockedClearablePackages(
                                                        0, str);
                                                break;
                                        }
                                    }
                                });
            }
        }
        boolean handleDeviceOwnerProvisioning =
                DualDARController.getInstance(this.mContext).handleDeviceOwnerProvisioning();
        if (handleDeviceOwnerProvisioning) {
            ((TrustManager) this.mContext.getSystemService("trust"))
                    .setDeviceLockedForUser(0, false);
            int reserveUserIdForSystem = this.mVirtualLockUtils.reserveUserIdForSystem();
            if (reserveUserIdForSystem != -10000) {
                this.mDualDarAuthUtils.setMainUserId(0, reserveUserIdForSystem);
                this.mDualDarAuthUtils.setInnerAuthUserId(reserveUserIdForSystem, 0);
            }
        } else {
            SystemProperties.set("persist.sys.dualdar.do", "0");
        }
        return handleDeviceOwnerProvisioning;
    }

    public final boolean handleScheduleDualDarTrialExpiryTimer(Bundle bundle) {
        int i = bundle.getInt("user_id");
        long j = bundle.getLong("TRIAL_SCHEDULED_TIME");
        if (bundle.getBoolean("IS_CREATION")) {
            DDLog.d(
                    "DualDarManagerImpl",
                    "setting alarm for license expiry, isCreation = true",
                    new Object[0]);
            return scheduleDualDarTrialExpiryTimer(i, j);
        }
        if (((LicenseExpiryAlarmListener)
                        this.mLicenseExpiryAlarmListenerForUser.get(Integer.valueOf(i)))
                != null) {
            return false;
        }
        DDLog.d(
                "DualDarManagerImpl",
                "setting alarm for license expiry, isCreation = false",
                new Object[0]);
        return scheduleDualDarTrialExpiryTimer(i, j);
    }

    public final boolean scheduleDualDarTrialExpiryTimer(int i, long j) {
        LicenseExpiryAlarmListener licenseExpiryAlarmListener =
                new LicenseExpiryAlarmListener(this.mContext, i);
        this.mLicenseExpiryAlarmListenerForUser.put(Integer.valueOf(i), licenseExpiryAlarmListener);
        Injector injector = this.mInjector;
        injector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mAlarmManager.setExact(
                        2,
                        j,
                        "LicenseNotificationHandler.LicenseLockForUser",
                        licenseExpiryAlarmListener,
                        null);
                injector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                injector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            injector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
