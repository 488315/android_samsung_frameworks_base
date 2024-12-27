package android.content.res;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FrameworkStatsLog;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public final class ResourceTimer {
    private static final String TAG = "ResourceTimer";
    private static Handler mHandler;
    private static int[] sApiMap;
    private static Config sConfig;
    private static int sCurrentPoint;
    private static ResourceTimer sManager;
    private static Timer[] sTimers;
    private static boolean sEnabled = true;
    private static boolean sIncrementalMetrics = true;
    private static boolean ENABLE_DEBUG = false;
    private static final Object sLock = new Object();
    private static final long sProcessStart = SystemClock.uptimeMillis();
    private static final long[] sPublicationPoints = {5, 60, 720};
    private static long sLastUpdated = 0;

    private static native int nativeEnableTimers(Config config);

    private static native int nativeGetTimers(Timer[] timerArr, boolean z);

    private static class Config {
        int maxBuckets;
        int maxLargest;
        int maxTimer;
        String[] timers;

        private Config() {}
    }

    private static class Timer {
        int count;
        int[] largest;
        int maxtime;
        int mintime;
        int[] percentile;
        long total;

        private Timer() {}

        public String toString() {
            return TextUtils.formatSimple(
                    "%d:%d:%d:%d",
                    Integer.valueOf(this.count),
                    Long.valueOf(this.total),
                    Integer.valueOf(this.mintime),
                    Integer.valueOf(this.maxtime));
        }
    }

    private ResourceTimer() {
        throw new RuntimeException("ResourceTimer constructor");
    }

    public static void start() {
        synchronized (sLock) {
            if (sEnabled) {
                if (mHandler != null) {
                    return;
                }
                if (Looper.getMainLooper() == null) {
                    throw new RuntimeException("ResourceTimer started too early");
                }
                mHandler =
                        new Handler(Looper.getMainLooper()) { // from class:
                            // android.content.res.ResourceTimer.1
                            @Override // android.os.Handler
                            public void handleMessage(Message msg) {
                                ResourceTimer.handleMessage(msg);
                            }
                        };
                byte b = 0;
                sConfig = new Config();
                nativeEnableTimers(sConfig);
                sTimers = new Timer[sConfig.maxTimer];
                for (int i = 0; i < sTimers.length; i++) {
                    sTimers[i] = new Timer();
                    sTimers[i].percentile = new int[sConfig.maxBuckets];
                    sTimers[i].largest = new int[sConfig.maxLargest];
                }
                sApiMap = new int[sConfig.maxTimer];
                for (int i2 = 0; i2 < sApiMap.length; i2++) {
                    if (sConfig.timers[i2].equals("GetResourceValue")) {
                        sApiMap[i2] = 1;
                    } else if (sConfig.timers[i2].equals("RetrieveAttributes")) {
                        sApiMap[i2] = 2;
                    } else {
                        sApiMap[i2] = 0;
                    }
                }
                sCurrentPoint = 0;
                startTimer();
            }
        }
    }

    public static void handleMessage(Message msg) {
        synchronized (sLock) {
            publish();
            startTimer();
        }
    }

    private static void startTimer() {
        long repeated;
        if (sCurrentPoint < sPublicationPoints.length) {
            repeated = sPublicationPoints[sCurrentPoint];
        } else {
            long repeated2 = sPublicationPoints[sPublicationPoints.length - 1];
            int prelude = sPublicationPoints.length - 1;
            repeated = (sCurrentPoint - prelude) * repeated2;
        }
        long delay = repeated * 60000;
        if (ENABLE_DEBUG) {
            delay /= 60;
        }
        mHandler.sendEmptyMessageAtTime(0, sProcessStart + delay);
    }

    private static void update(boolean reset) {
        nativeGetTimers(sTimers, reset);
        sLastUpdated = SystemClock.uptimeMillis();
    }

    private static void publish() {
        update(true);
        for (int i = 0; i < sTimers.length; i++) {
            Timer timer = sTimers[i];
            if (timer.count > 0) {
                Log.i(
                        TAG,
                        TextUtils.formatSimple(
                                "%s count=%d pvalues=%s",
                                sConfig.timers[i],
                                Integer.valueOf(timer.count),
                                packedString(timer.percentile)));
                if (sApiMap[i] != 0) {
                    FrameworkStatsLog.write(
                            517,
                            sApiMap[i],
                            timer.count,
                            timer.total,
                            timer.percentile[0],
                            timer.percentile[1],
                            timer.percentile[2],
                            timer.percentile[3],
                            timer.largest[0],
                            timer.largest[1],
                            timer.largest[2],
                            timer.largest[3],
                            timer.largest[4]);
                }
            }
        }
        int i2 = sCurrentPoint;
        sCurrentPoint = i2 + 1;
    }

    private static String packedString(int[] a) {
        return Arrays.toString(a).replaceAll("[\\]\\[ ]", "");
    }

    public static void dumpTimers(ParcelFileDescriptor pfd, String[] args) {
        FileOutputStream fout = new FileOutputStream(pfd.getFileDescriptor());
        PrintWriter pw = new FastPrintWriter(fout);
        synchronized (sLock) {
            if (sEnabled && sConfig != null) {
                boolean refresh = Arrays.asList(args).contains("-refresh");
                synchronized (sLock) {
                    update(refresh);
                    long runtime = sLastUpdated - sProcessStart;
                    pw.format(
                            "  config runtime=%d proc=%s\n",
                            Long.valueOf(runtime), Process.myProcessName());
                    for (int i = 0; i < sTimers.length; i++) {
                        Timer t = sTimers[i];
                        if (t.count != 0) {
                            String name = sConfig.timers[i];
                            pw.format(
                                    "  stats timer=%s cnt=%d avg=%d min=%d max=%d pval=%s"
                                            + " largest=%s\n",
                                    name,
                                    Integer.valueOf(t.count),
                                    Long.valueOf(t.total / t.count),
                                    Integer.valueOf(t.mintime),
                                    Integer.valueOf(t.maxtime),
                                    packedString(t.percentile),
                                    packedString(t.largest));
                        }
                    }
                }
                pw.flush();
                return;
            }
            pw.println("  Timers are not enabled in this process");
            pw.flush();
        }
    }
}
