package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.samsung.android.knox.SemPersonaState;

/* loaded from: classes.dex */
public interface ISystemPersonaObserver extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.ISystemPersonaObserver";

    void onKnoxContainerLaunch(int i) throws RemoteException;

    void onPersonaActive(int i) throws RemoteException;

    void onRemovePersona(int i) throws RemoteException;

    void onResetPersona(int i) throws RemoteException;

    void onStateChange(int i, SemPersonaState semPersonaState, SemPersonaState semPersonaState2)
            throws RemoteException;

    public static class Default implements ISystemPersonaObserver {
        @Override // android.content.pm.ISystemPersonaObserver
        public void onPersonaActive(int personaId) throws RemoteException {}

        @Override // android.content.pm.ISystemPersonaObserver
        public void onRemovePersona(int personaId) throws RemoteException {}

        @Override // android.content.pm.ISystemPersonaObserver
        public void onResetPersona(int personaId) throws RemoteException {}

        @Override // android.content.pm.ISystemPersonaObserver
        public void onKnoxContainerLaunch(int personaId) throws RemoteException {}

        @Override // android.content.pm.ISystemPersonaObserver
        public void onStateChange(int personaId, SemPersonaState oldState, SemPersonaState newState)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISystemPersonaObserver {
        static final int TRANSACTION_onKnoxContainerLaunch = 4;
        static final int TRANSACTION_onPersonaActive = 1;
        static final int TRANSACTION_onRemovePersona = 2;
        static final int TRANSACTION_onResetPersona = 3;
        static final int TRANSACTION_onStateChange = 5;

        public Stub() {
            attachInterface(this, ISystemPersonaObserver.DESCRIPTOR);
        }

        public static ISystemPersonaObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISystemPersonaObserver.DESCRIPTOR);
            if (iin != null && (iin instanceof ISystemPersonaObserver)) {
                return (ISystemPersonaObserver) iin;
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
                    return "onPersonaActive";
                case 2:
                    return "onRemovePersona";
                case 3:
                    return "onResetPersona";
                case 4:
                    return "onKnoxContainerLaunch";
                case 5:
                    return "onStateChange";
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
                data.enforceInterface(ISystemPersonaObserver.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISystemPersonaObserver.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onPersonaActive(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onRemovePersona(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    onResetPersona(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    onKnoxContainerLaunch(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    SemPersonaState _arg1 =
                            (SemPersonaState) data.readTypedObject(SemPersonaState.CREATOR);
                    SemPersonaState _arg2 =
                            (SemPersonaState) data.readTypedObject(SemPersonaState.CREATOR);
                    data.enforceNoDataAvail();
                    onStateChange(_arg05, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISystemPersonaObserver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISystemPersonaObserver.DESCRIPTOR;
            }

            @Override // android.content.pm.ISystemPersonaObserver
            public void onPersonaActive(int personaId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemPersonaObserver.DESCRIPTOR);
                    _data.writeInt(personaId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.ISystemPersonaObserver
            public void onRemovePersona(int personaId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemPersonaObserver.DESCRIPTOR);
                    _data.writeInt(personaId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.ISystemPersonaObserver
            public void onResetPersona(int personaId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemPersonaObserver.DESCRIPTOR);
                    _data.writeInt(personaId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.ISystemPersonaObserver
            public void onKnoxContainerLaunch(int personaId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemPersonaObserver.DESCRIPTOR);
                    _data.writeInt(personaId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.ISystemPersonaObserver
            public void onStateChange(
                    int personaId, SemPersonaState oldState, SemPersonaState newState)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISystemPersonaObserver.DESCRIPTOR);
                    _data.writeInt(personaId);
                    _data.writeTypedObject(oldState, 0);
                    _data.writeTypedObject(newState, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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
