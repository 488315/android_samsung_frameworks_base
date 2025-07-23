package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IParcelFileDescriptorRetriever extends IInterface {
    public static final String DESCRIPTOR = "android.app.IParcelFileDescriptorRetriever";

    public static class Default implements IParcelFileDescriptorRetriever {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IParcelFileDescriptorRetriever
        public ParcelFileDescriptor getPfd() throws RemoteException {
            return null;
        }
    }

    ParcelFileDescriptor getPfd() throws RemoteException;

    public static abstract class Stub extends Binder implements IParcelFileDescriptorRetriever {
        static final int TRANSACTION_getPfd = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IParcelFileDescriptorRetriever.DESCRIPTOR);
        }

        public static IParcelFileDescriptorRetriever asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IParcelFileDescriptorRetriever.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IParcelFileDescriptorRetriever)) {
                return (IParcelFileDescriptorRetriever) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getPfd";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IParcelFileDescriptorRetriever.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IParcelFileDescriptorRetriever.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ParcelFileDescriptor pfd = getPfd();
                parcel2.writeNoException();
                parcel2.writeTypedObject(pfd, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IParcelFileDescriptorRetriever {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IParcelFileDescriptorRetriever.DESCRIPTOR;
            }

            @Override // android.app.IParcelFileDescriptorRetriever
            public ParcelFileDescriptor getPfd() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IParcelFileDescriptorRetriever.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
