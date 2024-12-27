package android.app;

import android.content.AttributionSource;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IGrammaticalInflectionManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.IGrammaticalInflectionManager";

    int getSystemGrammaticalGender(AttributionSource attributionSource, int i)
            throws RemoteException;

    int peekSystemGrammaticalGenderByUserId(AttributionSource attributionSource, int i)
            throws RemoteException;

    void setRequestedApplicationGrammaticalGender(String str, int i, int i2) throws RemoteException;

    void setSystemWideGrammaticalGender(int i, int i2) throws RemoteException;

    public static class Default implements IGrammaticalInflectionManager {
        @Override // android.app.IGrammaticalInflectionManager
        public void setRequestedApplicationGrammaticalGender(
                String appPackageName, int userId, int gender) throws RemoteException {}

        @Override // android.app.IGrammaticalInflectionManager
        public void setSystemWideGrammaticalGender(int gender, int userId) throws RemoteException {}

        @Override // android.app.IGrammaticalInflectionManager
        public int getSystemGrammaticalGender(AttributionSource attributionSource, int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IGrammaticalInflectionManager
        public int peekSystemGrammaticalGenderByUserId(
                AttributionSource attributionSource, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IGrammaticalInflectionManager {
        static final int TRANSACTION_getSystemGrammaticalGender = 3;
        static final int TRANSACTION_peekSystemGrammaticalGenderByUserId = 4;
        static final int TRANSACTION_setRequestedApplicationGrammaticalGender = 1;
        static final int TRANSACTION_setSystemWideGrammaticalGender = 2;

        public Stub() {
            attachInterface(this, IGrammaticalInflectionManager.DESCRIPTOR);
        }

        public static IGrammaticalInflectionManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGrammaticalInflectionManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IGrammaticalInflectionManager)) {
                return (IGrammaticalInflectionManager) iin;
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
                    return "setRequestedApplicationGrammaticalGender";
                case 2:
                    return "setSystemWideGrammaticalGender";
                case 3:
                    return "getSystemGrammaticalGender";
                case 4:
                    return "peekSystemGrammaticalGenderByUserId";
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
                data.enforceInterface(IGrammaticalInflectionManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGrammaticalInflectionManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    setRequestedApplicationGrammaticalGender(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    setSystemWideGrammaticalGender(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 3:
                    AttributionSource _arg03 =
                            (AttributionSource) data.readTypedObject(AttributionSource.CREATOR);
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result = getSystemGrammaticalGender(_arg03, _arg13);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 4:
                    AttributionSource _arg04 =
                            (AttributionSource) data.readTypedObject(AttributionSource.CREATOR);
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result2 = peekSystemGrammaticalGenderByUserId(_arg04, _arg14);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGrammaticalInflectionManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGrammaticalInflectionManager.DESCRIPTOR;
            }

            @Override // android.app.IGrammaticalInflectionManager
            public void setRequestedApplicationGrammaticalGender(
                    String appPackageName, int userId, int gender) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGrammaticalInflectionManager.DESCRIPTOR);
                    _data.writeString(appPackageName);
                    _data.writeInt(userId);
                    _data.writeInt(gender);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGrammaticalInflectionManager
            public void setSystemWideGrammaticalGender(int gender, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGrammaticalInflectionManager.DESCRIPTOR);
                    _data.writeInt(gender);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGrammaticalInflectionManager
            public int getSystemGrammaticalGender(AttributionSource attributionSource, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGrammaticalInflectionManager.DESCRIPTOR);
                    _data.writeTypedObject(attributionSource, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGrammaticalInflectionManager
            public int peekSystemGrammaticalGenderByUserId(
                    AttributionSource attributionSource, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGrammaticalInflectionManager.DESCRIPTOR);
                    _data.writeTypedObject(attributionSource, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
