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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISehRadioSimIndication)) {
                return (ISehRadioSimIndication) iInterfaceQueryLocalInterface;
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
                    simPhonebookReadyIndication(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    phonebookInitCompleteIndication(i4);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stkSmsSendResultIndication(i5, i6);
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stkCallControlResultIndication(i7, string);
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simSwapStateChangedIndication(i8, i9);
                    return true;
                case 6:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simCountMismatchedIndication(i10, i11);
                    return true;
                case 7:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simOnOffStateChangedNotify(i12, i13);
                    return true;
                case 8:
                    int i14 = parcel.readInt();
                    SehSsReleaseComplete sehSsReleaseComplete = (SehSsReleaseComplete) parcel.readTypedObject(SehSsReleaseComplete.CREATOR);
                    parcel.enforceNoDataAvail();
                    releaseCompleteMessageIndication(i14, sehSsReleaseComplete);
                    return true;
                case 9:
                    int i15 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sapNotify(i15, bArrCreateByteArray);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simPhonebookReadyIndication is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void phonebookInitCompleteIndication(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method phonebookInitCompleteIndication is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void stkSmsSendResultIndication(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stkSmsSendResultIndication is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void stkCallControlResultIndication(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stkCallControlResultIndication is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void simSwapStateChangedIndication(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simSwapStateChangedIndication is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void simCountMismatchedIndication(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simCountMismatchedIndication is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void simOnOffStateChangedNotify(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simOnOffStateChangedNotify is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void releaseCompleteMessageIndication(int i, SehSsReleaseComplete sehSsReleaseComplete) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehSsReleaseComplete, 0);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method releaseCompleteMessageIndication is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
            public void sapNotify(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sapNotify is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
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

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
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
