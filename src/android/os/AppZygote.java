package android.os;

import android.content.pm.ApplicationInfo;
import android.content.pm.ProcessInfo;
import android.os.Process;
import android.util.Log;
import android.util.Pair;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.os.Zygote;
import dalvik.system.VMRuntime;
import java.util.Map;

/* loaded from: classes3.dex */
public class AppZygote {
    private static final String LOG_TAG = "AppZygote";
    private final ApplicationInfo mAppInfo;
    private final Object mLock = new Object();
    private final ProcessInfo mProcessInfo;
    private ChildZygoteProcess mZygote;
    private final int mZygoteUid;
    private final int mZygoteUidGidMax;
    private final int mZygoteUidGidMin;

    public AppZygote(ApplicationInfo applicationInfo, ProcessInfo processInfo, int i, int i2, int i3) {
        this.mAppInfo = applicationInfo;
        this.mProcessInfo = processInfo;
        this.mZygoteUid = i;
        this.mZygoteUidGidMin = i2;
        this.mZygoteUidGidMax = i3;
    }

    public ChildZygoteProcess getProcess() {
        synchronized (this.mLock) {
            ChildZygoteProcess childZygoteProcess = this.mZygote;
            if (childZygoteProcess != null) {
                return childZygoteProcess;
            }
            connectToZygoteIfNeededLocked();
            return this.mZygote;
        }
    }

    public void stopZygote() {
        synchronized (this.mLock) {
            stopZygoteLocked();
        }
    }

    public ApplicationInfo getAppInfo() {
        return this.mAppInfo;
    }

    public final Process.ProcessStartResult startProcess(String str, String str2, int i, int[] iArr, int i2, int i3, int i4, String str3, String str4, String str5, String str6, String str7, boolean z, long[] jArr, Map<String, Pair<String, Long>> map, Map<String, Pair<String, Long>> map2, String[] strArr) {
        try {
            return getProcess().start(str, str2, i, i, iArr, i2, i3, i4, str3, str4, str5, str6, null, str7, 0, z, jArr, map, map2, false, false, false, strArr);
        } catch (RuntimeException e) {
            if (!Flags.appZygoteRetryStart() || !getProcess().isDead()) {
                throw e;
            }
            Log.w(LOG_TAG, "retry starting process " + str2);
            stopZygote();
            return getProcess().start(str, str2, i, i, iArr, i2, i3, i4, str3, str4, str5, str6, null, str7, 0, z, jArr, map, map2, false, false, false, strArr);
        }
    }

    private void stopZygoteLocked() {
        ChildZygoteProcess childZygoteProcess = this.mZygote;
        if (childZygoteProcess != null) {
            childZygoteProcess.close();
            if (!this.mZygote.isDead()) {
                Process.killProcessGroup(this.mZygoteUid, this.mZygote.getPid());
            }
            this.mZygote = null;
        }
    }

    private void connectToZygoteIfNeededLocked() {
        String str = this.mAppInfo.primaryCpuAbi != null ? this.mAppInfo.primaryCpuAbi : Build.SUPPORTED_ABIS[0];
        try {
            int memorySafetyRuntimeFlagsForSecondaryZygote = Zygote.getMemorySafetyRuntimeFlagsForSecondaryZygote(this.mAppInfo, this.mProcessInfo);
            int[] iArr = {UserHandle.getSharedAppGid(UserHandle.getAppId(this.mAppInfo.uid))};
            ZygoteProcess zygoteProcess = Process.ZYGOTE_PROCESS;
            String str2 = this.mAppInfo.processName + "_zygote";
            int i = this.mZygoteUid;
            ChildZygoteProcess childZygoteProcessStartChildZygote = zygoteProcess.startChildZygote("com.android.internal.os.AppZygoteInit", str2, i, i, iArr, memorySafetyRuntimeFlagsForSecondaryZygote, "app_zygote", str, str, VMRuntime.getInstructionSet(str), this.mZygoteUidGidMin, this.mZygoteUidGidMax);
            this.mZygote = childZygoteProcessStartChildZygote;
            ZygoteProcess.waitForConnectionToZygote(childZygoteProcessStartChildZygote.getPrimarySocketAddress());
            Log.i(LOG_TAG, "Starting application preload.");
            this.mZygote.preloadApp(this.mAppInfo, str);
            Log.i(LOG_TAG, "Application preload done.");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error connecting to app zygote", e);
            stopZygoteLocked();
        }
    }
}
