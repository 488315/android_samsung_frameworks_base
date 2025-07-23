package android.companion.virtualnative;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IVirtualDeviceManagerNative extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtualnative.IVirtualDeviceManagerNative";
    public static final int DEVICE_POLICY_CUSTOM = 1;
    public static final int DEVICE_POLICY_DEFAULT = 0;
    public static final int POLICY_TYPE_ACTIVITY = 3;
    public static final int POLICY_TYPE_AUDIO = 1;
    public static final int POLICY_TYPE_CAMERA = 5;
    public static final int POLICY_TYPE_CLIPBOARD = 4;
    public static final int POLICY_TYPE_RECENTS = 2;
    public static final int POLICY_TYPE_SENSORS = 0;

    public static class Default implements IVirtualDeviceManagerNative {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
        public int getDeviceIdForDisplayId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
        public int[] getDeviceIdsForUid(int i) throws RemoteException {
            return null;
        }

        @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
        public int getDevicePolicy(int i, int i2) throws RemoteException {
            return 0;
        }
    }

    int getDeviceIdForDisplayId(int i) throws RemoteException;

    int[] getDeviceIdsForUid(int i) throws RemoteException;

    int getDevicePolicy(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualDeviceManagerNative {
        static final int TRANSACTION_getDeviceIdForDisplayId = 3;
        static final int TRANSACTION_getDeviceIdsForUid = 1;
        static final int TRANSACTION_getDevicePolicy = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IVirtualDeviceManagerNative.DESCRIPTOR);
        }

        public static IVirtualDeviceManagerNative asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVirtualDeviceManagerNative.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVirtualDeviceManagerNative)) {
                return (IVirtualDeviceManagerNative) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getDeviceIdsForUid";
            }
            if (i == 2) {
                return "getDevicePolicy";
            }
            if (i != 3) {
                return null;
            }
            return "getDeviceIdForDisplayId";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVirtualDeviceManagerNative.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVirtualDeviceManagerNative.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                int[] deviceIdsForUid = getDeviceIdsForUid(readInt);
                parcel2.writeNoException();
                parcel2.writeIntArray(deviceIdsForUid);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int devicePolicy = getDevicePolicy(readInt2, readInt3);
                parcel2.writeNoException();
                parcel2.writeInt(devicePolicy);
            } else if (i == 3) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int deviceIdForDisplayId = getDeviceIdForDisplayId(readInt4);
                parcel2.writeNoException();
                parcel2.writeInt(deviceIdForDisplayId);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVirtualDeviceManagerNative {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDeviceManagerNative.DESCRIPTOR;
            }

            @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
            public int[] getDeviceIdsForUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManagerNative.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
            public int getDevicePolicy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManagerNative.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
            public int getDeviceIdForDisplayId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManagerNative.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
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
