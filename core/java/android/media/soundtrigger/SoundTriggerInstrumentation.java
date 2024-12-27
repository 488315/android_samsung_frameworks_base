package android.media.soundtrigger;

import android.hardware.soundtrigger.ConversionUtil;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.soundtrigger_middleware.IAcknowledgeEvent;
import android.media.soundtrigger_middleware.IInjectGlobalEvent;
import android.media.soundtrigger_middleware.IInjectModelEvent;
import android.media.soundtrigger_middleware.IInjectRecognitionEvent;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.internal.app.ISoundTriggerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class SoundTriggerInstrumentation {
    private final GlobalCallback mClientCallback;
    private final Executor mGlobalCallbackExecutor;
    private final ISoundTriggerService mService;
    private final Object mLock = new Object();
    private IInjectGlobalEvent mInjectGlobalEvent = null;
    private Map<IBinder, ModelSession> mModelSessionMap = new HashMap();
    private Map<IBinder, RecognitionSession> mRecognitionSessionMap = new HashMap();
    private IBinder mClientToken = null;

    public interface RecognitionCallback {
        void onRecognitionStopped();
    }

    public interface GlobalCallback {
        void onModelLoaded(ModelSession modelSession);

        default void onPreempted() {}

        default void onRestarted() {}

        default void onFrameworkDetached() {}

        default void onClientAttached() {}

        default void onClientDetached() {}
    }

    public interface ModelCallback {
        void onRecognitionStarted(RecognitionSession recognitionSession);

        default void onModelUnloaded() {}

        default void onParamSet(int param, int value) {}
    }

    public class ModelSession {
        private final List<Consumer<ModelCallback>> mDroppedConsumerList;
        private final IInjectModelEvent mInjectModelEvent;
        private final SoundTriggerManager.Model mModel;
        private ModelCallback mModelCallback;
        private Executor mModelExecutor;
        private final SoundTrigger.Keyphrase[] mPhrases;

        public void triggerUnloadModel() {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                try {
                    try {
                        this.mInjectModelEvent.triggerUnloadModel();
                        SoundTriggerInstrumentation.this.mModelSessionMap.remove(
                                this.mInjectModelEvent.asBinder());
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public SoundTriggerManager.Model getSoundModel() {
            return this.mModel;
        }

        public List<SoundTrigger.Keyphrase> getPhrases() {
            if (this.mPhrases == null) {
                return new ArrayList();
            }
            return new ArrayList(Arrays.asList(this.mPhrases));
        }

        public boolean isKeyphrase() {
            return this.mPhrases != null;
        }

        public void setModelCallback(Executor executor, final ModelCallback callback) {
            Objects.requireNonNull(callback);
            Objects.requireNonNull(executor);
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (this.mModelCallback == null) {
                    for (final Consumer<ModelCallback> droppedConsumer :
                            this.mDroppedConsumerList) {
                        executor.execute(
                                new Runnable() { // from class:
                                    // android.media.soundtrigger.SoundTriggerInstrumentation$ModelSession$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        droppedConsumer.accept(callback);
                                    }
                                });
                    }
                    this.mDroppedConsumerList.clear();
                }
                this.mModelCallback = callback;
                this.mModelExecutor = executor;
            }
        }

        public void clearModelCallback() {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                this.mModelCallback = null;
                this.mModelExecutor = null;
            }
        }

        private ModelSession(SoundModel model, Phrase[] phrases, IInjectModelEvent injection) {
            this.mModelCallback = null;
            this.mModelExecutor = null;
            this.mDroppedConsumerList = new ArrayList();
            this.mModel =
                    SoundTriggerManager.Model.create(
                            UUID.fromString(model.uuid),
                            UUID.fromString(model.vendorUuid),
                            ConversionUtil.sharedMemoryToByteArray(model.data, model.dataSize));
            if (phrases != null) {
                this.mPhrases = new SoundTrigger.Keyphrase[phrases.length];
                int i = 0;
                int length = phrases.length;
                int i2 = 0;
                while (i2 < length) {
                    Phrase phrase = phrases[i2];
                    this.mPhrases[i] = ConversionUtil.aidl2apiPhrase(phrase);
                    i2++;
                    i++;
                }
            } else {
                this.mPhrases = null;
            }
            this.mInjectModelEvent = injection;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void wrap(final Consumer<ModelCallback> consumer) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (this.mModelCallback != null) {
                    final ModelCallback callback = this.mModelCallback;
                    this.mModelExecutor.execute(
                            new Runnable() { // from class:
                                // android.media.soundtrigger.SoundTriggerInstrumentation$ModelSession$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    consumer.accept(callback);
                                }
                            });
                } else {
                    this.mDroppedConsumerList.add(consumer);
                }
            }
        }
    }

    public class RecognitionSession {
        private final int mAudioSession;
        private final List<Consumer<RecognitionCallback>> mDroppedConsumerList;
        private final IInjectRecognitionEvent mInjectRecognitionEvent;
        private RecognitionCallback mRecognitionCallback;
        private final SoundTrigger.RecognitionConfig mRecognitionConfig;
        private Executor mRecognitionExecutor;

        public int getAudioSession() {
            return this.mAudioSession;
        }

        public SoundTrigger.RecognitionConfig getRecognitionConfig() {
            return this.mRecognitionConfig;
        }

        public void triggerRecognitionEvent(
                byte[] data, List<SoundTrigger.KeyphraseRecognitionExtra> phraseExtras) {
            PhraseRecognitionExtra[] converted = null;
            if (phraseExtras != null) {
                converted = new PhraseRecognitionExtra[phraseExtras.size()];
                int i = 0;
                for (SoundTrigger.KeyphraseRecognitionExtra phraseExtra : phraseExtras) {
                    converted[i] = ConversionUtil.api2aidlPhraseRecognitionExtra(phraseExtra);
                    i++;
                }
            }
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                SoundTriggerInstrumentation.this.mRecognitionSessionMap.remove(
                        this.mInjectRecognitionEvent.asBinder());
                try {
                    this.mInjectRecognitionEvent.triggerRecognitionEvent(data, converted);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        public void triggerAbortRecognition() {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                SoundTriggerInstrumentation.this.mRecognitionSessionMap.remove(
                        this.mInjectRecognitionEvent.asBinder());
                try {
                    this.mInjectRecognitionEvent.triggerAbortRecognition();
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        public void setRecognitionCallback(Executor executor, final RecognitionCallback callback) {
            Objects.requireNonNull(callback);
            Objects.requireNonNull(executor);
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (this.mRecognitionCallback == null) {
                    for (final Consumer<RecognitionCallback> droppedConsumer :
                            this.mDroppedConsumerList) {
                        executor.execute(
                                new Runnable() { // from class:
                                    // android.media.soundtrigger.SoundTriggerInstrumentation$RecognitionSession$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        droppedConsumer.accept(callback);
                                    }
                                });
                    }
                    this.mDroppedConsumerList.clear();
                }
                this.mRecognitionCallback = callback;
                this.mRecognitionExecutor = executor;
            }
        }

        public void clearRecognitionCallback() {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                this.mRecognitionCallback = null;
                this.mRecognitionExecutor = null;
            }
        }

        private RecognitionSession(
                int audioSession,
                RecognitionConfig recognitionConfig,
                IInjectRecognitionEvent injectRecognitionEvent) {
            this.mRecognitionExecutor = null;
            this.mRecognitionCallback = null;
            this.mDroppedConsumerList = new ArrayList();
            this.mAudioSession = audioSession;
            this.mRecognitionConfig = ConversionUtil.aidl2apiRecognitionConfig(recognitionConfig);
            this.mInjectRecognitionEvent = injectRecognitionEvent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void wrap(final Consumer<RecognitionCallback> consumer) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (this.mRecognitionCallback != null) {
                    final RecognitionCallback callback = this.mRecognitionCallback;
                    this.mRecognitionExecutor.execute(
                            new Runnable() { // from class:
                                // android.media.soundtrigger.SoundTriggerInstrumentation$RecognitionSession$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    consumer.accept(callback);
                                }
                            });
                } else {
                    this.mDroppedConsumerList.add(consumer);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Injection extends ISoundTriggerInjection.Stub {
        private Injection() {}

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void registerGlobalEventInjection(IInjectGlobalEvent globalInjection) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                SoundTriggerInstrumentation.this.mInjectGlobalEvent = globalInjection;
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onSoundModelLoaded(
                SoundModel model,
                Phrase[] phrases,
                IInjectModelEvent modelInjection,
                IInjectGlobalEvent globalSession) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (globalSession.asBinder()
                        != SoundTriggerInstrumentation.this.mInjectGlobalEvent.asBinder()) {
                    return;
                }
                final ModelSession modelSession = new ModelSession(model, phrases, modelInjection);
                SoundTriggerInstrumentation.this.mModelSessionMap.put(
                        modelInjection.asBinder(), modelSession);
                SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(
                        new Runnable() { // from class:
                            // android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                SoundTriggerInstrumentation.Injection.this
                                        .lambda$onSoundModelLoaded$0(modelSession);
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSoundModelLoaded$0(ModelSession modelSession) {
            SoundTriggerInstrumentation.this.mClientCallback.onModelLoaded(modelSession);
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onSoundModelUnloaded(IInjectModelEvent modelSession) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                ModelSession clientModelSession =
                        (ModelSession)
                                SoundTriggerInstrumentation.this.mModelSessionMap.remove(
                                        modelSession.asBinder());
                if (clientModelSession == null) {
                    return;
                }
                clientModelSession.wrap(
                        new Consumer() { // from class:
                            // android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda8
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((SoundTriggerInstrumentation.ModelCallback) obj).onModelUnloaded();
                            }
                        });
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRecognitionStarted(
                int audioSessionHandle,
                RecognitionConfig config,
                IInjectRecognitionEvent recognitionInjection,
                IInjectModelEvent modelSession) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                ModelSession clientModelSession =
                        (ModelSession)
                                SoundTriggerInstrumentation.this.mModelSessionMap.get(
                                        modelSession.asBinder());
                if (clientModelSession == null) {
                    return;
                }
                final RecognitionSession recogSession =
                        new RecognitionSession(audioSessionHandle, config, recognitionInjection);
                SoundTriggerInstrumentation.this.mRecognitionSessionMap.put(
                        recognitionInjection.asBinder(), recogSession);
                clientModelSession.wrap(
                        new Consumer() { // from class:
                            // android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((SoundTriggerInstrumentation.ModelCallback) obj)
                                        .onRecognitionStarted(
                                                SoundTriggerInstrumentation.RecognitionSession
                                                        .this);
                            }
                        });
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRecognitionStopped(IInjectRecognitionEvent recognitionSession) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                RecognitionSession clientRecognitionSession =
                        (RecognitionSession)
                                SoundTriggerInstrumentation.this.mRecognitionSessionMap.remove(
                                        recognitionSession.asBinder());
                if (clientRecognitionSession == null) {
                    return;
                }
                clientRecognitionSession.wrap(
                        new Consumer() { // from class:
                            // android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda7
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((SoundTriggerInstrumentation.RecognitionCallback) obj)
                                        .onRecognitionStopped();
                            }
                        });
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onParamSet(
                final int modelParam, final int value, IInjectModelEvent modelSession) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                ModelSession clientModelSession =
                        (ModelSession)
                                SoundTriggerInstrumentation.this.mModelSessionMap.get(
                                        modelSession.asBinder());
                if (clientModelSession == null) {
                    return;
                }
                clientModelSession.wrap(
                        new Consumer() { // from class:
                            // android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda6
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((SoundTriggerInstrumentation.ModelCallback) obj)
                                        .onParamSet(modelParam, value);
                            }
                        });
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRestarted(IInjectGlobalEvent globalSession) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (globalSession.asBinder()
                        != SoundTriggerInstrumentation.this.mInjectGlobalEvent.asBinder()) {
                    return;
                }
                SoundTriggerInstrumentation.this.mRecognitionSessionMap.clear();
                SoundTriggerInstrumentation.this.mModelSessionMap.clear();
                SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(
                        new Runnable() { // from class:
                            // android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                SoundTriggerInstrumentation.Injection.this.lambda$onRestarted$5();
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRestarted$5() {
            SoundTriggerInstrumentation.this.mClientCallback.onRestarted();
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onFrameworkDetached(IInjectGlobalEvent globalSession) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (globalSession.asBinder()
                        != SoundTriggerInstrumentation.this.mInjectGlobalEvent.asBinder()) {
                    return;
                }
                SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(
                        new Runnable() { // from class:
                            // android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda9
                            @Override // java.lang.Runnable
                            public final void run() {
                                SoundTriggerInstrumentation.Injection.this
                                        .lambda$onFrameworkDetached$6();
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameworkDetached$6() {
            SoundTriggerInstrumentation.this.mClientCallback.onFrameworkDetached();
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onClientAttached(IBinder token, IInjectGlobalEvent globalSession) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (globalSession.asBinder()
                        != SoundTriggerInstrumentation.this.mInjectGlobalEvent.asBinder()) {
                    return;
                }
                SoundTriggerInstrumentation.this.mClientToken = token;
                SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(
                        new Runnable() { // from class:
                            // android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                SoundTriggerInstrumentation.Injection.this
                                        .lambda$onClientAttached$7();
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClientAttached$7() {
            SoundTriggerInstrumentation.this.mClientCallback.onClientAttached();
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onClientDetached(IBinder token) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (token != SoundTriggerInstrumentation.this.mClientToken) {
                    return;
                }
                SoundTriggerInstrumentation.this.mClientToken = null;
                SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(
                        new Runnable() { // from class:
                            // android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                SoundTriggerInstrumentation.Injection.this
                                        .lambda$onClientDetached$8();
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClientDetached$8() {
            SoundTriggerInstrumentation.this.mClientCallback.onClientDetached();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreempted$9() {
            SoundTriggerInstrumentation.this.mClientCallback.onPreempted();
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onPreempted() {
            SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(
                    new Runnable() { // from class:
                        // android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SoundTriggerInstrumentation.Injection.this.lambda$onPreempted$9();
                        }
                    });
        }
    }

    public SoundTriggerInstrumentation(
            ISoundTriggerService service, Executor executor, GlobalCallback callback) {
        this.mClientCallback = (GlobalCallback) Objects.requireNonNull(callback);
        this.mGlobalCallbackExecutor = (Executor) Objects.requireNonNull(executor);
        this.mService = service;
        try {
            service.attachInjection(new Injection());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void triggerRestart() {
        synchronized (this.mLock) {
            if (this.mInjectGlobalEvent == null) {
                throw new IllegalStateException(
                        "Attempted to trigger HAL restart before registration");
            }
            try {
                this.mInjectGlobalEvent.triggerRestart();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void triggerOnResourcesAvailable() {
        synchronized (this.mLock) {
            if (this.mInjectGlobalEvent == null) {
                throw new IllegalStateException(
                        "Attempted to trigger HAL resources available before registration");
            }
            try {
                this.mInjectGlobalEvent.triggerOnResourcesAvailable();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setResourceContention(boolean isResourceContended) {
        synchronized (this.mLock) {
            if (this.mInjectGlobalEvent == null) {
                throw new IllegalStateException("Injection interface not set up");
            }
            IInjectGlobalEvent current = this.mInjectGlobalEvent;
            final CountDownLatch signal = new CountDownLatch(1);
            try {
                current.setResourceContention(
                        isResourceContended,
                        new IAcknowledgeEvent.Stub() { // from class:
                            // android.media.soundtrigger.SoundTriggerInstrumentation.1
                            @Override // android.media.soundtrigger_middleware.IAcknowledgeEvent
                            public void eventReceived() {
                                signal.countDown();
                            }
                        });
                try {
                    signal.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
    }

    public void setInPhoneCallState(boolean isInPhoneCall) {
        try {
            this.mService.setInPhoneCallState(isInPhoneCall);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
