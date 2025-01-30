package android.webkit;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.Application;
import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageInfo;
import android.content.p002pm.PackageManager;
import android.content.p002pm.Signature;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Process;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.SystemClock;
import android.p009os.SystemProperties;
import android.p009os.Trace;
import android.util.AndroidRuntimeException;
import android.util.ArraySet;
import android.util.Log;
import android.webkit.IWebViewUpdateService;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.ipm.SecIpmManager;
import java.io.File;
import java.lang.reflect.Method;

@SystemApi
/* loaded from: classes4.dex */
public final class WebViewFactory {
    private static final String CHROMIUM_WEBVIEW_FACTORY = "com.android.webview.chromium.WebViewChromiumFactoryProviderForT";
    private static final String CHROMIUM_WEBVIEW_FACTORY_METHOD = "create";
    private static final boolean DEBUG = false;
    public static final int LIBLOAD_ADDRESS_SPACE_NOT_RESERVED = 2;
    public static final int LIBLOAD_FAILED_JNI_CALL = 7;
    public static final int LIBLOAD_FAILED_LISTING_WEBVIEW_PACKAGES = 4;
    public static final int LIBLOAD_FAILED_TO_FIND_NAMESPACE = 10;
    public static final int LIBLOAD_FAILED_TO_LOAD_LIBRARY = 6;
    public static final int LIBLOAD_FAILED_TO_OPEN_RELRO_FILE = 5;
    public static final int LIBLOAD_FAILED_WAITING_FOR_RELRO = 3;
    public static final int LIBLOAD_FAILED_WAITING_FOR_WEBVIEW_REASON_UNKNOWN = 8;
    public static final int LIBLOAD_SUCCESS = 0;
    public static final int LIBLOAD_WRONG_PACKAGE_NAME = 1;
    private static final String LOGTAG = "WebViewFactory";
    private static final String WEBVIEW_PAC_PROPERTY = "knox.vpn.pac.webview";
    private static final String WEBVIEW_PAC_VALUE = "enable";
    private static String sDataDirectorySuffix;
    private static PackageInfo sPackageInfo;
    private static WebViewFactoryProvider sProviderInstance;
    private static boolean sWebViewDisabled;
    private static Boolean sWebViewSupported;
    private static final Object sProviderLock = new Object();
    static final StartupTimestamps sTimestamps = new StartupTimestamps();
    static boolean isSetDataDirectorySuffix = false;
    private static String WEBVIEW_UPDATE_SERVICE_NAME = "webviewupdate";

    public static class StartupTimestamps {
        long mAddAssetsEnd;
        long mAddAssetsStart;
        long mCreateContextEnd;
        long mCreateContextStart;
        long mGetClassLoaderEnd;
        long mGetClassLoaderStart;
        long mNativeLoadEnd;
        long mNativeLoadStart;
        long mProviderClassForNameEnd;
        long mProviderClassForNameStart;
        long mWebViewLoadStart;

        StartupTimestamps() {
        }

        public long getWebViewLoadStart() {
            return this.mWebViewLoadStart;
        }

        public long getCreateContextStart() {
            return this.mCreateContextStart;
        }

        public long getCreateContextEnd() {
            return this.mCreateContextEnd;
        }

        public long getAddAssetsStart() {
            return this.mAddAssetsStart;
        }

        public long getAddAssetsEnd() {
            return this.mAddAssetsEnd;
        }

        public long getGetClassLoaderStart() {
            return this.mGetClassLoaderStart;
        }

        public long getGetClassLoaderEnd() {
            return this.mGetClassLoaderEnd;
        }

        public long getNativeLoadStart() {
            return this.mNativeLoadStart;
        }

        public long getNativeLoadEnd() {
            return this.mNativeLoadEnd;
        }

        public long getProviderClassForNameStart() {
            return this.mProviderClassForNameStart;
        }

        public long getProviderClassForNameEnd() {
            return this.mProviderClassForNameEnd;
        }
    }

    static StartupTimestamps getStartupTimestamps() {
        return sTimestamps;
    }

    private static String getWebViewPreparationErrorReason(int error) {
        switch (error) {
            case 3:
                return "Time out waiting for Relro files being created";
            case 4:
                return "No WebView installed";
            case 8:
                return "Crashed for unknown reason";
            default:
                return "Unknown";
        }
    }

    static class MissingWebViewPackageException extends Exception {
        public MissingWebViewPackageException(String message) {
            super(message);
        }

        public MissingWebViewPackageException(Exception e) {
            super(e);
        }
    }

    private static boolean isWebViewSupported() {
        if (sWebViewSupported == null) {
            sWebViewSupported = Boolean.valueOf(AppGlobals.getInitialApplication().getPackageManager().hasSystemFeature(PackageManager.FEATURE_WEBVIEW));
        }
        return sWebViewSupported.booleanValue();
    }

    static void disableWebView() {
        synchronized (sProviderLock) {
            if (sProviderInstance != null) {
                throw new IllegalStateException("Can't disable WebView: WebView already initialized");
            }
            sWebViewDisabled = true;
        }
    }

    static void setDataDirectorySuffix(String suffix) {
        synchronized (sProviderLock) {
            if (sProviderInstance != null) {
                throw new IllegalStateException("Can't set data directory suffix: WebView already initialized");
            }
            if (suffix.indexOf(File.separatorChar) >= 0) {
                throw new IllegalArgumentException("Suffix " + suffix + " contains a path separator");
            }
            sDataDirectorySuffix = suffix;
            isSetDataDirectorySuffix = true;
        }
    }

    static String getDataDirectorySuffix() {
        String str;
        synchronized (sProviderLock) {
            str = sDataDirectorySuffix;
        }
        return str;
    }

    public static String getWebViewLibrary(ApplicationInfo ai) {
        if (ai.metaData != null) {
            return ai.metaData.getString("com.android.webview.WebViewLibrary");
        }
        return null;
    }

    public static PackageInfo getLoadedPackageInfo() {
        PackageInfo packageInfo;
        synchronized (sProviderLock) {
            packageInfo = sPackageInfo;
        }
        return packageInfo;
    }

    public static Class<WebViewFactoryProvider> getWebViewProviderClass(ClassLoader clazzLoader) throws ClassNotFoundException {
        return Class.forName(CHROMIUM_WEBVIEW_FACTORY, true, clazzLoader);
    }

    public static int loadWebViewNativeLibraryFromPackage(String packageName, ClassLoader clazzLoader) {
        if (!isWebViewSupported()) {
            return 1;
        }
        try {
            WebViewProviderResponse response = getUpdateService().waitForAndGetProvider();
            if (response.status != 0 && response.status != 3) {
                return response.status;
            }
            if (!response.packageInfo.packageName.equals(packageName)) {
                return 1;
            }
            PackageManager packageManager = AppGlobals.getInitialApplication().getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 268435584);
                String libraryFileName = getWebViewLibrary(packageInfo.applicationInfo);
                int loadNativeRet = WebViewLibraryLoader.loadNativeLibrary(clazzLoader, libraryFileName);
                return loadNativeRet == 0 ? response.status : loadNativeRet;
            } catch (PackageManager.NameNotFoundException e) {
                Log.m96e(LOGTAG, "Couldn't find package " + packageName);
                return 1;
            }
        } catch (RemoteException e2) {
            Log.m97e(LOGTAG, "error waiting for relro creation", e2);
            return 8;
        }
    }

    static WebViewFactoryProvider getProvider() {
        synchronized (sProviderLock) {
            WebViewFactoryProvider webViewFactoryProvider = sProviderInstance;
            if (webViewFactoryProvider != null) {
                return webViewFactoryProvider;
            }
            sTimestamps.mWebViewLoadStart = SystemClock.uptimeMillis();
            int uid = Process.myUid();
            String isKnoxVpnProxyEnabled = System.getProperty(WEBVIEW_PAC_PROPERTY);
            if (uid == 1002 && isKnoxVpnProxyEnabled != null && isKnoxVpnProxyEnabled.equalsIgnoreCase("enable")) {
                Log.m94d(LOGTAG, "enable webview for knox vpn proxy module");
            } else if (uid == 0 || uid == 1000 || uid == 1001 || uid == 1027 || uid == 1002) {
                throw new UnsupportedOperationException("For security reasons, WebView is not allowed in privileged processes");
            }
            if (!isWebViewSupported()) {
                throw new UnsupportedOperationException();
            }
            if (sWebViewDisabled) {
                throw new IllegalStateException("WebView.disableWebView() was called: WebView is disabled");
            }
            Trace.traceBegin(16L, "WebViewFactory.getProvider()");
            try {
                try {
                    Class<WebViewFactoryProvider> providerClass = getProviderClass();
                    Method staticFactory = providerClass.getMethod(CHROMIUM_WEBVIEW_FACTORY_METHOD, WebViewDelegate.class);
                    Trace.traceBegin(16L, "WebViewFactoryProvider invocation");
                    try {
                        sProviderInstance = (WebViewFactoryProvider) staticFactory.invoke(null, new WebViewDelegate());
                        if (!isSetDataDirectorySuffix) {
                            isSetDataDirectorySuffix = false;
                            String needRecord = SystemProperties.get("persist.sys.app_webview_preload_need", "false");
                            if (needRecord.startsWith("launching")) {
                                String[] strs = needRecord.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                                String processName = strs[1];
                                SecIpmManager mSecIpmManager = (SecIpmManager) AppGlobals.getInitialApplication().getSystemService("PkgPredictorService");
                                if (mSecIpmManager != null) {
                                    mSecIpmManager.setWebViewPreload("load-" + processName, -100);
                                }
                            }
                        }
                        WebViewFactoryProvider webViewFactoryProvider2 = sProviderInstance;
                        Trace.traceEnd(16L);
                        return webViewFactoryProvider2;
                    } finally {
                    }
                } finally {
                }
            } catch (Exception e) {
                Log.m97e(LOGTAG, "error instantiating provider", e);
                throw new AndroidRuntimeException(e);
            }
        }
    }

    private static boolean signaturesEquals(Signature[] s1, Signature[] s2) {
        if (s1 == null) {
            return s2 == null;
        }
        if (s2 == null) {
            return false;
        }
        ArraySet<Signature> set1 = new ArraySet<>();
        for (Signature signature : s1) {
            set1.add(signature);
        }
        ArraySet<Signature> set2 = new ArraySet<>();
        for (Signature signature2 : s2) {
            set2.add(signature2);
        }
        return set1.equals(set2);
    }

    private static void verifyPackageInfo(PackageInfo chosen, PackageInfo toUse) throws MissingWebViewPackageException {
        if (!chosen.packageName.equals(toUse.packageName)) {
            throw new MissingWebViewPackageException("Failed to verify WebView provider, packageName mismatch, expected: " + chosen.packageName + " actual: " + toUse.packageName);
        }
        if (chosen.getLongVersionCode() > toUse.getLongVersionCode()) {
            throw new MissingWebViewPackageException("Failed to verify WebView provider, version code is lower than expected: " + chosen.getLongVersionCode() + " actual: " + toUse.getLongVersionCode());
        }
        if (getWebViewLibrary(toUse.applicationInfo) == null) {
            throw new MissingWebViewPackageException("Tried to load an invalid WebView provider: " + toUse.packageName);
        }
        if (!signaturesEquals(chosen.signatures, toUse.signatures)) {
            throw new MissingWebViewPackageException("Failed to verify WebView provider, signature mismatch");
        }
    }

    private static Context getWebViewContextAndSetProvider() throws MissingWebViewPackageException {
        Application initialApplication = AppGlobals.getInitialApplication();
        try {
            Trace.traceBegin(16L, "WebViewUpdateService.waitForAndGetProvider()");
            try {
                WebViewProviderResponse response = getUpdateService().waitForAndGetProvider();
                Trace.traceEnd(16L);
                if (response.status != 0 && response.status != 3) {
                    throw new MissingWebViewPackageException("Failed to load WebView provider: " + getWebViewPreparationErrorReason(response.status));
                }
                Trace.traceBegin(16L, "ActivityManager.addPackageDependency()");
                try {
                    ActivityManager.getService().addPackageDependency(response.packageInfo.packageName);
                    Trace.traceEnd(16L);
                    PackageManager pm = initialApplication.getPackageManager();
                    Trace.traceBegin(16L, "PackageManager.getPackageInfo()");
                    try {
                        PackageInfo newPackageInfo = pm.getPackageInfo(response.packageInfo.packageName, 268444864);
                        Trace.traceEnd(16L);
                        verifyPackageInfo(response.packageInfo, newPackageInfo);
                        ApplicationInfo ai = newPackageInfo.applicationInfo;
                        Trace.traceBegin(16L, "initialApplication.createApplicationContext");
                        StartupTimestamps startupTimestamps = sTimestamps;
                        startupTimestamps.mCreateContextStart = SystemClock.uptimeMillis();
                        try {
                            Context webViewContext = initialApplication.createApplicationContext(ai, 3);
                            sPackageInfo = newPackageInfo;
                            startupTimestamps.mCreateContextEnd = SystemClock.uptimeMillis();
                            return webViewContext;
                        } catch (Throwable th) {
                            sTimestamps.mCreateContextEnd = SystemClock.uptimeMillis();
                            throw th;
                        }
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (PackageManager.NameNotFoundException | RemoteException e) {
            throw new MissingWebViewPackageException("Failed to load WebView provider: " + e);
        }
    }

    private static Class<WebViewFactoryProvider> getProviderClass() {
        Application initialApplication = AppGlobals.getInitialApplication();
        try {
            Trace.traceBegin(16L, "WebViewFactory.getWebViewContextAndSetProvider()");
            try {
                Context webViewContext = getWebViewContextAndSetProvider();
                Trace.traceEnd(16L);
                Log.m98i(LOGTAG, "Loading " + sPackageInfo.packageName + " version " + sPackageInfo.versionName + " (code " + sPackageInfo.getLongVersionCode() + NavigationBarInflaterView.KEY_CODE_END);
                Trace.traceBegin(16L, "WebViewFactory.getChromiumProviderClass()");
                try {
                    try {
                        sTimestamps.mAddAssetsStart = SystemClock.uptimeMillis();
                        for (String newAssetPath : webViewContext.getApplicationInfo().getAllApkPaths()) {
                            initialApplication.getAssets().addAssetPathAsSharedLibrary(newAssetPath);
                            if (initialApplication.getAssets() != initialApplication.getResources().getAssets()) {
                                initialApplication.getResources().getAssets().addAssetPathAsSharedLibrary(newAssetPath);
                            }
                        }
                        StartupTimestamps startupTimestamps = sTimestamps;
                        long uptimeMillis = SystemClock.uptimeMillis();
                        startupTimestamps.mGetClassLoaderStart = uptimeMillis;
                        startupTimestamps.mAddAssetsEnd = uptimeMillis;
                        ClassLoader clazzLoader = webViewContext.getClassLoader();
                        Trace.traceBegin(16L, "WebViewFactory.loadNativeLibrary()");
                        long uptimeMillis2 = SystemClock.uptimeMillis();
                        startupTimestamps.mNativeLoadStart = uptimeMillis2;
                        startupTimestamps.mGetClassLoaderEnd = uptimeMillis2;
                        WebViewLibraryLoader.loadNativeLibrary(clazzLoader, getWebViewLibrary(sPackageInfo.applicationInfo));
                        Trace.traceEnd(16L);
                        Trace.traceBegin(16L, "Class.forName()");
                        long uptimeMillis3 = SystemClock.uptimeMillis();
                        startupTimestamps.mProviderClassForNameStart = uptimeMillis3;
                        startupTimestamps.mNativeLoadEnd = uptimeMillis3;
                        try {
                            Class<WebViewFactoryProvider> webViewProviderClass = getWebViewProviderClass(clazzLoader);
                            startupTimestamps.mProviderClassForNameEnd = SystemClock.uptimeMillis();
                            Trace.traceEnd(16L);
                            return webViewProviderClass;
                        } catch (Throwable th) {
                            sTimestamps.mProviderClassForNameEnd = SystemClock.uptimeMillis();
                            throw th;
                        }
                    } finally {
                    }
                } catch (ClassNotFoundException e) {
                    Log.m97e(LOGTAG, "error loading provider", e);
                    throw new AndroidRuntimeException(e);
                }
            } finally {
            }
        } catch (MissingWebViewPackageException e2) {
            Log.m97e(LOGTAG, "Chromium WebView package does not exist", e2);
            throw new AndroidRuntimeException(e2);
        }
    }

    public static void prepareWebViewInZygote() {
        try {
            WebViewLibraryLoader.reserveAddressSpaceInZygote();
        } catch (Throwable t) {
            Log.m97e(LOGTAG, "error preparing native loader", t);
        }
    }

    public static int onWebViewProviderChanged(PackageInfo packageInfo) {
        int startedRelroProcesses = 0;
        try {
            startedRelroProcesses = WebViewLibraryLoader.prepareNativeLibraries(packageInfo);
        } catch (Throwable t) {
            Log.m97e(LOGTAG, "error preparing webview native library", t);
        }
        WebViewZygote.onWebViewProviderChanged(packageInfo);
        return startedRelroProcesses;
    }

    public static IWebViewUpdateService getUpdateService() {
        if (isWebViewSupported()) {
            return getUpdateServiceUnchecked();
        }
        return null;
    }

    static IWebViewUpdateService getUpdateServiceUnchecked() {
        return IWebViewUpdateService.Stub.asInterface(ServiceManager.getService(WEBVIEW_UPDATE_SERVICE_NAME));
    }
}
