package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IPinnedTaskListener extends IInterface {
    public static final String DESCRIPTOR = "android.view.IPinnedTaskListener";

    public static class Default implements IPinnedTaskListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IPinnedTaskListener
        public void onImeVisibilityChanged(boolean z, int i) throws RemoteException {
        }

        @Override // android.view.IPinnedTaskListener
        public void onMovementBoundsChanged(boolean z) throws RemoteException {
        }
    }

    void onImeVisibilityChanged(boolean z, int i) throws RemoteException;

    void onMovementBoundsChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IPinnedTaskListener {
        static final int TRANSACTION_onImeVisibilityChanged = 2;
        static final int TRANSACTION_onMovementBoundsChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IPinnedTaskListener.DESCRIPTOR);
        }

        public static IPinnedTaskListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPinnedTaskListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPinnedTaskListener)) {
                return (IPinnedTaskListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onMovementBoundsChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onImeVisibilityChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPinnedTaskListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPinnedTaskListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onMovementBoundsChanged(z);
            } else if (i == 2) {
                boolean z2 = parcel.readBoolean();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onImeVisibilityChanged(z2, i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPinnedTaskListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPinnedTaskListener.DESCRIPTOR;
            }

            @Override // android.view.IPinnedTaskListener
            public void onMovementBoundsChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPinnedTaskListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IPinnedTaskListener
            public void onImeVisibilityChanged(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPinnedTaskListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
