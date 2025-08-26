package com.samsung.android.knox.dar.sdp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISdpListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.dar.sdp.ISdpListener";

    public static class Default implements ISdpListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.dar.sdp.ISdpListener
        public void onEngineRemoved() throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.sdp.ISdpListener
        public void onStateChange(int i) throws RemoteException {
        }
    }

    void onEngineRemoved() throws RemoteException;

    void onStateChange(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISdpListener {
        static final int TRANSACTION_onEngineRemoved = 2;
        static final int TRANSACTION_onStateChange = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISdpListener.DESCRIPTOR);
        }

        public static ISdpListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISdpListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISdpListener)) {
                return (ISdpListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStateChange";
            }
            if (i != 2) {
                return null;
            }
            return "onEngineRemoved";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISdpListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISdpListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStateChange(i3);
                parcel2.writeNoException();
            } else if (i == 2) {
                onEngineRemoved();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISdpListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISdpListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.dar.sdp.ISdpListener
            public void onStateChange(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISdpListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.sdp.ISdpListener
            public void onEngineRemoved() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISdpListener.DESCRIPTOR);
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
