package android.hardware.tv.tuner;

import android.hardware.tv.tuner.IFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDescrambler extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$tv$tuner$IDescrambler".replace('$', '.');
    public static final String HASH = "b0d0067a930514438d7772c2e02069c7370f3620";
    public static final int VERSION = 3;

    void addPid(DemuxPid demuxPid, IFilter iFilter) throws RemoteException;

    void close() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void removePid(DemuxPid demuxPid, IFilter iFilter) throws RemoteException;

    void setDemuxSource(int i) throws RemoteException;

    void setKeyToken(byte[] bArr) throws RemoteException;

    public static class Default implements IDescrambler {
        @Override // android.hardware.tv.tuner.IDescrambler
        public void addPid(DemuxPid demuxPid, IFilter iFilter) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.tv.tuner.IDescrambler
        public void close() throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDescrambler
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IDescrambler
        public void removePid(DemuxPid demuxPid, IFilter iFilter) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDescrambler
        public void setDemuxSource(int i) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDescrambler
        public void setKeyToken(byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDescrambler
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IDescrambler {
        static final int TRANSACTION_addPid = 3;
        static final int TRANSACTION_close = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_removePid = 4;
        static final int TRANSACTION_setDemuxSource = 1;
        static final int TRANSACTION_setKeyToken = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IDescrambler asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDescrambler)) {
                return (IDescrambler) queryLocalInterface;
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
                parcel.enforceNoDataAvail();
                setDemuxSource(readInt);
                parcel2.writeNoException();
            } else if (i == 2) {
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                setKeyToken(createByteArray);
                parcel2.writeNoException();
            } else if (i == 3) {
                DemuxPid demuxPid = (DemuxPid) parcel.readTypedObject(DemuxPid.CREATOR);
                IFilter asInterface = IFilter.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addPid(demuxPid, asInterface);
                parcel2.writeNoException();
            } else if (i == 4) {
                DemuxPid demuxPid2 = (DemuxPid) parcel.readTypedObject(DemuxPid.CREATOR);
                IFilter asInterface2 = IFilter.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removePid(demuxPid2, asInterface2);
                parcel2.writeNoException();
            } else if (i == 5) {
                close();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDescrambler {
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

            @Override // android.hardware.tv.tuner.IDescrambler
            public void setDemuxSource(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setDemuxSource is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDescrambler
            public void setKeyToken(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setKeyToken is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDescrambler
            public void addPid(DemuxPid demuxPid, IFilter iFilter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(demuxPid, 0);
                    obtain.writeStrongInterface(iFilter);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method addPid is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDescrambler
            public void removePid(DemuxPid demuxPid, IFilter iFilter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(demuxPid, 0);
                    obtain.writeStrongInterface(iFilter);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method removePid is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDescrambler
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDescrambler
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

            @Override // android.hardware.tv.tuner.IDescrambler
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
