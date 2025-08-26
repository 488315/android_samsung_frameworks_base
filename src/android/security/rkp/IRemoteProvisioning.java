package android.security.rkp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.rkp.IGetRegistrationCallback;

/* loaded from: classes3.dex */
public interface IRemoteProvisioning extends IInterface {
    public static final String DESCRIPTOR = "android.security.rkp.IRemoteProvisioning";

    public static class Default implements IRemoteProvisioning {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.rkp.IRemoteProvisioning
        public void getRegistration(String str, IGetRegistrationCallback iGetRegistrationCallback) throws RemoteException {
        }
    }

    void getRegistration(String str, IGetRegistrationCallback iGetRegistrationCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteProvisioning {
        static final int TRANSACTION_getRegistration = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IRemoteProvisioning.DESCRIPTOR);
        }

        public static IRemoteProvisioning asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteProvisioning.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteProvisioning)) {
                return (IRemoteProvisioning) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getRegistration";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteProvisioning.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteProvisioning.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                IGetRegistrationCallback iGetRegistrationCallbackAsInterface = IGetRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getRegistration(string, iGetRegistrationCallbackAsInterface);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteProvisioning {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteProvisioning.DESCRIPTOR;
            }

            @Override // android.security.rkp.IRemoteProvisioning
            public void getRegistration(String str, IGetRegistrationCallback iGetRegistrationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRemoteProvisioning.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iGetRegistrationCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
