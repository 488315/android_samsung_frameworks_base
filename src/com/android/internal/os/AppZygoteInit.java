package com.android.internal.os;

import android.app.LoadedApk;
import android.app.ZygotePreload;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.net.LocalSocket;
import android.system.ErrnoException;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes5.dex */
class AppZygoteInit {
    public static final String TAG = "AppZygoteInit";
    private static ZygoteServer sServer;

    AppZygoteInit() {
    }

    private static class AppZygoteServer extends ZygoteServer {
        private AppZygoteServer() {
        }

        @Override // com.android.internal.os.ZygoteServer
        protected ZygoteConnection createNewConnection(LocalSocket localSocket, String str) throws IOException {
            return new AppZygoteConnection(localSocket, str);
        }
    }

    private static class AppZygoteConnection extends ZygoteConnection {
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

        AppZygoteConnection(LocalSocket localSocket, String str) throws IOException {
            super(localSocket, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r10v0, types: [com.android.internal.os.AppZygoteInit$AppZygoteConnection] */
        /* JADX WARN: Type inference failed for: r10v1 */
        /* JADX WARN: Type inference failed for: r10v2, types: [com.android.internal.os.AppZygoteInit$AppZygoteConnection] */
        /* JADX WARN: Type inference failed for: r10v4, types: [java.io.DataOutputStream] */
        /* JADX WARN: Type inference failed for: r10v5 */
        /* JADX WARN: Type inference failed for: r10v6 */
        /* JADX WARN: Type inference failed for: r10v7 */
        /* JADX WARN: Type inference failed for: r10v8 */
        /* JADX WARN: Type inference failed for: r4v3, types: [java.lang.String] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:10:0x0080 -> B:22:0x0099). Please report as a decompilation issue!!! */
        @Override // com.android.internal.os.ZygoteConnection
        protected void handlePreloadApp(ApplicationInfo applicationInfo) throws IOException, ClassNotFoundException {
            ?? r10;
            Log.i(AppZygoteInit.TAG, "Beginning application preload for " + applicationInfo.packageName);
            ApplicationInfo applicationInfo2 = applicationInfo;
            ClassLoader classLoader = new LoadedApk(null, applicationInfo2, null, null, false, true, false).getClassLoader();
            Zygote.allowAppFilesAcrossFork(applicationInfo2);
            int i = 1;
            if (applicationInfo2.zygotePreloadName != null) {
                try {
                    ComponentName componentNameCreateRelative = ComponentName.createRelative(applicationInfo2.packageName, applicationInfo2.zygotePreloadName);
                    Class<?> cls = Class.forName(componentNameCreateRelative.getClassName(), true, classLoader);
                    if (!ZygotePreload.class.isAssignableFrom(cls)) {
                        Log.e(AppZygoteInit.TAG, componentNameCreateRelative.getClassName() + " does not implement " + ZygotePreload.class.getName());
                        applicationInfo2 = applicationInfo2;
                        this = this;
                    } else {
                        Class[] clsArr = new Class[0];
                        ZygotePreload zygotePreload = (ZygotePreload) cls.getConstructor(null).newInstance(null);
                        Zygote.markOpenedFilesBeforePreload();
                        zygotePreload.doPreload(applicationInfo2);
                        Zygote.allowFilesOpenedByPreload();
                        applicationInfo2 = applicationInfo2;
                        this = this;
                    }
                } catch (ReflectiveOperationException e) {
                    ?? r4 = "AppZygote application preload failed for " + applicationInfo2.zygotePreloadName;
                    Log.e(AppZygoteInit.TAG, r4, e);
                    applicationInfo2 = r4;
                    r10 = this;
                }
            } else {
                Log.i(AppZygoteInit.TAG, "No zygotePreloadName attribute specified.");
                applicationInfo2 = applicationInfo2;
                this = this;
            }
            try {
                this = r10.getSocketOutputStream();
                if (classLoader == null) {
                    i = 0;
                }
                this.writeInt(i);
                Log.i(AppZygoteInit.TAG, "Application preload done");
            } catch (IOException e2) {
                throw new IllegalStateException("Error writing to command socket", e2);
            }
        }
    }

    public static void main(String[] strArr) throws NumberFormatException, ErrnoException {
        ChildZygoteInit.runZygoteServer(new AppZygoteServer(), strArr);
    }
}
