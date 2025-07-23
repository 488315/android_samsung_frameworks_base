package android.view.contentcapture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IDataShareWriteAdapter extends IInterface {
    public static final String DESCRIPTOR = "android.view.contentcapture.IDataShareWriteAdapter";

    public static class Default implements IDataShareWriteAdapter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void error(int i) throws RemoteException {
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void finish() throws RemoteException {
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void rejected() throws RemoteException {
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void write(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }
    }

    void error(int i) throws RemoteException;

    void finish() throws RemoteException;

    void rejected() throws RemoteException;

    void write(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    public static abstract class Stub extends Binder implements IDataShareWriteAdapter {
        static final int TRANSACTION_error = 2;
        static final int TRANSACTION_finish = 4;
        static final int TRANSACTION_rejected = 3;
        static final int TRANSACTION_write = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IDataShareWriteAdapter.DESCRIPTOR);
        }

        public static IDataShareWriteAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDataShareWriteAdapter.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDataShareWriteAdapter)) {
                return (IDataShareWriteAdapter) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "write";
            }
            if (i == 2) {
                return "error";
            }
            if (i == 3) {
                return "rejected";
            }
            if (i != 4) {
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
                parcel.enforceInterface(IDataShareWriteAdapter.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDataShareWriteAdapter.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                parcel.enforceNoDataAvail();
                write(parcelFileDescriptor);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                error(readInt);
            } else if (i == 3) {
                rejected();
            } else if (i == 4) {
                finish();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDataShareWriteAdapter {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDataShareWriteAdapter.DESCRIPTOR;
            }

            @Override // android.view.contentcapture.IDataShareWriteAdapter
            public void write(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDataShareWriteAdapter.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IDataShareWriteAdapter
            public void error(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDataShareWriteAdapter.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IDataShareWriteAdapter
            public void rejected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDataShareWriteAdapter.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IDataShareWriteAdapter
            public void finish() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDataShareWriteAdapter.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
