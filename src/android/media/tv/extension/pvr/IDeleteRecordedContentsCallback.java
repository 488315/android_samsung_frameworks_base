package android.media.tv.extension.pvr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDeleteRecordedContentsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.pvr.IDeleteRecordedContentsCallback";

    public static class Default implements IDeleteRecordedContentsCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.pvr.IDeleteRecordedContentsCallback
        public void onRecordedContentsDeleted(String[] strArr, int[] iArr) throws RemoteException {
        }
    }

    void onRecordedContentsDeleted(String[] strArr, int[] iArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IDeleteRecordedContentsCallback {
        static final int TRANSACTION_onRecordedContentsDeleted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.pvr.IDeleteRecordedContentsCallback");
        }

        public static IDeleteRecordedContentsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.pvr.IDeleteRecordedContentsCallback");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDeleteRecordedContentsCallback)) {
                return (IDeleteRecordedContentsCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onRecordedContentsDeleted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.pvr.IDeleteRecordedContentsCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.pvr.IDeleteRecordedContentsCallback");
                return true;
            }
            if (i == 1) {
                String[] createStringArray = parcel.createStringArray();
                int[] createIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                onRecordedContentsDeleted(createStringArray, createIntArray);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDeleteRecordedContentsCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.pvr.IDeleteRecordedContentsCallback";
            }

            @Override // android.media.tv.extension.pvr.IDeleteRecordedContentsCallback
            public void onRecordedContentsDeleted(String[] strArr, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.pvr.IDeleteRecordedContentsCallback");
                    obtain.writeStringArray(strArr);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
