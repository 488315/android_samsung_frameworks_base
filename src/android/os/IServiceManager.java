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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IServiceManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IServiceManager)) {
                return (IServiceManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBinder service = getService(string);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(service);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Service service2 = getService2(string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(service2, 1);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBinder iBinderCheckService = checkService(string3);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(iBinderCheckService);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Service serviceCheckService2 = checkService2(string4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceCheckService2, 1);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    IBinder strongBinder = parcel.readStrongBinder();
                    boolean z = parcel.readBoolean();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addService(string5, strongBinder, z, i3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] strArrListServices = listServices(i4);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrListServices);
                    return true;
                case 7:
                    String string6 = parcel.readString();
                    IServiceCallback iServiceCallbackAsInterface = IServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForNotifications(string6, iServiceCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string7 = parcel.readString();
                    IServiceCallback iServiceCallbackAsInterface2 = IServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForNotifications(string7, iServiceCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsDeclared = isDeclared(string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeclared);
                    return true;
                case 10:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] declaredInstances = getDeclaredInstances(string9);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(declaredInstances);
                    return true;
                case 11:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strUpdatableViaApex = updatableViaApex(string10);
                    parcel2.writeNoException();
                    parcel2.writeString(strUpdatableViaApex);
                    return true;
                case 12:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] updatableNames = getUpdatableNames(string11);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(updatableNames);
                    return true;
                case 13:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ConnectionInfo connectionInfo = getConnectionInfo(string12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(connectionInfo, 1);
                    return true;
                case 14:
                    String string13 = parcel.readString();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    IClientCallback iClientCallbackAsInterface = IClientCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerClientCallback(string13, strongBinder2, iClientCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String string14 = parcel.readString();
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    tryUnregisterService(string14, strongBinder3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public Service getService2(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Service) parcelObtain2.readTypedObject(Service.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public IBinder checkService(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public Service checkService2(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Service) parcelObtain2.readTypedObject(Service.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void addService(String str, IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public String[] listServices(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void registerForNotifications(String str, IServiceCallback iServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iServiceCallback);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void unregisterForNotifications(String str, IServiceCallback iServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iServiceCallback);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public boolean isDeclared(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public String[] getDeclaredInstances(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public String updatableViaApex(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public String[] getUpdatableNames(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public ConnectionInfo getConnectionInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ConnectionInfo) parcelObtain2.readTypedObject(ConnectionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void registerClientCallback(String str, IBinder iBinder, IClientCallback iClientCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iClientCallback);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public void tryUnregisterService(String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IServiceManager
            public ServiceDebugInfo[] getServiceDebugInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceManager.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ServiceDebugInfo[]) parcelObtain2.createTypedArray(ServiceDebugInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
