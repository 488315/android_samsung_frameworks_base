package android.hardware.thermal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IThermal extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$thermal$IThermal".replace('$', '.');
    public static final String HASH = "2f49c78011338b42b43d5d0e250d9b520850cc1f";
    public static final int VERSION = 2;

    CoolingDevice[] getCoolingDevices() throws RemoteException;

    CoolingDevice[] getCoolingDevicesWithType(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    TemperatureThreshold[] getTemperatureThresholds() throws RemoteException;

    TemperatureThreshold[] getTemperatureThresholdsWithType(int i) throws RemoteException;

    Temperature[] getTemperatures() throws RemoteException;

    Temperature[] getTemperaturesWithType(int i) throws RemoteException;

    void registerCoolingDeviceChangedCallbackWithType(
            ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback, int i)
            throws RemoteException;

    void registerThermalChangedCallback(IThermalChangedCallback iThermalChangedCallback)
            throws RemoteException;

    void registerThermalChangedCallbackWithType(
            IThermalChangedCallback iThermalChangedCallback, int i) throws RemoteException;

    void unregisterCoolingDeviceChangedCallback(
            ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback) throws RemoteException;

    void unregisterThermalChangedCallback(IThermalChangedCallback iThermalChangedCallback)
            throws RemoteException;

    public static class Default implements IThermal {
        @Override // android.hardware.thermal.IThermal
        public CoolingDevice[] getCoolingDevices() throws RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public CoolingDevice[] getCoolingDevicesWithType(int type) throws RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public Temperature[] getTemperatures() throws RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public Temperature[] getTemperaturesWithType(int type) throws RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public TemperatureThreshold[] getTemperatureThresholds() throws RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public TemperatureThreshold[] getTemperatureThresholdsWithType(int type)
                throws RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public void registerThermalChangedCallback(IThermalChangedCallback callback)
                throws RemoteException {}

        @Override // android.hardware.thermal.IThermal
        public void registerThermalChangedCallbackWithType(
                IThermalChangedCallback callback, int type) throws RemoteException {}

        @Override // android.hardware.thermal.IThermal
        public void unregisterThermalChangedCallback(IThermalChangedCallback callback)
                throws RemoteException {}

        @Override // android.hardware.thermal.IThermal
        public void registerCoolingDeviceChangedCallbackWithType(
                ICoolingDeviceChangedCallback callback, int type) throws RemoteException {}

        @Override // android.hardware.thermal.IThermal
        public void unregisterCoolingDeviceChangedCallback(ICoolingDeviceChangedCallback callback)
                throws RemoteException {}

        @Override // android.hardware.thermal.IThermal
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.thermal.IThermal
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IThermal {
        static final int TRANSACTION_getCoolingDevices = 1;
        static final int TRANSACTION_getCoolingDevicesWithType = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getTemperatureThresholds = 5;
        static final int TRANSACTION_getTemperatureThresholdsWithType = 6;
        static final int TRANSACTION_getTemperatures = 3;
        static final int TRANSACTION_getTemperaturesWithType = 4;
        static final int TRANSACTION_registerCoolingDeviceChangedCallbackWithType = 10;
        static final int TRANSACTION_registerThermalChangedCallback = 7;
        static final int TRANSACTION_registerThermalChangedCallbackWithType = 8;
        static final int TRANSACTION_unregisterCoolingDeviceChangedCallback = 11;
        static final int TRANSACTION_unregisterThermalChangedCallback = 9;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IThermal asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IThermal)) {
                return (IThermal) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getCoolingDevices";
                case 2:
                    return "getCoolingDevicesWithType";
                case 3:
                    return "getTemperatures";
                case 4:
                    return "getTemperaturesWithType";
                case 5:
                    return "getTemperatureThresholds";
                case 6:
                    return "getTemperatureThresholdsWithType";
                case 7:
                    return "registerThermalChangedCallback";
                case 8:
                    return "registerThermalChangedCallbackWithType";
                case 9:
                    return "unregisterThermalChangedCallback";
                case 10:
                    return "registerCoolingDeviceChangedCallbackWithType";
                case 11:
                    return "unregisterCoolingDeviceChangedCallback";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == 16777215) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == 16777214) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    CoolingDevice[] _result = getCoolingDevices();
                    reply.writeNoException();
                    reply.writeTypedArray(_result, 1);
                    return true;
                case 2:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    CoolingDevice[] _result2 = getCoolingDevicesWithType(_arg0);
                    reply.writeNoException();
                    reply.writeTypedArray(_result2, 1);
                    return true;
                case 3:
                    Temperature[] _result3 = getTemperatures();
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case 4:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    Temperature[] _result4 = getTemperaturesWithType(_arg02);
                    reply.writeNoException();
                    reply.writeTypedArray(_result4, 1);
                    return true;
                case 5:
                    TemperatureThreshold[] _result5 = getTemperatureThresholds();
                    reply.writeNoException();
                    reply.writeTypedArray(_result5, 1);
                    return true;
                case 6:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    TemperatureThreshold[] _result6 = getTemperatureThresholdsWithType(_arg03);
                    reply.writeNoException();
                    reply.writeTypedArray(_result6, 1);
                    return true;
                case 7:
                    IThermalChangedCallback _arg04 =
                            IThermalChangedCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerThermalChangedCallback(_arg04);
                    reply.writeNoException();
                    return true;
                case 8:
                    IThermalChangedCallback _arg05 =
                            IThermalChangedCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    registerThermalChangedCallbackWithType(_arg05, _arg1);
                    reply.writeNoException();
                    return true;
                case 9:
                    IThermalChangedCallback _arg06 =
                            IThermalChangedCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterThermalChangedCallback(_arg06);
                    reply.writeNoException();
                    return true;
                case 10:
                    ICoolingDeviceChangedCallback _arg07 =
                            ICoolingDeviceChangedCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    registerCoolingDeviceChangedCallbackWithType(_arg07, _arg12);
                    reply.writeNoException();
                    return true;
                case 11:
                    ICoolingDeviceChangedCallback _arg08 =
                            ICoolingDeviceChangedCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterCoolingDeviceChangedCallback(_arg08);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IThermal {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.thermal.IThermal
            public CoolingDevice[] getCoolingDevices() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getCoolingDevices is unimplemented.");
                    }
                    _reply.readException();
                    CoolingDevice[] _result =
                            (CoolingDevice[]) _reply.createTypedArray(CoolingDevice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public CoolingDevice[] getCoolingDevicesWithType(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getCoolingDevicesWithType is unimplemented.");
                    }
                    _reply.readException();
                    CoolingDevice[] _result =
                            (CoolingDevice[]) _reply.createTypedArray(CoolingDevice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public Temperature[] getTemperatures() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getTemperatures is unimplemented.");
                    }
                    _reply.readException();
                    Temperature[] _result =
                            (Temperature[]) _reply.createTypedArray(Temperature.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public Temperature[] getTemperaturesWithType(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getTemperaturesWithType is unimplemented.");
                    }
                    _reply.readException();
                    Temperature[] _result =
                            (Temperature[]) _reply.createTypedArray(Temperature.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public TemperatureThreshold[] getTemperatureThresholds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getTemperatureThresholds is unimplemented.");
                    }
                    _reply.readException();
                    TemperatureThreshold[] _result =
                            (TemperatureThreshold[])
                                    _reply.createTypedArray(TemperatureThreshold.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public TemperatureThreshold[] getTemperatureThresholdsWithType(int type)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getTemperatureThresholdsWithType is unimplemented.");
                    }
                    _reply.readException();
                    TemperatureThreshold[] _result =
                            (TemperatureThreshold[])
                                    _reply.createTypedArray(TemperatureThreshold.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void registerThermalChangedCallback(IThermalChangedCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException(
                                "Method registerThermalChangedCallback is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void registerThermalChangedCallbackWithType(
                    IThermalChangedCallback callback, int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException(
                                "Method registerThermalChangedCallbackWithType is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void unregisterThermalChangedCallback(IThermalChangedCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException(
                                "Method unregisterThermalChangedCallback is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void registerCoolingDeviceChangedCallbackWithType(
                    ICoolingDeviceChangedCallback callback, int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException(
                                "Method registerCoolingDeviceChangedCallbackWithType is"
                                        + " unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void unregisterCoolingDeviceChangedCallback(
                    ICoolingDeviceChangedCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException(
                                "Method unregisterCoolingDeviceChangedCallback is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.thermal.IThermal
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}
