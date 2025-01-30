package com.android.server.display;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.SemDlnaDevice;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;

/* loaded from: classes2.dex */
public final class DlnaController {
  public Context mContext;
  public SemDlnaDevice mDevice = new SemDlnaDevice();
  public DlnaClientDeathMonitor mDlnaMonitor;
  public final Handler mHandler;

  public final String dlnaConnectionStateToString(int i) {
    return i != 0
        ? i != 1 ? i != 2 ? i != 3 ? "None" : "Connecting" : "Error" : "Connected"
        : "Not_connected";
  }

  public final String dlnaPlayerTypeToString(int i) {
    return i != 0 ? i != 1 ? i != 2 ? i != 3 ? "None" : "Music_chn" : "Music" : "Image" : "Video";
  }

  public DlnaController(Context context, Handler handler) {
    this.mContext = context;
    this.mHandler = handler;
  }

  public boolean setDlnaDevice(SemDlnaDevice semDlnaDevice, IBinder iBinder) {
    Log.d(
        "DlnaController",
        "setDlnaDevice ::type = "
            + dlnaPlayerTypeToString(semDlnaDevice.getDlnaType())
            + ", state = "
            + dlnaConnectionStateToString(semDlnaDevice.getConnectionState())
            + ", name = "
            + semDlnaDevice.getDeviceName());
    if (semDlnaDevice.getDlnaType() != -1 && semDlnaDevice.getDlnaType() != 3) {
      r2 = this.mDevice.getConnectionState() != semDlnaDevice.getConnectionState();
      if (iBinder != null && this.mDlnaMonitor == null) {
        this.mDlnaMonitor = new DlnaClientDeathMonitor(iBinder, semDlnaDevice.getDlnaType());
      }
      this.mDevice = semDlnaDevice;
      sendStatusChangedBroadcast();
    }
    return r2;
  }

  public SemDlnaDevice getDlnaDevice() {
    return this.mDevice;
  }

  public boolean isConnected() {
    return this.mDevice.isConnected();
  }

  public void sendDisconnectionRequestBroadcast() {
    if (isConnected()) {
      this.mHandler.post(
          new Runnable() { // from class: com.android.server.display.DlnaController.1
            @Override // java.lang.Runnable
            public void run() {
              Log.d(
                  "DlnaController",
                  "sendDisconnectionRequestBroadcast::DLNA_DISCONNECTION_REQUEST");
              Intent intent =
                  new Intent("com.sec.android.screensharing.DLNA_DISCONNECTION_REQUEST");
              intent.putExtra("uid", DlnaController.this.mDevice.getUid());
              DlnaController.this.mContext.sendBroadcastAsUser(
                  intent, UserHandle.ALL, "android.permission.CONFIGURE_WIFI_DISPLAY");
            }
          });
    }
  }

  public final void sendStatusChangedBroadcast() {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.DlnaController.2
          @Override // java.lang.Runnable
          public void run() {
            Intent intent = new Intent("com.samsung.intent.action.DLNA_STATUS_CHANGED");
            int connectionState = DlnaController.this.mDevice.getConnectionState();
            intent.putExtra("status", connectionState);
            intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, connectionState);
            intent.putExtra("player_type", DlnaController.this.mDevice.getDlnaType());
            Log.d(
                "DlnaController",
                "sendStatusChangedBroadcast::SEM_ACTION_DLNA_STATUS_CHANGED state : "
                    + connectionState);
            DlnaController.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
          }
        });
  }

  public class DlnaClientDeathMonitor implements IBinder.DeathRecipient {
    public IBinder mBinder;
    public int mPlayerType;

    public DlnaClientDeathMonitor(IBinder iBinder, int i) {
      Log.d(
          "DlnaController",
          "DlnaClientDeathMonitor, playerType : " + DlnaController.this.dlnaPlayerTypeToString(i));
      this.mBinder = iBinder;
      this.mPlayerType = i;
      try {
        iBinder.linkToDeath(this, 0);
      } catch (RemoteException unused) {
        binderDied();
      }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      Log.i("DlnaController", "binderDied");
      DlnaController.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.display.DlnaController.DlnaClientDeathMonitor.1
            @Override // java.lang.Runnable
            public void run() {
              if (DlnaController.this.isConnected()) {
                DlnaController.this.mDevice.setConnectionState(0);
                DlnaController.this.sendStatusChangedBroadcast();
              }
              if (DlnaController.this.mDlnaMonitor != null) {
                DlnaController.this.mDlnaMonitor.unlinkToDeath();
                DlnaController.this.mDlnaMonitor = null;
              }
            }
          });
    }

    public void unlinkToDeath() {
      Log.i("DlnaController", "unlinkToDeath");
      this.mBinder.unlinkToDeath(this, 0);
    }
  }
}
