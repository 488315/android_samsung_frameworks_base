package com.android.internal.os;

import android.app.LoadedApk;
import android.content.pm.ApplicationInfo;
import android.net.LocalSocket;
import android.util.Log;
import android.webkit.WebViewFactory;
import android.webkit.WebViewFactoryProvider;
import android.webkit.WebViewLibraryLoader;
import java.io.IOException;
import java.lang.reflect.Method;

/* loaded from: classes5.dex */
class WebViewZygoteInit {
    public static final String TAG = "WebViewZygoteInit";

    WebViewZygoteInit() {
    }

    private static class WebViewZygoteServer extends ZygoteServer {
        private WebViewZygoteServer() {
        }

        @Override // com.android.internal.os.ZygoteServer
        protected ZygoteConnection createNewConnection(LocalSocket localSocket, String str) throws IOException {
            return new WebViewZygoteConnection(localSocket, str);
        }
    }

    private static class WebViewZygoteConnection extends ZygoteConnection {
        @Override // com.android.internal.os.ZygoteConnection
        protected boolean canPreloadApp() {
            return true;
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected boolean isPreloadComplete() {
            return true;
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected void preload() {
        }

        WebViewZygoteConnection(LocalSocket localSocket, String str) throws IOException {
            super(localSocket, str);
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected void handlePreloadApp(ApplicationInfo applicationInfo) {
            Log.i(WebViewZygoteInit.TAG, "Beginning application preload for " + applicationInfo.packageName);
            doPreload(new LoadedApk(null, applicationInfo, null, null, false, true, false).getClassLoader(), WebViewFactory.getWebViewLibrary(applicationInfo));
            Zygote.allowAppFilesAcrossFork(applicationInfo);
            Log.i(WebViewZygoteInit.TAG, "Application preload done");
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void doPreload(ClassLoader classLoader, String str) {
            WebViewLibraryLoader.loadNativeLibrary(classLoader, str);
            int i = 0;
            i = 0;
            try {
                Class<WebViewFactoryProvider> webViewProviderClass = WebViewFactory.getWebViewProviderClass(classLoader);
                Class[] clsArr = new Class[0];
                Method method = webViewProviderClass.getMethod("preloadInZygote", null);
                method.setAccessible(true);
                if (method.getReturnType() != Boolean.TYPE) {
                    Log.e(WebViewZygoteInit.TAG, "Unexpected return type: preloadInZygote must return boolean");
                } else {
                    Class[] clsArr2 = new Class[0];
                    boolean booleanValue = ((Boolean) webViewProviderClass.getMethod("preloadInZygote", null).invoke(null, null)).booleanValue();
                    i = booleanValue;
                    if (booleanValue == 0) {
                        Log.e(WebViewZygoteInit.TAG, "preloadInZygote returned false");
                        i = booleanValue;
                    }
                }
            } catch (ReflectiveOperationException e) {
                Log.e(WebViewZygoteInit.TAG, "Exception while preloading package", e);
            }
            try {
                getSocketOutputStream().writeInt(i);
            } catch (IOException e2) {
                throw new IllegalStateException("Error writing to command socket", e2);
            }
        }
    }

    public static void main(String[] strArr) {
        Log.i(TAG, "Starting WebViewZygoteInit");
        ChildZygoteInit.runZygoteServer(new WebViewZygoteServer(), strArr);
    }
}
