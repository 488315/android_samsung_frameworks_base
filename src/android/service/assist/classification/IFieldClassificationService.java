package android.service.assist.classification;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.assist.classification.IFieldClassificationCallback;

/* loaded from: classes3.dex */
public interface IFieldClassificationService extends IInterface {
    public static final String DESCRIPTOR = "android.service.assist.classification.IFieldClassificationService";

    public static class Default implements IFieldClassificationService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.assist.classification.IFieldClassificationService
        public void onConnected(boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.service.assist.classification.IFieldClassificationService
        public void onDisconnected() throws RemoteException {
        }

        @Override // android.service.assist.classification.IFieldClassificationService
        public void onFieldClassificationRequest(FieldClassificationRequest fieldClassificationRequest, IFieldClassificationCallback iFieldClassificationCallback) throws RemoteException {
        }
    }

    void onConnected(boolean z, boolean z2) throws RemoteException;

    void onDisconnected() throws RemoteException;

    void onFieldClassificationRequest(FieldClassificationRequest fieldClassificationRequest, IFieldClassificationCallback iFieldClassificationCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IFieldClassificationService {
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onFieldClassificationRequest = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IFieldClassificationService.DESCRIPTOR);
        }

        public static IFieldClassificationService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IFieldClassificationService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFieldClassificationService)) {
                return (IFieldClassificationService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onConnected";
            }
            if (i == 2) {
                return "onDisconnected";
            }
            if (i != 3) {
                return null;
            }
            return "onFieldClassificationRequest";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFieldClassificationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFieldClassificationService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onConnected(readBoolean, readBoolean2);
            } else if (i == 2) {
                onDisconnected();
            } else if (i == 3) {
                FieldClassificationRequest fieldClassificationRequest = (FieldClassificationRequest) parcel.readTypedObject(FieldClassificationRequest.CREATOR);
                IFieldClassificationCallback asInterface = IFieldClassificationCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onFieldClassificationRequest(fieldClassificationRequest, asInterface);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IFieldClassificationService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFieldClassificationService.DESCRIPTOR;
            }

            @Override // android.service.assist.classification.IFieldClassificationService
            public void onConnected(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFieldClassificationService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.assist.classification.IFieldClassificationService
            public void onDisconnected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFieldClassificationService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.assist.classification.IFieldClassificationService
            public void onFieldClassificationRequest(FieldClassificationRequest fieldClassificationRequest, IFieldClassificationCallback iFieldClassificationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFieldClassificationService.DESCRIPTOR);
                    obtain.writeTypedObject(fieldClassificationRequest, 0);
                    obtain.writeStrongInterface(iFieldClassificationCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
