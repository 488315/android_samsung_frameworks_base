package android.media.audiopolicy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IAudioVolumeChangeDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.audiopolicy.IAudioVolumeChangeDispatcher";

    public static class Default implements IAudioVolumeChangeDispatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.audiopolicy.IAudioVolumeChangeDispatcher
        public void onAudioVolumeGroupChanged(int i, int i2) throws RemoteException {
        }
    }

    void onAudioVolumeGroupChanged(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IAudioVolumeChangeDispatcher {
        static final int TRANSACTION_onAudioVolumeGroupChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAudioVolumeChangeDispatcher.DESCRIPTOR);
        }

        public static IAudioVolumeChangeDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAudioVolumeChangeDispatcher.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAudioVolumeChangeDispatcher)) {
                return (IAudioVolumeChangeDispatcher) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAudioVolumeGroupChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAudioVolumeChangeDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAudioVolumeChangeDispatcher.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onAudioVolumeGroupChanged(i3, i4);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAudioVolumeChangeDispatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAudioVolumeChangeDispatcher.DESCRIPTOR;
            }

            @Override // android.media.audiopolicy.IAudioVolumeChangeDispatcher
            public void onAudioVolumeGroupChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioVolumeChangeDispatcher.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
