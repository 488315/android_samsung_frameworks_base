package android.hardware.power;

import android.media.tv.interactive.TvInteractiveAppService;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IPowerHintSession extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$power$IPowerHintSession".replace('$', '.');
    public static final String HASH = "13171cf98a48de298baf85167633376ea3db4ea0";
    public static final int VERSION = 6;

    void close() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    SessionConfig getSessionConfig() throws RemoteException;

    void pause() throws RemoteException;

    void reportActualWorkDuration(WorkDuration[] workDurationArr) throws RemoteException;

    void resume() throws RemoteException;

    void sendHint(int i) throws RemoteException;

    void setMode(int i, boolean z) throws RemoteException;

    void setThreads(int[] iArr) throws RemoteException;

    void updateTargetWorkDuration(long j) throws RemoteException;

    public static class Default implements IPowerHintSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.power.IPowerHintSession
        public void close() throws RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.power.IPowerHintSession
        public SessionConfig getSessionConfig() throws RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPowerHintSession
        public void pause() throws RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void reportActualWorkDuration(WorkDuration[] workDurationArr) throws RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void resume() throws RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void sendHint(int i) throws RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void setMode(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void setThreads(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public void updateTargetWorkDuration(long j) throws RemoteException {
        }

        @Override // android.hardware.power.IPowerHintSession
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IPowerHintSession {
        static final int TRANSACTION_close = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSessionConfig = 9;
        static final int TRANSACTION_pause = 3;
        static final int TRANSACTION_reportActualWorkDuration = 2;
        static final int TRANSACTION_resume = 4;
        static final int TRANSACTION_sendHint = 6;
        static final int TRANSACTION_setMode = 8;
        static final int TRANSACTION_setThreads = 7;
        static final int TRANSACTION_updateTargetWorkDuration = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IPowerHintSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPowerHintSession)) {
                return (IPowerHintSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateTargetWorkDuration";
                case 2:
                    return "reportActualWorkDuration";
                case 3:
                    return TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PAUSE;
                case 4:
                    return "resume";
                case 5:
                    return "close";
                case 6:
                    return "sendHint";
                case 7:
                    return "setThreads";
                case 8:
                    return "setMode";
                case 9:
                    return "getSessionConfig";
                default:
                    switch (i) {
                        case 16777214:
                            return "getInterfaceHash";
                        case 16777215:
                            return "getInterfaceVersion";
                        default:
                            return null;
                    }
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateTargetWorkDuration(readLong);
                    return true;
                case 2:
                    WorkDuration[] workDurationArr = (WorkDuration[]) parcel.createTypedArray(WorkDuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportActualWorkDuration(workDurationArr);
                    return true;
                case 3:
                    pause();
                    return true;
                case 4:
                    resume();
                    return true;
                case 5:
                    close();
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendHint(readInt);
                    return true;
                case 7:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setThreads(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMode(readInt2, readBoolean);
                    return true;
                case 9:
                    SessionConfig sessionConfig = getSessionConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionConfig, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPowerHintSession {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.power.IPowerHintSession
            public void updateTargetWorkDuration(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method updateTargetWorkDuration is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void reportActualWorkDuration(WorkDuration[] workDurationArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(workDurationArr, 0);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method reportActualWorkDuration is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void pause() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method pause is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void resume() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method resume is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void sendHint(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendHint is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void setThreads(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setThreads is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public void setMode(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public SessionConfig getSessionConfig() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSessionConfig is unimplemented.");
                    }
                    obtain2.readException();
                    return (SessionConfig) obtain2.readTypedObject(SessionConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPowerHintSession
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.power.IPowerHintSession
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
