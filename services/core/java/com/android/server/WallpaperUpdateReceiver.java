package com.android.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class WallpaperUpdateReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null || !"android.intent.action.DEVICE_CUSTOMIZATION_READY".equals(intent.getAction())) {
            return;
        }
        AsyncTask.execute(new Runnable() { // from class: com.android.server.WallpaperUpdateReceiver$$ExternalSyntheticLambda1
            /* JADX WARN: Code restructure failed: missing block: B:18:0x0036, code lost:
            
                if (r2.getComponent().equals(android.app.WallpaperManager.getCmfDefaultWallpaperComponent(r0)) == false) goto L11;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    r3 = this;
                    com.android.server.WallpaperUpdateReceiver r3 = com.android.server.WallpaperUpdateReceiver.this
                    int r0 = com.android.server.WallpaperUpdateReceiver.$r8$clinit
                    r3.getClass()
                    java.lang.String r3 = "WallpaperUpdateReceiver"
                    android.app.ActivityThread r0 = android.app.ActivityThread.currentActivityThread()     // Catch: java.lang.Exception -> L3e
                    android.app.ContextImpl r0 = r0.getSystemUiContext()     // Catch: java.lang.Exception -> L3e
                    android.app.WallpaperManager r1 = android.app.WallpaperManager.getInstance(r0)     // Catch: java.lang.Exception -> L3e
                    android.app.WallpaperInfo r2 = r1.getWallpaperInfo()     // Catch: java.lang.Exception -> L3e
                    if (r2 != 0) goto L2a
                    r0 = 1
                    android.os.ParcelFileDescriptor r0 = r1.getWallpaperFile(r0)     // Catch: java.lang.Exception -> L3e
                    r2 = 2
                    android.os.ParcelFileDescriptor r2 = r1.getWallpaperFile(r2)     // Catch: java.lang.Exception -> L3e
                    if (r0 != 0) goto L38
                    if (r2 == 0) goto L40
                    goto L38
                L2a:
                    android.content.ComponentName r2 = r2.getComponent()     // Catch: java.lang.Exception -> L3e
                    android.content.ComponentName r0 = android.app.WallpaperManager.getCmfDefaultWallpaperComponent(r0)     // Catch: java.lang.Exception -> L3e
                    boolean r0 = r2.equals(r0)     // Catch: java.lang.Exception -> L3e
                    if (r0 != 0) goto L40
                L38:
                    java.lang.String r0 = "User has set wallpaper, skip to resetting"
                    android.util.Slog.i(r3, r0)     // Catch: java.lang.Exception -> L3e
                    goto L4f
                L3e:
                    r0 = move-exception
                    goto L4a
                L40:
                    android.app.WallpaperInfo r0 = r1.getWallpaperInfo()     // Catch: java.lang.Exception -> L3e
                    if (r0 != 0) goto L4f
                    r1.clearWallpaper()     // Catch: java.lang.Exception -> L3e
                    goto L4f
                L4a:
                    java.lang.String r1 = "Failed to customize system wallpaper."
                    com.android.server.WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(r0, r1, r3)
                L4f:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.WallpaperUpdateReceiver$$ExternalSyntheticLambda1.run():void");
            }
        });
    }
}
