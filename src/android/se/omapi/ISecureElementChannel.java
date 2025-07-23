package android.se.omapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISecureElementChannel extends IInterface {
    public static final String HASH = "894069bcfe4f35ceb2088278ddf87c83adee8014";
    public static final int VERSION = 1;

    void close() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    byte[] getSelectResponse() throws RemoteException;

    boolean isBasicChannel() throws RemoteException;

    boolean isClosed() throws RemoteException;

    boolean selectNext() throws RemoteException;

    byte[] transmit(byte[] bArr) throws RemoteException;

    public static class Default implements ISecureElementChannel {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.se.omapi.ISecureElementChannel
        public void close() throws RemoteException {
        }

        @Override // android.se.omapi.ISecureElementChannel
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.se.omapi.ISecureElementChannel
        public byte[] getSelectResponse() throws RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementChannel
        public boolean isBasicChannel() throws RemoteException {
            return false;
        }

        @Override // android.se.omapi.ISecureElementChannel
        public boolean isClosed() throws RemoteException {
            return false;
        }

        @Override // android.se.omapi.ISecureElementChannel
        public boolean selectNext() throws RemoteException {
            return false;
        }

        @Override // android.se.omapi.ISecureElementChannel
        public byte[] transmit(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementChannel
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISecureElementChannel {
        public static final String DESCRIPTOR = "android$se$omapi$ISecureElementChannel".replace('$', '.');
        static final int TRANSACTION_close = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSelectResponse = 4;
        static final int TRANSACTION_isBasicChannel = 3;
        static final int TRANSACTION_isClosed = 2;
        static final int TRANSACTION_selectNext = 6;
        static final int TRANSACTION_transmit = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISecureElementChannel asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISecureElementChannel)) {
                return (ISecureElementChannel) queryLocalInterface;
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
                    close();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean isClosed = isClosed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClosed);
                    return true;
                case 3:
                    boolean isBasicChannel = isBasicChannel();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBasicChannel);
                    return true;
                case 4:
                    byte[] selectResponse = getSelectResponse();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(selectResponse);
                    return true;
                case 5:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] transmit = transmit(createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(transmit);
                    return true;
                case 6:
                    boolean selectNext = selectNext();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(selectNext);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISecureElementChannel {
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

            @Override // android.se.omapi.ISecureElementChannel
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementChannel
            public boolean isClosed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isClosed is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementChannel
            public boolean isBasicChannel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isBasicChannel is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementChannel
            public byte[] getSelectResponse() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSelectResponse is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementChannel
            public byte[] transmit(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method transmit is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementChannel
            public boolean selectNext() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method selectNext is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementChannel
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

            @Override // android.se.omapi.ISecureElementChannel
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
