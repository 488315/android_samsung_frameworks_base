package com.samsung.android.globalactions.util;

import android.app.ActivityManager;
import android.app.StatusBarManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.security.KeyChain;
import android.telephony.TelephonyManager;

import com.android.internal.logging.MetricsLogger;

import com.samsung.android.emergencymode.SemEmergencyConstants;
import com.samsung.android.emergencymode.SemEmergencyManager;

public class SystemController {
    private static final String ACTION_CLEAR_COVER_STATE_CHANGE = "com.samsung.cover.STATE_CHANGE";
    private static final String ACTION_NETWORK_MODE_CHANGED =
            "android.intent.action.NETWORK_MODE_CHANGED";
    private static final int INTERVAL_SCOVER_TRANSITION = 200;
    private static final int MESSAGE_HIDE_QUICKPANEL = 4;
    private final Context mContext;
    private final HandlerUtil mHandlerWrapper;
    private TelephonyManager mTelephonyManager;

    public SystemController(Context context, HandlerUtil handlerWrapper) {
        this.mContext = context;
        this.mHandlerWrapper = handlerWrapper;
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    public void toggleEmergencyMode() {
        Intent intent = new Intent(SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER);
        intent.putExtra("enabled", !SemEmergencyManager.isEmergencyMode(this.mContext));
        intent.putExtra(SemEmergencyConstants.EXTRA_EMERGENCY_START_SERVICE_FLAG, 16);
        this.mContext.sendBroadcast(intent);
    }

    public void doBugReport(final boolean fullBugReport) {
        if (ActivityManager.isUserAMonkey()) {
            return;
        }
        this.mHandlerWrapper.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.globalactions.util.SystemController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SystemController.this.lambda$doBugReport$0(fullBugReport);
                    }
                },
                500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doBugReport$0(boolean fullBugReport) {
        try {
            if (fullBugReport) {
                MetricsLogger.action(this.mContext, 293);
                ActivityManager.getService().requestFullBugReport();
            } else {
                MetricsLogger.action(this.mContext, 292);
                ActivityManager.getService().requestInteractiveBugReport();
            }
        } catch (RemoteException e) {
        }
    }

    public void setDataEnabled(boolean enabled) {
        this.mTelephonyManager.setDataEnabled(enabled);
        Intent intent = new Intent(ACTION_NETWORK_MODE_CHANGED);
        intent.putExtra("state", enabled);
        this.mContext.sendBroadcast(intent);
    }

    public boolean startProKioskExitUI(String exitUIPackage, String exitUIClass) {
        Intent intent = new Intent();
        intent.setClassName(exitUIPackage, exitUIClass);
        intent.addFlags(268435456);
        try {
            this.mContext.startActivityAsUser(intent, UserHandle.OWNER);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public void goToHome() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(268435456);
        this.mContext.startActivityAsUser(i, UserHandle.OWNER);
    }

    public void hideQuickPanel(String sender) {
        ((StatusBarManager) this.mContext.getSystemService(Context.STATUS_BAR_SERVICE))
                .collapsePanels();
        Message msg = this.mHandlerWrapper.obtainMessage(4, sender);
        this.mHandlerWrapper.sendMessageDelayed(msg, 200L);
    }

    public void restoreQuickPanelBackground() {}

    public void clearCoverStateChange() {
        Intent intent = new Intent();
        intent.setAction(ACTION_CLEAR_COVER_STATE_CHANGE);
        intent.putExtra("suppressCoverUI", false);
        intent.putExtra("miniModeUI", true);
        intent.putExtra(KeyChain.EXTRA_SENDER, "GlobalActions$ConfirmDialog");
        this.mContext.sendBroadcast(intent);
    }

    public void startATTForceUpdate() {
        Intent intent = new Intent("com.attdm.intent.action.FIRSTNET_START_FORCE_UPDATE");
        intent.addFlags(268435456);
        intent.setPackage("com.ws.dm");
        this.mContext.sendBroadcast(intent);
    }

    public void startSecForceUpdate() {
        Intent intent = new Intent("com.samsung.intent.action.FIRSTNET_FORCE_UPDATE");
        intent.addFlags(268435456);
        intent.setPackage("com.wssyncmldm");
        this.mContext.sendBroadcast(intent);
    }
}
