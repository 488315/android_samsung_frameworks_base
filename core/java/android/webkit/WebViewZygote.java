package android.webkit;

import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.ChildZygoteProcess;
import android.os.Process;
import android.os.ZygoteProcess;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.os.Zygote;

/* loaded from: classes4.dex */
public class WebViewZygote {
    private static final String LOGTAG = "WebViewZygote";
    private static final Object sLock = new Object();
    private static boolean sMultiprocessEnabled = false;
    private static PackageInfo sPackage;
    private static ChildZygoteProcess sZygote;

    public static ZygoteProcess getProcess() {
        synchronized (sLock) {
            if (sZygote != null) {
                return sZygote;
            }
            connectToZygoteIfNeededLocked();
            return sZygote;
        }
    }

    public static String getPackageName() {
        String str;
        synchronized (sLock) {
            str = sPackage.packageName;
        }
        return str;
    }

    public static boolean isMultiprocessEnabled() {
        synchronized (sLock) {
            boolean z = true;
            if (Flags.updateServiceV2()) {
                if (sPackage == null) {
                    z = false;
                }
                return z;
            }
            if (!sMultiprocessEnabled || sPackage == null) {
                z = false;
            }
            return z;
        }
    }

    public static void setMultiprocessEnabled(boolean enabled) {
        if (Flags.updateServiceV2()) {
            throw new IllegalStateException(
                    "setMultiprocessEnabled shouldn't be called if update_service_v2 flag is set.");
        }
        synchronized (sLock) {
            sMultiprocessEnabled = enabled;
            if (!enabled) {
                stopZygoteLocked();
            }
        }
    }

    static void onWebViewProviderChanged(PackageInfo packageInfo) {
        synchronized (sLock) {
            sPackage = packageInfo;
            if (Flags.updateServiceV2() || sMultiprocessEnabled) {
                stopZygoteLocked();
            }
        }
    }

    private static void stopZygoteLocked() {
        if (sZygote != null) {
            sZygote.close();
            Process.killProcess(sZygote.getPid());
            sZygote = null;
        }
    }

    private static void connectToZygoteIfNeededLocked() {
        if (sZygote != null) {
            return;
        }
        if (sPackage == null) {
            Log.e(LOGTAG, "Cannot connect to zygote, no package specified");
            return;
        }
        try {
            String abi = sPackage.applicationInfo.primaryCpuAbi;
            int runtimeFlags =
                    Zygote.getMemorySafetyRuntimeFlagsForSecondaryZygote(
                            sPackage.applicationInfo, null);
            sZygote =
                    Process.ZYGOTE_PROCESS.startChildZygote(
                            "com.android.internal.os.WebViewZygoteInit",
                            "webview_zygote",
                            1053,
                            1053,
                            null,
                            runtimeFlags,
                            "webview_zygote",
                            abi,
                            TextUtils.join(",", Build.SUPPORTED_ABIS),
                            null,
                            Process.FIRST_ISOLATED_UID,
                            Integer.MAX_VALUE);
            ZygoteProcess.waitForConnectionToZygote(sZygote.getPrimarySocketAddress());
            sZygote.preloadApp(sPackage.applicationInfo, abi);
        } catch (Exception e) {
            Log.e(LOGTAG, "Error connecting to webview zygote", e);
            stopZygoteLocked();
        }
    }
}
