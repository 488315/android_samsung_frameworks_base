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

    int[] getDeviceIdsForUid(int i) throws RemoteException;

    int getDevicePolicy(int i, int i2) throws RemoteException;

    public static class Default implements IVirtualDeviceManagerNative {
        @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
        public int[] getDeviceIdsForUid(int uid) throws RemoteException {
            return null;
        }

        @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
        public int getDevicePolicy(int deviceId, int policyType) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVirtualDeviceManagerNative {
        static final int TRANSACTION_getDeviceIdsForUid = 1;
        static final int TRANSACTION_getDevicePolicy = 2;

        public Stub() {
            attachInterface(this, IVirtualDeviceManagerNative.DESCRIPTOR);
        }

        public static IVirtualDeviceManagerNative asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVirtualDeviceManagerNative.DESCRIPTOR);
            if (iin != null && (iin instanceof IVirtualDeviceManagerNative)) {
                return (IVirtualDeviceManagerNative) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getDeviceIdsForUid";
                case 2:
                    return "getDevicePolicy";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IVirtualDeviceManagerNative.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVirtualDeviceManagerNative.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result = getDeviceIdsForUid(_arg0);
                    reply.writeNoException();
                    reply.writeIntArray(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result2 = getDevicePolicy(_arg02, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVirtualDeviceManagerNative {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDeviceManagerNative.DESCRIPTOR;
            }

            @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
            public int[] getDeviceIdsForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManagerNative.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
            public int getDevicePolicy(int deviceId, int policyType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManagerNative.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeInt(policyType);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
