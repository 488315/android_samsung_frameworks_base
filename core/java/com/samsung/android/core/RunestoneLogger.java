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

        ScreenType(int value) {
            this.value = value;
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

        ScreenState(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static void interpretSaToRunestone(Context context, String eventId, String detail) {
        if (CoreSaConstant.SPLIT_EVENT_APP_PAIR_ID.equals(eventId)) {
            sendPairMultiWindow(context, detail);
        }
    }

    public static void sendDismissMultiWindowState(Context context) {
        sendMultiWindowState(context, new ArrayList());
    }

    private static void sendPairMultiWindow(Context context, String detail) {
        String[] packages = detail.substring(1, detail.length() - 1).split(", ");
        ArrayList<String> packageLists = new ArrayList<>();
        for (String packageName : packages) {
            packageLists.add(packageName);
        }
        sendMultiWindowState(context, packageLists);
    }

    private static void sendMultiWindowState(Context context, ArrayList<String> packageList) {
        ScreenState screenState;
        switch (packageList.size()) {
            case 0:
            case 1:
                screenState = ScreenState.NONE_MULTIWINDOW;
                break;
            case 2:
                screenState = ScreenState.MULTIWINDOW_2UP_MODE;
                break;
            case 3:
                screenState = ScreenState.MULTIWINDOW_3UP_MODE;
                break;
            default:
                Log.w(TAG, "Warning sendPairLoggingLocked [" + packageList + NavigationBarInflaterView.SIZE_MOD_END);
                return;
        }
        sendRunestoneLogging(context, ScreenType.MULTI_WINDOW.getValue(), screenState.getValue(), packageList);
    }

    public static void sendRunestoneLogging(Context context, int screenType, int screenState, ArrayList<String> packageList) {
        long timeStamp = System.currentTimeMillis();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_SCREEN_TYPE, screenType);
        if (screenType == ScreenType.FOLD.getValue()) {
            bundle.putInt(EXTRA_FOLD_STATE, screenState);
        } else if (screenType == ScreenType.MULTI_WINDOW.getValue()) {
            bundle.putInt(EXTRA_MULTI_WINDOW_STATE, screenState);
        }
        if (packageList != null && !packageList.isEmpty()) {
            bundle.putStringArrayList(EXTRA_PACKAGES, packageList);
        } else if (screenType == ScreenType.MULTI_WINDOW.getValue() && screenState != ScreenState.NONE_MULTIWINDOW.getValue()) {
            Log.d(TAG, "Send failed. it's MULTI_WINDOW Type, but package list is null");
            return;
        }
        bundle.putLong("timestamp", timeStamp);
        Intent intent = new Intent(ACTION_SCREEN_LOGGING);
        intent.setPackage("com.sec.android.diagmonagent");
        intent.putExtras(bundle);
        context.sendBroadcastAsUser(intent, UserHandle.CURRENT_OR_SELF, "com.sec.android.diagmonagent.permission.DIAGMON_SURVEY");
    }
}
