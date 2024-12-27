package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class GlobalSettingsImpl implements GlobalSettings {
    private final ContentResolver mContentResolver;

    public GlobalSettingsImpl(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public ContentResolver getContentResolver() {
        return this.mContentResolver;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public String getString(String str) {
        return Settings.Global.getString(this.mContentResolver, str);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public Uri getUriFor(String str) {
        return Settings.Global.getUriFor(str);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public boolean putString(String str, String str2) {
        return Settings.Global.putString(this.mContentResolver, str, str2);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public boolean putString(String str, String str2, String str3, boolean z) {
        return Settings.Global.putString(this.mContentResolver, str, str2, str3, z);
    }
}
