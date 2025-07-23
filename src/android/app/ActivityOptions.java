package android.app;

import android.annotation.SystemApi;
import android.app.ExitTransitionCoordinator;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
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
import com.android.internal.R;
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
    private static final String KEY_ACTIVITY_EMBEDDED_PLACEHOLDER = "android:activity.activityEmbeddedPlaceholder";
    private static final String KEY_ALLOW_ENTER_PIP_WHILE_LAUNCHING = "android.activity.enterPipWhileLaunching";
    private static final String KEY_ALLOW_PASS_THROUGH_ON_TOUCH_OUTSIDE = "android.activity.allowPassThroughOnTouchOutside";
    private static final String KEY_ANIMATION_FINISHED_LISTENER = "android:activity.animationFinishedListener";
    private static final String KEY_ANIM_ABORT_LISTENER = "android:activity.animAbortListener";
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
    private static final String KEY_APPLY_ACTIVITY_FLAGS_FOR_BUBBLES = "android:activity.applyActivityFlagsForBubbles";
    private static final String KEY_APPLY_BIG_FREEFORM_SIZE = "android:activity.applyBigFreeformSize";
    private static final String KEY_APPLY_MULTIPLE_TASK_FLAG_FOR_SHORTCUT = "android:activity.applyMultipleTaskFlagForShortcut";
    private static final String KEY_APPLY_NO_USER_ACTION_FLAG_FOR_SHORTCUT = "android:activity.applyNoUserActionFlagForShortcut";
    private static final String KEY_AVOID_MOVE_TO_FRONT = "android.activity.avoidMoveToFront";
    private static final String KEY_CALLER_DISPLAY_ID = "android.activity.callerDisplayId";
    private static final String KEY_CUSTOMIZED_COVER_DENSITY = "android.activity.customizedCoverDensity";
    private static final String KEY_DISABLE_SPLASH_SCREEN = "android.activity.disableSplashScreen";
    private static final String KEY_DISABLE_STARTING_WINDOW = "android.activity.disableStarting";
    private static final String KEY_DISALLOW_ENTER_PICTURE_IN_PICTURE_WHILE_LAUNCHING = "android:activity.disallowEnterPictureInPictureWhileLaunching";
    private static final String KEY_DISMISS_KEYGUARD_IF_INSECURE = "android.activity.dismissKeyguardIfInsecure";
    private static final String KEY_ENTER_SPLIT_SIDE_WITH_ADJACENT_FLAG = "android:activity.enterSplitSideWithAdjacentFlag";
    private static final String KEY_FLEXIBLE_LAUNCH_SIZE = "android.activity.flexibleLaunchSize";
    private static final String KEY_FORCE_LAUNCH_TASK_ON_HOME = "android.activity.forceLaunchTaskOnHome";
    private static final String KEY_FORCE_LAUNCH_WINDOWING_MODE = "android.activity.forceWindowingMode";
    private static final String KEY_FREEZE_RECENT_TASKS_REORDERING = "android.activity.freezeRecentTasksReordering";
    private static final String KEY_INSTANT_APP_VERIFICATION_BUNDLE = "android:instantapps.installerbundle";
    private static final String KEY_LAUNCHED_FROM_BUBBLE = "android.activity.launchTypeBubble";
    private static final String KEY_LAUNCHED_FROM_DND = "android.activity.launchTypeDnD";
    public static final String KEY_LAUNCHED_FROM_HOME = "android:activity.launchedFromHome";
    private static final String KEY_LAUNCH_ACTIVITY_TYPE = "android.activity.activityType";
    public static final String KEY_LAUNCH_BOUNDS = "android:activity.launchBounds";
    public static final String KEY_LAUNCH_COOKIE = "android.activity.launchCookie";
    private static final String KEY_LAUNCH_DISPLAY_ID = "android.activity.launchDisplayId";
    private static final String KEY_LAUNCH_INTO_PIP_PARAMS = "android.activity.launchIntoPipParams";
    private static final String KEY_LAUNCH_IN_FOCUSED_STAGE_ROOT = "android:activity.launchInFocusedStageRoot";
    public static final String KEY_LAUNCH_ROOT_TASK_TOKEN = "android.activity.launchRootTaskToken";
    private static final String KEY_LAUNCH_TASK_DISPLAY_AREA_FEATURE_ID = "android.activity.launchTaskDisplayAreaFeatureId";
    private static final String KEY_LAUNCH_TASK_DISPLAY_AREA_TOKEN = "android.activity.launchTaskDisplayAreaToken";
    public static final String KEY_LAUNCH_TASK_FRAGMENT_TOKEN = "android.activity.launchTaskFragmentToken";
    private static final String KEY_LAUNCH_TASK_ID = "android.activity.launchTaskId";
    private static final String KEY_LAUNCH_WINDOWING_MODE = "android.activity.windowingMode";
    public static final String KEY_LEGACY_PERMISSION_PROMPT_ELIGIBLE = "android:activity.legacyPermissionPromptEligible";
    private static final String KEY_LOCK_TASK_MODE = "android:activity.lockTaskMode";
    private static final String KEY_ML_LAUNCH_HINT = "android:activity.isMlLaunch";
    private static final String KEY_OVERRIDE_TASK_TRANSITION = "android:activity.overrideTaskTransition";
    public static final String KEY_PACKAGE_NAME = "android:activity.packageName";
    private static final String KEY_PENDING_INTENT_CREATOR_BACKGROUND_ACTIVITY_START_MODE = "android.activity.pendingIntentCreatorBackgroundActivityStartMode";
    private static final String KEY_PENDING_INTENT_LAUNCH_FLAGS = "android.activity.pendingIntentLaunchFlags";
    private static final String KEY_POP_OVER = "android:activity.popOver";
    private static final String KEY_POP_OVER_ANCHOR = "android:activity.popOverAnchor";
    private static final String KEY_POP_OVER_ANCHOR_POSITION = "android:activity.popOverAnchorPosition";
    private static final String KEY_POP_OVER_HEIGHT = "android:activity.popOverHeight";
    private static final String KEY_POP_OVER_INHERIT_OPTIONS = "android:activity.popOverInheritOptions";
    private static final String KEY_POP_OVER_WIDTH = "android:activity.popOverWidth";
    private static final String KEY_PRESERVE_TASK_WINDOWING_MODE = "android.activity.preserveTaskWindowingMode";
    private static final String KEY_REMOTE_ANIMATION_ADAPTER = "android:activity.remoteAnimationAdapter";
    private static final String KEY_REMOTE_TRANSITION = "android:activity.remoteTransition";
    private static final String KEY_REMOVE_WITH_TASK_ORGANIZER = "android.activity.removeWithTaskOrganizer";
    private static final String KEY_RESUMED_AFFORDANCE_ANIMATION_REQUESTED = "android:activity.resumedAffordanceAnimationRequested";
    private static final String KEY_ROTATION_ANIMATION_HINT = "android:activity.rotationAnimationHint";
    private static final String KEY_SCENE_TRANSITION_INFO = "android:activity.sceneTransitionInfo";
    private static final String KEY_SHARE_IDENTITY = "android:activity.shareIdentity";
    private static final String KEY_SOURCE_INFO = "android.activity.sourceInfo";
    private static final String KEY_SPECS_FUTURE = "android:activity.specsFuture";
    private static final String KEY_SPLASH_SCREEN_STYLE = "android.activity.splashScreenStyle";
    public static final String KEY_SPLASH_SCREEN_THEME = "android.activity.splashScreenTheme";
    private static final String KEY_SPLIT_POSITION = "android.activity.splitPosition";
    public static final String KEY_SPLIT_TASK_DEFER_RESUME = "android.activity.splitTaskDeferResume";
    public static final String KEY_STARTED_BY_MDM_ADMIN = "edm:activity.startedByMDMAdmin";
    public static final String KEY_STARTED_FROM_WINDOW_TYPE_LAUNCHER = "android:activity.startedFromWindowTypeLauncher";
    public static final String KEY_START_ASSISTANT_ACTIVITY = "android.activity.startAssistantActivity";
    private static final String KEY_TASK_ALWAYS_ON_TOP = "android.activity.alwaysOnTop";
    private static final String KEY_TASK_OVERLAY = "android.activity.taskOverlay";
    private static final String KEY_TASK_OVERLAY_CAN_RESUME = "android.activity.taskOverlayCanResume";
    public static final String KEY_TRANSIENT_LAUNCH = "android.activity.transientLaunch";
    public static final String KEY_UNHANDLED_DROP_LAUNCH = "android:activity.unhandledDropLaunch";
    private static final String KEY_USAGE_TIME_REPORT = "android:activity.usageTimeReport";
    public static final int LAND = 0;
    public static final int ML_TYPE_EMPTY_PROCESS = 1;
    public static final int ML_TYPE_NAP_PROCESS = 0;

    @Deprecated
    public static final int MODE_BACKGROUND_ACTIVITY_START_ALLOWED = 1;
    public static final int MODE_BACKGROUND_ACTIVITY_START_ALLOW_ALWAYS = 3;
    public static final int MODE_BACKGROUND_ACTIVITY_START_ALLOW_IF_VISIBLE = 4;
    public static final int MODE_BACKGROUND_ACTIVITY_START_COMPAT = -1;
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
    private boolean mAllowPassThroughOnTouchOutside;
    private AppTransitionAnimationSpec[] mAnimSpecs;
    private IRemoteCallback mAnimationAbortListener;
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
    private boolean mDisableSplashScreen;
    private boolean mDisableStartingWindow;
    private boolean mDisallowEnterPictureInPictureWhileLaunching;
    private boolean mDismissKeyguardIfInsecure;
    private int mEnterSplitSideWithAdjacentFlag;
    private boolean mFlexibleLaunchSize;
    private boolean mForceLaunchTaskOnHome;
    private int mForceLaunchWindowingMode;
    private boolean mFreezeRecentTasksReordering;
    private int mHeight;
    private boolean mIsActiveApplaunch;
    private boolean mIsActivityEmbeddedPlaceholder;
    private boolean mIsEligibleForLegacyPermissionPrompt;
    private int mIsMlLaunch;
    private boolean mIsPopOver;
    private boolean mIsStartedFromWindowTypeLauncher;
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
    private boolean mLaunchedFromHome;
    private boolean mLockTaskMode;
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
    private boolean mPreserveTaskWindowingMode;
    private RemoteAnimationAdapter mRemoteAnimationAdapter;
    private RemoteTransition mRemoteTransition;
    private boolean mRemoveWithTaskOrganizer;
    private boolean mResumedAffordanceAnimationRequested;
    private int mRotationAnimationHint;
    private SceneTransitionInfo mSceneTransitionInfo;
    private boolean mShareIdentity;
    private SourceInfo mSourceInfo;
    private IAppTransitionAnimationSpecsFuture mSpecsFuture;
    private int mSplashScreenStyle;
    private String mSplashScreenThemeResName;
    private int mSplitPosition;
    private boolean mSplitTaskDeferResume;
    private boolean mStartAssistantActivity;
    private int mStartX;
    private int mStartY;
    private boolean mStartedByMDMAdmin;
    private boolean mTaskAlwaysOnTop;
    private boolean mTaskOverlay;
    private boolean mTaskOverlayCanResume;
    private Bitmap mThumbnail;
    private boolean mTransientLaunch;
    private boolean mUnhandledDropLaunch;
    private PendingIntent mUsageTimeReport;
    private int mWidth;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BackgroundActivityStartMode {
    }

    public interface OnAnimationFinishedListener {
        void onAnimationFinished(long j);
    }

    public interface OnAnimationStartedListener {
        void onAnimationStarted(long j);
    }

    public void setSplitScreenCreateMode(int i) {
    }

    public static ActivityOptions makeCustomAnimation(Context context, int i, int i2) {
        return makeCustomAnimation(context, i, i2, 0, null, null);
    }

    public static ActivityOptions makeCustomAnimation(Context context, int i, int i2, int i3) {
        return makeCustomAnimation(context, i, i2, i3, null, null);
    }

    public static ActivityOptions makeCustomAnimation(Context context, int i, int i2, int i3, Handler handler, OnAnimationStartedListener onAnimationStartedListener) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = context.getPackageName();
        activityOptions.mAnimationType = 1;
        activityOptions.mCustomEnterResId = i;
        activityOptions.mCustomExitResId = i2;
        activityOptions.mCustomBackgroundColor = i3;
        activityOptions.setOnAnimationStartedListener(handler, onAnimationStartedListener);
        return activityOptions;
    }

    public static ActivityOptions makeCustomAnimation(Context context, int i, int i2, int i3, Handler handler, OnAnimationStartedListener onAnimationStartedListener, OnAnimationFinishedListener onAnimationFinishedListener) {
        ActivityOptions makeCustomAnimation = makeCustomAnimation(context, i, i2, i3, handler, onAnimationStartedListener);
        makeCustomAnimation.setOnAnimationFinishedListener(handler, onAnimationFinishedListener);
        return makeCustomAnimation;
    }

    public static ActivityOptions makeCustomTaskAnimation(Context context, int i, int i2, Handler handler, OnAnimationStartedListener onAnimationStartedListener, OnAnimationFinishedListener onAnimationFinishedListener) {
        ActivityOptions makeCustomAnimation = makeCustomAnimation(context, i, i2, 0, handler, onAnimationStartedListener, onAnimationFinishedListener);
        makeCustomAnimation.mOverrideTaskTransition = true;
        return makeCustomAnimation;
    }

    public static ActivityOptions makeCustomInPlaceAnimation(Context context, int i) {
        if (i == 0) {
            throw new RuntimeException("You must specify a valid animation.");
        }
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = context.getPackageName();
        activityOptions.mAnimationType = 10;
        activityOptions.mCustomInPlaceResId = i;
        return activityOptions;
    }

    private void setOnAnimationStartedListener(final Handler handler, final OnAnimationStartedListener onAnimationStartedListener) {
        if (onAnimationStartedListener != null) {
            this.mAnimationStartedListener = new IRemoteCallback.Stub(this) { // from class: android.app.ActivityOptions.1
                @Override // android.os.IRemoteCallback
                public void sendResult(Bundle bundle) throws RemoteException {
                    final long elapsedRealtime = SystemClock.elapsedRealtime();
                    handler.post(new Runnable() { // from class: android.app.ActivityOptions.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            onAnimationStartedListener.onAnimationStarted(elapsedRealtime);
                        }
                    });
                }
            };
        }
    }

    private void setOnAnimationFinishedListener(final Handler handler, final OnAnimationFinishedListener onAnimationFinishedListener) {
        if (onAnimationFinishedListener != null) {
            this.mAnimationFinishedListener = new IRemoteCallback.Stub(this) { // from class: android.app.ActivityOptions.2
                @Override // android.os.IRemoteCallback
                public void sendResult(Bundle bundle) throws RemoteException {
                    final long elapsedRealtime = SystemClock.elapsedRealtime();
                    handler.post(new Runnable() { // from class: android.app.ActivityOptions.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            onAnimationFinishedListener.onAnimationFinished(elapsedRealtime);
                        }
                    });
                }
            };
        }
    }

    public void setOnAnimationFinishedListener(IRemoteCallback iRemoteCallback) {
        this.mAnimationFinishedListener = iRemoteCallback;
    }

    public void setOnAnimationAbortListener(IRemoteCallback iRemoteCallback) {
        this.mAnimationAbortListener = iRemoteCallback;
    }

    public static ActivityOptions makeScaleUpAnimation(View view, int i, int i2, int i3, int i4) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = view.getContext().getPackageName();
        activityOptions.mAnimationType = 2;
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        activityOptions.mStartX = iArr[0] + i;
        activityOptions.mStartY = iArr[1] + i2;
        activityOptions.mWidth = i3;
        activityOptions.mHeight = i4;
        return activityOptions;
    }

    public static ActivityOptions makeClipRevealAnimation(View view, int i, int i2, int i3, int i4) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mAnimationType = 11;
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        activityOptions.mStartX = iArr[0] + i;
        activityOptions.mStartY = iArr[1] + i2;
        activityOptions.mWidth = i3;
        activityOptions.mHeight = i4;
        return activityOptions;
    }

    public static ActivityOptions makeOpenCrossProfileAppsAnimation() {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mAnimationType = 12;
        return activityOptions;
    }

    public static ActivityOptions makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int i, int i2) {
        return makeThumbnailScaleUpAnimation(view, bitmap, i, i2, null);
    }

    private static ActivityOptions makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int i, int i2, OnAnimationStartedListener onAnimationStartedListener) {
        return makeThumbnailAnimation(view, bitmap, i, i2, onAnimationStartedListener, true);
    }

    private static ActivityOptions makeThumbnailAnimation(View view, Bitmap bitmap, int i, int i2, OnAnimationStartedListener onAnimationStartedListener, boolean z) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = view.getContext().getPackageName();
        activityOptions.mAnimationType = z ? 3 : 4;
        activityOptions.mThumbnail = bitmap;
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        activityOptions.mStartX = iArr[0] + i;
        activityOptions.mStartY = iArr[1] + i2;
        activityOptions.setOnAnimationStartedListener(view.getHandler(), onAnimationStartedListener);
        return activityOptions;
    }

    public static ActivityOptions makeMultiThumbFutureAspectScaleAnimation(Context context, Handler handler, IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, OnAnimationStartedListener onAnimationStartedListener, boolean z) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = context.getPackageName();
        activityOptions.mAnimationType = z ? 8 : 9;
        activityOptions.mSpecsFuture = iAppTransitionAnimationSpecsFuture;
        activityOptions.setOnAnimationStartedListener(handler, onAnimationStartedListener);
        return activityOptions;
    }

    public static ActivityOptions makeThumbnailAspectScaleDownAnimation(View view, Bitmap bitmap, int i, int i2, int i3, int i4, Handler handler, OnAnimationStartedListener onAnimationStartedListener) {
        return makeAspectScaledThumbnailAnimation(view, bitmap, i, i2, i3, i4, handler, onAnimationStartedListener, false);
    }

    private static ActivityOptions makeAspectScaledThumbnailAnimation(View view, Bitmap bitmap, int i, int i2, int i3, int i4, Handler handler, OnAnimationStartedListener onAnimationStartedListener, boolean z) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = view.getContext().getPackageName();
        activityOptions.mAnimationType = z ? 8 : 9;
        activityOptions.mThumbnail = bitmap;
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        activityOptions.mStartX = iArr[0] + i;
        activityOptions.mStartY = iArr[1] + i2;
        activityOptions.mWidth = i3;
        activityOptions.mHeight = i4;
        activityOptions.setOnAnimationStartedListener(handler, onAnimationStartedListener);
        return activityOptions;
    }

    public static ActivityOptions makeThumbnailAspectScaleDownAnimation(View view, AppTransitionAnimationSpec[] appTransitionAnimationSpecArr, Handler handler, OnAnimationStartedListener onAnimationStartedListener, OnAnimationFinishedListener onAnimationFinishedListener) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mPackageName = view.getContext().getPackageName();
        activityOptions.mAnimationType = 9;
        activityOptions.mAnimSpecs = appTransitionAnimationSpecArr;
        activityOptions.setOnAnimationStartedListener(handler, onAnimationStartedListener);
        activityOptions.setOnAnimationFinishedListener(handler, onAnimationFinishedListener);
        return activityOptions;
    }

    public static ActivityOptions makeSceneTransitionAnimation(Activity activity, View view, String str) {
        return makeSceneTransitionAnimation(activity, Pair.create(view, str));
    }

    @SafeVarargs
    public static ActivityOptions makeSceneTransitionAnimation(Activity activity, Pair<View, String>... pairArr) {
        ActivityOptions activityOptions = new ActivityOptions();
        ExitTransitionCoordinator makeSceneTransitionAnimation = makeSceneTransitionAnimation(new ExitTransitionCoordinator.ActivityExitTransitionCallbacks(activity), activity.mExitTransitionListener, activity.getWindow(), activityOptions, pairArr);
        SceneTransitionInfo sceneTransitionInfo = activityOptions.getSceneTransitionInfo();
        if (sceneTransitionInfo != null) {
            sceneTransitionInfo.setExitCoordinatorKey(activity.mActivityTransitionState.addExitTransitionCoordinator(makeSceneTransitionAnimation));
        }
        Slog.d(TAG, "makeSceneTransitionAnimation is called, activity=" + activity + ", caller=" + Debug.getCallers(3));
        return activityOptions;
    }

    @SafeVarargs
    public static Pair<ActivityOptions, ExitTransitionCoordinator> startSharedElementAnimation(Window window, ExitTransitionCoordinator.ExitTransitionCallbacks exitTransitionCallbacks, SharedElementCallback sharedElementCallback, Pair<View, String>... pairArr) {
        ActivityOptions activityOptions = new ActivityOptions();
        ExitTransitionCoordinator makeSceneTransitionAnimation = makeSceneTransitionAnimation(exitTransitionCallbacks, sharedElementCallback, window, activityOptions, pairArr);
        SceneTransitionInfo sceneTransitionInfo = activityOptions.getSceneTransitionInfo();
        if (sceneTransitionInfo != null) {
            sceneTransitionInfo.setExitCoordinatorKey(-1);
        }
        return Pair.create(activityOptions, makeSceneTransitionAnimation);
    }

    public static void stopSharedElementAnimation(Window window) {
        ExitTransitionCoordinator exitTransitionCoordinator;
        View decorView = window.getDecorView();
        if (decorView == null || (exitTransitionCoordinator = (ExitTransitionCoordinator) decorView.getTag(R.id.cross_task_transition)) == null) {
            return;
        }
        exitTransitionCoordinator.cancelPendingTransitions();
        decorView.setTagInternal(R.id.cross_task_transition, null);
        TransitionManager.endTransitions((ViewGroup) decorView);
        exitTransitionCoordinator.resetViews();
        exitTransitionCoordinator.clearState();
        decorView.setVisibility(0);
    }

    static ExitTransitionCoordinator makeSceneTransitionAnimation(ExitTransitionCoordinator.ExitTransitionCallbacks exitTransitionCallbacks, SharedElementCallback sharedElementCallback, Window window, ActivityOptions activityOptions, Pair<View, String>[] pairArr) {
        if (!window.hasFeature(13)) {
            activityOptions.mAnimationType = 6;
            return null;
        }
        activityOptions.mAnimationType = 5;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList arrayList2 = new ArrayList();
        if (pairArr != null) {
            for (Pair<View, String> pair : pairArr) {
                String str = pair.second;
                if (str == null) {
                    throw new IllegalArgumentException("Shared element name must not be null");
                }
                arrayList.add(str);
                if (pair.first == null) {
                    throw new IllegalArgumentException("Shared element must not be null");
                }
                arrayList2.add(pair.first);
            }
        }
        ExitTransitionCoordinator exitTransitionCoordinator = new ExitTransitionCoordinator(exitTransitionCallbacks, window, sharedElementCallback, arrayList, arrayList, arrayList2, false);
        SceneTransitionInfo sceneTransitionInfo = new SceneTransitionInfo();
        sceneTransitionInfo.setResultReceiver(exitTransitionCoordinator);
        sceneTransitionInfo.setSharedElementNames(arrayList);
        sceneTransitionInfo.setReturning(false);
        activityOptions.setSceneTransitionInfo(sceneTransitionInfo);
        Slog.d(TAG, "makeSceneTransitionAnimation is called, window=" + window + ", caller=" + Debug.getCallers(3));
        return exitTransitionCoordinator;
    }

    public static void setExitTransitionTimeout(long j) {
        ExitTransitionCoordinator.sMaxWaitMillis = j;
    }

    static ActivityOptions makeSceneTransitionAnimation(Activity activity, ExitTransitionCoordinator exitTransitionCoordinator, ArrayList<String> arrayList, int i, Intent intent) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mAnimationType = 5;
        SceneTransitionInfo sceneTransitionInfo = new SceneTransitionInfo();
        sceneTransitionInfo.setSharedElementNames(arrayList);
        sceneTransitionInfo.setResultReceiver(exitTransitionCoordinator);
        sceneTransitionInfo.setReturning(true);
        sceneTransitionInfo.setResultCode(i);
        sceneTransitionInfo.setResultData(intent);
        if (activity == null) {
            sceneTransitionInfo.setExitCoordinatorKey(-1);
        } else {
            sceneTransitionInfo.setExitCoordinatorKey(activity.mActivityTransitionState.addExitTransitionCoordinator(exitTransitionCoordinator));
        }
        activityOptions.setSceneTransitionInfo(sceneTransitionInfo);
        Slog.d(TAG, "makeSceneTransitionAnimation is called, activity=" + activity + ", caller=" + Debug.getCallers(3));
        return activityOptions;
    }

    public static ActivityOptions makeTaskLaunchBehind() {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mAnimationType = 7;
        return activityOptions;
    }

    public static ActivityOptions makeBasic() {
        return new ActivityOptions();
    }

    public static ActivityOptions makeRemoteAnimation(RemoteAnimationAdapter remoteAnimationAdapter) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mRemoteAnimationAdapter = remoteAnimationAdapter;
        activityOptions.mAnimationType = 13;
        return activityOptions;
    }

    public static ActivityOptions makeRemoteAnimation(RemoteAnimationAdapter remoteAnimationAdapter, RemoteTransition remoteTransition) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mRemoteAnimationAdapter = remoteAnimationAdapter;
        activityOptions.mAnimationType = 13;
        activityOptions.mRemoteTransition = remoteTransition;
        return activityOptions;
    }

    public static ActivityOptions makeRemoteTransition(RemoteTransition remoteTransition) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mRemoteTransition = remoteTransition;
        Slog.d(TAG, "makeRemoteTransition, remoteTransition=" + remoteTransition + ", caller=" + Debug.getCallers(3));
        return activityOptions;
    }

    public static ActivityOptions makeLaunchIntoPip(PictureInPictureParams pictureInPictureParams) {
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.mLaunchIntoPipParams = new PictureInPictureParams.Builder(pictureInPictureParams).setIsLaunchIntoPip(true).build();
        return activityOptions;
    }

    public boolean getLaunchTaskBehind() {
        return this.mAnimationType == 7;
    }

    private ActivityOptions() {
        this.mAnimationType = -1;
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
        this.mFlexibleLaunchSize = false;
        this.mSplitPosition = 0;
        this.mLaunchInFocusedStageRoot = false;
        this.mForceLaunchWindowingMode = 0;
        this.mIsMlLaunch = -1;
        this.mSplitTaskDeferResume = false;
        this.mStartAssistantActivity = false;
        this.mIsActivityEmbeddedPlaceholder = false;
        this.mLaunchedFromDnD = false;
        this.mAllowEnterPipWhileLaunching = false;
        this.mForceLaunchTaskOnHome = false;
        this.mPopOverWidthDp = new int[2];
        this.mPopOverHeightDp = new int[2];
        this.mPopOverAnchorMarginDp = new Point[2];
        this.mPopOverAnchorPosition = new int[2];
        this.mPopOverInheritOptions = true;
        this.mDisableSplashScreen = false;
        this.mCustomizedCoverDensity = 0;
        this.mLaunchedFromHome = false;
    }

    public ActivityOptions(Bundle bundle) {
        super(bundle);
        this.mAnimationType = -1;
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
        this.mFlexibleLaunchSize = false;
        this.mSplitPosition = 0;
        this.mLaunchInFocusedStageRoot = false;
        this.mForceLaunchWindowingMode = 0;
        this.mIsMlLaunch = -1;
        this.mSplitTaskDeferResume = false;
        this.mStartAssistantActivity = false;
        this.mIsActivityEmbeddedPlaceholder = false;
        this.mLaunchedFromDnD = false;
        this.mAllowEnterPipWhileLaunching = false;
        this.mForceLaunchTaskOnHome = false;
        this.mPopOverWidthDp = new int[2];
        this.mPopOverHeightDp = new int[2];
        this.mPopOverAnchorMarginDp = new Point[2];
        this.mPopOverAnchorPosition = new int[2];
        this.mPopOverInheritOptions = true;
        this.mDisableSplashScreen = false;
        this.mCustomizedCoverDensity = 0;
        this.mLaunchedFromHome = false;
        this.mPackageName = bundle.getString(KEY_PACKAGE_NAME);
        try {
            this.mUsageTimeReport = (PendingIntent) bundle.getParcelable(KEY_USAGE_TIME_REPORT, PendingIntent.class);
        } catch (RuntimeException e) {
            Slog.w(TAG, e);
        }
        this.mLaunchBounds = (Rect) bundle.getParcelable(KEY_LAUNCH_BOUNDS, Rect.class);
        int i = bundle.getInt(KEY_ANIM_TYPE, -1);
        this.mAnimationType = i;
        switch (i) {
            case 1:
                this.mCustomEnterResId = bundle.getInt(KEY_ANIM_ENTER_RES_ID, 0);
                this.mCustomExitResId = bundle.getInt(KEY_ANIM_EXIT_RES_ID, 0);
                this.mCustomBackgroundColor = bundle.getInt(KEY_ANIM_BACKGROUND_COLOR, 0);
                this.mAnimationStartedListener = IRemoteCallback.Stub.asInterface(bundle.getBinder(KEY_ANIM_START_LISTENER));
                break;
            case 2:
            case 11:
                this.mStartX = bundle.getInt(KEY_ANIM_START_X, 0);
                this.mStartY = bundle.getInt(KEY_ANIM_START_Y, 0);
                this.mWidth = bundle.getInt(KEY_ANIM_WIDTH, 0);
                this.mHeight = bundle.getInt(KEY_ANIM_HEIGHT, 0);
                break;
            case 3:
            case 4:
            case 8:
            case 9:
                HardwareBuffer hardwareBuffer = (HardwareBuffer) bundle.getParcelable(KEY_ANIM_THUMBNAIL, HardwareBuffer.class);
                if (hardwareBuffer != null) {
                    this.mThumbnail = Bitmap.wrapHardwareBuffer(hardwareBuffer, null);
                }
                this.mStartX = bundle.getInt(KEY_ANIM_START_X, 0);
                this.mStartY = bundle.getInt(KEY_ANIM_START_Y, 0);
                this.mWidth = bundle.getInt(KEY_ANIM_WIDTH, 0);
                this.mHeight = bundle.getInt(KEY_ANIM_HEIGHT, 0);
                this.mAnimationStartedListener = IRemoteCallback.Stub.asInterface(bundle.getBinder(KEY_ANIM_START_LISTENER));
                break;
            case 5:
                this.mSceneTransitionInfo = (SceneTransitionInfo) bundle.getParcelable(KEY_SCENE_TRANSITION_INFO, SceneTransitionInfo.class);
                break;
            case 10:
                this.mCustomInPlaceResId = bundle.getInt(KEY_ANIM_IN_PLACE_RES_ID, 0);
                break;
        }
        this.mLockTaskMode = bundle.getBoolean(KEY_LOCK_TASK_MODE, false);
        this.mShareIdentity = bundle.getBoolean(KEY_SHARE_IDENTITY, false);
        this.mLaunchDisplayId = bundle.getInt(KEY_LAUNCH_DISPLAY_ID, -1);
        this.mCallerDisplayId = bundle.getInt(KEY_CALLER_DISPLAY_ID, -1);
        this.mLaunchTaskDisplayArea = (WindowContainerToken) bundle.getParcelable(KEY_LAUNCH_TASK_DISPLAY_AREA_TOKEN, WindowContainerToken.class);
        this.mLaunchTaskDisplayAreaFeatureId = bundle.getInt(KEY_LAUNCH_TASK_DISPLAY_AREA_FEATURE_ID, -1);
        this.mLaunchRootTask = (WindowContainerToken) bundle.getParcelable(KEY_LAUNCH_ROOT_TASK_TOKEN, WindowContainerToken.class);
        this.mLaunchTaskFragmentToken = bundle.getBinder(KEY_LAUNCH_TASK_FRAGMENT_TOKEN);
        this.mLaunchWindowingMode = bundle.getInt(KEY_LAUNCH_WINDOWING_MODE, 0);
        this.mLaunchActivityType = bundle.getInt(KEY_LAUNCH_ACTIVITY_TYPE, 0);
        this.mLaunchTaskId = bundle.getInt(KEY_LAUNCH_TASK_ID, -1);
        this.mPendingIntentLaunchFlags = bundle.getInt(KEY_PENDING_INTENT_LAUNCH_FLAGS, 0);
        this.mTaskAlwaysOnTop = bundle.getBoolean(KEY_TASK_ALWAYS_ON_TOP, false);
        this.mTaskOverlay = bundle.getBoolean(KEY_TASK_OVERLAY, false);
        this.mTaskOverlayCanResume = bundle.getBoolean(KEY_TASK_OVERLAY_CAN_RESUME, false);
        this.mAvoidMoveToFront = bundle.getBoolean(KEY_AVOID_MOVE_TO_FRONT, false);
        this.mFreezeRecentTasksReordering = bundle.getBoolean(KEY_FREEZE_RECENT_TASKS_REORDERING, false);
        this.mDisallowEnterPictureInPictureWhileLaunching = bundle.getBoolean(KEY_DISALLOW_ENTER_PICTURE_IN_PICTURE_WHILE_LAUNCHING, false);
        this.mApplyActivityFlagsForBubbles = bundle.getBoolean(KEY_APPLY_ACTIVITY_FLAGS_FOR_BUBBLES, false);
        this.mApplyMultipleTaskFlagForShortcut = bundle.getBoolean(KEY_APPLY_MULTIPLE_TASK_FLAG_FOR_SHORTCUT, false);
        this.mApplyNoUserActionFlagForShortcut = bundle.getBoolean(KEY_APPLY_NO_USER_ACTION_FLAG_FOR_SHORTCUT, false);
        if (bundle.containsKey(KEY_ANIM_SPECS)) {
            Parcelable[] parcelableArray = bundle.getParcelableArray(KEY_ANIM_SPECS);
            this.mAnimSpecs = new AppTransitionAnimationSpec[parcelableArray.length];
            for (int length = parcelableArray.length - 1; length >= 0; length--) {
                this.mAnimSpecs[length] = (AppTransitionAnimationSpec) parcelableArray[length];
            }
        }
        if (bundle.containsKey(KEY_ANIMATION_FINISHED_LISTENER)) {
            this.mAnimationFinishedListener = IRemoteCallback.Stub.asInterface(bundle.getBinder(KEY_ANIMATION_FINISHED_LISTENER));
        }
        this.mSourceInfo = (SourceInfo) bundle.getParcelable(KEY_SOURCE_INFO, SourceInfo.class);
        this.mRotationAnimationHint = bundle.getInt(KEY_ROTATION_ANIMATION_HINT, -1);
        this.mAppVerificationBundle = bundle.getBundle(KEY_INSTANT_APP_VERIFICATION_BUNDLE);
        if (bundle.containsKey(KEY_SPECS_FUTURE)) {
            this.mSpecsFuture = IAppTransitionAnimationSpecsFuture.Stub.asInterface(bundle.getBinder(KEY_SPECS_FUTURE));
        }
        this.mIsActiveApplaunch = bundle.getBoolean(KEY_ACTIVE_LAUNCH_HINT, false);
        this.mIsMlLaunch = bundle.getInt(KEY_ML_LAUNCH_HINT, -1);
        this.mStartedByMDMAdmin = bundle.getBoolean(KEY_STARTED_BY_MDM_ADMIN, false);
        this.mRemoteAnimationAdapter = (RemoteAnimationAdapter) bundle.getParcelable(KEY_REMOTE_ANIMATION_ADAPTER, RemoteAnimationAdapter.class);
        this.mLaunchCookie = bundle.getBinder(KEY_LAUNCH_COOKIE);
        this.mRemoteTransition = (RemoteTransition) bundle.getParcelable(KEY_REMOTE_TRANSITION, RemoteTransition.class);
        this.mOverrideTaskTransition = bundle.getBoolean(KEY_OVERRIDE_TASK_TRANSITION);
        this.mSplashScreenThemeResName = bundle.getString(KEY_SPLASH_SCREEN_THEME);
        this.mRemoveWithTaskOrganizer = bundle.getBoolean(KEY_REMOVE_WITH_TASK_ORGANIZER);
        this.mLaunchedFromBubble = bundle.getBoolean(KEY_LAUNCHED_FROM_BUBBLE);
        this.mTransientLaunch = bundle.getBoolean(KEY_TRANSIENT_LAUNCH);
        this.mSplashScreenStyle = bundle.getInt(KEY_SPLASH_SCREEN_STYLE);
        this.mLaunchIntoPipParams = (PictureInPictureParams) bundle.getParcelable(KEY_LAUNCH_INTO_PIP_PARAMS, PictureInPictureParams.class);
        this.mIsEligibleForLegacyPermissionPrompt = bundle.getBoolean(KEY_LEGACY_PERMISSION_PROMPT_ELIGIBLE);
        this.mDismissKeyguardIfInsecure = bundle.getBoolean(KEY_DISMISS_KEYGUARD_IF_INSECURE);
        this.mPendingIntentCreatorBackgroundActivityStartMode = bundle.getInt(KEY_PENDING_INTENT_CREATOR_BACKGROUND_ACTIVITY_START_MODE, 0);
        this.mFlexibleLaunchSize = bundle.getBoolean(KEY_FLEXIBLE_LAUNCH_SIZE, false);
        this.mDisableStartingWindow = bundle.getBoolean(KEY_DISABLE_STARTING_WINDOW);
        this.mAllowPassThroughOnTouchOutside = bundle.getBoolean(KEY_ALLOW_PASS_THROUGH_ON_TOUCH_OUTSIDE);
        this.mAnimationAbortListener = IRemoteCallback.Stub.asInterface(bundle.getBinder(KEY_ANIM_ABORT_LISTENER));
        if (bundle.containsKey(KEY_POP_OVER_WIDTH) && bundle.containsKey(KEY_POP_OVER_HEIGHT) && bundle.containsKey(KEY_POP_OVER_ANCHOR) && bundle.containsKey(KEY_POP_OVER_ANCHOR_POSITION)) {
            this.mPopOverWidthDp = bundle.getIntArray(KEY_POP_OVER_WIDTH);
            this.mPopOverHeightDp = bundle.getIntArray(KEY_POP_OVER_HEIGHT);
            Parcelable[] parcelableArray2 = bundle.getParcelableArray(KEY_POP_OVER_ANCHOR);
            for (int i2 = 0; i2 < parcelableArray2.length; i2++) {
                this.mPopOverAnchorMarginDp[i2] = (Point) parcelableArray2[i2];
            }
            this.mPopOverAnchorPosition = bundle.getIntArray(KEY_POP_OVER_ANCHOR_POSITION);
            this.mIsPopOver = bundle.getBoolean(KEY_POP_OVER);
        } else if (bundle.containsKey(KEY_POP_OVER_INHERIT_OPTIONS)) {
            this.mPopOverInheritOptions = bundle.getBoolean(KEY_POP_OVER_INHERIT_OPTIONS);
        }
        if (bundle.containsKey(KEY_FORCE_LAUNCH_WINDOWING_MODE)) {
            this.mForceLaunchWindowingMode = bundle.getInt(KEY_FORCE_LAUNCH_WINDOWING_MODE, 0);
        }
        if (bundle.containsKey(KEY_SPLIT_TASK_DEFER_RESUME)) {
            this.mSplitTaskDeferResume = bundle.getBoolean(KEY_SPLIT_TASK_DEFER_RESUME);
        }
        if (bundle.containsKey(KEY_APPLY_BIG_FREEFORM_SIZE)) {
            this.mApplyBigFreeformSize = bundle.getBoolean(KEY_APPLY_BIG_FREEFORM_SIZE, true);
        }
        if (CoreRune.MW_EMBED_ACTIVITY) {
            this.mIsActivityEmbeddedPlaceholder = bundle.getBoolean(KEY_ACTIVITY_EMBEDDED_PLACEHOLDER, false);
        }
        if (bundle.containsKey(KEY_LAUNCHED_FROM_DND)) {
            this.mLaunchedFromDnD = bundle.getBoolean(KEY_LAUNCHED_FROM_DND);
        }
        if (bundle.containsKey(KEY_SPLIT_POSITION)) {
            this.mSplitPosition = bundle.getInt(KEY_SPLIT_POSITION);
        }
        if (bundle.containsKey(KEY_LAUNCH_IN_FOCUSED_STAGE_ROOT)) {
            this.mLaunchInFocusedStageRoot = bundle.getBoolean(KEY_LAUNCH_IN_FOCUSED_STAGE_ROOT);
        }
        this.mEnterSplitSideWithAdjacentFlag = bundle.getInt(KEY_ENTER_SPLIT_SIDE_WITH_ADJACENT_FLAG, 0);
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW && bundle.containsKey(KEY_PRESERVE_TASK_WINDOWING_MODE)) {
            this.mPreserveTaskWindowingMode = bundle.getBoolean(KEY_PRESERVE_TASK_WINDOWING_MODE, false);
        }
        if (bundle.containsKey(KEY_ALLOW_ENTER_PIP_WHILE_LAUNCHING)) {
            this.mAllowEnterPipWhileLaunching = bundle.getBoolean(KEY_ALLOW_ENTER_PIP_WHILE_LAUNCHING, false);
        }
        if (bundle.containsKey(KEY_STARTED_FROM_WINDOW_TYPE_LAUNCHER)) {
            this.mIsStartedFromWindowTypeLauncher = bundle.getBoolean(KEY_STARTED_FROM_WINDOW_TYPE_LAUNCHER);
        }
        if (CoreRune.FW_SHELL_TRANSITION_RESUMED_AFFORDANCE && bundle.containsKey(KEY_RESUMED_AFFORDANCE_ANIMATION_REQUESTED)) {
            this.mResumedAffordanceAnimationRequested = bundle.getBoolean(KEY_RESUMED_AFFORDANCE_ANIMATION_REQUESTED);
        }
        if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && bundle.containsKey(KEY_FORCE_LAUNCH_TASK_ON_HOME)) {
            this.mForceLaunchTaskOnHome = bundle.getBoolean(KEY_FORCE_LAUNCH_TASK_ON_HOME, false);
        }
        this.mDisableSplashScreen = bundle.getBoolean(KEY_DISABLE_SPLASH_SCREEN, false);
        this.mUnhandledDropLaunch = bundle.getBoolean(KEY_UNHANDLED_DROP_LAUNCH, false);
        if (CoreRune.FW_WORKAROUND_RESPONSE_SPEED) {
            this.mLaunchedFromHome = bundle.getBoolean(KEY_LAUNCHED_FROM_HOME, false);
        }
        if (bundle.containsKey(KEY_START_ASSISTANT_ACTIVITY)) {
            this.mStartAssistantActivity = bundle.getBoolean(KEY_START_ASSISTANT_ACTIVITY);
        }
    }

    public ActivityOptions setLaunchBounds(Rect rect) {
        this.mLaunchBounds = rect != null ? new Rect(rect) : null;
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

    public void abort() {
        sendResultIgnoreErrors(this.mAnimationStartedListener, null);
        sendResultIgnoreErrors(this.mAnimationAbortListener, null);
    }

    private void sendResultIgnoreErrors(IRemoteCallback iRemoteCallback, Bundle bundle) {
        if (iRemoteCallback != null) {
            try {
                iRemoteCallback.sendResult(bundle);
            } catch (RemoteException unused) {
            }
        }
    }

    public ActivityOptions setSceneTransitionInfo(SceneTransitionInfo sceneTransitionInfo) {
        this.mAnimationType = 5;
        this.mSceneTransitionInfo = sceneTransitionInfo;
        return this;
    }

    public SceneTransitionInfo getSceneTransitionInfo() {
        return this.mSceneTransitionInfo;
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

    public ActivityOptions setRemoteTransition(RemoteTransition remoteTransition) {
        this.mRemoteTransition = remoteTransition;
        return this;
    }

    public static ActivityOptions fromBundle(Bundle bundle) {
        if (bundle != null) {
            return new ActivityOptions(bundle);
        }
        return null;
    }

    public static void abort(ActivityOptions activityOptions) {
        if (activityOptions != null) {
            activityOptions.abort();
        }
    }

    public static boolean hasLaunchTargetContainer(ActivityOptions activityOptions) {
        return (activityOptions.getLaunchDisplayId() == -1 && activityOptions.getLaunchTaskDisplayArea() == null && activityOptions.getLaunchTaskDisplayAreaFeatureId() == -1 && activityOptions.getLaunchRootTask() == null && activityOptions.getLaunchTaskId() == -1 && activityOptions.getLaunchTaskFragmentToken() == null) ? false : true;
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

    public ActivityOptions setSplashScreenStyle(int i) {
        if (i != 1 && i != 0) {
            return this;
        }
        this.mSplashScreenStyle = i;
        return this;
    }

    public boolean isEligibleForLegacyPermissionPrompt() {
        return this.mIsEligibleForLegacyPermissionPrompt;
    }

    public void setEligibleForLegacyPermissionPrompt(boolean z) {
        this.mIsEligibleForLegacyPermissionPrompt = z;
    }

    public ActivityOptions setLockTaskEnabled(boolean z) {
        this.mLockTaskMode = z;
        return this;
    }

    public ActivityOptions setShareIdentityEnabled(boolean z) {
        this.mShareIdentity = z;
        return this;
    }

    public int getLaunchDisplayId() {
        return this.mLaunchDisplayId;
    }

    public ActivityOptions setLaunchDisplayId(int i) {
        this.mLaunchDisplayId = i;
        return this;
    }

    public int getCallerDisplayId() {
        return this.mCallerDisplayId;
    }

    public ActivityOptions setCallerDisplayId(int i) {
        this.mCallerDisplayId = i;
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

    public void setLaunchTaskDisplayAreaFeatureId(int i) {
        this.mLaunchTaskDisplayAreaFeatureId = i;
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

    public ActivityOptions setLaunchTaskFragmentToken(IBinder iBinder) {
        this.mLaunchTaskFragmentToken = iBinder;
        return this;
    }

    public int getLaunchWindowingMode() {
        return this.mLaunchWindowingMode;
    }

    public void setLaunchWindowingMode(int i) {
        this.mLaunchWindowingMode = i;
    }

    public void preserveTaskWindowingMode() {
        this.mPreserveTaskWindowingMode = true;
    }

    public int getForceLaunchWindowingMode() {
        return this.mForceLaunchWindowingMode;
    }

    public void setForceLaunchWindowingMode(int i) {
        this.mForceLaunchWindowingMode = i;
    }

    public PictureInPictureParams getLaunchIntoPipParams() {
        return this.mLaunchIntoPipParams;
    }

    public boolean isLaunchIntoPip() {
        PictureInPictureParams pictureInPictureParams = this.mLaunchIntoPipParams;
        return pictureInPictureParams != null && pictureInPictureParams.isLaunchIntoPip();
    }

    public boolean isAllowPassThroughOnTouchOutside() {
        return this.mAllowPassThroughOnTouchOutside;
    }

    public void setAllowPassThroughOnTouchOutside(boolean z) {
        this.mAllowPassThroughOnTouchOutside = z;
    }

    public int getLaunchActivityType() {
        return this.mLaunchActivityType;
    }

    public void setLaunchActivityType(int i) {
        this.mLaunchActivityType = i;
    }

    @SystemApi
    public void setLaunchTaskId(int i) {
        this.mLaunchTaskId = i;
    }

    @SystemApi
    public int getLaunchTaskId() {
        return this.mLaunchTaskId;
    }

    public void setDisableStartingWindow(boolean z) {
        this.mDisableStartingWindow = z;
    }

    public boolean getDisableStartingWindow() {
        return this.mDisableStartingWindow;
    }

    public boolean isActiveApplaunch() {
        return this.mIsActiveApplaunch;
    }

    public void setActiveApplaunch(boolean z) {
        this.mIsActiveApplaunch = z;
    }

    public int isMlLaunch() {
        return this.mIsMlLaunch;
    }

    public void setMlLaunch(int i) {
        this.mIsMlLaunch = i;
    }

    public void setPendingIntentLaunchFlags(int i) {
        this.mPendingIntentLaunchFlags = i;
    }

    public int getPendingIntentLaunchFlags() {
        return this.mPendingIntentLaunchFlags & 402653184;
    }

    public void setTaskAlwaysOnTop(boolean z) {
        this.mTaskAlwaysOnTop = z;
    }

    public boolean getTaskAlwaysOnTop() {
        return this.mTaskAlwaysOnTop;
    }

    public void setTaskOverlay(boolean z, boolean z2) {
        this.mTaskOverlay = z;
        this.mTaskOverlayCanResume = z2;
    }

    public boolean getTaskOverlay() {
        return this.mTaskOverlay;
    }

    public boolean canTaskOverlayResume() {
        return this.mTaskOverlayCanResume;
    }

    public void setAvoidMoveToFront() {
        this.mAvoidMoveToFront = true;
        Slog.d(TAG, "setAvoidMoveToFront is called, package=" + ActivityThread.currentPackageName() + ", caller=" + Debug.getCallers(5));
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

    public void setDisallowEnterPictureInPictureWhileLaunching(boolean z) {
        this.mDisallowEnterPictureInPictureWhileLaunching = z;
    }

    public boolean getStartedByMDMAdmin() {
        return this.mStartedByMDMAdmin;
    }

    public boolean disallowEnterPictureInPictureWhileLaunching() {
        return this.mDisallowEnterPictureInPictureWhileLaunching;
    }

    public void setApplyActivityFlagsForBubbles(boolean z) {
        this.mApplyActivityFlagsForBubbles = z;
    }

    public boolean isApplyActivityFlagsForBubbles() {
        return this.mApplyActivityFlagsForBubbles;
    }

    public void setApplyMultipleTaskFlagForShortcut(boolean z) {
        this.mApplyMultipleTaskFlagForShortcut = z;
    }

    public boolean isApplyMultipleTaskFlagForShortcut() {
        return this.mApplyMultipleTaskFlagForShortcut;
    }

    public void setApplyNoUserActionFlagForShortcut(boolean z) {
        this.mApplyNoUserActionFlagForShortcut = z;
    }

    public boolean isApplyNoUserActionFlagForShortcut() {
        return this.mApplyNoUserActionFlagForShortcut;
    }

    public static final class LaunchCookie implements Parcelable {
        public static final Parcelable.Creator<LaunchCookie> CREATOR = new Parcelable.Creator<LaunchCookie>() { // from class: android.app.ActivityOptions.LaunchCookie.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LaunchCookie createFromParcel(Parcel parcel) {
                return LaunchCookie.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LaunchCookie[] newArray(int i) {
                return new LaunchCookie[i];
            }
        };
        public final IBinder binder;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public LaunchCookie() {
            this.binder = new Binder();
        }

        public LaunchCookie(String str) {
            this.binder = new Binder(str);
        }

        private LaunchCookie(IBinder iBinder) {
            this.binder = iBinder;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeStrongBinder(this.binder);
        }

        public static LaunchCookie readFromParcel(Parcel parcel) {
            IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder == null) {
                return null;
            }
            return new LaunchCookie(readStrongBinder);
        }

        public static void writeToParcel(LaunchCookie launchCookie, Parcel parcel) {
            if (launchCookie != null) {
                launchCookie.writeToParcel(parcel, 0);
            } else {
                parcel.writeStrongBinder(null);
            }
        }

        public boolean equals(Object obj) {
            return (obj instanceof LaunchCookie) && this.binder == ((LaunchCookie) obj).binder;
        }

        public int hashCode() {
            return this.binder.hashCode();
        }
    }

    public void setLaunchCookie(LaunchCookie launchCookie) {
        setLaunchCookie(launchCookie.binder);
    }

    public void setLaunchCookie(IBinder iBinder) {
        this.mLaunchCookie = iBinder;
    }

    public IBinder getLaunchCookie() {
        return this.mLaunchCookie;
    }

    public ActivityOptions setOverrideTaskTransition(boolean z) {
        this.mOverrideTaskTransition = z;
        return this;
    }

    public boolean getOverrideTaskTransition() {
        return this.mOverrideTaskTransition;
    }

    public void setRemoveWithTaskOrganizer(boolean z) {
        this.mRemoveWithTaskOrganizer = z;
    }

    public boolean getRemoveWithTaskOranizer() {
        return this.mRemoveWithTaskOrganizer;
    }

    public void setLaunchedFromBubble(boolean z) {
        this.mLaunchedFromBubble = z;
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

    public void setDismissKeyguardIfInsecure() {
        this.mDismissKeyguardIfInsecure = true;
    }

    public boolean getDismissKeyguardIfInsecure() {
        return this.mDismissKeyguardIfInsecure;
    }

    public ActivityOptions setPendingIntentCreatorBackgroundActivityStartMode(int i) {
        this.mPendingIntentCreatorBackgroundActivityStartMode = i;
        return this;
    }

    public int getPendingIntentCreatorBackgroundActivityStartMode() {
        return this.mPendingIntentCreatorBackgroundActivityStartMode;
    }

    public ActivityOptions setFlexibleLaunchSize(boolean z) {
        this.mFlexibleLaunchSize = z;
        return this;
    }

    public boolean getFlexibleLaunchSize() {
        return this.mFlexibleLaunchSize;
    }

    public int getSplitPosition() {
        return this.mSplitPosition;
    }

    public void setSplitPosition(int i) {
        this.mSplitPosition = i;
    }

    public boolean getLaunchInFocusedStageRoot() {
        return this.mLaunchInFocusedStageRoot;
    }

    public void setLaunchInFocusedStageRoot(boolean z) {
        this.mLaunchInFocusedStageRoot = z;
    }

    public boolean getLaunchedFromDnD() {
        return this.mLaunchedFromDnD;
    }

    public void setLaunchedFromDnD(boolean z) {
        this.mLaunchedFromDnD = z;
    }

    public boolean getSplitTaskDeferResume() {
        return this.mSplitTaskDeferResume;
    }

    public void setStartAssistantActivity(boolean z) {
        this.mStartAssistantActivity = z;
    }

    public boolean getStartAssistantActivity() {
        return this.mStartAssistantActivity;
    }

    public void setApplyBigFreeformSize(boolean z) {
        this.mApplyBigFreeformSize = z;
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

    public void update(ActivityOptions activityOptions) {
        String str = activityOptions.mPackageName;
        if (str != null) {
            this.mPackageName = str;
        }
        this.mUsageTimeReport = activityOptions.mUsageTimeReport;
        this.mSceneTransitionInfo = null;
        this.mAnimationType = activityOptions.mAnimationType;
        switch (activityOptions.mAnimationType) {
            case 1:
                this.mCustomEnterResId = activityOptions.mCustomEnterResId;
                this.mCustomExitResId = activityOptions.mCustomExitResId;
                this.mCustomBackgroundColor = activityOptions.mCustomBackgroundColor;
                this.mThumbnail = null;
                sendResultIgnoreErrors(this.mAnimationStartedListener, null);
                this.mAnimationStartedListener = activityOptions.mAnimationStartedListener;
                break;
            case 2:
                this.mStartX = activityOptions.mStartX;
                this.mStartY = activityOptions.mStartY;
                this.mWidth = activityOptions.mWidth;
                this.mHeight = activityOptions.mHeight;
                sendResultIgnoreErrors(this.mAnimationStartedListener, null);
                this.mAnimationStartedListener = null;
                break;
            case 3:
            case 4:
            case 8:
            case 9:
                this.mThumbnail = activityOptions.mThumbnail;
                this.mStartX = activityOptions.mStartX;
                this.mStartY = activityOptions.mStartY;
                this.mWidth = activityOptions.mWidth;
                this.mHeight = activityOptions.mHeight;
                sendResultIgnoreErrors(this.mAnimationStartedListener, null);
                this.mAnimationStartedListener = activityOptions.mAnimationStartedListener;
                break;
            case 5:
                this.mSceneTransitionInfo = activityOptions.mSceneTransitionInfo;
                this.mThumbnail = null;
                this.mAnimationStartedListener = null;
                break;
            case 10:
                this.mCustomInPlaceResId = activityOptions.mCustomInPlaceResId;
                break;
        }
        this.mLockTaskMode = activityOptions.mLockTaskMode;
        this.mShareIdentity = activityOptions.mShareIdentity;
        this.mAnimSpecs = activityOptions.mAnimSpecs;
        this.mAnimationFinishedListener = activityOptions.mAnimationFinishedListener;
        this.mSpecsFuture = activityOptions.mSpecsFuture;
        this.mRemoteAnimationAdapter = activityOptions.mRemoteAnimationAdapter;
        this.mLaunchIntoPipParams = activityOptions.mLaunchIntoPipParams;
        this.mIsEligibleForLegacyPermissionPrompt = activityOptions.mIsEligibleForLegacyPermissionPrompt;
        sendResultIgnoreErrors(this.mAnimationAbortListener, null);
        this.mAnimationAbortListener = activityOptions.mAnimationAbortListener;
    }

    @Override // android.app.ComponentOptions
    public Bundle toBundle() {
        Bundle bundle = super.toBundle();
        String str = this.mPackageName;
        if (str != null) {
            bundle.putString(KEY_PACKAGE_NAME, str);
        }
        Rect rect = this.mLaunchBounds;
        if (rect != null) {
            bundle.putParcelable(KEY_LAUNCH_BOUNDS, rect);
        }
        int i = this.mAnimationType;
        if (i != -1) {
            bundle.putInt(KEY_ANIM_TYPE, i);
        }
        PendingIntent pendingIntent = this.mUsageTimeReport;
        if (pendingIntent != null) {
            bundle.putParcelable(KEY_USAGE_TIME_REPORT, pendingIntent);
        }
        switch (this.mAnimationType) {
            case 1:
                bundle.putInt(KEY_ANIM_ENTER_RES_ID, this.mCustomEnterResId);
                bundle.putInt(KEY_ANIM_EXIT_RES_ID, this.mCustomExitResId);
                bundle.putInt(KEY_ANIM_BACKGROUND_COLOR, this.mCustomBackgroundColor);
                IRemoteCallback iRemoteCallback = this.mAnimationStartedListener;
                bundle.putBinder(KEY_ANIM_START_LISTENER, iRemoteCallback != null ? iRemoteCallback.asBinder() : null);
                break;
            case 2:
            case 11:
                bundle.putInt(KEY_ANIM_START_X, this.mStartX);
                bundle.putInt(KEY_ANIM_START_Y, this.mStartY);
                bundle.putInt(KEY_ANIM_WIDTH, this.mWidth);
                bundle.putInt(KEY_ANIM_HEIGHT, this.mHeight);
                break;
            case 3:
            case 4:
            case 8:
            case 9:
                Bitmap bitmap = this.mThumbnail;
                if (bitmap != null) {
                    Bitmap copy = bitmap.copy(Bitmap.Config.HARDWARE, false);
                    if (copy != null) {
                        bundle.putParcelable(KEY_ANIM_THUMBNAIL, copy.getHardwareBuffer());
                    } else {
                        Slog.w(TAG, "Failed to copy thumbnail");
                    }
                }
                bundle.putInt(KEY_ANIM_START_X, this.mStartX);
                bundle.putInt(KEY_ANIM_START_Y, this.mStartY);
                bundle.putInt(KEY_ANIM_WIDTH, this.mWidth);
                bundle.putInt(KEY_ANIM_HEIGHT, this.mHeight);
                IRemoteCallback iRemoteCallback2 = this.mAnimationStartedListener;
                bundle.putBinder(KEY_ANIM_START_LISTENER, iRemoteCallback2 != null ? iRemoteCallback2.asBinder() : null);
                break;
            case 5:
                SceneTransitionInfo sceneTransitionInfo = this.mSceneTransitionInfo;
                if (sceneTransitionInfo != null) {
                    bundle.putParcelable(KEY_SCENE_TRANSITION_INFO, sceneTransitionInfo);
                    break;
                }
                break;
            case 10:
                bundle.putInt(KEY_ANIM_IN_PLACE_RES_ID, this.mCustomInPlaceResId);
                break;
        }
        boolean z = this.mLockTaskMode;
        if (z) {
            bundle.putBoolean(KEY_LOCK_TASK_MODE, z);
        }
        boolean z2 = this.mShareIdentity;
        if (z2) {
            bundle.putBoolean(KEY_SHARE_IDENTITY, z2);
        }
        int i2 = this.mLaunchDisplayId;
        if (i2 != -1) {
            bundle.putInt(KEY_LAUNCH_DISPLAY_ID, i2);
        }
        int i3 = this.mCallerDisplayId;
        if (i3 != -1) {
            bundle.putInt(KEY_CALLER_DISPLAY_ID, i3);
        }
        WindowContainerToken windowContainerToken = this.mLaunchTaskDisplayArea;
        if (windowContainerToken != null) {
            bundle.putParcelable(KEY_LAUNCH_TASK_DISPLAY_AREA_TOKEN, windowContainerToken);
        }
        int i4 = this.mLaunchTaskDisplayAreaFeatureId;
        if (i4 != -1) {
            bundle.putInt(KEY_LAUNCH_TASK_DISPLAY_AREA_FEATURE_ID, i4);
        }
        WindowContainerToken windowContainerToken2 = this.mLaunchRootTask;
        if (windowContainerToken2 != null) {
            bundle.putParcelable(KEY_LAUNCH_ROOT_TASK_TOKEN, windowContainerToken2);
        }
        IBinder iBinder = this.mLaunchTaskFragmentToken;
        if (iBinder != null) {
            bundle.putBinder(KEY_LAUNCH_TASK_FRAGMENT_TOKEN, iBinder);
        }
        int i5 = this.mLaunchWindowingMode;
        if (i5 != 0) {
            bundle.putInt(KEY_LAUNCH_WINDOWING_MODE, i5);
        }
        int i6 = this.mLaunchActivityType;
        if (i6 != 0) {
            bundle.putInt(KEY_LAUNCH_ACTIVITY_TYPE, i6);
        }
        int i7 = this.mLaunchTaskId;
        if (i7 != -1) {
            bundle.putInt(KEY_LAUNCH_TASK_ID, i7);
        }
        int i8 = this.mPendingIntentLaunchFlags;
        if (i8 != 0) {
            bundle.putInt(KEY_PENDING_INTENT_LAUNCH_FLAGS, i8);
        }
        boolean z3 = this.mTaskAlwaysOnTop;
        if (z3) {
            bundle.putBoolean(KEY_TASK_ALWAYS_ON_TOP, z3);
        }
        boolean z4 = this.mTaskOverlay;
        if (z4) {
            bundle.putBoolean(KEY_TASK_OVERLAY, z4);
        }
        boolean z5 = this.mTaskOverlayCanResume;
        if (z5) {
            bundle.putBoolean(KEY_TASK_OVERLAY_CAN_RESUME, z5);
        }
        boolean z6 = this.mAvoidMoveToFront;
        if (z6) {
            bundle.putBoolean(KEY_AVOID_MOVE_TO_FRONT, z6);
        }
        boolean z7 = this.mFreezeRecentTasksReordering;
        if (z7) {
            bundle.putBoolean(KEY_FREEZE_RECENT_TASKS_REORDERING, z7);
        }
        boolean z8 = this.mDisallowEnterPictureInPictureWhileLaunching;
        if (z8) {
            bundle.putBoolean(KEY_DISALLOW_ENTER_PICTURE_IN_PICTURE_WHILE_LAUNCHING, z8);
        }
        boolean z9 = this.mApplyActivityFlagsForBubbles;
        if (z9) {
            bundle.putBoolean(KEY_APPLY_ACTIVITY_FLAGS_FOR_BUBBLES, z9);
        }
        boolean z10 = this.mApplyMultipleTaskFlagForShortcut;
        if (z10) {
            bundle.putBoolean(KEY_APPLY_MULTIPLE_TASK_FLAG_FOR_SHORTCUT, z10);
        }
        if (this.mApplyNoUserActionFlagForShortcut) {
            bundle.putBoolean(KEY_APPLY_NO_USER_ACTION_FLAG_FOR_SHORTCUT, true);
        }
        AppTransitionAnimationSpec[] appTransitionAnimationSpecArr = this.mAnimSpecs;
        if (appTransitionAnimationSpecArr != null) {
            bundle.putParcelableArray(KEY_ANIM_SPECS, appTransitionAnimationSpecArr);
        }
        IRemoteCallback iRemoteCallback3 = this.mAnimationFinishedListener;
        if (iRemoteCallback3 != null) {
            bundle.putBinder(KEY_ANIMATION_FINISHED_LISTENER, iRemoteCallback3.asBinder());
        }
        IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture = this.mSpecsFuture;
        if (iAppTransitionAnimationSpecsFuture != null) {
            bundle.putBinder(KEY_SPECS_FUTURE, iAppTransitionAnimationSpecsFuture.asBinder());
        }
        SourceInfo sourceInfo = this.mSourceInfo;
        if (sourceInfo != null) {
            bundle.putParcelable(KEY_SOURCE_INFO, sourceInfo);
        }
        int i9 = this.mRotationAnimationHint;
        if (i9 != -1) {
            bundle.putInt(KEY_ROTATION_ANIMATION_HINT, i9);
        }
        bundle.putBoolean(KEY_ACTIVE_LAUNCH_HINT, this.mIsActiveApplaunch);
        bundle.putInt(KEY_ML_LAUNCH_HINT, this.mIsMlLaunch);
        Bundle bundle2 = this.mAppVerificationBundle;
        if (bundle2 != null) {
            bundle.putBundle(KEY_INSTANT_APP_VERIFICATION_BUNDLE, bundle2);
        }
        RemoteAnimationAdapter remoteAnimationAdapter = this.mRemoteAnimationAdapter;
        if (remoteAnimationAdapter != null) {
            bundle.putParcelable(KEY_REMOTE_ANIMATION_ADAPTER, remoteAnimationAdapter);
        }
        IBinder iBinder2 = this.mLaunchCookie;
        if (iBinder2 != null) {
            bundle.putBinder(KEY_LAUNCH_COOKIE, iBinder2);
        }
        RemoteTransition remoteTransition = this.mRemoteTransition;
        if (remoteTransition != null) {
            bundle.putParcelable(KEY_REMOTE_TRANSITION, remoteTransition);
        }
        boolean z11 = this.mOverrideTaskTransition;
        if (z11) {
            bundle.putBoolean(KEY_OVERRIDE_TASK_TRANSITION, z11);
        }
        String str2 = this.mSplashScreenThemeResName;
        if (str2 != null && !str2.isEmpty()) {
            bundle.putString(KEY_SPLASH_SCREEN_THEME, this.mSplashScreenThemeResName);
        }
        boolean z12 = this.mRemoveWithTaskOrganizer;
        if (z12) {
            bundle.putBoolean(KEY_REMOVE_WITH_TASK_ORGANIZER, z12);
        }
        boolean z13 = this.mLaunchedFromBubble;
        if (z13) {
            bundle.putBoolean(KEY_LAUNCHED_FROM_BUBBLE, z13);
        }
        boolean z14 = this.mTransientLaunch;
        if (z14) {
            bundle.putBoolean(KEY_TRANSIENT_LAUNCH, z14);
        }
        int i10 = this.mSplashScreenStyle;
        if (i10 != 0) {
            bundle.putInt(KEY_SPLASH_SCREEN_STYLE, i10);
        }
        PictureInPictureParams pictureInPictureParams = this.mLaunchIntoPipParams;
        if (pictureInPictureParams != null) {
            bundle.putParcelable(KEY_LAUNCH_INTO_PIP_PARAMS, pictureInPictureParams);
        }
        boolean z15 = this.mIsEligibleForLegacyPermissionPrompt;
        if (z15) {
            bundle.putBoolean(KEY_LEGACY_PERMISSION_PROMPT_ELIGIBLE, z15);
        }
        boolean z16 = this.mDismissKeyguardIfInsecure;
        if (z16) {
            bundle.putBoolean(KEY_DISMISS_KEYGUARD_IF_INSECURE, z16);
        }
        int i11 = this.mPendingIntentCreatorBackgroundActivityStartMode;
        if (i11 != 0) {
            bundle.putInt(KEY_PENDING_INTENT_CREATOR_BACKGROUND_ACTIVITY_START_MODE, i11);
        }
        boolean z17 = this.mFlexibleLaunchSize;
        if (z17) {
            bundle.putBoolean(KEY_FLEXIBLE_LAUNCH_SIZE, z17);
        }
        boolean z18 = this.mDisableStartingWindow;
        if (z18) {
            bundle.putBoolean(KEY_DISABLE_STARTING_WINDOW, z18);
        }
        boolean z19 = this.mAllowPassThroughOnTouchOutside;
        if (z19) {
            bundle.putBoolean(KEY_ALLOW_PASS_THROUGH_ON_TOUCH_OUTSIDE, z19);
        }
        IRemoteCallback iRemoteCallback4 = this.mAnimationAbortListener;
        bundle.putBinder(KEY_ANIM_ABORT_LISTENER, iRemoteCallback4 != null ? iRemoteCallback4.asBinder() : null);
        if (this.mIsPopOver) {
            bundle.putIntArray(KEY_POP_OVER_WIDTH, this.mPopOverWidthDp);
            bundle.putIntArray(KEY_POP_OVER_HEIGHT, this.mPopOverHeightDp);
            bundle.putParcelableArray(KEY_POP_OVER_ANCHOR, this.mPopOverAnchorMarginDp);
            bundle.putIntArray(KEY_POP_OVER_ANCHOR_POSITION, this.mPopOverAnchorPosition);
            bundle.putBoolean(KEY_POP_OVER, this.mIsPopOver);
        } else {
            boolean z20 = this.mPopOverInheritOptions;
            if (!z20) {
                bundle.putBoolean(KEY_POP_OVER_INHERIT_OPTIONS, z20);
            }
        }
        int i12 = this.mForceLaunchWindowingMode;
        if (i12 != 0) {
            bundle.putInt(KEY_FORCE_LAUNCH_WINDOWING_MODE, i12);
        }
        bundle.putBoolean(KEY_SPLIT_TASK_DEFER_RESUME, this.mSplitTaskDeferResume);
        bundle.putBoolean(KEY_APPLY_BIG_FREEFORM_SIZE, this.mApplyBigFreeformSize);
        if (CoreRune.MW_EMBED_ACTIVITY) {
            bundle.putBoolean(KEY_ACTIVITY_EMBEDDED_PLACEHOLDER, this.mIsActivityEmbeddedPlaceholder);
        }
        bundle.putBoolean(KEY_LAUNCHED_FROM_DND, this.mLaunchedFromDnD);
        bundle.putInt(KEY_SPLIT_POSITION, this.mSplitPosition);
        bundle.putBoolean(KEY_LAUNCH_IN_FOCUSED_STAGE_ROOT, this.mLaunchInFocusedStageRoot);
        bundle.putInt(KEY_ENTER_SPLIT_SIDE_WITH_ADJACENT_FLAG, this.mEnterSplitSideWithAdjacentFlag);
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
            bundle.putBoolean(KEY_PRESERVE_TASK_WINDOWING_MODE, this.mPreserveTaskWindowingMode);
        }
        bundle.putBoolean(KEY_ALLOW_ENTER_PIP_WHILE_LAUNCHING, this.mAllowEnterPipWhileLaunching);
        bundle.putBoolean(KEY_STARTED_FROM_WINDOW_TYPE_LAUNCHER, this.mIsStartedFromWindowTypeLauncher);
        if (CoreRune.FW_SHELL_TRANSITION_RESUMED_AFFORDANCE && this.mResumedAffordanceAnimationRequested) {
            bundle.putBoolean(KEY_RESUMED_AFFORDANCE_ANIMATION_REQUESTED, true);
        }
        if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY) {
            bundle.putBoolean(KEY_FORCE_LAUNCH_TASK_ON_HOME, this.mForceLaunchTaskOnHome);
        }
        bundle.putBoolean(KEY_DISABLE_SPLASH_SCREEN, this.mDisableSplashScreen);
        bundle.putBoolean(KEY_UNHANDLED_DROP_LAUNCH, this.mUnhandledDropLaunch);
        if (CoreRune.FW_WORKAROUND_RESPONSE_SPEED) {
            bundle.putBoolean(KEY_LAUNCHED_FROM_HOME, this.mLaunchedFromHome);
        }
        bundle.putBoolean(KEY_START_ASSISTANT_ACTIVITY, this.mStartAssistantActivity);
        return bundle;
    }

    public void requestUsageTimeReport(PendingIntent pendingIntent) {
        this.mUsageTimeReport = pendingIntent;
    }

    public SourceInfo getSourceInfo() {
        return this.mSourceInfo;
    }

    public void setSourceInfo(int i, long j) {
        this.mSourceInfo = new SourceInfo(i, j);
    }

    public ActivityOptions forTargetActivity() {
        if (this.mAnimationType != 5) {
            return null;
        }
        ActivityOptions activityOptions = new ActivityOptions();
        activityOptions.update(this);
        return activityOptions;
    }

    public int getRotationAnimationHint() {
        return this.mRotationAnimationHint;
    }

    public void setRotationAnimationHint(int i) {
        this.mRotationAnimationHint = i;
    }

    public Bundle popAppVerificationBundle() {
        Bundle bundle = this.mAppVerificationBundle;
        this.mAppVerificationBundle = null;
        return bundle;
    }

    public ActivityOptions setAppVerificationBundle(Bundle bundle) {
        this.mAppVerificationBundle = bundle;
        return this;
    }

    public void setAllowEnterPipWhileLaunching(boolean z) {
        this.mAllowEnterPipWhileLaunching = z;
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

    public void setStartedFromWindowTypeLauncher(boolean z) {
        this.mIsStartedFromWindowTypeLauncher = z;
    }

    public boolean isStartedFromWindowTypeLauncher() {
        return this.mIsStartedFromWindowTypeLauncher;
    }

    @Override // android.app.ComponentOptions
    public ActivityOptions setPendingIntentBackgroundActivityStartMode(int i) {
        super.setPendingIntentBackgroundActivityStartMode(i);
        return this;
    }

    @Override // android.app.ComponentOptions
    public int getPendingIntentBackgroundActivityStartMode() {
        return super.getPendingIntentBackgroundActivityStartMode();
    }

    @Override // android.app.ComponentOptions
    @Deprecated
    public void setPendingIntentBackgroundActivityLaunchAllowed(boolean z) {
        super.setPendingIntentBackgroundActivityLaunchAllowed(z);
    }

    @Override // android.app.ComponentOptions
    @Deprecated
    public boolean isPendingIntentBackgroundActivityLaunchAllowed() {
        return super.isPendingIntentBackgroundActivityLaunchAllowed();
    }

    public String toString() {
        return "ActivityOptions(" + hashCode() + "), mPackageName=" + this.mPackageName + ", mAnimationType=" + this.mAnimationType + ", mStartX=" + this.mStartX + ", mStartY=" + this.mStartY + ", mWidth=" + this.mWidth + ", mHeight=" + this.mHeight + ", mLaunchDisplayId=" + this.mLaunchDisplayId;
    }

    public static class SourceInfo implements Parcelable {
        public static final Parcelable.Creator<SourceInfo> CREATOR = new Parcelable.Creator<SourceInfo>() { // from class: android.app.ActivityOptions.SourceInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SourceInfo createFromParcel(Parcel parcel) {
                return new SourceInfo(parcel.readInt(), parcel.readLong());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SourceInfo[] newArray(int i) {
                return new SourceInfo[i];
            }
        };
        public static final int TYPE_DESKTOP_ANIMATION = 5;
        public static final int TYPE_LAUNCHER = 1;
        public static final int TYPE_LOCKSCREEN = 3;
        public static final int TYPE_NOTIFICATION = 2;
        public static final int TYPE_RECENTS_ANIMATION = 4;
        public final long eventTimeMs;
        public final int type;

        @Retention(RetentionPolicy.SOURCE)
        public @interface SourceType {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        SourceInfo(int i, long j) {
            this.type = i;
            this.eventTimeMs = j;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.type);
            parcel.writeLong(this.eventTimeMs);
        }
    }

    public static class SceneTransitionInfo implements Parcelable {
        public static final Parcelable.Creator<SceneTransitionInfo> CREATOR = new Parcelable.Creator<SceneTransitionInfo>() { // from class: android.app.ActivityOptions.SceneTransitionInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SceneTransitionInfo createFromParcel(Parcel parcel) {
                return new SceneTransitionInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SceneTransitionInfo[] newArray(int i) {
                return new SceneTransitionInfo[i];
            }
        };
        private int mExitCoordinatorIndex;
        private boolean mIsReturning;
        private int mResultCode;
        private Intent mResultData;
        private ResultReceiver mResultReceiver;
        private ArrayList<String> mSharedElementNames;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public SceneTransitionInfo() {
        }

        SceneTransitionInfo(Parcel parcel) {
            this.mIsReturning = parcel.readBoolean();
            this.mResultCode = parcel.readInt();
            this.mResultData = (Intent) parcel.readTypedObject(Intent.CREATOR);
            this.mSharedElementNames = parcel.createStringArrayList();
            this.mResultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
            this.mExitCoordinatorIndex = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeBoolean(this.mIsReturning);
            parcel.writeInt(this.mResultCode);
            parcel.writeTypedObject(this.mResultData, i);
            parcel.writeStringList(this.mSharedElementNames);
            parcel.writeTypedObject(this.mResultReceiver, i);
            parcel.writeInt(this.mExitCoordinatorIndex);
        }

        public void setReturning(boolean z) {
            this.mIsReturning = z;
        }

        public boolean isReturning() {
            return this.mIsReturning;
        }

        public void setResultCode(int i) {
            this.mResultCode = i;
        }

        public int getResultCode() {
            return this.mResultCode;
        }

        public void setResultData(Intent intent) {
            this.mResultData = intent;
        }

        public Intent getResultData() {
            return this.mResultData;
        }

        public void setSharedElementNames(ArrayList<String> arrayList) {
            this.mSharedElementNames = arrayList;
        }

        public ArrayList<String> getSharedElementNames() {
            return this.mSharedElementNames;
        }

        public void setResultReceiver(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }

        public ResultReceiver getResultReceiver() {
            return this.mResultReceiver;
        }

        public void setExitCoordinatorKey(int i) {
            this.mExitCoordinatorIndex = i;
        }

        public int getExitCoordinatorKey() {
            return this.mExitCoordinatorIndex;
        }

        boolean isCrossTask() {
            return this.mExitCoordinatorIndex < 0;
        }

        public String toString() {
            return "SceneTransitionInfo, mIsReturning=" + this.mIsReturning + ", mResultCode=" + this.mResultCode + ", mResultData=" + this.mResultData + ", mSharedElementNames=" + this.mSharedElementNames + ", mTransitionReceiver=" + this.mResultReceiver + ", mExitCoordinatorIndex=" + this.mExitCoordinatorIndex;
        }
    }

    public ActivityOptions semSetPopOverOptions(int[] iArr, int[] iArr2, Point[] pointArr, int[] iArr3) {
        return setPopOverOptions(iArr, iArr2, pointArr, iArr3);
    }

    public ActivityOptions semSetChooserPopOverPosition(int i) {
        int i2 = i & 7;
        if (i2 != 0 && (i & 112) != 0) {
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
            iArr3[1] = i;
            iArr3[0] = i;
            if (i2 == 2) {
                this.mPopOverAnchorMarginDp[0].y = 44;
            }
            this.mIsPopOver = true;
        }
        return this;
    }

    public boolean isPopOver() {
        return this.mIsPopOver;
    }

    public ActivityOptions setPopOverOptions(int[] iArr, int[] iArr2, Point[] pointArr, int[] iArr3) {
        if (iArr == null && iArr2 == null && pointArr == null && iArr3 == null) {
            this.mPopOverInheritOptions = false;
            this.mIsPopOver = false;
            return this;
        }
        if (iArr != null && iArr.length == 2 && iArr2 != null && iArr2.length == 2 && pointArr != null && pointArr.length == 2 && iArr3 != null && iArr3.length == 2) {
            int i = 0;
            while (true) {
                if (i < 2) {
                    int i2 = iArr3[i];
                    if ((i2 & 7) == 0 || (i2 & 112) == 0 || iArr[i] <= 0 || iArr2[i] <= 0 || pointArr[i] == null) {
                        break;
                    }
                    i++;
                } else {
                    for (int i3 = 0; i3 < 2; i3++) {
                        this.mPopOverWidthDp[i3] = iArr[i3];
                        this.mPopOverHeightDp[i3] = iArr2[i3];
                        this.mPopOverAnchorMarginDp[i3] = new Point(pointArr[i3]);
                        this.mPopOverAnchorPosition[i3] = iArr3[i3];
                    }
                    this.mIsPopOver = true;
                }
            }
        }
        return this;
    }

    public boolean hasValidLaunchAdjacentExt() {
        return hasValidVerticalSplitLayoutWithAdjacentFlag() || hasValidHorizontalSplitLayoutWithAdjacentFlag();
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

    public void setDisableSplashScreen() {
        this.mDisableSplashScreen = true;
    }

    public boolean getDisableSplashScreen() {
        return this.mDisableSplashScreen;
    }

    public boolean getUnhandledDropLaunch() {
        return this.mUnhandledDropLaunch;
    }

    public boolean isResumedAffordanceAnimationRequested() {
        return this.mResumedAffordanceAnimationRequested;
    }

    public void setResumedAffordanceAnimation() {
        this.mResumedAffordanceAnimationRequested = true;
    }

    public int getCustomizedCoverDensity() {
        return this.mCustomizedCoverDensity;
    }

    public void setCustomizedCoverDensity(int i) {
        this.mCustomizedCoverDensity = i;
    }

    public void setLaunchedFromHome() {
        this.mLaunchedFromHome = true;
    }

    public boolean getLaunchedFromHome() {
        return this.mLaunchedFromHome;
    }
}
