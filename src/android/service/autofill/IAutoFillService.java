package android.service.autofill;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.autofill.IConvertCredentialCallback;
import android.service.autofill.IFillCallback;
import android.service.autofill.ISaveCallback;
import com.android.internal.os.IResultReceiver;

/* loaded from: classes3.dex */
public interface IAutoFillService extends IInterface {

    public static class Default implements IAutoFillService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.autofill.IAutoFillService
        public void onConnectedStateChanged(boolean z) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onConvertCredentialRequest(ConvertCredentialRequest convertCredentialRequest, IConvertCredentialCallback iConvertCredentialCallback) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onFillCredentialRequest(FillRequest fillRequest, IFillCallback iFillCallback, IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onFillRequest(FillRequest fillRequest, IFillCallback iFillCallback) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSaveRequest(SaveRequest saveRequest, ISaveCallback iSaveCallback) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSavedPasswordCountRequest(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSessionDestroyed(FillEventHistory fillEventHistory) throws RemoteException {
        }
    }

    void onConnectedStateChanged(boolean z) throws RemoteException;

    void onConvertCredentialRequest(ConvertCredentialRequest convertCredentialRequest, IConvertCredentialCallback iConvertCredentialCallback) throws RemoteException;

    void onFillCredentialRequest(FillRequest fillRequest, IFillCallback iFillCallback, IBinder iBinder) throws RemoteException;

    void onFillRequest(FillRequest fillRequest, IFillCallback iFillCallback) throws RemoteException;

    void onSaveRequest(SaveRequest saveRequest, ISaveCallback iSaveCallback) throws RemoteException;

    void onSavedPasswordCountRequest(IResultReceiver iResultReceiver) throws RemoteException;

    void onSessionDestroyed(FillEventHistory fillEventHistory) throws RemoteException;

    public static abstract class Stub extends Binder implements IAutoFillService {
        public static final String DESCRIPTOR = "android.service.autofill.IAutoFillService";
        static final int TRANSACTION_onConnectedStateChanged = 1;
        static final int TRANSACTION_onConvertCredentialRequest = 6;
        static final int TRANSACTION_onFillCredentialRequest = 3;
        static final int TRANSACTION_onFillRequest = 2;
        static final int TRANSACTION_onSaveRequest = 4;
        static final int TRANSACTION_onSavedPasswordCountRequest = 5;
        static final int TRANSACTION_onSessionDestroyed = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAutoFillService)) {
                return (IAutoFillService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConnectedStateChanged";
                case 2:
                    return "onFillRequest";
                case 3:
                    return "onFillCredentialRequest";
                case 4:
                    return "onSaveRequest";
                case 5:
                    return "onSavedPasswordCountRequest";
                case 6:
                    return "onConvertCredentialRequest";
                case 7:
                    return "onSessionDestroyed";
                default:
                    return null;
            }
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
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConnectedStateChanged(readBoolean);
                    return true;
                case 2:
                    FillRequest fillRequest = (FillRequest) parcel.readTypedObject(FillRequest.CREATOR);
                    IFillCallback asInterface = IFillCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onFillRequest(fillRequest, asInterface);
                    return true;
                case 3:
                    FillRequest fillRequest2 = (FillRequest) parcel.readTypedObject(FillRequest.CREATOR);
                    IFillCallback asInterface2 = IFillCallback.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onFillCredentialRequest(fillRequest2, asInterface2, readStrongBinder);
                    return true;
                case 4:
                    SaveRequest saveRequest = (SaveRequest) parcel.readTypedObject(SaveRequest.CREATOR);
                    ISaveCallback asInterface3 = ISaveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSaveRequest(saveRequest, asInterface3);
                    return true;
                case 5:
                    IResultReceiver asInterface4 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSavedPasswordCountRequest(asInterface4);
                    return true;
                case 6:
                    ConvertCredentialRequest convertCredentialRequest = (ConvertCredentialRequest) parcel.readTypedObject(ConvertCredentialRequest.CREATOR);
                    IConvertCredentialCallback asInterface5 = IConvertCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onConvertCredentialRequest(convertCredentialRequest, asInterface5);
                    return true;
                case 7:
                    FillEventHistory fillEventHistory = (FillEventHistory) parcel.readTypedObject(FillEventHistory.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSessionDestroyed(fillEventHistory);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAutoFillService {
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

            @Override // android.service.autofill.IAutoFillService
            public void onConnectedStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onFillRequest(FillRequest fillRequest, IFillCallback iFillCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fillRequest, 0);
                    obtain.writeStrongInterface(iFillCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onFillCredentialRequest(FillRequest fillRequest, IFillCallback iFillCallback, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fillRequest, 0);
                    obtain.writeStrongInterface(iFillCallback);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onSaveRequest(SaveRequest saveRequest, ISaveCallback iSaveCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(saveRequest, 0);
                    obtain.writeStrongInterface(iSaveCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onSavedPasswordCountRequest(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onConvertCredentialRequest(ConvertCredentialRequest convertCredentialRequest, IConvertCredentialCallback iConvertCredentialCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(convertCredentialRequest, 0);
                    obtain.writeStrongInterface(iConvertCredentialCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onSessionDestroyed(FillEventHistory fillEventHistory) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fillEventHistory, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
