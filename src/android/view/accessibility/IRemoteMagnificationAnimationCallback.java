package android.view.accessibility;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IRemoteMagnificationAnimationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.view.accessibility.IRemoteMagnificationAnimationCallback";

    public static class Default implements IRemoteMagnificationAnimationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.accessibility.IRemoteMagnificationAnimationCallback
        public void onResult(boolean z) throws RemoteException {
        }
    }

    void onResult(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteMagnificationAnimationCallback {
        static final int TRANSACTION_onResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IRemoteMagnificationAnimationCallback.DESCRIPTOR);
        }

        public static IRemoteMagnificationAnimationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteMagnificationAnimationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteMagnificationAnimationCallback)) {
                return (IRemoteMagnificationAnimationCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onResult";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteMagnificationAnimationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteMagnificationAnimationCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onResult(readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteMagnificationAnimationCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteMagnificationAnimationCallback.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IRemoteMagnificationAnimationCallback
            public void onResult(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteMagnificationAnimationCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
