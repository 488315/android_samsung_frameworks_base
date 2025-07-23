package android.media.musicrecognition;

import android.media.AudioFormat;
import android.media.musicrecognition.IMusicRecognitionAttributionTagCallback;
import android.media.musicrecognition.IMusicRecognitionServiceCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMusicRecognitionService extends IInterface {
    public static final String DESCRIPTOR = "android.media.musicrecognition.IMusicRecognitionService";

    public static class Default implements IMusicRecognitionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.musicrecognition.IMusicRecognitionService
        public void getAttributionTag(IMusicRecognitionAttributionTagCallback iMusicRecognitionAttributionTagCallback) throws RemoteException {
        }

        @Override // android.media.musicrecognition.IMusicRecognitionService
        public void onAudioStreamStarted(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, IMusicRecognitionServiceCallback iMusicRecognitionServiceCallback) throws RemoteException {
        }
    }

    void getAttributionTag(IMusicRecognitionAttributionTagCallback iMusicRecognitionAttributionTagCallback) throws RemoteException;

    void onAudioStreamStarted(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, IMusicRecognitionServiceCallback iMusicRecognitionServiceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IMusicRecognitionService {
        static final int TRANSACTION_getAttributionTag = 2;
        static final int TRANSACTION_onAudioStreamStarted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IMusicRecognitionService.DESCRIPTOR);
        }

        public static IMusicRecognitionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMusicRecognitionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMusicRecognitionService)) {
                return (IMusicRecognitionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onAudioStreamStarted";
            }
            if (i != 2) {
                return null;
            }
            return "getAttributionTag";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMusicRecognitionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMusicRecognitionService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                AudioFormat audioFormat = (AudioFormat) parcel.readTypedObject(AudioFormat.CREATOR);
                IMusicRecognitionServiceCallback asInterface = IMusicRecognitionServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onAudioStreamStarted(parcelFileDescriptor, audioFormat, asInterface);
            } else if (i == 2) {
                IMusicRecognitionAttributionTagCallback asInterface2 = IMusicRecognitionAttributionTagCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getAttributionTag(asInterface2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMusicRecognitionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMusicRecognitionService.DESCRIPTOR;
            }

            @Override // android.media.musicrecognition.IMusicRecognitionService
            public void onAudioStreamStarted(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, IMusicRecognitionServiceCallback iMusicRecognitionServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMusicRecognitionService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(audioFormat, 0);
                    obtain.writeStrongInterface(iMusicRecognitionServiceCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.musicrecognition.IMusicRecognitionService
            public void getAttributionTag(IMusicRecognitionAttributionTagCallback iMusicRecognitionAttributionTagCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMusicRecognitionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMusicRecognitionAttributionTagCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
