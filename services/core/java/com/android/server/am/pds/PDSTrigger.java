package com.android.server.am.pds;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.am.PDSController;

import java.util.ArrayList;

public final class PDSTrigger {
    public long firstTrigger;
    public boolean isInDuration;
    public boolean isMpsmActive;
    public boolean isUdsActive;
    public AlarmManager mAlarm;
    public Context mContext;
    public AnonymousClass1 mIntentReceiver;
    public long mLastTimePolicyTrigger;
    public PendingIntent mMARsFirstTriggerPolicyAlarmIntent;
    public AnonymousClass1 mPolicyIntentReceiver;
    public AnonymousClass1 mPolicyMPSMIntentReceiver;
    public AnonymousClass1 mPolicyUDSIntentReceiver;
    public boolean mReceiverRegistered;
    public UserHandle user;

    public abstract class PDSTriggerHolder {
        public static final PDSTrigger INSTANCE;

        /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.am.pds.PDSTrigger$1] */
        /* JADX WARN: Type inference failed for: r1v4, types: [com.android.server.am.pds.PDSTrigger$1] */
        /* JADX WARN: Type inference failed for: r1v5, types: [com.android.server.am.pds.PDSTrigger$1] */
        /* JADX WARN: Type inference failed for: r1v6, types: [com.android.server.am.pds.PDSTrigger$1] */
        static {
            final PDSTrigger pDSTrigger = new PDSTrigger();
            pDSTrigger.mReceiverRegistered = false;
            pDSTrigger.isMpsmActive = false;
            pDSTrigger.isUdsActive = false;
            pDSTrigger.isInDuration = false;
            pDSTrigger.mLastTimePolicyTrigger = -1L;
            pDSTrigger.firstTrigger = 300000L;
            final int i = 0;
            pDSTrigger.mIntentReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.pds.PDSTrigger.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            Bundle extras;
                            boolean z;
                            ArrayList<String> stringArrayList;
                            ArrayList<String> stringArrayList2;
                            switch (i) {
                                case 0:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    if (action.equals("android.intent.action.SCREEN_ON")) {
                                        PDSController.Lock lock = PDSController.PDSLock;
                                        PDSController pDSController =
                                                PDSController.PDSControllerHolder.INSTANCE;
                                        synchronized (pDSController) {
                                            pDSController.mScreenOn = true;
                                        }
                                        PDSTrigger pDSTrigger2 = pDSTrigger;
                                        PendingIntent pendingIntent =
                                                pDSTrigger2.mMARsFirstTriggerPolicyAlarmIntent;
                                        if (pendingIntent != null) {
                                            pDSTrigger2.mAlarm.cancel(pendingIntent);
                                        }
                                        PDSHandler.MainHandler mainHandler =
                                                PDSHandler.PDSHandlerHolder.INSTANCE.mMainHandler;
                                        if (mainHandler == null) {
                                            return;
                                        }
                                        mainHandler.removeMessages(1);
                                        return;
                                    }
                                    if (action.equals("android.intent.action.SCREEN_OFF")) {
                                        PDSController.Lock lock2 = PDSController.PDSLock;
                                        PDSController pDSController2 =
                                                PDSController.PDSControllerHolder.INSTANCE;
                                        synchronized (pDSController2) {
                                            pDSController2.mScreenOn = false;
                                        }
                                        if (pDSController2.mIsPDSEnable) {
                                            PDSTrigger pDSTrigger3 = pDSTrigger;
                                            if (pDSTrigger3.isMpsmActive
                                                    || pDSTrigger3.isUdsActive) {
                                                if (pDSTrigger3.mMARsFirstTriggerPolicyAlarmIntent
                                                        == null) {
                                                    pDSTrigger3.mMARsFirstTriggerPolicyAlarmIntent =
                                                            PendingIntent.getBroadcastAsUser(
                                                                    pDSTrigger3.mContext,
                                                                    0,
                                                                    new Intent(
                                                                                    "FIRST_ALARM_TRIGGER_ACTION")
                                                                            .setPackage("android")
                                                                            .setFlags(1073741824),
                                                                    67108864,
                                                                    pDSTrigger3.user);
                                                }
                                                pDSTrigger3.mAlarm.setExact(
                                                        1,
                                                        System.currentTimeMillis() + 5000,
                                                        pDSTrigger3
                                                                .mMARsFirstTriggerPolicyAlarmIntent);
                                                return;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 1:
                                    String action2 = intent.getAction();
                                    if (action2 == null) {
                                        return;
                                    }
                                    boolean equals = action2.equals("FIRST_ALARM_TRIGGER_ACTION");
                                    PDSHandler pDSHandler = PDSHandler.PDSHandlerHolder.INSTANCE;
                                    if (equals) {
                                        Slog.d(
                                                "PDSTrigger",
                                                "mPolicyIntentReceiver broadcast received action : "
                                                        .concat(action2));
                                        PDSController.Lock lock3 = PDSController.PDSLock;
                                        PDSController pDSController3 =
                                                PDSController.PDSControllerHolder.INSTANCE;
                                        synchronized (pDSController3) {
                                            z = pDSController3.mScreenOn;
                                        }
                                        if (!z) {
                                            if (pDSTrigger.isMpsmActive) {
                                                long currentTimeMillis = System.currentTimeMillis();
                                                PDSTrigger pDSTrigger4 = pDSTrigger;
                                                if (currentTimeMillis
                                                                - pDSTrigger4.mLastTimePolicyTrigger
                                                        < 300000) {
                                                    pDSTrigger4.firstTrigger =
                                                            300000
                                                                    - (System.currentTimeMillis()
                                                                            - pDSTrigger
                                                                                    .mLastTimePolicyTrigger);
                                                }
                                                pDSHandler.sendFirstTriggerMsgToMainHandler(
                                                        10, pDSTrigger.firstTrigger);
                                            }
                                            PDSTrigger pDSTrigger5 = pDSTrigger;
                                            if (pDSTrigger5.isUdsActive) {
                                                pDSHandler.sendFirstTriggerMsgToMainHandler(
                                                        5, pDSTrigger5.firstTrigger);
                                            }
                                        }
                                    }
                                    if (!action2.equals(
                                                    "com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")
                                            || (extras = intent.getExtras()) == null) {
                                        return;
                                    }
                                    String string = extras.getString("POLICY_NAME", "");
                                    pDSTrigger.getClass();
                                    int i2 =
                                            "udspolicy".equalsIgnoreCase(string)
                                                    ? 5
                                                    : "mpsmpolicy".equalsIgnoreCase(string)
                                                            ? 10
                                                            : 0;
                                    ArrayList<String> stringArrayList3 =
                                            extras.getStringArrayList("PACKAGE_NAME");
                                    if (i2 == 0 || stringArrayList3 == null) {
                                        return;
                                    }
                                    pDSHandler.sendRunPolicySpecificPkgMsgToMainHandler(
                                            i2,
                                            pDSTrigger.mContext.getUserId(),
                                            null,
                                            stringArrayList3);
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action3 = intent.getAction();
                                    boolean equals2 =
                                            action3.equals(
                                                    "com.android.server.am.MARS_TRIGGER_UDS_POLICY");
                                    PDSHandler pDSHandler2 = PDSHandler.PDSHandlerHolder.INSTANCE;
                                    if (equals2) {
                                        Slog.d(
                                                "PDSTrigger",
                                                "broadcast received action :"
                                                    + " MARS_TRIGGER_UDS_POLICY");
                                        pDSTrigger.isUdsActive = true;
                                        Bundle extras2 = intent.getExtras();
                                        if (extras2 == null
                                                || (stringArrayList =
                                                                extras2.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        pDSHandler2.sendRunPolicySpecificPkgMsgToMainHandler(
                                                5,
                                                pDSTrigger.mContext.getUserId(),
                                                PDSTrigger.m210$$Nest$mactionToString(
                                                        pDSTrigger, action3),
                                                stringArrayList);
                                        return;
                                    }
                                    if (action3.equals(
                                            "com.android.server.am.MARS_CANCEL_UDS_POLICY")) {
                                        Slog.d(
                                                "PDSTrigger",
                                                "broadcast received action :"
                                                    + " MARS_CANCEL_UDS_POLICY");
                                        pDSTrigger.isUdsActive = false;
                                        Bundle extras3 = intent.getExtras();
                                        if (extras3 == null) {
                                            pDSHandler2.sendCancelPolicyMsgToMainHandler(
                                                    5, pDSTrigger.mContext.getUserId(), null);
                                            return;
                                        }
                                        ArrayList<String> stringArrayList4 =
                                                extras3.getStringArrayList("PACKAGE_NAME");
                                        if (stringArrayList4 != null) {
                                            pDSHandler2.sendCancelPolicyMsgToMainHandler(
                                                    5,
                                                    pDSTrigger.mContext.getUserId(),
                                                    stringArrayList4);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action4 = intent.getAction();
                                    boolean equals3 =
                                            action4.equals(
                                                    "com.android.server.am.MARS_TRIGGER_MPSM_POLICY");
                                    PDSHandler pDSHandler3 = PDSHandler.PDSHandlerHolder.INSTANCE;
                                    if (!equals3) {
                                        if (action4.equals(
                                                "com.android.server.am.MARS_CANCEL_MPSM_POLICY")) {
                                            Slog.d(
                                                    "PDSTrigger",
                                                    "broadcast received action :"
                                                        + " MARS_CANCEL_MPSM_POLICY");
                                            PDSTrigger pDSTrigger6 = pDSTrigger;
                                            pDSTrigger6.isMpsmActive = false;
                                            pDSTrigger6.mLastTimePolicyTrigger = -1L;
                                            Bundle extras4 = intent.getExtras();
                                            if (extras4 == null) {
                                                pDSHandler3.sendCancelPolicyMsgToMainHandler(
                                                        10, pDSTrigger.mContext.getUserId(), null);
                                                return;
                                            }
                                            ArrayList<String> stringArrayList5 =
                                                    extras4.getStringArrayList("PACKAGE_NAME");
                                            if (stringArrayList5 != null) {
                                                pDSHandler3.sendCancelPolicyMsgToMainHandler(
                                                        10,
                                                        pDSTrigger.mContext.getUserId(),
                                                        stringArrayList5);
                                                return;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    PDSTrigger pDSTrigger7 = pDSTrigger;
                                    long currentTimeMillis2 = System.currentTimeMillis();
                                    PDSTrigger pDSTrigger8 = pDSTrigger;
                                    pDSTrigger7.isInDuration =
                                            currentTimeMillis2 - pDSTrigger8.mLastTimePolicyTrigger
                                                    >= 300000;
                                    boolean z2 = pDSTrigger8.isMpsmActive;
                                    if (!z2 || (z2 && pDSTrigger8.isInDuration)) {
                                        pDSTrigger8.isMpsmActive = true;
                                        pDSTrigger8.mLastTimePolicyTrigger =
                                                System.currentTimeMillis();
                                        Slog.d(
                                                "PDSTrigger",
                                                "broadcast received action :"
                                                    + " MARS_TRIGGER_MPSM_POLICY");
                                        Bundle extras5 = intent.getExtras();
                                        if (extras5 == null
                                                || (stringArrayList2 =
                                                                extras5.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        pDSHandler3.sendRunPolicySpecificPkgMsgToMainHandler(
                                                10,
                                                pDSTrigger.mContext.getUserId(),
                                                PDSTrigger.m210$$Nest$mactionToString(
                                                        pDSTrigger, action4),
                                                stringArrayList2);
                                        return;
                                    }
                                    return;
                            }
                        }
                    };
            final int i2 = 1;
            pDSTrigger.mPolicyIntentReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.pds.PDSTrigger.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            Bundle extras;
                            boolean z;
                            ArrayList<String> stringArrayList;
                            ArrayList<String> stringArrayList2;
                            switch (i2) {
                                case 0:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    if (action.equals("android.intent.action.SCREEN_ON")) {
                                        PDSController.Lock lock = PDSController.PDSLock;
                                        PDSController pDSController =
                                                PDSController.PDSControllerHolder.INSTANCE;
                                        synchronized (pDSController) {
                                            pDSController.mScreenOn = true;
                                        }
                                        PDSTrigger pDSTrigger2 = pDSTrigger;
                                        PendingIntent pendingIntent =
                                                pDSTrigger2.mMARsFirstTriggerPolicyAlarmIntent;
                                        if (pendingIntent != null) {
                                            pDSTrigger2.mAlarm.cancel(pendingIntent);
                                        }
                                        PDSHandler.MainHandler mainHandler =
                                                PDSHandler.PDSHandlerHolder.INSTANCE.mMainHandler;
                                        if (mainHandler == null) {
                                            return;
                                        }
                                        mainHandler.removeMessages(1);
                                        return;
                                    }
                                    if (action.equals("android.intent.action.SCREEN_OFF")) {
                                        PDSController.Lock lock2 = PDSController.PDSLock;
                                        PDSController pDSController2 =
                                                PDSController.PDSControllerHolder.INSTANCE;
                                        synchronized (pDSController2) {
                                            pDSController2.mScreenOn = false;
                                        }
                                        if (pDSController2.mIsPDSEnable) {
                                            PDSTrigger pDSTrigger3 = pDSTrigger;
                                            if (pDSTrigger3.isMpsmActive
                                                    || pDSTrigger3.isUdsActive) {
                                                if (pDSTrigger3.mMARsFirstTriggerPolicyAlarmIntent
                                                        == null) {
                                                    pDSTrigger3.mMARsFirstTriggerPolicyAlarmIntent =
                                                            PendingIntent.getBroadcastAsUser(
                                                                    pDSTrigger3.mContext,
                                                                    0,
                                                                    new Intent(
                                                                                    "FIRST_ALARM_TRIGGER_ACTION")
                                                                            .setPackage("android")
                                                                            .setFlags(1073741824),
                                                                    67108864,
                                                                    pDSTrigger3.user);
                                                }
                                                pDSTrigger3.mAlarm.setExact(
                                                        1,
                                                        System.currentTimeMillis() + 5000,
                                                        pDSTrigger3
                                                                .mMARsFirstTriggerPolicyAlarmIntent);
                                                return;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 1:
                                    String action2 = intent.getAction();
                                    if (action2 == null) {
                                        return;
                                    }
                                    boolean equals = action2.equals("FIRST_ALARM_TRIGGER_ACTION");
                                    PDSHandler pDSHandler = PDSHandler.PDSHandlerHolder.INSTANCE;
                                    if (equals) {
                                        Slog.d(
                                                "PDSTrigger",
                                                "mPolicyIntentReceiver broadcast received action : "
                                                        .concat(action2));
                                        PDSController.Lock lock3 = PDSController.PDSLock;
                                        PDSController pDSController3 =
                                                PDSController.PDSControllerHolder.INSTANCE;
                                        synchronized (pDSController3) {
                                            z = pDSController3.mScreenOn;
                                        }
                                        if (!z) {
                                            if (pDSTrigger.isMpsmActive) {
                                                long currentTimeMillis = System.currentTimeMillis();
                                                PDSTrigger pDSTrigger4 = pDSTrigger;
                                                if (currentTimeMillis
                                                                - pDSTrigger4.mLastTimePolicyTrigger
                                                        < 300000) {
                                                    pDSTrigger4.firstTrigger =
                                                            300000
                                                                    - (System.currentTimeMillis()
                                                                            - pDSTrigger
                                                                                    .mLastTimePolicyTrigger);
                                                }
                                                pDSHandler.sendFirstTriggerMsgToMainHandler(
                                                        10, pDSTrigger.firstTrigger);
                                            }
                                            PDSTrigger pDSTrigger5 = pDSTrigger;
                                            if (pDSTrigger5.isUdsActive) {
                                                pDSHandler.sendFirstTriggerMsgToMainHandler(
                                                        5, pDSTrigger5.firstTrigger);
                                            }
                                        }
                                    }
                                    if (!action2.equals(
                                                    "com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")
                                            || (extras = intent.getExtras()) == null) {
                                        return;
                                    }
                                    String string = extras.getString("POLICY_NAME", "");
                                    pDSTrigger.getClass();
                                    int i22 =
                                            "udspolicy".equalsIgnoreCase(string)
                                                    ? 5
                                                    : "mpsmpolicy".equalsIgnoreCase(string)
                                                            ? 10
                                                            : 0;
                                    ArrayList<String> stringArrayList3 =
                                            extras.getStringArrayList("PACKAGE_NAME");
                                    if (i22 == 0 || stringArrayList3 == null) {
                                        return;
                                    }
                                    pDSHandler.sendRunPolicySpecificPkgMsgToMainHandler(
                                            i22,
                                            pDSTrigger.mContext.getUserId(),
                                            null,
                                            stringArrayList3);
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action3 = intent.getAction();
                                    boolean equals2 =
                                            action3.equals(
                                                    "com.android.server.am.MARS_TRIGGER_UDS_POLICY");
                                    PDSHandler pDSHandler2 = PDSHandler.PDSHandlerHolder.INSTANCE;
                                    if (equals2) {
                                        Slog.d(
                                                "PDSTrigger",
                                                "broadcast received action :"
                                                    + " MARS_TRIGGER_UDS_POLICY");
                                        pDSTrigger.isUdsActive = true;
                                        Bundle extras2 = intent.getExtras();
                                        if (extras2 == null
                                                || (stringArrayList =
                                                                extras2.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        pDSHandler2.sendRunPolicySpecificPkgMsgToMainHandler(
                                                5,
                                                pDSTrigger.mContext.getUserId(),
                                                PDSTrigger.m210$$Nest$mactionToString(
                                                        pDSTrigger, action3),
                                                stringArrayList);
                                        return;
                                    }
                                    if (action3.equals(
                                            "com.android.server.am.MARS_CANCEL_UDS_POLICY")) {
                                        Slog.d(
                                                "PDSTrigger",
                                                "broadcast received action :"
                                                    + " MARS_CANCEL_UDS_POLICY");
                                        pDSTrigger.isUdsActive = false;
                                        Bundle extras3 = intent.getExtras();
                                        if (extras3 == null) {
                                            pDSHandler2.sendCancelPolicyMsgToMainHandler(
                                                    5, pDSTrigger.mContext.getUserId(), null);
                                            return;
                                        }
                                        ArrayList<String> stringArrayList4 =
                                                extras3.getStringArrayList("PACKAGE_NAME");
                                        if (stringArrayList4 != null) {
                                            pDSHandler2.sendCancelPolicyMsgToMainHandler(
                                                    5,
                                                    pDSTrigger.mContext.getUserId(),
                                                    stringArrayList4);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action4 = intent.getAction();
                                    boolean equals3 =
                                            action4.equals(
                                                    "com.android.server.am.MARS_TRIGGER_MPSM_POLICY");
                                    PDSHandler pDSHandler3 = PDSHandler.PDSHandlerHolder.INSTANCE;
                                    if (!equals3) {
                                        if (action4.equals(
                                                "com.android.server.am.MARS_CANCEL_MPSM_POLICY")) {
                                            Slog.d(
                                                    "PDSTrigger",
                                                    "broadcast received action :"
                                                        + " MARS_CANCEL_MPSM_POLICY");
                                            PDSTrigger pDSTrigger6 = pDSTrigger;
                                            pDSTrigger6.isMpsmActive = false;
                                            pDSTrigger6.mLastTimePolicyTrigger = -1L;
                                            Bundle extras4 = intent.getExtras();
                                            if (extras4 == null) {
                                                pDSHandler3.sendCancelPolicyMsgToMainHandler(
                                                        10, pDSTrigger.mContext.getUserId(), null);
                                                return;
                                            }
                                            ArrayList<String> stringArrayList5 =
                                                    extras4.getStringArrayList("PACKAGE_NAME");
                                            if (stringArrayList5 != null) {
                                                pDSHandler3.sendCancelPolicyMsgToMainHandler(
                                                        10,
                                                        pDSTrigger.mContext.getUserId(),
                                                        stringArrayList5);
                                                return;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    PDSTrigger pDSTrigger7 = pDSTrigger;
                                    long currentTimeMillis2 = System.currentTimeMillis();
                                    PDSTrigger pDSTrigger8 = pDSTrigger;
                                    pDSTrigger7.isInDuration =
                                            currentTimeMillis2 - pDSTrigger8.mLastTimePolicyTrigger
                                                    >= 300000;
                                    boolean z2 = pDSTrigger8.isMpsmActive;
                                    if (!z2 || (z2 && pDSTrigger8.isInDuration)) {
                                        pDSTrigger8.isMpsmActive = true;
                                        pDSTrigger8.mLastTimePolicyTrigger =
                                                System.currentTimeMillis();
                                        Slog.d(
                                                "PDSTrigger",
                                                "broadcast received action :"
                                                    + " MARS_TRIGGER_MPSM_POLICY");
                                        Bundle extras5 = intent.getExtras();
                                        if (extras5 == null
                                                || (stringArrayList2 =
                                                                extras5.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        pDSHandler3.sendRunPolicySpecificPkgMsgToMainHandler(
                                                10,
                                                pDSTrigger.mContext.getUserId(),
                                                PDSTrigger.m210$$Nest$mactionToString(
                                                        pDSTrigger, action4),
                                                stringArrayList2);
                                        return;
                                    }
                                    return;
                            }
                        }
                    };
            final int i3 = 2;
            pDSTrigger.mPolicyUDSIntentReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.pds.PDSTrigger.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            Bundle extras;
                            boolean z;
                            ArrayList<String> stringArrayList;
                            ArrayList<String> stringArrayList2;
                            switch (i3) {
                                case 0:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    if (action.equals("android.intent.action.SCREEN_ON")) {
                                        PDSController.Lock lock = PDSController.PDSLock;
                                        PDSController pDSController =
                                                PDSController.PDSControllerHolder.INSTANCE;
                                        synchronized (pDSController) {
                                            pDSController.mScreenOn = true;
                                        }
                                        PDSTrigger pDSTrigger2 = pDSTrigger;
                                        PendingIntent pendingIntent =
                                                pDSTrigger2.mMARsFirstTriggerPolicyAlarmIntent;
                                        if (pendingIntent != null) {
                                            pDSTrigger2.mAlarm.cancel(pendingIntent);
                                        }
                                        PDSHandler.MainHandler mainHandler =
                                                PDSHandler.PDSHandlerHolder.INSTANCE.mMainHandler;
                                        if (mainHandler == null) {
                                            return;
                                        }
                                        mainHandler.removeMessages(1);
                                        return;
                                    }
                                    if (action.equals("android.intent.action.SCREEN_OFF")) {
                                        PDSController.Lock lock2 = PDSController.PDSLock;
                                        PDSController pDSController2 =
                                                PDSController.PDSControllerHolder.INSTANCE;
                                        synchronized (pDSController2) {
                                            pDSController2.mScreenOn = false;
                                        }
                                        if (pDSController2.mIsPDSEnable) {
                                            PDSTrigger pDSTrigger3 = pDSTrigger;
                                            if (pDSTrigger3.isMpsmActive
                                                    || pDSTrigger3.isUdsActive) {
                                                if (pDSTrigger3.mMARsFirstTriggerPolicyAlarmIntent
                                                        == null) {
                                                    pDSTrigger3.mMARsFirstTriggerPolicyAlarmIntent =
                                                            PendingIntent.getBroadcastAsUser(
                                                                    pDSTrigger3.mContext,
                                                                    0,
                                                                    new Intent(
                                                                                    "FIRST_ALARM_TRIGGER_ACTION")
                                                                            .setPackage("android")
                                                                            .setFlags(1073741824),
                                                                    67108864,
                                                                    pDSTrigger3.user);
                                                }
                                                pDSTrigger3.mAlarm.setExact(
                                                        1,
                                                        System.currentTimeMillis() + 5000,
                                                        pDSTrigger3
                                                                .mMARsFirstTriggerPolicyAlarmIntent);
                                                return;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 1:
                                    String action2 = intent.getAction();
                                    if (action2 == null) {
                                        return;
                                    }
                                    boolean equals = action2.equals("FIRST_ALARM_TRIGGER_ACTION");
                                    PDSHandler pDSHandler = PDSHandler.PDSHandlerHolder.INSTANCE;
                                    if (equals) {
                                        Slog.d(
                                                "PDSTrigger",
                                                "mPolicyIntentReceiver broadcast received action : "
                                                        .concat(action2));
                                        PDSController.Lock lock3 = PDSController.PDSLock;
                                        PDSController pDSController3 =
                                                PDSController.PDSControllerHolder.INSTANCE;
                                        synchronized (pDSController3) {
                                            z = pDSController3.mScreenOn;
                                        }
                                        if (!z) {
                                            if (pDSTrigger.isMpsmActive) {
                                                long currentTimeMillis = System.currentTimeMillis();
                                                PDSTrigger pDSTrigger4 = pDSTrigger;
                                                if (currentTimeMillis
                                                                - pDSTrigger4.mLastTimePolicyTrigger
                                                        < 300000) {
                                                    pDSTrigger4.firstTrigger =
                                                            300000
                                                                    - (System.currentTimeMillis()
                                                                            - pDSTrigger
                                                                                    .mLastTimePolicyTrigger);
                                                }
                                                pDSHandler.sendFirstTriggerMsgToMainHandler(
                                                        10, pDSTrigger.firstTrigger);
                                            }
                                            PDSTrigger pDSTrigger5 = pDSTrigger;
                                            if (pDSTrigger5.isUdsActive) {
                                                pDSHandler.sendFirstTriggerMsgToMainHandler(
                                                        5, pDSTrigger5.firstTrigger);
                                            }
                                        }
                                    }
                                    if (!action2.equals(
                                                    "com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")
                                            || (extras = intent.getExtras()) == null) {
                                        return;
                                    }
                                    String string = extras.getString("POLICY_NAME", "");
                                    pDSTrigger.getClass();
                                    int i22 =
                                            "udspolicy".equalsIgnoreCase(string)
                                                    ? 5
                                                    : "mpsmpolicy".equalsIgnoreCase(string)
                                                            ? 10
                                                            : 0;
                                    ArrayList<String> stringArrayList3 =
                                            extras.getStringArrayList("PACKAGE_NAME");
                                    if (i22 == 0 || stringArrayList3 == null) {
                                        return;
                                    }
                                    pDSHandler.sendRunPolicySpecificPkgMsgToMainHandler(
                                            i22,
                                            pDSTrigger.mContext.getUserId(),
                                            null,
                                            stringArrayList3);
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action3 = intent.getAction();
                                    boolean equals2 =
                                            action3.equals(
                                                    "com.android.server.am.MARS_TRIGGER_UDS_POLICY");
                                    PDSHandler pDSHandler2 = PDSHandler.PDSHandlerHolder.INSTANCE;
                                    if (equals2) {
                                        Slog.d(
                                                "PDSTrigger",
                                                "broadcast received action :"
                                                    + " MARS_TRIGGER_UDS_POLICY");
                                        pDSTrigger.isUdsActive = true;
                                        Bundle extras2 = intent.getExtras();
                                        if (extras2 == null
                                                || (stringArrayList =
                                                                extras2.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        pDSHandler2.sendRunPolicySpecificPkgMsgToMainHandler(
                                                5,
                                                pDSTrigger.mContext.getUserId(),
                                                PDSTrigger.m210$$Nest$mactionToString(
                                                        pDSTrigger, action3),
                                                stringArrayList);
                                        return;
                                    }
                                    if (action3.equals(
                                            "com.android.server.am.MARS_CANCEL_UDS_POLICY")) {
                                        Slog.d(
                                                "PDSTrigger",
                                                "broadcast received action :"
                                                    + " MARS_CANCEL_UDS_POLICY");
                                        pDSTrigger.isUdsActive = false;
                                        Bundle extras3 = intent.getExtras();
                                        if (extras3 == null) {
                                            pDSHandler2.sendCancelPolicyMsgToMainHandler(
                                                    5, pDSTrigger.mContext.getUserId(), null);
                                            return;
                                        }
                                        ArrayList<String> stringArrayList4 =
                                                extras3.getStringArrayList("PACKAGE_NAME");
                                        if (stringArrayList4 != null) {
                                            pDSHandler2.sendCancelPolicyMsgToMainHandler(
                                                    5,
                                                    pDSTrigger.mContext.getUserId(),
                                                    stringArrayList4);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action4 = intent.getAction();
                                    boolean equals3 =
                                            action4.equals(
                                                    "com.android.server.am.MARS_TRIGGER_MPSM_POLICY");
                                    PDSHandler pDSHandler3 = PDSHandler.PDSHandlerHolder.INSTANCE;
                                    if (!equals3) {
                                        if (action4.equals(
                                                "com.android.server.am.MARS_CANCEL_MPSM_POLICY")) {
                                            Slog.d(
                                                    "PDSTrigger",
                                                    "broadcast received action :"
                                                        + " MARS_CANCEL_MPSM_POLICY");
                                            PDSTrigger pDSTrigger6 = pDSTrigger;
                                            pDSTrigger6.isMpsmActive = false;
                                            pDSTrigger6.mLastTimePolicyTrigger = -1L;
                                            Bundle extras4 = intent.getExtras();
                                            if (extras4 == null) {
                                                pDSHandler3.sendCancelPolicyMsgToMainHandler(
                                                        10, pDSTrigger.mContext.getUserId(), null);
                                                return;
                                            }
                                            ArrayList<String> stringArrayList5 =
                                                    extras4.getStringArrayList("PACKAGE_NAME");
                                            if (stringArrayList5 != null) {
                                                pDSHandler3.sendCancelPolicyMsgToMainHandler(
                                                        10,
                                                        pDSTrigger.mContext.getUserId(),
                                                        stringArrayList5);
                                                return;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    PDSTrigger pDSTrigger7 = pDSTrigger;
                                    long currentTimeMillis2 = System.currentTimeMillis();
                                    PDSTrigger pDSTrigger8 = pDSTrigger;
                                    pDSTrigger7.isInDuration =
                                            currentTimeMillis2 - pDSTrigger8.mLastTimePolicyTrigger
                                                    >= 300000;
                                    boolean z2 = pDSTrigger8.isMpsmActive;
                                    if (!z2 || (z2 && pDSTrigger8.isInDuration)) {
                                        pDSTrigger8.isMpsmActive = true;
                                        pDSTrigger8.mLastTimePolicyTrigger =
                                                System.currentTimeMillis();
                                        Slog.d(
                                                "PDSTrigger",
                                                "broadcast received action :"
                                                    + " MARS_TRIGGER_MPSM_POLICY");
                                        Bundle extras5 = intent.getExtras();
                                        if (extras5 == null
                                                || (stringArrayList2 =
                                                                extras5.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        pDSHandler3.sendRunPolicySpecificPkgMsgToMainHandler(
                                                10,
                                                pDSTrigger.mContext.getUserId(),
                                                PDSTrigger.m210$$Nest$mactionToString(
                                                        pDSTrigger, action4),
                                                stringArrayList2);
                                        return;
                                    }
                                    return;
                            }
                        }
                    };
            final int i4 = 3;
            pDSTrigger.mPolicyMPSMIntentReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.pds.PDSTrigger.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            Bundle extras;
                            boolean z;
                            ArrayList<String> stringArrayList;
                            ArrayList<String> stringArrayList2;
                            switch (i4) {
                                case 0:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    if (action.equals("android.intent.action.SCREEN_ON")) {
                                        PDSController.Lock lock = PDSController.PDSLock;
                                        PDSController pDSController =
                                                PDSController.PDSControllerHolder.INSTANCE;
                                        synchronized (pDSController) {
                                            pDSController.mScreenOn = true;
                                        }
                                        PDSTrigger pDSTrigger2 = pDSTrigger;
                                        PendingIntent pendingIntent =
                                                pDSTrigger2.mMARsFirstTriggerPolicyAlarmIntent;
                                        if (pendingIntent != null) {
                                            pDSTrigger2.mAlarm.cancel(pendingIntent);
                                        }
                                        PDSHandler.MainHandler mainHandler =
                                                PDSHandler.PDSHandlerHolder.INSTANCE.mMainHandler;
                                        if (mainHandler == null) {
                                            return;
                                        }
                                        mainHandler.removeMessages(1);
                                        return;
                                    }
                                    if (action.equals("android.intent.action.SCREEN_OFF")) {
                                        PDSController.Lock lock2 = PDSController.PDSLock;
                                        PDSController pDSController2 =
                                                PDSController.PDSControllerHolder.INSTANCE;
                                        synchronized (pDSController2) {
                                            pDSController2.mScreenOn = false;
                                        }
                                        if (pDSController2.mIsPDSEnable) {
                                            PDSTrigger pDSTrigger3 = pDSTrigger;
                                            if (pDSTrigger3.isMpsmActive
                                                    || pDSTrigger3.isUdsActive) {
                                                if (pDSTrigger3.mMARsFirstTriggerPolicyAlarmIntent
                                                        == null) {
                                                    pDSTrigger3.mMARsFirstTriggerPolicyAlarmIntent =
                                                            PendingIntent.getBroadcastAsUser(
                                                                    pDSTrigger3.mContext,
                                                                    0,
                                                                    new Intent(
                                                                                    "FIRST_ALARM_TRIGGER_ACTION")
                                                                            .setPackage("android")
                                                                            .setFlags(1073741824),
                                                                    67108864,
                                                                    pDSTrigger3.user);
                                                }
                                                pDSTrigger3.mAlarm.setExact(
                                                        1,
                                                        System.currentTimeMillis() + 5000,
                                                        pDSTrigger3
                                                                .mMARsFirstTriggerPolicyAlarmIntent);
                                                return;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 1:
                                    String action2 = intent.getAction();
                                    if (action2 == null) {
                                        return;
                                    }
                                    boolean equals = action2.equals("FIRST_ALARM_TRIGGER_ACTION");
                                    PDSHandler pDSHandler = PDSHandler.PDSHandlerHolder.INSTANCE;
                                    if (equals) {
                                        Slog.d(
                                                "PDSTrigger",
                                                "mPolicyIntentReceiver broadcast received action : "
                                                        .concat(action2));
                                        PDSController.Lock lock3 = PDSController.PDSLock;
                                        PDSController pDSController3 =
                                                PDSController.PDSControllerHolder.INSTANCE;
                                        synchronized (pDSController3) {
                                            z = pDSController3.mScreenOn;
                                        }
                                        if (!z) {
                                            if (pDSTrigger.isMpsmActive) {
                                                long currentTimeMillis = System.currentTimeMillis();
                                                PDSTrigger pDSTrigger4 = pDSTrigger;
                                                if (currentTimeMillis
                                                                - pDSTrigger4.mLastTimePolicyTrigger
                                                        < 300000) {
                                                    pDSTrigger4.firstTrigger =
                                                            300000
                                                                    - (System.currentTimeMillis()
                                                                            - pDSTrigger
                                                                                    .mLastTimePolicyTrigger);
                                                }
                                                pDSHandler.sendFirstTriggerMsgToMainHandler(
                                                        10, pDSTrigger.firstTrigger);
                                            }
                                            PDSTrigger pDSTrigger5 = pDSTrigger;
                                            if (pDSTrigger5.isUdsActive) {
                                                pDSHandler.sendFirstTriggerMsgToMainHandler(
                                                        5, pDSTrigger5.firstTrigger);
                                            }
                                        }
                                    }
                                    if (!action2.equals(
                                                    "com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")
                                            || (extras = intent.getExtras()) == null) {
                                        return;
                                    }
                                    String string = extras.getString("POLICY_NAME", "");
                                    pDSTrigger.getClass();
                                    int i22 =
                                            "udspolicy".equalsIgnoreCase(string)
                                                    ? 5
                                                    : "mpsmpolicy".equalsIgnoreCase(string)
                                                            ? 10
                                                            : 0;
                                    ArrayList<String> stringArrayList3 =
                                            extras.getStringArrayList("PACKAGE_NAME");
                                    if (i22 == 0 || stringArrayList3 == null) {
                                        return;
                                    }
                                    pDSHandler.sendRunPolicySpecificPkgMsgToMainHandler(
                                            i22,
                                            pDSTrigger.mContext.getUserId(),
                                            null,
                                            stringArrayList3);
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action3 = intent.getAction();
                                    boolean equals2 =
                                            action3.equals(
                                                    "com.android.server.am.MARS_TRIGGER_UDS_POLICY");
                                    PDSHandler pDSHandler2 = PDSHandler.PDSHandlerHolder.INSTANCE;
                                    if (equals2) {
                                        Slog.d(
                                                "PDSTrigger",
                                                "broadcast received action :"
                                                    + " MARS_TRIGGER_UDS_POLICY");
                                        pDSTrigger.isUdsActive = true;
                                        Bundle extras2 = intent.getExtras();
                                        if (extras2 == null
                                                || (stringArrayList =
                                                                extras2.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        pDSHandler2.sendRunPolicySpecificPkgMsgToMainHandler(
                                                5,
                                                pDSTrigger.mContext.getUserId(),
                                                PDSTrigger.m210$$Nest$mactionToString(
                                                        pDSTrigger, action3),
                                                stringArrayList);
                                        return;
                                    }
                                    if (action3.equals(
                                            "com.android.server.am.MARS_CANCEL_UDS_POLICY")) {
                                        Slog.d(
                                                "PDSTrigger",
                                                "broadcast received action :"
                                                    + " MARS_CANCEL_UDS_POLICY");
                                        pDSTrigger.isUdsActive = false;
                                        Bundle extras3 = intent.getExtras();
                                        if (extras3 == null) {
                                            pDSHandler2.sendCancelPolicyMsgToMainHandler(
                                                    5, pDSTrigger.mContext.getUserId(), null);
                                            return;
                                        }
                                        ArrayList<String> stringArrayList4 =
                                                extras3.getStringArrayList("PACKAGE_NAME");
                                        if (stringArrayList4 != null) {
                                            pDSHandler2.sendCancelPolicyMsgToMainHandler(
                                                    5,
                                                    pDSTrigger.mContext.getUserId(),
                                                    stringArrayList4);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action4 = intent.getAction();
                                    boolean equals3 =
                                            action4.equals(
                                                    "com.android.server.am.MARS_TRIGGER_MPSM_POLICY");
                                    PDSHandler pDSHandler3 = PDSHandler.PDSHandlerHolder.INSTANCE;
                                    if (!equals3) {
                                        if (action4.equals(
                                                "com.android.server.am.MARS_CANCEL_MPSM_POLICY")) {
                                            Slog.d(
                                                    "PDSTrigger",
                                                    "broadcast received action :"
                                                        + " MARS_CANCEL_MPSM_POLICY");
                                            PDSTrigger pDSTrigger6 = pDSTrigger;
                                            pDSTrigger6.isMpsmActive = false;
                                            pDSTrigger6.mLastTimePolicyTrigger = -1L;
                                            Bundle extras4 = intent.getExtras();
                                            if (extras4 == null) {
                                                pDSHandler3.sendCancelPolicyMsgToMainHandler(
                                                        10, pDSTrigger.mContext.getUserId(), null);
                                                return;
                                            }
                                            ArrayList<String> stringArrayList5 =
                                                    extras4.getStringArrayList("PACKAGE_NAME");
                                            if (stringArrayList5 != null) {
                                                pDSHandler3.sendCancelPolicyMsgToMainHandler(
                                                        10,
                                                        pDSTrigger.mContext.getUserId(),
                                                        stringArrayList5);
                                                return;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    PDSTrigger pDSTrigger7 = pDSTrigger;
                                    long currentTimeMillis2 = System.currentTimeMillis();
                                    PDSTrigger pDSTrigger8 = pDSTrigger;
                                    pDSTrigger7.isInDuration =
                                            currentTimeMillis2 - pDSTrigger8.mLastTimePolicyTrigger
                                                    >= 300000;
                                    boolean z2 = pDSTrigger8.isMpsmActive;
                                    if (!z2 || (z2 && pDSTrigger8.isInDuration)) {
                                        pDSTrigger8.isMpsmActive = true;
                                        pDSTrigger8.mLastTimePolicyTrigger =
                                                System.currentTimeMillis();
                                        Slog.d(
                                                "PDSTrigger",
                                                "broadcast received action :"
                                                    + " MARS_TRIGGER_MPSM_POLICY");
                                        Bundle extras5 = intent.getExtras();
                                        if (extras5 == null
                                                || (stringArrayList2 =
                                                                extras5.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        pDSHandler3.sendRunPolicySpecificPkgMsgToMainHandler(
                                                10,
                                                pDSTrigger.mContext.getUserId(),
                                                PDSTrigger.m210$$Nest$mactionToString(
                                                        pDSTrigger, action4),
                                                stringArrayList2);
                                        return;
                                    }
                                    return;
                            }
                        }
                    };
            INSTANCE = pDSTrigger;
        }
    }

    /* renamed from: -$$Nest$mactionToString, reason: not valid java name */
    public static String m210$$Nest$mactionToString(PDSTrigger pDSTrigger, String str) {
        if (str.equals("com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")) {
            return "User Trigger Policy";
        }
        if (str.equals("com.android.server.am.MARS_TRIGGER_UDS_POLICY")) {
            return "Trigger UDS(Ultra Data Saving) Policy";
        }
        if (str.equals("com.android.server.am.MARS_TRIGGER_MPSM_POLICY")) {
            return "Trigger MPSM Policy";
        }
        return null;
    }
}
