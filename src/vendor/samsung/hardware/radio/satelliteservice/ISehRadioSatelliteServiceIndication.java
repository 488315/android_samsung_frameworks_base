package vendor.samsung.hardware.radio.satelliteservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehRadioSatelliteServiceIndication extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$satelliteservice$ISehRadioSatelliteServiceIndication".replace('$', '.');
    public static final String HASH = "1822ff8c0c8881d25bb0be28db23c4c01611eba9";
    public static final int VERSION = 1;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void onNtnSignalStrengthChanged(int i, int i2) throws RemoteException;

    void onSatelliteCapabilitiesChanged(int i, SehSatelliteCapabilities sehSatelliteCapabilities) throws RemoteException;

    void onSatelliteDatagramReceived(int i, byte[] bArr) throws RemoteException;

    void onSatelliteEcefPositionReceived(int i, int[] iArr) throws RemoteException;

    void onSatelliteModemStateChanged(int i, int i2) throws RemoteException;

    void onSatelliteRegistrationFailure(int i, int i2) throws RemoteException;

    void onSatelliteSupportedStateChanged(int i, int i2) throws RemoteException;

    void onTerrestrialNetworkAvailableChanged(int i, int i2) throws RemoteException;

    public static class Default implements ISehRadioSatelliteServiceIndication {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
        public void onNtnSignalStrengthChanged(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
        public void onSatelliteCapabilitiesChanged(int i, SehSatelliteCapabilities sehSatelliteCapabilities) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
        public void onSatelliteDatagramReceived(int i, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
        public void onSatelliteEcefPositionReceived(int i, int[] iArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
        public void onSatelliteModemStateChanged(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
        public void onSatelliteRegistrationFailure(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
        public void onSatelliteSupportedStateChanged(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
        public void onTerrestrialNetworkAvailableChanged(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioSatelliteServiceIndication {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onNtnSignalStrengthChanged = 3;
        static final int TRANSACTION_onSatelliteCapabilitiesChanged = 5;
        static final int TRANSACTION_onSatelliteDatagramReceived = 1;
        static final int TRANSACTION_onSatelliteEcefPositionReceived = 4;
        static final int TRANSACTION_onSatelliteModemStateChanged = 2;
        static final int TRANSACTION_onSatelliteRegistrationFailure = 7;
        static final int TRANSACTION_onSatelliteSupportedStateChanged = 6;
        static final int TRANSACTION_onTerrestrialNetworkAvailableChanged = 8;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSatelliteServiceIndication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioSatelliteServiceIndication)) {
                return (ISehRadioSatelliteServiceIndication) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onSatelliteDatagramReceived(readInt, createByteArray);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSatelliteModemStateChanged(readInt2, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNtnSignalStrengthChanged(readInt4, readInt5);
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onSatelliteEcefPositionReceived(readInt6, createIntArray);
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    SehSatelliteCapabilities sehSatelliteCapabilities = (SehSatelliteCapabilities) parcel.readTypedObject(SehSatelliteCapabilities.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSatelliteCapabilitiesChanged(readInt7, sehSatelliteCapabilities);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSatelliteSupportedStateChanged(readInt8, readInt9);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSatelliteRegistrationFailure(readInt10, readInt11);
                    return true;
                case 8:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTerrestrialNetworkAvailableChanged(readInt12, readInt13);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioSatelliteServiceIndication {
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

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
            public void onSatelliteDatagramReceived(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onSatelliteDatagramReceived is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
            public void onSatelliteModemStateChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onSatelliteModemStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
            public void onNtnSignalStrengthChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onNtnSignalStrengthChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
            public void onSatelliteEcefPositionReceived(int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onSatelliteEcefPositionReceived is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
            public void onSatelliteCapabilitiesChanged(int i, SehSatelliteCapabilities sehSatelliteCapabilities) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehSatelliteCapabilities, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onSatelliteCapabilitiesChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
            public void onSatelliteSupportedStateChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onSatelliteSupportedStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
            public void onSatelliteRegistrationFailure(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onSatelliteRegistrationFailure is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
            public void onTerrestrialNetworkAvailableChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onTerrestrialNetworkAvailableChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
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

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication
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
