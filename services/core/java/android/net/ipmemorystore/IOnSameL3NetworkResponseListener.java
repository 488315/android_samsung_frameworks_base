package android.net.ipmemorystore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IOnSameL3NetworkResponseListener extends IInterface {
    public static final String DESCRIPTOR =
            "android$net$ipmemorystore$IOnSameL3NetworkResponseListener".replace('$', '.');
    public static final String HASH = "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
    public static final int VERSION = 10;

    public class Default implements IOnSameL3NetworkResponseListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.ipmemorystore.IOnSameL3NetworkResponseListener
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.net.ipmemorystore.IOnSameL3NetworkResponseListener
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.ipmemorystore.IOnSameL3NetworkResponseListener
        public void onSameL3NetworkResponse(
                StatusParcelable statusParcelable,
                SameL3NetworkResponseParcelable sameL3NetworkResponseParcelable)
                throws RemoteException {}
    }

    public abstract class Stub extends Binder implements IOnSameL3NetworkResponseListener {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onSameL3NetworkResponse = 1;

        public final class Proxy implements IOnSameL3NetworkResponseListener {
            public String mCachedHash;
            public int mCachedVersion;
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.net.ipmemorystore.IOnSameL3NetworkResponseListener
            public final synchronized String getInterfaceHash() {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        Parcel obtain = Parcel.obtain();
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(IOnSameL3NetworkResponseListener.DESCRIPTOR);
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

            @Override // android.net.ipmemorystore.IOnSameL3NetworkResponseListener
            public final int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(IOnSameL3NetworkResponseListener.DESCRIPTOR);
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

            @Override // android.net.ipmemorystore.IOnSameL3NetworkResponseListener
            public final void onSameL3NetworkResponse(
                    StatusParcelable statusParcelable,
                    SameL3NetworkResponseParcelable sameL3NetworkResponseParcelable) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOnSameL3NetworkResponseListener.DESCRIPTOR);
                    obtain.writeTypedObject(statusParcelable, 0);
                    obtain.writeTypedObject(sameL3NetworkResponseParcelable, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method onSameL3NetworkResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IOnSameL3NetworkResponseListener.DESCRIPTOR);
        }

        public static IOnSameL3NetworkResponseListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IOnSameL3NetworkResponseListener.DESCRIPTOR);
            if (queryLocalInterface != null
                    && (queryLocalInterface instanceof IOnSameL3NetworkResponseListener)) {
                return (IOnSameL3NetworkResponseListener) queryLocalInterface;
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
            String str = IOnSameL3NetworkResponseListener.DESCRIPTOR;
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
            onSameL3NetworkResponse(
                    (StatusParcelable) parcel.readTypedObject(StatusParcelable.CREATOR),
                    (SameL3NetworkResponseParcelable)
                            parcel.readTypedObject(SameL3NetworkResponseParcelable.CREATOR));
            return true;
        }
    }

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void onSameL3NetworkResponse(
            StatusParcelable statusParcelable,
            SameL3NetworkResponseParcelable sameL3NetworkResponseParcelable)
            throws RemoteException;
}
