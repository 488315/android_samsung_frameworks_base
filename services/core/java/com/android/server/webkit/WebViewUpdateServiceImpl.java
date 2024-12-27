package com.android.server.webkit;

import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.util.AndroidRuntimeException;
import android.util.Slog;
import android.webkit.Flags;
import android.webkit.UserPackage;
import android.webkit.WebViewFactory;
import android.webkit.WebViewProviderInfo;
import android.webkit.WebViewZygote;

import com.android.modules.expresslog.Counter;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WebViewUpdateServiceImpl implements WebViewUpdateServiceInterface {
    public final Context mContext;
    public final SystemImpl mSystemInterface;
    public long mMinimumVersionCode = -1;
    public int mNumRelroCreationsStarted = 0;
    public int mNumRelroCreationsFinished = 0;
    public boolean mWebViewPackageDirty = false;
    public boolean mAnyWebViewInstalled = false;
    public PackageInfo mCurrentWebViewPackage = null;
    public final Object mLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProviderAndPackageInfo {
        public final PackageInfo packageInfo;
        public final WebViewProviderInfo provider;

        public ProviderAndPackageInfo(
                WebViewProviderInfo webViewProviderInfo, PackageInfo packageInfo) {
            this.provider = webViewProviderInfo;
            this.packageInfo = packageInfo;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class WebViewPackageMissingException extends Exception {
        public WebViewPackageMissingException(String str) {
            super(str);
        }
    }

    public WebViewUpdateServiceImpl(Context context, SystemImpl systemImpl) {
        this.mContext = context;
        this.mSystemInterface = systemImpl;
    }

    public static boolean isInstalledAndEnabledForAllUsers(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UserPackage userPackage = (UserPackage) it.next();
            if (!userPackage.isInstalledPackage() || !userPackage.isEnabledPackage()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final String changeProviderAndSetting(String str) {
        PackageInfo updateCurrentWebViewPackage$1 = updateCurrentWebViewPackage$1(str);
        return updateCurrentWebViewPackage$1 == null
                ? ""
                : updateCurrentWebViewPackage$1.packageName;
    }

    public final void checkIfRelrosDoneLocked$1() {
        if (this.mNumRelroCreationsStarted == this.mNumRelroCreationsFinished) {
            if (!this.mWebViewPackageDirty) {
                this.mLock.notifyAll();
                return;
            }
            this.mWebViewPackageDirty = false;
            try {
                onWebViewProviderChanged$1(findPreferredWebViewPackage$1());
            } catch (WebViewPackageMissingException unused) {
                this.mCurrentWebViewPackage = null;
            }
        }
    }

    public final void dumpAllPackageInformationLocked$1(PrintWriter printWriter) {
        SystemImpl systemImpl = this.mSystemInterface;
        WebViewProviderInfo[] webViewProviderInfoArr = systemImpl.mWebViewProviderPackages;
        printWriter.println("  WebView packages:");
        for (WebViewProviderInfo webViewProviderInfo : webViewProviderInfoArr) {
            Context context = this.mContext;
            systemImpl.getClass();
            PackageInfo packageInfo =
                    ((UserPackage)
                                    UserPackage.getPackageInfosAllUsers(
                                                    context,
                                                    webViewProviderInfo.packageName,
                                                    272630976)
                                            .get(0))
                            .getPackageInfo();
            if (packageInfo == null) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        printWriter, "    ", webViewProviderInfo.packageName, " is NOT installed.");
            } else {
                int validityResult$1 = validityResult$1(webViewProviderInfo, packageInfo);
                String format =
                        String.format(
                                "versionName: %s, versionCode: %d, targetSdkVersion: %d",
                                packageInfo.versionName,
                                Long.valueOf(packageInfo.getLongVersionCode()),
                                Integer.valueOf(packageInfo.applicationInfo.targetSdkVersion));
                if (validityResult$1 == 0) {
                    Context context2 = this.mContext;
                    systemImpl.getClass();
                    ProxyManager$$ExternalSyntheticOutline0.m(
                            printWriter,
                            isInstalledAndEnabledForAllUsers(
                                            UserPackage.getPackageInfosAllUsers(
                                                    context2,
                                                    webViewProviderInfo.packageName,
                                                    272630976))
                                    ? ""
                                    : "NOT",
                            " installed/enabled for all users",
                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                    "    Valid package ",
                                    packageInfo.packageName,
                                    " (",
                                    format,
                                    ") is "));
                } else {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                    "    Invalid package ",
                                    packageInfo.packageName,
                                    " (",
                                    format,
                                    "), reason: "),
                            validityResult$1 != 1
                                    ? validityResult$1 != 2
                                            ? validityResult$1 != 3
                                                    ? validityResult$1 != 4
                                                            ? "Unexcepted validity-reason"
                                                            : "No WebView-library manifest flag"
                                                    : "Incorrect signature"
                                            : "Version code too low"
                                    : "SDK version too low",
                            printWriter);
                }
            }
        }
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final void dumpState(PrintWriter printWriter) {
        printWriter.println("Current WebView Update Service state");
        printWriter.println("  Multiprocess enabled: " + isMultiProcessEnabled());
        synchronized (this.mLock) {
            try {
                PackageInfo packageInfo = this.mCurrentWebViewPackage;
                if (packageInfo == null) {
                    printWriter.println("  Current WebView package is null");
                } else {
                    printWriter.println(
                            "  Current WebView package (name, version): ("
                                    + packageInfo.packageName
                                    + ", "
                                    + packageInfo.versionName
                                    + ")");
                }
                printWriter.println(String.format("  Minimum targetSdkVersion: %d", 33));
                printWriter.println(
                        String.format(
                                "  Minimum WebView version code: %d",
                                Long.valueOf(this.mMinimumVersionCode)));
                printWriter.println(
                        String.format(
                                "  Number of relros started: %d",
                                Integer.valueOf(this.mNumRelroCreationsStarted)));
                printWriter.println(
                        String.format(
                                "  Number of relros finished: %d",
                                Integer.valueOf(this.mNumRelroCreationsFinished)));
                printWriter.println("  WebView package dirty: " + this.mWebViewPackageDirty);
                printWriter.println(
                        "  Any WebView package installed: " + this.mAnyWebViewInstalled);
                try {
                    PackageInfo findPreferredWebViewPackage$1 = findPreferredWebViewPackage$1();
                    printWriter.println(
                            "  Preferred WebView package (name, version): ("
                                    + findPreferredWebViewPackage$1.packageName
                                    + ", "
                                    + findPreferredWebViewPackage$1.versionName
                                    + ")");
                } catch (WebViewPackageMissingException unused) {
                    printWriter.println("  Preferred WebView package: none");
                }
                dumpAllPackageInformationLocked$1(printWriter);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final void enableMultiProcess(boolean z) {
        PackageInfo currentWebViewPackage = getCurrentWebViewPackage();
        Context context = this.mContext;
        int i = z ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        SystemImpl systemImpl = this.mSystemInterface;
        systemImpl.getClass();
        if (Flags.updateServiceV2()) {
            throw new IllegalStateException(
                    "setMultiProcessSetting shouldn't be called if update_service_v2 flag is set.");
        }
        Settings.Global.putInt(context.getContentResolver(), "webview_multiprocess", i);
        systemImpl.getClass();
        if (Flags.updateServiceV2()) {
            throw new IllegalStateException(
                    "notifyZygote shouldn't be called if update_service_v2 flag is set.");
        }
        WebViewZygote.setMultiprocessEnabled(z);
        if (currentWebViewPackage != null) {
            systemImpl.killPackageDependents(currentWebViewPackage.packageName);
        }
    }

    public final PackageInfo findPreferredWebViewPackage$1() {
        ProviderAndPackageInfo[] validWebViewPackagesAndInfos = getValidWebViewPackagesAndInfos();
        Context context = this.mContext;
        SystemImpl systemImpl = this.mSystemInterface;
        systemImpl.getClass();
        String string = Settings.Global.getString(context.getContentResolver(), "webview_provider");
        for (ProviderAndPackageInfo providerAndPackageInfo : validWebViewPackagesAndInfos) {
            if (providerAndPackageInfo.provider.packageName.equals(string)) {
                Context context2 = this.mContext;
                WebViewProviderInfo webViewProviderInfo = providerAndPackageInfo.provider;
                systemImpl.getClass();
                if (isInstalledAndEnabledForAllUsers(
                        UserPackage.getPackageInfosAllUsers(
                                context2, webViewProviderInfo.packageName, 272630976))) {
                    return providerAndPackageInfo.packageInfo;
                }
            }
        }
        for (ProviderAndPackageInfo providerAndPackageInfo2 : validWebViewPackagesAndInfos) {
            WebViewProviderInfo webViewProviderInfo2 = providerAndPackageInfo2.provider;
            if (webViewProviderInfo2.availableByDefault) {
                Context context3 = this.mContext;
                systemImpl.getClass();
                if (isInstalledAndEnabledForAllUsers(
                        UserPackage.getPackageInfosAllUsers(
                                context3, webViewProviderInfo2.packageName, 272630976))) {
                    return providerAndPackageInfo2.packageInfo;
                }
            }
        }
        this.mAnyWebViewInstalled = false;
        throw new WebViewPackageMissingException("Could not find a loadable WebView package");
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final PackageInfo getCurrentWebViewPackage() {
        PackageInfo packageInfo;
        synchronized (this.mLock) {
            packageInfo = this.mCurrentWebViewPackage;
        }
        return packageInfo;
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final WebViewProviderInfo getDefaultWebViewPackage() {
        for (WebViewProviderInfo webViewProviderInfo : getWebViewPackages()) {
            if (webViewProviderInfo.availableByDefault) {
                return webViewProviderInfo;
            }
        }
        throw new AndroidRuntimeException("No available by default WebView Provider.");
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final WebViewProviderInfo[] getValidWebViewPackages() {
        ProviderAndPackageInfo[] validWebViewPackagesAndInfos = getValidWebViewPackagesAndInfos();
        WebViewProviderInfo[] webViewProviderInfoArr =
                new WebViewProviderInfo[validWebViewPackagesAndInfos.length];
        for (int i = 0; i < validWebViewPackagesAndInfos.length; i++) {
            webViewProviderInfoArr[i] = validWebViewPackagesAndInfos[i].provider;
        }
        return webViewProviderInfoArr;
    }

    public final ProviderAndPackageInfo[] getValidWebViewPackagesAndInfos() {
        SystemImpl systemImpl = this.mSystemInterface;
        WebViewProviderInfo[] webViewProviderInfoArr = systemImpl.mWebViewProviderPackages;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < webViewProviderInfoArr.length; i++) {
            try {
                PackageInfo packageInfoForProvider =
                        systemImpl.getPackageInfoForProvider(webViewProviderInfoArr[i]);
                if (validityResult$1(webViewProviderInfoArr[i], packageInfoForProvider) == 0) {
                    arrayList.add(
                            new ProviderAndPackageInfo(
                                    webViewProviderInfoArr[i], packageInfoForProvider));
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return (ProviderAndPackageInfo[])
                arrayList.toArray(new ProviderAndPackageInfo[arrayList.size()]);
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final WebViewProviderInfo[] getWebViewPackages() {
        return this.mSystemInterface.mWebViewProviderPackages;
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final void handleNewUser(int i) {
        if (i == 0) {
            return;
        }
        updateCurrentWebViewPackage$1(null);
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final void handleUserRemoved() {
        updateCurrentWebViewPackage$1(null);
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final boolean isMultiProcessEnabled() {
        Context context = this.mContext;
        SystemImpl systemImpl = this.mSystemInterface;
        systemImpl.getClass();
        if (Flags.updateServiceV2()) {
            throw new IllegalStateException(
                    "getMultiProcessSetting shouldn't be called if update_service_v2 flag is set.");
        }
        int i = Settings.Global.getInt(context.getContentResolver(), "webview_multiprocess", 0);
        systemImpl.getClass();
        return i > Integer.MIN_VALUE;
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final void notifyRelroCreationCompleted() {
        synchronized (this.mLock) {
            this.mNumRelroCreationsFinished++;
            checkIfRelrosDoneLocked$1();
        }
    }

    public final void onWebViewProviderChanged$1(PackageInfo packageInfo) {
        synchronized (this.mLock) {
            try {
                this.mAnyWebViewInstalled = true;
                if (this.mNumRelroCreationsStarted == this.mNumRelroCreationsFinished) {
                    this.mSystemInterface.pinWebviewIfRequired(packageInfo.applicationInfo);
                    this.mCurrentWebViewPackage = packageInfo;
                    this.mNumRelroCreationsStarted = Integer.MAX_VALUE;
                    this.mNumRelroCreationsFinished = 0;
                    this.mSystemInterface.getClass();
                    this.mNumRelroCreationsStarted =
                            WebViewFactory.onWebViewProviderChanged(packageInfo);
                    Counter.logIncrement("webview.value_on_webview_provider_changed_counter");
                    if (packageInfo.packageName.equals(getDefaultWebViewPackage().packageName)) {
                        Counter.logIncrement(
                                "webview.value_on_webview_provider_changed_with_default_package_counter");
                    }
                    checkIfRelrosDoneLocked$1();
                } else {
                    this.mWebViewPackageDirty = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (isMultiProcessEnabled()) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(
                    new Runnable() { // from class:
                                     // com.android.server.webkit.WebViewUpdateServiceImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            WebViewUpdateServiceImpl webViewUpdateServiceImpl =
                                    WebViewUpdateServiceImpl.this;
                            webViewUpdateServiceImpl.waitForAndGetProvider();
                            webViewUpdateServiceImpl.mSystemInterface.getClass();
                            WebViewZygote.getProcess();
                        }
                    });
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:9|10|11|(1:13)(1:46)|(2:14|15)|(2:19|(5:21|22|23|(1:25)|27))|42|22|23|(0)|27) */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0052, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0053, code lost:

       r7 = r3;
       r3 = r2;
       r2 = r7;
    */
    /* JADX WARN: Removed duplicated region for block: B:25:0x004e A[Catch: all -> 0x0022, WebViewPackageMissingException -> 0x0052, TRY_LEAVE, TryCatch #1 {WebViewPackageMissingException -> 0x0052, blocks: (B:23:0x0046, B:25:0x004e), top: B:22:0x0046 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0074 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void packageStateChanged(java.lang.String r9) {
        /*
            r8 = this;
            com.android.server.webkit.SystemImpl r0 = r8.mSystemInterface
            android.webkit.WebViewProviderInfo[] r0 = r0.mWebViewProviderPackages
            int r1 = r0.length
            r2 = 0
            r3 = r2
        L7:
            if (r3 >= r1) goto L83
            r4 = r0[r3]
            java.lang.String r5 = r4.packageName
            boolean r5 = r5.equals(r9)
            if (r5 == 0) goto L80
            java.lang.Object r5 = r8.mLock
            monitor-enter(r5)
            r9 = 0
            android.content.pm.PackageInfo r0 = r8.findPreferredWebViewPackage$1()     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L25
            android.content.pm.PackageInfo r1 = r8.mCurrentWebViewPackage     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L25
            if (r1 == 0) goto L29
            java.lang.String r1 = r1.packageName     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L25
            goto L2a
        L22:
            r8 = move-exception
            goto L7e
        L25:
            r0 = move-exception
            r1 = r9
        L27:
            r3 = r2
            goto L56
        L29:
            r1 = r9
        L2a:
            java.lang.String r3 = r4.packageName     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L43
            java.lang.String r6 = r0.packageName     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L43
            boolean r3 = r3.equals(r6)     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L43
            if (r3 != 0) goto L45
            java.lang.String r3 = r4.packageName     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L43
            boolean r3 = r3.equals(r1)     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L43
            if (r3 != 0) goto L45
            android.content.pm.PackageInfo r3 = r8.mCurrentWebViewPackage     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L43
            if (r3 != 0) goto L41
            goto L45
        L41:
            r3 = r2
            goto L46
        L43:
            r0 = move-exception
            goto L27
        L45:
            r3 = 1
        L46:
            java.lang.String r4 = r4.packageName     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L52
            boolean r2 = r4.equals(r1)     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L52
            if (r3 == 0) goto L71
            r8.onWebViewProviderChanged$1(r0)     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L52
            goto L71
        L52:
            r0 = move-exception
            r7 = r3
            r3 = r2
            r2 = r7
        L56:
            r8.mCurrentWebViewPackage = r9     // Catch: java.lang.Throwable -> L22
            java.lang.String r9 = "WebViewUpdateServiceImpl"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L22
            r4.<init>()     // Catch: java.lang.Throwable -> L22
            java.lang.String r6 = "Could not find valid WebView package to create relro with "
            r4.append(r6)     // Catch: java.lang.Throwable -> L22
            r4.append(r0)     // Catch: java.lang.Throwable -> L22
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L22
            android.util.Slog.e(r9, r0)     // Catch: java.lang.Throwable -> L22
            r7 = r3
            r3 = r2
            r2 = r7
        L71:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L22
            if (r3 == 0) goto L7d
            if (r2 != 0) goto L7d
            if (r1 == 0) goto L7d
            com.android.server.webkit.SystemImpl r8 = r8.mSystemInterface
            r8.killPackageDependents(r1)
        L7d:
            return
        L7e:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L22
            throw r8
        L80:
            int r3 = r3 + 1
            goto L7
        L83:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.webkit.WebViewUpdateServiceImpl.packageStateChanged(java.lang.String):void");
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final void prepareWebViewInSystemServer() {
        WebViewProviderInfo webViewProviderInfo;
        SystemImpl systemImpl = this.mSystemInterface;
        boolean isMultiProcessEnabled = isMultiProcessEnabled();
        systemImpl.getClass();
        if (Flags.updateServiceV2()) {
            throw new IllegalStateException(
                    "notifyZygote shouldn't be called if update_service_v2 flag is set.");
        }
        WebViewZygote.setMultiprocessEnabled(isMultiProcessEnabled);
        try {
            synchronized (this.mLock) {
                try {
                    this.mCurrentWebViewPackage = findPreferredWebViewPackage$1();
                    SystemImpl systemImpl2 = this.mSystemInterface;
                    Context context = this.mContext;
                    systemImpl2.getClass();
                    String string =
                            Settings.Global.getString(
                                    context.getContentResolver(), "webview_provider");
                    if (string != null && !string.equals(this.mCurrentWebViewPackage.packageName)) {
                        this.mSystemInterface.updateUserSetting(
                                this.mContext, this.mCurrentWebViewPackage.packageName);
                    }
                    onWebViewProviderChanged$1(this.mCurrentWebViewPackage);
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (WebViewPackageMissingException e) {
            Slog.e(
                    "WebViewUpdateServiceImpl",
                    "Could not find valid WebView package to create relro with",
                    e);
        } catch (Throwable th2) {
            Slog.wtf(
                    "WebViewUpdateServiceImpl",
                    "error preparing webview provider from system server",
                    th2);
        }
        if (getCurrentWebViewPackage() == null) {
            WebViewProviderInfo[] webViewProviderInfoArr =
                    this.mSystemInterface.mWebViewProviderPackages;
            int length = webViewProviderInfoArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    webViewProviderInfo = null;
                    break;
                }
                webViewProviderInfo = webViewProviderInfoArr[i];
                if (webViewProviderInfo.isFallback) {
                    break;
                } else {
                    i++;
                }
            }
            if (webViewProviderInfo == null) {
                Slog.e("WebViewUpdateServiceImpl", "No valid provider and no fallback available.");
            } else {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("No valid provider, trying to enable "),
                        webViewProviderInfo.packageName,
                        "WebViewUpdateServiceImpl");
                this.mSystemInterface.enablePackageForAllUsers(
                        this.mContext, webViewProviderInfo.packageName);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002b A[Catch: all -> 0x0011, TRY_ENTER, TryCatch #0 {, blocks: (B:4:0x0005, B:6:0x0009, B:8:0x0013, B:10:0x0019, B:14:0x002b, B:15:0x002e, B:25:0x003c, B:26:0x004f), top: B:3:0x0005, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.pm.PackageInfo updateCurrentWebViewPackage$1(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = "Couldn't find WebView package to use "
            java.lang.Object r1 = r5.mLock
            monitor-enter(r1)
            android.content.pm.PackageInfo r2 = r5.mCurrentWebViewPackage     // Catch: java.lang.Throwable -> L11
            if (r6 == 0) goto L13
            com.android.server.webkit.SystemImpl r3 = r5.mSystemInterface     // Catch: java.lang.Throwable -> L11
            android.content.Context r4 = r5.mContext     // Catch: java.lang.Throwable -> L11
            r3.updateUserSetting(r4, r6)     // Catch: java.lang.Throwable -> L11
            goto L13
        L11:
            r5 = move-exception
            goto L51
        L13:
            android.content.pm.PackageInfo r6 = r5.findPreferredWebViewPackage$1()     // Catch: java.lang.Throwable -> L11 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L26
            if (r2 == 0) goto L28
            java.lang.String r3 = r6.packageName     // Catch: java.lang.Throwable -> L11 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L26
            java.lang.String r4 = r2.packageName     // Catch: java.lang.Throwable -> L11 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L26
            boolean r0 = r3.equals(r4)     // Catch: java.lang.Throwable -> L11 com.android.server.webkit.WebViewUpdateServiceImpl.WebViewPackageMissingException -> L26
            if (r0 != 0) goto L24
            goto L28
        L24:
            r0 = 0
            goto L29
        L26:
            r6 = move-exception
            goto L3b
        L28:
            r0 = 1
        L29:
            if (r0 == 0) goto L2e
            r5.onWebViewProviderChanged$1(r6)     // Catch: java.lang.Throwable -> L11
        L2e:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L11
            if (r0 == 0) goto L3a
            if (r2 == 0) goto L3a
            com.android.server.webkit.SystemImpl r5 = r5.mSystemInterface
            java.lang.String r0 = r2.packageName
            r5.killPackageDependents(r0)
        L3a:
            return r6
        L3b:
            r2 = 0
            r5.mCurrentWebViewPackage = r2     // Catch: java.lang.Throwable -> L11
            java.lang.String r5 = "WebViewUpdateServiceImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L11
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L11
            r3.append(r6)     // Catch: java.lang.Throwable -> L11
            java.lang.String r6 = r3.toString()     // Catch: java.lang.Throwable -> L11
            android.util.Slog.e(r5, r6)     // Catch: java.lang.Throwable -> L11
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L11
            return r2
        L51:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L11
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.webkit.WebViewUpdateServiceImpl.updateCurrentWebViewPackage$1(java.lang.String):android.content.pm.PackageInfo");
    }

    public final int validityResult$1(
            WebViewProviderInfo webViewProviderInfo, PackageInfo packageInfo) {
        int i;
        if (!UserPackage.hasCorrectTargetSdkVersion(packageInfo)) {
            return 1;
        }
        long longVersionCode = packageInfo.getLongVersionCode();
        long j = this.mMinimumVersionCode;
        SystemImpl systemImpl = this.mSystemInterface;
        if (j <= 0) {
            long j2 = -1;
            for (WebViewProviderInfo webViewProviderInfo2 : systemImpl.mWebViewProviderPackages) {
                if (webViewProviderInfo2.availableByDefault) {
                    try {
                        String str = webViewProviderInfo2.packageName;
                        systemImpl.getClass();
                        long longVersionCode2 =
                                AppGlobals.getInitialApplication()
                                        .getPackageManager()
                                        .getPackageInfo(str, 2097152)
                                        .getLongVersionCode();
                        if (j2 < 0 || longVersionCode2 < j2) {
                            j2 = longVersionCode2;
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
            }
            this.mMinimumVersionCode = j2;
            j = j2;
        }
        if (longVersionCode / 100000 < j / 100000) {
            systemImpl.getClass();
            if (!Build.IS_DEBUGGABLE) {
                return 2;
            }
        }
        systemImpl.getClass();
        if (!Build.IS_DEBUGGABLE && !packageInfo.applicationInfo.isSystemApp()) {
            if (packageInfo.signatures.length != 1) {
                return 3;
            }
            for (Signature signature : webViewProviderInfo.signatures) {
                i = 0;
                if (!signature.equals(packageInfo.signatures[0])) {}
            }
            return 3;
        }
        i = 0;
        if (WebViewFactory.getWebViewLibrary(packageInfo.applicationInfo) == null) {
            return 4;
        }
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001c, code lost:

       if (r11.mAnyWebViewInstalled != false) goto L10;
    */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0023 A[Catch: all -> 0x0035, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x000e, B:6:0x0014, B:8:0x001a, B:12:0x0023, B:16:0x002d, B:17:0x0037, B:19:0x003b, B:21:0x0041, B:26:0x0046, B:29:0x0083, B:36:0x004b, B:39:0x0051), top: B:3:0x000e }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0045 -> B:10:0x001e). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:40:0x0020 -> B:11:0x0021). Please report as a decompilation issue!!! */
    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.webkit.WebViewProviderResponse waitForAndGetProvider() {
        /*
            r11 = this;
            long r0 = java.lang.System.nanoTime()
            r2 = 1000000(0xf4240, double:4.940656E-318)
            long r0 = r0 / r2
            r4 = 1000(0x3e8, double:4.94E-321)
            long r0 = r0 + r4
            java.lang.Object r4 = r11.mLock
            monitor-enter(r4)
            boolean r5 = r11.mWebViewPackageDirty     // Catch: java.lang.Throwable -> L35
            r6 = 1
            r7 = 0
            if (r5 != 0) goto L20
            int r5 = r11.mNumRelroCreationsStarted     // Catch: java.lang.Throwable -> L35
            int r8 = r11.mNumRelroCreationsFinished     // Catch: java.lang.Throwable -> L35
            if (r5 != r8) goto L20
            boolean r5 = r11.mAnyWebViewInstalled     // Catch: java.lang.Throwable -> L35
            if (r5 == 0) goto L20
        L1e:
            r5 = r6
            goto L21
        L20:
            r5 = r7
        L21:
            if (r5 != 0) goto L46
            long r8 = java.lang.System.nanoTime()     // Catch: java.lang.Throwable -> L35
            long r8 = r8 / r2
            int r10 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r10 < 0) goto L2d
            goto L46
        L2d:
            java.lang.Object r5 = r11.mLock     // Catch: java.lang.Throwable -> L35 java.lang.InterruptedException -> L37
            long r8 = r0 - r8
            r5.wait(r8)     // Catch: java.lang.Throwable -> L35 java.lang.InterruptedException -> L37
            goto L37
        L35:
            r11 = move-exception
            goto L93
        L37:
            boolean r5 = r11.mWebViewPackageDirty     // Catch: java.lang.Throwable -> L35
            if (r5 != 0) goto L20
            int r5 = r11.mNumRelroCreationsStarted     // Catch: java.lang.Throwable -> L35
            int r8 = r11.mNumRelroCreationsFinished     // Catch: java.lang.Throwable -> L35
            if (r5 != r8) goto L20
            boolean r5 = r11.mAnyWebViewInstalled     // Catch: java.lang.Throwable -> L35
            if (r5 == 0) goto L20
            goto L1e
        L46:
            android.content.pm.PackageInfo r0 = r11.mCurrentWebViewPackage     // Catch: java.lang.Throwable -> L35
            if (r5 == 0) goto L4b
            goto L83
        L4b:
            boolean r1 = r11.mAnyWebViewInstalled     // Catch: java.lang.Throwable -> L35
            if (r1 != 0) goto L51
            r7 = 4
            goto L83
        L51:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L35
            r1.<init>()     // Catch: java.lang.Throwable -> L35
            java.lang.String r2 = "Timed out waiting for relro creation, relros started "
            r1.append(r2)     // Catch: java.lang.Throwable -> L35
            int r2 = r11.mNumRelroCreationsStarted     // Catch: java.lang.Throwable -> L35
            r1.append(r2)     // Catch: java.lang.Throwable -> L35
            java.lang.String r2 = " relros finished "
            r1.append(r2)     // Catch: java.lang.Throwable -> L35
            int r2 = r11.mNumRelroCreationsFinished     // Catch: java.lang.Throwable -> L35
            r1.append(r2)     // Catch: java.lang.Throwable -> L35
            java.lang.String r2 = " package dirty? "
            r1.append(r2)     // Catch: java.lang.Throwable -> L35
            boolean r11 = r11.mWebViewPackageDirty     // Catch: java.lang.Throwable -> L35
            r1.append(r11)     // Catch: java.lang.Throwable -> L35
            java.lang.String r11 = r1.toString()     // Catch: java.lang.Throwable -> L35
            java.lang.String r1 = "WebViewUpdateServiceImpl"
            android.util.Slog.e(r1, r11)     // Catch: java.lang.Throwable -> L35
            r1 = 64
            android.os.Trace.instant(r1, r11)     // Catch: java.lang.Throwable -> L35
            r7 = 3
        L83:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L35
            if (r5 != 0) goto L8d
            java.lang.String r11 = "WebViewUpdateServiceImpl"
            java.lang.String r1 = "creating relro file timed out"
            android.util.Slog.w(r11, r1)
        L8d:
            android.webkit.WebViewProviderResponse r11 = new android.webkit.WebViewProviderResponse
            r11.<init>(r0, r7)
            return r11
        L93:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L35
            throw r11
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.webkit.WebViewUpdateServiceImpl.waitForAndGetProvider():android.webkit.WebViewProviderResponse");
    }
}
