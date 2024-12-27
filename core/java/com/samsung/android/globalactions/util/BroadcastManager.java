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

    public void registerDismissActions(final Runnable dismissRunnable, final Runnable dismissVIRunnable) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction(ACTION_TALKBACK_TOGGLED);
        intentFilter.addAction(ACTION_POWER_OFF_CANCEL);
        intentFilter.addAction(ACTION_KEYGUARD_STATE_UPDATE);
        intentFilter.addAction(ACTION_POWER_OFF_ANIMATION_START);
        this.mDismissBroadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.globalactions.util.BroadcastManager.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                char c;
                String action = intent.getAction();
                BroadcastManager.this.mLogWrapper.i(BroadcastManager.TAG, "action = " + action);
                switch (action.hashCode()) {
                    case -2128145023:
                        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1932656035:
                        if (action.equals(BroadcastManager.ACTION_POWER_OFF_ANIMATION_START)) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -616862702:
                        if (action.equals(BroadcastManager.ACTION_TALKBACK_TOGGLED)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -403228793:
                        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -140814967:
                        if (action.equals(BroadcastManager.ACTION_KEYGUARD_STATE_UPDATE)) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1747999492:
                        if (action.equals(BroadcastManager.ACTION_POWER_OFF_CANCEL)) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        dismissRunnable.run();
                        break;
                    case 1:
                        String reason = intent.getStringExtra("reason");
                        if ("dream".equals(reason)) {
                            dismissRunnable.run();
                            break;
                        } else {
                            dismissVIRunnable.run();
                            break;
                        }
                    case 2:
                    case 3:
                        dismissVIRunnable.run();
                        break;
                    case 4:
                        boolean bouncerShowing = intent.getBooleanExtra("bouncerShowing", false);
                        if (!bouncerShowing) {
                            dismissRunnable.run();
                            break;
                        }
                        break;
                    case 5:
                        BroadcastManager.this.mHandlerUtil.postDelayed(dismissVIRunnable, 2000L);
                        break;
                }
            }
        };
        this.mContext.registerReceiver(this.mDismissBroadcastReceiver, intentFilter, 2);
    }

    public void registerSecureConfirmAction(final Runnable confirmRunnable) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SHOW_GLOBAL_ACTIONS);
        this.mSecureConfirmBroadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.globalactions.util.BroadcastManager.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BroadcastManager.ACTION_SHOW_GLOBAL_ACTIONS.equals(action)) {
                    confirmRunnable.run();
                }
            }
        };
        this.mContext.registerReceiver(this.mSecureConfirmBroadcastReceiver, filter, 2);
    }

    public void unregisterDismissBroadcastReceiver() {
        if (this.mDismissBroadcastReceiver != null) {
            this.mContext.unregisterReceiver(this.mDismissBroadcastReceiver);
            this.mDismissBroadcastReceiver = null;
        }
    }

    public void unregisterSecureConfirmBroadcastReceiver() {
        if (this.mSecureConfirmBroadcastReceiver != null) {
            this.mContext.unregisterReceiver(this.mSecureConfirmBroadcastReceiver);
            this.mSecureConfirmBroadcastReceiver = null;
        }
    }
}
