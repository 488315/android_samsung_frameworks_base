package android.app;

import android.content.ComponentName;
import android.content.Intent;
import android.content.LocusId;
import android.content.p002pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.p009os.IBinder;
import android.p009os.Parcel;
import android.window.WindowContainerToken;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public class TaskInfo {
  public static final int CAMERA_COMPAT_CONTROL_DISMISSED = 3;
  public static final int CAMERA_COMPAT_CONTROL_HIDDEN = 0;
  public static final int CAMERA_COMPAT_CONTROL_TREATMENT_APPLIED = 2;
  public static final int CAMERA_COMPAT_CONTROL_TREATMENT_SUGGESTED = 1;
  public static final int PROPERTY_VALUE_UNSET = -1;
  private static final String TAG = "TaskInfo";
  public ComponentName baseActivity;
  public Intent baseIntent;
  public int defaultMinSize;
  public Rect displayCutoutInsets;
  public int displayId;
  public boolean hasPopOver;
  public boolean hasWallpaper;
  public boolean isAllowedSeamlessRotation;
  public boolean isCaptionHandlerHidden;
  public boolean isCoverLauncherWidgetTask;
  public boolean isCoverScreenTask;
  public boolean isFocused;
  public boolean isForceHidden;
  public boolean isFromLetterboxDoubleTap;
  public boolean isFullScreenCaptionState;
  public boolean isLetterboxDoubleTapEnabled;
  public boolean isResizeable;
  public boolean isRotationButtonVisible;
  public boolean isRunning;
  public boolean isSleeping;
  public boolean isTopTaskInStage;
  public boolean isTopTransparentActivity;
  public boolean isTranslucentTask;
  public boolean isVisible;
  public boolean isVisibleRequested;
  public long lastActiveTime;
  public long lastGainFocusTime;
  public int lastParentTaskIdBeforePip;
  public int launchIntoPipHostTaskId;
  public LocusId mTopActivityLocusId;
  public int maxHeight;
  public int maxWidth;
  public int minHeight;
  public int minWidth;
  public int numActivities;
  public ComponentName origActivity;
  public boolean originallySupportedMultiWindow;
  public int parentTaskId;
  public PictureInPictureParams pictureInPictureParams;
  public Point positionInParent;
  public ComponentName realActivity;
  public int resizeMode;
  public String rootAffinity;
  public boolean shouldDockBigOverlays;
  public boolean singleTapFromLetterbox;
  public SizeCompatInfo sizeCompatInfo;
  public Rect snappingGuideBounds;
  public boolean supportsMultiWindow;
  public boolean supportsPipOnly;
  public ActivityManager.TaskDescription taskDescription;
  public int taskId;
  public WindowContainerToken token;
  public ComponentName topActivity;
  public Rect topActivityBounds;
  public boolean topActivityEligibleForLetterboxEducation;
  public boolean topActivityInBoundsCompat;
  public boolean topActivityInDisplayCompat;
  public boolean topActivityInFixedAspectRatio;
  public boolean topActivityInSizeCompat;
  public ActivityInfo topActivityInfo;
  public int topActivityLetterboxHeight;
  public int topActivityLetterboxHorizontalPosition;
  public int topActivityLetterboxVerticalPosition;
  public int topActivityLetterboxWidth;
  public int topActivityType;
  public int topActivityUiMode;
  public int userId;
  public int displayAreaFeatureId = -1;
  public final Configuration configuration = new Configuration();
  public ArrayList<IBinder> launchCookies = new ArrayList<>();
  public int cameraCompatControlState = 0;
  public boolean isMinimized = false;

  @Retention(RetentionPolicy.SOURCE)
  public @interface CameraCompatControlState {}

  TaskInfo() {}

  private TaskInfo(Parcel source) {
    readFromParcel(source);
  }

  public boolean isVisible() {
    return this.isVisible;
  }

  public WindowContainerToken getToken() {
    return this.token;
  }

  public Configuration getConfiguration() {
    return this.configuration;
  }

  public PictureInPictureParams getPictureInPictureParams() {
    return this.pictureInPictureParams;
  }

  public boolean shouldDockBigOverlays() {
    return this.shouldDockBigOverlays;
  }

  public int getWindowingMode() {
    return this.configuration.windowConfiguration.getWindowingMode();
  }

  public int getActivityType() {
    return this.configuration.windowConfiguration.getActivityType();
  }

  public void addLaunchCookie(IBinder cookie) {
    if (cookie == null || this.launchCookies.contains(cookie)) {
      return;
    }
    this.launchCookies.add(cookie);
  }

  public boolean hasCameraCompatControl() {
    int i = this.cameraCompatControlState;
    return (i == 0 || i == 3) ? false : true;
  }

  public boolean hasCompatUI() {
    return hasCameraCompatControl()
        || this.topActivityInSizeCompat
        || this.topActivityEligibleForLetterboxEducation
        || this.isLetterboxDoubleTapEnabled;
  }

  public boolean containsLaunchCookie(IBinder cookie) {
    return this.launchCookies.contains(cookie);
  }

  public int getParentTaskId() {
    return this.parentTaskId;
  }

  public boolean hasParentTask() {
    return this.parentTaskId != -1;
  }

  public int getDisplayId() {
    return this.displayId;
  }

  public boolean isSplitScreen() {
    return this.configuration.windowConfiguration.isSplitScreen();
  }

  public boolean isFreeform() {
    return this.configuration.windowConfiguration.getWindowingMode() == 5;
  }

  public boolean equalsForTaskOrganizer(TaskInfo that) {
    if (that == null) {
      return false;
    }
    if ((CoreRune.FW_BOUNDS_COMPAT_UI && !equalsForAllBoundsCompats(that))
        || this.isForceHidden != that.isForceHidden) {
      return false;
    }
    if (CoreRune.MW_CAPTION_SHELL) {
      if (this.isCaptionHandlerHidden != that.isCaptionHandlerHidden
          || this.configuration.fontScale != that.getConfiguration().fontScale) {
        return false;
      }
      if (isFreeform()
          && this.configuration.semDisplayDeviceType
              != that.getConfiguration().semDisplayDeviceType) {
        return false;
      }
    }
    if (!CoreRune.MW_CAPTION_SHELL_FULL_SCREEN
        || this.isFullScreenCaptionState == that.isFullScreenCaptionState) {
      return (!CoreRune.MD_DEX_COMPAT_CAPTION_SHELL
              || this.isRotationButtonVisible == that.isRotationButtonVisible)
          && this.topActivityType == that.topActivityType
          && this.isResizeable == that.isResizeable
          && this.supportsMultiWindow == that.supportsMultiWindow
          && this.displayAreaFeatureId == that.displayAreaFeatureId
          && this.isFromLetterboxDoubleTap == that.isFromLetterboxDoubleTap
          && this.topActivityLetterboxVerticalPosition == that.topActivityLetterboxVerticalPosition
          && this.topActivityLetterboxWidth == that.topActivityLetterboxWidth
          && this.topActivityLetterboxHeight == that.topActivityLetterboxHeight
          && this.topActivityLetterboxHorizontalPosition
              == that.topActivityLetterboxHorizontalPosition
          && Objects.equals(this.positionInParent, that.positionInParent)
          && Objects.equals(this.pictureInPictureParams, that.pictureInPictureParams)
          && Objects.equals(
              Boolean.valueOf(this.shouldDockBigOverlays),
              Boolean.valueOf(that.shouldDockBigOverlays))
          && Objects.equals(this.displayCutoutInsets, that.displayCutoutInsets)
          && getWindowingMode() == that.getWindowingMode()
          && this.configuration.uiMode == that.configuration.uiMode
          && Objects.equals(this.taskDescription, that.taskDescription)
          && this.isFocused == that.isFocused
          && this.isVisible == that.isVisible
          && this.isVisibleRequested == that.isVisibleRequested
          && this.isSleeping == that.isSleeping
          && Objects.equals(this.mTopActivityLocusId, that.mTopActivityLocusId)
          && this.parentTaskId == that.parentTaskId
          && Objects.equals(this.topActivity, that.topActivity);
    }
    return false;
  }

  public boolean equalsForCompatUi(TaskInfo that) {
    if (that == null
        || this.displayId != that.displayId
        || this.taskId != that.taskId
        || this.topActivityInSizeCompat != that.topActivityInSizeCompat
        || this.isFromLetterboxDoubleTap != that.isFromLetterboxDoubleTap
        || this.topActivityEligibleForLetterboxEducation
            != that.topActivityEligibleForLetterboxEducation
        || this.topActivityLetterboxVerticalPosition != that.topActivityLetterboxVerticalPosition
        || this.topActivityLetterboxHorizontalPosition
            != that.topActivityLetterboxHorizontalPosition
        || this.topActivityLetterboxWidth != that.topActivityLetterboxWidth
        || this.topActivityLetterboxHeight != that.topActivityLetterboxHeight
        || this.cameraCompatControlState != that.cameraCompatControlState) {
      return false;
    }
    if (hasCompatUI()
        && !this.configuration
            .windowConfiguration
            .getBounds()
            .equals(that.configuration.windowConfiguration.getBounds())) {
      return false;
    }
    if (hasCompatUI()
        && this.configuration.getLayoutDirection() != that.configuration.getLayoutDirection()) {
      return false;
    }
    if (!hasCompatUI() || this.configuration.uiMode == that.configuration.uiMode) {
      return !hasCompatUI() || this.isVisible == that.isVisible;
    }
    return false;
  }

  public boolean equalsForAllBoundsCompats(TaskInfo that) {
    return that != null && equalsForFixedAspectRatio(that) && equalsForBoundsCompat(that);
  }

  public boolean equalsForFixedAspectRatio(TaskInfo that) {
    boolean z;
    if (that == null
        || this.displayId != that.displayId
        || this.taskId != that.taskId
        || (z = this.topActivityInFixedAspectRatio) != that.topActivityInFixedAspectRatio) {
      return false;
    }
    if (z
        && !this.configuration
            .windowConfiguration
            .getBounds()
            .equals(that.configuration.windowConfiguration.getBounds())) {
      return false;
    }
    if (!this.topActivityInFixedAspectRatio
        || this.configuration.getLayoutDirection() == that.configuration.getLayoutDirection()) {
      return !this.topActivityInFixedAspectRatio || this.isVisible == that.isVisible;
    }
    return false;
  }

  public boolean equalsForBoundsCompat(TaskInfo that) {
    boolean z;
    if (that == null
        || this.displayId != that.displayId
        || this.taskId != that.taskId
        || (z = this.topActivityInBoundsCompat) != that.topActivityInBoundsCompat) {
      return false;
    }
    if (z
        && !this.configuration
            .windowConfiguration
            .getBounds()
            .equals(that.configuration.windowConfiguration.getBounds())) {
      return false;
    }
    if (!this.topActivityInBoundsCompat
        || this.configuration.getLayoutDirection() == that.configuration.getLayoutDirection()) {
      return !this.topActivityInBoundsCompat || this.isVisible == that.isVisible;
    }
    return false;
  }

  public boolean preserveOrientationOnResize() {
    int i = this.resizeMode;
    return i == 6 || i == 5 || i == 7;
  }

  void readFromParcel(Parcel source) {
    this.userId = source.readInt();
    this.taskId = source.readInt();
    this.displayId = source.readInt();
    this.isRunning = source.readBoolean();
    this.baseIntent = (Intent) source.readTypedObject(Intent.CREATOR);
    this.baseActivity = ComponentName.readFromParcel(source);
    this.topActivity = ComponentName.readFromParcel(source);
    this.origActivity = ComponentName.readFromParcel(source);
    this.realActivity = ComponentName.readFromParcel(source);
    this.numActivities = source.readInt();
    this.lastActiveTime = source.readLong();
    this.taskDescription =
        (ActivityManager.TaskDescription)
            source.readTypedObject(ActivityManager.TaskDescription.CREATOR);
    this.supportsMultiWindow = source.readBoolean();
    this.resizeMode = source.readInt();
    this.configuration.readFromParcel(source);
    this.token = WindowContainerToken.CREATOR.createFromParcel(source);
    this.topActivityType = source.readInt();
    this.pictureInPictureParams =
        (PictureInPictureParams) source.readTypedObject(PictureInPictureParams.CREATOR);
    this.shouldDockBigOverlays = source.readBoolean();
    this.launchIntoPipHostTaskId = source.readInt();
    this.lastParentTaskIdBeforePip = source.readInt();
    this.displayCutoutInsets = (Rect) source.readTypedObject(Rect.CREATOR);
    this.topActivityInfo = (ActivityInfo) source.readTypedObject(ActivityInfo.CREATOR);
    this.isResizeable = source.readBoolean();
    this.minWidth = source.readInt();
    this.minHeight = source.readInt();
    this.defaultMinSize = source.readInt();
    source.readBinderList(this.launchCookies);
    this.positionInParent = (Point) source.readTypedObject(Point.CREATOR);
    this.parentTaskId = source.readInt();
    this.isFocused = source.readBoolean();
    this.isVisible = source.readBoolean();
    this.isVisibleRequested = source.readBoolean();
    this.isSleeping = source.readBoolean();
    this.topActivityInSizeCompat = source.readBoolean();
    this.topActivityEligibleForLetterboxEducation = source.readBoolean();
    this.mTopActivityLocusId = (LocusId) source.readTypedObject(LocusId.CREATOR);
    this.displayAreaFeatureId = source.readInt();
    this.cameraCompatControlState = source.readInt();
    this.isLetterboxDoubleTapEnabled = source.readBoolean();
    this.isFromLetterboxDoubleTap = source.readBoolean();
    this.topActivityLetterboxVerticalPosition = source.readInt();
    this.topActivityLetterboxHorizontalPosition = source.readInt();
    this.topActivityLetterboxWidth = source.readInt();
    this.topActivityLetterboxHeight = source.readInt();
    this.lastGainFocusTime = source.readLong();
    this.originallySupportedMultiWindow = source.readBoolean();
    this.supportsPipOnly = source.readBoolean();
    this.hasWallpaper = source.readBoolean();
    if (CoreRune.FW_FIXED_ASPECT_RATIO_MODE) {
      this.topActivityInFixedAspectRatio = source.readBoolean();
    }
    this.rootAffinity = source.readString();
    if (CoreRune.MW_CAPTION_SHELL) {
      this.maxWidth = source.readInt();
      this.maxHeight = source.readInt();
    }
    if (CoreRune.MT_SUPPORT_SIZE_COMPAT) {
      this.sizeCompatInfo = (SizeCompatInfo) source.readTypedObject(SizeCompatInfo.CREATOR);
    }
    if (CoreRune.FW_BOUNDS_COMPAT_UI_SUPPORT_ALIGNMENT) {
      this.topActivityInBoundsCompat = source.readBoolean();
      this.topActivityBounds = (Rect) source.readTypedObject(Rect.CREATOR);
    }
    if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_FOR_BOUNDS_COMPAT_UI_EXPERIENCE) {
      this.singleTapFromLetterbox = source.readBoolean();
    }
    this.isTopTaskInStage = source.readBoolean();
    if (CoreRune.MW_CAPTION_SHELL) {
      this.isTranslucentTask = source.readBoolean();
      this.isCaptionHandlerHidden = source.readBoolean();
      this.topActivityUiMode = source.readInt();
    }
    if (CoreRune.MW_CAPTION_SHELL_FULL_SCREEN) {
      this.isFullScreenCaptionState = source.readBoolean();
    }
    if (CoreRune.MD_DEX_COMPAT_CAPTION_SHELL) {
      this.isRotationButtonVisible = source.readBoolean();
    }
    this.snappingGuideBounds = (Rect) source.readTypedObject(Rect.CREATOR);
    this.isForceHidden = source.readBoolean();
    this.isAllowedSeamlessRotation = source.readBoolean();
    this.isTopTransparentActivity = source.readBoolean();
    this.hasPopOver = source.readBoolean();
    this.isMinimized = source.readBoolean();
  }

  /* JADX INFO: Access modifiers changed from: package-private */
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.userId);
    dest.writeInt(this.taskId);
    dest.writeInt(this.displayId);
    dest.writeBoolean(this.isRunning);
    dest.writeTypedObject(this.baseIntent, 0);
    ComponentName.writeToParcel(this.baseActivity, dest);
    ComponentName.writeToParcel(this.topActivity, dest);
    ComponentName.writeToParcel(this.origActivity, dest);
    ComponentName.writeToParcel(this.realActivity, dest);
    dest.writeInt(this.numActivities);
    dest.writeLong(this.lastActiveTime);
    dest.writeTypedObject(this.taskDescription, flags);
    dest.writeBoolean(this.supportsMultiWindow);
    dest.writeInt(this.resizeMode);
    this.configuration.writeToParcel(dest, flags);
    this.token.writeToParcel(dest, flags);
    dest.writeInt(this.topActivityType);
    dest.writeTypedObject(this.pictureInPictureParams, flags);
    dest.writeBoolean(this.shouldDockBigOverlays);
    dest.writeInt(this.launchIntoPipHostTaskId);
    dest.writeInt(this.lastParentTaskIdBeforePip);
    dest.writeTypedObject(this.displayCutoutInsets, flags);
    dest.writeTypedObject(this.topActivityInfo, flags);
    dest.writeBoolean(this.isResizeable);
    dest.writeInt(this.minWidth);
    dest.writeInt(this.minHeight);
    dest.writeInt(this.defaultMinSize);
    dest.writeBinderList(this.launchCookies);
    dest.writeTypedObject(this.positionInParent, flags);
    dest.writeInt(this.parentTaskId);
    dest.writeBoolean(this.isFocused);
    dest.writeBoolean(this.isVisible);
    dest.writeBoolean(this.isVisibleRequested);
    dest.writeBoolean(this.isSleeping);
    dest.writeBoolean(this.topActivityInSizeCompat);
    dest.writeBoolean(this.topActivityEligibleForLetterboxEducation);
    dest.writeTypedObject(this.mTopActivityLocusId, flags);
    dest.writeInt(this.displayAreaFeatureId);
    dest.writeInt(this.cameraCompatControlState);
    dest.writeBoolean(this.isLetterboxDoubleTapEnabled);
    dest.writeBoolean(this.isFromLetterboxDoubleTap);
    dest.writeInt(this.topActivityLetterboxVerticalPosition);
    dest.writeInt(this.topActivityLetterboxHorizontalPosition);
    dest.writeInt(this.topActivityLetterboxWidth);
    dest.writeInt(this.topActivityLetterboxHeight);
    dest.writeLong(this.lastGainFocusTime);
    dest.writeBoolean(this.originallySupportedMultiWindow);
    dest.writeBoolean(this.supportsPipOnly);
    dest.writeBoolean(this.hasWallpaper);
    if (CoreRune.FW_FIXED_ASPECT_RATIO_MODE) {
      dest.writeBoolean(this.topActivityInFixedAspectRatio);
    }
    dest.writeString(this.rootAffinity);
    if (CoreRune.MW_CAPTION_SHELL) {
      dest.writeInt(this.maxWidth);
      dest.writeInt(this.maxHeight);
    }
    if (CoreRune.MT_SUPPORT_SIZE_COMPAT) {
      dest.writeTypedObject(this.sizeCompatInfo, flags);
    }
    if (CoreRune.FW_BOUNDS_COMPAT_UI_SUPPORT_ALIGNMENT) {
      dest.writeBoolean(this.topActivityInBoundsCompat);
      dest.writeTypedObject(this.topActivityBounds, flags);
    }
    if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_FOR_BOUNDS_COMPAT_UI_EXPERIENCE) {
      dest.writeBoolean(this.singleTapFromLetterbox);
    }
    dest.writeBoolean(this.isTopTaskInStage);
    if (CoreRune.MW_CAPTION_SHELL) {
      dest.writeBoolean(this.isTranslucentTask);
      dest.writeBoolean(this.isCaptionHandlerHidden);
      dest.writeInt(this.topActivityUiMode);
    }
    if (CoreRune.MW_CAPTION_SHELL_FULL_SCREEN) {
      dest.writeBoolean(this.isFullScreenCaptionState);
    }
    if (CoreRune.MD_DEX_COMPAT_CAPTION_SHELL) {
      dest.writeBoolean(this.isRotationButtonVisible);
    }
    dest.writeTypedObject(this.snappingGuideBounds, flags);
    dest.writeBoolean(this.isForceHidden);
    dest.writeBoolean(this.isAllowedSeamlessRotation);
    dest.writeBoolean(this.isTopTransparentActivity);
    dest.writeBoolean(this.hasPopOver);
    dest.writeBoolean(this.isMinimized);
  }

  public String toString() {
    return "TaskInfo{userId="
        + this.userId
        + " taskId="
        + this.taskId
        + " displayId="
        + this.displayId
        + " isRunning="
        + this.isRunning
        + " baseIntent="
        + this.baseIntent
        + " baseActivity="
        + this.baseActivity
        + " topActivity="
        + this.topActivity
        + " origActivity="
        + this.origActivity
        + " realActivity="
        + this.realActivity
        + " numActivities="
        + this.numActivities
        + " lastActiveTime="
        + this.lastActiveTime
        + " supportsMultiWindow="
        + this.supportsMultiWindow
        + " resizeMode="
        + this.resizeMode
        + " isResizeable="
        + this.isResizeable
        + " minWidth="
        + this.minWidth
        + " minHeight="
        + this.minHeight
        + " maxWidth="
        + this.maxWidth
        + " maxHeight="
        + this.maxHeight
        + " defaultMinSize="
        + this.defaultMinSize
        + " token="
        + this.token
        + " topActivityType="
        + this.topActivityType
        + " pictureInPictureParams="
        + this.pictureInPictureParams
        + " shouldDockBigOverlays="
        + this.shouldDockBigOverlays
        + " launchIntoPipHostTaskId="
        + this.launchIntoPipHostTaskId
        + " lastParentTaskIdBeforePip="
        + this.lastParentTaskIdBeforePip
        + " displayCutoutSafeInsets="
        + this.displayCutoutInsets
        + " topActivityInfo="
        + this.topActivityInfo
        + " launchCookies="
        + this.launchCookies
        + " positionInParent="
        + this.positionInParent
        + " parentTaskId="
        + this.parentTaskId
        + " isFocused="
        + this.isFocused
        + " isVisible="
        + this.isVisible
        + " isVisibleRequested="
        + this.isVisibleRequested
        + " isSleeping="
        + this.isSleeping
        + " topActivityInSizeCompat="
        + this.topActivityInSizeCompat
        + " topActivityEligibleForLetterboxEducation= "
        + this.topActivityEligibleForLetterboxEducation
        + " topActivityLetterboxed= "
        + this.isLetterboxDoubleTapEnabled
        + " isFromDoubleTap= "
        + this.isFromLetterboxDoubleTap
        + " topActivityLetterboxVerticalPosition= "
        + this.topActivityLetterboxVerticalPosition
        + " topActivityLetterboxHorizontalPosition= "
        + this.topActivityLetterboxHorizontalPosition
        + " topActivityLetterboxWidth="
        + this.topActivityLetterboxWidth
        + " topActivityLetterboxHeight="
        + this.topActivityLetterboxHeight
        + " locusId="
        + this.mTopActivityLocusId
        + " displayAreaFeatureId="
        + this.displayAreaFeatureId
        + " cameraCompatControlState="
        + cameraCompatControlStateToString(this.cameraCompatControlState)
        + " originallySupportedMultiWindow="
        + this.originallySupportedMultiWindow
        + (this.supportsPipOnly ? " pipOnly=true" : "")
        + " hasWallpaper="
        + this.hasWallpaper
        + " topActivityInFixedAspectRatio="
        + this.topActivityInFixedAspectRatio
        + " rootAffinity="
        + this.rootAffinity
        + " topActivityInDisplayCompat="
        + this.topActivityInDisplayCompat
        + " topActivityInBoundsCompat="
        + this.topActivityInBoundsCompat
        + " topActivityBounds="
        + this.topActivityBounds
        + " singleTapFromLetterbox="
        + this.singleTapFromLetterbox
        + " isTopTaskInStage="
        + this.isTopTaskInStage
        + (this.isTranslucentTask ? " isTranslucentTask=true" : "")
        + (this.isCaptionHandlerHidden ? " handlerHidden=true" : "")
        + (this.topActivityUiMode != 0 ? " topActivityUiMode=" + this.topActivityUiMode : "")
        + (this.isFullScreenCaptionState ? "FullScreenCaptionState=true" : "")
        + " CoverLauncherWidgetTask="
        + this.isCoverLauncherWidgetTask
        + " CoverScreenTask="
        + this.isCoverScreenTask
        + " isAllowedSeamlessRotation="
        + this.isAllowedSeamlessRotation
        + " isTopTransparentActivity="
        + this.isTopTransparentActivity
        + " hasPopOver="
        + this.hasPopOver
        + "}";
  }

  public static String cameraCompatControlStateToString(int cameraCompatControlState) {
    switch (cameraCompatControlState) {
      case 0:
        return "hidden";
      case 1:
        return "treatment-suggested";
      case 2:
        return "treatment-applied";
      case 3:
        return "dismissed";
      default:
        throw new AssertionError(
            "Unexpected camera compat control state: " + cameraCompatControlState);
    }
  }
}
