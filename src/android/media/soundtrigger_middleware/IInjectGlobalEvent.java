package android.media.soundtrigger_middleware;

import android.media.soundtrigger_middleware.IAcknowledgeEvent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IInjectGlobalEvent extends IInterface {
    public static final String DESCRIPTOR = "android.media.soundtrigger_middleware.IInjectGlobalEvent";

    public static class Default implements IInjectGlobalEvent {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
        public void setResourceContention(boolean z, IAcknowledgeEvent iAcknowledgeEvent) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
        public void triggerOnResourcesAvailable() throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
        public void triggerRestart() throws RemoteException {
        }
    }

    void setResourceContention(boolean z, IAcknowledgeEvent iAcknowledgeEvent) throws RemoteException;

    void triggerOnResourcesAvailable() throws RemoteException;

    void triggerRestart() throws RemoteException;

    public static abstract class Stub extends Binder implements IInjectGlobalEvent {
        static final int TRANSACTION_setResourceContention = 2;
        static final int TRANSACTION_triggerOnResourcesAvailable = 3;
        static final int TRANSACTION_triggerRestart = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IInjectGlobalEvent.DESCRIPTOR);
        }

        public static IInjectGlobalEvent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInjectGlobalEvent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInjectGlobalEvent)) {
                return (IInjectGlobalEvent) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInjectGlobalEvent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInjectGlobalEvent.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                triggerRestart();
            } else if (i == 2) {
                boolean readBoolean = parcel.readBoolean();
                IAcknowledgeEvent asInterface = IAcknowledgeEvent.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setResourceContention(readBoolean, asInterface);
            } else if (i == 3) {
                triggerOnResourcesAvailable();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInjectGlobalEvent {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInjectGlobalEvent.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
            public void setResourceContention(boolean z, IAcknowledgeEvent iAcknowledgeEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInjectGlobalEvent.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iAcknowledgeEvent);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
            public void triggerOnResourcesAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInjectGlobalEvent.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
