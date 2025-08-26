package vendor.samsung.hardware.radio.satelliteservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.radio.SehRadioResponseInfo;

/* loaded from: classes6.dex */
public interface ISehRadioSatelliteServiceResponse extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$satelliteservice$ISehRadioSatelliteServiceResponse".replace('$', '.');
    public static final String HASH = "1822ff8c0c8881d25bb0be28db23c4c01611eba9";
    public static final int VERSION = 1;

    void abortSendingSatelliteDatagramsResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void enableTerrestrialNetworkScanWhileSatelliteModeIsOnResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void requestIsSatelliteEnabledResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void requestIsSatelliteSupportedResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void requestNtnSignalStrengthResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void requestSatelliteCapabilitiesResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatelliteCapabilities sehSatelliteCapabilities) throws RemoteException;

    void requestSatelliteEnabledResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void requestSatelliteModemStateResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void sendSatelliteDatagramResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void startSendingSatellitePointingInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void stopSendingSatellitePointingInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void updateSystemSelectionChannelsResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    public static class Default implements ISehRadioSatelliteServiceResponse {
        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public void abortSendingSatelliteDatagramsResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public void enableTerrestrialNetworkScanWhileSatelliteModeIsOnResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public void requestIsSatelliteEnabledResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public void requestIsSatelliteSupportedResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public void requestNtnSignalStrengthResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public void requestSatelliteCapabilitiesResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatelliteCapabilities sehSatelliteCapabilities) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public void requestSatelliteEnabledResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public void requestSatelliteModemStateResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public void sendSatelliteDatagramResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public void startSendingSatellitePointingInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public void stopSendingSatellitePointingInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public void updateSystemSelectionChannelsResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioSatelliteServiceResponse {
        static final int TRANSACTION_abortSendingSatelliteDatagramsResponse = 11;
        static final int TRANSACTION_enableTerrestrialNetworkScanWhileSatelliteModeIsOnResponse = 8;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_requestIsSatelliteEnabledResponse = 2;
        static final int TRANSACTION_requestIsSatelliteSupportedResponse = 3;
        static final int TRANSACTION_requestNtnSignalStrengthResponse = 7;
        static final int TRANSACTION_requestSatelliteCapabilitiesResponse = 4;
        static final int TRANSACTION_requestSatelliteEnabledResponse = 1;
        static final int TRANSACTION_requestSatelliteModemStateResponse = 6;
        static final int TRANSACTION_sendSatelliteDatagramResponse = 5;
        static final int TRANSACTION_startSendingSatellitePointingInfoResponse = 9;
        static final int TRANSACTION_stopSendingSatellitePointingInfoResponse = 10;
        static final int TRANSACTION_updateSystemSelectionChannelsResponse = 12;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSatelliteServiceResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISehRadioSatelliteServiceResponse)) {
                return (ISehRadioSatelliteServiceResponse) iInterfaceQueryLocalInterface;
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
                    SehRadioResponseInfo sehRadioResponseInfo = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestSatelliteEnabledResponse(sehRadioResponseInfo);
                    return true;
                case 2:
                    SehRadioResponseInfo sehRadioResponseInfo2 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteEnabledResponse(sehRadioResponseInfo2, i3);
                    return true;
                case 3:
                    SehRadioResponseInfo sehRadioResponseInfo3 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteSupportedResponse(sehRadioResponseInfo3, i4);
                    return true;
                case 4:
                    SehRadioResponseInfo sehRadioResponseInfo4 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatelliteCapabilities sehSatelliteCapabilities = (SehSatelliteCapabilities) parcel.readTypedObject(SehSatelliteCapabilities.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestSatelliteCapabilitiesResponse(sehRadioResponseInfo4, sehSatelliteCapabilities);
                    return true;
                case 5:
                    SehRadioResponseInfo sehRadioResponseInfo5 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSatelliteDatagramResponse(sehRadioResponseInfo5);
                    return true;
                case 6:
                    SehRadioResponseInfo sehRadioResponseInfo6 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestSatelliteModemStateResponse(sehRadioResponseInfo6, i5);
                    return true;
                case 7:
                    SehRadioResponseInfo sehRadioResponseInfo7 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestNtnSignalStrengthResponse(sehRadioResponseInfo7, i6);
                    return true;
                case 8:
                    SehRadioResponseInfo sehRadioResponseInfo8 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    enableTerrestrialNetworkScanWhileSatelliteModeIsOnResponse(sehRadioResponseInfo8);
                    return true;
                case 9:
                    SehRadioResponseInfo sehRadioResponseInfo9 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    startSendingSatellitePointingInfoResponse(sehRadioResponseInfo9);
                    return true;
                case 10:
                    SehRadioResponseInfo sehRadioResponseInfo10 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopSendingSatellitePointingInfoResponse(sehRadioResponseInfo10);
                    return true;
                case 11:
                    SehRadioResponseInfo sehRadioResponseInfo11 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    abortSendingSatelliteDatagramsResponse(sehRadioResponseInfo11);
                    return true;
                case 12:
                    SehRadioResponseInfo sehRadioResponseInfo12 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSystemSelectionChannelsResponse(sehRadioResponseInfo12);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioSatelliteServiceResponse {
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

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
            public void requestSatelliteEnabledResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestSatelliteEnabledResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
            public void requestIsSatelliteEnabledResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestIsSatelliteEnabledResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
            public void requestIsSatelliteSupportedResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestIsSatelliteSupportedResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
            public void requestSatelliteCapabilitiesResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatelliteCapabilities sehSatelliteCapabilities) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehSatelliteCapabilities, 0);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestSatelliteCapabilitiesResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
            public void sendSatelliteDatagramResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSatelliteDatagramResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
            public void requestSatelliteModemStateResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestSatelliteModemStateResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
            public void requestNtnSignalStrengthResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestNtnSignalStrengthResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
            public void enableTerrestrialNetworkScanWhileSatelliteModeIsOnResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableTerrestrialNetworkScanWhileSatelliteModeIsOnResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
            public void startSendingSatellitePointingInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startSendingSatellitePointingInfoResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
            public void stopSendingSatellitePointingInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopSendingSatellitePointingInfoResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
            public void abortSendingSatelliteDatagramsResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method abortSendingSatelliteDatagramsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
            public void updateSystemSelectionChannelsResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method updateSystemSelectionChannelsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
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

            @Override // vendor.samsung.hardware.radio.satelliteservice.ISehRadioSatelliteServiceResponse
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
