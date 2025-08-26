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
                SubSample[] subSampleArr = (SubSample[]) parcel.createTypedArray(SubSample.CREATOR);
                SharedBuffer sharedBuffer = (SharedBuffer) parcel.readTypedObject(SharedBuffer.CREATOR);
                long j = parcel.readLong();
                DestinationBuffer destinationBuffer = (DestinationBuffer) parcel.readTypedObject(DestinationBuffer.CREATOR);
                long j2 = parcel.readLong();
                parcel.enforceNoDataAvail();
                int iDescramble = descramble(i3, subSampleArr, sharedBuffer, j, destinationBuffer, j2);
                parcel2.writeNoException();
                parcel2.writeInt(iDescramble);
            } else if (i == 2) {
                release();
                parcel2.writeNoException();
            } else if (i == 3) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean zRequiresSecureDecoderComponent = requiresSecureDecoderComponent(string);
                parcel2.writeNoException();
                parcel2.writeBoolean(zRequiresSecureDecoderComponent);
            } else if (i == 4) {
                byte[] bArrCreateByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                setMediaCasSession(bArrCreateByteArray);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(subSampleArr, 0);
                    parcelObtain.writeTypedObject(sharedBuffer, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(destinationBuffer, 0);
                    parcelObtain.writeLong(j2);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method descramble is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.IDescrambler
            public void release() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method release is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.IDescrambler
            public boolean requiresSecureDecoderComponent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method requiresSecureDecoderComponent is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.IDescrambler
            public void setMediaCasSession(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setMediaCasSession is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.IDescrambler
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

            @Override // android.hardware.cas.IDescrambler
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
