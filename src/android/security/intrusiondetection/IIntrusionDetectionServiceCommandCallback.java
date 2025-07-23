package android.security.intrusiondetection;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IIntrusionDetectionServiceCommandCallback extends IInterface {
    public static final String DESCRIPTOR = "android.security.intrusiondetection.IIntrusionDetectionServiceCommandCallback";

    public static class Default implements IIntrusionDetectionServiceCommandCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionServiceCommandCallback
        public void onFailure(int i) throws RemoteException {
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionServiceCommandCallback
        public void onSuccess() throws RemoteException {
        }
    }

    public @interface ErrorCode {
        public static final int DATA_SOURCE_UNAVAILABLE = 4;
        public static final int INVALID_STATE_TRANSITION = 2;
        public static final int PERMISSION_DENIED = 1;
        public static final int TRANSPORT_UNAVAILABLE = 3;
        public static final int UNKNOWN = 0;
    }

    void onFailure(int i) throws RemoteException;

    void onSuccess() throws RemoteException;

    public static abstract class Stub extends Binder implements IIntrusionDetectionServiceCommandCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IIntrusionDetectionServiceCommandCallback.DESCRIPTOR);
        }

        public static IIntrusionDetectionServiceCommandCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIntrusionDetectionServiceCommandCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIntrusionDetectionServiceCommandCallback)) {
                return (IIntrusionDetectionServiceCommandCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i != 2) {
                return null;
            }
            return "onFailure";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIntrusionDetectionServiceCommandCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIntrusionDetectionServiceCommandCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onSuccess();
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFailure(readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIntrusionDetectionServiceCommandCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIntrusionDetectionServiceCommandCallback.DESCRIPTOR;
            }

            @Override // android.security.intrusiondetection.IIntrusionDetectionServiceCommandCallback
            public void onSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIntrusionDetectionServiceCommandCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.security.intrusiondetection.IIntrusionDetectionServiceCommandCallback
            public void onFailure(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIntrusionDetectionServiceCommandCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
