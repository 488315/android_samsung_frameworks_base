package android.media;

import android.media.audio.common.AudioVolumeGroupChangeEvent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface INativeAudioVolumeGroupCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.INativeAudioVolumeGroupCallback";

    public static class Default implements INativeAudioVolumeGroupCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.INativeAudioVolumeGroupCallback
        public void onAudioVolumeGroupChanged(AudioVolumeGroupChangeEvent audioVolumeGroupChangeEvent) throws RemoteException {
        }
    }

    void onAudioVolumeGroupChanged(AudioVolumeGroupChangeEvent audioVolumeGroupChangeEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements INativeAudioVolumeGroupCallback {
        static final int TRANSACTION_onAudioVolumeGroupChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, INativeAudioVolumeGroupCallback.DESCRIPTOR);
        }

        public static INativeAudioVolumeGroupCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(INativeAudioVolumeGroupCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INativeAudioVolumeGroupCallback)) {
                return (INativeAudioVolumeGroupCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INativeAudioVolumeGroupCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INativeAudioVolumeGroupCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AudioVolumeGroupChangeEvent audioVolumeGroupChangeEvent = (AudioVolumeGroupChangeEvent) parcel.readTypedObject(AudioVolumeGroupChangeEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onAudioVolumeGroupChanged(audioVolumeGroupChangeEvent);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements INativeAudioVolumeGroupCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INativeAudioVolumeGroupCallback.DESCRIPTOR;
            }

            @Override // android.media.INativeAudioVolumeGroupCallback
            public void onAudioVolumeGroupChanged(AudioVolumeGroupChangeEvent audioVolumeGroupChangeEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(INativeAudioVolumeGroupCallback.DESCRIPTOR);
                    obtain.writeTypedObject(audioVolumeGroupChangeEvent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
