package com.samsung.epic;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.epic.IEpicManager;
import android.os.epic.IEpicObject;

/* loaded from: classes6.dex */
public class Request {
  private static final String TAG = "EpicRequest";
  private static IEpicManager mEpicManager;
  private static boolean mHasLoad = false;
  private IEpicObject mEpicObject;

  private Request() {
    get_service();
  }

  public Request(int scenario_id) {
    this();
    try {
      this.mEpicObject = mEpicManager.Create(scenario_id);
    } catch (Exception e) {
      this.mEpicObject = null;
    }
  }

  public Request(int[] scenario_id_list) {
    this();
    try {
      this.mEpicObject = mEpicManager.Creates(scenario_id_list);
    } catch (Exception e) {
      this.mEpicObject = null;
    }
  }

  public boolean acquire_lock() {
    IEpicObject iEpicObject = this.mEpicObject;
    if (iEpicObject == null) {
      return false;
    }
    try {
      boolean ret = iEpicObject.acquire_lock();
      return ret;
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean release_lock() {
    IEpicObject iEpicObject = this.mEpicObject;
    if (iEpicObject == null) {
      return false;
    }
    try {
      boolean ret = iEpicObject.release_lock();
      return ret;
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean acquire_lock(int value, int usec) {
    IEpicObject iEpicObject = this.mEpicObject;
    if (iEpicObject == null) {
      return false;
    }
    try {
      boolean ret = iEpicObject.acquire_lock_option(value, usec);
      return ret;
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean acquire_lock(int[] value_list, int[] usec_list) {
    IEpicObject iEpicObject = this.mEpicObject;
    if (iEpicObject == null) {
      return false;
    }
    try {
      boolean ret = iEpicObject.acquire_lock_option_multi(value_list, usec_list);
      return ret;
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean perf_hint(String name) {
    IEpicObject iEpicObject = this.mEpicObject;
    if (iEpicObject == null) {
      return false;
    }
    try {
      boolean ret = iEpicObject.perf_hint(name);
      return ret;
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean hint_release(String name) {
    IEpicObject iEpicObject = this.mEpicObject;
    if (iEpicObject == null) {
      return false;
    }
    try {
      boolean ret = iEpicObject.hint_release(name);
      return ret;
    } catch (RemoteException e) {
      return false;
    }
  }

  private void get_service() {
    synchronized (Request.class) {
      try {
      } catch (Exception e) {
        mEpicManager = null;
      }
      if (mHasLoad) {
        return;
      }
      mEpicManager = IEpicManager.Stub.asInterface(ServiceManager.getService(Context.EPIC_SERVICE));
      mHasLoad = true;
    }
  }
}
