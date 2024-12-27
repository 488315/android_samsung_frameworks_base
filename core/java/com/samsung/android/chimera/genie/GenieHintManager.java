package com.samsung.android.chimera.genie;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.samsung.android.chimera.IChimera;

public class GenieHintManager {
    private static final String TAG = "GenieHintManager";
    private static IChimera sService = null;
    private static GenieHintManager mGenieHintMgr = null;

    private GenieHintManager() {
        getChimeraService();
    }

    public static synchronized GenieHintManager getGenieHintManager() {
        GenieHintManager genieHintManager;
        synchronized (GenieHintManager.class) {
            if (mGenieHintMgr == null) {
                mGenieHintMgr = new GenieHintManager();
            }
            genieHintManager = mGenieHintMgr;
        }
        return genieHintManager;
    }

    private IChimera getChimeraService() {
        if (sService == null) {
            sService =
                    IChimera.Stub.asInterface(ServiceManager.getService("ChimeraManagerService"));
        }
        if (sService == null) {
            Log.e(TAG, "ChimeraManagerService not accessible from here!!!!!");
        }
        return sService;
    }

    public void setGenieSessionStart() {
        try {
            getChimeraService().setGenieSessionStart();
        } catch (RemoteException | NullPointerException e) {
            Log.i(TAG, "Exception Caught while setGenieSessionStart " + e);
        }
    }

    public void prepareMemoryRequest(MemRequest memReq) {
        try {
            if (memReq == null) {
                Log.e(TAG, "Null MemRequest or Genie Disabled");
            } else {
                getChimeraService().prepareMemory(memReq);
            }
        } catch (RemoteException | NullPointerException e) {
            Log.i(TAG, "Exception Caught while prepareMemory " + e);
        }
    }

    public void setGenieSessionEnd() {
        try {
            Log.i(TAG, "Calling setGenieSessionEnd..");
            getChimeraService().setGenieSessionEnd();
        } catch (RemoteException | NullPointerException e) {
            Log.i(TAG, "Exception Caught while setGenieSessionEnd " + e);
        }
    }
}
