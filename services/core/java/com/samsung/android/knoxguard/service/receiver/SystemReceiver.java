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
import com.samsung.android.knoxguard.service.KnoxGuardService;
import com.samsung.android.knoxguard.service.utils.IntegrityUtil;
import com.samsung.android.knoxguard.service.utils.Utils;

/* loaded from: classes2.dex */
public class SystemReceiver extends BroadcastReceiver {
    public static final String TAG = "KG." + SystemReceiver.class.getSimpleName();

    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            Slog.w(TAG, "intent is null");
            return;
        }
        String action = intent.getAction();
        String str = TAG;
        Slog.i(str, "onReceive " + action);
        if (KnoxGuardService.getActionList() != null && KnoxGuardService.getActionList().contains(action)) {
            IntentRelayManager.sendRequestedIntent(context, KnoxGuardService.mPreFix + "." + action, intent.getExtras());
        }
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            Slog.i(str, "Received action " + action);
            String rlcState = Utils.getRlcState(context);
            boolean isSetupWizardFinished = Utils.isSetupWizardFinished(context);
            Slog.d(str, "isSetupWizardFinished : " + isSetupWizardFinished);
            IntegrityUtil.IntegrityResult checkKGClientIntegrity = IntegrityUtil.checkKGClientIntegrity(context, rlcState);
            if (checkKGClientIntegrity.isOk) {
                Slog.i(str, "checkKGClientIntegrity true. ");
                if (rlcState != null && "Locked".equalsIgnoreCase(rlcState)) {
                    if (isSetupWizardFinished) {
                        try {
                            KnoxGuardService.getKGVM().bindToLockScreen();
                            Slog.i(str, "KGVM bindToLockScreen");
                        } catch (Throwable th) {
                            Slog.e(TAG, th.getMessage(), th);
                        }
                    } else {
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.samsung.android.knoxguard.service.receiver.SystemReceiver$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SystemReceiver.lambda$onReceive$0(context);
                            }
                        }, BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
                    }
                }
            } else {
                if (Utils.isChinaDevice()) {
                    if (rlcState != null && "Prenormal".equalsIgnoreCase(rlcState)) {
                        Slog.i(str, "Device country is China");
                        Utils.setCheckingStateToRlc(context);
                        return;
                    } else {
                        if (isSetupWizardFinished && Utils.isStateForEnrolledDevice(rlcState)) {
                            Slog.i(str, "China Binary and previously enrolled. Lock device without passcode.");
                            Utils.autoLockDevice(context, "3040");
                            return;
                        }
                        return;
                    }
                }
                Utils.lockDevice(context, String.valueOf(IntegrityUtil.toErrorCode(checkKGClientIntegrity)));
                Slog.i(str, "checkKGClientIntegrity false. Lock device without passcode.");
            }
            if (Utils.isStateForEnrolledDevice(rlcState)) {
                IntegrityUtil.checkSystemUiIntegrity(context);
                return;
            }
            return;
        }
        if ("android.intent.action.USER_PRESENT".equals(action)) {
            Slog.i(str, "Received action " + action);
            String rlcState2 = Utils.getRlcState(context);
            if (rlcState2 == null || !"Locked".equalsIgnoreCase(rlcState2)) {
                return;
            }
            try {
                KnoxGuardService.getKGVM().bindToLockScreen();
                Slog.i(str, "KGVM bindToLockScreen");
                return;
            } catch (Throwable th2) {
                Slog.e(TAG, th2.getMessage(), th2);
                return;
            }
        }
        if ("android.intent.action.PACKAGE_REPLACED".equals(action) || "android.intent.action.PACKAGE_REMOVED".equals(action) || "android.intent.action.PACKAGE_CHANGED".equals(action)) {
            if (intent.getData() != null) {
                if (KnoxCustomManagerService.KG_PKG_NAME.equals(intent.getData().getSchemeSpecificPart())) {
                    IntegrityUtil.IntegrityResult checkKGClientIntegrity2 = IntegrityUtil.checkKGClientIntegrity(context, Utils.getRlcState(context));
                    if (checkKGClientIntegrity2.isOk) {
                        Slog.i(str, "checkKGClientIntegrity true. Do nothing.");
                        return;
                    } else {
                        Utils.lockDevice(context, String.valueOf(IntegrityUtil.toErrorCode(checkKGClientIntegrity2)));
                        Slog.i(str, "checkKGClientIntegrity false. Lock device without passcode.");
                        return;
                    }
                }
                if ("com.android.systemui".equals(intent.getData().getSchemeSpecificPart()) && Utils.isStateForEnrolledDevice(Utils.getRlcState(context))) {
                    IntegrityUtil.checkSystemUiIntegrity(context);
                    return;
                }
                return;
            }
            return;
        }
        if ("com.sec.android.app.secsetupwizard.SETUPWIZARD_COMPLETE".equals(action) || "com.sec.android.app.setupwizard.SETUPWIZARD_COMPLETE".equals(action)) {
            String rlcState3 = Utils.getRlcState(context);
            if (Utils.isChinaDevice() && Utils.isStateForEnrolledDevice(rlcState3)) {
                Slog.i(str, "China Binary and previously enrolled. Lock device without passcode.");
                Utils.autoLockDevice(context, "3040");
                return;
            }
            return;
        }
        if ("android.intent.action.PACKAGE_DATA_CLEARED".equals(action) && intent.getData() != null && KnoxCustomManagerService.KG_PKG_NAME.equals(intent.getData().getSchemeSpecificPart()) && "Locked".equalsIgnoreCase(Utils.getRlcState(context))) {
            Utils.lockDevice(context, "3001");
            Slog.i(str, "Client data was cleared. Lock device");
        }
    }

    public static /* synthetic */ void lambda$onReceive$0(Context context) {
        if ("Locked".equalsIgnoreCase(Utils.getRlcState(context))) {
            try {
                KnoxGuardService.getKGVM().bindToLockScreen();
                Slog.i(TAG, "KGVM bindToLockScreen");
            } catch (Throwable th) {
                Slog.e(TAG, th.getMessage(), th);
            }
        }
    }
}
