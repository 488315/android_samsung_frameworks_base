package android.net;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.telephony.SubscriptionManager;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public class LowLatencyModeManager {
  public static final int LOW = 2;
  private static final int MSG_GET_LATENCY_DONE = 2;
  private static final int MSG_SET_LATENCY_DONE = 1;
  public static final int NORMAL = 1;
  private static final int SIMSLOT1 = 0;
  private static final int SIMSLOT2 = 1;
  public static final int SUPER_LOW = 4;
  private static final String TAG = "LowLatencyModeManager";
  public static final int VERY_LOW = 3;
  private LatencyCallback mCallback;
  private final Context mContext;
  private int mDlLevel;
  private boolean mExtension;
  private boolean mPrioDefault;
  private int mUlLevel;
  private Messenger mServiceMessenger = null;
  private Messenger mServiceMessenger2 = null;
  private Handler mReceiverHandler =
      new Handler() { // from class: android.net.LowLatencyModeManager.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
          if (msg == null) {}
          int error = msg.getData().getInt("error");
          switch (msg.what) {
            case 1:
              LowLatencyModeManager.this.log("set latency done, error:" + error);
              LowLatencyModeManager.this.unbindRilService(msg.getData().getInt("slotId"));
              break;
            case 2:
              LowLatencyModeManager.this.log("get latency done, error:" + error);
              if (error == 0) {
                byte[] buf = msg.getData().getByteArray("response");
                if (buf == null || buf.length != 4) {
                  LowLatencyModeManager.this.log("get latency wrong result format");
                  break;
                } else {
                  LowLatencyModeManager.this.log(
                      "get latency settings from modem, ul:"
                          + ((int) buf[0])
                          + ",dl:"
                          + ((int) buf[1])
                          + ",prio:"
                          + ((int) buf[2])
                          + ",ets:"
                          + ((int) buf[3]));
                  LatencyResult result =
                      new LatencyResult(buf[0], buf[1], buf[2] == 1, buf[3] == 1);
                  if (LowLatencyModeManager.this.mCallback != null) {
                    LowLatencyModeManager.this.mCallback.onGetLatencyDone(result);
                    LowLatencyModeManager.this.mCallback = null;
                  }
                }
              }
              LowLatencyModeManager.this.unbindRilService(msg.getData().getInt("slotId"));
              break;
          }
        }
      };
  private Messenger mSvcModeMessenger = new Messenger(this.mReceiverHandler);
  private ServiceConnection mSecPhoneServiceConnection =
      new ServiceConnection() { // from class: android.net.LowLatencyModeManager.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
          LowLatencyModeManager.this.log(
              "onServiceConnected(), classname:" + className.getClassName());
          LowLatencyModeManager.this.mServiceMessenger = new Messenger(service);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
          LowLatencyModeManager.this.log(
              "onServiceDisconnected(), classname:" + className.getClassName());
          LowLatencyModeManager.this.mServiceMessenger = null;
        }
      };
  private ServiceConnection mSecPhoneServiceConnection2 =
      new ServiceConnection() { // from class: android.net.LowLatencyModeManager.3
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
          LowLatencyModeManager.this.log(
              "onServiceConnected(), classname:" + className.getClassName());
          LowLatencyModeManager.this.mServiceMessenger2 = new Messenger(service);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
          LowLatencyModeManager.this.log(
              "onServiceDisconnected(), classname:" + className.getClassName());
          LowLatencyModeManager.this.mServiceMessenger2 = null;
        }
      };

  public static class LatencyResult {
    private int mDlLevel;
    private boolean mExtension;
    private boolean mPrioDefault;
    private int mUlLevel;

    public LatencyResult(int ulevel, int dlevel, boolean prio, boolean ext) {
      this.mUlLevel = ulevel;
      this.mDlLevel = dlevel;
      this.mPrioDefault = prio;
      this.mExtension = ext;
    }

    public int getUlLevel() {
      return this.mUlLevel;
    }

    public int getDlLevel() {
      return this.mDlLevel;
    }

    public boolean getPrioDefault() {
      return this.mPrioDefault;
    }

    public boolean getExtension() {
      return this.mExtension;
    }
  }

  public static class LatencyCallback {
    public void onGetLatencyDone(LatencyResult result) {}
  }

  public LowLatencyModeManager(Context context) {
    this.mContext = context;
  }

  public void getLowLatencyMode(LatencyCallback callback) {
    int defaultDataSlotId =
        SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
    if (!SubscriptionManager.isValidPhoneId(defaultDataSlotId)) {
      loge("invalid default datat slotId id, " + defaultDataSlotId);
    } else {
      getLowLatencyMode(defaultDataSlotId, callback);
    }
  }

  public void getLowLatencyMode(int slotId, LatencyCallback callback) {
    if (!SubscriptionManager.isValidPhoneId(slotId)) {
      loge("invalid slotId id, " + slotId);
      return;
    }
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(bos);
    try {
      dos.writeByte(13);
      dos.writeByte(18);
      dos.writeShort(4);
      if (slotId == 0) {
        connectToRilService();
      } else if (slotId == 1) {
        connectToRilService2();
      }
      log("start to get latency settings from cp, slotId:" + slotId);
      Bundle req = new Bundle();
      req.putByteArray("request", bos.toByteArray());
      req.putInt("slotId", slotId);
      Message response = this.mReceiverHandler.obtainMessage(2);
      response.setData(req);
      response.replyTo = this.mSvcModeMessenger;
      boolean sent = false;
      int cnt = 0;
      while (true) {
        if (cnt >= 10) {
          break;
        }
        try {
          Messenger messenger = this.mServiceMessenger;
          if (messenger != null && slotId == 0) {
            messenger.send(response);
            sent = true;
            break;
          }
          Messenger messenger2 = this.mServiceMessenger2;
          if (messenger2 != null && slotId == 1) {
            messenger2.send(response);
            sent = true;
            break;
          }
          loge("mServiceMessenger is null, wait more time for it is ready");
          try {
            Thread.sleep(200L);
          } catch (InterruptedException ie) {
            ie.printStackTrace();
          } catch (NullPointerException e) {
            e.printStackTrace();
          }
          cnt++;
        } catch (RemoteException e2) {
          loge("get latency settings failed, e:" + e2);
          return;
        }
      }
      if (sent) {
        this.mCallback = callback;
      }
    } catch (IOException e3) {
      loge("make get latency settings data: exception occured: " + e3);
    }
  }

  public void setLowLatencyMode(int ulevel, int dlevel, boolean prio, boolean extension) {
    int defaultDataSlotId =
        SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
    if (!SubscriptionManager.isValidPhoneId(defaultDataSlotId)) {
      loge("invalid default datat slotId id, " + defaultDataSlotId);
    } else {
      setLowLatencyMode(defaultDataSlotId, ulevel, dlevel, prio, extension);
    }
  }

  public void setLowLatencyMode(
      int slotId, int ulevel, int dlevel, boolean prio, boolean extension) {
    if (!SubscriptionManager.isValidPhoneId(slotId)) {
      loge("invalid slotId id, " + slotId);
      return;
    }
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(bos);
    try {
      dos.writeByte(13);
      dos.writeByte(19);
      dos.writeShort(8);
      dos.writeByte(ulevel);
      dos.writeByte(dlevel);
      dos.writeByte(prio ? 1 : 0);
      dos.writeByte(extension ? 1 : 0);
      if (slotId == 0) {
        connectToRilService();
      } else if (slotId == 1) {
        connectToRilService2();
      }
      log(
          "set latency mode, ulevel:"
              + ulevel
              + ",dlevel:"
              + dlevel
              + ", prio:"
              + prio
              + ",extension:"
              + extension
              + ",slotId:"
              + slotId);
      this.mUlLevel = ulevel;
      this.mDlLevel = dlevel;
      this.mPrioDefault = prio;
      this.mExtension = extension;
      Bundle req = new Bundle();
      req.putByteArray("request", bos.toByteArray());
      req.putInt("slotId", slotId);
      Message response = this.mReceiverHandler.obtainMessage(1);
      response.setData(req);
      response.replyTo = this.mSvcModeMessenger;
      for (int cnt = 0; cnt < 10; cnt++) {
        try {
          Messenger messenger = this.mServiceMessenger;
          if (messenger != null && slotId == 0) {
            messenger.send(response);
            return;
          }
          Messenger messenger2 = this.mServiceMessenger2;
          if (messenger2 != null && slotId == 1) {
            messenger2.send(response);
            return;
          }
          loge("mServiceMessenger is null, wait more time for it is ready");
          try {
            Thread.sleep(200L);
          } catch (InterruptedException ie) {
            ie.printStackTrace();
          } catch (NullPointerException e) {
            e.printStackTrace();
          }
        } catch (RemoteException e2) {
          loge("set latency settings failed");
          return;
        }
      }
    } catch (IOException e3) {
      loge("make set latency settings data: exception occured: " + e3);
    }
  }

  private void connectToRilService() {
    log("connect To Ril service");
    Intent intent = new Intent();
    intent.setClassName("com.sec.phone", "com.sec.phone.SecPhoneService");
    this.mContext.bindService(intent, this.mSecPhoneServiceConnection, 1);
  }

  private void connectToRilService2() {
    log("connect To Ril service2");
    Intent intent = new Intent();
    intent.setClassName("com.sec.phone", "com.sec.phone.SecPhoneService2");
    this.mContext.bindService(intent, this.mSecPhoneServiceConnection2, 1);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void unbindRilService(int slotId) {
    if (slotId == 0) {
      if (this.mServiceMessenger != null && this.mSecPhoneServiceConnection != null) {
        try {
          log("unbindRilService");
          this.mContext.unbindService(this.mSecPhoneServiceConnection);
        } catch (IllegalArgumentException e) {
          loge("from unbindRilService : " + e.toString());
        }
        this.mServiceMessenger = null;
        return;
      }
      return;
    }
    if (slotId == 1
        && this.mServiceMessenger2 != null
        && this.mSecPhoneServiceConnection2 != null) {
      try {
        log("unbindRilService2");
        this.mContext.unbindService(this.mSecPhoneServiceConnection2);
      } catch (IllegalArgumentException e2) {
        loge("from unbindRilService : " + e2.toString());
      }
      this.mServiceMessenger2 = null;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void log(String info) {
    Log.m94d(TAG, info);
  }

  private void loge(String info) {
    Log.m96e(TAG, info);
  }
}
