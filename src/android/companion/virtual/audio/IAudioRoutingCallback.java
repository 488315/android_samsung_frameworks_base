package android.companion.virtual.audio;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAudioRoutingCallback extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtual.audio.IAudioRoutingCallback";

    public static class Default implements IAudioRoutingCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.virtual.audio.IAudioRoutingCallback
        public void onAppsNeedingAudioRoutingChanged(int[] iArr) throws RemoteException {
        }
    }

    void onAppsNeedingAudioRoutingChanged(int[] iArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IAudioRoutingCallback {
        static final int TRANSACTION_onAppsNeedingAudioRoutingChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAudioRoutingCallback.DESCRIPTOR);
        }

        public static IAudioRoutingCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAudioRoutingCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAudioRoutingCallback)) {
                return (IAudioRoutingCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAppsNeedingAudioRoutingChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAudioRoutingCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAudioRoutingCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int[] createIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                onAppsNeedingAudioRoutingChanged(createIntArray);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAudioRoutingCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAudioRoutingCallback.DESCRIPTOR;
            }

            @Override // android.companion.virtual.audio.IAudioRoutingCallback
            public void onAppsNeedingAudioRoutingChanged(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAudioRoutingCallback.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
