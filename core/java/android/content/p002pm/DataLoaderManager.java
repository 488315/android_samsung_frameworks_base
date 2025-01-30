package android.content.p002pm;

import android.p009os.RemoteException;

/* loaded from: classes.dex */
public class DataLoaderManager {
    private static final String TAG = "DataLoaderManager";
    private final IDataLoaderManager mService;

    public DataLoaderManager(IDataLoaderManager service) {
        this.mService = service;
    }

    public boolean bindToDataLoader(int dataLoaderId, DataLoaderParamsParcel params, long bindDelayMs, IDataLoaderStatusListener listener) {
        try {
            return this.mService.bindToDataLoader(dataLoaderId, params, bindDelayMs, listener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IDataLoader getDataLoader(int dataLoaderId) {
        try {
            return this.mService.getDataLoader(dataLoaderId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unbindFromDataLoader(int dataLoaderId) {
        try {
            this.mService.unbindFromDataLoader(dataLoaderId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
