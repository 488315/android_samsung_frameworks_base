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
    void onConnectedStateChanged(boolean z) throws RemoteException;

    void onConvertCredentialRequest(ConvertCredentialRequest convertCredentialRequest, IConvertCredentialCallback iConvertCredentialCallback) throws RemoteException;

    void onFillCredentialRequest(FillRequest fillRequest, IFillCallback iFillCallback, IBinder iBinder) throws RemoteException;

    void onFillRequest(FillRequest fillRequest, IFillCallback iFillCallback) throws RemoteException;

    void onSaveRequest(SaveRequest saveRequest, ISaveCallback iSaveCallback) throws RemoteException;

    void onSavedPasswordCountRequest(IResultReceiver iResultReceiver) throws RemoteException;

    public static class Default implements IAutoFillService {
        @Override // android.service.autofill.IAutoFillService
        public void onConnectedStateChanged(boolean connected) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onFillRequest(FillRequest request, IFillCallback callback) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onFillCredentialRequest(FillRequest request, IFillCallback callback, IBinder client) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSaveRequest(SaveRequest request, ISaveCallback callback) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSavedPasswordCountRequest(IResultReceiver receiver) throws RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onConvertCredentialRequest(ConvertCredentialRequest convertCredentialRequest, IConvertCredentialCallback convertCredentialCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAutoFillService {
        public static final String DESCRIPTOR = "android.service.autofill.IAutoFillService";
        static final int TRANSACTION_onConnectedStateChanged = 1;
        static final int TRANSACTION_onConvertCredentialRequest = 6;
        static final int TRANSACTION_onFillCredentialRequest = 3;
        static final int TRANSACTION_onFillRequest = 2;
        static final int TRANSACTION_onSaveRequest = 4;
        static final int TRANSACTION_onSavedPasswordCountRequest = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAutoFillService)) {
                return (IAutoFillService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onConnectedStateChanged(_arg0);
                    return true;
                case 2:
                    FillRequest _arg02 = (FillRequest) data.readTypedObject(FillRequest.CREATOR);
                    IFillCallback _arg1 = IFillCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onFillRequest(_arg02, _arg1);
                    return true;
                case 3:
                    FillRequest _arg03 = (FillRequest) data.readTypedObject(FillRequest.CREATOR);
                    IFillCallback _arg12 = IFillCallback.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg2 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    onFillCredentialRequest(_arg03, _arg12, _arg2);
                    return true;
                case 4:
                    SaveRequest _arg04 = (SaveRequest) data.readTypedObject(SaveRequest.CREATOR);
                    ISaveCallback _arg13 = ISaveCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onSaveRequest(_arg04, _arg13);
                    return true;
                case 5:
                    IResultReceiver _arg05 = IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onSavedPasswordCountRequest(_arg05);
                    return true;
                case 6:
                    ConvertCredentialRequest _arg06 = (ConvertCredentialRequest) data.readTypedObject(ConvertCredentialRequest.CREATOR);
                    IConvertCredentialCallback _arg14 = IConvertCredentialCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onConvertCredentialRequest(_arg06, _arg14);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAutoFillService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.autofill.IAutoFillService
            public void onConnectedStateChanged(boolean connected) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(connected);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onFillRequest(FillRequest request, IFillCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onFillCredentialRequest(FillRequest request, IFillCallback callback, IBinder client) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeStrongBinder(client);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onSaveRequest(SaveRequest request, ISaveCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onSavedPasswordCountRequest(IResultReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(receiver);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onConvertCredentialRequest(ConvertCredentialRequest convertCredentialRequest, IConvertCredentialCallback convertCredentialCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(convertCredentialRequest, 0);
                    _data.writeStrongInterface(convertCredentialCallback);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
