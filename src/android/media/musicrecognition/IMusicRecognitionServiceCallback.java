package android.media.musicrecognition;

import android.media.MediaMetadata;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMusicRecognitionServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.musicrecognition.IMusicRecognitionServiceCallback";

    public static class Default implements IMusicRecognitionServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.musicrecognition.IMusicRecognitionServiceCallback
        public void onRecognitionFailed(int i) throws RemoteException {
        }

        @Override // android.media.musicrecognition.IMusicRecognitionServiceCallback
        public void onRecognitionSucceeded(MediaMetadata mediaMetadata, Bundle bundle) throws RemoteException {
        }
    }

    void onRecognitionFailed(int i) throws RemoteException;

    void onRecognitionSucceeded(MediaMetadata mediaMetadata, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IMusicRecognitionServiceCallback {
        static final int TRANSACTION_onRecognitionFailed = 2;
        static final int TRANSACTION_onRecognitionSucceeded = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IMusicRecognitionServiceCallback.DESCRIPTOR);
        }

        public static IMusicRecognitionServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMusicRecognitionServiceCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMusicRecognitionServiceCallback)) {
                return (IMusicRecognitionServiceCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onRecognitionSucceeded";
            }
            if (i != 2) {
                return null;
            }
            return "onRecognitionFailed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMusicRecognitionServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMusicRecognitionServiceCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                MediaMetadata mediaMetadata = (MediaMetadata) parcel.readTypedObject(MediaMetadata.CREATOR);
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onRecognitionSucceeded(mediaMetadata, bundle);
            } else if (i == 2) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onRecognitionFailed(i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMusicRecognitionServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMusicRecognitionServiceCallback.DESCRIPTOR;
            }

            @Override // android.media.musicrecognition.IMusicRecognitionServiceCallback
            public void onRecognitionSucceeded(MediaMetadata mediaMetadata, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMusicRecognitionServiceCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(mediaMetadata, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.musicrecognition.IMusicRecognitionServiceCallback
            public void onRecognitionFailed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMusicRecognitionServiceCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
