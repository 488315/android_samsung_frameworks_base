package android.media.quality;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IAmbientBacklightCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.quality.IAmbientBacklightCallback";

    public static class Default implements IAmbientBacklightCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.quality.IAmbientBacklightCallback
        public void onAmbientBacklightEvent(AmbientBacklightEvent ambientBacklightEvent) throws RemoteException {
        }
    }

    void onAmbientBacklightEvent(AmbientBacklightEvent ambientBacklightEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements IAmbientBacklightCallback {
        static final int TRANSACTION_onAmbientBacklightEvent = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IAmbientBacklightCallback.DESCRIPTOR);
        }

        public static IAmbientBacklightCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAmbientBacklightCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAmbientBacklightCallback)) {
                return (IAmbientBacklightCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAmbientBacklightCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAmbientBacklightCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AmbientBacklightEvent ambientBacklightEvent = (AmbientBacklightEvent) parcel.readTypedObject(AmbientBacklightEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onAmbientBacklightEvent(ambientBacklightEvent);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAmbientBacklightCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAmbientBacklightCallback.DESCRIPTOR;
            }

            @Override // android.media.quality.IAmbientBacklightCallback
            public void onAmbientBacklightEvent(AmbientBacklightEvent ambientBacklightEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAmbientBacklightCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(ambientBacklightEvent, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
