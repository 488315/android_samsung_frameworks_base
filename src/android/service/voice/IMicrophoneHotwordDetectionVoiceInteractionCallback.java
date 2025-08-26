package android.service.voice;

import android.media.AudioFormat;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMicrophoneHotwordDetectionVoiceInteractionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback";

    public static class Default implements IMicrophoneHotwordDetectionVoiceInteractionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onDetected(HotwordDetectedResult hotwordDetectedResult, AudioFormat audioFormat, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onHotwordDetectionServiceFailure(HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws RemoteException {
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException {
        }
    }

    void onDetected(HotwordDetectedResult hotwordDetectedResult, AudioFormat audioFormat, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void onHotwordDetectionServiceFailure(HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws RemoteException;

    void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException;

    public static abstract class Stub extends Binder implements IMicrophoneHotwordDetectionVoiceInteractionCallback {
        static final int TRANSACTION_onDetected = 1;
        static final int TRANSACTION_onHotwordDetectionServiceFailure = 2;
        static final int TRANSACTION_onRejected = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
        }

        public static IMicrophoneHotwordDetectionVoiceInteractionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMicrophoneHotwordDetectionVoiceInteractionCallback)) {
                return (IMicrophoneHotwordDetectionVoiceInteractionCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onDetected";
            }
            if (i == 2) {
                return "onHotwordDetectionServiceFailure";
            }
            if (i != 3) {
                return null;
            }
            return "onRejected";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                HotwordDetectedResult hotwordDetectedResult = (HotwordDetectedResult) parcel.readTypedObject(HotwordDetectedResult.CREATOR);
                AudioFormat audioFormat = (AudioFormat) parcel.readTypedObject(AudioFormat.CREATOR);
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                parcel.enforceNoDataAvail();
                onDetected(hotwordDetectedResult, audioFormat, parcelFileDescriptor);
            } else if (i == 2) {
                HotwordDetectionServiceFailure hotwordDetectionServiceFailure = (HotwordDetectionServiceFailure) parcel.readTypedObject(HotwordDetectionServiceFailure.CREATOR);
                parcel.enforceNoDataAvail();
                onHotwordDetectionServiceFailure(hotwordDetectionServiceFailure);
            } else if (i == 3) {
                HotwordRejectedResult hotwordRejectedResult = (HotwordRejectedResult) parcel.readTypedObject(HotwordRejectedResult.CREATOR);
                parcel.enforceNoDataAvail();
                onRejected(hotwordRejectedResult);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMicrophoneHotwordDetectionVoiceInteractionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR;
            }

            @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
            public void onDetected(HotwordDetectedResult hotwordDetectedResult, AudioFormat audioFormat, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hotwordDetectedResult, 0);
                    parcelObtain.writeTypedObject(audioFormat, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
            public void onHotwordDetectionServiceFailure(HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hotwordDetectionServiceFailure, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
            public void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMicrophoneHotwordDetectionVoiceInteractionCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hotwordRejectedResult, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
