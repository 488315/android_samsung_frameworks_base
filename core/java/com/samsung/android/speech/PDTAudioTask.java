package com.samsung.android.speech;

import android.media.AudioRecord;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Message;
import android.util.Log;
import com.samsung.voicebargein.BargeInEngine;
import com.samsung.voicebargein.BargeInEngineWrapper;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes5.dex */
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

  /* renamed from: f */
  public File f3048f;
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

  /* renamed from: q */
  public LinkedBlockingQueue<short[]> f3049q;
  private int readNshorts;
  private int recogAfterReadCount;
  public short[] speech;
  private int totalReadCount;

  PDTAudioTask(
      SemSpeechRecognizer.ResultListener listener,
      String path,
      int command,
      int language,
      boolean samsungOOVResult) {
    super(listener, path, command, language, samsungOOVResult);
    this.TAG = PDTAudioTask.class.getSimpleName();
    this.f3049q = null;
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
    this.f3048f = null;
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
    this.handler =
        new Handler() { // from class: com.samsung.android.speech.PDTAudioTask.1
          @Override // android.p009os.Handler
          public void handleMessage(Message msg) {
            String[] result = msg.getData().getStringArray("recognition_result");
            if (PDTAudioTask.this.m_listener != null) {
              PDTAudioTask.this.m_listener.onResults(result);
            }
          }
        };
    init(new LinkedBlockingQueue<>(), 320, listener, path, command, language, samsungOOVResult);
  }

  @Override // com.samsung.android.speech.AudioTask
  void init(
      LinkedBlockingQueue<short[]> q,
      int block_size,
      SemSpeechRecognizer.ResultListener listener,
      String path,
      int command,
      int Language,
      boolean samsungOOVResult) {
    String simpleName = PDTAudioTask.class.getSimpleName();
    this.TAG = simpleName;
    Log.m98i(simpleName, "PDTAudioTask init()");
    Log.m98i(this.TAG, "command : " + command);
    Log.m98i(this.TAG, "Language : " + Language);
    this.done = false;
    this.f3049q = q;
    this.block_size = block_size;
    this.mCommandType = command;
    this.rec = null;
    this.m_listener = listener;
    this.loadPath = path;
    this.mLanguage = Language;
    this.mLocale = Config.GetLocale(Language);
    this.BargeinAct[0] = -1;
    if (command == 7 && Language == 0) {
      this.dualThresholdFlag = -1;
    }
    setPDTFilePath(Language, command);
    this.speech = new short[320];
    Log.m98i(this.TAG, "isPDTBargeInEnable : " + this.isPDTBargeInEnable);
    this.totalReadCount = 0;
    this.recogAfterReadCount = 0;
    if (this.isMakePCM) {
      this.f3048f = new File("/sdcard/Documents/", "testPCM.pcm");
      try {
        this.mDataOutputStream = new DataOutputStream(new FileOutputStream(this.f3048f, true));
      } catch (FileNotFoundException e1) {
        e1.printStackTrace();
      }
    }
    if (this.isCameraBargeIn || this.isCancelBargeIn) {
      this.AUDIO_START = 0;
      this.rec = getAudioRecord(this.AUDIO_RECORD_FOR_VOICE_RECOGNITION);
      if (this.rec != null) {
        Log.m94d(this.TAG, "new AudioRecord : " + this.AUDIO_RECORD_FOR_VOICE_RECOGNITION);
      }
    } else if (this.isPDTBargeInEnable) {
      this.AUDIO_START = 50;
      this.rec = getAudioRecord(this.AUDIO_RECORD_FOR_BARGE_IN_OEM);
      if (this.rec != null) {
        Log.m94d(this.TAG, "new AudioRecord : " + this.AUDIO_RECORD_FOR_BARGE_IN_OEM);
      }
    }
    if (this.rec == null) {
      this.rec = getAudioRecord(this.AUDIO_RECORD_FOR_BARGE_IN);
      Log.m94d(this.TAG, "new AudioRecord : " + this.AUDIO_RECORD_FOR_BARGE_IN);
    }
    if (this.isPDTBargeInEnable) {
      BargeInEngine bargeInEngineWrapper = BargeInEngineWrapper.getInstance();
      this.aPDTBargeInEngine = bargeInEngineWrapper;
      if (bargeInEngineWrapper != null) {
        this.consoleInitReturn =
            bargeInEngineWrapper.phrasespotInit(this.acousticModelPathname, this.mLocale);
      } else {
        Log.m96e(this.TAG, "BargeInEngineWrapper.getInstance() is null");
      }
    }
  }

  @Override // com.samsung.android.speech.AudioTask
  public void stop() {
    Log.m98i(this.TAG, "PDTAudioTask : stop start");
    this.mStopHandler = null;
    this.done = true;
    this.readNshorts = -1;
    Log.m98i(this.TAG, "PDTAudioTask : stop end");
  }

  @Override // com.samsung.android.speech.AudioTask
  public void stopBargeInAudioRecord() {
    DataOutputStream dataOutputStream;
    Log.m98i(this.TAG, "stopBargeInAudioRecord start");
    if (this.rec != null) {
      if (this.isMakePCM && (dataOutputStream = this.mDataOutputStream) != null) {
        try {
          dataOutputStream.flush();
          this.mDataOutputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      Log.m94d(this.TAG, "Call rec.stop start");
      this.rec.stop();
      Log.m94d(this.TAG, "Call rec.stop end");
      Log.m94d(this.TAG, "Call rec.release start");
      this.rec.release();
      Log.m94d(this.TAG, "Call rec.release end");
      this.rec = null;
      Log.m94d(this.TAG, "rec = null");
    }
    Log.m98i(this.TAG, "stopBargeInAudioRecord end");
  }

  @Override // com.samsung.android.speech.AudioTask, java.lang.Runnable
  public void run() {
    Handler handler;
    Log.m94d(this.TAG, "PDTAudioTask run() ");
    if (this.rec != null) {
      Log.m94d(this.TAG, "Call rec.startRecording start");
      this.rec.startRecording();
      Log.m94d(this.TAG, "Call startRecording end");
      while (!this.done) {
        readShortBlock();
        if (this.done || this.readNshorts <= 0) {
          break;
        }
      }
    } else {
      Log.m96e(this.TAG, "Bargein fail to start");
    }
    Log.m98i(this.TAG, "run end");
    if (!this.done && (handler = this.mStopHandler) != null) {
      handler.sendEmptyMessage(0);
    }
  }

  @Override // com.samsung.android.speech.AudioTask
  public void stopPhraseSpotter() {
    stopBargeInAudioRecord();
    if (this.aPDTBargeInEngine != null) {
      Log.m98i(this.TAG, "PDT phrasespotClose start");
      long j = this.consoleInitReturn;
      if (j != -1) {
        this.aPDTBargeInEngine.phrasespotClose(j);
      }
      Log.m98i(this.TAG, "PDT phrasespotClose end");
    }
    this.aPDTBargeInEngine = null;
    this.m_listener = null;
    Log.m94d(this.TAG, "aPDTBargeInEngine = null");
    Log.m94d(this.TAG, "m_listener = null");
  }

  int readShortBlock() {
    if (this.done) {
      Log.m96e(this.TAG, "readByteBlock return -1 : Section1 ");
      this.readNshorts = -1;
      return -1;
    }
    if (this.rec != null && !this.done) {
      AudioRecord audioRecord = this.rec;
      short[] sArr = this.speech;
      this.readNshorts = audioRecord.read(sArr, 0, sArr.length);
    }
    if (this.done) {
      Log.m96e(this.TAG, "readByteBlock return -1 : Section2 ");
      this.readNshorts = -1;
      return -1;
    }
    if (this.readNshorts < 320) {
      Log.m96e(
          this.TAG,
          "AudioRecord Read problem : nshorts = "
              + this.readNshorts
              + " command = "
              + this.mCommandType
              + " language : "
              + this.mLanguage);
    }
    if (this.totalReadCount % 20 == 0) {
      Log.m94d(
          this.TAG,
          "nshorts = "
              + (this.readNshorts * 10)
              + " command = "
              + this.mCommandType
              + " language : "
              + this.mLanguage
              + " dualThr : "
              + this.dualThresholdFlag);
    }
    int i = this.totalReadCount + 1;
    this.totalReadCount = i;
    int i2 = this.recogAfterReadCount;
    if (i2 != 0) {
      this.recogAfterReadCount = (i2 + 1) % 50;
    }
    boolean z = this.done;
    if (z) {
      Log.m96e(this.TAG, "readByteBlock return -1 : Section3 ");
      this.readNshorts = -1;
      return -1;
    }
    if (this.readNshorts > 0) {
      if (z) {
        Log.m96e(this.TAG, "readByteBlock return -1 : Section4 ");
        this.readNshorts = -1;
        return -1;
      }
      if (this.isPDTBargeInEnable) {
        if (z) {
          Log.m96e(this.TAG, "readByteBlock return -1 : Section5 ");
          this.readNshorts = -1;
          return -1;
        }
        if (this.aPDTBargeInEngine != null && i > this.AUDIO_START) {
          getPDTRecognitionResult(this.consoleInitReturn, this.speech);
        }
      }
      if (this.isMakePCM) {
        AudioTask.swap(this.speech);
        int i3 = 0;
        while (true) {
          try {
            short[] sArr2 = this.speech;
            if (i3 >= sArr2.length) {
              break;
            }
            this.mDataOutputStream.writeShort(sArr2[i3]);
            i3++;
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    } else {
      Log.m98i(this.TAG, "readNshorts is " + this.readNshorts + " So do nothing ");
    }
    return this.readNshorts;
  }

  private boolean getPDTRecognitionResult(long consoleInitReturn, short[] speech) {
    float[] ResultValue = new float[3];
    String consoleResult =
        this.aPDTBargeInEngine.phrasespotPipe(consoleInitReturn, speech, 320L, 16000L, ResultValue);
    if (consoleResult != null) {
      this.BargeinAct[0] = (short) getPDTBargeInAct(this.mCommandType, consoleResult);
      this.strResult[0] = consoleResult;
      float CMscore = ResultValue[0];
      Log.m98i(this.TAG, "consoleResult : " + consoleResult);
      Log.m94d(this.TAG, "strResult[0] : " + this.strResult[0]);
      Log.m94d(this.TAG, "BargeinAct[0] : " + ((int) this.BargeinAct[0]));
      Log.m98i(this.TAG, "CMscore : " + CMscore);
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

  private void SendHandlerMessage(String[] result) {
    Message msg = this.handler.obtainMessage();
    Bundle b = new Bundle();
    b.putStringArray("recognition_result", result);
    msg.setData(b);
    try {
      this.handler.sendMessage(msg);
    } catch (IllegalStateException e) {
      Log.m96e(this.TAG, "IllegalStateException " + e.getMessage());
      stop();
    }
  }

  private void setPDTFilePath(int language, int domain) {
    String PDTModelPath = Config.GetPDTAM(language, domain) + ".bin";
    if ((isBargeInFile(Config.PDT_SO_FILE_PATH) || isBargeInFile(Config.PDT_SO_FILE_PATH_64))
        && isBargeInFile(PDTModelPath)) {
      this.isPDTBargeInEnable = true;
      this.acousticModelPathname = PDTModelPath;
    }
    int i = this.mCommandType;
    if (i == 7) {
      this.isCameraBargeIn = true;
      if (this.isPDTBargeInEnable) {
        this.isPDTBargeInEnable = true;
        return;
      }
      return;
    }
    if (i == 9) {
      this.isCancelBargeIn = true;
    }
  }

  public boolean isPDTBargeinEnabled() {
    return this.isPDTBargeInEnable;
  }

  private int getPDTBargeInAct(int domain, String result) {
    switch (domain) {
      case 0:
      case 1:
      case 2:
        if (result.startsWith("Answer")) {
          return 1;
        }
        if (result.startsWith("Reject")) {
          return 2;
        }
        return -1;
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
        if (result.startsWith("Smile")) {
          return 1;
        }
        if (result.startsWith("Cheese")) {
          return 2;
        }
        if (result.startsWith("Capture")) {
          return 3;
        }
        if (result.startsWith("Shoot")) {
          return 4;
        }
        if (result.startsWith("Record Video")
            || result.startsWith("Record_Video")
            || result.startsWith("RecordVideo")) {
          return 5;
        }
        if (result.startsWith("auto settings")
            || result.startsWith("auto_settings")
            || result.startsWith("autosettings")) {
          return 6;
        }
        if (result.startsWith("beauty face")
            || result.startsWith("beauty_face")
            || result.startsWith("beautyface")) {
          return 7;
        }
        if (result.startsWith("timer")) {
          return 8;
        }
        if (result.startsWith("zoom in")
            || result.startsWith("zoom_in")
            || result.startsWith("zoomin")) {
          return 9;
        }
        if (result.startsWith("zoom out")
            || result.startsWith("zoom_out")
            || result.startsWith("zoomout")) {
          return 10;
        }
        if (result.startsWith("flash on")
            || result.startsWith("flash_on")
            || result.startsWith("flashon")) {
          return 11;
        }
        if (result.startsWith("flash off")
            || result.startsWith("flash_off")
            || result.startsWith("flashoff")) {
          return 12;
        }
        if (result.startsWith("upload pics")
            || result.startsWith("upload_pics")
            || result.startsWith("uploadpics")) {
          return 13;
        }
        if (result.startsWith("gallery")) {
          return 14;
        }
        return -1;
      default:
        return -1;
    }
  }
}
