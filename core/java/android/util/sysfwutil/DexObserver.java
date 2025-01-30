package android.util.sysfwutil;

import android.app.job.JobInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.UEventObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/* loaded from: classes4.dex */
public class DexObserver {
  private static final String CCIC_DOCK_UEVENT_MATCH = "DEVPATH=/devices/virtual/sec/ccic";
  private static final String TAG = "DexObserverFW";
  private static final String USBPD_IDS_PATH = "/sys/class/sec/ccic/usbpd_ids";
  private static final String USBPD_TYPE_PATH = "/sys/class/sec/ccic/usbpd_type";
  private final UEventObserver mDexUEventObserver;
  private volatile boolean mDexMode = false;
  private volatile boolean mSemiDexMode = false;
  private final Object mDexStateLock = new Object();
  private final BlockingDeque<DexConnectionListener> mListeners = new LinkedBlockingDeque();
  private boolean mTestModeOn = false;

  public DexObserver() {
    UEventObserver uEventObserver =
        new UEventObserver() { // from class: android.util.sysfwutil.DexObserver.1
          @Override // android.os.UEventObserver
          public void onUEvent(UEventObserver.UEvent event) {
            try {
              Slog.m147d(DexObserver.TAG, "UEventObserver, event : " + event);
              DexObserver.this.setDexState(Integer.parseInt(event.get("SWITCH_STATE")), event);
            } catch (NumberFormatException e) {
              Slog.m149e(DexObserver.TAG, "Could not parse switch state from event " + event);
            }
          }
        };
    this.mDexUEventObserver = uEventObserver;
    Slog.m147d(TAG, "Started" + (this.mTestModeOn ? " TestModeOn" : ""));
    checkDexStatebySysfs();
    uEventObserver.startObserving(CCIC_DOCK_UEVENT_MATCH);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void setDexState(int state, UEventObserver.UEvent event) {
    Slog.m147d(TAG, "setDockState() : " + state);
    switch (state) {
      case 109:
      case 110:
      case 111:
      case 114:
        this.mDexMode = true;
        break;
      case 200:
        String s = event.get("USBPD_IDS");
        if (s != null && s.equals("04e8:a027")) {
          this.mSemiDexMode = true;
          break;
        }
        break;
      default:
        this.mDexMode = false;
        this.mSemiDexMode = false;
        break;
    }
    onUpdateDexMode();
  }

  private void checkDexStatebySysfs() {
    StringBuilder sb;
    String mVIDPID = null;
    String mUsbType = null;
    FileReader fr1 = null;
    FileReader fr2 = null;
    BufferedReader br1 = null;
    BufferedReader br2 = null;
    try {
      try {
        try {
          try {
            File usb_usbpd_ids_fd = new File(USBPD_IDS_PATH);
            File usb_usbpd_type_fd = new File(USBPD_TYPE_PATH);
            if (usb_usbpd_ids_fd.exists()) {
              fr1 = new FileReader(USBPD_IDS_PATH);
              br1 = new BufferedReader(fr1);
              mVIDPID = br1.readLine();
            } else {
              Slog.m149e(TAG, "USBPD IDS File does not exist");
            }
            if (usb_usbpd_type_fd.exists()) {
              fr2 = new FileReader(USBPD_TYPE_PATH);
              br2 = new BufferedReader(fr2);
              mUsbType = br2.readLine();
            } else {
              Slog.m149e(TAG, "USBPD TYPE File does not exist");
            }
            if (mVIDPID == null || mUsbType == null) {
              Slog.m147d(TAG, "checkDexStatebySysfs() USBPD_IDS or USBPD_TYPE is NULL!!");
            } else if (this.mTestModeOn) {
              Slog.m147d(
                  TAG,
                  "checkDexStatebySysfs() USBPD_IDS["
                      + mVIDPID
                      + "], USBPD_TYPE["
                      + mUsbType
                      + NavigationBarInflaterView.SIZE_MOD_END);
            }
            if (mUsbType == null || !"200".equals(mUsbType)) {
              if (mUsbType == null || !"114".equals(mUsbType)) {
                this.mDexMode = false;
                this.mSemiDexMode = false;
              } else {
                if (this.mTestModeOn) {
                  Slog.m147d(TAG, "checkDexStatebySysfs() : DEX MODE is ON");
                }
                this.mDexMode = true;
              }
            } else if (mVIDPID != null && "04e8:a027".equals(mVIDPID)) {
              if (this.mTestModeOn) {
                Slog.m147d(TAG, "checkDexStatebySysfs() : SEMI DEX MODE is ON");
              }
              this.mSemiDexMode = true;
            }
            if (this.mTestModeOn) {
              Slog.m147d(TAG, "checkDexStatebySysfs() : Update DeX Connection State");
            }
            onUpdateDexMode();
            if (fr1 != null) {
              try {
                fr1.close();
              } catch (IOException e) {
                ioex = e;
                sb = new StringBuilder();
                Slog.m149e(
                    TAG, sb.append("IOException(iex): ").append(ioex.getMessage()).toString());
                return;
              }
            }
            if (br1 != null) {
              br1.close();
            }
            if (fr2 != null) {
              fr2.close();
            }
            if (br2 != null) {
              br2.close();
            }
          } catch (IllegalArgumentException iae) {
            Slog.m149e(TAG, "IllegalArgumentException: " + iae.getMessage());
            if (fr1 != null) {
              try {
                fr1.close();
              } catch (IOException e2) {
                ioex = e2;
                sb = new StringBuilder();
                Slog.m149e(
                    TAG, sb.append("IOException(iex): ").append(ioex.getMessage()).toString());
                return;
              }
            }
            if (br1 != null) {
              br1.close();
            }
            if (fr2 != null) {
              fr2.close();
            }
            if (br2 != null) {
              br2.close();
            }
          }
        } catch (IOException ioe) {
          Slog.m149e(TAG, "IOException: " + ioe.getMessage());
          if (fr1 != null) {
            try {
              fr1.close();
            } catch (IOException e3) {
              ioex = e3;
              sb = new StringBuilder();
              Slog.m149e(TAG, sb.append("IOException(iex): ").append(ioex.getMessage()).toString());
              return;
            }
          }
          if (br1 != null) {
            br1.close();
          }
          if (fr2 != null) {
            fr2.close();
          }
          if (br2 != null) {
            br2.close();
          }
        }
      } catch (Throwable th) {
        if (fr1 != null) {
          try {
            fr1.close();
          } catch (IOException ioex) {
            Slog.m149e(TAG, "IOException(iex): " + ioex.getMessage());
            throw th;
          }
        }
        if (br1 != null) {
          br1.close();
        }
        if (fr2 != null) {
          fr2.close();
        }
        if (br2 != null) {
          br2.close();
        }
        throw th;
      }
    } catch (FileNotFoundException fnfe) {
      Slog.m149e(TAG, "File not Found exception: " + fnfe.getMessage());
      if (fr1 != null) {
        try {
          fr1.close();
        } catch (IOException e4) {
          ioex = e4;
          sb = new StringBuilder();
          Slog.m149e(TAG, sb.append("IOException(iex): ").append(ioex.getMessage()).toString());
          return;
        }
      }
      if (br1 != null) {
        br1.close();
      }
      if (fr2 != null) {
        fr2.close();
      }
      if (br2 != null) {
        br2.close();
      }
    }
  }

  /* JADX WARN: Type inference failed for: r1v3, types: [android.util.sysfwutil.DexObserver$2] */
  private void onUpdateDexMode() {
    if (this.mTestModeOn) {
      Slog.m147d(TAG, "setDexMode() : delay ++");
      try {
        Thread.sleep(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
      } catch (InterruptedException e) {
      }
      Slog.m147d(TAG, "setDexMode() : delay --");
    }
    synchronized (this.mDexStateLock) {
      Slog.m147d(
          TAG, "setDexMode() : mDexMode " + this.mDexMode + " mSemiDexMode " + this.mSemiDexMode);
      if (this.mDexMode || this.mSemiDexMode) {
        new Thread("notifyListeners") { // from class: android.util.sysfwutil.DexObserver.2
          @Override // java.lang.Thread, java.lang.Runnable
          public void run() {
            for (DexConnectionListener li : DexObserver.this.mListeners) {
              li.onConnect();
            }
          }
        }.start();
      }
    }
  }

  public boolean isDexModeOn() {
    boolean z;
    synchronized (this.mDexStateLock) {
      Slog.m147d(TAG, "isDexModeOn() : " + this.mDexMode);
      z = this.mDexMode;
    }
    return z;
  }

  public boolean isSemiDexModeOn() {
    boolean z;
    synchronized (this.mDexStateLock) {
      Slog.m147d(TAG, "isSemiDexModeOn() : " + this.mSemiDexMode);
      z = this.mSemiDexMode;
    }
    return z;
  }

  public void addListener(DexConnectionListener listener) {
    this.mListeners.add(listener);
  }

  public void dump(PrintWriter pw) {
    synchronized (this.mDexStateLock) {
      pw.println("Current DexModeObserver state of DeXMode :" + this.mDexMode);
      pw.println("Current DexModeObserver state of SemiDeXMode :" + this.mSemiDexMode);
    }
  }
}
