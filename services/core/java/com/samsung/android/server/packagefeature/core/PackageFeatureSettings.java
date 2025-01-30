package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;

/* loaded from: classes2.dex */
public class PackageFeatureSettings extends ContentObserver {
    public static final Uri URI_PACKAGE_POLICY_DISABLED = Settings.Global.getUriFor("package_policy_disabled");
    public final Callback mCallback;
    public final Context mContext;

    public interface Callback {
        void onSettingsChanged();
    }

    public PackageFeatureSettings(Context context, Handler handler, Callback callback) {
        super(handler);
        this.mContext = context;
        this.mCallback = callback;
        updateAllFeaturesDisabled(false);
        observe();
    }

    public final void observe() {
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("package_policy_disabled"), false, this, -1);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
        if (uri == null || !URI_PACKAGE_POLICY_DISABLED.equals(uri)) {
            return;
        }
        this.mCallback.onSettingsChanged();
    }

    public boolean isAllFeaturesDisabled() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "package_policy_disabled", 0) == 1;
    }

    public boolean updateAllFeaturesDisabled(boolean z) {
        if (z == isAllFeaturesDisabled()) {
            return false;
        }
        Settings.Global.putInt(this.mContext.getContentResolver(), "package_policy_disabled", z ? 1 : 0);
        return true;
    }
}
