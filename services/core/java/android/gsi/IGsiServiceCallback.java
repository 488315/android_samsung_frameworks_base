package android.gsi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGsiServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.gsi.IGsiServiceCallback";

    public class Default implements IGsiServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.gsi.IGsiServiceCallback
        public void onResult(int i) throws RemoteException {}
    }

    public abstract class Stub extends Binder implements IGsiServiceCallback {
        static final int TRANSACTION_onResult = 1;

        public final class Proxy implements IGsiServiceCallback {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.gsi.IGsiServiceCallback
            public final void onResult(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(IGsiServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IGsiServiceCallback.DESCRIPTOR);
        }

        public static IGsiServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IGsiServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null
                    && (queryLocalInterface instanceof IGsiServiceCallback)) {
                return (IGsiServiceCallback) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mRemote = iBinder;
            return proxy;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGsiServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGsiServiceCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            onResult(readInt);
            return true;
        }
    }

    void onResult(int i) throws RemoteException;
}
