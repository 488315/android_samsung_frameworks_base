package android.hardware.tv.tuner;

import android.hardware.tv.tuner.IDvr;
import android.hardware.tv.tuner.IDvrCallback;
import android.hardware.tv.tuner.IFilter;
import android.hardware.tv.tuner.IFilterCallback;
import android.hardware.tv.tuner.ITimeFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDemux extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$tv$tuner$IDemux".replace('$', '.');
    public static final String HASH = "b0d0067a930514438d7772c2e02069c7370f3620";
    public static final int VERSION = 3;

    void close() throws RemoteException;

    void connectCiCam(int i) throws RemoteException;

    void disconnectCiCam() throws RemoteException;

    int getAvSyncHwId(IFilter iFilter) throws RemoteException;

    long getAvSyncTime(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    IDvr openDvr(byte b, int i, IDvrCallback iDvrCallback) throws RemoteException;

    IFilter openFilter(DemuxFilterType demuxFilterType, int i, IFilterCallback iFilterCallback) throws RemoteException;

    ITimeFilter openTimeFilter() throws RemoteException;

    void setFrontendDataSource(int i) throws RemoteException;

    public static class Default implements IDemux {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public void close() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDemux
        public void connectCiCam(int i) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDemux
        public void disconnectCiCam() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDemux
        public int getAvSyncHwId(IFilter iFilter) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public long getAvSyncTime(int i) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public IDvr openDvr(byte b, int i, IDvrCallback iDvrCallback) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public IFilter openFilter(DemuxFilterType demuxFilterType, int i, IFilterCallback iFilterCallback) throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public ITimeFilter openTimeFilter() throws RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public void setFrontendDataSource(int i) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDemux
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IDemux {
        static final int TRANSACTION_close = 6;
        static final int TRANSACTION_connectCiCam = 8;
        static final int TRANSACTION_disconnectCiCam = 9;
        static final int TRANSACTION_getAvSyncHwId = 4;
        static final int TRANSACTION_getAvSyncTime = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_openDvr = 7;
        static final int TRANSACTION_openFilter = 2;
        static final int TRANSACTION_openTimeFilter = 3;
        static final int TRANSACTION_setFrontendDataSource = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IDemux asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDemux)) {
                return (IDemux) queryLocalInterface;
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
                    setFrontendDataSource(readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    DemuxFilterType demuxFilterType = (DemuxFilterType) parcel.readTypedObject(DemuxFilterType.CREATOR);
                    int readInt2 = parcel.readInt();
                    IFilterCallback asInterface = IFilterCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    IFilter openFilter = openFilter(demuxFilterType, readInt2, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openFilter);
                    return true;
                case 3:
                    ITimeFilter openTimeFilter = openTimeFilter();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openTimeFilter);
                    return true;
                case 4:
                    IFilter asInterface2 = IFilter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int avSyncHwId = getAvSyncHwId(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(avSyncHwId);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long avSyncTime = getAvSyncTime(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeLong(avSyncTime);
                    return true;
                case 6:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    byte readByte = parcel.readByte();
                    int readInt4 = parcel.readInt();
                    IDvrCallback asInterface3 = IDvrCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    IDvr openDvr = openDvr(readByte, readInt4, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openDvr);
                    return true;
                case 8:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    connectCiCam(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    disconnectCiCam();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDemux {
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

            @Override // android.hardware.tv.tuner.IDemux
            public void setFrontendDataSource(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setFrontendDataSource is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public IFilter openFilter(DemuxFilterType demuxFilterType, int i, IFilterCallback iFilterCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(demuxFilterType, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iFilterCallback);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openFilter is unimplemented.");
                    }
                    obtain2.readException();
                    return IFilter.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public ITimeFilter openTimeFilter() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openTimeFilter is unimplemented.");
                    }
                    obtain2.readException();
                    return ITimeFilter.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public int getAvSyncHwId(IFilter iFilter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iFilter);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getAvSyncHwId is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public long getAvSyncTime(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getAvSyncTime is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public IDvr openDvr(byte b, int i, IDvrCallback iDvrCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDvrCallback);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openDvr is unimplemented.");
                    }
                    obtain2.readException();
                    return IDvr.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public void connectCiCam(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method connectCiCam is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public void disconnectCiCam() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method disconnectCiCam is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
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

            @Override // android.hardware.tv.tuner.IDemux
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
