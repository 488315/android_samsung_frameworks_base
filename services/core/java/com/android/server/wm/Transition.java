package com.android.server.wm;

import android.app.ActivityManager;
import android.app.PictureInPictureParams;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.view.RemoteAnimationDefinition;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.window.ITransitionPlayer;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.window.flags.Flags;

import com.samsung.android.rune.CoreRune;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Transition implements BLASTSyncEngine.TransactionReadyListener {
    public int mAdditionalFlags;
    public int mAnimationTrack;
    public final ChangeTransitionController mChangeTransitController;
    public ArrayList mConfigAtEndActivities;
    public final TransitionController mController;
    public int mFlags;
    public boolean mForcePlaying;
    public boolean mIsPlayerEnabled;
    public final TransitionController.Logger mLogger;
    public TransitionInfo.AnimationOptions mOverrideOptions;
    public int mParallelCollectType;
    public ActivityRecord mPipActivity;
    public RemoteTransition mRemoteTransition;
    public long mStatusBarTransitionDelay;
    public final BLASTSyncEngine mSyncEngine;
    public ArrayList mTargets;
    public final Token mToken;
    public ArrayList mTransientHideTasks;
    public final int mType;
    public int mSyncId = -1;
    public SurfaceControl.Transaction mStartTransaction = null;
    public SurfaceControl.Transaction mFinishTransaction = null;
    public SurfaceControl.Transaction mCleanupTransaction = null;
    public final ArrayMap mChanges = new ArrayMap();
    public final ArraySet mParticipants = new ArraySet();
    public final ArrayList mTargetDisplays = new ArrayList();
    public final ArrayList mOnTopTasksStart = new ArrayList();
    public final ArrayList mOnTopTasksAtReady = new ArrayList();
    public final ArraySet mVisibleAtTransitionEndTokens = new ArraySet();
    public ArrayMap mTransientLaunches = null;
    ArrayList mTransactionCompletedListeners = null;
    public ArrayList mTransitionEndedListeners = null;
    public IRemoteCallback mClientAnimationStartCallback = null;
    public IRemoteCallback mClientAnimationFinishCallback = null;
    public int mState = -1;
    public final ReadyTrackerOld mReadyTrackerOld = new ReadyTrackerOld();
    public final ReadyTracker mReadyTracker = new ReadyTracker(this);
    public int mRecentsDisplayId = -1;
    public boolean mCanPipOnFinish = true;
    public boolean mIsSeamlessRotation = false;
    public IContainerFreezer mContainerFreezer = null;
    public boolean mPriorVisibilityMightBeDirty = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class ChangeInfo {
        public final Rect mAbsoluteBounds;
        public boolean mAffordanceTargetFreeformTask;
        public SurfaceControl mChangeLeash;
        public int mChangeTransitFlags;
        public int mChangeTransitMode;
        public WindowContainer mCommonAncestor;
        public final WindowContainer mContainer;
        public final int mDisplayId;
        public WindowContainer mEndParent;
        public boolean mExistenceChanged;
        public int mFlags;
        public boolean mForceChangeSplitTask;
        public boolean mForceChanged;
        public int mForceHidingTransit;
        public final Rect mFreezeOutsets;
        public boolean mHideWhileTwoHandDragging;
        public int mKnownConfigChanges;
        public int mMinimizeAnimState;
        public final PointF mMinimizePoint;
        public int mReadyFlags;
        public int mReadyMode;
        public boolean mResumedAffordance;
        public final int mRotation;
        public final boolean mShowWallpaper;
        public SurfaceControl mSnapshot;
        public float mSnapshotLuma;
        public final WindowContainer mStartParent;
        public boolean mVisible;
        public int mWindowingMode;

        public ChangeInfo(WindowContainer windowContainer) {
            this.mExistenceChanged = false;
            Rect rect = new Rect();
            this.mAbsoluteBounds = rect;
            this.mRotation = -1;
            this.mDisplayId = -1;
            this.mFlags = 0;
            this.mChangeLeash = null;
            this.mChangeTransitMode = 0;
            this.mFreezeOutsets = new Rect();
            this.mAffordanceTargetFreeformTask = false;
            this.mForceChanged = false;
            this.mForceHidingTransit = 0;
            this.mMinimizeAnimState = 0;
            this.mMinimizePoint = new PointF();
            this.mResumedAffordance = false;
            this.mForceChangeSplitTask = false;
            this.mContainer = windowContainer;
            this.mVisible = windowContainer.isVisibleRequested();
            this.mWindowingMode = windowContainer.getWindowingMode();
            rect.set(windowContainer.getBounds());
            this.mShowWallpaper = windowContainer.showWallpaper();
            this.mRotation = windowContainer.getWindowConfiguration().getRotation();
            this.mStartParent = windowContainer.getParent();
            this.mDisplayId = Transition.getDisplayId(windowContainer);
        }

        public ChangeInfo(WindowContainer windowContainer, boolean z, boolean z2) {
            this(windowContainer);
            this.mVisible = z;
            this.mExistenceChanged = z2;
            this.mShowWallpaper = false;
        }

        public final int getTransitMode(WindowContainer windowContainer) {
            if ((this.mFlags & 4) != 0) {
                return this.mExistenceChanged ? 2 : 4;
            }
            if (this.mHideWhileTwoHandDragging && !windowContainer.inPinnedWindowingMode()) {
                return 4;
            }
            boolean isVisibleRequested = windowContainer.isVisibleRequested();
            if (isVisibleRequested == this.mVisible) {
                return 6;
            }
            return this.mExistenceChanged ? isVisibleRequested ? 1 : 2 : isVisibleRequested ? 3 : 4;
        }

        public final boolean hasChanged() {
            int i = this.mFlags;
            if ((i & 2) != 0 || (i & 4) != 0) {
                return true;
            }
            boolean z = CoreRune.MW_SPLIT_SHELL_TRANSITION;
            WindowContainer windowContainer = this.mContainer;
            if (z) {
                if ((windowContainer.asTask() == null
                                || !windowContainer.inSplitScreenWindowingMode())
                        ? false
                        : this.mForceChangeSplitTask) {
                    return true;
                }
            }
            boolean isVisibleRequested = windowContainer.isVisibleRequested();
            boolean z2 = this.mVisible;
            if (isVisibleRequested == z2 && !z2) {
                return false;
            }
            if (isVisibleRequested != z2 || this.mKnownConfigChanges != 0) {
                return true;
            }
            if ((this.mWindowingMode != 0
                            && windowContainer.getWindowingMode() != this.mWindowingMode)
                    || !windowContainer.getBounds().equals(this.mAbsoluteBounds)) {
                return true;
            }
            if (this.mRotation != windowContainer.getWindowConfiguration().getRotation()) {
                return true;
            }
            if (this.mDisplayId != Transition.getDisplayId(windowContainer)
                    || (this.mFlags & 32) != 0) {
                return true;
            }
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION && this.mChangeLeash != null) {
                return true;
            }
            if ((CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && this.mForceChanged)
                    || this.mHideWhileTwoHandDragging) {
                return true;
            }
            if (this.mForceHidingTransit != 0) {
                return true;
            }
            return CoreRune.FW_SHELL_TRANSITION_RESUMED_AFFORDANCE && this.mResumedAffordance;
        }

        public final String toString() {
            return this.mContainer.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface IContainerFreezer {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReadyCondition {
        public String mAlternate;
        public final Object mDebugTarget;
        public boolean mMet;
        public final String mName;
        public ReadyTracker mTracker;

        public ReadyCondition(String str) {
            this.mMet = false;
            this.mAlternate = null;
            this.mName = str;
            this.mDebugTarget = null;
        }

        public ReadyCondition(String str, Object obj) {
            this.mMet = false;
            this.mAlternate = null;
            this.mName = str;
            this.mDebugTarget = obj;
        }

        public final void meet() {
            if (this.mMet) {
                return;
            }
            ReadyTracker readyTracker = this.mTracker;
            if (readyTracker == null) {
                throw new IllegalStateException("Can't meet a condition before it is waited on");
            }
            Transition transition = readyTracker.mTransition;
            if (transition == null || !transition.mController.mFullReadyTracking) {
                return;
            }
            if (transition.mState >= 2) {
                Slog.w(
                        "Transition",
                        "#%d: Condition met too late, already in state="
                                + transition.mState
                                + ": "
                                + this);
                return;
            }
            if (readyTracker.mConditions.remove(this)) {
                this.mMet = true;
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                            7631061720069910622L,
                            20,
                            null,
                            String.valueOf(this),
                            Long.valueOf(transition.mSyncId),
                            Long.valueOf(readyTracker.mConditions.size()));
                }
                readyTracker.mMet.add(this);
                transition.applyReady();
                return;
            }
            if (readyTracker.mMet.contains(this)) {
                throw new IllegalStateException(
                        "Can't meet the same condition more than once: "
                                + this
                                + " #"
                                + transition.mSyncId);
            }
            throw new IllegalArgumentException(
                    "Can't meet a condition that isn't being waited on: "
                            + this
                            + " in #"
                            + transition.mSyncId);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            String str = this.mName;
            Object obj = this.mDebugTarget;
            if (obj != null) {
                str = str + ":" + obj;
            }
            sb.append(str);
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                    sb,
                    this.mAlternate != null
                            ? AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(" ("), this.mAlternate, ")")
                            : "",
                    "}");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReadyTracker {
        public static final ReadyTracker NULL_TRACKER = new ReadyTracker(null);
        public final ArrayList mConditions = new ArrayList();
        public final ArrayList mMet = new ArrayList();
        public final Transition mTransition;

        public ReadyTracker(Transition transition) {
            this.mTransition = transition;
        }

        public final void add(ReadyCondition readyCondition) {
            Transition transition = this.mTransition;
            if (transition == null || !transition.mController.mFullReadyTracking) {
                readyCondition.mTracker = NULL_TRACKER;
                return;
            }
            this.mConditions.add(readyCondition);
            readyCondition.mTracker = this;
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                        -2971560715211489406L,
                        4,
                        null,
                        String.valueOf(readyCondition),
                        Long.valueOf(transition.mSyncId));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReadyTrackerOld {
        public final ArrayMap mReadyGroups = new ArrayMap();
        public boolean mUsed = false;
        public boolean mReadyOverride = false;
        public int mDeferReadyDepth = 0;

        public final boolean allReady() {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                boolean z = this.mUsed;
                boolean z2 = this.mReadyOverride;
                long j = this.mDeferReadyDepth;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < this.mReadyGroups.size(); i++) {
                    if (i != 0) {
                        sb.append(',');
                    }
                    sb.append(this.mReadyGroups.keyAt(i));
                    sb.append(':');
                    sb.append(this.mReadyGroups.valueAt(i));
                }
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                        -3263748870548668913L,
                        31,
                        null,
                        Boolean.valueOf(z),
                        Boolean.valueOf(z2),
                        Long.valueOf(j),
                        String.valueOf(sb.toString()));
            }
            if (!this.mUsed || this.mDeferReadyDepth > 0) {
                return false;
            }
            if (this.mReadyOverride) {
                return true;
            }
            for (int size = this.mReadyGroups.size() - 1; size >= 0; size--) {
                WindowContainer windowContainer = (WindowContainer) this.mReadyGroups.keyAt(size);
                if (windowContainer.isAttached()
                        && windowContainer.isVisibleRequested()
                        && !((Boolean) this.mReadyGroups.valueAt(size)).booleanValue()) {
                    return false;
                }
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class ScreenshotFreezer implements IContainerFreezer {
        public final ArraySet mFrozen = new ArraySet();

        public ScreenshotFreezer() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Targets {
        public final SparseArray mArray = new SparseArray();
        public int mDepthFactor;
        public ArrayList mRemovedTargets;

        public final void add(ChangeInfo changeInfo) {
            if (this.mDepthFactor == 0) {
                this.mDepthFactor = changeInfo.mContainer.mWmService.mRoot.getTreeWeight() + 1;
            }
            int prefixOrderIndex = changeInfo.mContainer.getPrefixOrderIndex();
            WindowContainer windowContainer = changeInfo.mContainer;
            while (windowContainer != null) {
                windowContainer = windowContainer.getParent();
                if (windowContainer != null) {
                    prefixOrderIndex += this.mDepthFactor;
                }
            }
            this.mArray.put(prefixOrderIndex, changeInfo);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Token extends Binder {
        public final WeakReference mTransition;

        public Token(Transition transition) {
            this.mTransition = new WeakReference(transition);
        }

        public final String toString() {
            return "Token{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " "
                    + this.mTransition.get()
                    + "}";
        }
    }

    public Transition(
            int i,
            int i2,
            TransitionController transitionController,
            BLASTSyncEngine bLASTSyncEngine) {
        TransitionController.Logger logger = new TransitionController.Logger();
        this.mLogger = logger;
        this.mForcePlaying = false;
        this.mIsPlayerEnabled = true;
        this.mParallelCollectType = 0;
        this.mAnimationTrack = 0;
        this.mConfigAtEndActivities = null;
        this.mType = i;
        this.mFlags = i2;
        this.mController = transitionController;
        this.mSyncEngine = bLASTSyncEngine;
        this.mToken = new Token(this);
        logger.mCreateWallTimeMs = System.currentTimeMillis();
        logger.mCreateTimeNs = SystemClock.elapsedRealtimeNanos();
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            this.mChangeTransitController = transitionController.mAtm.mChangeTransitController;
        }
        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
            StringBuilder sb = new StringBuilder("Transition is created, t=");
            sb.append(this);
            sb.append(", caller=");
            ActivityManagerService$$ExternalSyntheticOutline0.m(5, sb, "WindowManager");
        }
    }

    public static void addOnTopTasks(DisplayContent displayContent, ArrayList arrayList) {
        final int collectingTransitionType =
                displayContent.mTransitionController.getCollectingTransitionType();
        Task rootTask =
                displayContent.getRootTask(
                        new Predicate() { // from class:
                                          // com.android.server.wm.Transition$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Task task = (Task) obj;
                                return !(task.getWindowConfiguration().isAlwaysOnTop()
                                                || (task.inFreeformWindowingMode()
                                                        && task.isDexMode()))
                                        || (task.inFreeformWindowingMode()
                                                && !task.isDexMode()
                                                && collectingTransitionType == 3);
                            }
                        });
        if (rootTask == null) {
            return;
        }
        arrayList.add(rootTask);
        addOnTopTasks(rootTask, arrayList);
    }

    public static void addOnTopTasks(Task task, ArrayList arrayList) {
        for (int childCount = task.getChildCount() - 1; childCount >= 0; childCount--) {
            Task asTask = task.getChildAt(childCount).asTask();
            if (asTask == null) {
                return;
            }
            if (!asTask.getWindowConfiguration().isAlwaysOnTop()) {
                arrayList.add(asTask);
                addOnTopTasks(asTask, arrayList);
                return;
            }
        }
    }

    public static void assignLayers(
            SurfaceControl.Transaction transaction, WindowContainer windowContainer) {
        windowContainer.mTransitionController.mBuildingFinishLayers = true;
        try {
            windowContainer.assignChildLayers(transaction);
        } finally {
            windowContainer.mTransitionController.mBuildingFinishLayers = false;
        }
    }

    public static TransitionInfo.AnimationOptions calculateAnimationOptionsForActivityTransition(
            int i, ArrayList arrayList, WindowManager.LayoutParams layoutParams) {
        TransitionInfo.AnimationOptions animationOptions;
        WindowContainer windowContainer;
        int i2 = 0;
        while (true) {
            animationOptions = null;
            if (i2 >= arrayList.size()) {
                windowContainer = null;
                break;
            }
            if (!isWallpaper(((ChangeInfo) arrayList.get(i2)).mContainer)
                    && (!CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY
                            || ((ChangeInfo) arrayList.get(i2))
                                            .mContainer.asTransientLaunchOverlay()
                                    == null)) {
                break;
            }
            i2++;
        }
        windowContainer = ((ChangeInfo) arrayList.get(i2)).mContainer;
        if (windowContainer.asActivityRecord() != null) {
            ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
            ActivityRecord.CustomAppTransition customAppTransition =
                    asActivityRecord.mCustomOpenTransition;
            if (customAppTransition != null) {
                animationOptions =
                        TransitionInfo.AnimationOptions.makeCommonAnimOptions(
                                asActivityRecord.packageName);
                animationOptions.addCustomActivityTransition(
                        true,
                        customAppTransition.mEnterAnim,
                        customAppTransition.mExitAnim,
                        customAppTransition.mBackgroundColor);
            }
            ActivityRecord.CustomAppTransition customAppTransition2 =
                    asActivityRecord.mCustomCloseTransition;
            if (customAppTransition2 != null) {
                if (animationOptions == null) {
                    animationOptions =
                            TransitionInfo.AnimationOptions.makeCommonAnimOptions(
                                    asActivityRecord.packageName);
                }
                animationOptions.addCustomActivityTransition(
                        false,
                        customAppTransition2.mEnterAnim,
                        customAppTransition2.mExitAnim,
                        customAppTransition2.mBackgroundColor);
            }
        }
        if (!CoreRune.FW_SHELL_TRANSITION_WITH_DIM || layoutParams == null) {
            layoutParams = getLayoutParamsForAnimationsStyle(arrayList, i);
        }
        if (layoutParams == null || layoutParams.type == 3 || layoutParams.windowAnimations == 0) {
            return animationOptions;
        }
        if (animationOptions == null) {
            return TransitionInfo.AnimationOptions.makeAnimOptionsFromLayoutParameters(
                    layoutParams);
        }
        animationOptions.addOptionsFromLayoutParameters(layoutParams);
        return animationOptions;
    }

    public static ArrayList calculateTargets(ArraySet arraySet, ArrayMap arrayMap) {
        return calculateTargets(arraySet, arrayMap, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:228:0x04aa, code lost:

       if (r10 == false) goto L235;
    */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x04ac, code lost:

       r8.mEndParent = r9;
    */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x04b6, code lost:

       if ((r8.mFlags & 64) == 0) goto L243;
    */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x04b8, code lost:

       r10 = r8.mContainer;
    */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x04be, code lost:

       if (r10.asActivityRecord() == null) goto L243;
    */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x04c4, code lost:

       if (r10.getParent() != r9) goto L243;
    */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x04c6, code lost:

       r12.mFlags |= 64;
    */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x04d0, code lost:

       if (r4.isEmpty() == false) goto L246;
    */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x04d3, code lost:

       r8.mEndParent = ((com.android.server.wm.Transition.ChangeInfo) r4.get(0)).mContainer;
    */
    /* JADX WARN: Code restructure failed: missing block: B:241:0x04e3, code lost:

       if (r11 >= (r4.size() - 1)) goto L303;
    */
    /* JADX WARN: Code restructure failed: missing block: B:242:0x04e5, code lost:

       r8 = (com.android.server.wm.Transition.ChangeInfo) r4.get(r11);
       r11 = r11 + 1;
       r8.mEndParent = ((com.android.server.wm.Transition.ChangeInfo) r4.get(r11)).mContainer;
       r1.add(r8);
    */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x04af, code lost:

       r4.add(r12);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.ArrayList calculateTargets(
            android.util.ArraySet r25, android.util.ArrayMap r26, int r27) {
        /*
            Method dump skipped, instructions count: 1504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.Transition.calculateTargets(android.util.ArraySet,"
                    + " android.util.ArrayMap, int):java.util.ArrayList");
    }

    /* JADX WARN: Code restructure failed: missing block: B:135:0x01f7, code lost:

       if (r15.mChildren.indexOf(r1) < r15.mChildren.indexOf(r9)) goto L154;
    */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x0359, code lost:

       if (r13.getMode() == 1) goto L263;
    */
    /* JADX WARN: Removed duplicated region for block: B:107:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x031c  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0354  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0429  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0493  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x049a  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x04a3  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x04e8  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x05fc  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0604  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x0609  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x0601  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x0634  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x0637  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x0679  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x06b4  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x06c2  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x06cf  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x06da  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x0793  */
    /* JADX WARN: Removed duplicated region for block: B:392:0x0798  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x07c6  */
    /* JADX WARN: Removed duplicated region for block: B:401:0x07d2  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x07e4  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x0805  */
    /* JADX WARN: Removed duplicated region for block: B:415:0x0811  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x0795  */
    /* JADX WARN: Removed duplicated region for block: B:422:0x0641  */
    /* JADX WARN: Removed duplicated region for block: B:429:0x065d  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x0660  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x0646  */
    /* JADX WARN: Removed duplicated region for block: B:435:0x05a7  */
    /* JADX WARN: Removed duplicated region for block: B:451:0x05a9  */
    /* JADX WARN: Removed duplicated region for block: B:452:0x04a7  */
    /* JADX WARN: Removed duplicated region for block: B:453:0x04a0  */
    /* JADX WARN: Removed duplicated region for block: B:477:0x0511  */
    /* JADX WARN: Removed duplicated region for block: B:483:0x0364  */
    /* JADX WARN: Removed duplicated region for block: B:488:0x037f A[EDGE_INSN: B:488:0x037f->B:200:0x037f BREAK  A[LOOP:1: B:482:0x0362->B:485:0x037c], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:489:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:508:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:531:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x013d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.window.TransitionInfo calculateTransitionInfo(
            int r29,
            int r30,
            java.util.ArrayList r31,
            android.view.SurfaceControl.Transaction r32) {
        /*
            Method dump skipped, instructions count: 2085
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.Transition.calculateTransitionInfo(int, int,"
                    + " java.util.ArrayList,"
                    + " android.view.SurfaceControl$Transaction):android.window.TransitionInfo");
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0132 A[LOOP:1: B:38:0x0132->B:40:0x0138, LOOP_START, PHI: r0
    0x0132: PHI (r0v4 com.android.server.wm.WindowContainer) = (r0v3 com.android.server.wm.WindowContainer), (r0v5 com.android.server.wm.WindowContainer) binds: [B:26:0x0116, B:40:0x0138] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void calculateTransitionRoots(
            android.window.TransitionInfo r16,
            java.util.ArrayList r17,
            android.view.SurfaceControl.Transaction r18) {
        /*
            Method dump skipped, instructions count: 426
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.Transition.calculateTransitionRoots(android.window.TransitionInfo,"
                    + " java.util.ArrayList, android.view.SurfaceControl$Transaction):void");
    }

    public static boolean containsChangeFor(WindowContainer windowContainer, ArrayList arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (((ChangeInfo) arrayList.get(size)).mContainer == windowContainer) {
                return true;
            }
        }
        return false;
    }

    public static Transition fromBinder(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        try {
            return (Transition) ((Token) iBinder).mTransition.get();
        } catch (ClassCastException e) {
            Slog.w("Transition", "Invalid transition token: " + iBinder, e);
            return null;
        }
    }

    public static WindowContainer getAnimatableParent(WindowContainer windowContainer) {
        WindowContainer parent = windowContainer.getParent();
        while (parent != null
                && !parent.canCreateRemoteAnimationTarget()
                && !parent.isOrganized()) {
            parent = parent.getParent();
        }
        return parent;
    }

    public static int getDisplayId(WindowContainer windowContainer) {
        if (windowContainer.getDisplayContent() != null) {
            return windowContainer.getDisplayContent().mDisplayId;
        }
        return -1;
    }

    public static WindowManager.LayoutParams getLayoutParamsForAnimationsStyle(
            ArrayList arrayList, final int i) {
        final ArraySet arraySet = new ArraySet();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            WindowContainer windowContainer = ((ChangeInfo) arrayList.get(i2)).mContainer;
            if (windowContainer.asActivityRecord() != null) {
                arraySet.add(Integer.valueOf(windowContainer.getActivityType()));
            } else if (windowContainer.asWindowToken() == null
                    && windowContainer.asWindowState() == null) {
                return null;
            }
        }
        if (arraySet.isEmpty()) {
            return null;
        }
        ActivityRecord lookForTopWindowWithFilter =
                lookForTopWindowWithFilter(
                        arrayList,
                        new Predicate() { // from class:
                                          // com.android.server.wm.Transition$$ExternalSyntheticLambda8
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                int i3 = i;
                                ArraySet arraySet2 = arraySet;
                                RemoteAnimationDefinition remoteAnimationDefinition =
                                        ((ActivityRecord) obj).mRemoteAnimationDefinition;
                                return remoteAnimationDefinition != null
                                        && remoteAnimationDefinition.hasTransition(i3, arraySet2);
                            }
                        });
        if (lookForTopWindowWithFilter == null
                && (lookForTopWindowWithFilter =
                                lookForTopWindowWithFilter(
                                        arrayList, new Transition$$ExternalSyntheticLambda2(2)))
                        == null) {
            lookForTopWindowWithFilter =
                    lookForTopWindowWithFilter(
                            arrayList, new Transition$$ExternalSyntheticLambda2(1));
        }
        WindowState findMainWindow =
                lookForTopWindowWithFilter != null
                        ? lookForTopWindowWithFilter.findMainWindow(true)
                        : null;
        if (findMainWindow != null) {
            return findMainWindow.mAttrs;
        }
        return null;
    }

    public static SurfaceControl getLeashSurface(
            SurfaceControl.Transaction transaction, WindowContainer windowContainer) {
        WindowToken asWindowToken;
        DisplayContent asDisplayContent = windowContainer.asDisplayContent();
        if (asDisplayContent != null) {
            return asDisplayContent.getWindowingLayer();
        }
        if (!windowContainer.mTransitionController.useShellTransitionsRotation()
                && (asWindowToken = windowContainer.asWindowToken()) != null) {
            SurfaceControl orCreateFixedRotationLeash =
                    transaction != null
                            ? asWindowToken.getOrCreateFixedRotationLeash(transaction)
                            : asWindowToken.mFixedRotationTransformLeash;
            if (orCreateFixedRotationLeash != null) {
                return orCreateFixedRotationLeash;
            }
        }
        return windowContainer.getSurfaceControl();
    }

    public static boolean isWallpaper(WindowContainer windowContainer) {
        return windowContainer.asWallpaperToken() != null;
    }

    public static ActivityRecord lookForTopWindowWithFilter(List list, Predicate predicate) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            WindowContainer windowContainer = ((ChangeInfo) list.get(i)).mContainer;
            ActivityRecord topNonFinishingActivity =
                    windowContainer.asTaskFragment() != null
                            ? windowContainer
                                    .asTaskFragment()
                                    .getTopNonFinishingActivity(true, true)
                            : windowContainer.asActivityRecord();
            if (topNonFinishingActivity != null && predicate.test(topNonFinishingActivity)) {
                return topNonFinishingActivity;
            }
        }
        return null;
    }

    public static void resetSurfaceTransform(
            SurfaceControl.Transaction transaction,
            WindowContainer windowContainer,
            SurfaceControl surfaceControl) {
        windowContainer.getRelativePosition(new Point());
        transaction.setPosition(surfaceControl, r0.x, r0.y);
        if (windowContainer.asTaskFragment() == null) {
            transaction.setCrop(surfaceControl, null);
        } else {
            Rect resolvedOverrideBounds = windowContainer.getResolvedOverrideBounds();
            if (!CoreRune.MW_SPLIT_SHELL_TRANSITION
                    || !windowContainer.inSplitScreenWindowingMode()
                    || windowContainer.asTask() == null
                    || windowContainer.asTask().mCreatedByOrganizer
                    || resolvedOverrideBounds.isEmpty()
                    || windowContainer.getParent() == null) {
                transaction.setWindowCrop(
                        surfaceControl,
                        resolvedOverrideBounds.width(),
                        resolvedOverrideBounds.height());
            } else {
                Rect bounds = windowContainer.getParent().getBounds();
                transaction.setWindowCrop(surfaceControl, bounds.width(), bounds.height());
            }
        }
        transaction.setMatrix(
                surfaceControl,
                1.0f,
                FullScreenMagnificationGestureHandler.MAX_SCALE,
                FullScreenMagnificationGestureHandler.MAX_SCALE,
                1.0f);
        if (windowContainer.isOrganized() && windowContainer.matchParentBounds()) {
            transaction.setWindowCrop(surfaceControl, -1, -1);
        }
    }

    public final void abort() {
        ITransitionPlayer transitionPlayer;
        int i = this.mState;
        if (i == 3) {
            return;
        }
        if (i == -1) {
            if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                Slog.d(
                        "WindowManager",
                        "Aborting Transition: "
                                + this.mSyncId
                                + " in STATE_PENDING called from"
                                + Debug.getCaller());
            }
            this.mState = 3;
            return;
        }
        if (i != 0 && i != 1) {
            throw new IllegalStateException("Too late to abort. state=" + this.mState);
        }
        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
            Slog.d(
                    "WindowManager",
                    "Aborting Transition: "
                            + this.mSyncId
                            + " in state "
                            + this.mState
                            + " called from "
                            + Debug.getCaller());
        } else if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                    1230784960534033968L,
                    1,
                    null,
                    Long.valueOf(this.mSyncId));
        }
        this.mState = 3;
        long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        TransitionController.Logger logger = this.mLogger;
        logger.mAbortTimeNs = elapsedRealtimeNanos;
        TransitionController transitionController = this.mController;
        transitionController.mTransitionTracer.logAbortedTransition(this);
        int i2 = this.mSyncId;
        BLASTSyncEngine bLASTSyncEngine = this.mSyncEngine;
        BLASTSyncEngine.SyncGroup syncGroup = bLASTSyncEngine.getSyncGroup(i2);
        ArrayList arrayList = BLASTSyncEngine.SyncGroup.NO_DEPENDENCIES;
        syncGroup.finishNow();
        bLASTSyncEngine.removeFromDependencies(syncGroup);
        for (int i3 = 0; i3 < transitionController.mLegacyListeners.size(); i3++) {
            ((WindowManagerInternal.AppTransitionListener)
                            transitionController.mLegacyListeners.get(i3))
                    .onAppTransitionCancelledLocked(false);
        }
        invokeTransitionEndedListeners();
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            ChangeTransitionController changeTransitionController = this.mChangeTransitController;
            changeTransitionController.getClass();
            if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
                if (changeTransitionController.mTransitionToChangingPipTask.containsKey(this)) {
                    Slog.d(
                            "ChangeTransitionController",
                            "onTransitionAborted: "
                                    + this
                                    + ", changingPipTasks="
                                    + changeTransitionController.mTransitionToChangingPipTask);
                    ((Task) changeTransitionController.mTransitionToChangingPipTask.get(this))
                                    .mIsChangingPipToSplit =
                            false;
                }
                changeTransitionController.mTransitionToChangingPipTask.clear();
            }
        }
        if (CoreRune.MW_SHELL_TRANSITION) {
            transitionController.getClass();
            if (logger.mRequest == null
                    || (transitionPlayer = transitionController.getTransitionPlayer()) == null) {
                return;
            }
            try {
                transitionPlayer.transitionAborted(this.mToken);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public final void addFlag(int i) {
        this.mFlags = i | this.mFlags;
    }

    public boolean allReady() {
        return this.mReadyTrackerOld.allReady();
    }

    public final void applyDisplayChangeIfNeeded(ArraySet arraySet) {
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            DisplayContent asDisplayContent =
                    ((WindowContainer) this.mParticipants.valueAt(size)).asDisplayContent();
            if (asDisplayContent != null
                    && ((ChangeInfo) this.mChanges.get(asDisplayContent)).hasChanged()) {
                boolean sendNewConfiguration = asDisplayContent.sendNewConfiguration();
                if (!this.mReadyTrackerOld.mUsed) {
                    setReady(asDisplayContent, true);
                }
                if (sendNewConfiguration
                        && this.mController.mAtm.mTaskSupervisor.mDeferRootVisibilityUpdate) {
                    asDisplayContent.forAllActivities(
                            new Transition$$ExternalSyntheticLambda11(1, arraySet));
                }
            }
        }
    }

    public final void applyReady() {
        boolean allReady;
        if (this.mState < 1) {
            return;
        }
        TransitionController transitionController = this.mController;
        if (transitionController.mFullReadyTracking) {
            ReadyTracker readyTracker = this.mReadyTracker;
            allReady = readyTracker.mConditions.isEmpty() && !readyTracker.mMet.isEmpty();
        } else {
            allReady = this.mReadyTrackerOld.allReady();
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                    -3942072270654590479L,
                    7,
                    null,
                    Boolean.valueOf(allReady),
                    Long.valueOf(this.mSyncId));
        }
        if (this.mSyncEngine.setReady(this.mSyncId, allReady) && allReady) {
            this.mLogger.mReadyTimeNs = SystemClock.elapsedRealtimeNanos();
            this.mOnTopTasksAtReady.clear();
            for (int i = 0; i < this.mTargetDisplays.size(); i++) {
                addOnTopTasks(
                        (DisplayContent) this.mTargetDisplays.get(i), this.mOnTopTasksAtReady);
            }
            transitionController.tryStartCollectFromQueue();
        }
    }

    public final void buildFinishTransaction(
            SurfaceControl.Transaction transaction, TransitionInfo transitionInfo) {
        int i;
        ArraySet arraySet = new ArraySet();
        int size = this.mTargets.size() - 1;
        while (true) {
            i = 0;
            if (size < 0) {
                break;
            }
            WindowContainer windowContainer = ((ChangeInfo) this.mTargets.get(size)).mContainer;
            if (windowContainer.getParent() != null) {
                SurfaceControl leashSurface = getLeashSurface(null, windowContainer);
                transaction.reparent(
                        leashSurface,
                        windowContainer.asDisplayContent() != null
                                ? windowContainer.getSurfaceControl()
                                : windowContainer.getParent().getSurfaceControl());
                transaction.setLayer(leashSurface, windowContainer.getLastLayer());
                Task asTask = windowContainer.asTask();
                boolean z = asTask != null && asTask.inFreeformWindowingMode();
                if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && z) {
                    transaction.setCrop(leashSurface, null);
                }
                boolean z2 = asTask != null && asTask.inPinnedWindowingMode();
                if (asTask != null && asTask.isStageRootTask()) {
                    i = 1;
                }
                if ((!CoreRune.MW_PIP_SHELL_TRANSITION || !z2)
                        && (!CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER || i == 0)) {
                    transaction.setCornerRadius(
                            leashSurface, FullScreenMagnificationGestureHandler.MAX_SCALE);
                    transaction.setShadowRadius(
                            leashSurface, FullScreenMagnificationGestureHandler.MAX_SCALE);
                }
                if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY
                        && windowContainer.asTransientLaunchOverlay() != null) {
                    transaction.hide(leashSurface);
                }
                if (z && asTask.isFreeformForceHidden()) {
                    transaction.setAlpha(
                            leashSurface, FullScreenMagnificationGestureHandler.MAX_SCALE);
                } else {
                    transaction.setAlpha(leashSurface, 1.0f);
                }
                arraySet.add(windowContainer.getDisplayContent());
                if ((((ChangeInfo) this.mTargets.get(size)).mFlags & 64) == 0
                        && (windowContainer.asWallpaperToken() == null
                                || !windowContainer.asWallpaperToken().mIsPortraitWindowToken)) {
                    resetSurfaceTransform(transaction, windowContainer, leashSurface);
                }
            }
            size--;
        }
        IContainerFreezer iContainerFreezer = this.mContainerFreezer;
        if (iContainerFreezer != null) {
            ScreenshotFreezer screenshotFreezer = (ScreenshotFreezer) iContainerFreezer;
            for (int i2 = 0; i2 < screenshotFreezer.mFrozen.size(); i2++) {
                ChangeInfo changeInfo =
                        (ChangeInfo)
                                Transition.this.mChanges.get(screenshotFreezer.mFrozen.valueAt(i2));
                Objects.requireNonNull(changeInfo);
                SurfaceControl surfaceControl = changeInfo.mSnapshot;
                if (surfaceControl != null) {
                    transaction.reparent(surfaceControl, null);
                }
            }
        }
        for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
            if (arraySet.valueAt(size2) != null) {
                assignLayers(transaction, (WindowContainer) arraySet.valueAt(size2));
            }
        }
        while (i < transitionInfo.getRootCount()) {
            transaction.reparent(transitionInfo.getRoot(i).getLeash(), null);
            i++;
        }
    }

    public final void calcParallelCollectType(
            WindowContainerTransaction windowContainerTransaction) {
        Bundle launchOptions;
        for (int i = 0; i < windowContainerTransaction.getHierarchyOps().size(); i++) {
            WindowContainerTransaction.HierarchyOp hierarchyOp =
                    (WindowContainerTransaction.HierarchyOp)
                            windowContainerTransaction.getHierarchyOps().get(i);
            if (hierarchyOp.getType() == 7
                    && (launchOptions = hierarchyOp.getLaunchOptions()) != null
                    && !launchOptions.isEmpty()
                    && launchOptions.getBoolean("android.activity.transientLaunch")) {
                if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                    if (ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS,
                                4330128790342680291L,
                                0,
                                "Starting a Recents transition which can be parallel.",
                                null);
                    }
                } else if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                            -2700498872917476567L,
                            0,
                            null,
                            null);
                }
                this.mParallelCollectType = 2;
            }
        }
    }

    public final void cleanUpInternal() {
        SurfaceControl surfaceControl;
        for (int i = 0; i < this.mChanges.size(); i++) {
            ChangeInfo changeInfo = (ChangeInfo) this.mChanges.valueAt(i);
            SurfaceControl surfaceControl2 = changeInfo.mSnapshot;
            if (surfaceControl2 != null) {
                surfaceControl2.release();
            }
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION
                    && (surfaceControl = changeInfo.mChangeLeash) != null) {
                surfaceControl.release();
                changeInfo.mChangeLeash = null;
            }
            if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                changeInfo.mMinimizeAnimState = 0;
                changeInfo.mMinimizePoint.set(
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE);
            }
            Task asTask = changeInfo.mContainer.asTask();
            if (asTask != null) {
                asTask.setBoostTaskLayerForFreeform(false, false);
            }
        }
        SurfaceControl.Transaction transaction = this.mCleanupTransaction;
        if (transaction != null) {
            transaction.apply();
            this.mCleanupTransaction = null;
        }
    }

    public final void collect(WindowContainer windowContainer, boolean z) {
        if (this.mState < 0) {
            throw new IllegalStateException("Transition hasn't started collecting.");
        }
        if (isCollecting()) {
            if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX && isWallpaper(windowContainer)) {
                DisplayContent displayContent = windowContainer.mDisplayContent;
                if (!displayContent.isDefaultDisplay
                        && !this.mTargetDisplays.contains(displayContent)) {
                    ActivityManagerService$$ExternalSyntheticOutline0.m(
                            10,
                            new StringBuilder("don't collect wallpaper of other display. caller="),
                            "Transition");
                    return;
                }
            }
            if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                if (ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS,
                            4599273309511892975L,
                            1,
                            "Collecting in transition %d: %s, caller=%s",
                            Long.valueOf(this.mSyncId),
                            String.valueOf(windowContainer),
                            String.valueOf(Debug.getCallers(5)));
                }
            } else if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                        -4672522645315112127L,
                        1,
                        null,
                        Long.valueOf(this.mSyncId),
                        String.valueOf(windowContainer));
            }
            snapshotStartState(getAnimatableParent(windowContainer));
            if (!this.mParticipants.contains(windowContainer) || z) {
                if (!isInTransientHide(windowContainer)) {
                    this.mSyncEngine.addToSyncSet(this.mSyncId, windowContainer);
                }
                if (windowContainer.asWindowToken() == null
                        || !windowContainer.asWindowToken().mRoundedCornerOverlay) {
                    ChangeInfo changeInfo = (ChangeInfo) this.mChanges.get(windowContainer);
                    if (changeInfo == null) {
                        changeInfo = new ChangeInfo(windowContainer);
                        updateTransientFlags(changeInfo);
                        WindowContainer windowContainer2 = changeInfo.mContainer;
                        if (windowContainer2.asActivityRecord() != null) {
                            TransitionController.Logger logger = this.mLogger;
                            TransitionRequestInfo transitionRequestInfo = logger.mRequest;
                            if (((transitionRequestInfo == null
                                                    || transitionRequestInfo.getType() != 6
                                                    || logger.mRequest.getDisplayChange() == null)
                                            ? false
                                            : logger.mRequest
                                                    .getDisplayChange()
                                                    .isPhysicalDisplayChanged())
                                    && windowContainer2.asActivityRecord().task != null
                                    && windowContainer2
                                                    .asActivityRecord()
                                                    .task
                                                    .topRunningActivity(false)
                                            != null
                                    && windowContainer2
                                            .asActivityRecord()
                                            .task
                                            .topRunningActivity(false)
                                            .mPopOverState
                                            .mIsActivated) {
                                changeInfo.mFlags |= 262144;
                            }
                        }
                        this.mChanges.put(windowContainer, changeInfo);
                    }
                    this.mParticipants.add(windowContainer);
                    recordDisplay(windowContainer.getDisplayContent());
                    if (changeInfo.mShowWallpaper) {
                        WallpaperController.FindWallpaperTargetResult findWallpaperTargetResult =
                                windowContainer.mDisplayContent.mWallpaperController.mFindResults;
                        WallpaperController.FindWallpaperTargetResult.TopWallpaper topWallpaper =
                                findWallpaperTargetResult.mTopWallpaper;
                        if (topWallpaper.mTopShowWhenLockedWallpaper != null) {
                            if (Flags.ensureWallpaperInTransitions()) {
                                collect(topWallpaper.mTopShowWhenLockedWallpaper.mToken, false);
                            } else {
                                collect(topWallpaper.mTopShowWhenLockedWallpaper, false);
                            }
                        }
                        if (findWallpaperTargetResult.mTopWallpaper.mTopHideWhenLockedWallpaper
                                != null) {
                            if (Flags.ensureWallpaperInTransitions()) {
                                collect(topWallpaper.mTopHideWhenLockedWallpaper.mToken, false);
                            } else {
                                collect(topWallpaper.mTopHideWhenLockedWallpaper, false);
                            }
                        }
                    }
                }
            }
        }
    }

    public final void collectClose(WindowContainer windowContainer) {
        if (windowContainer.isVisibleRequested()) {
            collectExistenceChange(windowContainer);
        } else {
            collect(windowContainer, false);
        }
    }

    public final void collectExistenceChange(WindowContainer windowContainer) {
        if (this.mState >= 2) {
            return;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                    1101215730201607371L,
                    1,
                    null,
                    Long.valueOf(this.mSyncId),
                    String.valueOf(windowContainer));
        }
        collect(windowContainer, false);
        ((ChangeInfo) this.mChanges.get(windowContainer)).mExistenceChanged = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0097  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void collectOrderChanges(boolean r11) {
        /*
            r10 = this;
            java.util.ArrayList r0 = r10.mOnTopTasksStart
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L9
            return
        L9:
            r0 = 0
            r1 = r0
        Lb:
            java.util.ArrayList r2 = r10.mOnTopTasksAtReady
            int r2 = r2.size()
            if (r1 >= r2) goto L26
            java.util.ArrayList r2 = r10.mOnTopTasksAtReady
            java.lang.Object r2 = r2.get(r1)
            com.android.server.wm.Task r2 = (com.android.server.wm.Task) r2
            java.util.ArrayList r3 = r10.mOnTopTasksStart
            boolean r2 = r3.contains(r2)
            if (r2 == 0) goto L29
            int r1 = r1 + 1
            goto Lb
        L26:
            if (r11 != 0) goto L29
            return
        L29:
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
        L2e:
            java.util.ArrayList r1 = r10.mTargetDisplays
            int r1 = r1.size()
            if (r0 >= r1) goto Le4
            java.util.ArrayList r1 = r10.mTargetDisplays
            java.lang.Object r1 = r1.get(r0)
            com.android.server.wm.DisplayContent r1 = (com.android.server.wm.DisplayContent) r1
            addOnTopTasks(r1, r11)
            java.util.ArrayList r1 = r10.mTargetDisplays
            java.lang.Object r1 = r1.get(r0)
            com.android.server.wm.DisplayContent r1 = (com.android.server.wm.DisplayContent) r1
            int r1 = r1.mDisplayId
            com.android.server.wm.TransitionController r2 = r10.mController
            android.util.SparseArray r3 = r2.mLatestOnTopTasksReported
            java.lang.Object r3 = r3.get(r1)
            java.util.ArrayList r3 = (java.util.ArrayList) r3
            int r4 = r11.size()
            int r4 = r4 + (-1)
            r5 = 0
        L5c:
            if (r4 < 0) goto Lcf
            java.lang.Object r6 = r11.get(r4)
            com.android.server.wm.Task r6 = (com.android.server.wm.Task) r6
            int r7 = r6.getDisplayId()
            if (r7 == r1) goto L6b
            goto Lcc
        L6b:
            if (r3 != 0) goto L83
            java.util.ArrayList r7 = r10.mOnTopTasksStart
            boolean r7 = r7.contains(r6)
            if (r7 == 0) goto L76
            goto Lcc
        L76:
            boolean r7 = com.samsung.android.rune.CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION
            if (r7 == 0) goto L8a
            if (r5 == 0) goto L8a
            com.android.server.wm.WindowContainer r7 = r5.getParent()
            if (r7 != r6) goto L8a
            goto Lcc
        L83:
            boolean r7 = r3.contains(r6)
            if (r7 == 0) goto L8a
            goto Lcc
        L8a:
            android.util.ArraySet r7 = r10.mParticipants
            r7.add(r6)
            android.util.ArrayMap r7 = r10.mChanges
            int r7 = r7.indexOfKey(r6)
            if (r7 >= 0) goto La7
            android.util.ArrayMap r7 = r10.mChanges
            com.android.server.wm.Transition$ChangeInfo r8 = new com.android.server.wm.Transition$ChangeInfo
            r8.<init>(r6)
            r7.put(r6, r8)
            android.util.ArrayMap r7 = r10.mChanges
            int r7 = r7.indexOfKey(r6)
        La7:
            android.util.ArrayMap r8 = r10.mChanges
            java.lang.Object r8 = r8.valueAt(r7)
            com.android.server.wm.Transition$ChangeInfo r8 = (com.android.server.wm.Transition.ChangeInfo) r8
            int r9 = r8.mFlags
            r9 = r9 | 32
            r8.mFlags = r9
            boolean r8 = com.samsung.android.rune.CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION
            if (r8 == 0) goto Lcc
            boolean r8 = r6.inSplitScreenWindowingMode()
            if (r8 == 0) goto Lcc
            android.util.ArrayMap r8 = r10.mChanges
            java.lang.Object r7 = r8.valueAt(r7)
            com.android.server.wm.Transition$ChangeInfo r7 = (com.android.server.wm.Transition.ChangeInfo) r7
            boolean r7 = r7.mResumedAffordance
            if (r7 == 0) goto Lcc
            r5 = r6
        Lcc:
            int r4 = r4 + (-1)
            goto L5c
        Lcf:
            android.util.SparseArray r2 = r2.mLatestOnTopTasksReported
            r2.put(r1, r11)
            if (r3 == 0) goto Ld8
            r11 = r3
            goto Ldd
        Ld8:
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
        Ldd:
            r11.clear()
            int r0 = r0 + 1
            goto L2e
        Le4:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.Transition.collectOrderChanges(boolean):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:146:0x044d, code lost:

       if (r2.inSplitScreenWindowingMode() != false) goto L190;
    */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0469, code lost:

       if (com.android.server.wm.MultiTaskingAppCompatConfiguration.isPresetWallpaperLetterboxed(r2 != null ? r2.getTopNonFinishingActivity(true, true) : r25.asActivityRecord()) != false) goto L190;
    */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x04be, code lost:

       if ((r3.mChangeTransitFlags & 1) != 0) goto L226;
    */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0525  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x052a  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0530  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0598  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x05a2  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x05c6  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0607  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0628  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x060a  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0532  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x052c  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0527  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void collectVisibleChange(com.android.server.wm.WindowContainer r25) {
        /*
            Method dump skipped, instructions count: 1661
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.Transition.collectVisibleChange(com.android.server.wm.WindowContainer):void");
    }

    public final boolean didCommitTransientLaunch() {
        if (this.mTransientLaunches == null) {
            return false;
        }
        for (int i = 0; i < this.mTransientLaunches.size(); i++) {
            if (((ActivityRecord) this.mTransientLaunches.keyAt(i)).isVisibleRequested()) {
                return true;
            }
        }
        return false;
    }

    public SurfaceControl.Transaction getFinishTransaction() {
        return this.mFinishTransaction;
    }

    public SurfaceControl.Transaction getStartTransaction() {
        return this.mStartTransaction;
    }

    public final void handleLegacyRecentsStartBehavior(
            DisplayContent displayContent, TransitionInfo transitionInfo) {
        WindowState windowState;
        WindowToken windowToken;
        Task fromWindowContainerToken;
        if ((this.mFlags & 128) == 0) {
            return;
        }
        InputConsumerImpl inputConsumer =
                displayContent.mInputMonitor.getInputConsumer("recents_animation_input_consumer");
        WindowContainer windowContainer = null;
        ActivityRecord activityRecord = null;
        if (inputConsumer != null) {
            Task task = null;
            for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
                ActivityManager.RunningTaskInfo taskInfo =
                        ((TransitionInfo.Change) transitionInfo.getChanges().get(i)).getTaskInfo();
                if (taskInfo != null
                        && (fromWindowContainerToken =
                                        Task.fromWindowContainerToken(taskInfo.token))
                                != null) {
                    int i2 = taskInfo.topActivityType;
                    boolean z = i2 == 2 || i2 == 3;
                    if (z && activityRecord == null) {
                        activityRecord =
                                fromWindowContainerToken.getTopVisibleActivity(true, false);
                    } else if (!z && task == null) {
                        task = fromWindowContainerToken;
                    }
                }
            }
            if (activityRecord != null && task != null) {
                inputConsumer.mWindowHandle.touchableRegion.set(task.getBounds());
                displayContent.mInputMonitor.setActiveRecents(task, activityRecord);
            }
        }
        if (activityRecord == null) {
            return;
        }
        this.mRecentsDisplayId = displayContent.mDisplayId;
        if (displayContent.mDisplayPolicy.shouldAttachNavBarToAppDuringTransition()
                && displayContent.getAsyncRotationController() == null) {
            for (int i3 = 0; i3 < transitionInfo.getChanges().size(); i3++) {
                TransitionInfo.Change change =
                        (TransitionInfo.Change) transitionInfo.getChanges().get(i3);
                if (change.getTaskInfo() != null
                        && change.getTaskInfo().displayId == this.mRecentsDisplayId
                        && change.getTaskInfo().getActivityType() == 1
                        && (change.getMode() == 2 || change.getMode() == 4)) {
                    windowContainer = WindowContainer.fromBinder(change.getContainer().asBinder());
                    break;
                }
            }
            if (windowContainer == null
                    || windowContainer.inMultiWindowMode()
                    || (windowState = displayContent.mDisplayPolicy.mNavigationBar) == null
                    || (windowToken = windowState.mToken) == null) {
                return;
            }
            this.mController.mNavigationBarAttachedToApp = true;
            windowToken.cancelAnimation();
            SurfaceControl.Transaction pendingTransaction =
                    windowState.mToken.getPendingTransaction();
            SurfaceControl surfaceControl = windowState.mToken.mSurfaceControl;
            pendingTransaction.reparent(surfaceControl, windowContainer.getSurfaceControl());
            pendingTransaction.show(surfaceControl);
            DisplayContent.ImeContainer imeContainer = displayContent.mImeWindowsContainer;
            if (imeContainer.isVisible()) {
                pendingTransaction.setRelativeLayer(
                        surfaceControl, imeContainer.getSurfaceControl(), 1);
            } else {
                pendingTransaction.setLayer(surfaceControl, Integer.MAX_VALUE);
            }
            StatusBarManagerInternal statusBarManagerInternal =
                    displayContent.mDisplayPolicy.getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                int i4 = this.mRecentsDisplayId;
                IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
                if (iStatusBar != null) {
                    try {
                        iStatusBar.setNavigationBarLumaSamplingEnabled(i4, false);
                    } catch (RemoteException unused) {
                    }
                }
            }
        }
    }

    public final boolean hasTransientLaunch() {
        ArrayMap arrayMap = this.mTransientLaunches;
        return (arrayMap == null || arrayMap.isEmpty()) ? false : true;
    }

    public final void invokeTransitionEndedListeners() {
        if (this.mTransitionEndedListeners == null) {
            return;
        }
        for (int i = 0; i < this.mTransitionEndedListeners.size(); i++) {
            ((Runnable) this.mTransitionEndedListeners.get(i)).run();
        }
        this.mTransitionEndedListeners = null;
    }

    public final boolean isCollecting() {
        int i = this.mState;
        return i == 0 || i == 1;
    }

    public final boolean isInTransientHide(WindowContainer windowContainer) {
        ArrayList arrayList = this.mTransientHideTasks;
        if (arrayList == null) {
            return false;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mTransientHideTasks.get(size);
            if (windowContainer == task || windowContainer.isDescendantOf(task)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isInTransition(WindowContainer windowContainer) {
        while (windowContainer != null) {
            if (this.mParticipants.contains(windowContainer)) {
                return true;
            }
            windowContainer = windowContainer.getParent();
        }
        return false;
    }

    public final boolean isTransientLaunch(ActivityRecord activityRecord) {
        ArrayMap arrayMap = this.mTransientLaunches;
        return arrayMap != null && arrayMap.containsKey(activityRecord);
    }

    public final boolean isTransientVisible(Task task) {
        WindowContainer parent;
        PictureInPictureParams pictureInPictureParams;
        ArrayMap arrayMap = this.mTransientLaunches;
        if (arrayMap == null) {
            return false;
        }
        int size = arrayMap.size();
        int i = 0;
        for (int i2 = size - 1; i2 >= 0; i2--) {
            Task rootTask = ((ActivityRecord) this.mTransientLaunches.keyAt(i2)).getRootTask();
            if (rootTask != null
                    && (parent = rootTask.getParent()) != null
                    && parent.getTopChild() != rootTask) {
                ActivityTaskSupervisor.OpaqueActivityHelper opaqueActivityHelper =
                        this.mController.mAtm.mTaskSupervisor.mOpaqueActivityHelper;
                opaqueActivityHelper.mIncludeInvisibleAndFinishing = true;
                opaqueActivityHelper.mIgnoringKeyguard = true;
                opaqueActivityHelper.mIgnoreFloatingWindow = true;
                ActivityRecord activity = parent.getActivity(opaqueActivityHelper, true, null);
                if (rootTask.compareTo((WindowContainer) activity.getRootTask()) < 0) {
                    ActivityRecord activityRecord = task.topRunningActivity(false);
                    if (this.mCanPipOnFinish
                            && isInTransientHide(task)
                            && activityRecord != null
                            && activityRecord.checkEnterPictureInPictureState(
                                    "startActivityUnchecked", false, false)
                            && (pictureInPictureParams = activityRecord.pictureInPictureArgs)
                                    != null
                            && pictureInPictureParams.isAutoEnterEnabled()
                            && activity.isActivityTypeHome()
                            && rootTask.isActivityTypeRecents()) {
                        Slog.d(
                                "Transition",
                                "PipTask isTransientVisible: Allow for auto-pip: " + task);
                        return true;
                    }
                    i++;
                } else {
                    continue;
                }
            }
        }
        if (i != size) {
            return isInTransientHide(task);
        }
        for (int size2 = this.mTransientLaunches.size() - 1; size2 >= 0; size2--) {
            if (((ActivityRecord) this.mTransientLaunches.keyAt(size2)).isDescendantOf(task)) {
                return true;
            }
        }
        return false;
    }

    public final void legacyRestoreNavigationBarFromApp() {
        IStatusBar iStatusBar;
        TransitionController transitionController = this.mController;
        if (transitionController.mNavigationBarAttachedToApp) {
            boolean z = false;
            transitionController.mNavigationBarAttachedToApp = false;
            int i = this.mRecentsDisplayId;
            if (i == -1) {
                Slog.i(
                        "Transition",
                        "Restore parent surface of navigation bar by another transition");
                i = 0;
            }
            DisplayContent displayContent =
                    transitionController.mAtm.mRootWindowContainer.getDisplayContent(i);
            StatusBarManagerInternal statusBarManagerInternal =
                    displayContent.mDisplayPolicy.getStatusBarManagerInternal();
            if (statusBarManagerInternal != null
                    && (iStatusBar = StatusBarManagerService.this.mBar) != null) {
                try {
                    iStatusBar.setNavigationBarLumaSamplingEnabled(i, true);
                } catch (RemoteException unused) {
                }
            }
            WindowState windowState = displayContent.mDisplayPolicy.mNavigationBar;
            if (windowState == null) {
                return;
            }
            windowState.mSurfaceTranslationY = 0;
            WindowToken windowToken = windowState.mToken;
            if (windowToken == null) {
                return;
            }
            SurfaceControl.Transaction pendingTransaction = displayContent.getPendingTransaction();
            WindowContainer parent = windowToken.getParent();
            pendingTransaction.setLayer(windowToken.mSurfaceControl, windowToken.getLastLayer());
            int i2 = 0;
            while (true) {
                if (i2 < this.mTargets.size()) {
                    Task asTask = ((ChangeInfo) this.mTargets.get(i2)).mContainer.asTask();
                    if (asTask != null && asTask.isActivityTypeHomeOrRecents()) {
                        z = asTask.isVisibleRequested();
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
            if (z) {
                new NavBarFadeAnimationController(displayContent).fadeWindowToken(true);
            } else {
                pendingTransaction.reparent(
                        windowToken.mSurfaceControl, parent.getSurfaceControl());
            }
            displayContent.mWmService.scheduleAnimationLocked();
        }
    }

    @Override // com.android.server.wm.BLASTSyncEngine.TransactionReadyListener
    public final void onReadyTimeout() {
        if (!this.mController.mFullReadyTracking) {
            StringBuilder sb = new StringBuilder("#");
            sb.append(this.mSyncId);
            sb.append(" readiness timeout, used=");
            ReadyTrackerOld readyTrackerOld = this.mReadyTrackerOld;
            sb.append(readyTrackerOld.mUsed);
            sb.append(" deferReadyDepth=");
            sb.append(readyTrackerOld.mDeferReadyDepth);
            sb.append(" group=");
            sb.append(readyTrackerOld.mReadyGroups);
            Slog.e("Transition", sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder("#");
        sb2.append(this.mSyncId);
        sb2.append(" met conditions: ");
        ReadyTracker readyTracker = this.mReadyTracker;
        sb2.append(readyTracker.mMet);
        Slog.e("Transition", sb2.toString());
        Slog.e("Transition", "#" + this.mSyncId + " unmet conditions: " + readyTracker.mConditions);
    }

    public final void onSeamlessRotating(DisplayContent displayContent) {
        if (this.mSyncEngine.getSyncSet(this.mSyncId).mSyncMethod == 1) {
            return;
        }
        if (this.mContainerFreezer == null) {
            this.mContainerFreezer = new ScreenshotFreezer();
        }
        WindowState windowState = displayContent.mDisplayPolicy.mTopFullscreenOpaqueWindowState;
        if (windowState != null) {
            this.mIsSeamlessRotation = true;
            windowState.mSyncMethodOverride = 1;
            if (!CoreRune.FW_SHELL_TRANSITION_LOG) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                            2734227875286695843L,
                            0,
                            null,
                            String.valueOf(windowState.getName()));
                    return;
                }
                return;
            }
            if (ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS,
                        -9070539140885954971L,
                        0,
                        "Override sync-method for %s because seamless rotating, caller=%s",
                        String.valueOf(windowState.getName()),
                        String.valueOf(Debug.getCallers(5)));
            }
        }
    }

    @Override // com.android.server.wm.BLASTSyncEngine.TransactionReadyListener
    public final void onTransactionCommitTimeout() {
        SurfaceControl surfaceControl;
        if (this.mCleanupTransaction == null) {
            return;
        }
        for (int size = this.mTargetDisplays.size() - 1; size >= 0; size--) {
            DisplayContent displayContent = (DisplayContent) this.mTargetDisplays.get(size);
            AsyncRotationController asyncRotationController =
                    displayContent.getAsyncRotationController();
            if (asyncRotationController != null
                    && containsChangeFor(displayContent, this.mTargets)) {
                SurfaceControl.Transaction transaction = this.mCleanupTransaction;
                if (!asyncRotationController.mIsStartTransactionCommitted) {
                    for (int size2 = asyncRotationController.mTargetWindowTokens.size() - 1;
                            size2 >= 0;
                            size2--) {
                        AsyncRotationController.Operation operation =
                                (AsyncRotationController.Operation)
                                        asyncRotationController.mTargetWindowTokens.valueAt(size2);
                        operation.mIsCompletionPending = true;
                        if (operation.mAction == 1
                                && (surfaceControl = operation.mLeash) != null
                                && surfaceControl.isValid()) {
                            Slog.d(
                                    "AsyncRotation_WindowManager",
                                    "Transaction timeout. Clear transform for "
                                            + ((WindowToken)
                                                            asyncRotationController
                                                                    .mTargetWindowTokens.keyAt(
                                                                    size2))
                                                    .getTopChild());
                            SurfaceControl surfaceControl2 = operation.mLeash;
                            transaction.setMatrix(
                                    surfaceControl2,
                                    1.0f,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    1.0f);
                            transaction.setPosition(
                                    surfaceControl2,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:281:0x0513  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x053e  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x0546  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x065a  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x0689  */
    /* JADX WARN: Removed duplicated region for block: B:367:0x0685 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:578:0x0a61 A[Catch: RemoteException -> 0x0a6c, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x0a6c, blocks: (B:575:0x0a54, B:576:0x0a59, B:578:0x0a61), top: B:574:0x0a54 }] */
    /* JADX WARN: Removed duplicated region for block: B:583:0x0a78  */
    /* JADX WARN: Removed duplicated region for block: B:593:0x0ad1  */
    /* JADX WARN: Removed duplicated region for block: B:616:0x0b1a  */
    /* JADX WARN: Removed duplicated region for block: B:619:0x0b28  */
    /* JADX WARN: Removed duplicated region for block: B:621:? A[RETURN, SYNTHETIC] */
    @Override // com.android.server.wm.BLASTSyncEngine.TransactionReadyListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onTransactionReady(android.view.SurfaceControl.Transaction r25, int r26) {
        /*
            Method dump skipped, instructions count: 2891
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.Transition.onTransactionReady(android.view.SurfaceControl$Transaction,"
                    + " int):void");
    }

    public final void postCleanupOnFailure() {
        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
            StringBuilder sb = new StringBuilder("postCleanupOnFailure, transit=");
            sb.append(this);
            sb.append(", caller=");
            ActivityManagerService$$ExternalSyntheticOutline0.m(3, sb, "Transition");
        }
        this.mController.mAtm.mH.post(new Transition$$ExternalSyntheticLambda3(1, this));
    }

    public final void recordDisplay(DisplayContent displayContent) {
        if (displayContent == null || this.mTargetDisplays.contains(displayContent)) {
            return;
        }
        this.mTargetDisplays.add(displayContent);
        addOnTopTasks(displayContent, this.mOnTopTasksStart);
        if (this.mController.mAnimatingState) {
            displayContent.enableHighPerfTransition(true);
        }
    }

    public final void sendRemoteCallback(IRemoteCallback iRemoteCallback) {
        if (iRemoteCallback == null) {
            return;
        }
        this.mController.mAtm.mH.sendMessage(
                PooledLambda.obtainMessage(
                        new Transition$$ExternalSyntheticLambda1(1), iRemoteCallback));
    }

    public final void setAllReady() {
        if (!isCollecting() || this.mSyncId < 0) {
            return;
        }
        boolean z = ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1];
        ReadyTrackerOld readyTrackerOld = this.mReadyTrackerOld;
        if (z) {
            readyTrackerOld.getClass();
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 6039132370452820927L, 0, null, null);
        }
        readyTrackerOld.mUsed = true;
        readyTrackerOld.mReadyOverride = true;
        applyReady();
    }

    public final void setOverrideAnimation(
            TransitionInfo.AnimationOptions animationOptions,
            IRemoteCallback iRemoteCallback,
            IRemoteCallback iRemoteCallback2) {
        if (isCollecting()) {
            this.mOverrideOptions = animationOptions;
            sendRemoteCallback(this.mClientAnimationStartCallback);
            this.mClientAnimationStartCallback = iRemoteCallback;
            this.mClientAnimationFinishCallback = iRemoteCallback2;
        }
    }

    public final void setReady(WindowContainer windowContainer, boolean z) {
        if (!isCollecting() || this.mSyncId < 0) {
            return;
        }
        ReadyTrackerOld readyTrackerOld = this.mReadyTrackerOld;
        readyTrackerOld.mUsed = true;
        WindowContainer windowContainer2 = windowContainer;
        while (true) {
            if (windowContainer2 == null) {
                break;
            }
            if (windowContainer2 instanceof DisplayContent) {
                readyTrackerOld.mReadyGroups.put(windowContainer2, Boolean.valueOf(z));
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                            -4770394322045550928L,
                            3,
                            null,
                            Boolean.valueOf(z),
                            String.valueOf(windowContainer2),
                            String.valueOf(windowContainer));
                }
            } else {
                windowContainer2 = windowContainer2.getParent();
            }
        }
        applyReady();
    }

    public final boolean shouldApplyOnDisplayThread() {
        ChangeInfo changeInfo;
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            DisplayContent asDisplayContent =
                    ((WindowContainer) this.mParticipants.valueAt(size)).asDisplayContent();
            if (asDisplayContent != null
                    && (changeInfo = (ChangeInfo) this.mChanges.get(asDisplayContent)) != null) {
                if (changeInfo.mRotation != asDisplayContent.mDisplayRotation.mRotation) {
                    return Looper.myLooper() != this.mController.mAtm.mWindowManager.mH.getLooper();
                }
            }
        }
        return false;
    }

    public final boolean shouldUsePerfHint(DisplayContent displayContent) {
        TransitionInfo.AnimationOptions animationOptions = this.mOverrideOptions;
        if (animationOptions != null
                && animationOptions.getType() == 5
                && this.mType == 4
                && this.mParticipants.size() == 1) {
            return false;
        }
        return this.mTargetDisplays.contains(displayContent);
    }

    public final void snapshotStartState(WindowContainer windowContainer) {
        while (windowContainer != null && !this.mChanges.containsKey(windowContainer)) {
            ChangeInfo changeInfo = new ChangeInfo(windowContainer);
            updateTransientFlags(changeInfo);
            this.mChanges.put(windowContainer, changeInfo);
            if (windowContainer instanceof DisplayContent) {
                ReadyTrackerOld readyTrackerOld = this.mReadyTrackerOld;
                if (!readyTrackerOld.mReadyGroups.containsKey(windowContainer)) {
                    readyTrackerOld.mReadyGroups.put(windowContainer, Boolean.FALSE);
                }
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                            65881049096729394L,
                            1,
                            null,
                            Long.valueOf(this.mSyncId),
                            String.valueOf(windowContainer));
                }
            }
            windowContainer = getAnimatableParent(windowContainer);
        }
    }

    public final void start() {
        int i = this.mState;
        if (i < 0) {
            throw new IllegalStateException("Can't start Transition which isn't collecting.");
        }
        if (i >= 1) {
            StringBuilder sb = new StringBuilder("Transition already started id=");
            sb.append(this.mSyncId);
            sb.append(" state=");
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb, this.mState, "Transition");
            return;
        }
        this.mState = 1;
        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
            if (ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS,
                        6426378928388453266L,
                        1,
                        "Starting Transition %d, caller=%s",
                        Long.valueOf(this.mSyncId),
                        String.valueOf(Debug.getCallers(3)));
            }
        } else if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                    2808217645990556209L,
                    1,
                    null,
                    Long.valueOf(this.mSyncId));
        }
        applyReady();
        this.mLogger.mStartTimeNs = SystemClock.elapsedRealtimeNanos();
        this.mController.updateAnimatingState();
    }

    public final String toString() {
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(64, "TransitionRecord{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(" id=" + this.mSyncId);
        m.append(" type=" + WindowManager.transitTypeToString(this.mType));
        m.append(" flags=0x" + Integer.toHexString(this.mFlags));
        m.append('}');
        return m.toString();
    }

    public final void updateTransientFlags(ChangeInfo changeInfo) {
        WindowContainer windowContainer = changeInfo.mContainer;
        if (!(windowContainer.asTaskFragment() == null
                        && windowContainer.asActivityRecord() == null)
                && isInTransientHide(windowContainer)) {
            changeInfo.mFlags |= 4;
        }
    }
}
