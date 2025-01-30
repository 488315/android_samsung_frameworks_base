package com.android.systemui.bixby2.controller;

import android.R;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.util.BiometricPromptWrapperBixby;
import com.android.systemui.indexsearch.DetailPanelLaunchActivity;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.DeviceType;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.feature.SemCscFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DeviceController {
    private static final int ORIENTATION_LANDSCAPE = 1;
    private static final int ORIENTATION_PORTRAIT = 0;
    private static final String TAG = "DeviceController";
    private final Context mContext;
    private final FlashlightController mFlashlightController;
    private final RotationLockController mRotationLockController;
    private BiometricPromptWrapperBixby mBiometricPromptWrapperBixby = null;
    private LockPatternUtils mLockPatternUtils = null;
    private CancellationSignal mSignal = null;

    public DeviceController(Context context, FlashlightController flashlightController, RotationLockController rotationLockController) {
        this.mContext = context;
        this.mFlashlightController = flashlightController;
        this.mRotationLockController = rotationLockController;
    }

    private boolean isNeedSecureConfirm(ContentResolver contentResolver) {
        return (this.mLockPatternUtils.isRMMLockEnabled(ActivityManager.getCurrentUser()) || (Settings.System.getInt(contentResolver, "power_off_lock_option", 1) == 0)) ? false : true;
    }

    private boolean isScreenRotationSupported() {
        return true;
    }

    private boolean isSupportPowerOffLock() {
        return SemCscFeature.getInstance().getBoolean("CscFeature_SystemUI_SupportPowerOffLock");
    }

    private CommandActionResponse setOrientationMode(Context context, int i) {
        if (!isScreenRotationSupported()) {
            return new CommandActionResponse(2, null);
        }
        if (this.mRotationLockController == null) {
            return new CommandActionResponse(2, null);
        }
        boolean z = context.getResources().getConfiguration().orientation == 2;
        if (!z && i == 0) {
            return new CommandActionResponse(2, "already_set");
        }
        if (z && i == 1) {
            return new CommandActionResponse(2, "already_set");
        }
        boolean isRotationLocked = this.mRotationLockController.isRotationLocked();
        this.mRotationLockController.setRotationLockedAtAngle(i, true);
        return isRotationLocked ? new CommandActionResponse(1, "success") : new CommandActionResponse(1, ActionResults.RESULT_SUCCESS_AFTER_SET_OFF);
    }

    public PendingIntent getFlashLightIntent() {
        Intent intent = new Intent();
        intent.setAction("com.android.systemui.indexsearch.OPEN_DETAIL");
        intent.setClass(this.mContext, DetailPanelLaunchActivity.class);
        intent.putExtra("tileSpec", "Flashlight");
        return PendingIntent.getActivity(this.mContext, 0, intent, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
    }

    public int getFlashLightLevel() {
        return ((FlashlightControllerImpl) this.mFlashlightController).mFlashlightLevel + 1;
    }

    public boolean hasFlashLight() {
        return ((FlashlightControllerImpl) this.mFlashlightController).mHasFlashlight;
    }

    public boolean isAutoRotationEnabled() {
        return !this.mRotationLockController.isRotationLocked();
    }

    public boolean isFlashLightEnabled() {
        return ((FlashlightControllerImpl) this.mFlashlightController).isEnabled();
    }

    public void restartDevice(Context context) {
        Log.d(TAG, "restartDevice");
        IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        if (asInterface != null) {
            try {
                asInterface.rebootByBixby(false);
            } catch (RemoteException e) {
                Log.e(TAG, "reboot RemoteException ", e);
            }
        }
    }

    public CommandActionResponse setAutoRotate(boolean z) {
        if (this.mRotationLockController == null) {
            return new CommandActionResponse(2, null);
        }
        if (z == (!r0.isRotationLocked())) {
            return new CommandActionResponse(2, "already_set");
        }
        this.mRotationLockController.setRotationLocked(!z);
        return new CommandActionResponse(1, "success");
    }

    public CommandActionResponse setFlashlight(boolean z) {
        if (!((FlashlightControllerImpl) this.mFlashlightController).isAvailable()) {
            return new CommandActionResponse(2, null);
        }
        boolean isEnabled = ((FlashlightControllerImpl) this.mFlashlightController).isEnabled();
        if ((z && isEnabled) || (!z && !isEnabled)) {
            return new CommandActionResponse(2, "already_set");
        }
        ((FlashlightControllerImpl) this.mFlashlightController).setFlashlight(z);
        return new CommandActionResponse(1, "success");
    }

    public CommandActionResponse setFlashlightWithLevel(int i) {
        getFlashLightLevel();
        if (!((FlashlightControllerImpl) this.mFlashlightController).isAvailable()) {
            return new CommandActionResponse(2, null);
        }
        boolean isEnabled = ((FlashlightControllerImpl) this.mFlashlightController).isEnabled();
        int flashLightLevel = getFlashLightLevel();
        if (isEnabled && i == flashLightLevel) {
            return new CommandActionResponse(2, "already_set");
        }
        if (!isEnabled) {
            ((FlashlightControllerImpl) this.mFlashlightController).setFlashlight(true);
        }
        ((FlashlightControllerImpl) this.mFlashlightController).setFlashlightLevel(i, true);
        return new CommandActionResponse(1, "success");
    }

    public CommandActionResponse setLandscapeMode(Context context) {
        return setOrientationMode(context, 1);
    }

    public CommandActionResponse setPortraitMode(Context context) {
        return setOrientationMode(context, 0);
    }

    public void turnOffDevice(final Context context) {
        Log.d(TAG, "turnOffDevice");
        ContentResolver contentResolver = context.getContentResolver();
        IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        if (this.mSignal == null) {
            this.mSignal = new CancellationSignal();
        }
        if (this.mBiometricPromptWrapperBixby == null) {
            this.mBiometricPromptWrapperBixby = new BiometricPromptWrapperBixby(context);
        }
        if (this.mLockPatternUtils == null) {
            this.mLockPatternUtils = new LockPatternUtils(context);
        }
        if (this.mLockPatternUtils.isFMMLockEnabled(ActivityManager.getCurrentUser())) {
            Log.d(TAG, "isFMMLocked = true");
            final int i = DeviceType.isTablet() ? R.string.mediasize_iso_c8 : R.string.mediasize_iso_c7;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.systemui.bixby2.controller.DeviceController.1
                @Override // java.lang.Runnable
                public void run() {
                    Context context2 = context;
                    Toast.makeText(context2, context2.getResources().getString(i), 1).show();
                }
            }, 0L);
            return;
        }
        Log.d(TAG, "isFMMLocked = false");
        if (isSupportPowerOffLock() && isNeedSecureConfirm(contentResolver)) {
            Log.d(TAG, "init BiometricPrompt");
            this.mBiometricPromptWrapperBixby.initPrompt(" ", true);
            this.mBiometricPromptWrapperBixby.buildAndRun(this.mSignal);
        } else if (asInterface != null) {
            try {
                asInterface.shutdownByBixby();
            } catch (RemoteException e) {
                Log.e(TAG, "shutdown RemoteException ", e);
            }
        }
    }

    public void turnOffScreen(Context context) {
        try {
            ((PowerManager) context.getSystemService("power")).semGoToSleep(SystemClock.uptimeMillis());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
