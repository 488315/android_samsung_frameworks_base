package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.settings.UserTracker;

/* JADX INFO: Access modifiers changed from: package-private */
public class SecureSettingsImpl implements SecureSettings {
    private final ContentResolver mContentResolver;
    private final UserTracker mUserTracker;

    public SecureSettingsImpl(ContentResolver contentResolver, UserTracker userTracker) {
        this.mContentResolver = contentResolver;
        this.mUserTracker = userTracker;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public ContentResolver getContentResolver() {
        return this.mContentResolver;
    }

    @Override // com.android.systemui.util.settings.UserSettingsProxy
    public String getStringForUser(String str, int i) {
        return Settings.Secure.getStringForUser(this.mContentResolver, str, getRealUserHandle(i));
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public Uri getUriFor(String str) {
        return Settings.Secure.getUriFor(str);
    }

    @Override // com.android.systemui.util.settings.UserSettingsProxy
    public UserTracker getUserTracker() {
        return this.mUserTracker;
    }

    @Override // com.android.systemui.util.settings.UserSettingsProxy
    public boolean putString(String str, String str2, boolean z) {
        return Settings.Secure.putString(this.mContentResolver, str, str2, z);
    }

    @Override // com.android.systemui.util.settings.UserSettingsProxy
    public boolean putStringForUser(String str, String str2, int i) {
        return Settings.Secure.putStringForUser(this.mContentResolver, str, str2, getRealUserHandle(i));
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public boolean putString(String str, String str2, String str3, boolean z) {
        return Settings.Secure.putString(this.mContentResolver, str, str2, str3, z);
    }

    @Override // com.android.systemui.util.settings.UserSettingsProxy
    public boolean putStringForUser(String str, String str2, String str3, boolean z, int i, boolean z2) {
        return Settings.Secure.putStringForUser(this.mContentResolver, str, str2, str3, z, getRealUserHandle(i), z2);
    }
}
