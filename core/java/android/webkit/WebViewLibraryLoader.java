package android.webkit;

import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.app.LoadedApk;
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
    private static final String CHROMIUM_WEBVIEW_NATIVE_RELRO_32 =
            "/data/misc/shared_relro/libwebviewchromium32.relro";
    private static final String CHROMIUM_WEBVIEW_NATIVE_RELRO_64 =
            "/data/misc/shared_relro/libwebviewchromium64.relro";
    private static final boolean DEBUG = false;
    private static final String LOGTAG = WebViewLibraryLoader.class.getSimpleName();
    private static boolean sAddressSpaceReserved = false;

    static native boolean nativeCreateRelroFile(String str, String str2, ClassLoader classLoader);

    static native int nativeLoadWithRelroFile(String str, String str2, ClassLoader classLoader);

    static native boolean nativeReserveAddressSpace(long j);

    private static class RelroFileCreator {
        private RelroFileCreator() {}

        public static void main(String[] args) {
            boolean is64Bit = VMRuntime.getRuntime().is64Bit();
            try {
                if (args.length == 2 && args[0] != null && args[1] != null) {
                    String packageName = args[0];
                    String libraryFileName = args[1];
                    Log.v(
                            WebViewLibraryLoader.LOGTAG,
                            "RelroFileCreator (64bit = "
                                    + is64Bit
                                    + "), package: "
                                    + packageName
                                    + " library: "
                                    + libraryFileName);
                    if (!WebViewLibraryLoader.sAddressSpaceReserved) {
                        Log.e(
                                WebViewLibraryLoader.LOGTAG,
                                "can't create relro file; address space not reserved");
                        return;
                    }
                    LoadedApk apk =
                            ActivityThread.currentActivityThread()
                                    .getPackageInfo(packageName, (CompatibilityInfo) null, 3);
                    boolean result =
                            WebViewLibraryLoader.nativeCreateRelroFile(
                                    libraryFileName,
                                    is64Bit
                                            ? WebViewLibraryLoader.CHROMIUM_WEBVIEW_NATIVE_RELRO_64
                                            : WebViewLibraryLoader.CHROMIUM_WEBVIEW_NATIVE_RELRO_32,
                                    apk.getClassLoader());
                    try {
                        if (Flags.updateServiceIpcWrapper()) {
                            WebViewUpdateManager.getInstance().notifyRelroCreationCompleted();
                        } else {
                            WebViewFactory.getUpdateServiceUnchecked()
                                    .notifyRelroCreationCompleted();
                        }
                    } catch (Exception e) {
                        Log.e(WebViewLibraryLoader.LOGTAG, "error notifying update service", e);
                    }
                    if (!result) {
                        Log.e(WebViewLibraryLoader.LOGTAG, "failed to create relro file");
                    }
                    System.exit(0);
                    return;
                }
                Log.e(
                        WebViewLibraryLoader.LOGTAG,
                        "Invalid RelroFileCreator args: " + Arrays.toString(args));
                try {
                    if (Flags.updateServiceIpcWrapper()) {
                        WebViewUpdateManager.getInstance().notifyRelroCreationCompleted();
                    } else {
                        WebViewFactory.getUpdateServiceUnchecked().notifyRelroCreationCompleted();
                    }
                } catch (Exception e2) {
                    Log.e(WebViewLibraryLoader.LOGTAG, "error notifying update service", e2);
                }
                if (0 == 0) {
                    Log.e(WebViewLibraryLoader.LOGTAG, "failed to create relro file");
                }
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
                if (0 == 0) {
                    Log.e(WebViewLibraryLoader.LOGTAG, "failed to create relro file");
                }
                System.exit(0);
            }
        }
    }

    static void createRelroFile(boolean is64Bit, String packageName, String libraryFileName) {
        final String abi =
                is64Bit ? Build.SUPPORTED_64_BIT_ABIS[0] : Build.SUPPORTED_32_BIT_ABIS[0];
        Runnable crashHandler =
                new Runnable() { // from class: android.webkit.WebViewLibraryLoader.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            Log.e(
                                    WebViewLibraryLoader.LOGTAG,
                                    "relro file creator for "
                                            + abi
                                            + " crashed. Proceeding without");
                            if (Flags.updateServiceIpcWrapper()) {
                                WebViewUpdateManager.getInstance().notifyRelroCreationCompleted();
                            } else {
                                WebViewFactory.getUpdateService().notifyRelroCreationCompleted();
                            }
                        } catch (Exception e) {
                            Log.e(
                                    WebViewLibraryLoader.LOGTAG,
                                    "Cannot reach WebViewUpdateService. " + e.getMessage());
                        }
                    }
                };
        try {
            boolean success =
                    ((ActivityManagerInternal)
                                    LocalServices.getService(ActivityManagerInternal.class))
                            .startIsolatedProcess(
                                    RelroFileCreator.class.getName(),
                                    new String[] {packageName, libraryFileName},
                                    "WebViewLoader-" + abi,
                                    abi,
                                    1037,
                                    crashHandler);
            if (!success) {
                throw new Exception("Failed to start the relro file creator process");
            }
        } catch (Throwable t) {
            Slog.wtf(LOGTAG, "error starting relro file creator for abi " + abi, t);
            crashHandler.run();
        }
    }

    static int prepareNativeLibraries(PackageInfo webViewPackageInfo) {
        String libraryFileName =
                WebViewFactory.getWebViewLibrary(webViewPackageInfo.applicationInfo);
        if (libraryFileName == null) {
            return 0;
        }
        return createRelros(webViewPackageInfo.packageName, libraryFileName);
    }

    private static int createRelros(String packageName, String libraryFileName) {
        int numRelros = 0;
        if (Build.SUPPORTED_32_BIT_ABIS.length > 0) {
            createRelroFile(false, packageName, libraryFileName);
            numRelros = 0 + 1;
        }
        if (Build.SUPPORTED_64_BIT_ABIS.length > 0) {
            createRelroFile(true, packageName, libraryFileName);
            return numRelros + 1;
        }
        return numRelros;
    }

    static void reserveAddressSpaceInZygote() {
        long addressSpaceToReserve;
        System.loadLibrary("webviewchromium_loader");
        if (VMRuntime.getRuntime().is64Bit()) {
            addressSpaceToReserve = 1073741824;
        } else if (VMRuntime.getRuntime().vmInstructionSet().equals("arm")) {
            addressSpaceToReserve = 136314880;
        } else {
            addressSpaceToReserve = 199229440;
        }
        sAddressSpaceReserved = nativeReserveAddressSpace(addressSpaceToReserve);
        if (!sAddressSpaceReserved) {
            Log.e(LOGTAG, "reserving " + addressSpaceToReserve + " bytes of address space failed");
        }
    }

    public static int loadNativeLibrary(ClassLoader clazzLoader, String libraryFileName) {
        if (!sAddressSpaceReserved) {
            Log.e(LOGTAG, "can't load with relro file; address space not reserved");
            return 2;
        }
        String relroPath =
                VMRuntime.getRuntime().is64Bit()
                        ? CHROMIUM_WEBVIEW_NATIVE_RELRO_64
                        : CHROMIUM_WEBVIEW_NATIVE_RELRO_32;
        int result = nativeLoadWithRelroFile(libraryFileName, relroPath, clazzLoader);
        if (result != 0) {
            Log.w(LOGTAG, "failed to load with relro file, proceeding without");
        }
        return result;
    }
}
