package android.credentials;

import android.content.ComponentName;
import android.credentials.IClearCredentialStateCallback;
import android.credentials.ICreateCredentialCallback;
import android.credentials.IGetCandidateCredentialsCallback;
import android.credentials.IGetCredentialCallback;
import android.credentials.IPrepareGetCredentialCallback;
import android.credentials.ISetEnabledProvidersCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface ICredentialManager extends IInterface {
    public static final String DESCRIPTOR = "android.credentials.ICredentialManager";

    public static class Default implements ICredentialManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public ICancellationSignal clearCredentialState(ClearCredentialStateRequest clearCredentialStateRequest, IClearCredentialStateCallback iClearCredentialStateCallback, String str) throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public ICancellationSignal executeCreateCredential(CreateCredentialRequest createCredentialRequest, ICreateCredentialCallback iCreateCredentialCallback, String str) throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public ICancellationSignal executeGetCredential(GetCredentialRequest getCredentialRequest, IGetCredentialCallback iGetCredentialCallback, String str) throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public ICancellationSignal executePrepareGetCredential(GetCredentialRequest getCredentialRequest, IPrepareGetCredentialCallback iPrepareGetCredentialCallback, IGetCredentialCallback iGetCredentialCallback, String str) throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public ICancellationSignal getCandidateCredentials(GetCredentialRequest getCredentialRequest, IGetCandidateCredentialsCallback iGetCandidateCredentialsCallback, IBinder iBinder, String str) throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public List<CredentialProviderInfo> getCredentialProviderServices(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public List<CredentialProviderInfo> getCredentialProviderServicesForTesting(int i) throws RemoteException {
            return null;
        }

        @Override // android.credentials.ICredentialManager
        public boolean isEnabledCredentialProviderService(ComponentName componentName, String str) throws RemoteException {
            return false;
        }

        @Override // android.credentials.ICredentialManager
        public boolean isServiceEnabled() throws RemoteException {
            return false;
        }

        @Override // android.credentials.ICredentialManager
        public void registerCredentialDescription(RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest, String str) throws RemoteException {
        }

        @Override // android.credentials.ICredentialManager
        public void setEnabledProviders(List<String> list, List<String> list2, int i, ISetEnabledProvidersCallback iSetEnabledProvidersCallback) throws RemoteException {
        }

        @Override // android.credentials.ICredentialManager
        public void unregisterCredentialDescription(UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest, String str) throws RemoteException {
        }
    }

    ICancellationSignal clearCredentialState(ClearCredentialStateRequest clearCredentialStateRequest, IClearCredentialStateCallback iClearCredentialStateCallback, String str) throws RemoteException;

    ICancellationSignal executeCreateCredential(CreateCredentialRequest createCredentialRequest, ICreateCredentialCallback iCreateCredentialCallback, String str) throws RemoteException;

    ICancellationSignal executeGetCredential(GetCredentialRequest getCredentialRequest, IGetCredentialCallback iGetCredentialCallback, String str) throws RemoteException;

    ICancellationSignal executePrepareGetCredential(GetCredentialRequest getCredentialRequest, IPrepareGetCredentialCallback iPrepareGetCredentialCallback, IGetCredentialCallback iGetCredentialCallback, String str) throws RemoteException;

    ICancellationSignal getCandidateCredentials(GetCredentialRequest getCredentialRequest, IGetCandidateCredentialsCallback iGetCandidateCredentialsCallback, IBinder iBinder, String str) throws RemoteException;

    List<CredentialProviderInfo> getCredentialProviderServices(int i, int i2) throws RemoteException;

    List<CredentialProviderInfo> getCredentialProviderServicesForTesting(int i) throws RemoteException;

    boolean isEnabledCredentialProviderService(ComponentName componentName, String str) throws RemoteException;

    boolean isServiceEnabled() throws RemoteException;

    void registerCredentialDescription(RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest, String str) throws RemoteException;

    void setEnabledProviders(List<String> list, List<String> list2, int i, ISetEnabledProvidersCallback iSetEnabledProvidersCallback) throws RemoteException;

    void unregisterCredentialDescription(UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ICredentialManager {
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, ICredentialManager.DESCRIPTOR);
        }

        public static ICredentialManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICredentialManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICredentialManager)) {
                return (ICredentialManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICredentialManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICredentialManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    GetCredentialRequest getCredentialRequest = (GetCredentialRequest) parcel.readTypedObject(GetCredentialRequest.CREATOR);
                    IGetCredentialCallback asInterface = IGetCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ICancellationSignal executeGetCredential = executeGetCredential(getCredentialRequest, asInterface, readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(executeGetCredential);
                    return true;
                case 2:
                    GetCredentialRequest getCredentialRequest2 = (GetCredentialRequest) parcel.readTypedObject(GetCredentialRequest.CREATOR);
                    IPrepareGetCredentialCallback asInterface2 = IPrepareGetCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    IGetCredentialCallback asInterface3 = IGetCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ICancellationSignal executePrepareGetCredential = executePrepareGetCredential(getCredentialRequest2, asInterface2, asInterface3, readString2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(executePrepareGetCredential);
                    return true;
                case 3:
                    CreateCredentialRequest createCredentialRequest = (CreateCredentialRequest) parcel.readTypedObject(CreateCredentialRequest.CREATOR);
                    ICreateCredentialCallback asInterface4 = ICreateCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ICancellationSignal executeCreateCredential = executeCreateCredential(createCredentialRequest, asInterface4, readString3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(executeCreateCredential);
                    return true;
                case 4:
                    GetCredentialRequest getCredentialRequest3 = (GetCredentialRequest) parcel.readTypedObject(GetCredentialRequest.CREATOR);
                    IGetCandidateCredentialsCallback asInterface5 = IGetCandidateCredentialsCallback.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ICancellationSignal candidateCredentials = getCandidateCredentials(getCredentialRequest3, asInterface5, readStrongBinder, readString4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(candidateCredentials);
                    return true;
                case 5:
                    ClearCredentialStateRequest clearCredentialStateRequest = (ClearCredentialStateRequest) parcel.readTypedObject(ClearCredentialStateRequest.CREATOR);
                    IClearCredentialStateCallback asInterface6 = IClearCredentialStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ICancellationSignal clearCredentialState = clearCredentialState(clearCredentialStateRequest, asInterface6, readString5);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(clearCredentialState);
                    return true;
                case 6:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    int readInt = parcel.readInt();
                    ISetEnabledProvidersCallback asInterface7 = ISetEnabledProvidersCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setEnabledProviders(createStringArrayList, createStringArrayList2, readInt, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest = (RegisterCredentialDescriptionRequest) parcel.readTypedObject(RegisterCredentialDescriptionRequest.CREATOR);
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerCredentialDescription(registerCredentialDescriptionRequest, readString6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest = (UnregisterCredentialDescriptionRequest) parcel.readTypedObject(UnregisterCredentialDescriptionRequest.CREATOR);
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterCredentialDescription(unregisterCredentialDescriptionRequest, readString7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isEnabledCredentialProviderService = isEnabledCredentialProviderService(componentName, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEnabledCredentialProviderService);
                    return true;
                case 10:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<CredentialProviderInfo> credentialProviderServices = getCredentialProviderServices(readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(credentialProviderServices, 1);
                    return true;
                case 11:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<CredentialProviderInfo> credentialProviderServicesForTesting = getCredentialProviderServicesForTesting(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(credentialProviderServicesForTesting, 1);
                    return true;
                case 12:
                    boolean isServiceEnabled = isServiceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isServiceEnabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICredentialManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICredentialManager.DESCRIPTOR;
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal executeGetCredential(GetCredentialRequest getCredentialRequest, IGetCredentialCallback iGetCredentialCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(getCredentialRequest, 0);
                    obtain.writeStrongInterface(iGetCredentialCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal executePrepareGetCredential(GetCredentialRequest getCredentialRequest, IPrepareGetCredentialCallback iPrepareGetCredentialCallback, IGetCredentialCallback iGetCredentialCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(getCredentialRequest, 0);
                    obtain.writeStrongInterface(iPrepareGetCredentialCallback);
                    obtain.writeStrongInterface(iGetCredentialCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal executeCreateCredential(CreateCredentialRequest createCredentialRequest, ICreateCredentialCallback iCreateCredentialCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(createCredentialRequest, 0);
                    obtain.writeStrongInterface(iCreateCredentialCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal getCandidateCredentials(GetCredentialRequest getCredentialRequest, IGetCandidateCredentialsCallback iGetCandidateCredentialsCallback, IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(getCredentialRequest, 0);
                    obtain.writeStrongInterface(iGetCandidateCredentialsCallback);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal clearCredentialState(ClearCredentialStateRequest clearCredentialStateRequest, IClearCredentialStateCallback iClearCredentialStateCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(clearCredentialStateRequest, 0);
                    obtain.writeStrongInterface(iClearCredentialStateCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public void setEnabledProviders(List<String> list, List<String> list2, int i, ISetEnabledProvidersCallback iSetEnabledProvidersCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSetEnabledProvidersCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public void registerCredentialDescription(RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(registerCredentialDescriptionRequest, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public void unregisterCredentialDescription(UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(unregisterCredentialDescriptionRequest, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public boolean isEnabledCredentialProviderService(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public List<CredentialProviderInfo> getCredentialProviderServices(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CredentialProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public List<CredentialProviderInfo> getCredentialProviderServicesForTesting(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CredentialProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public boolean isServiceEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
