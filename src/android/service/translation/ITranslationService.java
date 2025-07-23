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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITranslationService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITranslationService)) {
                return (ITranslationService) queryLocalInterface;
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
                IBinder readStrongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                onConnected(readStrongBinder);
            } else if (i == 2) {
                onDisconnected();
            } else if (i == 3) {
                TranslationContext translationContext = (TranslationContext) parcel.readTypedObject(TranslationContext.CREATOR);
                int readInt = parcel.readInt();
                IResultReceiver asInterface = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onCreateTranslationSession(translationContext, readInt, asInterface);
            } else if (i == 4) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                parcel.enforceNoDataAvail();
                onTranslationCapabilitiesRequest(readInt2, readInt3, resultReceiver);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.translation.ITranslationService
            public void onDisconnected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.translation.ITranslationService
            public void onCreateTranslationSession(TranslationContext translationContext, int i, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationService.DESCRIPTOR);
                    obtain.writeTypedObject(translationContext, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.translation.ITranslationService
            public void onTranslationCapabilitiesRequest(int i, int i2, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
