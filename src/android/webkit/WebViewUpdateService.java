package android.webkit;

import android.annotation.SystemApi;
import android.os.RemoteException;

@SystemApi
@Deprecated
/* loaded from: classes4.dex */
public final class WebViewUpdateService {
    private WebViewUpdateService() {
    }

    public static WebViewProviderInfo[] getAllWebViewPackages() {
        if (Flags.updateServiceIpcWrapper()) {
            if (WebViewFactory.isWebViewSupported()) {
                return WebViewUpdateManager.getInstance().getAllWebViewPackages();
            }
            return new WebViewProviderInfo[0];
        }
        IWebViewUpdateService updateService = getUpdateService();
        if (updateService == null) {
            return new WebViewProviderInfo[0];
        }
        try {
            return updateService.getAllWebViewPackages();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static WebViewProviderInfo[] getValidWebViewPackages() {
        if (Flags.updateServiceIpcWrapper()) {
            if (WebViewFactory.isWebViewSupported()) {
                return WebViewUpdateManager.getInstance().getValidWebViewPackages();
            }
            return new WebViewProviderInfo[0];
        }
        IWebViewUpdateService updateService = getUpdateService();
        if (updateService == null) {
            return new WebViewProviderInfo[0];
        }
        try {
            return updateService.getValidWebViewPackages();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String getCurrentWebViewPackageName() {
        if (Flags.updateServiceIpcWrapper()) {
            if (WebViewFactory.isWebViewSupported()) {
                return WebViewUpdateManager.getInstance().getCurrentWebViewPackageName();
            }
            return null;
        }
        IWebViewUpdateService updateService = getUpdateService();
        if (updateService == null) {
            return null;
        }
        try {
            return updateService.getCurrentWebViewPackageName();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static IWebViewUpdateService getUpdateService() {
        return WebViewFactory.getUpdateService();
    }
}
