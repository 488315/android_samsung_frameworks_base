package android.util;

import android.content.Context;
import android.graphics.BLASTBufferQueue;
import android.os.SystemProperties;

import dalvik.system.PathClassLoader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

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
    public final int board_api_lvl;
    public final int board_first_api_lvl;
    private Object mPerf;
    private Object mUxPerf;
    private static boolean sIsLoaded = false;
    private static Class<?> sPerfClass = null;
    private static Method sAcquireFunc = null;
    private static Method sPerfHintFunc = null;
    private static Method sReleaseFunc = null;
    private static Method sReleaseHandlerFunc = null;
    private static Method sFeedbackFunc = null;
    private static Method sFeedbackFuncExtn = null;
    private static Method sPerfGetPropFunc = null;
    private static Method sAcqAndReleaseFunc = null;
    private static Method sperfHintAcqRelFunc = null;
    private static Method sperfHintRenewFunc = null;
    private static Method sPerfEventFunc = null;
    private static Method sPerfGetPerfHalVerFunc = null;
    private static Method sPerfSyncRequest = null;
    private static Method sIOPStart = null;
    private static Method sIOPStop = null;
    private static Method sUXEngineEvents = null;
    private static Method sUXEngineTrigger = null;
    private static boolean sUxIsLoaded = false;
    private static Class<?> sUxPerfClass = null;
    private static Method sUxIOPStart = null;

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
        File fileJarPath = new File(PERFORMANCE_JAR);
        if (!fileJarPath.exists()) {
            return;
        }
        initFunctions();
        try {
            if (sPerfClass != null) {
                this.mPerf = sPerfClass.newInstance();
            }
            if (sUxPerfClass != null) {
                this.mUxPerf = sUxPerfClass.newInstance();
            }
        } catch (Exception e) {
            Log.e(TAG, "BoostFramework() : Exception_2 = " + e);
        }
    }

    public BoostFramework(Context context) {
        this(context, false);
    }

    public BoostFramework(Context context, boolean isTrusted) {
        Constructor cons;
        this.board_first_api_lvl = SystemProperties.getInt("ro.board.first_api_level", 0);
        this.board_api_lvl = SystemProperties.getInt("ro.board.api_level", 0);
        this.mPerf = null;
        this.mUxPerf = null;
        File fileJarPath = new File(PERFORMANCE_JAR);
        if (!fileJarPath.exists()) {
            return;
        }
        initFunctions();
        try {
            if (sPerfClass != null && (cons = sPerfClass.getConstructor(Context.class)) != null) {
                this.mPerf = cons.newInstance(context);
            }
            if (sUxPerfClass != null) {
                if (isTrusted) {
                    Constructor cons2 = sUxPerfClass.getConstructor(Context.class);
                    if (cons2 != null) {
                        this.mUxPerf = cons2.newInstance(context);
                    }
                    return;
                }
                this.mUxPerf = sUxPerfClass.newInstance();
            }
        } catch (Exception e) {
            Log.e(TAG, "BoostFramework() : Exception_3 = " + e);
        }
    }

    public BoostFramework(boolean isUntrustedDomain) {
        Constructor cons;
        this.board_first_api_lvl = SystemProperties.getInt("ro.board.first_api_level", 0);
        this.board_api_lvl = SystemProperties.getInt("ro.board.api_level", 0);
        this.mPerf = null;
        this.mUxPerf = null;
        initFunctions();
        try {
            if (sPerfClass != null && (cons = sPerfClass.getConstructor(Boolean.TYPE)) != null) {
                this.mPerf = cons.newInstance(Boolean.valueOf(isUntrustedDomain));
            }
            if (sUxPerfClass != null) {
                this.mUxPerf = sUxPerfClass.newInstance();
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
                    Class[] argClasses = {Integer.TYPE, int[].class};
                    sAcquireFunc = sPerfClass.getMethod("perfLockAcquire", argClasses);
                    Class[] argClasses2 = {Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE};
                    sPerfHintFunc = sPerfClass.getMethod("perfHint", argClasses2);
                    sReleaseFunc = sPerfClass.getMethod("perfLockRelease", new Class[0]);
                    Class[] argClasses3 = {Integer.TYPE};
                    sReleaseHandlerFunc =
                            sPerfClass.getDeclaredMethod("perfLockReleaseHandler", argClasses3);
                    Class[] argClasses4 = {Integer.TYPE, String.class};
                    sFeedbackFunc = sPerfClass.getMethod("perfGetFeedback", argClasses4);
                    Class[] argClasses5 = {Integer.TYPE, String.class, Integer.TYPE, int[].class};
                    sFeedbackFuncExtn = sPerfClass.getMethod("perfGetFeedbackExtn", argClasses5);
                    Class[] argClasses6 = {Integer.TYPE, String.class, String.class};
                    sIOPStart = sPerfClass.getDeclaredMethod("perfIOPrefetchStart", argClasses6);
                    sIOPStop = sPerfClass.getDeclaredMethod("perfIOPrefetchStop", new Class[0]);
                    Class[] argClasses7 = {String.class, String.class};
                    sPerfGetPropFunc = sPerfClass.getMethod("perfGetProp", argClasses7);
                    Class[] argClasses8 = {
                        Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, int[].class
                    };
                    sAcqAndReleaseFunc = sPerfClass.getMethod("perfLockAcqAndRelease", argClasses8);
                    Class[] argClasses9 = {Integer.TYPE, String.class, Integer.TYPE, int[].class};
                    sPerfEventFunc = sPerfClass.getMethod("perfEvent", argClasses9);
                    Class[] argClasses10 = {Integer.TYPE};
                    sPerfSyncRequest = sPerfClass.getMethod("perfSyncRequest", argClasses10);
                    Class[] argClasses11 = {
                        Integer.TYPE,
                        Integer.TYPE,
                        String.class,
                        Integer.TYPE,
                        Integer.TYPE,
                        Integer.TYPE,
                        int[].class
                    };
                    sperfHintAcqRelFunc = sPerfClass.getMethod("perfHintAcqRel", argClasses11);
                    Class[] argClasses12 = {
                        Integer.TYPE,
                        Integer.TYPE,
                        String.class,
                        Integer.TYPE,
                        Integer.TYPE,
                        Integer.TYPE,
                        int[].class
                    };
                    sperfHintRenewFunc = sPerfClass.getMethod("perfHintRenew", argClasses12);
                    try {
                        sPerfGetPerfHalVerFunc =
                                sPerfClass.getMethod("perfGetHalVer", new Class[0]);
                    } catch (Exception e) {
                        Log.i(TAG, "BoostFramework() : Exception_1 = perfGetHalVer not supported");
                        sPerfGetPerfHalVerFunc = null;
                    }
                    try {
                        Class[] argClasses13 = {
                            Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE, String.class
                        };
                        sUXEngineEvents =
                                sPerfClass.getDeclaredMethod("perfUXEngine_events", argClasses13);
                        Class[] argClasses14 = {Integer.TYPE};
                        sUXEngineTrigger =
                                sPerfClass.getDeclaredMethod("perfUXEngine_trigger", argClasses14);
                    } catch (Exception e2) {
                        Log.i(TAG, "BoostFramework() : Exception_4 = PreferredApps not supported");
                    }
                    sIsLoaded = true;
                } catch (Exception e3) {
                    Log.e(TAG, "BoostFramework() : Exception_1 = " + e3);
                }
                try {
                    sUxPerfClass = Class.forName(UXPERFORMANCE_CLASS);
                    Class[] argUxClasses = {Integer.TYPE, String.class, String.class};
                    sUxIOPStart =
                            sUxPerfClass.getDeclaredMethod("perfIOPrefetchStart", argUxClasses);
                    sUxIsLoaded = true;
                } catch (Exception e4) {
                    Log.e(TAG, "BoostFramework() Ux Perf: Exception = " + e4);
                }
            }
        }
    }

    public int perfLockAcquire(int duration, int... list) {
        if (sAcquireFunc == null) {
            return -1;
        }
        try {
            Object retVal = sAcquireFunc.invoke(this.mPerf, Integer.valueOf(duration), list);
            int ret = ((Integer) retVal).intValue();
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfLockRelease() {
        if (sReleaseFunc == null) {
            return -1;
        }
        try {
            Object retVal = sReleaseFunc.invoke(this.mPerf, new Object[0]);
            int ret = ((Integer) retVal).intValue();
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfLockReleaseHandler(int handle) {
        if (sReleaseHandlerFunc == null) {
            return -1;
        }
        try {
            Object retVal = sReleaseHandlerFunc.invoke(this.mPerf, Integer.valueOf(handle));
            int ret = ((Integer) retVal).intValue();
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfHint(int hint, String userDataStr) {
        return perfHint(hint, userDataStr, -1, -1);
    }

    public int perfHint(int hint, String userDataStr, int userData) {
        return perfHint(hint, userDataStr, userData, -1);
    }

    public int perfHint(int hint, String userDataStr, int userData1, int userData2) {
        if (sPerfHintFunc == null) {
            return -1;
        }
        try {
            Object retVal =
                    sPerfHintFunc.invoke(
                            this.mPerf,
                            Integer.valueOf(hint),
                            userDataStr,
                            Integer.valueOf(userData1),
                            Integer.valueOf(userData2));
            int ret = ((Integer) retVal).intValue();
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public double getPerfHalVersion() {
        try {
            if (sPerfGetPerfHalVerFunc == null) {
                return 2.200000047683716d;
            }
            Object ret = sPerfGetPerfHalVerFunc.invoke(this.mPerf, new Object[0]);
            double retVal = ((Double) ret).doubleValue();
            return retVal;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return 2.200000047683716d;
        }
    }

    public int perfGetFeedback(int req, String pkg_name) {
        try {
            if (sFeedbackFunc == null) {
                return -1;
            }
            Object retVal = sFeedbackFunc.invoke(this.mPerf, Integer.valueOf(req), pkg_name);
            int ret = ((Integer) retVal).intValue();
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfGetFeedbackExtn(int req, String pkg_name, int numArgs, int... list) {
        try {
            if (sFeedbackFuncExtn == null) {
                return -1;
            }
            Object retVal =
                    sFeedbackFuncExtn.invoke(
                            this.mPerf,
                            Integer.valueOf(req),
                            pkg_name,
                            Integer.valueOf(numArgs),
                            list);
            int ret = ((Integer) retVal).intValue();
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfIOPrefetchStart(int pid, String pkgName, String codePath) {
        int ret = -1;
        try {
            Object retVal = sIOPStart.invoke(this.mPerf, Integer.valueOf(pid), pkgName, codePath);
            ret = ((Integer) retVal).intValue();
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
        }
        try {
            Object retVal2 =
                    sUxIOPStart.invoke(this.mUxPerf, Integer.valueOf(pid), pkgName, codePath);
            int ret2 = ((Integer) retVal2).intValue();
            return ret2;
        } catch (Exception e2) {
            Log.e(TAG, "Ux Perf Exception " + e2);
            return ret;
        }
    }

    public int perfIOPrefetchStop() {
        try {
            Object retVal = sIOPStop.invoke(this.mPerf, new Object[0]);
            int ret = ((Integer) retVal).intValue();
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfUXEngine_events(int opcode, int pid, String pkgName, int lat) {
        return perfUXEngine_events(opcode, pid, pkgName, lat, null);
    }

    public int perfUXEngine_events(int opcode, int pid, String pkgName, int lat, String codePath) {
        try {
            if (sUXEngineEvents == null) {
                return -1;
            }
            Object retVal =
                    sUXEngineEvents.invoke(
                            this.mPerf,
                            Integer.valueOf(opcode),
                            Integer.valueOf(pid),
                            pkgName,
                            Integer.valueOf(lat),
                            codePath);
            int ret = ((Integer) retVal).intValue();
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public String perfUXEngine_trigger(int opcode) {
        try {
            if (sUXEngineTrigger == null) {
                return null;
            }
            Object retVal = sUXEngineTrigger.invoke(this.mPerf, Integer.valueOf(opcode));
            String ret = (String) retVal;
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return null;
        }
    }

    public String perfSyncRequest(int opcode) {
        try {
            if (sPerfSyncRequest == null) {
                return null;
            }
            Object retVal = sPerfSyncRequest.invoke(this.mPerf, Integer.valueOf(opcode));
            String ret = (String) retVal;
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return null;
        }
    }

    public String perfGetProp(String prop_name, String def_val) {
        try {
            if (sPerfGetPropFunc != null) {
                Object retVal = sPerfGetPropFunc.invoke(this.mPerf, prop_name, def_val);
                String ret = (String) retVal;
                return ret;
            }
            return def_val;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return "";
        }
    }

    public int perfLockAcqAndRelease(
            int handle, int duration, int numArgs, int reserveNumArgs, int... list) {
        try {
            if (sAcqAndReleaseFunc == null) {
                return -1;
            }
            Object retVal =
                    sAcqAndReleaseFunc.invoke(
                            this.mPerf,
                            Integer.valueOf(handle),
                            Integer.valueOf(duration),
                            Integer.valueOf(numArgs),
                            Integer.valueOf(reserveNumArgs),
                            list);
            int ret = ((Integer) retVal).intValue();
            return ret;
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
            return -1;
        }
    }

    public void perfEvent(int eventId, String pkg_name) {
        perfEvent(eventId, pkg_name, 0, new int[0]);
    }

    public void perfEvent(int eventId, String pkg_name, int numArgs, int... list) {
        try {
            if (sPerfEventFunc != null) {
                sPerfEventFunc.invoke(
                        this.mPerf,
                        Integer.valueOf(eventId),
                        pkg_name,
                        Integer.valueOf(numArgs),
                        list);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
        }
    }

    public int perfHintAcqRel(int handle, int hint, String pkg_name) {
        return perfHintAcqRel(handle, hint, pkg_name, -1, -1, 0, new int[0]);
    }

    public int perfHintAcqRel(int handle, int hint, String pkg_name, int duration) {
        return perfHintAcqRel(handle, hint, pkg_name, duration, -1, 0, new int[0]);
    }

    public int perfHintAcqRel(int handle, int hint, String pkg_name, int duration, int hintType) {
        return perfHintAcqRel(handle, hint, pkg_name, duration, hintType, 0, new int[0]);
    }

    public int perfHintAcqRel(
            int handle,
            int hint,
            String pkg_name,
            int duration,
            int hintType,
            int numArgs,
            int... list) {
        try {
            if (sperfHintAcqRelFunc == null) {
                return -1;
            }
            try {
                Object retVal =
                        sperfHintAcqRelFunc.invoke(
                                this.mPerf,
                                Integer.valueOf(handle),
                                Integer.valueOf(hint),
                                pkg_name,
                                Integer.valueOf(duration),
                                Integer.valueOf(hintType),
                                Integer.valueOf(numArgs),
                                list);
                int ret = ((Integer) retVal).intValue();
                return ret;
            } catch (Exception e) {
                retVal = e;
                Log.e(TAG, "Exception " + retVal);
                return -1;
            }
        } catch (Exception e2) {
            retVal = e2;
        }
    }

    public int perfHintRenew(int handle, int hint, String pkg_name) {
        return perfHintRenew(handle, hint, pkg_name, -1, -1, 0, new int[0]);
    }

    public int perfHintRenew(int handle, int hint, String pkg_name, int duration) {
        return perfHintRenew(handle, hint, pkg_name, duration, -1, 0, new int[0]);
    }

    public int perfHintRenew(int handle, int hint, String pkg_name, int duration, int hintType) {
        return perfHintRenew(handle, hint, pkg_name, duration, hintType, 0, new int[0]);
    }

    public int perfHintRenew(
            int handle,
            int hint,
            String pkg_name,
            int duration,
            int hintType,
            int numArgs,
            int... list) {
        try {
            if (sperfHintRenewFunc == null) {
                return -1;
            }
            try {
                Object retVal =
                        sperfHintRenewFunc.invoke(
                                this.mPerf,
                                Integer.valueOf(handle),
                                Integer.valueOf(hint),
                                pkg_name,
                                Integer.valueOf(duration),
                                Integer.valueOf(hintType),
                                Integer.valueOf(numArgs),
                                list);
                int ret = ((Integer) retVal).intValue();
                return ret;
            } catch (Exception e) {
                retVal = e;
                Log.e(TAG, "Exception " + retVal);
                return -1;
            }
        } catch (Exception e2) {
            retVal = e2;
        }
    }

    public static class ScrollOptimizer {
        public static final int FLING_END = 0;
        public static final int FLING_START = 1;
        private static final String QXPERFORMANCE_JAR = "/system/framework/QXPerformance.jar";
        private static final String SCROLL_OPT_CLASS =
                "com.qualcomm.qti.QXPerformance.ScrollOptimizer";
        private static final String SCROLL_OPT_PROP = "ro.vendor.perf.scroll_opt";
        private static boolean sScrollOptProp = false;
        private static boolean sScrollOptEnable = false;
        private static boolean sQXIsLoaded = false;
        private static Class<?> sQXPerfClass = null;
        private static Method sSetFrameInterval = null;
        private static Method sSetBLASTBufferQueue = null;
        private static Method sSetMotionType = null;
        private static Method sSetVsyncTime = null;
        private static Method sSetUITaskStatus = null;
        private static Method sSetFlingFlag = null;
        private static Method sShouldUseVsync = null;
        private static Method sGetFrameDelay = null;
        private static Method sGetAdjustedAnimationClock = null;

        /* JADX INFO: Access modifiers changed from: private */
        public static void initQXPerfFuncs() {
            if (sQXIsLoaded) {
                return;
            }
            try {
                sScrollOptProp = SystemProperties.getBoolean(SCROLL_OPT_PROP, false);
                if (!sScrollOptProp) {
                    sScrollOptEnable = false;
                    sQXIsLoaded = true;
                    return;
                }
                PathClassLoader qXPerfClassLoader =
                        new PathClassLoader(QXPERFORMANCE_JAR, ClassLoader.getSystemClassLoader());
                sQXPerfClass = qXPerfClassLoader.loadClass(SCROLL_OPT_CLASS);
                Class[] argClasses = {Long.TYPE};
                sSetFrameInterval = sQXPerfClass.getMethod("setFrameInterval", argClasses);
                Class[] argClasses2 = {BLASTBufferQueue.class};
                sSetBLASTBufferQueue = sQXPerfClass.getMethod("setBLASTBufferQueue", argClasses2);
                Class[] argClasses3 = {Integer.TYPE};
                sSetMotionType = sQXPerfClass.getMethod("setMotionType", argClasses3);
                Class[] argClasses4 = {Long.TYPE};
                sSetVsyncTime = sQXPerfClass.getMethod("setVsyncTime", argClasses4);
                Class[] argClasses5 = {Boolean.TYPE};
                sSetUITaskStatus = sQXPerfClass.getMethod("setUITaskStatus", argClasses5);
                Class[] argClasses6 = {Integer.TYPE};
                sSetFlingFlag = sQXPerfClass.getMethod("setFlingFlag", argClasses6);
                sShouldUseVsync = sQXPerfClass.getMethod("shouldUseVsync", new Class[0]);
                Class[] argClasses7 = {Long.TYPE};
                sGetFrameDelay = sQXPerfClass.getMethod("getFrameDelay", argClasses7);
                Class[] argClasses8 = {Long.TYPE};
                sGetAdjustedAnimationClock =
                        sQXPerfClass.getMethod("getAdjustedAnimationClock", argClasses8);
                sQXIsLoaded = true;
            } catch (Exception e) {
                Log.e(BoostFramework.TAG, "initQXPerfFuncs failed");
                e.printStackTrace();
            }
        }

        public static void setFrameInterval(final long frameIntervalNanos) {
            if (sQXIsLoaded) {
                if (sScrollOptEnable && sSetFrameInterval != null) {
                    try {
                        sSetFrameInterval.invoke(null, Long.valueOf(frameIntervalNanos));
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                return;
            }
            Thread initThread =
                    new Thread(
                            new Runnable() { // from class:
                                             // android.util.BoostFramework.ScrollOptimizer.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    try {
                                        ScrollOptimizer.initQXPerfFuncs();
                                        if (ScrollOptimizer.sScrollOptProp
                                                && ScrollOptimizer.sSetFrameInterval != null) {
                                            ScrollOptimizer.sSetFrameInterval.invoke(
                                                    null, Long.valueOf(frameIntervalNanos));
                                            ScrollOptimizer.sScrollOptEnable = true;
                                        }
                                    } catch (Exception e2) {
                                        Log.e(BoostFramework.TAG, "Failed to run initThread.");
                                        e2.printStackTrace();
                                    }
                                }
                            });
            initThread.start();
        }

        public static void setBLASTBufferQueue(BLASTBufferQueue blastBufferQueue) {
            if (sScrollOptEnable && sSetBLASTBufferQueue != null) {
                try {
                    sSetBLASTBufferQueue.invoke(null, blastBufferQueue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static void setMotionType(int eventType) {
            if (sScrollOptEnable && sSetMotionType != null) {
                try {
                    sSetMotionType.invoke(null, Integer.valueOf(eventType));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static void setVsyncTime(long vsyncTimeNanos) {
            if (sScrollOptEnable && sSetVsyncTime != null) {
                try {
                    sSetVsyncTime.invoke(null, Long.valueOf(vsyncTimeNanos));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static void setUITaskStatus(boolean running) {
            if (sScrollOptEnable && sSetUITaskStatus != null) {
                try {
                    sSetUITaskStatus.invoke(null, Boolean.valueOf(running));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static void setFlingFlag(int flag) {
            if (sScrollOptEnable && sSetFlingFlag != null) {
                try {
                    sSetFlingFlag.invoke(null, Integer.valueOf(flag));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static boolean shouldUseVsync(boolean defaultVsyncFlag) {
            if (!sScrollOptEnable || sShouldUseVsync == null) {
                return defaultVsyncFlag;
            }
            try {
                Object retVal = sShouldUseVsync.invoke(null, new Object[0]);
                boolean useVsync = ((Boolean) retVal).booleanValue();
                return useVsync;
            } catch (Exception e) {
                e.printStackTrace();
                return defaultVsyncFlag;
            }
        }

        public static long getFrameDelay(long defaultDelay, long lastFrameTimeNanos) {
            if (!sScrollOptEnable || sGetFrameDelay == null) {
                return defaultDelay;
            }
            try {
                Object retVal = sGetFrameDelay.invoke(null, Long.valueOf(lastFrameTimeNanos));
                long frameDelay = ((Long) retVal).longValue();
                return frameDelay;
            } catch (Exception e) {
                e.printStackTrace();
                return defaultDelay;
            }
        }

        public static long getAdjustedAnimationClock(long frameTimeNanos) {
            if (!sScrollOptEnable || sGetAdjustedAnimationClock == null) {
                return frameTimeNanos;
            }
            try {
                Object retVal =
                        sGetAdjustedAnimationClock.invoke(null, Long.valueOf(frameTimeNanos));
                long newFrameTimeNanos = ((Long) retVal).longValue();
                return newFrameTimeNanos;
            } catch (Exception e) {
                e.printStackTrace();
                return frameTimeNanos;
            }
        }
    }
}
