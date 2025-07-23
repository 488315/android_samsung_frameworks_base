package vendor.samsung.hardware.radio.sim;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.radio.sim.ISehRadioSimIndication;
import vendor.samsung.hardware.radio.sim.ISehRadioSimResponse;

/* loaded from: classes6.dex */
public interface ISehRadioSim extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$sim$ISehRadioSim".replace('$', '.');
    public static final String HASH = "e94363ef2041c98feabe84e756d983ad196394e8";
    public static final int VERSION = 1;

    void accessPhonebookEntry(int i, int i2, int i3, int i4, SehAdnRecord sehAdnRecord, String str) throws RemoteException;

    void changeIccPersonalization(int i, String str, String str2) throws RemoteException;

    void getAtr(int i) throws RemoteException;

    void getIccCardStatus(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getPhonebookEntry(int i, int i2, int i3) throws RemoteException;

    void getPhonebookStorageInfo(int i, int i2) throws RemoteException;

    void getSimLockInfo(int i, int i2, int i3) throws RemoteException;

    void getUsimPhonebookCapability(int i) throws RemoteException;

    void setResponseFunctions(ISehRadioSimResponse iSehRadioSimResponse, ISehRadioSimIndication iSehRadioSimIndication) throws RemoteException;

    void setSimInitEvent(int i) throws RemoteException;

    void setSimOnOff(int i, int i2) throws RemoteException;

    void supplyIccPersonalization(int i, String str) throws RemoteException;

    void supplyNetworkDepersonalization(int i, String str, int i2) throws RemoteException;

    public static class Default implements ISehRadioSim {
        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void accessPhonebookEntry(int i, int i2, int i3, int i4, SehAdnRecord sehAdnRecord, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void changeIccPersonalization(int i, String str, String str2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void getAtr(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void getIccCardStatus(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void getPhonebookEntry(int i, int i2, int i3) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void getPhonebookStorageInfo(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void getSimLockInfo(int i, int i2, int i3) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void getUsimPhonebookCapability(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void setResponseFunctions(ISehRadioSimResponse iSehRadioSimResponse, ISehRadioSimIndication iSehRadioSimIndication) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void setSimInitEvent(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void setSimOnOff(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void supplyIccPersonalization(int i, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void supplyNetworkDepersonalization(int i, String str, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioSim {
        static final int TRANSACTION_accessPhonebookEntry = 11;
        static final int TRANSACTION_changeIccPersonalization = 9;
        static final int TRANSACTION_getAtr = 12;
        static final int TRANSACTION_getIccCardStatus = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getPhonebookEntry = 10;
        static final int TRANSACTION_getPhonebookStorageInfo = 3;
        static final int TRANSACTION_getSimLockInfo = 7;
        static final int TRANSACTION_getUsimPhonebookCapability = 4;
        static final int TRANSACTION_setResponseFunctions = 13;
        static final int TRANSACTION_setSimInitEvent = 6;
        static final int TRANSACTION_setSimOnOff = 5;
        static final int TRANSACTION_supplyIccPersonalization = 8;
        static final int TRANSACTION_supplyNetworkDepersonalization = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSim asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioSim)) {
                return (ISehRadioSim) queryLocalInterface;
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
                    getIccCardStatus(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    String readString = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyNetworkDepersonalization(readInt2, readString, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPhonebookStorageInfo(readInt4, readInt5);
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getUsimPhonebookCapability(readInt6);
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSimOnOff(readInt7, readInt8);
                    return true;
                case 6:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSimInitEvent(readInt9);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSimLockInfo(readInt10, readInt11, readInt12);
                    return true;
                case 8:
                    int readInt13 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplyIccPersonalization(readInt13, readString2);
                    return true;
                case 9:
                    int readInt14 = parcel.readInt();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    changeIccPersonalization(readInt14, readString3, readString4);
                    return true;
                case 10:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPhonebookEntry(readInt15, readInt16, readInt17);
                    return true;
                case 11:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    SehAdnRecord sehAdnRecord = (SehAdnRecord) parcel.readTypedObject(SehAdnRecord.CREATOR);
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    accessPhonebookEntry(readInt18, readInt19, readInt20, readInt21, sehAdnRecord, readString5);
                    return true;
                case 12:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAtr(readInt22);
                    return true;
                case 13:
                    ISehRadioSimResponse asInterface = ISehRadioSimResponse.Stub.asInterface(parcel.readStrongBinder());
                    ISehRadioSimIndication asInterface2 = ISehRadioSimIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioSim {
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

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getIccCardStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIccCardStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void supplyNetworkDepersonalization(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyNetworkDepersonalization is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getPhonebookStorageInfo(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPhonebookStorageInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getUsimPhonebookCapability(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getUsimPhonebookCapability is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void setSimOnOff(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimOnOff is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void setSimInitEvent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimInitEvent is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getSimLockInfo(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimLockInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void supplyIccPersonalization(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPersonalization is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void changeIccPersonalization(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method changeIccPersonalization is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getPhonebookEntry(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPhonebookEntry is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void accessPhonebookEntry(int i, int i2, int i3, int i4, SehAdnRecord sehAdnRecord, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(sehAdnRecord, 0);
                    obtain.writeString(str);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method accessPhonebookEntry is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getAtr(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAtr is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void setResponseFunctions(ISehRadioSimResponse iSehRadioSimResponse, ISehRadioSimIndication iSehRadioSimIndication) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iSehRadioSimResponse);
                    obtain.writeStrongInterface(iSehRadioSimIndication);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
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

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
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
