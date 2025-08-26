package android.media;

import android.media.AudioManager;
import android.media.IAudioService;
import android.media.IRecordingConfigDispatcher;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class AudioRecordingMonitorImpl implements AudioRecordingMonitor {
    private static final int MSG_RECORDING_CONFIG_CHANGE = 1;
    private static final String TAG = "android.media.AudioRecordingMonitor";
    private static IAudioService sService;
    private final AudioRecordingMonitorClient mClient;
    private volatile Handler mRecordingCallbackHandler;
    private HandlerThread mRecordingCallbackHandlerThread;
    private final Object mRecordCallbackLock = new Object();
    private LinkedList<AudioRecordingCallbackInfo> mRecordCallbackList = new LinkedList<>();
    private final IRecordingConfigDispatcher mRecordingCallback = new IRecordingConfigDispatcher.Stub() { // from class: android.media.AudioRecordingMonitorImpl.1
        @Override // android.media.IRecordingConfigDispatcher
        public void dispatchRecordingConfigChange(List<AudioRecordingConfiguration> list) {
            AudioRecordingConfiguration myConfig = AudioRecordingMonitorImpl.this.getMyConfig(list);
            if (myConfig != null) {
                synchronized (AudioRecordingMonitorImpl.this.mRecordCallbackLock) {
                    if (AudioRecordingMonitorImpl.this.mRecordingCallbackHandler != null) {
                        AudioRecordingMonitorImpl.this.mRecordingCallbackHandler.sendMessage(AudioRecordingMonitorImpl.this.mRecordingCallbackHandler.obtainMessage(1, myConfig));
                    }
                }
            }
        }
    };

    AudioRecordingMonitorImpl(AudioRecordingMonitorClient audioRecordingMonitorClient) {
        this.mClient = audioRecordingMonitorClient;
    }

    @Override // android.media.AudioRecordingMonitor
    public void registerAudioRecordingCallback(Executor executor, AudioManager.AudioRecordingCallback audioRecordingCallback) {
        if (audioRecordingCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Illegal null Executor");
        }
        synchronized (this.mRecordCallbackLock) {
            Iterator<AudioRecordingCallbackInfo> it = this.mRecordCallbackList.iterator();
            while (it.hasNext()) {
                if (it.next().mCb == audioRecordingCallback) {
                    throw new IllegalArgumentException("AudioRecordingCallback already registered");
                }
            }
            beginRecordingCallbackHandling();
            this.mRecordCallbackList.add(new AudioRecordingCallbackInfo(executor, audioRecordingCallback));
        }
    }

    @Override // android.media.AudioRecordingMonitor
    public void unregisterAudioRecordingCallback(AudioManager.AudioRecordingCallback audioRecordingCallback) {
        if (audioRecordingCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback argument");
        }
        synchronized (this.mRecordCallbackLock) {
            Iterator<AudioRecordingCallbackInfo> it = this.mRecordCallbackList.iterator();
            while (it.hasNext()) {
                AudioRecordingCallbackInfo next = it.next();
                if (next.mCb == audioRecordingCallback) {
                    this.mRecordCallbackList.remove(next);
                    if (this.mRecordCallbackList.size() == 0) {
                        endRecordingCallbackHandling();
                    }
                }
            }
            throw new IllegalArgumentException("AudioRecordingCallback was not registered");
        }
    }

    @Override // android.media.AudioRecordingMonitor
    public AudioRecordingConfiguration getActiveRecordingConfiguration() {
        try {
            return getMyConfig(getService().getActiveRecordingConfigurations());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class AudioRecordingCallbackInfo {
        final AudioManager.AudioRecordingCallback mCb;
        final Executor mExecutor;

        AudioRecordingCallbackInfo(Executor executor, AudioManager.AudioRecordingCallback audioRecordingCallback) {
            this.mExecutor = executor;
            this.mCb = audioRecordingCallback;
        }
    }

    private void beginRecordingCallbackHandling() {
        if (this.mRecordingCallbackHandlerThread == null) {
            HandlerThread handlerThread = new HandlerThread("android.media.AudioRecordingMonitor.RecordingCallback");
            this.mRecordingCallbackHandlerThread = handlerThread;
            handlerThread.start();
            Looper looper = this.mRecordingCallbackHandlerThread.getLooper();
            if (looper != null) {
                this.mRecordingCallbackHandler = new AnonymousClass2(looper);
                try {
                    getService().registerRecordingCallback(this.mRecordingCallback);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    /* renamed from: android.media.AudioRecordingMonitorImpl$2, reason: invalid class name */
    class AnonymousClass2 extends Handler {
        AnonymousClass2(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1) {
                if (message.obj == null) {
                    return;
                }
                final ArrayList arrayList = new ArrayList();
                arrayList.add((AudioRecordingConfiguration) message.obj);
                synchronized (AudioRecordingMonitorImpl.this.mRecordCallbackLock) {
                    if (AudioRecordingMonitorImpl.this.mRecordCallbackList.size() == 0) {
                        return;
                    }
                    LinkedList linkedList = new LinkedList(AudioRecordingMonitorImpl.this.mRecordCallbackList);
                    long jClearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        Iterator it = linkedList.iterator();
                        while (it.hasNext()) {
                            final AudioRecordingCallbackInfo audioRecordingCallbackInfo = (AudioRecordingCallbackInfo) it.next();
                            audioRecordingCallbackInfo.mExecutor.execute(new Runnable() { // from class: android.media.AudioRecordingMonitorImpl$2$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    audioRecordingCallbackInfo.mCb.onRecordingConfigChanged(arrayList);
                                }
                            });
                        }
                        return;
                    } finally {
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                    }
                }
            }
            Log.e(AudioRecordingMonitorImpl.TAG, "Unknown event " + message.what);
        }
    }

    private void endRecordingCallbackHandling() {
        if (this.mRecordingCallbackHandlerThread != null) {
            try {
                getService().unregisterRecordingCallback(this.mRecordingCallback);
                this.mRecordingCallbackHandlerThread.quit();
                this.mRecordingCallbackHandlerThread = null;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    AudioRecordingConfiguration getMyConfig(List<AudioRecordingConfiguration> list) {
        int portId = this.mClient.getPortId();
        for (AudioRecordingConfiguration audioRecordingConfiguration : list) {
            if (audioRecordingConfiguration.getClientPortId() == portId) {
                return audioRecordingConfiguration;
            }
        }
        return null;
    }

    private static IAudioService getService() {
        IAudioService iAudioService = sService;
        if (iAudioService != null) {
            return iAudioService;
        }
        IAudioService iAudioServiceAsInterface = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        sService = iAudioServiceAsInterface;
        return iAudioServiceAsInterface;
    }
}
