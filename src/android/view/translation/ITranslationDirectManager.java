package android.view.translation;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.translation.ITranslationCallback;

/* loaded from: classes4.dex */
public interface ITranslationDirectManager extends IInterface {
    public static final String DESCRIPTOR = "android.view.translation.ITranslationDirectManager";

    public static class Default implements ITranslationDirectManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.translation.ITranslationDirectManager
        public void onFinishTranslationSession(int i) throws RemoteException {
        }

        @Override // android.view.translation.ITranslationDirectManager
        public void onTranslationRequest(TranslationRequest translationRequest, int i, ICancellationSignal iCancellationSignal, ITranslationCallback iTranslationCallback) throws RemoteException {
        }
    }

    void onFinishTranslationSession(int i) throws RemoteException;

    void onTranslationRequest(TranslationRequest translationRequest, int i, ICancellationSignal iCancellationSignal, ITranslationCallback iTranslationCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ITranslationDirectManager {
        static final int TRANSACTION_onFinishTranslationSession = 2;
        static final int TRANSACTION_onTranslationRequest = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ITranslationDirectManager.DESCRIPTOR);
        }

        public static ITranslationDirectManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITranslationDirectManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITranslationDirectManager)) {
                return (ITranslationDirectManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onTranslationRequest";
            }
            if (i != 2) {
                return null;
            }
            return "onFinishTranslationSession";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITranslationDirectManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITranslationDirectManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                TranslationRequest translationRequest = (TranslationRequest) parcel.readTypedObject(TranslationRequest.CREATOR);
                int i3 = parcel.readInt();
                ICancellationSignal iCancellationSignalAsInterface = ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                ITranslationCallback iTranslationCallbackAsInterface = ITranslationCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onTranslationRequest(translationRequest, i3, iCancellationSignalAsInterface, iTranslationCallbackAsInterface);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFinishTranslationSession(i4);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITranslationDirectManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITranslationDirectManager.DESCRIPTOR;
            }

            @Override // android.view.translation.ITranslationDirectManager
            public void onTranslationRequest(TranslationRequest translationRequest, int i, ICancellationSignal iCancellationSignal, ITranslationCallback iTranslationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationDirectManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(translationRequest, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iCancellationSignal);
                    parcelObtain.writeStrongInterface(iTranslationCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationDirectManager
            public void onFinishTranslationSession(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationDirectManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
