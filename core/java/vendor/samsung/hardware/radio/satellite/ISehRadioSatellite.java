package vendor.samsung.hardware.radio.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISehRadioSatellite extends IInterface {
    public static final String DESCRIPTOR =
            "vendor$samsung$hardware$radio$satellite$ISehRadioSatellite".replace('$', '.');
    public static final String HASH = "ae6e84d3e4b8110a872e15980b70939aa6b152ef";
    public static final int VERSION = 1;

    void answer(int i) throws RemoteException;

    void dial(int i, String str) throws RemoteException;

    void getAbsoluteRfChannelNumber(int i) throws RemoteException;

    void getCallEndReason(int i) throws RemoteException;

    void getCallState(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getRegistrationState(int i) throws RemoteException;

    void getSatelliteId(int i) throws RemoteException;

    void getSerialNumber(int i) throws RemoteException;

    void getSignalStrength(int i) throws RemoteException;

    void getTxPower(int i) throws RemoteException;

    void hangup(int i) throws RemoteException;

    void sendIccSimAuthentication(int i, SehSatSimAuthRespData sehSatSimAuthRespData)
            throws RemoteException;

    void sendRawAtCommand(int i, String str) throws RemoteException;

    void sendSMSExpectMore(int i, SehSatSmsMessage sehSatSmsMessage) throws RemoteException;

    void sendSms(int i, SehSatSmsMessage sehSatSmsMessage) throws RemoteException;

    void setDsiConfig(int i, int i2, int i3) throws RemoteException;

    void setGpsInfo(int i, int i2, int i3) throws RemoteException;

    void setImei(int i, String str) throws RemoteException;

    void setImsi(int i, String str) throws RemoteException;

    void setNetworkQueryMode(int i, int i2) throws RemoteException;

    void setPower(int i, int i2) throws RemoteException;

    void setResponseFunctions(
            ISehRadioSatelliteResponse iSehRadioSatelliteResponse,
            ISehRadioSatelliteIndication iSehRadioSatelliteIndication)
            throws RemoteException;

    void setSignalStrengthReport(int i, boolean z) throws RemoteException;

    void setSignalThresholdReport(int i, int i2, int[] iArr) throws RemoteException;

    void setSmscAddress(int i, String str) throws RemoteException;

    void startDtmf(int i, String str) throws RemoteException;

    void startNetworkSearch(int i) throws RemoteException;

    void stopDtmf(int i) throws RemoteException;

    public static class Default implements ISehRadioSatellite {
        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void answer(int serial) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void dial(int serial, String address) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void hangup(int serial) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getCallEndReason(int serial) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getCallState(int serial) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void startDtmf(int serial, String s) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void stopDtmf(int serial) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getRegistrationState(int serial) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getSignalStrength(int serial) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setNetworkQueryMode(int serial, int mode) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setSignalStrengthReport(int serial, boolean enable) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setSignalThresholdReport(int serial, int type, int[] thresholds)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setPower(int serial, int state) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setGpsInfo(int serial, int longitude, int latitude) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setImsi(int serial, String imsi) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void sendIccSimAuthentication(int serial, SehSatSimAuthRespData data)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setImei(int serial, String imei) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getSerialNumber(int serial) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getAbsoluteRfChannelNumber(int serial) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getTxPower(int serial) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void sendSMSExpectMore(int serial, SehSatSmsMessage message)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void sendSms(int serial, SehSatSmsMessage message) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setSmscAddress(int serial, String smsc) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getSatelliteId(int serial) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setDsiConfig(int serial, int enable, int dsi) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void sendRawAtCommand(int serial, String cmd) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void startNetworkSearch(int serial) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setResponseFunctions(
                ISehRadioSatelliteResponse radioResponse,
                ISehRadioSatelliteIndication radioIndication)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISehRadioSatellite {
        static final int TRANSACTION_answer = 1;
        static final int TRANSACTION_dial = 2;
        static final int TRANSACTION_getAbsoluteRfChannelNumber = 19;
        static final int TRANSACTION_getCallEndReason = 4;
        static final int TRANSACTION_getCallState = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getRegistrationState = 8;
        static final int TRANSACTION_getSatelliteId = 24;
        static final int TRANSACTION_getSerialNumber = 18;
        static final int TRANSACTION_getSignalStrength = 9;
        static final int TRANSACTION_getTxPower = 20;
        static final int TRANSACTION_hangup = 3;
        static final int TRANSACTION_sendIccSimAuthentication = 16;
        static final int TRANSACTION_sendRawAtCommand = 26;
        static final int TRANSACTION_sendSMSExpectMore = 21;
        static final int TRANSACTION_sendSms = 22;
        static final int TRANSACTION_setDsiConfig = 25;
        static final int TRANSACTION_setGpsInfo = 14;
        static final int TRANSACTION_setImei = 17;
        static final int TRANSACTION_setImsi = 15;
        static final int TRANSACTION_setNetworkQueryMode = 10;
        static final int TRANSACTION_setPower = 13;
        static final int TRANSACTION_setResponseFunctions = 28;
        static final int TRANSACTION_setSignalStrengthReport = 11;
        static final int TRANSACTION_setSignalThresholdReport = 12;
        static final int TRANSACTION_setSmscAddress = 23;
        static final int TRANSACTION_startDtmf = 6;
        static final int TRANSACTION_startNetworkSearch = 27;
        static final int TRANSACTION_stopDtmf = 7;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSatellite asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISehRadioSatellite)) {
                return (ISehRadioSatellite) iin;
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
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    answer(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    dial(_arg02, _arg1);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    hangup(_arg03);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    getCallEndReason(_arg04);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    getCallState(_arg05);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    startDtmf(_arg06, _arg12);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    stopDtmf(_arg07);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    data.enforceNoDataAvail();
                    getRegistrationState(_arg08);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    getSignalStrength(_arg09);
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    setNetworkQueryMode(_arg010, _arg13);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    boolean _arg14 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSignalStrengthReport(_arg011, _arg14);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    int _arg15 = data.readInt();
                    int[] _arg2 = data.createIntArray();
                    data.enforceNoDataAvail();
                    setSignalThresholdReport(_arg012, _arg15, _arg2);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    setPower(_arg013, _arg16);
                    return true;
                case 14:
                    int _arg014 = data.readInt();
                    int _arg17 = data.readInt();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    setGpsInfo(_arg014, _arg17, _arg22);
                    return true;
                case 15:
                    int _arg015 = data.readInt();
                    String _arg18 = data.readString();
                    data.enforceNoDataAvail();
                    setImsi(_arg015, _arg18);
                    return true;
                case 16:
                    int _arg016 = data.readInt();
                    SehSatSimAuthRespData _arg19 =
                            (SehSatSimAuthRespData)
                                    data.readTypedObject(SehSatSimAuthRespData.CREATOR);
                    data.enforceNoDataAvail();
                    sendIccSimAuthentication(_arg016, _arg19);
                    return true;
                case 17:
                    int _arg017 = data.readInt();
                    String _arg110 = data.readString();
                    data.enforceNoDataAvail();
                    setImei(_arg017, _arg110);
                    return true;
                case 18:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    getSerialNumber(_arg018);
                    return true;
                case 19:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    getAbsoluteRfChannelNumber(_arg019);
                    return true;
                case 20:
                    int _arg020 = data.readInt();
                    data.enforceNoDataAvail();
                    getTxPower(_arg020);
                    return true;
                case 21:
                    int _arg021 = data.readInt();
                    SehSatSmsMessage _arg111 =
                            (SehSatSmsMessage) data.readTypedObject(SehSatSmsMessage.CREATOR);
                    data.enforceNoDataAvail();
                    sendSMSExpectMore(_arg021, _arg111);
                    return true;
                case 22:
                    int _arg022 = data.readInt();
                    SehSatSmsMessage _arg112 =
                            (SehSatSmsMessage) data.readTypedObject(SehSatSmsMessage.CREATOR);
                    data.enforceNoDataAvail();
                    sendSms(_arg022, _arg112);
                    return true;
                case 23:
                    int _arg023 = data.readInt();
                    String _arg113 = data.readString();
                    data.enforceNoDataAvail();
                    setSmscAddress(_arg023, _arg113);
                    return true;
                case 24:
                    int _arg024 = data.readInt();
                    data.enforceNoDataAvail();
                    getSatelliteId(_arg024);
                    return true;
                case 25:
                    int _arg025 = data.readInt();
                    int _arg114 = data.readInt();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    setDsiConfig(_arg025, _arg114, _arg23);
                    return true;
                case 26:
                    int _arg026 = data.readInt();
                    String _arg115 = data.readString();
                    data.enforceNoDataAvail();
                    sendRawAtCommand(_arg026, _arg115);
                    return true;
                case 27:
                    int _arg027 = data.readInt();
                    data.enforceNoDataAvail();
                    startNetworkSearch(_arg027);
                    return true;
                case 28:
                    ISehRadioSatelliteResponse _arg028 =
                            ISehRadioSatelliteResponse.Stub.asInterface(data.readStrongBinder());
                    ISehRadioSatelliteIndication _arg116 =
                            ISehRadioSatelliteIndication.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setResponseFunctions(_arg028, _arg116);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISehRadioSatellite {
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void answer(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method answer is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void dial(int serial, String address) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeString(address);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method dial is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void hangup(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method hangup is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getCallEndReason(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getCallEndReason is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getCallState(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getCallState is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void startDtmf(int serial, String s) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeString(s);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method startDtmf is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void stopDtmf(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method stopDtmf is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getRegistrationState(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getRegistrationState is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getSignalStrength(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getSignalStrength is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setNetworkQueryMode(int serial, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(mode);
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setNetworkQueryMode is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setSignalStrengthReport(int serial, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeBoolean(enable);
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setSignalStrengthReport is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setSignalThresholdReport(int serial, int type, int[] thresholds)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(type);
                    _data.writeIntArray(thresholds);
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setSignalThresholdReport is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setPower(int serial, int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(state);
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setPower is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setGpsInfo(int serial, int longitude, int latitude) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(longitude);
                    _data.writeInt(latitude);
                    boolean _status = this.mRemote.transact(14, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setGpsInfo is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setImsi(int serial, String imsi) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeString(imsi);
                    boolean _status = this.mRemote.transact(15, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setImsi is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendIccSimAuthentication(int serial, SehSatSimAuthRespData data)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeTypedObject(data, 0);
                    boolean _status = this.mRemote.transact(16, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method sendIccSimAuthentication is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setImei(int serial, String imei) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeString(imei);
                    boolean _status = this.mRemote.transact(17, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setImei is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getSerialNumber(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(18, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getSerialNumber is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getAbsoluteRfChannelNumber(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(19, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getAbsoluteRfChannelNumber is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getTxPower(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(20, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getTxPower is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendSMSExpectMore(int serial, SehSatSmsMessage message)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeTypedObject(message, 0);
                    boolean _status = this.mRemote.transact(21, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method sendSMSExpectMore is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendSms(int serial, SehSatSmsMessage message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeTypedObject(message, 0);
                    boolean _status = this.mRemote.transact(22, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method sendSms is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setSmscAddress(int serial, String smsc) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeString(smsc);
                    boolean _status = this.mRemote.transact(23, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setSmscAddress is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getSatelliteId(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(24, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getSatelliteId is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setDsiConfig(int serial, int enable, int dsi) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(enable);
                    _data.writeInt(dsi);
                    boolean _status = this.mRemote.transact(25, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setDsiConfig is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendRawAtCommand(int serial, String cmd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeString(cmd);
                    boolean _status = this.mRemote.transact(26, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method sendRawAtCommand is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void startNetworkSearch(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(27, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method startNetworkSearch is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setResponseFunctions(
                    ISehRadioSatelliteResponse radioResponse,
                    ISehRadioSatelliteIndication radioIndication)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(radioResponse);
                    _data.writeStrongInterface(radioIndication);
                    boolean _status = this.mRemote.transact(28, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
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
