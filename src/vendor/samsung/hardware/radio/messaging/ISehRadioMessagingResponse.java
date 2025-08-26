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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISehRadioMessagingResponse)) {
                return (ISehRadioMessagingResponse) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    writeSmsToSimResponse(sehRadioResponseInfo7, i3);
                    return true;
                case 8:
                    SehRadioResponseInfo sehRadioResponseInfo8 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehCbConfigArgs sehCbConfigArgs = (SehCbConfigArgs) parcel.readTypedObject(SehCbConfigArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCellBroadcastConfigResponse(sehRadioResponseInfo8, sehCbConfigArgs);
                    return true;
                case 9:
                    SehRadioResponseInfo sehRadioResponseInfo9 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    getImsRegistrationStateResponse(sehRadioResponseInfo9, iArrCreateIntArray);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehSendSmsResult, 0);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSmsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void sendSMSExpectMoreResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehSendSmsResult, 0);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSMSExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void sendCdmaSmsResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehSendSmsResult, 0);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCdmaSmsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void sendCdmaSmsExpectMoreResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehSendSmsResult, 0);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCdmaSmsExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void getStoredMsgCountFromSimResponse(SehRadioResponseInfo sehRadioResponseInfo, SehStoredMsgCount sehStoredMsgCount) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehStoredMsgCount, 0);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getStoredMsgCountFromSimResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void readSmsFromSimResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSimMsgArgs sehSimMsgArgs) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehSimMsgArgs, 0);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method readSmsFromSimResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void writeSmsToSimResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method writeSmsToSimResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void getCellBroadcastConfigResponse(SehRadioResponseInfo sehRadioResponseInfo, SehCbConfigArgs sehCbConfigArgs) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehCbConfigArgs, 0);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCellBroadcastConfigResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
            public void getImsRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeIntArray(iArr);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getImsRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
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

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse
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
