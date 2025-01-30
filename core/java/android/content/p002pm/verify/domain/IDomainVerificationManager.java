package android.content.p002pm.verify.domain;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IDomainVerificationManager extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.verify.domain.IDomainVerificationManager";

    DomainVerificationInfo getDomainVerificationInfo(String str) throws RemoteException;

    DomainVerificationUserState getDomainVerificationUserState(String str, int i) throws RemoteException;

    List<DomainOwner> getOwnersForDomain(String str, int i) throws RemoteException;

    List<String> queryValidVerificationPackageNames() throws RemoteException;

    void setDomainVerificationLinkHandlingAllowed(String str, boolean z, int i) throws RemoteException;

    int setDomainVerificationStatus(String str, DomainSet domainSet, int i) throws RemoteException;

    int setDomainVerificationUserSelection(String str, DomainSet domainSet, boolean z, int i) throws RemoteException;

    public static class Default implements IDomainVerificationManager {
        @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
        public List<String> queryValidVerificationPackageNames() throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
        public DomainVerificationInfo getDomainVerificationInfo(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
        public DomainVerificationUserState getDomainVerificationUserState(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
        public List<DomainOwner> getOwnersForDomain(String domain, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
        public int setDomainVerificationStatus(String domainSetId, DomainSet domains, int state) throws RemoteException {
            return 0;
        }

        @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
        public void setDomainVerificationLinkHandlingAllowed(String packageName, boolean allowed, int userId) throws RemoteException {
        }

        @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
        public int setDomainVerificationUserSelection(String domainSetId, DomainSet domains, boolean enabled, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDomainVerificationManager {
        static final int TRANSACTION_getDomainVerificationInfo = 2;
        static final int TRANSACTION_getDomainVerificationUserState = 3;
        static final int TRANSACTION_getOwnersForDomain = 4;
        static final int TRANSACTION_queryValidVerificationPackageNames = 1;
        static final int TRANSACTION_setDomainVerificationLinkHandlingAllowed = 6;
        static final int TRANSACTION_setDomainVerificationStatus = 5;
        static final int TRANSACTION_setDomainVerificationUserSelection = 7;

        public Stub() {
            attachInterface(this, IDomainVerificationManager.DESCRIPTOR);
        }

        public static IDomainVerificationManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDomainVerificationManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IDomainVerificationManager)) {
                return (IDomainVerificationManager) iin;
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
                    return "queryValidVerificationPackageNames";
                case 2:
                    return "getDomainVerificationInfo";
                case 3:
                    return "getDomainVerificationUserState";
                case 4:
                    return "getOwnersForDomain";
                case 5:
                    return "setDomainVerificationStatus";
                case 6:
                    return "setDomainVerificationLinkHandlingAllowed";
                case 7:
                    return "setDomainVerificationUserSelection";
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
                data.enforceInterface(IDomainVerificationManager.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IDomainVerificationManager.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            List<String> _result = queryValidVerificationPackageNames();
                            reply.writeNoException();
                            reply.writeStringList(_result);
                            return true;
                        case 2:
                            String _arg0 = data.readString();
                            data.enforceNoDataAvail();
                            DomainVerificationInfo _result2 = getDomainVerificationInfo(_arg0);
                            reply.writeNoException();
                            reply.writeTypedObject(_result2, 1);
                            return true;
                        case 3:
                            String _arg02 = data.readString();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            DomainVerificationUserState _result3 = getDomainVerificationUserState(_arg02, _arg1);
                            reply.writeNoException();
                            reply.writeTypedObject(_result3, 1);
                            return true;
                        case 4:
                            String _arg03 = data.readString();
                            int _arg12 = data.readInt();
                            data.enforceNoDataAvail();
                            List<DomainOwner> _result4 = getOwnersForDomain(_arg03, _arg12);
                            reply.writeNoException();
                            reply.writeTypedList(_result4, 1);
                            return true;
                        case 5:
                            String _arg04 = data.readString();
                            DomainSet _arg13 = (DomainSet) data.readTypedObject(DomainSet.CREATOR);
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result5 = setDomainVerificationStatus(_arg04, _arg13, _arg2);
                            reply.writeNoException();
                            reply.writeInt(_result5);
                            return true;
                        case 6:
                            String _arg05 = data.readString();
                            boolean _arg14 = data.readBoolean();
                            int _arg22 = data.readInt();
                            data.enforceNoDataAvail();
                            setDomainVerificationLinkHandlingAllowed(_arg05, _arg14, _arg22);
                            reply.writeNoException();
                            return true;
                        case 7:
                            String _arg06 = data.readString();
                            DomainSet _arg15 = (DomainSet) data.readTypedObject(DomainSet.CREATOR);
                            boolean _arg23 = data.readBoolean();
                            int _arg3 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result6 = setDomainVerificationUserSelection(_arg06, _arg15, _arg23, _arg3);
                            reply.writeNoException();
                            reply.writeInt(_result6);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IDomainVerificationManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDomainVerificationManager.DESCRIPTOR;
            }

            @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
            public List<String> queryValidVerificationPackageNames() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
            public DomainVerificationInfo getDomainVerificationInfo(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    DomainVerificationInfo _result = (DomainVerificationInfo) _reply.readTypedObject(DomainVerificationInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
            public DomainVerificationUserState getDomainVerificationUserState(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    DomainVerificationUserState _result = (DomainVerificationUserState) _reply.readTypedObject(DomainVerificationUserState.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
            public List<DomainOwner> getOwnersForDomain(String domain, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    _data.writeString(domain);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    List<DomainOwner> _result = _reply.createTypedArrayList(DomainOwner.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
            public int setDomainVerificationStatus(String domainSetId, DomainSet domains, int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    _data.writeString(domainSetId);
                    _data.writeTypedObject(domains, 0);
                    _data.writeInt(state);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
            public void setDomainVerificationLinkHandlingAllowed(String packageName, boolean allowed, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(allowed);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.verify.domain.IDomainVerificationManager
            public int setDomainVerificationUserSelection(String domainSetId, DomainSet domains, boolean enabled, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    _data.writeString(domainSetId);
                    _data.writeTypedObject(domains, 0);
                    _data.writeBoolean(enabled);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
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
