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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISehRadioSatelliteService)) {
                return (ISehRadioSatelliteService) iInterfaceQueryLocalInterface;
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
                    ISehRadioSatelliteServiceResponse iSehRadioSatelliteServiceResponseAsInterface = ISehRadioSatelliteServiceResponse.Stub.asInterface(parcel.readStrongBinder());
                    ISehRadioSatelliteServiceIndication iSehRadioSatelliteServiceIndicationAsInterface = ISehRadioSatelliteServiceIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(iSehRadioSatelliteServiceResponseAsInterface, iSehRadioSatelliteServiceIndicationAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestSatelliteEnabled(i3, i4, i5, i6, string, string2);
                    return true;
                case 3:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteEnabled(i7);
                    return true;
                case 4:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteSupported(i8);
                    return true;
                case 5:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestSatelliteCapabilities(i9);
                    return true;
                case 6:
                    int i10 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSatelliteDatagram(i10, bArrCreateByteArray, i11);
                    return true;
                case 7:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestSatelliteModemState(i12);
                    return true;
                case 8:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestNtnSignalStrength(i13);
                    return true;
                case 9:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableTerrestrialNetworkScanWhileSatelliteModeIsOn(i14, i15);
                    return true;
                case 10:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startSendingSatellitePointingInfo(i16);
                    return true;
                case 11:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopSendingSatellitePointingInfo(i17);
                    return true;
                case 12:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    abortSendingSatelliteDatagrams(i18);
                    return true;
                case 13:
                    int i19 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SehSystemSelectionSpecifier.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSystemSelectionChannels(i19, arrayListCreateTypedArrayList);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSehRadioSatelliteServiceResponse);
                    parcelObtain.writeStrongInterface(iSehRadioSatelliteServiceIndication);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void requestSatelliteEnabled(int i, int i2, int i3, int i4, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestSatelliteEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void requestIsSatelliteEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestIsSatelliteEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void requestIsSatelliteSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestIsSatelliteSupported is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void requestSatelliteCapabilities(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestSatelliteCapabilities is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void sendSatelliteDatagram(int i, byte[] bArr, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSatelliteDatagram is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void requestSatelliteModemState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestSatelliteModemState is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void requestNtnSignalStrength(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestNtnSignalStrength is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void enableTerrestrialNetworkScanWhileSatelliteModeIsOn(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableTerrestrialNetworkScanWhileSatelliteModeIsOn is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void startSendingSatellitePointingInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startSendingSatellitePointingInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void stopSendingSatellitePointingInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopSendingSatellitePointingInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void abortSendingSatelliteDatagrams(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method abortSendingSatelliteDatagrams is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
            public void updateSystemSelectionChannels(int i, List<SehSystemSelectionSpecifier> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method updateSystemSelectionChannels is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
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

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteService
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
