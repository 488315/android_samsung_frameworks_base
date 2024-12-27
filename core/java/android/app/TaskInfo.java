package android.app;

import android.content.ComponentName;
import android.content.Intent;
import android.content.LocusId;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.Parcel;
import android.window.WindowContainerToken;

import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.Objects;

public class TaskInfo {
    public static final int PROPERTY_VALUE_UNSET = -1;
    private static final String TAG = "TaskInfo";
    public ComponentName baseActivity;
    public Intent baseIntent;
    public int defaultMinSize;
    public Rect displayCutoutInsets;
    public int displayId;
    public boolean hasConfigChanged;
    public boolean hasPopOver;
    public boolean hasWallpaper;
    public boolean isAliasManaged;
    public boolean isAllowedSeamlessRotation;
    public boolean isCaptionHandlerHidden;
    public boolean isCoverLauncherWidgetTask;
    public boolean isFocused;
    public boolean isForceHidden;
    public boolean isFullScreenCaptionState;
    public boolean isKeepScreenOn;
    public boolean isResizeable;
    public boolean isRotationButtonVisible;
    public boolean isRunning;
    public boolean isSleeping;
    public boolean isTopActivityStyleFloating;
    public boolean isTopActivityTransparent;
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
    public SizeCompatInfo sizeCompatInfo;
    public Rect snappingGuideBounds;
    public boolean supportsMultiWindow;
    public boolean supportsPipOnly;
    public ActivityManager.TaskDescription taskDescription;
    public int taskId;
    public WindowContainerToken token;
    public ComponentName topActivity;
    public boolean topActivityInSizeCompat;
    public ActivityInfo topActivityInfo;
    public int topActivityType;
    public int topActivityUiMode;
    public int userId;
    public int displayAreaFeatureId = -1;
    public final Configuration configuration = new Configuration();
    public ArrayList<IBinder> launchCookies = new ArrayList<>();
    public AppCompatTaskInfo appCompatTaskInfo = AppCompatTaskInfo.create();
    public boolean isAiKeyRemoveAppTask = false;

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

    public boolean isFreeform() {
        return this.configuration.windowConfiguration.getWindowingMode() == 5;
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

    public boolean preserveOrientationOnResize() {
        return this.resizeMode == 6 || this.resizeMode == 5 || this.resizeMode == 7;
    }

    public boolean equalsForTaskOrganizer(TaskInfo that) {
        if (that == null) {
            return false;
        }
        if (CoreRune.MW_CAPTION_SHELL) {
            if (this.isCaptionHandlerHidden != that.isCaptionHandlerHidden
                    || this.isTranslucentTask != that.isTranslucentTask
                    || this.configuration.fontScale != that.getConfiguration().fontScale) {
                return false;
            }
            if (isFreeform()
                    && this.configuration.semDisplayDeviceType
                            != that.getConfiguration().semDisplayDeviceType) {
                return false;
            }
        }
        if ((CoreRune.MW_CAPTION_SHELL_FULL_SCREEN
                        && this.isFullScreenCaptionState != that.isFullScreenCaptionState)
                || this.isForceHidden != that.isForceHidden) {
            return false;
        }
        if (CoreRune.MW_CAPTION_SHELL_KEEP_SCREEN_ON
                && this.isKeepScreenOn != that.isKeepScreenOn) {
            return false;
        }
        if (!CoreRune.MD_DEX_COMPAT_CAPTION_SHELL
                || this.isRotationButtonVisible == that.isRotationButtonVisible) {
            return (!CoreRune.MW_SPLIT_FLEX_PANEL_MODE
                            || this.configuration.windowConfiguration.isFlexPanelEnabled()
                                    == that.configuration.windowConfiguration.isFlexPanelEnabled())
                    && this.topActivityType == that.topActivityType
                    && this.isResizeable == that.isResizeable
                    && this.supportsMultiWindow == that.supportsMultiWindow
                    && this.displayAreaFeatureId == that.displayAreaFeatureId
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
                    && Objects.equals(this.topActivity, that.topActivity)
                    && this.isTopActivityTransparent == that.isTopActivityTransparent
                    && this.isTopActivityStyleFloating == that.isTopActivityStyleFloating
                    && this.appCompatTaskInfo.equalsForTaskOrganizer(that.appCompatTaskInfo);
        }
        return false;
    }

    public boolean equalsForCompatUi(TaskInfo that) {
        if (that == null) {
            return false;
        }
        boolean hasCompatUI = this.appCompatTaskInfo.hasCompatUI();
        if (this.displayId != that.displayId
                || this.taskId != that.taskId
                || this.topActivityInSizeCompat != that.topActivityInSizeCompat
                || this.isFocused != that.isFocused
                || this.isTopActivityTransparent != that.isTopActivityTransparent
                || !this.appCompatTaskInfo.equalsForCompatUi(that.appCompatTaskInfo)) {
            return false;
        }
        if (hasCompatUI
                && !this.configuration
                        .windowConfiguration
                        .getBounds()
                        .equals(that.configuration.windowConfiguration.getBounds())) {
            return false;
        }
        if (hasCompatUI
                && this.configuration.getLayoutDirection()
                        != that.configuration.getLayoutDirection()) {
            return false;
        }
        if (!hasCompatUI || this.configuration.uiMode == that.configuration.uiMode) {
            return !hasCompatUI || this.isVisible == that.isVisible;
        }
        return false;
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
        this.mTopActivityLocusId = (LocusId) source.readTypedObject(LocusId.CREATOR);
        this.displayAreaFeatureId = source.readInt();
        this.isTopActivityTransparent = source.readBoolean();
        this.isTopActivityStyleFloating = source.readBoolean();
        this.appCompatTaskInfo =
                (AppCompatTaskInfo) source.readTypedObject(AppCompatTaskInfo.CREATOR);
        this.lastGainFocusTime = source.readLong();
        this.originallySupportedMultiWindow = source.readBoolean();
        this.supportsPipOnly = source.readBoolean();
        this.hasWallpaper = source.readBoolean();
        this.rootAffinity = source.readString();
        if (CoreRune.MT_SIZE_COMPAT_POLICY) {
            this.topActivityInSizeCompat = source.readBoolean();
            this.sizeCompatInfo = (SizeCompatInfo) source.readTypedObject(SizeCompatInfo.CREATOR);
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
        if (CoreRune.MW_CAPTION_SHELL_KEEP_SCREEN_ON) {
            this.isKeepScreenOn = source.readBoolean();
        }
        if (CoreRune.MD_DEX_COMPAT_CAPTION_SHELL) {
            this.isRotationButtonVisible = source.readBoolean();
        }
        this.isAllowedSeamlessRotation = source.readBoolean();
        this.isForceHidden = source.readBoolean();
        if (CoreRune.MW_CAPTION_SHELL_FREEFORM_RESIZE_VIEW) {
            this.maxWidth = source.readInt();
            this.maxHeight = source.readInt();
        }
        this.isTopTransparentActivity = source.readBoolean();
        if (CoreRune.MW_CAPTION_SHELL_DEX_SNAPPING_WINDOW) {
            this.snappingGuideBounds = (Rect) source.readTypedObject(Rect.CREATOR);
        }
        this.hasPopOver = source.readBoolean();
        this.isAliasManaged = source.readBoolean();
        this.hasConfigChanged = source.readBoolean();
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
        dest.writeTypedObject(this.mTopActivityLocusId, flags);
        dest.writeInt(this.displayAreaFeatureId);
        dest.writeBoolean(this.isTopActivityTransparent);
        dest.writeBoolean(this.isTopActivityStyleFloating);
        dest.writeTypedObject(this.appCompatTaskInfo, flags);
        dest.writeLong(this.lastGainFocusTime);
        dest.writeBoolean(this.originallySupportedMultiWindow);
        dest.writeBoolean(this.supportsPipOnly);
        dest.writeBoolean(this.hasWallpaper);
        dest.writeString(this.rootAffinity);
        if (CoreRune.MT_SIZE_COMPAT_POLICY) {
            dest.writeBoolean(this.topActivityInSizeCompat);
            dest.writeTypedObject(this.sizeCompatInfo, flags);
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
        if (CoreRune.MW_CAPTION_SHELL_KEEP_SCREEN_ON) {
            dest.writeBoolean(this.isKeepScreenOn);
        }
        if (CoreRune.MD_DEX_COMPAT_CAPTION_SHELL) {
            dest.writeBoolean(this.isRotationButtonVisible);
        }
        dest.writeBoolean(this.isAllowedSeamlessRotation);
        dest.writeBoolean(this.isForceHidden);
        if (CoreRune.MW_CAPTION_SHELL_FREEFORM_RESIZE_VIEW) {
            dest.writeInt(this.maxWidth);
            dest.writeInt(this.maxHeight);
        }
        dest.writeBoolean(this.isTopTransparentActivity);
        if (CoreRune.MW_CAPTION_SHELL_DEX_SNAPPING_WINDOW) {
            dest.writeTypedObject(this.snappingGuideBounds, flags);
        }
        dest.writeBoolean(this.hasPopOver);
        dest.writeBoolean(this.isAliasManaged);
        dest.writeBoolean(this.hasConfigChanged);
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
                + " locusId="
                + this.mTopActivityLocusId
                + " displayAreaFeatureId="
                + this.displayAreaFeatureId
                + " isTopActivityTransparent="
                + this.isTopActivityTransparent
                + " isTopActivityStyleFloating="
                + this.isTopActivityStyleFloating
                + " appCompatTaskInfo="
                + this.appCompatTaskInfo
                + " originallySupportedMultiWindow="
                + this.originallySupportedMultiWindow
                + (this.supportsPipOnly ? " pipOnly=true" : "")
                + " hasWallpaper="
                + this.hasWallpaper
                + " rootAffinity="
                + this.rootAffinity
                + " isTopTaskInStage="
                + this.isTopTaskInStage
                + (this.isTranslucentTask ? " isTranslucentTask=true" : "")
                + (this.isCaptionHandlerHidden ? " handlerHidden=true" : "")
                + (this.topActivityUiMode != 0
                        ? " topActivityUiMode=" + this.topActivityUiMode
                        : "")
                + (this.isFullScreenCaptionState ? "FullScreenCaptionState=true" : "")
                + " CoverLauncherWidgetTask="
                + this.isCoverLauncherWidgetTask
                + (this.isKeepScreenOn ? " isKeepScreenOn=true" : "")
                + " isAllowedSeamlessRotation="
                + this.isAllowedSeamlessRotation
                + " isTopTransparentActivity="
                + this.isTopTransparentActivity
                + " snappingGuideBounds="
                + this.snappingGuideBounds
                + " hasPopOver="
                + this.hasPopOver
                + " isAliasManaged="
                + this.isAliasManaged
                + " hasConfigChanged="
                + this.hasConfigChanged
                + " isAiKeyRemoveAppTask="
                + this.isAiKeyRemoveAppTask
                + "}";
    }
}
