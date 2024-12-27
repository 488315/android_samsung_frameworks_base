package android.media.soundtrigger_middleware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IInjectModelEvent extends IInterface {
    public static final String DESCRIPTOR =
            "android.media.soundtrigger_middleware.IInjectModelEvent";

    void triggerUnloadModel() throws RemoteException;

    public static class Default implements IInjectModelEvent {
        @Override // android.media.soundtrigger_middleware.IInjectModelEvent
        public void triggerUnloadModel() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IInjectModelEvent {
        static final int TRANSACTION_triggerUnloadModel = 1;

        public Stub() {
            attachInterface(this, IInjectModelEvent.DESCRIPTOR);
        }

        public static IInjectModelEvent asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInjectModelEvent.DESCRIPTOR);
            if (iin != null && (iin instanceof IInjectModelEvent)) {
                return (IInjectModelEvent) iin;
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
                data.enforceInterface(IInjectModelEvent.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IInjectModelEvent.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    triggerUnloadModel();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInjectModelEvent {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInjectModelEvent.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.IInjectModelEvent
            public void triggerUnloadModel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInjectModelEvent.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
