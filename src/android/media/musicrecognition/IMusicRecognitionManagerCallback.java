package android.media.musicrecognition;

import android.media.MediaMetadata;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMusicRecognitionManagerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.musicrecognition.IMusicRecognitionManagerCallback";

    public static class Default implements IMusicRecognitionManagerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
        public void onAudioStreamClosed() throws RemoteException {
        }

        @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
        public void onRecognitionFailed(int i) throws RemoteException {
        }

        @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
        public void onRecognitionSucceeded(MediaMetadata mediaMetadata, Bundle bundle) throws RemoteException {
        }
    }

    void onAudioStreamClosed() throws RemoteException;

    void onRecognitionFailed(int i) throws RemoteException;

    void onRecognitionSucceeded(MediaMetadata mediaMetadata, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IMusicRecognitionManagerCallback {
        static final int TRANSACTION_onAudioStreamClosed = 3;
        static final int TRANSACTION_onRecognitionFailed = 2;
        static final int TRANSACTION_onRecognitionSucceeded = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IMusicRecognitionManagerCallback.DESCRIPTOR);
        }

        public static IMusicRecognitionManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMusicRecognitionManagerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMusicRecognitionManagerCallback)) {
                return (IMusicRecognitionManagerCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onRecognitionSucceeded";
            }
            if (i == 2) {
                return "onRecognitionFailed";
            }
            if (i != 3) {
                return null;
            }
            return "onAudioStreamClosed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMusicRecognitionManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMusicRecognitionManagerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                MediaMetadata mediaMetadata = (MediaMetadata) parcel.readTypedObject(MediaMetadata.CREATOR);
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onRecognitionSucceeded(mediaMetadata, bundle);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onRecognitionFailed(readInt);
            } else if (i == 3) {
                onAudioStreamClosed();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMusicRecognitionManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMusicRecognitionManagerCallback.DESCRIPTOR;
            }

            @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
            public void onRecognitionSucceeded(MediaMetadata mediaMetadata, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMusicRecognitionManagerCallback.DESCRIPTOR);
                    obtain.writeTypedObject(mediaMetadata, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
            public void onRecognitionFailed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMusicRecognitionManagerCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
            public void onAudioStreamClosed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMusicRecognitionManagerCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
