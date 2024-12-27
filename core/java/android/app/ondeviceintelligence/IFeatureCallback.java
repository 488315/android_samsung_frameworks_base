package android.app.ondeviceintelligence;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IFeatureCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.ondeviceintelligence.IFeatureCallback";

    void onFailure(int i, String str, PersistableBundle persistableBundle) throws RemoteException;

    void onSuccess(Feature feature) throws RemoteException;

    public static class Default implements IFeatureCallback {
        @Override // android.app.ondeviceintelligence.IFeatureCallback
        public void onSuccess(Feature result) throws RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IFeatureCallback
        public void onFailure(int errorCode, String errorMessage, PersistableBundle errorParams) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFeatureCallback {
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            attachInterface(this, IFeatureCallback.DESCRIPTOR);
        }

        public static IFeatureCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IFeatureCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IFeatureCallback)) {
                return (IFeatureCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 2:
                    return "onSuccess";
                case 3:
                    return "onFailure";
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
                data.enforceInterface(IFeatureCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFeatureCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 2:
                    Feature _arg0 = (Feature) data.readTypedObject(Feature.CREATOR);
                    data.enforceNoDataAvail();
                    onSuccess(_arg0);
                    return true;
                case 3:
                    int _arg02 = data.readInt();
                    String _arg1 = data.readString();
                    PersistableBundle _arg2 = (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    onFailure(_arg02, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IFeatureCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFeatureCallback.DESCRIPTOR;
            }

            @Override // android.app.ondeviceintelligence.IFeatureCallback
            public void onSuccess(Feature result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFeatureCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IFeatureCallback
            public void onFailure(int errorCode, String errorMessage, PersistableBundle errorParams) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFeatureCallback.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    _data.writeString(errorMessage);
                    _data.writeTypedObject(errorParams, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
