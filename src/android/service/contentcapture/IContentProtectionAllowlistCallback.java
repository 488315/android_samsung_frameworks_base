package android.service.contentcapture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IContentProtectionAllowlistCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.contentcapture.IContentProtectionAllowlistCallback";

    public static class Default implements IContentProtectionAllowlistCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.contentcapture.IContentProtectionAllowlistCallback
        public void setAllowlist(List<String> list) throws RemoteException {
        }
    }

    void setAllowlist(List<String> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IContentProtectionAllowlistCallback {
        static final int TRANSACTION_setAllowlist = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IContentProtectionAllowlistCallback.DESCRIPTOR);
        }

        public static IContentProtectionAllowlistCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IContentProtectionAllowlistCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IContentProtectionAllowlistCallback)) {
                return (IContentProtectionAllowlistCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "setAllowlist";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IContentProtectionAllowlistCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContentProtectionAllowlistCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                parcel.enforceNoDataAvail();
                setAllowlist(arrayListCreateStringArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IContentProtectionAllowlistCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContentProtectionAllowlistCallback.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IContentProtectionAllowlistCallback
            public void setAllowlist(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentProtectionAllowlistCallback.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
