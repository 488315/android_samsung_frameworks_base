package android.webkit;

import android.annotation.SystemApi;
import android.app.SystemServiceRegistry;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.RemoteException;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes4.dex */
public final class WebViewUpdateManager {
    private final IWebViewUpdateService mService;

    public WebViewUpdateManager(IWebViewUpdateService service) {
        this.mService = service;
    }

    public static WebViewUpdateManager getInstance() {
        return (WebViewUpdateManager) SystemServiceRegistry.getSystemServiceWithNoContext(Context.WEBVIEW_UPDATE_SERVICE);
    }

    public WebViewProviderResponse waitForAndGetProvider() {
        try {
            return this.mService.waitForAndGetProvider();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public PackageInfo getCurrentWebViewPackage() {
        try {
            return this.mService.getCurrentWebViewPackage();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public WebViewProviderInfo[] getAllWebViewPackages() {
        try {
            return this.mService.getAllWebViewPackages();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public WebViewProviderInfo[] getValidWebViewPackages() {
        try {
            return this.mService.getValidWebViewPackages();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getCurrentWebViewPackageName() {
        try {
            return this.mService.getCurrentWebViewPackageName();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String changeProviderAndSetting(String newProvider) {
        try {
            return this.mService.changeProviderAndSetting(newProvider);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void notifyRelroCreationCompleted() {
        try {
            this.mService.notifyRelroCreationCompleted();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public WebViewProviderInfo getDefaultWebViewPackage() {
        try {
            return this.mService.getDefaultWebViewPackage();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
