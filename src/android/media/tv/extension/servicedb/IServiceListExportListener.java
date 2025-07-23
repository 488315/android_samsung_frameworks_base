package android.media.tv.extension.servicedb;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IServiceListExportListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.servicedb.IServiceListExportListener";

    public static class Default implements IServiceListExportListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListExportListener
        public void onExported(int i) throws RemoteException {
        }
    }

    void onExported(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IServiceListExportListener {
        static final int TRANSACTION_onExported = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.servicedb.IServiceListExportListener");
        }

        public static IServiceListExportListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.servicedb.IServiceListExportListener");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IServiceListExportListener)) {
                return (IServiceListExportListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onExported";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.servicedb.IServiceListExportListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.servicedb.IServiceListExportListener");
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onExported(readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IServiceListExportListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.servicedb.IServiceListExportListener";
            }

            @Override // android.media.tv.extension.servicedb.IServiceListExportListener
            public void onExported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListExportListener");
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
