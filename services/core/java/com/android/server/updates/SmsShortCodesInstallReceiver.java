package com.android.server.updates;

/* loaded from: classes3.dex */
public class SmsShortCodesInstallReceiver extends ConfigUpdateInstallReceiver {
  public SmsShortCodesInstallReceiver() {
    super("/data/misc/sms/", "codes", "metadata/", "version");
  }
}
