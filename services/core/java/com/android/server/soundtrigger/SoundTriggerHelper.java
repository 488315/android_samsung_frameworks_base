package com.android.server.soundtrigger;

import android.content.Context;
import android.hardware.soundtrigger.IRecognitionStatusCallback;
import android.hardware.soundtrigger.SoundTrigger;
import android.hardware.soundtrigger.SoundTriggerModule;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.logging.MetricsLogger;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SoundTriggerHelper implements SoundTrigger.StatusListener {
    public final Context mContext;
    public final EventLogger mEventLogger;
    public SoundTriggerModule mModule;
    public final int mModuleId;
    public final Supplier mModulePropertiesProvider;
    public final Function mModuleProvider;
    public final Object mLock = new Object();
    public final HashMap mModelDataMap = new HashMap();
    public final HashMap mKeyphraseUuidMap = new HashMap();
    public boolean mIsDetached = false;
    public DeviceStateHandler.SoundTriggerDeviceState mDeviceState =
            DeviceStateHandler.SoundTriggerDeviceState.DISABLE;
    public boolean mIsAppOpPermitted = true;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModelData {
        public int mModelHandle;
        public final UUID mModelId;
        public int mModelState;
        public final int mModelType;
        public boolean mRequested = false;
        public IRecognitionStatusCallback mCallback = null;
        public SoundTrigger.RecognitionConfig mRecognitionConfig = null;
        public boolean mRunInBatterySaverMode = false;
        public SoundTrigger.SoundModel mSoundModel = null;
        public IBinder mRecognitionToken = null;

        public ModelData(UUID uuid, int i) {
            this.mModelId = uuid;
            this.mModelType = i;
        }

        public final synchronized void clearState() {
            this.mModelState = 0;
            this.mRecognitionToken = null;
            this.mRecognitionConfig = null;
            this.mRequested = false;
            this.mCallback = null;
        }

        public final synchronized IRecognitionStatusCallback getCallback() {
            return this.mCallback;
        }

        public final synchronized int getHandle() {
            return this.mModelHandle;
        }

        public final synchronized UUID getModelId() {
            return this.mModelId;
        }

        public final synchronized boolean isGenericModel() {
            return this.mModelType == 1;
        }

        public final synchronized boolean isKeyphraseModel() {
            return this.mModelType == 0;
        }

        public final synchronized boolean isModelLoaded() {
            boolean z;
            int i = this.mModelState;
            z = true;
            if (i != 1 && i != 2) {
                z = false;
            }
            return z;
        }

        public final synchronized boolean isModelStarted() {
            return this.mModelState == 2;
        }

        public final synchronized String modelTypeToString() {
            int i;
            try {
                i = this.mModelType;
            } catch (Throwable th) {
                throw th;
            }
            return "Model type: "
                    + (i != -1 ? i != 0 ? i != 1 ? null : "Generic" : "Keyphrase" : "Unknown")
                    + "\n";
        }

        public final synchronized void setRequested(boolean z) {
            this.mRequested = z;
        }

        public final synchronized void setStopped() {
            this.mRecognitionToken = null;
            this.mModelState = 1;
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x0055 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final synchronized java.lang.String toString() {
            /*
                r3 = this;
                java.lang.String r0 = "Handle: "
                monitor-enter(r3)
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La9
                r1.<init>(r0)     // Catch: java.lang.Throwable -> La9
                int r0 = r3.mModelHandle     // Catch: java.lang.Throwable -> La9
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                java.lang.String r0 = "\nModelState: "
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                monitor-enter(r3)     // Catch: java.lang.Throwable -> La9
                int r0 = r3.mModelState     // Catch: java.lang.Throwable -> L21
                if (r0 == 0) goto L2c
                r2 = 1
                if (r0 == r2) goto L28
                r2 = 2
                if (r0 == r2) goto L24
                java.lang.String r0 = "Unknown state"
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
                goto L2f
            L21:
                r0 = move-exception
                goto Lb2
            L24:
                java.lang.String r0 = "STARTED"
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
                goto L2f
            L28:
                java.lang.String r0 = "LOADED"
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
                goto L2f
            L2c:
                java.lang.String r0 = "NOT_LOADED"
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
            L2f:
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                java.lang.String r0 = "\n"
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                java.lang.String r0 = "Requested: "
                monitor-enter(r3)     // Catch: java.lang.Throwable -> La9
                boolean r2 = r3.mRequested     // Catch: java.lang.Throwable -> L41
                if (r2 == 0) goto L43
                java.lang.String r2 = "Yes"
                goto L45
            L41:
                r0 = move-exception
                goto Lb0
            L43:
                java.lang.String r2 = "No"
            L45:
                java.lang.String r0 = r0.concat(r2)     // Catch: java.lang.Throwable -> L41
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                java.lang.String r0 = "\n"
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                java.lang.String r0 = "Callback: "
                monitor-enter(r3)     // Catch: java.lang.Throwable -> La9
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L63
                r2.<init>(r0)     // Catch: java.lang.Throwable -> L63
                android.hardware.soundtrigger.IRecognitionStatusCallback r0 = r3.mCallback     // Catch: java.lang.Throwable -> L63
                if (r0 == 0) goto L65
                android.os.IBinder r0 = r0.asBinder()     // Catch: java.lang.Throwable -> L63
                goto L68
            L63:
                r0 = move-exception
                goto Lae
            L65:
                java.lang.String r0 = "null"
            L68:
                r2.append(r0)     // Catch: java.lang.Throwable -> L63
                java.lang.String r0 = r2.toString()     // Catch: java.lang.Throwable -> L63
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                java.lang.String r0 = "\n"
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                java.lang.String r0 = "UUID: "
                monitor-enter(r3)     // Catch: java.lang.Throwable -> La9
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lab
                r2.<init>(r0)     // Catch: java.lang.Throwable -> Lab
                java.util.UUID r0 = r3.mModelId     // Catch: java.lang.Throwable -> Lab
                r2.append(r0)     // Catch: java.lang.Throwable -> Lab
                java.lang.String r0 = r2.toString()     // Catch: java.lang.Throwable -> Lab
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                java.lang.String r0 = "\n"
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                java.lang.String r0 = r3.modelTypeToString()     // Catch: java.lang.Throwable -> La9
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                java.lang.String r0 = "RunInBatterySaverMode="
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                boolean r0 = r3.mRunInBatterySaverMode     // Catch: java.lang.Throwable -> La9
                r1.append(r0)     // Catch: java.lang.Throwable -> La9
                java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> La9
                monitor-exit(r3)
                return r0
            La9:
                r0 = move-exception
                goto Lb4
            Lab:
                r0 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
                throw r0     // Catch: java.lang.Throwable -> La9
            Lae:
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
                throw r0     // Catch: java.lang.Throwable -> La9
            Lb0:
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
                throw r0     // Catch: java.lang.Throwable -> La9
            Lb2:
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
                throw r0     // Catch: java.lang.Throwable -> La9
            Lb4:
                monitor-exit(r3)
                throw r0
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.soundtrigger.SoundTriggerHelper.ModelData.toString():java.lang.String");
        }
    }

    public SoundTriggerHelper(
            Context context,
            EventLogger eventLogger,
            SoundTriggerService$$ExternalSyntheticLambda1
                    soundTriggerService$$ExternalSyntheticLambda1,
            int i,
            SoundTriggerService$$ExternalSyntheticLambda2
                    soundTriggerService$$ExternalSyntheticLambda2) {
        this.mModuleId = i;
        this.mContext = context;
        this.mModuleProvider = soundTriggerService$$ExternalSyntheticLambda1;
        this.mEventLogger = eventLogger;
        this.mModulePropertiesProvider = soundTriggerService$$ExternalSyntheticLambda2;
        if (i == -1) {
            this.mModule = null;
        } else {
            this.mModule =
                    (SoundTriggerModule) soundTriggerService$$ExternalSyntheticLambda1.apply(this);
        }
    }

    public final void detach() {
        synchronized (this.mLock) {
            try {
                if (this.mIsDetached) {
                    return;
                }
                this.mIsDetached = true;
                Iterator it = this.mModelDataMap.values().iterator();
                while (it.hasNext()) {
                    forceStopAndUnloadModelLocked((ModelData) it.next(), null, null);
                }
                this.mModelDataMap.clear();
                SoundTriggerModule soundTriggerModule = this.mModule;
                if (soundTriggerModule != null) {
                    soundTriggerModule.detach();
                    this.mModule = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void forceStopAndUnloadModelLocked(
            ModelData modelData, Exception exc, Iterator it) {
        if (exc != null) {
            Slog.e("SoundTriggerHelper", "forceStopAndUnloadModel", exc);
        }
        if (this.mModule == null) {
            return;
        }
        if (modelData.isModelStarted()) {
            Slog.d(
                    "SoundTriggerHelper",
                    "Stopping previously started dangling model " + modelData.getHandle());
            if (this.mModule.stopRecognition(modelData.getHandle()) == 0) {
                modelData.setStopped();
                modelData.setRequested(false);
            } else {
                Slog.e("SoundTriggerHelper", "Failed to stop model " + modelData.getHandle());
            }
        }
        if (modelData.isModelLoaded()) {
            Slog.d(
                    "SoundTriggerHelper",
                    "Unloading previously loaded dangling model " + modelData.getHandle());
            if (this.mModule.unloadSoundModel(modelData.getHandle()) != 0) {
                Slog.e("SoundTriggerHelper", "Failed to unload model " + modelData.getHandle());
                return;
            }
            if (it != null) {
                it.remove();
            } else {
                this.mModelDataMap.remove(modelData.getModelId());
            }
            Iterator it2 = this.mKeyphraseUuidMap.entrySet().iterator();
            while (it2.hasNext()) {
                if (((Map.Entry) it2.next()).getValue().equals(modelData.getModelId())) {
                    it2.remove();
                }
            }
            modelData.clearState();
        }
    }

    public final int getGenericModelState(UUID uuid) {
        synchronized (this.mLock) {
            try {
                MetricsLogger.count(this.mContext, "sth_get_generic_model_state", 1);
                if (uuid != null && this.mModule != null) {
                    if (this.mIsDetached) {
                        throw new IllegalStateException("SoundTriggerHelper has been detached");
                    }
                    ModelData modelData = (ModelData) this.mModelDataMap.get(uuid);
                    if (modelData != null && modelData.isGenericModel()) {
                        if (!modelData.isModelLoaded()) {
                            Slog.i(
                                    "SoundTriggerHelper",
                                    "GetGenericModelState: Given generic model is not loaded:"
                                            + uuid);
                            return Integer.MIN_VALUE;
                        }
                        if (modelData.isModelStarted()) {
                            return this.mModule.getModelState(modelData.getHandle());
                        }
                        Slog.i(
                                "SoundTriggerHelper",
                                "GetGenericModelState: Given generic model is not started:" + uuid);
                        return Integer.MIN_VALUE;
                    }
                    Slog.w(
                            "SoundTriggerHelper",
                            "GetGenericModelState error: Invalid generic model id:" + uuid);
                    return Integer.MIN_VALUE;
                }
                return Integer.MIN_VALUE;
            } finally {
            }
        }
    }

    public final ModelData getKeyphraseModelDataLocked(int i) {
        UUID uuid = (UUID) this.mKeyphraseUuidMap.get(Integer.valueOf(i));
        if (uuid == null) {
            return null;
        }
        return (ModelData) this.mModelDataMap.get(uuid);
    }

    public final ModelData getModelDataForLocked(int i) {
        for (ModelData modelData : this.mModelDataMap.values()) {
            if (modelData.getHandle() == i) {
                return modelData;
            }
        }
        return null;
    }

    public final SoundTrigger.ModuleProperties getModuleProperties() {
        synchronized (this.mLock) {
            if (this.mIsDetached) {
                throw new IllegalStateException("SoundTriggerHelper has been detached");
            }
        }
        for (SoundTrigger.ModuleProperties moduleProperties :
                (List) this.mModulePropertiesProvider.get()) {
            if (moduleProperties.getId() == this.mModuleId) {
                return moduleProperties;
            }
        }
        VaultKeeperService$$ExternalSyntheticOutline0.m(
                new StringBuilder("Module properties not found for existing moduleId "),
                this.mModuleId,
                "SoundTriggerHelper");
        return null;
    }

    public final int getParameterLocked(ModelData modelData, int i) {
        MetricsLogger.count(this.mContext, "sth_get_parameter", 1);
        if (this.mModule == null) {
            throw new UnsupportedOperationException("SoundTriggerModule not initialized");
        }
        if (modelData == null) {
            throw new IllegalArgumentException("Invalid model id");
        }
        if (modelData.isModelLoaded()) {
            return this.mModule.getParameter(modelData.getHandle(), i);
        }
        throw new UnsupportedOperationException("Given model is not loaded:" + modelData);
    }

    public final boolean isRecognitionAllowed(ModelData modelData) {
        boolean z = false;
        if (!this.mIsAppOpPermitted) {
            return false;
        }
        int ordinal = this.mDeviceState.ordinal();
        if (ordinal != 0) {
            z = true;
            if (ordinal == 1) {
                synchronized (modelData) {
                    z = modelData.mRunInBatterySaverMode;
                }
            } else if (ordinal != 2) {
                throw new AssertionError("Enum changed between compile and runtime");
            }
        }
        return z;
    }

    public final void onDeviceStateChanged(
            DeviceStateHandler.SoundTriggerDeviceState soundTriggerDeviceState) {
        synchronized (this.mLock) {
            try {
                if (!this.mIsDetached && this.mDeviceState != soundTriggerDeviceState) {
                    this.mDeviceState = soundTriggerDeviceState;
                    updateAllRecognitionsLocked();
                }
            } finally {
            }
        }
    }

    public final void onGenericRecognitionLocked(
            SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
        IBinder iBinder;
        SoundTrigger.RecognitionConfig recognitionConfig;
        boolean z;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                SoundTriggerEvent$SessionEvent$Type.RECOGNITION;
        MetricsLogger.count(this.mContext, "sth_generic_recognition_event", 1);
        int i = genericRecognitionEvent.status;
        if (i == 0 || i == 3) {
            ModelData modelDataForLocked =
                    getModelDataForLocked(genericRecognitionEvent.soundModelHandle);
            if (modelDataForLocked == null || !modelDataForLocked.isGenericModel()) {
                HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                        new StringBuilder(
                                "Generic recognition event: Model does not exist for handle: "),
                        genericRecognitionEvent.soundModelHandle,
                        "SoundTriggerHelper");
                return;
            }
            IBinder token = genericRecognitionEvent.getToken();
            synchronized (modelDataForLocked) {
                iBinder = modelDataForLocked.mRecognitionToken;
            }
            if (Objects.equals(token, iBinder)) {
                IRecognitionStatusCallback callback = modelDataForLocked.getCallback();
                if (callback == null) {
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                            new StringBuilder(
                                    "Generic recognition event: Null callback for model handle: "),
                            genericRecognitionEvent.soundModelHandle,
                            "SoundTriggerHelper");
                    return;
                }
                if (!genericRecognitionEvent.recognitionStillActive) {
                    modelDataForLocked.setStopped();
                }
                try {
                    this.mEventLogger.enqueue(
                            new SoundTriggerEvent$ServiceEvent(
                                    soundTriggerEvent$SessionEvent$Type,
                                    modelDataForLocked.getModelId(),
                                    (String) null));
                    callback.onGenericSoundTriggerDetected(genericRecognitionEvent);
                    synchronized (modelDataForLocked) {
                        recognitionConfig = modelDataForLocked.mRecognitionConfig;
                    }
                    if (recognitionConfig == null) {
                        HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                                new StringBuilder(
                                        "Generic recognition event: Null RecognitionConfig for"
                                            + " model handle: "),
                                genericRecognitionEvent.soundModelHandle,
                                "SoundTriggerHelper");
                        return;
                    }
                    modelDataForLocked.setRequested(recognitionConfig.allowMultipleTriggers);
                    synchronized (modelDataForLocked) {
                        z = modelDataForLocked.mRequested;
                    }
                    if (z) {
                        updateRecognitionLocked(modelDataForLocked, true);
                    }
                } catch (RemoteException e) {
                    EventLogger eventLogger = this.mEventLogger;
                    SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                            new SoundTriggerEvent$ServiceEvent(
                                    soundTriggerEvent$SessionEvent$Type,
                                    modelDataForLocked.getModelId(),
                                    "RemoteException");
                    soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerHelper");
                    eventLogger.enqueue(soundTriggerEvent$ServiceEvent);
                    forceStopAndUnloadModelLocked(modelDataForLocked, e, null);
                }
            }
        }
    }

    public final void onKeyphraseRecognitionLocked(
            SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
        IBinder iBinder;
        SoundTrigger.RecognitionConfig recognitionConfig;
        boolean z;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                SoundTriggerEvent$SessionEvent$Type.RECOGNITION;
        Slog.i("SoundTriggerHelper", "Recognition success");
        MetricsLogger.count(this.mContext, "sth_keyphrase_recognition_event", 1);
        int i = Integer.MIN_VALUE;
        if (keyphraseRecognitionEvent == null) {
            Slog.w("SoundTriggerHelper", "Null RecognitionEvent received.");
        } else {
            SoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr =
                    keyphraseRecognitionEvent.keyphraseExtras;
            if (keyphraseRecognitionExtraArr == null || keyphraseRecognitionExtraArr.length == 0) {
                Slog.w("SoundTriggerHelper", "Invalid keyphrase recognition event!");
            } else {
                i = keyphraseRecognitionExtraArr[0].id;
            }
        }
        ModelData keyphraseModelDataLocked = getKeyphraseModelDataLocked(i);
        if (keyphraseModelDataLocked == null || !keyphraseModelDataLocked.isKeyphraseModel()) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i, "Keyphase model data does not exist for ID:", "SoundTriggerHelper");
            return;
        }
        IBinder token = keyphraseRecognitionEvent.getToken();
        synchronized (keyphraseModelDataLocked) {
            iBinder = keyphraseModelDataLocked.mRecognitionToken;
        }
        if (Objects.equals(token, iBinder)) {
            if (keyphraseModelDataLocked.getCallback() == null) {
                Slog.w(
                        "SoundTriggerHelper",
                        "Received onRecognition event without callback for keyphrase model.");
                return;
            }
            if (!keyphraseRecognitionEvent.recognitionStillActive) {
                keyphraseModelDataLocked.setStopped();
            }
            try {
                this.mEventLogger.enqueue(
                        new SoundTriggerEvent$ServiceEvent(
                                soundTriggerEvent$SessionEvent$Type,
                                keyphraseModelDataLocked.getModelId(),
                                (String) null));
                keyphraseModelDataLocked
                        .getCallback()
                        .onKeyphraseDetected(keyphraseRecognitionEvent);
                synchronized (keyphraseModelDataLocked) {
                    recognitionConfig = keyphraseModelDataLocked.mRecognitionConfig;
                }
                if (recognitionConfig != null) {
                    keyphraseModelDataLocked.setRequested(recognitionConfig.allowMultipleTriggers);
                }
                synchronized (keyphraseModelDataLocked) {
                    z = keyphraseModelDataLocked.mRequested;
                }
                if (z) {
                    updateRecognitionLocked(keyphraseModelDataLocked, true);
                }
            } catch (RemoteException e) {
                EventLogger eventLogger = this.mEventLogger;
                SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                        new SoundTriggerEvent$ServiceEvent(
                                soundTriggerEvent$SessionEvent$Type,
                                keyphraseModelDataLocked.getModelId(),
                                "RemoteException");
                soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerHelper");
                eventLogger.enqueue(soundTriggerEvent$ServiceEvent);
                forceStopAndUnloadModelLocked(keyphraseModelDataLocked, e, null);
            }
        }
    }

    public final void onModelUnloaded(int i) {
        synchronized (this.mLock) {
            MetricsLogger.count(this.mContext, "sth_sound_model_updated", 1);
            ModelData modelDataForLocked = getModelDataForLocked(i);
            if (modelDataForLocked != null) {
                synchronized (modelDataForLocked) {
                    modelDataForLocked.mRecognitionToken = null;
                    modelDataForLocked.mModelState = 0;
                }
            }
        }
    }

    public final void onRecognition(SoundTrigger.RecognitionEvent recognitionEvent) {
        if (recognitionEvent == null) {
            Slog.w("SoundTriggerHelper", "Null recognition event!");
            return;
        }
        if (!(recognitionEvent instanceof SoundTrigger.KeyphraseRecognitionEvent)
                && !(recognitionEvent instanceof SoundTrigger.GenericRecognitionEvent)) {
            Slog.w(
                    "SoundTriggerHelper",
                    "Invalid recognition event type (not one of generic or keyphrase)!");
            return;
        }
        synchronized (this.mLock) {
            try {
                int i = recognitionEvent.status;
                if (i != 0) {
                    if (i == 1) {
                        onRecognitionAbortLocked(recognitionEvent);
                    } else if (i != 2 && i != 3) {
                    }
                }
                if (recognitionEvent instanceof SoundTrigger.KeyphraseRecognitionEvent) {
                    onKeyphraseRecognitionLocked(
                            (SoundTrigger.KeyphraseRecognitionEvent) recognitionEvent);
                } else {
                    onGenericRecognitionLocked(
                            (SoundTrigger.GenericRecognitionEvent) recognitionEvent);
                }
            } finally {
            }
        }
    }

    public final void onRecognitionAbortLocked(SoundTrigger.RecognitionEvent recognitionEvent) {
        IBinder iBinder;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                SoundTriggerEvent$SessionEvent$Type.PAUSE;
        Slog.w("SoundTriggerHelper", "Recognition aborted");
        MetricsLogger.count(this.mContext, "sth_recognition_aborted", 1);
        ModelData modelDataForLocked = getModelDataForLocked(recognitionEvent.soundModelHandle);
        if (modelDataForLocked == null || !modelDataForLocked.isModelStarted()) {
            return;
        }
        IBinder token = recognitionEvent.getToken();
        synchronized (modelDataForLocked) {
            iBinder = modelDataForLocked.mRecognitionToken;
        }
        if (Objects.equals(token, iBinder)) {
            modelDataForLocked.setStopped();
            try {
                IRecognitionStatusCallback callback = modelDataForLocked.getCallback();
                if (callback != null) {
                    this.mEventLogger.enqueue(
                            new SoundTriggerEvent$ServiceEvent(
                                    soundTriggerEvent$SessionEvent$Type,
                                    modelDataForLocked.getModelId(),
                                    (String) null));
                    callback.onRecognitionPaused();
                }
            } catch (RemoteException e) {
                EventLogger eventLogger = this.mEventLogger;
                SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                        new SoundTriggerEvent$ServiceEvent(
                                soundTriggerEvent$SessionEvent$Type,
                                modelDataForLocked.getModelId(),
                                "RemoteException");
                soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerHelper");
                eventLogger.enqueue(soundTriggerEvent$ServiceEvent);
                forceStopAndUnloadModelLocked(modelDataForLocked, e, null);
            }
        }
    }

    public final void onResourcesAvailable() {
        synchronized (this.mLock) {
            this.mEventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            SoundTriggerEvent$SessionEvent$Type.RESOURCES_AVAILABLE,
                            (UUID) null,
                            (String) null));
            updateAllRecognitionsLocked();
        }
    }

    public final void onServiceDied() {
        Slog.e("SoundTriggerHelper", "onServiceDied!!");
        MetricsLogger.count(this.mContext, "sth_service_died", 1);
        synchronized (this.mLock) {
            onServiceDiedLocked();
        }
    }

    public final void onServiceDiedLocked() {
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                SoundTriggerEvent$SessionEvent$Type.MODULE_DIED;
        try {
            MetricsLogger.count(this.mContext, "sth_service_died", 1);
            for (ModelData modelData : this.mModelDataMap.values()) {
                IRecognitionStatusCallback callback = modelData.getCallback();
                if (callback != null) {
                    try {
                        EventLogger eventLogger = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        modelData.getModelId(),
                                        (String) null);
                        soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerHelper");
                        eventLogger.enqueue(soundTriggerEvent$ServiceEvent);
                        callback.onModuleDied();
                    } catch (RemoteException unused) {
                        EventLogger eventLogger2 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent2 =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        modelData.getModelId(),
                                        "RemoteException");
                        soundTriggerEvent$ServiceEvent2.printLog(2, "SoundTriggerHelper");
                        eventLogger2.enqueue(soundTriggerEvent$ServiceEvent2);
                    }
                }
            }
            Iterator it = this.mModelDataMap.values().iterator();
            while (it.hasNext()) {
                ((ModelData) it.next()).clearState();
            }
            SoundTriggerModule soundTriggerModule = this.mModule;
            if (soundTriggerModule != null) {
                soundTriggerModule.detach();
                try {
                    this.mModule = (SoundTriggerModule) this.mModuleProvider.apply(this);
                } catch (Exception unused2) {
                    this.mModule = null;
                }
            }
        } catch (Throwable th) {
            Iterator it2 = this.mModelDataMap.values().iterator();
            while (it2.hasNext()) {
                ((ModelData) it2.next()).clearState();
            }
            SoundTriggerModule soundTriggerModule2 = this.mModule;
            if (soundTriggerModule2 != null) {
                soundTriggerModule2.detach();
                try {
                    this.mModule = (SoundTriggerModule) this.mModuleProvider.apply(this);
                } catch (Exception unused3) {
                    this.mModule = null;
                }
            }
            throw th;
        }
    }

    public final SoundTrigger.ModelParamRange queryParameterLocked(ModelData modelData, int i) {
        MetricsLogger.count(this.mContext, "sth_query_parameter", 1);
        if (this.mModule == null) {
            return null;
        }
        if (modelData == null) {
            Slog.w("SoundTriggerHelper", "queryParameter: Invalid model id");
            return null;
        }
        if (modelData.isModelLoaded()) {
            return this.mModule.queryParameter(modelData.getHandle(), i);
        }
        Slog.i("SoundTriggerHelper", "queryParameter: Given model is not loaded:" + modelData);
        return null;
    }

    public final int setParameterLocked(ModelData modelData, int i, int i2) {
        MetricsLogger.count(this.mContext, "sth_set_parameter", 1);
        if (this.mModule == null) {
            return SoundTrigger.STATUS_NO_INIT;
        }
        if (modelData != null && modelData.isModelLoaded()) {
            return this.mModule.setParameter(modelData.getHandle(), i, i2);
        }
        Slog.i("SoundTriggerHelper", "SetParameter: Given model is not loaded:" + modelData);
        return SoundTrigger.STATUS_BAD_VALUE;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0043 A[Catch: all -> 0x004c, TryCatch #0 {all -> 0x004c, blocks: (B:9:0x0017, B:11:0x001b, B:13:0x0025, B:16:0x0043, B:17:0x004a, B:20:0x004e, B:21:0x0057, B:23:0x0030, B:25:0x0036, B:26:0x0059, B:27:0x0060), top: B:8:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004e A[Catch: all -> 0x004c, TryCatch #0 {all -> 0x004c, blocks: (B:9:0x0017, B:11:0x001b, B:13:0x0025, B:16:0x0043, B:17:0x004a, B:20:0x004e, B:21:0x0057, B:23:0x0030, B:25:0x0036, B:26:0x0059, B:27:0x0060), top: B:8:0x0017 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int startGenericRecognition(
            java.util.UUID r11,
            android.hardware.soundtrigger.SoundTrigger.GenericSoundModel r12,
            android.hardware.soundtrigger.IRecognitionStatusCallback r13,
            android.hardware.soundtrigger.SoundTrigger.RecognitionConfig r14,
            boolean r15) {
        /*
            r10 = this;
            android.content.Context r0 = r10.mContext
            java.lang.String r1 = "sth_start_recognition"
            r2 = 1
            com.android.internal.logging.MetricsLogger.count(r0, r1, r2)
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r11 == 0) goto L63
            if (r12 == 0) goto L63
            if (r13 == 0) goto L63
            if (r14 != 0) goto L14
            goto L63
        L14:
            java.lang.Object r1 = r10.mLock
            monitor-enter(r1)
            boolean r3 = r10.mIsDetached     // Catch: java.lang.Throwable -> L4c
            if (r3 != 0) goto L59
            java.util.HashMap r3 = r10.mModelDataMap     // Catch: java.lang.Throwable -> L4c
            java.lang.Object r3 = r3.get(r11)     // Catch: java.lang.Throwable -> L4c
            com.android.server.soundtrigger.SoundTriggerHelper$ModelData r3 = (com.android.server.soundtrigger.SoundTriggerHelper.ModelData) r3     // Catch: java.lang.Throwable -> L4c
            if (r3 != 0) goto L30
            com.android.server.soundtrigger.SoundTriggerHelper$ModelData r3 = new com.android.server.soundtrigger.SoundTriggerHelper$ModelData     // Catch: java.lang.Throwable -> L4c
            r3.<init>(r11, r2)     // Catch: java.lang.Throwable -> L4c
            java.util.HashMap r2 = r10.mModelDataMap     // Catch: java.lang.Throwable -> L4c
            r2.put(r11, r3)     // Catch: java.lang.Throwable -> L4c
            goto L40
        L30:
            boolean r11 = r3.isGenericModel()     // Catch: java.lang.Throwable -> L4c
            if (r11 != 0) goto L40
            java.lang.String r11 = "SoundTriggerHelper"
            java.lang.String r2 = "UUID already used for non-generic model."
            android.util.Slog.e(r11, r2)     // Catch: java.lang.Throwable -> L4c
            r11 = 0
            r6 = r11
            goto L41
        L40:
            r6 = r3
        L41:
            if (r6 != 0) goto L4e
            java.lang.String r10 = "SoundTriggerHelper"
            java.lang.String r11 = "Irrecoverable error occurred, check UUID / sound model data."
            android.util.Slog.w(r10, r11)     // Catch: java.lang.Throwable -> L4c
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L4c
            return r0
        L4c:
            r10 = move-exception
            goto L61
        L4e:
            r4 = r10
            r5 = r12
            r7 = r13
            r8 = r14
            r9 = r15
            int r10 = r4.startRecognition(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L4c
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L4c
            return r10
        L59:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L4c
            java.lang.String r11 = "SoundTriggerHelper has been detached"
            r10.<init>(r11)     // Catch: java.lang.Throwable -> L4c
            throw r10     // Catch: java.lang.Throwable -> L4c
        L61:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L4c
            throw r10
        L63:
            java.lang.String r10 = "SoundTriggerHelper"
            java.lang.String r11 = "Passed in bad data to startGenericRecognition()."
            android.util.Slog.w(r10, r11)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.soundtrigger.SoundTriggerHelper.startGenericRecognition(java.util.UUID,"
                    + " android.hardware.soundtrigger.SoundTrigger$GenericSoundModel,"
                    + " android.hardware.soundtrigger.IRecognitionStatusCallback,"
                    + " android.hardware.soundtrigger.SoundTrigger$RecognitionConfig,"
                    + " boolean):int");
    }

    public final int startRecognition(
            SoundTrigger.SoundModel soundModel,
            ModelData modelData,
            IRecognitionStatusCallback iRecognitionStatusCallback,
            SoundTrigger.RecognitionConfig recognitionConfig,
            boolean z) {
        SoundTrigger.SoundModel soundModel2;
        SoundTrigger.SoundModel soundModel3;
        SoundTrigger.SoundModel soundModel4;
        boolean z2;
        boolean z3;
        int tryStopAndUnloadLocked;
        synchronized (this.mLock) {
            IRecognitionStatusCallback callback = modelData.getCallback();
            if (callback != null && callback.asBinder() != iRecognitionStatusCallback.asBinder()) {
                Slog.w(
                        "SoundTriggerHelper",
                        "Canceling previous recognition for model id: " + modelData.getModelId());
                try {
                    callback.onPreempted();
                } catch (RemoteException e) {
                    Slog.w("SoundTriggerHelper", "RemoteException in onDetectionStopped", e);
                }
                synchronized (modelData) {
                    modelData.mCallback = null;
                }
            }
            synchronized (modelData) {
                soundModel2 = modelData.mSoundModel;
            }
            if (soundModel2 != null) {
                synchronized (modelData) {
                    soundModel3 = modelData.mSoundModel;
                }
                if (soundModel3.equals(soundModel) && modelData.isModelStarted()) {
                    z2 = true;
                    z3 = false;
                } else {
                    synchronized (modelData) {
                        soundModel4 = modelData.mSoundModel;
                    }
                    if (soundModel4.equals(soundModel)) {
                        z2 = false;
                        z3 = false;
                    } else {
                        z2 = modelData.isModelStarted();
                        z3 = modelData.isModelLoaded();
                    }
                }
                if ((z2 || z3)
                        && (tryStopAndUnloadLocked = tryStopAndUnloadLocked(modelData, z2, z3))
                                != 0) {
                    Slog.w(
                            "SoundTriggerHelper",
                            "Unable to stop or unload previous model: " + modelData.toString());
                    return tryStopAndUnloadLocked;
                }
            }
            synchronized (modelData) {
                modelData.mCallback = iRecognitionStatusCallback;
            }
            modelData.setRequested(true);
            synchronized (modelData) {
                modelData.mRecognitionConfig = recognitionConfig;
            }
            synchronized (modelData) {
                modelData.mRunInBatterySaverMode = z;
            }
            synchronized (modelData) {
                modelData.mSoundModel = soundModel;
            }
            if (isRecognitionAllowed(modelData)) {
                int updateRecognitionLocked = updateRecognitionLocked(modelData, false);
                if (updateRecognitionLocked == 0) {
                    return updateRecognitionLocked;
                }
                if (updateRecognitionLocked != SoundTrigger.STATUS_BUSY) {
                    modelData.setRequested(false);
                    return updateRecognitionLocked;
                }
            }
            if (iRecognitionStatusCallback != null) {
                try {
                    this.mEventLogger.enqueue(
                            new SoundTriggerEvent$ServiceEvent(
                                    SoundTriggerEvent$SessionEvent$Type.PAUSE,
                                    modelData.getModelId(),
                                    (String) null));
                    iRecognitionStatusCallback.onRecognitionPaused();
                } catch (RemoteException e2) {
                    EventLogger eventLogger = this.mEventLogger;
                    SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                            new SoundTriggerEvent$ServiceEvent(
                                    SoundTriggerEvent$SessionEvent$Type.PAUSE,
                                    modelData.getModelId(),
                                    "RemoteException");
                    soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerHelper");
                    eventLogger.enqueue(soundTriggerEvent$ServiceEvent);
                    forceStopAndUnloadModelLocked(modelData, e2, null);
                }
            }
            return 0;
        }
    }

    public final int stopGenericRecognition(
            UUID uuid, IRecognitionStatusCallback iRecognitionStatusCallback) {
        synchronized (this.mLock) {
            try {
                MetricsLogger.count(this.mContext, "sth_stop_recognition", 1);
                if (iRecognitionStatusCallback != null && uuid != null) {
                    if (this.mIsDetached) {
                        throw new IllegalStateException("SoundTriggerHelper has been detached");
                    }
                    ModelData modelData = (ModelData) this.mModelDataMap.get(uuid);
                    if (modelData != null && modelData.isGenericModel()) {
                        int stopRecognition =
                                stopRecognition(modelData, iRecognitionStatusCallback);
                        if (stopRecognition != 0) {
                            Slog.w(
                                    "SoundTriggerHelper",
                                    "stopGenericRecognition failed: " + stopRecognition);
                        }
                        return stopRecognition;
                    }
                    Slog.w(
                            "SoundTriggerHelper",
                            "Attempting stopRecognition on invalid model with id:" + uuid);
                    return Integer.MIN_VALUE;
                }
                Slog.e(
                        "SoundTriggerHelper",
                        "Null callbackreceived for stopGenericRecognition() for modelid:" + uuid);
                return Integer.MIN_VALUE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int stopKeyphraseRecognition(
            int i, IRecognitionStatusCallback iRecognitionStatusCallback) {
        synchronized (this.mLock) {
            try {
                MetricsLogger.count(this.mContext, "sth_stop_recognition", 1);
                if (iRecognitionStatusCallback == null) {
                    Slog.e(
                            "SoundTriggerHelper",
                            "Null callback received for stopKeyphraseRecognition() for keyphraseId:"
                                    + i);
                    return Integer.MIN_VALUE;
                }
                if (this.mIsDetached) {
                    throw new IllegalStateException("SoundTriggerHelper has been detached");
                }
                ModelData keyphraseModelDataLocked = getKeyphraseModelDataLocked(i);
                if (keyphraseModelDataLocked != null
                        && keyphraseModelDataLocked.isKeyphraseModel()) {
                    int stopRecognition =
                            stopRecognition(keyphraseModelDataLocked, iRecognitionStatusCallback);
                    return stopRecognition != 0 ? stopRecognition : stopRecognition;
                }
                Slog.w("SoundTriggerHelper", "No model exists for given keyphrase Id " + i);
                return Integer.MIN_VALUE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int stopRecognition(
            ModelData modelData, IRecognitionStatusCallback iRecognitionStatusCallback) {
        boolean z;
        synchronized (this.mLock) {
            try {
                if (iRecognitionStatusCallback == null) {
                    return Integer.MIN_VALUE;
                }
                if (this.mModule == null) {
                    Slog.w("SoundTriggerHelper", "Attempting stopRecognition after detach");
                    return Integer.MIN_VALUE;
                }
                IRecognitionStatusCallback callback = modelData.getCallback();
                if (callback != null) {
                    synchronized (modelData) {
                        z = modelData.mRequested;
                    }
                    if (z || modelData.isModelStarted()) {
                        if (callback.asBinder() != iRecognitionStatusCallback.asBinder()) {
                            Slog.w(
                                    "SoundTriggerHelper",
                                    "Attempting stopRecognition for another recognition");
                            return Integer.MIN_VALUE;
                        }
                        modelData.setRequested(false);
                        int updateRecognitionLocked = updateRecognitionLocked(modelData, false);
                        if (updateRecognitionLocked != 0) {
                            return updateRecognitionLocked;
                        }
                        synchronized (modelData) {
                            modelData.mModelState = 1;
                        }
                        synchronized (modelData) {
                            modelData.mCallback = null;
                        }
                        synchronized (modelData) {
                            modelData.mRecognitionConfig = null;
                        }
                        return updateRecognitionLocked;
                    }
                }
                Slog.w(
                        "SoundTriggerHelper",
                        "Attempting stopRecognition without a successful startRecognition");
                return Integer.MIN_VALUE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int stopRecognitionLocked(ModelData modelData, boolean z) {
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                SoundTriggerEvent$SessionEvent$Type.PAUSE;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type2 =
                SoundTriggerEvent$SessionEvent$Type.PAUSE_FAILED;
        if (this.mModule == null) {
            return Integer.MIN_VALUE;
        }
        IRecognitionStatusCallback callback = modelData.getCallback();
        int stopRecognition = this.mModule.stopRecognition(modelData.getHandle());
        if (stopRecognition != 0) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    stopRecognition, "stopRecognition call failed with ", "SoundTriggerHelper");
            MetricsLogger.count(this.mContext, "sth_stop_recognition_error", 1);
            if (z) {
                try {
                    EventLogger eventLogger = this.mEventLogger;
                    SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                            new SoundTriggerEvent$ServiceEvent(
                                    soundTriggerEvent$SessionEvent$Type2,
                                    modelData.getModelId(),
                                    String.valueOf(stopRecognition));
                    soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerHelper");
                    eventLogger.enqueue(soundTriggerEvent$ServiceEvent);
                    modelData.setRequested(false);
                    callback.onPauseFailed(stopRecognition);
                } catch (RemoteException e) {
                    EventLogger eventLogger2 = this.mEventLogger;
                    SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent2 =
                            new SoundTriggerEvent$ServiceEvent(
                                    soundTriggerEvent$SessionEvent$Type2,
                                    modelData.getModelId(),
                                    String.valueOf(stopRecognition) + " - RemoteException");
                    soundTriggerEvent$ServiceEvent2.printLog(2, "SoundTriggerHelper");
                    eventLogger2.enqueue(soundTriggerEvent$ServiceEvent2);
                    forceStopAndUnloadModelLocked(modelData, e, null);
                }
            }
        } else {
            modelData.setStopped();
            MetricsLogger.count(this.mContext, "sth_stop_recognition_success", 1);
            if (z) {
                try {
                    this.mEventLogger.enqueue(
                            new SoundTriggerEvent$ServiceEvent(
                                    soundTriggerEvent$SessionEvent$Type,
                                    modelData.getModelId(),
                                    (String) null));
                    callback.onRecognitionPaused();
                } catch (RemoteException e2) {
                    EventLogger eventLogger3 = this.mEventLogger;
                    SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent3 =
                            new SoundTriggerEvent$ServiceEvent(
                                    soundTriggerEvent$SessionEvent$Type,
                                    modelData.getModelId(),
                                    "RemoteException");
                    soundTriggerEvent$ServiceEvent3.printLog(2, "SoundTriggerHelper");
                    eventLogger3.enqueue(soundTriggerEvent$ServiceEvent3);
                    forceStopAndUnloadModelLocked(modelData, e2, null);
                }
            }
        }
        return stopRecognition;
    }

    public final int tryStopAndUnloadLocked(ModelData modelData, boolean z, boolean z2) {
        int i;
        boolean z3;
        synchronized (modelData) {
            i = 0;
            z3 = modelData.mModelState == 0;
        }
        if (z3) {
            return 0;
        }
        if (z && modelData.isModelStarted() && (i = stopRecognitionLocked(modelData, false)) != 0) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "stopRecognition failed: ", "SoundTriggerHelper");
            return i;
        }
        if (z2 && modelData.isModelLoaded()) {
            Slog.d("SoundTriggerHelper", "Unloading previously loaded stale model.");
            SoundTriggerModule soundTriggerModule = this.mModule;
            if (soundTriggerModule == null) {
                return Integer.MIN_VALUE;
            }
            i = soundTriggerModule.unloadSoundModel(modelData.getHandle());
            MetricsLogger.count(this.mContext, "sth_unloading_stale_model", 1);
            if (i != 0) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i, "unloadSoundModel call failed with ", "SoundTriggerHelper");
            } else {
                modelData.clearState();
            }
        }
        return i;
    }

    public final int unloadGenericSoundModel(UUID uuid) {
        int stopRecognitionLocked;
        synchronized (this.mLock) {
            try {
                MetricsLogger.count(this.mContext, "sth_unload_generic_sound_model", 1);
                if (uuid != null && this.mModule != null) {
                    if (this.mIsDetached) {
                        throw new IllegalStateException("SoundTriggerHelper has been detached");
                    }
                    ModelData modelData = (ModelData) this.mModelDataMap.get(uuid);
                    if (modelData != null && modelData.isGenericModel()) {
                        if (!modelData.isModelLoaded()) {
                            Slog.i(
                                    "SoundTriggerHelper",
                                    "Unload: Given generic model is not loaded:" + uuid);
                            return 0;
                        }
                        if (modelData.isModelStarted()
                                && (stopRecognitionLocked = stopRecognitionLocked(modelData, false))
                                        != 0) {
                            Slog.w(
                                    "SoundTriggerHelper",
                                    "stopGenericRecognition failed: " + stopRecognitionLocked);
                        }
                        SoundTriggerModule soundTriggerModule = this.mModule;
                        if (soundTriggerModule == null) {
                            return Integer.MIN_VALUE;
                        }
                        int unloadSoundModel =
                                soundTriggerModule.unloadSoundModel(modelData.getHandle());
                        if (unloadSoundModel != 0) {
                            Slog.w(
                                    "SoundTriggerHelper",
                                    "unloadGenericSoundModel() call failed with "
                                            + unloadSoundModel);
                            Slog.w(
                                    "SoundTriggerHelper",
                                    "unloadGenericSoundModel() force-marking model as unloaded.");
                        }
                        this.mModelDataMap.remove(uuid);
                        return unloadSoundModel;
                    }
                    Slog.w(
                            "SoundTriggerHelper",
                            "Unload error: Attempting unload invalid generic model with id:"
                                    + uuid);
                    return Integer.MIN_VALUE;
                }
                return Integer.MIN_VALUE;
            } finally {
            }
        }
    }

    public final int unloadKeyphraseSoundModel(int i) {
        synchronized (this.mLock) {
            try {
                MetricsLogger.count(this.mContext, "sth_unload_keyphrase_sound_model", 1);
                ModelData keyphraseModelDataLocked = getKeyphraseModelDataLocked(i);
                if (this.mModule != null
                        && keyphraseModelDataLocked != null
                        && keyphraseModelDataLocked.isModelLoaded()
                        && keyphraseModelDataLocked.isKeyphraseModel()) {
                    if (this.mIsDetached) {
                        throw new IllegalStateException("SoundTriggerHelper has been detached");
                    }
                    keyphraseModelDataLocked.setRequested(false);
                    int updateRecognitionLocked =
                            updateRecognitionLocked(keyphraseModelDataLocked, false);
                    if (updateRecognitionLocked != 0) {
                        Slog.w(
                                "SoundTriggerHelper",
                                "Stop recognition failed for keyphrase ID:"
                                        + updateRecognitionLocked);
                    }
                    int unloadSoundModel =
                            this.mModule.unloadSoundModel(keyphraseModelDataLocked.getHandle());
                    if (unloadSoundModel != 0) {
                        Slog.w(
                                "SoundTriggerHelper",
                                "unloadKeyphraseSoundModel call failed with " + unloadSoundModel);
                    }
                    UUID uuid = (UUID) this.mKeyphraseUuidMap.get(Integer.valueOf(i));
                    if (uuid != null) {
                        this.mModelDataMap.remove(uuid);
                        this.mKeyphraseUuidMap.remove(Integer.valueOf(i));
                    }
                    return unloadSoundModel;
                }
                return Integer.MIN_VALUE;
            } finally {
            }
        }
    }

    public final void updateAllRecognitionsLocked() {
        Iterator it = new ArrayList(this.mModelDataMap.values()).iterator();
        while (it.hasNext()) {
            updateRecognitionLocked((ModelData) it.next(), true);
        }
    }

    public final int updateRecognitionLocked(ModelData modelData, boolean z) {
        boolean z2;
        int i;
        SoundTrigger.SoundModel soundModel;
        SoundTrigger.RecognitionConfig recognitionConfig;
        IBinder startRecognitionWithToken;
        synchronized (modelData) {
            z2 = modelData.mRequested;
        }
        boolean z3 = z2 && isRecognitionAllowed(modelData);
        if (z3 == modelData.isModelStarted()) {
            return 0;
        }
        if (!z3) {
            return stopRecognitionLocked(modelData, z);
        }
        int i2 = Integer.MIN_VALUE;
        if (this.mModule == null) {
            Slog.w(
                    "SoundTriggerHelper",
                    "prepareForRecognition: cannot attach to sound trigger module");
            i = Integer.MIN_VALUE;
        } else {
            if (!modelData.isModelLoaded()) {
                Iterator it = this.mModelDataMap.entrySet().iterator();
                while (it.hasNext()) {
                    ModelData modelData2 = (ModelData) ((Map.Entry) it.next()).getValue();
                    if (modelData2.isModelLoaded()
                            && (modelData2.getCallback() == null
                                    || (modelData2.getCallback().asBinder() != null
                                            && !modelData2
                                                    .getCallback()
                                                    .asBinder()
                                                    .pingBinder()))) {
                        Slog.w(
                                "SoundTriggerHelper",
                                "Removing model "
                                        + modelData2.getHandle()
                                        + " that has no clients");
                        forceStopAndUnloadModelLocked(modelData2, null, it);
                    }
                }
                int[] iArr = {0};
                SoundTriggerModule soundTriggerModule = this.mModule;
                synchronized (modelData) {
                    soundModel = modelData.mSoundModel;
                }
                i = soundTriggerModule.loadSoundModel(soundModel, iArr);
                if (i != 0) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            i,
                            "prepareForRecognition: loadSoundModel failed with status: ",
                            "SoundTriggerHelper");
                } else {
                    int i3 = iArr[0];
                    synchronized (modelData) {
                        modelData.mModelHandle = i3;
                    }
                    synchronized (modelData) {
                        modelData.mModelState = 1;
                    }
                }
            }
            i = 0;
        }
        if (i != 0) {
            Slog.w(
                    "SoundTriggerHelper",
                    "startRecognition failed to prepare model for recognition");
            return i;
        }
        IRecognitionStatusCallback callback = modelData.getCallback();
        synchronized (modelData) {
            recognitionConfig = modelData.mRecognitionConfig;
        }
        if (callback == null || !modelData.isModelLoaded() || recognitionConfig == null) {
            Slog.w("SoundTriggerHelper", "startRecognition: Bad data passed in.");
            MetricsLogger.count(this.mContext, "sth_start_recognition_error", 1);
        } else {
            if (!isRecognitionAllowed(modelData)) {
                Slog.w("SoundTriggerHelper", "startRecognition requested but not allowed.");
                MetricsLogger.count(this.mContext, "sth_start_recognition_not_allowed", 1);
                return 0;
            }
            SoundTriggerModule soundTriggerModule2 = this.mModule;
            if (soundTriggerModule2 != null) {
                try {
                    startRecognitionWithToken =
                            soundTriggerModule2.startRecognitionWithToken(
                                    modelData.getHandle(), recognitionConfig);
                } catch (Exception e) {
                    i2 = SoundTrigger.handleException(e);
                }
                synchronized (modelData) {
                    modelData.mRecognitionToken = startRecognitionWithToken;
                    i2 = 0;
                    if (i2 != 0) {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                i2, "startRecognition failed with ", "SoundTriggerHelper");
                        MetricsLogger.count(this.mContext, "sth_start_recognition_error", 1);
                        if (z) {
                            try {
                                EventLogger eventLogger = this.mEventLogger;
                                SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                                        new SoundTriggerEvent$ServiceEvent(
                                                SoundTriggerEvent$SessionEvent$Type.RESUME_FAILED,
                                                modelData.getModelId(),
                                                String.valueOf(i2));
                                soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerHelper");
                                eventLogger.enqueue(soundTriggerEvent$ServiceEvent);
                                modelData.setRequested(false);
                                callback.onResumeFailed(i2);
                            } catch (RemoteException e2) {
                                EventLogger eventLogger2 = this.mEventLogger;
                                SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent2 =
                                        new SoundTriggerEvent$ServiceEvent(
                                                SoundTriggerEvent$SessionEvent$Type.RESUME_FAILED,
                                                modelData.getModelId(),
                                                String.valueOf(i2) + " - RemoteException");
                                soundTriggerEvent$ServiceEvent2.printLog(2, "SoundTriggerHelper");
                                eventLogger2.enqueue(soundTriggerEvent$ServiceEvent2);
                                forceStopAndUnloadModelLocked(modelData, e2, null);
                            }
                        }
                    } else {
                        Slog.i("SoundTriggerHelper", "startRecognition successful.");
                        MetricsLogger.count(this.mContext, "sth_start_recognition_success", 1);
                        synchronized (modelData) {
                            modelData.mModelState = 2;
                            if (z) {
                                try {
                                    this.mEventLogger.enqueue(
                                            new SoundTriggerEvent$ServiceEvent(
                                                    SoundTriggerEvent$SessionEvent$Type.RESUME,
                                                    modelData.getModelId(),
                                                    (String) null));
                                    callback.onRecognitionResumed();
                                } catch (RemoteException e3) {
                                    EventLogger eventLogger3 = this.mEventLogger;
                                    SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent3 =
                                            new SoundTriggerEvent$ServiceEvent(
                                                    SoundTriggerEvent$SessionEvent$Type.RESUME,
                                                    modelData.getModelId(),
                                                    "RemoteException");
                                    soundTriggerEvent$ServiceEvent3.printLog(
                                            2, "SoundTriggerHelper");
                                    eventLogger3.enqueue(soundTriggerEvent$ServiceEvent3);
                                    forceStopAndUnloadModelLocked(modelData, e3, null);
                                }
                            }
                        }
                    }
                }
            }
        }
        return i2;
    }
}
