package com.samsung.android.ims;

/* loaded from: classes5.dex */
public interface SemImsRegistrationListener {
  void onDeregistered(
      SemImsRegistration semImsRegistration, SemImsRegistrationError semImsRegistrationError);

  void onRegistered(SemImsRegistration semImsRegistration);
}
