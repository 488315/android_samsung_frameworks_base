package com.android.server.appprelauncher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Slog;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes.dex */
public class PrelauncherStorage {
  public final Context mContext;
  public final SharedPreferences mSharedPrefs;

  public PrelauncherStorage(Context context) {
    this.mContext = context;
    this.mSharedPrefs =
        context
            .createDeviceProtectedStorageContext()
            .getSharedPreferences(
                new File(
                    new File(Environment.getDataSystemDirectory(), "shared_prefs"),
                    "prel_config.xml"),
                0);
  }

  public synchronized void storeScpmConfig(String str) {
    try {
      JSONObject jSONObject = new JSONObject(new JSONTokener(str));
      boolean z = jSONObject.getBoolean("disabled");
      Slog.i("PREL_STORAGE", "Disabled: " + z);
      this.mSharedPrefs.edit().putBoolean("disabled", z).apply();
      storeJsonArray("blocked_packages", jSONObject);
      storeJsonArray("blocked_activities", jSONObject);
      storeJsonArray("blocked_libs", jSONObject);
    } catch (Exception e) {
      Slog.e("PREL_STORAGE", "Exception occurred: " + e.getMessage());
    }
  }

  public synchronized boolean getScpmStopRule() {
    return this.mSharedPrefs.getBoolean("disabled", false);
  }

  public synchronized Set getBlockedPackages(Set set) {
    return this.mSharedPrefs.getStringSet("blocked_packages", set);
  }

  public synchronized Set getBlockedActivities(Set set) {
    return this.mSharedPrefs.getStringSet("blocked_activities", set);
  }

  public synchronized Set getBlockedLibs(Set set) {
    return this.mSharedPrefs.getStringSet("blocked_libs", set);
  }

  public final void storeJsonArray(String str, JSONObject jSONObject) {
    try {
      JSONArray jSONArray = jSONObject.getJSONArray(str);
      HashSet hashSet = new HashSet();
      for (int i = 0; i < jSONArray.length(); i++) {
        hashSet.add(jSONArray.getString(i));
      }
      this.mSharedPrefs.edit().putStringSet(str, hashSet).apply();
    } catch (Exception e) {
      Slog.e("PREL_STORAGE", "Exception occurred: " + e.getMessage());
    }
  }
}
