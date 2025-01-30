package android.app;

import android.content.ComponentName;
import android.content.p002pm.ResolveInfo;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface ISearchManager extends IInterface {
    List<ResolveInfo> getGlobalSearchActivities() throws RemoteException;

    ComponentName getGlobalSearchActivity() throws RemoteException;

    SearchableInfo getSearchableInfo(ComponentName componentName) throws RemoteException;

    List<SearchableInfo> getSearchablesInGlobalSearch() throws RemoteException;

    List<SearchableInfo> getSearchablesInInsightSearch(boolean z) throws RemoteException;

    ComponentName getWebSearchActivity() throws RemoteException;

    void launchAssist(int i, Bundle bundle) throws RemoteException;

    public static class Default implements ISearchManager {
        @Override // android.app.ISearchManager
        public SearchableInfo getSearchableInfo(ComponentName launchActivity) throws RemoteException {
            return null;
        }

        @Override // android.app.ISearchManager
        public List<SearchableInfo> getSearchablesInGlobalSearch() throws RemoteException {
            return null;
        }

        @Override // android.app.ISearchManager
        public List<ResolveInfo> getGlobalSearchActivities() throws RemoteException {
            return null;
        }

        @Override // android.app.ISearchManager
        public ComponentName getGlobalSearchActivity() throws RemoteException {
            return null;
        }

        @Override // android.app.ISearchManager
        public ComponentName getWebSearchActivity() throws RemoteException {
            return null;
        }

        @Override // android.app.ISearchManager
        public void launchAssist(int userHandle, Bundle args) throws RemoteException {
        }

        @Override // android.app.ISearchManager
        public List<SearchableInfo> getSearchablesInInsightSearch(boolean includeGlobalSearch) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISearchManager {
        public static final String DESCRIPTOR = "android.app.ISearchManager";
        static final int TRANSACTION_getGlobalSearchActivities = 3;
        static final int TRANSACTION_getGlobalSearchActivity = 4;
        static final int TRANSACTION_getSearchableInfo = 1;
        static final int TRANSACTION_getSearchablesInGlobalSearch = 2;
        static final int TRANSACTION_getSearchablesInInsightSearch = 7;
        static final int TRANSACTION_getWebSearchActivity = 5;
        static final int TRANSACTION_launchAssist = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISearchManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISearchManager)) {
                return (ISearchManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getSearchableInfo";
                case 2:
                    return "getSearchablesInGlobalSearch";
                case 3:
                    return "getGlobalSearchActivities";
                case 4:
                    return "getGlobalSearchActivity";
                case 5:
                    return "getWebSearchActivity";
                case 6:
                    return "launchAssist";
                case 7:
                    return "getSearchablesInInsightSearch";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            SearchableInfo _result = getSearchableInfo(_arg0);
                            reply.writeNoException();
                            reply.writeTypedObject(_result, 1);
                            return true;
                        case 2:
                            List<SearchableInfo> _result2 = getSearchablesInGlobalSearch();
                            reply.writeNoException();
                            reply.writeTypedList(_result2, 1);
                            return true;
                        case 3:
                            List<ResolveInfo> _result3 = getGlobalSearchActivities();
                            reply.writeNoException();
                            reply.writeTypedList(_result3, 1);
                            return true;
                        case 4:
                            ComponentName _result4 = getGlobalSearchActivity();
                            reply.writeNoException();
                            reply.writeTypedObject(_result4, 1);
                            return true;
                        case 5:
                            ComponentName _result5 = getWebSearchActivity();
                            reply.writeNoException();
                            reply.writeTypedObject(_result5, 1);
                            return true;
                        case 6:
                            int _arg02 = data.readInt();
                            Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            data.enforceNoDataAvail();
                            launchAssist(_arg02, _arg1);
                            reply.writeNoException();
                            return true;
                        case 7:
                            boolean _arg03 = data.readBoolean();
                            data.enforceNoDataAvail();
                            List<SearchableInfo> _result6 = getSearchablesInInsightSearch(_arg03);
                            reply.writeNoException();
                            reply.writeTypedList(_result6, 1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISearchManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.ISearchManager
            public SearchableInfo getSearchableInfo(ComponentName launchActivity) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(launchActivity, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    SearchableInfo _result = (SearchableInfo) _reply.readTypedObject(SearchableInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ISearchManager
            public List<SearchableInfo> getSearchablesInGlobalSearch() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    List<SearchableInfo> _result = _reply.createTypedArrayList(SearchableInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ISearchManager
            public List<ResolveInfo> getGlobalSearchActivities() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    List<ResolveInfo> _result = _reply.createTypedArrayList(ResolveInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ISearchManager
            public ComponentName getGlobalSearchActivity() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ISearchManager
            public ComponentName getWebSearchActivity() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ISearchManager
            public void launchAssist(int userHandle, Bundle args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeTypedObject(args, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ISearchManager
            public List<SearchableInfo> getSearchablesInInsightSearch(boolean includeGlobalSearch) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(includeGlobalSearch);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    List<SearchableInfo> _result = _reply.createTypedArrayList(SearchableInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
