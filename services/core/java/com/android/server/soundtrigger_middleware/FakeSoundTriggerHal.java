package com.android.server.soundtrigger_middleware;

import android.hardware.soundtrigger3.ISoundTriggerHw;
import android.hardware.soundtrigger3.ISoundTriggerHwCallback;
import android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback;
import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.Phrase;
import android.media.soundtrigger.PhraseRecognitionEvent;
import android.media.soundtrigger.PhraseRecognitionExtra;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.RecognitionEvent;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.IAcknowledgeEvent;
import android.media.soundtrigger_middleware.IInjectGlobalEvent;
import android.media.soundtrigger_middleware.IInjectModelEvent;
import android.media.soundtrigger_middleware.IInjectRecognitionEvent;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ServiceSpecificException;
import android.util.Slog;
import com.android.internal.util.FunctionalUtils;
import com.android.server.soundtrigger_middleware.FakeSoundTriggerHal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class FakeSoundTriggerHal extends ISoundTriggerHw.Stub {
    public IBinder.DeathRecipient mDeathRecipient;
    public GlobalCallbackDispatcher mGlobalCallbackDispatcher;
    public final IInjectGlobalEvent.Stub mGlobalEventSession;
    public final InjectionDispatcher mInjectionDispatcher;
    public final Object mLock = new Object();
    public boolean mIsResourceContended = false;
    public final Map mModelSessionMap = new HashMap();
    public int mModelKeyCounter = 101;
    public boolean mIsDead = false;
    public final Properties mProperties = createDefaultProperties();

    public abstract class ExecutorHolder {
        public static final Executor CALLBACK_EXECUTOR = Executors.newSingleThreadExecutor();
        public static final Executor INJECTION_EXECUTOR = Executors.newSingleThreadExecutor();
    }

    public class ModelSession extends IInjectModelEvent.Stub {
        public final CallbackDispatcher mCallbackDispatcher;
        public final boolean mIsKeyphrase;
        public boolean mIsUnloaded;
        public final int mModelHandle;
        public RecognitionSession mRecognitionSession;
        public int mThreshold;

        public ModelSession(int i, CallbackDispatcher callbackDispatcher, boolean z) {
            this.mThreshold = 0;
            this.mIsUnloaded = false;
            this.mModelHandle = i;
            this.mCallbackDispatcher = callbackDispatcher;
            this.mIsKeyphrase = z;
        }

        public final RecognitionSession startRecognitionForModel() {
            RecognitionSession recognitionSession;
            synchronized (FakeSoundTriggerHal.this.mLock) {
                recognitionSession = new RecognitionSession();
                this.mRecognitionSession = recognitionSession;
            }
            return recognitionSession;
        }

        public final RecognitionSession stopRecognitionForModel() {
            RecognitionSession recognitionSession;
            synchronized (FakeSoundTriggerHal.this.mLock) {
                recognitionSession = this.mRecognitionSession;
                this.mRecognitionSession = null;
            }
            return recognitionSession;
        }

        public final void forceRecognitionForModel() {
            synchronized (FakeSoundTriggerHal.this.mLock) {
                if (this.mIsKeyphrase) {
                    final PhraseRecognitionEvent createDefaultKeyphraseEvent = FakeSoundTriggerHal.createDefaultKeyphraseEvent(3);
                    this.mCallbackDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda2
                        public final void acceptOrThrow(Object obj) {
                            FakeSoundTriggerHal.ModelSession.this.lambda$forceRecognitionForModel$0(createDefaultKeyphraseEvent, (ISoundTriggerHwCallback) obj);
                        }
                    });
                } else {
                    final RecognitionEvent createDefaultEvent = FakeSoundTriggerHal.createDefaultEvent(3);
                    this.mCallbackDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda3
                        public final void acceptOrThrow(Object obj) {
                            FakeSoundTriggerHal.ModelSession.this.lambda$forceRecognitionForModel$1(createDefaultEvent, (ISoundTriggerHwCallback) obj);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$forceRecognitionForModel$0(PhraseRecognitionEvent phraseRecognitionEvent, ISoundTriggerHwCallback iSoundTriggerHwCallback) {
            iSoundTriggerHwCallback.phraseRecognitionCallback(this.mModelHandle, phraseRecognitionEvent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$forceRecognitionForModel$1(RecognitionEvent recognitionEvent, ISoundTriggerHwCallback iSoundTriggerHwCallback) {
            iSoundTriggerHwCallback.recognitionCallback(this.mModelHandle, recognitionEvent);
        }

        public final void setThresholdFactor(int i) {
            synchronized (FakeSoundTriggerHal.this.mLock) {
                this.mThreshold = i;
            }
        }

        public final int getThresholdFactor() {
            int i;
            synchronized (FakeSoundTriggerHal.this.mLock) {
                i = this.mThreshold;
            }
            return i;
        }

        public final boolean getIsUnloaded() {
            boolean z;
            synchronized (FakeSoundTriggerHal.this.mLock) {
                z = this.mIsUnloaded;
            }
            return z;
        }

        public final RecognitionSession getRecogSession() {
            RecognitionSession recognitionSession;
            synchronized (FakeSoundTriggerHal.this.mLock) {
                recognitionSession = this.mRecognitionSession;
            }
            return recognitionSession;
        }

        public void triggerUnloadModel() {
            synchronized (FakeSoundTriggerHal.this.mLock) {
                if (!FakeSoundTriggerHal.this.mIsDead && !this.mIsUnloaded) {
                    RecognitionSession recognitionSession = this.mRecognitionSession;
                    if (recognitionSession != null) {
                        recognitionSession.triggerAbortRecognition();
                    }
                    this.mIsUnloaded = true;
                    this.mCallbackDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda0
                        public final void acceptOrThrow(Object obj) {
                            FakeSoundTriggerHal.ModelSession.this.lambda$triggerUnloadModel$2((ISoundTriggerHwCallback) obj);
                        }
                    });
                    if (FakeSoundTriggerHal.this.getNumLoadedModelsLocked() == FakeSoundTriggerHal.this.mProperties.maxSoundModels - 1 && !FakeSoundTriggerHal.this.mIsResourceContended) {
                        FakeSoundTriggerHal.this.mGlobalCallbackDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda1
                            public final void acceptOrThrow(Object obj) {
                                ((ISoundTriggerHwGlobalCallback) obj).onResourcesAvailable();
                            }
                        });
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerUnloadModel$2(ISoundTriggerHwCallback iSoundTriggerHwCallback) {
            iSoundTriggerHwCallback.modelUnloaded(this.mModelHandle);
        }

        public class RecognitionSession extends IInjectRecognitionEvent.Stub {
            public RecognitionSession() {
            }

            public void triggerRecognitionEvent(byte[] bArr, PhraseRecognitionExtra[] phraseRecognitionExtraArr) {
                synchronized (FakeSoundTriggerHal.this.mLock) {
                    if (!FakeSoundTriggerHal.this.mIsDead && ModelSession.this.mRecognitionSession == this) {
                        ModelSession.this.mRecognitionSession = null;
                        if (ModelSession.this.mIsKeyphrase) {
                            final PhraseRecognitionEvent createDefaultKeyphraseEvent = FakeSoundTriggerHal.createDefaultKeyphraseEvent(0);
                            createDefaultKeyphraseEvent.common.data = bArr;
                            if (phraseRecognitionExtraArr != null) {
                                createDefaultKeyphraseEvent.phraseExtras = phraseRecognitionExtraArr;
                            }
                            ModelSession.this.mCallbackDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$RecognitionSession$$ExternalSyntheticLambda0
                                public final void acceptOrThrow(Object obj) {
                                    FakeSoundTriggerHal.ModelSession.RecognitionSession.this.lambda$triggerRecognitionEvent$0(createDefaultKeyphraseEvent, (ISoundTriggerHwCallback) obj);
                                }
                            });
                        } else {
                            final RecognitionEvent createDefaultEvent = FakeSoundTriggerHal.createDefaultEvent(0);
                            createDefaultEvent.data = bArr;
                            ModelSession.this.mCallbackDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$RecognitionSession$$ExternalSyntheticLambda1
                                public final void acceptOrThrow(Object obj) {
                                    FakeSoundTriggerHal.ModelSession.RecognitionSession.this.lambda$triggerRecognitionEvent$1(createDefaultEvent, (ISoundTriggerHwCallback) obj);
                                }
                            });
                        }
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$triggerRecognitionEvent$0(PhraseRecognitionEvent phraseRecognitionEvent, ISoundTriggerHwCallback iSoundTriggerHwCallback) {
                iSoundTriggerHwCallback.phraseRecognitionCallback(ModelSession.this.mModelHandle, phraseRecognitionEvent);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$triggerRecognitionEvent$1(RecognitionEvent recognitionEvent, ISoundTriggerHwCallback iSoundTriggerHwCallback) {
                iSoundTriggerHwCallback.recognitionCallback(ModelSession.this.mModelHandle, recognitionEvent);
            }

            public void triggerAbortRecognition() {
                synchronized (FakeSoundTriggerHal.this.mLock) {
                    if (!FakeSoundTriggerHal.this.mIsDead && ModelSession.this.mRecognitionSession == this) {
                        ModelSession.this.mRecognitionSession = null;
                        if (ModelSession.this.mIsKeyphrase) {
                            ModelSession.this.mCallbackDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$RecognitionSession$$ExternalSyntheticLambda2
                                public final void acceptOrThrow(Object obj) {
                                    FakeSoundTriggerHal.ModelSession.RecognitionSession.this.lambda$triggerAbortRecognition$2((ISoundTriggerHwCallback) obj);
                                }
                            });
                        } else {
                            ModelSession.this.mCallbackDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$RecognitionSession$$ExternalSyntheticLambda3
                                public final void acceptOrThrow(Object obj) {
                                    FakeSoundTriggerHal.ModelSession.RecognitionSession.this.lambda$triggerAbortRecognition$3((ISoundTriggerHwCallback) obj);
                                }
                            });
                        }
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$triggerAbortRecognition$2(ISoundTriggerHwCallback iSoundTriggerHwCallback) {
                iSoundTriggerHwCallback.phraseRecognitionCallback(ModelSession.this.mModelHandle, FakeSoundTriggerHal.createDefaultKeyphraseEvent(1));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$triggerAbortRecognition$3(ISoundTriggerHwCallback iSoundTriggerHwCallback) {
                iSoundTriggerHwCallback.recognitionCallback(ModelSession.this.mModelHandle, FakeSoundTriggerHal.createDefaultEvent(1));
            }
        }
    }

    public FakeSoundTriggerHal(ISoundTriggerInjection iSoundTriggerInjection) {
        this.mGlobalCallbackDispatcher = null;
        InjectionDispatcher injectionDispatcher = new InjectionDispatcher(iSoundTriggerInjection);
        this.mInjectionDispatcher = injectionDispatcher;
        this.mGlobalCallbackDispatcher = null;
        this.mGlobalEventSession = new C26201();
        injectionDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda7
            public final void acceptOrThrow(Object obj) {
                FakeSoundTriggerHal.this.lambda$new$0((ISoundTriggerInjection) obj);
            }
        });
    }

    /* renamed from: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1 */
    public class C26201 extends IInjectGlobalEvent.Stub {
        public C26201() {
        }

        public void triggerRestart() {
            synchronized (FakeSoundTriggerHal.this.mLock) {
                if (FakeSoundTriggerHal.this.mIsDead) {
                    return;
                }
                FakeSoundTriggerHal.this.mIsDead = true;
                FakeSoundTriggerHal.this.mInjectionDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1$$ExternalSyntheticLambda0
                    public final void acceptOrThrow(Object obj) {
                        FakeSoundTriggerHal.C26201.this.lambda$triggerRestart$0((ISoundTriggerInjection) obj);
                    }
                });
                FakeSoundTriggerHal.this.mModelSessionMap.clear();
                if (FakeSoundTriggerHal.this.mDeathRecipient != null) {
                    final IBinder.DeathRecipient deathRecipient = FakeSoundTriggerHal.this.mDeathRecipient;
                    ExecutorHolder.CALLBACK_EXECUTOR.execute(new Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FakeSoundTriggerHal.C26201.this.lambda$triggerRestart$1(deathRecipient);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerRestart$0(ISoundTriggerInjection iSoundTriggerInjection) {
            iSoundTriggerInjection.onRestarted(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerRestart$1(IBinder.DeathRecipient deathRecipient) {
            try {
                deathRecipient.binderDied(FakeSoundTriggerHal.this.asBinder());
            } catch (Throwable th) {
                Slog.wtf("FakeSoundTriggerHal", "Callback dispatch threw", th);
            }
        }

        public void setResourceContention(boolean z, final IAcknowledgeEvent iAcknowledgeEvent) {
            synchronized (FakeSoundTriggerHal.this.mLock) {
                if (FakeSoundTriggerHal.this.mIsDead) {
                    return;
                }
                boolean z2 = FakeSoundTriggerHal.this.mIsResourceContended;
                FakeSoundTriggerHal.this.mIsResourceContended = z;
                FakeSoundTriggerHal.this.mInjectionDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1$$ExternalSyntheticLambda3
                    public final void acceptOrThrow(Object obj) {
                        iAcknowledgeEvent.eventReceived();
                    }
                });
                if (!FakeSoundTriggerHal.this.mIsResourceContended && z2) {
                    FakeSoundTriggerHal.this.mGlobalCallbackDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1$$ExternalSyntheticLambda4
                        public final void acceptOrThrow(Object obj) {
                            ((ISoundTriggerHwGlobalCallback) obj).onResourcesAvailable();
                        }
                    });
                }
            }
        }

        public void triggerOnResourcesAvailable() {
            synchronized (FakeSoundTriggerHal.this.mLock) {
                if (FakeSoundTriggerHal.this.mIsDead) {
                    return;
                }
                FakeSoundTriggerHal.this.mGlobalCallbackDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1$$ExternalSyntheticLambda2
                    public final void acceptOrThrow(Object obj) {
                        ((ISoundTriggerHwGlobalCallback) obj).onResourcesAvailable();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ISoundTriggerInjection iSoundTriggerInjection) {
        iSoundTriggerInjection.registerGlobalEventInjection(this.mGlobalEventSession);
    }

    public IInjectGlobalEvent getGlobalEventInjection() {
        return this.mGlobalEventSession;
    }

    public void linkToDeath(IBinder.DeathRecipient deathRecipient, int i) {
        synchronized (this.mLock) {
            if (this.mDeathRecipient != null) {
                Slog.wtf("FakeSoundTriggerHal", "Received two death recipients concurrently");
            }
            this.mDeathRecipient = deathRecipient;
        }
    }

    public boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient, int i) {
        synchronized (this.mLock) {
            if (this.mIsDead) {
                return false;
            }
            if (this.mDeathRecipient != deathRecipient) {
                throw new NoSuchElementException();
            }
            this.mDeathRecipient = null;
            return true;
        }
    }

    public Properties getProperties() {
        Properties properties;
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
            Parcel obtain = Parcel.obtain();
            try {
                this.mProperties.writeToParcel(obtain, 0);
                obtain.setDataPosition(0);
                properties = (Properties) Properties.CREATOR.createFromParcel(obtain);
            } finally {
                obtain.recycle();
            }
        }
        return properties;
    }

    public void registerGlobalCallback(ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) {
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
            this.mGlobalCallbackDispatcher = new GlobalCallbackDispatcher(iSoundTriggerHwGlobalCallback);
        }
    }

    public int loadSoundModel(final SoundModel soundModel, ISoundTriggerHwCallback iSoundTriggerHwCallback) {
        int i;
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
            if (this.mIsResourceContended || getNumLoadedModelsLocked() == this.mProperties.maxSoundModels) {
                throw new ServiceSpecificException(1);
            }
            i = this.mModelKeyCounter;
            this.mModelKeyCounter = i + 1;
            final ModelSession modelSession = new ModelSession(i, new CallbackDispatcher(iSoundTriggerHwCallback), false);
            this.mModelSessionMap.put(Integer.valueOf(i), modelSession);
            this.mInjectionDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda4
                public final void acceptOrThrow(Object obj) {
                    FakeSoundTriggerHal.this.lambda$loadSoundModel$1(soundModel, modelSession, (ISoundTriggerInjection) obj);
                }
            });
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadSoundModel$1(SoundModel soundModel, ModelSession modelSession, ISoundTriggerInjection iSoundTriggerInjection) {
        iSoundTriggerInjection.onSoundModelLoaded(soundModel, (Phrase[]) null, modelSession, this.mGlobalEventSession);
    }

    public int loadPhraseSoundModel(final PhraseSoundModel phraseSoundModel, ISoundTriggerHwCallback iSoundTriggerHwCallback) {
        int i;
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
            if (this.mIsResourceContended || getNumLoadedModelsLocked() == this.mProperties.maxSoundModels) {
                throw new ServiceSpecificException(1);
            }
            i = this.mModelKeyCounter;
            this.mModelKeyCounter = i + 1;
            final ModelSession modelSession = new ModelSession(i, new CallbackDispatcher(iSoundTriggerHwCallback), true);
            this.mModelSessionMap.put(Integer.valueOf(i), modelSession);
            this.mInjectionDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda1
                public final void acceptOrThrow(Object obj) {
                    FakeSoundTriggerHal.this.lambda$loadPhraseSoundModel$2(phraseSoundModel, modelSession, (ISoundTriggerInjection) obj);
                }
            });
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPhraseSoundModel$2(PhraseSoundModel phraseSoundModel, ModelSession modelSession, ISoundTriggerInjection iSoundTriggerInjection) {
        iSoundTriggerInjection.onSoundModelLoaded(phraseSoundModel.common, phraseSoundModel.phrases, modelSession, this.mGlobalEventSession);
    }

    public void unloadSoundModel(int i) {
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
            final ModelSession modelSession = (ModelSession) this.mModelSessionMap.get(Integer.valueOf(i));
            if (modelSession == null) {
                Slog.wtf("FakeSoundTriggerHal", "Attempted to unload model which was never loaded");
            }
            if (modelSession.getRecogSession() != null) {
                Slog.wtf("FakeSoundTriggerHal", "Session unloaded before recog stopped!");
            }
            if (modelSession.getIsUnloaded()) {
                return;
            }
            this.mInjectionDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda5
                public final void acceptOrThrow(Object obj) {
                    ((ISoundTriggerInjection) obj).onSoundModelUnloaded(FakeSoundTriggerHal.ModelSession.this);
                }
            });
            if (getNumLoadedModelsLocked() == this.mProperties.maxSoundModels - 1 && !this.mIsResourceContended) {
                this.mGlobalCallbackDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda6
                    public final void acceptOrThrow(Object obj) {
                        ((ISoundTriggerHwGlobalCallback) obj).onResourcesAvailable();
                    }
                });
            }
        }
    }

    public void startRecognition(int i, int i2, int i3, final RecognitionConfig recognitionConfig) {
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
            final ModelSession modelSession = (ModelSession) this.mModelSessionMap.get(Integer.valueOf(i));
            if (modelSession == null) {
                Slog.wtf("FakeSoundTriggerHal", "Attempted to start recognition with invalid handle");
            }
            if (this.mIsResourceContended) {
                throw new ServiceSpecificException(1);
            }
            if (modelSession.getIsUnloaded()) {
                throw new ServiceSpecificException(1);
            }
            final ModelSession.RecognitionSession startRecognitionForModel = modelSession.startRecognitionForModel();
            this.mInjectionDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda3
                public final void acceptOrThrow(Object obj) {
                    ((ISoundTriggerInjection) obj).onRecognitionStarted(-1, recognitionConfig, startRecognitionForModel, modelSession);
                }
            });
        }
    }

    public void stopRecognition(int i) {
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
            ModelSession modelSession = (ModelSession) this.mModelSessionMap.get(Integer.valueOf(i));
            if (modelSession == null) {
                Slog.wtf("FakeSoundTriggerHal", "Attempted to stop recognition with invalid handle");
            }
            final ModelSession.RecognitionSession stopRecognitionForModel = modelSession.stopRecognitionForModel();
            if (stopRecognitionForModel != null) {
                this.mInjectionDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda2
                    public final void acceptOrThrow(Object obj) {
                        ((ISoundTriggerInjection) obj).onRecognitionStopped(FakeSoundTriggerHal.ModelSession.RecognitionSession.this);
                    }
                });
            }
        }
    }

    public void forceRecognitionEvent(int i) {
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
            ModelSession modelSession = (ModelSession) this.mModelSessionMap.get(Integer.valueOf(i));
            if (modelSession == null) {
                Slog.wtf("FakeSoundTriggerHal", "Attempted to force recognition with invalid handle");
            }
            if (modelSession.getRecogSession() == null) {
                return;
            }
            modelSession.forceRecognitionForModel();
        }
    }

    public ModelParameterRange queryParameter(int i, int i2) {
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
            if (((ModelSession) this.mModelSessionMap.get(Integer.valueOf(i))) == null) {
                Slog.wtf("FakeSoundTriggerHal", "Attempted to get param with invalid handle");
            }
        }
        if (i2 != 0) {
            return null;
        }
        ModelParameterRange modelParameterRange = new ModelParameterRange();
        modelParameterRange.minInclusive = -10;
        modelParameterRange.maxInclusive = 10;
        return modelParameterRange;
    }

    public int getParameter(int i, int i2) {
        int thresholdFactor;
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
            ModelSession modelSession = (ModelSession) this.mModelSessionMap.get(Integer.valueOf(i));
            if (modelSession == null) {
                Slog.wtf("FakeSoundTriggerHal", "Attempted to get param with invalid handle");
            }
            if (i2 != 0) {
                throw new IllegalArgumentException();
            }
            thresholdFactor = modelSession.getThresholdFactor();
        }
        return thresholdFactor;
    }

    public void setParameter(int i, final int i2, final int i3) {
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
            final ModelSession modelSession = (ModelSession) this.mModelSessionMap.get(Integer.valueOf(i));
            if (modelSession == null) {
                Slog.wtf("FakeSoundTriggerHal", "Attempted to get param with invalid handle");
            }
            if (i2 != 0 && (i3 < -10 || i3 > 10)) {
                throw new IllegalArgumentException();
            }
            modelSession.setThresholdFactor(i3);
            this.mInjectionDispatcher.wrap(new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda0
                public final void acceptOrThrow(Object obj) {
                    ((ISoundTriggerInjection) obj).onParamSet(i2, i3, modelSession);
                }
            });
        }
    }

    public int getInterfaceVersion() {
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
        }
        return 1;
    }

    public String getInterfaceHash() {
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
        }
        return "7d8d63478cd50e766d2072140c8aa3457f9fb585";
    }

    public final int getNumLoadedModelsLocked() {
        Iterator it = this.mModelSessionMap.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (!((ModelSession) it.next()).getIsUnloaded()) {
                i++;
            }
        }
        return i;
    }

    public static Properties createDefaultProperties() {
        Properties properties = new Properties();
        properties.implementor = "android";
        properties.description = "AOSP fake STHAL";
        properties.version = 1;
        properties.uuid = "00000001-0002-0003-0004-deadbeefabcd";
        properties.supportedModelArch = "injection";
        properties.maxSoundModels = 8;
        properties.maxKeyPhrases = 2;
        properties.maxUsers = 2;
        properties.recognitionModes = 9;
        properties.captureTransition = true;
        properties.maxBufferMs = 5000;
        properties.concurrentCapture = true;
        properties.triggerInEvent = false;
        properties.powerConsumptionMw = 0;
        properties.audioCapabilities = 0;
        return properties;
    }

    public static RecognitionEvent createDefaultEvent(int i) {
        RecognitionEvent recognitionEvent = new RecognitionEvent();
        recognitionEvent.status = i;
        recognitionEvent.type = 1;
        recognitionEvent.captureAvailable = true;
        recognitionEvent.captureDelayMs = 50;
        recognitionEvent.capturePreambleMs = 200;
        recognitionEvent.triggerInData = false;
        recognitionEvent.audioConfig = null;
        recognitionEvent.data = new byte[0];
        recognitionEvent.recognitionStillActive = false;
        return recognitionEvent;
    }

    public static PhraseRecognitionEvent createDefaultKeyphraseEvent(int i) {
        RecognitionEvent createDefaultEvent = createDefaultEvent(i);
        createDefaultEvent.type = 0;
        PhraseRecognitionEvent phraseRecognitionEvent = new PhraseRecognitionEvent();
        phraseRecognitionEvent.common = createDefaultEvent;
        phraseRecognitionEvent.phraseExtras = new PhraseRecognitionExtra[0];
        return phraseRecognitionEvent;
    }

    public class CallbackDispatcher {
        public final ISoundTriggerHwCallback mCallback;

        public CallbackDispatcher(ISoundTriggerHwCallback iSoundTriggerHwCallback) {
            this.mCallback = iSoundTriggerHwCallback;
        }

        public final void wrap(final FunctionalUtils.ThrowingConsumer throwingConsumer) {
            ExecutorHolder.CALLBACK_EXECUTOR.execute(new Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$CallbackDispatcher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FakeSoundTriggerHal.CallbackDispatcher.this.lambda$wrap$0(throwingConsumer);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wrap$0(FunctionalUtils.ThrowingConsumer throwingConsumer) {
            try {
                throwingConsumer.accept(this.mCallback);
            } catch (Throwable th) {
                Slog.wtf("FakeSoundTriggerHal", "Callback dispatch threw", th);
            }
        }
    }

    public class GlobalCallbackDispatcher {
        public final ISoundTriggerHwGlobalCallback mCallback;

        public GlobalCallbackDispatcher(ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) {
            this.mCallback = iSoundTriggerHwGlobalCallback;
        }

        public final void wrap(final FunctionalUtils.ThrowingConsumer throwingConsumer) {
            ExecutorHolder.CALLBACK_EXECUTOR.execute(new Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$GlobalCallbackDispatcher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FakeSoundTriggerHal.GlobalCallbackDispatcher.this.lambda$wrap$0(throwingConsumer);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wrap$0(FunctionalUtils.ThrowingConsumer throwingConsumer) {
            try {
                throwingConsumer.accept(this.mCallback);
            } catch (Throwable th) {
                Slog.wtf("FakeSoundTriggerHal", "Callback dispatch threw", th);
            }
        }
    }

    public class InjectionDispatcher {
        public final ISoundTriggerInjection mInjection;

        public InjectionDispatcher(ISoundTriggerInjection iSoundTriggerInjection) {
            this.mInjection = iSoundTriggerInjection;
        }

        public final void wrap(final FunctionalUtils.ThrowingConsumer throwingConsumer) {
            ExecutorHolder.INJECTION_EXECUTOR.execute(new Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$InjectionDispatcher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FakeSoundTriggerHal.InjectionDispatcher.this.lambda$wrap$0(throwingConsumer);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wrap$0(FunctionalUtils.ThrowingConsumer throwingConsumer) {
            try {
                throwingConsumer.accept(this.mInjection);
            } catch (Throwable th) {
                Slog.wtf("FakeSoundTriggerHal", "Callback dispatch threw", th);
            }
        }
    }
}
