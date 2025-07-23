package android.print;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public interface IWriteResultCallback extends IInterface {

    public static class Default implements IWriteResultCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.print.IWriteResultCallback
        public void onWriteCanceled(int i) throws RemoteException {
        }

        @Override // android.print.IWriteResultCallback
        public void onWriteFailed(CharSequence charSequence, int i) throws RemoteException {
        }

        @Override // android.print.IWriteResultCallback
        public void onWriteFinished(PageRange[] pageRangeArr, int i) throws RemoteException {
        }

        @Override // android.print.IWriteResultCallback
        public void onWriteStarted(ICancellationSignal iCancellationSignal, int i) throws RemoteException {
        }
    }

    void onWriteCanceled(int i) throws RemoteException;

    void onWriteFailed(CharSequence charSequence, int i) throws RemoteException;

    void onWriteFinished(PageRange[] pageRangeArr, int i) throws RemoteException;

    void onWriteStarted(ICancellationSignal iCancellationSignal, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IWriteResultCallback {
        public static final String DESCRIPTOR = "android.print.IWriteResultCallback";
        static final int TRANSACTION_onWriteCanceled = 4;
        static final int TRANSACTION_onWriteFailed = 3;
        static final int TRANSACTION_onWriteFinished = 2;
        static final int TRANSACTION_onWriteStarted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWriteResultCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWriteResultCallback)) {
                return (IWriteResultCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onWriteStarted";
            }
            if (i == 2) {
                return "onWriteFinished";
            }
            if (i == 3) {
                return "onWriteFailed";
            }
            if (i != 4) {
                return null;
            }
            return "onWriteCanceled";
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
                ICancellationSignal asInterface = ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onWriteStarted(asInterface, readInt);
            } else if (i == 2) {
                PageRange[] pageRangeArr = (PageRange[]) parcel.createTypedArray(PageRange.CREATOR);
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onWriteFinished(pageRangeArr, readInt2);
            } else if (i == 3) {
                CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onWriteFailed(charSequence, readInt3);
            } else if (i == 4) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onWriteCanceled(readInt4);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IWriteResultCallback {
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

            @Override // android.print.IWriteResultCallback
            public void onWriteStarted(ICancellationSignal iCancellationSignal, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCancellationSignal);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IWriteResultCallback
            public void onWriteFinished(PageRange[] pageRangeArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(pageRangeArr, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IWriteResultCallback
            public void onWriteFailed(CharSequence charSequence, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IWriteResultCallback
            public void onWriteCanceled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
