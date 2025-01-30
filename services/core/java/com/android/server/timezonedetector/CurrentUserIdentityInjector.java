package com.android.server.timezonedetector;

/* loaded from: classes3.dex */
public interface CurrentUserIdentityInjector {
  public static final CurrentUserIdentityInjector REAL = new Real();

  public class Real implements CurrentUserIdentityInjector {}
}
