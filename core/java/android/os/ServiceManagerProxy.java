package android.os;


/* compiled from: ServiceManagerNative.java */
/* loaded from: classes3.dex */
class ServiceManagerProxy implements IServiceManager {
  private IBinder mRemote;
  private IServiceManager mServiceManager;

  public ServiceManagerProxy(IBinder remote) {
    this.mRemote = remote;
    this.mServiceManager = IServiceManager.Stub.asInterface(remote);
  }

  @Override // android.os.IInterface
  public IBinder asBinder() {
    return this.mRemote;
  }

  @Override // android.os.IServiceManager
  public IBinder getService(String name) throws RemoteException {
    return this.mServiceManager.checkService(name);
  }

  @Override // android.os.IServiceManager
  public IBinder checkService(String name) throws RemoteException {
    return this.mServiceManager.checkService(name);
  }

  @Override // android.os.IServiceManager
  public void addService(String name, IBinder service, boolean allowIsolated, int dumpPriority)
      throws RemoteException {
    this.mServiceManager.addService(name, service, allowIsolated, dumpPriority);
  }

  @Override // android.os.IServiceManager
  public String[] listServices(int dumpPriority) throws RemoteException {
    return this.mServiceManager.listServices(dumpPriority);
  }

  @Override // android.os.IServiceManager
  public void registerForNotifications(String name, IServiceCallback cb) throws RemoteException {
    this.mServiceManager.registerForNotifications(name, cb);
  }

  @Override // android.os.IServiceManager
  public void unregisterForNotifications(String name, IServiceCallback cb) throws RemoteException {
    throw new RemoteException();
  }

  @Override // android.os.IServiceManager
  public boolean isDeclared(String name) throws RemoteException {
    return this.mServiceManager.isDeclared(name);
  }

  @Override // android.os.IServiceManager
  public String[] getDeclaredInstances(String iface) throws RemoteException {
    return this.mServiceManager.getDeclaredInstances(iface);
  }

  @Override // android.os.IServiceManager
  public String updatableViaApex(String name) throws RemoteException {
    return this.mServiceManager.updatableViaApex(name);
  }

  @Override // android.os.IServiceManager
  public String[] getUpdatableNames(String apexName) throws RemoteException {
    return this.mServiceManager.getUpdatableNames(apexName);
  }

  @Override // android.os.IServiceManager
  public ConnectionInfo getConnectionInfo(String name) throws RemoteException {
    return this.mServiceManager.getConnectionInfo(name);
  }

  @Override // android.os.IServiceManager
  public void registerClientCallback(String name, IBinder service, IClientCallback cb)
      throws RemoteException {
    throw new RemoteException();
  }

  @Override // android.os.IServiceManager
  public void tryUnregisterService(String name, IBinder service) throws RemoteException {
    throw new RemoteException();
  }

  @Override // android.os.IServiceManager
  public ServiceDebugInfo[] getServiceDebugInfo() throws RemoteException {
    return this.mServiceManager.getServiceDebugInfo();
  }
}
