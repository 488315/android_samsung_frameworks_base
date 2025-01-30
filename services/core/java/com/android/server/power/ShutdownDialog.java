package com.android.server.power;

import android.R;
import android.app.Dialog;
import android.app.Presentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.p005os.IInstalld;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.view.SemWindowManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* loaded from: classes3.dex */
public class ShutdownDialog extends Dialog {
  public String PATH_SHUTDOWNIMG_POSTFIX;
  public String PATH_SHUTDOWNIMG_PREFIX;
  public String[] SHUTDOWN_ANIM_FILES;
  public String[] SHUTDOWN_LOOP_FILES;
  public String[] SHUTDOWN_SUBANIM_FILES;
  public String SYSFS_OCTA_WINDOW_TYPE_FROM_PANEL;
  public final int WINDOWTYPE_OFFSET;
  public final Bitmap[] bitmapQ;
  public int bitmapQFront;
  public int bitmapQRear;
  public final String chameleonCode;
  public final String chameleonFile;
  public final boolean chameleonFileExist;
  public Context displayContext;
  public Display[] displays;

  /* renamed from: dm */
  public DisplayManager f1721dm;
  public final Object drawBufferLock;
  public boolean hasSubDisplayDevice;
  public String knoxAnimPath;
  public String knoxSoundPath;
  public String knoxSubAnimPath;
  public final Handler logHandler;
  public Context mContext;
  public DrawHandler mDrawHandler;
  public ImageView mImageView;
  public final StringBuffer mLogString;
  public TextView mLogView;
  public DrawState mState;
  public final StateDrawing mStateDrawing;
  public final StatePrepare mStatePrepare;

  /* renamed from: mp */
  public MediaPlayer f1722mp;
  public final List qmgList;
  public boolean silentShutdown;
  public Presentation subDialog;
  public ImageView subImageView;
  public final Bitmap[] subbitmapQ;
  public Display subdisplay;
  public final List subqmgList;
  public final boolean supportChameleon;

  public interface DrawState {
    boolean checkRunning();

    void prepare();

    void start();
  }

  public ShutdownDialog(Context context) {
    this(context, R.style.Theme.NoTitleBar.Fullscreen);
  }

  /* JADX WARN: Multi-variable type inference failed */
  public ShutdownDialog(Context context, int i) {
    super(context, i);
    this.PATH_SHUTDOWNIMG_PREFIX = "//system/media/shutdown_";
    this.PATH_SHUTDOWNIMG_POSTFIX = ".qmg";
    this.SYSFS_OCTA_WINDOW_TYPE_FROM_PANEL = "/sys/class/lcd/panel/window_type";
    this.WINDOWTYPE_OFFSET = 15;
    this.SHUTDOWN_ANIM_FILES =
        new String[] {"shutdownbefore.qmg", "shutdown.qmg", "shutdownafter.qmg"};
    this.SHUTDOWN_LOOP_FILES = new String[] {"shutdownloop.qmg", "sub_shutdownloop.qmg"};
    this.SHUTDOWN_SUBANIM_FILES =
        new String[] {"sub_shutdownbefore.qmg", "sub_shutdown.qmg", "sub_shutdownafter.qmg"};
    this.chameleonFile = "/carrier/chameleon.xml";
    this.supportChameleon =
        SemCscFeature.getInstance().getBoolean("CscFeature_Common_UseChameleon", false);
    this.chameleonFileExist = new File("/carrier/chameleon.xml").exists();
    this.chameleonCode = getChameleonCode();
    this.bitmapQ = new Bitmap[3];
    this.bitmapQFront = 0;
    this.bitmapQRear = 0;
    this.drawBufferLock = new Object();
    this.qmgList = new ArrayList();
    this.silentShutdown = false;
    this.subqmgList = new ArrayList();
    this.subbitmapQ = new Bitmap[3];
    this.hasSubDisplayDevice = false;
    this.mLogString = new StringBuffer();
    this.logHandler =
        new Handler() { // from class: com.android.server.power.ShutdownDialog.1
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            ShutdownDialog.this.mLogString.append(message.getData().getString("ShutdownDialog"));
            if (ShutdownDialog.this.mLogView != null) {
              ShutdownDialog.this.mLogView.setText(ShutdownDialog.this.mLogString.toString());
            }
          }
        };
    StatePrepare statePrepare = new StatePrepare();
    this.mStatePrepare = statePrepare;
    this.mStateDrawing = new StateDrawing();
    this.mState = statePrepare;
    this.mContext = context;
    if (new File("//data/system/b2b/ShutdownFileInfo.txt").exists()) {
      getKnoxShutdownPath();
      android.util.Slog.d("ShutdownDialog", "Knox sound : " + this.knoxSoundPath);
      android.util.Slog.d("ShutdownDialog", "Knox img : " + this.knoxAnimPath);
      android.util.Slog.d("ShutdownDialog", "Knox subImg : " + this.knoxSubAnimPath);
    }
    setCancelable(false);
    Window window = getWindow();
    if (window != null) {
      WindowManager.LayoutParams attributes = window.getAttributes();
      attributes.setTitle("ShutdownDialog");
      attributes.flags |= -2140535424;
      attributes.privateFlags |= 16;
      attributes.systemUiVisibility |= 2;
      attributes.rotationAnimation |= 2;
      window.clearFlags(65536);
      attributes.type = 2021;
      attributes.layoutInDisplayCutoutMode = 1;
      window.setAttributes(attributes);
      window.setWindowAnimations(0);
    }
  }

  @Override // android.app.Dialog
  public void onCreate(Bundle bundle) {
    android.util.Slog.d("ShutdownDialog", "onCreate");
    super.onCreate(bundle);
    Window window = getWindow();
    if (window != null) {
      window.setStatusBarColor(0);
      window.setDecorFitsSystemWindows(false);
    }
    FrameLayout frameLayout = new FrameLayout(this.mContext);
    frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 17));
    frameLayout.setBackgroundColor(-16777216);
    this.mImageView = new ImageView(this.mContext);
    TextView textView = new TextView(this.mContext);
    this.mLogView = textView;
    textView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    this.mLogView.setTextColor(-16711681);
    frameLayout.addView(this.mImageView);
    frameLayout.addView(this.mLogView);
    this.mDrawHandler = new DrawHandler();
    setContentView(frameLayout);
    DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
    this.f1721dm = displayManager;
    Display[] displays =
        displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
    this.displays = displays;
    if (displays == null || displays.length <= 1) {
      return;
    }
    android.util.Slog.i("TAG", "bloom subdisplay exist");
    this.displayContext = this.mContext.createDisplayContext(this.displays[1]);
    Presentation presentation = new Presentation(this.displayContext, this.displays[1]);
    this.subDialog = presentation;
    Window window2 = presentation.getWindow();
    WindowManager.LayoutParams attributes = window2.getAttributes();
    attributes.layoutInDisplayCutoutMode = 3;
    window2
        .getDecorView()
        .setSystemUiVisibility(
            window2.getDecorView().getSystemUiVisibility()
                | 512
                | 1024
                | 2
                | 4
                | IInstalld.FLAG_USE_QUOTA);
    window2.setAttributes(attributes);
    this.subImageView = new ImageView(this.displayContext);
  }

  @Override // android.app.Dialog
  public void onStart() {
    super.onStart();
    this.mState.start();
  }

  @Override // android.app.Dialog
  public void onStop() {
    super.onStop();
  }

  public class StatePrepare implements DrawState {
    public boolean hasAnim;
    public boolean hasSound;

    public StatePrepare() {}

    @Override // com.android.server.power.ShutdownDialog.DrawState
    public void prepare() {
      android.util.Slog.i("ShutdownDialog", "prepare shutdown dialog image and sound");
      boolean z = false;
      if (ShutdownDialog.this.qmgList.size() > 0) {
        LibQmg libQmg = (LibQmg) ShutdownDialog.this.qmgList.get(0);
        android.util.Slog.d("ShutdownDialog", "!@[prepare] " + libQmg.toString());
        int width = libQmg.getWidth();
        int height = libQmg.getHeight();
        disableWindowRotation(width, height);
        for (int i = 0; i < 3; i++) {
          if (ShutdownDialog.this.bitmapQ[i] == null) {
            ShutdownDialog.this.bitmapQ[i] =
                Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
          }
        }
      } else {
        android.util.Slog.e("ShutdownDialog", "qmglist error");
      }
      if (ShutdownDialog.this.subqmgList.size() > 0) {
        ShutdownDialog.this.hasSubDisplayDevice = true;
        LibQmg libQmg2 = (LibQmg) ShutdownDialog.this.subqmgList.get(0);
        android.util.Slog.d("ShutdownDialog", "!@[prepare] subdisplay : " + libQmg2.toString());
        int width2 = libQmg2.getWidth();
        int height2 = libQmg2.getHeight();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ShutdownDialog shutdownDialog = ShutdownDialog.this;
        shutdownDialog.subdisplay =
            ((DisplayManager) shutdownDialog.mContext.getSystemService("display")).getDisplay(1);
        if (ShutdownDialog.this.subdisplay != null) {
          ShutdownDialog.this.subdisplay.getMetrics(displayMetrics);
          for (int i2 = 0; i2 < 3; i2++) {
            if (ShutdownDialog.this.subbitmapQ[i2] == null) {
              ShutdownDialog.this.subbitmapQ[i2] =
                  Bitmap.createBitmap(displayMetrics, width2, height2, Bitmap.Config.RGB_565);
            }
          }
        }
      } else {
        android.util.Slog.e("ShutdownDialog", "no sub_shutdown.qmg");
      }
      int systemVolume = ShutdownDialog.this.getSystemVolume();
      String shutdownSoundPath = ShutdownDialog.this.getShutdownSoundPath();
      if (!ShutdownDialog.this.silentShutdown
          && shutdownSoundPath != null
          && systemVolume != 0
          && !checkFotaSilent()) {
        z = true;
      }
      this.hasSound = z;
      if (z) {
        ShutdownDialog.this.prepareSound(shutdownSoundPath);
      } else if (systemVolume == 0) {
        AudioSystem.setParameters("g_shutdown_suspend=suspend");
      } else {
        new Thread() { // from class: com.android.server.power.ShutdownDialog.StatePrepare.1
          @Override // java.lang.Thread, java.lang.Runnable
          public void run() {
            try {
              Thread.sleep(500L);
            } catch (InterruptedException e) {
              android.util.Slog.e("ShutdownDialog", "InterruptedException", e);
            }
            AudioSystem.setParameters("g_shutdown_suspend=suspend");
          }
        }.start();
      }
      ShutdownDialog.this.mContext.sendBroadcast(new Intent("POWER_OFF_ANIMATION_START"));
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0056: MOVE (r5 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:32:0x0056 */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0059 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean compareFileStr(String str, String str2) {
      BufferedReader bufferedReader;
      IOException e;
      FileNotFoundException e2;
      BufferedReader bufferedReader2;
      File file = new File(str);
      if (!file.exists()) {
        return false;
      }
      BufferedReader bufferedReader3 = null;
      try {
        try {
          try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            try {
              boolean equals = str2.equals(bufferedReader.readLine());
              try {
                bufferedReader.close();
              } catch (IOException e3) {
                android.util.Slog.e("ShutdownDialog", "File close error", e3);
              }
              return equals;
            } catch (FileNotFoundException e4) {
              e2 = e4;
              android.util.Slog.e("ShutdownDialog", "Exception", e2);
              if (bufferedReader == null) {
                return false;
              }
              bufferedReader.close();
              return false;
            } catch (IOException e5) {
              e = e5;
              android.util.Slog.e("ShutdownDialog", "Exception", e);
              if (bufferedReader == null) {
                return false;
              }
              bufferedReader.close();
              return false;
            }
          } catch (Throwable th) {
            th = th;
            bufferedReader3 = bufferedReader2;
            if (bufferedReader3 != null) {
              try {
                bufferedReader3.close();
              } catch (IOException e6) {
                android.util.Slog.e("ShutdownDialog", "File close error", e6);
              }
            }
            throw th;
          }
        } catch (FileNotFoundException e7) {
          bufferedReader = null;
          e2 = e7;
        } catch (IOException e8) {
          bufferedReader = null;
          e = e8;
        } catch (Throwable th2) {
          th = th2;
          if (bufferedReader3 != null) {}
          throw th;
        }
      } catch (IOException e9) {
        android.util.Slog.e("ShutdownDialog", "File close error", e9);
        return false;
      }
    }

    public boolean checkFotaSilent() {
      return compareFileStr("/efs/sec_efs/auto_reboot/autoinstall.status", "AUTO_INSTALL");
    }

    @Override // com.android.server.power.ShutdownDialog.DrawState
    public void start() {
      android.util.Slog.d("ShutdownDialog", "start draw");
      ShutdownDialog.this.mStateDrawing.start();
      ShutdownDialog shutdownDialog = ShutdownDialog.this;
      shutdownDialog.mState = shutdownDialog.mStateDrawing;
    }

    @Override // com.android.server.power.ShutdownDialog.DrawState
    public boolean checkRunning() {
      return this.hasSound || this.hasAnim;
    }

    public final void disableWindowRotation(int i, int i2) {
      Window window = ShutdownDialog.this.getWindow();
      if (window != null) {
        WindowManager.LayoutParams attributes = window.getAttributes();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(attributes);
        if (i > i2) {
          layoutParams.screenOrientation = 0;
        } else {
          layoutParams.screenOrientation = 1;
        }
        window.setAttributes(layoutParams);
      }
    }
  }

  public final int getSystemVolume() {
    int i;
    AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
    if (audioManager != null) {
      audioManager.setParameter("g_shutdown_mute", "1");
      i = audioManager.getStreamVolume(1);
    } else {
      i = -9999;
    }
    android.util.Slog.i("ShutdownDialog", String.format("!@systemVol:%d", Integer.valueOf(i)));
    return i;
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:51:0x0090 -> B:9:0x0093). Please report as a decompilation issue!!! */
  public final void prepareSound(String str) {
    FileInputStream fileInputStream;
    AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
    File file = new File(str);
    FileInputStream fileInputStream2 = null;
    FileInputStream fileInputStream3 = null;
    FileInputStream fileInputStream4 = null;
    FileInputStream fileInputStream5 = null;
    FileInputStream fileInputStream6 = null;
    fileInputStream2 = null;
    try {
      try {
        try {
          fileInputStream = new FileInputStream(file);
        } catch (Throwable th) {
          th = th;
        }
      } catch (IOException e) {
        e = e;
      } catch (IllegalArgumentException e2) {
        e = e2;
      } catch (IllegalStateException e3) {
        e = e3;
      } catch (SecurityException e4) {
        e = e4;
      }
    } catch (IOException e5) {
      android.util.Slog.e("ShutdownDialog", "sound file.close", e5);
      fileInputStream2 = fileInputStream2;
    }
    try {
      MediaPlayer mediaPlayer = new MediaPlayer();
      mediaPlayer.setDataSource(fileInputStream.getFD());
      AudioAttributes build =
          new AudioAttributes.Builder()
              .setLegacyStreamType(1)
              .semAddAudioTag("stv_boot_sound")
              .build();
      float semGetSituationVolume = audioManager.semGetSituationVolume(8, 0);
      mediaPlayer.setAudioAttributes(build);
      mediaPlayer.setVolume(semGetSituationVolume, semGetSituationVolume);
      mediaPlayer.setLooping(false);
      mediaPlayer.prepare();
      this.f1722mp = mediaPlayer;
      fileInputStream.close();
      fileInputStream2 = mediaPlayer;
    } catch (IOException e6) {
      e = e6;
      fileInputStream3 = fileInputStream;
      android.util.Slog.e("ShutdownDialog", "!@MediaPlayer exception. Sound will not start!", e);
      fileInputStream2 = fileInputStream3;
      if (fileInputStream3 != null) {
        fileInputStream3.close();
        fileInputStream2 = fileInputStream3;
      }
    } catch (IllegalArgumentException e7) {
      e = e7;
      fileInputStream4 = fileInputStream;
      android.util.Slog.e("ShutdownDialog", "!@MediaPlayer exception. Sound will not start!", e);
      fileInputStream2 = fileInputStream4;
      if (fileInputStream4 != null) {
        fileInputStream4.close();
        fileInputStream2 = fileInputStream4;
      }
    } catch (IllegalStateException e8) {
      e = e8;
      fileInputStream5 = fileInputStream;
      android.util.Slog.e("ShutdownDialog", "!@MediaPlayer exception. Sound will not start!", e);
      fileInputStream2 = fileInputStream5;
      if (fileInputStream5 != null) {
        fileInputStream5.close();
        fileInputStream2 = fileInputStream5;
      }
    } catch (SecurityException e9) {
      e = e9;
      fileInputStream6 = fileInputStream;
      android.util.Slog.e("ShutdownDialog", "!@MediaPlayer exception. Sound will not start!", e);
      fileInputStream2 = fileInputStream6;
      if (fileInputStream6 != null) {
        fileInputStream6.close();
        fileInputStream2 = fileInputStream6;
      }
    } catch (Throwable th2) {
      th = th2;
      fileInputStream2 = fileInputStream;
      if (fileInputStream2 != null) {
        try {
          fileInputStream2.close();
        } catch (IOException e10) {
          android.util.Slog.e("ShutdownDialog", "sound file.close", e10);
        }
      }
      throw th;
    }
  }

  public class StateDrawing implements DrawState {
    public ImageLoadThread imageLoadThread;
    public SoundThread soundThread;

    public StateDrawing() {
      this.imageLoadThread = null;
      this.soundThread = null;
    }

    @Override // com.android.server.power.ShutdownDialog.DrawState
    public void prepare() {
      if (checkRunning()) {
        android.util.Slog.w("ShutdownDialog", "becareful prepare while draw");
      } else {
        ShutdownDialog.this.mDrawHandler.init();
      }
    }

    @Override // com.android.server.power.ShutdownDialog.DrawState
    public void start() {
      android.util.Slog.i("ShutdownDialog", "!@StateDrawing.start()");
      this.soundThread = ShutdownDialog.this.new SoundThread();
      this.imageLoadThread = new ImageLoadThread();
      new Thread(this.soundThread).start();
      new Thread(this.imageLoadThread).start();
    }

    @Override // com.android.server.power.ShutdownDialog.DrawState
    public boolean checkRunning() {
      return this.soundThread.checkRunning() || this.imageLoadThread.checkRunning();
    }
  }

  public abstract class RunningCheckable extends Thread implements Runnable {
    public boolean running;

    public RunningCheckable() {
      this.running = true;
    }

    public boolean checkRunning() {
      return this.running;
    }
  }

  public class ImageLoadThread extends RunningCheckable {
    public ImageLoadThread() {
      super();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      String str;
      android.util.Slog.i(
          "ShutdownDialog",
          "!@ImageLoadThread.run(), qmgList.size = " + ShutdownDialog.this.qmgList.size());
      ShutdownDialog.this.mDrawHandler.sendEmptyMessage(0);
      while (ShutdownDialog.this.qmgList.size() > 0) {
        try {
          if (ShutdownDialog.this.subqmgList.size() > 0) {
            multiFrameLoadLoop(
                (LibQmg) ShutdownDialog.this.qmgList.get(0),
                (LibQmg) ShutdownDialog.this.subqmgList.get(0));
            ShutdownDialog.this.qmgList.remove(0);
            ShutdownDialog.this.subqmgList.remove(0);
          } else {
            frameLoadLoop((LibQmg) ShutdownDialog.this.qmgList.get(0));
            ShutdownDialog.this.qmgList.remove(0);
          }
        } catch (IndexOutOfBoundsException e) {
          android.util.Slog.e(
              "ShutdownDialog", "!@qmgList or subqmgList IndexOutOfBoundsException", e);
        }
      }
      this.running = false;
      String str2 = "//system/media/" + ShutdownDialog.this.SHUTDOWN_LOOP_FILES[0];
      if (ShutdownDialog.this.knoxAnimPath != null) {
        str2 = "";
        str = "";
      } else {
        str = null;
        if (SystemProperties.get("persist.sys.omc_respath") != null) {
          if (new File(
                  SystemProperties.get("persist.sys.omc_respath")
                      + "/media/video/shutdown/"
                      + ShutdownDialog.this.SHUTDOWN_LOOP_FILES[0])
              .exists()) {
            str2 =
                SystemProperties.get("persist.sys.omc_respath")
                    + "/media/video/shutdown/"
                    + ShutdownDialog.this.SHUTDOWN_LOOP_FILES[0];
            if (new File(
                    SystemProperties.get("persist.sys.omc_respath")
                        + "/media/video/shutdown/"
                        + ShutdownDialog.this.SHUTDOWN_LOOP_FILES[1])
                .exists()) {
              str =
                  SystemProperties.get("persist.sys.omc_respath")
                      + "/media/video/shutdown/"
                      + ShutdownDialog.this.SHUTDOWN_LOOP_FILES[1];
            }
          }
        }
      }
      boolean exists = new File(str2).exists();
      while (exists) {
        if (str != null) {
          try {
            ShutdownDialog.this.addToPlaylistIfExists(str2);
            ShutdownDialog.this.addToSubPlaylistIfExists(str);
            multiFrameLoadLoop(
                (LibQmg) ShutdownDialog.this.qmgList.get(0),
                (LibQmg) ShutdownDialog.this.subqmgList.get(0));
            ShutdownDialog.this.qmgList.remove(0);
            ShutdownDialog.this.subqmgList.remove(0);
          } catch (IndexOutOfBoundsException e2) {
            android.util.Slog.e(
                "ShutdownDialog", "!@loopqmgList or subloopqmgList IndexOutOfBoundsException", e2);
          }
        } else {
          ShutdownDialog.this.addToPlaylistIfExists(str2);
          frameLoadLoop((LibQmg) ShutdownDialog.this.qmgList.get(0));
          ShutdownDialog.this.qmgList.remove(0);
        }
      }
      ShutdownDialog.this.mDrawHandler.sendEmptyMessage(-1);
    }

    public final void frameLoadLoop(LibQmg libQmg) {
      int loadFrame;
      libQmg.ensureQmgHandle();
      ShutdownDialog.this.mDrawHandler.sendEmptyMessage(libQmg.getDelayTime() + 100);
      android.util.Slog.i("ShutdownDialog", "!@[frameLoadLoop] " + libQmg.toString());
      do {
        try {
          loadFrame =
              libQmg.loadFrame(ShutdownDialog.this.bitmapQ[ShutdownDialog.this.bitmapQFront]);
          android.util.Slog.d("ShutdownDialog", "load frame: ret=" + loadFrame);
          if (loadFrame >= 0) {
            synchronized (ShutdownDialog.this.drawBufferLock) {
              ShutdownDialog shutdownDialog = ShutdownDialog.this;
              shutdownDialog.bitmapQFront = (shutdownDialog.bitmapQFront + 1) % 3;
              if ((ShutdownDialog.this.bitmapQFront + 1) % 3 == ShutdownDialog.this.bitmapQRear) {
                try {
                  ShutdownDialog.this.drawBufferLock.wait(5000L);
                } catch (InterruptedException e) {
                  android.util.Slog.e("ShutdownDialog", "InterruptedException", e);
                }
              }
            }
          }
        } catch (IOException e2) {
          android.util.Slog.e("ShutdownDialog", "qmgLoadFrame return < 0", e2);
        }
      } while (loadFrame > 0);
      libQmg.close();
    }

    public final void multiFrameLoadLoop(LibQmg libQmg, LibQmg libQmg2) {
      int loadFrame;
      libQmg.ensureQmgHandle();
      android.util.Slog.i(
          "ShutdownDialog", "!@[multiframeLoadLoop] startMain " + libQmg.toString());
      libQmg2.ensureQmgHandle();
      android.util.Slog.i(
          "ShutdownDialog", "!@[multiframeLoadLoop] startSub " + libQmg2.toString());
      ShutdownDialog.this.mDrawHandler.sendEmptyMessage(libQmg.getDelayTime() + 100);
      do {
        try {
          loadFrame =
              libQmg.loadFrame(ShutdownDialog.this.bitmapQ[ShutdownDialog.this.bitmapQFront]);
          android.util.Slog.d(
              "ShutdownDialog",
              "load frame: ret="
                  + loadFrame
                  + ", load subframe: ret="
                  + libQmg2.loadFrame(
                      ShutdownDialog.this.subbitmapQ[ShutdownDialog.this.bitmapQFront]));
          if (loadFrame >= 0) {
            synchronized (ShutdownDialog.this.drawBufferLock) {
              ShutdownDialog shutdownDialog = ShutdownDialog.this;
              shutdownDialog.bitmapQFront = (shutdownDialog.bitmapQFront + 1) % 3;
              if ((ShutdownDialog.this.bitmapQFront + 1) % 3 == ShutdownDialog.this.bitmapQRear) {
                try {
                  ShutdownDialog.this.drawBufferLock.wait(5000L);
                } catch (InterruptedException e) {
                  android.util.Slog.e("ShutdownDialog", "InterruptedException", e);
                }
              }
            }
          }
        } catch (IOException e2) {
          android.util.Slog.e("ShutdownDialog", "qmgLoadFrame return < 0", e2);
        }
      } while (loadFrame > 0);
      libQmg.close();
      libQmg2.close();
    }
  }

  public class DrawHandler extends Handler {
    public boolean finished;
    public int maxSleep;

    public DrawHandler() {
      init();
    }

    public void init() {
      this.finished = false;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      android.util.Slog.i("ShutdownDialog", "DrawHandler.handleMessage");
      int i = message.what;
      if (i > 100) {
        this.maxSleep = i - 100;
        return;
      }
      if (i == -1) {
        android.util.Slog.i("ShutdownDialog", "frame load finish");
        this.finished = true;
        return;
      }
      if (ShutdownDialog.this.bitmapQRear != ShutdownDialog.this.bitmapQFront) {
        ShutdownDialog.this.mDrawHandler.sendEmptyMessageDelayed(0, this.maxSleep);
        if (ShutdownDialog.this.hasSubDisplayDevice) {
          int i2 =
              ShutdownDialog.this.mContext.getResources().getConfiguration().semDisplayDeviceType;
          boolean isFolded = SemWindowManager.getInstance().isFolded();
          if (i2 == 0) {
            ShutdownDialog.this.mImageView.setImageBitmap(
                ShutdownDialog.this.bitmapQ[ShutdownDialog.this.bitmapQRear]);
            android.util.Slog.i("ShutdownDialog", "main image draw");
          } else if (i2 == 5) {
            ShutdownDialog.this.mImageView.setImageBitmap(
                ShutdownDialog.this.subbitmapQ[ShutdownDialog.this.bitmapQRear]);
            android.util.Slog.i("ShutdownDialog", "sub image draw");
          } else if (!isFolded) {
            ShutdownDialog.this.mImageView.setImageBitmap(
                ShutdownDialog.this.bitmapQ[ShutdownDialog.this.bitmapQRear]);
            android.util.Slog.i("ShutdownDialog", "main image draw");
          } else if (isFolded) {
            ShutdownDialog.this.subImageView.setImageBitmap(
                ShutdownDialog.this.subbitmapQ[ShutdownDialog.this.bitmapQRear]);
            ShutdownDialog.this.subDialog.setContentView(ShutdownDialog.this.subImageView);
            ShutdownDialog.this.subDialog.show();
            android.util.Slog.i("ShutdownDialog", "sub image draw");
          }
        } else {
          ShutdownDialog.this.mImageView.setImageBitmap(
              ShutdownDialog.this.bitmapQ[ShutdownDialog.this.bitmapQRear]);
        }
        synchronized (ShutdownDialog.this.drawBufferLock) {
          ShutdownDialog shutdownDialog = ShutdownDialog.this;
          shutdownDialog.bitmapQRear = (shutdownDialog.bitmapQRear + 1) % 3;
          ShutdownDialog.this.drawBufferLock.notifyAll();
        }
        return;
      }
      if (this.finished) {
        android.util.Slog.i("ShutdownDialog", "image draw finish");
      } else {
        android.util.Slog.e("ShutdownDialog", "image buffer not ready");
        ShutdownDialog.this.mDrawHandler.sendEmptyMessageDelayed(0, this.maxSleep / 2);
      }
    }
  }

  public class SoundThread extends RunningCheckable implements MediaPlayer.OnCompletionListener {
    public final CountDownLatch completeSignal;

    public SoundThread() {
      super();
      this.completeSignal = new CountDownLatch(1);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      if (ShutdownDialog.this.f1722mp == null) {
        android.util.Slog.i("ShutdownDialog", "MediaPlayer is null");
        this.running = false;
        return;
      }
      ShutdownDialog.this.f1722mp.setOnCompletionListener(this);
      android.util.Slog.i("ShutdownDialog", "Start play sound file");
      try {
        ShutdownDialog.this.f1722mp.start();
      } catch (IllegalStateException unused) {
        android.util.Slog.e("ShutdownDialog", "sound thread IllegalStateException");
      } catch (Exception unused2) {
        android.util.Slog.e("ShutdownDialog", "sound thread exception");
      }
      try {
        this.completeSignal.await(
            ShutdownDialog.this.f1722mp.getDuration() * 2, TimeUnit.MILLISECONDS);
        android.util.Slog.i("ShutdownDialog", "Set sound complete audioParam");
        AudioSystem.setParameters("g_shutdown_suspend=suspend");
        android.util.Slog.i("ShutdownDialog", "H/W workaround. wait a sec before power off");
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
        android.util.Slog.e("ShutdownDialog", "Wait fail", e);
      }
      this.running = false;
      android.util.Slog.i("ShutdownDialog", "Shutdown sound done");
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
      android.util.Slog.i("ShutdownDialog", "!@onCompletion(MediaPlayer arg0) called !!");
      this.completeSignal.countDown();
    }
  }

  public final String getChameleonCode() {
    String str;
    if (!this.supportChameleon || !this.chameleonFileExist || !isCarrierActivated()) {
      return null;
    }
    String search = search("/carrier/chameleon.xml", "Operators.AndroidOperatorNetworkCode");
    android.util.Slog.d("ShutdownDialog", "!@Power off sound CHAMELEON network code : " + search);
    if (search == null) {
      search = "310000";
    }
    switch (search) {
      case "310120":
        str = "SPR";
        break;
      case "311490":
        str = "VMU";
        break;
      case "311870":
        str = "BST";
        break;
      default:
        str = "XAS";
        break;
    }
    android.util.Slog.d("ShutdownDialog", "!@Power off sound CHAMELEON is activated : " + str);
    return str;
  }

  public final boolean isCarrierActivated() {
    return search("/carrier/chameleon.xml", "Operators.SubscriberCarrierId") != null;
  }

  public static String search(String str, String str2) {
    Node element = getElement(str);
    if (str2 == null || element == null) {
      return null;
    }
    StringTokenizer stringTokenizer = new StringTokenizer(str2, ".");
    while (stringTokenizer.hasMoreTokens()) {
      element = search(element, stringTokenizer.nextToken());
      if (element == null) {
        return null;
      }
    }
    Node firstChild = element.getFirstChild();
    if (firstChild == null) {
      return null;
    }
    return firstChild.getNodeValue();
  }

  public static Node search(Node node, String str) {
    NodeList childNodes;
    if (node != null && (childNodes = node.getChildNodes()) != null) {
      int length = childNodes.getLength();
      for (int i = 0; i < length; i++) {
        Node item = childNodes.item(i);
        if (item.getNodeName().equals(str)) {
          return item;
        }
      }
    }
    return null;
  }

  public static Node getElement(String str) {
    File file = new File(str);
    try {
      DocumentBuilder newDocumentBuilder =
          DocumentBuilderFactory.newInstance().newDocumentBuilder();
      if (file.exists()) {
        return newDocumentBuilder.parse(file).getDocumentElement();
      }
      return null;
    } catch (Exception e) {
      android.util.Slog.e("ShutdownDialog", "Exception", e);
      return null;
    }
  }

  public final String getShutdownSoundPath() {
    if (this.knoxSoundPath != null && new File(this.knoxSoundPath).exists()) {
      android.util.Slog.i("ShutdownDialog", "!@[getShutdownSoundPath] knoxSoundPath");
      return this.knoxSoundPath;
    }
    String str = SystemProperties.get("persist.sys.omc_respath");
    if (str != null) {
      if (new File(str + "/media/audio/ui/PowerOff.ogg").exists()) {
        android.util.Slog.i(
            "ShutdownDialog", "!@[getShutdownSoundPath] PATH_SHUTDOWNSOUND_SKU_OGG");
        return str + "/media/audio/ui/PowerOff.ogg";
      }
      if (new File(str + "/media/audio/ui/PowerOff.wav").exists()) {
        android.util.Slog.i(
            "ShutdownDialog", "!@[getShutdownSoundPath] PATH_SHUTDOWNSOUND_SKU_WAV");
        return str + "/media/audio/ui/PowerOff.wav";
      }
    }
    if (this.chameleonCode != null) {
      StringBuilder sb = new StringBuilder("/system/media/audio/ui/");
      sb.append(this.chameleonCode);
      sb.append("/PowerOff.ogg");
      if (new File(sb.toString()).exists()) {
        android.util.Slog.i("ShutdownDialog", "!@[getShutdownSoundPath] chameleonPath");
        return sb.toString();
      }
    }
    if (new File("//system/media/audio/ui/PowerOff.wav").exists()) {
      android.util.Slog.i("ShutdownDialog", "!@[getShutdownSoundPath] PowerOff.wav");
      return "//system/media/audio/ui/PowerOff.wav";
    }
    if (!new File("//system/media/audio/ui/PowerOff.ogg").exists()) {
      return null;
    }
    android.util.Slog.i("ShutdownDialog", "!@[getShutdownSoundPath] PowerOff.ogg");
    return "//system/media/audio/ui/PowerOff.ogg";
  }

  public final boolean addToPlaylistIfExists(String str, String[] strArr) {
    boolean z = false;
    for (String str2 : strArr) {
      z |= addToPlaylistIfExists(str + str2);
    }
    return z;
  }

  public final boolean addToPlaylistIfExists(String str) {
    if (!new File(str).exists()) {
      return false;
    }
    try {
      this.qmgList.add(new LibQmg(str));
    } catch (ArrayIndexOutOfBoundsException e) {
      android.util.Slog.e("ShutdownDialog", "!@qmgList.add ArrayIndexOutOfBoundsException", e);
    }
    return true;
  }

  public final boolean addToSubPlaylistIfExists(String str, String[] strArr) {
    boolean z = false;
    for (String str2 : strArr) {
      z |= addToSubPlaylistIfExists(str + str2);
    }
    return z;
  }

  public final boolean addToSubPlaylistIfExists(String str) {
    if (!new File(str).exists()) {
      return false;
    }
    try {
      this.subqmgList.add(new LibQmg(str));
    } catch (ArrayIndexOutOfBoundsException e) {
      android.util.Slog.e("ShutdownDialog", "!@subqmgList.add ArrayIndexOutOfBoundsException", e);
    }
    return true;
  }

  public final void createShutdownQmgPlayList() {
    String str;
    this.qmgList.clear();
    this.subqmgList.clear();
    if (this.chameleonCode != null) {
      StringBuffer stringBuffer = new StringBuffer("//system/media/");
      stringBuffer.append(this.chameleonCode);
      stringBuffer.append("_shutdown.qmg");
      if (addToPlaylistIfExists(stringBuffer.toString())) {
        return;
      }
    }
    String str2 = this.knoxAnimPath;
    if (str2 != null && addToPlaylistIfExists(str2)) {
      String str3 = this.knoxSubAnimPath;
      if (str3 != null) {
        addToSubPlaylistIfExists(str3);
        return;
      }
      return;
    }
    String qmgCodeById = getQmgCodeById(readColorId());
    String str4 = SystemProperties.get("persist.sys.omc_respath");
    if (str4 != null && (!this.supportChameleon || this.chameleonCode != null)) {
      String str5 = SystemProperties.get("ro.csc.sales_code");
      if (str5 != null && (str = this.chameleonCode) != null && !str.equals(str5)) {
        str4 = str4.replace(str5, this.chameleonCode);
        android.util.Slog.d(
            "ShutdownDialog", "!@Power off sound CHAMELEON - update animation path to : " + str4);
      }
      if (qmgCodeById != null) {
        String str6 = "shutdown_" + qmgCodeById + this.PATH_SHUTDOWNIMG_POSTFIX;
        if (new File(str4 + "/media/video/shutdown/" + str6).isFile()) {
          this.SHUTDOWN_ANIM_FILES[1] = str6;
          if (addToPlaylistIfExists(str4 + "/media/video/shutdown/", this.SHUTDOWN_ANIM_FILES)) {
            addToSubPlaylistIfExists(str4 + "/media/video/shutdown/", this.SHUTDOWN_SUBANIM_FILES);
            return;
          }
        }
      }
      if (addToPlaylistIfExists(str4 + "/media/video/shutdown/", this.SHUTDOWN_ANIM_FILES)) {
        addToSubPlaylistIfExists(str4 + "/media/video/shutdown/", this.SHUTDOWN_SUBANIM_FILES);
        return;
      }
    }
    if (qmgCodeById != null) {
      if (new File(this.PATH_SHUTDOWNIMG_PREFIX + qmgCodeById + this.PATH_SHUTDOWNIMG_POSTFIX)
          .isFile()) {
        addToPlaylistIfExists("//system/media/", this.SHUTDOWN_ANIM_FILES);
        addToSubPlaylistIfExists("//system/media/", this.SHUTDOWN_SUBANIM_FILES);
        return;
      }
    }
    if (new File("//system/media/" + this.SHUTDOWN_ANIM_FILES[1]).isFile()) {
      addToPlaylistIfExists("//system/media/", this.SHUTDOWN_ANIM_FILES);
      addToSubPlaylistIfExists("//system/media/", this.SHUTDOWN_SUBANIM_FILES);
    } else {
      addToPlaylistIfExists("//system/media/" + this.SHUTDOWN_LOOP_FILES[0]);
    }
  }

  public final String getQmgCodeById(int i) {
    if (SystemProperties.get("ro.build.product").startsWith("dream")) {
      return null;
    }
    switch (i) {
      case 2:
      case 3:
      case 4:
      case 8:
        return "WH";
      case 5:
        return SystemProperties.get("ro.color.green", (String) null);
      case 6:
        return SystemProperties.get("ro.color.blue", (String) null);
      case 7:
        return SystemProperties.get("ro.color.pinkgold", (String) null);
      default:
        return null;
    }
  }

  public final int readColorId() {
    String substring;
    String readWindowType = readWindowType();
    if (readWindowType == null
        || readWindowType.length() <= 0
        || (substring = readWindowType.substring(0, readWindowType.indexOf(32))) == null) {
      return 0;
    }
    return Integer.parseInt(substring, 16) & 15;
  }

  public final String readWindowType() {
    StringBuilder sb = new StringBuilder();
    BufferedReader bufferedReader = null;
    try {
    } catch (IOException e) {
      android.util.Slog.d("ShutdownDialog", e.toString());
    }
    try {
      try {
        BufferedReader bufferedReader2 =
            new BufferedReader(new FileReader(this.SYSFS_OCTA_WINDOW_TYPE_FROM_PANEL));
        while (true) {
          try {
            String readLine = bufferedReader2.readLine();
            if (readLine == null) {
              break;
            }
            sb.append(readLine);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
          } catch (IOException e2) {
            e = e2;
            bufferedReader = bufferedReader2;
            android.util.Slog.d("ShutdownDialog", e.toString());
            if (bufferedReader != null) {
              bufferedReader.close();
            }
            return sb.toString().trim();
          } catch (Throwable th) {
            th = th;
            bufferedReader = bufferedReader2;
            if (bufferedReader != null) {
              try {
                bufferedReader.close();
              } catch (IOException e3) {
                android.util.Slog.d("ShutdownDialog", e3.toString());
              }
            }
            throw th;
          }
        }
        bufferedReader2.close();
      } catch (IOException e4) {
        e = e4;
      }
      return sb.toString().trim();
    } catch (Throwable th2) {
      th = th2;
    }
  }

  public boolean waitForAnimEnd(int i) {
    long elapsedRealtime = SystemClock.elapsedRealtime() + (i * 1000);
    while (this.mState.checkRunning()) {
      if (elapsedRealtime - SystemClock.elapsedRealtime() <= 0) {
        android.util.Slog.w("ShutdownDialog", "!@Animation finish wait timed out");
        return true;
      }
      android.util.Slog.d("ShutdownDialog", "!@wait for finish : sleep 100ms");
      try {
        Thread.sleep(100L);
      } catch (InterruptedException unused) {
        android.util.Slog.e("ShutdownDialog", "InterruptedException");
      }
    }
    return true;
  }

  public void appendTextLog(String str) {
    Message obtainMessage = this.logHandler.obtainMessage(0, str);
    Bundle bundle = new Bundle();
    bundle.putString("ShutdownDialog", str);
    obtainMessage.setData(bundle);
    this.logHandler.sendMessage(obtainMessage);
  }

  public boolean existAnim() {
    createShutdownQmgPlayList();
    return this.qmgList.size() > 0;
  }

  public void prepareShutdown() {
    android.util.Slog.i("ShutdownDialog", "prepare shutdown dialog image and sound");
    this.mState.prepare();
  }

  public void setSilentShutdown(boolean z) {
    this.silentShutdown = z;
  }

  public final void getKnoxShutdownPath() {
    try {
      BufferedReader bufferedReader =
          new BufferedReader(new FileReader("//data/system/b2b/ShutdownFileInfo.txt"));
      try {
        this.knoxAnimPath = bufferedReader.readLine();
        this.knoxSoundPath = bufferedReader.readLine();
        String readLine = bufferedReader.readLine();
        if (readLine != null) {
          this.knoxSubAnimPath = readLine;
        }
        bufferedReader.close();
      } catch (Throwable th) {
        try {
          bufferedReader.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
        throw th;
      }
    } catch (FileNotFoundException e) {
      android.util.Slog.e("ShutdownDialog", "getKnoxShutdownPath(): File not found", e);
    } catch (IOException e2) {
      android.util.Slog.e(
          "ShutdownDialog",
          "getKnoxShutdownPath(): An exception occurred while reading the file",
          e2);
    }
  }
}
