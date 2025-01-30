package com.samsung.android.media.codec;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetadataRetriever;
import android.p009os.Build;
import android.p009os.IBinder;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.media.mediacapture.SemMediaCapture;
import com.samsung.android.media.mediacapture.SemMediaCapture.DynamicViewingConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes5.dex */
public class SemVideoTranscodingService {
  public static final String KEY_CHANGE_SPEED_END_MS = "change-speed-end-ms";
  public static final String KEY_CHANGE_SPEED_RATE = "change-speed-rate";
  public static final String KEY_CHANGE_SPEED_START_MS = "change-speed-start-ms";
  public static final String KEY_END_MS = "end-ms";
  public static final String KEY_INPUT_PATH = "input-path";
  public static final String KEY_OUTPUT_PATH = "output-path";
  public static final String KEY_START_MS = "start-ms";
  private static final int RECORDING_MODE_SLOW_MOTION = 1;
  private static final int RECORDING_MODE_SLOW_MOTION_V2 = 12;
  private static final int RECORDING_MODE_SLOW_MOTION_V2_120 = 13;
  private static final int RECORDING_MODE_SLOW_MOTION_V2_HEVC = 21;
  private static final int RECORDING_MODE_SLOW_MOTION_V2_WITHOUT_SVC = 15;
  private static final int RECORDING_MODE_SLOW_MOTION_V2_WITHOUT_SVC_240 = 19;
  private static final String TAG = "SemVideoTranscodingService";
  public static final int TRANSCODING_MODE_HDR_TO_SDR = 0;
  public static final int TRANSCODING_MODE_INSTANT_SLOW_MOTION = 200;
  public static final int TRANSCODING_MODE_INSTANT_SLOW_MOTION_WITH_HDR2SDR = 201;
  public static final int TRANSCODING_MODE_P3_TO_SRGB = 100;
  public static final int TRANSCODING_MODE_SLOW_MOTION_TO_NORMAL = 1;
  private IVideoTranscodingService mService;

  public static class ProgressCallback extends IVideoTranscodingServiceCallback.Stub {
    private Client mClient;

    public void setClient(Client client) {
      this.mClient = client;
    }

    @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
    public void onReady() throws RemoteException {
      this.mClient.transcode();
    }

    @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
    public void onStarted() throws RemoteException {}

    @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
    public void onProgressChanged(int i) throws RemoteException {}

    @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
    public void onCompleted() throws RemoteException {}

    @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
    public void onError() throws RemoteException {}
  }

  public static class Client {
    private Map mArgs;
    private final SemMediaCapture mCapture;
    private FileInputStream mFis;
    private FileOutputStream mFos;
    private final String mID;
    private boolean mIgnoreError;
    private boolean mIsRunning;
    private final int mMode;
    private final ProgressCallback mProgressCallback;
    private final SemVideoTranscoder mTranscoder;
    private final IVideoTranscodingService mTranscodingService;

    private class TranscodingThread extends Thread {
      private static final String THREAD_PREFIX = "transcoding";

      public TranscodingThread() {
        setName(THREAD_PREFIX + Client.this.mID);
      }

      @Override // java.lang.Thread, java.lang.Runnable
      public void run() {
        String inputPath;
        String outputPath;
        boolean isValid;
        int dstVideoCodecType;
        int i;
        super.run();
        Log.m98i("SemVideoTranscodingService", getName() + " is running");
        try {
          try {
            try {
              inputPath = (String) Client.this.mArgs.get(SemVideoTranscodingService.KEY_INPUT_PATH);
              outputPath =
                  (String) Client.this.mArgs.get(SemVideoTranscodingService.KEY_OUTPUT_PATH);
            } catch (Exception e) {
              e.printStackTrace();
              Log.m102w(
                  "SemVideoTranscodingService",
                  "Task(" + Client.this.mID + ") has been terminated unexpectedly");
              if (Client.this.mIgnoreError) {
                Log.m98i(
                    "SemVideoTranscodingService",
                    "Client has stopped " + Client.this.mID + ", Ignore this error.");
              } else {
                try {
                  Client.this.mProgressCallback.onError();
                } catch (RemoteException re) {
                  re.printStackTrace();
                }
              }
              Client.this.mIsRunning = false;
              Client.this.mTranscodingService.stopTask(Client.this.mID);
            }
            if (Client.this.mMode != 0 && Client.this.mMode != 1) {
              if (Client.this.mMode != 200 && Client.this.mMode != 201) {
                switch (Client.this.mMode) {
                  case 100:
                    Client.this.mIsRunning = true;
                    Client.this.mProgressCallback.onStarted();
                    boolean ret = ImgCsConverter.convertToSRGB(inputPath, outputPath);
                    if (!ret) {
                      Client.this.mProgressCallback.onError();
                      break;
                    } else {
                      Client.this.mProgressCallback.onProgressChanged(100);
                      Client.this.mProgressCallback.onCompleted();
                      break;
                    }
                  default:
                    Log.m102w(
                        "SemVideoTranscodingService",
                        "Unsupported mode ("
                            + Client.this.mMode
                            + NavigationBarInflaterView.KEY_CODE_END);
                    Client.this.mProgressCallback.onError();
                    break;
                }
                Client.this.mIsRunning = false;
                Client.this.mTranscodingService.stopTask(Client.this.mID);
              }
              Client.this.mIsRunning = true;
              while (Client.this.mIsRunning) {
                Client.this.mProgressCallback.onProgressChanged(
                    (int) (Client.this.mCapture.getProgressForCapture() * 100.0f));
                sleep(100L);
              }
              Client.this.mIsRunning = false;
              Client.this.mTranscodingService.stopTask(Client.this.mID);
            }
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(inputPath);
            String width = retriever.extractMetadata(18);
            String height = retriever.extractMetadata(19);
            String hdr10bit = retriever.extractMetadata(1027);
            String bitDepth = retriever.extractMetadata(1028);
            String recording_mode = retriever.extractMetadata(1022);
            retriever.release();
            Log.m94d(
                "SemVideoTranscodingService",
                "    METADATA_KEY_VIDEO_WIDTH[" + width + NavigationBarInflaterView.SIZE_MOD_END);
            Log.m94d(
                "SemVideoTranscodingService",
                "    METADATA_KEY_VIDEO_HEIGHT[" + height + NavigationBarInflaterView.SIZE_MOD_END);
            Log.m94d(
                "SemVideoTranscodingService",
                "    SEM_METADATA_KEY_HDR10_VIDEO["
                    + hdr10bit
                    + NavigationBarInflaterView.SIZE_MOD_END);
            Log.m94d(
                "SemVideoTranscodingService",
                "    SEM_METADATA_KEY_VIDEO_BIT_DEPTH["
                    + bitDepth
                    + NavigationBarInflaterView.SIZE_MOD_END);
            Log.m94d(
                "SemVideoTranscodingService",
                "    SEM_METADATA_KEY_RECORDINGMODE["
                    + recording_mode
                    + NavigationBarInflaterView.SIZE_MOD_END);
            int w = Integer.parseInt(width);
            int h = Integer.parseInt(height);
            switch (Client.this.mMode) {
              case 0:
                if (SemVideoTranscodingService.isSupportedHdrToSdr()
                    && hdr10bit != null
                    && bitDepth != null) {
                  int bit = Integer.parseInt(bitDepth);
                  if (hdr10bit.equals("yes") && bit > 8) {
                    isValid = true;
                    dstVideoCodecType = 4;
                    break;
                  }
                }
                dstVideoCodecType = 4;
                isValid = false;
                break;
              case 1:
                if (recording_mode != null
                    && ((i = Integer.parseInt(recording_mode)) == 1
                        || i == 12
                        || i == 13
                        || i == 15
                        || i == 19
                        || i == 21)) {
                  int dstVideoCodecType2 = i == 21 ? 5 : 4;
                  isValid = true;
                  dstVideoCodecType = dstVideoCodecType2;
                  break;
                }
                dstVideoCodecType = 4;
                isValid = false;
                break;
              default:
                Log.m102w(
                    "SemVideoTranscodingService",
                    "Unsupported mode ("
                        + Client.this.mMode
                        + NavigationBarInflaterView.KEY_CODE_END);
                dstVideoCodecType = 4;
                isValid = false;
            }
            if (isValid) {
              Client.this.mTranscoder.initialize(outputPath, w, h, inputPath);
              Client.this.mTranscoder.setVideoTranscodingServiceCallback(
                  Client.this.mProgressCallback);
              Client.this.mTranscoder.setOutputConfig(1, dstVideoCodecType);
              Client.this.mTranscoder.setOutputConfig(2, 2);
              if (Client.this.mMode == 0) {
                Client.this.mTranscoder.setOutputConfig(4, 8);
              }
              Client.this.mIsRunning = true;
              Client.this.mTranscoder.encode();
              Log.m98i(
                  "SemVideoTranscodingService", "Task(" + Client.this.mID + ") has been finished");
            } else {
              Log.m102w("SemVideoTranscodingService", "Invalid argument");
              Client.this.mProgressCallback.onError();
            }
            Client.this.mIsRunning = false;
            Client.this.mTranscodingService.stopTask(Client.this.mID);
          } finally {
          }
        } catch (RemoteException e2) {
          e2.printStackTrace();
        }
      }
    }

    public Client(
        IVideoTranscodingService transcodingService,
        String id,
        int mode,
        Map args,
        ProgressCallback progressCallback) {
      this.mTranscodingService = transcodingService;
      this.mID = id;
      this.mMode = mode;
      this.mArgs = args;
      this.mProgressCallback = progressCallback;
      progressCallback.setClient(this);
      if (mode == 0 || mode == 1) {
        this.mTranscoder = new SemVideoTranscoder();
        this.mCapture = null;
      } else if (mode == 200 || mode == 201) {
        this.mTranscoder = null;
        this.mCapture = new SemMediaCapture();
      } else {
        this.mTranscoder = null;
        this.mCapture = null;
      }
      this.mFis = null;
      this.mFos = null;
      this.mIsRunning = false;
      this.mIgnoreError = false;
    }

    public void start() {
      try {
        this.mTranscodingService.startTask(this.mID);
      } catch (RemoteException e) {
        Log.m96e("SemVideoTranscodingService", "Exception startTask()");
        e.printStackTrace();
      }
    }

    public void stop() {
      this.mIgnoreError = true;
      if (this.mIsRunning) {
        Log.m98i(
            "SemVideoTranscodingService",
            "stop running client id(" + this.mID + NavigationBarInflaterView.KEY_CODE_END);
        int i = this.mMode;
        if (i == 0 || i == 1) {
          this.mTranscoder.stop();
          try {
            Thread.sleep(1000L);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } else if (i == 200 || i == 201) {
          this.mCapture.stopCapture();
          try {
            Thread.sleep(1000L);
          } catch (InterruptedException e2) {
            e2.printStackTrace();
          }
          this.mCapture.reset();
          this.mCapture.release();
          try {
            FileInputStream fileInputStream = this.mFis;
            if (fileInputStream != null) {
              fileInputStream.close();
              this.mFis = null;
            }
            FileOutputStream fileOutputStream = this.mFos;
            if (fileOutputStream != null) {
              fileOutputStream.close();
              this.mFos = null;
            }
          } catch (IOException ie) {
            ie.printStackTrace();
          }
        }
        this.mIsRunning = false;
      }
      try {
        this.mTranscodingService.stopTask(this.mID);
      } catch (RemoteException e3) {
        Log.m96e("SemVideoTranscodingService", "Exception startTask()");
        e3.printStackTrace();
      }
    }

    public void transcode() {
      try {
        int i = this.mMode;
        if (i != 200 && i != 201) {
          TranscodingThread transcodingThread = new TranscodingThread();
          transcodingThread.start();
          return;
        }
        this.mCapture.setOnPreparedListener(
            new SemMediaCapture
                .OnPreparedListener() { // from class:
                                        // com.samsung.android.media.codec.SemVideoTranscodingService.Client.1
              @Override // com.samsung.android.media.mediacapture.SemMediaCapture.OnPreparedListener
              public void onPrepared(SemMediaCapture semMediaCapture) {
                Log.m94d("SemVideoTranscodingService", "onPrepared() " + Client.this.mID);
                Client.this.mCapture.startCapture();
                TranscodingThread transcodingThread2 = Client.this.new TranscodingThread();
                transcodingThread2.start();
              }
            });
        this.mCapture.setOnRecordingCompletionListener(
            new SemMediaCapture
                .OnRecordingCompletionListener() { // from class:
                                                   // com.samsung.android.media.codec.SemVideoTranscodingService.Client.2
              @Override // com.samsung.android.media.mediacapture.SemMediaCapture.OnRecordingCompletionListener
              public void onRecordingCompletion(SemMediaCapture semMediaCapture) {
                Log.m94d(
                    "SemVideoTranscodingService", "onRecordingCompletion() " + Client.this.mID);
                try {
                  Client.this.mIsRunning = false;
                  Client.this.mCapture.reset();
                  Client.this.mCapture.release();
                  try {
                    if (Client.this.mFis != null) {
                      Client.this.mFis.close();
                      Client.this.mFis = null;
                    }
                    if (Client.this.mFos != null) {
                      Client.this.mFos.close();
                      Client.this.mFos = null;
                    }
                  } catch (IOException ie) {
                    ie.printStackTrace();
                  }
                  Client.this.mProgressCallback.onProgressChanged(100);
                  Client.this.mProgressCallback.onCompleted();
                } catch (RemoteException re) {
                  re.printStackTrace();
                }
              }
            });
        this.mCapture.setOnErrorListener(
            new SemMediaCapture
                .OnErrorListener() { // from class:
                                     // com.samsung.android.media.codec.SemVideoTranscodingService.Client.3
              @Override // com.samsung.android.media.mediacapture.SemMediaCapture.OnErrorListener
              public boolean onError(SemMediaCapture semMediaCapture, int i2, int i1) {
                Log.m94d("SemVideoTranscodingService", "onError() " + Client.this.mID);
                Client.this.mIsRunning = false;
                Client.this.mCapture.reset();
                Client.this.mCapture.release();
                try {
                  if (Client.this.mFis != null) {
                    Client.this.mFis.close();
                    Client.this.mFis = null;
                  }
                  if (Client.this.mFos != null) {
                    Client.this.mFos.close();
                    Client.this.mFos = null;
                  }
                } catch (IOException ie) {
                  ie.printStackTrace();
                }
                if (Client.this.mIgnoreError) {
                  Log.m98i(
                      "SemVideoTranscodingService",
                      "Client has stopped " + Client.this.mID + ", Ignore this error.");
                } else {
                  try {
                    Client.this.mProgressCallback.onError();
                    Client.this.mTranscodingService.stopTask(Client.this.mID);
                  } catch (RemoteException re) {
                    re.printStackTrace();
                  }
                }
                return false;
              }
            });
        String inputPath = (String) this.mArgs.get(SemVideoTranscodingService.KEY_INPUT_PATH);
        FileInputStream fileInputStream = new FileInputStream(new File(inputPath));
        this.mFis = fileInputStream;
        this.mCapture.setDataSource(fileInputStream.getFD());
        String outputPath = (String) this.mArgs.get(SemVideoTranscodingService.KEY_OUTPUT_PATH);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outputPath));
        this.mFos = fileOutputStream;
        this.mCapture.setOutputFile(fileOutputStream.getFD());
        this.mCapture.setParameter(1006, 1);
        this.mCapture.setParameter(1010, 1);
        this.mCapture.setParameter(1011, 2);
        this.mCapture.setParameter(1012, 89);
        if (this.mMode == 201) {
          this.mCapture.setParameter(1013, 1);
        }
        int changeSpeedStartMs =
            ((Integer) this.mArgs.get(SemVideoTranscodingService.KEY_CHANGE_SPEED_START_MS))
                .intValue();
        int changeSpeedEndMs =
            ((Integer) this.mArgs.get(SemVideoTranscodingService.KEY_CHANGE_SPEED_END_MS))
                .intValue();
        float changeSpeedRate =
            ((Float) this.mArgs.get(SemVideoTranscodingService.KEY_CHANGE_SPEED_RATE)).floatValue();
        this.mCapture.setStartEndTime(changeSpeedStartMs, changeSpeedEndMs);
        ArrayList<SemMediaCapture.DynamicViewingConfiguration> dvConfigs = new ArrayList<>();
        SemMediaCapture semMediaCapture = this.mCapture;
        Objects.requireNonNull(semMediaCapture);
        SemMediaCapture.DynamicViewingConfiguration dvConfig =
            semMediaCapture
            .new DynamicViewingConfiguration(changeSpeedStartMs, changeSpeedEndMs, changeSpeedRate);
        dvConfigs.add(dvConfig);
        this.mCapture.setDynamicViewingConfigurations(dvConfigs);
        this.mCapture.prepare();
      } catch (Exception e) {
        e.printStackTrace();
        Log.m102w(
            "SemVideoTranscodingService",
            "Task(" + this.mID + ") has been terminated unexpectedly");
        if (this.mIgnoreError) {
          Log.m98i(
              "SemVideoTranscodingService",
              "Client has stopped " + this.mID + ", Ignore this error.");
        } else {
          try {
            this.mIsRunning = false;
            this.mProgressCallback.onError();
            this.mTranscodingService.stopTask(this.mID);
          } catch (RemoteException re) {
            re.printStackTrace();
          }
        }
        try {
          FileInputStream fileInputStream2 = this.mFis;
          if (fileInputStream2 != null) {
            fileInputStream2.close();
          }
          FileOutputStream fileOutputStream2 = this.mFos;
          if (fileOutputStream2 != null) {
            fileOutputStream2.close();
          }
        } catch (IOException ie) {
          ie.printStackTrace();
        }
      }
    }
  }

  public SemVideoTranscodingService() {
    IBinder b = ServiceManager.getService("SemVideoTranscodingService");
    this.mService = IVideoTranscodingService.Stub.asInterface(b);
  }

  public Client createClient(
      int mode, String inputPath, String outputPath, ProgressCallback progressCallback) {
    Log.m94d(
        "SemVideoTranscodingService",
        "mode("
            + mode
            + ") in("
            + inputPath
            + ") out("
            + outputPath
            + NavigationBarInflaterView.KEY_CODE_END);
    Map args = new HashMap();
    args.put(KEY_INPUT_PATH, inputPath);
    args.put(KEY_OUTPUT_PATH, outputPath);
    return createClient(mode, args, progressCallback);
  }

  public Client createClient(int mode, Map args, ProgressCallback progressCallback) {
    Log.m94d("SemVideoTranscodingService", "mode(" + mode + NavigationBarInflaterView.KEY_CODE_END);
    IVideoTranscodingService iVideoTranscodingService = this.mService;
    if (iVideoTranscodingService == null) {
      Log.m102w("SemVideoTranscodingService", "IVideoTranscodingService is null");
      return null;
    }
    try {
      String id = iVideoTranscodingService.register(mode, progressCallback);
      if (id == null) {
        Log.m102w("SemVideoTranscodingService", "id is null");
        return null;
      }
      return new Client(this.mService, id, mode, args, progressCallback);
    } catch (RemoteException e) {
      Log.m96e("SemVideoTranscodingService", "Exception createClient()");
      e.printStackTrace();
      return null;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static boolean isSupportedHdrToSdr() {
    boolean result = false;
    if (Build.VERSION.SEM_PLATFORM_INT >= 100000) {
      result =
          SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_HDR2SDR");
    }
    Log.m94d("SemVideoTranscodingService", "isSupportedHdrToSdr() " + result);
    return result;
  }
}
