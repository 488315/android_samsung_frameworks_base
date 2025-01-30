package com.samsung.android.app.scrollcapture.lib;

import android.content.Context;
import android.util.Log;
import com.android.systemui.screenshot.SaveImageInBackgroundTask;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RemoteScrollCaptureInterface {
    public ServiceConnectionC45501 mConnection;
    public SaveImageInBackgroundTask.C23351 mConnectionListener;
    public Context mContext;
    public IScrollCaptureService mService;

    public final void disconnect() {
        Log.d("[ScrCap]_RemoteScrollCaptureInterface", "disconnect");
        ServiceConnectionC45501 serviceConnectionC45501 = this.mConnection;
        if (serviceConnectionC45501 != null) {
            try {
                this.mContext.unbindService(serviceConnectionC45501);
            } catch (Exception e) {
                Log.e("[ScrCap]_RemoteScrollCaptureInterface", "disconnect : e=" + e);
                e.printStackTrace();
            }
        } else {
            Log.e("[ScrCap]_RemoteScrollCaptureInterface", "disconnect : No service connection");
        }
        this.mContext = null;
        this.mConnection = null;
        this.mService = null;
        this.mConnectionListener = null;
    }
}
