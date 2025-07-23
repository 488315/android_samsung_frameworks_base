package android.service.credentials;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.credentials.IBeginCreateCredentialCallback;
import android.service.credentials.IBeginGetCredentialCallback;
import android.service.credentials.IClearCredentialStateCallback;

/* loaded from: classes3.dex */
public interface ICredentialProviderService extends IInterface {
    public static final String DESCRIPTOR = "android.service.credentials.ICredentialProviderService";

    public static class Default implements ICredentialProviderService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.credentials.ICredentialProviderService
        public void onBeginCreateCredential(BeginCreateCredentialRequest beginCreateCredentialRequest, IBeginCreateCredentialCallback iBeginCreateCredentialCallback) throws RemoteException {
        }

        @Override // android.service.credentials.ICredentialProviderService
        public void onBeginGetCredential(BeginGetCredentialRequest beginGetCredentialRequest, IBeginGetCredentialCallback iBeginGetCredentialCallback) throws RemoteException {
        }

        @Override // android.service.credentials.ICredentialProviderService
        public void onClearCredentialState(ClearCredentialStateRequest clearCredentialStateRequest, IClearCredentialStateCallback iClearCredentialStateCallback) throws RemoteException {
        }
    }

    void onBeginCreateCredential(BeginCreateCredentialRequest beginCreateCredentialRequest, IBeginCreateCredentialCallback iBeginCreateCredentialCallback) throws RemoteException;

    void onBeginGetCredential(BeginGetCredentialRequest beginGetCredentialRequest, IBeginGetCredentialCallback iBeginGetCredentialCallback) throws RemoteException;

    void onClearCredentialState(ClearCredentialStateRequest clearCredentialStateRequest, IClearCredentialStateCallback iClearCredentialStateCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ICredentialProviderService {
        static final int TRANSACTION_onBeginCreateCredential = 2;
        static final int TRANSACTION_onBeginGetCredential = 1;
        static final int TRANSACTION_onClearCredentialState = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ICredentialProviderService.DESCRIPTOR);
        }

        public static ICredentialProviderService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICredentialProviderService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICredentialProviderService)) {
                return (ICredentialProviderService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onBeginGetCredential";
            }
            if (i == 2) {
                return "onBeginCreateCredential";
            }
            if (i != 3) {
                return null;
            }
            return "onClearCredentialState";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICredentialProviderService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICredentialProviderService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                BeginGetCredentialRequest beginGetCredentialRequest = (BeginGetCredentialRequest) parcel.readTypedObject(BeginGetCredentialRequest.CREATOR);
                IBeginGetCredentialCallback asInterface = IBeginGetCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onBeginGetCredential(beginGetCredentialRequest, asInterface);
            } else if (i == 2) {
                BeginCreateCredentialRequest beginCreateCredentialRequest = (BeginCreateCredentialRequest) parcel.readTypedObject(BeginCreateCredentialRequest.CREATOR);
                IBeginCreateCredentialCallback asInterface2 = IBeginCreateCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onBeginCreateCredential(beginCreateCredentialRequest, asInterface2);
            } else if (i == 3) {
                ClearCredentialStateRequest clearCredentialStateRequest = (ClearCredentialStateRequest) parcel.readTypedObject(ClearCredentialStateRequest.CREATOR);
                IClearCredentialStateCallback asInterface3 = IClearCredentialStateCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onClearCredentialState(clearCredentialStateRequest, asInterface3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICredentialProviderService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICredentialProviderService.DESCRIPTOR;
            }

            @Override // android.service.credentials.ICredentialProviderService
            public void onBeginGetCredential(BeginGetCredentialRequest beginGetCredentialRequest, IBeginGetCredentialCallback iBeginGetCredentialCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICredentialProviderService.DESCRIPTOR);
                    obtain.writeTypedObject(beginGetCredentialRequest, 0);
                    obtain.writeStrongInterface(iBeginGetCredentialCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.credentials.ICredentialProviderService
            public void onBeginCreateCredential(BeginCreateCredentialRequest beginCreateCredentialRequest, IBeginCreateCredentialCallback iBeginCreateCredentialCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICredentialProviderService.DESCRIPTOR);
                    obtain.writeTypedObject(beginCreateCredentialRequest, 0);
                    obtain.writeStrongInterface(iBeginCreateCredentialCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.credentials.ICredentialProviderService
            public void onClearCredentialState(ClearCredentialStateRequest clearCredentialStateRequest, IClearCredentialStateCallback iClearCredentialStateCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICredentialProviderService.DESCRIPTOR);
                    obtain.writeTypedObject(clearCredentialStateRequest, 0);
                    obtain.writeStrongInterface(iClearCredentialStateCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
