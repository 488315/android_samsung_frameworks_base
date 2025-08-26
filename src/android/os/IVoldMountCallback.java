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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVoldMountCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVoldMountCallback)) {
                return (IVoldMountCallback) iInterfaceQueryLocalInterface;
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
                FileDescriptor rawFileDescriptor = parcel.readRawFileDescriptor();
                String string = parcel.readString();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean zOnVolumeChecking = onVolumeChecking(rawFileDescriptor, string, string2);
                parcel2.writeNoException();
                parcel2.writeBoolean(zOnVolumeChecking);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVoldMountCallback.DESCRIPTOR);
                    parcelObtain.writeRawFileDescriptor(fileDescriptor);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
