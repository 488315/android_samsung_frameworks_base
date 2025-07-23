package android.media.tv.extension.scan;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITkgsInfoListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.ITkgsInfoListener";

    public static class Default implements ITkgsInfoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.ITkgsInfoListener
        public void onServiceList(String[] strArr) throws RemoteException {
        }

        @Override // android.media.tv.extension.scan.ITkgsInfoListener
        public void onTableVersionUpdate(int i) throws RemoteException {
        }

        @Override // android.media.tv.extension.scan.ITkgsInfoListener
        public void onUserMessage(String str) throws RemoteException {
        }
    }

    void onServiceList(String[] strArr) throws RemoteException;

    void onTableVersionUpdate(int i) throws RemoteException;

    void onUserMessage(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ITkgsInfoListener {
        static final int TRANSACTION_onServiceList = 1;
        static final int TRANSACTION_onTableVersionUpdate = 2;
        static final int TRANSACTION_onUserMessage = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.ITkgsInfoListener");
        }

        public static ITkgsInfoListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.ITkgsInfoListener");
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITkgsInfoListener)) {
                return (ITkgsInfoListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onServiceList";
            }
            if (i == 2) {
                return "onTableVersionUpdate";
            }
            if (i != 3) {
                return null;
            }
            return "onUserMessage";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.scan.ITkgsInfoListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.ITkgsInfoListener");
                return true;
            }
            if (i == 1) {
                String[] createStringArray = parcel.createStringArray();
                parcel.enforceNoDataAvail();
                onServiceList(createStringArray);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTableVersionUpdate(readInt);
            } else if (i == 3) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onUserMessage(readString);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITkgsInfoListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.ITkgsInfoListener";
            }

            @Override // android.media.tv.extension.scan.ITkgsInfoListener
            public void onServiceList(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.ITkgsInfoListener");
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.ITkgsInfoListener
            public void onTableVersionUpdate(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.ITkgsInfoListener");
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.ITkgsInfoListener
            public void onUserMessage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.ITkgsInfoListener");
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
