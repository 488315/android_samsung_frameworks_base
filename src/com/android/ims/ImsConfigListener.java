package com.android.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ImsConfigListener extends IInterface {

    public static class Default implements ImsConfigListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.ImsConfigListener
        public void onGetFeatureResponse(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.ims.ImsConfigListener
        public void onGetVideoQuality(int i, int i2) throws RemoteException {
        }

        @Override // com.android.ims.ImsConfigListener
        public void onSetFeatureResponse(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.ims.ImsConfigListener
        public void onSetVideoQuality(int i) throws RemoteException {
        }
    }

    void onGetFeatureResponse(int i, int i2, int i3, int i4) throws RemoteException;

    void onGetVideoQuality(int i, int i2) throws RemoteException;

    void onSetFeatureResponse(int i, int i2, int i3, int i4) throws RemoteException;

    void onSetVideoQuality(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ImsConfigListener {
        public static final String DESCRIPTOR = "com.android.ims.ImsConfigListener";
        static final int TRANSACTION_onGetFeatureResponse = 1;
        static final int TRANSACTION_onGetVideoQuality = 3;
        static final int TRANSACTION_onSetFeatureResponse = 2;
        static final int TRANSACTION_onSetVideoQuality = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ImsConfigListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ImsConfigListener)) {
                return (ImsConfigListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onGetFeatureResponse";
            }
            if (i == 2) {
                return "onSetFeatureResponse";
            }
            if (i == 3) {
                return "onGetVideoQuality";
            }
            if (i != 4) {
                return null;
            }
            return "onSetVideoQuality";
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
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onGetFeatureResponse(readInt, readInt2, readInt3, readInt4);
            } else if (i == 2) {
                int readInt5 = parcel.readInt();
                int readInt6 = parcel.readInt();
                int readInt7 = parcel.readInt();
                int readInt8 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSetFeatureResponse(readInt5, readInt6, readInt7, readInt8);
            } else if (i == 3) {
                int readInt9 = parcel.readInt();
                int readInt10 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onGetVideoQuality(readInt9, readInt10);
            } else if (i == 4) {
                int readInt11 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSetVideoQuality(readInt11);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ImsConfigListener {
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

            @Override // com.android.ims.ImsConfigListener
            public void onGetFeatureResponse(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.ImsConfigListener
            public void onSetFeatureResponse(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.ImsConfigListener
            public void onGetVideoQuality(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.ImsConfigListener
            public void onSetVideoQuality(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
