package android.service.settings.suggestions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface ISuggestionService extends IInterface {

    public static class Default implements ISuggestionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.settings.suggestions.ISuggestionService
        public void dismissSuggestion(Suggestion suggestion) throws RemoteException {
        }

        @Override // android.service.settings.suggestions.ISuggestionService
        public List<Suggestion> getSuggestions() throws RemoteException {
            return null;
        }

        @Override // android.service.settings.suggestions.ISuggestionService
        public void launchSuggestion(Suggestion suggestion) throws RemoteException {
        }
    }

    void dismissSuggestion(Suggestion suggestion) throws RemoteException;

    List<Suggestion> getSuggestions() throws RemoteException;

    void launchSuggestion(Suggestion suggestion) throws RemoteException;

    public static abstract class Stub extends Binder implements ISuggestionService {
        public static final String DESCRIPTOR = "android.service.settings.suggestions.ISuggestionService";
        static final int TRANSACTION_dismissSuggestion = 3;
        static final int TRANSACTION_getSuggestions = 2;
        static final int TRANSACTION_launchSuggestion = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISuggestionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISuggestionService)) {
                return (ISuggestionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 2) {
                return "getSuggestions";
            }
            if (i == 3) {
                return "dismissSuggestion";
            }
            if (i != 4) {
                return null;
            }
            return "launchSuggestion";
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
            if (i == 2) {
                List<Suggestion> suggestions = getSuggestions();
                parcel2.writeNoException();
                parcel2.writeTypedList(suggestions, 1);
            } else if (i == 3) {
                Suggestion suggestion = (Suggestion) parcel.readTypedObject(Suggestion.CREATOR);
                parcel.enforceNoDataAvail();
                dismissSuggestion(suggestion);
                parcel2.writeNoException();
            } else if (i == 4) {
                Suggestion suggestion2 = (Suggestion) parcel.readTypedObject(Suggestion.CREATOR);
                parcel.enforceNoDataAvail();
                launchSuggestion(suggestion2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISuggestionService {
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

            @Override // android.service.settings.suggestions.ISuggestionService
            public List<Suggestion> getSuggestions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Suggestion.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.settings.suggestions.ISuggestionService
            public void dismissSuggestion(Suggestion suggestion) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(suggestion, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.settings.suggestions.ISuggestionService
            public void launchSuggestion(Suggestion suggestion) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(suggestion, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
