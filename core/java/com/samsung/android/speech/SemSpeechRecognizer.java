package com.samsung.android.speech;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.Message;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

/* loaded from: classes5.dex */
public class SemSpeechRecognizer {
    public static final int STATE_READY = 0;
    public static final int STATE_RUNNING = 1;
    private static final String TAG = SemSpeechRecognizer.class.getSimpleName();
    private Context mContext;
    private AudioTask audio = null;
    private Thread audio_thread = null;
    private ResultListener mListener = null;
    private int mState = 0;
    public boolean isEnableBargeIn = false;
    private boolean isEnableChineseBargeIn = false;
    private boolean isEnableExtraSpanish = false;
    private boolean isEnableExtraRussian = false;
    private boolean samsungOOVResult = false;
    private int intBargeInResult = -1;
    private int uselanguage = 1;
    private Handler handler = null;
    private Handler mStopHandler = null;
    private final String SVOICE_LANGUAGE_FILE = "/data/data/com.vlingo.midas/files/language.bin";

    public interface ResultListener {
        void onResults(String[] strArr);
    }

    public SemSpeechRecognizer() {
        this.mContext = null;
        this.mContext = null;
        init();
    }

    public SemSpeechRecognizer(Context ctx) {
        this.mContext = null;
        this.mContext = ctx;
        Log.m98i(TAG, "BargeInRecognizer get Context " + this.mContext);
        init();
    }

    private void init() {
        String str = TAG;
        Log.m98i(str, "make new SemSpeechRecognizer VER 18.11.13");
        this.isEnableBargeIn = isUseModel();
        this.isEnableChineseBargeIn = isChineseMode();
        if (isPDTModel()) {
            this.isEnableExtraSpanish = true;
            this.isEnableExtraRussian = true;
        } else {
            this.isEnableExtraSpanish = isBargeInFile("/system/voicecommanddata/include/bargein_language_extra_es");
            this.isEnableExtraRussian = isBargeInFile("/system/voicecommanddata/include/bargein_language_extra_ru");
        }
        setLanguage();
        this.mState = 0;
        Log.m98i(str, "isEnableBargeIn : " + this.isEnableBargeIn);
        Log.m98i(str, "uselanguage : " + this.uselanguage);
        Log.m98i(str, "isEnableChineseBargeIn : " + this.isEnableChineseBargeIn);
        Log.m98i(str, "isEnableExtraSpanish : " + this.isEnableExtraSpanish);
        Log.m98i(str, "isEnableExtraRussian : " + this.isEnableExtraRussian);
    }

    public void setListener(ResultListener listener) {
        this.mListener = listener;
        this.mState = 0;
    }

    public void setContext(Context context) {
        Log.m98i(TAG, "setContext");
        this.mContext = context;
    }

    public int getState() {
        Log.m98i(TAG, "getState mState : " + this.mState);
        return this.mState;
    }

    private void SendHandlerMessage(int type) {
        Handler handler = this.handler;
        if (handler != null) {
            Message msg = handler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("commandType", type);
            msg.setData(b);
            if (type == 2) {
                Log.m94d(TAG, "sendMessageDelayed : 1500");
                this.handler.sendMessageDelayed(msg, 1500L);
            } else {
                Log.m94d(TAG, "sendMessageDelayed : 700");
                this.handler.sendMessageDelayed(msg, 700L);
            }
        }
    }

    private void start(int commandType) {
        String str = TAG;
        Log.m98i(str, "start");
        if (isEnabled(commandType)) {
            this.mState = 1;
            Log.m94d(str, "mState change to : " + this.mState);
            if (this.mStopHandler == null) {
                this.mStopHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.speech.SemSpeechRecognizer.1
                    @Override // android.p009os.Handler
                    public void handleMessage(Message msg) {
                        Log.m96e(SemSpeechRecognizer.TAG, "audio is halt without stopRecognition()");
                        SemSpeechRecognizer.this.stopRecognition();
                    }
                };
                Log.m94d(str, "StopHandler create");
            }
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.speech.SemSpeechRecognizer.2
                    @Override // android.p009os.Handler
                    public void handleMessage(Message msg) {
                        int result = msg.getData().getInt("commandType");
                        SemSpeechRecognizer semSpeechRecognizer = SemSpeechRecognizer.this;
                        semSpeechRecognizer.delayedStartBargeIn(result, semSpeechRecognizer.mStopHandler);
                    }
                };
                Log.m94d(str, "handler create");
            }
            SendHandlerMessage(commandType);
        }
    }

    public void startRecognition(int commandType) {
        String str = TAG;
        Log.m98i(str, "startRecognition");
        Log.m98i(str, "commandType : " + commandType);
        this.intBargeInResult = -1;
        setLanguage();
        start(commandType);
    }

    public void startRecognition(int commandType, int setLang) {
        String str = TAG;
        Log.m98i(str, "startRecognition Type2");
        Log.m98i(str, "commandType : " + commandType);
        Log.m98i(str, "setLanguage : " + setLang);
        this.intBargeInResult = -1;
        this.uselanguage = setLang;
        start(commandType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delayedStartBargeIn(int commandType, Handler stopHandler) {
        String str = TAG;
        Log.m98i(str, "delayedStartBargeIn");
        synchronized (this) {
            if (this.audio != null) {
                Log.m102w(str, "BargeIn is running. So Do nothing");
                this.audio.BargeinAct[0] = -1;
            } else {
                if (isPDTModel()) {
                    Log.m94d(str, "Load PDTAudioTask");
                    this.audio = new PDTAudioTask(this.mListener, Config.DEFAULT_PATH, commandType, this.uselanguage, this.samsungOOVResult);
                } else {
                    Log.m94d(str, "Load OEMAudioTask");
                }
                AudioTask audioTask = this.audio;
                if (audioTask != null && audioTask.rec != null) {
                    this.audio.setHandler(stopHandler);
                    Thread thread = new Thread(this.audio);
                    this.audio_thread = thread;
                    thread.start();
                    this.mState = 1;
                    Log.m94d(str, "mState change to : " + this.mState);
                } else {
                    Log.m96e(str, "fail to running Bargein");
                    AudioTask audioTask2 = this.audio;
                    if (audioTask2 != null) {
                        audioTask2.stop();
                    }
                    if (this.audio_thread != null) {
                        Log.m96e(str, "why running empty audio_thread");
                    }
                    this.audio = null;
                }
            }
        }
    }

    public void stopRecognition() {
        String str = TAG;
        Log.m98i(str, "stopRecognition");
        synchronized (this) {
            if (this.isEnableBargeIn) {
                Handler handler = this.handler;
                if (handler != null) {
                    handler.removeMessages(0);
                    this.handler = null;
                    Log.m94d(str, "handler = null");
                }
                Handler handler2 = this.mStopHandler;
                if (handler2 != null) {
                    handler2.removeMessages(0);
                    this.mStopHandler = null;
                    Log.m94d(str, "Stop Handler = null");
                }
                AudioTask audioTask = this.audio;
                if (audioTask != null) {
                    this.intBargeInResult = audioTask.BargeinAct[0];
                    this.audio.stop();
                    if (this.audio_thread != null) {
                        try {
                            Log.m94d(str, "wait for audio to stop: begin");
                            this.audio_thread.join(700L);
                            this.audio.stopPhraseSpotter();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        Log.m94d(str, "audio_thread was not working");
                    }
                    String str2 = TAG;
                    Log.m94d(str2, "wait for audio to stop: end");
                    this.audio = null;
                    Log.m94d(str2, "audio = null");
                }
                this.audio_thread = null;
                String str3 = TAG;
                Log.m94d(str3, "audio_thread = null");
                this.mState = 0;
                Log.m94d(str3, "mState change to : " + this.mState);
            }
        }
    }

    private boolean isUseModel() {
        if (isPDTModel()) {
            Log.m98i(TAG, "use libVoiceCommandEngine.so");
            return true;
        }
        if (isSamsungModel()) {
            this.samsungOOVResult = true;
            return true;
        }
        if (isOEMModel()) {
            this.samsungOOVResult = false;
            Log.m98i(TAG, "Could not find libsasr-jni.so use only libOemBargeInEngine.so");
            return true;
        }
        Log.m96e(TAG, "Error : Could not find libsasr-jni.so && libOemBargeInEngine.so");
        return false;
    }

    private static boolean isSamsungModel() {
        return isBargeInFile(Config.SAMSUNG_SO_FILE_PATH) && isBargeInFile(Config.GetSamsungModels(1)) && isBargeInFile(Config.GetSamsungModels(0));
    }

    private static boolean isPDTModel() {
        if (isBargeInFile(Config.PDT_SO_FILE_PATH) || isBargeInFile(Config.PDT_SO_FILE_PATH_64)) {
            return true;
        }
        return false;
    }

    private static boolean isOEMModel() {
        if (isBargeInFile(Config.OEM_SO_FILE_PATH) || isBargeInFile(Config.OEM_SO_FILE_PATH_64)) {
            return true;
        }
        return false;
    }

    public boolean isChineseMode() {
        return isPDTModel() || isBargeInFile(Config.GetSamsungModels(2));
    }

    public String getBargeInCmdLanguage() {
        switch (this.uselanguage) {
        }
        return "en-US";
    }

    public String[] getCommandStringArray(int CommandType) {
        return getCommandStringArray(CommandType, this.uselanguage);
    }

    public String[] getCommandStringArray(int CommandType, int Language) {
        String str = TAG;
        Log.m98i(str, "getCommandStringArray : CommandType ( " + CommandType + " ) Language ( " + Language + " )");
        if (Language >= 15) {
            Language = 1;
        }
        if (!isEnabled(CommandType, Language)) {
            Language = 1;
            Log.m98i(str, "getCommandStringArray : possible language is ( 1 )");
        }
        switch (CommandType) {
            case 2:
                if (isPDTModel()) {
                    return CommandLanguage.CALL_PDT[Language];
                }
                return CommandLanguage.CALL[Language];
            case 3:
                return CommandLanguage.ALARM[Language];
            case 4:
                return CommandLanguage.MUSIC[Language];
            case 5:
            case 6:
            case 8:
            default:
                return null;
            case 7:
                return CommandLanguage.CAMERA[Language];
            case 9:
                return CommandLanguage.CANCEL[Language];
        }
    }

    public int getCommandLanguage() {
        Log.m98i(TAG, "getCommandLanguage : " + this.uselanguage);
        return this.uselanguage;
    }

    private void setLanguage() {
        String defaultLanguage;
        String stringLanguage;
        String stringCountry;
        Locale currentLocale = Locale.getDefault();
        if (currentLocale == null) {
            defaultLanguage = "en_US";
            stringLanguage = "en";
            stringCountry = "US";
        } else {
            defaultLanguage = currentLocale.toString();
            stringLanguage = currentLocale.getLanguage();
            stringCountry = currentLocale.getCountry();
        }
        String str = TAG;
        Log.m98i(str, "stringLanguage : " + stringLanguage);
        Log.m98i(str, "stringCountry : " + stringCountry);
        if (stringLanguage != null) {
            if (stringLanguage.equals(Locale.KOREA.getLanguage())) {
                this.uselanguage = 0;
                return;
            }
            if (stringLanguage.equals(Locale.US.getLanguage())) {
                if (stringCountry.equals("GB")) {
                    this.uselanguage = 10;
                    return;
                } else {
                    this.uselanguage = 1;
                    return;
                }
            }
            if (stringLanguage.equals(Locale.CHINA.getLanguage()) && this.isEnableChineseBargeIn) {
                if (stringCountry.equals("CN")) {
                    this.uselanguage = 2;
                    return;
                }
                if (stringCountry.equals("TW")) {
                    this.uselanguage = 12;
                    return;
                }
                if (stringCountry.equals("HK")) {
                    this.uselanguage = 13;
                    return;
                } else if (stringCountry.equals("SG")) {
                    this.uselanguage = 14;
                    return;
                } else {
                    this.uselanguage = 1;
                    return;
                }
            }
            if (stringCountry.equals("ES")) {
                this.uselanguage = 3;
                if (!this.isEnableExtraSpanish && !stringLanguage.equals("es")) {
                    this.uselanguage = 1;
                    return;
                } else {
                    Log.m98i(str, "Extra Sapnish is enabled : " + defaultLanguage);
                    return;
                }
            }
            if (stringLanguage.equals("es")) {
                this.uselanguage = 11;
                return;
            }
            if (stringLanguage.equals(Locale.FRANCE.getLanguage())) {
                this.uselanguage = 4;
                return;
            }
            if (stringLanguage.equals(Locale.GERMAN.getLanguage())) {
                this.uselanguage = 5;
                return;
            }
            if (stringLanguage.equals(Locale.ITALY.getLanguage())) {
                this.uselanguage = 6;
                return;
            }
            if (stringLanguage.equals(Locale.JAPAN.getLanguage())) {
                this.uselanguage = 7;
                return;
            }
            if (stringLanguage.equals("ru")) {
                this.uselanguage = 8;
                return;
            }
            if (stringLanguage.equals("pt")) {
                if (stringCountry.equals("BR")) {
                    this.uselanguage = 9;
                    return;
                } else {
                    this.uselanguage = 1;
                    return;
                }
            }
            if (this.isEnableExtraRussian) {
                if (defaultLanguage.contains("az_AZ") || defaultLanguage.contains("kk_KZ") || defaultLanguage.contains("uz_UZ") || defaultLanguage.equals("ky_KZ") || defaultLanguage.equals("tg_TJ") || defaultLanguage.equals("tk_TM") || defaultLanguage.equals("be_BY")) {
                    this.uselanguage = 8;
                    Log.m98i(str, "Extra Russian is enabled : " + defaultLanguage);
                    return;
                } else {
                    this.uselanguage = 1;
                    return;
                }
            }
            this.uselanguage = 1;
        }
    }

    public int getRecognitionResult() {
        synchronized (this) {
            AudioTask audioTask = this.audio;
            if (audioTask != null) {
                return audioTask.BargeinAct[0];
            }
            return this.intBargeInResult;
        }
    }

    private String readString(String filePath) {
        File mFile = new File(filePath);
        FileInputStream mFileInputStream = null;
        if (!mFile.exists()) {
            return null;
        }
        try {
            mFileInputStream = new FileInputStream(mFile);
            byte[] data = new byte[mFileInputStream.available()];
            mFileInputStream.read(data);
            mFileInputStream.close();
            return new String(data);
        } catch (IOException e) {
            if (mFileInputStream != null) {
                try {
                    mFileInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
            return null;
        }
    }

    public boolean isEnabled() {
        return this.isEnableBargeIn;
    }

    public boolean isEnabled(int commandType) {
        int language = this.uselanguage;
        if (isEnabled(commandType, language)) {
            return true;
        }
        if (language != 1) {
            this.uselanguage = 1;
            return isEnabled(commandType, 1);
        }
        return false;
    }

    public boolean isEnabled(int commandType, int language) {
        if (isPDTModel()) {
            String PDTModelPath = Config.GetPDTAM(language, commandType);
            if (!isBargeInFile(PDTModelPath + ".bin")) {
                return false;
            }
            Log.m98i(TAG, "isEnabled: PDTBargeIn is available in commandType (" + commandType + ") uselanguage(" + language + NavigationBarInflaterView.KEY_CODE_END);
            return true;
        }
        if (isOEMModel()) {
            String OEMModelPath = Config.GetOEMAM(language, commandType);
            if (isBargeInFile(OEMModelPath + ".bin")) {
                Log.m98i(TAG, "isEnabled: OEMBargeIn is available in commandType (" + commandType + ") uselanguage(" + language + NavigationBarInflaterView.KEY_CODE_END);
                return true;
            }
        }
        if (isSamsungModel()) {
            String samsungModelPath = Config.GetSamsungModels(language);
            String samsungNameList = Config.GetSamsungPath(language) + Config.GetSamsungNameList(commandType);
            if (isBargeInFile(samsungModelPath) && isBargeInFile(samsungNameList)) {
                Log.m98i(TAG, "isEnabled: SamsungBargeIn is available in commandType (" + commandType + ") uselanguage(" + language + NavigationBarInflaterView.KEY_CODE_END);
                return true;
            }
        }
        String samsungModelPath2 = TAG;
        Log.m102w(samsungModelPath2, "isEnabled: BargeIn is not available in commandType (" + commandType + ") uselanguage(" + language + NavigationBarInflaterView.KEY_CODE_END);
        return false;
    }

    private static boolean isBargeInFile(String mFilePath) {
        if (new File(mFilePath).exists()) {
            return true;
        }
        return false;
    }
}
