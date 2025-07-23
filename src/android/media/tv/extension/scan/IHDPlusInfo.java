package android.media.tv.extension.scan;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IHDPlusInfo extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.IHDPlusInfo";

    public static class Default implements IHDPlusInfo {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.IHDPlusInfo
        public int setHDPlusInfo(boolean z, boolean z2) throws RemoteException {
            return 0;
        }
    }

    int setHDPlusInfo(boolean z, boolean z2) throws RemoteException;

    public static abstract class Stub extends Binder implements IHDPlusInfo {
        static final int TRANSACTION_setHDPlusInfo = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.IHDPlusInfo");
        }

        public static IHDPlusInfo asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.IHDPlusInfo");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IHDPlusInfo)) {
                return (IHDPlusInfo) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "setHDPlusInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.scan.IHDPlusInfo");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.IHDPlusInfo");
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                int hDPlusInfo = setHDPlusInfo(readBoolean, readBoolean2);
                parcel2.writeNoException();
                parcel2.writeInt(hDPlusInfo);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IHDPlusInfo {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.IHDPlusInfo";
            }

            @Override // android.media.tv.extension.scan.IHDPlusInfo
            public int setHDPlusInfo(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IHDPlusInfo");
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
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
