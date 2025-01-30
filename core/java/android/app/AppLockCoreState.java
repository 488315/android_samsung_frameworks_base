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
        SharedPreferences sharedPreferences = mPref;
        return sharedPreferences != null ? sharedPreferences.getString(APPLOCK_LOCKED_APPS_PACKAGES, "") : "";
    }

    public String getApplockLockedAppsClass() {
        SharedPreferences sharedPreferences = mPref;
        return sharedPreferences != null ? sharedPreferences.getString(APPLOCK_LOCKED_APPS_CLASSS, "") : "";
    }

    public int getApplockType() {
        SharedPreferences sharedPreferences = mPref;
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(APPLOCK_TYPE, 0);
        }
        return 0;
    }

    public boolean isApplockEnabled() {
        SharedPreferences sharedPreferences = mPref;
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(APPLOCK_ENABLED, false);
        }
        return false;
    }

    public String getSsecureHiddenAppsPackages() {
        SharedPreferences sharedPreferences = mPref;
        return sharedPreferences != null ? sharedPreferences.getString("ssecure_hidden_apps_packages", "") : "";
    }

    public void setSsecureHiddenAppsPackages(String packages) {
        SharedPreferences sharedPreferences = mPref;
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ssecure_hidden_apps_packages", packages);
            editor.apply();
        }
    }

    public void setApplockLockedAppsPackage(String packages) {
        SharedPreferences sharedPreferences = mPref;
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(APPLOCK_LOCKED_APPS_PACKAGES, packages);
            editor.apply();
        }
    }

    public void setApplockLockedAppsClass(String classes) {
        SharedPreferences sharedPreferences = mPref;
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(APPLOCK_LOCKED_APPS_CLASSS, classes);
            editor.apply();
        }
    }

    public void setApplockType(int type) {
        SharedPreferences sharedPreferences = mPref;
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(APPLOCK_TYPE, type);
            editor.apply();
        }
    }

    public void setApplockEnabled(boolean value) {
        SharedPreferences sharedPreferences = mPref;
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(APPLOCK_ENABLED, value);
            editor.apply();
        }
    }
}
