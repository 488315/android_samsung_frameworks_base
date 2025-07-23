package android.service.contentsuggestions;

import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.ISelectionsCallback;
import android.app.contentsuggestions.SelectionsRequest;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.window.TaskSnapshot;

/* loaded from: classes3.dex */
public interface IContentSuggestionsService extends IInterface {
    public static final String DESCRIPTOR = "android.service.contentsuggestions.IContentSuggestionsService";

    public static class Default implements IContentSuggestionsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void classifyContentSelections(ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException {
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void notifyInteraction(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void provideContextImage(int i, TaskSnapshot taskSnapshot, Bundle bundle) throws RemoteException {
        }

        @Override // android.service.contentsuggestions.IContentSuggestionsService
        public void suggestContentSelections(SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException {
        }
    }

    void classifyContentSelections(ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException;

    void notifyInteraction(String str, Bundle bundle) throws RemoteException;

    void provideContextImage(int i, TaskSnapshot taskSnapshot, Bundle bundle) throws RemoteException;

    void suggestContentSelections(SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IContentSuggestionsService {
        static final int TRANSACTION_classifyContentSelections = 3;
        static final int TRANSACTION_notifyInteraction = 4;
        static final int TRANSACTION_provideContextImage = 1;
        static final int TRANSACTION_suggestContentSelections = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IContentSuggestionsService.DESCRIPTOR);
        }

        public static IContentSuggestionsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IContentSuggestionsService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContentSuggestionsService)) {
                return (IContentSuggestionsService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "provideContextImage";
            }
            if (i == 2) {
                return "suggestContentSelections";
            }
            if (i == 3) {
                return "classifyContentSelections";
            }
            if (i != 4) {
                return null;
            }
            return "notifyInteraction";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IContentSuggestionsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContentSuggestionsService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                TaskSnapshot taskSnapshot = (TaskSnapshot) parcel.readTypedObject(TaskSnapshot.CREATOR);
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                provideContextImage(readInt, taskSnapshot, bundle);
            } else if (i == 2) {
                SelectionsRequest selectionsRequest = (SelectionsRequest) parcel.readTypedObject(SelectionsRequest.CREATOR);
                ISelectionsCallback asInterface = ISelectionsCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                suggestContentSelections(selectionsRequest, asInterface);
            } else if (i == 3) {
                ClassificationsRequest classificationsRequest = (ClassificationsRequest) parcel.readTypedObject(ClassificationsRequest.CREATOR);
                IClassificationsCallback asInterface2 = IClassificationsCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                classifyContentSelections(classificationsRequest, asInterface2);
            } else if (i == 4) {
                String readString = parcel.readString();
                Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                notifyInteraction(readString, bundle2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IContentSuggestionsService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContentSuggestionsService.DESCRIPTOR;
            }

            @Override // android.service.contentsuggestions.IContentSuggestionsService
            public void provideContextImage(int i, TaskSnapshot taskSnapshot, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(taskSnapshot, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentsuggestions.IContentSuggestionsService
            public void suggestContentSelections(SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsService.DESCRIPTOR);
                    obtain.writeTypedObject(selectionsRequest, 0);
                    obtain.writeStrongInterface(iSelectionsCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentsuggestions.IContentSuggestionsService
            public void classifyContentSelections(ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsService.DESCRIPTOR);
                    obtain.writeTypedObject(classificationsRequest, 0);
                    obtain.writeStrongInterface(iClassificationsCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentsuggestions.IContentSuggestionsService
            public void notifyInteraction(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
