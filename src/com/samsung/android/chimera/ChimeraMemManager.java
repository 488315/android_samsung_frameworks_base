package com.samsung.android.chimera;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.chimera.IChimera;
import java.util.List;

/* loaded from: classes6.dex */
public class ChimeraMemManager {
    private static final String TAG = "ChimeraMemManager";
    private static final Object mLock = new Object();
    private static IChimera sService;
    private Context mContext;

    public ChimeraMemManager(Context context) {
        this.mContext = context;
        getService();
    }

    public ChimeraMemManager(Context context, IChimera iChimera) {
        this.mContext = context;
        setService(iChimera);
    }

    private static void setService(IChimera iChimera) {
        synchronized (mLock) {
            sService = iChimera;
        }
    }

    private IChimera getService() {
        IChimera iChimera;
        synchronized (mLock) {
            if (sService == null) {
                sService = IChimera.Stub.asInterface(ServiceManager.getService("ChimeraManagerService"));
            }
            iChimera = sService;
        }
        return iChimera;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<PSIAvailableMem> getAvailableMemInfo(long j, long j2) {
        try {
            Log.d(TAG, "getAvailableMemInfo  startTime=" + j + " endTime" + j2);
            return getService().getAvailableMemInfo(j, j2);
        } catch (RemoteException unused) {
            return null;
        }
    }
}
