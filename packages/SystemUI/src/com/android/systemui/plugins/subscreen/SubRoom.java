package com.android.systemui.plugins.subscreen;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface SubRoom {
    public static final String EXTRA_FOCUS_REQUIRED = "focusRequired";
    public static final String EXTRA_HAS_UNREAD = "hasUnread";
    public static final String EXTRA_KEY_AIRPLANE_MODE = "airplane";
    public static final String EXTRA_KEY_DISPLAY_CUTOUT_BOTTOM = "displayCutoutBottom";
    public static final String EXTRA_KEY_DISPLAY_CUTOUT_TOP = "displayCutoutTop";
    public static final String EXTRA_KEY_LAUNCH_MEDIA_NOWBAR = "launchMediaNowbar";
    public static final String EXTRA_KEY_NO_SIGNAL = "no_siginal";
    public static final String EXTRA_KEY_QUICK_PANEL_SWIPE_FRACTION = "quickPanelSwipeFraction";
    public static final String EXTRA_KEY_ROUTINE_MODE = "routine_mode";
    public static final String EXTRA_VALUE_CLOCK = "clock";
    public static final String EXTRA_VALUE_NOTIFICATION = "notification";
    public static final int EXTRA_VALUE_NO_SIGNAL = 1;
    public static final int EXTRA_VALUE_NO_SIGNAL_SIM_1 = 16;
    public static final int EXTRA_VALUE_NO_SIGNAL_SIM_2 = 256;
    public static final String STATE_BIO_POPUP_CANCELED = "STATE_BIO_POPUP_CANCELED";
    public static final String STATE_MUSIC_CAPSULE_INFO = "STATE_MUSIC_CAPSULE_INFO";
    public static final String STATE_NETWORK_INFO = "STATE_NETWORK_INFO";
    public static final String STATE_POPUP_DISMISSED = "STATE_POPUP_DISMISSED";
    public static final String STATE_UNREAD_NOTIFICATION = "STATE_UNREAD_NOTIFICATION";
    public static final String STATE_WINDOW_INSETS_DISPLAY_CUTOUT = "STATE_WINDOW_INSETS_DISPLAY_CUTOUT";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface StateChangeListener {
        void onStateChanged(Bundle bundle);

        default void requestCoverPopup(PendingIntent pendingIntent, Intent intent) {
        }

        default void requestCoverPopup(PendingIntent pendingIntent, String str) {
        }
    }

    View getView(Context context);

    void removeListener();

    Bundle request(String str, Bundle bundle);

    void setListener(StateChangeListener stateChangeListener);

    default void onCloseFinished() {
    }

    default void onCloseStarted() {
    }

    default void onOpenFinished() {
    }

    default void onOpenStarted() {
    }
}
