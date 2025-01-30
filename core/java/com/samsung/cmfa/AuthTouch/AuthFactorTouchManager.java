package com.samsung.cmfa.AuthTouch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;

/* loaded from: classes6.dex */
public class AuthFactorTouchManager {
  private static final String TAG = "AuthFactorTouchManager";
  private static AuthFactorTouchManager mAuthFactorTouchManager;
  private IAuthTouchEnableListener mAuthTouchEnableListener;
  private Context mContext;
  private IAuthFactorTouchService mRemoteService;
  private PackageManager packageManager;
  private boolean isServiceConnected = false;
  private boolean isEnableListenerRegistered = false;
  private ServiceConnection mConnection =
      new ServiceConnection() { // from class: com.samsung.cmfa.AuthTouch.AuthFactorTouchManager.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
          Log.m94d(AuthFactorTouchManager.TAG, "onServiceConnected");
          AuthFactorTouchManager.this.mRemoteService =
              IAuthFactorTouchService.Stub.asInterface(service);
          AuthFactorTouchManager.this.isServiceConnected = true;
          if (name != null) {
            Log.m94d(AuthFactorTouchManager.TAG, "onServiceConnected" + name.toString());
          }
          if (AuthFactorTouchManager.this.mAuthTouchEnableListener != null) {
            try {
              Log.m94d(
                  AuthFactorTouchManager.TAG,
                  "mAuthTouchEnableListener:"
                      + AuthFactorTouchManager.this.mAuthTouchEnableListener);
              AuthFactorTouchManager.this.mRemoteService.registerAuthTouchEnableListener(
                  AuthFactorTouchManager.this.mAuthTouchEnableListener);
              AuthFactorTouchManager.this.mAuthTouchEnableListener = null;
              AuthFactorTouchManager.this.isEnableListenerRegistered = true;
            } catch (RemoteException re) {
              re.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
          AuthFactorTouchManager.this.mRemoteService = null;
          AuthFactorTouchManager.this.isServiceConnected = false;
          AuthFactorTouchManager.this.isEnableListenerRegistered = false;
        }
      };

  private AuthFactorTouchManager(Context context) {
    this.mContext = context;
    this.packageManager = context.getPackageManager();
    if (this.mRemoteService == null) {
      connectService();
    }
  }

  public static AuthFactorTouchManager getInstance(Context context) {
    if (mAuthFactorTouchManager == null) {
      mAuthFactorTouchManager = new AuthFactorTouchManager(context);
    }
    return mAuthFactorTouchManager;
  }

  private void connectService() {
    ComponentName serviceComponent =
        new ComponentName(
            "com.samsung.cmfa.AuthTouch", "com.samsung.cmfa.AuthTouch.AuthFactorTouchService");
    if (isServiceInstalled(serviceComponent)) {
      Intent i = new Intent("com.samsung.cmfa.AuthTouch.AuthFactorTouchService");
      i.setPackage("com.samsung.cmfa.AuthTouch");
      this.mContext.bindService(i, this.mConnection, 1);
    }
  }

  public boolean isServiceConnected() {
    return this.isServiceConnected;
  }

  public boolean isEnableListenerRegistered() {
    return this.isEnableListenerRegistered;
  }

  public boolean registerAuthTouchEventListener(IAuthTouchEventListener listener) {
    IAuthFactorTouchService iAuthFactorTouchService = this.mRemoteService;
    if (iAuthFactorTouchService == null) {
      connectService();
      return false;
    }
    try {
      iAuthFactorTouchService.registerAuthTouchEventListener(listener);
      return true;
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean unregisterAuthTouchEventListener(IAuthTouchEventListener listener) {
    IAuthFactorTouchService iAuthFactorTouchService = this.mRemoteService;
    if (iAuthFactorTouchService == null) {
      connectService();
      return false;
    }
    try {
      iAuthFactorTouchService.unregisterAuthTouchEventListener(listener);
      return true;
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }

  public void registerAuthTouchEnableListener(IAuthTouchEnableListener listener) {
    this.mAuthTouchEnableListener = listener;
  }

  public boolean setTouchEvent(boolean ret, boolean debugMode) {
    IAuthFactorTouchService iAuthFactorTouchService = this.mRemoteService;
    if (iAuthFactorTouchService == null) {
      connectService();
      return false;
    }
    try {
      iAuthFactorTouchService.setTouchEvent(ret, debugMode);
      return true;
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }

  public void onPointerEvent(MotionEvent event) {
    IAuthFactorTouchService iAuthFactorTouchService = this.mRemoteService;
    if (iAuthFactorTouchService == null) {
      connectService();
      return;
    }
    try {
      iAuthFactorTouchService.onPointerEvent(event);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  private boolean isServiceInstalled(ComponentName component) {
    try {
      this.packageManager.getServiceInfo(component, 0);
      return true;
    } catch (PackageManager.NameNotFoundException e) {
      return false;
    }
  }
}
