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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDescrambler)) {
                return (IDescrambler) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setDemuxSource(i3);
                parcel2.writeNoException();
            } else if (i == 2) {
                byte[] bArrCreateByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                setKeyToken(bArrCreateByteArray);
                parcel2.writeNoException();
            } else if (i == 3) {
                DemuxPid demuxPid = (DemuxPid) parcel.readTypedObject(DemuxPid.CREATOR);
                IFilter iFilterAsInterface = IFilter.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addPid(demuxPid, iFilterAsInterface);
                parcel2.writeNoException();
            } else if (i == 4) {
                DemuxPid demuxPid2 = (DemuxPid) parcel.readTypedObject(DemuxPid.CREATOR);
                IFilter iFilterAsInterface2 = IFilter.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removePid(demuxPid2, iFilterAsInterface2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setDemuxSource is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDescrambler
            public void setKeyToken(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setKeyToken is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDescrambler
            public void addPid(DemuxPid demuxPid, IFilter iFilter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(demuxPid, 0);
                    parcelObtain.writeStrongInterface(iFilter);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method addPid is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDescrambler
            public void removePid(DemuxPid demuxPid, IFilter iFilter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(demuxPid, 0);
                    parcelObtain.writeStrongInterface(iFilter);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method removePid is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDescrambler
            public void close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDescrambler
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

            @Override // android.hardware.tv.tuner.IDescrambler
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
