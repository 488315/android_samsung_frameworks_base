package android.companion;

import android.companion.IAssociationRequestCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes.dex */
public interface ICompanionDeviceDiscoveryService extends IInterface {

    public static class Default implements ICompanionDeviceDiscoveryService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.ICompanionDeviceDiscoveryService
        public void onAssociationCreated() throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceDiscoveryService
        public void startDiscovery(AssociationRequest associationRequest, String str, IAssociationRequestCallback iAssociationRequestCallback, AndroidFuture<String> androidFuture) throws RemoteException {
        }
    }

    void onAssociationCreated() throws RemoteException;

    void startDiscovery(AssociationRequest associationRequest, String str, IAssociationRequestCallback iAssociationRequestCallback, AndroidFuture<String> androidFuture) throws RemoteException;

    public static abstract class Stub extends Binder implements ICompanionDeviceDiscoveryService {
        public static final String DESCRIPTOR = "android.companion.ICompanionDeviceDiscoveryService";
        static final int TRANSACTION_onAssociationCreated = 2;
        static final int TRANSACTION_startDiscovery = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICompanionDeviceDiscoveryService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICompanionDeviceDiscoveryService)) {
                return (ICompanionDeviceDiscoveryService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startDiscovery";
            }
            if (i != 2) {
                return null;
            }
            return "onAssociationCreated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AssociationRequest associationRequest = (AssociationRequest) parcel.readTypedObject(AssociationRequest.CREATOR);
                String string = parcel.readString();
                IAssociationRequestCallback iAssociationRequestCallbackAsInterface = IAssociationRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                AndroidFuture<String> androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                parcel.enforceNoDataAvail();
                startDiscovery(associationRequest, string, iAssociationRequestCallbackAsInterface, androidFuture);
            } else if (i == 2) {
                onAssociationCreated();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICompanionDeviceDiscoveryService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.companion.ICompanionDeviceDiscoveryService
            public void startDiscovery(AssociationRequest associationRequest, String str, IAssociationRequestCallback iAssociationRequestCallback, AndroidFuture<String> androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(associationRequest, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iAssociationRequestCallback);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceDiscoveryService
            public void onAssociationCreated() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
