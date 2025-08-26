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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IProxyAgent.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IProxyAgent)) {
                return (IProxyAgent) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                String string = parcel.readString();
                String string2 = parcel.readString();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                Bundle bundleOnMessage = onMessage(i3, string, string2, bundle);
                parcel2.writeNoException();
                parcel2.writeTypedObject(bundleOnMessage, 1);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                String string3 = parcel.readString();
                String string4 = parcel.readString();
                String string5 = parcel.readString();
                parcel.enforceNoDataAvail();
                String strInitializeSecureSession = initializeSecureSession(i4, string3, string4, string5);
                parcel2.writeNoException();
                parcel2.writeString(strInitializeSecureSession);
            } else if (i == 3) {
                int i5 = parcel.readInt();
                String string6 = parcel.readString();
                String string7 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean zTerminateSecureSession = terminateSecureSession(i5, string6, string7);
                parcel2.writeNoException();
                parcel2.writeBoolean(zTerminateSecureSession);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProxyAgent.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
            public String initializeSecureSession(int i, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProxyAgent.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
            public boolean terminateSecureSession(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProxyAgent.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
            public void onAgentReconnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProxyAgent.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
