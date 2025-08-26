package android.hardware.radio.config;

import android.hardware.radio.config.IRadioConfigIndication;
import android.hardware.radio.config.IRadioConfigResponse;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioConfig extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$config$IRadioConfig".replace('$', '.');
    public static final String HASH = "fc7eeb47f5238e538dead4af7575507920c359f7";
    public static final int VERSION = 4;

    void getHalDeviceCapabilities(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getNumOfLiveModems(int i) throws RemoteException;

    void getPhoneCapability(int i) throws RemoteException;

    void getSimSlotsStatus(int i) throws RemoteException;

    void getSimTypeInfo(int i) throws RemoteException;

    void getSimultaneousCallingSupport(int i) throws RemoteException;

    void setNumOfLiveModems(int i, byte b) throws RemoteException;

    void setPreferredDataModem(int i, byte b) throws RemoteException;

    void setResponseFunctions(IRadioConfigResponse iRadioConfigResponse, IRadioConfigIndication iRadioConfigIndication) throws RemoteException;

    void setSimSlotsMapping(int i, SlotPortMapping[] slotPortMappingArr) throws RemoteException;

    void setSimType(int i, int[] iArr) throws RemoteException;

    public static class Default implements IRadioConfig {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void getHalDeviceCapabilities(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void getNumOfLiveModems(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void getPhoneCapability(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void getSimSlotsStatus(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void getSimTypeInfo(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void getSimultaneousCallingSupport(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void setNumOfLiveModems(int i, byte b) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void setPreferredDataModem(int i, byte b) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void setResponseFunctions(IRadioConfigResponse iRadioConfigResponse, IRadioConfigIndication iRadioConfigIndication) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void setSimSlotsMapping(int i, SlotPortMapping[] slotPortMappingArr) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public void setSimType(int i, int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.radio.config.IRadioConfig
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioConfig {
        static final int TRANSACTION_getHalDeviceCapabilities = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNumOfLiveModems = 2;
        static final int TRANSACTION_getPhoneCapability = 3;
        static final int TRANSACTION_getSimSlotsStatus = 4;
        static final int TRANSACTION_getSimTypeInfo = 10;
        static final int TRANSACTION_getSimultaneousCallingSupport = 9;
        static final int TRANSACTION_setNumOfLiveModems = 5;
        static final int TRANSACTION_setPreferredDataModem = 6;
        static final int TRANSACTION_setResponseFunctions = 7;
        static final int TRANSACTION_setSimSlotsMapping = 8;
        static final int TRANSACTION_setSimType = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioConfig asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioConfig)) {
                return (IRadioConfig) iInterfaceQueryLocalInterface;
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
                    getHalDeviceCapabilities(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getNumOfLiveModems(i4);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPhoneCapability(i5);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSimSlotsStatus(i6);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    byte b = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setNumOfLiveModems(i7, b);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    byte b2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setPreferredDataModem(i8, b2);
                    return true;
                case 7:
                    IRadioConfigResponse iRadioConfigResponseAsInterface = IRadioConfigResponse.Stub.asInterface(parcel.readStrongBinder());
                    IRadioConfigIndication iRadioConfigIndicationAsInterface = IRadioConfigIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(iRadioConfigResponseAsInterface, iRadioConfigIndicationAsInterface);
                    return true;
                case 8:
                    int i9 = parcel.readInt();
                    SlotPortMapping[] slotPortMappingArr = (SlotPortMapping[]) parcel.createTypedArray(SlotPortMapping.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSimSlotsMapping(i9, slotPortMappingArr);
                    return true;
                case 9:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSimultaneousCallingSupport(i10);
                    return true;
                case 10:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSimTypeInfo(i11);
                    return true;
                case 11:
                    int i12 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setSimType(i12, iArrCreateIntArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioConfig {
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

            @Override // android.hardware.radio.config.IRadioConfig
            public void getHalDeviceCapabilities(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getHalDeviceCapabilities is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void getNumOfLiveModems(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getNumOfLiveModems is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void getPhoneCapability(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPhoneCapability is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void getSimSlotsStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimSlotsStatus is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void setNumOfLiveModems(int i, byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByte(b);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNumOfLiveModems is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void setPreferredDataModem(int i, byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByte(b);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setPreferredDataModem is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void setResponseFunctions(IRadioConfigResponse iRadioConfigResponse, IRadioConfigIndication iRadioConfigIndication) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRadioConfigResponse);
                    parcelObtain.writeStrongInterface(iRadioConfigIndication);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void setSimSlotsMapping(int i, SlotPortMapping[] slotPortMappingArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(slotPortMappingArr, 0);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimSlotsMapping is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void getSimultaneousCallingSupport(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimultaneousCallingSupport is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void getSimTypeInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimTypeInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
            public void setSimType(int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimType is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfig
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

            @Override // android.hardware.radio.config.IRadioConfig
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
