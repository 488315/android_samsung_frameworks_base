package com.samsung.android.knox.dar.ddar.proxy;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IProxyAgent extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.dar.ddar.proxy.IProxyAgent";

    public static class Default implements IProxyAgent {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
        public String initializeSecureSession(int i, String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
        public void onAgentReconnected() throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
        public Bundle onMessage(int i, String str, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
        public boolean terminateSecureSession(int i, String str, String str2) throws RemoteException {
            return false;
        }
    }

    String initializeSecureSession(int i, String str, String str2, String str3) throws RemoteException;

    void onAgentReconnected() throws RemoteException;

    Bundle onMessage(int i, String str, String str2, Bundle bundle) throws RemoteException;

    boolean terminateSecureSession(int i, String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IProxyAgent {
        static final int TRANSACTION_initializeSecureSession = 2;
        static final int TRANSACTION_onAgentReconnected = 4;
        static final int TRANSACTION_onMessage = 1;
        static final int TRANSACTION_terminateSecureSession = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IProxyAgent.DESCRIPTOR);
        }

        public static IProxyAgent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProxyAgent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProxyAgent)) {
                return (IProxyAgent) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onMessage";
            }
            if (i == 2) {
                return "initializeSecureSession";
            }
            if (i == 3) {
                return "terminateSecureSession";
            }
            if (i != 4) {
                return null;
            }
            return "onAgentReconnected";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProxyAgent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProxyAgent.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                Bundle onMessage = onMessage(readInt, readString, readString2, bundle);
                parcel2.writeNoException();
                parcel2.writeTypedObject(onMessage, 1);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                String readString3 = parcel.readString();
                String readString4 = parcel.readString();
                String readString5 = parcel.readString();
                parcel.enforceNoDataAvail();
                String initializeSecureSession = initializeSecureSession(readInt2, readString3, readString4, readString5);
                parcel2.writeNoException();
                parcel2.writeString(initializeSecureSession);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                String readString6 = parcel.readString();
                String readString7 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean terminateSecureSession = terminateSecureSession(readInt3, readString6, readString7);
                parcel2.writeNoException();
                parcel2.writeBoolean(terminateSecureSession);
            } else if (i == 4) {
                onAgentReconnected();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IProxyAgent {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProxyAgent.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
            public Bundle onMessage(int i, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProxyAgent.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
            public String initializeSecureSession(int i, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProxyAgent.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
            public boolean terminateSecureSession(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProxyAgent.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
            public void onAgentReconnected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProxyAgent.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
