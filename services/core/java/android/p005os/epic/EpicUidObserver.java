package android.p005os.epic;

import android.app.IActivityManager;
import android.app.IUidObserver;
import android.content.Context;
import android.content.pm.PackageManager;
import com.samsung.epic.Request;
import java.util.Timer;

/* loaded from: classes.dex */
public final class EpicUidObserver extends IUidObserver.Stub {
  public static final long DELAY_CHECK_MS = 1000;
  public final IActivityManager mActivityManager;
  public Timer mCheckTimer;
  public EpicChromeDetector mChromeDetector;
  public final Context mContext;
  public final PackageManager mPackageManager;
  public String mPrevPkgName = null;
  public EpicChromeTask mBrowserTimerTask = null;
  public Request mRequest = new Request(0);

  public void onUidActive(int i) {}

  public void onUidCachedChanged(int i, boolean z) {}

  public void onUidIdle(int i, boolean z) {}

  public void onUidProcAdjChanged(int i, int i2) {}

  public EpicUidObserver(
      Context context,
      PackageManager packageManager,
      IActivityManager iActivityManager,
      EpicChromeDetector epicChromeDetector) {
    this.mChromeDetector = null;
    this.mCheckTimer = null;
    this.mContext = context;
    this.mPackageManager = packageManager;
    this.mActivityManager = iActivityManager;
    this.mChromeDetector = epicChromeDetector;
    if (epicChromeDetector == null) {
      return;
    }
    if (!epicChromeDetector.LinkLibrary()) {
      this.mChromeDetector = null;
    } else {
      this.mChromeDetector.Initialize();
      this.mCheckTimer = new Timer();
    }
  }

  public void onUidStateChanged(int i, int i2, long j, int i3) {
    if (i2 != 2) {
      return;
    }
    try {
      String nameForUid = this.mPackageManager.getNameForUid(i);
      String str = this.mPrevPkgName;
      if (str != nameForUid) {
        this.mRequest.hint_release(str);
      }
      this.mRequest.perf_hint(nameForUid);
      this.mPrevPkgName = nameForUid;
      if (this.mChromeDetector == null) {
        return;
      }
      EpicChromeTask epicChromeTask = this.mBrowserTimerTask;
      if (epicChromeTask != null && !epicChromeTask.cancel()) {
        this.mBrowserTimerTask.setCancel();
      }
      EpicChromeTask epicChromeTask2 =
          new EpicChromeTask(this.mChromeDetector, this.mBrowserTimerTask);
      this.mBrowserTimerTask = epicChromeTask2;
      epicChromeTask2.setCheckPkgName(nameForUid);
      this.mBrowserTimerTask.reset();
      this.mCheckTimer.schedule(this.mBrowserTimerTask, 1000L);
    } catch (Exception unused) {
    }
  }

  public void onUidGone(int i, boolean z) {
    try {
      String nameForUid = this.mPackageManager.getNameForUid(i);
      EpicChromeDetector epicChromeDetector = this.mChromeDetector;
      if (epicChromeDetector == null || nameForUid == null) {
        return;
      }
      epicChromeDetector.RemoveUid(nameForUid);
    } catch (Exception unused) {
    }
  }
}
