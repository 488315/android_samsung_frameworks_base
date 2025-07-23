package android.content.pm;

import android.os.RemoteException;

/* loaded from: classes.dex */
public class DataLoaderManager {
    private static final String TAG = "DataLoaderManager";
    private final IDataLoaderManager mService;

    public DataLoaderManager(IDataLoaderManager iDataLoaderManager) {
        this.mService = iDataLoaderManager;
    }

    public boolean bindToDataLoader(int i, DataLoaderParamsParcel dataLoaderParamsParcel, long j, IDataLoaderStatusListener iDataLoaderStatusListener) {
        try {
            return this.mService.bindToDataLoader(i, dataLoaderParamsParcel, j, iDataLoaderStatusListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IDataLoader getDataLoader(int i) {
        try {
            return this.mService.getDataLoader(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unbindFromDataLoader(int i) {
        try {
            this.mService.unbindFromDataLoader(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
