package android.app;

import android.Manifest;
import android.app.AlarmManager;
import android.app.IAlarmListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.WorkSource;
import java.util.List;

/* loaded from: classes.dex */
public interface IAlarmManager extends IInterface {

    public static class Default implements IAlarmManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IAlarmManager
        public boolean canScheduleExactAlarms(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IAlarmManager
        public int getConfigVersion() throws RemoteException {
            return 0;
        }

        @Override // android.app.IAlarmManager
        public AlarmManager.AlarmClockInfo getNextAlarmClock(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IAlarmManager
        public List<AlarmManager.AlarmClockInfo> getNextAlarmClocks(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IAlarmManager
        public long getNextWakeFromIdleTime() throws RemoteException {
            return 0L;
        }

        @Override // android.app.IAlarmManager
        public boolean hasScheduleExactAlarm(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IAlarmManager
        public void remove(PendingIntent pendingIntent, IAlarmListener iAlarmListener) throws RemoteException {
        }

        @Override // android.app.IAlarmManager
        public void removeAll(String str) throws RemoteException {
        }

        @Override // android.app.IAlarmManager
        public void set(String str, int i, long j, long j2, long j3, int i2, PendingIntent pendingIntent, IAlarmListener iAlarmListener, String str2, WorkSource workSource, AlarmManager.AlarmClockInfo alarmClockInfo) throws RemoteException {
        }

        @Override // android.app.IAlarmManager
        public void setAutoPowerUp(String str) throws RemoteException {
        }

        @Override // android.app.IAlarmManager
        public boolean setTime(long j) throws RemoteException {
            return false;
        }

        @Override // android.app.IAlarmManager
        public void setTimeZone(String str) throws RemoteException {
        }
    }

    boolean canScheduleExactAlarms(String str) throws RemoteException;

    int getConfigVersion() throws RemoteException;

    AlarmManager.AlarmClockInfo getNextAlarmClock(int i) throws RemoteException;

    List<AlarmManager.AlarmClockInfo> getNextAlarmClocks(int i) throws RemoteException;

    long getNextWakeFromIdleTime() throws RemoteException;

    boolean hasScheduleExactAlarm(String str, int i) throws RemoteException;

    void remove(PendingIntent pendingIntent, IAlarmListener iAlarmListener) throws RemoteException;

    void removeAll(String str) throws RemoteException;

    void set(String str, int i, long j, long j2, long j3, int i2, PendingIntent pendingIntent, IAlarmListener iAlarmListener, String str2, WorkSource workSource, AlarmManager.AlarmClockInfo alarmClockInfo) throws RemoteException;

    void setAutoPowerUp(String str) throws RemoteException;

    boolean setTime(long j) throws RemoteException;

    void setTimeZone(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IAlarmManager {
        public static final String DESCRIPTOR = "android.app.IAlarmManager";
        static final int TRANSACTION_canScheduleExactAlarms = 8;
        static final int TRANSACTION_getConfigVersion = 10;
        static final int TRANSACTION_getNextAlarmClock = 7;
        static final int TRANSACTION_getNextAlarmClocks = 12;
        static final int TRANSACTION_getNextWakeFromIdleTime = 6;
        static final int TRANSACTION_hasScheduleExactAlarm = 9;
        static final int TRANSACTION_remove = 4;
        static final int TRANSACTION_removeAll = 5;
        static final int TRANSACTION_set = 1;
        static final int TRANSACTION_setAutoPowerUp = 11;
        static final int TRANSACTION_setTime = 2;
        static final int TRANSACTION_setTimeZone = 3;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
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

        public static IAlarmManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAlarmManager)) {
                return (IAlarmManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "set";
                case 2:
                    return "setTime";
                case 3:
                    return "setTimeZone";
                case 4:
                    return "remove";
                case 5:
                    return "removeAll";
                case 6:
                    return "getNextWakeFromIdleTime";
                case 7:
                    return "getNextAlarmClock";
                case 8:
                    return "canScheduleExactAlarms";
                case 9:
                    return "hasScheduleExactAlarm";
                case 10:
                    return "getConfigVersion";
                case 11:
                    return "setAutoPowerUp";
                case 12:
                    return "getNextAlarmClocks";
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
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    long j3 = parcel.readLong();
                    int i4 = parcel.readInt();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    IAlarmListener iAlarmListenerAsInterface = IAlarmListener.Stub.asInterface(parcel.readStrongBinder());
                    String string2 = parcel.readString();
                    WorkSource workSource = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    AlarmManager.AlarmClockInfo alarmClockInfo = (AlarmManager.AlarmClockInfo) parcel.readTypedObject(AlarmManager.AlarmClockInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    set(string, i3, j, j2, j3, i4, pendingIntent, iAlarmListenerAsInterface, string2, workSource, alarmClockInfo);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean time = setTime(j4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(time);
                    break;
                case 3:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTimeZone(string3);
                    parcel2.writeNoException();
                    break;
                case 4:
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    IAlarmListener iAlarmListenerAsInterface2 = IAlarmListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    remove(pendingIntent2, iAlarmListenerAsInterface2);
                    parcel2.writeNoException();
                    break;
                case 5:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeAll(string4);
                    parcel2.writeNoException();
                    break;
                case 6:
                    long nextWakeFromIdleTime = getNextWakeFromIdleTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(nextWakeFromIdleTime);
                    break;
                case 7:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AlarmManager.AlarmClockInfo nextAlarmClock = getNextAlarmClock(i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(nextAlarmClock, 1);
                    break;
                case 8:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanScheduleExactAlarms = canScheduleExactAlarms(string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanScheduleExactAlarms);
                    break;
                case 9:
                    String string6 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasScheduleExactAlarm = hasScheduleExactAlarm(string6, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasScheduleExactAlarm);
                    break;
                case 10:
                    int configVersion = getConfigVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(configVersion);
                    break;
                case 11:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAutoPowerUp(string7);
                    parcel2.writeNoException();
                    break;
                case 12:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AlarmManager.AlarmClockInfo> nextAlarmClocks = getNextAlarmClocks(i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(nextAlarmClocks, 1);
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAlarmManager {
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

            @Override // android.app.IAlarmManager
            public void set(String str, int i, long j, long j2, long j3, int i2, PendingIntent pendingIntent, IAlarmListener iAlarmListener, String str2, WorkSource workSource, AlarmManager.AlarmClockInfo alarmClockInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeLong(j3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeStrongInterface(iAlarmListener);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(workSource, 0);
                    parcelObtain.writeTypedObject(alarmClockInfo, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public boolean setTime(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public void setTimeZone(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public void remove(PendingIntent pendingIntent, IAlarmListener iAlarmListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeStrongInterface(iAlarmListener);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public void removeAll(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public long getNextWakeFromIdleTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public AlarmManager.AlarmClockInfo getNextAlarmClock(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AlarmManager.AlarmClockInfo) parcelObtain2.readTypedObject(AlarmManager.AlarmClockInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public boolean canScheduleExactAlarms(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public boolean hasScheduleExactAlarm(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public int getConfigVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public void setAutoPowerUp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public List<AlarmManager.AlarmClockInfo> getNextAlarmClocks(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AlarmManager.AlarmClockInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void setTime_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SET_TIME, getCallingPid(), getCallingUid());
        }

        protected void setTimeZone_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SET_TIME_ZONE, getCallingPid(), getCallingUid());
        }

        protected void getConfigVersion_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DUMP, getCallingPid(), getCallingUid());
        }

        protected void setAutoPowerUp_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DEVICE_POWER, getCallingPid(), getCallingUid());
        }
    }
}
