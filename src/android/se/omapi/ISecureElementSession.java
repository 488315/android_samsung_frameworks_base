package android.se.omapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.se.omapi.ISecureElementChannel;
import android.se.omapi.ISecureElementListener;

/* loaded from: classes3.dex */
public interface ISecureElementSession extends IInterface {
    public static final String HASH = "894069bcfe4f35ceb2088278ddf87c83adee8014";
    public static final int VERSION = 1;

    void close() throws RemoteException;

    void closeChannels() throws RemoteException;

    byte[] getAtr() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    boolean isClosed() throws RemoteException;

    ISecureElementChannel openBasicChannel(byte[] bArr, byte b, ISecureElementListener iSecureElementListener) throws RemoteException;

    ISecureElementChannel openLogicalChannel(byte[] bArr, byte b, ISecureElementListener iSecureElementListener) throws RemoteException;

    public static class Default implements ISecureElementSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.se.omapi.ISecureElementSession
        public void close() throws RemoteException {
        }

        @Override // android.se.omapi.ISecureElementSession
        public void closeChannels() throws RemoteException {
        }

        @Override // android.se.omapi.ISecureElementSession
        public byte[] getAtr() throws RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementSession
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.se.omapi.ISecureElementSession
        public boolean isClosed() throws RemoteException {
            return false;
        }

        @Override // android.se.omapi.ISecureElementSession
        public ISecureElementChannel openBasicChannel(byte[] bArr, byte b, ISecureElementListener iSecureElementListener) throws RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementSession
        public ISecureElementChannel openLogicalChannel(byte[] bArr, byte b, ISecureElementListener iSecureElementListener) throws RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementSession
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISecureElementSession {
        public static final String DESCRIPTOR = "android$se$omapi$ISecureElementSession".replace('$', '.');
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_closeChannels = 3;
        static final int TRANSACTION_getAtr = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_isClosed = 4;
        static final int TRANSACTION_openBasicChannel = 5;
        static final int TRANSACTION_openLogicalChannel = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISecureElementSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISecureElementSession)) {
                return (ISecureElementSession) queryLocalInterface;
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
                    byte[] atr = getAtr();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(atr);
                    return true;
                case 2:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    closeChannels();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean isClosed = isClosed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClosed);
                    return true;
                case 5:
                    byte[] createByteArray = parcel.createByteArray();
                    byte readByte = parcel.readByte();
                    ISecureElementListener asInterface = ISecureElementListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    ISecureElementChannel openBasicChannel = openBasicChannel(createByteArray, readByte, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openBasicChannel);
                    return true;
                case 6:
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte readByte2 = parcel.readByte();
                    ISecureElementListener asInterface2 = ISecureElementListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    ISecureElementChannel openLogicalChannel = openLogicalChannel(createByteArray2, readByte2, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openLogicalChannel);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISecureElementSession {
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
                return Stub.DESCRIPTOR;
            }

            @Override // android.se.omapi.ISecureElementSession
            public byte[] getAtr() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getAtr is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementSession
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementSession
            public void closeChannels() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method closeChannels is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementSession
            public boolean isClosed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isClosed is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementSession
            public ISecureElementChannel openBasicChannel(byte[] bArr, byte b, ISecureElementListener iSecureElementListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByte(b);
                    obtain.writeStrongInterface(iSecureElementListener);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openBasicChannel is unimplemented.");
                    }
                    obtain2.readException();
                    return ISecureElementChannel.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementSession
            public ISecureElementChannel openLogicalChannel(byte[] bArr, byte b, ISecureElementListener iSecureElementListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByte(b);
                    obtain.writeStrongInterface(iSecureElementListener);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openLogicalChannel is unimplemented.");
                    }
                    obtain2.readException();
                    return ISecureElementChannel.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementSession
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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

            @Override // android.se.omapi.ISecureElementSession
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
