package com.samsung.android.mocca;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.mocca.IInternalServiceBridgeListener;

/* loaded from: classes6.dex */
public interface IInternalServiceBridge extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.mocca.IInternalServiceBridge";

    public static class Default implements IInternalServiceBridge {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.mocca.IInternalServiceBridge
        public void clearAllListeners() throws RemoteException {
        }

        @Override // com.samsung.android.mocca.IInternalServiceBridge
        public Bundle getValue(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.mocca.IInternalServiceBridge
        public boolean isAvailable(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.mocca.IInternalServiceBridge
        public void setListener(String str, IInternalServiceBridgeListener iInternalServiceBridgeListener) throws RemoteException {
        }

        @Override // com.samsung.android.mocca.IInternalServiceBridge
        public void start() throws RemoteException {
        }

        @Override // com.samsung.android.mocca.IInternalServiceBridge
        public void stop() throws RemoteException {
        }
    }

    void clearAllListeners() throws RemoteException;

    Bundle getValue(String str) throws RemoteException;

    boolean isAvailable(String str) throws RemoteException;

    void setListener(String str, IInternalServiceBridgeListener iInternalServiceBridgeListener) throws RemoteException;

    void start() throws RemoteException;

    void stop() throws RemoteException;

    public static abstract class Stub extends Binder implements IInternalServiceBridge {
        static final int TRANSACTION_clearAllListeners = 6;
        static final int TRANSACTION_getValue = 4;
        static final int TRANSACTION_isAvailable = 3;
        static final int TRANSACTION_setListener = 5;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IInternalServiceBridge.DESCRIPTOR);
        }

        public static IInternalServiceBridge asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInternalServiceBridge.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInternalServiceBridge)) {
                return (IInternalServiceBridge) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "start";
                case 2:
                    return "stop";
                case 3:
                    return "isAvailable";
                case 4:
                    return "getValue";
                case 5:
                    return "setListener";
                case 6:
                    return "clearAllListeners";
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
                parcel.enforceInterface(IInternalServiceBridge.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInternalServiceBridge.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    start();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    stop();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAvailable = isAvailable(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAvailable);
                    return true;
                case 4:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle value = getValue(string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(value, 1);
                    return true;
                case 5:
                    String string3 = parcel.readString();
                    IInternalServiceBridgeListener iInternalServiceBridgeListenerAsInterface = IInternalServiceBridgeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(string3, iInternalServiceBridgeListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    clearAllListeners();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IInternalServiceBridge {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInternalServiceBridge.DESCRIPTOR;
            }

            @Override // com.samsung.android.mocca.IInternalServiceBridge
            public void start() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IInternalServiceBridge.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IInternalServiceBridge
            public void stop() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IInternalServiceBridge.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IInternalServiceBridge
            public boolean isAvailable(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IInternalServiceBridge.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IInternalServiceBridge
            public Bundle getValue(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IInternalServiceBridge.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IInternalServiceBridge
            public void setListener(String str, IInternalServiceBridgeListener iInternalServiceBridgeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IInternalServiceBridge.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iInternalServiceBridgeListener);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IInternalServiceBridge
            public void clearAllListeners() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IInternalServiceBridge.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
