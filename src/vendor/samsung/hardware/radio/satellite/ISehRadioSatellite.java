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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISehRadioSatellite)) {
                return (ISehRadioSatellite) iInterfaceQueryLocalInterface;
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
                    parcel.enforceNoDataAvail();
                    answer(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dial(i4, string);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hangup(i5);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCallEndReason(i6);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCallState(i7);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startDtmf(i8, string2);
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopDtmf(i9);
                    return true;
                case 8:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getRegistrationState(i10);
                    return true;
                case 9:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSignalStrength(i11);
                    return true;
                case 10:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNetworkQueryMode(i12, i13);
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSignalStrengthReport(i14, z);
                    return true;
                case 12:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setSignalThresholdReport(i15, i16, iArrCreateIntArray);
                    return true;
                case 13:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPower(i17, i18);
                    return true;
                case 14:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setGpsInfo(i19, i20, i21);
                    return true;
                case 15:
                    int i22 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setImsi(i22, string3);
                    return true;
                case 16:
                    int i23 = parcel.readInt();
                    SehSatSimAuthRespData sehSatSimAuthRespData = (SehSatSimAuthRespData) parcel.readTypedObject(SehSatSimAuthRespData.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendIccSimAuthentication(i23, sehSatSimAuthRespData);
                    return true;
                case 17:
                    int i24 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setImei(i24, string4);
                    return true;
                case 18:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSerialNumber(i25);
                    return true;
                case 19:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAbsoluteRfChannelNumber(i26);
                    return true;
                case 20:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getTxPower(i27);
                    return true;
                case 21:
                    int i28 = parcel.readInt();
                    SehSatSmsMessage sehSatSmsMessage = (SehSatSmsMessage) parcel.readTypedObject(SehSatSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSMSExpectMore(i28, sehSatSmsMessage);
                    return true;
                case 22:
                    int i29 = parcel.readInt();
                    SehSatSmsMessage sehSatSmsMessage2 = (SehSatSmsMessage) parcel.readTypedObject(SehSatSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSms(i29, sehSatSmsMessage2);
                    return true;
                case 23:
                    int i30 = parcel.readInt();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSmscAddress(i30, string5);
                    return true;
                case 24:
                    int i31 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendLocationUserPermit(i31, string6);
                    return true;
                case 25:
                    int i32 = parcel.readInt();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendLocationData(i32, string7);
                    return true;
                case 26:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSatelliteId(i33);
                    return true;
                case 27:
                    int i34 = parcel.readInt();
                    int i35 = parcel.readInt();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDsiConfig(i34, i35, i36);
                    return true;
                case 28:
                    int i37 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendRawAtCommand(i37, string8);
                    return true;
                case 29:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startNetworkSearch(i38);
                    return true;
                case 30:
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cleanupNetworkInfo(i39);
                    return true;
                case 31:
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableIotMode(i40);
                    return true;
                case 32:
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableIotMode(i41);
                    return true;
                case 33:
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getIotMode(i42);
                    return true;
                case 34:
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getIotRegistrationState(i43);
                    return true;
                case 35:
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCombinedConfigMode(i44, i45);
                    return true;
                case 36:
                    ISehRadioSatelliteResponse iSehRadioSatelliteResponseAsInterface = ISehRadioSatelliteResponse.Stub.asInterface(parcel.readStrongBinder());
                    ISehRadioSatelliteIndication iSehRadioSatelliteIndicationAsInterface = ISehRadioSatelliteIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(iSehRadioSatelliteResponseAsInterface, iSehRadioSatelliteIndicationAsInterface);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method answer is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void dial(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method dial is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void hangup(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method hangup is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getCallEndReason(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCallEndReason is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getCallState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCallState is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void startDtmf(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startDtmf is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void stopDtmf(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopDtmf is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getRegistrationState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getRegistrationState is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getSignalStrength(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSignalStrength is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setNetworkQueryMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNetworkQueryMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setSignalStrengthReport(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSignalStrengthReport is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setSignalThresholdReport(int i, int i2, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSignalThresholdReport is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setPower(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setPower is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setGpsInfo(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setGpsInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setImsi(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setImsi is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendIccSimAuthentication(int i, SehSatSimAuthRespData sehSatSimAuthRespData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehSatSimAuthRespData, 0);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendIccSimAuthentication is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setImei(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setImei is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getSerialNumber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(18, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSerialNumber is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getAbsoluteRfChannelNumber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(19, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAbsoluteRfChannelNumber is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getTxPower(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(20, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getTxPower is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendSMSExpectMore(int i, SehSatSmsMessage sehSatSmsMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehSatSmsMessage, 0);
                    if (this.mRemote.transact(21, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSMSExpectMore is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendSms(int i, SehSatSmsMessage sehSatSmsMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehSatSmsMessage, 0);
                    if (this.mRemote.transact(22, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSms is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setSmscAddress(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(23, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSmscAddress is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendLocationUserPermit(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(24, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendLocationUserPermit is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendLocationData(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(25, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendLocationData is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getSatelliteId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(26, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSatelliteId is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setDsiConfig(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    if (this.mRemote.transact(27, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDsiConfig is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void sendRawAtCommand(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(28, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendRawAtCommand is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void startNetworkSearch(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(29, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startNetworkSearch is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void cleanupNetworkInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(30, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cleanupNetworkInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void enableIotMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(31, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableIotMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void disableIotMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(32, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method disableIotMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getIotMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(33, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIotMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void getIotRegistrationState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(34, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIotRegistrationState is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setCombinedConfigMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(35, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCombinedConfigMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
            public void setResponseFunctions(ISehRadioSatelliteResponse iSehRadioSatelliteResponse, ISehRadioSatelliteIndication iSehRadioSatelliteIndication) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSehRadioSatelliteResponse);
                    parcelObtain.writeStrongInterface(iSehRadioSatelliteIndication);
                    if (!this.mRemote.transact(36, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatellite
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
