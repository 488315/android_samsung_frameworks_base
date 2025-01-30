package com.samsung.android.knoxguard.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Slog;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.IntentRelayManager;
import com.samsung.android.knoxguard.service.KgErrWrapper;
import com.samsung.android.knoxguard.service.KnoxGuardNative;
import com.samsung.android.knoxguard.service.KnoxGuardSeService;
import com.samsung.android.knoxguard.service.utils.IntegritySeUtil;
import com.samsung.android.knoxguard.service.utils.Utils;

/* loaded from: classes2.dex */
public class SystemSeReceiver extends BroadcastReceiver {
    public static final String TAG = "KG." + SystemSeReceiver.class.getSimpleName();

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            Slog.w(TAG, "intent is null");
            return;
        }
        String action = intent.getAction();
        String str = TAG;
        Slog.i(str, "onReceive " + action);
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            Slog.i(str, "Received action " + action);
            int stateAndSetToKGSystemProperty = Utils.getStateAndSetToKGSystemProperty();
            int taErrorCode = KnoxGuardSeService.getTaErrorCode();
            boolean isSetupWizardFinished = Utils.isSetupWizardFinished(context);
            Slog.d(str, "isSetupWizardFinished : " + isSetupWizardFinished);
            if (!IntegritySeUtil.checkTaIntegrity(taErrorCode)) {
                Slog.i(str, "checkTaIntegrity false. Lock device without passcode.");
                Utils.lockSeDevice(context, "1001");
                return;
            }
            if (!IntegritySeUtil.checkAPSerialIntegrity(stateAndSetToKGSystemProperty)) {
                Slog.i(str, "checkAPSerialIntegrity false. Lock device without passcode.");
                Utils.lockSeDevice(context, "1002");
                return;
            }
            IntegritySeUtil.IntegritySeResult checkKGClientIntegrityAndEnableComponent = IntegritySeUtil.checkKGClientIntegrityAndEnableComponent(context, stateAndSetToKGSystemProperty);
            if (checkKGClientIntegrityAndEnableComponent.isOk) {
                Slog.i(str, "checkKGClientIntegrity true. ");
                if (3 != stateAndSetToKGSystemProperty) {
                    Utils.startClientHealthCheckAlarm(context);
                } else if (isSetupWizardFinished) {
                    try {
                        KnoxGuardSeService.bindAndSetToLockScreen();
                        Slog.i(str, "KG bindToLockScreen");
                    } catch (Throwable th) {
                        Slog.e(TAG, th.getMessage(), th);
                    }
                } else {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.samsung.android.knoxguard.service.receiver.SystemSeReceiver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SystemSeReceiver.lambda$onReceive$0();
                        }
                    }, BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
                }
            } else {
                if (Utils.isChinaDevice()) {
                    if (stateAndSetToKGSystemProperty == 0) {
                        Slog.i(str, "Device country is China");
                        setCheckingStateToKg();
                        return;
                    } else {
                        if (isSetupWizardFinished && Utils.isStateForEnrolledDevice(stateAndSetToKGSystemProperty)) {
                            Slog.i(str, "China Binary and previously enrolled. Lock device without passcode.");
                            Utils.autoLockDevice(context, "3040");
                            return;
                        }
                        return;
                    }
                }
                Utils.lockSeDevice(context, String.valueOf(IntegritySeUtil.toErrorCode(checkKGClientIntegrityAndEnableComponent)));
                Slog.i(str, "checkKGClientIntegrity false. Lock device without passcode.");
            }
            if (Utils.isStateForEnrolledDevice(stateAndSetToKGSystemProperty)) {
                IntegritySeUtil.checkSystemUiIntegrity(context);
                return;
            }
            return;
        }
        if ("android.intent.action.USER_PRESENT".equals(action)) {
            int tAState = KnoxGuardNative.getTAState();
            Slog.i(str, "Locking device " + tAState);
            if (3 == tAState) {
                try {
                    KnoxGuardSeService.bindAndSetToLockScreen();
                    return;
                } catch (Throwable th2) {
                    Slog.e(TAG, th2.getMessage(), th2);
                    return;
                }
            }
            Utils.lockSeDevice(context, "2003");
            return;
        }
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            if (intent.getData() == null || !KnoxCustomManagerService.KG_PKG_NAME.equals(intent.getData().getSchemeSpecificPart())) {
                return;
            }
            IntentRelayManager.sendRequestedIntent(context, "com.samsung.kgclient.android.intent.action.KG_PACKAGE_ADDED", intent.getExtras());
            return;
        }
        if ("android.intent.action.PACKAGE_REPLACED".equals(action) || "android.intent.action.PACKAGE_REMOVED".equals(action) || "android.intent.action.PACKAGE_CHANGED".equals(action)) {
            if (intent.getData() != null) {
                if (KnoxCustomManagerService.KG_PKG_NAME.equals(intent.getData().getSchemeSpecificPart())) {
                    IntegritySeUtil.IntegritySeResult checkKGClientIntegrity = IntegritySeUtil.checkKGClientIntegrity(context, KnoxGuardNative.getTAState());
                    if (checkKGClientIntegrity.isOk) {
                        Slog.i(str, "checkKGClientIntegrity true. Do nothing.");
                        return;
                    } else {
                        Utils.lockSeDevice(context, String.valueOf(IntegritySeUtil.toErrorCode(checkKGClientIntegrity)));
                        Slog.i(str, "checkKGClientIntegrity false. Lock device without passcode.");
                        return;
                    }
                }
                if ("com.android.systemui".equals(intent.getData().getSchemeSpecificPart()) && Utils.isStateForEnrolledDevice(KnoxGuardNative.getTAState())) {
                    IntegritySeUtil.checkSystemUiIntegrity(context);
                    return;
                }
                return;
            }
            return;
        }
        if ("com.sec.android.app.secsetupwizard.SETUPWIZARD_COMPLETE".equals(action) || "com.sec.android.app.setupwizard.SETUPWIZARD_COMPLETE".equals(action)) {
            int tAState2 = KnoxGuardNative.getTAState();
            if (Utils.isChinaDevice() && Utils.isStateForEnrolledDevice(tAState2)) {
                Slog.i(str, "China Binary and previously enrolled. Lock device without passcode.");
                Utils.autoLockDevice(context, "3040");
                return;
            }
            return;
        }
        if ("android.intent.action.PACKAGE_DATA_CLEARED".equals(action) && intent.getData() != null && KnoxCustomManagerService.KG_PKG_NAME.equals(intent.getData().getSchemeSpecificPart()) && 3 == KnoxGuardNative.getTAState()) {
            Utils.lockSeDevice(context, "3001");
            Slog.i(str, "Client data was cleared. Lock device");
        }
    }

    public static /* synthetic */ void lambda$onReceive$0() {
        if (3 == KnoxGuardNative.getTAState()) {
            try {
                KnoxGuardSeService.bindAndSetToLockScreen();
                Slog.i(TAG, "KG bindToLockScreen");
            } catch (Throwable th) {
                Slog.e(TAG, th.getMessage(), th);
            }
        }
    }

    public final void setCheckingStateToKg() {
        String str = TAG;
        Slog.d(str, "setCheckingStateToKg");
        try {
            Slog.d(str, "setCheckingStateToKg result " + getResult(KnoxGuardNative.userCheckingRefactor()));
        } catch (Exception e) {
            Slog.e(TAG, "setCheckingStateToKg Exception " + e.getMessage(), e);
        }
    }

    public final int getResult(KgErrWrapper kgErrWrapper) {
        if (kgErrWrapper == null) {
            return -1000;
        }
        Slog.d(TAG, "err wrapper = " + kgErrWrapper.err);
        int i = kgErrWrapper.err;
        return i == 0 ? kgErrWrapper.result : i;
    }
}
