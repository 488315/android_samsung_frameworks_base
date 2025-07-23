package android.media.soundtrigger_middleware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IAcknowledgeEvent extends IInterface {
    public static final String DESCRIPTOR = "android.media.soundtrigger_middleware.IAcknowledgeEvent";

    public static class Default implements IAcknowledgeEvent {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.IAcknowledgeEvent
        public void eventReceived() throws RemoteException {
        }
    }

    void eventReceived() throws RemoteException;

    public static abstract class Stub extends Binder implements IAcknowledgeEvent {
        static final int TRANSACTION_eventReceived = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IAcknowledgeEvent.DESCRIPTOR);
        }

        public static IAcknowledgeEvent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAcknowledgeEvent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAcknowledgeEvent)) {
                return (IAcknowledgeEvent) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAcknowledgeEvent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAcknowledgeEvent.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                eventReceived();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAcknowledgeEvent {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAcknowledgeEvent.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
