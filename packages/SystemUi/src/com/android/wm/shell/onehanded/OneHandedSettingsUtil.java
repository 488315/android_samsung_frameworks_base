package com.android.wm.shell.onehanded;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.wm.shell.onehanded.OneHandedController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OneHandedSettingsUtil {
    public static final String ONE_HANDED_MODE_TARGET_NAME = AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME.getShortClassName();

    public static boolean getSettingsOneHandedModeEnabled(ContentResolver contentResolver, int i) {
        return Settings.Secure.getIntForUser(contentResolver, "one_handed_mode_enabled", 0, i) == 1;
    }

    public static boolean getSettingsSwipeToNotificationEnabled(ContentResolver contentResolver, int i) {
        return Settings.Secure.getIntForUser(contentResolver, "swipe_bottom_to_notification_enabled", 0, i) == 1;
    }

    public static void registerSettingsKeyObserver(String str, ContentResolver contentResolver, OneHandedController.C40305 c40305, int i) {
        Uri uriFor = Settings.Secure.getUriFor(str);
        if (contentResolver == null || uriFor == null) {
            return;
        }
        contentResolver.registerContentObserver(uriFor, false, c40305, i);
    }
}
