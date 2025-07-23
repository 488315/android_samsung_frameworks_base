package com.samsung.android.knox.dar.ddar.proxy;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IProxyService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.dar.ddar.proxy.IProxyService";

    public static class Default implements IProxyService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public void deregisterAgent(String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public boolean registerAgentByAction(String str, int i, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public boolean registerAgentByMetadata(String str, int i, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public Bundle relay(String str, String str2, String str3, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public Bundle relayAsync(String str, String str2, String str3, Bundle bundle) throws RemoteException {
            return null;
        }
    }

    void deregisterAgent(String str) throws RemoteException;

    boolean registerAgentByAction(String str, int i, String str2, String str3) throws RemoteException;

    boolean registerAgentByMetadata(String str, int i, String str2, String str3) throws RemoteException;

    Bundle relay(String str, String str2, String str3, Bundle bundle) throws RemoteException;

    Bundle relayAsync(String str, String str2, String str3, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IProxyService {
        static final int TRANSACTION_deregisterAgent = 3;
        static final int TRANSACTION_registerAgentByAction = 1;
        static final int TRANSACTION_registerAgentByMetadata = 2;
        static final int TRANSACTION_relay = 4;
        static final int TRANSACTION_relayAsync = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IProxyService.DESCRIPTOR);
        }

        public static IProxyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProxyService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProxyService)) {
                return (IProxyService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerAgentByAction";
            }
            if (i == 2) {
                return "registerAgentByMetadata";
            }
            if (i == 3) {
                return "deregisterAgent";
            }
            if (i == 4) {
                return "relay";
            }
            if (i != 5) {
                return null;
            }
            return "relayAsync";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProxyService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProxyService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                String readString2 = parcel.readString();
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean registerAgentByAction = registerAgentByAction(readString, readInt, readString2, readString3);
                parcel2.writeNoException();
                parcel2.writeBoolean(registerAgentByAction);
            } else if (i == 2) {
                String readString4 = parcel.readString();
                int readInt2 = parcel.readInt();
                String readString5 = parcel.readString();
                String readString6 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean registerAgentByMetadata = registerAgentByMetadata(readString4, readInt2, readString5, readString6);
                parcel2.writeNoException();
                parcel2.writeBoolean(registerAgentByMetadata);
            } else if (i == 3) {
                String readString7 = parcel.readString();
                parcel.enforceNoDataAvail();
                deregisterAgent(readString7);
                parcel2.writeNoException();
            } else if (i == 4) {
                String readString8 = parcel.readString();
                String readString9 = parcel.readString();
                String readString10 = parcel.readString();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                Bundle relay = relay(readString8, readString9, readString10, bundle);
                parcel2.writeNoException();
                parcel2.writeTypedObject(relay, 1);
            } else if (i == 5) {
                String readString11 = parcel.readString();
                String readString12 = parcel.readString();
                String readString13 = parcel.readString();
                Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                Bundle relayAsync = relayAsync(readString11, readString12, readString13, bundle2);
                parcel2.writeNoException();
                parcel2.writeTypedObject(relayAsync, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IProxyService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProxyService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public boolean registerAgentByAction(String str, int i, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public boolean registerAgentByMetadata(String str, int i, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public void deregisterAgent(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public Bundle relay(String str, String str2, String str3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public Bundle relayAsync(String str, String str2, String str3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
