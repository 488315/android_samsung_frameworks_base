package android.webkit;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.webkit.IWebViewUpdateService;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.ipm.SecIpmManager;
import com.samsung.android.lock.LsConstants;
import java.io.File;
import java.lang.reflect.Method;

@SystemApi
/* loaded from: classes4.dex */
public final class WebViewFactory {
    private static final String CHROMIUM_WEBVIEW_FACTORY_METHOD = "create";
    private static final boolean DEBUG = false;
    public static final int LIBLOAD_ADDRESS_SPACE_NOT_RESERVED = 2;
    public static final int LIBLOAD_FAILED_JNI_CALL = 7;
    public static final int LIBLOAD_FAILED_LISTING_WEBVIEW_PACKAGES = 4;
    static final int LIBLOAD_FAILED_OTHER = 11;
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
    private static String WEBVIEW_UPDATE_SERVICE_NAME = Context.WEBVIEW_UPDATE_SERVICE;

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

    private static String getWebViewPreparationErrorReason(int i) {
        if (i == 3) {
            return "Time out waiting for Relro files being created";
        }
        if (i == 4) {
            return "No WebView installed";
        }
        if (i == 8) {
            return "Crashed for unknown reason";
        }
        return LsConstants.TAG_UNKNOWN;
    }

    static class MissingWebViewPackageException extends Exception {
        public MissingWebViewPackageException(String str) {
            super(str);
        }

        public MissingWebViewPackageException(Exception exc) {
            super(exc);
        }
    }

    static boolean isWebViewSupported() {
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

    static void setDataDirectorySuffix(String str) {
        synchronized (sProviderLock) {
            if (sProviderInstance != null) {
                throw new IllegalStateException("Can't set data directory suffix: WebView already initialized");
            }
            if (str.indexOf(File.separatorChar) >= 0) {
                throw new IllegalArgumentException("Suffix " + str + " contains a path separator");
            }
            sDataDirectorySuffix = str;
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

    public static String getWebViewLibrary(ApplicationInfo applicationInfo) {
        if (applicationInfo.metaData != null) {
            return applicationInfo.metaData.getString("com.android.webview.WebViewLibrary");
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

    public static Class<WebViewFactoryProvider> getWebViewProviderClass(ClassLoader classLoader) throws ClassNotFoundException {
        return Class.forName(WebViewFactoryProvider.getWebViewFactoryClassName(), true, classLoader);
    }

    public static int loadWebViewNativeLibraryFromPackage(String str, ClassLoader classLoader) {
        WebViewProviderResponse webViewProviderResponseWaitForAndGetProvider;
        if (!isWebViewSupported()) {
            return 1;
        }
        Application initialApplication = AppGlobals.getInitialApplication();
        try {
            if (Flags.updateServiceIpcWrapper()) {
                webViewProviderResponseWaitForAndGetProvider = ((WebViewUpdateManager) initialApplication.getSystemService(WebViewUpdateManager.class)).waitForAndGetProvider();
            } else {
                webViewProviderResponseWaitForAndGetProvider = getUpdateService().waitForAndGetProvider();
            }
            if (webViewProviderResponseWaitForAndGetProvider.status != 0 && webViewProviderResponseWaitForAndGetProvider.status != 3) {
                return webViewProviderResponseWaitForAndGetProvider.status;
            }
            if (!webViewProviderResponseWaitForAndGetProvider.packageInfo.packageName.equals(str)) {
                return 1;
            }
            try {
                int iLoadNativeLibrary = WebViewLibraryLoader.loadNativeLibrary(classLoader, getWebViewLibrary(initialApplication.getPackageManager().getPackageInfo(str, 128).applicationInfo));
                return iLoadNativeLibrary == 0 ? webViewProviderResponseWaitForAndGetProvider.status : iLoadNativeLibrary;
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e(LOGTAG, "Couldn't find package " + str);
                return 1;
            }
        } catch (Exception e) {
            Log.e(LOGTAG, "error waiting for relro creation", e);
            return 8;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0045, code lost:
    
        if (r2 != 1002) goto L22;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7, types: [long] */
    /* JADX WARN: Type inference failed for: r3v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static WebViewFactoryProvider getProvider() {
        ?? r3;
        synchronized (sProviderLock) {
            WebViewFactoryProvider webViewFactoryProvider = sProviderInstance;
            if (webViewFactoryProvider != null) {
                return webViewFactoryProvider;
            }
            sTimestamps.mWebViewLoadStart = SystemClock.uptimeMillis();
            int appId = UserHandle.getAppId(Process.myUid());
            String property = System.getProperty(WEBVIEW_PAC_PROPERTY);
            if (appId == 1002 && property != null && property.equalsIgnoreCase("enable")) {
                Log.d(LOGTAG, "enable webview for knox vpn proxy module");
                r3 = "enable webview for knox vpn proxy module";
            } else {
                if (appId != 0 && appId != 1000 && appId != 1001) {
                    r3 = 1027;
                    if (appId != 1027) {
                    }
                }
                throw new UnsupportedOperationException("For security reasons, WebView is not allowed in privileged processes");
            }
            if (!isWebViewSupported()) {
                throw new UnsupportedOperationException();
            }
            if (sWebViewDisabled) {
                throw new IllegalStateException("WebView.disableWebView() was called: WebView is disabled");
            }
            try {
                r3 = 16;
                long j = 16;
                long j2 = 16;
                Trace.traceBegin(16L, "WebViewFactory.getProvider()");
                try {
                    Method method = getProviderClass().getMethod(CHROMIUM_WEBVIEW_FACTORY_METHOD, WebViewDelegate.class);
                    Trace.traceBegin(16L, "WebViewFactoryProvider invocation");
                    try {
                        sProviderInstance = (WebViewFactoryProvider) method.invoke(null, new WebViewDelegate());
                        if (!isSetDataDirectorySuffix) {
                            isSetDataDirectorySuffix = false;
                            String str = SystemProperties.get("persist.sys.app_webview_preload_need", "false");
                            if (str.startsWith("launching")) {
                                String str2 = str.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)[1];
                                SecIpmManager secIpmManager = (SecIpmManager) AppGlobals.getInitialApplication().getSystemService("PkgPredictorService");
                                if (secIpmManager != null) {
                                    secIpmManager.setWebViewPreload("load-" + str2, -100);
                                }
                            }
                        }
                        WebViewFactoryProvider webViewFactoryProvider2 = sProviderInstance;
                        Trace.traceEnd(16L);
                        return webViewFactoryProvider2;
                    } finally {
                    }
                } catch (Exception e) {
                    Log.e(LOGTAG, "error instantiating provider", e);
                    throw new AndroidRuntimeException(e);
                }
            } finally {
            }
        }
    }

    private static boolean signaturesEquals(Signature[] signatureArr, Signature[] signatureArr2) {
        if (signatureArr == null) {
            return signatureArr2 == null;
        }
        if (signatureArr2 == null) {
            return false;
        }
        ArraySet arraySet = new ArraySet();
        for (Signature signature : signatureArr) {
            arraySet.add(signature);
        }
        ArraySet arraySet2 = new ArraySet();
        for (Signature signature2 : signatureArr2) {
            arraySet2.add(signature2);
        }
        return arraySet.equals(arraySet2);
    }

    private static void verifyPackageInfo(PackageInfo packageInfo, PackageInfo packageInfo2) throws MissingWebViewPackageException {
        if (!packageInfo.packageName.equals(packageInfo2.packageName)) {
            throw new MissingWebViewPackageException("Failed to verify WebView provider, packageName mismatch, expected: " + packageInfo.packageName + " actual: " + packageInfo2.packageName);
        }
        if (packageInfo.getLongVersionCode() > packageInfo2.getLongVersionCode()) {
            throw new MissingWebViewPackageException("Failed to verify WebView provider, version code is lower than expected: " + packageInfo.getLongVersionCode() + " actual: " + packageInfo2.getLongVersionCode());
        }
        if (getWebViewLibrary(packageInfo2.applicationInfo) == null) {
            throw new MissingWebViewPackageException("Tried to load an invalid WebView provider: " + packageInfo2.packageName);
        }
        if (!signaturesEquals(packageInfo.signatures, packageInfo2.signatures)) {
            throw new MissingWebViewPackageException("Failed to verify WebView provider, signature mismatch");
        }
    }

    private static boolean isEnabledPackage(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        return packageInfo.applicationInfo.enabled;
    }

    private static boolean isInstalledPackage(PackageInfo packageInfo) {
        return (packageInfo == null || (packageInfo.applicationInfo.flags & 8388608) == 0 || (packageInfo.applicationInfo.privateFlags & 1) != 0) ? false : true;
    }

    private static Context getWebViewContextAndSetProvider() throws MissingWebViewPackageException {
        WebViewProviderResponse webViewProviderResponseWaitForAndGetProvider;
        Application initialApplication = AppGlobals.getInitialApplication();
        try {
            Trace.traceBegin(16L, "WebViewUpdateService.waitForAndGetProvider()");
            try {
                if (Flags.updateServiceIpcWrapper()) {
                    webViewProviderResponseWaitForAndGetProvider = ((WebViewUpdateManager) initialApplication.getSystemService(WebViewUpdateManager.class)).waitForAndGetProvider();
                } else {
                    webViewProviderResponseWaitForAndGetProvider = getUpdateService().waitForAndGetProvider();
                }
                Trace.traceEnd(16L);
                if (webViewProviderResponseWaitForAndGetProvider.status != 0 && webViewProviderResponseWaitForAndGetProvider.status != 3) {
                    throw new MissingWebViewPackageException("Failed to load WebView provider: " + getWebViewPreparationErrorReason(webViewProviderResponseWaitForAndGetProvider.status));
                }
                Trace.traceBegin(16L, "ActivityManager.addPackageDependency()");
                try {
                    ActivityManager.getService().addPackageDependency(webViewProviderResponseWaitForAndGetProvider.packageInfo.packageName);
                    Trace.traceEnd(16L);
                    PackageManager packageManager = initialApplication.getPackageManager();
                    Trace.traceBegin(16L, "PackageManager.getPackageInfo()");
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(webViewProviderResponseWaitForAndGetProvider.packageInfo.packageName, 9408);
                        Trace.traceEnd(16L);
                        if (!isInstalledPackage(packageInfo)) {
                            throw new MissingWebViewPackageException(TextUtils.formatSimple("Current WebView Package (%s) is not installed for the current user", packageInfo.packageName));
                        }
                        if (!isEnabledPackage(packageInfo)) {
                            throw new MissingWebViewPackageException(TextUtils.formatSimple("Current WebView Package (%s) is not enabled for the current user", packageInfo.packageName));
                        }
                        verifyPackageInfo(webViewProviderResponseWaitForAndGetProvider.packageInfo, packageInfo);
                        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                        Trace.traceBegin(16L, "initialApplication.createApplicationContext");
                        StartupTimestamps startupTimestamps = sTimestamps;
                        startupTimestamps.mCreateContextStart = SystemClock.uptimeMillis();
                        try {
                            Context contextCreateApplicationContext = initialApplication.createApplicationContext(applicationInfo, 3);
                            sPackageInfo = packageInfo;
                            startupTimestamps.mCreateContextEnd = SystemClock.uptimeMillis();
                            return contextCreateApplicationContext;
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
                Context webViewContextAndSetProvider = getWebViewContextAndSetProvider();
                Trace.traceEnd(16L);
                Log.i(LOGTAG, "Loading " + sPackageInfo.packageName + " version " + sPackageInfo.versionName + " (code " + sPackageInfo.getLongVersionCode() + NavigationBarInflaterView.KEY_CODE_END);
                Trace.traceBegin(16L, "WebViewFactory.getChromiumProviderClass()");
                try {
                    try {
                        sTimestamps.mAddAssetsStart = SystemClock.uptimeMillis();
                        if (android.content.res.Flags.registerResourcePaths()) {
                            Resources.registerResourcePaths(webViewContextAndSetProvider.getPackageName(), webViewContextAndSetProvider.getApplicationInfo());
                        } else {
                            for (String str : webViewContextAndSetProvider.getApplicationInfo().getAllApkPaths()) {
                                initialApplication.getAssets().addAssetPathAsSharedLibrary(str);
                            }
                        }
                        StartupTimestamps startupTimestamps = sTimestamps;
                        long jUptimeMillis = SystemClock.uptimeMillis();
                        startupTimestamps.mGetClassLoaderStart = jUptimeMillis;
                        startupTimestamps.mAddAssetsEnd = jUptimeMillis;
                        ClassLoader classLoader = webViewContextAndSetProvider.getClassLoader();
                        Trace.traceBegin(16L, "WebViewFactory.loadNativeLibrary()");
                        long jUptimeMillis2 = SystemClock.uptimeMillis();
                        startupTimestamps.mNativeLoadStart = jUptimeMillis2;
                        startupTimestamps.mGetClassLoaderEnd = jUptimeMillis2;
                        WebViewLibraryLoader.loadNativeLibrary(classLoader, getWebViewLibrary(sPackageInfo.applicationInfo));
                        Trace.traceEnd(16L);
                        Trace.traceBegin(16L, "Class.forName()");
                        long jUptimeMillis3 = SystemClock.uptimeMillis();
                        startupTimestamps.mProviderClassForNameStart = jUptimeMillis3;
                        startupTimestamps.mNativeLoadEnd = jUptimeMillis3;
                        try {
                            Class<WebViewFactoryProvider> webViewProviderClass = getWebViewProviderClass(classLoader);
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
                    Log.e(LOGTAG, "error loading provider", e);
                    throw new AndroidRuntimeException(e);
                }
            } finally {
            }
        } catch (MissingWebViewPackageException e2) {
            Log.e(LOGTAG, "Chromium WebView package does not exist", e2);
            throw new AndroidRuntimeException(e2);
        }
    }

    public static void prepareWebViewInZygote() {
        try {
            WebViewLibraryLoader.reserveAddressSpaceInZygote();
        } catch (Throwable th) {
            Log.e(LOGTAG, "error preparing native loader", th);
        }
    }

    public static int onWebViewProviderChanged(PackageInfo packageInfo) {
        int iPrepareNativeLibraries;
        try {
            iPrepareNativeLibraries = WebViewLibraryLoader.prepareNativeLibraries(packageInfo);
        } catch (Throwable th) {
            Slog.wtf(LOGTAG, "error preparing webview native library", th);
            iPrepareNativeLibraries = 0;
        }
        WebViewZygote.onWebViewProviderChanged(packageInfo);
        return iPrepareNativeLibraries;
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
