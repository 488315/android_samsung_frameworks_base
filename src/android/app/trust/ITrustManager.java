package android.app.trust;

import android.Manifest;
import android.app.ActivityThread;
import android.app.trust.ITrustListener;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import com.android.internal.policy.IDeviceLockedStateListener;

/* loaded from: classes.dex */
public interface ITrustManager extends IInterface {

    public static class Default implements ITrustManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.trust.ITrustManager
        public void clearAllBiometricRecognized(BiometricSourceType biometricSourceType, int i) throws RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public boolean isActiveUnlockRunning(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.trust.ITrustManager
        public boolean isDeviceLocked(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.trust.ITrustManager
        public boolean isDeviceSecure(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.trust.ITrustManager
        public boolean isInSignificantPlace() throws RemoteException {
            return false;
        }

        @Override // android.app.trust.ITrustManager
        public boolean isTrustUsuallyManaged(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.trust.ITrustManager
        public void registerDeviceLockedStateListener(IDeviceLockedStateListener iDeviceLockedStateListener, int i) throws RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void registerTrustListener(ITrustListener iTrustListener) throws RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void reportEnabledTrustAgentsChanged(int i) throws RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void reportKeyguardShowingChanged() throws RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void reportUnlockAttempt(boolean z, int i) throws RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void reportUnlockLockout(int i, int i2) throws RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void reportUserMayRequestUnlock(int i) throws RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void reportUserRequestedUnlock(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void setDeviceLockedForUser(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void unlockedByBiometricForUser(int i, BiometricSourceType biometricSourceType) throws RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void unregisterDeviceLockedStateListener(IDeviceLockedStateListener iDeviceLockedStateListener) throws RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void unregisterTrustListener(ITrustListener iTrustListener) throws RemoteException {
        }
    }

    void clearAllBiometricRecognized(BiometricSourceType biometricSourceType, int i) throws RemoteException;

    boolean isActiveUnlockRunning(int i) throws RemoteException;

    boolean isDeviceLocked(int i, int i2) throws RemoteException;

    boolean isDeviceSecure(int i, int i2) throws RemoteException;

    boolean isInSignificantPlace() throws RemoteException;

    boolean isTrustUsuallyManaged(int i) throws RemoteException;

    void registerDeviceLockedStateListener(IDeviceLockedStateListener iDeviceLockedStateListener, int i) throws RemoteException;

    void registerTrustListener(ITrustListener iTrustListener) throws RemoteException;

    void reportEnabledTrustAgentsChanged(int i) throws RemoteException;

    void reportKeyguardShowingChanged() throws RemoteException;

    void reportUnlockAttempt(boolean z, int i) throws RemoteException;

    void reportUnlockLockout(int i, int i2) throws RemoteException;

    void reportUserMayRequestUnlock(int i) throws RemoteException;

    void reportUserRequestedUnlock(int i, boolean z) throws RemoteException;

    void setDeviceLockedForUser(int i, boolean z) throws RemoteException;

    void unlockedByBiometricForUser(int i, BiometricSourceType biometricSourceType) throws RemoteException;

    void unregisterDeviceLockedStateListener(IDeviceLockedStateListener iDeviceLockedStateListener) throws RemoteException;

    void unregisterTrustListener(ITrustListener iTrustListener) throws RemoteException;

    public static abstract class Stub extends Binder implements ITrustManager {
        public static final String DESCRIPTOR = "android.app.trust.ITrustManager";
        static final int TRANSACTION_clearAllBiometricRecognized = 14;
        static final int TRANSACTION_isActiveUnlockRunning = 15;
        static final int TRANSACTION_isDeviceLocked = 10;
        static final int TRANSACTION_isDeviceSecure = 11;
        static final int TRANSACTION_isInSignificantPlace = 16;
        static final int TRANSACTION_isTrustUsuallyManaged = 12;
        static final int TRANSACTION_registerDeviceLockedStateListener = 17;
        static final int TRANSACTION_registerTrustListener = 6;
        static final int TRANSACTION_reportEnabledTrustAgentsChanged = 5;
        static final int TRANSACTION_reportKeyguardShowingChanged = 8;
        static final int TRANSACTION_reportUnlockAttempt = 1;
        static final int TRANSACTION_reportUnlockLockout = 4;
        static final int TRANSACTION_reportUserMayRequestUnlock = 3;
        static final int TRANSACTION_reportUserRequestedUnlock = 2;
        static final int TRANSACTION_setDeviceLockedForUser = 9;
        static final int TRANSACTION_unlockedByBiometricForUser = 13;
        static final int TRANSACTION_unregisterDeviceLockedStateListener = 18;
        static final int TRANSACTION_unregisterTrustListener = 7;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static ITrustManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITrustManager)) {
                return (ITrustManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "reportUnlockAttempt";
                case 2:
                    return "reportUserRequestedUnlock";
                case 3:
                    return "reportUserMayRequestUnlock";
                case 4:
                    return "reportUnlockLockout";
                case 5:
                    return "reportEnabledTrustAgentsChanged";
                case 6:
                    return "registerTrustListener";
                case 7:
                    return "unregisterTrustListener";
                case 8:
                    return "reportKeyguardShowingChanged";
                case 9:
                    return "setDeviceLockedForUser";
                case 10:
                    return "isDeviceLocked";
                case 11:
                    return "isDeviceSecure";
                case 12:
                    return "isTrustUsuallyManaged";
                case 13:
                    return "unlockedByBiometricForUser";
                case 14:
                    return "clearAllBiometricRecognized";
                case 15:
                    return "isActiveUnlockRunning";
                case 16:
                    return "isInSignificantPlace";
                case 17:
                    return "registerDeviceLockedStateListener";
                case 18:
                    return "unregisterDeviceLockedStateListener";
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportUnlockAttempt(readBoolean, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportUserRequestedUnlock(readInt2, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportUserMayRequestUnlock(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportUnlockLockout(readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportEnabledTrustAgentsChanged(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    ITrustListener asInterface = ITrustListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTrustListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ITrustListener asInterface2 = ITrustListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTrustListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    reportKeyguardShowingChanged();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt7 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceLockedForUser(readInt7, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDeviceLocked = isDeviceLocked(readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceLocked);
                    return true;
                case 11:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDeviceSecure = isDeviceSecure(readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceSecure);
                    return true;
                case 12:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isTrustUsuallyManaged = isTrustUsuallyManaged(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTrustUsuallyManaged);
                    return true;
                case 13:
                    int readInt13 = parcel.readInt();
                    BiometricSourceType biometricSourceType = (BiometricSourceType) parcel.readTypedObject(BiometricSourceType.CREATOR);
                    parcel.enforceNoDataAvail();
                    unlockedByBiometricForUser(readInt13, biometricSourceType);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    BiometricSourceType biometricSourceType2 = (BiometricSourceType) parcel.readTypedObject(BiometricSourceType.CREATOR);
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearAllBiometricRecognized(biometricSourceType2, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isActiveUnlockRunning = isActiveUnlockRunning(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActiveUnlockRunning);
                    return true;
                case 16:
                    boolean isInSignificantPlace = isInSignificantPlace();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInSignificantPlace);
                    return true;
                case 17:
                    IDeviceLockedStateListener asInterface3 = IDeviceLockedStateListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerDeviceLockedStateListener(asInterface3, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IDeviceLockedStateListener asInterface4 = IDeviceLockedStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDeviceLockedStateListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITrustManager {
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

            @Override // android.app.trust.ITrustManager
            public void reportUnlockAttempt(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportUserRequestedUnlock(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportUserMayRequestUnlock(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportUnlockLockout(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportEnabledTrustAgentsChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void registerTrustListener(ITrustListener iTrustListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTrustListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void unregisterTrustListener(ITrustListener iTrustListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTrustListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportKeyguardShowingChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void setDeviceLockedForUser(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isDeviceLocked(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isDeviceSecure(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isTrustUsuallyManaged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void unlockedByBiometricForUser(int i, BiometricSourceType biometricSourceType) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(biometricSourceType, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void clearAllBiometricRecognized(BiometricSourceType biometricSourceType, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(biometricSourceType, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isActiveUnlockRunning(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isInSignificantPlace() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void registerDeviceLockedStateListener(IDeviceLockedStateListener iDeviceLockedStateListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDeviceLockedStateListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void unregisterDeviceLockedStateListener(IDeviceLockedStateListener iDeviceLockedStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDeviceLockedStateListener);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void isTrustUsuallyManaged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TRUST_LISTENER, getCallingPid(), getCallingUid());
        }

        protected void isInSignificantPlace_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_FINE_LOCATION, getCallingPid(), getCallingUid());
        }

        protected void registerDeviceLockedStateListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE, getCallingPid(), getCallingUid());
        }

        protected void unregisterDeviceLockedStateListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE, getCallingPid(), getCallingUid());
        }
    }
}
