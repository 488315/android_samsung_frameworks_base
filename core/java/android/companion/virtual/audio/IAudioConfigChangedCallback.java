package android.companion.virtual.audio;

import android.media.AudioPlaybackConfiguration;
import android.media.AudioRecordingConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface IAudioConfigChangedCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.companion.virtual.audio.IAudioConfigChangedCallback";

    void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> list) throws RemoteException;

    void onRecordingConfigChanged(List<AudioRecordingConfiguration> list) throws RemoteException;

    public static class Default implements IAudioConfigChangedCallback {
        @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
        public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> configs)
                throws RemoteException {}

        @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
        public void onRecordingConfigChanged(List<AudioRecordingConfiguration> configs)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IAudioConfigChangedCallback {
        static final int TRANSACTION_onPlaybackConfigChanged = 1;
        static final int TRANSACTION_onRecordingConfigChanged = 2;

        public Stub() {
            attachInterface(this, IAudioConfigChangedCallback.DESCRIPTOR);
        }

        public static IAudioConfigChangedCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAudioConfigChangedCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IAudioConfigChangedCallback)) {
                return (IAudioConfigChangedCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onPlaybackConfigChanged";
                case 2:
                    return "onRecordingConfigChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IAudioConfigChangedCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAudioConfigChangedCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<AudioPlaybackConfiguration> _arg0 =
                            data.createTypedArrayList(AudioPlaybackConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    onPlaybackConfigChanged(_arg0);
                    return true;
                case 2:
                    List<AudioRecordingConfiguration> _arg02 =
                            data.createTypedArrayList(AudioRecordingConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    onRecordingConfigChanged(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAudioConfigChangedCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAudioConfigChangedCallback.DESCRIPTOR;
            }

            @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
            public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> configs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAudioConfigChangedCallback.DESCRIPTOR);
                    _data.writeTypedList(configs, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
            public void onRecordingConfigChanged(List<AudioRecordingConfiguration> configs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAudioConfigChangedCallback.DESCRIPTOR);
                    _data.writeTypedList(configs, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
