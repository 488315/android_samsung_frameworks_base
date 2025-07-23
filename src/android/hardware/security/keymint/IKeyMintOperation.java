package android.hardware.security.keymint;

import android.hardware.security.secureclock.TimeStampToken;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IKeyMintOperation extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$security$keymint$IKeyMintOperation".replace('$', '.');
    public static final String HASH = "a05c8079586139db45b0762a528cdd9745ad15ce";
    public static final int VERSION = 4;

    void abort() throws RemoteException;

    byte[] finish(byte[] bArr, byte[] bArr2, HardwareAuthToken hardwareAuthToken, TimeStampToken timeStampToken, byte[] bArr3) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    byte[] update(byte[] bArr, HardwareAuthToken hardwareAuthToken, TimeStampToken timeStampToken) throws RemoteException;

    void updateAad(byte[] bArr, HardwareAuthToken hardwareAuthToken, TimeStampToken timeStampToken) throws RemoteException;

    public static class Default implements IKeyMintOperation {
        @Override // android.hardware.security.keymint.IKeyMintOperation
        public void abort() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintOperation
        public byte[] finish(byte[] bArr, byte[] bArr2, HardwareAuthToken hardwareAuthToken, TimeStampToken timeStampToken, byte[] bArr3) throws RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintOperation
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.security.keymint.IKeyMintOperation
        public byte[] update(byte[] bArr, HardwareAuthToken hardwareAuthToken, TimeStampToken timeStampToken) throws RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintOperation
        public void updateAad(byte[] bArr, HardwareAuthToken hardwareAuthToken, TimeStampToken timeStampToken) throws RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintOperation
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IKeyMintOperation {
        static final int TRANSACTION_abort = 4;
        static final int TRANSACTION_finish = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_update = 2;
        static final int TRANSACTION_updateAad = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IKeyMintOperation asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKeyMintOperation)) {
                return (IKeyMintOperation) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "updateAad";
            }
            if (i == 2) {
                return "update";
            }
            if (i == 3) {
                return "finish";
            }
            if (i == 4) {
                return "abort";
            }
            switch (i) {
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
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
            if (i == 1) {
                byte[] createByteArray = parcel.createByteArray();
                HardwareAuthToken hardwareAuthToken = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                TimeStampToken timeStampToken = (TimeStampToken) parcel.readTypedObject(TimeStampToken.CREATOR);
                parcel.enforceNoDataAvail();
                updateAad(createByteArray, hardwareAuthToken, timeStampToken);
                parcel2.writeNoException();
            } else if (i == 2) {
                byte[] createByteArray2 = parcel.createByteArray();
                HardwareAuthToken hardwareAuthToken2 = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                TimeStampToken timeStampToken2 = (TimeStampToken) parcel.readTypedObject(TimeStampToken.CREATOR);
                parcel.enforceNoDataAvail();
                byte[] update = update(createByteArray2, hardwareAuthToken2, timeStampToken2);
                parcel2.writeNoException();
                parcel2.writeByteArray(update);
            } else if (i == 3) {
                byte[] createByteArray3 = parcel.createByteArray();
                byte[] createByteArray4 = parcel.createByteArray();
                HardwareAuthToken hardwareAuthToken3 = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                TimeStampToken timeStampToken3 = (TimeStampToken) parcel.readTypedObject(TimeStampToken.CREATOR);
                byte[] createByteArray5 = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                byte[] finish = finish(createByteArray3, createByteArray4, hardwareAuthToken3, timeStampToken3, createByteArray5);
                parcel2.writeNoException();
                parcel2.writeByteArray(finish);
            } else if (i == 4) {
                abort();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IKeyMintOperation {
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

            @Override // android.hardware.security.keymint.IKeyMintOperation
            public void updateAad(byte[] bArr, HardwareAuthToken hardwareAuthToken, TimeStampToken timeStampToken) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeTypedObject(timeStampToken, 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 32)) {
                        throw new RemoteException("Method updateAad is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintOperation
            public byte[] update(byte[] bArr, HardwareAuthToken hardwareAuthToken, TimeStampToken timeStampToken) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeTypedObject(timeStampToken, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 32)) {
                        throw new RemoteException("Method update is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintOperation
            public byte[] finish(byte[] bArr, byte[] bArr2, HardwareAuthToken hardwareAuthToken, TimeStampToken timeStampToken, byte[] bArr3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeTypedObject(timeStampToken, 0);
                    obtain.writeByteArray(bArr3);
                    if (!this.mRemote.transact(3, obtain, obtain2, 32)) {
                        throw new RemoteException("Method finish is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintOperation
            public void abort() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 32)) {
                        throw new RemoteException("Method abort is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintOperation
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

            @Override // android.hardware.security.keymint.IKeyMintOperation
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
