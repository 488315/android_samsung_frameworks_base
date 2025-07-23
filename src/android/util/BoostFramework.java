package android.util;

import android.content.Context;
import android.graphics.BLASTBufferQueue;
import android.os.SystemProperties;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class BoostFramework {
    public static final int MPCTLV3_GPU_IS_APP_BG = 1115832320;
    public static final int MPCTLV3_GPU_IS_APP_FG = 1115815936;
    public static final int MPCTLV3_SCHED_TASK_BOOST = 1086849024;
    private static final String PERFORMANCE_CLASS = "com.qualcomm.qti.Performance";
    private static final String PERFORMANCE_JAR = "/system/framework/QPerformance.jar";
    public static final float PERF_HAL_V22 = 2.2f;
    public static final float PERF_HAL_V23 = 2.3f;
    private static final String TAG = "BoostFramework";
    public static final int UXE_EVENT_BINDAPP = 2;
    public static final int UXE_EVENT_DISPLAYED_ACT = 3;
    public static final int UXE_EVENT_GAME = 5;
    public static final int UXE_EVENT_KILL = 4;
    public static final int UXE_EVENT_PKG_INSTALL = 8;
    public static final int UXE_EVENT_PKG_UNINSTALL = 7;
    public static final int UXE_EVENT_SUB_LAUNCH = 6;
    public static final int UXE_TRIGGER = 1;
    private static final String UXPERFORMANCE_CLASS = "com.qualcomm.qti.UxPerformance";
    private static final String UXPERFORMANCE_JAR = "/system/framework/UxPerformance.jar";
    public static final int VENDOR_FEEDBACK_LAUNCH_END_POINT = 5634;
    public static final int VENDOR_FEEDBACK_PA_FW = 5636;
    public static final int VENDOR_FEEDBACK_WORKLOAD_TYPE = 5633;
    public static final int VENDOR_HINT_ACTIVITY_BOOST = 4228;
    public static final int VENDOR_HINT_ANIM_BOOST = 4227;
    public static final int VENDOR_HINT_APP_UPDATE = 4242;
    public static final int VENDOR_HINT_BINDAPP = 4256;
    public static final int VENDOR_HINT_BOOST_RENDERTHREAD = 4246;
    public static final int VENDOR_HINT_DRAG_BOOST = 4231;
    public static final int VENDOR_HINT_DRAG_END = 4178;
    public static final int VENDOR_HINT_DRAG_START = 4177;
    public static final int VENDOR_HINT_FIRST_DRAW = 4162;
    public static final int VENDOR_HINT_FIRST_LAUNCH_BOOST = 4225;
    public static final int VENDOR_HINT_IME_LAUNCH_EVENT = 4255;
    public static final int VENDOR_HINT_KILL = 4243;
    public static final int VENDOR_HINT_MTP_BOOST = 4230;
    public static final int VENDOR_HINT_PACKAGE_INSTALL_BOOST = 4232;
    public static final int VENDOR_HINT_PERFORMANCE_MODE = 4241;
    public static final int VENDOR_HINT_PKG_INSTALL = 4259;
    public static final int VENDOR_HINT_PKG_UNINSTALL = 4260;
    public static final int VENDOR_HINT_ROTATION_ANIM_BOOST = 4240;
    public static final int VENDOR_HINT_ROTATION_LATENCY_BOOST = 4233;
    public static final int VENDOR_HINT_SCROLL_BOOST = 4224;
    public static final int VENDOR_HINT_SUBSEQ_LAUNCH_BOOST = 4226;
    public static final int VENDOR_HINT_TAP_EVENT = 4163;
    public static final int VENDOR_HINT_TOUCH_BOOST = 4229;
    public static final int VENDOR_HINT_WARM_LAUNCH = 4257;
    public static final int VENDOR_T_API_LEVEL = 33;
    private static Method sAcqAndReleaseFunc = null;
    private static Method sAcquireFunc = null;
    private static Method sFeedbackFunc = null;
    private static Method sFeedbackFuncExtn = null;
    private static Method sIOPStart = null;
    private static Method sIOPStop = null;
    private static boolean sIsLoaded = false;
    private static Class<?> sPerfClass = null;
    private static Method sPerfEventFunc = null;
    private static Method sPerfGetPerfHalVerFunc = null;
    private static Method sPerfGetPropFunc = null;
    private static Method sPerfHintFunc = null;
    private static Method sPerfSyncRequest = null;
    private static Method sReleaseFunc = null;
    private static Method sReleaseHandlerFunc = null;
    private static Method sUXEngineEvents = null;
    private static Method sUXEngineTrigger = null;
    private static Method sUxIOPStart = null;
    private static boolean sUxIsLoaded = false;
    private static Class<?> sUxPerfClass;
    private static Method sperfHintAcqRelFunc;
    private static Method sperfHintRenewFunc;
    public final int board_api_lvl;
    public final int board_first_api_lvl;
    private Object mPerf;
    private Object mUxPerf;

    public static class Draw {
        public static final int EVENT_TYPE_V1 = 1;
    }

    public static class Launch {
        public static final int ACTIVITY_LAUNCH_BOOST = 10;
        public static final int BOOST_GAME = 4;
        public static final int BOOST_V1 = 1;
        public static final int BOOST_V2 = 2;
        public static final int BOOST_V3 = 3;
        public static final int RESERVED_1 = 5;
        public static final int RESERVED_2 = 6;
        public static final int RESERVED_3 = 7;
        public static final int RESERVED_4 = 8;
        public static final int RESERVED_5 = 9;
        public static final int TYPE_ATTACH_APPLICATION = 103;
        public static final int TYPE_SERVICE_START = 100;
        public static final int TYPE_START_APP_FROM_BG = 102;
        public static final int TYPE_START_PROC = 101;
    }

    public static class Scroll {
        public static final int HORIZONTAL = 2;
        public static final int PANEL_VIEW = 3;
        public static final int PREFILING = 4;
        public static final int VERTICAL = 1;
    }

    public static class WorkloadType {
        public static final int APP = 1;
        public static final int BROWSER = 3;
        public static final int GAME = 2;
        public static final int NOT_KNOWN = 0;
        public static final int PREPROAPP = 4;
    }

    public BoostFramework() {
        this.board_first_api_lvl = SystemProperties.getInt("ro.board.first_api_level", 0);
        this.board_api_lvl = SystemProperties.getInt("ro.board.api_level", 0);
        this.mPerf = null;
        this.mUxPerf = null;
        if (new File(PERFORMANCE_JAR).exists()) {
            initFunctions();
            try {
                Class<?> cls = sPerfClass;
                if (cls != null) {
                    this.mPerf = cls.newInstance();
                }
                Class<?> cls2 = sUxPerfClass;
                if (cls2 != null) {
                    this.mUxPerf = cls2.newInstance();
                }
            } catch (Exception e) {
                Log.e(TAG, "BoostFramework() : Exception_2 = " + e);
            }
        }
    }

    public BoostFramework(Context context) {
        this(context, false);
    }

    public BoostFramework(Context context, boolean z) {
        Constructor<?> constructor;
        this.board_first_api_lvl = SystemProperties.getInt("ro.board.first_api_level", 0);
        this.board_api_lvl = SystemProperties.getInt("ro.board.api_level", 0);
        this.mPerf = null;
        this.mUxPerf = null;
        if (new File(PERFORMANCE_JAR).exists()) {
            initFunctions();
            try {
                Class<?> cls = sPerfClass;
                if (cls != null && (constructor = cls.getConstructor(Context.class)) != null) {
                    this.mPerf = constructor.newInstance(context);
                }
                Class<?> cls2 = sUxPerfClass;
                if (cls2 != null) {
                    if (z) {
                        Constructor<?> constructor2 = cls2.getConstructor(Context.class);
                        if (constructor2 != null) {
                            this.mUxPerf = constructor2.newInstance(context);
                            return;
                        }
                        return;
                    }
                    this.mUxPerf = cls2.newInstance();
                }
            } catch (Exception e) {
                Log.e(TAG, "BoostFramework() : Exception_3 = " + e);
            }
        }
    }

    public BoostFramework(boolean z) {
        Constructor<?> constructor;
        this.board_first_api_lvl = SystemProperties.getInt("ro.board.first_api_level", 0);
        this.board_api_lvl = SystemProperties.getInt("ro.board.api_level", 0);
        this.mPerf = null;
        this.mUxPerf = null;
        initFunctions();
        try {
            Class<?> cls = sPerfClass;
            if (cls != null && (constructor = cls.getConstructor(Boolean.TYPE)) != null) {
                this.mPerf = constructor.newInstance(Boolean.valueOf(z));
            }
            Class<?> cls2 = sUxPerfClass;
            if (cls2 != null) {
                this.mUxPerf = cls2.newInstance();
            }
        } catch (Exception e) {
            Log.e(TAG, "BoostFramework() : Exception_5 = " + e);
        }
    }

    private void initFunctions() {
        synchronized (BoostFramework.class) {
            if (!sIsLoaded) {
                try {
                    sPerfClass = Class.forName(PERFORMANCE_CLASS);
                    sAcquireFunc = sPerfClass.getMethod("perfLockAcquire", Integer.TYPE, int[].class);
                    sPerfHintFunc = sPerfClass.getMethod("perfHint", Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE);
                    Class[] clsArr = new Class[0];
                    sReleaseFunc = sPerfClass.getMethod("perfLockRelease", null);
                    sReleaseHandlerFunc = sPerfClass.getDeclaredMethod("perfLockReleaseHandler", Integer.TYPE);
                    sFeedbackFunc = sPerfClass.getMethod("perfGetFeedback", Integer.TYPE, String.class);
                    sFeedbackFuncExtn = sPerfClass.getMethod("perfGetFeedbackExtn", Integer.TYPE, String.class, Integer.TYPE, int[].class);
                    sIOPStart = sPerfClass.getDeclaredMethod("perfIOPrefetchStart", Integer.TYPE, String.class, String.class);
                    Class[] clsArr2 = new Class[0];
                    sIOPStop = sPerfClass.getDeclaredMethod("perfIOPrefetchStop", null);
                    sPerfGetPropFunc = sPerfClass.getMethod("perfGetProp", String.class, String.class);
                    sAcqAndReleaseFunc = sPerfClass.getMethod("perfLockAcqAndRelease", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, int[].class);
                    sPerfEventFunc = sPerfClass.getMethod("perfEvent", Integer.TYPE, String.class, Integer.TYPE, int[].class);
                    sPerfSyncRequest = sPerfClass.getMethod("perfSyncRequest", Integer.TYPE);
                    sperfHintAcqRelFunc = sPerfClass.getMethod("perfHintAcqRel", Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, int[].class);
                    sperfHintRenewFunc = sPerfClass.getMethod("perfHintRenew", Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, int[].class);
                    try {
                        Class[] clsArr3 = new Class[0];
                        sPerfGetPerfHalVerFunc = sPerfClass.getMethod("perfGetHalVer", null);
                    } catch (Exception unused) {
                        Log.i(TAG, "BoostFramework() : Exception_1 = perfGetHalVer not supported");
                        sPerfGetPerfHalVerFunc = null;
                    }
                    try {
                        sUXEngineEvents = sPerfClass.getDeclaredMethod("perfUXEngine_events", Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE, String.class);
                        sUXEngineTrigger = sPerfClass.getDeclaredMethod("perfUXEngine_trigger", Integer.TYPE);
                    } catch (Exception unused2) {
                        Log.i(TAG, "BoostFramework() : Exception_4 = PreferredApps not supported");
                    }
                    sIsLoaded = true;
                } catch (Exception e) {
                    Log.e(TAG, "BoostFramework() : Exception_1 = " + e);
                }
                try {
                    sUxPerfClass = Class.forName(UXPERFORMANCE_CLASS);
                    sUxIOPStart = sUxPerfClass.getDeclaredMethod("perfIOPrefetchStart", Integer.TYPE, String.class, String.class);
                    sUxIsLoaded = true;
                } catch (Exception e2) {
                    Log.e(TAG, "BoostFramework() Ux Perf: Exception = " + e2);
                }
            }
        }
    }

    public int perfLockAcquire(int i, int... iArr) {
        Method method = sAcquireFunc;
        if (method == null) {
            return -1;
        }
        try {
            return ((Integer) method.invoke(this.mPerf, Integer.valueOf(i), iArr)).intValue();
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfLockRelease() {
        Method method = sReleaseFunc;
        if (method == null) {
            return -1;
        }
        try {
            return ((Integer) method.invoke(this.mPerf, null)).intValue();
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfLockReleaseHandler(int i) {
        Method method = sReleaseHandlerFunc;
        if (method == null) {
            return -1;
        }
        try {
            return ((Integer) method.invoke(this.mPerf, Integer.valueOf(i))).intValue();
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfHint(int i, String str) {
        return perfHint(i, str, -1, -1);
    }

    public int perfHint(int i, String str, int i2) {
        return perfHint(i, str, i2, -1);
    }

    public int perfHint(int i, String str, int i2, int i3) {
        Method method = sPerfHintFunc;
        if (method == null) {
            return -1;
        }
        try {
            return ((Integer) method.invoke(this.mPerf, Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3))).intValue();
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public double getPerfHalVersion() {
        try {
            Method method = sPerfGetPerfHalVerFunc;
            if (method != null) {
                return ((Double) method.invoke(this.mPerf, null)).doubleValue();
            }
            return 2.200000047683716d;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return 2.200000047683716d;
        }
    }

    public int perfGetFeedback(int i, String str) {
        try {
            Method method = sFeedbackFunc;
            if (method != null) {
                return ((Integer) method.invoke(this.mPerf, Integer.valueOf(i), str)).intValue();
            }
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfGetFeedbackExtn(int i, String str, int i2, int... iArr) {
        try {
            Method method = sFeedbackFuncExtn;
            if (method != null) {
                return ((Integer) method.invoke(this.mPerf, Integer.valueOf(i), str, Integer.valueOf(i2), iArr)).intValue();
            }
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfIOPrefetchStart(int i, String str, String str2) {
        int i2;
        try {
            i2 = ((Integer) sIOPStart.invoke(this.mPerf, Integer.valueOf(i), str, str2)).intValue();
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            i2 = -1;
        }
        try {
            return ((Integer) sUxIOPStart.invoke(this.mUxPerf, Integer.valueOf(i), str, str2)).intValue();
        } catch (Exception e2) {
            Log.e(TAG, "Ux Perf Exception " + e2);
            return i2;
        }
    }

    public int perfIOPrefetchStop() {
        try {
            return ((Integer) sIOPStop.invoke(this.mPerf, null)).intValue();
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfUXEngine_events(int i, int i2, String str, int i3) {
        return perfUXEngine_events(i, i2, str, i3, null);
    }

    public int perfUXEngine_events(int i, int i2, String str, int i3, String str2) {
        try {
            Method method = sUXEngineEvents;
            if (method == null) {
                return -1;
            }
            return ((Integer) method.invoke(this.mPerf, Integer.valueOf(i), Integer.valueOf(i2), str, Integer.valueOf(i3), str2)).intValue();
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public String perfUXEngine_trigger(int i) {
        try {
            Method method = sUXEngineTrigger;
            if (method == null) {
                return null;
            }
            return (String) method.invoke(this.mPerf, Integer.valueOf(i));
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return null;
        }
    }

    public String perfSyncRequest(int i) {
        try {
            Method method = sPerfSyncRequest;
            if (method == null) {
                return null;
            }
            return (String) method.invoke(this.mPerf, Integer.valueOf(i));
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return null;
        }
    }

    public String perfGetProp(String str, String str2) {
        try {
            Method method = sPerfGetPropFunc;
            return method != null ? (String) method.invoke(this.mPerf, str, str2) : str2;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return "";
        }
    }

    public int perfLockAcqAndRelease(int i, int i2, int i3, int i4, int... iArr) {
        try {
            Method method = sAcqAndReleaseFunc;
            if (method != null) {
                return ((Integer) method.invoke(this.mPerf, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), iArr)).intValue();
            }
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public void perfEvent(int i, String str) {
        perfEvent(i, str, 0, new int[0]);
    }

    public void perfEvent(int i, String str, int i2, int... iArr) {
        try {
            Method method = sPerfEventFunc;
            if (method != null) {
                method.invoke(this.mPerf, Integer.valueOf(i), str, Integer.valueOf(i2), iArr);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
        }
    }

    public int perfHintAcqRel(int i, int i2, String str) {
        return perfHintAcqRel(i, i2, str, -1, -1, 0, new int[0]);
    }

    public int perfHintAcqRel(int i, int i2, String str, int i3) {
        return perfHintAcqRel(i, i2, str, i3, -1, 0, new int[0]);
    }

    public int perfHintAcqRel(int i, int i2, String str, int i3, int i4) {
        return perfHintAcqRel(i, i2, str, i3, i4, 0, new int[0]);
    }

    public int perfHintAcqRel(int i, int i2, String str, int i3, int i4, int i5, int... iArr) {
        try {
            Method method = sperfHintAcqRelFunc;
            if (method != null) {
                return ((Integer) method.invoke(this.mPerf, Integer.valueOf(i), Integer.valueOf(i2), str, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), iArr)).intValue();
            }
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfHintRenew(int i, int i2, String str) {
        return perfHintRenew(i, i2, str, -1, -1, 0, new int[0]);
    }

    public int perfHintRenew(int i, int i2, String str, int i3) {
        return perfHintRenew(i, i2, str, i3, -1, 0, new int[0]);
    }

    public int perfHintRenew(int i, int i2, String str, int i3, int i4) {
        return perfHintRenew(i, i2, str, i3, i4, 0, new int[0]);
    }

    public int perfHintRenew(int i, int i2, String str, int i3, int i4, int i5, int... iArr) {
        try {
            Method method = sperfHintRenewFunc;
            if (method != null) {
                return ((Integer) method.invoke(this.mPerf, Integer.valueOf(i), Integer.valueOf(i2), str, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), iArr)).intValue();
            }
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public static class ScrollOptimizer {
        public static final int FLING_END = 0;
        public static final int FLING_START = 1;
        private static final String QXPERFORMANCE_JAR = "/system/framework/QXPerformance.jar";
        private static final String SCROLL_OPT_CLASS = "com.qualcomm.qti.QXPerformance.ScrollOptimizer";
        private static final String SCROLL_OPT_PROP = "ro.vendor.perf.scroll_opt";
        private static Method sGetAdjustedAnimationClock = null;
        private static Method sGetFrameDelay = null;
        private static boolean sQXIsLoaded = false;
        private static Class<?> sQXPerfClass = null;
        private static boolean sScrollOptEnable = false;
        private static boolean sScrollOptProp = false;
        private static Method sSetBLASTBufferQueue;
        private static Method sSetFlingFlag;
        private static Method sSetFrameInterval;
        private static Method sSetMotionType;
        private static Method sSetUITaskStatus;
        private static Method sSetVsyncTime;
        private static Method sShouldUseVsync;

        /* JADX INFO: Access modifiers changed from: private */
        public static void initQXPerfFuncs() {
            if (sQXIsLoaded) {
                return;
            }
            try {
                boolean z = SystemProperties.getBoolean(SCROLL_OPT_PROP, false);
                sScrollOptProp = z;
                if (!z) {
                    sScrollOptEnable = false;
                    sQXIsLoaded = true;
                    return;
                }
                sQXPerfClass = new PathClassLoader(QXPERFORMANCE_JAR, ClassLoader.getSystemClassLoader()).loadClass(SCROLL_OPT_CLASS);
                sSetFrameInterval = sQXPerfClass.getMethod("setFrameInterval", Long.TYPE);
                sSetBLASTBufferQueue = sQXPerfClass.getMethod("setBLASTBufferQueue", BLASTBufferQueue.class);
                sSetMotionType = sQXPerfClass.getMethod("setMotionType", Integer.TYPE);
                sSetVsyncTime = sQXPerfClass.getMethod("setVsyncTime", Long.TYPE);
                sSetUITaskStatus = sQXPerfClass.getMethod("setUITaskStatus", Boolean.TYPE);
                sSetFlingFlag = sQXPerfClass.getMethod("setFlingFlag", Integer.TYPE);
                Class[] clsArr = new Class[0];
                sShouldUseVsync = sQXPerfClass.getMethod("shouldUseVsync", null);
                sGetFrameDelay = sQXPerfClass.getMethod("getFrameDelay", Long.TYPE);
                sGetAdjustedAnimationClock = sQXPerfClass.getMethod("getAdjustedAnimationClock", Long.TYPE);
                sQXIsLoaded = true;
            } catch (Exception e) {
                Log.e(BoostFramework.TAG, "initQXPerfFuncs failed");
                e.printStackTrace();
            }
        }

        public static void setFrameInterval(final long j) {
            Method method;
            if (sQXIsLoaded) {
                if (!sScrollOptEnable || (method = sSetFrameInterval) == null) {
                    return;
                }
                try {
                    method.invoke(null, Long.valueOf(j));
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            new Thread(new Runnable() { // from class: android.util.BoostFramework.ScrollOptimizer.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ScrollOptimizer.initQXPerfFuncs();
                        if (!ScrollOptimizer.sScrollOptProp || ScrollOptimizer.sSetFrameInterval == null) {
                            return;
                        }
                        ScrollOptimizer.sSetFrameInterval.invoke(null, Long.valueOf(j));
                        ScrollOptimizer.sScrollOptEnable = true;
                    } catch (Exception e2) {
                        Log.e(BoostFramework.TAG, "Failed to run initThread.");
                        e2.printStackTrace();
                    }
                }
            }).start();
        }

        public static void setBLASTBufferQueue(BLASTBufferQueue bLASTBufferQueue) {
            Method method;
            if (!sScrollOptEnable || (method = sSetBLASTBufferQueue) == null) {
                return;
            }
            try {
                method.invoke(null, bLASTBufferQueue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void setMotionType(int i) {
            Method method;
            if (!sScrollOptEnable || (method = sSetMotionType) == null) {
                return;
            }
            try {
                method.invoke(null, Integer.valueOf(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void setVsyncTime(long j) {
            Method method;
            if (!sScrollOptEnable || (method = sSetVsyncTime) == null) {
                return;
            }
            try {
                method.invoke(null, Long.valueOf(j));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void setUITaskStatus(boolean z) {
            Method method;
            if (!sScrollOptEnable || (method = sSetUITaskStatus) == null) {
                return;
            }
            try {
                method.invoke(null, Boolean.valueOf(z));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void setFlingFlag(int i) {
            Method method;
            if (!sScrollOptEnable || (method = sSetFlingFlag) == null) {
                return;
            }
            try {
                method.invoke(null, Integer.valueOf(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static boolean shouldUseVsync(boolean z) {
            Method method;
            if (sScrollOptEnable && (method = sShouldUseVsync) != null) {
                try {
                    return ((Boolean) method.invoke(null, null)).booleanValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return z;
        }

        public static long getFrameDelay(long j, long j2) {
            Method method;
            if (sScrollOptEnable && (method = sGetFrameDelay) != null) {
                try {
                    return ((Long) method.invoke(null, Long.valueOf(j2))).longValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return j;
        }

        public static long getAdjustedAnimationClock(long j) {
            Method method;
            if (sScrollOptEnable && (method = sGetAdjustedAnimationClock) != null) {
                try {
                    return ((Long) method.invoke(null, Long.valueOf(j))).longValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return j;
        }
    }
}
