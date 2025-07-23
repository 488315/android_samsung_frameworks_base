package android.hardware.radio.modem;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioModemIndication extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$modem$IRadioModemIndication".replace('$', '.');
    public static final String HASH = "787419262f7c39ea36c0fbe22681bada95d1f97b";
    public static final int VERSION = 4;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void hardwareConfigChanged(int i, HardwareConfig[] hardwareConfigArr) throws RemoteException;

    void modemReset(int i, String str) throws RemoteException;

    void onImeiMappingChanged(int i, ImeiInfo imeiInfo) throws RemoteException;

    void radioCapabilityIndication(int i, RadioCapability radioCapability) throws RemoteException;

    void radioStateChanged(int i, int i2) throws RemoteException;

    void rilConnected(int i) throws RemoteException;

    public static class Default implements IRadioModemIndication {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public void hardwareConfigChanged(int i, HardwareConfig[] hardwareConfigArr) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public void modemReset(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public void onImeiMappingChanged(int i, ImeiInfo imeiInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public void radioCapabilityIndication(int i, RadioCapability radioCapability) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public void radioStateChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public void rilConnected(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioModemIndication {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_hardwareConfigChanged = 1;
        static final int TRANSACTION_modemReset = 2;
        static final int TRANSACTION_onImeiMappingChanged = 6;
        static final int TRANSACTION_radioCapabilityIndication = 3;
        static final int TRANSACTION_radioStateChanged = 4;
        static final int TRANSACTION_rilConnected = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioModemIndication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioModemIndication)) {
                return (IRadioModemIndication) queryLocalInterface;
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
                    HardwareConfig[] hardwareConfigArr = (HardwareConfig[]) parcel.createTypedArray(HardwareConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    hardwareConfigChanged(readInt, hardwareConfigArr);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    modemReset(readInt2, readString);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    RadioCapability radioCapability = (RadioCapability) parcel.readTypedObject(RadioCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    radioCapabilityIndication(readInt3, radioCapability);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    radioStateChanged(readInt4, readInt5);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    rilConnected(readInt6);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    ImeiInfo imeiInfo = (ImeiInfo) parcel.readTypedObject(ImeiInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onImeiMappingChanged(readInt7, imeiInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioModemIndication {
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

            @Override // android.hardware.radio.modem.IRadioModemIndication
            public void hardwareConfigChanged(int i, HardwareConfig[] hardwareConfigArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(hardwareConfigArr, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method hardwareConfigChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemIndication
            public void modemReset(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method modemReset is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemIndication
            public void radioCapabilityIndication(int i, RadioCapability radioCapability) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(radioCapability, 0);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method radioCapabilityIndication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemIndication
            public void radioStateChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method radioStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemIndication
            public void rilConnected(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method rilConnected is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemIndication
            public void onImeiMappingChanged(int i, ImeiInfo imeiInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imeiInfo, 0);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onImeiMappingChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemIndication
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

            @Override // android.hardware.radio.modem.IRadioModemIndication
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
