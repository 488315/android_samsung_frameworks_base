package android.security.advancedprotection;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IAdvancedProtectionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.security.advancedprotection.IAdvancedProtectionCallback";

    public static class Default implements IAdvancedProtectionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.advancedprotection.IAdvancedProtectionCallback
        public void onAdvancedProtectionChanged(boolean z) throws RemoteException {
        }
    }

    void onAdvancedProtectionChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IAdvancedProtectionCallback {
        static final int TRANSACTION_onAdvancedProtectionChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAdvancedProtectionCallback.DESCRIPTOR);
        }

        public static IAdvancedProtectionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAdvancedProtectionCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAdvancedProtectionCallback)) {
                return (IAdvancedProtectionCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAdvancedProtectionChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAdvancedProtectionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAdvancedProtectionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onAdvancedProtectionChanged(z);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAdvancedProtectionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAdvancedProtectionCallback.DESCRIPTOR;
            }

            @Override // android.security.advancedprotection.IAdvancedProtectionCallback
            public void onAdvancedProtectionChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedProtectionCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
