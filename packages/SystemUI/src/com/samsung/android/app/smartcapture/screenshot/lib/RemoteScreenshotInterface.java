package com.samsung.android.app.smartcapture.screenshot.lib;

import android.content.Context;
import android.util.Log;
import com.android.systemui.screenshot.ScreenshotController$startCaptureAppRemoteServiceConnection$1;

/* loaded from: classes4.dex */
public class RemoteScreenshotInterface {
    public AnonymousClass1 mConnection;
    public ScreenshotController$startCaptureAppRemoteServiceConnection$1 mConnectionListener;
    public Context mContext;
    public IScreenshotService mService;

    public final void disconnect() {
        Log.d("[ScrCap]_RemoteScreenshotInterface", "disconnect");
        AnonymousClass1 anonymousClass1 = this.mConnection;
        if (anonymousClass1 != null) {
            try {
                this.mContext.unbindService(anonymousClass1);
            } catch (Exception e) {
                Log.e("[ScrCap]_RemoteScreenshotInterface", "disconnect : e=" + e);
                Log.e("[ScrCap]_RemoteScreenshotInterface", e.toString());
            }
        } else {
            Log.e("[ScrCap]_RemoteScreenshotInterface", "disconnect : No service connection");
        }
        this.mContext = null;
        this.mConnection = null;
        this.mService = null;
        this.mConnectionListener = null;
    }
}
