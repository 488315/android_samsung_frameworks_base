package com.android.server.wm;

import android.R;
import android.app.AppOpsManager;
import android.app.WindowConfiguration;
import android.app.admin.DevicePolicyCache;
import android.app.servertransaction.WindowStateInsetsControlChangeItem;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.GraphicsProtos;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.WorkSource;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.MergedConfiguration;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.IWindow;
import android.view.IWindowFocusObserver;
import android.view.IWindowId;
import android.view.InputChannel;
import android.view.InputWindowHandle;
import android.view.InsetsFrameProvider;
import android.view.InsetsSource;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.View;
import android.view.ViewDebug;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.inputmethod.ImeTracker;
import android.window.ActivityWindowInfo;
import android.window.ClientWindowFrames;
import android.window.OnBackInvokedCallbackInfo;

import com.android.internal.policy.KeyInterceptionInfo;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.protolog.common.LogLevel;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ToBooleanFunction;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;
import com.android.server.media.MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0;
import com.android.server.policy.PhoneWindowManagerExt;
import com.android.server.policy.WindowManagerPolicy;
import com.android.window.flags.Flags;

import dalvik.annotation.optimization.NeverCompile;

import com.samsung.android.knox.localservice.RestrictionPolicyInternal;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class WindowState extends WindowContainer
        implements WindowManagerPolicy.WindowState, InputTarget {
    public static final int MINIMUM_VISIBLE_WIDTH_IN_DP;
    public static final int RESIZE_HANDLE_WIDTH_IN_DP;
    public static final StringBuilder sTmpSB;
    public static final AnonymousClass1 sWindowSubLayerComparator;
    public final InsetsState mAboveInsetsState;
    public ActivityRecord mActivityRecord;
    public boolean mAnimatingExit;
    public boolean mAppFreezing;
    public final int mAppOp;
    public boolean mAppOpVisibility;
    public final WindowManager.LayoutParams mAttrs;
    public final int mBaseLayer;
    public final ArraySet mChildDimmingDialogs;
    public final IWindow mClient;
    public float mCompatScale;
    public final Context mContext;
    public final long mCreateTime;
    public boolean mDestroying;
    public int mDisableFlags;
    public boolean mDisableHideSViewOnce;
    public boolean mDragResizing;
    public boolean mDragResizingChangeReported;
    public final List mDrawHandlers;
    public PowerManager.WakeLock mDrawLock;
    public boolean mDrawnStateEvaluated;
    public final List mExclusionRects;
    public RemoteCallbackList mFocusCallbacks;
    public boolean mForceHideNonSystemOverlayWindow;
    public final boolean mForceSeamlesslyRotate;
    public int mFrameRateSelectionPriority;
    public final RefreshRatePolicy.FrameRateVote mFrameRateVote;
    public InsetsState mFrozenInsetsState;
    public final Rect mGivenContentInsets;
    public boolean mGivenInsetsPending;
    public final Region mGivenTouchableRegion;
    public final Rect mGivenVisibleInsets;
    public float mGlobalScale;
    public float mHScale;
    public boolean mHasSurface;
    public boolean mHaveFrame;
    public boolean mHidden;
    public boolean mHiddenWhileProfileLockedState;
    public boolean mHiddenWhileSuspended;
    public final boolean mIgnoreHideNonSystemOverlayWindow;
    public boolean mImeBlurEffectAppliedForDex;
    public boolean mImeInsetsConsumed;
    public boolean mInRelayout;
    public InputChannel mInputChannel;
    public IBinder mInputChannelToken;
    public final InputWindowHandleWrapper mInputWindowHandle;
    public float mInvGlobalScale;
    public final boolean mIsChildWindow;
    public boolean mIsDimming;
    public final boolean mIsFloatingLayer;
    public final boolean mIsImWindow;
    public boolean mIsSurfacePositionPaused;
    public boolean mIsTspNoteMode;
    public final boolean mIsWallpaper;
    public final List mKeepClearAreas;
    public KeyInterceptionInfo mKeyInterceptionInfo;
    public boolean mKeyguardWallpaperTouchAllowed;
    public int mLastBlurRadius;
    public boolean mLastConfigReportedToClient;
    public final long[] mLastExclusionLogUptimeMillis;
    public int mLastFreezeDuration;
    public final int[] mLastGrantedExclusionHeight;
    public float mLastHScale;
    public final InsetsSourceControl.Array mLastReportedActiveControls;
    public final ActivityWindowInfo mLastReportedActivityWindowInfo;
    public final MergedConfiguration mLastReportedConfiguration;
    public final ClientWindowFrames mLastReportedFrames;
    public final InsetsState mLastReportedInsetsState;
    public final int[] mLastRequestedExclusionHeight;
    public int mLastRequestedHeight;
    public int mLastRequestedWidth;
    public boolean mLastShownChangedReported;
    public final Rect mLastSurfaceInsets;
    public CharSequence mLastTitle;
    public float mLastVScale;
    public final boolean mLayoutAttached;
    public boolean mLayoutNeeded;
    public int mLayoutSeq;
    public boolean mLayoutWithIme;
    public boolean mLegacyPolicyVisibilityAfterAnim;
    public int mLetterboxDirection;
    public SparseArray mMergedLocalInsetsSources;
    public final Rect mMinimizedInsets;
    public boolean mMovedByResize;
    public boolean mObscured;
    public OnBackInvokedCallbackInfo mOnBackInvokedCallbackInfo;
    public long mOrientationChangeRedrawRequestTime;
    public boolean mOrientationChangeTimedOut;
    public boolean mOrientationChanging;
    public float mOriginalDimAmount;
    public int mOriginalDimBehind;
    public long mOriginalDimDuration;
    public int mOriginalLayoutInDisplayCutoutMode;
    public final float mOverrideScale;
    public final boolean mOwnerCanAddInternalSystemWindow;
    public final int mOwnerUid;
    public SeamlessRotator mPendingSeamlessRotate;
    public boolean mPermanentlyHidden;
    public final WindowManagerPolicy mPolicy;
    public int mPolicyVisibility;
    public boolean mPopOverDimmerNeeded;
    public int mPrepareSyncSeqId;
    public boolean mRedrawForSyncReported;
    public boolean mRelayoutCalled;
    public int mRelayoutSeq;
    public boolean mRemoveOnExit;
    public boolean mRemoved;
    public int mRequestedHeight;
    public int mRequestedVisibleTypes;
    public int mRequestedWidth;
    public RestrictionPolicyInternal mRestrictionPolicy;
    public final WindowState$$ExternalSyntheticLambda0 mSeamlessRotationFinishedConsumer;
    public boolean mSeamlesslyRotated;
    public final Session mSession;
    public final WindowState$$ExternalSyntheticLambda0 mSetSurfacePositionConsumer;
    public final boolean mShouldHideSViewOnce;
    public boolean mShouldScaleWallpaper;
    public final int mShowUserId;
    public boolean mSkipExitAnimation;
    public StartingData mStartingData;
    public String mStringNameCache;
    public final int mSubLayer;
    public boolean mSurfacePlacementNeeded;
    public final Point mSurfacePosition;
    public int mSurfaceTranslationY;
    public int mSyncSeqId;
    public final Region mTapExcludeRegion;
    public final Configuration mTempConfiguration;
    public final Matrix mTmpMatrix;
    public final float[] mTmpMatrixArray;
    public final Point mTmpPoint;
    public final Rect mTmpRect;
    public final Region mTmpRegion;
    public final SurfaceControl.Transaction mTmpTransaction;
    public WindowToken mToken;
    public int mTouchableInsets;
    public Bundle mTspDeadzone;
    public final List mUnrestrictedKeepClearAreas;
    public float mVScale;
    public int mViewVisibility;
    public boolean mWaitToHandleResizing;
    public int mWallpaperDisplayOffsetX;
    public int mWallpaperDisplayOffsetY;
    public float mWallpaperScale;
    public float mWallpaperX;
    public float mWallpaperXStep;
    public float mWallpaperY;
    public float mWallpaperYStep;
    public float mWallpaperZoomOut;
    public boolean mWasExiting;
    public final WindowStateAnimator mWinAnimator;
    public final WindowFrames mWindowFrames;
    public final WindowId mWindowId;
    public boolean mWindowRemovalAllowed;
    public int mXOffset;
    public int mYOffset;

    /* renamed from: com.android.server.wm.WindowState$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            int i = ((WindowState) obj).mSubLayer;
            int i2 = ((WindowState) obj2).mSubLayer;
            return (i < i2 || (i == i2 && i2 < 0)) ? -1 : 1;
        }
    }

    public final class DrawHandler {
        public Consumer mConsumer;
        public boolean mIsEnteringPipFromSplit;
        public int mSeqId;
        public int mType;
    }

    public final class MoveAnimationSpec implements LocalAnimationAdapter.AnimationSpec {
        public final long mDuration;
        public final Point mFrom;
        public final Interpolator mInterpolator;
        public final Point mTo;

        public MoveAnimationSpec(WindowState windowState, int i, int i2, int i3, int i4) {
            Point point = new Point();
            this.mFrom = point;
            Point point2 = new Point();
            this.mTo = point2;
            Context context = windowState.mContext;
            boolean z = CoreRune.FW_IMPROVED_MOVED_ANIMATION_FOR_IME;
            int i5 = 17432958;
            if (z && z && windowState.isImeLayeringTarget()) {
                i5 = R.anim.voice_layer_exit;
            }
            Animation loadAnimation = AnimationUtils.loadAnimation(context, i5);
            this.mDuration =
                    (long)
                            (windowState.mWmService.getWindowAnimationScaleLocked()
                                    * loadAnimation.computeDurationHint());
            this.mInterpolator = loadAnimation.getInterpolator();
            point.set(i, i2);
            point2.set(i3, i4);
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public final void apply(
                SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, long j) {
            float interpolation = this.mInterpolator.getInterpolation(getFraction(j));
            Point point = this.mFrom;
            int i = point.x;
            Point point2 = this.mTo;
            transaction.setPosition(
                    surfaceControl,
                    ((point2.x - i) * interpolation) + i,
                    ((point2.y - r7) * interpolation) + point.y);
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public final void dump(PrintWriter printWriter, String str) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "from=");
            m.append(this.mFrom);
            m.append(" to=");
            m.append(this.mTo);
            m.append(" duration=");
            m.append(this.mDuration);
            printWriter.println(m.toString());
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public final void dumpDebugInner(ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268034L);
            GraphicsProtos.dumpPointProto(this.mFrom, protoOutputStream, 1146756268033L);
            GraphicsProtos.dumpPointProto(this.mTo, protoOutputStream, 1146756268034L);
            protoOutputStream.write(1112396529667L, this.mDuration);
            protoOutputStream.end(start);
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public final long getDuration() {
            return this.mDuration;
        }
    }

    public final class UpdateReportedVisibilityResults {
        public boolean nowGone;
        public int numDrawn;
        public int numInteresting;
        public int numVisible;
    }

    public final class WindowId extends IWindowId.Stub {
        public final WeakReference mOuter;

        public WindowId(WindowState windowState) {
            this.mOuter = new WeakReference(windowState);
        }

        public final boolean isFocused() {
            boolean isFocused;
            WindowState windowState = (WindowState) this.mOuter.get();
            if (windowState == null) {
                return false;
            }
            WindowManagerGlobalLock windowManagerGlobalLock = windowState.mWmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    isFocused = windowState.isFocused();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return isFocused;
        }

        public final void registerFocusObserver(IWindowFocusObserver iWindowFocusObserver) {
            WindowState windowState = (WindowState) this.mOuter.get();
            if (windowState != null) {
                WindowManagerGlobalLock windowManagerGlobalLock =
                        windowState.mWmService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (windowState.mFocusCallbacks == null) {
                            windowState.mFocusCallbacks = new RemoteCallbackList();
                        }
                        windowState.mFocusCallbacks.register(iWindowFocusObserver);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        public final void unregisterFocusObserver(IWindowFocusObserver iWindowFocusObserver) {
            WindowState windowState = (WindowState) this.mOuter.get();
            if (windowState != null) {
                WindowManagerGlobalLock windowManagerGlobalLock =
                        windowState.mWmService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        RemoteCallbackList remoteCallbackList = windowState.mFocusCallbacks;
                        if (remoteCallbackList != null) {
                            remoteCallbackList.unregister(iWindowFocusObserver);
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    static {
        MINIMUM_VISIBLE_WIDTH_IN_DP = CoreRune.MW_CAPTION_SHELL ? 32 : 48;
        RESIZE_HANDLE_WIDTH_IN_DP = CoreRune.MW_SHELL_FREEFORM_TASK_POSITIONER ? 0 : 30;
        sTmpSB = new StringBuilder();
        sWindowSubLayerComparator = new AnonymousClass1();
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x0327, code lost:

       if (r22.mActivityRecord.info.applicationInfo.isUpdatedSystemApp() == false) goto L108;
    */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0350, code lost:

       if (r0.isUpdatedSystemApp() != false) goto L109;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public WindowState(
            com.android.server.wm.WindowManagerService r23,
            com.android.server.wm.Session r24,
            android.view.IWindow r25,
            com.android.server.wm.WindowToken r26,
            com.android.server.wm.WindowState r27,
            int r28,
            android.view.WindowManager.LayoutParams r29,
            int r30,
            int r31,
            int r32,
            boolean r33) {
        /*
            Method dump skipped, instructions count: 1074
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowState.<init>(com.android.server.wm.WindowManagerService,"
                    + " com.android.server.wm.Session, android.view.IWindow,"
                    + " com.android.server.wm.WindowToken, com.android.server.wm.WindowState, int,"
                    + " android.view.WindowManager$LayoutParams, int, int, int, boolean):void");
    }

    public final void adjustImeParamsForDex(boolean z) {
        if (this.mAttrs.type == 2011 && getDisplayContent().isDefaultDisplay) {
            if (this.mImeBlurEffectAppliedForDex) {
                WindowManager.LayoutParams layoutParams = this.mAttrs;
                layoutParams.flags &= -3;
                layoutParams.samsungFlags &= -65;
                layoutParams.dimAmount = 1.0f;
                this.mImeBlurEffectAppliedForDex = false;
                if (CoreRune.IS_DEBUG_LEVEL_MID) {
                    Slog.d("WindowManager", "applyDims: release BlurDim for Ime in dex mode.");
                }
            }
            if (z) {
                DexController dexController = this.mWmService.mAtmService.mDexController;
                if (dexController.getDexModeLocked() == 2
                        && !dexController.mDexTouchPadEnabled
                        && dexController.shouldShowDexImeInDefaultDisplayLocked()) {
                    WindowManager.LayoutParams layoutParams2 = this.mAttrs;
                    layoutParams2.flags |= 2;
                    layoutParams2.samsungFlags |= 64;
                    layoutParams2.dimAmount = 0.1f;
                    if (CoreRune.IS_DEBUG_LEVEL_MID) {
                        Slog.d("WindowManager", "applyDims: apply BlurDim for Ime in dex mode.");
                    }
                    this.mImeBlurEffectAppliedForDex = true;
                }
            }
        }
    }

    public final void adjustRegionInFreefromWindowMode(Rect rect) {
        if (inFreeformWindowingMode()) {
            Task rootTask = getRootTask();
            if (rootTask.isFreeformStashed()) {
                rect.set(rootTask.getStashedBounds());
            }
            int i =
                    -WindowManagerService.dipToPixel(
                            RESIZE_HANDLE_WIDTH_IN_DP, getDisplayContent().mDisplayMetrics);
            rect.inset(i, i);
        }
    }

    public final void adjustStartingWindowFlags() {
        ActivityRecord activityRecord;
        WindowState windowState;
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        if (layoutParams.type != 1
                || (activityRecord = this.mActivityRecord) == null
                || (windowState = activityRecord.mStartingWindow) == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams2 = windowState.mAttrs;
        layoutParams2.flags = (layoutParams.flags & 4718593) | (layoutParams2.flags & (-4718594));
    }

    public final boolean applyImeWindowsIfNeeded(ToBooleanFunction toBooleanFunction, boolean z) {
        if (!isImeLayeringTarget()) {
            return false;
        }
        InputTarget inputTarget = this.mDisplayContent.mImeInputTarget;
        WindowState windowState = inputTarget != null ? inputTarget.getWindowState() : null;
        if (windowState == null
                || windowState.isDrawn()
                || windowState.isVisibleRequested()
                || (getDisplayId() == 0
                        && getTask() == null
                        && this.mWmService.mAtmService.mDexController.getDexModeLocked() == 2
                        && this.mWmService.mAtmService.mDexController
                                .shouldShowDexImeInDefaultDisplayLocked())) {
            return this.mDisplayContent.mImeWindowsContainer.forAllWindowForce(
                    toBooleanFunction, z);
        }
        return false;
    }

    public final boolean applyInOrderWithImeWindows(
            ToBooleanFunction toBooleanFunction, boolean z) {
        return z
                ? applyImeWindowsIfNeeded(toBooleanFunction, z) || toBooleanFunction.apply(this)
                : toBooleanFunction.apply(this) || applyImeWindowsIfNeeded(toBooleanFunction, z);
    }

    public final void applyWithNextDraw(int i, Consumer consumer) {
        ActivityRecord activityRecord;
        if (this.mSyncState != 0) {
            Slog.w(
                    "WindowManager",
                    "applyWithNextDraw with mSyncState="
                            + this.mSyncState
                            + ", "
                            + this
                            + ", "
                            + Debug.getCallers(8));
        }
        int i2 = this.mSyncSeqId + 1;
        this.mSyncSeqId = i2;
        List list = this.mDrawHandlers;
        DrawHandler drawHandler = new DrawHandler();
        drawHandler.mType = 0;
        drawHandler.mSeqId = i2;
        drawHandler.mConsumer = consumer;
        ((ArrayList) list).add(drawHandler);
        ArrayList arrayList = (ArrayList) this.mDrawHandlers;
        DrawHandler drawHandler2 = (DrawHandler) arrayList.get(arrayList.size() - 1);
        drawHandler2.mType = i;
        if (CoreRune.MW_PIP_SHELL_TRANSITION
                && (activityRecord = this.mActivityRecord) != null
                && activityRecord.mIsEnteringPipFromSplit) {
            drawHandler2.mIsEnteringPipFromSplit = true;
        }
        StringBuilder sb = new StringBuilder("applyWithNextDraw, mSyncSeqId=");
        sb.append(this.mSyncSeqId);
        sb.append(", win=");
        sb.append(this);
        sb.append(", caller=");
        ActivityManagerService$$ExternalSyntheticOutline0.m(6, sb, "WindowManager");
        this.mRedrawForSyncReported = false;
        WindowManagerService.H h = this.mWmService.mH;
        h.removeMessages(64, this);
        h.sendMessageDelayed(h.obtainMessage(64, this), 5000L);
    }

    public final boolean areAppWindowBoundsLetterboxed() {
        if (isDexMode() || this.mActivityRecord == null || isStartingWindowAssociatedToTask()) {
            return false;
        }
        return this.mActivityRecord.areBoundsLetterboxed() || isLetterboxedForDisplayCutout();
    }

    @Override // com.android.server.wm.WindowContainer
    public final WindowState asWindowState() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void assignChildLayers(SurfaceControl.Transaction transaction) {
        int i = 2;
        for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
            WindowState windowState = (WindowState) this.mChildren.get(i2);
            int i3 = windowState.mAttrs.type;
            if (i3 == 1001) {
                if (this.mWinAnimator.hasSurface()) {
                    windowState.assignRelativeLayer(
                            transaction, this.mWinAnimator.mSurfaceController.mSurfaceControl, -2);
                } else {
                    windowState.assignLayer(transaction, -2);
                }
            } else if (i3 != 1004) {
                windowState.assignLayer(transaction, i);
            } else if (this.mWinAnimator.hasSurface()) {
                windowState.assignRelativeLayer(
                        transaction, this.mWinAnimator.mSurfaceController.mSurfaceControl, -1);
            } else {
                windowState.assignLayer(transaction, -1);
            }
            windowState.assignChildLayers(transaction);
            i++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0085, code lost:

       if (r6.mWmService.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked() == false) goto L85;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x011e, code lost:

       assignRelativeLayer(r7, getDisplayContent().mImeWindowsContainer.getSurfaceControl(), 1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x012c, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00ef, code lost:

       if ((r0.flags & 131072) != 0) goto L85;
    */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x011c, code lost:

       if (r1.compareTo((com.android.server.wm.WindowContainer) r6) <= 0) goto L85;
    */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x00c3, code lost:

       if (r1.compareTo((com.android.server.wm.WindowContainer) r6) <= 0) goto L85;
    */
    @Override // com.android.server.wm.WindowContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void assignLayer(android.view.SurfaceControl.Transaction r7, int r8) {
        /*
            Method dump skipped, instructions count: 305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowState.assignLayer(android.view.SurfaceControl$Transaction,"
                    + " int):void");
    }

    public final boolean canBeHiddenByKeyguard() {
        int i;
        if (this.mWmService.mExt.mExtraDisplayPolicy.hasCoverHome(getDisplayId())
                || this.mActivityRecord != null
                || (i = this.mAttrs.type) == 2000
                || i == 2013
                || i == 2019
                || i == 2040
                || this.mDisplayContent.mDisplayPolicy.mExt.mTaskbarController.mTaskbarWin
                        == this) {
            return false;
        }
        this.mPolicy.getClass();
        int windowLayerLw = WindowManagerPolicy.getWindowLayerLw(this);
        this.mPolicy.getClass();
        return windowLayerLw < WindowManagerPolicy.getWindowLayerFromTypeLw(2040);
    }

    public final boolean canBeImeTarget() {
        ActivityRecord activityRecord;
        ActivityRecord activityRecord2;
        int i;
        if (this.mIsImWindow || inPinnedWindowingMode() || this.mAttrs.type == 2036) {
            return false;
        }
        ActivityRecord activityRecord3 = this.mActivityRecord;
        if (activityRecord3 != null && !activityRecord3.windowsAreFocusable(false)) {
            return false;
        }
        Task rootTask = getRootTask();
        if (rootTask != null && !rootTask.isFocusable()) {
            return false;
        }
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        if (layoutParams.type != 3 && (i = layoutParams.flags & 131080) != 0 && i != 131080) {
            return false;
        }
        if (rootTask != null
                && (activityRecord2 = this.mActivityRecord) != null
                && this.mTransitionController.isTransientLaunch(activityRecord2)) {
            return false;
        }
        if (!isVisibleRequestedOrAdding()) {
            if (!isVisible()
                    || (activityRecord = this.mActivityRecord) == null
                    || !activityRecord.mVisible) {
                return false;
            }
            if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX
                    && !this.mTransitionController.hasTransientLaunch(this.mDisplayContent)) {
                return false;
            }
        }
        return true;
    }

    public final boolean canPlayMoveAnimation() {
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord == null || !activityRecord.hasStartingWindow()) {
            return this.mToken.okToAnimate()
                    && (this.mAttrs.privateFlags & 64) == 0
                    && !this.mDragResizing
                    && (getTask() == null
                            ? getWindowConfiguration().hasMovementAnimations()
                            : getTask().getWindowConfiguration().hasMovementAnimations())
                    && !this.mWinAnimator.mLastHidden
                    && !this.mSeamlesslyRotated;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x004b, code lost:

       if (r0.isFocusedRootTaskOnDisplay() == false) goto L38;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean canReceiveKeys(boolean r5) {
        /*
            r4 = this;
            com.android.server.wm.ActivityRecord r0 = r4.mActivityRecord
            r1 = 1
            if (r0 == 0) goto Le
            com.android.server.wm.TransitionController r2 = r4.mTransitionController
            boolean r0 = r2.shouldKeepFocus(r0)
            if (r0 == 0) goto Le
            return r1
        Le:
            boolean r0 = r4.isVisibleRequestedOrAdding()
            r2 = 0
            if (r0 == 0) goto L69
            int r0 = r4.mViewVisibility
            if (r0 != 0) goto L69
            boolean r0 = r4.mRemoveOnExit
            if (r0 != 0) goto L69
            android.view.WindowManager$LayoutParams r0 = r4.mAttrs
            int r0 = r0.flags
            r0 = r0 & 8
            if (r0 != 0) goto L69
            com.android.server.wm.ActivityRecord r0 = r4.mActivityRecord
            if (r0 == 0) goto L2f
            boolean r0 = r0.windowsAreFocusable(r5)
            if (r0 == 0) goto L69
        L2f:
            com.android.server.wm.ActivityRecord r0 = r4.mActivityRecord
            if (r0 == 0) goto L4e
            com.android.server.wm.Task r0 = r0.task
            if (r0 == 0) goto L4e
            com.android.server.wm.Task r0 = r0.getRootTask()
            com.android.server.wm.ActivityTaskManagerService r3 = r0.mAtmService
            boolean r3 = r3.mHasLeanbackFeature
            if (r3 == 0) goto L4e
            boolean r3 = r0.inPinnedWindowingMode()
            if (r3 == 0) goto L4e
            boolean r0 = r0.isFocusedRootTaskOnDisplay()
            if (r0 != 0) goto L4e
            goto L69
        L4e:
            if (r5 != 0) goto L68
            com.android.server.wm.DisplayContent r5 = r4.getDisplayContent()
            boolean r5 = r5.isOnTop()
            if (r5 != 0) goto L68
            com.android.server.wm.DisplayContent r4 = r4.getDisplayContent()
            android.view.Display r4 = r4.mDisplay
            boolean r4 = r4.isTrusted()
            if (r4 == 0) goto L67
            goto L68
        L67:
            r1 = r2
        L68:
            return r1
        L69:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowState.canReceiveKeys(boolean):boolean");
    }

    public final boolean canReceiveTouchInput() {
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord == null
                || activityRecord.task == null
                || this.mTransitionController.shouldKeepFocus(activityRecord)) {
            return true;
        }
        BackNavigationController backNavigationController =
                this.mWmService.mAtmService.mBackNavigationController;
        ActivityRecord activityRecord2 = this.mActivityRecord;
        BackNavigationController.AnimationHandler animationHandler =
                backNavigationController.mAnimationHandler;
        if (animationHandler.mComposed
                && backNavigationController.mWaitTransitionFinish == null
                && animationHandler.isTarget(
                        activityRecord2, activityRecord2.isVisibleRequested())) {
            return false;
        }
        Task rootTask = this.mActivityRecord.task.getRootTask();
        return !(rootTask.mAtmService.mHasLeanbackFeature
                        && rootTask.inPinnedWindowingMode()
                        && !rootTask.isFocusedRootTaskOnDisplay())
                && this.mActivityRecord.isVisibleRequested();
    }

    @Override // com.android.server.wm.InputTarget
    public final boolean canScreenshotIme() {
        return !isSecureLocked();
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public final boolean canShowTransient() {
        return (this.mAttrs.insetsFlags.behavior & 2) != 0;
    }

    public final boolean canShowWhenLocked() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null
                ? activityRecord.canShowWhenLocked()
                : (this.mAttrs.flags & 524288) != 0;
    }

    public final boolean clearAnimatingFlags() {
        boolean z;
        boolean z2 = false;
        if (!this.mRemoveOnExit) {
            if (this.mAnimatingExit) {
                this.mAnimatingExit = false;
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled[0]) {
                    ProtoLogImpl_54989576.d(
                            ProtoLogGroup.WM_DEBUG_ANIM,
                            -3153130647145726082L,
                            0,
                            null,
                            String.valueOf(this));
                }
                z = true;
            } else {
                z = false;
            }
            if (this.mDestroying) {
                this.mDestroying = false;
                this.mWmService.mDestroySurface.remove(this);
                z2 = true;
            } else {
                z2 = z;
            }
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z2 |= ((WindowState) this.mChildren.get(size)).clearAnimatingFlags();
        }
        return z2;
    }

    public final void clearPolicyVisibilityFlag(int i) {
        this.mPolicyVisibility = (~i) & this.mPolicyVisibility;
        this.mWmService.scheduleAnimationLocked();
    }

    public final boolean commitFinishDrawing(SurfaceControl.Transaction transaction) {
        boolean commitFinishDrawingLocked = this.mWinAnimator.commitFinishDrawingLocked();
        if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX && Flags.removePrepareSurfaceInPlacement()) {
            WindowStateAnimator windowStateAnimator = this.mWinAnimator;
            if (windowStateAnimator.mDrawState == 4 && windowStateAnimator.mLastHidden) {
                commitFinishDrawingLocked = true;
            }
        }
        if (commitFinishDrawingLocked) {
            this.mWinAnimator.prepareSurfaceLocked(transaction);
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            commitFinishDrawingLocked |=
                    ((WindowState) this.mChildren.get(size)).commitFinishDrawing(transaction);
        }
        if (getAnimationLeash() != null) {
            transaction.merge(getSyncTransaction());
        }
        return commitFinishDrawingLocked;
    }

    public final boolean computeDragResizing() {
        Task task = getTask();
        if (task == null) {
            return false;
        }
        if ((!inFreeformWindowingMode() && !task.getRootTask().mCreatedByOrganizer)
                || task.getActivityType() == 2) {
            return false;
        }
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        return layoutParams.width == -1 && layoutParams.height == -1 && task.mDragResizing;
    }

    public final void consumeInsetsChange() {
        WindowFrames windowFrames = this.mWindowFrames;
        if (windowFrames.mInsetsChanged) {
            windowFrames.mInsetsChanged = false;
            WindowManagerService windowManagerService = this.mWmService;
            int i = windowManagerService.mWindowsInsetsChanged - 1;
            windowManagerService.mWindowsInsetsChanged = i;
            if (i == 0) {
                windowManagerService.mH.removeMessages(66);
            }
        }
    }

    public final void cropRegionToRootTaskBoundsIfNeeded(Region region) {
        Task rootTask;
        Task task = getTask();
        if (task == null
                || !task.cropWindowsToRootTaskBounds()
                || (rootTask = task.getRootTask()) == null
                || rootTask.mCreatedByOrganizer) {
            return;
        }
        rootTask.getDimBounds(this.mTmpRect);
        adjustRegionInFreefromWindowMode(this.mTmpRect);
        region.op(this.mTmpRect, Region.Op.INTERSECT);
    }

    public final boolean destroySurface(boolean z, boolean z2) {
        ArrayList arrayList = new ArrayList(this.mChildren);
        boolean z3 = false;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            z3 |= ((WindowState) arrayList.get(size)).destroySurface(z, z2);
        }
        if ((z2 || this.mWindowRemovalAllowed || z) && this.mDestroying) {
            if (ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_ADD_REMOVE_enabled[4]) {
                ProtoLogImpl_54989576.e(
                        ProtoLogGroup.WM_FORCE_DEBUG_ADD_REMOVE,
                        -5801540178617093985L,
                        2044,
                        "win=%s destroySurfaces: appStopped=%b cleanupOnResume=%b"
                            + " win.mWindowRemovalAllowed=%b win.mRemoveOnExit=%b"
                            + " win.mViewVisibility=%d caller=%s",
                        String.valueOf(this),
                        Boolean.valueOf(z2),
                        Boolean.valueOf(z),
                        Boolean.valueOf(this.mWindowRemovalAllowed),
                        Boolean.valueOf(this.mRemoveOnExit),
                        Long.valueOf(this.mViewVisibility),
                        String.valueOf(Debug.getCallers(7)));
            }
            if (!z || this.mRemoveOnExit) {
                destroySurfaceUnchecked();
            }
            if (this.mRemoveOnExit) {
                removeImmediately();
            }
            if (z) {
                requestUpdateWallpaperIfNeeded();
            }
            this.mDestroying = false;
            if (!getDisplayContent().mAppTransition.isTransitionSet()
                    || !getDisplayContent().mOpeningApps.contains(this.mActivityRecord)) {
                return true;
            }
            this.mWmService.mWindowPlacerLocked.requestTraversal();
            return true;
        }
        return z3;
    }

    public final void destroySurfaceUnchecked() {
        this.mWinAnimator.destroySurfaceLocked(this.mTmpTransaction);
        this.mTmpTransaction.apply();
        this.mAnimatingExit = false;
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_ANIM,
                    7336961102428192483L,
                    0,
                    null,
                    String.valueOf(this));
        }
        if (syncNextBuffer()) {
            finishDrawing(null, Integer.MAX_VALUE);
            this.mWmService.mH.removeMessages(64, this);
        }
    }

    public final void disposeInputChannel() {
        IBinder iBinder = this.mInputChannelToken;
        if (iBinder != null) {
            this.mWmService.mInputManager.removeInputChannel(iBinder);
            this.mWmService.mKeyInterceptionInfoForToken.remove(this.mInputChannelToken);
            this.mWmService.mInputToWindowMap.remove(this.mInputChannelToken);
            this.mInputChannelToken = null;
        }
        InputChannel inputChannel = this.mInputChannel;
        if (inputChannel != null) {
            inputChannel.dispose();
            this.mInputChannel = null;
        }
        this.mInputWindowHandle.setToken(null);
    }

    public final void dropBufferFrom(SurfaceControl.Transaction transaction) {
        WindowStateAnimator windowStateAnimator = this.mWinAnimator;
        SurfaceControl surfaceControl =
                !windowStateAnimator.hasSurface()
                        ? null
                        : windowStateAnimator.mSurfaceController.mSurfaceControl;
        if (surfaceControl == null) {
            return;
        }
        if (transaction != null) {
            StringBuilder sb = new StringBuilder("dropBufferFrom, t=");
            sb.append(transaction.mDebugName);
            sb.append(", win=");
            sb.append(this);
            sb.append(", caller=");
            ActivityManagerService$$ExternalSyntheticOutline0.m(3, sb, "WindowManager");
        }
        transaction.unsetBuffer(surfaceControl);
    }

    @Override // com.android.server.wm.WindowContainer
    @NeverCompile
    public final void dump(PrintWriter printWriter, String str, boolean z) {
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mDisplayId=");
        m.append(getDisplayId());
        printWriter.print(m.toString());
        if (getRootTask() != null) {
            StringBuilder sb = new StringBuilder(" rootTaskId=");
            Task rootTask = getRootTask();
            sb.append(rootTask == null ? -1 : rootTask.mTaskId);
            printWriter.print(sb.toString());
        }
        printWriter.println(" mSession=" + this.mSession + " mClient=" + this.mClient.asBinder());
        printWriter.println(
                str
                        + "mOwnerUid="
                        + this.mOwnerUid
                        + " showForAllUsers="
                        + showForAllUsers()
                        + " package="
                        + this.mAttrs.packageName
                        + " appop="
                        + AppOpsManager.opToName(this.mAppOp));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("mAttrs=");
        sb2.append(this.mAttrs.toString(str));
        printWriter.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append("Requested w=");
        sb3.append(this.mRequestedWidth);
        sb3.append(" h=");
        sb3.append(this.mRequestedHeight);
        sb3.append(" mLayoutSeq=");
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(sb3, this.mLayoutSeq, printWriter);
        if (this.mRequestedWidth != this.mLastRequestedWidth
                || this.mRequestedHeight != this.mLastRequestedHeight) {
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str, "LastRequested w=");
            m2.append(this.mLastRequestedWidth);
            m2.append(" h=");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    m2, this.mLastRequestedHeight, printWriter);
        }
        if (this.mIsChildWindow || this.mLayoutAttached) {
            StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(str, "mParentWindow=");
            m3.append(getParentWindow());
            m3.append(" mLayoutAttached=");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    m3, this.mLayoutAttached, printWriter);
        }
        if (this.mIsImWindow || this.mIsWallpaper || this.mIsFloatingLayer) {
            StringBuilder m4 = Preconditions$$ExternalSyntheticOutline0.m(str, "mIsImWindow=");
            m4.append(this.mIsImWindow);
            m4.append(" mIsWallpaper=");
            m4.append(this.mIsWallpaper);
            m4.append(" mIsFloatingLayer=");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    m4, this.mIsFloatingLayer, printWriter);
        }
        if (z) {
            printWriter.print(str);
            printWriter.print("mBaseLayer=");
            printWriter.print(this.mBaseLayer);
            printWriter.print(" mSubLayer=");
            printWriter.print(this.mSubLayer);
        }
        if (z) {
            StringBuilder m5 = Preconditions$$ExternalSyntheticOutline0.m(str, "mToken=");
            m5.append(this.mToken);
            printWriter.println(m5.toString());
            if (this.mActivityRecord != null) {
                StringBuilder m6 =
                        Preconditions$$ExternalSyntheticOutline0.m(str, "mActivityRecord=");
                m6.append(this.mActivityRecord);
                printWriter.println(m6.toString());
                printWriter.print(str + "drawnStateEvaluated=" + this.mDrawnStateEvaluated);
                printWriter.println(str + "mightAffectAllDrawn=" + mightAffectAllDrawn());
            }
            StringBuilder m7 =
                    Preconditions$$ExternalSyntheticOutline0.m(str, "mViewVisibility=0x");
            BatteryService$$ExternalSyntheticOutline0.m(this.mViewVisibility, m7, " mHaveFrame=");
            m7.append(this.mHaveFrame);
            m7.append(" mObscured=");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(m7, this.mObscured, printWriter);
            if (this.mDisableFlags != 0) {
                StringBuilder m8 =
                        Preconditions$$ExternalSyntheticOutline0.m(str, "mDisableFlags=");
                m8.append(
                        ViewDebug.flagsToString(
                                View.class, "mSystemUiVisibility", this.mDisableFlags));
                printWriter.println(m8.toString());
            }
        }
        if (!isVisibleByPolicy()
                || !this.mLegacyPolicyVisibilityAfterAnim
                || !this.mAppOpVisibility
                || isParentWindowHidden()
                || this.mPermanentlyHidden
                || this.mForceHideNonSystemOverlayWindow
                || this.mHiddenWhileSuspended) {
            StringBuilder m9 =
                    Preconditions$$ExternalSyntheticOutline0.m(str, "mPolicyVisibility=");
            m9.append(isVisibleByPolicy());
            m9.append(" mLegacyPolicyVisibilityAfterAnim=");
            m9.append(this.mLegacyPolicyVisibilityAfterAnim);
            m9.append(" mAppOpVisibility=");
            m9.append(this.mAppOpVisibility);
            m9.append(" parentHidden=");
            m9.append(isParentWindowHidden());
            m9.append(" mPermanentlyHidden=");
            m9.append(this.mPermanentlyHidden);
            m9.append(" mHiddenWhileSuspended=");
            m9.append(this.mHiddenWhileSuspended);
            m9.append(" mForceHideNonSystemOverlayWindow=");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    m9, this.mForceHideNonSystemOverlayWindow, printWriter);
        }
        if (!this.mRelayoutCalled || this.mLayoutNeeded) {
            StringBuilder m10 = Preconditions$$ExternalSyntheticOutline0.m(str, "mRelayoutCalled=");
            m10.append(this.mRelayoutCalled);
            m10.append(" mLayoutNeeded=");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    m10, this.mLayoutNeeded, printWriter);
        }
        if (z) {
            StringBuilder m11 =
                    Preconditions$$ExternalSyntheticOutline0.m(str, "mGivenContentInsets=");
            Rect rect = this.mGivenContentInsets;
            StringBuilder sb4 = sTmpSB;
            m11.append(rect.toShortString(sb4));
            m11.append(" mGivenVisibleInsets=");
            m11.append(this.mGivenVisibleInsets.toShortString(sb4));
            printWriter.println(m11.toString());
            if (this.mTouchableInsets != 0 || this.mGivenInsetsPending) {
                StringBuilder m12 =
                        Preconditions$$ExternalSyntheticOutline0.m(str, "mTouchableInsets=");
                m12.append(this.mTouchableInsets);
                m12.append(" mGivenInsetsPending=");
                m12.append(this.mGivenInsetsPending);
                printWriter.println(m12.toString());
                Region region = new Region();
                getTouchableRegion(region);
                printWriter.println(str + "touchable region=" + region);
            }
            StringBuilder m13 =
                    Preconditions$$ExternalSyntheticOutline0.m(str, "mFullConfiguration=");
            m13.append(getConfiguration());
            printWriter.println(m13.toString());
            printWriter.println(
                    str
                            + "mLastReportedConfiguration="
                            + this.mLastReportedConfiguration.getMergedConfiguration());
            if (this.mLastReportedActivityWindowInfo != null) {
                StringBuilder m14 =
                        Preconditions$$ExternalSyntheticOutline0.m(
                                str, "mLastReportedActivityWindowInfo=");
                m14.append(this.mLastReportedActivityWindowInfo);
                printWriter.println(m14.toString());
            }
        }
        StringBuilder m15 = Preconditions$$ExternalSyntheticOutline0.m(str, "mHasSurface=");
        m15.append(this.mHasSurface);
        m15.append(" isReadyForDisplay()=");
        m15.append(isReadyForDisplay());
        m15.append(" mWindowRemovalAllowed=");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                m15, this.mWindowRemovalAllowed, printWriter);
        if (this.mIsSurfacePositionPaused) {
            printWriter.println(str + "mIsSurfacePositionPaused=true");
        }
        if (this.mInvGlobalScale != 1.0f) {
            StringBuilder m16 = Preconditions$$ExternalSyntheticOutline0.m(str, "mCompatFrame=");
            m16.append(this.mWindowFrames.mCompatFrame.toShortString(sTmpSB));
            printWriter.println(m16.toString());
        }
        if (z) {
            WindowFrames windowFrames = this.mWindowFrames;
            windowFrames.getClass();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str);
            sb5.append("Frames: parent=");
            Rect rect2 = windowFrames.mParentFrame;
            StringBuilder sb6 = WindowFrames.sTmpSB;
            sb5.append(rect2.toShortString(sb6));
            sb5.append(" display=");
            sb5.append(windowFrames.mDisplayFrame.toShortString(sb6));
            sb5.append(" frame=");
            sb5.append(windowFrames.mFrame.toShortString(sb6));
            sb5.append(" last=");
            sb5.append(windowFrames.mLastFrame.toShortString(sb6));
            sb5.append(" insetsChanged=");
            StringBuilder m17 =
                    MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                            sb5, windowFrames.mInsetsChanged, printWriter, str, " surface=");
            m17.append(this.mAttrs.surfaceInsets.toShortString(sTmpSB));
            printWriter.println(m17.toString());
        }
        super.dump(printWriter, str, z);
        StringBuilder m18 = BootReceiver$$ExternalSyntheticOutline0.m(str);
        m18.append(this.mWinAnimator);
        m18.append(":");
        printWriter.println(m18.toString());
        WindowStateAnimator windowStateAnimator = this.mWinAnimator;
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  ");
        if (windowStateAnimator.mAnimationIsEntrance) {
            printWriter.print(m$1);
            printWriter.print(" mAnimationIsEntrance=");
            printWriter.print(windowStateAnimator.mAnimationIsEntrance);
        }
        WindowSurfaceController windowSurfaceController = windowStateAnimator.mSurfaceController;
        if (windowSurfaceController != null) {
            if (z) {
                printWriter.print(m$1);
                printWriter.print("mSurface=");
                printWriter.println(windowSurfaceController.mSurfaceControl);
            }
            printWriter.print(m$1);
            printWriter.print("Surface: shown=");
            printWriter.print(windowSurfaceController.mSurfaceShown);
        }
        if (z) {
            printWriter.print(m$1);
            printWriter.print("mDrawState=");
            printWriter.print(windowStateAnimator.drawStateToString());
            printWriter.print(m$1);
            printWriter.print(" mLastHidden=");
            printWriter.println(windowStateAnimator.mLastHidden);
            printWriter.print(m$1);
            printWriter.print(
                    "mEnterAnimationPending=" + windowStateAnimator.mEnterAnimationPending);
            printWriter.print(m$1);
            printWriter.print("mSystemDecorRect=");
            windowStateAnimator.mSystemDecorRect.printShortString(printWriter);
            printWriter.println();
        }
        if (windowStateAnimator.mShownAlpha != 1.0f
                || windowStateAnimator.mAlpha != 1.0f
                || windowStateAnimator.mLastAlpha != 1.0f) {
            printWriter.print(m$1);
            printWriter.print("mShownAlpha=");
            printWriter.print(windowStateAnimator.mShownAlpha);
            printWriter.print(" mAlpha=");
            printWriter.print(windowStateAnimator.mAlpha);
            printWriter.print(" mLastAlpha=");
            printWriter.println(windowStateAnimator.mLastAlpha);
        }
        WindowState windowState = windowStateAnimator.mWin;
        if (windowState.mGlobalScale != 1.0f) {
            printWriter.print(m$1);
            printWriter.print("mGlobalScale=");
            printWriter.print(windowState.mGlobalScale);
        }
        if (this.mAnimatingExit || this.mRemoveOnExit || this.mDestroying || this.mRemoved) {
            StringBuilder m19 = Preconditions$$ExternalSyntheticOutline0.m(str, "mAnimatingExit=");
            m19.append(this.mAnimatingExit);
            m19.append(" mRemoveOnExit=");
            m19.append(this.mRemoveOnExit);
            m19.append(" mDestroying=");
            m19.append(this.mDestroying);
            m19.append(" mRemoved=");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(m19, this.mRemoved, printWriter);
        }
        if (getOrientationChanging() || this.mAppFreezing) {
            StringBuilder m20 =
                    Preconditions$$ExternalSyntheticOutline0.m(str, "mOrientationChanging=");
            m20.append(this.mOrientationChanging);
            m20.append(" configOrientationChanging=");
            m20.append(
                    this.mLastReportedConfiguration.getMergedConfiguration().orientation
                            != getConfiguration().orientation);
            m20.append(" mAppFreezing=");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    m20, this.mAppFreezing, printWriter);
        }
        if (this.mLastFreezeDuration != 0) {
            printWriter.print(str + "mLastFreezeDuration=");
            TimeUtils.formatDuration((long) this.mLastFreezeDuration, printWriter);
            printWriter.println();
        }
        StringBuilder m21 =
                Preconditions$$ExternalSyntheticOutline0.m(str, "mForceSeamlesslyRotate=");
        m21.append(this.mForceSeamlesslyRotate);
        m21.append(" seamlesslyRotate: pending=");
        printWriter.print(m21.toString());
        SeamlessRotator seamlessRotator = this.mPendingSeamlessRotate;
        if (seamlessRotator != null) {
            printWriter.print("{old=");
            printWriter.print(seamlessRotator.mOldRotation);
            printWriter.print(", new=");
            printWriter.print(seamlessRotator.mNewRotation);
            printWriter.print("}");
        } else {
            printWriter.print("null");
        }
        printWriter.println();
        if (this.mXOffset != 0 || this.mYOffset != 0) {
            StringBuilder m22 = Preconditions$$ExternalSyntheticOutline0.m(str, "mXOffset=");
            m22.append(this.mXOffset);
            m22.append(" mYOffset=");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    m22, this.mYOffset, printWriter);
        }
        if (this.mHScale != 1.0f || this.mVScale != 1.0f) {
            StringBuilder m23 = Preconditions$$ExternalSyntheticOutline0.m(str, "mHScale=");
            m23.append(this.mHScale);
            m23.append(" mVScale=");
            AggressivePolicyHandler$$ExternalSyntheticOutline0.m(m23, this.mVScale, printWriter);
        }
        if (this.mWallpaperX != -1.0f || this.mWallpaperY != -1.0f) {
            StringBuilder m24 = Preconditions$$ExternalSyntheticOutline0.m(str, "mWallpaperX=");
            m24.append(this.mWallpaperX);
            m24.append(" mWallpaperY=");
            AggressivePolicyHandler$$ExternalSyntheticOutline0.m(
                    m24, this.mWallpaperY, printWriter);
        }
        if (this.mWallpaperXStep != -1.0f || this.mWallpaperYStep != -1.0f) {
            StringBuilder m25 = Preconditions$$ExternalSyntheticOutline0.m(str, "mWallpaperXStep=");
            m25.append(this.mWallpaperXStep);
            m25.append(" mWallpaperYStep=");
            AggressivePolicyHandler$$ExternalSyntheticOutline0.m(
                    m25, this.mWallpaperYStep, printWriter);
        }
        if (this.mWallpaperZoomOut != -1.0f) {
            AggressivePolicyHandler$$ExternalSyntheticOutline0.m(
                    Preconditions$$ExternalSyntheticOutline0.m(str, "mWallpaperZoomOut="),
                    this.mWallpaperZoomOut,
                    printWriter);
        }
        if (this.mWallpaperDisplayOffsetX != Integer.MIN_VALUE
                || this.mWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
            StringBuilder m26 =
                    Preconditions$$ExternalSyntheticOutline0.m(str, "mWallpaperDisplayOffsetX=");
            m26.append(this.mWallpaperDisplayOffsetX);
            m26.append(" mWallpaperDisplayOffsetY=");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    m26, this.mWallpaperDisplayOffsetY, printWriter);
        }
        if (this.mDrawLock != null) {
            StringBuilder m27 = Preconditions$$ExternalSyntheticOutline0.m(str, "mDrawLock=");
            m27.append(this.mDrawLock);
            printWriter.println(m27.toString());
        }
        if (this.mDragResizing) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    Preconditions$$ExternalSyntheticOutline0.m(str, "isDragResizing="),
                    this.mDragResizing,
                    printWriter);
        }
        if (computeDragResizing()) {
            StringBuilder m28 =
                    Preconditions$$ExternalSyntheticOutline0.m(str, "computeDragResizing=");
            m28.append(computeDragResizing());
            printWriter.println(m28.toString());
        }
        if (this.mImeInsetsConsumed) {
            printWriter.println(str + "mImeInsetsConsumed=true");
        }
        StringBuilder m29 = Preconditions$$ExternalSyntheticOutline0.m(str, "isOnScreen=");
        m29.append(isOnScreen());
        printWriter.println(m29.toString());
        printWriter.println(str + "isVisible=" + isVisible());
        printWriter.println(
                str
                        + "keepClearAreas: restricted="
                        + this.mKeepClearAreas
                        + ", unrestricted="
                        + this.mUnrestrictedKeepClearAreas);
        if (z && this.mRequestedVisibleTypes != WindowInsets.Type.defaultVisible()) {
            StringBuilder m30 =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            str, "Requested non-default-visibility types: ");
            m30.append(
                    WindowInsets.Type.toString(
                            this.mRequestedVisibleTypes ^ WindowInsets.Type.defaultVisible()));
            printWriter.println(m30.toString());
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        Preconditions$$ExternalSyntheticOutline0.m(str, "mPrepareSyncSeqId="),
                        this.mPrepareSyncSeqId,
                        printWriter,
                        str,
                        "mHiddenWhileProfileLockedState="),
                this.mHiddenWhileProfileLockedState,
                printWriter);
        if (this.mPopOverDimmerNeeded) {
            printWriter.println(str + "mPopOverDimmerNeeded=true");
        }
        if (this.mAttrs.layoutInDisplayCutoutMode != this.mOriginalLayoutInDisplayCutoutMode) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    Preconditions$$ExternalSyntheticOutline0.m(
                            str, "mOriginalLayoutInDisplayCutoutMode="),
                    this.mOriginalLayoutInDisplayCutoutMode,
                    printWriter);
        }
        if (CoreRune.FW_WAIT_TO_HANDLE_RESIZING_FROM_CLIENT && this.mWaitToHandleResizing) {
            printWriter.println(str + "mWaitToHandleResizing=true");
        }
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public final void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        boolean isVisible = isVisible();
        if (i != 2 || isVisible) {
            long start = protoOutputStream.start(j);
            super.dumpDebug(protoOutputStream, 1146756268033L, i);
            protoOutputStream.write(1120986464259L, getDisplayId());
            Task rootTask = getRootTask();
            protoOutputStream.write(1120986464260L, rootTask == null ? -1 : rootTask.mTaskId);
            this.mAttrs.dumpDebug(protoOutputStream, 1146756268037L);
            this.mGivenContentInsets.dumpDebug(protoOutputStream, 1146756268038L);
            WindowFrames windowFrames = this.mWindowFrames;
            windowFrames.getClass();
            long start2 = protoOutputStream.start(1146756268073L);
            windowFrames.mParentFrame.dumpDebug(protoOutputStream, 1146756268040L);
            windowFrames.mDisplayFrame.dumpDebug(protoOutputStream, 1146756268036L);
            windowFrames.mFrame.dumpDebug(protoOutputStream, 1146756268037L);
            windowFrames.mCompatFrame.dumpDebug(protoOutputStream, 1146756268048L);
            protoOutputStream.end(start2);
            this.mAttrs.surfaceInsets.dumpDebug(protoOutputStream, 1146756268044L);
            GraphicsProtos.dumpPointProto(this.mSurfacePosition, protoOutputStream, 1146756268048L);
            WindowStateAnimator windowStateAnimator = this.mWinAnimator;
            windowStateAnimator.getClass();
            long start3 = protoOutputStream.start(1146756268045L);
            WindowSurfaceController windowSurfaceController =
                    windowStateAnimator.mSurfaceController;
            if (windowSurfaceController != null) {
                long start4 = protoOutputStream.start(1146756268034L);
                protoOutputStream.write(1133871366145L, windowSurfaceController.mSurfaceShown);
                protoOutputStream.end(start4);
            }
            protoOutputStream.write(1159641169923L, windowStateAnimator.mDrawState);
            windowStateAnimator.mSystemDecorRect.dumpDebug(protoOutputStream, 1146756268036L);
            protoOutputStream.end(start3);
            protoOutputStream.write(1133871366158L, this.mAnimatingExit);
            protoOutputStream.write(1120986464274L, this.mRequestedWidth);
            protoOutputStream.write(1120986464275L, this.mRequestedHeight);
            protoOutputStream.write(1120986464276L, this.mViewVisibility);
            protoOutputStream.write(1133871366166L, this.mHasSurface);
            protoOutputStream.write(1133871366167L, isReadyForDisplay());
            protoOutputStream.write(1133871366178L, this.mRemoveOnExit);
            protoOutputStream.write(1133871366179L, this.mDestroying);
            protoOutputStream.write(1133871366180L, this.mRemoved);
            protoOutputStream.write(1133871366181L, isOnScreen());
            protoOutputStream.write(1133871366182L, isVisible);
            protoOutputStream.write(1133871366183L, this.mPendingSeamlessRotate != null);
            protoOutputStream.write(1133871366186L, this.mForceSeamlesslyRotate);
            protoOutputStream.write(1133871366187L, hasCompatScale());
            protoOutputStream.write(1108101562412L, this.mGlobalScale);
            protoOutputStream.write(1120986464304L, this.mRequestedVisibleTypes);
            Iterator it = ((ArrayList) this.mKeepClearAreas).iterator();
            while (it.hasNext()) {
                ((Rect) it.next()).dumpDebug(protoOutputStream, 2246267895853L);
            }
            Iterator it2 = ((ArrayList) this.mUnrestrictedKeepClearAreas).iterator();
            while (it2.hasNext()) {
                ((Rect) it2.next()).dumpDebug(protoOutputStream, 2246267895854L);
            }
            if (this.mMergedLocalInsetsSources != null) {
                for (int i2 = 0; i2 < this.mMergedLocalInsetsSources.size(); i2++) {
                    ((InsetsSource) this.mMergedLocalInsetsSources.valueAt(i2))
                            .dumpDebug(protoOutputStream, 2246267895855L);
                }
            }
            protoOutputStream.end(start);
        }
    }

    @Override // com.android.server.wm.InputTarget
    public final void dumpProto(int i, ProtoOutputStream protoOutputStream) {
        dumpDebug(protoOutputStream, 1146756268060L, i);
    }

    public final void fillClientWindowFramesAndConfiguration(
            ClientWindowFrames clientWindowFrames,
            MergedConfiguration mergedConfiguration,
            ActivityWindowInfo activityWindowInfo,
            boolean z,
            boolean z2) {
        Configuration globalConfiguration;
        int pid;
        ActivityWindowInfo activityWindowInfo2;
        ActivityRecord activityRecord;
        clientWindowFrames.frame.set(this.mWindowFrames.mCompatFrame);
        clientWindowFrames.displayFrame.set(this.mWindowFrames.mDisplayFrame);
        if (this.mIsChildWindow
                && (this.mAttrs.gravity & 80) == 80
                && this.mOverrideScale != 1.0f) {
            float f = this.mCompatScale;
            if (f != 1.0f) {
                clientWindowFrames.displayFrame.scale(f);
            }
        } else {
            float f2 = this.mInvGlobalScale;
            if (f2 != 1.0f) {
                clientWindowFrames.displayFrame.scale(f2);
            }
        }
        if (this.mLayoutAttached) {
            if (clientWindowFrames.attachedFrame == null) {
                clientWindowFrames.attachedFrame = new Rect();
            }
            if (this.mIsChildWindow
                    && (this.mAttrs.gravity & 80) == 80
                    && this.mOverrideScale != 1.0f) {
                float f3 = this.mCompatScale;
                if (f3 != 1.0f) {
                    clientWindowFrames.attachedFrame.scale(f3);
                }
            } else {
                clientWindowFrames.attachedFrame.set(getParentWindow().mWindowFrames.mFrame);
            }
            float f4 = this.mInvGlobalScale;
            if (f4 != 1.0f) {
                clientWindowFrames.attachedFrame.scale(f4);
            }
        }
        clientWindowFrames.compatScale =
                this.mToken.hasSizeCompatBounds() ? 1.0f : this.mCompatScale;
        ClientWindowFrames clientWindowFrames2 = this.mLastReportedFrames;
        if (clientWindowFrames2 != clientWindowFrames) {
            clientWindowFrames2.setTo(clientWindowFrames);
        }
        if (z
                || (z2
                        && ((activityRecord = this.mActivityRecord) == null
                                || activityRecord.isVisibleRequested()))) {
            if (this.mAttrs.type != 4
                    || (pid = this.mActivityRecord.getPid()) == 0
                    || pid == this.mSession.mPid) {
                int i = this.mSession.mPid;
                int i2 = WindowManagerService.MY_PID;
                if (i == i2 || i < 0) {
                    globalConfiguration = this.mWmService.mAtmService.getGlobalConfiguration();
                } else {
                    WindowState parentWindow = getParentWindow();
                    Session session = parentWindow != null ? parentWindow.mSession : this.mSession;
                    globalConfiguration =
                            session.mPid == i2
                                    ? this.mWmService.mRoot.getConfiguration()
                                    : session.mProcess.getConfiguration();
                }
            } else {
                globalConfiguration = this.mActivityRecord.app.getConfiguration();
            }
            mergedConfiguration.setConfiguration(
                    globalConfiguration, getMergedOverrideConfiguration());
            MergedConfiguration mergedConfiguration2 = this.mLastReportedConfiguration;
            if (mergedConfiguration != mergedConfiguration2) {
                mergedConfiguration2.setTo(mergedConfiguration);
            }
            if (activityWindowInfo != null && this.mLastReportedActivityWindowInfo != null) {
                activityWindowInfo.set(this.mActivityRecord.getActivityWindowInfo());
                this.mLastReportedActivityWindowInfo.set(activityWindowInfo);
            }
        } else {
            mergedConfiguration.setTo(this.mLastReportedConfiguration);
            if (activityWindowInfo != null
                    && (activityWindowInfo2 = this.mLastReportedActivityWindowInfo) != null) {
                activityWindowInfo.set(activityWindowInfo2);
            }
        }
        this.mLastConfigReportedToClient = true;
    }

    public final void fillInsetsState(InsetsState insetsState, boolean z) {
        InsetsState insetsState2 = getInsetsState(false);
        if (this.mInvGlobalScale != 1.0f) {
            InsetsState insetsState3 = new InsetsState(insetsState2, true);
            insetsState3.scale(this.mInvGlobalScale);
            insetsState2 = insetsState3;
        }
        insetsState.set(insetsState2, z);
        InsetsState insetsState4 = this.mLastReportedInsetsState;
        if (insetsState != insetsState4) {
            insetsState4.set(insetsState, false);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean fillsParent() {
        return this.mAttrs.type == 3;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean finishDrawing(android.view.SurfaceControl.Transaction r25, int r26) {
        /*
            Method dump skipped, instructions count: 814
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowState.finishDrawing(android.view.SurfaceControl$Transaction,"
                    + " int):boolean");
    }

    @Override // com.android.server.wm.WindowContainer
    public final void finishSync(
            SurfaceControl.Transaction transaction,
            BLASTSyncEngine.SyncGroup syncGroup,
            boolean z) {
        if (isDifferentSyncGroup(syncGroup)) {
            return;
        }
        this.mPrepareSyncSeqId = 0;
        if (z) {
            dropBufferFrom(this.mSyncTransaction);
        }
        super.finishSync(transaction, syncGroup, z);
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean forAllWindows(ToBooleanFunction toBooleanFunction, boolean z) {
        if (this.mChildren.isEmpty()) {
            return applyInOrderWithImeWindows(toBooleanFunction, z);
        }
        if (z) {
            int size = this.mChildren.size() - 1;
            WindowState windowState = (WindowState) this.mChildren.get(size);
            while (size >= 0 && windowState.mSubLayer >= 0) {
                if (windowState.applyInOrderWithImeWindows(toBooleanFunction, true)) {
                    break;
                }
                size--;
                if (size < 0) {
                    break;
                }
                windowState = (WindowState) this.mChildren.get(size);
            }
            if (!applyInOrderWithImeWindows(toBooleanFunction, true)) {
                while (size >= 0) {
                    if (!windowState.applyInOrderWithImeWindows(toBooleanFunction, true)) {
                        size--;
                        if (size < 0) {
                            return false;
                        }
                        windowState = (WindowState) this.mChildren.get(size);
                    }
                }
                return false;
            }
            return true;
        }
        int size2 = this.mChildren.size();
        WindowState windowState2 = (WindowState) this.mChildren.get(0);
        int i = 0;
        while (i < size2 && windowState2.mSubLayer < 0) {
            if (windowState2.applyInOrderWithImeWindows(toBooleanFunction, false)) {
                break;
            }
            i++;
            if (i >= size2) {
                break;
            }
            windowState2 = (WindowState) this.mChildren.get(i);
        }
        if (!applyInOrderWithImeWindows(toBooleanFunction, false)) {
            while (i < size2) {
                if (!windowState2.applyInOrderWithImeWindows(toBooleanFunction, false)) {
                    i++;
                    if (i >= size2) {
                        return false;
                    }
                    windowState2 = (WindowState) this.mChildren.get(i);
                }
            }
            return false;
        }
        return true;
    }

    public final void forceExecuteDrawHandlers(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < ((ArrayList) this.mDrawHandlers).size(); i2++) {
            DrawHandler drawHandler = (DrawHandler) ((ArrayList) this.mDrawHandlers).get(i2);
            if (drawHandler.mType == i) {
                drawHandler.mConsumer.accept(this.mTmpTransaction);
                arrayList.add(drawHandler);
                Slog.d(
                        "WindowManager",
                        "forceExecuteDrawHandlers: win=" + this + ", handler=" + drawHandler);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            ((ArrayList) this.mDrawHandlers).remove((DrawHandler) arrayList.get(i3));
        }
        if (((ArrayList) this.mDrawHandlers).isEmpty()) {
            this.mWmService.mH.removeMessages(64, this);
        }
        this.mTmpTransaction.apply();
    }

    public final void forceReportingResized() {
        this.mWindowFrames.mForceReportingResized = true;
    }

    @Override // com.android.server.wm.InputTarget
    public final ActivityRecord getActivityRecord() {
        return this.mActivityRecord;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void getAnimationFrames(Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        if (inFreeformWindowingMode()) {
            rect.set(this.mWindowFrames.mFrame);
        } else if (areAppWindowBoundsLetterboxed() || this.mToken.isFixedRotationTransforming()) {
            rect.set(getTask().getBounds());
        } else {
            rect.set(this.mWindowFrames.mParentFrame);
        }
        rect4.set(this.mAttrs.surfaceInsets);
        InsetsState insetsStateWithVisibilityOverride = getInsetsStateWithVisibilityOverride();
        rect2.set(
                insetsStateWithVisibilityOverride
                        .calculateInsets(rect, WindowInsets.Type.systemBars(), false)
                        .toRect());
        rect3.set(
                insetsStateWithVisibilityOverride
                        .calculateInsets(rect, WindowInsets.Type.systemBars(), true)
                        .toRect());
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl getAnimationLeashParent() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return (activityRecord == null
                        || activityRecord.hasFixedRotationTransform()
                        || !isStartingWindowAssociatedToTask())
                ? getParentSurfaceControl()
                : this.mStartingData.mAssociatedTask.mSurfaceControl;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final Rect getBounds() {
        return this.mToken.hasSizeCompatBounds() ? this.mToken.getBounds() : super.getBounds();
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final Configuration getConfiguration() {
        WindowState parentWindow = getParentWindow();
        Session session = parentWindow != null ? parentWindow.mSession : this.mSession;
        if (session.mPid != WindowManagerService.MY_PID) {
            session.mProcess.getClass();
        }
        return super.getConfiguration();
    }

    public final int getCoverMode() {
        if (getParentWindow() == null) {
            return this.mAttrs.coverMode;
        }
        if (getParentWindow().mAttrs.type == 2099) {
            return 10;
        }
        return getParentWindow().mAttrs.coverMode;
    }

    @Override // com.android.server.wm.InputTarget
    public final int getDisplayId() {
        DisplayContent displayContent = getDisplayContent();
        if (displayContent == null) {
            return -1;
        }
        return displayContent.mDisplayId;
    }

    public final DisplayInfo getDisplayInfo() {
        DisplayInfo fixedRotationTransformDisplayInfo =
                this.mToken.getFixedRotationTransformDisplayInfo();
        return fixedRotationTransformDisplayInfo != null
                ? fixedRotationTransformDisplayInfo
                : getDisplayContent().mDisplayInfo;
    }

    public final void getEffectiveTouchableRegion(Region region) {
        DisplayContent displayContent = getDisplayContent();
        if (!this.mAttrs.isModal() || displayContent == null) {
            getTouchableRegion(region);
            return;
        }
        region.set(displayContent.getBounds());
        cropRegionToRootTaskBoundsIfNeeded(region);
        Task task = getTask();
        if (task != null && task.inSplitScreenWindowingMode() && task.isLeafTask()) {
            task.getBounds(this.mTmpRect);
            if (!this.mTmpRect.isEmpty()) {
                region.op(this.mTmpRect, Region.Op.INTERSECT);
            }
        }
        subtractTouchExcludeRegionIfNeeded(region);
    }

    @Override // com.android.server.wm.InputTarget
    public final InsetsControlTarget getImeControlTarget() {
        return getDisplayContent().getImeHostOrFallback(this);
    }

    public final InsetsState getInsetsState(boolean z) {
        InsetsState insetsState;
        int i;
        Consumer consumer;
        WindowState windowState;
        InsetsSourceProvider controllableInsetProvider;
        InsetsSource insetsSource;
        WindowToken windowToken = this.mToken;
        InsetsState insetsState2 =
                windowToken.isFixedRotationTransforming()
                        ? windowToken.mFixedRotationTransformState.mDisplayFrames.mInsetsState
                        : null;
        InsetsPolicy insetsPolicy = getDisplayContent().mInsetsPolicy;
        boolean z2 = false;
        if (insetsState2 != null) {
            return insetsPolicy.adjustInsetsForWindow(this, insetsState2, false);
        }
        InsetsState insetsState3 = this.mFrozenInsetsState;
        if (insetsState3 == null) {
            insetsState3 =
                    this.mAttrs.receiveInsetsIgnoringZOrder
                            ? getDisplayContent().mInsetsStateController.mState
                            : this.mAboveInsetsState;
            if (this.mMergedLocalInsetsSources != null) {
                InsetsState insetsState4 = new InsetsState(insetsState3);
                for (int i2 = 0; i2 < this.mMergedLocalInsetsSources.size(); i2++) {
                    insetsState4.addSource(
                            (InsetsSource) this.mMergedLocalInsetsSources.valueAt(i2));
                }
                insetsState3 = insetsState4;
            }
        }
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        int windowingMode = getWindowingMode();
        boolean isAlwaysOnTop = isAlwaysOnTop();
        insetsPolicy.getClass();
        if (layoutParams.type == 2011) {
            insetsState = new InsetsState(insetsState3);
            insetsState.removeSource(InsetsSource.ID_IME);
        } else {
            InsetsFrameProvider[] insetsFrameProviderArr = layoutParams.providedInsets;
            if (insetsFrameProviderArr != null) {
                InsetsState insetsState5 = insetsState3;
                for (InsetsFrameProvider insetsFrameProvider : insetsFrameProviderArr) {
                    if ((insetsFrameProvider.getType() & WindowInsets.Type.systemBars()) != 0) {
                        if (insetsState5 == insetsState3) {
                            insetsState5 = new InsetsState(insetsState5);
                        }
                        insetsState5.removeSource(insetsFrameProvider.getId());
                    }
                }
                insetsState = insetsState5;
            } else {
                insetsState = insetsState3;
            }
        }
        if (!layoutParams.isFullscreen() || layoutParams.getFitInsetsTypes() != 0) {
            if (insetsState == insetsState3) {
                insetsState = new InsetsState(insetsState3);
            }
            if (CoreRune.MW_CAPTION_SHELL_INSETS
                    && insetsPolicy.mDisplayContent.isDesktopModeEnabled()
                    && layoutParams.y == 0
                    && layoutParams.height == -1
                    && ((i = layoutParams.type) == 1 || i == 2 || i == 4)) {
                z2 = true;
            }
            for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
                if (insetsState.sourceAt(sourceSize).getType() == WindowInsets.Type.captionBar()
                        && !z2) {
                    insetsState.removeSourceAt(sourceSize);
                }
            }
        }
        SparseArray sparseArray = insetsPolicy.mStateController.mProviders;
        int i3 = layoutParams.type;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            InsetsSourceProvider insetsSourceProvider =
                    (InsetsSourceProvider) sparseArray.valueAt(size);
            if (insetsSourceProvider.mOverrideFrames.contains(i3)) {
                if (insetsState == insetsState3) {
                    insetsState = new InsetsState(insetsState);
                }
                InsetsSource insetsSource2 = new InsetsSource(insetsSourceProvider.mSource);
                insetsSource2.setFrame((Rect) insetsSourceProvider.mOverrideFrames.get(i3));
                insetsState.addSource(insetsSource2);
            }
        }
        if (WindowConfiguration.isFloating(windowingMode)
                || (windowingMode == 6 && isAlwaysOnTop)) {
            int captionBar = WindowInsets.Type.captionBar();
            if (windowingMode != 2) {
                captionBar |= WindowInsets.Type.ime();
            }
            InsetsState insetsState6 = new InsetsState();
            insetsState6.set(insetsState, captionBar);
            insetsState = insetsState6;
        }
        if (isLayoutNeededInUdcCutout()) {
            this.mDisplayContent.mUdcCutoutPolicy.adjustInsetsForUdc(this, insetsState);
        }
        if (getControllableInsetProvider() == null
                && this.mAboveInsetsState.peekSource(InsetsSource.ID_IME) == null
                && getDisplayContent().mDisplayPolicy.mExt.mNavigationMode != 0) {
            DisplayPolicyExt displayPolicyExt = getDisplayContent().mDisplayPolicy.mExt;
            int i4 = this.mDisplayContent.mDisplayFrames.mRotation;
            PhoneWindowManagerExt phoneWindowManagerExt = displayPolicyExt.mService.mExt.mPolicyExt;
            if ((phoneWindowManagerExt.mNavBarImeBtnEnabled
                            || phoneWindowManagerExt.mShowKeyboardBtnEnabled)
                    && ((displayPolicyExt.mNavBarImeBtnAllRotationsAllowed
                                    || displayPolicyExt.mDisplayPolicy.mDisplayContent
                                            .mDisplayRotation.isAnyPortrait(i4))
                            && getDisplayContent()
                                    .mInsetsStateController
                                    .getImeSourceProvider()
                                    .mSource
                                    .isVisible()
                            && (windowState = getDisplayContent().mDisplayPolicy.mNavigationBar)
                                    != null
                            && (controllableInsetProvider =
                                            windowState.getControllableInsetProvider())
                                    != null
                            && (insetsSource = controllableInsetProvider.mSource) != null
                            && insetsSource.isVisible()
                            && controllableInsetProvider.mOverrideFrames.contains(2011)
                            && !((Rect) controllableInsetProvider.mOverrideFrames.get(2011))
                                    .equals(insetsSource.getFrame()))) {
                InsetsSource insetsSource3 = new InsetsSource(insetsSource);
                insetsSource3.setFrame((Rect) controllableInsetProvider.mOverrideFrames.get(2011));
                InsetsState insetsState7 = new InsetsState(insetsState);
                insetsState7.addSource(insetsSource3);
                insetsState = insetsState7;
            }
        }
        if (CoreRune.MW_SPLIT_FLEX_PANEL_SYSTEMUI_VISIBILITY
                && this.mAttrs.type != 2040
                && FlexPanelController.isFlexPanelTopEnabled(this)) {
            InsetsState insetsState8 = null;
            for (int sourceSize2 = insetsState.sourceSize() - 1; sourceSize2 >= 0; sourceSize2--) {
                final InsetsSource sourceAt = insetsState.sourceAt(sourceSize2);
                int type = sourceAt.getType();
                if (type == WindowInsets.Type.displayCutout()) {
                    final int i5 = 0;
                    consumer =
                            new Consumer() { // from class:
                                             // com.android.server.wm.WindowState$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int i6 = i5;
                                    InsetsSource insetsSource4 = sourceAt;
                                    InsetsState insetsState9 = (InsetsState) obj;
                                    switch (i6) {
                                        case 0:
                                            insetsState9.removeSource(insetsSource4.getId());
                                            break;
                                        default:
                                            InsetsSource insetsSource5 =
                                                    new InsetsSource(insetsSource4);
                                            insetsSource5.setVisible(false);
                                            insetsState9.addSource(insetsSource5);
                                            break;
                                    }
                                }
                            };
                } else if (type == WindowInsets.Type.statusBars() && sourceAt.isVisible()) {
                    final int i6 = 1;
                    consumer =
                            new Consumer() { // from class:
                                             // com.android.server.wm.WindowState$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int i62 = i6;
                                    InsetsSource insetsSource4 = sourceAt;
                                    InsetsState insetsState9 = (InsetsState) obj;
                                    switch (i62) {
                                        case 0:
                                            insetsState9.removeSource(insetsSource4.getId());
                                            break;
                                        default:
                                            InsetsSource insetsSource5 =
                                                    new InsetsSource(insetsSource4);
                                            insetsSource5.setVisible(false);
                                            insetsState9.addSource(insetsSource5);
                                            break;
                                    }
                                }
                            };
                } else {
                    consumer = null;
                }
                if (consumer != null) {
                    if (insetsState8 == null) {
                        insetsState8 = new InsetsState(insetsState);
                    }
                    consumer.accept(insetsState8);
                }
            }
            if (insetsState8 != null) {
                insetsState = insetsState8;
            }
        }
        if (isPopOver()) {
            int sourceSize3 = insetsState.sourceSize() - 1;
            while (true) {
                if (sourceSize3 < 0) {
                    break;
                }
                if (insetsState.sourceAt(sourceSize3).getType() == WindowInsets.Type.captionBar()) {
                    InsetsState insetsState9 = new InsetsState(insetsState);
                    insetsState9.removeSourceAt(sourceSize3);
                    insetsState = insetsState9;
                    break;
                }
                sourceSize3--;
            }
        }
        return insetsPolicy.adjustInsetsForWindow(this, insetsState, z);
    }

    public final InsetsState getInsetsStateWithVisibilityOverride() {
        InsetsState insetsState = new InsetsState(getInsetsState(false), true);
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
            boolean isRequestedVisible = isRequestedVisible(sourceAt.getType(), false);
            if (sourceAt.isVisible() != isRequestedVisible) {
                sourceAt.setVisible(isRequestedVisible);
            }
        }
        return insetsState;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final Rect getMaxBounds() {
        WindowToken windowToken = this.mToken;
        Rect maxBounds =
                windowToken.isFixedRotationTransforming()
                        ? windowToken.mFixedRotationTransformState.mRotatedOverrideConfiguration
                                .windowConfiguration.getMaxBounds()
                        : null;
        return maxBounds != null ? maxBounds : super.getMaxBounds();
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final String getName() {
        return Integer.toHexString(System.identityHashCode(this)) + " " + ((Object) getWindowTag());
    }

    public final boolean getOrientationChanging() {
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            return false;
        }
        return ((!this.mOrientationChanging
                                && (!isVisible()
                                        || getConfiguration().orientation
                                                == this.mLastReportedConfiguration
                                                        .getMergedConfiguration()
                                                        .orientation))
                        || this.mSeamlesslyRotated
                        || this.mOrientationChangeTimedOut)
                ? false
                : true;
    }

    public final WindowState getParentWindow() {
        if (this.mIsChildWindow) {
            return (WindowState) getParent();
        }
        return null;
    }

    @Override // com.android.server.wm.InputTarget
    public final int getPid() {
        return this.mSession.mPid;
    }

    @Override // com.android.server.wm.WindowContainer
    public final long getProtoFieldId() {
        return 1146756268040L;
    }

    public final List getRectsInScreenSpace(List list, Matrix matrix, float[] fArr) {
        getTransformationMatrix(fArr, matrix);
        ArrayList arrayList = new ArrayList();
        RectF rectF = new RectF();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            rectF.set((Rect) it.next());
            matrix.mapRect(rectF);
            Rect rect = new Rect();
            rectF.roundOut(rect);
            arrayList.add(rect);
        }
        return arrayList;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public final int getRequestedVisibleTypes() {
        return this.mRequestedVisibleTypes;
    }

    public final Task getRootTask() {
        Task task = getTask();
        if (task != null) {
            return task.getRootTask();
        }
        DisplayContent displayContent = getDisplayContent();
        if (this.mAttrs.type < 2000 || displayContent == null) {
            return null;
        }
        return displayContent.getDefaultTaskDisplayArea().mRootHomeTask;
    }

    @Override // com.android.server.wm.WindowContainer
    public final SurfaceSession getSession() {
        SurfaceSession surfaceSession = this.mSession.mSurfaceSession;
        return surfaceSession != null ? surfaceSession : getParent().getSession();
    }

    public final Task getTask() {
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null) {
            return activityRecord.task;
        }
        return null;
    }

    public final void getTouchableRegion(Region region) {
        Rect rect = this.mWindowFrames.mFrame;
        int i = this.mTouchableInsets;
        if (i == 1) {
            Rect rect2 = this.mGivenContentInsets;
            region.set(
                    rect.left + rect2.left,
                    rect.top + rect2.top,
                    rect.right - rect2.right,
                    rect.bottom - rect2.bottom);
        } else if (i == 2) {
            Rect rect3 = this.mGivenVisibleInsets;
            region.set(
                    rect.left + rect3.left,
                    rect.top + rect3.top,
                    rect.right - rect3.right,
                    rect.bottom - rect3.bottom);
        } else if (i != 3) {
            region.set(rect);
        } else {
            region.set(this.mGivenTouchableRegion);
            int i2 = rect.left;
            if (i2 != 0 || rect.top != 0) {
                region.translate(i2, rect.top);
            }
        }
        cropRegionToRootTaskBoundsIfNeeded(region);
        subtractTouchExcludeRegionIfNeeded(region);
    }

    public final void getTransformationMatrix(float[] fArr, Matrix matrix) {
        float f = this.mGlobalScale;
        fArr[0] = f;
        fArr[3] = 0.0f;
        fArr[1] = 0.0f;
        fArr[4] = f;
        transformSurfaceInsetsPosition(this.mAttrs.surfaceInsets, this.mTmpPoint);
        Point point = this.mSurfacePosition;
        int i = point.x;
        Point point2 = this.mTmpPoint;
        int i2 = i + point2.x;
        int i3 = point.y + point2.y;
        WindowContainer parent = getParent();
        if (this.mIsChildWindow) {
            WindowState parentWindow = getParentWindow();
            Rect rect = parentWindow.mWindowFrames.mFrame;
            int i4 = rect.left;
            Rect rect2 = parentWindow.mAttrs.surfaceInsets;
            i2 += i4 - rect2.left;
            i3 += rect.top - rect2.top;
        } else if (parent != null) {
            Rect bounds = parent.getBounds();
            i2 += bounds.left;
            i3 += bounds.top;
        }
        fArr[2] = i2;
        fArr[5] = i3;
        fArr[6] = 0.0f;
        fArr[7] = 0.0f;
        fArr[8] = 1.0f;
        matrix.setValues(fArr);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void getVisibleBounds(android.graphics.Rect r9) {
        /*
            r8 = this;
            com.android.server.wm.Task r0 = r8.getTask()
            r1 = 0
            if (r0 == 0) goto Lf
            boolean r2 = r0.cropWindowsToRootTaskBounds()
            if (r2 == 0) goto Lf
            r2 = 1
            goto L10
        Lf:
            r2 = r1
        L10:
            r9.setEmpty()
            android.graphics.Rect r3 = r8.mTmpRect
            r3.setEmpty()
            if (r2 == 0) goto L25
            com.android.server.wm.Task r0 = r0.getRootTask()
            if (r0 == 0) goto L26
            android.graphics.Rect r1 = r8.mTmpRect
            r0.getDimBounds(r1)
        L25:
            r1 = r2
        L26:
            com.android.server.wm.WindowFrames r0 = r8.mWindowFrames
            android.graphics.Rect r0 = r0.mFrame
            r9.set(r0)
            android.view.InsetsState r2 = r8.getInsetsStateWithVisibilityOverride()
            android.view.WindowManager$LayoutParams r0 = r8.mAttrs
            int r4 = r0.type
            int r5 = r8.getActivityType()
            android.view.WindowManager$LayoutParams r0 = r8.mAttrs
            int r6 = r0.softInputMode
            int r7 = r0.flags
            r3 = r9
            android.graphics.Insets r0 = r2.calculateVisibleInsets(r3, r4, r5, r6, r7)
            r9.inset(r0)
            if (r1 == 0) goto L4e
            android.graphics.Rect r8 = r8.mTmpRect
            r9.intersect(r8)
        L4e:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowState.getVisibleBounds(android.graphics.Rect):void");
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public final WindowState getWindow() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    public final WindowState getWindow(Predicate predicate) {
        if (this.mChildren.isEmpty()) {
            if (predicate.test(this)) {
                return this;
            }
            return null;
        }
        int size = this.mChildren.size() - 1;
        WindowState windowState = (WindowState) this.mChildren.get(size);
        while (size >= 0 && windowState.mSubLayer >= 0) {
            if (predicate.test(windowState)) {
                return windowState;
            }
            size--;
            if (size < 0) {
                break;
            }
            windowState = (WindowState) this.mChildren.get(size);
        }
        if (predicate.test(this)) {
            return this;
        }
        while (size >= 0) {
            if (predicate.test(windowState)) {
                return windowState;
            }
            size--;
            if (size < 0) {
                break;
            }
            windowState = (WindowState) this.mChildren.get(size);
        }
        return null;
    }

    @Override // com.android.server.wm.InputTarget
    public final WindowState getWindowState() {
        return this;
    }

    public final CharSequence getWindowTag() {
        CharSequence title = this.mAttrs.getTitle();
        return (title == null || title.length() <= 0) ? this.mAttrs.packageName : title;
    }

    @Override // com.android.server.wm.InputTarget
    public final IBinder getWindowToken() {
        return this.mClient.asBinder();
    }

    @Override // com.android.server.wm.WindowContainer
    public final int getWindowType() {
        return this.mAttrs.type;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean handleCompleteDeferredRemoval() {
        if (this.mRemoveOnExit && !isSelfAnimating(0, 16)) {
            this.mRemoveOnExit = false;
            removeImmediately();
        }
        return super.handleCompleteDeferredRemoval();
    }

    @Override // com.android.server.wm.InputTarget
    public final void handleTapOutsideFocusInsideSelf() {
        int i;
        if (this.mWmService.mAtmService.mDexController.getDexModeLocked() != 2
                || ((i = this.mAttrs.type) != 2011 && i != 2012 && i != 2019)) {
            this.mWmService.moveDisplayToTopInternal(getDisplayId());
        }
        this.mWmService.handleTaskFocusChange(getTask(), this.mActivityRecord);
    }

    @Override // com.android.server.wm.InputTarget
    public final void handleTapOutsideFocusOutsideSelf() {}

    public final boolean hasCompatScale() {
        if (this.mAttrs.type == 3) {
            return false;
        }
        CompatModePackages compatModePackages = this.mWmService.mAtmService.mCompatModePackages;
        if (compatModePackages.mLegacyScreenCompatPackages.size() == 0
                ? false
                : compatModePackages.mLegacyScreenCompatPackages.get(
                        this.mSession.mProcess.mInfo.packageName.hashCode())) {
            return true;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        return (activityRecord != null && activityRecord.hasSizeCompatBounds())
                || this.mOverrideScale != 1.0f;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean hasContentToDisplay() {
        if (!this.mAppFreezing && isDrawn()) {
            if (this.mViewVisibility == 0) {
                return true;
            }
            if (isAnimating(3) && !getDisplayContent().mAppTransition.isTransitionSet()) {
                return true;
            }
        }
        return super.hasContentToDisplay();
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0038, code lost:

       if (r1.left == r0.left) goto L27;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean hasMoved() {
        /*
            r4 = this;
            boolean r0 = r4.mHasSurface
            if (r0 == 0) goto L52
            com.android.server.wm.WindowFrames r0 = r4.mWindowFrames
            boolean r1 = r0.mContentChanged
            if (r1 != 0) goto Le
            boolean r1 = r4.mMovedByResize
            if (r1 == 0) goto L52
        Le:
            boolean r1 = r4.mAnimatingExit
            if (r1 != 0) goto L52
            android.graphics.Rect r1 = r0.mRelFrame
            int r2 = r1.top
            android.graphics.Rect r0 = r0.mLastRelFrame
            int r3 = r0.top
            if (r2 != r3) goto L3a
            int r1 = r1.left
            int r0 = r0.left
            if (r1 != r0) goto L3a
            boolean r0 = r4.isPopOver()
            if (r0 == 0) goto L52
            com.android.server.wm.WindowFrames r0 = r4.mWindowFrames
            android.graphics.Rect r1 = r0.mFrame
            int r2 = r1.top
            android.graphics.Rect r0 = r0.mLastFrame
            int r3 = r0.top
            if (r2 != r3) goto L3a
            int r1 = r1.left
            int r0 = r0.left
            if (r1 == r0) goto L52
        L3a:
            boolean r0 = r4.mIsChildWindow
            if (r0 == 0) goto L48
            com.android.server.wm.WindowState r0 = r4.getParentWindow()
            boolean r0 = r0.hasMoved()
            if (r0 != 0) goto L52
        L48:
            com.android.server.wm.TransitionController r4 = r4.mTransitionController
            boolean r4 = r4.isCollecting()
            if (r4 != 0) goto L52
            r4 = 1
            goto L53
        L52:
            r4 = 0
        L53:
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.wm.WindowState.hasMoved():boolean");
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean hasWallpaper() {
        ActivityRecord activityRecord;
        return (this.mAttrs.flags & 1048576) != 0
                || ((activityRecord = this.mActivityRecord) != null
                        && activityRecord.mAppCompatController.mAppCompatOverrides
                                .mAppCompatLetterboxOverrides.hasWallpaperBackgroundForLetterbox());
    }

    public final boolean hide(boolean z, boolean z2) {
        if (z && !this.mToken.okToAnimate()) {
            z = false;
        }
        if (!(z ? this.mLegacyPolicyVisibilityAfterAnim : (this.mPolicyVisibility & 1) != 0)) {
            return false;
        }
        if (z && !this.mWinAnimator.applyAnimationLocked(2, false)) {
            z = false;
        }
        this.mLegacyPolicyVisibilityAfterAnim = false;
        boolean isFocused = isFocused();
        if (!z) {
            clearPolicyVisibilityFlag(1);
            this.mWmService.enableScreenIfNeededLocked();
            if (isFocused) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_LIGHT_enabled[2]) {
                    ProtoLogImpl_54989576.i(
                            ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
                            -208079497999140637L,
                            0,
                            null,
                            null);
                }
                this.mWmService.mFocusMayChange = true;
            }
        }
        if (z2) {
            this.mWmService.scheduleAnimationLocked();
        }
        if (isFocused) {
            this.mWmService.updateFocusedWindowLocked(0, false);
        }
        return true;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public final void hideInsets(int i, boolean z, ImeTracker.Token token) {
        try {
            ImeTracker.forLogging().onProgress(token, 22);
            this.mClient.hideInsets(i, true, token);
        } catch (RemoteException e) {
            Slog.w("WindowManager", "Failed to deliver hideInsets", e);
            ImeTracker.forLogging().onFailed(token, 22);
        }
    }

    public final void initAppOpsState() {
        int startOpNoThrow;
        int i = this.mAppOp;
        if (i == -1
                || !this.mAppOpVisibility
                || (startOpNoThrow =
                                this.mWmService.mAppOps.startOpNoThrow(
                                        i,
                                        this.mOwnerUid,
                                        this.mAttrs.packageName,
                                        true,
                                        null,
                                        "init-default-visibility"))
                        == 0
                || startOpNoThrow == 3) {
            return;
        }
        setAppOpVisibilityLw(false);
    }

    public final boolean isAnimationRunningSelfOrParent() {
        return inTransitionSelfOrParent() || isAnimating(0, 16);
    }

    public final boolean isDisplayed() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return isDrawn()
                && isVisibleByPolicy()
                && ((!isParentWindowHidden()
                                && (activityRecord == null || activityRecord.isVisibleRequested()))
                        || isAnimationRunningSelfOrParent());
    }

    public final boolean isDrawn() {
        int i;
        return this.mHasSurface
                && !this.mDestroying
                && ((i = this.mWinAnimator.mDrawState) == 3 || i == 4);
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isEmbedded() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && activityRecord.isEmbedded();
    }

    public final boolean isFocused() {
        return getDisplayContent().mCurrentFocus == this;
    }

    public final boolean isFullyTransparent() {
        return this.mAttrs.alpha == FullScreenMagnificationGestureHandler.MAX_SCALE;
    }

    public final boolean isGoneForLayout() {
        WindowState parentWindow;
        ActivityRecord activityRecord = this.mActivityRecord;
        return this.mViewVisibility == 8
                || !this.mRelayoutCalled
                || (activityRecord == null
                        && !(wouldBeVisibleIfPolicyIgnored() && isVisibleByPolicy()))
                || (!(activityRecord == null || activityRecord.isVisibleRequested())
                        || (((parentWindow = getParentWindow()) != null
                                        && parentWindow.isGoneForLayout())
                                || ((this.mAnimatingExit && !isAnimating(3)) || this.mDestroying)));
    }

    public final boolean isImeLayeringTarget() {
        return getDisplayContent().getImeTarget(0) == this;
    }

    public final boolean isImeOverlayLayeringTarget() {
        return isImeLayeringTarget() && (this.mAttrs.flags & 131080) != 0;
    }

    @Override // com.android.server.wm.InputTarget
    public final boolean isInputMethodClientFocus(int i, int i2) {
        WindowState computeImeTarget = getDisplayContent().computeImeTarget(false);
        if (computeImeTarget == null) {
            return false;
        }
        Session session = computeImeTarget.mSession;
        return session.mUid == i && session.mPid == i2;
    }

    public final boolean isLetterboxedForDisplayCutout() {
        if (this.mActivityRecord == null
                || !this.mWindowFrames.mParentFrameWasClippedByDisplayCutout) {
            return false;
        }
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        if (layoutParams.layoutInDisplayCutoutMode == 3 || !layoutParams.isFullscreen()) {
            return false;
        }
        this.mTmpRect.set(this.mActivityRecord.getBounds());
        this.mTmpRect.intersectUnchecked(this.mWindowFrames.mFrame);
        return !this.mActivityRecord.getBounds().equals(this.mTmpRect);
    }

    public final boolean isOnScreen() {
        TransientLaunchOverlayToken asTransientLaunchOverlay;
        if (!this.mHasSurface || this.mDestroying || !isVisibleByPolicy()) {
            return false;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null) {
            return (!isParentWindowHidden()
                            && (isStartingWindowAssociatedToTask()
                                    ? this.mStartingData.mAssociatedTask.isVisible()
                                    : activityRecord.mVisible))
                    || isAnimationRunningSelfOrParent();
        }
        WallpaperWindowToken asWallpaperToken = this.mToken.asWallpaperToken();
        return asWallpaperToken != null
                ? !isParentWindowHidden() && asWallpaperToken.mClientVisible
                : (!CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY
                                || (asTransientLaunchOverlay =
                                                this.mToken.asTransientLaunchOverlay())
                                        == null)
                        ? !isParentWindowHidden() || isAnimating(3)
                        : asTransientLaunchOverlay.isVisibleRequested();
    }

    public final boolean isParentWindowHidden() {
        WindowState parentWindow = getParentWindow();
        return parentWindow != null && parentWindow.mHidden;
    }

    public final boolean isPopOver() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && activityRecord.mPopOverState.mIsActivated;
    }

    public final boolean isReadyForDisplay() {
        boolean z = !isParentWindowHidden() && this.mViewVisibility == 0 && this.mToken.isVisible();
        if (this.mHasSurface && isVisibleByPolicy() && !this.mDestroying) {
            return z || isAnimating(3);
        }
        return false;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public final boolean isRequestedVisible(int i) {
        return isRequestedVisible(i, false);
    }

    public final boolean isRequestedVisible(int i, boolean z) {
        if ((WindowInsets.Type.statusBars() & i) == 0
                || !PolicyControl.shouldApplyImmersiveStatus(this)) {
            return ((WindowInsets.Type.navigationBars() & i) == 0
                            || !PolicyControl.shouldApplyImmersiveNavigation(this, z))
                    && (this.mRequestedVisibleTypes & i) != 0;
        }
        return false;
    }

    public final boolean isRtl() {
        return getConfiguration().getLayoutDirection() == 1;
    }

    public final boolean isSecureLocked() {
        if (this.mWmService.mDisableSecureWindows) {
            return false;
        }
        if ((this.mAttrs.flags & 8192) != 0) {
            return true;
        }
        try {
            if (this.mRestrictionPolicy == null) {
                this.mRestrictionPolicy =
                        (RestrictionPolicyInternal)
                                LocalServices.getService(RestrictionPolicyInternal.class);
            }
            RestrictionPolicyInternal restrictionPolicyInternal = this.mRestrictionPolicy;
            this.mRestrictionPolicy = restrictionPolicyInternal;
            if (restrictionPolicyInternal != null
                    && !restrictionPolicyInternal.isScreenCaptureEnabledEx(
                            this.mShowUserId, false)) {
                Slog.d(
                        "WindowManager",
                        "Screen Capture is disabled by mdm for user(" + this.mShowUserId + ")");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.mWmService.mSensitiveContentPackages.shouldBlockScreenCaptureForApp(
                this.mOwnerUid, this.mClient.asBinder(), this.mAttrs.packageName)) {
            return true;
        }
        if (DevicePolicyCache.getInstance() == null) {
            return false;
        }
        return !DevicePolicyCache.getInstance().isScreenCaptureAllowed(this.mShowUserId);
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isSelfAnimating(int i, int i2) {
        if (this.mControllableInsetProvider != null) {
            return false;
        }
        return super.isSelfAnimating(i, i2);
    }

    public boolean isSelfOrAncestorWindowAnimatingExit() {
        while (!this.mAnimatingExit) {
            this = this.getParentWindow();
            if (this == null) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isSplitEmbedded() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && activityRecord.isSplitEmbedded();
    }

    public final boolean isStartingWindowAssociatedToTask() {
        StartingData startingData = this.mStartingData;
        return (startingData == null || startingData.mAssociatedTask == null) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isSyncFinished(BLASTSyncEngine.SyncGroup syncGroup) {
        if (!isVisibleRequested() || isFullyTransparent()) {
            return true;
        }
        if (CoreRune.FW_WAIT_TO_HANDLE_RESIZING_FROM_CLIENT && this.mWaitToHandleResizing) {
            return false;
        }
        if (this.mSyncState == 1
                && this.mLastConfigReportedToClient
                && isDrawn()
                && this.mPrepareSyncSeqId <= 0) {
            onSyncFinishedDrawing();
        } else if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX
                && this.mActivityRecord == null
                && this.mSyncState == 1
                && this.mViewVisibility != 0) {
            onSyncFinishedDrawing();
        }
        return super.isSyncFinished(syncGroup);
    }

    public final boolean isTrustedOverlay() {
        if (!Flags.surfaceTrustedOverlay()) {
            return (this.mInputWindowHandle.mHandle.inputConfig & 256) != 0;
        }
        WindowState parentWindow = getParentWindow();
        if (isWindowTrustedOverlay()) {
            return true;
        }
        return parentWindow != null && parentWindow.isWindowTrustedOverlay();
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isVisible() {
        InsetsSourceProvider insetsSourceProvider;
        return wouldBeVisibleIfPolicyIgnored()
                && isVisibleByPolicy()
                && ((insetsSourceProvider = this.mControllableInsetProvider) == null
                        || insetsSourceProvider.mClientVisible);
    }

    public final boolean isVisibleByPolicy() {
        return (this.mPolicyVisibility & 3) == 3;
    }

    public final boolean isVisibleNow() {
        return (this.mToken.isVisible() || this.mAttrs.type == 3) && isVisible();
    }

    @Override // com.android.server.wm.WindowContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isVisibleRequested() {
        /*
            r3 = this;
            com.android.server.wm.WindowState r0 = r3.getParentWindow()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto Lf
            boolean r0 = r0.isVisibleRequested()
            if (r0 != 0) goto Lf
            goto L3d
        Lf:
            boolean r0 = r3.mAnimatingExit
            if (r0 != 0) goto L3d
            boolean r0 = r3.mDestroying
            if (r0 == 0) goto L18
            goto L3d
        L18:
            boolean r0 = com.samsung.android.rune.CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY
            if (r0 == 0) goto L2b
            com.android.server.wm.WindowToken r0 = r3.mToken
            com.android.server.wm.TransientLaunchOverlayToken r0 = r0.asTransientLaunchOverlay()
            if (r0 == 0) goto L2b
            com.android.server.wm.WindowToken r0 = r3.mToken
            boolean r0 = r0.isVisibleRequested()
            goto L3e
        L2b:
            com.android.server.wm.WindowToken r0 = r3.mToken
            com.android.server.wm.WallpaperWindowToken r0 = r0.asWallpaperToken()
            if (r0 == 0) goto L3b
            com.android.server.wm.WindowToken r0 = r3.mToken
            boolean r0 = r0.isVisibleRequested()
            if (r0 == 0) goto L3d
        L3b:
            r0 = r2
            goto L3e
        L3d:
            r0 = r1
        L3e:
            if (r0 == 0) goto L54
            boolean r0 = r3.isVisibleByPolicy()
            if (r0 == 0) goto L50
            com.android.server.wm.InsetsSourceProvider r0 = r3.mControllableInsetProvider
            if (r0 == 0) goto L4e
            boolean r0 = r0.mClientVisible
            if (r0 == 0) goto L50
        L4e:
            r0 = r2
            goto L51
        L50:
            r0 = r1
        L51:
            if (r0 == 0) goto L54
            r1 = r2
        L54:
            if (r1 == 0) goto L63
            boolean r0 = r3.shouldCheckTokenVisibleRequested()
            if (r0 == 0) goto L63
            com.android.server.wm.WindowToken r3 = r3.mToken
            boolean r3 = r3.isVisibleRequested()
            return r3
        L63:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowState.isVisibleRequested():boolean");
    }

    public final boolean isVisibleRequestedOrAdding() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return (this.mHasSurface || (!this.mRelayoutCalled && this.mViewVisibility == 0))
                && isVisibleByPolicy()
                && !isParentWindowHidden()
                && !((activityRecord != null && !activityRecord.isVisibleRequested())
                        || this.mAnimatingExit
                        || this.mDestroying);
    }

    public final boolean isWindowTrustedOverlay() {
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        int i = layoutParams.type;
        if (i != 2039 && i != 2011 && i != 2012 && i != 2027 && i != 2000 && i != 2040 && i != 2019
                && i != 2024 && i != 2015 && i != 2034 && i != 2032 && i != 2022 && i != 2031
                && i != 2041) {
            int i2 = layoutParams.privateFlags;
            if (((536870912 & i2) == 0 || !this.mSession.mCanAddInternalSystemWindow)
                    && (((i2 & 8) == 0 || !this.mSession.mCanCreateSystemApplicationOverlay)
                            && (layoutParams.samsungFlags & 131072) == 0)) {
                return false;
            }
        }
        return true;
    }

    public final void logExclusionRestrictions(int i) {
        WindowManager.LayoutParams layoutParams;
        int i2;
        DisplayContent.AnonymousClass1 anonymousClass1 = DisplayContent.COPY_SOURCE_VISIBILITY;
        if (this.mWmService.mConstants.mSystemGestureExclusionLogDebounceTimeoutMillis <= 0
                || (i2 = (layoutParams = this.mAttrs).type) == 2013
                || i2 == 3
                || i2 == 2019
                || (layoutParams.flags & 16) != 0) {
            return;
        }
        if (DisplayContent.needsGestureExclusionRestrictions(this, true)) {
            DisplayPolicy displayPolicy = getDisplayContent().mDisplayPolicy;
            if (displayPolicy.mHasNavigationBar) {
                if ((displayPolicy.mLeftGestureInset > 0 || displayPolicy.mRightGestureInset > 0)
                        && SystemClock.uptimeMillis()
                                >= this.mLastExclusionLogUptimeMillis[i]
                                        + this.mWmService
                                                .mConstants
                                                .mSystemGestureExclusionLogDebounceTimeoutMillis) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    long[] jArr = this.mLastExclusionLogUptimeMillis;
                    long j = uptimeMillis - jArr[i];
                    jArr[i] = uptimeMillis;
                    int i3 = this.mLastRequestedExclusionHeight[i];
                    FrameworkStatsLog.write(
                            FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED,
                            this.mAttrs.packageName,
                            i3,
                            i3 - this.mLastGrantedExclusionHeight[i],
                            i + 1,
                            getConfiguration().orientation == 2,
                            false,
                            (int) j);
                }
            }
        }
    }

    public final boolean mightAffectAllDrawn() {
        int i = this.mWinAnimator.mAttrType;
        return ((!isOnScreen() && !(i == 1 || i == 4)) || this.mAnimatingExit || this.mDestroying)
                ? false
                : true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean needsZBoost() {
        ActivityRecord activityRecord;
        InsetsControlTarget imeTarget = getDisplayContent().getImeTarget(0);
        if (!this.mIsImWindow
                || imeTarget == null
                || (activityRecord = imeTarget.getWindow().mActivityRecord) == null) {
            return false;
        }
        return activityRecord.needsZBoost();
    }

    public final void notifyInsetsChanged() {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_INSETS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_WINDOW_INSETS,
                    -5211036212243647844L,
                    0,
                    null,
                    String.valueOf(this));
        }
        WindowFrames windowFrames = this.mWindowFrames;
        if (!windowFrames.mInsetsChanged) {
            windowFrames.mInsetsChanged = true;
            WindowManagerService windowManagerService = this.mWmService;
            windowManagerService.mWindowsInsetsChanged++;
            windowManagerService.mH.removeMessages(66);
            this.mWmService.mH.sendEmptyMessage(66);
        }
        WindowContainer parent = getParent();
        if (parent != null) {
            parent.updateOverlayInsetsState(this);
        }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public final void notifyInsetsControlChanged(int i) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_INSETS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_WINDOW_INSETS,
                    -3186229270467822891L,
                    0,
                    null,
                    String.valueOf(this));
        }
        if (this.mRemoved) {
            return;
        }
        fillInsetsState(this.mLastReportedInsetsState, false);
        InsetsSourceControl.Array array = this.mLastReportedActiveControls;
        array.set(getDisplayContent().mInsetsStateController.getControlsForDispatch(this), false);
        InsetsSourceControl.Array array2 = this.mLastReportedActiveControls;
        if (array != array2) {
            array2.setTo(array, false);
        }
        if (Flags.insetsControlChangedItem()) {
            this.mSession.mProcess.scheduleClientTransactionItem(
                    WindowStateInsetsControlChangeItem.obtain(
                            this.mClient,
                            this.mLastReportedInsetsState,
                            this.mLastReportedActiveControls));
            return;
        }
        try {
            this.mClient.insetsControlChanged(
                    this.mLastReportedInsetsState, this.mLastReportedActiveControls);
        } catch (RemoteException e) {
            Slog.w("WindowManager", "Failed to deliver inset control state change to w=" + this, e);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
        super.onAnimationFinished(i, animationAdapter);
        WindowStateAnimator windowStateAnimator = this.mWinAnimator;
        boolean z = ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled[1];
        WindowState windowState = windowStateAnimator.mWin;
        if (z) {
            String valueOf = String.valueOf(windowStateAnimator);
            boolean z2 = windowState.mAnimatingExit;
            ActivityRecord activityRecord = windowState.mActivityRecord;
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_ANIM,
                    -1495677286613044867L,
                    60,
                    null,
                    valueOf,
                    Boolean.valueOf(z2),
                    Boolean.valueOf(activityRecord != null && activityRecord.reportedVisible));
        }
        int i2 = windowState.mPolicyVisibility;
        boolean z3 = (i2 & 1) != 0;
        boolean z4 = windowState.mLegacyPolicyVisibilityAfterAnim;
        if (z3 != z4) {
            if (z4) {
                windowState.mPolicyVisibility = i2 | 1;
                windowState.mWmService.scheduleAnimationLocked();
            } else {
                windowState.clearPolicyVisibilityFlag(1);
            }
            if (!windowState.isVisibleByPolicy()) {
                windowState.mWinAnimator.hide(
                        windowState.getPendingTransaction(), "checkPolicyVisibilityChange");
                if (windowState.isFocused()) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_LIGHT_enabled[2]) {
                        ProtoLogImpl_54989576.i(
                                ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
                                -6167820560758523840L,
                                0,
                                null,
                                null);
                    }
                    windowState.mWmService.mFocusMayChange = true;
                }
                windowState.setDisplayLayoutNeeded();
                windowState.mWmService.enableScreenIfNeededLocked();
            }
        }
        DisplayContent displayContent = windowState.getDisplayContent();
        int i3 = windowStateAnimator.mAttrType;
        if ((i3 == 2000 || i3 == 2040) && windowState.isVisibleByPolicy()) {
            displayContent.setLayoutNeeded();
        }
        windowState.onExitAnimationDone();
        displayContent.pendingLayoutChanges |= 8;
        if (displayContent.mWallpaperController.isWallpaperTarget(windowState)) {
            displayContent.pendingLayoutChanges |= 4;
        }
        ActivityRecord activityRecord2 = windowState.mActivityRecord;
        if (activityRecord2 != null) {
            activityRecord2.updateReportedVisibilityLocked();
        }
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public final void onAnimationLeashCreated(
            SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        super.onAnimationLeashCreated(transaction, surfaceControl);
        if (isStartingWindowAssociatedToTask()) {
            transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
        }
    }

    public final void onAppVisibilityChanged(boolean z, boolean z2) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowState) this.mChildren.get(size)).onAppVisibilityChanged(z, z2);
        }
        boolean isVisibleNow = isVisibleNow();
        if (this.mAttrs.type != 3) {
            if (z != isVisibleNow) {
                if (!z2 && isVisibleNow) {
                    AccessibilityController accessibilityController =
                            this.mWmService.mAccessibilityController;
                    this.mWinAnimator.applyAnimationLocked(2, false);
                    if (accessibilityController.hasCallbacks()) {
                        accessibilityController.onWindowTransition(this, 2);
                    }
                }
                setDisplayLayoutNeeded();
                return;
            }
            return;
        }
        if (!z && isVisibleNow && this.mActivityRecord.isAnimating(3)) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_ANIM,
                        7646042751617940718L,
                        0,
                        null,
                        String.valueOf(this));
            }
            this.mAnimatingExit = true;
            this.mRemoveOnExit = true;
            this.mWindowRemovalAllowed = true;
        }
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public final void onConfigurationChanged(Configuration configuration) {
        Configuration configuration2 = super.getConfiguration();
        this.mTempConfiguration.setTo(configuration2);
        int windowingMode = getWindowingMode();
        super.onConfigurationChanged(configuration);
        if (inFreeformWindowingMode() && windowingMode != 5) {
            getDisplayContent().updateImeControlTarget(true);
            return;
        }
        int diff = configuration2.diff(this.mTempConfiguration);
        if (diff != 0) {
            this.mLastConfigReportedToClient = false;
        }
        if ((getDisplayContent().mImeInputTarget == this || isImeLayeringTarget())
                && (diff & 536870912) != 0) {
            this.mDisplayContent.updateImeControlTarget(isImeLayeringTarget());
            StartingData startingData = this.mStartingData;
            if (startingData == null
                    || startingData.mAssociatedTask != null
                    || this.mTempConfiguration.windowConfiguration.getRotation()
                            != configuration2.windowConfiguration.getRotation()
                    || this.mTempConfiguration
                            .windowConfiguration
                            .getBounds()
                            .equals(getBounds())) {
                return;
            }
            this.mStartingData.mResizedFromTransfer = true;
            this.mActivityRecord.associateStartingWindowWithTaskIfNeeded();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onDisplayChanged(DisplayContent displayContent) {
        InputWindowHandleWrapper inputWindowHandleWrapper;
        InputWindowHandle inputWindowHandle;
        int i;
        int i2;
        DisplayContent displayContent2;
        InputTarget inputTarget;
        boolean z =
                (displayContent == null
                                || this.mDisplayContent == null
                                || (!displayContent.isAppCastingDisplay()
                                        && !this.mDisplayContent.isAppCastingDisplay()))
                        ? false
                        : true;
        boolean z2 = CoreRune.CARLIFE_NAVBAR;
        boolean z3 =
                z2
                        && displayContent != null
                        && this.mDisplayContent != null
                        && (displayContent.isCarLifeDisplay()
                                || this.mDisplayContent.isCarLifeDisplay());
        if (displayContent != null
                && (displayContent2 = this.mDisplayContent) != null
                && displayContent != displayContent2
                && (inputTarget = displayContent2.mImeInputTarget) == this) {
            displayContent.updateImeInputAndControlTarget(
                    inputTarget != null ? inputTarget.getWindowState() : null);
            this.mDisplayContent.setImeInputTarget(null);
            if (this.mWmService.mAtmService.mDexController.getDexModeLocked() == 2) {
                DisplayContent displayContent3 = this.mDisplayContent;
                if (displayContent3.isDefaultDisplay && displayContent.mDisplayId == 2) {
                    displayContent3.updateImeControlTarget(false);
                }
            }
        }
        super.onDisplayChanged(displayContent);
        if (displayContent != null
                && (i =
                                (inputWindowHandle =
                                                (inputWindowHandleWrapper = this.mInputWindowHandle)
                                                        .mHandle)
                                        .displayId)
                        != (i2 = displayContent.mDisplayId)) {
            this.mLayoutSeq = displayContent.mLayoutSeq - 1;
            if (i != i2) {
                inputWindowHandle.displayId = i2;
                inputWindowHandleWrapper.mChanged = true;
            }
        }
        if (z || (z2 && z3)) {
            displayContent.mInsetsStateController.updateAboveInsetsState(false);
        }
    }

    public final void onExitAnimationDone() {
        ProtoLogGroup protoLogGroup = ProtoLogGroup.WM_DEBUG_ANIM;
        boolean isEnabled = ProtoLogImpl_54989576.isEnabled(protoLogGroup, LogLevel.VERBOSE);
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled;
        if (isEnabled) {
            AnimationAdapter animationAdapter = this.mSurfaceAnimator.mAnimation;
            StringWriter stringWriter = new StringWriter();
            if (animationAdapter != null) {
                animationAdapter.dump(new PrintWriter(stringWriter), "");
            }
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        protoLogGroup,
                        -1007526574020149845L,
                        252,
                        null,
                        String.valueOf(this),
                        Boolean.valueOf(this.mAnimatingExit),
                        Boolean.valueOf(this.mRemoveOnExit),
                        Boolean.valueOf(isAnimating()),
                        String.valueOf(stringWriter));
            }
        }
        if (!this.mChildren.isEmpty()) {
            ArrayList arrayList = new ArrayList(this.mChildren);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((WindowState) arrayList.get(size)).onExitAnimationDone();
            }
        }
        WindowStateAnimator windowStateAnimator = this.mWinAnimator;
        if (windowStateAnimator.mEnteringAnimation) {
            windowStateAnimator.mEnteringAnimation = false;
            this.mWmService.requestTraversal();
            if (this.mActivityRecord == null) {
                try {
                    this.mClient.dispatchWindowShown();
                } catch (RemoteException unused) {
                }
            }
        }
        if (!isAnimating() && isSelfOrAncestorWindowAnimatingExit()) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
                        1738645946553610841L,
                        12,
                        null,
                        String.valueOf(this),
                        Boolean.valueOf(this.mRemoveOnExit));
            }
            this.mDestroying = true;
            boolean hasSurface = this.mWinAnimator.hasSurface();
            this.mWinAnimator.hide(getPendingTransaction(), "onExitAnimationDone");
            ActivityRecord activityRecord = this.mActivityRecord;
            if (activityRecord != null) {
                if (this.mAttrs.type == 1) {
                    activityRecord.destroySurfaces(false);
                } else {
                    destroySurface(false, activityRecord.mAppStopped);
                }
            } else if (hasSurface) {
                this.mWmService.mDestroySurface.add(this);
            }
            this.mAnimatingExit = false;
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_ANIM,
                        -7737516306844862315L,
                        0,
                        null,
                        String.valueOf(this));
            }
            getDisplayContent().mWallpaperController.hideWallpapers(this);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onMovedByResize() {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RESIZE_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_RESIZE,
                    5236278969232209904L,
                    0,
                    null,
                    String.valueOf(this));
        }
        this.mMovedByResize = true;
        super.onMovedByResize();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public final void onParentChanged(
            ConfigurationContainer configurationContainer,
            ConfigurationContainer configurationContainer2) {
        super.onParentChanged(configurationContainer, configurationContainer2);
        this.mDrawnStateEvaluated = false;
        DisplayContent displayContent = getDisplayContent();
        if (displayContent.mMagnificationSpec != null) {
            displayContent.applyMagnificationSpec(
                    displayContent.getPendingTransaction(), displayContent.mMagnificationSpec);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onResize() {
        ArrayList arrayList = this.mWmService.mResizingWindows;
        if (this.mHasSurface && !isGoneForLayout() && !arrayList.contains(this)) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RESIZE_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_RESIZE,
                        1783521309242112490L,
                        0,
                        null,
                        String.valueOf(this));
            }
            arrayList.add(this);
        }
        super.onResize();
    }

    public final void onResizePostDispatched(int i, int i2, boolean z) {
        if (z
                && i >= 0
                && i
                        != this.mLastReportedConfiguration
                                .getMergedConfiguration()
                                .windowConfiguration
                                .getRotation()) {
            this.mOrientationChangeRedrawRequestTime = SystemClock.elapsedRealtime();
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_ORIENTATION,
                        -5755338358883139945L,
                        0,
                        null,
                        String.valueOf(this));
            }
        }
        if (this.mWmService.mAccessibilityController.hasCallbacks()) {
            AccessibilityController accessibilityController =
                    this.mWmService.mAccessibilityController;
            accessibilityController.getClass();
            accessibilityController.onSomeWindowResizedOrMovedWithCallingUid(
                    Binder.getCallingUid(), i2);
        }
    }

    public final boolean onSetAppExiting(boolean z) {
        DisplayContent displayContent = getDisplayContent();
        boolean z2 = false;
        if (!z) {
            this.mPermanentlyHidden = true;
            hide(false, false);
        }
        if (isVisibleNow() && z) {
            this.mWinAnimator.applyAnimationLocked(2, false);
            if (this.mWmService.mAccessibilityController.hasCallbacks()) {
                this.mWmService.mAccessibilityController.onWindowTransition(this, 2);
            }
            if (displayContent != null) {
                displayContent.setLayoutNeeded();
            }
            z2 = true;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z2 |= ((WindowState) this.mChildren.get(size)).onSetAppExiting(z);
        }
        return z2;
    }

    public final void onStartFreezingScreen() {
        this.mAppFreezing = true;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowState) this.mChildren.get(size)).onStartFreezingScreen();
        }
    }

    public final boolean onStopFreezingScreen() {
        boolean z = false;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z |= ((WindowState) this.mChildren.get(size)).onStopFreezingScreen();
        }
        if (!this.mAppFreezing) {
            return z;
        }
        this.mAppFreezing = false;
        if (this.mHasSurface
                && !getOrientationChanging()
                && this.mWmService.mWindowsFreezingScreen != 2) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_ORIENTATION,
                        8812513438749898553L,
                        0,
                        null,
                        String.valueOf(this));
            }
            setOrientationChanging(true);
        }
        this.mLastFreezeDuration = 0;
        setDisplayLayoutNeeded();
        return true;
    }

    public final void openInputChannel(InputChannel inputChannel) {
        if (this.mInputChannel != null) {
            throw new IllegalStateException("Window already has an input channel.");
        }
        InputChannel createInputChannel =
                this.mWmService.mInputManager.createInputChannel(getName());
        this.mInputChannel = createInputChannel;
        IBinder token = createInputChannel.getToken();
        this.mInputChannelToken = token;
        this.mInputWindowHandle.setToken(token);
        this.mWmService.mInputToWindowMap.put(this.mInputChannelToken, this);
        this.mInputChannel.copyTo(inputChannel);
    }

    public final boolean performShowLocked() {
        ActivityRecord activityRecord;
        boolean z;
        boolean z2;
        if (!showToCurrentUser()) {
            clearPolicyVisibilityFlag(2);
            return false;
        }
        int i = this.mWinAnimator.mDrawState;
        if ((i == 4 || i == 3) && (activityRecord = this.mActivityRecord) != null) {
            if (this.mAttrs.type != 3) {
                activityRecord.firstWindowDrawn = true;
                activityRecord.mSplashScreenStyleSolidColor = true;
                BackNavigationController.AnimationHandler animationHandler =
                        activityRecord.mAtmService.mBackNavigationController.mAnimationHandler;
                if (animationHandler.mComposed && !animationHandler.mWaitTransition) {
                    boolean z3 = true;
                    for (int length = animationHandler.mOpenAnimAdaptor.mAdaptors.length - 1;
                            length >= 0;
                            length--) {
                        BackNavigationController.AnimationHandler.BackWindowAnimationAdaptor
                                backWindowAnimationAdaptor =
                                        animationHandler.mOpenAnimAdaptor.mAdaptors[length];
                        if (BackNavigationController.AnimationHandler.isAnimateTarget(
                                activityRecord,
                                backWindowAnimationAdaptor.mTarget,
                                animationHandler.mSwitchType)) {
                            backWindowAnimationAdaptor.mAppWindowDrawn = true;
                        }
                        z3 &= backWindowAnimationAdaptor.mAppWindowDrawn;
                    }
                    if (z3) {
                        animationHandler.mOpenAnimAdaptor.cleanUpWindowlessSurface(true);
                    }
                }
                if (activityRecord.mStartingWindow != null) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STARTING_WINDOW_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
                                -6965298896142649709L,
                                0,
                                "Finish starting %s: first real window is shown, no animation",
                                String.valueOf(this.mToken));
                    }
                    cancelAnimation();
                }
                Task task = activityRecord.task;
                if (task.mSharedStartingData == null) {
                    task = null;
                }
                if (task == null) {
                    if (this.mAttrs.type == 1) {
                        activityRecord.removeStartingWindow();
                    }
                } else if (task.getActivity(new ActivityRecord$$ExternalSyntheticLambda4(1))
                        == null) {
                    WindowState window = task.getWindow(new Task$$ExternalSyntheticLambda0(6));
                    ActivityRecord activityRecord2 = window != null ? window.mActivityRecord : null;
                    if (activityRecord2 != null) {
                        activityRecord2.removeStartingWindow();
                    }
                }
                activityRecord.updateReportedVisibilityLocked();
            } else {
                Task task2 = activityRecord.task;
                if (task2 != null) {
                    activityRecord.mSplashScreenStyleSolidColor = true;
                    if (task2.mHasBeenVisible) {
                        z2 = false;
                    } else {
                        if (activityRecord.inTransition()) {
                            activityRecord.task.setDeferTaskAppear(true);
                        }
                        activityRecord.task.setHasBeenVisible();
                        z2 = true;
                    }
                    z = !z2;
                } else {
                    z = false;
                }
                if (!z
                        && activityRecord.mStartingData != null
                        && !activityRecord.finishing
                        && !activityRecord.mLaunchedFromBubble
                        && activityRecord.mVisibleRequested
                        && !activityRecord.mDisplayContent.mAppTransition.isReady()) {
                    DisplayContent displayContent = activityRecord.mDisplayContent;
                    if (!(displayContent.mAppTransition.mAppTransitionState == 2)
                            && displayContent.isNextTransitionForward()) {
                        activityRecord.mStartingData.mIsTransitionForward = true;
                        if (activityRecord
                                != activityRecord.mDisplayContent.getLastOrientationSource()) {
                            activityRecord.mDisplayContent.updateOrientation(false);
                        }
                        activityRecord.mDisplayContent.executeAppTransition();
                    }
                }
            }
        }
        if (this.mWinAnimator.mDrawState != 3 || !isReadyForDisplay()) {
            return false;
        }
        this.mWmService.enableScreenIfNeededLocked();
        this.mWinAnimator.applyEnterAnimationLocked();
        this.mWinAnimator.mLastAlpha = -1.0f;
        if (ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_ANIM_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_FORCE_DEBUG_ANIM,
                    893587812979915130L,
                    0,
                    "performShowLocked: mDrawState=HAS_DRAWN in %s",
                    String.valueOf(this));
        }
        this.mWinAnimator.mDrawState = 4;
        this.mWmService.scheduleAnimationLocked();
        if (this.mHidden) {
            this.mHidden = false;
            DisplayContent displayContent2 = getDisplayContent();
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                WindowState windowState = (WindowState) this.mChildren.get(size);
                if (windowState.mWinAnimator.mSurfaceController != null) {
                    windowState.performShowLocked();
                    if (displayContent2 != null) {
                        displayContent2.setLayoutNeeded();
                    }
                }
            }
        }
        return true;
    }

    public final void pokeDrawLockLw(long j) {
        if (isVisibleRequestedOrAdding()) {
            if (this.mDrawLock == null) {
                CharSequence windowTag = getWindowTag();
                PowerManager.WakeLock newWakeLock =
                        this.mWmService.mPowerManager.newWakeLock(
                                128, "Window:" + ((Object) windowTag));
                this.mDrawLock = newWakeLock;
                newWakeLock.setReferenceCounted(false);
                this.mDrawLock.setWorkSource(
                        new WorkSource(this.mOwnerUid, this.mAttrs.packageName));
            }
            this.mDrawLock.acquire(j);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0186, code lost:

       if (r2.mTmpWindow != null) goto L93;
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01db, code lost:

       if (r5 == false) goto L122;
    */
    @Override // com.android.server.wm.WindowContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void prepareSurfaces() {
        /*
            Method dump skipped, instructions count: 616
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.wm.WindowState.prepareSurfaces():void");
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean prepareSync() {
        int i;
        int i2;
        if (!((ArrayList) this.mDrawHandlers).isEmpty()) {
            Slog.w(
                    "WindowManager",
                    "prepareSync with mDrawHandlers, " + this + ", " + Debug.getCallers(8));
            if (CoreRune.MW_PIP_SHELL_TRANSITION
                    && this.mTransitionController.isCollecting()
                    && inPinnedWindowingMode()
                    && this.mDrawHandlers.stream()
                            .anyMatch(new WindowState$$ExternalSyntheticLambda4(1))) {
                Slog.d(
                        "WindowManager",
                        "prepareSync: skip, reason=split_to_pip_bounds_change" + this);
                return false;
            }
        }
        if (!super.prepareSync()) {
            return false;
        }
        if (this.mIsWallpaper) {
            return true;
        }
        if (this.mActivityRecord != null
                && this.mViewVisibility != 0
                && (i2 = this.mWinAnimator.mAttrType) != 1
                && i2 != 3) {
            return false;
        }
        if (CoreRune.MW_EMBED_ACTIVITY && isSplitEmbedded() && this.mStartingData == null) {
            Task task = getTask();
            ActivityRecord activityRecord = this.mActivityRecord;
            if (task != null
                    && task.mSharedStartingData != null
                    && activityRecord != null
                    && activityRecord.mStartingData == null) {
                return false;
            }
        }
        this.mSyncState = 1;
        if (this.mPrepareSyncSeqId > 0) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_SYNC_ENGINE_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
                        -5774445199273871848L,
                        0,
                        null,
                        String.valueOf(this));
            }
            dropBufferFrom(this.mSyncTransaction);
        }
        this.mSyncSeqId++;
        BLASTSyncEngine.SyncGroup syncGroup = getSyncGroup();
        if (syncGroup == null) {
            i = 0;
        } else {
            i = this.mSyncMethodOverride;
            if (i == -1) {
                i = syncGroup.mSyncMethod;
            }
        }
        if (i == 1) {
            this.mPrepareSyncSeqId = this.mSyncSeqId;
            this.mRedrawForSyncReported = false;
        } else if (this.mHasSurface && this.mWinAnimator.mDrawState != 1) {
            this.mRedrawForSyncReported = false;
        }
        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
            Slog.d(
                    "WindowManager",
                    "prepareSync <SYNC_STATE_WAITING_FOR_DRAW>, mPrepareSyncSeqId="
                            + this.mPrepareSyncSeqId
                            + ", win="
                            + this);
        }
        return true;
    }

    public final void prepareWindowToDisplayDuringRelayout(boolean z) {
        ActivityRecord activityRecord;
        if ((this.mAttrs.flags & 2097152) != 0
                || ((activityRecord = this.mActivityRecord) != null
                        && activityRecord.canTurnScreenOn())) {
            WindowManagerService windowManagerService = this.mWmService;
            boolean z2 =
                    windowManagerService.mAllowTheaterModeWakeFromLayout
                            || Settings.Global.getInt(
                                            windowManagerService.mContext.getContentResolver(),
                                            "theater_mode_on",
                                            0)
                                    == 0;
            ActivityRecord activityRecord2 = this.mActivityRecord;
            boolean z3 = activityRecord2 == null || activityRecord2.mCurrentLaunchCanTurnScreenOn;
            if (z2
                    && z3
                    && ((this.mWmService.mAtmService.mActiveDreamComponent != null
                                    || !this.mWmService.mPowerManager.isInteractive())
                            && (this.mWmService.mAtmService.mDexController.getDexModeLocked() != 2
                                    || !this.mWmService.mPowerManager.isInteractive()
                                    || !this.mWmService.mPowerManagerInternal.isInternalDisplayOff()
                                    || getDisplayContent() == null
                                    || getDisplayContent().mDisplayId != 2))) {
                this.mWmService.mPowerManager.wakeUp(
                        SystemClock.uptimeMillis(),
                        2,
                        "android.server.wm:SCREEN_ON_FLAG::" + this.mAttrs.packageName);
            }
            ActivityRecord activityRecord3 = this.mActivityRecord;
            if (activityRecord3 != null) {
                activityRecord3.mCurrentLaunchCanTurnScreenOn = false;
            }
        }
        if (z) {
            return;
        }
        if ((this.mAttrs.softInputMode & 240) == 16) {
            this.mLayoutNeeded = true;
        }
        if (isDrawn() && this.mToken.okToAnimate()) {
            this.mWinAnimator.applyEnterAnimationLocked();
        }
    }

    public final boolean providesDisplayDecorInsets() {
        SparseArray sparseArray = this.mInsetsSourceProviders;
        if (sparseArray == null) {
            return false;
        }
        WindowManagerService windowManagerService = this.mWmService;
        int i = windowManagerService.mOverrideConfigTypes | windowManagerService.mConfigTypes;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            if ((((InsetsSourceProvider) this.mInsetsSourceProviders.valueAt(size))
                                    .mSource.getType()
                            & i)
                    != 0) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.wm.InputTarget
    public final boolean receiveFocusFromTapOutside() {
        return canReceiveKeys(true);
    }

    public final int relayoutVisibleWindow(int i) {
        boolean isVisible = isVisible();
        int i2 = i | ((isVisible && isDrawn()) ? 0 : 1);
        if (this.mAnimatingExit) {
            StringBuilder sb = new StringBuilder("relayoutVisibleWindow: ");
            sb.append(this);
            sb.append(" mAnimatingExit=true, mRemoveOnExit=");
            sb.append(this.mRemoveOnExit);
            sb.append(", mDestroying=");
            AnyMotionDetector$$ExternalSyntheticOutline0.m("WindowManager", sb, this.mDestroying);
            this.mAnimatingExit = false;
            if (isAnimating()) {
                cancelAnimation();
            }
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_ANIM,
                        -5202247309108694583L,
                        0,
                        null,
                        String.valueOf(this));
            }
        }
        if (this.mDestroying) {
            this.mDestroying = false;
            this.mWmService.mDestroySurface.remove(this);
        }
        if (!isVisible) {
            this.mWinAnimator.mEnterAnimationPending = true;
        }
        int i3 = getDisplayContent().mDisplayRotation.mRotation;
        this.mWinAnimator.mEnteringAnimation = true;
        Trace.traceBegin(32L, "prepareToDisplay");
        try {
            prepareWindowToDisplayDuringRelayout(isVisible);
            return i2;
        } finally {
            Trace.traceEnd(32L);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void removeIfPossible() {
        boolean z;
        ActivityRecord activityRecord;
        ActivityRecord activityRecord2;
        this.mWindowRemovalAllowed = true;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled;
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
                    3927343382258792268L,
                    0,
                    null,
                    String.valueOf(this),
                    String.valueOf(Debug.getCallers(5)));
        }
        boolean z2 = this.mStartingData != null;
        if (z2) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STARTING_WINDOW_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
                        -4831815184899821371L,
                        0,
                        "Starting window removed %s",
                        String.valueOf(this));
            }
            ActivityRecord activityRecord3 = this.mActivityRecord;
            if (activityRecord3 != null) {
                activityRecord3.forAllWindows(
                        (ToBooleanFunction) new WindowState$$ExternalSyntheticLambda6(), true);
            }
            this.mTransitionController.mTransitionTracer.logRemovingStartingWindow(
                    this.mStartingData);
        } else if (this.mAttrs.type == 1 && isSelfAnimating(0, 128)) {
            cancelAnimation();
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_FOCUS,
                    -5803097884846965819L,
                    1,
                    null,
                    Long.valueOf(System.identityHashCode(this.mClient.asBinder())),
                    String.valueOf(this.mWinAnimator.mSurfaceController),
                    String.valueOf(Debug.getCallers(5)));
        }
        DisplayContent displayContent = getDisplayContent();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            disposeInputChannel();
            this.mOnBackInvokedCallbackInfo = null;
            if (ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_APP_TRANSITIONS_enabled[1]) {
                String valueOf = String.valueOf(this);
                String valueOf2 = String.valueOf(this.mWinAnimator.mSurfaceController);
                boolean z3 = this.mAnimatingExit;
                boolean z4 = this.mRemoveOnExit;
                boolean z5 = this.mHasSurface;
                boolean shown = this.mWinAnimator.getShown();
                boolean isAnimating = isAnimating(3);
                ActivityRecord activityRecord4 = this.mActivityRecord;
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_FORCE_DEBUG_APP_TRANSITIONS,
                        6527265605379240657L,
                        262128,
                        "Remove %s: mSurfaceController=%s mAnimatingExit=%b mRemoveOnExit=%b"
                            + " mHasSurface=%b surfaceShowing=%b animating=%b app-animation=%b"
                            + " mDisplayFrozen=%b callers=%s",
                        valueOf,
                        valueOf2,
                        Boolean.valueOf(z3),
                        Boolean.valueOf(z4),
                        Boolean.valueOf(z5),
                        Boolean.valueOf(shown),
                        Boolean.valueOf(isAnimating),
                        Boolean.valueOf(activityRecord4 != null && activityRecord4.isAnimating(3)),
                        Boolean.valueOf(this.mWmService.mDisplayFrozen),
                        String.valueOf(Debug.getCallers(6)));
            }
            adjustImeParamsForDex(false);
            if (this.mHasSurface && this.mToken.okToAnimate()) {
                z = isVisible();
                boolean z6 =
                        !displayContent.inTransition()
                                && ((activityRecord2 = this.mActivityRecord) == null
                                        || !activityRecord2.isRelaunching());
                boolean[] zArr2 = ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled;
                if (z && isDisplayed()) {
                    int i = !z2 ? 2 : 5;
                    if (z6 && this.mWinAnimator.applyAnimationLocked(i, false)) {
                        if (zArr2[1]) {
                            ProtoLogImpl_54989576.v(
                                    ProtoLogGroup.WM_DEBUG_ANIM,
                                    7789778354950913237L,
                                    0,
                                    null,
                                    String.valueOf(this));
                        }
                        if (z2 && this.mSurfaceAnimator.hasLeash()) {
                            getPendingTransaction()
                                    .setLayer(this.mSurfaceAnimator.mLeash, Integer.MAX_VALUE);
                        }
                        this.mAnimatingExit = true;
                        setDisplayLayoutNeeded();
                        this.mWmService.requestTraversal();
                    }
                    if (this.mWmService.mAccessibilityController.hasCallbacks()) {
                        this.mWmService.mAccessibilityController.onWindowTransition(this, i);
                    }
                }
                boolean z7 = z6 && (this.mAnimatingExit || isAnimationRunningSelfOrParent());
                if (z7
                        && getDisplayContent() != null
                        && getDisplayContent().isAppCastingDisplay()
                        && !this.mAnimatingExit
                        && !isAnimating(0, 16)) {
                    z7 = false;
                }
                boolean z8 =
                        z2
                                && (activityRecord = this.mActivityRecord) != null
                                && activityRecord.mChildren.size() == 1
                                && activityRecord.mChildren.get(0) == this;
                if (this.mWinAnimator.getShown() && !z8 && z7) {
                    this.mAnimatingExit = true;
                    if (zArr[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
                                -4143841388126586338L,
                                0,
                                null,
                                String.valueOf(this));
                    }
                    if (zArr2[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_ANIM,
                                4419190702135590390L,
                                0,
                                null,
                                String.valueOf(this));
                    }
                    this.mRemoveOnExit = true;
                    setDisplayLayoutNeeded();
                    getDisplayContent().mDisplayPolicy.removeWindowLw(this);
                    boolean updateFocusedWindowLocked =
                            this.mWmService.updateFocusedWindowLocked(3, false);
                    this.mWmService.mWindowPlacerLocked.performSurfacePlacement(false);
                    if (updateFocusedWindowLocked) {
                        getDisplayContent().mInputMonitor.updateInputWindowsLw(false);
                    }
                    ActivityRecord activityRecord5 = this.mActivityRecord;
                    if (activityRecord5 != null) {
                        activityRecord5.updateReportedVisibilityLocked();
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return;
                }
            } else {
                z = false;
            }
            boolean providesDisplayDecorInsets = providesDisplayDecorInsets();
            removeImmediately();
            boolean z9 = z && displayContent.updateOrientation(false);
            if (providesDisplayDecorInsets
                    && ((z9 = z9 | displayContent.mDisplayPolicy.updateDecorInsetsInfo()))) {
                this.mWmService
                                .mAtmService
                                .mMultiTaskingAppCompatController
                                .mSizeCompatModePolicy
                                .mAvoidAppCompatDisplayInsets =
                        true;
            }
            if (z9) {
                displayContent.sendNewConfiguration();
                this.mWmService
                                .mAtmService
                                .mMultiTaskingAppCompatController
                                .mSizeCompatModePolicy
                                .mAvoidAppCompatDisplayInsets =
                        false;
            }
            this.mWmService.updateFocusedWindowLocked(isFocused() ? 4 : 0, true);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void removeImmediately() {
        ActivityRecord activityRecord;
        boolean z = true;
        if (this.mRemoved) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
                        1351053513466395411L,
                        0,
                        null,
                        String.valueOf(this));
                return;
            }
            return;
        }
        this.mRemoved = true;
        this.mWinAnimator.destroySurfaceLocked(getSyncTransaction());
        if (!((ArrayList) this.mDrawHandlers).isEmpty()) {
            this.mWmService.mH.removeMessages(64, this);
        }
        super.removeImmediately();
        boolean z2 = false;
        if (isImeOverlayLayeringTarget()) {
            WindowManagerService windowManagerService = this.mWmService;
            IBinder asBinder = this.mClient.asBinder();
            int i = this.mAttrs.type;
            if (windowManagerService.mImeTargetChangeListener != null) {
                windowManagerService.mH.post(
                        new WindowManagerService$$ExternalSyntheticLambda21(
                                windowManagerService, asBinder, i, z2, z));
            }
        }
        DisplayContent displayContent = getDisplayContent();
        if (isImeLayeringTarget()) {
            displayContent.removeImeSurfaceByTarget(this);
            displayContent.setImeLayeringTarget(null);
            displayContent.computeImeTarget(true);
        }
        if (displayContent.mImeInputTarget == this
                && ((activityRecord = this.mActivityRecord) == null
                        || !activityRecord.isRelaunching()
                        || (getDisplayId() == 0
                                && (this.mLastReportedConfiguration
                                                        .getMergedConfiguration()
                                                        .isDexMode()
                                                != isDexMode()
                                        || this.mWmService.mAtmService.mDexController
                                                .shouldShowDexImeInDefaultDisplayLocked())))) {
            this.mWmService.dispatchImeInputTargetVisibilityChanged(
                    this.mClient.asBinder(), false, true);
            displayContent.updateImeInputAndControlTarget(null);
        }
        int i2 = this.mAttrs.type;
        if (i2 == 2037 || i2 == 2030) {
            this.mWmService.mDisplayManagerInternal.onPresentation(
                    displayContent.mDisplay.getDisplayId(), false);
        }
        displayContent.mDisplayPolicy.removeWindowLw(this);
        FreeformController freeformController = this.mWmService.mAtmService.mFreeformController;
        if (freeformController.mForceHiddenFreeformContainers.contains(this)) {
            freeformController.mForceHiddenFreeformContainers.remove(this);
        }
        if (syncNextBuffer()) {
            Slog.w(
                    "WindowManager",
                    "removeImmediately: Call immediatelyNotifyBlastSync, w=" + this);
            finishDrawing(null, Integer.MAX_VALUE);
            this.mWmService.mH.removeMessages(64, this);
        }
        disposeInputChannel();
        this.mOnBackInvokedCallbackInfo = null;
        Session session = this.mSession;
        session.mAddedWindows.remove(this);
        if (session.mAddedWindows.isEmpty()) {
            session.killSessionLocked();
        }
        WindowManagerService windowManagerService2 = this.mWmService;
        windowManagerService2.getClass();
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
                    5265273548711408921L,
                    0,
                    null,
                    String.valueOf(this));
        }
        IBinder asBinder2 = this.mClient.asBinder();
        windowManagerService2.mWindowMap.remove(asBinder2);
        if (android.view.flags.Flags.sensitiveContentAppProtection()) {
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService2.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (((ArrayList) windowManagerService2.mOnWindowRemovedListeners).isEmpty()) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        WindowManagerInternal.OnWindowRemovedListener[] onWindowRemovedListenerArr =
                                new WindowManagerInternal.OnWindowRemovedListener
                                        [((ArrayList)
                                                        windowManagerService2
                                                                .mOnWindowRemovedListeners)
                                                .size()];
                        ((ArrayList) windowManagerService2.mOnWindowRemovedListeners)
                                .toArray(onWindowRemovedListenerArr);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        windowManagerService2.mH.post(
                                new WindowManagerService$$ExternalSyntheticLambda3(
                                        1, onWindowRemovedListenerArr, asBinder2));
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
        DisplayContent displayContent2 = getDisplayContent();
        displayContent2.mDisplayRotation.markForSeamlessRotation(this, false);
        int i3 = this.mAppOp;
        if (i3 != -1 && this.mAppOpVisibility) {
            this.mWmService.mAppOps.finishOp(
                    i3, this.mOwnerUid, this.mAttrs.packageName, (String) null);
        }
        if (displayContent2.mCurrentFocus == null) {
            displayContent2.mWinRemovedSinceNullFocus.add(this);
        }
        EmbeddedWindowController embeddedWindowController =
                windowManagerService2.mEmbeddedWindowController;
        for (int size = embeddedWindowController.mWindows.size() - 1; size >= 0; size--) {
            EmbeddedWindowController.EmbeddedWindow embeddedWindow =
                    (EmbeddedWindowController.EmbeddedWindow)
                            embeddedWindowController.mWindows.valueAt(size);
            if (embeddedWindow.mHostWindowState == this) {
                ((EmbeddedWindowController.EmbeddedWindow)
                                embeddedWindowController.mWindows.removeAt(size))
                        .onRemoved();
                embeddedWindowController.mWindowsByInputTransferToken.remove(
                        embeddedWindow.mInputTransferToken);
                embeddedWindowController.mWindowsByWindowToken.remove(embeddedWindow.mClient);
            }
        }
        windowManagerService2.mResizingWindows.remove(this);
        windowManagerService2.updateNonSystemOverlayWindowsVisibilityIfNeeded(this, false);
        windowManagerService2.mWindowsChanged = true;
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_MOVEMENT_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_MOVEMENT,
                    -3847568084407666790L,
                    0,
                    null,
                    String.valueOf(this));
        }
        DisplayContent displayContent3 = getDisplayContent();
        if (displayContent3.mInputMethodWindow == this) {
            displayContent3.setInputMethodWindowLocked(null);
        }
        WindowToken windowToken = this.mToken;
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
                    1419572818243106725L,
                    0,
                    null,
                    String.valueOf(this),
                    String.valueOf(windowToken));
        }
        if (windowToken.mChildren.isEmpty() && !windowToken.mPersistOnEmpty) {
            windowToken.removeImmediately();
        }
        ActivityRecord activityRecord2 = this.mActivityRecord;
        if (activityRecord2 != null) {
            activityRecord2.postWindowRemoveStartingWindowCleanup(this);
        }
        if (this.mAttrs.type == 2013) {
            displayContent2.mWallpaperController.mLastWallpaperTimeoutTime = 0L;
            displayContent2.pendingLayoutChanges |= 4;
        } else if (displayContent2.mWallpaperController.isWallpaperTarget(this)) {
            displayContent2.pendingLayoutChanges |= 4;
        }
        if (!windowManagerService2.mWindowPlacerLocked.mInLayout) {
            displayContent2.assignWindowLayers(true);
            windowManagerService2.mWindowPlacerLocked.performSurfacePlacement(false);
            ActivityRecord activityRecord3 = this.mActivityRecord;
            if (activityRecord3 != null) {
                activityRecord3.updateReportedVisibilityLocked();
            }
        }
        displayContent2.mInputMonitor.updateInputWindowsLw(true);
        consumeInsetsChange();
    }

    @Override // com.android.server.wm.WindowContainer
    public final void reparentSurfaceControl(
            SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (isStartingWindowAssociatedToTask()) {
            return;
        }
        super.reparentSurfaceControl(transaction, surfaceControl);
    }

    public final void reportFocusChangedSerialized(boolean z) {
        RemoteCallbackList remoteCallbackList = this.mFocusCallbacks;
        if (remoteCallbackList != null) {
            int beginBroadcast = remoteCallbackList.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                IWindowFocusObserver broadcastItem = this.mFocusCallbacks.getBroadcastItem(i);
                if (z) {
                    try {
                        broadcastItem.focusGained(this.mWindowId.asBinder());
                    } catch (RemoteException unused) {
                    }
                } else {
                    broadcastItem.focusLost(this.mWindowId.asBinder());
                }
            }
            this.mFocusCallbacks.finishBroadcast();
        }
    }

    public final void requestUpdateWallpaperIfNeeded() {
        DisplayContent displayContent = getDisplayContent();
        if (displayContent != null
                && ((this.mIsWallpaper && !this.mLastConfigReportedToClient) || hasWallpaper())) {
            displayContent.pendingLayoutChanges |= 4;
            displayContent.setLayoutNeeded();
            this.mWmService.mWindowPlacerLocked.requestTraversal();
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowState) this.mChildren.get(size)).requestUpdateWallpaperIfNeeded();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void resetDragResizingChangeReported() {
        this.mDragResizingChangeReported = false;
        super.resetDragResizingChangeReported();
    }

    @Override // com.android.server.wm.WindowContainer
    public final void sendAppVisibilityToClients() {
        super.sendAppVisibilityToClients();
        boolean z = this.mToken.mClientVisible;
        if (this.mAttrs.type != 3 || z) {
            try {
                Slog.v(
                        "WindowManager",
                        "Setting visibility of "
                                + this
                                + ": "
                                + z
                                + ", caller="
                                + Debug.getCallers(5));
                this.mClient.dispatchAppVisibility(z);
            } catch (RemoteException e) {
                Slog.w("WindowManager", "Exception thrown during dispatchAppVisibility " + this, e);
                int uidForPid = Process.getUidForPid(this.mSession.mPid);
                Session session = this.mSession;
                if (uidForPid == session.mUid) {
                    Process.killProcess(session.mPid);
                }
            }
        }
    }

    public final void setAppOpVisibilityLw(boolean z) {
        if (this.mAppOpVisibility != z) {
            this.mAppOpVisibility = z;
            if (z) {
                show(true);
            } else {
                hide(true, true);
            }
        }
    }

    public final void setDisplayLayoutNeeded() {
        DisplayContent displayContent = getDisplayContent();
        if (displayContent != null) {
            displayContent.setLayoutNeeded();
        }
    }

    public final void setForceHideNonSystemOverlayWindowIfNeeded(boolean z) {
        WindowState windowState;
        WindowState windowState2 = this;
        loop0:
        while (true) {
            windowState = windowState2;
            while (windowState2 != null && windowState2.mIsChildWindow) {
                windowState2 = windowState2.getParentWindow();
                if (windowState2 != null) {
                    break;
                }
            }
        }
        int i = windowState.mAttrs.type;
        if (this.mSession.mCanAddInternalSystemWindow) {
            return;
        }
        if (WindowManager.LayoutParams.isSystemAlertWindowType(i) || i == 2005) {
            if (this.mIgnoreHideNonSystemOverlayWindow) {
                Slog.d("WindowManager", "Hide non system overlay window ignored app - " + this);
                return;
            }
            if (i == 2038
                    && this.mAttrs.isSystemApplicationOverlay()
                    && this.mSession.mCanCreateSystemApplicationOverlay) {
                return;
            }
            if (CoreRune.BAIDU_CARLIFE && getDisplayContent().isCarLifeDisplay()) {
                Slog.d("WindowManager", "do not Hide non system overlay window in CarLife display");
                return;
            }
            if (this.mForceHideNonSystemOverlayWindow == z) {
                return;
            }
            this.mForceHideNonSystemOverlayWindow = z;
            if (z) {
                hide(true, true);
            } else {
                show(true);
            }
        }
    }

    public final void setHiddenWhileProfileLockedStateLocked(boolean z) {
        if (this.mOwnerCanAddInternalSystemWindow) {
            return;
        }
        if ((WindowManager.LayoutParams.isSystemAlertWindowType(this.mAttrs.type)
                        || this.mAttrs.type == 2005)
                && this.mHiddenWhileProfileLockedState != z) {
            this.mHiddenWhileProfileLockedState = z;
            if (z) {
                hide(true, true);
            } else {
                show(true);
            }
        }
    }

    public final void setHiddenWhileSuspended(boolean z) {
        if (this.mOwnerCanAddInternalSystemWindow) {
            return;
        }
        if ((WindowManager.LayoutParams.isSystemAlertWindowType(this.mAttrs.type)
                        || this.mAttrs.type == 2005)
                && this.mHiddenWhileSuspended != z) {
            this.mHiddenWhileSuspended = z;
            if (z) {
                hide(true, true);
            } else {
                show(true);
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void setInitialSurfaceControlProperties(SurfaceControl.Builder builder) {
        super.setInitialSurfaceControlProperties(builder);
        if (Flags.surfaceTrustedOverlay() && isWindowTrustedOverlay()) {
            getPendingTransaction().setTrustedOverlay(this.mSurfaceControl, true);
        }
        if (Flags.secureWindowState()) {
            getPendingTransaction().setSecure(this.mSurfaceControl, isSecureLocked());
        }
        getPendingTransaction()
                .setCanOccludePresentation(
                        this.mSurfaceControl, !this.mSession.mCanAddInternalSystemWindow);
    }

    public final boolean setKeepClearAreas(List list, List list2) {
        boolean z = !((ArrayList) this.mKeepClearAreas).equals(list);
        boolean z2 = !((ArrayList) this.mUnrestrictedKeepClearAreas).equals(list2);
        if (!z && !z2) {
            return false;
        }
        if (z) {
            ((ArrayList) this.mKeepClearAreas).clear();
            ((ArrayList) this.mKeepClearAreas).addAll(list);
        }
        if (z2) {
            ((ArrayList) this.mUnrestrictedKeepClearAreas).clear();
            ((ArrayList) this.mUnrestrictedKeepClearAreas).addAll(list2);
        }
        return true;
    }

    public final void setOrientationChanging(boolean z) {
        this.mOrientationChangeTimedOut = false;
        if (this.mOrientationChanging == z) {
            return;
        }
        this.mOrientationChanging = z;
        if (!z) {
            this.mDisplayContent.finishAsyncRotation(this.mToken);
            return;
        }
        this.mLastFreezeDuration = 0;
        if (this.mWmService.mRoot.mOrientationChangeComplete
                && this.mDisplayContent.shouldSyncRotationChange(this)) {
            this.mWmService.mRoot.mOrientationChangeComplete = false;
        }
    }

    public final void setRequestedSize(int i, int i2) {
        if (this.mRequestedWidth == i && this.mRequestedHeight == i2) {
            return;
        }
        this.mLayoutNeeded = true;
        this.mRequestedWidth = i;
        this.mRequestedHeight = i2;
    }

    public void setRequestedVisibleTypes(int i, int i2) {
        int i3 = this.mRequestedVisibleTypes;
        int i4 = (i & i2) | ((~i2) & i3);
        if (i3 != i4) {
            this.mRequestedVisibleTypes = i4;
        }
    }

    public final void setSecureLocked(boolean z) {
        if (ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled[2]) {
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_SHOW_TRANSACTIONS,
                    8269653477215188641L,
                    3,
                    null,
                    Boolean.valueOf(z),
                    String.valueOf(getName()));
        }
        if (!Flags.secureWindowState()) {
            WindowSurfaceController windowSurfaceController = this.mWinAnimator.mSurfaceController;
            if (windowSurfaceController == null
                    || windowSurfaceController.mSurfaceControl == null) {
                return;
            } else {
                getPendingTransaction()
                        .setSecure(this.mWinAnimator.mSurfaceController.mSurfaceControl, z);
            }
        } else if (this.mSurfaceControl == null) {
            return;
        } else {
            getPendingTransaction().setSecure(this.mSurfaceControl, z);
        }
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null) {
            SurfaceControl.Transaction syncTransaction = getSyncTransaction();
            InputTarget inputTarget = displayContent.mImeInputTarget;
            displayContent.mImeWindowsContainer.setCanScreenshot(
                    syncTransaction, inputTarget == null || inputTarget.canScreenshotIme());
        }
        this.mWmService.scheduleAnimationLocked();
    }

    public final void setViewVisibility(int i) {
        this.mViewVisibility = i;
        adjustImeParamsForDex(i == 0);
    }

    public final void setWindowScale(int i, int i2) {
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        if ((layoutParams.flags
                        & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)
                == 0) {
            this.mVScale = 1.0f;
            this.mHScale = 1.0f;
        } else {
            int i3 = layoutParams.width;
            this.mHScale = i3 != i ? i3 / i : 1.0f;
            int i4 = layoutParams.height;
            this.mVScale = i4 != i2 ? i4 / i2 : 1.0f;
        }
    }

    public final boolean shouldCheckTokenVisibleRequested() {
        return (this.mActivityRecord == null
                        && this.mToken.asWallpaperToken() == null
                        && (!CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY
                                || this.mToken.asTransientLaunchOverlay() == null))
                ? false
                : true;
    }

    @Override // com.android.server.wm.InputTarget
    public final boolean shouldControlIme() {
        if (CoreRune.MW_EMBED_ACTIVITY
                && isSplitEmbedded()
                && getTask() != null
                && getTask().inFullscreenWindowingMode()) {
            return true;
        }
        return !inMultiWindowMode();
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean shouldMagnify() {
        if (!this.mDisplayContent.hasOneHandOpSpec()) {
            WindowManager.LayoutParams layoutParams = this.mAttrs;
            int i = layoutParams.type;
            return (i == 2039
                            || i == 2011
                            || i == 2012
                            || i == 2027
                            || i == 2019
                            || i == 2024
                            || i == 1100
                            || (layoutParams.privateFlags & 4194304) != 0)
                    ? false
                    : true;
        }
        WindowManager.LayoutParams layoutParams2 = this.mAttrs;
        int i2 = layoutParams2.type;
        if (i2 == 2015 || i2 == 2024) {
            if ((layoutParams2.privateFlags & 1048576) != 0) {
                return false;
            }
        } else if (i2 == 2619
                || i2 == 2600
                || (i2 == 2601 && (layoutParams2.privateFlags & 4194304) != 0)) {
            return false;
        }
        return true;
    }

    public final boolean shouldSendRedrawForSync() {
        if (this.mRedrawForSyncReported) {
            return false;
        }
        if (!this.mInRelayout
                || (this.mPrepareSyncSeqId <= 0
                        && !(this.mViewVisibility == 0 && this.mWinAnimator.mDrawState == 1))) {
            return syncNextBuffer();
        }
        return false;
    }

    public final boolean shouldSyncWithBuffers() {
        int i;
        if (!((ArrayList) this.mDrawHandlers).isEmpty()) {
            return true;
        }
        BLASTSyncEngine.SyncGroup syncGroup = getSyncGroup();
        if (syncGroup == null) {
            i = 0;
        } else {
            i = this.mSyncMethodOverride;
            if (i == -1) {
                i = syncGroup.mSyncMethod;
            }
        }
        return i == 1;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean shouldUpdateSyncOnReparent() {
        return (this.mSyncState == 0 || this.mLastConfigReportedToClient) ? false : true;
    }

    public final boolean show(boolean z) {
        if ((((this.mPolicyVisibility & 1) != 0) && this.mLegacyPolicyVisibilityAfterAnim)
                || !showToCurrentUser()
                || !this.mAppOpVisibility
                || this.mPermanentlyHidden
                || this.mHiddenWhileSuspended
                || this.mHiddenWhileProfileLockedState
                || this.mForceHideNonSystemOverlayWindow) {
            return false;
        }
        if (z
                && (!this.mToken.okToAnimate()
                        || ((this.mPolicyVisibility & 1) != 0 && !isAnimating(3)))) {
            z = false;
        }
        this.mPolicyVisibility |= 1;
        this.mWmService.scheduleAnimationLocked();
        this.mLegacyPolicyVisibilityAfterAnim = true;
        if (z) {
            this.mWinAnimator.applyAnimationLocked(1, true);
        }
        this.mWmService.scheduleAnimationLocked();
        if ((this.mAttrs.flags & 8) == 0) {
            this.mWmService.updateFocusedWindowLocked(0, false);
        }
        return true;
    }

    public final boolean showForAllUsers() {
        int i = this.mAttrs.type;
        if (i != 3 && i != 2024 && i != 2030 && i != 2034 && i != 2037 && i != 2026 && i != 2027) {
            switch (i) {
                case 2000:
                case 2001:
                case 2002:
                    break;
                default:
                    switch (i) {
                        case 2007:
                        case 2008:
                        case 2009:
                            break;
                        default:
                            switch (i) {
                                case 2017:
                                case 2018:
                                case 2019:
                                case 2020:
                                case 2021:
                                case 2022:
                                    break;
                                default:
                                    switch (i) {
                                        case 2039:
                                        case 2040:
                                        case 2041:
                                            break;
                                        default:
                                            this.mWmService.mExt.getClass();
                                            if (i != 2411 && (this.mAttrs.privateFlags & 16) == 0) {
                                                return false;
                                            }
                                            break;
                                    }
                            }
                    }
            }
        }
        return this.mOwnerCanAddInternalSystemWindow;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public final void showInsets(int i, boolean z, ImeTracker.Token token) {
        try {
            ImeTracker.forLogging().onProgress(token, 21);
            this.mClient.showInsets(i, z, token);
        } catch (RemoteException e) {
            Slog.w("WindowManager", "Failed to deliver showInsets", e);
            ImeTracker.forLogging().onFailed(token, 21);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean showToCurrentUser() {
        WindowState windowState;
        ActivityRecord activityRecord;
        WindowState windowState2 = this;
        loop0:
        while (true) {
            windowState = windowState2;
            while (windowState2 != null && windowState2.mIsChildWindow) {
                windowState2 = windowState2.getParentWindow();
                if (windowState2 != null) {
                    break;
                }
            }
        }
        if (windowState.mAttrs.type < 2000
                && (activityRecord = windowState.mActivityRecord) != null
                && activityRecord.mShowForAllUsers) {
            WindowFrames windowFrames = windowState.mWindowFrames;
            Rect rect = windowFrames.mFrame;
            int i = rect.left;
            Rect rect2 = windowFrames.mDisplayFrame;
            if (i <= rect2.left
                    && rect.top <= rect2.top
                    && rect.right >= rect2.right
                    && rect.bottom >= rect2.bottom) {
                return true;
            }
        }
        if (windowState.showForAllUsers()) {
            return true;
        }
        return this.mWmService.mUmInternal.isUserVisible(windowState.mShowUserId);
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean showWallpaper() {
        if (!isVisibleRequested() || inMultiWindowMode()) {
            return false;
        }
        return hasWallpaper();
    }

    public final void startAnimation(Animation animation) {
        if (this.mControllableInsetProvider != null) {
            return;
        }
        DisplayInfo displayInfo = getDisplayInfo();
        animation.initialize(
                this.mWindowFrames.mFrame.width(),
                this.mWindowFrames.mFrame.height(),
                displayInfo.appWidth,
                displayInfo.appHeight);
        animation.restrictDuration(10000L);
        animation.scaleCurrentDuration(this.mWmService.getWindowAnimationScaleLocked());
        Point point = new Point();
        if (Flags.removePrepareSurfaceInPlacement()) {
            Rect rect = this.mWindowFrames.mFrame;
            transformFrameToSurfacePosition(rect.left, rect.top, point);
        } else {
            point.set(this.mSurfacePosition);
        }
        startAnimation(
                this.mActivityRecord != null ? getSyncTransaction() : getPendingTransaction(),
                new LocalAnimationAdapter(
                        new WindowAnimationSpec(
                                animation,
                                point,
                                false,
                                FullScreenMagnificationGestureHandler.MAX_SCALE),
                        this.mWmService.mSurfaceAnimationRunner),
                this.mWinAnimator.mLastHidden,
                16);
        scheduleAnimation();
    }

    public final void subtractTouchExcludeRegionIfNeeded(Region region) {
        if (this.mTapExcludeRegion.isEmpty()) {
            return;
        }
        Region obtain = Region.obtain();
        this.mTmpRect.set(this.mWindowFrames.mFrame);
        this.mTmpRect.offsetTo(0, 0);
        obtain.set(this.mTapExcludeRegion);
        obtain.op(this.mTmpRect, Region.Op.INTERSECT);
        Rect rect = this.mWindowFrames.mFrame;
        obtain.translate(rect.left, rect.top);
        if (!obtain.isEmpty()) {
            region.op(obtain, Region.Op.DIFFERENCE);
        }
        obtain.recycle();
    }

    @Override // com.android.server.wm.WindowContainer
    public final void switchUser(int i) {
        super.switchUser(i);
        if (!showToCurrentUser()) {
            clearPolicyVisibilityFlag(2);
        } else {
            this.mPolicyVisibility |= 2;
            this.mWmService.scheduleAnimationLocked();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean syncNextBuffer() {
        return super.syncNextBuffer() || this.mDrawHandlers.size() != 0;
    }

    public final String toString() {
        CharSequence windowTag = getWindowTag();
        if (this.mStringNameCache == null
                || this.mLastTitle != windowTag
                || this.mWasExiting != this.mAnimatingExit) {
            this.mLastTitle = windowTag;
            this.mWasExiting = this.mAnimatingExit;
            StringBuilder sb = new StringBuilder("Window{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" u");
            sb.append(this.mShowUserId);
            sb.append(" ");
            sb.append((Object) this.mLastTitle);
            sb.append(this.mAnimatingExit ? " EXITING}" : "}");
            this.mStringNameCache = sb.toString();
        }
        return this.mStringNameCache;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void transformFrameToSurfacePosition(int i, int i2, Point point) {
        point.set(i, i2);
        WindowContainer parent = getParent();
        if (this.mIsChildWindow) {
            WindowState parentWindow = getParentWindow();
            Rect rect = parentWindow.mWindowFrames.mFrame;
            point.offset(-rect.left, -rect.top);
            float f = this.mInvGlobalScale;
            if (f != 1.0f) {
                point.x = (int) ((point.x * f) + 0.5f);
                point.y = (int) ((point.y * f) + 0.5f);
            }
            transformSurfaceInsetsPosition(parentWindow.mAttrs.surfaceInsets, this.mTmpPoint);
            Point point2 = this.mTmpPoint;
            point.offset(point2.x, point2.y);
        } else if (parent != null) {
            Rect bounds =
                    isStartingWindowAssociatedToTask()
                            ? this.mStartingData.mAssociatedTask.getBounds()
                            : parent.getBounds();
            point.offset(-bounds.left, -bounds.top);
        }
        transformSurfaceInsetsPosition(this.mAttrs.surfaceInsets, this.mTmpPoint);
        Point point3 = this.mTmpPoint;
        point.offset(-point3.x, -point3.y);
        point.y += this.mSurfaceTranslationY;
    }

    public final void transformSurfaceInsetsPosition(Rect rect, Point point) {
        float f = this.mGlobalScale;
        if (f == 1.0f || this.mIsChildWindow) {
            point.x = rect.left;
            point.y = rect.top;
        } else {
            point.x = (int) ((rect.left * f) + 0.5f);
            point.y = (int) ((rect.top * f) + 0.5f);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void updateAboveInsetsState(
            final InsetsState insetsState, SparseArray sparseArray, final ArraySet arraySet) {
        final SparseArray createMergedSparseArray =
                WindowContainer.createMergedSparseArray(sparseArray, this.mLocalInsetsSources);
        forAllWindows(
                new Consumer() { // from class:
                                 // com.android.server.wm.WindowState$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        InsetsState insetsState2 = insetsState;
                        ArraySet arraySet2 = arraySet;
                        SparseArray sparseArray2 = createMergedSparseArray;
                        WindowState windowState = (WindowState) obj;
                        if (!windowState.mAboveInsetsState.equals(insetsState2)) {
                            windowState.mAboveInsetsState.set(insetsState2);
                            arraySet2.add(windowState);
                        }
                        if (!sparseArray2.contentEquals(windowState.mMergedLocalInsetsSources)) {
                            windowState.mMergedLocalInsetsSources = sparseArray2;
                            arraySet2.add(windowState);
                        }
                        SparseArray sparseArray3 = windowState.mInsetsSourceProviders;
                        if (sparseArray3 != null) {
                            for (int size = sparseArray3.size() - 1; size >= 0; size--) {
                                insetsState2.addSource(
                                        ((InsetsSourceProvider) sparseArray3.valueAt(size))
                                                .mSource);
                            }
                        }
                    }
                },
                true);
    }

    public void updateFrameRateSelectionPriorityIfNeeded() {
        boolean update;
        boolean contains;
        int i;
        RefreshRatePolicy refreshRatePolicy = getDisplayContent().mDisplayPolicy.mRefreshRatePolicy;
        refreshRatePolicy.getClass();
        boolean isFocused = isFocused();
        int preferredModeId = refreshRatePolicy.getPreferredModeId(this);
        int i2 =
                (isFocused || preferredModeId <= 0)
                        ? (isFocused && preferredModeId == 0)
                                ? 1
                                : (!isFocused || preferredModeId <= 0) ? -1 : 0
                        : 2;
        if (this.mFrameRateSelectionPriority != i2) {
            this.mFrameRateSelectionPriority = i2;
            getPendingTransaction()
                    .setFrameRateSelectionPriority(
                            this.mSurfaceControl, this.mFrameRateSelectionPriority);
        }
        int refreshRateSwitchingType =
                refreshRatePolicy.mWmService.mDisplayManagerInternal.getRefreshRateSwitchingType();
        if (refreshRateSwitchingType == 0) {
            update =
                    this.mFrameRateVote.update(
                            0, 0, FullScreenMagnificationGestureHandler.MAX_SCALE);
        } else if (Flags.explicitRefreshRateHints() || !isAnimationRunningSelfOrParent()) {
            if (refreshRateSwitchingType != 3 && (i = this.mAttrs.preferredDisplayModeId) > 0) {
                for (Display.Mode mode : refreshRatePolicy.mDisplayInfo.appsSupportedModes) {
                    if (i == mode.getModeId()) {
                        if (CoreRune.FW_VRR_HRR_CHINA_DELTA && mode.getRefreshRate() >= 119.99f) {
                            if (refreshRatePolicy.mWmService.mAtmService.mExt
                                    .mHighRefreshRateBlockList.contains(this.mAttrs.packageName)) {
                                update =
                                        this.mFrameRateVote.update(
                                                0,
                                                0,
                                                FullScreenMagnificationGestureHandler.MAX_SCALE);
                            }
                        }
                        update = this.mFrameRateVote.update(100, 1, mode.getRefreshRate());
                    }
                }
            }
            WindowManager.LayoutParams layoutParams = this.mAttrs;
            float f = layoutParams.preferredRefreshRate;
            if (f > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                update = this.mFrameRateVote.update(0, 1, f);
            } else {
                if (refreshRateSwitchingType != 3) {
                    String str = layoutParams.packageName;
                    HighRefreshRateDenylist highRefreshRateDenylist =
                            refreshRatePolicy.mHighRefreshRateDenylist;
                    synchronized (highRefreshRateDenylist.mLock) {
                        contains = highRefreshRateDenylist.mDenylistedPackages.contains(str);
                    }
                    if (contains) {
                        update =
                                this.mFrameRateVote.update(
                                        100,
                                        1,
                                        refreshRatePolicy.mLowRefreshRateMode.getRefreshRate());
                    }
                }
                update =
                        this.mFrameRateVote.update(
                                0, 0, FullScreenMagnificationGestureHandler.MAX_SCALE);
            }
        } else {
            update =
                    this.mFrameRateVote.update(
                            0, 0, FullScreenMagnificationGestureHandler.MAX_SCALE);
        }
        if (update) {
            SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
            SurfaceControl surfaceControl = this.mSurfaceControl;
            RefreshRatePolicy.FrameRateVote frameRateVote = this.mFrameRateVote;
            pendingTransaction.setFrameRate(
                    surfaceControl, frameRateVote.mRefreshRate, frameRateVote.mCompatibility, 1);
            if (Flags.explicitRefreshRateHints()) {
                getPendingTransaction()
                        .setFrameRateSelectionStrategy(
                                this.mSurfaceControl, this.mFrameRateVote.mSelectionStrategy);
            }
        }
    }

    public final void updateGlobalScale() {
        if (!hasCompatScale()) {
            this.mCompatScale = 1.0f;
            this.mInvGlobalScale = 1.0f;
            this.mGlobalScale = 1.0f;
        } else {
            float compatScale =
                    (this.mOverrideScale == 1.0f || this.mToken.hasSizeCompatBounds())
                            ? this.mToken.getCompatScale()
                            : 1.0f;
            this.mCompatScale = compatScale;
            float f = compatScale * this.mOverrideScale;
            this.mGlobalScale = f;
            this.mInvGlobalScale = 1.0f / f;
        }
    }

    public final void updateLetterboxDirectionIfNeeded() {
        int i = 0;
        if (getDisplayContent().mInitialDisplayCutout != null) {
            if (this.mIsImWindow) {
                ActivityRecord activity =
                        getDisplayContent()
                                .getActivity(new WindowState$$ExternalSyntheticLambda4(0));
                if (activity != null) {
                    i =
                            activity.mAppCompatController.mAppCompatLetterboxPolicy
                                    .getLetterboxDirection();
                }
            } else if (this.mActivityRecord != null
                    && (!inMultiWindowMode() || inSplitScreenWindowingMode())) {
                i =
                        this.mActivityRecord.mAppCompatController.mAppCompatLetterboxPolicy
                                .getLetterboxDirection();
            }
        }
        if (this.mLetterboxDirection != i) {
            this.mLetterboxDirection = i;
            try {
                this.mClient.dispatchLetterboxDirectionChanged(i);
            } catch (RemoteException e) {
                Slog.w("WindowManager", "Failed to deliver letterbox direction, w=" + this, e);
            }
        }
    }

    public final void updateReportedVisibility(
            UpdateReportedVisibilityResults updateReportedVisibilityResults) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowState) this.mChildren.get(size))
                    .updateReportedVisibility(updateReportedVisibilityResults);
        }
        if (this.mAppFreezing
                || this.mViewVisibility != 0
                || this.mAttrs.type == 3
                || this.mDestroying) {
            return;
        }
        updateReportedVisibilityResults.numInteresting++;
        if (!isDrawn()) {
            if (isAnimating(3)) {
                updateReportedVisibilityResults.nowGone = false;
            }
        } else {
            updateReportedVisibilityResults.numDrawn++;
            if (!isAnimating(3)) {
                updateReportedVisibilityResults.numVisible++;
            }
            updateReportedVisibilityResults.nowGone = false;
        }
    }

    public final void updateSourceFrame(Rect rect) {
        if (hasInsetsSourceProvider() && !this.mGivenInsetsPending) {
            SparseArray insetsSourceProviders = getInsetsSourceProviders();
            for (int size = insetsSourceProviders.size() - 1; size >= 0; size--) {
                ((InsetsSourceProvider) insetsSourceProviders.valueAt(size))
                        .updateSourceFrame(rect);
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void updateSurfacePosition(SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl == null || this.mIsSurfacePositionPaused) {
            return;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord == null || !activityRecord.isConfigurationDispatchPaused()) {
            if ((this.mWmService.mWindowPlacerLocked.mDeferDepth <= 0 && !isGoneForLayout())
                    || this.mSurfacePlacementNeeded) {
                this.mSurfacePlacementNeeded = false;
                Rect rect = this.mWindowFrames.mFrame;
                transformFrameToSurfacePosition(rect.left, rect.top, this.mSurfacePosition);
                if (this.mWallpaperScale != 1.0f) {
                    Rect rect2 = this.mWindowFrames.mParentFrame;
                    Matrix matrix = this.mTmpMatrix;
                    matrix.setTranslate(this.mXOffset, this.mYOffset);
                    float f = this.mWallpaperScale;
                    matrix.postScale(f, f, rect2.exactCenterX(), rect2.exactCenterY());
                    matrix.getValues(this.mTmpMatrixArray);
                    this.mSurfacePosition.offset(
                            Math.round(this.mTmpMatrixArray[2]),
                            Math.round(this.mTmpMatrixArray[5]));
                } else {
                    this.mSurfacePosition.offset(this.mXOffset, this.mYOffset);
                }
                AsyncRotationController asyncRotationController =
                        this.mDisplayContent.getAsyncRotationController();
                if (asyncRotationController != null) {
                    AsyncRotationController.Operation operation =
                            (AsyncRotationController.Operation)
                                    asyncRotationController.mTargetWindowTokens.get(this.mToken);
                    if (operation != null && operation.mAction == 1) {
                        return;
                    }
                }
                if (this.mPendingSeamlessRotate != null
                        || this.mSurfaceAnimator.hasLeash()
                        || this.mLastSurfacePosition.equals(this.mSurfacePosition)) {
                    return;
                }
                WindowFrames windowFrames = this.mWindowFrames;
                boolean z = windowFrames.mFrameSizeChanged || windowFrames.didFrameSizeChange();
                boolean z2 = !this.mLastSurfaceInsets.equals(this.mAttrs.surfaceInsets);
                boolean z3 = z || z2;
                Point point = this.mLastSurfacePosition;
                Point point2 = this.mSurfacePosition;
                point.set(point2.x, point2.y);
                if (z2) {
                    this.mLastSurfaceInsets.set(this.mAttrs.surfaceInsets);
                }
                boolean z4 =
                        z3
                                && this.mWinAnimator.getShown()
                                && !canPlayMoveAnimation()
                                && okToDisplay()
                                && this.mSyncState == 0;
                ActivityRecord activityRecord2 = this.mActivityRecord;
                boolean z5 =
                        activityRecord2 != null
                                && activityRecord2.areBoundsLetterboxed()
                                && activityRecord2
                                        .mAppCompatController
                                        .mAppCompatOverrides
                                        .mAppCompatOrientationOverrides
                                        .mOrientationOverridesState
                                        .mIsRelaunchingAfterRequestedOrientationChanged;
                if (z4 || z5) {
                    applyWithNextDraw(0, this.mSetSurfacePositionConsumer);
                } else {
                    this.mSetSurfacePositionConsumer.accept(transaction);
                }
            }
        }
    }

    public final void updateTrustedOverlay() {
        InputWindowHandleWrapper inputWindowHandleWrapper = this.mInputWindowHandle;
        inputWindowHandleWrapper.mHandle.setTrustedOverlay(
                getPendingTransaction(), this.mSurfaceControl, isWindowTrustedOverlay());
        this.mInputWindowHandle.mChanged = true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean updateUseForceLayoutInUdcCutoutIfNeeded() {
        if (this.mActivityRecord != null) {
            return false;
        }
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        if (layoutParams.packageName != null) {
            throw null;
        }
        if (TextUtils.isEmpty(layoutParams.getTitle().toString())) {
            return false;
        }
        throw null;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void waitForSyncTransactionCommit(ArraySet arraySet) {
        if (!CoreRune.MW_PIP_SHELL_TRANSITION
                || !this.mTransitionController.isCollecting()
                || !inPinnedWindowingMode()
                || !this.mDrawHandlers.stream()
                        .anyMatch(new WindowState$$ExternalSyntheticLambda4(1))) {
            super.waitForSyncTransactionCommit(arraySet);
            return;
        }
        Slog.w(
                "WindowManager",
                "waitForSyncTransactionCommit: skip, split_to_pip_bounds_change" + this);
    }

    public final boolean wouldBeVisibleIfPolicyIgnored() {
        if (!this.mHasSurface
                || isParentWindowHidden()
                || this.mAnimatingExit
                || this.mDestroying) {
            return false;
        }
        return (!CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY
                        || this.mToken.asTransientLaunchOverlay() == null)
                ? this.mToken.asWallpaperToken() == null || this.mToken.isVisible()
                : this.mToken.isVisibleRequested();
    }

    @Override // com.android.server.wm.WindowContainer
    public final void writeIdentifierToProto(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, System.identityHashCode(this));
        protoOutputStream.write(1120986464258L, this.mShowUserId);
        CharSequence windowTag = getWindowTag();
        if (windowTag != null) {
            protoOutputStream.write(1138166333443L, windowTag.toString());
        }
        protoOutputStream.end(start);
    }
}
