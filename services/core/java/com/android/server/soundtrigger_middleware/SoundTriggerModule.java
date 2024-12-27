package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.ISoundTriggerCallback;
import android.media.soundtrigger_middleware.ISoundTriggerModule;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SoundTriggerModule
        implements IBinder.DeathRecipient, ISoundTriggerHal.GlobalCallback {
    public final Set mActiveSessions = new HashSet();
    public final SoundTriggerMiddlewareImpl.AudioSessionProvider mAudioSessionProvider;
    public final HalFactory mHalFactory;
    public SoundTriggerHalEnforcer mHalService;
    public Properties mProperties;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class ModelState {
        public static final /* synthetic */ ModelState[] $VALUES;
        public static final ModelState ACTIVE;
        public static final ModelState INIT;
        public static final ModelState LOADED;

        static {
            ModelState modelState = new ModelState("INIT", 0);
            INIT = modelState;
            ModelState modelState2 = new ModelState("LOADED", 1);
            LOADED = modelState2;
            ModelState modelState3 = new ModelState("ACTIVE", 2);
            ACTIVE = modelState3;
            $VALUES = new ModelState[] {modelState, modelState2, modelState3};
        }

        public static ModelState valueOf(String str) {
            return (ModelState) Enum.valueOf(ModelState.class, str);
        }

        public static ModelState[] values() {
            return (ModelState[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Session implements ISoundTriggerModule {
        public ISoundTriggerCallback mCallback;
        public final Map mLoadedModels;
        public final IBinder mToken;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Model implements ISoundTriggerHal.ModelCallback {
            public int mHandle;
            public SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession mSession;
            public ModelState mState = ModelState.INIT;
            public IBinder mRecognitionToken = null;
            public boolean mIsStopping = false;

            /* renamed from: -$$Nest$mload, reason: not valid java name */
            public static int m900$$Nest$mload(
                    Model model,
                    PhraseSoundModel phraseSoundModel,
                    SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession audioSession) {
                model.mSession = audioSession;
                Session session = Session.this;
                model.mHandle =
                        SoundTriggerModule.this.mHalService.loadPhraseSoundModel(
                                phraseSoundModel, model);
                model.setState(ModelState.LOADED);
                ((HashMap) session.mLoadedModels).put(Integer.valueOf(model.mHandle), model);
                return model.mHandle;
            }

            /* renamed from: -$$Nest$mload, reason: not valid java name */
            public static int m901$$Nest$mload(
                    Model model,
                    SoundModel soundModel,
                    SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession audioSession) {
                model.mSession = audioSession;
                Session session = Session.this;
                model.mHandle =
                        SoundTriggerModule.this.mHalService.loadSoundModel(soundModel, model);
                model.setState(ModelState.LOADED);
                ((HashMap) session.mLoadedModels).put(Integer.valueOf(model.mHandle), model);
                return model.mHandle;
            }

            /* renamed from: -$$Nest$mstartRecognition, reason: not valid java name */
            public static IBinder m902$$Nest$mstartRecognition(
                    Model model, RecognitionConfig recognitionConfig) {
                if (model.mIsStopping) {
                    throw new RecoverableException(5, "Race occurred");
                }
                SoundTriggerHalEnforcer soundTriggerHalEnforcer =
                        SoundTriggerModule.this.mHalService;
                int i = model.mHandle;
                SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession audioSession =
                        model.mSession;
                soundTriggerHalEnforcer.startRecognition(
                        i, audioSession.mDeviceHandle, audioSession.mIoHandle, recognitionConfig);
                model.mRecognitionToken = new Binder();
                model.setState(ModelState.ACTIVE);
                return model.mRecognitionToken;
            }

            public Model() {}

            @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
            public final void modelUnloaded(int i) {
                ISoundTriggerCallback iSoundTriggerCallback;
                synchronized (SoundTriggerModule.this) {
                    iSoundTriggerCallback = Session.this.mCallback;
                }
                if (iSoundTriggerCallback != null) {
                    try {
                        iSoundTriggerCallback.onModelUnloaded(i);
                    } catch (RemoteException e) {
                        throw e.rethrowAsRuntimeException();
                    }
                }
            }

            @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
            public final void phraseRecognitionCallback(
                    int i, PhraseRecognitionEventSys phraseRecognitionEventSys) {
                synchronized (SoundTriggerModule.this) {
                    try {
                        IBinder iBinder = this.mRecognitionToken;
                        if (iBinder == null) {
                            return;
                        }
                        phraseRecognitionEventSys.token = iBinder;
                        if (!phraseRecognitionEventSys
                                .phraseRecognitionEvent
                                .common
                                .recognitionStillActive) {
                            setState(ModelState.LOADED);
                            this.mRecognitionToken = null;
                        }
                        ISoundTriggerCallback iSoundTriggerCallback = Session.this.mCallback;
                        if (iSoundTriggerCallback != null) {
                            try {
                                iSoundTriggerCallback.onPhraseRecognition(
                                        this.mHandle,
                                        phraseRecognitionEventSys,
                                        this.mSession.mSessionHandle);
                            } catch (RemoteException e) {
                                throw e.rethrowAsRuntimeException();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
            public final void recognitionCallback(int i, RecognitionEventSys recognitionEventSys) {
                synchronized (SoundTriggerModule.this) {
                    try {
                        IBinder iBinder = this.mRecognitionToken;
                        if (iBinder == null) {
                            return;
                        }
                        recognitionEventSys.token = iBinder;
                        if (!recognitionEventSys.recognitionEvent.recognitionStillActive) {
                            setState(ModelState.LOADED);
                            this.mRecognitionToken = null;
                        }
                        ISoundTriggerCallback iSoundTriggerCallback = Session.this.mCallback;
                        if (iSoundTriggerCallback != null) {
                            try {
                                iSoundTriggerCallback.onRecognition(
                                        this.mHandle,
                                        recognitionEventSys,
                                        this.mSession.mSessionHandle);
                            } catch (RemoteException e) {
                                throw e.rethrowAsRuntimeException();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void setState(ModelState modelState) {
                this.mState = modelState;
                SoundTriggerModule.this.notifyAll();
            }
        }

        public Session(ISoundTriggerCallback iSoundTriggerCallback) {
            Binder binder = new Binder();
            this.mToken = binder;
            this.mLoadedModels = new HashMap();
            this.mCallback = iSoundTriggerCallback;
            SoundTriggerModule.this.mHalService.clientAttached(binder);
        }

        public final IBinder asBinder() {
            throw new UnsupportedOperationException(
                    "This implementation is not intended to be used directly with Binder.");
        }

        public final void checkValid() {
            if (this.mCallback == null) {
                throw new RecoverableException(4);
            }
        }

        public final void detach() {
            synchronized (SoundTriggerModule.this) {
                try {
                    if (this.mCallback == null) {
                        return;
                    }
                    ((HashSet) SoundTriggerModule.this.mActiveSessions).remove(this);
                    this.mCallback = null;
                    SoundTriggerModule.this.mHalService.clientDetached(this.mToken);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void forceRecognitionEvent(int i) {
            synchronized (SoundTriggerModule.this) {
                checkValid();
                Model model = (Model) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
                if (model.mState == ModelState.ACTIVE) {
                    SoundTriggerModule.this.mHalService.forceRecognitionEvent(model.mHandle);
                }
            }
        }

        public final int getModelParameter(int i, int i2) {
            int modelParameter;
            synchronized (SoundTriggerModule.this) {
                checkValid();
                Model model = (Model) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
                modelParameter =
                        SoundTriggerModule.this.mHalService.getModelParameter(
                                model.mHandle, i2 != 0 ? -1 : 0);
            }
            return modelParameter;
        }

        public final int loadModel(SoundModel soundModel) {
            int m901$$Nest$mload;
            synchronized (SoundTriggerModule.this) {
                SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession acquireSession =
                        SoundTriggerModule.this.mAudioSessionProvider.acquireSession();
                try {
                    checkValid();
                    m901$$Nest$mload =
                            Model.m901$$Nest$mload(new Model(), soundModel, acquireSession);
                } catch (Exception e) {
                    try {
                        SoundTriggerModule.this.mAudioSessionProvider.releaseSession(
                                acquireSession.mSessionHandle);
                    } catch (Exception e2) {
                        Slog.e("SoundTriggerModule", "Failed to release session.", e2);
                    }
                    throw e;
                }
            }
            return m901$$Nest$mload;
        }

        public final int loadPhraseModel(PhraseSoundModel phraseSoundModel) {
            int m900$$Nest$mload;
            synchronized (SoundTriggerModule.this) {
                SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession acquireSession =
                        SoundTriggerModule.this.mAudioSessionProvider.acquireSession();
                try {
                    checkValid();
                    m900$$Nest$mload =
                            Model.m900$$Nest$mload(new Model(), phraseSoundModel, acquireSession);
                    Slog.d(
                            "SoundTriggerModule",
                            String.format(
                                    "loadPhraseModel()->%d", Integer.valueOf(m900$$Nest$mload)));
                } catch (Exception e) {
                    try {
                        SoundTriggerModule.this.mAudioSessionProvider.releaseSession(
                                acquireSession.mSessionHandle);
                    } catch (Exception e2) {
                        Slog.e("SoundTriggerModule", "Failed to release session.", e2);
                    }
                    throw e;
                }
            }
            return m900$$Nest$mload;
        }

        public final ModelParameterRange queryModelParameterSupport(int i, int i2) {
            ModelParameterRange queryParameter;
            synchronized (SoundTriggerModule.this) {
                checkValid();
                Model model = (Model) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
                queryParameter =
                        SoundTriggerModule.this.mHalService.queryParameter(model.mHandle, i2);
            }
            return queryParameter;
        }

        public final void setModelParameter(int i, int i2, int i3) {
            synchronized (SoundTriggerModule.this) {
                checkValid();
                Model model = (Model) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
                SoundTriggerModule.this.mHalService.setModelParameter(
                        model.mHandle, i2 != 0 ? -1 : 0, i3);
            }
        }

        public final IBinder startRecognition(int i, RecognitionConfig recognitionConfig) {
            IBinder m902$$Nest$mstartRecognition;
            synchronized (SoundTriggerModule.this) {
                checkValid();
                m902$$Nest$mstartRecognition =
                        Model.m902$$Nest$mstartRecognition(
                                (Model) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i)),
                                recognitionConfig);
            }
            return m902$$Nest$mstartRecognition;
        }

        public final void stopRecognition(int i) {
            Model model;
            synchronized (SoundTriggerModule.this) {
                checkValid();
                model = (Model) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
            }
            synchronized (SoundTriggerModule.this) {
                try {
                    ModelState modelState = model.mState;
                    ModelState modelState2 = ModelState.LOADED;
                    if (modelState == modelState2) {
                        return;
                    }
                    model.mRecognitionToken = null;
                    model.mIsStopping = true;
                    SoundTriggerModule.this.mHalService.stopRecognition(model.mHandle);
                    synchronized (SoundTriggerModule.this) {
                        model.mIsStopping = false;
                        model.setState(modelState2);
                    }
                } finally {
                }
            }
        }

        public final void unloadModel(int i) {
            synchronized (SoundTriggerModule.this) {
                checkValid();
                Model model = (Model) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
                Session session = Session.this;
                SoundTriggerModule.this.mHalService.unloadSoundModel(model.mHandle);
                ((HashMap) session.mLoadedModels).remove(Integer.valueOf(model.mHandle));
                SoundTriggerModule.this.mAudioSessionProvider.releaseSession(
                        model.mSession.mSessionHandle);
            }
        }
    }

    public SoundTriggerModule(
            HalFactory halFactory,
            SoundTriggerMiddlewareImpl.AudioSessionProvider audioSessionProvider) {
        Objects.requireNonNull(halFactory);
        this.mHalFactory = halFactory;
        this.mAudioSessionProvider = audioSessionProvider;
        attachToHal();
    }

    public final void attachToHal() {
        this.mHalService = null;
        while (true) {
            SoundTriggerHalEnforcer soundTriggerHalEnforcer = this.mHalService;
            if (soundTriggerHalEnforcer != null) {
                soundTriggerHalEnforcer.linkToDeath(this);
                this.mHalService.registerCallback(this);
                this.mProperties = this.mHalService.getProperties();
                return;
            } else {
                try {
                    this.mHalService =
                            new SoundTriggerHalEnforcer(
                                    new SoundTriggerHalWatchdog(
                                            new SoundTriggerDuplicateModelHandler(
                                                    this.mHalFactory.create())));
                } catch (RuntimeException e) {
                    if (!(e.getCause() instanceof RemoteException)) {
                        throw e;
                    }
                }
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        ArrayList arrayList;
        Slog.w("SoundTriggerModule", "Underlying HAL driver died.");
        synchronized (this) {
            try {
                arrayList = new ArrayList(((HashSet) this.mActiveSessions).size());
                Iterator it = ((HashSet) this.mActiveSessions).iterator();
                while (it.hasNext()) {
                    Session session = (Session) it.next();
                    ISoundTriggerCallback iSoundTriggerCallback = session.mCallback;
                    session.mCallback = null;
                    arrayList.add(iSoundTriggerCallback);
                }
                ((HashSet) this.mActiveSessions).clear();
                this.mHalService.detach();
                attachToHal();
            } catch (Throwable th) {
                throw th;
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            try {
                ((ISoundTriggerCallback) it2.next()).onModuleDied();
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback
    public final void onResourcesAvailable() {
        ArrayList arrayList;
        synchronized (this) {
            try {
                arrayList = new ArrayList(((HashSet) this.mActiveSessions).size());
                Iterator it = ((HashSet) this.mActiveSessions).iterator();
                while (it.hasNext()) {
                    arrayList.add(((Session) it.next()).mCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            try {
                ((ISoundTriggerCallback) it2.next()).onResourcesAvailable();
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }
}
