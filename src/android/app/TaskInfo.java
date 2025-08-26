package android.app;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.LocusId;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.window.WindowContainerToken;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public class TaskInfo {
    public static final int PROPERTY_VALUE_UNSET = -1;
    private static final String TAG = "TaskInfo";
    public boolean activatableDeskRoot;
    public ComponentName baseActivity;
    public Intent baseIntent;
    public Uri capturedLink;
    public long capturedLinkTimestamp;
    public int defaultMinSize;
    public int desktopDefaultMinSize;
    public Rect displayCutoutInsets;
    public int displayId;
    public int effectiveUid;
    public boolean hasConfigChanged;
    public boolean hasNoTopWindow;
    public boolean hasWallpaper;
    public boolean isActivityStackTransparent;
    public boolean isAliasManaged;
    public boolean isAllowedSeamlessRotation;
    public boolean isCaptionHiddenRequested;
    public boolean isCoverLauncherWidgetTask;
    public boolean isDisplayCutoutHide;
    public boolean isFocused;
    public boolean isForceHidden;
    public boolean isFullSizeWindow;
    public boolean isGameToolsOverlayVisible;
    public boolean isKeepScreenOn;
    public boolean isLaunchedFromAppsCoverLauncher;
    public boolean isLaunchedFromHomeSwipe;
    public boolean isLaunchedFromMultistarCoverLauncher;
    public boolean isResizeable;
    public boolean isRunning;
    public boolean isSleeping;
    public boolean isTopActivityNoDisplay;
    public boolean isTopActivityTransparent;
    public boolean isTopFullScreenWindow;
    public boolean isTopTaskInStage;
    public boolean isTopTransparentActivity;
    public boolean isTranslucentTask;
    public boolean isVisible;
    public boolean isVisibleRequested;
    public long lastActiveTime;
    public Rect lastDesktopWindowingBounds;
    public Rect lastExternalDesktopWindowingBounds;
    public long lastGainFocusTime;
    public Rect lastNonFullscreenBounds;
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
    public boolean requestFullscreenMode;
    public int requestedVisibleTypes;
    public int resizeMode;
    public String rootAffinity;
    public Rect safeCutoutInsets;
    public boolean shouldDockBigOverlays;
    public Rect snappingGuideBounds;
    public boolean supportsMultiWindow;
    public boolean supportsPipOnly;
    public ActivityManager.TaskDescription taskDescription;
    public int taskId;
    public WindowContainerToken token;
    public ComponentName topActivity;
    public ActivityInfo topActivityInfo;
    public Rect topActivityMainWindowFrame;
    public long topActivityRequestOpenInBrowserEducationTimestamp;
    public int topActivityType;
    public int userId;
    public int displayAreaFeatureId = -1;
    public final Configuration configuration = new Configuration();
    public ArrayList<IBinder> launchCookies = new ArrayList<>();
    public AppCompatTaskInfo appCompatTaskInfo = AppCompatTaskInfo.create();
    public boolean isHandleImmersive = false;
    public boolean isAiKeyRemoveAppTask = false;

    TaskInfo() {
    }

    public TaskInfo(Parcel parcel) {
        readTaskFromParcel(parcel);
    }

    public int getTaskId() {
        return this.taskId;
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

    public void addLaunchCookie(IBinder iBinder) {
        if (iBinder == null || this.launchCookies.contains(iBinder)) {
            return;
        }
        this.launchCookies.add(iBinder);
    }

    public boolean containsLaunchCookie(IBinder iBinder) {
        return this.launchCookies.contains(iBinder);
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
        int i = this.resizeMode;
        return i == 6 || i == 5 || i == 7;
    }

    public boolean equalsForTaskOrganizer(TaskInfo taskInfo) {
        if (taskInfo == null || this.isForceHidden != taskInfo.isForceHidden || !Objects.equals(this.lastDesktopWindowingBounds, taskInfo.lastDesktopWindowingBounds) || !Objects.equals(this.lastExternalDesktopWindowingBounds, taskInfo.lastExternalDesktopWindowingBounds)) {
            return false;
        }
        if (CoreRune.MW_CAPTION && this.isTranslucentTask != taskInfo.isTranslucentTask) {
            return false;
        }
        if (CoreRune.MW_CAPTION_HANDLE && (this.isCaptionHiddenRequested != taskInfo.isCaptionHiddenRequested || this.isLaunchedFromHomeSwipe != taskInfo.isLaunchedFromHomeSwipe)) {
            return false;
        }
        if (CoreRune.MW_CAPTION_HANDLE_KEEP_SCREEN_ON && this.isKeepScreenOn != taskInfo.isKeepScreenOn) {
            return false;
        }
        if (!CoreRune.MW_CAPTION_FULL_SCREEN || (this.isTopFullScreenWindow == taskInfo.isTopFullScreenWindow && this.isGameToolsOverlayVisible == taskInfo.isGameToolsOverlayVisible && this.isFullSizeWindow == taskInfo.isFullSizeWindow && this.isHandleImmersive == taskInfo.isHandleImmersive)) {
            return (!CoreRune.MW_CAPTION_CUTOUT || (Objects.equals(this.safeCutoutInsets, taskInfo.safeCutoutInsets) && Objects.equals(Boolean.valueOf(this.isDisplayCutoutHide), Boolean.valueOf(taskInfo.isDisplayCutoutHide)))) && this.activatableDeskRoot == taskInfo.activatableDeskRoot && this.topActivityType == taskInfo.topActivityType && this.isResizeable == taskInfo.isResizeable && this.supportsMultiWindow == taskInfo.supportsMultiWindow && this.displayAreaFeatureId == taskInfo.displayAreaFeatureId && Objects.equals(this.positionInParent, taskInfo.positionInParent) && Objects.equals(this.pictureInPictureParams, taskInfo.pictureInPictureParams) && Objects.equals(Boolean.valueOf(this.shouldDockBigOverlays), Boolean.valueOf(taskInfo.shouldDockBigOverlays)) && Objects.equals(this.displayCutoutInsets, taskInfo.displayCutoutInsets) && getWindowingMode() == taskInfo.getWindowingMode() && this.configuration.uiMode == taskInfo.configuration.uiMode && Objects.equals(this.taskDescription, taskInfo.taskDescription) && this.isFocused == taskInfo.isFocused && this.isVisible == taskInfo.isVisible && this.isVisibleRequested == taskInfo.isVisibleRequested && this.isTopActivityNoDisplay == taskInfo.isTopActivityNoDisplay && this.isSleeping == taskInfo.isSleeping && Objects.equals(this.mTopActivityLocusId, taskInfo.mTopActivityLocusId) && this.parentTaskId == taskInfo.parentTaskId && Objects.equals(this.topActivity, taskInfo.topActivity) && this.isTopActivityTransparent == taskInfo.isTopActivityTransparent && this.isActivityStackTransparent == taskInfo.isActivityStackTransparent && Objects.equals(this.lastNonFullscreenBounds, taskInfo.lastNonFullscreenBounds) && Objects.equals(this.capturedLink, taskInfo.capturedLink) && this.capturedLinkTimestamp == taskInfo.capturedLinkTimestamp && this.requestedVisibleTypes == taskInfo.requestedVisibleTypes && this.topActivityRequestOpenInBrowserEducationTimestamp == taskInfo.topActivityRequestOpenInBrowserEducationTimestamp && this.appCompatTaskInfo.equalsForTaskOrganizer(taskInfo.appCompatTaskInfo) && Objects.equals(this.topActivityMainWindowFrame, taskInfo.topActivityMainWindowFrame);
        }
        return false;
    }

    public boolean equalsForCompatUi(TaskInfo taskInfo) {
        if (taskInfo == null) {
            return false;
        }
        boolean zHasCompatUI = this.appCompatTaskInfo.hasCompatUI();
        return this.displayId == taskInfo.displayId && this.taskId == taskInfo.taskId && this.isTopActivityTransparent == taskInfo.isTopActivityTransparent && this.appCompatTaskInfo.equalsForCompatUi(taskInfo.appCompatTaskInfo) && (!zHasCompatUI || this.configuration.windowConfiguration.getBounds().equals(taskInfo.configuration.windowConfiguration.getBounds())) && ((!zHasCompatUI || this.configuration.getLayoutDirection() == taskInfo.configuration.getLayoutDirection()) && ((!zHasCompatUI || this.configuration.uiMode == taskInfo.configuration.uiMode) && (!zHasCompatUI || this.isVisible == taskInfo.isVisible)));
    }

    void readTaskFromParcel(Parcel parcel) {
        this.userId = parcel.readInt();
        this.taskId = parcel.readInt();
        this.effectiveUid = parcel.readInt();
        this.displayId = parcel.readInt();
        this.isRunning = parcel.readBoolean();
        this.baseIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        this.baseActivity = ComponentName.readFromParcel(parcel);
        this.topActivity = ComponentName.readFromParcel(parcel);
        this.origActivity = ComponentName.readFromParcel(parcel);
        this.realActivity = ComponentName.readFromParcel(parcel);
        this.numActivities = parcel.readInt();
        this.lastActiveTime = parcel.readLong();
        this.taskDescription = (ActivityManager.TaskDescription) parcel.readTypedObject(ActivityManager.TaskDescription.CREATOR);
        this.supportsMultiWindow = parcel.readBoolean();
        this.resizeMode = parcel.readInt();
        this.configuration.readFromParcel(parcel);
        this.token = WindowContainerToken.CREATOR.createFromParcel(parcel);
        this.topActivityType = parcel.readInt();
        this.pictureInPictureParams = (PictureInPictureParams) parcel.readTypedObject(PictureInPictureParams.CREATOR);
        this.shouldDockBigOverlays = parcel.readBoolean();
        this.launchIntoPipHostTaskId = parcel.readInt();
        this.lastParentTaskIdBeforePip = parcel.readInt();
        this.displayCutoutInsets = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.topActivityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
        this.isResizeable = parcel.readBoolean();
        this.minWidth = parcel.readInt();
        this.minHeight = parcel.readInt();
        this.defaultMinSize = parcel.readInt();
        this.desktopDefaultMinSize = parcel.readInt();
        parcel.readBinderList(this.launchCookies);
        this.positionInParent = (Point) parcel.readTypedObject(Point.CREATOR);
        this.parentTaskId = parcel.readInt();
        this.isFocused = parcel.readBoolean();
        this.isVisible = parcel.readBoolean();
        this.isVisibleRequested = parcel.readBoolean();
        this.isTopActivityNoDisplay = parcel.readBoolean();
        this.isSleeping = parcel.readBoolean();
        this.mTopActivityLocusId = (LocusId) parcel.readTypedObject(LocusId.CREATOR);
        this.displayAreaFeatureId = parcel.readInt();
        this.isTopActivityTransparent = parcel.readBoolean();
        this.isActivityStackTransparent = parcel.readBoolean();
        this.lastNonFullscreenBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.capturedLink = (Uri) parcel.readTypedObject(Uri.CREATOR);
        this.capturedLinkTimestamp = parcel.readLong();
        this.requestedVisibleTypes = parcel.readInt();
        this.topActivityRequestOpenInBrowserEducationTimestamp = parcel.readLong();
        this.appCompatTaskInfo = (AppCompatTaskInfo) parcel.readTypedObject(AppCompatTaskInfo.CREATOR);
        this.topActivityMainWindowFrame = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.lastGainFocusTime = parcel.readLong();
        this.originallySupportedMultiWindow = parcel.readBoolean();
        this.supportsPipOnly = parcel.readBoolean();
        this.hasWallpaper = parcel.readBoolean();
        this.rootAffinity = parcel.readString();
        this.isTopTaskInStage = parcel.readBoolean();
        this.isAllowedSeamlessRotation = parcel.readBoolean();
        this.isForceHidden = parcel.readBoolean();
        this.maxWidth = parcel.readInt();
        this.maxHeight = parcel.readInt();
        this.isTopTransparentActivity = parcel.readBoolean();
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APPS_CUTOUT) {
            this.isLaunchedFromAppsCoverLauncher = parcel.readBoolean();
        }
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT_ASPECT_RATIO_POLICY) {
            this.isLaunchedFromMultistarCoverLauncher = parcel.readBoolean();
        }
        this.isAliasManaged = parcel.readBoolean();
        this.hasConfigChanged = parcel.readBoolean();
        this.isAiKeyRemoveAppTask = parcel.readBoolean();
        this.lastDesktopWindowingBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.lastExternalDesktopWindowingBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        if (CoreRune.MW_CAPTION) {
            this.isTranslucentTask = parcel.readBoolean();
        }
        if (CoreRune.MW_CAPTION_HANDLE) {
            this.isCaptionHiddenRequested = parcel.readBoolean();
            this.isLaunchedFromHomeSwipe = parcel.readBoolean();
        }
        if (CoreRune.MW_CAPTION_HANDLE_KEEP_SCREEN_ON) {
            this.isKeepScreenOn = parcel.readBoolean();
        }
        if (CoreRune.MW_CAPTION_FULL_SCREEN) {
            this.isTopFullScreenWindow = parcel.readBoolean();
            this.isGameToolsOverlayVisible = parcel.readBoolean();
            this.isFullSizeWindow = parcel.readBoolean();
            this.isHandleImmersive = parcel.readBoolean();
        }
        if (CoreRune.MW_CAPTION_CUTOUT) {
            this.safeCutoutInsets = (Rect) parcel.readTypedObject(Rect.CREATOR);
            this.isDisplayCutoutHide = parcel.readBoolean();
        }
        this.activatableDeskRoot = parcel.readBoolean();
        this.hasNoTopWindow = parcel.readBoolean();
        this.requestFullscreenMode = parcel.readBoolean();
    }

    public void writeTaskToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.userId);
        parcel.writeInt(this.taskId);
        parcel.writeInt(this.effectiveUid);
        parcel.writeInt(this.displayId);
        parcel.writeBoolean(this.isRunning);
        parcel.writeTypedObject(this.baseIntent, 0);
        ComponentName.writeToParcel(this.baseActivity, parcel);
        ComponentName.writeToParcel(this.topActivity, parcel);
        ComponentName.writeToParcel(this.origActivity, parcel);
        ComponentName.writeToParcel(this.realActivity, parcel);
        parcel.writeInt(this.numActivities);
        parcel.writeLong(this.lastActiveTime);
        parcel.writeTypedObject(this.taskDescription, i);
        parcel.writeBoolean(this.supportsMultiWindow);
        parcel.writeInt(this.resizeMode);
        this.configuration.writeToParcel(parcel, i);
        this.token.writeToParcel(parcel, i);
        parcel.writeInt(this.topActivityType);
        parcel.writeTypedObject(this.pictureInPictureParams, i);
        parcel.writeBoolean(this.shouldDockBigOverlays);
        parcel.writeInt(this.launchIntoPipHostTaskId);
        parcel.writeInt(this.lastParentTaskIdBeforePip);
        parcel.writeTypedObject(this.displayCutoutInsets, i);
        parcel.writeTypedObject(this.topActivityInfo, i);
        parcel.writeBoolean(this.isResizeable);
        parcel.writeInt(this.minWidth);
        parcel.writeInt(this.minHeight);
        parcel.writeInt(this.defaultMinSize);
        parcel.writeInt(this.desktopDefaultMinSize);
        parcel.writeBinderList(this.launchCookies);
        parcel.writeTypedObject(this.positionInParent, i);
        parcel.writeInt(this.parentTaskId);
        parcel.writeBoolean(this.isFocused);
        parcel.writeBoolean(this.isVisible);
        parcel.writeBoolean(this.isVisibleRequested);
        parcel.writeBoolean(this.isTopActivityNoDisplay);
        parcel.writeBoolean(this.isSleeping);
        parcel.writeTypedObject(this.mTopActivityLocusId, i);
        parcel.writeInt(this.displayAreaFeatureId);
        parcel.writeBoolean(this.isTopActivityTransparent);
        parcel.writeBoolean(this.isActivityStackTransparent);
        parcel.writeTypedObject(this.lastNonFullscreenBounds, i);
        parcel.writeTypedObject(this.capturedLink, i);
        parcel.writeLong(this.capturedLinkTimestamp);
        parcel.writeInt(this.requestedVisibleTypes);
        parcel.writeLong(this.topActivityRequestOpenInBrowserEducationTimestamp);
        parcel.writeTypedObject(this.appCompatTaskInfo, i);
        parcel.writeTypedObject(this.topActivityMainWindowFrame, i);
        parcel.writeLong(this.lastGainFocusTime);
        parcel.writeBoolean(this.originallySupportedMultiWindow);
        parcel.writeBoolean(this.supportsPipOnly);
        parcel.writeBoolean(this.hasWallpaper);
        parcel.writeString(this.rootAffinity);
        parcel.writeBoolean(this.isTopTaskInStage);
        parcel.writeBoolean(this.isAllowedSeamlessRotation);
        parcel.writeBoolean(this.isForceHidden);
        parcel.writeInt(this.maxWidth);
        parcel.writeInt(this.maxHeight);
        parcel.writeBoolean(this.isTopTransparentActivity);
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APPS_CUTOUT) {
            parcel.writeBoolean(this.isLaunchedFromAppsCoverLauncher);
        }
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT_ASPECT_RATIO_POLICY) {
            parcel.writeBoolean(this.isLaunchedFromMultistarCoverLauncher);
        }
        parcel.writeBoolean(this.isAliasManaged);
        parcel.writeBoolean(this.hasConfigChanged);
        parcel.writeBoolean(this.isAiKeyRemoveAppTask);
        parcel.writeTypedObject(this.lastDesktopWindowingBounds, i);
        parcel.writeTypedObject(this.lastExternalDesktopWindowingBounds, i);
        if (CoreRune.MW_CAPTION) {
            parcel.writeBoolean(this.isTranslucentTask);
        }
        if (CoreRune.MW_CAPTION_HANDLE) {
            parcel.writeBoolean(this.isCaptionHiddenRequested);
            parcel.writeBoolean(this.isLaunchedFromHomeSwipe);
        }
        if (CoreRune.MW_CAPTION_HANDLE_KEEP_SCREEN_ON) {
            parcel.writeBoolean(this.isKeepScreenOn);
        }
        if (CoreRune.MW_CAPTION_FULL_SCREEN) {
            parcel.writeBoolean(this.isTopFullScreenWindow);
            parcel.writeBoolean(this.isGameToolsOverlayVisible);
            parcel.writeBoolean(this.isFullSizeWindow);
            parcel.writeBoolean(this.isHandleImmersive);
        }
        if (CoreRune.MW_CAPTION_CUTOUT) {
            parcel.writeTypedObject(this.safeCutoutInsets, i);
            parcel.writeBoolean(this.isDisplayCutoutHide);
        }
        parcel.writeBoolean(this.activatableDeskRoot);
        parcel.writeBoolean(this.hasNoTopWindow);
        parcel.writeBoolean(this.requestFullscreenMode);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("TaskInfo{userId=");
        sb.append(this.userId);
        sb.append(" taskId=");
        sb.append(this.taskId);
        sb.append(" effectiveUid=");
        sb.append(this.effectiveUid);
        sb.append(" displayId=");
        sb.append(this.displayId);
        sb.append(" isRunning=");
        sb.append(this.isRunning);
        sb.append(" baseIntent=");
        sb.append(this.baseIntent);
        sb.append(" baseActivity=");
        sb.append(this.baseActivity);
        sb.append(" topActivity=");
        sb.append(this.topActivity);
        sb.append(" origActivity=");
        sb.append(this.origActivity);
        sb.append(" realActivity=");
        sb.append(this.realActivity);
        sb.append(" numActivities=");
        sb.append(this.numActivities);
        sb.append(" lastActiveTime=");
        sb.append(this.lastActiveTime);
        sb.append(" supportsMultiWindow=");
        sb.append(this.supportsMultiWindow);
        sb.append(" resizeMode=");
        sb.append(this.resizeMode);
        sb.append(" isResizeable=");
        sb.append(this.isResizeable);
        sb.append(" minWidth=");
        sb.append(this.minWidth);
        sb.append(" minHeight=");
        sb.append(this.minHeight);
        sb.append(" maxWidth=");
        sb.append(this.maxWidth);
        sb.append(" maxHeight=");
        sb.append(this.maxHeight);
        sb.append(" defaultMinSize=");
        sb.append(this.defaultMinSize);
        sb.append(" desktopDefaultMinSize=");
        sb.append(this.desktopDefaultMinSize);
        sb.append(" token=");
        sb.append(this.token);
        sb.append(" topActivityType=");
        sb.append(this.topActivityType);
        sb.append(" pictureInPictureParams=");
        sb.append(this.pictureInPictureParams);
        sb.append(" shouldDockBigOverlays=");
        sb.append(this.shouldDockBigOverlays);
        sb.append(" launchIntoPipHostTaskId=");
        sb.append(this.launchIntoPipHostTaskId);
        sb.append(" lastParentTaskIdBeforePip=");
        sb.append(this.lastParentTaskIdBeforePip);
        sb.append(" displayCutoutSafeInsets=");
        sb.append(this.displayCutoutInsets);
        sb.append(" topActivityInfo=");
        sb.append(this.topActivityInfo);
        sb.append(" launchCookies=");
        sb.append(this.launchCookies);
        sb.append(" positionInParent=");
        sb.append(this.positionInParent);
        sb.append(" parentTaskId=");
        sb.append(this.parentTaskId);
        sb.append(" isFocused=");
        sb.append(this.isFocused);
        sb.append(" isVisible=");
        sb.append(this.isVisible);
        sb.append(" isVisibleRequested=");
        sb.append(this.isVisibleRequested);
        sb.append(" isTopActivityNoDisplay=");
        sb.append(this.isTopActivityNoDisplay);
        sb.append(" isSleeping=");
        sb.append(this.isSleeping);
        sb.append(" locusId=");
        sb.append(this.mTopActivityLocusId);
        sb.append(" displayAreaFeatureId=");
        sb.append(this.displayAreaFeatureId);
        sb.append(" isTopActivityTransparent=");
        sb.append(this.isTopActivityTransparent);
        sb.append(" isActivityStackTransparent=");
        sb.append(this.isActivityStackTransparent);
        sb.append(" lastNonFullscreenBounds=");
        sb.append(this.lastNonFullscreenBounds);
        sb.append(" capturedLink=");
        sb.append(this.capturedLink);
        sb.append(" capturedLinkTimestamp=");
        sb.append(this.capturedLinkTimestamp);
        sb.append(" requestedVisibleTypes=");
        sb.append(this.requestedVisibleTypes);
        sb.append(" topActivityRequestOpenInBrowserEducationTimestamp=");
        sb.append(this.topActivityRequestOpenInBrowserEducationTimestamp);
        sb.append(" appCompatTaskInfo=");
        sb.append(this.appCompatTaskInfo);
        sb.append(" topActivityMainWindowFrame=");
        sb.append(this.topActivityMainWindowFrame);
        sb.append(" originallySupportedMultiWindow=");
        sb.append(this.originallySupportedMultiWindow);
        sb.append(this.supportsPipOnly ? " pipOnly=true" : "");
        sb.append(" hasWallpaper=");
        sb.append(this.hasWallpaper);
        sb.append(" rootAffinity=");
        sb.append(this.rootAffinity);
        sb.append(" isTopTaskInStage=");
        sb.append(this.isTopTaskInStage);
        sb.append(" CoverLauncherWidgetTask=");
        sb.append(this.isCoverLauncherWidgetTask);
        sb.append(" LaunchedFromAppsCoverLauncherTask=");
        sb.append(this.isLaunchedFromAppsCoverLauncher);
        sb.append(" LaunchedFromMultistarCoverLauncherTask=");
        sb.append(this.isLaunchedFromMultistarCoverLauncher);
        sb.append(" isAllowedSeamlessRotation=");
        sb.append(this.isAllowedSeamlessRotation);
        sb.append(" isTopTransparentActivity=");
        sb.append(this.isTopTransparentActivity);
        sb.append(" isAliasManaged=");
        sb.append(this.isAliasManaged);
        sb.append(" hasConfigChanged=");
        sb.append(this.hasConfigChanged);
        sb.append(" snappingGuideBounds=");
        sb.append(this.snappingGuideBounds);
        sb.append(" isAiKeyRemoveAppTask=");
        sb.append(this.isAiKeyRemoveAppTask);
        sb.append(" lastDesktopWindowingBounds=");
        sb.append(this.lastDesktopWindowingBounds);
        sb.append(" lastExternalDesktopWindowingBounds=");
        sb.append(this.lastExternalDesktopWindowingBounds);
        sb.append(this.isTranslucentTask ? " isTranslucentTask=true" : "");
        sb.append(this.isCaptionHiddenRequested ? " handlerHidden=true" : "");
        sb.append(this.isLaunchedFromHomeSwipe ? " isLaunchedFromHomeSwipe=true" : "");
        sb.append(this.isKeepScreenOn ? " isKeepScreenOn=true" : "");
        sb.append(this.isTopFullScreenWindow ? " isTopFullScreenWindow=true" : "");
        sb.append(this.isGameToolsOverlayVisible ? " isGameToolsOverlayVisible=true" : "");
        sb.append(this.isFullSizeWindow ? " isFullSizeWindow=true" : "");
        sb.append(this.isHandleImmersive ? "isHandleImmersive=true" : "");
        sb.append(" safeCutoutInsets=");
        sb.append(this.safeCutoutInsets);
        sb.append(this.isDisplayCutoutHide ? " isDisplayCutoutHide=true" : "");
        sb.append(this.requestFullscreenMode ? " exitDesktopByLaunchingFullscreenMode=true" : "");
        sb.append("}");
        return sb.toString();
    }
}
