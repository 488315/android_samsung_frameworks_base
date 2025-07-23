package android.os;

import java.io.FileDescriptor;

/* loaded from: classes3.dex */
public interface IVoldMountCallback extends IInterface {
    public static final String DESCRIPTOR = "android.os.IVoldMountCallback";

    public static class Default implements IVoldMountCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IVoldMountCallback
        public boolean onVolumeChecking(FileDescriptor fileDescriptor, String str, String str2) throws RemoteException {
            return false;
        }
    }

    boolean onVolumeChecking(FileDescriptor fileDescriptor, String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IVoldMountCallback {
        static final int TRANSACTION_onVolumeChecking = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IVoldMountCallback.DESCRIPTOR);
        }

        public static IVoldMountCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVoldMountCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVoldMountCallback)) {
                return (IVoldMountCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onVolumeChecking";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVoldMountCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVoldMountCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                FileDescriptor readRawFileDescriptor = parcel.readRawFileDescriptor();
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean onVolumeChecking = onVolumeChecking(readRawFileDescriptor, readString, readString2);
                parcel2.writeNoException();
                parcel2.writeBoolean(onVolumeChecking);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IVoldMountCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVoldMountCallback.DESCRIPTOR;
            }

            @Override // android.os.IVoldMountCallback
            public boolean onVolumeChecking(FileDescriptor fileDescriptor, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVoldMountCallback.DESCRIPTOR);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
