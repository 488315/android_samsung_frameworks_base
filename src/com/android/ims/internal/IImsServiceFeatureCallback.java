package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.ImsFeatureContainer;

/* loaded from: classes5.dex */
public interface IImsServiceFeatureCallback extends IInterface {

    public static class Default implements IImsServiceFeatureCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.IImsServiceFeatureCallback
        public void imsFeatureCreated(ImsFeatureContainer imsFeatureContainer, int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsServiceFeatureCallback
        public void imsFeatureRemoved(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsServiceFeatureCallback
        public void imsStatusChanged(int i, int i2) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsServiceFeatureCallback
        public void updateCapabilities(long j) throws RemoteException {
        }
    }

    void imsFeatureCreated(ImsFeatureContainer imsFeatureContainer, int i) throws RemoteException;

    void imsFeatureRemoved(int i) throws RemoteException;

    void imsStatusChanged(int i, int i2) throws RemoteException;

    void updateCapabilities(long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsServiceFeatureCallback {
        public static final String DESCRIPTOR = "com.android.ims.internal.IImsServiceFeatureCallback";
        static final int TRANSACTION_imsFeatureCreated = 1;
        static final int TRANSACTION_imsFeatureRemoved = 2;
        static final int TRANSACTION_imsStatusChanged = 3;
        static final int TRANSACTION_updateCapabilities = 4;

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

        public static IImsServiceFeatureCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsServiceFeatureCallback)) {
                return (IImsServiceFeatureCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "imsFeatureCreated";
            }
            if (i == 2) {
                return "imsFeatureRemoved";
            }
            if (i == 3) {
                return "imsStatusChanged";
            }
            if (i != 4) {
                return null;
            }
            return "updateCapabilities";
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
                ImsFeatureContainer imsFeatureContainer = (ImsFeatureContainer) parcel.readTypedObject(ImsFeatureContainer.CREATOR);
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                imsFeatureCreated(imsFeatureContainer, readInt);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                imsFeatureRemoved(readInt2);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                imsStatusChanged(readInt3, readInt4);
            } else if (i == 4) {
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                updateCapabilities(readLong);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IImsServiceFeatureCallback {
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

            @Override // com.android.ims.internal.IImsServiceFeatureCallback
            public void imsFeatureCreated(ImsFeatureContainer imsFeatureContainer, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsFeatureContainer, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsServiceFeatureCallback
            public void imsFeatureRemoved(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsServiceFeatureCallback
            public void imsStatusChanged(int i, int i2) throws RemoteException {
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

            @Override // com.android.ims.internal.IImsServiceFeatureCallback
            public void updateCapabilities(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
