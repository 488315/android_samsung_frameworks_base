package android.content.res;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FrameworkStatsLog;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class ResourceTimer {
    private static boolean ENABLE_DEBUG = false;
    private static final String TAG = "ResourceTimer";
    private static Handler mHandler = null;
    private static int[] sApiMap = null;
    private static Config sConfig = null;
    private static int sCurrentPoint = 0;
    private static boolean sEnabled = true;
    private static boolean sIncrementalMetrics = true;
    private static ResourceTimer sManager;
    private static Timer[] sTimers;
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

        private Config() {
        }
    }

    private static class Timer {
        int count;
        int[] largest;
        int maxtime;
        int mintime;
        int[] percentile;
        long total;

        private Timer() {
        }

        public String toString() {
            return TextUtils.formatSimple("%d:%d:%d:%d", Integer.valueOf(this.count), Long.valueOf(this.total), Integer.valueOf(this.mintime), Integer.valueOf(this.maxtime));
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
                mHandler = new Handler(Looper.getMainLooper()) { // from class: android.content.res.ResourceTimer.1
                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        ResourceTimer.handleMessage(message);
                    }
                };
                Config config = new Config();
                sConfig = config;
                nativeEnableTimers(config);
                sTimers = new Timer[sConfig.maxTimer];
                int i = 0;
                while (true) {
                    Timer[] timerArr = sTimers;
                    if (i >= timerArr.length) {
                        break;
                    }
                    timerArr[i] = new Timer();
                    sTimers[i].percentile = new int[sConfig.maxBuckets];
                    sTimers[i].largest = new int[sConfig.maxLargest];
                    i++;
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

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleMessage(Message message) {
        synchronized (sLock) {
            publish();
            startTimer();
        }
    }

    private static void startTimer() {
        long length;
        int i = sCurrentPoint;
        long[] jArr = sPublicationPoints;
        if (i < jArr.length) {
            length = jArr[i];
        } else {
            length = (i - (jArr.length - 1)) * jArr[jArr.length - 1];
        }
        long j = length * 60000;
        if (ENABLE_DEBUG) {
            j /= 60;
        }
        mHandler.sendEmptyMessageAtTime(0, sProcessStart + j);
    }

    private static void update(boolean z) {
        nativeGetTimers(sTimers, z);
        sLastUpdated = SystemClock.uptimeMillis();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void publish() {
        char c;
        char c2 = 1;
        update(true);
        int i = 0;
        while (true) {
            Timer[] timerArr = sTimers;
            if (i < timerArr.length) {
                Timer timer = timerArr[i];
                if (timer.count > 0) {
                    Log.i(TAG, TextUtils.formatSimple("%s count=%d pvalues=%s", sConfig.timers[i], Integer.valueOf(timer.count), packedString(timer.percentile)));
                    int i2 = sApiMap[i];
                    if (i2 != 0) {
                        c = c2;
                        FrameworkStatsLog.write(517, i2, timer.count, timer.total, timer.percentile[0], timer.percentile[c2], timer.percentile[2], timer.percentile[3], timer.largest[0], timer.largest[c2], timer.largest[2], timer.largest[3], timer.largest[4]);
                    } else {
                        c = c2;
                    }
                }
                i++;
                c2 = c;
            } else {
                sCurrentPoint++;
                return;
            }
        }
    }

    private static String packedString(int[] iArr) {
        return Arrays.toString(iArr).replaceAll("[\\]\\[ ]", "");
    }

    public static void dumpTimers(FileDescriptor fileDescriptor, String... strArr) {
        int i;
        boolean z;
        FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(fileDescriptor));
        try {
            fastPrintWriter.println("\nDumping ResourceTimers");
            Object obj = sLock;
            synchronized (obj) {
                i = 0;
                z = sEnabled && sConfig != null;
            }
            if (!z) {
                fastPrintWriter.println("  Timers are not enabled in this process");
                fastPrintWriter.close();
                return;
            }
            boolean zContains = Arrays.asList(strArr).contains("-refresh");
            synchronized (obj) {
                update(zContains);
                fastPrintWriter.format("  config runtime=%d proc=%s\n", Long.valueOf(sLastUpdated - sProcessStart), Process.myProcessName());
                while (true) {
                    Timer[] timerArr = sTimers;
                    if (i < timerArr.length) {
                        Timer timer = timerArr[i];
                        if (timer.count != 0) {
                            fastPrintWriter.format("  stats timer=%s cnt=%d avg=%d min=%d max=%d pval=%s largest=%s\n", sConfig.timers[i], Integer.valueOf(timer.count), Long.valueOf(timer.total / timer.count), Integer.valueOf(timer.mintime), Integer.valueOf(timer.maxtime), packedString(timer.percentile), packedString(timer.largest));
                        }
                        i++;
                    }
                }
            }
            fastPrintWriter.close();
        } finally {
        }
    }
}
