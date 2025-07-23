package android.app.admin;

import android.app.admin.IKeyguardCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IKeyguardClient extends IInterface {
    public static final String DESCRIPTOR = "android.app.admin.IKeyguardClient";

    public static class Default implements IKeyguardClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.admin.IKeyguardClient
        public void onCreateKeyguardSurface(IBinder iBinder, IKeyguardCallback iKeyguardCallback) throws RemoteException {
        }
    }

    void onCreateKeyguardSurface(IBinder iBinder, IKeyguardCallback iKeyguardCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IKeyguardClient {
        static final int TRANSACTION_onCreateKeyguardSurface = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IKeyguardClient.DESCRIPTOR);
        }

        public static IKeyguardClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKeyguardClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKeyguardClient)) {
                return (IKeyguardClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onCreateKeyguardSurface";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKeyguardClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKeyguardClient.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                IKeyguardCallback asInterface = IKeyguardCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onCreateKeyguardSurface(readStrongBinder, asInterface);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IKeyguardClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyguardClient.DESCRIPTOR;
            }

            @Override // android.app.admin.IKeyguardClient
            public void onCreateKeyguardSurface(IBinder iBinder, IKeyguardCallback iKeyguardCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IKeyguardClient.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iKeyguardCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
