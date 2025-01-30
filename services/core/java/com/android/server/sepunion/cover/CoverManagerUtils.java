package com.android.server.sepunion.cover;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import com.samsung.android.cover.CoverState;
import com.samsung.android.os.SemDvfsManager;
import com.samsung.android.sepunion.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class CoverManagerUtils {
  public static final String TAG = "CoverManager_" + CoverManagerUtils.class.getSimpleName();
  public static SemDvfsManager sCoverCpuBooster = null;
  public static SemDvfsManager sCoverCoreNumLockHelper = null;
  public static SemDvfsManager sCoverBusBooster = null;
  public static int BOOSTING_TIMEOUT = 2000;
  public static final boolean isSupportWirelessCharge = isSupportWirelessCharge();

  public static boolean isCoverTypeForWirelessCharger(int i) {
    return i == 7 || i == 8 || i == 14 || i == 15 || i == 16 || i == 0 || i == 17;
  }

  public static boolean needsCPUBoostCover(int i) {
    if (i == 8) {
      return true;
    }
    switch (i) {
      case 15:
      case 16:
      case 17:
        return true;
      default:
        return false;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:17:0x003b, code lost:

     if (r0 == null) goto L26;
  */
  /* JADX WARN: Code restructure failed: missing block: B:18:0x0037, code lost:

     r0.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:20:0x0035, code lost:

     if (r0 == null) goto L26;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static int getValueFromSysFS(String str, int i) {
    FileReader fileReader;
    if (isFileExists(str)) {
      FileReader fileReader2 = null;
      try {
        try {
          fileReader = new FileReader(str);
        } catch (IOException unused) {
        }
      } catch (IOException unused2) {
      } catch (NumberFormatException unused3) {
      } catch (Throwable th) {
        th = th;
      }
      try {
        char[] cArr = new char[15];
        int read = fileReader.read(cArr);
        if (read > 0) {
          i = Integer.parseInt(new String(cArr, 0, read - 1));
        }
        fileReader.close();
      } catch (IOException unused4) {
        fileReader2 = fileReader;
      } catch (NumberFormatException unused5) {
        fileReader2 = fileReader;
      } catch (Throwable th2) {
        th = th2;
        fileReader2 = fileReader;
        if (fileReader2 != null) {
          try {
            fileReader2.close();
          } catch (IOException unused6) {
          }
        }
        throw th;
      }
    }
    return i;
  }

  /* JADX WARN: Code restructure failed: missing block: B:17:0x0037, code lost:

     if (r0 == null) goto L26;
  */
  /* JADX WARN: Code restructure failed: missing block: B:18:0x0033, code lost:

     r0.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:20:0x0031, code lost:

     if (r0 == null) goto L26;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static String getValueFromSysFS(String str, String str2) {
    if (isFileExists(str)) {
      FileReader fileReader = null;
      try {
        try {
          FileReader fileReader2 = new FileReader(str);
          try {
            char[] cArr = new char[15];
            int read = fileReader2.read(cArr);
            if (read > 0) {
              str2 = new String(cArr, 0, read - 1);
            }
            fileReader2.close();
          } catch (IOException unused) {
            fileReader = fileReader2;
          } catch (NumberFormatException unused2) {
            fileReader = fileReader2;
          } catch (Throwable th) {
            th = th;
            fileReader = fileReader2;
            if (fileReader != null) {
              try {
                fileReader.close();
              } catch (IOException unused3) {
              }
            }
            throw th;
          }
        } catch (IOException unused4) {
        }
      } catch (IOException unused5) {
      } catch (NumberFormatException unused6) {
      } catch (Throwable th2) {
        th = th2;
      }
    }
    return str2;
  }

  public static boolean isFileExists(String str) {
    return new File(str).exists();
  }

  public static boolean isSamsungCover(CoverState coverState) {
    return coverState.getType() != 2 && coverState.getFriendsType() == 0;
  }

  public static boolean isClearCover(CoverState coverState) {
    int type = coverState.getType();
    return type == 8 || type == 15 || type == 16 || type == 17;
  }

  public static boolean isBackCover(CoverState coverState) {
    int type = coverState.getType();
    return type == 9 || type == 10 || type == 14 || type == 13 || type == 12;
  }

  public static void performCPUBoostCover(Context context) {
    int[] supportedFrequency;
    int[] supportedFrequency2;
    int i;
    int[] supportedFrequency3;
    if (sCoverCpuBooster == null) {
      SemDvfsManager createInstance = SemDvfsManager.createInstance(context, "COVER_BOOSTER", 12);
      sCoverCpuBooster = createInstance;
      if (createInstance != null
          && (supportedFrequency3 = createInstance.getSupportedFrequency()) != null) {
        sCoverCpuBooster.setDvfsValue(supportedFrequency3[0]);
      }
    }
    SemDvfsManager semDvfsManager = sCoverCpuBooster;
    if (semDvfsManager != null) {
      try {
        semDvfsManager.acquire(BOOSTING_TIMEOUT);
      } catch (Exception e) {
        Log.e(TAG, "sCoverCpuBooster.acquire is failed", e);
      }
    }
    if (sCoverCoreNumLockHelper == null) {
      SemDvfsManager createInstance2 =
          SemDvfsManager.createInstance(context, "COVER_CORE_BOOSTER", 14);
      sCoverCoreNumLockHelper = createInstance2;
      if (createInstance2 != null
          && (supportedFrequency2 = createInstance2.getSupportedFrequency()) != null
          && supportedFrequency2.length > 0
          && (i = supportedFrequency2[0]) >= 2) {
        sCoverCoreNumLockHelper.setDvfsValue(i);
      }
    }
    SemDvfsManager semDvfsManager2 = sCoverCoreNumLockHelper;
    if (semDvfsManager2 != null) {
      try {
        semDvfsManager2.acquire(BOOSTING_TIMEOUT);
      } catch (Exception e2) {
        Log.e(TAG, "sCoverCoreNumLockHelper.acquire is failed", e2);
      }
    }
    if (sCoverBusBooster == null) {
      SemDvfsManager createInstance3 =
          SemDvfsManager.createInstance(context, "COVER_BUS_BOOSTER", 19);
      sCoverBusBooster = createInstance3;
      if (createInstance3 != null
          && (supportedFrequency = createInstance3.getSupportedFrequency()) != null
          && supportedFrequency.length > 0) {
        sCoverBusBooster.setDvfsValue(supportedFrequency[0]);
      }
    }
    SemDvfsManager semDvfsManager3 = sCoverBusBooster;
    if (semDvfsManager3 != null) {
      try {
        semDvfsManager3.acquire(BOOSTING_TIMEOUT);
      } catch (Exception e3) {
        Log.e(TAG, "sCoverBusBooster.acquire is failed", e3);
      }
    }
  }

  public static void sendCoverInformationToAgent(Context context, String str, boolean z) {
    if (!Feature.getInstance(context).isNfcAuthEnabled()) {
      str = getValueFromSysFS("/sys/devices/w1_bus_master1/w1_master_check_sn", (String) null);
    }
    if (str != null) {
      Intent intent = new Intent("com.samsung.android.intent.action.COVER_ATTACHED");
      intent.setClassName(
          "com.sec.android.soagent", "com.sec.android.soagent.receiver.PhoneCoverReceiver");
      intent.putExtra("isBoot", z);
      intent.putExtra("serialNumber", str);
      context.sendBroadcastAsUser(
          intent, UserHandle.CURRENT, "com.samsung.android.permission.COVER");
      Log.d(TAG, "sendCoverInformationToAgent : broadcast !!");
    }
  }

  public static boolean isSupportWirelessCharge() {
    String valueFromSysFS = getValueFromSysFS("/sys/class/power_supply/wireless/present", "0");
    if (valueFromSysFS == null) {
      Log.d(TAG, "Feature for Wireless Charge is NOT existed");
      return false;
    }
    if ("0".equals(valueFromSysFS.trim())) {
      Log.d(TAG, "Wireless Charge is NOT Supported");
      return false;
    }
    Log.d(TAG, "Wireless Charge is Supported: Type " + valueFromSysFS);
    return true;
  }

  public static boolean fileWriteInt(String str, int i) {
    FileOutputStream fileOutputStream;
    String str2 = TAG;
    Log.d(str2, "fileWriteInt to " + str + ", " + i);
    if (i != 0 && i != 1) {
      Log.e(str2, "Invalid value : " + i);
      return false;
    }
    FileOutputStream fileOutputStream2 = null;
    try {
      try {
        fileOutputStream = new FileOutputStream(new File(str));
      } catch (IOException e) {
        e = e;
      }
      try {
        fileOutputStream.write(Integer.toString(i).getBytes());
        fileOutputStream.close();
        return true;
      } catch (IOException e2) {
        e = e2;
        fileOutputStream2 = fileOutputStream;
        e.printStackTrace();
        try {
          fileOutputStream2.close();
          return false;
        } catch (Exception e3) {
          e3.printStackTrace();
          return false;
        }
      }
    } catch (FileNotFoundException e4) {
      e4.printStackTrace();
      return false;
    }
  }
}
