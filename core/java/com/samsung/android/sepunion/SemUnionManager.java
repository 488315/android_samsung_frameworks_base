package com.samsung.android.sepunion;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class SemUnionManager {
  private static final boolean DEBUG = false;
  private static final String TAG = SemUnionManager.class.getSimpleName();
  private static final Object mLock = new Object();
  private static final HashMap<String, Constructor> sConstructorMap = new HashMap<>();
  private static final HashMap<String, Object> sManagerMap = new HashMap<>();
  private static boolean sNeedInitialize;
  private Context mContext;
  private IUnionManager mService;

  static {
    for (String key : UnionConstants.sClassPathForManager.keySet()) {
      sManagerMap.put(key, null);
    }
    sNeedInitialize = true;
  }

  public SemUnionManager(Context context) {
    this(context, null);
  }

  public SemUnionManager(Context context, IUnionManager service) {
    this.mContext = context;
    if (service == null) {
      this.mService = getService();
    } else {
      this.mService = service;
    }
    initializeManagerMapData();
  }

  private void initializeManagerMapData() {
    synchronized (mLock) {
      if (sNeedInitialize) {
        int callingUid = Binder.getCallingUid();
        Log.m301i(
            TAG,
            "initializeManagerMapData(" + callingUid + ") context = " + this.mContext.toString());
        for (Map.Entry<String, String> entry : UnionConstants.sClassPathForManager.entrySet()) {
          String key = entry.getKey();
          Constructor constructor = getConstructor(entry.getValue());
          if (constructor != null) {
            sConstructorMap.put(key, constructor);
          }
        }
        sNeedInitialize = false;
      }
    }
  }

  private IUnionManager getService() {
    IUnionManager service =
        IUnionManager.Stub.asInterface(ServiceManager.getService(Context.SEP_UNION_SERVICE));
    if (service == null) {
      Log.m301i(TAG, "IUnionManager is NULL");
    }
    return service;
  }

  public static boolean isUnionService(String name) {
    boolean containsKey;
    synchronized (mLock) {
      containsKey = sManagerMap.containsKey(name);
    }
    return containsKey;
  }

  public Object getUnionService(String name) {
    Object obj;
    Log.m301i(TAG, "getUnionService(" + name + NavigationBarInflaterView.KEY_CODE_END);
    synchronized (mLock) {
      if (sNeedInitialize) {
        initializeManagerMapData();
      }
      Constructor t = sConstructorMap.get(name);
      obj = null;
      try {
        try {
          try {
            obj = t.newInstance(this.mContext);
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        } catch (InvocationTargetException e2) {
          e2.printStackTrace();
        }
      } catch (InstantiationException e3) {
        e3.printStackTrace();
      }
    }
    return obj;
  }

  private Constructor getConstructor(String classPath) {
    try {
      Class clazz = Class.forName(classPath);
      Constructor constructor = clazz.getConstructor(Context.class);
      return constructor;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (NoSuchMethodException e2) {
      e2.printStackTrace();
      return null;
    }
  }

  public IBinder getSemSystemService(String name) {
    Log.m301i(TAG, "getSemSystemService : " + name);
    if (this.mService == null) {
      this.mService = getService();
    }
    try {
      IBinder b = this.mService.getSemSystemService(name, new Bundle());
      return b;
    } catch (RemoteException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void setDumpEnabled(String label, String path) {
    if (this.mService == null) {
      this.mService = getService();
    }
    try {
      Log.m301i(TAG, "setDumpEnabled : " + path);
      this.mService.setDumpEnabled(label, path);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}
