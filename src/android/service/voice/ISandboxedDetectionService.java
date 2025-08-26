package android.service.voice;

import android.content.ContentCaptureOptions;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.service.voice.IDetectorSessionStorageService;
import android.service.voice.IDetectorSessionVisualQueryDetectionCallback;
import android.service.voice.IDspHotwordDetectionCallback;
import android.speech.IRecognitionServiceManager;
import android.view.contentcapture.IContentCaptureManager;

/* loaded from: classes3.dex */
public interface ISandboxedDetectionService extends IInterface {
    public static final String DESCRIPTOR = "android.service.voice.ISandboxedDetectionService";

    public static class Default implements ISandboxedDetectionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromDspSource(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, AudioFormat audioFormat, long j, IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromMicrophoneSource(ParcelFileDescriptor parcelFileDescriptor, int i, AudioFormat audioFormat, PersistableBundle persistableBundle, IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectWithVisualSignals(IDetectorSessionVisualQueryDetectionCallback iDetectorSessionVisualQueryDetectionCallback) throws RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void ping(IPingMe iPingMe) throws RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void registerRemoteStorageService(IDetectorSessionStorageService iDetectorSessionStorageService) throws RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void stopDetection() throws RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateAudioFlinger(IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateContentCaptureManager(IContentCaptureManager iContentCaptureManager, ContentCaptureOptions contentCaptureOptions) throws RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateRecognitionServiceManager(IRecognitionServiceManager iRecognitionServiceManager) throws RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory, IRemoteCallback iRemoteCallback) throws RemoteException {
        }
    }

    void detectFromDspSource(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, AudioFormat audioFormat, long j, IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws RemoteException;

    void detectFromMicrophoneSource(ParcelFileDescriptor parcelFileDescriptor, int i, AudioFormat audioFormat, PersistableBundle persistableBundle, IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws RemoteException;

    void detectWithVisualSignals(IDetectorSessionVisualQueryDetectionCallback iDetectorSessionVisualQueryDetectionCallback) throws RemoteException;

    void ping(IPingMe iPingMe) throws RemoteException;

    void registerRemoteStorageService(IDetectorSessionStorageService iDetectorSessionStorageService) throws RemoteException;

    void stopDetection() throws RemoteException;

    void updateAudioFlinger(IBinder iBinder) throws RemoteException;

    void updateContentCaptureManager(IContentCaptureManager iContentCaptureManager, ContentCaptureOptions contentCaptureOptions) throws RemoteException;

    void updateRecognitionServiceManager(IRecognitionServiceManager iRecognitionServiceManager) throws RemoteException;

    void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory, IRemoteCallback iRemoteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ISandboxedDetectionService {
        static final int TRANSACTION_detectFromDspSource = 1;
        static final int TRANSACTION_detectFromMicrophoneSource = 2;
        static final int TRANSACTION_detectWithVisualSignals = 3;
        static final int TRANSACTION_ping = 8;
        static final int TRANSACTION_registerRemoteStorageService = 10;
        static final int TRANSACTION_stopDetection = 9;
        static final int TRANSACTION_updateAudioFlinger = 5;
        static final int TRANSACTION_updateContentCaptureManager = 6;
        static final int TRANSACTION_updateRecognitionServiceManager = 7;
        static final int TRANSACTION_updateState = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, ISandboxedDetectionService.DESCRIPTOR);
        }

        public static ISandboxedDetectionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISandboxedDetectionService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISandboxedDetectionService)) {
                return (ISandboxedDetectionService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "detectFromDspSource";
                case 2:
                    return "detectFromMicrophoneSource";
                case 3:
                    return "detectWithVisualSignals";
                case 4:
                    return "updateState";
                case 5:
                    return "updateAudioFlinger";
                case 6:
                    return "updateContentCaptureManager";
                case 7:
                    return "updateRecognitionServiceManager";
                case 8:
                    return "ping";
                case 9:
                    return "stopDetection";
                case 10:
                    return "registerRemoteStorageService";
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
                parcel.enforceInterface(ISandboxedDetectionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISandboxedDetectionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent = (SoundTrigger.KeyphraseRecognitionEvent) parcel.readTypedObject(SoundTrigger.KeyphraseRecognitionEvent.CREATOR);
                    AudioFormat audioFormat = (AudioFormat) parcel.readTypedObject(AudioFormat.CREATOR);
                    long j = parcel.readLong();
                    IDspHotwordDetectionCallback iDspHotwordDetectionCallbackAsInterface = IDspHotwordDetectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    detectFromDspSource(keyphraseRecognitionEvent, audioFormat, j, iDspHotwordDetectionCallbackAsInterface);
                    return true;
                case 2:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int i3 = parcel.readInt();
                    AudioFormat audioFormat2 = (AudioFormat) parcel.readTypedObject(AudioFormat.CREATOR);
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    IDspHotwordDetectionCallback iDspHotwordDetectionCallbackAsInterface2 = IDspHotwordDetectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    detectFromMicrophoneSource(parcelFileDescriptor, i3, audioFormat2, persistableBundle, iDspHotwordDetectionCallbackAsInterface2);
                    return true;
                case 3:
                    IDetectorSessionVisualQueryDetectionCallback iDetectorSessionVisualQueryDetectionCallbackAsInterface = IDetectorSessionVisualQueryDetectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    detectWithVisualSignals(iDetectorSessionVisualQueryDetectionCallbackAsInterface);
                    return true;
                case 4:
                    PersistableBundle persistableBundle2 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    SharedMemory sharedMemory = (SharedMemory) parcel.readTypedObject(SharedMemory.CREATOR);
                    IRemoteCallback iRemoteCallbackAsInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateState(persistableBundle2, sharedMemory, iRemoteCallbackAsInterface);
                    return true;
                case 5:
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    updateAudioFlinger(strongBinder);
                    return true;
                case 6:
                    IContentCaptureManager iContentCaptureManagerAsInterface = IContentCaptureManager.Stub.asInterface(parcel.readStrongBinder());
                    ContentCaptureOptions contentCaptureOptions = (ContentCaptureOptions) parcel.readTypedObject(ContentCaptureOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateContentCaptureManager(iContentCaptureManagerAsInterface, contentCaptureOptions);
                    return true;
                case 7:
                    IRecognitionServiceManager iRecognitionServiceManagerAsInterface = IRecognitionServiceManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateRecognitionServiceManager(iRecognitionServiceManagerAsInterface);
                    return true;
                case 8:
                    IPingMe iPingMeAsInterface = IPingMe.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    ping(iPingMeAsInterface);
                    return true;
                case 9:
                    stopDetection();
                    return true;
                case 10:
                    IDetectorSessionStorageService iDetectorSessionStorageServiceAsInterface = IDetectorSessionStorageService.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRemoteStorageService(iDetectorSessionStorageServiceAsInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISandboxedDetectionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISandboxedDetectionService.DESCRIPTOR;
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void detectFromDspSource(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, AudioFormat audioFormat, long j, IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISandboxedDetectionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyphraseRecognitionEvent, 0);
                    parcelObtain.writeTypedObject(audioFormat, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iDspHotwordDetectionCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void detectFromMicrophoneSource(ParcelFileDescriptor parcelFileDescriptor, int i, AudioFormat audioFormat, PersistableBundle persistableBundle, IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISandboxedDetectionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(audioFormat, 0);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    parcelObtain.writeStrongInterface(iDspHotwordDetectionCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void detectWithVisualSignals(IDetectorSessionVisualQueryDetectionCallback iDetectorSessionVisualQueryDetectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISandboxedDetectionService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDetectorSessionVisualQueryDetectionCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISandboxedDetectionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    parcelObtain.writeTypedObject(sharedMemory, 0);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void updateAudioFlinger(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISandboxedDetectionService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void updateContentCaptureManager(IContentCaptureManager iContentCaptureManager, ContentCaptureOptions contentCaptureOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISandboxedDetectionService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iContentCaptureManager);
                    parcelObtain.writeTypedObject(contentCaptureOptions, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void updateRecognitionServiceManager(IRecognitionServiceManager iRecognitionServiceManager) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISandboxedDetectionService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRecognitionServiceManager);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void ping(IPingMe iPingMe) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISandboxedDetectionService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPingMe);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void stopDetection() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISandboxedDetectionService.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void registerRemoteStorageService(IDetectorSessionStorageService iDetectorSessionStorageService) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISandboxedDetectionService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDetectorSessionStorageService);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }

    public interface IPingMe extends IInterface {
        public static final String DESCRIPTOR = "android.service.voice.ISandboxedDetectionService.IPingMe";

        public static class Default implements IPingMe {
            @Override // android.os.IInterface
            public IBinder asBinder() {
                return null;
            }

            @Override // android.service.voice.ISandboxedDetectionService.IPingMe
            public void onPing() throws RemoteException {
            }
        }

        void onPing() throws RemoteException;

        public static abstract class Stub extends Binder implements IPingMe {
            static final int TRANSACTION_onPing = 1;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this;
            }

            @Override // android.os.Binder
            public int getMaxTransactionId() {
                return 0;
            }

            public Stub() {
                attachInterface(this, IPingMe.DESCRIPTOR);
            }

            public static IPingMe asInterface(IBinder iBinder) {
                if (iBinder == null) {
                    return null;
                }
                IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPingMe.DESCRIPTOR);
                if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPingMe)) {
                    return (IPingMe) iInterfaceQueryLocalInterface;
                }
                return new Proxy(iBinder);
            }

            public static String getDefaultTransactionName(int i) {
                if (i != 1) {
                    return null;
                }
                return "onPing";
            }

            @Override // android.os.Binder
            public String getTransactionName(int i) {
                return getDefaultTransactionName(i);
            }

            @Override // android.os.Binder
            public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                if (i >= 1 && i <= 16777215) {
                    parcel.enforceInterface(IPingMe.DESCRIPTOR);
                }
                if (i == 1598968902) {
                    parcel2.writeString(IPingMe.DESCRIPTOR);
                    return true;
                }
                if (i == 1) {
                    onPing();
                    parcel2.writeNoException();
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }

            private static class Proxy implements IPingMe {
                private IBinder mRemote;

                Proxy(IBinder iBinder) {
                    this.mRemote = iBinder;
                }

                @Override // android.os.IInterface
                public IBinder asBinder() {
                    return this.mRemote;
                }

                public String getInterfaceDescriptor() {
                    return IPingMe.DESCRIPTOR;
                }

                @Override // android.service.voice.ISandboxedDetectionService.IPingMe
                public void onPing() throws RemoteException {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(IPingMe.DESCRIPTOR);
                        this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
            }
        }
    }
}
