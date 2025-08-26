package android.media.soundtrigger;

import android.hardware.soundtrigger.ConversionUtil;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.soundtrigger.SoundTriggerInstrumentation;
import android.media.soundtrigger.SoundTriggerManager;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class SoundTriggerInstrumentation {
    private final GlobalCallback mClientCallback;
    private final Executor mGlobalCallbackExecutor;
    private final ISoundTriggerService mService;
    private final Object mLock = new Object();
    private IInjectGlobalEvent mInjectGlobalEvent = null;
    private Map<IBinder, ModelSession> mModelSessionMap = new HashMap();
    private Map<IBinder, RecognitionSession> mRecognitionSessionMap = new HashMap();
    private IBinder mClientToken = null;

    public interface GlobalCallback {
        default void onClientAttached() {
        }

        default void onClientDetached() {
        }

        default void onFrameworkDetached() {
        }

        void onModelLoaded(ModelSession modelSession);

        default void onPreempted() {
        }

        default void onRestarted() {
        }
    }

    public interface ModelCallback {
        default void onModelUnloaded() {
        }

        default void onParamSet(int i, int i2) {
        }

        void onRecognitionStarted(RecognitionSession recognitionSession);
    }

    public interface RecognitionCallback {
        void onRecognitionStopped();
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
                        SoundTriggerInstrumentation.this.mModelSessionMap.remove(this.mInjectModelEvent.asBinder());
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

        public void setModelCallback(Executor executor, final ModelCallback modelCallback) {
            Objects.requireNonNull(modelCallback);
            Objects.requireNonNull(executor);
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (this.mModelCallback == null) {
                    for (final Consumer<ModelCallback> consumer : this.mDroppedConsumerList) {
                        executor.execute(new Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$ModelSession$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(modelCallback);
                            }
                        });
                    }
                    this.mDroppedConsumerList.clear();
                }
                this.mModelCallback = modelCallback;
                this.mModelExecutor = executor;
            }
        }

        public void clearModelCallback() {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                this.mModelCallback = null;
                this.mModelExecutor = null;
            }
        }

        private ModelSession(SoundModel soundModel, Phrase[] phraseArr, IInjectModelEvent iInjectModelEvent) {
            this.mModelCallback = null;
            this.mModelExecutor = null;
            this.mDroppedConsumerList = new ArrayList();
            this.mModel = SoundTriggerManager.Model.create(UUID.fromString(soundModel.uuid), UUID.fromString(soundModel.vendorUuid), ConversionUtil.sharedMemoryToByteArray(soundModel.data, soundModel.dataSize));
            if (phraseArr != null) {
                this.mPhrases = new SoundTrigger.Keyphrase[phraseArr.length];
                int length = phraseArr.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    this.mPhrases[i2] = ConversionUtil.aidl2apiPhrase(phraseArr[i]);
                    i++;
                    i2++;
                }
            } else {
                this.mPhrases = null;
            }
            this.mInjectModelEvent = iInjectModelEvent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void wrap(final Consumer<ModelCallback> consumer) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                final ModelCallback modelCallback = this.mModelCallback;
                if (modelCallback != null) {
                    this.mModelExecutor.execute(new Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$ModelSession$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            consumer.accept(modelCallback);
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

        public void triggerRecognitionEvent(byte[] bArr, List<SoundTrigger.KeyphraseRecognitionExtra> list) {
            PhraseRecognitionExtra[] phraseRecognitionExtraArr;
            if (list != null) {
                phraseRecognitionExtraArr = new PhraseRecognitionExtra[list.size()];
                Iterator<SoundTrigger.KeyphraseRecognitionExtra> it = list.iterator();
                int i = 0;
                while (it.hasNext()) {
                    phraseRecognitionExtraArr[i] = ConversionUtil.api2aidlPhraseRecognitionExtra(it.next());
                    i++;
                }
            } else {
                phraseRecognitionExtraArr = null;
            }
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                SoundTriggerInstrumentation.this.mRecognitionSessionMap.remove(this.mInjectRecognitionEvent.asBinder());
                try {
                    this.mInjectRecognitionEvent.triggerRecognitionEvent(bArr, phraseRecognitionExtraArr);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        public void triggerAbortRecognition() {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                SoundTriggerInstrumentation.this.mRecognitionSessionMap.remove(this.mInjectRecognitionEvent.asBinder());
                try {
                    this.mInjectRecognitionEvent.triggerAbortRecognition();
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        public void setRecognitionCallback(Executor executor, final RecognitionCallback recognitionCallback) {
            Objects.requireNonNull(recognitionCallback);
            Objects.requireNonNull(executor);
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (this.mRecognitionCallback == null) {
                    for (final Consumer<RecognitionCallback> consumer : this.mDroppedConsumerList) {
                        executor.execute(new Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$RecognitionSession$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(recognitionCallback);
                            }
                        });
                    }
                    this.mDroppedConsumerList.clear();
                }
                this.mRecognitionCallback = recognitionCallback;
                this.mRecognitionExecutor = executor;
            }
        }

        public void clearRecognitionCallback() {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                this.mRecognitionCallback = null;
                this.mRecognitionExecutor = null;
            }
        }

        private RecognitionSession(int i, RecognitionConfig recognitionConfig, IInjectRecognitionEvent iInjectRecognitionEvent) {
            this.mRecognitionExecutor = null;
            this.mRecognitionCallback = null;
            this.mDroppedConsumerList = new ArrayList();
            this.mAudioSession = i;
            this.mRecognitionConfig = ConversionUtil.aidl2apiRecognitionConfig(recognitionConfig);
            this.mInjectRecognitionEvent = iInjectRecognitionEvent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void wrap(final Consumer<RecognitionCallback> consumer) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                final RecognitionCallback recognitionCallback = this.mRecognitionCallback;
                if (recognitionCallback != null) {
                    this.mRecognitionExecutor.execute(new Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$RecognitionSession$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            consumer.accept(recognitionCallback);
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
        private Injection() {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void registerGlobalEventInjection(IInjectGlobalEvent iInjectGlobalEvent) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                SoundTriggerInstrumentation.this.mInjectGlobalEvent = iInjectGlobalEvent;
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onSoundModelLoaded(SoundModel soundModel, Phrase[] phraseArr, IInjectModelEvent iInjectModelEvent, IInjectGlobalEvent iInjectGlobalEvent) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (iInjectGlobalEvent.asBinder() != SoundTriggerInstrumentation.this.mInjectGlobalEvent.asBinder()) {
                    return;
                }
                final ModelSession modelSession = new ModelSession(soundModel, phraseArr, iInjectModelEvent);
                SoundTriggerInstrumentation.this.mModelSessionMap.put(iInjectModelEvent.asBinder(), modelSession);
                SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(new Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSoundModelLoaded$0(modelSession);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSoundModelLoaded$0(ModelSession modelSession) {
            SoundTriggerInstrumentation.this.mClientCallback.onModelLoaded(modelSession);
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onSoundModelUnloaded(IInjectModelEvent iInjectModelEvent) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                ModelSession modelSession = (ModelSession) SoundTriggerInstrumentation.this.mModelSessionMap.remove(iInjectModelEvent.asBinder());
                if (modelSession == null) {
                    return;
                }
                modelSession.wrap(new Consumer() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((SoundTriggerInstrumentation.ModelCallback) obj).onModelUnloaded();
                    }
                });
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRecognitionStarted(int i, RecognitionConfig recognitionConfig, IInjectRecognitionEvent iInjectRecognitionEvent, IInjectModelEvent iInjectModelEvent) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                ModelSession modelSession = (ModelSession) SoundTriggerInstrumentation.this.mModelSessionMap.get(iInjectModelEvent.asBinder());
                if (modelSession == null) {
                    return;
                }
                final RecognitionSession recognitionSession = new RecognitionSession(i, recognitionConfig, iInjectRecognitionEvent);
                SoundTriggerInstrumentation.this.mRecognitionSessionMap.put(iInjectRecognitionEvent.asBinder(), recognitionSession);
                modelSession.wrap(new Consumer() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((SoundTriggerInstrumentation.ModelCallback) obj).onRecognitionStarted(recognitionSession);
                    }
                });
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRecognitionStopped(IInjectRecognitionEvent iInjectRecognitionEvent) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                RecognitionSession recognitionSession = (RecognitionSession) SoundTriggerInstrumentation.this.mRecognitionSessionMap.remove(iInjectRecognitionEvent.asBinder());
                if (recognitionSession == null) {
                    return;
                }
                recognitionSession.wrap(new Consumer() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((SoundTriggerInstrumentation.RecognitionCallback) obj).onRecognitionStopped();
                    }
                });
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onParamSet(final int i, final int i2, IInjectModelEvent iInjectModelEvent) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                ModelSession modelSession = (ModelSession) SoundTriggerInstrumentation.this.mModelSessionMap.get(iInjectModelEvent.asBinder());
                if (modelSession == null) {
                    return;
                }
                modelSession.wrap(new Consumer() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((SoundTriggerInstrumentation.ModelCallback) obj).onParamSet(i, i2);
                    }
                });
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRestarted(IInjectGlobalEvent iInjectGlobalEvent) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (iInjectGlobalEvent.asBinder() != SoundTriggerInstrumentation.this.mInjectGlobalEvent.asBinder()) {
                    return;
                }
                SoundTriggerInstrumentation.this.mRecognitionSessionMap.clear();
                SoundTriggerInstrumentation.this.mModelSessionMap.clear();
                SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(new Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onRestarted$5();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRestarted$5() {
            SoundTriggerInstrumentation.this.mClientCallback.onRestarted();
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onFrameworkDetached(IInjectGlobalEvent iInjectGlobalEvent) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (iInjectGlobalEvent.asBinder() != SoundTriggerInstrumentation.this.mInjectGlobalEvent.asBinder()) {
                    return;
                }
                SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(new Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onFrameworkDetached$6();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameworkDetached$6() {
            SoundTriggerInstrumentation.this.mClientCallback.onFrameworkDetached();
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onClientAttached(IBinder iBinder, IInjectGlobalEvent iInjectGlobalEvent) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (iInjectGlobalEvent.asBinder() != SoundTriggerInstrumentation.this.mInjectGlobalEvent.asBinder()) {
                    return;
                }
                SoundTriggerInstrumentation.this.mClientToken = iBinder;
                SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(new Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onClientAttached$7();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClientAttached$7() {
            SoundTriggerInstrumentation.this.mClientCallback.onClientAttached();
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onClientDetached(IBinder iBinder) {
            synchronized (SoundTriggerInstrumentation.this.mLock) {
                if (iBinder != SoundTriggerInstrumentation.this.mClientToken) {
                    return;
                }
                SoundTriggerInstrumentation.this.mClientToken = null;
                SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(new Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onClientDetached$8();
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
            SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(new Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onPreempted$9();
                }
            });
        }
    }

    public SoundTriggerInstrumentation(ISoundTriggerService iSoundTriggerService, Executor executor, GlobalCallback globalCallback) {
        this.mClientCallback = (GlobalCallback) Objects.requireNonNull(globalCallback);
        this.mGlobalCallbackExecutor = (Executor) Objects.requireNonNull(executor);
        this.mService = iSoundTriggerService;
        try {
            iSoundTriggerService.attachInjection(new Injection());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void triggerRestart() {
        synchronized (this.mLock) {
            IInjectGlobalEvent iInjectGlobalEvent = this.mInjectGlobalEvent;
            if (iInjectGlobalEvent == null) {
                throw new IllegalStateException("Attempted to trigger HAL restart before registration");
            }
            try {
                iInjectGlobalEvent.triggerRestart();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void triggerOnResourcesAvailable() {
        synchronized (this.mLock) {
            IInjectGlobalEvent iInjectGlobalEvent = this.mInjectGlobalEvent;
            if (iInjectGlobalEvent == null) {
                throw new IllegalStateException("Attempted to trigger HAL resources available before registration");
            }
            try {
                iInjectGlobalEvent.triggerOnResourcesAvailable();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setResourceContention(boolean z) {
        synchronized (this.mLock) {
            IInjectGlobalEvent iInjectGlobalEvent = this.mInjectGlobalEvent;
            if (iInjectGlobalEvent == null) {
                throw new IllegalStateException("Injection interface not set up");
            }
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            try {
                iInjectGlobalEvent.setResourceContention(z, new IAcknowledgeEvent.Stub(this) { // from class: android.media.soundtrigger.SoundTriggerInstrumentation.1
                    @Override // android.media.soundtrigger_middleware.IAcknowledgeEvent
                    public void eventReceived() {
                        countDownLatch.countDown();
                    }
                });
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
    }

    public void setInPhoneCallState(boolean z) {
        try {
            this.mService.setInPhoneCallState(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
