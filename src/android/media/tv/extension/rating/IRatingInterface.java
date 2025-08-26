package android.media.tv.extension.rating;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IRatingInterface extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.rating.IRatingInterface";

    public static class Default implements IRatingInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.rating.IRatingInterface
        public Bundle getRRTRatingInfo() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.rating.IRatingInterface
        public boolean setRRTRatingInfo(Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.extension.rating.IRatingInterface
        public boolean setResetRrt5() throws RemoteException {
            return false;
        }
    }

    Bundle getRRTRatingInfo() throws RemoteException;

    boolean setRRTRatingInfo(Bundle bundle) throws RemoteException;

    boolean setResetRrt5() throws RemoteException;

    public static abstract class Stub extends Binder implements IRatingInterface {
        static final int TRANSACTION_getRRTRatingInfo = 1;
        static final int TRANSACTION_setRRTRatingInfo = 2;
        static final int TRANSACTION_setResetRrt5 = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.rating.IRatingInterface");
        }

        public static IRatingInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.rating.IRatingInterface");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRatingInterface)) {
                return (IRatingInterface) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getRRTRatingInfo";
            }
            if (i == 2) {
                return "setRRTRatingInfo";
            }
            if (i != 3) {
                return null;
            }
            return "setResetRrt5";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.rating.IRatingInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.rating.IRatingInterface");
                return true;
            }
            if (i == 1) {
                Bundle rRTRatingInfo = getRRTRatingInfo();
                parcel2.writeNoException();
                parcel2.writeTypedObject(rRTRatingInfo, 1);
            } else if (i == 2) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                boolean rRTRatingInfo2 = setRRTRatingInfo(bundle);
                parcel2.writeNoException();
                parcel2.writeBoolean(rRTRatingInfo2);
            } else if (i == 3) {
                boolean resetRrt5 = setResetRrt5();
                parcel2.writeNoException();
                parcel2.writeBoolean(resetRrt5);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRatingInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.rating.IRatingInterface";
            }

            @Override // android.media.tv.extension.rating.IRatingInterface
            public Bundle getRRTRatingInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.rating.IRatingInterface");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.rating.IRatingInterface
            public boolean setRRTRatingInfo(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.rating.IRatingInterface");
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.rating.IRatingInterface
            public boolean setResetRrt5() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.rating.IRatingInterface");
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
