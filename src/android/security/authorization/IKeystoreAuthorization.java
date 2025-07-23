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

    public static class Default implements IKeystoreAuthorization {
        @Override // android.security.authorization.IKeystoreAuthorization
        public void addAuthToken(HardwareAuthToken hardwareAuthToken) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public AuthorizationTokens getAuthTokensForCredStore(long j, long j2, long j3) throws RemoteException {
            return null;
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public long getLastAuthTime(long j, int[] iArr) throws RemoteException {
            return 0L;
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public void onDeviceLocked(int i, long[] jArr, boolean z) throws RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public void onDeviceUnlocked(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public void onNonLskfUnlockMethodsExpired(int i) throws RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public void onWeakUnlockMethodsExpired(int i) throws RemoteException {
        }
    }

    void addAuthToken(HardwareAuthToken hardwareAuthToken) throws RemoteException;

    AuthorizationTokens getAuthTokensForCredStore(long j, long j2, long j3) throws RemoteException;

    long getLastAuthTime(long j, int[] iArr) throws RemoteException;

    void onDeviceLocked(int i, long[] jArr, boolean z) throws RemoteException;

    void onDeviceUnlocked(int i, byte[] bArr) throws RemoteException;

    void onNonLskfUnlockMethodsExpired(int i) throws RemoteException;

    void onWeakUnlockMethodsExpired(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IKeystoreAuthorization {
        static final int TRANSACTION_addAuthToken = 1;
        static final int TRANSACTION_getAuthTokensForCredStore = 6;
        static final int TRANSACTION_getLastAuthTime = 7;
        static final int TRANSACTION_onDeviceLocked = 3;
        static final int TRANSACTION_onDeviceUnlocked = 2;
        static final int TRANSACTION_onNonLskfUnlockMethodsExpired = 5;
        static final int TRANSACTION_onWeakUnlockMethodsExpired = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IKeystoreAuthorization.DESCRIPTOR);
        }

        public static IKeystoreAuthorization asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKeystoreAuthorization.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKeystoreAuthorization)) {
                return (IKeystoreAuthorization) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKeystoreAuthorization.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKeystoreAuthorization.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    HardwareAuthToken hardwareAuthToken = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAuthToken(hardwareAuthToken);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onDeviceUnlocked(readInt, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    long[] createLongArray = parcel.createLongArray();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onDeviceLocked(readInt2, createLongArray, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onWeakUnlockMethodsExpired(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNonLskfUnlockMethodsExpired(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    AuthorizationTokens authTokensForCredStore = getAuthTokensForCredStore(readLong, readLong2, readLong3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(authTokensForCredStore, 1);
                    return true;
                case 7:
                    long readLong4 = parcel.readLong();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long lastAuthTime = getLastAuthTime(readLong4, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastAuthTime);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IKeystoreAuthorization {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeystoreAuthorization.DESCRIPTOR;
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void addAuthToken(HardwareAuthToken hardwareAuthToken) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    this.mRemote.transact(1, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void onDeviceUnlocked(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void onDeviceLocked(int i, long[] jArr, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLongArray(jArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void onWeakUnlockMethodsExpired(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void onNonLskfUnlockMethodsExpired(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public AuthorizationTokens getAuthTokensForCredStore(long j, long j2, long j3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    this.mRemote.transact(6, obtain, obtain2, 32);
                    obtain2.readException();
                    return (AuthorizationTokens) obtain2.readTypedObject(AuthorizationTokens.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public long getLastAuthTime(long j, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(7, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
