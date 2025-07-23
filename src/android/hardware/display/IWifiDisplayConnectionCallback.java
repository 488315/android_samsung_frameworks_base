package android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IWifiDisplayConnectionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.display.IWifiDisplayConnectionCallback";

    public static class Default implements IWifiDisplayConnectionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.display.IWifiDisplayConnectionCallback
        public void onFailure(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IWifiDisplayConnectionCallback
        public void onSuccess(List<SemWifiDisplayParameter> list) throws RemoteException {
        }
    }

    void onFailure(int i) throws RemoteException;

    void onSuccess(List<SemWifiDisplayParameter> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IWifiDisplayConnectionCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IWifiDisplayConnectionCallback.DESCRIPTOR);
        }

        public static IWifiDisplayConnectionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWifiDisplayConnectionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWifiDisplayConnectionCallback)) {
                return (IWifiDisplayConnectionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i != 2) {
                return null;
            }
            return "onFailure";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWifiDisplayConnectionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWifiDisplayConnectionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(SemWifiDisplayParameter.CREATOR);
                parcel.enforceNoDataAvail();
                onSuccess(createTypedArrayList);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFailure(readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IWifiDisplayConnectionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWifiDisplayConnectionCallback.DESCRIPTOR;
            }

            @Override // android.hardware.display.IWifiDisplayConnectionCallback
            public void onSuccess(List<SemWifiDisplayParameter> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWifiDisplayConnectionCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IWifiDisplayConnectionCallback
            public void onFailure(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWifiDisplayConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
