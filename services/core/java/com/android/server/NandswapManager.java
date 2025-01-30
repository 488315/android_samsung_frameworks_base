package com.android.server;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.FileUtils;
import android.os.Process;
import android.os.SemHqmManager;
import android.os.SystemProperties;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class NandswapManager extends JobService {
  public static final boolean SUPPORT_RAM_EXPAND_SWITCH;
  public static final BroadcastReceiver intentReceiver;
  public static final ComponentName sNandswapManager =
      new ComponentName("android", NandswapManager.class.getName());
  public static Context mContext = null;
  public static NandswapClient mClient = null;
  public static NandSwapBigdataManager mNandBigData = null;
  public static boolean isJdmDevice = false;

  static {
    SUPPORT_RAM_EXPAND_SWITCH = Build.VERSION.SEM_PLATFORM_INT >= 130100;
    intentReceiver =
        new BroadcastReceiver() { // from class: com.android.server.NandswapManager.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            boolean z;
            String action = intent.getAction();
            Slog.d("NandswapManager", "received action: " + action);
            try {
              if ("com.sec.android.intent.action.HQM_UPDATE_REQ".equals(action)) {
                if (NandswapManager.mNandBigData != null) {
                  NandSwapBigdataManager unused = NandswapManager.mNandBigData;
                  NandSwapBigdataManager.uploadBigdataToHQM();
                  return;
                }
                return;
              }
              if (!"android.intent.action.ACTION_SHUTDOWN".equals(action)
                  && !"android.intent.action.REBOOT".equals(action)) {
                if ("com.samsung.intent.action.LAZY_BOOT_COMPLETE".equals(action)) {
                  Slog.d(
                      "NandswapManager",
                      "support ramExpandSwitch: "
                          + NandswapManager.SUPPORT_RAM_EXPAND_SWITCH
                          + " for "
                          + Build.VERSION.SEM_PLATFORM_INT);
                  int ramExpandSize = NandswapManager.getRamExpandSize(context);
                  String ramExpandSizeList = NandswapManager.getRamExpandSizeList(context);
                  int i = 0;
                  if (ramExpandSize == -1) {
                    Slog.d("NandswapManager", "ram_expand_size was not set");
                    z = true;
                  } else {
                    z = false;
                  }
                  if (ramExpandSizeList == null) {
                    Slog.d("NandswapManager", "ram_expand_size_list was not set");
                    z = true;
                  }
                  if (!NandswapManager.isBackingDevSet()) {
                    Slog.d("NandswapManager", "zram backing_dev is not set");
                    z = false;
                  }
                  if (z) {
                    try {
                      i =
                          (int)
                              (((new File("/data/per_boot/zram_swap").length() / 1024) / 1024) * 4);
                    } catch (Exception unused2) {
                      Slog.e("NandswapManager", "error on file length");
                    }
                    NandswapManager.setExpandSizeAndList(context, i);
                    return;
                  }
                  return;
                }
                return;
              }
              if (NandswapManager.getRamExpandSize(context) == -1
                  && NandswapManager.isBackingDevSet()) {
                NandswapManager.putRamExpandSize(
                    context, NandswapManager.getDefaultRamExpandSize());
              }
              int ramExpandSizePersistProp = NandswapManager.getRamExpandSizePersistProp();
              if (ramExpandSizePersistProp != -1
                  && ramExpandSizePersistProp != NandswapManager.getRamExpandSize(context)) {
                NandswapManager.clearClientsBigdataInfo();
                Slog.i("NandswapManager", "Ramplus Option is changed. clear bigdata's info.");
              } else {
                NandswapManager.saveClientsBigdataInfoInReboot();
              }
            } catch (Exception e) {
              Slog.e("NandswapManager", "intent exception msg : " + e.getMessage());
            }
          }
        };
  }

  public static int getRamExpandSize(Context context) {
    return Settings.Global.getInt(context.getContentResolver(), "ram_expand_size", -1);
  }

  public static String getRamExpandSizeList(Context context) {
    return Settings.Global.getString(context.getContentResolver(), "ram_expand_size_list");
  }

  public static void putRamExpandSize(Context context, int i) {
    try {
      Settings.Global.putInt(context.getContentResolver(), "ram_expand_size", i);
    } catch (Exception unused) {
      Slog.e("NandswapManager", "error on ram_expand_size: " + i);
    }
  }

  public static void putRamExpandSizeList(Context context, String str) {
    try {
      Settings.Global.putString(context.getContentResolver(), "ram_expand_size_list", str);
    } catch (Exception unused) {
      Slog.e("NandswapManager", "error on ram_expand_size_list " + str);
    }
  }

  public static int getRamExpandSizePersistProp() {
    try {
      return SystemProperties.getInt("persist.sys.zram.ram_expand_size", -1);
    } catch (Exception unused) {
      Slog.e("NandswapManager", "error on get SystemProperties");
      return -1;
    }
  }

  public static void setRamExpandSizePersistProp(int i) {
    try {
      SystemProperties.set("persist.sys.zram.ram_expand_size", String.valueOf(i));
    } catch (Exception unused) {
      Slog.e("NandswapManager", "error on set SystemProperties: " + i);
    }
  }

  public static int getDefaultRamExpandSize() {
    int storageSize = getStorageSize();
    if (SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.higher_max_size", false)
        && storageSize >= 256) {
      return IInstalld.FLAG_FORCE;
    }
    if (storageSize > 32) {
      return IInstalld.FLAG_USE_QUOTA;
    }
    if (storageSize == 32) {
      return IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
    }
    return 0;
  }

  public static String getAvailSizeList() {
    int totalMemory = (int) (Process.getTotalMemory() >> 30);
    return totalMemory < 4 ? "2,4" : totalMemory < 6 ? "2,4,6" : "2,4,6,8";
  }

  public static boolean isValidRamExpandSize(int i) {
    String availSizeList = getAvailSizeList();
    if (i != 0) {
      return i % 1024 == 0 && availSizeList.indexOf(String.valueOf(i / 1024)) != -1;
    }
    return true;
  }

  public static void testRestoreExpandSizeAndList(Context context) {
    int ramExpandSize = getRamExpandSize(context);
    if (ramExpandSize == -1) {
      return;
    }
    if (!SUPPORT_RAM_EXPAND_SWITCH) {
      Slog.d("NandswapManager", "no ramExpandSwitch for version " + Build.VERSION.SEM_PLATFORM_INT);
      int defaultRamExpandSize = getDefaultRamExpandSize();
      if (ramExpandSize != defaultRamExpandSize) {
        putRamExpandSize(context, defaultRamExpandSize);
        ramExpandSize = defaultRamExpandSize;
      }
      Slog.d("NandswapManager", "ramExpandSizeMb: " + ramExpandSize);
      return;
    }
    String availSizeList = getAvailSizeList();
    if (!availSizeList.equals(getRamExpandSizeList(context))) {
      putRamExpandSizeList(context, availSizeList);
    }
    if (!isValidRamExpandSize(ramExpandSize)) {
      ramExpandSize = getDefaultRamExpandSize();
      putRamExpandSize(context, ramExpandSize);
    }
    setRamExpandSizePersistProp(ramExpandSize);
    Slog.d("NandswapManager", "ramExpandSizeMb: " + ramExpandSize + " avail: " + availSizeList);
  }

  public static void setExpandSizeAndList(Context context, int i) {
    boolean z;
    int storageSize = getStorageSize();
    boolean z2 = false;
    if (storageSize < 32) {
      Slog.d("NandswapManager", "no ramExpandSwitch for low storage " + storageSize);
      z = false;
    } else {
      z = true;
    }
    if (SUPPORT_RAM_EXPAND_SWITCH) {
      z2 = z;
    } else {
      Slog.d("NandswapManager", "no ramExpandSwitch for version " + Build.VERSION.SEM_PLATFORM_INT);
    }
    putRamExpandSize(context, i);
    setRamExpandSizePersistProp(i);
    if (z2) {
      String availSizeList = getAvailSizeList();
      Slog.d("NandswapManager", "ramExpandSizeMb: " + i + " avail: " + availSizeList);
      putRamExpandSizeList(context, availSizeList);
    }
  }

  public static boolean isBackingDevSet() {
    try {
      return !"none"
          .equals(FileUtils.readTextFile(new File("/sys/block/zram0/backing_dev"), 128, "").trim());
    } catch (IOException unused) {
      Slog.w("NandswapManager", "exception on checking backing_dev /sys/block/zram0/backing_dev");
      return false;
    }
  }

  public static boolean isRemainStorageLifeTime() {
    String str = "/sys/class/scsi_host/host0/lt";
    File file = new File("/sys/class/scsi_host/host0/lt");
    File file2 = new File("/sys/class/sec/ufs/lt");
    if (!file.exists()) {
      str = file2.exists() ? "/sys/class/sec/ufs/lt" : "/sys/block/mmcblk0/device/life_time";
    }
    Slog.d("NandswapManager", "try to check lifetime via " + str);
    try {
      for (String str2 : FileUtils.readTextFile(new File(str), 16, "").trim().split("\\s+")) {
        int parseInt = Integer.parseInt(str2.replaceFirst("0x", ""), 16);
        if (parseInt > 8) {
          Slog.e(
              "NandswapManager",
              String.format(
                  Locale.US, "abort: lifetime below 20%%(val=%d)", Integer.valueOf(parseInt)));
          return false;
        }
      }
      return true;
    } catch (Exception unused) {
      Slog.e("NandswapManager", "Failed to read " + str);
      return false;
    }
  }

  public static boolean isUfs() {
    try {
    } catch (Exception unused) {
      Slog.w("NandswapManager", "Failed to read /sys/class/scsi_host/host0/proc_name");
    }
    if (new File("/sys/class/sec/ufs/un").exists()) {
      return true;
    }
    if (new File("/sys/class/sec/mmc/un").exists()) {
      return false;
    }
    File file = new File("/sys/class/scsi_host/host0/proc_name");
    if (file.exists()) {
      if ("ufshcd".equals(FileUtils.readTextFile(file, 64, "").trim())) {
        return true;
      }
    }
    return false;
  }

  public static int getStorageSize() {
    int i;
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/partitions"));
      try {
        String str = isUfs() ? ".*(sda|sdc)$" : ".*(mmcblk0)$";
        i = 0;
        while (true) {
          String readLine = bufferedReader.readLine();
          if (readLine == null) {
            break;
          }
          if (readLine.matches(str)) {
            String[] split = readLine.trim().split("\\s+");
            if (split.length == 4) {
              i = Math.max(Integer.parseInt(split[2]), i);
            }
          }
        }
        bufferedReader.close();
      } finally {
      }
    } catch (Exception unused) {
      Slog.e("NandswapManager", "Failed to read storage size from /proc/partitions");
      i = 0;
    }
    if (i == 0) {
      return 0;
    }
    return 1 << (((int) (Math.log(i / 1048576) / Math.log(2.0d))) + 1);
  }

  public static int selectDailyQuota() {
    HashMap hashMap = new HashMap();
    HashMap hashMap2 = new HashMap();
    int[] iArr = {0, 0, 8, 12};
    int[] iArr2 = {0, 1, 8, 12};
    int[] iArr3 = {16, 32, 64, 128};
    int storageSize = getStorageSize();
    if (isJdmDevice) {
      Slog.i("NandswapManager", "This device is JDM model");
      if (storageSize >= 64) {
        return 262144;
      }
      if (storageSize == 32) {
        return IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
      }
      return 0;
    }
    Slog.i("NandswapManager", "This device is in-house model");
    for (int i = 0; i < 4; i++) {
      hashMap.put(Integer.valueOf(iArr3[i]), Integer.valueOf(iArr[i]));
      hashMap2.put(Integer.valueOf(iArr3[i]), Integer.valueOf(iArr2[i]));
    }
    int i2 = storageSize <= 128 ? storageSize : 128;
    int i3 = 16 <= i2 ? i2 : 16;
    try {
      return isUfs()
          ? ((Integer) hashMap.get(Integer.valueOf(i3))).intValue() * 262144
          : ((Integer) hashMap2.get(Integer.valueOf(i3))).intValue() * 262144;
    } catch (NullPointerException unused) {
      Slog.w("NandswapManager", "Storage size is not in Quota table! size:" + Integer.toString(i3));
      return 262144;
    }
  }

  public static void initNandswapClient() {
    Slog.i("NandswapManager", "Init Nandswap Client");
    if (isRemainStorageLifeTime()) {
      int selectDailyQuota = selectDailyQuota();
      if (SystemProperties.getInt("persist.sys.zram.daily_quota", -1) == -1) {
        SystemProperties.set("persist.sys.zram.daily_quota", Integer.toString(selectDailyQuota));
        Slog.i("NandswapManager", "First boot, set daily quota...");
      }
      if (SystemProperties.getInt("persist.sys.zram.daily_quota_remain", -1) == -1) {
        SystemProperties.set(
            "persist.sys.zram.daily_quota_remain", Integer.toString(selectDailyQuota));
        Slog.i("NandswapManager", "First boot, set daily quota remaining...");
      }
      int i = SystemProperties.getInt("persist.sys.zram.daily_quota", 262144);
      int i2 = i * 3;
      if (mClient.isNandswapEnabled()) {
        NandswapClient nandswapClient = mClient;
        nandswapClient.dailyQuota = i;
        nandswapClient.dailyQuotaLimit = i2;
        if (nandswapClient.getQuotaSysNode() == 0) {
          mClient.setQuotaSysNode(mClient.getQuotaRemainingProp(0));
        }
      }
    }
  }

  public static void resetClientQuotas() {
    Slog.i("NandswapManager", "Reset Client Quotas");
    if (mClient.isNandswapEnabled()) {
      int quotaSysNode = mClient.getQuotaSysNode();
      NandswapClient nandswapClient = mClient;
      int i = quotaSysNode + nandswapClient.dailyQuota;
      int i2 = nandswapClient.dailyQuotaLimit;
      if (i > i2) {
        i = i2;
      }
      nandswapClient.setQuotaSysNode(i);
    }
  }

  public static void saveClientsStorageUsage() {
    if (mClient.isNandswapEnabled()) {
      mClient.saveStorageUsage();
    }
  }

  public static void saveClientsBigdataInfoInReboot() {
    if (mClient.isNandswapEnabled()) {
      mClient.setBigdataInfoProp();
    }
  }

  public static void clearClientsBigdataInfo() {
    if (mClient.isNandswapEnabled()) {
      mClient.initBigdataInfoProp();
    }
  }

  public static void schedNextLimitReset() {
    JobScheduler jobScheduler = (JobScheduler) mContext.getSystemService("jobscheduler");
    JobInfo.Builder builder = new JobInfo.Builder(813, sNandswapManager);
    TimeUnit timeUnit = TimeUnit.HOURS;
    jobScheduler.schedule(
        builder
            .setMinimumLatency(timeUnit.toMillis(24L))
            .setOverrideDeadline(timeUnit.toMillis(24L))
            .build());
  }

  public static void schedNextUpdateAvgerage() {
    JobScheduler jobScheduler = (JobScheduler) mContext.getSystemService("jobscheduler");
    JobInfo.Builder builder = new JobInfo.Builder(814, sNandswapManager);
    TimeUnit timeUnit = TimeUnit.HOURS;
    jobScheduler.schedule(
        builder
            .setMinimumLatency(timeUnit.toMillis(4L))
            .setOverrideDeadline(timeUnit.toMillis(4L))
            .build());
  }

  public static boolean makeNandswapClient() {
    try {
      mClient = new NandswapClient();
      initNandswapClient();
      return true;
    } catch (Exception e) {
      Slog.e("NandswapManager", "Unexpected error while create mClient: " + e.getMessage());
      mClient = null;
      return false;
    }
  }

  public static boolean makeNandswapBigdataManager(Context context) {
    try {
      mNandBigData = new NandSwapBigdataManager(context);
      return true;
    } catch (Exception e) {
      Slog.e("NandswapManager", "Unexpected error while create bigdataManager: " + e.getMessage());
      mNandBigData = null;
      return false;
    }
  }

  public static void scheduleNandswapManager(Context context) {
    try {
      Slog.d("NandswapManager", "Initialize NandswapManager...");
      if (makeNandswapClient()) {
        if (!makeNandswapBigdataManager(context)) {
          Slog.e("NandswapManager", "Bigdata is not supported");
        }
        mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sec.android.intent.action.HQM_UPDATE_REQ");
        intentFilter.addAction("android.intent.action.REBOOT");
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("com.samsung.intent.action.LAZY_BOOT_COMPLETE");
        mContext.registerReceiver(intentReceiver, intentFilter);
        testRestoreExpandSizeAndList(context);
        if (isRemainStorageLifeTime()) {
          schedNextLimitReset();
          schedNextUpdateAvgerage();
        }
      }
    } catch (Exception e) {
      Slog.e("NandswapManager", "Unexpected error while scheduleNandswapManager");
      e.printStackTrace();
    }
  }

  @Override // android.app.job.JobService
  public boolean onStartJob(JobParameters jobParameters) {
    Slog.d("NandswapManager", "onStartJob");
    try {
    } catch (Exception e) {
      Slog.e("NandswapManager", "scheduler exception occurred : " + e.getMessage());
    }
    if (!isRemainStorageLifeTime()) {
      jobFinished(jobParameters, false);
      return false;
    }
    if (jobParameters.getJobId() == 813) {
      resetClientQuotas();
      schedNextLimitReset();
    } else if (jobParameters.getJobId() == 814) {
      saveClientsStorageUsage();
      schedNextUpdateAvgerage();
    }
    jobFinished(jobParameters, false);
    return false;
  }

  @Override // android.app.job.JobService
  public boolean onStopJob(JobParameters jobParameters) {
    Slog.d("NandswapManager", "onStopJob");
    return false;
  }

  public static final HashMap getMemInfo() {
    HashMap hashMap = new HashMap();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"));
      while (true) {
        try {
          String readLine = bufferedReader.readLine();
          if (readLine == null) {
            break;
          }
          int indexOf = readLine.indexOf(XmlUtils.STRING_ARRAY_SEPARATOR);
          if (indexOf > 0) {
            hashMap.put(
                readLine.substring(0, indexOf),
                Integer.valueOf(
                    Integer.parseInt(readLine.substring(indexOf).replaceAll("\\D+", ""))));
          }
        } finally {
        }
      }
      bufferedReader.close();
    } catch (Exception unused) {
      Slog.e("NandswapManager", "Failed to read stats from /proc/meminfo");
      hashMap.clear();
    }
    return hashMap;
  }

  public class NandswapClient {
    public ProcessingManager normalStat;
    public ProcessingManager pprStat;
    public int dailyQuota = 0;
    public int dailyQuotaLimit = 0;
    public int state = -1;
    public boolean supportBigdataState = false;
    public HashMap bdStatMap = new HashMap();
    public HashMap bigdataPersistPropMap = new HashMap();

    public NandswapClient() {
      this.normalStat = new ProcessingManager();
      this.pprStat = new ProcessingManager();
      initNandswapState();
      initBigdataState();
      setPreviousInfo();
    }

    public final boolean isWritebackEnabled() {
      try {
        File file = new File("/sys/block/zram0/backing_dev");
        if ("none".equals(FileUtils.readTextFile(file, 128, "").trim()) && !file.exists()) {
          Slog.w("NandswapManager", "Writeback device is not set");
          return false;
        }
        return true;
      } catch (IOException unused) {
        Slog.w("NandswapManager", "Writeback is not enabled on /sys/block/zram0/backing_dev");
        return false;
      }
    }

    public void initNandswapState() {
      if (isWritebackEnabled()) {
        this.state = 1;
      } else {
        this.state = 0;
      }
    }

    public boolean isNandswapEnabled() {
      return this.state == 1;
    }

    public void initBigdataState() {
      try {
        if (FileUtils.readTextFile(
                    new File("/sys/block/zram0/bd_stat"), IInstalld.FLAG_USE_QUOTA, "")
                .trim()
                .split("\\s+")
                .length
            >= 10) {
          this.supportBigdataState = true;
        } else {
          this.supportBigdataState = false;
        }
      } catch (IOException unused) {
        Slog.e("NandswapManager", "Failed to read stats from /sys/block/zram0/bd_stat");
      }
    }

    public boolean isSupportBigdata() {
      return this.supportBigdataState;
    }

    public int getQuotaRemainingProp(int i) {
      return SystemProperties.getInt("persist.sys.zram.daily_quota_remain", i);
    }

    public int getQuotaSysNode() {
      try {
        return Integer.parseInt(
            FileUtils.readTextFile(new File("/sys/block/zram0/writeback_limit"), 128, "").trim());
      } catch (Exception unused) {
        Slog.e("NandswapManager", "Failed to read quota from /sys/block/zram0/writeback_limit");
        return 0;
      }
    }

    public HashMap getBdStat() {
      String[] strArr = {
        "expire",
        "count",
        "reads",
        "writes",
        "objcnt",
        "size",
        "max_count",
        "max_size",
        "ppr_count",
        "ppr_reads",
        "ppr_writes",
        "ppr_objcnt",
        "ppr_size",
        "ppr_max_count",
        "ppr_max_size",
        "objreads",
        "objwrites"
      };
      this.bdStatMap.clear();
      try {
        String[] split =
            FileUtils.readTextFile(
                    new File("/sys/block/zram0/bd_stat"), IInstalld.FLAG_USE_QUOTA, "")
                .trim()
                .split("\\s+");
        for (int i = 0; i < split.length; i++) {
          this.bdStatMap.put(strArr[i], Integer.valueOf(Integer.parseInt(split[i])));
        }
      } catch (IOException unused) {
        Slog.e("NandswapManager", "Failed to read stats from /sys/block/zram0/bd_stat");
      }
      return this.bdStatMap;
    }

    public void resetBdStatSysNode() {
      try {
        FileUtils.stringToFile(new File("/sys/block/zram0/bd_stat"), String.valueOf(0));
      } catch (IOException unused) {
        Slog.e("NandswapManager", "Failed to reset stats from /sys/block/zram0/bd_stat");
      }
    }

    public void setQuotaSysNode(int i) {
      try {
        FileUtils.stringToFile(new File("/sys/block/zram0/writeback_limit"), String.valueOf(i));
      } catch (IOException unused) {
        Slog.e("NandswapManager", "Failed to write new quota to /sys/block/zram0/writeback_limit");
      }
    }

    public void saveStorageUsage() {
      if (this.supportBigdataState) {
        HashMap bdStat = getBdStat();
        this.normalStat.setCount(((Integer) bdStat.get("count")).intValue());
        this.normalStat.setSize(((Integer) bdStat.get("size")).intValue());
        this.pprStat.setCount(((Integer) bdStat.get("ppr_count")).intValue());
        this.pprStat.setSize(((Integer) bdStat.get("ppr_size")).intValue());
        Slog.i("NandswapManager", "Saved storage usage successfully!!");
      }
    }

    public double getCountAverage() {
      if (this.supportBigdataState) {
        return this.normalStat.calcCountAverage();
      }
      return 0.0d;
    }

    public double getSizeAverage() {
      if (this.supportBigdataState) {
        return this.normalStat.calcSizeAverage();
      }
      return 0.0d;
    }

    public double getPprCountAverage() {
      if (this.supportBigdataState) {
        return this.pprStat.calcCountAverage();
      }
      return 0.0d;
    }

    public double getPprSizeAverage() {
      if (this.supportBigdataState) {
        return this.pprStat.calcSizeAverage();
      }
      return 0.0d;
    }

    public boolean isExistBigdataInfoProp() {
      return this.supportBigdataState
          && !SystemProperties.get("persist.sys.zram0.bigdata_info", "None").equals("None");
    }

    public HashMap getBigdataInfoProp() {
      String[] strArr = {
        "reads",
        "writes",
        "max_count",
        "max_size",
        "ppr_reads",
        "ppr_writes",
        "ppr_max_count",
        "ppr_max_size",
        "objreads",
        "objwrites"
      };
      this.bigdataPersistPropMap.clear();
      if (this.supportBigdataState) {
        String str = SystemProperties.get("persist.sys.zram0.bigdata_info", "None");
        if (!str.equals("None")) {
          String[] split = str.split(",");
          for (int i = 0; i < 10; i++) {
            try {
              this.bigdataPersistPropMap.put(
                  strArr[i], Integer.valueOf(Integer.parseInt(split[i])));
            } catch (Exception e) {
              Slog.d("NandswapManager", "getBigdataInfo parseInt err - " + e.getMessage());
              this.bigdataPersistPropMap.clear();
              for (int i2 = 0; i2 < 10; i2++) {
                this.bigdataPersistPropMap.put(strArr[i2], 0);
              }
            }
          }
        }
      }
      return this.bigdataPersistPropMap;
    }

    public void initBigdataInfoProp() {
      if (this.supportBigdataState) {
        try {
          SystemProperties.set("persist.sys.zram0.bigdata_info", "0,0,0,0,0,0,0,0,0,0");
        } catch (Exception unused) {
          Slog.e("NandswapManager", "Failed to init Bigdata Info...");
        }
      }
    }

    public void setBigdataInfoProp() {
      if (this.supportBigdataState) {
        HashMap bigdataInfoProp = getBigdataInfoProp();
        HashMap bdStat = getBdStat();
        if (bigdataInfoProp.isEmpty() || bdStat.isEmpty()) {
          Slog.e(
              "NandswapManager",
              String.format(
                  Locale.US,
                  "%s(%d) or bd_stat(%d) is empty...",
                  "persist.sys.zram0.bigdata_info",
                  Integer.valueOf(bigdataInfoProp.size()),
                  Integer.valueOf(bdStat.size())));
          return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("");
        Locale locale = Locale.US;
        sb.append(
            String.format(
                locale,
                "%d,",
                Integer.valueOf(
                    ((Integer) bigdataInfoProp.get("reads")).intValue()
                        + (((Integer) bdStat.get("reads")).intValue()
                            - this.normalStat.getPreviousReads()))));
        try {
          SystemProperties.set(
              "persist.sys.zram0.bigdata_info",
              ((((((((sb.toString()
                                                  + String.format(
                                                      locale,
                                                      "%d,",
                                                      Integer.valueOf(
                                                          ((Integer) bigdataInfoProp.get("writes"))
                                                                  .intValue()
                                                              + (((Integer) bdStat.get("writes"))
                                                                      .intValue()
                                                                  - this.normalStat
                                                                      .getPreviousWrites()))))
                                              + String.format(
                                                  locale,
                                                  "%d,",
                                                  Integer.valueOf(
                                                      Math.max(
                                                          ((Integer)
                                                                  bigdataInfoProp.get("max_count"))
                                                              .intValue(),
                                                          ((Integer) bdStat.get("max_count"))
                                                              .intValue()))))
                                          + String.format(
                                              locale,
                                              "%d,",
                                              Integer.valueOf(
                                                  Math.max(
                                                      ((Integer) bigdataInfoProp.get("max_size"))
                                                          .intValue(),
                                                      ((Integer) bdStat.get("max_size"))
                                                          .intValue()))))
                                      + String.format(
                                          locale,
                                          "%d,",
                                          Integer.valueOf(
                                              ((Integer) bigdataInfoProp.get("ppr_reads"))
                                                      .intValue()
                                                  + (((Integer) bdStat.get("ppr_reads")).intValue()
                                                      - this.pprStat.getPreviousReads()))))
                                  + String.format(
                                      locale,
                                      "%d,",
                                      Integer.valueOf(
                                          ((Integer) bigdataInfoProp.get("ppr_writes")).intValue()
                                              + (((Integer) bdStat.get("ppr_writes")).intValue()
                                                  - this.pprStat.getPreviousWrites()))))
                              + String.format(
                                  locale,
                                  "%d,",
                                  Integer.valueOf(
                                      Math.max(
                                          ((Integer) bigdataInfoProp.get("ppr_max_count"))
                                              .intValue(),
                                          ((Integer) bdStat.get("ppr_max_count")).intValue()))))
                          + String.format(
                              locale,
                              "%d,",
                              Integer.valueOf(
                                  Math.max(
                                      ((Integer) bigdataInfoProp.get("ppr_max_size")).intValue(),
                                      ((Integer) bdStat.get("ppr_max_size")).intValue()))))
                      + String.format(
                          locale,
                          "%d,",
                          Integer.valueOf(
                              ((Integer) bigdataInfoProp.get("objreads")).intValue()
                                  + (((Integer) bdStat.get("objreads")).intValue()
                                      - this.normalStat.getPreviousObjReads()))))
                  + String.format(
                      locale,
                      "%d",
                      Integer.valueOf(
                          ((Integer) bigdataInfoProp.get("objwrites")).intValue()
                              + (((Integer) bdStat.get("objwrites")).intValue()
                                  - this.normalStat.getPreviousObjWrites()))));
          Slog.i("NandswapManager", "Saved Bigdata Info successfully!!");
        } catch (Exception unused) {
          Slog.e("NandswapManager", "Failed to saved Bigdata Info...");
        }
      }
    }

    public void setPreviousInfo() {
      if (this.supportBigdataState) {
        HashMap bdStat = getBdStat();
        this.normalStat.setPreviousValue(
            ((Integer) bdStat.get("reads")).intValue(),
            ((Integer) bdStat.get("writes")).intValue(),
            ((Integer) bdStat.get("objreads")).intValue(),
            ((Integer) bdStat.get("objwrites")).intValue());
        this.pprStat.setPreviousValue(
            ((Integer) bdStat.get("ppr_reads")).intValue(),
            ((Integer) bdStat.get("ppr_writes")).intValue(),
            ((Integer) bdStat.get("objreads")).intValue(),
            ((Integer) bdStat.get("objwrites")).intValue());
      }
    }

    public void clearAverageList() {
      if (this.supportBigdataState) {
        this.normalStat.clearAllList();
        this.pprStat.clearAllList();
      }
    }
  }

  public final class ProcessingManager {
    public ArrayList countList;
    public int objReads;
    public int objWrites;
    public int reads;
    public ArrayList sizeList;
    public int writes;

    public ProcessingManager() {
      this.countList = new ArrayList();
      this.sizeList = new ArrayList();
      this.reads = 0;
      this.writes = 0;
      this.objReads = 0;
      this.objWrites = 0;
    }

    public final void setCount(int i) {
      this.countList.add(Integer.valueOf(i));
    }

    public final void setSize(int i) {
      this.sizeList.add(Integer.valueOf(i));
    }

    public final double calcCountAverage() {
      return calcAverage(1);
    }

    public final double calcSizeAverage() {
      return calcAverage(2);
    }

    public final void clearAllList() {
      this.countList.clear();
      this.sizeList.clear();
    }

    public final double calcAverage(int i) {
      double d;
      double d2;
      if (i == 1) {
        d = this.countList.size();
        d2 = 0.0d;
        while (this.countList.iterator().hasNext()) {
          d2 += ((Integer) r9.next()).intValue();
        }
      } else if (i == 2) {
        d = this.sizeList.size();
        d2 = 0.0d;
        while (this.sizeList.iterator().hasNext()) {
          d2 += ((Integer) r9.next()).intValue();
        }
      } else {
        d = 0.0d;
        d2 = 0.0d;
      }
      if (d == 0.0d) {
        return 0.0d;
      }
      return d2 / d;
    }

    public final void setPreviousValue(int i, int i2, int i3, int i4) {
      this.reads = i;
      this.writes = i2;
      this.objReads = i3;
      this.objWrites = i4;
    }

    public final int getPreviousReads() {
      return this.reads;
    }

    public final int getPreviousWrites() {
      return this.writes;
    }

    public final int getPreviousObjReads() {
      return this.objReads;
    }

    public final int getPreviousObjWrites() {
      return this.objWrites;
    }
  }

  public final class NandSwapBigdataManager {
    public static final String[] bigdataJsonFormat = {
      "count_average",
      "size_average",
      "reads",
      "writes",
      "objcnt",
      "max_count",
      "max_size",
      "ppr_count_average",
      "ppr_size_average",
      "ppr_reads",
      "ppr_writes",
      "ppr_objcnt",
      "ppr_max_count",
      "ppr_max_size",
      "objreads",
      "objwrites",
      "gpu_total_mem",
      "gpu_reclaimed_mem",
      "swap_used"
    };
    public static Context mContext;
    public static SemHqmManager semHqmManager;

    public NandSwapBigdataManager(Context context) {
      mContext = context;
      semHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
      if (!NandswapManager.mClient.isSupportBigdata()
          || NandswapManager.mClient.isExistBigdataInfoProp()) {
        return;
      }
      NandswapManager.mClient.initBigdataInfoProp();
    }

    public static void uploadBigdataToHQM() {
      if (semHqmManager == null) {
        Slog.e("NandswapManager", "Bigdata semHqmManager isn't initialized...");
        return;
      }
      if (NandswapManager.mClient.isSupportBigdata()) {
        Slog.i("NandswapManager", "Upload Bigdata...");
        semHqmManager.sendHWParamToHQM(
            0, "AP", "NSST", "sm", "0.0", "sec", "", makeJsonFormat(), "");
        Slog.i("NandswapManager", "Initialize bigdata information...");
        NandswapManager.mClient.initBigdataInfoProp();
        NandswapManager.mClient.clearAverageList();
        NandswapManager.mClient.setPreviousInfo();
        NandswapManager.mClient.resetBdStatSysNode();
      }
    }

    public static String makeJsonFormat() {
      String format;
      HashMap bdStat = NandswapManager.mClient.getBdStat();
      HashMap bigdataInfoProp = NandswapManager.mClient.getBigdataInfoProp();
      HashMap memInfo = NandswapManager.getMemInfo();
      if (bigdataInfoProp.isEmpty() || bdStat.isEmpty() || memInfo.isEmpty()) {
        Slog.e(
            "NandswapManager",
            String.format(
                Locale.US,
                "%s(%d) or bd_stat(%d) or meminfo(%d) is empty...",
                "persist.sys.zram0.bigdata_info",
                Integer.valueOf(bigdataInfoProp.size()),
                Integer.valueOf(bdStat.size()),
                Integer.valueOf(memInfo.size())));
        return "";
      }
      String str = "\"option\":\"" + NandswapManager.getRamExpandSizePersistProp() + "\",";
      for (String str2 : bigdataJsonFormat) {
        if (str2.equals("count_average")) {
          format =
              String.format(
                  Locale.US, "%.3f", Double.valueOf(NandswapManager.mClient.getCountAverage()));
        } else if (str2.equals("size_average")) {
          format =
              String.format(
                  Locale.US, "%.3f", Double.valueOf(NandswapManager.mClient.getSizeAverage()));
        } else if (str2.equals("ppr_count_average")) {
          format =
              String.format(
                  Locale.US, "%.3f", Double.valueOf(NandswapManager.mClient.getPprCountAverage()));
        } else if (str2.equals("ppr_size_average")) {
          format =
              String.format(
                  Locale.US, "%.3f", Double.valueOf(NandswapManager.mClient.getPprSizeAverage()));
        } else if (str2.equals("objcnt") || str2.equals("ppr_objcnt")) {
          format = String.format(Locale.US, "%d", bdStat.get(str2));
        } else if (str2.equals("max_count")
            || str2.equals("max_size")
            || str2.equals("ppr_max_count")
            || str2.equals("ppr_max_size")) {
          format =
              String.format(
                  Locale.US,
                  "%d",
                  Integer.valueOf(
                      Math.max(
                          ((Integer) bigdataInfoProp.get(str2)).intValue(),
                          ((Integer) bdStat.get(str2)).intValue())));
        } else if (str2.equals("reads")) {
          format =
              String.format(
                  Locale.US,
                  "%d",
                  Integer.valueOf(
                      ((Integer) bigdataInfoProp.get(str2)).intValue()
                          + (((Integer) bdStat.get(str2)).intValue()
                              - NandswapManager.mClient.normalStat.getPreviousReads())));
        } else if (str2.equals("writes")) {
          format =
              String.format(
                  Locale.US,
                  "%d",
                  Integer.valueOf(
                      ((Integer) bigdataInfoProp.get(str2)).intValue()
                          + (((Integer) bdStat.get(str2)).intValue()
                              - NandswapManager.mClient.normalStat.getPreviousWrites())));
        } else if (str2.equals("ppr_reads")) {
          format =
              String.format(
                  Locale.US,
                  "%d",
                  Integer.valueOf(
                      ((Integer) bigdataInfoProp.get(str2)).intValue()
                          + (((Integer) bdStat.get(str2)).intValue()
                              - NandswapManager.mClient.pprStat.getPreviousReads())));
        } else if (str2.equals("ppr_writes")) {
          format =
              String.format(
                  Locale.US,
                  "%d",
                  Integer.valueOf(
                      ((Integer) bigdataInfoProp.get(str2)).intValue()
                          + (((Integer) bdStat.get(str2)).intValue()
                              - NandswapManager.mClient.pprStat.getPreviousWrites())));
        } else if (str2.equals("objreads")) {
          format =
              String.format(
                  Locale.US,
                  "%d",
                  Integer.valueOf(
                      ((Integer) bigdataInfoProp.get(str2)).intValue()
                          + (((Integer) bdStat.get(str2)).intValue()
                              - NandswapManager.mClient.normalStat.getPreviousObjReads())));
        } else if (str2.equals("objwrites")) {
          format =
              String.format(
                  Locale.US,
                  "%d",
                  Integer.valueOf(
                      ((Integer) bigdataInfoProp.get(str2)).intValue()
                          + (((Integer) bdStat.get(str2)).intValue()
                              - NandswapManager.mClient.normalStat.getPreviousObjWrites())));
        } else if (str2.equals("gpu_total_mem")) {
          if (memInfo.get("KgslShmemUsage") != null) {
            format = String.valueOf(memInfo.get("KgslShmemUsage"));
          }
          format = "-1";
        } else if (str2.equals("gpu_reclaimed_mem")) {
          if (memInfo.get("KgslReclaimed") != null) {
            format = String.valueOf(memInfo.get("KgslReclaimed"));
          }
          format = "-1";
        } else {
          if (!str2.equals("swap_used")) {
            return "";
          }
          format =
              String.valueOf(
                  ((Integer) memInfo.get("SwapTotal")).intValue()
                      - ((Integer) memInfo.get("SwapFree")).intValue());
        }
        str = str + "\"" + str2 + "\":\"" + format + "\",";
      }
      return str.length() != 0 ? str.substring(0, str.length() - 1) : str;
    }
  }
}
