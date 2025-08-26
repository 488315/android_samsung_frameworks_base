package vendor.samsung.hardware.radio.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehRadioSatelliteIndication extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$satellite$ISehRadioSatelliteIndication".replace('$', '.');
    public static final String HASH = "90863b100bf8b0ec3c45dec007d73ce7f04d8850";
    public static final int VERSION = 1;

    void callEndReasonUpdated(int i, SehSatCallEndReason sehSatCallEndReason) throws RemoteException;

    void callNumberDisplayInfoUpdated(int i, SehSatCallDisplayInfo sehSatCallDisplayInfo) throws RemoteException;

    void callStateChanged(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void iotRegistrationStateChanged(int i, SehSatIotRegState sehSatIotRegState) throws RemoteException;

    void newSms(int i, byte[] bArr) throws RemoteException;

    void newSmsStatusReport(int i, SehSatStatusReport sehSatStatusReport) throws RemoteException;

    void radioStateChanged(int i, int i2) throws RemoteException;

    void registrationStateChanged(int i, SehSatRegStateResult sehSatRegStateResult) throws RemoteException;

    void requestGpsData(int i) throws RemoteException;

    void requestIccSimAuthentication(int i, SehSatSimAuthReqData sehSatSimAuthReqData) throws RemoteException;

    void signalStrenghChanged(int i, SehSatSignalStrength sehSatSignalStrength) throws RemoteException;

    void simAuthenticationFailed(int i) throws RemoteException;

    public static class Default implements ISehRadioSatelliteIndication {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void callEndReasonUpdated(int i, SehSatCallEndReason sehSatCallEndReason) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void callNumberDisplayInfoUpdated(int i, SehSatCallDisplayInfo sehSatCallDisplayInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void callStateChanged(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void iotRegistrationStateChanged(int i, SehSatIotRegState sehSatIotRegState) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void newSms(int i, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void newSmsStatusReport(int i, SehSatStatusReport sehSatStatusReport) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void radioStateChanged(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void registrationStateChanged(int i, SehSatRegStateResult sehSatRegStateResult) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void requestGpsData(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void requestIccSimAuthentication(int i, SehSatSimAuthReqData sehSatSimAuthReqData) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void signalStrenghChanged(int i, SehSatSignalStrength sehSatSignalStrength) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public void simAuthenticationFailed(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioSatelliteIndication {
        static final int TRANSACTION_callEndReasonUpdated = 2;
        static final int TRANSACTION_callNumberDisplayInfoUpdated = 3;
        static final int TRANSACTION_callStateChanged = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_iotRegistrationStateChanged = 12;
        static final int TRANSACTION_newSms = 10;
        static final int TRANSACTION_newSmsStatusReport = 11;
        static final int TRANSACTION_radioStateChanged = 7;
        static final int TRANSACTION_registrationStateChanged = 4;
        static final int TRANSACTION_requestGpsData = 6;
        static final int TRANSACTION_requestIccSimAuthentication = 8;
        static final int TRANSACTION_signalStrenghChanged = 5;
        static final int TRANSACTION_simAuthenticationFailed = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSatelliteIndication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISehRadioSatelliteIndication)) {
                return (ISehRadioSatelliteIndication) iInterfaceQueryLocalInterface;
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
                    callStateChanged(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    SehSatCallEndReason sehSatCallEndReason = (SehSatCallEndReason) parcel.readTypedObject(SehSatCallEndReason.CREATOR);
                    parcel.enforceNoDataAvail();
                    callEndReasonUpdated(i4, sehSatCallEndReason);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    SehSatCallDisplayInfo sehSatCallDisplayInfo = (SehSatCallDisplayInfo) parcel.readTypedObject(SehSatCallDisplayInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callNumberDisplayInfoUpdated(i5, sehSatCallDisplayInfo);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    SehSatRegStateResult sehSatRegStateResult = (SehSatRegStateResult) parcel.readTypedObject(SehSatRegStateResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    registrationStateChanged(i6, sehSatRegStateResult);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    SehSatSignalStrength sehSatSignalStrength = (SehSatSignalStrength) parcel.readTypedObject(SehSatSignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    signalStrenghChanged(i7, sehSatSignalStrength);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestGpsData(i8);
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    radioStateChanged(i9, i10);
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    SehSatSimAuthReqData sehSatSimAuthReqData = (SehSatSimAuthReqData) parcel.readTypedObject(SehSatSimAuthReqData.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestIccSimAuthentication(i11, sehSatSimAuthReqData);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simAuthenticationFailed(i12);
                    return true;
                case 10:
                    int i13 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    newSms(i13, bArrCreateByteArray);
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    SehSatStatusReport sehSatStatusReport = (SehSatStatusReport) parcel.readTypedObject(SehSatStatusReport.CREATOR);
                    parcel.enforceNoDataAvail();
                    newSmsStatusReport(i14, sehSatStatusReport);
                    return true;
                case 12:
                    int i15 = parcel.readInt();
                    SehSatIotRegState sehSatIotRegState = (SehSatIotRegState) parcel.readTypedObject(SehSatIotRegState.CREATOR);
                    parcel.enforceNoDataAvail();
                    iotRegistrationStateChanged(i15, sehSatIotRegState);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioSatelliteIndication {
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void callStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method callStateChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void callEndReasonUpdated(int i, SehSatCallEndReason sehSatCallEndReason) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehSatCallEndReason, 0);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method callEndReasonUpdated is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void callNumberDisplayInfoUpdated(int i, SehSatCallDisplayInfo sehSatCallDisplayInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehSatCallDisplayInfo, 0);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method callNumberDisplayInfoUpdated is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void registrationStateChanged(int i, SehSatRegStateResult sehSatRegStateResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehSatRegStateResult, 0);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method registrationStateChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void signalStrenghChanged(int i, SehSatSignalStrength sehSatSignalStrength) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehSatSignalStrength, 0);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method signalStrenghChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void requestGpsData(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestGpsData is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void radioStateChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method radioStateChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void requestIccSimAuthentication(int i, SehSatSimAuthReqData sehSatSimAuthReqData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehSatSimAuthReqData, 0);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestIccSimAuthentication is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void simAuthenticationFailed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simAuthenticationFailed is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void newSms(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method newSms is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void newSmsStatusReport(int i, SehSatStatusReport sehSatStatusReport) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehSatStatusReport, 0);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method newSmsStatusReport is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
            public void iotRegistrationStateChanged(int i, SehSatIotRegState sehSatIotRegState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehSatIotRegState, 0);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iotRegistrationStateChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteIndication
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
