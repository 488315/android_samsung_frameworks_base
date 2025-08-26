package android.hardware.radio.messaging;

import android.hardware.radio.RadioResponseInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioMessagingResponse extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$messaging$IRadioMessagingResponse".replace('$', '.');
    public static final String HASH = "b28416394e6595c08e97c0473855eb05eed1baed";
    public static final int VERSION = 4;

    void acknowledgeIncomingGsmSmsWithPduResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void acknowledgeLastIncomingCdmaSmsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void acknowledgeLastIncomingGsmSmsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void acknowledgeRequest(int i) throws RemoteException;

    @Deprecated
    void deleteSmsOnRuimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void deleteSmsOnSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void getCdmaBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr) throws RemoteException;

    void getGsmBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getSmscAddressResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void reportSmsMemoryStatusResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void sendCdmaSmsExpectMoreResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    @Deprecated
    void sendCdmaSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    void sendImsSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    void sendSmsExpectMoreResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    void sendSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    @Deprecated
    void setCdmaBroadcastActivationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void setCdmaBroadcastConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setGsmBroadcastActivationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setGsmBroadcastConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSmscAddressResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void writeSmsToRuimResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void writeSmsToSimResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    public static class Default implements IRadioMessagingResponse {
        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void acknowledgeIncomingGsmSmsWithPduResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void acknowledgeLastIncomingCdmaSmsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void acknowledgeLastIncomingGsmSmsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void acknowledgeRequest(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void deleteSmsOnRuimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void deleteSmsOnSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void getCdmaBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void getGsmBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void getSmscAddressResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void reportSmsMemoryStatusResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void sendCdmaSmsExpectMoreResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void sendCdmaSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void sendImsSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void sendSmsExpectMoreResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void sendSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void setCdmaBroadcastActivationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void setCdmaBroadcastConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void setGsmBroadcastActivationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void setGsmBroadcastConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void setSmscAddressResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void writeSmsToRuimResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public void writeSmsToSimResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingResponse
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioMessagingResponse {
        static final int TRANSACTION_acknowledgeIncomingGsmSmsWithPduResponse = 1;
        static final int TRANSACTION_acknowledgeLastIncomingCdmaSmsResponse = 2;
        static final int TRANSACTION_acknowledgeLastIncomingGsmSmsResponse = 3;
        static final int TRANSACTION_acknowledgeRequest = 4;
        static final int TRANSACTION_deleteSmsOnRuimResponse = 5;
        static final int TRANSACTION_deleteSmsOnSimResponse = 6;
        static final int TRANSACTION_getCdmaBroadcastConfigResponse = 7;
        static final int TRANSACTION_getGsmBroadcastConfigResponse = 8;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSmscAddressResponse = 9;
        static final int TRANSACTION_reportSmsMemoryStatusResponse = 10;
        static final int TRANSACTION_sendCdmaSmsExpectMoreResponse = 11;
        static final int TRANSACTION_sendCdmaSmsResponse = 12;
        static final int TRANSACTION_sendImsSmsResponse = 13;
        static final int TRANSACTION_sendSmsExpectMoreResponse = 14;
        static final int TRANSACTION_sendSmsResponse = 15;
        static final int TRANSACTION_setCdmaBroadcastActivationResponse = 16;
        static final int TRANSACTION_setCdmaBroadcastConfigResponse = 17;
        static final int TRANSACTION_setGsmBroadcastActivationResponse = 18;
        static final int TRANSACTION_setGsmBroadcastConfigResponse = 19;
        static final int TRANSACTION_setSmscAddressResponse = 20;
        static final int TRANSACTION_writeSmsToRuimResponse = 21;
        static final int TRANSACTION_writeSmsToSimResponse = 22;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioMessagingResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioMessagingResponse)) {
                return (IRadioMessagingResponse) iInterfaceQueryLocalInterface;
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
                    RadioResponseInfo radioResponseInfo = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    acknowledgeIncomingGsmSmsWithPduResponse(radioResponseInfo);
                    return true;
                case 2:
                    RadioResponseInfo radioResponseInfo2 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    acknowledgeLastIncomingCdmaSmsResponse(radioResponseInfo2);
                    return true;
                case 3:
                    RadioResponseInfo radioResponseInfo3 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    acknowledgeLastIncomingGsmSmsResponse(radioResponseInfo3);
                    return true;
                case 4:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeRequest(i3);
                    return true;
                case 5:
                    RadioResponseInfo radioResponseInfo4 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteSmsOnRuimResponse(radioResponseInfo4);
                    return true;
                case 6:
                    RadioResponseInfo radioResponseInfo5 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteSmsOnSimResponse(radioResponseInfo5);
                    return true;
                case 7:
                    RadioResponseInfo radioResponseInfo6 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr = (CdmaBroadcastSmsConfigInfo[]) parcel.createTypedArray(CdmaBroadcastSmsConfigInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCdmaBroadcastConfigResponse(radioResponseInfo6, cdmaBroadcastSmsConfigInfoArr);
                    return true;
                case 8:
                    RadioResponseInfo radioResponseInfo7 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr = (GsmBroadcastSmsConfigInfo[]) parcel.createTypedArray(GsmBroadcastSmsConfigInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getGsmBroadcastConfigResponse(radioResponseInfo7, gsmBroadcastSmsConfigInfoArr);
                    return true;
                case 9:
                    RadioResponseInfo radioResponseInfo8 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getSmscAddressResponse(radioResponseInfo8, string);
                    return true;
                case 10:
                    RadioResponseInfo radioResponseInfo9 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportSmsMemoryStatusResponse(radioResponseInfo9);
                    return true;
                case 11:
                    RadioResponseInfo radioResponseInfo10 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    SendSmsResult sendSmsResult = (SendSmsResult) parcel.readTypedObject(SendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaSmsExpectMoreResponse(radioResponseInfo10, sendSmsResult);
                    return true;
                case 12:
                    RadioResponseInfo radioResponseInfo11 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    SendSmsResult sendSmsResult2 = (SendSmsResult) parcel.readTypedObject(SendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaSmsResponse(radioResponseInfo11, sendSmsResult2);
                    return true;
                case 13:
                    RadioResponseInfo radioResponseInfo12 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    SendSmsResult sendSmsResult3 = (SendSmsResult) parcel.readTypedObject(SendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendImsSmsResponse(radioResponseInfo12, sendSmsResult3);
                    return true;
                case 14:
                    RadioResponseInfo radioResponseInfo13 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    SendSmsResult sendSmsResult4 = (SendSmsResult) parcel.readTypedObject(SendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSmsExpectMoreResponse(radioResponseInfo13, sendSmsResult4);
                    return true;
                case 15:
                    RadioResponseInfo radioResponseInfo14 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    SendSmsResult sendSmsResult5 = (SendSmsResult) parcel.readTypedObject(SendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSmsResponse(radioResponseInfo14, sendSmsResult5);
                    return true;
                case 16:
                    RadioResponseInfo radioResponseInfo15 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCdmaBroadcastActivationResponse(radioResponseInfo15);
                    return true;
                case 17:
                    RadioResponseInfo radioResponseInfo16 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCdmaBroadcastConfigResponse(radioResponseInfo16);
                    return true;
                case 18:
                    RadioResponseInfo radioResponseInfo17 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setGsmBroadcastActivationResponse(radioResponseInfo17);
                    return true;
                case 19:
                    RadioResponseInfo radioResponseInfo18 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setGsmBroadcastConfigResponse(radioResponseInfo18);
                    return true;
                case 20:
                    RadioResponseInfo radioResponseInfo19 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSmscAddressResponse(radioResponseInfo19);
                    return true;
                case 21:
                    RadioResponseInfo radioResponseInfo20 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    writeSmsToRuimResponse(radioResponseInfo20, i4);
                    return true;
                case 22:
                    RadioResponseInfo radioResponseInfo21 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    writeSmsToSimResponse(radioResponseInfo21, i5);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioMessagingResponse {
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

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void acknowledgeIncomingGsmSmsWithPduResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acknowledgeIncomingGsmSmsWithPduResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void acknowledgeLastIncomingCdmaSmsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acknowledgeLastIncomingCdmaSmsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void acknowledgeLastIncomingGsmSmsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acknowledgeLastIncomingGsmSmsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void acknowledgeRequest(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acknowledgeRequest is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void deleteSmsOnRuimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method deleteSmsOnRuimResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void deleteSmsOnSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method deleteSmsOnSimResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void getCdmaBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedArray(cdmaBroadcastSmsConfigInfoArr, 0);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCdmaBroadcastConfigResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void getGsmBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedArray(gsmBroadcastSmsConfigInfoArr, 0);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getGsmBroadcastConfigResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void getSmscAddressResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSmscAddressResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void reportSmsMemoryStatusResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method reportSmsMemoryStatusResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void sendCdmaSmsExpectMoreResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sendSmsResult, 0);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCdmaSmsExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void sendCdmaSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sendSmsResult, 0);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCdmaSmsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void sendImsSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sendSmsResult, 0);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendImsSmsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void sendSmsExpectMoreResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sendSmsResult, 0);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSmsExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void sendSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sendSmsResult, 0);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSmsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void setCdmaBroadcastActivationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCdmaBroadcastActivationResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void setCdmaBroadcastConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCdmaBroadcastConfigResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void setGsmBroadcastActivationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(18, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setGsmBroadcastActivationResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void setGsmBroadcastConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(19, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setGsmBroadcastConfigResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void setSmscAddressResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(20, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSmscAddressResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void writeSmsToRuimResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(21, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method writeSmsToRuimResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
            public void writeSmsToSimResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(22, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method writeSmsToSimResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
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

            @Override // android.hardware.radio.messaging.IRadioMessagingResponse
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
