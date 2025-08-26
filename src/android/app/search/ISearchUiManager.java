package android.app.search;

import android.app.search.ISearchCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISearchUiManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.search.ISearchUiManager";

    public static class Default implements ISearchUiManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.search.ISearchUiManager
        public void createSearchSession(SearchContext searchContext, SearchSessionId searchSessionId, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.search.ISearchUiManager
        public void destroySearchSession(SearchSessionId searchSessionId) throws RemoteException {
        }

        @Override // android.app.search.ISearchUiManager
        public void notifyEvent(SearchSessionId searchSessionId, Query query, SearchTargetEvent searchTargetEvent) throws RemoteException {
        }

        @Override // android.app.search.ISearchUiManager
        public void query(SearchSessionId searchSessionId, Query query, ISearchCallback iSearchCallback) throws RemoteException {
        }

        @Override // android.app.search.ISearchUiManager
        public void registerEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) throws RemoteException {
        }

        @Override // android.app.search.ISearchUiManager
        public void unregisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) throws RemoteException {
        }
    }

    void createSearchSession(SearchContext searchContext, SearchSessionId searchSessionId, IBinder iBinder) throws RemoteException;

    void destroySearchSession(SearchSessionId searchSessionId) throws RemoteException;

    void notifyEvent(SearchSessionId searchSessionId, Query query, SearchTargetEvent searchTargetEvent) throws RemoteException;

    void query(SearchSessionId searchSessionId, Query query, ISearchCallback iSearchCallback) throws RemoteException;

    void registerEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) throws RemoteException;

    void unregisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ISearchUiManager {
        static final int TRANSACTION_createSearchSession = 1;
        static final int TRANSACTION_destroySearchSession = 6;
        static final int TRANSACTION_notifyEvent = 3;
        static final int TRANSACTION_query = 2;
        static final int TRANSACTION_registerEmptyQueryResultUpdateCallback = 4;
        static final int TRANSACTION_unregisterEmptyQueryResultUpdateCallback = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, ISearchUiManager.DESCRIPTOR);
        }

        public static ISearchUiManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISearchUiManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISearchUiManager)) {
                return (ISearchUiManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createSearchSession";
                case 2:
                    return "query";
                case 3:
                    return "notifyEvent";
                case 4:
                    return "registerEmptyQueryResultUpdateCallback";
                case 5:
                    return "unregisterEmptyQueryResultUpdateCallback";
                case 6:
                    return "destroySearchSession";
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
                parcel.enforceInterface(ISearchUiManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISearchUiManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    SearchContext searchContext = (SearchContext) parcel.readTypedObject(SearchContext.CREATOR);
                    SearchSessionId searchSessionId = (SearchSessionId) parcel.readTypedObject(SearchSessionId.CREATOR);
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createSearchSession(searchContext, searchSessionId, strongBinder);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    SearchSessionId searchSessionId2 = (SearchSessionId) parcel.readTypedObject(SearchSessionId.CREATOR);
                    Query query = (Query) parcel.readTypedObject(Query.CREATOR);
                    ISearchCallback iSearchCallbackAsInterface = ISearchCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    query(searchSessionId2, query, iSearchCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    SearchSessionId searchSessionId3 = (SearchSessionId) parcel.readTypedObject(SearchSessionId.CREATOR);
                    Query query2 = (Query) parcel.readTypedObject(Query.CREATOR);
                    SearchTargetEvent searchTargetEvent = (SearchTargetEvent) parcel.readTypedObject(SearchTargetEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyEvent(searchSessionId3, query2, searchTargetEvent);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    SearchSessionId searchSessionId4 = (SearchSessionId) parcel.readTypedObject(SearchSessionId.CREATOR);
                    ISearchCallback iSearchCallbackAsInterface2 = ISearchCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerEmptyQueryResultUpdateCallback(searchSessionId4, iSearchCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    SearchSessionId searchSessionId5 = (SearchSessionId) parcel.readTypedObject(SearchSessionId.CREATOR);
                    ISearchCallback iSearchCallbackAsInterface3 = ISearchCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterEmptyQueryResultUpdateCallback(searchSessionId5, iSearchCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    SearchSessionId searchSessionId6 = (SearchSessionId) parcel.readTypedObject(SearchSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    destroySearchSession(searchSessionId6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISearchUiManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISearchUiManager.DESCRIPTOR;
            }

            @Override // android.app.search.ISearchUiManager
            public void createSearchSession(SearchContext searchContext, SearchSessionId searchSessionId, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISearchUiManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(searchContext, 0);
                    parcelObtain.writeTypedObject(searchSessionId, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.search.ISearchUiManager
            public void query(SearchSessionId searchSessionId, Query query, ISearchCallback iSearchCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISearchUiManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(searchSessionId, 0);
                    parcelObtain.writeTypedObject(query, 0);
                    parcelObtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.search.ISearchUiManager
            public void notifyEvent(SearchSessionId searchSessionId, Query query, SearchTargetEvent searchTargetEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISearchUiManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(searchSessionId, 0);
                    parcelObtain.writeTypedObject(query, 0);
                    parcelObtain.writeTypedObject(searchTargetEvent, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.search.ISearchUiManager
            public void registerEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISearchUiManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(searchSessionId, 0);
                    parcelObtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.search.ISearchUiManager
            public void unregisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISearchUiManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(searchSessionId, 0);
                    parcelObtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.search.ISearchUiManager
            public void destroySearchSession(SearchSessionId searchSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISearchUiManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(searchSessionId, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
