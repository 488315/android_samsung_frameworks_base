package android.print;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public interface ILayoutResultCallback extends IInterface {

    public static class Default implements ILayoutResultCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.print.ILayoutResultCallback
        public void onLayoutCanceled(int i) throws RemoteException {
        }

        @Override // android.print.ILayoutResultCallback
        public void onLayoutFailed(CharSequence charSequence, int i) throws RemoteException {
        }

        @Override // android.print.ILayoutResultCallback
        public void onLayoutFinished(PrintDocumentInfo printDocumentInfo, boolean z, int i) throws RemoteException {
        }

        @Override // android.print.ILayoutResultCallback
        public void onLayoutStarted(ICancellationSignal iCancellationSignal, int i) throws RemoteException {
        }
    }

    void onLayoutCanceled(int i) throws RemoteException;

    void onLayoutFailed(CharSequence charSequence, int i) throws RemoteException;

    void onLayoutFinished(PrintDocumentInfo printDocumentInfo, boolean z, int i) throws RemoteException;

    void onLayoutStarted(ICancellationSignal iCancellationSignal, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ILayoutResultCallback {
        public static final String DESCRIPTOR = "android.print.ILayoutResultCallback";
        static final int TRANSACTION_onLayoutCanceled = 4;
        static final int TRANSACTION_onLayoutFailed = 3;
        static final int TRANSACTION_onLayoutFinished = 2;
        static final int TRANSACTION_onLayoutStarted = 1;

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

        public static ILayoutResultCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILayoutResultCallback)) {
                return (ILayoutResultCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onLayoutStarted";
            }
            if (i == 2) {
                return "onLayoutFinished";
            }
            if (i == 3) {
                return "onLayoutFailed";
            }
            if (i != 4) {
                return null;
            }
            return "onLayoutCanceled";
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
                onLayoutStarted(asInterface, readInt);
            } else if (i == 2) {
                PrintDocumentInfo printDocumentInfo = (PrintDocumentInfo) parcel.readTypedObject(PrintDocumentInfo.CREATOR);
                boolean readBoolean = parcel.readBoolean();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onLayoutFinished(printDocumentInfo, readBoolean, readInt2);
            } else if (i == 3) {
                CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onLayoutFailed(charSequence, readInt3);
            } else if (i == 4) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onLayoutCanceled(readInt4);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ILayoutResultCallback {
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

            @Override // android.print.ILayoutResultCallback
            public void onLayoutStarted(ICancellationSignal iCancellationSignal, int i) throws RemoteException {
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

            @Override // android.print.ILayoutResultCallback
            public void onLayoutFinished(PrintDocumentInfo printDocumentInfo, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printDocumentInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.ILayoutResultCallback
            public void onLayoutFailed(CharSequence charSequence, int i) throws RemoteException {
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

            @Override // android.print.ILayoutResultCallback
            public void onLayoutCanceled(int i) throws RemoteException {
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
