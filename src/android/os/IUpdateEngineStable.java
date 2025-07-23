package android.os;

import android.os.IUpdateEngineStableCallback;

/* loaded from: classes3.dex */
public interface IUpdateEngineStable extends IInterface {
    public static final String DESCRIPTOR = "android$os$IUpdateEngineStable".replace('$', '.');
    public static final String HASH = "ee2e6f0bd51391955f79f4d5eeeafc37c668cd40";
    public static final int VERSION = 2;

    void applyPayloadFd(ParcelFileDescriptor parcelFileDescriptor, long j, long j2, String[] strArr) throws RemoteException;

    boolean bind(IUpdateEngineStableCallback iUpdateEngineStableCallback) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    boolean unbind(IUpdateEngineStableCallback iUpdateEngineStableCallback) throws RemoteException;

    public static class Default implements IUpdateEngineStable {
        @Override // android.os.IUpdateEngineStable
        public void applyPayloadFd(ParcelFileDescriptor parcelFileDescriptor, long j, long j2, String[] strArr) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IUpdateEngineStable
        public boolean bind(IUpdateEngineStableCallback iUpdateEngineStableCallback) throws RemoteException {
            return false;
        }

        @Override // android.os.IUpdateEngineStable
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.os.IUpdateEngineStable
        public boolean unbind(IUpdateEngineStableCallback iUpdateEngineStableCallback) throws RemoteException {
            return false;
        }

        @Override // android.os.IUpdateEngineStable
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IUpdateEngineStable {
        static final int TRANSACTION_applyPayloadFd = 1;
        static final int TRANSACTION_bind = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_unbind = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUpdateEngineStable asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUpdateEngineStable)) {
                return (IUpdateEngineStable) queryLocalInterface;
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
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                long readLong = parcel.readLong();
                long readLong2 = parcel.readLong();
                String[] createStringArray = parcel.createStringArray();
                parcel.enforceNoDataAvail();
                applyPayloadFd(parcelFileDescriptor, readLong, readLong2, createStringArray);
                parcel2.writeNoException();
            } else if (i == 2) {
                IUpdateEngineStableCallback asInterface = IUpdateEngineStableCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                boolean bind = bind(asInterface);
                parcel2.writeNoException();
                parcel2.writeBoolean(bind);
            } else if (i == 3) {
                IUpdateEngineStableCallback asInterface2 = IUpdateEngineStableCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                boolean unbind = unbind(asInterface2);
                parcel2.writeNoException();
                parcel2.writeBoolean(unbind);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IUpdateEngineStable {
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

            @Override // android.os.IUpdateEngineStable
            public void applyPayloadFd(ParcelFileDescriptor parcelFileDescriptor, long j, long j2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method applyPayloadFd is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngineStable
            public boolean bind(IUpdateEngineStableCallback iUpdateEngineStableCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iUpdateEngineStableCallback);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method bind is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngineStable
            public boolean unbind(IUpdateEngineStableCallback iUpdateEngineStableCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iUpdateEngineStableCallback);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method unbind is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngineStable
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

            @Override // android.os.IUpdateEngineStable
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
