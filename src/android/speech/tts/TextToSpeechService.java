package android.speech.tts;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.provider.Settings;
import android.speech.tts.ITextToSpeechService;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

/* loaded from: classes3.dex */
public abstract class TextToSpeechService extends Service {
    private static final boolean DBG = false;
    private static final String SYNTH_THREAD_NAME = "SynthThread";
    private static final String TAG = "TextToSpeechService";
    private AudioPlaybackHandler mAudioPlaybackHandler;
    private CallbackMap mCallbacks;
    private TtsEngines mEngineHelper;
    private String mPackageName;
    private SynthHandler mSynthHandler;
    private IActivityManager mIActivityManager = ActivityManager.getService();
    private ArrayList<Integer> mTTSList = new ArrayList<>();
    private final Object mVoicesInfoLock = new Object();
    private final ITextToSpeechService.Stub mBinder = new ITextToSpeechService.Stub() { // from class: android.speech.tts.TextToSpeechService.1
        @Override // android.speech.tts.ITextToSpeechService
        public int speak(IBinder iBinder, CharSequence charSequence, int i, Bundle bundle, String str) {
            if (!checkNonNull(iBinder, charSequence, bundle)) {
                return -1;
            }
            return TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(i, TextToSpeechService.this.new SynthesisSpeechItem(iBinder, Binder.getCallingUid(), Binder.getCallingPid(), bundle, str, charSequence));
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int synthesizeToFileDescriptor(IBinder iBinder, CharSequence charSequence, ParcelFileDescriptor parcelFileDescriptor, Bundle bundle, String str) {
            if (!checkNonNull(iBinder, charSequence, parcelFileDescriptor, bundle)) {
                return -1;
            }
            return TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(1, new SynthesisToFileOutputStreamSpeechItem(TextToSpeechService.this, iBinder, Binder.getCallingUid(), Binder.getCallingPid(), bundle, str, charSequence, new ParcelFileDescriptor.AutoCloseOutputStream(ParcelFileDescriptor.adoptFd(parcelFileDescriptor.detachFd()))));
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int playAudio(IBinder iBinder, Uri uri, int i, Bundle bundle, String str) {
            if (!checkNonNull(iBinder, uri, bundle)) {
                return -1;
            }
            return TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(i, TextToSpeechService.this.new AudioSpeechItem(iBinder, Binder.getCallingUid(), Binder.getCallingPid(), bundle, str, uri));
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int playSilence(IBinder iBinder, long j, int i, String str) {
            if (!checkNonNull(iBinder)) {
                return -1;
            }
            return TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(i, TextToSpeechService.this.new SilenceSpeechItem(iBinder, Binder.getCallingUid(), Binder.getCallingPid(), str, j));
        }

        @Override // android.speech.tts.ITextToSpeechService
        public boolean isSpeaking() {
            return TextToSpeechService.this.mSynthHandler.isSpeaking() || TextToSpeechService.this.mAudioPlaybackHandler.isSpeaking();
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int stop(IBinder iBinder) {
            if (checkNonNull(iBinder)) {
                return TextToSpeechService.this.mSynthHandler.stopForApp(iBinder);
            }
            return -1;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public String[] getLanguage() {
            return TextToSpeechService.this.onGetLanguage();
        }

        @Override // android.speech.tts.ITextToSpeechService
        public String[] getClientDefaultLanguage() {
            return TextToSpeechService.this.getSettingsLocale();
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int isLanguageAvailable(String str, String str2, String str3) {
            if (checkNonNull(str)) {
                return TextToSpeechService.this.onIsLanguageAvailable(str, str2, str3);
            }
            return -1;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public String[] getFeaturesForLanguage(String str, String str2, String str3) {
            Set<String> onGetFeaturesForLanguage = TextToSpeechService.this.onGetFeaturesForLanguage(str, str2, str3);
            if (onGetFeaturesForLanguage != null) {
                String[] strArr = new String[onGetFeaturesForLanguage.size()];
                onGetFeaturesForLanguage.toArray(strArr);
                return strArr;
            }
            return new String[0];
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int loadLanguage(IBinder iBinder, String str, String str2, String str3) {
            if (!checkNonNull(str)) {
                return -1;
            }
            int onIsLanguageAvailable = TextToSpeechService.this.onIsLanguageAvailable(str, str2, str3);
            if (onIsLanguageAvailable == 0 || onIsLanguageAvailable == 1 || onIsLanguageAvailable == 2) {
                if (TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(1, TextToSpeechService.this.new LoadLanguageItem(iBinder, Binder.getCallingUid(), Binder.getCallingPid(), str, str2, str3)) != 0) {
                    return -1;
                }
            }
            return onIsLanguageAvailable;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public List<Voice> getVoices() {
            return TextToSpeechService.this.onGetVoices();
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int loadVoice(IBinder iBinder, String str) {
            if (!checkNonNull(str)) {
                return -1;
            }
            int onIsValidVoiceName = TextToSpeechService.this.onIsValidVoiceName(str);
            if (onIsValidVoiceName == 0) {
                if (TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(1, TextToSpeechService.this.new LoadVoiceItem(iBinder, Binder.getCallingUid(), Binder.getCallingPid(), str)) != 0) {
                    return -1;
                }
            }
            return onIsValidVoiceName;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public String getDefaultVoiceNameFor(String str, String str2, String str3) {
            if (!checkNonNull(str)) {
                return null;
            }
            int onIsLanguageAvailable = TextToSpeechService.this.onIsLanguageAvailable(str, str2, str3);
            if (onIsLanguageAvailable == 0 || onIsLanguageAvailable == 1 || onIsLanguageAvailable == 2) {
                return TextToSpeechService.this.onGetDefaultVoiceNameFor(str, str2, str3);
            }
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public void setCallback(IBinder iBinder, ITextToSpeechCallback iTextToSpeechCallback) {
            if (checkNonNull(iBinder)) {
                TextToSpeechService.this.mCallbacks.setCallback(iBinder, iTextToSpeechCallback);
            }
        }

        private String intern(String str) {
            return str.intern();
        }

        private boolean checkNonNull(Object... objArr) {
            for (Object obj : objArr) {
                if (obj == null) {
                    return false;
                }
            }
            return true;
        }
    };

    interface UtteranceProgressDispatcher {
        void dispatchOnAudioAvailable(byte[] bArr);

        void dispatchOnBeginSynthesis(int i, int i2, int i3);

        void dispatchOnError(int i);

        void dispatchOnRangeStart(int i, int i2, int i3);

        void dispatchOnStart();

        void dispatchOnStop();

        void dispatchOnSuccess();
    }

    protected abstract String[] onGetLanguage();

    protected abstract int onIsLanguageAvailable(String str, String str2, String str3);

    protected abstract int onLoadLanguage(String str, String str2, String str3);

    protected abstract void onStop();

    protected abstract void onSynthesizeText(SynthesisRequest synthesisRequest, SynthesisCallback synthesisCallback);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        SynthThread synthThread = new SynthThread();
        synthThread.start();
        this.mSynthHandler = new SynthHandler(synthThread.getLooper());
        AudioPlaybackHandler audioPlaybackHandler = new AudioPlaybackHandler();
        this.mAudioPlaybackHandler = audioPlaybackHandler;
        audioPlaybackHandler.start();
        this.mEngineHelper = new TtsEngines(this);
        this.mCallbacks = new CallbackMap();
        this.mPackageName = getApplicationInfo().packageName;
        String[] settingsLocale = getSettingsLocale();
        onLoadLanguage(settingsLocale[0], settingsLocale[1], settingsLocale[2]);
    }

    @Override // android.app.Service
    public void onDestroy() {
        try {
            this.mTTSList.clear();
            this.mIActivityManager.clearTTSPkgInfo();
        } catch (Exception e) {
            Log.e(TAG, "onDestroy" + e);
        }
        this.mSynthHandler.quit();
        this.mAudioPlaybackHandler.quit();
        this.mCallbacks.kill();
        super.onDestroy();
    }

    protected Set<String> onGetFeaturesForLanguage(String str, String str2, String str3) {
        return new HashSet();
    }

    private int getExpectedLanguageAvailableStatus(Locale locale) {
        if (locale.getVariant().isEmpty()) {
            return locale.getCountry().isEmpty() ? 0 : 1;
        }
        return 2;
    }

    public List<Voice> onGetVoices() {
        ArrayList arrayList = new ArrayList();
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                if (onIsLanguageAvailable(locale.getISO3Language(), locale.getISO3Country(), locale.getVariant()) == getExpectedLanguageAvailableStatus(locale)) {
                    arrayList.add(new Voice(onGetDefaultVoiceNameFor(locale.getISO3Language(), locale.getISO3Country(), locale.getVariant()), locale, 300, 300, false, onGetFeaturesForLanguage(locale.getISO3Language(), locale.getISO3Country(), locale.getVariant())));
                }
            } catch (MissingResourceException unused) {
            }
        }
        return arrayList;
    }

    public String onGetDefaultVoiceNameFor(String str, String str2, String str3) {
        Locale locale;
        int onIsLanguageAvailable = onIsLanguageAvailable(str, str2, str3);
        if (onIsLanguageAvailable == 0) {
            locale = new Locale(str);
        } else if (onIsLanguageAvailable == 1) {
            locale = new Locale(str, str2);
        } else {
            if (onIsLanguageAvailable != 2) {
                return null;
            }
            locale = new Locale(str, str2, str3);
        }
        String languageTag = TtsEngines.normalizeTTSLocale(locale).toLanguageTag();
        if (onIsValidVoiceName(languageTag) == 0) {
            return languageTag;
        }
        return null;
    }

    public int onLoadVoice(String str) {
        Locale forLanguageTag = Locale.forLanguageTag(str);
        if (forLanguageTag == null) {
            return -1;
        }
        try {
            if (onIsLanguageAvailable(forLanguageTag.getISO3Language(), forLanguageTag.getISO3Country(), forLanguageTag.getVariant()) != getExpectedLanguageAvailableStatus(forLanguageTag)) {
                return -1;
            }
            onLoadLanguage(forLanguageTag.getISO3Language(), forLanguageTag.getISO3Country(), forLanguageTag.getVariant());
            return 0;
        } catch (MissingResourceException unused) {
            return -1;
        }
    }

    public int onIsValidVoiceName(String str) {
        Locale forLanguageTag = Locale.forLanguageTag(str);
        if (forLanguageTag == null) {
            return -1;
        }
        try {
            return onIsLanguageAvailable(forLanguageTag.getISO3Language(), forLanguageTag.getISO3Country(), forLanguageTag.getVariant()) != getExpectedLanguageAvailableStatus(forLanguageTag) ? -1 : 0;
        } catch (MissingResourceException unused) {
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDefaultSpeechRate() {
        return getSecureSettingInt(Settings.Secure.TTS_DEFAULT_RATE, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDefaultPitch() {
        return getSecureSettingInt(Settings.Secure.TTS_DEFAULT_PITCH, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String[] getSettingsLocale() {
        return TtsEngines.toOldLocaleStringFormat(this.mEngineHelper.getLocalePrefForEngine(this.mPackageName));
    }

    private int getSecureSettingInt(String str, int i) {
        return Settings.Secure.getInt(getContentResolver(), str, i);
    }

    private class SynthThread extends HandlerThread implements MessageQueue.IdleHandler {
        private boolean mFirstIdle;

        public SynthThread() {
            super(TextToSpeechService.SYNTH_THREAD_NAME, 0);
            this.mFirstIdle = true;
        }

        @Override // android.os.HandlerThread
        protected void onLooperPrepared() {
            getLooper().getQueue().addIdleHandler(this);
        }

        @Override // android.os.MessageQueue.IdleHandler
        public boolean queueIdle() {
            if (this.mFirstIdle) {
                this.mFirstIdle = false;
                return true;
            }
            broadcastTtsQueueProcessingCompleted();
            return true;
        }

        private void broadcastTtsQueueProcessingCompleted() {
            TextToSpeechService.this.sendBroadcast(new Intent(TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED));
        }
    }

    private class SynthHandler extends Handler {
        private SpeechItem mCurrentSpeechItem;
        private int mFlushAll;
        private List<Object> mFlushedObjects;

        public SynthHandler(Looper looper) {
            super(looper);
            this.mCurrentSpeechItem = null;
            this.mFlushedObjects = new ArrayList();
            this.mFlushAll = 0;
        }

        private void startFlushingSpeechItems(Object obj) {
            synchronized (this.mFlushedObjects) {
                if (obj == null) {
                    this.mFlushAll++;
                } else {
                    this.mFlushedObjects.add(obj);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void endFlushingSpeechItems(Object obj) {
            synchronized (this.mFlushedObjects) {
                if (obj == null) {
                    this.mFlushAll--;
                } else {
                    this.mFlushedObjects.remove(obj);
                }
            }
        }

        private boolean isFlushed(SpeechItem speechItem) {
            boolean z;
            synchronized (this.mFlushedObjects) {
                z = this.mFlushAll > 0 || this.mFlushedObjects.contains(speechItem.getCallerIdentity());
            }
            return z;
        }

        private synchronized SpeechItem getCurrentSpeechItem() {
            return this.mCurrentSpeechItem;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized boolean setCurrentSpeechItem(SpeechItem speechItem) {
            if (speechItem != null) {
                if (isFlushed(speechItem)) {
                    return false;
                }
            }
            this.mCurrentSpeechItem = speechItem;
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized SpeechItem removeCurrentSpeechItem() {
            SpeechItem speechItem;
            speechItem = this.mCurrentSpeechItem;
            this.mCurrentSpeechItem = null;
            return speechItem;
        }

        private synchronized SpeechItem maybeRemoveCurrentSpeechItem(Object obj) {
            SpeechItem speechItem = this.mCurrentSpeechItem;
            if (speechItem == null || speechItem.getCallerIdentity() != obj) {
                return null;
            }
            SpeechItem speechItem2 = this.mCurrentSpeechItem;
            this.mCurrentSpeechItem = null;
            return speechItem2;
        }

        public boolean isSpeaking() {
            return getCurrentSpeechItem() != null;
        }

        public void quit() {
            getLooper().quit();
            SpeechItem removeCurrentSpeechItem = removeCurrentSpeechItem();
            if (removeCurrentSpeechItem != null) {
                removeCurrentSpeechItem.stop();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int enqueueSpeechItem(int i, final SpeechItem speechItem) {
            UtteranceProgressDispatcher utteranceProgressDispatcher = speechItem instanceof UtteranceProgressDispatcher ? (UtteranceProgressDispatcher) speechItem : null;
            if (!speechItem.isValid()) {
                if (utteranceProgressDispatcher != null) {
                    utteranceProgressDispatcher.dispatchOnError(-8);
                }
                return -1;
            }
            if (i == 0) {
                stopForApp(speechItem.getCallerIdentity());
            } else if (i == 2) {
                stopAll();
            }
            Message obtain = Message.obtain(this, new Runnable() { // from class: android.speech.tts.TextToSpeechService.SynthHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    if (SynthHandler.this.setCurrentSpeechItem(speechItem)) {
                        speechItem.play();
                        SynthHandler.this.removeCurrentSpeechItem();
                    } else {
                        speechItem.stop();
                    }
                }
            });
            obtain.obj = speechItem.getCallerIdentity();
            if (sendMessage(obtain)) {
                try {
                    int callerUid = speechItem.getCallerUid();
                    if (TextToSpeechService.this.mTTSList.contains(Integer.valueOf(callerUid))) {
                        return 0;
                    }
                    TextToSpeechService.this.mTTSList.add(Integer.valueOf(callerUid));
                    TextToSpeechService.this.mIActivityManager.setTTSPkgInfo(callerUid);
                    return 0;
                } catch (Exception e) {
                    Log.e(TextToSpeechService.TAG, "enqueueSpeech" + e);
                    return 0;
                }
            }
            Log.w(TextToSpeechService.TAG, "SynthThread has quit");
            if (utteranceProgressDispatcher != null) {
                utteranceProgressDispatcher.dispatchOnError(-4);
            }
            return -1;
        }

        public int stopForApp(final Object obj) {
            if (obj == null) {
                return -1;
            }
            startFlushingSpeechItems(obj);
            SpeechItem maybeRemoveCurrentSpeechItem = maybeRemoveCurrentSpeechItem(obj);
            if (maybeRemoveCurrentSpeechItem != null) {
                maybeRemoveCurrentSpeechItem.stop();
            }
            TextToSpeechService.this.mAudioPlaybackHandler.stopForApp(obj);
            sendMessage(Message.obtain(this, new Runnable() { // from class: android.speech.tts.TextToSpeechService.SynthHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    SynthHandler.this.endFlushingSpeechItems(obj);
                }
            }));
            return 0;
        }

        public int stopAll() {
            startFlushingSpeechItems(null);
            SpeechItem removeCurrentSpeechItem = removeCurrentSpeechItem();
            if (removeCurrentSpeechItem != null) {
                removeCurrentSpeechItem.stop();
            }
            TextToSpeechService.this.mAudioPlaybackHandler.stop();
            sendMessage(Message.obtain(this, new Runnable() { // from class: android.speech.tts.TextToSpeechService.SynthHandler.3
                @Override // java.lang.Runnable
                public void run() {
                    SynthHandler.this.endFlushingSpeechItems(null);
                }
            }));
            return 0;
        }
    }

    static class AudioOutputParams {
        public final AudioAttributes mAudioAttributes;
        public final float mPan;
        public final int mSessionId;
        public final float mVolume;

        AudioOutputParams() {
            this.mSessionId = 0;
            this.mVolume = 1.0f;
            this.mPan = 0.0f;
            this.mAudioAttributes = null;
        }

        AudioOutputParams(int i, float f, float f2, AudioAttributes audioAttributes) {
            this.mSessionId = i;
            this.mVolume = f;
            this.mPan = f2;
            this.mAudioAttributes = audioAttributes;
        }

        static AudioOutputParams createFromParamsBundle(Bundle bundle, boolean z) {
            if (bundle == null) {
                return new AudioOutputParams();
            }
            AudioAttributes audioAttributes = (AudioAttributes) bundle.getParcelable(TextToSpeech.Engine.KEY_PARAM_AUDIO_ATTRIBUTES, AudioAttributes.class);
            if (audioAttributes == null) {
                audioAttributes = new AudioAttributes.Builder().setLegacyStreamType(bundle.getInt(TextToSpeech.Engine.KEY_PARAM_STREAM, 3)).setContentType(z ? 1 : 4).build();
            }
            return new AudioOutputParams(bundle.getInt("sessionId", 0), bundle.getFloat("volume", 1.0f), bundle.getFloat(TextToSpeech.Engine.KEY_PARAM_PAN, 0.0f), audioAttributes);
        }
    }

    private abstract class SpeechItem {
        private final Object mCallerIdentity;
        private final int mCallerPid;
        private final int mCallerUid;
        private boolean mStarted = false;
        private boolean mStopped = false;

        public abstract boolean isValid();

        protected abstract void playImpl();

        protected abstract void stopImpl();

        public SpeechItem(TextToSpeechService textToSpeechService, Object obj, int i, int i2) {
            this.mCallerIdentity = obj;
            this.mCallerUid = i;
            this.mCallerPid = i2;
        }

        public Object getCallerIdentity() {
            return this.mCallerIdentity;
        }

        public int getCallerUid() {
            return this.mCallerUid;
        }

        public int getCallerPid() {
            return this.mCallerPid;
        }

        public void play() {
            synchronized (this) {
                if (this.mStarted) {
                    throw new IllegalStateException("play() called twice");
                }
                this.mStarted = true;
            }
            playImpl();
        }

        public void stop() {
            synchronized (this) {
                if (this.mStopped) {
                    throw new IllegalStateException("stop() called twice");
                }
                this.mStopped = true;
            }
            stopImpl();
        }

        protected synchronized boolean isStopped() {
            return this.mStopped;
        }

        protected synchronized boolean isStarted() {
            return this.mStarted;
        }
    }

    private abstract class UtteranceSpeechItem extends SpeechItem implements UtteranceProgressDispatcher {
        public abstract String getUtteranceId();

        public UtteranceSpeechItem(Object obj, int i, int i2) {
            super(TextToSpeechService.this, obj, i, i2);
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnSuccess() {
            String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnSuccess(getCallerIdentity(), utteranceId);
            }
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnStop() {
            String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnStop(getCallerIdentity(), utteranceId, isStarted());
            }
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnStart() {
            String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnStart(getCallerIdentity(), utteranceId);
            }
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnError(int i) {
            String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnError(getCallerIdentity(), utteranceId, i);
            }
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnBeginSynthesis(int i, int i2, int i3) {
            String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnBeginSynthesis(getCallerIdentity(), utteranceId, i, i2, i3);
            }
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnAudioAvailable(byte[] bArr) {
            String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnAudioAvailable(getCallerIdentity(), utteranceId, bArr);
            }
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnRangeStart(int i, int i2, int i3) {
            String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                TextToSpeechService.this.mCallbacks.dispatchOnRangeStart(getCallerIdentity(), utteranceId, i, i2, i3);
            }
        }

        String getStringParam(Bundle bundle, String str, String str2) {
            return bundle == null ? str2 : bundle.getString(str, str2);
        }

        int getIntParam(Bundle bundle, String str, int i) {
            return bundle == null ? i : bundle.getInt(str, i);
        }

        float getFloatParam(Bundle bundle, String str, float f) {
            return bundle == null ? f : bundle.getFloat(str, f);
        }
    }

    private abstract class UtteranceSpeechItemWithParams extends UtteranceSpeechItem {
        protected final Bundle mParams;
        protected final String mUtteranceId;

        UtteranceSpeechItemWithParams(Object obj, int i, int i2, Bundle bundle, String str) {
            super(obj, i, i2);
            this.mParams = bundle;
            this.mUtteranceId = str;
        }

        boolean hasLanguage() {
            return !TextUtils.isEmpty(getStringParam(this.mParams, "language", null));
        }

        int getSpeechRate() {
            return getIntParam(this.mParams, TextToSpeech.Engine.KEY_PARAM_RATE, TextToSpeechService.this.getDefaultSpeechRate());
        }

        int getPitch() {
            return getIntParam(this.mParams, TextToSpeech.Engine.KEY_PARAM_PITCH, TextToSpeechService.this.getDefaultPitch());
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceSpeechItem
        public String getUtteranceId() {
            return this.mUtteranceId;
        }

        AudioOutputParams getAudioParams() {
            return AudioOutputParams.createFromParamsBundle(this.mParams, true);
        }
    }

    class SynthesisSpeechItem extends UtteranceSpeechItemWithParams {
        private final int mCallerUid;
        private final String[] mDefaultLocale;
        private final EventLogger mEventLogger;
        private AbstractSynthesisCallback mSynthesisCallback;
        private final SynthesisRequest mSynthesisRequest;
        private final CharSequence mText;

        public SynthesisSpeechItem(Object obj, int i, int i2, Bundle bundle, String str, CharSequence charSequence) {
            super(obj, i, i2, bundle, str);
            this.mText = charSequence;
            this.mCallerUid = i;
            SynthesisRequest synthesisRequest = new SynthesisRequest(charSequence, this.mParams);
            this.mSynthesisRequest = synthesisRequest;
            this.mDefaultLocale = TextToSpeechService.this.getSettingsLocale();
            setRequestParams(synthesisRequest);
            this.mEventLogger = new EventLogger(synthesisRequest, i, i2, TextToSpeechService.this.mPackageName);
        }

        public CharSequence getText() {
            return this.mText;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        public boolean isValid() {
            CharSequence charSequence = this.mText;
            if (charSequence == null) {
                Log.e(TextToSpeechService.TAG, "null synthesis text");
                return false;
            }
            if (charSequence.length() <= TextToSpeech.getMaxSpeechInputLength()) {
                return true;
            }
            Log.w(TextToSpeechService.TAG, "Text too long: " + this.mText.length() + " chars");
            return false;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void playImpl() {
            this.mEventLogger.onRequestProcessingStart();
            synchronized (this) {
                if (isStopped()) {
                    return;
                }
                AbstractSynthesisCallback createSynthesisCallback = createSynthesisCallback();
                this.mSynthesisCallback = createSynthesisCallback;
                TextToSpeechService.this.onSynthesizeText(this.mSynthesisRequest, createSynthesisCallback);
                if (!createSynthesisCallback.hasStarted() || createSynthesisCallback.hasFinished()) {
                    return;
                }
                createSynthesisCallback.done();
            }
        }

        protected AbstractSynthesisCallback createSynthesisCallback() {
            return new PlaybackSynthesisCallback(getAudioParams(), TextToSpeechService.this.mAudioPlaybackHandler, this, getCallerIdentity(), this.mEventLogger, false);
        }

        private void setRequestParams(SynthesisRequest synthesisRequest) {
            String voiceName = getVoiceName();
            synthesisRequest.setLanguage(getLanguage(), getCountry(), getVariant());
            if (!TextUtils.isEmpty(voiceName)) {
                synthesisRequest.setVoiceName(getVoiceName());
            }
            synthesisRequest.setSpeechRate(getSpeechRate());
            synthesisRequest.setCallerUid(this.mCallerUid);
            synthesisRequest.setPitch(getPitch());
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void stopImpl() {
            AbstractSynthesisCallback abstractSynthesisCallback;
            synchronized (this) {
                abstractSynthesisCallback = this.mSynthesisCallback;
            }
            if (abstractSynthesisCallback != null) {
                abstractSynthesisCallback.stop();
                TextToSpeechService.this.onStop();
            } else {
                dispatchOnStop();
            }
        }

        private String getCountry() {
            return !hasLanguage() ? this.mDefaultLocale[1] : getStringParam(this.mParams, TextToSpeech.Engine.KEY_PARAM_COUNTRY, "");
        }

        private String getVariant() {
            return !hasLanguage() ? this.mDefaultLocale[2] : getStringParam(this.mParams, TextToSpeech.Engine.KEY_PARAM_VARIANT, "");
        }

        public String getLanguage() {
            return getStringParam(this.mParams, "language", this.mDefaultLocale[0]);
        }

        public String getVoiceName() {
            return getStringParam(this.mParams, TextToSpeech.Engine.KEY_PARAM_VOICE_NAME, "");
        }
    }

    private class SynthesisToFileOutputStreamSpeechItem extends SynthesisSpeechItem {
        private final FileOutputStream mFileOutputStream;

        public SynthesisToFileOutputStreamSpeechItem(TextToSpeechService textToSpeechService, Object obj, int i, int i2, Bundle bundle, String str, CharSequence charSequence, FileOutputStream fileOutputStream) {
            super(obj, i, i2, bundle, str, charSequence);
            this.mFileOutputStream = fileOutputStream;
        }

        @Override // android.speech.tts.TextToSpeechService.SynthesisSpeechItem
        protected AbstractSynthesisCallback createSynthesisCallback() {
            return new FileSynthesisCallback(this.mFileOutputStream.getChannel(), this, false);
        }

        @Override // android.speech.tts.TextToSpeechService.SynthesisSpeechItem, android.speech.tts.TextToSpeechService.SpeechItem
        protected void playImpl() {
            super.playImpl();
            try {
                this.mFileOutputStream.close();
            } catch (IOException e) {
                Log.w(TextToSpeechService.TAG, "Failed to close output file", e);
            }
        }
    }

    private class AudioSpeechItem extends UtteranceSpeechItemWithParams {
        private final AudioPlaybackQueueItem mItem;

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        public boolean isValid() {
            return true;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void stopImpl() {
        }

        public AudioSpeechItem(Object obj, int i, int i2, Bundle bundle, String str, Uri uri) {
            super(obj, i, i2, bundle, str);
            this.mItem = new AudioPlaybackQueueItem(this, getCallerIdentity(), TextToSpeechService.this, uri, getAudioParams());
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void playImpl() {
            TextToSpeechService.this.mAudioPlaybackHandler.enqueue(this.mItem);
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceSpeechItemWithParams, android.speech.tts.TextToSpeechService.UtteranceSpeechItem
        public String getUtteranceId() {
            return getStringParam(this.mParams, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, null);
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceSpeechItemWithParams
        AudioOutputParams getAudioParams() {
            return AudioOutputParams.createFromParamsBundle(this.mParams, false);
        }
    }

    private class SilenceSpeechItem extends UtteranceSpeechItem {
        private final long mDuration;
        private final String mUtteranceId;

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        public boolean isValid() {
            return true;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void stopImpl() {
        }

        public SilenceSpeechItem(Object obj, int i, int i2, String str, long j) {
            super(obj, i, i2);
            this.mUtteranceId = str;
            this.mDuration = j;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void playImpl() {
            TextToSpeechService.this.mAudioPlaybackHandler.enqueue(new SilencePlaybackQueueItem(this, getCallerIdentity(), this.mDuration));
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceSpeechItem
        public String getUtteranceId() {
            return this.mUtteranceId;
        }
    }

    private class LoadLanguageItem extends SpeechItem {
        private final String mCountry;
        private final String mLanguage;
        private final String mVariant;

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        public boolean isValid() {
            return true;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void stopImpl() {
        }

        public LoadLanguageItem(Object obj, int i, int i2, String str, String str2, String str3) {
            super(TextToSpeechService.this, obj, i, i2);
            this.mLanguage = str;
            this.mCountry = str2;
            this.mVariant = str3;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void playImpl() {
            TextToSpeechService.this.onLoadLanguage(this.mLanguage, this.mCountry, this.mVariant);
        }
    }

    private class LoadVoiceItem extends SpeechItem {
        private final String mVoiceName;

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        public boolean isValid() {
            return true;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void stopImpl() {
        }

        public LoadVoiceItem(Object obj, int i, int i2, String str) {
            super(TextToSpeechService.this, obj, i, i2);
            this.mVoiceName = str;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void playImpl() {
            TextToSpeechService.this.onLoadVoice(this.mVoiceName);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (!TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE.equals(intent.getAction())) {
            return null;
        }
        Binder.allowBlocking(this.mBinder.asBinder());
        return this.mBinder;
    }

    private class CallbackMap extends RemoteCallbackList<ITextToSpeechCallback> {
        private final HashMap<IBinder, ITextToSpeechCallback> mCallerToCallback;

        private CallbackMap() {
            this.mCallerToCallback = new HashMap<>();
        }

        public void setCallback(IBinder iBinder, ITextToSpeechCallback iTextToSpeechCallback) {
            ITextToSpeechCallback remove;
            synchronized (this.mCallerToCallback) {
                if (iTextToSpeechCallback != null) {
                    register(iTextToSpeechCallback, iBinder);
                    remove = this.mCallerToCallback.put(iBinder, iTextToSpeechCallback);
                } else {
                    remove = this.mCallerToCallback.remove(iBinder);
                }
                if (remove != null && remove != iTextToSpeechCallback) {
                    unregister(remove);
                }
            }
        }

        public void dispatchOnStop(Object obj, String str, boolean z) {
            ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onStop(str, z);
            } catch (RemoteException e) {
                Log.e(TextToSpeechService.TAG, "Callback onStop failed: " + e);
            }
        }

        public void dispatchOnSuccess(Object obj, String str) {
            ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onSuccess(str);
            } catch (RemoteException e) {
                Log.e(TextToSpeechService.TAG, "Callback onDone failed: " + e);
            }
        }

        public void dispatchOnStart(Object obj, String str) {
            ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onStart(str);
            } catch (RemoteException e) {
                Log.e(TextToSpeechService.TAG, "Callback onStart failed: " + e);
            }
        }

        public void dispatchOnError(Object obj, String str, int i) {
            ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onError(str, i);
            } catch (RemoteException e) {
                Log.e(TextToSpeechService.TAG, "Callback onError failed: " + e);
            }
        }

        public void dispatchOnBeginSynthesis(Object obj, String str, int i, int i2, int i3) {
            ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onBeginSynthesis(str, i, i2, i3);
            } catch (RemoteException e) {
                Log.e(TextToSpeechService.TAG, "Callback dispatchOnBeginSynthesis(String, int, int, int) failed: " + e);
            }
        }

        public void dispatchOnAudioAvailable(Object obj, String str, byte[] bArr) {
            ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onAudioAvailable(str, bArr);
            } catch (RemoteException e) {
                Log.e(TextToSpeechService.TAG, "Callback dispatchOnAudioAvailable(String, byte[]) failed: " + e);
            }
        }

        public void dispatchOnRangeStart(Object obj, String str, int i, int i2, int i3) {
            ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onRangeStart(str, i, i2, i3);
            } catch (RemoteException e) {
                Log.e(TextToSpeechService.TAG, "Callback dispatchOnRangeStart(String, int, int, int) failed: " + e);
            }
        }

        @Override // android.os.RemoteCallbackList
        public void onCallbackDied(ITextToSpeechCallback iTextToSpeechCallback, Object obj) {
            IBinder iBinder = (IBinder) obj;
            synchronized (this.mCallerToCallback) {
                this.mCallerToCallback.remove(iBinder);
            }
            TextToSpeechService.this.mSynthHandler.stopForApp(iBinder);
        }

        @Override // android.os.RemoteCallbackList
        public void kill() {
            synchronized (this.mCallerToCallback) {
                this.mCallerToCallback.clear();
                super.kill();
            }
        }

        private ITextToSpeechCallback getCallbackFor(Object obj) {
            ITextToSpeechCallback iTextToSpeechCallback;
            IBinder iBinder = (IBinder) obj;
            synchronized (this.mCallerToCallback) {
                iTextToSpeechCallback = this.mCallerToCallback.get(iBinder);
            }
            return iTextToSpeechCallback;
        }
    }
}
