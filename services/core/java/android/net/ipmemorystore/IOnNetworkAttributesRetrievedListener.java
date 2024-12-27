package android.net.ipmemorystore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface IOnNetworkAttributesRetrievedListener extends IInterface {
    public static final String DESCRIPTOR =
            "android$net$ipmemorystore$IOnNetworkAttributesRetrievedListener".replace('$', '.');
    public static final String HASH = "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
    public static final int VERSION = 10;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Default implements IOnNetworkAttributesRetrievedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener
        public void onNetworkAttributesRetrieved(
                StatusParcelable statusParcelable,
                String str,
                NetworkAttributesParcelable networkAttributesParcelable)
                throws RemoteException {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements IOnNetworkAttributesRetrievedListener {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onNetworkAttributesRetrieved = 1;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements IOnNetworkAttributesRetrievedListener {
            public String mCachedHash;
            public int mCachedVersion;
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener
            public final synchronized String getInterfaceHash() {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        Parcel obtain = Parcel.obtain();
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(
                                    IOnNetworkAttributesRetrievedListener.DESCRIPTOR);
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

            @Override // android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener
            public final int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(
                                IOnNetworkAttributesRetrievedListener.DESCRIPTOR);
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

            @Override // android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener
            public final void onNetworkAttributesRetrieved(
                    StatusParcelable statusParcelable,
                    String str,
                    NetworkAttributesParcelable networkAttributesParcelable) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOnNetworkAttributesRetrievedListener.DESCRIPTOR);
                    obtain.writeTypedObject(statusParcelable, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(networkAttributesParcelable, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method onNetworkAttributesRetrieved is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IOnNetworkAttributesRetrievedListener.DESCRIPTOR);
        }

        public static IOnNetworkAttributesRetrievedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IOnNetworkAttributesRetrievedListener.DESCRIPTOR);
            if (queryLocalInterface != null
                    && (queryLocalInterface instanceof IOnNetworkAttributesRetrievedListener)) {
                return (IOnNetworkAttributesRetrievedListener) queryLocalInterface;
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
            String str = IOnNetworkAttributesRetrievedListener.DESCRIPTOR;
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
            onNetworkAttributesRetrieved(
                    (StatusParcelable) parcel.readTypedObject(StatusParcelable.CREATOR),
                    parcel.readString(),
                    (NetworkAttributesParcelable)
                            parcel.readTypedObject(NetworkAttributesParcelable.CREATOR));
            return true;
        }
    }

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void onNetworkAttributesRetrieved(
            StatusParcelable statusParcelable,
            String str,
            NetworkAttributesParcelable networkAttributesParcelable)
            throws RemoteException;
}
