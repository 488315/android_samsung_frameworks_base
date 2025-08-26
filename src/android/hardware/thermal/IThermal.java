package android.hardware.thermal;

import android.hardware.thermal.ICoolingDeviceChangedCallback;
import android.hardware.thermal.IThermalChangedCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IThermal extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$thermal$IThermal".replace('$', '.');
    public static final String HASH = "4c4fc474c40b64963eb8d78b713b1095fecd72f0";
    public static final int VERSION = 3;

    float forecastSkinTemperature(int i) throws RemoteException;

    CoolingDevice[] getCoolingDevices() throws RemoteException;

    CoolingDevice[] getCoolingDevicesWithType(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    TemperatureThreshold[] getTemperatureThresholds() throws RemoteException;

    TemperatureThreshold[] getTemperatureThresholdsWithType(int i) throws RemoteException;

    Temperature[] getTemperatures() throws RemoteException;

    Temperature[] getTemperaturesWithType(int i) throws RemoteException;

    void registerCoolingDeviceChangedCallbackWithType(ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback, int i) throws RemoteException;

    void registerThermalChangedCallback(IThermalChangedCallback iThermalChangedCallback) throws RemoteException;

    void registerThermalChangedCallbackWithType(IThermalChangedCallback iThermalChangedCallback, int i) throws RemoteException;

    void unregisterCoolingDeviceChangedCallback(ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback) throws RemoteException;

    void unregisterThermalChangedCallback(IThermalChangedCallback iThermalChangedCallback) throws RemoteException;

    public static class Default implements IThermal {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public float forecastSkinTemperature(int i) throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.thermal.IThermal
        public CoolingDevice[] getCoolingDevices() throws RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public CoolingDevice[] getCoolingDevicesWithType(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.thermal.IThermal
        public TemperatureThreshold[] getTemperatureThresholds() throws RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public TemperatureThreshold[] getTemperatureThresholdsWithType(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public Temperature[] getTemperatures() throws RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public Temperature[] getTemperaturesWithType(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public void registerCoolingDeviceChangedCallbackWithType(ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback, int i) throws RemoteException {
        }

        @Override // android.hardware.thermal.IThermal
        public void registerThermalChangedCallback(IThermalChangedCallback iThermalChangedCallback) throws RemoteException {
        }

        @Override // android.hardware.thermal.IThermal
        public void registerThermalChangedCallbackWithType(IThermalChangedCallback iThermalChangedCallback, int i) throws RemoteException {
        }

        @Override // android.hardware.thermal.IThermal
        public void unregisterCoolingDeviceChangedCallback(ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback) throws RemoteException {
        }

        @Override // android.hardware.thermal.IThermal
        public void unregisterThermalChangedCallback(IThermalChangedCallback iThermalChangedCallback) throws RemoteException {
        }

        @Override // android.hardware.thermal.IThermal
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IThermal {
        static final int TRANSACTION_forecastSkinTemperature = 12;
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

        public static IThermal asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IThermal)) {
                return (IThermal) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                case 12:
                    return "forecastSkinTemperature";
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
                    CoolingDevice[] coolingDevices = getCoolingDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(coolingDevices, 1);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CoolingDevice[] coolingDevicesWithType = getCoolingDevicesWithType(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(coolingDevicesWithType, 1);
                    return true;
                case 3:
                    Temperature[] temperatures = getTemperatures();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(temperatures, 1);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Temperature[] temperaturesWithType = getTemperaturesWithType(i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(temperaturesWithType, 1);
                    return true;
                case 5:
                    TemperatureThreshold[] temperatureThresholds = getTemperatureThresholds();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(temperatureThresholds, 1);
                    return true;
                case 6:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    TemperatureThreshold[] temperatureThresholdsWithType = getTemperatureThresholdsWithType(i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(temperatureThresholdsWithType, 1);
                    return true;
                case 7:
                    IThermalChangedCallback iThermalChangedCallbackAsInterface = IThermalChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerThermalChangedCallback(iThermalChangedCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IThermalChangedCallback iThermalChangedCallbackAsInterface2 = IThermalChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerThermalChangedCallbackWithType(iThermalChangedCallbackAsInterface2, i6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IThermalChangedCallback iThermalChangedCallbackAsInterface3 = IThermalChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterThermalChangedCallback(iThermalChangedCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    ICoolingDeviceChangedCallback iCoolingDeviceChangedCallbackAsInterface = ICoolingDeviceChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCoolingDeviceChangedCallbackWithType(iCoolingDeviceChangedCallbackAsInterface, i7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ICoolingDeviceChangedCallback iCoolingDeviceChangedCallbackAsInterface2 = ICoolingDeviceChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCoolingDeviceChangedCallback(iCoolingDeviceChangedCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float fForecastSkinTemperature = forecastSkinTemperature(i8);
                    parcel2.writeNoException();
                    parcel2.writeFloat(fForecastSkinTemperature);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IThermal {
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

            @Override // android.hardware.thermal.IThermal
            public CoolingDevice[] getCoolingDevices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getCoolingDevices is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (CoolingDevice[]) parcelObtain2.createTypedArray(CoolingDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public CoolingDevice[] getCoolingDevicesWithType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getCoolingDevicesWithType is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (CoolingDevice[]) parcelObtain2.createTypedArray(CoolingDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public Temperature[] getTemperatures() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getTemperatures is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (Temperature[]) parcelObtain2.createTypedArray(Temperature.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public Temperature[] getTemperaturesWithType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getTemperaturesWithType is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (Temperature[]) parcelObtain2.createTypedArray(Temperature.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public TemperatureThreshold[] getTemperatureThresholds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getTemperatureThresholds is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (TemperatureThreshold[]) parcelObtain2.createTypedArray(TemperatureThreshold.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public TemperatureThreshold[] getTemperatureThresholdsWithType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getTemperatureThresholdsWithType is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (TemperatureThreshold[]) parcelObtain2.createTypedArray(TemperatureThreshold.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void registerThermalChangedCallback(IThermalChangedCallback iThermalChangedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iThermalChangedCallback);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method registerThermalChangedCallback is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void registerThermalChangedCallbackWithType(IThermalChangedCallback iThermalChangedCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iThermalChangedCallback);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method registerThermalChangedCallbackWithType is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void unregisterThermalChangedCallback(IThermalChangedCallback iThermalChangedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iThermalChangedCallback);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method unregisterThermalChangedCallback is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void registerCoolingDeviceChangedCallbackWithType(ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCoolingDeviceChangedCallback);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method registerCoolingDeviceChangedCallbackWithType is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void unregisterCoolingDeviceChangedCallback(ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCoolingDeviceChangedCallback);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method unregisterCoolingDeviceChangedCallback is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public float forecastSkinTemperature(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method forecastSkinTemperature is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.thermal.IThermal
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
