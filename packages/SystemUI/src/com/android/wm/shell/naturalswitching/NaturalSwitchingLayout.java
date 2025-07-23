package com.android.wm.shell.naturalswitching;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.StatusBarManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemProperties;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DismissViewManager;
import com.android.wm.shell.common.DismissViewManager$$ExternalSyntheticLambda0;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NaturalSwitchingLayout {
    public static final boolean DEBUG_DEV = SystemProperties.getBoolean("persist.debug.ns.dev", false);
    public static final boolean DEBUG_PIP = SystemProperties.getBoolean("persist.debug.ns.pip", false);
    public final ShellExecutor mBackgroundExecutor;
    public DismissViewManager mCancelButtonManager;
    public final Context mContext;
    public DragTargetView mDragTargetView;
    public boolean mHasDropped;
    public boolean mHideRequested;
    public boolean mIsPipNaturalSwitching;
    public NonDragTargetView mNonDragTargetView;
    public int mNsWindowingMode;
    public boolean mPassedInitialSlop;
    public boolean mReadyToStart;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public final SplitScreenController mSplitScreenController;
    public final StatusBarManager mStatusBarManager;
    public final SyncTransactionQueue mSyncQueue;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final TaskVisibility mTaskVisibility;
    public int mTouchSlop;
    public int mNaturalSwitchingMode = 0;
    public boolean mNaturalSwitchingStartReported = false;
    public final Handler mHandler = new Handler(Looper.myLooper());
    public final AnonymousClass1 mHideRunnable = new AnonymousClass1();
    public final Binder mBinder = new Binder();
    public final Point mDownPoint = new Point();
    public final Point mMovePoint = new Point();
    public final Point mTouchGap = new Point();
    public final ArrayList mHideTasks = new ArrayList();
    public NaturalSwitchingChanger mLastChanger = null;
    public final NaturalSwitchingAlgorithm mNaturalSwitchingAlgorithm = new NaturalSwitchingAlgorithm();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.naturalswitching.NaturalSwitchingLayout$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            NaturalSwitchingLayout naturalSwitchingLayout = NaturalSwitchingLayout.this;
            if (naturalSwitchingLayout.mHideRequested) {
                return;
            }
            NonDragTargetView nonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
            if (nonDragTargetView != null) {
                nonDragTargetView.mWm.removeView(nonDragTargetView);
            }
            DragTargetView dragTargetView = naturalSwitchingLayout.mDragTargetView;
            if (dragTargetView != null) {
                dragTargetView.mIsDragEndCalled = true;
                dragTargetView.mWm.removeView(dragTargetView);
            }
            NaturalSwitchingLayout.this.mStatusBarManager.disable(0);
            NaturalSwitchingLayout naturalSwitchingLayout2 = NaturalSwitchingLayout.this;
            if (naturalSwitchingLayout2.mNaturalSwitchingStartReported) {
                Log.d("NaturalSwitchingLayout", "finishNaturalSwitchingIfNeeded");
                naturalSwitchingLayout2.mNaturalSwitchingStartReported = false;
                if (naturalSwitchingLayout2.mNaturalSwitchingMode == 1 && !naturalSwitchingLayout2.mTaskVisibility.isTaskVisible(1)) {
                    naturalSwitchingLayout2.mSplitScreenController.setDividerVisibilityFromNS(true);
                }
                MultiWindowManager.getInstance().finishNaturalSwitching();
            }
            DismissViewManager dismissViewManager = NaturalSwitchingLayout.this.mCancelButtonManager;
            if (dismissViewManager != null) {
                dismissViewManager.mView.hide(new DismissViewManager$$ExternalSyntheticLambda0(dismissViewManager));
            }
            NaturalSwitchingLayout naturalSwitchingLayout3 = NaturalSwitchingLayout.this;
            naturalSwitchingLayout3.mDragTargetView = null;
            NonDragTargetView nonDragTargetView2 = naturalSwitchingLayout3.mNonDragTargetView;
            if (nonDragTargetView2 != null) {
                nonDragTargetView2.getRootView().getViewTreeObserver().removeOnDrawListener(nonDragTargetView2.mOnDrawListener);
                NaturalSwitchingLayout.this.mNonDragTargetView = null;
            }
            NaturalSwitchingLayout.this.mHideRequested = true;
        }
    }

    public NaturalSwitchingLayout(Context context, SplitScreenController splitScreenController, Transitions transitions, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mSplitScreenController = splitScreenController;
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mSyncQueue = syncTransactionQueue;
        this.mBackgroundExecutor = shellExecutor;
        this.mStatusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        this.mTaskVisibility = new TaskVisibility(context);
        transitions.registerObserver(new Transitions.TransitionObserver() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingLayout.2
            @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
            public final void onTransitionStarting(IBinder iBinder) {
                NaturalSwitchingLayout naturalSwitchingLayout = NaturalSwitchingLayout.this;
                NaturalSwitchingChanger naturalSwitchingChanger = naturalSwitchingLayout.mLastChanger;
                if (naturalSwitchingChanger != null) {
                    Runnable runnable = naturalSwitchingChanger.mRunAfterTransitionStarted;
                    if (runnable != null) {
                        runnable.run();
                        naturalSwitchingChanger.mRunAfterTransitionStarted = null;
                    }
                    naturalSwitchingLayout.mLastChanger = null;
                }
            }

            @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
            public final void onTransitionFinished(IBinder iBinder, boolean z) {
            }

            @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
            public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
            }

            @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
            public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
            }
        });
    }

    public static int getNaturalSwitchingWindowingMode(int i, int i2) {
        int i3 = 1;
        if (i != 1) {
            i3 = 2;
            if (i != 2) {
                if (i == 5) {
                    return 5;
                }
                if (i != 6) {
                    return 0;
                }
                if ((i2 & 1) != 0) {
                    return 3;
                }
                if ((i2 & 2) != 0) {
                    return 4;
                }
                if ((i2 & 4) != 0) {
                    return 12;
                }
            }
        }
        return i3;
    }

    public static boolean isFloating(int i) {
        return (CoreRune.MW_NATURAL_SWITCHING_PIP && i == 2) || i == 5;
    }

    public final void hide(boolean z) {
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(2, "NaturalSwitchingLayout", new StringBuilder("hide: callers="));
        Handler handler = this.mHandler;
        AnonymousClass1 anonymousClass1 = this.mHideRunnable;
        handler.removeCallbacks(anonymousClass1);
        if (this.mHideRequested) {
            return;
        }
        if (!this.mHideTasks.isEmpty()) {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            this.mHideTasks.forEach(new NaturalSwitchingLayout$$ExternalSyntheticLambda2(transaction, 0));
            this.mHideTasks.clear();
            transaction.apply();
        }
        if (!z) {
            anonymousClass1.run();
            return;
        }
        handler.postDelayed(anonymousClass1, 5000L);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingLayout$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                NaturalSwitchingLayout naturalSwitchingLayout = NaturalSwitchingLayout.this;
                boolean z2 = NaturalSwitchingLayout.DEBUG_DEV;
                naturalSwitchingLayout.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                naturalSwitchingLayout.mDragTargetView.mDragTarget.setAlpha(floatValue);
                naturalSwitchingLayout.mNonDragTargetView.mMainView.setAlpha(floatValue);
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingLayout.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                NaturalSwitchingLayout.this.hide(false);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                NaturalSwitchingLayout.this.hide(false);
            }
        });
        ofFloat.start();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{WindowingMode=");
        sb.append(this.mNsWindowingMode);
        sb.append(", NsMode=");
        sb.append(this.mNaturalSwitchingMode);
        sb.append(", dragToken=");
        DragTargetView dragTargetView = this.mDragTargetView;
        sb.append(dragTargetView != null ? dragTargetView.getWindowToken() : null);
        sb.append("}");
        return sb.toString();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r2v30 int, still in use, count: 2, list:
          (r2v30 int) from 0x02c5: IF  (wrap:int:0x02c1: IGET (r7v10 com.android.wm.shell.naturalswitching.NonDragTargetView) A[WRAPPED] (LINE:706) com.android.wm.shell.naturalswitching.NonDragTargetView.mDropSide int) != (r2v30 int)  -> B:101:0x031e A[HIDDEN] (LINE:710)
          (r2v30 int) from 0x02ca: PHI (r2v29 int) = (r2v30 int) binds: [B:85:0x02c5] A[DONT_GENERATE, DONT_INLINE]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:125)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:62)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:45)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:67)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:35)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:34)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    /* JADX WARN: Removed duplicated region for block: B:184:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02b3  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0302 A[LOOP:0: B:94:0x0302->B:96:0x030a, LOOP_START, PHI: r1
      0x0302: PHI (r1v36 int) = (r1v35 int), (r1v39 int) binds: [B:93:0x0300, B:96:0x030a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update(android.view.MotionEvent r20) {
        /*
            Method dump skipped, instructions count: 1186
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.naturalswitching.NaturalSwitchingLayout.update(android.view.MotionEvent):void");
    }
}
