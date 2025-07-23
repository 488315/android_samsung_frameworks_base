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
    private static final String TAG = "SemSpeechRecognizer";
    private final String SVOICE_LANGUAGE_FILE;
    private AudioTask audio;
    private Thread audio_thread;
    private Handler handler;
    private int intBargeInResult;
    private boolean isCallStopRecognition;
    public boolean isEnableBargeIn;
    private boolean isEnableChineseBargeIn;
    private boolean isEnableExtraRussian;
    private boolean isEnableExtraSpanish;
    private boolean isStartBargeIn;
    private Context mContext;
    private ResultListener mListener;
    private int mState;
    private Handler mStopHandler;
    private boolean samsungOOVResult;
    private int uselanguage;

    public interface ResultListener {
        void onResults(String[] strArr);
    }

    public SemSpeechRecognizer() {
        this.audio = null;
        this.audio_thread = null;
        this.mListener = null;
        this.mState = 0;
        this.isEnableBargeIn = false;
        this.isEnableChineseBargeIn = false;
        this.isEnableExtraSpanish = false;
        this.isEnableExtraRussian = false;
        this.samsungOOVResult = false;
        this.intBargeInResult = -1;
        this.uselanguage = 1;
        this.handler = null;
        this.mStopHandler = null;
        this.SVOICE_LANGUAGE_FILE = "/data/data/com.vlingo.midas/files/language.bin";
        this.mContext = null;
        init();
    }

    public SemSpeechRecognizer(Context context) {
        this.audio = null;
        this.audio_thread = null;
        this.mListener = null;
        this.mState = 0;
        this.isEnableBargeIn = false;
        this.isEnableChineseBargeIn = false;
        this.isEnableExtraSpanish = false;
        this.isEnableExtraRussian = false;
        this.samsungOOVResult = false;
        this.intBargeInResult = -1;
        this.uselanguage = 1;
        this.handler = null;
        this.mStopHandler = null;
        this.SVOICE_LANGUAGE_FILE = "/data/data/com.vlingo.midas/files/language.bin";
        this.mContext = context;
        Log.i(TAG, "BargeInRecognizer get Context " + this.mContext);
        init();
    }

    private void init() {
        String str = TAG;
        Log.i(str, "make new SemSpeechRecognizer VER 18.11.13");
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
        Log.i(str, "isEnableBargeIn : " + this.isEnableBargeIn);
        Log.i(str, "uselanguage : " + this.uselanguage);
        Log.i(str, "isEnableChineseBargeIn : " + this.isEnableChineseBargeIn);
        Log.i(str, "isEnableExtraSpanish : " + this.isEnableExtraSpanish);
        Log.i(str, "isEnableExtraRussian : " + this.isEnableExtraRussian);
    }

    public void setListener(ResultListener resultListener) {
        this.mListener = resultListener;
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

    private void SendHandlerMessage(int i) {
        Handler handler = this.handler;
        if (handler != null) {
            Message obtainMessage = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putInt("commandType", i);
            obtainMessage.setData(bundle);
            if (i == 2) {
                Log.d(TAG, "sendMessageDelayed : 1500");
                this.handler.sendMessageDelayed(obtainMessage, 1500L);
            } else {
                Log.d(TAG, "sendMessageDelayed : 700");
                this.handler.sendMessageDelayed(obtainMessage, 700L);
            }
        }
    }

    private void start(int i) {
        String str = TAG;
        Log.i(str, "start");
        if (isEnabled(i)) {
            this.mState = 1;
            Log.d(str, "mState change to : " + this.mState);
            if (this.mStopHandler == null) {
                this.mStopHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.speech.SemSpeechRecognizer.1
                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        Log.e(SemSpeechRecognizer.TAG, "audio is halt without stopRecognition()");
                        SemSpeechRecognizer.this.stopRecognition();
                    }
                };
                Log.d(str, "StopHandler create");
            }
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.speech.SemSpeechRecognizer.2
                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        int i2 = message.getData().getInt("commandType");
                        SemSpeechRecognizer semSpeechRecognizer = SemSpeechRecognizer.this;
                        semSpeechRecognizer.delayedStartBargeIn(i2, semSpeechRecognizer.mStopHandler);
                    }
                };
                Log.d(str, "handler create");
            }
            this.isCallStopRecognition = false;
            this.isStartBargeIn = false;
            SendHandlerMessage(i);
        }
    }

    public void startRecognition(int i) {
        String str = TAG;
        Log.i(str, "startRecognition");
        Log.i(str, "commandType : " + i);
        this.intBargeInResult = -1;
        setLanguage();
        start(i);
    }

    public void startRecognition(int i, int i2) {
        String str = TAG;
        Log.i(str, "startRecognition Type2");
        Log.i(str, "commandType : " + i);
        Log.i(str, "setLanguage : " + i2);
        this.intBargeInResult = -1;
        this.uselanguage = i2;
        start(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delayedStartBargeIn(int i, Handler handler) {
        String str = TAG;
        Log.i(str, "delayedStartBargeIn");
        synchronized (this) {
            if (this.isCallStopRecognition) {
                Log.i(str, "Stop load PDTAudioTask after when stopRecognition() call first");
                this.isCallStopRecognition = false;
                return;
            }
            if (this.audio != null) {
                Log.w(str, "BargeIn is running. So Do nothing");
                this.audio.BargeinAct[0] = -1;
            } else {
                if (isPDTModel()) {
                    Log.i(str, "Load PDTAudioTask");
                    this.audio = new PDTAudioTask(this.mListener, Config.DEFAULT_PATH, i, this.uselanguage, this.samsungOOVResult);
                } else {
                    Log.i(str, "Load OEMAudioTask");
                }
                AudioTask audioTask = this.audio;
                if (audioTask != null && audioTask.rec != null) {
                    this.audio.setHandler(handler);
                    Thread thread = new Thread(this.audio);
                    this.audio_thread = thread;
                    thread.start();
                    this.mState = 1;
                    Log.i(str, "mState change to : " + this.mState);
                } else {
                    Log.e(str, "fail to running Bargein");
                    AudioTask audioTask2 = this.audio;
                    if (audioTask2 != null) {
                        audioTask2.stop();
                    }
                    if (this.audio_thread != null) {
                        Log.e(str, "why running empty audio_thread");
                    }
                    this.audio = null;
                }
            }
            this.isStartBargeIn = true;
            this.isCallStopRecognition = false;
        }
    }

    public void stopRecognition() {
        String str = TAG;
        Log.i(str, "stopRecognition");
        if (!this.isStartBargeIn) {
            this.isCallStopRecognition = true;
        }
        synchronized (this) {
            if (this.isEnableBargeIn) {
                Handler handler = this.handler;
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                    this.handler = null;
                    Log.i(str, "handler removeCallbacksAndMessages; handler = null");
                }
                Handler handler2 = this.mStopHandler;
                if (handler2 != null) {
                    handler2.removeCallbacksAndMessages(null);
                    this.mStopHandler = null;
                    Log.i(str, "mStopHandler removeCallbacksAndMessages; Stop Handler = null");
                }
                AudioTask audioTask = this.audio;
                if (audioTask != null) {
                    this.intBargeInResult = audioTask.BargeinAct[0];
                    this.audio.stop();
                    if (this.audio_thread != null) {
                        try {
                            Log.d(str, "wait for audio to stop: begin");
                            this.audio_thread.join(700L);
                            this.audio.stopPhraseSpotter();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d(str, "audio_thread was not working");
                    }
                    String str2 = TAG;
                    Log.d(str2, "wait for audio to stop: end");
                    this.audio = null;
                    Log.d(str2, "audio = null");
                }
                this.audio_thread = null;
                String str3 = TAG;
                Log.d(str3, "audio_thread = null");
                this.mState = 0;
                Log.d(str3, "mState change to : " + this.mState);
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
        return isBargeInFile(Config.PDT_SO_FILE_PATH) || isBargeInFile(Config.PDT_SO_FILE_PATH_64);
    }

    private static boolean isOEMModel() {
        return isBargeInFile(Config.OEM_SO_FILE_PATH) || isBargeInFile(Config.OEM_SO_FILE_PATH_64);
    }

    public boolean isChineseMode() {
        return isPDTModel() || isBargeInFile(Config.GetSamsungModels(2));
    }

    public String getBargeInCmdLanguage() {
        int i = this.uselanguage;
        if (i == 0) {
            return "ko-KR";
        }
        switch (i) {
            case 2:
                return "zh-CN";
            case 3:
                return "es-ES";
            case 4:
                return "fr-FR";
            case 5:
                return "de-DE";
            case 6:
                return "it-IT";
            case 7:
                return "ja-JP";
            case 8:
                return "ru-RU";
            case 9:
                return "pt-BR";
            case 10:
                return "en-GB";
            case 11:
                return "v-es-LA";
            case 12:
                return "zh-TW";
            case 13:
                return "zh-HK";
            default:
                return "en-US";
        }
    }

    public String[] getCommandStringArray(int i) {
        return getCommandStringArray(i, this.uselanguage);
    }

    public String[] getCommandStringArray(int i, int i2) {
        String str = TAG;
        Log.i(str, "getCommandStringArray : CommandType ( " + i + " ) Language ( " + i2 + " )");
        int i3 = 1;
        if (i2 >= 15) {
            i2 = 1;
        }
        if (isEnabled(i, i2)) {
            i3 = i2;
        } else {
            Log.i(str, "getCommandStringArray : possible language is ( 1 )");
        }
        if (i == 2) {
            if (isPDTModel()) {
                return CommandLanguage.CALL_PDT[i3];
            }
            return CommandLanguage.CALL[i3];
        }
        if (i == 3) {
            return CommandLanguage.ALARM[i3];
        }
        if (i == 4) {
            return CommandLanguage.MUSIC[i3];
        }
        if (i == 7) {
            return CommandLanguage.CAMERA[i3];
        }
        if (i != 9) {
            return null;
        }
        return CommandLanguage.CANCEL[i3];
    }

    public int getCommandLanguage() {
        Log.i(TAG, "getCommandLanguage : " + this.uselanguage);
        return this.uselanguage;
    }

    private void setLanguage() {
        String country;
        String str;
        String str2;
        Locale locale = Locale.getDefault();
        if (locale == null) {
            str = "en_US";
            str2 = "en";
            country = "US";
        } else {
            String locale2 = locale.toString();
            String language = locale.getLanguage();
            country = locale.getCountry();
            str = locale2;
            str2 = language;
        }
        String str3 = TAG;
        Log.i(str3, "stringLanguage : " + str2);
        Log.i(str3, "stringCountry : " + country);
        if (str2 != null) {
            if (str2.equals(Locale.KOREA.getLanguage())) {
                this.uselanguage = 0;
                return;
            }
            if (str2.equals(Locale.US.getLanguage())) {
                if (country.equals("GB")) {
                    this.uselanguage = 10;
                    return;
                } else {
                    this.uselanguage = 1;
                    return;
                }
            }
            if (str2.equals(Locale.CHINA.getLanguage()) && this.isEnableChineseBargeIn) {
                if (country.equals("CN")) {
                    this.uselanguage = 2;
                    return;
                }
                if (country.equals("TW")) {
                    this.uselanguage = 12;
                    return;
                }
                if (country.equals("HK")) {
                    this.uselanguage = 13;
                    return;
                } else if (country.equals("SG")) {
                    this.uselanguage = 14;
                    return;
                } else {
                    this.uselanguage = 1;
                    return;
                }
            }
            if (country.equals("ES")) {
                this.uselanguage = 3;
                if (!this.isEnableExtraSpanish && !str2.equals("es")) {
                    this.uselanguage = 1;
                    return;
                }
                Log.i(str3, "Extra Sapnish is enabled : " + str);
                return;
            }
            if (str2.equals("es")) {
                this.uselanguage = 11;
                return;
            }
            if (str2.equals(Locale.FRANCE.getLanguage())) {
                this.uselanguage = 4;
                return;
            }
            if (str2.equals(Locale.GERMAN.getLanguage())) {
                this.uselanguage = 5;
                return;
            }
            if (str2.equals(Locale.ITALY.getLanguage())) {
                this.uselanguage = 6;
                return;
            }
            if (str2.equals(Locale.JAPAN.getLanguage())) {
                this.uselanguage = 7;
                return;
            }
            if (str2.equals("ru")) {
                this.uselanguage = 8;
                return;
            }
            if (str2.equals("pt")) {
                if (country.equals("BR")) {
                    this.uselanguage = 9;
                    return;
                } else {
                    this.uselanguage = 1;
                    return;
                }
            }
            if (this.isEnableExtraRussian) {
                if (str.contains("az_AZ") || str.contains("kk_KZ") || str.contains("uz_UZ") || str.equals("ky_KZ") || str.equals("tg_TJ") || str.equals("tk_TM") || str.equals("be_BY")) {
                    this.uselanguage = 8;
                    Log.i(str3, "Extra Russian is enabled : " + str);
                    return;
                }
                this.uselanguage = 1;
                return;
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

    private String readString(String str) {
        FileInputStream fileInputStream;
        File file = new File(str);
        if (file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    byte[] bArr = new byte[fileInputStream.available()];
                    fileInputStream.read(bArr);
                    fileInputStream.close();
                    return new String(bArr);
                } catch (IOException e) {
                    e = e;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    e.printStackTrace();
                    return null;
                }
            } catch (IOException e3) {
                e = e3;
                fileInputStream = null;
            }
        }
        return null;
    }

    public boolean isEnabled() {
        return this.isEnableBargeIn;
    }

    public boolean isEnabled(int i) {
        int i2 = this.uselanguage;
        if (isEnabled(i, i2)) {
            return true;
        }
        if (i2 == 1) {
            return false;
        }
        this.uselanguage = 1;
        return isEnabled(i, 1);
    }

    public boolean isEnabled(int i, int i2) {
        if (!isPDTModel() || i != 7 || i2 >= 15) {
            return false;
        }
        Log.i(TAG, "isEnabled: PDTBargeIn is available in commandType (" + i + ") uselanguage(" + i2 + NavigationBarInflaterView.KEY_CODE_END);
        return true;
    }

    private static boolean isBargeInFile(String str) {
        return new File(str).exists();
    }
}
