package android.system.suspend.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISuspendControlServiceInternal extends IInterface {
    public static final String DESCRIPTOR = "android.system.suspend.internal.ISuspendControlServiceInternal";
    public static final int WAKE_LOCK_INFO_ACTIVE_COUNT = 1;
    public static final int WAKE_LOCK_INFO_ACTIVE_TIME = 32;
    public static final int WAKE_LOCK_INFO_ALL_FIELDS = 4095;
    public static final int WAKE_LOCK_INFO_EVENT_COUNT = 256;
    public static final int WAKE_LOCK_INFO_EXPIRE_COUNT = 512;
    public static final int WAKE_LOCK_INFO_IS_ACTIVE = 16;
    public static final int WAKE_LOCK_INFO_IS_KERNEL_WAKELOCK = 64;
    public static final int WAKE_LOCK_INFO_LAST_CHANGE = 2;
    public static final int WAKE_LOCK_INFO_MAX_TIME = 4;
    public static final int WAKE_LOCK_INFO_PID = 128;
    public static final int WAKE_LOCK_INFO_PREVENT_SUSPEND_TIME = 1024;
    public static final int WAKE_LOCK_INFO_TOTAL_TIME = 8;
    public static final int WAKE_LOCK_INFO_WAKEUP_COUNT = 2048;

    public static class Default implements ISuspendControlServiceInternal {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.system.suspend.internal.ISuspendControlServiceInternal
        public boolean enableAutosuspend(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.system.suspend.internal.ISuspendControlServiceInternal
        public boolean forceSuspend() throws RemoteException {
            return false;
        }

        @Override // android.system.suspend.internal.ISuspendControlServiceInternal
        public SuspendInfo getSuspendStats() throws RemoteException {
            return null;
        }

        @Override // android.system.suspend.internal.ISuspendControlServiceInternal
        public WakeLockInfo[] getWakeLockStats() throws RemoteException {
            return null;
        }

        @Override // android.system.suspend.internal.ISuspendControlServiceInternal
        public WakeLockInfo[] getWakeLockStatsFiltered(int i) throws RemoteException {
            return null;
        }

        @Override // android.system.suspend.internal.ISuspendControlServiceInternal
        public WakeupInfo[] getWakeupStats() throws RemoteException {
            return null;
        }
    }

    boolean enableAutosuspend(IBinder iBinder) throws RemoteException;

    boolean forceSuspend() throws RemoteException;

    SuspendInfo getSuspendStats() throws RemoteException;

    WakeLockInfo[] getWakeLockStats() throws RemoteException;

    WakeLockInfo[] getWakeLockStatsFiltered(int i) throws RemoteException;

    WakeupInfo[] getWakeupStats() throws RemoteException;

    public static abstract class Stub extends Binder implements ISuspendControlServiceInternal {
        static final int TRANSACTION_enableAutosuspend = 1;
        static final int TRANSACTION_forceSuspend = 2;
        static final int TRANSACTION_getSuspendStats = 6;
        static final int TRANSACTION_getWakeLockStats = 3;
        static final int TRANSACTION_getWakeLockStatsFiltered = 4;
        static final int TRANSACTION_getWakeupStats = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISuspendControlServiceInternal.DESCRIPTOR);
        }

        public static ISuspendControlServiceInternal asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISuspendControlServiceInternal.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISuspendControlServiceInternal)) {
                return (ISuspendControlServiceInternal) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISuspendControlServiceInternal.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISuspendControlServiceInternal.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean zEnableAutosuspend = enableAutosuspend(parcel.readStrongBinder());
                    parcel2.writeNoException();
                    parcel2.writeInt(zEnableAutosuspend ? 1 : 0);
                    return true;
                case 2:
                    boolean zForceSuspend = forceSuspend();
                    parcel2.writeNoException();
                    parcel2.writeInt(zForceSuspend ? 1 : 0);
                    return true;
                case 3:
                    WakeLockInfo[] wakeLockStats = getWakeLockStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(wakeLockStats, 1);
                    return true;
                case 4:
                    WakeLockInfo[] wakeLockStatsFiltered = getWakeLockStatsFiltered(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(wakeLockStatsFiltered, 1);
                    return true;
                case 5:
                    WakeupInfo[] wakeupStats = getWakeupStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(wakeupStats, 1);
                    return true;
                case 6:
                    SuspendInfo suspendStats = getSuspendStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(suspendStats, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISuspendControlServiceInternal {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISuspendControlServiceInternal.DESCRIPTOR;
            }

            @Override // android.system.suspend.internal.ISuspendControlServiceInternal
            public boolean enableAutosuspend(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISuspendControlServiceInternal.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.suspend.internal.ISuspendControlServiceInternal
            public boolean forceSuspend() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISuspendControlServiceInternal.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.suspend.internal.ISuspendControlServiceInternal
            public WakeLockInfo[] getWakeLockStats() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISuspendControlServiceInternal.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WakeLockInfo[]) parcelObtain2.createTypedArray(WakeLockInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.suspend.internal.ISuspendControlServiceInternal
            public WakeLockInfo[] getWakeLockStatsFiltered(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISuspendControlServiceInternal.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WakeLockInfo[]) parcelObtain2.createTypedArray(WakeLockInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.suspend.internal.ISuspendControlServiceInternal
            public WakeupInfo[] getWakeupStats() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISuspendControlServiceInternal.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WakeupInfo[]) parcelObtain2.createTypedArray(WakeupInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.suspend.internal.ISuspendControlServiceInternal
            public SuspendInfo getSuspendStats() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISuspendControlServiceInternal.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SuspendInfo) parcelObtain2.readTypedObject(SuspendInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
