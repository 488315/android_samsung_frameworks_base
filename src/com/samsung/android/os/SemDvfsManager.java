package com.samsung.android.os;

import android.content.Context;
import android.hardware.scontext.SContextConstants;
import android.os.CustomFrequencyManager;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;
import com.sec.android.sdhms.ISamsungDeviceHealthManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
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

    @Deprecated
    public static final String HINT_APP_LAUNCH = "APP_LAUNCH";

    @Deprecated
    public static final String HINT_BADGE_UPDATE = "BADGE_UPDATE";

    @Deprecated
    public static final String HINT_BROWSER_FLING = "BROWSER_FLING";

    @Deprecated
    public static final String HINT_BROWSER_TOUCH = "BROWSER_TOUCH";

    @Deprecated
    public static final String HINT_CONTACT_SCROLL = "CONTACT_SCROLL";
    public static final String HINT_DEVICE_WAKEUP = "DEVICE_WAKEUP";

    @Deprecated
    public static final String HINT_GALLERY_SCROLL = "GALLERY_SCROLL";

    @Deprecated
    public static final String HINT_GALLERY_TOUCH = "GALLERY_TOUCH";

    @Deprecated
    public static final String HINT_GALLERY_TOUCH_TAIL = "GALLERY_TOUCH_TAIL";
    public static final String HINT_GESTURE_DETECTED = "GESTURE_DETECTED";
    public static final String HINT_GESTURE_DETECTED_HRR = "GESTURE_DETECTED_HRR";

    @Deprecated
    public static final String HINT_HOME_KEY_TOUCH = "HOME_KEY_TOUCH";

    @Deprecated
    public static final String HINT_LAUNCHER_TOUCH = "LAUNCHER_TOUCH";
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

    @Deprecated
    public static final int TYPE_BUS_MAX = 20;

    @Deprecated
    public static final int TYPE_BUS_MIN = 19;
    public static final int TYPE_CPUCTL = 28;
    public static final int TYPE_CPUSET = 27;

    @Deprecated
    public static final int TYPE_CPU_CORE_NUM_MAX = 15;

    @Deprecated
    public static final int TYPE_CPU_CORE_NUM_MIN = 14;

    @Deprecated
    public static final int TYPE_CPU_DELAYED_LOW_POWER_MODE = 31;

    @Deprecated
    public static final int TYPE_CPU_HOTPLUG_DISABLE = 25;

    @Deprecated
    public static final int TYPE_CPU_LEGACY_SCHEDULER = 24;

    @Deprecated
    public static final int TYPE_CPU_MAX = 13;

    @Deprecated
    public static final int TYPE_CPU_MIN = 12;

    @Deprecated
    public static final int TYPE_CPU_MIN_LITTLE_CORE = 32;

    @Deprecated
    public static final int TYPE_CPU_POWER_COLLAPSE_DISABLE = 23;

    @Deprecated
    public static final int TYPE_EMMC_BURST_MODE = 18;
    public static final int TYPE_EXTRA_TIMEOUT = -16777215;
    public static final int TYPE_FPS_MAX = 22;

    @Deprecated
    public static final int TYPE_GPU_MAX = 17;

    @Deprecated
    public static final int TYPE_GPU_MIN = 16;

    @Deprecated
    public static final int TYPE_HINT = 21;
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
    public static final int TYPE_RESOURCE_NPU_MAX = 1342181378;
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
    public static final int TYPE_RESOURCE_TID = -14680064;
    public static final int TYPE_RESOURCE_UID = -15728640;
    public static final int TYPE_SCHEDTUNE_BOOST = 30;
    public static final int TYPE_SCHEDTUNE_POLICY = 29;
    public static final int TYPE_TIMEOUT = -16777216;
    private static final String VALUE_APP_START_BY_LAUNCHER = "app_start_by_launcher";
    private static final String VALUE_APP_START_BY_SYSTEM_LAUNCH = "app_start_by_system_launch";
    private static final String VALUE_APP_START_BY_SYSTEM_SWITCH = "app_start_by_system_switch";
    private static final String VALUE_APP_START_HOME = "app_start_for_home";
    private static HashMap<String, Integer> hintHash = null;
    protected static HashMap<Integer, Boolean> hintSupportHash = null;
    private static final boolean isGpisEnableChip;
    private static final boolean isGpisEnableCpuStructure;
    private static boolean isHyPerConnected = false;
    static boolean isSystemUid = false;
    private static Object mLock = null;
    private static int mUid = -999;
    private static int nextToken;
    private static int pid;
    private static HashMap<Integer, Integer> resourceHash;
    private static final boolean sIsDebugLevelHigh = "0x4948".equals(SystemProperties.get("ro.boot.debug_level", "0x4f4c"));
    Context mContext;
    private CustomFrequencyManager mCustomFreqManager;
    private DvfsRequester mDvfsRequester;
    private String mProcName;
    private String mTagName;
    private int mToken;
    int mType;
    String LOG_TAG = "SemDvfsManager";
    protected HashMap<Integer, Integer> acquireHash = new HashMap<>();
    private int mHint = -999;
    private int mRequestedHint = -999;
    volatile boolean mIsAcquired = false;
    private String acquirePkg = "";
    String mName = null;

    @Retention(RetentionPolicy.SOURCE)
    public @interface HintType {
    }

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
    public @interface TypeResource {
    }

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
    public abstract void setDvfsValue(int i);

    static {
        isGpisEnableChip = "SM8450".equals(SystemProperties.get("ro.soc.model", "chipname")) || "SM8475".equals(SystemProperties.get("ro.soc.model", "chipname")) || "SM8550".equals(SystemProperties.get("ro.soc.model", "chipname")) || "SM8650".equals(SystemProperties.get("ro.soc.model", "chipname")) || "s5e9945".equals(SystemProperties.get("ro.soc.model", "chipname")) || "s5e9955".equals(SystemProperties.get("ro.soc.model", "chipname"));
        isGpisEnableCpuStructure = "6:2".equals(SystemProperties.get("sys.perf.hmp", "4:4"));
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
        hintHash.put(HINT_SAMSUNG_KEYBOARD_FIRST_SHOW, Integer.valueOf(HINT_TYPE_SAMSUNG_KEYBOARD_FIRST_SHOW));
        hintHash.put(HINT_SAMSUNG_KEYBOARD_RE_ENTER_SHOW, Integer.valueOf(HINT_TYPE_SAMSUNG_KEYBOARD_RE_ENTER_SHOW));
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
        int iMyUid = Process.myUid();
        mUid = iMyUid;
        isSystemUid = iMyUid == 1000;
    }

    public static SemDvfsManager createInstance(Context context, String str) {
        if (context == null) {
            return null;
        }
        return new SemDvfsHyPerManager(context, str, -999);
    }

    public static SemDvfsManager createInstance(Context context) {
        if (context == null) {
            return null;
        }
        return new SemDvfsHyPerManager(context, context.getPackageName(), -999);
    }

    @Deprecated
    public static SemDvfsManager createInstance(Context context, int i) {
        return createInstance(context, context.getPackageName(), i);
    }

    @Deprecated
    public static SemDvfsManager createInstance(Context context, String str, int i) {
        return new SemDvfsHyPerManager(context, str, i);
    }

    protected SemDvfsManager(Context context, String str, int i) {
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
        this.mType = i;
        synchronized (mLock) {
            int i2 = nextToken;
            this.mToken = i2;
            nextToken = (i2 + 1) % 1000;
            this.mTagName = str + "/" + pid + "@" + this.mToken;
        }
        this.mCustomFreqManager = (CustomFrequencyManager) this.mContext.getSystemService(Context.CFMS_SERVICE);
        this.mDvfsRequester = createRequester(isSystemUid);
        Integer num = resourceHash.get(Integer.valueOf(i));
        if (num != null) {
            this.mType = num.intValue();
        }
        if (i == 21) {
            try {
                Integer num2 = hintHash.get(str);
                if (num2 == null) {
                    setHint(-999);
                } else {
                    setHint(num2.intValue());
                }
            } catch (NullPointerException unused) {
                setHint(-999);
            }
            this.mType = -999;
        }
        logOnEng(this.LOG_TAG, "SemDvfsManager:: New instance is created for " + this.mTagName + ", type = " + this.mType);
    }

    public void acquire(int i) {
        int i2;
        int i3;
        int i4;
        int[] supportedFrequency;
        if (this.mDvfsRequester == null) {
            return;
        }
        int i5 = this.mRequestedHint;
        if (i5 != -999) {
            setHint(i5);
        }
        Log.i(this.LOG_TAG, "acquire hyper - " + this.mTagName + ", type = " + this.mType);
        int i6 = 0;
        boolean z = i != -999 && i > 0;
        int size = this.acquireHash.size() * 2;
        if (size == 0 && (i4 = this.mType) != -999 && (supportedFrequency = getSupportedFrequency(i4, 1)) != null && supportedFrequency.length > 0) {
            setDvfsValue(supportedFrequency[0]);
            size = this.acquireHash.size() * 2;
        }
        int i7 = this.mHint;
        if (i7 != -999 || size > 0) {
            if (z) {
                size += 2;
            }
            if (mUid != -999) {
                size += 2;
            }
            int[] iArr = new int[size];
            for (Map.Entry<Integer, Integer> entry : this.acquireHash.entrySet()) {
                int i8 = i6 + 1;
                if (i8 < size) {
                    if ((entry.getKey().intValue() & (-14680064)) == -14680064) {
                        iArr[i6] = -14680064;
                    } else {
                        iArr[i6] = entry.getKey().intValue();
                    }
                    iArr[i8] = entry.getValue().intValue();
                    i6 += 2;
                }
            }
            if (z && (i3 = i6 + 1) < size) {
                iArr[i6] = -16777216;
                i6 += 2;
                iArr[i3] = i;
            }
            int i9 = mUid;
            if (i9 != -999 && (i2 = i6 + 1) < size) {
                iArr[i6] = -15728640;
                iArr[i2] = i9;
            }
            this.mDvfsRequester.acquire(pid, this.mToken, this.mProcName, i7, iArr);
            String appStartValue = getAppStartValue(this.mHint);
            if (appStartValue != null) {
                triggerAppStart(appStartValue);
            }
        }
    }

    public void acquire(String str) {
        this.acquirePkg = str;
        acquire(-999);
    }

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

    public int[] getSupportedFrequency(int i) {
        return getSupportedFrequency(i, 1);
    }

    public int[] getSupportedFrequencyForSsrm(int i) {
        return getSupportedFrequency(i, 0);
    }

    public int[] getSupportedFrequency(int i, int i2) {
        DvfsRequester dvfsRequester = this.mDvfsRequester;
        int[] supportedFrequency = dvfsRequester != null ? dvfsRequester.getSupportedFrequency(i, i2) : null;
        int[] iArr = (supportedFrequency == null || supportedFrequency.length > 0) ? supportedFrequency : null;
        if (iArr != null) {
            for (int i3 = 0; i3 < iArr.length; i3++) {
                logOnEng(this.LOG_TAG, "GetSupportedFrequency[" + i + "][" + i3 + "] " + iArr[i3]);
            }
            return iArr;
        }
        logOnEng(this.LOG_TAG, "GetSupportedFrequency is Null");
        return iArr;
    }

    public void sysfsWrite(int i, String str) {
        DvfsRequester dvfsRequester;
        if (str == null || (dvfsRequester = this.mDvfsRequester) == null) {
            return;
        }
        dvfsRequester.sysfsWrite(i, str);
    }

    public String sysfsRead(int i) {
        DvfsRequester dvfsRequester = this.mDvfsRequester;
        if (dvfsRequester != null) {
            return dvfsRequester.sysfsRead(i);
        }
        return "";
    }

    public boolean checkSysfsIdExist(int i) {
        DvfsRequester dvfsRequester = this.mDvfsRequester;
        if (dvfsRequester != null) {
            return dvfsRequester.checkSysfsIdExist(i);
        }
        return false;
    }

    public boolean checkHintSupported(int i) {
        Boolean bool = hintSupportHash.get(Integer.valueOf(i));
        if (bool != null) {
            return bool.booleanValue();
        }
        if (this.mDvfsRequester == null || !checkHyPerConnected()) {
            return false;
        }
        boolean zCheckHintExist = this.mDvfsRequester.checkHintExist(i);
        hintSupportHash.put(Integer.valueOf(i), Boolean.valueOf(zCheckHintExist));
        return zCheckHintExist;
    }

    public boolean checkResourceSupported(int i) {
        DvfsRequester dvfsRequester = this.mDvfsRequester;
        if (dvfsRequester != null) {
            return dvfsRequester.checkResourceExist(i);
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

    private int getApproximateFrequency(int i, int i2, int i3, int[] iArr) {
        int length;
        if (i < 0 || i2 == -999) {
            return -999;
        }
        if ((iArr == null && (iArr = getSupportedFrequency(i2, i3)) == null) || iArr.length - 1 < 0) {
            return -999;
        }
        int i4 = iArr[0];
        for (length = iArr.length - 1; length >= 0; length--) {
            int i5 = iArr[length];
            if (i5 >= i) {
                return i5;
            }
        }
        return i4;
    }

    public int getApproximateFrequency(int i, int i2, int i3) {
        return getApproximateFrequency(i, i2, i3, null);
    }

    @Deprecated
    public int getApproximateFrequency(int i) {
        return getApproximateFrequency(i, this.mType, 1);
    }

    public int getApproximateFrequencyByPercent(double d, int i, int i2) {
        int[] supportedFrequency;
        if (d < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || d > 1.0d || i == -999 || (supportedFrequency = getSupportedFrequency(i, i2)) == null || supportedFrequency.length <= 0) {
            return -999;
        }
        return getApproximateFrequency((int) (supportedFrequency[0] * d), i, i2, supportedFrequency);
    }

    public int getApproximateFrequencyByPercent(double d) {
        return getApproximateFrequencyByPercent(d, this.mType, 1);
    }

    public int getApproximateFrequencyByPercentForSsrm(double d) {
        return getApproximateFrequencyByPercent(d, this.mType, 0);
    }

    public void addResourceValue(int i, int i2) {
        this.acquireHash.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void removeResourceValue(int i) {
        this.acquireHash.remove(Integer.valueOf(i));
    }

    public void clearResourceValue() {
        this.acquireHash.clear();
    }

    public void setHint(int i) {
        if (i == this.mHint) {
            return;
        }
        this.mRequestedHint = i;
        if (!checkHyPerConnected()) {
            Log.i(this.LOG_TAG, "Fail to set Hint... [" + i + "] HyPer is not connected");
            this.mHint = -999;
            return;
        }
        if (checkHintSupported(i)) {
            this.mHint = i;
        } else {
            this.mHint = -999;
        }
        this.mRequestedHint = -999;
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

    public void setProcName(String str) {
        this.mProcName = str;
    }

    public String getName() {
        return this.mName;
    }

    private static String getAppStartValue(int i) {
        if (i == 18) {
            return VALUE_APP_START_BY_LAUNCHER;
        }
        if (i == 7) {
            return VALUE_APP_START_BY_SYSTEM_SWITCH;
        }
        if (i == 32 || i == 27 || i == 28) {
            return VALUE_APP_START_BY_SYSTEM_LAUNCH;
        }
        if (i == 8) {
            return VALUE_APP_START_HOME;
        }
        return null;
    }

    private void triggerAppStart(String str) {
        ISamsungDeviceHealthManager iSamsungDeviceHealthManagerAsInterface;
        if (str == null || str.isEmpty()) {
            return;
        }
        String str2 = this.acquirePkg;
        if (str2 != null && !str2.isEmpty()) {
            str = str + "/" + this.acquirePkg;
        }
        IBinder service = ServiceManager.getService("sdhms");
        if (service == null || (iSamsungDeviceHealthManagerAsInterface = ISamsungDeviceHealthManager.Stub.asInterface(service)) == null) {
            return;
        }
        try {
            iSamsungDeviceHealthManagerAsInterface.sendCommand("APP_START", str);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static boolean isGpisDisableHint(int i) {
        return (i == 18 || i == 7) ? !isGpisEnableChip : i == 8 && isGpisEnableCpuStructure;
    }

    private static boolean isGpisEnableHint(int i) {
        return i == 8 && !isGpisEnableCpuStructure;
    }

    private void setGpisHint(boolean z) {
        CustomFrequencyManager customFrequencyManager = this.mCustomFreqManager;
        if (customFrequencyManager != null) {
            try {
                customFrequencyManager.setGpisHint(z);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void logOnEng(String str, String str2) {
        if (sIsDebugLevelHigh) {
            Log.i(str, str2);
        }
    }

    static class DvfsRequester {
        RequestFunc func;

        public DvfsRequester(RequestFunc requestFunc) {
            this.func = requestFunc;
        }

        public void acquire(int i, int i2, String str, int i3, int[] iArr) {
            RequestFunc requestFunc = this.func;
            if (requestFunc != null) {
                requestFunc.acquire(i, i2, str, i3, iArr);
            }
        }

        public void release(int i, int i2) {
            RequestFunc requestFunc = this.func;
            if (requestFunc != null) {
                requestFunc.release(i, i2);
            }
        }

        public int[] getSupportedFrequency(int i, int i2) {
            RequestFunc requestFunc = this.func;
            if (requestFunc != null) {
                return requestFunc.getSupportedFrequency(i, i2);
            }
            return null;
        }

        public void sysfsWrite(int i, String str) {
            RequestFunc requestFunc = this.func;
            if (requestFunc != null) {
                requestFunc.sysfsWrite(i, str);
            }
        }

        public String sysfsRead(int i) {
            RequestFunc requestFunc = this.func;
            if (requestFunc != null) {
                return requestFunc.sysfsRead(i);
            }
            return "";
        }

        public boolean checkSysfsIdExist(int i) {
            RequestFunc requestFunc = this.func;
            if (requestFunc != null) {
                return requestFunc.checkSysfsIdExist(i);
            }
            return false;
        }

        public boolean checkHintExist(int i) {
            RequestFunc requestFunc = this.func;
            if (requestFunc != null) {
                return requestFunc.checkHintExist(i);
            }
            return false;
        }

        public boolean checkResourceExist(int i) {
            RequestFunc requestFunc = this.func;
            if (requestFunc != null) {
                return requestFunc.checkResourceExist(i);
            }
            return false;
        }
    }

    private DvfsRequester createRequester(boolean z) {
        if (z) {
            return new DvfsRequester(new RequestFunc(this) { // from class: com.samsung.android.os.SemDvfsManager.1
                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public void acquire(int i, int i2, String str, int i3, int[] iArr) {
                    SemDvfsManager.nativeAcquire(i, i2, str, i3, iArr);
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public void release(int i, int i2) {
                    SemDvfsManager.nativeRelease(i, i2);
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public int[] getSupportedFrequency(int i, int i2) {
                    return SemDvfsManager.nativeGetSupportedFrequency(i, i2);
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public void sysfsWrite(int i, String str) {
                    SemDvfsManager.nativeSysfsWrite(i, str);
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public String sysfsRead(int i) {
                    return SemDvfsManager.nativeSysfsRead(i);
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public boolean checkSysfsIdExist(int i) {
                    return SemDvfsManager.nativeCheckSysfsIdExist(i);
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public boolean checkHintExist(int i) {
                    return SemDvfsManager.nativeCheckHintExist(i);
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public boolean checkResourceExist(int i) {
                    return SemDvfsManager.nativeCheckResourceExist(i);
                }
            });
        }
        if (this.mCustomFreqManager != null) {
            return new DvfsRequester(new RequestFunc() { // from class: com.samsung.android.os.SemDvfsManager.2
                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public void acquire(int i, int i2, String str, int i3, int[] iArr) {
                    try {
                        SemDvfsManager.this.mCustomFreqManager.acquire(i, i2, str, i3, iArr);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public void release(int i, int i2) {
                    try {
                        SemDvfsManager.this.mCustomFreqManager.release(i, i2);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public int[] getSupportedFrequency(int i, int i2) {
                    try {
                        return SemDvfsManager.this.mCustomFreqManager.getSupportedFrequency(i, i2);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public void sysfsWrite(int i, String str) {
                    try {
                        SemDvfsManager.this.mCustomFreqManager.writeSysfs(i, str);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public String sysfsRead(int i) {
                    try {
                        return SemDvfsManager.this.mCustomFreqManager.readSysfs(i);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        return "";
                    }
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public boolean checkSysfsIdExist(int i) {
                    try {
                        return SemDvfsManager.this.mCustomFreqManager.checkSysfsIdExist(i);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public boolean checkHintExist(int i) {
                    try {
                        return SemDvfsManager.this.mCustomFreqManager.checkHintExist(i);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                @Override // com.samsung.android.os.SemDvfsManager.RequestFunc
                public boolean checkResourceExist(int i) {
                    try {
                        return SemDvfsManager.this.mCustomFreqManager.checkResourceExist(i);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            });
        }
        return null;
    }

    private static String x(int[] iArr) {
        StringBuilder sb = new StringBuilder();
        for (int i : iArr) {
            sb.append((char) (i ^ 122));
        }
        return sb.toString();
    }
}
