package android.media.soundtrigger_middleware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IAcknowledgeEvent extends IInterface {
    public static final String DESCRIPTOR = "android.media.soundtrigger_middleware.IAcknowledgeEvent";

    void eventReceived() throws RemoteException;

    public static class Default implements IAcknowledgeEvent {
        @Override // android.media.soundtrigger_middleware.IAcknowledgeEvent
        public void eventReceived() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAcknowledgeEvent {
        static final int TRANSACTION_eventReceived = 1;

        public Stub() {
            attachInterface(this, IAcknowledgeEvent.DESCRIPTOR);
        }

        public static IAcknowledgeEvent asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAcknowledgeEvent.DESCRIPTOR);
            if (iin != null && (iin instanceof IAcknowledgeEvent)) {
                return (IAcknowledgeEvent) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IAcknowledgeEvent.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAcknowledgeEvent.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    eventReceived();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAcknowledgeEvent {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAcknowledgeEvent.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.IAcknowledgeEvent
            public void eventReceived() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAcknowledgeEvent.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
