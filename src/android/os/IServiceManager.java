package android.os;

import android.os.IClientCallback;
import android.os.IServiceCallback;

/* loaded from: classes3.dex */
public interface IServiceManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.IServiceManager";
    public static final int DUMP_FLAG_PRIORITY_ALL = 15;
    public static final int DUMP_FLAG_PRIORITY_CRITICAL = 1;
    public static final int DUMP_FLAG_PRIORITY_DEFAULT = 8;
    public static final int DUMP_FLAG_PRIORITY_HIGH = 2;
    public static final int DUMP_FLAG_PRIORITY_NORMAL = 4;
    public static final int DUMP_FLAG_PROTO = 16;
    public static final int FLAG_IS_LAZY_SERVICE = 1073741824;

    public static class Default implements IServiceManager {
        @Override // android.os.IServiceManager
        public void addService(String str, IBinder iBinder, boolean z, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IServiceManager
        public IBinder checkService(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public Service checkService2(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public ConnectionInfo getConnectionInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public String[] getDeclaredInstances(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public IBinder getService(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public Service getService2(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public ServiceDebugInfo[] getServiceDebugInfo() throws RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public String[] getUpdatableNames(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public boolean isDeclared(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IServiceManager
        public String[] listServices(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IServiceManager
        public void registerClientCallback(String str, IBinder iBinder, IClientCallback iClientCallback) throws RemoteException {
        }

        @Override // android.os.IServiceManager
        public void registerForNotifications(String str, IServiceCallback iServiceCallback) throws RemoteException {
        }

        @Override // android.os.IServiceManager
        public void tryUnregisterService(String str, IBinder iBinder) throws RemoteException {
        }

        @Override // android.os.IServiceManager
        public void unregisterForNotifications(String str, IServiceCallback iServiceCallback) throws RemoteException {
        }

        @Override // android.os.IServiceManager
        public String updatableViaApex(String str) throws RemoteException {
            return null;
        }
    }

    void addService(String str, IBinder iBinder, boolean z, int i) throws RemoteException;

    @Deprecated
    IBinder checkService(String str) throws RemoteException;

    Service checkService2(String str) throws RemoteException;

    ConnectionInfo getConnectionInfo(String str) throws RemoteException;

    String[] getDeclaredInstances(String str) throws RemoteException;

    @Deprecated
    IBinder getService(String str) throws RemoteException;

    Service getService2(String str) throws RemoteException;

    ServiceDebugInfo[] getServiceDebugInfo() throws RemoteException;

    String[] getUpdatableNames(String str) throws RemoteException;

    boolean isDeclared(String str) throws RemoteException;

    String[] listServices(int i) throws RemoteException;

    void registerClientCallback(String str, IBinder iBinder, IClientCallback iClientCallback) throws RemoteException;

    void registerForNotifications(String str, IServiceCallback iServiceCallback) throws RemoteException;

    void tryUnregisterService(String str, IBinder iBinder) throws RemoteException;

    void unregisterForNotifications(String str, IServiceCallback iServiceCallback) throws RemoteException;

    String updatableViaApex(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IServiceManager {
        static final int TRANSACTION_addService = 5;
        static final int TRANSACTION_checkService = 3;
        static final int TRANSACTION_checkService2 = 4;
        static final int TRANSACTION_getConnectionInfo = 13;
        static final int TRANSACTION_getDeclaredInstances = 10;
        static final int TRANSACTION_getService = 1;
        static final int TRANSACTION_getService2 = 2;
        static final int TRANSACTION_getServiceDebugInfo = 16;
        static final int TRANSACTION_getUpdatableNames = 12;
        static final int TRANSACTION_isDeclared = 9;
        static final int TRANSACTION_listServices = 6;
        static final int TRANSACTION_registerClientCallback = 14;
        static final int TRANSACTION_registerForNotifications = 7;
        static final int TRANSACTION_tryUnregisterService = 15;
        static final int TRANSACTION_unregisterForNotifications = 8;
        static final int TRANSACTION_updatableViaApex = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }

        public Stub() {
            attachInterface(this, IServiceManager.DESCRIPTOR);
        }

        public static IServiceManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IServiceManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IServiceManager)) {
                return (IServiceManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getService";
                case 2:
                    return "getService2";
                case 3:
                    return "checkService";
                case 4:
                    return "checkService2";
                case 5:
                    return "addService";
                case 6:
                    return "listServices";
                case 7:
                    return "registerForNotifications";
                case 8:
                    return "unregisterForNotifications";
                case 9:
                    return "isDeclared";
                case 10:
                    return "getDeclaredInstances";
                case 11:
                    return "updatableViaApex";
                case 12:
                    return "getUpdatableNames";
                case 13:
                    return "getConnectionInfo";
                case 14:
                    return "registerClientCallback";
                case 15:
                    return "tryUnregisterService";
                case 16:
                    return "getServiceDebugInfo";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IServiceManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IServiceManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBinder service = getService(readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(service);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Service service2 = getService2(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(service2, 1);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBinder checkService = checkService(readString3);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(checkService);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Service checkService2 = checkService2(readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(checkService2, 1);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addService(readString5, readStrongBinder, readBoolean, readInt);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] listServices = listServices(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listServices);
                    return true;
                case 7:
                    String readString6 = parcel.readString();
                    IServiceCallback asInterface = IServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForNotifications(readString6, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString7 = parcel.readString();
                    IServiceCallback asInterface2 = IServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForNotifications(readString7, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isDeclared = isDeclared(readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeclared);
                    return true;
                case 10:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] declaredInstances = getDeclaredInstances(readString9);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(declaredInstances);
                    return true;
                case 11:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String updatableViaApex = updatableViaApex(readString10);
                    parcel2.writeNoException();
                    parcel2.writeString(updatableViaApex);
                    return true;
                case 12:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] updatableNames = getUpdatableNames(readString11);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(updatableNames);
                    return true;
                case 13:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ConnectionInfo connectionInfo = getConnectionInfo(readString12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(connectionInfo, 1);
                    return true;
                case 14:
                    String readString13 = parcel.readString();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    IClientCallback asInterface3 = IClientCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerClientCallback(readString13, readStrongBinder2, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString14 = parcel.readString();
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    tryUnregisterService(readString14, readStrongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    ServiceDebugInfo[] serviceDebugInfo = getServiceDebugInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(serviceDebugInfo, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IServiceManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IServiceManager.DESCRIPTOR;
            }

            @Override // android.os.IServiceManager
            public IBinder getService(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public Service getService2(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Service) obtain2.readTypedObject(Service.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public IBinder checkService(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public Service checkService2(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Service) obtain2.readTypedObject(Service.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void addService(String str, IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public String[] listServices(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void registerForNotifications(String str, IServiceCallback iServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iServiceCallback);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void unregisterForNotifications(String str, IServiceCallback iServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iServiceCallback);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public boolean isDeclared(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public String[] getDeclaredInstances(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public String updatableViaApex(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public String[] getUpdatableNames(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public ConnectionInfo getConnectionInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ConnectionInfo) obtain2.readTypedObject(ConnectionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void registerClientCallback(String str, IBinder iBinder, IClientCallback iClientCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iClientCallback);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void tryUnregisterService(String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public ServiceDebugInfo[] getServiceDebugInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ServiceDebugInfo[]) obtain2.createTypedArray(ServiceDebugInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
