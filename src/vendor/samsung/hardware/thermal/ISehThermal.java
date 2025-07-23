package vendor.samsung.hardware.thermal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.thermal.ISehThermalChangedCallback;

/* loaded from: classes6.dex */
public interface ISehThermal extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$thermal$ISehThermal".replace('$', '.');
    public static final String HASH = "261f5623a2c8ff2223f5f289e93242b275eadfcd";
    public static final int VERSION = 1;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    int getScenarioHint() throws RemoteException;

    SehTemperature[] getTemperatures() throws RemoteException;

    SehTemperature[] getTemperaturesWithType(int i) throws RemoteException;

    void registerThermalChangedCallback(ISehThermalChangedCallback iSehThermalChangedCallback) throws RemoteException;

    void setScenarioHint(int i) throws RemoteException;

    void unregisterThermalChangedCallback(ISehThermalChangedCallback iSehThermalChangedCallback) throws RemoteException;

    public static class Default implements ISehThermal {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public int getScenarioHint() throws RemoteException {
            return 0;
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public SehTemperature[] getTemperatures() throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public SehTemperature[] getTemperaturesWithType(int i) throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public void registerThermalChangedCallback(ISehThermalChangedCallback iSehThermalChangedCallback) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public void setScenarioHint(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public void unregisterThermalChangedCallback(ISehThermalChangedCallback iSehThermalChangedCallback) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.thermal.ISehThermal
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehThermal {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getScenarioHint = 6;
        static final int TRANSACTION_getTemperatures = 1;
        static final int TRANSACTION_getTemperaturesWithType = 2;
        static final int TRANSACTION_registerThermalChangedCallback = 3;
        static final int TRANSACTION_setScenarioHint = 5;
        static final int TRANSACTION_unregisterThermalChangedCallback = 4;

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

        public static ISehThermal asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehThermal)) {
                return (ISehThermal) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTemperatures";
                case 2:
                    return "getTemperaturesWithType";
                case 3:
                    return "registerThermalChangedCallback";
                case 4:
                    return "unregisterThermalChangedCallback";
                case 5:
                    return "setScenarioHint";
                case 6:
                    return "getScenarioHint";
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
                    SehTemperature[] temperatures = getTemperatures();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(temperatures, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SehTemperature[] temperaturesWithType = getTemperaturesWithType(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(temperaturesWithType, 1);
                    return true;
                case 3:
                    ISehThermalChangedCallback asInterface = ISehThermalChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerThermalChangedCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ISehThermalChangedCallback asInterface2 = ISehThermalChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterThermalChangedCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setScenarioHint(readInt2);
                    return true;
                case 6:
                    int scenarioHint = getScenarioHint();
                    parcel2.writeNoException();
                    parcel2.writeInt(scenarioHint);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehThermal {
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

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public SehTemperature[] getTemperatures() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getTemperatures is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehTemperature[]) obtain2.createTypedArray(SehTemperature.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public SehTemperature[] getTemperaturesWithType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getTemperaturesWithType is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehTemperature[]) obtain2.createTypedArray(SehTemperature.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public void registerThermalChangedCallback(ISehThermalChangedCallback iSehThermalChangedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iSehThermalChangedCallback);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method registerThermalChangedCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public void unregisterThermalChangedCallback(ISehThermalChangedCallback iSehThermalChangedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iSehThermalChangedCallback);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method unregisterThermalChangedCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public void setScenarioHint(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setScenarioHint is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
            public int getScenarioHint() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getScenarioHint is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.thermal.ISehThermal
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

            @Override // vendor.samsung.hardware.thermal.ISehThermal
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
