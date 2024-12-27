package android.service.voice;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.compat.CompatChanges;
import android.content.Intent;
import android.hardware.soundtrigger.KeyphraseEnrollmentInfo;
import android.hardware.soundtrigger.KeyphraseMetadata;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.media.permission.Identity;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.service.voice.AlwaysOnHotwordDetector;
import android.service.voice.HotwordDetector;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.internal.app.IHotwordRecognitionStatusCallback;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.app.IVoiceInteractionSoundTriggerSession;
import com.android.internal.hidden_from_bootclasspath.android.app.wearable.Flags;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.FunctionalUtils;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

@SystemApi
/* loaded from: classes3.dex */
public class AlwaysOnHotwordDetector extends AbstractDetector {
    public static final int AUDIO_CAPABILITY_ECHO_CANCELLATION = 1;
    public static final int AUDIO_CAPABILITY_NOISE_SUPPRESSION = 2;
    static final boolean DBG = false;
    public static final int MODEL_PARAM_THRESHOLD_FACTOR = 0;
    private static final int MSG_AVAILABILITY_CHANGED = 1;
    private static final int MSG_DETECTION_ERROR = 3;
    private static final int MSG_DETECTION_HOTWORD_DETECTION_SERVICE_FAILURE = 9;
    private static final int MSG_DETECTION_PAUSE = 4;
    private static final int MSG_DETECTION_RESUME = 5;
    private static final int MSG_DETECTION_SOUND_TRIGGER_FAILURE = 10;
    private static final int MSG_DETECTION_UNKNOWN_FAILURE = 11;
    private static final int MSG_HOTWORD_DETECTED = 2;
    private static final int MSG_HOTWORD_REJECTED = 6;
    private static final int MSG_HOTWORD_STATUS_REPORTED = 7;
    private static final int MSG_PROCESS_RESTARTED = 8;
    public static final int RECOGNITION_FLAG_ALLOW_MULTIPLE_TRIGGERS = 2;
    public static final int RECOGNITION_FLAG_CAPTURE_TRIGGER_AUDIO = 1;
    public static final int RECOGNITION_FLAG_ENABLE_AUDIO_ECHO_CANCELLATION = 4;
    public static final int RECOGNITION_FLAG_ENABLE_AUDIO_NOISE_SUPPRESSION = 8;
    public static final int RECOGNITION_FLAG_NONE = 0;
    public static final int RECOGNITION_FLAG_RUN_IN_BATTERY_SAVER = 16;
    public static final int RECOGNITION_MODE_USER_IDENTIFICATION = 2;
    public static final int RECOGNITION_MODE_VOICE_TRIGGER = 1;
    static final long SEND_ON_FAILURE_FOR_ASYNC_EXCEPTIONS = 280471513;
    public static final int STATE_ERROR = 3;
    public static final int STATE_HARDWARE_UNAVAILABLE = -2;
    private static final int STATE_INVALID = -3;
    public static final int STATE_KEYPHRASE_ENROLLED = 2;
    public static final int STATE_KEYPHRASE_UNENROLLED = 1;

    @Deprecated
    public static final int STATE_KEYPHRASE_UNSUPPORTED = -1;
    private static final int STATE_NOT_READY = 0;
    private static final int STATUS_ERROR = Integer.MIN_VALUE;
    private static final int STATUS_OK = 0;
    static final String TAG = "AlwaysOnHotwordDetector";
    static final long THROW_ON_INITIALIZE_IF_NO_DSP = 269165460;
    private final String mAttributionTag;
    private int mAvailability;
    private final IBinder mBinder;
    private final Callback mExternalCallback;
    private final Executor mExternalExecutor;
    private final Handler mHandler;
    private final SoundTriggerListener mInternalCallback;
    private boolean mIsAvailabilityOverriddenByTestApi;
    private final KeyphraseEnrollmentInfo mKeyphraseEnrollmentInfo;
    private KeyphraseMetadata mKeyphraseMetadata;
    private final Locale mLocale;
    private final IVoiceInteractionManagerService mModelManagementService;
    private IVoiceInteractionSoundTriggerSession mSoundTriggerSession;
    private final boolean mSupportSandboxedDetectionService;
    private final String mText;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioCapabilities {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModelParams {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RecognitionFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RecognitionModes {
    }

    @Override // android.service.voice.AbstractDetector, android.service.voice.HotwordDetector
    public /* bridge */ /* synthetic */ boolean startRecognition(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, PersistableBundle persistableBundle) {
        return super.startRecognition(parcelFileDescriptor, audioFormat, persistableBundle);
    }

    public static final class ModelParamRange {
        private final SoundTrigger.ModelParamRange mModelParamRange;

        ModelParamRange(SoundTrigger.ModelParamRange modelParamRange) {
            this.mModelParamRange = modelParamRange;
        }

        public int getStart() {
            return this.mModelParamRange.getStart();
        }

        public int getEnd() {
            return this.mModelParamRange.getEnd();
        }

        public String toString() {
            return this.mModelParamRange.toString();
        }

        public boolean equals(Object obj) {
            return this.mModelParamRange.equals(obj);
        }

        public int hashCode() {
            return this.mModelParamRange.hashCode();
        }
    }

    public static class EventPayload {
        public static final int DATA_FORMAT_RAW = 0;
        public static final int DATA_FORMAT_TRIGGER_AUDIO = 1;
        private final AudioFormat mAudioFormat;
        private final ParcelFileDescriptor mAudioStream;
        private final boolean mCaptureAvailable;
        private final int mCaptureSession;
        private final byte[] mData;
        private final int mDataFormat;
        private final long mHalEventReceivedMillis;
        private final HotwordDetectedResult mHotwordDetectedResult;
        private final boolean mIsRecognitionStopped;
        private final List<SoundTrigger.KeyphraseRecognitionExtra> mKephraseExtras;

        @Retention(RetentionPolicy.SOURCE)
        public @interface DataFormat {
        }

        private EventPayload(boolean captureAvailable, AudioFormat audioFormat, int captureSession, int dataFormat, byte[] data, HotwordDetectedResult hotwordDetectedResult, ParcelFileDescriptor audioStream, List<SoundTrigger.KeyphraseRecognitionExtra> keyphraseExtras, long halEventReceivedMillis, boolean isRecognitionStopped) {
            this.mCaptureAvailable = captureAvailable;
            this.mCaptureSession = captureSession;
            this.mAudioFormat = audioFormat;
            this.mDataFormat = dataFormat;
            this.mData = data;
            this.mHotwordDetectedResult = hotwordDetectedResult;
            this.mAudioStream = audioStream;
            this.mKephraseExtras = keyphraseExtras;
            this.mHalEventReceivedMillis = halEventReceivedMillis;
            this.mIsRecognitionStopped = isRecognitionStopped;
        }

        public AudioFormat getCaptureAudioFormat() {
            return this.mAudioFormat;
        }

        @Deprecated
        public byte[] getTriggerAudio() {
            if (this.mDataFormat == 1) {
                return this.mData;
            }
            return null;
        }

        public int getDataFormat() {
            return this.mDataFormat;
        }

        public byte[] getData() {
            return this.mData;
        }

        public Integer getCaptureSession() {
            if (this.mCaptureAvailable) {
                return Integer.valueOf(this.mCaptureSession);
            }
            return null;
        }

        public HotwordDetectedResult getHotwordDetectedResult() {
            return this.mHotwordDetectedResult;
        }

        public ParcelFileDescriptor getAudioStream() {
            return this.mAudioStream;
        }

        public List<SoundTrigger.KeyphraseRecognitionExtra> getKeyphraseRecognitionExtras() {
            return this.mKephraseExtras;
        }

        public long getHalEventReceivedMillis() {
            return this.mHalEventReceivedMillis;
        }

        public boolean isRecognitionStopped() {
            return this.mIsRecognitionStopped;
        }

        public static final class Builder {
            private boolean mCaptureAvailable = false;
            private int mCaptureSession = -1;
            private AudioFormat mAudioFormat = null;
            private int mDataFormat = 0;
            private byte[] mData = null;
            private HotwordDetectedResult mHotwordDetectedResult = null;
            private ParcelFileDescriptor mAudioStream = null;
            private List<SoundTrigger.KeyphraseRecognitionExtra> mKeyphraseExtras = Collections.emptyList();
            private long mHalEventReceivedMillis = -1;
            private boolean mIsRecognitionStopped = true;

            public Builder() {
            }

            Builder(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
                setCaptureAvailable(keyphraseRecognitionEvent.isCaptureAvailable());
                setCaptureSession(keyphraseRecognitionEvent.getCaptureSession());
                if (keyphraseRecognitionEvent.getCaptureFormat() != null) {
                    setCaptureAudioFormat(keyphraseRecognitionEvent.getCaptureFormat());
                }
                setDataFormat(keyphraseRecognitionEvent.triggerInData ? 1 : 0);
                if (keyphraseRecognitionEvent.getData() != null) {
                    setData(keyphraseRecognitionEvent.getData());
                }
                if (keyphraseRecognitionEvent.keyphraseExtras != null) {
                    setKeyphraseRecognitionExtras(Arrays.asList(keyphraseRecognitionEvent.keyphraseExtras));
                }
                setHalEventReceivedMillis(keyphraseRecognitionEvent.getHalEventReceivedMillis());
            }

            public Builder setCaptureAvailable(boolean captureAvailable) {
                this.mCaptureAvailable = captureAvailable;
                return this;
            }

            public Builder setCaptureSession(int captureSession) {
                this.mCaptureSession = captureSession;
                return this;
            }

            public Builder setCaptureAudioFormat(AudioFormat audioFormat) {
                this.mAudioFormat = audioFormat;
                return this;
            }

            public Builder setDataFormat(int dataFormat) {
                this.mDataFormat = dataFormat;
                return this;
            }

            public Builder setData(byte[] data) {
                this.mData = data;
                return this;
            }

            public Builder setHotwordDetectedResult(HotwordDetectedResult hotwordDetectedResult) {
                this.mHotwordDetectedResult = hotwordDetectedResult;
                return this;
            }

            public Builder setAudioStream(ParcelFileDescriptor audioStream) {
                this.mAudioStream = audioStream;
                return this;
            }

            public Builder setKeyphraseRecognitionExtras(List<SoundTrigger.KeyphraseRecognitionExtra> keyphraseRecognitionExtras) {
                this.mKeyphraseExtras = keyphraseRecognitionExtras;
                return this;
            }

            public Builder setHalEventReceivedMillis(long halEventReceivedMillis) {
                this.mHalEventReceivedMillis = halEventReceivedMillis;
                return this;
            }

            public Builder setIsRecognitionStopped(boolean isRecognitionStopped) {
                this.mIsRecognitionStopped = isRecognitionStopped;
                return this;
            }

            public EventPayload build() {
                return new EventPayload(this.mCaptureAvailable, this.mAudioFormat, this.mCaptureSession, this.mDataFormat, this.mData, this.mHotwordDetectedResult, this.mAudioStream, this.mKeyphraseExtras, this.mHalEventReceivedMillis, this.mIsRecognitionStopped);
            }
        }
    }

    public static abstract class Callback implements HotwordDetector.Callback {
        public abstract void onAvailabilityChanged(int i);

        @Override // android.service.voice.HotwordDetector.Callback
        public abstract void onDetected(EventPayload eventPayload);

        @Override // android.service.voice.HotwordDetector.Callback
        @Deprecated
        public abstract void onError();

        @Override // android.service.voice.HotwordDetector.Callback
        public abstract void onRecognitionPaused();

        @Override // android.service.voice.HotwordDetector.Callback
        public abstract void onRecognitionResumed();

        public void onFailure(SoundTriggerFailure soundTriggerFailure) {
            onError();
        }

        @Override // android.service.voice.HotwordDetector.Callback
        public void onRejected(HotwordRejectedResult result) {
        }

        @Override // android.service.voice.HotwordDetector.Callback
        public void onHotwordDetectionServiceInitialized(int status) {
        }

        @Override // android.service.voice.HotwordDetector.Callback
        public void onHotwordDetectionServiceRestarted() {
        }
    }

    public AlwaysOnHotwordDetector(String text, Locale locale, Executor executor, Callback callback, KeyphraseEnrollmentInfo keyphraseEnrollmentInfo, IVoiceInteractionManagerService modelManagementService, int targetSdkVersion, boolean supportSandboxedDetectionService, String attributionTag) {
        super(modelManagementService, executor, callback);
        this.mBinder = new Binder();
        this.mIsAvailabilityOverriddenByTestApi = false;
        this.mAvailability = 0;
        this.mHandler = new MyHandler(Looper.getMainLooper());
        this.mText = text;
        this.mLocale = locale;
        this.mKeyphraseEnrollmentInfo = keyphraseEnrollmentInfo;
        this.mExternalCallback = callback;
        this.mExternalExecutor = executor != null ? executor : new HandlerExecutor(new Handler(Looper.myLooper()));
        this.mInternalCallback = new SoundTriggerListener(this.mHandler);
        this.mModelManagementService = modelManagementService;
        this.mSupportSandboxedDetectionService = supportSandboxedDetectionService;
        this.mAttributionTag = attributionTag;
    }

    @Override // android.service.voice.AbstractDetector
    void initialize(PersistableBundle options, SharedMemory sharedMemory) {
    }

    void initialize(PersistableBundle options, SharedMemory sharedMemory, SoundTrigger.ModuleProperties moduleProperties) {
        if (this.mSupportSandboxedDetectionService) {
            initAndVerifyDetector(options, sharedMemory, this.mInternalCallback, 1, this.mAttributionTag);
        }
        try {
            Identity identity = new Identity();
            identity.packageName = ActivityThread.currentOpPackageName();
            if (moduleProperties == null) {
                moduleProperties = this.mModelManagementService.listModuleProperties(identity).stream().filter(new Predicate() { // from class: android.service.voice.AlwaysOnHotwordDetector$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return AlwaysOnHotwordDetector.lambda$initialize$0((SoundTrigger.ModuleProperties) obj);
                    }
                }).findFirst().orElse(null);
                if (CompatChanges.isChangeEnabled(THROW_ON_INITIALIZE_IF_NO_DSP) && moduleProperties == null) {
                    throw new IllegalStateException("No DSP module available to attach to");
                }
            }
            this.mSoundTriggerSession = this.mModelManagementService.createSoundTriggerSessionAsOriginator(identity, this.mBinder, moduleProperties);
            new RefreshAvailabilityTask().execute(new Void[0]);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    static /* synthetic */ boolean lambda$initialize$0(SoundTrigger.ModuleProperties prop) {
        return !prop.getSupportedModelArch().equals("injection");
    }

    @Override // android.service.voice.AbstractDetector, android.service.voice.HotwordDetector
    public final void updateState(PersistableBundle options, SharedMemory sharedMemory) {
        synchronized (this.mLock) {
            if (!this.mSupportSandboxedDetectionService) {
                throw new IllegalStateException("updateState called, but it doesn't support hotword detection service");
            }
            if (this.mAvailability == -3 || this.mAvailability == 3) {
                throw new IllegalStateException("updateState called on an invalid detector or error state");
            }
        }
        super.updateState(options, sharedMemory);
    }

    public void overrideAvailability(int availability) {
        synchronized (this.mLock) {
            this.mAvailability = availability;
            this.mIsAvailabilityOverriddenByTestApi = true;
            if (this.mKeyphraseMetadata == null && this.mAvailability == 2) {
                Set<Locale> fakeSupportedLocales = new HashSet<>();
                fakeSupportedLocales.add(this.mLocale);
                this.mKeyphraseMetadata = new KeyphraseMetadata(1, this.mText, fakeSupportedLocales, 1);
            }
            notifyStateChangedLocked();
        }
    }

    public void resetAvailability() {
        synchronized (this.mLock) {
            this.mIsAvailabilityOverriddenByTestApi = false;
        }
        new RefreshAvailabilityTask().execute(new Void[0]);
    }

    public void triggerHardwareRecognitionEventForTest(int status, int soundModelHandle, long halEventReceivedMillis, boolean captureAvailable, int captureSession, int captureDelayMs, int capturePreambleMs, boolean triggerInData, AudioFormat captureFormat, byte[] data, List<SoundTrigger.KeyphraseRecognitionExtra> keyphraseRecognitionExtras) {
        Log.d(TAG, "triggerHardwareRecognitionEventForTest()");
        synchronized (this.mLock) {
            if (this.mAvailability == -3 || this.mAvailability == 3) {
                throw new IllegalStateException("triggerHardwareRecognitionEventForTest called on an invalid detector or error state");
            }
            try {
                this.mModelManagementService.triggerHardwareRecognitionEventForTest(new SoundTrigger.KeyphraseRecognitionEvent(status, soundModelHandle, captureAvailable, captureSession, captureDelayMs, capturePreambleMs, triggerInData, captureFormat, data, (SoundTrigger.KeyphraseRecognitionExtra[]) keyphraseRecognitionExtras.toArray(new SoundTrigger.KeyphraseRecognitionExtra[0]), halEventReceivedMillis, new Binder()), this.mInternalCallback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getSupportedRecognitionModes() {
        int supportedRecognitionModesLocked;
        synchronized (this.mLock) {
            supportedRecognitionModesLocked = getSupportedRecognitionModesLocked();
        }
        return supportedRecognitionModesLocked;
    }

    private int getSupportedRecognitionModesLocked() {
        if (this.mAvailability == -3 || this.mAvailability == 3) {
            throw new IllegalStateException("getSupportedRecognitionModes called on an invalid detector or error state");
        }
        if (this.mAvailability != 2 || this.mKeyphraseMetadata == null) {
            throw new UnsupportedOperationException("Getting supported recognition modes for the keyphrase is not supported");
        }
        return this.mKeyphraseMetadata.getRecognitionModeFlags();
    }

    public int getSupportedAudioCapabilities() {
        int supportedAudioCapabilitiesLocked;
        synchronized (this.mLock) {
            supportedAudioCapabilitiesLocked = getSupportedAudioCapabilitiesLocked();
        }
        return supportedAudioCapabilitiesLocked;
    }

    private int getSupportedAudioCapabilitiesLocked() {
        try {
            SoundTrigger.ModuleProperties properties = this.mSoundTriggerSession.getDspModuleProperties();
            if (properties != null) {
                return properties.getAudioCapabilities();
            }
            return 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean startRecognition(int recognitionFlags, byte[] data) {
        boolean z;
        synchronized (this.mLock) {
            z = startRecognitionLocked(recognitionFlags, data) == 0;
        }
        return z;
    }

    public boolean startRecognition(int recognitionFlags) {
        boolean z;
        synchronized (this.mLock) {
            z = startRecognitionLocked(recognitionFlags, null) == 0;
        }
        return z;
    }

    @Override // android.service.voice.HotwordDetector
    public boolean startRecognition() {
        return startRecognition(0);
    }

    @Override // android.service.voice.HotwordDetector
    public boolean stopRecognition() {
        boolean z;
        synchronized (this.mLock) {
            if (this.mAvailability == -3 || this.mAvailability == 3) {
                throw new IllegalStateException("stopRecognition called on an invalid detector or error state");
            }
            if (this.mAvailability != 2) {
                throw new UnsupportedOperationException("Recognition for the given keyphrase is not supported");
            }
            z = stopRecognitionLocked() == 0;
        }
        return z;
    }

    public int setParameter(int modelParam, int value) {
        int parameterLocked;
        synchronized (this.mLock) {
            if (this.mAvailability == -3 || this.mAvailability == 3) {
                throw new IllegalStateException("setParameter called on an invalid detector or error state");
            }
            parameterLocked = setParameterLocked(modelParam, value);
        }
        return parameterLocked;
    }

    public int getParameter(int modelParam) {
        int parameterLocked;
        synchronized (this.mLock) {
            if (this.mAvailability == -3 || this.mAvailability == 3) {
                throw new IllegalStateException("getParameter called on an invalid detector or error state");
            }
            parameterLocked = getParameterLocked(modelParam);
        }
        return parameterLocked;
    }

    public ModelParamRange queryParameter(int modelParam) {
        ModelParamRange queryParameterLocked;
        synchronized (this.mLock) {
            if (this.mAvailability == -3 || this.mAvailability == 3) {
                throw new IllegalStateException("queryParameter called on an invalid detector or error state");
            }
            queryParameterLocked = queryParameterLocked(modelParam);
        }
        return queryParameterLocked;
    }

    public Intent createEnrollIntent() {
        Intent manageIntentLocked;
        synchronized (this.mLock) {
            manageIntentLocked = getManageIntentLocked(0);
        }
        return manageIntentLocked;
    }

    public Intent createUnEnrollIntent() {
        Intent manageIntentLocked;
        synchronized (this.mLock) {
            manageIntentLocked = getManageIntentLocked(2);
        }
        return manageIntentLocked;
    }

    public Intent createReEnrollIntent() {
        Intent manageIntentLocked;
        synchronized (this.mLock) {
            manageIntentLocked = getManageIntentLocked(1);
        }
        return manageIntentLocked;
    }

    private Intent getManageIntentLocked(int action) {
        if (this.mAvailability == -3 || this.mAvailability == 3) {
            throw new IllegalStateException("getManageIntent called on an invalid detector or error state");
        }
        if (this.mAvailability != 2 && this.mAvailability != 1) {
            throw new UnsupportedOperationException("Managing the given keyphrase is not supported");
        }
        return this.mKeyphraseEnrollmentInfo.getManageKeyphraseIntent(action, this.mText, this.mLocale);
    }

    @Override // android.service.voice.AbstractDetector, android.service.voice.HotwordDetector
    public void destroy() {
        synchronized (this.mLock) {
            detachSessionLocked();
            this.mAvailability = -3;
            this.mIsAvailabilityOverriddenByTestApi = false;
            notifyStateChangedLocked();
        }
        super.destroy();
    }

    private void detachSessionLocked() {
        try {
            if (this.mSoundTriggerSession != null) {
                this.mSoundTriggerSession.detach();
            }
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Override // android.service.voice.HotwordDetector
    public boolean isUsingSandboxedDetectionService() {
        return this.mSupportSandboxedDetectionService;
    }

    void onSoundModelsChanged() {
        synchronized (this.mLock) {
            if (this.mAvailability != -3 && this.mAvailability != -2 && this.mAvailability != 3) {
                if (this.mIsAvailabilityOverriddenByTestApi) {
                    Slog.w(TAG, "Suppressing system availability update. Availability is overridden by test API.");
                    return;
                }
                if (this.mAvailability == 2) {
                    try {
                        int result = stopRecognitionLocked();
                        if (result == 0) {
                            sendSoundTriggerFailure(new SoundTriggerFailure(0, "stopped recognition because of enrollment update", 4));
                        }
                        Log.w(TAG, "Failed to stop recognition after enrollment update: code=" + result);
                    } catch (Exception e) {
                        Slog.w(TAG, "Failed to stop recognition after enrollment update", e);
                        if (CompatChanges.isChangeEnabled(SEND_ON_FAILURE_FOR_ASYNC_EXCEPTIONS)) {
                            sendSoundTriggerFailure(new SoundTriggerFailure(0, "Failed to stop recognition after enrollment update: " + Log.getStackTraceString(e), 3));
                        } else {
                            updateAndNotifyStateChangedLocked(3);
                        }
                        return;
                    }
                }
                new RefreshAvailabilityTask().execute(new Void[0]);
                return;
            }
            Slog.w(TAG, "Received onSoundModelsChanged for an unsupported keyphrase/config or in the error state");
        }
    }

    private int startRecognitionLocked(int recognitionFlags, byte[] data) {
        int audioCapabilities;
        if (this.mAvailability == -3 || this.mAvailability == 3) {
            throw new IllegalStateException("startRecognition called on an invalid detector or error state");
        }
        if (this.mAvailability != 2) {
            throw new UnsupportedOperationException("Recognition for the given keyphrase is not supported");
        }
        SoundTrigger.KeyphraseRecognitionExtra[] recognitionExtra = {new SoundTrigger.KeyphraseRecognitionExtra(this.mKeyphraseMetadata.getId(), this.mKeyphraseMetadata.getRecognitionModeFlags(), 0, new SoundTrigger.ConfidenceLevel[0])};
        boolean captureTriggerAudio = (recognitionFlags & 1) != 0;
        boolean allowMultipleTriggers = (recognitionFlags & 2) != 0;
        boolean runInBatterySaver = (recognitionFlags & 16) != 0;
        int audioCapabilities2 = 0;
        if ((recognitionFlags & 4) != 0) {
            audioCapabilities2 = 0 | 1;
        }
        if ((recognitionFlags & 8) == 0) {
            audioCapabilities = audioCapabilities2;
        } else {
            audioCapabilities = audioCapabilities2 | 2;
        }
        try {
            int code = this.mSoundTriggerSession.startRecognition(this.mKeyphraseMetadata.getId(), this.mLocale.toLanguageTag(), this.mInternalCallback, new SoundTrigger.RecognitionConfig(captureTriggerAudio, allowMultipleTriggers, recognitionExtra, data, audioCapabilities), runInBatterySaver);
            if (code != 0) {
                Slog.w(TAG, "startRecognition() failed with error code " + code);
            }
            return code;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int stopRecognitionLocked() {
        try {
            int code = this.mSoundTriggerSession.stopRecognition(this.mKeyphraseMetadata.getId(), this.mInternalCallback);
            if (code != 0) {
                Slog.w(TAG, "stopRecognition() failed with error code " + code);
            }
            return code;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int setParameterLocked(int modelParam, int value) {
        try {
            int code = this.mSoundTriggerSession.setParameter(this.mKeyphraseMetadata.getId(), modelParam, value);
            if (code != 0) {
                Slog.w(TAG, "setParameter failed with error code " + code);
            }
            return code;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int getParameterLocked(int modelParam) {
        try {
            return this.mSoundTriggerSession.getParameter(this.mKeyphraseMetadata.getId(), modelParam);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private ModelParamRange queryParameterLocked(int modelParam) {
        try {
            SoundTrigger.ModelParamRange modelParamRange = this.mSoundTriggerSession.queryParameter(this.mKeyphraseMetadata.getId(), modelParam);
            if (modelParamRange == null) {
                return null;
            }
            return new ModelParamRange(modelParamRange);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAndNotifyStateChangedLocked(int availability) {
        updateAvailabilityLocked(availability);
        notifyStateChangedLocked();
    }

    private void updateAvailabilityLocked(int availability) {
        if (!this.mIsAvailabilityOverriddenByTestApi) {
            this.mAvailability = availability;
        }
    }

    private void notifyStateChangedLocked() {
        Message message = Message.obtain(this.mHandler, 1);
        message.arg1 = this.mAvailability;
        message.sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUnknownFailure(String failureMessage) {
        updateAvailabilityLocked(3);
        Message.obtain(this.mHandler, 11, failureMessage).sendToTarget();
    }

    private void sendSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure) {
        Message.obtain(this.mHandler, 10, soundTriggerFailure).sendToTarget();
    }

    static final class SoundTriggerListener extends IHotwordRecognitionStatusCallback.Stub {
        private final Handler mHandler;

        public SoundTriggerListener(Handler handler) {
            this.mHandler = handler;
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent event, HotwordDetectedResult result) {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onDetected");
            Message.obtain(this.mHandler, 2, new EventPayload.Builder(event).setHotwordDetectedResult(result).build()).sendToTarget();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetectedFromExternalSource(HotwordDetectedResult result) {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onKeyphraseDetectedFromExternalSource");
            EventPayload.Builder eventPayloadBuilder = new EventPayload.Builder();
            if (Flags.enableHotwordWearableSensingApi()) {
                eventPayloadBuilder.setIsRecognitionStopped(false);
            }
            Message.obtain(this.mHandler, 2, eventPayloadBuilder.setHotwordDetectedResult(result).build()).sendToTarget();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent event) {
            Slog.w(AlwaysOnHotwordDetector.TAG, "Generic sound trigger event detected at AOHD: " + event);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRejected(HotwordRejectedResult result) {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onRejected");
            Message.obtain(this.mHandler, 6, result).sendToTarget();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onHotwordDetectionServiceFailure(HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
            Slog.v(AlwaysOnHotwordDetector.TAG, "onHotwordDetectionServiceFailure: " + hotwordDetectionServiceFailure);
            if (hotwordDetectionServiceFailure != null) {
                Message.obtain(this.mHandler, 9, hotwordDetectionServiceFailure).sendToTarget();
            } else {
                Message.obtain(this.mHandler, 11, "Error data is null").sendToTarget();
            }
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onVisualQueryDetectionServiceFailure(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws RemoteException {
            Slog.w(AlwaysOnHotwordDetector.TAG, "onVisualQueryDetectionServiceFailure: " + visualQueryDetectionServiceFailure);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure) {
            Message.obtain(this.mHandler, 10, Objects.requireNonNull(soundTriggerFailure)).sendToTarget();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onUnknownFailure(String errorMessage) throws RemoteException {
            Slog.v(AlwaysOnHotwordDetector.TAG, "onUnknownFailure: " + errorMessage);
            Message.obtain(this.mHandler, 11, !TextUtils.isEmpty(errorMessage) ? errorMessage : "Error data is null").sendToTarget();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionPaused() {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onRecognitionPaused");
            this.mHandler.sendEmptyMessage(4);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionResumed() {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onRecognitionResumed");
            this.mHandler.sendEmptyMessage(5);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onStatusReported(int status) {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onStatusReported");
            Message message = Message.obtain(this.mHandler, 7);
            message.arg1 = status;
            message.sendToTarget();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onProcessRestarted() {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onProcessRestarted");
            this.mHandler.sendEmptyMessage(8);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onOpenFile(String filename, AndroidFuture future) throws RemoteException {
            throw new UnsupportedOperationException("Hotword cannot access files from the disk.");
        }
    }

    void onDetectorRemoteException() {
        Message.obtain(this.mHandler, 9, new HotwordDetectionServiceFailure(7, "Detector remote exception occurs")).sendToTarget();
    }

    class MyHandler extends Handler {
        MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            synchronized (AlwaysOnHotwordDetector.this.mLock) {
                if (AlwaysOnHotwordDetector.this.mAvailability == -3) {
                    Slog.w(AlwaysOnHotwordDetector.TAG, "Received message: " + msg.what + " for an invalid detector");
                } else {
                    final Message message = Message.obtain(msg);
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.AlwaysOnHotwordDetector$MyHandler$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            AlwaysOnHotwordDetector.MyHandler.this.lambda$handleMessage$1(message);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$1(final Message message) throws Exception {
            AlwaysOnHotwordDetector.this.mExternalExecutor.execute(new Runnable() { // from class: android.service.voice.AlwaysOnHotwordDetector$MyHandler$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AlwaysOnHotwordDetector.MyHandler.this.lambda$handleMessage$0(message);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(Message message) {
            Slog.i(AlwaysOnHotwordDetector.TAG, "handle message " + message.what);
            switch (message.what) {
                case 1:
                    AlwaysOnHotwordDetector.this.mExternalCallback.onAvailabilityChanged(message.arg1);
                    break;
                case 2:
                    AlwaysOnHotwordDetector.this.mExternalCallback.onDetected((EventPayload) message.obj);
                    break;
                case 3:
                    AlwaysOnHotwordDetector.this.mExternalCallback.onError();
                    break;
                case 4:
                    AlwaysOnHotwordDetector.this.mExternalCallback.onRecognitionPaused();
                    break;
                case 5:
                    AlwaysOnHotwordDetector.this.mExternalCallback.onRecognitionResumed();
                    break;
                case 6:
                    AlwaysOnHotwordDetector.this.mExternalCallback.onRejected((HotwordRejectedResult) message.obj);
                    break;
                case 7:
                    AlwaysOnHotwordDetector.this.mExternalCallback.onHotwordDetectionServiceInitialized(message.arg1);
                    break;
                case 8:
                    AlwaysOnHotwordDetector.this.mExternalCallback.onHotwordDetectionServiceRestarted();
                    break;
                case 9:
                    AlwaysOnHotwordDetector.this.mExternalCallback.onFailure((HotwordDetectionServiceFailure) message.obj);
                    break;
                case 10:
                    AlwaysOnHotwordDetector.this.mExternalCallback.onFailure((SoundTriggerFailure) message.obj);
                    break;
                case 11:
                    AlwaysOnHotwordDetector.this.mExternalCallback.onUnknownFailure((String) message.obj);
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
            message.recycle();
        }
    }

    class RefreshAvailabilityTask extends AsyncTask<Void, Void, Void> {
        RefreshAvailabilityTask() {
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Void... params) {
            try {
                int availability = internalGetInitialAvailability();
                synchronized (AlwaysOnHotwordDetector.this.mLock) {
                    if (availability == 0) {
                        internalUpdateEnrolledKeyphraseMetadata();
                        if (AlwaysOnHotwordDetector.this.mKeyphraseMetadata != null) {
                            availability = 2;
                        } else {
                            availability = 1;
                        }
                    }
                    AlwaysOnHotwordDetector.this.updateAndNotifyStateChangedLocked(availability);
                }
                return null;
            } catch (Exception e) {
                Slog.w(AlwaysOnHotwordDetector.TAG, "Failed to refresh availability", e);
                synchronized (AlwaysOnHotwordDetector.this.mLock) {
                    if (CompatChanges.isChangeEnabled(AlwaysOnHotwordDetector.SEND_ON_FAILURE_FOR_ASYNC_EXCEPTIONS)) {
                        AlwaysOnHotwordDetector.this.sendUnknownFailure("Failed to refresh availability: " + Log.getStackTraceString(e));
                    } else {
                        AlwaysOnHotwordDetector.this.updateAndNotifyStateChangedLocked(3);
                    }
                    return null;
                }
            }
        }

        private int internalGetInitialAvailability() {
            synchronized (AlwaysOnHotwordDetector.this.mLock) {
                if (AlwaysOnHotwordDetector.this.mAvailability == -3) {
                    return -3;
                }
                if (!CompatChanges.isChangeEnabled(AlwaysOnHotwordDetector.THROW_ON_INITIALIZE_IF_NO_DSP)) {
                    try {
                        SoundTrigger.ModuleProperties dspModuleProperties = AlwaysOnHotwordDetector.this.mSoundTriggerSession.getDspModuleProperties();
                        if (dspModuleProperties == null) {
                            return -2;
                        }
                        return 0;
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
                return 0;
            }
        }

        private void internalUpdateEnrolledKeyphraseMetadata() {
            try {
                AlwaysOnHotwordDetector.this.mKeyphraseMetadata = AlwaysOnHotwordDetector.this.mModelManagementService.getEnrolledKeyphraseMetadata(AlwaysOnHotwordDetector.this.mText, AlwaysOnHotwordDetector.this.mLocale.toLanguageTag());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean equals(Object obj) {
        if (CompatChanges.isChangeEnabled(193232191L)) {
            if (!(obj instanceof AlwaysOnHotwordDetector)) {
                return false;
            }
            AlwaysOnHotwordDetector other = (AlwaysOnHotwordDetector) obj;
            return TextUtils.equals(this.mText, other.mText) && this.mLocale.equals(other.mLocale);
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return Objects.hash(this.mText, this.mLocale);
    }

    @Override // android.service.voice.HotwordDetector
    public void dump(String prefix, PrintWriter pw) {
        synchronized (this.mLock) {
            pw.print(prefix);
            pw.print("Text=");
            pw.println(this.mText);
            pw.print(prefix);
            pw.print("Locale=");
            pw.println(this.mLocale);
            pw.print(prefix);
            pw.print("Availability=");
            pw.println(this.mAvailability);
            pw.print(prefix);
            pw.print("KeyphraseMetadata=");
            pw.println(this.mKeyphraseMetadata);
            pw.print(prefix);
            pw.print("EnrollmentInfo=");
            pw.println(this.mKeyphraseEnrollmentInfo);
        }
    }
}
