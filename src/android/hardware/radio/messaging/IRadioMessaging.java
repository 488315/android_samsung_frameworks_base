package android.hardware.radio.messaging;

import android.hardware.radio.messaging.IRadioMessagingIndication;
import android.hardware.radio.messaging.IRadioMessagingResponse;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioMessaging extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$messaging$IRadioMessaging".replace('$', '.');
    public static final String HASH = "b28416394e6595c08e97c0473855eb05eed1baed";
    public static final int VERSION = 4;

    void acknowledgeIncomingGsmSmsWithPdu(int i, boolean z, String str) throws RemoteException;

    @Deprecated
    void acknowledgeLastIncomingCdmaSms(int i, CdmaSmsAck cdmaSmsAck) throws RemoteException;

    void acknowledgeLastIncomingGsmSms(int i, boolean z, int i2) throws RemoteException;

    @Deprecated
    void deleteSmsOnRuim(int i, int i2) throws RemoteException;

    void deleteSmsOnSim(int i, int i2) throws RemoteException;

    @Deprecated
    void getCdmaBroadcastConfig(int i) throws RemoteException;

    void getGsmBroadcastConfig(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getSmscAddress(int i) throws RemoteException;

    void reportSmsMemoryStatus(int i, boolean z) throws RemoteException;

    void responseAcknowledgement() throws RemoteException;

    @Deprecated
    void sendCdmaSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException;

    @Deprecated
    void sendCdmaSmsExpectMore(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException;

    void sendImsSms(int i, ImsSmsMessage imsSmsMessage) throws RemoteException;

    void sendSms(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException;

    void sendSmsExpectMore(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException;

    @Deprecated
    void setCdmaBroadcastActivation(int i, boolean z) throws RemoteException;

    @Deprecated
    void setCdmaBroadcastConfig(int i, CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr) throws RemoteException;

    void setGsmBroadcastActivation(int i, boolean z) throws RemoteException;

    void setGsmBroadcastConfig(int i, GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr) throws RemoteException;

    void setResponseFunctions(IRadioMessagingResponse iRadioMessagingResponse, IRadioMessagingIndication iRadioMessagingIndication) throws RemoteException;

    void setSmscAddress(int i, String str) throws RemoteException;

    @Deprecated
    void writeSmsToRuim(int i, CdmaSmsWriteArgs cdmaSmsWriteArgs) throws RemoteException;

    void writeSmsToSim(int i, SmsWriteArgs smsWriteArgs) throws RemoteException;

    public static class Default implements IRadioMessaging {
        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void acknowledgeIncomingGsmSmsWithPdu(int i, boolean z, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void acknowledgeLastIncomingCdmaSms(int i, CdmaSmsAck cdmaSmsAck) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void acknowledgeLastIncomingGsmSms(int i, boolean z, int i2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void deleteSmsOnRuim(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void deleteSmsOnSim(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void getCdmaBroadcastConfig(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void getGsmBroadcastConfig(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void getSmscAddress(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void reportSmsMemoryStatus(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void responseAcknowledgement() throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void sendCdmaSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void sendCdmaSmsExpectMore(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void sendImsSms(int i, ImsSmsMessage imsSmsMessage) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void sendSms(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void sendSmsExpectMore(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void setCdmaBroadcastActivation(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void setCdmaBroadcastConfig(int i, CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void setGsmBroadcastActivation(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void setGsmBroadcastConfig(int i, GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void setResponseFunctions(IRadioMessagingResponse iRadioMessagingResponse, IRadioMessagingIndication iRadioMessagingIndication) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void setSmscAddress(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void writeSmsToRuim(int i, CdmaSmsWriteArgs cdmaSmsWriteArgs) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public void writeSmsToSim(int i, SmsWriteArgs smsWriteArgs) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessaging
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioMessaging {
        static final int TRANSACTION_acknowledgeIncomingGsmSmsWithPdu = 1;
        static final int TRANSACTION_acknowledgeLastIncomingCdmaSms = 2;
        static final int TRANSACTION_acknowledgeLastIncomingGsmSms = 3;
        static final int TRANSACTION_deleteSmsOnRuim = 4;
        static final int TRANSACTION_deleteSmsOnSim = 5;
        static final int TRANSACTION_getCdmaBroadcastConfig = 6;
        static final int TRANSACTION_getGsmBroadcastConfig = 7;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSmscAddress = 8;
        static final int TRANSACTION_reportSmsMemoryStatus = 9;
        static final int TRANSACTION_responseAcknowledgement = 10;
        static final int TRANSACTION_sendCdmaSms = 11;
        static final int TRANSACTION_sendCdmaSmsExpectMore = 12;
        static final int TRANSACTION_sendImsSms = 13;
        static final int TRANSACTION_sendSms = 14;
        static final int TRANSACTION_sendSmsExpectMore = 15;
        static final int TRANSACTION_setCdmaBroadcastActivation = 16;
        static final int TRANSACTION_setCdmaBroadcastConfig = 17;
        static final int TRANSACTION_setGsmBroadcastActivation = 18;
        static final int TRANSACTION_setGsmBroadcastConfig = 19;
        static final int TRANSACTION_setResponseFunctions = 20;
        static final int TRANSACTION_setSmscAddress = 21;
        static final int TRANSACTION_writeSmsToRuim = 22;
        static final int TRANSACTION_writeSmsToSim = 23;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioMessaging asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioMessaging)) {
                return (IRadioMessaging) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acknowledgeIncomingGsmSmsWithPdu(i3, z, string);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    CdmaSmsAck cdmaSmsAck = (CdmaSmsAck) parcel.readTypedObject(CdmaSmsAck.CREATOR);
                    parcel.enforceNoDataAvail();
                    acknowledgeLastIncomingCdmaSms(i4, cdmaSmsAck);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeLastIncomingGsmSms(i5, z2, i6);
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteSmsOnRuim(i7, i8);
                    return true;
                case 5:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteSmsOnSim(i9, i10);
                    return true;
                case 6:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCdmaBroadcastConfig(i11);
                    return true;
                case 7:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getGsmBroadcastConfig(i12);
                    return true;
                case 8:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSmscAddress(i13);
                    return true;
                case 9:
                    int i14 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportSmsMemoryStatus(i14, z3);
                    return true;
                case 10:
                    responseAcknowledgement();
                    return true;
                case 11:
                    int i15 = parcel.readInt();
                    CdmaSmsMessage cdmaSmsMessage = (CdmaSmsMessage) parcel.readTypedObject(CdmaSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaSms(i15, cdmaSmsMessage);
                    return true;
                case 12:
                    int i16 = parcel.readInt();
                    CdmaSmsMessage cdmaSmsMessage2 = (CdmaSmsMessage) parcel.readTypedObject(CdmaSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaSmsExpectMore(i16, cdmaSmsMessage2);
                    return true;
                case 13:
                    int i17 = parcel.readInt();
                    ImsSmsMessage imsSmsMessage = (ImsSmsMessage) parcel.readTypedObject(ImsSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendImsSms(i17, imsSmsMessage);
                    return true;
                case 14:
                    int i18 = parcel.readInt();
                    GsmSmsMessage gsmSmsMessage = (GsmSmsMessage) parcel.readTypedObject(GsmSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSms(i18, gsmSmsMessage);
                    return true;
                case 15:
                    int i19 = parcel.readInt();
                    GsmSmsMessage gsmSmsMessage2 = (GsmSmsMessage) parcel.readTypedObject(GsmSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSmsExpectMore(i19, gsmSmsMessage2);
                    return true;
                case 16:
                    int i20 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCdmaBroadcastActivation(i20, z4);
                    return true;
                case 17:
                    int i21 = parcel.readInt();
                    CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr = (CdmaBroadcastSmsConfigInfo[]) parcel.createTypedArray(CdmaBroadcastSmsConfigInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCdmaBroadcastConfig(i21, cdmaBroadcastSmsConfigInfoArr);
                    return true;
                case 18:
                    int i22 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setGsmBroadcastActivation(i22, z5);
                    return true;
                case 19:
                    int i23 = parcel.readInt();
                    GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr = (GsmBroadcastSmsConfigInfo[]) parcel.createTypedArray(GsmBroadcastSmsConfigInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setGsmBroadcastConfig(i23, gsmBroadcastSmsConfigInfoArr);
                    return true;
                case 20:
                    IRadioMessagingResponse iRadioMessagingResponseAsInterface = IRadioMessagingResponse.Stub.asInterface(parcel.readStrongBinder());
                    IRadioMessagingIndication iRadioMessagingIndicationAsInterface = IRadioMessagingIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(iRadioMessagingResponseAsInterface, iRadioMessagingIndicationAsInterface);
                    return true;
                case 21:
                    int i24 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSmscAddress(i24, string2);
                    return true;
                case 22:
                    int i25 = parcel.readInt();
                    CdmaSmsWriteArgs cdmaSmsWriteArgs = (CdmaSmsWriteArgs) parcel.readTypedObject(CdmaSmsWriteArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    writeSmsToRuim(i25, cdmaSmsWriteArgs);
                    return true;
                case 23:
                    int i26 = parcel.readInt();
                    SmsWriteArgs smsWriteArgs = (SmsWriteArgs) parcel.readTypedObject(SmsWriteArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    writeSmsToSim(i26, smsWriteArgs);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioMessaging {
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

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void acknowledgeIncomingGsmSmsWithPdu(int i, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acknowledgeIncomingGsmSmsWithPdu is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void acknowledgeLastIncomingCdmaSms(int i, CdmaSmsAck cdmaSmsAck) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(cdmaSmsAck, 0);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acknowledgeLastIncomingCdmaSms is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void acknowledgeLastIncomingGsmSms(int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acknowledgeLastIncomingGsmSms is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void deleteSmsOnRuim(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method deleteSmsOnRuim is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void deleteSmsOnSim(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method deleteSmsOnSim is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void getCdmaBroadcastConfig(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCdmaBroadcastConfig is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void getGsmBroadcastConfig(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getGsmBroadcastConfig is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void getSmscAddress(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSmscAddress is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void reportSmsMemoryStatus(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method reportSmsMemoryStatus is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void responseAcknowledgement() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void sendCdmaSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(cdmaSmsMessage, 0);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCdmaSms is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void sendCdmaSmsExpectMore(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(cdmaSmsMessage, 0);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCdmaSmsExpectMore is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void sendImsSms(int i, ImsSmsMessage imsSmsMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(imsSmsMessage, 0);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendImsSms is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void sendSms(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(gsmSmsMessage, 0);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSms is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void sendSmsExpectMore(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(gsmSmsMessage, 0);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSmsExpectMore is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void setCdmaBroadcastActivation(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCdmaBroadcastActivation is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void setCdmaBroadcastConfig(int i, CdmaBroadcastSmsConfigInfo[] cdmaBroadcastSmsConfigInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(cdmaBroadcastSmsConfigInfoArr, 0);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCdmaBroadcastConfig is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void setGsmBroadcastActivation(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(18, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setGsmBroadcastActivation is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void setGsmBroadcastConfig(int i, GsmBroadcastSmsConfigInfo[] gsmBroadcastSmsConfigInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(gsmBroadcastSmsConfigInfoArr, 0);
                    if (this.mRemote.transact(19, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setGsmBroadcastConfig is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void setResponseFunctions(IRadioMessagingResponse iRadioMessagingResponse, IRadioMessagingIndication iRadioMessagingIndication) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRadioMessagingResponse);
                    parcelObtain.writeStrongInterface(iRadioMessagingIndication);
                    if (this.mRemote.transact(20, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void setSmscAddress(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(21, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSmscAddress is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void writeSmsToRuim(int i, CdmaSmsWriteArgs cdmaSmsWriteArgs) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(cdmaSmsWriteArgs, 0);
                    if (this.mRemote.transact(22, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method writeSmsToRuim is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
            public void writeSmsToSim(int i, SmsWriteArgs smsWriteArgs) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(smsWriteArgs, 0);
                    if (this.mRemote.transact(23, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method writeSmsToSim is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessaging
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

            @Override // android.hardware.radio.messaging.IRadioMessaging
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
