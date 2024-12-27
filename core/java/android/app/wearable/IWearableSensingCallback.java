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

    void openFile(String str, AndroidFuture<ParcelFileDescriptor> androidFuture) throws RemoteException;

    public static class Default implements IWearableSensingCallback {
        @Override // android.app.wearable.IWearableSensingCallback
        public void openFile(String filename, AndroidFuture<ParcelFileDescriptor> future) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWearableSensingCallback {
        static final int TRANSACTION_openFile = 1;

        public Stub() {
            attachInterface(this, IWearableSensingCallback.DESCRIPTOR);
        }

        public static IWearableSensingCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWearableSensingCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IWearableSensingCallback)) {
                return (IWearableSensingCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "openFile";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IWearableSensingCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWearableSensingCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    AndroidFuture<ParcelFileDescriptor> _arg1 = (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    data.enforceNoDataAvail();
                    openFile(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWearableSensingCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWearableSensingCallback.DESCRIPTOR;
            }

            @Override // android.app.wearable.IWearableSensingCallback
            public void openFile(String filename, AndroidFuture<ParcelFileDescriptor> future) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingCallback.DESCRIPTOR);
                    _data.writeString(filename);
                    _data.writeTypedObject(future, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
