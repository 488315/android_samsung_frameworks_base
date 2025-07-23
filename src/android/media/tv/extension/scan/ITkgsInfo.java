package android.media.tv.extension.scan;

import android.media.tv.extension.scan.ITkgsInfoListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITkgsInfo extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.ITkgsInfo";

    public static class Default implements ITkgsInfo {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.ITkgsInfo
        public int setPrefServiceList(String str) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.ITkgsInfo
        public int setTkgsInfoListener(ITkgsInfoListener iTkgsInfoListener) throws RemoteException {
            return 0;
        }
    }

    int setPrefServiceList(String str) throws RemoteException;

    int setTkgsInfoListener(ITkgsInfoListener iTkgsInfoListener) throws RemoteException;

    public static abstract class Stub extends Binder implements ITkgsInfo {
        static final int TRANSACTION_setPrefServiceList = 1;
        static final int TRANSACTION_setTkgsInfoListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.ITkgsInfo");
        }

        public static ITkgsInfo asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.ITkgsInfo");
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITkgsInfo)) {
                return (ITkgsInfo) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setPrefServiceList";
            }
            if (i != 2) {
                return null;
            }
            return "setTkgsInfoListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.scan.ITkgsInfo");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.ITkgsInfo");
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                int prefServiceList = setPrefServiceList(readString);
                parcel2.writeNoException();
                parcel2.writeInt(prefServiceList);
            } else if (i == 2) {
                ITkgsInfoListener asInterface = ITkgsInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int tkgsInfoListener = setTkgsInfoListener(asInterface);
                parcel2.writeNoException();
                parcel2.writeInt(tkgsInfoListener);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITkgsInfo {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.ITkgsInfo";
            }

            @Override // android.media.tv.extension.scan.ITkgsInfo
            public int setPrefServiceList(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.ITkgsInfo");
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.ITkgsInfo
            public int setTkgsInfoListener(ITkgsInfoListener iTkgsInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.ITkgsInfo");
                    obtain.writeStrongInterface(iTkgsInfoListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
