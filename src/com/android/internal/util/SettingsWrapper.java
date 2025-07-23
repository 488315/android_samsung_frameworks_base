package com.android.internal.util;

import android.content.ContentResolver;
import android.provider.Settings;

/* loaded from: classes4.dex */
public class SettingsWrapper {
    public String getStringForUser(ContentResolver contentResolver, String str, int i) {
        return Settings.System.getStringForUser(contentResolver, str, i);
    }

    public String putStringForUser(ContentResolver contentResolver, String str, int i) {
        return Settings.System.getStringForUser(contentResolver, str, i);
    }
}
