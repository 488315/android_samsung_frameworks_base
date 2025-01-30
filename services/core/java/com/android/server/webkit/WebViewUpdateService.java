package com.android.server.webkit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.Process;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.util.Slog;
import android.webkit.IWebViewUpdateService;
import android.webkit.WebViewProviderInfo;
import android.webkit.WebViewProviderResponse;
import com.android.internal.util.DumpUtils;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class WebViewUpdateService extends SystemService {
    public WebViewUpdateServiceImpl mImpl;
    public BroadcastReceiver mWebViewUpdatedReceiver;

    public WebViewUpdateService(Context context) {
        super(context);
        this.mImpl = new WebViewUpdateServiceImpl(context, SystemImpl.getInstance());
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        this.mWebViewUpdatedReceiver = new BroadcastReceiver() { // from class: com.android.server.webkit.WebViewUpdateService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                int intExtra;
                intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                String action = intent.getAction();
                action.hashCode();
                switch (action) {
                    case "android.intent.action.USER_REMOVED":
                        WebViewUpdateService.this.mImpl.handleUserRemoved(intExtra);
                        break;
                    case "android.intent.action.USER_STARTED":
                        WebViewUpdateService.this.mImpl.handleNewUser(intExtra);
                        break;
                    case "android.intent.action.PACKAGE_CHANGED":
                        if (WebViewUpdateService.entirePackageChanged(intent)) {
                            WebViewUpdateService.this.mImpl.packageStateChanged(WebViewUpdateService.packageNameFromIntent(intent), 0, intExtra);
                            break;
                        }
                        break;
                    case "android.intent.action.PACKAGE_REMOVED":
                        if (!intent.getExtras().getBoolean("android.intent.extra.REPLACING")) {
                            WebViewUpdateService.this.mImpl.packageStateChanged(WebViewUpdateService.packageNameFromIntent(intent), 3, intExtra);
                            break;
                        }
                        break;
                    case "android.intent.action.PACKAGE_ADDED":
                        WebViewUpdateService.this.mImpl.packageStateChanged(WebViewUpdateService.packageNameFromIntent(intent), intent.getExtras().getBoolean("android.intent.extra.REPLACING") ? 2 : 1, intExtra);
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
        getContext().registerReceiverAsUser(this.mWebViewUpdatedReceiver, UserHandle.ALL, intentFilter, null, null);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_STARTED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        getContext().registerReceiverAsUser(this.mWebViewUpdatedReceiver, UserHandle.ALL, intentFilter2, null, null);
        publishBinderService("webviewupdate", new BinderService(), true);
    }

    public void prepareWebViewInSystemServer() {
        this.mImpl.prepareWebViewInSystemServer();
    }

    public static String packageNameFromIntent(Intent intent) {
        return intent.getDataString().substring(8);
    }

    public static boolean entirePackageChanged(Intent intent) {
        return Arrays.asList(intent.getStringArrayExtra("android.intent.extra.changed_component_name_list")).contains(intent.getDataString().substring(8));
    }

    public class BinderService extends IWebViewUpdateService.Stub {
        public BinderService() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            new WebViewUpdateServiceShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public void notifyRelroCreationCompleted() {
            if (Binder.getCallingUid() == 1037 || Binder.getCallingUid() == 1000) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    WebViewUpdateService.this.mImpl.notifyRelroCreationCompleted();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public WebViewProviderResponse waitForAndGetProvider() {
            if (Binder.getCallingPid() == Process.myPid()) {
                throw new IllegalStateException("Cannot create a WebView from the SystemServer");
            }
            WebViewProviderResponse waitForAndGetProvider = WebViewUpdateService.this.mImpl.waitForAndGetProvider();
            PackageInfo packageInfo = waitForAndGetProvider.packageInfo;
            if (packageInfo != null) {
                grantVisibilityToCaller(packageInfo.packageName, Binder.getCallingUid());
            }
            return waitForAndGetProvider;
        }

        public final void grantVisibilityToCaller(String str, int i) {
            PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            packageManagerInternal.grantImplicitAccess(UserHandle.getUserId(i), null, UserHandle.getAppId(i), packageManagerInternal.getPackageUid(str, 0L, UserHandle.getUserId(i)), true);
        }

        public String changeProviderAndSetting(String str) {
            if (WebViewUpdateService.this.getContext().checkCallingPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                String str2 = "Permission Denial: changeProviderAndSetting() from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " requires android.permission.WRITE_SECURE_SETTINGS";
                Slog.w("WebViewUpdateService", str2);
                throw new SecurityException(str2);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return WebViewUpdateService.this.mImpl.changeProviderAndSetting(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public WebViewProviderInfo[] getValidWebViewPackages() {
            return WebViewUpdateService.this.mImpl.getValidWebViewPackages();
        }

        public WebViewProviderInfo[] getAllWebViewPackages() {
            return WebViewUpdateService.this.mImpl.getWebViewPackages();
        }

        public String getCurrentWebViewPackageName() {
            PackageInfo currentWebViewPackage = getCurrentWebViewPackage();
            if (currentWebViewPackage == null) {
                return null;
            }
            return currentWebViewPackage.packageName;
        }

        public PackageInfo getCurrentWebViewPackage() {
            PackageInfo currentWebViewPackage = WebViewUpdateService.this.mImpl.getCurrentWebViewPackage();
            if (currentWebViewPackage != null) {
                grantVisibilityToCaller(currentWebViewPackage.packageName, Binder.getCallingUid());
            }
            return currentWebViewPackage;
        }

        public boolean isMultiProcessEnabled() {
            return WebViewUpdateService.this.mImpl.isMultiProcessEnabled();
        }

        public void enableMultiProcess(boolean z) {
            if (WebViewUpdateService.this.getContext().checkCallingPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                String str = "Permission Denial: enableMultiProcess() from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " requires android.permission.WRITE_SECURE_SETTINGS";
                Slog.w("WebViewUpdateService", str);
                throw new SecurityException(str);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                WebViewUpdateService.this.mImpl.enableMultiProcess(z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(WebViewUpdateService.this.getContext(), "WebViewUpdateService", printWriter)) {
                WebViewUpdateService.this.mImpl.dumpState(printWriter);
            }
        }
    }
}
