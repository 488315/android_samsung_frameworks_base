package com.android.systemui.shared.system;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import java.util.StringJoiner;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class QuickStepContract {
    public static final boolean ALLOW_BACK_GESTURE_IN_SHADE = BasicRuneWrapper.NAVBAR_GESTURE;
    public static boolean SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN = false;

    public static void addInterface(IInterface iInterface, Bundle bundle) {
        IBinder asBinder;
        if (iInterface == null || (asBinder = iInterface.asBinder()) == null) {
            return;
        }
        try {
            bundle.putIBinder(asBinder.getInterfaceDescriptor(), asBinder);
        } catch (RemoteException e) {
            Log.d("QuickStepContract", "Invalid interface description " + asBinder, e);
        }
    }

    public static String getSystemUiStateString(long j) {
        StringJoiner stringJoiner = new StringJoiner("|");
        if ((1 & j) != 0) {
            stringJoiner.add("screen_pinned");
        }
        if ((128 & j) != 0) {
            stringJoiner.add("overview_disabled");
        }
        if ((256 & j) != 0) {
            stringJoiner.add("home_disabled");
        }
        if ((1024 & j) != 0) {
            stringJoiner.add("search_disabled");
        }
        if ((2 & j) != 0) {
            stringJoiner.add("navbar_hidden");
        }
        if ((4 & j) != 0) {
            stringJoiner.add("notif_expanded");
        }
        if ((2048 & j) != 0) {
            stringJoiner.add("qs_visible");
        }
        if ((64 & j) != 0) {
            stringJoiner.add("keygrd_visible");
        }
        if ((512 & j) != 0) {
            stringJoiner.add("keygrd_occluded");
        }
        if ((8 & j) != 0) {
            stringJoiner.add("bouncer_visible");
        }
        if ((32768 & j) != 0) {
            stringJoiner.add("dialog_showing");
        }
        if ((16 & j) != 0) {
            stringJoiner.add("a11y_click");
        }
        if ((32 & j) != 0) {
            stringJoiner.add("a11y_long_click");
        }
        if ((4096 & j) != 0) {
            stringJoiner.add("disable_gesture_split_invocation");
        }
        if ((8192 & j) != 0) {
            stringJoiner.add("asst_gesture_constrain");
        }
        if ((16384 & j) != 0) {
            stringJoiner.add("bubbles_expanded");
        }
        if ((65536 & j) != 0) {
            stringJoiner.add("one_handed_active");
        }
        if ((131072 & j) != 0) {
            stringJoiner.add("allow_gesture");
        }
        if ((262144 & j) != 0) {
            stringJoiner.add("ime_visible");
        }
        if ((524288 & j) != 0) {
            stringJoiner.add("magnification_overlap");
        }
        if ((1048576 & j) != 0) {
            stringJoiner.add("ime_switcher_button_visible");
        }
        if ((2097152 & j) != 0) {
            stringJoiner.add("device_dozing");
        }
        if ((4194304 & j) != 0) {
            stringJoiner.add("back_disabled");
        }
        if ((8388608 & j) != 0) {
            stringJoiner.add("bubbles_mange_menu_expanded");
        }
        if ((33554432 & j) != 0) {
            stringJoiner.add("vis_win_showing");
        }
        if ((67108864 & j) != 0) {
            stringJoiner.add("freeform_active_in_desktop_mode");
        }
        if ((134217728 & j) != 0) {
            stringJoiner.add("device_dreaming");
        }
        if ((536870912 & j) != 0) {
            stringJoiner.add("wakefulness_transition");
        }
        if ((268435456 & j) != 0) {
            stringJoiner.add("awake");
        }
        if ((1073741824 & j) != 0) {
            stringJoiner.add("notif_visible");
        }
        if ((2147483648L & j) != 0) {
            stringJoiner.add("keygrd_going_away");
        }
        if ((4294967296L & j) != 0) {
            stringJoiner.add("shortcut_helper_showing");
        }
        if ((8589934592L & j) != 0) {
            stringJoiner.add("touchpad_gestures_disabled");
        }
        if ((17179869184L & j) != 0) {
            stringJoiner.add("disable_gesture_pip_animating");
        }
        if ((34359738368L & j) != 0) {
            stringJoiner.add("communal_hub_showing");
        }
        if ((68719476736L & j) != 0) {
            stringJoiner.add("back_dismiss_ime");
        }
        if ((137438953472L & j) != 0) {
            stringJoiner.add("game_tools_showing");
        }
        if ((274877906944L & j) != 0) {
            stringJoiner.add("requested_recent_key");
        }
        if ((549755813888L & j) != 0) {
            stringJoiner.add("requested_home_key");
        }
        if ((1099511627776L & j) != 0) {
            stringJoiner.add("navbar_gone");
        }
        if ((j & 2199023255552L) != 0) {
            stringJoiner.add("knox_hard_key_intent");
        }
        return stringJoiner.toString();
    }

    public static boolean isAssistantGestureDisabled(long j) {
        if ((131072 & j) != 0) {
            j &= -3;
        }
        if ((3083 & j) != 0) {
            return true;
        }
        return (4 & j) != 0 && (j & 64) == 0;
    }

    public static boolean isBackGestureDisabled(long j, boolean z) {
        if ((8 & j) == 0 && (32768 & j) == 0 && (33554432 & j) == 0) {
            if ((34359738368L & j) != 0) {
                return (j & 2048) == 0;
            }
            if ((131072 & j) != 0 || SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN) {
                j &= -3;
            }
            long j2 = !z ? 1099515822082L : 1099515822080L;
            if (!ALLOW_BACK_GESTURE_IN_SHADE) {
                j2 |= 4;
            }
            if ((j & j2) != 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGesturalMode(int i) {
        return i == 2 || i == 3;
    }
}
