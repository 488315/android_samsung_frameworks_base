package com.samsung.android.emergencymode;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

/* loaded from: classes5.dex */
public final class Elog {
  private static final boolean DEBUG = true;
  private static final String M_TAG = "EmergencyMode";

  /* renamed from: d */
  public static void m254d(String moduleTag, String log) {
    Log.m94d(M_TAG, NavigationBarInflaterView.SIZE_MOD_START + moduleTag + "] " + log);
  }

  /* renamed from: v */
  public static void m255v(String moduleTag, String log) {
    Log.m100v(M_TAG, NavigationBarInflaterView.SIZE_MOD_START + moduleTag + "] " + log);
  }
}
