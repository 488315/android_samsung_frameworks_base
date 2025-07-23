package android.app;

import android.app.IUriGrantsManager;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Singleton;

/* loaded from: classes.dex */
public class UriGrantsManager {
    private static final Singleton<IUriGrantsManager> IUriGrantsManagerSingleton = new Singleton<IUriGrantsManager>() { // from class: android.app.UriGrantsManager.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public IUriGrantsManager create() {
            return IUriGrantsManager.Stub.asInterface(ServiceManager.getService(Context.URI_GRANTS_SERVICE));
        }
    };
    private final Context mContext;

    UriGrantsManager(Context context, Handler handler) {
        this.mContext = context;
    }

    public static IUriGrantsManager getService() {
        return IUriGrantsManagerSingleton.get();
    }

    public void clearGrantedUriPermissions(String str) {
        try {
            getService().clearGrantedUriPermissions(str, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParceledListSlice<GrantedUriPermission> getGrantedUriPermissions(String str) {
        try {
            return getService().getGrantedUriPermissions(str, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
