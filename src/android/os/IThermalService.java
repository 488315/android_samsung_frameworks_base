package android.os;

import android.os.IThermalEventListener;
import android.os.IThermalHeadroomListener;
import android.os.IThermalStatusListener;

/* loaded from: classes3.dex */
public interface IThermalService extends IInterface {

    public static class Default implements IThermalService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IThermalService
        public CoolingDevice[] getCurrentCoolingDevices() throws RemoteException {
            return null;
        }

        @Override // android.os.IThermalService
        public CoolingDevice[] getCurrentCoolingDevicesWithType(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IThermalService
        public Temperature[] getCurrentTemperatures() throws RemoteException {
            return null;
        }

        @Override // android.os.IThermalService
        public Temperature[] getCurrentTemperaturesWithType(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IThermalService
        public int getCurrentThermalStatus() throws RemoteException {
            return 0;
        }

        @Override // android.os.IThermalService
        public float getThermalHeadroom(int i) throws RemoteException {
            return 0.0f;
        }

        @Override // android.os.IThermalService
        public float[] getThermalHeadroomThresholds() throws RemoteException {
            return null;
        }

        @Override // android.os.IThermalService
        public boolean registerThermalEventListener(IThermalEventListener iThermalEventListener) throws RemoteException {
            return false;
        }

        @Override // android.os.IThermalService
        public boolean registerThermalEventListenerWithType(IThermalEventListener iThermalEventListener, int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IThermalService
        public boolean registerThermalHeadroomListener(IThermalHeadroomListener iThermalHeadroomListener) throws RemoteException {
            return false;
        }

        @Override // android.os.IThermalService
        public boolean registerThermalStatusListener(IThermalStatusListener iThermalStatusListener) throws RemoteException {
            return false;
        }

        @Override // android.os.IThermalService
        public boolean unregisterThermalEventListener(IThermalEventListener iThermalEventListener) throws RemoteException {
            return false;
        }

        @Override // android.os.IThermalService
        public boolean unregisterThermalHeadroomListener(IThermalHeadroomListener iThermalHeadroomListener) throws RemoteException {
            return false;
        }

        @Override // android.os.IThermalService
        public boolean unregisterThermalStatusListener(IThermalStatusListener iThermalStatusListener) throws RemoteException {
            return false;
        }
    }

    CoolingDevice[] getCurrentCoolingDevices() throws RemoteException;

    CoolingDevice[] getCurrentCoolingDevicesWithType(int i) throws RemoteException;

    Temperature[] getCurrentTemperatures() throws RemoteException;

    Temperature[] getCurrentTemperaturesWithType(int i) throws RemoteException;

    int getCurrentThermalStatus() throws RemoteException;

    float getThermalHeadroom(int i) throws RemoteException;

    float[] getThermalHeadroomThresholds() throws RemoteException;

    boolean registerThermalEventListener(IThermalEventListener iThermalEventListener) throws RemoteException;

    boolean registerThermalEventListenerWithType(IThermalEventListener iThermalEventListener, int i) throws RemoteException;

    boolean registerThermalHeadroomListener(IThermalHeadroomListener iThermalHeadroomListener) throws RemoteException;

    boolean registerThermalStatusListener(IThermalStatusListener iThermalStatusListener) throws RemoteException;

    boolean unregisterThermalEventListener(IThermalEventListener iThermalEventListener) throws RemoteException;

    boolean unregisterThermalHeadroomListener(IThermalHeadroomListener iThermalHeadroomListener) throws RemoteException;

    boolean unregisterThermalStatusListener(IThermalStatusListener iThermalStatusListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IThermalService {
        public static final String DESCRIPTOR = "android.os.IThermalService";
        static final int TRANSACTION_getCurrentCoolingDevices = 9;
        static final int TRANSACTION_getCurrentCoolingDevicesWithType = 10;
        static final int TRANSACTION_getCurrentTemperatures = 4;
        static final int TRANSACTION_getCurrentTemperaturesWithType = 5;
        static final int TRANSACTION_getCurrentThermalStatus = 8;
        static final int TRANSACTION_getThermalHeadroom = 11;
        static final int TRANSACTION_getThermalHeadroomThresholds = 12;
        static final int TRANSACTION_registerThermalEventListener = 1;
        static final int TRANSACTION_registerThermalEventListenerWithType = 2;
        static final int TRANSACTION_registerThermalHeadroomListener = 13;
        static final int TRANSACTION_registerThermalStatusListener = 6;
        static final int TRANSACTION_unregisterThermalEventListener = 3;
        static final int TRANSACTION_unregisterThermalHeadroomListener = 14;
        static final int TRANSACTION_unregisterThermalStatusListener = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IThermalService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IThermalService)) {
                return (IThermalService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerThermalEventListener";
                case 2:
                    return "registerThermalEventListenerWithType";
                case 3:
                    return "unregisterThermalEventListener";
                case 4:
                    return "getCurrentTemperatures";
                case 5:
                    return "getCurrentTemperaturesWithType";
                case 6:
                    return "registerThermalStatusListener";
                case 7:
                    return "unregisterThermalStatusListener";
                case 8:
                    return "getCurrentThermalStatus";
                case 9:
                    return "getCurrentCoolingDevices";
                case 10:
                    return "getCurrentCoolingDevicesWithType";
                case 11:
                    return "getThermalHeadroom";
                case 12:
                    return "getThermalHeadroomThresholds";
                case 13:
                    return "registerThermalHeadroomListener";
                case 14:
                    return "unregisterThermalHeadroomListener";
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
                    IThermalEventListener iThermalEventListenerAsInterface = IThermalEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterThermalEventListener = registerThermalEventListener(iThermalEventListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterThermalEventListener);
                    return true;
                case 2:
                    IThermalEventListener iThermalEventListenerAsInterface2 = IThermalEventListener.Stub.asInterface(parcel.readStrongBinder());
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterThermalEventListenerWithType = registerThermalEventListenerWithType(iThermalEventListenerAsInterface2, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterThermalEventListenerWithType);
                    return true;
                case 3:
                    IThermalEventListener iThermalEventListenerAsInterface3 = IThermalEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterThermalEventListener = unregisterThermalEventListener(iThermalEventListenerAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterThermalEventListener);
                    return true;
                case 4:
                    Temperature[] currentTemperatures = getCurrentTemperatures();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentTemperatures, 1);
                    return true;
                case 5:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Temperature[] currentTemperaturesWithType = getCurrentTemperaturesWithType(i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentTemperaturesWithType, 1);
                    return true;
                case 6:
                    IThermalStatusListener iThermalStatusListenerAsInterface = IThermalStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterThermalStatusListener = registerThermalStatusListener(iThermalStatusListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterThermalStatusListener);
                    return true;
                case 7:
                    IThermalStatusListener iThermalStatusListenerAsInterface2 = IThermalStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterThermalStatusListener = unregisterThermalStatusListener(iThermalStatusListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterThermalStatusListener);
                    return true;
                case 8:
                    int currentThermalStatus = getCurrentThermalStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentThermalStatus);
                    return true;
                case 9:
                    CoolingDevice[] currentCoolingDevices = getCurrentCoolingDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentCoolingDevices, 1);
                    return true;
                case 10:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CoolingDevice[] currentCoolingDevicesWithType = getCurrentCoolingDevicesWithType(i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentCoolingDevicesWithType, 1);
                    return true;
                case 11:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float thermalHeadroom = getThermalHeadroom(i6);
                    parcel2.writeNoException();
                    parcel2.writeFloat(thermalHeadroom);
                    return true;
                case 12:
                    float[] thermalHeadroomThresholds = getThermalHeadroomThresholds();
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(thermalHeadroomThresholds);
                    return true;
                case 13:
                    IThermalHeadroomListener iThermalHeadroomListenerAsInterface = IThermalHeadroomListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterThermalHeadroomListener = registerThermalHeadroomListener(iThermalHeadroomListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterThermalHeadroomListener);
                    return true;
                case 14:
                    IThermalHeadroomListener iThermalHeadroomListenerAsInterface2 = IThermalHeadroomListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterThermalHeadroomListener = unregisterThermalHeadroomListener(iThermalHeadroomListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterThermalHeadroomListener);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IThermalService {
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

            @Override // android.os.IThermalService
            public boolean registerThermalEventListener(IThermalEventListener iThermalEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iThermalEventListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public boolean registerThermalEventListenerWithType(IThermalEventListener iThermalEventListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iThermalEventListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public boolean unregisterThermalEventListener(IThermalEventListener iThermalEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iThermalEventListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public Temperature[] getCurrentTemperatures() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Temperature[]) parcelObtain2.createTypedArray(Temperature.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public Temperature[] getCurrentTemperaturesWithType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Temperature[]) parcelObtain2.createTypedArray(Temperature.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public boolean registerThermalStatusListener(IThermalStatusListener iThermalStatusListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iThermalStatusListener);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public boolean unregisterThermalStatusListener(IThermalStatusListener iThermalStatusListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iThermalStatusListener);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public int getCurrentThermalStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public CoolingDevice[] getCurrentCoolingDevices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CoolingDevice[]) parcelObtain2.createTypedArray(CoolingDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public CoolingDevice[] getCurrentCoolingDevicesWithType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CoolingDevice[]) parcelObtain2.createTypedArray(CoolingDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public float getThermalHeadroom(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public float[] getThermalHeadroomThresholds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createFloatArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public boolean registerThermalHeadroomListener(IThermalHeadroomListener iThermalHeadroomListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iThermalHeadroomListener);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public boolean unregisterThermalHeadroomListener(IThermalHeadroomListener iThermalHeadroomListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iThermalHeadroomListener);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
