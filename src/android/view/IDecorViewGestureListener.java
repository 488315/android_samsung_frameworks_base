package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IDecorViewGestureListener extends IInterface {
    public static final String DESCRIPTOR = "android.view.IDecorViewGestureListener";

    public static class Default implements IDecorViewGestureListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IDecorViewGestureListener
        public void onInterceptionChanged(IBinder iBinder, boolean z) throws RemoteException {
        }
    }

    void onInterceptionChanged(IBinder iBinder, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IDecorViewGestureListener {
        static final int TRANSACTION_onInterceptionChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDecorViewGestureListener.DESCRIPTOR);
        }

        public static IDecorViewGestureListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDecorViewGestureListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDecorViewGestureListener)) {
                return (IDecorViewGestureListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onInterceptionChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDecorViewGestureListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDecorViewGestureListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder strongBinder = parcel.readStrongBinder();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onInterceptionChanged(strongBinder, z);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDecorViewGestureListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDecorViewGestureListener.DESCRIPTOR;
            }

            @Override // android.view.IDecorViewGestureListener
            public void onInterceptionChanged(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDecorViewGestureListener.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
