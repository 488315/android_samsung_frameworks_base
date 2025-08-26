package com.android.wm.shell.windowdecor;

import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public class CaptionGlobalState {
    public static String COLOR_THEME_COLOR = "";
    public static boolean COLOR_THEME_ENABLED = false;
    public static boolean FULLSCREEN_HANDLER_ENABLED = false;
    public static boolean USER_SETUP_COMPLETED = false;

    public final String toString() {
        StringBuilder sb = new StringBuilder("CaptionGlobalState{COLOR_THEME_ENABLED = ");
        sb.append(COLOR_THEME_ENABLED);
        sb.append(" FULLSCREEN_HANDLER_ENABLED = ");
        sb.append(FULLSCREEN_HANDLER_ENABLED);
        sb.append(" USER_SETUP_COMPLETED = ");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, USER_SETUP_COMPLETED, " TRANSIENT_DELAY=-1}");
    }
}
