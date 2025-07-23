package android.service.gatekeeper;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IGateKeeperService extends IInterface {
    public static final String DESCRIPTOR = "android.service.gatekeeper.IGateKeeperService";

    public static class Default implements IGateKeeperService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.gatekeeper.IGateKeeperService
        public void clearSecureUserId(int i) throws RemoteException {
        }

        @Override // android.service.gatekeeper.IGateKeeperService
        public GateKeeperResponse enroll(int i, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException {
            return null;
        }

        @Override // android.service.gatekeeper.IGateKeeperService
        public int getFailureCount(int i) throws RemoteException {
            return 0;
        }

        @Override // android.service.gatekeeper.IGateKeeperService
        public long getSecureUserId(int i) throws RemoteException {
            return 0L;
        }

        @Override // android.service.gatekeeper.IGateKeeperService
        public void reportDeviceSetupComplete() throws RemoteException {
        }

        @Override // android.service.gatekeeper.IGateKeeperService
        public GateKeeperResponse verify(int i, byte[] bArr, byte[] bArr2) throws RemoteException {
            return null;
        }

        @Override // android.service.gatekeeper.IGateKeeperService
        public GateKeeperResponse verifyChallenge(int i, long j, byte[] bArr, byte[] bArr2) throws RemoteException {
            return null;
        }
    }

    void clearSecureUserId(int i) throws RemoteException;

    GateKeeperResponse enroll(int i, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException;

    int getFailureCount(int i) throws RemoteException;

    long getSecureUserId(int i) throws RemoteException;

    void reportDeviceSetupComplete() throws RemoteException;

    GateKeeperResponse verify(int i, byte[] bArr, byte[] bArr2) throws RemoteException;

    GateKeeperResponse verifyChallenge(int i, long j, byte[] bArr, byte[] bArr2) throws RemoteException;

    public static abstract class Stub extends Binder implements IGateKeeperService {
        static final int TRANSACTION_clearSecureUserId = 5;
        static final int TRANSACTION_enroll = 1;
        static final int TRANSACTION_getFailureCount = 7;
        static final int TRANSACTION_getSecureUserId = 4;
        static final int TRANSACTION_reportDeviceSetupComplete = 6;
        static final int TRANSACTION_verify = 2;
        static final int TRANSACTION_verifyChallenge = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, "android.service.gatekeeper.IGateKeeperService");
        }

        public static IGateKeeperService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.service.gatekeeper.IGateKeeperService");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGateKeeperService)) {
                return (IGateKeeperService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "enroll";
                case 2:
                    return "verify";
                case 3:
                    return "verifyChallenge";
                case 4:
                    return "getSecureUserId";
                case 5:
                    return "clearSecureUserId";
                case 6:
                    return "reportDeviceSetupComplete";
                case 7:
                    return "getFailureCount";
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
                parcel.enforceInterface("android.service.gatekeeper.IGateKeeperService");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.service.gatekeeper.IGateKeeperService");
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    GateKeeperResponse enroll = enroll(readInt, createByteArray, createByteArray2, createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enroll, 1);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    byte[] createByteArray4 = parcel.createByteArray();
                    byte[] createByteArray5 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    GateKeeperResponse verify = verify(readInt2, createByteArray4, createByteArray5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verify, 1);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    long readLong = parcel.readLong();
                    byte[] createByteArray6 = parcel.createByteArray();
                    byte[] createByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    GateKeeperResponse verifyChallenge = verifyChallenge(readInt3, readLong, createByteArray6, createByteArray7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyChallenge, 1);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long secureUserId = getSecureUserId(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeLong(secureUserId);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearSecureUserId(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    reportDeviceSetupComplete();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int failureCount = getFailureCount(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(failureCount);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IGateKeeperService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.service.gatekeeper.IGateKeeperService";
            }

            @Override // android.service.gatekeeper.IGateKeeperService
            public GateKeeperResponse enroll(int i, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.service.gatekeeper.IGateKeeperService");
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    this.mRemote.transact(1, obtain, obtain2, 32);
                    obtain2.readException();
                    return (GateKeeperResponse) obtain2.readTypedObject(GateKeeperResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.gatekeeper.IGateKeeperService
            public GateKeeperResponse verify(int i, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.service.gatekeeper.IGateKeeperService");
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(2, obtain, obtain2, 32);
                    obtain2.readException();
                    return (GateKeeperResponse) obtain2.readTypedObject(GateKeeperResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.gatekeeper.IGateKeeperService
            public GateKeeperResponse verifyChallenge(int i, long j, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.service.gatekeeper.IGateKeeperService");
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(3, obtain, obtain2, 32);
                    obtain2.readException();
                    return (GateKeeperResponse) obtain2.readTypedObject(GateKeeperResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.gatekeeper.IGateKeeperService
            public long getSecureUserId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.service.gatekeeper.IGateKeeperService");
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.gatekeeper.IGateKeeperService
            public void clearSecureUserId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.service.gatekeeper.IGateKeeperService");
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.gatekeeper.IGateKeeperService
            public void reportDeviceSetupComplete() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.service.gatekeeper.IGateKeeperService");
                    this.mRemote.transact(6, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.gatekeeper.IGateKeeperService
            public int getFailureCount(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.service.gatekeeper.IGateKeeperService");
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 32);
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
