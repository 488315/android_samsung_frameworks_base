package android.system.keystore2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.system.keystore2.IKeystoreSecurityLevel;

/* loaded from: classes3.dex */
public interface IKeystoreService extends IInterface {
    public static final String DESCRIPTOR = "android$system$keystore2$IKeystoreService".replace('$', '.');
    public static final String HASH = "5648acc9b43590ef0a775b6f5c553831c20cccc4";
    public static final int VERSION = 4;

    void deleteKey(KeyDescriptor keyDescriptor) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    KeyEntryResponse getKeyEntry(KeyDescriptor keyDescriptor) throws RemoteException;

    int getNumberOfEntries(int i, long j) throws RemoteException;

    IKeystoreSecurityLevel getSecurityLevel(int i) throws RemoteException;

    KeyDescriptor grant(KeyDescriptor keyDescriptor, int i, int i2) throws RemoteException;

    @Deprecated
    KeyDescriptor[] listEntries(int i, long j) throws RemoteException;

    KeyDescriptor[] listEntriesBatched(int i, long j, String str) throws RemoteException;

    void ungrant(KeyDescriptor keyDescriptor, int i) throws RemoteException;

    void updateSubcomponent(KeyDescriptor keyDescriptor, byte[] bArr, byte[] bArr2) throws RemoteException;

    public static class Default implements IKeystoreService {
        @Override // android.system.keystore2.IKeystoreService
        public IKeystoreSecurityLevel getSecurityLevel(int securityLevel) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public KeyEntryResponse getKeyEntry(KeyDescriptor key) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public void updateSubcomponent(KeyDescriptor key, byte[] publicCert, byte[] certificateChain) throws RemoteException {
        }

        @Override // android.system.keystore2.IKeystoreService
        public KeyDescriptor[] listEntries(int domain, long nspace) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public void deleteKey(KeyDescriptor key) throws RemoteException {
        }

        @Override // android.system.keystore2.IKeystoreService
        public KeyDescriptor grant(KeyDescriptor key, int granteeUid, int accessVector) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public void ungrant(KeyDescriptor key, int granteeUid) throws RemoteException {
        }

        @Override // android.system.keystore2.IKeystoreService
        public int getNumberOfEntries(int domain, long nspace) throws RemoteException {
            return 0;
        }

        @Override // android.system.keystore2.IKeystoreService
        public KeyDescriptor[] listEntriesBatched(int domain, long nspace, String startingPastAlias) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.system.keystore2.IKeystoreService
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IKeystoreService {
        static final int TRANSACTION_deleteKey = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getKeyEntry = 2;
        static final int TRANSACTION_getNumberOfEntries = 8;
        static final int TRANSACTION_getSecurityLevel = 1;
        static final int TRANSACTION_grant = 6;
        static final int TRANSACTION_listEntries = 4;
        static final int TRANSACTION_listEntriesBatched = 9;
        static final int TRANSACTION_ungrant = 7;
        static final int TRANSACTION_updateSubcomponent = 3;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IKeystoreService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IKeystoreService)) {
                return (IKeystoreService) iin;
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
                    return "getSecurityLevel";
                case 2:
                    return "getKeyEntry";
                case 3:
                    return "updateSubcomponent";
                case 4:
                    return "listEntries";
                case 5:
                    return "deleteKey";
                case 6:
                    return "grant";
                case 7:
                    return "ungrant";
                case 8:
                    return "getNumberOfEntries";
                case 9:
                    return "listEntriesBatched";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == 16777215) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == 16777214) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    IKeystoreSecurityLevel _result = getSecurityLevel(_arg0);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result);
                    return true;
                case 2:
                    KeyDescriptor _arg02 = (KeyDescriptor) data.readTypedObject(KeyDescriptor.CREATOR);
                    data.enforceNoDataAvail();
                    KeyEntryResponse _result2 = getKeyEntry(_arg02);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    KeyDescriptor _arg03 = (KeyDescriptor) data.readTypedObject(KeyDescriptor.CREATOR);
                    byte[] _arg1 = data.createByteArray();
                    byte[] _arg2 = data.createByteArray();
                    data.enforceNoDataAvail();
                    updateSubcomponent(_arg03, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    long _arg12 = data.readLong();
                    data.enforceNoDataAvail();
                    KeyDescriptor[] _result3 = listEntries(_arg04, _arg12);
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case 5:
                    KeyDescriptor _arg05 = (KeyDescriptor) data.readTypedObject(KeyDescriptor.CREATOR);
                    data.enforceNoDataAvail();
                    deleteKey(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    KeyDescriptor _arg06 = (KeyDescriptor) data.readTypedObject(KeyDescriptor.CREATOR);
                    int _arg13 = data.readInt();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    KeyDescriptor _result4 = grant(_arg06, _arg13, _arg22);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 7:
                    KeyDescriptor _arg07 = (KeyDescriptor) data.readTypedObject(KeyDescriptor.CREATOR);
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    ungrant(_arg07, _arg14);
                    reply.writeNoException();
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    long _arg15 = data.readLong();
                    data.enforceNoDataAvail();
                    int _result5 = getNumberOfEntries(_arg08, _arg15);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    long _arg16 = data.readLong();
                    String _arg23 = data.readString();
                    data.enforceNoDataAvail();
                    KeyDescriptor[] _result6 = listEntriesBatched(_arg09, _arg16, _arg23);
                    reply.writeNoException();
                    reply.writeTypedArray(_result6, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKeystoreService {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.system.keystore2.IKeystoreService
            public IKeystoreSecurityLevel getSecurityLevel(int securityLevel) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(securityLevel);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getSecurityLevel is unimplemented.");
                    }
                    _reply.readException();
                    IKeystoreSecurityLevel _result = IKeystoreSecurityLevel.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public KeyEntryResponse getKeyEntry(KeyDescriptor key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(key, 0);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getKeyEntry is unimplemented.");
                    }
                    _reply.readException();
                    KeyEntryResponse _result = (KeyEntryResponse) _reply.readTypedObject(KeyEntryResponse.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public void updateSubcomponent(KeyDescriptor key, byte[] publicCert, byte[] certificateChain) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(key, 0);
                    _data.writeByteArray(publicCert);
                    _data.writeByteArray(certificateChain);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method updateSubcomponent is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public KeyDescriptor[] listEntries(int domain, long nspace) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(domain);
                    _data.writeLong(nspace);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method listEntries is unimplemented.");
                    }
                    _reply.readException();
                    KeyDescriptor[] _result = (KeyDescriptor[]) _reply.createTypedArray(KeyDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public void deleteKey(KeyDescriptor key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(key, 0);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method deleteKey is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public KeyDescriptor grant(KeyDescriptor key, int granteeUid, int accessVector) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(key, 0);
                    _data.writeInt(granteeUid);
                    _data.writeInt(accessVector);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method grant is unimplemented.");
                    }
                    _reply.readException();
                    KeyDescriptor _result = (KeyDescriptor) _reply.readTypedObject(KeyDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public void ungrant(KeyDescriptor key, int granteeUid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(key, 0);
                    _data.writeInt(granteeUid);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method ungrant is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public int getNumberOfEntries(int domain, long nspace) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(domain);
                    _data.writeLong(nspace);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getNumberOfEntries is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public KeyDescriptor[] listEntriesBatched(int domain, long nspace, String startingPastAlias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(domain);
                    _data.writeLong(nspace);
                    _data.writeString(startingPastAlias);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method listEntriesBatched is unimplemented.");
                    }
                    _reply.readException();
                    KeyDescriptor[] _result = (KeyDescriptor[]) _reply.createTypedArray(KeyDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.system.keystore2.IKeystoreService
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}
