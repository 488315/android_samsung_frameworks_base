package android.os.epic;

import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class EpicManager {
    private static final String TAG = "EpicManager";
    final IEpicManager mService;

    public EpicManager(IEpicManager service) {
        this.mService = service;
    }

    IEpicObject Create(int scenario_id) {
        if (this.mService == null) {
            return null;
        }
        try {
            IEpicObject ret = this.mService.Create(scenario_id);
            return ret;
        } catch (RemoteException e) {
            return null;
        }
    }

    IEpicObject Creates(int[] scenario_id_list) {
        if (this.mService == null) {
            return null;
        }
        try {
            IEpicObject ret = this.mService.Creates(scenario_id_list);
            return ret;
        } catch (RemoteException e) {
            return null;
        }
    }
}
