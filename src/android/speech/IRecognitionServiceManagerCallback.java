package android.speech;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.speech.IRecognitionService;

/* loaded from: classes3.dex */
public interface IRecognitionServiceManagerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.speech.IRecognitionServiceManagerCallback";

    public static class Default implements IRecognitionServiceManagerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.speech.IRecognitionServiceManagerCallback
        public void onError(int i) throws RemoteException {
        }

        @Override // android.speech.IRecognitionServiceManagerCallback
        public void onSuccess(IRecognitionService iRecognitionService) throws RemoteException {
        }
    }

    void onError(int i) throws RemoteException;

    void onSuccess(IRecognitionService iRecognitionService) throws RemoteException;

    public static abstract class Stub extends Binder implements IRecognitionServiceManagerCallback {
        static final int TRANSACTION_onError = 2;
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
            attachInterface(this, IRecognitionServiceManagerCallback.DESCRIPTOR);
        }

        public static IRecognitionServiceManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRecognitionServiceManagerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRecognitionServiceManagerCallback)) {
                return (IRecognitionServiceManagerCallback) iInterfaceQueryLocalInterface;
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
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRecognitionServiceManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRecognitionServiceManagerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IRecognitionService iRecognitionServiceAsInterface = IRecognitionService.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onSuccess(iRecognitionServiceAsInterface);
            } else if (i == 2) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRecognitionServiceManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRecognitionServiceManagerCallback.DESCRIPTOR;
            }

            @Override // android.speech.IRecognitionServiceManagerCallback
            public void onSuccess(IRecognitionService iRecognitionService) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRecognitionServiceManagerCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRecognitionService);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionServiceManagerCallback
            public void onError(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRecognitionServiceManagerCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
