package android.util;

import java.io.File;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class BoostFrameworkMtk {
    private static final String PERFORMANCE_CLASS = "com.mediatek.boostframework.Performance";
    private static final String PERFORMANCE_JAR = "/system/framework/mediatek-framework.jar";
    public static final int PERF_RES_CPUCORE_MAX_CLUSTER_0 = 8404992;
    public static final int PERF_RES_CPUCORE_MAX_CLUSTER_1 = 8405248;
    public static final int PERF_RES_CPUCORE_MIN_CLUSTER_0 = 8388608;
    public static final int PERF_RES_CPUCORE_MIN_CLUSTER_1 = 8388864;
    public static final int PERF_RES_GPU_FREQ_MAX = 12599296;
    public static final int PERF_RES_GPU_FREQ_MIN = 12582912;
    public static final int PERF_RES_SCHED_BOOST = 21037056;
    private static final String TAG = "BoostFrameworkMtk";
    private Object mPerf;
    private static boolean sIsLoaded = false;
    private static Class<?> sPerfClass = null;
    private static Method sAcquireFunc = null;
    private static Method sReleaseFunc = null;

    public BoostFrameworkMtk() {
        this.mPerf = null;
        File fileJarPath = new File(PERFORMANCE_JAR);
        if (!fileJarPath.exists()) {
            return;
        }
        initFunctions();
        try {
            Class<?> cls = sPerfClass;
            if (cls != null) {
                this.mPerf = cls.newInstance();
            }
        } catch (Exception e) {
            Log.m96e(TAG, "BoostFrameworkMtk() : Exception_2 = " + e);
        }
    }

    private void initFunctions() {
        synchronized (BoostFrameworkMtk.class) {
            if (!sIsLoaded) {
                try {
                    sPerfClass = Class.forName(PERFORMANCE_CLASS);
                    Class[] argClasses = {Integer.TYPE, int[].class};
                    sAcquireFunc = sPerfClass.getMethod("perfLockAcquire", argClasses);
                    sReleaseFunc = sPerfClass.getMethod("perfLockRelease", new Class[0]);
                    sIsLoaded = true;
                } catch (Exception e) {
                    Log.m96e(TAG, "BoostFrameworkMtk() : Exception_1 = " + e);
                }
            }
        }
    }

    public int perfLockAcquire(int duration, int... list) {
        Method method = sAcquireFunc;
        if (method == null || method == null) {
            return -1;
        }
        try {
            Object retVal = method.invoke(this.mPerf, Integer.valueOf(duration), list);
            int ret = ((Integer) retVal).intValue();
            return ret;
        } catch (Exception e) {
            Log.m96e(TAG, "Exception " + e);
            return -1;
        }
    }

    public int perfLockRelease() {
        Method method = sReleaseFunc;
        if (method == null || method == null) {
            return -1;
        }
        try {
            Object retVal = method.invoke(this.mPerf, new Object[0]);
            int ret = ((Integer) retVal).intValue();
            return ret;
        } catch (Exception e) {
            Log.m96e(TAG, "Exception " + e);
            return -1;
        }
    }
}
