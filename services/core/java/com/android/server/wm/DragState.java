package com.android.server.wm;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Binder;
import android.os.IBinder;
import android.os.InputConstants;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.util.Slog;
import android.view.Display;
import android.view.DragEvent;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputWindowHandle;
import android.view.MagnificationSpec;
import android.view.SurfaceControl;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.pm.UserManagerInternal;

import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

public final class DragState {
    public ValueAnimator mAnimator;
    public boolean mApplyAlpha;
    public String mCallingPackageName;
    public int mCallingTaskIdToHide;
    public float mCenterPivotOffsetX;
    public float mCenterPivotOffsetY;
    public boolean mCrossProfileCopyAllowed;
    public float mCurrentX;
    public float mCurrentY;
    public ClipData mData;
    public ClipDescription mDataDescription;
    public int mDeviceId;
    public DisplayContent mDisplayContent;
    public final DragDropController mDragDropController;
    public boolean mDragInProgress;
    public boolean mDragInProgressByRecents;
    public boolean mDragMoved;
    public boolean mDragResult;
    public boolean mDragSplitAppIconHasDrawable;
    public int mFlags;
    public InputInterceptor mInputInterceptor;
    public SurfaceControl mInputSurface;
    public boolean mIsClosing;
    public boolean mIsObjectCapture;
    public boolean mIsUpdatingClipdata;
    public IBinder mLocalWin;
    public boolean mNeedAdjustPosition;
    public RectF mObjectCaptureRect;
    public float mOriginalAlpha;
    public float mOriginalX;
    public float mOriginalY;
    public int mPid;
    public int mPointerId;
    public boolean mRelinquishDragSurfaceToDropTarget;
    public final WindowManagerService mService;
    public int mSourceUserId;
    public float mSourceX;
    public float mSourceY;
    public SurfaceControl mSurfaceControl;
    public float mTargetX;
    public float mTargetY;
    public float mThumbOffsetX;
    public float mThumbOffsetY;
    public IBinder mToken;
    public final SurfaceControl.Transaction mTransaction;
    public int mUid;
    public DragEvent mUnhandledDropEvent;
    public float mAnimatedScale = 1.0f;
    public volatile boolean mAnimationCompleted = false;
    public final Interpolator mCubicEaseOutInterpolator = new DecelerateInterpolator(1.5f);
    public final Point mDisplaySize = new Point();
    public final Rect mTmpClipRect = new Rect();
    public int mMimeType = -1;
    public final HashMap mAnimatorSet = new HashMap();
    public float mScaleAnimSource = 1.0f;
    public float mScaleAnimTarget = 0.8f;
    public final PathInterpolator mAlphaAnimInterpolator =
            new PathInterpolator(
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    1.0f,
                    1.0f);
    public final PathInterpolator mPositionScaleAnimInterpolator =
            new PathInterpolator(
                    0.22f, 0.25f, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f);
    public float mCurrentAlpha = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public ArrayList mNotifiedWindows = new ArrayList();

    /* renamed from: com.android.server.wm.DragState$1, reason: invalid class name */
    public final class AnonymousClass1 extends AnimationListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DragState this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass1(DragState dragState, int i) {
            super(dragState, 3);
            this.$r8$classId = i;
            this.this$0 = dragState;
        }

        @Override // com.android.server.wm.DragState.AnimationListener,
                  // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            switch (this.$r8$classId) {
                case 0:
                    DragState.m1061$$Nest$mendAnimator(this.this$0, 2);
                    this.this$0.mDragDropController.mHandler.obtainMessage(2, null).sendToTarget();
                    break;
                case 1:
                    this.this$0.mIsUpdatingClipdata = false;
                    break;
                default:
                    DragState.m1061$$Nest$mendAnimator(this.this$0, 2);
                    this.this$0.mDragDropController.mHandler.obtainMessage(2, null).sendToTarget();
                    break;
            }
        }

        @Override // com.android.server.wm.DragState.AnimationListener,
                  // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            switch (this.$r8$classId) {
                case 1:
                    SurfaceControl.Transaction transaction =
                            (SurfaceControl.Transaction)
                                    this.this$0.mService.mTransactionFactory.get();
                    try {
                        if (this.this$0.mSurfaceControl == null) {
                            Slog.d(
                                    "WindowManager",
                                    "mSurfaceControl is null, animation cannot be updated.");
                            if (transaction == null) {
                                return;
                            }
                        } else {
                            float floatValue =
                                    ((Float) valueAnimator.getAnimatedValue("pivot_offset"))
                                            .floatValue();
                            transaction.setMatrix(
                                    this.this$0.mSurfaceControl,
                                    ((Float) valueAnimator.getAnimatedValue("scale")).floatValue(),
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    ((Float) valueAnimator.getAnimatedValue("scale")).floatValue());
                            DragState dragState = this.this$0;
                            transaction.setPosition(
                                    dragState.mSurfaceControl,
                                    dragState.mCenterPivotOffsetX + floatValue,
                                    dragState.mCenterPivotOffsetY + floatValue);
                            transaction.apply();
                        }
                        transaction.close();
                        return;
                    } catch (Throwable th) {
                        if (transaction != null) {
                            try {
                                transaction.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                default:
                    super.onAnimationUpdate(valueAnimator);
                    return;
            }
        }
    }

    public class AnimationListener
            implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DragState this$0;

        public /* synthetic */ AnimationListener(DragState dragState, int i) {
            this.$r8$classId = i;
            this.this$0 = dragState;
        }

        private final void onAnimationCancel$com$android$server$wm$DragState$AlphaAnimationListener(
                Animator animator) {}

        private final void onAnimationCancel$com$android$server$wm$DragState$AnimationListener(
                Animator animator) {}

        private final void
                onAnimationCancel$com$android$server$wm$DragState$PositionAnimationListener(
                        Animator animator) {}

        private final void onAnimationCancel$com$android$server$wm$DragState$ScaleAnimationListener(
                Animator animator) {}

        private final void onAnimationRepeat$com$android$server$wm$DragState$AlphaAnimationListener(
                Animator animator) {}

        private final void onAnimationRepeat$com$android$server$wm$DragState$AnimationListener(
                Animator animator) {}

        private final void
                onAnimationRepeat$com$android$server$wm$DragState$PositionAnimationListener(
                        Animator animator) {}

        private final void onAnimationRepeat$com$android$server$wm$DragState$ScaleAnimationListener(
                Animator animator) {}

        private final void onAnimationStart$com$android$server$wm$DragState$AlphaAnimationListener(
                Animator animator) {}

        private final void onAnimationStart$com$android$server$wm$DragState$AnimationListener(
                Animator animator) {}

        private final void
                onAnimationStart$com$android$server$wm$DragState$PositionAnimationListener(
                        Animator animator) {}

        private final void onAnimationStart$com$android$server$wm$DragState$ScaleAnimationListener(
                Animator animator) {}

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            int i = this.$r8$classId;
        }

        public void onAnimationEnd(Animator animator) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mAnimationCompleted = true;
                    this.this$0.mDragDropController.mHandler.obtainMessage(2, null).sendToTarget();
                    break;
                case 1:
                    DragState.m1061$$Nest$mendAnimator(this.this$0, 0);
                    break;
                case 2:
                    DragState.m1061$$Nest$mendAnimator(this.this$0, 1);
                    if (CoreRune.MT_DND_SEAMLESS_ANIMATION) {
                        this.this$0.mNeedAdjustPosition = false;
                        break;
                    }
                    break;
                default:
                    DragState.m1061$$Nest$mendAnimator(this.this$0, 2);
                    break;
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
            int i = this.$r8$classId;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            int i = this.$r8$classId;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            SurfaceControl.Transaction transaction;
            switch (this.$r8$classId) {
                case 0:
                    SurfaceControl.Transaction transaction2 =
                            (SurfaceControl.Transaction)
                                    this.this$0.mService.mTransactionFactory.get();
                    try {
                        if (CoreRune.MT_DND_ANIMATION && this.this$0.mSurfaceControl == null) {
                            Slog.d(
                                    "WindowManager",
                                    "mSurfaceControl is null, animation cannot be updated.");
                            if (transaction2 == null) {
                                return;
                            }
                        } else {
                            transaction2.setPosition(
                                    this.this$0.mSurfaceControl,
                                    ((Float) valueAnimator.getAnimatedValue("x")).floatValue(),
                                    ((Float) valueAnimator.getAnimatedValue("y")).floatValue());
                            transaction2.setAlpha(
                                    this.this$0.mSurfaceControl,
                                    ((Float) valueAnimator.getAnimatedValue("alpha")).floatValue());
                            transaction2.setMatrix(
                                    this.this$0.mSurfaceControl,
                                    ((Float) valueAnimator.getAnimatedValue("scale")).floatValue(),
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    ((Float) valueAnimator.getAnimatedValue("scale")).floatValue());
                            transaction2.apply();
                        }
                        transaction2.close();
                        return;
                    } finally {
                        if (transaction2 != null) {
                            try {
                                transaction2.close();
                            } catch (Throwable th) {
                                th.addSuppressed(th);
                            }
                        }
                    }
                case 1:
                    transaction =
                            (SurfaceControl.Transaction)
                                    this.this$0.mService.mTransactionFactory.get();
                    try {
                        this.this$0.mCurrentAlpha =
                                ((Float) valueAnimator.getAnimatedValue("alpha")).floatValue();
                        DragState dragState = this.this$0;
                        SurfaceControl surfaceControl = dragState.mSurfaceControl;
                        if (surfaceControl == null) {
                            Slog.d(
                                    "WindowManager",
                                    "mSurfaceControl is null, animation cannot be updated.");
                            if (transaction == null) {
                                return;
                            }
                        } else {
                            transaction.setAlpha(surfaceControl, dragState.mCurrentAlpha);
                            transaction.apply();
                        }
                        transaction.close();
                        return;
                    } finally {
                        if (transaction != null) {
                            try {
                                transaction.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                    }
                case 2:
                    transaction =
                            (SurfaceControl.Transaction)
                                    this.this$0.mService.mTransactionFactory.get();
                    try {
                        float floatValue =
                                ((Float) valueAnimator.getAnimatedValue("x")).floatValue();
                        float floatValue2 =
                                ((Float) valueAnimator.getAnimatedValue("y")).floatValue();
                        if (CoreRune.MT_DND_ANIMATION) {
                            DragState dragState2 = this.this$0;
                            if (dragState2.mNeedAdjustPosition) {
                                float adjustPosition =
                                        dragState2.adjustPosition(dragState2.mTargetX, 0);
                                DragState dragState3 = this.this$0;
                                float m1060$$Nest$mcalculateDistance =
                                        DragState.m1060$$Nest$mcalculateDistance(
                                                dragState2,
                                                floatValue,
                                                floatValue2,
                                                adjustPosition,
                                                dragState3.adjustPosition(dragState3.mTargetY, 1));
                                DragState dragState4 = this.this$0;
                                float adjustPosition2 =
                                        dragState4.adjustPosition(dragState4.mSourceX, 0);
                                DragState dragState5 = this.this$0;
                                float adjustPosition3 =
                                        dragState5.adjustPosition(dragState5.mSourceY, 1);
                                DragState dragState6 = this.this$0;
                                float adjustPosition4 =
                                        dragState6.adjustPosition(dragState6.mTargetX, 0);
                                DragState dragState7 = this.this$0;
                                float m1060$$Nest$mcalculateDistance2 =
                                        DragState.m1060$$Nest$mcalculateDistance(
                                                dragState4,
                                                adjustPosition2,
                                                adjustPosition3,
                                                adjustPosition4,
                                                dragState7.adjustPosition(dragState7.mTargetY, 1));
                                float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
                                if (m1060$$Nest$mcalculateDistance2
                                        != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                                    f =
                                            m1060$$Nest$mcalculateDistance
                                                    / m1060$$Nest$mcalculateDistance2;
                                }
                                DragState dragState8 = this.this$0;
                                float adjustPosition5 =
                                        dragState8.adjustPosition(
                                                dragState8.mCurrentX
                                                        - (dragState8.mThumbOffsetX
                                                                * dragState8.mScaleAnimSource),
                                                0);
                                DragState dragState9 = this.this$0;
                                float adjustPosition6 =
                                        dragState9.adjustPosition(
                                                dragState9.mCurrentY
                                                        - (dragState9.mThumbOffsetY
                                                                * dragState9.mScaleAnimSource),
                                                1);
                                DragState dragState10 = this.this$0;
                                float adjustPosition7 =
                                        adjustPosition5
                                                + ((dragState10.adjustPosition(
                                                                        dragState10.mSourceX, 0)
                                                                - adjustPosition5)
                                                        * f);
                                DragState dragState11 = this.this$0;
                                float adjustPosition8 =
                                        adjustPosition6
                                                + ((dragState11.adjustPosition(
                                                                        dragState11.mSourceY, 1)
                                                                - adjustPosition6)
                                                        * f);
                                floatValue = adjustPosition7;
                                floatValue2 = adjustPosition8;
                            }
                        }
                        SurfaceControl surfaceControl2 = this.this$0.mSurfaceControl;
                        if (surfaceControl2 == null) {
                            Slog.d(
                                    "WindowManager",
                                    "mSurfaceControl is null, animation cannot be updated.");
                            if (transaction == null) {
                                return;
                            }
                        } else {
                            transaction.setPosition(surfaceControl2, floatValue, floatValue2);
                            transaction.apply();
                        }
                        transaction.close();
                        return;
                    } catch (Throwable th3) {
                        throw th3;
                    }
                default:
                    transaction =
                            (SurfaceControl.Transaction)
                                    this.this$0.mService.mTransactionFactory.get();
                    try {
                        SurfaceControl surfaceControl3 = this.this$0.mSurfaceControl;
                        if (surfaceControl3 == null) {
                            Slog.d(
                                    "WindowManager",
                                    "mSurfaceControl is null, animation cannot be updated.");
                            if (transaction == null) {
                                return;
                            }
                        } else {
                            transaction.setMatrix(
                                    surfaceControl3,
                                    ((Float) valueAnimator.getAnimatedValue("scale")).floatValue(),
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    ((Float) valueAnimator.getAnimatedValue("scale")).floatValue());
                            transaction.apply();
                        }
                        transaction.close();
                        return;
                    } finally {
                    }
            }
        }
    }

    public final class InputInterceptor {
        public InputChannel mClientChannel;
        public InputApplicationHandle mDragApplicationHandle;
        public InputWindowHandle mDragWindowHandle;
        public DragInputEventReceiver mInputEventReceiver;

        public InputInterceptor(Display display) {
            this.mClientChannel = DragState.this.mService.mInputManager.createInputChannel("drag");
            InputChannel inputChannel = this.mClientChannel;
            WindowManagerService windowManagerService = DragState.this.mService;
            this.mInputEventReceiver =
                    new DragInputEventReceiver(
                            inputChannel,
                            windowManagerService.mH.getLooper(),
                            DragState.this.mDragDropController);
            Binder binder = new Binder();
            int i = InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
            this.mDragApplicationHandle = new InputApplicationHandle(binder, "drag", i);
            InputWindowHandle inputWindowHandle =
                    new InputWindowHandle(this.mDragApplicationHandle, display.getDisplayId());
            this.mDragWindowHandle = inputWindowHandle;
            inputWindowHandle.name = "drag";
            inputWindowHandle.token = this.mClientChannel.getToken();
            InputWindowHandle inputWindowHandle2 = this.mDragWindowHandle;
            inputWindowHandle2.layoutParamsType = 2016;
            inputWindowHandle2.dispatchingTimeoutMillis = i;
            inputWindowHandle2.ownerPid = WindowManagerService.MY_PID;
            inputWindowHandle2.ownerUid = WindowManagerService.MY_UID;
            inputWindowHandle2.scaleFactor = 1.0f;
            if ((DragState.this.mFlags & 256) != 0) {
                inputWindowHandle2.inputConfig |= 256;
            }
            inputWindowHandle2.touchableRegion.setEmpty();
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_ORIENTATION, 7928129513685401229L, 0, null, null);
            }
            windowManagerService.mRoot.forAllDisplays(new DragState$$ExternalSyntheticLambda8(1));
        }

        public final void tearDown() {
            DragState dragState = DragState.this;
            dragState.mService.mInputManager.removeInputChannel(this.mClientChannel.getToken());
            this.mInputEventReceiver.dispose();
            this.mInputEventReceiver = null;
            this.mClientChannel.dispose();
            this.mClientChannel = null;
            this.mDragWindowHandle = null;
            this.mDragApplicationHandle = null;
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_ORIENTATION, 8231481023986546563L, 0, null, null);
            }
            dragState.mService.mRoot.forAllDisplays(new DragState$$ExternalSyntheticLambda8(2));
        }
    }

    /* renamed from: -$$Nest$mcalculateDistance, reason: not valid java name */
    public static float m1060$$Nest$mcalculateDistance(
            DragState dragState, float f, float f2, float f3, float f4) {
        dragState.getClass();
        return (float)
                Math.sqrt(Math.pow(Math.abs(f2 - f4), 2.0d) + Math.pow(Math.abs(f - f3), 2.0d));
    }

    /* renamed from: -$$Nest$mendAnimator, reason: not valid java name */
    public static void m1061$$Nest$mendAnimator(final DragState dragState, final int i) {
        dragState.mService.mAnimationHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.wm.DragState$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        DragState dragState2 = DragState.this;
                        int i2 = i;
                        WindowManagerGlobalLock windowManagerGlobalLock =
                                dragState2.mService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                dragState2.mAnimatorSet.remove(Integer.valueOf(i2));
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                });
    }

    public DragState(
            WindowManagerService windowManagerService,
            DragDropController dragDropController,
            IBinder iBinder,
            SurfaceControl surfaceControl,
            int i,
            IBinder iBinder2) {
        this.mService = windowManagerService;
        this.mDragDropController = dragDropController;
        this.mToken = iBinder;
        this.mSurfaceControl = surfaceControl;
        this.mFlags = i;
        this.mLocalWin = iBinder2;
        this.mTransaction =
                (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
    }

    public static boolean containsApplicationExtras(ClipDescription clipDescription) {
        if (clipDescription == null) {
            return false;
        }
        return clipDescription.hasMimeType("application/vnd.android.activity")
                || clipDescription.hasMimeType("application/vnd.android.shortcut")
                || clipDescription.hasMimeType("application/vnd.android.task");
    }

    public static boolean targetInterceptsGlobalDrag(WindowState windowState) {
        return (windowState == null || (windowState.mAttrs.privateFlags & Integer.MIN_VALUE) == 0)
                ? false
                : true;
    }

    public final float adjustPosition(float f, int i) {
        MagnificationSpec magnificationSpec =
                this.mDisplayContent.hasOneHandOpSpec()
                        ? this.mDisplayContent.mMagnificationSpec
                        : null;
        if (magnificationSpec != null) {
            return (f * magnificationSpec.scale)
                    + (i == 0 ? magnificationSpec.offsetX : magnificationSpec.offsetY);
        }
        return f;
    }

    public final float adjustScale(float f) {
        MagnificationSpec magnificationSpec =
                this.mDisplayContent.hasOneHandOpSpec()
                        ? this.mDisplayContent.mMagnificationSpec
                        : null;
        return magnificationSpec != null ? f * magnificationSpec.scale : f;
    }

    public final void broadcastDragStartedLocked(final float f, final float f2) {
        Trace.instant(32L, "DragDropController#DRAG_STARTED");
        this.mCurrentX = f;
        this.mOriginalX = f;
        this.mCurrentY = f2;
        this.mOriginalY = f2;
        ClipData clipData = this.mData;
        this.mDataDescription = clipData != null ? clipData.getDescription() : null;
        this.mNotifiedWindows.clear();
        this.mDragInProgress = true;
        this.mSourceUserId = UserHandle.getUserId(this.mUid);
        this.mCrossProfileCopyAllowed =
                true
                        ^ ((UserManagerInternal)
                                        LocalServices.getService(UserManagerInternal.class))
                                .getUserRestriction(
                                        this.mSourceUserId, "no_cross_profile_copy_paste");
        Slog.d("WindowManager", "Broadcasting DRAG_STARTED at (" + f + ", " + f2 + ")");
        final boolean containsApplicationExtras = containsApplicationExtras(this.mDataDescription);
        this.mService.mRoot.forAllWindows(
                new Consumer() { // from class:
                                 // com.android.server.wm.DragState$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DragState.this.sendDragStartedLocked(
                                (WindowState) obj, f, f2, containsApplicationExtras, false);
                    }
                },
                false);
    }

    public final void cancelAnimatorAllLocked() {
        if (this.mAnimatorSet.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mAnimatorSet.values());
        this.mAnimatorSet.clear();
        this.mService.mAnimationHandler.post(new DragState$$ExternalSyntheticLambda2(2, arrayList));
    }

    public final void cancelDragLocked(boolean z) {
        ValueAnimator ofPropertyValuesHolder;
        int i = 0;
        if (this.mAnimator != null) {
            return;
        }
        if (!this.mDragInProgress || z || (this.mFlags & 1024) != 0) {
            closeLocked(false);
            return;
        }
        if (CoreRune.MT_DND_ANIMATION && isAnimationSet()) {
            cancelAnimatorAllLocked();
            ofPropertyValuesHolder = createReleaseAnimationLocked();
        } else {
            if (this.mCallingTaskIdToHide != -1) {
                float f = this.mCurrentX;
                float[] fArr = {f, f};
                float f2 = this.mCurrentY;
                float[] fArr2 = {f2, f2};
                float f3 = this.mAnimatedScale;
                ofPropertyValuesHolder =
                        ValueAnimator.ofPropertyValuesHolder(
                                PropertyValuesHolder.ofFloat("x", fArr),
                                PropertyValuesHolder.ofFloat("y", fArr2),
                                PropertyValuesHolder.ofFloat("scale", f3, f3),
                                PropertyValuesHolder.ofFloat(
                                        "alpha",
                                        this.mOriginalAlpha,
                                        FullScreenMagnificationGestureHandler.MAX_SCALE));
            } else {
                MagnificationSpec magnificationSpec =
                        this.mDisplayContent.hasOneHandOpSpec()
                                ? this.mDisplayContent.mMagnificationSpec
                                : null;
                if (magnificationSpec != null) {
                    float f4 = this.mCurrentX;
                    float f5 = f4 - this.mThumbOffsetX;
                    float f6 = magnificationSpec.scale;
                    float f7 = magnificationSpec.offsetX;
                    float[] fArr3 = {(f5 * f6) + f7, (f4 * f6) + f7};
                    float f8 = this.mCurrentY;
                    float f9 = f8 - this.mThumbOffsetY;
                    float f10 = magnificationSpec.scale;
                    float f11 = magnificationSpec.offsetY;
                    ofPropertyValuesHolder =
                            ValueAnimator.ofPropertyValuesHolder(
                                    PropertyValuesHolder.ofFloat("x", fArr3),
                                    PropertyValuesHolder.ofFloat(
                                            "y", (f9 * f10) + f11, (f8 * f10) + f11),
                                    PropertyValuesHolder.ofFloat(
                                            "scale",
                                            this.mAnimatedScale * magnificationSpec.scale,
                                            FullScreenMagnificationGestureHandler.MAX_SCALE),
                                    PropertyValuesHolder.ofFloat(
                                            "alpha",
                                            this.mOriginalAlpha,
                                            FullScreenMagnificationGestureHandler.MAX_SCALE));
                } else {
                    float f12 = this.mCurrentX;
                    float[] fArr4 = {f12 - this.mThumbOffsetX, f12};
                    float f13 = this.mCurrentY;
                    ofPropertyValuesHolder =
                            ValueAnimator.ofPropertyValuesHolder(
                                    PropertyValuesHolder.ofFloat("x", fArr4),
                                    PropertyValuesHolder.ofFloat(
                                            "y", f13 - this.mThumbOffsetY, f13),
                                    PropertyValuesHolder.ofFloat(
                                            "scale",
                                            this.mAnimatedScale,
                                            FullScreenMagnificationGestureHandler.MAX_SCALE),
                                    PropertyValuesHolder.ofFloat(
                                            "alpha",
                                            this.mOriginalAlpha,
                                            FullScreenMagnificationGestureHandler.MAX_SCALE));
                }
            }
            AnimationListener animationListener = new AnimationListener(this, i);
            ofPropertyValuesHolder.setDuration(195L);
            ofPropertyValuesHolder.setInterpolator(this.mCubicEaseOutInterpolator);
            ofPropertyValuesHolder.addListener(animationListener);
            ofPropertyValuesHolder.addUpdateListener(animationListener);
            this.mService.mAnimationHandler.post(
                    new DragState$$ExternalSyntheticLambda3(ofPropertyValuesHolder, 1));
        }
        this.mAnimator = ofPropertyValuesHolder;
    }

    public final void closeLocked(boolean z) {
        SurfaceControl surfaceControl;
        float f;
        float f2;
        if (CoreRune.MT_DND_ANIMATION && !z) {
            this.mIsClosing = true;
            if (this.mInputInterceptor != null) {
                Slog.d("WindowManager", "unregistering drag input channel");
                this.mDragDropController
                        .mHandler
                        .obtainMessage(1, this.mInputInterceptor)
                        .sendToTarget();
                this.mInputInterceptor = null;
            }
        }
        if (this.mDragInProgress) {
            Slog.d("WindowManager", "Broadcasting DRAG_ENDED");
            Iterator it = this.mNotifiedWindows.iterator();
            while (it.hasNext()) {
                WindowState windowState = (WindowState) it.next();
                boolean z2 = this.mDragResult;
                if (z2 || windowState.mSession.mPid != this.mPid) {
                    surfaceControl = null;
                    f = 0.0f;
                    f2 = 0.0f;
                } else {
                    float f3 = this.mCurrentX;
                    Rect rect = windowState.mWindowFrames.mFrame;
                    float f4 = f3 - rect.left;
                    float f5 = windowState.mGlobalScale;
                    if (f5 != 1.0f) {
                        f4 *= windowState.mInvGlobalScale;
                    }
                    float f6 = this.mCurrentY - rect.top;
                    if (f5 != 1.0f) {
                        f6 *= windowState.mInvGlobalScale;
                    }
                    if ((this.mFlags & 2048) != 0) {
                        surfaceControl = this.mSurfaceControl;
                        f2 = f6;
                        f = f4;
                    } else {
                        surfaceControl = null;
                        f = f4;
                        f2 = f6;
                    }
                }
                DragEvent obtain =
                        DragEvent.obtain(
                                4,
                                f,
                                f2,
                                this.mThumbOffsetX,
                                this.mThumbOffsetY,
                                this.mFlags,
                                null,
                                null,
                                null,
                                surfaceControl,
                                null,
                                z2);
                try {
                    Slog.d("WindowManager", "Sending DRAG_ENDED to " + windowState);
                    windowState.mClient.dispatchDragEvent(obtain);
                } catch (RemoteException unused) {
                    Slog.w("WindowManager", "Unable to drag-end window " + windowState);
                }
                if (WindowManagerService.MY_PID != windowState.mSession.mPid) {
                    obtain.recycle();
                }
            }
            this.mNotifiedWindows.clear();
            this.mDragInProgress = false;
            Trace.instant(32L, "DragDropController#DRAG_ENDED");
        }
        boolean z3 = CoreRune.MT_DND_ANIMATION;
        if (z3 && z) {
            return;
        }
        if (this.mDragInProgressByRecents) {
            this.mDragInProgressByRecents = false;
        }
        SurfaceControl surfaceControl2 = this.mInputSurface;
        if (surfaceControl2 != null) {
            this.mTransaction.remove(surfaceControl2).apply();
            this.mInputSurface = null;
        }
        if (this.mSurfaceControl != null) {
            if (z3) {
                cancelAnimatorAllLocked();
                if (CoreRune.MT_DND_SEAMLESS_ANIMATION) {
                    this.mNeedAdjustPosition = false;
                }
            }
            if (this.mRelinquishDragSurfaceToDropTarget || (this.mFlags & 2048) != 0) {
                DragDropController dragDropController = this.mDragDropController;
                SurfaceControl surfaceControl3 = this.mSurfaceControl;
                DragDropController.DragHandler dragHandler = dragDropController.mHandler;
                dragHandler.removeMessages(3, surfaceControl3);
                dragHandler.sendMessageDelayed(
                        dragHandler.obtainMessage(3, surfaceControl3), 5000L);
            } else if (z3) {
                final SurfaceControl.Transaction transaction = this.mTransaction;
                final SurfaceControl surfaceControl4 = this.mSurfaceControl;
                this.mService.mAnimationHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.wm.DragState$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                transaction.remove(surfaceControl4).apply();
                            }
                        });
            } else {
                this.mTransaction.remove(this.mSurfaceControl).apply();
            }
            this.mSurfaceControl = null;
        }
        if ((this.mAnimator != null && !this.mAnimationCompleted)
                || (z3 && (!this.mAnimatorSet.isEmpty()))) {
            Slog.wtf(
                    "WindowManager",
                    "Unexpectedly destroying mSurfaceControl while animation is running");
        }
        this.mFlags = 0;
        this.mLocalWin = null;
        this.mToken = null;
        this.mData = null;
        this.mThumbOffsetY = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mThumbOffsetX = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mNotifiedWindows = null;
        DragEvent dragEvent = this.mUnhandledDropEvent;
        if (dragEvent != null) {
            dragEvent.recycle();
            this.mUnhandledDropEvent = null;
        }
        DragDropController dragDropController2 = this.mDragDropController;
        if (dragDropController2.mDragState != this) {
            Slog.wtf("WindowManager", "Unknown drag state is closed");
            return;
        }
        dragDropController2.mDragState = null;
        if (dragDropController2.mDragSourceTask != null) {
            Slog.d("WindowManager", "[TWODND] onDragStateClosedLocked");
            Task task = dragDropController2.mDragSourceTask;
            task.mIsDragSourceTask = false;
            task.mHiddenWhileActivatingDrag = false;
            dragDropController2.mDragSourceTask = null;
            if (dragDropController2.mUpdateTaskVisibilityAfterDragClosed) {
                dragDropController2.mHandler.sendEmptyMessage(100);
            }
        }
    }

    public final ValueAnimator createAlphaAnimator(float f, float f2, long j) {
        ValueAnimator ofPropertyValuesHolder =
                ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("alpha", f, f2));
        ofPropertyValuesHolder.setStartDelay(0L);
        ofPropertyValuesHolder.setDuration(j);
        ofPropertyValuesHolder.setInterpolator(this.mAlphaAnimInterpolator);
        AnimationListener animationListener = new AnimationListener(this, 1);
        ofPropertyValuesHolder.addListener(animationListener);
        ofPropertyValuesHolder.addUpdateListener(animationListener);
        return ofPropertyValuesHolder;
    }

    public final ValueAnimator createCenteredScaleAnimator(float f, float f2, float f3, long j) {
        ValueAnimator ofPropertyValuesHolder =
                ValueAnimator.ofPropertyValuesHolder(
                        PropertyValuesHolder.ofFloat("scale", f, f2),
                        PropertyValuesHolder.ofFloat(
                                "pivot_offset",
                                FullScreenMagnificationGestureHandler.MAX_SCALE,
                                f3));
        ofPropertyValuesHolder.setDuration(j);
        ofPropertyValuesHolder.setInterpolator(this.mPositionScaleAnimInterpolator);
        return ofPropertyValuesHolder;
    }

    public final DragEvent createDropEvent(float f, float f2, WindowState windowState, boolean z) {
        ClipData clipData;
        if (windowState == null) {
            return obtainDragEvent(3, f, f2, this.mDataDescription, this.mData, z, z, null);
        }
        int userId = UserHandle.getUserId(windowState.mOwnerUid);
        int i = this.mFlags;
        DragAndDropPermissionsHandler dragAndDropPermissionsHandler =
                ((i & 256) == 0 || (i & 3) == 0 || this.mData == null)
                        ? null
                        : new DragAndDropPermissionsHandler(
                                this.mService.mGlobalLock,
                                this.mData,
                                this.mUid,
                                windowState.mAttrs.packageName,
                                this.mFlags & 195,
                                this.mSourceUserId,
                                userId);
        int i2 = this.mSourceUserId;
        if (i2 != userId && (clipData = this.mData) != null) {
            clipData.fixUris(i2);
        }
        boolean targetInterceptsGlobalDrag = targetInterceptsGlobalDrag(windowState);
        return obtainDragEvent(
                3,
                f,
                f2,
                this.mDataDescription,
                this.mData,
                targetInterceptsGlobalDrag,
                targetInterceptsGlobalDrag,
                dragAndDropPermissionsHandler);
    }

    public final ValueAnimator createPositionAnimator(float f, float f2, float f3, float f4) {
        ValueAnimator ofPropertyValuesHolder =
                ValueAnimator.ofPropertyValuesHolder(
                        PropertyValuesHolder.ofFloat(
                                "x", adjustPosition(f, 0), adjustPosition(f2, 0)),
                        PropertyValuesHolder.ofFloat(
                                "y", adjustPosition(f3, 1), adjustPosition(f4, 1)));
        ofPropertyValuesHolder.setDuration(350L);
        ofPropertyValuesHolder.setInterpolator(this.mPositionScaleAnimInterpolator);
        AnimationListener animationListener = new AnimationListener(this, 2);
        ofPropertyValuesHolder.addListener(animationListener);
        ofPropertyValuesHolder.addUpdateListener(animationListener);
        return ofPropertyValuesHolder;
    }

    public final ValueAnimator createReleaseAnimationLocked() {
        float width;
        boolean z = CoreRune.MT_DND_OBJECT_CAPTURE;
        WindowManagerService windowManagerService = this.mService;
        if (z && this.mIsObjectCapture && !this.mDragResult) {
            Slog.d("WindowManager", "createObjectReleaseAnimationLocked");
            AnimatorSet animatorSet = new AnimatorSet();
            this.mAnimatorSet.clear();
            int width2 = this.mSurfaceControl.getWidth();
            if (width2 < 1) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        width2, "dragSurfaceWidth is wrong!!, width: ", "WindowManager");
                width = 1.0f;
            } else {
                RectF rectF = this.mObjectCaptureRect;
                width = (rectF.right - rectF.left) / this.mSurfaceControl.getWidth();
            }
            ValueAnimator createScaleAnimator = createScaleAnimator(1.0f, adjustScale(width));
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, 2);
            createScaleAnimator.addListener(anonymousClass1);
            createScaleAnimator.addUpdateListener(anonymousClass1);
            this.mAnimatorSet.put(2, createScaleAnimator);
            float f = this.mCurrentX;
            float f2 = this.mThumbOffsetX;
            float f3 = this.mScaleAnimSource;
            float f4 = f - (f2 * f3);
            float f5 = this.mCurrentY - (this.mThumbOffsetY * f3);
            RectF rectF2 = this.mObjectCaptureRect;
            this.mAnimatorSet.put(1, createPositionAnimator(f4, rectF2.left, f5, rectF2.top));
            animatorSet.playTogether(new ArrayList(this.mAnimatorSet.values()));
            if (CoreRune.MT_DND_SEAMLESS_ANIMATION) {
                this.mNeedAdjustPosition = false;
            }
            windowManagerService.mAnimationHandler.post(
                    new DragState$$ExternalSyntheticLambda2(0, animatorSet));
            return createScaleAnimator;
        }
        Slog.d("WindowManager", "createReleaseAnimationLocked");
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mAnimatorSet.clear();
        this.mAnimatorSet.put(
                0,
                createAlphaAnimator(0.3f, FullScreenMagnificationGestureHandler.MAX_SCALE, 150L));
        ValueAnimator createScaleAnimator2 =
                createScaleAnimator(
                        adjustScale(this.mScaleAnimSource), adjustScale(this.mScaleAnimTarget));
        AnonymousClass1 anonymousClass12 = new AnonymousClass1(this, 0);
        createScaleAnimator2.addListener(anonymousClass12);
        createScaleAnimator2.addUpdateListener(anonymousClass12);
        this.mAnimatorSet.put(2, createScaleAnimator2);
        float f6 = this.mThumbOffsetX;
        float f7 = this.mScaleAnimTarget;
        float f8 = f6 * f7;
        float f9 = this.mScaleAnimSource;
        float f10 = f6 * f9;
        float f11 = f8 - f10;
        float f12 = this.mThumbOffsetY;
        float f13 = f7 * f12;
        float f14 = f12 * f9;
        float f15 = this.mCurrentX - f10;
        float f16 = this.mCurrentY - f14;
        this.mAnimatorSet.put(1, createPositionAnimator(f15, f15 - f11, f16, f16 - (f13 - f14)));
        animatorSet2.playTogether(new ArrayList(this.mAnimatorSet.values()));
        if (CoreRune.MT_DND_SEAMLESS_ANIMATION) {
            this.mNeedAdjustPosition = false;
        }
        windowManagerService.mAnimationHandler.post(
                new DragState$$ExternalSyntheticLambda2(0, animatorSet2));
        return createScaleAnimator2;
    }

    public final ValueAnimator createScaleAnimator(float f, float f2) {
        ValueAnimator ofPropertyValuesHolder =
                ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("scale", f, f2));
        ofPropertyValuesHolder.setDuration(350L);
        ofPropertyValuesHolder.setInterpolator(this.mPositionScaleAnimInterpolator);
        return ofPropertyValuesHolder;
    }

    public final void endDragLocked(boolean z, boolean z2) {
        ValueAnimator ofPropertyValuesHolder;
        long sqrt;
        int i = 0;
        this.mDragResult = z;
        this.mRelinquishDragSurfaceToDropTarget = z2;
        if (this.mAnimator != null) {
            return;
        }
        if ((!z || (CoreRune.MT_DND_ANIMATION && isAnimationSet()))
                && (!CoreRune.MT_DND_DISABLE_CANCEL_ANIMATION || (this.mFlags & 1048576) == 0)) {
            int i2 = this.mFlags;
            if ((i2 & 1024) == 0 && (i2 & 2048) == 0) {
                boolean z3 = CoreRune.MT_DND_ANIMATION;
                if ((z3 && this.mMimeType == 1)
                        || (CoreRune.MT_DND_OBJECT_CAPTURE && this.mIsObjectCapture)) {
                    closeLocked(true);
                }
                if (z3 && isAnimationSet()) {
                    cancelAnimatorAllLocked();
                    ofPropertyValuesHolder = createReleaseAnimationLocked();
                } else {
                    if (this.mCallingTaskIdToHide != -1) {
                        float f = this.mCurrentX;
                        float[] fArr = {f, f};
                        float f2 = this.mCurrentY;
                        float[] fArr2 = {f2, f2};
                        float f3 = this.mAnimatedScale;
                        ofPropertyValuesHolder =
                                ValueAnimator.ofPropertyValuesHolder(
                                        PropertyValuesHolder.ofFloat("x", fArr),
                                        PropertyValuesHolder.ofFloat("y", fArr2),
                                        PropertyValuesHolder.ofFloat("scale", f3, f3),
                                        PropertyValuesHolder.ofFloat(
                                                "alpha",
                                                this.mOriginalAlpha,
                                                FullScreenMagnificationGestureHandler.MAX_SCALE));
                        sqrt = 195;
                    } else {
                        MagnificationSpec magnificationSpec =
                                this.mDisplayContent.hasOneHandOpSpec()
                                        ? this.mDisplayContent.mMagnificationSpec
                                        : null;
                        if (magnificationSpec != null) {
                            float f4 = this.mCurrentX;
                            float f5 = this.mThumbOffsetX;
                            float f6 = magnificationSpec.scale;
                            float f7 = magnificationSpec.offsetX;
                            float[] fArr3 = {
                                ((f4 - f5) * f6) + f7, ((this.mOriginalX - f5) * f6) + f7
                            };
                            float f8 = this.mCurrentY;
                            float f9 = this.mThumbOffsetY;
                            float f10 = magnificationSpec.scale;
                            float f11 = magnificationSpec.offsetY;
                            float[] fArr4 = {
                                ((f8 - f9) * f10) + f11, ((this.mOriginalY - f9) * f10) + f11
                            };
                            float f12 = this.mAnimatedScale * magnificationSpec.scale;
                            float f13 = this.mOriginalAlpha;
                            ofPropertyValuesHolder =
                                    ValueAnimator.ofPropertyValuesHolder(
                                            PropertyValuesHolder.ofFloat("x", fArr3),
                                            PropertyValuesHolder.ofFloat("y", fArr4),
                                            PropertyValuesHolder.ofFloat("scale", f12, f12),
                                            PropertyValuesHolder.ofFloat("alpha", f13, f13 / 2.0f));
                        } else {
                            float f14 = this.mCurrentX;
                            float f15 = this.mThumbOffsetX;
                            float[] fArr5 = {f14 - f15, this.mOriginalX - f15};
                            float f16 = this.mCurrentY;
                            float f17 = this.mThumbOffsetY;
                            float[] fArr6 = {f16 - f17, this.mOriginalY - f17};
                            float f18 = this.mAnimatedScale;
                            float[] fArr7 = {f18, f18};
                            float f19 = this.mOriginalAlpha;
                            ofPropertyValuesHolder =
                                    ValueAnimator.ofPropertyValuesHolder(
                                            PropertyValuesHolder.ofFloat("x", fArr5),
                                            PropertyValuesHolder.ofFloat("y", fArr6),
                                            PropertyValuesHolder.ofFloat("scale", fArr7),
                                            PropertyValuesHolder.ofFloat("alpha", f19, f19 / 2.0f));
                        }
                        float f20 = this.mOriginalX - this.mCurrentX;
                        float f21 = this.mOriginalY - this.mCurrentY;
                        double sqrt2 = Math.sqrt((f21 * f21) + (f20 * f20));
                        Point point = this.mDisplaySize;
                        int i3 = point.x;
                        int i4 = point.y;
                        sqrt = ((long) ((sqrt2 / Math.sqrt((i4 * i4) + (i3 * i3))) * 180.0d)) + 195;
                    }
                    AnimationListener animationListener = new AnimationListener(this, i);
                    ofPropertyValuesHolder.setDuration(sqrt);
                    ofPropertyValuesHolder.setInterpolator(this.mCubicEaseOutInterpolator);
                    ofPropertyValuesHolder.addListener(animationListener);
                    ofPropertyValuesHolder.addUpdateListener(animationListener);
                    this.mService.mAnimationHandler.post(
                            new DragState$$ExternalSyntheticLambda3(ofPropertyValuesHolder, 0));
                }
                this.mAnimator = ofPropertyValuesHolder;
                return;
            }
        }
        closeLocked(false);
    }

    public final DragAndDropPermissionsHandler getPermissionsHandlerIfPossible(
            WindowState windowState) {
        if (windowState == null) {
            return null;
        }
        int i = this.mFlags;
        if ((i & 256) == 0 || (i & 3) == 0 || this.mData == null) {
            return null;
        }
        return new DragAndDropPermissionsHandler(
                this.mService.mGlobalLock,
                this.mData,
                this.mUid,
                windowState.mAttrs.packageName,
                this.mFlags & 3,
                this.mSourceUserId,
                UserHandle.getUserId(windowState.mOwnerUid));
    }

    public final boolean isAnimationSet() {
        return CoreRune.MT_DND_ANIMATION && this.mMimeType != -1;
    }

    public final boolean isWindowNotified(WindowState windowState) {
        Iterator it = this.mNotifiedWindows.iterator();
        while (it.hasNext()) {
            if (((WindowState) it.next()) == windowState) {
                return true;
            }
        }
        return false;
    }

    public final void notifyDownEventLocked() {
        if (!this.mAnimatorSet.isEmpty()) {
            Slog.d("WindowManager", "notifyDownEventLocked(), isAnimating() is true");
            return;
        }
        if ((this.mFlags & 512) == 0) {
            Slog.d("WindowManager", "createPressAnimationLocked");
            AnimatorSet animatorSet = new AnimatorSet();
            this.mAnimatorSet.clear();
            this.mAnimatorSet.put(
                    0,
                    createAlphaAnimator(
                            FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f, 200L));
            if (this.mMimeType == 1) {
                this.mScaleAnimTarget = 0.8f;
                this.mScaleAnimSource = 1.0f;
            } else {
                float f = this.mThumbOffsetX * 2.0f;
                DragDropController dragDropController = this.mDragDropController;
                if (f
                        >= dragDropController.dpToPixel(
                                FrameworkStatsLog
                                        .DEVICE_POLICY_EVENT__EVENT_ID__SET_PREFERENTIAL_NETWORK_SERVICE_ENABLED)) {
                    float dpToPixel = dragDropController.dpToPixel(200) / f;
                    this.mScaleAnimSource = dpToPixel;
                    this.mScaleAnimTarget = dpToPixel * 0.8f;
                } else if (f <= dragDropController.dpToPixel(99)) {
                    float dpToPixel2 = dragDropController.dpToPixel(100) / f;
                    this.mScaleAnimSource = dpToPixel2;
                    this.mScaleAnimTarget = dpToPixel2 * 0.8f;
                }
            }
            float f2 = this.mThumbOffsetX;
            float f3 = this.mScaleAnimSource;
            float f4 = f2 * f3;
            float f5 = this.mScaleAnimTarget;
            float f6 = f4 - (f2 * f5);
            float f7 = this.mThumbOffsetY;
            float f8 = f3 * f7;
            float f9 = f8 - (f7 * f5);
            float f10 = this.mOriginalX - f4;
            this.mTargetX = f10;
            float f11 = this.mOriginalY - f8;
            this.mTargetY = f11;
            float f12 = f6 + f10;
            this.mSourceX = f12;
            float f13 = f9 + f11;
            this.mSourceY = f13;
            this.mAnimatorSet.put(1, createPositionAnimator(f12, f10, f13, f11));
            ValueAnimator createScaleAnimator =
                    createScaleAnimator(
                            adjustScale(this.mScaleAnimTarget), adjustScale(this.mScaleAnimSource));
            AnimationListener animationListener = new AnimationListener(this, 3);
            createScaleAnimator.addListener(animationListener);
            createScaleAnimator.addUpdateListener(animationListener);
            this.mAnimatorSet.put(2, createScaleAnimator);
            animatorSet.playTogether(new ArrayList(this.mAnimatorSet.values()));
            if (CoreRune.MT_DND_SEAMLESS_ANIMATION) {
                this.mNeedAdjustPosition = true;
            }
            this.mService.mAnimationHandler.post(
                    new DragState$$ExternalSyntheticLambda2(0, animatorSet));
        }
    }

    public final void notifyLocationToEavesdropDragEventWindowLocked(float f, float f2) {
        DisplayContent displayContent = this.mDisplayContent;
        displayContent.getClass();
        WindowState window =
                displayContent.getWindow(new DisplayContent$$ExternalSyntheticLambda7(0));
        if (window == null || window.isVisible() || !isWindowNotified(window)) {
            return;
        }
        try {
            int myPid = Process.myPid();
            Slog.d("WindowManager", "sending DRAG_LOCATION to " + window);
            DragEvent obtainDragEvent =
                    obtainDragEvent(2, f, f2, this.mDataDescription, null, false, true, null);
            obtainDragEvent.setEavesDrop(true);
            window.mClient.dispatchDragEvent(obtainDragEvent);
            if (myPid != window.mSession.mPid) {
                obtainDragEvent.recycle();
            }
        } catch (RemoteException unused) {
            Slog.w(
                    "WindowManager",
                    "can't send drag notification to a window eavesdropping dragEvent");
        }
    }

    public final void notifyUpdateClipDataLocked() {
        Slog.d("WindowManager", "notifyUpdateClipDataLocked(), isAnimating() is true");
        Slog.d("WindowManager", "createUpdateClipDataAnimationLocked");
        this.mIsUpdatingClipdata = true;
        AnimatorSet animatorSet = new AnimatorSet();
        final AnimatorSet animatorSet2 = new AnimatorSet();
        float adjustScale = adjustScale(1.0f);
        float adjustScale2 = adjustScale(1.2f);
        float width =
                ((this.mSurfaceControl.getWidth() * adjustScale2) - this.mSurfaceControl.getWidth())
                        / 2.0f;
        if (this.mCurrentAlpha == FullScreenMagnificationGestureHandler.MAX_SCALE) {
            this.mCurrentAlpha = 1.0f;
        }
        Animator createAlphaAnimator = createAlphaAnimator(this.mCurrentAlpha, 1.0f, 100L);
        Animator createAlphaAnimator2 = createAlphaAnimator(1.0f, 0.3f, 300L);
        ValueAnimator createCenteredScaleAnimator =
                createCenteredScaleAnimator(adjustScale, adjustScale2, width, 300L);
        AnimationListener animationListener =
                new AnimationListener() { // from class: com.android.server.wm.DragState.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(DragState.this, 3);
                    }

                    @Override // com.android.server.wm.DragState.AnimationListener,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        animatorSet2.start();
                    }

                    @Override // com.android.server.wm.DragState.AnimationListener,
                              // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        SurfaceControl.Transaction transaction =
                                (SurfaceControl.Transaction)
                                        DragState.this.mService.mTransactionFactory.get();
                        try {
                            if (DragState.this.mSurfaceControl == null) {
                                Slog.d(
                                        "WindowManager",
                                        "mSurfaceControl is null, animation cannot be updated.");
                                if (transaction != null) {
                                    transaction.close();
                                    return;
                                }
                                return;
                            }
                            float floatValue =
                                    ((Float) valueAnimator.getAnimatedValue("pivot_offset"))
                                            .floatValue();
                            DragState dragState = DragState.this;
                            dragState.mCenterPivotOffsetX =
                                    (dragState.mCurrentX - dragState.mThumbOffsetX) - floatValue;
                            dragState.mCenterPivotOffsetY =
                                    (dragState.mCurrentY - dragState.mThumbOffsetY) - floatValue;
                            transaction.setMatrix(
                                    dragState.mSurfaceControl,
                                    ((Float) valueAnimator.getAnimatedValue("scale")).floatValue(),
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    ((Float) valueAnimator.getAnimatedValue("scale")).floatValue());
                            DragState dragState2 = DragState.this;
                            transaction.setPosition(
                                    dragState2.mSurfaceControl,
                                    dragState2.mCenterPivotOffsetX,
                                    dragState2.mCenterPivotOffsetY);
                            transaction.apply();
                            transaction.close();
                        } catch (Throwable th) {
                            if (transaction != null) {
                                try {
                                    transaction.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    }
                };
        createCenteredScaleAnimator.addListener(animationListener);
        createCenteredScaleAnimator.addUpdateListener(animationListener);
        this.mAnimatorSet.put(2, createCenteredScaleAnimator);
        ValueAnimator createCenteredScaleAnimator2 =
                createCenteredScaleAnimator(adjustScale2, adjustScale, width, 500L);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, 1);
        createCenteredScaleAnimator2.addListener(anonymousClass1);
        createCenteredScaleAnimator2.addUpdateListener(anonymousClass1);
        this.mAnimatorSet.put(2, createCenteredScaleAnimator2);
        animatorSet.play(createCenteredScaleAnimator).with(createAlphaAnimator);
        animatorSet2.play(createCenteredScaleAnimator2).with(createAlphaAnimator2);
        this.mService.mAnimationHandler.post(
                new DragState$$ExternalSyntheticLambda2(0, animatorSet));
    }

    public final DragEvent obtainDragEvent(
            int i,
            float f,
            float f2,
            ClipDescription clipDescription,
            ClipData clipData,
            boolean z,
            boolean z2,
            DragAndDropPermissionsHandler dragAndDropPermissionsHandler) {
        return DragEvent.obtain(
                i,
                f,
                f2,
                this.mThumbOffsetX,
                this.mThumbOffsetY,
                z2 ? this.mFlags : 0,
                null,
                clipDescription,
                clipData,
                z ? this.mSurfaceControl : null,
                dragAndDropPermissionsHandler,
                false);
    }

    public final boolean reportDropWindowLock(IBinder iBinder, float f, float f2) {
        if (this.mAnimator != null) {
            return false;
        }
        try {
            Trace.traceBegin(32L, "DragDropController#DROP");
            return reportDropWindowLockInner(iBinder, f, f2);
        } finally {
            Trace.traceEnd(32L);
        }
    }

    public final boolean reportDropWindowLockInner(IBinder iBinder, float f, float f2) {
        if (this.mAnimator != null) {
            return false;
        }
        WindowState windowState = (WindowState) this.mService.mInputToWindowMap.get(iBinder);
        DragEvent createDropEvent = createDropEvent(f, f2, null, true);
        boolean isWindowNotified = isWindowNotified(windowState);
        DragDropController dragDropController = this.mDragDropController;
        if (!isWindowNotified) {
            if (dragDropController.notifyUnhandledDrop(createDropEvent, "unhandled-drop")) {
                return true;
            }
            Trace.traceBegin(32L, "DragDropController#noWindow");
            endDragLocked(false, false);
            Slog.d("WindowManager", "Drop outside a valid window " + windowState);
            Trace.traceEnd(32L);
            return false;
        }
        Slog.d("WindowManager", "Sending DROP to " + windowState);
        IBinder asBinder = windowState.mClient.asBinder();
        DragEvent createDropEvent2 = createDropEvent(f, f2, windowState, false);
        try {
            try {
                Trace.traceBegin(32L, "DragDropController#dispatchDrop");
                windowState.mClient.dispatchDragEvent(createDropEvent2);
                DragDropController.DragHandler dragHandler = dragDropController.mHandler;
                dragHandler.removeMessages(0, asBinder);
                dragHandler.sendMessageDelayed(dragHandler.obtainMessage(0, asBinder), 5000L);
                if (WindowManagerService.MY_PID != windowState.mSession.mPid) {
                    createDropEvent2.recycle();
                }
                Trace.traceEnd(32L);
                this.mToken = asBinder;
                this.mUnhandledDropEvent = createDropEvent;
                return true;
            } catch (RemoteException unused) {
                Slog.w("WindowManager", "can't send drop notification to win " + windowState);
                endDragLocked(false, false);
                if (WindowManagerService.MY_PID != windowState.mSession.mPid) {
                    createDropEvent2.recycle();
                }
                Trace.traceEnd(32L);
                return false;
            }
        } catch (Throwable th) {
            if (WindowManagerService.MY_PID != windowState.mSession.mPid) {
                createDropEvent2.recycle();
            }
            Trace.traceEnd(32L);
            throw th;
        }
    }

    public final void restartDragLocked(ClipData clipData) {
        DragEvent obtainDragEvent;
        ClipData clipData2;
        this.mData = clipData;
        this.mDataDescription = clipData != null ? clipData.getDescription() : null;
        Iterator it = this.mNotifiedWindows.iterator();
        while (it.hasNext()) {
            WindowState windowState = (WindowState) it.next();
            if (targetInterceptsGlobalDrag(windowState)) {
                float f = this.mCurrentX;
                float f2 = this.mCurrentY;
                ClipDescription clipDescription = this.mDataDescription;
                ClipData clipData3 = this.mData;
                if (clipData3 == null) {
                    clipData2 = null;
                } else {
                    ClipData copyForTransferWithActivityInfo =
                            clipData3.copyForTransferWithActivityInfo();
                    copyForTransferWithActivityInfo.setCallingUserId(this.mSourceUserId);
                    copyForTransferWithActivityInfo.setCallingPackageName(this.mCallingPackageName);
                    clipData2 = copyForTransferWithActivityInfo;
                }
                obtainDragEvent =
                        obtainDragEvent(
                                1,
                                f,
                                f2,
                                clipDescription,
                                clipData2,
                                true,
                                false,
                                getPermissionsHandlerIfPossible(windowState));
            } else {
                obtainDragEvent =
                        obtainDragEvent(
                                1,
                                this.mCurrentX,
                                this.mCurrentY,
                                this.mDataDescription,
                                this.mData,
                                false,
                                false,
                                null);
            }
            if ((this.mFlags & 256) != 0) {
                obtainDragEvent.setIsStickyEvent(true);
            }
            try {
                try {
                    windowState.mClient.dispatchDragEventUpdated(obtainDragEvent);
                } catch (RemoteException unused) {
                    Slog.w("WindowManager", "Unable to drag-restart window " + windowState);
                    if (Process.myPid() != windowState.mSession.mPid) {}
                }
                if (Process.myPid() != windowState.mSession.mPid) {
                    obtainDragEvent.recycle();
                }
            } catch (Throwable th) {
                if (Process.myPid() != windowState.mSession.mPid) {
                    obtainDragEvent.recycle();
                }
                throw th;
            }
        }
    }

    public final void sendDragStartedLocked(
            WindowState windowState, float f, float f2, boolean z, boolean z2) {
        ActivityRecord activityRecord;
        ClipData clipData;
        DragEvent obtainDragEvent;
        ClipData clipData2;
        boolean targetInterceptsGlobalDrag = targetInterceptsGlobalDrag(windowState);
        if (!this.mDragInProgress || windowState == null) {
            return;
        }
        boolean z3 = this.mLocalWin == windowState.mClient.asBinder();
        if (z3 || targetInterceptsGlobalDrag || !z) {
            if ((!targetInterceptsGlobalDrag && !windowState.isVisibleNow())
                    || windowState.mRemoved
                    || windowState.mInputChannel == null
                    || windowState.mInputWindowHandle == null) {
                return;
            }
            int i = this.mFlags;
            boolean z4 = (i & 4096) != 0;
            if ((((i & 256) == 0 && !z4)
                            || ((activityRecord = windowState.mActivityRecord) != null
                                    && activityRecord.mTargetSdk < 24))
                    && !z3) {
                return;
            }
            if (!z4 || targetInterceptsGlobalDrag || this.mUid == windowState.mSession.mUid) {
                if (!targetInterceptsGlobalDrag
                        || this.mDisplayContent.mDisplayId == windowState.getDisplayId()) {
                    if (targetInterceptsGlobalDrag
                            || this.mCrossProfileCopyAllowed
                            || this.mSourceUserId == UserHandle.getUserId(windowState.mOwnerUid)) {
                        Slog.d(
                                "WindowManager",
                                "Sending DRAG_STARTED to new window " + windowState);
                        if (targetInterceptsGlobalDrag) {
                            clipData = this.mData.copyForTransferWithActivityInfo();
                            PersistableBundle extras =
                                    clipData.getDescription().getExtras() != null
                                            ? clipData.getDescription().getExtras()
                                            : new PersistableBundle();
                            extras.putInt(
                                    "android.intent.extra.HIDE_DRAG_SOURCE_TASK_ID",
                                    this.mCallingTaskIdToHide);
                            clipData.getDescription().setExtras(extras);
                            DeviceIdleController$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Adding EXTRA_HIDE_DRAG_SOURCE_TASK_ID="),
                                    this.mCallingTaskIdToHide,
                                    "WindowManager");
                        } else {
                            clipData = null;
                        }
                        ClipDescription description =
                                clipData != null
                                        ? clipData.getDescription()
                                        : this.mDataDescription;
                        Rect rect = windowState.mWindowFrames.mFrame;
                        float f3 = f - rect.left;
                        float f4 = windowState.mGlobalScale;
                        if (f4 != 1.0f) {
                            f3 *= windowState.mInvGlobalScale;
                        }
                        float f5 = f2 - rect.top;
                        if (f4 != 1.0f) {
                            f5 *= windowState.mInvGlobalScale;
                        }
                        float f6 = f5;
                        if (targetInterceptsGlobalDrag) {
                            ClipData clipData3 = this.mData;
                            if (clipData3 == null) {
                                clipData2 = null;
                            } else {
                                ClipData copyForTransferWithActivityInfo =
                                        clipData3.copyForTransferWithActivityInfo();
                                copyForTransferWithActivityInfo.setCallingUserId(
                                        this.mSourceUserId);
                                copyForTransferWithActivityInfo.setCallingPackageName(
                                        this.mCallingPackageName);
                                clipData2 = copyForTransferWithActivityInfo;
                            }
                            obtainDragEvent =
                                    obtainDragEvent(
                                            1,
                                            f3,
                                            f6,
                                            description,
                                            clipData2,
                                            true,
                                            true,
                                            getPermissionsHandlerIfPossible(windowState));
                        } else {
                            obtainDragEvent =
                                    obtainDragEvent(
                                            1, f3, f6, description, null, false, false, null);
                        }
                        if (z2 && (this.mFlags & 256) != 0) {
                            obtainDragEvent.setIsStickyEvent(true);
                        }
                        try {
                            try {
                                windowState.mClient.dispatchDragEvent(obtainDragEvent);
                                this.mNotifiedWindows.add(windowState);
                                if (WindowManagerService.MY_PID == windowState.mSession.mPid) {
                                    return;
                                }
                            } catch (RemoteException unused) {
                                Slog.w(
                                        "WindowManager",
                                        "Unable to drag-start window " + windowState);
                                if (WindowManagerService.MY_PID == windowState.mSession.mPid) {
                                    return;
                                }
                            }
                            obtainDragEvent.recycle();
                        } catch (Throwable th) {
                            if (WindowManagerService.MY_PID != windowState.mSession.mPid) {
                                obtainDragEvent.recycle();
                            }
                            throw th;
                        }
                    }
                }
            }
        }
    }

    public final void updateDragSurfaceLocked(boolean z, float f, float f2) {
        boolean z2;
        if (this.mAnimator != null
                || ((z2 = CoreRune.MT_DND_ANIMATION)
                        && isAnimationSet()
                        && (!this.mAnimatorSet.isEmpty())
                        && !this.mApplyAlpha)) {
            if (CoreRune.MT_DND_SEAMLESS_ANIMATION && this.mNeedAdjustPosition) {
                this.mCurrentX = f;
                this.mCurrentY = f2;
                if (this.mAnimatorSet.get(1) == null) {
                    this.mTransaction
                            .setPosition(
                                    this.mSurfaceControl,
                                    adjustPosition(
                                            f - (this.mThumbOffsetX * this.mScaleAnimSource), 0),
                                    adjustPosition(
                                            f2 - (this.mThumbOffsetY * this.mScaleAnimSource), 1))
                            .apply();
                    return;
                }
                return;
            }
            return;
        }
        if ((CoreRune.MT_DND_OBJECT_CAPTURE && this.mIsObjectCapture && !this.mDragMoved)
                || this.mIsUpdatingClipdata) {
            this.mApplyAlpha = true;
            this.mDragMoved = true;
            Slog.d("WindowManager", "skip createMoveAnimation");
            return;
        }
        if (z2 && !this.mDragMoved) {
            float dpToPixel = this.mDragDropController.dpToPixel(10);
            float f3 = this.mOriginalX;
            float f4 = this.mOriginalY;
            if (new RectF(f3 - dpToPixel, f4 - dpToPixel, f3 + dpToPixel, f4 + dpToPixel)
                    .contains(f, f2)) {
                return;
            }
        }
        this.mDragMoved = true;
        float f5 = 1.0f;
        if (this.mDragSplitAppIconHasDrawable) {
            this.mTransaction.setAlpha(
                    this.mSurfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE);
        } else if (z2 && isAnimationSet()) {
            if (!this.mApplyAlpha) {
                if ((this.mFlags & 1048576) != 0) {
                    Slog.d("WindowManager", "skip createMoveAnimationLocked");
                } else {
                    Slog.d("WindowManager", "createMoveAnimationLocked");
                    AnimatorSet animatorSet = new AnimatorSet();
                    this.mAnimatorSet.clear();
                    this.mAnimatorSet.put(0, createAlphaAnimator(1.0f, 0.3f, 150L));
                    animatorSet.playTogether(new ArrayList(this.mAnimatorSet.values()));
                    this.mService.mAnimationHandler.post(
                            new DragState$$ExternalSyntheticLambda2(0, animatorSet));
                }
                this.mApplyAlpha = true;
            }
        } else if (!this.mDragSplitAppIconHasDrawable) {
            this.mTransaction.setAlpha(this.mSurfaceControl, this.mOriginalAlpha);
        }
        if (z2 && this.mMimeType == 0) {
            f5 = this.mScaleAnimSource;
        }
        this.mCurrentX = f;
        this.mCurrentY = f2;
        if (z) {
            if (this.mDisplayContent.hasOneHandOpSpec()) {
                MagnificationSpec magnificationSpec = this.mDisplayContent.mMagnificationSpec;
                if (magnificationSpec != null) {
                    float f6 = f - (this.mThumbOffsetX * f5);
                    float f7 = magnificationSpec.scale;
                    this.mTransaction
                            .setPosition(
                                    this.mSurfaceControl,
                                    (int) ((f6 * f7) + magnificationSpec.offsetX),
                                    (int)
                                            (((f2 - (this.mThumbOffsetY * f5)) * f7)
                                                    + magnificationSpec.offsetY))
                            .apply();
                }
            } else if (z2) {
                this.mTransaction
                        .setPosition(
                                this.mSurfaceControl,
                                f - (this.mThumbOffsetX * f5),
                                f2 - (this.mThumbOffsetY * f5))
                        .apply();
            } else {
                this.mTransaction
                        .setPosition(
                                this.mSurfaceControl,
                                f - this.mThumbOffsetX,
                                f2 - this.mThumbOffsetY)
                        .apply();
            }
            if (ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled[2]) {
                ProtoLogImpl_54989576.i(
                        ProtoLogGroup.WM_SHOW_TRANSACTIONS,
                        12662399232325663L,
                        20,
                        null,
                        String.valueOf(this.mSurfaceControl),
                        Long.valueOf((int) (f - this.mThumbOffsetX)),
                        Long.valueOf((int) (f2 - this.mThumbOffsetY)));
            }
        }
    }
}
