package android.os.epic;

import android.app.IActivityManager;
import android.app.IUidObserver;
import android.content.Context;
import android.content.pm.PackageManager;

import com.samsung.epic.Request;

import java.util.Timer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class EpicUidObserver extends IUidObserver.Stub {
    private static final long DELAY_CHECK_MS = 1000;
    private final IActivityManager mActivityManager;
    private Timer mCheckTimer;
    private EpicChromeDetector mChromeDetector;
    private final Context mContext;
    private final PackageManager mPackageManager;
    private String mPrevPkgName = null;
    private EpicChromeTask mBrowserTimerTask = null;
    private Request mRequest = new Request(0);

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

    public void onUidActive(int i) {}

    public void onUidCachedChanged(int i, boolean z) {}

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

    public void onUidIdle(int i, boolean z) {}

    public void onUidProcAdjChanged(int i, int i2) {}

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
            this.mCheckTimer.schedule(this.mBrowserTimerTask, DELAY_CHECK_MS);
        } catch (Exception unused) {
        }
    }
}
