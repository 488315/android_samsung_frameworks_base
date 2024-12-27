package com.android.server.soundtrigger_middleware;

import android.hardware.soundtrigger3.ISoundTriggerHw;
import android.hardware.soundtrigger3.ISoundTriggerHwCallback;
import android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback;
import android.media.soundtrigger.ModelParameterRange;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class FakeSoundTriggerHal extends ISoundTriggerHw.Stub {
    public IBinder.DeathRecipient mDeathRecipient;
    public CallbackDispatcher mGlobalCallbackDispatcher;
    public final AnonymousClass1 mGlobalEventSession;
    public final CallbackDispatcher mInjectionDispatcher;
    public final Properties mProperties;
    public final Object mLock = new Object();
    public boolean mIsResourceContended = false;
    public final Map mModelSessionMap = new HashMap();
    public int mModelKeyCounter = 101;
    public boolean mIsDead = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1, reason: invalid class name */
    public final class AnonymousClass1 extends IInjectGlobalEvent.Stub {
        public AnonymousClass1() {}

        public final void setResourceContention(boolean z, IAcknowledgeEvent iAcknowledgeEvent) {
            synchronized (FakeSoundTriggerHal.this.mLock) {
                try {
                    FakeSoundTriggerHal fakeSoundTriggerHal = FakeSoundTriggerHal.this;
                    if (fakeSoundTriggerHal.mIsDead) {
                        return;
                    }
                    boolean z2 = fakeSoundTriggerHal.mIsResourceContended;
                    fakeSoundTriggerHal.mIsResourceContended = z;
                    CallbackDispatcher.m896$$Nest$mwrap$2(
                            fakeSoundTriggerHal.mInjectionDispatcher,
                            new FakeSoundTriggerHal$$ExternalSyntheticLambda0(iAcknowledgeEvent));
                    FakeSoundTriggerHal fakeSoundTriggerHal2 = FakeSoundTriggerHal.this;
                    if (!fakeSoundTriggerHal2.mIsResourceContended && z2) {
                        CallbackDispatcher.m895$$Nest$mwrap$1(
                                fakeSoundTriggerHal2.mGlobalCallbackDispatcher,
                                new FakeSoundTriggerHal$$ExternalSyntheticLambda2(1));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void triggerOnResourcesAvailable() {
            synchronized (FakeSoundTriggerHal.this.mLock) {
                try {
                    FakeSoundTriggerHal fakeSoundTriggerHal = FakeSoundTriggerHal.this;
                    if (fakeSoundTriggerHal.mIsDead) {
                        return;
                    }
                    CallbackDispatcher.m895$$Nest$mwrap$1(
                            fakeSoundTriggerHal.mGlobalCallbackDispatcher,
                            new FakeSoundTriggerHal$$ExternalSyntheticLambda2(2));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void triggerRestart() {
            synchronized (FakeSoundTriggerHal.this.mLock) {
                try {
                    FakeSoundTriggerHal fakeSoundTriggerHal = FakeSoundTriggerHal.this;
                    if (fakeSoundTriggerHal.mIsDead) {
                        return;
                    }
                    fakeSoundTriggerHal.mIsDead = true;
                    CallbackDispatcher.m896$$Nest$mwrap$2(
                            fakeSoundTriggerHal.mInjectionDispatcher,
                            new FakeSoundTriggerHal$$ExternalSyntheticLambda0(this));
                    ((HashMap) FakeSoundTriggerHal.this.mModelSessionMap).clear();
                    IBinder.DeathRecipient deathRecipient =
                            FakeSoundTriggerHal.this.mDeathRecipient;
                    if (deathRecipient != null) {
                        ExecutorHolder.CALLBACK_EXECUTOR.execute(
                                new FakeSoundTriggerHal$1$$ExternalSyntheticLambda3(
                                        0, this, deathRecipient));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallbackDispatcher {
        public final Object mCallback;

        /* renamed from: -$$Nest$mwrap, reason: not valid java name */
        public static void m894$$Nest$mwrap(
                CallbackDispatcher callbackDispatcher,
                FunctionalUtils.ThrowingConsumer throwingConsumer) {
            callbackDispatcher.getClass();
            ExecutorHolder.CALLBACK_EXECUTOR.execute(
                    new FakeSoundTriggerHal$1$$ExternalSyntheticLambda3(
                            1, callbackDispatcher, throwingConsumer));
        }

        /* renamed from: -$$Nest$mwrap$1, reason: not valid java name */
        public static void m895$$Nest$mwrap$1(
                CallbackDispatcher callbackDispatcher,
                FunctionalUtils.ThrowingConsumer throwingConsumer) {
            callbackDispatcher.getClass();
            ExecutorHolder.CALLBACK_EXECUTOR.execute(
                    new FakeSoundTriggerHal$1$$ExternalSyntheticLambda3(
                            2, callbackDispatcher, throwingConsumer));
        }

        /* renamed from: -$$Nest$mwrap$2, reason: not valid java name */
        public static void m896$$Nest$mwrap$2(
                CallbackDispatcher callbackDispatcher,
                FunctionalUtils.ThrowingConsumer throwingConsumer) {
            callbackDispatcher.getClass();
            ExecutorHolder.INJECTION_EXECUTOR.execute(
                    new FakeSoundTriggerHal$1$$ExternalSyntheticLambda3(
                            3, callbackDispatcher, throwingConsumer));
        }

        public CallbackDispatcher(ISoundTriggerHwCallback iSoundTriggerHwCallback) {
            this.mCallback = iSoundTriggerHwCallback;
        }

        public CallbackDispatcher(ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) {
            this.mCallback = iSoundTriggerHwGlobalCallback;
        }

        public CallbackDispatcher(SoundTriggerInjection soundTriggerInjection) {
            this.mCallback = soundTriggerInjection;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class ExecutorHolder {
        public static final Executor CALLBACK_EXECUTOR = Executors.newSingleThreadExecutor();
        public static final Executor INJECTION_EXECUTOR = Executors.newSingleThreadExecutor();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModelSession extends IInjectModelEvent.Stub {
        public final CallbackDispatcher mCallbackDispatcher;
        public final boolean mIsKeyphrase;
        public final int mModelHandle;
        public RecognitionSession mRecognitionSession;
        public int mThreshold = 0;
        public boolean mIsUnloaded = false;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class RecognitionSession extends IInjectRecognitionEvent.Stub {
            public RecognitionSession() {}

            public final void triggerAbortRecognition() {
                synchronized (FakeSoundTriggerHal.this.mLock) {
                    try {
                        ModelSession modelSession = ModelSession.this;
                        if (!FakeSoundTriggerHal.this.mIsDead
                                && modelSession.mRecognitionSession == this) {
                            modelSession.mRecognitionSession = null;
                            if (modelSession.mIsKeyphrase) {
                                CallbackDispatcher.m894$$Nest$mwrap(
                                        modelSession.mCallbackDispatcher,
                                        new FakeSoundTriggerHal$$ExternalSyntheticLambda7(this, 1));
                            } else {
                                CallbackDispatcher.m894$$Nest$mwrap(
                                        modelSession.mCallbackDispatcher,
                                        new FakeSoundTriggerHal$$ExternalSyntheticLambda7(this, 2));
                            }
                        }
                    } finally {
                    }
                }
            }

            public final void triggerRecognitionEvent(
                    byte[] bArr, PhraseRecognitionExtra[] phraseRecognitionExtraArr) {
                synchronized (FakeSoundTriggerHal.this.mLock) {
                    try {
                        ModelSession modelSession = ModelSession.this;
                        if (!FakeSoundTriggerHal.this.mIsDead
                                && modelSession.mRecognitionSession == this) {
                            modelSession.mRecognitionSession = null;
                            if (modelSession.mIsKeyphrase) {
                                PhraseRecognitionEvent m893$$Nest$smcreateDefaultKeyphraseEvent =
                                        FakeSoundTriggerHal
                                                .m893$$Nest$smcreateDefaultKeyphraseEvent(0);
                                m893$$Nest$smcreateDefaultKeyphraseEvent.common.data = bArr;
                                if (phraseRecognitionExtraArr != null) {
                                    m893$$Nest$smcreateDefaultKeyphraseEvent.phraseExtras =
                                            phraseRecognitionExtraArr;
                                }
                                CallbackDispatcher.m894$$Nest$mwrap(
                                        ModelSession.this.mCallbackDispatcher,
                                        new FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda2(
                                                this, m893$$Nest$smcreateDefaultKeyphraseEvent));
                            } else {
                                RecognitionEvent createDefaultEvent =
                                        FakeSoundTriggerHal.createDefaultEvent(0);
                                createDefaultEvent.data = bArr;
                                CallbackDispatcher.m894$$Nest$mwrap(
                                        ModelSession.this.mCallbackDispatcher,
                                        new FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda2(
                                                this, createDefaultEvent));
                            }
                        }
                    } finally {
                    }
                }
            }
        }

        /* renamed from: -$$Nest$mforceRecognitionForModel, reason: not valid java name */
        public static void m897$$Nest$mforceRecognitionForModel(ModelSession modelSession) {
            synchronized (FakeSoundTriggerHal.this.mLock) {
                try {
                    if (modelSession.mIsKeyphrase) {
                        CallbackDispatcher.m894$$Nest$mwrap(
                                modelSession.mCallbackDispatcher,
                                new FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda2(
                                        modelSession,
                                        FakeSoundTriggerHal
                                                .m893$$Nest$smcreateDefaultKeyphraseEvent(3)));
                    } else {
                        CallbackDispatcher.m894$$Nest$mwrap(
                                modelSession.mCallbackDispatcher,
                                new FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda2(
                                        modelSession, FakeSoundTriggerHal.createDefaultEvent(3)));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* renamed from: -$$Nest$mgetIsUnloaded, reason: not valid java name */
        public static boolean m898$$Nest$mgetIsUnloaded(ModelSession modelSession) {
            boolean z;
            synchronized (FakeSoundTriggerHal.this.mLock) {
                z = modelSession.mIsUnloaded;
            }
            return z;
        }

        public ModelSession(int i, CallbackDispatcher callbackDispatcher, boolean z) {
            this.mModelHandle = i;
            this.mCallbackDispatcher = callbackDispatcher;
            this.mIsKeyphrase = z;
        }

        public final void triggerUnloadModel() {
            synchronized (FakeSoundTriggerHal.this.mLock) {
                try {
                    if (!FakeSoundTriggerHal.this.mIsDead && !this.mIsUnloaded) {
                        RecognitionSession recognitionSession = this.mRecognitionSession;
                        if (recognitionSession != null) {
                            recognitionSession.triggerAbortRecognition();
                        }
                        this.mIsUnloaded = true;
                        CallbackDispatcher.m894$$Nest$mwrap(
                                this.mCallbackDispatcher,
                                new FakeSoundTriggerHal$$ExternalSyntheticLambda1(this, 1));
                        int numLoadedModelsLocked =
                                FakeSoundTriggerHal.this.getNumLoadedModelsLocked();
                        FakeSoundTriggerHal fakeSoundTriggerHal = FakeSoundTriggerHal.this;
                        if (numLoadedModelsLocked
                                        == fakeSoundTriggerHal.mProperties.maxSoundModels - 1
                                && !fakeSoundTriggerHal.mIsResourceContended) {
                            CallbackDispatcher.m895$$Nest$mwrap$1(
                                    fakeSoundTriggerHal.mGlobalCallbackDispatcher,
                                    new FakeSoundTriggerHal$$ExternalSyntheticLambda2(3));
                        }
                    }
                } finally {
                }
            }
        }
    }

    /* renamed from: -$$Nest$smcreateDefaultKeyphraseEvent, reason: not valid java name */
    public static PhraseRecognitionEvent m893$$Nest$smcreateDefaultKeyphraseEvent(int i) {
        RecognitionEvent createDefaultEvent = createDefaultEvent(i);
        createDefaultEvent.type = 0;
        PhraseRecognitionEvent phraseRecognitionEvent = new PhraseRecognitionEvent();
        phraseRecognitionEvent.common = createDefaultEvent;
        phraseRecognitionEvent.phraseExtras = new PhraseRecognitionExtra[0];
        return phraseRecognitionEvent;
    }

    public FakeSoundTriggerHal(SoundTriggerInjection soundTriggerInjection) {
        this.mGlobalCallbackDispatcher = null;
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
        this.mProperties = properties;
        CallbackDispatcher callbackDispatcher = new CallbackDispatcher(soundTriggerInjection);
        this.mInjectionDispatcher = callbackDispatcher;
        this.mGlobalCallbackDispatcher = null;
        this.mGlobalEventSession = new AnonymousClass1();
        CallbackDispatcher.m896$$Nest$mwrap$2(
                callbackDispatcher, new FakeSoundTriggerHal$$ExternalSyntheticLambda0(this));
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

    public final void forceRecognitionEvent(int i) {
        ModelSession.RecognitionSession recognitionSession;
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new DeadObjectException();
                }
                ModelSession modelSession =
                        (ModelSession) ((HashMap) this.mModelSessionMap).get(Integer.valueOf(i));
                if (modelSession == null) {
                    Slog.wtf(
                            "FakeSoundTriggerHal",
                            "Attempted to force recognition with invalid handle");
                }
                synchronized (FakeSoundTriggerHal.this.mLock) {
                    recognitionSession = modelSession.mRecognitionSession;
                }
                if (recognitionSession == null) {
                    return;
                }
                ModelSession.m897$$Nest$mforceRecognitionForModel(modelSession);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String getInterfaceHash() {
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new DeadObjectException();
            }
        }
        return "6b24e60ad261e3ff56106efd86ce6aa7ef5621b0";
    }

    public final int getInterfaceVersion() {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new DeadObjectException();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return 2;
    }

    public final int getNumLoadedModelsLocked() {
        Iterator it = ((HashMap) this.mModelSessionMap).values().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (!ModelSession.m898$$Nest$mgetIsUnloaded((ModelSession) it.next())) {
                i++;
            }
        }
        return i;
    }

    public final int getParameter(int i, int i2) {
        int i3;
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new DeadObjectException();
                }
                ModelSession modelSession =
                        (ModelSession) ((HashMap) this.mModelSessionMap).get(Integer.valueOf(i));
                if (modelSession == null) {
                    Slog.wtf("FakeSoundTriggerHal", "Attempted to get param with invalid handle");
                }
                if (i2 != 0) {
                    throw new IllegalArgumentException();
                }
                synchronized (FakeSoundTriggerHal.this.mLock) {
                    i3 = modelSession.mThreshold;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i3;
    }

    public final Properties getProperties() {
        Properties properties;
        synchronized (this.mLock) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
        return properties;
    }

    public final void linkToDeath(IBinder.DeathRecipient deathRecipient, int i) {
        synchronized (this.mLock) {
            try {
                if (this.mDeathRecipient != null) {
                    Slog.wtf("FakeSoundTriggerHal", "Received two death recipients concurrently");
                }
                this.mDeathRecipient = deathRecipient;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int loadPhraseSoundModel(
            PhraseSoundModel phraseSoundModel, ISoundTriggerHwCallback iSoundTriggerHwCallback) {
        int i;
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new DeadObjectException();
                }
                if (this.mIsResourceContended
                        || getNumLoadedModelsLocked() == this.mProperties.maxSoundModels) {
                    throw new ServiceSpecificException(1);
                }
                i = this.mModelKeyCounter;
                this.mModelKeyCounter = i + 1;
                ModelSession modelSession =
                        new ModelSession(i, new CallbackDispatcher(iSoundTriggerHwCallback), true);
                ((HashMap) this.mModelSessionMap).put(Integer.valueOf(i), modelSession);
                CallbackDispatcher.m896$$Nest$mwrap$2(
                        this.mInjectionDispatcher,
                        new FakeSoundTriggerHal$$ExternalSyntheticLambda4(
                                this, phraseSoundModel, modelSession));
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int loadSoundModel(
            SoundModel soundModel, ISoundTriggerHwCallback iSoundTriggerHwCallback) {
        int i;
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new DeadObjectException();
                }
                if (this.mIsResourceContended
                        || getNumLoadedModelsLocked() == this.mProperties.maxSoundModels) {
                    throw new ServiceSpecificException(1);
                }
                i = this.mModelKeyCounter;
                this.mModelKeyCounter = i + 1;
                ModelSession modelSession =
                        new ModelSession(i, new CallbackDispatcher(iSoundTriggerHwCallback), false);
                ((HashMap) this.mModelSessionMap).put(Integer.valueOf(i), modelSession);
                CallbackDispatcher.m896$$Nest$mwrap$2(
                        this.mInjectionDispatcher,
                        new FakeSoundTriggerHal$$ExternalSyntheticLambda4(
                                this, soundModel, modelSession, 0));
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final ModelParameterRange queryParameter(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new DeadObjectException();
                }
                if (((ModelSession) ((HashMap) this.mModelSessionMap).get(Integer.valueOf(i)))
                        == null) {
                    Slog.wtf("FakeSoundTriggerHal", "Attempted to get param with invalid handle");
                }
            } catch (Throwable th) {
                throw th;
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

    public final void registerGlobalCallback(
            ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new DeadObjectException();
                }
                this.mGlobalCallbackDispatcher =
                        new CallbackDispatcher(iSoundTriggerHwGlobalCallback);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setParameter(int i, final int i2, final int i3) {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new DeadObjectException();
                }
                final ModelSession modelSession =
                        (ModelSession) ((HashMap) this.mModelSessionMap).get(Integer.valueOf(i));
                if (modelSession == null) {
                    Slog.wtf("FakeSoundTriggerHal", "Attempted to get param with invalid handle");
                }
                if (i2 != 0 && (i3 < -10 || i3 > 10)) {
                    throw new IllegalArgumentException();
                }
                synchronized (FakeSoundTriggerHal.this.mLock) {
                    modelSession.mThreshold = i3;
                }
                CallbackDispatcher.m896$$Nest$mwrap$2(
                        this.mInjectionDispatcher,
                        new FunctionalUtils
                                .ThrowingConsumer() { // from class:
                                                      // com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda3
                            public final void acceptOrThrow(Object obj) {
                                ((ISoundTriggerInjection) obj).onParamSet(i2, i3, modelSession);
                            }
                        });
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) {
        ModelSession.RecognitionSession recognitionSession;
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new DeadObjectException();
                }
                ModelSession modelSession =
                        (ModelSession) ((HashMap) this.mModelSessionMap).get(Integer.valueOf(i));
                if (modelSession == null) {
                    Slog.wtf(
                            "FakeSoundTriggerHal",
                            "Attempted to start recognition with invalid handle");
                }
                if (this.mIsResourceContended) {
                    throw new ServiceSpecificException(1);
                }
                if (ModelSession.m898$$Nest$mgetIsUnloaded(modelSession)) {
                    throw new ServiceSpecificException(1);
                }
                synchronized (FakeSoundTriggerHal.this.mLock) {
                    recognitionSession = modelSession.new RecognitionSession();
                    modelSession.mRecognitionSession = recognitionSession;
                }
                CallbackDispatcher.m896$$Nest$mwrap$2(
                        this.mInjectionDispatcher,
                        new FakeSoundTriggerHal$$ExternalSyntheticLambda4(
                                recognitionConfig, recognitionSession, modelSession, 2));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopRecognition(int i) {
        ModelSession.RecognitionSession recognitionSession;
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new DeadObjectException();
                }
                ModelSession modelSession =
                        (ModelSession) ((HashMap) this.mModelSessionMap).get(Integer.valueOf(i));
                if (modelSession == null) {
                    Slog.wtf(
                            "FakeSoundTriggerHal",
                            "Attempted to stop recognition with invalid handle");
                }
                synchronized (FakeSoundTriggerHal.this.mLock) {
                    recognitionSession = modelSession.mRecognitionSession;
                    modelSession.mRecognitionSession = null;
                }
                if (recognitionSession != null) {
                    CallbackDispatcher.m896$$Nest$mwrap$2(
                            this.mInjectionDispatcher,
                            new FakeSoundTriggerHal$$ExternalSyntheticLambda7(
                                    recognitionSession, 0));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient, int i) {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    return false;
                }
                if (this.mDeathRecipient != deathRecipient) {
                    throw new NoSuchElementException();
                }
                this.mDeathRecipient = null;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unloadSoundModel(int i) {
        ModelSession.RecognitionSession recognitionSession;
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new DeadObjectException();
                }
                ModelSession modelSession =
                        (ModelSession) ((HashMap) this.mModelSessionMap).get(Integer.valueOf(i));
                if (modelSession == null) {
                    Slog.wtf(
                            "FakeSoundTriggerHal",
                            "Attempted to unload model which was never loaded");
                }
                synchronized (FakeSoundTriggerHal.this.mLock) {
                    recognitionSession = modelSession.mRecognitionSession;
                }
                if (recognitionSession != null) {
                    Slog.wtf("FakeSoundTriggerHal", "Session unloaded before recog stopped!");
                }
                if (ModelSession.m898$$Nest$mgetIsUnloaded(modelSession)) {
                    return;
                }
                CallbackDispatcher.m896$$Nest$mwrap$2(
                        this.mInjectionDispatcher,
                        new FakeSoundTriggerHal$$ExternalSyntheticLambda1(modelSession, 0));
                if (getNumLoadedModelsLocked() == this.mProperties.maxSoundModels - 1
                        && !this.mIsResourceContended) {
                    CallbackDispatcher.m895$$Nest$mwrap$1(
                            this.mGlobalCallbackDispatcher,
                            new FakeSoundTriggerHal$$ExternalSyntheticLambda2(0));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
