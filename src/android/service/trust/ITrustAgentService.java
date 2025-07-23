package android.service.trust;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.trust.ITrustAgentServiceCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface ITrustAgentService extends IInterface {

    public static class Default implements ITrustAgentService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.trust.ITrustAgentService
        public void onConfigure(List<PersistableBundle> list, IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onDeviceLocked() throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onDeviceUnlocked() throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onEscrowTokenAdded(byte[] bArr, long j, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onEscrowTokenRemoved(long j, boolean z) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onTokenStateReceived(long j, int i) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onTrustTimeout() throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onUnlockAttempt(boolean z) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onUnlockLockout(int i) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onUserMayRequestUnlock() throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onUserRequestedUnlock(boolean z) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentService
        public void setCallback(ITrustAgentServiceCallback iTrustAgentServiceCallback) throws RemoteException {
        }
    }

    void onConfigure(List<PersistableBundle> list, IBinder iBinder) throws RemoteException;

    void onDeviceLocked() throws RemoteException;

    void onDeviceUnlocked() throws RemoteException;

    void onEscrowTokenAdded(byte[] bArr, long j, UserHandle userHandle) throws RemoteException;

    void onEscrowTokenRemoved(long j, boolean z) throws RemoteException;

    void onTokenStateReceived(long j, int i) throws RemoteException;

    void onTrustTimeout() throws RemoteException;

    void onUnlockAttempt(boolean z) throws RemoteException;

    void onUnlockLockout(int i) throws RemoteException;

    void onUserMayRequestUnlock() throws RemoteException;

    void onUserRequestedUnlock(boolean z) throws RemoteException;

    void setCallback(ITrustAgentServiceCallback iTrustAgentServiceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ITrustAgentService {
        public static final String DESCRIPTOR = "android.service.trust.ITrustAgentService";
        static final int TRANSACTION_onConfigure = 8;
        static final int TRANSACTION_onDeviceLocked = 6;
        static final int TRANSACTION_onDeviceUnlocked = 7;
        static final int TRANSACTION_onEscrowTokenAdded = 10;
        static final int TRANSACTION_onEscrowTokenRemoved = 12;
        static final int TRANSACTION_onTokenStateReceived = 11;
        static final int TRANSACTION_onTrustTimeout = 5;
        static final int TRANSACTION_onUnlockAttempt = 1;
        static final int TRANSACTION_onUnlockLockout = 4;
        static final int TRANSACTION_onUserMayRequestUnlock = 3;
        static final int TRANSACTION_onUserRequestedUnlock = 2;
        static final int TRANSACTION_setCallback = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITrustAgentService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITrustAgentService)) {
                return (ITrustAgentService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUnlockAttempt";
                case 2:
                    return "onUserRequestedUnlock";
                case 3:
                    return "onUserMayRequestUnlock";
                case 4:
                    return "onUnlockLockout";
                case 5:
                    return "onTrustTimeout";
                case 6:
                    return "onDeviceLocked";
                case 7:
                    return "onDeviceUnlocked";
                case 8:
                    return "onConfigure";
                case 9:
                    return "setCallback";
                case 10:
                    return "onEscrowTokenAdded";
                case 11:
                    return "onTokenStateReceived";
                case 12:
                    return "onEscrowTokenRemoved";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUnlockAttempt(readBoolean);
                    return true;
                case 2:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUserRequestedUnlock(readBoolean2);
                    return true;
                case 3:
                    onUserMayRequestUnlock();
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUnlockLockout(readInt);
                    return true;
                case 5:
                    onTrustTimeout();
                    return true;
                case 6:
                    onDeviceLocked();
                    return true;
                case 7:
                    onDeviceUnlocked();
                    return true;
                case 8:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(PersistableBundle.CREATOR);
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onConfigure(createTypedArrayList, readStrongBinder);
                    return true;
                case 9:
                    ITrustAgentServiceCallback asInterface = ITrustAgentServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    return true;
                case 10:
                    byte[] createByteArray = parcel.createByteArray();
                    long readLong = parcel.readLong();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEscrowTokenAdded(createByteArray, readLong, userHandle);
                    return true;
                case 11:
                    long readLong2 = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTokenStateReceived(readLong2, readInt2);
                    return true;
                case 12:
                    long readLong3 = parcel.readLong();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onEscrowTokenRemoved(readLong3, readBoolean3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITrustAgentService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.trust.ITrustAgentService
            public void onUnlockAttempt(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onUserRequestedUnlock(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onUserMayRequestUnlock() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onUnlockLockout(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onTrustTimeout() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onDeviceLocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onDeviceUnlocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onConfigure(List<PersistableBundle> list, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void setCallback(ITrustAgentServiceCallback iTrustAgentServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTrustAgentServiceCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onEscrowTokenAdded(byte[] bArr, long j, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onTokenStateReceived(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentService
            public void onEscrowTokenRemoved(long j, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
