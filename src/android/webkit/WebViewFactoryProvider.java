package android.webkit;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import java.util.List;

@SystemApi
/* loaded from: classes4.dex */
public interface WebViewFactoryProvider {
    public static final int MINIMUM_SUPPORTED_TARGET_SDK = 33;
    public static final long MINIMUM_SUPPORTED_VERSION_CODE = 661308800;

    public interface Statics {
        void clearClientCertPreferences(Runnable runnable);

        void enableSlowWholeDocumentDraw();

        String findAddress(String str);

        void freeMemoryForTests();

        String getDefaultUserAgent(Context context);

        Uri getSafeBrowsingPrivacyPolicyUrl();

        void initSafeBrowsing(Context context, ValueCallback<Boolean> valueCallback);

        Uri[] parseFileChooserResult(int i, Intent intent);

        void setSafeBrowsingWhitelist(List<String> list, ValueCallback<Boolean> valueCallback);

        void setWebContentsDebuggingEnabled(boolean z);
    }

    WebViewProvider createWebView(WebView webView, WebView.PrivateAccess privateAccess);

    CookieManager getCookieManager();

    GeolocationPermissions getGeolocationPermissions();

    ServiceWorkerController getServiceWorkerController();

    Statics getStatics();

    TokenBindingService getTokenBindingService();

    TracingController getTracingController();

    WebIconDatabase getWebIconDatabase();

    WebStorage getWebStorage();

    ClassLoader getWebViewClassLoader();

    WebViewDatabase getWebViewDatabase(Context context);

    static boolean isCompatibleImplementationPackage(PackageInfo packageInfo) {
        return Flags.useBEntryPoint() ? ((long) packageInfo.versionCode) >= MINIMUM_SUPPORTED_VERSION_CODE : packageInfo.applicationInfo.targetSdkVersion >= 33;
    }

    static String describeCompatibleImplementationPackage() {
        if (Flags.useBEntryPoint()) {
            return TextUtils.formatSimple("Minimum versionCode for OS support: %d", Long.valueOf(MINIMUM_SUPPORTED_VERSION_CODE));
        }
        return TextUtils.formatSimple("Minimum targetSdkVersion: %d", 33);
    }

    static String getWebViewFactoryClassName() {
        if (Flags.useBEntryPoint()) {
            return "com.android.webview.chromium.WebViewChromiumFactoryProviderForB";
        }
        return "com.android.webview.chromium.WebViewChromiumFactoryProviderForT";
    }

    default PacProcessor getPacProcessor() {
        throw new UnsupportedOperationException("Not implemented");
    }

    default PacProcessor createPacProcessor() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
