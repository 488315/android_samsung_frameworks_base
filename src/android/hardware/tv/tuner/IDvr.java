package android.hardware.tv.tuner;

import android.hardware.common.fmq.MQDescriptor;
import android.hardware.tv.tuner.IFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDvr extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$tv$tuner$IDvr".replace('$', '.');
    public static final String HASH = "b0d0067a930514438d7772c2e02069c7370f3620";
    public static final int VERSION = 3;

    void attachFilter(IFilter iFilter) throws RemoteException;

    void close() throws RemoteException;

    void configure(DvrSettings dvrSettings) throws RemoteException;

    void detachFilter(IFilter iFilter) throws RemoteException;

    void flush() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getQueueDesc(MQDescriptor<Byte, Byte> mQDescriptor) throws RemoteException;

    void setStatusCheckIntervalHint(long j) throws RemoteException;

    void start() throws RemoteException;

    void stop() throws RemoteException;

    public static class Default implements IDvr {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void attachFilter(IFilter iFilter) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void close() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void configure(DvrSettings dvrSettings) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void detachFilter(IFilter iFilter) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void flush() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void getQueueDesc(MQDescriptor<Byte, Byte> mQDescriptor) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void setStatusCheckIntervalHint(long j) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void start() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void stop() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IDvr {
        static final int TRANSACTION_attachFilter = 3;
        static final int TRANSACTION_close = 8;
        static final int TRANSACTION_configure = 2;
        static final int TRANSACTION_detachFilter = 4;
        static final int TRANSACTION_flush = 7;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getQueueDesc = 1;
        static final int TRANSACTION_setStatusCheckIntervalHint = 9;
        static final int TRANSACTION_start = 5;
        static final int TRANSACTION_stop = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IDvr asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDvr)) {
                return (IDvr) queryLocalInterface;
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
                    MQDescriptor<Byte, Byte> mQDescriptor = new MQDescriptor<>();
                    parcel.enforceNoDataAvail();
                    getQueueDesc(mQDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mQDescriptor, 1);
                    return true;
                case 2:
                    DvrSettings dvrSettings = (DvrSettings) parcel.readTypedObject(DvrSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    configure(dvrSettings);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IFilter asInterface = IFilter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachFilter(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IFilter asInterface2 = IFilter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    detachFilter(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    start();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    stop();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    flush();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setStatusCheckIntervalHint(readLong);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDvr {
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

            @Override // android.hardware.tv.tuner.IDvr
            public void getQueueDesc(MQDescriptor<Byte, Byte> mQDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getQueueDesc is unimplemented.");
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        mQDescriptor.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void configure(DvrSettings dvrSettings) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(dvrSettings, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method configure is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void attachFilter(IFilter iFilter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iFilter);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method attachFilter is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void detachFilter(IFilter iFilter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iFilter);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method detachFilter is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void start() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method start is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void stop() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method stop is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void flush() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method flush is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void setStatusCheckIntervalHint(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setStatusCheckIntervalHint is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
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

            @Override // android.hardware.tv.tuner.IDvr
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
