package vendor.samsung.hardware.radio.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehRadioSatelliteIndication extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$satellite$ISehRadioSatelliteIndication".replace('$', '.');
    public static final String HASH = "ae6e84d3e4b8110a872e15980b70939aa6b152ef";
    public static final int VERSION = 1;

    void callEndReasonUpdated(int i, SehSatCallEndReason sehSatCallEndReason) throws RemoteException;

    void callNumberDisplayInfoUpdated(int i, SehSatCallDisplayInfo sehSatCallDisplayInfo) throws RemoteException;

    void callStateChanged(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void newSms(int i, byte[] bArr) throws RemoteException;

    void newSmsStatusReport(int i, SehSatStatusReport sehSatStatusReport) throws RemoteException;

    void radioStateChanged(int i, int i2) throws RemoteException;

    void registrationStateChanged(int i, SehSatRegStateResult sehSatRegStateResult) throws RemoteException;

    void requestGpsData(int i) throws RemoteException;

    void requestIccSimAuthentication(int i, SehSatSimAuthReqData sehSatSimAuthReqData) throws RemoteException;

    void signalStrenghChanged(int i, SehSatSignalStrength sehSatSignalStrength) throws RemoteException;

    void simAuthenticationFailed(int i) throws RemoteException;

    public static class Default implements ISehRadioSatelliteIndication {
        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void callStateChanged(int type) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void callEndReasonUpdated(int type, SehSatCallEndReason reason) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void callNumberDisplayInfoUpdated(int type, SehSatCallDisplayInfo info) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void registrationStateChanged(int type, SehSatRegStateResult result) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void signalStrenghChanged(int type, SehSatSignalStrength signal) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void requestGpsData(int type) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void radioStateChanged(int type, int state) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void requestIccSimAuthentication(int type, SehSatSimAuthReqData data) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void simAuthenticationFailed(int type) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void newSms(int type, byte[] pdu) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void newSmsStatusReport(int type, SehSatStatusReport data) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioSatelliteIndication {
        static final int TRANSACTION_callEndReasonUpdated = 2;
        static final int TRANSACTION_callNumberDisplayInfoUpdated = 3;
        static final int TRANSACTION_callStateChanged = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_newSms = 10;
        static final int TRANSACTION_newSmsStatusReport = 11;
        static final int TRANSACTION_radioStateChanged = 7;
        static final int TRANSACTION_registrationStateChanged = 4;
        static final int TRANSACTION_requestGpsData = 6;
        static final int TRANSACTION_requestIccSimAuthentication = 8;
        static final int TRANSACTION_signalStrenghChanged = 5;
        static final int TRANSACTION_simAuthenticationFailed = 9;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSatelliteIndication asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISehRadioSatelliteIndication)) {
                return (ISehRadioSatelliteIndication) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
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
                    callStateChanged(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    SehSatCallEndReason _arg1 = (SehSatCallEndReason) data.readTypedObject(SehSatCallEndReason.CREATOR);
                    data.enforceNoDataAvail();
                    callEndReasonUpdated(_arg02, _arg1);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    SehSatCallDisplayInfo _arg12 = (SehSatCallDisplayInfo) data.readTypedObject(SehSatCallDisplayInfo.CREATOR);
                    data.enforceNoDataAvail();
                    callNumberDisplayInfoUpdated(_arg03, _arg12);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    SehSatRegStateResult _arg13 = (SehSatRegStateResult) data.readTypedObject(SehSatRegStateResult.CREATOR);
                    data.enforceNoDataAvail();
                    registrationStateChanged(_arg04, _arg13);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    SehSatSignalStrength _arg14 = (SehSatSignalStrength) data.readTypedObject(SehSatSignalStrength.CREATOR);
                    data.enforceNoDataAvail();
                    signalStrenghChanged(_arg05, _arg14);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    requestGpsData(_arg06);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    radioStateChanged(_arg07, _arg15);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    SehSatSimAuthReqData _arg16 = (SehSatSimAuthReqData) data.readTypedObject(SehSatSimAuthReqData.CREATOR);
                    data.enforceNoDataAvail();
                    requestIccSimAuthentication(_arg08, _arg16);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    simAuthenticationFailed(_arg09);
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    byte[] _arg17 = data.createByteArray();
                    data.enforceNoDataAvail();
                    newSms(_arg010, _arg17);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    SehSatStatusReport _arg18 = (SehSatStatusReport) data.readTypedObject(SehSatStatusReport.CREATOR);
                    data.enforceNoDataAvail();
                    newSmsStatusReport(_arg011, _arg18);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISehRadioSatelliteIndication {
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void callStateChanged(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method callStateChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void callEndReasonUpdated(int type, SehSatCallEndReason reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(reason, 0);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method callEndReasonUpdated is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void callNumberDisplayInfoUpdated(int type, SehSatCallDisplayInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method callNumberDisplayInfoUpdated is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void registrationStateChanged(int type, SehSatRegStateResult result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(result, 0);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method registrationStateChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void signalStrenghChanged(int type, SehSatSignalStrength signal) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(signal, 0);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method signalStrenghChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void requestGpsData(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method requestGpsData is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void radioStateChanged(int type, int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(state);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method radioStateChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void requestIccSimAuthentication(int type, SehSatSimAuthReqData data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(data, 0);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method requestIccSimAuthentication is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void simAuthenticationFailed(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method simAuthenticationFailed is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void newSms(int type, byte[] pdu) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeByteArray(pdu);
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method newSms is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void newSmsStatusReport(int type, SehSatStatusReport data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(data, 0);
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method newSmsStatusReport is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
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
