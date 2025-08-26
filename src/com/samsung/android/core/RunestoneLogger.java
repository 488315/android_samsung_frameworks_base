package com.samsung.android.core;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class RunestoneLogger {
    private static final String ACTION_SCREEN_LOGGING = "com.sec.android.diagmonagent.intent.ACTION_SCREEN_LOGGING";
    private static final boolean DEBUG = false;
    private static final String EXTRA_FOLD_STATE = "fold_state";
    private static final String EXTRA_MULTI_WINDOW_STATE = "multi_window_state";
    private static final String EXTRA_PACKAGES = "packages";
    private static final String EXTRA_SCREEN_TYPE = "screen_type";
    private static final String EXTRA_TIMESTAMP = "timestamp";
    private static final String PACKAGE_NAME = "com.sec.android.diagmonagent";
    private static final String PERMISSION_SCREEN_LOGGING = "com.sec.android.diagmonagent.permission.DIAGMON_SURVEY";
    private static final String TAG = "RunestoneLogger";

    public enum ScreenType {
        UNKNOWN(0),
        FOLD(1),
        MULTI_WINDOW(2);

        private final int value;

        ScreenType(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum ScreenState {
        UNKNOWN(0),
        FOLD(1),
        UNFOLD(2),
        NONE_MULTIWINDOW(1),
        MULTIWINDOW_2UP_MODE(2),
        MULTIWINDOW_3UP_MODE(3);

        private final int value;

        ScreenState(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static void interpretSaToRunestone(Context context, String str, String str2) {
        if (CoreSaConstant.SPLIT_EVENT_APP_PAIR_ID.equals(str)) {
            sendPairMultiWindow(context, str2);
        }
    }

    public static void sendDismissMultiWindowState(Context context) {
        sendMultiWindowState(context, new ArrayList());
    }

    private static void sendPairMultiWindow(Context context, String str) {
        String[] strArrSplit = str.substring(1, str.length() - 1).split(", ");
        ArrayList arrayList = new ArrayList();
        for (String str2 : strArrSplit) {
            arrayList.add(str2);
        }
        sendMultiWindowState(context, arrayList);
    }

    private static void sendMultiWindowState(Context context, ArrayList<String> arrayList) {
        ScreenState screenState;
        int size = arrayList.size();
        if (size == 0 || size == 1) {
            screenState = ScreenState.NONE_MULTIWINDOW;
        } else if (size == 2) {
            screenState = ScreenState.MULTIWINDOW_2UP_MODE;
        } else if (size == 3) {
            screenState = ScreenState.MULTIWINDOW_3UP_MODE;
        } else {
            Log.w(TAG, "Warning sendPairLoggingLocked [" + arrayList + NavigationBarInflaterView.SIZE_MOD_END);
            return;
        }
        sendRunestoneLogging(context, ScreenType.MULTI_WINDOW.getValue(), screenState.getValue(), arrayList);
    }

    public static void sendRunestoneLogging(Context context, int i, int i2, ArrayList<String> arrayList) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_SCREEN_TYPE, i);
        if (i == ScreenType.FOLD.getValue()) {
            bundle.putInt(EXTRA_FOLD_STATE, i2);
        } else if (i == ScreenType.MULTI_WINDOW.getValue()) {
            bundle.putInt(EXTRA_MULTI_WINDOW_STATE, i2);
        }
        if (arrayList != null && !arrayList.isEmpty()) {
            bundle.putStringArrayList(EXTRA_PACKAGES, arrayList);
        } else if (i == ScreenType.MULTI_WINDOW.getValue() && i2 != ScreenState.NONE_MULTIWINDOW.getValue()) {
            Log.d(TAG, "Send failed. it's MULTI_WINDOW Type, but package list is null");
            return;
        }
        bundle.putLong("timestamp", jCurrentTimeMillis);
        Intent intent = new Intent(ACTION_SCREEN_LOGGING);
        intent.setPackage("com.sec.android.diagmonagent");
        intent.putExtras(bundle);
        context.sendBroadcastAsUser(intent, UserHandle.CURRENT_OR_SELF, "com.sec.android.diagmonagent.permission.DIAGMON_SURVEY");
    }
}
