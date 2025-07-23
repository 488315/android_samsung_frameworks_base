package vendor.samsung.hardware.radio.messaging;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.radio.SehRadioResponseInfo;

/* loaded from: classes6.dex */
public interface ISehRadioMessagingResponse extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$messaging$ISehRadioMessagingResponse".replace('$', '.');
    public static final String HASH = "c1a8596db57e3bcc8e4e86f1eb7b2df7839ca140";
    public static final int VERSION = 1;

    void getCellBroadcastConfigResponse(SehRadioResponseInfo sehRadioResponseInfo, SehCbConfigArgs sehCbConfigArgs) throws RemoteException;

    void getImsRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, int[] iArr) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getStoredMsgCountFromSimResponse(SehRadioResponseInfo sehRadioResponseInfo, SehStoredMsgCount sehStoredMsgCount) throws RemoteException;

    void readSmsFromSimResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSimMsgArgs sehSimMsgArgs) throws RemoteException;

    void sendCdmaSmsExpectMoreResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException;

    void sendCdmaSmsResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException;

    void sendSMSExpectMoreResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException;

    void sendSmsResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException;

    void writeSmsToSimResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    public static class Default implements ISehRadioMessagingResponse {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void getCellBroadcastConfigResponse(SehRadioResponseInfo sehRadioResponseInfo, SehCbConfigArgs sehCbConfigArgs) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void getImsRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, int[] iArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void getStoredMsgCountFromSimResponse(SehRadioResponseInfo sehRadioResponseInfo, SehStoredMsgCount sehStoredMsgCount) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void readSmsFromSimResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSimMsgArgs sehSimMsgArgs) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void sendCdmaSmsExpectMoreResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void sendCdmaSmsResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void sendSMSExpectMoreResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void sendSmsResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public void writeSmsToSimResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioMessagingResponse {
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioMessagingResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioMessagingResponse)) {
                return (ISehRadioMessagingResponse) queryLocalInterface;
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
                    SehSendSmsResult sehSendSmsResult = (SehSendSmsResult) parcel.readTypedObject(SehSendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSmsResponse(sehRadioResponseInfo, sehSendSmsResult);
                    return true;
                case 2:
                    SehRadioResponseInfo sehRadioResponseInfo2 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSendSmsResult sehSendSmsResult2 = (SehSendSmsResult) parcel.readTypedObject(SehSendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSMSExpectMoreResponse(sehRadioResponseInfo2, sehSendSmsResult2);
                    return true;
                case 3:
                    SehRadioResponseInfo sehRadioResponseInfo3 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSendSmsResult sehSendSmsResult3 = (SehSendSmsResult) parcel.readTypedObject(SehSendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaSmsResponse(sehRadioResponseInfo3, sehSendSmsResult3);
                    return true;
                case 4:
                    SehRadioResponseInfo sehRadioResponseInfo4 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSendSmsResult sehSendSmsResult4 = (SehSendSmsResult) parcel.readTypedObject(SehSendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaSmsExpectMoreResponse(sehRadioResponseInfo4, sehSendSmsResult4);
                    return true;
                case 5:
                    SehRadioResponseInfo sehRadioResponseInfo5 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehStoredMsgCount sehStoredMsgCount = (SehStoredMsgCount) parcel.readTypedObject(SehStoredMsgCount.CREATOR);
                    parcel.enforceNoDataAvail();
                    getStoredMsgCountFromSimResponse(sehRadioResponseInfo5, sehStoredMsgCount);
                    return true;
                case 6:
                    SehRadioResponseInfo sehRadioResponseInfo6 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSimMsgArgs sehSimMsgArgs = (SehSimMsgArgs) parcel.readTypedObject(SehSimMsgArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    readSmsFromSimResponse(sehRadioResponseInfo6, sehSimMsgArgs);
                    return true;
                case 7:
                    SehRadioResponseInfo sehRadioResponseInfo7 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    writeSmsToSimResponse(sehRadioResponseInfo7, readInt);
                    return true;
                case 8:
                    SehRadioResponseInfo sehRadioResponseInfo8 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehCbConfigArgs sehCbConfigArgs = (SehCbConfigArgs) parcel.readTypedObject(SehCbConfigArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCellBroadcastConfigResponse(sehRadioResponseInfo8, sehCbConfigArgs);
                    return true;
                case 9:
                    SehRadioResponseInfo sehRadioResponseInfo9 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    getImsRegistrationStateResponse(sehRadioResponseInfo9, createIntArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioMessagingResponse {
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

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void sendSmsResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSendSmsResult, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSmsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void sendSMSExpectMoreResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSendSmsResult, 0);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSMSExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void sendCdmaSmsResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSendSmsResult, 0);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCdmaSmsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void sendCdmaSmsExpectMoreResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSendSmsResult, 0);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCdmaSmsExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void getStoredMsgCountFromSimResponse(SehRadioResponseInfo sehRadioResponseInfo, SehStoredMsgCount sehStoredMsgCount) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehStoredMsgCount, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getStoredMsgCountFromSimResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void readSmsFromSimResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSimMsgArgs sehSimMsgArgs) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSimMsgArgs, 0);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method readSmsFromSimResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void writeSmsToSimResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method writeSmsToSimResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void getCellBroadcastConfigResponse(SehRadioResponseInfo sehRadioResponseInfo, SehCbConfigArgs sehCbConfigArgs) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehCbConfigArgs, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCellBroadcastConfigResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void getImsRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeIntArray(iArr);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getImsRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
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

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
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
