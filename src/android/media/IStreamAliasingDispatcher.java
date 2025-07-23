package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IStreamAliasingDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.IStreamAliasingDispatcher";

    public static class Default implements IStreamAliasingDispatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IStreamAliasingDispatcher
        public void dispatchStreamAliasingChanged() throws RemoteException {
        }
    }

    void dispatchStreamAliasingChanged() throws RemoteException;

    public static abstract class Stub extends Binder implements IStreamAliasingDispatcher {
        static final int TRANSACTION_dispatchStreamAliasingChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IStreamAliasingDispatcher.DESCRIPTOR);
        }

        public static IStreamAliasingDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IStreamAliasingDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStreamAliasingDispatcher)) {
                return (IStreamAliasingDispatcher) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "dispatchStreamAliasingChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IStreamAliasingDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IStreamAliasingDispatcher.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                dispatchStreamAliasingChanged();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IStreamAliasingDispatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStreamAliasingDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IStreamAliasingDispatcher
            public void dispatchStreamAliasingChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IStreamAliasingDispatcher.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
