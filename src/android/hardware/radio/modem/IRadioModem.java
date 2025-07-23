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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioModem)) {
                return (IRadioModem) queryLocalInterface;
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableModem(readInt, readBoolean);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getBasebandVersion(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getDeviceIdentity(readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getHardwareConfig(readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getModemActivityInfo(readInt5);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getModemStackStatus(readInt6);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getRadioCapability(readInt7);
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    nvReadItem(readInt8, readInt9);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    nvResetConfig(readInt10, readInt11);
                    return true;
                case 10:
                    int readInt12 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    nvWriteCdmaPrl(readInt12, createByteArray);
                    return true;
                case 11:
                    int readInt13 = parcel.readInt();
                    NvWriteItem nvWriteItem = (NvWriteItem) parcel.readTypedObject(NvWriteItem.CREATOR);
                    parcel.enforceNoDataAvail();
                    nvWriteItem(readInt13, nvWriteItem);
                    return true;
                case 12:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestShutdown(readInt14);
                    return true;
                case 13:
                    responseAcknowledgement();
                    return true;
                case 14:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendDeviceState(readInt15, readInt16, readBoolean2);
                    return true;
                case 15:
                    int readInt17 = parcel.readInt();
                    RadioCapability radioCapability = (RadioCapability) parcel.readTypedObject(RadioCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRadioCapability(readInt17, radioCapability);
                    return true;
                case 16:
                    int readInt18 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRadioPower(readInt18, readBoolean3, readBoolean4, readBoolean5);
                    return true;
                case 17:
                    IRadioModemResponse asInterface = IRadioModemResponse.Stub.asInterface(parcel.readStrongBinder());
                    IRadioModemIndication asInterface2 = IRadioModemIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    return true;
                case 18:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getImei(readInt19);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableModem is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getBasebandVersion(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getBasebandVersion is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getDeviceIdentity(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getDeviceIdentity is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getHardwareConfig(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getHardwareConfig is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getModemActivityInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getModemActivityInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getModemStackStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getModemStackStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getRadioCapability(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getRadioCapability is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void nvReadItem(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvReadItem is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void nvResetConfig(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvResetConfig is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void nvWriteCdmaPrl(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvWriteCdmaPrl is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void nvWriteItem(int i, NvWriteItem nvWriteItem) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(nvWriteItem, 0);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nvWriteItem is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void requestShutdown(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestShutdown is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void responseAcknowledgement() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void sendDeviceState(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendDeviceState is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void setRadioCapability(int i, RadioCapability radioCapability) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(radioCapability, 0);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setRadioCapability is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void setRadioPower(int i, boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    if (this.mRemote.transact(16, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setRadioPower is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void setResponseFunctions(IRadioModemResponse iRadioModemResponse, IRadioModemIndication iRadioModemIndication) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iRadioModemResponse);
                    obtain.writeStrongInterface(iRadioModemIndication);
                    if (this.mRemote.transact(17, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
            public void getImei(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(18, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getImei is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModem
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

            @Override // android.hardware.radio.modem.IRadioModem
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
