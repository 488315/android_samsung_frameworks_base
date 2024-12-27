package com.android.server.policy;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Slog;
import android.view.Display;
import android.view.WindowManager;
import com.android.internal.os.Clock;
import com.android.server.LocalServices;
import com.android.server.policy.WindowWakeUpPolicyInternal;
import com.samsung.android.os.SemDvfsManager;
import com.sec.android.sdhms.ISamsungDeviceHealthManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowWakeUpPolicy {
    public static ISamsungDeviceHealthManager mSdhmsService;
    public final boolean mAllowTheaterModeWakeFromCameraLens;
    public final boolean mAllowTheaterModeWakeFromKey;
    public final boolean mAllowTheaterModeWakeFromLidSwitch;
    public final boolean mAllowTheaterModeWakeFromMotion;
    public final boolean mAllowTheaterModeWakeFromPowerKey;
    public final boolean mAllowTheaterModeWakeFromWakeGesture;
    public final Clock mClock;
    public final Context mContext;
    public WindowWakeUpPolicyInternal.InputWakeUpDelegate mInputWakeUpDelegate;
    public final PowerManager mPowerManager;
    public final WindowManager mWindowManager;
    public SemDvfsManager mSemRotationBooster = null;
    public SemDvfsManager mSemWakeUpBooster = null;
    public final Object mBoosterLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService implements WindowWakeUpPolicyInternal {
        public LocalService() {
        }

        @Override // com.android.server.policy.WindowWakeUpPolicyInternal
        public final void setInputWakeUpDelegate(WindowWakeUpPolicyInternal.InputWakeUpDelegate inputWakeUpDelegate) {
            Flags.supportInputWakeupDelegate();
            WindowWakeUpPolicy.this.mInputWakeUpDelegate = inputWakeUpDelegate;
        }
    }

    public WindowWakeUpPolicy(Context context, Clock clock) {
        this.mContext = context;
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mClock = clock;
        Resources resources = context.getResources();
        boolean z = resources.getBoolean(R.bool.config_allowTheaterModeWakeFromMotionWhenNotDreaming);
        this.mAllowTheaterModeWakeFromKey = z;
        this.mAllowTheaterModeWakeFromPowerKey = z || resources.getBoolean(R.bool.config_allow_pin_storage_for_unattended_reboot);
        this.mAllowTheaterModeWakeFromMotion = resources.getBoolean(R.bool.config_allowTheaterModeWakeFromUnplug);
        this.mAllowTheaterModeWakeFromCameraLens = resources.getBoolean(R.bool.config_allowTheaterModeWakeFromKey);
        this.mAllowTheaterModeWakeFromLidSwitch = resources.getBoolean(R.bool.config_allowTheaterModeWakeFromPowerKey);
        this.mAllowTheaterModeWakeFromWakeGesture = resources.getBoolean(R.bool.config_allowTheaterModeWakeFromMotion);
        Flags.supportInputWakeupDelegate();
        LocalServices.addService(WindowWakeUpPolicyInternal.class, new LocalService());
        initSdhmsService();
    }

    public static void boosterAcquireLocked(SemDvfsManager semDvfsManager, String str) {
        if (semDvfsManager == null) {
            return;
        }
        try {
            Slog.d("WindowWakeUpPolicy", str.concat(" acquire()"));
            semDvfsManager.acquire();
        } catch (Exception unused) {
            Slog.e("WindowWakeUpPolicy", str.concat(" acquire is failed"));
        }
    }

    public static void initSdhmsService() {
        IBinder service;
        try {
            if (mSdhmsService != null || (service = ServiceManager.getService("sdhms")) == null) {
                return;
            }
            mSdhmsService = ISamsungDeviceHealthManager.Stub.asInterface(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final boolean canWakeUp(boolean z) {
        Flags.supportInputWakeupDelegate();
        if (Display.isOnState(this.mWindowManager.getDefaultDisplay().getState())) {
            return true;
        }
        return z || !(Settings.Global.getInt(this.mContext.getContentResolver(), "theater_mode_on", 0) == 1);
    }

    public final void wakeUp(long j, int i, String str) {
        synchronized (this.mBoosterLock) {
            try {
                if (this.mSemWakeUpBooster == null) {
                    SemDvfsManager createInstance = SemDvfsManager.createInstance(this.mContext, "DEVICE_WAKEUP");
                    this.mSemWakeUpBooster = createInstance;
                    if (createInstance != null) {
                        createInstance.setHint(19);
                    }
                }
                boosterAcquireLocked(this.mSemWakeUpBooster, "HINT_DEVICE_WAKEUP");
            } catch (Throwable th) {
                throw th;
            }
        }
        if (mSdhmsService == null) {
            initSdhmsService();
        }
        ISamsungDeviceHealthManager iSamsungDeviceHealthManager = mSdhmsService;
        if (iSamsungDeviceHealthManager != null) {
            try {
                iSamsungDeviceHealthManager.sendCommand("WAKEUP", "start");
            } catch (DeadObjectException e) {
                mSdhmsService = null;
                initSdhmsService();
                Slog.e("WindowWakeUpPolicy", "re-try to initSdhmsService");
                e.printStackTrace();
            } catch (Exception e2) {
                mSdhmsService = null;
                Slog.e("WindowWakeUpPolicy", "sendCommandToSdhms fail");
                e2.printStackTrace();
            }
        }
        this.mPowerManager.wakeUp(j, i, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("android.policy:", str));
    }
}
