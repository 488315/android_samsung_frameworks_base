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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITrustManager)) {
                return (ITrustManager) iInterfaceQueryLocalInterface;
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
                    boolean z = parcel.readBoolean();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportUnlockAttempt(z, i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportUserRequestedUnlock(i4, z2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportUserMayRequestUnlock(i5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportUnlockLockout(i6, i7);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportEnabledTrustAgentsChanged(i8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    ITrustListener iTrustListenerAsInterface = ITrustListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTrustListener(iTrustListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ITrustListener iTrustListenerAsInterface2 = ITrustListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTrustListener(iTrustListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    reportKeyguardShowingChanged();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i9 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceLockedForUser(i9, z3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDeviceLocked = isDeviceLocked(i10, i11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceLocked);
                    return true;
                case 11:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDeviceSecure = isDeviceSecure(i12, i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceSecure);
                    return true;
                case 12:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsTrustUsuallyManaged = isTrustUsuallyManaged(i14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTrustUsuallyManaged);
                    return true;
                case 13:
                    int i15 = parcel.readInt();
                    BiometricSourceType biometricSourceType = (BiometricSourceType) parcel.readTypedObject(BiometricSourceType.CREATOR);
                    parcel.enforceNoDataAvail();
                    unlockedByBiometricForUser(i15, biometricSourceType);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    BiometricSourceType biometricSourceType2 = (BiometricSourceType) parcel.readTypedObject(BiometricSourceType.CREATOR);
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearAllBiometricRecognized(biometricSourceType2, i16);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsActiveUnlockRunning = isActiveUnlockRunning(i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsActiveUnlockRunning);
                    return true;
                case 16:
                    boolean zIsInSignificantPlace = isInSignificantPlace();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInSignificantPlace);
                    return true;
                case 17:
                    IDeviceLockedStateListener iDeviceLockedStateListenerAsInterface = IDeviceLockedStateListener.Stub.asInterface(parcel.readStrongBinder());
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerDeviceLockedStateListener(iDeviceLockedStateListenerAsInterface, i18);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IDeviceLockedStateListener iDeviceLockedStateListenerAsInterface2 = IDeviceLockedStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDeviceLockedStateListener(iDeviceLockedStateListenerAsInterface2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportUserRequestedUnlock(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportUserMayRequestUnlock(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportUnlockLockout(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportEnabledTrustAgentsChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void registerTrustListener(ITrustListener iTrustListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTrustListener);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void unregisterTrustListener(ITrustListener iTrustListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTrustListener);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportKeyguardShowingChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void setDeviceLockedForUser(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isDeviceLocked(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isDeviceSecure(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isTrustUsuallyManaged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void unlockedByBiometricForUser(int i, BiometricSourceType biometricSourceType) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(biometricSourceType, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void clearAllBiometricRecognized(BiometricSourceType biometricSourceType, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(biometricSourceType, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isActiveUnlockRunning(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isInSignificantPlace() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void registerDeviceLockedStateListener(IDeviceLockedStateListener iDeviceLockedStateListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDeviceLockedStateListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void unregisterDeviceLockedStateListener(IDeviceLockedStateListener iDeviceLockedStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDeviceLockedStateListener);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
