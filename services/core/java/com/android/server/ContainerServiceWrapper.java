package com.android.server;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.pm.PersonaServiceProxy;
import com.samsung.android.knox.IContainerService;

/* loaded from: classes.dex */
public class ContainerServiceWrapper {
  public String category;
  public ContainerServiceInfo info;
  public boolean mBound;
  public IContainerService mContainerService;
  public Context mContext;
  public PersonaServiceProxy mService;
  public ComponentName name;
  public int userid;
  public String TAG = "KnoxService::ContainerServiceWrapper";
  public final ServiceConnection mConnection =
      new ServiceConnection() { // from class: com.android.server.ContainerServiceWrapper.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
          Log.v(
              ContainerServiceWrapper.this.TAG,
              "Container service started : " + ContainerServiceWrapper.this.info.toString());
          ContainerServiceWrapper.this.mHandler.removeMessages(1);
          ContainerServiceWrapper.this.mContainerService =
              IContainerService.Stub.asInterface(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
          Log.v(
              ContainerServiceWrapper.this.TAG,
              "Container service disconnected : " + ContainerServiceWrapper.this.info.toString());
          if (ContainerServiceWrapper.this.mBound) {
            ContainerServiceWrapper.this.mBound = false;
            ContainerServiceWrapper.this.triggerRestart();
          }
        }
      };
  public final Handler mHandler =
      new Handler() { // from class: com.android.server.ContainerServiceWrapper.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
          if (message.what != 1) {
            return;
          }
          ContainerServiceWrapper containerServiceWrapper = ContainerServiceWrapper.this;
          containerServiceWrapper.mService.handleServiceDied(containerServiceWrapper.info);
        }
      };

  public ContainerServiceWrapper(
      Context context,
      PersonaServiceProxy personaServiceProxy,
      ContainerServiceInfo containerServiceInfo) {
    this.mContext = context;
    this.mService = personaServiceProxy;
    this.info = containerServiceInfo;
    this.userid = containerServiceInfo.userid;
    this.category = containerServiceInfo.category;
    this.name = containerServiceInfo.name;
  }

  public boolean connect() {
    if (isBound()) {
      Log.e(this.TAG, "service " + this.name.flattenToShortString() + " already bound");
      return true;
    }
    boolean bindServiceAsUser =
        this.mContext.bindServiceAsUser(
            new Intent().setComponent(this.name),
            this.mConnection,
            67108865,
            new UserHandle(this.userid));
    this.mBound = bindServiceAsUser;
    if (!bindServiceAsUser) {
      Log.e(this.TAG, "Can't bind to container service " + this.name.flattenToShortString());
    }
    return this.mBound;
  }

  public boolean disconnect() {
    if (!isBound()) {
      return false;
    }
    this.mContext.unbindService(this.mConnection);
    this.mContainerService = null;
    this.mBound = false;
    Log.d(this.TAG, "Ubinding service is successful...");
    return true;
  }

  public boolean isBound() {
    return this.mBound;
  }

  public final void triggerRestart() {
    this.mHandler.removeMessages(1);
    this.mHandler.sendEmptyMessage(1);
  }

  public Bundle onMessage(String str, Bundle bundle) {
    try {
      IContainerService iContainerService = this.mContainerService;
      if (iContainerService != null) {
        return iContainerService.onMessage(str, bundle);
      }
      return null;
    } catch (RemoteException e) {
      Log.e(
          this.TAG, "RemoteException: name:" + this.name.flattenToShortString() + " action:" + str);
      e.printStackTrace();
      return null;
    }
  }
}
