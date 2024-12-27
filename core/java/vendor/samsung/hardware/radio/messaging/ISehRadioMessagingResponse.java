package vendor.samsung.hardware.radio.messaging;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import vendor.samsung.hardware.radio.SehRadioResponseInfo;

public interface ISehRadioMessagingResponse extends IInterface {
    public static final String DESCRIPTOR =
            "vendor$samsung$hardware$radio$messaging$ISehRadioMessagingResponse".replace('$', '.');
    public static final String HASH = "c1a8596db57e3bcc8e4e86f1eb7b2df7839ca140";
    public static final int VERSION = 1;

    void getCellBroadcastConfigResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehCbConfigArgs sehCbConfigArgs)
            throws RemoteException;

    void getImsRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, int[] iArr)
            throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getStoredMsgCountFromSimResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehStoredMsgCount sehStoredMsgCount)
            throws RemoteException;

    void readSmsFromSimResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehSimMsgArgs sehSimMsgArgs)
            throws RemoteException;

    void sendCdmaSmsExpectMoreResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult)
            throws RemoteException;

    void sendCdmaSmsResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult)
            throws RemoteException;

    void sendSMSExpectMoreResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult)
            throws RemoteException;

    void sendSmsResponse(
            SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult)
            throws RemoteException;

    void writeSmsToSimResponse(SehRadioResponseInfo sehRadioResponseInfo, int i)
            throws RemoteException;

    public static class Default implements ISehRadioMessagingResponse {
        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void sendSmsResponse(SehRadioResponseInfo info, SehSendSmsResult sms)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void sendSMSExpectMoreResponse(SehRadioResponseInfo info, SehSendSmsResult sms)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void sendCdmaSmsResponse(SehRadioResponseInfo info, SehSendSmsResult sms)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void sendCdmaSmsExpectMoreResponse(SehRadioResponseInfo info, SehSendSmsResult sms)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void getStoredMsgCountFromSimResponse(
                SehRadioResponseInfo info, SehStoredMsgCount storedMsgInfo)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void readSmsFromSimResponse(SehRadioResponseInfo info, SehSimMsgArgs simMsg)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void writeSmsToSimResponse(SehRadioResponseInfo info, int index)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void getCellBroadcastConfigResponse(
                SehRadioResponseInfo info, SehCbConfigArgs configs) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void getImsRegistrationStateResponse(SehRadioResponseInfo info, int[] regState)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISehRadioMessagingResponse {
        static final int TRANSACTION_getCellBroadcastConfigResponse = 8;
        static final int TRANSACTION_getImsRegistrationStateResponse = 9;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getStoredMsgCountFromSimResponse = 5;
        static final int TRANSACTION_readSmsFromSimResponse = 6;
        static final int TRANSACTION_sendCdmaSmsExpectMoreResponse = 4;
        static final int TRANSACTION_sendCdmaSmsResponse = 3;
        static final int TRANSACTION_sendSMSExpectMoreResponse = 2;
        static final int TRANSACTION_sendSmsResponse = 1;
        static final int TRANSACTION_writeSmsToSimResponse = 7;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioMessagingResponse asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISehRadioMessagingResponse)) {
                return (ISehRadioMessagingResponse) iin;
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
                    SehSendSmsResult _arg1 =
                            (SehSendSmsResult) data.readTypedObject(SehSendSmsResult.CREATOR);
                    data.enforceNoDataAvail();
                    sendSmsResponse(_arg0, _arg1);
                    return true;
                case 2:
                    SehRadioResponseInfo _arg02 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSendSmsResult _arg12 =
                            (SehSendSmsResult) data.readTypedObject(SehSendSmsResult.CREATOR);
                    data.enforceNoDataAvail();
                    sendSMSExpectMoreResponse(_arg02, _arg12);
                    return true;
                case 3:
                    SehRadioResponseInfo _arg03 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSendSmsResult _arg13 =
                            (SehSendSmsResult) data.readTypedObject(SehSendSmsResult.CREATOR);
                    data.enforceNoDataAvail();
                    sendCdmaSmsResponse(_arg03, _arg13);
                    return true;
                case 4:
                    SehRadioResponseInfo _arg04 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSendSmsResult _arg14 =
                            (SehSendSmsResult) data.readTypedObject(SehSendSmsResult.CREATOR);
                    data.enforceNoDataAvail();
                    sendCdmaSmsExpectMoreResponse(_arg04, _arg14);
                    return true;
                case 5:
                    SehRadioResponseInfo _arg05 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehStoredMsgCount _arg15 =
                            (SehStoredMsgCount) data.readTypedObject(SehStoredMsgCount.CREATOR);
                    data.enforceNoDataAvail();
                    getStoredMsgCountFromSimResponse(_arg05, _arg15);
                    return true;
                case 6:
                    SehRadioResponseInfo _arg06 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSimMsgArgs _arg16 =
                            (SehSimMsgArgs) data.readTypedObject(SehSimMsgArgs.CREATOR);
                    data.enforceNoDataAvail();
                    readSmsFromSimResponse(_arg06, _arg16);
                    return true;
                case 7:
                    SehRadioResponseInfo _arg07 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    writeSmsToSimResponse(_arg07, _arg17);
                    return true;
                case 8:
                    SehRadioResponseInfo _arg08 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehCbConfigArgs _arg18 =
                            (SehCbConfigArgs) data.readTypedObject(SehCbConfigArgs.CREATOR);
                    data.enforceNoDataAvail();
                    getCellBroadcastConfigResponse(_arg08, _arg18);
                    return true;
                case 9:
                    SehRadioResponseInfo _arg09 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int[] _arg19 = data.createIntArray();
                    data.enforceNoDataAvail();
                    getImsRegistrationStateResponse(_arg09, _arg19);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISehRadioMessagingResponse {
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

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void sendSmsResponse(SehRadioResponseInfo info, SehSendSmsResult sms)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(sms, 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method sendSmsResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void sendSMSExpectMoreResponse(SehRadioResponseInfo info, SehSendSmsResult sms)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(sms, 0);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method sendSMSExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void sendCdmaSmsResponse(SehRadioResponseInfo info, SehSendSmsResult sms)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(sms, 0);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method sendCdmaSmsResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void sendCdmaSmsExpectMoreResponse(
                    SehRadioResponseInfo info, SehSendSmsResult sms) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(sms, 0);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method sendCdmaSmsExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void getStoredMsgCountFromSimResponse(
                    SehRadioResponseInfo info, SehStoredMsgCount storedMsgInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(storedMsgInfo, 0);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getStoredMsgCountFromSimResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void readSmsFromSimResponse(SehRadioResponseInfo info, SehSimMsgArgs simMsg)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(simMsg, 0);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method readSmsFromSimResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void writeSmsToSimResponse(SehRadioResponseInfo info, int index)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(index);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method writeSmsToSimResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void getCellBroadcastConfigResponse(
                    SehRadioResponseInfo info, SehCbConfigArgs configs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(configs, 0);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getCellBroadcastConfigResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void getImsRegistrationStateResponse(SehRadioResponseInfo info, int[] regState)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeIntArray(regState);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getImsRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
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

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
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
