package android.hardware.radio.config;

import android.hardware.radio.RadioResponseInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioConfigResponse extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$config$IRadioConfigResponse".replace('$', '.');
    public static final String HASH = "fc7eeb47f5238e538dead4af7575507920c359f7";
    public static final int VERSION = 4;

    void getHalDeviceCapabilitiesResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getNumOfLiveModemsResponse(RadioResponseInfo radioResponseInfo, byte b) throws RemoteException;

    void getPhoneCapabilityResponse(RadioResponseInfo radioResponseInfo, PhoneCapability phoneCapability) throws RemoteException;

    void getSimSlotsStatusResponse(RadioResponseInfo radioResponseInfo, SimSlotStatus[] simSlotStatusArr) throws RemoteException;

    void getSimTypeInfoResponse(RadioResponseInfo radioResponseInfo, SimTypeInfo[] simTypeInfoArr) throws RemoteException;

    void getSimultaneousCallingSupportResponse(RadioResponseInfo radioResponseInfo, int[] iArr) throws RemoteException;

    void setNumOfLiveModemsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setPreferredDataModemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSimSlotsMappingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSimTypeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    public static class Default implements IRadioConfigResponse {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getHalDeviceCapabilitiesResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getNumOfLiveModemsResponse(RadioResponseInfo radioResponseInfo, byte b) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getPhoneCapabilityResponse(RadioResponseInfo radioResponseInfo, PhoneCapability phoneCapability) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getSimSlotsStatusResponse(RadioResponseInfo radioResponseInfo, SimSlotStatus[] simSlotStatusArr) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getSimTypeInfoResponse(RadioResponseInfo radioResponseInfo, SimTypeInfo[] simTypeInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getSimultaneousCallingSupportResponse(RadioResponseInfo radioResponseInfo, int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void setNumOfLiveModemsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void setPreferredDataModemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void setSimSlotsMappingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void setSimTypeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioConfigResponse {
        static final int TRANSACTION_getHalDeviceCapabilitiesResponse = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNumOfLiveModemsResponse = 2;
        static final int TRANSACTION_getPhoneCapabilityResponse = 3;
        static final int TRANSACTION_getSimSlotsStatusResponse = 4;
        static final int TRANSACTION_getSimTypeInfoResponse = 9;
        static final int TRANSACTION_getSimultaneousCallingSupportResponse = 8;
        static final int TRANSACTION_setNumOfLiveModemsResponse = 5;
        static final int TRANSACTION_setPreferredDataModemResponse = 6;
        static final int TRANSACTION_setSimSlotsMappingResponse = 7;
        static final int TRANSACTION_setSimTypeResponse = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioConfigResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioConfigResponse)) {
                return (IRadioConfigResponse) iInterfaceQueryLocalInterface;
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
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    getHalDeviceCapabilitiesResponse(radioResponseInfo, z);
                    return true;
                case 2:
                    RadioResponseInfo radioResponseInfo2 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    byte b = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    getNumOfLiveModemsResponse(radioResponseInfo2, b);
                    return true;
                case 3:
                    RadioResponseInfo radioResponseInfo3 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    PhoneCapability phoneCapability = (PhoneCapability) parcel.readTypedObject(PhoneCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPhoneCapabilityResponse(radioResponseInfo3, phoneCapability);
                    return true;
                case 4:
                    RadioResponseInfo radioResponseInfo4 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    SimSlotStatus[] simSlotStatusArr = (SimSlotStatus[]) parcel.createTypedArray(SimSlotStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSimSlotsStatusResponse(radioResponseInfo4, simSlotStatusArr);
                    return true;
                case 5:
                    RadioResponseInfo radioResponseInfo5 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNumOfLiveModemsResponse(radioResponseInfo5);
                    return true;
                case 6:
                    RadioResponseInfo radioResponseInfo6 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferredDataModemResponse(radioResponseInfo6);
                    return true;
                case 7:
                    RadioResponseInfo radioResponseInfo7 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSimSlotsMappingResponse(radioResponseInfo7);
                    return true;
                case 8:
                    RadioResponseInfo radioResponseInfo8 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    getSimultaneousCallingSupportResponse(radioResponseInfo8, iArrCreateIntArray);
                    return true;
                case 9:
                    RadioResponseInfo radioResponseInfo9 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    SimTypeInfo[] simTypeInfoArr = (SimTypeInfo[]) parcel.createTypedArray(SimTypeInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSimTypeInfoResponse(radioResponseInfo9, simTypeInfoArr);
                    return true;
                case 10:
                    RadioResponseInfo radioResponseInfo10 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSimTypeResponse(radioResponseInfo10);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioConfigResponse {
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

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getHalDeviceCapabilitiesResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getHalDeviceCapabilitiesResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getNumOfLiveModemsResponse(RadioResponseInfo radioResponseInfo, byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeByte(b);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getNumOfLiveModemsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getPhoneCapabilityResponse(RadioResponseInfo radioResponseInfo, PhoneCapability phoneCapability) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedObject(phoneCapability, 0);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPhoneCapabilityResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getSimSlotsStatusResponse(RadioResponseInfo radioResponseInfo, SimSlotStatus[] simSlotStatusArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedArray(simSlotStatusArr, 0);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimSlotsStatusResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void setNumOfLiveModemsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNumOfLiveModemsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void setPreferredDataModemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setPreferredDataModemResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void setSimSlotsMappingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimSlotsMappingResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getSimultaneousCallingSupportResponse(RadioResponseInfo radioResponseInfo, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeIntArray(iArr);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimultaneousCallingSupportResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getSimTypeInfoResponse(RadioResponseInfo radioResponseInfo, SimTypeInfo[] simTypeInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedArray(simTypeInfoArr, 0);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimTypeInfoResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void setSimTypeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimTypeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
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

            @Override // android.hardware.radio.config.IRadioConfigResponse
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
