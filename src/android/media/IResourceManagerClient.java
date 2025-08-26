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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IResourceManagerClient.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IResourceManagerClient)) {
                return (IResourceManagerClient) iInterfaceQueryLocalInterface;
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
                boolean zReclaimResource = reclaimResource();
                parcel2.writeNoException();
                parcel2.writeBoolean(zReclaimResource);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerClient
            public String getName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerClient
            public int getCodecState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerClient
            public void startWatchingMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerClient
            public void stopWatchingMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
