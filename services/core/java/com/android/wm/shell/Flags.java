package com.android.wm.shell;

public abstract class Flags {
    public static boolean enablePip2Implementation() {
        return false;
    }

    public static boolean enableTinyTaskbar() {
        return false;
    }

    public static boolean onlyReuseBubbledTaskWhenLaunchedFromBubble() {
        return false;
    }
}
