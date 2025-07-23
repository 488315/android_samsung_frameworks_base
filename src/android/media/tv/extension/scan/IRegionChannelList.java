package android.media.tv.extension.scan;

import android.media.tv.extension.scan.IRegionChannelListListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IRegionChannelList extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.IRegionChannelList";

    public static class Default implements IRegionChannelList {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.IRegionChannelList
        public int setListener(IRegionChannelListListener iRegionChannelListListener) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IRegionChannelList
        public int setRegionChannelList(String str) throws RemoteException {
            return 0;
        }
    }

    int setListener(IRegionChannelListListener iRegionChannelListListener) throws RemoteException;

    int setRegionChannelList(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IRegionChannelList {
        static final int TRANSACTION_setListener = 2;
        static final int TRANSACTION_setRegionChannelList = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.IRegionChannelList");
        }

        public static IRegionChannelList asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.IRegionChannelList");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRegionChannelList)) {
                return (IRegionChannelList) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setRegionChannelList";
            }
            if (i != 2) {
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
                parcel.enforceInterface("android.media.tv.extension.scan.IRegionChannelList");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.IRegionChannelList");
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                int regionChannelList = setRegionChannelList(readString);
                parcel2.writeNoException();
                parcel2.writeInt(regionChannelList);
            } else if (i == 2) {
                IRegionChannelListListener asInterface = IRegionChannelListListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int listener = setListener(asInterface);
                parcel2.writeNoException();
                parcel2.writeInt(listener);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRegionChannelList {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.IRegionChannelList";
            }

            @Override // android.media.tv.extension.scan.IRegionChannelList
            public int setRegionChannelList(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IRegionChannelList");
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IRegionChannelList
            public int setListener(IRegionChannelListListener iRegionChannelListListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IRegionChannelList");
                    obtain.writeStrongInterface(iRegionChannelListListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
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
