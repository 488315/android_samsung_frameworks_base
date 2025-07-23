package android.hardware.cas;

import android.hardware.cas.ICas;
import android.hardware.cas.ICasListener;
import android.hardware.cas.IDescrambler;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IMediaCasService extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$cas$IMediaCasService".replace('$', '.');
    public static final String HASH = "bc51d8d70a55ec4723d3f73d0acf7003306bf69f";
    public static final int VERSION = 1;

    IDescrambler createDescrambler(int i) throws RemoteException;

    ICas createPlugin(int i, ICasListener iCasListener) throws RemoteException;

    AidlCasPluginDescriptor[] enumeratePlugins() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    boolean isDescramblerSupported(int i) throws RemoteException;

    boolean isSystemIdSupported(int i) throws RemoteException;

    public static class Default implements IMediaCasService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.cas.IMediaCasService
        public IDescrambler createDescrambler(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.cas.IMediaCasService
        public ICas createPlugin(int i, ICasListener iCasListener) throws RemoteException {
            return null;
        }

        @Override // android.hardware.cas.IMediaCasService
        public AidlCasPluginDescriptor[] enumeratePlugins() throws RemoteException {
            return null;
        }

        @Override // android.hardware.cas.IMediaCasService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.cas.IMediaCasService
        public boolean isDescramblerSupported(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.cas.IMediaCasService
        public boolean isSystemIdSupported(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.cas.IMediaCasService
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IMediaCasService {
        static final int TRANSACTION_createDescrambler = 1;
        static final int TRANSACTION_createPlugin = 2;
        static final int TRANSACTION_enumeratePlugins = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_isDescramblerSupported = 4;
        static final int TRANSACTION_isSystemIdSupported = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaCasService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaCasService)) {
                return (IMediaCasService) queryLocalInterface;
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
                IDescrambler createDescrambler = createDescrambler(readInt);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(createDescrambler);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                ICasListener asInterface = ICasListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ICas createPlugin = createPlugin(readInt2, asInterface);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(createPlugin);
            } else if (i == 3) {
                AidlCasPluginDescriptor[] enumeratePlugins = enumeratePlugins();
                parcel2.writeNoException();
                parcel2.writeTypedArray(enumeratePlugins, 1);
            } else if (i == 4) {
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean isDescramblerSupported = isDescramblerSupported(readInt3);
                parcel2.writeNoException();
                parcel2.writeBoolean(isDescramblerSupported);
            } else if (i == 5) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean isSystemIdSupported = isSystemIdSupported(readInt4);
                parcel2.writeNoException();
                parcel2.writeBoolean(isSystemIdSupported);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMediaCasService {
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

            @Override // android.hardware.cas.IMediaCasService
            public IDescrambler createDescrambler(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method createDescrambler is unimplemented.");
                    }
                    obtain2.readException();
                    return IDescrambler.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public ICas createPlugin(int i, ICasListener iCasListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCasListener);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method createPlugin is unimplemented.");
                    }
                    obtain2.readException();
                    return ICas.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public AidlCasPluginDescriptor[] enumeratePlugins() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method enumeratePlugins is unimplemented.");
                    }
                    obtain2.readException();
                    return (AidlCasPluginDescriptor[]) obtain2.createTypedArray(AidlCasPluginDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public boolean isDescramblerSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isDescramblerSupported is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public boolean isSystemIdSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isSystemIdSupported is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
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

            @Override // android.hardware.cas.IMediaCasService
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
