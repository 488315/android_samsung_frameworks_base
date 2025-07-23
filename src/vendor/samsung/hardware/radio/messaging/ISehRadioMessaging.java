package vendor.samsung.hardware.radio.messaging;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.radio.messaging.ISehRadioMessagingIndication;
import vendor.samsung.hardware.radio.messaging.ISehRadioMessagingResponse;

/* loaded from: classes6.dex */
public interface ISehRadioMessaging extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$messaging$ISehRadioMessaging".replace('$', '.');
    public static final String HASH = "c1a8596db57e3bcc8e4e86f1eb7b2df7839ca140";
    public static final int VERSION = 1;

    void getCellBroadcastConfig(int i) throws RemoteException;

    void getImsRegistrationState(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getStoredMsgCountFromSim(int i) throws RemoteException;

    void readSmsFromSim(int i, int i2) throws RemoteException;

    void sendCdmaSms(int i, SehCdmaSmsMessage sehCdmaSmsMessage) throws RemoteException;

    void sendCdmaSmsExpectMore(int i, SehCdmaSmsMessage sehCdmaSmsMessage) throws RemoteException;

    void sendSMSExpectMore(int i, SehGsmSmsMessage sehGsmSmsMessage) throws RemoteException;

    void sendSms(int i, SehGsmSmsMessage sehGsmSmsMessage) throws RemoteException;

    void setResponseFunctions(ISehRadioMessagingResponse iSehRadioMessagingResponse, ISehRadioMessagingIndication iSehRadioMessagingIndication) throws RemoteException;

    void writeSmsToSim(int i, SehSimMsgArgs sehSimMsgArgs) throws RemoteException;

    public static class Default implements ISehRadioMessaging {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
        public void getCellBroadcastConfig(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
        public void getImsRegistrationState(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
        public void getStoredMsgCountFromSim(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
        public void readSmsFromSim(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
        public void sendCdmaSms(int i, SehCdmaSmsMessage sehCdmaSmsMessage) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
        public void sendCdmaSmsExpectMore(int i, SehCdmaSmsMessage sehCdmaSmsMessage) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
        public void sendSMSExpectMore(int i, SehGsmSmsMessage sehGsmSmsMessage) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
        public void sendSms(int i, SehGsmSmsMessage sehGsmSmsMessage) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
        public void setResponseFunctions(ISehRadioMessagingResponse iSehRadioMessagingResponse, ISehRadioMessagingIndication iSehRadioMessagingIndication) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
        public void writeSmsToSim(int i, SehSimMsgArgs sehSimMsgArgs) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioMessaging {
        static final int TRANSACTION_getCellBroadcastConfig = 1;
        static final int TRANSACTION_getImsRegistrationState = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getStoredMsgCountFromSim = 3;
        static final int TRANSACTION_readSmsFromSim = 4;
        static final int TRANSACTION_sendCdmaSms = 5;
        static final int TRANSACTION_sendCdmaSmsExpectMore = 6;
        static final int TRANSACTION_sendSMSExpectMore = 7;
        static final int TRANSACTION_sendSms = 8;
        static final int TRANSACTION_setResponseFunctions = 9;
        static final int TRANSACTION_writeSmsToSim = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioMessaging asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioMessaging)) {
                return (ISehRadioMessaging) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCellBroadcastConfig(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getImsRegistrationState(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getStoredMsgCountFromSim(readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    readSmsFromSim(readInt4, readInt5);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    SehCdmaSmsMessage sehCdmaSmsMessage = (SehCdmaSmsMessage) parcel.readTypedObject(SehCdmaSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaSms(readInt6, sehCdmaSmsMessage);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    SehCdmaSmsMessage sehCdmaSmsMessage2 = (SehCdmaSmsMessage) parcel.readTypedObject(SehCdmaSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaSmsExpectMore(readInt7, sehCdmaSmsMessage2);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    SehGsmSmsMessage sehGsmSmsMessage = (SehGsmSmsMessage) parcel.readTypedObject(SehGsmSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSMSExpectMore(readInt8, sehGsmSmsMessage);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    SehGsmSmsMessage sehGsmSmsMessage2 = (SehGsmSmsMessage) parcel.readTypedObject(SehGsmSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSms(readInt9, sehGsmSmsMessage2);
                    return true;
                case 9:
                    ISehRadioMessagingResponse asInterface = ISehRadioMessagingResponse.Stub.asInterface(parcel.readStrongBinder());
                    ISehRadioMessagingIndication asInterface2 = ISehRadioMessagingIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    SehSimMsgArgs sehSimMsgArgs = (SehSimMsgArgs) parcel.readTypedObject(SehSimMsgArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    writeSmsToSim(readInt10, sehSimMsgArgs);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioMessaging {
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

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
            public void getCellBroadcastConfig(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCellBroadcastConfig is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
            public void getImsRegistrationState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getImsRegistrationState is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
            public void getStoredMsgCountFromSim(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getStoredMsgCountFromSim is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
            public void readSmsFromSim(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method readSmsFromSim is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
            public void sendCdmaSms(int i, SehCdmaSmsMessage sehCdmaSmsMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehCdmaSmsMessage, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCdmaSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
            public void sendCdmaSmsExpectMore(int i, SehCdmaSmsMessage sehCdmaSmsMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehCdmaSmsMessage, 0);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCdmaSmsExpectMore is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
            public void sendSMSExpectMore(int i, SehGsmSmsMessage sehGsmSmsMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehGsmSmsMessage, 0);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSMSExpectMore is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
            public void sendSms(int i, SehGsmSmsMessage sehGsmSmsMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehGsmSmsMessage, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
            public void setResponseFunctions(ISehRadioMessagingResponse iSehRadioMessagingResponse, ISehRadioMessagingIndication iSehRadioMessagingIndication) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iSehRadioMessagingResponse);
                    obtain.writeStrongInterface(iSehRadioMessagingIndication);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
            public void writeSmsToSim(int i, SehSimMsgArgs sehSimMsgArgs) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehSimMsgArgs, 0);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method writeSmsToSim is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
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

            @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
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
