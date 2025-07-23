package android.os;

import android.os.IServiceManager;

/* compiled from: ServiceManagerNative.java */
/* loaded from: classes3.dex */
class ServiceManagerProxy implements IServiceManager {
    private IBinder mRemote;
    private IServiceManager mServiceManager = IServiceManager.Stub.asInterface(Binder.allowBlocking(getNativeServiceManager()));

    private native IBinder getNativeServiceManager();

    public ServiceManagerProxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override // android.os.IServiceManager
    public IBinder getService(String str) throws RemoteException {
        return checkService2(str).getServiceWithMetadata().service;
    }

    @Override // android.os.IServiceManager
    public Service getService2(String str) throws RemoteException {
        return checkService2(str);
    }

    @Override // android.os.IServiceManager
    public IBinder checkService(String str) throws RemoteException {
        return checkService2(str).getServiceWithMetadata().service;
    }

    @Override // android.os.IServiceManager
    public Service checkService2(String str) throws RemoteException {
        return this.mServiceManager.checkService2(str);
    }

    @Override // android.os.IServiceManager
    public void addService(String str, IBinder iBinder, boolean z, int i) throws RemoteException {
        this.mServiceManager.addService(str, iBinder, z, i);
    }

    @Override // android.os.IServiceManager
    public String[] listServices(int i) throws RemoteException {
        return this.mServiceManager.listServices(i);
    }

    @Override // android.os.IServiceManager
    public void registerForNotifications(String str, IServiceCallback iServiceCallback) throws RemoteException {
        this.mServiceManager.registerForNotifications(str, iServiceCallback);
    }

    @Override // android.os.IServiceManager
    public void unregisterForNotifications(String str, IServiceCallback iServiceCallback) throws RemoteException {
        throw new RemoteException();
    }

    @Override // android.os.IServiceManager
    public boolean isDeclared(String str) throws RemoteException {
        return this.mServiceManager.isDeclared(str);
    }

    @Override // android.os.IServiceManager
    public String[] getDeclaredInstances(String str) throws RemoteException {
        return this.mServiceManager.getDeclaredInstances(str);
    }

    @Override // android.os.IServiceManager
    public String updatableViaApex(String str) throws RemoteException {
        return this.mServiceManager.updatableViaApex(str);
    }

    @Override // android.os.IServiceManager
    public String[] getUpdatableNames(String str) throws RemoteException {
        return this.mServiceManager.getUpdatableNames(str);
    }

    @Override // android.os.IServiceManager
    public ConnectionInfo getConnectionInfo(String str) throws RemoteException {
        return this.mServiceManager.getConnectionInfo(str);
    }

    @Override // android.os.IServiceManager
    public void registerClientCallback(String str, IBinder iBinder, IClientCallback iClientCallback) throws RemoteException {
        throw new RemoteException();
    }

    @Override // android.os.IServiceManager
    public void tryUnregisterService(String str, IBinder iBinder) throws RemoteException {
        throw new RemoteException();
    }

    @Override // android.os.IServiceManager
    public ServiceDebugInfo[] getServiceDebugInfo() throws RemoteException {
        return this.mServiceManager.getServiceDebugInfo();
    }
}
