package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.feature.ConnectionFailureInfo;

/* loaded from: classes4.dex */
public interface IImsTrafficSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsTrafficSessionCallback";

    public static class Default implements IImsTrafficSessionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsTrafficSessionCallback
        public void onError(ConnectionFailureInfo connectionFailureInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsTrafficSessionCallback
        public void onReady() throws RemoteException {
        }
    }

    void onError(ConnectionFailureInfo connectionFailureInfo) throws RemoteException;

    void onReady() throws RemoteException;

    public static abstract class Stub extends Binder implements IImsTrafficSessionCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onReady = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IImsTrafficSessionCallback.DESCRIPTOR);
        }

        public static IImsTrafficSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsTrafficSessionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsTrafficSessionCallback)) {
                return (IImsTrafficSessionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onReady";
            }
            if (i != 2) {
                return null;
            }
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsTrafficSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsTrafficSessionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onReady();
            } else if (i == 2) {
                ConnectionFailureInfo connectionFailureInfo = (ConnectionFailureInfo) parcel.readTypedObject(ConnectionFailureInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onError(connectionFailureInfo);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IImsTrafficSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsTrafficSessionCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsTrafficSessionCallback
            public void onReady() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsTrafficSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsTrafficSessionCallback
            public void onError(ConnectionFailureInfo connectionFailureInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsTrafficSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(connectionFailureInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
