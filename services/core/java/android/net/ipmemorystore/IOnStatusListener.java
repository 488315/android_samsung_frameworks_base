package android.net.ipmemorystore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IOnStatusListener extends IInterface {
    public static final String DESCRIPTOR =
            "android$net$ipmemorystore$IOnStatusListener".replace('$', '.');
    public static final String HASH = "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
    public static final int VERSION = 10;

    public class Default implements IOnStatusListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.ipmemorystore.IOnStatusListener
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.net.ipmemorystore.IOnStatusListener
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.ipmemorystore.IOnStatusListener
        public void onComplete(StatusParcelable statusParcelable) throws RemoteException {}
    }

    public abstract class Stub extends Binder implements IOnStatusListener {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onComplete = 1;

        public final class Proxy implements IOnStatusListener {
            public String mCachedHash;
            public int mCachedVersion;
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.net.ipmemorystore.IOnStatusListener
            public final synchronized String getInterfaceHash() {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        Parcel obtain = Parcel.obtain();
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(IOnStatusListener.DESCRIPTOR);
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

            @Override // android.net.ipmemorystore.IOnStatusListener
            public final int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(IOnStatusListener.DESCRIPTOR);
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

            @Override // android.net.ipmemorystore.IOnStatusListener
            public final void onComplete(StatusParcelable statusParcelable) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOnStatusListener.DESCRIPTOR);
                    obtain.writeTypedObject(statusParcelable, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onComplete is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IOnStatusListener.DESCRIPTOR);
        }

        public static IOnStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IOnStatusListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOnStatusListener)) {
                return (IOnStatusListener) queryLocalInterface;
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
            String str = IOnStatusListener.DESCRIPTOR;
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
            onComplete((StatusParcelable) parcel.readTypedObject(StatusParcelable.CREATOR));
            return true;
        }
    }

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void onComplete(StatusParcelable statusParcelable) throws RemoteException;
}
