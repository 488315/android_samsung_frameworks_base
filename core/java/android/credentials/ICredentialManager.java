package android.credentials;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes.dex */
public interface ICredentialManager extends IInterface {
    public static final String DESCRIPTOR = "android.credentials.ICredentialManager";

    ICancellationSignal clearCredentialState(
            ClearCredentialStateRequest clearCredentialStateRequest,
            IClearCredentialStateCallback iClearCredentialStateCallback,
            String str)
            throws RemoteException;

    ICancellationSignal executeCreateCredential(
            CreateCredentialRequest createCredentialRequest,
            ICreateCredentialCallback iCreateCredentialCallback,
            String str)
            throws RemoteException;

    ICancellationSignal executeGetCredential(
            GetCredentialRequest getCredentialRequest,
            IGetCredentialCallback iGetCredentialCallback,
            String str)
            throws RemoteException;

    ICancellationSignal executePrepareGetCredential(
            GetCredentialRequest getCredentialRequest,
            IPrepareGetCredentialCallback iPrepareGetCredentialCallback,
            IGetCredentialCallback iGetCredentialCallback,
            String str)
            throws RemoteException;

    ICancellationSignal getCandidateCredentials(
            GetCredentialRequest getCredentialRequest,
            IGetCandidateCredentialsCallback iGetCandidateCredentialsCallback,
            IBinder iBinder,
            String str)
            throws RemoteException;

    List<CredentialProviderInfo> getCredentialProviderServices(int i, int i2)
            throws RemoteException;

    List<CredentialProviderInfo> getCredentialProviderServicesForTesting(int i)
            throws RemoteException;

    boolean isEnabledCredentialProviderService(ComponentName componentName, String str)
            throws RemoteException;

    boolean isServiceEnabled() throws RemoteException;

    void registerCredentialDescription(
            RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest, String str)
            throws RemoteException;

    void setEnabledProviders(
            List<String> list,
            List<String> list2,
            int i,
            ISetEnabledProvidersCallback iSetEnabledProvidersCallback)
            throws RemoteException;

    void unregisterCredentialDescription(
            UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest,
            String str)
            throws RemoteException;

    public static class Default implements ICredentialManager {
        @Override // android.credentials.ICredentialManager
        public ICancellationSignal executeGetCredential(
                GetCredentialRequest request,
                IGetCredentialCallback callback,
                String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public ICancellationSignal executePrepareGetCredential(
                GetCredentialRequest request,
                IPrepareGetCredentialCallback prepareGetCredentialCallback,
                IGetCredentialCallback getCredentialCallback,
                String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public ICancellationSignal executeCreateCredential(
                CreateCredentialRequest request,
                ICreateCredentialCallback callback,
                String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public ICancellationSignal getCandidateCredentials(
                GetCredentialRequest request,
                IGetCandidateCredentialsCallback callback,
                IBinder clientCallback,
                String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public ICancellationSignal clearCredentialState(
                ClearCredentialStateRequest request,
                IClearCredentialStateCallback callback,
                String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public void setEnabledProviders(
                List<String> primaryProviders,
                List<String> providers,
                int userId,
                ISetEnabledProvidersCallback callback)
                throws RemoteException {}

        @Override // android.credentials.ICredentialManager
        public void registerCredentialDescription(
                RegisterCredentialDescriptionRequest request, String callingPackage)
                throws RemoteException {}

        @Override // android.credentials.ICredentialManager
        public void unregisterCredentialDescription(
                UnregisterCredentialDescriptionRequest request, String callingPackage)
                throws RemoteException {}

        @Override // android.credentials.ICredentialManager
        public boolean isEnabledCredentialProviderService(
                ComponentName componentName, String callingPackage) throws RemoteException {
            return false;
        }

        @Override // android.credentials.ICredentialManager
        public List<CredentialProviderInfo> getCredentialProviderServices(
                int userId, int providerFilter) throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public List<CredentialProviderInfo> getCredentialProviderServicesForTesting(
                int providerFilter) throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public boolean isServiceEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ICredentialManager {
        static final int TRANSACTION_clearCredentialState = 5;
        static final int TRANSACTION_executeCreateCredential = 3;
        static final int TRANSACTION_executeGetCredential = 1;
        static final int TRANSACTION_executePrepareGetCredential = 2;
        static final int TRANSACTION_getCandidateCredentials = 4;
        static final int TRANSACTION_getCredentialProviderServices = 10;
        static final int TRANSACTION_getCredentialProviderServicesForTesting = 11;
        static final int TRANSACTION_isEnabledCredentialProviderService = 9;
        static final int TRANSACTION_isServiceEnabled = 12;
        static final int TRANSACTION_registerCredentialDescription = 7;
        static final int TRANSACTION_setEnabledProviders = 6;
        static final int TRANSACTION_unregisterCredentialDescription = 8;

        public Stub() {
            attachInterface(this, ICredentialManager.DESCRIPTOR);
        }

        public static ICredentialManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICredentialManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ICredentialManager)) {
                return (ICredentialManager) iin;
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
                    return "executeGetCredential";
                case 2:
                    return "executePrepareGetCredential";
                case 3:
                    return "executeCreateCredential";
                case 4:
                    return "getCandidateCredentials";
                case 5:
                    return "clearCredentialState";
                case 6:
                    return "setEnabledProviders";
                case 7:
                    return "registerCredentialDescription";
                case 8:
                    return "unregisterCredentialDescription";
                case 9:
                    return "isEnabledCredentialProviderService";
                case 10:
                    return "getCredentialProviderServices";
                case 11:
                    return "getCredentialProviderServicesForTesting";
                case 12:
                    return "isServiceEnabled";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICredentialManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICredentialManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    GetCredentialRequest _arg0 =
                            (GetCredentialRequest)
                                    data.readTypedObject(GetCredentialRequest.CREATOR);
                    IGetCredentialCallback _arg1 =
                            IGetCredentialCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg2 = data.readString();
                    data.enforceNoDataAvail();
                    ICancellationSignal _result = executeGetCredential(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result);
                    return true;
                case 2:
                    GetCredentialRequest _arg02 =
                            (GetCredentialRequest)
                                    data.readTypedObject(GetCredentialRequest.CREATOR);
                    IPrepareGetCredentialCallback _arg12 =
                            IPrepareGetCredentialCallback.Stub.asInterface(data.readStrongBinder());
                    IGetCredentialCallback _arg22 =
                            IGetCredentialCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    ICancellationSignal _result2 =
                            executePrepareGetCredential(_arg02, _arg12, _arg22, _arg3);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result2);
                    return true;
                case 3:
                    CreateCredentialRequest _arg03 =
                            (CreateCredentialRequest)
                                    data.readTypedObject(CreateCredentialRequest.CREATOR);
                    ICreateCredentialCallback _arg13 =
                            ICreateCredentialCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg23 = data.readString();
                    data.enforceNoDataAvail();
                    ICancellationSignal _result3 = executeCreateCredential(_arg03, _arg13, _arg23);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result3);
                    return true;
                case 4:
                    GetCredentialRequest _arg04 =
                            (GetCredentialRequest)
                                    data.readTypedObject(GetCredentialRequest.CREATOR);
                    IGetCandidateCredentialsCallback _arg14 =
                            IGetCandidateCredentialsCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    IBinder _arg24 = data.readStrongBinder();
                    String _arg32 = data.readString();
                    data.enforceNoDataAvail();
                    ICancellationSignal _result4 =
                            getCandidateCredentials(_arg04, _arg14, _arg24, _arg32);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result4);
                    return true;
                case 5:
                    ClearCredentialStateRequest _arg05 =
                            (ClearCredentialStateRequest)
                                    data.readTypedObject(ClearCredentialStateRequest.CREATOR);
                    IClearCredentialStateCallback _arg15 =
                            IClearCredentialStateCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg25 = data.readString();
                    data.enforceNoDataAvail();
                    ICancellationSignal _result5 = clearCredentialState(_arg05, _arg15, _arg25);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result5);
                    return true;
                case 6:
                    List<String> _arg06 = data.createStringArrayList();
                    List<String> _arg16 = data.createStringArrayList();
                    int _arg26 = data.readInt();
                    ISetEnabledProvidersCallback _arg33 =
                            ISetEnabledProvidersCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setEnabledProviders(_arg06, _arg16, _arg26, _arg33);
                    reply.writeNoException();
                    return true;
                case 7:
                    RegisterCredentialDescriptionRequest _arg07 =
                            (RegisterCredentialDescriptionRequest)
                                    data.readTypedObject(
                                            RegisterCredentialDescriptionRequest.CREATOR);
                    String _arg17 = data.readString();
                    data.enforceNoDataAvail();
                    registerCredentialDescription(_arg07, _arg17);
                    reply.writeNoException();
                    return true;
                case 8:
                    UnregisterCredentialDescriptionRequest _arg08 =
                            (UnregisterCredentialDescriptionRequest)
                                    data.readTypedObject(
                                            UnregisterCredentialDescriptionRequest.CREATOR);
                    String _arg18 = data.readString();
                    data.enforceNoDataAvail();
                    unregisterCredentialDescription(_arg08, _arg18);
                    reply.writeNoException();
                    return true;
                case 9:
                    ComponentName _arg09 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg19 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result6 = isEnabledCredentialProviderService(_arg09, _arg19);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    List<CredentialProviderInfo> _result7 =
                            getCredentialProviderServices(_arg010, _arg110);
                    reply.writeNoException();
                    reply.writeTypedList(_result7, 1);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    data.enforceNoDataAvail();
                    List<CredentialProviderInfo> _result8 =
                            getCredentialProviderServicesForTesting(_arg011);
                    reply.writeNoException();
                    reply.writeTypedList(_result8, 1);
                    return true;
                case 12:
                    boolean _result9 = isServiceEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICredentialManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICredentialManager.DESCRIPTOR;
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal executeGetCredential(
                    GetCredentialRequest request,
                    IGetCredentialCallback callback,
                    String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ICancellationSignal _result =
                            ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal executePrepareGetCredential(
                    GetCredentialRequest request,
                    IPrepareGetCredentialCallback prepareGetCredentialCallback,
                    IGetCredentialCallback getCredentialCallback,
                    String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(prepareGetCredentialCallback);
                    _data.writeStrongInterface(getCredentialCallback);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    ICancellationSignal _result =
                            ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal executeCreateCredential(
                    CreateCredentialRequest request,
                    ICreateCredentialCallback callback,
                    String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    ICancellationSignal _result =
                            ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal getCandidateCredentials(
                    GetCredentialRequest request,
                    IGetCandidateCredentialsCallback callback,
                    IBinder clientCallback,
                    String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeStrongBinder(clientCallback);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    ICancellationSignal _result =
                            ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal clearCredentialState(
                    ClearCredentialStateRequest request,
                    IClearCredentialStateCallback callback,
                    String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    ICancellationSignal _result =
                            ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public void setEnabledProviders(
                    List<String> primaryProviders,
                    List<String> providers,
                    int userId,
                    ISetEnabledProvidersCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    _data.writeStringList(primaryProviders);
                    _data.writeStringList(providers);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public void registerCredentialDescription(
                    RegisterCredentialDescriptionRequest request, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public void unregisterCredentialDescription(
                    UnregisterCredentialDescriptionRequest request, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public boolean isEnabledCredentialProviderService(
                    ComponentName componentName, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public List<CredentialProviderInfo> getCredentialProviderServices(
                    int userId, int providerFilter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(providerFilter);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    List<CredentialProviderInfo> _result =
                            _reply.createTypedArrayList(CredentialProviderInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public List<CredentialProviderInfo> getCredentialProviderServicesForTesting(
                    int providerFilter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    _data.writeInt(providerFilter);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    List<CredentialProviderInfo> _result =
                            _reply.createTypedArrayList(CredentialProviderInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public boolean isServiceEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
