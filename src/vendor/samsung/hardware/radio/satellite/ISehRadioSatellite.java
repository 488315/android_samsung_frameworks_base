package vendor.samsung.hardware.radio.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication;
import vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse;

/* loaded from: classes6.dex */
public interface ISehRadioSatellite extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$satellite$ISehRadioSatellite".replace('$', '.');
    public static final String HASH = "90863b100bf8b0ec3c45dec007d73ce7f04d8850";
    public static final int VERSION = 1;

    void answer(int i) throws RemoteException;

    void cleanupNetworkInfo(int i) throws RemoteException;

    void dial(int i, String str) throws RemoteException;

    void disableIotMode(int i) throws RemoteException;

    void enableIotMode(int i) throws RemoteException;

    void getAbsoluteRfChannelNumber(int i) throws RemoteException;

    void getCallEndReason(int i) throws RemoteException;

    void getCallState(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getIotMode(int i) throws RemoteException;

    void getIotRegistrationState(int i) throws RemoteException;

    void getRegistrationState(int i) throws RemoteException;

    void getSatelliteId(int i) throws RemoteException;

    void getSerialNumber(int i) throws RemoteException;

    void getSignalStrength(int i) throws RemoteException;

    void getTxPower(int i) throws RemoteException;

    void hangup(int i) throws RemoteException;

    void sendIccSimAuthentication(int i, SehSatSimAuthRespData sehSatSimAuthRespData) throws RemoteException;

    void sendLocationData(int i, String str) throws RemoteException;

    void sendLocationUserPermit(int i, String str) throws RemoteException;

    void sendRawAtCommand(int i, String str) throws RemoteException;

    void sendSMSExpectMore(int i, SehSatSmsMessage sehSatSmsMessage) throws RemoteException;

    void sendSms(int i, SehSatSmsMessage sehSatSmsMessage) throws RemoteException;

    void setCombinedConfigMode(int i, int i2) throws RemoteException;

    void setDsiConfig(int i, int i2, int i3) throws RemoteException;

    void setGpsInfo(int i, int i2, int i3) throws RemoteException;

    void setImei(int i, String str) throws RemoteException;

    void setImsi(int i, String str) throws RemoteException;

    void setNetworkQueryMode(int i, int i2) throws RemoteException;

    void setPower(int i, int i2) throws RemoteException;

    void setResponseFunctions(ISehRadioSatelliteResponse iSehRadioSatelliteResponse, ISehRadioSatelliteIndication iSehRadioSatelliteIndication) throws RemoteException;

    void setSignalStrengthReport(int i, boolean z) throws RemoteException;

    void setSignalThresholdReport(int i, int i2, int[] iArr) throws RemoteException;

    void setSmscAddress(int i, String str) throws RemoteException;

    void startDtmf(int i, String str) throws RemoteException;

    void startNetworkSearch(int i) throws RemoteException;

    void stopDtmf(int i) throws RemoteException;

    public static class Default implements ISehRadioSatellite {
        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void answer(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void cleanupNetworkInfo(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void dial(int i, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void disableIotMode(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void enableIotMode(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getAbsoluteRfChannelNumber(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getCallEndReason(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getCallState(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getIotMode(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getIotRegistrationState(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getRegistrationState(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getSatelliteId(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getSerialNumber(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getSignalStrength(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void getTxPower(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void hangup(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void sendIccSimAuthentication(int i, SehSatSimAuthRespData sehSatSimAuthRespData) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void sendLocationData(int i, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void sendLocationUserPermit(int i, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void sendRawAtCommand(int i, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void sendSMSExpectMore(int i, SehSatSmsMessage sehSatSmsMessage) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void sendSms(int i, SehSatSmsMessage sehSatSmsMessage) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setCombinedConfigMode(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setDsiConfig(int i, int i2, int i3) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setGpsInfo(int i, int i2, int i3) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setImei(int i, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setImsi(int i, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setNetworkQueryMode(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setPower(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setResponseFunctions(ISehRadioSatelliteResponse iSehRadioSatelliteResponse, ISehRadioSatelliteIndication iSehRadioSatelliteIndication) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setSignalStrengthReport(int i, boolean z) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setSignalThresholdReport(int i, int i2, int[] iArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void setSmscAddress(int i, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void startDtmf(int i, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void startNetworkSearch(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public void stopDtmf(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioSatellite {
        static final int TRANSACTION_answer = 1;
        static final int TRANSACTION_cleanupNetworkInfo = 30;
        static final int TRANSACTION_dial = 2;
        static final int TRANSACTION_disableIotMode = 32;
        static final int TRANSACTION_enableIotMode = 31;
        static final int TRANSACTION_getAbsoluteRfChannelNumber = 19;
        static final int TRANSACTION_getCallEndReason = 4;
        static final int TRANSACTION_getCallState = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getIotMode = 33;
        static final int TRANSACTION_getIotRegistrationState = 34;
        static final int TRANSACTION_getRegistrationState = 8;
        static final int TRANSACTION_getSatelliteId = 26;
        static final int TRANSACTION_getSerialNumber = 18;
        static final int TRANSACTION_getSignalStrength = 9;
        static final int TRANSACTION_getTxPower = 20;
        static final int TRANSACTION_hangup = 3;
        static final int TRANSACTION_sendIccSimAuthentication = 16;
        static final int TRANSACTION_sendLocationData = 25;
        static final int TRANSACTION_sendLocationUserPermit = 24;
        static final int TRANSACTION_sendRawAtCommand = 28;
        static final int TRANSACTION_sendSMSExpectMore = 21;
        static final int TRANSACTION_sendSms = 22;
        static final int TRANSACTION_setCombinedConfigMode = 35;
        static final int TRANSACTION_setDsiConfig = 27;
        static final int TRANSACTION_setGpsInfo = 14;
        static final int TRANSACTION_setImei = 17;
        static final int TRANSACTION_setImsi = 15;
        static final int TRANSACTION_setNetworkQueryMode = 10;
        static final int TRANSACTION_setPower = 13;
        static final int TRANSACTION_setResponseFunctions = 36;
        static final int TRANSACTION_setSignalStrengthReport = 11;
        static final int TRANSACTION_setSignalThresholdReport = 12;
        static final int TRANSACTION_setSmscAddress = 23;
        static final int TRANSACTION_startDtmf = 6;
        static final int TRANSACTION_startNetworkSearch = 29;
        static final int TRANSACTION_stopDtmf = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSatellite asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioSatellite)) {
                return (ISehRadioSatellite) queryLocalInterface;
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
                    answer(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dial(readInt2, readString);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hangup(readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCallEndReason(readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCallState(readInt5);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startDtmf(readInt6, readString2);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopDtmf(readInt7);
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getRegistrationState(readInt8);
                    return true;
                case 9:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSignalStrength(readInt9);
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNetworkQueryMode(readInt10, readInt11);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSignalStrengthReport(readInt12, readBoolean);
                    return true;
                case 12:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setSignalThresholdReport(readInt13, readInt14, createIntArray);
                    return true;
                case 13:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPower(readInt15, readInt16);
                    return true;
                case 14:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setGpsInfo(readInt17, readInt18, readInt19);
                    return true;
                case 15:
                    int readInt20 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setImsi(readInt20, readString3);
                    return true;
                case 16:
                    int readInt21 = parcel.readInt();
                    SehSatSimAuthRespData sehSatSimAuthRespData = (SehSatSimAuthRespData) parcel.readTypedObject(SehSatSimAuthRespData.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendIccSimAuthentication(readInt21, sehSatSimAuthRespData);
                    return true;
                case 17:
                    int readInt22 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setImei(readInt22, readString4);
                    return true;
                case 18:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSerialNumber(readInt23);
                    return true;
                case 19:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAbsoluteRfChannelNumber(readInt24);
                    return true;
                case 20:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getTxPower(readInt25);
                    return true;
                case 21:
                    int readInt26 = parcel.readInt();
                    SehSatSmsMessage sehSatSmsMessage = (SehSatSmsMessage) parcel.readTypedObject(SehSatSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSMSExpectMore(readInt26, sehSatSmsMessage);
                    return true;
                case 22:
                    int readInt27 = parcel.readInt();
                    SehSatSmsMessage sehSatSmsMessage2 = (SehSatSmsMessage) parcel.readTypedObject(SehSatSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSms(readInt27, sehSatSmsMessage2);
                    return true;
                case 23:
                    int readInt28 = parcel.readInt();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSmscAddress(readInt28, readString5);
                    return true;
                case 24:
                    int readInt29 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendLocationUserPermit(readInt29, readString6);
                    return true;
                case 25:
                    int readInt30 = parcel.readInt();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendLocationData(readInt30, readString7);
                    return true;
                case 26:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSatelliteId(readInt31);
                    return true;
                case 27:
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDsiConfig(readInt32, readInt33, readInt34);
                    return true;
                case 28:
                    int readInt35 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendRawAtCommand(readInt35, readString8);
                    return true;
                case 29:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startNetworkSearch(readInt36);
                    return true;
                case 30:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cleanupNetworkInfo(readInt37);
                    return true;
                case 31:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableIotMode(readInt38);
                    return true;
                case 32:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableIotMode(readInt39);
                    return true;
                case 33:
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getIotMode(readInt40);
                    return true;
                case 34:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getIotRegistrationState(readInt41);
                    return true;
                case 35:
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCombinedConfigMode(readInt42, readInt43);
                    return true;
                case 36:
                    ISehRadioSatelliteResponse asInterface = ISehRadioSatelliteResponse.Stub.asInterface(parcel.readStrongBinder());
                    ISehRadioSatelliteIndication asInterface2 = ISehRadioSatelliteIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioSatellite {
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void answer(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method answer is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void dial(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method dial is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void hangup(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method hangup is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getCallEndReason(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCallEndReason is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getCallState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCallState is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void startDtmf(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startDtmf is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void stopDtmf(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopDtmf is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getRegistrationState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getRegistrationState is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getSignalStrength(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSignalStrength is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setNetworkQueryMode(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNetworkQueryMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setSignalStrengthReport(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSignalStrengthReport is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setSignalThresholdReport(int i, int i2, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSignalThresholdReport is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setPower(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setPower is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setGpsInfo(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setGpsInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setImsi(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setImsi is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendIccSimAuthentication(int i, SehSatSimAuthRespData sehSatSimAuthRespData) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehSatSimAuthRespData, 0);
                    if (this.mRemote.transact(16, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendIccSimAuthentication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setImei(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(17, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setImei is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getSerialNumber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(18, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSerialNumber is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getAbsoluteRfChannelNumber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(19, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAbsoluteRfChannelNumber is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getTxPower(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(20, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getTxPower is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendSMSExpectMore(int i, SehSatSmsMessage sehSatSmsMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehSatSmsMessage, 0);
                    if (this.mRemote.transact(21, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSMSExpectMore is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendSms(int i, SehSatSmsMessage sehSatSmsMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehSatSmsMessage, 0);
                    if (this.mRemote.transact(22, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setSmscAddress(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(23, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSmscAddress is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendLocationUserPermit(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(24, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendLocationUserPermit is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendLocationData(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(25, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendLocationData is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getSatelliteId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(26, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSatelliteId is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setDsiConfig(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (this.mRemote.transact(27, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDsiConfig is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendRawAtCommand(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(28, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendRawAtCommand is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void startNetworkSearch(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(29, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startNetworkSearch is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void cleanupNetworkInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(30, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cleanupNetworkInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void enableIotMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(31, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableIotMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void disableIotMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(32, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method disableIotMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getIotMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(33, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIotMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getIotRegistrationState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(34, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIotRegistrationState is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setCombinedConfigMode(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(35, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCombinedConfigMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setResponseFunctions(ISehRadioSatelliteResponse iSehRadioSatelliteResponse, ISehRadioSatelliteIndication iSehRadioSatelliteIndication) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iSehRadioSatelliteResponse);
                    obtain.writeStrongInterface(iSehRadioSatelliteIndication);
                    if (!this.mRemote.transact(36, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
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
