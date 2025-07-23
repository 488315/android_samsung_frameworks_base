package com.samsung.android.globalactions.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/* loaded from: classes6.dex */
public class BroadcastManager {
    public static final String ACTION_KEYGUARD_STATE_UPDATE = "com.samsung.keyguard.KEYGUARD_STATE_UPDATE";
    public static final String ACTION_POWER_OFF_ANIMATION_START = "POWER_OFF_ANIMATION_START";
    public static final String ACTION_POWER_OFF_CANCEL = "POWER_OFF_CANCEL";
    public static final String ACTION_SHOW_GLOBAL_ACTIONS = "android.intent.action.SHOW_GLOBAL_ACTIONS";
    public static final String ACTION_TALKBACK_TOGGLED = "com.samsung.settings.action.talkback_toggled";
    public static final String SYSTEM_DIALOG_REASON_DREAM = "dream";
    public static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    public static final String TAG = "BroadcastManager";
    private final Context mContext;
    private BroadcastReceiver mDismissBroadcastReceiver;
    private final HandlerUtil mHandlerUtil;
    private BroadcastReceiver mKeyguardShowBroadcastReceiver;
    private final LogWrapper mLogWrapper;
    private BroadcastReceiver mSecureConfirmBroadcastReceiver;

    public BroadcastManager(Context context, LogWrapper logWrapper, HandlerUtil handlerUtil) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
        this.mHandlerUtil = handlerUtil;
    }

    public void registerDismissActions(final Runnable runnable, final Runnable runnable2) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction(ACTION_TALKBACK_TOGGLED);
        intentFilter.addAction(ACTION_POWER_OFF_CANCEL);
        intentFilter.addAction(ACTION_KEYGUARD_STATE_UPDATE);
        intentFilter.addAction(ACTION_POWER_OFF_ANIMATION_START);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.globalactions.util.BroadcastManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                BroadcastManager.this.mLogWrapper.i(BroadcastManager.TAG, "action = " + action);
                action.hashCode();
                switch (action) {
                    case "android.intent.action.SCREEN_OFF":
                        runnable.run();
                        break;
                    case "POWER_OFF_ANIMATION_START":
                        BroadcastManager.this.mHandlerUtil.postDelayed(runnable2, 2000L);
                        break;
                    case "com.samsung.settings.action.talkback_toggled":
                    case "POWER_OFF_CANCEL":
                        runnable2.run();
                        break;
                    case "android.intent.action.CLOSE_SYSTEM_DIALOGS":
                        if ("dream".equals(intent.getStringExtra("reason"))) {
                            runnable.run();
                            break;
                        } else {
                            runnable2.run();
                            break;
                        }
                    case "com.samsung.keyguard.KEYGUARD_STATE_UPDATE":
                        if (!intent.getBooleanExtra("bouncerShowing", false)) {
                            runnable.run();
                            break;
                        }
                        break;
                }
            }
        };
        this.mDismissBroadcastReceiver = broadcastReceiver;
        this.mContext.registerReceiver(broadcastReceiver, intentFilter, 2);
    }

    public void registerSecureConfirmAction(final Runnable runnable) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SHOW_GLOBAL_ACTIONS);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.samsung.android.globalactions.util.BroadcastManager.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (BroadcastManager.ACTION_SHOW_GLOBAL_ACTIONS.equals(intent.getAction())) {
                    runnable.run();
                }
            }
        };
        this.mSecureConfirmBroadcastReceiver = broadcastReceiver;
        this.mContext.registerReceiver(broadcastReceiver, intentFilter, 2);
    }

    public void unregisterDismissBroadcastReceiver() {
        BroadcastReceiver broadcastReceiver = this.mDismissBroadcastReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
            this.mDismissBroadcastReceiver = null;
        }
    }

    public void unregisterSecureConfirmBroadcastReceiver() {
        BroadcastReceiver broadcastReceiver = this.mSecureConfirmBroadcastReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
            this.mSecureConfirmBroadcastReceiver = null;
        }
    }
}
