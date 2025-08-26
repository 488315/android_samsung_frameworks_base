package android.hardware.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.media.permission.ClearCallingIdentityContext;
import android.media.permission.Identity;
import android.media.permission.SafeCloseable;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.ISoundTriggerCallback;
import android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService;
import android.media.soundtrigger_middleware.ISoundTriggerModule;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes2.dex */
public class SoundTriggerModule {
    private static final int EVENT_MODEL_UNLOADED = 4;
    private static final int EVENT_RECOGNITION = 1;
    private static final int EVENT_RESOURCES_AVAILABLE = 3;
    private static final int EVENT_SERVICE_DIED = 2;
    private static final String TAG = "SoundTriggerModule";
    private EventHandlerDelegate mEventHandlerDelegate;
    private int mId;
    private ISoundTriggerModule mService;

    public SoundTriggerModule(ISoundTriggerMiddlewareService iSoundTriggerMiddlewareService, int i, SoundTrigger.StatusListener statusListener, Looper looper, Identity identity) {
        this.mId = i;
        this.mEventHandlerDelegate = new EventHandlerDelegate(this, statusListener, looper);
        try {
            SafeCloseable safeCloseableCreate = ClearCallingIdentityContext.create();
            try {
                this.mService = iSoundTriggerMiddlewareService.attachAsOriginator(i, identity, this.mEventHandlerDelegate);
                if (safeCloseableCreate != null) {
                    safeCloseableCreate.close();
                }
                this.mService.asBinder().linkToDeath(this.mEventHandlerDelegate, 0);
            } finally {
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SoundTriggerModule(ISoundTriggerMiddlewareService iSoundTriggerMiddlewareService, int i, SoundTrigger.StatusListener statusListener, Looper looper, Identity identity, Identity identity2, boolean z) {
        this.mId = i;
        this.mEventHandlerDelegate = new EventHandlerDelegate(this, statusListener, looper);
        try {
            SafeCloseable safeCloseableCreate = ClearCallingIdentityContext.create();
            try {
                this.mService = iSoundTriggerMiddlewareService.attachAsMiddleman(i, identity, identity2, this.mEventHandlerDelegate, z);
                if (safeCloseableCreate != null) {
                    safeCloseableCreate.close();
                }
                this.mService.asBinder().linkToDeath(this.mEventHandlerDelegate, 0);
            } finally {
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected void finalize() {
        detach();
    }

    @Deprecated
    public synchronized void detach() {
        try {
            ISoundTriggerModule iSoundTriggerModule = this.mService;
            if (iSoundTriggerModule != null) {
                iSoundTriggerModule.asBinder().unlinkToDeath(this.mEventHandlerDelegate, 0);
                this.mService.detach();
                this.mService = null;
            }
        } catch (Exception e) {
            SoundTrigger.handleException(e);
        }
    }

    @Deprecated
    public synchronized int loadSoundModel(SoundTrigger.SoundModel soundModel, int[] iArr) {
        try {
            if (soundModel instanceof SoundTrigger.GenericSoundModel) {
                SoundModel soundModelApi2aidlGenericSoundModel = ConversionUtil.api2aidlGenericSoundModel((SoundTrigger.GenericSoundModel) soundModel);
                try {
                    iArr[0] = this.mService.loadModel(soundModelApi2aidlGenericSoundModel);
                    return 0;
                } finally {
                    if (soundModelApi2aidlGenericSoundModel.data != null) {
                        try {
                            soundModelApi2aidlGenericSoundModel.data.close();
                        } catch (IOException e) {
                            Log.e(TAG, "Failed to close file", e);
                        }
                    }
                }
            }
            if (soundModel instanceof SoundTrigger.KeyphraseSoundModel) {
                PhraseSoundModel phraseSoundModelApi2aidlPhraseSoundModel = ConversionUtil.api2aidlPhraseSoundModel((SoundTrigger.KeyphraseSoundModel) soundModel);
                try {
                    iArr[0] = this.mService.loadPhraseModel(phraseSoundModelApi2aidlPhraseSoundModel);
                    return 0;
                } finally {
                    if (phraseSoundModelApi2aidlPhraseSoundModel.common.data != null) {
                        try {
                            phraseSoundModelApi2aidlPhraseSoundModel.common.data.close();
                        } catch (IOException e2) {
                            Log.e(TAG, "Failed to close file", e2);
                        }
                    }
                }
            }
            return SoundTrigger.STATUS_BAD_VALUE;
        } catch (Exception e3) {
            return SoundTrigger.handleException(e3);
        }
    }

    @Deprecated
    public synchronized int unloadSoundModel(int i) {
        try {
            this.mService.unloadModel(i);
        } catch (Exception e) {
            return SoundTrigger.handleException(e);
        }
        return 0;
    }

    @Deprecated
    public synchronized int startRecognition(int i, SoundTrigger.RecognitionConfig recognitionConfig) {
        try {
            this.mService.startRecognition(i, ConversionUtil.api2aidlRecognitionConfig(recognitionConfig));
        } catch (Exception e) {
            return SoundTrigger.handleException(e);
        }
        return 0;
    }

    public synchronized IBinder startRecognitionWithToken(int i, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException {
        return this.mService.startRecognition(i, ConversionUtil.api2aidlRecognitionConfig(recognitionConfig));
    }

    @Deprecated
    public synchronized int stopRecognition(int i) {
        try {
            this.mService.stopRecognition(i);
        } catch (Exception e) {
            return SoundTrigger.handleException(e);
        }
        return 0;
    }

    public synchronized int getModelState(int i) {
        try {
            this.mService.forceRecognitionEvent(i);
        } catch (Exception e) {
            return SoundTrigger.handleException(e);
        }
        return 0;
    }

    public synchronized int setParameter(int i, int i2, int i3) {
        try {
            this.mService.setModelParameter(i, ConversionUtil.api2aidlModelParameter(i2), i3);
        } catch (Exception e) {
            return SoundTrigger.handleException(e);
        }
        return 0;
    }

    public synchronized int getParameter(int i, int i2) {
        try {
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
        return this.mService.getModelParameter(i, ConversionUtil.api2aidlModelParameter(i2));
    }

    public synchronized SoundTrigger.ModelParamRange queryParameter(int i, int i2) {
        try {
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
        return ConversionUtil.aidl2apiModelParameterRange(this.mService.queryModelParameterSupport(i, ConversionUtil.api2aidlModelParameter(i2)));
    }

    private class EventHandlerDelegate extends ISoundTriggerCallback.Stub implements IBinder.DeathRecipient {
        private final Handler mHandler;

        EventHandlerDelegate(final SoundTriggerModule soundTriggerModule, final SoundTrigger.StatusListener statusListener, Looper looper) {
            this.mHandler = new Handler(this, looper) { // from class: android.hardware.soundtrigger.SoundTriggerModule.EventHandlerDelegate.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    int i = message.what;
                    if (i == 1) {
                        statusListener.onRecognition((SoundTrigger.RecognitionEvent) message.obj);
                        return;
                    }
                    if (i == 2) {
                        statusListener.onServiceDied();
                        return;
                    }
                    if (i == 3) {
                        statusListener.onResourcesAvailable();
                    } else {
                        if (i == 4) {
                            statusListener.onModelUnloaded(((Integer) message.obj).intValue());
                            return;
                        }
                        Log.e(SoundTriggerModule.TAG, "Unknown message: " + message.toString());
                    }
                }
            };
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public synchronized void onRecognition(int i, RecognitionEventSys recognitionEventSys, int i2) throws RemoteException {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, ConversionUtil.aidl2apiRecognitionEvent(i, i2, recognitionEventSys)));
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public synchronized void onPhraseRecognition(int i, PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) throws RemoteException {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, ConversionUtil.aidl2apiPhraseRecognitionEvent(i, i2, phraseRecognitionEventSys)));
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onModelUnloaded(int i) throws RemoteException {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(4, Integer.valueOf(i)));
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public synchronized void onResourcesAvailable() throws RemoteException {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public synchronized void onModuleDied() {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(2));
        }

        @Override // android.os.IBinder.DeathRecipient
        public synchronized void binderDied() {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(2));
        }
    }
}
