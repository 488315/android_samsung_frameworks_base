package android.media.tv.extension.servicedb;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IChannelListTransfer extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.servicedb.IChannelListTransfer";

    public static class Default implements IChannelListTransfer {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IChannelListTransfer
        public void exportChannelList(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override // android.media.tv.extension.servicedb.IChannelListTransfer
        public void importChannelList(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }
    }

    void exportChannelList(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void importChannelList(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    public static abstract class Stub extends Binder implements IChannelListTransfer {
        static final int TRANSACTION_exportChannelList = 2;
        static final int TRANSACTION_importChannelList = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.servicedb.IChannelListTransfer");
        }

        public static IChannelListTransfer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.servicedb.IChannelListTransfer");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IChannelListTransfer)) {
                return (IChannelListTransfer) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "importChannelList";
            }
            if (i != 2) {
                return null;
            }
            return "exportChannelList";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.servicedb.IChannelListTransfer");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.servicedb.IChannelListTransfer");
                return true;
            }
            if (i == 1) {
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                parcel.enforceNoDataAvail();
                importChannelList(parcelFileDescriptor);
                parcel2.writeNoException();
            } else if (i == 2) {
                ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                parcel.enforceNoDataAvail();
                exportChannelList(parcelFileDescriptor2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IChannelListTransfer {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.servicedb.IChannelListTransfer";
            }

            @Override // android.media.tv.extension.servicedb.IChannelListTransfer
            public void importChannelList(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IChannelListTransfer");
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IChannelListTransfer
            public void exportChannelList(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IChannelListTransfer");
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
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
