package com.android.server.sepunion.eventdelegator;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.android.internal.content.PackageMonitor;
import com.android.server.sepunion.SemDeviceInfoManagerService;
import com.samsung.android.sepunion.Log;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class EventProcessHandler extends Handler {
  public static final String TAG = SemDeviceInfoManagerService.TAG;
  public final Context mContext;
  public final PackageMonitor mPackagesMonitor;
  public final PhoneStateListener mPhoneStateListener;
  public final SemDeviceInfoManagerService mService;

  public EventProcessHandler(
      Context context,
      SemDeviceInfoManagerService semDeviceInfoManagerService,
      HandlerThread handlerThread) {
    super(handlerThread.getLooper());
    this.mPackagesMonitor =
        new PackageMonitor() { // from class:
                               // com.android.server.sepunion.eventdelegator.EventProcessHandler.2
          public void onPackageModified(String str) {
            UnionEventListenerItem unionEventListenerItem;
            Log.d(EventProcessHandler.TAG, "onPackageModified(): " + str);
            synchronized (EventProcessHandler.this.mService.mLock) {
              String str2 = "monitor_package_state;" + str;
              ListenerContainer systemListenerContainer =
                  EventProcessHandler.this.mService.getSystemListenerContainer();
              if (systemListenerContainer.mCustomEventMap.containsKey(str2)
                  && (unionEventListenerItem =
                          (UnionEventListenerItem)
                              systemListenerContainer.mCustomEventMap.get(str2))
                      != null) {
                EventProcessHandler.this.mService.reportPackageStateChanged(
                    unionEventListenerItem, str, "package_modified");
              }
            }
          }

          public void onPackageAdded(String str, int i) {
            UnionEventListenerItem unionEventListenerItem;
            Log.d(EventProcessHandler.TAG, "onPackageAdded(): " + str);
            synchronized (EventProcessHandler.this.mService.mLock) {
              String str2 = "monitor_package_state;" + str;
              ListenerContainer systemListenerContainer =
                  EventProcessHandler.this.mService.getSystemListenerContainer();
              if (systemListenerContainer.mCustomEventMap.containsKey(str2)
                  && (unionEventListenerItem =
                          (UnionEventListenerItem)
                              systemListenerContainer.mCustomEventMap.get(str2))
                      != null) {
                EventProcessHandler.this.mService.reportPackageStateChanged(
                    unionEventListenerItem, str, "package_added");
              }
            }
          }

          public void onPackageRemoved(String str, int i) {
            UnionEventListenerItem unionEventListenerItem;
            Log.d(EventProcessHandler.TAG, "onPackageRemoved() :" + str);
            synchronized (EventProcessHandler.this.mService.mLock) {
              String str2 = "monitor_package_state;" + str;
              ListenerContainer systemListenerContainer =
                  EventProcessHandler.this.mService.getSystemListenerContainer();
              if (systemListenerContainer.mCustomEventMap.containsKey(str2)
                  && (unionEventListenerItem =
                          (UnionEventListenerItem)
                              systemListenerContainer.mCustomEventMap.get(str2))
                      != null) {
                EventProcessHandler.this.mService.reportPackageStateChanged(
                    unionEventListenerItem, str, "package_removed");
              }
            }
          }
        };
    this.mContext = context;
    this.mService = semDeviceInfoManagerService;
    this.mPhoneStateListener =
        new PhoneStateListener(
            handlerThread
                .getLooper()) { // from class:
                                // com.android.server.sepunion.eventdelegator.EventProcessHandler.1
          @Override // android.telephony.PhoneStateListener
          public void onCallStateChanged(int i, String str) {
            Log.d(EventProcessHandler.TAG, "onCallStateChanged() state = " + i);
            synchronized (EventProcessHandler.this.mService.mLock) {
              ListenerContainer systemListenerContainer =
                  EventProcessHandler.this.mService.getSystemListenerContainer();
              if (systemListenerContainer.mCustomEventMap.containsKey("monitor_call_state")) {
                EventProcessHandler.this.mService.reportCallStateChanged(
                    (UnionEventListenerItem)
                        systemListenerContainer.mCustomEventMap.get("monitor_call_state"),
                    i,
                    str);
              }
            }
          }
        };
  }

  @Override // android.os.Handler
  public void handleMessage(Message message) {
    String str = TAG;
    Log.d(str, "EventProcessHandler:handleMessage() msg.what = " + message.what);
    int i = message.what;
    if (i == 1) {
      ((TelephonyManager) this.mContext.getSystemService("phone"))
          .listen(this.mPhoneStateListener, 32);
      return;
    }
    if (i == 2) {
      ((TelephonyManager) this.mContext.getSystemService("phone"))
          .listen(this.mPhoneStateListener, 0);
      return;
    }
    if (i == 3) {
      try {
        this.mPackagesMonitor.register(this.mContext, (Looper) null, UserHandle.ALL, true);
        return;
      } catch (Exception e) {
        Log.e(TAG, "Error on registering package monitor! " + e);
        return;
      }
    }
    if (i == 4) {
      synchronized (this.mService.mLock) {
        Iterator it =
            this.mService.getSystemListenerContainer().mCustomEventMap.keySet().iterator();
        while (it.hasNext()) {
          if (((String) it.next()).startsWith("monitor_package_state")) {
            Log.d(TAG, "PackageMonitor is still in use! DO NOT UNREGISTER!");
            return;
          }
        }
        this.mPackagesMonitor.unregister();
        return;
      }
    }
    Log.e(str, "msg.what has invalid value. msg.what = " + message.what);
  }

  public void notifyToHandler(int i, Bundle bundle) {
    Message obtainMessage = obtainMessage(i);
    obtainMessage.setData(bundle);
    sendMessage(obtainMessage);
  }
}
