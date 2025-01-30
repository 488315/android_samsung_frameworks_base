package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.settings.UserTracker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecureSettingsImpl implements SecureSettings {
    public final ContentResolver mContentResolver;
    public final UserTracker mUserTracker;

    public SecureSettingsImpl(ContentResolver contentResolver, UserTracker userTracker) {
        this.mContentResolver = contentResolver;
        this.mUserTracker = userTracker;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public final ContentResolver getContentResolver() {
        return this.mContentResolver;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public final String getStringForUser(int i, String str) {
        return Settings.Secure.getStringForUser(this.mContentResolver, str, getRealUserHandle(i));
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public final Uri getUriFor(String str) {
        return Settings.Secure.getUriFor(str);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public final UserTracker getUserTracker() {
        return this.mUserTracker;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public final boolean putStringForUser(int i, String str, String str2) {
        return Settings.Secure.putStringForUser(this.mContentResolver, str, str2, getRealUserHandle(i));
    }
}
