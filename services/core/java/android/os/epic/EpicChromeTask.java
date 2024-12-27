package android.os.epic;

import com.samsung.epic.Request;

import java.util.TimerTask;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class EpicChromeTask extends TimerTask {
    private boolean mAcquired;
    private EpicChromeDetector mDetector;
    private Request mHandle;
    private Request mRequest;
    private String mCheckPkgName = null;
    private boolean mCancel = false;

    public EpicChromeTask(EpicChromeDetector epicChromeDetector, EpicChromeTask epicChromeTask) {
        this.mHandle = null;
        this.mAcquired = false;
        this.mDetector = epicChromeDetector;
        this.mHandle = new Request(100300);
        if (epicChromeTask != null) {
            this.mAcquired = epicChromeTask.mAcquired;
        }
    }

    public String getCheckPkgName() {
        return this.mCheckPkgName;
    }

    public void reset() {
        this.mCancel = false;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        synchronized (this) {
            try {
                EpicChromeDetector epicChromeDetector = this.mDetector;
                if (epicChromeDetector != null && !this.mCancel) {
                    boolean CheckChromeBrowser =
                            epicChromeDetector.CheckChromeBrowser(this.mCheckPkgName);
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
            } finally {
            }
        }
    }

    public void setCancel() {
        this.mCancel = true;
    }

    public void setCheckPkgName(String str) {
        synchronized (this) {
            this.mCheckPkgName = str;
        }
    }
}
