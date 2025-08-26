package android.app.supervision;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISupervisionAppService extends IInterface {
    public static final String DESCRIPTOR = "android.app.supervision.ISupervisionAppService";

    public static class Default implements ISupervisionAppService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.supervision.ISupervisionAppService
        public void onDisabled() throws RemoteException {
        }

        @Override // android.app.supervision.ISupervisionAppService
        public void onEnabled() throws RemoteException {
        }
    }

    void onDisabled() throws RemoteException;

    void onEnabled() throws RemoteException;

    public static abstract class Stub extends Binder implements ISupervisionAppService {
        static final int TRANSACTION_onDisabled = 2;
        static final int TRANSACTION_onEnabled = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISupervisionAppService.DESCRIPTOR);
        }

        public static ISupervisionAppService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISupervisionAppService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISupervisionAppService)) {
                return (ISupervisionAppService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onEnabled";
            }
            if (i != 2) {
                return null;
            }
            return "onDisabled";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISupervisionAppService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISupervisionAppService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onEnabled();
                parcel2.writeNoException();
            } else if (i == 2) {
                onDisabled();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISupervisionAppService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISupervisionAppService.DESCRIPTOR;
            }

            @Override // android.app.supervision.ISupervisionAppService
            public void onEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISupervisionAppService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.supervision.ISupervisionAppService
            public void onDisabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISupervisionAppService.DESCRIPTOR);
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
