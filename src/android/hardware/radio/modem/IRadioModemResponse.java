package android.hardware.radio.modem;

import android.hardware.radio.RadioResponseInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioModemResponse extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$modem$IRadioModemResponse".replace('$', '.');
    public static final String HASH = "787419262f7c39ea36c0fbe22681bada95d1f97b";
    public static final int VERSION = 4;

    void acknowledgeRequest(int i) throws RemoteException;

    void enableModemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void getBasebandVersionResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    @Deprecated
    void getDeviceIdentityResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4) throws RemoteException;

    void getHardwareConfigResponse(RadioResponseInfo radioResponseInfo, HardwareConfig[] hardwareConfigArr) throws RemoteException;

    void getImeiResponse(RadioResponseInfo radioResponseInfo, ImeiInfo imeiInfo) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getModemActivityInfoResponse(RadioResponseInfo radioResponseInfo, ActivityStatsInfo activityStatsInfo) throws RemoteException;

    void getModemStackStatusResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void getRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException;

    @Deprecated
    void nvReadItemResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void nvResetConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void nvWriteCdmaPrlResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void nvWriteItemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void requestShutdownResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendDeviceStateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException;

    void setRadioPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    public static class Default implements IRadioModemResponse {
        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void acknowledgeRequest(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void enableModemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getBasebandVersionResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getDeviceIdentityResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getHardwareConfigResponse(RadioResponseInfo radioResponseInfo, HardwareConfig[] hardwareConfigArr) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getImeiResponse(RadioResponseInfo radioResponseInfo, ImeiInfo imeiInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getModemActivityInfoResponse(RadioResponseInfo radioResponseInfo, ActivityStatsInfo activityStatsInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getModemStackStatusResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void getRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void nvReadItemResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void nvResetConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void nvWriteCdmaPrlResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void nvWriteItemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void requestShutdownResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void sendDeviceStateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void setRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public void setRadioPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemResponse
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioModemResponse {
        static final int TRANSACTION_acknowledgeRequest = 1;
        static final int TRANSACTION_enableModemResponse = 2;
        static final int TRANSACTION_getBasebandVersionResponse = 3;
        static final int TRANSACTION_getDeviceIdentityResponse = 4;
        static final int TRANSACTION_getHardwareConfigResponse = 5;
        static final int TRANSACTION_getImeiResponse = 17;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getModemActivityInfoResponse = 6;
        static final int TRANSACTION_getModemStackStatusResponse = 7;
        static final int TRANSACTION_getRadioCapabilityResponse = 8;
        static final int TRANSACTION_nvReadItemResponse = 9;
        static final int TRANSACTION_nvResetConfigResponse = 10;
        static final int TRANSACTION_nvWriteCdmaPrlResponse = 11;
        static final int TRANSACTION_nvWriteItemResponse = 12;
        static final int TRANSACTION_requestShutdownResponse = 13;
        static final int TRANSACTION_sendDeviceStateResponse = 14;
        static final int TRANSACTION_setRadioCapabilityResponse = 15;
        static final int TRANSACTION_setRadioPowerResponse = 16;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioModemResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioModemResponse)) {
                return (IRadioModemResponse) iInterfaceQueryLocalInterface;
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
                    acknowledgeRequest(i3);
                    return true;
                case 2:
                    RadioResponseInfo radioResponseInfo = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    enableModemResponse(radioResponseInfo);
                    return true;
                case 3:
                    RadioResponseInfo radioResponseInfo2 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getBasebandVersionResponse(radioResponseInfo2, string);
                    return true;
                case 4:
                    RadioResponseInfo radioResponseInfo3 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getDeviceIdentityResponse(radioResponseInfo3, string2, string3, string4, string5);
                    return true;
                case 5:
                    RadioResponseInfo radioResponseInfo4 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    HardwareConfig[] hardwareConfigArr = (HardwareConfig[]) parcel.createTypedArray(HardwareConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    getHardwareConfigResponse(radioResponseInfo4, hardwareConfigArr);
                    return true;
                case 6:
                    RadioResponseInfo radioResponseInfo5 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    ActivityStatsInfo activityStatsInfo = (ActivityStatsInfo) parcel.readTypedObject(ActivityStatsInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getModemActivityInfoResponse(radioResponseInfo5, activityStatsInfo);
                    return true;
                case 7:
                    RadioResponseInfo radioResponseInfo6 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    getModemStackStatusResponse(radioResponseInfo6, z);
                    return true;
                case 8:
                    RadioResponseInfo radioResponseInfo7 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    RadioCapability radioCapability = (RadioCapability) parcel.readTypedObject(RadioCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    getRadioCapabilityResponse(radioResponseInfo7, radioCapability);
                    return true;
                case 9:
                    RadioResponseInfo radioResponseInfo8 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    nvReadItemResponse(radioResponseInfo8, string6);
                    return true;
                case 10:
                    RadioResponseInfo radioResponseInfo9 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    nvResetConfigResponse(radioResponseInfo9);
                    return true;
                case 11:
                    RadioResponseInfo radioResponseInfo10 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    nvWriteCdmaPrlResponse(radioResponseInfo10);
                    return true;
                case 12:
                    RadioResponseInfo radioResponseInfo11 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    nvWriteItemResponse(radioResponseInfo11);
                    return true;
                case 13:
                    RadioResponseInfo radioResponseInfo12 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestShutdownResponse(radioResponseInfo12);
                    return true;
                case 14:
                    RadioResponseInfo radioResponseInfo13 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDeviceStateResponse(radioResponseInfo13);
                    return true;
                case 15:
                    RadioResponseInfo radioResponseInfo14 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    RadioCapability radioCapability2 = (RadioCapability) parcel.readTypedObject(RadioCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRadioCapabilityResponse(radioResponseInfo14, radioCapability2);
                    return true;
                case 16:
                    RadioResponseInfo radioResponseInfo15 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRadioPowerResponse(radioResponseInfo15);
                    return true;
                case 17:
                    RadioResponseInfo radioResponseInfo16 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    ImeiInfo imeiInfo = (ImeiInfo) parcel.readTypedObject(ImeiInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getImeiResponse(radioResponseInfo16, imeiInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioModemResponse {
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

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void acknowledgeRequest(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acknowledgeRequest is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void enableModemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableModemResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getBasebandVersionResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getBasebandVersionResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getDeviceIdentityResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getDeviceIdentityResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getHardwareConfigResponse(RadioResponseInfo radioResponseInfo, HardwareConfig[] hardwareConfigArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedArray(hardwareConfigArr, 0);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getHardwareConfigResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getModemActivityInfoResponse(RadioResponseInfo radioResponseInfo, ActivityStatsInfo activityStatsInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedObject(activityStatsInfo, 0);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getModemActivityInfoResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getModemStackStatusResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getModemStackStatusResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedObject(radioCapability, 0);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getRadioCapabilityResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void nvReadItemResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvReadItemResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void nvResetConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvResetConfigResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void nvWriteCdmaPrlResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvWriteCdmaPrlResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void nvWriteItemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvWriteItemResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void requestShutdownResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestShutdownResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void sendDeviceStateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendDeviceStateResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void setRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedObject(radioCapability, 0);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setRadioCapabilityResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void setRadioPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setRadioPowerResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getImeiResponse(RadioResponseInfo radioResponseInfo, ImeiInfo imeiInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedObject(imeiInfo, 0);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getImeiResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
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

            @Override // android.hardware.radio.modem.IRadioModemResponse
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
