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

    HwNoService() {
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager.Stub
    public String toString() {
        return "[HwNoService]";
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public IBase get(String str, String str2) throws RemoteException {
        Log.i(TAG, "get " + str + "/" + str2 + " with no hwservicemanager");
        return null;
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public boolean add(String str, IBase iBase) throws RemoteException {
        Log.i(TAG, "get " + str + " with no hwservicemanager");
        return false;
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public byte getTransport(String str, String str2) throws RemoteException {
        Log.i(TAG, "getTransoport " + str + "/" + str2 + " with no hwservicemanager");
        return (byte) 0;
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public ArrayList<String> list() throws RemoteException {
        Log.i(TAG, "list with no hwservicemanager");
        return new ArrayList<>();
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public ArrayList<String> listByInterface(String str) throws RemoteException {
        Log.i(TAG, "listByInterface with no hwservicemanager");
        return new ArrayList<>();
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public boolean registerForNotifications(String str, String str2, IServiceNotification iServiceNotification) throws RemoteException {
        Log.i(TAG, "registerForNotifications with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public ArrayList<IServiceManager.InstanceDebugInfo> debugDump() throws RemoteException {
        Log.i(TAG, "debugDump with no hwservicemanager");
        return new ArrayList<>();
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public void registerPassthroughClient(String str, String str2) throws RemoteException {
        Log.i(TAG, "registerPassthroughClient with no hwservicemanager");
    }

    @Override // android.internal.hidl.manager.V1_1.IServiceManager
    public boolean unregisterForNotifications(String str, String str2, IServiceNotification iServiceNotification) throws RemoteException {
        Log.i(TAG, "unregisterForNotifications with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public boolean registerClientCallback(String str, String str2, IBase iBase, android.internal.hidl.manager.V1_2.IClientCallback iClientCallback) throws RemoteException {
        Log.i(TAG, "registerClientCallback for " + str + "/" + str2 + " with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public boolean unregisterClientCallback(IBase iBase, android.internal.hidl.manager.V1_2.IClientCallback iClientCallback) throws RemoteException {
        Log.i(TAG, "unregisterClientCallback with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public boolean addWithChain(String str, IBase iBase, ArrayList<String> arrayList) throws RemoteException {
        Log.i(TAG, "addWithChain with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public ArrayList<String> listManifestByInterface(String str) throws RemoteException {
        Log.i(TAG, "listManifestByInterface for " + str + " with no hwservicemanager");
        return new ArrayList<>();
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public boolean tryUnregister(String str, String str2, IBase iBase) throws RemoteException {
        Log.i(TAG, "tryUnregister for " + str + "/" + str2 + " with no hwservicemanager");
        return true;
    }
}
