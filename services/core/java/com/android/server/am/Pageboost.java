package com.android.server.am;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.INetd;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SemHqmManager;
import android.os.SystemProperties;
import android.os.IInstalld;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.PinnerService;
import com.android.server.ServiceThread;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.game.SemGameManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/* loaded from: classes.dex */
public abstract class Pageboost {
  public static final String TAG = "Pageboost";
  public static final boolean PAGEBOOST_IO_PREFETCH_ENABLED =
      "true".equals(SystemProperties.get("ro.config.pageboost.io_prefetch.enabled", "true"));
  public static final boolean PAGEBOOST_IGNORE_DRAM_SPECIFICATION =
      "true".equals(SystemProperties.get("ro.config.pageboost.ignore_dram_spec", "false"));
  public static final boolean PAGEBOOST_ACTIVE_LAUNCH_ENABLED =
      "true".equals(SystemProperties.get("ro.config.pageboost.active_launch.enabled", "true"));
  public static final int PAGEBOOST_ACTIVE_LAUNCH_TIMEOUT =
      SystemProperties.getInt("ro.config.pageboost.active_launch.timeout", 100);
  public static final boolean PAGEBOOST_MINIMIZE =
      "true".equals(SystemProperties.get("ro.config.pageboost.vramdisk.minimize", "false"));
  public static final boolean PAGEBOOST_VRAMDISK_WITHOUT_PREDICT =
      "true".equals(SystemProperties.get("ro.config.pageboost.vramdisk.without_predict", "true"));
  public static final boolean BOOTFILE_ENABLED =
      "true".equals(SystemProperties.get("ro.config.pageboost.vramdisk.bootfile.enabled", "true"));
  public static boolean PAGEBOOST_KERNEL_ENABLED = true;
  public static boolean PAGEBOOST_DAEMON_ENABLED = true;
  public static Context mContext = null;
  public static ActivityManagerService mActivityManagerService = null;
  public static ServiceThread sHandlerThread = null;
  public static PageboostHandler sHandler = null;
  public static PageboostPredictor mPredictor = null;
  public static PageboostAppList mGlobalAppLRU = null;
  public static BroadcastReceiver mReceiver = null;
  public static BroadcastReceiver mPackageReceiver = null;
  public static PageboostAppDBHelper mAppDBHelper = null;
  public static PageboostFileDBHelper mFileDBHelper = null;
  public static SQLiteDatabase mAppDB = null;
  public static SQLiteDatabase mFileDB = null;
  public static FileMapList mSystemServerFileMap = null;
  public static FileMapList mZygote64FileMap = null;
  public static FileMapList mZygoteFileMap = null;
  public static VramdiskMlockManager mBootFileManager = null;
  public static boolean user_unlock_done = false;
  public static boolean munlock_firstapp = false;

  public abstract class MemReclaimer {
    public static boolean reclaimMem(PageboostAppInfo pageboostAppInfo) {
      return false;
    }
  }

  public interface PreDo {
    boolean activeLaunch(PageboostAppInfo pageboostAppInfo);

    boolean execute(PageboostAppInfo pageboostAppInfo);
  }

  static {
    staticInitialize();
  }

  public static void setMlockConfiguration(long j) {
    munlock_firstapp = j <= 3221225472L;
    Slog.i(TAG, "dram size : " + j + " APP_LAUNCH_MUNLOCK_RAMSIZE: 3221225472");
  }

  public static void staticInitialize() {
    try {
      Slog.i(TAG, "Static Initialization of Pageboost");
      ServiceThread serviceThread = new ServiceThread(Pageboost.class.getSimpleName(), 10, true);
      sHandlerThread = serviceThread;
      serviceThread.start();
      sHandler = new PageboostHandler(sHandlerThread.getLooper());
      mPredictor = new PageboostPredictor();
      Vramdisk.setConfiguration(Process.getTotalMemory());
      setMlockConfiguration(Process.getTotalMemory());
      mReceiver =
          new BroadcastReceiver() { // from class: com.android.server.am.Pageboost.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
              String str;
              try {
                String action = intent.getAction();
                if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                  Slog.i(Pageboost.TAG, "received ACTION_USER_UNLOCKED");
                  if (Pageboost.user_unlock_done) {
                    return;
                  }
                  Slog.i(Pageboost.TAG, "init mGlobalAppLRU");
                  Pageboost.user_unlock_done = true;
                  Pageboost.sendMessage(4, null, -1, -1, -1, 10000);
                  return;
                }
                if (Pageboost.mGlobalAppLRU == null) {
                  return;
                }
                if (!"com.sec.android.launcher.action.RUN_APP".equals(action)
                    && !"com.android.server.am.ACTION_PAGEBOOST".equals(action)) {
                  if ("com.android.server.am.ACTION_VRAMDISK_PREFETCH".equals(action)) {
                    Pageboost.prefetchPackage(
                        intent.getStringExtra("package"), intent.getStringExtra("reason"));
                    return;
                  }
                  if (!"android.intent.action.ACTION_SHUTDOWN".equals(action)
                      && !"android.intent.action.REBOOT".equals(action)) {
                    if ("com.sec.android.intent.action.HQM_UPDATE_REQ".equals(action)) {
                      Pageboost.sendMessage(13, null, -1, -1, -1, 0);
                      return;
                    }
                    return;
                  }
                  Pageboost.sendMessage(6, null, -1, -1, -1, 0);
                  return;
                }
                String stringExtra = intent.getStringExtra("package");
                Slog.i(Pageboost.TAG, "package " + stringExtra);
                if (stringExtra != null) {
                  return;
                }
                String stringExtra2 = intent.getStringExtra("apps");
                if (stringExtra2 == null) {
                  Slog.i(Pageboost.TAG, "apps " + stringExtra2);
                  return;
                }
                String[] split = stringExtra2.split(" ");
                PageboostAppList pageboostAppList = new PageboostAppList();
                for (int i = 0; i < split.length && (str = split[i]) != null; i++) {
                  String[] split2 = str.split("/", 2);
                  if (split2[0] != null) {
                    PageboostAppInfo pageboostApp =
                        Pageboost.mGlobalAppLRU != null
                            ? Pageboost.mGlobalAppLRU.getPageboostApp(split2[0])
                            : null;
                    if (pageboostApp != null) {
                      pageboostAppList.add(pageboostApp, false);
                    }
                  }
                }
                Pageboost.sendMessageWithObject(2, pageboostAppList, 0);
              } catch (Exception unused) {
                Slog.e(Pageboost.TAG, "failed to mReceiver by exception");
              }
            }
          };
      mPackageReceiver =
          new BroadcastReceiver() { // from class: com.android.server.am.Pageboost.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
              Uri data;
              try {
                if (Pageboost.mGlobalAppLRU == null) {
                  return;
                }
                String action = intent.getAction();
                if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                  Uri data2 = intent.getData();
                  if (data2 != null) {
                    Pageboost.sendMessageWithObject(7, data2.getEncodedSchemeSpecificPart(), 0);
                  }
                } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)
                    && (data = intent.getData()) != null) {
                  Pageboost.sendMessageWithObject(8, data.getEncodedSchemeSpecificPart(), 0);
                }
              } catch (Exception unused) {
                Slog.e(Pageboost.TAG, "failed to mPackageReceiver by exception");
              }
            }
          };
    } catch (Exception unused) {
      Slog.e(TAG, "failed to staticInitialize by exception");
    }
  }

  public static void checkPageboostKernelSupport() {
    try {
      if (new File("/proc/self/io_record_control").exists()) {
        Slog.i(TAG, "io_record_control file exists: kernel support = true");
      } else {
        PAGEBOOST_KERNEL_ENABLED = false;
        Slog.i(TAG, "io_record_control file does not exist: kernel support = false");
      }
    } catch (Exception unused) {
      Slog.e(TAG, "io_record_control file open failed");
    }
    Slog.i(TAG, "checkPageboostKernelSupport: " + Boolean.toString(PAGEBOOST_KERNEL_ENABLED));
  }

  public static void checkPageboostDaemonSupport() {
    PAGEBOOST_DAEMON_ENABLED =
        INetd.IF_FLAG_RUNNING.equals(SystemProperties.get("init.svc.pageboostd", ""));
    Slog.i(TAG, "checkPageboostDaemonSupport: " + Boolean.toString(PAGEBOOST_DAEMON_ENABLED));
  }

  public static boolean isPageboostMinimized() {
    return (!PAGEBOOST_MINIMIZE && PAGEBOOST_KERNEL_ENABLED && PAGEBOOST_DAEMON_ENABLED)
        ? false
        : true;
  }

  public static int getPidFromPackageName(String str, boolean z) {
    SparseArray sparseArray;
    int i;
    ActivityManagerService activityManagerService = mActivityManagerService;
    ActivityManagerService.boostPriorityForLockedSection();
    synchronized (activityManagerService) {
      try {
        sparseArray =
            (SparseArray)
                mActivityManagerService.mProcessList.getProcessNamesLOSP().getMap().get(str);
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
      if (sparseArray != null) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
          ProcessRecord processRecord = (ProcessRecord) sparseArray.valueAt(size);
          if (z) {
            if (processRecord != null
                && processRecord.getThread() != null
                && !"cch-empty".equals(processRecord.mState.getAdjType())) {
              i = processRecord.getPid();
              break;
            }
          } else {
            if (processRecord != null) {
              i = processRecord.getPid();
              break;
            }
          }
          ActivityManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      i = 0;
    }
    ActivityManagerService.resetPriorityAfterLockedSection();
    return i;
  }

  public static void initPageboost(Context context, ActivityManagerService activityManagerService) {
    try {
      mContext = context;
      mActivityManagerService = activityManagerService;
      if (!isPageboostMinimized()) {
        sendMessage(1, null, -1, -1, -1, 5000);
      }
      if (BOOTFILE_ENABLED) {
        sendMessage(23, null, -1, -1, -1, 0);
        sendMessage(24, null, -1, -1, -1, 600000);
      }
    } catch (Exception unused) {
      Slog.e(TAG, "failed to initPageboost by exception");
    }
  }

  public static FileMapList getFileMapFromCmd(String str) {
    int[] pidsForCommands = Process.getPidsForCommands(new String[] {str});
    if (pidsForCommands == null || pidsForCommands.length <= 0) {
      return null;
    }
    return new FileMapList(pidsForCommands[0]);
  }

  public static void deleteDB(String str) {
    File databasePath = mContext.getDatabasePath(str);
    if (databasePath == null || !databasePath.exists()) {
      return;
    }
    SQLiteDatabase.deleteDatabase(databasePath);
  }

  public static void openAppDB(boolean z) {
    if (z) {
      Slog.e(TAG, "delete app db and open");
      deleteDB("/data/misc/pageboost/pageboost_app_db.db");
    }
    PageboostAppDBHelper pageboostAppDBHelper = new PageboostAppDBHelper(mContext);
    mAppDBHelper = pageboostAppDBHelper;
    try {
      mAppDB = pageboostAppDBHelper.getWritableDatabase();
      Slog.e(TAG, "getWritableDatabase for appDB");
    } catch (SQLiteDatabaseCorruptException unused) {
      Slog.e(TAG, "SQLiteDatabaseCorruptException for appDB");
      mAppDB = mAppDBHelper.getWritableDatabase();
    } catch (SQLiteException e) {
      String str = TAG;
      Slog.e(str, "SQLiteException for appDB " + e.getMessage());
      if (e.getMessage().contains("malformed database")) {
        Slog.e(str, "delete app db and open");
        deleteDB("/data/misc/pageboost/pageboost_app_db.db");
        mAppDB = mAppDBHelper.getWritableDatabase();
      }
    } catch (Exception e2) {
      Slog.e(TAG, "Exception for appDB " + e2.getMessage());
    }
  }

  public static boolean openFileDB() {
    PageboostFileDBHelper pageboostFileDBHelper = new PageboostFileDBHelper(mContext);
    mFileDBHelper = pageboostFileDBHelper;
    boolean z = true;
    pageboostFileDBHelper.setWriteAheadLoggingEnabled(true);
    boolean z2 = false;
    try {
      mFileDB = mFileDBHelper.getWritableDatabase();
      Slog.e(TAG, "getWritableDatabase for fileDB");
    } catch (SQLiteDatabaseCorruptException unused) {
      Slog.e(TAG, "SQLiteDatabaseCorruptException for fileDB");
      mFileDB = mFileDBHelper.getWritableDatabase();
    } catch (SQLiteException e) {
      String str = TAG;
      Slog.e(str, "SQLiteException for fileDB " + e.getMessage());
      if (e.getMessage().contains("malformed database")) {
        Slog.e(str, "delete fileDB and open");
        deleteDB("/data/misc/pageboost/pageboost_file_db.db");
        mFileDB = mFileDBHelper.getWritableDatabase();
      } else {
        z = false;
      }
      z2 = z;
    } catch (Exception e2) {
      Slog.e(TAG, "Exception for fileDB " + e2.getMessage());
    }
    SQLiteDatabase sQLiteDatabase = mFileDB;
    if (sQLiteDatabase != null) {
      PageboostFileDBHelper.initFileDB(sQLiteDatabase);
    }
    return z2;
  }

  public static void delayedInitPageboost() {
    checkPageboostKernelSupport();
    checkPageboostDaemonSupport();
    if (isPageboostMinimized()) {
      Slog.i(TAG, "abort delayedInitPageboost");
      return;
    }
    if (PageboostAppCapture.isRecordingCapture()) {
      IoRecord.emergencyReset();
      Slog.i(TAG, "emergency reset during bootup");
    }
    if (mContext != null && mReceiver != null && mPackageReceiver != null) {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("com.sec.android.launcher.action.RUN_APP");
      intentFilter.addAction("com.android.server.am.ACTION_PAGEBOOST");
      intentFilter.addAction("com.android.server.am.ACTION_VRAMDISK_PREFETCH");
      intentFilter.addAction("com.sec.android.intent.action.HQM_UPDATE_REQ");
      intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
      intentFilter.addAction("android.intent.action.REBOOT");
      intentFilter.addAction("android.intent.action.USER_UNLOCKED");
      mContext.registerReceiver(
          mReceiver, intentFilter, "com.android.server.am.permission.PAGEBOOST", null);
      IntentFilter intentFilter2 = new IntentFilter();
      intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
      intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
      intentFilter2.addDataScheme("package");
      mContext.registerReceiver(mPackageReceiver, intentFilter2);
      openAppDB(openFileDB());
      mZygote64FileMap = getFileMapFromCmd("zygote64");
      mZygoteFileMap = getFileMapFromCmd("zygote");
      mSystemServerFileMap = getFileMapFromCmd("system_server");
      BigDataProxy.initBigDataProxy();
      PageboostdProxy.initPageboostdProxy();
      return;
    }
    Slog.e(TAG, "Pageboost Delayed Init Failed");
  }

  public final class PageboostAppList implements Iterable {
    public LinkedList mPageboostApps = new LinkedList();

    @Override // java.lang.Iterable
    public Iterator iterator() {
      return this.mPageboostApps.iterator();
    }

    public void add(PageboostAppInfo pageboostAppInfo, boolean z) {
      synchronized (this.mPageboostApps) {
        if (z) {
          this.mPageboostApps.addFirst(pageboostAppInfo);
        } else {
          this.mPageboostApps.add(pageboostAppInfo);
        }
      }
    }

    public boolean remove(PageboostAppInfo pageboostAppInfo) {
      boolean remove;
      synchronized (this.mPageboostApps) {
        remove = this.mPageboostApps.remove(pageboostAppInfo);
      }
      return remove;
    }

    public boolean contains(PageboostAppInfo pageboostAppInfo) {
      boolean contains;
      synchronized (this.mPageboostApps) {
        contains = this.mPageboostApps.contains(pageboostAppInfo);
      }
      return contains;
    }

    public PageboostAppInfo getPageboostApp(String str) {
      PageboostAppInfo pageboostAppInfo = null;
      if (TextUtils.isEmpty(str)) {
        return null;
      }
      synchronized (this.mPageboostApps) {
        Iterator it = this.mPageboostApps.iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          PageboostAppInfo pageboostAppInfo2 = (PageboostAppInfo) it.next();
          if (str.equals(pageboostAppInfo2.mName)) {
            pageboostAppInfo = pageboostAppInfo2;
            break;
          }
        }
      }
      return pageboostAppInfo;
    }

    public PageboostAppInfo removeFromName(String str) {
      PageboostAppInfo pageboostAppInfo = null;
      if (TextUtils.isEmpty(str)) {
        return null;
      }
      synchronized (this.mPageboostApps) {
        Iterator it = this.mPageboostApps.iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          PageboostAppInfo pageboostAppInfo2 = (PageboostAppInfo) it.next();
          if (str.equals(pageboostAppInfo2.mName)) {
            it.remove();
            Slog.i(Pageboost.TAG, "remove app @ runtime : " + pageboostAppInfo2.mName);
            pageboostAppInfo = pageboostAppInfo2;
            break;
          }
        }
      }
      return pageboostAppInfo;
    }

    public void removeNotInstalled() {
      synchronized (this.mPageboostApps) {
        Iterator it = this.mPageboostApps.iterator();
        while (it.hasNext()) {
          PageboostAppInfo pageboostAppInfo = (PageboostAppInfo) it.next();
          if (pageboostAppInfo.mInstalled == 0) {
            it.remove();
            Slog.i(Pageboost.TAG, "remove not installed app : " + pageboostAppInfo.mName);
          }
        }
      }
    }

    public String toString() {
      String str = "";
      synchronized (this.mPageboostApps) {
        Iterator it = this.mPageboostApps.iterator();
        while (it.hasNext()) {
          str =
              str
                  + ((PageboostAppInfo) it.next()).toString()
                  + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
        }
      }
      return str;
    }

    public void print(PrintWriter printWriter) {
      synchronized (this.mPageboostApps) {
        Iterator it = this.mPageboostApps.iterator();
        while (it.hasNext()) {
          printWriter.println(((PageboostAppInfo) it.next()).toString());
        }
      }
    }
  }

  public abstract class VramdiskXMLParser {
    /* JADX WARN: Removed duplicated region for block: B:15:0x0064 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static LinkedList getString(String str) {
      int i;
      FileInputStream fileInputStream;
      LinkedList linkedList = new LinkedList();
      int i2 = 0;
      try {
        fileInputStream = new FileInputStream("/vendor/etc/vramdiskd.xml");
      } catch (Exception unused) {
        Slog.e(Pageboost.TAG, "failed to getString of /vendor/etc/vramdiskd.xml");
        i = i2;
        if (i > 0) {}
      }
      try {
        Document parse =
            DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileInputStream);
        parse.getDocumentElement().normalize();
        NodeList elementsByTagName = parse.getElementsByTagName(str);
        i = 0;
        for (int i3 = 0; i3 < elementsByTagName.getLength(); i3++) {
          try {
            linkedList.add(elementsByTagName.item(i3).getChildNodes().item(0).getNodeValue());
            i++;
          } catch (Throwable th) {
            th = th;
            i2 = i;
            try {
              fileInputStream.close();
            } catch (Throwable th2) {
              th.addSuppressed(th2);
            }
            throw th;
          }
        }
        try {
          fileInputStream.close();
        } catch (Exception unused2) {
          i2 = i;
          Slog.e(Pageboost.TAG, "failed to getString of /vendor/etc/vramdiskd.xml");
          i = i2;
          if (i > 0) {}
        }
        if (i > 0) {
          return linkedList;
        }
        return null;
      } catch (Throwable th3) {
        th = th3;
      }
    }
  }

  public final class VramdiskMlockManager {
    public LinkedList mFiles;
    public ArrayList pinnedFiles = new ArrayList();

    public VramdiskMlockManager(LinkedList linkedList) {
      this.mFiles = null;
      this.mFiles = linkedList;
    }

    public boolean hasPinnedFile() {
      return !this.pinnedFiles.isEmpty();
    }

    public void mlockAllFiles() {
      LinkedList linkedList = this.mFiles;
      if (linkedList == null) {
        return;
      }
      Iterator it = linkedList.iterator();
      while (it.hasNext()) {
        String str = (String) it.next();
        if (!new File(str).exists()) {
          Slog.i(Pageboost.TAG, "skip pinning: " + str + " does not exist");
        } else {
          PinnerService.PinnedFile doPinFile =
              PinnerService.doPinFile(str, Integer.MAX_VALUE, false);
          if (doPinFile != null) {
            this.pinnedFiles.add(doPinFile);
            Slog.i(Pageboost.TAG, "successfully pinned " + str);
          }
        }
      }
    }

    public void munlockAllFiles() {
      Iterator it = this.pinnedFiles.iterator();
      while (it.hasNext()) {
        ((PinnerService.PinnedFile) it.next()).close();
      }
      this.pinnedFiles.clear();
      Slog.i(Pageboost.TAG, "munlockAllFiles() done");
    }
  }

  public abstract class Vramdisk {
    public static boolean ENABLED = false;

    public static void setConfiguration(long j) {
      Slog.i(Pageboost.TAG, "dram size : " + j);
      ENABLED = Pageboost.PAGEBOOST_IGNORE_DRAM_SPECIFICATION || j >= 6442450944L;
    }

    public static LinkedList getDalvikcacheFileList(String str) {
      try {
        LinkedList linkedList = new LinkedList();
        if (!str.startsWith("/system/") && !str.startsWith("/product/")) {
          return linkedList;
        }
        String[] strArr = {"/data/dalvik-cache/arm/", "/data/dalvik-cache/arm64/"};
        String[] strArr2 = {"art", "dex", "vdex"};
        String str2 = str.substring(1).replace('/', '@') + "@classes.";
        LinkedList linkedList2 = new LinkedList();
        for (int i = 0; i < 2; i++) {
          for (int i2 = 0; i2 < 3; i2++) {
            File file = new File(strArr[i] + str2 + strArr2[i2]);
            if (file.exists()) {
              linkedList2.add(file);
            }
          }
        }
        return linkedList2;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }
  }

  public abstract class PageboostAppSelectionPolicy {
    public final Object mLock;
    public PageboostAppList mRecentPrefetchList;
    public ArrayList mRecentPrefetchStrs;

    public abstract PageboostAppList appFilter(PageboostAppList pageboostAppList, int i);

    public abstract boolean judgePredict(PageboostAppInfo pageboostAppInfo, int i);

    public PageboostAppSelectionPolicy() {
      this.mRecentPrefetchList = null;
      this.mRecentPrefetchStrs = null;
      this.mLock = new Object();
    }

    public void finalizePredict() {
      synchronized (this.mLock) {
        this.mRecentPrefetchStrs = null;
        this.mRecentPrefetchList = null;
      }
    }
  }

  public final class LRUPolicy extends PageboostAppSelectionPolicy {
    public LRUPolicy() {
      super();
    }

    @Override // com.android.server.am.Pageboost.PageboostAppSelectionPolicy
    public PageboostAppList appFilter(PageboostAppList pageboostAppList, int i) {
      PageboostAppList pageboostAppList2 = new PageboostAppList();
      ArrayList arrayList = new ArrayList();
      synchronized (this.mLock) {
        if (Pageboost.mGlobalAppLRU != null) {
          synchronized (pageboostAppList.mPageboostApps) {
            Iterator it = pageboostAppList.iterator();
            while (it.hasNext()) {
              arrayList.add(((PageboostAppInfo) it.next()).mName);
            }
          }
          synchronized (Pageboost.mGlobalAppLRU.mPageboostApps) {
            Iterator it2 = Pageboost.mGlobalAppLRU.iterator();
            int i2 = 0;
            while (it2.hasNext()) {
              PageboostAppInfo pageboostAppInfo = (PageboostAppInfo) it2.next();
              if (pageboostAppList.contains(pageboostAppInfo) && !pageboostAppInfo.alive()) {
                pageboostAppList2.add(pageboostAppInfo, false);
                i2++;
                if (i2 == i) {
                  break;
                }
              }
            }
          }
        }
        this.mRecentPrefetchStrs = arrayList;
        this.mRecentPrefetchList = pageboostAppList2;
      }
      return pageboostAppList2;
    }

    @Override // com.android.server.am.Pageboost.PageboostAppSelectionPolicy
    public boolean judgePredict(PageboostAppInfo pageboostAppInfo, int i) {
      boolean z;
      synchronized (this.mLock) {
        PageboostAppList pageboostAppList = this.mRecentPrefetchList;
        z = false;
        if (pageboostAppList != null) {
          synchronized (pageboostAppList.mPageboostApps) {
            Iterator it = this.mRecentPrefetchList.iterator();
            while (true) {
              if (!it.hasNext()) {
                break;
              }
              if (((PageboostAppInfo) it.next()) == pageboostAppInfo) {
                z = true;
                break;
              }
            }
          }
        }
        this.mRecentPrefetchStrs = null;
        this.mRecentPrefetchList = null;
      }
      return z;
    }
  }

  public final class PageboostPredictor {
    public static PageboostAppSelectionPolicy mAppSelection;

    public PageboostPredictor() {
      mAppSelection = new LRUPolicy();
    }

    public void predict(PageboostAppList pageboostAppList) {
      PageboostAppInfo pageboostAppInfo;
      PageboostAppList appFilter = mAppSelection.appFilter(pageboostAppList, 1);
      String str = "";
      synchronized (appFilter.mPageboostApps) {
        Iterator it = appFilter.iterator();
        pageboostAppInfo = null;
        int i = 0;
        while (it.hasNext()) {
          PageboostAppInfo pageboostAppInfo2 = (PageboostAppInfo) it.next();
          if (pageboostAppInfo2.execute()) {
            str = str + pageboostAppInfo2.mName + ",";
          }
          int total = pageboostAppInfo2.getMemUsage().getTotal();
          if (total >= i) {
            pageboostAppInfo = pageboostAppInfo2;
            i = total;
          }
        }
      }
      if (pageboostAppInfo != null) {
        MemReclaimer.reclaimMem(pageboostAppInfo);
      }
      StringBuilder sb = new StringBuilder();
      sb.append("Prefetch,");
      sb.append(str);
      sb.append(",MemPrep,");
      sb.append(pageboostAppInfo != null ? pageboostAppInfo.mName : "n/a");
      VramdiskLogger.add(sb.toString());
    }

    public boolean checkPredictHit(PageboostAppInfo pageboostAppInfo, int i) {
      if (i <= 0) {
        return false;
      }
      return mAppSelection.judgePredict(pageboostAppInfo, i);
    }

    public void finalizePredict() {
      mAppSelection.finalizePredict();
    }

    public static void haltPredict() {
      if (Pageboost.PAGEBOOST_ACTIVE_LAUNCH_ENABLED) {
        Pageboost.sHandler.removeMessages(21);
      }
      Pageboost.sHandler.removeMessages(3);
      PageboostdProxy.executeCmd(2, null);
    }
  }

  public final class PageboostAppInfo {
    public long mAccExecTime;
    public int mAnon;
    public String mApkPath;
    public int mApkSize;
    public int mCaptured;
    public int mExecCnt;
    public int mGPU;
    public int mHitCnt;
    public int mION;
    public int mInstalled;
    public boolean mIsGameApp;
    public long mMapCaptureSizeForPrefetch;
    public String mName;
    public String mNameTrimmed;
    public int mPid;
    public int mPrefetchCnt;
    public int mProcStatus;
    public int mProcStatusPid;
    public int mScore;
    public long mSizeForPrefetch;
    public long mSizeForVramdisk;

    public PageboostAppInfo(String str) {
      this(str, 0, 0, 0, 0, 0L, 0L, 0, 0, 0L, 0, 0, "");
      this.mPid = 0;
      this.mProcStatusPid = 0;
      this.mProcStatus = 0;
      this.mApkSize = 0;
      this.mScore = 0;
      this.mExecCnt = 0;
      this.mAccExecTime = 0L;
      this.mPrefetchCnt = 0;
      this.mHitCnt = 0;
      this.mInstalled = 0;
      this.mApkPath = "";
      this.mIsGameApp = isGameApp(str);
    }

    public PageboostAppInfo(
        String str,
        int i,
        int i2,
        int i3,
        int i4,
        long j,
        long j2,
        int i5,
        int i6,
        long j3,
        int i7,
        int i8,
        String str2) {
      this.mPid = 0;
      this.mProcStatusPid = 0;
      this.mProcStatus = 0;
      this.mInstalled = 0;
      this.mName = str;
      this.mNameTrimmed = str.replace(".", "");
      this.mCaptured = i;
      this.mAnon = i2;
      this.mION = i3;
      this.mGPU = i4;
      this.mSizeForPrefetch = j;
      this.mMapCaptureSizeForPrefetch = j2;
      this.mSizeForVramdisk = 0L;
      this.mScore = i5;
      this.mExecCnt = i6;
      this.mAccExecTime = j3;
      this.mPrefetchCnt = i7;
      this.mHitCnt = i8;
      this.mApkPath = str2;
      this.mIsGameApp = isGameApp(str);
      Slog.i(Pageboost.TAG, "appinfo : " + str + " " + i5 + " " + i6 + " " + i7 + " " + i8);
    }

    public String toString() {
      String str =
          ((((((((((((((" " + this.mPid) + " " + this.mProcStatusPid) + " " + this.mProcStatus)
                                                          + " "
                                                          + this.mName)
                                                      + " "
                                                      + this.mCaptured)
                                                  + " "
                                                  + this.mAnon)
                                              + " "
                                              + this.mION)
                                          + " "
                                          + this.mGPU)
                                      + " "
                                      + this.mSizeForPrefetch)
                                  + " "
                                  + this.mMapCaptureSizeForPrefetch)
                              + " "
                              + this.mSizeForVramdisk)
                          + " "
                          + this.mApkSize)
                      + " "
                      + this.mScore)
                  + " "
                  + this.mExecCnt)
              + " "
              + this.mAccExecTime;
      StringBuilder sb = new StringBuilder();
      sb.append(str);
      sb.append(" ");
      int i = this.mExecCnt;
      sb.append(i != 0 ? this.mAccExecTime / i : 0L);
      return ((((sb.toString() + " " + this.mPrefetchCnt) + " " + this.mHitCnt)
                  + " "
                  + this.mInstalled)
              + " "
              + this.mIsGameApp)
          + " "
          + this.mApkPath;
    }

    public final boolean isGameApp(String str) {
      if (!SemGameManager.isAvailable()) {
        return false;
      }
      try {
        return SemGameManager.isGamePackage(str);
      } catch (Exception unused) {
        return false;
      }
    }

    public boolean getGameApp() {
      return this.mIsGameApp;
    }

    public void setMemUsage(MemUsage memUsage) {
      this.mAnon = memUsage.getAnon();
      this.mION = memUsage.getIONMem();
      this.mGPU = memUsage.getGPUMem();
    }

    public MemUsage getMemUsage() {
      return new MemUsage(this.mAnon, this.mION, this.mGPU);
    }

    public void setCapturedLevel(int i) {
      this.mCaptured = i;
    }

    public int getCapturedLevel() {
      return this.mCaptured;
    }

    public void setCapturedSizeForPrefetch(long j, long j2) {
      this.mSizeForPrefetch = j;
      this.mMapCaptureSizeForPrefetch = j2;
    }

    public boolean isFullyExecuted() {
      return this.mProcStatus == 2;
    }

    public boolean isApkPathCorrect() {
      String str = this.mApkPath;
      if (str == null) {
        return false;
      }
      return str.startsWith("/data/")
          || this.mApkPath.startsWith("/system/")
          || this.mApkPath.startsWith("/product/");
    }

    public void setApkPath(String str) {
      if (str == null) {
        return;
      }
      if (str.equals(this.mApkPath)) {
        Slog.i(
            Pageboost.TAG,
            "Reuse the dbinfo for this app info : " + this.mName + " " + this.mApkPath);
        return;
      }
      Slog.i(Pageboost.TAG, "Reset this app info : " + this.mName + " " + this.mApkPath);
      this.mApkPath = str;
      if (!isApkPathCorrect()) {
        Slog.i(Pageboost.TAG, "app not in internal storage : " + this.mName + " " + this.mApkPath);
        return;
      }
      PageboostFileDBHelper.dropTable(Pageboost.mFileDB, this.mNameTrimmed);
      initAalPrefetchList();
    }

    public void updateExecTime(int i) {
      this.mExecCnt++;
      this.mAccExecTime += i;
    }

    public int getPid() {
      return this.mPid;
    }

    public void setPid(int i) {
      this.mPid = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x003b, code lost:

       r2.close();
    */
    /* JADX WARN: Removed duplicated region for block: B:26:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void getIoinfo(long[] jArr) {
      int i;
      int i2;
      Throwable th;
      try {
        BufferedReader bufferedReader =
            new BufferedReader(new FileReader("/proc/" + this.mPid + "/ioinfo"));
        i = 0;
        while (true) {
          try {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
              i2 = i + 1;
              try {
                jArr[i] = Long.parseLong(readLine);
                if (i2 == jArr.length) {
                  i = i2;
                  break;
                }
                i = i2;
              } catch (Throwable th2) {
                th = th2;
                try {
                  try {
                    bufferedReader.close();
                  } catch (Throwable th3) {
                    th.addSuppressed(th3);
                  }
                  throw th;
                } catch (Exception unused) {
                  i = i2;
                  Slog.e(Pageboost.TAG, "ioinfo read failed");
                  if (i == jArr.length) {}
                  jArr[0] = -1;
                }
              }
            }
          } catch (Throwable th4) {
            i2 = i;
            th = th4;
          }
        }
      } catch (Exception unused2) {
        i = 0;
      }
      if (i == jArr.length || !checkCmdlineByPid()) {
        jArr[0] = -1;
      }
    }

    public boolean checkCmdlineByPid() {
      boolean z = false;
      try {
        BufferedReader bufferedReader =
            new BufferedReader(new FileReader("/proc/" + this.mPid + "/cmdline"));
        try {
          String readLine = bufferedReader.readLine();
          if (readLine != null) {
            if (this.mName.equals(readLine.split("[^\\p{Alnum}\\.]+", 2)[0])) {
              z = true;
            }
          }
          bufferedReader.close();
        } finally {
        }
      } catch (Exception unused) {
        Slog.e(Pageboost.TAG, "cmdline read failed");
      }
      return z;
    }

    public final PreDo getPreDo(int i) {
      return new IoPrefetch();
    }

    public boolean execute() {
      return getPreDo(this.mScore).execute(this);
    }

    public boolean activeLaunch() {
      return getPreDo(this.mScore).activeLaunch(this);
    }

    public boolean alive() {
      return Pageboost.getPidFromPackageName(this.mName, true) != 0;
    }

    public final void initAalPrefetchList() {
      LinkedList dalvikcacheFileList;
      File[] listFiles;
      if (Pageboost.PAGEBOOST_ACTIVE_LAUNCH_ENABLED
          && PageboostFileDBHelper.createTable(Pageboost.mFileDB, this.mNameTrimmed) != null) {
        try {
          String str = this.mApkPath;
          File parentFile = new File(str).getParentFile();
          if (parentFile == null) {
            Slog.e(Pageboost.TAG, "apkpath seems not correct : " + str + ", ");
            return;
          }
          String path = parentFile.getPath();
          boolean z = false;
          if (path != null) {
            String[] strArr = {"/oat/arm64/", "/oat/arm/"};
            boolean z2 = false;
            for (int i = 0; i < 2; i++) {
              File file = new File(path + strArr[i]);
              if (file.exists() && (listFiles = file.listFiles()) != null) {
                for (File file2 : listFiles) {
                  if (file2.isFile()) {
                    PageboostFileDBHelper.insertTable(
                        Pageboost.mFileDB, this.mNameTrimmed, file2.getPath(), -1, null, 0, 2);
                  }
                }
                z2 = true;
              }
            }
            z = z2;
          }
          if (z) {
            return;
          }
          if ((str.startsWith("/system/") || str.startsWith("/product/"))
              && (dalvikcacheFileList = Vramdisk.getDalvikcacheFileList(str)) != null) {
            Iterator it = dalvikcacheFileList.iterator();
            while (it.hasNext()) {
              PageboostFileDBHelper.insertTable(
                  Pageboost.mFileDB,
                  this.mNameTrimmed,
                  ((File) it.next()).getPath(),
                  -1,
                  null,
                  0,
                  2);
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  public final class IoPrefetch implements PreDo {
    @Override // com.android.server.am.Pageboost.PreDo
    public boolean execute(PageboostAppInfo pageboostAppInfo) {
      if (pageboostAppInfo.mNameTrimmed.length() >= 256) {
        Slog.e(
            Pageboost.TAG,
            "Abort IoPrefetch due to long package name : " + pageboostAppInfo.mNameTrimmed);
        return false;
      }
      if (pageboostAppInfo.getCapturedLevel() <= 0) {
        return false;
      }
      PageboostdProxy.executeCmd(1, pageboostAppInfo.mNameTrimmed);
      Slog.i(Pageboost.TAG, "IO Prefetch for : " + pageboostAppInfo.mName);
      return true;
    }

    @Override // com.android.server.am.Pageboost.PreDo
    public boolean activeLaunch(PageboostAppInfo pageboostAppInfo) {
      if (pageboostAppInfo.mNameTrimmed.length() >= 256) {
        Slog.e(
            Pageboost.TAG, "Abort alp due to long package name : " + pageboostAppInfo.mNameTrimmed);
        return false;
      }
      if (pageboostAppInfo.mProcStatus != 2) {
        PageboostdProxy.executeCmd(4, pageboostAppInfo.mNameTrimmed);
        Slog.i(
            Pageboost.TAG,
            "alp for : " + pageboostAppInfo.mName + " , " + pageboostAppInfo.mProcStatus);
        return true;
      }
      Slog.i(
          Pageboost.TAG,
          "alp skip : " + pageboostAppInfo.mName + " , " + pageboostAppInfo.mProcStatus);
      return false;
    }
  }

  public final class FileMapList {
    public boolean mCorrectness;
    public Hashtable mFiles = new Hashtable();
    public int mPid;

    public FileMapList(int i) {
      this.mPid = 0;
      this.mCorrectness = false;
      if (i <= 0) {
        return;
      }
      String num = Integer.toString(i);
      this.mPid = i;
      Slog.i(Pageboost.TAG, "filemap pid : " + num);
      try {
        BufferedReader bufferedReader =
            new BufferedReader(new FileReader("/proc/" + num + "/filemap_list"));
        try {
          this.mCorrectness = true;
          while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
              add(readLine);
            } else {
              Slog.i(Pageboost.TAG, "filemap pid : " + num + ", done correctly");
              bufferedReader.close();
              return;
            }
          }
        } finally {
        }
      } catch (Exception unused) {
        Slog.e(Pageboost.TAG, "filemap pid : " + num + ", aborted");
        this.mCorrectness = false;
      }
    }

    public final void add(String str) {
      if (!this.mCorrectness || contain(str)) {
        return;
      }
      this.mFiles.put(str, 1);
    }

    public boolean contain(String str) {
      return this.mCorrectness && this.mFiles.get(str) != null;
    }

    public boolean isCorrect() {
      return this.mCorrectness;
    }
  }

  public final class FileMapInfo {
    public boolean mCorrectness;
    public int mPid;

    public FileMapInfo(int i) {
      this.mPid = 0;
      this.mCorrectness = false;
      if (i <= 0) {
        return;
      }
      this.mPid = i;
      this.mCorrectness = true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00b7, code lost:

       android.util.Slog.e(com.android.server.am.Pageboost.TAG, "unknown error during parsing of filemap_info");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long[] getFileMapInfo(String str, boolean z, String str2, String str3) {
      long j;
      FileWriter fileWriter;
      Throwable th;
      int i;
      if (!this.mCorrectness) {
        return null;
      }
      String str4 = "/proc/" + Integer.toString(this.mPid) + "/filemap_info";
      long j2 = 0;
      try {
        fileWriter = new FileWriter(new File(str4));
      } catch (Exception e) {
        e = e;
        j = 0;
      }
      try {
        try {
          fileWriter.write(str);
          byte[] readAllBytes = Files.readAllBytes(Paths.get(str4, new String[0]));
          j = z ? readAllBytes.length : 0L;
          int i2 = 0;
          int i3 = 0;
          while (true) {
            try {
              if (i2 >= readAllBytes.length) {
                break;
              }
              if (readAllBytes[i2] == 10) {
                String[] split =
                    new String(Arrays.copyOfRange(readAllBytes, i3, i2), StandardCharsets.UTF_8)
                        .split(" ");
                if (split.length != 2) {
                  break;
                }
                if (split[0].equals("mapped")) {
                  j2 += Long.parseLong(split[1]) * 4096;
                  i3 = i2 + 1;
                } else {
                  int parseInt = Integer.parseInt(split[0]);
                  int parseInt2 = Integer.parseInt(split[1]);
                  int i4 = i2 + parseInt2 + 1;
                  byte[] copyOfRange = Arrays.copyOfRange(readAllBytes, i2 + 1, i4);
                  int i5 = i4 + 1;
                  if (z) {
                    i = i4;
                    PageboostFileDBHelper.insertTable(
                        Pageboost.mFileDB, str2, str, parseInt, copyOfRange, parseInt2, 0);
                  } else {
                    i = i4;
                  }
                  i3 = i5;
                  i2 = i;
                }
              }
              i2++;
            } catch (Throwable th2) {
              th = th2;
              try {
                fileWriter.close();
                throw th;
              } catch (Throwable th3) {
                th.addSuppressed(th3);
                throw th;
              }
            }
          }
          fileWriter.close();
        } catch (Exception e2) {
          e = e2;
          e.printStackTrace();
          return new long[] {j2, j};
        }
        return new long[] {j2, j};
      } catch (Throwable th4) {
        th = th4;
        j = 0;
      }
    }

    public boolean isCorrect() {
      return this.mCorrectness;
    }
  }

  public final class IoRecord {
    public boolean is64bit;
    public PageboostAppInfo mApp;
    public boolean mCorrectness;
    public int mPid;
    public String mPkg;
    public long mBytesRecorded = 0;
    public boolean mRecordDone = false;

    public IoRecord(PageboostAppInfo pageboostAppInfo, int i) {
      this.mPid = 0;
      this.mCorrectness = false;
      if (i <= 0) {
        return;
      }
      String num = Integer.toString(i);
      this.mPid = i;
      this.mPkg = pageboostAppInfo.mName;
      this.mApp = pageboostAppInfo;
      boolean write = write("/proc/" + num + "/io_record_control", "2");
      this.mCorrectness = write;
      if (write) {
        this.is64bit = PageboostAppCapture.check64Bit(this.mPid);
        Slog.i(Pageboost.TAG, "IoRecord pid : " + num + ", started correctly");
      }
    }

    public static void emergencyReset() {
      write("/proc/self/io_record_control", "1");
    }

    public static boolean write(String str, String str2) {
      try {
        FileWriter fileWriter = new FileWriter(new File(str));
        try {
          fileWriter.write(str2);
          fileWriter.flush();
          fileWriter.close();
          return true;
        } finally {
        }
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }

    public PageboostAppInfo getApp() {
      return this.mApp;
    }

    public static int readInt(byte[] bArr) {
      return (bArr[0] & 255) | (bArr[3] << 24) | ((bArr[2] & 255) << 16) | ((bArr[1] & 255) << 8);
    }

    public static byte[] resizeBitmap(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
      if (((i3 - (i + i2)) + 7) / 8 > i5) {
        return null;
      }
      int i6 = (i4 + i3) - i;
      byte[] createBitmap = createBitmap(i6);
      System.arraycopy(bArr, 0, createBitmap, 0, bArr.length);
      int i7 = i3 - i;
      fillBitmap(createBitmap, i2, i7, false);
      fillBitmap(createBitmap, i7, i6, true);
      return createBitmap;
    }

    public static byte[] createBitmap(int i) {
      return new byte[(i + 7) / 8];
    }

    public static void fillBitmap(byte[] bArr, int i, int i2, boolean z) {
      int i3 = i % 8;
      if (i3 != 0) {
        int i4 = i / 8;
        int i5 = bArr[i4] & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        while (i3 < 8) {
          i5 = z ? i5 | (1 << i3) : i5 & (~(1 << i3));
          i3++;
        }
        bArr[i4] = (byte) (i5 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        i = (i4 + 1) * 8;
      }
      int i6 = i / 8;
      int i7 = i2 / 8;
      if (i6 < i7) {
        Arrays.fill(bArr, i6, i7, (byte) (z ? 255 : 0));
      }
      int i8 = i2 % 8;
      if (i8 != 0) {
        int i9 = bArr[i7] & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        for (int i10 = 0; i10 < i8; i10++) {
          i9 = z ? i9 | (1 << i10) : i9 & (~(1 << i10));
        }
        bArr[i7] = (byte) (i9 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
      }
    }

    public final long parsingResult(byte[] bArr) {
      int i;
      byte[] bArr2;
      byte[] bArr3;
      String str;
      int i2;
      int i3;
      int i4;
      int i5;
      boolean z;
      if (!isCorrect()) {
        return -1L;
      }
      PageboostFileDBHelper.dropTable(Pageboost.mFileDB, this.mApp.mNameTrimmed);
      String createTable =
          PageboostFileDBHelper.createTable(Pageboost.mFileDB, this.mApp.mNameTrimmed);
      if (createTable == null) {
        Slog.i(Pageboost.TAG, "db table failed");
        return -1L;
      }
      this.mApp.initAalPrefetchList();
      Slog.i(Pageboost.TAG, "db insert start");
      long j = 0;
      int i6 = 0;
      boolean z2 = false;
      while (i6 < bArr.length) {
        int i7 = i6 + 4;
        int readInt = readInt(Arrays.copyOfRange(bArr, i6, i7));
        int i8 = -1;
        if (readInt == -1) {
          break;
        }
        int i9 = i7 + readInt;
        String str2 = new String(Arrays.copyOfRange(bArr, i7, i9), StandardCharsets.UTF_8);
        String str3 = this.mApp.mApkPath;
        int i10 = (str3 == null || !str3.equals(str2)) ? 0 : 2;
        int i11 = -1;
        int i12 = -1;
        byte[] bArr4 = null;
        while (true) {
          int i13 = i9 + 4;
          int readInt2 = readInt(Arrays.copyOfRange(bArr, i9, i13));
          i = i13 + 4;
          int readInt3 = readInt(Arrays.copyOfRange(bArr, i13, i));
          if (readInt2 == i8 && readInt3 == i8) {
            break;
          }
          if (PageboostAppCapture.isRecordingTarget(str2, this.is64bit)) {
            int i14 = readInt;
            long j2 = j + (readInt3 * 4096);
            if (j2 > 419430400) {
              z2 = true;
              break;
            }
            if (bArr4 != null) {
              bArr2 = bArr4;
              bArr3 = resizeBitmap(bArr4, i11, i12, readInt2, readInt3, i14);
            } else {
              bArr2 = bArr4;
              bArr3 = null;
            }
            if (bArr3 == null) {
              if (bArr2 != null) {
                SQLiteDatabase sQLiteDatabase = Pageboost.mFileDB;
                int length = bArr2.length;
                i4 = readInt3;
                i5 = readInt2;
                String str4 = str2;
                str = str2;
                byte[] bArr5 = bArr2;
                i3 = -1;
                z = true;
                i2 = i14;
                PageboostFileDBHelper.insertTable(
                    sQLiteDatabase, createTable, str4, i11, bArr5, length, i10);
              } else {
                i4 = readInt3;
                i5 = readInt2;
                str = str2;
                z = true;
                i2 = i14;
                i3 = -1;
              }
              byte[] createBitmap = createBitmap(i4);
              fillBitmap(createBitmap, 0, i4, z);
              i11 = i5;
              bArr4 = createBitmap;
            } else {
              str = str2;
              i2 = i14;
              i3 = -1;
              bArr4 = bArr3;
              i4 = (readInt2 + readInt3) - i11;
            }
            i9 = i;
            str2 = str;
            i8 = i3;
            readInt = i2;
            j = j2;
            i12 = i4;
          } else {
            i9 = i;
          }
        }
        if (bArr4 != null) {
          PageboostFileDBHelper.insertTable(
              Pageboost.mFileDB, createTable, str2, i11, bArr4, bArr4.length, i10);
        }
        if (z2) {
          break;
        }
        i6 = i;
      }
      Slog.i(Pageboost.TAG, "db insert done, overLimit " + z2);
      this.mBytesRecorded = j;
      return j;
    }

    public long getResultFromKernel() {
      if (!isCorrect()) {
        return -1L;
      }
      Slog.i(Pageboost.TAG, "IoRecord pid : " + this.mPid);
      String str = "/proc/" + this.mPid + "/io_record_control";
      if (!write(str, "3")) {
        Slog.e(Pageboost.TAG, "StopRecording Failed");
        return -1L;
      }
      if (!write(str, "4")) {
        Slog.e(Pageboost.TAG, "PostRecording Failed");
        return -1L;
      }
      try {
        byte[] readAllBytes = Files.readAllBytes(Paths.get(str, new String[0]));
        Slog.i(
            Pageboost.TAG,
            "IoRecord pid : " + this.mPid + ", result_size : " + readAllBytes.length);
        long parsingResult = parsingResult(readAllBytes);
        if (!write(str, "1")) {
          Slog.e(Pageboost.TAG, "iorecord re-init Failed");
          return -1L;
        }
        if (!(parsingResult >= 0)) {
          return -1L;
        }
        this.mRecordDone = true;
        return parsingResult;
      } catch (Exception unused) {
        Slog.e(Pageboost.TAG, "CatRecordedData Failed");
        return -1L;
      }
    }

    public boolean isCorrect() {
      return this.mCorrectness;
    }
  }

  public abstract class PageboostAppCapture {
    public static final int PAGEBOOST_IO_PREFETCH_LEVEL =
        SystemProperties.getInt("ro.config.pageboost.io_prefetch.level", 3);
    public static boolean record_ongoing = false;

    public static boolean isCaptureTarget(String str) {
      if (str != null) {
        return ((!str.startsWith("/data")
                    && !str.startsWith("/system")
                    && !str.startsWith("/product"))
                || str.startsWith("/data/misc")
                || str.contains("(deleted"))
            ? false
            : true;
      }
      return false;
    }

    public static boolean check64Bit(int i) {
      boolean z = false;
      if (i <= 0) {
        return false;
      }
      try {
        RandomAccessFile randomAccessFile =
            new RandomAccessFile(new File("/proc/" + i + "/exe"), "r");
        try {
          byte[] bArr = new byte[6];
          byte[] bytes = "\u007fELF".getBytes("UTF-8");
          randomAccessFile.read(bArr);
          boolean z2 = Arrays.equals(Arrays.copyOfRange(bArr, 0, 4), bytes) && bArr[4] == 2;
          randomAccessFile.close();
          randomAccessFile.close();
          z = z2;
        } finally {
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      Slog.i(Pageboost.TAG, "64 bit checked : " + z + " for " + i);
      return z;
    }

    public static boolean needCapture(PageboostAppInfo pageboostAppInfo) {
      return pageboostAppInfo != null
          && !pageboostAppInfo.isFullyExecuted()
          && pageboostAppInfo.isApkPathCorrect()
          && pageboostAppInfo.getCapturedLevel() < PAGEBOOST_IO_PREFETCH_LEVEL;
    }

    public static boolean isRecordingCapture() {
      return PAGEBOOST_IO_PREFETCH_LEVEL == 3;
    }

    public static void capture(PageboostAppInfo pageboostAppInfo, int i) {
      if (3 == PAGEBOOST_IO_PREFETCH_LEVEL) {
        record(pageboostAppInfo, i);
      } else {
        snapshot(pageboostAppInfo, i);
      }
    }

    public static boolean isRecordingTarget(String str, boolean z) {
      if (isCaptureTarget(str)) {
        if (z && !Pageboost.mZygote64FileMap.contain(str)) {
          return true;
        }
        if (!z && !Pageboost.mZygoteFileMap.contain(str)) {
          return true;
        }
      }
      return false;
    }

    public static void captureFinished(IoRecord ioRecord) {
      int i = PAGEBOOST_IO_PREFETCH_LEVEL;
      if (3 == i) {
        PageboostAppInfo app = ioRecord.getApp();
        record_ongoing = false;
        if (app == null) {
          Slog.e(Pageboost.TAG, "captureFinished requested for null app");
          return;
        }
        if (ioRecord.mRecordDone) {
          app.setCapturedLevel(i);
          app.setCapturedSizeForPrefetch(ioRecord.mBytesRecorded, 0L);
          Slog.i(Pageboost.TAG, "captureFinished success : " + ioRecord.mBytesRecorded);
        } else {
          IoRecord.emergencyReset();
          Slog.e(Pageboost.TAG, "captureFinished fail");
        }
        VramdiskLogger.add("RecordEnd," + app.mName + "," + ioRecord.mRecordDone);
      }
    }

    public static void record(PageboostAppInfo pageboostAppInfo, int i) {
      if (record_ongoing) {
        Slog.e(Pageboost.TAG, "record is ongoing. Abort for " + i);
        return;
      }
      if (i > 0) {
        IoRecord ioRecord = new IoRecord(pageboostAppInfo, i);
        if (ioRecord.isCorrect()) {
          record_ongoing = true;
          VramdiskLogger.add("RecordStart," + pageboostAppInfo.mName);
          if (pageboostAppInfo.getGameApp()) {
            Pageboost.sendMessageWithObject(15, ioRecord, 35000);
          } else {
            Pageboost.sendMessageWithObject(15, ioRecord, 5000);
          }
        }
      }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0179, code lost:

       r9 = r7;
       r7 = r13;
       r5 = r20;
    */
    /* JADX WARN: Removed duplicated region for block: B:42:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x014a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0215  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void snapshot(PageboostAppInfo pageboostAppInfo, int i) {
      long j;
      long j2;
      boolean z;
      int i2;
      String str;
      File file;
      boolean z2;
      Iterator it;
      boolean z3;
      boolean z4;
      long currentTimeMillis;
      if (Pageboost.mZygote64FileMap == null
          || !Pageboost.mZygote64FileMap.isCorrect()
          || Pageboost.mZygoteFileMap == null
          || !Pageboost.mZygoteFileMap.isCorrect()) {
        return;
      }
      Slog.i(Pageboost.TAG, "IO Capture start for : " + pageboostAppInfo.mName + "pid : " + i);
      PageboostFileDBHelper.dropTable(Pageboost.mFileDB, pageboostAppInfo.mNameTrimmed);
      String createTable =
          PageboostFileDBHelper.createTable(Pageboost.mFileDB, pageboostAppInfo.mNameTrimmed);
      if (createTable == null) {
        return;
      }
      long j3 = 0;
      if (2 <= PAGEBOOST_IO_PREFETCH_LEVEL) {
        long currentTimeMillis2 = System.currentTimeMillis();
        boolean check64Bit = check64Bit(i);
        FileMapList fileMapList = new FileMapList(i);
        if (fileMapList.isCorrect()) {
          Iterator it2 = fileMapList.mFiles.keySet().iterator();
          long j4 = 0;
          long j5 = 0;
          boolean z5 = false;
          while (true) {
            if (!it2.hasNext()) {
              z2 = z5;
              break;
            }
            String str2 = (String) it2.next();
            if (isCaptureTarget(str2)
                && (!check64Bit || !Pageboost.mZygote64FileMap.contain(str2))) {
              if (check64Bit || !Pageboost.mZygoteFileMap.contain(str2)) {
                FileMapInfo fileMapInfo = new FileMapInfo(i);
                boolean z6 = check64Bit;
                if (fileMapInfo.isCorrect()) {
                  it = it2;
                  z3 = z5;
                  long[] fileMapInfo2 =
                      fileMapInfo.getFileMapInfo(
                          str2, true, createTable, pageboostAppInfo.mApkPath);
                  if (fileMapInfo2 != null) {
                    long j6 = fileMapInfo2[0];
                    j3 += j6;
                    j4 += j6;
                    long j7 = j5 + fileMapInfo2[1];
                    if (j4 > 157286400) {
                      Slog.i(Pageboost.TAG, "IO Capture 'map' over limit : " + j4 + "/157286400");
                      break;
                    }
                    if (j7 > 131072) {
                      Slog.i(
                          Pageboost.TAG,
                          "IO Capture 'map' over dbsize limit : "
                              + j7
                              + "/"
                              + IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES);
                      break;
                    }
                    j5 = j7;
                    z4 = true;
                    boolean z7 = z4;
                    currentTimeMillis = System.currentTimeMillis() - currentTimeMillis2;
                    if (currentTimeMillis <= 1000) {
                      String str3 = Pageboost.TAG;
                      StringBuilder sb = new StringBuilder();
                      z2 = z7;
                      sb.append("IO Capture 'map' timed out : ");
                      sb.append(currentTimeMillis);
                      sb.append("/");
                      sb.append(1000);
                      Slog.i(str3, sb.toString());
                      break;
                    }
                    it2 = it;
                    check64Bit = z6;
                    z5 = z7;
                  }
                } else {
                  it = it2;
                  z3 = z5;
                }
                z4 = z3;
                boolean z72 = z4;
                currentTimeMillis = System.currentTimeMillis() - currentTimeMillis2;
                if (currentTimeMillis <= 1000) {}
              }
            }
          }
          long j8 = j3;
          long j9 = j4;
          z = true;
          long currentTimeMillis3 = System.currentTimeMillis();
          Slog.i(
              Pageboost.TAG,
              "IO Capture 'mapped files' pid : "
                  + i
                  + " size : "
                  + j9
                  + " time : "
                  + (currentTimeMillis3 - currentTimeMillis2));
          j2 = j9;
          j = j8;
          i2 = PAGEBOOST_IO_PREFETCH_LEVEL;
          if (1 <= i2 && (str = pageboostAppInfo.mApkPath) != null && isCaptureTarget(str)) {
            file = new File(str);
            if (file.exists()) {
              int length = (int) file.length();
              int i3 = length > 52428800 ? 52428800 : length;
              int i4 = i3 / 32768;
              if (i4 > 0) {
                byte[] bArr = new byte[i4];
                Arrays.fill(bArr, (byte) -1);
                int i5 = i3;
                PageboostFileDBHelper.insertTable(
                    Pageboost.mFileDB, createTable, str, 0, bArr, i4, 0);
                Slog.i(Pageboost.TAG, "IO Capture 'apk', pid : " + i + ", size : " + i5);
                j += (long) i5;
                z = true;
              }
            }
          }
          if (z) {
            pageboostAppInfo.setCapturedLevel(i2);
            pageboostAppInfo.setCapturedSizeForPrefetch(j, j2);
            VramdiskLogger.add("Capture," + pageboostAppInfo.mName);
          }
          Slog.i(
              Pageboost.TAG,
              "IO Capture end for : "
                  + pageboostAppInfo.mName
                  + "pid : "
                  + i
                  + " done correctly? "
                  + z);
        }
      }
      j = 0;
      j2 = 0;
      z = false;
      i2 = PAGEBOOST_IO_PREFETCH_LEVEL;
      if (1 <= i2) {
        file = new File(str);
        if (file.exists()) {}
      }
      if (z) {}
      Slog.i(
          Pageboost.TAG,
          "IO Capture end for : "
              + pageboostAppInfo.mName
              + "pid : "
              + i
              + " done correctly? "
              + z);
    }
  }

  public final class MemUsage {
    public int anon;
    public int gpu;
    public int ion;

    public MemUsage(int i, int i2, int i3) {
      this.anon = i;
      this.ion = i2;
      this.gpu = i3;
    }

    public int getAnon() {
      return this.anon;
    }

    public int getIONMem() {
      return this.ion;
    }

    public int getGPUMem() {
      return this.gpu;
    }

    public int getTotal() {
      return this.anon + this.ion + this.gpu;
    }
  }

  public abstract class MemUsageCollector {
    public static boolean collectMemUsage(PageboostAppInfo pageboostAppInfo) {
      int i = pageboostAppInfo.mProcStatusPid;
      if (i <= 0) {
        return false;
      }
      Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
      Debug.getMemoryInfo(i, memoryInfo);
      int collectAnon = collectAnon(memoryInfo);
      if (collectAnon <= 0) {
        return false;
      }
      int collectIONMem = collectIONMem(memoryInfo);
      int collectGPUMem = collectGPUMem(memoryInfo);
      pageboostAppInfo.setMemUsage(new MemUsage(collectAnon, collectIONMem, collectGPUMem));
      Slog.i(
          Pageboost.TAG,
          "memUsage collected : "
              + collectAnon
              + " "
              + collectIONMem
              + " "
              + collectGPUMem
              + " for "
              + pageboostAppInfo.mName
              + " "
              + i);
      return true;
    }

    public static int collectAnon(Debug.MemoryInfo memoryInfo) {
      return memoryInfo.getTotalPrivateDirty() + memoryInfo.getTotalSwappedOutPss();
    }

    public static int collectIONMem(Debug.MemoryInfo memoryInfo) {
      return memoryInfo.getOtherPrivate(14);
    }

    public static int collectGPUMem(Debug.MemoryInfo memoryInfo) {
      return memoryInfo.getOtherPrivate(15);
    }
  }

  public static void sendMessageWithBundle(int i, Bundle bundle, int i2) {
    Message obtainMessage = sHandler.obtainMessage(i);
    obtainMessage.setData(bundle);
    if (i2 > 0) {
      sHandler.sendMessageDelayed(obtainMessage, i2);
    } else {
      sHandler.sendMessage(obtainMessage);
    }
  }

  public static void sendMessageWithObject(int i, Object obj, int i2) {
    Message obtainMessage = sHandler.obtainMessage(i, obj);
    if (i2 > 0) {
      sHandler.sendMessageDelayed(obtainMessage, i2);
    } else {
      sHandler.sendMessage(obtainMessage);
    }
  }

  public static void sendMessage(int i, String str, int i2, int i3, int i4, int i5) {
    Bundle bundle = new Bundle();
    if (str != null) {
      bundle.putString("pkg", str);
    }
    if (i2 >= 0) {
      bundle.putInt("launchtime", i2);
    }
    if (i3 >= 0) {
      bundle.putInt("pid", i3);
    }
    if (i4 >= 0) {
      bundle.putInt("status", i4);
    }
    Message obtainMessage = sHandler.obtainMessage(i);
    obtainMessage.setData(bundle);
    if (i5 > 0) {
      sHandler.sendMessageDelayed(obtainMessage, i5);
    } else {
      sHandler.sendMessage(obtainMessage);
    }
  }

  public final class PageboostHandler extends Handler {
    public PageboostHandler(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int pidFromPackageName;
      try {
        PageboostAppInfo pageboostAppInfo = null;
        boolean z = false;
        z = false;
        z = false;
        z = false;
        switch (message.what) {
          case 1:
            Pageboost.delayedInitPageboost();
            return;
          case 2:
            if (Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED) {
              PageboostPredictor.haltPredict();
              Pageboost.sendMessageWithObject(3, (PageboostAppList) message.obj, 400);
              return;
            }
            return;
          case 3:
            if (Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED
                && !Pageboost.PAGEBOOST_VRAMDISK_WITHOUT_PREDICT) {
              PageboostAppList pageboostAppList = (PageboostAppList) message.obj;
              Slog.i(Pageboost.TAG, "Launcher Page Up");
              if (Pageboost.mPredictor != null) {
                Pageboost.mPredictor.predict(pageboostAppList);
                return;
              }
              return;
            }
            return;
          case 4:
            Pageboost.realupdatePackages();
            return;
          case 5:
            if (Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED && Pageboost.mGlobalAppLRU != null) {
              String str = (String) message.obj;
              int pidFromPackageName2 = Pageboost.getPidFromPackageName(str, true);
              if (pidFromPackageName2 > 0) {
                PageboostAppInfo pageboostApp = Pageboost.mGlobalAppLRU.getPageboostApp(str);
                if (PageboostAppCapture.needCapture(pageboostApp)) {
                  Slog.i(Pageboost.TAG, "capturing App IO");
                  PageboostAppCapture.capture(pageboostApp, pidFromPackageName2);
                  return;
                }
                return;
              }
              return;
            }
            return;
          case 6:
            if (Pageboost.mGlobalAppLRU == null) {
              return;
            }
            PageboostAppDBHelper.storeAppLRU(Pageboost.mAppDB, Pageboost.mGlobalAppLRU);
            BigDataProxy.keepLastData();
            return;
          case 7:
            String str2 = (String) message.obj;
            if (str2 != null) {
              Pageboost.addPackage(str2);
              return;
            }
            return;
          case 8:
            String str3 = (String) message.obj;
            if (str3 != null) {
              Pageboost.removePackage(str3);
              return;
            }
            return;
          case 9:
            PageboostPredictor.haltPredict();
            Pageboost.sendMessageWithBundle(11, message.getData(), 3000);
            return;
          case 10:
            PageboostPredictor.haltPredict();
            Pageboost.sendMessageWithBundle(12, message.getData(), 3000);
            return;
          case 11:
          case 12:
            if (Pageboost.mGlobalAppLRU == null) {
              return;
            }
            Bundle data = message.getData();
            int i = data.getInt("pid");
            int i2 = data.getInt("launchtime");
            String str4 = "0,0,0";
            String string = data.getString("pkg");
            Slog.i(Pageboost.TAG, "Launcher App Execution");
            if (Pageboost.mPredictor != null) {
              pageboostAppInfo = Pageboost.mGlobalAppLRU.getPageboostApp(string);
              if (pageboostAppInfo != null && Pageboost.mGlobalAppLRU.remove(pageboostAppInfo)) {
                if (pageboostAppInfo.mProcStatus == 1) {
                  pageboostAppInfo.mProcStatus = 2;
                }
                if (i == 0 || i == pageboostAppInfo.getPid()) {
                  i2 = 0;
                } else {
                  pageboostAppInfo.setPid(i);
                  if (i2 > 0 && i2 < 10000) {
                    pageboostAppInfo.updateExecTime(i2);
                  }
                  long[] jArr = new long[3];
                  pageboostAppInfo.getIoinfo(jArr);
                  if (jArr[0] != -1) {
                    str4 = "";
                    for (int i3 = 0; i3 < 3; i3++) {
                      str4 = str4 + jArr[i3] + ",";
                    }
                  }
                }
                if (Pageboost.mPredictor.checkPredictHit(pageboostAppInfo, i2)) {
                  pageboostAppInfo.mHitCnt++;
                  Slog.i(Pageboost.TAG, "Prefetch Hit! : " + string);
                  z = true;
                }
                Pageboost.mGlobalAppLRU.add(pageboostAppInfo, true);
              }
              Pageboost.mPredictor.finalizePredict();
            }
            if (pageboostAppInfo != null) {
              if (i2 > 0) {
                VramdiskLogger.add("AppEntry," + string + "," + i2 + "," + z + "," + str4);
                return;
              }
              if (i2 == 0) {
                VramdiskLogger.add("AppReEntry," + string + "," + i2 + "," + z);
                return;
              }
              return;
            }
            return;
          case 13:
            BigDataProxy.sendData();
            return;
          case 14:
            if (Pageboost.mGlobalAppLRU == null) {
              return;
            }
            String str5 = (String) message.obj;
            PageboostAppInfo pageboostApp2 = Pageboost.mGlobalAppLRU.getPageboostApp(str5);
            if (PageboostAppCapture.needCapture(pageboostApp2)) {
              int i4 = pageboostApp2.mProcStatusPid;
              Slog.i(Pageboost.TAG, "Record App IO : " + str5 + " pid " + i4);
              if (i4 == 0) {
                for (int i5 = 0; i5 < 10; i5++) {
                  try {
                    i4 = Pageboost.getPidFromPackageName(str5, true);
                    if (i4 <= 0) {
                      Thread.sleep(50L);
                    }
                  } catch (Exception e) {
                    Slog.e(Pageboost.TAG, "Failed to sleep");
                    e.printStackTrace();
                  }
                }
              }
              if (i4 > 0) {
                PageboostAppCapture.capture(pageboostApp2, i4);
                return;
              }
              return;
            }
            return;
          case 15:
            IoRecord ioRecord = (IoRecord) message.obj;
            ioRecord.getResultFromKernel();
            PageboostAppCapture.captureFinished(ioRecord);
            PageboostAppInfo app = ioRecord.getApp();
            if (app != null) {
              PageboostAppDBHelper.storeApp(Pageboost.mAppDB, Pageboost.mGlobalAppLRU, app);
              return;
            }
            return;
          case 16:
            if (Pageboost.mGlobalAppLRU == null) {
              return;
            }
            Bundle data2 = message.getData();
            int i6 = data2.getInt("status");
            int i7 = data2.getInt("pid");
            String string2 = data2.getString("pkg");
            PageboostAppInfo pageboostApp3 = Pageboost.mGlobalAppLRU.getPageboostApp(string2);
            if (pageboostApp3 != null) {
              if (i6 == 1) {
                if (pageboostApp3.mProcStatusPid != 0
                    && (pidFromPackageName = Pageboost.getPidFromPackageName(string2, false))
                        != 0) {
                  i7 = pidFromPackageName;
                }
                if (pageboostApp3.mProcStatusPid != i7) {
                  pageboostApp3.mProcStatusPid = i7;
                  pageboostApp3.mProcStatus = 1;
                  return;
                }
                return;
              }
              if (i6 == 2 && pageboostApp3.mProcStatusPid == i7) {
                pageboostApp3.mProcStatusPid = 0;
                pageboostApp3.mProcStatus = 0;
                return;
              }
              return;
            }
            return;
          case 17:
            if (Pageboost.mGlobalAppLRU == null) {
              return;
            }
            PageboostAppInfo pageboostApp4 =
                Pageboost.mGlobalAppLRU.getPageboostApp((String) message.obj);
            if (pageboostApp4 == null || pageboostApp4.getMemUsage().getTotal() != 0) {
              return;
            }
            if (pageboostApp4.getGameApp()) {
              Pageboost.sendMessageWithObject(18, pageboostApp4, 35000);
              return;
            } else {
              Pageboost.sendMessageWithObject(18, pageboostApp4, 5000);
              return;
            }
          case 18:
            MemUsageCollector.collectMemUsage((PageboostAppInfo) message.obj);
            return;
          case 19:
            if (Pageboost.mGlobalAppLRU == null) {
              return;
            }
            Pageboost.prefetchPackage(message.getData().getString("pkg"), "alp");
            return;
          case 20:
            PageboostPredictor.haltPredict();
            return;
          case 21:
            Slog.i(Pageboost.TAG, "stop alp by timeout");
            PageboostPredictor.haltPredict();
            return;
          case 22:
          default:
            return;
          case 23:
            Pageboost.mBootFileManager =
                new VramdiskMlockManager(VramdiskXMLParser.getString("boot_file"));
            Pageboost.mBootFileManager.mlockAllFiles();
            return;
          case 24:
            if (Pageboost.mBootFileManager != null) {
              Pageboost.mBootFileManager.munlockAllFiles();
            }
            Pageboost.mBootFileManager = null;
            return;
        }
      } catch (Exception unused) {
        Slog.e(Pageboost.TAG, "failed to handleMessage " + message.what);
      }
      Slog.e(Pageboost.TAG, "failed to handleMessage " + message.what);
    }
  }

  public abstract class PageboostdProxy {
    public static DaemonConnector conn;

    public static void initPageboostdProxy() {
      conn = new DaemonConnector("pageboostd");
    }

    public static void executeCmd(int i, String str) {
      if (conn == null) {
        return;
      }
      if (i == 1) {
        byte[] bytes = str.getBytes();
        ByteBuffer allocate = ByteBuffer.allocate(bytes.length + 8);
        allocate.putInt(1);
        allocate.putInt(bytes.length);
        allocate.put(bytes);
        conn.writeDaemon(allocate);
        return;
      }
      if (i == 2) {
        ByteBuffer allocate2 = ByteBuffer.allocate(4);
        allocate2.putInt(2);
        conn.writeDaemon(allocate2);
      } else {
        if (i == 3) {
          ByteBuffer allocate3 = ByteBuffer.allocate(8);
          allocate3.putInt(3);
          allocate3.putInt(Integer.parseInt(str));
          conn.writeDaemon(allocate3);
          return;
        }
        if (i == 4) {
          byte[] bytes2 = str.getBytes();
          ByteBuffer allocate4 = ByteBuffer.allocate(bytes2.length + 8);
          allocate4.putInt(4);
          allocate4.putInt(bytes2.length);
          allocate4.put(bytes2);
          conn.writeDaemon(allocate4);
        }
      }
    }
  }

  public final class DaemonConnector {
    public OutputStream sOutputStream;
    public LocalSocket sSocket;
    public String sSocketName;

    public DaemonConnector(String str) {
      this.sSocketName = str;
      openSocket();
    }

    public final boolean openSocket() {
      try {
        LocalSocket localSocket = new LocalSocket(3);
        this.sSocket = localSocket;
        localSocket.connect(
            new LocalSocketAddress(this.sSocketName, LocalSocketAddress.Namespace.RESERVED));
        this.sOutputStream = this.sSocket.getOutputStream();
        return true;
      } catch (IOException unused) {
        Slog.e(Pageboost.TAG, "socket open failed for " + this.sSocketName);
        this.sSocket = null;
        return false;
      }
    }

    public void writeDaemon(ByteBuffer byteBuffer) {
      if (this.sSocket == null) {
        openSocket();
      }
      if (this.sSocket != null) {
        try {
          this.sOutputStream.write(byteBuffer.array(), 0, byteBuffer.position());
        } catch (IOException unused) {
          Slog.e(Pageboost.TAG, "Error writing to socket " + this.sSocketName);
          try {
            this.sSocket.close();
          } catch (IOException unused2) {
            Slog.e(Pageboost.TAG, "Error closing socket " + this.sSocketName);
          }
          this.sSocket = null;
        }
      }
    }
  }

  public abstract class BigDataProxy {
    public static int CNT_INFO = 10;
    public static boolean ENABLED = false;
    public static SemHqmManager mSemHqmManager;
    public static final Object sLock = new Object();

    public static String makeString() {
      return " ";
    }

    public static void initBigDataProxy() {
      synchronized (sLock) {
        if (ENABLED) {
          Slog.i(Pageboost.TAG, "bigdata init started");
          try {
            String str =
                new String(
                    Files.readAllBytes(
                        Paths.get("/data/misc/pageboost/last_bigdata_string", new String[0])));
            if (str.split(" ").length <= CNT_INFO) {
              Slog.i(Pageboost.TAG, "bigdata str: " + str);
            } else {
              Slog.e(Pageboost.TAG, "invalid bigdata file data");
            }
          } catch (Exception unused) {
            Slog.e(Pageboost.TAG, "Bigdata File is not found..");
          }
        }
      }
    }

    public static void keepLastData() {
      FileOutputStream fileOutputStream;
      OutputStreamWriter outputStreamWriter;
      if (Pageboost.mContext == null || !ENABLED) {
        return;
      }
      synchronized (sLock) {
        try {
          fileOutputStream = new FileOutputStream("/data/misc/pageboost/last_bigdata_string");
          try {
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
          } catch (Throwable th) {
            try {
              fileOutputStream.close();
            } catch (Throwable th2) {
              th.addSuppressed(th2);
            }
            throw th;
          }
        } catch (Exception e) {
          Slog.e(Pageboost.TAG, "invalid bigdata file data");
          e.printStackTrace();
        }
        try {
          Slog.i(Pageboost.TAG, "write :  ");
          outputStreamWriter.append((CharSequence) " ");
          outputStreamWriter.close();
          fileOutputStream.close();
        } finally {
        }
      }
    }

    public static void sendData() {
      if (ENABLED) {
        mSemHqmManager.sendHWParamToHQM(
            0, "Sluggish", "VRDK", "ph", "0.0", "sec", "", makeString(), "");
      }
    }
  }

  public final class PageboostAppDBHelper extends SQLiteOpenHelper {
    public PageboostAppDBHelper(Context context) {
      super(context, "/data/misc/pageboost/pageboost_app_db.db", null, 7, new AppDBErrorHandler());
    }

    public class AppDBErrorHandler implements DatabaseErrorHandler {
      @Override // android.database.DatabaseErrorHandler
      public void onCorruption(SQLiteDatabase sQLiteDatabase) {
        Slog.i(Pageboost.TAG, "appDB is deleted by AppDBErrorHandler");
        SQLiteDatabase.deleteDatabase(new File(sQLiteDatabase.getPath()));
      }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
      try {
        sQLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS AppLRU (ID INTEGER, APPNAME TEXT NOT NULL UNIQUE, CAPTURED"
                + " INTEGER, ANON INTEGER, ION INTEGER, GPU INTEGER, SIZE_PR INTEGER, SIZE_MAP_PR"
                + " INTEGER, SCORE INTEGER, EXEC_CNT INTEGER, ACC_ETIME INTEGER, PREFETCH_CNT"
                + " INTEGER, HIT_CNT INTEGER, APK_PATH TEXT NOT NULL );");
        Slog.i(Pageboost.TAG, "AppDB table creation done");
      } catch (Exception e) {
        Slog.e(Pageboost.TAG, "failed to create AppDB table");
        e.printStackTrace();
      }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
      try {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS AppLRU");
        onCreate(sQLiteDatabase);
      } catch (Exception e) {
        Slog.e(
            Pageboost.TAG, "failed to upgrade or downgrade AppDB table : from " + i + " to " + i2);
        e.printStackTrace();
      }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
      onUpgrade(sQLiteDatabase, i, i2);
    }

    public static void clearTable(SQLiteDatabase sQLiteDatabase) {
      long delete = sQLiteDatabase.delete("AppLRU", "1", null);
      Slog.i(Pageboost.TAG, "db clear : ret " + delete);
    }

    public static ContentValues setContentValue(PageboostAppInfo pageboostAppInfo, int i) {
      ContentValues contentValues = new ContentValues();
      contentValues.put("ID", Integer.valueOf(i));
      contentValues.put("APPNAME", pageboostAppInfo.mName);
      contentValues.put("CAPTURED", Integer.valueOf(pageboostAppInfo.mCaptured));
      contentValues.put("ANON", Integer.valueOf(pageboostAppInfo.mAnon));
      contentValues.put("ION", Integer.valueOf(pageboostAppInfo.mION));
      contentValues.put("GPU", Integer.valueOf(pageboostAppInfo.mGPU));
      contentValues.put("SIZE_PR", Long.valueOf(pageboostAppInfo.mSizeForPrefetch));
      contentValues.put("SIZE_MAP_PR", Long.valueOf(pageboostAppInfo.mMapCaptureSizeForPrefetch));
      contentValues.put("SCORE", Integer.valueOf(pageboostAppInfo.mScore));
      contentValues.put("EXEC_CNT", Integer.valueOf(pageboostAppInfo.mExecCnt));
      contentValues.put("ACC_ETIME", Long.valueOf(pageboostAppInfo.mAccExecTime));
      contentValues.put("PREFETCH_CNT", Integer.valueOf(pageboostAppInfo.mPrefetchCnt));
      contentValues.put("HIT_CNT", Integer.valueOf(pageboostAppInfo.mHitCnt));
      contentValues.put("APK_PATH", pageboostAppInfo.mApkPath);
      return contentValues;
    }

    public static void storeApp(
        SQLiteDatabase sQLiteDatabase,
        PageboostAppList pageboostAppList,
        PageboostAppInfo pageboostAppInfo) {
      if (pageboostAppList == null || pageboostAppInfo == null) {
        return;
      }
      synchronized (pageboostAppList.mPageboostApps) {
        ContentValues contentValue = setContentValue(pageboostAppInfo, 0);
        long update =
            sQLiteDatabase.update(
                "AppLRU", contentValue, "APPNAME = ?", new String[] {pageboostAppInfo.mName});
        if (update == 0) {
          update = sQLiteDatabase.insertWithOnConflict("AppLRU", null, contentValue, 4);
        }
        Slog.i(Pageboost.TAG, "db update :" + pageboostAppInfo.mName + " ret " + update);
      }
    }

    public static void storeAppLRU(
        SQLiteDatabase sQLiteDatabase, PageboostAppList pageboostAppList) {
      try {
        long delete = sQLiteDatabase.delete("AppLRU", "1", null);
        Slog.i(Pageboost.TAG, "db clear : ret " + delete);
        if (pageboostAppList == null) {
          return;
        }
        synchronized (pageboostAppList.mPageboostApps) {
          Iterator it = pageboostAppList.iterator();
          int i = 0;
          while (it.hasNext()) {
            PageboostAppInfo pageboostAppInfo = (PageboostAppInfo) it.next();
            int i2 = i + 1;
            long replace =
                sQLiteDatabase.replace("AppLRU", null, setContentValue(pageboostAppInfo, i));
            Slog.i(Pageboost.TAG, "db insert :" + pageboostAppInfo.mName + " ret " + replace);
            i = i2;
          }
        }
      } catch (Exception unused) {
        Slog.e(Pageboost.TAG, "failed to store app lru info");
      }
    }

    public static PageboostAppList restoreAppLRU(SQLiteDatabase sQLiteDatabase) {
      PageboostAppList pageboostAppList = new PageboostAppList();
      Cursor query = sQLiteDatabase.query("AppLRU", null, null, null, null, null, "ID ASC");
      if (query != null) {
        while (query.moveToNext()) {
          try {
            pageboostAppList.add(
                new PageboostAppInfo(
                    query.getString(query.getColumnIndexOrThrow("APPNAME")),
                    query.getInt(query.getColumnIndexOrThrow("CAPTURED")),
                    query.getInt(query.getColumnIndexOrThrow("ANON")),
                    query.getInt(query.getColumnIndexOrThrow("ION")),
                    query.getInt(query.getColumnIndexOrThrow("GPU")),
                    query.getLong(query.getColumnIndexOrThrow("SIZE_PR")),
                    query.getLong(query.getColumnIndexOrThrow("SIZE_MAP_PR")),
                    query.getInt(query.getColumnIndexOrThrow("SCORE")),
                    query.getInt(query.getColumnIndexOrThrow("EXEC_CNT")),
                    query.getLong(query.getColumnIndexOrThrow("ACC_ETIME")),
                    query.getInt(query.getColumnIndexOrThrow("PREFETCH_CNT")),
                    query.getInt(query.getColumnIndexOrThrow("HIT_CNT")),
                    query.getString(query.getColumnIndexOrThrow("APK_PATH"))),
                false);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        query.close();
      }
      return pageboostAppList;
    }
  }

  public final class PageboostFileDBHelper extends SQLiteOpenHelper {
    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {}

    public PageboostFileDBHelper(Context context) {
      super(
          context, "/data/misc/pageboost/pageboost_file_db.db", null, 3, new FileDBErrorHandler());
    }

    public class FileDBErrorHandler implements DatabaseErrorHandler {
      @Override // android.database.DatabaseErrorHandler
      public void onCorruption(SQLiteDatabase sQLiteDatabase) {
        Slog.i(Pageboost.TAG, "fileDB is deleted by FileDBErrorHandler");
        SQLiteDatabase.deleteDatabase(new File(sQLiteDatabase.getPath()));
        Slog.i(Pageboost.TAG, "appDB is deleted by FileDBErrorHandler");
        Pageboost.deleteDB("/data/misc/pageboost/pageboost_app_db.db");
      }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
      Cursor rawQuery =
          sQLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
      ArrayList arrayList = new ArrayList();
      if (rawQuery != null) {
        while (rawQuery.moveToNext()) {
          arrayList.add(rawQuery.getString(0));
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
          dropTable(sQLiteDatabase, (String) it.next());
        }
        rawQuery.close();
      }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
      onUpgrade(sQLiteDatabase, i, i2);
    }

    public static void initFileDB(SQLiteDatabase sQLiteDatabase) {
      try {
        Slog.i(Pageboost.TAG, "db pragma init");
        sQLiteDatabase.execSQL("PRAGMA synchronous = NORMAL;");
      } catch (Exception e) {
        Slog.e(Pageboost.TAG, "pragma init failed");
        e.printStackTrace();
      }
    }

    public static void dropTable(SQLiteDatabase sQLiteDatabase, String str) {
      try {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + str);
      } catch (Exception e) {
        Slog.e(Pageboost.TAG, "drop table failed : " + str);
        e.printStackTrace();
      }
    }

    public static String createTable(SQLiteDatabase sQLiteDatabase, String str) {
      File file;
      if (str == null) {
        return null;
      }
      try {
        file = new File("/data/misc/pageboost/pageboost_file_db.db");
      } catch (Exception e) {
        Slog.e(Pageboost.TAG, "create table failed : " + str);
        e.printStackTrace();
      }
      if (file.exists() && file.length() < 31457280) {
        String str2 =
            "CREATE TABLE IF NOT EXISTS "
                + str
                + " (FILENAME TEXT, OFFSET INTEGER, SIZE INTEGER, FORVRAMDISK INTEGER, BITMAP BLOB,"
                + " unique (FILENAME, OFFSET) );";
        Slog.i(Pageboost.TAG, "db create : " + str2);
        sQLiteDatabase.execSQL(str2);
        return str;
      }
      Slog.e(Pageboost.TAG, "db length : " + file.exists() + " " + file.length());
      return null;
    }

    public static void insertTable(
        SQLiteDatabase sQLiteDatabase,
        String str,
        String str2,
        int i,
        byte[] bArr,
        int i2,
        int i3) {
      if (str == null) {
        return;
      }
      ContentValues contentValues = new ContentValues();
      contentValues.put("FILENAME", str2);
      contentValues.put("OFFSET", Integer.valueOf(i));
      contentValues.put("SIZE", Integer.valueOf(i2));
      contentValues.put("FORVRAMDISK", Integer.valueOf(i3));
      contentValues.put("BITMAP", bArr);
      sQLiteDatabase.insertWithOnConflict(str, null, contentValues, 4);
    }
  }

  public static void addPackage(String str) {
    String str2;
    if (mGlobalAppLRU == null) {
      return;
    }
    PageboostAppInfo pageboostAppInfo = new PageboostAppInfo(str);
    pageboostAppInfo.mInstalled = 1;
    try {
      Context context = mContext;
      if (context != null
          && (str2 = context.getPackageManager().getApplicationInfo(str, 0).sourceDir) != null) {
        pageboostAppInfo.setApkPath(str2);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    mGlobalAppLRU.add(pageboostAppInfo, false);
    Slog.i(TAG, "add app to global @ runtime: " + pageboostAppInfo.mName);
  }

  public static void removePackage(String str) {
    PageboostAppInfo removeFromName;
    PageboostAppList pageboostAppList = mGlobalAppLRU;
    if (pageboostAppList == null
        || (removeFromName = pageboostAppList.removeFromName(str)) == null) {
      return;
    }
    PageboostFileDBHelper.dropTable(mFileDB, removeFromName.mNameTrimmed);
  }

  public static void realupdatePackages() {
    if (isPageboostMinimized()) {
      return;
    }
    if (mContext == null || mPredictor == null) {
      Slog.e(TAG, "mContext or mPredictor is not initialized yet.");
      return;
    }
    if (mGlobalAppLRU != null) {
      Slog.i(TAG, "reinit global list");
      mGlobalAppLRU = null;
    }
    PackageManager packageManager = mContext.getPackageManager();
    PageboostAppList restoreAppLRU = PageboostAppDBHelper.restoreAppLRU(mAppDB);
    for (ApplicationInfo applicationInfo : packageManager.getInstalledApplications(128)) {
      if (packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null) {
        String str = TAG;
        Slog.i(str, "pkg info : " + applicationInfo.packageName);
        PageboostAppInfo pageboostApp = restoreAppLRU.getPageboostApp(applicationInfo.packageName);
        if (pageboostApp == null) {
          PageboostAppInfo pageboostAppInfo = new PageboostAppInfo(applicationInfo.packageName);
          restoreAppLRU.add(pageboostAppInfo, false);
          Slog.i(str, "add app to global : " + applicationInfo.packageName);
          String str2 = applicationInfo.sourceDir;
          if (str2 != null) {
            pageboostAppInfo.setApkPath(str2);
            pageboostAppInfo.mInstalled = 1;
          }
        } else {
          String str3 = applicationInfo.sourceDir;
          if (str3 != null) {
            pageboostApp.setApkPath(str3);
            pageboostApp.mInstalled = 1;
          }
        }
      }
    }
    restoreAppLRU.removeNotInstalled();
    mGlobalAppLRU = restoreAppLRU;
  }

  public static void updatePackages() {
    try {
      if (isPageboostMinimized()) {
        return;
      }
      Slog.i(TAG, "Update Packages");
      sendMessage(4, null, -1, -1, -1, 10000);
    } catch (Exception unused) {
      Slog.e(TAG, "failed to updatePackages by exception");
    }
  }

  public static void onAppLaunch(Intent intent) {
    try {
      if (!isPageboostMinimized()
          && intent != null
          && "android.intent.action.MAIN".equals(intent.getAction())
          && intent.hasCategory("android.intent.category.LAUNCHER")
          && intent.getComponent() != null) {
        String packageName = intent.getComponent().getPackageName();
        Slog.i(TAG, "onAppLaunch : " + packageName);
        if (packageName == null) {
          return;
        }
        if (PageboostAppCapture.isRecordingCapture()) {
          sendMessageWithObject(14, packageName, 0);
        } else {
          sendMessageWithObject(5, packageName, 2000);
        }
        sendMessageWithObject(17, packageName, 0);
      }
    } catch (Exception unused) {
      Slog.e(TAG, "failed to onAppLaunch by exception");
    }
  }

  public static void gatherLaunchTime(String str, int i, int i2) {
    VramdiskMlockManager vramdiskMlockManager;
    if (str == null) {
      return;
    }
    try {
      if (munlock_firstapp
          && (vramdiskMlockManager = mBootFileManager) != null
          && vramdiskMlockManager.hasPinnedFile()) {
        Slog.i(TAG, "packageName " + str);
        if (!str.contains("launcher")
            && !str.equals("com.android.settings")
            && !str.equals("com.samsung.android.mtp")) {
          sendMessage(24, null, -1, -1, -1, 3000);
        }
      }
      if (isPageboostMinimized()) {
        return;
      }
      if (!str.contains("com.att.iqi")) {
        Slog.i(TAG, "Launch time gathered : pid " + i2 + " " + str + " " + i);
      }
      sendMessage(9, str, i, i2, -1, 0);
    } catch (Exception unused) {
      Slog.e(TAG, "failed to gatherLaunchTime by exception");
    }
  }

  public static void moveTaskToFront(String str) {
    try {
      if (isPageboostMinimized() || str == null) {
        return;
      }
      Slog.i(TAG, "moveTaskToFront : " + str);
      sendMessage(10, str, 0, 0, -1, 0);
    } catch (Exception unused) {
      Slog.e(TAG, "failed to moveTaskToFront by exception");
    }
  }

  public static void onProcStatusChange(int i, String str, int i2) {
    try {
      if (isPageboostMinimized()) {
        return;
      }
      sendMessage(16, str, -1, i2, i, 0);
    } catch (Exception unused) {
      Slog.e(TAG, "failed to onProcStatusChange by exception");
    }
  }

  public static void startActiveLaunch(String str) {
    try {
      if (!PAGEBOOST_ACTIVE_LAUNCH_ENABLED || isPageboostMinimized() || str == null) {
        return;
      }
      Slog.i(TAG, "start alp : " + str);
      sendMessage(19, str, -1, -1, -1, 0);
    } catch (Exception unused) {
      Slog.e(TAG, "failed to startActiveLaunch by exception");
    }
  }

  public static void stopActiveLaunch() {
    if (!PAGEBOOST_ACTIVE_LAUNCH_ENABLED || isPageboostMinimized()) {
      return;
    }
    Slog.i(TAG, "stop alp");
    sendMessage(20, null, -1, -1, -1, 0);
  }

  public static void startActiveLaunchTimeout() {
    if (!PAGEBOOST_ACTIVE_LAUNCH_ENABLED || isPageboostMinimized()) {
      return;
    }
    sendMessage(21, null, -1, -1, -1, PAGEBOOST_ACTIVE_LAUNCH_TIMEOUT);
  }

  public abstract class VramdiskLogger {
    public static int cur_idx;
    public static String[] array = new String[2000];
    public static final Object sLock = new Object();

    public static int getNextIdx() {
      int i = cur_idx;
      cur_idx = i + 1;
      return i % 2000;
    }

    public static void add(String str) {
      synchronized (sLock) {
        array[getNextIdx()] =
            new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()) + " " + str;
      }
    }

    public static void print(PrintWriter printWriter) {
      synchronized (sLock) {
        for (int i = 0; i < 2000; i++) {
          if (array[i] != null) {
            printWriter.println("[" + i + "] " + array[i]);
          }
        }
      }
    }
  }

  public static void dumpInfo(PrintWriter printWriter, String[] strArr) {
    try {
      if (isPageboostMinimized()) {
        printWriter.println(
            "minimized: "
                + Boolean.toString(PAGEBOOST_MINIMIZE)
                + ", kernel support: "
                + Boolean.toString(PAGEBOOST_KERNEL_ENABLED)
                + ", daemon support: "
                + Boolean.toString(PAGEBOOST_DAEMON_ENABLED));
        return;
      }
      if (printWriter != null && mGlobalAppLRU != null) {
        if (strArr.length > 1 && "purge".equals(strArr[1])) {
          SQLiteDatabase sQLiteDatabase = mAppDB;
          if (sQLiteDatabase != null) {
            PageboostAppDBHelper.clearTable(sQLiteDatabase);
            printWriter.println("DB is purged. Please type 'adb reboot'");
            printWriter.println(
                "(!CAUTION: Do not reboot using 'power key'. Unless DB will be restored)");
            return;
          }
          printWriter.println("app DB not exists. Try again after 5 seconds");
          return;
        }
        if (strArr.length <= 2 || !"heimdall".equals(strArr[1])) {
          printWriter.println("== Pageboost dump start ==");
          printWriter.println("");
          printWriter.println("- Pageboost Configurations");
          printWriter.println("ignore dram spec: " + PAGEBOOST_IGNORE_DRAM_SPECIFICATION);
          printWriter.println("prefetch enabled: " + PAGEBOOST_IO_PREFETCH_ENABLED);
          printWriter.println("alp enabled: " + PAGEBOOST_ACTIVE_LAUNCH_ENABLED);
          printWriter.println("prefetch level: " + PageboostAppCapture.PAGEBOOST_IO_PREFETCH_LEVEL);
          printWriter.println("prefetch app count: 1");
          printWriter.println(
              "vramdisk enabled: (config) false (decision_by_dram) " + Vramdisk.ENABLED);
          printWriter.println("memprep enabled: false");
          printWriter.println("");
          printWriter.println("- Pageboost AppLRU Info");
          mGlobalAppLRU.print(printWriter);
          printWriter.println("");
          printWriter.println("- Pageboost Vramdisk Info");
          printWriter.println("disabled");
          printWriter.println("");
          printWriter.println("- Action Log");
          VramdiskLogger.print(printWriter);
          printWriter.println(
              "- Bigdata: " + BigDataProxy.ENABLED + ",  String: " + BigDataProxy.makeString());
          printWriter.println("== pageboost dump end ==");
        }
      }
    } catch (Exception unused) {
      Slog.e(TAG, "failed to dumpInfo by exception");
    }
  }

  public static int prefetchPackage(String str, String str2) {
    try {
      if (isPageboostMinimized()) {
        return -1;
      }
      PageboostAppList pageboostAppList = mGlobalAppLRU;
      if (pageboostAppList == null) {
        Slog.e(TAG, "Pageboost pkg list is not initialized yet");
        return -1;
      }
      if (str == null) {
        Slog.e(TAG, "null pkg is requested");
        return -1;
      }
      PageboostAppInfo pageboostApp = pageboostAppList.getPageboostApp(str);
      if (pageboostApp == null) {
        Slog.i(TAG, "not in Pageboost pkg list : " + str);
        return -1;
      }
      if ("alp".equals(str2)) {
        if (!pageboostApp.activeLaunch()) {
          return -1;
        }
        startActiveLaunchTimeout();
      } else if (!pageboostApp.execute()) {
        Slog.i(TAG, "failed to prefetch. maybe not captured yet : " + str);
        return -1;
      }
      VramdiskLogger.add("prefetchRequested," + str + "," + str2);
      return 0;
    } catch (Exception unused) {
      Slog.e(TAG, "failed to prefetchPackage by exception");
      return -1;
    }
  }
}
