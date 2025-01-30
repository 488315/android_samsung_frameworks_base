package android.service.p012vr;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.Message;

/* loaded from: classes3.dex */
public abstract class VrListenerService extends Service {
  private static final int MSG_ON_CURRENT_VR_ACTIVITY_CHANGED = 1;
  public static final String SERVICE_INTERFACE = "android.service.vr.VrListenerService";
  private final IVrListener.Stub mBinder =
      new IVrListener.Stub() { // from class: android.service.vr.VrListenerService.1
        @Override // android.service.p012vr.IVrListener
        public void focusedActivityChanged(ComponentName componentName, boolean z, int i) {
          VrListenerService.this
              .mHandler
              .obtainMessage(1, z ? 1 : 0, i, componentName)
              .sendToTarget();
        }
      };
  private final Handler mHandler = new VrListenerHandler(Looper.getMainLooper());

  private final class VrListenerHandler extends Handler {
    public VrListenerHandler(Looper looper) {
      super(looper);
    }

    @Override // android.p009os.Handler
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case 1:
          VrListenerService.this.onCurrentVrActivityChanged(
              (ComponentName) msg.obj, msg.arg1 == 1, msg.arg2);
          break;
      }
    }
  }

  @Override // android.app.Service
  public IBinder onBind(Intent intent) {
    return this.mBinder;
  }

  public void onCurrentVrActivityChanged(ComponentName component) {}

  public void onCurrentVrActivityChanged(ComponentName component, boolean running2dInVr, int pid) {
    onCurrentVrActivityChanged(running2dInVr ? null : component);
  }

  public static final boolean isVrModePackageEnabled(
      Context context, ComponentName requestedComponent) {
    ActivityManager am = (ActivityManager) context.getSystemService(ActivityManager.class);
    if (am == null) {
      return false;
    }
    return am.isVrModePackageEnabled(requestedComponent);
  }
}
