package android.media.tv.interactive;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITvInteractiveAppManagerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppManagerCallback";

    public static class Default implements ITvInteractiveAppManagerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
        public void onInteractiveAppServiceAdded(String str) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
        public void onInteractiveAppServiceRemoved(String str) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
        public void onInteractiveAppServiceUpdated(String str) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
        public void onStateChanged(String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
        public void onTvInteractiveAppServiceInfoUpdated(TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) throws RemoteException {
        }
    }

    void onInteractiveAppServiceAdded(String str) throws RemoteException;

    void onInteractiveAppServiceRemoved(String str) throws RemoteException;

    void onInteractiveAppServiceUpdated(String str) throws RemoteException;

    void onStateChanged(String str, int i, int i2, int i3) throws RemoteException;

    void onTvInteractiveAppServiceInfoUpdated(TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInteractiveAppManagerCallback {
        static final int TRANSACTION_onInteractiveAppServiceAdded = 1;
        static final int TRANSACTION_onInteractiveAppServiceRemoved = 2;
        static final int TRANSACTION_onInteractiveAppServiceUpdated = 3;
        static final int TRANSACTION_onStateChanged = 5;
        static final int TRANSACTION_onTvInteractiveAppServiceInfoUpdated = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ITvInteractiveAppManagerCallback.DESCRIPTOR);
        }

        public static ITvInteractiveAppManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITvInteractiveAppManagerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvInteractiveAppManagerCallback)) {
                return (ITvInteractiveAppManagerCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onInteractiveAppServiceAdded";
            }
            if (i == 2) {
                return "onInteractiveAppServiceRemoved";
            }
            if (i == 3) {
                return "onInteractiveAppServiceUpdated";
            }
            if (i == 4) {
                return "onTvInteractiveAppServiceInfoUpdated";
            }
            if (i != 5) {
                return null;
            }
            return "onStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITvInteractiveAppManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITvInteractiveAppManagerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onInteractiveAppServiceAdded(string);
            } else if (i == 2) {
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onInteractiveAppServiceRemoved(string2);
            } else if (i == 3) {
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                onInteractiveAppServiceUpdated(string3);
            } else if (i == 4) {
                TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo = (TvInteractiveAppServiceInfo) parcel.readTypedObject(TvInteractiveAppServiceInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onTvInteractiveAppServiceInfoUpdated(tvInteractiveAppServiceInfo);
            } else if (i == 5) {
                String string4 = parcel.readString();
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStateChanged(string4, i3, i4, i5);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITvInteractiveAppManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvInteractiveAppManagerCallback.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceAdded(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManagerCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceRemoved(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManagerCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceUpdated(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManagerCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onTvInteractiveAppServiceInfoUpdated(TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManagerCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(tvInteractiveAppServiceInfo, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onStateChanged(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppManagerCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
