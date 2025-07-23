package android.media.tv.extension.scan;

import android.media.tv.extension.scan.ILcnV2ChannelListListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ILcnV2ChannelList extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.ILcnV2ChannelList";

    public static class Default implements ILcnV2ChannelList {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.ILcnV2ChannelList
        public Bundle[] getLcnV2ChannelLists() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.ILcnV2ChannelList
        public int setLcnV2ChannelList(Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.ILcnV2ChannelList
        public int setListener(ILcnV2ChannelListListener iLcnV2ChannelListListener) throws RemoteException {
            return 0;
        }
    }

    Bundle[] getLcnV2ChannelLists() throws RemoteException;

    int setLcnV2ChannelList(Bundle bundle) throws RemoteException;

    int setListener(ILcnV2ChannelListListener iLcnV2ChannelListListener) throws RemoteException;

    public static abstract class Stub extends Binder implements ILcnV2ChannelList {
        static final int TRANSACTION_getLcnV2ChannelLists = 1;
        static final int TRANSACTION_setLcnV2ChannelList = 2;
        static final int TRANSACTION_setListener = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.ILcnV2ChannelList");
        }

        public static ILcnV2ChannelList asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.ILcnV2ChannelList");
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILcnV2ChannelList)) {
                return (ILcnV2ChannelList) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getLcnV2ChannelLists";
            }
            if (i == 2) {
                return "setLcnV2ChannelList";
            }
            if (i != 3) {
                return null;
            }
            return "setListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.scan.ILcnV2ChannelList");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.ILcnV2ChannelList");
                return true;
            }
            if (i == 1) {
                Bundle[] lcnV2ChannelLists = getLcnV2ChannelLists();
                parcel2.writeNoException();
                parcel2.writeTypedArray(lcnV2ChannelLists, 1);
            } else if (i == 2) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                int lcnV2ChannelList = setLcnV2ChannelList(bundle);
                parcel2.writeNoException();
                parcel2.writeInt(lcnV2ChannelList);
            } else if (i == 3) {
                ILcnV2ChannelListListener asInterface = ILcnV2ChannelListListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int listener = setListener(asInterface);
                parcel2.writeNoException();
                parcel2.writeInt(listener);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ILcnV2ChannelList {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.ILcnV2ChannelList";
            }

            @Override // android.media.tv.extension.scan.ILcnV2ChannelList
            public Bundle[] getLcnV2ChannelLists() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.ILcnV2ChannelList");
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle[]) obtain2.createTypedArray(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.ILcnV2ChannelList
            public int setLcnV2ChannelList(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.ILcnV2ChannelList");
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.ILcnV2ChannelList
            public int setListener(ILcnV2ChannelListListener iLcnV2ChannelListListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.ILcnV2ChannelList");
                    obtain.writeStrongInterface(iLcnV2ChannelListListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
