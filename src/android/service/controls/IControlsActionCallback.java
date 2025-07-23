package android.service.controls;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IControlsActionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.controls.IControlsActionCallback";

    public static class Default implements IControlsActionCallback {
        @Override // android.service.controls.IControlsActionCallback
        public void accept(IBinder iBinder, String str, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void accept(IBinder iBinder, String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IControlsActionCallback {
        static final int TRANSACTION_accept = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IControlsActionCallback.DESCRIPTOR);
        }

        public static IControlsActionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IControlsActionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IControlsActionCallback)) {
                return (IControlsActionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "accept";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IControlsActionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IControlsActionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                accept(readStrongBinder, readString, readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IControlsActionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IControlsActionCallback.DESCRIPTOR;
            }

            @Override // android.service.controls.IControlsActionCallback
            public void accept(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IControlsActionCallback.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
