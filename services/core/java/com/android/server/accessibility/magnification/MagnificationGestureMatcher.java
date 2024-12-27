package com.android.server.accessibility.magnification;

public abstract class MagnificationGestureMatcher {
    public static String gestureIdToString(int i) {
        if (i == 201) {
            return "SEM_GESTURE_DOUBLE_TAP";
        }
        if (i == 202) {
            return "SEM_GESTURE_DOUBLE_TAP_AND_HOLD";
        }
        switch (i) {
            case 101:
                return "GESTURE_TWO_FINGERS_DOWN_OR_SWIPE";
            case 102:
                return "GESTURE_SWIPE";
            case 103:
                return "GESTURE_SINGLE_TAP";
            case 104:
                return "GESTURE_SINGLE_TAP_AND_HOLD";
            case 105:
                return "GESTURE_TRIPLE_TAP";
            case 106:
                return "GESTURE_TRIPLE_TAP_AND_HOLD";
            default:
                return "none";
        }
    }
}
