package android.media.soundtrigger_middleware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IInjectGlobalEvent extends IInterface {
    public static final String DESCRIPTOR =
            "android.media.soundtrigger_middleware.IInjectGlobalEvent";

    void setResourceContention(boolean z, IAcknowledgeEvent iAcknowledgeEvent)
            throws RemoteException;

    void triggerOnResourcesAvailable() throws RemoteException;

    void triggerRestart() throws RemoteException;

    public static class Default implements IInjectGlobalEvent {
        @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
        public void triggerRestart() throws RemoteException {}

        @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
        public void setResourceContention(boolean isContended, IAcknowledgeEvent callback)
                throws RemoteException {}

        @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
        public void triggerOnResourcesAvailable() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IInjectGlobalEvent {
        static final int TRANSACTION_setResourceContention = 2;
        static final int TRANSACTION_triggerOnResourcesAvailable = 3;
        static final int TRANSACTION_triggerRestart = 1;

        public Stub() {
            attachInterface(this, IInjectGlobalEvent.DESCRIPTOR);
        }

        public static IInjectGlobalEvent asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInjectGlobalEvent.DESCRIPTOR);
            if (iin != null && (iin instanceof IInjectGlobalEvent)) {
                return (IInjectGlobalEvent) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IInjectGlobalEvent.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IInjectGlobalEvent.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    triggerRestart();
                    return true;
                case 2:
                    boolean _arg0 = data.readBoolean();
                    IAcknowledgeEvent _arg1 =
                            IAcknowledgeEvent.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setResourceContention(_arg0, _arg1);
                    return true;
                case 3:
                    triggerOnResourcesAvailable();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInjectGlobalEvent {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInjectGlobalEvent.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
            public void triggerRestart() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInjectGlobalEvent.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
            public void setResourceContention(boolean isContended, IAcknowledgeEvent callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInjectGlobalEvent.DESCRIPTOR);
                    _data.writeBoolean(isContended);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
            public void triggerOnResourcesAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInjectGlobalEvent.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
