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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISehRadioSim)) {
                return (ISehRadioSim) iInterfaceQueryLocalInterface;
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
                    getIccCardStatus(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    String string = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyNetworkDepersonalization(i4, string, i5);
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPhonebookStorageInfo(i6, i7);
                    return true;
                case 4:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getUsimPhonebookCapability(i8);
                    return true;
                case 5:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSimOnOff(i9, i10);
                    return true;
                case 6:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSimInitEvent(i11);
                    return true;
                case 7:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSimLockInfo(i12, i13, i14);
                    return true;
                case 8:
                    int i15 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplyIccPersonalization(i15, string2);
                    return true;
                case 9:
                    int i16 = parcel.readInt();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    changeIccPersonalization(i16, string3, string4);
                    return true;
                case 10:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPhonebookEntry(i17, i18, i19);
                    return true;
                case 11:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    SehAdnRecord sehAdnRecord = (SehAdnRecord) parcel.readTypedObject(SehAdnRecord.CREATOR);
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    accessPhonebookEntry(i20, i21, i22, i23, sehAdnRecord, string5);
                    return true;
                case 12:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAtr(i24);
                    return true;
                case 13:
                    ISehRadioSimResponse iSehRadioSimResponseAsInterface = ISehRadioSimResponse.Stub.asInterface(parcel.readStrongBinder());
                    ISehRadioSimIndication iSehRadioSimIndicationAsInterface = ISehRadioSimIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(iSehRadioSimResponseAsInterface, iSehRadioSimIndicationAsInterface);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIccCardStatus is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void supplyNetworkDepersonalization(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyNetworkDepersonalization is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getPhonebookStorageInfo(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPhonebookStorageInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getUsimPhonebookCapability(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getUsimPhonebookCapability is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void setSimOnOff(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimOnOff is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void setSimInitEvent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimInitEvent is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getSimLockInfo(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimLockInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void supplyIccPersonalization(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPersonalization is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void changeIccPersonalization(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method changeIccPersonalization is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getPhonebookEntry(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPhonebookEntry is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void accessPhonebookEntry(int i, int i2, int i3, int i4, SehAdnRecord sehAdnRecord, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeTypedObject(sehAdnRecord, 0);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method accessPhonebookEntry is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getAtr(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAtr is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void setResponseFunctions(ISehRadioSimResponse iSehRadioSimResponse, ISehRadioSimIndication iSehRadioSimIndication) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSehRadioSimResponse);
                    parcelObtain.writeStrongInterface(iSehRadioSimIndication);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
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

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
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
