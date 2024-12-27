package com.android.server.webkit;

import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.Slog;
import android.webkit.UserPackage;
import android.webkit.WebViewFactory;
import android.webkit.WebViewProviderInfo;
import android.webkit.WebViewZygote;

import com.android.modules.expresslog.Counter;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WebViewUpdateServiceImpl2 implements WebViewUpdateServiceInterface {
    public final Context mContext;
    public final WebViewProviderInfo mDefaultProvider;
    public final SystemImpl mSystemInterface;
    public long mMinimumVersionCode = -1;
    public int mNumRelroCreationsStarted = 0;
    public int mNumRelroCreationsFinished = 0;
    public boolean mWebViewPackageDirty = false;
    public boolean mAnyWebViewInstalled = false;
    public boolean mAttemptedToRepairBefore = false;
    public PackageInfo mCurrentWebViewPackage = null;
    public final Object mLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProviderAndPackageInfo {
        public final WebViewProviderInfo provider;

        public ProviderAndPackageInfo(WebViewProviderInfo webViewProviderInfo) {
            this.provider = webViewProviderInfo;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class WebViewPackageMissingException extends Exception {
        public WebViewPackageMissingException(String str) {
            super(str);
        }
    }

    public WebViewUpdateServiceImpl2(Context context, SystemImpl systemImpl) {
        int i = 0;
        WebViewProviderInfo webViewProviderInfo = null;
        this.mContext = context;
        this.mSystemInterface = systemImpl;
        WebViewProviderInfo[] webViewPackages = getWebViewPackages();
        int length = webViewPackages.length;
        while (true) {
            if (i >= length) {
                break;
            }
            WebViewProviderInfo webViewProviderInfo2 = webViewPackages[i];
            if (webViewProviderInfo2.availableByDefault) {
                webViewProviderInfo = webViewProviderInfo2;
                break;
            }
            i++;
        }
        if (webViewProviderInfo == null) {
            throw new AndroidRuntimeException("No available by default WebView Provider.");
        }
        this.mDefaultProvider = webViewProviderInfo;
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

    public final void attemptRepair() {
        synchronized (this.mLock) {
            try {
                if (this.mAttemptedToRepairBefore) {
                    return;
                }
                this.mAttemptedToRepairBefore = true;
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder(
                                "No provider available for all users, trying to install and"
                                    + " enable "),
                        this.mDefaultProvider.packageName,
                        "WebViewUpdateServiceImpl2");
                SystemImpl systemImpl = this.mSystemInterface;
                Context context = this.mContext;
                String str = this.mDefaultProvider.packageName;
                systemImpl.getClass();
                Iterator it =
                        ((UserManager) context.getSystemService(UserManager.class))
                                .getUsers()
                                .iterator();
                while (it.hasNext()) {
                    AppGlobals.getInitialApplication()
                            .createContextAsUser(UserHandle.of(((UserInfo) it.next()).id), 0)
                            .getPackageManager()
                            .getPackageInstaller()
                            .installExistingPackage(str, 0, null);
                }
                this.mSystemInterface.enablePackageForAllUsers(
                        this.mContext, this.mDefaultProvider.packageName);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final String changeProviderAndSetting(String str) {
        PackageInfo updateCurrentWebViewPackage = updateCurrentWebViewPackage(str);
        return updateCurrentWebViewPackage == null ? "" : updateCurrentWebViewPackage.packageName;
    }

    public final void checkIfRelrosDoneLocked() {
        if (this.mNumRelroCreationsStarted == this.mNumRelroCreationsFinished) {
            if (!this.mWebViewPackageDirty) {
                this.mLock.notifyAll();
                return;
            }
            this.mWebViewPackageDirty = false;
            try {
                onWebViewProviderChanged(findPreferredWebViewPackage());
            } catch (WebViewPackageMissingException unused) {
                this.mCurrentWebViewPackage = null;
            }
        }
    }

    public final void dumpAllPackageInformationLocked(PrintWriter printWriter) {
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
                printWriter.println(
                        TextUtils.formatSimple(
                                "    %s is NOT installed.",
                                new Object[] {webViewProviderInfo.packageName}));
            } else {
                int validityResult = validityResult(webViewProviderInfo, packageInfo);
                String formatSimple =
                        TextUtils.formatSimple(
                                "versionName: %s, versionCode: %d, targetSdkVersion: %d",
                                new Object[] {
                                    packageInfo.versionName,
                                    Long.valueOf(packageInfo.getLongVersionCode()),
                                    Integer.valueOf(packageInfo.applicationInfo.targetSdkVersion)
                                });
                if (validityResult == 0) {
                    Context context2 = this.mContext;
                    systemImpl.getClass();
                    printWriter.println(
                            TextUtils.formatSimple(
                                    "    Valid package %s (%s) is %s installed/enabled for all"
                                        + " users",
                                    new Object[] {
                                        packageInfo.packageName,
                                        formatSimple,
                                        isInstalledAndEnabledForAllUsers(
                                                        UserPackage.getPackageInfosAllUsers(
                                                                context2,
                                                                webViewProviderInfo.packageName,
                                                                272630976))
                                                ? ""
                                                : "NOT"
                                    }));
                } else {
                    printWriter.println(
                            TextUtils.formatSimple(
                                    "    Invalid package %s (%s), reason: %s",
                                    new Object[] {
                                        packageInfo.packageName,
                                        formatSimple,
                                        validityResult != 1
                                                ? validityResult != 2
                                                        ? validityResult != 3
                                                                ? validityResult != 4
                                                                        ? "Unexcepted"
                                                                              + " validity-reason"
                                                                        : "No WebView-library"
                                                                              + " manifest flag"
                                                                : "Incorrect signature"
                                                        : "Version code too low"
                                                : "SDK version too low"
                                    }));
                }
            }
        }
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final void dumpState(PrintWriter printWriter) {
        printWriter.println("Current WebView Update Service state");
        synchronized (this.mLock) {
            try {
                PackageInfo packageInfo = this.mCurrentWebViewPackage;
                if (packageInfo == null) {
                    printWriter.println("  Current WebView package is null");
                } else {
                    printWriter.println(
                            TextUtils.formatSimple(
                                    "  Current WebView package (name, version): (%s, %s)",
                                    new Object[] {
                                        packageInfo.packageName, packageInfo.versionName
                                    }));
                }
                printWriter.println(
                        TextUtils.formatSimple(
                                "  Minimum targetSdkVersion: %d", new Object[] {33}));
                printWriter.println(
                        TextUtils.formatSimple(
                                "  Minimum WebView version code: %d",
                                new Object[] {Long.valueOf(this.mMinimumVersionCode)}));
                printWriter.println(
                        TextUtils.formatSimple(
                                "  Number of relros started: %d",
                                new Object[] {Integer.valueOf(this.mNumRelroCreationsStarted)}));
                printWriter.println(
                        TextUtils.formatSimple(
                                "  Number of relros finished: %d",
                                new Object[] {Integer.valueOf(this.mNumRelroCreationsFinished)}));
                printWriter.println(
                        TextUtils.formatSimple(
                                "  WebView package dirty: %b",
                                new Object[] {Boolean.valueOf(this.mWebViewPackageDirty)}));
                printWriter.println(
                        TextUtils.formatSimple(
                                "  Any WebView package installed: %b",
                                new Object[] {Boolean.valueOf(this.mAnyWebViewInstalled)}));
                try {
                    PackageInfo findPreferredWebViewPackage = findPreferredWebViewPackage();
                    printWriter.println(
                            TextUtils.formatSimple(
                                    "  Preferred WebView package (name, version): (%s, %s)",
                                    new Object[] {
                                        findPreferredWebViewPackage.packageName,
                                        findPreferredWebViewPackage.versionName
                                    }));
                } catch (WebViewPackageMissingException unused) {
                    printWriter.println("  Preferred WebView package: none");
                }
                dumpAllPackageInformationLocked(printWriter);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final void enableMultiProcess(boolean z) {
        throw new IllegalStateException(
                "enableMultiProcess shouldn't be called if update_service_v2 flag is set.");
    }

    public final PackageInfo findPreferredWebViewPackage() {
        WebViewProviderInfo webViewProviderInfo;
        PackageInfo packageInfoForProvider;
        Counter.logIncrement("webview.value_find_preferred_webview_package_counter");
        Context context = this.mContext;
        SystemImpl systemImpl = this.mSystemInterface;
        systemImpl.getClass();
        String string = Settings.Global.getString(context.getContentResolver(), "webview_provider");
        WebViewProviderInfo[] webViewPackages = getWebViewPackages();
        int i = 0;
        while (true) {
            if (i >= webViewPackages.length) {
                webViewProviderInfo = null;
                break;
            }
            if (webViewPackages[i].packageName.equals(string)) {
                webViewProviderInfo = webViewPackages[i];
                break;
            }
            i++;
        }
        if (webViewProviderInfo != null) {
            try {
                PackageInfo packageInfoForProvider2 =
                        systemImpl.getPackageInfoForProvider(webViewProviderInfo);
                if (validityResult(webViewProviderInfo, packageInfoForProvider2) == 0) {
                    Context context2 = this.mContext;
                    systemImpl.getClass();
                    if (isInstalledAndEnabledForAllUsers(
                            UserPackage.getPackageInfosAllUsers(
                                    context2, webViewProviderInfo.packageName, 272630976))) {
                        return packageInfoForProvider2;
                    }
                }
            } catch (PackageManager.NameNotFoundException unused) {
                PinnerService$$ExternalSyntheticOutline0.m(
                        "User chosen WebView package (",
                        string,
                        ") not found",
                        "WebViewUpdateServiceImpl2");
            }
        }
        try {
            packageInfoForProvider = systemImpl.getPackageInfoForProvider(this.mDefaultProvider);
        } catch (PackageManager.NameNotFoundException unused2) {
            ProfileService$1$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Default WebView package ("),
                    this.mDefaultProvider.packageName,
                    ") not found",
                    "WebViewUpdateServiceImpl2");
        }
        if (validityResult(this.mDefaultProvider, packageInfoForProvider) == 0) {
            return packageInfoForProvider;
        }
        Counter.logIncrement("webview.value_default_webview_package_invalid_counter");
        Counter.logIncrement("webview.value_webview_not_usable_for_all_users_counter");
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
        return this.mDefaultProvider;
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final WebViewProviderInfo[] getValidWebViewPackages() {
        SystemImpl systemImpl = this.mSystemInterface;
        WebViewProviderInfo[] webViewProviderInfoArr = systemImpl.mWebViewProviderPackages;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < webViewProviderInfoArr.length; i++) {
            try {
                if (validityResult(
                                webViewProviderInfoArr[i],
                                systemImpl.getPackageInfoForProvider(webViewProviderInfoArr[i]))
                        == 0) {
                    arrayList.add(new ProviderAndPackageInfo(webViewProviderInfoArr[i]));
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        ProviderAndPackageInfo[] providerAndPackageInfoArr =
                (ProviderAndPackageInfo[])
                        arrayList.toArray(new ProviderAndPackageInfo[arrayList.size()]);
        WebViewProviderInfo[] webViewProviderInfoArr2 =
                new WebViewProviderInfo[providerAndPackageInfoArr.length];
        for (int i2 = 0; i2 < providerAndPackageInfoArr.length; i2++) {
            webViewProviderInfoArr2[i2] = providerAndPackageInfoArr[i2].provider;
        }
        return webViewProviderInfoArr2;
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
        updateCurrentWebViewPackage(null);
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final void handleUserRemoved() {
        updateCurrentWebViewPackage(null);
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final boolean isMultiProcessEnabled() {
        throw new IllegalStateException(
                "isMultiProcessEnabled shouldn't be called if update_service_v2 flag is set.");
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final void notifyRelroCreationCompleted() {
        synchronized (this.mLock) {
            this.mNumRelroCreationsFinished++;
            checkIfRelrosDoneLocked();
        }
    }

    public final void onWebViewProviderChanged(PackageInfo packageInfo) {
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
                    if (packageInfo.packageName.equals(this.mDefaultProvider.packageName)) {
                        Counter.logIncrement(
                                "webview.value_on_webview_provider_changed_with_default_package_counter");
                    }
                    checkIfRelrosDoneLocked();
                } else {
                    this.mWebViewPackageDirty = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        AsyncTask.THREAD_POOL_EXECUTOR.execute(
                new Runnable() { // from class:
                                 // com.android.server.webkit.WebViewUpdateServiceImpl2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        WebViewUpdateServiceImpl2 webViewUpdateServiceImpl2 =
                                WebViewUpdateServiceImpl2.this;
                        webViewUpdateServiceImpl2.waitForAndGetProvider();
                        webViewUpdateServiceImpl2.mSystemInterface.getClass();
                        WebViewZygote.getProcess();
                    }
                });
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:9|(5:10|11|(1:13)(1:47)|15|16)|(2:20|(6:22|23|24|(1:26)|28|29))|44|23|24|(0)|28|29) */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0052, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0053, code lost:

       r7 = r3;
       r3 = r2;
       r2 = r7;
    */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004e A[Catch: all -> 0x0022, WebViewPackageMissingException -> 0x0052, TRY_LEAVE, TryCatch #0 {, blocks: (B:11:0x0017, B:13:0x001f, B:16:0x002a, B:18:0x0034, B:20:0x003c, B:24:0x0046, B:26:0x004e, B:28:0x0071, B:29:0x0075, B:42:0x0056), top: B:10:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0078 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
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
            if (r3 >= r1) goto L8d
            r4 = r0[r3]
            java.lang.String r5 = r4.packageName
            boolean r5 = r5.equals(r9)
            if (r5 == 0) goto L89
            java.lang.Object r5 = r8.mLock
            monitor-enter(r5)
            r9 = 0
            android.content.pm.PackageInfo r0 = r8.findPreferredWebViewPackage()     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L25
            android.content.pm.PackageInfo r1 = r8.mCurrentWebViewPackage     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L25
            if (r1 == 0) goto L29
            java.lang.String r1 = r1.packageName     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L25
            goto L2a
        L22:
            r8 = move-exception
            goto L87
        L25:
            r0 = move-exception
            r1 = r9
        L27:
            r3 = r2
            goto L56
        L29:
            r1 = r9
        L2a:
            java.lang.String r3 = r4.packageName     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L43
            java.lang.String r6 = r0.packageName     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L43
            boolean r3 = r3.equals(r6)     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L43
            if (r3 != 0) goto L45
            java.lang.String r3 = r4.packageName     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L43
            boolean r3 = r3.equals(r1)     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L43
            if (r3 != 0) goto L45
            android.content.pm.PackageInfo r3 = r8.mCurrentWebViewPackage     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L43
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
            java.lang.String r4 = r4.packageName     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L52
            boolean r2 = r4.equals(r1)     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L52
            if (r3 == 0) goto L71
            r8.onWebViewProviderChanged(r0)     // Catch: java.lang.Throwable -> L22 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L52
            goto L71
        L52:
            r0 = move-exception
            r7 = r3
            r3 = r2
            r2 = r7
        L56:
            r8.mCurrentWebViewPackage = r9     // Catch: java.lang.Throwable -> L22
            java.lang.String r9 = "WebViewUpdateServiceImpl2"
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
            boolean r9 = r8.shouldTriggerRepairLocked()     // Catch: java.lang.Throwable -> L22
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L22
            if (r3 == 0) goto L81
            if (r2 != 0) goto L81
            if (r1 == 0) goto L81
            com.android.server.webkit.SystemImpl r0 = r8.mSystemInterface
            r0.killPackageDependents(r1)
        L81:
            if (r9 == 0) goto L86
            r8.attemptRepair()
        L86:
            return
        L87:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L22
            throw r8
        L89:
            int r3 = r3 + 1
            goto L7
        L8d:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.webkit.WebViewUpdateServiceImpl2.packageStateChanged(java.lang.String):void");
    }

    @Override // com.android.server.webkit.WebViewUpdateServiceInterface
    public final void prepareWebViewInSystemServer() {
        boolean shouldTriggerRepairLocked;
        try {
            synchronized (this.mLock) {
                try {
                    this.mCurrentWebViewPackage = findPreferredWebViewPackage();
                    shouldTriggerRepairLocked = shouldTriggerRepairLocked();
                    SystemImpl systemImpl = this.mSystemInterface;
                    Context context = this.mContext;
                    systemImpl.getClass();
                    String string =
                            Settings.Global.getString(
                                    context.getContentResolver(), "webview_provider");
                    if (string != null && !string.equals(this.mCurrentWebViewPackage.packageName)) {
                        this.mSystemInterface.updateUserSetting(
                                this.mContext, this.mCurrentWebViewPackage.packageName);
                    }
                    onWebViewProviderChanged(this.mCurrentWebViewPackage);
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (shouldTriggerRepairLocked) {
                attemptRepair();
            }
        } catch (WebViewPackageMissingException e) {
            Slog.e(
                    "WebViewUpdateServiceImpl2",
                    "Could not find valid WebView package to create relro with",
                    e);
        } catch (Throwable th2) {
            Slog.wtf(
                    "WebViewUpdateServiceImpl2",
                    "error preparing webview provider from system server",
                    th2);
        }
    }

    public final boolean shouldTriggerRepairLocked() {
        if (this.mAttemptedToRepairBefore) {
            return false;
        }
        PackageInfo packageInfo = this.mCurrentWebViewPackage;
        if (packageInfo == null) {
            return true;
        }
        if (!packageInfo.packageName.equals(this.mDefaultProvider.packageName)) {
            return false;
        }
        Context context = this.mContext;
        WebViewProviderInfo webViewProviderInfo = this.mDefaultProvider;
        this.mSystemInterface.getClass();
        return !isInstalledAndEnabledForAllUsers(
                UserPackage.getPackageInfosAllUsers(
                        context, webViewProviderInfo.packageName, 272630976));
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002c A[Catch: all -> 0x0011, TRY_ENTER, TryCatch #0 {, blocks: (B:4:0x0005, B:6:0x0009, B:8:0x0013, B:10:0x001a, B:14:0x002c, B:16:0x0031, B:17:0x0035, B:29:0x0048, B:30:0x005b), top: B:3:0x0005, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0031 A[Catch: all -> 0x0011, TryCatch #0 {, blocks: (B:4:0x0005, B:6:0x0009, B:8:0x0013, B:10:0x001a, B:14:0x002c, B:16:0x0031, B:17:0x0035, B:29:0x0048, B:30:0x005b), top: B:3:0x0005, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.pm.PackageInfo updateCurrentWebViewPackage(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = "Couldn't find WebView package to use "
            java.lang.Object r1 = r7.mLock
            monitor-enter(r1)
            android.content.pm.PackageInfo r2 = r7.mCurrentWebViewPackage     // Catch: java.lang.Throwable -> L11
            if (r8 == 0) goto L13
            com.android.server.webkit.SystemImpl r3 = r7.mSystemInterface     // Catch: java.lang.Throwable -> L11
            android.content.Context r4 = r7.mContext     // Catch: java.lang.Throwable -> L11
            r3.updateUserSetting(r4, r8)     // Catch: java.lang.Throwable -> L11
            goto L13
        L11:
            r7 = move-exception
            goto L5d
        L13:
            android.content.pm.PackageInfo r3 = r7.findPreferredWebViewPackage()     // Catch: java.lang.Throwable -> L11 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L27
            r4 = 0
            if (r2 == 0) goto L29
            java.lang.String r5 = r3.packageName     // Catch: java.lang.Throwable -> L11 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L27
            java.lang.String r6 = r2.packageName     // Catch: java.lang.Throwable -> L11 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L27
            boolean r0 = r5.equals(r6)     // Catch: java.lang.Throwable -> L11 com.android.server.webkit.WebViewUpdateServiceImpl2.WebViewPackageMissingException -> L27
            if (r0 != 0) goto L25
            goto L29
        L25:
            r0 = r4
            goto L2a
        L27:
            r8 = move-exception
            goto L47
        L29:
            r0 = 1
        L2a:
            if (r0 == 0) goto L2f
            r7.onWebViewProviderChanged(r3)     // Catch: java.lang.Throwable -> L11
        L2f:
            if (r8 != 0) goto L35
            boolean r4 = r7.shouldTriggerRepairLocked()     // Catch: java.lang.Throwable -> L11
        L35:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L11
            if (r0 == 0) goto L41
            if (r2 == 0) goto L41
            com.android.server.webkit.SystemImpl r8 = r7.mSystemInterface
            java.lang.String r0 = r2.packageName
            r8.killPackageDependents(r0)
        L41:
            if (r4 == 0) goto L46
            r7.attemptRepair()
        L46:
            return r3
        L47:
            r2 = 0
            r7.mCurrentWebViewPackage = r2     // Catch: java.lang.Throwable -> L11
            java.lang.String r7 = "WebViewUpdateServiceImpl2"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L11
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L11
            r3.append(r8)     // Catch: java.lang.Throwable -> L11
            java.lang.String r8 = r3.toString()     // Catch: java.lang.Throwable -> L11
            android.util.Slog.e(r7, r8)     // Catch: java.lang.Throwable -> L11
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L11
            return r2
        L5d:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L11
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.webkit.WebViewUpdateServiceImpl2.updateCurrentWebViewPackage(java.lang.String):android.content.pm.PackageInfo");
    }

    public final int validityResult(
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
            java.lang.String r1 = "WebViewUpdateServiceImpl2"
            android.util.Slog.e(r1, r11)     // Catch: java.lang.Throwable -> L35
            r1 = 64
            android.os.Trace.instant(r1, r11)     // Catch: java.lang.Throwable -> L35
            r7 = 3
        L83:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L35
            if (r5 != 0) goto L8d
            java.lang.String r11 = "WebViewUpdateServiceImpl2"
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
                    + " com.android.server.webkit.WebViewUpdateServiceImpl2.waitForAndGetProvider():android.webkit.WebViewProviderResponse");
    }
}
