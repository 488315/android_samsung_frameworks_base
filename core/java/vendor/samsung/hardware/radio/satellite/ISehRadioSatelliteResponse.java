package vendor.samsung.hardware.radio.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import vendor.samsung.hardware.radio.SehRadioResponseInfo;

public interface ISehRadioSatelliteResponse extends IInterface {
    public static final String DESCRIPTOR =
            "vendor$samsung$hardware$radio$satellite$ISehRadioSatelliteResponse".replace('$', '.');
    public static final String HASH = "ae6e84d3e4b8110a872e15980b70939aa6b152ef";
    public static final int VERSION = 1;

    void answerResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void dialResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void getAbsoluteRfChannelNumberResponse(SehRadioResponseInfo sehRadioResponseInfo, int i)
            throws RemoteException;

    void getCallEndReasonResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehSatCallEndReason sehSatCallEndReason)
            throws RemoteException;

    void getCallStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatCall[] sehSatCallArr)
            throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getRegistrationStateResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehSatRegStateResult sehSatRegStateResult)
            throws RemoteException;

    void getSatelliteIdResponse(SehRadioResponseInfo sehRadioResponseInfo, int i)
            throws RemoteException;

    void getSerialNumberResponse(SehRadioResponseInfo sehRadioResponseInfo, String str)
            throws RemoteException;

    void getSignalStrengthResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehSatSignalStrength sehSatSignalStrength)
            throws RemoteException;

    void getTxPowerResponse(SehRadioResponseInfo sehRadioResponseInfo, int i)
            throws RemoteException;

    void hangupResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void sendIccSimAuthenticationResponse(SehRadioResponseInfo sehRadioResponseInfo)
            throws RemoteException;

    void sendRawAtCommandResponse(SehRadioResponseInfo sehRadioResponseInfo, String str)
            throws RemoteException;

    void sendSMSExpectMoreResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehSatSendSmsResult sehSatSendSmsResult)
            throws RemoteException;

    void sendSmsResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehSatSendSmsResult sehSatSendSmsResult)
            throws RemoteException;

    void setDsiConfigResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setGpsInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setImeiResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setImsiResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setNetworkQueryModeResponse(SehRadioResponseInfo sehRadioResponseInfo)
            throws RemoteException;

    void setPowerResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setSignalStrengthReportResponse(SehRadioResponseInfo sehRadioResponseInfo)
            throws RemoteException;

    void setSignalThresholdReportResponse(SehRadioResponseInfo sehRadioResponseInfo)
            throws RemoteException;

    void setSmscAddressResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void startDtmfResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void startNetworkSearchResponse(SehRadioResponseInfo sehRadioResponseInfo)
            throws RemoteException;

    void stopDtmfResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    public static class Default implements ISehRadioSatelliteResponse {
        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void answerResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void dialResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getCallEndReasonResponse(SehRadioResponseInfo info, SehSatCallEndReason reason)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getCallStateResponse(SehRadioResponseInfo info, SehSatCall[] list)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void hangupResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void startDtmfResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void stopDtmfResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getRegistrationStateResponse(
                SehRadioResponseInfo info, SehSatRegStateResult result) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getSignalStrengthResponse(
                SehRadioResponseInfo info, SehSatSignalStrength signal) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setNetworkQueryModeResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setSignalStrengthReportResponse(SehRadioResponseInfo info)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setSignalThresholdReportResponse(SehRadioResponseInfo info)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setPowerResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setGpsInfoResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setImsiResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void sendIccSimAuthenticationResponse(SehRadioResponseInfo info)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setImeiResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getSerialNumberResponse(SehRadioResponseInfo info, String sn)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getAbsoluteRfChannelNumberResponse(SehRadioResponseInfo info, int arfcn)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getTxPowerResponse(SehRadioResponseInfo info, int tx_power)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void sendSmsResponse(SehRadioResponseInfo info, SehSatSendSmsResult result)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void sendSMSExpectMoreResponse(SehRadioResponseInfo info, SehSatSendSmsResult result)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setSmscAddressResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getSatelliteIdResponse(SehRadioResponseInfo info, int id)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setDsiConfigResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void sendRawAtCommandResponse(SehRadioResponseInfo info, String response)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void startNetworkSearchResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISehRadioSatelliteResponse {
        static final int TRANSACTION_answerResponse = 1;
        static final int TRANSACTION_dialResponse = 2;
        static final int TRANSACTION_getAbsoluteRfChannelNumberResponse = 19;
        static final int TRANSACTION_getCallEndReasonResponse = 3;
        static final int TRANSACTION_getCallStateResponse = 4;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getRegistrationStateResponse = 8;
        static final int TRANSACTION_getSatelliteIdResponse = 24;
        static final int TRANSACTION_getSerialNumberResponse = 18;
        static final int TRANSACTION_getSignalStrengthResponse = 9;
        static final int TRANSACTION_getTxPowerResponse = 20;
        static final int TRANSACTION_hangupResponse = 5;
        static final int TRANSACTION_sendIccSimAuthenticationResponse = 16;
        static final int TRANSACTION_sendRawAtCommandResponse = 26;
        static final int TRANSACTION_sendSMSExpectMoreResponse = 22;
        static final int TRANSACTION_sendSmsResponse = 21;
        static final int TRANSACTION_setDsiConfigResponse = 25;
        static final int TRANSACTION_setGpsInfoResponse = 14;
        static final int TRANSACTION_setImeiResponse = 17;
        static final int TRANSACTION_setImsiResponse = 15;
        static final int TRANSACTION_setNetworkQueryModeResponse = 10;
        static final int TRANSACTION_setPowerResponse = 13;
        static final int TRANSACTION_setSignalStrengthReportResponse = 11;
        static final int TRANSACTION_setSignalThresholdReportResponse = 12;
        static final int TRANSACTION_setSmscAddressResponse = 23;
        static final int TRANSACTION_startDtmfResponse = 6;
        static final int TRANSACTION_startNetworkSearchResponse = 27;
        static final int TRANSACTION_stopDtmfResponse = 7;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSatelliteResponse asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISehRadioSatelliteResponse)) {
                return (ISehRadioSatelliteResponse) iin;
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
                    answerResponse(_arg0);
                    return true;
                case 2:
                    SehRadioResponseInfo _arg02 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    dialResponse(_arg02);
                    return true;
                case 3:
                    SehRadioResponseInfo _arg03 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatCallEndReason _arg1 =
                            (SehSatCallEndReason) data.readTypedObject(SehSatCallEndReason.CREATOR);
                    data.enforceNoDataAvail();
                    getCallEndReasonResponse(_arg03, _arg1);
                    return true;
                case 4:
                    SehRadioResponseInfo _arg04 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatCall[] _arg12 = (SehSatCall[]) data.createTypedArray(SehSatCall.CREATOR);
                    data.enforceNoDataAvail();
                    getCallStateResponse(_arg04, _arg12);
                    return true;
                case 5:
                    SehRadioResponseInfo _arg05 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    hangupResponse(_arg05);
                    return true;
                case 6:
                    SehRadioResponseInfo _arg06 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    startDtmfResponse(_arg06);
                    return true;
                case 7:
                    SehRadioResponseInfo _arg07 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    stopDtmfResponse(_arg07);
                    return true;
                case 8:
                    SehRadioResponseInfo _arg08 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatRegStateResult _arg13 =
                            (SehSatRegStateResult)
                                    data.readTypedObject(SehSatRegStateResult.CREATOR);
                    data.enforceNoDataAvail();
                    getRegistrationStateResponse(_arg08, _arg13);
                    return true;
                case 9:
                    SehRadioResponseInfo _arg09 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatSignalStrength _arg14 =
                            (SehSatSignalStrength)
                                    data.readTypedObject(SehSatSignalStrength.CREATOR);
                    data.enforceNoDataAvail();
                    getSignalStrengthResponse(_arg09, _arg14);
                    return true;
                case 10:
                    SehRadioResponseInfo _arg010 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setNetworkQueryModeResponse(_arg010);
                    return true;
                case 11:
                    SehRadioResponseInfo _arg011 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setSignalStrengthReportResponse(_arg011);
                    return true;
                case 12:
                    SehRadioResponseInfo _arg012 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setSignalThresholdReportResponse(_arg012);
                    return true;
                case 13:
                    SehRadioResponseInfo _arg013 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setPowerResponse(_arg013);
                    return true;
                case 14:
                    SehRadioResponseInfo _arg014 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setGpsInfoResponse(_arg014);
                    return true;
                case 15:
                    SehRadioResponseInfo _arg015 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setImsiResponse(_arg015);
                    return true;
                case 16:
                    SehRadioResponseInfo _arg016 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    sendIccSimAuthenticationResponse(_arg016);
                    return true;
                case 17:
                    SehRadioResponseInfo _arg017 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setImeiResponse(_arg017);
                    return true;
                case 18:
                    SehRadioResponseInfo _arg018 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    getSerialNumberResponse(_arg018, _arg15);
                    return true;
                case 19:
                    SehRadioResponseInfo _arg019 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    getAbsoluteRfChannelNumberResponse(_arg019, _arg16);
                    return true;
                case 20:
                    SehRadioResponseInfo _arg020 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    getTxPowerResponse(_arg020, _arg17);
                    return true;
                case 21:
                    SehRadioResponseInfo _arg021 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatSendSmsResult _arg18 =
                            (SehSatSendSmsResult) data.readTypedObject(SehSatSendSmsResult.CREATOR);
                    data.enforceNoDataAvail();
                    sendSmsResponse(_arg021, _arg18);
                    return true;
                case 22:
                    SehRadioResponseInfo _arg022 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatSendSmsResult _arg19 =
                            (SehSatSendSmsResult) data.readTypedObject(SehSatSendSmsResult.CREATOR);
                    data.enforceNoDataAvail();
                    sendSMSExpectMoreResponse(_arg022, _arg19);
                    return true;
                case 23:
                    SehRadioResponseInfo _arg023 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setSmscAddressResponse(_arg023);
                    return true;
                case 24:
                    SehRadioResponseInfo _arg024 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    getSatelliteIdResponse(_arg024, _arg110);
                    return true;
                case 25:
                    SehRadioResponseInfo _arg025 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setDsiConfigResponse(_arg025);
                    return true;
                case 26:
                    SehRadioResponseInfo _arg026 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    String _arg111 = data.readString();
                    data.enforceNoDataAvail();
                    sendRawAtCommandResponse(_arg026, _arg111);
                    return true;
                case 27:
                    SehRadioResponseInfo _arg027 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    startNetworkSearchResponse(_arg027);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISehRadioSatelliteResponse {
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void answerResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method answerResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void dialResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method dialResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getCallEndReasonResponse(
                    SehRadioResponseInfo info, SehSatCallEndReason reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(reason, 0);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getCallEndReasonResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getCallStateResponse(SehRadioResponseInfo info, SehSatCall[] list)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedArray(list, 0);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getCallStateResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void hangupResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method hangupResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void startDtmfResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method startDtmfResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void stopDtmfResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method stopDtmfResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getRegistrationStateResponse(
                    SehRadioResponseInfo info, SehSatRegStateResult result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(result, 0);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getSignalStrengthResponse(
                    SehRadioResponseInfo info, SehSatSignalStrength signal) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(signal, 0);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getSignalStrengthResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setNetworkQueryModeResponse(SehRadioResponseInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setNetworkQueryModeResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setSignalStrengthReportResponse(SehRadioResponseInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setSignalStrengthReportResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setSignalThresholdReportResponse(SehRadioResponseInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setSignalThresholdReportResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setPowerResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setPowerResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setGpsInfoResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(14, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setGpsInfoResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setImsiResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(15, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setImsiResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendIccSimAuthenticationResponse(SehRadioResponseInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(16, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method sendIccSimAuthenticationResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setImeiResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(17, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setImeiResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getSerialNumberResponse(SehRadioResponseInfo info, String sn)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeString(sn);
                    boolean _status = this.mRemote.transact(18, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getSerialNumberResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getAbsoluteRfChannelNumberResponse(SehRadioResponseInfo info, int arfcn)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(arfcn);
                    boolean _status = this.mRemote.transact(19, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getAbsoluteRfChannelNumberResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getTxPowerResponse(SehRadioResponseInfo info, int tx_power)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(tx_power);
                    boolean _status = this.mRemote.transact(20, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getTxPowerResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendSmsResponse(SehRadioResponseInfo info, SehSatSendSmsResult result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(result, 0);
                    boolean _status = this.mRemote.transact(21, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method sendSmsResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendSMSExpectMoreResponse(
                    SehRadioResponseInfo info, SehSatSendSmsResult result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(result, 0);
                    boolean _status = this.mRemote.transact(22, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method sendSMSExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setSmscAddressResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(23, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setSmscAddressResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getSatelliteIdResponse(SehRadioResponseInfo info, int id)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(id);
                    boolean _status = this.mRemote.transact(24, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getSatelliteIdResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setDsiConfigResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(25, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setDsiConfigResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendRawAtCommandResponse(SehRadioResponseInfo info, String response)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeString(response);
                    boolean _status = this.mRemote.transact(26, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method sendRawAtCommandResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void startNetworkSearchResponse(SehRadioResponseInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(27, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method startNetworkSearchResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
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
