package android.p005os.epic;

import com.samsung.epic.Request;
import java.util.TimerTask;

/* loaded from: classes.dex */
public final class EpicChromeTask extends TimerTask {
  public boolean mAcquired;
  public EpicChromeDetector mDetector;
  public Request mHandle;
  public Request mRequest;
  public String mCheckPkgName = null;
  public boolean mCancel = false;

  public EpicChromeTask(EpicChromeDetector epicChromeDetector, EpicChromeTask epicChromeTask) {
    this.mHandle = null;
    this.mAcquired = false;
    this.mDetector = epicChromeDetector;
    this.mHandle = new Request(100300);
    if (epicChromeTask != null) {
      this.mAcquired = epicChromeTask.mAcquired;
    }
  }

  public void setCheckPkgName(String str) {
    synchronized (this) {
      this.mCheckPkgName = str;
    }
  }

  public String getCheckPkgName() {
    return this.mCheckPkgName;
  }

  public void reset() {
    this.mCancel = false;
  }

  public void setCancel() {
    this.mCancel = true;
  }

  @Override // java.util.TimerTask, java.lang.Runnable
  public void run() {
    synchronized (this) {
      EpicChromeDetector epicChromeDetector = this.mDetector;
      if (epicChromeDetector != null && !this.mCancel) {
        boolean CheckChromeBrowser = epicChromeDetector.CheckChromeBrowser(this.mCheckPkgName);
        if (this.mCancel) {
          return;
        }
        if (CheckChromeBrowser) {
          if (!this.mAcquired) {
            this.mHandle.acquire_lock();
          }
          this.mAcquired = true;
        } else {
          if (this.mAcquired) {
            this.mHandle.release_lock();
          }
          this.mAcquired = false;
        }
      }
    }
  }
}
