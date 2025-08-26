package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.util.settings.SettingsProxy;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class SecureSettingsImpl implements SecureSettings {
    private final ContentResolver mContentResolver;
    private final SettingsProxy.CurrentUserIdProvider mCurrentUserProvider;
    private final CoroutineScope mSettingsScope;

    public SecureSettingsImpl(ContentResolver contentResolver, SettingsProxy.CurrentUserIdProvider currentUserIdProvider, @SettingsSingleThreadBackground CoroutineScope coroutineScope) {
        this.mContentResolver = contentResolver;
        this.mCurrentUserProvider = currentUserIdProvider;
        this.mSettingsScope = coroutineScope;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public ContentResolver getContentResolver() {
        return this.mContentResolver;
    }

    @Override // com.android.systemui.util.settings.UserSettingsProxy
    public SettingsProxy.CurrentUserIdProvider getCurrentUserProvider() {
        return this.mCurrentUserProvider;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public CoroutineScope getSettingsScope() {
        return this.mSettingsScope;
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
