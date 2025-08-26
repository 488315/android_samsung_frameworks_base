package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IForegroundServiceObserver extends IInterface {
    public static final String DESCRIPTOR = "android.app.IForegroundServiceObserver";

    public static class Default implements IForegroundServiceObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IForegroundServiceObserver
        public void onForegroundStateChanged(IBinder iBinder, String str, int i, boolean z) throws RemoteException {
        }
    }

    void onForegroundStateChanged(IBinder iBinder, String str, int i, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IForegroundServiceObserver {
        static final int TRANSACTION_onForegroundStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IForegroundServiceObserver.DESCRIPTOR);
        }

        public static IForegroundServiceObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IForegroundServiceObserver.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IForegroundServiceObserver)) {
                return (IForegroundServiceObserver) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onForegroundStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IForegroundServiceObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IForegroundServiceObserver.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder strongBinder = parcel.readStrongBinder();
                String string = parcel.readString();
                int i3 = parcel.readInt();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onForegroundStateChanged(strongBinder, string, i3, z);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IForegroundServiceObserver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IForegroundServiceObserver.DESCRIPTOR;
            }

            @Override // android.app.IForegroundServiceObserver
            public void onForegroundStateChanged(IBinder iBinder, String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IForegroundServiceObserver.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
