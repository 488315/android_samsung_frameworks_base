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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.IRegionChannelList");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRegionChannelList)) {
                return (IRegionChannelList) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                int regionChannelList = setRegionChannelList(string);
                parcel2.writeNoException();
                parcel2.writeInt(regionChannelList);
            } else if (i == 2) {
                IRegionChannelListListener iRegionChannelListListenerAsInterface = IRegionChannelListListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int listener = setListener(iRegionChannelListListenerAsInterface);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IRegionChannelList");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IRegionChannelList
            public int setListener(IRegionChannelListListener iRegionChannelListListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IRegionChannelList");
                    parcelObtain.writeStrongInterface(iRegionChannelListListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
