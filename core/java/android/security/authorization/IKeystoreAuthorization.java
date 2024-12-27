package android.security.authorization;

import android.hardware.security.keymint.HardwareAuthToken;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IKeystoreAuthorization extends IInterface {
    public static final String DESCRIPTOR = "android.security.authorization.IKeystoreAuthorization";

    void addAuthToken(HardwareAuthToken hardwareAuthToken) throws RemoteException;

    AuthorizationTokens getAuthTokensForCredStore(long j, long j2, long j3) throws RemoteException;

    long getLastAuthTime(long j, int[] iArr) throws RemoteException;

    void onDeviceLocked(int i, long[] jArr, boolean z) throws RemoteException;

    void onDeviceUnlocked(int i, byte[] bArr) throws RemoteException;

    void onNonLskfUnlockMethodsExpired(int i) throws RemoteException;

    void onWeakUnlockMethodsExpired(int i) throws RemoteException;

    public static class Default implements IKeystoreAuthorization {
        @Override // android.security.authorization.IKeystoreAuthorization
        public void addAuthToken(HardwareAuthToken authToken) throws RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public void onDeviceUnlocked(int userId, byte[] password) throws RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public void onDeviceLocked(int userId, long[] unlockingSids, boolean weakUnlockEnabled) throws RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public void onWeakUnlockMethodsExpired(int userId) throws RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public void onNonLskfUnlockMethodsExpired(int userId) throws RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public AuthorizationTokens getAuthTokensForCredStore(long challenge, long secureUserId, long authTokenMaxAgeMillis) throws RemoteException {
            return null;
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public long getLastAuthTime(long secureUserId, int[] authTypes) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IKeystoreAuthorization {
        static final int TRANSACTION_addAuthToken = 1;
        static final int TRANSACTION_getAuthTokensForCredStore = 6;
        static final int TRANSACTION_getLastAuthTime = 7;
        static final int TRANSACTION_onDeviceLocked = 3;
        static final int TRANSACTION_onDeviceUnlocked = 2;
        static final int TRANSACTION_onNonLskfUnlockMethodsExpired = 5;
        static final int TRANSACTION_onWeakUnlockMethodsExpired = 4;

        public Stub() {
            attachInterface(this, IKeystoreAuthorization.DESCRIPTOR);
        }

        public static IKeystoreAuthorization asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IKeystoreAuthorization.DESCRIPTOR);
            if (iin != null && (iin instanceof IKeystoreAuthorization)) {
                return (IKeystoreAuthorization) iin;
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
                    return "addAuthToken";
                case 2:
                    return "onDeviceUnlocked";
                case 3:
                    return "onDeviceLocked";
                case 4:
                    return "onWeakUnlockMethodsExpired";
                case 5:
                    return "onNonLskfUnlockMethodsExpired";
                case 6:
                    return "getAuthTokensForCredStore";
                case 7:
                    return "getLastAuthTime";
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
                data.enforceInterface(IKeystoreAuthorization.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IKeystoreAuthorization.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    HardwareAuthToken _arg0 = (HardwareAuthToken) data.readTypedObject(HardwareAuthToken.CREATOR);
                    data.enforceNoDataAvail();
                    addAuthToken(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    byte[] _arg1 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onDeviceUnlocked(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    long[] _arg12 = data.createLongArray();
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onDeviceLocked(_arg03, _arg12, _arg2);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    onWeakUnlockMethodsExpired(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    onNonLskfUnlockMethodsExpired(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    long _arg06 = data.readLong();
                    long _arg13 = data.readLong();
                    long _arg22 = data.readLong();
                    data.enforceNoDataAvail();
                    AuthorizationTokens _result = getAuthTokensForCredStore(_arg06, _arg13, _arg22);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 7:
                    long _arg07 = data.readLong();
                    int[] _arg14 = data.createIntArray();
                    data.enforceNoDataAvail();
                    long _result2 = getLastAuthTime(_arg07, _arg14);
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKeystoreAuthorization {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeystoreAuthorization.DESCRIPTOR;
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void addAuthToken(HardwareAuthToken authToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    _data.writeTypedObject(authToken, 0);
                    this.mRemote.transact(1, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void onDeviceUnlocked(int userId, byte[] password) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeByteArray(password);
                    this.mRemote.transact(2, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void onDeviceLocked(int userId, long[] unlockingSids, boolean weakUnlockEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeLongArray(unlockingSids);
                    _data.writeBoolean(weakUnlockEnabled);
                    this.mRemote.transact(3, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void onWeakUnlockMethodsExpired(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void onNonLskfUnlockMethodsExpired(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public AuthorizationTokens getAuthTokensForCredStore(long challenge, long secureUserId, long authTokenMaxAgeMillis) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    _data.writeLong(challenge);
                    _data.writeLong(secureUserId);
                    _data.writeLong(authTokenMaxAgeMillis);
                    this.mRemote.transact(6, _data, _reply, 32);
                    _reply.readException();
                    AuthorizationTokens _result = (AuthorizationTokens) _reply.readTypedObject(AuthorizationTokens.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public long getLastAuthTime(long secureUserId, int[] authTypes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    _data.writeLong(secureUserId);
                    _data.writeIntArray(authTypes);
                    this.mRemote.transact(7, _data, _reply, 32);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
