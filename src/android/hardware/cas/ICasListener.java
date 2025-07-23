package android.hardware.cas;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICasListener extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$cas$ICasListener".replace('$', '.');
    public static final String HASH = "bc51d8d70a55ec4723d3f73d0acf7003306bf69f";
    public static final int VERSION = 1;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void onEvent(int i, int i2, byte[] bArr) throws RemoteException;

    void onSessionEvent(byte[] bArr, int i, int i2, byte[] bArr2) throws RemoteException;

    void onStatusUpdate(byte b, int i) throws RemoteException;

    public static class Default implements ICasListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.cas.ICasListener
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.cas.ICasListener
        public void onEvent(int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.cas.ICasListener
        public void onSessionEvent(byte[] bArr, int i, int i2, byte[] bArr2) throws RemoteException {
        }

        @Override // android.hardware.cas.ICasListener
        public void onStatusUpdate(byte b, int i) throws RemoteException {
        }

        @Override // android.hardware.cas.ICasListener
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ICasListener {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onEvent = 1;
        static final int TRANSACTION_onSessionEvent = 2;
        static final int TRANSACTION_onStatusUpdate = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ICasListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICasListener)) {
                return (ICasListener) queryLocalInterface;
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
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onEvent(readInt, readInt2, createByteArray);
                parcel2.writeNoException();
            } else if (i == 2) {
                byte[] createByteArray2 = parcel.createByteArray();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                byte[] createByteArray3 = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onSessionEvent(createByteArray2, readInt3, readInt4, createByteArray3);
                parcel2.writeNoException();
            } else if (i == 3) {
                byte readByte = parcel.readByte();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStatusUpdate(readByte, readInt5);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICasListener {
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

            @Override // android.hardware.cas.ICasListener
            public void onEvent(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method onEvent is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICasListener
            public void onSessionEvent(byte[] bArr, int i, int i2, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method onSessionEvent is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICasListener
            public void onStatusUpdate(byte b, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method onStatusUpdate is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICasListener
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

            @Override // android.hardware.cas.ICasListener
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
