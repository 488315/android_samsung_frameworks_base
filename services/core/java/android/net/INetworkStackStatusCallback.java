package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INetworkStackStatusCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android$net$INetworkStackStatusCallback".replace('$', '.');
    public static final String HASH = "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
    public static final int VERSION = 21;

    public class Default implements INetworkStackStatusCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.INetworkStackStatusCallback
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.net.INetworkStackStatusCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.INetworkStackStatusCallback
        public void onStatusAvailable(int i) throws RemoteException {}
    }

    public abstract class Stub extends Binder implements INetworkStackStatusCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onStatusAvailable = 1;

        public final class Proxy implements INetworkStackStatusCallback {
            public String mCachedHash;
            public int mCachedVersion;
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.net.INetworkStackStatusCallback
            public final synchronized String getInterfaceHash() {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        Parcel obtain = Parcel.obtain();
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(INetworkStackStatusCallback.DESCRIPTOR);
                            this.mRemote.transact(
                                    Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
                } catch (Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }

            @Override // android.net.INetworkStackStatusCallback
            public final int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(INetworkStackStatusCallback.DESCRIPTOR);
                        this.mRemote.transact(
                                Stub.TRANSACTION_getInterfaceVersion, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.net.INetworkStackStatusCallback
            public final void onStatusAvailable(int i) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkStackStatusCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onStatusAvailable is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, INetworkStackStatusCallback.DESCRIPTOR);
        }

        public static INetworkStackStatusCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(INetworkStackStatusCallback.DESCRIPTOR);
            if (queryLocalInterface != null
                    && (queryLocalInterface instanceof INetworkStackStatusCallback)) {
                return (INetworkStackStatusCallback) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mCachedVersion = -1;
            proxy.mCachedHash = "-1";
            proxy.mRemote = iBinder;
            return proxy;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            String str = INetworkStackStatusCallback.DESCRIPTOR;
            if (i >= 1 && i <= TRANSACTION_getInterfaceVersion) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == TRANSACTION_getInterfaceVersion) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onStatusAvailable(parcel.readInt());
            return true;
        }
    }

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void onStatusAvailable(int i) throws RemoteException;
}
