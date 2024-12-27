package com.android.internal.os;

import android.app.ApplicationLoaders;
import android.app.LoadedApk;
import android.content.pm.ApplicationInfo;
import android.net.LocalSocket;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebViewFactory;
import android.webkit.WebViewFactoryProvider;
import android.webkit.WebViewLibraryLoader;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

class WebViewZygoteInit {
    public static final String TAG = "WebViewZygoteInit";

    WebViewZygoteInit() {}

    private static class WebViewZygoteServer extends ZygoteServer {
        private WebViewZygoteServer() {}

        @Override // com.android.internal.os.ZygoteServer
        protected ZygoteConnection createNewConnection(LocalSocket socket, String abiList)
                throws IOException {
            return new WebViewZygoteConnection(socket, abiList);
        }
    }

    private static class WebViewZygoteConnection extends ZygoteConnection {
        WebViewZygoteConnection(LocalSocket socket, String abiList) throws IOException {
            super(socket, abiList);
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected void preload() {}

        @Override // com.android.internal.os.ZygoteConnection
        protected boolean isPreloadComplete() {
            return true;
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected boolean canPreloadApp() {
            return true;
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected void handlePreloadApp(ApplicationInfo appInfo) {
            Log.i(
                    WebViewZygoteInit.TAG,
                    "Beginning application preload for " + appInfo.packageName);
            LoadedApk loadedApk = new LoadedApk(null, appInfo, null, null, false, true, false);
            ClassLoader loader = loadedApk.getClassLoader();
            doPreload(loader, WebViewFactory.getWebViewLibrary(appInfo));
            Zygote.allowAppFilesAcrossFork(appInfo);
            Log.i(WebViewZygoteInit.TAG, "Application preload done");
        }

        @Override // com.android.internal.os.ZygoteConnection
        protected void handlePreloadPackage(
                String packagePath, String libsPath, String libFileName, String cacheKey) {
            Log.i(WebViewZygoteInit.TAG, "Beginning package preload");
            ClassLoader loader =
                    ApplicationLoaders.getDefault()
                            .createAndCacheWebViewClassLoader(packagePath, libsPath, cacheKey);
            String[] packageList = TextUtils.split(packagePath, File.pathSeparator);
            for (String packageEntry : packageList) {
                Zygote.nativeAllowFileAcrossFork(packageEntry);
            }
            doPreload(loader, libFileName);
            Log.i(WebViewZygoteInit.TAG, "Package preload done");
        }

        private void doPreload(ClassLoader loader, String libFileName) {
            WebViewLibraryLoader.loadNativeLibrary(loader, libFileName);
            boolean preloadSucceeded = false;
            int i = 1;
            try {
                Class<WebViewFactoryProvider> providerClass =
                        WebViewFactory.getWebViewProviderClass(loader);
                Method preloadInZygote = providerClass.getMethod("preloadInZygote", new Class[0]);
                preloadInZygote.setAccessible(true);
                if (preloadInZygote.getReturnType() != Boolean.TYPE) {
                    Log.e(
                            WebViewZygoteInit.TAG,
                            "Unexpected return type: preloadInZygote must return boolean");
                } else {
                    preloadSucceeded =
                            ((Boolean)
                                            providerClass
                                                    .getMethod("preloadInZygote", new Class[0])
                                                    .invoke(null, new Object[0]))
                                    .booleanValue();
                    if (!preloadSucceeded) {
                        Log.e(WebViewZygoteInit.TAG, "preloadInZygote returned false");
                    }
                }
            } catch (ReflectiveOperationException e) {
                Log.e(WebViewZygoteInit.TAG, "Exception while preloading package", e);
            }
            try {
                DataOutputStream socketOut = getSocketOutputStream();
                if (!preloadSucceeded) {
                    i = 0;
                }
                socketOut.writeInt(i);
            } catch (IOException ioe) {
                throw new IllegalStateException("Error writing to command socket", ioe);
            }
        }
    }

    public static void main(String[] argv) {
        Log.i(TAG, "Starting WebViewZygoteInit");
        WebViewZygoteServer server = new WebViewZygoteServer();
        ChildZygoteInit.runZygoteServer(server, argv);
    }
}
