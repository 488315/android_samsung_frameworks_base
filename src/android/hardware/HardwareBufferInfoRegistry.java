package android.hardware;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.hardware.HardwareBuffer;
import android.os.Debug;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public final class HardwareBufferInfoRegistry {
    private static final int HEAP_DUMP_INTERVAL = SystemProperties.getInt("persist.debug.detect.hardwarebuffer.leaks", 100);
    private static String TAG = "HardwareBufferInfoRegistry";
    private static HardwareBufferInfoRegistry sInfoRegistry;
    private boolean mEnabled = false;
    private boolean[] mHasDumped = new boolean[5];
    private final Map<Long, HardwareBuffer.Info> mInfoList = new HashMap();

    private HardwareBufferInfoRegistry() {
    }

    public static HardwareBufferInfoRegistry getInstance() {
        HardwareBufferInfoRegistry hardwareBufferInfoRegistry;
        synchronized (HardwareBufferInfoRegistry.class) {
            if (sInfoRegistry == null) {
                sInfoRegistry = new HardwareBufferInfoRegistry();
            }
            hardwareBufferInfoRegistry = sInfoRegistry;
        }
        return hardwareBufferInfoRegistry;
    }

    public void enable(Context context) {
        if (context.checkSelfPermission(Manifest.permission.READ_FRAME_BUFFER) != 0) {
            throw new SecurityException("Expected caller to hold READ_FRAME_BUFFER");
        }
        this.mEnabled = true;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void add(HardwareBuffer.Info info) {
        if (info == null) {
            return;
        }
        synchronized (this) {
            this.mInfoList.put(Long.valueOf(info.mNativeObject), info);
        }
    }

    public void remove(long j) {
        synchronized (this) {
            this.mInfoList.remove(Long.valueOf(j));
        }
    }

    public void dump(PrintWriter printWriter) {
        synchronized (this) {
            printWriter.println();
            printWriter.println("HardwareBufferInfoRegistry / " + this);
            printWriter.println("----------------------");
            printWriter.println("Listing of " + this.mInfoList.size());
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            Iterator<Map.Entry<Long, HardwareBuffer.Info>> it = this.mInfoList.entrySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                HardwareBuffer.Info value = it.next().getValue();
                printWriter.print("  ");
                StringBuilder sb = new StringBuilder();
                sb.append("#");
                int i2 = i + 1;
                sb.append(i);
                sb.append(" / ");
                printWriter.print(sb.toString());
                printWriter.print(value.mNativeObject);
                printWriter.print("(id:" + value.mId + ") ");
                printWriter.print("/ " + value.mWidth + "x" + value.mHeight);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(" [");
                sb2.append((jElapsedRealtime - value.mCreatedTime) / 1000);
                sb2.append("s ago]");
                printWriter.println(sb2.toString());
                printWriter.print("     CallStack=");
                printWriter.println(value.mCallStack);
                i = i2;
            }
        }
    }

    public void dumpHeapIfNeeded() {
        final int i;
        int iMax = Math.max(this.mInfoList.size(), SystemProperties.getInt("debug.detect.hardwarebuffer.fake_leaks", 0));
        if (iMax >= 1 && (i = iMax / HEAP_DUMP_INTERVAL) > 0) {
            boolean[] zArr = this.mHasDumped;
            if (i > zArr.length || zArr[i - 1]) {
                return;
            }
            new Thread(TAG + ":heapDump") { // from class: android.hardware.HardwareBufferInfoRegistry.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    String processName = Application.getProcessName();
                    String str = "/data/log/core/" + processName + ".hprof";
                    try {
                        Log.i(HardwareBufferInfoRegistry.TAG, "Dumping heap for process: " + processName);
                        Debug.dumpHprofData(str);
                    } catch (Exception e) {
                        Log.w(HardwareBufferInfoRegistry.TAG, "Failed to dump heap for process: " + processName, e);
                    } finally {
                        HardwareBufferInfoRegistry.this.mHasDumped[i - 1] = true;
                    }
                }
            }.start();
        }
    }
}
