package com.android.server.webkit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.Process;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.UserHandle;
import android.webkit.Flags;
import android.webkit.IWebViewUpdateService;
import android.webkit.WebViewProviderInfo;
import android.webkit.WebViewProviderResponse;

import com.android.internal.util.DumpUtils;
import com.android.modules.expresslog.Histogram;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WebViewUpdateService extends SystemService {
    public final WebViewUpdateServiceInterface mImpl;
    public AnonymousClass1 mWebViewUpdatedReceiver;
    public static final Histogram sPrepareWebViewInSystemServerLatency =
            new Histogram(
                    "webview.value_prepare_webview_in_system_server_latency",
                    new Histogram.ScaledRangeOptions(20, 0, 1.0f, 1.5f));
    public static final Histogram sAppWaitingForRelroCompletionDelay =
            new Histogram(
                    "webview.value_app_waiting_for_relro_completion_delay",
                    new Histogram.ScaledRangeOptions(20, 0, 1.0f, 1.4f));

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderService extends IWebViewUpdateService.Stub {
        public BinderService() {}

        public static void grantVisibilityToCaller(int i, String str) {
            PackageManagerInternal packageManagerInternal =
                    (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl =
                    (PackageManagerService.PackageManagerInternalImpl) packageManagerInternal;
            packageManagerInternalImpl.grantImplicitAccess(
                    UserHandle.getUserId(i),
                    null,
                    UserHandle.getAppId(i),
                    packageManagerInternal.getPackageUid(str, 0L, UserHandle.getUserId(i)),
                    true,
                    false);
        }

        public final String changeProviderAndSetting(String str) {
            if (WebViewUpdateService.this
                            .getContext()
                            .checkCallingPermission("android.permission.WRITE_SECURE_SETTINGS")
                    != 0) {
                throw new SecurityException(
                        ActivityManagerService$$ExternalSyntheticOutline0.m(
                                new StringBuilder(
                                        "Permission Denial: changeProviderAndSetting() from pid="),
                                ", uid=",
                                " requires android.permission.WRITE_SECURE_SETTINGS",
                                "WebViewUpdateService"));
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return WebViewUpdateService.this.mImpl.changeProviderAndSetting(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    WebViewUpdateService.this.getContext(), "WebViewUpdateService", printWriter)) {
                WebViewUpdateService.this.mImpl.dumpState(printWriter);
            }
        }

        public final void enableMultiProcess(boolean z) {
            if (Flags.updateServiceV2()) {
                throw new IllegalStateException(
                        "enableMultiProcess shouldn't be called if update_service_v2 flag is set.");
            }
            if (WebViewUpdateService.this
                            .getContext()
                            .checkCallingPermission("android.permission.WRITE_SECURE_SETTINGS")
                    != 0) {
                throw new SecurityException(
                        ActivityManagerService$$ExternalSyntheticOutline0.m(
                                new StringBuilder(
                                        "Permission Denial: enableMultiProcess() from pid="),
                                ", uid=",
                                " requires android.permission.WRITE_SECURE_SETTINGS",
                                "WebViewUpdateService"));
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                WebViewUpdateService.this.mImpl.enableMultiProcess(z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final WebViewProviderInfo[] getAllWebViewPackages() {
            return WebViewUpdateService.this.mImpl.getWebViewPackages();
        }

        public final PackageInfo getCurrentWebViewPackage() {
            PackageInfo currentWebViewPackage =
                    WebViewUpdateService.this.mImpl.getCurrentWebViewPackage();
            if (currentWebViewPackage != null) {
                grantVisibilityToCaller(Binder.getCallingUid(), currentWebViewPackage.packageName);
            }
            return currentWebViewPackage;
        }

        public final String getCurrentWebViewPackageName() {
            PackageInfo currentWebViewPackage = getCurrentWebViewPackage();
            if (currentWebViewPackage == null) {
                return null;
            }
            return currentWebViewPackage.packageName;
        }

        public final WebViewProviderInfo getDefaultWebViewPackage() {
            return WebViewUpdateService.this.mImpl.getDefaultWebViewPackage();
        }

        public final WebViewProviderInfo[] getValidWebViewPackages() {
            return WebViewUpdateService.this.mImpl.getValidWebViewPackages();
        }

        public final boolean isMultiProcessEnabled() {
            if (Flags.updateServiceV2()) {
                throw new IllegalStateException(
                        "isMultiProcessEnabled shouldn't be called if update_service_v2 flag is"
                            + " set.");
            }
            return WebViewUpdateService.this.mImpl.isMultiProcessEnabled();
        }

        public final void notifyRelroCreationCompleted() {
            if (Binder.getCallingUid() == 1037 || Binder.getCallingUid() == 1000) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    WebViewUpdateService.this.mImpl.notifyRelroCreationCompleted();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            if (Flags.updateServiceV2()) {
                new WebViewUpdateServiceShellCommand2(this)
                        .exec(
                                this,
                                fileDescriptor,
                                fileDescriptor2,
                                fileDescriptor3,
                                strArr,
                                shellCallback,
                                resultReceiver);
            } else {
                new WebViewUpdateServiceShellCommand(this)
                        .exec(
                                this,
                                fileDescriptor,
                                fileDescriptor2,
                                fileDescriptor3,
                                strArr,
                                shellCallback,
                                resultReceiver);
            }
        }

        public final WebViewProviderResponse waitForAndGetProvider() {
            if (Binder.getCallingPid() == Process.myPid()) {
                throw new IllegalStateException("Cannot create a WebView from the SystemServer");
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            WebViewProviderResponse waitForAndGetProvider =
                    WebViewUpdateService.this.mImpl.waitForAndGetProvider();
            WebViewUpdateService.sAppWaitingForRelroCompletionDelay.logSample(
                    SystemClock.uptimeMillis() - uptimeMillis);
            PackageInfo packageInfo = waitForAndGetProvider.packageInfo;
            if (packageInfo != null) {
                grantVisibilityToCaller(Binder.getCallingUid(), packageInfo.packageName);
            }
            return waitForAndGetProvider;
        }
    }

    public WebViewUpdateService(Context context) {
        super(context);
        if (Flags.updateServiceV2()) {
            this.mImpl = new WebViewUpdateServiceImpl2(context, SystemImpl.LazyHolder.INSTANCE);
        } else {
            this.mImpl = new WebViewUpdateServiceImpl(context, SystemImpl.LazyHolder.INSTANCE);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.webkit.WebViewUpdateService$1] */
    @Override // com.android.server.SystemService
    public final void onStart() {
        this.mWebViewUpdatedReceiver =
                new BroadcastReceiver() { // from class:
                    // com.android.server.webkit.WebViewUpdateService.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        int intExtra;
                        intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        String action = intent.getAction();
                        action.getClass();
                        switch (action) {
                            case "android.intent.action.USER_REMOVED":
                                WebViewUpdateService.this.mImpl.handleUserRemoved();
                                break;
                            case "android.intent.action.USER_STARTED":
                                WebViewUpdateService.this.mImpl.handleNewUser(intExtra);
                                break;
                            case "android.intent.action.PACKAGE_CHANGED":
                                if (Arrays.asList(
                                                intent.getStringArrayExtra(
                                                        "android.intent.extra.changed_component_name_list"))
                                        .contains(intent.getDataString().substring(8))) {
                                    WebViewUpdateService.this.mImpl.packageStateChanged(
                                            intent.getDataString().substring(8));
                                    break;
                                }
                                break;
                            case "android.intent.action.PACKAGE_REMOVED":
                                if (!intent.getExtras()
                                        .getBoolean("android.intent.extra.REPLACING")) {
                                    WebViewUpdateService.this.mImpl.packageStateChanged(
                                            intent.getDataString().substring(8));
                                    break;
                                }
                                break;
                            case "android.intent.action.PACKAGE_ADDED":
                                WebViewUpdateServiceInterface webViewUpdateServiceInterface =
                                        WebViewUpdateService.this.mImpl;
                                String substring = intent.getDataString().substring(8);
                                intent.getExtras().getBoolean("android.intent.extra.REPLACING");
                                webViewUpdateServiceInterface.packageStateChanged(substring);
                                break;
                        }
                    }
                };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        for (WebViewProviderInfo webViewProviderInfo : this.mImpl.getWebViewPackages()) {
            intentFilter.addDataSchemeSpecificPart(webViewProviderInfo.packageName, 0);
        }
        Context context = getContext();
        BroadcastReceiver broadcastReceiver = this.mWebViewUpdatedReceiver;
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, null, null);
        getContext()
                .registerReceiverAsUser(
                        this.mWebViewUpdatedReceiver,
                        userHandle,
                        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                "android.intent.action.USER_STARTED",
                                "android.intent.action.USER_REMOVED"),
                        null,
                        null);
        publishBinderService("webviewupdate", new BinderService(), true);
    }
}
