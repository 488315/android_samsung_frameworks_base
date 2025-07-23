package android.media.session;

import android.content.pm.ParceledListSlice;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public interface ISessionControllerCallback extends IInterface {

    public static class Default implements ISessionControllerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onEvent(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onExtrasChanged(Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onMetadataChanged(MediaMetadata mediaMetadata) throws RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onPlaybackStateChanged(PlaybackState playbackState) throws RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onQueueChanged(ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onSessionDestroyed() throws RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onVolumeInfoChanged(MediaController.PlaybackInfo playbackInfo) throws RemoteException {
        }
    }

    void onEvent(String str, Bundle bundle) throws RemoteException;

    void onExtrasChanged(Bundle bundle) throws RemoteException;

    void onMetadataChanged(MediaMetadata mediaMetadata) throws RemoteException;

    void onPlaybackStateChanged(PlaybackState playbackState) throws RemoteException;

    void onQueueChanged(ParceledListSlice parceledListSlice) throws RemoteException;

    void onQueueTitleChanged(CharSequence charSequence) throws RemoteException;

    void onSessionDestroyed() throws RemoteException;

    void onVolumeInfoChanged(MediaController.PlaybackInfo playbackInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements ISessionControllerCallback {
        public static final String DESCRIPTOR = "android.media.session.ISessionControllerCallback";
        static final int TRANSACTION_onEvent = 1;
        static final int TRANSACTION_onExtrasChanged = 7;
        static final int TRANSACTION_onMetadataChanged = 4;
        static final int TRANSACTION_onPlaybackStateChanged = 3;
        static final int TRANSACTION_onQueueChanged = 5;
        static final int TRANSACTION_onQueueTitleChanged = 6;
        static final int TRANSACTION_onSessionDestroyed = 2;
        static final int TRANSACTION_onVolumeInfoChanged = 8;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISessionControllerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISessionControllerCallback)) {
                return (ISessionControllerCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onEvent";
                case 2:
                    return "onSessionDestroyed";
                case 3:
                    return "onPlaybackStateChanged";
                case 4:
                    return "onMetadataChanged";
                case 5:
                    return "onQueueChanged";
                case 6:
                    return "onQueueTitleChanged";
                case 7:
                    return "onExtrasChanged";
                case 8:
                    return "onVolumeInfoChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEvent(readString, bundle);
                    return true;
                case 2:
                    onSessionDestroyed();
                    return true;
                case 3:
                    PlaybackState playbackState = (PlaybackState) parcel.readTypedObject(PlaybackState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPlaybackStateChanged(playbackState);
                    return true;
                case 4:
                    MediaMetadata mediaMetadata = (MediaMetadata) parcel.readTypedObject(MediaMetadata.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMetadataChanged(mediaMetadata);
                    return true;
                case 5:
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onQueueChanged(parceledListSlice);
                    return true;
                case 6:
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    onQueueTitleChanged(charSequence);
                    return true;
                case 7:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onExtrasChanged(bundle2);
                    return true;
                case 8:
                    MediaController.PlaybackInfo playbackInfo = (MediaController.PlaybackInfo) parcel.readTypedObject(MediaController.PlaybackInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onVolumeInfoChanged(playbackInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISessionControllerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onEvent(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onSessionDestroyed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onPlaybackStateChanged(PlaybackState playbackState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(playbackState, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onMetadataChanged(MediaMetadata mediaMetadata) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(mediaMetadata, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onQueueChanged(ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onVolumeInfoChanged(MediaController.PlaybackInfo playbackInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(playbackInfo, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
