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

    public SoundTriggerModule(ISoundTriggerMiddlewareService service, int moduleId, SoundTrigger.StatusListener listener, Looper looper, Identity originatorIdentity) {
        this.mId = moduleId;
        this.mEventHandlerDelegate = new EventHandlerDelegate(listener, looper);
        try {
            SafeCloseable ignored = ClearCallingIdentityContext.create();
            try {
                this.mService = service.attachAsOriginator(moduleId, originatorIdentity, this.mEventHandlerDelegate);
                if (ignored != null) {
                    ignored.close();
                }
                this.mService.asBinder().linkToDeath(this.mEventHandlerDelegate, 0);
            } finally {
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SoundTriggerModule(ISoundTriggerMiddlewareService service, int moduleId, SoundTrigger.StatusListener listener, Looper looper, Identity middlemanIdentity, Identity originatorIdentity, boolean isTrusted) {
        this.mId = moduleId;
        this.mEventHandlerDelegate = new EventHandlerDelegate(listener, looper);
        try {
            SafeCloseable ignored = ClearCallingIdentityContext.create();
            try {
                this.mService = service.attachAsMiddleman(moduleId, middlemanIdentity, originatorIdentity, this.mEventHandlerDelegate, isTrusted);
                if (ignored != null) {
                    ignored.close();
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
            if (this.mService != null) {
                this.mService.asBinder().unlinkToDeath(this.mEventHandlerDelegate, 0);
                this.mService.detach();
                this.mService = null;
            }
        } catch (Exception e) {
            SoundTrigger.handleException(e);
        }
    }

    @Deprecated
    public synchronized int loadSoundModel(SoundTrigger.SoundModel model, int[] soundModelHandle) {
        try {
            if (model instanceof SoundTrigger.GenericSoundModel) {
                SoundModel aidlModel = ConversionUtil.api2aidlGenericSoundModel((SoundTrigger.GenericSoundModel) model);
                try {
                    soundModelHandle[0] = this.mService.loadModel(aidlModel);
                    return 0;
                } finally {
                    if (aidlModel.data != null) {
                        try {
                            aidlModel.data.close();
                        } catch (IOException e) {
                            Log.e(TAG, "Failed to close file", e);
                        }
                    }
                }
            }
            if (model instanceof SoundTrigger.KeyphraseSoundModel) {
                PhraseSoundModel aidlModel2 = ConversionUtil.api2aidlPhraseSoundModel((SoundTrigger.KeyphraseSoundModel) model);
                try {
                    soundModelHandle[0] = this.mService.loadPhraseModel(aidlModel2);
                    return 0;
                } finally {
                    if (aidlModel2.common.data != null) {
                        try {
                            aidlModel2.common.data.close();
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
    public synchronized int unloadSoundModel(int soundModelHandle) {
        try {
            this.mService.unloadModel(soundModelHandle);
        } catch (Exception e) {
            return SoundTrigger.handleException(e);
        }
        return 0;
    }

    @Deprecated
    public synchronized int startRecognition(int soundModelHandle, SoundTrigger.RecognitionConfig config) {
        try {
            this.mService.startRecognition(soundModelHandle, ConversionUtil.api2aidlRecognitionConfig(config));
        } catch (Exception e) {
            return SoundTrigger.handleException(e);
        }
        return 0;
    }

    public synchronized IBinder startRecognitionWithToken(int soundModelHandle, SoundTrigger.RecognitionConfig config) throws RemoteException {
        return this.mService.startRecognition(soundModelHandle, ConversionUtil.api2aidlRecognitionConfig(config));
    }

    @Deprecated
    public synchronized int stopRecognition(int soundModelHandle) {
        try {
            this.mService.stopRecognition(soundModelHandle);
        } catch (Exception e) {
            return SoundTrigger.handleException(e);
        }
        return 0;
    }

    public synchronized int getModelState(int soundModelHandle) {
        try {
            this.mService.forceRecognitionEvent(soundModelHandle);
        } catch (Exception e) {
            return SoundTrigger.handleException(e);
        }
        return 0;
    }

    public synchronized int setParameter(int soundModelHandle, int modelParam, int value) {
        try {
            this.mService.setModelParameter(soundModelHandle, ConversionUtil.api2aidlModelParameter(modelParam), value);
        } catch (Exception e) {
            return SoundTrigger.handleException(e);
        }
        return 0;
    }

    public synchronized int getParameter(int soundModelHandle, int modelParam) {
        try {
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
        return this.mService.getModelParameter(soundModelHandle, ConversionUtil.api2aidlModelParameter(modelParam));
    }

    public synchronized SoundTrigger.ModelParamRange queryParameter(int soundModelHandle, int modelParam) {
        try {
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
        return ConversionUtil.aidl2apiModelParameterRange(this.mService.queryModelParameterSupport(soundModelHandle, ConversionUtil.api2aidlModelParameter(modelParam)));
    }

    private class EventHandlerDelegate extends ISoundTriggerCallback.Stub implements IBinder.DeathRecipient {
        private final Handler mHandler;

        EventHandlerDelegate(final SoundTrigger.StatusListener listener, Looper looper) {
            this.mHandler = new Handler(looper) { // from class: android.hardware.soundtrigger.SoundTriggerModule.EventHandlerDelegate.1
                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            listener.onRecognition((SoundTrigger.RecognitionEvent) msg.obj);
                            break;
                        case 2:
                            listener.onServiceDied();
                            break;
                        case 3:
                            listener.onResourcesAvailable();
                            break;
                        case 4:
                            listener.onModelUnloaded(((Integer) msg.obj).intValue());
                            break;
                        default:
                            Log.e(SoundTriggerModule.TAG, "Unknown message: " + msg.toString());
                            break;
                    }
                }
            };
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public synchronized void onRecognition(int handle, RecognitionEventSys event, int captureSession) throws RemoteException {
            Message m = this.mHandler.obtainMessage(1, ConversionUtil.aidl2apiRecognitionEvent(handle, captureSession, event));
            this.mHandler.sendMessage(m);
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public synchronized void onPhraseRecognition(int handle, PhraseRecognitionEventSys event, int captureSession) throws RemoteException {
            Message m = this.mHandler.obtainMessage(1, ConversionUtil.aidl2apiPhraseRecognitionEvent(handle, captureSession, event));
            this.mHandler.sendMessage(m);
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onModelUnloaded(int modelHandle) throws RemoteException {
            Message m = this.mHandler.obtainMessage(4, Integer.valueOf(modelHandle));
            this.mHandler.sendMessage(m);
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public synchronized void onResourcesAvailable() throws RemoteException {
            Message m = this.mHandler.obtainMessage(3);
            this.mHandler.sendMessage(m);
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public synchronized void onModuleDied() {
            Message m = this.mHandler.obtainMessage(2);
            this.mHandler.sendMessage(m);
        }

        @Override // android.os.IBinder.DeathRecipient
        public synchronized void binderDied() {
            Message m = this.mHandler.obtainMessage(2);
            this.mHandler.sendMessage(m);
        }
    }
}
