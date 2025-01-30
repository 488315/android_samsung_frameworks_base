package com.android.server.wm;

import android.R;
import android.app.ActivityOptions;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Slog;
import android.util.SparseArray;
import android.view.KeyEvent;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class DexMetaKeyPolicy {
  public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
  public final ActivityTaskManagerService mAtm;
  public final DexController mDexController;
  public final Rect mTaskBounds = new Rect();
  public final Rect mDisplayBounds = new Rect();
  public final Rect mLeftHalfDisplayBounds = new Rect();
  public final Rect mRightHalfDisplayBounds = new Rect();
  public final Rect mLeftDockingBounds = new Rect();
  public final Rect mRightDockingBounds = new Rect();
  public final Rect mLeftTopQuarterDisplayBounds = new Rect();
  public final Rect mLeftBottomQuarterDisplayBounds = new Rect();
  public final Rect mRightTopQuarterDisplayBounds = new Rect();
  public final Rect mRightBottomQuarterDisplayBounds = new Rect();
  public final Rect mMinHeightBounds = new Rect();
  public final Rect mMaxHeightBounds = new Rect();
  public Task mSnappingTask = null;
  public Rect mOtherSnappingBounds = null;
  public final SparseArray mMetaKeyBoundsProviderMap = new SparseArray();

  public DexMetaKeyPolicy(
      ActivityTaskManagerService activityTaskManagerService, DexController dexController) {
    this.mAtm = activityTaskManagerService;
    this.mDexController = dexController;
    registerMetaKeyBoundsProviders();
  }

  public final void registerMetaKeyBoundsProviders() {
    this.mMetaKeyBoundsProviderMap.put(21, new LeftMetaKeyBoundsProvider());
    this.mMetaKeyBoundsProviderMap.put(22, new RightMetaKeyBoundsProvider());
    this.mMetaKeyBoundsProviderMap.put(19, new UpMetaKeyBoundsProvider());
    this.mMetaKeyBoundsProviderMap.put(20, new DownMetaKeyBoundsProvider());
  }

  public void handleMetaKeyEvent(IBinder iBinder, KeyEvent keyEvent) {
    MetaKeyBoundsProvider metaKeyBoundsProvider = getMetaKeyBoundsProvider(keyEvent.getKeyCode());
    if (metaKeyBoundsProvider == null) {
      if (DEBUG) {
        Slog.d("DexMetaKeyPolicy", "handleMetaKeyEvent: cannot find provider, " + keyEvent);
        return;
      }
      return;
    }
    Task taskForMetaKey = getTaskForMetaKey(iBinder);
    if (taskForMetaKey == null) {
      if (DEBUG) {
        Slog.d("DexMetaKeyPolicy", "handleMetaKeyEvent: cannot find task, " + iBinder);
        return;
      }
      return;
    }
    updateBoundsInfo(taskForMetaKey);
    Rect launchBounds = metaKeyBoundsProvider.getLaunchBounds(taskForMetaKey, keyEvent);
    if (MultiWindowUtils.MetaKeyBoundsChecker.isInvalidBounds(launchBounds)) {
      return;
    }
    metaKeyBoundsProvider.updateTaskBoundsInfoIfNeeded(taskForMetaKey, launchBounds);
    metaKeyBoundsProvider.applyBounds(taskForMetaKey, launchBounds);
    updateSnappingTaskIfNeeded(taskForMetaKey, launchBounds);
  }

  public void updateSnappingTaskIfNeeded(Task task, Rect rect) {
    Rect rect2;
    boolean isLeftHalfDisplayBounds = isLeftHalfDisplayBounds(rect);
    boolean isRightHalfDisplayBounds = isRightHalfDisplayBounds(rect);
    if (!isLeftHalfDisplayBounds && !isRightHalfDisplayBounds) {
      this.mSnappingTask = null;
      this.mOtherSnappingBounds = null;
      return;
    }
    this.mSnappingTask = task;
    if (isLeftHalfDisplayBounds) {
      rect2 = new Rect(this.mRightDockingBounds);
    } else {
      rect2 = new Rect(this.mLeftDockingBounds);
    }
    this.mOtherSnappingBounds = rect2;
  }

  public void handleDexMetaKeyForSnapping() {
    if (this.mSnappingTask == null) {
      return;
    }
    this.mAtm.mDexDockingController.initDockingBounds(
        this.mLeftDockingBounds, this.mRightDockingBounds, this.mDisplayBounds.width());
    this.mAtm.mDexDockingController.setCandidateTask(this.mSnappingTask);
    this.mAtm.mDexController.scheduleNotifyDexSnappingCallback(
        this.mSnappingTask.mTaskId, this.mOtherSnappingBounds);
    this.mSnappingTask = null;
    this.mOtherSnappingBounds = null;
  }

  public final Task getTaskForMetaKey(IBinder iBinder) {
    WindowState windowState = (WindowState) this.mAtm.mWindowManager.mInputToWindowMap.get(iBinder);
    Task task = windowState != null ? windowState.getTask() : null;
    if (task == null || task.getDisplayContent() == null || !task.isDexMode()) {
      return null;
    }
    if (task.inFreeformWindowingMode()
        || (task.inFullscreenWindowingMode() && task.isActivityTypeStandardOrUndefined())) {
      return task;
    }
    return null;
  }

  public void updateBoundsInfo(Task task) {
    int i;
    int i2;
    task.getBounds(this.mTaskBounds);
    DisplayContent displayContent = task.getDisplayContent();
    displayContent.getStableRect(this.mDisplayBounds);
    if (CoreRune.MW_CAPTION_SHELL) {
      int captionHeight = task.getCaptionHeight();
      i = task.getFreeformThickness();
      this.mDisplayBounds.top = captionHeight;
      i2 = (captionHeight + i) / 2;
    } else {
      i = 0;
      i2 = 0;
    }
    Rect rect = this.mDisplayBounds;
    int width = rect.left + (rect.width() / 2);
    Rect rect2 = this.mDisplayBounds;
    int height = rect2.top + (rect2.height() / 2);
    this.mLeftHalfDisplayBounds.set(this.mDisplayBounds);
    Rect rect3 = this.mLeftHalfDisplayBounds;
    rect3.right = width;
    this.mLeftDockingBounds.set(rect3);
    this.mLeftDockingBounds.right -= i;
    this.mRightHalfDisplayBounds.set(this.mDisplayBounds);
    Rect rect4 = this.mRightHalfDisplayBounds;
    rect4.left = width;
    this.mRightDockingBounds.set(rect4);
    this.mRightDockingBounds.left += i;
    this.mLeftTopQuarterDisplayBounds.set(this.mLeftHalfDisplayBounds);
    int i3 = height - i2;
    this.mLeftTopQuarterDisplayBounds.bottom = i3;
    this.mRightTopQuarterDisplayBounds.set(this.mRightHalfDisplayBounds);
    this.mRightTopQuarterDisplayBounds.bottom = i3;
    this.mLeftBottomQuarterDisplayBounds.set(this.mLeftHalfDisplayBounds);
    int i4 = height + i2;
    this.mLeftBottomQuarterDisplayBounds.top = i4;
    this.mRightBottomQuarterDisplayBounds.set(this.mRightHalfDisplayBounds);
    this.mRightBottomQuarterDisplayBounds.top = i4;
    Resources currentUserResources = displayContent.getDisplayPolicy().getCurrentUserResources();
    int i5 = task.mMinHeight;
    if (i5 == -1) {
      i5 = currentUserResources.getDimensionPixelSize(R.dimen.dialog_corner_radius);
    }
    this.mMaxHeightBounds.set(this.mTaskBounds);
    Rect rect5 = this.mMaxHeightBounds;
    Rect rect6 = this.mDisplayBounds;
    rect5.top = rect6.top;
    rect5.bottom = rect6.bottom;
    this.mMinHeightBounds.set(this.mTaskBounds);
    this.mMinHeightBounds.bottom = this.mTaskBounds.top + i5;
  }

  public MetaKeyBoundsProvider getMetaKeyBoundsProvider(int i) {
    return (MetaKeyBoundsProvider) this.mMetaKeyBoundsProviderMap.get(i);
  }

  public boolean isDexDualMode() {
    return this.mDexController.getDexModeLocked() == 2;
  }

  public boolean isLeftHalfDisplayBounds(Rect rect) {
    return rect != null && rect.equals(this.mLeftHalfDisplayBounds);
  }

  public boolean isRightHalfDisplayBounds(Rect rect) {
    return rect != null && rect.equals(this.mRightHalfDisplayBounds);
  }

  public boolean isMinHeightBounds(Rect rect) {
    return rect != null && rect.equals(this.mMinHeightBounds);
  }

  public boolean isMaxHeightBounds(Rect rect) {
    return rect != null && rect.equals(this.mMaxHeightBounds);
  }

  public boolean isQuarterDisplayBounds(Rect rect) {
    if (rect == null) {
      return false;
    }
    return this.mLeftTopQuarterDisplayBounds.equals(rect)
        || this.mLeftBottomQuarterDisplayBounds.equals(rect)
        || this.mRightTopQuarterDisplayBounds.equals(rect)
        || this.mRightBottomQuarterDisplayBounds.equals(rect);
  }

  public boolean isTopQuarterDisplayBounds(Rect rect) {
    if (rect == null) {
      return false;
    }
    return this.mLeftTopQuarterDisplayBounds.equals(rect)
        || this.mRightTopQuarterDisplayBounds.equals(rect);
  }

  public boolean isBottomQuarterDisplayBounds(Rect rect) {
    if (rect == null) {
      return false;
    }
    return this.mLeftBottomQuarterDisplayBounds.equals(rect)
        || this.mRightBottomQuarterDisplayBounds.equals(rect);
  }

  public abstract class MetaKeyBoundsProvider {
    public abstract Rect getLaunchBounds(Task task, KeyEvent keyEvent);

    public abstract void updateTaskBoundsInfoIfNeeded(Task task, Rect rect);

    public MetaKeyBoundsProvider() {}

    public Rect getDefaultBounds() {
      int i;
      float f;
      float f2;
      int width = DexMetaKeyPolicy.this.mDisplayBounds.width();
      int height = DexMetaKeyPolicy.this.mDisplayBounds.height();
      if (DexMetaKeyPolicy.this.isDexDualMode()) {
        PointF pointF = MultiWindowUtils.DEX_DEFAULT_SIZE_RATIO;
        i = (int) (width * pointF.x);
        f = height;
        f2 = pointF.y;
      } else {
        PointF pointF2 = MultiWindowUtils.DEX_DEFAULT_SIZE_RATIO_FOR_STANDALONE;
        i = (int) (width * pointF2.x);
        f = height;
        f2 = pointF2.y;
      }
      int i2 = (int) (f * f2);
      int i3 = (width - i) / 2;
      int i4 = (height - i2) / 2;
      return new Rect(i3, i4, i + i3, i2 + i4);
    }

    public void applyBounds(Task task, Rect rect) {
      IBinder topAppToken;
      if (MultiWindowUtils.MetaKeyBoundsChecker.isMoveToDefaultDisplayBounds(rect)) {
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchDisplayId(0);
        DexMetaKeyPolicy.this.mAtm.startActivityFromRecents(task.mTaskId, makeBasic.toBundle());
      } else if (task.inFreeformWindowingMode()) {
        DexMetaKeyPolicy.this.mAtm.resizeTask(task.mTaskId, rect, 0);
      } else {
        if (!task.inFullscreenWindowingMode() || (topAppToken = task.getTopAppToken()) == null) {
          return;
        }
        DexMetaKeyPolicy.this.mAtm.mActivityClientController.toggleFreeformWindowingMode(
            topAppToken);
      }
    }
  }

  public class LeftMetaKeyBoundsProvider extends MetaKeyBoundsProvider {
    public LeftMetaKeyBoundsProvider() {
      super();
    }

    @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
    public Rect getLaunchBounds(Task task, KeyEvent keyEvent) {
      if (keyEvent.isShiftPressed()) {
        return DexMetaKeyPolicy.this.isDexDualMode()
            ? MultiWindowUtils.MetaKeyBoundsChecker.sMoveToDefaultDisplayBounds
            : MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
      }
      if (task.isDexCompatEnabled()) {
        return MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
      }
      DexMetaKeyPolicy dexMetaKeyPolicy = DexMetaKeyPolicy.this;
      if (dexMetaKeyPolicy.isLeftHalfDisplayBounds(dexMetaKeyPolicy.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mRightHalfDisplayBounds;
      }
      DexMetaKeyPolicy dexMetaKeyPolicy2 = DexMetaKeyPolicy.this;
      if (dexMetaKeyPolicy2.isRightHalfDisplayBounds(dexMetaKeyPolicy2.mTaskBounds)) {
        return task.hasMetaKeyBounds() ? task.takeMetaKeyBounds() : getDefaultBounds();
      }
      DexMetaKeyPolicy dexMetaKeyPolicy3 = DexMetaKeyPolicy.this;
      if (dexMetaKeyPolicy3.isTopQuarterDisplayBounds(dexMetaKeyPolicy3.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mLeftTopQuarterDisplayBounds.equals(
                DexMetaKeyPolicy.this.mTaskBounds)
            ? DexMetaKeyPolicy.this.mRightTopQuarterDisplayBounds
            : DexMetaKeyPolicy.this.mLeftTopQuarterDisplayBounds;
      }
      DexMetaKeyPolicy dexMetaKeyPolicy4 = DexMetaKeyPolicy.this;
      if (dexMetaKeyPolicy4.isBottomQuarterDisplayBounds(dexMetaKeyPolicy4.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mLeftBottomQuarterDisplayBounds.equals(
                DexMetaKeyPolicy.this.mTaskBounds)
            ? DexMetaKeyPolicy.this.mRightBottomQuarterDisplayBounds
            : DexMetaKeyPolicy.this.mLeftBottomQuarterDisplayBounds;
      }
      return DexMetaKeyPolicy.this.mLeftHalfDisplayBounds;
    }

    @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
    public void updateTaskBoundsInfoIfNeeded(Task task, Rect rect) {
      if (DexMetaKeyPolicy.this.isLeftHalfDisplayBounds(rect)) {
        DexMetaKeyPolicy dexMetaKeyPolicy = DexMetaKeyPolicy.this;
        if (dexMetaKeyPolicy.isQuarterDisplayBounds(dexMetaKeyPolicy.mTaskBounds)) {
          return;
        }
        if (task.inFullscreenWindowingMode()) {
          task.setMetaKeyBounds(null);
          task.setLastNonFullscreenBounds(rect);
        } else {
          task.setMetaKeyBounds(new Rect(DexMetaKeyPolicy.this.mTaskBounds));
        }
      }
    }
  }

  public class RightMetaKeyBoundsProvider extends MetaKeyBoundsProvider {
    public RightMetaKeyBoundsProvider() {
      super();
    }

    @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
    public Rect getLaunchBounds(Task task, KeyEvent keyEvent) {
      if (keyEvent.isShiftPressed()) {
        return DexMetaKeyPolicy.this.isDexDualMode()
            ? MultiWindowUtils.MetaKeyBoundsChecker.sMoveToDefaultDisplayBounds
            : MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
      }
      if (task.isDexCompatEnabled()) {
        return MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
      }
      DexMetaKeyPolicy dexMetaKeyPolicy = DexMetaKeyPolicy.this;
      if (dexMetaKeyPolicy.isLeftHalfDisplayBounds(dexMetaKeyPolicy.mTaskBounds)) {
        return task.hasMetaKeyBounds() ? task.takeMetaKeyBounds() : getDefaultBounds();
      }
      DexMetaKeyPolicy dexMetaKeyPolicy2 = DexMetaKeyPolicy.this;
      if (dexMetaKeyPolicy2.isRightHalfDisplayBounds(dexMetaKeyPolicy2.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mLeftHalfDisplayBounds;
      }
      DexMetaKeyPolicy dexMetaKeyPolicy3 = DexMetaKeyPolicy.this;
      if (dexMetaKeyPolicy3.isTopQuarterDisplayBounds(dexMetaKeyPolicy3.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mLeftTopQuarterDisplayBounds.equals(
                DexMetaKeyPolicy.this.mTaskBounds)
            ? DexMetaKeyPolicy.this.mRightTopQuarterDisplayBounds
            : DexMetaKeyPolicy.this.mLeftTopQuarterDisplayBounds;
      }
      DexMetaKeyPolicy dexMetaKeyPolicy4 = DexMetaKeyPolicy.this;
      if (dexMetaKeyPolicy4.isBottomQuarterDisplayBounds(dexMetaKeyPolicy4.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mLeftBottomQuarterDisplayBounds.equals(
                DexMetaKeyPolicy.this.mTaskBounds)
            ? DexMetaKeyPolicy.this.mRightBottomQuarterDisplayBounds
            : DexMetaKeyPolicy.this.mLeftBottomQuarterDisplayBounds;
      }
      return DexMetaKeyPolicy.this.mRightHalfDisplayBounds;
    }

    @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
    public void updateTaskBoundsInfoIfNeeded(Task task, Rect rect) {
      if (DexMetaKeyPolicy.this.isRightHalfDisplayBounds(rect)) {
        DexMetaKeyPolicy dexMetaKeyPolicy = DexMetaKeyPolicy.this;
        if (dexMetaKeyPolicy.isQuarterDisplayBounds(dexMetaKeyPolicy.mTaskBounds)) {
          return;
        }
        if (task.inFullscreenWindowingMode()) {
          task.setMetaKeyBounds(null);
          task.setLastNonFullscreenBounds(rect);
        } else {
          task.setMetaKeyBounds(new Rect(DexMetaKeyPolicy.this.mTaskBounds));
        }
      }
    }
  }

  public class UpMetaKeyBoundsProvider extends MetaKeyBoundsProvider {
    public UpMetaKeyBoundsProvider() {
      super();
    }

    @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
    public Rect getLaunchBounds(Task task, KeyEvent keyEvent) {
      if (!task.inFreeformWindowingMode()) {
        return MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
      }
      if (DexMetaKeyPolicy.this.mLeftHalfDisplayBounds.equals(DexMetaKeyPolicy.this.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mLeftTopQuarterDisplayBounds;
      }
      if (DexMetaKeyPolicy.this.mRightHalfDisplayBounds.equals(DexMetaKeyPolicy.this.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mRightTopQuarterDisplayBounds;
      }
      if (DexMetaKeyPolicy.this.mLeftBottomQuarterDisplayBounds.equals(
          DexMetaKeyPolicy.this.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mLeftHalfDisplayBounds;
      }
      if (DexMetaKeyPolicy.this.mRightBottomQuarterDisplayBounds.equals(
          DexMetaKeyPolicy.this.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mRightHalfDisplayBounds;
      }
      if (keyEvent.isShiftPressed()) {
        return canUseShiftKey(task)
            ? DexMetaKeyPolicy.this.mMaxHeightBounds
            : MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
      }
      return null;
    }

    public boolean canUseShiftKey(Task task) {
      if (task.isDexCompatEnabled() || !task.inFreeformWindowingMode()) {
        return false;
      }
      DexMetaKeyPolicy dexMetaKeyPolicy = DexMetaKeyPolicy.this;
      return !dexMetaKeyPolicy.isMaxHeightBounds(dexMetaKeyPolicy.mTaskBounds);
    }

    @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
    public void updateTaskBoundsInfoIfNeeded(Task task, Rect rect) {
      boolean z;
      DexMetaKeyPolicy dexMetaKeyPolicy = DexMetaKeyPolicy.this;
      if (dexMetaKeyPolicy.isQuarterDisplayBounds(dexMetaKeyPolicy.mTaskBounds)) {
        return;
      }
      if (rect == null) {
        DexMetaKeyPolicy dexMetaKeyPolicy2 = DexMetaKeyPolicy.this;
        if (!dexMetaKeyPolicy2.isLeftHalfDisplayBounds(dexMetaKeyPolicy2.mTaskBounds)) {
          DexMetaKeyPolicy dexMetaKeyPolicy3 = DexMetaKeyPolicy.this;
          if (!dexMetaKeyPolicy3.isRightHalfDisplayBounds(dexMetaKeyPolicy3.mTaskBounds)) {
            z = true;
            if (!z || DexMetaKeyPolicy.this.isMaxHeightBounds(rect)) {
              task.setMetaKeyBounds(null);
            }
            return;
          }
        }
      }
      z = false;
      if (z) {}
      task.setMetaKeyBounds(null);
    }

    @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
    public void applyBounds(Task task, Rect rect) {
      if (rect != null) {
        DexMetaKeyPolicy.this.mAtm.resizeTask(task.mTaskId, rect, 0);
        return;
      }
      IBinder topAppToken = task.getTopAppToken();
      if (topAppToken == null) {
        return;
      }
      DexMetaKeyPolicy.this.mAtm.mActivityClientController.toggleFreeformWindowingMode(topAppToken);
    }
  }

  public class DownMetaKeyBoundsProvider extends MetaKeyBoundsProvider {
    public DownMetaKeyBoundsProvider() {
      super();
    }

    @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
    public Rect getLaunchBounds(Task task, KeyEvent keyEvent) {
      if (DexMetaKeyPolicy.this.mLeftHalfDisplayBounds.equals(DexMetaKeyPolicy.this.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mLeftBottomQuarterDisplayBounds;
      }
      if (DexMetaKeyPolicy.this.mRightHalfDisplayBounds.equals(DexMetaKeyPolicy.this.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mRightBottomQuarterDisplayBounds;
      }
      if (DexMetaKeyPolicy.this.mLeftTopQuarterDisplayBounds.equals(
          DexMetaKeyPolicy.this.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mLeftHalfDisplayBounds;
      }
      if (DexMetaKeyPolicy.this.mRightTopQuarterDisplayBounds.equals(
          DexMetaKeyPolicy.this.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mRightHalfDisplayBounds;
      }
      if (keyEvent.isShiftPressed()) {
        return getLaunchBoundsWhenShiftPressed(task);
      }
      if (task.inFullscreenWindowingMode()) {
        return task.hasMetaKeyBounds() ? task.takeMetaKeyBounds() : getDefaultBounds();
      }
      return MultiWindowUtils.MetaKeyBoundsChecker.sMinimizeBounds;
    }

    public final Rect getLaunchBoundsWhenShiftPressed(Task task) {
      if (!canUseShiftKey(task)) {
        return MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
      }
      DexMetaKeyPolicy dexMetaKeyPolicy = DexMetaKeyPolicy.this;
      if (!dexMetaKeyPolicy.isMaxHeightBounds(dexMetaKeyPolicy.mTaskBounds)) {
        return DexMetaKeyPolicy.this.mMinHeightBounds;
      }
      if (task.hasMetaKeyBounds()) {
        return task.takeMetaKeyBounds();
      }
      Rect defaultBounds = getDefaultBounds();
      defaultBounds.left = DexMetaKeyPolicy.this.mTaskBounds.left;
      defaultBounds.right = DexMetaKeyPolicy.this.mTaskBounds.right;
      return defaultBounds;
    }

    public boolean canUseShiftKey(Task task) {
      if (task.isDexCompatEnabled() || !task.inFreeformWindowingMode()) {
        return false;
      }
      DexMetaKeyPolicy dexMetaKeyPolicy = DexMetaKeyPolicy.this;
      return !dexMetaKeyPolicy.isMinHeightBounds(dexMetaKeyPolicy.mTaskBounds);
    }

    @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
    public void updateTaskBoundsInfoIfNeeded(Task task, Rect rect) {
      if (DexMetaKeyPolicy.this.isMinHeightBounds(rect)) {
        task.setMetaKeyBounds(new Rect(DexMetaKeyPolicy.this.mTaskBounds));
      }
    }

    @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
    public void applyBounds(Task task, Rect rect) {
      if (MultiWindowUtils.MetaKeyBoundsChecker.isMinimizeBounds(rect)) {
        DexMetaKeyPolicy.this.mAtm.mMultiTaskingController.minimizeTaskLocked(task, true);
        return;
      }
      if (task.inFreeformWindowingMode()) {
        DexMetaKeyPolicy.this.mAtm.resizeTask(task.mTaskId, rect, 0);
        return;
      }
      IBinder topAppToken = task.getTopAppToken();
      if (topAppToken == null || !task.inFullscreenWindowingMode()) {
        return;
      }
      DexMetaKeyPolicy.this.mAtm.mActivityClientController.toggleFreeformWindowingMode(topAppToken);
    }
  }
}
