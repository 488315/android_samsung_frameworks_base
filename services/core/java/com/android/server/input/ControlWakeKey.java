package com.android.server.input;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.text.TextUtils;
import android.util.Slog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class ControlWakeKey {
  public static String TAG = "InputManager.ControlWakeKey";
  public Context mContext;
  public HashMap mWakeKeyRefCount;
  public String mWakeKeyFilePath = "/sys/class/sec/sec_key/wakeup_keys";
  public String mWakeKeyFilePath1 = "/sys/power/volkey_wakeup";
  public String mWakeKeyFilePath2 = "/sys/class/sec/ap_pmic/volkey_wakeup";
  public String mDefaultWakeKey = "116,172";

  public ControlWakeKey(Context context) {
    this.mWakeKeyRefCount = null;
    this.mContext = context;
    this.mWakeKeyRefCount = new HashMap();
    boolean z = true;
    makeWakeKeyRefCount(true, this.mDefaultWakeKey);
    if (!this.mWakeKeyRefCount.containsKey("114") && !this.mWakeKeyRefCount.containsKey("115")) {
      z = false;
    }
    writeWakeKeyVolume(z, this.mWakeKeyFilePath1);
    writeWakeKeyVolume(z, this.mWakeKeyFilePath2);
    writeWakeKeyString(makeWakeKeyString());
  }

  public void setWakeKeyDynamicallyInternal(boolean z, String str) {
    boolean z2 = true;
    if (this.mWakeKeyRefCount.isEmpty()) {
      makeWakeKeyRefCount(true, this.mDefaultWakeKey);
    }
    makeWakeKeyRefCount(z, str);
    if (!this.mWakeKeyRefCount.containsKey("114") && !this.mWakeKeyRefCount.containsKey("115")) {
      z2 = false;
    }
    writeWakeKeyVolume(z2, this.mWakeKeyFilePath1);
    writeWakeKeyVolume(z2, this.mWakeKeyFilePath2);
    writeWakeKeyString(makeWakeKeyString());
  }

  public final void makeWakeKeyRefCount(boolean z, String str) {
    String[] split = str.split(",");
    int i = 0;
    if (z) {
      int length = split.length;
      while (i < length) {
        String trim = split[i].trim();
        int intValue =
            this.mWakeKeyRefCount.containsKey(trim)
                ? ((Integer) this.mWakeKeyRefCount.get(trim)).intValue() + 1
                : 1;
        this.mWakeKeyRefCount.put(trim, Integer.valueOf(intValue));
        Slog.d(TAG, "mWakeKeyRefCount(" + trim + ") is increased: " + intValue);
        i++;
      }
      return;
    }
    int length2 = split.length;
    while (i < length2) {
      String trim2 = split[i].trim();
      if (this.mWakeKeyRefCount.containsKey(trim2)) {
        int intValue2 = ((Integer) this.mWakeKeyRefCount.get(trim2)).intValue() - 1;
        if (intValue2 <= 0) {
          this.mWakeKeyRefCount.remove(trim2);
          Slog.i(TAG, "keyCode is removed: " + trim2);
        } else {
          this.mWakeKeyRefCount.put(trim2, Integer.valueOf(intValue2));
          Slog.d(TAG, "mWakeKeyRefCount(" + trim2 + ") is decreased: " + intValue2);
        }
      }
      i++;
    }
  }

  public final String makeWakeKeyString() {
    StringBuffer stringBuffer = new StringBuffer();
    if (!this.mWakeKeyRefCount.isEmpty()) {
      int size = this.mWakeKeyRefCount.size();
      String[] strArr = new String[size];
      this.mWakeKeyRefCount.keySet().toArray(strArr);
      for (int i = 0; i < size; i++) {
        stringBuffer.append(strArr[i]);
        stringBuffer.append(",");
      }
      stringBuffer.deleteCharAt(stringBuffer.length() - 1);
      Slog.d(TAG, "keyCodes in makeWakeKeyString is " + stringBuffer.toString());
    } else {
      Slog.d(TAG, "mWakeKeyRefCount is empty");
    }
    return new String(stringBuffer.toString());
  }

  /* JADX WARN: Removed duplicated region for block: B:10:0x005b A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:18:0x0034 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void writeWakeKeyVolume(boolean z, String str) {
    FileOutputStream fileOutputStream;
    String str2 = z ? "1" : "0";
    FileOutputStream fileOutputStream2 = null;
    try {
      fileOutputStream = new FileOutputStream(new File(str));
      try {
        Slog.i(TAG, "FileOutputStreamvolume is passed");
      } catch (FileNotFoundException unused) {
        fileOutputStream2 = fileOutputStream;
        Slog.i(TAG, "file not found: " + str);
        fileOutputStream = fileOutputStream2;
        if (fileOutputStream != null) {}
        if (fileOutputStream == null) {}
      }
    } catch (FileNotFoundException unused2) {
    }
    if (fileOutputStream != null) {
      try {
        fileOutputStream.write(str2.getBytes());
        Slog.i(TAG, "fosWakeKeyvolume writing is passed " + str2);
      } catch (IOException unused3) {
        Slog.i(TAG, "fosWakeKeyvolume writing is failed");
      }
    }
    if (fileOutputStream == null) {
      try {
        fileOutputStream.close();
        Slog.i(TAG, "closing fosWakeKeyvolume is passed");
      } catch (IOException unused4) {
        Slog.i(TAG, "closing fosWakeKeyvolume is failed");
      }
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:16:0x0031 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:8:0x0049 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void writeWakeKeyString(String str) {
    FileOutputStream fileOutputStream;
    FileOutputStream fileOutputStream2 = null;
    try {
      fileOutputStream = new FileOutputStream(new File(this.mWakeKeyFilePath));
      try {
        Slog.i(TAG, "FileOutputStream is passed");
      } catch (FileNotFoundException unused) {
        fileOutputStream2 = fileOutputStream;
        Slog.i(TAG, "file not found: " + this.mWakeKeyFilePath);
        fileOutputStream = fileOutputStream2;
        if (fileOutputStream != null) {}
        if (fileOutputStream == null) {}
      }
    } catch (FileNotFoundException unused2) {
    }
    if (fileOutputStream != null) {
      try {
        fileOutputStream.write(str.getBytes());
        Slog.i(TAG, "fosWakeKey writing is passed");
      } catch (IOException unused3) {
        Slog.i(TAG, "fosWakeKey writing is failed");
      }
    }
    if (fileOutputStream == null) {
      try {
        fileOutputStream.close();
        Slog.i(TAG, "closing fosWakeKey is passed");
      } catch (IOException unused4) {
        Slog.i(TAG, "closing fosWakeKey is failed");
      }
    }
  }

  public void setWakeKeyDynamically(String str, boolean z, String str2) {
    boolean z2;
    if (TextUtils.isEmpty(str)) {
      String str3 = TAG;
      StringBuilder sb = new StringBuilder();
      sb.append("setWakeKeyDynamically: pkg=");
      sb.append(str != null ? "empty" : "null");
      Slog.i(str3, sb.toString());
      return;
    }
    if (TextUtils.isEmpty(str2)) {
      String str4 = TAG;
      StringBuilder sb2 = new StringBuilder();
      sb2.append("setWakeKeyDynamically: pkg=");
      sb2.append(str);
      sb2.append(", keyCodes=");
      sb2.append(str2 != null ? "empty" : "null");
      Slog.d(str4, sb2.toString());
      return;
    }
    int callingUid = Binder.getCallingUid();
    Slog.i(
        TAG,
        "setWakeKeyDynamically: pkg="
            + str
            + ", uid="
            + callingUid
            + ", keyCodes="
            + str2
            + ", put="
            + z);
    PackageManager packageManager = this.mContext.getPackageManager();
    if (packageManager == null) {
      Slog.i(TAG, "pm is null");
      return;
    }
    String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
    if (packagesForUid == null || packagesForUid.length == 0) {
      String str5 = TAG;
      StringBuilder sb3 = new StringBuilder();
      sb3.append("packages: ");
      sb3.append(packagesForUid != null ? "empty" : "null");
      Slog.i(str5, sb3.toString());
      return;
    }
    int length = packagesForUid.length;
    int i = 0;
    while (true) {
      if (i >= length) {
        z2 = false;
        break;
      } else {
        if (str.equals(packagesForUid[i])) {
          z2 = true;
          break;
        }
        i++;
      }
    }
    try {
      ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
      if (applicationInfo == null) {
        Slog.i(TAG, "appinfo is null");
        return;
      }
      if (!z2 || !applicationInfo.isPrivilegedApp()) {
        Slog.i(
            TAG,
            "uidHasPackage is " + z2 + ", appinfo.privateFlags is " + applicationInfo.privateFlags);
        if (!z2) {
          for (String str6 : packagesForUid) {
            Slog.d(TAG, "packages with uid " + callingUid + ": " + str6);
          }
        }
        if (Binder.getCallingUid() != 1000) {
          throw new SecurityException(
              "only system app can use this method, but " + str + " is not system app");
        }
      }
      try {
        setWakeKeyDynamicallyInternal(z, str2);
      } catch (NullPointerException e) {
        e.printStackTrace();
      }
    } catch (PackageManager.NameNotFoundException unused) {
      Slog.i(TAG, "NameNotFoundException is occured");
    }
  }
}
