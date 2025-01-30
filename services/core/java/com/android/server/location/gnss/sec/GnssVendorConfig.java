package com.android.server.location.gnss.sec;

import android.os.Build;
import android.os.SystemProperties;
import java.io.File;

/* loaded from: classes2.dex */
public class GnssVendorConfig {
  public static GnssVendorConfig mInstance;

  public static synchronized GnssVendorConfig getInstance() {
    GnssVendorConfig gnssVendorConfig;
    synchronized (GnssVendorConfig.class) {
      if (mInstance == null) {
        mInstance = new GnssVendorConfig();
      }
      gnssVendorConfig = mInstance;
    }
    return gnssVendorConfig;
  }

  public boolean isIzatServiceEnabled() {
    return isQcomHardware() && !isNonQcomGnss();
  }

  public boolean isQcomHardware() {
    return "qcom".equals(Build.HARDWARE);
  }

  public boolean isNonQcomGnss() {
    return isBroadcomGnss() || isLsiGnss() || isMtkGnss();
  }

  public boolean isMtkGnss() {
    return new File("vendor/etc/gnss/mnl.prop").exists();
  }

  public boolean isLsiGnss() {
    return new File("vendor/etc/gnss/gps.cfg").exists();
  }

  public boolean isBroadcomGnss() {
    return new File("vendor/etc/gnss/gps.xml").exists();
  }

  public boolean isUnisocGnss() {
    return SystemProperties.get("ro.hardware.chipname", "Unknown").toLowerCase().contains("unisoc");
  }
}
