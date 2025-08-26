package android.hardware.radio.voice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioVoiceIndication extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$voice$IRadioVoiceIndication".replace('$', '.');
    public static final String HASH = "576f05d082e9269bcf773b0c9b9112d507ab4b9a";
    public static final int VERSION = 4;

    void callRing(int i, boolean z, CdmaSignalInfoRecord cdmaSignalInfoRecord) throws RemoteException;

    void callStateChanged(int i) throws RemoteException;

    @Deprecated
    void cdmaCallWaiting(int i, CdmaCallWaiting cdmaCallWaiting) throws RemoteException;

    @Deprecated
    void cdmaInfoRec(int i, CdmaInformationRecord[] cdmaInformationRecordArr) throws RemoteException;

    @Deprecated
    void cdmaOtaProvisionStatus(int i, int i2) throws RemoteException;

    void currentEmergencyNumberList(int i, EmergencyNumber[] emergencyNumberArr) throws RemoteException;

    void enterEmergencyCallbackMode(int i) throws RemoteException;

    void exitEmergencyCallbackMode(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void indicateRingbackTone(int i, boolean z) throws RemoteException;

    void onSupplementaryServiceIndication(int i, StkCcUnsolSsResult stkCcUnsolSsResult) throws RemoteException;

    void onUssd(int i, int i2, String str) throws RemoteException;

    void resendIncallMute(int i) throws RemoteException;

    void srvccStateNotify(int i, int i2) throws RemoteException;

    void stkCallControlAlphaNotify(int i, String str) throws RemoteException;

    void stkCallSetup(int i, long j) throws RemoteException;

    public static class Default implements IRadioVoiceIndication {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void callRing(int i, boolean z, CdmaSignalInfoRecord cdmaSignalInfoRecord) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void callStateChanged(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void cdmaCallWaiting(int i, CdmaCallWaiting cdmaCallWaiting) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void cdmaInfoRec(int i, CdmaInformationRecord[] cdmaInformationRecordArr) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void cdmaOtaProvisionStatus(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void currentEmergencyNumberList(int i, EmergencyNumber[] emergencyNumberArr) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void enterEmergencyCallbackMode(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void exitEmergencyCallbackMode(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void indicateRingbackTone(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void onSupplementaryServiceIndication(int i, StkCcUnsolSsResult stkCcUnsolSsResult) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void onUssd(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void resendIncallMute(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void srvccStateNotify(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void stkCallControlAlphaNotify(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public void stkCallSetup(int i, long j) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceIndication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioVoiceIndication {
        static final int TRANSACTION_callRing = 1;
        static final int TRANSACTION_callStateChanged = 2;
        static final int TRANSACTION_cdmaCallWaiting = 3;
        static final int TRANSACTION_cdmaInfoRec = 4;
        static final int TRANSACTION_cdmaOtaProvisionStatus = 5;
        static final int TRANSACTION_currentEmergencyNumberList = 6;
        static final int TRANSACTION_enterEmergencyCallbackMode = 7;
        static final int TRANSACTION_exitEmergencyCallbackMode = 8;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_indicateRingbackTone = 9;
        static final int TRANSACTION_onSupplementaryServiceIndication = 10;
        static final int TRANSACTION_onUssd = 11;
        static final int TRANSACTION_resendIncallMute = 12;
        static final int TRANSACTION_srvccStateNotify = 13;
        static final int TRANSACTION_stkCallControlAlphaNotify = 14;
        static final int TRANSACTION_stkCallSetup = 15;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioVoiceIndication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioVoiceIndication)) {
                return (IRadioVoiceIndication) iInterfaceQueryLocalInterface;
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
                    CdmaSignalInfoRecord cdmaSignalInfoRecord = (CdmaSignalInfoRecord) parcel.readTypedObject(CdmaSignalInfoRecord.CREATOR);
                    parcel.enforceNoDataAvail();
                    callRing(i3, z, cdmaSignalInfoRecord);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callStateChanged(i4);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    CdmaCallWaiting cdmaCallWaiting = (CdmaCallWaiting) parcel.readTypedObject(CdmaCallWaiting.CREATOR);
                    parcel.enforceNoDataAvail();
                    cdmaCallWaiting(i5, cdmaCallWaiting);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    CdmaInformationRecord[] cdmaInformationRecordArr = (CdmaInformationRecord[]) parcel.createTypedArray(CdmaInformationRecord.CREATOR);
                    parcel.enforceNoDataAvail();
                    cdmaInfoRec(i6, cdmaInformationRecordArr);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cdmaOtaProvisionStatus(i7, i8);
                    return true;
                case 6:
                    int i9 = parcel.readInt();
                    EmergencyNumber[] emergencyNumberArr = (EmergencyNumber[]) parcel.createTypedArray(EmergencyNumber.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentEmergencyNumberList(i9, emergencyNumberArr);
                    return true;
                case 7:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enterEmergencyCallbackMode(i10);
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    exitEmergencyCallbackMode(i11);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    indicateRingbackTone(i12, z2);
                    return true;
                case 10:
                    int i13 = parcel.readInt();
                    StkCcUnsolSsResult stkCcUnsolSsResult = (StkCcUnsolSsResult) parcel.readTypedObject(StkCcUnsolSsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSupplementaryServiceIndication(i13, stkCcUnsolSsResult);
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onUssd(i14, i15, string);
                    return true;
                case 12:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resendIncallMute(i16);
                    return true;
                case 13:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    srvccStateNotify(i17, i18);
                    return true;
                case 14:
                    int i19 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stkCallControlAlphaNotify(i19, string2);
                    return true;
                case 15:
                    int i20 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    stkCallSetup(i20, j);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioVoiceIndication {
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

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void callRing(int i, boolean z, CdmaSignalInfoRecord cdmaSignalInfoRecord) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(cdmaSignalInfoRecord, 0);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method callRing is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void callStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method callStateChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void cdmaCallWaiting(int i, CdmaCallWaiting cdmaCallWaiting) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(cdmaCallWaiting, 0);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cdmaCallWaiting is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void cdmaInfoRec(int i, CdmaInformationRecord[] cdmaInformationRecordArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(cdmaInformationRecordArr, 0);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cdmaInfoRec is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void cdmaOtaProvisionStatus(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cdmaOtaProvisionStatus is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void currentEmergencyNumberList(int i, EmergencyNumber[] emergencyNumberArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(emergencyNumberArr, 0);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method currentEmergencyNumberList is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void enterEmergencyCallbackMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enterEmergencyCallbackMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void exitEmergencyCallbackMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method exitEmergencyCallbackMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void indicateRingbackTone(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method indicateRingbackTone is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void onSupplementaryServiceIndication(int i, StkCcUnsolSsResult stkCcUnsolSsResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(stkCcUnsolSsResult, 0);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onSupplementaryServiceIndication is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void onUssd(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onUssd is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void resendIncallMute(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method resendIncallMute is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void srvccStateNotify(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method srvccStateNotify is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void stkCallControlAlphaNotify(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stkCallControlAlphaNotify is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
            public void stkCallSetup(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stkCallSetup is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
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

            @Override // android.hardware.radio.voice.IRadioVoiceIndication
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
