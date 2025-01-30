package com.android.server.sepunion.cover;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.android.server.LocalServices;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.sepunion.Log;

/* loaded from: classes3.dex */
public class SleepTokenAcquirer {
    public static final String TAG = "CoverManager_" + SleepTokenAcquirer.class.getSimpleName();
    public final Context mContext;
    public ActivityTaskManagerInternal.SleepTokenAcquirer mDefaultAcquirer;
    public final Handler mHandler;
    public boolean mIsCoverAppCovered;
    public boolean mSwitchState;
    public ActivityTaskManagerInternal.SleepTokenAcquirer mVirtualAcquirer;
    public final Runnable mSleepTokenTask = new Runnable() { // from class: com.android.server.sepunion.cover.SleepTokenAcquirer$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            SleepTokenAcquirer.this.doSleepToken();
        }
    };
    public final Runnable mDefaultSleepToken = new Runnable() { // from class: com.android.server.sepunion.cover.SleepTokenAcquirer$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            SleepTokenAcquirer.this.doDefaultSleepToken();
        }
    };
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);

    public SleepTokenAcquirer(Context context, Looper looper) {
        this.mContext = context;
        this.mHandler = new Handler(looper);
    }

    public void update(boolean z, boolean z2) {
        this.mSwitchState = z;
        this.mIsCoverAppCovered = z2;
        this.mHandler.removeCallbacks(this.mSleepTokenTask);
        this.mHandler.post(this.mSleepTokenTask);
    }

    public final void acquire(int i) {
        ActivityTaskManagerInternal.SleepTokenAcquirer sleepTokenAcquirer = getSleepTokenAcquirer(i);
        if (sleepTokenAcquirer != null) {
            Log.d(TAG, "acquireSleepToken " + i);
            sleepTokenAcquirer.acquire(i);
        }
    }

    public final void release(int i) {
        ActivityTaskManagerInternal.SleepTokenAcquirer sleepTokenAcquirer = getSleepTokenAcquirer(i);
        if (sleepTokenAcquirer != null) {
            Log.d(TAG, "releaseSleepToken " + i);
            sleepTokenAcquirer.release(i);
        }
    }

    public final ActivityTaskManagerInternal.SleepTokenAcquirer getSleepTokenAcquirer(int i) {
        if (i == 0) {
            if (this.mDefaultAcquirer == null) {
                Log.d(TAG, "createSleepTokenAcquirer " + i);
                this.mDefaultAcquirer = this.mActivityTaskManagerInternal.createSleepTokenAcquirer("cover");
            }
        } else if (this.mVirtualAcquirer == null) {
            Log.d(TAG, "createSleepTokenAcquirer " + i);
            this.mVirtualAcquirer = this.mActivityTaskManagerInternal.createSleepTokenAcquirer("cover-virtual");
        }
        return i == 0 ? this.mDefaultAcquirer : this.mVirtualAcquirer;
    }

    public final void doSleepToken() {
        if (!this.mSwitchState && !this.mIsCoverAppCovered) {
            this.mHandler.removeCallbacks(this.mDefaultSleepToken);
            this.mHandler.postDelayed(this.mDefaultSleepToken, 200L);
        } else if (this.mHandler.hasCallbacks(this.mDefaultSleepToken)) {
            this.mHandler.removeCallbacks(this.mDefaultSleepToken);
        } else {
            release(0);
        }
    }

    public final void doDefaultSleepToken() {
        if (this.mSwitchState || this.mIsCoverAppCovered) {
            return;
        }
        acquire(0);
    }
}
