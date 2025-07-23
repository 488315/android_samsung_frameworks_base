package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IBackAnimationFinishedCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.IBackAnimationFinishedCallback";

    public static class Default implements IBackAnimationFinishedCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IBackAnimationFinishedCallback
        public void onAnimationFinished(boolean z) throws RemoteException {
        }
    }

    void onAnimationFinished(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IBackAnimationFinishedCallback {
        static final int TRANSACTION_onAnimationFinished = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IBackAnimationFinishedCallback.DESCRIPTOR);
        }

        public static IBackAnimationFinishedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBackAnimationFinishedCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBackAnimationFinishedCallback)) {
                return (IBackAnimationFinishedCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAnimationFinished";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBackAnimationFinishedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBackAnimationFinishedCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onAnimationFinished(readBoolean);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IBackAnimationFinishedCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBackAnimationFinishedCallback.DESCRIPTOR;
            }

            @Override // android.window.IBackAnimationFinishedCallback
            public void onAnimationFinished(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBackAnimationFinishedCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
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
