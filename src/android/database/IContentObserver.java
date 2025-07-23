package android.database;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IContentObserver extends IInterface {

    public static class Default implements IContentObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.database.IContentObserver
        public void onChange(boolean z, Uri uri, int i) throws RemoteException {
        }

        @Override // android.database.IContentObserver
        public void onChangeEtc(boolean z, Uri[] uriArr, int i, int i2) throws RemoteException {
        }
    }

    void onChange(boolean z, Uri uri, int i) throws RemoteException;

    void onChangeEtc(boolean z, Uri[] uriArr, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IContentObserver {
        public static final String DESCRIPTOR = "android.database.IContentObserver";
        static final int TRANSACTION_onChange = 1;
        static final int TRANSACTION_onChangeEtc = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContentObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContentObserver)) {
                return (IContentObserver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onChange";
            }
            if (i != 2) {
                return null;
            }
            return "onChangeEtc";
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
                boolean readBoolean = parcel.readBoolean();
                Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onChange(readBoolean, uri, readInt);
            } else if (i == 2) {
                boolean readBoolean2 = parcel.readBoolean();
                Uri[] uriArr = (Uri[]) parcel.createTypedArray(Uri.CREATOR);
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onChangeEtc(readBoolean2, uriArr, readInt2, readInt3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IContentObserver {
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

            @Override // android.database.IContentObserver
            public void onChange(boolean z, Uri uri, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.database.IContentObserver
            public void onChangeEtc(boolean z, Uri[] uriArr, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedArray(uriArr, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
