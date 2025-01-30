package com.android.server.updates;

/* loaded from: classes3.dex */
public class CarrierProvisioningUrlsInstallReceiver extends ConfigUpdateInstallReceiver {
  public CarrierProvisioningUrlsInstallReceiver() {
    super("/data/misc/radio/", "provisioning_urls.xml", "metadata/", "version");
  }
}
