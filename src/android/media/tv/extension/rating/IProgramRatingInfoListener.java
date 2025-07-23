package android.media.tv.extension.rating;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IProgramRatingInfoListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.rating.IProgramRatingInfoListener";

    public static class Default implements IProgramRatingInfoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.rating.IProgramRatingInfoListener
        public void onProgramInfoChanged(String str, Bundle bundle) throws RemoteException {
        }
    }

    void onProgramInfoChanged(String str, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IProgramRatingInfoListener {
        static final int TRANSACTION_onProgramInfoChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IProgramRatingInfoListener.DESCRIPTOR);
        }

        public static IProgramRatingInfoListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProgramRatingInfoListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProgramRatingInfoListener)) {
                return (IProgramRatingInfoListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onProgramInfoChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProgramRatingInfoListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProgramRatingInfoListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onProgramInfoChanged(readString, bundle);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IProgramRatingInfoListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProgramRatingInfoListener.DESCRIPTOR;
            }

            @Override // android.media.tv.extension.rating.IProgramRatingInfoListener
            public void onProgramInfoChanged(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProgramRatingInfoListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
