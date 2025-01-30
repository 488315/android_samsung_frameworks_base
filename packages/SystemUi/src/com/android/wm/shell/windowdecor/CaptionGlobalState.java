package com.android.wm.shell.windowdecor;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CaptionGlobalState {
    public static String COLOR_THEME_COLOR = "";
    public static boolean COLOR_THEME_ENABLED = false;
    public static boolean FULLSCREEN_HANDLER_ENABLED = false;
    public static int TRANSIENT_DELAY = -1;

    public final String toString() {
        StringBuilder sb = new StringBuilder("CaptionGlobalState{COLOR_THEME_ENABLED=");
        sb.append(COLOR_THEME_ENABLED);
        sb.append(" FULLSCREEN_HANDLER_ENABLED=");
        sb.append(FULLSCREEN_HANDLER_ENABLED);
        sb.append(" TRANSIENT_DELAY=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, TRANSIENT_DELAY, "}");
    }
}
