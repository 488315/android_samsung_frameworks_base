package com.android.server.contentprotection;

import android.app.admin.DevicePolicyCache;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.LocalServices;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ContentProtectionConsentManager {
    public volatile boolean mCachedContentProtectionUserConsent;
    public volatile boolean mCachedPackageVerifierConsent;
    public final ContentObserver mContentObserver;
    public final ContentResolver mContentResolver;
    public final DevicePolicyCache mDevicePolicyCache;
    public final DevicePolicyManagerInternal mDevicePolicyManagerInternal =
            (DevicePolicyManagerInternal)
                    LocalServices.getService(DevicePolicyManagerInternal.class);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            String lastPathSegment;
            if (uri == null || (lastPathSegment = uri.getLastPathSegment()) == null) {
                return;
            }
            if (lastPathSegment.equals("package_verifier_user_consent")) {
                ContentProtectionConsentManager contentProtectionConsentManager =
                        ContentProtectionConsentManager.this;
                contentProtectionConsentManager.mCachedPackageVerifierConsent =
                        Settings.Global.getInt(
                                        contentProtectionConsentManager.mContentResolver,
                                        "package_verifier_user_consent",
                                        0)
                                >= 1;
            } else if (!lastPathSegment.equals("content_protection_user_consent")) {
                Slog.w(
                        "ContentProtectionConsentManager",
                        "Ignoring unexpected property: ".concat(lastPathSegment));
            } else {
                ContentProtectionConsentManager contentProtectionConsentManager2 =
                        ContentProtectionConsentManager.this;
                contentProtectionConsentManager2.mCachedContentProtectionUserConsent =
                        Settings.Global.getInt(
                                        contentProtectionConsentManager2.mContentResolver,
                                        "content_protection_user_consent",
                                        0)
                                >= 0;
            }
        }
    }

    public ContentProtectionConsentManager(
            Handler handler, ContentResolver contentResolver, DevicePolicyCache devicePolicyCache) {
        this.mContentResolver = contentResolver;
        this.mDevicePolicyCache = devicePolicyCache;
        SettingsObserver settingsObserver = new SettingsObserver(handler);
        this.mContentObserver = settingsObserver;
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("package_verifier_user_consent"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("content_protection_user_consent"),
                false,
                settingsObserver,
                -1);
        this.mCachedPackageVerifierConsent =
                Settings.Global.getInt(contentResolver, "package_verifier_user_consent", 0) >= 1;
        this.mCachedContentProtectionUserConsent =
                Settings.Global.getInt(contentResolver, "content_protection_user_consent", 0) >= 0;
    }
}
