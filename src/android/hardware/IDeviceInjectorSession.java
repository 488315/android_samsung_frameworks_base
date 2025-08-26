package android.hardware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDeviceInjectorSession extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.IDeviceInjectorSession";

    public static class Default implements IDeviceInjectorSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.IDeviceInjectorSession
        public void setDeviceInjectorPending(boolean z) throws RemoteException {
        }

        @Override // android.hardware.IDeviceInjectorSession
        public void stopDeviceInjector() throws RemoteException {
        }
    }

    void setDeviceInjectorPending(boolean z) throws RemoteException;

    void stopDeviceInjector() throws RemoteException;

    public static abstract class Stub extends Binder implements IDeviceInjectorSession {
        static final int TRANSACTION_setDeviceInjectorPending = 2;
        static final int TRANSACTION_stopDeviceInjector = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IDeviceInjectorSession.DESCRIPTOR);
        }

        public static IDeviceInjectorSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDeviceInjectorSession.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDeviceInjectorSession)) {
                return (IDeviceInjectorSession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "stopDeviceInjector";
            }
            if (i != 2) {
                return null;
            }
            return "setDeviceInjectorPending";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDeviceInjectorSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDeviceInjectorSession.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                stopDeviceInjector();
                parcel2.writeNoException();
            } else if (i == 2) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setDeviceInjectorPending(z);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDeviceInjectorSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceInjectorSession.DESCRIPTOR;
            }

            @Override // android.hardware.IDeviceInjectorSession
            public void stopDeviceInjector() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInjectorSession.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IDeviceInjectorSession
            public void setDeviceInjectorPending(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInjectorSession.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
