package android.service.contentcapture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDataShareReadAdapter extends IInterface {
    public static final String DESCRIPTOR = "android.service.contentcapture.IDataShareReadAdapter";

    public static class Default implements IDataShareReadAdapter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.contentcapture.IDataShareReadAdapter
        public void error(int i) throws RemoteException {
        }

        @Override // android.service.contentcapture.IDataShareReadAdapter
        public void finish() throws RemoteException {
        }

        @Override // android.service.contentcapture.IDataShareReadAdapter
        public void start(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }
    }

    void error(int i) throws RemoteException;

    void finish() throws RemoteException;

    void start(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    public static abstract class Stub extends Binder implements IDataShareReadAdapter {
        static final int TRANSACTION_error = 2;
        static final int TRANSACTION_finish = 3;
        static final int TRANSACTION_start = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IDataShareReadAdapter.DESCRIPTOR);
        }

        public static IDataShareReadAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDataShareReadAdapter.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDataShareReadAdapter)) {
                return (IDataShareReadAdapter) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "start";
            }
            if (i == 2) {
                return "error";
            }
            if (i != 3) {
                return null;
            }
            return "finish";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDataShareReadAdapter.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDataShareReadAdapter.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                parcel.enforceNoDataAvail();
                start(parcelFileDescriptor);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                error(readInt);
            } else if (i == 3) {
                finish();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDataShareReadAdapter {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDataShareReadAdapter.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IDataShareReadAdapter
            public void start(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDataShareReadAdapter.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IDataShareReadAdapter
            public void error(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDataShareReadAdapter.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IDataShareReadAdapter
            public void finish() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDataShareReadAdapter.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
