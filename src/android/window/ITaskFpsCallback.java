package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ITaskFpsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.ITaskFpsCallback";

    public static class Default implements ITaskFpsCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.ITaskFpsCallback
        public void onFpsReported(float f) throws RemoteException {
        }
    }

    void onFpsReported(float f) throws RemoteException;

    public static abstract class Stub extends Binder implements ITaskFpsCallback {
        static final int TRANSACTION_onFpsReported = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ITaskFpsCallback.DESCRIPTOR);
        }

        public static ITaskFpsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITaskFpsCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITaskFpsCallback)) {
                return (ITaskFpsCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onFpsReported";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITaskFpsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITaskFpsCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                float readFloat = parcel.readFloat();
                parcel.enforceNoDataAvail();
                onFpsReported(readFloat);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ITaskFpsCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITaskFpsCallback.DESCRIPTOR;
            }

            @Override // android.window.ITaskFpsCallback
            public void onFpsReported(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITaskFpsCallback.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
