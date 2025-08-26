package android.service.translation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.view.translation.TranslationContext;
import com.android.internal.os.IResultReceiver;

/* loaded from: classes3.dex */
public interface ITranslationService extends IInterface {
    public static final String DESCRIPTOR = "android.service.translation.ITranslationService";

    public static class Default implements ITranslationService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.translation.ITranslationService
        public void onConnected(IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.translation.ITranslationService
        public void onCreateTranslationSession(TranslationContext translationContext, int i, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.service.translation.ITranslationService
        public void onDisconnected() throws RemoteException {
        }

        @Override // android.service.translation.ITranslationService
        public void onTranslationCapabilitiesRequest(int i, int i2, ResultReceiver resultReceiver) throws RemoteException {
        }
    }

    void onConnected(IBinder iBinder) throws RemoteException;

    void onCreateTranslationSession(TranslationContext translationContext, int i, IResultReceiver iResultReceiver) throws RemoteException;

    void onDisconnected() throws RemoteException;

    void onTranslationCapabilitiesRequest(int i, int i2, ResultReceiver resultReceiver) throws RemoteException;

    public static abstract class Stub extends Binder implements ITranslationService {
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onCreateTranslationSession = 3;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onTranslationCapabilitiesRequest = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ITranslationService.DESCRIPTOR);
        }

        public static ITranslationService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITranslationService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITranslationService)) {
                return (ITranslationService) iInterfaceQueryLocalInterface;
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
            if (i == 3) {
                return "onCreateTranslationSession";
            }
            if (i != 4) {
                return null;
            }
            return "onTranslationCapabilitiesRequest";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITranslationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITranslationService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder strongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                onConnected(strongBinder);
            } else if (i == 2) {
                onDisconnected();
            } else if (i == 3) {
                TranslationContext translationContext = (TranslationContext) parcel.readTypedObject(TranslationContext.CREATOR);
                int i3 = parcel.readInt();
                IResultReceiver iResultReceiverAsInterface = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onCreateTranslationSession(translationContext, i3, iResultReceiverAsInterface);
            } else if (i == 4) {
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                parcel.enforceNoDataAvail();
                onTranslationCapabilitiesRequest(i4, i5, resultReceiver);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITranslationService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITranslationService.DESCRIPTOR;
            }

            @Override // android.service.translation.ITranslationService
            public void onConnected(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.translation.ITranslationService
            public void onDisconnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.translation.ITranslationService
            public void onCreateTranslationSession(TranslationContext translationContext, int i, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(translationContext, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.translation.ITranslationService
            public void onTranslationCapabilitiesRequest(int i, int i2, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
