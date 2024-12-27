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

public interface IHotwordRecognitionStatusCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.app.IHotwordRecognitionStatusCallback";

    void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent genericRecognitionEvent)
            throws RemoteException;

    void onHotwordDetectionServiceFailure(
            HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws RemoteException;

    void onKeyphraseDetected(
            SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent,
            HotwordDetectedResult hotwordDetectedResult)
            throws RemoteException;

    void onKeyphraseDetectedFromExternalSource(HotwordDetectedResult hotwordDetectedResult)
            throws RemoteException;

    void onOpenFile(String str, AndroidFuture androidFuture) throws RemoteException;

    void onProcessRestarted() throws RemoteException;

    void onRecognitionPaused() throws RemoteException;

    void onRecognitionResumed() throws RemoteException;

    void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException;

    void onSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure) throws RemoteException;

    void onStatusReported(int i) throws RemoteException;

    void onUnknownFailure(String str) throws RemoteException;

    void onVisualQueryDetectionServiceFailure(
            VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure)
            throws RemoteException;

    public static class Default implements IHotwordRecognitionStatusCallback {
        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetected(
                SoundTrigger.KeyphraseRecognitionEvent recognitionEvent,
                HotwordDetectedResult result)
                throws RemoteException {}

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetectedFromExternalSource(HotwordDetectedResult result)
                throws RemoteException {}

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onGenericSoundTriggerDetected(
                SoundTrigger.GenericRecognitionEvent recognitionEvent) throws RemoteException {}

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRejected(HotwordRejectedResult result) throws RemoteException {}

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onHotwordDetectionServiceFailure(
                HotwordDetectionServiceFailure hotwordDetectionServiceFailure)
                throws RemoteException {}

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onVisualQueryDetectionServiceFailure(
                VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure)
                throws RemoteException {}

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure)
                throws RemoteException {}

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onUnknownFailure(String errorMessage) throws RemoteException {}

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionPaused() throws RemoteException {}

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionResumed() throws RemoteException {}

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onStatusReported(int status) throws RemoteException {}

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onProcessRestarted() throws RemoteException {}

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onOpenFile(String filename, AndroidFuture future) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IHotwordRecognitionStatusCallback {
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

        public Stub() {
            attachInterface(this, IHotwordRecognitionStatusCallback.DESCRIPTOR);
        }

        public static IHotwordRecognitionStatusCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IHotwordRecognitionStatusCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IHotwordRecognitionStatusCallback)) {
                return (IHotwordRecognitionStatusCallback) iin;
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IHotwordRecognitionStatusCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SoundTrigger.KeyphraseRecognitionEvent _arg0 =
                            (SoundTrigger.KeyphraseRecognitionEvent)
                                    data.readTypedObject(
                                            SoundTrigger.KeyphraseRecognitionEvent.CREATOR);
                    HotwordDetectedResult _arg1 =
                            (HotwordDetectedResult)
                                    data.readTypedObject(HotwordDetectedResult.CREATOR);
                    data.enforceNoDataAvail();
                    onKeyphraseDetected(_arg0, _arg1);
                    return true;
                case 2:
                    HotwordDetectedResult _arg02 =
                            (HotwordDetectedResult)
                                    data.readTypedObject(HotwordDetectedResult.CREATOR);
                    data.enforceNoDataAvail();
                    onKeyphraseDetectedFromExternalSource(_arg02);
                    return true;
                case 3:
                    SoundTrigger.GenericRecognitionEvent _arg03 =
                            (SoundTrigger.GenericRecognitionEvent)
                                    data.readTypedObject(
                                            SoundTrigger.GenericRecognitionEvent.CREATOR);
                    data.enforceNoDataAvail();
                    onGenericSoundTriggerDetected(_arg03);
                    return true;
                case 4:
                    HotwordRejectedResult _arg04 =
                            (HotwordRejectedResult)
                                    data.readTypedObject(HotwordRejectedResult.CREATOR);
                    data.enforceNoDataAvail();
                    onRejected(_arg04);
                    return true;
                case 5:
                    HotwordDetectionServiceFailure _arg05 =
                            (HotwordDetectionServiceFailure)
                                    data.readTypedObject(HotwordDetectionServiceFailure.CREATOR);
                    data.enforceNoDataAvail();
                    onHotwordDetectionServiceFailure(_arg05);
                    return true;
                case 6:
                    VisualQueryDetectionServiceFailure _arg06 =
                            (VisualQueryDetectionServiceFailure)
                                    data.readTypedObject(
                                            VisualQueryDetectionServiceFailure.CREATOR);
                    data.enforceNoDataAvail();
                    onVisualQueryDetectionServiceFailure(_arg06);
                    return true;
                case 7:
                    SoundTriggerFailure _arg07 =
                            (SoundTriggerFailure) data.readTypedObject(SoundTriggerFailure.CREATOR);
                    data.enforceNoDataAvail();
                    onSoundTriggerFailure(_arg07);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    data.enforceNoDataAvail();
                    onUnknownFailure(_arg08);
                    return true;
                case 9:
                    onRecognitionPaused();
                    return true;
                case 10:
                    onRecognitionResumed();
                    return true;
                case 11:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    onStatusReported(_arg09);
                    return true;
                case 12:
                    onProcessRestarted();
                    return true;
                case 13:
                    String _arg010 = data.readString();
                    AndroidFuture _arg12 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    data.enforceNoDataAvail();
                    onOpenFile(_arg010, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IHotwordRecognitionStatusCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHotwordRecognitionStatusCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onKeyphraseDetected(
                    SoundTrigger.KeyphraseRecognitionEvent recognitionEvent,
                    HotwordDetectedResult result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    _data.writeTypedObject(recognitionEvent, 0);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onKeyphraseDetectedFromExternalSource(HotwordDetectedResult result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onGenericSoundTriggerDetected(
                    SoundTrigger.GenericRecognitionEvent recognitionEvent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    _data.writeTypedObject(recognitionEvent, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onRejected(HotwordRejectedResult result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onHotwordDetectionServiceFailure(
                    HotwordDetectionServiceFailure hotwordDetectionServiceFailure)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    _data.writeTypedObject(hotwordDetectionServiceFailure, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onVisualQueryDetectionServiceFailure(
                    VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    _data.writeTypedObject(visualQueryDetectionServiceFailure, 0);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    _data.writeTypedObject(soundTriggerFailure, 0);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onUnknownFailure(String errorMessage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    _data.writeString(errorMessage);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onRecognitionPaused() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onRecognitionResumed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onStatusReported(int status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    _data.writeInt(status);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onProcessRestarted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
            public void onOpenFile(String filename, AndroidFuture future) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
                    _data.writeString(filename);
                    _data.writeTypedObject(future, 0);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
