package com.android.wm.shell.desktopmode;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DesktopModeStatus {
    public static final boolean IS_SUPPORTED = SystemProperties.getBoolean("persist.wm.debug.desktop_mode", CoreRune.MT_NEW_DEX_DEFAULT_DISPLAY_LAUNCH_POLICY);
    public static final boolean IS_PROTO2_ENABLED = SystemProperties.getBoolean("persist.wm.debug.desktop_mode_2", false);
    public static final boolean IS_VEILED_RESIZE_ENABLED = SystemProperties.getBoolean("persist.wm.debug.desktop_veiled_resizing", true);
    public static final boolean IS_DISPLAY_CHANGE_ENABLED = SystemProperties.getBoolean("persist.wm.debug.desktop_change_display", false);

    public static boolean isActive(Context context) {
        if (!isAnyEnabled()) {
            return false;
        }
        if (IS_PROTO2_ENABLED) {
            return true;
        }
        try {
            return (Settings.System.getIntForUser(context.getContentResolver(), "desktop_mode", -2) == 0 && (CoreRune.MT_NEW_DEX_DEFAULT_DISPLAY_LAUNCH_POLICY ? Settings.System.getIntForUser(context.getContentResolver(), "new_dex", -2) : 0) == 0) ? false : true;
        } catch (Exception e) {
            if (CoreRune.MT_NEW_DEX_DEFAULT_DISPLAY_LAUNCH_POLICY) {
                Settings.System.putInt(context.getContentResolver(), "desktop_mode", 0);
            }
            if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -824195809, 0, null, String.valueOf(e));
            }
            return false;
        }
    }

    public static boolean isAnyEnabled() {
        return IS_SUPPORTED || IS_PROTO2_ENABLED;
    }
}
