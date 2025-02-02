package com.android.server.wm;

import android.R;
import android.app.AppOpsManager;
import android.app.WindowConfiguration;
import android.app.admin.DevicePolicyCache;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.GraphicsProtos;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Debug;
import android.os.IBinder;
import android.os.InputConstants;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.WorkSource;
import android.os.IInstalld;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.MergedConfiguration;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayInfo;
import android.view.IWindow;
import android.view.IWindowFocusObserver;
import android.view.IWindowId;
import android.view.InputChannel;
import android.view.InputWindowHandle;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.SurfaceEffects;
import android.view.SurfaceSession;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewRootImpl;
import android.view.WindowInfo;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.inputmethod.ImeTracker;
import android.window.ClientWindowFrames;
import android.window.OnBackInvokedCallbackInfo;
import com.android.internal.policy.KeyInterceptionInfo;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ToBooleanFunction;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.display.DisplayPowerController2;
import com.android.server.am.ActivityManagerService;
import com.android.server.wm.BLASTSyncEngine;
import com.android.server.wm.Dimmer;
import com.android.server.wm.DisplayContent;
import com.android.server.wm.DisplayPolicy;
import com.android.server.wm.LocalAnimationAdapter;
import com.android.server.wm.RefreshRatePolicy;
import com.android.server.wm.WindowState;
import com.android.server.policy.WindowManagerPolicy;
import com.samsung.android.knox.localservice.RestrictionPolicyInternal;
import com.samsung.android.rune.CoreRune;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class WindowState extends WindowContainer implements WindowManagerPolicy.WindowState, InputTarget {
    public static final int MINIMUM_VISIBLE_WIDTH_IN_DP;
    public static final int RESIZE_HANDLE_WIDTH_IN_DP;
    public static final StringBuilder sTmpSB;
    public static final Comparator sWindowSubLayerComparator;
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
    public final ClientWindowFrames mClientWindowFrames;
    public float mCompatScale;
    public boolean mCompatWindowAnimationByIme;
    public boolean mCompatWindowMovedByIme;
    public final Context mContext;
    public final DeathRecipient mDeathRecipient;
    public boolean mDestroying;
    public int mDisableFlags;
    public boolean mDisableHideSViewOnce;
    public boolean mDragResizing;
    public boolean mDragResizingChangeReported;
    public final List mDrawHandlers;
    public PowerManager.WakeLock mDrawLock;
    public boolean mDrawnStateEvaluated;
    public boolean mDssEnabled;
    public float mDssScale;
    public final List mExclusionRects;
    public RemoteCallbackList mFocusCallbacks;
    public boolean mForceHideNonSystemOverlayWindow;
    public final boolean mForceSeamlesslyRotate;
    public int mFrameRateSelectionPriority;
    public RefreshRatePolicy.FrameRateVote mFrameRateVote;
    public InsetsState mFrozenInsetsState;
    public final Rect mGivenContentInsets;
    public boolean mGivenInsetsPending;
    public final Region mGivenTouchableRegion;
    public final Rect mGivenVisibleInsets;
    public float mGlobalScale;
    public float mHScale;
    public boolean mHasSeamlessOperation;
    public boolean mHasSurface;
    public boolean mHaveFrame;
    public boolean mHidden;
    public boolean mHiddenByViewCover;
    public boolean mHiddenWhileProfileLockedState;
    public boolean mHiddenWhileSuspended;
    public final boolean mIgnoreHideNonSystemOverlayWindow;
    public boolean mImeBlurEffectAppliedForDex;
    public boolean mInRelayout;
    public InputChannel mInputChannel;
    public IBinder mInputChannelToken;
    public final InputWindowHandleWrapper mInputWindowHandle;
    public float mInvGlobalScale;
    public boolean mIsChildWindow;
    public boolean mIsDimming;
    public final boolean mIsFloatingLayer;
    public final boolean mIsImWindow;
    public boolean mIsTspNoteMode;
    public final boolean mIsWallpaper;
    public final List mKeepClearAreas;
    public KeyInterceptionInfo mKeyInterceptionInfo;
    public boolean mKeyguardWallpaperTouchAllowed;
    public int mLastBlurRadius;
    public boolean mLastConfigReportedToClient;
    public Dimmer mLastDimmer;
    public final long[] mLastExclusionLogUptimeMillis;
    public int mLastFreezeDuration;
    public final int[] mLastGrantedExclusionHeight;
    public float mLastHScale;
    public final MergedConfiguration mLastReportedConfiguration;
    public final int[] mLastRequestedExclusionHeight;
    public int mLastRequestedHeight;
    public int mLastRequestedWidth;
    public boolean mLastShownChangedReported;
    public final Rect mLastSurfaceInsets;
    public CharSequence mLastTitle;
    public float mLastVScale;
    public int mLastVisibleLayoutRotation;
    public int mLayer;
    public final boolean mLayoutAttached;
    public boolean mLayoutNeeded;
    public int mLayoutSeq;
    public boolean mLayoutWithIme;
    public boolean mLegacyPolicyVisibilityAfterAnim;
    public int mLetterboxDirection;
    public SparseArray mMergedLocalInsetsSources;
    public boolean mMovedByIme;
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
    public Dimmer mPopOverDimmer;
    public boolean mPopOverDimmerNeeded;
    public PowerManagerWrapper mPowerManagerWrapper;
    public int mPrepareSyncSeqId;
    public boolean mRedrawForSyncReported;
    public boolean mRelayoutCalled;
    public int mRelayoutSeq;
    public boolean mRemoveOnExit;
    public boolean mRemoved;
    public int mRequestedHeight;
    public int mRequestedVisibleTypes;
    public int mRequestedWidth;
    public boolean mResizedWhileGone;
    public RestrictionPolicyInternal mRestrictionPolicy;
    public final Consumer mSeamlessRotationFinishedConsumer;
    public boolean mSeamlesslyRotated;
    public final Session mSession;
    public final Consumer mSetSurfacePositionConsumer;
    public boolean mShouldHideSViewOnce;
    public boolean mShouldScaleWallpaper;
    public final int mShowUserId;
    public StartingData mStartingData;
    public String mStringNameCache;
    public final int mSubLayer;
    public boolean mSurfacePlacementNeeded;
    public final Point mSurfacePosition;
    public int mSurfaceTranslationX;
    public int mSurfaceTranslationY;
    public int mSyncSeqId;
    public final Region mTapExcludeRegion;
    public final Configuration mTempConfiguration;
    public final Rect mTmpDimBoundsRect;
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
    public final WindowProcessController mWpcForDisplayAreaConfigChanges;
    public int mXOffset;
    public int mYOffset;

    public interface PowerManagerWrapper {
        boolean isInteractive();

        void wakeUp(long j, int i, String str);
    }

    @Override // com.android.server.wm.WindowContainer
    public WindowState asWindowState() {
        return this;
    }

    public final int getLegacyBlurRadius(float f) {
        return (int) ((f / 0.4f) * 352.0f);
    }

    @Override // com.android.server.wm.WindowContainer
    public long getProtoFieldId() {
        return 1146756268040L;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public WindowState getWindow() {
        return this;
    }

    @Override // com.android.server.wm.InputTarget
    public WindowState getWindowState() {
        return this;
    }

    @Override // com.android.server.wm.InputTarget
    public void handleTapOutsideFocusOutsideSelf() {
    }

    public final void logPerformShow(String str) {
    }

    static {
        MINIMUM_VISIBLE_WIDTH_IN_DP = CoreRune.MW_CAPTION_SHELL ? 32 : 48;
        RESIZE_HANDLE_WIDTH_IN_DP = CoreRune.MW_SHELL_FREEFORM_TASK_POSITIONER ? 0 : 30;
        sTmpSB = new StringBuilder();
        sWindowSubLayerComparator = new Comparator() { // from class: com.android.server.wm.WindowState.1
            @Override // java.util.Comparator
            public int compare(WindowState windowState, WindowState windowState2) {
                int i = windowState.mSubLayer;
                int i2 = windowState2.mSubLayer;
                if (i >= i2) {
                    return (i != i2 || i2 >= 0) ? 1 : -1;
                }
                return -1;
            }
        };
    }

    public void setCompatWindowMovedByIme(boolean z) {
        if (z == this.mCompatWindowMovedByIme) {
            return;
        }
        this.mCompatWindowMovedByIme = z;
        this.mCompatWindowAnimationByIme = true;
    }

    public class DrawHandler {
        public Consumer mConsumer;
        public int mSeqId;
        public int mType = 0;

        public DrawHandler(int i, Consumer consumer) {
            this.mSeqId = i;
            this.mConsumer = consumer;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(SurfaceControl.Transaction transaction) {
        finishSeamlessRotation(transaction);
        updateSurfacePosition(transaction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl = this.mSurfaceControl;
        if (surfaceControl == null || !surfaceControl.isValid() || this.mSurfaceAnimator.hasLeash()) {
            return;
        }
        SurfaceControl surfaceControl2 = this.mSurfaceControl;
        Point point = this.mSurfacePosition;
        transaction.setPosition(surfaceControl2, point.x, point.y);
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public boolean isRequestedVisible(int i) {
        return isRequestedVisible(i, false);
    }

    public boolean isRequestedVisible(int i, boolean z) {
        if ((WindowInsets.Type.statusBars() & i) == 0 || !PolicyControl.shouldApplyImmersiveStatus(this)) {
            return ((WindowInsets.Type.navigationBars() & i) == 0 || !PolicyControl.shouldApplyImmersiveNavigation(this, z)) && (this.mRequestedVisibleTypes & i) != 0;
        }
        return false;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public int getRequestedVisibleTypes() {
        return this.mRequestedVisibleTypes;
    }

    public void setRequestedVisibleTypes(int i) {
        if (this.mRequestedVisibleTypes != i) {
            this.mRequestedVisibleTypes = i;
        }
    }

    public void setRequestedVisibleTypes(int i, int i2) {
        setRequestedVisibleTypes((i & i2) | (this.mRequestedVisibleTypes & (~i2)));
    }

    public void freezeInsetsState() {
        if (this.mFrozenInsetsState == null) {
            this.mFrozenInsetsState = new InsetsState(getInsetsState(), true);
        }
    }

    public void clearFrozenInsetsState() {
        this.mFrozenInsetsState = null;
    }

    public boolean isReadyToDispatchInsetsState() {
        return (shouldCheckTokenVisibleRequested() ? isVisibleRequested() : isVisible()) && this.mFrozenInsetsState == null;
    }

    public void seamlesslyRotateIfAllowed(SurfaceControl.Transaction transaction, int i, int i2, boolean z) {
        if (!isVisibleNow() || this.mIsWallpaper || this.mToken.hasFixedRotationTransform()) {
            return;
        }
        Task task = getTask();
        if (task == null || !task.inPinnedWindowingMode()) {
            SeamlessRotator seamlessRotator = this.mPendingSeamlessRotate;
            if (seamlessRotator != null) {
                i = seamlessRotator.getOldRotation();
            }
            InsetsSourceProvider insetsSourceProvider = this.mControllableInsetProvider;
            if (insetsSourceProvider == null || insetsSourceProvider.getSource().getType() != WindowInsets.Type.ime()) {
                if (this.mForceSeamlesslyRotate || z) {
                    InsetsSourceProvider insetsSourceProvider2 = this.mControllableInsetProvider;
                    if (insetsSourceProvider2 != null) {
                        insetsSourceProvider2.startSeamlessRotation();
                    }
                    this.mPendingSeamlessRotate = new SeamlessRotator(i, i2, getDisplayInfo(), false);
                    Point point = this.mLastSurfacePosition;
                    Point point2 = this.mSurfacePosition;
                    point.set(point2.x, point2.y);
                    this.mPendingSeamlessRotate.unrotate(transaction, this);
                    getDisplayContent().getDisplayRotation().markForSeamlessRotation(this, true);
                    applyWithNextDraw(this.mSeamlessRotationFinishedConsumer);
                }
            }
        }
    }

    public void cancelSeamlessRotation() {
        finishSeamlessRotation(getPendingTransaction());
    }

    public void finishSeamlessRotation(SurfaceControl.Transaction transaction) {
        SeamlessRotator seamlessRotator = this.mPendingSeamlessRotate;
        if (seamlessRotator == null) {
            return;
        }
        seamlessRotator.finish(transaction, this);
        this.mPendingSeamlessRotate = null;
        getDisplayContent().getDisplayRotation().markForSeamlessRotation(this, false);
        InsetsSourceProvider insetsSourceProvider = this.mControllableInsetProvider;
        if (insetsSourceProvider != null) {
            insetsSourceProvider.finishSeamlessRotation();
        }
    }

    public List getSystemGestureExclusion() {
        return this.mExclusionRects;
    }

    public boolean setSystemGestureExclusion(List list) {
        if (this.mExclusionRects.equals(list)) {
            return false;
        }
        this.mExclusionRects.clear();
        this.mExclusionRects.addAll(list);
        return true;
    }

    public boolean isImplicitlyExcludingAllSystemGestures() {
        ActivityRecord activityRecord;
        return (this.mAttrs.insetsFlags.behavior == 2 && !isRequestedVisible(WindowInsets.Type.navigationBars())) && this.mWmService.mConstants.mSystemGestureExcludedByPreQStickyImmersive && (activityRecord = this.mActivityRecord) != null && activityRecord.mTargetSdk < 29;
    }

    public void setLastExclusionHeights(int i, int i2, int i3) {
        if ((this.mLastGrantedExclusionHeight[i] == i3 && this.mLastRequestedExclusionHeight[i] == i2) ? false : true) {
            if (this.mLastShownChangedReported) {
                logExclusionRestrictions(i);
            }
            this.mLastGrantedExclusionHeight[i] = i3;
            this.mLastRequestedExclusionHeight[i] = i2;
        }
    }

    public void getKeepClearAreas(Collection collection, Collection collection2, Matrix matrix, float[] fArr) {
        collection.addAll(getRectsInScreenSpace(this.mKeepClearAreas, matrix, fArr));
        collection2.addAll(getRectsInScreenSpace(this.mUnrestrictedKeepClearAreas, matrix, fArr));
    }

    public List getRectsInScreenSpace(List list, Matrix matrix, float[] fArr) {
        getTransformationMatrix(fArr, matrix);
        ArrayList arrayList = new ArrayList();
        RectF rectF = new RectF();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            rectF.set((Rect) it.next());
            matrix.mapRect(rectF);
            Rect rect = new Rect();
            rectF.roundOut(rect);
            arrayList.add(rect);
        }
        return arrayList;
    }

    public boolean setKeepClearAreas(List list, List list2) {
        boolean z = !this.mKeepClearAreas.equals(list);
        boolean z2 = !this.mUnrestrictedKeepClearAreas.equals(list2);
        if (!z && !z2) {
            return false;
        }
        if (z) {
            this.mKeepClearAreas.clear();
            this.mKeepClearAreas.addAll(list);
        }
        if (z2) {
            this.mUnrestrictedKeepClearAreas.clear();
            this.mUnrestrictedKeepClearAreas.addAll(list2);
        }
        return true;
    }

    public void setOnBackInvokedCallbackInfo(OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) {
        if (ProtoLogCache.WM_DEBUG_BACK_PREVIEW_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, -228813488, 0, "%s: Setting back callback %s", new Object[]{String.valueOf(this), String.valueOf(onBackInvokedCallbackInfo)});
        }
        this.mOnBackInvokedCallbackInfo = onBackInvokedCallbackInfo;
    }

    public OnBackInvokedCallbackInfo getOnBackInvokedCallbackInfo() {
        return this.mOnBackInvokedCallbackInfo;
    }

    public WindowState(final WindowManagerService windowManagerService, Session session, IWindow iWindow, WindowToken windowToken, WindowState windowState, int i, WindowManager.LayoutParams layoutParams, int i2, int i3, int i4, boolean z) {
        this(windowManagerService, session, iWindow, windowToken, windowState, i, layoutParams, i2, i3, i4, z, new PowerManagerWrapper() { // from class: com.android.server.wm.WindowState.2
            @Override // com.android.server.wm.WindowState.PowerManagerWrapper
            public void wakeUp(long j, int i5, String str) {
                WindowManagerService.this.mPowerManager.wakeUp(j, i5, str);
            }

            @Override // com.android.server.wm.WindowState.PowerManagerWrapper
            public boolean isInteractive() {
                return WindowManagerService.this.mPowerManager.isInteractive();
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x02c2, code lost:
    
        r3 = true;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public WindowState(WindowManagerService windowManagerService, Session session, IWindow iWindow, WindowToken windowToken, WindowState windowState, int i, WindowManager.LayoutParams layoutParams, int i2, int i3, int i4, boolean z, PowerManagerWrapper powerManagerWrapper) {
        super(windowManagerService);
        ?? r3;
        int i5;
        ActivityRecord activityRecord;
        Bundle bundle;
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
        this.mAttrs = layoutParams2;
        this.mPolicyVisibility = 3;
        this.mLegacyPolicyVisibilityAfterAnim = true;
        this.mAppOpVisibility = true;
        this.mHidden = true;
        this.mDragResizingChangeReported = true;
        this.mRedrawForSyncReported = true;
        this.mSyncSeqId = 0;
        this.mPrepareSyncSeqId = 0;
        this.mRelayoutSeq = -1;
        this.mLayoutSeq = -1;
        this.mLastReportedConfiguration = new MergedConfiguration();
        this.mTempConfiguration = new Configuration();
        this.mGivenContentInsets = new Rect();
        this.mGivenVisibleInsets = new Rect();
        this.mGivenTouchableRegion = new Region();
        this.mTouchableInsets = 0;
        this.mGlobalScale = 1.0f;
        this.mInvGlobalScale = 1.0f;
        this.mCompatScale = 1.0f;
        this.mHScale = 1.0f;
        this.mVScale = 1.0f;
        this.mLastHScale = 1.0f;
        this.mLastVScale = 1.0f;
        this.mXOffset = 0;
        this.mYOffset = 0;
        this.mWallpaperScale = 1.0f;
        this.mTmpMatrix = new Matrix();
        this.mTmpMatrixArray = new float[9];
        this.mWindowFrames = new WindowFrames();
        this.mClientWindowFrames = new ClientWindowFrames();
        this.mExclusionRects = new ArrayList();
        this.mKeepClearAreas = new ArrayList();
        this.mUnrestrictedKeepClearAreas = new ArrayList();
        this.mLastRequestedExclusionHeight = new int[]{0, 0};
        this.mLastGrantedExclusionHeight = new int[]{0, 0};
        this.mLastExclusionLogUptimeMillis = new long[]{0, 0};
        this.mWallpaperX = -1.0f;
        this.mWallpaperY = -1.0f;
        this.mWallpaperZoomOut = -1.0f;
        this.mWallpaperXStep = -1.0f;
        this.mWallpaperYStep = -1.0f;
        this.mWallpaperDisplayOffsetX = Integer.MIN_VALUE;
        this.mWallpaperDisplayOffsetY = Integer.MIN_VALUE;
        this.mLastVisibleLayoutRotation = -1;
        this.mHasSurface = false;
        this.mTmpRect = new Rect();
        this.mTmpPoint = new Point();
        this.mTmpRegion = new Region();
        this.mResizedWhileGone = false;
        this.mSeamlesslyRotated = false;
        this.mAboveInsetsState = new InsetsState();
        this.mMergedLocalInsetsSources = null;
        Rect rect = new Rect();
        this.mLastSurfaceInsets = rect;
        this.mSurfacePosition = new Point();
        this.mTapExcludeRegion = new Region();
        this.mIsDimming = false;
        this.mRequestedVisibleTypes = WindowInsets.Type.defaultVisible();
        this.mFrameRateSelectionPriority = -1;
        this.mFrameRateVote = new RefreshRatePolicy.FrameRateVote();
        this.mImeBlurEffectAppliedForDex = false;
        this.mDrawHandlers = new ArrayList();
        this.mSeamlessRotationFinishedConsumer = new Consumer() { // from class: com.android.server.wm.WindowState$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WindowState.this.lambda$new$0((SurfaceControl.Transaction) obj);
            }
        };
        this.mSetSurfacePositionConsumer = new Consumer() { // from class: com.android.server.wm.WindowState$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WindowState.this.lambda$new$1((SurfaceControl.Transaction) obj);
            }
        };
        this.mDssEnabled = false;
        this.mDssScale = 1.0f;
        this.mIsTspNoteMode = false;
        this.mLetterboxDirection = 0;
        this.mChildDimmingDialogs = new ArraySet();
        this.mTmpDimBoundsRect = new Rect();
        this.mShouldHideSViewOnce = false;
        this.mDisableHideSViewOnce = false;
        this.mKeyguardWallpaperTouchAllowed = false;
        this.mTmpTransaction = (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
        this.mSession = session;
        this.mClient = iWindow;
        this.mAppOp = i;
        this.mToken = windowToken;
        this.mActivityRecord = windowToken.asActivityRecord();
        this.mOwnerUid = i3;
        this.mShowUserId = i4;
        this.mOwnerCanAddInternalSystemWindow = z;
        this.mWindowId = new WindowId();
        layoutParams2.copyFrom(layoutParams);
        rect.set(layoutParams2.surfaceInsets);
        this.mViewVisibility = i2;
        WindowManagerService windowManagerService2 = this.mWmService;
        WindowManagerPolicy windowManagerPolicy = windowManagerService2.mPolicy;
        this.mPolicy = windowManagerPolicy;
        Context context = windowManagerService2.mContext;
        this.mContext = context;
        DeathRecipient deathRecipient = new DeathRecipient();
        this.mPowerManagerWrapper = powerManagerWrapper;
        this.mForceSeamlesslyRotate = windowToken.mRoundedCornerOverlay;
        ActivityRecord activityRecord2 = this.mActivityRecord;
        InputWindowHandleWrapper inputWindowHandleWrapper = new InputWindowHandleWrapper(new InputWindowHandle(activityRecord2 != null ? activityRecord2.getInputApplicationHandle(false) : null, getDisplayId()));
        this.mInputWindowHandle = inputWindowHandleWrapper;
        inputWindowHandleWrapper.setFocusable(false);
        inputWindowHandleWrapper.setOwnerPid(session.mPid);
        inputWindowHandleWrapper.setOwnerUid(session.mUid);
        inputWindowHandleWrapper.setName(getName());
        inputWindowHandleWrapper.setPackageName(layoutParams2.packageName);
        inputWindowHandleWrapper.setLayoutParamsType(layoutParams2.type);
        inputWindowHandleWrapper.setTrustedOverlay(shouldWindowHandleBeTrusted(session));
        try {
            try {
                iWindow.asBinder().linkToDeath(deathRecipient, 0);
                this.mDeathRecipient = deathRecipient;
                int i6 = layoutParams2.type;
                if (i6 >= 1000 && i6 <= 1999) {
                    this.mBaseLayer = (windowManagerPolicy.getWindowLayerLw(windowState) * 10000) + 1000;
                    this.mSubLayer = windowManagerPolicy.getSubWindowLayerFromTypeLw(layoutParams.type);
                    this.mIsChildWindow = true;
                    this.mLayoutAttached = layoutParams2.type != 1003;
                    int i7 = windowState.mAttrs.type;
                    this.mIsImWindow = i7 == 2011 || i7 == 2012;
                    this.mIsWallpaper = i7 == 2013;
                } else {
                    this.mBaseLayer = (windowManagerPolicy.getWindowLayerLw(this) * 10000) + 1000;
                    this.mSubLayer = 0;
                    this.mIsChildWindow = false;
                    this.mLayoutAttached = false;
                    int i8 = layoutParams2.type;
                    this.mIsImWindow = i8 == 2011 || i8 == 2012;
                    this.mIsWallpaper = i8 == 2013;
                }
                this.mIsFloatingLayer = this.mIsImWindow || this.mIsWallpaper;
                ActivityRecord activityRecord3 = this.mActivityRecord;
                if (activityRecord3 != null && activityRecord3.mShowForAllUsers) {
                    layoutParams2.flags |= 524288;
                }
                WindowStateAnimator windowStateAnimator = new WindowStateAnimator(this);
                this.mWinAnimator = windowStateAnimator;
                windowStateAnimator.mAlpha = layoutParams.alpha;
                this.mRequestedWidth = -1;
                this.mRequestedHeight = -1;
                this.mLastRequestedWidth = -1;
                this.mLastRequestedHeight = -1;
                this.mLayer = 0;
                this.mOverrideScale = this.mWmService.mAtmService.mCompatModePackages.getCompatScale(layoutParams2.packageName, session.mUid);
                updateGlobalScale();
                boolean z2 = layoutParams2.type == 2038 && !z && this.mWmService.mExt.ignoreHideNoneSystemOverlayWindowAllowed(layoutParams2.packageName);
                if (z2) {
                    z2 = (r3 = this.mActivityRecord) != null ? false : false;
                }
                this.mIgnoreHideNonSystemOverlayWindow = z2;
                if (this.mIsChildWindow) {
                    if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -916108501, 0, (String) null, new Object[]{String.valueOf(this), String.valueOf(windowState)});
                    }
                    windowState.addChild(this, sWindowSubLayerComparator);
                }
                int i9 = session.mPid;
                this.mWpcForDisplayAreaConfigChanges = (i9 == WindowManagerService.MY_PID || i9 < 0) ? null : windowManagerService.mAtmService.getProcessController(i9, session.mUid);
                this.mOriginalLayoutInDisplayCutoutMode = layoutParams.layoutInDisplayCutoutMode;
                if ((layoutParams2.samsungFlags & 8) != 0) {
                    this.mToken.setPortraitWindowToken(true);
                }
                if (WmCoverState.isEnabled() && getDisplayContent().getDisplayPolicy().mExt.mCoverPolicy != null) {
                    try {
                        Bundle bundle2 = context.getPackageManager().getApplicationInfo(layoutParams2.packageName, 128).metaData;
                        i5 = bundle2 != null ? bundle2.getInt("com.sec.android.sdk.cover.MODE", 0) : 0;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                        i5 = 0;
                    }
                    if (i5 == 0 && (activityRecord = this.mActivityRecord) != null && (bundle = activityRecord.info.metaData) != null && this.mAttrs.type == 1) {
                        i5 = bundle.getInt("com.sec.android.sdk.cover.MODE", 0);
                    }
                    if (i5 != 0) {
                        this.mAttrs.coverMode = i5;
                    }
                    if (getCoverMode() == 2) {
                        if (WmCoverState.getInstance().isCoverAppSupported()) {
                            this.mShouldHideSViewOnce = !this.mDisableHideSViewOnce;
                        } else {
                            this.mShouldHideSViewOnce = false;
                            this.mDisableHideSViewOnce = true;
                        }
                    }
                }
                UdcCutoutPolicy.updateUseLayoutInUdcCutoutIfNeeded(this);
            } catch (RemoteException unused) {
                r3 = 0;
                this.mDeathRecipient = null;
                this.mIsChildWindow = r3;
                this.mLayoutAttached = r3;
                this.mIsImWindow = r3;
                this.mIsWallpaper = r3;
                this.mIsFloatingLayer = r3;
                this.mBaseLayer = r3;
                this.mSubLayer = r3;
                this.mWinAnimator = null;
                this.mWpcForDisplayAreaConfigChanges = null;
                this.mIgnoreHideNonSystemOverlayWindow = r3;
                this.mOverrideScale = 1.0f;
            }
        } catch (RemoteException unused2) {
            r3 = 0;
        }
    }

    public boolean shouldWindowHandleBeTrusted(Session session) {
        if (!InputMonitor.isTrustedOverlay(this.mAttrs.type)) {
            WindowManager.LayoutParams layoutParams = this.mAttrs;
            int i = layoutParams.privateFlags;
            if (((536870912 & i) == 0 || !session.mCanAddInternalSystemWindow) && (((i & 8) == 0 || !session.mCanCreateSystemApplicationOverlay) && (layoutParams.samsungFlags & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) == 0)) {
                return false;
            }
        }
        return true;
    }

    public int getTouchOcclusionMode() {
        return (WindowManager.LayoutParams.isSystemAlertWindowType(this.mAttrs.type) || isAnimating(3, -1) || inTransition()) ? 1 : 0;
    }

    public void attach() {
        this.mSession.windowAddedLocked();
    }

    public void updateGlobalScale() {
        if (hasCompatScale()) {
            float compatScale = (this.mOverrideScale == 1.0f || this.mToken.hasSizeCompatBounds()) ? this.mToken.getCompatScale() : 1.0f;
            this.mCompatScale = compatScale;
            float f = compatScale * this.mOverrideScale;
            this.mGlobalScale = f;
            this.mInvGlobalScale = 1.0f / f;
            return;
        }
        this.mCompatScale = 1.0f;
        this.mInvGlobalScale = 1.0f;
        this.mGlobalScale = 1.0f;
    }

    public float getCompatScaleForClient() {
        if (this.mToken.hasSizeCompatBounds()) {
            return 1.0f;
        }
        return this.mCompatScale;
    }

    public boolean hasCompatScale() {
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        if ((layoutParams.privateFlags & 128) != 0) {
            return true;
        }
        if (layoutParams.type == 3) {
            return false;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        return (activityRecord != null && activityRecord.hasSizeCompatBounds()) || this.mOverrideScale != 1.0f;
    }

    public boolean getDrawnStateEvaluated() {
        return this.mDrawnStateEvaluated;
    }

    public void setDrawnStateEvaluated(boolean z) {
        this.mDrawnStateEvaluated = z;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onParentChanged(ConfigurationContainer configurationContainer, ConfigurationContainer configurationContainer2) {
        super.onParentChanged(configurationContainer, configurationContainer2);
        setDrawnStateEvaluated(false);
        getDisplayContent().reapplyMagnificationSpec();
    }

    public int getOwningUid() {
        return this.mOwnerUid;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowState
    public String getOwningPackage() {
        return this.mAttrs.packageName;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowState
    public boolean canAddInternalSystemWindow() {
        return this.mOwnerCanAddInternalSystemWindow;
    }

    public boolean skipLayout() {
        if (CoreRune.FW_FOLD_WALLPAPER_POLICY && this.mToken.isWaitingForChangingFoldedType()) {
            return true;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && activityRecord.mWaitForEnteringPinnedMode;
    }

    public void setFrames(ClientWindowFrames clientWindowFrames, int i, int i2) {
        int i3;
        int i4;
        WindowFrames windowFrames = this.mWindowFrames;
        this.mTmpRect.set(windowFrames.mParentFrame);
        windowFrames.mDisplayFrame.set(clientWindowFrames.displayFrame);
        windowFrames.mParentFrame.set(clientWindowFrames.parentFrame);
        windowFrames.mFrame.set(clientWindowFrames.frame);
        windowFrames.mCompatFrame.set(windowFrames.mFrame);
        if (!applyScaleToCompatFrameIfNeeded(windowFrames.mCompatFrame)) {
            float f = this.mInvGlobalScale;
            if (f != 1.0f) {
                windowFrames.mCompatFrame.scale(f);
            }
        }
        windowFrames.setParentFrameWasClippedByDisplayCutout(clientWindowFrames.isParentFrameClippedByDisplayCutout);
        windowFrames.mRelFrame.set(windowFrames.mFrame);
        WindowContainer parent = getParent();
        if (this.mIsChildWindow) {
            Rect rect = ((WindowState) parent).mWindowFrames.mFrame;
            i4 = rect.left;
            i3 = rect.top;
        } else if (parent != null) {
            Rect bounds = parent.getBounds();
            i4 = bounds.left;
            i3 = bounds.top;
        } else {
            i3 = 0;
            i4 = 0;
        }
        Rect rect2 = windowFrames.mRelFrame;
        Rect rect3 = windowFrames.mFrame;
        rect2.offsetTo(rect3.left - i4, rect3.top - i3);
        if (i != this.mLastRequestedWidth || i2 != this.mLastRequestedHeight || !this.mTmpRect.equals(windowFrames.mParentFrame)) {
            this.mLastRequestedWidth = i;
            this.mLastRequestedHeight = i2;
            windowFrames.setContentChanged(true);
        }
        if (this.mAttrs.type == 2034 && !windowFrames.mFrame.equals(windowFrames.mLastFrame)) {
            this.mMovedByResize = true;
        }
        if (this.mIsWallpaper) {
            Rect rect4 = windowFrames.mLastFrame;
            Rect rect5 = windowFrames.mFrame;
            if (rect4.width() != rect5.width() || rect4.height() != rect5.height()) {
                this.mDisplayContent.mWallpaperController.updateWallpaperOffset(this, false);
            }
        }
        updateSourceFrame(windowFrames.mFrame);
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null && !this.mIsChildWindow) {
            activityRecord.layoutLetterbox(this);
        }
        this.mSurfacePlacementNeeded = true;
        this.mHaveFrame = true;
    }

    public void updateSourceFrame(Rect rect) {
        if (hasInsetsSourceProvider()) {
            if (!this.mGivenInsetsPending || this.mDisplayContent.getDisplayPolicy().mExt.getTaskbarController().isTaskbar(this)) {
                SparseArray insetsSourceProviders = getInsetsSourceProviders();
                for (int size = insetsSourceProviders.size() - 1; size >= 0; size--) {
                    ((InsetsSourceProvider) insetsSourceProviders.valueAt(size)).updateSourceFrame(rect);
                }
            }
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public Rect getBounds() {
        return this.mToken.hasSizeCompatBounds() ? this.mToken.getBounds() : super.getBounds();
    }

    public Rect getFrame() {
        return this.mWindowFrames.mFrame;
    }

    public Rect getRelativeFrame() {
        return this.mWindowFrames.mRelFrame;
    }

    public Rect getDisplayFrame() {
        return this.mWindowFrames.mDisplayFrame;
    }

    public Rect getParentFrame() {
        return this.mWindowFrames.mParentFrame;
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt.WindowStateExt
    public WindowManager.LayoutParams getAttrs() {
        return this.mAttrs;
    }

    public int getDisableFlags() {
        return this.mDisableFlags;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowState
    public int getBaseType() {
        return getTopParentWindow().mAttrs.type;
    }

    public boolean setReportResizeHints() {
        return this.mWindowFrames.setReportResizeHints();
    }

    public void updateResizingWindowIfNeeded() {
        StartingData startingData;
        boolean hasInsetsChanged = this.mWindowFrames.hasInsetsChanged();
        if ((this.mHasSurface && getDisplayContent().mLayoutSeq == this.mLayoutSeq && !isGoneForLayout()) || hasInsetsChanged || (isPopOver() && this.mWindowFrames.mForceReportingResized)) {
            WindowStateAnimator windowStateAnimator = this.mWinAnimator;
            boolean reportResizeHints = setReportResizeHints();
            boolean z = (this.mInRelayout || isLastConfigReportedToClient()) ? false : true;
            boolean z2 = ViewRootImpl.LOCAL_LAYOUT && this.mLayoutAttached && getParentWindow().frameChanged();
            if (reportResizeHints || z || hasInsetsChanged || shouldSendRedrawForSync() || z2) {
                if (ProtoLogCache.WM_DEBUG_RESIZE_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_RESIZE, 2128570903, 4080, (String) null, new Object[]{String.valueOf(this), String.valueOf(this.mWindowFrames.getInsetsChangedInfo()), Boolean.valueOf(z), Boolean.valueOf(hasInsetsChanged), Boolean.valueOf(shouldSendRedrawForSync()), Boolean.valueOf(reportResizeHints)});
                }
                if (hasInsetsChanged) {
                    this.mWindowFrames.setInsetsChanged(false);
                    if (!CoreRune.MW_CAPTION_SHELL_BUG_FIX || this.mWmService.mWindowsInsetsChanged >= 1) {
                        this.mWmService.mWindowsInsetsChanged--;
                    }
                    WindowManagerService windowManagerService = this.mWmService;
                    if (windowManagerService.mWindowsInsetsChanged == 0) {
                        windowManagerService.f1749mH.removeMessages(66);
                    }
                }
                onResizeHandled();
                this.mWmService.makeWindowFreezingScreenIfNeededLocked(this);
                if (((z && (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX || inTransition(2))) || getOrientationChanging()) && isVisibleRequested()) {
                    windowStateAnimator.mDrawState = 1;
                    ActivityRecord activityRecord = this.mActivityRecord;
                    if (activityRecord != null) {
                        activityRecord.clearAllDrawn();
                        if (this.mAttrs.type == 3 && (startingData = this.mActivityRecord.mStartingData) != null) {
                            startingData.mIsDisplayed = false;
                        }
                    } else if (CoreRune.FW_WAIT_TO_HANDLE_RESIZING_FROM_CLIENT && reportResizeHints && shouldWaitToHandleResizing()) {
                        if (this.mWaitToHandleResizing) {
                            dropBufferFrom(getSyncTransaction());
                            forceExecuteDrawHandlers(2);
                        }
                        Slog.d(StartingSurfaceController.TAG, "updateResizingWindowIfNeeded, Start waiting to handle resizing w=" + this);
                        this.mWaitToHandleResizing = true;
                        applyWithNextDraw(new Consumer() { // from class: com.android.server.wm.WindowState$$ExternalSyntheticLambda6
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                WindowState.this.lambda$updateResizingWindowIfNeeded$2((SurfaceControl.Transaction) obj);
                            }
                        }, 2);
                    }
                }
                if (this.mWmService.mResizingWindows.contains(this)) {
                    return;
                }
                if (ProtoLogCache.WM_DEBUG_RESIZE_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_RESIZE, 685047360, 0, (String) null, new Object[]{String.valueOf(this)});
                }
                this.mWmService.mResizingWindows.add(this);
                return;
            }
            if (getOrientationChanging() && isDrawn()) {
                if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 1329340614, 0, (String) null, new Object[]{String.valueOf(this), String.valueOf(windowStateAnimator.mSurfaceController)});
                }
                setOrientationChanging(false);
                this.mLastFreezeDuration = (int) (SystemClock.elapsedRealtime() - this.mWmService.mDisplayFreezeTime);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateResizingWindowIfNeeded$2(SurfaceControl.Transaction transaction) {
        Slog.d(StartingSurfaceController.TAG, "Clear waiting to handle resizing w=" + this + ", caller=" + Debug.getCallers(5));
        this.mWaitToHandleResizing = false;
    }

    public final boolean shouldWaitToHandleResizing() {
        if (this.mActivityRecord != null || this.mToken.asWallpaperToken() != null || this.mToken.asTransientLaunchOverlay() != null || this.mSyncState == 0) {
            return false;
        }
        AsyncRotationController asyncRotationController = getDisplayContent().getAsyncRotationController();
        return (asyncRotationController == null || !asyncRotationController.isTargetToken(this.mToken)) && !this.mInRelayout && isOnScreen();
    }

    public final boolean frameChanged() {
        WindowFrames windowFrames = this.mWindowFrames;
        return !windowFrames.mFrame.equals(windowFrames.mLastFrame);
    }

    public boolean getOrientationChanging() {
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            return false;
        }
        return ((!this.mOrientationChanging && (!isVisible() || getConfiguration().orientation == getLastReportedConfiguration().orientation)) || this.mSeamlesslyRotated || this.mOrientationChangeTimedOut) ? false : true;
    }

    public void setOrientationChanging(boolean z) {
        this.mOrientationChangeTimedOut = false;
        if (this.mOrientationChanging == z) {
            return;
        }
        this.mOrientationChanging = z;
        if (z) {
            this.mLastFreezeDuration = 0;
            if (this.mWmService.mRoot.mOrientationChangeComplete && this.mDisplayContent.shouldSyncRotationChange(this)) {
                this.mWmService.mRoot.mOrientationChangeComplete = false;
                return;
            }
            return;
        }
        this.mDisplayContent.finishAsyncRotation(this.mToken);
    }

    public void orientationChangeTimedOut() {
        this.mOrientationChangeTimedOut = true;
    }

    @Override // com.android.server.wm.WindowContainer
    public DisplayContent getDisplayContent() {
        return this.mToken.getDisplayContent();
    }

    @Override // com.android.server.wm.WindowContainer
    public void onDisplayChanged(DisplayContent displayContent) {
        DisplayContent displayContent2;
        boolean z = (displayContent == null || this.mDisplayContent == null || (!displayContent.isAppCastingDisplay() && !this.mDisplayContent.isAppCastingDisplay())) ? false : true;
        boolean z2 = CoreRune.CARLIFE_NAVBAR && displayContent != null && this.mDisplayContent != null && (displayContent.isCarLifeDisplay() || this.mDisplayContent.isCarLifeDisplay());
        if (displayContent != null && (displayContent2 = this.mDisplayContent) != null && displayContent != displayContent2 && displayContent2.getImeInputTarget() == this) {
            displayContent.updateImeInputAndControlTarget(getImeInputTarget());
            this.mDisplayContent.setImeInputTarget(null);
            if (this.mWmService.mAtmService.mDexController.getDexModeLocked() == 2 && this.mDisplayContent.isDefaultDisplay && displayContent.getDisplayId() == 2) {
                this.mDisplayContent.updateImeControlTarget();
            }
        }
        super.onDisplayChanged(displayContent);
        if (displayContent != null && this.mInputWindowHandle.getDisplayId() != displayContent.getDisplayId()) {
            this.mLayoutSeq = displayContent.mLayoutSeq - 1;
            this.mInputWindowHandle.setDisplayId(displayContent.getDisplayId());
        }
        if (z || (CoreRune.CARLIFE_NAVBAR && z2)) {
            displayContent.getInsetsStateController().updateAboveInsetsState(false);
        }
    }

    public DisplayFrames getDisplayFrames(DisplayFrames displayFrames) {
        DisplayFrames fixedRotationTransformDisplayFrames = this.mToken.getFixedRotationTransformDisplayFrames();
        return fixedRotationTransformDisplayFrames != null ? fixedRotationTransformDisplayFrames : isLayoutNeededInUdcCutout() ? this.mDisplayContent.mUdcCutoutPolicy.mUdcDisplayFrames : displayFrames;
    }

    public DisplayInfo getDisplayInfo() {
        DisplayInfo fixedRotationTransformDisplayInfo = this.mToken.getFixedRotationTransformDisplayInfo();
        return fixedRotationTransformDisplayInfo != null ? fixedRotationTransformDisplayInfo : getDisplayContent().getDisplayInfo();
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public Rect getMaxBounds() {
        Rect fixedRotationTransformMaxBounds = this.mToken.getFixedRotationTransformMaxBounds();
        return fixedRotationTransformMaxBounds != null ? fixedRotationTransformMaxBounds : super.getMaxBounds();
    }

    public InsetsState getInsetsState() {
        return getInsetsState(false);
    }

    public InsetsState getInsetsState(boolean z) {
        InsetsState fixedRotationTransformInsetsState = this.mToken.getFixedRotationTransformInsetsState();
        InsetsPolicy insetsPolicy = getDisplayContent().getInsetsPolicy();
        if (fixedRotationTransformInsetsState != null) {
            if (!CoreRune.CARLIFE_NAVBAR || !this.mDisplayContent.isCarLifeDisplay()) {
                fixedRotationTransformInsetsState = this.mDisplayContent.getDisplayPolicy().mExt.getTaskbarController().adjustInsetsForTaskbar(fixedRotationTransformInsetsState);
            }
            return insetsPolicy.adjustInsetsForWindow(this, fixedRotationTransformInsetsState);
        }
        InsetsState insetsState = this.mFrozenInsetsState;
        if (insetsState == null) {
            insetsState = getMergedInsetsState();
        }
        InsetsState enforceInsetsPolicyForTarget = insetsPolicy.enforceInsetsPolicyForTarget(this.mAttrs, getWindowingMode(), isAlwaysOnTop(), insetsState);
        if (isLayoutNeededInUdcCutout()) {
            this.mDisplayContent.mUdcCutoutPolicy.adjustInsetsForUdc(this, enforceInsetsPolicyForTarget);
        }
        if (!CoreRune.CARLIFE_NAVBAR || !this.mDisplayContent.isCarLifeDisplay()) {
            enforceInsetsPolicyForTarget = this.mDisplayContent.getDisplayPolicy().mExt.getTaskbarController().adjustInsetsForTaskbar(enforceInsetsPolicyForTarget);
        }
        InsetsState adjustNavInsetsForWindowAboveThanIme = adjustNavInsetsForWindowAboveThanIme(enforceInsetsPolicyForTarget);
        if (isPopOver()) {
            adjustNavInsetsForWindowAboveThanIme = adjustInsetsForPopOver(adjustNavInsetsForWindowAboveThanIme);
        }
        return insetsPolicy.adjustInsetsForWindow(this, adjustNavInsetsForWindowAboveThanIme, z);
    }

    public final InsetsState getMergedInsetsState() {
        InsetsState insetsState;
        if (this.mAttrs.receiveInsetsIgnoringZOrder) {
            insetsState = getDisplayContent().getInsetsStateController().getRawInsetsState();
        } else {
            insetsState = this.mAboveInsetsState;
        }
        if (this.mMergedLocalInsetsSources == null) {
            return insetsState;
        }
        InsetsState insetsState2 = new InsetsState(insetsState);
        for (int i = 0; i < this.mMergedLocalInsetsSources.size(); i++) {
            insetsState2.addSource((InsetsSource) this.mMergedLocalInsetsSources.valueAt(i));
        }
        return insetsState2;
    }

    public InsetsState getCompatInsetsState() {
        InsetsState insetsState = getInsetsState();
        if (this.mInvGlobalScale == 1.0f) {
            return insetsState;
        }
        InsetsState insetsState2 = new InsetsState(insetsState, true);
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord == null || !CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT || !activityRecord.mCompatRecord.mCanRotationCompatMode) {
            insetsState2.scale(this.mInvGlobalScale);
        }
        return insetsState2;
    }

    public InsetsState getInsetsStateWithVisibilityOverride() {
        InsetsState insetsState = new InsetsState(getInsetsState(), true);
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
            boolean isRequestedVisible = isRequestedVisible(sourceAt.getType());
            if (sourceAt.isVisible() != isRequestedVisible) {
                sourceAt.setVisible(isRequestedVisible);
            }
        }
        return insetsState;
    }

    @Override // com.android.server.wm.InputTarget
    public int getDisplayId() {
        DisplayContent displayContent = getDisplayContent();
        if (displayContent == null) {
            return -1;
        }
        return displayContent.getDisplayId();
    }

    @Override // com.android.server.wm.InputTarget
    public IWindow getIWindow() {
        return this.mClient;
    }

    @Override // com.android.server.wm.InputTarget
    public int getPid() {
        return this.mSession.mPid;
    }

    public int getUid() {
        return this.mSession.mUid;
    }

    public Task getTask() {
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null) {
            return activityRecord.getTask();
        }
        return null;
    }

    public TaskFragment getTaskFragment() {
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null) {
            return activityRecord.getTaskFragment();
        }
        return null;
    }

    public Task getRootTask() {
        Task task = getTask();
        if (task != null) {
            return task.getRootTask();
        }
        DisplayContent displayContent = getDisplayContent();
        if (this.mAttrs.type < 2000 || displayContent == null) {
            return null;
        }
        return displayContent.getDefaultTaskDisplayArea().getRootHomeTask();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void getVisibleBounds(Rect rect) {
        Task task = getTask();
        boolean z = false;
        boolean z2 = task != null && task.cropWindowsToRootTaskBounds();
        rect.setEmpty();
        this.mTmpRect.setEmpty();
        if (z2) {
            Task rootTask = task.getRootTask();
            if (rootTask != null) {
                rootTask.getDimBounds(this.mTmpRect);
            }
            rect.set(this.mWindowFrames.mFrame);
            InsetsState insetsStateWithVisibilityOverride = getInsetsStateWithVisibilityOverride();
            int i = this.mAttrs.type;
            int activityType = getActivityType();
            WindowManager.LayoutParams layoutParams = this.mAttrs;
            rect.inset(insetsStateWithVisibilityOverride.calculateVisibleInsets(rect, i, activityType, layoutParams.softInputMode, layoutParams.flags));
            if (z) {
                return;
            }
            rect.intersect(this.mTmpRect);
            return;
        }
        z = z2;
        rect.set(this.mWindowFrames.mFrame);
        InsetsState insetsStateWithVisibilityOverride2 = getInsetsStateWithVisibilityOverride();
        int i2 = this.mAttrs.type;
        int activityType2 = getActivityType();
        WindowManager.LayoutParams layoutParams2 = this.mAttrs;
        rect.inset(insetsStateWithVisibilityOverride2.calculateVisibleInsets(rect, i2, activityType2, layoutParams2.softInputMode, layoutParams2.flags));
        if (z) {
        }
    }

    public long getInputDispatchingTimeoutMillis() {
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null) {
            return activityRecord.mInputDispatchingTimeoutMillis;
        }
        return InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
    }

    public boolean hasAppShownWindows() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && (activityRecord.firstWindowDrawn || activityRecord.isStartingWindowDisplayed());
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean hasContentToDisplay() {
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

    public final boolean isVisibleByPolicyOrInsets() {
        InsetsSourceProvider insetsSourceProvider;
        return isVisibleByPolicy() && ((insetsSourceProvider = this.mControllableInsetProvider) == null || insetsSourceProvider.isClientVisible());
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isVisible() {
        return wouldBeVisibleIfPolicyIgnored() && isVisibleByPolicyOrInsets();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isVisibleRequested() {
        boolean z = wouldBeVisibleRequestedIfPolicyIgnored() && isVisibleByPolicyOrInsets();
        return (z && shouldCheckTokenVisibleRequested()) ? this.mToken.isVisibleRequested() : z;
    }

    public boolean shouldCheckTokenVisibleRequested() {
        return (this.mActivityRecord == null && this.mToken.asWallpaperToken() == null && (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY || this.mToken.asTransientLaunchOverlay() == null)) ? false : true;
    }

    public boolean isVisibleByPolicy() {
        return (this.mPolicyVisibility & 3) == 3;
    }

    public boolean providesDisplayDecorInsets() {
        SparseArray sparseArray = this.mInsetsSourceProviders;
        if (sparseArray == null) {
            return false;
        }
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            if ((((InsetsSourceProvider) this.mInsetsSourceProviders.valueAt(size)).getSource().getType() & DisplayPolicy.DecorInsets.CONFIG_TYPES) != 0) {
                return true;
            }
        }
        return false;
    }

    public void clearPolicyVisibilityFlag(int i) {
        this.mPolicyVisibility = (~i) & this.mPolicyVisibility;
        this.mWmService.scheduleAnimationLocked();
    }

    public void setPolicyVisibilityFlag(int i) {
        this.mPolicyVisibility = i | this.mPolicyVisibility;
        this.mWmService.scheduleAnimationLocked();
    }

    public final boolean isLegacyPolicyVisibility() {
        return (this.mPolicyVisibility & 1) != 0;
    }

    public boolean wouldBeVisibleIfPolicyIgnored() {
        if (!this.mHasSurface || isParentWindowHidden() || this.mAnimatingExit || this.mDestroying) {
            return false;
        }
        if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY || this.mToken.asTransientLaunchOverlay() == null) {
            return !(this.mToken.asWallpaperToken() != null) || this.mToken.isVisible();
        }
        return this.mToken.isVisibleRequested();
    }

    public final boolean wouldBeVisibleRequestedIfPolicyIgnored() {
        WindowState parentWindow = getParentWindow();
        if (((parentWindow == null || parentWindow.isVisibleRequested()) ? false : true) || this.mAnimatingExit || this.mDestroying) {
            return false;
        }
        if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY || this.mToken.asTransientLaunchOverlay() == null) {
            return !(this.mToken.asWallpaperToken() != null) || this.mToken.isVisibleRequested();
        }
        return this.mToken.isVisibleRequested();
    }

    public boolean isVisibleNow() {
        return (this.mToken.isVisible() || this.mAttrs.type == 3) && isVisible();
    }

    public boolean isPotentialDragTarget(boolean z) {
        return ((!z && !isVisibleNow()) || this.mRemoved || this.mInputChannel == null || this.mInputWindowHandle == null) ? false : true;
    }

    public boolean isVisibleRequestedOrAdding() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return (this.mHasSurface || (!this.mRelayoutCalled && this.mViewVisibility == 0)) && isVisibleByPolicy() && !isParentWindowHidden() && !((activityRecord != null && !activityRecord.isVisibleRequested()) || this.mAnimatingExit || this.mDestroying);
    }

    public boolean isOnScreen() {
        TransientLaunchOverlayToken asTransientLaunchOverlay;
        if (!this.mHasSurface || this.mDestroying || !isVisibleByPolicy()) {
            return false;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null) {
            return (!isParentWindowHidden() && activityRecord.isVisible()) || isAnimationRunningSelfOrParent();
        }
        WallpaperWindowToken asWallpaperToken = this.mToken.asWallpaperToken();
        if (asWallpaperToken != null) {
            return !isParentWindowHidden() && asWallpaperToken.isVisible();
        }
        if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY || (asTransientLaunchOverlay = this.mToken.asTransientLaunchOverlay()) == null) {
            return !isParentWindowHidden() || isAnimating(3);
        }
        return asTransientLaunchOverlay.isVisibleRequested();
    }

    public boolean isDreamWindow() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && activityRecord.getActivityType() == 5;
    }

    public boolean isSecureLocked() {
        if ((this.mAttrs.flags & IInstalld.FLAG_FORCE) != 0 || isScreenCaptureDisabledByMdm()) {
            return true;
        }
        if (DevicePolicyCache.getInstance() == null) {
            return false;
        }
        return !DevicePolicyCache.getInstance().isScreenCaptureAllowed(this.mShowUserId);
    }

    public final boolean isScreenCaptureDisabledByMdm() {
        try {
            RestrictionPolicyInternal restrictionPolicy = getRestrictionPolicy();
            this.mRestrictionPolicy = restrictionPolicy;
            if (restrictionPolicy != null && !restrictionPolicy.isScreenCaptureEnabledEx(this.mShowUserId, false)) {
                Slog.d(StartingSurfaceController.TAG, "Screen Capture is disabled by mdm for user(" + this.mShowUserId + ")");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean mightAffectAllDrawn() {
        int i = this.mWinAnimator.mAttrType;
        return ((!isOnScreen() && !(i == 1 || i == 4)) || this.mAnimatingExit || this.mDestroying) ? false : true;
    }

    public boolean isInteresting() {
        RecentsAnimationController recentsAnimationController = this.mWmService.getRecentsAnimationController();
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && !(activityRecord.isFreezingScreen() && this.mAppFreezing) && this.mViewVisibility == 0 && (recentsAnimationController == null || recentsAnimationController.isInterestingForAllDrawn(this));
    }

    public void logReadyForDisplay(String str) {
        String str2 = str + " logReadyForDisplay win=" + this;
        if (this.mToken.waitingToShow && getDisplayContent().mAppTransition.isTransitionSet()) {
            Slog.d(StartingSurfaceController.TAG, str2 + " mToken.waitingToShow:true isTransitionSet:true");
            return;
        }
        if (!isParentWindowHidden() && this.mViewVisibility == 0 && this.mToken.isVisible()) {
            Slog.d(StartingSurfaceController.TAG, str2 + " isParentWindowHidden:false mToken.isVisible():true");
            if (this.mHasSurface && isVisibleByPolicy() && !this.mDestroying) {
                Slog.d(StartingSurfaceController.TAG, str2 + " mHasSurface:true isVisibleByPolicy:true mDestroying:false");
            }
        }
    }

    public boolean isReadyForDisplay() {
        if (this.mToken.waitingToShow && getDisplayContent().mAppTransition.isTransitionSet()) {
            return false;
        }
        boolean z = !isParentWindowHidden() && this.mViewVisibility == 0 && this.mToken.isVisible();
        if (this.mHasSurface && isVisibleByPolicy() && !this.mDestroying) {
            return z || isAnimating(3);
        }
        return false;
    }

    public boolean isFullyTransparent() {
        return this.mAttrs.alpha == DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }

    public boolean canAffectSystemUiFlags() {
        if (isFullyTransparent()) {
            return false;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord == null) {
            return this.mWinAnimator.getShown() && !(this.mAnimatingExit || this.mDestroying);
        }
        if (activityRecord.canAffectSystemUiFlags()) {
            return (this.mAttrs.type == 3 && (this.mStartingData instanceof SnapshotStartingData)) ? false : true;
        }
        return false;
    }

    public boolean isDisplayed() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return isDrawn() && isVisibleByPolicy() && ((!isParentWindowHidden() && (activityRecord == null || activityRecord.isVisibleRequested())) || isAnimationRunningSelfOrParent());
    }

    public boolean isAnimatingLw() {
        return isAnimating(3);
    }

    public boolean isGoneForLayout() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return this.mViewVisibility == 8 || !this.mRelayoutCalled || (activityRecord == null && !((wouldBeVisibleIfPolicyIgnored() && isVisibleByPolicy()) || (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER && getWindowType() == 2604))) || (!(activityRecord == null || activityRecord.isVisibleRequested()) || isParentWindowGoneForLayout() || ((this.mAnimatingExit && !isAnimatingLw()) || this.mDestroying));
    }

    public boolean isDrawFinishedLw() {
        int i;
        return this.mHasSurface && !this.mDestroying && ((i = this.mWinAnimator.mDrawState) == 2 || i == 3 || i == 4);
    }

    public boolean isDrawn() {
        int i;
        return this.mHasSurface && !this.mDestroying && ((i = this.mWinAnimator.mDrawState) == 3 || i == 4);
    }

    public final boolean isOpaqueDrawn() {
        boolean z = this.mToken.asWallpaperToken() != null;
        return ((!z && this.mAttrs.format == -1) || (z && this.mToken.isVisible())) && isDrawn() && !isAnimating(3);
    }

    public void requestDrawIfNeeded(List list) {
        if (isVisible()) {
            ActivityRecord activityRecord = this.mActivityRecord;
            if (activityRecord != null) {
                if (!activityRecord.isVisibleRequested()) {
                    return;
                }
                ActivityRecord activityRecord2 = this.mActivityRecord;
                if (activityRecord2.allDrawn) {
                    return;
                }
                if (this.mAttrs.type == 3) {
                    if (isDrawn()) {
                        return;
                    }
                } else if (activityRecord2.mStartingWindow != null) {
                    return;
                }
            } else if (!this.mPolicy.isKeyguardHostWindow(this.mAttrs) || this.mWmService.mAtmService.mKeyguardController.isKeyguardGoingAway(getDisplayId())) {
                return;
            }
            this.mWinAnimator.mDrawState = 1;
            forceReportingResized();
            list.add(this);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void onMovedByResize() {
        if (ProtoLogCache.WM_DEBUG_RESIZE_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_RESIZE, 1635462459, 0, (String) null, new Object[]{String.valueOf(this)});
        }
        this.mMovedByResize = true;
        super.onMovedByResize();
    }

    public void onAppVisibilityChanged(boolean z, boolean z2) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowState) this.mChildren.get(size)).onAppVisibilityChanged(z, z2);
        }
        boolean isVisibleNow = isVisibleNow();
        if (this.mAttrs.type != 3) {
            if (z != isVisibleNow) {
                if (!z2 && isVisibleNow) {
                    AccessibilityController accessibilityController = this.mWmService.mAccessibilityController;
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
            if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ANIM, -1471518109, 0, (String) null, new Object[]{String.valueOf(this)});
            }
            this.mAnimatingExit = true;
            this.mRemoveOnExit = true;
            this.mWindowRemovalAllowed = true;
        }
    }

    public boolean onSetAppExiting(boolean z) {
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

    @Override // com.android.server.wm.WindowContainer
    public void onResize() {
        ArrayList arrayList = this.mWmService.mResizingWindows;
        if (this.mHasSurface && !isGoneForLayout() && !arrayList.contains(this)) {
            if (ProtoLogCache.WM_DEBUG_RESIZE_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_RESIZE, 417311568, 0, (String) null, new Object[]{String.valueOf(this)});
            }
            arrayList.add(this);
        }
        if (isGoneForLayout()) {
            this.mResizedWhileGone = true;
        }
        super.onResize();
    }

    public void handleWindowMovedIfNeeded() {
        if (hasMoved()) {
            if (isPopOver() && this.mWindowFrames.hasContentChanged()) {
                Slog.d(StartingSurfaceController.TAG, "Force report resize for pop-over w=" + this);
                this.mWindowFrames.forceReportingResized();
            }
            Rect rect = this.mWindowFrames.mFrame;
            int i = rect.left;
            int i2 = rect.top;
            if (canPlayMoveAnimation()) {
                startMoveAnimation(i, i2);
            }
            if (CoreRune.FW_BOUNDS_COMPAT_FOR_IME_EXPERIENCE && this.mCompatWindowAnimationByIme) {
                this.mCompatWindowAnimationByIme = false;
                return;
            }
            if (CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_ANIMATION && BoundsCompatAlignmentController.shouldPlayMoveAnimation(this)) {
                return;
            }
            if (this.mWmService.mAccessibilityController.hasCallbacks()) {
                this.mWmService.mAccessibilityController.onSomeWindowResizedOrMoved(getDisplayId());
            }
            try {
                this.mClient.moved(i, i2);
            } catch (RemoteException unused) {
            }
            this.mMovedByResize = false;
        }
    }

    public final boolean canPlayMoveAnimation() {
        boolean hasMovementAnimations;
        if (getTask() == null) {
            hasMovementAnimations = getWindowConfiguration().hasMovementAnimations();
        } else {
            hasMovementAnimations = getTask().getWindowConfiguration().hasMovementAnimations();
        }
        return this.mToken.okToAnimate() && !(((this.mAttrs.privateFlags & 64) != 0 && ((!CoreRune.FW_BOUNDS_COMPAT_FOR_IME_EXPERIENCE || !this.mCompatWindowAnimationByIme) && (!CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_ANIMATION || !BoundsCompatAlignmentController.shouldPlayMoveAnimation(this)))) || isDragResizing() || !hasMovementAnimations || this.mWinAnimator.mLastHidden || this.mSeamlesslyRotated);
    }

    public final boolean hasMoved() {
        if (this.mHasSurface && ((this.mWindowFrames.hasContentChanged() || this.mMovedByResize) && !this.mAnimatingExit)) {
            WindowFrames windowFrames = this.mWindowFrames;
            Rect rect = windowFrames.mRelFrame;
            int i = rect.top;
            Rect rect2 = windowFrames.mLastRelFrame;
            if ((i != rect2.top || rect.left != rect2.left || ((CoreRune.FW_BOUNDS_COMPAT_FOR_IME_EXPERIENCE && this.mCompatWindowAnimationByIme) || (CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_ANIMATION && BoundsCompatAlignmentController.shouldPlayMoveAnimation(this)))) && ((!this.mIsChildWindow || !getParentWindow().hasMoved()) && !this.mTransitionController.isCollecting())) {
                return true;
            }
        }
        return false;
    }

    public boolean isObscuringDisplay() {
        Task task = getTask();
        return (task == null || task.fillsParent()) && isOpaqueDrawn() && fillsDisplay();
    }

    public boolean fillsDisplay() {
        DisplayInfo displayInfo = getDisplayInfo();
        Rect rect = this.mWindowFrames.mFrame;
        return rect.left <= 0 && rect.top <= 0 && rect.right >= displayInfo.appWidth && rect.bottom >= displayInfo.appHeight;
    }

    public boolean matchesDisplayAreaBounds() {
        Rect fixedRotationTransformDisplayBounds = this.mToken.getFixedRotationTransformDisplayBounds();
        if (fixedRotationTransformDisplayBounds != null) {
            return fixedRotationTransformDisplayBounds.equals(getBounds());
        }
        DisplayArea displayArea = getDisplayArea();
        if (displayArea == null) {
            return getDisplayContent().getBounds().equals(getBounds());
        }
        return displayArea.getBounds().equals(getBounds());
    }

    public boolean isLastConfigReportedToClient() {
        return this.mLastConfigReportedToClient;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(Configuration configuration) {
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
        if ((getDisplayContent().getImeInputTarget() == this || isImeLayeringTarget()) && (diff & 536870912) != 0) {
            this.mDisplayContent.updateImeControlTarget(isImeLayeringTarget());
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void removeImmediately() {
        StartingData startingData;
        Dimmer.DimState dimState;
        DisplayContent.RemoteInsetsControlTarget remoteInsetsControlTarget;
        if (this.mRemoved) {
            if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 2018454757, 0, (String) null, new Object[]{String.valueOf(this)});
                return;
            }
            return;
        }
        this.mRemoved = true;
        this.mWinAnimator.destroySurfaceLocked(getSyncTransaction());
        if (!this.mDrawHandlers.isEmpty()) {
            this.mWmService.f1749mH.removeMessages(64, this);
        }
        super.removeImmediately();
        if (isImeOverlayLayeringTarget()) {
            this.mWmService.dispatchImeTargetOverlayVisibilityChanged(this.mClient.asBinder(), this.mAttrs.type, false, true);
        }
        DisplayContent displayContent = getDisplayContent();
        if (isImeLayeringTarget()) {
            displayContent.removeImeSurfaceByTarget(this);
            displayContent.setImeLayeringTarget(null);
            displayContent.computeImeTarget(true);
        }
        if (displayContent.getImeInputTarget() == this && (!inRelaunchingActivity() || (getDisplayId() == 0 && (getLastReportedConfiguration().isDexMode() != isDexMode() || this.mWmService.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked())))) {
            this.mWmService.dispatchImeInputTargetVisibilityChanged(this.mClient.asBinder(), false, true);
            InsetsControlTarget imeTarget = displayContent.getImeTarget(2);
            displayContent.updateImeInputAndControlTarget(null);
            if ((inFreeformWindowingMode() || inSplitScreenWindowingMode()) && displayContent.getImeTarget(2) == null && displayContent.getImeInputTarget() == null && imeTarget != null && imeTarget == (remoteInsetsControlTarget = displayContent.mRemoteInsetsControlTarget)) {
                remoteInsetsControlTarget.clearImeRequestedVisibleTypes("input_target_removed(" + this + ")");
            }
        }
        if (WindowManagerService.excludeWindowTypeFromTapOutTask(this.mAttrs.type)) {
            displayContent.mTapExcludedWindows.remove(this);
        }
        displayContent.mTapExcludeProvidingWindows.remove(this);
        displayContent.getDisplayPolicy().removeWindowLw(this);
        this.mWmService.mAtmService.mFreeformController.onWindowRemovedLocked(this);
        if (useBLASTSync()) {
            Slog.w(StartingSurfaceController.TAG, "removeImmediately: Call immediatelyNotifyBlastSync, w=" + this);
            immediatelyNotifyBlastSync();
        }
        disposeInputChannel();
        this.mOnBackInvokedCallbackInfo = null;
        this.mSession.windowRemovedLocked();
        Dimmer dimmer = this.mPopOverDimmer;
        if (dimmer != null && (dimState = dimmer.mDimState) != null && dimState.mDimLayer.isValid()) {
            getPendingTransaction().remove(this.mPopOverDimmer.mDimState.mDimLayer);
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null && activityRecord.isRelaunching() && this.mTransitionController.inCollectingTransition(this.mActivityRecord.mStartingWindow) && (startingData = this.mActivityRecord.mStartingData) != null && startingData.mRemoveAfterTransaction) {
            startingData.mRemoveAfterTransaction = false;
        }
        try {
            this.mClient.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
        } catch (RuntimeException unused) {
        }
        this.mWmService.postWindowRemoveCleanupLocked(this);
    }

    @Override // com.android.server.wm.WindowContainer
    public void removeIfPossible() {
        int i;
        int i2;
        boolean z;
        boolean z2;
        int i3;
        ActivityRecord activityRecord;
        this.mWindowRemovalAllowed = true;
        if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 1504168072, 0, (String) null, new Object[]{String.valueOf(this), String.valueOf(Debug.getCallers(5))});
        }
        int i4 = this.mAttrs.type;
        boolean z3 = i4 == 3;
        if (z3) {
            if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -986746907, 0, "Starting window removed %s", new Object[]{String.valueOf(this)});
            }
            ActivityRecord activityRecord2 = this.mActivityRecord;
            if (activityRecord2 != null) {
                activityRecord2.forAllWindows(new ToBooleanFunction() { // from class: com.android.server.wm.WindowState$$ExternalSyntheticLambda3
                    public final boolean apply(Object obj) {
                        boolean lambda$removeIfPossible$5;
                        lambda$removeIfPossible$5 = WindowState.lambda$removeIfPossible$5((WindowState) obj);
                        return lambda$removeIfPossible$5;
                    }
                }, true);
            }
        } else if (i4 == 1 && isSelfAnimating(0, 128)) {
            cancelAnimation();
        }
        if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_FOCUS, -1047945589, 1, (String) null, new Object[]{Long.valueOf(System.identityHashCode(this.mClient.asBinder())), String.valueOf(this.mWinAnimator.mSurfaceController), String.valueOf(Debug.getCallers(5))});
        }
        DisplayContent displayContent = getDisplayContent();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            disposeInputChannel();
            this.mOnBackInvokedCallbackInfo = null;
            if (ProtoLogCache.WM_FORCE_DEBUG_APP_TRANSITIONS_enabled) {
                String valueOf = String.valueOf(this);
                String valueOf2 = String.valueOf(this.mWinAnimator.mSurfaceController);
                boolean z4 = this.mAnimatingExit;
                boolean z5 = this.mRemoveOnExit;
                boolean z6 = this.mHasSurface;
                boolean shown = this.mWinAnimator.getShown();
                boolean isAnimating = isAnimating(3);
                ActivityRecord activityRecord3 = this.mActivityRecord;
                i2 = 2;
                i = 4;
                ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_APP_TRANSITIONS, 801797637, 262128, "Remove %s: mSurfaceController=%s mAnimatingExit=%b mRemoveOnExit=%b mHasSurface=%b surfaceShowing=%b animating=%b app-animation=%b mDisplayFrozen=%b callers=%s", new Object[]{valueOf, valueOf2, Boolean.valueOf(z4), Boolean.valueOf(z5), Boolean.valueOf(z6), Boolean.valueOf(shown), Boolean.valueOf(isAnimating), Boolean.valueOf(activityRecord3 != null && activityRecord3.isAnimating(3)), Boolean.valueOf(this.mWmService.mDisplayFrozen), String.valueOf(Debug.getCallers(6))});
                z = false;
            } else {
                i = 4;
                i2 = 2;
                z = false;
            }
            adjustImeParamsForDex(z);
            if (this.mHasSurface && this.mToken.okToAnimate()) {
                z2 = isVisible();
                boolean z7 = (displayContent.inTransition() || inRelaunchingActivity()) ? false : true;
                if (z2 && isDisplayed()) {
                    int i5 = !z3 ? i2 : 5;
                    if (z7 && this.mWinAnimator.applyAnimationLocked(i5, false)) {
                        if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ANIM, -91393839, 0, (String) null, new Object[]{String.valueOf(this)});
                        }
                        if (!z3) {
                            Slog.v(StartingSurfaceController.TAG, "removeIfPossible 2, " + this + ": mAnimatingExit=true caller=" + Debug.getCallers(5));
                        }
                        this.mAnimatingExit = true;
                        setDisplayLayoutNeeded();
                        this.mWmService.requestTraversal();
                    }
                    if (this.mWmService.mAccessibilityController.hasCallbacks()) {
                        this.mWmService.mAccessibilityController.onWindowTransition(this, i5);
                    }
                }
                boolean z8 = z7 && (this.mAnimatingExit || isAnimationRunningSelfOrParent());
                if (z8 && getDisplayContent() != null && getDisplayContent().isAppCastingDisplay() && !this.mAnimatingExit && !isAnimating(0, 16)) {
                    z8 = false;
                }
                boolean z9 = z3 && (activityRecord = this.mActivityRecord) != null && activityRecord.isLastWindow(this);
                if (this.mWinAnimator.getShown() && !z9 && z8) {
                    this.mAnimatingExit = true;
                    if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -1103716954, 0, (String) null, new Object[]{String.valueOf(this)});
                    }
                    if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ANIM, 975275467, 0, (String) null, new Object[]{String.valueOf(this)});
                    }
                    setupWindowForRemoveOnExit();
                    ActivityRecord activityRecord4 = this.mActivityRecord;
                    if (activityRecord4 != null) {
                        activityRecord4.updateReportedVisibilityLocked();
                    }
                    return;
                }
            } else {
                z2 = false;
            }
            boolean providesDisplayDecorInsets = providesDisplayDecorInsets();
            InsetsSourceProvider insetsSourceProvider = this.mControllableInsetProvider;
            if (insetsSourceProvider != null && insetsSourceProvider.getSource().getType() == WindowInsets.Type.navigationBars()) {
                displayContent.getDisplayPolicy().mInsetsSourceWindowsExceptIme.remove(this);
                displayContent.getDisplayPolicy().updateDecorInsetsInfo();
                this.mWmService.mAtmService.mExt.mAvoidCompatDisplayInsets = true;
                displayContent.sendNewConfiguration();
                this.mWmService.mAtmService.mExt.mAvoidCompatDisplayInsets = false;
            }
            removeImmediately();
            boolean z10 = z2 && displayContent.updateOrientation();
            if (providesDisplayDecorInsets && ((z10 = z10 | displayContent.getDisplayPolicy().updateDecorInsetsInfo()))) {
                this.mWmService.mAtmService.mExt.mAvoidCompatDisplayInsets = true;
            }
            if (z10) {
                displayContent.sendNewConfiguration();
                i3 = 0;
                this.mWmService.mAtmService.mExt.mAvoidCompatDisplayInsets = false;
            } else {
                i3 = 0;
            }
            this.mWmService.updateFocusedWindowLocked(isFocused() ? i : i3, true);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static /* synthetic */ boolean lambda$removeIfPossible$5(WindowState windowState) {
        if (!windowState.isSelfAnimating(0, 128)) {
            return false;
        }
        windowState.cancelAnimation();
        return true;
    }

    public final void setupWindowForRemoveOnExit() {
        this.mRemoveOnExit = true;
        setDisplayLayoutNeeded();
        getDisplayContent().getDisplayPolicy().removeWindowLw(this);
        boolean updateFocusedWindowLocked = this.mWmService.updateFocusedWindowLocked(3, false);
        this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
        if (updateFocusedWindowLocked) {
            getDisplayContent().getInputMonitor().updateInputWindowsLw(false);
        }
    }

    public void setHasSurface(boolean z) {
        this.mHasSurface = z;
    }

    public boolean canBeImeTarget() {
        ActivityRecord activityRecord;
        ActivityRecord activityRecord2;
        int i;
        if (this.mIsImWindow || inPinnedWindowingMode() || this.mAttrs.type == 2036) {
            return false;
        }
        ActivityRecord activityRecord3 = this.mActivityRecord;
        if (!(activityRecord3 == null || activityRecord3.windowsAreFocusable())) {
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
        if (rootTask == null || (activityRecord2 = this.mActivityRecord) == null || !this.mTransitionController.isTransientLaunch(activityRecord2)) {
            return isVisibleRequestedOrAdding() || (isVisible() && (activityRecord = this.mActivityRecord) != null && activityRecord.isVisible());
        }
        return false;
    }

    public void openInputChannel(InputChannel inputChannel) {
        if (this.mInputChannel != null) {
            throw new IllegalStateException("Window already has an input channel.");
        }
        InputChannel createInputChannel = this.mWmService.mInputManager.createInputChannel(getName());
        this.mInputChannel = createInputChannel;
        IBinder token = createInputChannel.getToken();
        this.mInputChannelToken = token;
        this.mInputWindowHandle.setToken(token);
        this.mWmService.mInputToWindowMap.put(this.mInputChannelToken, this);
        this.mInputChannel.copyTo(inputChannel);
    }

    public boolean transferTouch() {
        return this.mWmService.mInputManager.transferTouch(this.mInputChannelToken, getDisplayId());
    }

    public void disposeInputChannel() {
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

    public void setDisplayLayoutNeeded() {
        DisplayContent displayContent = getDisplayContent();
        if (displayContent != null) {
            displayContent.setLayoutNeeded();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void switchUser(int i) {
        super.switchUser(i);
        if (showToCurrentUser()) {
            setPolicyVisibilityFlag(2);
        } else {
            clearPolicyVisibilityFlag(2);
        }
    }

    public void getSurfaceTouchableRegion(Region region, WindowManager.LayoutParams layoutParams) {
        boolean isModal = layoutParams.isModal();
        if (isModal) {
            if (this.mActivityRecord != null) {
                updateRegionForModalActivityWindow(region);
            } else {
                getDisplayContent().getBounds(this.mTmpRect);
                int width = this.mTmpRect.width();
                int height = this.mTmpRect.height();
                region.set(-width, -height, width + width, height + height);
            }
            subtractTouchExcludeRegionIfNeeded(region);
        } else {
            getTouchableRegion(region);
        }
        Rect rect = this.mWindowFrames.mFrame;
        int i = rect.left;
        if (i != 0 || rect.top != 0) {
            region.translate(-i, -rect.top);
        }
        if (isModal && this.mTouchableInsets == 3) {
            this.mTmpRegion.set(0, 0, rect.right, rect.bottom);
            this.mTmpRegion.op(this.mGivenTouchableRegion, Region.Op.DIFFERENCE);
            region.op(this.mTmpRegion, Region.Op.DIFFERENCE);
        }
        float f = this.mInvGlobalScale;
        if (f != 1.0f) {
            region.scale(f);
        }
    }

    public final void adjustRegionInFreefromWindowMode(Rect rect) {
        if (inFreeformWindowingMode()) {
            int i = -WindowManagerService.dipToPixel(RESIZE_HANDLE_WIDTH_IN_DP, getDisplayContent().getDisplayMetrics());
            rect.inset(i, i);
        }
    }

    public final void updateRegionForModalActivityWindow(Region region) {
        this.mActivityRecord.getLetterboxInnerBounds(this.mTmpRect);
        if (this.mTmpRect.isEmpty()) {
            Rect fixedRotationTransformDisplayBounds = this.mActivityRecord.getFixedRotationTransformDisplayBounds();
            if (CoreRune.FW_CUSTOM_LETTERBOX && CustomLetterboxConfiguration.isCustomLetterboxEnabled(this.mActivityRecord)) {
                this.mTmpRect.set(this.mActivityRecord.getBounds());
            } else if (fixedRotationTransformDisplayBounds != null) {
                this.mTmpRect.set(fixedRotationTransformDisplayBounds);
            } else {
                TaskFragment taskFragment = getTaskFragment();
                if (taskFragment != null) {
                    Task asTask = taskFragment.asTask();
                    if (asTask != null) {
                        asTask.getDimBounds(this.mTmpRect);
                    } else {
                        this.mTmpRect.set(taskFragment.getBounds());
                    }
                } else if (getRootTask() != null) {
                    getRootTask().getDimBounds(this.mTmpRect);
                }
            }
        }
        adjustRegionInFreefromWindowMode(this.mTmpRect);
        region.set(this.mTmpRect);
        cropRegionToRootTaskBoundsIfNeeded(region);
    }

    public void checkPolicyVisibilityChange() {
        boolean isLegacyPolicyVisibility = isLegacyPolicyVisibility();
        boolean z = this.mLegacyPolicyVisibilityAfterAnim;
        if (isLegacyPolicyVisibility != z) {
            if (z) {
                setPolicyVisibilityFlag(1);
            } else {
                clearPolicyVisibilityFlag(1);
            }
            if (isVisibleByPolicy()) {
                return;
            }
            this.mWinAnimator.hide(SurfaceControl.getGlobalTransaction(), "checkPolicyVisibilityChange");
            if (isFocused()) {
                if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
                    ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 693423992, 0, (String) null, (Object[]) null);
                }
                this.mWmService.mFocusMayChange = true;
            }
            setDisplayLayoutNeeded();
            this.mWmService.enableScreenIfNeededLocked();
        }
    }

    public void setRequestedSize(int i, int i2) {
        if (this.mRequestedWidth == i && this.mRequestedHeight == i2) {
            return;
        }
        this.mLayoutNeeded = true;
        this.mRequestedWidth = i;
        this.mRequestedHeight = i2;
    }

    public void prepareWindowToDisplayDuringRelayout(boolean z) {
        ActivityRecord activityRecord;
        if ((this.mAttrs.flags & 2097152) != 0 || ((activityRecord = this.mActivityRecord) != null && activityRecord.canTurnScreenOn())) {
            WindowManagerService windowManagerService = this.mWmService;
            boolean z2 = windowManagerService.mAllowTheaterModeWakeFromLayout || Settings.Global.getInt(windowManagerService.mContext.getContentResolver(), "theater_mode_on", 0) == 0;
            ActivityRecord activityRecord2 = this.mActivityRecord;
            boolean z3 = activityRecord2 == null || activityRecord2.currentLaunchCanTurnScreenOn();
            if (z2 && z3 && (this.mWmService.mAtmService.isDreaming() || !this.mPowerManagerWrapper.isInteractive())) {
                if (!(this.mWmService.mAtmService.mDexController.getDexModeLocked() == 2 && this.mPowerManagerWrapper.isInteractive() && this.mWmService.mPowerManagerInternal.isInternalDisplayOff()) || getDisplayContent() == null || getDisplayContent().getDisplayId() != 2) {
                    this.mPowerManagerWrapper.wakeUp(SystemClock.uptimeMillis(), 2, "android.server.wm:SCREEN_ON_FLAG::" + getOwningPackage());
                }
            }
            ActivityRecord activityRecord3 = this.mActivityRecord;
            if (activityRecord3 != null) {
                activityRecord3.setCurrentLaunchCanTurnScreenOn(false);
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

    public final Configuration getProcessGlobalConfiguration() {
        int i = this.mSession.mPid;
        if (i == ActivityManagerService.MY_PID || i < 0) {
            return this.mWmService.mAtmService.getGlobalConfigurationForPid(i);
        }
        WindowState parentWindow = getParentWindow();
        return this.mWmService.mAtmService.getGlobalConfigurationForPid((parentWindow != null ? parentWindow.mSession : this.mSession).mPid);
    }

    public Configuration getLastReportedConfiguration() {
        return this.mLastReportedConfiguration.getMergedConfiguration();
    }

    public void adjustStartingWindowFlags() {
        ActivityRecord activityRecord;
        WindowState windowState;
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        if (layoutParams.type != 1 || (activityRecord = this.mActivityRecord) == null || (windowState = activityRecord.mStartingWindow) == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams2 = windowState.mAttrs;
        layoutParams2.flags = (layoutParams.flags & 4718593) | (layoutParams2.flags & (-4718594));
    }

    public void setWindowScale(int i, int i2) {
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        if ((layoutParams.flags & 16384) != 0) {
            int i3 = layoutParams.width;
            this.mHScale = i3 != i ? i3 / i : 1.0f;
            int i4 = layoutParams.height;
            this.mVScale = i4 != i2 ? i4 / i2 : 1.0f;
            return;
        }
        this.mVScale = 1.0f;
        this.mHScale = 1.0f;
    }

    public class DeathRecipient implements IBinder.DeathRecipient {
        public DeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            try {
                WindowManagerGlobalLock windowManagerGlobalLock = WindowState.this.mWmService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowState windowState = WindowState.this;
                        WindowState windowForClientLocked = windowState.mWmService.windowForClientLocked(windowState.mSession, windowState.mClient, false);
                        Slog.i(StartingSurfaceController.TAG, "WIN DEATH: " + windowForClientLocked);
                        if (windowForClientLocked != null) {
                            ActivityRecord activityRecord = windowForClientLocked.mActivityRecord;
                            if (activityRecord != null && activityRecord.findMainWindow() == windowForClientLocked) {
                                WindowState.this.mWmService.mSnapshotController.onAppDied(windowForClientLocked.mActivityRecord);
                            }
                            windowForClientLocked.removeIfPossible();
                        } else if (WindowState.this.mHasSurface) {
                            Slog.e(StartingSurfaceController.TAG, "!!! LEAK !!! Window removed but surface still valid.");
                            WindowState.this.removeIfPossible();
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (IllegalArgumentException unused) {
            }
        }
    }

    public boolean canReceiveKeys() {
        return canReceiveKeys(false);
    }

    public String canReceiveKeysReason(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("fromTouch= ");
        sb.append(z);
        sb.append(" isVisibleRequestedOrAdding=");
        sb.append(isVisibleRequestedOrAdding());
        sb.append(" mViewVisibility=");
        sb.append(this.mViewVisibility);
        sb.append(" mRemoveOnExit=");
        sb.append(this.mRemoveOnExit);
        sb.append(" flags=");
        sb.append(this.mAttrs.flags);
        sb.append(" appWindowsAreFocusable=");
        ActivityRecord activityRecord = this.mActivityRecord;
        boolean z2 = false;
        sb.append(activityRecord == null || activityRecord.windowsAreFocusable(z));
        sb.append(" canReceiveTouchInput=");
        sb.append(canReceiveTouchInput());
        sb.append(" displayIsOnTop=");
        sb.append(getDisplayContent().isOnTop());
        sb.append(" displayIsTrusted=");
        sb.append(getDisplayContent().isTrusted());
        sb.append(" transitShouldKeepFocus=");
        ActivityRecord activityRecord2 = this.mActivityRecord;
        if (activityRecord2 != null && this.mTransitionController.shouldKeepFocus(activityRecord2)) {
            z2 = true;
        }
        sb.append(z2);
        return sb.toString();
    }

    public boolean canReceiveKeys(boolean z) {
        ActivityRecord activityRecord;
        ActivityRecord activityRecord2;
        ActivityRecord activityRecord3 = this.mActivityRecord;
        if (activityRecord3 != null && this.mTransitionController.shouldKeepFocus(activityRecord3)) {
            return true;
        }
        if (isVisibleRequestedOrAdding() && this.mViewVisibility == 0 && !this.mRemoveOnExit && (this.mAttrs.flags & 8) == 0 && ((activityRecord = this.mActivityRecord) == null || activityRecord.windowsAreFocusable(z)) && ((activityRecord2 = this.mActivityRecord) == null || activityRecord2.getTask() == null || !this.mActivityRecord.getTask().getRootTask().shouldIgnoreInput())) {
            return z || getDisplayContent().isOnTop() || getDisplayContent().isTrusted();
        }
        return false;
    }

    public boolean canShowWhenLocked() {
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null) {
            return activityRecord.canShowWhenLocked();
        }
        return (this.mAttrs.flags & 524288) != 0;
    }

    public boolean canReceiveTouchInput() {
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord == null || activityRecord.getTask() == null || this.mTransitionController.shouldKeepFocus(this.mActivityRecord)) {
            return true;
        }
        return !this.mActivityRecord.getTask().getRootTask().shouldIgnoreInput() && this.mActivityRecord.isVisibleRequested();
    }

    public boolean hasDrawn() {
        return this.mWinAnimator.mDrawState == 4;
    }

    public boolean show(boolean z, boolean z2) {
        if ((isLegacyPolicyVisibility() && this.mLegacyPolicyVisibilityAfterAnim) || !showToCurrentUser() || !this.mAppOpVisibility || this.mPermanentlyHidden || this.mHiddenWhileSuspended || this.mHiddenWhileProfileLockedState || this.mForceHideNonSystemOverlayWindow) {
            return false;
        }
        if (z && (!this.mToken.okToAnimate() || (isLegacyPolicyVisibility() && !isAnimating(3)))) {
            z = false;
        }
        setPolicyVisibilityFlag(1);
        this.mLegacyPolicyVisibilityAfterAnim = true;
        if (z) {
            this.mWinAnimator.applyAnimationLocked(1, true);
        }
        if (z2) {
            this.mWmService.scheduleAnimationLocked();
        }
        if ((this.mAttrs.flags & 8) == 0) {
            this.mWmService.updateFocusedWindowLocked(0, false);
        }
        return true;
    }

    public boolean hide(boolean z, boolean z2) {
        if (z && !this.mToken.okToAnimate()) {
            z = false;
        }
        if (!(z ? this.mLegacyPolicyVisibilityAfterAnim : isLegacyPolicyVisibility())) {
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
                if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
                    ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 1288731814, 0, (String) null, (Object[]) null);
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

    public void setForceHideNonSystemOverlayWindowIfNeeded(boolean z) {
        int baseType = getBaseType();
        if (this.mSession.mCanAddInternalSystemWindow) {
            return;
        }
        if (WindowManager.LayoutParams.isSystemAlertWindowType(baseType) || baseType == 2005) {
            if (this.mIgnoreHideNonSystemOverlayWindow) {
                Slog.d(StartingSurfaceController.TAG, "Hide non system overlay window ignored app - " + this);
                return;
            }
            if (baseType == 2038 && this.mAttrs.isSystemApplicationOverlay() && this.mSession.mCanCreateSystemApplicationOverlay) {
                return;
            }
            if (CoreRune.BAIDU_CARLIFE && getDisplayContent().isCarLifeDisplay()) {
                Slog.d(StartingSurfaceController.TAG, "do not Hide non system overlay window in CarLife display");
                return;
            }
            if (this.mForceHideNonSystemOverlayWindow == z) {
                return;
            }
            this.mForceHideNonSystemOverlayWindow = z;
            if (z) {
                hide(true, true);
            } else {
                show(true, true);
            }
        }
    }

    public void setHiddenWhileProfileLockedStateLocked(boolean z) {
        if (this.mOwnerCanAddInternalSystemWindow) {
            return;
        }
        if ((WindowManager.LayoutParams.isSystemAlertWindowType(this.mAttrs.type) || this.mAttrs.type == 2005) && this.mHiddenWhileProfileLockedState != z) {
            this.mHiddenWhileProfileLockedState = z;
            if (z) {
                hide(true, true);
            } else {
                show(true, true);
            }
        }
    }

    public void setHiddenWhileSuspended(boolean z) {
        if (this.mOwnerCanAddInternalSystemWindow) {
            return;
        }
        if ((WindowManager.LayoutParams.isSystemAlertWindowType(this.mAttrs.type) || this.mAttrs.type == 2005) && this.mHiddenWhileSuspended != z) {
            this.mHiddenWhileSuspended = z;
            if (z) {
                hide(true, true);
            } else {
                show(true, true);
            }
        }
    }

    public void setHiddenByViewCover(boolean z) {
        this.mHiddenByViewCover = z;
    }

    public boolean isHiddenByViewCover() {
        if (WmCoverState.isEnabled()) {
            return this.mHiddenByViewCover;
        }
        return false;
    }

    public final void setAppOpVisibilityLw(boolean z) {
        if (this.mAppOpVisibility != z) {
            this.mAppOpVisibility = z;
            if (z) {
                show(true, true);
            } else {
                hide(true, true);
            }
        }
    }

    public void initAppOpsState() {
        int startOpNoThrow;
        int i = this.mAppOp;
        if (i == -1 || !this.mAppOpVisibility || (startOpNoThrow = this.mWmService.mAppOps.startOpNoThrow(i, getOwningUid(), getOwningPackage(), true, null, "init-default-visibility")) == 0 || startOpNoThrow == 3) {
            return;
        }
        setAppOpVisibilityLw(false);
    }

    public void resetAppOpsState() {
        int i = this.mAppOp;
        if (i == -1 || !this.mAppOpVisibility) {
            return;
        }
        this.mWmService.mAppOps.finishOp(i, getOwningUid(), getOwningPackage(), (String) null);
    }

    public void updateAppOpsState() {
        if (this.mAppOp == -1) {
            return;
        }
        int owningUid = getOwningUid();
        String owningPackage = getOwningPackage();
        if (this.mAppOpVisibility) {
            int checkOpNoThrow = this.mWmService.mAppOps.checkOpNoThrow(this.mAppOp, owningUid, owningPackage);
            if (checkOpNoThrow == 0 || checkOpNoThrow == 3) {
                return;
            }
            this.mWmService.mAppOps.finishOp(this.mAppOp, owningUid, owningPackage, (String) null);
            setAppOpVisibilityLw(false);
            return;
        }
        int startOpNoThrow = this.mWmService.mAppOps.startOpNoThrow(this.mAppOp, owningUid, owningPackage, true, null, "attempt-to-be-visible");
        if (startOpNoThrow == 0 || startOpNoThrow == 3) {
            setAppOpVisibilityLw(true);
        }
    }

    public void hidePermanentlyLw() {
        if (this.mPermanentlyHidden) {
            return;
        }
        this.mPermanentlyHidden = true;
        hide(true, true);
    }

    public void pokeDrawLockLw(long j) {
        if (isVisibleRequestedOrAdding()) {
            if (this.mDrawLock == null) {
                CharSequence windowTag = getWindowTag();
                PowerManager.WakeLock newWakeLock = this.mWmService.mPowerManager.newWakeLock(128, "Window:" + ((Object) windowTag));
                this.mDrawLock = newWakeLock;
                newWakeLock.setReferenceCounted(false);
                this.mDrawLock.setWorkSource(new WorkSource(this.mOwnerUid, this.mAttrs.packageName));
            }
            this.mDrawLock.acquire(j);
        }
    }

    public boolean isAlive() {
        return this.mClient.asBinder().isBinderAlive();
    }

    @Override // com.android.server.wm.WindowContainer
    public void sendAppVisibilityToClients() {
        super.sendAppVisibilityToClients();
        boolean isClientVisible = this.mToken.isClientVisible();
        if (this.mAttrs.type != 3 || isClientVisible) {
            try {
                Slog.v(StartingSurfaceController.TAG, "Setting visibility of " + this + ": " + isClientVisible + ", caller=" + Debug.getCallers(5));
                this.mClient.dispatchAppVisibility(isClientVisible);
            } catch (DeadObjectException e) {
                Slog.w(StartingSurfaceController.TAG, "Client process is already killed " + this, e);
            } catch (RemoteException e2) {
                Slog.w(StartingSurfaceController.TAG, "Exception thrown during dispatchAppVisibility " + this, e2);
                int i = this.mSession.mPid;
                int i2 = WindowManagerService.MY_PID;
                if (i == i2 || Process.getThreadGroupLeader(i) == i2) {
                    return;
                }
                int uidForPid = Process.getUidForPid(this.mSession.mPid);
                Session session = this.mSession;
                if (uidForPid == session.mUid) {
                    Process.killProcess(session.mPid);
                }
            }
        }
    }

    public void onStartFreezingScreen() {
        this.mAppFreezing = true;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowState) this.mChildren.get(size)).onStartFreezingScreen();
        }
    }

    public boolean onStopFreezingScreen() {
        boolean z = false;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z |= ((WindowState) this.mChildren.get(size)).onStopFreezingScreen();
        }
        if (!this.mAppFreezing) {
            return z;
        }
        this.mAppFreezing = false;
        if (this.mHasSurface && !getOrientationChanging() && this.mWmService.mWindowsFreezingScreen != 2) {
            if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1747461042, 0, (String) null, new Object[]{String.valueOf(this)});
            }
            setOrientationChanging(true);
        }
        this.mLastFreezeDuration = 0;
        setDisplayLayoutNeeded();
        return true;
    }

    public boolean destroySurface(boolean z, boolean z2) {
        ArrayList arrayList = new ArrayList(this.mChildren);
        boolean z3 = false;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            z3 |= ((WindowState) arrayList.get(size)).destroySurface(z, z2);
        }
        if ((z2 || this.mWindowRemovalAllowed || z) && this.mDestroying) {
            if (ProtoLogCache.WM_FORCE_DEBUG_ADD_REMOVE_enabled) {
                ProtoLogImpl.e(ProtoLogGroup.WM_FORCE_DEBUG_ADD_REMOVE, 597817991, 2044, "win=%s destroySurfaces: appStopped=%b cleanupOnResume=%b win.mWindowRemovalAllowed=%b win.mRemoveOnExit=%b win.mViewVisibility=%d caller=%s", new Object[]{String.valueOf(this), Boolean.valueOf(z2), Boolean.valueOf(z), Boolean.valueOf(this.mWindowRemovalAllowed), Boolean.valueOf(this.mRemoveOnExit), Long.valueOf(this.mViewVisibility), String.valueOf(Debug.getCallers(7))});
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
            if (!getDisplayContent().mAppTransition.isTransitionSet() || !getDisplayContent().mOpeningApps.contains(this.mActivityRecord)) {
                return true;
            }
            this.mWmService.mWindowPlacerLocked.requestTraversal();
            return true;
        }
        return z3;
    }

    public void destroySurfaceUnchecked() {
        this.mWinAnimator.destroySurfaceLocked(this.mTmpTransaction);
        this.mTmpTransaction.apply();
        this.mAnimatingExit = false;
        if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ANIM, -2052051397, 0, (String) null, new Object[]{String.valueOf(this)});
        }
        if (useBLASTSync()) {
            immediatelyNotifyBlastSync();
        }
    }

    public void onSurfaceShownChanged(boolean z) {
        if (this.mLastShownChangedReported == z) {
            return;
        }
        this.mLastShownChangedReported = z;
        if (z) {
            initExclusionRestrictions();
        } else {
            logExclusionRestrictions(0);
            logExclusionRestrictions(1);
            getDisplayContent().removeImeSurfaceByTarget(this);
        }
        int i = this.mAttrs.type;
        if (i < 2000 || i == 2005 || i == 2030) {
            return;
        }
        if (i == 2037 && isOnVirtualDisplay()) {
            return;
        }
        this.mWmService.mAtmService.mActiveUids.onNonAppSurfaceVisibilityChanged(this.mOwnerUid, z);
    }

    public final boolean isOnVirtualDisplay() {
        return getDisplayContent().mDisplay.getType() == 5;
    }

    public final void logExclusionRestrictions(int i) {
        if (!DisplayContent.logsGestureExclusionRestrictions(this) || SystemClock.uptimeMillis() < this.mLastExclusionLogUptimeMillis[i] + this.mWmService.mConstants.mSystemGestureExclusionLogDebounceTimeoutMillis) {
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        long[] jArr = this.mLastExclusionLogUptimeMillis;
        long j = uptimeMillis - jArr[i];
        jArr[i] = uptimeMillis;
        int i2 = this.mLastRequestedExclusionHeight[i];
        FrameworkStatsLog.write(FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED, this.mAttrs.packageName, i2, i2 - this.mLastGrantedExclusionHeight[i], i + 1, getConfiguration().orientation == 2, false, (int) j);
    }

    public final void initExclusionRestrictions() {
        long uptimeMillis = SystemClock.uptimeMillis();
        long[] jArr = this.mLastExclusionLogUptimeMillis;
        jArr[0] = uptimeMillis;
        jArr[1] = uptimeMillis;
    }

    public boolean showForAllUsers() {
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
                                            if (!this.mWmService.mExt.showForAllUsers(i) && (this.mAttrs.privateFlags & 16) == 0) {
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

    @Override // com.android.server.wm.WindowContainer
    public boolean showToCurrentUser() {
        ActivityRecord activityRecord;
        WindowState topParentWindow = getTopParentWindow();
        return (topParentWindow.mAttrs.type < 2000 && (activityRecord = topParentWindow.mActivityRecord) != null && activityRecord.mShowForAllUsers && topParentWindow.getFrame().left <= topParentWindow.getDisplayFrame().left && topParentWindow.getFrame().top <= topParentWindow.getDisplayFrame().top && topParentWindow.getFrame().right >= topParentWindow.getDisplayFrame().right && topParentWindow.getFrame().bottom >= topParentWindow.getDisplayFrame().bottom) || topParentWindow.showForAllUsers() || this.mWmService.isUserVisible(topParentWindow.mShowUserId);
    }

    public static void applyInsets(Region region, Rect rect, Rect rect2) {
        region.set(rect.left + rect2.left, rect.top + rect2.top, rect.right - rect2.right, rect.bottom - rect2.bottom);
    }

    public void getTouchableRegion(Region region) {
        Rect rect = this.mWindowFrames.mFrame;
        int i = this.mTouchableInsets;
        if (i == 1) {
            applyInsets(region, rect, this.mGivenContentInsets);
        } else if (i == 2) {
            applyInsets(region, rect, this.mGivenVisibleInsets);
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

    public void getEffectiveTouchableRegion(Region region) {
        DisplayContent displayContent = getDisplayContent();
        if (this.mAttrs.isModal() && displayContent != null) {
            region.set(displayContent.getBounds());
            cropRegionToRootTaskBoundsIfNeeded(region);
            cropRegionToTaskBoundsIfNeeded(region);
            subtractTouchExcludeRegionIfNeeded(region);
            return;
        }
        getTouchableRegion(region);
    }

    public final void cropRegionToTaskBoundsIfNeeded(Region region) {
        Task task = getTask();
        if (task != null && task.inSplitScreenWindowingMode() && task.isLeafTask()) {
            task.getBounds(this.mTmpRect);
            if (this.mTmpRect.isEmpty()) {
                return;
            }
            region.op(this.mTmpRect, Region.Op.INTERSECT);
        }
    }

    public final void cropRegionToRootTaskBoundsIfNeeded(Region region) {
        Task rootTask;
        Task task = getTask();
        if (task == null || !task.cropWindowsToRootTaskBounds() || (rootTask = task.getRootTask()) == null || rootTask.mCreatedByOrganizer) {
            return;
        }
        rootTask.getDimBounds(this.mTmpRect);
        adjustRegionInFreefromWindowMode(this.mTmpRect);
        region.op(this.mTmpRect, Region.Op.INTERSECT);
    }

    public final void subtractTouchExcludeRegionIfNeeded(Region region) {
        if (this.mTapExcludeRegion.isEmpty()) {
            return;
        }
        Region obtain = Region.obtain();
        getTapExcludeRegion(obtain);
        if (!obtain.isEmpty()) {
            region.op(obtain, Region.Op.DIFFERENCE);
        }
        obtain.recycle();
    }

    public void reportFocusChangedSerialized(boolean z) {
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

    @Override // com.android.server.wm.ConfigurationContainer
    public Configuration getConfiguration() {
        if (!registeredForDisplayAreaConfigChanges()) {
            return super.getConfiguration();
        }
        this.mTempConfiguration.setTo(getProcessGlobalConfiguration());
        this.mTempConfiguration.updateFrom(getMergedOverrideConfiguration());
        return this.mTempConfiguration;
    }

    public final boolean registeredForDisplayAreaConfigChanges() {
        WindowProcessController windowProcessController;
        WindowState parentWindow = getParentWindow();
        if (parentWindow != null) {
            windowProcessController = parentWindow.mWpcForDisplayAreaConfigChanges;
        } else {
            windowProcessController = this.mWpcForDisplayAreaConfigChanges;
        }
        return windowProcessController != null && windowProcessController.registeredForDisplayAreaConfigChanges();
    }

    public WindowProcessController getProcess() {
        return this.mWpcForDisplayAreaConfigChanges;
    }

    public void fillClientWindowFramesAndConfiguration(ClientWindowFrames clientWindowFrames, MergedConfiguration mergedConfiguration, boolean z, boolean z2) {
        ActivityRecord activityRecord;
        clientWindowFrames.frame.set(this.mWindowFrames.mCompatFrame);
        clientWindowFrames.displayFrame.set(this.mWindowFrames.mDisplayFrame);
        if (this.mIsChildWindow && (this.mAttrs.gravity & 80) == 80 && this.mOverrideScale != 1.0f) {
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
            clientWindowFrames.attachedFrame.set(getParentWindow().getFrame());
            if (this.mIsChildWindow && (this.mAttrs.gravity & 80) == 80 && this.mOverrideScale != 1.0f) {
                float f3 = this.mCompatScale;
                if (f3 != 1.0f) {
                    clientWindowFrames.attachedFrame.scale(f3);
                }
            } else {
                float f4 = this.mInvGlobalScale;
                if (f4 != 1.0f) {
                    clientWindowFrames.attachedFrame.scale(f4);
                }
            }
        }
        clientWindowFrames.compatScale = getCompatScaleForClient();
        if (z || (z2 && ((activityRecord = this.mActivityRecord) == null || activityRecord.isVisibleRequested()))) {
            mergedConfiguration.setConfiguration(getProcessGlobalConfiguration(), getMergedOverrideConfiguration());
            MergedConfiguration mergedConfiguration2 = this.mLastReportedConfiguration;
            if (mergedConfiguration != mergedConfiguration2) {
                mergedConfiguration2.setTo(mergedConfiguration);
            }
        } else {
            mergedConfiguration.setTo(this.mLastReportedConfiguration);
        }
        this.mLastConfigReportedToClient = true;
    }

    public void reportResized() {
        if (inRelaunchingActivity()) {
            return;
        }
        if (!shouldCheckTokenVisibleRequested() || this.mToken.isVisibleRequested()) {
            if (Trace.isTagEnabled(32L)) {
                Trace.traceBegin(32L, "wm.reportResized_" + ((Object) getWindowTag()));
            }
            if (ProtoLogCache.WM_DEBUG_RESIZE_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_RESIZE, -1824578273, 0, (String) null, new Object[]{String.valueOf(this), String.valueOf(this.mWindowFrames.mCompatFrame)});
            }
            boolean z = this.mWinAnimator.mDrawState == 1;
            if (z && ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1130868271, 0, (String) null, new Object[]{String.valueOf(this)});
            }
            this.mDragResizingChangeReported = true;
            this.mWindowFrames.clearReportResizeHints();
            updateLastFrames();
            int rotation = this.mLastReportedConfiguration.getMergedConfiguration().windowConfiguration.getRotation();
            fillClientWindowFramesAndConfiguration(this.mClientWindowFrames, this.mLastReportedConfiguration, true, false);
            boolean shouldSendRedrawForSync = shouldSendRedrawForSync();
            boolean z2 = shouldSendRedrawForSync && shouldSyncWithBuffers();
            boolean z3 = shouldSendRedrawForSync || z;
            boolean isDragResizeChanged = isDragResizeChanged();
            boolean z4 = z2 || isDragResizeChanged;
            DisplayContent displayContent = getDisplayContent();
            boolean areSystemBarsForcedConsumedLw = displayContent.getDisplayPolicy().areSystemBarsForcedConsumedLw();
            int displayId = displayContent.getDisplayId();
            if (isDragResizeChanged) {
                setDragResizing();
            }
            boolean isDragResizing = isDragResizing();
            markRedrawForSyncReported();
            try {
                this.mClient.resized(this.mClientWindowFrames, z3, this.mLastReportedConfiguration, getCompatInsetsState(), z4, areSystemBarsForcedConsumedLw, displayId, z2 ? this.mSyncSeqId : -1, isDragResizing);
                if (z && rotation >= 0 && rotation != this.mLastReportedConfiguration.getMergedConfiguration().windowConfiguration.getRotation()) {
                    this.mOrientationChangeRedrawRequestTime = SystemClock.elapsedRealtime();
                    if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -567946587, 0, (String) null, new Object[]{String.valueOf(this)});
                    }
                }
                if (this.mWmService.mAccessibilityController.hasCallbacks()) {
                    this.mWmService.mAccessibilityController.onSomeWindowResizedOrMoved(displayId);
                }
            } catch (RemoteException e) {
                setOrientationChanging(false);
                this.mLastFreezeDuration = (int) (SystemClock.elapsedRealtime() - this.mWmService.mDisplayFreezeTime);
                Slog.w(StartingSurfaceController.TAG, "Failed to report 'resized' to " + this + " due to " + e);
            }
            Trace.traceEnd(32L);
        }
    }

    public boolean inRelaunchingActivity() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && activityRecord.isRelaunching();
    }

    public boolean isClientLocal() {
        return this.mClient instanceof IWindow.Stub;
    }

    public void notifyInsetsChanged() {
        if (ProtoLogCache.WM_DEBUG_WINDOW_INSETS_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_WINDOW_INSETS, 1047505501, 0, (String) null, new Object[]{String.valueOf(this)});
        }
        this.mWindowFrames.setInsetsChanged(true);
        WindowManagerService windowManagerService = this.mWmService;
        windowManagerService.mWindowsInsetsChanged++;
        windowManagerService.f1749mH.removeMessages(66);
        this.mWmService.f1749mH.sendEmptyMessage(66);
        WindowContainer parent = getParent();
        if (parent != null) {
            parent.updateOverlayInsetsState(this);
        }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public void notifyInsetsControlChanged() {
        if (ProtoLogCache.WM_DEBUG_WINDOW_INSETS_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_WINDOW_INSETS, 1030898920, 0, (String) null, new Object[]{String.valueOf(this)});
        }
        if (this.mRemoved) {
            return;
        }
        try {
            this.mClient.insetsControlChanged(getCompatInsetsState(), getDisplayContent().getInsetsStateController().getControlsForDispatch(this));
        } catch (RemoteException e) {
            Slog.w(StartingSurfaceController.TAG, "Failed to deliver inset control state change to w=" + this, e);
        }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public void showInsets(int i, boolean z, ImeTracker.Token token) {
        try {
            ImeTracker.forLogging().onProgress(token, 21);
            this.mClient.showInsets(i, z, token);
        } catch (RemoteException e) {
            Slog.w(StartingSurfaceController.TAG, "Failed to deliver showInsets", e);
            ImeTracker.forLogging().onFailed(token, 21);
        }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public void hideInsets(int i, boolean z, ImeTracker.Token token) {
        try {
            ImeTracker.forLogging().onProgress(token, 22);
            this.mClient.hideInsets(i, z, token);
        } catch (RemoteException e) {
            Slog.w(StartingSurfaceController.TAG, "Failed to deliver hideInsets", e);
            ImeTracker.forLogging().onFailed(token, 22);
        }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public boolean canShowTransient() {
        return (this.mAttrs.insetsFlags.behavior & 2) != 0;
    }

    public boolean canBeHiddenByKeyguard() {
        int i;
        return (this.mWmService.mExt.mExtraDisplayPolicy.hasCoverHome(getDisplayId()) || this.mActivityRecord != null || (i = this.mAttrs.type) == 2000 || i == 2013 || i == 2019 || i == 2040 || i == 2633 || this.mDisplayContent.getDisplayPolicy().mExt.getTaskbarController().isTaskbar(this) || this.mPolicy.getWindowLayerLw(this) >= this.mPolicy.getWindowLayerFromTypeLw(2040)) ? false : true;
    }

    public final int getRootTaskId() {
        Task rootTask = getRootTask();
        if (rootTask == null) {
            return -1;
        }
        return rootTask.mTaskId;
    }

    public void registerFocusObserver(IWindowFocusObserver iWindowFocusObserver) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mFocusCallbacks == null) {
                    this.mFocusCallbacks = new RemoteCallbackList();
                }
                this.mFocusCallbacks.register(iWindowFocusObserver);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void unregisterFocusObserver(IWindowFocusObserver iWindowFocusObserver) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                RemoteCallbackList remoteCallbackList = this.mFocusCallbacks;
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

    public boolean isFocused() {
        return getDisplayContent().mCurrentFocus == this;
    }

    public boolean areAppWindowBoundsLetterboxed() {
        ActivityRecord activityRecord;
        if (isDexMode() || (activityRecord = this.mActivityRecord) == null) {
            return false;
        }
        return activityRecord.areBoundsLetterboxed() || isLetterboxedForDisplayCutout();
    }

    public boolean isLetterboxedForDisplayCutout() {
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord == null) {
            return false;
        }
        if ((CoreRune.FW_CUSTOM_LETTERBOX && CustomLetterboxConfiguration.isCustomLetterboxEnabled(activityRecord)) || !this.mWindowFrames.parentFrameWasClippedByDisplayCutout()) {
            return false;
        }
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        if (layoutParams.layoutInDisplayCutoutMode != 3 && layoutParams.isFullscreen()) {
            return !frameCoversEntireAppTokenBounds();
        }
        return false;
    }

    public final boolean frameCoversEntireAppTokenBounds() {
        this.mTmpRect.set(this.mActivityRecord.getBounds());
        this.mTmpRect.intersectUnchecked(this.mWindowFrames.mFrame);
        return this.mActivityRecord.getBounds().equals(this.mTmpRect);
    }

    public boolean isFullyTransparentBarAllowed(Rect rect) {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord == null || activityRecord.isFullyTransparentBarAllowed(rect);
    }

    public boolean isDragResizeChanged() {
        return this.mDragResizing != computeDragResizing();
    }

    @Override // com.android.server.wm.WindowContainer
    public void setWaitingForDrawnIfResizingChanged() {
        if (isDragResizeChanged()) {
            this.mWmService.mRoot.mWaitingForDrawn.add(this);
        }
        super.setWaitingForDrawnIfResizingChanged();
    }

    @Override // com.android.server.wm.WindowContainer
    public void resetDragResizingChangeReported() {
        this.mDragResizingChangeReported = false;
        super.resetDragResizingChangeReported();
    }

    public final boolean computeDragResizing() {
        Task task = getTask();
        if (task == null) {
            return false;
        }
        if ((!inFreeformWindowingMode() && !task.getRootTask().mCreatedByOrganizer) || task.getActivityType() == 2) {
            return false;
        }
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        return layoutParams.width == -1 && layoutParams.height == -1 && task.isDragResizing();
    }

    public void setDragResizing() {
        boolean computeDragResizing = computeDragResizing();
        if (computeDragResizing == this.mDragResizing) {
            return;
        }
        this.mDragResizing = computeDragResizing;
    }

    public boolean isDragResizing() {
        return this.mDragResizing;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        boolean isVisible = isVisible();
        if (i != 2 || isVisible) {
            long start = protoOutputStream.start(j);
            super.dumpDebug(protoOutputStream, 1146756268033L, i);
            protoOutputStream.write(1120986464259L, getDisplayId());
            protoOutputStream.write(1120986464260L, getRootTaskId());
            this.mAttrs.dumpDebug(protoOutputStream, 1146756268037L);
            this.mGivenContentInsets.dumpDebug(protoOutputStream, 1146756268038L);
            this.mWindowFrames.dumpDebug(protoOutputStream, 1146756268073L);
            this.mAttrs.surfaceInsets.dumpDebug(protoOutputStream, 1146756268044L);
            GraphicsProtos.dumpPointProto(this.mSurfacePosition, protoOutputStream, 1146756268048L);
            this.mWinAnimator.dumpDebug(protoOutputStream, 1146756268045L);
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
            Iterator it = this.mKeepClearAreas.iterator();
            while (it.hasNext()) {
                ((Rect) it.next()).dumpDebug(protoOutputStream, 2246267895853L);
            }
            Iterator it2 = this.mUnrestrictedKeepClearAreas.iterator();
            while (it2.hasNext()) {
                ((Rect) it2.next()).dumpDebug(protoOutputStream, 2246267895854L);
            }
            if (this.mMergedLocalInsetsSources != null) {
                for (int i2 = 0; i2 < this.mMergedLocalInsetsSources.size(); i2++) {
                    ((InsetsSource) this.mMergedLocalInsetsSources.valueAt(i2)).dumpDebug(protoOutputStream, 2246267895855L);
                }
            }
            protoOutputStream.end(start);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void writeIdentifierToProto(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, System.identityHashCode(this));
        protoOutputStream.write(1120986464258L, this.mShowUserId);
        CharSequence windowTag = getWindowTag();
        if (windowTag != null) {
            protoOutputStream.write(1138166333443L, windowTag.toString());
        }
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.WindowContainer
    @NeverCompile
    public void dump(PrintWriter printWriter, String str, boolean z) {
        printWriter.print(str + "mDisplayId=" + getDisplayId());
        if (getRootTask() != null) {
            printWriter.print(" rootTaskId=" + getRootTaskId());
        }
        printWriter.println(" mSession=" + this.mSession + " mClient=" + this.mClient.asBinder());
        printWriter.println(str + "mOwnerUid=" + this.mOwnerUid + " showForAllUsers=" + showForAllUsers() + " package=" + this.mAttrs.packageName + " appop=" + AppOpsManager.opToName(this.mAppOp));
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("mAttrs=");
        sb.append(this.mAttrs.toString(str));
        printWriter.println(sb.toString());
        printWriter.println(str + "Requested w=" + this.mRequestedWidth + " h=" + this.mRequestedHeight + " mLayoutSeq=" + this.mLayoutSeq);
        if (this.mRequestedWidth != this.mLastRequestedWidth || this.mRequestedHeight != this.mLastRequestedHeight) {
            printWriter.println(str + "LastRequested w=" + this.mLastRequestedWidth + " h=" + this.mLastRequestedHeight);
        }
        if (this.mIsChildWindow || this.mLayoutAttached) {
            printWriter.println(str + "mParentWindow=" + getParentWindow() + " mLayoutAttached=" + this.mLayoutAttached);
        }
        if (this.mIsImWindow || this.mIsWallpaper || this.mIsFloatingLayer) {
            printWriter.println(str + "mIsImWindow=" + this.mIsImWindow + " mIsWallpaper=" + this.mIsWallpaper + " mIsFloatingLayer=" + this.mIsFloatingLayer);
        }
        if (z) {
            printWriter.print(str);
            printWriter.print("mBaseLayer=");
            printWriter.print(this.mBaseLayer);
            printWriter.print(" mSubLayer=");
            printWriter.print(this.mSubLayer);
        }
        if (z) {
            printWriter.println(str + "mToken=" + this.mToken);
            if (this.mActivityRecord != null) {
                printWriter.println(str + "mActivityRecord=" + this.mActivityRecord);
                printWriter.print(str + "drawnStateEvaluated=" + getDrawnStateEvaluated());
                printWriter.println(str + "mightAffectAllDrawn=" + mightAffectAllDrawn());
            }
            printWriter.println(str + "mViewVisibility=0x" + Integer.toHexString(this.mViewVisibility) + " mHaveFrame=" + this.mHaveFrame + " mObscured=" + this.mObscured);
            if (this.mDisableFlags != 0) {
                printWriter.println(str + "mDisableFlags=" + ViewDebug.flagsToString(View.class, "mSystemUiVisibility", this.mDisableFlags));
            }
        }
        if (!isVisibleByPolicy() || !this.mLegacyPolicyVisibilityAfterAnim || !this.mAppOpVisibility || isParentWindowHidden() || this.mPermanentlyHidden || this.mForceHideNonSystemOverlayWindow || this.mHiddenWhileSuspended) {
            printWriter.println(str + "mPolicyVisibility=" + isVisibleByPolicy() + " mLegacyPolicyVisibilityAfterAnim=" + this.mLegacyPolicyVisibilityAfterAnim + " mAppOpVisibility=" + this.mAppOpVisibility + " parentHidden=" + isParentWindowHidden() + " mPermanentlyHidden=" + this.mPermanentlyHidden + " mHiddenWhileSuspended=" + this.mHiddenWhileSuspended + " mForceHideNonSystemOverlayWindow=" + this.mForceHideNonSystemOverlayWindow);
        }
        if (!this.mRelayoutCalled || this.mLayoutNeeded) {
            printWriter.println(str + "mRelayoutCalled=" + this.mRelayoutCalled + " mLayoutNeeded=" + this.mLayoutNeeded);
        }
        if (z) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("mGivenContentInsets=");
            Rect rect = this.mGivenContentInsets;
            StringBuilder sb3 = sTmpSB;
            sb2.append(rect.toShortString(sb3));
            sb2.append(" mGivenVisibleInsets=");
            sb2.append(this.mGivenVisibleInsets.toShortString(sb3));
            printWriter.println(sb2.toString());
            if (this.mTouchableInsets != 0 || this.mGivenInsetsPending) {
                printWriter.println(str + "mTouchableInsets=" + this.mTouchableInsets + " mGivenInsetsPending=" + this.mGivenInsetsPending);
                Region region = new Region();
                getTouchableRegion(region);
                printWriter.println(str + "touchable region=" + region);
            }
            printWriter.println(str + "mFullConfiguration=" + getConfiguration());
            printWriter.println(str + "mLastReportedConfiguration=" + getLastReportedConfiguration());
        }
        printWriter.println(str + "mHasSurface=" + this.mHasSurface + " isReadyForDisplay()=" + isReadyForDisplay() + " mWindowRemovalAllowed=" + this.mWindowRemovalAllowed);
        if (this.mInvGlobalScale != 1.0f) {
            printWriter.println(str + "mCompatFrame=" + this.mWindowFrames.mCompatFrame.toShortString(sTmpSB));
        }
        if (z) {
            this.mWindowFrames.dump(printWriter, str);
            printWriter.println(str + " surface=" + this.mAttrs.surfaceInsets.toShortString(sTmpSB));
        }
        super.dump(printWriter, str, z);
        printWriter.println(str + this.mWinAnimator + XmlUtils.STRING_ARRAY_SEPARATOR);
        this.mWinAnimator.dump(printWriter, str + "  ", z);
        if (this.mAnimatingExit || this.mRemoveOnExit || this.mDestroying || this.mRemoved) {
            printWriter.println(str + "mAnimatingExit=" + this.mAnimatingExit + " mRemoveOnExit=" + this.mRemoveOnExit + " mDestroying=" + this.mDestroying + " mRemoved=" + this.mRemoved);
        }
        if (getOrientationChanging() || this.mAppFreezing) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append("mOrientationChanging=");
            sb4.append(this.mOrientationChanging);
            sb4.append(" configOrientationChanging=");
            sb4.append(getLastReportedConfiguration().orientation != getConfiguration().orientation);
            sb4.append(" mAppFreezing=");
            sb4.append(this.mAppFreezing);
            printWriter.println(sb4.toString());
        }
        if (this.mLastFreezeDuration != 0) {
            printWriter.print(str + "mLastFreezeDuration=");
            TimeUtils.formatDuration((long) this.mLastFreezeDuration, printWriter);
            printWriter.println();
        }
        printWriter.print(str + "mForceSeamlesslyRotate=" + this.mForceSeamlesslyRotate + " seamlesslyRotate: pending=");
        SeamlessRotator seamlessRotator = this.mPendingSeamlessRotate;
        if (seamlessRotator != null) {
            seamlessRotator.dump(printWriter);
        } else {
            printWriter.print("null");
        }
        if (this.mXOffset != 0 || this.mYOffset != 0) {
            printWriter.println(str + "mXOffset=" + this.mXOffset + " mYOffset=" + this.mYOffset);
        }
        if (this.mHScale != 1.0f || this.mVScale != 1.0f) {
            printWriter.println(str + "mHScale=" + this.mHScale + " mVScale=" + this.mVScale);
        }
        if (this.mWallpaperX != -1.0f || this.mWallpaperY != -1.0f) {
            printWriter.println(str + "mWallpaperX=" + this.mWallpaperX + " mWallpaperY=" + this.mWallpaperY);
        }
        if (this.mWallpaperXStep != -1.0f || this.mWallpaperYStep != -1.0f) {
            printWriter.println(str + "mWallpaperXStep=" + this.mWallpaperXStep + " mWallpaperYStep=" + this.mWallpaperYStep);
        }
        if (this.mWallpaperZoomOut != -1.0f) {
            printWriter.println(str + "mWallpaperZoomOut=" + this.mWallpaperZoomOut);
        }
        if (this.mWallpaperDisplayOffsetX != Integer.MIN_VALUE || this.mWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
            printWriter.println(str + "mWallpaperDisplayOffsetX=" + this.mWallpaperDisplayOffsetX + " mWallpaperDisplayOffsetY=" + this.mWallpaperDisplayOffsetY);
        }
        if (this.mDrawLock != null) {
            printWriter.println(str + "mDrawLock=" + this.mDrawLock);
        }
        if (isDragResizing()) {
            printWriter.println(str + "isDragResizing=" + isDragResizing());
        }
        if (computeDragResizing()) {
            printWriter.println(str + "computeDragResizing=" + computeDragResizing());
        }
        printWriter.println(str + "isOnScreen=" + isOnScreen());
        printWriter.println(str + "isVisible=" + isVisible());
        printWriter.println(str + "keepClearAreas: restricted=" + this.mKeepClearAreas + ", unrestricted=" + this.mUnrestrictedKeepClearAreas);
        if (z && this.mRequestedVisibleTypes != WindowInsets.Type.defaultVisible()) {
            printWriter.println(str + "Requested non-default-visibility types: " + WindowInsets.Type.toString(this.mRequestedVisibleTypes ^ WindowInsets.Type.defaultVisible()));
        }
        if (this.mPopOverDimmerNeeded) {
            printWriter.println(str + "mPopOverDimmerNeeded=true");
        }
        printWriter.println(str + "mPrepareSyncSeqId=" + this.mPrepareSyncSeqId);
        if (this.mAttrs.layoutInDisplayCutoutMode != this.mOriginalLayoutInDisplayCutoutMode) {
            printWriter.println(str + "mOriginalLayoutInDisplayCutoutMode=" + this.mOriginalLayoutInDisplayCutoutMode);
        }
        if (CoreRune.FW_WAIT_TO_HANDLE_RESIZING_FROM_CLIENT && this.mWaitToHandleResizing) {
            printWriter.println(str + "mWaitToHandleResizing=true");
        }
        printWriter.println(str + "mHiddenWhileProfileLockedState=" + this.mHiddenWhileProfileLockedState);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public String getName() {
        return Integer.toHexString(System.identityHashCode(this)) + " " + ((Object) getWindowTag());
    }

    public CharSequence getWindowTag() {
        CharSequence title = this.mAttrs.getTitle();
        return (title == null || title.length() <= 0) ? this.mAttrs.packageName : title;
    }

    public String toString() {
        CharSequence windowTag = getWindowTag();
        if (this.mStringNameCache == null || this.mLastTitle != windowTag || this.mWasExiting != this.mAnimatingExit) {
            this.mLastTitle = windowTag;
            this.mWasExiting = this.mAnimatingExit;
            StringBuilder sb = new StringBuilder();
            sb.append("Window{");
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

    public boolean isChildWindow() {
        return this.mIsChildWindow;
    }

    public boolean hideNonSystemOverlayWindowsWhenVisible() {
        return (this.mAttrs.privateFlags & 524288) != 0 && this.mSession.mCanHideNonSystemOverlayWindows;
    }

    public WindowState getParentWindow() {
        if (this.mIsChildWindow) {
            return (WindowState) super.getParent();
        }
        return null;
    }

    public WindowState getTopParentWindow() {
        WindowState windowState;
        loop0: while (true) {
            windowState = this;
            while (this != null && this.mIsChildWindow) {
                this = this.getParentWindow();
                if (this != null) {
                    break;
                }
            }
        }
        return windowState;
    }

    public boolean isParentWindowHidden() {
        WindowState parentWindow = getParentWindow();
        return parentWindow != null && parentWindow.mHidden;
    }

    public final boolean isParentWindowGoneForLayout() {
        WindowState parentWindow = getParentWindow();
        return parentWindow != null && parentWindow.isGoneForLayout();
    }

    public void requestUpdateWallpaperIfNeeded() {
        DisplayContent displayContent = getDisplayContent();
        if (displayContent != null && ((this.mIsWallpaper && !this.mLastConfigReportedToClient) || hasWallpaper())) {
            displayContent.pendingLayoutChanges |= 4;
            displayContent.setLayoutNeeded();
            this.mWmService.mWindowPlacerLocked.requestTraversal();
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowState) this.mChildren.get(size)).requestUpdateWallpaperIfNeeded();
        }
    }

    public float translateToWindowX(float f) {
        float f2 = f - this.mWindowFrames.mFrame.left;
        return this.mGlobalScale != 1.0f ? f2 * this.mInvGlobalScale : f2;
    }

    public float translateToWindowY(float f) {
        float f2 = f - this.mWindowFrames.mFrame.top;
        return this.mGlobalScale != 1.0f ? f2 * this.mInvGlobalScale : f2;
    }

    public int getRotationAnimationHint() {
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null) {
            return activityRecord.mRotationAnimationHint;
        }
        return -1;
    }

    public boolean commitFinishDrawing(SurfaceControl.Transaction transaction) {
        boolean commitFinishDrawingLocked = this.mWinAnimator.commitFinishDrawingLocked();
        if (commitFinishDrawingLocked) {
            this.mWinAnimator.prepareSurfaceLocked(transaction);
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            commitFinishDrawingLocked |= ((WindowState) this.mChildren.get(size)).commitFinishDrawing(transaction);
        }
        return commitFinishDrawingLocked;
    }

    public boolean performShowLocked() {
        ActivityRecord activityRecord;
        if (!showToCurrentUser()) {
            clearPolicyVisibilityFlag(2);
            return false;
        }
        logPerformShow("performShow on ");
        int i = this.mWinAnimator.mDrawState;
        if ((i == 4 || i == 3) && (activityRecord = this.mActivityRecord) != null) {
            if (this.mAttrs.type != 3) {
                activityRecord.onFirstWindowDrawn(this);
            } else {
                activityRecord.onStartingWindowDrawn();
            }
        }
        if (this.mWinAnimator.mDrawState != 3 || !isReadyForDisplay()) {
            if (this.mWinAnimator.mDrawState == 3) {
                logReadyForDisplay("performShow");
            }
            return false;
        }
        logPerformShow("Showing ");
        this.mWmService.enableScreenIfNeededLocked();
        this.mWinAnimator.applyEnterAnimationLocked();
        this.mWinAnimator.mLastAlpha = -1.0f;
        if (ProtoLogCache.WM_FORCE_DEBUG_ANIM_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_ANIM, -172103155, 0, "performShowLocked: mDrawState=HAS_DRAWN in %s", new Object[]{String.valueOf(this)});
        }
        this.mWinAnimator.mDrawState = 4;
        this.mWmService.scheduleAnimationLocked();
        if (this.mHidden) {
            this.mHidden = false;
            DisplayContent displayContent = getDisplayContent();
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                WindowState windowState = (WindowState) this.mChildren.get(size);
                if (windowState.mWinAnimator.mSurfaceController != null) {
                    windowState.performShowLocked();
                    if (displayContent != null) {
                        displayContent.setLayoutNeeded();
                    }
                }
            }
        }
        return true;
    }

    public WindowInfo getWindowInfo() {
        WindowInfo obtain = WindowInfo.obtain();
        obtain.displayId = getDisplayId();
        obtain.type = this.mAttrs.type;
        obtain.layer = this.mLayer;
        obtain.token = this.mClient.asBinder();
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null) {
            obtain.activityToken = activityRecord.token;
        }
        obtain.accessibilityIdOfAnchor = this.mAttrs.accessibilityIdOfAnchor;
        obtain.focused = isFocused();
        Task task = getTask();
        obtain.inPictureInPicture = task != null && task.inPinnedWindowingMode();
        obtain.taskId = task == null ? -1 : task.mTaskId;
        obtain.hasFlagWatchOutsideTouch = (this.mAttrs.flags & 262144) != 0;
        if (this.mIsChildWindow) {
            obtain.parentToken = getParentWindow().mClient.asBinder();
        }
        int size = this.mChildren.size();
        if (size > 0) {
            if (obtain.childTokens == null) {
                obtain.childTokens = new ArrayList(size);
            }
            for (int i = 0; i < size; i++) {
                obtain.childTokens.add(((WindowState) this.mChildren.get(i)).mClient.asBinder());
            }
        }
        return obtain;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean forAllWindows(ToBooleanFunction toBooleanFunction, boolean z) {
        if (this.mChildren.isEmpty()) {
            return applyInOrderWithImeWindows(toBooleanFunction, z);
        }
        if (z) {
            return forAllWindowTopToBottom(toBooleanFunction);
        }
        return forAllWindowBottomToTop(toBooleanFunction);
    }

    public final boolean forAllWindowBottomToTop(ToBooleanFunction toBooleanFunction) {
        int size = this.mChildren.size();
        WindowState windowState = (WindowState) this.mChildren.get(0);
        int i = 0;
        while (i < size && windowState.mSubLayer < 0) {
            if (windowState.applyInOrderWithImeWindows(toBooleanFunction, false)) {
                return true;
            }
            i++;
            if (i >= size) {
                break;
            }
            windowState = (WindowState) this.mChildren.get(i);
        }
        if (applyInOrderWithImeWindows(toBooleanFunction, false)) {
            return true;
        }
        while (i < size) {
            if (windowState.applyInOrderWithImeWindows(toBooleanFunction, false)) {
                return true;
            }
            i++;
            if (i >= size) {
                break;
            }
            windowState = (WindowState) this.mChildren.get(i);
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public void updateAboveInsetsState(final InsetsState insetsState, SparseArray sparseArray, final ArraySet arraySet) {
        final SparseArray createMergedSparseArray = WindowContainer.createMergedSparseArray(sparseArray, this.mLocalInsetsSources);
        forAllWindows(new Consumer() { // from class: com.android.server.wm.WindowState$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WindowState.lambda$updateAboveInsetsState$6(insetsState, arraySet, createMergedSparseArray, (WindowState) obj);
            }
        }, true);
    }

    public static /* synthetic */ void lambda$updateAboveInsetsState$6(InsetsState insetsState, ArraySet arraySet, SparseArray sparseArray, WindowState windowState) {
        if (!windowState.mAboveInsetsState.equals(insetsState)) {
            windowState.mAboveInsetsState.set(insetsState);
            arraySet.add(windowState);
        }
        if (!sparseArray.contentEquals(windowState.mMergedLocalInsetsSources)) {
            windowState.mMergedLocalInsetsSources = sparseArray;
            arraySet.add(windowState);
        }
        SparseArray sparseArray2 = windowState.mInsetsSourceProviders;
        if (sparseArray2 != null) {
            for (int size = sparseArray2.size() - 1; size >= 0; size--) {
                insetsState.addSource(((InsetsSourceProvider) sparseArray2.valueAt(size)).getSource());
            }
        }
    }

    public final boolean forAllWindowTopToBottom(ToBooleanFunction toBooleanFunction) {
        int size = this.mChildren.size() - 1;
        WindowState windowState = (WindowState) this.mChildren.get(size);
        while (size >= 0 && windowState.mSubLayer >= 0) {
            if (windowState.applyInOrderWithImeWindows(toBooleanFunction, true)) {
                return true;
            }
            size--;
            if (size < 0) {
                break;
            }
            windowState = (WindowState) this.mChildren.get(size);
        }
        if (applyInOrderWithImeWindows(toBooleanFunction, true)) {
            return true;
        }
        while (size >= 0) {
            if (windowState.applyInOrderWithImeWindows(toBooleanFunction, true)) {
                return true;
            }
            size--;
            if (size < 0) {
                return false;
            }
            windowState = (WindowState) this.mChildren.get(size);
        }
        return false;
    }

    public final boolean applyImeWindowsIfNeeded(ToBooleanFunction toBooleanFunction, boolean z) {
        if (!isImeLayeringTarget()) {
            return false;
        }
        WindowState imeInputTarget = getImeInputTarget();
        if (imeInputTarget == null || imeInputTarget.isDrawn() || imeInputTarget.isVisibleRequested() || (getDisplayId() == 0 && getTask() == null && this.mWmService.mAtmService.mDexController.getDexModeLocked() == 2 && this.mWmService.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked())) {
            return this.mDisplayContent.forAllImeWindows(toBooleanFunction, z);
        }
        return false;
    }

    public final boolean applyInOrderWithImeWindows(ToBooleanFunction toBooleanFunction, boolean z) {
        return z ? applyImeWindowsIfNeeded(toBooleanFunction, z) || toBooleanFunction.apply(this) : toBooleanFunction.apply(this) || applyImeWindowsIfNeeded(toBooleanFunction, z);
    }

    @Override // com.android.server.wm.WindowContainer
    public WindowState getWindow(Predicate predicate) {
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

    public boolean isSelfOrAncestorWindowAnimatingExit() {
        while (!this.mAnimatingExit) {
            this = this.getParentWindow();
            if (this == null) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnimationRunningSelfOrParent() {
        return inTransitionSelfOrParent() || isAnimating(0, 16);
    }

    public final boolean shouldFinishAnimatingExit() {
        if (inTransition()) {
            if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -1145384901, 0, (String) null, new Object[]{String.valueOf(this)});
            }
            return false;
        }
        if (!this.mDisplayContent.okToAnimate()) {
            return true;
        }
        if (isAnimationRunningSelfOrParent()) {
            if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -743856570, 0, (String) null, new Object[]{String.valueOf(this)});
            }
            return false;
        }
        if (!this.mDisplayContent.mWallpaperController.isWallpaperTarget(this)) {
            return true;
        }
        if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -208825711, 0, (String) null, new Object[]{String.valueOf(this)});
        }
        return false;
    }

    public void cleanupAnimatingExitWindow() {
        if (this.mAnimatingExit && shouldFinishAnimatingExit()) {
            if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_enabled) {
                ProtoLogImpl.w(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 1087494661, 0, (String) null, new Object[]{String.valueOf(this)});
            }
            onExitAnimationDone();
        }
    }

    public void onExitAnimationDone() {
        if (ProtoLogImpl.isEnabled(ProtoLogGroup.WM_DEBUG_ANIM)) {
            AnimationAdapter animation = this.mSurfaceAnimator.getAnimation();
            StringWriter stringWriter = new StringWriter();
            if (animation != null) {
                animation.dump(new PrintWriter(stringWriter), "");
            }
            if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ANIM, 1164325516, 252, (String) null, new Object[]{String.valueOf(this), Boolean.valueOf(this.mAnimatingExit), Boolean.valueOf(this.mRemoveOnExit), Boolean.valueOf(isAnimating()), String.valueOf(stringWriter)});
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
            if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 1051545910, 12, (String) null, new Object[]{String.valueOf(this), Boolean.valueOf(this.mRemoveOnExit)});
            }
            this.mDestroying = true;
            boolean hasSurface = this.mWinAnimator.hasSurface();
            this.mWinAnimator.hide(getPendingTransaction(), "onExitAnimationDone");
            ActivityRecord activityRecord = this.mActivityRecord;
            if (activityRecord != null) {
                if (this.mAttrs.type == 1) {
                    activityRecord.destroySurfaces();
                } else {
                    destroySurface(false, activityRecord.mAppStopped);
                }
            } else if (hasSurface) {
                this.mWmService.mDestroySurface.add(this);
            }
            this.mAnimatingExit = false;
            if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ANIM, 283489582, 0, (String) null, new Object[]{String.valueOf(this)});
            }
            getDisplayContent().mWallpaperController.hideWallpapers(this);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean handleCompleteDeferredRemoval() {
        if (this.mRemoveOnExit && !isSelfAnimating(0, 16)) {
            this.mRemoveOnExit = false;
            removeImmediately();
        }
        return super.handleCompleteDeferredRemoval();
    }

    public boolean clearAnimatingFlags() {
        boolean z;
        boolean z2 = false;
        if (!this.mRemoveOnExit) {
            if (this.mAnimatingExit) {
                this.mAnimatingExit = false;
                if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ANIM, -1209252064, 0, (String) null, new Object[]{String.valueOf(this)});
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

    public boolean isRtl() {
        return getConfiguration().getLayoutDirection() == 1;
    }

    public void updateReportedVisibility(UpdateReportedVisibilityResults updateReportedVisibilityResults) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowState) this.mChildren.get(size)).updateReportedVisibility(updateReportedVisibilityResults);
        }
        if (this.mAppFreezing || this.mViewVisibility != 0 || this.mAttrs.type == 3 || this.mDestroying) {
            return;
        }
        updateReportedVisibilityResults.numInteresting++;
        if (isDrawn()) {
            updateReportedVisibilityResults.numDrawn++;
            if (!isAnimating(3)) {
                updateReportedVisibilityResults.numVisible++;
            }
            updateReportedVisibilityResults.nowGone = false;
            return;
        }
        if (isAnimating(3)) {
            updateReportedVisibilityResults.nowGone = false;
        }
    }

    public boolean surfaceInsetsChanging() {
        return !this.mLastSurfaceInsets.equals(this.mAttrs.surfaceInsets);
    }

    public int relayoutVisibleWindow(int i) {
        boolean isVisible = isVisible();
        int i2 = i | ((isVisible && isDrawn()) ? 0 : 1);
        if (this.mAnimatingExit) {
            Slog.d(StartingSurfaceController.TAG, "relayoutVisibleWindow: " + this + " mAnimatingExit=true, mRemoveOnExit=" + this.mRemoveOnExit + ", mDestroying=" + this.mDestroying);
            if (isAnimating()) {
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                    cancelAnimation(getPendingTransaction());
                } else {
                    cancelAnimation();
                }
            }
            this.mAnimatingExit = false;
            if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ANIM, -1933723759, 0, (String) null, new Object[]{String.valueOf(this)});
            }
        }
        if (this.mDestroying) {
            this.mDestroying = false;
            this.mWmService.mDestroySurface.remove(this);
        }
        if (!isVisible) {
            this.mWinAnimator.mEnterAnimationPending = true;
        }
        this.mLastVisibleLayoutRotation = getDisplayContent().getRotation();
        this.mWinAnimator.mEnteringAnimation = true;
        Trace.traceBegin(32L, "prepareToDisplay");
        try {
            prepareWindowToDisplayDuringRelayout(isVisible);
            return i2;
        } finally {
            Trace.traceEnd(32L);
        }
    }

    public boolean isLaidOut() {
        return this.mLayoutSeq != -1;
    }

    public void updateLastFrames() {
        WindowFrames windowFrames = this.mWindowFrames;
        windowFrames.mLastFrame.set(windowFrames.mFrame);
        WindowFrames windowFrames2 = this.mWindowFrames;
        windowFrames2.mLastRelFrame.set(windowFrames2.mRelFrame);
    }

    public void onResizeHandled() {
        this.mWindowFrames.onResizeHandled();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isSelfAnimating(int i, int i2) {
        if (this.mControllableInsetProvider != null) {
            return false;
        }
        return super.isSelfAnimating(i, i2);
    }

    public void startAnimation(Animation animation) {
        if (this.mControllableInsetProvider != null) {
            return;
        }
        DisplayInfo displayInfo = getDisplayInfo();
        animation.initialize(this.mWindowFrames.mFrame.width(), this.mWindowFrames.mFrame.height(), displayInfo.appWidth, displayInfo.appHeight);
        animation.restrictDuration(10000L);
        animation.scaleCurrentDuration(this.mWmService.getWindowAnimationScaleLocked());
        startAnimation(getPendingTransaction(), new LocalAnimationAdapter(new WindowAnimationSpec(animation, this.mSurfacePosition, false, DisplayPowerController2.RATE_FROM_DOZE_TO_ON), this.mWmService.mSurfaceAnimationRunner));
        commitPendingTransaction();
    }

    public final void startMoveAnimation(int i, int i2) {
        if (this.mControllableInsetProvider != null) {
            return;
        }
        if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ANIM, -347866078, 0, (String) null, new Object[]{String.valueOf(this)});
        }
        Point point = new Point();
        Point point2 = new Point();
        Rect rect = this.mWindowFrames.mLastFrame;
        transformFrameToSurfacePosition(rect.left, rect.top, point);
        transformFrameToSurfacePosition(i, i2, point2);
        startAnimation(getPendingTransaction(), new LocalAnimationAdapter(new MoveAnimationSpec(point.x, point.y, point2.x, point2.y), this.mWmService.mSurfaceAnimationRunner));
    }

    public final void startAnimation(SurfaceControl.Transaction transaction, AnimationAdapter animationAdapter) {
        ActivityRecord activityRecord;
        if ((this.mIsChildWindow || this.mAttrs.type == 2) && !this.mAttrs.isFullscreen() && (activityRecord = this.mActivityRecord) != null && activityRecord.hasStartingWindow()) {
            Slog.w(StartingSurfaceController.TAG, "Skip child pop-up window animation as starting window showing.");
        } else {
            startAnimation(transaction, animationAdapter, this.mWinAnimator.mLastHidden, 16);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
        super.onAnimationFinished(i, animationAdapter);
        this.mWinAnimator.onAnimationFinished();
    }

    public void getTransformationMatrix(float[] fArr, Matrix matrix) {
        float f = this.mGlobalScale;
        fArr[0] = f;
        fArr[3] = 0.0f;
        fArr[1] = 0.0f;
        fArr[4] = f;
        transformSurfaceInsetsPosition(this.mTmpPoint, this.mAttrs.surfaceInsets);
        Point point = this.mSurfacePosition;
        int i = point.x;
        Point point2 = this.mTmpPoint;
        int i2 = i + point2.x;
        int i3 = point.y + point2.y;
        WindowContainer parent = getParent();
        if (isChildWindow()) {
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

    public final class UpdateReportedVisibilityResults {
        public boolean nowGone = true;
        public int numDrawn;
        public int numInteresting;
        public int numVisible;

        public void reset() {
            this.numInteresting = 0;
            this.numVisible = 0;
            this.numDrawn = 0;
            this.nowGone = true;
        }
    }

    public final class WindowId extends IWindowId.Stub {
        public final WeakReference mOuter;

        public WindowId(WindowState windowState) {
            this.mOuter = new WeakReference(windowState);
        }

        public void registerFocusObserver(IWindowFocusObserver iWindowFocusObserver) {
            WindowState windowState = (WindowState) this.mOuter.get();
            if (windowState != null) {
                windowState.registerFocusObserver(iWindowFocusObserver);
            }
        }

        public void unregisterFocusObserver(IWindowFocusObserver iWindowFocusObserver) {
            WindowState windowState = (WindowState) this.mOuter.get();
            if (windowState != null) {
                windowState.unregisterFocusObserver(iWindowFocusObserver);
            }
        }

        public boolean isFocused() {
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
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean shouldMagnify() {
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        int i = layoutParams.type;
        return (i == 2039 || i == 2011 || i == 2012 || i == 2027 || i == 2019 || i == 2024 || i == 1100 || (layoutParams.privateFlags & 4194304) != 0) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer
    public SurfaceSession getSession() {
        SurfaceSession surfaceSession = this.mSession.mSurfaceSession;
        return surfaceSession != null ? surfaceSession : getParent().getSession();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean needsZBoost() {
        ActivityRecord activityRecord;
        InsetsControlTarget imeTarget = getDisplayContent().getImeTarget(0);
        if (!this.mIsImWindow || imeTarget == null || (activityRecord = imeTarget.getWindow().mActivityRecord) == null) {
            return false;
        }
        return activityRecord.needsZBoost();
    }

    public final boolean isStartingWindowAssociatedToTask() {
        StartingData startingData = this.mStartingData;
        return (startingData == null || startingData.mAssociatedTask == null) ? false : true;
    }

    public final void applyDims() {
        if (this.mRequestedHeight == 0 || this.mRequestedWidth == 0) {
            return;
        }
        if (((this.mAttrs.flags & 2) != 0 || shouldDrawBlurBehind()) && this.mToken.isVisibleRequested() && isVisibleNow() && !this.mHidden && this.mTransitionController.canApplyDim(getTask())) {
            if (inFreeformWindowingMode() ? this.mWmService.mAtmService.mFreeformController.canApplyDimsLocked(this) : true) {
                this.mIsDimming = true;
                WindowManager.LayoutParams layoutParams = this.mAttrs;
                if ((layoutParams.samsungFlags & 64) != 0) {
                    return;
                }
                getDimmer().dimBelow(this, (layoutParams.flags & 2) != 0 ? layoutParams.dimAmount : DisplayPowerController2.RATE_FROM_DOZE_TO_ON, shouldDrawBlurBehind() ? this.mAttrs.getBlurBehindRadius() : 0);
            }
        }
    }

    public final boolean shouldDrawBlurBehind() {
        return (this.mAttrs.flags & 4) != 0 && this.mWmService.mBlurController.getBlurEnabled();
    }

    public void updateFrameRateSelectionPriorityIfNeeded() {
        RefreshRatePolicy refreshRatePolicy = getDisplayContent().getDisplayPolicy().getRefreshRatePolicy();
        int calculatePriority = refreshRatePolicy.calculatePriority(this);
        if (this.mFrameRateSelectionPriority != calculatePriority) {
            this.mFrameRateSelectionPriority = calculatePriority;
            getPendingTransaction().setFrameRateSelectionPriority(this.mSurfaceControl, this.mFrameRateSelectionPriority);
        }
        if (refreshRatePolicy.updateFrameRateVote(this)) {
            SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
            SurfaceControl surfaceControl = this.mSurfaceControl;
            RefreshRatePolicy.FrameRateVote frameRateVote = this.mFrameRateVote;
            pendingTransaction.setFrameRate(surfaceControl, frameRateVote.mRefreshRate, frameRateVote.mCompatibility, 1);
        }
    }

    public final void updateScaleIfNeeded() {
        if (isVisibleRequested() || (this.mIsWallpaper && this.mToken.isVisible())) {
            float f = this.mGlobalScale;
            WindowState parentWindow = getParentWindow();
            if (parentWindow != null) {
                f *= parentWindow.mInvGlobalScale;
            }
            float f2 = this.mHScale * f;
            float f3 = this.mWallpaperScale;
            float f4 = f2 * f3;
            float f5 = f3 * this.mVScale * f;
            if (f == 1.0f && this.mLastHScale == f4 && this.mLastVScale == f5) {
                return;
            }
            getSyncTransaction().setMatrix(this.mSurfaceControl, f4, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, f5);
            this.mLastHScale = f4;
            this.mLastVScale = f5;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void prepareSurfaces() {
        this.mIsDimming = false;
        Dimmer dimmer = this.mPopOverDimmer;
        if (dimmer != null) {
            dimmer.resetDimStates();
            if (this.mPopOverDimmer != getDimmer()) {
                clearPopOverDimmer();
            }
        }
        if (this.mHasSurface) {
            applyDims();
            updateSurfacePositionNonOrganized();
            updateFrameRateSelectionPriorityIfNeeded();
            updateScaleIfNeeded();
            this.mWinAnimator.prepareSurfaceLocked(getSyncTransaction());
        }
        super.prepareSurfaces();
        if (this.mPopOverDimmer != null) {
            updatePopOverDimmer();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void updateSurfacePosition(SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl == null) {
            return;
        }
        if ((this.mWmService.mWindowPlacerLocked.isLayoutDeferred() || isGoneForLayout()) && !this.mSurfacePlacementNeeded) {
            return;
        }
        boolean z = false;
        this.mSurfacePlacementNeeded = false;
        Rect rect = this.mWindowFrames.mFrame;
        transformFrameToSurfacePosition(rect.left, rect.top, this.mSurfacePosition);
        if (this.mWallpaperScale != 1.0f) {
            Rect parentFrame = getParentFrame();
            Matrix matrix = this.mTmpMatrix;
            matrix.setTranslate(this.mXOffset, this.mYOffset);
            float f = this.mWallpaperScale;
            matrix.postScale(f, f, parentFrame.exactCenterX(), parentFrame.exactCenterY());
            matrix.getValues(this.mTmpMatrixArray);
            this.mSurfacePosition.offset(Math.round(this.mTmpMatrixArray[2]), Math.round(this.mTmpMatrixArray[5]));
        } else {
            this.mSurfacePosition.offset(this.mXOffset, this.mYOffset);
        }
        if (this.mSurfaceAnimator.hasLeash() || this.mPendingSeamlessRotate != null) {
            return;
        }
        if ((CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && this.mHasSeamlessOperation) || this.mLastSurfacePosition.equals(this.mSurfacePosition)) {
            return;
        }
        boolean isFrameSizeChangeReported = this.mWindowFrames.isFrameSizeChangeReported();
        boolean surfaceInsetsChanging = surfaceInsetsChanging();
        boolean z2 = isFrameSizeChangeReported || surfaceInsetsChanging;
        Point point = this.mLastSurfacePosition;
        Point point2 = this.mSurfacePosition;
        point.set(point2.x, point2.y);
        if (surfaceInsetsChanging) {
            this.mLastSurfaceInsets.set(this.mAttrs.surfaceInsets);
        }
        boolean z3 = z2 && this.mWinAnimator.getShown() && !canPlayMoveAnimation() && okToDisplay() && this.mSyncState == 0;
        ActivityRecord activityRecord = getActivityRecord();
        if (activityRecord != null && activityRecord.areBoundsLetterboxed() && activityRecord.mLetterboxUiController.getIsRelaunchingAfterRequestedOrientationChanged()) {
            z = true;
        }
        if (z3 || z) {
            applyWithNextDraw(this.mSetSurfacePositionConsumer);
        } else {
            this.mSetSurfacePositionConsumer.accept(transaction);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void transformFrameToSurfacePosition(int i, int i2, Point point) {
        Rect bounds;
        point.set(i, i2);
        WindowContainer parent = getParent();
        if (isChildWindow()) {
            WindowState parentWindow = getParentWindow();
            Rect rect = parentWindow.mWindowFrames.mFrame;
            point.offset(-rect.left, -rect.top);
            float f = this.mInvGlobalScale;
            if (f != 1.0f) {
                point.x = (int) ((point.x * f) + 0.5f);
                point.y = (int) ((point.y * f) + 0.5f);
            }
            transformSurfaceInsetsPosition(this.mTmpPoint, parentWindow.mAttrs.surfaceInsets);
            Point point2 = this.mTmpPoint;
            point.offset(point2.x, point2.y);
        } else if (parent != null) {
            if (isStartingWindowAssociatedToTask()) {
                bounds = this.mStartingData.mAssociatedTask.getBounds();
            } else {
                bounds = parent.getBounds();
            }
            point.offset(-bounds.left, -bounds.top);
        }
        transformSurfaceInsetsPosition(this.mTmpPoint, this.mAttrs.surfaceInsets);
        Point point3 = this.mTmpPoint;
        point.offset(-point3.x, -point3.y);
        point.x += this.mSurfaceTranslationX;
        point.y += this.mSurfaceTranslationY;
    }

    public final void transformSurfaceInsetsPosition(Point point, Rect rect) {
        float f = this.mGlobalScale;
        if (f == 1.0f || this.mIsChildWindow) {
            point.x = rect.left;
            point.y = rect.top;
        } else {
            point.x = (int) ((rect.left * f) + 0.5f);
            point.y = (int) ((rect.top * f) + 0.5f);
        }
    }

    public boolean needsRelativeLayeringToIme() {
        WindowState imeLayeringTarget;
        if (this.mDisplayContent.shouldImeAttachedToApp() || !getDisplayContent().getImeContainer().isVisible()) {
            return false;
        }
        if (isChildWindow()) {
            if (getParentWindow().isImeLayeringTarget() && !this.mWmService.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked()) {
                return true;
            }
        } else if (this.mActivityRecord != null) {
            WindowState imeLayeringTarget2 = getImeLayeringTarget();
            return (imeLayeringTarget2 == null || imeLayeringTarget2 == this || imeLayeringTarget2.mToken != this.mToken || this.mAttrs.type == 3 || getParent() == null || imeLayeringTarget2.compareTo((WindowContainer) this) > 0) ? false : true;
        }
        WindowState imeLayeringTarget3 = getImeLayeringTarget();
        if (imeLayeringTarget3 != null && imeLayeringTarget3.inMultiWindowMode() && this.mActivityRecord == null) {
            WindowManager.LayoutParams layoutParams = this.mAttrs;
            if (layoutParams.type == 2038 && (layoutParams.flags & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) != 0) {
                return true;
            }
        }
        return (this.mAttrs.flags & 131080) == 131072 && isTrustedOverlay() && canAddInternalSystemWindow() && (imeLayeringTarget = getImeLayeringTarget()) != null && imeLayeringTarget != this && imeLayeringTarget.compareTo((WindowContainer) this) <= 0 && getDisplayContent().getImeContainer().compareTo((WindowContainer) this) >= 0;
    }

    @Override // com.android.server.wm.InputTarget
    public InsetsControlTarget getImeControlTarget() {
        return getDisplayContent().getImeHostOrFallback(this);
    }

    @Override // com.android.server.wm.WindowContainer
    public void assignLayer(SurfaceControl.Transaction transaction, int i) {
        WindowState windowEavesdropDragEvent;
        if (this.mStartingData != null) {
            if (CoreRune.MW_CAPTION_SHELL_BUG_FIX && inFreeformWindowingMode() && getTask() != null) {
                assignRelativeLayer(transaction, getTask().getSurfaceControl(), Integer.MAX_VALUE);
                return;
            } else {
                setLayer(transaction, Integer.MAX_VALUE);
                return;
            }
        }
        if (this.mAttrs.type == 2281 && (windowEavesdropDragEvent = getDisplayContent().getWindowEavesdropDragEvent()) != null && windowEavesdropDragEvent.getSurfaceControl() != null && windowEavesdropDragEvent.isVisible()) {
            assignRelativeLayer(transaction, windowEavesdropDragEvent.getSurfaceControl(), -1);
        } else if (needsRelativeLayeringToIme()) {
            getDisplayContent().assignRelativeLayerForImeTargetChild(transaction, this);
        } else {
            super.assignLayer(transaction, i);
        }
    }

    public boolean isDimming() {
        return this.mIsDimming;
    }

    @Override // com.android.server.wm.WindowContainer
    public void reparentSurfaceControl(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (isStartingWindowAssociatedToTask()) {
            return;
        }
        super.reparentSurfaceControl(transaction, surfaceControl);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public SurfaceControl getAnimationLeashParent() {
        if (isStartingWindowAssociatedToTask()) {
            return this.mStartingData.mAssociatedTask.mSurfaceControl;
        }
        return super.getAnimationLeashParent();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashCreated(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        super.onAnimationLeashCreated(transaction, surfaceControl);
        if (isStartingWindowAssociatedToTask()) {
            transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void assignChildLayers(SurfaceControl.Transaction transaction) {
        int i = 2;
        for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
            WindowState windowState = (WindowState) this.mChildren.get(i2);
            int i3 = windowState.mAttrs.type;
            if (i3 == 1001) {
                if (this.mWinAnimator.hasSurface()) {
                    windowState.assignRelativeLayer(transaction, this.mWinAnimator.mSurfaceController.mSurfaceControl, -2);
                } else {
                    windowState.assignLayer(transaction, -2);
                }
            } else if (i3 == 1004) {
                if (this.mWinAnimator.hasSurface()) {
                    windowState.assignRelativeLayer(transaction, this.mWinAnimator.mSurfaceController.mSurfaceControl, -1);
                } else {
                    windowState.assignLayer(transaction, -1);
                }
            } else {
                windowState.assignLayer(transaction, i);
            }
            windowState.assignChildLayers(transaction);
            i++;
        }
    }

    public void updateTapExcludeRegion(Region region) {
        DisplayContent displayContent = getDisplayContent();
        if (displayContent == null) {
            throw new IllegalStateException("Trying to update window not attached to any display.");
        }
        if (region == null || region.isEmpty()) {
            this.mTapExcludeRegion.setEmpty();
            displayContent.mTapExcludeProvidingWindows.remove(this);
        } else {
            this.mTapExcludeRegion.set(region);
            displayContent.mTapExcludeProvidingWindows.add(this);
        }
        displayContent.updateTouchExcludeRegion();
        displayContent.getInputMonitor().updateInputWindowsLw(true);
    }

    public void getTapExcludeRegion(Region region) {
        this.mTmpRect.set(this.mWindowFrames.mFrame);
        this.mTmpRect.offsetTo(0, 0);
        region.set(this.mTapExcludeRegion);
        region.op(this.mTmpRect, Region.Op.INTERSECT);
        Rect rect = this.mWindowFrames.mFrame;
        region.translate(rect.left, rect.top);
    }

    public boolean isImeLayeringTarget() {
        return getDisplayContent().getImeTarget(0) == this;
    }

    public boolean isImeOverlayLayeringTarget() {
        return isImeLayeringTarget() && (this.mAttrs.flags & 131080) != 0;
    }

    public WindowState getImeLayeringTarget() {
        InsetsControlTarget imeTarget = getDisplayContent().getImeTarget(0);
        if (imeTarget != null) {
            return imeTarget.getWindow();
        }
        return null;
    }

    public WindowState getImeInputTarget() {
        InputTarget imeInputTarget = this.mDisplayContent.getImeInputTarget();
        if (imeInputTarget != null) {
            return imeInputTarget.getWindowState();
        }
        return null;
    }

    public void forceReportingResized() {
        this.mWindowFrames.forceReportingResized();
    }

    public WindowFrames getWindowFrames() {
        return this.mWindowFrames;
    }

    public void resetContentChanged() {
        this.mWindowFrames.setContentChanged(false);
    }

    public void resetEffects() {
        this.mLastBlurRadius = 0;
    }

    public void applyBlurEffectInTransaction(SurfaceControl.Transaction transaction) {
        if (this.mWinAnimator == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        int legacyBlurRadius = (layoutParams.flags & 2) != 0 && (layoutParams.samsungFlags & 64) != 0 ? getLegacyBlurRadius(layoutParams.dimAmount) : 0;
        if (this.mLastBlurRadius == legacyBlurRadius) {
            return;
        }
        long j = this.mAttrs.dimDuration;
        if (j == -1) {
            j = 200;
        }
        SurfaceEffects.Effect.Builder pixelEffectType = SurfaceEffects.newBuilder().setPixelEffectType(SurfaceEffects.PixEffectType.BLUR);
        WindowManager.LayoutParams layoutParams2 = this.mAttrs;
        if (layoutParams2.height == -2 || layoutParams2.width == -2) {
            pixelEffectType.makeFullscreen();
        }
        boolean z = CoreRune.IS_DEBUG_LEVEL_MID && getAttrs().type == 2011 && getDisplayContent().isDefaultDisplay;
        if (legacyBlurRadius > 0) {
            pixelEffectType.setAnimationMode(SurfaceEffects.AnimationMode.ONCE_STAY_END).addPixAnimation(SurfaceEffects.AnimParam.BLUR_RADIUS, 0, this.mLastBlurRadius, SurfaceEffects.InterpMode.HOLD).addPixAnimation(SurfaceEffects.AnimParam.BLUR_RADIUS, (int) j, legacyBlurRadius, SurfaceEffects.InterpMode.SMOOTH_IN);
            if (z) {
                Slog.d(StartingSurfaceController.TAG, "applyBlurEffectInTransaction: Set ONCE_STAY_END blurRadius=" + legacyBlurRadius);
            }
        } else {
            pixelEffectType.setAnimationMode(SurfaceEffects.AnimationMode.ONCE_DESTROY).addPixAnimation(SurfaceEffects.AnimParam.BLUR_RADIUS, 0, this.mLastBlurRadius, SurfaceEffects.InterpMode.HOLD).addPixAnimation(SurfaceEffects.AnimParam.BLUR_RADIUS, (int) j, legacyBlurRadius, SurfaceEffects.InterpMode.SMOOTH_OUT);
            if (z) {
                Slog.d(StartingSurfaceController.TAG, "applyBlurEffectInTransaction: Set ONCE_DESTROY blurRadius=" + legacyBlurRadius);
            }
        }
        if (CoreRune.SAFE_DEBUG) {
            Slog.d(StartingSurfaceController.TAG, "applyBlurEffectInTransaction, duration=" + j + ", blurRadius=" + legacyBlurRadius + ", mLastBlurRadius=" + this.mLastBlurRadius + ", dimAmount=" + this.mAttrs.dimAmount + ", win=" + this);
        }
        this.mWinAnimator.mSurfaceController.startSurfaceAnimationInTransaction(transaction, pixelEffectType.build().getBytes());
        this.mLastBlurRadius = legacyBlurRadius;
    }

    public final class MoveAnimationSpec implements LocalAnimationAdapter.AnimationSpec {
        public final long mDuration;
        public Point mFrom;
        public Interpolator mInterpolator;
        public Point mTo;

        public MoveAnimationSpec(int i, int i2, int i3, int i4) {
            this.mFrom = new Point();
            this.mTo = new Point();
            Animation loadAnimation = AnimationUtils.loadAnimation(WindowState.this.mContext, (CoreRune.FW_IMPROVED_MOVED_ANIMATION_FOR_IME || CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_ANIMATION) ? WindowState.this.getSamsungAnimResId() : 17432968);
            this.mDuration = (long) (loadAnimation.computeDurationHint() * WindowState.this.mWmService.getWindowAnimationScaleLocked());
            this.mInterpolator = loadAnimation.getInterpolator();
            this.mFrom.set(i, i2);
            this.mTo.set(i3, i4);
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public long getDuration() {
            return this.mDuration;
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public void apply(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, long j) {
            float interpolation = this.mInterpolator.getInterpolation(getFraction(j));
            Point point = this.mFrom;
            int i = point.x;
            Point point2 = this.mTo;
            transaction.setPosition(surfaceControl, i + ((point2.x - i) * interpolation), point.y + ((point2.y - r7) * interpolation));
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public void dump(PrintWriter printWriter, String str) {
            printWriter.println(str + "from=" + this.mFrom + " to=" + this.mTo + " duration=" + this.mDuration);
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public void dumpDebugInner(ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268034L);
            GraphicsProtos.dumpPointProto(this.mFrom, protoOutputStream, 1146756268033L);
            GraphicsProtos.dumpPointProto(this.mTo, protoOutputStream, 1146756268034L);
            protoOutputStream.write(1112396529667L, this.mDuration);
            protoOutputStream.end(start);
        }
    }

    public KeyInterceptionInfo getKeyInterceptionInfo() {
        KeyInterceptionInfo keyInterceptionInfo = this.mKeyInterceptionInfo;
        if (keyInterceptionInfo == null || keyInterceptionInfo.layoutParamsPrivateFlags != getAttrs().privateFlags || this.mKeyInterceptionInfo.layoutParamsType != getAttrs().type || this.mKeyInterceptionInfo.windowTitle != getWindowTag()) {
            this.mKeyInterceptionInfo = new KeyInterceptionInfo(getAttrs().type, getAttrs().privateFlags, getWindowTag().toString());
        }
        return this.mKeyInterceptionInfo;
    }

    @Override // com.android.server.wm.WindowContainer
    public void getAnimationFrames(Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        if (inFreeformWindowingMode()) {
            rect.set(getFrame());
        } else if (areAppWindowBoundsLetterboxed() || this.mToken.isFixedRotationTransforming()) {
            rect.set(getTask().getBounds());
        } else {
            rect.set(getParentFrame());
        }
        rect4.set(getAttrs().surfaceInsets);
        InsetsState insetsStateWithVisibilityOverride = getInsetsStateWithVisibilityOverride();
        rect2.set(insetsStateWithVisibilityOverride.calculateInsets(rect, WindowInsets.Type.systemBars(), false).toRect());
        rect3.set(insetsStateWithVisibilityOverride.calculateInsets(rect, WindowInsets.Type.systemBars(), true).toRect());
    }

    public void setViewVisibility(int i) {
        this.mViewVisibility = i;
        adjustImeParamsForDex(i == 0);
    }

    public SurfaceControl getClientViewRootSurface() {
        return this.mWinAnimator.getSurfaceControl();
    }

    public final void dropBufferFrom(SurfaceControl.Transaction transaction) {
        SurfaceControl clientViewRootSurface = getClientViewRootSurface();
        if (clientViewRootSurface == null) {
            return;
        }
        if (transaction != null) {
            Slog.d(StartingSurfaceController.TAG, "dropBufferFrom, t=" + transaction.mDebugName + ", win=" + this + ", caller=" + Debug.getCallers(3));
        }
        transaction.unsetBuffer(clientViewRootSurface);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean shouldUpdateSyncOnReparent() {
        return (this.mSyncState == 0 || this.mLastConfigReportedToClient) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean prepareSync() {
        if (!this.mDrawHandlers.isEmpty()) {
            Slog.w(StartingSurfaceController.TAG, "prepareSync with mDrawHandlers, " + this + ", " + Debug.getCallers(8));
        }
        if (!super.prepareSync()) {
            return false;
        }
        if (this.mIsWallpaper && (!CoreRune.FW_LOCK_ONLY_LIVE_WALLPAPER || !this.mTransitionController.isCollecting() || !this.mTransitionController.getCollectingTransition().isInKeyguardTransition())) {
            return false;
        }
        if (CoreRune.MW_EMBED_ACTIVITY && isSplitEmbedded() && this.mStartingData == null) {
            Task task = getTask();
            ActivityRecord activityRecord = getActivityRecord();
            if (task != null && task.mSharedStartingData != null && activityRecord != null && activityRecord.mStartingData == null) {
                return false;
            }
        }
        this.mSyncState = 1;
        if (this.mPrepareSyncSeqId > 0) {
            if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, -417730399, 0, (String) null, new Object[]{String.valueOf(this)});
            }
            dropBufferFrom(this.mSyncTransaction);
        }
        this.mSyncSeqId++;
        if (getSyncMethod() == 1) {
            this.mPrepareSyncSeqId = this.mSyncSeqId;
            requestRedrawForSync();
        } else if (this.mHasSurface && this.mWinAnimator.mDrawState != 1) {
            requestRedrawForSync();
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            Slog.d(StartingSurfaceController.TAG, "prepareSync <SYNC_STATE_WAITING_FOR_DRAW>, mPrepareSyncSeqId=" + this.mPrepareSyncSeqId + ", win=" + this);
        }
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isSyncFinished(BLASTSyncEngine.SyncGroup syncGroup) {
        Transition collectingTransition;
        int i;
        AsyncRotationController asyncRotationController;
        if (!isVisibleRequested() || isFullyTransparent()) {
            return true;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && this.mActivityRecord == null && getDisplayContent() != null && (asyncRotationController = getDisplayContent().getAsyncRotationController()) != null && asyncRotationController.hasFadeOperation(this.mToken)) {
            if (CoreRune.SAFE_DEBUG) {
                Slog.d(StartingSurfaceController.TAG, "onSyncFinishedDrawing by async rotation, w=" + this);
            }
            onSyncFinishedDrawing();
            return true;
        }
        if (CoreRune.FW_WAIT_TO_HANDLE_RESIZING_FROM_CLIENT && this.mWaitToHandleResizing) {
            return false;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && this.mActivityRecord != null && (i = this.mAttrs.type) != 1 && i != 3 && this.mViewVisibility != 0) {
            return true;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_RAPID_RECENTS_TRANSIT && this.mActivityRecord != null && showWallpaper() && (collectingTransition = this.mTransitionController.getCollectingTransition()) != null && collectingTransition.isTransientLaunch(this.mActivityRecord)) {
            WallpaperController wallpaperController = this.mDisplayContent.mWallpaperController;
            if (wallpaperController.getWallpaperTarget() != null && wallpaperController.wallpaperTransitionReady()) {
                if (CoreRune.SAFE_DEBUG) {
                    Slog.d(StartingSurfaceController.TAG, "onSyncFinishedDrawing by transient-launch, w=" + this);
                }
                onSyncFinishedDrawing();
            }
        }
        if (this.mSyncState == 1 && this.mLastConfigReportedToClient && isDrawn()) {
            onSyncFinishedDrawing();
        } else if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && this.mActivityRecord == null && this.mToken.asWallpaperToken() == null && this.mSyncState == 1 && this.mWinAnimator.mDrawState == 0) {
            onSyncFinishedDrawing();
        }
        return super.isSyncFinished(syncGroup);
    }

    @Override // com.android.server.wm.WindowContainer
    public void finishSync(SurfaceControl.Transaction transaction, BLASTSyncEngine.SyncGroup syncGroup, boolean z) {
        BLASTSyncEngine.SyncGroup syncGroup2 = getSyncGroup();
        if (syncGroup2 == null || syncGroup == syncGroup2) {
            this.mPrepareSyncSeqId = 0;
            if (z) {
                dropBufferFrom(this.mSyncTransaction);
            }
            super.finishSync(transaction, syncGroup, z);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0125 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean finishDrawing(SurfaceControl.Transaction transaction, int i) {
        boolean z;
        boolean z2;
        boolean z3 = (transaction == null || TextUtils.isEmpty(transaction.mDebugName)) ? false : true;
        if (this.mOrientationChangeRedrawRequestTime > 0) {
            Slog.i(StartingSurfaceController.TAG, "finishDrawing of orientation change: " + this + " " + (SystemClock.elapsedRealtime() - this.mOrientationChangeRedrawRequestTime) + "ms");
            this.mOrientationChangeRedrawRequestTime = 0L;
        } else {
            ActivityRecord activityRecord = this.mActivityRecord;
            if (activityRecord != null && activityRecord.mRelaunchStartTime != 0 && activityRecord.findMainWindow(false) == this) {
                Slog.i(StartingSurfaceController.TAG, "finishDrawing of relaunch: " + this + " " + (SystemClock.elapsedRealtime() - this.mActivityRecord.mRelaunchStartTime) + "ms");
                this.mActivityRecord.finishOrAbortReplacingWindow();
            }
        }
        if (this.mActivityRecord != null && this.mAttrs.type == 3) {
            this.mWmService.mAtmService.mTaskSupervisor.getActivityMetricsLogger().notifyStartingWindowDrawn(this.mActivityRecord);
        }
        int i2 = this.mPrepareSyncSeqId;
        boolean z4 = i2 > 0;
        boolean z5 = z4 && i2 > i;
        if (z5 && transaction != null) {
            if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, -4263657, 5, (String) null, new Object[]{Long.valueOf(i), Long.valueOf(i2), String.valueOf(this)});
            }
            dropBufferFrom(transaction);
        }
        boolean executeDrawHandlers = executeDrawHandlers(transaction, i);
        AsyncRotationController asyncRotationController = this.mDisplayContent.getAsyncRotationController();
        if (asyncRotationController == null || !asyncRotationController.handleFinishDrawing(this, transaction)) {
            if (z4) {
                z = !z5 ? onSyncFinishedDrawing() : false;
                if (transaction != null) {
                    this.mSyncTransaction.merge(transaction);
                    z2 = false;
                }
            } else {
                if (!useBLASTSync()) {
                    z = false;
                    z2 = false;
                    if (z3) {
                        logTransactionTracker("finishDrawing", transaction);
                    }
                    return z2 && (executeDrawHandlers || (this.mWinAnimator.finishDrawingLocked(transaction) || z));
                }
                z = onSyncFinishedDrawing();
            }
            z2 = false;
            if (z3) {
            }
            if (z2) {
            }
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && this.mDisplayContent.getAsyncRotationController() == null) {
            z = false;
            z2 = false;
        } else {
            z2 = true;
            z = false;
        }
        transaction = null;
        if (z3) {
        }
        if (z2) {
        }
    }

    public void logTransactionTracker(String str, SurfaceControl.Transaction transaction) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(", ");
        sb.append("postDrawTransaction=");
        sb.append(transaction != null ? transaction.mDebugName : "null");
        sb.append(", ");
        sb.append("syncTransaction=");
        SurfaceControl.Transaction transaction2 = this.mSyncTransaction;
        sb.append(transaction2 != null ? transaction2.mDebugName : "null");
        SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
        sb.append(", ");
        sb.append("pendingTransaction=");
        sb.append(pendingTransaction != null ? pendingTransaction.mDebugName : "null");
        sb.append(", w=");
        sb.append(this);
        Slog.d(StartingSurfaceController.TAG, sb.toString());
    }

    public void immediatelyNotifyBlastSync() {
        finishDrawing(null, Integer.MAX_VALUE);
        this.mWmService.f1749mH.removeMessages(64, this);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean fillsParent() {
        return this.mAttrs.type == 3;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean showWallpaper() {
        if (!isVisibleRequested() || inMultiWindowMode()) {
            return false;
        }
        return hasWallpaper();
    }

    public boolean hasWallpaper() {
        return (this.mAttrs.flags & 1048576) != 0 || hasWallpaperForLetterboxBackground();
    }

    public boolean hasWallpaperForLetterboxBackground() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && activityRecord.hasWallpaperBackgroundForLetterbox();
    }

    public final boolean shouldSendRedrawForSync() {
        if (this.mRedrawForSyncReported) {
            return false;
        }
        if (!this.mInRelayout || (this.mPrepareSyncSeqId <= 0 && !(this.mViewVisibility == 0 && this.mWinAnimator.mDrawState == 1))) {
            return useBLASTSync();
        }
        return false;
    }

    public int getSyncMethod() {
        BLASTSyncEngine.SyncGroup syncGroup = getSyncGroup();
        if (syncGroup == null) {
            return 0;
        }
        int i = this.mSyncMethodOverride;
        return i != -1 ? i : syncGroup.mSyncMethod;
    }

    public boolean shouldSyncWithBuffers() {
        return !this.mDrawHandlers.isEmpty() || getSyncMethod() == 1;
    }

    public void requestRedrawForSync() {
        this.mRedrawForSyncReported = false;
    }

    public void setTspDeadzone(Bundle bundle) {
        this.mTspDeadzone = bundle;
    }

    public Bundle getTspDeadzone() {
        return this.mTspDeadzone;
    }

    public void clearTspDeadzone() {
        this.mTspDeadzone = null;
    }

    public void setTspNoteMode(boolean z) {
        this.mIsTspNoteMode = z;
    }

    public boolean isTspNoteMode() {
        return this.mIsTspNoteMode;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean useBLASTSync() {
        return super.useBLASTSync() || this.mDrawHandlers.size() != 0;
    }

    public void applyWithNextDraw(Consumer consumer) {
        applyWithNextDraw(consumer, 0);
    }

    public void applyWithNextDraw(Consumer consumer, int i) {
        if (this.mSyncState != 0) {
            Slog.w(StartingSurfaceController.TAG, "applyWithNextDraw with mSyncState=" + this.mSyncState + ", " + this + ", " + Debug.getCallers(8));
        }
        int i2 = this.mSyncSeqId + 1;
        this.mSyncSeqId = i2;
        this.mDrawHandlers.add(new DrawHandler(i2, consumer));
        ((DrawHandler) this.mDrawHandlers.get(r5.size() - 1)).mType = i;
        Slog.d(StartingSurfaceController.TAG, "applyWithNextDraw, mSyncSeqId=" + this.mSyncSeqId + ", win=" + this + ", caller=" + Debug.getCallers(6));
        requestRedrawForSync();
        this.mWmService.f1749mH.sendNewMessageDelayed(64, this, 5000L);
    }

    public void forceExecuteDrawHandlers(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mDrawHandlers.size(); i2++) {
            DrawHandler drawHandler = (DrawHandler) this.mDrawHandlers.get(i2);
            if (drawHandler.mType == i) {
                drawHandler.mConsumer.accept(this.mTmpTransaction);
                arrayList.add(drawHandler);
                Slog.d(StartingSurfaceController.TAG, "forceExecuteDrawHandlers: win=" + this + ", handler=" + drawHandler);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            this.mDrawHandlers.remove((DrawHandler) arrayList.get(i3));
        }
        if (this.mDrawHandlers.isEmpty()) {
            this.mWmService.f1749mH.removeMessages(64, this);
        }
        this.mTmpTransaction.apply();
    }

    public boolean executeDrawHandlers(SurfaceControl.Transaction transaction, int i) {
        boolean z;
        if (transaction == null) {
            transaction = this.mTmpTransaction;
            z = true;
        } else {
            z = false;
        }
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        for (int i2 = 0; i2 < this.mDrawHandlers.size(); i2++) {
            DrawHandler drawHandler = (DrawHandler) this.mDrawHandlers.get(i2);
            if (drawHandler.mSeqId <= i) {
                drawHandler.mConsumer.accept(transaction);
                arrayList.add(drawHandler);
                z2 = true;
            }
            Slog.d(StartingSurfaceController.TAG, "executeDrawHandlers, seqId=" + i + ", h.mSeqId=" + drawHandler.mSeqId + ", hadHandlers=" + z2 + ", win=" + this);
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            this.mDrawHandlers.remove((DrawHandler) arrayList.get(i3));
        }
        if (z2) {
            this.mWmService.f1749mH.removeMessages(64, this);
        }
        if (z) {
            transaction.apply();
        }
        return z2;
    }

    public void setSurfaceTranslationX(int i) {
        this.mSurfaceTranslationX = i;
    }

    public void setSurfaceTranslationY(int i) {
        this.mSurfaceTranslationY = i;
    }

    @Override // com.android.server.wm.WindowContainer
    public int getWindowType() {
        return this.mAttrs.type;
    }

    public void markRedrawForSyncReported() {
        this.mRedrawForSyncReported = true;
    }

    public boolean setWallpaperOffset(int i, int i2, float f) {
        if (this.mXOffset == i && this.mYOffset == i2 && Float.compare(this.mWallpaperScale, f) == 0) {
            return false;
        }
        this.mXOffset = i;
        this.mYOffset = i2;
        this.mWallpaperScale = f;
        scheduleAnimation();
        return true;
    }

    public final RestrictionPolicyInternal getRestrictionPolicy() {
        if (this.mRestrictionPolicy == null) {
            this.mRestrictionPolicy = (RestrictionPolicyInternal) LocalServices.getService(RestrictionPolicyInternal.class);
        }
        return this.mRestrictionPolicy;
    }

    public boolean isTrustedOverlay() {
        return this.mInputWindowHandle.isTrustedOverlay();
    }

    @Override // com.android.server.wm.InputTarget
    public boolean receiveFocusFromTapOutside() {
        return canReceiveKeys(true);
    }

    @Override // com.android.server.wm.InputTarget
    public void handleTapOutsideFocusInsideSelf() {
        if (this.mWmService.mAtmService.mDexController.getDexModeLocked() != 2 || !DexController.isDisplayFocusChangeExcludeWindow(this.mAttrs.type)) {
            this.mWmService.moveDisplayToTopInternal(getDisplayId());
        }
        this.mWmService.handleTaskFocusChange(getTask(), this.mActivityRecord);
    }

    public void clearClientTouchableRegion() {
        this.mTouchableInsets = 0;
        this.mGivenTouchableRegion.setEmpty();
    }

    @Override // com.android.server.wm.InputTarget
    public boolean shouldControlIme() {
        if (CoreRune.MW_EMBED_ACTIVITY && isSplitEmbedded() && getTask() != null && getTask().inFullscreenWindowingMode()) {
            return true;
        }
        return !inMultiWindowMode();
    }

    @Override // com.android.server.wm.InputTarget
    public boolean canScreenshotIme() {
        return !isSecureLocked();
    }

    @Override // com.android.server.wm.InputTarget
    public ActivityRecord getActivityRecord() {
        return this.mActivityRecord;
    }

    @Override // com.android.server.wm.InputTarget
    public boolean isInputMethodClientFocus(int i, int i2) {
        return getDisplayContent().isInputMethodClientFocus(i, i2);
    }

    @Override // com.android.server.wm.InputTarget
    public void dumpProto(ProtoOutputStream protoOutputStream, long j, int i) {
        dumpDebug(protoOutputStream, j, i);
    }

    public boolean cancelAndRedraw() {
        return this.mPrepareSyncSeqId > 0;
    }

    public final InsetsState adjustNavInsetsForWindowAboveThanIme(InsetsState insetsState) {
        WindowState navigationBar;
        InsetsSourceProvider controllableInsetProvider;
        InsetsSource source;
        if (WindowConfiguration.isFloating(getWindowingMode()) || getControllableInsetProvider() != null || this.mAboveInsetsState.peekSource(InsetsSource.ID_IME) != null || !getDisplayContent().getDisplayPolicy().mExt.isNavigationGestureMode() || !getDisplayContent().getDisplayPolicy().mExt.isImeBtnOnGestureAllowed(this.mDisplayContent.mDisplayFrames.mRotation) || !getDisplayContent().getInsetsStateController().getImeSourceProvider().getSource().isVisible() || (navigationBar = getDisplayContent().getDisplayPolicy().getNavigationBar()) == null || (controllableInsetProvider = navigationBar.getControllableInsetProvider()) == null || (source = controllableInsetProvider.getSource()) == null || !source.isVisible() || !controllableInsetProvider.overridesFrame(2011) || controllableInsetProvider.getOverriddenFrame(2011).equals(source.getFrame())) {
            return insetsState;
        }
        InsetsSource insetsSource = new InsetsSource(source);
        insetsSource.setFrame(controllableInsetProvider.getOverriddenFrame(2011));
        InsetsState insetsState2 = new InsetsState(insetsState);
        insetsState2.addSource(insetsSource);
        return insetsState2;
    }

    public boolean isEavesdropDragEvent() {
        WindowManager.LayoutParams layoutParams = this.mAttrs;
        return ((layoutParams.privateFlags & Integer.MIN_VALUE) == 0 || (layoutParams.multiwindowFlags & 16) == 0) ? false : true;
    }

    public void updateLetterboxDirectionIfNeeded() {
        int i = 0;
        if (getDisplayContent().mInitialDisplayCutout != null) {
            if (this.mIsImWindow) {
                ActivityRecord activity = getDisplayContent().getActivity(new Predicate() { // from class: com.android.server.wm.WindowState$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$updateLetterboxDirectionIfNeeded$7;
                        lambda$updateLetterboxDirectionIfNeeded$7 = WindowState.lambda$updateLetterboxDirectionIfNeeded$7((ActivityRecord) obj);
                        return lambda$updateLetterboxDirectionIfNeeded$7;
                    }
                });
                if (activity != null) {
                    i = activity.mLetterboxUiController.getLetterboxDirection();
                }
            } else if (this.mActivityRecord != null && (!inMultiWindowMode() || inSplitScreenWindowingMode())) {
                i = this.mActivityRecord.mLetterboxUiController.getLetterboxDirection();
            }
        }
        if (this.mLetterboxDirection != i) {
            boolean z = CoreRune.SAFE_DEBUG;
            this.mLetterboxDirection = i;
            try {
                this.mClient.dispatchLetterboxDirectionChanged(i);
            } catch (RemoteException e) {
                Slog.w(StartingSurfaceController.TAG, "Failed to deliver letterbox direction, w=" + this, e);
            }
        }
    }

    public static /* synthetic */ boolean lambda$updateLetterboxDirectionIfNeeded$7(ActivityRecord activityRecord) {
        return (!activityRecord.inMultiWindowMode() || activityRecord.inSplitScreenWindowingMode()) && activityRecord.mLetterboxUiController.getLetterboxDirection() != 0;
    }

    @Override // com.android.server.wm.WindowContainer
    public Dimmer getDimmer() {
        if (this.mPopOverDimmerNeeded && isPopOver()) {
            if (this.mPopOverDimmer == null) {
                this.mPopOverDimmer = new Dimmer(getTask());
            }
            return this.mPopOverDimmer;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null) {
            if (this.mLastDimmer == null || activityRecord.isVisibleRequested()) {
                this.mLastDimmer = super.getDimmer();
            }
            return this.mLastDimmer;
        }
        return super.getDimmer();
    }

    public final void updatePopOverDimmer() {
        if (getTask() == null) {
            return;
        }
        getTask().getDimBounds(this.mTmpDimBoundsRect);
        if (inFreeformWindowingMode()) {
            getTask().getBounds(this.mTmpRect);
            Rect rect = this.mTmpDimBoundsRect;
            int i = rect.left;
            Rect rect2 = this.mTmpRect;
            rect.offsetTo(i - rect2.left, rect.top - rect2.top);
        } else {
            this.mTmpDimBoundsRect.offsetTo(0, 0);
        }
        if (this.mPopOverDimmer.updateDims(getPendingTransaction())) {
            scheduleAnimation();
        }
    }

    public void clearPopOverDimmer() {
        Dimmer dimmer = this.mPopOverDimmer;
        if (dimmer == null) {
            return;
        }
        dimmer.resetDimStates();
        this.mPopOverDimmer.updateDims(getPendingTransaction());
        this.mPopOverDimmer = null;
        this.mPopOverDimmerNeeded = false;
    }

    public boolean isPopOver() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && activityRecord.mPopOverState.isActivated();
    }

    public boolean isLayoutWithIme() {
        return this.mLayoutWithIme;
    }

    public void setLayoutWithIme(boolean z) {
        this.mLayoutWithIme = z;
        if (z) {
            this.mMovedByIme = true;
        }
    }

    public final InsetsState adjustInsetsForPopOver(InsetsState insetsState) {
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            if (insetsState.sourceAt(sourceSize).getType() == WindowInsets.Type.captionBar()) {
                InsetsState insetsState2 = new InsetsState(insetsState);
                insetsState2.removeSourceAt(sourceSize);
                return insetsState2;
            }
        }
        return insetsState;
    }

    public void notifyWindowFocusInTaskChanged(boolean z) {
        if (isPopOver()) {
            return;
        }
        Slog.v(StartingSurfaceController.TAG, "notifyWindowFocusInTaskChanged: " + z + ", win=" + this);
        try {
            this.mClient.windowFocusInTaskChanged(z);
        } catch (RemoteException e) {
            Slog.w(StartingSurfaceController.TAG, "Failed to notifyWindowFocusInTaskChanged, win=" + this, e);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt.WindowStateExt
    public int getBaseLayer() {
        return this.mBaseLayer;
    }

    public void adjustImeParamsForDex(boolean z) {
        if (this.mAttrs.type == 2011 && getDisplayContent().isDefaultDisplay) {
            if (this.mImeBlurEffectAppliedForDex) {
                WindowManager.LayoutParams layoutParams = this.mAttrs;
                layoutParams.flags &= -3;
                layoutParams.samsungFlags &= -65;
                layoutParams.dimAmount = 1.0f;
                this.mImeBlurEffectAppliedForDex = false;
                if (CoreRune.IS_DEBUG_LEVEL_MID) {
                    Slog.d(StartingSurfaceController.TAG, "applyDims: release BlurDim for Ime in dex mode.");
                }
            }
            if (z) {
                DexController dexController = this.mWmService.mAtmService.mDexController;
                if (dexController.getDexModeLocked() == 2 && !dexController.isDexTouchPadEnabledLocked() && dexController.shouldShowDexImeInDefaultDisplayLocked()) {
                    WindowManager.LayoutParams layoutParams2 = this.mAttrs;
                    layoutParams2.flags |= 2;
                    layoutParams2.samsungFlags |= 64;
                    layoutParams2.dimAmount = 0.1f;
                    if (CoreRune.IS_DEBUG_LEVEL_MID) {
                        Slog.d(StartingSurfaceController.TAG, "applyDims: apply BlurDim for Ime in dex mode.");
                    }
                    this.mImeBlurEffectAppliedForDex = true;
                }
            }
        }
    }

    public final int getSamsungAnimResId() {
        if (CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_ANIMATION && BoundsCompatAlignmentController.shouldPlayMoveAnimation(this)) {
            return R.anim.ft_avd_tooverflow_rectangle_1_pivot_animation;
        }
        if (!CoreRune.FW_IMPROVED_MOVED_ANIMATION_FOR_IME || !isImeLayeringTarget()) {
            return 17432968;
        }
        if (!CoreRune.SAFE_DEBUG) {
            return R.anim.window_move_from_decor;
        }
        Slog.d(StartingSurfaceController.TAG, "MoveAnimationSpec is set to improvedMoveAnim, win=" + this);
        return R.anim.window_move_from_decor;
    }

    public boolean isAllowedSeamlessRotation() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && activityRecord.mIsAllowedSeamlessRotation;
    }

    public boolean isInBubbleMode() {
        return (getTask() == null || getTask().getWindowingMode() != 6 || getTask().inSplitScreenWindowingMode()) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isEmbedded() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && activityRecord.isEmbedded();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isSplitEmbedded() {
        ActivityRecord activityRecord = this.mActivityRecord;
        return activityRecord != null && activityRecord.isSplitEmbedded();
    }

    public void disableHideSViewCoverOnce(boolean z) {
        this.mDisableHideSViewOnce = z;
    }

    public boolean willBeHideSViewCoverOnce() {
        return this.mShouldHideSViewOnce && !this.mDisableHideSViewOnce;
    }

    public int getCoverMode() {
        if (getParentWindow() != null) {
            if (getParentWindow().mAttrs.type == 2099) {
                return 10;
            }
            return getParentWindow().mAttrs.coverMode;
        }
        return this.mAttrs.coverMode;
    }

    public final boolean applyScaleToCompatFrameIfNeeded(Rect rect) {
        if (this.mInvGlobalScale == 1.0f) {
            return false;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        if ((activityRecord == null || !activityRecord.mCompatRecord.isCompatModeEnabled() || this.mActivityRecord.getSizeCompatBounds() == null) && (!CoreRune.MT_SUPPORT_SIZE_COMPAT || getConfiguration().windowConfiguration.getCompatSandboxInvScale() == 1.0f)) {
            return false;
        }
        WindowConfiguration.scaleBounds(this.mInvGlobalScale, rect);
        return true;
    }

    public void setKeyguardWallpaperTouchAllowed(boolean z) {
        this.mKeyguardWallpaperTouchAllowed = z;
    }

    public boolean getKeyguardWallpaperTouchAllowed() {
        return this.mKeyguardWallpaperTouchAllowed;
    }

    public final boolean hasPipBoundsChangeDrawHandler() {
        return inPinnedWindowingMode() && this.mDrawHandlers.stream().anyMatch(new Predicate() { // from class: com.android.server.wm.WindowState$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$hasPipBoundsChangeDrawHandler$8;
                lambda$hasPipBoundsChangeDrawHandler$8 = WindowState.lambda$hasPipBoundsChangeDrawHandler$8((WindowState.DrawHandler) obj);
                return lambda$hasPipBoundsChangeDrawHandler$8;
            }
        });
    }

    public static /* synthetic */ boolean lambda$hasPipBoundsChangeDrawHandler$8(DrawHandler drawHandler) {
        return drawHandler.mType == 1;
    }

    @Override // com.android.server.wm.WindowContainer
    public void waitForSyncTransactionCommit(ArraySet arraySet) {
        if (CoreRune.MW_PIP_SHELL_TRANSITION && this.mTransitionController.isCollecting() && hasPipBoundsChangeDrawHandler()) {
            Slog.w(StartingSurfaceController.TAG, "waitForSyncTransactionCommit: skip, reason=pip_bounds_change" + this);
            return;
        }
        super.waitForSyncTransactionCommit(arraySet);
    }

    @Override // com.android.server.wm.WindowContainer
    public void onSyncTransactionCommitted(SurfaceControl.Transaction transaction) {
        super.onSyncTransactionCommitted(transaction);
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent.mInputMethodWindow == this) {
            displayContent.updateImeControlTarget();
        }
    }
}
