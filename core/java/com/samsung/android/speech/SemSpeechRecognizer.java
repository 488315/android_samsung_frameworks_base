package com.samsung.android.speech;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

/* loaded from: classes6.dex */
public class SemSpeechRecognizer {
    public static final int STATE_READY = 0;
    public static final int STATE_RUNNING = 1;
    private static final String TAG = SemSpeechRecognizer.class.getSimpleName();
    private boolean isCallStopRecognition;
    private boolean isStartBargeIn;
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
        Log.i(TAG, "BargeInRecognizer get Context " + this.mContext);
        init();
    }

    private void init() {
        Log.i(TAG, "make new SemSpeechRecognizer VER 18.11.13");
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
        Log.i(TAG, "isEnableBargeIn : " + this.isEnableBargeIn);
        Log.i(TAG, "uselanguage : " + this.uselanguage);
        Log.i(TAG, "isEnableChineseBargeIn : " + this.isEnableChineseBargeIn);
        Log.i(TAG, "isEnableExtraSpanish : " + this.isEnableExtraSpanish);
        Log.i(TAG, "isEnableExtraRussian : " + this.isEnableExtraRussian);
    }

    public void setListener(ResultListener listener) {
        this.mListener = listener;
        this.mState = 0;
    }

    public void setContext(Context context) {
        Log.i(TAG, "setContext");
        this.mContext = context;
    }

    public int getState() {
        Log.i(TAG, "getState mState : " + this.mState);
        return this.mState;
    }

    private void SendHandlerMessage(int type) {
        if (this.handler != null) {
            Message msg = this.handler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("commandType", type);
            msg.setData(b);
            if (type == 2) {
                Log.d(TAG, "sendMessageDelayed : 1500");
                this.handler.sendMessageDelayed(msg, 1500L);
            } else {
                Log.d(TAG, "sendMessageDelayed : 700");
                this.handler.sendMessageDelayed(msg, 700L);
            }
        }
    }

    private void start(int commandType) {
        Log.i(TAG, "start");
        if (isEnabled(commandType)) {
            this.mState = 1;
            Log.d(TAG, "mState change to : " + this.mState);
            if (this.mStopHandler == null) {
                this.mStopHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.speech.SemSpeechRecognizer.1
                    @Override // android.os.Handler
                    public void handleMessage(Message msg) {
                        Log.e(SemSpeechRecognizer.TAG, "audio is halt without stopRecognition()");
                        SemSpeechRecognizer.this.stopRecognition();
                    }
                };
                Log.d(TAG, "StopHandler create");
            }
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.speech.SemSpeechRecognizer.2
                    @Override // android.os.Handler
                    public void handleMessage(Message msg) {
                        int result = msg.getData().getInt("commandType");
                        SemSpeechRecognizer.this.delayedStartBargeIn(result, SemSpeechRecognizer.this.mStopHandler);
                    }
                };
                Log.d(TAG, "handler create");
            }
            this.isCallStopRecognition = false;
            this.isStartBargeIn = false;
            SendHandlerMessage(commandType);
        }
    }

    public void startRecognition(int commandType) {
        Log.i(TAG, "startRecognition");
        Log.i(TAG, "commandType : " + commandType);
        this.intBargeInResult = -1;
        setLanguage();
        start(commandType);
    }

    public void startRecognition(int commandType, int setLang) {
        Log.i(TAG, "startRecognition Type2");
        Log.i(TAG, "commandType : " + commandType);
        Log.i(TAG, "setLanguage : " + setLang);
        this.intBargeInResult = -1;
        this.uselanguage = setLang;
        start(commandType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delayedStartBargeIn(int commandType, Handler stopHandler) {
        Log.i(TAG, "delayedStartBargeIn");
        synchronized (this) {
            if (this.isCallStopRecognition) {
                Log.i(TAG, "Stop load PDTAudioTask after when stopRecognition() call first");
                this.isCallStopRecognition = false;
                return;
            }
            if (this.audio != null) {
                Log.w(TAG, "BargeIn is running. So Do nothing");
                this.audio.BargeinAct[0] = -1;
            } else {
                if (isPDTModel()) {
                    Log.i(TAG, "Load PDTAudioTask");
                    this.audio = new PDTAudioTask(this.mListener, Config.DEFAULT_PATH, commandType, this.uselanguage, this.samsungOOVResult);
                } else {
                    Log.i(TAG, "Load OEMAudioTask");
                }
                if (this.audio != null && this.audio.rec != null) {
                    this.audio.setHandler(stopHandler);
                    this.audio_thread = new Thread(this.audio);
                    this.audio_thread.start();
                    this.mState = 1;
                    Log.i(TAG, "mState change to : " + this.mState);
                } else {
                    Log.e(TAG, "fail to running Bargein");
                    if (this.audio != null) {
                        this.audio.stop();
                    }
                    if (this.audio_thread != null) {
                        Log.e(TAG, "why running empty audio_thread");
                    }
                    this.audio = null;
                }
            }
            this.isStartBargeIn = true;
            this.isCallStopRecognition = false;
        }
    }

    public void stopRecognition() {
        Log.i(TAG, "stopRecognition");
        if (!this.isStartBargeIn) {
            this.isCallStopRecognition = true;
        }
        synchronized (this) {
            if (this.isEnableBargeIn) {
                if (this.handler != null) {
                    this.handler.removeCallbacksAndMessages(null);
                    this.handler = null;
                    Log.i(TAG, "handler removeCallbacksAndMessages; handler = null");
                }
                if (this.mStopHandler != null) {
                    this.mStopHandler.removeCallbacksAndMessages(null);
                    this.mStopHandler = null;
                    Log.i(TAG, "mStopHandler removeCallbacksAndMessages; Stop Handler = null");
                }
                if (this.audio != null) {
                    this.intBargeInResult = this.audio.BargeinAct[0];
                    this.audio.stop();
                    if (this.audio_thread != null) {
                        try {
                            Log.d(TAG, "wait for audio to stop: begin");
                            this.audio_thread.join(700L);
                            this.audio.stopPhraseSpotter();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        Log.d(TAG, "audio_thread was not working");
                    }
                    Log.d(TAG, "wait for audio to stop: end");
                    this.audio = null;
                    Log.d(TAG, "audio = null");
                }
                this.audio_thread = null;
                Log.d(TAG, "audio_thread = null");
                this.mState = 0;
                Log.d(TAG, "mState change to : " + this.mState);
            }
        }
    }

    private boolean isUseModel() {
        if (isPDTModel()) {
            Log.i(TAG, "use libVoiceCommandEngine.so");
            return true;
        }
        if (isSamsungModel()) {
            this.samsungOOVResult = true;
            return true;
        }
        if (isOEMModel()) {
            this.samsungOOVResult = false;
            Log.i(TAG, "Could not find libsasr-jni.so use only libOemBargeInEngine.so");
            return true;
        }
        Log.e(TAG, "Error : Could not find libsasr-jni.so && libOemBargeInEngine.so");
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
        Log.i(TAG, "getCommandStringArray : CommandType ( " + CommandType + " ) Language ( " + Language + " )");
        if (Language >= 15) {
            Language = 1;
        }
        if (!isEnabled(CommandType, Language)) {
            Language = 1;
            Log.i(TAG, "getCommandStringArray : possible language is ( 1 )");
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
        Log.i(TAG, "getCommandLanguage : " + this.uselanguage);
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
        Log.i(TAG, "stringLanguage : " + stringLanguage);
        Log.i(TAG, "stringCountry : " + stringCountry);
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
                    Log.i(TAG, "Extra Sapnish is enabled : " + defaultLanguage);
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
                    Log.i(TAG, "Extra Russian is enabled : " + defaultLanguage);
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
            if (this.audio != null) {
                return this.audio.BargeinAct[0];
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
            return isEnabled(commandType, this.uselanguage);
        }
        return false;
    }

    public boolean isEnabled(int commandType, int language) {
        if (isPDTModel() && commandType == 7 && language < 15) {
            Log.i(TAG, "isEnabled: PDTBargeIn is available in commandType (" + commandType + ") uselanguage(" + language + NavigationBarInflaterView.KEY_CODE_END);
            return true;
        }
        return false;
    }

    private static boolean isBargeInFile(String mFilePath) {
        if (new File(mFilePath).exists()) {
            return true;
        }
        return false;
    }
}
