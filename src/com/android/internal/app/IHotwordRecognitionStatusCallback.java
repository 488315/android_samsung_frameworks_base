package com.android.internal.app;

import android.hardware.soundtrigger.SoundTrigger;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.voice.HotwordDetectedResult;
import android.service.voice.HotwordDetectionServiceFailure;
import android.service.voice.HotwordRejectedResult;
import android.service.voice.SoundTriggerFailure;
import android.service.voice.VisualQueryDetectionServiceFailure;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes5.dex */
public interface IHotwordRecognitionStatusCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IHotwordRecognitionStatusCallback";

    public static class Default implements IHotwordRecognitionStatusCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onHotwordDetectionServiceFailure(HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, HotwordDetectedResult hotwordDetectedResult) throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetectedFromExternalSource(HotwordDetectedResult hotwordDetectedResult) throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onOpenFile(String str, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onProcessRestarted() throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionPaused() throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionResumed() throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure) throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onStatusReported(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onUnknownFailure(String str) throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onVisualQueryDetectionServiceFailure(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws RemoteException {
        }
    }

    void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws RemoteException;

    void onHotwordDetectionServiceFailure(HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws RemoteException;

    void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, HotwordDetectedResult hotwordDetectedResult) throws RemoteException;

    void onKeyphraseDetectedFromExternalSource(HotwordDetectedResult hotwordDetectedResult) throws RemoteException;

    void onOpenFile(String str, AndroidFuture androidFuture) throws RemoteException;

    void onProcessRestarted() throws RemoteException;

    void onRecognitionPaused() throws RemoteException;

    void onRecognitionResumed() throws RemoteException;

    void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException;

    void onSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure) throws RemoteException;

    void onStatusReported(int i) throws RemoteException;

    void onUnknownFailure(String str) throws RemoteException;

    void onVisualQueryDetectionServiceFailure(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws RemoteException;

    public static abstract class Stub extends Binder implements IHotwordRecognitionStatusCallback {
        static final int TRANSACTION_onGenericSoundTriggerDetected = 3;
        static final int TRANSACTION_onHotwordDetectionServiceFailure = 5;
        static final int TRANSACTION_onKeyphraseDetected = 1;
        static final int TRANSACTION_onKeyphraseDetectedFromExternalSource = 2;
        static final int TRANSACTION_onOpenFile = 13;
        static final int TRANSACTION_onProcessRestarted = 12;
        static final int TRANSACTION_onRecognitionPaused = 9;
        static final int TRANSACTION_onRecognitionResumed = 10;
        static final int TRANSACTION_onRejected = 4;
        static final int TRANSACTION_onSoundTriggerFailure = 7;
        static final int TRANSACTION_onStatusReported = 11;
        static final int TRANSACTION_onUnknownFailure = 8;
        static final int TRANSACTION_onVisualQueryDetectionServiceFailure = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, IHotwordRecognitionStatusCallback.DESCRIPTOR);
        }

        public static IHotwordRecognitionStatusCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IHotwordRecognitionStatusCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IHotwordRecognitionStatusCallback)) {
                return (IHotwordRecognitionStatusCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onKeyphraseDetected";
                case 2:
                    return "onKeyphraseDetectedFromExternalSource";
                case 3:
                    return "onGenericSoundTriggerDetected";
                case 4:
                    return "onRejected";
                case 5:
                    return "onHotwordDetectionServiceFailure";
                case 6:
                    return "onVisualQueryDetectionServiceFailure";
                case 7:
                    return "onSoundTriggerFailure";
                case 8:
                    return "onUnknownFailure";
                case 9:
                    return "onRecognitionPaused";
                case 10:
                    return "onRecognitionResumed";
                case 11:
                    return "onStatusReported";
                case 12:
                    return "onProcessRestarted";
                case 13:
                    return "onOpenFile";
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
                parcel.enforceInterface(IHotwordRecognitionStatusCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent = (SoundTrigger.KeyphraseRecognitionEvent) parcel.readTypedObject(SoundTrigger.KeyphraseRecognitionEvent.CREATOR);
                    HotwordDetectedResult hotwordDetectedResult = (HotwordDetectedResult) parcel.readTypedObject(HotwordDetectedResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onKeyphraseDetected(keyphraseRecognitionEvent, hotwordDetectedResult);
                    return true;
                case 2:
                    HotwordDetectedResult hotwordDetectedResult2 = (HotwordDetectedResult) parcel.readTypedObject(HotwordDetectedResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onKeyphraseDetectedFromExternalSource(hotwordDetectedResult2);
                    return true;
                case 3:
                    SoundTrigger.GenericRecognitionEvent genericRecognitionEvent = (SoundTrigger.GenericRecognitionEvent) parcel.readTypedObject(SoundTrigger.GenericRecognitionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGenericSoundTriggerDetected(genericRecognitionEvent);
                    return true;
                case 4:
                    HotwordRejectedResult hotwordRejectedResult = (HotwordRejectedResult) parcel.readTypedObject(HotwordRejectedResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRejected(hotwordRejectedResult);
                    return true;
                case 5:
                    HotwordDetectionServiceFailure hotwordDetectionServiceFailure = (HotwordDetectionServiceFailure) parcel.readTypedObject(HotwordDetectionServiceFailure.CREATOR);
                    parcel.enforceNoDataAvail();
                    onHotwordDetectionServiceFailure(hotwordDetectionServiceFailure);
                    return true;
                case 6:
                    VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure = (VisualQueryDetectionServiceFailure) parcel.readTypedObject(VisualQueryDetectionServiceFailure.CREATOR);
                    parcel.enforceNoDataAvail();
                    onVisualQueryDetectionServiceFailure(visualQueryDetectionServiceFailure);
                    return true;
                case 7:
                    SoundTriggerFailure soundTriggerFailure = (SoundTriggerFailure) parcel.readTypedObject(SoundTriggerFailure.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSoundTriggerFailure(soundTriggerFailure);
                    return true;
                case 8:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onUnknownFailure(string);
                    return true;
                case 9:
                    onRecognitionPaused();
                    return true;
                case 10:
                    onRecognitionResumed();
                    return true;
                case 11:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStatusReported(i3);
                    return true;
                case 12:
                    onProcessRestarted();
                    return true;
                case 13:
                    String string2 = parcel.readString();
                    AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    onOpenFile(string2, androidFuture);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IHotwordRecognitionStatusCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHotwordRecognitionStatusCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, HotwordDetectedResult hotwordDetectedResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyphraseRecognitionEvent, 0);
                    parcelObtain.writeTypedObject(hotwordDetectedResult, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onKeyphraseDetectedFromExternalSource(HotwordDetectedResult hotwordDetectedResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hotwordDetectedResult, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(genericRecognitionEvent, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hotwordRejectedResult, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onHotwordDetectionServiceFailure(HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hotwordDetectionServiceFailure, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onVisualQueryDetectionServiceFailure(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(visualQueryDetectionServiceFailure, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(soundTriggerFailure, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onUnknownFailure(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onRecognitionPaused() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onRecognitionResumed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onStatusReported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onProcessRestarted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onOpenFile(String str, AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
