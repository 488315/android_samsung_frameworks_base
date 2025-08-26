package android.service.translation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.translation.TranslationResponse;

/* loaded from: classes3.dex */
public interface ITranslationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.translation.ITranslationCallback";

    public static class Default implements ITranslationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.translation.ITranslationCallback
        public void onTranslationResponse(TranslationResponse translationResponse) throws RemoteException {
        }
    }

    void onTranslationResponse(TranslationResponse translationResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements ITranslationCallback {
        static final int TRANSACTION_onTranslationResponse = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ITranslationCallback.DESCRIPTOR);
        }

        public static ITranslationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITranslationCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITranslationCallback)) {
                return (ITranslationCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onTranslationResponse";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITranslationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITranslationCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                TranslationResponse translationResponse = (TranslationResponse) parcel.readTypedObject(TranslationResponse.CREATOR);
                parcel.enforceNoDataAvail();
                onTranslationResponse(translationResponse);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ITranslationCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITranslationCallback.DESCRIPTOR;
            }

            @Override // android.service.translation.ITranslationCallback
            public void onTranslationResponse(TranslationResponse translationResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(translationResponse, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
