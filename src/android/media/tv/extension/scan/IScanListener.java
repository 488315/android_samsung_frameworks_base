package android.media.tv.extension.scan;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IScanListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.IScanListener";

    public static class Default implements IScanListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanListener
        public void onEvent(Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.extension.scan.IScanListener
        public void onScanCompleted(int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.extension.scan.IScanListener
        public void onScanProgress(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.extension.scan.IScanListener
        public void onStoreCompleted(int i) throws RemoteException {
        }
    }

    void onEvent(Bundle bundle) throws RemoteException;

    void onScanCompleted(int i, Bundle bundle) throws RemoteException;

    void onScanProgress(String str, Bundle bundle) throws RemoteException;

    void onStoreCompleted(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IScanListener {
        static final int TRANSACTION_onEvent = 1;
        static final int TRANSACTION_onScanCompleted = 3;
        static final int TRANSACTION_onScanProgress = 2;
        static final int TRANSACTION_onStoreCompleted = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.IScanListener");
        }

        public static IScanListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.IScanListener");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IScanListener)) {
                return (IScanListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onEvent";
            }
            if (i == 2) {
                return "onScanProgress";
            }
            if (i == 3) {
                return "onScanCompleted";
            }
            if (i != 4) {
                return null;
            }
            return "onStoreCompleted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.scan.IScanListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.IScanListener");
                return true;
            }
            if (i == 1) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onEvent(bundle);
            } else if (i == 2) {
                String readString = parcel.readString();
                Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onScanProgress(readString, bundle2);
            } else if (i == 3) {
                int readInt = parcel.readInt();
                Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onScanCompleted(readInt, bundle3);
            } else if (i == 4) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStoreCompleted(readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IScanListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.IScanListener";
            }

            @Override // android.media.tv.extension.scan.IScanListener
            public void onEvent(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanListener");
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanListener
            public void onScanProgress(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanListener");
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanListener
            public void onScanCompleted(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanListener");
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanListener
            public void onStoreCompleted(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanListener");
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
