package android.media.tv.extension.pvr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IGetInfoRecordedContentsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.pvr.IGetInfoRecordedContentsCallback";

    public static class Default implements IGetInfoRecordedContentsCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.pvr.IGetInfoRecordedContentsCallback
        public void onRecordedContentsGetInfo(int i) throws RemoteException {
        }
    }

    void onRecordedContentsGetInfo(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IGetInfoRecordedContentsCallback {
        static final int TRANSACTION_onRecordedContentsGetInfo = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.pvr.IGetInfoRecordedContentsCallback");
        }

        public static IGetInfoRecordedContentsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.pvr.IGetInfoRecordedContentsCallback");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGetInfoRecordedContentsCallback)) {
                return (IGetInfoRecordedContentsCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onRecordedContentsGetInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.pvr.IGetInfoRecordedContentsCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.pvr.IGetInfoRecordedContentsCallback");
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onRecordedContentsGetInfo(readInt);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGetInfoRecordedContentsCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.pvr.IGetInfoRecordedContentsCallback";
            }

            @Override // android.media.tv.extension.pvr.IGetInfoRecordedContentsCallback
            public void onRecordedContentsGetInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.pvr.IGetInfoRecordedContentsCallback");
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
