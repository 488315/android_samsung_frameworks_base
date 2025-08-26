package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ISurfaceSyncGroupCompletedListener extends IInterface {
    public static final String DESCRIPTOR = "android.window.ISurfaceSyncGroupCompletedListener";

    public static class Default implements ISurfaceSyncGroupCompletedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.ISurfaceSyncGroupCompletedListener
        public void onSurfaceSyncGroupComplete() throws RemoteException {
        }
    }

    void onSurfaceSyncGroupComplete() throws RemoteException;

    public static abstract class Stub extends Binder implements ISurfaceSyncGroupCompletedListener {
        static final int TRANSACTION_onSurfaceSyncGroupComplete = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISurfaceSyncGroupCompletedListener.DESCRIPTOR);
        }

        public static ISurfaceSyncGroupCompletedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISurfaceSyncGroupCompletedListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISurfaceSyncGroupCompletedListener)) {
                return (ISurfaceSyncGroupCompletedListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onSurfaceSyncGroupComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISurfaceSyncGroupCompletedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISurfaceSyncGroupCompletedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onSurfaceSyncGroupComplete();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISurfaceSyncGroupCompletedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISurfaceSyncGroupCompletedListener.DESCRIPTOR;
            }

            @Override // android.window.ISurfaceSyncGroupCompletedListener
            public void onSurfaceSyncGroupComplete() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISurfaceSyncGroupCompletedListener.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
