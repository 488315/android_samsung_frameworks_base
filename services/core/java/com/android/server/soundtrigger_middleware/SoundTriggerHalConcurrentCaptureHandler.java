package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;
import android.os.IBinder;
import com.android.server.soundtrigger_middleware.ICaptureStateNotifier;
import com.android.server.soundtrigger_middleware.ISoundTriggerHal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SoundTriggerHalConcurrentCaptureHandler implements ISoundTriggerHal, ICaptureStateNotifier.Listener {
    public boolean mCaptureState;
    public final ISoundTriggerHal mDelegate;
    public SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0 mGlobalCallback;
    public final ICaptureStateNotifier mNotifier;
    public final Object mStartStopLock = new Object();
    public final Map mLoadedModels = new ConcurrentHashMap();
    public final Set mActiveModels = new HashSet();
    public final Map mDeathRecipientMap = new ConcurrentHashMap();
    public final CallbackThread mCallbackThread = new CallbackThread();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallbackThread implements Runnable {
        public final Queue mList = new LinkedList();
        public int mPushCount = 0;
        public int mProcessedCount = 0;
        public boolean mQuitting = false;

        public CallbackThread() {
            new Thread(this, "STHAL Concurrent Capture Handler Callback").start();
        }

        public final Runnable pop() {
            synchronized (this.mList) {
                while (this.mList.isEmpty() && !this.mQuitting) {
                    try {
                        this.mList.wait();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (this.mList.isEmpty() && this.mQuitting) {
                    return null;
                }
                return (Runnable) ((LinkedList) this.mList).remove();
            }
        }

        public final void push(Runnable runnable) {
            synchronized (this.mList) {
                try {
                    if (this.mQuitting) {
                        return;
                    }
                    ((LinkedList) this.mList).add(runnable);
                    this.mPushCount++;
                    this.mList.notifyAll();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            while (true) {
                try {
                    Runnable pop = pop();
                    if (pop == null) {
                        return;
                    }
                    pop.run();
                    synchronized (this.mList) {
                        this.mProcessedCount++;
                        this.mList.notifyAll();
                    }
                } catch (InterruptedException unused) {
                    return;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallbackWrapper implements ISoundTriggerHal.ModelCallback {
        public final ISoundTriggerHal.ModelCallback mDelegateCallback;

        public CallbackWrapper(ISoundTriggerHal.ModelCallback modelCallback) {
            this.mDelegateCallback = modelCallback;
        }

        @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
        public final void modelUnloaded(int i) {
            SoundTriggerHalConcurrentCaptureHandler.this.mCallbackThread.push(new SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda1(this, i));
        }

        @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
        public final void phraseRecognitionCallback(int i, PhraseRecognitionEventSys phraseRecognitionEventSys) {
            synchronized (SoundTriggerHalConcurrentCaptureHandler.this.mActiveModels) {
                try {
                    if (((HashSet) SoundTriggerHalConcurrentCaptureHandler.this.mActiveModels).contains(Integer.valueOf(i))) {
                        if (!phraseRecognitionEventSys.phraseRecognitionEvent.common.recognitionStillActive) {
                            ((HashSet) SoundTriggerHalConcurrentCaptureHandler.this.mActiveModels).remove(Integer.valueOf(i));
                        }
                        SoundTriggerHalConcurrentCaptureHandler.this.mCallbackThread.push(new SoundTriggerHalConcurrentCaptureHandler$CallbackWrapper$$ExternalSyntheticLambda0(this, i, phraseRecognitionEventSys));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
        public final void recognitionCallback(int i, RecognitionEventSys recognitionEventSys) {
            synchronized (SoundTriggerHalConcurrentCaptureHandler.this.mActiveModels) {
                try {
                    if (((HashSet) SoundTriggerHalConcurrentCaptureHandler.this.mActiveModels).contains(Integer.valueOf(i))) {
                        if (!recognitionEventSys.recognitionEvent.recognitionStillActive) {
                            ((HashSet) SoundTriggerHalConcurrentCaptureHandler.this.mActiveModels).remove(Integer.valueOf(i));
                        }
                        SoundTriggerHalConcurrentCaptureHandler.this.mCallbackThread.push(new SoundTriggerHalConcurrentCaptureHandler$CallbackWrapper$$ExternalSyntheticLambda0(this, i, recognitionEventSys));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LoadedModel {
        public final ISoundTriggerHal.ModelCallback callback;
        public final int type;

        public LoadedModel(int i, ISoundTriggerHal.ModelCallback modelCallback) {
            this.type = i;
            this.callback = modelCallback;
        }
    }

    public SoundTriggerHalConcurrentCaptureHandler(SoundTriggerHalMaxModelLimiter soundTriggerHalMaxModelLimiter, ICaptureStateNotifier iCaptureStateNotifier) {
        this.mDelegate = soundTriggerHalMaxModelLimiter;
        this.mNotifier = iCaptureStateNotifier;
        this.mCaptureState = ((ExternalCaptureStateTracker) iCaptureStateNotifier).registerListener(this);
    }

    public final void abortAllActiveModels() {
        Integer num;
        int intValue;
        while (true) {
            synchronized (this.mActiveModels) {
                try {
                    Iterator it = ((HashSet) this.mActiveModels).iterator();
                    if (!it.hasNext()) {
                        return;
                    }
                    num = (Integer) it.next();
                    intValue = num.intValue();
                    ((HashSet) this.mActiveModels).remove(num);
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mDelegate.stopRecognition(intValue);
            this.mCallbackThread.push(new SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda1(intValue, (LoadedModel) ((ConcurrentHashMap) this.mLoadedModels).get(num)));
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void clientAttached(IBinder iBinder) {
        this.mDelegate.clientAttached(iBinder);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void clientDetached(IBinder iBinder) {
        this.mDelegate.clientDetached(iBinder);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void detach() {
        this.mDelegate.detach();
        this.mNotifier.unregisterListener(this);
        CallbackThread callbackThread = this.mCallbackThread;
        synchronized (callbackThread.mList) {
            callbackThread.mQuitting = true;
            callbackThread.mList.notifyAll();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void forceRecognitionEvent(int i) {
        this.mDelegate.forceRecognitionEvent(i);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int getModelParameter(int i, int i2) {
        return this.mDelegate.getModelParameter(i, i2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final Properties getProperties() {
        return this.mDelegate.getProperties();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void linkToDeath(final IBinder.DeathRecipient deathRecipient) {
        IBinder.DeathRecipient deathRecipient2 = new IBinder.DeathRecipient() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda2
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                SoundTriggerHalConcurrentCaptureHandler soundTriggerHalConcurrentCaptureHandler = SoundTriggerHalConcurrentCaptureHandler.this;
                IBinder.DeathRecipient deathRecipient3 = deathRecipient;
                soundTriggerHalConcurrentCaptureHandler.getClass();
                Objects.requireNonNull(deathRecipient3);
                soundTriggerHalConcurrentCaptureHandler.mCallbackThread.push(new SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda3(1, deathRecipient3));
            }
        };
        this.mDelegate.linkToDeath(deathRecipient2);
        ((ConcurrentHashMap) this.mDeathRecipientMap).put(deathRecipient, deathRecipient2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadPhraseSoundModel(PhraseSoundModel phraseSoundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        int loadPhraseSoundModel = this.mDelegate.loadPhraseSoundModel(phraseSoundModel, new CallbackWrapper(modelCallback));
        ((ConcurrentHashMap) this.mLoadedModels).put(Integer.valueOf(loadPhraseSoundModel), new LoadedModel(0, modelCallback));
        return loadPhraseSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadSoundModel(SoundModel soundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        int loadSoundModel = this.mDelegate.loadSoundModel(soundModel, new CallbackWrapper(modelCallback));
        ((ConcurrentHashMap) this.mLoadedModels).put(Integer.valueOf(loadSoundModel), new LoadedModel(1, modelCallback));
        return loadSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final ModelParameterRange queryParameter(int i, int i2) {
        return this.mDelegate.queryParameter(i, i2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void reboot() {
        this.mDelegate.reboot();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void registerCallback(ISoundTriggerHal.GlobalCallback globalCallback) {
        SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0 soundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0 = new SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0(this, globalCallback);
        this.mGlobalCallback = soundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0;
        this.mDelegate.registerCallback(soundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void setModelParameter(int i, int i2, int i3) {
        this.mDelegate.setModelParameter(i, i2, i3);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) {
        synchronized (this.mStartStopLock) {
            synchronized (this.mActiveModels) {
                if (this.mCaptureState) {
                    throw new RecoverableException(1);
                }
                this.mDelegate.startRecognition(i, i2, i3, recognitionConfig);
                ((HashSet) this.mActiveModels).add(Integer.valueOf(i));
            }
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void stopRecognition(int i) {
        boolean remove;
        synchronized (this.mStartStopLock) {
            try {
                synchronized (this.mActiveModels) {
                    remove = ((HashSet) this.mActiveModels).remove(Integer.valueOf(i));
                }
                if (remove) {
                    this.mDelegate.stopRecognition(i);
                }
            } finally {
            }
        }
        CallbackThread callbackThread = this.mCallbackThread;
        callbackThread.getClass();
        try {
            synchronized (callbackThread.mList) {
                try {
                    int i2 = callbackThread.mPushCount;
                    while (callbackThread.mProcessedCount < i2) {
                        callbackThread.mList.wait();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (InterruptedException unused) {
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void unloadSoundModel(int i) {
        ((ConcurrentHashMap) this.mLoadedModels).remove(Integer.valueOf(i));
        this.mDelegate.unloadSoundModel(i);
    }
}
