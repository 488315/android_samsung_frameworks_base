package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IKeyEventActivityListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IKeyEventActivityListener";

    public static class Default implements IKeyEventActivityListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.input.IKeyEventActivityListener
        public void onKeyEventActivity() throws RemoteException {
        }
    }

    void onKeyEventActivity() throws RemoteException;

    public static abstract class Stub extends Binder implements IKeyEventActivityListener {
        static final int TRANSACTION_onKeyEventActivity = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IKeyEventActivityListener.DESCRIPTOR);
        }

        public static IKeyEventActivityListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKeyEventActivityListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKeyEventActivityListener)) {
                return (IKeyEventActivityListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onKeyEventActivity";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKeyEventActivityListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKeyEventActivityListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onKeyEventActivity();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IKeyEventActivityListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyEventActivityListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IKeyEventActivityListener
            public void onKeyEventActivity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IKeyEventActivityListener.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
