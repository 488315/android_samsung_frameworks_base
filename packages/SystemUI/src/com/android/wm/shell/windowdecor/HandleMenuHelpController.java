package com.android.wm.shell.windowdecor;

import android.content.Context;
import android.provider.Settings;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class HandleMenuHelpController {
    public static boolean FREEFORM_HANDLER_HELP_POPUP_ENABLED = false;
    public static boolean SPLIT_HANDLER_HELP_POPUP_ENABLED = false;
    public final Context mContext;

    public HandleMenuHelpController(Context context) {
        this.mContext = context;
        SPLIT_HANDLER_HELP_POPUP_ENABLED = Settings.Global.getInt(context.getContentResolver(), "multi_split_quick_options_help_count", 0) < 1;
        FREEFORM_HANDLER_HELP_POPUP_ENABLED = Settings.Global.getInt(context.getContentResolver(), "freeform_handler_help_popup_count", 0) < 1;
    }
}
