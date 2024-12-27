package android.webkit;

import android.annotation.SystemApi;
import android.app.SystemServiceRegistry;
import android.content.Context;
import android.os.IBinder;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes4.dex */
public class WebViewBootstrapFrameworkInitializer {
    private WebViewBootstrapFrameworkInitializer() {}

    public static void registerServiceWrappers() {
        SystemServiceRegistry.registerForeverStaticService(
                Context.WEBVIEW_UPDATE_SERVICE,
                WebViewUpdateManager.class,
                new SystemServiceRegistry
                        .StaticServiceProducerWithBinder() { // from class:
                                                             // android.webkit.WebViewBootstrapFrameworkInitializer$$ExternalSyntheticLambda0
                    @Override // android.app.SystemServiceRegistry.StaticServiceProducerWithBinder
                    public final Object createService(IBinder iBinder) {
                        return WebViewBootstrapFrameworkInitializer
                                .lambda$registerServiceWrappers$0(iBinder);
                    }
                });
    }

    static /* synthetic */ WebViewUpdateManager lambda$registerServiceWrappers$0(IBinder b) {
        return new WebViewUpdateManager(IWebViewUpdateService.Stub.asInterface(b));
    }
}
