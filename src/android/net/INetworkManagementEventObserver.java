package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface INetworkManagementEventObserver extends IInterface {

    public static class Default implements INetworkManagementEventObserver {
        @Override // android.net.INetworkManagementEventObserver
        public void addressRemoved(String str, LinkAddress linkAddress) throws RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void addressUpdated(String str, LinkAddress linkAddress) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.INetworkManagementEventObserver
        public void interfaceAdded(String str) throws RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void interfaceClassDataActivityChanged(int i, boolean z, long j, int i2) throws RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void interfaceDnsServerInfo(String str, long j, String[] strArr) throws RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void interfaceLinkStateChanged(String str, boolean z) throws RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void interfaceRemoved(String str) throws RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void interfaceStatusChanged(String str, boolean z) throws RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void limitReached(String str, String str2) throws RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void routeRemoved(RouteInfo routeInfo) throws RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void routeUpdated(RouteInfo routeInfo) throws RemoteException {
        }
    }

    void addressRemoved(String str, LinkAddress linkAddress) throws RemoteException;

    void addressUpdated(String str, LinkAddress linkAddress) throws RemoteException;

    void interfaceAdded(String str) throws RemoteException;

    void interfaceClassDataActivityChanged(int i, boolean z, long j, int i2) throws RemoteException;

    void interfaceDnsServerInfo(String str, long j, String[] strArr) throws RemoteException;

    void interfaceLinkStateChanged(String str, boolean z) throws RemoteException;

    void interfaceRemoved(String str) throws RemoteException;

    void interfaceStatusChanged(String str, boolean z) throws RemoteException;

    void limitReached(String str, String str2) throws RemoteException;

    void routeRemoved(RouteInfo routeInfo) throws RemoteException;

    void routeUpdated(RouteInfo routeInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements INetworkManagementEventObserver {
        public static final String DESCRIPTOR = "android.net.INetworkManagementEventObserver";
        static final int TRANSACTION_addressRemoved = 6;
        static final int TRANSACTION_addressUpdated = 5;
        static final int TRANSACTION_interfaceAdded = 3;
        static final int TRANSACTION_interfaceClassDataActivityChanged = 8;
        static final int TRANSACTION_interfaceDnsServerInfo = 9;
        static final int TRANSACTION_interfaceLinkStateChanged = 2;
        static final int TRANSACTION_interfaceRemoved = 4;
        static final int TRANSACTION_interfaceStatusChanged = 1;
        static final int TRANSACTION_limitReached = 7;
        static final int TRANSACTION_routeRemoved = 11;
        static final int TRANSACTION_routeUpdated = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkManagementEventObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INetworkManagementEventObserver)) {
                return (INetworkManagementEventObserver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "interfaceStatusChanged";
                case 2:
                    return "interfaceLinkStateChanged";
                case 3:
                    return "interfaceAdded";
                case 4:
                    return "interfaceRemoved";
                case 5:
                    return "addressUpdated";
                case 6:
                    return "addressRemoved";
                case 7:
                    return "limitReached";
                case 8:
                    return "interfaceClassDataActivityChanged";
                case 9:
                    return "interfaceDnsServerInfo";
                case 10:
                    return "routeUpdated";
                case 11:
                    return "routeRemoved";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    interfaceStatusChanged(readString, readBoolean);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    interfaceLinkStateChanged(readString2, readBoolean2);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    interfaceAdded(readString3);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    interfaceRemoved(readString4);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    LinkAddress linkAddress = (LinkAddress) parcel.readTypedObject(LinkAddress.CREATOR);
                    parcel.enforceNoDataAvail();
                    addressUpdated(readString5, linkAddress);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    LinkAddress linkAddress2 = (LinkAddress) parcel.readTypedObject(LinkAddress.CREATOR);
                    parcel.enforceNoDataAvail();
                    addressRemoved(readString6, linkAddress2);
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    limitReached(readString7, readString8);
                    return true;
                case 8:
                    int readInt = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    interfaceClassDataActivityChanged(readInt, readBoolean3, readLong, readInt2);
                    return true;
                case 9:
                    String readString9 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    interfaceDnsServerInfo(readString9, readLong2, createStringArray);
                    return true;
                case 10:
                    RouteInfo routeInfo = (RouteInfo) parcel.readTypedObject(RouteInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    routeUpdated(routeInfo);
                    return true;
                case 11:
                    RouteInfo routeInfo2 = (RouteInfo) parcel.readTypedObject(RouteInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    routeRemoved(routeInfo2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements INetworkManagementEventObserver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.net.INetworkManagementEventObserver
            public void interfaceStatusChanged(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void interfaceLinkStateChanged(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void interfaceAdded(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void interfaceRemoved(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void addressUpdated(String str, LinkAddress linkAddress) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(linkAddress, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void addressRemoved(String str, LinkAddress linkAddress) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(linkAddress, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void limitReached(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void interfaceClassDataActivityChanged(int i, boolean z, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void interfaceDnsServerInfo(String str, long j, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void routeUpdated(RouteInfo routeInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(routeInfo, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void routeRemoved(RouteInfo routeInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(routeInfo, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
