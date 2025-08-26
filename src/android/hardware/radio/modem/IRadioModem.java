package android.hardware.radio.modem;

import android.hardware.radio.modem.IRadioModemIndication;
import android.hardware.radio.modem.IRadioModemResponse;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioModem extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$modem$IRadioModem".replace('$', '.');
    public static final String HASH = "787419262f7c39ea36c0fbe22681bada95d1f97b";
    public static final int VERSION = 4;

    void enableModem(int i, boolean z) throws RemoteException;

    void getBasebandVersion(int i) throws RemoteException;

    @Deprecated
    void getDeviceIdentity(int i) throws RemoteException;

    void getHardwareConfig(int i) throws RemoteException;

    void getImei(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getModemActivityInfo(int i) throws RemoteException;

    void getModemStackStatus(int i) throws RemoteException;

    void getRadioCapability(int i) throws RemoteException;

    @Deprecated
    void nvReadItem(int i, int i2) throws RemoteException;

    void nvResetConfig(int i, int i2) throws RemoteException;

    @Deprecated
    void nvWriteCdmaPrl(int i, byte[] bArr) throws RemoteException;

    @Deprecated
    void nvWriteItem(int i, NvWriteItem nvWriteItem) throws RemoteException;

    void requestShutdown(int i) throws RemoteException;

    void responseAcknowledgement() throws RemoteException;

    void sendDeviceState(int i, int i2, boolean z) throws RemoteException;

    void setRadioCapability(int i, RadioCapability radioCapability) throws RemoteException;

    void setRadioPower(int i, boolean z, boolean z2, boolean z3) throws RemoteException;

    void setResponseFunctions(IRadioModemResponse iRadioModemResponse, IRadioModemIndication iRadioModemIndication) throws RemoteException;

    public static class Default implements IRadioModem {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void enableModem(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void getBasebandVersion(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void getDeviceIdentity(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void getHardwareConfig(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void getImei(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void getModemActivityInfo(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void getModemStackStatus(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void getRadioCapability(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void nvReadItem(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void nvResetConfig(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void nvWriteCdmaPrl(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void nvWriteItem(int i, NvWriteItem nvWriteItem) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void requestShutdown(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void responseAcknowledgement() throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void sendDeviceState(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void setRadioCapability(int i, RadioCapability radioCapability) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void setRadioPower(int i, boolean z, boolean z2, boolean z3) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public void setResponseFunctions(IRadioModemResponse iRadioModemResponse, IRadioModemIndication iRadioModemIndication) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModem
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioModem {
        static final int TRANSACTION_enableModem = 1;
        static final int TRANSACTION_getBasebandVersion = 2;
        static final int TRANSACTION_getDeviceIdentity = 3;
        static final int TRANSACTION_getHardwareConfig = 4;
        static final int TRANSACTION_getImei = 18;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getModemActivityInfo = 5;
        static final int TRANSACTION_getModemStackStatus = 6;
        static final int TRANSACTION_getRadioCapability = 7;
        static final int TRANSACTION_nvReadItem = 8;
        static final int TRANSACTION_nvResetConfig = 9;
        static final int TRANSACTION_nvWriteCdmaPrl = 10;
        static final int TRANSACTION_nvWriteItem = 11;
        static final int TRANSACTION_requestShutdown = 12;
        static final int TRANSACTION_responseAcknowledgement = 13;
        static final int TRANSACTION_sendDeviceState = 14;
        static final int TRANSACTION_setRadioCapability = 15;
        static final int TRANSACTION_setRadioPower = 16;
        static final int TRANSACTION_setResponseFunctions = 17;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioModem asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioModem)) {
                return (IRadioModem) iInterfaceQueryLocalInterface;
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
                    parcel.enforceNoDataAvail();
                    enableModem(i3, z);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getBasebandVersion(i4);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getDeviceIdentity(i5);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getHardwareConfig(i6);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getModemActivityInfo(i7);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getModemStackStatus(i8);
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getRadioCapability(i9);
                    return true;
                case 8:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    nvReadItem(i10, i11);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    nvResetConfig(i12, i13);
                    return true;
                case 10:
                    int i14 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    nvWriteCdmaPrl(i14, bArrCreateByteArray);
                    return true;
                case 11:
                    int i15 = parcel.readInt();
                    NvWriteItem nvWriteItem = (NvWriteItem) parcel.readTypedObject(NvWriteItem.CREATOR);
                    parcel.enforceNoDataAvail();
                    nvWriteItem(i15, nvWriteItem);
                    return true;
                case 12:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestShutdown(i16);
                    return true;
                case 13:
                    responseAcknowledgement();
                    return true;
                case 14:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendDeviceState(i17, i18, z2);
                    return true;
                case 15:
                    int i19 = parcel.readInt();
                    RadioCapability radioCapability = (RadioCapability) parcel.readTypedObject(RadioCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRadioCapability(i19, radioCapability);
                    return true;
                case 16:
                    int i20 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRadioPower(i20, z3, z4, z5);
                    return true;
                case 17:
                    IRadioModemResponse iRadioModemResponseAsInterface = IRadioModemResponse.Stub.asInterface(parcel.readStrongBinder());
                    IRadioModemIndication iRadioModemIndicationAsInterface = IRadioModemIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(iRadioModemResponseAsInterface, iRadioModemIndicationAsInterface);
                    return true;
                case 18:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getImei(i21);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioModem {
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

            @Override // android.hardware.radio.modem.IRadioModem
            public void enableModem(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableModem is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getBasebandVersion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getBasebandVersion is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getDeviceIdentity(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getDeviceIdentity is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getHardwareConfig(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getHardwareConfig is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getModemActivityInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getModemActivityInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getModemStackStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getModemStackStatus is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getRadioCapability(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getRadioCapability is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void nvReadItem(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvReadItem is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void nvResetConfig(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvResetConfig is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void nvWriteCdmaPrl(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvWriteCdmaPrl is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void nvWriteItem(int i, NvWriteItem nvWriteItem) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(nvWriteItem, 0);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvWriteItem is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void requestShutdown(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestShutdown is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void responseAcknowledgement() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void sendDeviceState(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendDeviceState is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void setRadioCapability(int i, RadioCapability radioCapability) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(radioCapability, 0);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setRadioCapability is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void setRadioPower(int i, boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setRadioPower is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void setResponseFunctions(IRadioModemResponse iRadioModemResponse, IRadioModemIndication iRadioModemIndication) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRadioModemResponse);
                    parcelObtain.writeStrongInterface(iRadioModemIndication);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getImei(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(18, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getImei is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
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

            @Override // android.hardware.radio.modem.IRadioModem
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
