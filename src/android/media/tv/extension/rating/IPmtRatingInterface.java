package android.media.tv.extension.rating;

import android.media.tv.extension.rating.IPmtRatingListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IPmtRatingInterface extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.rating.IPmtRatingInterface";

    public static class Default implements IPmtRatingInterface {
        @Override // android.media.tv.extension.rating.IPmtRatingInterface
        public void addPmtRatingListener(String str, IPmtRatingListener iPmtRatingListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.rating.IPmtRatingInterface
        public String getPmtRating(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.rating.IPmtRatingInterface
        public void removePmtRatingListener(IPmtRatingListener iPmtRatingListener) throws RemoteException {
        }
    }

    void addPmtRatingListener(String str, IPmtRatingListener iPmtRatingListener) throws RemoteException;

    String getPmtRating(String str) throws RemoteException;

    void removePmtRatingListener(IPmtRatingListener iPmtRatingListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IPmtRatingInterface {
        static final int TRANSACTION_addPmtRatingListener = 2;
        static final int TRANSACTION_getPmtRating = 1;
        static final int TRANSACTION_removePmtRatingListener = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.rating.IPmtRatingInterface");
        }

        public static IPmtRatingInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.rating.IPmtRatingInterface");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPmtRatingInterface)) {
                return (IPmtRatingInterface) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getPmtRating";
            }
            if (i == 2) {
                return "addPmtRatingListener";
            }
            if (i != 3) {
                return null;
            }
            return "removePmtRatingListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.rating.IPmtRatingInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.rating.IPmtRatingInterface");
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                String pmtRating = getPmtRating(string);
                parcel2.writeNoException();
                parcel2.writeString(pmtRating);
            } else if (i == 2) {
                String string2 = parcel.readString();
                IPmtRatingListener iPmtRatingListenerAsInterface = IPmtRatingListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addPmtRatingListener(string2, iPmtRatingListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 3) {
                IPmtRatingListener iPmtRatingListenerAsInterface2 = IPmtRatingListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removePmtRatingListener(iPmtRatingListenerAsInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPmtRatingInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.rating.IPmtRatingInterface";
            }

            @Override // android.media.tv.extension.rating.IPmtRatingInterface
            public String getPmtRating(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.rating.IPmtRatingInterface");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.rating.IPmtRatingInterface
            public void addPmtRatingListener(String str, IPmtRatingListener iPmtRatingListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.rating.IPmtRatingInterface");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iPmtRatingListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.rating.IPmtRatingInterface
            public void removePmtRatingListener(IPmtRatingListener iPmtRatingListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.rating.IPmtRatingInterface");
                    parcelObtain.writeStrongInterface(iPmtRatingListener);
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
