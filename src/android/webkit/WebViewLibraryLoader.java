package android.webkit;

import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.content.pm.PackageInfo;
import android.content.res.CompatibilityInfo;
import android.os.Build;
import android.util.Log;
import android.util.Slog;
import com.android.server.LocalServices;
import dalvik.system.VMRuntime;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class WebViewLibraryLoader {
    private static final String CHROMIUM_WEBVIEW_NATIVE_RELRO_32 = "/data/misc/shared_relro/libwebviewchromium32.relro";
    private static final String CHROMIUM_WEBVIEW_NATIVE_RELRO_64 = "/data/misc/shared_relro/libwebviewchromium64.relro";
    private static final boolean DEBUG = false;
    private static final String LOGTAG = "WebViewLibraryLoader";
    private static boolean sAddressSpaceReserved = false;

    static native boolean nativeCreateRelroFile(String str, String str2, ClassLoader classLoader);

    static native int nativeLoadWithRelroFile(String str, String str2, ClassLoader classLoader);

    static native boolean nativeReserveAddressSpace(long j);

    private static class RelroFileCreator {
        private RelroFileCreator() {
        }

        public static void main(String[] strArr) {
            String str;
            String str2;
            boolean zIs64Bit = VMRuntime.getRuntime().is64Bit();
            try {
                if (strArr.length == 2 && (str = strArr[0]) != null && (str2 = strArr[1]) != null) {
                    Log.v(WebViewLibraryLoader.LOGTAG, "RelroFileCreator (64bit = " + zIs64Bit + "), package: " + str + " library: " + str2);
                    if (!WebViewLibraryLoader.sAddressSpaceReserved) {
                        Log.e(WebViewLibraryLoader.LOGTAG, "can't create relro file; address space not reserved");
                        return;
                    }
                    boolean zNativeCreateRelroFile = WebViewLibraryLoader.nativeCreateRelroFile(str2, zIs64Bit ? WebViewLibraryLoader.CHROMIUM_WEBVIEW_NATIVE_RELRO_64 : WebViewLibraryLoader.CHROMIUM_WEBVIEW_NATIVE_RELRO_32, ActivityThread.currentActivityThread().getPackageInfo(str, (CompatibilityInfo) null, 3).getClassLoader());
                    try {
                        if (Flags.updateServiceIpcWrapper()) {
                            WebViewUpdateManager.getInstance().notifyRelroCreationCompleted();
                        } else {
                            WebViewFactory.getUpdateServiceUnchecked().notifyRelroCreationCompleted();
                        }
                    } catch (Exception e) {
                        Log.e(WebViewLibraryLoader.LOGTAG, "error notifying update service", e);
                    }
                    if (!zNativeCreateRelroFile) {
                        Log.e(WebViewLibraryLoader.LOGTAG, "failed to create relro file");
                    }
                    System.exit(0);
                    return;
                }
                Log.e(WebViewLibraryLoader.LOGTAG, "Invalid RelroFileCreator args: " + Arrays.toString(strArr));
                try {
                    if (Flags.updateServiceIpcWrapper()) {
                        WebViewUpdateManager.getInstance().notifyRelroCreationCompleted();
                    } else {
                        WebViewFactory.getUpdateServiceUnchecked().notifyRelroCreationCompleted();
                    }
                } catch (Exception e2) {
                    Log.e(WebViewLibraryLoader.LOGTAG, "error notifying update service", e2);
                }
                Log.e(WebViewLibraryLoader.LOGTAG, "failed to create relro file");
                System.exit(0);
            } finally {
                try {
                    if (Flags.updateServiceIpcWrapper()) {
                        WebViewUpdateManager.getInstance().notifyRelroCreationCompleted();
                    } else {
                        WebViewFactory.getUpdateServiceUnchecked().notifyRelroCreationCompleted();
                    }
                } catch (Exception e3) {
                    Log.e(WebViewLibraryLoader.LOGTAG, "error notifying update service", e3);
                }
                Log.e(WebViewLibraryLoader.LOGTAG, "failed to create relro file");
                System.exit(0);
            }
        }
    }

    static void createRelroFile(boolean z, String str, String str2) {
        final String str3 = z ? Build.SUPPORTED_64_BIT_ABIS[0] : Build.SUPPORTED_32_BIT_ABIS[0];
        Runnable runnable = new Runnable() { // from class: android.webkit.WebViewLibraryLoader.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.e(WebViewLibraryLoader.LOGTAG, "relro file creator for " + str3 + " crashed. Proceeding without");
                    if (Flags.updateServiceIpcWrapper()) {
                        WebViewUpdateManager.getInstance().notifyRelroCreationCompleted();
                    } else {
                        WebViewFactory.getUpdateService().notifyRelroCreationCompleted();
                    }
                } catch (Exception e) {
                    Log.e(WebViewLibraryLoader.LOGTAG, "Cannot reach WebViewUpdateService. " + e.getMessage());
                }
            }
        };
        try {
            if (((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).startIsolatedProcess(RelroFileCreator.class.getName(), new String[]{str, str2}, "WebViewLoader-" + str3, str3, 1037, runnable)) {
            } else {
                throw new Exception("Failed to start the relro file creator process");
            }
        } catch (Throwable th) {
            Slog.wtf(LOGTAG, "error starting relro file creator for abi " + str3, th);
            runnable.run();
        }
    }

    static int prepareNativeLibraries(PackageInfo packageInfo) {
        String webViewLibrary = WebViewFactory.getWebViewLibrary(packageInfo.applicationInfo);
        if (webViewLibrary == null) {
            return 0;
        }
        return createRelros(packageInfo.packageName, webViewLibrary);
    }

    private static int createRelros(String str, String str2) {
        int i = 0;
        if (Build.SUPPORTED_32_BIT_ABIS.length > 0) {
            createRelroFile(false, str, str2);
            i = 1;
        }
        if (Build.SUPPORTED_64_BIT_ABIS.length <= 0) {
            return i;
        }
        createRelroFile(true, str, str2);
        return i + 1;
    }

    static void reserveAddressSpaceInZygote() {
        long j;
        System.loadLibrary("webviewchromium_loader");
        if (VMRuntime.getRuntime().is64Bit()) {
            j = 1073741824;
        } else {
            j = VMRuntime.getRuntime().vmInstructionSet().equals("arm") ? 136314880L : 199229440L;
        }
        boolean zNativeReserveAddressSpace = nativeReserveAddressSpace(j);
        sAddressSpaceReserved = zNativeReserveAddressSpace;
        if (zNativeReserveAddressSpace) {
            return;
        }
        Log.e(LOGTAG, "reserving " + j + " bytes of address space failed");
    }

    public static int loadNativeLibrary(ClassLoader classLoader, String str) {
        if (!sAddressSpaceReserved) {
            Log.e(LOGTAG, "can't load with relro file; address space not reserved");
            return 2;
        }
        int iNativeLoadWithRelroFile = nativeLoadWithRelroFile(str, VMRuntime.getRuntime().is64Bit() ? CHROMIUM_WEBVIEW_NATIVE_RELRO_64 : CHROMIUM_WEBVIEW_NATIVE_RELRO_32, classLoader);
        if (iNativeLoadWithRelroFile != 0) {
            Log.w(LOGTAG, "failed to load with relro file, proceeding without");
        }
        return iNativeLoadWithRelroFile;
    }
}
