package android.se.omapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.se.omapi.ISecureElementSession;

/* loaded from: classes3.dex */
public interface ISecureElementReader extends IInterface {
    public static final String HASH = "894069bcfe4f35ceb2088278ddf87c83adee8014";
    public static final int VERSION = 1;

    void closeSessions() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    boolean isSecureElementPresent() throws RemoteException;

    ISecureElementSession openSession() throws RemoteException;

    boolean reset() throws RemoteException;

    public static class Default implements ISecureElementReader {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.se.omapi.ISecureElementReader
        public void closeSessions() throws RemoteException {
        }

        @Override // android.se.omapi.ISecureElementReader
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.se.omapi.ISecureElementReader
        public boolean isSecureElementPresent() throws RemoteException {
            return false;
        }

        @Override // android.se.omapi.ISecureElementReader
        public ISecureElementSession openSession() throws RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementReader
        public boolean reset() throws RemoteException {
            return false;
        }

        @Override // android.se.omapi.ISecureElementReader
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISecureElementReader {
        public static final String DESCRIPTOR = "android$se$omapi$ISecureElementReader".replace('$', '.');
        static final int TRANSACTION_closeSessions = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_isSecureElementPresent = 1;
        static final int TRANSACTION_openSession = 2;
        static final int TRANSACTION_reset = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISecureElementReader asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISecureElementReader)) {
                return (ISecureElementReader) queryLocalInterface;
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
                boolean isSecureElementPresent = isSecureElementPresent();
                parcel2.writeNoException();
                parcel2.writeBoolean(isSecureElementPresent);
            } else if (i == 2) {
                ISecureElementSession openSession = openSession();
                parcel2.writeNoException();
                parcel2.writeStrongInterface(openSession);
            } else if (i == 3) {
                closeSessions();
                parcel2.writeNoException();
            } else if (i == 4) {
                boolean reset = reset();
                parcel2.writeNoException();
                parcel2.writeBoolean(reset);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISecureElementReader {
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

            @Override // android.se.omapi.ISecureElementReader
            public boolean isSecureElementPresent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isSecureElementPresent is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementReader
            public ISecureElementSession openSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method openSession is unimplemented.");
                    }
                    obtain2.readException();
                    return ISecureElementSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementReader
            public void closeSessions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method closeSessions is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementReader
            public boolean reset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method reset is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementReader
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

            @Override // android.se.omapi.ISecureElementReader
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
