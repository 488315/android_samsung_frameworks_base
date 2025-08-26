package com.samsung.android.sepunion;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.samsung.android.sepunion.IUnionManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes6.dex */
public class SemUnionManager {
    private static final boolean DEBUG = false;
    private static final String TAG = "SemUnionManager";
    private static final Object mLock = new Object();
    private static final HashMap<String, Constructor> sConstructorMap = new HashMap<>();
    private static final HashMap<String, Object> sManagerMap = new HashMap<>();
    private static boolean sNeedInitialize;
    private Context mContext;
    private IUnionManager mService;

    static {
        Iterator<String> it = UnionConstants.sClassPathForManager.keySet().iterator();
        while (it.hasNext()) {
            sManagerMap.put(it.next(), null);
        }
        sNeedInitialize = true;
    }

    public SemUnionManager(Context context) {
        this(context, null);
    }

    public SemUnionManager(Context context, IUnionManager iUnionManager) {
        this.mContext = context;
        if (iUnionManager == null) {
            this.mService = getService();
        } else {
            this.mService = iUnionManager;
        }
        initializeManagerMapData();
    }

    private void initializeManagerMapData() {
        synchronized (mLock) {
            if (sNeedInitialize) {
                int callingUid = Binder.getCallingUid();
                Log.i(TAG, "initializeManagerMapData(" + callingUid + ") context = " + this.mContext.toString());
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
        IUnionManager iUnionManagerAsInterface = IUnionManager.Stub.asInterface(ServiceManager.getService(Context.SEP_UNION_SERVICE));
        if (iUnionManagerAsInterface == null) {
            Log.i(TAG, "IUnionManager is NULL");
        }
        return iUnionManagerAsInterface;
    }

    public static boolean isUnionService(String str) {
        boolean zContainsKey;
        synchronized (mLock) {
            zContainsKey = sManagerMap.containsKey(str);
        }
        return zContainsKey;
    }

    public Object getUnionService(String str) {
        Object objNewInstance;
        Log.i(TAG, "getUnionService(" + str + NavigationBarInflaterView.KEY_CODE_END);
        synchronized (mLock) {
            if (sNeedInitialize) {
                initializeManagerMapData();
            }
            try {
                try {
                    objNewInstance = sConstructorMap.get(str).newInstance(this.mContext);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    objNewInstance = null;
                    return objNewInstance;
                }
            } catch (InstantiationException e2) {
                e2.printStackTrace();
                objNewInstance = null;
                return objNewInstance;
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
                objNewInstance = null;
                return objNewInstance;
            }
        }
        return objNewInstance;
    }

    private Constructor getConstructor(String str) {
        try {
            return Class.forName(str).getConstructor(Context.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public IBinder getSemSystemService(String str) {
        Log.i(TAG, "getSemSystemService : " + str);
        if (this.mService == null) {
            this.mService = getService();
        }
        try {
            return this.mService.getSemSystemService(str, new Bundle());
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setDumpEnabled(String str, String str2) {
        if (this.mService == null) {
            this.mService = getService();
        }
        try {
            Log.i(TAG, "setDumpEnabled : " + str2);
            this.mService.setDumpEnabled(str, str2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
