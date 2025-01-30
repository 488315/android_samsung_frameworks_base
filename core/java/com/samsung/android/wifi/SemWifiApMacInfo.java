package com.samsung.android.wifi;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class SemWifiApMacInfo {
  private static final int BUFFER_SIZE = 64;
  private static final String WIFI_MAC_INFO = "/data/misc/wifi_hostapd/wifimac.info";
  public static final int WL_FAIL = 2;
  public static final int WL_SUCCESS = 1;
  private static volatile SemWifiApMacInfo uniqueInstance;
  private String TAG = "SemWifiApMacInfo";

  private SemWifiApMacInfo() {
    createOrChangePermission();
  }

  public static SemWifiApMacInfo getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new SemWifiApMacInfo();
    }
    return uniqueInstance;
  }

  private void createOrChangePermission() {
    File file = new File(WIFI_MAC_INFO);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        String[] cmd = {
          "/system/bin/sh", "-c", "/system/bin/chmod 665 /data/misc/wifi_hostapd/wifimac.info"
        };
        Process p = Runtime.getRuntime().exec(cmd);
        try {
          p.waitFor();
          p.destroy();
        } catch (InterruptedException e2) {
          e2.printStackTrace();
        }
      } catch (IOException e3) {
        e3.printStackTrace();
      }
    }
  }

  public String readWifiMacInfo() {
    BufferedReader buf = null;
    String mac = null;
    try {
      try {
        try {
          FileInputStream fis = new FileInputStream(WIFI_MAC_INFO);
          InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
          buf = new BufferedReader(isr, 64);
          mac = buf.readLine();
          buf.close();
        } catch (IOException e) {
          e.printStackTrace();
          if (buf != null) {
            buf.close();
          }
        }
      } catch (Throwable th) {
        if (buf != null) {
          try {
            buf.close();
          } catch (IOException e2) {
            e2.printStackTrace();
          }
        }
        throw th;
      }
    } catch (IOException e3) {
      e3.printStackTrace();
    }
    Log.m94d(this.TAG, "JDM MAC" + (mac != null ? mac.substring(9) : null));
    return mac;
  }

  public void writeWifiMacInfo(String str) {
    OutputStreamWriter fw = null;
    if (str != null && !str.isEmpty() && isMacAddress(str) && str.length() >= 17) {
      if (str.length() == 17 && str.substring(9).equals("00:00:00")) {
        return;
      }
      synchronized (this) {
        try {
          try {
            fw =
                new OutputStreamWriter(new FileOutputStream(WIFI_MAC_INFO), StandardCharsets.UTF_8);
            fw.write(str);
            try {
              fw.close();
            } catch (IOException e) {
              e = e;
              e.printStackTrace();
            }
          } catch (IOException e2) {
            e2.printStackTrace();
            if (fw != null) {
              try {
                fw.close();
              } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
              }
            }
          }
        } finally {
        }
      }
    }
  }

  private boolean isMacAddress(String macAddressCandidate) {
    Pattern macPattern =
        Pattern.compile(
            "[0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0"
                + " -9a-fA-F]{2}[-:][0-9a-fA-F]{2}");
    Matcher m = macPattern.matcher(macAddressCandidate);
    return m.matches();
  }
}
