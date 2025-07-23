package android.hardware.vibrator;

import android.hardware.vibrator.IVibrationSession;
import android.hardware.vibrator.IVibrator;
import android.hardware.vibrator.IVibratorCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IVibratorManager extends IInterface {
    public static final int CAP_MIXED_TRIGGER_COMPOSE = 64;
    public static final int CAP_MIXED_TRIGGER_ON = 16;
    public static final int CAP_MIXED_TRIGGER_PERFORM = 32;
    public static final int CAP_PREPARE_COMPOSE = 8;
    public static final int CAP_PREPARE_ON = 2;
    public static final int CAP_PREPARE_PERFORM = 4;
    public static final int CAP_START_SESSIONS = 256;
    public static final int CAP_SYNC = 1;
    public static final int CAP_TRIGGER_CALLBACK = 128;
    public static final String DESCRIPTOR = "android$hardware$vibrator$IVibratorManager".replace('$', '.');
    public static final String HASH = "720a16b521507c378f14c516749ae178a60dfc44";
    public static final int VERSION = 3;

    void cancelSynced() throws RemoteException;

    void clearSessions() throws RemoteException;

    int getCapabilities() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    IVibrator getVibrator(int i) throws RemoteException;

    int[] getVibratorIds() throws RemoteException;

    void prepareSynced(int[] iArr) throws RemoteException;

    IVibrationSession startSession(int[] iArr, VibrationSessionConfig vibrationSessionConfig, IVibratorCallback iVibratorCallback) throws RemoteException;

    void triggerSynced(IVibratorCallback iVibratorCallback) throws RemoteException;

    public static class Default implements IVibratorManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public void cancelSynced() throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public void clearSessions() throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public int getCapabilities() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public IVibrator getVibrator(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public int[] getVibratorIds() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public void prepareSynced(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public IVibrationSession startSession(int[] iArr, VibrationSessionConfig vibrationSessionConfig, IVibratorCallback iVibratorCallback) throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public void triggerSynced(IVibratorCallback iVibratorCallback) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IVibratorManager {
        static final int TRANSACTION_cancelSynced = 6;
        static final int TRANSACTION_clearSessions = 8;
        static final int TRANSACTION_getCapabilities = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getVibrator = 3;
        static final int TRANSACTION_getVibratorIds = 2;
        static final int TRANSACTION_prepareSynced = 4;
        static final int TRANSACTION_startSession = 7;
        static final int TRANSACTION_triggerSynced = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IVibratorManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVibratorManager)) {
                return (IVibratorManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
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
                    int capabilities = getCapabilities();
                    parcel2.writeNoException();
                    parcel2.writeInt(capabilities);
                    return true;
                case 2:
                    int[] vibratorIds = getVibratorIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(vibratorIds);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IVibrator vibrator = getVibrator(readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(vibrator);
                    return true;
                case 4:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    prepareSynced(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IVibratorCallback asInterface = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    triggerSynced(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    cancelSynced();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int[] createIntArray2 = parcel.createIntArray();
                    VibrationSessionConfig vibrationSessionConfig = (VibrationSessionConfig) parcel.readTypedObject(VibrationSessionConfig.CREATOR);
                    IVibratorCallback asInterface2 = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    IVibrationSession startSession = startSession(createIntArray2, vibrationSessionConfig, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startSession);
                    return true;
                case 8:
                    clearSessions();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVibratorManager {
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

            @Override // android.hardware.vibrator.IVibratorManager
            public int getCapabilities() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getCapabilities is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public int[] getVibratorIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getVibratorIds is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public IVibrator getVibrator(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getVibrator is unimplemented.");
                    }
                    obtain2.readException();
                    return IVibrator.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public void prepareSynced(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method prepareSynced is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public void triggerSynced(IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method triggerSynced is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public void cancelSynced() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method cancelSynced is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public IVibrationSession startSession(int[] iArr, VibrationSessionConfig vibrationSessionConfig, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(vibrationSessionConfig, 0);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method startSession is unimplemented.");
                    }
                    obtain2.readException();
                    return IVibrationSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public void clearSessions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method clearSessions is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
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

            @Override // android.hardware.vibrator.IVibratorManager
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
