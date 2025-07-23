package com.samsung.android.speech;

import android.media.AudioRecord;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.samsung.android.speech.SemSpeechRecognizer;
import com.samsung.voicebargein.BargeInEngine;
import com.samsung.voicebargein.BargeInEngineWrapper;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes6.dex */
class PDTAudioTask extends AudioTask implements Runnable {
    static final int DEFAULT_BLOCK_SIZE = 320;
    private int AUDIO_START;
    public float CMscore;
    private final int RECOGNITION_WAIT_TIME;
    private String TAG;
    public double THscore;
    private BargeInEngine aPDTBargeInEngine;
    private String acousticModelPathname;
    public int block_size;
    public byte[] buf;
    public long consoleInitReturn;
    private boolean done;
    private int dualThresholdFlag;
    public File f;
    private Handler handler;
    public boolean isCameraBargeIn;
    public boolean isCancelBargeIn;
    private boolean isMakePCM;
    public boolean isPDTBargeInEnable;
    public boolean isSensoryResult;
    public String loadPath;
    public int mCommandType;
    public DataOutputStream mDataOutputStream;
    public int mLanguage;
    public String mLocale;
    public Handler mStopHandler;
    private SemSpeechRecognizer.ResultListener m_listener;
    public int numRecogResult;
    public LinkedBlockingQueue<short[]> q;
    private int readNshorts;
    private int recogAfterReadCount;
    public short[] speech;
    private int totalReadCount;

    PDTAudioTask(SemSpeechRecognizer.ResultListener resultListener, String str, int i, int i2, boolean z) {
        super(resultListener, str, i, i2, z);
        this.TAG = "PDTAudioTask";
        this.q = null;
        this.block_size = 0;
        this.done = false;
        this.aPDTBargeInEngine = null;
        this.consoleInitReturn = -1L;
        this.numRecogResult = 0;
        this.CMscore = 0.0f;
        this.speech = null;
        this.isMakePCM = false;
        this.m_listener = null;
        this.loadPath = null;
        this.mCommandType = 0;
        this.mLanguage = 1;
        this.mLocale = Config.GetLocale(1);
        this.totalReadCount = 0;
        this.AUDIO_START = 0;
        this.recogAfterReadCount = 0;
        this.RECOGNITION_WAIT_TIME = 50;
        this.f = null;
        this.mDataOutputStream = null;
        this.THscore = -1.5d;
        this.acousticModelPathname = Config.GetPDTAM(0, 2) + ".bin";
        this.isPDTBargeInEnable = false;
        this.isCameraBargeIn = false;
        this.isCancelBargeIn = false;
        this.readNshorts = -1;
        this.isSensoryResult = false;
        this.mStopHandler = null;
        this.dualThresholdFlag = 0;
        this.handler = new Handler() { // from class: com.samsung.android.speech.PDTAudioTask.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                String[] stringArray = message.getData().getStringArray("recognition_result");
                if (PDTAudioTask.this.m_listener != null) {
                    PDTAudioTask.this.m_listener.onResults(stringArray);
                }
            }
        };
        init(new LinkedBlockingQueue<>(), 320, resultListener, str, i, i2, z);
    }

    @Override // com.samsung.android.speech.AudioTask
    void init(LinkedBlockingQueue<short[]> linkedBlockingQueue, int i, SemSpeechRecognizer.ResultListener resultListener, String str, int i2, int i3, boolean z) {
        this.TAG = "PDTAudioTask";
        Log.i("PDTAudioTask", "PDTAudioTask init()");
        Log.i(this.TAG, "command : " + i2);
        Log.i(this.TAG, "Language : " + i3);
        this.done = false;
        this.q = linkedBlockingQueue;
        this.block_size = i;
        this.mCommandType = i2;
        this.rec = null;
        this.m_listener = resultListener;
        this.loadPath = str;
        this.mLanguage = i3;
        this.mLocale = Config.GetLocale(i3);
        this.BargeinAct[0] = -1;
        if (i2 == 7 && i3 == 0) {
            this.dualThresholdFlag = -1;
        }
        setPDTFilePath(i3, i2);
        this.speech = new short[320];
        Log.i(this.TAG, "isPDTBargeInEnable : " + this.isPDTBargeInEnable);
        this.totalReadCount = 0;
        this.recogAfterReadCount = 0;
        if (this.isMakePCM) {
            this.f = new File("/sdcard/Documents/", "testPCM.pcm");
            try {
                this.mDataOutputStream = new DataOutputStream(new FileOutputStream(this.f, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (this.isCameraBargeIn || this.isCancelBargeIn) {
            this.AUDIO_START = 0;
            this.rec = getAudioRecord(this.AUDIO_RECORD_FOR_VOICE_RECOGNITION);
            if (this.rec != null) {
                Log.d(this.TAG, "new AudioRecord : " + this.AUDIO_RECORD_FOR_VOICE_RECOGNITION);
            }
        } else if (this.isPDTBargeInEnable) {
            this.AUDIO_START = 50;
            this.rec = getAudioRecord(this.AUDIO_RECORD_FOR_BARGE_IN_OEM);
            if (this.rec != null) {
                Log.d(this.TAG, "new AudioRecord : " + this.AUDIO_RECORD_FOR_BARGE_IN_OEM);
            }
        }
        if (this.rec == null) {
            this.rec = getAudioRecord(this.AUDIO_RECORD_FOR_BARGE_IN);
            Log.d(this.TAG, "new AudioRecord : " + this.AUDIO_RECORD_FOR_BARGE_IN);
        }
        if (this.isPDTBargeInEnable) {
            BargeInEngine bargeInEngineWrapper = BargeInEngineWrapper.getInstance();
            this.aPDTBargeInEngine = bargeInEngineWrapper;
            if (bargeInEngineWrapper != null) {
                this.consoleInitReturn = bargeInEngineWrapper.phrasespotInit(this.acousticModelPathname, this.mLocale);
            } else {
                Log.e(this.TAG, "BargeInEngineWrapper.getInstance() is null");
            }
        }
    }

    @Override // com.samsung.android.speech.AudioTask
    public void stop() {
        Log.i(this.TAG, "PDTAudioTask : stop start");
        this.mStopHandler = null;
        this.done = true;
        this.readNshorts = -1;
        Log.i(this.TAG, "PDTAudioTask : stop end");
    }

    @Override // com.samsung.android.speech.AudioTask
    public void stopBargeInAudioRecord() {
        DataOutputStream dataOutputStream;
        Log.i(this.TAG, "stopBargeInAudioRecord start");
        if (this.rec != null) {
            if (this.isMakePCM && (dataOutputStream = this.mDataOutputStream) != null) {
                try {
                    dataOutputStream.flush();
                    this.mDataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d(this.TAG, "Call rec.stop start");
            this.rec.stop();
            Log.d(this.TAG, "Call rec.stop end");
            Log.d(this.TAG, "Call rec.release start");
            this.rec.release();
            Log.d(this.TAG, "Call rec.release end");
            this.rec = null;
            Log.d(this.TAG, "rec = null");
        }
        Log.i(this.TAG, "stopBargeInAudioRecord end");
    }

    @Override // com.samsung.android.speech.AudioTask, java.lang.Runnable
    public void run() {
        Handler handler;
        Log.d(this.TAG, "PDTAudioTask run() ");
        if (this.rec != null) {
            Log.d(this.TAG, "Call rec.startRecording start");
            this.rec.startRecording();
            Log.d(this.TAG, "Call startRecording end");
            while (!this.done) {
                readShortBlock();
                if (this.done || this.readNshorts <= 0) {
                    break;
                }
            }
        } else {
            Log.e(this.TAG, "Bargein fail to start");
        }
        Log.i(this.TAG, "run end");
        if (this.done || (handler = this.mStopHandler) == null) {
            return;
        }
        handler.sendEmptyMessage(0);
    }

    @Override // com.samsung.android.speech.AudioTask
    public void stopPhraseSpotter() {
        stopBargeInAudioRecord();
        if (this.aPDTBargeInEngine != null) {
            Log.i(this.TAG, "PDT phrasespotClose start");
            long j = this.consoleInitReturn;
            if (j != -1) {
                this.aPDTBargeInEngine.phrasespotClose(j);
            }
            Log.i(this.TAG, "PDT phrasespotClose end");
        }
        this.aPDTBargeInEngine = null;
        this.m_listener = null;
        Log.d(this.TAG, "aPDTBargeInEngine = null");
        Log.d(this.TAG, "m_listener = null");
    }

    int readShortBlock() {
        if (this.done) {
            Log.e(this.TAG, "readByteBlock return -1 : Section1 ");
            this.readNshorts = -1;
            return -1;
        }
        int i = 0;
        if (this.rec != null && !this.done) {
            AudioRecord audioRecord = this.rec;
            short[] sArr = this.speech;
            this.readNshorts = audioRecord.read(sArr, 0, sArr.length);
        }
        if (this.done) {
            Log.e(this.TAG, "readByteBlock return -1 : Section2 ");
            this.readNshorts = -1;
            return -1;
        }
        if (this.readNshorts < 320) {
            Log.e(this.TAG, "AudioRecord Read problem : nshorts = " + this.readNshorts + " command = " + this.mCommandType + " language : " + this.mLanguage);
        }
        if (this.totalReadCount % 20 == 0) {
            Log.d(this.TAG, "nshorts = " + (this.readNshorts * 10) + " command = " + this.mCommandType + " language : " + this.mLanguage + " dualThr : " + this.dualThresholdFlag);
        }
        int i2 = this.totalReadCount + 1;
        this.totalReadCount = i2;
        int i3 = this.recogAfterReadCount;
        if (i3 != 0) {
            this.recogAfterReadCount = (i3 + 1) % 50;
        }
        boolean z = this.done;
        if (z) {
            Log.e(this.TAG, "readByteBlock return -1 : Section3 ");
            this.readNshorts = -1;
            return -1;
        }
        if (this.readNshorts <= 0) {
            Log.i(this.TAG, "readNshorts is " + this.readNshorts + " So do nothing ");
        } else {
            if (z) {
                Log.e(this.TAG, "readByteBlock return -1 : Section4 ");
                this.readNshorts = -1;
                return -1;
            }
            if (this.isPDTBargeInEnable) {
                if (z) {
                    Log.e(this.TAG, "readByteBlock return -1 : Section5 ");
                    this.readNshorts = -1;
                    return -1;
                }
                if (this.aPDTBargeInEngine != null && i2 > this.AUDIO_START) {
                    getPDTRecognitionResult(this.consoleInitReturn, this.speech);
                }
            }
            if (this.isMakePCM) {
                AudioTask.swap(this.speech);
                while (true) {
                    try {
                        short[] sArr2 = this.speech;
                        if (i >= sArr2.length) {
                            break;
                        }
                        this.mDataOutputStream.writeShort(sArr2[i]);
                        i++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return this.readNshorts;
    }

    private boolean getPDTRecognitionResult(long j, short[] sArr) {
        float[] fArr = new float[3];
        String phrasespotPipe = this.aPDTBargeInEngine.phrasespotPipe(j, sArr, 320L, 16000L, fArr);
        if (phrasespotPipe != null) {
            this.BargeinAct[0] = (short) getPDTBargeInAct(this.mCommandType, phrasespotPipe);
            this.strResult[0] = phrasespotPipe;
            float f = fArr[0];
            Log.i(this.TAG, "consoleResult : " + phrasespotPipe);
            Log.d(this.TAG, "strResult[0] : " + this.strResult[0]);
            Log.d(this.TAG, "BargeinAct[0] : " + ((int) this.BargeinAct[0]));
            Log.i(this.TAG, "CMscore : " + f);
            if (!this.isCameraBargeIn) {
                SendHandlerMessage(this.strResult);
                return true;
            }
            if (this.recogAfterReadCount == 0) {
                this.recogAfterReadCount = 1;
                SendHandlerMessage(this.strResult);
                return true;
            }
        }
        return false;
    }

    private void SendHandlerMessage(String[] strArr) {
        Message obtainMessage = this.handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putStringArray("recognition_result", strArr);
        obtainMessage.setData(bundle);
        try {
            this.handler.sendMessage(obtainMessage);
        } catch (IllegalStateException e) {
            Log.e(this.TAG, "IllegalStateException " + e.getMessage());
            stop();
        }
    }

    private void setPDTFilePath(int i, int i2) {
        String str = Config.GetPDTAM(i, i2) + ".bin";
        if (isBargeInFile(Config.PDT_SO_FILE_PATH) || isBargeInFile(Config.PDT_SO_FILE_PATH_64)) {
            this.isPDTBargeInEnable = true;
            this.acousticModelPathname = str;
        }
        int i3 = this.mCommandType;
        if (i3 != 7) {
            if (i3 == 9) {
                this.isCancelBargeIn = true;
            }
        } else {
            this.isCameraBargeIn = true;
            if (this.isPDTBargeInEnable) {
                this.isPDTBargeInEnable = true;
            }
        }
    }

    public boolean isPDTBargeinEnabled() {
        return this.isPDTBargeInEnable;
    }

    private int getPDTBargeInAct(int i, String str) {
        switch (i) {
            case 0:
            case 1:
            case 2:
                if (str.startsWith("Answer")) {
                    return 1;
                }
                return str.startsWith("Reject") ? 2 : -1;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                if (str.startsWith("Smile")) {
                    return 1;
                }
                if (str.startsWith("Cheese")) {
                    return 2;
                }
                if (str.startsWith("Capture")) {
                    return 3;
                }
                if (str.startsWith("Shoot")) {
                    return 4;
                }
                if (str.startsWith("Record Video") || str.startsWith("Record_Video") || str.startsWith("RecordVideo")) {
                    return 5;
                }
                if (str.startsWith("auto settings") || str.startsWith("auto_settings") || str.startsWith("autosettings")) {
                    return 6;
                }
                if (str.startsWith("beauty face") || str.startsWith("beauty_face") || str.startsWith("beautyface")) {
                    return 7;
                }
                if (str.startsWith("timer")) {
                    return 8;
                }
                if (str.startsWith("zoom in") || str.startsWith("zoom_in") || str.startsWith("zoomin")) {
                    return 9;
                }
                if (str.startsWith("zoom out") || str.startsWith("zoom_out") || str.startsWith("zoomout")) {
                    return 10;
                }
                if (str.startsWith("flash on") || str.startsWith("flash_on") || str.startsWith("flashon")) {
                    return 11;
                }
                if (str.startsWith("flash off") || str.startsWith("flash_off") || str.startsWith("flashoff")) {
                    return 12;
                }
                if (str.startsWith("upload pics") || str.startsWith("upload_pics") || str.startsWith("uploadpics")) {
                    return 13;
                }
                return str.startsWith("gallery") ? 14 : -1;
            default:
                return -1;
        }
    }
}
