package com.android.systemui.plugins.subscreen;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface SubRoom {
    public static final String EXTRA_FOCUS_REQUIRED = "focusRequired";
    public static final String EXTRA_HAS_UNREAD = "hasUnread";
    public static final String EXTRA_KEY_AIRPLANE_MODE = "airplane";
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
