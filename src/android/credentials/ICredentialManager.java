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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICredentialManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICredentialManager)) {
                return (ICredentialManager) iInterfaceQueryLocalInterface;
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
                    IGetCredentialCallback iGetCredentialCallbackAsInterface = IGetCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ICancellationSignal iCancellationSignalExecuteGetCredential = executeGetCredential(getCredentialRequest, iGetCredentialCallbackAsInterface, string);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCancellationSignalExecuteGetCredential);
                    return true;
                case 2:
                    GetCredentialRequest getCredentialRequest2 = (GetCredentialRequest) parcel.readTypedObject(GetCredentialRequest.CREATOR);
                    IPrepareGetCredentialCallback iPrepareGetCredentialCallbackAsInterface = IPrepareGetCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    IGetCredentialCallback iGetCredentialCallbackAsInterface2 = IGetCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ICancellationSignal iCancellationSignalExecutePrepareGetCredential = executePrepareGetCredential(getCredentialRequest2, iPrepareGetCredentialCallbackAsInterface, iGetCredentialCallbackAsInterface2, string2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCancellationSignalExecutePrepareGetCredential);
                    return true;
                case 3:
                    CreateCredentialRequest createCredentialRequest = (CreateCredentialRequest) parcel.readTypedObject(CreateCredentialRequest.CREATOR);
                    ICreateCredentialCallback iCreateCredentialCallbackAsInterface = ICreateCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ICancellationSignal iCancellationSignalExecuteCreateCredential = executeCreateCredential(createCredentialRequest, iCreateCredentialCallbackAsInterface, string3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCancellationSignalExecuteCreateCredential);
                    return true;
                case 4:
                    GetCredentialRequest getCredentialRequest3 = (GetCredentialRequest) parcel.readTypedObject(GetCredentialRequest.CREATOR);
                    IGetCandidateCredentialsCallback iGetCandidateCredentialsCallbackAsInterface = IGetCandidateCredentialsCallback.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder = parcel.readStrongBinder();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ICancellationSignal candidateCredentials = getCandidateCredentials(getCredentialRequest3, iGetCandidateCredentialsCallbackAsInterface, strongBinder, string4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(candidateCredentials);
                    return true;
                case 5:
                    ClearCredentialStateRequest clearCredentialStateRequest = (ClearCredentialStateRequest) parcel.readTypedObject(ClearCredentialStateRequest.CREATOR);
                    IClearCredentialStateCallback iClearCredentialStateCallbackAsInterface = IClearCredentialStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ICancellationSignal iCancellationSignalClearCredentialState = clearCredentialState(clearCredentialStateRequest, iClearCredentialStateCallbackAsInterface, string5);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCancellationSignalClearCredentialState);
                    return true;
                case 6:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    int i3 = parcel.readInt();
                    ISetEnabledProvidersCallback iSetEnabledProvidersCallbackAsInterface = ISetEnabledProvidersCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setEnabledProviders(arrayListCreateStringArrayList, arrayListCreateStringArrayList2, i3, iSetEnabledProvidersCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest = (RegisterCredentialDescriptionRequest) parcel.readTypedObject(RegisterCredentialDescriptionRequest.CREATOR);
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerCredentialDescription(registerCredentialDescriptionRequest, string6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest = (UnregisterCredentialDescriptionRequest) parcel.readTypedObject(UnregisterCredentialDescriptionRequest.CREATOR);
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterCredentialDescription(unregisterCredentialDescriptionRequest, string7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsEnabledCredentialProviderService = isEnabledCredentialProviderService(componentName, string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEnabledCredentialProviderService);
                    return true;
                case 10:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<CredentialProviderInfo> credentialProviderServices = getCredentialProviderServices(i4, i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(credentialProviderServices, 1);
                    return true;
                case 11:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<CredentialProviderInfo> credentialProviderServicesForTesting = getCredentialProviderServicesForTesting(i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(credentialProviderServicesForTesting, 1);
                    return true;
                case 12:
                    boolean zIsServiceEnabled = isServiceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsServiceEnabled);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(getCredentialRequest, 0);
                    parcelObtain.writeStrongInterface(iGetCredentialCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal executePrepareGetCredential(GetCredentialRequest getCredentialRequest, IPrepareGetCredentialCallback iPrepareGetCredentialCallback, IGetCredentialCallback iGetCredentialCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(getCredentialRequest, 0);
                    parcelObtain.writeStrongInterface(iPrepareGetCredentialCallback);
                    parcelObtain.writeStrongInterface(iGetCredentialCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal executeCreateCredential(CreateCredentialRequest createCredentialRequest, ICreateCredentialCallback iCreateCredentialCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(createCredentialRequest, 0);
                    parcelObtain.writeStrongInterface(iCreateCredentialCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal getCandidateCredentials(GetCredentialRequest getCredentialRequest, IGetCandidateCredentialsCallback iGetCandidateCredentialsCallback, IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(getCredentialRequest, 0);
                    parcelObtain.writeStrongInterface(iGetCandidateCredentialsCallback);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public ICancellationSignal clearCredentialState(ClearCredentialStateRequest clearCredentialStateRequest, IClearCredentialStateCallback iClearCredentialStateCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clearCredentialStateRequest, 0);
                    parcelObtain.writeStrongInterface(iClearCredentialStateCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public void setEnabledProviders(List<String> list, List<String> list2, int i, ISetEnabledProvidersCallback iSetEnabledProvidersCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSetEnabledProvidersCallback);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public void registerCredentialDescription(RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(registerCredentialDescriptionRequest, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public void unregisterCredentialDescription(UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(unregisterCredentialDescriptionRequest, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public boolean isEnabledCredentialProviderService(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public List<CredentialProviderInfo> getCredentialProviderServices(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(CredentialProviderInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public List<CredentialProviderInfo> getCredentialProviderServicesForTesting(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(CredentialProviderInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.ICredentialManager
            public boolean isServiceEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialManager.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
