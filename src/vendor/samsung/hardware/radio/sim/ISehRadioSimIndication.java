package vendor.samsung.hardware.radio.sim;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehRadioSimIndication extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$sim$ISehRadioSimIndication".replace('$', '.');
    public static final String HASH = "e94363ef2041c98feabe84e756d983ad196394e8";
    public static final int VERSION = 1;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void phonebookInitCompleteIndication(int i) throws RemoteException;

    void releaseCompleteMessageIndication(int i, SehSsReleaseComplete sehSsReleaseComplete) throws RemoteException;

    void sapNotify(int i, byte[] bArr) throws RemoteException;

    void simCountMismatchedIndication(int i, int i2) throws RemoteException;

    void simOnOffStateChangedNotify(int i, int i2) throws RemoteException;

    void simPhonebookReadyIndication(int i) throws RemoteException;

    void simSwapStateChangedIndication(int i, int i2) throws RemoteException;

    void stkCallControlResultIndication(int i, String str) throws RemoteException;

    void stkSmsSendResultIndication(int i, int i2) throws RemoteException;

    public static class Default implements ISehRadioSimIndication {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
        public void phonebookInitCompleteIndication(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
        public void releaseCompleteMessageIndication(int i, SehSsReleaseComplete sehSsReleaseComplete) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
        public void sapNotify(int i, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
        public void simCountMismatchedIndication(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
        public void simOnOffStateChangedNotify(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
        public void simPhonebookReadyIndication(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
        public void simSwapStateChangedIndication(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
        public void stkCallControlResultIndication(int i, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
        public void stkSmsSendResultIndication(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioSimIndication {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_phonebookInitCompleteIndication = 2;
        static final int TRANSACTION_releaseCompleteMessageIndication = 8;
        static final int TRANSACTION_sapNotify = 9;
        static final int TRANSACTION_simCountMismatchedIndication = 6;
        static final int TRANSACTION_simOnOffStateChangedNotify = 7;
        static final int TRANSACTION_simPhonebookReadyIndication = 1;
        static final int TRANSACTION_simSwapStateChangedIndication = 5;
        static final int TRANSACTION_stkCallControlResultIndication = 4;
        static final int TRANSACTION_stkSmsSendResultIndication = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSimIndication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioSimIndication)) {
                return (ISehRadioSimIndication) queryLocalInterface;
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
                    simPhonebookReadyIndication(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    phonebookInitCompleteIndication(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stkSmsSendResultIndication(readInt3, readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stkCallControlResultIndication(readInt5, readString);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simSwapStateChangedIndication(readInt6, readInt7);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simCountMismatchedIndication(readInt8, readInt9);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simOnOffStateChangedNotify(readInt10, readInt11);
                    return true;
                case 8:
                    int readInt12 = parcel.readInt();
                    SehSsReleaseComplete sehSsReleaseComplete = (SehSsReleaseComplete) parcel.readTypedObject(SehSsReleaseComplete.CREATOR);
                    parcel.enforceNoDataAvail();
                    releaseCompleteMessageIndication(readInt12, sehSsReleaseComplete);
                    return true;
                case 9:
                    int readInt13 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sapNotify(readInt13, createByteArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioSimIndication {
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

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void simPhonebookReadyIndication(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simPhonebookReadyIndication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void phonebookInitCompleteIndication(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method phonebookInitCompleteIndication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void stkSmsSendResultIndication(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stkSmsSendResultIndication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void stkCallControlResultIndication(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stkCallControlResultIndication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void simSwapStateChangedIndication(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simSwapStateChangedIndication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void simCountMismatchedIndication(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simCountMismatchedIndication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void simOnOffStateChangedNotify(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simOnOffStateChangedNotify is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void releaseCompleteMessageIndication(int i, SehSsReleaseComplete sehSsReleaseComplete) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehSsReleaseComplete, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method releaseCompleteMessageIndication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void sapNotify(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sapNotify is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
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

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
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
