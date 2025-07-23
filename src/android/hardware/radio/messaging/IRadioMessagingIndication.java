package android.hardware.radio.messaging;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioMessagingIndication extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$messaging$IRadioMessagingIndication".replace('$', '.');
    public static final String HASH = "b28416394e6595c08e97c0473855eb05eed1baed";
    public static final int VERSION = 4;

    @Deprecated
    void cdmaNewSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException;

    @Deprecated
    void cdmaRuimSmsStorageFull(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void newBroadcastSms(int i, byte[] bArr) throws RemoteException;

    void newSms(int i, byte[] bArr) throws RemoteException;

    void newSmsOnSim(int i, int i2) throws RemoteException;

    void newSmsStatusReport(int i, byte[] bArr) throws RemoteException;

    void simSmsStorageFull(int i) throws RemoteException;

    public static class Default implements IRadioMessagingIndication {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void cdmaNewSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void cdmaRuimSmsStorageFull(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void newBroadcastSms(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void newSms(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void newSmsOnSim(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void newSmsStatusReport(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public void simSmsStorageFull(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.messaging.IRadioMessagingIndication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioMessagingIndication {
        static final int TRANSACTION_cdmaNewSms = 1;
        static final int TRANSACTION_cdmaRuimSmsStorageFull = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_newBroadcastSms = 3;
        static final int TRANSACTION_newSms = 4;
        static final int TRANSACTION_newSmsOnSim = 5;
        static final int TRANSACTION_newSmsStatusReport = 6;
        static final int TRANSACTION_simSmsStorageFull = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioMessagingIndication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioMessagingIndication)) {
                return (IRadioMessagingIndication) queryLocalInterface;
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
                    CdmaSmsMessage cdmaSmsMessage = (CdmaSmsMessage) parcel.readTypedObject(CdmaSmsMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    cdmaNewSms(readInt, cdmaSmsMessage);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cdmaRuimSmsStorageFull(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    newBroadcastSms(readInt3, createByteArray);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    newSms(readInt4, createByteArray2);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    newSmsOnSim(readInt5, readInt6);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    newSmsStatusReport(readInt7, createByteArray3);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    simSmsStorageFull(readInt8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioMessagingIndication {
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

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void cdmaNewSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cdmaSmsMessage, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cdmaNewSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void cdmaRuimSmsStorageFull(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cdmaRuimSmsStorageFull is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void newBroadcastSms(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method newBroadcastSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void newSms(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method newSms is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void newSmsOnSim(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method newSmsOnSim is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void newSmsStatusReport(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method newSmsStatusReport is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
            public void simSmsStorageFull(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method simSmsStorageFull is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
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

            @Override // android.hardware.radio.messaging.IRadioMessagingIndication
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
