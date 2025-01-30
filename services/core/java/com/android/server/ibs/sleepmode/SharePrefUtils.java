package com.android.server.ibs.sleepmode;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes2.dex */
public abstract class SharePrefUtils {
  public static void putInt(Context context, String str, int i) {
    SharedPreferences.Editor edit = context.getSharedPreferences("sleep_mode_pref", 0).edit();
    edit.putInt(str, i);
    edit.apply();
  }

  public static int getInt(Context context, String str, int i) {
    try {
      return context.getSharedPreferences("sleep_mode_pref", 0).getInt(str, i);
    } catch (NullPointerException e) {
      e.printStackTrace();
      return i;
    }
  }

  public static void putLong(Context context, String str, long j) {
    SharedPreferences.Editor edit = context.getSharedPreferences("sleep_mode_pref", 0).edit();
    edit.putLong(str, j);
    edit.apply();
  }

  public static long getLong(Context context, String str, long j) {
    try {
      return context.getSharedPreferences("sleep_mode_pref", 0).getLong(str, j);
    } catch (NullPointerException e) {
      e.printStackTrace();
      return j;
    }
  }

  public static void putBoolean(Context context, String str, boolean z) {
    SharedPreferences.Editor edit = context.getSharedPreferences("sleep_mode_pref", 0).edit();
    edit.putBoolean(str, z);
    edit.apply();
  }

  public static boolean getBoolean(Context context, String str, boolean z) {
    try {
      return context.getSharedPreferences("sleep_mode_pref", 0).getBoolean(str, z);
    } catch (NullPointerException e) {
      e.printStackTrace();
      return z;
    }
  }

  public static void clear(Context context) {
    SharedPreferences.Editor edit = context.getSharedPreferences("sleep_mode_pref", 0).edit();
    edit.clear();
    edit.apply();
  }
}
