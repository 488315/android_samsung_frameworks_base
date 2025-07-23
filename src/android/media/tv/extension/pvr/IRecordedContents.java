package android.media.tv.extension.pvr;

import android.media.tv.extension.pvr.IDeleteRecordedContentsCallback;
import android.media.tv.extension.pvr.IGetInfoRecordedContentsCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IRecordedContents extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.pvr.IRecordedContents";

    public static class Default implements IRecordedContents {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.pvr.IRecordedContents
        public void deleteRecordedContents(String[] strArr, IDeleteRecordedContentsCallback iDeleteRecordedContentsCallback) throws RemoteException {
        }

        @Override // android.media.tv.extension.pvr.IRecordedContents
        public void getRecordedContentsLockInfoAsync(String str, IGetInfoRecordedContentsCallback iGetInfoRecordedContentsCallback) throws RemoteException {
        }

        @Override // android.media.tv.extension.pvr.IRecordedContents
        public int getRecordedContentsLockInfoSync(String str) throws RemoteException {
            return 0;
        }
    }

    void deleteRecordedContents(String[] strArr, IDeleteRecordedContentsCallback iDeleteRecordedContentsCallback) throws RemoteException;

    void getRecordedContentsLockInfoAsync(String str, IGetInfoRecordedContentsCallback iGetInfoRecordedContentsCallback) throws RemoteException;

    int getRecordedContentsLockInfoSync(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IRecordedContents {
        static final int TRANSACTION_deleteRecordedContents = 1;
        static final int TRANSACTION_getRecordedContentsLockInfoAsync = 3;
        static final int TRANSACTION_getRecordedContentsLockInfoSync = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.pvr.IRecordedContents");
        }

        public static IRecordedContents asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.pvr.IRecordedContents");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRecordedContents)) {
                return (IRecordedContents) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "deleteRecordedContents";
            }
            if (i == 2) {
                return "getRecordedContentsLockInfoSync";
            }
            if (i != 3) {
                return null;
            }
            return "getRecordedContentsLockInfoAsync";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.pvr.IRecordedContents");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.pvr.IRecordedContents");
                return true;
            }
            if (i == 1) {
                String[] createStringArray = parcel.createStringArray();
                IDeleteRecordedContentsCallback asInterface = IDeleteRecordedContentsCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                deleteRecordedContents(createStringArray, asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                int recordedContentsLockInfoSync = getRecordedContentsLockInfoSync(readString);
                parcel2.writeNoException();
                parcel2.writeInt(recordedContentsLockInfoSync);
            } else if (i == 3) {
                String readString2 = parcel.readString();
                IGetInfoRecordedContentsCallback asInterface2 = IGetInfoRecordedContentsCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getRecordedContentsLockInfoAsync(readString2, asInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRecordedContents {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.pvr.IRecordedContents";
            }

            @Override // android.media.tv.extension.pvr.IRecordedContents
            public void deleteRecordedContents(String[] strArr, IDeleteRecordedContentsCallback iDeleteRecordedContentsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.pvr.IRecordedContents");
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongInterface(iDeleteRecordedContentsCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.pvr.IRecordedContents
            public int getRecordedContentsLockInfoSync(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.pvr.IRecordedContents");
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.pvr.IRecordedContents
            public void getRecordedContentsLockInfoAsync(String str, IGetInfoRecordedContentsCallback iGetInfoRecordedContentsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.pvr.IRecordedContents");
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iGetInfoRecordedContentsCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
