package com.samsung.android.chimera;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import java.util.List;

/* loaded from: classes5.dex */
public class ChimeraMemManager {
    private static final String TAG = "ChimeraMemManager";
    private static final Object mLock = new Object();
    private static IChimera sService = null;
    private Context mContext;

    public ChimeraMemManager(Context mContext) {
        this.mContext = mContext;
        getService();
    }

    public ChimeraMemManager(Context context, IChimera service) {
        this.mContext = context;
        setService(service);
    }

    private static void setService(IChimera service) {
        synchronized (mLock) {
            sService = service;
        }
    }

    private IChimera getService() {
        IChimera iChimera;
        synchronized (mLock) {
            if (sService == null) {
                sService =
                        IChimera.Stub.asInterface(
                                ServiceManager.getService("ChimeraManagerService"));
            }
            iChimera = sService;
        }
        return iChimera;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<PSIAvailableMem> getAvailableMemInfo(long startTime, long endTime) {
        try {
            Log.d(TAG, "getAvailableMemInfo  startTime=" + startTime + " endTime" + endTime);
            return getService().getAvailableMemInfo(startTime, endTime);
        } catch (RemoteException e) {
            return null;
        }
    }
}
