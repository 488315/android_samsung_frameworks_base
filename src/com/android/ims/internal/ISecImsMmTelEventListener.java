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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISecImsMmTelEventListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISecImsMmTelEventListener)) {
                return (ISecImsMmTelEventListener) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onIncomingCall(i3, bundle);
                parcel2.writeNoException();
            } else if (i == 2) {
                String string = parcel.readString();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCdpnInfo(string, i4);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecImsMmTelEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.ISecImsMmTelEventListener
            public void onCdpnInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecImsMmTelEventListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
