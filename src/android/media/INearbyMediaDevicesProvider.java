package android.media;

import android.media.INearbyMediaDevicesUpdateCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface INearbyMediaDevicesProvider extends IInterface {
    public static final String DESCRIPTOR = "android.media.INearbyMediaDevicesProvider";

    public static class Default implements INearbyMediaDevicesProvider {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.INearbyMediaDevicesProvider
        public void registerNearbyDevicesCallback(INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) throws RemoteException {
        }

        @Override // android.media.INearbyMediaDevicesProvider
        public void unregisterNearbyDevicesCallback(INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) throws RemoteException {
        }
    }

    void registerNearbyDevicesCallback(INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) throws RemoteException;

    void unregisterNearbyDevicesCallback(INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements INearbyMediaDevicesProvider {
        static final int TRANSACTION_registerNearbyDevicesCallback = 3;
        static final int TRANSACTION_unregisterNearbyDevicesCallback = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, INearbyMediaDevicesProvider.DESCRIPTOR);
        }

        public static INearbyMediaDevicesProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(INearbyMediaDevicesProvider.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INearbyMediaDevicesProvider)) {
                return (INearbyMediaDevicesProvider) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 3) {
                return "registerNearbyDevicesCallback";
            }
            if (i != 4) {
                return null;
            }
            return "unregisterNearbyDevicesCallback";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INearbyMediaDevicesProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INearbyMediaDevicesProvider.DESCRIPTOR);
                return true;
            }
            if (i == 3) {
                INearbyMediaDevicesUpdateCallback asInterface = INearbyMediaDevicesUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerNearbyDevicesCallback(asInterface);
            } else if (i == 4) {
                INearbyMediaDevicesUpdateCallback asInterface2 = INearbyMediaDevicesUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterNearbyDevicesCallback(asInterface2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements INearbyMediaDevicesProvider {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INearbyMediaDevicesProvider.DESCRIPTOR;
            }

            @Override // android.media.INearbyMediaDevicesProvider
            public void registerNearbyDevicesCallback(INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(INearbyMediaDevicesProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iNearbyMediaDevicesUpdateCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.INearbyMediaDevicesProvider
            public void unregisterNearbyDevicesCallback(INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(INearbyMediaDevicesProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iNearbyMediaDevicesUpdateCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
