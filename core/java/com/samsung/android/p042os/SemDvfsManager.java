package com.samsung.android.p042os;

import android.content.Context;
import android.hardware.scontext.SContextConstants;
import android.p009os.CustomFrequencyManager;
import android.p009os.IBinder;
import android.p009os.Process;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.SystemProperties;
import android.util.Log;
import com.sec.android.sdhms.ISamsungDeviceHealthManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

/* loaded from: classes5.dex */
public abstract class SemDvfsManager {
  private static final int ACQUIRE_PAIR_SIZE = 2;
  public static final int FREQUENCY_LIST_ALL = 0;
  public static final int FREQUENCY_LIST_DEFAULT = 1;
  public static final String HINT_AMS_ACT_LAZY = "AMS_ACT_LAZY";
  public static final String HINT_AMS_ACT_RESUME = "AMS_ACT_RESUME";
  public static final String HINT_AMS_ACT_START = "AMS_ACT_START";
  public static final String HINT_AMS_APP_HOME = "AMS_APP_HOME";
  public static final String HINT_AMS_APP_SWITCH = "AMS_APP_SWITCH";
  public static final String HINT_AMS_RELAUNCH_RESUME = "AMS_RELAUNCH_RESUME";
  public static final String HINT_AMS_RESUME = "AMS_RESUME";
  public static final String HINT_AMS_RESUME_TAIL = "AMS_RESUME_TAIL";
  public static final String HINT_AMS_RESUME_TAIL_CSTATE = "AMS_RESUME_TAIL_CSTATE";

  @Deprecated public static final String HINT_APP_LAUNCH = "APP_LAUNCH";

  @Deprecated public static final String HINT_BADGE_UPDATE = "BADGE_UPDATE";

  @Deprecated public static final String HINT_BROWSER_FLING = "BROWSER_FLING";

  @Deprecated public static final String HINT_BROWSER_TOUCH = "BROWSER_TOUCH";

  @Deprecated public static final String HINT_CONTACT_SCROLL = "CONTACT_SCROLL";
  public static final String HINT_DEVICE_WAKEUP = "DEVICE_WAKEUP";

  @Deprecated public static final String HINT_GALLERY_SCROLL = "GALLERY_SCROLL";

  @Deprecated public static final String HINT_GALLERY_TOUCH = "GALLERY_TOUCH";

  @Deprecated public static final String HINT_GALLERY_TOUCH_TAIL = "GALLERY_TOUCH_TAIL";
  public static final String HINT_GESTURE_DETECTED = "GESTURE_DETECTED";
  public static final String HINT_GESTURE_DETECTED_HRR = "GESTURE_DETECTED_HRR";

  @Deprecated public static final String HINT_HOME_KEY_TOUCH = "HOME_KEY_TOUCH";

  @Deprecated public static final String HINT_LAUNCHER_TOUCH = "LAUNCHER_TOUCH";
  public static final String HINT_LISTVIEW_SCROLL = "LISTVIEW_SCROLL";
  public static final String HINT_PWM_ROTATION = "PWM_ROTATION";
  public static final String HINT_SAMSUNG_KEYBOARD_FIRST_SHOW = "SKBD_FIRST_SHOW";
  public static final String HINT_SAMSUNG_KEYBOARD_RE_ENTER_SHOW = "SKBD_RE_ENTER_SHOW";
  public static final String HINT_SMART_VIEW_NORMAL = "SMART_VIEW_NORMAL";
  public static final String HINT_SMART_VIEW_SECURE = "SMART_VIEW_SECURE";
  public static final String HINT_SMOOTH_SCROLL = "SMOOTH_SCROLL";
  public static final int HINT_TYPE_AMS_ACT_LAZY = 6;
  public static final int HINT_TYPE_AMS_ACT_RESUME = 3;
  public static final int HINT_TYPE_AMS_ACT_RESUME_LOW = 30;
  public static final int HINT_TYPE_AMS_ACT_START = 4;
  public static final int HINT_TYPE_AMS_ACT_START_LOW = 29;
  public static final int HINT_TYPE_AMS_APP_HOME = 8;
  public static final int HINT_TYPE_AMS_APP_LAUNCH = 32;
  public static final int HINT_TYPE_AMS_APP_LAUNCH_LM = 28;
  public static final int HINT_TYPE_AMS_APP_LAUNCH_M = 27;
  public static final int HINT_TYPE_AMS_APP_LAUNCH_WARM = 33;
  public static final int HINT_TYPE_AMS_APP_LAUNCH_WARM_LM = 35;
  public static final int HINT_TYPE_AMS_APP_LAUNCH_WARM_M = 34;
  public static final int HINT_TYPE_AMS_APP_SWITCH = 7;
  public static final int HINT_TYPE_AMS_RELAUNCH_RESUME = 2;
  public static final int HINT_TYPE_AMS_RESUME = 1;
  public static final int HINT_TYPE_AMS_RESUME_TAIL = 5;
  public static final int HINT_TYPE_AMS_RESUME_TAIL_LOW = 31;
  public static final int HINT_TYPE_APP_LAUNCH = 18;
  public static final int HINT_TYPE_APP_START = 24;
  public static final int HINT_TYPE_BADGE_UPDATE = 26;
  public static final int HINT_TYPE_BROWSER_FLING = 17;
  public static final int HINT_TYPE_BROWSER_TOUCH = 13;
  public static final int HINT_TYPE_CONTACT_SCROLL = 20;
  public static final int HINT_TYPE_DEVICE_WAKEUP = 19;
  public static final int HINT_TYPE_DEX_SWITCH_BOOST_STANDALONE_MODE = 3000;
  public static final int HINT_TYPE_GALLERY_SCROLL = 11;
  public static final int HINT_TYPE_GALLERY_TOUCH = 9;
  public static final int HINT_TYPE_GALLERY_TOUCH_TAIL = 10;
  public static final int HINT_TYPE_GESTURE_DETECTED = 22;
  public static final int HINT_TYPE_GESTURE_DETECTED_HRR = 23;
  public static final int HINT_TYPE_HOME_KEY_TOUCH = 14;
  public static final int HINT_TYPE_LAUNCHER_TOUCH = 12;
  public static final int HINT_TYPE_LISTVIEW_SCROLL = 16;
  public static final int HINT_TYPE_PERF_TUNE_30 = 1002;
  public static final int HINT_TYPE_PERF_TUNE_50 = 1001;
  public static final int HINT_TYPE_PERF_TUNE_70 = 1000;
  public static final int HINT_TYPE_PERF_TUNE_MAX = 1003;
  public static final int HINT_TYPE_PWM_ROTATION = 15;
  public static final int HINT_TYPE_SAMSUNG_KEYBOARD_FIRST_SHOW = 2340;
  public static final int HINT_TYPE_SAMSUNG_KEYBOARD_RE_ENTER_SHOW = 2341;
  public static final int HINT_TYPE_SIOP_LIMIT_BOOST = 1100;
  public static final int HINT_TYPE_SMART_VIEW_NORMAL = 1200;
  public static final int HINT_TYPE_SMART_VIEW_SECURE = 1201;
  public static final int HINT_TYPE_SMOOTH_SCROLL = 21;
  private static final int MAX_TOKEN_SIZE = 1000;
  public static final int NONE = -999;

  @Deprecated public static final int TYPE_BUS_MAX = 20;

  @Deprecated public static final int TYPE_BUS_MIN = 19;
  public static final int TYPE_CPUCTL = 28;
  public static final int TYPE_CPUSET = 27;

  @Deprecated public static final int TYPE_CPU_CORE_NUM_MAX = 15;

  @Deprecated public static final int TYPE_CPU_CORE_NUM_MIN = 14;

  @Deprecated public static final int TYPE_CPU_DELAYED_LOW_POWER_MODE = 31;

  @Deprecated public static final int TYPE_CPU_HOTPLUG_DISABLE = 25;

  @Deprecated public static final int TYPE_CPU_LEGACY_SCHEDULER = 24;

  @Deprecated public static final int TYPE_CPU_MAX = 13;

  @Deprecated public static final int TYPE_CPU_MIN = 12;

  @Deprecated public static final int TYPE_CPU_MIN_LITTLE_CORE = 32;

  @Deprecated public static final int TYPE_CPU_POWER_COLLAPSE_DISABLE = 23;

  @Deprecated public static final int TYPE_EMMC_BURST_MODE = 18;
  public static final int TYPE_EXTRA_TIMEOUT = -16777215;
  public static final int TYPE_FPS_MAX = 22;

  @Deprecated public static final int TYPE_GPU_MAX = 17;

  @Deprecated public static final int TYPE_GPU_MIN = 16;

  @Deprecated public static final int TYPE_HINT = 21;
  public static final int TYPE_NONE = -999;
  public static final int TYPE_PCIE_PSM_DISABLE = 26;
  public static final int TYPE_RESOURCE_BUS_MAX = 805310466;
  public static final int TYPE_RESOURCE_BUS_MIN = 805310465;
  public static final int TYPE_RESOURCE_CPU_CORE_NUM_MAX = 268443652;
  public static final int TYPE_RESOURCE_CPU_CORE_NUM_MIN = 268443651;
  public static final int TYPE_RESOURCE_CPU_DELAYED_LOW_POWER_MODE = 268451840;
  public static final int TYPE_RESOURCE_CPU_LITTLE_CORE_NUM_MIN = 268443649;
  public static final int TYPE_RESOURCE_CPU_LITTLE_RTG = 285220865;
  public static final int TYPE_RESOURCE_CPU_MAX = 301993986;
  public static final int TYPE_RESOURCE_CPU_MAX_LITTLE_CORE = 285216770;
  public static final int TYPE_RESOURCE_CPU_MIN = 301993985;
  public static final int TYPE_RESOURCE_CPU_MIN_LITTLE_CORE = 285216769;
  public static final int TYPE_RESOURCE_CPU_POWER_COLLAPSE_DISABLE = 268447744;
  public static final int TYPE_RESOURCE_CPU_PRIME_CORE_NUM_MAX = 268443654;
  public static final int TYPE_RESOURCE_CPU_PRIME_CORE_NUM_MIN = 268443653;
  public static final int TYPE_RESOURCE_DDR_LIMIT = 805314560;
  public static final int TYPE_RESOURCE_DDR_MEMLAT_RATIO_CEIL = 838864902;
  public static final int TYPE_RESOURCE_GPU_MAX = 536875010;
  public static final int TYPE_RESOURCE_GPU_MIN = 536875009;
  public static final int TYPE_RESOURCE_LLCC_BW_HYST = 838864897;
  public static final int TYPE_RESOURCE_LLCC_BW_IO_PERCENT = 838864899;
  public static final int TYPE_RESOURCE_LLCC_BW_SAMPLE_MS = 838864898;
  public static final int TYPE_RESOURCE_LLCC_DDR_RATIO_CEIL = 838864901;
  public static final int TYPE_RESOURCE_LLCC_MEMLAT_RATIO_CEIL = 838864900;
  public static final int TYPE_RESOURCE_LLC_OFF_DISABLE = 838868993;
  public static final int TYPE_RESOURCE_OVER_LIMIT = 1610616833;
  public static final int TYPE_RESOURCE_PCIE_PSM_DISABLE = 1610612736;
  public static final int TYPE_RESOURCE_SCHED_LOAD_BOOST_CPU0 = 302002176;
  public static final int TYPE_RESOURCE_SCHED_LOAD_BOOST_CPU1 = 302002177;
  public static final int TYPE_RESOURCE_SCHED_LOAD_BOOST_CPU2 = 302002178;
  public static final int TYPE_RESOURCE_SCHED_LOAD_BOOST_CPU3 = 302002179;
  public static final int TYPE_RESOURCE_SCHED_LOAD_BOOST_CPU4 = 302002180;
  public static final int TYPE_RESOURCE_SCHED_LOAD_BOOST_CPU5 = 302002181;
  public static final int TYPE_RESOURCE_SCHED_LOAD_BOOST_CPU6 = 302002182;
  public static final int TYPE_RESOURCE_SCHED_LOAD_BOOST_CPU7 = 302002183;
  public static final int TYPE_RESOURCE_TA_BOOST = 1090523139;
  public static final int TYPE_RESOURCE_UID = -15728640;
  public static final int TYPE_SCHEDTUNE_BOOST = 30;
  public static final int TYPE_SCHEDTUNE_POLICY = 29;
  public static final int TYPE_TIMEOUT = -16777216;
  private static final String VALUE_APP_START_BY_LAUNCHER = "app_start_by_launcher";
  private static final String VALUE_APP_START_BY_SYSTEM_LAUNCH = "app_start_by_system_launch";
  private static final String VALUE_APP_START_BY_SYSTEM_SWITCH = "app_start_by_system_switch";
  private static final String VALUE_APP_START_HOME = "app_start_for_home";
  private static HashMap<String, Integer> hintHash;
  protected static HashMap<Integer, Boolean> hintSupportHash;
  private static final boolean isGpisEnableChip;
  private static final boolean isGpisEnableCpuStructure;
  private static boolean isHyPerConnected;
  static boolean isSystemUid;
  private static Object mLock;
  private static int mUid;
  private static int nextToken;
  private static int pid;
  private static HashMap<Integer, Integer> resourceHash;
  private static final boolean sIsDebugLevelHigh =
      "0x4948".equals(SystemProperties.get("ro.boot.debug_level", "0x4f4c"));
  Context mContext;
  private CustomFrequencyManager mCustomFreqManager;
  private DvfsRequester mDvfsRequester;
  private String mProcName;
  private String mTagName;
  private int mToken;
  int mType;
  String LOG_TAG = SemDvfsManager.class.getSimpleName();
  protected HashMap<Integer, Integer> acquireHash = new HashMap<>();
  private int mHint = -999;
  private int mRequestedHint = -999;
  volatile boolean mIsAcquired = false;
  private String acquirePkg = "";
  String mName = null;

  @Retention(RetentionPolicy.SOURCE)
  public @interface HintType {}

  interface RequestFunc {
    void acquire(int i, int i2, String str, int i3, int[] iArr);

    boolean checkHintExist(int i);

    boolean checkResourceExist(int i);

    boolean checkSysfsIdExist(int i);

    int[] getSupportedFrequency(int i, int i2);

    void release(int i, int i2);

    String sysfsRead(int i);

    void sysfsWrite(int i, String str);
  }

  @Retention(RetentionPolicy.SOURCE)
  public @interface TypeResource {}

  /* JADX INFO: Access modifiers changed from: private */
  public static native void nativeAcquire(int i, int i2, String str, int i3, int[] iArr);

  /* JADX INFO: Access modifiers changed from: private */
  public static native boolean nativeCheckHintExist(int i);

  /* JADX INFO: Access modifiers changed from: private */
  public static native boolean nativeCheckResourceExist(int i);

  /* JADX INFO: Access modifiers changed from: private */
  public static native boolean nativeCheckSysfsIdExist(int i);

  /* JADX INFO: Access modifiers changed from: private */
  public static native int[] nativeGetSupportedFrequency(int i, int i2);

  /* JADX INFO: Access modifiers changed from: private */
  public static native void nativeRelease(int i, int i2);

  /* JADX INFO: Access modifiers changed from: private */
  public static native String nativeSysfsRead(int i);

  /* JADX INFO: Access modifiers changed from: private */
  public static native void nativeSysfsWrite(int i, String str);

  public abstract void acquire();

  @Deprecated
  public abstract void clearDvfsValue();

  @Deprecated
  public abstract void setDvfsValue(int i);

  @Deprecated
  public abstract void setDvfsValue(String str);

  static {
    boolean z;
    isSystemUid = false;
    mUid = -999;
    isGpisEnableChip =
        "SM8450".equals(SystemProperties.get("ro.soc.model", "chipname"))
            || "SM8475".equals(SystemProperties.get("ro.soc.model", "chipname"))
            || "SM8550".equals(SystemProperties.get("ro.soc.model", "chipname"))
            || "SM8650".equals(SystemProperties.get("ro.soc.model", "chipname"))
            || "s5e9945".equals(SystemProperties.get("ro.soc.model", "chipname"))
            || "MT6989".equals(SystemProperties.get("ro.soc.model", "chipname"));
    if (!"6:2".equals(SystemProperties.get("sys.perf.hmp", "4:4"))) {
      z = false;
    } else {
      z = true;
    }
    isGpisEnableCpuStructure = z;
    nextToken = 0;
    pid = Process.myPid();
    isHyPerConnected = false;
    hintSupportHash = new HashMap<>();
    mLock = new Object();
    hintHash = new HashMap<>();
    resourceHash = new HashMap<>();
    hintHash.put(HINT_AMS_RELAUNCH_RESUME, 2);
    hintHash.put(HINT_AMS_RESUME, 1);
    hintHash.put(HINT_AMS_ACT_RESUME, 3);
    hintHash.put(HINT_AMS_ACT_START, 4);
    hintHash.put(HINT_AMS_RESUME_TAIL, 5);
    hintHash.put(HINT_AMS_ACT_LAZY, 6);
    hintHash.put(HINT_AMS_APP_SWITCH, 7);
    hintHash.put(HINT_AMS_APP_HOME, 8);
    hintHash.put(HINT_GALLERY_TOUCH, 9);
    hintHash.put(HINT_GALLERY_TOUCH_TAIL, 10);
    hintHash.put(HINT_GALLERY_SCROLL, 11);
    hintHash.put(HINT_LAUNCHER_TOUCH, 12);
    hintHash.put(HINT_BROWSER_TOUCH, 13);
    hintHash.put(HINT_HOME_KEY_TOUCH, 14);
    hintHash.put(HINT_PWM_ROTATION, 15);
    hintHash.put(HINT_LISTVIEW_SCROLL, 16);
    hintHash.put(HINT_BROWSER_FLING, 17);
    hintHash.put(HINT_APP_LAUNCH, 18);
    hintHash.put(HINT_DEVICE_WAKEUP, 19);
    hintHash.put(HINT_BADGE_UPDATE, 26);
    hintHash.put("SMOOTH_SCROLL", 21);
    hintHash.put("GESTURE_DETECTED", 22);
    hintHash.put(HINT_GESTURE_DETECTED_HRR, 23);
    hintHash.put(HINT_CONTACT_SCROLL, 20);
    hintHash.put(HINT_SMART_VIEW_NORMAL, 1200);
    hintHash.put(HINT_SMART_VIEW_SECURE, 1201);
    hintHash.put(
        HINT_SAMSUNG_KEYBOARD_FIRST_SHOW, Integer.valueOf(HINT_TYPE_SAMSUNG_KEYBOARD_FIRST_SHOW));
    hintHash.put(
        HINT_SAMSUNG_KEYBOARD_RE_ENTER_SHOW,
        Integer.valueOf(HINT_TYPE_SAMSUNG_KEYBOARD_RE_ENTER_SHOW));
    resourceHash.put(12, Integer.valueOf(TYPE_RESOURCE_CPU_MIN));
    resourceHash.put(13, Integer.valueOf(TYPE_RESOURCE_CPU_MAX));
    resourceHash.put(14, Integer.valueOf(TYPE_RESOURCE_CPU_CORE_NUM_MIN));
    resourceHash.put(15, Integer.valueOf(TYPE_RESOURCE_CPU_CORE_NUM_MAX));
    resourceHash.put(16, Integer.valueOf(TYPE_RESOURCE_GPU_MIN));
    resourceHash.put(17, Integer.valueOf(TYPE_RESOURCE_GPU_MAX));
    resourceHash.put(19, Integer.valueOf(TYPE_RESOURCE_BUS_MIN));
    resourceHash.put(20, Integer.valueOf(TYPE_RESOURCE_BUS_MAX));
    resourceHash.put(23, Integer.valueOf(TYPE_RESOURCE_CPU_POWER_COLLAPSE_DISABLE));
    resourceHash.put(30, Integer.valueOf(TYPE_RESOURCE_TA_BOOST));
    resourceHash.put(31, Integer.valueOf(TYPE_RESOURCE_CPU_DELAYED_LOW_POWER_MODE));
    resourceHash.put(32, Integer.valueOf(TYPE_RESOURCE_CPU_MIN_LITTLE_CORE));
    resourceHash.put(26, 1610612736);
    int myUid = Process.myUid();
    mUid = myUid;
    isSystemUid = myUid == 1000;
  }

  public static SemDvfsManager createInstance(Context context, String tagName) {
    if (context == null) {
      return null;
    }
    return new SemDvfsHyPerManager(context, tagName, -999);
  }

  public static SemDvfsManager createInstance(Context context) {
    if (context == null) {
      return null;
    }
    return new SemDvfsHyPerManager(context, context.getPackageName(), -999);
  }

  @Deprecated
  public static SemDvfsManager createInstance(Context context, int type) {
    return createInstance(context, context.getPackageName(), type);
  }

  @Deprecated
  public static SemDvfsManager createInstance(Context context, String tagName, int type) {
    return new SemDvfsHyPerManager(context, tagName, type);
  }

  @Deprecated
  public static SemDvfsManager createInstance(
      Context context, String tagName, int type, int isIntent) {
    return createInstance(context, tagName, type);
  }

  protected SemDvfsManager(Context context, String tagName, int type) {
    this.mToken = -999;
    this.mContext = null;
    this.mCustomFreqManager = null;
    this.mTagName = null;
    this.mDvfsRequester = null;
    this.mType = -999;
    if (context == null) {
      return;
    }
    this.mContext = context;
    this.mProcName = context.getPackageName();
    this.mType = type;
    synchronized (mLock) {
      int i = nextToken;
      this.mToken = i;
      nextToken = (i + 1) % 1000;
      this.mTagName = tagName + "/" + pid + "@" + this.mToken;
    }
    this.mCustomFreqManager =
        (CustomFrequencyManager) this.mContext.getSystemService(Context.CFMS_SERVICE);
    this.mDvfsRequester = createRequester(isSystemUid);
    Integer res = resourceHash.get(Integer.valueOf(type));
    if (res != null) {
      this.mType = res.intValue();
    }
    if (type == 21) {
      try {
        Integer hint = hintHash.get(tagName);
        if (hint == null) {
          setHint(-999);
        } else {
          setHint(hint.intValue());
        }
      } catch (NullPointerException e) {
        setHint(-999);
      }
      this.mType = -999;
    }
    logOnEng(
        this.LOG_TAG,
        "SemDvfsManager:: New instance is created for " + this.mTagName + ", type = " + this.mType);
  }

  public void acquire(int timeout) {
    int[] freqTable;
    if (this.mDvfsRequester == null) {
      return;
    }
    int i = this.mRequestedHint;
    if (i != -999) {
      setHint(i);
    }
    Log.m98i(this.LOG_TAG, "acquire hyper - " + this.mTagName + ", type = " + this.mType);
    boolean timeoutExist = timeout != -999 && timeout > 0;
    int listSize = this.acquireHash.size() * 2;
    if (listSize == 0
        && this.mType != -999
        && (freqTable = getSupportedFrequency()) != null
        && freqTable.length > 0) {
      setDvfsValue(freqTable[0]);
      listSize = this.acquireHash.size() * 2;
    }
    if (this.mHint == -999 && listSize <= 0) {
      return;
    }
    if (timeoutExist) {
      listSize += 2;
    }
    int hintId = this.mHint;
    if (mUid != -999) {
      listSize += 2;
    }
    int[] list = new int[listSize];
    int idx = 0;
    for (Integer key : this.acquireHash.keySet()) {
      Integer value = this.acquireHash.get(key);
      if (value != null && idx + 1 < list.length) {
        list[idx] = key.intValue();
        list[idx + 1] = value.intValue();
        idx += 2;
      }
    }
    if (timeoutExist && idx + 1 < list.length) {
      int idx2 = idx + 1;
      list[idx] = -16777216;
      idx = idx2 + 1;
      list[idx2] = timeout;
    }
    int i2 = mUid;
    if (i2 != -999 && idx + 1 < list.length) {
      int idx3 = idx + 1;
      list[idx] = -15728640;
      int i3 = idx3 + 1;
      list[idx3] = i2;
    }
    this.mDvfsRequester.acquire(pid, this.mToken, this.mProcName, hintId, list);
    String appStartValue = getAppStartValue(this.mHint);
    if (appStartValue != null) {
      triggerAppStart(appStartValue);
    }
    if (isGpisDisableHint(this.mHint)) {
      setGpisHint(false);
    } else if (isGpisEnableHint(this.mHint)) {
      setGpisHint(true);
    }
  }

  public void acquire(String pkg_name) {
    this.acquirePkg = pkg_name;
    acquire(-999);
  }

  @Deprecated
  public void update(Context context, String hint) {}

  public void release() {
    DvfsRequester dvfsRequester = this.mDvfsRequester;
    if (dvfsRequester != null) {
      dvfsRequester.release(pid, this.mToken);
    }
  }

  @Deprecated
  public int[] getSupportedFrequency() {
    int i = this.mType;
    if (i == -999) {
      return null;
    }
    return getSupportedFrequency(i, 1);
  }

  public int[] getSupportedFrequency(int type) {
    return getSupportedFrequency(type, 1);
  }

  public int[] getSupportedFrequencyForSsrm(int type) {
    return getSupportedFrequency(type, 0);
  }

  public int[] getSupportedFrequency(int type, int level) {
    int[] supportedFrequency = null;
    DvfsRequester dvfsRequester = this.mDvfsRequester;
    if (dvfsRequester != null) {
      supportedFrequency = dvfsRequester.getSupportedFrequency(type, level);
    }
    if (supportedFrequency != null && supportedFrequency.length <= 0) {
      supportedFrequency = null;
    }
    if (supportedFrequency != null) {
      for (int i = 0; i < supportedFrequency.length; i++) {
        logOnEng(
            this.LOG_TAG,
            "GetSupportedFrequency[" + type + "][" + i + "] " + supportedFrequency[i]);
      }
    } else {
      logOnEng(this.LOG_TAG, "GetSupportedFrequency is Null");
    }
    return supportedFrequency;
  }

  public void sysfsWrite(int sysfsId, String value) {
    DvfsRequester dvfsRequester;
    if (value != null && (dvfsRequester = this.mDvfsRequester) != null) {
      dvfsRequester.sysfsWrite(sysfsId, value);
    }
  }

  public String sysfsRead(int sysfsId) {
    DvfsRequester dvfsRequester = this.mDvfsRequester;
    if (dvfsRequester != null) {
      return dvfsRequester.sysfsRead(sysfsId);
    }
    return "";
  }

  public boolean checkSysfsIdExist(int sysfsId) {
    DvfsRequester dvfsRequester = this.mDvfsRequester;
    if (dvfsRequester != null) {
      return dvfsRequester.checkSysfsIdExist(sysfsId);
    }
    return false;
  }

  public boolean checkHintSupported(int hint) {
    Boolean result = hintSupportHash.get(Integer.valueOf(hint));
    if (result != null) {
      return result.booleanValue();
    }
    if (this.mDvfsRequester != null && checkHyPerConnected()) {
      boolean ret = this.mDvfsRequester.checkHintExist(hint);
      hintSupportHash.put(Integer.valueOf(hint), Boolean.valueOf(ret));
      return ret;
    }
    return false;
  }

  public boolean checkResourceSupported(int resourceType) {
    DvfsRequester dvfsRequester = this.mDvfsRequester;
    if (dvfsRequester != null) {
      return dvfsRequester.checkResourceExist(resourceType);
    }
    return false;
  }

  @Deprecated
  public int[] getSupportedFrequencyForSsrm() {
    int i = this.mType;
    if (i == -999) {
      return null;
    }
    return getSupportedFrequency(i, 0);
  }

  private int getApproximateFrequency(int freq, int type, int level, int[] supportedFrequency) {
    int[] mSupportedFrequency;
    if (freq < 0 || type == -999) {
      return -999;
    }
    if (supportedFrequency == null) {
      mSupportedFrequency = getSupportedFrequency(type, level);
      if (mSupportedFrequency == null) {
        return -999;
      }
    } else {
      mSupportedFrequency = supportedFrequency;
    }
    int idx = mSupportedFrequency.length - 1;
    if (idx < 0) {
      return -999;
    }
    int realFreq = mSupportedFrequency[0];
    while (idx >= 0) {
      if (mSupportedFrequency[idx] >= freq) {
        int realFreq2 = mSupportedFrequency[idx];
        return realFreq2;
      }
      idx--;
    }
    return realFreq;
  }

  public int getApproximateFrequency(int freq, int type, int level) {
    return getApproximateFrequency(freq, type, level, null);
  }

  @Deprecated
  public int getApproximateFrequency(int freq) {
    return getApproximateFrequency(freq, this.mType, 1);
  }

  public int getApproximateFrequencyByPercent(double percent, int type, int level) {
    int[] mSupportedValues;
    if (percent < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
        || percent > 1.0d
        || type == -999
        || (mSupportedValues = getSupportedFrequency(type, level)) == null
        || mSupportedValues.length <= 0) {
      return -999;
    }
    return getApproximateFrequency(
        (int) (mSupportedValues[0] * percent), type, level, mSupportedValues);
  }

  @Deprecated
  public int getApproximateFrequencyByPercent(double percent) {
    return getApproximateFrequencyByPercent(percent, this.mType, 1);
  }

  @Deprecated
  public int getApproximateFrequencyByPercentForSsrm(double percent) {
    return getApproximateFrequencyByPercent(percent, this.mType, 0);
  }

  @Deprecated
  public int getApproximateFrequencyForSsrm(int freq) {
    return getApproximateFrequency(freq, this.mType, 0);
  }

  public void addResourceValue(int type, int value) {
    if (this.acquireHash.get(Integer.valueOf(type)) != null) {
      this.acquireHash.remove(Integer.valueOf(type));
    }
    this.acquireHash.put(Integer.valueOf(type), Integer.valueOf(value));
  }

  public void removeResourceValue(int type) {
    this.acquireHash.remove(Integer.valueOf(type));
  }

  public void clearResourceValue() {
    this.acquireHash.clear();
  }

  public void setHint(int hint) {
    if (hint == this.mHint) {
      return;
    }
    this.mRequestedHint = hint;
    if (!checkHyPerConnected()) {
      Log.m98i(this.LOG_TAG, "Fail to set Hint... [" + hint + "] HyPer is not connected");
      this.mHint = -999;
    } else {
      if (checkHintSupported(hint)) {
        this.mHint = hint;
      } else {
        this.mHint = -999;
      }
      this.mRequestedHint = -999;
    }
  }

  private boolean checkHyPerConnected() {
    DvfsRequester dvfsRequester = this.mDvfsRequester;
    if (dvfsRequester == null) {
      return false;
    }
    if (isHyPerConnected) {
      return true;
    }
    if (dvfsRequester.checkHintExist(32)) {
      isHyPerConnected = true;
    }
    return isHyPerConnected;
  }

  public void setProcName(String procName) {
    this.mProcName = procName;
  }

  public String getName() {
    return this.mName;
  }

  private static String getAppStartValue(int hint) {
    if (hint == 18) {
      return VALUE_APP_START_BY_LAUNCHER;
    }
    if (hint == 7) {
      return VALUE_APP_START_BY_SYSTEM_SWITCH;
    }
    if (hint == 32 || hint == 27 || hint == 28) {
      return VALUE_APP_START_BY_SYSTEM_LAUNCH;
    }
    if (hint == 8) {
      return VALUE_APP_START_HOME;
    }
    return null;
  }

  private void triggerAppStart(String value) {
    ISamsungDeviceHealthManager service;
    if (value == null || value.isEmpty()) {
      return;
    }
    String str = this.acquirePkg;
    if (str != null && !str.isEmpty()) {
      value = value + "/" + this.acquirePkg;
    }
    IBinder binder = ServiceManager.getService("sdhms");
    if (binder != null
        && (service = ISamsungDeviceHealthManager.Stub.asInterface(binder)) != null) {
      try {
        service.sendCommand("APP_START", value);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
  }

  private static boolean isGpisDisableHint(int hint) {
    return (hint == 18 || hint == 7) ? !isGpisEnableChip : hint == 8 && isGpisEnableCpuStructure;
  }

  private static boolean isGpisEnableHint(int hint) {
    if (hint == 8 && !isGpisEnableCpuStructure) {
      return true;
    }
    return false;
  }

  private void setGpisHint(boolean flag) {
    CustomFrequencyManager customFrequencyManager = this.mCustomFreqManager;
    if (customFrequencyManager != null) {
      try {
        customFrequencyManager.setGpisHint(flag);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void logOnEng(String tag, String msg) {
    if (sIsDebugLevelHigh) {
      Log.m98i(tag, msg);
    }
  }

  class DvfsRequester {
    RequestFunc func;

    public DvfsRequester(RequestFunc requestFunc) {
      this.func = null;
      this.func = requestFunc;
    }

    public void acquire(int pid, int token, String procName, int hint, int[] list) {
      RequestFunc requestFunc = this.func;
      if (requestFunc != null) {
        requestFunc.acquire(pid, token, procName, hint, list);
      }
    }

    public void release(int pid, int token) {
      RequestFunc requestFunc = this.func;
      if (requestFunc != null) {
        requestFunc.release(pid, token);
      }
    }

    public int[] getSupportedFrequency(int type, int level) {
      RequestFunc requestFunc = this.func;
      if (requestFunc != null) {
        return requestFunc.getSupportedFrequency(type, level);
      }
      return null;
    }

    public void sysfsWrite(int sysfsId, String value) {
      RequestFunc requestFunc = this.func;
      if (requestFunc != null) {
        requestFunc.sysfsWrite(sysfsId, value);
      }
    }

    public String sysfsRead(int sysfsId) {
      RequestFunc requestFunc = this.func;
      if (requestFunc != null) {
        return requestFunc.sysfsRead(sysfsId);
      }
      return "";
    }

    public boolean checkSysfsIdExist(int sysfsId) {
      RequestFunc requestFunc = this.func;
      if (requestFunc != null) {
        return requestFunc.checkSysfsIdExist(sysfsId);
      }
      return false;
    }

    public boolean checkHintExist(int hint) {
      RequestFunc requestFunc = this.func;
      if (requestFunc != null) {
        return requestFunc.checkHintExist(hint);
      }
      return false;
    }

    public boolean checkResourceExist(int resource) {
      RequestFunc requestFunc = this.func;
      if (requestFunc != null) {
        return requestFunc.checkResourceExist(resource);
      }
      return false;
    }
  }

  private DvfsRequester createRequester(boolean isSystemUid2) {
    if (isSystemUid2) {
      return new DvfsRequester(
          new RequestFunc() { // from class: com.samsung.android.os.SemDvfsManager.1
            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public void acquire(int pid2, int token, String procName, int hint, int[] list) {
              SemDvfsManager.nativeAcquire(pid2, token, procName, hint, list);
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public void release(int pid2, int token) {
              SemDvfsManager.nativeRelease(pid2, token);
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public int[] getSupportedFrequency(int type, int level) {
              return SemDvfsManager.nativeGetSupportedFrequency(type, level);
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public void sysfsWrite(int sysfsId, String value) {
              SemDvfsManager.nativeSysfsWrite(sysfsId, value);
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public String sysfsRead(int sysfsId) {
              return SemDvfsManager.nativeSysfsRead(sysfsId);
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public boolean checkSysfsIdExist(int sysfsId) {
              return SemDvfsManager.nativeCheckSysfsIdExist(sysfsId);
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public boolean checkHintExist(int hint) {
              return SemDvfsManager.nativeCheckHintExist(hint);
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public boolean checkResourceExist(int resource) {
              return SemDvfsManager.nativeCheckResourceExist(resource);
            }
          });
    }
    if (this.mCustomFreqManager != null) {
      return new DvfsRequester(
          new RequestFunc() { // from class: com.samsung.android.os.SemDvfsManager.2
            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public void acquire(int pid2, int token, String procName, int hint, int[] list) {
              try {
                SemDvfsManager.this.mCustomFreqManager.acquire(pid2, token, procName, hint, list);
              } catch (NullPointerException e) {
                e.printStackTrace();
              }
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public void release(int pid2, int token) {
              try {
                SemDvfsManager.this.mCustomFreqManager.release(pid2, token);
              } catch (NullPointerException e) {
                e.printStackTrace();
              }
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public int[] getSupportedFrequency(int type, int level) {
              try {
                return SemDvfsManager.this.mCustomFreqManager.getSupportedFrequency(type, level);
              } catch (NullPointerException e) {
                e.printStackTrace();
                return null;
              }
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public void sysfsWrite(int sysfsId, String value) {
              try {
                SemDvfsManager.this.mCustomFreqManager.writeSysfs(sysfsId, value);
              } catch (NullPointerException e) {
                e.printStackTrace();
              }
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public String sysfsRead(int sysfsId) {
              try {
                return SemDvfsManager.this.mCustomFreqManager.readSysfs(sysfsId);
              } catch (NullPointerException e) {
                e.printStackTrace();
                return "";
              }
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public boolean checkSysfsIdExist(int sysfsId) {
              try {
                return SemDvfsManager.this.mCustomFreqManager.checkSysfsIdExist(sysfsId);
              } catch (NullPointerException e) {
                e.printStackTrace();
                return false;
              }
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public boolean checkHintExist(int hint) {
              try {
                return SemDvfsManager.this.mCustomFreqManager.checkHintExist(hint);
              } catch (NullPointerException e) {
                e.printStackTrace();
                return false;
              }
            }

            @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
            public boolean checkResourceExist(int resource) {
              try {
                return SemDvfsManager.this.mCustomFreqManager.checkResourceExist(resource);
              } catch (NullPointerException e) {
                e.printStackTrace();
                return false;
              }
            }
          });
    }
    return null;
  }

  /* renamed from: x */
  private static String m290x(int[] e) {
    StringBuilder sb = new StringBuilder();
    for (int i : e) {
      sb.append((char) (i ^ 122));
    }
    return sb.toString();
  }
}
