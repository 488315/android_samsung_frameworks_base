package vendor.samsung.hardware.authfw;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehAuthenticationFramework extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$authfw$ISehAuthenticationFramework".replace('$', '.');
    public static final String HASH = "ea997bca08b7fab1afc1f53991c5a068408d1670";
    public static final int VERSION = 1;

    SehResult execute(int i, byte[] bArr) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    boolean load(int i, byte[] bArr) throws RemoteException;

    boolean terminate(int i) throws RemoteException;

    public static class Default implements ISehAuthenticationFramework {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
        public SehResult execute(int i, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
        public boolean load(int i, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
        public boolean terminate(int i) throws RemoteException {
            return false;
        }

        @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehAuthenticationFramework {
        static final int TRANSACTION_execute = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_load = 1;
        static final int TRANSACTION_terminate = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehAuthenticationFramework asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehAuthenticationFramework)) {
                return (ISehAuthenticationFramework) queryLocalInterface;
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
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                boolean load = load(readInt, createByteArray);
                parcel2.writeNoException();
                parcel2.writeBoolean(load);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean terminate = terminate(readInt2);
                parcel2.writeNoException();
                parcel2.writeBoolean(terminate);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                byte[] createByteArray2 = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                SehResult execute = execute(readInt3, createByteArray2);
                parcel2.writeNoException();
                parcel2.writeTypedObject(execute, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISehAuthenticationFramework {
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

            @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
            public boolean load(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method load is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
            public boolean terminate(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method terminate is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
            public SehResult execute(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method execute is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehResult) obtain2.readTypedObject(SehResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
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

            @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
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
