package android.app;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.p009os.Bundle;
import android.p009os.Debug;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.IRemoteCallback;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.RemoteException;
import android.p009os.ResultReceiver;
import android.p009os.SystemClock;
import android.transition.TransitionManager;
import android.util.Pair;
import android.util.Slog;
import android.view.AppTransitionAnimationSpec;
import android.view.IAppTransitionAnimationSpecsFuture;
import android.view.RemoteAnimationAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.window.RemoteTransition;
import android.window.WindowContainerToken;
import com.android.internal.C4337R;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ActivityOptions extends ComponentOptions {
  public static final int ANIM_CLIP_REVEAL = 11;
  public static final int ANIM_CUSTOM = 1;
  public static final int ANIM_CUSTOM_DISPLAY_CHANGE = 15;
  public static final int ANIM_CUSTOM_IN_PLACE = 10;
  public static final int ANIM_DEFAULT = 6;
  public static final int ANIM_FROM_STYLE = 14;
  public static final int ANIM_LAUNCH_TASK_BEHIND = 7;
  public static final int ANIM_NONE = 0;
  public static final int ANIM_OPEN_CROSS_PROFILE_APPS = 12;
  public static final int ANIM_REMOTE_ANIMATION = 13;
  public static final int ANIM_SCALE_UP = 2;
  public static final int ANIM_SCENE_TRANSITION = 5;
  public static final int ANIM_THUMBNAIL_ASPECT_SCALE_DOWN = 9;
  public static final int ANIM_THUMBNAIL_ASPECT_SCALE_UP = 8;
  public static final int ANIM_THUMBNAIL_SCALE_DOWN = 4;
  public static final int ANIM_THUMBNAIL_SCALE_UP = 3;
  public static final int ANIM_UNDEFINED = -1;
  public static final int END = 2;
  public static final String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
  public static final String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";
  public static final int INVALID_ML_TYPE = -1;
  private static final String KEY_ACTIVE_LAUNCH_HINT = "android:activity.isActiveLaunch";
  private static final String KEY_ACTIVITY_EMBEDDED_PLACEHOLDER =
      "android:activity.activityEmbeddedPlaceholder";
  private static final String KEY_ALLOW_ENTER_PIP_WHILE_LAUNCHING =
      "android.activity.enterPipWhileLaunching";
  private static final String KEY_ANIMATION_FINISHED_LISTENER =
      "android:activity.animationFinishedListener";
  public static final String KEY_ANIM_BACKGROUND_COLOR = "android:activity.backgroundColor";
  public static final String KEY_ANIM_ENTER_RES_ID = "android:activity.animEnterRes";
  public static final String KEY_ANIM_EXIT_RES_ID = "android:activity.animExitRes";
  public static final String KEY_ANIM_HEIGHT = "android:activity.animHeight";
  public static final String KEY_ANIM_IN_PLACE_RES_ID = "android:activity.animInPlaceRes";
  private static final String KEY_ANIM_SPECS = "android:activity.animSpecs";
  public static final String KEY_ANIM_START_LISTENER = "android:activity.animStartListener";
  public static final String KEY_ANIM_START_X = "android:activity.animStartX";
  public static final String KEY_ANIM_START_Y = "android:activity.animStartY";
  public static final String KEY_ANIM_THUMBNAIL = "android:activity.animThumbnail";
  public static final String KEY_ANIM_TYPE = "android:activity.animType";
  public static final String KEY_ANIM_WIDTH = "android:activity.animWidth";
  private static final String KEY_APPLY_ACTIVITY_FLAGS_FOR_BUBBLES =
      "android:activity.applyActivityFlagsForBubbles";
  private static final String KEY_APPLY_BIG_FREEFORM_SIZE = "android:activity.applyBigFreeformSize";
  private static final String KEY_APPLY_MULTIPLE_TASK_FLAG_FOR_SHORTCUT =
      "android:activity.applyMultipleTaskFlagForShortcut";
  private static final String KEY_APPLY_NO_USER_ACTION_FLAG_FOR_SHORTCUT =
      "android:activity.applyNoUserActionFlagForShortcut";
  private static final String KEY_AVOID_MOVE_TO_FRONT = "android.activity.avoidMoveToFront";
  private static final String KEY_CALLER_DISPLAY_ID = "android.activity.callerDisplayId";
  private static final String KEY_CUSTOMIZED_COVER_DENSITY =
      "android.activity.customizedCoverDensity";
  private static final String KEY_DISABLE_STARTING_WINDOW = "android.activity.disableStarting";
  private static final String KEY_DISALLOW_ENTER_PICTURE_IN_PICTURE_WHILE_LAUNCHING =
      "android:activity.disallowEnterPictureInPictureWhileLaunching";
  private static final String KEY_DISMISS_KEYGUARD = "android.activity.dismissKeyguard";
  private static final String KEY_DISMISS_SPLIT_BEFORE_LAUNCH =
      "android:activity.dismissSplitBeforeLaunch";
  private static final String KEY_ENTER_SPLIT_SIDE_WITH_ADJACENT_FLAG =
      "android:activity.enterSplitSideWithAdjacentFlag";
  private static final String KEY_EXIT_COORDINATOR_INDEX = "android:activity.exitCoordinatorIndex";
  private static final String KEY_FORCE_LAUNCH_TASK_ON_HOME =
      "android.activity.forceLaunchTaskOnHome";
  private static final String KEY_FORCE_LAUNCH_WINDOWING_MODE =
      "android.activity.forceWindowingMode";
  private static final String KEY_FREEZE_RECENT_TASKS_REORDERING =
      "android.activity.freezeRecentTasksReordering";
  private static final String KEY_INSTANT_APP_VERIFICATION_BUNDLE =
      "android:instantapps.installerbundle";
  public static final String KEY_LATE_TRANSIENT_LAUNCH = "android.activity.lateTransientLaunch";
  private static final String KEY_LAUNCHED_FROM_BUBBLE = "android.activity.launchTypeBubble";
  private static final String KEY_LAUNCHED_FROM_DND = "android.activity.launchTypeDnD";
  private static final String KEY_LAUNCH_ACTIVITY_TYPE = "android.activity.activityType";
  public static final String KEY_LAUNCH_BOUNDS = "android:activity.launchBounds";
  public static final String KEY_LAUNCH_COOKIE = "android.activity.launchCookie";
  private static final String KEY_LAUNCH_DISPLAY_ID = "android.activity.launchDisplayId";
  private static final String KEY_LAUNCH_INTO_PIP_PARAMS = "android.activity.launchIntoPipParams";
  private static final String KEY_LAUNCH_IN_FOCUSED_STAGE_ROOT =
      "android:activity.launchInFocusedStageRoot";
  public static final String KEY_LAUNCH_ROOT_TASK_TOKEN = "android.activity.launchRootTaskToken";
  private static final String KEY_LAUNCH_TASK_DISPLAY_AREA_FEATURE_ID =
      "android.activity.launchTaskDisplayAreaFeatureId";
  private static final String KEY_LAUNCH_TASK_DISPLAY_AREA_TOKEN =
      "android.activity.launchTaskDisplayAreaToken";
  public static final String KEY_LAUNCH_TASK_FRAGMENT_TOKEN =
      "android.activity.launchTaskFragmentToken";
  private static final String KEY_LAUNCH_TASK_ID = "android.activity.launchTaskId";
  private static final String KEY_LAUNCH_WINDOWING_MODE = "android.activity.windowingMode";
  public static final String KEY_LEGACY_PERMISSION_PROMPT_ELIGIBLE =
      "android:activity.legacyPermissionPromptEligible";
  private static final String KEY_LOCK_TASK_MODE = "android:activity.lockTaskMode";
  private static final String KEY_ML_LAUNCH_HINT = "android:activity.isMlLaunch";
  private static final String KEY_NO_TRANSITION_OCCLUSION =
      "android.activity.noTransitionOcclusion";
  private static final String KEY_OVERRIDE_TASK_TRANSITION =
      "android:activity.overrideTaskTransition";
  public static final String KEY_PACKAGE_NAME = "android:activity.packageName";
  private static final String KEY_PENDING_INTENT_CREATOR_BACKGROUND_ACTIVITY_START_MODE =
      "android.activity.pendingIntentCreatorBackgroundActivityStartMode";
  private static final String KEY_PENDING_INTENT_LAUNCH_FLAGS =
      "android.activity.pendingIntentLaunchFlags";
  private static final String KEY_POP_OVER = "android:activity.popOver";
  private static final String KEY_POP_OVER_ANCHOR = "android:activity.popOverAnchor";
  private static final String KEY_POP_OVER_ANCHOR_POSITION =
      "android:activity.popOverAnchorPosition";
  private static final String KEY_POP_OVER_HEIGHT = "android:activity.popOverHeight";
  private static final String KEY_POP_OVER_INHERIT_OPTIONS =
      "android:activity.popOverInheritOptions";
  private static final String KEY_POP_OVER_WIDTH = "android:activity.popOverWidth";
  private static final String KEY_PRELAUNCH = "android.activity.prelaunch";
  private static final String KEY_PRESERVE_TASK_WINDOWING_MODE =
      "android.activity.preserveTaskWindowingMode";
  private static final String KEY_REMOTE_ANIMATION_ADAPTER =
      "android:activity.remoteAnimationAdapter";
  private static final String KEY_REMOTE_TRANSITION = "android:activity.remoteTransition";
  private static final String KEY_REMOVE_WITH_TASK_ORGANIZER =
      "android.activity.removeWithTaskOrganizer";
  private static final String KEY_RESULT_CODE = "android:activity.resultCode";
  private static final String KEY_RESULT_DATA = "android:activity.resultData";
  private static final String KEY_RESUMED_AFFORDANCE_ANIMATION_REQUESTED =
      "android:activity.resumedAffordanceAnimationRequested";
  private static final String KEY_ROTATION_ANIMATION_HINT =
      "android:activity.rotationAnimationHint";
  private static final String KEY_SHARE_IDENTITY = "android:activity.shareIdentity";
  private static final String KEY_SOURCE_INFO = "android.activity.sourceInfo";
  private static final String KEY_SPECS_FUTURE = "android:activity.specsFuture";
  private static final String KEY_SPLASH_SCREEN_STYLE = "android.activity.splashScreenStyle";
  public static final String KEY_SPLASH_SCREEN_THEME = "android.activity.splashScreenTheme";
  private static final String KEY_SPLIT_POSITION = "android.activity.splitPosition";
  public static final String KEY_SPLIT_TASK_DEFER_RESUME = "android.activity.splitTaskDeferResume";
  public static final String KEY_STARTED_BY_MDM_ADMIN = "edm:activity.startedByMDMAdmin";
  public static final String KEY_STARTED_FROM_WINDOW_TYPE_LAUNCHER =
      "android:activity.startedFromWindowTypeLauncher";
  private static final String KEY_TASK_ALWAYS_ON_TOP = "android.activity.alwaysOnTop";
  private static final String KEY_TASK_OVERLAY = "android.activity.taskOverlay";
  private static final String KEY_TASK_OVERLAY_CAN_RESUME = "android.activity.taskOverlayCanResume";
  public static final String KEY_TRANSIENT_LAUNCH = "android.activity.transientLaunch";
  private static final String KEY_TRANSITION_COMPLETE_LISTENER =
      "android:activity.transitionCompleteListener";
  private static final String KEY_TRANSITION_IS_RETURNING =
      "android:activity.transitionIsReturning";
  private static final String KEY_TRANSITION_SHARED_ELEMENTS =
      "android:activity.sharedElementNames";
  private static final String KEY_USAGE_TIME_REPORT = "android:activity.usageTimeReport";
  public static final int LAND = 0;
  public static final int ML_TYPE_EMPTY_PROCESS = 1;
  public static final int ML_TYPE_NAP_PROCESS = 0;
  public static final int ML_TYPE_SEEDBED_PROCESS = 2;
  public static final int MODE_BACKGROUND_ACTIVITY_START_ALLOWED = 1;
  public static final int MODE_BACKGROUND_ACTIVITY_START_DENIED = 2;
  public static final int MODE_BACKGROUND_ACTIVITY_START_SYSTEM_DEFINED = 0;
  public static final int POP_OVER_ANCHOR_HORIZONTAL_MASK = 112;
  public static final int POP_OVER_ANCHOR_VERTICAL_MASK = 7;
  private static final int POP_OVER_CHOOSER_BOTTOM_MARGIN_DP = 44;
  private static final int POP_OVER_CHOOSER_HEIGHT_DP = 360;
  private static final int POP_OVER_CHOOSER_WIDTH_DP = 360;
  public static final int POP_OVER_HORIZONTAL_MASK = 112;
  public static final int POP_OVER_VERTICAL_MASK = 7;
  public static final int PORT = 1;
  public static final int SEM_POP_OVER_ANCHOR_POSITION_HORIZONTAL_CENTER = 64;
  public static final int SEM_POP_OVER_ANCHOR_POSITION_HORIZONTAL_LEFT = 16;
  public static final int SEM_POP_OVER_ANCHOR_POSITION_HORIZONTAL_RIGHT = 32;
  public static final int SEM_POP_OVER_ANCHOR_POSITION_VERTICAL_BOTTOM = 2;
  public static final int SEM_POP_OVER_ANCHOR_POSITION_VERTICAL_CENTER = 4;
  public static final int SEM_POP_OVER_ANCHOR_POSITION_VERTICAL_TOP = 1;
  public static final int SEM_POP_OVER_POSITION_HORIZONTAL_CENTER = 64;
  public static final int SEM_POP_OVER_POSITION_HORIZONTAL_LEFT = 16;
  public static final int SEM_POP_OVER_POSITION_HORIZONTAL_RIGHT = 32;
  public static final int SEM_POP_OVER_POSITION_VERTICAL_BOTTOM = 2;
  public static final int SEM_POP_OVER_POSITION_VERTICAL_CENTER = 4;
  public static final int SEM_POP_OVER_POSITION_VERTICAL_TOP = 1;
  private static final String TAG = "ActivityOptions";
  private boolean mAllowEnterPipWhileLaunching;
  private AppTransitionAnimationSpec[] mAnimSpecs;
  private IRemoteCallback mAnimationFinishedListener;
  private IRemoteCallback mAnimationStartedListener;
  private int mAnimationType;
  private Bundle mAppVerificationBundle;
  private boolean mApplyActivityFlagsForBubbles;
  private boolean mApplyBigFreeformSize;
  private boolean mApplyMultipleTaskFlagForShortcut;
  private boolean mApplyNoUserActionFlagForShortcut;
  private boolean mAvoidMoveToFront;
  private int mCallerDisplayId;
  private int mCustomBackgroundColor;
  private int mCustomEnterResId;
  private int mCustomExitResId;
  private int mCustomInPlaceResId;
  private int mCustomizedCoverDensity;
  private boolean mDisableStartingWindow;
  private boolean mDisallowEnterPictureInPictureWhileLaunching;
  private boolean mDismissKeyguard;
  private boolean mDismissSplitBeforeLaunch;
  private int mEnterSplitSideWithAdjacentFlag;
  private int mExitCoordinatorIndex;
  private boolean mForceLaunchTaskOnHome;
  private int mForceLaunchWindowingMode;
  private boolean mFreezeRecentTasksReordering;
  private int mHeight;
  private boolean mIsActiveApplaunch;
  private boolean mIsActivityEmbeddedPlaceholder;
  private boolean mIsEligibleForLegacyPermissionPrompt;
  private int mIsMlLaunch;
  private boolean mIsPopOver;
  private boolean mIsPrelaunch;
  private boolean mIsReturning;
  private boolean mIsStartedFromWindowTypeLauncher;
  private boolean mLateTransientLaunch;
  private int mLaunchActivityType;
  private Rect mLaunchBounds;
  private IBinder mLaunchCookie;
  private int mLaunchDisplayId;
  private boolean mLaunchInFocusedStageRoot;
  private PictureInPictureParams mLaunchIntoPipParams;
  private WindowContainerToken mLaunchRootTask;
  private WindowContainerToken mLaunchTaskDisplayArea;
  private int mLaunchTaskDisplayAreaFeatureId;
  private IBinder mLaunchTaskFragmentToken;
  private int mLaunchTaskId;
  private int mLaunchWindowingMode;
  private boolean mLaunchedFromBubble;
  private boolean mLaunchedFromDnD;
  private boolean mLockTaskMode;
  private boolean mNoTransitionOcclusion;
  private boolean mOverrideTaskTransition;
  private String mPackageName;
  private int mPendingIntentCreatorBackgroundActivityStartMode;
  private int mPendingIntentLaunchFlags;
  public Point[] mPopOverAnchorMarginDp;
  public int[] mPopOverAnchorPosition;
  public int[] mPopOverHeightDp;
  public boolean mPopOverInheritOptions;
  public boolean mPopOverRemoveOutlineEffect;
  public int[] mPopOverWidthDp;
  private boolean mPreseveTaskWindowingMode;
  private RemoteAnimationAdapter mRemoteAnimationAdapter;
  private RemoteTransition mRemoteTransition;
  private boolean mRemoveWithTaskOrganizer;
  private int mResultCode;
  private Intent mResultData;
  private boolean mResumedAffordanceAnimationRequested;
  private int mRotationAnimationHint;
  private boolean mShareIdentity;
  private ArrayList<String> mSharedElementNames;
  private SourceInfo mSourceInfo;
  private IAppTransitionAnimationSpecsFuture mSpecsFuture;
  private int mSplashScreenStyle;
  private String mSplashScreenThemeResName;
  private int mSplitPosition;
  private boolean mSplitTaskDeferResume;
  private int mStartX;
  private int mStartY;
  private boolean mStartedByMDMAdmin;
  private boolean mTaskAlwaysOnTop;
  private boolean mTaskOverlay;
  private boolean mTaskOverlayCanResume;
  private Bitmap mThumbnail;
  private boolean mTransientLaunch;
  private ResultReceiver mTransitionReceiver;
  private PendingIntent mUsageTimeReport;
  private int mWidth;

  public interface OnAnimationFinishedListener {
    void onAnimationFinished(long j);
  }

  public interface OnAnimationStartedListener {
    void onAnimationStarted(long j);
  }

  public static ActivityOptions makeCustomAnimation(
      Context context, int enterResId, int exitResId) {
    return makeCustomAnimation(context, enterResId, exitResId, 0, null, null);
  }

  public static ActivityOptions makeCustomAnimation(
      Context context, int enterResId, int exitResId, int backgroundColor) {
    return makeCustomAnimation(context, enterResId, exitResId, backgroundColor, null, null);
  }

  public static ActivityOptions makeCustomAnimation(
      Context context,
      int enterResId,
      int exitResId,
      int backgroundColor,
      Handler handler,
      OnAnimationStartedListener listener) {
    ActivityOptions opts = new ActivityOptions();
    opts.mPackageName = context.getPackageName();
    opts.mAnimationType = 1;
    opts.mCustomEnterResId = enterResId;
    opts.mCustomExitResId = exitResId;
    opts.mCustomBackgroundColor = backgroundColor;
    opts.setOnAnimationStartedListener(handler, listener);
    return opts;
  }

  public static ActivityOptions makeCustomAnimation(
      Context context,
      int enterResId,
      int exitResId,
      int backgroundColor,
      Handler handler,
      OnAnimationStartedListener startedListener,
      OnAnimationFinishedListener finishedListener) {
    ActivityOptions opts =
        makeCustomAnimation(
            context, enterResId, exitResId, backgroundColor, handler, startedListener);
    opts.setOnAnimationFinishedListener(handler, finishedListener);
    return opts;
  }

  public static ActivityOptions makeCustomTaskAnimation(
      Context context,
      int enterResId,
      int exitResId,
      Handler handler,
      OnAnimationStartedListener startedListener,
      OnAnimationFinishedListener finishedListener) {
    ActivityOptions opts =
        makeCustomAnimation(
            context, enterResId, exitResId, 0, handler, startedListener, finishedListener);
    opts.mOverrideTaskTransition = true;
    return opts;
  }

  public static ActivityOptions makeCustomInPlaceAnimation(Context context, int animId) {
    if (animId == 0) {
      throw new RuntimeException("You must specify a valid animation.");
    }
    ActivityOptions opts = new ActivityOptions();
    opts.mPackageName = context.getPackageName();
    opts.mAnimationType = 10;
    opts.mCustomInPlaceResId = animId;
    return opts;
  }

  private void setOnAnimationStartedListener(
      final Handler handler, final OnAnimationStartedListener listener) {
    if (listener != null) {
      this.mAnimationStartedListener =
          new IRemoteCallback.Stub() { // from class: android.app.ActivityOptions.1
            @Override // android.p009os.IRemoteCallback
            public void sendResult(Bundle data) throws RemoteException {
              final long elapsedRealtime = SystemClock.elapsedRealtime();
              handler.post(
                  new Runnable() { // from class: android.app.ActivityOptions.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                      listener.onAnimationStarted(elapsedRealtime);
                    }
                  });
            }
          };
    }
  }

  private void setOnAnimationFinishedListener(
      final Handler handler, final OnAnimationFinishedListener listener) {
    if (listener != null) {
      this.mAnimationFinishedListener =
          new IRemoteCallback.Stub() { // from class: android.app.ActivityOptions.2
            @Override // android.p009os.IRemoteCallback
            public void sendResult(Bundle data) throws RemoteException {
              final long elapsedRealtime = SystemClock.elapsedRealtime();
              handler.post(
                  new Runnable() { // from class: android.app.ActivityOptions.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                      listener.onAnimationFinished(elapsedRealtime);
                    }
                  });
            }
          };
    }
  }

  public static ActivityOptions makeScaleUpAnimation(
      View source, int startX, int startY, int width, int height) {
    ActivityOptions opts = new ActivityOptions();
    opts.mPackageName = source.getContext().getPackageName();
    opts.mAnimationType = 2;
    int[] pts = new int[2];
    source.getLocationOnScreen(pts);
    opts.mStartX = pts[0] + startX;
    opts.mStartY = pts[1] + startY;
    opts.mWidth = width;
    opts.mHeight = height;
    return opts;
  }

  public static ActivityOptions makeClipRevealAnimation(
      View source, int startX, int startY, int width, int height) {
    ActivityOptions opts = new ActivityOptions();
    opts.mAnimationType = 11;
    int[] pts = new int[2];
    source.getLocationOnScreen(pts);
    opts.mStartX = pts[0] + startX;
    opts.mStartY = pts[1] + startY;
    opts.mWidth = width;
    opts.mHeight = height;
    return opts;
  }

  public static ActivityOptions makeOpenCrossProfileAppsAnimation() {
    ActivityOptions options = new ActivityOptions();
    options.mAnimationType = 12;
    return options;
  }

  public static ActivityOptions makeThumbnailScaleUpAnimation(
      View source, Bitmap thumbnail, int startX, int startY) {
    return makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY, null);
  }

  private static ActivityOptions makeThumbnailScaleUpAnimation(
      View source, Bitmap thumbnail, int startX, int startY, OnAnimationStartedListener listener) {
    return makeThumbnailAnimation(source, thumbnail, startX, startY, listener, true);
  }

  private static ActivityOptions makeThumbnailAnimation(
      View source,
      Bitmap thumbnail,
      int startX,
      int startY,
      OnAnimationStartedListener listener,
      boolean scaleUp) {
    ActivityOptions opts = new ActivityOptions();
    opts.mPackageName = source.getContext().getPackageName();
    opts.mAnimationType = scaleUp ? 3 : 4;
    opts.mThumbnail = thumbnail;
    int[] pts = new int[2];
    source.getLocationOnScreen(pts);
    opts.mStartX = pts[0] + startX;
    opts.mStartY = pts[1] + startY;
    opts.setOnAnimationStartedListener(source.getHandler(), listener);
    return opts;
  }

  public static ActivityOptions makeMultiThumbFutureAspectScaleAnimation(
      Context context,
      Handler handler,
      IAppTransitionAnimationSpecsFuture specsFuture,
      OnAnimationStartedListener listener,
      boolean scaleUp) {
    int i;
    ActivityOptions opts = new ActivityOptions();
    opts.mPackageName = context.getPackageName();
    if (scaleUp) {
      i = 8;
    } else {
      i = 9;
    }
    opts.mAnimationType = i;
    opts.mSpecsFuture = specsFuture;
    opts.setOnAnimationStartedListener(handler, listener);
    return opts;
  }

  public static ActivityOptions makeThumbnailAspectScaleDownAnimation(
      View source,
      Bitmap thumbnail,
      int startX,
      int startY,
      int targetWidth,
      int targetHeight,
      Handler handler,
      OnAnimationStartedListener listener) {
    return makeAspectScaledThumbnailAnimation(
        source, thumbnail, startX, startY, targetWidth, targetHeight, handler, listener, false);
  }

  private static ActivityOptions makeAspectScaledThumbnailAnimation(
      View source,
      Bitmap thumbnail,
      int startX,
      int startY,
      int targetWidth,
      int targetHeight,
      Handler handler,
      OnAnimationStartedListener listener,
      boolean scaleUp) {
    ActivityOptions opts = new ActivityOptions();
    opts.mPackageName = source.getContext().getPackageName();
    opts.mAnimationType = scaleUp ? 8 : 9;
    opts.mThumbnail = thumbnail;
    int[] pts = new int[2];
    source.getLocationOnScreen(pts);
    opts.mStartX = pts[0] + startX;
    opts.mStartY = pts[1] + startY;
    opts.mWidth = targetWidth;
    opts.mHeight = targetHeight;
    opts.setOnAnimationStartedListener(handler, listener);
    return opts;
  }

  public static ActivityOptions makeThumbnailAspectScaleDownAnimation(
      View source,
      AppTransitionAnimationSpec[] specs,
      Handler handler,
      OnAnimationStartedListener onAnimationStartedListener,
      OnAnimationFinishedListener onAnimationFinishedListener) {
    ActivityOptions opts = new ActivityOptions();
    opts.mPackageName = source.getContext().getPackageName();
    opts.mAnimationType = 9;
    opts.mAnimSpecs = specs;
    opts.setOnAnimationStartedListener(handler, onAnimationStartedListener);
    opts.setOnAnimationFinishedListener(handler, onAnimationFinishedListener);
    return opts;
  }

  public static ActivityOptions makeSceneTransitionAnimation(
      Activity activity, View sharedElement, String sharedElementName) {
    return makeSceneTransitionAnimation(activity, Pair.create(sharedElement, sharedElementName));
  }

  @SafeVarargs
  public static ActivityOptions makeSceneTransitionAnimation(
      Activity activity, Pair<View, String>... sharedElements) {
    ActivityOptions opts = new ActivityOptions();
    ExitTransitionCoordinator exit =
        makeSceneTransitionAnimation(
            new ExitTransitionCoordinator.ActivityExitTransitionCallbacks(activity),
            activity.mExitTransitionListener,
            activity.getWindow(),
            opts,
            sharedElements);
    opts.mExitCoordinatorIndex =
        activity.mActivityTransitionState.addExitTransitionCoordinator(exit);
    return opts;
  }

  @SafeVarargs
  public static Pair<ActivityOptions, ExitTransitionCoordinator> startSharedElementAnimation(
      Window window,
      ExitTransitionCoordinator.ExitTransitionCallbacks exitCallbacks,
      SharedElementCallback callback,
      Pair<View, String>... sharedElements) {
    ActivityOptions opts = new ActivityOptions();
    ExitTransitionCoordinator exit =
        makeSceneTransitionAnimation(exitCallbacks, callback, window, opts, sharedElements);
    opts.mExitCoordinatorIndex = -1;
    return Pair.create(opts, exit);
  }

  public static void stopSharedElementAnimation(Window window) {
    ExitTransitionCoordinator exit;
    View decorView = window.getDecorView();
    if (decorView != null
        && (exit = (ExitTransitionCoordinator) decorView.getTag(C4337R.id.cross_task_transition))
            != null) {
      exit.cancelPendingTransitions();
      decorView.setTagInternal(C4337R.id.cross_task_transition, null);
      TransitionManager.endTransitions((ViewGroup) decorView);
      exit.resetViews();
      exit.clearState();
      decorView.setVisibility(0);
    }
  }

  static ExitTransitionCoordinator makeSceneTransitionAnimation(
      ExitTransitionCoordinator.ExitTransitionCallbacks exitCallbacks,
      SharedElementCallback callback,
      Window window,
      ActivityOptions opts,
      Pair<View, String>[] sharedElements) {
    if (!window.hasFeature(13)) {
      opts.mAnimationType = 6;
      return null;
    }
    opts.mAnimationType = 5;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<View> views = new ArrayList<>();
    if (sharedElements != null) {
      for (Pair<View, String> sharedElement : sharedElements) {
        String sharedElementName = sharedElement.second;
        if (sharedElementName == null) {
          throw new IllegalArgumentException("Shared element name must not be null");
        }
        names.add(sharedElementName);
        View view = sharedElement.first;
        if (view == null) {
          throw new IllegalArgumentException("Shared element must not be null");
        }
        views.add(sharedElement.first);
      }
    }
    ExitTransitionCoordinator exit =
        new ExitTransitionCoordinator(exitCallbacks, window, callback, names, names, views, false);
    opts.mTransitionReceiver = exit;
    opts.mSharedElementNames = names;
    opts.mIsReturning = false;
    return exit;
  }

  public static void setExitTransitionTimeout(long timeoutMillis) {
    ExitTransitionCoordinator.sMaxWaitMillis = timeoutMillis;
  }

  static ActivityOptions makeSceneTransitionAnimation(
      Activity activity,
      ExitTransitionCoordinator exitCoordinator,
      ArrayList<String> sharedElementNames,
      int resultCode,
      Intent resultData) {
    ActivityOptions opts = new ActivityOptions();
    opts.mAnimationType = 5;
    opts.mSharedElementNames = sharedElementNames;
    opts.mTransitionReceiver = exitCoordinator;
    opts.mIsReturning = true;
    opts.mResultCode = resultCode;
    opts.mResultData = resultData;
    if (activity == null) {
      opts.mExitCoordinatorIndex = -1;
    } else {
      opts.mExitCoordinatorIndex =
          activity.mActivityTransitionState.addExitTransitionCoordinator(exitCoordinator);
    }
    Slog.m113d(
        TAG,
        "makeSceneTransitionAnimation is called, activity="
            + activity
            + ", caller="
            + Debug.getCallers(3));
    return opts;
  }

  public static ActivityOptions makeTaskLaunchBehind() {
    ActivityOptions opts = new ActivityOptions();
    opts.mAnimationType = 7;
    return opts;
  }

  public static ActivityOptions makeBasic() {
    ActivityOptions opts = new ActivityOptions();
    return opts;
  }

  public static ActivityOptions makeRemoteAnimation(RemoteAnimationAdapter remoteAnimationAdapter) {
    ActivityOptions opts = new ActivityOptions();
    opts.mRemoteAnimationAdapter = remoteAnimationAdapter;
    opts.mAnimationType = 13;
    Slog.m113d(
        TAG,
        "makeRemoteAnimation, adapter="
            + remoteAnimationAdapter
            + ", caller="
            + Debug.getCallers(3));
    return opts;
  }

  public static ActivityOptions makeRemoteAnimation(
      RemoteAnimationAdapter remoteAnimationAdapter, RemoteTransition remoteTransition) {
    ActivityOptions opts = new ActivityOptions();
    opts.mRemoteAnimationAdapter = remoteAnimationAdapter;
    opts.mAnimationType = 13;
    opts.mRemoteTransition = remoteTransition;
    Slog.m113d(
        TAG,
        "makeRemoteAnimation, adapter="
            + remoteAnimationAdapter
            + ", remoteTransition="
            + remoteTransition
            + ", caller="
            + Debug.getCallers(3));
    return opts;
  }

  public static ActivityOptions makeRemoteTransition(RemoteTransition remoteTransition) {
    ActivityOptions opts = new ActivityOptions();
    opts.mRemoteTransition = remoteTransition;
    Slog.m113d(
        TAG,
        "makeRemoteTransition, remoteTransition="
            + remoteTransition
            + ", caller="
            + Debug.getCallers(3));
    return opts;
  }

  public static ActivityOptions makeLaunchIntoPip(PictureInPictureParams pictureInPictureParams) {
    ActivityOptions opts = new ActivityOptions();
    opts.mLaunchIntoPipParams =
        new PictureInPictureParams.Builder(pictureInPictureParams).setIsLaunchIntoPip(true).build();
    return opts;
  }

  public boolean getLaunchTaskBehind() {
    return this.mAnimationType == 7;
  }

  private ActivityOptions() {
    this.mAnimationType = -1;
    this.mIsMlLaunch = -1;
    this.mLaunchDisplayId = -1;
    this.mCallerDisplayId = -1;
    this.mLaunchTaskDisplayAreaFeatureId = -1;
    this.mLaunchWindowingMode = 0;
    this.mLaunchActivityType = 0;
    this.mLaunchTaskId = -1;
    this.mLockTaskMode = false;
    this.mShareIdentity = false;
    this.mRotationAnimationHint = -1;
    this.mSplashScreenStyle = -1;
    this.mPendingIntentCreatorBackgroundActivityStartMode = 0;
    this.mForceLaunchWindowingMode = 0;
    this.mSplitPosition = 0;
    this.mLaunchInFocusedStageRoot = false;
    this.mIsActivityEmbeddedPlaceholder = false;
    this.mLaunchedFromDnD = false;
    this.mAllowEnterPipWhileLaunching = false;
    this.mForceLaunchTaskOnHome = false;
    this.mSplitTaskDeferResume = false;
    this.mIsPrelaunch = false;
    this.mNoTransitionOcclusion = false;
    this.mPopOverWidthDp = new int[2];
    this.mPopOverHeightDp = new int[2];
    this.mPopOverAnchorMarginDp = new Point[2];
    this.mPopOverAnchorPosition = new int[2];
    this.mPopOverInheritOptions = true;
    this.mCustomizedCoverDensity = 0;
  }

  public ActivityOptions(Bundle opts) {
    super(opts);
    this.mAnimationType = -1;
    this.mIsMlLaunch = -1;
    this.mLaunchDisplayId = -1;
    this.mCallerDisplayId = -1;
    this.mLaunchTaskDisplayAreaFeatureId = -1;
    this.mLaunchWindowingMode = 0;
    this.mLaunchActivityType = 0;
    this.mLaunchTaskId = -1;
    this.mLockTaskMode = false;
    this.mShareIdentity = false;
    this.mRotationAnimationHint = -1;
    this.mSplashScreenStyle = -1;
    this.mPendingIntentCreatorBackgroundActivityStartMode = 0;
    this.mForceLaunchWindowingMode = 0;
    this.mSplitPosition = 0;
    this.mLaunchInFocusedStageRoot = false;
    this.mIsActivityEmbeddedPlaceholder = false;
    this.mLaunchedFromDnD = false;
    this.mAllowEnterPipWhileLaunching = false;
    this.mForceLaunchTaskOnHome = false;
    this.mSplitTaskDeferResume = false;
    this.mIsPrelaunch = false;
    this.mNoTransitionOcclusion = false;
    this.mPopOverWidthDp = new int[2];
    this.mPopOverHeightDp = new int[2];
    this.mPopOverAnchorMarginDp = new Point[2];
    this.mPopOverAnchorPosition = new int[2];
    this.mPopOverInheritOptions = true;
    this.mCustomizedCoverDensity = 0;
    this.mPackageName = opts.getString(KEY_PACKAGE_NAME);
    try {
      this.mUsageTimeReport =
          (PendingIntent) opts.getParcelable(KEY_USAGE_TIME_REPORT, PendingIntent.class);
    } catch (RuntimeException e) {
      Slog.m123w(TAG, e);
    }
    this.mLaunchBounds = (Rect) opts.getParcelable(KEY_LAUNCH_BOUNDS, Rect.class);
    int i = opts.getInt(KEY_ANIM_TYPE, -1);
    this.mAnimationType = i;
    switch (i) {
      case 1:
        this.mCustomEnterResId = opts.getInt(KEY_ANIM_ENTER_RES_ID, 0);
        this.mCustomExitResId = opts.getInt(KEY_ANIM_EXIT_RES_ID, 0);
        this.mCustomBackgroundColor = opts.getInt(KEY_ANIM_BACKGROUND_COLOR, 0);
        this.mAnimationStartedListener =
            IRemoteCallback.Stub.asInterface(opts.getBinder(KEY_ANIM_START_LISTENER));
        break;
      case 2:
      case 11:
        this.mStartX = opts.getInt(KEY_ANIM_START_X, 0);
        this.mStartY = opts.getInt(KEY_ANIM_START_Y, 0);
        this.mWidth = opts.getInt(KEY_ANIM_WIDTH, 0);
        this.mHeight = opts.getInt(KEY_ANIM_HEIGHT, 0);
        break;
      case 3:
      case 4:
      case 8:
      case 9:
        HardwareBuffer buffer =
            (HardwareBuffer) opts.getParcelable(KEY_ANIM_THUMBNAIL, HardwareBuffer.class);
        if (buffer != null) {
          this.mThumbnail = Bitmap.wrapHardwareBuffer(buffer, null);
        }
        this.mStartX = opts.getInt(KEY_ANIM_START_X, 0);
        this.mStartY = opts.getInt(KEY_ANIM_START_Y, 0);
        this.mWidth = opts.getInt(KEY_ANIM_WIDTH, 0);
        this.mHeight = opts.getInt(KEY_ANIM_HEIGHT, 0);
        this.mAnimationStartedListener =
            IRemoteCallback.Stub.asInterface(opts.getBinder(KEY_ANIM_START_LISTENER));
        break;
      case 5:
        this.mTransitionReceiver =
            (ResultReceiver)
                opts.getParcelable(KEY_TRANSITION_COMPLETE_LISTENER, ResultReceiver.class);
        this.mIsReturning = opts.getBoolean(KEY_TRANSITION_IS_RETURNING, false);
        this.mSharedElementNames = opts.getStringArrayList(KEY_TRANSITION_SHARED_ELEMENTS);
        this.mResultData = (Intent) opts.getParcelable(KEY_RESULT_DATA, Intent.class);
        this.mResultCode = opts.getInt(KEY_RESULT_CODE);
        this.mExitCoordinatorIndex = opts.getInt(KEY_EXIT_COORDINATOR_INDEX);
        break;
      case 10:
        this.mCustomInPlaceResId = opts.getInt(KEY_ANIM_IN_PLACE_RES_ID, 0);
        break;
    }
    this.mLockTaskMode = opts.getBoolean(KEY_LOCK_TASK_MODE, false);
    this.mShareIdentity = opts.getBoolean(KEY_SHARE_IDENTITY, false);
    this.mLaunchDisplayId = opts.getInt(KEY_LAUNCH_DISPLAY_ID, -1);
    this.mCallerDisplayId = opts.getInt(KEY_CALLER_DISPLAY_ID, -1);
    this.mLaunchTaskDisplayArea =
        (WindowContainerToken)
            opts.getParcelable(KEY_LAUNCH_TASK_DISPLAY_AREA_TOKEN, WindowContainerToken.class);
    this.mLaunchTaskDisplayAreaFeatureId = opts.getInt(KEY_LAUNCH_TASK_DISPLAY_AREA_FEATURE_ID, -1);
    this.mLaunchRootTask =
        (WindowContainerToken)
            opts.getParcelable(KEY_LAUNCH_ROOT_TASK_TOKEN, WindowContainerToken.class);
    this.mLaunchTaskFragmentToken = opts.getBinder(KEY_LAUNCH_TASK_FRAGMENT_TOKEN);
    this.mLaunchWindowingMode = opts.getInt(KEY_LAUNCH_WINDOWING_MODE, 0);
    this.mLaunchActivityType = opts.getInt(KEY_LAUNCH_ACTIVITY_TYPE, 0);
    this.mLaunchTaskId = opts.getInt(KEY_LAUNCH_TASK_ID, -1);
    this.mPendingIntentLaunchFlags = opts.getInt(KEY_PENDING_INTENT_LAUNCH_FLAGS, 0);
    this.mTaskAlwaysOnTop = opts.getBoolean(KEY_TASK_ALWAYS_ON_TOP, false);
    this.mTaskOverlay = opts.getBoolean(KEY_TASK_OVERLAY, false);
    this.mTaskOverlayCanResume = opts.getBoolean(KEY_TASK_OVERLAY_CAN_RESUME, false);
    this.mAvoidMoveToFront = opts.getBoolean(KEY_AVOID_MOVE_TO_FRONT, false);
    this.mFreezeRecentTasksReordering = opts.getBoolean(KEY_FREEZE_RECENT_TASKS_REORDERING, false);
    this.mDisallowEnterPictureInPictureWhileLaunching =
        opts.getBoolean(KEY_DISALLOW_ENTER_PICTURE_IN_PICTURE_WHILE_LAUNCHING, false);
    this.mApplyActivityFlagsForBubbles =
        opts.getBoolean(KEY_APPLY_ACTIVITY_FLAGS_FOR_BUBBLES, false);
    this.mApplyMultipleTaskFlagForShortcut =
        opts.getBoolean(KEY_APPLY_MULTIPLE_TASK_FLAG_FOR_SHORTCUT, false);
    this.mApplyNoUserActionFlagForShortcut =
        opts.getBoolean(KEY_APPLY_NO_USER_ACTION_FLAG_FOR_SHORTCUT, false);
    if (opts.containsKey(KEY_ANIM_SPECS)) {
      Parcelable[] specs = opts.getParcelableArray(KEY_ANIM_SPECS);
      this.mAnimSpecs = new AppTransitionAnimationSpec[specs.length];
      for (int i2 = specs.length - 1; i2 >= 0; i2--) {
        this.mAnimSpecs[i2] = (AppTransitionAnimationSpec) specs[i2];
      }
    }
    if (opts.containsKey(KEY_ANIMATION_FINISHED_LISTENER)) {
      this.mAnimationFinishedListener =
          IRemoteCallback.Stub.asInterface(opts.getBinder(KEY_ANIMATION_FINISHED_LISTENER));
    }
    this.mSourceInfo = (SourceInfo) opts.getParcelable(KEY_SOURCE_INFO, SourceInfo.class);
    this.mRotationAnimationHint = opts.getInt(KEY_ROTATION_ANIMATION_HINT, -1);
    this.mAppVerificationBundle = opts.getBundle(KEY_INSTANT_APP_VERIFICATION_BUNDLE);
    if (opts.containsKey(KEY_SPECS_FUTURE)) {
      this.mSpecsFuture =
          IAppTransitionAnimationSpecsFuture.Stub.asInterface(opts.getBinder(KEY_SPECS_FUTURE));
    }
    this.mIsActiveApplaunch = opts.getBoolean(KEY_ACTIVE_LAUNCH_HINT, false);
    this.mIsMlLaunch = opts.getInt(KEY_ML_LAUNCH_HINT, -1);
    this.mStartedByMDMAdmin = opts.getBoolean(KEY_STARTED_BY_MDM_ADMIN, false);
    this.mRemoteAnimationAdapter =
        (RemoteAnimationAdapter)
            opts.getParcelable(KEY_REMOTE_ANIMATION_ADAPTER, RemoteAnimationAdapter.class);
    this.mLaunchCookie = opts.getBinder(KEY_LAUNCH_COOKIE);
    this.mRemoteTransition =
        (RemoteTransition) opts.getParcelable(KEY_REMOTE_TRANSITION, RemoteTransition.class);
    this.mOverrideTaskTransition = opts.getBoolean(KEY_OVERRIDE_TASK_TRANSITION);
    this.mSplashScreenThemeResName = opts.getString(KEY_SPLASH_SCREEN_THEME);
    this.mRemoveWithTaskOrganizer = opts.getBoolean(KEY_REMOVE_WITH_TASK_ORGANIZER);
    this.mLaunchedFromBubble = opts.getBoolean(KEY_LAUNCHED_FROM_BUBBLE);
    this.mTransientLaunch = opts.getBoolean(KEY_TRANSIENT_LAUNCH);
    this.mSplashScreenStyle = opts.getInt(KEY_SPLASH_SCREEN_STYLE);
    this.mLaunchIntoPipParams =
        (PictureInPictureParams)
            opts.getParcelable(KEY_LAUNCH_INTO_PIP_PARAMS, PictureInPictureParams.class);
    this.mIsEligibleForLegacyPermissionPrompt =
        opts.getBoolean(KEY_LEGACY_PERMISSION_PROMPT_ELIGIBLE);
    this.mDismissKeyguard = opts.getBoolean(KEY_DISMISS_KEYGUARD);
    this.mPendingIntentCreatorBackgroundActivityStartMode =
        opts.getInt(KEY_PENDING_INTENT_CREATOR_BACKGROUND_ACTIVITY_START_MODE, 0);
    this.mDisableStartingWindow = opts.getBoolean(KEY_DISABLE_STARTING_WINDOW);
    if (opts.containsKey(KEY_POP_OVER_WIDTH)
        && opts.containsKey(KEY_POP_OVER_HEIGHT)
        && opts.containsKey(KEY_POP_OVER_ANCHOR)
        && opts.containsKey(KEY_POP_OVER_ANCHOR_POSITION)) {
      this.mPopOverWidthDp = opts.getIntArray(KEY_POP_OVER_WIDTH);
      this.mPopOverHeightDp = opts.getIntArray(KEY_POP_OVER_HEIGHT);
      Parcelable[] parcelables = opts.getParcelableArray(KEY_POP_OVER_ANCHOR);
      for (int i3 = 0; i3 < parcelables.length; i3++) {
        this.mPopOverAnchorMarginDp[i3] = (Point) parcelables[i3];
      }
      this.mPopOverAnchorPosition = opts.getIntArray(KEY_POP_OVER_ANCHOR_POSITION);
      this.mIsPopOver = opts.getBoolean(KEY_POP_OVER);
    } else if (opts.containsKey(KEY_POP_OVER_INHERIT_OPTIONS)) {
      this.mPopOverInheritOptions = opts.getBoolean(KEY_POP_OVER_INHERIT_OPTIONS);
    }
    if (opts.containsKey(KEY_RESUMED_AFFORDANCE_ANIMATION_REQUESTED)) {
      this.mResumedAffordanceAnimationRequested =
          opts.getBoolean(KEY_RESUMED_AFFORDANCE_ANIMATION_REQUESTED);
    }
    if (CoreRune.FW_CHN_PREMIUM_WATCH && opts.containsKey(KEY_NO_TRANSITION_OCCLUSION)) {
      this.mNoTransitionOcclusion = opts.getBoolean(KEY_NO_TRANSITION_OCCLUSION);
    }
    if (opts.containsKey(KEY_FORCE_LAUNCH_WINDOWING_MODE)) {
      this.mForceLaunchWindowingMode = opts.getInt(KEY_FORCE_LAUNCH_WINDOWING_MODE, 0);
    }
    if (opts.containsKey(KEY_SPLIT_POSITION)) {
      this.mSplitPosition = opts.getInt(KEY_SPLIT_POSITION);
    }
    if (opts.containsKey(KEY_LAUNCH_IN_FOCUSED_STAGE_ROOT)) {
      this.mLaunchInFocusedStageRoot = opts.getBoolean(KEY_LAUNCH_IN_FOCUSED_STAGE_ROOT);
    }
    this.mDismissSplitBeforeLaunch = opts.getBoolean(KEY_DISMISS_SPLIT_BEFORE_LAUNCH, false);
    if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW
        && opts.containsKey(KEY_PRESERVE_TASK_WINDOWING_MODE)) {
      this.mPreseveTaskWindowingMode = opts.getBoolean(KEY_PRESERVE_TASK_WINDOWING_MODE, false);
    }
    if (opts.containsKey(KEY_APPLY_BIG_FREEFORM_SIZE)) {
      this.mApplyBigFreeformSize = opts.getBoolean(KEY_APPLY_BIG_FREEFORM_SIZE, true);
    }
    if (CoreRune.MW_EMBED_ACTIVITY) {
      this.mIsActivityEmbeddedPlaceholder =
          opts.getBoolean(KEY_ACTIVITY_EMBEDDED_PLACEHOLDER, false);
    }
    if (opts.containsKey(KEY_LAUNCHED_FROM_DND)) {
      this.mLaunchedFromDnD = opts.getBoolean(KEY_LAUNCHED_FROM_DND);
    }
    this.mEnterSplitSideWithAdjacentFlag = opts.getInt(KEY_ENTER_SPLIT_SIDE_WITH_ADJACENT_FLAG, 0);
    if (opts.containsKey(KEY_STARTED_FROM_WINDOW_TYPE_LAUNCHER)) {
      this.mIsStartedFromWindowTypeLauncher =
          opts.getBoolean(KEY_STARTED_FROM_WINDOW_TYPE_LAUNCHER);
    }
    if (opts.containsKey(KEY_ALLOW_ENTER_PIP_WHILE_LAUNCHING)) {
      this.mAllowEnterPipWhileLaunching =
          opts.getBoolean(KEY_ALLOW_ENTER_PIP_WHILE_LAUNCHING, false);
    }
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LATE_TRANSIENT_LAUNCH) {
      this.mLateTransientLaunch = opts.getBoolean(KEY_LATE_TRANSIENT_LAUNCH);
    }
    if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && opts.containsKey(KEY_FORCE_LAUNCH_TASK_ON_HOME)) {
      this.mForceLaunchTaskOnHome = opts.getBoolean(KEY_FORCE_LAUNCH_TASK_ON_HOME, false);
    }
    if (opts.containsKey(KEY_SPLIT_TASK_DEFER_RESUME)) {
      this.mSplitTaskDeferResume = opts.getBoolean(KEY_SPLIT_TASK_DEFER_RESUME);
    }
    if (CoreRune.SYSFW_APP_PREL) {
      this.mIsPrelaunch = opts.getBoolean(KEY_PRELAUNCH, false);
    }
  }

  public ActivityOptions setLaunchBounds(Rect screenSpacePixelRect) {
    this.mLaunchBounds = screenSpacePixelRect != null ? new Rect(screenSpacePixelRect) : null;
    return this;
  }

  public String getPackageName() {
    return this.mPackageName;
  }

  public Rect getLaunchBounds() {
    return this.mLaunchBounds;
  }

  public int getAnimationType() {
    return this.mAnimationType;
  }

  public int getCustomEnterResId() {
    return this.mCustomEnterResId;
  }

  public int getCustomExitResId() {
    return this.mCustomExitResId;
  }

  public int getCustomInPlaceResId() {
    return this.mCustomInPlaceResId;
  }

  public int getCustomBackgroundColor() {
    return this.mCustomBackgroundColor;
  }

  public HardwareBuffer getThumbnail() {
    Bitmap bitmap = this.mThumbnail;
    if (bitmap != null) {
      return bitmap.getHardwareBuffer();
    }
    return null;
  }

  public int getStartX() {
    return this.mStartX;
  }

  public int getStartY() {
    return this.mStartY;
  }

  public int getWidth() {
    return this.mWidth;
  }

  public int getHeight() {
    return this.mHeight;
  }

  public IRemoteCallback getAnimationStartedListener() {
    return this.mAnimationStartedListener;
  }

  public IRemoteCallback getAnimationFinishedListener() {
    return this.mAnimationFinishedListener;
  }

  public int getExitCoordinatorKey() {
    return this.mExitCoordinatorIndex;
  }

  public void abort() {
    IRemoteCallback iRemoteCallback = this.mAnimationStartedListener;
    if (iRemoteCallback != null) {
      try {
        iRemoteCallback.sendResult(null);
      } catch (RemoteException e) {
      }
    }
  }

  public boolean isReturning() {
    return this.mIsReturning;
  }

  public boolean isActiveApplaunch() {
    return this.mIsActiveApplaunch;
  }

  public void setActiveApplaunch(boolean activeApplaunch) {
    this.mIsActiveApplaunch = activeApplaunch;
  }

  public int isMlLaunch() {
    return this.mIsMlLaunch;
  }

  public void setMlLaunch(int mlLaunch) {
    this.mIsMlLaunch = mlLaunch;
  }

  boolean isCrossTask() {
    return this.mExitCoordinatorIndex < 0;
  }

  public ArrayList<String> getSharedElementNames() {
    return this.mSharedElementNames;
  }

  public ResultReceiver getResultReceiver() {
    return this.mTransitionReceiver;
  }

  public int getResultCode() {
    return this.mResultCode;
  }

  public Intent getResultData() {
    return this.mResultData;
  }

  public PendingIntent getUsageTimeReport() {
    return this.mUsageTimeReport;
  }

  public AppTransitionAnimationSpec[] getAnimSpecs() {
    return this.mAnimSpecs;
  }

  public IAppTransitionAnimationSpecsFuture getSpecsFuture() {
    return this.mSpecsFuture;
  }

  public RemoteAnimationAdapter getRemoteAnimationAdapter() {
    return this.mRemoteAnimationAdapter;
  }

  public void setRemoteAnimationAdapter(RemoteAnimationAdapter remoteAnimationAdapter) {
    this.mRemoteAnimationAdapter = remoteAnimationAdapter;
  }

  public RemoteTransition getRemoteTransition() {
    return this.mRemoteTransition;
  }

  public void setRemoteTransition(RemoteTransition remoteTransition) {
    this.mRemoteTransition = remoteTransition;
  }

  public static ActivityOptions fromBundle(Bundle bOptions) {
    if (bOptions != null) {
      return new ActivityOptions(bOptions);
    }
    return null;
  }

  public static void abort(ActivityOptions options) {
    if (options != null) {
      options.abort();
    }
  }

  public boolean getLockTaskMode() {
    return this.mLockTaskMode;
  }

  public boolean isShareIdentityEnabled() {
    return this.mShareIdentity;
  }

  public String getSplashScreenThemeResName() {
    return this.mSplashScreenThemeResName;
  }

  public int getSplashScreenStyle() {
    return this.mSplashScreenStyle;
  }

  public ActivityOptions setSplashScreenStyle(int style) {
    if (style == 1 || style == 0) {
      this.mSplashScreenStyle = style;
    }
    return this;
  }

  public boolean isEligibleForLegacyPermissionPrompt() {
    return this.mIsEligibleForLegacyPermissionPrompt;
  }

  public void setEligibleForLegacyPermissionPrompt(boolean eligible) {
    this.mIsEligibleForLegacyPermissionPrompt = eligible;
  }

  public ActivityOptions setLockTaskEnabled(boolean lockTaskMode) {
    this.mLockTaskMode = lockTaskMode;
    return this;
  }

  public ActivityOptions setShareIdentityEnabled(boolean shareIdentity) {
    this.mShareIdentity = shareIdentity;
    return this;
  }

  public int getLaunchDisplayId() {
    return this.mLaunchDisplayId;
  }

  public ActivityOptions setLaunchDisplayId(int launchDisplayId) {
    this.mLaunchDisplayId = launchDisplayId;
    return this;
  }

  public int getCallerDisplayId() {
    return this.mCallerDisplayId;
  }

  public ActivityOptions setCallerDisplayId(int callerDisplayId) {
    this.mCallerDisplayId = callerDisplayId;
    return this;
  }

  public WindowContainerToken getLaunchTaskDisplayArea() {
    return this.mLaunchTaskDisplayArea;
  }

  public ActivityOptions setLaunchTaskDisplayArea(WindowContainerToken windowContainerToken) {
    this.mLaunchTaskDisplayArea = windowContainerToken;
    return this;
  }

  public int getLaunchTaskDisplayAreaFeatureId() {
    return this.mLaunchTaskDisplayAreaFeatureId;
  }

  public void setLaunchTaskDisplayAreaFeatureId(int launchTaskDisplayAreaFeatureId) {
    this.mLaunchTaskDisplayAreaFeatureId = launchTaskDisplayAreaFeatureId;
  }

  public WindowContainerToken getLaunchRootTask() {
    return this.mLaunchRootTask;
  }

  public ActivityOptions setLaunchRootTask(WindowContainerToken windowContainerToken) {
    this.mLaunchRootTask = windowContainerToken;
    return this;
  }

  public IBinder getLaunchTaskFragmentToken() {
    return this.mLaunchTaskFragmentToken;
  }

  public ActivityOptions setLaunchTaskFragmentToken(IBinder taskFragmentToken) {
    this.mLaunchTaskFragmentToken = taskFragmentToken;
    return this;
  }

  public int getLaunchWindowingMode() {
    return this.mLaunchWindowingMode;
  }

  public void setLaunchWindowingMode(int windowingMode) {
    this.mLaunchWindowingMode = windowingMode;
  }

  public void preserveTaskWindowingMode() {
    this.mPreseveTaskWindowingMode = true;
  }

  public boolean shouldPreserveTaskWindowingMode() {
    return this.mPreseveTaskWindowingMode;
  }

  public int getForceLaunchWindowingMode() {
    return this.mForceLaunchWindowingMode;
  }

  public void setForceLaunchWindowingMode(int windowingMode) {
    this.mForceLaunchWindowingMode = windowingMode;
  }

  public PictureInPictureParams getLaunchIntoPipParams() {
    return this.mLaunchIntoPipParams;
  }

  public boolean isLaunchIntoPip() {
    PictureInPictureParams pictureInPictureParams = this.mLaunchIntoPipParams;
    return pictureInPictureParams != null && pictureInPictureParams.isLaunchIntoPip();
  }

  public int getLaunchActivityType() {
    return this.mLaunchActivityType;
  }

  public void setLaunchActivityType(int activityType) {
    this.mLaunchActivityType = activityType;
  }

  @SystemApi
  public void setLaunchTaskId(int taskId) {
    this.mLaunchTaskId = taskId;
  }

  @SystemApi
  public int getLaunchTaskId() {
    return this.mLaunchTaskId;
  }

  public void setDisableStartingWindow(boolean disable) {
    this.mDisableStartingWindow = disable;
  }

  public boolean getDisableStartingWindow() {
    return this.mDisableStartingWindow;
  }

  public void setPendingIntentLaunchFlags(int flags) {
    this.mPendingIntentLaunchFlags = flags;
  }

  public int getPendingIntentLaunchFlags() {
    return this.mPendingIntentLaunchFlags & 268435456;
  }

  public void setTaskAlwaysOnTop(boolean alwaysOnTop) {
    this.mTaskAlwaysOnTop = alwaysOnTop;
  }

  public boolean getTaskAlwaysOnTop() {
    return this.mTaskAlwaysOnTop;
  }

  public void setTaskOverlay(boolean taskOverlay, boolean canResume) {
    this.mTaskOverlay = taskOverlay;
    this.mTaskOverlayCanResume = canResume;
  }

  public boolean getTaskOverlay() {
    return this.mTaskOverlay;
  }

  public boolean canTaskOverlayResume() {
    return this.mTaskOverlayCanResume;
  }

  public void setAvoidMoveToFront() {
    this.mAvoidMoveToFront = true;
    Slog.m113d(
        TAG,
        "setAvoidMoveToFront is called, package="
            + ActivityThread.currentPackageName()
            + ", caller="
            + Debug.getCallers(5));
  }

  public boolean getAvoidMoveToFront() {
    return this.mAvoidMoveToFront;
  }

  public void setFreezeRecentTasksReordering() {
    this.mFreezeRecentTasksReordering = true;
  }

  public boolean freezeRecentTasksReordering() {
    return this.mFreezeRecentTasksReordering;
  }

  public void setSplitScreenCreateMode(int splitScreenCreateMode) {}

  public void setDisallowEnterPictureInPictureWhileLaunching(boolean disallow) {
    this.mDisallowEnterPictureInPictureWhileLaunching = disallow;
  }

  public boolean getStartedByMDMAdmin() {
    return this.mStartedByMDMAdmin;
  }

  public boolean disallowEnterPictureInPictureWhileLaunching() {
    return this.mDisallowEnterPictureInPictureWhileLaunching;
  }

  public void setApplyActivityFlagsForBubbles(boolean apply) {
    this.mApplyActivityFlagsForBubbles = apply;
  }

  public boolean isApplyActivityFlagsForBubbles() {
    return this.mApplyActivityFlagsForBubbles;
  }

  public void setApplyMultipleTaskFlagForShortcut(boolean apply) {
    this.mApplyMultipleTaskFlagForShortcut = apply;
  }

  public boolean isApplyMultipleTaskFlagForShortcut() {
    return this.mApplyMultipleTaskFlagForShortcut;
  }

  public void setApplyNoUserActionFlagForShortcut(boolean apply) {
    this.mApplyNoUserActionFlagForShortcut = apply;
  }

  public boolean isApplyNoUserActionFlagForShortcut() {
    return this.mApplyNoUserActionFlagForShortcut;
  }

  public void setLaunchCookie(IBinder launchCookie) {
    this.mLaunchCookie = launchCookie;
  }

  public IBinder getLaunchCookie() {
    return this.mLaunchCookie;
  }

  public boolean getOverrideTaskTransition() {
    return this.mOverrideTaskTransition;
  }

  public void setRemoveWithTaskOrganizer(boolean remove) {
    this.mRemoveWithTaskOrganizer = remove;
  }

  public boolean getRemoveWithTaskOranizer() {
    return this.mRemoveWithTaskOrganizer;
  }

  public void setLaunchedFromBubble(boolean fromBubble) {
    this.mLaunchedFromBubble = fromBubble;
  }

  public boolean getLaunchedFromBubble() {
    return this.mLaunchedFromBubble;
  }

  public ActivityOptions setTransientLaunch() {
    this.mTransientLaunch = true;
    return this;
  }

  public boolean getTransientLaunch() {
    return this.mTransientLaunch;
  }

  public void setDismissKeyguard() {
    this.mDismissKeyguard = true;
  }

  public boolean getDismissKeyguard() {
    return this.mDismissKeyguard;
  }

  @Deprecated
  public ActivityOptions setIgnorePendingIntentCreatorForegroundState(boolean ignore) {
    this.mPendingIntentCreatorBackgroundActivityStartMode = ignore ? 2 : 1;
    return this;
  }

  public ActivityOptions setPendingIntentCreatorBackgroundActivityStartMode(int mode) {
    this.mPendingIntentCreatorBackgroundActivityStartMode = mode;
    return this;
  }

  public int getPendingIntentCreatorBackgroundActivityStartMode() {
    return this.mPendingIntentCreatorBackgroundActivityStartMode;
  }

  public int getSplitPosition() {
    return this.mSplitPosition;
  }

  public void setSplitPosition(int splitPosition) {
    this.mSplitPosition = splitPosition;
  }

  public boolean getLaunchInFocusedStageRoot() {
    return this.mLaunchInFocusedStageRoot;
  }

  public void setLaunchInFocusedStageRoot(boolean launchInFocusedStageRoot) {
    this.mLaunchInFocusedStageRoot = launchInFocusedStageRoot;
  }

  public boolean getLaunchedFromDnD() {
    return this.mLaunchedFromDnD;
  }

  public void setLaunchedFromDnD(boolean launchedFromDnd) {
    this.mLaunchedFromDnD = launchedFromDnd;
  }

  public boolean getSplitTaskDeferResume() {
    return this.mSplitTaskDeferResume;
  }

  public void update(ActivityOptions otherOptions) {
    String str = otherOptions.mPackageName;
    if (str != null) {
      this.mPackageName = str;
    }
    this.mUsageTimeReport = otherOptions.mUsageTimeReport;
    this.mTransitionReceiver = null;
    this.mSharedElementNames = null;
    this.mIsReturning = false;
    this.mResultData = null;
    this.mResultCode = 0;
    this.mExitCoordinatorIndex = 0;
    this.mAnimationType = otherOptions.mAnimationType;
    switch (otherOptions.mAnimationType) {
      case 1:
        this.mCustomEnterResId = otherOptions.mCustomEnterResId;
        this.mCustomExitResId = otherOptions.mCustomExitResId;
        this.mCustomBackgroundColor = otherOptions.mCustomBackgroundColor;
        this.mThumbnail = null;
        IRemoteCallback iRemoteCallback = this.mAnimationStartedListener;
        if (iRemoteCallback != null) {
          try {
            iRemoteCallback.sendResult(null);
          } catch (RemoteException e) {
          }
        }
        this.mAnimationStartedListener = otherOptions.mAnimationStartedListener;
        break;
      case 2:
        this.mStartX = otherOptions.mStartX;
        this.mStartY = otherOptions.mStartY;
        this.mWidth = otherOptions.mWidth;
        this.mHeight = otherOptions.mHeight;
        IRemoteCallback iRemoteCallback2 = this.mAnimationStartedListener;
        if (iRemoteCallback2 != null) {
          try {
            iRemoteCallback2.sendResult(null);
          } catch (RemoteException e2) {
          }
        }
        this.mAnimationStartedListener = null;
        break;
      case 3:
      case 4:
      case 8:
      case 9:
        this.mThumbnail = otherOptions.mThumbnail;
        this.mStartX = otherOptions.mStartX;
        this.mStartY = otherOptions.mStartY;
        this.mWidth = otherOptions.mWidth;
        this.mHeight = otherOptions.mHeight;
        IRemoteCallback iRemoteCallback3 = this.mAnimationStartedListener;
        if (iRemoteCallback3 != null) {
          try {
            iRemoteCallback3.sendResult(null);
          } catch (RemoteException e3) {
          }
        }
        this.mAnimationStartedListener = otherOptions.mAnimationStartedListener;
        break;
      case 5:
        this.mTransitionReceiver = otherOptions.mTransitionReceiver;
        this.mSharedElementNames = otherOptions.mSharedElementNames;
        this.mIsReturning = otherOptions.mIsReturning;
        this.mThumbnail = null;
        this.mAnimationStartedListener = null;
        this.mResultData = otherOptions.mResultData;
        this.mResultCode = otherOptions.mResultCode;
        this.mExitCoordinatorIndex = otherOptions.mExitCoordinatorIndex;
        break;
      case 10:
        this.mCustomInPlaceResId = otherOptions.mCustomInPlaceResId;
        break;
    }
    this.mLockTaskMode = otherOptions.mLockTaskMode;
    this.mShareIdentity = otherOptions.mShareIdentity;
    this.mAnimSpecs = otherOptions.mAnimSpecs;
    this.mAnimationFinishedListener = otherOptions.mAnimationFinishedListener;
    this.mSpecsFuture = otherOptions.mSpecsFuture;
    this.mRemoteAnimationAdapter = otherOptions.mRemoteAnimationAdapter;
    this.mLaunchIntoPipParams = otherOptions.mLaunchIntoPipParams;
    this.mIsEligibleForLegacyPermissionPrompt = otherOptions.mIsEligibleForLegacyPermissionPrompt;
  }

  @Override // android.app.ComponentOptions
  public Bundle toBundle() {
    boolean z;
    Bundle b = super.toBundle();
    String str = this.mPackageName;
    if (str != null) {
      b.putString(KEY_PACKAGE_NAME, str);
    }
    Rect rect = this.mLaunchBounds;
    if (rect != null) {
      b.putParcelable(KEY_LAUNCH_BOUNDS, rect);
    }
    int i = this.mAnimationType;
    if (i != -1) {
      b.putInt(KEY_ANIM_TYPE, i);
    }
    PendingIntent pendingIntent = this.mUsageTimeReport;
    if (pendingIntent != null) {
      b.putParcelable(KEY_USAGE_TIME_REPORT, pendingIntent);
    }
    switch (this.mAnimationType) {
      case 1:
        b.putInt(KEY_ANIM_ENTER_RES_ID, this.mCustomEnterResId);
        b.putInt(KEY_ANIM_EXIT_RES_ID, this.mCustomExitResId);
        b.putInt(KEY_ANIM_BACKGROUND_COLOR, this.mCustomBackgroundColor);
        IRemoteCallback iRemoteCallback = this.mAnimationStartedListener;
        b.putBinder(
            KEY_ANIM_START_LISTENER, iRemoteCallback != null ? iRemoteCallback.asBinder() : null);
        break;
      case 2:
      case 11:
        b.putInt(KEY_ANIM_START_X, this.mStartX);
        b.putInt(KEY_ANIM_START_Y, this.mStartY);
        b.putInt(KEY_ANIM_WIDTH, this.mWidth);
        b.putInt(KEY_ANIM_HEIGHT, this.mHeight);
        break;
      case 3:
      case 4:
      case 8:
      case 9:
        Bitmap bitmap = this.mThumbnail;
        if (bitmap != null) {
          Bitmap hwBitmap = bitmap.copy(Bitmap.Config.HARDWARE, false);
          if (hwBitmap != null) {
            b.putParcelable(KEY_ANIM_THUMBNAIL, hwBitmap.getHardwareBuffer());
          } else {
            Slog.m121w(TAG, "Failed to copy thumbnail");
          }
        }
        b.putInt(KEY_ANIM_START_X, this.mStartX);
        b.putInt(KEY_ANIM_START_Y, this.mStartY);
        b.putInt(KEY_ANIM_WIDTH, this.mWidth);
        b.putInt(KEY_ANIM_HEIGHT, this.mHeight);
        IRemoteCallback iRemoteCallback2 = this.mAnimationStartedListener;
        b.putBinder(
            KEY_ANIM_START_LISTENER, iRemoteCallback2 != null ? iRemoteCallback2.asBinder() : null);
        break;
      case 5:
        ResultReceiver resultReceiver = this.mTransitionReceiver;
        if (resultReceiver != null) {
          b.putParcelable(KEY_TRANSITION_COMPLETE_LISTENER, resultReceiver);
        }
        b.putBoolean(KEY_TRANSITION_IS_RETURNING, this.mIsReturning);
        b.putStringArrayList(KEY_TRANSITION_SHARED_ELEMENTS, this.mSharedElementNames);
        b.putParcelable(KEY_RESULT_DATA, this.mResultData);
        b.putInt(KEY_RESULT_CODE, this.mResultCode);
        b.putInt(KEY_EXIT_COORDINATOR_INDEX, this.mExitCoordinatorIndex);
        break;
      case 10:
        b.putInt(KEY_ANIM_IN_PLACE_RES_ID, this.mCustomInPlaceResId);
        break;
    }
    boolean z2 = this.mLockTaskMode;
    if (z2) {
      b.putBoolean(KEY_LOCK_TASK_MODE, z2);
    }
    boolean z3 = this.mShareIdentity;
    if (z3) {
      b.putBoolean(KEY_SHARE_IDENTITY, z3);
    }
    int i2 = this.mLaunchDisplayId;
    if (i2 != -1) {
      b.putInt(KEY_LAUNCH_DISPLAY_ID, i2);
    }
    int i3 = this.mCallerDisplayId;
    if (i3 != -1) {
      b.putInt(KEY_CALLER_DISPLAY_ID, i3);
    }
    WindowContainerToken windowContainerToken = this.mLaunchTaskDisplayArea;
    if (windowContainerToken != null) {
      b.putParcelable(KEY_LAUNCH_TASK_DISPLAY_AREA_TOKEN, windowContainerToken);
    }
    int i4 = this.mLaunchTaskDisplayAreaFeatureId;
    if (i4 != -1) {
      b.putInt(KEY_LAUNCH_TASK_DISPLAY_AREA_FEATURE_ID, i4);
    }
    WindowContainerToken windowContainerToken2 = this.mLaunchRootTask;
    if (windowContainerToken2 != null) {
      b.putParcelable(KEY_LAUNCH_ROOT_TASK_TOKEN, windowContainerToken2);
    }
    IBinder iBinder = this.mLaunchTaskFragmentToken;
    if (iBinder != null) {
      b.putBinder(KEY_LAUNCH_TASK_FRAGMENT_TOKEN, iBinder);
    }
    int i5 = this.mLaunchWindowingMode;
    if (i5 != 0) {
      b.putInt(KEY_LAUNCH_WINDOWING_MODE, i5);
    }
    int i6 = this.mLaunchActivityType;
    if (i6 != 0) {
      b.putInt(KEY_LAUNCH_ACTIVITY_TYPE, i6);
    }
    int i7 = this.mLaunchTaskId;
    if (i7 != -1) {
      b.putInt(KEY_LAUNCH_TASK_ID, i7);
    }
    int i8 = this.mPendingIntentLaunchFlags;
    if (i8 != 0) {
      b.putInt(KEY_PENDING_INTENT_LAUNCH_FLAGS, i8);
    }
    boolean z4 = this.mTaskAlwaysOnTop;
    if (z4) {
      b.putBoolean(KEY_TASK_ALWAYS_ON_TOP, z4);
    }
    boolean z5 = this.mTaskOverlay;
    if (z5) {
      b.putBoolean(KEY_TASK_OVERLAY, z5);
    }
    boolean z6 = this.mTaskOverlayCanResume;
    if (z6) {
      b.putBoolean(KEY_TASK_OVERLAY_CAN_RESUME, z6);
    }
    boolean z7 = this.mAvoidMoveToFront;
    if (z7) {
      b.putBoolean(KEY_AVOID_MOVE_TO_FRONT, z7);
    }
    boolean z8 = this.mFreezeRecentTasksReordering;
    if (z8) {
      b.putBoolean(KEY_FREEZE_RECENT_TASKS_REORDERING, z8);
    }
    boolean z9 = this.mDisallowEnterPictureInPictureWhileLaunching;
    if (z9) {
      b.putBoolean(KEY_DISALLOW_ENTER_PICTURE_IN_PICTURE_WHILE_LAUNCHING, z9);
    }
    boolean z10 = this.mApplyActivityFlagsForBubbles;
    if (z10) {
      b.putBoolean(KEY_APPLY_ACTIVITY_FLAGS_FOR_BUBBLES, z10);
    }
    boolean z11 = this.mApplyMultipleTaskFlagForShortcut;
    if (z11) {
      b.putBoolean(KEY_APPLY_MULTIPLE_TASK_FLAG_FOR_SHORTCUT, z11);
    }
    if (this.mApplyNoUserActionFlagForShortcut) {
      b.putBoolean(KEY_APPLY_NO_USER_ACTION_FLAG_FOR_SHORTCUT, true);
    }
    AppTransitionAnimationSpec[] appTransitionAnimationSpecArr = this.mAnimSpecs;
    if (appTransitionAnimationSpecArr != null) {
      b.putParcelableArray(KEY_ANIM_SPECS, appTransitionAnimationSpecArr);
    }
    IRemoteCallback iRemoteCallback3 = this.mAnimationFinishedListener;
    if (iRemoteCallback3 != null) {
      b.putBinder(KEY_ANIMATION_FINISHED_LISTENER, iRemoteCallback3.asBinder());
    }
    IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture = this.mSpecsFuture;
    if (iAppTransitionAnimationSpecsFuture != null) {
      b.putBinder(KEY_SPECS_FUTURE, iAppTransitionAnimationSpecsFuture.asBinder());
    }
    SourceInfo sourceInfo = this.mSourceInfo;
    if (sourceInfo != null) {
      b.putParcelable(KEY_SOURCE_INFO, sourceInfo);
    }
    int i9 = this.mRotationAnimationHint;
    if (i9 != -1) {
      b.putInt(KEY_ROTATION_ANIMATION_HINT, i9);
    }
    b.putBoolean(KEY_ACTIVE_LAUNCH_HINT, this.mIsActiveApplaunch);
    b.putInt(KEY_ML_LAUNCH_HINT, this.mIsMlLaunch);
    Bundle bundle = this.mAppVerificationBundle;
    if (bundle != null) {
      b.putBundle(KEY_INSTANT_APP_VERIFICATION_BUNDLE, bundle);
    }
    RemoteAnimationAdapter remoteAnimationAdapter = this.mRemoteAnimationAdapter;
    if (remoteAnimationAdapter != null) {
      b.putParcelable(KEY_REMOTE_ANIMATION_ADAPTER, remoteAnimationAdapter);
    }
    IBinder iBinder2 = this.mLaunchCookie;
    if (iBinder2 != null) {
      b.putBinder(KEY_LAUNCH_COOKIE, iBinder2);
    }
    RemoteTransition remoteTransition = this.mRemoteTransition;
    if (remoteTransition != null) {
      b.putParcelable(KEY_REMOTE_TRANSITION, remoteTransition);
    }
    boolean z12 = this.mOverrideTaskTransition;
    if (z12) {
      b.putBoolean(KEY_OVERRIDE_TASK_TRANSITION, z12);
    }
    String str2 = this.mSplashScreenThemeResName;
    if (str2 != null && !str2.isEmpty()) {
      b.putString(KEY_SPLASH_SCREEN_THEME, this.mSplashScreenThemeResName);
    }
    boolean z13 = this.mRemoveWithTaskOrganizer;
    if (z13) {
      b.putBoolean(KEY_REMOVE_WITH_TASK_ORGANIZER, z13);
    }
    boolean z14 = this.mLaunchedFromBubble;
    if (z14) {
      b.putBoolean(KEY_LAUNCHED_FROM_BUBBLE, z14);
    }
    boolean z15 = this.mTransientLaunch;
    if (z15) {
      b.putBoolean(KEY_TRANSIENT_LAUNCH, z15);
    }
    int i10 = this.mSplashScreenStyle;
    if (i10 != 0) {
      b.putInt(KEY_SPLASH_SCREEN_STYLE, i10);
    }
    PictureInPictureParams pictureInPictureParams = this.mLaunchIntoPipParams;
    if (pictureInPictureParams != null) {
      b.putParcelable(KEY_LAUNCH_INTO_PIP_PARAMS, pictureInPictureParams);
    }
    boolean z16 = this.mIsEligibleForLegacyPermissionPrompt;
    if (z16) {
      b.putBoolean(KEY_LEGACY_PERMISSION_PROMPT_ELIGIBLE, z16);
    }
    boolean z17 = this.mDismissKeyguard;
    if (z17) {
      b.putBoolean(KEY_DISMISS_KEYGUARD, z17);
    }
    int i11 = this.mPendingIntentCreatorBackgroundActivityStartMode;
    if (i11 != 0) {
      b.putInt(KEY_PENDING_INTENT_CREATOR_BACKGROUND_ACTIVITY_START_MODE, i11);
    }
    boolean z18 = this.mDisableStartingWindow;
    if (z18) {
      b.putBoolean(KEY_DISABLE_STARTING_WINDOW, z18);
    }
    if (this.mIsPopOver) {
      b.putIntArray(KEY_POP_OVER_WIDTH, this.mPopOverWidthDp);
      b.putIntArray(KEY_POP_OVER_HEIGHT, this.mPopOverHeightDp);
      b.putParcelableArray(KEY_POP_OVER_ANCHOR, this.mPopOverAnchorMarginDp);
      b.putIntArray(KEY_POP_OVER_ANCHOR_POSITION, this.mPopOverAnchorPosition);
      b.putBoolean(KEY_POP_OVER, this.mIsPopOver);
    } else {
      boolean z19 = this.mPopOverInheritOptions;
      if (!z19) {
        b.putBoolean(KEY_POP_OVER_INHERIT_OPTIONS, z19);
      }
    }
    if (this.mResumedAffordanceAnimationRequested) {
      b.putBoolean(KEY_RESUMED_AFFORDANCE_ANIMATION_REQUESTED, true);
    }
    if (CoreRune.FW_CHN_PREMIUM_WATCH && this.mNoTransitionOcclusion) {
      b.putBoolean(KEY_NO_TRANSITION_OCCLUSION, true);
    }
    int i12 = this.mForceLaunchWindowingMode;
    if (i12 != 0) {
      b.putInt(KEY_FORCE_LAUNCH_WINDOWING_MODE, i12);
    }
    b.putInt(KEY_SPLIT_POSITION, this.mSplitPosition);
    b.putBoolean(KEY_LAUNCH_IN_FOCUSED_STAGE_ROOT, this.mLaunchInFocusedStageRoot);
    b.putBoolean(KEY_LAUNCHED_FROM_DND, this.mLaunchedFromDnD);
    b.putBoolean(KEY_DISMISS_SPLIT_BEFORE_LAUNCH, this.mDismissSplitBeforeLaunch);
    b.putBoolean(KEY_APPLY_BIG_FREEFORM_SIZE, this.mApplyBigFreeformSize);
    if (CoreRune.MW_EMBED_ACTIVITY) {
      b.putBoolean(KEY_ACTIVITY_EMBEDDED_PLACEHOLDER, this.mIsActivityEmbeddedPlaceholder);
    }
    b.putInt(KEY_ENTER_SPLIT_SIDE_WITH_ADJACENT_FLAG, this.mEnterSplitSideWithAdjacentFlag);
    b.putBoolean(KEY_STARTED_FROM_WINDOW_TYPE_LAUNCHER, this.mIsStartedFromWindowTypeLauncher);
    if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
      b.putBoolean(KEY_PRESERVE_TASK_WINDOWING_MODE, this.mPreseveTaskWindowingMode);
    }
    b.putBoolean(KEY_ALLOW_ENTER_PIP_WHILE_LAUNCHING, this.mAllowEnterPipWhileLaunching);
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LATE_TRANSIENT_LAUNCH
        && (z = this.mLateTransientLaunch)) {
      b.putBoolean(KEY_LATE_TRANSIENT_LAUNCH, z);
    }
    if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY) {
      b.putBoolean(KEY_FORCE_LAUNCH_TASK_ON_HOME, this.mForceLaunchTaskOnHome);
    }
    b.putBoolean(KEY_SPLIT_TASK_DEFER_RESUME, this.mSplitTaskDeferResume);
    if (CoreRune.SYSFW_APP_PREL) {
      b.putBoolean(KEY_PRELAUNCH, this.mIsPrelaunch);
    }
    return b;
  }

  public void requestUsageTimeReport(PendingIntent receiver) {
    this.mUsageTimeReport = receiver;
  }

  public SourceInfo getSourceInfo() {
    return this.mSourceInfo;
  }

  public void setSourceInfo(int type, long uptimeMillis) {
    this.mSourceInfo = new SourceInfo(type, uptimeMillis);
  }

  public ActivityOptions forTargetActivity() {
    if (this.mAnimationType == 5) {
      ActivityOptions result = new ActivityOptions();
      result.update(this);
      return result;
    }
    return null;
  }

  public int getRotationAnimationHint() {
    return this.mRotationAnimationHint;
  }

  public void setRotationAnimationHint(int hint) {
    this.mRotationAnimationHint = hint;
  }

  public Bundle popAppVerificationBundle() {
    Bundle out = this.mAppVerificationBundle;
    this.mAppVerificationBundle = null;
    return out;
  }

  public ActivityOptions setAppVerificationBundle(Bundle bundle) {
    this.mAppVerificationBundle = bundle;
    return this;
  }

  public void setDismissSplitBeforeLaunch(boolean dismiss) {
    this.mDismissSplitBeforeLaunch = dismiss;
  }

  public boolean isDismissSplitBeforeLaunch() {
    return this.mDismissSplitBeforeLaunch;
  }

  public void setApplyBigFreeformSize(boolean applyBigFreeformSize) {
    this.mApplyBigFreeformSize = applyBigFreeformSize;
  }

  public boolean isApplyBigFreeformSize() {
    return this.mApplyBigFreeformSize;
  }

  public boolean isActivityEmbeddedPlaceholder() {
    return this.mIsActivityEmbeddedPlaceholder;
  }

  public void setActivityEmbeddedPlaceholder() {
    this.mIsActivityEmbeddedPlaceholder = true;
  }

  public void setStartedFromWindowTypeLauncher(boolean isWindowTypeLauncher) {
    this.mIsStartedFromWindowTypeLauncher = isWindowTypeLauncher;
  }

  public boolean isStartedFromWindowTypeLauncher() {
    return this.mIsStartedFromWindowTypeLauncher;
  }

  public boolean isNoTransitionOcclusion() {
    return this.mNoTransitionOcclusion;
  }

  public void setNoTransitionOcclusion() {
    this.mNoTransitionOcclusion = true;
  }

  public boolean isResumedAffordanceAnimationRequested() {
    return this.mResumedAffordanceAnimationRequested;
  }

  public void setResumedAffordanceAnimation() {
    this.mResumedAffordanceAnimationRequested = true;
  }

  public void setAllowEnterPipWhileLaunching(boolean allow) {
    this.mAllowEnterPipWhileLaunching = allow;
  }

  public boolean allowEnterPipWhileLaunching() {
    return this.mAllowEnterPipWhileLaunching;
  }

  public void setForceLaunchTaskOnHome() {
    this.mForceLaunchTaskOnHome = true;
  }

  public boolean isForceLaunchTaskOnHome() {
    return this.mForceLaunchTaskOnHome;
  }

  @Override // android.app.ComponentOptions
  public ActivityOptions setPendingIntentBackgroundActivityStartMode(int state) {
    super.setPendingIntentBackgroundActivityStartMode(state);
    return this;
  }

  @Override // android.app.ComponentOptions
  public int getPendingIntentBackgroundActivityStartMode() {
    return super.getPendingIntentBackgroundActivityStartMode();
  }

  @Override // android.app.ComponentOptions
  @Deprecated
  public void setPendingIntentBackgroundActivityLaunchAllowed(boolean allowed) {
    super.setPendingIntentBackgroundActivityLaunchAllowed(allowed);
  }

  @Override // android.app.ComponentOptions
  @Deprecated
  public boolean isPendingIntentBackgroundActivityLaunchAllowed() {
    return super.isPendingIntentBackgroundActivityLaunchAllowed();
  }

  public String toString() {
    return "ActivityOptions("
        + hashCode()
        + "), mPackageName="
        + this.mPackageName
        + ", mAnimationType="
        + this.mAnimationType
        + ", mStartX="
        + this.mStartX
        + ", mStartY="
        + this.mStartY
        + ", mWidth="
        + this.mWidth
        + ", mHeight="
        + this.mHeight
        + ", mLaunchDisplayId="
        + this.mLaunchDisplayId;
  }

  public static class SourceInfo implements Parcelable {
    public static final Parcelable.Creator<SourceInfo> CREATOR =
        new Parcelable.Creator<
            SourceInfo>() { // from class: android.app.ActivityOptions.SourceInfo.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SourceInfo createFromParcel(Parcel in) {
            return new SourceInfo(in.readInt(), in.readLong());
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SourceInfo[] newArray(int size) {
            return new SourceInfo[size];
          }
        };
    public static final int TYPE_LAUNCHER = 1;
    public static final int TYPE_LOCKSCREEN = 3;
    public static final int TYPE_NOTIFICATION = 2;
    public static final int TYPE_RECENTS_ANIMATION = 4;
    public final long eventTimeMs;
    public final int type;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SourceType {}

    SourceInfo(int srcType, long uptimeMillis) {
      this.type = srcType;
      this.eventTimeMs = uptimeMillis;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.type);
      dest.writeLong(this.eventTimeMs);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
    }
  }

  public ActivityOptions semSetPopOverOptions(
      int[] widthDp, int[] heightDp, Point[] marginDp, int[] position) {
    return setPopOverOptions(widthDp, heightDp, marginDp, position);
  }

  public ActivityOptions semSetChooserPopOverPosition(int position) {
    if ((position & 7) == 0 || (position & 112) == 0) {
      return this;
    }
    int[] iArr = this.mPopOverWidthDp;
    iArr[1] = 360;
    iArr[0] = 360;
    int[] iArr2 = this.mPopOverHeightDp;
    iArr2[1] = 360;
    iArr2[0] = 360;
    Point[] pointArr = this.mPopOverAnchorMarginDp;
    Point point = new Point(0, 0);
    pointArr[1] = point;
    pointArr[0] = point;
    int[] iArr3 = this.mPopOverAnchorPosition;
    iArr3[1] = position;
    iArr3[0] = position;
    boolean hasBottom = (position & 7) == 2;
    if (hasBottom) {
      this.mPopOverAnchorMarginDp[0].f86y = 44;
    }
    this.mIsPopOver = true;
    return this;
  }

  public boolean isPopOver() {
    return this.mIsPopOver;
  }

  public ActivityOptions setPopOverOptions(
      int[] widthDp, int[] heightDp, Point[] marginDp, int[] position) {
    if (widthDp == null && heightDp == null && marginDp == null && position == null) {
      this.mPopOverInheritOptions = false;
      this.mIsPopOver = false;
      return this;
    }
    if (widthDp == null
        || widthDp.length != 2
        || heightDp == null
        || heightDp.length != 2
        || marginDp == null
        || marginDp.length != 2
        || position == null
        || position.length != 2) {
      return this;
    }
    for (int i = 0; i < 2; i++) {
      if ((position[i] & 7) == 0 || (position[i] & 112) == 0) {
        return this;
      }
      if (widthDp[i] <= 0 || heightDp[i] <= 0 || marginDp[i] == null) {
        return this;
      }
    }
    for (int i2 = 0; i2 < 2; i2++) {
      this.mPopOverWidthDp[i2] = widthDp[i2];
      this.mPopOverHeightDp[i2] = heightDp[i2];
      this.mPopOverAnchorMarginDp[i2] = new Point(marginDp[i2]);
      this.mPopOverAnchorPosition[i2] = position[i2];
    }
    this.mIsPopOver = true;
    return this;
  }

  public boolean hasValidLaunchAdjacentExt() {
    return hasValidVerticalSplitLayoutWithAdjacentFlag()
        || hasValidHorizontalSplitLayoutWithAdjacentFlag();
  }

  public boolean hasValidHorizontalSplitLayoutWithAdjacentFlag() {
    int i = this.mEnterSplitSideWithAdjacentFlag;
    return i == 1 || i == 2;
  }

  public boolean hasValidVerticalSplitLayoutWithAdjacentFlag() {
    int i = this.mEnterSplitSideWithAdjacentFlag;
    return i == 3 || i == 4;
  }

  public boolean launchToTopSideWithAdjacentFlag() {
    return this.mEnterSplitSideWithAdjacentFlag == 1;
  }

  public boolean launchToRightSideWithAdjacentFlag() {
    return this.mEnterSplitSideWithAdjacentFlag == 4;
  }

  public ActivityOptions setLateTransientLaunch() {
    this.mLateTransientLaunch = true;
    return this;
  }

  public boolean getLateTransientLaunch() {
    return this.mLateTransientLaunch;
  }

  public int getCustomizedCoverDensity() {
    return this.mCustomizedCoverDensity;
  }

  public void setCustomizedCoverDensity(int density) {
    this.mCustomizedCoverDensity = density;
  }

  public boolean getPrelaunch() {
    return this.mIsPrelaunch;
  }

  public void setPrelaunch() {
    this.mIsPrelaunch = true;
  }
}
