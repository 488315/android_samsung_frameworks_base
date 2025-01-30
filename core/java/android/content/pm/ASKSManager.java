package android.content.pm;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import android.util.Xml;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ASKSManager {
  public static final int ASKS_UNKNOWN_BLOCKBYLIST = 1;
  public static final int ASKS_UNKNOWN_BLOCKBYPEM = 2;
  public static final int ASKS_UNKNOWN_BLOCKBYURL = 3;
  public static final int ASKS_UNKNOWN_BLOCKED_BYRAMPART = 127;
  public static final int ASKS_UNKNOWN_BLOCK_DETAIL_0 = 120;
  public static final int ASKS_UNKNOWN_BLOCK_DETAIL_1 = 121;
  public static final int ASKS_UNKNOWN_BLOCK_DETAIL_2 = 122;
  public static final int ASKS_UNKNOWN_BLOCK_DETAIL_3 = 123;
  public static final int ASKS_UNKNOWN_BLOCK_DETAIL_4 = 124;
  public static final int ASKS_UNKNOWN_BLOCK_DETAIL_GLOBAL_1 = 150;
  public static final int ASKS_UNKNOWN_BLOCK_DETAIL_GLOBAL_2 = 151;
  public static final int ASKS_UNKNOWN_DANGEROUSBLOCK = 102;
  public static final int ASKS_UNKNOWN_DANGEROUSBLOCKBYPEM = 103;
  public static final int ASKS_UNKNOWN_DANGEROUSWARNING = 101;
  public static final int ASKS_UNKNOWN_EXCEPT = 0;
  public static final int ASKS_UNKNOWN_EXECUTE_ALLOW = 126;
  public static final int ASKS_UNKNOWN_EXECUTE_BLOCK = 125;
  public static final int ASKS_UNKNOWN_NO_TARGET = 129;
  public static final int ASKS_UNKNOWN_TARGET = 128;
  public static final int ASKS_UNKNOWN_TARGET_NO_POPUP = 130;
  public static final int ASKS_UNKNOWN_WARNING = 100;
  public static final int ASKS_UNKNOWN_WARNING_DETAIL_0 = 110;
  public static final int ASKS_UNKNOWN_WARNING_DETAIL_1 = 111;
  public static final int ASKS_UNKNOWN_WARNING_DETAIL_2 = 112;
  public static final int ASKS_UNKNOWN_WARNING_DETAIL_3 = 113;
  public static final int ASKS_UNKNOWN_WARNING_DETAIL_4 = 114;
  public static final int ASKS_UNKNOWN_WARNING_DETAIL_GLOBAL_1 = 140;
  public static final int ASKS_UNKNOWN_WARNING_DETAIL_GLOBAL_2 = 141;
  private static final String TAG = "ASKSManager";
  public static final String TYPE_DENY = "DENY";
  public static final String TYPE_REVOKE = "REVOKE";
  static volatile IASKSManager sASKSManager;
  private static boolean hasBlockedPolicy = true;
  private static boolean isExactlyTargetDevice = false;
  private static HashMap<String, String> mASKSRestrictedPackages = new HashMap<>();
  private static HashMap<Integer, String> mASKSPidMap = new HashMap<>();
  private static ArrayList<String> mIMEIList = new ArrayList<>();

  public static synchronized IASKSManager getASKSManager() {
    synchronized (ASKSManager.class) {
      if (sASKSManager != null) {
        return sASKSManager;
      }
      IBinder b = ServiceManager.getService("asks");
      Slog.m119v(TAG, "default service binder = " + b);
      sASKSManager = IASKSManager.Stub.asInterface(b);
      Slog.m119v(TAG, "default service = " + sASKSManager);
      return sASKSManager;
    }
  }

  public static boolean isRestrictedTarget(String packageName, String type) {
    boolean isTarget = false;
    if (packageName == null || type == null) {
      return false;
    }
    synchronized (mASKSRestrictedPackages) {
      if (mASKSRestrictedPackages.containsKey(packageName)
          && type != null
          && type.equals(mASKSRestrictedPackages.get(packageName))) {
        isTarget = true;
      }
    }
    return isTarget;
  }

  public static void updateRestrictedTargetPackages(HashMap<String, String> updateMap) {
    synchronized (mASKSRestrictedPackages) {
      mASKSRestrictedPackages.clear();
      mASKSRestrictedPackages.putAll(updateMap);
    }
  }

  public static void addPackageWithPid(int pid, String packageName) {
    synchronized (mASKSPidMap) {
      if (packageName != null) {
        mASKSPidMap.put(Integer.valueOf(pid), packageName);
      }
    }
  }

  public static void removePackageWithPid(int pid) {
    synchronized (mASKSPidMap) {
      if (mASKSPidMap.containsKey(Integer.valueOf(pid))) {
        mASKSPidMap.remove(Integer.valueOf(pid));
      }
    }
  }

  public static String getPackageNameFromPid(int pid) {
    String packageName;
    synchronized (mASKSPidMap) {
      packageName = mASKSPidMap.get(Integer.valueOf(pid));
    }
    return packageName;
  }

  public static String getASKSerrorDetail(int returnCode) {
    switch (returnCode) {
      case PackageManager.INSTALL_FAILED_BLOCKED_CROSS_DOWN /* -3006 */:
        return "INSTALL_FAILED_BLOCKED_CROSS_DOWN";
      case PackageManager.INSTALL_FAILED_ADP_VERSION_LOCKED /* -3005 */:
        return "INSTALL_FAILED_ADP_VERSION_LOCKED";
      case PackageManager.INSTALL_FAILED_AUTH_ASKSTOKEN /* -3004 */:
      case PackageManager.INSTALL_FAILED_MISSING_ASKSTOKEN /* -3003 */:
      case PackageManager.INSTALL_FAILED_MISSING_CERTIFICATION /* -3002 */:
        return "INSTALL_FAILED_MISSING_CERTIFICATION";
      case PackageManager.INSTALL_FAILED_REJECTED_BY_BUILDTYPE /* -3001 */:
        return "INSTALL_FAILED_REJECTED_BY_BUILDTYPE";
      case PackageManager.INSTALL_FAILED_REJECTED_BY_DATE /* -3000 */:
        return "INSTALL_FAILED_REJECTED_BY_DATE";
      default:
        return "Unknown Reason";
    }
  }

  public static boolean hasBlockPolicy() {
    return hasBlockedPolicy;
  }

  public static boolean isBlockTarget(int uid, String packageName) {
    if (!isExactlyTargetDevice) {
      HashMap<String, ArrayList<String>> identMap = new HashMap<>();
      getASKSIDataFromXML(identMap);
      if (!identMap.isEmpty()) {
        if (sASKSManager != null && mIMEIList.isEmpty()) {
          try {
            List<String> imeis = sASKSManager.getIMEIList();
            if (!imeis.isEmpty()) {
              mIMEIList.addAll(imeis);
            }
          } catch (RemoteException e) {
          }
        }
        Iterator<String> it = mIMEIList.iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          String hemi = it.next();
          if (identMap.containsKey(hemi)) {
            Slog.m117i(TAG, "blocking target matched");
            isExactlyTargetDevice = true;
            break;
          }
        }
      } else {
        Slog.m117i(TAG, "identMap is empty");
      }
    }
    if (isExactlyTargetDevice) {
      if ((uid <= 10000
              || "com.samsung.android.messaging".equals(packageName)
              || "com.wsomacp".equals(packageName)
              || "com.samsung.android.dialer".equals(packageName))
          && !packageName.contains(Context.CAMERA_SERVICE)) {
        return false;
      }
      return true;
    }
    if (mIMEIList.isEmpty()) {
      return false;
    }
    File file = new File("/data/system/.aasa/AASApolicy/ASKSI.xml");
    if (!file.exists()) {
      return false;
    }
    Slog.m117i(TAG, "This is not target device");
    file.delete();
    return false;
  }

  private static void getASKSIDataFromXML(HashMap<String, ArrayList<String>> store) {
    ArrayList<String> features = new ArrayList<>();
    features.add("IDENT");
    features.add("DUMMY");
    File file = new File("/data/system/.aasa/AASApolicy/ASKSI.xml");
    if (file.getParentFile() != null && !file.getParentFile().exists()) {
      file.getParentFile().mkdir();
      file.getParentFile().setReadable(true, false);
    }
    if (!file.exists()) {
      hasBlockedPolicy = false;
      return;
    }
    try {
      FileReader fileReader = new FileReader(file);
      try {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(fileReader);
        String keyName = "";
        ArrayList<String> values = null;
        for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
          String name = parser.getName();
          switch (eventType) {
            case 2:
              if (features.get(0).equals(name)) {
                if (parser.getAttributeValue(0) != null) {
                  keyName = parser.getAttributeValue(0);
                }
                values = new ArrayList<>();
                break;
              } else if (features.contains(name)
                  && parser.getAttributeValue(0) != null
                  && values != null) {
                values.add(parser.getAttributeValue(0));
                break;
              }
              break;
            case 3:
              if (features.get(0).equals(name) && store != null) {
                store.put(keyName, values);
                break;
              }
              break;
          }
        }
        fileReader.close();
      } catch (IOException e) {
        try {
          fileReader.close();
        } catch (IOException e2) {
        }
        e.printStackTrace();
      } catch (XmlPullParserException e3) {
        try {
          fileReader.close();
        } catch (IOException e4) {
        }
        e3.printStackTrace();
      }
    } catch (FileNotFoundException e5) {
      e5.printStackTrace();
    }
  }
}
