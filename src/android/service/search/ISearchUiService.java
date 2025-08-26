package android.service.search;

import android.app.search.ISearchCallback;
import android.app.search.Query;
import android.app.search.SearchContext;
import android.app.search.SearchSessionId;
import android.app.search.SearchTargetEvent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISearchUiService extends IInterface {
    public static final String DESCRIPTOR = "android.service.search.ISearchUiService";

    public static class Default implements ISearchUiService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.search.ISearchUiService
        public void onCreateSearchSession(SearchContext searchContext, SearchSessionId searchSessionId) throws RemoteException {
        }

        @Override // android.service.search.ISearchUiService
        public void onDestroy(SearchSessionId searchSessionId) throws RemoteException {
        }

        @Override // android.service.search.ISearchUiService
        public void onNotifyEvent(SearchSessionId searchSessionId, Query query, SearchTargetEvent searchTargetEvent) throws RemoteException {
        }

        @Override // android.service.search.ISearchUiService
        public void onQuery(SearchSessionId searchSessionId, Query query, ISearchCallback iSearchCallback) throws RemoteException {
        }

        @Override // android.service.search.ISearchUiService
        public void onRegisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) throws RemoteException {
        }

        @Override // android.service.search.ISearchUiService
        public void onUnregisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) throws RemoteException {
        }
    }

    void onCreateSearchSession(SearchContext searchContext, SearchSessionId searchSessionId) throws RemoteException;

    void onDestroy(SearchSessionId searchSessionId) throws RemoteException;

    void onNotifyEvent(SearchSessionId searchSessionId, Query query, SearchTargetEvent searchTargetEvent) throws RemoteException;

    void onQuery(SearchSessionId searchSessionId, Query query, ISearchCallback iSearchCallback) throws RemoteException;

    void onRegisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) throws RemoteException;

    void onUnregisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ISearchUiService {
        static final int TRANSACTION_onCreateSearchSession = 1;
        static final int TRANSACTION_onDestroy = 6;
        static final int TRANSACTION_onNotifyEvent = 3;
        static final int TRANSACTION_onQuery = 2;
        static final int TRANSACTION_onRegisterEmptyQueryResultUpdateCallback = 4;
        static final int TRANSACTION_onUnregisterEmptyQueryResultUpdateCallback = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, ISearchUiService.DESCRIPTOR);
        }

        public static ISearchUiService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISearchUiService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISearchUiService)) {
                return (ISearchUiService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCreateSearchSession";
                case 2:
                    return "onQuery";
                case 3:
                    return "onNotifyEvent";
                case 4:
                    return "onRegisterEmptyQueryResultUpdateCallback";
                case 5:
                    return "onUnregisterEmptyQueryResultUpdateCallback";
                case 6:
                    return "onDestroy";
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
                parcel.enforceInterface(ISearchUiService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISearchUiService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    SearchContext searchContext = (SearchContext) parcel.readTypedObject(SearchContext.CREATOR);
                    SearchSessionId searchSessionId = (SearchSessionId) parcel.readTypedObject(SearchSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCreateSearchSession(searchContext, searchSessionId);
                    return true;
                case 2:
                    SearchSessionId searchSessionId2 = (SearchSessionId) parcel.readTypedObject(SearchSessionId.CREATOR);
                    Query query = (Query) parcel.readTypedObject(Query.CREATOR);
                    ISearchCallback iSearchCallbackAsInterface = ISearchCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onQuery(searchSessionId2, query, iSearchCallbackAsInterface);
                    return true;
                case 3:
                    SearchSessionId searchSessionId3 = (SearchSessionId) parcel.readTypedObject(SearchSessionId.CREATOR);
                    Query query2 = (Query) parcel.readTypedObject(Query.CREATOR);
                    SearchTargetEvent searchTargetEvent = (SearchTargetEvent) parcel.readTypedObject(SearchTargetEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotifyEvent(searchSessionId3, query2, searchTargetEvent);
                    return true;
                case 4:
                    SearchSessionId searchSessionId4 = (SearchSessionId) parcel.readTypedObject(SearchSessionId.CREATOR);
                    ISearchCallback iSearchCallbackAsInterface2 = ISearchCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRegisterEmptyQueryResultUpdateCallback(searchSessionId4, iSearchCallbackAsInterface2);
                    return true;
                case 5:
                    SearchSessionId searchSessionId5 = (SearchSessionId) parcel.readTypedObject(SearchSessionId.CREATOR);
                    ISearchCallback iSearchCallbackAsInterface3 = ISearchCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onUnregisterEmptyQueryResultUpdateCallback(searchSessionId5, iSearchCallbackAsInterface3);
                    return true;
                case 6:
                    SearchSessionId searchSessionId6 = (SearchSessionId) parcel.readTypedObject(SearchSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDestroy(searchSessionId6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISearchUiService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISearchUiService.DESCRIPTOR;
            }

            @Override // android.service.search.ISearchUiService
            public void onCreateSearchSession(SearchContext searchContext, SearchSessionId searchSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISearchUiService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(searchContext, 0);
                    parcelObtain.writeTypedObject(searchSessionId, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.search.ISearchUiService
            public void onQuery(SearchSessionId searchSessionId, Query query, ISearchCallback iSearchCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISearchUiService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(searchSessionId, 0);
                    parcelObtain.writeTypedObject(query, 0);
                    parcelObtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.search.ISearchUiService
            public void onNotifyEvent(SearchSessionId searchSessionId, Query query, SearchTargetEvent searchTargetEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISearchUiService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(searchSessionId, 0);
                    parcelObtain.writeTypedObject(query, 0);
                    parcelObtain.writeTypedObject(searchTargetEvent, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.search.ISearchUiService
            public void onRegisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISearchUiService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(searchSessionId, 0);
                    parcelObtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.search.ISearchUiService
            public void onUnregisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISearchUiService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(searchSessionId, 0);
                    parcelObtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.search.ISearchUiService
            public void onDestroy(SearchSessionId searchSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISearchUiService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(searchSessionId, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
