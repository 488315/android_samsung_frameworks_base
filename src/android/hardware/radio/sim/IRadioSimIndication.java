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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioSimIndication)) {
                return (IRadioSimIndication) queryLocalInterface;
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
                    carrierInfoForImsiEncryption(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cdmaSubscriptionSourceChanged(readInt2, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simPhonebookChanged(readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    byte readByte = parcel.readByte();
                    PhonebookRecordInfo[] phonebookRecordInfoArr = (PhonebookRecordInfo[]) parcel.createTypedArray(PhonebookRecordInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    simPhonebookRecordsReceived(readInt5, readByte, phonebookRecordInfoArr);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    SimRefreshResult simRefreshResult = (SimRefreshResult) parcel.readTypedObject(SimRefreshResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    simRefresh(readInt6, simRefreshResult);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simStatusChanged(readInt7);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stkEventNotify(readInt8, readString);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stkProactiveCommand(readInt9, readString2);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stkSessionEnd(readInt10);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    subscriptionStatusChanged(readInt11, readBoolean);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    uiccApplicationsEnablementChanged(readInt12, readBoolean2);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method carrierInfoForImsiEncryption is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void cdmaSubscriptionSourceChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cdmaSubscriptionSourceChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void simPhonebookChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simPhonebookChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void simPhonebookRecordsReceived(int i, byte b, PhonebookRecordInfo[] phonebookRecordInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    obtain.writeTypedArray(phonebookRecordInfoArr, 0);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simPhonebookRecordsReceived is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void simRefresh(int i, SimRefreshResult simRefreshResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(simRefreshResult, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simRefresh is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void simStatusChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simStatusChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void stkEventNotify(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stkEventNotify is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void stkProactiveCommand(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stkProactiveCommand is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void stkSessionEnd(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stkSessionEnd is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void subscriptionStatusChanged(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method subscriptionStatusChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
            public void uiccApplicationsEnablementChanged(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method uiccApplicationsEnablementChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimIndication
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

            @Override // android.hardware.radio.sim.IRadioSimIndication
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
