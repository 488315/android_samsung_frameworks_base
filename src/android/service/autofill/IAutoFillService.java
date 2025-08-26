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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAutoFillService)) {
                return (IAutoFillService) iInterfaceQueryLocalInterface;
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
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConnectedStateChanged(z);
                    return true;
                case 2:
                    FillRequest fillRequest = (FillRequest) parcel.readTypedObject(FillRequest.CREATOR);
                    IFillCallback iFillCallbackAsInterface = IFillCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onFillRequest(fillRequest, iFillCallbackAsInterface);
                    return true;
                case 3:
                    FillRequest fillRequest2 = (FillRequest) parcel.readTypedObject(FillRequest.CREATOR);
                    IFillCallback iFillCallbackAsInterface2 = IFillCallback.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onFillCredentialRequest(fillRequest2, iFillCallbackAsInterface2, strongBinder);
                    return true;
                case 4:
                    SaveRequest saveRequest = (SaveRequest) parcel.readTypedObject(SaveRequest.CREATOR);
                    ISaveCallback iSaveCallbackAsInterface = ISaveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSaveRequest(saveRequest, iSaveCallbackAsInterface);
                    return true;
                case 5:
                    IResultReceiver iResultReceiverAsInterface = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSavedPasswordCountRequest(iResultReceiverAsInterface);
                    return true;
                case 6:
                    ConvertCredentialRequest convertCredentialRequest = (ConvertCredentialRequest) parcel.readTypedObject(ConvertCredentialRequest.CREATOR);
                    IConvertCredentialCallback iConvertCredentialCallbackAsInterface = IConvertCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onConvertCredentialRequest(convertCredentialRequest, iConvertCredentialCallbackAsInterface);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onFillRequest(FillRequest fillRequest, IFillCallback iFillCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(fillRequest, 0);
                    parcelObtain.writeStrongInterface(iFillCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onFillCredentialRequest(FillRequest fillRequest, IFillCallback iFillCallback, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(fillRequest, 0);
                    parcelObtain.writeStrongInterface(iFillCallback);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onSaveRequest(SaveRequest saveRequest, ISaveCallback iSaveCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(saveRequest, 0);
                    parcelObtain.writeStrongInterface(iSaveCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onSavedPasswordCountRequest(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onConvertCredentialRequest(ConvertCredentialRequest convertCredentialRequest, IConvertCredentialCallback iConvertCredentialCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(convertCredentialRequest, 0);
                    parcelObtain.writeStrongInterface(iConvertCredentialCallback);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onSessionDestroyed(FillEventHistory fillEventHistory) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(fillEventHistory, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
