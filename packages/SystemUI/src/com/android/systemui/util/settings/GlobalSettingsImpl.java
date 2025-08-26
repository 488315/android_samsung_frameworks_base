package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class GlobalSettingsImpl implements GlobalSettings {
    private final ContentResolver mContentResolver;
    private final CoroutineScope mSettingsScope;

    public GlobalSettingsImpl(ContentResolver contentResolver, @SettingsSingleThreadBackground CoroutineScope coroutineScope) {
        this.mContentResolver = contentResolver;
        this.mSettingsScope = coroutineScope;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public ContentResolver getContentResolver() {
        return this.mContentResolver;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public CoroutineScope getSettingsScope() {
        return this.mSettingsScope;
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
