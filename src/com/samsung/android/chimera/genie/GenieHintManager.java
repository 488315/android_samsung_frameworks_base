package com.samsung.android.chimera.genie;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.chimera.IChimera;

/* loaded from: classes6.dex */
public class GenieHintManager {
    private static final String TAG = "GenieHintManager";
    private static GenieHintManager mGenieHintMgr;
    private static IChimera sService;

    private GenieHintManager() {
        getChimeraService();
    }

    public static synchronized GenieHintManager getGenieHintManager() {
        if (mGenieHintMgr == null) {
            mGenieHintMgr = new GenieHintManager();
        }
        return mGenieHintMgr;
    }

    private IChimera getChimeraService() {
        if (sService == null) {
            sService = IChimera.Stub.asInterface(ServiceManager.getService("ChimeraManagerService"));
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

    public void prepareMemoryRequest(MemRequest memRequest) {
        try {
            if (memRequest == null) {
                Log.e(TAG, "Null MemRequest or Genie Disabled");
            } else {
                getChimeraService().prepareMemory(memRequest);
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
