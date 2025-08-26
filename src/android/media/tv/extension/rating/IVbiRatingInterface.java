package android.media.tv.extension.rating;

import android.media.tv.extension.rating.IVbiRatingListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IVbiRatingInterface extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.rating.IVbiRatingInterface";

    public static class Default implements IVbiRatingInterface {
        @Override // android.media.tv.extension.rating.IVbiRatingInterface
        public void addVbiRatingListener(String str, IVbiRatingListener iVbiRatingListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.rating.IVbiRatingInterface
        public String getVbiRating(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.rating.IVbiRatingInterface
        public void removeVbiRatingListener(IVbiRatingListener iVbiRatingListener) throws RemoteException {
        }
    }

    void addVbiRatingListener(String str, IVbiRatingListener iVbiRatingListener) throws RemoteException;

    String getVbiRating(String str) throws RemoteException;

    void removeVbiRatingListener(IVbiRatingListener iVbiRatingListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IVbiRatingInterface {
        static final int TRANSACTION_addVbiRatingListener = 2;
        static final int TRANSACTION_getVbiRating = 1;
        static final int TRANSACTION_removeVbiRatingListener = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.rating.IVbiRatingInterface");
        }

        public static IVbiRatingInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.rating.IVbiRatingInterface");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVbiRatingInterface)) {
                return (IVbiRatingInterface) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getVbiRating";
            }
            if (i == 2) {
                return "addVbiRatingListener";
            }
            if (i != 3) {
                return null;
            }
            return "removeVbiRatingListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.rating.IVbiRatingInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.rating.IVbiRatingInterface");
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                String vbiRating = getVbiRating(string);
                parcel2.writeNoException();
                parcel2.writeString(vbiRating);
            } else if (i == 2) {
                String string2 = parcel.readString();
                IVbiRatingListener iVbiRatingListenerAsInterface = IVbiRatingListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addVbiRatingListener(string2, iVbiRatingListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 3) {
                IVbiRatingListener iVbiRatingListenerAsInterface2 = IVbiRatingListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeVbiRatingListener(iVbiRatingListenerAsInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVbiRatingInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.rating.IVbiRatingInterface";
            }

            @Override // android.media.tv.extension.rating.IVbiRatingInterface
            public String getVbiRating(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.rating.IVbiRatingInterface");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.rating.IVbiRatingInterface
            public void addVbiRatingListener(String str, IVbiRatingListener iVbiRatingListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.rating.IVbiRatingInterface");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iVbiRatingListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.rating.IVbiRatingInterface
            public void removeVbiRatingListener(IVbiRatingListener iVbiRatingListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.rating.IVbiRatingInterface");
                    parcelObtain.writeStrongInterface(iVbiRatingListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
