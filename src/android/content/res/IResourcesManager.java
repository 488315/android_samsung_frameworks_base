package android.content.res;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IResourcesManager extends IInterface {
    public static final String DESCRIPTOR = "android.content.res.IResourcesManager";

    public static class Default implements IResourcesManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.res.IResourcesManager
        public boolean dumpResources(String str, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
            return false;
        }
    }

    boolean dumpResources(String str, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IResourcesManager {
        static final int TRANSACTION_dumpResources = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IResourcesManager.DESCRIPTOR);
        }

        public static IResourcesManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IResourcesManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IResourcesManager)) {
                return (IResourcesManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "dumpResources";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IResourcesManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IResourcesManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                boolean dumpResources = dumpResources(readString, parcelFileDescriptor, remoteCallback);
                parcel2.writeNoException();
                parcel2.writeBoolean(dumpResources);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IResourcesManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResourcesManager.DESCRIPTOR;
            }

            @Override // android.content.res.IResourcesManager
            public boolean dumpResources(String str, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourcesManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
