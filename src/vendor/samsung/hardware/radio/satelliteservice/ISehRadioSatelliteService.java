package vendor.samsung.hardware.radio.satelliteservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;
import vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceIndication;
import vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse;

/* loaded from: classes6.dex */
public interface ISehRadioSatelliteService extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$satelliteservice$ISehRadioSatelliteService".replace('$', '.');
    public static final String HASH = "1822ff8c0c8881d25bb0be28db23c4c01611eba9";
    public static final int VERSION = 1;

    void abortSendingSatelliteDatagrams(int i) throws RemoteException;

    void enableTerrestrialNetworkScanWhileSatelliteModeIsOn(int i, int i2) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void requestIsSatelliteEnabled(int i) throws RemoteException;

    void requestIsSatelliteSupported(int i) throws RemoteException;

    void requestNtnSignalStrength(int i) throws RemoteException;

    void requestSatelliteCapabilities(int i) throws RemoteException;

    void requestSatelliteEnabled(int i, int i2, int i3, int i4, String str, String str2) throws RemoteException;

    void requestSatelliteModemState(int i) throws RemoteException;

    void sendSatelliteDatagram(int i, byte[] bArr, int i2) throws RemoteException;

    void setResponseFunctions(ISehRadioSatelliteServiceResponse iSehRadioSatelliteServiceResponse, ISehRadioSatelliteServiceIndication iSehRadioSatelliteServiceIndication) throws RemoteException;

    void startSendingSatellitePointingInfo(int i) throws RemoteException;

    void stopSendingSatellitePointingInfo(int i) throws RemoteException;

    void updateSystemSelectionChannels(int i, List<SehSystemSelectionSpecifier> list) throws RemoteException;

    public static class Default implements ISehRadioSatelliteService {
        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void abortSendingSatelliteDatagrams(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void enableTerrestrialNetworkScanWhileSatelliteModeIsOn(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void requestIsSatelliteEnabled(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void requestIsSatelliteSupported(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void requestNtnSignalStrength(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void requestSatelliteCapabilities(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void requestSatelliteEnabled(int i, int i2, int i3, int i4, String str, String str2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void requestSatelliteModemState(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void sendSatelliteDatagram(int i, byte[] bArr, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void setResponseFunctions(ISehRadioSatelliteServiceResponse iSehRadioSatelliteServiceResponse, ISehRadioSatelliteServiceIndication iSehRadioSatelliteServiceIndication) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void startSendingSatellitePointingInfo(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void stopSendingSatellitePointingInfo(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public void updateSystemSelectionChannels(int i, List<SehSystemSelectionSpecifier> list) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioSatelliteService {
        static final int TRANSACTION_abortSendingSatelliteDatagrams = 12;
        static final int TRANSACTION_enableTerrestrialNetworkScanWhileSatelliteModeIsOn = 9;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_requestIsSatelliteEnabled = 3;
        static final int TRANSACTION_requestIsSatelliteSupported = 4;
        static final int TRANSACTION_requestNtnSignalStrength = 8;
        static final int TRANSACTION_requestSatelliteCapabilities = 5;
        static final int TRANSACTION_requestSatelliteEnabled = 2;
        static final int TRANSACTION_requestSatelliteModemState = 7;
        static final int TRANSACTION_sendSatelliteDatagram = 6;
        static final int TRANSACTION_setResponseFunctions = 1;
        static final int TRANSACTION_startSendingSatellitePointingInfo = 10;
        static final int TRANSACTION_stopSendingSatellitePointingInfo = 11;
        static final int TRANSACTION_updateSystemSelectionChannels = 13;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSatelliteService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioSatelliteService)) {
                return (ISehRadioSatelliteService) queryLocalInterface;
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
                    ISehRadioSatelliteServiceResponse asInterface = ISehRadioSatelliteServiceResponse.Stub.asInterface(parcel.readStrongBinder());
                    ISehRadioSatelliteServiceIndication asInterface2 = ISehRadioSatelliteServiceIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestSatelliteEnabled(readInt, readInt2, readInt3, readInt4, readString, readString2);
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteEnabled(readInt5);
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteSupported(readInt6);
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestSatelliteCapabilities(readInt7);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSatelliteDatagram(readInt8, createByteArray, readInt9);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestSatelliteModemState(readInt10);
                    return true;
                case 8:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestNtnSignalStrength(readInt11);
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableTerrestrialNetworkScanWhileSatelliteModeIsOn(readInt12, readInt13);
                    return true;
                case 10:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startSendingSatellitePointingInfo(readInt14);
                    return true;
                case 11:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopSendingSatellitePointingInfo(readInt15);
                    return true;
                case 12:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    abortSendingSatelliteDatagrams(readInt16);
                    return true;
                case 13:
                    int readInt17 = parcel.readInt();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(SehSystemSelectionSpecifier.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSystemSelectionChannels(readInt17, createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioSatelliteService {
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

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void setResponseFunctions(ISehRadioSatelliteServiceResponse iSehRadioSatelliteServiceResponse, ISehRadioSatelliteServiceIndication iSehRadioSatelliteServiceIndication) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iSehRadioSatelliteServiceResponse);
                    obtain.writeStrongInterface(iSehRadioSatelliteServiceIndication);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void requestSatelliteEnabled(int i, int i2, int i3, int i4, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestSatelliteEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void requestIsSatelliteEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestIsSatelliteEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void requestIsSatelliteSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestIsSatelliteSupported is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void requestSatelliteCapabilities(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestSatelliteCapabilities is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void sendSatelliteDatagram(int i, byte[] bArr, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSatelliteDatagram is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void requestSatelliteModemState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestSatelliteModemState is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void requestNtnSignalStrength(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestNtnSignalStrength is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void enableTerrestrialNetworkScanWhileSatelliteModeIsOn(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableTerrestrialNetworkScanWhileSatelliteModeIsOn is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void startSendingSatellitePointingInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startSendingSatellitePointingInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void stopSendingSatellitePointingInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopSendingSatellitePointingInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void abortSendingSatelliteDatagrams(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method abortSendingSatelliteDatagrams is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void updateSystemSelectionChannels(int i, List<SehSystemSelectionSpecifier> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method updateSystemSelectionChannels is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
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

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
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
