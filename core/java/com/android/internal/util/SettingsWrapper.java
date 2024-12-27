package com.android.internal.util;

import android.content.ContentResolver;
import android.provider.Settings;

public class SettingsWrapper {
    public String getStringForUser(ContentResolver contentResolver, String name, int userHandle) {
        return Settings.System.getStringForUser(contentResolver, name, userHandle);
    }

    public String putStringForUser(ContentResolver contentResolver, String name, int userHandle) {
        return Settings.System.getStringForUser(contentResolver, name, userHandle);
    }
}
