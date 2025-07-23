package android.service.vr;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.service.vr.IVrListener;

/* loaded from: classes3.dex */
public abstract class VrListenerService extends Service {
    private static final int MSG_ON_CURRENT_VR_ACTIVITY_CHANGED = 1;
    public static final String SERVICE_INTERFACE = "android.service.vr.VrListenerService";
    private final IVrListener.Stub mBinder = new IVrListener.Stub() { // from class: android.service.vr.VrListenerService.1
        @Override // android.service.vr.IVrListener
        public void focusedActivityChanged(ComponentName componentName, boolean z, int i) {
            VrListenerService.this.mHandler.obtainMessage(1, z ? 1 : 0, i, componentName).sendToTarget();
        }
    };
    private final Handler mHandler = new VrListenerHandler(Looper.getMainLooper());

    public void onCurrentVrActivityChanged(ComponentName componentName) {
    }

    private final class VrListenerHandler extends Handler {
        public VrListenerHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            VrListenerService.this.onCurrentVrActivityChanged((ComponentName) message.obj, message.arg1 == 1, message.arg2);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public void onCurrentVrActivityChanged(ComponentName componentName, boolean z, int i) {
        if (z) {
            componentName = null;
        }
        onCurrentVrActivityChanged(componentName);
    }

    public static final boolean isVrModePackageEnabled(Context context, ComponentName componentName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
        if (activityManager == null) {
            return false;
        }
        return activityManager.isVrModePackageEnabled(componentName);
    }
}
