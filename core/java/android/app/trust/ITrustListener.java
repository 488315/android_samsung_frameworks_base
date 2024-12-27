package android.app.trust;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import java.util.List;

/* loaded from: classes.dex */
public interface ITrustListener extends IInterface {
    void onEnabledTrustAgentsChanged(int i) throws RemoteException;

    void onIsActiveUnlockRunningChanged(boolean z, int i) throws RemoteException;

    void onTrustChanged(boolean z, boolean z2, int i, int i2, List<String> list) throws RemoteException;

    void onTrustError(CharSequence charSequence) throws RemoteException;

    void onTrustManagedChanged(boolean z, int i) throws RemoteException;

    public static class Default implements ITrustListener {
        @Override // android.app.trust.ITrustListener
        public void onEnabledTrustAgentsChanged(int userId) throws RemoteException {
        }

        @Override // android.app.trust.ITrustListener
        public void onTrustChanged(boolean enabled, boolean newlyUnlocked, int userId, int flags, List<String> trustGrantedMessages) throws RemoteException {
        }

        @Override // android.app.trust.ITrustListener
        public void onTrustManagedChanged(boolean managed, int userId) throws RemoteException {
        }

        @Override // android.app.trust.ITrustListener
        public void onTrustError(CharSequence message) throws RemoteException {
        }

        @Override // android.app.trust.ITrustListener
        public void onIsActiveUnlockRunningChanged(boolean isRunning, int userId) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITrustListener {
        public static final String DESCRIPTOR = "android.app.trust.ITrustListener";
        static final int TRANSACTION_onEnabledTrustAgentsChanged = 1;
        static final int TRANSACTION_onIsActiveUnlockRunningChanged = 5;
        static final int TRANSACTION_onTrustChanged = 2;
        static final int TRANSACTION_onTrustError = 4;
        static final int TRANSACTION_onTrustManagedChanged = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITrustListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ITrustListener)) {
                return (ITrustListener) iin;
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
                    return "onEnabledTrustAgentsChanged";
                case 2:
                    return "onTrustChanged";
                case 3:
                    return "onTrustManagedChanged";
                case 4:
                    return "onTrustError";
                case 5:
                    return "onIsActiveUnlockRunningChanged";
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
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onEnabledTrustAgentsChanged(_arg0);
                    return true;
                case 2:
                    boolean _arg02 = data.readBoolean();
                    boolean _arg1 = data.readBoolean();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    List<String> _arg4 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    onTrustChanged(_arg02, _arg1, _arg2, _arg3, _arg4);
                    return true;
                case 3:
                    boolean _arg03 = data.readBoolean();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    onTrustManagedChanged(_arg03, _arg12);
                    return true;
                case 4:
                    CharSequence _arg04 = (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    onTrustError(_arg04);
                    return true;
                case 5:
                    boolean _arg05 = data.readBoolean();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    onIsActiveUnlockRunningChanged(_arg05, _arg13);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITrustListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.trust.ITrustListener
            public void onEnabledTrustAgentsChanged(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.trust.ITrustListener
            public void onTrustChanged(boolean enabled, boolean newlyUnlocked, int userId, int flags, List<String> trustGrantedMessages) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    _data.writeBoolean(newlyUnlocked);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    _data.writeStringList(trustGrantedMessages);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.trust.ITrustListener
            public void onTrustManagedChanged(boolean managed, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(managed);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.trust.ITrustListener
            public void onTrustError(CharSequence message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.trust.ITrustListener
            public void onIsActiveUnlockRunningChanged(boolean isRunning, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isRunning);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
