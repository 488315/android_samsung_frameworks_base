package android.speech.tts;

import android.companion.virtual.VirtualDeviceManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.hardware.usb.UsbManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.media.audio.Enums;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.speech.tts.ITextToSpeechCallback;
import android.speech.tts.ITextToSpeechManager;
import android.speech.tts.ITextToSpeechService;
import android.speech.tts.ITextToSpeechSessionCallback;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class TextToSpeech {
    public static final String ACTION_TTS_QUEUE_PROCESSING_COMPLETED = "android.speech.tts.TTS_QUEUE_PROCESSING_COMPLETED";
    private static final boolean DEBUG = false;
    public static final int ERROR = -1;
    public static final int ERROR_INVALID_REQUEST = -8;
    public static final int ERROR_NETWORK = -6;
    public static final int ERROR_NETWORK_TIMEOUT = -7;
    public static final int ERROR_NOT_INSTALLED_YET = -9;
    public static final int ERROR_OUTPUT = -5;
    public static final int ERROR_SERVICE = -4;
    public static final int ERROR_SYNTHESIS = -3;
    public static final int LANG_AVAILABLE = 0;
    public static final int LANG_COUNTRY_AVAILABLE = 1;
    public static final int LANG_COUNTRY_VAR_AVAILABLE = 2;
    public static final int LANG_MISSING_DATA = -1;
    public static final int LANG_NOT_SUPPORTED = -2;
    private static final String PRIVATE_ENGINE = "com.samsung.SMT";
    public static final int QUEUE_ADD = 1;
    static final int QUEUE_DESTROY = 2;
    public static final int QUEUE_FLUSH = 0;
    public static final int STOPPED = -2;
    public static final int SUCCESS = 0;
    private static final String TAG = "TextToSpeech";
    private Connection mConnectingServiceConnection;
    private final Context mContext;
    private volatile String mCurrentEngine;
    private final Map<String, Uri> mEarcons;
    private final TtsEngines mEnginesHelper;
    private final Executor mInitExecutor;
    private OnInitListener mInitListener;
    private final boolean mIsSystem;
    private final Bundle mParams;
    private String mRequestedEngine;
    private Connection mServiceConnection;
    private final Object mStartLock;
    private final boolean mUseFallback;
    private volatile UtteranceProgressListener mUtteranceProgressListener;
    private final Map<CharSequence, Uri> mUtterances;

    /* JADX INFO: Access modifiers changed from: private */
    interface Action<R> {
        R run(ITextToSpeechService iTextToSpeechService) throws RemoteException;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Error {
    }

    public interface OnInitListener {
        void onInit(int i);
    }

    @Deprecated
    public interface OnUtteranceCompletedListener {
        void onUtteranceCompleted(String str);
    }

    public static int getMaxSpeechInputLength() {
        return 4000;
    }

    @Deprecated
    public boolean areDefaultsEnforced() {
        return false;
    }

    public class Engine {
        public static final String ACTION_CHECK_TTS_DATA = "android.speech.tts.engine.CHECK_TTS_DATA";
        public static final String ACTION_GET_SAMPLE_TEXT = "android.speech.tts.engine.GET_SAMPLE_TEXT";
        public static final String ACTION_INSTALL_TTS_DATA = "android.speech.tts.engine.INSTALL_TTS_DATA";
        public static final String ACTION_TTS_DATA_INSTALLED = "android.speech.tts.engine.TTS_DATA_INSTALLED";

        @Deprecated
        public static final int CHECK_VOICE_DATA_BAD_DATA = -1;
        public static final int CHECK_VOICE_DATA_FAIL = 0;

        @Deprecated
        public static final int CHECK_VOICE_DATA_MISSING_DATA = -2;

        @Deprecated
        public static final int CHECK_VOICE_DATA_MISSING_VOLUME = -3;
        public static final int CHECK_VOICE_DATA_PASS = 1;

        @Deprecated
        public static final String DEFAULT_ENGINE = "com.svox.pico";
        public static final float DEFAULT_PAN = 0.0f;
        public static final int DEFAULT_PITCH = 100;
        public static final int DEFAULT_RATE = 100;
        public static final int DEFAULT_STREAM = 3;
        public static final float DEFAULT_VOLUME = 1.0f;
        public static final String EXTRA_AVAILABLE_VOICES = "availableVoices";

        @Deprecated
        public static final String EXTRA_CHECK_VOICE_DATA_FOR = "checkVoiceDataFor";
        public static final String EXTRA_SAMPLE_TEXT = "sampleText";

        @Deprecated
        public static final String EXTRA_TTS_DATA_INSTALLED = "dataInstalled";
        public static final String EXTRA_UNAVAILABLE_VOICES = "unavailableVoices";

        @Deprecated
        public static final String EXTRA_VOICE_DATA_FILES = "dataFiles";

        @Deprecated
        public static final String EXTRA_VOICE_DATA_FILES_INFO = "dataFilesInfo";

        @Deprecated
        public static final String EXTRA_VOICE_DATA_ROOT_DIRECTORY = "dataRoot";
        public static final String INTENT_ACTION_TTS_SERVICE = "android.intent.action.TTS_SERVICE";

        @Deprecated
        public static final String KEY_FEATURE_EMBEDDED_SYNTHESIS = "embeddedTts";
        public static final String KEY_FEATURE_NETWORK_RETRIES_COUNT = "networkRetriesCount";

        @Deprecated
        public static final String KEY_FEATURE_NETWORK_SYNTHESIS = "networkTts";
        public static final String KEY_FEATURE_NETWORK_TIMEOUT_MS = "networkTimeoutMs";
        public static final String KEY_FEATURE_NOT_INSTALLED = "notInstalled";
        public static final String KEY_PARAM_AUDIO_ATTRIBUTES = "audioAttributes";
        public static final String KEY_PARAM_COUNTRY = "country";
        public static final String KEY_PARAM_ENGINE = "engine";
        public static final String KEY_PARAM_LANGUAGE = "language";
        public static final String KEY_PARAM_PAN = "pan";
        public static final String KEY_PARAM_PITCH = "pitch";
        public static final String KEY_PARAM_RATE = "rate";
        public static final String KEY_PARAM_SESSION_ID = "sessionId";
        public static final String KEY_PARAM_STREAM = "streamType";
        public static final String KEY_PARAM_UTTERANCE_ID = "utteranceId";
        public static final String KEY_PARAM_VARIANT = "variant";
        public static final String KEY_PARAM_VOICE_NAME = "voiceName";
        public static final String KEY_PARAM_VOLUME = "volume";
        public static final String SERVICE_META_DATA = "android.speech.tts";
        public static final int USE_DEFAULTS = 0;

        public Engine(TextToSpeech textToSpeech) {
        }
    }

    public TextToSpeech(Context context, OnInitListener onInitListener) {
        this(context, onInitListener, null);
    }

    public TextToSpeech(Context context, OnInitListener onInitListener, String str) {
        this(context, onInitListener, str, null, true);
    }

    public TextToSpeech(Context context, OnInitListener onInitListener, String str, String str2, boolean z) {
        this(context, null, onInitListener, str, str2, z, true);
    }

    private TextToSpeech(Context context, Executor executor, OnInitListener onInitListener, String str, String str2, boolean z, boolean z2) {
        this.mStartLock = new Object();
        Bundle bundle = new Bundle();
        this.mParams = bundle;
        this.mCurrentEngine = null;
        this.mContext = context;
        this.mInitExecutor = executor;
        this.mInitListener = onInitListener;
        this.mRequestedEngine = str;
        this.mUseFallback = z;
        this.mEarcons = new HashMap();
        this.mUtterances = new HashMap();
        this.mUtteranceProgressListener = null;
        this.mEnginesHelper = new TtsEngines(context);
        this.mIsSystem = z2;
        StringBuilder sb = new StringBuilder("Create TextToSpeech : caller process id[");
        sb.append(Process.myPid());
        sb.append("] / name[");
        sb.append(Process.myProcessName());
        sb.append("] / target[");
        String str3 = this.mRequestedEngine;
        sb.append(str3 == null ? PerfettoProtoLogImpl.NULL_STRING : str3);
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        Log.i(TAG, sb.toString());
        addDeviceSpecificSessionIdToParams(context, bundle);
        initTts();
    }

    private static void addDeviceSpecificSessionIdToParams(Context context, Bundle bundle) {
        int deviceSpecificPlaybackSessionId = getDeviceSpecificPlaybackSessionId(context);
        if (deviceSpecificPlaybackSessionId != 0) {
            bundle.putInt("sessionId", deviceSpecificPlaybackSessionId);
        }
    }

    private static int getDeviceSpecificPlaybackSessionId(Context context) {
        VirtualDeviceManager virtualDeviceManager;
        int deviceId = context.getDeviceId();
        if (deviceId == 0 || (virtualDeviceManager = (VirtualDeviceManager) context.getSystemService(VirtualDeviceManager.class)) == null) {
            return 0;
        }
        return virtualDeviceManager.getAudioPlaybackSessionId(deviceId);
    }

    private <R> R runActionNoReconnect(Action<R> action, R r, String str, boolean z) {
        return (R) runAction(action, r, str, false, z);
    }

    private <R> R runAction(Action<R> action, R r, String str) {
        return (R) runAction(action, r, str, true, true);
    }

    private <R> R runAction(Action<R> action, R r, String str, boolean z, boolean z2) {
        synchronized (this.mStartLock) {
            Connection connection = this.mServiceConnection;
            if (connection == null) {
                Log.w(TAG, str + " failed: not bound to TTS engine");
                return r;
            }
            return (R) connection.runAction(action, r, str, z, z2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int initTts() {
        String str = this.mRequestedEngine;
        if (str == null || TextUtils.isEmpty(str)) {
            this.mRequestedEngine = getDefaultEngine();
        }
        String str2 = this.mRequestedEngine;
        if (str2 == null || TextUtils.isEmpty(str2)) {
            this.mRequestedEngine = this.mEnginesHelper.getHighestRankedEngineName();
        }
        String str3 = this.mRequestedEngine;
        if (str3 != null && str3.equals(PRIVATE_ENGINE)) {
            if (isPrivateEngineAvailable(this.mRequestedEngine)) {
                if (connectToEngine(this.mRequestedEngine)) {
                    this.mCurrentEngine = this.mRequestedEngine;
                    return 0;
                }
                if (!this.mUseFallback) {
                    this.mCurrentEngine = null;
                    dispatchOnInit(-1);
                    return -1;
                }
            } else {
                this.mRequestedEngine = getHighestRankedPublicEngineName();
            }
        }
        String str4 = this.mRequestedEngine;
        if (str4 != null) {
            if (this.mEnginesHelper.isEngineInstalled(str4)) {
                if (connectToEngine(this.mRequestedEngine)) {
                    this.mCurrentEngine = this.mRequestedEngine;
                    return 0;
                }
                if (!this.mUseFallback) {
                    this.mCurrentEngine = null;
                    dispatchOnInit(-1);
                    return -1;
                }
            } else if (!this.mUseFallback) {
                Log.i(TAG, "Requested engine not installed: " + this.mRequestedEngine);
                this.mCurrentEngine = null;
                dispatchOnInit(-1);
                return -1;
            }
        }
        this.mCurrentEngine = null;
        dispatchOnInit(-1);
        return -1;
    }

    private boolean connectToEngine(String str) {
        Connection directConnection;
        if (this.mIsSystem) {
            directConnection = new SystemConnection();
        } else {
            directConnection = new DirectConnection();
        }
        if (!directConnection.connect(str)) {
            Log.e(TAG, "Failed to bind to " + str);
            return false;
        }
        Log.i(TAG, "Sucessfully bound to " + str);
        this.mConnectingServiceConnection = directConnection;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnInit(final int i) {
        Runnable runnable = new Runnable() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                TextToSpeech.this.lambda$dispatchOnInit$0(i);
            }
        };
        Executor executor = this.mInitExecutor;
        if (executor != null) {
            executor.execute(runnable);
        } else {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchOnInit$0(int i) {
        synchronized (this.mStartLock) {
            OnInitListener onInitListener = this.mInitListener;
            if (onInitListener != null) {
                onInitListener.onInit(i);
                this.mInitListener = null;
            }
        }
    }

    private IBinder getCallerIdentity() {
        return this.mServiceConnection.getCallerIdentity();
    }

    public void shutdown() {
        synchronized (this.mStartLock) {
            Connection connection = this.mConnectingServiceConnection;
            if (connection != null) {
                connection.disconnect();
                this.mConnectingServiceConnection = null;
            } else {
                runActionNoReconnect(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda12
                    @Override // android.speech.tts.TextToSpeech.Action
                    public final Object run(ITextToSpeechService iTextToSpeechService) {
                        Object lambda$shutdown$1;
                        lambda$shutdown$1 = TextToSpeech.this.lambda$shutdown$1(iTextToSpeechService);
                        return lambda$shutdown$1;
                    }
                }, null, UsbManager.USB_FUNCTION_SHUTDOWN, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$shutdown$1(ITextToSpeechService iTextToSpeechService) throws RemoteException {
        iTextToSpeechService.setCallback(getCallerIdentity(), null);
        iTextToSpeechService.stop(getCallerIdentity());
        this.mServiceConnection.disconnect();
        this.mServiceConnection = null;
        this.mCurrentEngine = null;
        return null;
    }

    public int addSpeech(String str, String str2, int i) {
        return addSpeech(str, makeResourceUri(str2, i));
    }

    public int addSpeech(CharSequence charSequence, String str, int i) {
        return addSpeech(charSequence, makeResourceUri(str, i));
    }

    public int addSpeech(String str, String str2) {
        return addSpeech(str, Uri.parse(str2));
    }

    public int addSpeech(CharSequence charSequence, File file) {
        return addSpeech(charSequence, Uri.fromFile(file));
    }

    public int addSpeech(CharSequence charSequence, Uri uri) {
        synchronized (this.mStartLock) {
            this.mUtterances.put(charSequence, uri);
        }
        return 0;
    }

    public int addEarcon(String str, String str2, int i) {
        return addEarcon(str, makeResourceUri(str2, i));
    }

    @Deprecated
    public int addEarcon(String str, String str2) {
        return addEarcon(str, Uri.parse(str2));
    }

    public int addEarcon(String str, File file) {
        return addEarcon(str, Uri.fromFile(file));
    }

    public int addEarcon(String str, Uri uri) {
        synchronized (this.mStartLock) {
            this.mEarcons.put(str, uri);
        }
        return 0;
    }

    private Uri makeResourceUri(String str, int i) {
        return new Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE).encodedAuthority(str).appendEncodedPath(String.valueOf(i)).build();
    }

    public int speak(final CharSequence charSequence, final int i, final Bundle bundle, final String str) {
        return ((Integer) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda1
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                Integer lambda$speak$2;
                lambda$speak$2 = TextToSpeech.this.lambda$speak$2(charSequence, i, bundle, str, iTextToSpeechService);
                return lambda$speak$2;
            }
        }, -1, "speak")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$speak$2(CharSequence charSequence, int i, Bundle bundle, String str, ITextToSpeechService iTextToSpeechService) throws RemoteException {
        Uri uri = this.mUtterances.get(charSequence);
        if (uri != null) {
            return Integer.valueOf(iTextToSpeechService.playAudio(getCallerIdentity(), uri, i, getParams(bundle), str));
        }
        return Integer.valueOf(iTextToSpeechService.speak(getCallerIdentity(), charSequence, i, getParams(bundle), str));
    }

    @Deprecated
    public int speak(String str, int i, HashMap<String, String> hashMap) {
        return speak(str, i, convertParamsHashMaptoBundle(hashMap), hashMap == null ? null : hashMap.get(Engine.KEY_PARAM_UTTERANCE_ID));
    }

    public int playEarcon(final String str, final int i, final Bundle bundle, final String str2) {
        return ((Integer) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda10
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                Integer lambda$playEarcon$3;
                lambda$playEarcon$3 = TextToSpeech.this.lambda$playEarcon$3(str, i, bundle, str2, iTextToSpeechService);
                return lambda$playEarcon$3;
            }
        }, -1, "playEarcon")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$playEarcon$3(String str, int i, Bundle bundle, String str2, ITextToSpeechService iTextToSpeechService) throws RemoteException {
        Uri uri = this.mEarcons.get(str);
        if (uri == null) {
            return -1;
        }
        return Integer.valueOf(iTextToSpeechService.playAudio(getCallerIdentity(), uri, i, getParams(bundle), str2));
    }

    @Deprecated
    public int playEarcon(String str, int i, HashMap<String, String> hashMap) {
        return playEarcon(str, i, convertParamsHashMaptoBundle(hashMap), hashMap == null ? null : hashMap.get(Engine.KEY_PARAM_UTTERANCE_ID));
    }

    public int playSilentUtterance(final long j, final int i, final String str) {
        return ((Integer) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda2
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                Integer lambda$playSilentUtterance$4;
                lambda$playSilentUtterance$4 = TextToSpeech.this.lambda$playSilentUtterance$4(j, i, str, iTextToSpeechService);
                return lambda$playSilentUtterance$4;
            }
        }, -1, "playSilentUtterance")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$playSilentUtterance$4(long j, int i, String str, ITextToSpeechService iTextToSpeechService) throws RemoteException {
        return Integer.valueOf(iTextToSpeechService.playSilence(getCallerIdentity(), j, i, str));
    }

    @Deprecated
    public int playSilence(long j, int i, HashMap<String, String> hashMap) {
        return playSilentUtterance(j, i, hashMap == null ? null : hashMap.get(Engine.KEY_PARAM_UTTERANCE_ID));
    }

    @Deprecated
    public Set<String> getFeatures(final Locale locale) {
        return (Set) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda16
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                return TextToSpeech.lambda$getFeatures$5(locale, iTextToSpeechService);
            }
        }, null, "getFeatures");
    }

    static /* synthetic */ Set lambda$getFeatures$5(Locale locale, ITextToSpeechService iTextToSpeechService) throws RemoteException {
        try {
            String[] featuresForLanguage = iTextToSpeechService.getFeaturesForLanguage(locale.getISO3Language(), locale.getISO3Country(), locale.getVariant());
            if (featuresForLanguage == null) {
                return null;
            }
            HashSet hashSet = new HashSet();
            Collections.addAll(hashSet, featuresForLanguage);
            return hashSet;
        } catch (MissingResourceException e) {
            Log.w(TAG, "Couldn't retrieve 3 letter ISO 639-2/T language and/or ISO 3166 country code for locale: " + locale, e);
            return null;
        }
    }

    public boolean isSpeaking() {
        return ((Boolean) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda17
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                Boolean valueOf;
                valueOf = Boolean.valueOf(iTextToSpeechService.isSpeaking());
                return valueOf;
            }
        }, false, "isSpeaking")).booleanValue();
    }

    public int stop() {
        return ((Integer) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda7
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                Integer lambda$stop$7;
                lambda$stop$7 = TextToSpeech.this.lambda$stop$7(iTextToSpeechService);
                return lambda$stop$7;
            }
        }, -1, "stop")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$stop$7(ITextToSpeechService iTextToSpeechService) throws RemoteException {
        return Integer.valueOf(iTextToSpeechService.stop(getCallerIdentity()));
    }

    public int setSpeechRate(float f) {
        int i;
        if (f <= 0.0f || (i = (int) (f * 100.0f)) <= 0) {
            return -1;
        }
        synchronized (this.mStartLock) {
            this.mParams.putInt(Engine.KEY_PARAM_RATE, i);
        }
        return 0;
    }

    public int setPitch(float f) {
        int i;
        if (f <= 0.0f || (i = (int) (f * 100.0f)) <= 0) {
            return -1;
        }
        synchronized (this.mStartLock) {
            this.mParams.putInt(Engine.KEY_PARAM_PITCH, i);
        }
        return 0;
    }

    public int setAudioAttributes(AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            return -1;
        }
        synchronized (this.mStartLock) {
            this.mParams.putParcelable(Engine.KEY_PARAM_AUDIO_ATTRIBUTES, audioAttributes);
        }
        return 0;
    }

    public String getCurrentEngine() {
        return this.mCurrentEngine;
    }

    @Deprecated
    public Locale getDefaultLanguage() {
        return (Locale) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda15
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                return TextToSpeech.lambda$getDefaultLanguage$8(iTextToSpeechService);
            }
        }, null, "getDefaultLanguage");
    }

    static /* synthetic */ Locale lambda$getDefaultLanguage$8(ITextToSpeechService iTextToSpeechService) throws RemoteException {
        String[] clientDefaultLanguage = iTextToSpeechService.getClientDefaultLanguage();
        return new Locale(clientDefaultLanguage[0], clientDefaultLanguage[1], clientDefaultLanguage[2]);
    }

    public int setLanguage(final Locale locale) {
        return ((Integer) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda8
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                Integer lambda$setLanguage$9;
                lambda$setLanguage$9 = TextToSpeech.this.lambda$setLanguage$9(locale, iTextToSpeechService);
                return lambda$setLanguage$9;
            }
        }, -2, "setLanguage")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setLanguage$9(Locale locale, ITextToSpeechService iTextToSpeechService) throws RemoteException {
        String str;
        String str2 = "";
        if (locale == null) {
            return -2;
        }
        try {
            String iSO3Language = locale.getISO3Language();
            try {
                String iSO3Country = locale.getISO3Country();
                String variant = locale.getVariant();
                int isLanguageAvailable = iTextToSpeechService.isLanguageAvailable(iSO3Language, iSO3Country, variant);
                if (isLanguageAvailable >= 0) {
                    String defaultVoiceNameFor = iTextToSpeechService.getDefaultVoiceNameFor(iSO3Language, iSO3Country, variant);
                    if (TextUtils.isEmpty(defaultVoiceNameFor)) {
                        Log.w(TAG, "Couldn't find the default voice for " + iSO3Language + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + iSO3Country + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + variant);
                        return -2;
                    }
                    if (iTextToSpeechService.loadVoice(getCallerIdentity(), defaultVoiceNameFor) == -1) {
                        Log.w(TAG, "The service claimed " + iSO3Language + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + iSO3Country + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + variant + " was available with voice name " + defaultVoiceNameFor + " but loadVoice returned ERROR");
                        return -2;
                    }
                    Voice voice = getVoice(iTextToSpeechService, defaultVoiceNameFor);
                    if (voice == null) {
                        Log.w(TAG, "getDefaultVoiceNameFor returned " + defaultVoiceNameFor + " for locale " + iSO3Language + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + iSO3Country + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + variant + " but getVoice returns null");
                        return -2;
                    }
                    try {
                        str = voice.getLocale().getISO3Language();
                    } catch (MissingResourceException e) {
                        Log.w(TAG, "Couldn't retrieve ISO 639-2/T language code for locale: " + voice.getLocale(), e);
                        str = "";
                    }
                    try {
                        str2 = voice.getLocale().getISO3Country();
                    } catch (MissingResourceException e2) {
                        Log.w(TAG, "Couldn't retrieve ISO 3166 country code for locale: " + voice.getLocale(), e2);
                    }
                    this.mParams.putString(Engine.KEY_PARAM_VOICE_NAME, defaultVoiceNameFor);
                    this.mParams.putString("language", str);
                    this.mParams.putString(Engine.KEY_PARAM_COUNTRY, str2);
                    this.mParams.putString(Engine.KEY_PARAM_VARIANT, voice.getLocale().getVariant());
                }
                return Integer.valueOf(isLanguageAvailable);
            } catch (MissingResourceException e3) {
                Log.w(TAG, "Couldn't retrieve ISO 3166 country code for locale: " + locale, e3);
                return -2;
            }
        } catch (MissingResourceException e4) {
            Log.w(TAG, "Couldn't retrieve ISO 639-2/T language code for locale: " + locale, e4);
            return -2;
        }
    }

    @Deprecated
    public Locale getLanguage() {
        return (Locale) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda6
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                Locale lambda$getLanguage$10;
                lambda$getLanguage$10 = TextToSpeech.this.lambda$getLanguage$10(iTextToSpeechService);
                return lambda$getLanguage$10;
            }
        }, null, "getLanguage");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Locale lambda$getLanguage$10(ITextToSpeechService iTextToSpeechService) throws RemoteException {
        return new Locale(this.mParams.getString("language", ""), this.mParams.getString(Engine.KEY_PARAM_COUNTRY, ""), this.mParams.getString(Engine.KEY_PARAM_VARIANT, ""));
    }

    public Set<Locale> getAvailableLanguages() {
        return (Set) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda3
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                return TextToSpeech.lambda$getAvailableLanguages$11(iTextToSpeechService);
            }
        }, null, "getAvailableLanguages");
    }

    static /* synthetic */ HashSet lambda$getAvailableLanguages$11(ITextToSpeechService iTextToSpeechService) throws RemoteException {
        List<Voice> voices = iTextToSpeechService.getVoices();
        if (voices == null) {
            return new HashSet();
        }
        HashSet hashSet = new HashSet();
        Iterator<Voice> it = voices.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().getLocale());
        }
        return hashSet;
    }

    public Set<Voice> getVoices() {
        return (Set) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda11
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                return TextToSpeech.lambda$getVoices$12(iTextToSpeechService);
            }
        }, null, "getVoices");
    }

    static /* synthetic */ HashSet lambda$getVoices$12(ITextToSpeechService iTextToSpeechService) throws RemoteException {
        List<Voice> voices = iTextToSpeechService.getVoices();
        return voices != null ? new HashSet(voices) : new HashSet();
    }

    public int setVoice(final Voice voice) {
        return ((Integer) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda14
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                Integer lambda$setVoice$13;
                lambda$setVoice$13 = TextToSpeech.this.lambda$setVoice$13(voice, iTextToSpeechService);
                return lambda$setVoice$13;
            }
        }, -2, "setVoice")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setVoice$13(Voice voice, ITextToSpeechService iTextToSpeechService) throws RemoteException {
        String str;
        String str2 = "";
        int loadVoice = iTextToSpeechService.loadVoice(getCallerIdentity(), voice.getName());
        if (loadVoice == 0) {
            this.mParams.putString(Engine.KEY_PARAM_VOICE_NAME, voice.getName());
            try {
                str = voice.getLocale().getISO3Language();
            } catch (MissingResourceException e) {
                Log.w(TAG, "Couldn't retrieve ISO 639-2/T language code for locale: " + voice.getLocale(), e);
                str = "";
            }
            try {
                str2 = voice.getLocale().getISO3Country();
            } catch (MissingResourceException e2) {
                Log.w(TAG, "Couldn't retrieve ISO 3166 country code for locale: " + voice.getLocale(), e2);
            }
            this.mParams.putString("language", str);
            this.mParams.putString(Engine.KEY_PARAM_COUNTRY, str2);
            this.mParams.putString(Engine.KEY_PARAM_VARIANT, voice.getLocale().getVariant());
        }
        return Integer.valueOf(loadVoice);
    }

    public Voice getVoice() {
        return (Voice) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda0
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                Voice lambda$getVoice$14;
                lambda$getVoice$14 = TextToSpeech.this.lambda$getVoice$14(iTextToSpeechService);
                return lambda$getVoice$14;
            }
        }, null, "getVoice");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Voice lambda$getVoice$14(ITextToSpeechService iTextToSpeechService) throws RemoteException {
        String string = this.mParams.getString(Engine.KEY_PARAM_VOICE_NAME, "");
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return getVoice(iTextToSpeechService, string);
    }

    private Voice getVoice(ITextToSpeechService iTextToSpeechService, String str) throws RemoteException {
        List<Voice> voices = iTextToSpeechService.getVoices();
        if (voices == null) {
            Log.w(TAG, "getVoices returned null");
            return null;
        }
        for (Voice voice : voices) {
            if (voice.getName().equals(str)) {
                return voice;
            }
        }
        Log.w(TAG, "Could not find voice " + str + " in voice list");
        return null;
    }

    public Voice getDefaultVoice() {
        return (Voice) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda9
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                return TextToSpeech.lambda$getDefaultVoice$15(iTextToSpeechService);
            }
        }, null, "getDefaultVoice");
    }

    static /* synthetic */ Voice lambda$getDefaultVoice$15(ITextToSpeechService iTextToSpeechService) throws RemoteException {
        List<Voice> voices;
        String[] clientDefaultLanguage = iTextToSpeechService.getClientDefaultLanguage();
        if (clientDefaultLanguage == null || clientDefaultLanguage.length == 0) {
            Log.e(TAG, "service.getClientDefaultLanguage() returned empty array");
            return null;
        }
        String str = clientDefaultLanguage[0];
        String str2 = clientDefaultLanguage.length > 1 ? clientDefaultLanguage[1] : "";
        String str3 = clientDefaultLanguage.length > 2 ? clientDefaultLanguage[2] : "";
        if (iTextToSpeechService.isLanguageAvailable(str, str2, str3) < 0) {
            return null;
        }
        String defaultVoiceNameFor = iTextToSpeechService.getDefaultVoiceNameFor(str, str2, str3);
        if (TextUtils.isEmpty(defaultVoiceNameFor) || (voices = iTextToSpeechService.getVoices()) == null) {
            return null;
        }
        for (Voice voice : voices) {
            if (voice.getName().equals(defaultVoiceNameFor)) {
                return voice;
            }
        }
        return null;
    }

    public int isLanguageAvailable(final Locale locale) {
        return ((Integer) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda4
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                return TextToSpeech.lambda$isLanguageAvailable$16(locale, iTextToSpeechService);
            }
        }, -2, "isLanguageAvailable")).intValue();
    }

    static /* synthetic */ Integer lambda$isLanguageAvailable$16(Locale locale, ITextToSpeechService iTextToSpeechService) throws RemoteException {
        try {
            try {
                return Integer.valueOf(iTextToSpeechService.isLanguageAvailable(locale.getISO3Language(), locale.getISO3Country(), locale.getVariant()));
            } catch (MissingResourceException e) {
                Log.w(TAG, "Couldn't retrieve ISO 3166 country code for locale: " + locale, e);
                return -2;
            }
        } catch (MissingResourceException e2) {
            Log.w(TAG, "Couldn't retrieve ISO 639-2/T language code for locale: " + locale, e2);
            return -2;
        }
    }

    public int synthesizeToFile(final CharSequence charSequence, final Bundle bundle, final ParcelFileDescriptor parcelFileDescriptor, final String str) {
        return ((Integer) runAction(new Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda5
            @Override // android.speech.tts.TextToSpeech.Action
            public final Object run(ITextToSpeechService iTextToSpeechService) {
                Integer lambda$synthesizeToFile$17;
                lambda$synthesizeToFile$17 = TextToSpeech.this.lambda$synthesizeToFile$17(charSequence, parcelFileDescriptor, bundle, str, iTextToSpeechService);
                return lambda$synthesizeToFile$17;
            }
        }, -1, "synthesizeToFile")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$synthesizeToFile$17(CharSequence charSequence, ParcelFileDescriptor parcelFileDescriptor, Bundle bundle, String str, ITextToSpeechService iTextToSpeechService) throws RemoteException {
        return Integer.valueOf(iTextToSpeechService.synthesizeToFileDescriptor(getCallerIdentity(), charSequence, parcelFileDescriptor, getParams(bundle), str));
    }

    public int synthesizeToFile(CharSequence charSequence, Bundle bundle, File file, String str) {
        if (file.exists() && !file.canWrite()) {
            Log.e(TAG, "Can't write to " + file);
            return -1;
        }
        try {
            ParcelFileDescriptor open = ParcelFileDescriptor.open(file, Enums.AUDIO_FORMAT_MPEGH);
            try {
                int synthesizeToFile = synthesizeToFile(charSequence, bundle, open, str);
                open.close();
                if (open != null) {
                    open.close();
                }
                return synthesizeToFile;
            } catch (Throwable th) {
                if (open != null) {
                    try {
                        open.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Opening file " + file + " failed", e);
            return -1;
        } catch (IOException e2) {
            Log.e(TAG, "Closing file " + file + " failed", e2);
            return -1;
        }
    }

    @Deprecated
    public int synthesizeToFile(String str, HashMap<String, String> hashMap, String str2) {
        return synthesizeToFile(str, convertParamsHashMaptoBundle(hashMap), new File(str2), hashMap.get(Engine.KEY_PARAM_UTTERANCE_ID));
    }

    private Bundle convertParamsHashMaptoBundle(HashMap<String, String> hashMap) {
        if (hashMap == null || hashMap.isEmpty()) {
            return null;
        }
        Bundle bundle = new Bundle();
        copyIntParam(bundle, hashMap, Engine.KEY_PARAM_STREAM);
        copyIntParam(bundle, hashMap, "sessionId");
        copyStringParam(bundle, hashMap, Engine.KEY_PARAM_UTTERANCE_ID);
        copyFloatParam(bundle, hashMap, "volume");
        copyFloatParam(bundle, hashMap, Engine.KEY_PARAM_PAN);
        copyStringParam(bundle, hashMap, Engine.KEY_FEATURE_NETWORK_SYNTHESIS);
        copyStringParam(bundle, hashMap, Engine.KEY_FEATURE_EMBEDDED_SYNTHESIS);
        copyIntParam(bundle, hashMap, Engine.KEY_FEATURE_NETWORK_TIMEOUT_MS);
        copyIntParam(bundle, hashMap, Engine.KEY_FEATURE_NETWORK_RETRIES_COUNT);
        if (!TextUtils.isEmpty(this.mCurrentEngine)) {
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                if (key != null && key.startsWith(this.mCurrentEngine)) {
                    bundle.putString(key, entry.getValue());
                }
            }
        }
        return bundle;
    }

    private Bundle getParams(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            Bundle bundle2 = new Bundle(this.mParams);
            bundle2.putAll(bundle);
            verifyIntegerBundleParam(bundle2, Engine.KEY_PARAM_STREAM);
            verifyIntegerBundleParam(bundle2, "sessionId");
            verifyStringBundleParam(bundle2, Engine.KEY_PARAM_UTTERANCE_ID);
            verifyFloatBundleParam(bundle2, "volume");
            verifyFloatBundleParam(bundle2, Engine.KEY_PARAM_PAN);
            verifyBooleanBundleParam(bundle2, Engine.KEY_FEATURE_NETWORK_SYNTHESIS);
            verifyBooleanBundleParam(bundle2, Engine.KEY_FEATURE_EMBEDDED_SYNTHESIS);
            verifyIntegerBundleParam(bundle2, Engine.KEY_FEATURE_NETWORK_TIMEOUT_MS);
            verifyIntegerBundleParam(bundle2, Engine.KEY_FEATURE_NETWORK_RETRIES_COUNT);
            return bundle2;
        }
        return this.mParams;
    }

    private static boolean verifyIntegerBundleParam(Bundle bundle, String str) {
        if (!bundle.containsKey(str) || (bundle.get(str) instanceof Integer) || (bundle.get(str) instanceof Long)) {
            return true;
        }
        bundle.remove(str);
        Log.w(TAG, "Synthesis request paramter " + str + " containst value  with invalid type. Should be an Integer or a Long");
        return false;
    }

    private static boolean verifyStringBundleParam(Bundle bundle, String str) {
        if (!bundle.containsKey(str) || (bundle.get(str) instanceof String)) {
            return true;
        }
        bundle.remove(str);
        Log.w(TAG, "Synthesis request paramter " + str + " containst value  with invalid type. Should be a String");
        return false;
    }

    private static boolean verifyBooleanBundleParam(Bundle bundle, String str) {
        if (!bundle.containsKey(str) || (bundle.get(str) instanceof Boolean) || (bundle.get(str) instanceof String)) {
            return true;
        }
        bundle.remove(str);
        Log.w(TAG, "Synthesis request paramter " + str + " containst value  with invalid type. Should be a Boolean or String");
        return false;
    }

    private static boolean verifyFloatBundleParam(Bundle bundle, String str) {
        if (!bundle.containsKey(str) || (bundle.get(str) instanceof Float) || (bundle.get(str) instanceof Double)) {
            return true;
        }
        bundle.remove(str);
        Log.w(TAG, "Synthesis request paramter " + str + " containst value  with invalid type. Should be a Float or a Double");
        return false;
    }

    private void copyStringParam(Bundle bundle, HashMap<String, String> hashMap, String str) {
        String str2 = hashMap.get(str);
        if (str2 != null) {
            bundle.putString(str, str2);
        }
    }

    private void copyIntParam(Bundle bundle, HashMap<String, String> hashMap, String str) {
        String str2 = hashMap.get(str);
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        try {
            bundle.putInt(str, Integer.parseInt(str2));
        } catch (NumberFormatException e) {
            Log.w(TAG, "Error", e);
        }
    }

    private void copyFloatParam(Bundle bundle, HashMap<String, String> hashMap, String str) {
        String str2 = hashMap.get(str);
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        try {
            bundle.putFloat(str, Float.parseFloat(str2));
        } catch (NumberFormatException e) {
            Log.w(TAG, "Error", e);
        }
    }

    @Deprecated
    public int setOnUtteranceCompletedListener(OnUtteranceCompletedListener onUtteranceCompletedListener) {
        this.mUtteranceProgressListener = UtteranceProgressListener.from(onUtteranceCompletedListener);
        return 0;
    }

    public int setOnUtteranceProgressListener(UtteranceProgressListener utteranceProgressListener) {
        this.mUtteranceProgressListener = utteranceProgressListener;
        return 0;
    }

    @Deprecated
    public int setEngineByPackageName(String str) {
        this.mRequestedEngine = str;
        return initTts();
    }

    public String getDefaultEngine() {
        String defaultEngine = this.mEnginesHelper.getDefaultEngine();
        return (defaultEngine == null || !defaultEngine.equals(PRIVATE_ENGINE) || isPrivateEngineAvailable(PRIVATE_ENGINE)) ? defaultEngine : getHighestRankedPublicEngineName();
    }

    public List<EngineInfo> getEngines() {
        ArrayList arrayList = new ArrayList();
        for (EngineInfo engineInfo : this.mEnginesHelper.getEngines()) {
            if (!engineInfo.name.equals(PRIVATE_ENGINE) || isPrivateEngineAvailable(PRIVATE_ENGINE)) {
                arrayList.add(engineInfo);
            }
        }
        return arrayList;
    }

    private abstract class Connection implements ServiceConnection {
        private final ITextToSpeechCallback.Stub mCallback;
        private boolean mEstablished;
        private SetupConnectionAsyncTask mOnSetupConnectionAsyncTask;
        private ITextToSpeechService mService;

        abstract boolean connect(String str);

        abstract void disconnect();

        private Connection() {
            this.mCallback = new ITextToSpeechCallback.Stub() { // from class: android.speech.tts.TextToSpeech.Connection.1
                @Override // android.speech.tts.ITextToSpeechCallback
                public void onStop(String str, boolean z) throws RemoteException {
                    UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onStop(str, z);
                    }
                }

                @Override // android.speech.tts.ITextToSpeechCallback
                public void onSuccess(String str) {
                    UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onDone(str);
                    }
                }

                @Override // android.speech.tts.ITextToSpeechCallback
                public void onError(String str, int i) {
                    UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onError(str, i);
                    }
                }

                @Override // android.speech.tts.ITextToSpeechCallback
                public void onStart(String str) {
                    UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onStart(str);
                    }
                }

                @Override // android.speech.tts.ITextToSpeechCallback
                public void onBeginSynthesis(String str, int i, int i2, int i3) {
                    UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onBeginSynthesis(str, i, i2, i3);
                    }
                }

                @Override // android.speech.tts.ITextToSpeechCallback
                public void onAudioAvailable(String str, byte[] bArr) {
                    UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onAudioAvailable(str, bArr);
                    }
                }

                @Override // android.speech.tts.ITextToSpeechCallback
                public void onRangeStart(String str, int i, int i2, int i3) {
                    UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onRangeStart(str, i, i2, i3);
                    }
                }
            };
        }

        private class SetupConnectionAsyncTask extends AsyncTask<Void, Void, Integer> {
            private SetupConnectionAsyncTask() {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public Integer doInBackground(Void... voidArr) {
                synchronized (TextToSpeech.this.mStartLock) {
                    if (isCancelled()) {
                        return null;
                    }
                    try {
                        Connection.this.mService.setCallback(Connection.this.getCallerIdentity(), Connection.this.mCallback);
                        if (TextToSpeech.this.mParams.getString("language") == null) {
                            String[] clientDefaultLanguage = Connection.this.mService.getClientDefaultLanguage();
                            TextToSpeech.this.mParams.putString("language", clientDefaultLanguage[0]);
                            TextToSpeech.this.mParams.putString(Engine.KEY_PARAM_COUNTRY, clientDefaultLanguage[1]);
                            TextToSpeech.this.mParams.putString(Engine.KEY_PARAM_VARIANT, clientDefaultLanguage[2]);
                            TextToSpeech.this.mParams.putString(Engine.KEY_PARAM_VOICE_NAME, Connection.this.mService.getDefaultVoiceNameFor(clientDefaultLanguage[0], clientDefaultLanguage[1], clientDefaultLanguage[2]));
                        }
                        Log.i(TextToSpeech.TAG, "Setting up the connection to TTS engine...");
                        return 0;
                    } catch (RemoteException unused) {
                        Log.e(TextToSpeech.TAG, "Error connecting to service, setCallback() failed");
                        return -1;
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(Integer num) {
                synchronized (TextToSpeech.this.mStartLock) {
                    if (Connection.this.mOnSetupConnectionAsyncTask == this) {
                        Connection.this.mOnSetupConnectionAsyncTask = null;
                    }
                    Connection.this.mEstablished = true;
                    TextToSpeech.this.dispatchOnInit(num.intValue());
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (TextToSpeech.this.mStartLock) {
                TextToSpeech.this.mConnectingServiceConnection = null;
                Log.i(TextToSpeech.TAG, "Connected to TTS engine");
                SetupConnectionAsyncTask setupConnectionAsyncTask = this.mOnSetupConnectionAsyncTask;
                if (setupConnectionAsyncTask != null) {
                    setupConnectionAsyncTask.cancel(false);
                }
                this.mService = ITextToSpeechService.Stub.asInterface(iBinder);
                TextToSpeech.this.mServiceConnection = this;
                this.mEstablished = false;
                SetupConnectionAsyncTask setupConnectionAsyncTask2 = new SetupConnectionAsyncTask();
                this.mOnSetupConnectionAsyncTask = setupConnectionAsyncTask2;
                setupConnectionAsyncTask2.execute(new Void[0]);
            }
        }

        public IBinder getCallerIdentity() {
            return this.mCallback;
        }

        protected boolean clearServiceConnection() {
            boolean z;
            synchronized (TextToSpeech.this.mStartLock) {
                SetupConnectionAsyncTask setupConnectionAsyncTask = this.mOnSetupConnectionAsyncTask;
                z = false;
                if (setupConnectionAsyncTask != null) {
                    z = setupConnectionAsyncTask.cancel(false);
                    this.mOnSetupConnectionAsyncTask = null;
                }
                this.mService = null;
                if (TextToSpeech.this.mServiceConnection == this) {
                    TextToSpeech.this.mServiceConnection = null;
                }
            }
            return z;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TextToSpeech.TAG, "Disconnected from TTS engine");
            if (clearServiceConnection()) {
                TextToSpeech.this.dispatchOnInit(-1);
            }
        }

        public boolean isEstablished() {
            return this.mService != null && this.mEstablished;
        }

        public <R> R runAction(Action<R> action, R r, String str, boolean z, boolean z2) {
            synchronized (TextToSpeech.this.mStartLock) {
                try {
                    try {
                        if (this.mService == null) {
                            Log.w(TextToSpeech.TAG, str + " failed: not connected to TTS engine");
                            return r;
                        }
                        if (z2 && !isEstablished()) {
                            Log.w(TextToSpeech.TAG, str + " failed: TTS engine connection not fully set up");
                            return r;
                        }
                        return action.run(this.mService);
                    } catch (RemoteException e) {
                        Log.e(TextToSpeech.TAG, str + " failed", e);
                        if (z) {
                            disconnect();
                            TextToSpeech.this.initTts();
                        }
                        return r;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private class DirectConnection extends Connection {
        private DirectConnection() {
            super();
        }

        @Override // android.speech.tts.TextToSpeech.Connection
        boolean connect(String str) {
            Intent intent = new Intent(Engine.INTENT_ACTION_TTS_SERVICE);
            intent.setPackage(str);
            return TextToSpeech.this.mContext.bindService(intent, this, 1);
        }

        @Override // android.speech.tts.TextToSpeech.Connection
        void disconnect() {
            TextToSpeech.this.mContext.unbindService(this);
            clearServiceConnection();
        }
    }

    private class SystemConnection extends Connection {
        private volatile ITextToSpeechSession mSession;

        private SystemConnection() {
            super();
        }

        @Override // android.speech.tts.TextToSpeech.Connection
        boolean connect(String str) {
            ITextToSpeechManager asInterface = ITextToSpeechManager.Stub.asInterface(ServiceManager.getService(Context.TEXT_TO_SPEECH_MANAGER_SERVICE));
            if (asInterface == null) {
                Log.e(TextToSpeech.TAG, "System service is not available!");
                return false;
            }
            try {
                asInterface.createSession(str, new ITextToSpeechSessionCallback.Stub() { // from class: android.speech.tts.TextToSpeech.SystemConnection.1
                    @Override // android.speech.tts.ITextToSpeechSessionCallback
                    public void onConnected(ITextToSpeechSession iTextToSpeechSession, IBinder iBinder) {
                        SystemConnection.this.mSession = iTextToSpeechSession;
                        SystemConnection.this.onServiceConnected(null, iBinder);
                    }

                    @Override // android.speech.tts.ITextToSpeechSessionCallback
                    public void onDisconnected() {
                        SystemConnection.this.onServiceDisconnected(null);
                        SystemConnection.this.mSession = null;
                    }

                    @Override // android.speech.tts.ITextToSpeechSessionCallback
                    public void onError(String str2) {
                        Log.w(TextToSpeech.TAG, "System TTS connection error: " + str2);
                        TextToSpeech.this.dispatchOnInit(-1);
                    }
                });
                return true;
            } catch (RemoteException e) {
                Log.e(TextToSpeech.TAG, "Error communicating with the System Server: ", e);
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.speech.tts.TextToSpeech.Connection
        void disconnect() {
            ITextToSpeechSession iTextToSpeechSession = this.mSession;
            if (iTextToSpeechSession != null) {
                try {
                    iTextToSpeechSession.disconnect();
                } catch (RemoteException e) {
                    Log.w(TextToSpeech.TAG, "Error disconnecting session", e);
                }
                clearServiceConnection();
            }
        }
    }

    public static class EngineInfo {
        public int icon;
        public String label;
        public String name;
        public int priority;
        public boolean system;

        public String toString() {
            return "EngineInfo{name=" + this.name + "}";
        }
    }

    private boolean isPrivateEngineAvailable(String str) {
        if ("CN".equalsIgnoreCase(SystemProperties.get("ro.csc.countryiso_code"))) {
            return true;
        }
        if (!isPrivateEngineInstalled()) {
            Log.i(TAG, "com.samsung.SMT is not installed.");
            return false;
        }
        if (checkAuthority(str)) {
            Log.i(TAG, this.mContext.getPackageName() + " is allowed to bind to private engine.");
            return true;
        }
        Log.i(TAG, this.mContext.getPackageName() + " is not allowed to bind to private engine.");
        return false;
    }

    private boolean isPrivateEngineInstalled() {
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = new Intent(Engine.INTENT_ACTION_TTS_SERVICE);
        intent.setPackage(PRIVATE_ENGINE);
        List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 128);
        return queryIntentServices != null && queryIntentServices.size() == 1;
    }

    private boolean checkAuthority(String str) {
        Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://com.samsung.SMT.LanguageProvider"), new String[]{"check_allowed_package"}, this.mContext.getPackageName(), new String[]{str}, null);
        boolean z = false;
        if (query != null) {
            query.moveToFirst();
            do {
                int columnIndex = query.getColumnIndex("allowed");
                if (columnIndex >= 0 && "true".equals(query.getString(columnIndex))) {
                    z = true;
                }
            } while (query.moveToNext());
            query.close();
        }
        return z;
    }

    private String getHighestRankedPublicEngineName() {
        List<EngineInfo> engines = this.mEnginesHelper.getEngines();
        if (engines.size() <= 0) {
            return null;
        }
        for (EngineInfo engineInfo : engines) {
            if (engineInfo.system && !engineInfo.name.equals(PRIVATE_ENGINE)) {
                Log.i(TAG, "getHighestRankedPublicEngineName = " + engineInfo.name);
                return engineInfo.name;
            }
        }
        return null;
    }
}
