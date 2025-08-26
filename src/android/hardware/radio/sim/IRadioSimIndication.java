package android.hardware.radio.sim;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioSimIndication extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$sim$IRadioSimIndication".replace('$', '.');
    public static final String HASH = "fc1a19a4f86a58981158cc8d956763c9d8ace630";
    public static final int VERSION = 4;

    void carrierInfoForImsiEncryption(int i) throws RemoteException;

    @Deprecated
    void cdmaSubscriptionSourceChanged(int i, int i2) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void simPhonebookChanged(int i) throws RemoteException;

    void simPhonebookRecordsReceived(int i, byte b, PhonebookRecordInfo[] phonebookRecordInfoArr) throws RemoteException;

    void simRefresh(int i, SimRefreshResult simRefreshResult) throws RemoteException;

    void simStatusChanged(int i) throws RemoteException;

    void stkEventNotify(int i, String str) throws RemoteException;

    void stkProactiveCommand(int i, String str) throws RemoteException;

    void stkSessionEnd(int i) throws RemoteException;

    void subscriptionStatusChanged(int i, boolean z) throws RemoteException;

    void uiccApplicationsEnablementChanged(int i, boolean z) throws RemoteException;

    public static class Default implements IRadioSimIndication {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void carrierInfoForImsiEncryption(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void cdmaSubscriptionSourceChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void simPhonebookChanged(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void simPhonebookRecordsReceived(int i, byte b, PhonebookRecordInfo[] phonebookRecordInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void simRefresh(int i, SimRefreshResult simRefreshResult) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void simStatusChanged(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void stkEventNotify(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void stkProactiveCommand(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void stkSessionEnd(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void subscriptionStatusChanged(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public void uiccApplicationsEnablementChanged(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimIndication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioSimIndication {
        static final int TRANSACTION_carrierInfoForImsiEncryption = 1;
        static final int TRANSACTION_cdmaSubscriptionSourceChanged = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_simPhonebookChanged = 3;
        static final int TRANSACTION_simPhonebookRecordsReceived = 4;
        static final int TRANSACTION_simRefresh = 5;
        static final int TRANSACTION_simStatusChanged = 6;
        static final int TRANSACTION_stkEventNotify = 7;
        static final int TRANSACTION_stkProactiveCommand = 8;
        static final int TRANSACTION_stkSessionEnd = 9;
        static final int TRANSACTION_subscriptionStatusChanged = 10;
        static final int TRANSACTION_uiccApplicationsEnablementChanged = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioSimIndication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioSimIndication)) {
                return (IRadioSimIndication) iInterfaceQueryLocalInterface;
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
                    carrierInfoForImsiEncryption(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cdmaSubscriptionSourceChanged(i4, i5);
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simPhonebookChanged(i6);
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    byte b = parcel.readByte();
                    PhonebookRecordInfo[] phonebookRecordInfoArr = (PhonebookRecordInfo[]) parcel.createTypedArray(PhonebookRecordInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    simPhonebookRecordsReceived(i7, b, phonebookRecordInfoArr);
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    SimRefreshResult simRefreshResult = (SimRefreshResult) parcel.readTypedObject(SimRefreshResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    simRefresh(i8, simRefreshResult);
                    return true;
                case 6:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simStatusChanged(i9);
                    return true;
                case 7:
                    int i10 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stkEventNotify(i10, string);
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stkProactiveCommand(i11, string2);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stkSessionEnd(i12);
                    return true;
                case 10:
                    int i13 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    subscriptionStatusChanged(i13, z);
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    uiccApplicationsEnablementChanged(i14, z2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioSimIndication {
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

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void carrierInfoForImsiEncryption(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method carrierInfoForImsiEncryption is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void cdmaSubscriptionSourceChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cdmaSubscriptionSourceChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void simPhonebookChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simPhonebookChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void simPhonebookRecordsReceived(int i, byte b, PhonebookRecordInfo[] phonebookRecordInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeTypedArray(phonebookRecordInfoArr, 0);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simPhonebookRecordsReceived is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void simRefresh(int i, SimRefreshResult simRefreshResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(simRefreshResult, 0);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simRefresh is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void simStatusChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simStatusChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void stkEventNotify(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stkEventNotify is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void stkProactiveCommand(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stkProactiveCommand is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void stkSessionEnd(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stkSessionEnd is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void subscriptionStatusChanged(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method subscriptionStatusChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void uiccApplicationsEnablementChanged(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method uiccApplicationsEnablementChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
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

            @Override // android.hardware.radio.sim.IRadioSimIndication
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
