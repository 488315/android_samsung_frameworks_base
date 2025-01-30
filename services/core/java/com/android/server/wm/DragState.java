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
import android.hardware.input.InputManagerGlobal;
import android.os.Binder;
import android.os.IBinder;
import android.os.InputConstants;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.p005os.IInstalld;
import android.util.Slog;
import android.view.Display;
import android.view.DragEvent;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputWindowHandle;
import android.view.SurfaceControl;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.view.IDragAndDropPermissions;
import com.android.server.LocalServices;
import com.android.server.display.DisplayPowerController2;
import com.android.server.pm.UserManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class DragState {
  public ValueAnimator mAnimator;
  public boolean mApplyAlpha;
  public String mCallingPackageName;
  public float mCenterPivotOffsetX;
  public float mCenterPivotOffsetY;
  public boolean mCrossProfileCopyAllowed;
  public float mCurrentX;
  public float mCurrentY;
  public ClipData mData;
  public ClipDescription mDataDescription;
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
  public int mTouchSource;
  public final SurfaceControl.Transaction mTransaction;
  public int mUid;
  public float mAnimatedScale = 1.0f;
  public volatile boolean mAnimationCompleted = false;
  public final Interpolator mCubicEaseOutInterpolator = new DecelerateInterpolator(1.5f);
  public final Point mDisplaySize = new Point();
  public final Rect mTmpClipRect = new Rect();
  public int mMimeType = -1;
  public HashMap mAnimatorSet = new HashMap();
  public float mScaleAnimSource = 1.0f;
  public float mScaleAnimTarget = 0.8f;
  public final PathInterpolator mAlphaAnimInterpolator =
      new PathInterpolator(
          DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
          DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
          1.0f,
          1.0f);
  public final PathInterpolator mPositionScaleAnimInterpolator =
      new PathInterpolator(0.22f, 0.25f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f);
  public float mCurrentAlpha = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public ArrayList mNotifiedWindows = new ArrayList();

  public final float adjustPosition(float f, int i) {
    return f;
  }

  public final float adjustScale(float f) {
    return f;
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
    this.mTransaction = (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
  }

  public boolean isClosing() {
    return this.mIsClosing;
  }

  public final CompletableFuture showInputSurface() {
    if (this.mInputSurface == null) {
      this.mInputSurface =
          this.mService
              .makeSurfaceBuilder(this.mDisplayContent.getSession())
              .setContainerLayer()
              .setName("Drag and Drop Input Consumer")
              .setCallsite("DragState.showInputSurface")
              .setParent(this.mDisplayContent.getOverlayLayer())
              .build();
    }
    InputWindowHandle inputWindowHandle = getInputWindowHandle();
    if (inputWindowHandle == null) {
      Slog.w(
          StartingSurfaceController.TAG, "Drag is in progress but there is no drag window handle.");
      return CompletableFuture.completedFuture(null);
    }
    Rect rect = this.mTmpClipRect;
    Point point = this.mDisplaySize;
    rect.set(0, 0, point.x, point.y);
    this.mTransaction
        .show(this.mInputSurface)
        .setInputWindowInfo(this.mInputSurface, inputWindowHandle)
        .setLayer(this.mInputSurface, Integer.MAX_VALUE)
        .setCrop(this.mInputSurface, this.mTmpClipRect);
    final CompletableFuture completableFuture = new CompletableFuture();
    this.mTransaction
        .addWindowInfosReportedListener(
            new Runnable() { // from class:
                             // com.android.server.wm.DragState$$ExternalSyntheticLambda5
              @Override // java.lang.Runnable
              public final void run() {
                completableFuture.complete(null);
              }
            })
        .apply();
    return completableFuture;
  }

  public void closeLocked() {
    closeLocked(false);
  }

  public void closeLocked(boolean z) {
    SurfaceControl surfaceControl;
    float f;
    float f2;
    if (CoreRune.FW_DND_ANIMATION && !z) {
      this.mIsClosing = true;
      if (this.mInputInterceptor != null) {
        Slog.d(StartingSurfaceController.TAG, "unregistering drag input channel");
        this.mDragDropController.sendHandlerMessage(1, this.mInputInterceptor);
        this.mInputInterceptor = null;
      }
    }
    if (this.mDragInProgress) {
      Slog.d(StartingSurfaceController.TAG, "broadcasting DRAG_ENDED");
      Iterator it = this.mNotifiedWindows.iterator();
      while (it.hasNext()) {
        WindowState windowState = (WindowState) it.next();
        if (this.mDragResult || windowState.mSession.mPid != this.mPid) {
          surfaceControl = null;
          f = 0.0f;
          f2 = 0.0f;
        } else {
          float translateToWindowX = windowState.translateToWindowX(this.mCurrentX);
          float translateToWindowY = windowState.translateToWindowY(this.mCurrentY);
          if (relinquishDragSurfaceToDragSource()) {
            f = translateToWindowX;
            f2 = translateToWindowY;
            surfaceControl = this.mSurfaceControl;
          } else {
            surfaceControl = null;
            f = translateToWindowX;
            f2 = translateToWindowY;
          }
        }
        DragEvent obtain =
            DragEvent.obtain(
                4,
                f,
                f2,
                this.mThumbOffsetX,
                this.mThumbOffsetY,
                null,
                null,
                null,
                surfaceControl,
                null,
                this.mDragResult);
        try {
          windowState.mClient.dispatchDragEvent(obtain);
        } catch (RemoteException unused) {
          Slog.w(StartingSurfaceController.TAG, "Unable to drag-end window " + windowState);
        }
        if (WindowManagerService.MY_PID != windowState.mSession.mPid) {
          obtain.recycle();
        }
      }
      this.mNotifiedWindows.clear();
      this.mDragInProgress = false;
    }
    if (this.mDragInProgressByRecents) {
      this.mDragInProgressByRecents = false;
    }
    if (CoreRune.FW_DND_ANIMATION && z) {
      return;
    }
    if (isFromSource(8194)) {
      this.mService.restorePointerIconLocked(
          this.mDisplayContent, this.mCurrentX, this.mCurrentY, true);
      this.mTouchSource = 0;
    }
    SurfaceControl surfaceControl2 = this.mInputSurface;
    if (surfaceControl2 != null) {
      this.mTransaction.remove(surfaceControl2).apply();
      this.mInputSurface = null;
    }
    if (this.mSurfaceControl != null) {
      if (!this.mRelinquishDragSurfaceToDropTarget && !relinquishDragSurfaceToDragSource()) {
        this.mTransaction.reparent(this.mSurfaceControl, null).apply();
      } else {
        this.mDragDropController.sendTimeoutMessage(3, this.mSurfaceControl, 5000L);
      }
      if (CoreRune.FW_DND_ANIMATION) {
        cancelAnimatorAllLocked();
        if (CoreRune.FW_DND_SEAMLESS_ANIMATION) {
          this.mNeedAdjustPosition = false;
        }
      }
      this.mSurfaceControl = null;
    }
    if ((this.mAnimator != null && !this.mAnimationCompleted)
        || (CoreRune.FW_DND_ANIMATION && isAnimating())) {
      Slog.wtf(
          StartingSurfaceController.TAG,
          "Unexpectedly destroying mSurfaceControl while animation is running");
    }
    this.mFlags = 0;
    this.mLocalWin = null;
    this.mToken = null;
    this.mData = null;
    this.mThumbOffsetY = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    this.mThumbOffsetX = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    this.mNotifiedWindows = null;
    this.mDragDropController.onDragStateClosedLocked(this);
  }

  public void restartDragLocked(ClipData clipData) {
    DragEvent obtainDragEvent;
    this.mData = clipData;
    this.mDataDescription = clipData != null ? clipData.getDescription() : null;
    Iterator it = this.mNotifiedWindows.iterator();
    while (it.hasNext()) {
      WindowState windowState = (WindowState) it.next();
      if (targetInterceptsGlobalDrag(windowState)) {
        obtainDragEvent =
            obtainDragEvent(
                1,
                this.mCurrentX,
                this.mCurrentY,
                createClipDataWithContentsToWindowExtras(this.mData),
                true,
                getPermissionsHandlerIfPossible(windowState));
      } else {
        obtainDragEvent =
            obtainDragEvent(1, this.mCurrentX, this.mCurrentY, this.mData, false, null);
      }
      if ((this.mFlags & 256) != 0) {
        obtainDragEvent.setIsStickyEvent(true);
      }
      try {
        try {
          windowState.mClient.dispatchDragEventUpdated(obtainDragEvent);
        } catch (RemoteException unused) {
          Slog.w(StartingSurfaceController.TAG, "Unable to drag-restart window " + windowState);
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

  public boolean reportDropWindowLock(IBinder iBinder, float f, float f2) {
    ClipData clipData;
    if (this.mAnimator != null) {
      return false;
    }
    WindowState windowState = (WindowState) this.mService.mInputToWindowMap.get(iBinder);
    if (!isWindowNotified(windowState)) {
      this.mDragResult = false;
      endDragLocked();
      Slog.d(StartingSurfaceController.TAG, "Drop outside a valid window " + windowState);
      return false;
    }
    Slog.d(StartingSurfaceController.TAG, "sending DROP to " + windowState);
    int userId = UserHandle.getUserId(windowState.getOwningUid());
    int i = this.mFlags;
    DragAndDropPermissionsHandler dragAndDropPermissionsHandler =
        ((i & 256) == 0 || (i & 3) == 0 || this.mData == null)
            ? null
            : new DragAndDropPermissionsHandler(
                this.mService.mGlobalLock,
                this.mData,
                this.mUid,
                windowState.getOwningPackage(),
                this.mFlags & 195,
                this.mSourceUserId,
                userId);
    int i2 = this.mSourceUserId;
    if (i2 != userId && (clipData = this.mData) != null) {
      clipData.fixUris(i2);
    }
    IBinder asBinder = windowState.mClient.asBinder();
    DragEvent obtainDragEvent =
        obtainDragEvent(
            3,
            f,
            f2,
            this.mData,
            targetInterceptsGlobalDrag(windowState),
            dragAndDropPermissionsHandler);
    try {
      try {
        windowState.mClient.dispatchDragEvent(obtainDragEvent);
        this.mDragDropController.sendTimeoutMessage(0, asBinder, 5000L);
        if (WindowManagerService.MY_PID != windowState.mSession.mPid) {
          obtainDragEvent.recycle();
        }
        this.mToken = asBinder;
        return true;
      } catch (RemoteException unused) {
        Slog.w(StartingSurfaceController.TAG, "can't send drop notification to win " + windowState);
        endDragLocked();
        if (WindowManagerService.MY_PID != windowState.mSession.mPid) {
          obtainDragEvent.recycle();
        }
        return false;
      }
    } catch (Throwable th) {
      if (WindowManagerService.MY_PID != windowState.mSession.mPid) {
        obtainDragEvent.recycle();
      }
      throw th;
    }
  }

  public void setDragSplitAppIconHasDrawable(boolean z) {
    if (this.mDragSplitAppIconHasDrawable != z) {
      this.mTransaction.setAlpha(
          this.mSurfaceControl, z ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : 0.3f);
      this.mDragSplitAppIconHasDrawable = z;
    }
  }

  public class InputInterceptor {
    public InputChannel mClientChannel;
    public InputApplicationHandle mDragApplicationHandle =
        new InputApplicationHandle(
            new Binder(), "drag", InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS);
    public InputWindowHandle mDragWindowHandle;
    public DragInputEventReceiver mInputEventReceiver;

    public InputInterceptor(Display display) {
      this.mClientChannel = DragState.this.mService.mInputManager.createInputChannel("drag");
      this.mInputEventReceiver =
          new DragInputEventReceiver(
              this.mClientChannel,
              DragState.this.mService.f1749mH.getLooper(),
              DragState.this.mDragDropController);
      InputWindowHandle inputWindowHandle =
          new InputWindowHandle(this.mDragApplicationHandle, display.getDisplayId());
      this.mDragWindowHandle = inputWindowHandle;
      inputWindowHandle.name = "drag";
      inputWindowHandle.token = this.mClientChannel.getToken();
      InputWindowHandle inputWindowHandle2 = this.mDragWindowHandle;
      inputWindowHandle2.layoutParamsType = 2016;
      inputWindowHandle2.dispatchingTimeoutMillis =
          InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
      inputWindowHandle2.ownerPid = WindowManagerService.MY_PID;
      inputWindowHandle2.ownerUid = WindowManagerService.MY_UID;
      inputWindowHandle2.scaleFactor = 1.0f;
      inputWindowHandle2.inputConfig = 256;
      if ((DragState.this.mFlags & 256) != 0) {
        inputWindowHandle2.inputConfig = 256 | 256;
      }
      inputWindowHandle2.touchableRegion.setEmpty();
      if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_ORIENTATION, -694710814, 0, (String) null, (Object[]) null);
      }
      DragState.this.mService.mRoot.forAllDisplays(
          new Consumer() { // from class:
                           // com.android.server.wm.DragState$InputInterceptor$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DragState.InputInterceptor.lambda$new$0((DisplayContent) obj);
            }
          });
    }

    public static /* synthetic */ void lambda$new$0(DisplayContent displayContent) {
      displayContent.getDisplayRotation().pause();
    }

    public void tearDown() {
      DragState.this.mService.mInputManager.removeInputChannel(this.mClientChannel.getToken());
      this.mInputEventReceiver.dispose();
      this.mInputEventReceiver = null;
      this.mClientChannel.dispose();
      this.mClientChannel = null;
      this.mDragWindowHandle = null;
      this.mDragApplicationHandle = null;
      if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_ORIENTATION, 269576220, 0, (String) null, (Object[]) null);
      }
      DragState.this.mService.mRoot.forAllDisplays(
          new Consumer() { // from class:
                           // com.android.server.wm.DragState$InputInterceptor$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DragState.InputInterceptor.lambda$tearDown$1((DisplayContent) obj);
            }
          });
    }

    public static /* synthetic */ void lambda$tearDown$1(DisplayContent displayContent) {
      displayContent.getDisplayRotation().resume();
    }
  }

  public InputChannel getInputChannel() {
    InputInterceptor inputInterceptor = this.mInputInterceptor;
    if (inputInterceptor == null) {
      return null;
    }
    return inputInterceptor.mClientChannel;
  }

  public InputWindowHandle getInputWindowHandle() {
    InputInterceptor inputInterceptor = this.mInputInterceptor;
    if (inputInterceptor == null) {
      return null;
    }
    return inputInterceptor.mDragWindowHandle;
  }

  public CompletableFuture register(Display display) {
    display.getRealSize(this.mDisplaySize);
    Slog.d(StartingSurfaceController.TAG, "registering drag input channel");
    if (this.mInputInterceptor != null) {
      Slog.e(StartingSurfaceController.TAG, "Duplicate register of drag input channel");
      return CompletableFuture.completedFuture(null);
    }
    this.mInputInterceptor = new InputInterceptor(display);
    return showInputSurface();
  }

  public void broadcastDragStartedLocked(final float f, final float f2) {
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
            ^ ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class))
                .getUserRestriction(this.mSourceUserId, "no_cross_profile_copy_paste");
    Slog.d(StartingSurfaceController.TAG, "broadcasting DRAG_STARTED at (" + f + ", " + f2 + ")");
    final boolean containsApplicationExtras = containsApplicationExtras(this.mDataDescription);
    this.mService.mRoot.forAllWindows(
        new Consumer() { // from class: com.android.server.wm.DragState$$ExternalSyntheticLambda4
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DragState.this.lambda$broadcastDragStartedLocked$1(
                f, f2, containsApplicationExtras, (WindowState) obj);
          }
        },
        false);
  }

  /* renamed from: sendDragStartedLocked, reason: merged with bridge method [inline-methods] */
  public final void lambda$broadcastDragStartedLocked$1(
      WindowState windowState, float f, float f2, boolean z) {
    sendDragStartedLocked(windowState, f, f2, z, false);
  }

  public final void sendDragStartedLocked(
      WindowState windowState, float f, float f2, boolean z, boolean z2) {
    boolean targetInterceptsGlobalDrag = targetInterceptsGlobalDrag(windowState);
    if (this.mDragInProgress && isValidDropTarget(windowState, z, targetInterceptsGlobalDrag)) {
      if (targetInterceptsGlobalDrag) {
        this.mData.copyForTransferWithActivityInfo();
      }
      DragEvent obtainDragStartedEvent =
          obtainDragStartedEvent(
              windowState,
              windowState.translateToWindowX(f),
              windowState.translateToWindowY(f2),
              targetInterceptsGlobalDrag);
      if (z2 && (this.mFlags & 256) != 0) {
        obtainDragStartedEvent.setIsStickyEvent(true);
      }
      try {
        try {
          windowState.mClient.dispatchDragEvent(obtainDragStartedEvent);
          this.mNotifiedWindows.add(windowState);
          if (WindowManagerService.MY_PID == windowState.mSession.mPid) {
            return;
          }
        } catch (RemoteException unused) {
          Slog.w(StartingSurfaceController.TAG, "Unable to drag-start window " + windowState);
          if (WindowManagerService.MY_PID == windowState.mSession.mPid) {
            return;
          }
        }
        obtainDragStartedEvent.recycle();
      } catch (Throwable th) {
        if (WindowManagerService.MY_PID != windowState.mSession.mPid) {
          obtainDragStartedEvent.recycle();
        }
        throw th;
      }
    }
  }

  public final boolean containsApplicationExtras(ClipDescription clipDescription) {
    if (clipDescription == null) {
      return false;
    }
    return clipDescription.hasMimeType("application/vnd.android.activity")
        || clipDescription.hasMimeType("application/vnd.android.shortcut")
        || clipDescription.hasMimeType("application/vnd.android.task");
  }

  public final boolean isValidDropTarget(WindowState windowState, boolean z, boolean z2) {
    if (windowState == null) {
      return false;
    }
    boolean z3 = this.mLocalWin == windowState.mClient.asBinder();
    if ((!z3 && !z2 && z) || !windowState.isPotentialDragTarget(z2)) {
      return false;
    }
    if (((this.mFlags & 256) == 0 || !targetWindowSupportsGlobalDrag(windowState)) && !z3) {
      return false;
    }
    if (!z2 || this.mDisplayContent.getDisplayId() == windowState.getDisplayId()) {
      return z2
          || this.mCrossProfileCopyAllowed
          || this.mSourceUserId == UserHandle.getUserId(windowState.getOwningUid());
    }
    return false;
  }

  public final boolean targetWindowSupportsGlobalDrag(WindowState windowState) {
    ActivityRecord activityRecord = windowState.mActivityRecord;
    return activityRecord == null || activityRecord.mTargetSdk >= 24;
  }

  public boolean targetInterceptsGlobalDrag(WindowState windowState) {
    return (windowState.mAttrs.privateFlags & Integer.MIN_VALUE) != 0;
  }

  public void sendDragStartedIfNeededLocked(WindowState windowState, boolean z) {
    if (!this.mDragInProgress || isWindowNotified(windowState)) {
      return;
    }
    Slog.d(StartingSurfaceController.TAG, "need to send DRAG_STARTED to new window " + windowState);
    sendDragStartedLocked(
        windowState,
        this.mCurrentX,
        this.mCurrentY,
        containsApplicationExtras(this.mDataDescription),
        z);
  }

  public boolean isWindowNotified(WindowState windowState) {
    Iterator it = this.mNotifiedWindows.iterator();
    while (it.hasNext()) {
      if (((WindowState) it.next()) == windowState) {
        return true;
      }
    }
    return false;
  }

  public void endDragLocked() {
    if (this.mAnimator != null) {
      return;
    }
    if ((!this.mDragResult || (CoreRune.FW_DND_ANIMATION && isAnimationSet()))
        && ((!CoreRune.FW_DND_DISABLE_CANCEL_ANIMATION || (this.mFlags & 1048576) == 0)
            && !isAccessibilityDragDrop()
            && !relinquishDragSurfaceToDragSource())) {
      if ((CoreRune.FW_DND_ANIMATION && this.mMimeType == 1)
          || (CoreRune.FW_DND_OBJECT_CAPTURE && this.mIsObjectCapture)) {
        closeLocked(true);
      }
      this.mAnimator = createReturnAnimationLocked();
      return;
    }
    closeLocked();
  }

  public void cancelDragLocked(boolean z) {
    if (this.mAnimator != null) {
      return;
    }
    if (!this.mDragInProgress || z || isAccessibilityDragDrop()) {
      closeLocked();
    } else {
      this.mAnimator = createCancelAnimationLocked();
    }
  }

  public void updateDragSurfaceLocked(boolean z, float f, float f2) {
    if (this.mAnimator != null
        || (CoreRune.FW_DND_ANIMATION && isAnimationSet() && isAnimating() && !this.mApplyAlpha)) {
      if (CoreRune.FW_DND_SEAMLESS_ANIMATION && this.mNeedAdjustPosition) {
        this.mCurrentX = f;
        this.mCurrentY = f2;
        if (this.mAnimatorSet.get(1) == null) {
          this.mTransaction
              .setPosition(
                  this.mSurfaceControl,
                  adjustPosition(f - (this.mThumbOffsetX * this.mScaleAnimSource), 0),
                  adjustPosition(f2 - (this.mThumbOffsetY * this.mScaleAnimSource), 1))
              .apply();
          return;
        }
        return;
      }
      return;
    }
    if ((CoreRune.FW_DND_OBJECT_CAPTURE && this.mIsObjectCapture && !this.mDragMoved)
        || this.mIsUpdatingClipdata) {
      this.mApplyAlpha = true;
      this.mDragMoved = true;
      Slog.d(StartingSurfaceController.TAG, "skip createMoveAnimation");
      return;
    }
    if (CoreRune.FW_DND_ANIMATION && !this.mDragMoved && hasThresholdArea(f, f2)) {
      return;
    }
    this.mDragMoved = true;
    if (this.mDragSplitAppIconHasDrawable) {
      this.mTransaction.setAlpha(
          this.mSurfaceControl, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
    } else if (CoreRune.FW_DND_ANIMATION && isAnimationSet()) {
      if (!this.mApplyAlpha) {
        createMoveAnimationLocked();
        this.mApplyAlpha = true;
      }
    } else if (!this.mDragSplitAppIconHasDrawable) {
      this.mTransaction.setAlpha(this.mSurfaceControl, this.mOriginalAlpha);
    }
    boolean z2 = CoreRune.FW_DND_ANIMATION;
    float f3 = (z2 && this.mMimeType == 0) ? this.mScaleAnimSource : 1.0f;
    this.mCurrentX = f;
    this.mCurrentY = f2;
    if (z) {
      if (z2) {
        this.mTransaction
            .setPosition(
                this.mSurfaceControl, f - (this.mThumbOffsetX * f3), f2 - (this.mThumbOffsetY * f3))
            .apply();
      } else {
        this.mTransaction
            .setPosition(this.mSurfaceControl, f - this.mThumbOffsetX, f2 - this.mThumbOffsetY)
            .apply();
      }
      if (ProtoLogCache.WM_SHOW_TRANSACTIONS_enabled) {
        ProtoLogImpl.i(
            ProtoLogGroup.WM_SHOW_TRANSACTIONS,
            342460966,
            20,
            (String) null,
            new Object[] {
              String.valueOf(this.mSurfaceControl),
              Long.valueOf((int) (f - this.mThumbOffsetX)),
              Long.valueOf((int) (f2 - this.mThumbOffsetY))
            });
      }
    }
  }

  public final DragEvent obtainDragEvent(
      int i,
      float f,
      float f2,
      ClipData clipData,
      boolean z,
      IDragAndDropPermissions iDragAndDropPermissions) {
    return DragEvent.obtain(
        i,
        f,
        f2,
        this.mThumbOffsetX,
        this.mThumbOffsetY,
        null,
        this.mDataDescription,
        clipData,
        z ? this.mSurfaceControl : null,
        iDragAndDropPermissions,
        false);
  }

  public final DragEvent obtainDragStartedEvent(
      WindowState windowState, float f, float f2, boolean z) {
    if (z) {
      return obtainDragEvent(
          1,
          f,
          f2,
          createClipDataWithContentsToWindowExtras(this.mData),
          true,
          getPermissionsHandlerIfPossible(windowState));
    }
    return obtainDragEvent(1, f, f2, null, false, null);
  }

  public final ClipData createClipDataWithContentsToWindowExtras(ClipData clipData) {
    if (clipData == null) {
      return null;
    }
    ClipData copyForTransferWithActivityInfo = clipData.copyForTransferWithActivityInfo();
    copyForTransferWithActivityInfo.setCallingUserId(this.mSourceUserId);
    copyForTransferWithActivityInfo.setCallingPackageName(this.mCallingPackageName);
    return copyForTransferWithActivityInfo;
  }

  public final IDragAndDropPermissions getPermissionsHandlerIfPossible(WindowState windowState) {
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
        windowState.getOwningPackage(),
        this.mFlags & 3,
        this.mSourceUserId,
        UserHandle.getUserId(windowState.getOwningUid()));
  }

  public void notifyLocationToEavesdropDragEventWindowLocked(float f, float f2) {
    WindowState windowEavesdropDragEvent = this.mDisplayContent.getWindowEavesdropDragEvent();
    if (windowEavesdropDragEvent == null
        || windowEavesdropDragEvent.isVisible()
        || !isWindowNotified(windowEavesdropDragEvent)) {
      return;
    }
    try {
      int myPid = Process.myPid();
      Slog.d(StartingSurfaceController.TAG, "sending DRAG_LOCATION to " + windowEavesdropDragEvent);
      DragEvent obtainDragEvent = obtainDragEvent(2, f, f2, null, false, null);
      obtainDragEvent.setEavesDrop(true);
      windowEavesdropDragEvent.mClient.dispatchDragEvent(obtainDragEvent);
      if (myPid != windowEavesdropDragEvent.mSession.mPid) {
        obtainDragEvent.recycle();
      }
    } catch (RemoteException unused) {
      Slog.w(
          StartingSurfaceController.TAG,
          "can't send drag notification to a window eavesdropping dragEvent");
    }
  }

  public final ValueAnimator createReturnAnimationLocked() {
    if (CoreRune.FW_DND_ANIMATION && isAnimationSet()) {
      cancelAnimatorAllLocked();
      return createReleaseAnimationLocked();
    }
    float f = this.mCurrentX;
    float f2 = this.mThumbOffsetX;
    PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("x", f - f2, this.mOriginalX - f2);
    float f3 = this.mCurrentY;
    float f4 = this.mThumbOffsetY;
    PropertyValuesHolder ofFloat2 =
        PropertyValuesHolder.ofFloat("y", f3 - f4, this.mOriginalY - f4);
    PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat("scale", 1.0f, 1.0f);
    float f5 = this.mOriginalAlpha;
    final ValueAnimator ofPropertyValuesHolder =
        ValueAnimator.ofPropertyValuesHolder(
            ofFloat, ofFloat2, ofFloat3, PropertyValuesHolder.ofFloat("alpha", f5, f5 / 2.0f));
    float f6 = this.mOriginalX - this.mCurrentX;
    float f7 = this.mOriginalY - this.mCurrentY;
    double sqrt = Math.sqrt((f6 * f6) + (f7 * f7));
    Point point = this.mDisplaySize;
    int i = point.x;
    int i2 = point.y;
    long sqrt2 = ((long) ((sqrt / Math.sqrt((i * i) + (i2 * i2))) * 180.0d)) + 195;
    AnimationListener animationListener = new AnimationListener();
    ofPropertyValuesHolder.setDuration(sqrt2);
    ofPropertyValuesHolder.setInterpolator(this.mCubicEaseOutInterpolator);
    ofPropertyValuesHolder.addListener(animationListener);
    ofPropertyValuesHolder.addUpdateListener(animationListener);
    this.mService.mAnimationHandler.post(
        new Runnable() { // from class: com.android.server.wm.DragState$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            ofPropertyValuesHolder.start();
          }
        });
    return ofPropertyValuesHolder;
  }

  public final ValueAnimator createCancelAnimationLocked() {
    if (CoreRune.FW_DND_ANIMATION && isAnimationSet()) {
      cancelAnimatorAllLocked();
      return createReleaseAnimationLocked();
    }
    float f = this.mCurrentX;
    PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("x", f - this.mThumbOffsetX, f);
    float f2 = this.mCurrentY;
    final ValueAnimator ofPropertyValuesHolder =
        ValueAnimator.ofPropertyValuesHolder(
            ofFloat,
            PropertyValuesHolder.ofFloat("y", f2 - this.mThumbOffsetY, f2),
            PropertyValuesHolder.ofFloat(
                "scale", 1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON),
            PropertyValuesHolder.ofFloat(
                "alpha", this.mOriginalAlpha, DisplayPowerController2.RATE_FROM_DOZE_TO_ON));
    AnimationListener animationListener = new AnimationListener();
    ofPropertyValuesHolder.setDuration(195L);
    ofPropertyValuesHolder.setInterpolator(this.mCubicEaseOutInterpolator);
    ofPropertyValuesHolder.addListener(animationListener);
    ofPropertyValuesHolder.addUpdateListener(animationListener);
    this.mService.mAnimationHandler.post(
        new Runnable() { // from class: com.android.server.wm.DragState$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            ofPropertyValuesHolder.start();
          }
        });
    return ofPropertyValuesHolder;
  }

  public void cancelAnimatorAllLocked() {
    this.mAnimatorSet.forEach(
        new BiConsumer() { // from class: com.android.server.wm.DragState$$ExternalSyntheticLambda1
          @Override // java.util.function.BiConsumer
          public final void accept(Object obj, Object obj2) {
            DragState.lambda$cancelAnimatorAllLocked$4((Integer) obj, (ValueAnimator) obj2);
          }
        });
    this.mAnimatorSet.clear();
  }

  public static /* synthetic */ void lambda$cancelAnimatorAllLocked$4(
      Integer num, ValueAnimator valueAnimator) {
    try {
      valueAnimator.cancel();
      valueAnimator.removeAllListeners();
      valueAnimator.removeAllUpdateListeners();
    } catch (Exception e) {
      Slog.w(StartingSurfaceController.TAG, "Unable to cancel animator", e);
    }
  }

  public void endAnimator(final int i) {
    this.mService.f1749mH.post(
        new Runnable() { // from class: com.android.server.wm.DragState$$ExternalSyntheticLambda6
          @Override // java.lang.Runnable
          public final void run() {
            DragState.this.lambda$endAnimator$5(i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$endAnimator$5(int i) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mAnimatorSet.remove(Integer.valueOf(i));
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public final void calculateScale() {
    if (this.mMimeType == 1) {
      this.mScaleAnimTarget = 0.8f;
      this.mScaleAnimSource = 1.0f;
      return;
    }
    float f = this.mThumbOffsetX * 2.0f;
    if (f >= this.mDragDropController.dpToPixel(FrameworkStatsLog.f690xa4f1b8b3)) {
      float dpToPixel = this.mDragDropController.dpToPixel(200) / f;
      this.mScaleAnimSource = dpToPixel;
      this.mScaleAnimTarget = dpToPixel * 0.8f;
    } else if (f <= this.mDragDropController.dpToPixel(99)) {
      float dpToPixel2 = this.mDragDropController.dpToPixel(100) / f;
      this.mScaleAnimSource = dpToPixel2;
      this.mScaleAnimTarget = dpToPixel2 * 0.8f;
    }
  }

  public void notifyDownEventLocked() {
    if (isAnimating()) {
      Slog.d(StartingSurfaceController.TAG, "notifyDownEventLocked(), isAnimating() is true");
    } else if ((this.mFlags & 512) == 0) {
      createPressAnimationLocked();
    }
  }

  public void notifyUpdateClipDataLocked() {
    Slog.d(StartingSurfaceController.TAG, "notifyUpdateClipDataLocked(), isAnimating() is true");
    createUpdateClipDataAnimationLocked();
  }

  public final ValueAnimator createCenteredScaleAnimator(
      float f, float f2, float f3, float f4, long j) {
    ValueAnimator ofPropertyValuesHolder =
        ValueAnimator.ofPropertyValuesHolder(
            PropertyValuesHolder.ofFloat("scale", f, f2),
            PropertyValuesHolder.ofFloat("pivot_offset", f3, f4));
    ofPropertyValuesHolder.setDuration(j);
    ofPropertyValuesHolder.setInterpolator(this.mPositionScaleAnimInterpolator);
    return ofPropertyValuesHolder;
  }

  public boolean isAnimationSet() {
    return CoreRune.FW_DND_ANIMATION && this.mMimeType != -1;
  }

  public final boolean isAnimating() {
    return !this.mAnimatorSet.isEmpty();
  }

  public final boolean hasThresholdArea(float f, float f2) {
    float dpToPixel = this.mDragDropController.dpToPixel(10);
    float f3 = this.mOriginalX;
    float f4 = this.mOriginalY;
    return new RectF(f3 - dpToPixel, f4 - dpToPixel, f3 + dpToPixel, f4 + dpToPixel)
        .contains(f, f2);
  }

  public final ValueAnimator createAlphaAnimator(float f, float f2, long j, long j2) {
    ValueAnimator ofPropertyValuesHolder =
        ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("alpha", f, f2));
    ofPropertyValuesHolder.setStartDelay(j2);
    ofPropertyValuesHolder.setDuration(j);
    ofPropertyValuesHolder.setInterpolator(this.mAlphaAnimInterpolator);
    AlphaAnimationListener alphaAnimationListener = new AlphaAnimationListener();
    ofPropertyValuesHolder.addListener(alphaAnimationListener);
    ofPropertyValuesHolder.addUpdateListener(alphaAnimationListener);
    return ofPropertyValuesHolder;
  }

  public final ValueAnimator createScaleAnimator(float f, float f2, long j) {
    ValueAnimator ofPropertyValuesHolder =
        ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("scale", f, f2));
    ofPropertyValuesHolder.setDuration(j);
    ofPropertyValuesHolder.setInterpolator(this.mPositionScaleAnimInterpolator);
    return ofPropertyValuesHolder;
  }

  public final ValueAnimator createPositionAnimator(float f, float f2, float f3, float f4, long j) {
    ValueAnimator ofPropertyValuesHolder =
        ValueAnimator.ofPropertyValuesHolder(
            PropertyValuesHolder.ofFloat("x", adjustPosition(f, 0), adjustPosition(f2, 0)),
            PropertyValuesHolder.ofFloat("y", adjustPosition(f3, 1), adjustPosition(f4, 1)));
    ofPropertyValuesHolder.setDuration(j);
    ofPropertyValuesHolder.setInterpolator(this.mPositionScaleAnimInterpolator);
    PositionAnimationListener positionAnimationListener = new PositionAnimationListener();
    ofPropertyValuesHolder.addListener(positionAnimationListener);
    ofPropertyValuesHolder.addUpdateListener(positionAnimationListener);
    return ofPropertyValuesHolder;
  }

  public final void createMoveAnimationLocked() {
    if ((this.mFlags & 1048576) != 0) {
      Slog.d(StartingSurfaceController.TAG, "skip createMoveAnimationLocked");
      return;
    }
    Slog.d(StartingSurfaceController.TAG, "createMoveAnimationLocked");
    AnimatorSet animatorSet = new AnimatorSet();
    this.mAnimatorSet.clear();
    this.mAnimatorSet.put(0, createAlphaAnimator(1.0f, 0.3f, 150L, 0L));
    animatorSet.playTogether(new ArrayList(this.mAnimatorSet.values()));
    this.mService.mAnimationHandler.post(new DragState$$ExternalSyntheticLambda3(animatorSet));
  }

  public final void createPressAnimationLocked() {
    Slog.d(StartingSurfaceController.TAG, "createPressAnimationLocked");
    AnimatorSet animatorSet = new AnimatorSet();
    this.mAnimatorSet.clear();
    this.mAnimatorSet.put(
        0, createAlphaAnimator(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f, 200L, 0L));
    calculateScale();
    float f = this.mThumbOffsetX;
    float f2 = this.mScaleAnimSource;
    float f3 = this.mScaleAnimTarget;
    float f4 = this.mThumbOffsetY;
    float f5 = this.mOriginalX - (f * f2);
    this.mTargetX = f5;
    float f6 = this.mOriginalY - (f4 * f2);
    this.mTargetY = f6;
    float f7 = f5 + ((f * f2) - (f * f3));
    this.mSourceX = f7;
    float f8 = f6 + ((f4 * f2) - (f3 * f4));
    this.mSourceY = f8;
    this.mAnimatorSet.put(1, createPositionAnimator(f7, f5, f8, f6, 350L));
    ValueAnimator createScaleAnimator =
        createScaleAnimator(
            adjustScale(this.mScaleAnimTarget), adjustScale(this.mScaleAnimSource), 350L);
    ScaleAnimationListener scaleAnimationListener = new ScaleAnimationListener();
    createScaleAnimator.addListener(scaleAnimationListener);
    createScaleAnimator.addUpdateListener(scaleAnimationListener);
    this.mAnimatorSet.put(2, createScaleAnimator);
    animatorSet.playTogether(new ArrayList(this.mAnimatorSet.values()));
    if (CoreRune.FW_DND_SEAMLESS_ANIMATION) {
      this.mNeedAdjustPosition = true;
    }
    this.mService.mAnimationHandler.post(new DragState$$ExternalSyntheticLambda3(animatorSet));
  }

  public final ValueAnimator createReleaseAnimationLocked() {
    if (CoreRune.FW_DND_OBJECT_CAPTURE && this.mIsObjectCapture && !this.mDragResult) {
      return createObjectReleaseAnimationLocked();
    }
    Slog.d(StartingSurfaceController.TAG, "createReleaseAnimationLocked");
    AnimatorSet animatorSet = new AnimatorSet();
    this.mAnimatorSet.clear();
    this.mAnimatorSet.put(
        0, createAlphaAnimator(0.3f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 150L, 0L));
    ValueAnimator createScaleAnimator =
        createScaleAnimator(
            adjustScale(this.mScaleAnimSource), adjustScale(this.mScaleAnimTarget), 350L);
    ScaleAnimationListener scaleAnimationListener =
        new ScaleAnimationListener() { // from class: com.android.server.wm.DragState.1
          @Override // com.android.server.wm.DragState.ScaleAnimationListener,
                    // android.animation.Animator.AnimatorListener
          public void onAnimationEnd(Animator animator) {
            DragState.this.endAnimator(2);
            DragState.this.mDragDropController.sendHandlerMessage(2, null);
          }
        };
    createScaleAnimator.addListener(scaleAnimationListener);
    createScaleAnimator.addUpdateListener(scaleAnimationListener);
    this.mAnimatorSet.put(2, createScaleAnimator);
    float f = this.mThumbOffsetX;
    float f2 = this.mScaleAnimTarget;
    float f3 = this.mScaleAnimSource;
    float f4 = (f * f2) - (f * f3);
    float f5 = this.mThumbOffsetY;
    float f6 = (f2 * f5) - (f5 * f3);
    float f7 = this.mCurrentX - (f * f3);
    float f8 = this.mCurrentY - (f5 * f3);
    this.mAnimatorSet.put(1, createPositionAnimator(f7, f7 - f4, f8, f8 - f6, 350L));
    animatorSet.playTogether(new ArrayList(this.mAnimatorSet.values()));
    if (CoreRune.FW_DND_SEAMLESS_ANIMATION) {
      this.mNeedAdjustPosition = false;
    }
    this.mService.mAnimationHandler.post(new DragState$$ExternalSyntheticLambda3(animatorSet));
    return createScaleAnimator;
  }

  public final void createUpdateClipDataAnimationLocked() {
    Slog.d(StartingSurfaceController.TAG, "createUpdateClipDataAnimationLocked");
    this.mIsUpdatingClipdata = true;
    AnimatorSet animatorSet = new AnimatorSet();
    final AnimatorSet animatorSet2 = new AnimatorSet();
    float adjustScale = adjustScale(1.0f);
    float adjustScale2 = adjustScale(1.2f);
    float width =
        ((this.mSurfaceControl.getWidth() * adjustScale2) - this.mSurfaceControl.getWidth()) / 2.0f;
    if (this.mCurrentAlpha == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      this.mCurrentAlpha = 1.0f;
    }
    Animator createAlphaAnimator = createAlphaAnimator(this.mCurrentAlpha, 1.0f, 100L, 0L);
    Animator createAlphaAnimator2 = createAlphaAnimator(1.0f, 0.3f, 300L, 0L);
    ValueAnimator createCenteredScaleAnimator =
        createCenteredScaleAnimator(
            adjustScale, adjustScale2, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, width, 300L);
    ScaleAnimationListener scaleAnimationListener =
        new ScaleAnimationListener() { // from class: com.android.server.wm.DragState.2
          /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
          {
            super();
          }

          @Override // com.android.server.wm.DragState.ScaleAnimationListener,
                    // android.animation.ValueAnimator.AnimatorUpdateListener
          public void onAnimationUpdate(ValueAnimator valueAnimator) {
            SurfaceControl.Transaction transaction =
                (SurfaceControl.Transaction) DragState.this.mService.mTransactionFactory.get();
            try {
              if (DragState.this.mSurfaceControl == null) {
                Slog.d(
                    StartingSurfaceController.TAG,
                    "mSurfaceControl is null, animation cannot be updated.");
                if (transaction != null) {
                  transaction.close();
                  return;
                }
                return;
              }
              float floatValue =
                  ((Float) valueAnimator.getAnimatedValue("pivot_offset")).floatValue();
              DragState dragState = DragState.this;
              dragState.mCenterPivotOffsetX =
                  (dragState.mCurrentX - dragState.mThumbOffsetX) - floatValue;
              DragState dragState2 = DragState.this;
              dragState2.mCenterPivotOffsetY =
                  (dragState2.mCurrentY - dragState2.mThumbOffsetY) - floatValue;
              transaction.setMatrix(
                  DragState.this.mSurfaceControl,
                  ((Float) valueAnimator.getAnimatedValue("scale")).floatValue(),
                  DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
                  DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
                  ((Float) valueAnimator.getAnimatedValue("scale")).floatValue());
              DragState dragState3 = DragState.this;
              transaction.setPosition(
                  dragState3.mSurfaceControl,
                  dragState3.mCenterPivotOffsetX,
                  DragState.this.mCenterPivotOffsetY);
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

          @Override // com.android.server.wm.DragState.ScaleAnimationListener,
                    // android.animation.Animator.AnimatorListener
          public void onAnimationEnd(Animator animator) {
            animatorSet2.start();
          }
        };
    createCenteredScaleAnimator.addListener(scaleAnimationListener);
    createCenteredScaleAnimator.addUpdateListener(scaleAnimationListener);
    ValueAnimator createCenteredScaleAnimator2 =
        createCenteredScaleAnimator(
            adjustScale2, adjustScale, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, width, 500L);
    ScaleAnimationListener scaleAnimationListener2 =
        new ScaleAnimationListener() { // from class: com.android.server.wm.DragState.3
          @Override // com.android.server.wm.DragState.ScaleAnimationListener,
                    // android.animation.ValueAnimator.AnimatorUpdateListener
          public void onAnimationUpdate(ValueAnimator valueAnimator) {
            SurfaceControl.Transaction transaction =
                (SurfaceControl.Transaction) DragState.this.mService.mTransactionFactory.get();
            try {
              if (DragState.this.mSurfaceControl == null) {
                Slog.d(
                    StartingSurfaceController.TAG,
                    "mSurfaceControl is null, animation cannot be updated.");
                if (transaction != null) {
                  transaction.close();
                  return;
                }
                return;
              }
              float floatValue =
                  ((Float) valueAnimator.getAnimatedValue("pivot_offset")).floatValue();
              transaction.setMatrix(
                  DragState.this.mSurfaceControl,
                  ((Float) valueAnimator.getAnimatedValue("scale")).floatValue(),
                  DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
                  DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
                  ((Float) valueAnimator.getAnimatedValue("scale")).floatValue());
              DragState dragState = DragState.this;
              transaction.setPosition(
                  dragState.mSurfaceControl,
                  dragState.mCenterPivotOffsetX + floatValue,
                  DragState.this.mCenterPivotOffsetY + floatValue);
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

          @Override // com.android.server.wm.DragState.ScaleAnimationListener,
                    // android.animation.Animator.AnimatorListener
          public void onAnimationEnd(Animator animator) {
            DragState.this.mIsUpdatingClipdata = false;
          }
        };
    createCenteredScaleAnimator2.addListener(scaleAnimationListener2);
    createCenteredScaleAnimator2.addUpdateListener(scaleAnimationListener2);
    animatorSet.play(createCenteredScaleAnimator).with(createAlphaAnimator);
    animatorSet2.play(createCenteredScaleAnimator2).with(createAlphaAnimator2);
    this.mService.mAnimationHandler.post(new DragState$$ExternalSyntheticLambda3(animatorSet));
  }

  public class PositionAnimationListener
      implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {}

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {}

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {}

    public PositionAnimationListener() {}

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
      SurfaceControl.Transaction transaction =
          (SurfaceControl.Transaction) DragState.this.mService.mTransactionFactory.get();
      try {
        float floatValue = ((Float) valueAnimator.getAnimatedValue("x")).floatValue();
        float floatValue2 = ((Float) valueAnimator.getAnimatedValue("y")).floatValue();
        if (CoreRune.FW_DND_ANIMATION && DragState.this.mNeedAdjustPosition) {
          DragState dragState = DragState.this;
          float adjustPosition = dragState.adjustPosition(dragState.mTargetX, 0);
          DragState dragState2 = DragState.this;
          float calculateDistance =
              dragState.calculateDistance(
                  floatValue,
                  floatValue2,
                  adjustPosition,
                  dragState2.adjustPosition(dragState2.mTargetY, 1));
          DragState dragState3 = DragState.this;
          float adjustPosition2 = dragState3.adjustPosition(dragState3.mSourceX, 0);
          DragState dragState4 = DragState.this;
          float adjustPosition3 = dragState4.adjustPosition(dragState4.mSourceY, 1);
          DragState dragState5 = DragState.this;
          float adjustPosition4 = dragState5.adjustPosition(dragState5.mTargetX, 0);
          DragState dragState6 = DragState.this;
          float calculateDistance2 =
              dragState3.calculateDistance(
                  adjustPosition2,
                  adjustPosition3,
                  adjustPosition4,
                  dragState6.adjustPosition(dragState6.mTargetY, 1));
          float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
          if (calculateDistance2 != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            f = calculateDistance / calculateDistance2;
          }
          DragState dragState7 = DragState.this;
          float adjustPosition5 =
              dragState7.adjustPosition(
                  dragState7.mCurrentX - (dragState7.mThumbOffsetX * dragState7.mScaleAnimSource),
                  0);
          DragState dragState8 = DragState.this;
          float adjustPosition6 =
              dragState8.adjustPosition(
                  dragState8.mCurrentY - (dragState8.mThumbOffsetY * dragState8.mScaleAnimSource),
                  1);
          DragState dragState9 = DragState.this;
          float adjustPosition7 =
              adjustPosition5
                  + ((dragState9.adjustPosition(dragState9.mSourceX, 0) - adjustPosition5) * f);
          DragState dragState10 = DragState.this;
          float adjustPosition8 =
              adjustPosition6
                  + ((dragState10.adjustPosition(dragState10.mSourceY, 1) - adjustPosition6) * f);
          floatValue = adjustPosition7;
          floatValue2 = adjustPosition8;
        }
        SurfaceControl surfaceControl = DragState.this.mSurfaceControl;
        if (surfaceControl == null) {
          Slog.d(
              StartingSurfaceController.TAG,
              "mSurfaceControl is null, animation cannot be updated.");
          if (transaction != null) {
            transaction.close();
            return;
          }
          return;
        }
        transaction.setPosition(surfaceControl, floatValue, floatValue2);
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

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
      DragState.this.endAnimator(1);
      if (CoreRune.FW_DND_SEAMLESS_ANIMATION) {
        DragState.this.mNeedAdjustPosition = false;
      }
    }
  }

  public class ScaleAnimationListener
      implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {}

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {}

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {}

    public ScaleAnimationListener() {}

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
      SurfaceControl.Transaction transaction =
          (SurfaceControl.Transaction) DragState.this.mService.mTransactionFactory.get();
      try {
        SurfaceControl surfaceControl = DragState.this.mSurfaceControl;
        if (surfaceControl == null) {
          Slog.d(
              StartingSurfaceController.TAG,
              "mSurfaceControl is null, animation cannot be updated.");
          if (transaction != null) {
            transaction.close();
            return;
          }
          return;
        }
        transaction.setMatrix(
            surfaceControl,
            ((Float) valueAnimator.getAnimatedValue("scale")).floatValue(),
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            ((Float) valueAnimator.getAnimatedValue("scale")).floatValue());
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

    public void onAnimationEnd(Animator animator) {
      DragState.this.endAnimator(2);
    }
  }

  public class AlphaAnimationListener
      implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {}

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {}

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {}

    public AlphaAnimationListener() {}

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
      SurfaceControl.Transaction transaction =
          (SurfaceControl.Transaction) DragState.this.mService.mTransactionFactory.get();
      try {
        DragState.this.mCurrentAlpha =
            ((Float) valueAnimator.getAnimatedValue("alpha")).floatValue();
        DragState dragState = DragState.this;
        SurfaceControl surfaceControl = dragState.mSurfaceControl;
        if (surfaceControl == null) {
          Slog.d(
              StartingSurfaceController.TAG,
              "mSurfaceControl is null, animation cannot be updated.");
          if (transaction != null) {
            transaction.close();
            return;
          }
          return;
        }
        transaction.setAlpha(surfaceControl, dragState.mCurrentAlpha);
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

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
      DragState.this.endAnimator(0);
    }
  }

  public final float calculateDistance(float f, float f2, float f3, float f4) {
    return (float) Math.sqrt(Math.pow(Math.abs(f - f3), 2.0d) + Math.pow(Math.abs(f2 - f4), 2.0d));
  }

  public final ValueAnimator createObjectReleaseAnimationLocked() {
    float width;
    Slog.d(StartingSurfaceController.TAG, "createObjectReleaseAnimationLocked");
    AnimatorSet animatorSet = new AnimatorSet();
    this.mAnimatorSet.clear();
    int width2 = this.mSurfaceControl.getWidth();
    if (width2 < 1) {
      Slog.d(StartingSurfaceController.TAG, "dragSurfaceWidth is wrong!!, width: " + width2);
      width = 1.0f;
    } else {
      RectF rectF = this.mObjectCaptureRect;
      width = (rectF.right - rectF.left) / this.mSurfaceControl.getWidth();
    }
    ValueAnimator createScaleAnimator = createScaleAnimator(1.0f, adjustScale(width), 350L);
    ScaleAnimationListener scaleAnimationListener =
        new ScaleAnimationListener() { // from class: com.android.server.wm.DragState.4
          @Override // com.android.server.wm.DragState.ScaleAnimationListener,
                    // android.animation.Animator.AnimatorListener
          public void onAnimationEnd(Animator animator) {
            DragState.this.endAnimator(2);
            DragState.this.mDragDropController.sendHandlerMessage(2, null);
          }
        };
    createScaleAnimator.addListener(scaleAnimationListener);
    createScaleAnimator.addUpdateListener(scaleAnimationListener);
    this.mAnimatorSet.put(2, createScaleAnimator);
    float f = this.mCurrentX;
    float f2 = this.mThumbOffsetX;
    float f3 = this.mScaleAnimSource;
    float f4 = f - (f2 * f3);
    float f5 = this.mCurrentY - (this.mThumbOffsetY * f3);
    RectF rectF2 = this.mObjectCaptureRect;
    this.mAnimatorSet.put(1, createPositionAnimator(f4, rectF2.left, f5, rectF2.top, 350L));
    animatorSet.playTogether(new ArrayList(this.mAnimatorSet.values()));
    if (CoreRune.FW_DND_SEAMLESS_ANIMATION) {
      this.mNeedAdjustPosition = false;
    }
    this.mService.mAnimationHandler.post(new DragState$$ExternalSyntheticLambda3(animatorSet));
    return createScaleAnimator;
  }

  public final boolean isFromSource(int i) {
    return (this.mTouchSource & i) == i;
  }

  public void overridePointerIconLocked(int i) {
    this.mTouchSource = i;
    if (isFromSource(8194)) {
      InputManagerGlobal.getInstance().setPointerIconType(1021);
    }
  }

  public class AnimationListener
      implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {}

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {}

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {}

    public AnimationListener() {}

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
      SurfaceControl.Transaction transaction =
          (SurfaceControl.Transaction) DragState.this.mService.mTransactionFactory.get();
      try {
        SurfaceControl surfaceControl = DragState.this.mSurfaceControl;
        if (surfaceControl == null) {
          Slog.d(
              StartingSurfaceController.TAG,
              "mSurfaceControl is null, animation cannot be updated.");
          if (transaction != null) {
            transaction.close();
            return;
          }
          return;
        }
        transaction.setPosition(
            surfaceControl,
            ((Float) valueAnimator.getAnimatedValue("x")).floatValue(),
            ((Float) valueAnimator.getAnimatedValue("y")).floatValue());
        transaction.setAlpha(
            DragState.this.mSurfaceControl,
            ((Float) valueAnimator.getAnimatedValue("alpha")).floatValue());
        transaction.setMatrix(
            DragState.this.mSurfaceControl,
            ((Float) valueAnimator.getAnimatedValue("scale")).floatValue(),
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            ((Float) valueAnimator.getAnimatedValue("scale")).floatValue());
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

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
      DragState.this.mAnimationCompleted = true;
      DragState.this.mDragDropController.sendHandlerMessage(2, null);
    }
  }

  public boolean isAccessibilityDragDrop() {
    return (this.mFlags & 1024) != 0;
  }

  public final boolean relinquishDragSurfaceToDragSource() {
    return (this.mFlags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0;
  }
}
