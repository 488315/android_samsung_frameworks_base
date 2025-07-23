package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IResourceManagerClient extends IInterface {
    public static final String DESCRIPTOR = "android.media.IResourceManagerClient";

    public static class Default implements IResourceManagerClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IResourceManagerClient
        public int getCodecState() throws RemoteException {
            return 0;
        }

        @Override // android.media.IResourceManagerClient
        public String getName() throws RemoteException {
            return null;
        }

        @Override // android.media.IResourceManagerClient
        public boolean reclaimResource() throws RemoteException {
            return false;
        }

        @Override // android.media.IResourceManagerClient
        public void startWatchingMode() throws RemoteException {
        }

        @Override // android.media.IResourceManagerClient
        public void stopWatchingMode() throws RemoteException {
        }
    }

    int getCodecState() throws RemoteException;

    String getName() throws RemoteException;

    boolean reclaimResource() throws RemoteException;

    void startWatchingMode() throws RemoteException;

    void stopWatchingMode() throws RemoteException;

    public static abstract class Stub extends Binder implements IResourceManagerClient {
        static final int TRANSACTION_getCodecState = 3;
        static final int TRANSACTION_getName = 2;
        static final int TRANSACTION_reclaimResource = 1;
        static final int TRANSACTION_startWatchingMode = 4;
        static final int TRANSACTION_stopWatchingMode = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IResourceManagerClient.DESCRIPTOR);
        }

        public static IResourceManagerClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IResourceManagerClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IResourceManagerClient)) {
                return (IResourceManagerClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "reclaimResource";
            }
            if (i == 2) {
                return "getName";
            }
            if (i == 3) {
                return "getCodecState";
            }
            if (i == 4) {
                return "startWatchingMode";
            }
            if (i != 5) {
                return null;
            }
            return "stopWatchingMode";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IResourceManagerClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IResourceManagerClient.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean reclaimResource = reclaimResource();
                parcel2.writeNoException();
                parcel2.writeBoolean(reclaimResource);
            } else if (i == 2) {
                String name = getName();
                parcel2.writeNoException();
                parcel2.writeString(name);
            } else if (i == 3) {
                int codecState = getCodecState();
                parcel2.writeNoException();
                parcel2.writeInt(codecState);
            } else if (i == 4) {
                startWatchingMode();
            } else if (i == 5) {
                stopWatchingMode();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IResourceManagerClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResourceManagerClient.DESCRIPTOR;
            }

            @Override // android.media.IResourceManagerClient
            public boolean reclaimResource() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerClient
            public String getName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerClient
            public int getCodecState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerClient
            public void startWatchingMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerClient
            public void stopWatchingMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
