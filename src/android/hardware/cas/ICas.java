package android.hardware.cas;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICas extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$cas$ICas".replace('$', '.');
    public static final String HASH = "bc51d8d70a55ec4723d3f73d0acf7003306bf69f";
    public static final int VERSION = 1;

    void closeSession(byte[] bArr) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    byte[] openSession(int i, int i2) throws RemoteException;

    byte[] openSessionDefault() throws RemoteException;

    void processEcm(byte[] bArr, byte[] bArr2) throws RemoteException;

    void processEmm(byte[] bArr) throws RemoteException;

    void provision(String str) throws RemoteException;

    void refreshEntitlements(int i, byte[] bArr) throws RemoteException;

    void release() throws RemoteException;

    void sendEvent(int i, int i2, byte[] bArr) throws RemoteException;

    void sendSessionEvent(byte[] bArr, int i, int i2, byte[] bArr2) throws RemoteException;

    void setPrivateData(byte[] bArr) throws RemoteException;

    void setSessionPrivateData(byte[] bArr, byte[] bArr2) throws RemoteException;

    public static class Default implements ICas {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.cas.ICas
        public void closeSession(byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.cas.ICas
        public byte[] openSession(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.cas.ICas
        public byte[] openSessionDefault() throws RemoteException {
            return null;
        }

        @Override // android.hardware.cas.ICas
        public void processEcm(byte[] bArr, byte[] bArr2) throws RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void processEmm(byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void provision(String str) throws RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void refreshEntitlements(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void release() throws RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void sendEvent(int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void sendSessionEvent(byte[] bArr, int i, int i2, byte[] bArr2) throws RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void setPrivateData(byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void setSessionPrivateData(byte[] bArr, byte[] bArr2) throws RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ICas {
        static final int TRANSACTION_closeSession = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_openSession = 3;
        static final int TRANSACTION_openSessionDefault = 2;
        static final int TRANSACTION_processEcm = 4;
        static final int TRANSACTION_processEmm = 5;
        static final int TRANSACTION_provision = 6;
        static final int TRANSACTION_refreshEntitlements = 7;
        static final int TRANSACTION_release = 8;
        static final int TRANSACTION_sendEvent = 9;
        static final int TRANSACTION_sendSessionEvent = 10;
        static final int TRANSACTION_setPrivateData = 11;
        static final int TRANSACTION_setSessionPrivateData = 12;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ICas asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICas)) {
                return (ICas) iInterfaceQueryLocalInterface;
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
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    closeSession(bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    byte[] bArrOpenSessionDefault = openSessionDefault();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrOpenSessionDefault);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] bArrOpenSession = openSession(i3, i4);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrOpenSession);
                    return true;
                case 4:
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    processEcm(bArrCreateByteArray2, bArrCreateByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    processEmm(bArrCreateByteArray4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    provision(string);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i5 = parcel.readInt();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    refreshEntitlements(i5, bArrCreateByteArray5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    release();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendEvent(i6, i7, bArrCreateByteArray6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendSessionEvent(bArrCreateByteArray7, i8, i9, bArrCreateByteArray8);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    byte[] bArrCreateByteArray9 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setPrivateData(bArrCreateByteArray9);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    byte[] bArrCreateByteArray10 = parcel.createByteArray();
                    byte[] bArrCreateByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setSessionPrivateData(bArrCreateByteArray10, bArrCreateByteArray11);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICas {
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

            @Override // android.hardware.cas.ICas
            public void closeSession(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method closeSession is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public byte[] openSessionDefault() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method openSessionDefault is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public byte[] openSession(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method openSession is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void processEcm(byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method processEcm is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void processEmm(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method processEmm is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void provision(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method provision is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void refreshEntitlements(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method refreshEntitlements is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void release() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method release is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void sendEvent(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method sendEvent is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void sendSessionEvent(byte[] bArr, int i, int i2, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method sendSessionEvent is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void setPrivateData(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setPrivateData is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void setSessionPrivateData(byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setSessionPrivateData is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
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

            @Override // android.hardware.cas.ICas
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
