package android.service.remotelockscreenvalidation;

import android.app.RemoteLockscreenValidationResult;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IRemoteLockscreenValidationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback";

    public static class Default implements IRemoteLockscreenValidationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback
        public void onFailure(String str) throws RemoteException {
        }

        @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback
        public void onSuccess(RemoteLockscreenValidationResult remoteLockscreenValidationResult) throws RemoteException {
        }
    }

    void onFailure(String str) throws RemoteException;

    void onSuccess(RemoteLockscreenValidationResult remoteLockscreenValidationResult) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteLockscreenValidationCallback {
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
            attachInterface(this, IRemoteLockscreenValidationCallback.DESCRIPTOR);
        }

        public static IRemoteLockscreenValidationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteLockscreenValidationCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteLockscreenValidationCallback)) {
                return (IRemoteLockscreenValidationCallback) iInterfaceQueryLocalInterface;
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
                parcel.enforceInterface(IRemoteLockscreenValidationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteLockscreenValidationCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                RemoteLockscreenValidationResult remoteLockscreenValidationResult = (RemoteLockscreenValidationResult) parcel.readTypedObject(RemoteLockscreenValidationResult.CREATOR);
                parcel.enforceNoDataAvail();
                onSuccess(remoteLockscreenValidationResult);
            } else if (i == 2) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onFailure(string);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRemoteLockscreenValidationCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteLockscreenValidationCallback.DESCRIPTOR;
            }

            @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback
            public void onSuccess(RemoteLockscreenValidationResult remoteLockscreenValidationResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRemoteLockscreenValidationCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(remoteLockscreenValidationResult, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback
            public void onFailure(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRemoteLockscreenValidationCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
