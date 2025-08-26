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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKeyMintOperation)) {
                return (IKeyMintOperation) iInterfaceQueryLocalInterface;
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
                byte[] bArrCreateByteArray = parcel.createByteArray();
                HardwareAuthToken hardwareAuthToken = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                TimeStampToken timeStampToken = (TimeStampToken) parcel.readTypedObject(TimeStampToken.CREATOR);
                parcel.enforceNoDataAvail();
                updateAad(bArrCreateByteArray, hardwareAuthToken, timeStampToken);
                parcel2.writeNoException();
            } else if (i == 2) {
                byte[] bArrCreateByteArray2 = parcel.createByteArray();
                HardwareAuthToken hardwareAuthToken2 = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                TimeStampToken timeStampToken2 = (TimeStampToken) parcel.readTypedObject(TimeStampToken.CREATOR);
                parcel.enforceNoDataAvail();
                byte[] bArrUpdate = update(bArrCreateByteArray2, hardwareAuthToken2, timeStampToken2);
                parcel2.writeNoException();
                parcel2.writeByteArray(bArrUpdate);
            } else if (i == 3) {
                byte[] bArrCreateByteArray3 = parcel.createByteArray();
                byte[] bArrCreateByteArray4 = parcel.createByteArray();
                HardwareAuthToken hardwareAuthToken3 = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                TimeStampToken timeStampToken3 = (TimeStampToken) parcel.readTypedObject(TimeStampToken.CREATOR);
                byte[] bArrCreateByteArray5 = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                byte[] bArrFinish = finish(bArrCreateByteArray3, bArrCreateByteArray4, hardwareAuthToken3, timeStampToken3, bArrCreateByteArray5);
                parcel2.writeNoException();
                parcel2.writeByteArray(bArrFinish);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(hardwareAuthToken, 0);
                    parcelObtain.writeTypedObject(timeStampToken, 0);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method updateAad is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintOperation
            public byte[] update(byte[] bArr, HardwareAuthToken hardwareAuthToken, TimeStampToken timeStampToken) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(hardwareAuthToken, 0);
                    parcelObtain.writeTypedObject(timeStampToken, 0);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method update is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintOperation
            public byte[] finish(byte[] bArr, byte[] bArr2, HardwareAuthToken hardwareAuthToken, TimeStampToken timeStampToken, byte[] bArr3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeTypedObject(hardwareAuthToken, 0);
                    parcelObtain.writeTypedObject(timeStampToken, 0);
                    parcelObtain.writeByteArray(bArr3);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method finish is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintOperation
            public void abort() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method abort is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintOperation
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

            @Override // android.hardware.security.keymint.IKeyMintOperation
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
