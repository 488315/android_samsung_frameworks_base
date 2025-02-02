package com.android.internal.policy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.view.DisplayInfo;
import com.android.internal.C4337R;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class DividerSnapAlgorithm {
  public static final int MINIMAL_SIZE_THRESHOLD_DPI_FOR_FOLDABLE = 460;
  public static final int MINIMAL_SIZE_THRESHOLD_DPI_FOR_TABLET = 420;
  private static final int MIN_DISMISS_VELOCITY_DP_PER_SECOND = 600;
  private static final int MIN_FLING_VELOCITY_DP_PER_SECOND = 400;
  private static final int SNAP_FIXED_RATIO = 1;
  private static final int SNAP_MODE_16_9 = 0;
  private static final int SNAP_MODE_MINIMIZED = 3;
  private static final int SNAP_MW_MULTI_SPLIT_MODE = 20;
  private static final int SNAP_MW_SPLIT_FLEXIBLE_MODE = 10;
  private static final int SNAP_ONLY_1_1 = 2;
  private final int mDensityDpi;
  private final SnapTarget mDismissEndTarget;
  private final SnapTarget mDismissStartTarget;
  private final int mDisplayDeviceType;
  private final int mDisplayHeight;
  private final int mDisplayWidth;
  private final int mDividerSize;
  private final SnapTarget mFirstSplitTarget;
  private final float mFixedRatio;
  private final boolean mFreeSnapMode;
  private final Rect mInsets;
  private boolean mIsHorizontalDivision;
  private final SnapTarget mLastSplitTarget;
  private final SnapTarget mMiddleTarget;
  private final float mMinDismissVelocityPxPerSecond;
  private final float mMinFlingVelocityPxPerSecond;
  private final int mMinimalSizeResizableTask;
  private final int mMinimumSplitTargetGap;
  private final int mMultiSplitMinimalSize;
  private final int mSnapMode;
  private final ArrayList<SnapTarget> mTargets;
  private final int mTaskHeightInMinimizedMode;

  public static DividerSnapAlgorithm create(Context ctx, Rect insets) {
    DisplayInfo displayInfo = new DisplayInfo();
    ((DisplayManager) ctx.getSystemService(DisplayManager.class))
        .getDisplay(0)
        .getDisplayInfo(displayInfo);
    int dividerWindowWidth =
        ctx.getResources().getDimensionPixelSize(C4337R.dimen.docked_stack_divider_thickness);
    int dividerInsets =
        ctx.getResources().getDimensionPixelSize(C4337R.dimen.docked_stack_divider_insets);
    if (CoreRune.MW_MULTI_SPLIT_DIVIDER) {
      dividerWindowWidth =
          ctx.getResources()
              .getDimensionPixelSize(C4337R.dimen.multi_split_screen_divider_thickness);
      if (CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD) {
        dividerInsets =
            ctx.getResources()
                .getDimensionPixelSize(C4337R.dimen.multi_split_screen_divider_insets_fold);
      } else {
        dividerInsets =
            ctx.getResources()
                .getDimensionPixelSize(C4337R.dimen.multi_split_screen_divider_insets);
      }
    }
    return new DividerSnapAlgorithm(
        ctx.getResources(),
        displayInfo.logicalWidth,
        displayInfo.logicalHeight,
        dividerWindowWidth - (dividerInsets * 2),
        ctx.getApplicationContext().getResources().getConfiguration().orientation == 1,
        insets);
  }

  public DividerSnapAlgorithm(
      Resources res,
      int displayWidth,
      int displayHeight,
      int dividerSize,
      boolean isHorizontalDivision,
      Rect insets) {
    this(
        res,
        displayWidth,
        displayHeight,
        dividerSize,
        isHorizontalDivision,
        insets,
        -1,
        false,
        true);
  }

  public DividerSnapAlgorithm(
      Resources res,
      int displayWidth,
      int displayHeight,
      int dividerSize,
      boolean isHorizontalDivision,
      Rect insets,
      int dockSide) {
    this(
        res,
        displayWidth,
        displayHeight,
        dividerSize,
        isHorizontalDivision,
        insets,
        dockSide,
        false,
        true);
  }

  public DividerSnapAlgorithm(
      Resources res,
      int displayWidth,
      int displayHeight,
      int dividerSize,
      boolean isHorizontalDivision,
      Rect insets,
      int dockSide,
      boolean isMinimizedMode,
      boolean isHomeResizable) {
    ArrayList<SnapTarget> arrayList = new ArrayList<>();
    this.mTargets = arrayList;
    Rect rect = new Rect();
    this.mInsets = rect;
    this.mMinFlingVelocityPxPerSecond = res.getDisplayMetrics().density * 400.0f;
    this.mMinDismissVelocityPxPerSecond = res.getDisplayMetrics().density * 600.0f;
    this.mDividerSize = dividerSize;
    this.mDisplayWidth = displayWidth;
    this.mDisplayHeight = displayHeight;
    this.mIsHorizontalDivision = isHorizontalDivision;
    rect.set(insets);
    if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
      this.mSnapMode = isMinimizedMode ? 3 : 20;
    } else {
      this.mSnapMode = isMinimizedMode ? 3 : 10;
    }
    this.mFreeSnapMode = res.getBoolean(C4337R.bool.config_dockedStackDividerFreeSnapMode);
    this.mFixedRatio = res.getFraction(C4337R.fraction.docked_stack_divider_fixed_ratio, 1, 1);
    this.mMinimalSizeResizableTask =
        res.getDimensionPixelSize(C4337R.dimen.default_minimal_size_resizable_task);
    this.mTaskHeightInMinimizedMode =
        isHomeResizable ? res.getDimensionPixelSize(C4337R.dimen.task_height_of_minimized_mode) : 0;
    this.mDensityDpi = res.getConfiguration().densityDpi;
    this.mMultiSplitMinimalSize =
        res.getDimensionPixelSize(C4337R.dimen.multi_split_minimal_size_resizable_task);
    this.mMinimumSplitTargetGap =
        res.getDimensionPixelSize(C4337R.dimen.multi_split_minimum_target_gap);
    this.mDisplayDeviceType = res.getConfiguration().semDisplayDeviceType;
    calculateTargets(isHorizontalDivision, dockSide);
    this.mFirstSplitTarget = arrayList.get(1);
    this.mLastSplitTarget = arrayList.get(arrayList.size() - 2);
    this.mDismissStartTarget = arrayList.get(0);
    this.mDismissEndTarget = arrayList.get(arrayList.size() - 1);
    SnapTarget snapTarget = arrayList.get(arrayList.size() / 2);
    this.mMiddleTarget = snapTarget;
    snapTarget.isMiddleTarget = true;
  }

  public boolean isSplitScreenFeasible() {
    int size;
    int statusBarSize = this.mInsets.top;
    int navBarSize = this.mIsHorizontalDivision ? this.mInsets.bottom : this.mInsets.right;
    if (this.mIsHorizontalDivision) {
      size = this.mDisplayHeight;
    } else {
      size = this.mDisplayWidth;
    }
    if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM && !this.mIsHorizontalDivision) {
      navBarSize = 0;
    }
    int availableSpace = ((size - navBarSize) - statusBarSize) - this.mDividerSize;
    return availableSpace / 2
        >= (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM
            ? getMinimalSize()
            : this.mMinimalSizeResizableTask);
  }

  public SnapTarget calculateSnapTarget(int position, float velocity) {
    return calculateSnapTarget(position, velocity, true);
  }

  public SnapTarget calculateSnapTarget(int position, float velocity, boolean hardDismiss) {
    if (position < this.mFirstSplitTarget.position
        && velocity < (-this.mMinDismissVelocityPxPerSecond)) {
      return this.mDismissStartTarget;
    }
    if (position > this.mLastSplitTarget.position
        && velocity > this.mMinDismissVelocityPxPerSecond) {
      return this.mDismissEndTarget;
    }
    if (Math.abs(velocity) < this.mMinFlingVelocityPxPerSecond) {
      return snap(position, hardDismiss);
    }
    if (velocity < 0.0f) {
      return this.mFirstSplitTarget;
    }
    return this.mLastSplitTarget;
  }

  public SnapTarget calculateNonDismissingSnapTarget(int position) {
    SnapTarget target = snap(position, false);
    if (target == this.mDismissStartTarget) {
      return this.mFirstSplitTarget;
    }
    if (target == this.mDismissEndTarget) {
      return this.mLastSplitTarget;
    }
    return target;
  }

  public float calculateDismissingFraction(int position) {
    if (position < this.mFirstSplitTarget.position) {
      return 1.0f
          - ((position - getStartInset()) / (this.mFirstSplitTarget.position - getStartInset()));
    }
    if (position > this.mLastSplitTarget.position) {
      return (position - this.mLastSplitTarget.position)
          / ((this.mDismissEndTarget.position - this.mLastSplitTarget.position)
              - this.mDividerSize);
    }
    return 0.0f;
  }

  public SnapTarget getClosestDismissTarget(int position) {
    if (position < this.mFirstSplitTarget.position) {
      return this.mDismissStartTarget;
    }
    if (position > this.mLastSplitTarget.position) {
      return this.mDismissEndTarget;
    }
    if (position - this.mDismissStartTarget.position < this.mDismissEndTarget.position - position) {
      return this.mDismissStartTarget;
    }
    return this.mDismissEndTarget;
  }

  public SnapTarget getFirstSplitTarget() {
    return this.mFirstSplitTarget;
  }

  public SnapTarget getLastSplitTarget() {
    return this.mLastSplitTarget;
  }

  public SnapTarget getDismissStartTarget() {
    return this.mDismissStartTarget;
  }

  public SnapTarget getDismissEndTarget() {
    return this.mDismissEndTarget;
  }

  private int getStartInset() {
    if (this.mIsHorizontalDivision) {
      return this.mInsets.top;
    }
    return this.mInsets.left;
  }

  private int getEndInset() {
    if (this.mIsHorizontalDivision) {
      return this.mInsets.bottom;
    }
    return this.mInsets.right;
  }

  private boolean shouldApplyFreeSnapMode(int position) {
    return this.mFreeSnapMode
        && isFirstSplitTargetAvailable()
        && isLastSplitTargetAvailable()
        && this.mFirstSplitTarget.position < position
        && position < this.mLastSplitTarget.position;
  }

  private SnapTarget snap(int position, boolean hardDismiss) {
    if (shouldApplyFreeSnapMode(position)) {
      return new SnapTarget(position, position, 0);
    }
    int minIndex = -1;
    float minDistance = Float.MAX_VALUE;
    int size = this.mTargets.size();
    for (int i = 0; i < size; i++) {
      SnapTarget target = this.mTargets.get(i);
      float distance = Math.abs(position - target.position);
      if (hardDismiss) {
        distance /= target.distanceMultiplier;
      }
      if (distance < minDistance) {
        minIndex = i;
        minDistance = distance;
      }
    }
    return this.mTargets.get(minIndex);
  }

  private void calculateTargets(boolean isHorizontalDivision, int dockedSide) {
    int dividerMax;
    this.mTargets.clear();
    if (isHorizontalDivision) {
      dividerMax = this.mDisplayHeight;
    } else {
      dividerMax = this.mDisplayWidth;
    }
    int startPos = -this.mDividerSize;
    if (dockedSide == 3) {
      startPos += this.mInsets.left;
    }
    this.mTargets.add(new SnapTarget(startPos, startPos, 1, 0.35f));
    switch (this.mSnapMode) {
      case 0:
        addRatio16_9Targets(isHorizontalDivision, dividerMax);
        break;
      case 1:
        addFixedDivisionTargets(isHorizontalDivision, dividerMax);
        break;
      case 2:
        addMiddleTarget(isHorizontalDivision);
        break;
      case 3:
        addMinimizedTarget(isHorizontalDivision, dockedSide);
        break;
      case 10:
        addFlexibleSnapTargets(isHorizontalDivision, dividerMax);
        break;
      case 20:
        addMultiSplitSnapTargets(isHorizontalDivision, dividerMax);
        break;
    }
    this.mTargets.add(new SnapTarget(dividerMax, dividerMax, 2, 0.35f));
  }

  private void addNonDismissingTargets(
      boolean isHorizontalDivision, int topPosition, int bottomPosition, int dividerMax) {
    maybeAddTarget(topPosition, topPosition - getStartInset());
    addMiddleTarget(isHorizontalDivision);
    maybeAddTarget(
        bottomPosition, (dividerMax - getEndInset()) - (this.mDividerSize + bottomPosition));
  }

  private void addFixedDivisionTargets(boolean isHorizontalDivision, int dividerMax) {
    int end;
    Rect rect = this.mInsets;
    int start = isHorizontalDivision ? rect.top : rect.left;
    if (isHorizontalDivision) {
      end = this.mDisplayHeight - this.mInsets.bottom;
    } else {
      end = this.mDisplayWidth - this.mInsets.right;
    }
    int i = (int) (this.mFixedRatio * (end - start));
    int i2 = this.mDividerSize;
    int size = i - (i2 / 2);
    int topPosition = start + size;
    int bottomPosition = (end - size) - i2;
    addNonDismissingTargets(isHorizontalDivision, topPosition, bottomPosition, dividerMax);
  }

  private void addRatio16_9Targets(boolean isHorizontalDivision, int dividerMax) {
    int end;
    int endOther;
    Rect rect = this.mInsets;
    int start = isHorizontalDivision ? rect.top : rect.left;
    if (isHorizontalDivision) {
      end = this.mDisplayHeight - this.mInsets.bottom;
    } else {
      end = this.mDisplayWidth - this.mInsets.right;
    }
    Rect rect2 = this.mInsets;
    int startOther = isHorizontalDivision ? rect2.left : rect2.top;
    if (isHorizontalDivision) {
      endOther = this.mDisplayWidth - this.mInsets.right;
    } else {
      endOther = this.mDisplayHeight - this.mInsets.bottom;
    }
    float size = (endOther - startOther) * 0.5625f;
    int sizeInt = (int) Math.floor(size);
    int topPosition = start + sizeInt;
    int bottomPosition = (end - sizeInt) - this.mDividerSize;
    addNonDismissingTargets(isHorizontalDivision, topPosition, bottomPosition, dividerMax);
  }

  private void addFlexibleSnapTargets(boolean isHorizontalDivision, int dividerMax) {
    Rect rect = this.mInsets;
    int start = isHorizontalDivision ? rect.top : rect.left;
    int end =
        isHorizontalDivision
            ? this.mDisplayHeight - this.mInsets.bottom
            : this.mDisplayWidth - this.mInsets.right;
    int shortSize = Math.min(this.mDisplayWidth, this.mDisplayHeight);
    int size = Math.max(Math.round(shortSize * 0.5625f), this.mMinimalSizeResizableTask);
    int topPosition = start + size;
    int bottomPosition = (end - size) - this.mDividerSize;
    addNonDismissingTargets(isHorizontalDivision, topPosition, bottomPosition, dividerMax);
  }

  private void maybeAddTarget(int position, int smallerSize) {
    if (smallerSize >= this.mMinimalSizeResizableTask) {
      this.mTargets.add(new SnapTarget(position, position, 0));
    }
  }

  private void addMiddleTarget(boolean isHorizontalDivision) {
    int position =
        DockedDividerUtils.calculateMiddlePosition(
            isHorizontalDivision,
            this.mInsets,
            this.mDisplayWidth,
            this.mDisplayHeight,
            this.mDividerSize);
    this.mTargets.add(new SnapTarget(position, position, 0));
  }

  private void addMinimizedTarget(boolean isHorizontalDivision, int dockedSide) {
    int position = this.mTaskHeightInMinimizedMode + this.mInsets.top;
    if (!isHorizontalDivision) {
      if (dockedSide == 1) {
        position += this.mInsets.left;
      } else if (dockedSide == 3) {
        position = ((this.mDisplayWidth - position) - this.mInsets.right) - this.mDividerSize;
      }
    }
    this.mTargets.add(new SnapTarget(position, position, 0));
  }

  public SnapTarget getMiddleTarget() {
    return this.mMiddleTarget;
  }

  public SnapTarget getNextTarget(SnapTarget snapTarget) {
    int index = this.mTargets.indexOf(snapTarget);
    if (index != -1 && index < this.mTargets.size() - 1) {
      return this.mTargets.get(index + 1);
    }
    return snapTarget;
  }

  public SnapTarget getPreviousTarget(SnapTarget snapTarget) {
    int index = this.mTargets.indexOf(snapTarget);
    if (index != -1 && index > 0) {
      return this.mTargets.get(index - 1);
    }
    return snapTarget;
  }

  public boolean showMiddleSplitTargetForAccessibility() {
    return this.mTargets.size() + (-2) > 1;
  }

  public boolean isFirstSplitTargetAvailable() {
    return this.mFirstSplitTarget != this.mMiddleTarget;
  }

  public boolean isLastSplitTargetAvailable() {
    return this.mLastSplitTarget != this.mMiddleTarget;
  }

  public SnapTarget cycleNonDismissTarget(SnapTarget snapTarget, int increment) {
    int index = this.mTargets.indexOf(snapTarget);
    if (index != -1) {
      ArrayList<SnapTarget> arrayList = this.mTargets;
      SnapTarget newTarget =
          arrayList.get(((arrayList.size() + index) + increment) % this.mTargets.size());
      if (newTarget == this.mDismissStartTarget) {
        return this.mLastSplitTarget;
      }
      if (newTarget == this.mDismissEndTarget) {
        return this.mFirstSplitTarget;
      }
      return newTarget;
    }
    return snapTarget;
  }

  private void addMultiSplitSnapTargets(boolean isHorizontalDivision, int dividerMax) {
    if (!CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
      return;
    }
    Rect rect = this.mInsets;
    int start = isHorizontalDivision ? rect.top : rect.left;
    int end =
        isHorizontalDivision
            ? this.mDisplayHeight - this.mInsets.bottom
            : this.mDisplayWidth - this.mInsets.right;
    int size = getMinimalSize();
    int topPosition = start + size;
    int bottomPosition = (end - size) - this.mDividerSize;
    if (bottomPosition - topPosition <= this.mMinimumSplitTargetGap) {
      addMiddleTarget(isHorizontalDivision);
    } else {
      addNonDismissingTargets(isHorizontalDivision, topPosition, bottomPosition, dividerMax);
    }
  }

  private int getMinimalSize() {
    int i;
    int i2 = this.mDensityDpi;
    if (MultiWindowUtils.isTablet()
        || (CoreRune.MW_MULTI_SPLIT_FOR_COVER_DISPLAY && this.mDisplayDeviceType == 5)) {
      i = 420;
    } else {
      i = 460;
    }
    if (i2 >= i) {
      return this.mMinimalSizeResizableTask;
    }
    return this.mMultiSplitMinimalSize;
  }

  public static class SnapTarget {
    public static final int FLAG_DISMISS_END = 2;
    public static final int FLAG_DISMISS_START = 1;
    public static final int FLAG_NONE = 0;
    private final float distanceMultiplier;
    public final int flag;
    public boolean isMiddleTarget;
    public final int position;
    public final int taskPosition;

    public SnapTarget(int position, int taskPosition, int flag) {
      this(position, taskPosition, flag, 1.0f);
    }

    public SnapTarget(int position, int taskPosition, int flag, float distanceMultiplier) {
      this.position = position;
      this.taskPosition = taskPosition;
      this.flag = flag;
      this.distanceMultiplier = distanceMultiplier;
    }
  }

  public boolean isMiddleTargetOnly() {
    return this.mTargets.size() == 3;
  }

  public int getTargetMinimalRatio() {
    float ratio;
    if (this.mIsHorizontalDivision) {
      ratio =
          this.mMultiSplitMinimalSize
              / ((this.mDisplayHeight - this.mInsets.top) - this.mInsets.bottom);
    } else {
      ratio =
          this.mMultiSplitMinimalSize
              / ((this.mDisplayWidth - this.mInsets.left) - this.mInsets.right);
    }
    return ((double) ratio) >= 0.35d ? 40 : 30;
  }
}
