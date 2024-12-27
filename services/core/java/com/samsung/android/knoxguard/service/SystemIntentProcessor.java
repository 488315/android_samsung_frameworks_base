package com.samsung.android.knoxguard.service;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;

import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.knoxguard.service.utils.IntegritySeUtil;
import com.samsung.android.knoxguard.service.utils.Utils;

public final class SystemIntentProcessor {
    public static final String KEY_URI = "uri";
    public static final String TAG = "KG.SystemIntentProcessor";

    public static int getResult(KgErrWrapper kgErrWrapper) {
        if (kgErrWrapper == null) {
            return -1000;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("err wrapper = "), kgErrWrapper.err, TAG);
        int i = kgErrWrapper.err;
        return i == 0 ? kgErrWrapper.result : i;
    }

    public static void handleBootCompleted(Context context) {
        Slog.i(TAG, "handling boot completed");
        if (context == null) {
            return;
        }
        int stateAndSetToKGSystemProperty = Utils.getStateAndSetToKGSystemProperty();
        int taErrorCode = KnoxGuardSeService.getTaErrorCode();
        boolean isSetupWizardFinished = Utils.isSetupWizardFinished(context);
        Slog.d(TAG, "isSetupWizardFinished : " + isSetupWizardFinished);
        IntegritySeUtil.TAIntegrityResult checkTaIntegrity =
                IntegritySeUtil.checkTaIntegrity(stateAndSetToKGSystemProperty, taErrorCode);
        if (!checkTaIntegrity.isOk) {
            Slog.i(TAG, "checkTaIntegrity false. Lock device without passcode.");
            Utils.lockSeDevice(
                    context, IntegritySeUtil.getTALockScreenErrorCode(checkTaIntegrity.errorCode));
            return;
        }
        if (!IntegritySeUtil.checkAPSerialIntegrity(stateAndSetToKGSystemProperty)) {
            Slog.i(TAG, "checkAPSerialIntegrity false. Lock device without passcode.");
            Utils.lockSeDevice(context, Constants.ERROR_KGTA_APSERIAL_FAILED);
            return;
        }
        IntegritySeUtil.IntegritySeResult checkKGClientIntegrityAndEnableComponentsWithFlag =
                IntegritySeUtil.checkKGClientIntegrityAndEnableComponentsWithFlag(
                        context, stateAndSetToKGSystemProperty, true);
        if (checkKGClientIntegrityAndEnableComponentsWithFlag.isOk) {
            Slog.i(TAG, "checkKGClientIntegrity true. ");
            if (3 == stateAndSetToKGSystemProperty) {
                if (isSetupWizardFinished) {
                    try {
                        KnoxGuardSeService.bindAndSetToLockScreen();
                        Slog.i(TAG, "KG bindToLockScreen");
                    } catch (Throwable th) {
                        Slog.e(TAG, th.getMessage(), th);
                    }
                } else {
                    new Handler(Looper.getMainLooper())
                            .postDelayed(
                                    new SystemIntentProcessor$$ExternalSyntheticLambda0(), 300000L);
                }
            }
        } else {
            if (Utils.isChinaDevice()) {
                if (stateAndSetToKGSystemProperty == 0) {
                    Slog.i(TAG, "Device country is China");
                    setCheckingStateToKg();
                    return;
                } else {
                    if (isSetupWizardFinished
                            && Utils.isStateForEnrolledDevice(stateAndSetToKGSystemProperty)) {
                        Slog.i(
                                TAG,
                                "China Binary and previously enrolled. Lock device without"
                                    + " passcode.");
                        Utils.autoLockDevice(context, Constants.ERROR_CLIENT_INTEGRITY_FOR_CHINA);
                        return;
                    }
                    return;
                }
            }
            Utils.lockSeDevice(
                    context,
                    String.valueOf(
                            IntegritySeUtil.toErrorCode(
                                    checkKGClientIntegrityAndEnableComponentsWithFlag)));
            Slog.i(TAG, "checkKGClientIntegrity false. Lock device without passcode.");
        }
        if (Utils.isStateForEnrolledDevice(stateAndSetToKGSystemProperty)) {
            IntegritySeUtil.checkSystemUiIntegrity(context);
        }
    }

    public static void handlePackageDataCleared(Context context, Bundle bundle) {
        Slog.i(TAG, "handling package data cleared");
        if (bundle == null
                || context == null
                || bundle.getParcelable(KEY_URI) == null
                || !"com.samsung.android.kgclient"
                        .equals(((Uri) bundle.getParcelable(KEY_URI)).getSchemeSpecificPart())
                || 3 != KnoxGuardNative.getTAState()) {
            return;
        }
        Utils.lockSeDevice(context, Constants.ERROR_CLIENT_APP_DATA_CLEARED);
        Slog.i(TAG, "Client data was cleared. Lock device");
    }

    public static void handlePackageReplacedOrRemoved(Context context, Bundle bundle) {
        Slog.i(TAG, "handling package replaced or removed");
        if (bundle == null || context == null || bundle.getParcelable(KEY_URI) == null) {
            return;
        }
        Uri uri = (Uri) bundle.getParcelable(KEY_URI);
        if (!"com.samsung.android.kgclient".equals(uri.getSchemeSpecificPart())) {
            if (Constants.SYSTEMUI_PACKAGE_NAME.equals(uri.getSchemeSpecificPart())
                    && Utils.isStateForEnrolledDevice(KnoxGuardNative.getTAState())) {
                IntegritySeUtil.checkSystemUiIntegrity(context);
                return;
            }
            return;
        }
        IntegritySeUtil.IntegritySeResult checkKGClientIntegrityAndEnableComponentsWithFlag =
                IntegritySeUtil.checkKGClientIntegrityAndEnableComponentsWithFlag(
                        context, KnoxGuardNative.getTAState(), false);
        if (checkKGClientIntegrityAndEnableComponentsWithFlag.isOk) {
            Slog.i(TAG, "checkKGClientIntegrity true. Do nothing.");
        } else {
            Utils.lockSeDevice(
                    context,
                    String.valueOf(
                            IntegritySeUtil.toErrorCode(
                                    checkKGClientIntegrityAndEnableComponentsWithFlag)));
            Slog.i(TAG, "checkKGClientIntegrity false. Lock device without passcode.");
        }
    }

    public static void handleSetupWizardCompleted(Context context) {
        Slog.i(TAG, "handling setup wizard completed");
        if (context == null) {
            return;
        }
        int tAState = KnoxGuardNative.getTAState();
        if (Utils.isChinaDevice() && Utils.isStateForEnrolledDevice(tAState)) {
            Slog.i(TAG, "China Binary and previously enrolled. Lock device without passcode.");
            Utils.autoLockDevice(context, Constants.ERROR_CLIENT_INTEGRITY_FOR_CHINA);
        }
    }

    public static void handleUserPresent(Context context) {
        Slog.i(TAG, "handling user present");
        if (context == null) {
            return;
        }
        int tAState = KnoxGuardNative.getTAState();
        HermesService$3$$ExternalSyntheticOutline0.m(tAState, "Locking device ", TAG);
        if (3 != tAState) {
            Utils.lockSeDevice(context, Constants.ERROR_LOCK_FROM_USER_PRESENT);
            return;
        }
        try {
            KnoxGuardSeService.bindAndSetToLockScreen();
            Slog.i(TAG, "KG bindToLockScreen");
        } catch (Throwable th) {
            Slog.e(TAG, th.getMessage(), th);
        }
    }

    public static /* synthetic */ void lambda$handleBootCompleted$0() {
        if (3 == KnoxGuardNative.getTAState()) {
            try {
                KnoxGuardSeService.bindAndSetToLockScreen();
                Slog.i(TAG, "KG bindToLockScreen");
            } catch (Throwable th) {
                Slog.e(TAG, th.getMessage(), th);
            }
        }
    }

    public static void setCheckingStateToKg() {
        Slog.d(TAG, "setCheckingStateToKg");
        try {
            Slog.d(
                    TAG,
                    "setCheckingStateToKg result "
                            + getResult(
                                    KnoxGuardNative.tz_userChecking(
                                            KnoxGuardNative.KGTA_PARAM_DEFAULT)));
        } catch (Exception e) {
            Slog.e(TAG, "setCheckingStateToKg Exception " + e.getMessage(), e);
        }
    }
}
