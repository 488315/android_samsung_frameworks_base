package vendor.samsung.hardware.radio.network;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import vendor.samsung.hardware.radio.SehRadioResponseInfo;

public interface ISehRadioNetworkResponse extends IInterface {
    public static final String DESCRIPTOR =
            "vendor$samsung$hardware$radio$network$ISehRadioNetworkResponse".replace('$', '.');
    public static final String HASH = "8fb94cb4cf759f224987eb2d1908582a1f6f1600";
    public static final int VERSION = 1;

    void emergencyControlResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void emergencySearchResponse(SehRadioResponseInfo sehRadioResponseInfo, int i)
            throws RemoteException;

    void getAvailableNetworksResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehOperatorInfo[] sehOperatorInfoArr)
            throws RemoteException;

    void getCnapResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void getCsgListResponse(SehRadioResponseInfo sehRadioResponseInfo, SehCsgInfo[] sehCsgInfoArr)
            throws RemoteException;

    void getDisable2gResponse(SehRadioResponseInfo sehRadioResponseInfo, int i)
            throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getNrIconTypeResponse(SehRadioResponseInfo sehRadioResponseInfo, int i)
            throws RemoteException;

    void getNrModeResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void getPreferredNetworkListResponse(
            SehRadioResponseInfo sehRadioResponseInfo,
            SehPreferredNetworkInfo[] sehPreferredNetworkInfoArr)
            throws RemoteException;

    void getRoamingNetworkInfoViaBLEResponse(
            SehRadioResponseInfo sehRadioResponseInfo,
            SehRoamingNetworkInfo[] sehRoamingNetworkInfoArr)
            throws RemoteException;

    void getVendorSpecificConfigurationResponse(
            SehRadioResponseInfo sehRadioResponseInfo,
            SehVendorConfiguration[] sehVendorConfigurationArr)
            throws RemoteException;

    void selectCsgManualResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void sendEncodedUssdResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void sendRequestRawResponse(SehRadioResponseInfo sehRadioResponseInfo, byte[] bArr)
            throws RemoteException;

    void sendRequestStringsResponse(SehRadioResponseInfo sehRadioResponseInfo, String[] strArr)
            throws RemoteException;

    void setCurrentNetworkInfoViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo)
            throws RemoteException;

    void setDisable2gResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setImsCallListResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setNrModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setPreferredNetworkListResponse(SehRadioResponseInfo sehRadioResponseInfo)
            throws RemoteException;

    void setRoamingNetworkInfoViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo)
            throws RemoteException;

    void setScanResultViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo)
            throws RemoteException;

    void setVendorSpecificConfigurationResponse(SehRadioResponseInfo sehRadioResponseInfo)
            throws RemoteException;

    public static class Default implements ISehRadioNetworkResponse {
        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void emergencyControlResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void emergencySearchResponse(SehRadioResponseInfo info, int respEmergencySearch)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getAvailableNetworksResponse(
                SehRadioResponseInfo info, SehOperatorInfo[] networkInfos) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getCnapResponse(SehRadioResponseInfo info, int m) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getCsgListResponse(SehRadioResponseInfo info, SehCsgInfo[] csgInfos)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getDisable2gResponse(SehRadioResponseInfo info, int isDisable)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getNrIconTypeResponse(SehRadioResponseInfo info, int nrIconType)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getNrModeResponse(SehRadioResponseInfo info, int nrMode)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getPreferredNetworkListResponse(
                SehRadioResponseInfo info, SehPreferredNetworkInfo[] infos)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getRoamingNetworkInfoViaBLEResponse(
                SehRadioResponseInfo info, SehRoamingNetworkInfo[] networkInfo)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getVendorSpecificConfigurationResponse(
                SehRadioResponseInfo info, SehVendorConfiguration[] configurations)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void selectCsgManualResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void sendEncodedUssdResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void sendRequestRawResponse(SehRadioResponseInfo info, byte[] data)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void sendRequestStringsResponse(SehRadioResponseInfo info, String[] data)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setCurrentNetworkInfoViaBLEResponse(SehRadioResponseInfo info)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setDisable2gResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setImsCallListResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setNrModeResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setPreferredNetworkListResponse(SehRadioResponseInfo info)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setRoamingNetworkInfoViaBLEResponse(SehRadioResponseInfo info)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setScanResultViaBLEResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setVendorSpecificConfigurationResponse(SehRadioResponseInfo info)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISehRadioNetworkResponse {
        static final int TRANSACTION_emergencyControlResponse = 1;
        static final int TRANSACTION_emergencySearchResponse = 2;
        static final int TRANSACTION_getAvailableNetworksResponse = 3;
        static final int TRANSACTION_getCnapResponse = 4;
        static final int TRANSACTION_getCsgListResponse = 5;
        static final int TRANSACTION_getDisable2gResponse = 6;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNrIconTypeResponse = 7;
        static final int TRANSACTION_getNrModeResponse = 8;
        static final int TRANSACTION_getPreferredNetworkListResponse = 9;
        static final int TRANSACTION_getRoamingNetworkInfoViaBLEResponse = 10;
        static final int TRANSACTION_getVendorSpecificConfigurationResponse = 11;
        static final int TRANSACTION_selectCsgManualResponse = 12;
        static final int TRANSACTION_sendEncodedUssdResponse = 13;
        static final int TRANSACTION_sendRequestRawResponse = 14;
        static final int TRANSACTION_sendRequestStringsResponse = 15;
        static final int TRANSACTION_setCurrentNetworkInfoViaBLEResponse = 16;
        static final int TRANSACTION_setDisable2gResponse = 17;
        static final int TRANSACTION_setImsCallListResponse = 18;
        static final int TRANSACTION_setNrModeResponse = 19;
        static final int TRANSACTION_setPreferredNetworkListResponse = 20;
        static final int TRANSACTION_setRoamingNetworkInfoViaBLEResponse = 21;
        static final int TRANSACTION_setScanResultViaBLEResponse = 22;
        static final int TRANSACTION_setVendorSpecificConfigurationResponse = 23;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioNetworkResponse asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISehRadioNetworkResponse)) {
                return (ISehRadioNetworkResponse) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
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
                    SehRadioResponseInfo _arg0 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    emergencyControlResponse(_arg0);
                    return true;
                case 2:
                    SehRadioResponseInfo _arg02 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    emergencySearchResponse(_arg02, _arg1);
                    return true;
                case 3:
                    SehRadioResponseInfo _arg03 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehOperatorInfo[] _arg12 =
                            (SehOperatorInfo[]) data.createTypedArray(SehOperatorInfo.CREATOR);
                    data.enforceNoDataAvail();
                    getAvailableNetworksResponse(_arg03, _arg12);
                    return true;
                case 4:
                    SehRadioResponseInfo _arg04 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    getCnapResponse(_arg04, _arg13);
                    return true;
                case 5:
                    SehRadioResponseInfo _arg05 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehCsgInfo[] _arg14 = (SehCsgInfo[]) data.createTypedArray(SehCsgInfo.CREATOR);
                    data.enforceNoDataAvail();
                    getCsgListResponse(_arg05, _arg14);
                    return true;
                case 6:
                    SehRadioResponseInfo _arg06 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    getDisable2gResponse(_arg06, _arg15);
                    return true;
                case 7:
                    SehRadioResponseInfo _arg07 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    getNrIconTypeResponse(_arg07, _arg16);
                    return true;
                case 8:
                    SehRadioResponseInfo _arg08 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    getNrModeResponse(_arg08, _arg17);
                    return true;
                case 9:
                    SehRadioResponseInfo _arg09 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehPreferredNetworkInfo[] _arg18 =
                            (SehPreferredNetworkInfo[])
                                    data.createTypedArray(SehPreferredNetworkInfo.CREATOR);
                    data.enforceNoDataAvail();
                    getPreferredNetworkListResponse(_arg09, _arg18);
                    return true;
                case 10:
                    SehRadioResponseInfo _arg010 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehRoamingNetworkInfo[] _arg19 =
                            (SehRoamingNetworkInfo[])
                                    data.createTypedArray(SehRoamingNetworkInfo.CREATOR);
                    data.enforceNoDataAvail();
                    getRoamingNetworkInfoViaBLEResponse(_arg010, _arg19);
                    return true;
                case 11:
                    SehRadioResponseInfo _arg011 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehVendorConfiguration[] _arg110 =
                            (SehVendorConfiguration[])
                                    data.createTypedArray(SehVendorConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    getVendorSpecificConfigurationResponse(_arg011, _arg110);
                    return true;
                case 12:
                    SehRadioResponseInfo _arg012 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    selectCsgManualResponse(_arg012);
                    return true;
                case 13:
                    SehRadioResponseInfo _arg013 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    sendEncodedUssdResponse(_arg013);
                    return true;
                case 14:
                    SehRadioResponseInfo _arg014 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    byte[] _arg111 = data.createByteArray();
                    data.enforceNoDataAvail();
                    sendRequestRawResponse(_arg014, _arg111);
                    return true;
                case 15:
                    SehRadioResponseInfo _arg015 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    String[] _arg112 = data.createStringArray();
                    data.enforceNoDataAvail();
                    sendRequestStringsResponse(_arg015, _arg112);
                    return true;
                case 16:
                    SehRadioResponseInfo _arg016 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setCurrentNetworkInfoViaBLEResponse(_arg016);
                    return true;
                case 17:
                    SehRadioResponseInfo _arg017 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setDisable2gResponse(_arg017);
                    return true;
                case 18:
                    SehRadioResponseInfo _arg018 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setImsCallListResponse(_arg018);
                    return true;
                case 19:
                    SehRadioResponseInfo _arg019 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setNrModeResponse(_arg019);
                    return true;
                case 20:
                    SehRadioResponseInfo _arg020 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setPreferredNetworkListResponse(_arg020);
                    return true;
                case 21:
                    SehRadioResponseInfo _arg021 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setRoamingNetworkInfoViaBLEResponse(_arg021);
                    return true;
                case 22:
                    SehRadioResponseInfo _arg022 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setScanResultViaBLEResponse(_arg022);
                    return true;
                case 23:
                    SehRadioResponseInfo _arg023 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setVendorSpecificConfigurationResponse(_arg023);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISehRadioNetworkResponse {
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void emergencyControlResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method emergencyControlResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void emergencySearchResponse(SehRadioResponseInfo info, int respEmergencySearch)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(respEmergencySearch);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method emergencySearchResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getAvailableNetworksResponse(
                    SehRadioResponseInfo info, SehOperatorInfo[] networkInfos)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedArray(networkInfos, 0);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getAvailableNetworksResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getCnapResponse(SehRadioResponseInfo info, int m) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(m);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getCnapResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getCsgListResponse(SehRadioResponseInfo info, SehCsgInfo[] csgInfos)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedArray(csgInfos, 0);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getCsgListResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getDisable2gResponse(SehRadioResponseInfo info, int isDisable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(isDisable);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getDisable2gResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getNrIconTypeResponse(SehRadioResponseInfo info, int nrIconType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(nrIconType);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getNrIconTypeResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getNrModeResponse(SehRadioResponseInfo info, int nrMode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(nrMode);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getNrModeResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getPreferredNetworkListResponse(
                    SehRadioResponseInfo info, SehPreferredNetworkInfo[] infos)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedArray(infos, 0);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getPreferredNetworkListResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getRoamingNetworkInfoViaBLEResponse(
                    SehRadioResponseInfo info, SehRoamingNetworkInfo[] networkInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedArray(networkInfo, 0);
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getRoamingNetworkInfoViaBLEResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getVendorSpecificConfigurationResponse(
                    SehRadioResponseInfo info, SehVendorConfiguration[] configurations)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedArray(configurations, 0);
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getVendorSpecificConfigurationResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void selectCsgManualResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method selectCsgManualResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void sendEncodedUssdResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method sendEncodedUssdResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void sendRequestRawResponse(SehRadioResponseInfo info, byte[] data)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeByteArray(data);
                    boolean _status = this.mRemote.transact(14, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method sendRequestRawResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void sendRequestStringsResponse(SehRadioResponseInfo info, String[] data)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeStringArray(data);
                    boolean _status = this.mRemote.transact(15, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method sendRequestStringsResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setCurrentNetworkInfoViaBLEResponse(SehRadioResponseInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(16, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setCurrentNetworkInfoViaBLEResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setDisable2gResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(17, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setDisable2gResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setImsCallListResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(18, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setImsCallListResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setNrModeResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(19, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setNrModeResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setPreferredNetworkListResponse(SehRadioResponseInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(20, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setPreferredNetworkListResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setRoamingNetworkInfoViaBLEResponse(SehRadioResponseInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(21, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setRoamingNetworkInfoViaBLEResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setScanResultViaBLEResponse(SehRadioResponseInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(22, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setScanResultViaBLEResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setVendorSpecificConfigurationResponse(SehRadioResponseInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(23, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setVendorSpecificConfigurationResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
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
    }
}
