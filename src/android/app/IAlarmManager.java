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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAlarmManager)) {
                return (IAlarmManager) queryLocalInterface;
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
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    long readLong3 = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    IAlarmListener asInterface = IAlarmListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString2 = parcel.readString();
                    WorkSource workSource = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    AlarmManager.AlarmClockInfo alarmClockInfo = (AlarmManager.AlarmClockInfo) parcel.readTypedObject(AlarmManager.AlarmClockInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    set(readString, readInt, readLong, readLong2, readLong3, readInt2, pendingIntent, asInterface, readString2, workSource, alarmClockInfo);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean time = setTime(readLong4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(time);
                    break;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTimeZone(readString3);
                    parcel2.writeNoException();
                    break;
                case 4:
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    IAlarmListener asInterface2 = IAlarmListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    remove(pendingIntent2, asInterface2);
                    parcel2.writeNoException();
                    break;
                case 5:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeAll(readString4);
                    parcel2.writeNoException();
                    break;
                case 6:
                    long nextWakeFromIdleTime = getNextWakeFromIdleTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(nextWakeFromIdleTime);
                    break;
                case 7:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AlarmManager.AlarmClockInfo nextAlarmClock = getNextAlarmClock(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(nextAlarmClock, 1);
                    break;
                case 8:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canScheduleExactAlarms = canScheduleExactAlarms(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canScheduleExactAlarms);
                    break;
                case 9:
                    String readString6 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasScheduleExactAlarm = hasScheduleExactAlarm(readString6, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasScheduleExactAlarm);
                    break;
                case 10:
                    int configVersion = getConfigVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(configVersion);
                    break;
                case 11:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAutoPowerUp(readString7);
                    parcel2.writeNoException();
                    break;
                case 12:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AlarmManager.AlarmClockInfo> nextAlarmClocks = getNextAlarmClocks(readInt5);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeStrongInterface(iAlarmListener);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeTypedObject(alarmClockInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public boolean setTime(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public void setTimeZone(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public void remove(PendingIntent pendingIntent, IAlarmListener iAlarmListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeStrongInterface(iAlarmListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public void removeAll(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public long getNextWakeFromIdleTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public AlarmManager.AlarmClockInfo getNextAlarmClock(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AlarmManager.AlarmClockInfo) obtain2.readTypedObject(AlarmManager.AlarmClockInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public boolean canScheduleExactAlarms(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public boolean hasScheduleExactAlarm(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public int getConfigVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public void setAutoPowerUp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IAlarmManager
            public List<AlarmManager.AlarmClockInfo> getNextAlarmClocks(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AlarmManager.AlarmClockInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
