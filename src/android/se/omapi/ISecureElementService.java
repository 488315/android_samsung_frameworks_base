package android.se.omapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.se.omapi.ISecureElementReader;

/* loaded from: classes3.dex */
public interface ISecureElementService extends IInterface {
    public static final String HASH = "894069bcfe4f35ceb2088278ddf87c83adee8014";
    public static final int VERSION = 1;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    ISecureElementReader getReader(String str) throws RemoteException;

    String[] getReaders() throws RemoteException;

    boolean[] isNfcEventAllowed(String str, byte[] bArr, String[] strArr, int i) throws RemoteException;

    public static class Default implements ISecureElementService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.se.omapi.ISecureElementService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.se.omapi.ISecureElementService
        public ISecureElementReader getReader(String str) throws RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementService
        public String[] getReaders() throws RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementService
        public boolean[] isNfcEventAllowed(String str, byte[] bArr, String[] strArr, int i) throws RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementService
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISecureElementService {
        public static final String DESCRIPTOR = "android$se$omapi$ISecureElementService".replace('$', '.');
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getReader = 2;
        static final int TRANSACTION_getReaders = 1;
        static final int TRANSACTION_isNfcEventAllowed = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISecureElementService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISecureElementService)) {
                return (ISecureElementService) queryLocalInterface;
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
                String[] readers = getReaders();
                parcel2.writeNoException();
                parcel2.writeStringArray(readers);
            } else if (i == 2) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                ISecureElementReader reader = getReader(readString);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(reader);
            } else if (i == 3) {
                String readString2 = parcel.readString();
                byte[] createByteArray = parcel.createByteArray();
                String[] createStringArray = parcel.createStringArray();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean[] isNfcEventAllowed = isNfcEventAllowed(readString2, createByteArray, createStringArray, readInt);
                parcel2.writeNoException();
                parcel2.writeBooleanArray(isNfcEventAllowed);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISecureElementService {
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

            @Override // android.se.omapi.ISecureElementService
            public String[] getReaders() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getReaders is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementService
            public ISecureElementReader getReader(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getReader is unimplemented.");
                    }
                    obtain2.readException();
                    return ISecureElementReader.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementService
            public boolean[] isNfcEventAllowed(String str, byte[] bArr, String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isNfcEventAllowed is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createBooleanArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementService
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

            @Override // android.se.omapi.ISecureElementService
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
