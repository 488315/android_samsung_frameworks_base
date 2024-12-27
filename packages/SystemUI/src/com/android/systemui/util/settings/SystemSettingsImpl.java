package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.settings.UserTracker;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class SystemSettingsImpl implements SystemSettings {
    private final ContentResolver mContentResolver;
    private final UserTracker mUserTracker;

    public SystemSettingsImpl(ContentResolver contentResolver, UserTracker userTracker) {
        this.mContentResolver = contentResolver;
        this.mUserTracker = userTracker;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public ContentResolver getContentResolver() {
        return this.mContentResolver;
    }

    @Override // com.android.systemui.util.settings.UserSettingsProxy
    public String getStringForUser(String str, int i) {
        return Settings.System.getStringForUser(this.mContentResolver, str, getRealUserHandle(i));
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public Uri getUriFor(String str) {
        return Settings.System.getUriFor(str);
    }

    @Override // com.android.systemui.util.settings.UserSettingsProxy
    public UserTracker getUserTracker() {
        return this.mUserTracker;
    }

    @Override // com.android.systemui.util.settings.UserSettingsProxy
    public boolean putString(String str, String str2, boolean z) {
        return Settings.System.putString(this.mContentResolver, str, str2, z);
    }

    @Override // com.android.systemui.util.settings.UserSettingsProxy
    public boolean putStringForUser(String str, String str2, int i) {
        return Settings.System.putStringForUser(this.mContentResolver, str, str2, getRealUserHandle(i));
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    public boolean putString(String str, String str2, String str3, boolean z) {
        throw new UnsupportedOperationException("This method only exists publicly for Settings.Secure and Settings.Global");
    }

    @Override // com.android.systemui.util.settings.UserSettingsProxy
    public boolean putStringForUser(String str, String str2, String str3, boolean z, int i, boolean z2) {
        throw new UnsupportedOperationException("This method only exists publicly for Settings.Secure and Settings.Global");
    }
}
