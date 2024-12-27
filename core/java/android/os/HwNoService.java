package android.os;

import android.internal.hidl.base.V1_0.IBase;
import android.internal.hidl.manager.V1_0.IServiceManager;
import android.internal.hidl.manager.V1_0.IServiceNotification;
import android.internal.hidl.manager.V1_2.IServiceManager;
import android.util.Log;

import java.util.ArrayList;

/* loaded from: classes3.dex */
final class HwNoService extends IServiceManager.Stub implements IHwBinder, IHwInterface {
    private static final String TAG = "HwNoService";

    HwNoService() {}

    @Override // android.internal.hidl.manager.V1_2.IServiceManager.Stub
    public String toString() {
        return "[HwNoService]";
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public IBase get(String fqName, String name) throws RemoteException {
        Log.i(TAG, "get " + fqName + "/" + name + " with no hwservicemanager");
        return null;
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public boolean add(String name, IBase service) throws RemoteException {
        Log.i(TAG, "get " + name + " with no hwservicemanager");
        return false;
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public byte getTransport(String fqName, String name) throws RemoteException {
        Log.i(TAG, "getTransoport " + fqName + "/" + name + " with no hwservicemanager");
        return (byte) 0;
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public ArrayList<String> list() throws RemoteException {
        Log.i(TAG, "list with no hwservicemanager");
        return new ArrayList<>();
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public ArrayList<String> listByInterface(String fqName) throws RemoteException {
        Log.i(TAG, "listByInterface with no hwservicemanager");
        return new ArrayList<>();
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public boolean registerForNotifications(
            String fqName, String name, IServiceNotification callback) throws RemoteException {
        Log.i(TAG, "registerForNotifications with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public ArrayList<IServiceManager.InstanceDebugInfo> debugDump() throws RemoteException {
        Log.i(TAG, "debugDump with no hwservicemanager");
        return new ArrayList<>();
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public void registerPassthroughClient(String fqName, String name) throws RemoteException {
        Log.i(TAG, "registerPassthroughClient with no hwservicemanager");
    }

    @Override // android.internal.hidl.manager.V1_1.IServiceManager
    public boolean unregisterForNotifications(
            String fqName, String name, IServiceNotification callback) throws RemoteException {
        Log.i(TAG, "unregisterForNotifications with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public boolean registerClientCallback(
            String fqName,
            String name,
            IBase server,
            android.internal.hidl.manager.V1_2.IClientCallback cb)
            throws RemoteException {
        Log.i(
                TAG,
                "registerClientCallback for " + fqName + "/" + name + " with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public boolean unregisterClientCallback(
            IBase server, android.internal.hidl.manager.V1_2.IClientCallback cb)
            throws RemoteException {
        Log.i(TAG, "unregisterClientCallback with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public boolean addWithChain(String name, IBase service, ArrayList<String> chain)
            throws RemoteException {
        Log.i(TAG, "addWithChain with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public ArrayList<String> listManifestByInterface(String fqName) throws RemoteException {
        Log.i(TAG, "listManifestByInterface for " + fqName + " with no hwservicemanager");
        return new ArrayList<>();
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public boolean tryUnregister(String fqName, String name, IBase service) throws RemoteException {
        Log.i(TAG, "tryUnregister for " + fqName + "/" + name + " with no hwservicemanager");
        return true;
    }
}
