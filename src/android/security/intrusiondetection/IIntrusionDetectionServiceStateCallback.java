package android.security.intrusiondetection;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IIntrusionDetectionServiceStateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.security.intrusiondetection.IIntrusionDetectionServiceStateCallback";

    public static class Default implements IIntrusionDetectionServiceStateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionServiceStateCallback
        public void onStateChange(int i) throws RemoteException {
        }
    }

    public @interface State {
        public static final int DISABLED = 1;
        public static final int ENABLED = 2;
        public static final int UNKNOWN = 0;
    }

    void onStateChange(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IIntrusionDetectionServiceStateCallback {
        static final int TRANSACTION_onStateChange = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IIntrusionDetectionServiceStateCallback.DESCRIPTOR);
        }

        public static IIntrusionDetectionServiceStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIntrusionDetectionServiceStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIntrusionDetectionServiceStateCallback)) {
                return (IIntrusionDetectionServiceStateCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onStateChange";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIntrusionDetectionServiceStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIntrusionDetectionServiceStateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStateChange(readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IIntrusionDetectionServiceStateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIntrusionDetectionServiceStateCallback.DESCRIPTOR;
            }

            @Override // android.security.intrusiondetection.IIntrusionDetectionServiceStateCallback
            public void onStateChange(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIntrusionDetectionServiceStateCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
