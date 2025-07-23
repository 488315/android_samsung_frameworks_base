package android.app.wearable;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes.dex */
public interface IWearableSensingCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.wearable.IWearableSensingCallback";

    public static class Default implements IWearableSensingCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.wearable.IWearableSensingCallback
        public void openFile(String str, AndroidFuture<ParcelFileDescriptor> androidFuture) throws RemoteException {
        }
    }

    void openFile(String str, AndroidFuture<ParcelFileDescriptor> androidFuture) throws RemoteException;

    public static abstract class Stub extends Binder implements IWearableSensingCallback {
        static final int TRANSACTION_openFile = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IWearableSensingCallback.DESCRIPTOR);
        }

        public static IWearableSensingCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWearableSensingCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWearableSensingCallback)) {
                return (IWearableSensingCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "openFile";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWearableSensingCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWearableSensingCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                AndroidFuture<ParcelFileDescriptor> androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                parcel.enforceNoDataAvail();
                openFile(readString, androidFuture);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IWearableSensingCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWearableSensingCallback.DESCRIPTOR;
            }

            @Override // android.app.wearable.IWearableSensingCallback
            public void openFile(String str, AndroidFuture<ParcelFileDescriptor> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
