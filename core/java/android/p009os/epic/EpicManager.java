package android.p009os.epic;

import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public final class EpicManager {
    private static final String TAG = "EpicManager";
    final IEpicManager mService;

    public EpicManager(IEpicManager service) {
        this.mService = service;
    }

    IEpicObject Create(int scenario_id) {
        IEpicManager iEpicManager = this.mService;
        if (iEpicManager == null) {
            return null;
        }
        try {
            IEpicObject ret = iEpicManager.Create(scenario_id);
            return ret;
        } catch (RemoteException e) {
            return null;
        }
    }

    IEpicObject Creates(int[] scenario_id_list) {
        IEpicManager iEpicManager = this.mService;
        if (iEpicManager == null) {
            return null;
        }
        try {
            IEpicObject ret = iEpicManager.Creates(scenario_id_list);
            return ret;
        } catch (RemoteException e) {
            return null;
        }
    }
}
