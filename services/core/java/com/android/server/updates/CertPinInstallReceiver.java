package com.android.server.updates;

/* loaded from: classes3.dex */
public class CertPinInstallReceiver extends ConfigUpdateInstallReceiver {
  public CertPinInstallReceiver() {
    super("/data/misc/keychain/", "pins", "metadata/", "version");
  }
}
