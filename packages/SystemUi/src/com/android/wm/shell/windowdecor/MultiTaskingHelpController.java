package com.android.wm.shell.windowdecor;

import android.content.Context;
import android.provider.Settings;
import android.util.Slog;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultiTaskingHelpController {
    public static boolean FREEFORM_HANDLER_HELP_POPUP_ENABLED = false;
    public static boolean SPLIT_HANDLER_HELP_POPUP_ENABLED = false;
    public final Context mContext;
    public final int mWindowingMode;

    /* JADX WARN: Removed duplicated region for block: B:10:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public MultiTaskingHelpController(Context context, int i) {
        int i2;
        this.mContext = context;
        this.mWindowingMode = i;
        int i3 = 0;
        if (i != 6) {
            if (i == 5) {
                i2 = Settings.Global.getInt(context.getContentResolver(), "freeform_handler_help_popup_count", 0);
                FREEFORM_HANDLER_HELP_POPUP_ENABLED = i2 < 1;
            }
            if (CoreRune.SAFE_DEBUG) {
                return;
            }
            Slog.d("MultiTaskingHelpController", "MultiTaskingHelpController: windowingMode=" + i + " count = " + i3);
            return;
        }
        i2 = Settings.Global.getInt(context.getContentResolver(), "multi_split_quick_options_help_count", 0);
        SPLIT_HANDLER_HELP_POPUP_ENABLED = i2 < 1;
        i3 = i2;
        if (CoreRune.SAFE_DEBUG) {
        }
    }
}
