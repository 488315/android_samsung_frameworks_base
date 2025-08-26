package android.service.remotelockscreenvalidation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback;

/* loaded from: classes3.dex */
public interface IRemoteLockscreenValidationService extends IInterface {
    public static final String DESCRIPTOR = "android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService";

    public static class Default implements IRemoteLockscreenValidationService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService
        public void validateLockscreenGuess(byte[] bArr, IRemoteLockscreenValidationCallback iRemoteLockscreenValidationCallback) throws RemoteException {
        }
    }

    void validateLockscreenGuess(byte[] bArr, IRemoteLockscreenValidationCallback iRemoteLockscreenValidationCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteLockscreenValidationService {
        static final int TRANSACTION_validateLockscreenGuess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IRemoteLockscreenValidationService.DESCRIPTOR);
        }

        public static IRemoteLockscreenValidationService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteLockscreenValidationService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteLockscreenValidationService)) {
                return (IRemoteLockscreenValidationService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "validateLockscreenGuess";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteLockscreenValidationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteLockscreenValidationService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                byte[] bArrCreateByteArray = parcel.createByteArray();
                IRemoteLockscreenValidationCallback iRemoteLockscreenValidationCallbackAsInterface = IRemoteLockscreenValidationCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                validateLockscreenGuess(bArrCreateByteArray, iRemoteLockscreenValidationCallbackAsInterface);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteLockscreenValidationService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteLockscreenValidationService.DESCRIPTOR;
            }

            @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService
            public void validateLockscreenGuess(byte[] bArr, IRemoteLockscreenValidationCallback iRemoteLockscreenValidationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteLockscreenValidationService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeStrongInterface(iRemoteLockscreenValidationCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
