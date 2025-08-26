package android.hardware.biometrics;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ITestSession extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.ITestSession";

    public static class Default implements ITestSession {
        @Override // android.hardware.biometrics.ITestSession
        public void acceptAuthentication(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.ITestSession
        public void cleanupInternalState(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.ITestSession
        public void finishEnroll(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.ITestSession
        public int getSensorId() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.ITestSession
        public void notifyAcquired(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.biometrics.ITestSession
        public void notifyError(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.biometrics.ITestSession
        public void notifyVendorAcquired(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.biometrics.ITestSession
        public void notifyVendorError(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.biometrics.ITestSession
        public void rejectAuthentication(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.ITestSession
        public void setTestHalEnabled(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.ITestSession
        public void startEnroll(int i) throws RemoteException {
        }
    }

    void acceptAuthentication(int i) throws RemoteException;

    void cleanupInternalState(int i) throws RemoteException;

    void finishEnroll(int i) throws RemoteException;

    int getSensorId() throws RemoteException;

    void notifyAcquired(int i, int i2) throws RemoteException;

    void notifyError(int i, int i2) throws RemoteException;

    void notifyVendorAcquired(int i, int i2) throws RemoteException;

    void notifyVendorError(int i, int i2) throws RemoteException;

    void rejectAuthentication(int i) throws RemoteException;

    void setTestHalEnabled(boolean z) throws RemoteException;

    void startEnroll(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITestSession {
        static final int TRANSACTION_acceptAuthentication = 4;
        static final int TRANSACTION_cleanupInternalState = 8;
        static final int TRANSACTION_finishEnroll = 3;
        static final int TRANSACTION_getSensorId = 9;
        static final int TRANSACTION_notifyAcquired = 6;
        static final int TRANSACTION_notifyError = 7;
        static final int TRANSACTION_notifyVendorAcquired = 10;
        static final int TRANSACTION_notifyVendorError = 11;
        static final int TRANSACTION_rejectAuthentication = 5;
        static final int TRANSACTION_setTestHalEnabled = 1;
        static final int TRANSACTION_startEnroll = 2;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, ITestSession.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static ITestSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITestSession.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITestSession)) {
                return (ITestSession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setTestHalEnabled";
                case 2:
                    return "startEnroll";
                case 3:
                    return "finishEnroll";
                case 4:
                    return "acceptAuthentication";
                case 5:
                    return "rejectAuthentication";
                case 6:
                    return "notifyAcquired";
                case 7:
                    return "notifyError";
                case 8:
                    return "cleanupInternalState";
                case 9:
                    return "getSensorId";
                case 10:
                    return "notifyVendorAcquired";
                case 11:
                    return "notifyVendorError";
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
                parcel.enforceInterface(ITestSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITestSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTestHalEnabled(z);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startEnroll(i3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishEnroll(i4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acceptAuthentication(i5);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    rejectAuthentication(i6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAcquired(i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyError(i9, i10);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cleanupInternalState(i11);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int sensorId = getSensorId();
                    parcel2.writeNoException();
                    parcel2.writeInt(sensorId);
                    return true;
                case 10:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVendorAcquired(i12, i13);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVendorError(i14, i15);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITestSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITestSession.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.ITestSession
            public void setTestHalEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITestSession.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.ITestSession
            public void startEnroll(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITestSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.ITestSession
            public void finishEnroll(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITestSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.ITestSession
            public void acceptAuthentication(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITestSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.ITestSession
            public void rejectAuthentication(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITestSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.ITestSession
            public void notifyAcquired(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITestSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.ITestSession
            public void notifyError(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITestSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.ITestSession
            public void cleanupInternalState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITestSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.ITestSession
            public int getSensorId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITestSession.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.ITestSession
            public void notifyVendorAcquired(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITestSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.ITestSession
            public void notifyVendorError(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITestSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void setTestHalEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void startEnroll_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void finishEnroll_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void acceptAuthentication_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void rejectAuthentication_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void notifyAcquired_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void notifyError_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void cleanupInternalState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void getSensorId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void notifyVendorAcquired_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void notifyVendorError_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }
    }
}
