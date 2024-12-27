package com.samsung.android.isrb;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Singleton;

public class IsrbManager {
    private static final Singleton<IIsrbManager> IIsrbManagerSingleton =
            new Singleton<IIsrbManager>() { // from class: com.samsung.android.isrb.IsrbManager.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.util.Singleton
                public IIsrbManager create() {
                    IBinder b = ServiceManager.getService(Context.ISRB_MANAGER_SERVICE);
                    IIsrbManager im = IIsrbManager.Stub.asInterface(b);
                    return im;
                }
            };
    private static final String TAG = "IsrbManager";
    private Context mContext;

    public IsrbManager(Context context) {
        this.mContext = context;
    }

    public static IIsrbManager getService() {
        return IIsrbManagerSingleton.get();
    }

    public boolean isBootCompleteState() {
        try {
            boolean state = getService().isBootCompleteState();
            return state;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in isBootCompleteState : ", e);
            return false;
        }
    }

    public void setIsrbEnable(boolean state) {
        try {
            getService().setIsrbEnable(state);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in setIsrbEnable : ", e);
        }
    }

    public void setFakeTime() {
        try {
            getService().setFakeTime();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in setFakeTime : ", e);
        }
    }
}
