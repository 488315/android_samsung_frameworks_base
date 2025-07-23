package android.service.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IEuiccServiceDumpResultCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.euicc.IEuiccServiceDumpResultCallback";

    public static class Default implements IEuiccServiceDumpResultCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.euicc.IEuiccServiceDumpResultCallback
        public void onComplete(String str) throws RemoteException {
        }
    }

    void onComplete(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IEuiccServiceDumpResultCallback {
        static final int TRANSACTION_onComplete = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IEuiccServiceDumpResultCallback.DESCRIPTOR);
        }

        public static IEuiccServiceDumpResultCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEuiccServiceDumpResultCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEuiccServiceDumpResultCallback)) {
                return (IEuiccServiceDumpResultCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEuiccServiceDumpResultCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEuiccServiceDumpResultCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onComplete(readString);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IEuiccServiceDumpResultCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEuiccServiceDumpResultCallback.DESCRIPTOR;
            }

            @Override // android.service.euicc.IEuiccServiceDumpResultCallback
            public void onComplete(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccServiceDumpResultCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
