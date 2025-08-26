package android.media.soundtrigger_middleware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IInjectModelEvent extends IInterface {
    public static final String DESCRIPTOR = "android.media.soundtrigger_middleware.IInjectModelEvent";

    public static class Default implements IInjectModelEvent {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.IInjectModelEvent
        public void triggerUnloadModel() throws RemoteException {
        }
    }

    void triggerUnloadModel() throws RemoteException;

    public static abstract class Stub extends Binder implements IInjectModelEvent {
        static final int TRANSACTION_triggerUnloadModel = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IInjectModelEvent.DESCRIPTOR);
        }

        public static IInjectModelEvent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInjectModelEvent.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInjectModelEvent)) {
                return (IInjectModelEvent) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInjectModelEvent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInjectModelEvent.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                triggerUnloadModel();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IInjectModelEvent {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInjectModelEvent.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
