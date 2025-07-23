package android.hardware.cas;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDescrambler extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$cas$IDescrambler".replace('$', '.');
    public static final String HASH = "bc51d8d70a55ec4723d3f73d0acf7003306bf69f";
    public static final int VERSION = 1;

    int descramble(int i, SubSample[] subSampleArr, SharedBuffer sharedBuffer, long j, DestinationBuffer destinationBuffer, long j2) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void release() throws RemoteException;

    boolean requiresSecureDecoderComponent(String str) throws RemoteException;

    void setMediaCasSession(byte[] bArr) throws RemoteException;

    public static class Default implements IDescrambler {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.cas.IDescrambler
        public int descramble(int i, SubSample[] subSampleArr, SharedBuffer sharedBuffer, long j, DestinationBuffer destinationBuffer, long j2) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.cas.IDescrambler
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.cas.IDescrambler
        public void release() throws RemoteException {
        }

        @Override // android.hardware.cas.IDescrambler
        public boolean requiresSecureDecoderComponent(String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.cas.IDescrambler
        public void setMediaCasSession(byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.cas.IDescrambler
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IDescrambler {
        static final int TRANSACTION_descramble = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_release = 2;
        static final int TRANSACTION_requiresSecureDecoderComponent = 3;
        static final int TRANSACTION_setMediaCasSession = 4;

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
                SubSample[] subSampleArr = (SubSample[]) parcel.createTypedArray(SubSample.CREATOR);
                SharedBuffer sharedBuffer = (SharedBuffer) parcel.readTypedObject(SharedBuffer.CREATOR);
                long readLong = parcel.readLong();
                DestinationBuffer destinationBuffer = (DestinationBuffer) parcel.readTypedObject(DestinationBuffer.CREATOR);
                long readLong2 = parcel.readLong();
                parcel.enforceNoDataAvail();
                int descramble = descramble(readInt, subSampleArr, sharedBuffer, readLong, destinationBuffer, readLong2);
                parcel2.writeNoException();
                parcel2.writeInt(descramble);
            } else if (i == 2) {
                release();
                parcel2.writeNoException();
            } else if (i == 3) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean requiresSecureDecoderComponent = requiresSecureDecoderComponent(readString);
                parcel2.writeNoException();
                parcel2.writeBoolean(requiresSecureDecoderComponent);
            } else if (i == 4) {
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                setMediaCasSession(createByteArray);
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

            @Override // android.hardware.cas.IDescrambler
            public int descramble(int i, SubSample[] subSampleArr, SharedBuffer sharedBuffer, long j, DestinationBuffer destinationBuffer, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(subSampleArr, 0);
                    obtain.writeTypedObject(sharedBuffer, 0);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(destinationBuffer, 0);
                    obtain.writeLong(j2);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method descramble is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IDescrambler
            public void release() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method release is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IDescrambler
            public boolean requiresSecureDecoderComponent(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method requiresSecureDecoderComponent is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IDescrambler
            public void setMediaCasSession(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setMediaCasSession is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IDescrambler
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

            @Override // android.hardware.cas.IDescrambler
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
