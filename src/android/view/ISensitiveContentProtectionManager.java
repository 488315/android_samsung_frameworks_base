package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISensitiveContentProtectionManager extends IInterface {
    public static final String DESCRIPTOR = "android.view.ISensitiveContentProtectionManager";

    public static class Default implements ISensitiveContentProtectionManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.ISensitiveContentProtectionManager
        public void setSensitiveContentProtection(IBinder iBinder, String str, boolean z) throws RemoteException {
        }
    }

    void setSensitiveContentProtection(IBinder iBinder, String str, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISensitiveContentProtectionManager {
        static final int TRANSACTION_setSensitiveContentProtection = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISensitiveContentProtectionManager.DESCRIPTOR);
        }

        public static ISensitiveContentProtectionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISensitiveContentProtectionManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISensitiveContentProtectionManager)) {
                return (ISensitiveContentProtectionManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "setSensitiveContentProtection";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISensitiveContentProtectionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISensitiveContentProtectionManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder strongBinder = parcel.readStrongBinder();
                String string = parcel.readString();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setSensitiveContentProtection(strongBinder, string, z);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISensitiveContentProtectionManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISensitiveContentProtectionManager.DESCRIPTOR;
            }

            @Override // android.view.ISensitiveContentProtectionManager
            public void setSensitiveContentProtection(IBinder iBinder, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISensitiveContentProtectionManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
