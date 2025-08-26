package android.companion.virtual.audio;

import android.media.AudioPlaybackConfiguration;
import android.media.AudioRecordingConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IAudioConfigChangedCallback extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtual.audio.IAudioConfigChangedCallback";

    public static class Default implements IAudioConfigChangedCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
        public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> list) throws RemoteException {
        }

        @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
        public void onRecordingConfigChanged(List<AudioRecordingConfiguration> list) throws RemoteException {
        }
    }

    void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> list) throws RemoteException;

    void onRecordingConfigChanged(List<AudioRecordingConfiguration> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IAudioConfigChangedCallback {
        static final int TRANSACTION_onPlaybackConfigChanged = 1;
        static final int TRANSACTION_onRecordingConfigChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IAudioConfigChangedCallback.DESCRIPTOR);
        }

        public static IAudioConfigChangedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAudioConfigChangedCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAudioConfigChangedCallback)) {
                return (IAudioConfigChangedCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onPlaybackConfigChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onRecordingConfigChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAudioConfigChangedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAudioConfigChangedCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AudioPlaybackConfiguration.CREATOR);
                parcel.enforceNoDataAvail();
                onPlaybackConfigChanged(arrayListCreateTypedArrayList);
            } else if (i == 2) {
                ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(AudioRecordingConfiguration.CREATOR);
                parcel.enforceNoDataAvail();
                onRecordingConfigChanged(arrayListCreateTypedArrayList2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAudioConfigChangedCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAudioConfigChangedCallback.DESCRIPTOR;
            }

            @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
            public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioConfigChangedCallback.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
            public void onRecordingConfigChanged(List<AudioRecordingConfiguration> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioConfigChangedCallback.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
