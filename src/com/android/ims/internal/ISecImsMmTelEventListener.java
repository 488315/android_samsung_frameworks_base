package com.android.ims.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ISecImsMmTelEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.ims.internal.ISecImsMmTelEventListener";

    public static class Default implements ISecImsMmTelEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.ISecImsMmTelEventListener
        public void onCdpnInfo(String str, int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.ISecImsMmTelEventListener
        public void onIncomingCall(int i, Bundle bundle) throws RemoteException {
        }
    }

    void onCdpnInfo(String str, int i) throws RemoteException;

    void onIncomingCall(int i, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ISecImsMmTelEventListener {
        static final int TRANSACTION_onCdpnInfo = 2;
        static final int TRANSACTION_onIncomingCall = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISecImsMmTelEventListener.DESCRIPTOR);
        }

        public static ISecImsMmTelEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISecImsMmTelEventListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISecImsMmTelEventListener)) {
                return (ISecImsMmTelEventListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onIncomingCall";
            }
            if (i != 2) {
                return null;
            }
            return "onCdpnInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISecImsMmTelEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISecImsMmTelEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onIncomingCall(readInt, bundle);
                parcel2.writeNoException();
            } else if (i == 2) {
                String readString = parcel.readString();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCdpnInfo(readString, readInt2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISecImsMmTelEventListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISecImsMmTelEventListener.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.ISecImsMmTelEventListener
            public void onIncomingCall(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecImsMmTelEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.ISecImsMmTelEventListener
            public void onCdpnInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecImsMmTelEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
