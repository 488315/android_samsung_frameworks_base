package android.app;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes.dex */
public class AppLockCoreState {
    private static final String APPLOCK_ENABLED = "applock_enabled";
    private static final String APPLOCK_LOCKED_APPS_CLASSS = "applock_locked_classes";
    private static final String APPLOCK_LOCKED_APPS_PACKAGES = "applock_locked_packages";
    private static final String APPLOCK_SHARED_PREF = "applock_shared_preference";
    private static final String APPLOCK_TYPE = "applock_type";
    private static final String SSECURE_HIDDEN_APPS_PACKAGES = "ssecure_hidden_apps_packages";
    private static final String TAG = "AppLockCoreState";
    private static SharedPreferences mPref;
    private final Context mContext;

    public AppLockCoreState(Context context) {
        this.mContext = context;
    }

    public void initializeSharedPreference() {
        if (mPref == null) {
            mPref = this.mContext.getSharedPreferences(APPLOCK_SHARED_PREF, 0);
        }
    }

    public String getApplockLockedAppsPackage() {
        return mPref != null ? mPref.getString("applock_locked_packages", "") : "";
    }

    public String getApplockLockedAppsClass() {
        return mPref != null ? mPref.getString(APPLOCK_LOCKED_APPS_CLASSS, "") : "";
    }

    public int getApplockType() {
        if (mPref != null) {
            return mPref.getInt(APPLOCK_TYPE, 0);
        }
        return 0;
    }

    public boolean isApplockEnabled() {
        if (mPref != null) {
            return mPref.getBoolean(APPLOCK_ENABLED, false);
        }
        return false;
    }

    public String getSsecureHiddenAppsPackages() {
        return mPref != null ? mPref.getString("ssecure_hidden_apps_packages", "") : "";
    }

    public void setSsecureHiddenAppsPackages(String packages) {
        if (mPref != null) {
            SharedPreferences.Editor editor = mPref.edit();
            editor.putString("ssecure_hidden_apps_packages", packages);
            editor.apply();
        }
    }

    public void setApplockLockedAppsPackage(String packages) {
        if (mPref != null) {
            SharedPreferences.Editor editor = mPref.edit();
            editor.putString("applock_locked_packages", packages);
            editor.apply();
        }
    }

    public void setApplockLockedAppsClass(String classes) {
        if (mPref != null) {
            SharedPreferences.Editor editor = mPref.edit();
            editor.putString(APPLOCK_LOCKED_APPS_CLASSS, classes);
            editor.apply();
        }
    }

    public void setApplockType(int type) {
        if (mPref != null) {
            SharedPreferences.Editor editor = mPref.edit();
            editor.putInt(APPLOCK_TYPE, type);
            editor.apply();
        }
    }

    public void setApplockEnabled(boolean value) {
        if (mPref != null) {
            SharedPreferences.Editor editor = mPref.edit();
            editor.putBoolean(APPLOCK_ENABLED, value);
            editor.apply();
        }
    }
}
