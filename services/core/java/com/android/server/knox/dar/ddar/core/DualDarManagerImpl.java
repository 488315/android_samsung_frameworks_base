package com.android.server.knox.dar.ddar.core;

import android.app.AlarmManager;
import android.app.trust.TrustManager;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.EnterprisePartitionManager;
import com.android.server.knox.dar.ddar.DDLog;
import com.samsung.android.knox.dar.VirtualLockUtils;
import com.samsung.android.knox.dar.ddar.DualDARController;
import com.samsung.android.knox.dar.ddar.DualDarAuthUtils;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.dar.ddar.fsm.Event;
import com.samsung.android.knox.dar.ddar.fsm.StateMachine;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManager;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class DualDarManagerImpl {
    public static boolean delayDataLockOnceForDeviceOwner = false;
    public final Runnable hardResetTask;
    public final AlarmManager mAlarmManager;
    public final Context mContext;
    public final DualDarAuthUtils mDualDarAuthUtils;
    public Handler mHandler;
    public final Injector mInjector;
    public final ArrayMap mLicenseExpiryAlarmListenerForUser;
    public final LockPatternUtils mLockPatternUtils;
    public final VirtualLockUtils mVirtualLockUtils;

    public DualDarManagerImpl(Context context) {
        this(new Injector(context));
    }

    public DualDarManagerImpl(Injector injector) {
        this.mLicenseExpiryAlarmListenerForUser = new ArrayMap();
        this.hardResetTask = new Runnable() { // from class: com.android.server.knox.dar.ddar.core.DualDarManagerImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DualDarManagerImpl.this.lambda$new$2();
            }
        };
        this.mInjector = injector;
        Context context = injector.getContext();
        this.mContext = context;
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mDualDarAuthUtils = new DualDarAuthUtils(context);
        this.mVirtualLockUtils = new VirtualLockUtils();
        this.mAlarmManager = injector.getAlarmManager();
        HandlerThread handlerThread = new HandlerThread("DualDarManagerImpl");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
    }

    public class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public Context getContext() {
            return this.mContext;
        }

        public AlarmManager getAlarmManager() {
            return (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
        }

        public long binderClearCallingIdentity() {
            return Binder.clearCallingIdentity();
        }

        public void binderRestoreCallingIdentity(long j) {
            Binder.restoreCallingIdentity(j);
        }
    }

    public boolean handleDeviceOwnerProvisioning(Bundle bundle) {
        Log.d("DualDarManagerImpl", "handleDeviceOwnerProvisioning");
        setDualDarDoProperty(true);
        updateBlockedClearablePackages(bundle);
        boolean handleDeviceOwnerProvisioning = DualDARController.getInstance(this.mContext).handleDeviceOwnerProvisioning();
        if (handleDeviceOwnerProvisioning) {
            setDeviceUnlockedForUser(0, false);
            setInnerAuthUserId();
        } else {
            setDualDarDoProperty(false);
        }
        return handleDeviceOwnerProvisioning;
    }

    public final void setDualDarDoProperty(boolean z) {
        SystemProperties.set("persist.sys.dualdar.do", z ? "1" : "0");
    }

    public final void updateBlockedClearablePackages(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        final String string = bundle.getString("DUAL_DAR_ADMIN_PACKAGE");
        final String string2 = bundle.getString("dualdar-config-client-package");
        if (!TextUtils.isEmpty(string)) {
            getDarManagerService().ifPresent(new Consumer() { // from class: com.android.server.knox.dar.ddar.core.DualDarManagerImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((DarManagerService) obj).addBlockedClearablePackages(0, string);
                }
            });
        }
        if (TextUtils.isEmpty(string2)) {
            return;
        }
        getDarManagerService().ifPresent(new Consumer() { // from class: com.android.server.knox.dar.ddar.core.DualDarManagerImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((DarManagerService) obj).addBlockedClearablePackages(0, string2);
            }
        });
    }

    public final Optional getDarManagerService() {
        return Optional.ofNullable((DarManagerService) ServiceManager.getService("dar"));
    }

    public final void setInnerAuthUserId() {
        int reserveUserIdForSystem = this.mVirtualLockUtils.reserveUserIdForSystem();
        if (reserveUserIdForSystem != -10000) {
            this.mDualDarAuthUtils.setMainUserId(0, reserveUserIdForSystem);
            this.mDualDarAuthUtils.setInnerAuthUserId(reserveUserIdForSystem, 0);
        }
    }

    public boolean handleEnsureDataUnlocked() {
        if (this.mLockPatternUtils.isSecure(0)) {
            Log.d("DualDarManagerImpl", "handleEnsureDataUnlocked - Not able to ensure while device lockscreen is set");
            return false;
        }
        StateMachine.processEvent(0, Event.DEVICE_AUTH_SUCCESS);
        setDeviceUnlockedForUser(0, false);
        return true;
    }

    public final void setDeviceUnlockedForUser(int i, boolean z) {
        ((TrustManager) this.mContext.getSystemService("trust")).setDeviceLockedForUser(i, z);
    }

    public boolean isInnerLayerUnlocked() {
        boolean dualDARLockstate = EnterprisePartitionManager.getInstance(this.mContext).getDualDARLockstate();
        StringBuilder sb = new StringBuilder();
        sb.append("isInnerLayerUnlocked - result : ");
        sb.append(!dualDARLockstate);
        Log.d("DualDarManagerImpl", sb.toString());
        return !dualDARLockstate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        handleDataLock(0);
    }

    public void handleDataLock(int i) {
        if (DualDarManager.isOnDeviceOwner(i)) {
            long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
            try {
                PowerManager powerManager = getPowerManager();
                TelecomManager telecomManager = getTelecomManager();
                if (telecomManager != null && telecomManager.isInCall()) {
                    DDLog.m33d("DualDarManagerImpl", "User is on the phone now. Check in 60 seconds again.", new Object[0]);
                    this.mHandler.postDelayed(this.hardResetTask, 60000L);
                } else if (powerManager != null && powerManager.isInteractive() && !delayDataLockOnceForDeviceOwner) {
                    DDLog.m33d("DualDarManagerImpl", "User is interactive now. Delay once for 60 seconds.", new Object[0]);
                    delayDataLockOnceForDeviceOwner = true;
                    this.mHandler.postDelayed(this.hardResetTask, 60000L);
                } else {
                    DDLog.m33d("DualDarManagerImpl", "Perform hard reboot when datalock in DO case", new Object[0]);
                    powerManager.reboot("data lock timeout for dualdar");
                }
            } finally {
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            }
        }
    }

    public final PowerManager getPowerManager() {
        return (PowerManager) this.mContext.getSystemService("power");
    }

    public final TelecomManager getTelecomManager() {
        return (TelecomManager) this.mContext.getSystemService("telecom");
    }

    public void cancelDataLock(int i) {
        if (DualDarManager.isOnDeviceOwner(i)) {
            DDLog.m33d("DualDarManagerImpl", "Cancel data lock", new Object[0]);
            delayDataLockOnceForDeviceOwner = false;
            this.mHandler.removeCallbacks(this.hardResetTask);
        }
    }

    public boolean handleScheduleDualDarTrialExpiryTimer(Bundle bundle) {
        int i = bundle.getInt("user_id");
        long j = bundle.getLong("TRIAL_SCHEDULED_TIME");
        if (bundle.getBoolean("IS_CREATION")) {
            DDLog.m33d("DualDarManagerImpl", "setting alarm for license expiry, isCreation = true", new Object[0]);
            return scheduleDualDarTrialExpiryTimer(i, j);
        }
        if (((LicenseExpiryAlarmListener) this.mLicenseExpiryAlarmListenerForUser.get(Integer.valueOf(i))) != null) {
            return false;
        }
        DDLog.m33d("DualDarManagerImpl", "setting alarm for license expiry, isCreation = false", new Object[0]);
        return scheduleDualDarTrialExpiryTimer(i, j);
    }

    public final boolean scheduleDualDarTrialExpiryTimer(int i, long j) {
        LicenseExpiryAlarmListener licenseExpiryAlarmListener = new LicenseExpiryAlarmListener(this.mContext, i);
        this.mLicenseExpiryAlarmListenerForUser.put(Integer.valueOf(i), licenseExpiryAlarmListener);
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            try {
                this.mAlarmManager.setExact(2, j, "LicenseNotificationHandler.LicenseLockForUser", licenseExpiryAlarmListener, null);
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            throw th;
        }
    }

    public boolean handleClearDualDarTrialExpiryTimer(Bundle bundle) {
        int i = bundle.getInt("user_id");
        LicenseExpiryAlarmListener licenseExpiryAlarmListener = (LicenseExpiryAlarmListener) this.mLicenseExpiryAlarmListenerForUser.get(Integer.valueOf(i));
        if (licenseExpiryAlarmListener == null) {
            return false;
        }
        DDLog.m33d("DualDarManagerImpl", "handleRemoveLicenseExpiryTimer cancelled alarm successful for user : " + i, new Object[0]);
        this.mLicenseExpiryAlarmListenerForUser.remove(Integer.valueOf(i));
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            try {
                this.mAlarmManager.cancel(licenseExpiryAlarmListener);
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            throw th;
        }
    }

    public class LicenseExpiryAlarmListener implements AlarmManager.OnAlarmListener {
        public final Context mContext;
        public final int mUserId;

        public LicenseExpiryAlarmListener(Context context, int i) {
            this.mContext = context;
            this.mUserId = i;
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            DDLog.m33d("DualDarManagerImpl", "LicenseExpiryAlarmListener onAlarm() " + this.mUserId, new Object[0]);
            Bundle bundle = new Bundle();
            bundle.putInt("user_id", this.mUserId);
            KnoxProxyManager.getInstance(this.mContext).relayMessage("KNOXCORE_PROXY_AGENT", "DUALDAR_CONTROLLER_SERVICE", "EXPIRE_TRIAL_PERIOD_TIME", bundle);
        }
    }
}
