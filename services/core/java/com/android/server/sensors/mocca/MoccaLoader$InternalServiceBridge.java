package com.android.server.sensors.mocca;

import android.os.Bundle;
import android.os.ServiceManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.mocca.IInternalServiceBridge;
import com.samsung.android.mocca.IInternalServiceBridgeListener;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class MoccaLoader$InternalServiceBridge extends IInternalServiceBridge.Stub
    implements WindowManagerPolicyConstants.PointerEventListener {
  public final String[] SUPPORTED_TYPES = {"touch"};
  public final Map mListeners = new HashMap();
  public WindowManagerService mWms = null;

  public Bundle getValue(String str) {
    return null;
  }

  public void start() {
    WindowManagerService asInterface =
        IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
    this.mWms = asInterface;
    if (asInterface != null) {
      asInterface.registerPointerEventListener(this, 0);
    }
  }

  public void stop() {
    WindowManagerService windowManagerService = this.mWms;
    if (windowManagerService != null) {
      windowManagerService.unregisterPointerEventListener(this, 0);
    }
  }

  public boolean isAvailable(String str) {
    for (String str2 : this.SUPPORTED_TYPES) {
      if (str2.equals(str)) {
        return true;
      }
    }
    return false;
  }

  public void setListener(
      String str, IInternalServiceBridgeListener iInternalServiceBridgeListener) {
    synchronized (this.mListeners) {
      this.mListeners.put(str, iInternalServiceBridgeListener);
    }
  }

  public void clearAllListeners() {
    synchronized (this.mListeners) {
      this.mListeners.clear();
    }
  }

  public void onPointerEvent(MotionEvent motionEvent) {
    if (motionEvent.getAction() == 0) {
      synchronized (this.mListeners) {
        IInternalServiceBridgeListener iInternalServiceBridgeListener =
            (IInternalServiceBridgeListener) this.mListeners.get("touch");
        if (iInternalServiceBridgeListener != null) {
          try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("event", motionEvent);
            iInternalServiceBridgeListener.onUpdated("touch", bundle);
          } catch (Throwable th) {
            Log.e("MoccaLoader", "PointerEvent : Failed to invoke method", th);
          }
        }
      }
    }
  }
}
