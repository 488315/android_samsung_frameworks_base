package com.android.wm.shell.onehanded;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.wm.shell.onehanded.OneHandedController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OneHandedSettingsUtil {
    public static final String ONE_HANDED_MODE_TARGET_NAME = AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME.getShortClassName();

    public static boolean getSettingsOneHandedModeEnabled(ContentResolver contentResolver, int i) {
        return Settings.Secure.getIntForUser(contentResolver, "one_handed_mode_enabled", 0, i) == 1;
    }

    public static boolean getSettingsSwipeToNotificationEnabled(ContentResolver contentResolver, int i) {
        return Settings.Secure.getIntForUser(contentResolver, "swipe_bottom_to_notification_enabled", 0, i) == 1;
    }

    public static void registerSettingsKeyObserver(String str, ContentResolver contentResolver, OneHandedController.AnonymousClass5 anonymousClass5, int i) {
        Uri uriFor = Settings.Secure.getUriFor(str);
        if (contentResolver == null || uriFor == null) {
            return;
        }
        contentResolver.registerContentObserver(uriFor, false, anonymousClass5, i);
    }
}
