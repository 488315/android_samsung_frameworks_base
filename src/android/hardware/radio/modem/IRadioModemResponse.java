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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioModemResponse)) {
                return (IRadioModemResponse) queryLocalInterface;
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
                    acknowledgeRequest(readInt);
                    return true;
                case 2:
                    RadioResponseInfo radioResponseInfo = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    enableModemResponse(radioResponseInfo);
                    return true;
                case 3:
                    RadioResponseInfo radioResponseInfo2 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getBasebandVersionResponse(radioResponseInfo2, readString);
                    return true;
                case 4:
                    RadioResponseInfo radioResponseInfo3 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getDeviceIdentityResponse(radioResponseInfo3, readString2, readString3, readString4, readString5);
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    getModemStackStatusResponse(radioResponseInfo6, readBoolean);
                    return true;
                case 8:
                    RadioResponseInfo radioResponseInfo7 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    RadioCapability radioCapability = (RadioCapability) parcel.readTypedObject(RadioCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    getRadioCapabilityResponse(radioResponseInfo7, radioCapability);
                    return true;
                case 9:
                    RadioResponseInfo radioResponseInfo8 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    nvReadItemResponse(radioResponseInfo8, readString6);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acknowledgeRequest is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void enableModemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableModemResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getBasebandVersionResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getBasebandVersionResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getDeviceIdentityResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getDeviceIdentityResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getHardwareConfigResponse(RadioResponseInfo radioResponseInfo, HardwareConfig[] hardwareConfigArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(hardwareConfigArr, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getHardwareConfigResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getModemActivityInfoResponse(RadioResponseInfo radioResponseInfo, ActivityStatsInfo activityStatsInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(activityStatsInfo, 0);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getModemActivityInfoResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getModemStackStatusResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getModemStackStatusResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(radioCapability, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getRadioCapabilityResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void nvReadItemResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvReadItemResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void nvResetConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvResetConfigResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void nvWriteCdmaPrlResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvWriteCdmaPrlResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void nvWriteItemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvWriteItemResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void requestShutdownResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestShutdownResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void sendDeviceStateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendDeviceStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void setRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(radioCapability, 0);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setRadioCapabilityResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void setRadioPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(16, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setRadioPowerResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
            public void getImeiResponse(RadioResponseInfo radioResponseInfo, ImeiInfo imeiInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(imeiInfo, 0);
                    if (this.mRemote.transact(17, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getImeiResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemResponse
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

            @Override // android.hardware.radio.modem.IRadioModemResponse
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
