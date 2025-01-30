package com.android.server.enterprise.nap;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.pm.PackageManagerService;
import com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class NetworkAnalyticsDataDelivery {
  public static final boolean DBG = NetworkAnalyticsService.DBG;
  public static Set appset;
  public static NetworkAnalyticsDataDelivery mInstance;
  public static PackageManagerService.IPackageManagerImpl pmsImp;
  public List dataEntry;
  public long startTimer;
  public List registeredDataRecipients = null;
  public Object syncObject = new Object();
  public HandlerThread mHandlerThread = null;
  public DataDeliveryHandler mHandler = null;

  static {
    getPackageManagerImpl();
    appset = Collections.synchronizedSet(new HashSet());
  }

  public class AppInfoSet {
    public String hash;
    public String packageName;
    public String processName;
    public String truncatedProcessName;
    public int uid;
    public int userId;

    public AppInfoSet(int i, String str, String str2, String str3, String str4, int i2) {
      this.uid = i;
      this.packageName = str;
      this.processName = str2;
      this.truncatedProcessName = str3;
      this.hash = str4;
      this.userId = i2;
    }
  }

  public NetworkAnalyticsDataDelivery() {
    this.dataEntry = null;
    this.startTimer = 0L;
    this.dataEntry = new ArrayList();
    this.startTimer = System.currentTimeMillis();
  }

  public static NetworkAnalyticsDataDelivery getInstance() {
    if (mInstance == null) {
      mInstance = new NetworkAnalyticsDataDelivery();
    }
    return mInstance;
  }

  public void addNAPDataRecipient(DataDeliveryHelper dataDeliveryHelper) {
    if (!validateRecipientObject(dataDeliveryHelper) && DBG) {
      Log.d(
          "NetworkAnalytics:NetworkAnalyticsDataDelivery",
          "adding recipient failed for recipient: " + dataDeliveryHelper.getIdentifier());
    }
    String identifier = dataDeliveryHelper.getIdentifier();
    boolean z = DBG;
    if (z) {
      Log.d(
          "NetworkAnalytics:NetworkAnalyticsDataDelivery",
          "adding recipient for data collection:" + identifier);
    }
    synchronized (this.syncObject) {
      if (isDataRecipientPresent(
              NetworkAnalyticsService.getVendorNameFromTransformedName(identifier),
              NetworkAnalyticsService.getCidFromTransformedName(identifier))
          < 0) {
        getRecipientList().add(dataDeliveryHelper);
        if (z) {
          Log.d(
              "NetworkAnalytics:NetworkAnalyticsDataDelivery",
              "added recipient for data collection:" + identifier);
        }
      }
    }
  }

  public void removeNAPDataRecipient(String str, int i) {
    synchronized (this.syncObject) {
      int isDataRecipientPresent = isDataRecipientPresent(str, i);
      if (isDataRecipientPresent < 0) {
        return;
      }
      if (DBG) {
        Log.d(
            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
            "removeNAPDataRecipient: removing recipient for data collection:"
                + NetworkAnalyticsService.getTransformedVendorName(str, i));
      }
      getRecipientList().remove(isDataRecipientPresent);
    }
  }

  public void removeDataRecipientsForPackage(String str, int i) {
    synchronized (this.syncObject) {
      Iterator it = getRecipientList().iterator();
      while (it.hasNext()) {
        DataDeliveryHelper dataDeliveryHelper = (DataDeliveryHelper) it.next();
        if (dataDeliveryHelper.getProfile().getPackageName().equals(str)
            && dataDeliveryHelper.getProfile().getUserId() == i) {
          Log.d(
              "NetworkAnalytics:NetworkAnalyticsDataDelivery",
              "removeDataRecipientsForPackage: removing recipient for package:"
                  + str
                  + i
                  + dataDeliveryHelper.getProfile().getProfileName());
          it.remove();
        }
      }
    }
  }

  public int deliverData(List list) {
    if (list == null || list.size() <= 0) {
      return -1;
    }
    synchronized (this.syncObject) {
      List<DataDeliveryHelper> recipientList = getRecipientList();
      if (recipientList != null && recipientList.size() > 0) {
        for (DataDeliveryHelper dataDeliveryHelper : recipientList) {
          try {
            INetworkAnalyticsService serviceBinder = dataDeliveryHelper.getServiceBinder();
            String profileName = dataDeliveryHelper.getProfile().getProfileName();
            if (serviceBinder == null) {
              if (DBG) {
                Log.d(
                    "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                    "deliverData: service connection is null for entry:"
                        + dataDeliveryHelper.getIdentifier());
              }
            } else {
              List augmentedData = getAugmentedData(dataDeliveryHelper, list);
              if (augmentedData != null && augmentedData.size() > 0) {
                serviceBinder.onDataAvailable(profileName, augmentedData);
              }
            }
          } catch (RemoteException e) {
            Log.e(
                "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                "deliverData: RemoteException ",
                e);
          } catch (Exception e2) {
            Log.e("NetworkAnalytics:NetworkAnalyticsDataDelivery", "deliverData: Exception ", e2);
          }
        }
        return 0;
      }
      if (DBG) {
        Log.d(
            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
            "deliverData: No data delivery herlper entries.");
      }
      return -1;
    }
  }

  public int accumulateData(String str) {
    sendMessageToHandler(1, 0, 0, str);
    return 0;
  }

  public void initializeHandlerThread() {
    HandlerThread handlerThread = new HandlerThread("DataDeliveryHandler", 10);
    this.mHandlerThread = handlerThread;
    handlerThread.start();
    this.mHandler = new DataDeliveryHandler(this.mHandlerThread.getLooper());
  }

  public static String getHashFromCache(int i, String str) {
    synchronized (appset) {
      for (AppInfoSet appInfoSet : appset) {
        if (appInfoSet.uid == i
            && (appInfoSet.processName.equalsIgnoreCase(str)
                || appInfoSet.truncatedProcessName.equalsIgnoreCase(str))) {
          return appInfoSet.hash;
        }
      }
      return null;
    }
  }

  public static String getHashFromCacheBasedOnUid(int i) {
    synchronized (appset) {
      for (AppInfoSet appInfoSet : appset) {
        if (appInfoSet.uid == i) {
          return appInfoSet.hash;
        }
      }
      return null;
    }
  }

  public static void insertHashIntoCache(AppInfoSet appInfoSet) {
    boolean z = DBG;
    if (z) {
      Log.d("NetworkAnalytics:NetworkAnalyticsDataDelivery", "insertHashIntoCache Called");
    }
    synchronized (appset) {
      if (z) {
        Log.d(
            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
            "DataDelivery hash cache insertion uid:"
                + appInfoSet.uid
                + " pacName:"
                + appInfoSet.packageName
                + " procName:"
                + appInfoSet.processName
                + " trunProcName:"
                + appInfoSet.truncatedProcessName
                + " hash:"
                + appInfoSet.hash);
      }
      appset.add(appInfoSet);
    }
  }

  public static void updateHashCache(int i, String str) {
    if (DBG) {
      Log.d("NetworkAnalytics:NetworkAnalyticsDataDelivery", "updateHashCache Called");
    }
    synchronized (appset) {
      Iterator it = appset.iterator();
      while (it.hasNext()) {
        AppInfoSet appInfoSet = (AppInfoSet) it.next();
        if (appInfoSet.uid == i && appInfoSet.packageName.equalsIgnoreCase(str)) {
          if (DBG) {
            Log.d(
                "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                "DataDelivery hash cache deletion uid:"
                    + appInfoSet.uid
                    + " pacName:"
                    + appInfoSet.packageName
                    + " procName:"
                    + appInfoSet.processName
                    + " trunProcName:"
                    + appInfoSet.truncatedProcessName
                    + " hash:"
                    + appInfoSet.hash);
          }
          it.remove();
        }
      }
    }
  }

  public static void updateHashCacheForUser(int i) {
    if (DBG) {
      Log.d("NetworkAnalytics:NetworkAnalyticsDataDelivery", "updateHashCacheForUser Called");
    }
    synchronized (appset) {
      Iterator it = appset.iterator();
      while (it.hasNext()) {
        AppInfoSet appInfoSet = (AppInfoSet) it.next();
        if (appInfoSet.userId == i) {
          if (DBG) {
            Log.d(
                "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                "DataDelivery hash cache user deletion uid:"
                    + appInfoSet.uid
                    + " pacName:"
                    + appInfoSet.packageName
                    + " procName:"
                    + appInfoSet.processName
                    + " trunProcName:"
                    + appInfoSet.truncatedProcessName
                    + " hash:"
                    + appInfoSet.hash);
          }
          it.remove();
        }
      }
    }
  }

  public static void clearHashCacheEntire() {
    synchronized (appset) {
      boolean z = DBG;
      if (z) {
        Log.d(
            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
            "clearHashCacheEntire Called : cache size: " + appset.size());
      }
      appset.clear();
      if (z) {
        Log.d(
            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
            "DataDelivery hash cache entire deletion : cache size: " + appset.size());
      }
    }
  }

  public static String getPackageHash(int i, String str) {
    String hashFromCache;
    try {
      hashFromCache = getHashFromCache(i, str);
    } catch (Exception e) {
      Log.e("NetworkAnalytics:NetworkAnalyticsDataDelivery", "getPackageHash: Exception", e);
    }
    if (hashFromCache != null) {
      return hashFromCache;
    }
    String checkIfProcessIsDaemon = checkIfProcessIsDaemon(str);
    if (checkIfProcessIsDaemon != null
        && !checkIfProcessIsDaemon.isEmpty()
        && !checkIfProcessIsDaemon.equals("null")) {
      File file = new File(checkIfProcessIsDaemon);
      if (!file.exists()) {
        return null;
      }
      String hash = getHash(file);
      if (hash != null) {
        return hash;
      }
    } else {
      String checkSingleUidAndCalculateHash = checkSingleUidAndCalculateHash(i, str);
      if (checkSingleUidAndCalculateHash != null) {
        return checkSingleUidAndCalculateHash;
      }
      String fileLocationFromProcessNameAndCalculateHash =
          getFileLocationFromProcessNameAndCalculateHash(i, str);
      if (fileLocationFromProcessNameAndCalculateHash != null) {
        return fileLocationFromProcessNameAndCalculateHash;
      }
      String packageNameFromPathAndCalculateHash = getPackageNameFromPathAndCalculateHash(i, str);
      if (packageNameFromPathAndCalculateHash != null) {
        return packageNameFromPathAndCalculateHash;
      }
    }
    return null;
  }

  public static String checkIfProcessIsDaemon(String str) {
    StringBuffer stringBuffer = new StringBuffer();
    try {
      Process exec = Runtime.getRuntime().exec("which " + str);
      exec.waitFor();
      BufferedReader bufferedReader =
          new BufferedReader(new InputStreamReader(exec.getInputStream()));
      while (true) {
        String readLine = bufferedReader.readLine();
        if (readLine == null) {
          break;
        }
        stringBuffer.append(readLine);
      }
      bufferedReader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return stringBuffer.toString();
  }

  public static String checkSingleUidAndCalculateHash(int i, String str) {
    PackageManagerService.IPackageManagerImpl iPackageManagerImpl;
    AppInfoSet appInfoSet;
    try {
      iPackageManagerImpl = pmsImp;
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (iPackageManagerImpl == null) {
      return null;
    }
    String[] packagesForUid = iPackageManagerImpl.getPackagesForUid(i);
    if (packagesForUid == null) {
      Log.d(
          "NetworkAnalytics:NetworkAnalyticsDataDelivery",
          "unable to find the packages for uid: " + i + " for processName: " + str);
      return null;
    }
    if (packagesForUid.length == 1) {
      String hashFromCacheBasedOnUid = getHashFromCacheBasedOnUid(i);
      if (hashFromCacheBasedOnUid != null) {
        return hashFromCacheBasedOnUid;
      }
      String str2 = packagesForUid[0];
      ApplicationInfo applicationInfo =
          pmsImp.getApplicationInfo(str2, 0L, UserHandle.getUserId(i));
      if (applicationInfo != null && applicationInfo.sourceDir != null) {
        File file = new File(applicationInfo.sourceDir);
        if (!file.exists()) {
          Log.d(
              "NetworkAnalytics:NetworkAnalyticsDataDelivery",
              "unable to find the file location for the process:"
                  + str
                  + "for package "
                  + str2
                  + "for uid "
                  + i);
          return null;
        }
        String hash = getHash(file);
        if (hash != null) {
          String str3 = applicationInfo.processName;
          if (str3 != null) {
            if (str3.length() > 15) {
              String str4 = applicationInfo.processName;
              appInfoSet =
                  new AppInfoSet(
                      i,
                      str2,
                      str4,
                      str4.substring(str4.length() - 15),
                      hash,
                      UserHandle.getUserId(i));
            } else {
              String str5 = applicationInfo.processName;
              appInfoSet = new AppInfoSet(i, str2, str5, str5, hash, UserHandle.getUserId(i));
            }
            insertHashIntoCache(appInfoSet);
          }
          return hash;
        }
      }
    }
    return null;
  }

  public static String getFileLocationFromProcessNameAndCalculateHash(int i, String str) {
    PackageManagerService.IPackageManagerImpl iPackageManagerImpl;
    String[] packagesForUid;
    ActivityInfo[] activityInfoArr;
    String str2;
    String compareProcessNamesAndCalculateHash;
    ActivityInfo[] activityInfoArr2;
    String str3;
    String compareProcessNamesAndCalculateHash2;
    ProviderInfo[] providerInfoArr;
    String str4;
    String compareProcessNamesAndCalculateHash3;
    ServiceInfo[] serviceInfoArr;
    String str5;
    String compareProcessNamesAndCalculateHash4;
    String str6;
    String compareProcessNamesAndCalculateHash5;
    try {
      iPackageManagerImpl = pmsImp;
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (iPackageManagerImpl == null
        || (packagesForUid = iPackageManagerImpl.getPackagesForUid(i)) == null) {
      return null;
    }
    for (String str7 : packagesForUid) {
      ApplicationInfo applicationInfo =
          pmsImp.getApplicationInfo(str7, 0L, UserHandle.getUserId(i));
      if (applicationInfo != null
          && (str6 = applicationInfo.processName) != null
          && str != null
          && (compareProcessNamesAndCalculateHash5 =
                  compareProcessNamesAndCalculateHash(i, str, str7, str6, applicationInfo))
              != null) {
        return compareProcessNamesAndCalculateHash5;
      }
      PackageInfo packageInfo = pmsImp.getPackageInfo(str7, 4L, UserHandle.getUserId(i));
      if (packageInfo != null && (serviceInfoArr = packageInfo.services) != null) {
        for (ServiceInfo serviceInfo : serviceInfoArr) {
          if (serviceInfo != null
              && (str5 = serviceInfo.processName) != null
              && str != null
              && applicationInfo != null
              && (compareProcessNamesAndCalculateHash4 =
                      compareProcessNamesAndCalculateHash(i, str, str7, str5, applicationInfo))
                  != null) {
            return compareProcessNamesAndCalculateHash4;
          }
        }
      }
      PackageInfo packageInfo2 = pmsImp.getPackageInfo(str7, 8L, UserHandle.getUserId(i));
      if (packageInfo2 != null && (providerInfoArr = packageInfo2.providers) != null) {
        for (ProviderInfo providerInfo : providerInfoArr) {
          if (providerInfo != null
              && (str4 = providerInfo.processName) != null
              && str != null
              && applicationInfo != null
              && (compareProcessNamesAndCalculateHash3 =
                      compareProcessNamesAndCalculateHash(i, str, str7, str4, applicationInfo))
                  != null) {
            return compareProcessNamesAndCalculateHash3;
          }
        }
      }
      PackageInfo packageInfo3 = pmsImp.getPackageInfo(str7, 2L, UserHandle.getUserId(i));
      if (packageInfo3 != null && (activityInfoArr2 = packageInfo3.receivers) != null) {
        for (ActivityInfo activityInfo : activityInfoArr2) {
          if (activityInfo != null
              && (str3 = activityInfo.processName) != null
              && str != null
              && applicationInfo != null
              && (compareProcessNamesAndCalculateHash2 =
                      compareProcessNamesAndCalculateHash(i, str, str7, str3, applicationInfo))
                  != null) {
            return compareProcessNamesAndCalculateHash2;
          }
        }
      }
      PackageInfo packageInfo4 = pmsImp.getPackageInfo(str7, 1L, UserHandle.getUserId(i));
      if (packageInfo4 != null && (activityInfoArr = packageInfo4.activities) != null) {
        for (ActivityInfo activityInfo2 : activityInfoArr) {
          if (activityInfo2 != null
              && (str2 = activityInfo2.processName) != null
              && str != null
              && applicationInfo != null
              && (compareProcessNamesAndCalculateHash =
                      compareProcessNamesAndCalculateHash(i, str, str7, str2, applicationInfo))
                  != null) {
            return compareProcessNamesAndCalculateHash;
          }
        }
      }
    }
    return null;
  }

  public static String getPackageNameFromPathAndCalculateHash(int i, String str) {
    String str2;
    String[] packagesForUid;
    ApplicationInfo applicationInfo;
    try {
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (pmsImp == null) {
      return null;
    }
    String[] split = str.split("/");
    if (split.length >= 4) {
      if (split[2].equals("data")) {
        str2 = split[3];
      } else {
        str2 =
            (split.length >= 5
                    && split[2].equals("user")
                    && Integer.toString(UserHandle.getUserId(i)).equals(split[3]))
                ? split[4]
                : null;
      }
      if (str2 == null || (packagesForUid = pmsImp.getPackagesForUid(i)) == null) {
        return null;
      }
      for (String str3 : packagesForUid) {
        if (str3.equalsIgnoreCase(str2)
            && (applicationInfo = pmsImp.getApplicationInfo(str3, 0L, UserHandle.getUserId(i)))
                != null
            && applicationInfo.sourceDir != null) {
          File file = new File(applicationInfo.sourceDir);
          if (!file.exists()) {
            Log.d(
                "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                "unable to find the file location for the deamon path:" + str + " for uid " + i);
            return null;
          }
          String hash = getHash(file);
          if (hash != null) {
            return hash;
          }
        }
      }
    }
    return null;
  }

  public static String compareProcessNamesAndCalculateHash(
      int i, String str, String str2, String str3, ApplicationInfo applicationInfo) {
    if (str != null && str2 != null && str3 != null && applicationInfo != null) {
      try {
        if (str3.length() > 15) {
          if ((str3.substring(str3.length() - 15).equalsIgnoreCase(str)
                  || str3.equalsIgnoreCase(str))
              && applicationInfo.sourceDir != null) {
            File file = new File(applicationInfo.sourceDir);
            if (!file.exists()) {
              Log.d(
                  "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                  "unable to find the file location for the process:"
                      + str
                      + "for package "
                      + str2
                      + "for uid "
                      + i);
              return null;
            }
            String hash = getHash(file);
            if (hash != null) {
              insertHashIntoCache(
                  new AppInfoSet(
                      i,
                      str2,
                      str3,
                      str3.substring(str3.length() - 15),
                      hash,
                      UserHandle.getUserId(i)));
              return hash;
            }
          }
        } else if (str3.equalsIgnoreCase(str) && applicationInfo.sourceDir != null) {
          File file2 = new File(applicationInfo.sourceDir);
          if (!file2.exists()) {
            Log.d(
                "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                "unable to find the file location for the process:"
                    + str
                    + "for package "
                    + str2
                    + "for uid "
                    + i);
            return null;
          }
          String hash2 = getHash(file2);
          if (hash2 != null) {
            insertHashIntoCache(
                new AppInfoSet(i, str2, str3, str3, hash2, UserHandle.getUserId(i)));
            return hash2;
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /* JADX WARN: Not initialized variable reg: 4, insn: 0x0092: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:66:0x0092 */
  /* JADX WARN: Removed duplicated region for block: B:23:0x008b A[RETURN] */
  /* JADX WARN: Removed duplicated region for block: B:25:0x008c  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static String getHash(File file) {
    FileInputStream fileInputStream;
    StringBuffer stringBuffer;
    FileInputStream fileInputStream2;
    FileInputStream fileInputStream3 = null;
    try {
    } catch (Throwable th) {
      th = th;
      fileInputStream3 = fileInputStream;
    }
    try {
      try {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        fileInputStream2 = new FileInputStream(file);
        try {
          byte[] bArr = new byte[1024];
          while (true) {
            int read = fileInputStream2.read(bArr);
            if (read == -1) {
              break;
            }
            messageDigest.update(bArr, 0, read);
          }
          byte[] digest = messageDigest.digest();
          stringBuffer = new StringBuffer();
          for (byte b : digest) {
            try {
              stringBuffer.append(String.format("%02X", Integer.valueOf(b & 255)));
            } catch (FileNotFoundException e) {
              e = e;
              Log.e(
                  "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                  "getHash: FileNotFoundException",
                  e);
              if (fileInputStream2 != null) {
                fileInputStream2.close();
              }
              if (stringBuffer != null) {}
            } catch (IOException e2) {
              e = e2;
              Log.e("NetworkAnalytics:NetworkAnalyticsDataDelivery", "getHash: IOException", e);
              if (fileInputStream2 != null) {
                fileInputStream2.close();
              }
              if (stringBuffer != null) {}
            } catch (NoSuchAlgorithmException e3) {
              e = e3;
              Log.e(
                  "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                  "getHash: NoSuchAlgorithmException",
                  e);
              if (fileInputStream2 != null) {
                fileInputStream2.close();
              }
              if (stringBuffer != null) {}
            }
          }
          fileInputStream2.close();
        } catch (FileNotFoundException e4) {
          e = e4;
          stringBuffer = null;
        } catch (IOException e5) {
          e = e5;
          stringBuffer = null;
        } catch (NoSuchAlgorithmException e6) {
          e = e6;
          stringBuffer = null;
        }
      } catch (IOException e7) {
        Log.e(
            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
            "close FileInputStream: IOException",
            e7);
      }
    } catch (FileNotFoundException e8) {
      e = e8;
      stringBuffer = null;
      fileInputStream2 = null;
    } catch (IOException e9) {
      e = e9;
      stringBuffer = null;
      fileInputStream2 = null;
    } catch (NoSuchAlgorithmException e10) {
      e = e10;
      stringBuffer = null;
      fileInputStream2 = null;
    } catch (Throwable th2) {
      th = th2;
      if (fileInputStream3 != null) {
        try {
          fileInputStream3.close();
        } catch (IOException e11) {
          Log.e(
              "NetworkAnalytics:NetworkAnalyticsDataDelivery",
              "close FileInputStream: IOException",
              e11);
        }
      }
      throw th;
    }
    if (stringBuffer != null) {
      return null;
    }
    return stringBuffer.toString();
  }

  public static PackageManagerService.IPackageManagerImpl getPackageManagerImpl() {
    if (pmsImp == null) {
      pmsImp = (PackageManagerService.IPackageManagerImpl) ServiceManager.getService("package");
    }
    return pmsImp;
  }

  public final void sendMessageToHandler(int i, int i2, int i3, Object obj) {
    DataDeliveryHandler dataDeliveryHandler = this.mHandler;
    if (dataDeliveryHandler != null) {
      this.mHandler.sendMessage(Message.obtain(dataDeliveryHandler, i, i2, i3, obj));
    }
  }

  public final List getAugmentedData(DataDeliveryHelper dataDeliveryHelper, List list) {
    ArrayList arrayList = null;
    if (dataDeliveryHelper == null || list == null) {
      if (DBG) {
        Log.d(
            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
            "getAugmentedData: Helper or data is null.");
      }
      return null;
    }
    if (list.size() > 0) {
      arrayList = new ArrayList();
      Iterator it = list.iterator();
      while (it.hasNext()) {
        String processData = dataDeliveryHelper.processData((String) it.next());
        if (processData != null) {
          arrayList.add(processData);
        }
      }
    }
    return arrayList;
  }

  public final boolean validateRecipientObject(DataDeliveryHelper dataDeliveryHelper) {
    return (dataDeliveryHelper == null
            || dataDeliveryHelper.getProfile() == null
            || dataDeliveryHelper.getServiceConnection() == null)
        ? false
        : true;
  }

  public final int isDataRecipientPresent(String str, int i) {
    List recipientList = getRecipientList();
    for (int i2 = 0; i2 < recipientList.size(); i2++) {
      if (((DataDeliveryHelper) recipientList.get(i2))
          .getIdentifier()
          .equals(NetworkAnalyticsService.getTransformedVendorName(str, i))) {
        if (!DBG) {
          return i2;
        }
        Log.d(
            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
            "isDataRecipientPresent: found recipient:"
                + NetworkAnalyticsService.getTransformedVendorName(str, i));
        return i2;
      }
    }
    return -1;
  }

  public List getRecipientList() {
    List list;
    synchronized (this.syncObject) {
      if (this.registeredDataRecipients == null) {
        this.registeredDataRecipients = new ArrayList();
      }
      list = this.registeredDataRecipients;
    }
    return list;
  }

  public class DataDeliveryHandler extends Handler {
    public DataDeliveryHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      String str;
      if (message.what == 1 && (str = (String) message.obj) != null) {
        NetworkAnalyticsDataDelivery.this.dataEntry.add(str);
        if (NetworkAnalyticsDataDelivery.this.dataEntry.size() >= 50
            || System.currentTimeMillis() - NetworkAnalyticsDataDelivery.this.startTimer > 10000) {
          NetworkAnalyticsDataDelivery networkAnalyticsDataDelivery =
              NetworkAnalyticsDataDelivery.this;
          networkAnalyticsDataDelivery.deliverData(networkAnalyticsDataDelivery.dataEntry);
          NetworkAnalyticsDataDelivery.this.dataEntry.clear();
          NetworkAnalyticsDataDelivery.this.startTimer = System.currentTimeMillis();
        }
      }
    }
  }
}
