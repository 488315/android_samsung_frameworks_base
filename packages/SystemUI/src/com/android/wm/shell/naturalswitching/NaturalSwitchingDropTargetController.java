package com.android.wm.shell.naturalswitching;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NaturalSwitchingDropTargetController implements GestureDetector.OnGestureListener, ShellTaskOrganizer.TaskVanishedListener {
    public final ShellExecutor mBackgroundExecutor;
    public final Context mContext;
    public final DisplayController mDisplayController;
    public GestureDetector mGestureDetector;
    public boolean mLayoutChanged;
    public final Handler mMainHandler;
    public NaturalSwitchingLayout mNaturalSwitchingLayout;
    public final SyncTransactionQueue mSyncQueue;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Transitions mTransitions;
    public boolean mIsRunning = false;
    public boolean mAllowInterceptTouch = true;

    public NaturalSwitchingDropTargetController(Context context, ShellTaskOrganizer shellTaskOrganizer, Handler handler, ShellExecutor shellExecutor, DisplayController displayController, Transitions transitions, SyncTransactionQueue syncTransactionQueue) {
        this.mContext = context;
        this.mTaskOrganizer = shellTaskOrganizer;
        shellTaskOrganizer.addTaskVanishedListener(this);
        this.mMainHandler = handler;
        this.mBackgroundExecutor = shellExecutor;
        this.mDisplayController = displayController;
        this.mTransitions = transitions;
        this.mSyncQueue = syncTransactionQueue;
    }

    public final boolean allowInterceptTouch(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo != null && runningTaskInfo.supportsMultiWindow) {
            int windowingMode = runningTaskInfo.getWindowingMode();
            if ((windowingMode == 1 ? CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN : windowingMode == 2 ? CoreRune.MW_NATURAL_SWITCHING_PIP : true) && ((!CoreRune.MW_NATURAL_SWITCHING_PIP || windowingMode != 2 || !runningTaskInfo.supportsPipOnly) && runningTaskInfo.getDisplayId() == 0)) {
                DesktopStateImpl.Companion.getClass();
                if (!DesktopStateImpl.Companion.inDesktopWindowing(0) && !MultiWindowManager.getInstance().preventNaturalSwitching(runningTaskInfo.taskId)) {
                    InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
                    if (!(inputMethodManager != null && inputMethodManager.isInputMethodShown()) && !this.mNaturalSwitchingLayout.mSplitScreenController.mSplitState.isSplitStashed()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final void cancelNaturalSwitching(String str) {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("cancelNaturalSwitching: ", str, ", ");
            m.append(this.mTaskInfo);
            Log.w("NaturalSwitchingDropTargetController", m.toString());
            this.mNaturalSwitchingLayout.hide(false);
        }
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x0154, code lost:
    
        if (r5.mNsWindowingMode != r6) goto L79;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r14, int r15) {
        /*
            Method dump skipped, instructions count: 407
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController.onInterceptTouchEvent(android.view.MotionEvent, int):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:113:0x042c, code lost:
    
        if ((!com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT ? r8.mSupportOnlyTwoUpMode ? r8.isTwoUp() : r8.isMultiSplit() : r8.isTwoUp()) == false) goto L138;
     */
    /* JADX WARN: Removed duplicated region for block: B:103:0x05c4  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x041a  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0428  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02d2  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x02dc  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x030f  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x00de A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x037b  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0438  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0466  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x04b3  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0501  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x055c  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x05be  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x05cf  */
    @Override // android.view.GestureDetector.OnGestureListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onLongPress(android.view.MotionEvent r27) {
        /*
            Method dump skipped, instructions count: 1498
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController.onLongPress(android.view.MotionEvent):void");
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskVanishedListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i = runningTaskInfo.taskId;
        if (this.mIsRunning) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
            if ((runningTaskInfo2 != null ? runningTaskInfo2.taskId : -1) == i) {
                cancelNaturalSwitching("TaskVanished(" + runningTaskInfo.taskId + ")");
            }
        }
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final void onShowPress(MotionEvent motionEvent) {
    }
}
