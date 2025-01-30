package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ICompatCameraControlCallback;
import android.app.IScreenCaptureObserver;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.ResultInfo;
import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.app.admin.DevicePolicyManager;
import android.app.assist.ActivityId;
import android.app.servertransaction.ActivityConfigurationChangeItem;
import android.app.servertransaction.ActivityLifecycleItem;
import android.app.servertransaction.ActivityRelaunchItem;
import android.app.servertransaction.ActivityResultItem;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.DestroyActivityItem;
import android.app.servertransaction.MoveToDisplayItem;
import android.app.servertransaction.NewIntentItem;
import android.app.servertransaction.PauseActivityItem;
import android.app.servertransaction.ResumeActivityItem;
import android.app.servertransaction.StartActivityItem;
import android.app.servertransaction.StopActivityItem;
import android.app.servertransaction.TopResumedActivityChangeItem;
import android.app.servertransaction.TransferSplashScreenViewStateItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.LocusId;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConstrainDisplayApisConfig;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.HardwareBuffer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.InputConstants;
import android.os.LocaleList;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.p005os.IInstalld;
import android.service.voice.IVoiceInteractionSession;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.MergedConfiguration;
import android.util.Pair;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.view.AppTransitionAnimationSpec;
import android.view.DisplayInfo;
import android.view.IAppTransitionAnimationSpecsFuture;
import android.view.InputApplicationHandle;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationDefinition;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.window.ITaskFragmentOrganizer;
import android.window.RemoteTransition;
import android.window.SizeConfigurationBuckets;
import android.window.SplashScreenView;
import android.window.TaskSnapshot;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import com.android.internal.app.ResolverActivity;
import com.android.internal.content.ReferrerIntent;
import com.android.internal.os.TimeoutRecord;
import com.android.internal.os.TransferPipe;
import com.android.internal.policy.AttributeCache;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.ToBooleanFunction;
import com.android.internal.util.jobs.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.UiModeManagerInternal;
import com.android.server.am.AppTimeTracker;
import com.android.server.am.FreecessController;
import com.android.server.am.PendingIntentRecord;
import com.android.server.appprelauncher.AppPrelaunchManagerService;
import com.android.server.contentcapture.ContentCaptureManagerInternal;
import com.android.server.display.DisplayPowerController2;
import com.android.server.display.color.ColorDisplayService;
import com.android.server.uri.NeededUriGrants;
import com.android.server.uri.UriPermissionOwner;
import com.android.server.wm.utils.InsetUtils;
import com.google.android.collect.Sets;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.feature.SemGateConfig;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import dalvik.annotation.optimization.NeverCompile;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class ActivityRecord extends WindowToken
    implements WindowManagerService.AppFreezeListener {
  public static ConstrainDisplayApisConfig sConstrainDisplayApisConfig;
  public boolean allDrawn;
  public WindowProcessController app;
  public AppTimeTracker appTimeTracker;
  public final Binder assistToken;
  public final boolean componentSpecified;
  public int configChangeFlags;
  public long createTime;
  public boolean deferRelaunchUntilPaused;
  public boolean delayedResume;
  public boolean finishing;
  public boolean firstWindowDrawn;
  public boolean forceNewConfig;
  public boolean frozenBeforeDestroy;
  public boolean hasBeenLaunched;
  public int icon;
  public boolean idle;
  public boolean immersive;
  public volatile boolean inHistory;
  public final ActivityInfo info;
  public final Intent intent;
  public boolean keysPaused;
  public int labelRes;
  public long lastLaunchTime;
  public long lastVisibleTime;
  public int launchCount;
  public boolean launchFailed;
  public int launchMode;
  public long launchTickTime;
  public final String launchedFromFeatureId;
  public final String launchedFromPackage;
  public final int launchedFromPid;
  public final int launchedFromUid;
  public int lockTaskLaunchMode;
  public final ComponentName mActivityComponent;
  public final ActivityRecordInputSink mActivityRecordInputSink;
  public final AddStartingWindow mAddStartingWindow;
  public boolean mAliasChild;
  public boolean mAllowCrossUidActivitySwitchFromBelow;
  public int mAllowedTouchUid;
  public AnimatingActivityRegistry mAnimatingActivityRegistry;
  public final boolean mAppActivityEmbeddingSplitsEnabled;
  public boolean mAppStopped;
  public final ActivityTaskManagerService mAtmService;
  public boolean mAutoEnteringPip;
  public boolean mCameraCompatControlClickedByUser;
  public final boolean mCameraCompatControlEnabled;
  public int mCameraCompatControlState;
  public RemoteCallbackList mCaptureCallbacks;
  public boolean mClientVisibilityDeferred;
  public final ColorDisplayService.ColorTransformController mColorTransformController;
  public ICompatCameraControlCallback mCompatCameraControlCallback;
  public CompatDisplayInsets mCompatDisplayInsets;
  public BoundsCompatRecord mCompatRecord;
  public int mConfigurationSeq;
  public boolean mCurrentLaunchCanTurnScreenOn;
  public CustomAppTransition mCustomCloseTransition;
  public CustomAppTransition mCustomOpenTransition;
  public boolean mDeferHidingClient;
  public final Runnable mDestroyTimeoutRunnable;
  public boolean mDisableSnapshot;
  public boolean mDismissKeyguard;
  public boolean mEnableRecentsScreenshot;
  public boolean mEnteringAnimation;
  public Drawable mEnterpriseThumbnailDrawable;
  public boolean mForceSendResultForMediaProjection;
  public boolean mFreezingScreen;
  public boolean mHandleExitSplashScreen;
  int mHandoverLaunchDisplayId;
  TaskDisplayArea mHandoverTaskDisplayArea;
  public Boolean mHasDeskResources;
  public final boolean mHasSceneTransition;
  public boolean mHaveState;
  public boolean mHiddenWhileEnteringPinnedMode;
  public final Runnable mHiddenWhileEnteringPinnedTimeoutRunnable;
  public Bundle mIcicle;
  public boolean mIgnoreDevSettingForNonResizable;
  public boolean mIgnoreDisplayCompatMode;
  public boolean mImeInsetsFrozenUntilStartInput;
  public boolean mInSizeCompatModeForBounds;
  public boolean mInheritShownWhenLocked;
  public InputApplicationHandle mInputApplicationHandle;
  public long mInputDispatchingTimeoutMillis;
  public boolean mIsActivityReparentToEmbeddedTask;
  public boolean mIsAliasActivity;
  public boolean mIsAllowedSeamlessRotation;
  public boolean mIsAppLockExceptionActivity;
  public boolean mIsAspectRatioApplied;
  public boolean mIsDexCompatEnabled;
  public boolean mIsEligibleForFixedOrientationLetterbox;
  public boolean mIsExiting;
  public boolean mIsInputDroppedForAnimation;
  public boolean mIsLateTransientLaunch;
  public boolean mIsPrelMode;
  public boolean mKeepScreenWhenFolding;
  public boolean mKeepSnapshotCache;
  public boolean mLastAllDrawn;
  public boolean mLastAllReadyAtSync;
  public AppSaturationInfo mLastAppSaturationInfo;
  public boolean mLastContainsDismissKeyguardWindow;
  public boolean mLastContainsShowWhenLockedWindow;
  public boolean mLastContainsTurnScreenOnWindow;
  public boolean mLastDeferHidingClient;
  public int mLastDropInputMode;
  public boolean mLastImeShown;
  public Intent mLastNewIntent;
  public Task mLastParentBeforePip;
  public MergedConfiguration mLastReportedConfiguration;
  public int mLastReportedDisplayId;
  public boolean mLastReportedMultiWindowMode;
  public boolean mLastReportedPictureInPictureMode;
  public boolean mLastSurfaceShowing;
  public ITaskFragmentOrganizer mLastTaskFragmentOrganizerBeforePip;
  public long mLastTransactionSequence;
  public IBinder mLaunchCookie;
  public ActivityRecord mLaunchIntoPipHostActivity;
  public WindowContainerToken mLaunchRootTask;
  public final int mLaunchSourceType;
  public final Runnable mLaunchTickRunnable;
  public boolean mLaunchedFromBubble;
  public boolean mLaunchingRequestedFromNotification;
  public Rect mLetterboxBoundsForFixedOrientationAndAspectRatio;
  public final LetterboxUiController mLetterboxUiController;
  public LocusId mLocusId;
  public boolean mNeedsLetterboxedAnimation;
  public boolean mNoTransitionOcclusion;
  public boolean mNotToBeTopForAppLockException;
  public int mNumDrawnWindows;
  public int mNumInterestingWindows;
  public boolean mOccludesParent;
  public boolean mOverrideTaskTransition;
  public boolean mPauseSchedulePendingForPip;
  public final Runnable mPauseTimeoutRunnable;
  public ActivityOptions mPendingOptions;
  public int mPendingRelaunchCount;
  public RemoteAnimationAdapter mPendingRemoteAnimation;
  public RemoteTransition mPendingRemoteTransition;
  public PersistableBundle mPersistentState;
  public PopOverState mPopOverState;
  public ApplicationInfo mProcessAppInfo;
  public int mProcessAppLaunchPolicy;
  public ActivityRecord mRelativeActivity;
  public int mRelaunchReason;
  public long mRelaunchStartTime;
  public RemoteAnimationDefinition mRemoteAnimationDefinition;
  public boolean mRemovingFromDisplay;
  public boolean mReportedDrawn;
  public final WindowState.UpdateReportedVisibilityResults mReportedVisibilityResults;
  public boolean mRequestFinishFromSourceActivity;
  public boolean mRequestForceTransition;
  public boolean mRequestFreeformForceHiding;
  public IBinder mRequestedLaunchingTaskFragmentToken;
  public int mResolvedConfigChangeFlags;
  public int mRestoreActivityGroupZone;
  public Rect mReturnSizeCompatBounds;
  public final RootWindowContainer mRootWindowContainer;
  public int mRotationAnimationHint;
  public ActivityServiceConnectionsHolder mServiceConnectionsHolder;
  public boolean mShareIdentity;
  public boolean mShouldPrelNotify;
  public boolean mShouldShowPackageNightModeDialog;
  public final boolean mShowForAllUsers;
  public boolean mShowWhenLocked;
  public SizeCompatAttributes mSizeCompatAttributes;
  public Rect mSizeCompatBounds;
  public float mSizeCompatScale;
  public SizeConfigurationBuckets mSizeConfigurations;
  public ActivityRecord mSourceSplitActivity;
  public boolean mSplashScreenStyleSolidColor;
  public StartingData mStartingData;
  public StartingSurfaceController.StartingSurface mStartingSurface;
  public WindowState mStartingWindow;
  public State mState;
  public final Runnable mStopTimeoutRunnable;
  public final boolean mStyleFillsParent;
  public boolean mStyleFloating;
  public int mTargetSdk;
  public final List mTargetSplitActivities;
  public boolean mTaskOverlay;
  public final ActivityTaskSupervisor mTaskSupervisor;
  public final Rect mTmpBounds;
  public final Configuration mTmpConfig;
  public final Runnable mTransferSplashScreenTimeoutRunnable;
  public int mTransferringSplashScreenState;
  public final int mTransientBarShowingDelayMillis;
  public int mTransitionChangeFlags;
  public boolean mTurnScreenOn;
  public final int mUserId;
  public boolean mVisible;
  public volatile boolean mVisibleForServiceConnection;
  public boolean mVisibleSetFromTransferredStartingWindow;
  public boolean mVoiceInteraction;
  public boolean mWaitForEnteringPinnedMode;
  public boolean mWillCloseOrEnterPip;
  public ArrayList newIntents;
  boolean noDisplay;
  public CharSequence nonLocalizedLabel;
  public boolean nowVisible;
  public final String packageName;
  public long pauseTime;
  public HashSet pendingResults;
  public boolean pendingVoiceInteractionStart;
  public PictureInPictureParams pictureInPictureArgs;
  public boolean preserveWindowOnDeferredRelaunch;
  public final String processName;
  public boolean reportedVisible;
  public final int requestCode;
  public ComponentName requestedVrComponent;
  public final String resolvedType;
  public ActivityRecord resultTo;
  public final String resultWho;
  public ArrayList results;
  public ActivityOptions returningOptions;
  public final boolean rootVoiceInteraction;
  public final Binder shareableActivityToken;
  public final String shortComponentName;
  public boolean shouldDockBigOverlays;
  public boolean startingMoved;
  public final boolean stateNotNeeded;
  public boolean supportsEnterPipOnTaskSwitch;
  public Task task;
  public final String taskAffinity;
  public final String taskAffinityWithoutUid;
  public ActivityManager.TaskDescription taskDescription;
  public int theme;
  public long topResumedStateLossTime;
  public UriPermissionOwner uriPermissions;
  public boolean visibleIgnoringKeyguard;
  public IVoiceInteractionSession voiceSession;

  public class CustomAppTransition {
    public int mBackgroundColor;
    public int mEnterAnim;
    public int mExitAnim;
  }

  enum State {
    INITIALIZING,
    STARTED,
    RESUMED,
    PAUSING,
    PAUSED,
    STOPPING,
    STOPPED,
    FINISHING,
    DESTROYING,
    DESTROYED,
    RESTARTING_PROCESS
  }

  public static int getCenterOffset(int i, int i2) {
    return (int) (((i - i2) + 1) * 0.5f);
  }

  public static boolean hasResizeChange(int i) {
    return (i & 3456) != 0;
  }

  public static boolean isResizeOnlyChange(int i) {
    return (i & (-3457)) == 0;
  }

  public static int reverseConfigurationOrientation(int i) {
    if (i == 1) {
      return 2;
    }
    if (i != 2) {
      return i;
    }
    return 1;
  }

  @Override // com.android.server.wm.WindowContainer
  public ActivityRecord asActivityRecord() {
    return this;
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean canCreateRemoteAnimationTarget() {
    return true;
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean canCustomizeAppTransition() {
    return true;
  }

  @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
  public long getProtoFieldId() {
    return 1146756268038L;
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean hasActivity() {
    return true;
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean onChildVisibleRequestedChanged(WindowContainer windowContainer) {
    return false;
  }

  @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
  public void resetSurfacePositionForAnimationLeash(SurfaceControl.Transaction transaction) {}

  @Override // com.android.server.wm.WindowContainer
  public boolean showSurfaceOnCreation() {
    return false;
  }

  public final void updateEnterpriseThumbnailDrawable(final Context context) {
    this.mEnterpriseThumbnailDrawable =
        ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class))
            .getResources()
            .getDrawable(
                "WORK_PROFILE_ICON",
                "OUTLINE",
                "PROFILE_SWITCH_ANIMATION",
                new Supplier() { // from class:
                                 // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda30
                  @Override // java.util.function.Supplier
                  public final Object get() {
                    Drawable lambda$updateEnterpriseThumbnailDrawable$0;
                    lambda$updateEnterpriseThumbnailDrawable$0 =
                        ActivityRecord.lambda$updateEnterpriseThumbnailDrawable$0(context);
                    return lambda$updateEnterpriseThumbnailDrawable$0;
                  }
                });
  }

  public static /* synthetic */ Drawable lambda$updateEnterpriseThumbnailDrawable$0(
      Context context) {
    return context.getDrawable(R.drawable.ic_clear_normal);
  }

  public WindowState findMaxVisibleAppWindow() {
    int size = this.mChildren.size();
    WindowState windowState = null;
    WindowState windowState2 = null;
    for (int i = 0; i < size; i++) {
      WindowState windowState3 = (WindowState) this.mChildren.get(i);
      int i2 = windowState3.mAttrs.type;
      if (windowState3.mHasSurface && windowState3.mViewVisibility == 0 && (i2 == 1 || i2 == 2)) {
        int width = windowState3.getWindowFrames().mFrame.width();
        int height = windowState3.getWindowFrames().mFrame.height();
        if (windowState3.mAnimatingExit) {
          if (windowState2 == null
              || (windowState2.getWindowFrames().mFrame.height() <= height
                  && windowState2.getWindowFrames().mFrame.width() <= width)) {
            windowState2 = windowState3;
          }
        } else if (windowState == null
            || (windowState.getWindowFrames().mFrame.height() <= height
                && windowState.getWindowFrames().mFrame.width() <= width)) {
          windowState = windowState3;
        }
      }
    }
    return windowState != null ? windowState : windowState2;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$2(final float[] fArr, final float[] fArr2) {
    this.mWmService.f1749mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda29
          @Override // java.lang.Runnable
          public final void run() {
            ActivityRecord.this.lambda$new$1(fArr, fArr2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$1(float[] fArr, float[] fArr2) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (this.mLastAppSaturationInfo == null) {
          this.mLastAppSaturationInfo = new AppSaturationInfo();
        }
        this.mLastAppSaturationInfo.setSaturation(fArr, fArr2);
        updateColorTransform();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
  @NeverCompile
  public void dump(PrintWriter printWriter, String str, boolean z) {
    SizeCompatAttributes sizeCompatAttributes;
    ApplicationInfo applicationInfo;
    long uptimeMillis = SystemClock.uptimeMillis();
    printWriter.print(str);
    printWriter.print("packageName=");
    printWriter.print(this.packageName);
    printWriter.print(" processName=");
    printWriter.println(this.processName);
    printWriter.print(str);
    printWriter.print("launchedFromUid=");
    printWriter.print(this.launchedFromUid);
    printWriter.print(" launchedFromPackage=");
    printWriter.print(this.launchedFromPackage);
    printWriter.print(" launchedFromFeature=");
    printWriter.print(this.launchedFromFeatureId);
    printWriter.print(" userId=");
    printWriter.println(this.mUserId);
    printWriter.print(str);
    printWriter.print("app=");
    printWriter.println(this.app);
    printWriter.print(str);
    printWriter.println(this.intent.toInsecureString());
    printWriter.print(str);
    printWriter.print("rootOfTask=");
    printWriter.print(isRootOfTask());
    printWriter.print(" task=");
    printWriter.println(this.task);
    printWriter.print(str);
    printWriter.print("taskAffinity=");
    printWriter.println(this.taskAffinity);
    printWriter.print(str);
    printWriter.print("mActivityComponent=");
    printWriter.println(this.mActivityComponent.flattenToShortString());
    ActivityInfo activityInfo = this.info;
    if (activityInfo != null && (applicationInfo = activityInfo.applicationInfo) != null) {
      printWriter.print(str);
      printWriter.print("baseDir=");
      printWriter.println(applicationInfo.sourceDir);
      if (!Objects.equals(applicationInfo.sourceDir, applicationInfo.publicSourceDir)) {
        printWriter.print(str);
        printWriter.print("resDir=");
        printWriter.println(applicationInfo.publicSourceDir);
      }
      printWriter.print(str);
      printWriter.print("dataDir=");
      printWriter.println(applicationInfo.dataDir);
      if (applicationInfo.splitSourceDirs != null) {
        printWriter.print(str);
        printWriter.print("splitDir=");
        printWriter.println(Arrays.toString(applicationInfo.splitSourceDirs));
      }
      if (applicationInfo.metaData != null) {
        printWriter.print(str);
        printWriter.print("appMetaData=");
        printWriter.println(applicationInfo.metaData);
      }
    }
    printWriter.print(str);
    printWriter.print("stateNotNeeded=");
    printWriter.print(this.stateNotNeeded);
    printWriter.print(" componentSpecified=");
    printWriter.print(this.componentSpecified);
    printWriter.print(" mActivityType=");
    printWriter.println(WindowConfiguration.activityTypeToString(getActivityType()));
    if (this.rootVoiceInteraction) {
      printWriter.print(str);
      printWriter.print("rootVoiceInteraction=");
      printWriter.println(this.rootVoiceInteraction);
    }
    printWriter.print(str);
    printWriter.print("compat=");
    printWriter.print(
        this.mAtmService.compatibilityInfoForPackageLocked(this.info.applicationInfo));
    printWriter.print(" labelRes=0x");
    printWriter.print(Integer.toHexString(this.labelRes));
    printWriter.print(" icon=0x");
    printWriter.print(Integer.toHexString(this.icon));
    printWriter.print(" theme=0x");
    printWriter.println(Integer.toHexString(this.theme));
    printWriter.println(str + "mLastReportedConfigurations:");
    this.mLastReportedConfiguration.dump(printWriter, str + "  ");
    printWriter.print(str);
    printWriter.print("CurrentConfiguration=");
    printWriter.println(getConfiguration());
    if (!getRequestedOverrideConfiguration().equals(Configuration.EMPTY)) {
      printWriter.println(
          str + "RequestedOverrideConfiguration=" + getRequestedOverrideConfiguration());
    }
    if (!getResolvedOverrideConfiguration().equals(getRequestedOverrideConfiguration())) {
      printWriter.println(
          str + "ResolvedOverrideConfiguration=" + getResolvedOverrideConfiguration());
    }
    if (!matchParentBounds()) {
      printWriter.println(str + "bounds=" + getBounds());
    }
    if (this.resultTo != null || this.resultWho != null) {
      printWriter.print(str);
      printWriter.print("resultTo=");
      printWriter.print(this.resultTo);
      printWriter.print(" resultWho=");
      printWriter.print(this.resultWho);
      printWriter.print(" resultCode=");
      printWriter.println(this.requestCode);
    }
    ActivityManager.TaskDescription taskDescription = this.taskDescription;
    if (taskDescription != null
        && (taskDescription.getIconFilename() != null
            || this.taskDescription.getLabel() != null
            || this.taskDescription.getPrimaryColor() != 0)) {
      printWriter.print(str);
      printWriter.print("taskDescription:");
      printWriter.print(" label=\"");
      printWriter.print(this.taskDescription.getLabel());
      printWriter.print("\"");
      printWriter.print(" icon=");
      printWriter.print(
          this.taskDescription.getInMemoryIcon() != null
              ? this.taskDescription.getInMemoryIcon().getByteCount() + " bytes"
              : "null");
      printWriter.print(" iconResource=");
      printWriter.print(this.taskDescription.getIconResourcePackage());
      printWriter.print("/");
      printWriter.print(this.taskDescription.getIconResource());
      printWriter.print(" iconFilename=");
      printWriter.print(this.taskDescription.getIconFilename());
      printWriter.print(" primaryColor=");
      printWriter.println(Integer.toHexString(this.taskDescription.getPrimaryColor()));
      printWriter.print(str);
      printWriter.print("  backgroundColor=");
      printWriter.print(Integer.toHexString(this.taskDescription.getBackgroundColor()));
      printWriter.print(" statusBarColor=");
      printWriter.print(Integer.toHexString(this.taskDescription.getStatusBarColor()));
      printWriter.print(" navigationBarColor=");
      printWriter.println(Integer.toHexString(this.taskDescription.getNavigationBarColor()));
      printWriter.print(str);
      printWriter.print(" backgroundColorFloating=");
      printWriter.println(Integer.toHexString(this.taskDescription.getBackgroundColorFloating()));
    }
    if (this.results != null) {
      printWriter.print(str);
      printWriter.print("results=");
      printWriter.println(this.results);
    }
    HashSet hashSet = this.pendingResults;
    if (hashSet != null && hashSet.size() > 0) {
      printWriter.print(str);
      printWriter.println("Pending Results:");
      Iterator it = this.pendingResults.iterator();
      while (it.hasNext()) {
        WeakReference weakReference = (WeakReference) it.next();
        PendingIntentRecord pendingIntentRecord =
            weakReference != null ? (PendingIntentRecord) weakReference.get() : null;
        printWriter.print(str);
        printWriter.print("  - ");
        if (pendingIntentRecord == null) {
          printWriter.println("null");
        } else {
          printWriter.println(pendingIntentRecord);
          pendingIntentRecord.dump(printWriter, str + "    ");
        }
      }
    }
    ArrayList arrayList = this.newIntents;
    if (arrayList != null && arrayList.size() > 0) {
      printWriter.print(str);
      printWriter.println("Pending New Intents:");
      for (int i = 0; i < this.newIntents.size(); i++) {
        Intent intent = (Intent) this.newIntents.get(i);
        printWriter.print(str);
        printWriter.print("  - ");
        if (intent == null) {
          printWriter.println("null");
        } else {
          printWriter.println(intent.toShortString(false, true, false, false));
        }
      }
    }
    if (this.mPendingOptions != null) {
      printWriter.print(str);
      printWriter.print("pendingOptions=");
      printWriter.println(this.mPendingOptions);
    }
    if (this.returningOptions != null) {
      printWriter.print(str);
      printWriter.print("returningOptions=");
      printWriter.println(this.returningOptions);
    }
    if (this.mPendingRemoteAnimation != null) {
      printWriter.print(str);
      printWriter.print("pendingRemoteAnimationCallingPid=");
      printWriter.println(this.mPendingRemoteAnimation.getCallingPid());
    }
    if (this.mPendingRemoteTransition != null) {
      printWriter.print(
          str + " pendingRemoteTransition=" + this.mPendingRemoteTransition.getRemoteTransition());
    }
    AppTimeTracker appTimeTracker = this.appTimeTracker;
    if (appTimeTracker != null) {
      appTimeTracker.dumpWithHeader(printWriter, str, false);
    }
    UriPermissionOwner uriPermissionOwner = this.uriPermissions;
    if (uriPermissionOwner != null) {
      uriPermissionOwner.dump(printWriter, str);
    }
    printWriter.print(str);
    printWriter.print("launchFailed=");
    printWriter.print(this.launchFailed);
    printWriter.print(" launchCount=");
    printWriter.print(this.launchCount);
    printWriter.print(" lastLaunchTime=");
    long j = this.lastLaunchTime;
    if (j == 0) {
      printWriter.print("0");
    } else {
      TimeUtils.formatDuration(j, uptimeMillis, printWriter);
    }
    printWriter.println();
    if (this.mLaunchCookie != null) {
      printWriter.print(str);
      printWriter.print("launchCookie=");
      printWriter.println(this.mLaunchCookie);
    }
    if (this.mLaunchRootTask != null) {
      printWriter.print(str);
      printWriter.print("mLaunchRootTask=");
      printWriter.println(this.mLaunchRootTask);
    }
    printWriter.print(str);
    printWriter.print("mHaveState=");
    printWriter.print(this.mHaveState);
    printWriter.print(" mIcicle=");
    printWriter.println(this.mIcicle);
    printWriter.print(str);
    printWriter.print("state=");
    printWriter.print(this.mState);
    printWriter.print(" delayedResume=");
    printWriter.print(this.delayedResume);
    printWriter.print(" finishing=");
    printWriter.println(this.finishing);
    printWriter.print(str);
    printWriter.print("keysPaused=");
    printWriter.print(this.keysPaused);
    printWriter.print(" inHistory=");
    printWriter.print(this.inHistory);
    printWriter.print(" idle=");
    printWriter.println(this.idle);
    printWriter.print(str);
    printWriter.print("occludesParent=");
    printWriter.print(occludesParent());
    printWriter.print(" noDisplay=");
    printWriter.print(this.noDisplay);
    printWriter.print(" immersive=");
    printWriter.print(this.immersive);
    printWriter.print(" launchMode=");
    printWriter.println(this.launchMode);
    printWriter.print(str);
    printWriter.print("frozenBeforeDestroy=");
    printWriter.print(this.frozenBeforeDestroy);
    printWriter.print(" forceNewConfig=");
    printWriter.println(this.forceNewConfig);
    printWriter.print(str);
    printWriter.print("mActivityType=");
    printWriter.println(WindowConfiguration.activityTypeToString(getActivityType()));
    printWriter.print(str);
    printWriter.print("mImeInsetsFrozenUntilStartInput=");
    printWriter.println(this.mImeInsetsFrozenUntilStartInput);
    if (this.requestedVrComponent != null) {
      printWriter.print(str);
      printWriter.print("requestedVrComponent=");
      printWriter.println(this.requestedVrComponent);
    }
    super.dump(printWriter, str, z);
    if (this.mVoiceInteraction) {
      printWriter.println(str + "mVoiceInteraction=true");
    }
    printWriter.print(str);
    printWriter.print("mOccludesParent=");
    printWriter.println(this.mOccludesParent);
    printWriter.print(str);
    printWriter.print("mStyleFillsParent=");
    printWriter.println(this.mStyleFillsParent);
    printWriter.print(str);
    printWriter.print("overrideOrientation=");
    printWriter.println(ActivityInfo.screenOrientationToString(getOverrideOrientation()));
    printWriter.print(str);
    printWriter.print("requestedOrientation=");
    printWriter.println(ActivityInfo.screenOrientationToString(super.getOverrideOrientation()));
    StringBuilder sb = new StringBuilder();
    sb.append(str);
    sb.append("mVisibleRequested=");
    sb.append(this.mVisibleRequested);
    sb.append(" mVisible=");
    sb.append(this.mVisible);
    sb.append(" mClientVisible=");
    sb.append(isClientVisible());
    sb.append(this.mDeferHidingClient ? " mDeferHidingClient=" + this.mDeferHidingClient : "");
    sb.append(" reportedDrawn=");
    sb.append(this.mReportedDrawn);
    sb.append(" reportedVisible=");
    sb.append(this.reportedVisible);
    printWriter.println(sb.toString());
    if (this.paused) {
      printWriter.print(str);
      printWriter.print("paused=");
      printWriter.println(this.paused);
    }
    if (this.mAppStopped) {
      printWriter.print(str);
      printWriter.print("mAppStopped=");
      printWriter.println(this.mAppStopped);
    }
    if (this.mNumInterestingWindows != 0
        || this.mNumDrawnWindows != 0
        || this.allDrawn
        || this.mLastAllDrawn) {
      printWriter.print(str);
      printWriter.print("mNumInterestingWindows=");
      printWriter.print(this.mNumInterestingWindows);
      printWriter.print(" mNumDrawnWindows=");
      printWriter.print(this.mNumDrawnWindows);
      printWriter.print(" allDrawn=");
      printWriter.print(this.allDrawn);
      printWriter.print(" lastAllDrawn=");
      printWriter.print(this.mLastAllDrawn);
      printWriter.println(")");
    }
    if (this.mStartingData != null || this.firstWindowDrawn || this.mIsExiting) {
      printWriter.print(str);
      printWriter.print("startingData=");
      printWriter.print(this.mStartingData);
      printWriter.print(" firstWindowDrawn=");
      printWriter.print(this.firstWindowDrawn);
      printWriter.print(" mIsExiting=");
      printWriter.println(this.mIsExiting);
    }
    if (this.mStartingWindow != null
        || this.mStartingData != null
        || this.mStartingSurface != null
        || this.startingMoved
        || this.mVisibleSetFromTransferredStartingWindow) {
      printWriter.print(str);
      printWriter.print("startingWindow=");
      printWriter.print(this.mStartingWindow);
      printWriter.print(" startingSurface=");
      printWriter.print(this.mStartingSurface);
      printWriter.print(" startingDisplayed=");
      printWriter.print(isStartingWindowDisplayed());
      printWriter.print(" startingMoved=");
      printWriter.print(this.startingMoved);
      printWriter.println(
          " mVisibleSetFromTransferredStartingWindow="
              + this.mVisibleSetFromTransferredStartingWindow);
    }
    if (this.mPendingRelaunchCount != 0) {
      printWriter.print(str);
      printWriter.print("mPendingRelaunchCount=");
      printWriter.println(this.mPendingRelaunchCount);
    }
    if (this.mSizeCompatScale != 1.0f || this.mSizeCompatBounds != null) {
      printWriter.println(
          str
              + "mSizeCompatScale="
              + this.mSizeCompatScale
              + " mSizeCompatBounds="
              + this.mSizeCompatBounds);
    }
    if (this.mRemovingFromDisplay) {
      printWriter.println(str + "mRemovingFromDisplay=" + this.mRemovingFromDisplay);
    }
    if (CoreRune.MT_SUPPORT_SIZE_COMPAT
        && (sizeCompatAttributes = this.mSizeCompatAttributes) != null
        && sizeCompatAttributes.hasBounds()) {
      this.mSizeCompatAttributes.dump(printWriter, str);
    }
    printWriter.print(str);
    printWriter.print("mIsDexCompatEnabled=");
    printWriter.println(this.mIsDexCompatEnabled);
    if (this.lastVisibleTime != 0 || this.nowVisible) {
      printWriter.print(str);
      printWriter.print("nowVisible=");
      printWriter.print(this.nowVisible);
      printWriter.print(" lastVisibleTime=");
      long j2 = this.lastVisibleTime;
      if (j2 == 0) {
        printWriter.print("0");
      } else {
        TimeUtils.formatDuration(j2, uptimeMillis, printWriter);
      }
      printWriter.println();
    }
    if (this.mDeferHidingClient) {
      printWriter.println(str + "mDeferHidingClient=" + this.mDeferHidingClient);
    }
    if (this.mLastDeferHidingClient) {
      printWriter.println(str + "mLastDeferHidingClient=" + this.mLastDeferHidingClient);
    }
    if (this.deferRelaunchUntilPaused || this.configChangeFlags != 0) {
      printWriter.print(str);
      printWriter.print("deferRelaunchUntilPaused=");
      printWriter.print(this.deferRelaunchUntilPaused);
      printWriter.print(" configChangeFlags=");
      printWriter.println(Integer.toHexString(this.configChangeFlags));
    }
    if (this.mServiceConnectionsHolder != null) {
      printWriter.print(str);
      printWriter.print("connections=");
      printWriter.println(this.mServiceConnectionsHolder);
    }
    if (this.info != null) {
      printWriter.println(
          str + "resizeMode=" + ActivityInfo.resizeModeToString(this.info.resizeMode));
      printWriter.println(
          str
              + "mLastReportedMultiWindowMode="
              + this.mLastReportedMultiWindowMode
              + " mLastReportedPictureInPictureMode="
              + this.mLastReportedPictureInPictureMode);
      if (this.info.supportsPictureInPicture()) {
        printWriter.println(
            str + "supportsPictureInPicture=" + this.info.supportsPictureInPicture());
        printWriter.println(
            str + "supportsEnterPipOnTaskSwitch: " + this.supportsEnterPipOnTaskSwitch);
        printWriter.println(
            str + "supportPictureInPictureAppOps=" + checkEnterPictureInPictureAppOpsState());
      }
      if (getMaxAspectRatio() != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        printWriter.println(str + "maxAspectRatio=" + getMaxAspectRatio());
      }
      float minAspectRatio = getMinAspectRatio();
      if (minAspectRatio != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        printWriter.println(str + "minAspectRatio=" + minAspectRatio);
      }
      if (minAspectRatio != this.info.getManifestMinAspectRatio()) {
        printWriter.println(
            str + "manifestMinAspectRatio=" + this.info.getManifestMinAspectRatio());
      }
      printWriter.println(
          str
              + "supportsSizeChanges="
              + ActivityInfo.sizeChangesSupportModeToString(supportsSizeChanges()));
      if (this.info.configChanges != 0) {
        printWriter.println(
            str + "configChanges=0x" + Integer.toHexString(this.info.configChanges));
      }
      printWriter.println(
          str
              + "neverSandboxDisplayApis="
              + this.info.neverSandboxDisplayApis(sConstrainDisplayApisConfig));
      printWriter.println(
          str
              + "alwaysSandboxDisplayApis="
              + this.info.alwaysSandboxDisplayApis(sConstrainDisplayApisConfig));
      if (this.mIgnoreDevSettingForNonResizable) {
        printWriter.println(str + "mIgnoreDevSettingForNonResizable=true");
      }
      if (this.mIsAllowedSeamlessRotation) {
        printWriter.println(str + "mIsAllowedSeamlessRotation=true");
      }
      if (this.info.metaData != null) {
        printWriter.println(str + "activityMetaData=" + this.info.metaData);
      }
    }
    if (this.mLastParentBeforePip != null) {
      printWriter.println(str + "lastParentTaskIdBeforePip=" + this.mLastParentBeforePip.mTaskId);
    }
    if (this.mLaunchIntoPipHostActivity != null) {
      printWriter.println(str + "launchIntoPipHostActivity=" + this.mLaunchIntoPipHostActivity);
    }
    this.mLetterboxUiController.dump(printWriter, str);
    printWriter.println(
        str
            + "mCameraCompatControlState="
            + TaskInfo.cameraCompatControlStateToString(this.mCameraCompatControlState));
    printWriter.println(str + "mCameraCompatControlEnabled=" + this.mCameraCompatControlEnabled);
    if (this.mAtmService.mExt.isKeepAliveActivity(this)) {
      printWriter.println(str + "keepAlive=true");
    }
    if (this.mWaitForEnteringPinnedMode) {
      printWriter.println(str + "mWaitForEnteringPinnedMode=true");
    }
    this.mCompatRecord.dump(printWriter, str);
    CompatDisplayInsets compatDisplayInsets = this.mCompatDisplayInsets;
    if (compatDisplayInsets != null) {
      compatDisplayInsets.dump(printWriter, str);
      if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT
          && this.mLetterboxBoundsForFixedOrientationAndAspectRatio != null) {
        printWriter.print(str);
        printWriter.print("mLetterboxBoundsForFixedOrientationAndAspectRatio=");
        printWriter.print(this.mLetterboxBoundsForFixedOrientationAndAspectRatio);
        printWriter.println();
      }
    }
    this.mPopOverState.dump(printWriter, str);
    printWriter.println(str + "mLastSurfaceShowing=" + this.mLastSurfaceShowing);
  }

  public static boolean dumpActivity(
      FileDescriptor fileDescriptor,
      PrintWriter printWriter,
      int i,
      ActivityRecord activityRecord,
      String str,
      String str2,
      boolean z,
      boolean z2,
      boolean z3,
      String str3,
      boolean z4,
      Runnable runnable,
      Task task) {
    if (str3 != null && !str3.equals(activityRecord.packageName)) {
      return false;
    }
    boolean z5 = !z2 && (z || !activityRecord.isInHistory());
    if (z4) {
      printWriter.println("");
    }
    if (runnable != null) {
      runnable.run();
    }
    String str4 = str + "  ";
    String[] strArr = new String[0];
    if (task != activityRecord.getTask()) {
      Task task2 = activityRecord.getTask();
      printWriter.print(str);
      printWriter.print(z5 ? "* " : "  ");
      printWriter.println(task2);
      if (z5) {
        task2.dump(printWriter, str + "  ");
      } else if (z && task2.intent != null) {
        printWriter.print(str);
        printWriter.print("  ");
        printWriter.println(task2.intent.toInsecureString());
      }
    }
    printWriter.print(str);
    printWriter.print(z5 ? "* " : "    ");
    printWriter.print(str2);
    printWriter.print(" #");
    printWriter.print(i);
    printWriter.print(": ");
    printWriter.println(activityRecord);
    if (z5) {
      activityRecord.dump(printWriter, str4, true);
    } else if (z) {
      printWriter.print(str4);
      printWriter.println(activityRecord.intent.toInsecureString());
      if (activityRecord.app != null) {
        printWriter.print(str4);
        printWriter.println(activityRecord.app);
      }
    }
    if (z3 && activityRecord.attachedToProcess()) {
      printWriter.flush();
      try {
        TransferPipe transferPipe = new TransferPipe();
        try {
          activityRecord
              .app
              .getThread()
              .dumpActivity(transferPipe.getWriteFd(), activityRecord.token, str4, strArr);
          transferPipe.go(fileDescriptor, 2000L);
          transferPipe.kill();
        } catch (Throwable th) {
          transferPipe.kill();
          throw th;
        }
      } catch (RemoteException unused) {
        printWriter.println(str4 + "Got a RemoteException while dumping the activity");
      } catch (IOException e) {
        printWriter.println(str4 + "Failure while dumping the activity: " + e);
      }
    }
    return true;
  }

  public void setSavedState(Bundle bundle) {
    this.mIcicle = bundle;
    this.mHaveState = bundle != null;
  }

  public Bundle getSavedState() {
    return this.mIcicle;
  }

  public boolean hasSavedState() {
    return this.mHaveState;
  }

  public PersistableBundle getPersistentSavedState() {
    return this.mPersistentState;
  }

  public void updateApplicationInfo(ApplicationInfo applicationInfo) {
    this.info.applicationInfo = applicationInfo;
  }

  public void setSizeConfigurations(SizeConfigurationBuckets sizeConfigurationBuckets) {
    this.mSizeConfigurations = sizeConfigurationBuckets;
  }

  public final void scheduleActivityMovedToDisplay(int i, Configuration configuration) {
    if (!attachedToProcess()) {
      if (ProtoLogCache.WM_DEBUG_SWITCH_enabled) {
        ProtoLogImpl.w(
            ProtoLogGroup.WM_DEBUG_SWITCH,
            -1495062622,
            4,
            (String) null,
            new Object[] {String.valueOf(this), Long.valueOf(i)});
      }
    } else {
      try {
        if (ProtoLogCache.WM_DEBUG_SWITCH_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_SWITCH,
              374506950,
              4,
              (String) null,
              new Object[] {String.valueOf(this), Long.valueOf(i), String.valueOf(configuration)});
        }
        this.mAtmService
            .getLifecycleManager()
            .scheduleTransaction(
                this.app.getThread(),
                this.token,
                (ClientTransactionItem) MoveToDisplayItem.obtain(i, configuration));
      } catch (RemoteException unused) {
      }
    }
  }

  public final void scheduleConfigurationChanged(Configuration configuration) {
    if (!attachedToProcess()) {
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.w(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            1040675582,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
        return;
      }
      return;
    }
    try {
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            969323241,
            0,
            (String) null,
            new Object[] {String.valueOf(this), String.valueOf(configuration)});
      }
      Slog.d(
          "ActivityTaskManager",
          "scheduleConfigurationChanged: "
              + this
              + " state="
              + this.mState
              + " config="
              + configuration
              + " caller="
              + Debug.getCallers(3));
      this.mAtmService
          .getLifecycleManager()
          .scheduleTransaction(
              this.app.getThread(),
              this.token,
              (ClientTransactionItem) ActivityConfigurationChangeItem.obtain(configuration));
    } catch (RemoteException unused) {
    }
  }

  public boolean scheduleTopResumedActivityChanged(boolean z) {
    if (!attachedToProcess()) {
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.w(
            ProtoLogGroup.WM_DEBUG_STATES,
            -1193946201,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      return false;
    }
    if (z) {
      this.app.addToPendingTop();
    }
    try {
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STATES,
            -1305966693,
            12,
            (String) null,
            new Object[] {String.valueOf(this), Boolean.valueOf(z)});
      }
      Slog.d(
          "ActivityTaskManager",
          "scheduleTopResumedActivityChanged, onTop="
              + z
              + ", r="
              + this
              + ", caller="
              + Debug.getCallers(6));
      this.mAtmService
          .getLifecycleManager()
          .scheduleTransaction(
              this.app.getThread(),
              this.token,
              (ClientTransactionItem) TopResumedActivityChangeItem.obtain(z));
      return true;
    } catch (RemoteException e) {
      Slog.w("ActivityTaskManager", "Failed to send top-resumed=" + z + " to " + this, e);
      return false;
    }
  }

  public void updateMultiWindowMode() {
    boolean inMultiWindowMode;
    Task task = this.task;
    if (task == null
        || task.getRootTask() == null
        || !attachedToProcess()
        || (inMultiWindowMode = inMultiWindowMode()) == this.mLastReportedMultiWindowMode) {
      return;
    }
    if (!inMultiWindowMode && this.mLastReportedPictureInPictureMode) {
      updatePictureInPictureMode(null, false);
    } else {
      this.mLastReportedMultiWindowMode = inMultiWindowMode;
      ensureActivityConfiguration(0, true);
    }
  }

  public void updatePictureInPictureMode(Rect rect, boolean z) {
    Task task = this.task;
    if (task == null || task.getRootTask() == null || !attachedToProcess()) {
      return;
    }
    boolean z2 = inPinnedWindowingMode() && rect != null;
    if (z2 != this.mLastReportedPictureInPictureMode || z) {
      this.mLastReportedPictureInPictureMode = z2;
      this.mLastReportedMultiWindowMode = z2;
      ensureActivityConfiguration(0, true, true);
      if (z2 && findMainWindow() == null) {
        EventLog.writeEvent(1397638484, "265293293", -1, "");
        removeImmediately();
      }
    }
  }

  public Task getTask() {
    return this.task;
  }

  public TaskFragment getTaskFragment() {
    WindowContainer parent = getParent();
    if (parent != null) {
      return parent.asTaskFragment();
    }
    return null;
  }

  public final boolean shouldStartChangeTransition(
      TaskFragment taskFragment, TaskFragment taskFragment2) {
    if (taskFragment == null || taskFragment2 == null || !canStartChangeTransition()) {
      return false;
    }
    return (!taskFragment.isOrganizedTaskFragment()
            || taskFragment.getBounds().equals(taskFragment2.getBounds())
            || (CoreRune.MW_EMBED_ACTIVITY
                && this.mStartingData != null
                && ((this.mStartingWindow != null
                        && taskFragment.isEmbedded()
                        && this.mStartingWindow.matchParentBounds())
                    || this.mStartingData.mAssociatedTask != null)))
        ? false
        : true;
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean canStartChangeTransition() {
    Task task = getTask();
    return (task == null || task.isDragResizing() || !super.canStartChangeTransition())
        ? false
        : true;
  }

  @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
  public void onParentChanged(
      ConfigurationContainer configurationContainer,
      ConfigurationContainer configurationContainer2) {
    TaskFragment taskFragment = (TaskFragment) configurationContainer2;
    TaskFragment taskFragment2 = (TaskFragment) configurationContainer;
    Task task = taskFragment != null ? taskFragment.getTask() : null;
    Task task2 = taskFragment2 != null ? taskFragment2.getTask() : null;
    this.task = task2;
    this.mPopOverState.applyOptionsInherited();
    this.mPopOverState.toggle();
    if (shouldStartChangeTransition(taskFragment2, taskFragment)) {
      if (this.mTransitionController.isShellTransitionsEnabled()) {
        initializeChangeTransition(getBounds());
      } else {
        taskFragment2.initializeChangeTransition(getBounds(), getSurfaceControl());
      }
    }
    super.onParentChanged(taskFragment2, taskFragment);
    if (isPersistable()) {
      if (task != null) {
        this.mAtmService.notifyTaskPersisterLocked(task, false);
      }
      if (task2 != null) {
        this.mAtmService.notifyTaskPersisterLocked(task2, false);
      }
    }
    if (taskFragment == null && taskFragment2 != null) {
      this.mVoiceInteraction = task2.voiceSession != null;
      task2.updateOverrideConfigurationFromLaunchBounds();
      this.mLastReportedMultiWindowMode = inMultiWindowMode();
      this.mLastReportedPictureInPictureMode = inPinnedWindowingMode();
      Bundle bundle = this.info.metaData;
      this.mRequestFreeformForceHiding =
          bundle != null
              && bundle.getBoolean(
                  "com.samsung.android.sdk.multiwindow.force_hide_floating_multiwindow", false);
    }
    if (this.task == null && getDisplayContent() != null) {
      getDisplayContent().mClosingApps.remove(this);
    }
    Task rootTask = getRootTask();
    updateAnimatingActivityRegistry();
    Task task3 = this.task;
    if (task3 == this.mLastParentBeforePip && task3 != null) {
      this.mAtmService.mWindowOrganizerController.mTaskFragmentOrganizerController
          .onActivityReparentedToTask(this);
      clearLastParentBeforePip();
      setWindowingMode(0);
    }
    if (CoreRune.MW_EMBED_ACTIVITY && isActivityReparentToEmbeddedTask() && this.task != null) {
      this.mAtmService.mWindowOrganizerController.mTaskFragmentOrganizerController
          .onActivityReparentedToTask(this);
      setActivityReparentToEmbeddedTask(false);
    }
    updateColorTransform();
    if (taskFragment != null) {
      taskFragment.cleanUpActivityReferences(this);
      this.mRequestedLaunchingTaskFragmentToken = null;
    }
    if (taskFragment2 != null) {
      if (isState(State.RESUMED)) {
        taskFragment2.setResumedActivity(this, "onParentChanged");
      }
      this.mLetterboxUiController.updateInheritedLetterbox();
    }
    if (rootTask != null && rootTask.topRunningActivity() == this && this.firstWindowDrawn) {
      rootTask.setHasBeenVisible(true);
    }
    updateUntrustedEmbeddingInputProtection();
  }

  @Override // com.android.server.wm.WindowContainer
  public void setSurfaceControl(SurfaceControl surfaceControl) {
    super.setSurfaceControl(surfaceControl);
    if (surfaceControl != null) {
      this.mLastDropInputMode = 0;
      updateUntrustedEmbeddingInputProtection();
    }
  }

  public void setDropInputForAnimation(boolean z) {
    if (this.mIsInputDroppedForAnimation == z) {
      return;
    }
    this.mIsInputDroppedForAnimation = z;
    updateUntrustedEmbeddingInputProtection();
  }

  public final void updateUntrustedEmbeddingInputProtection() {
    if (getSurfaceControl() == null) {
      return;
    }
    if (this.mIsInputDroppedForAnimation) {
      setDropInputMode(1);
    } else if (isEmbeddedInUntrustedMode()) {
      setDropInputMode(2);
    } else {
      setDropInputMode(0);
    }
  }

  public void setDropInputMode(int i) {
    if (this.mLastDropInputMode != i) {
      this.mLastDropInputMode = i;
      ((SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get())
          .setDropInputMode(getSurfaceControl(), i)
          .apply();
    }
  }

  public final boolean isEmbeddedInUntrustedMode() {
    if (getOrganizedTaskFragment() == null) {
      return false;
    }
    return !r0.isAllowedToEmbedActivityInTrustedMode(this);
  }

  public void updateAnimatingActivityRegistry() {
    Task rootTask = getRootTask();
    AnimatingActivityRegistry animatingActivityRegistry =
        rootTask != null ? rootTask.getAnimatingActivityRegistry() : null;
    AnimatingActivityRegistry animatingActivityRegistry2 = this.mAnimatingActivityRegistry;
    if (animatingActivityRegistry2 != null
        && animatingActivityRegistry2 != animatingActivityRegistry) {
      animatingActivityRegistry2.notifyFinished(this);
    }
    this.mAnimatingActivityRegistry = animatingActivityRegistry;
  }

  public void setLastParentBeforePip(ActivityRecord activityRecord) {
    Task task;
    TaskFragment organizedTaskFragment;
    if (activityRecord == null) {
      task = getTask();
    } else {
      task = activityRecord.getTask();
    }
    this.mLastParentBeforePip = task;
    task.mChildPipActivity = this;
    this.mLaunchIntoPipHostActivity = activityRecord;
    if (activityRecord == null) {
      organizedTaskFragment = getOrganizedTaskFragment();
    } else {
      organizedTaskFragment = activityRecord.getOrganizedTaskFragment();
    }
    this.mLastTaskFragmentOrganizerBeforePip =
        organizedTaskFragment != null ? organizedTaskFragment.getTaskFragmentOrganizer() : null;
  }

  public void clearLastParentBeforePip() {
    Task task = this.mLastParentBeforePip;
    if (task != null) {
      task.mChildPipActivity = null;
      this.mLastParentBeforePip = null;
    }
    this.mLaunchIntoPipHostActivity = null;
    this.mLastTaskFragmentOrganizerBeforePip = null;
  }

  public Task getLastParentBeforePip() {
    return this.mLastParentBeforePip;
  }

  public final void updateColorTransform() {
    if (this.mSurfaceControl == null || this.mLastAppSaturationInfo == null) {
      return;
    }
    SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
    SurfaceControl surfaceControl = this.mSurfaceControl;
    AppSaturationInfo appSaturationInfo = this.mLastAppSaturationInfo;
    pendingTransaction.setColorTransform(
        surfaceControl, appSaturationInfo.mMatrix, appSaturationInfo.mTranslation);
    this.mWmService.scheduleAnimationLocked();
  }

  @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
  public void onDisplayChanged(DisplayContent displayContent) {
    UiModeManagerInternal uiModeManagerInternal;
    DisplayContent displayContent2 = this.mDisplayContent;
    super.onDisplayChanged(displayContent);
    DisplayContent displayContent3 = this.mDisplayContent;
    if (displayContent2 == displayContent3) {
      return;
    }
    displayContent3.onRunningActivityChanged();
    if (displayContent2 == null
        && displayContent.getDisplayId() == 0
        && !this.mLastReportedConfiguration.getGlobalConfiguration().isNightModeActive()
        && (uiModeManagerInternal =
                (UiModeManagerInternal) LocalServices.getService(UiModeManagerInternal.class))
            != null) {
      if (uiModeManagerInternal.applyPackageNightModeIfNeeded(this.packageName, this.mUserId)
          || uiModeManagerInternal.needToShowNightModeDialog(this.packageName, this.mUserId)) {
        this.mShouldShowPackageNightModeDialog = true;
      }
      if (this.mShouldShowPackageNightModeDialog) {
        uiModeManagerInternal.setNightModeDialogShown(this.packageName, this.mUserId);
      }
      if (uiModeManagerInternal.shouldIgnoreDialog()) {
        this.mShouldShowPackageNightModeDialog = false;
      }
    }
    if (displayContent2 == null) {
      return;
    }
    displayContent2.onRunningActivityChanged();
    this.mTransitionController.collect(this);
    if (displayContent2.mOpeningApps.remove(this)) {
      this.mDisplayContent.mOpeningApps.add(this);
      this.mDisplayContent.transferAppTransitionFrom(displayContent2);
      this.mDisplayContent.executeAppTransition();
    }
    displayContent2.mClosingApps.remove(this);
    displayContent2.getDisplayPolicy().removeRelaunchingApp(this);
    if (displayContent2.mFocusedApp == this) {
      displayContent2.setFocusedApp(null);
      if (displayContent.getTopMostActivity() == this) {
        displayContent.setFocusedApp(this);
      }
    }
    if (displayContent2.mInputMethodSurfaceParentContainer == this) {
      displayContent2.updateImeControlTarget(true);
    }
    this.mLetterboxUiController.onMovedToDisplay(this.mDisplayContent.getDisplayId());
  }

  public void layoutLetterbox(WindowState windowState) {
    this.mLetterboxUiController.layoutLetterbox(windowState);
  }

  public boolean hasWallpaperBackgroundForLetterbox() {
    return this.mLetterboxUiController.hasWallpaperBackgroundForLetterbox();
  }

  public void updateLetterboxSurface(
      WindowState windowState, SurfaceControl.Transaction transaction) {
    this.mLetterboxUiController.updateLetterboxSurface(windowState, transaction);
  }

  public void updateLetterboxSurface(WindowState windowState) {
    this.mLetterboxUiController.updateLetterboxSurface(windowState);
  }

  public Rect getLetterboxInsets() {
    return this.mLetterboxUiController.getLetterboxInsets();
  }

  public void getLetterboxInnerBounds(Rect rect) {
    this.mLetterboxUiController.getLetterboxInnerBounds(rect);
  }

  public void updateCameraCompatState(
      boolean z, boolean z2, ICompatCameraControlCallback iCompatCameraControlCallback) {
    if (isCameraCompatControlEnabled()) {
      if (this.mCameraCompatControlClickedByUser && (z || this.mCameraCompatControlState == 3)) {
        return;
      }
      this.mCompatCameraControlCallback = iCompatCameraControlCallback;
      int i = !z ? 0 : z2 ? 2 : 1;
      if (setCameraCompatControlState(i)) {
        this.mTaskSupervisor
            .getActivityMetricsLogger()
            .logCameraCompatControlAppearedEventReported(i, this.info.applicationInfo.uid);
        if (i == 0) {
          this.mCameraCompatControlClickedByUser = false;
          this.mCompatCameraControlCallback = null;
        }
        getTask().dispatchTaskInfoChangedIfNeeded(true);
        getDisplayContent().setLayoutNeeded();
        this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
      }
    }
  }

  public void updateCameraCompatStateFromUser(int i) {
    if (isCameraCompatControlEnabled()) {
      if (i == 0) {
        Slog.w("ActivityTaskManager", "Unexpected hidden state in updateCameraCompatState");
        return;
      }
      boolean cameraCompatControlState = setCameraCompatControlState(i);
      this.mCameraCompatControlClickedByUser = true;
      if (cameraCompatControlState) {
        this.mTaskSupervisor
            .getActivityMetricsLogger()
            .logCameraCompatControlClickedEventReported(i, this.info.applicationInfo.uid);
        if (i == 3) {
          this.mCompatCameraControlCallback = null;
          return;
        }
        ICompatCameraControlCallback iCompatCameraControlCallback =
            this.mCompatCameraControlCallback;
        if (iCompatCameraControlCallback == null) {
          Slog.w("ActivityTaskManager", "Callback for a camera compat control is null");
          return;
        }
        try {
          if (i == 2) {
            iCompatCameraControlCallback.applyCameraCompatTreatment();
          } else {
            iCompatCameraControlCallback.revertCameraCompatTreatment();
          }
        } catch (RemoteException e) {
          Slog.e("ActivityTaskManager", "Unable to apply or revert camera compat treatment", e);
        }
      }
    }
  }

  public final boolean setCameraCompatControlState(int i) {
    if (!isCameraCompatControlEnabled() || this.mCameraCompatControlState == i) {
      return false;
    }
    this.mCameraCompatControlState = i;
    return true;
  }

  public int getCameraCompatControlState() {
    return this.mCameraCompatControlState;
  }

  public boolean isCameraCompatControlEnabled() {
    return this.mCameraCompatControlEnabled;
  }

  public boolean isFullyTransparentBarAllowed(Rect rect) {
    if (isLayoutNeededInUdcCutout()) {
      rect = this.mDisplayContent.mUdcCutoutPolicy.getIntersectedCutout(rect);
    }
    return this.mLetterboxUiController.isFullyTransparentBarAllowed(rect);
  }

  public class Token extends Binder {
    public WeakReference mActivityRef;

    public String toString() {
      return "Token{"
          + Integer.toHexString(System.identityHashCode(this))
          + " "
          + this.mActivityRef.get()
          + "}";
    }
  }

  public static ActivityRecord forToken(IBinder iBinder) {
    if (iBinder == null) {
      return null;
    }
    try {
      return (ActivityRecord) ((Token) iBinder).mActivityRef.get();
    } catch (ClassCastException e) {
      Slog.w("ActivityTaskManager", "Bad activity token: " + iBinder, e);
      return null;
    }
  }

  public static ActivityRecord forTokenLocked(IBinder iBinder) {
    ActivityRecord forToken = forToken(iBinder);
    if (forToken == null || forToken.getRootTask() == null) {
      return null;
    }
    return forToken;
  }

  public static boolean isResolverActivity(String str) {
    return ResolverActivity.class.getName().equals(str);
  }

  public boolean isResolverOrDelegateActivity() {
    return isResolverActivity(this.mActivityComponent.getClassName())
        || Objects.equals(
            this.mActivityComponent, this.mAtmService.mTaskSupervisor.getSystemChooserActivity());
  }

  public boolean isResolverOrChildActivity() {
    if (!"android".equals(this.packageName)) {
      return false;
    }
    try {
      return ResolverActivity.class.isAssignableFrom(
          Object.class.getClassLoader().loadClass(this.mActivityComponent.getClassName()));
    } catch (ClassNotFoundException unused) {
      return false;
    }
  }

  /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
  public ActivityRecord(
      ActivityTaskManagerService activityTaskManagerService,
      WindowProcessController windowProcessController,
      int i,
      int i2,
      String str,
      String str2,
      Intent intent,
      String str3,
      ActivityInfo activityInfo,
      Configuration configuration,
      ActivityRecord activityRecord,
      String str4,
      int i3,
      boolean z,
      boolean z2,
      ActivityTaskSupervisor activityTaskSupervisor,
      ActivityOptions activityOptions,
      ActivityRecord activityRecord2,
      PersistableBundle persistableBundle,
      ActivityManager.TaskDescription taskDescription,
      long j) {
    super(activityTaskManagerService.mWindowManager, new Token(), 2, true, null, false);
    boolean z3;
    boolean z4;
    Bundle bundle;
    boolean z5;
    Bundle bundle2;
    Bundle bundle3;
    int i4;
    String str5;
    int i5;
    ActivityOptions activityOptions2 = activityOptions;
    this.mHandoverLaunchDisplayId = -1;
    this.createTime = System.currentTimeMillis();
    this.mHaveState = true;
    this.pictureInPictureArgs = new PictureInPictureParams.Builder().build();
    this.mSplashScreenStyleSolidColor = false;
    this.mPauseSchedulePendingForPip = false;
    this.mAutoEnteringPip = false;
    this.mTaskOverlay = false;
    this.mRelaunchReason = 0;
    this.mForceSendResultForMediaProjection = false;
    this.mRemovingFromDisplay = false;
    this.mIsActivityReparentToEmbeddedTask = false;
    this.mReportedVisibilityResults = new WindowState.UpdateReportedVisibilityResults();
    this.mCurrentLaunchCanTurnScreenOn = true;
    this.mInputDispatchingTimeoutMillis = InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
    this.mLastTransactionSequence = Long.MIN_VALUE;
    this.mLastAllReadyAtSync = false;
    this.mSizeCompatScale = 1.0f;
    this.mInSizeCompatModeForBounds = false;
    this.mIsAspectRatioApplied = false;
    this.mCameraCompatControlState = 0;
    this.mEnableRecentsScreenshot = true;
    this.mLastDropInputMode = 0;
    this.mTransferringSplashScreenState = 0;
    this.mRotationAnimationHint = -1;
    this.mIsDexCompatEnabled = false;
    this.mProcessAppLaunchPolicy = 0;
    this.mIsAllowedSeamlessRotation = false;
    this.mSourceSplitActivity = null;
    this.mTargetSplitActivities = new ArrayList();
    this.mRequestFinishFromSourceActivity = false;
    this.mRestoreActivityGroupZone = -1;
    this.mRelativeActivity = null;
    this.mKeepScreenWhenFolding = false;
    ColorDisplayService.ColorTransformController colorTransformController =
        new ColorDisplayService
            .ColorTransformController() { // from class:
                                          // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda24
          @Override // com.android.server.display.color.ColorDisplayService.ColorTransformController
          public final void applyAppSaturation(float[] fArr, float[] fArr2) {
            ActivityRecord.this.lambda$new$2(fArr, fArr2);
          }
        };
    this.mColorTransformController = colorTransformController;
    this.mTmpConfig = new Configuration();
    this.mTmpBounds = new Rect();
    this.assistToken = new Binder();
    this.shareableActivityToken = new Binder();
    this.mPauseTimeoutRunnable =
        new Runnable() { // from class: com.android.server.wm.ActivityRecord.1
          @Override // java.lang.Runnable
          public void run() {
            Slog.w("ActivityTaskManager", "Activity pause timeout for " + ActivityRecord.this);
            WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityRecord.this.mAtmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
              try {
                if (!ActivityRecord.this.hasProcess()) {
                  WindowManagerService.resetPriorityAfterLockedSection();
                  return;
                }
                ActivityRecord activityRecord3 = ActivityRecord.this;
                activityRecord3.mAtmService.logAppTooSlow(
                    activityRecord3.app,
                    activityRecord3.pauseTime,
                    "pausing " + ActivityRecord.this);
                ActivityRecord.this.activityPaused(true);
                WindowManagerService.resetPriorityAfterLockedSection();
              } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
              }
            }
          }
        };
    this.mLaunchTickRunnable =
        new Runnable() { // from class: com.android.server.wm.ActivityRecord.2
          @Override // java.lang.Runnable
          public void run() {
            WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityRecord.this.mAtmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
              try {
                if (ActivityRecord.this.continueLaunchTicking()) {
                  ActivityRecord activityRecord3 = ActivityRecord.this;
                  activityRecord3.mAtmService.logAppTooSlow(
                      activityRecord3.app,
                      activityRecord3.launchTickTime,
                      "launching " + ActivityRecord.this);
                }
              } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
              }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        };
    this.mDestroyTimeoutRunnable =
        new Runnable() { // from class: com.android.server.wm.ActivityRecord.3
          @Override // java.lang.Runnable
          public void run() {
            WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityRecord.this.mAtmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
              try {
                Slog.w(
                    "ActivityTaskManager", "Activity destroy timeout for " + ActivityRecord.this);
                if (FreecessController.getInstance().getFreecessEnabled()) {
                  FreecessController.getInstance()
                      .protectFreezePackage(
                          ActivityRecord.this.info.applicationInfo.uid,
                          "ActivityDestroyTimeout",
                          20000L);
                }
                ActivityRecord.this.destroyed("destroyTimeout");
              } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
              }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        };
    this.mStopTimeoutRunnable =
        new Runnable() { // from class: com.android.server.wm.ActivityRecord.4
          @Override // java.lang.Runnable
          public void run() {
            WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityRecord.this.mAtmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
              try {
                Slog.w("ActivityTaskManager", "Activity stop timeout for " + ActivityRecord.this);
                if (ActivityRecord.this.isInHistory()) {
                  ActivityRecord.this.activityStopped(null, null, null);
                }
              } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
              }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        };
    this.mHiddenWhileEnteringPinnedTimeoutRunnable =
        new Runnable() { // from class: com.android.server.wm.ActivityRecord.5
          @Override // java.lang.Runnable
          public void run() {
            if (CoreRune.MW_PIP_SHELL_TRANSITION) {
              WindowManagerGlobalLock windowManagerGlobalLock =
                  ActivityRecord.this.mAtmService.mGlobalLock;
              WindowManagerService.boostPriorityForLockedSection();
              synchronized (windowManagerGlobalLock) {
                try {
                  Slog.w("ActivityTaskManager", "enter pip timeout for  " + ActivityRecord.this);
                  ActivityRecord.this.setHiddenWhileEnteringPinnedMode(false, "timeout");
                } catch (Throwable th) {
                  WindowManagerService.resetPriorityAfterLockedSection();
                  throw th;
                }
              }
              WindowManagerService.resetPriorityAfterLockedSection();
            }
          }
        };
    this.mAddStartingWindow = new AddStartingWindow();
    this.mTransferSplashScreenTimeoutRunnable =
        new Runnable() { // from class: com.android.server.wm.ActivityRecord.6
          @Override // java.lang.Runnable
          public void run() {
            WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityRecord.this.mAtmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
              try {
                Slog.w(
                    "ActivityTaskManager",
                    "Activity transferring splash screen timeout for "
                        + ActivityRecord.this
                        + " state "
                        + ActivityRecord.this.mTransferringSplashScreenState);
                if (ActivityRecord.this.isTransferringSplashScreen()) {
                  ActivityRecord activityRecord3 = ActivityRecord.this;
                  activityRecord3.mTransferringSplashScreenState = 3;
                  activityRecord3.removeStartingWindow();
                }
              } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
              }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        };
    this.mIsPrelMode = false;
    this.mShouldPrelNotify = false;
    this.mAtmService = activityTaskManagerService;
    ((Token) this.token).mActivityRef = new WeakReference(this);
    this.info = activityInfo;
    int userId = UserHandle.getUserId(activityInfo.applicationInfo.uid);
    this.mUserId = userId;
    String str6 = activityInfo.applicationInfo.packageName;
    this.packageName = str6;
    this.intent = intent;
    this.mCompatRecord = new BoundsCompatRecord(this);
    this.mPopOverState = new PopOverState(this);
    String str7 = activityInfo.targetActivity;
    if (str7 == null
        || (str7.equals(intent.getComponent().getClassName())
            && ((i5 = activityInfo.launchMode) == 0 || i5 == 1))) {
      this.mActivityComponent = intent.getComponent();
    } else {
      this.mActivityComponent =
          new ComponentName(activityInfo.packageName, activityInfo.targetActivity);
    }
    this.mLetterboxUiController = new LetterboxUiController(this.mWmService, this);
    this.mCameraCompatControlEnabled =
        this.mWmService
            .mContext
            .getResources()
            .getBoolean(R.bool.config_fingerprintSupportsGestures);
    this.mTargetSdk = activityInfo.applicationInfo.targetSdkVersion;
    this.mShowForAllUsers = (activityInfo.flags & 1024) != 0;
    setOrientation(activityInfo.screenOrientation);
    this.mRotationAnimationHint = activityInfo.rotationAnimation;
    int i6 = activityInfo.flags;
    this.mShowWhenLocked = (i6 & 8388608) != 0;
    this.mInheritShownWhenLocked = (activityInfo.privateFlags & 1) != 0;
    this.mTurnScreenOn = (i6 & 16777216) != 0;
    int themeResource = activityInfo.getThemeResource();
    AttributeCache.Entry entry =
        AttributeCache.instance()
            .get(
                str6,
                themeResource == 0
                    ? activityInfo.applicationInfo.targetSdkVersion < 11
                        ? R.style.Theme
                        : R.style.Theme.Holo
                    : themeResource,
                com.android.internal.R.styleable.Window,
                userId);
    if (entry != null) {
      boolean z6 =
          !ActivityInfo.isTranslucentOrFloating(entry.array) || entry.array.getBoolean(14, false);
      this.mOccludesParent = z6;
      this.mStyleFillsParent = z6;
      if (CoreRune.FW_SUPPORT_OCCLUDES_PARENT_CHANGE_CALLBACK) {
        this.mStyleFloating = entry.array.getBoolean(4, false);
      }
      if (!this.mOccludesParent) {
        Slog.d(
            "ActivityTaskManager",
            "ActivityRecord, mOccludesParent of "
                + this
                + " is set to false, isTranslucentOrFloating="
                + ActivityInfo.isTranslucentOrFloating(entry.array)
                + " mStyleFloating="
                + this.mStyleFloating);
      }
      this.noDisplay = entry.array.getBoolean(10, false);
    } else {
      this.mOccludesParent = true;
      this.mStyleFillsParent = true;
      this.noDisplay = false;
    }
    if (activityOptions2 != null) {
      this.mLaunchTaskBehind = activityOptions.getLaunchTaskBehind();
      int rotationAnimationHint = activityOptions.getRotationAnimationHint();
      if (rotationAnimationHint >= 0) {
        this.mRotationAnimationHint = rotationAnimationHint;
      }
      if (activityOptions.getLaunchIntoPipParams() != null) {
        this.pictureInPictureArgs = activityOptions.getLaunchIntoPipParams();
        if (activityRecord2 != null) {
          adjustPictureInPictureParamsIfNeeded(activityRecord2.getBounds());
        }
      }
      this.mOverrideTaskTransition = activityOptions.getOverrideTaskTransition();
      this.mDismissKeyguard = activityOptions.getDismissKeyguard();
      this.mShareIdentity = activityOptions.isShareIdentityEnabled();
    }
    ((ColorDisplayService.ColorDisplayServiceInternal)
            LocalServices.getService(ColorDisplayService.ColorDisplayServiceInternal.class))
        .attachColorTransformController(str6, userId, new WeakReference(colorTransformController));
    this.mRootWindowContainer = activityTaskManagerService.mRootWindowContainer;
    this.launchedFromPid = i;
    this.launchedFromUid = i2;
    this.launchedFromPackage = str;
    this.launchedFromFeatureId = str2;
    this.mLaunchSourceType = determineLaunchSourceType(i2, windowProcessController);
    this.shortComponentName = intent.getComponent().flattenToShortString();
    this.resolvedType = str3;
    this.componentSpecified = z;
    this.rootVoiceInteraction = z2;
    this.mLastReportedConfiguration = new MergedConfiguration(configuration);
    this.resultTo = activityRecord;
    this.resultWho = str4;
    this.requestCode = i3;
    setState(State.INITIALIZING, "ActivityRecord ctor");
    this.launchFailed = false;
    this.delayedResume = false;
    this.finishing = false;
    this.deferRelaunchUntilPaused = false;
    this.keysPaused = false;
    this.inHistory = false;
    this.nowVisible = false;
    super.setClientVisible(true);
    this.idle = false;
    this.hasBeenLaunched = false;
    this.mTaskSupervisor = activityTaskSupervisor;
    String str8 = activityInfo.taskAffinity;
    this.taskAffinityWithoutUid = str8;
    String computeTaskAffinity =
        computeTaskAffinity(
            str8,
            activityInfo.applicationInfo.uid,
            activityInfo.launchMode,
            this.mActivityComponent);
    activityInfo.taskAffinity = computeTaskAffinity;
    this.taskAffinity = computeTaskAffinity;
    String num = Integer.toString(activityInfo.applicationInfo.uid);
    ActivityInfo.WindowLayout windowLayout = activityInfo.windowLayout;
    if (windowLayout != null
        && (str5 = windowLayout.windowLayoutAffinity) != null
        && !str5.startsWith(num)) {
      activityInfo.windowLayout.windowLayoutAffinity =
          num + XmlUtils.STRING_ARRAY_SEPARATOR + activityInfo.windowLayout.windowLayoutAffinity;
    }
    if (sConstrainDisplayApisConfig == null) {
      sConstrainDisplayApisConfig = new ConstrainDisplayApisConfig();
    }
    this.stateNotNeeded = (activityInfo.flags & 16) != 0;
    CharSequence charSequence = activityInfo.nonLocalizedLabel;
    this.nonLocalizedLabel = charSequence;
    int i7 = activityInfo.labelRes;
    this.labelRes = i7;
    if (charSequence == null && i7 == 0) {
      ApplicationInfo applicationInfo = activityInfo.applicationInfo;
      this.nonLocalizedLabel = applicationInfo.nonLocalizedLabel;
      this.labelRes = applicationInfo.labelRes;
    }
    this.icon = activityInfo.getIconResource();
    this.theme = activityInfo.getThemeResource();
    int i8 = activityInfo.flags;
    if ((i8 & 1) != 0
        && windowProcessController != null
        && ((i4 = activityInfo.applicationInfo.uid) == 1000
            || i4 == windowProcessController.mInfo.uid)) {
      this.processName = windowProcessController.mName;
    } else {
      this.processName = activityInfo.processName;
    }
    if ((i8 & 32) != 0) {
      intent.addFlags(8388608);
    }
    this.launchMode = activityInfo.launchMode;
    Bundle bundle4 = activityInfo.metaData;
    if (bundle4 != null && bundle4.getBoolean("com.samsung.android.multiwindow.nonresizeable")) {
      activityInfo.resizeMode = 10;
    }
    activityTaskManagerService.mMwSupportPolicyController.lambda$updateAllTasksLocked$0(this);
    if (activityRecord2 != null && activityRecord2.mIsAliasActivity) {
      this.mAliasChild = true;
    }
    setActivityType(z, i2, intent, activityOptions, activityRecord2);
    this.immersive = (activityInfo.flags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0;
    String str9 = activityInfo.requestedVrComponent;
    this.requestedVrComponent = str9 == null ? null : ComponentName.unflattenFromString(str9);
    this.lockTaskLaunchMode = getLockTaskLaunchMode(activityInfo, activityOptions2);
    if (activityOptions2 != null) {
      setOptions(activityOptions2);
      this.mHasSceneTransition =
          activityOptions.getAnimationType() == 5 && activityOptions.getResultReceiver() != null;
      PendingIntent usageTimeReport = activityOptions.getUsageTimeReport();
      if (usageTimeReport != null) {
        this.appTimeTracker = new AppTimeTracker(usageTimeReport);
      }
      WindowContainerToken launchTaskDisplayArea = activityOptions.getLaunchTaskDisplayArea();
      this.mHandoverTaskDisplayArea =
          launchTaskDisplayArea != null
              ? (TaskDisplayArea) WindowContainer.fromBinder(launchTaskDisplayArea.asBinder())
              : null;
      this.mHandoverLaunchDisplayId = activityOptions.getLaunchDisplayId();
      this.mLaunchCookie = activityOptions.getLaunchCookie();
      this.mLaunchRootTask = activityOptions.getLaunchRootTask();
      if (CoreRune.FW_CHN_PREMIUM_WATCH && activityOptions.isNoTransitionOcclusion()) {
        z3 = true;
        this.mNoTransitionOcclusion = true;
      } else {
        z3 = true;
      }
    } else {
      z3 = true;
      this.mHasSceneTransition = false;
    }
    this.mPopOverState.setupOptions(
        activityOptions2 == null ? ActivityOptions.makeBasic() : activityOptions2, activityRecord2);
    this.mPopOverState.setLastOccludesParent(this.mOccludesParent);
    Bundle bundle5 = activityInfo.metaData;
    if (bundle5 != null) {
      z4 = false;
      this.mShowWhenLocked =
          bundle5.getBoolean("com.samsung.android.activity.showWhenLocked", false)
              | this.mShowWhenLocked;
      this.mTurnScreenOn |=
          activityInfo.metaData.getBoolean("com.samsung.android.activity.turnScreenOn", false);
      this.mIsAllowedSeamlessRotation =
          activityInfo.metaData.getBoolean("com.samsung.android.allow_seamless_rotation", false);
      if (CoreRune.FW_APPLOCK && (bundle3 = activityInfo.applicationInfo.metaData) != null) {
        this.mNotToBeTopForAppLockException =
            bundle3.getBoolean(
                "com.samsung.android.applock.not_to_be_top_for_AppLock_exception", false);
      }
    } else {
      z4 = false;
    }
    Bundle bundle6 = activityInfo.metaData;
    this.mKeepSnapshotCache =
        ((bundle6 == null || !bundle6.getBoolean("com.samsung.android.keep-snapshotcache", z4))
                && ((bundle = activityInfo.applicationInfo.metaData) == null
                    || !bundle.getBoolean("com.samsung.android.keep-snapshotcache", z4)))
            ? false
            : z3;
    UdcCutoutPolicy.updateUseLayoutInUdcCutoutIfNeeded(this);
    this.mPersistentState = persistableBundle;
    this.taskDescription = taskDescription;
    this.shouldDockBigOverlays =
        this.mWmService
            .mContext
            .getResources()
            .getBoolean(R.bool.config_defaultEmergencyGestureSoundEnabled);
    if (j > 0) {
      this.createTime = j;
    }
    activityTaskManagerService.mPackageConfigPersister.updateConfigIfNeeded(this, userId, str6);
    this.mActivityRecordInputSink = new ActivityRecordInputSink(this, activityRecord2);
    updateEnterpriseThumbnailDrawable(activityTaskManagerService.getUiContext());
    if (WindowManager.hasWindowExtensionsEnabled()) {
      if (activityTaskManagerService
          .mContext
          .getPackageManager()
          .getProperty("android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED", str6)
          .getBoolean()) {
        z5 = z3;
        this.mAppActivityEmbeddingSplitsEnabled = z5;
        ActivityInfo activityInfo2 = this.info;
        this.mTransientBarShowingDelayMillis = activityInfo2.transientBarShowingDelayMillis;
        Bundle bundle7 = activityInfo2.metaData;
        this.mDisableSnapshot =
            (!(bundle7 == null && bundle7.getBoolean("com.samsung.android.disableSnapshot", false))
                    && ((bundle2 = this.info.applicationInfo.metaData) == null
                        || !bundle2.getBoolean("com.samsung.android.disableSnapshot", false)))
                ? false
                : z3;
      }
    }
    z5 = false;
    this.mAppActivityEmbeddingSplitsEnabled = z5;
    ActivityInfo activityInfo22 = this.info;
    this.mTransientBarShowingDelayMillis = activityInfo22.transientBarShowingDelayMillis;
    Bundle bundle72 = activityInfo22.metaData;
    this.mDisableSnapshot =
        (!(bundle72 == null && bundle72.getBoolean("com.samsung.android.disableSnapshot", false))
                && ((bundle2 = this.info.applicationInfo.metaData) == null
                    || !bundle2.getBoolean("com.samsung.android.disableSnapshot", false)))
            ? false
            : z3;
  }

  public static String computeTaskAffinity(String str, int i, int i2, ComponentName componentName) {
    String num = Integer.toString(i);
    if (str == null || str.startsWith(num)) {
      return str;
    }
    String str2 = num + XmlUtils.STRING_ARRAY_SEPARATOR + str;
    if (i2 != 3 || componentName == null) {
      return str2;
    }
    return str2 + XmlUtils.STRING_ARRAY_SEPARATOR + componentName.hashCode();
  }

  public static int getLockTaskLaunchMode(
      ActivityInfo activityInfo, ActivityOptions activityOptions) {
    int i = activityInfo.lockTaskLaunchMode;
    if (!activityInfo.applicationInfo.isPrivilegedApp() && (i == 2 || i == 1)) {
      i = 0;
    }
    if (activityOptions != null && activityOptions.getLockTaskMode() && i == 0) {
      return 3;
    }
    return i;
  }

  public InputApplicationHandle getInputApplicationHandle(boolean z) {
    if (this.mInputApplicationHandle == null) {
      this.mInputApplicationHandle =
          new InputApplicationHandle(this.token, toString(), this.mInputDispatchingTimeoutMillis);
    } else if (z) {
      String activityRecord = toString();
      long j = this.mInputDispatchingTimeoutMillis;
      InputApplicationHandle inputApplicationHandle = this.mInputApplicationHandle;
      if (j != inputApplicationHandle.dispatchingTimeoutMillis
          || !activityRecord.equals(inputApplicationHandle.name)) {
        this.mInputApplicationHandle =
            new InputApplicationHandle(
                this.token, activityRecord, this.mInputDispatchingTimeoutMillis);
      }
    }
    return this.mInputApplicationHandle;
  }

  public void setProcess(WindowProcessController windowProcessController) {
    this.app = windowProcessController;
    Task task = this.task;
    if ((task != null ? task.getRootActivity() : null) == this) {
      this.task.setRootProcess(windowProcessController);
    }
    windowProcessController.addActivityIfNeeded(this);
    this.mInputDispatchingTimeoutMillis =
        ActivityTaskManagerService.getInputDispatchingTimeoutMillisLocked(this);
    TaskFragment taskFragment = getTaskFragment();
    if (taskFragment != null) {
      taskFragment.sendTaskFragmentInfoChanged();
    }
  }

  public boolean hasProcess() {
    return this.app != null;
  }

  public boolean attachedToProcess() {
    return hasProcess() && this.app.hasThread();
  }

  public final int evaluateStartingWindowTheme(
      ActivityRecord activityRecord, String str, int i, int i2) {
    if (validateStartingWindowTheme(activityRecord, str, i)) {
      return (i2 == 0 || !validateStartingWindowTheme(activityRecord, str, i2)) ? i : i2;
    }
    return 0;
  }

  public final boolean launchedFromSystemSurface() {
    int i = this.mLaunchSourceType;
    return i == 1 || i == 2 || i == 3;
  }

  public boolean isLaunchSourceType(int i) {
    return this.mLaunchSourceType == i;
  }

  public final int determineLaunchSourceType(
      int i, WindowProcessController windowProcessController) {
    if (i == 1000 || i == 0) {
      return 1;
    }
    if (windowProcessController == null) {
      return 4;
    }
    if (windowProcessController.isHomeProcess()) {
      return 2;
    }
    return this.mAtmService
            .getSysUiServiceComponentLocked()
            .getPackageName()
            .equals(windowProcessController.mInfo.packageName)
        ? 3
        : 4;
  }

  public final boolean validateStartingWindowTheme(
      ActivityRecord activityRecord, String str, int i) {
    AttributeCache.Entry entry;
    if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
          -1782453012,
          1,
          "Checking theme of starting window: 0x%x",
          new Object[] {Long.valueOf(i)});
    }
    if (i == 0
        || (entry =
                AttributeCache.instance()
                    .get(
                        str,
                        i,
                        com.android.internal.R.styleable.Window,
                        this.mWmService.mCurrentUserId))
            == null) {
      return false;
    }
    boolean z = entry.array.getBoolean(5, false);
    boolean z2 = entry.array.getBoolean(4, false);
    boolean z3 = entry.array.getBoolean(14, false);
    boolean z4 = entry.array.getBoolean(12, false);
    if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
          -124316973,
          0,
          "Translucent=%s Floating=%s ShowWallpaper=%s Disable=%s",
          new Object[] {
            String.valueOf(z), String.valueOf(z2), String.valueOf(z3), String.valueOf(z4)
          });
    }
    if (z || z2) {
      return false;
    }
    if (z3 && getDisplayContent().mWallpaperController.getWallpaperTarget() != null) {
      return false;
    }
    if (!z4 || launchedFromSystemSurface()) {
      return true;
    }
    if (activityRecord != null
        && activityRecord.getActivityType() == 1
        && activityRecord.mTransferringSplashScreenState == 0) {
      if (activityRecord.mStartingData != null) {
        return true;
      }
      if (activityRecord.mStartingWindow != null && activityRecord.mStartingSurface != null) {
        return true;
      }
    }
    return false;
  }

  public boolean addStartingWindow(
      String str,
      int i,
      ActivityRecord activityRecord,
      boolean z,
      boolean z2,
      boolean z3,
      boolean z4,
      boolean z5,
      boolean z6,
      boolean z7,
      boolean z8) {
    StartingData startingData;
    if (!okToDisplay() || this.mStartingData != null) {
      return false;
    }
    WindowState findMainWindow = findMainWindow();
    if (findMainWindow != null
        && (findMainWindow.mWinAnimator.getShown() || findMainWindow.isDrawn())) {
      return false;
    }
    TaskSnapshotController taskSnapshotController = this.mWmService.mTaskSnapshotController;
    Task task = this.task;
    TaskSnapshot snapshot =
        taskSnapshotController.getSnapshot(task.mTaskId, task.mUserId, false, false);
    int startingWindowType = getStartingWindowType(z, z2, z3, z4, z5, z7, snapshot);
    if (z8 && startingWindowType == 2) {
      return false;
    }
    if (this.mWmService.mDragDropController.isDragInProgressByRecents()
        && !this.task.inMultiWindowMode()
        && isResizeable()) {
      Slog.d("ActivityTaskManager", "Ignore to create starting window during drag by Recents.");
      return false;
    }
    int makeStartingWindowTypeParameter =
        StartingSurfaceController.makeStartingWindowTypeParameter(
            z,
            z2,
            z3,
            z4,
            z5,
            z6,
            startingWindowType == 2
                && this.mWmService.mStartingSurfaceController.isExceptionApp(
                    this.packageName,
                    this.mTargetSdk,
                    new Supplier() { // from class:
                                     // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda21
                      @Override // java.util.function.Supplier
                      public final Object get() {
                        ApplicationInfo lambda$addStartingWindow$3;
                        lambda$addStartingWindow$3 =
                            ActivityRecord.this.lambda$addStartingWindow$3();
                        return lambda$addStartingWindow$3;
                      }
                    }),
            z7,
            startingWindowType,
            this.packageName,
            this.mUserId);
    if (startingWindowType == 1) {
      if (isActivityTypeHome()) {
        this.mWmService.mTaskSnapshotController.removeSnapshotCache(this.task.mTaskId);
        if (snapshot.containsBlurLayers()) {
          Slog.w(
              "ActivityTaskManager", "Not add starting window with snapshot contains blur layer");
          return false;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
          Transition collectingTransition = this.mTransitionController.getCollectingTransition();
          if (collectingTransition != null && (collectingTransition.getFlags() & 2) == 0) {
            return false;
          }
        } else if ((this.mDisplayContent.mAppTransition.getTransitFlags() & 2) == 0) {
          return false;
        }
      }
      return createSnapshot(snapshot, makeStartingWindowTypeParameter);
    }
    if (i == 0
        && this.theme != 0
        && (!CoreRune.MW_EMBED_ACTIVITY
            || !isSplitEmbedded()
            || activityRecord == null
            || (startingData = activityRecord.mStartingData) == null
            || startingData.mAssociatedTask != getTask())) {
      return false;
    }
    if (activityRecord != null && transferStartingWindow(activityRecord)) {
      return true;
    }
    if (startingWindowType != 2) {
      return false;
    }
    if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
          2018852077,
          0,
          "Creating SplashScreenStartingData",
          (Object[]) null);
    }
    this.mStartingData =
        new SplashScreenStartingData(this.mWmService, i, makeStartingWindowTypeParameter);
    scheduleAddStartingWindow();
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ ApplicationInfo lambda$addStartingWindow$3() {
    ActivityInfo resolveActivityInfo =
        this.intent.resolveActivityInfo(this.mAtmService.mContext.getPackageManager(), 128);
    if (resolveActivityInfo != null) {
      return resolveActivityInfo.applicationInfo;
    }
    return null;
  }

  public final boolean createSnapshot(TaskSnapshot taskSnapshot, int i) {
    if (taskSnapshot == null) {
      return false;
    }
    if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
          -1340540100,
          0,
          "Creating SnapshotStartingData",
          (Object[]) null);
    }
    this.mStartingData = new SnapshotStartingData(this.mWmService, taskSnapshot, i);
    if (this.task.forAllLeafTaskFragments(new ActivityRecord$$ExternalSyntheticLambda31())) {
      associateStartingDataWithTask();
    }
    scheduleAddStartingWindow();
    return true;
  }

  public void scheduleAddStartingWindow() {
    this.mAddStartingWindow.run();
  }

  public class AddStartingWindow implements Runnable {
    public AddStartingWindow() {}

    @Override // java.lang.Runnable
    public void run() {
      StartingSurfaceController.StartingSurface startingSurface;
      boolean z;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityRecord.this.mWmService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityRecord activityRecord = ActivityRecord.this;
          StartingData startingData = activityRecord.mStartingData;
          if (startingData == null) {
            if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
              ProtoLogImpl.v(
                  ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
                  1836214582,
                  0,
                  "startingData was nulled out before handling mAddStartingWindow: %s",
                  new Object[] {String.valueOf(activityRecord)});
            }
            return;
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          Slog.d(
              "ActivityTaskManager",
              "Add starting "
                  + ActivityRecord.this
                  + ": typeParam="
                  + startingData.mTypeParams
                  + " startingData="
                  + startingData);
          try {
            startingSurface = startingData.createStartingSurface(ActivityRecord.this);
          } catch (Exception e) {
            Slog.w("ActivityTaskManager", "Exception when adding starting window", e);
            startingSurface = null;
          }
          if (startingSurface != null) {
            WindowManagerGlobalLock windowManagerGlobalLock2 =
                ActivityRecord.this.mWmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
              try {
                ActivityRecord activityRecord2 = ActivityRecord.this;
                if (activityRecord2.mStartingData == null) {
                  if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
                    ProtoLogImpl.v(
                        ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
                        1822843721,
                        0,
                        "Aborted starting %s: startingData=%s",
                        new Object[] {
                          String.valueOf(activityRecord2),
                          String.valueOf(ActivityRecord.this.mStartingData)
                        });
                  }
                  ActivityRecord activityRecord3 = ActivityRecord.this;
                  activityRecord3.mStartingWindow = null;
                  activityRecord3.mStartingData = null;
                  z = true;
                } else {
                  activityRecord2.mStartingSurface = startingSurface;
                  z = false;
                }
                if (!z && ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
                  ProtoLogImpl.v(
                      ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
                      -1292329638,
                      0,
                      "Added starting %s: startingWindow=%s startingView=%s",
                      new Object[] {
                        String.valueOf(ActivityRecord.this),
                        String.valueOf(ActivityRecord.this.mStartingWindow),
                        String.valueOf(ActivityRecord.this.mStartingSurface)
                      });
                }
              } finally {
              }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            if (z) {
              startingSurface.remove(false);
              return;
            }
            return;
          }
          if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
            ProtoLogImpl.v(
                ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
                1254403969,
                0,
                "Surface returned was null: %s",
                new Object[] {String.valueOf(ActivityRecord.this)});
          }
        } finally {
          WindowManagerService.resetPriorityAfterLockedSection();
        }
      }
    }
  }

  public final int getStartingWindowType(
      boolean z,
      boolean z2,
      boolean z3,
      boolean z4,
      boolean z5,
      boolean z6,
      TaskSnapshot taskSnapshot) {
    Intent intent;
    ActivityRecord activity;
    if (!z
        && z2
        && z3
        && !z5
        && (intent = this.task.intent) != null
        && this.mActivityComponent.equals(intent.getComponent())
        && (activity =
                this.task.getActivity(
                    new Predicate() { // from class:
                                      // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda26
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        return ((ActivityRecord) obj).attachedToProcess();
                      }
                    }))
            != null) {
      return (activity.isSnapshotCompatible(taskSnapshot)
              && this.mDisplayContent
                      .getDisplayRotation()
                      .rotationForOrientation(
                          getOverrideOrientation(), this.mDisplayContent.getRotation())
                  == taskSnapshot.getRotation())
          ? 1
          : 0;
    }
    boolean z7 =
        isActivityTypeHome()
            || (CoreRune.FW_CUSTOM_SHELL_TRANSITION_SEPARATE_RECENTS && isActivityTypeRecents());
    if ((z || !z3 || (z2 && !z5)) && !z7) {
      if (z3 || z2 || !this.mPopOverState.isActivated()) {
        return (!CoreRune.MW_EMBED_ACTIVITY || z5 || z3 || z2 || !isSplitEmbedded()) ? 2 : 0;
      }
      Slog.v(
          StartingSurfaceController.TAG,
          "Skip splash screen for pop-over. newTask=" + z + ", r=" + this);
      return 0;
    }
    if (z2) {
      if (z4) {
        if (isSnapshotCompatible(taskSnapshot)) {
          return 1;
        }
        if (this.mPopOverState.isActivated()) {
          return 0;
        }
        if (CoreRune.MW_EMBED_ACTIVITY && isSplitEmbedded()) {
          return 0;
        }
        if (!z7) {
          return 2;
        }
      }
      if (!z6 && !z7 && !this.mDisableSnapshot) {
        return 2;
      }
    }
    return 0;
  }

  public boolean isSnapshotCompatible(TaskSnapshot taskSnapshot) {
    if (taskSnapshot == null
        || !taskSnapshot.getTopActivityComponent().equals(this.mActivityComponent)
        || !isSnapshotSizeCompatible(taskSnapshot)) {
      return false;
    }
    if (hasFixedRotationTransform() && taskSnapshot.hasImeSurface()) {
      Slog.d(
          "ActivityTaskManager",
          "IME will be hidden by async rotation cause fixed launching.So, snapshot which include"
              + " ime is not compatible.");
      return false;
    }
    int rotationForActivityInDifferentOrientation =
        this.mDisplayContent.rotationForActivityInDifferentOrientation(this);
    int rotation = this.task.getWindowConfiguration().getRotation();
    if (rotationForActivityInDifferentOrientation == -1) {
      rotationForActivityInDifferentOrientation = rotation;
    }
    if (taskSnapshot.getRotation() != rotationForActivityInDifferentOrientation) {
      return false;
    }
    Rect bounds = this.task.getBounds();
    int width = bounds.width();
    int height = bounds.height();
    Point taskSize = taskSnapshot.getTaskSize();
    if (Math.abs(rotation - rotationForActivityInDifferentOrientation) % 2 == 1) {
      width = height;
      height = width;
    }
    return Math.abs(
            (((float) taskSize.x) / ((float) Math.max(taskSize.y, 1)))
                - (((float) width) / ((float) Math.max(height, 1))))
        <= 0.01f;
  }

  public void setCustomizeSplashScreenExitAnimation(boolean z) {
    if (this.mHandleExitSplashScreen == z) {
      return;
    }
    this.mHandleExitSplashScreen = z;
  }

  public final void scheduleTransferSplashScreenTimeout() {
    this.mAtmService.f1734mH.postDelayed(this.mTransferSplashScreenTimeoutRunnable, 2000L);
  }

  public final void removeTransferSplashScreenTimeout() {
    this.mAtmService.f1734mH.removeCallbacks(this.mTransferSplashScreenTimeoutRunnable);
  }

  public final boolean transferSplashScreenIfNeeded() {
    if (this.finishing
        || !this.mHandleExitSplashScreen
        || this.mStartingSurface == null
        || this.mStartingWindow == null
        || this.mTransferringSplashScreenState == 3) {
      return false;
    }
    if (isTransferringSplashScreen()) {
      return true;
    }
    requestCopySplashScreen();
    return isTransferringSplashScreen();
  }

  public final boolean isTransferringSplashScreen() {
    int i = this.mTransferringSplashScreenState;
    return i == 2 || i == 1;
  }

  public final void requestCopySplashScreen() {
    this.mTransferringSplashScreenState = 1;
    if (!this.mAtmService.mTaskOrganizerController.copySplashScreenView(getTask())) {
      this.mTransferringSplashScreenState = 3;
      removeStartingWindow();
    }
    scheduleTransferSplashScreenTimeout();
  }

  public void onCopySplashScreenFinish(
      SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) {
    WindowState windowState;
    removeTransferSplashScreenTimeout();
    if (splashScreenViewParcelable == null
        || this.mTransferringSplashScreenState != 1
        || (windowState = this.mStartingWindow) == null
        || windowState.mRemoved
        || this.finishing) {
      if (splashScreenViewParcelable != null) {
        splashScreenViewParcelable.clearIfNeeded();
      }
      this.mTransferringSplashScreenState = 3;
      removeStartingWindow();
      return;
    }
    SurfaceControl applyStartingWindowAnimation =
        TaskOrganizerController.applyStartingWindowAnimation(windowState);
    try {
      this.mTransferringSplashScreenState = 2;
      this.mAtmService
          .getLifecycleManager()
          .scheduleTransaction(
              this.app.getThread(),
              this.token,
              (ClientTransactionItem)
                  TransferSplashScreenViewStateItem.obtain(
                      splashScreenViewParcelable, applyStartingWindowAnimation));
      scheduleTransferSplashScreenTimeout();
    } catch (Exception unused) {
      Slog.w("ActivityTaskManager", "onCopySplashScreenComplete fail: " + this);
      this.mStartingWindow.cancelAnimation();
      splashScreenViewParcelable.clearIfNeeded();
      this.mTransferringSplashScreenState = 3;
    }
  }

  public final void onSplashScreenAttachComplete() {
    removeTransferSplashScreenTimeout();
    WindowState windowState = this.mStartingWindow;
    if (windowState != null) {
      windowState.cancelAnimation();
      this.mStartingWindow.hide(false, false);
    }
    this.mTransferringSplashScreenState = 3;
    removeStartingWindowAnimation(false);
  }

  public void cleanUpSplashScreen() {
    if (!this.mHandleExitSplashScreen || this.startingMoved) {
      return;
    }
    int i = this.mTransferringSplashScreenState;
    if (i == 3 || i == 0) {
      if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
            -1003678883,
            0,
            "Cleaning splash screen token=%s",
            new Object[] {String.valueOf(this)});
      }
      this.mAtmService.mTaskOrganizerController.onAppSplashScreenViewRemoved(getTask());
    }
  }

  public boolean isStartingWindowDisplayed() {
    StartingData startingData = this.mStartingData;
    if (startingData == null) {
      Task task = this.task;
      startingData = task != null ? task.mSharedStartingData : null;
    }
    return startingData != null && startingData.mIsDisplayed;
  }

  public void attachStartingWindow(WindowState windowState) {
    StartingData startingData = this.mStartingData;
    windowState.mStartingData = startingData;
    this.mStartingWindow = windowState;
    if (startingData != null) {
      if (startingData.mAssociatedTask != null) {
        attachStartingSurfaceToAssociatedTask();
      } else if (isEmbedded()) {
        associateStartingWindowWithTaskIfNeeded();
      }
    }
  }

  public final void attachStartingSurfaceToAssociatedTask() {
    WindowContainer.overrideConfigurationPropagation(
        this.mStartingWindow, this.mStartingData.mAssociatedTask);
    getSyncTransaction()
        .reparent(
            this.mStartingWindow.mSurfaceControl,
            this.mStartingData.mAssociatedTask.mSurfaceControl);
  }

  public final void associateStartingDataWithTask() {
    StartingData startingData = this.mStartingData;
    Task task = this.task;
    startingData.mAssociatedTask = task;
    task.mSharedStartingData = startingData;
  }

  public void associateStartingWindowWithTaskIfNeeded() {
    StartingData startingData;
    if (this.mStartingWindow == null
        || (startingData = this.mStartingData) == null
        || startingData.mAssociatedTask != null) {
      return;
    }
    if (!CoreRune.MW_EMBED_ACTIVITY || isSplitEmbedded()) {
      associateStartingDataWithTask();
      attachStartingSurfaceToAssociatedTask();
    }
  }

  public void removeStartingWindow() {
    boolean isEligibleForLetterboxEducation = isEligibleForLetterboxEducation();
    if (transferSplashScreenIfNeeded()) {
      return;
    }
    removeStartingWindowAnimation(true);
    Task task = getTask();
    if (isEligibleForLetterboxEducation == isEligibleForLetterboxEducation() || task == null) {
      return;
    }
    task.dispatchTaskInfoChangedIfNeeded(true);
  }

  @Override // com.android.server.wm.WindowContainer
  public void waitForSyncTransactionCommit(ArraySet arraySet) {
    super.waitForSyncTransactionCommit(arraySet);
    StartingData startingData = this.mStartingData;
    if (startingData != null) {
      startingData.mWaitForSyncTransactionCommit = true;
    }
  }

  @Override // com.android.server.wm.WindowContainer
  public void onSyncTransactionCommitted(SurfaceControl.Transaction transaction) {
    super.onSyncTransactionCommitted(transaction);
    StartingData startingData = this.mStartingData;
    if (startingData == null) {
      return;
    }
    startingData.mWaitForSyncTransactionCommit = false;
    if (startingData.mRemoveAfterTransaction) {
      startingData.mRemoveAfterTransaction = false;
      removeStartingWindowAnimation(startingData.mPrepareRemoveAnimation);
    }
  }

  public void removeStartingWindowAnimation(boolean z) {
    this.mTransferringSplashScreenState = 0;
    Task task = this.task;
    if (task != null) {
      task.mSharedStartingData = null;
    }
    WindowState windowState = this.mStartingWindow;
    if (windowState == null) {
      if (this.mStartingData != null) {
        if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
              -2127842445,
              0,
              "Clearing startingData for token=%s",
              new Object[] {String.valueOf(this)});
        }
        this.mStartingData = null;
        this.mStartingSurface = null;
        return;
      }
      return;
    }
    StartingData startingData = this.mStartingData;
    if (startingData != null) {
      if (startingData.mWaitForSyncTransactionCommit
          || this.mTransitionController.inCollectingTransition(windowState)) {
        StartingData startingData2 = this.mStartingData;
        startingData2.mRemoveAfterTransaction = true;
        startingData2.mPrepareRemoveAnimation = z;
        return;
      }
      boolean z2 =
          z && this.mStartingData.needRevealAnimation() && this.mStartingWindow.isVisibleByPolicy();
      if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
            -1237827119,
            48,
            "Schedule remove starting %s startingWindow=%s animate=%b Callers=%s",
            new Object[] {
              String.valueOf(this),
              String.valueOf(this.mStartingWindow),
              Boolean.valueOf(z2),
              String.valueOf(Debug.getCallers(5))
            });
      }
      StartingSurfaceController.StartingSurface startingSurface = this.mStartingSurface;
      this.mStartingData = null;
      this.mStartingSurface = null;
      this.mStartingWindow = null;
      this.mTransitionChangeFlags &= -9;
      if (startingSurface == null) {
        if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
              45285419,
              0,
              "startingWindow was set but startingSurface==null, couldn't remove",
              (Object[]) null);
          return;
        }
        return;
      }
      startingSurface.remove(z2);
      return;
    }
    if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
          146871307,
          0,
          "Tried to remove starting window but startingWindow was null: %s",
          new Object[] {String.valueOf(this)});
    }
  }

  public void reparent(TaskFragment taskFragment, int i, String str) {
    if (getParent() == null) {
      Slog.w(
          "ActivityTaskManager",
          "reparent: Attempted to reparent non-existing app token: " + this.token);
      return;
    }
    if (getTaskFragment() == taskFragment) {
      throw new IllegalArgumentException(
          str + ": task fragment =" + taskFragment + " is already the parent of r=" + this);
    }
    if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
          573582981,
          20,
          (String) null,
          new Object[] {String.valueOf(this), Long.valueOf(this.task.mTaskId), Long.valueOf(i)});
    }
    reparent(taskFragment, i);
  }

  public final boolean isHomeIntent(Intent intent) {
    return "android.intent.action.MAIN".equals(intent.getAction())
        && (intent.hasCategory("android.intent.category.HOME")
            || intent.hasCategory("android.intent.category.SECONDARY_HOME"))
        && intent.getCategories().size() == 1
        && intent.getData() == null
        && intent.getType() == null;
  }

  public static boolean isMainIntent(Intent intent) {
    return "android.intent.action.MAIN".equals(intent.getAction())
        && intent.hasCategory("android.intent.category.LAUNCHER")
        && intent.getCategories().size() == 1
        && intent.getData() == null
        && intent.getType() == null;
  }

  public boolean canLaunchHomeActivity(int i, ActivityRecord activityRecord) {
    if (i == 1000 || i == 0) {
      return true;
    }
    RecentTasks recentTasks = this.mTaskSupervisor.mService.getRecentTasks();
    if (recentTasks == null || !recentTasks.isCallerRecents(i)) {
      return activityRecord != null && activityRecord.isResolverOrDelegateActivity();
    }
    return true;
  }

  public final boolean canLaunchAssistActivity(String str) {
    ComponentName componentName = this.mAtmService.mActiveVoiceInteractionServiceComponent;
    if (componentName != null) {
      return componentName.getPackageName().equals(str);
    }
    return false;
  }

  /* JADX WARN: Code restructure failed: missing block: B:22:0x0067, code lost:

     if (android.service.dreams.DreamActivity.class.getName() == r2.info.name) goto L34;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setActivityType(
      boolean z,
      int i,
      Intent intent,
      ActivityOptions activityOptions,
      ActivityRecord activityRecord) {
    int i2 = 4;
    if ((!z || canLaunchHomeActivity(i, activityRecord))
        && isHomeIntent(intent)
        && !isResolverOrDelegateActivity()) {
      ActivityInfo activityInfo = this.info;
      int i3 = activityInfo.resizeMode;
      if (i3 == 4 || i3 == 1) {
        activityInfo.resizeMode = 0;
      }
      i2 = 2;
    } else if (this.mAtmService
        .getRecentTasks()
        .isRecentsComponent(this.mActivityComponent, this.info.applicationInfo.uid)) {
      i2 = 3;
    } else if (activityOptions == null
        || activityOptions.getLaunchActivityType() != 4
        || !canLaunchAssistActivity(this.launchedFromPackage)) {
      if (activityOptions != null) {
        i2 = 5;
        if (activityOptions.getLaunchActivityType() == 5) {
          if (this.mAtmService.canLaunchDreamActivity(this.launchedFromPackage)) {}
        }
      }
      i2 = 0;
    }
    setActivityType(i2);
  }

  public void setTaskToAffiliateWith(Task task) {
    int i = this.launchMode;
    if (i == 3 || i == 2) {
      return;
    }
    this.task.setTaskToAffiliateWith(task);
  }

  public Task getRootTask() {
    Task task = this.task;
    if (task != null) {
      return task.getRootTask();
    }
    return null;
  }

  public int getRootTaskId() {
    Task task = this.task;
    if (task != null) {
      return task.getRootTaskId();
    }
    return -1;
  }

  public Task getOrganizedTask() {
    Task task = this.task;
    if (task != null) {
      return task.getOrganizedTask();
    }
    return null;
  }

  public TaskFragment getOrganizedTaskFragment() {
    TaskFragment taskFragment = getTaskFragment();
    if (taskFragment != null) {
      return taskFragment.getOrganizedTaskFragment();
    }
    return null;
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean isEmbedded() {
    TaskFragment taskFragment = getTaskFragment();
    return taskFragment != null && taskFragment.isEmbedded();
  }

  @Override // com.android.server.wm.WindowContainer
  public TaskDisplayArea getDisplayArea() {
    return (TaskDisplayArea) super.getDisplayArea();
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean providesOrientation() {
    return this.mStyleFillsParent;
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean fillsParent() {
    return occludesParent(true);
  }

  public boolean occludesParent() {
    return occludesParent(false);
  }

  public boolean occludesParent(boolean z) {
    if (!z && this.finishing) {
      return false;
    }
    if (CoreRune.FW_CUSTOM_LETTERBOX
        && !this.mOccludesParent
        && CustomLetterboxConfiguration.isCustomLetterboxEnabled(this)) {
      return false;
    }
    return this.mOccludesParent || showWallpaper();
  }

  public boolean setOccludesParent(boolean z) {
    return setOccludesParent(z, false);
  }

  public boolean setOccludesParent(boolean z, boolean z2) {
    int state = this.mPopOverState.getState();
    this.mPopOverState.setLastOccludesParent(z);
    if (this.mPopOverState.isActivated()) {
      if (this.mPopOverState.getState() != state) {
        recomputeConfiguration();
      }
      return false;
    }
    boolean z3 = z != this.mOccludesParent;
    this.mOccludesParent = z;
    if (CoreRune.FW_SUPPORT_OCCLUDES_PARENT_CHANGE_CALLBACK && z3) {
      this.mWmService.mExt.updateOccludeTargetIfNeeded(getDisplayContent(), this);
    }
    if (!z2) {
      setMainWindowOpaque(z);
    }
    this.mWmService.mWindowPlacerLocked.requestTraversal();
    if (z3 && this.task != null && !z) {
      getRootTask().convertActivityToTranslucent(this);
    }
    if (z3 || !z) {
      this.mRootWindowContainer.ensureActivitiesVisible(null, 0, false);
    }
    return z3;
  }

  public void setMainWindowOpaque(boolean z) {
    WindowState findMainWindow = findMainWindow();
    if (findMainWindow == null) {
      return;
    }
    findMainWindow.mWinAnimator.setOpaqueLocked(
        z & (!PixelFormat.formatHasAlpha(findMainWindow.getAttrs().format)));
  }

  public void takeFromHistory() {
    if (this.inHistory) {
      this.inHistory = false;
      if (this.task != null && !this.finishing) {
        this.task = null;
      }
      abortAndClearOptionsAnimation();
    }
  }

  public boolean isInHistory() {
    return this.inHistory;
  }

  public boolean isInRootTaskLocked() {
    Task rootTask = getRootTask();
    return (rootTask == null || rootTask.isInTask(this) == null) ? false : true;
  }

  public boolean isPersistable() {
    Intent intent;
    int i = this.info.persistableMode;
    return (i == 0 || i == 2)
        && ((intent = this.intent) == null || (intent.getFlags() & 8388608) == 0);
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean isFocusable() {
    return super.isFocusable() && (canReceiveKeys() || isAlwaysFocusable());
  }

  public boolean canReceiveKeys() {
    Task task;
    return getWindowConfiguration().canReceiveKeys()
        && ((task = this.task) == null
            || (task.getWindowConfiguration().canReceiveKeys() && !this.task.isFreeformStashed()));
  }

  public boolean isResizeable() {
    return isResizeable(true);
  }

  public boolean isResizeable(boolean z) {
    int i = this.info.resizeMode;
    if (i == 10) {
      return false;
    }
    return this.mAtmService.mForceResizableActivities
        || ActivityInfo.isResizeableMode(i)
        || (this.info.supportsPictureInPicture() && z)
        || isEmbedded();
  }

  public boolean isForceNonResizeable() {
    return this.info.resizeMode == 10;
  }

  public boolean canForceResizeNonResizable(int i) {
    boolean supportsMultiWindow;
    int i2;
    if (i == 2 && this.info.supportsPictureInPicture()) {
      return false;
    }
    Task task = this.task;
    if (task != null) {
      supportsMultiWindow = task.supportsMultiWindow() || supportsMultiWindow();
    } else {
      supportsMultiWindow = supportsMultiWindow();
    }
    return ((WindowConfiguration.inMultiWindowMode(i)
                && supportsMultiWindow
                && !this.mAtmService.mForceResizableActivities)
            || (i2 = this.info.resizeMode) == 2
            || (1048576 & i2) != 0
            || i2 == 1)
        ? false
        : true;
  }

  public boolean supportsPictureInPicture() {
    return this.mAtmService.mSupportsPictureInPicture
        && isActivityTypeStandardOrUndefined()
        && this.info.supportsPictureInPicture();
  }

  public boolean supportsFreeform() {
    return supportsFreeformInDisplayArea(getDisplayArea());
  }

  public boolean supportsFreeformInDisplayArea(TaskDisplayArea taskDisplayArea) {
    return this.mAtmService.mSupportsFreeformWindowManagement
        && supportsMultiWindowInDisplayArea(taskDisplayArea);
  }

  public boolean supportsMultiWindow() {
    return supportsMultiWindowInDisplayArea(getDisplayArea());
  }

  public boolean supportsMultiWindowInDefaultDisplayArea() {
    return supportsMultiWindowInDisplayArea(this.mRootWindowContainer.getDefaultTaskDisplayArea());
  }

  public boolean supportsMultiWindowInDisplayArea(TaskDisplayArea taskDisplayArea) {
    if (isActivityTypeHome()) {
      return false;
    }
    ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
    if (activityTaskManagerService.mSupportsMultiWindow && taskDisplayArea != null) {
      return activityTaskManagerService.mMwSupportPolicyController.supportsMultiWindowInDisplayArea(
          taskDisplayArea,
          this.info.resizeMode,
          isResizeable(),
          this.mIgnoreDevSettingForNonResizable);
    }
    return false;
  }

  public boolean canBeLaunchedOnDisplay(int i) {
    return this.mAtmService.mTaskSupervisor.canPlaceEntityOnDisplay(
        i, this.launchedFromPid, this.launchedFromUid, this.info);
  }

  public boolean checkEnterPictureInPictureState(String str, boolean z) {
    return checkEnterPictureInPictureState(str, z, false);
  }

  public boolean checkEnterPictureInPictureState(String str, boolean z, boolean z2) {
    if (!supportsPictureInPicture()
        || !checkEnterPictureInPictureAppOpsState()
        || this.mAtmService.shouldDisableNonVrUiLocked()) {
      return false;
    }
    DisplayContent displayContent = this.mDisplayContent;
    if (displayContent != null && !displayContent.mDwpcHelper.isEnteringPipAllowed(getUid())) {
      Slog.w(
          "ActivityTaskManager",
          "Display "
              + this.mDisplayContent.getDisplayId()
              + " doesn't support enter picture-in-picture mode. caller = "
              + str);
      return false;
    }
    if (isDexMode()) {
      return false;
    }
    if (!CoreRune.MT_NEW_DEX_PIP && isNewDexMode()) {
      return false;
    }
    if ((getDisplayContent() != null && getDisplayContent().isMultiTaskingDisplay())
        || this.mWmService.mSwitchingUser) {
      return false;
    }
    if (CoreRune.MW_PIP_SHELL_TRANSITION && this.finishing) {
      Slog.w("ActivityTaskManager", "checkEnterPictureInPictureState: failed, reason=finishing");
      return false;
    }
    boolean z3 = this.mAtmService.getLockTaskModeState() != 0;
    TaskDisplayArea displayArea = getDisplayArea();
    boolean z4 = displayArea != null && displayArea.hasPinnedTask();
    boolean z5 = (isKeyguardLocked() || z3) ? false : true;
    if (z && z4) {
      return false;
    }
    int i =
        AbstractC28617.$SwitchMap$com$android$server$wm$ActivityRecord$State[this.mState.ordinal()];
    if (i == 1) {
      return z2;
    }
    if (i != 2) {
      return (i == 3 || i == 4)
          ? z5 && !z4 && this.supportsEnterPipOnTaskSwitch
          : i == 5 && this.supportsEnterPipOnTaskSwitch && z5 && !z4;
    }
    if (z3) {
      return false;
    }
    return this.supportsEnterPipOnTaskSwitch || !z;
  }

  /* renamed from: com.android.server.wm.ActivityRecord$7 */
  public abstract /* synthetic */ class AbstractC28617 {
    public static final /* synthetic */ int[] $SwitchMap$com$android$server$wm$ActivityRecord$State;

    static {
      int[] iArr = new int[State.values().length];
      $SwitchMap$com$android$server$wm$ActivityRecord$State = iArr;
      try {
        iArr[State.INITIALIZING.ordinal()] = 1;
      } catch (NoSuchFieldError unused) {
      }
      try {
        $SwitchMap$com$android$server$wm$ActivityRecord$State[State.RESUMED.ordinal()] = 2;
      } catch (NoSuchFieldError unused2) {
      }
      try {
        $SwitchMap$com$android$server$wm$ActivityRecord$State[State.PAUSING.ordinal()] = 3;
      } catch (NoSuchFieldError unused3) {
      }
      try {
        $SwitchMap$com$android$server$wm$ActivityRecord$State[State.PAUSED.ordinal()] = 4;
      } catch (NoSuchFieldError unused4) {
      }
      try {
        $SwitchMap$com$android$server$wm$ActivityRecord$State[State.STOPPING.ordinal()] = 5;
      } catch (NoSuchFieldError unused5) {
      }
      try {
        $SwitchMap$com$android$server$wm$ActivityRecord$State[State.STARTED.ordinal()] = 6;
      } catch (NoSuchFieldError unused6) {
      }
      try {
        $SwitchMap$com$android$server$wm$ActivityRecord$State[State.STOPPED.ordinal()] = 7;
      } catch (NoSuchFieldError unused7) {
      }
      try {
        $SwitchMap$com$android$server$wm$ActivityRecord$State[State.DESTROYED.ordinal()] = 8;
      } catch (NoSuchFieldError unused8) {
      }
      try {
        $SwitchMap$com$android$server$wm$ActivityRecord$State[State.DESTROYING.ordinal()] = 9;
      } catch (NoSuchFieldError unused9) {
      }
    }
  }

  public void setWillCloseOrEnterPip(boolean z) {
    this.mWillCloseOrEnterPip = z;
  }

  public boolean willCloseOrEnterPip() {
    return this.mWillCloseOrEnterPip;
  }

  public boolean checkEnterPictureInPictureAppOpsState() {
    return this.mAtmService
            .getAppOpsManager()
            .checkOpNoThrow(67, this.info.applicationInfo.uid, this.packageName)
        == 0;
  }

  public final boolean isAlwaysFocusable() {
    return (this.info.flags & 262144) != 0;
  }

  public boolean windowsAreFocusable() {
    return windowsAreFocusable(false);
  }

  public boolean windowsAreFocusable(boolean z) {
    if (!z && this.mTargetSdk < 29) {
      ActivityRecord activityRecord =
          (ActivityRecord)
              this.mWmService.mRoot.mTopFocusedAppByProcess.get(Integer.valueOf(getPid()));
      if (activityRecord != null && activityRecord != this) {
        return false;
      }
    }
    return (canReceiveKeys() || isAlwaysFocusable()) && isAttached();
  }

  public boolean moveFocusableActivityToTop(String str) {
    if (!isFocusable()) {
      if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_FOCUS,
            240271590,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      return false;
    }
    Task rootTask = getRootTask();
    if (rootTask == null) {
      Slog.w(
          "ActivityTaskManager",
          "moveFocusableActivityToTop: invalid root task: activity=" + this + " task=" + this.task);
      return false;
    }
    DisplayContent displayContent = this.mDisplayContent;
    ActivityRecord activityRecord = displayContent.mFocusedApp;
    if (activityRecord != null && activityRecord.task == this.task) {
      if (this.task
          == displayContent.getTask(
              new Predicate() { // from class:
                                // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda10
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$moveFocusableActivityToTop$4;
                  lambda$moveFocusableActivityToTop$4 =
                      ActivityRecord.lambda$moveFocusableActivityToTop$4((Task) obj);
                  return lambda$moveFocusableActivityToTop$4;
                }
              },
              true)) {
        if (activityRecord == this) {
          if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
            ProtoLogImpl.d(
                ProtoLogGroup.WM_DEBUG_FOCUS,
                385237117,
                0,
                (String) null,
                new Object[] {String.valueOf(this)});
          }
        } else {
          if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
            ProtoLogImpl.d(
                ProtoLogGroup.WM_DEBUG_FOCUS,
                1239439010,
                0,
                (String) null,
                new Object[] {String.valueOf(this)});
          }
          this.mDisplayContent.setFocusedApp(this);
          this.mAtmService.mWindowManager.updateFocusedWindowLocked(0, true);
        }
        DisplayContent displayContent2 = getDisplayContent();
        ActivityRecord activityRecord2 = this.mAtmService.mLastResumedActivity;
        DisplayContent displayContent3 =
            activityRecord2 != null ? activityRecord2.getDisplayContent() : null;
        if (displayContent3 != null
            && displayContent2 != null
            && displayContent3 != displayContent2
            && (displayContent3.isMultiTaskingDisplay()
                || displayContent2.isMultiTaskingDisplay())) {
          this.mAtmService.setLastResumedActivityUncheckLocked(this, str);
        }
        return !isState(State.RESUMED);
      }
    }
    if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_FOCUS,
          -50336993,
          0,
          (String) null,
          new Object[] {String.valueOf(this)});
    }
    rootTask.moveToFront(str, this.task);
    if (this.mRootWindowContainer.getTopResumedActivity() == this) {
      this.mAtmService.setLastResumedActivityUncheckLocked(this, str);
    }
    return true;
  }

  public static /* synthetic */ boolean lambda$moveFocusableActivityToTop$4(Task task) {
    return task.isLeafTask() && task.isFocusable();
  }

  public void finishIfSubActivity(ActivityRecord activityRecord, String str, int i) {
    if (this.resultTo == activityRecord
        && this.requestCode == i
        && Objects.equals(this.resultWho, str)) {
      finishIfPossible("request-sub", false);
    }
  }

  public boolean finishIfSameAffinity(ActivityRecord activityRecord) {
    if (!Objects.equals(activityRecord.taskAffinity, this.taskAffinity)) {
      return true;
    }
    activityRecord.finishIfPossible("request-affinity", true);
    return false;
  }

  public final void finishActivityResults(
      final int i, final Intent intent, final NeededUriGrants neededUriGrants) {
    ActivityRecord activityRecord = this.resultTo;
    if (activityRecord != null) {
      int i2 = activityRecord.mUserId;
      int i3 = this.mUserId;
      if (i2 != i3 && intent != null) {
        intent.prepareToLeaveUser(i3);
      }
      if (this.info.applicationInfo.uid > 0) {
        this.mAtmService.mUgmInternal.grantUriPermissionUncheckedFromIntent(
            neededUriGrants, this.resultTo.getUriPermissionsLocked());
      }
      if (this.mForceSendResultForMediaProjection || this.resultTo.isState(State.RESUMED)) {
        final ActivityRecord activityRecord2 = this.resultTo;
        this.mAtmService.f1734mH.post(
            new Runnable() { // from class:
                             // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda14
              @Override // java.lang.Runnable
              public final void run() {
                ActivityRecord.this.lambda$finishActivityResults$5(
                    activityRecord2, i, intent, neededUriGrants);
              }
            });
      } else {
        this.resultTo.addResultLocked(this, this.resultWho, this.requestCode, i, intent);
      }
      this.resultTo = null;
    }
    this.results = null;
    this.pendingResults = null;
    this.newIntents = null;
    setSavedState(null);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$finishActivityResults$5(
      ActivityRecord activityRecord, int i, Intent intent, NeededUriGrants neededUriGrants) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        activityRecord.sendResult(
            getUid(),
            this.resultWho,
            this.requestCode,
            i,
            intent,
            neededUriGrants,
            this.mForceSendResultForMediaProjection);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public int finishIfPossible(String str, boolean z) {
    return finishIfPossible(0, null, null, str, z);
  }

  /* JADX WARN: Removed duplicated region for block: B:135:0x025f A[Catch: all -> 0x02d2, TryCatch #0 {all -> 0x02d2, blocks: (B:24:0x0099, B:26:0x00ba, B:28:0x00c5, B:29:0x00ca, B:31:0x00cf, B:33:0x00d5, B:34:0x00da, B:36:0x00e3, B:39:0x00ec, B:42:0x00f3, B:44:0x00ff, B:46:0x0107, B:51:0x0115, B:53:0x011b, B:55:0x0121, B:56:0x0128, B:58:0x012c, B:59:0x0133, B:61:0x0137, B:64:0x013d, B:66:0x0143, B:68:0x0149, B:70:0x014f, B:71:0x0154, B:73:0x0158, B:75:0x015e, B:77:0x0167, B:79:0x0177, B:83:0x0182, B:85:0x0189, B:88:0x0192, B:89:0x019f, B:91:0x01ac, B:93:0x01b2, B:94:0x01cc, B:96:0x01d3, B:98:0x01db, B:100:0x01e1, B:101:0x01e3, B:105:0x0211, B:107:0x021c, B:108:0x0223, B:110:0x0227, B:113:0x022d, B:115:0x0237, B:116:0x023a, B:118:0x023e, B:120:0x0242, B:123:0x024a, B:126:0x0255, B:128:0x025b, B:131:0x01ee, B:133:0x01f2, B:134:0x0202, B:135:0x025f, B:137:0x0267, B:139:0x026b, B:141:0x0273, B:143:0x0278, B:144:0x027e, B:145:0x0281, B:149:0x028e, B:151:0x0296, B:152:0x029b, B:154:0x02a1, B:158:0x02b2, B:160:0x02b6, B:161:0x02c6, B:163:0x02ca), top: B:23:0x0099 }] */
  /* JADX WARN: Removed duplicated region for block: B:58:0x012c A[Catch: all -> 0x02d2, TryCatch #0 {all -> 0x02d2, blocks: (B:24:0x0099, B:26:0x00ba, B:28:0x00c5, B:29:0x00ca, B:31:0x00cf, B:33:0x00d5, B:34:0x00da, B:36:0x00e3, B:39:0x00ec, B:42:0x00f3, B:44:0x00ff, B:46:0x0107, B:51:0x0115, B:53:0x011b, B:55:0x0121, B:56:0x0128, B:58:0x012c, B:59:0x0133, B:61:0x0137, B:64:0x013d, B:66:0x0143, B:68:0x0149, B:70:0x014f, B:71:0x0154, B:73:0x0158, B:75:0x015e, B:77:0x0167, B:79:0x0177, B:83:0x0182, B:85:0x0189, B:88:0x0192, B:89:0x019f, B:91:0x01ac, B:93:0x01b2, B:94:0x01cc, B:96:0x01d3, B:98:0x01db, B:100:0x01e1, B:101:0x01e3, B:105:0x0211, B:107:0x021c, B:108:0x0223, B:110:0x0227, B:113:0x022d, B:115:0x0237, B:116:0x023a, B:118:0x023e, B:120:0x0242, B:123:0x024a, B:126:0x0255, B:128:0x025b, B:131:0x01ee, B:133:0x01f2, B:134:0x0202, B:135:0x025f, B:137:0x0267, B:139:0x026b, B:141:0x0273, B:143:0x0278, B:144:0x027e, B:145:0x0281, B:149:0x028e, B:151:0x0296, B:152:0x029b, B:154:0x02a1, B:158:0x02b2, B:160:0x02b6, B:161:0x02c6, B:163:0x02ca), top: B:23:0x0099 }] */
  /* JADX WARN: Removed duplicated region for block: B:83:0x0182 A[Catch: all -> 0x02d2, TryCatch #0 {all -> 0x02d2, blocks: (B:24:0x0099, B:26:0x00ba, B:28:0x00c5, B:29:0x00ca, B:31:0x00cf, B:33:0x00d5, B:34:0x00da, B:36:0x00e3, B:39:0x00ec, B:42:0x00f3, B:44:0x00ff, B:46:0x0107, B:51:0x0115, B:53:0x011b, B:55:0x0121, B:56:0x0128, B:58:0x012c, B:59:0x0133, B:61:0x0137, B:64:0x013d, B:66:0x0143, B:68:0x0149, B:70:0x014f, B:71:0x0154, B:73:0x0158, B:75:0x015e, B:77:0x0167, B:79:0x0177, B:83:0x0182, B:85:0x0189, B:88:0x0192, B:89:0x019f, B:91:0x01ac, B:93:0x01b2, B:94:0x01cc, B:96:0x01d3, B:98:0x01db, B:100:0x01e1, B:101:0x01e3, B:105:0x0211, B:107:0x021c, B:108:0x0223, B:110:0x0227, B:113:0x022d, B:115:0x0237, B:116:0x023a, B:118:0x023e, B:120:0x0242, B:123:0x024a, B:126:0x0255, B:128:0x025b, B:131:0x01ee, B:133:0x01f2, B:134:0x0202, B:135:0x025f, B:137:0x0267, B:139:0x026b, B:141:0x0273, B:143:0x0278, B:144:0x027e, B:145:0x0281, B:149:0x028e, B:151:0x0296, B:152:0x029b, B:154:0x02a1, B:158:0x02b2, B:160:0x02b6, B:161:0x02c6, B:163:0x02ca), top: B:23:0x0099 }] */
  /* JADX WARN: Removed duplicated region for block: B:87:0x0190  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int finishIfPossible(
      int i, Intent intent, NeededUriGrants neededUriGrants, String str, boolean z) {
    boolean z2;
    Task rootTask;
    ActivityRecord activityRecord;
    Transition.ChangeInfo changeInfo;
    ActivityRecord activityRecord2;
    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STATES,
          1047769218,
          4,
          (String) null,
          new Object[] {
            String.valueOf(this), Long.valueOf(i), String.valueOf(intent), String.valueOf(str)
          });
    }
    if (this.finishing) {
      Slog.w("ActivityTaskManager", "Duplicate finish request for r=" + this);
      return 0;
    }
    if (!isInRootTaskLocked()) {
      Slog.w("ActivityTaskManager", "Finish request when not in root task for r=" + this);
      return 0;
    }
    Task rootTask2 = getRootTask();
    State state = State.RESUMED;
    int i2 = 1;
    boolean z3 =
        (isState(state) || rootTask2.getTopResumedActivity() == null)
            && rootTask2.isFocusedRootTaskOnDisplay()
            && !this.task.isClearingToReuseTask();
    boolean z4 = z3 && this.mRootWindowContainer.isTopDisplayFocusedRootTask(rootTask2);
    this.mAtmService.deferWindowLayout();
    try {
      this.mTaskSupervisor.mNoHistoryActivities.remove(this);
      makeFinishingLocked();
      Task task = getTask();
      EventLogTags.writeWmFinishActivity(
          this.mUserId, System.identityHashCode(this), task.mTaskId, this.shortComponentName, str);
      ActivityRecord activityAbove = task.getActivityAbove(this);
      if (activityAbove != null && (this.intent.getFlags() & 524288) != 0) {
        activityAbove.intent.addFlags(524288);
      }
      pauseKeyDispatchingLocked();
      if (z3 && task.topRunningActivity(true) == null) {
        task.adjustFocusToNextFocusableTask("finish-top", false, z4);
      }
      finishActivityResults(i, intent, neededUriGrants);
      boolean z5 = task.getTopNonFinishingActivity() == null && !task.isClearingToReuseTask();
      Transition requestCloseTransitionIfNeeded =
          this.mTransitionController.requestCloseTransitionIfNeeded(z5 ? task : this);
      if (!"clear-task-all".equals(str)
          && !"clear-task-stack".equals(str)
          && !"clear-task-top".equals(str)) {
        z2 = false;
        if (!z2
            && task.isDexCompatEnabled()
            && (activityRecord2 = rootTask2.topRunningActivity()) != null) {
          this.mAtmService.mDexCompatController.changeWindowingModeIfNeeded(
              rootTask2, task, activityRecord2);
        }
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
          this.mAtmService.mChangeTransitController.onActivityFinished(this);
        }
        if (CoreRune.MW_EMBED_ACTIVITY
            && this.app != null
            && !z5
            && isSplitEmbedded()
            && task.getTopMostActivity() == this
            && task.getNonFinishingActivityCount() != 0) {
          this.app.updateTopActivityIfNeeded(null);
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION
            && inSplitScreenWindowingMode()
            && this.mTransitionController.getCollectingTransitionType() == 3) {
          changeInfo =
              (Transition.ChangeInfo)
                  this.mTransitionController.getCollectingTransition().mChanges.get(task);
          boolean z6 = !z2 && task.isClearingToReuseTask();
          if (changeInfo != null) {
            changeInfo.updateForceChangeSplitTask(task.getNonFinishingActivityCount(), z6);
          }
        }
        if (!isState(state)) {
          if (z5) {
            this.mAtmService
                .getTaskChangeNotificationController()
                .notifyTaskRemovalStarted(task.getTaskInfo());
          }
          this.mDisplayContent.prepareAppTransition(2);
          if (!this.mTransitionController.isShellTransitionsEnabled()
              && !task.isAnimatingByRecents()) {
            ArraySet newArraySet = Sets.newArraySet(new Task[] {task});
            this.mAtmService.mWindowManager.mTaskSnapshotController.snapshotTasks(newArraySet);
            this.mAtmService.mWindowManager.mTaskSnapshotController.addSkipClosingAppSnapshotTasks(
                newArraySet);
          }
          setVisibility(false);
          if (this.mLastImeShown
              && this.mTransitionController.isShellTransitionsEnabled()
              && (activityRecord = task.topRunningActivity()) != null) {
            activityRecord.mLastImeShown = true;
          }
          if (getTaskFragment().getPausingActivity() == null) {
            if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
              ProtoLogImpl.v(
                  ProtoLogGroup.WM_DEBUG_STATES,
                  -1003060523,
                  0,
                  (String) null,
                  new Object[] {String.valueOf(this)});
            }
            getTaskFragment().startPausing(false, false, null, null, "finish");
          }
          if (z5) {
            this.mAtmService.getLockTaskController().clearLockedTask(task);
            if (z3) {
              this.mNeedsZBoost = true;
              this.mDisplayContent.assignWindowLayers(false);
            }
          }
          if (CoreRune.MW_SHELL_CHANGE_TRANSITION
              && CoreRune.MW_SHELL_TRANSITION_BUG_FIX
              && !z5
              && this.mAtmService.mChangeTransitController.isInChangeTransition(task)) {
            rootTask2.ensureActivitiesVisible(null, 0, false);
          }
          if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX
              && this.noDisplay
              && inFreeformWindowingMode()
              && z5
              && task != (rootTask = this.mDisplayContent.getRootTask(5, 1))
              && rootTask != null
              && rootTask.isAliasManaged()) {
            rootTask.ensureActivitiesVisible(null, 0, false);
          }
        } else if (!isState(State.PAUSING)) {
          if (this.mVisibleRequested) {
            if (this.mTransitionController.isShellTransitionsEnabled()) {
              setVisibility(false);
              if (requestCloseTransitionIfNeeded != null) {
                requestCloseTransitionIfNeeded.setReady(this.mDisplayContent, true);
              }
            } else {
              prepareActivityHideTransitionAnimation();
            }
          }
          boolean z7 = completeFinishing("finishIfPossible") == null;
          if (z && isState(State.STOPPING)) {
            this.mAtmService.updateOomAdj();
          }
          if (task.onlyHasTaskOverlayActivities(false)) {
            task.forAllActivities(
                new Consumer() { // from class:
                                 // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda15
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    ((ActivityRecord) obj).prepareActivityHideTransitionAnimationIfOvarlay();
                  }
                });
          }
          if (z7) {
            i2 = 2;
          }
        } else {
          if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
            ProtoLogImpl.v(
                ProtoLogGroup.WM_DEBUG_STATES,
                1023413388,
                0,
                (String) null,
                new Object[] {String.valueOf(this)});
          }
          if (!this.mVisibleRequested) {
            this.mDisplayContent.mUnknownAppVisibilityController.appRemovedOrHidden(this);
          }
        }
        return i2;
      }
      z2 = true;
      if (!z2) {
        this.mAtmService.mDexCompatController.changeWindowingModeIfNeeded(
            rootTask2, task, activityRecord2);
      }
      if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {}
      if (CoreRune.MW_EMBED_ACTIVITY) {
        this.app.updateTopActivityIfNeeded(null);
      }
      if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
        changeInfo =
            (Transition.ChangeInfo)
                this.mTransitionController.getCollectingTransition().mChanges.get(task);
        if (z2) {}
        if (changeInfo != null) {}
      }
      if (!isState(state)) {}
      return i2;
    } finally {
      this.mAtmService.continueWindowLayout();
    }
  }

  public void setForceSendResultForMediaProjection() {
    this.mForceSendResultForMediaProjection = true;
  }

  public final void prepareActivityHideTransitionAnimationIfOvarlay() {
    if (this.mTaskOverlay) {
      prepareActivityHideTransitionAnimation();
    }
  }

  public final void prepareActivityHideTransitionAnimation() {
    DisplayContent displayContent = this.mDisplayContent;
    displayContent.prepareAppTransition(2);
    setVisibility(false);
    displayContent.executeAppTransition();
  }

  public ActivityRecord completeFinishing(String str) {
    return completeFinishing(true, str);
  }

  /* JADX WARN: Removed duplicated region for block: B:58:0x00bf  */
  /* JADX WARN: Removed duplicated region for block: B:79:0x010c  */
  /* JADX WARN: Removed duplicated region for block: B:81:? A[RETURN, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:83:0x0103  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public ActivityRecord completeFinishing(boolean z, String str) {
    boolean z2;
    boolean z3;
    Task task;
    if (!this.finishing || isState(State.RESUMED)) {
      throw new IllegalArgumentException(
          "Activity must be finishing and not resumed to complete, r="
              + this
              + ", finishing="
              + this.finishing
              + ", state="
              + this.mState);
    }
    if (isState(State.PAUSING)) {
      return this;
    }
    boolean z4 = false;
    boolean z5 = this.mVisibleRequested || isState(State.PAUSED, State.STARTED);
    if (z && z5 && !this.task.isClearingToReuseTask()) {
      if (occludesParent(true)
          || (isKeyguardLocked()
              && this.mTaskSupervisor.getKeyguardController().topActivityOccludesKeyguard(this))) {
        this.mDisplayContent.ensureActivitiesVisible(null, 0, false, true);
      }
    }
    ActivityRecord activityRecord = getDisplayArea().topRunningActivity(true);
    TaskFragment taskFragment = getTaskFragment();
    if (activityRecord != null && taskFragment != null && taskFragment.isEmbedded()) {
      TaskFragment organizedTaskFragment = taskFragment.getOrganizedTaskFragment();
      TaskFragment adjacentTaskFragment =
          organizedTaskFragment != null ? organizedTaskFragment.getAdjacentTaskFragment() : null;
      if (adjacentTaskFragment != null
          && activityRecord.isDescendantOf(adjacentTaskFragment)
          && organizedTaskFragment.topRunningActivity() == null) {
        z2 = organizedTaskFragment.isDelayLastActivityRemoval();
        z3 =
            activityRecord == null
                && !((activityRecord.nowVisible && activityRecord.isVisibleRequested())
                    || inPinnedWindowingMode());
        if (z3
            && this.mDisplayContent.isSleeping()
            && activityRecord == activityRecord.getTaskFragment().mLastPausedActivity) {
          activityRecord.getTaskFragment().clearLastPausedActivity();
        }
        if (!z5) {
          if (CoreRune.MW_FREEFORM_SHELL_TRANSITION
              && !z2
              && inFreeformWindowingMode()
              && (task = this.task) != null
              && task.isRemovalRequested()) {
            Transition transitionOf = this.mTransitionController.getTransitionOf(this.task);
            z2 = transitionOf != null && transitionOf.mType == 2;
          }
          if (z3 || z2) {
            addToStopping(false, false, "completeFinishing");
            setState(State.STOPPING, "completeFinishing");
          } else if (!addToFinishingAndWaitForIdle()) {
            z4 = destroyIfPossible(str);
          }
        } else {
          addToFinishingAndWaitForIdle();
          z4 = destroyIfPossible(str);
        }
        if (z4) {
          return this;
        }
        return null;
      }
    }
    z2 = false;
    if (activityRecord == null) {}
    if (z3) {
      activityRecord.getTaskFragment().clearLastPausedActivity();
    }
    if (!z5) {}
    if (z4) {}
  }

  public boolean destroyIfPossible(String str) {
    setState(State.FINISHING, "destroyIfPossible");
    this.mTaskSupervisor.mStoppingActivities.remove(this);
    Task rootTask = getRootTask();
    TaskDisplayArea displayArea = getDisplayArea();
    if (CoreRune.SYSFW_APP_SPEG && (displayArea == null || rootTask == null)) {
      Slog.w("SPEG", "Impossible to destroy activity: " + this + ", task is null");
    }
    ActivityRecord activityRecord = displayArea.topRunningActivity();
    if ((activityRecord == null
            && rootTask.isFocusedRootTaskOnDisplay()
            && displayArea.getOrCreateRootHomeTask() != null)
        && getDisplayId() != 4) {
      addToFinishingAndWaitForIdle();
      return false;
    }
    makeFinishingLocked();
    boolean destroyImmediately = destroyImmediately("finish-imm:" + str);
    if (activityRecord == null) {
      this.mRootWindowContainer.ensureVisibilityAndConfig(
          activityRecord, getDisplayId(), false, true);
    }
    if (destroyImmediately) {
      this.mRootWindowContainer.resumeFocusedTasksTopActivities();
    }
    if (ProtoLogCache.WM_DEBUG_CONTAINERS_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_CONTAINERS,
          -401282500,
          0,
          (String) null,
          new Object[] {String.valueOf(this), String.valueOf(destroyImmediately)});
    }
    return destroyImmediately;
  }

  public boolean addToFinishingAndWaitForIdle() {
    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STATES,
          1610646518,
          0,
          (String) null,
          new Object[] {String.valueOf(this)});
    }
    setState(State.FINISHING, "addToFinishingAndWaitForIdle");
    if (!this.mTaskSupervisor.mFinishingActivities.contains(this)) {
      this.mTaskSupervisor.mFinishingActivities.add(this);
    }
    resumeKeyDispatchingLocked();
    return this.mRootWindowContainer.resumeFocusedTasksTopActivities();
  }

  public boolean destroyImmediately(String str) {
    boolean z;
    State state = State.DESTROYING;
    State state2 = State.DESTROYED;
    if (isState(state, state2)) {
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STATES,
            -21399771,
            0,
            (String) null,
            new Object[] {String.valueOf(this), String.valueOf(str)});
      }
      return false;
    }
    EventLogTags.writeWmDestroyActivity(
        this.mUserId,
        System.identityHashCode(this),
        this.task.mTaskId,
        this.shortComponentName,
        str);
    cleanUp(false, false);
    boolean z2 = true;
    if (hasProcess()) {
      this.app.removeActivity(this, true);
      if (!this.app.hasActivities()) {
        this.mAtmService.clearHeavyWeightProcessIfEquals(this.app);
      }
      try {
        this.mAtmService
            .getLifecycleManager()
            .scheduleTransaction(
                this.app.getThread(),
                this.token,
                (ActivityLifecycleItem)
                    DestroyActivityItem.obtain(this.finishing, this.configChangeFlags));
      } catch (Exception unused) {
        if (this.finishing) {
          removeFromHistory(str + " exceptionInScheduleDestroy");
          z = true;
        }
      }
      z = false;
      z2 = false;
      this.nowVisible = false;
      if (this.finishing && !z2) {
        if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_STATES,
              -1432963966,
              0,
              (String) null,
              new Object[] {String.valueOf(this)});
        }
        setState(State.DESTROYING, "destroyActivityLocked. finishing and not skipping destroy");
        this.mAtmService.f1734mH.postDelayed(this.mDestroyTimeoutRunnable, 10000L);
      } else {
        if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_STATES,
              726205185,
              0,
              (String) null,
              new Object[] {String.valueOf(this)});
        }
        setState(State.DESTROYED, "destroyActivityLocked. not finishing or skipping destroy");
        detachFromProcess();
      }
      if (CoreRune.SYSFW_APP_PREL && this.mShouldPrelNotify && this.mAtmService.mAps != null) {
        WindowProcessController windowProcessController = this.app;
        if (windowProcessController != null) {
          windowProcessController.mIsPrelScheduleGroupOverride = false;
        }
        this.mShouldPrelNotify = false;
        Slog.w("PREL", "destroyImmediately " + this + ", reason " + str);
        this.mAtmService.mAps.setTaskProcessedForPrelaunchedAppAsync(getUid());
      }
      z2 = z;
    } else if (this.finishing) {
      removeFromHistory(str + " hadNoApp");
    } else {
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STATES,
            -729530161,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      setState(state2, "destroyActivityLocked. not finishing and had no app");
      z2 = false;
    }
    this.configChangeFlags = 0;
    return z2;
  }

  public void removeFromHistory(String str) {
    finishActivityResults(0, null, null);
    makeFinishingLocked();
    if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
          350168164,
          0,
          (String) null,
          new Object[] {
            String.valueOf(this), String.valueOf(str), String.valueOf(Debug.getCallers(5))
          });
    }
    takeFromHistory();
    removeTimeouts();
    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STATES,
          579298675,
          0,
          (String) null,
          new Object[] {String.valueOf(this)});
    }
    setState(State.DESTROYED, "removeFromHistory");
    detachFromProcess();
    resumeKeyDispatchingLocked();
    this.mDisplayContent.removeAppToken(this.token);
    cleanUpActivityServices();
    removeUriPermissionsLocked();
  }

  public void detachFromProcess() {
    WindowProcessController windowProcessController = this.app;
    if (windowProcessController != null) {
      windowProcessController.removeActivity(this, false);
    }
    this.app = null;
    this.mInputDispatchingTimeoutMillis = InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
  }

  public void makeFinishingLocked() {
    Task task;
    ActivityRecord activity;
    if (this.finishing) {
      return;
    }
    this.finishing = true;
    if (this.mLaunchCookie != null
        && this.mState != State.RESUMED
        && (task = this.task) != null
        && !task.mInRemoveTask
        && !task.isClearingToReuseTask()
        && (activity =
                this.task.getActivity(
                    new Predicate() { // from class:
                                      // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda13
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        boolean lambda$makeFinishingLocked$7;
                        lambda$makeFinishingLocked$7 =
                            ActivityRecord.this.lambda$makeFinishingLocked$7((ActivityRecord) obj);
                        return lambda$makeFinishingLocked$7;
                      }
                    },
                    this,
                    false,
                    false))
            != null) {
      activity.mLaunchCookie = this.mLaunchCookie;
      this.mLaunchCookie = null;
    }
    TaskFragment taskFragment = getTaskFragment();
    if (taskFragment != null) {
      Task task2 = taskFragment.getTask();
      if (task2 != null
          && task2.isClearingToReuseTask()
          && taskFragment.getTopNonFinishingActivity() == null) {
        taskFragment.mClearedTaskForReuse = true;
      }
      taskFragment.sendTaskFragmentInfoChanged();
    }
    if (this.mAppStopped) {
      abortAndClearOptionsAnimation();
    }
    DisplayContent displayContent = this.mDisplayContent;
    if (displayContent != null) {
      displayContent.mUnknownAppVisibilityController.appRemovedOrHidden(this);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$makeFinishingLocked$7(ActivityRecord activityRecord) {
    return activityRecord.mLaunchCookie == null
        && !activityRecord.finishing
        && activityRecord.isUid(getUid());
  }

  public boolean isFinishing() {
    return this.finishing;
  }

  public void destroyed(String str) {
    removeDestroyTimeout();
    if (ProtoLogCache.WM_DEBUG_CONTAINERS_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_CONTAINERS,
          -1598452494,
          0,
          (String) null,
          new Object[] {String.valueOf(this)});
    }
    if (!isState(State.DESTROYING, State.DESTROYED)) {
      throw new IllegalStateException(
          "Reported destroyed for activity that is not destroying: r=" + this);
    }
    this.mTaskSupervisor.killTaskProcessesOnDestroyedIfNeeded(this.task);
    if (isInRootTaskLocked()) {
      cleanUp(true, false);
      removeFromHistory(str);
    }
    this.mRootWindowContainer.resumeFocusedTasksTopActivities();
  }

  public void cleanUp(boolean z, boolean z2) {
    HashSet hashSet;
    getTaskFragment().cleanUpActivityReferences(this);
    clearLastParentBeforePip();
    cleanUpSplashScreen();
    this.deferRelaunchUntilPaused = false;
    this.frozenBeforeDestroy = false;
    if (z2) {
      setState(State.DESTROYED, "cleanUp");
      detachFromProcess();
    }
    this.mTaskSupervisor.cleanupActivity(this);
    if (this.finishing && (hashSet = this.pendingResults) != null) {
      Iterator it = hashSet.iterator();
      while (it.hasNext()) {
        PendingIntentRecord pendingIntentRecord =
            (PendingIntentRecord) ((WeakReference) it.next()).get();
        if (pendingIntentRecord != null) {
          this.mAtmService.mPendingIntentController.cancelIntentSender(pendingIntentRecord, false);
        }
      }
      this.pendingResults = null;
    }
    if (z) {
      cleanUpActivityServices();
    }
    removeTimeouts();
    clearRelaunching();
  }

  public boolean isRelaunching() {
    return this.mPendingRelaunchCount > 0;
  }

  public void startRelaunching() {
    if (this.mPendingRelaunchCount == 0) {
      this.mRelaunchStartTime = SystemClock.elapsedRealtime();
      if (this.mVisibleRequested) {
        this.mDisplayContent.getDisplayPolicy().addRelaunchingApp(this);
      }
    }
    clearAllDrawn();
    this.mPendingRelaunchCount++;
  }

  public void finishRelaunching() {
    this.mLetterboxUiController.setRelaunchingAfterRequestedOrientationChanged(false);
    this.mTaskSupervisor.getActivityMetricsLogger().notifyActivityRelaunched(this);
    int i = this.mPendingRelaunchCount;
    if (i > 0) {
      int i2 = i - 1;
      this.mPendingRelaunchCount = i2;
      if (i2 == 0 && !isClientVisible()) {
        finishOrAbortReplacingWindow();
      }
    } else {
      checkKeyguardFlagsChanged();
    }
    Task rootTask = getRootTask();
    if (rootTask == null || !rootTask.shouldSleepOrShutDownActivities()) {
      return;
    }
    rootTask.ensureActivitiesVisible(null, 0, false);
  }

  public void clearRelaunching() {
    if (this.mPendingRelaunchCount == 0) {
      return;
    }
    this.mPendingRelaunchCount = 0;
    finishOrAbortReplacingWindow();
  }

  public void finishOrAbortReplacingWindow() {
    this.mRelaunchStartTime = 0L;
    this.mDisplayContent.getDisplayPolicy().removeRelaunchingApp(this);
  }

  public ActivityServiceConnectionsHolder getOrCreateServiceConnectionsHolder() {
    ActivityServiceConnectionsHolder activityServiceConnectionsHolder;
    synchronized (this) {
      if (this.mServiceConnectionsHolder == null) {
        this.mServiceConnectionsHolder = new ActivityServiceConnectionsHolder(this);
      }
      activityServiceConnectionsHolder = this.mServiceConnectionsHolder;
    }
    return activityServiceConnectionsHolder;
  }

  public final void cleanUpActivityServices() {
    synchronized (this) {
      ActivityServiceConnectionsHolder activityServiceConnectionsHolder =
          this.mServiceConnectionsHolder;
      if (activityServiceConnectionsHolder == null) {
        return;
      }
      activityServiceConnectionsHolder.disconnectActivityFromServices();
      this.mServiceConnectionsHolder = null;
    }
  }

  public final void updateVisibleForServiceConnection() {
    State state;
    this.mVisibleForServiceConnection =
        this.mVisibleRequested || (state = this.mState) == State.RESUMED || state == State.PAUSING;
  }

  /* JADX WARN: Code restructure failed: missing block: B:47:0x0013, code lost:

     if (r0 != 2) goto L13;
  */
  /* JADX WARN: Code restructure failed: missing block: B:51:0x0025, code lost:

     if (r10.launchCount > 2) goto L17;
  */
  /* JADX WARN: Code restructure failed: missing block: B:59:0x0054, code lost:

     if (r10.lastLaunchTime > (android.os.SystemClock.uptimeMillis() - 60000)) goto L4;
  */
  /* JADX WARN: Code restructure failed: missing block: B:65:0x0039, code lost:

     if (r10.app.skipToFinishActivities() == false) goto L4;
  */
  /* JADX WARN: Code restructure failed: missing block: B:69:0x001c, code lost:

     if (r10.finishing != false) goto L13;
  */
  /* JADX WARN: Removed duplicated region for block: B:27:0x00eb  */
  /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:5:0x0059  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void handleAppDied() {
    boolean z;
    Task task;
    ActivityRecord activityRecord;
    Task task2;
    WindowProcessController windowProcessController;
    if (!Process.isSdkSandboxUid(getUid())) {
      int i = this.mRelaunchReason;
      z = false;
      if (i != 1) {}
      if (this.launchCount < 3) {}
      if (this.mHaveState) {}
      if (!this.stateNotNeeded) {
        if (!isState(State.RESTARTING_PROCESS)) {}
      }
      if (!this.finishing) {
        if (!this.mVisibleRequested) {
          if (this.launchCount > 2) {}
        }
        if (z) {
          if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
            ProtoLogImpl.i(
                ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
                1789603530,
                204,
                (String) null,
                new Object[] {
                  String.valueOf(this),
                  Boolean.valueOf(this.mHaveState),
                  String.valueOf(this.stateNotNeeded),
                  Boolean.valueOf(this.finishing),
                  String.valueOf(this.mState),
                  String.valueOf(Debug.getCallers(5))
                });
          }
          if (!this.finishing
              || ((windowProcessController = this.app) != null
                  && windowProcessController.isRemoved())) {
            Slog.w("ActivityTaskManager", "Force removing " + this + ": app died, no saved state");
            int i2 = this.mUserId;
            int identityHashCode = System.identityHashCode(this);
            Task task3 = this.task;
            EventLogTags.writeWmFinishActivity(
                i2,
                identityHashCode,
                task3 != null ? task3.mTaskId : -1,
                this.shortComponentName,
                "proc died without state saved");
          }
        }
        this.mTransitionController.requestCloseTransitionIfNeeded(
            (z || (task2 = this.task) == null || task2.getChildCount() != 1) ? this : this.task);
        cleanUp(true, true);
        if (z) {
          return;
        }
        if (this.mStartingData != null
            && this.mVisible
            && (task = this.task) != null
            && (activityRecord = task.topRunningActivity()) != null
            && !activityRecord.mVisible
            && activityRecord.shouldBeVisible()) {
          activityRecord.transferStartingWindow(this);
        }
        removeFromHistory("appDied");
        return;
      }
    }
    z = true;
    if (z) {}
    this.mTransitionController.requestCloseTransitionIfNeeded(
        (z || (task2 = this.task) == null || task2.getChildCount() != 1) ? this : this.task);
    cleanUp(true, true);
    if (z) {}
  }

  @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
  public void removeImmediately() {
    if (this.mState != State.DESTROYED) {
      Slog.w("ActivityTaskManager", "Force remove immediately " + this + " state=" + this.mState);
      destroyImmediately("removeImmediately");
      destroyed("removeImmediately");
    } else {
      onRemovedFromDisplay();
    }
    this.mActivityRecordInputSink.releaseSurfaceControl();
    super.removeImmediately();
  }

  @Override // com.android.server.wm.WindowContainer
  public void removeIfPossible() {
    this.mIsExiting = false;
    removeAllWindowsIfPossible();
    removeImmediately();
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean handleCompleteDeferredRemoval() {
    if (this.mIsExiting) {
      removeIfPossible();
    }
    return super.handleCompleteDeferredRemoval();
  }

  /* JADX WARN: Code restructure failed: missing block: B:52:0x007f, code lost:

     if (r10.mTransitionController.inTransition() != false) goto L10;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void onRemovedFromDisplay() {
    if (this.mRemovingFromDisplay) {
      return;
    }
    this.mRemovingFromDisplay = true;
    if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
          -1352076759,
          0,
          (String) null,
          new Object[] {String.valueOf(this)});
    }
    getDisplayContent().mOpeningApps.remove(this);
    getDisplayContent().mUnknownAppVisibilityController.appRemovedOrHidden(this);
    this.mWmService.mSnapshotController.onAppRemoved(this);
    this.mTaskSupervisor.getActivityMetricsLogger().notifyActivityRemoved(this);
    this.mTaskSupervisor.mStoppingActivities.remove(this);
    this.mLetterboxUiController.destroy();
    this.waitingToShow = false;
    boolean isAnimating = isAnimating(7, 17);
    if (!getDisplayContent().mClosingApps.contains(this)) {
      if (getDisplayContent().mAppTransition.isTransitionSet()) {
        getDisplayContent().mClosingApps.add(this);
      }
    }
    isAnimating = true;
    if (!isAnimating) {
      commitVisibility(false, true);
    } else {
      setVisibleRequested(false);
    }
    this.mTransitionController.collect(this);
    if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
          1653210583,
          204,
          (String) null,
          new Object[] {
            String.valueOf(this),
            Boolean.valueOf(isAnimating),
            String.valueOf(getAnimation()),
            Boolean.valueOf(isAnimating(3, 1))
          });
    }
    if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
          -1539974875,
          12,
          (String) null,
          new Object[] {
            String.valueOf(this), Boolean.valueOf(isAnimating), String.valueOf(Debug.getCallers(4))
          });
    }
    if (this.mStartingData != null) {
      removeStartingWindow();
    }
    if (isAnimating(3, 1)) {
      getDisplayContent().mNoAnimationNotifyOnTransitionFinished.add(this.token);
    }
    if (isAnimating
        && (!isEmpty()
            || (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX
                && this.mTransitionController.inTransition(this)))) {
      if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
            -2109936758,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      this.mIsExiting = true;
    } else {
      cancelAnimation();
      removeIfPossible();
    }
    stopFreezingScreen(true, true);
    DisplayContent displayContent = getDisplayContent();
    if (displayContent.mFocusedApp == this) {
      if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
            -771177730,
            4,
            (String) null,
            new Object[] {String.valueOf(this), Long.valueOf(displayContent.getDisplayId())});
      }
      displayContent.setFocusedApp(null);
      this.mWmService.updateFocusedWindowLocked(0, true);
    }
    if (!isAnimating) {
      updateReportedVisibilityLocked();
    }
    this.mDisplayContent.mPinnedTaskController.onActivityHidden(this.mActivityComponent);
    this.mDisplayContent.onRunningActivityChanged();
    this.mWmService.mEmbeddedWindowController.onActivityRemoved(this);
    this.mRemovingFromDisplay = false;
  }

  @Override // com.android.server.wm.WindowToken
  public boolean isFirstChildWindowGreaterThanSecond(
      WindowState windowState, WindowState windowState2) {
    int i = windowState.mAttrs.type;
    int i2 = windowState2.mAttrs.type;
    if (i == 1 && i2 != 1) {
      return false;
    }
    if (i == 1 || i2 != 1) {
      return (i == 3 && i2 != 3) || i == 3 || i2 != 3;
    }
    return true;
  }

  public boolean hasStartingWindow() {
    if (this.mStartingData != null) {
      return true;
    }
    for (int size = this.mChildren.size() - 1; size >= 0; size--) {
      if (((WindowState) getChildAt(size)).mAttrs.type == 3) {
        return true;
      }
    }
    return false;
  }

  public boolean isLastWindow(WindowState windowState) {
    return this.mChildren.size() == 1 && this.mChildren.get(0) == windowState;
  }

  @Override // com.android.server.wm.WindowToken
  public void addWindow(WindowState windowState) {
    super.addWindow(windowState);
    if (windowState.mAttrs.type == 1 && windowState.isSecureLocked()) {
      this.mAtmService.mMultiTaskingController.notifySecureWindowAdded(windowState);
    }
    checkKeyguardFlagsChanged();
  }

  @Override // com.android.server.wm.WindowContainer
  public void removeChild(WindowState windowState) {
    if (this.mChildren.contains(windowState)) {
      if (windowState.mAttrs.type == 1 && windowState.isSecureLocked()) {
        this.mAtmService.mMultiTaskingController.notifySecureWindowRemoved(windowState);
      }
      super.removeChild((WindowContainer) windowState);
      checkKeyguardFlagsChanged();
      updateLetterboxSurface(windowState);
    }
  }

  public void setAppLayoutChanges(int i, String str) {
    if (this.mChildren.isEmpty()) {
      return;
    }
    DisplayContent displayContent = getDisplayContent();
    displayContent.pendingLayoutChanges = i | displayContent.pendingLayoutChanges;
  }

  public final boolean transferStartingWindow(ActivityRecord activityRecord) {
    if (this.mPopOverState.isActivated() != activityRecord.mPopOverState.isActivated()
        || this.mCompatRecord.isCompatModeEnabled()
            != activityRecord.mCompatRecord.isCompatModeEnabled()) {
      return false;
    }
    WindowState windowState = activityRecord.mStartingWindow;
    if (windowState != null && activityRecord.mStartingSurface != null) {
      if (windowState.getParent() == null) {
        return false;
      }
      if (activityRecord.mVisible) {
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX
            && this.mTransitionController.isCollecting()) {
          Transition collectingTransition = this.mTransitionController.getCollectingTransition();
          if (collectingTransition.isInTransition(this)) {
            collectingTransition.addFlag(1024);
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
              collectingTransition.setSkipMergeAnimation();
            }
          }
        }
        this.mDisplayContent.mSkipAppTransitionAnimation = true;
      }
      if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
            -1938204785,
            0,
            "Moving existing starting %s from %s to %s",
            new Object[] {
              String.valueOf(windowState), String.valueOf(activityRecord), String.valueOf(this)
            });
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (activityRecord.hasFixedRotationTransform()) {
          this.mDisplayContent.handleTopActivityLaunchingInDifferentOrientation(this, false);
          if (!this.mDisplayContent.isFixedRotationLaunchingApp(this)) {
            return false;
          }
        }
        this.mStartingData = activityRecord.mStartingData;
        this.mStartingSurface = activityRecord.mStartingSurface;
        this.mStartingWindow = windowState;
        this.reportedVisible = activityRecord.reportedVisible;
        activityRecord.mStartingData = null;
        activityRecord.mStartingSurface = null;
        activityRecord.mStartingWindow = null;
        activityRecord.startingMoved = true;
        windowState.mToken = this;
        windowState.mActivityRecord = this;
        if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
              -1499134947,
              0,
              (String) null,
              new Object[] {String.valueOf(windowState), String.valueOf(activityRecord)});
        }
        this.mTransitionController.collect(windowState);
        windowState.reparent(this, Integer.MAX_VALUE);
        windowState.clearFrozenInsetsState();
        if (activityRecord.allDrawn) {
          this.allDrawn = true;
        }
        if (activityRecord.firstWindowDrawn) {
          this.firstWindowDrawn = true;
        }
        if (activityRecord.isVisible()) {
          setVisible(true);
          setVisibleRequested(true);
          this.mVisibleSetFromTransferredStartingWindow = true;
        }
        setClientVisible(activityRecord.isClientVisible());
        if (activityRecord.isAnimating()) {
          transferAnimation(activityRecord);
          this.mTransitionChangeFlags |= 8;
        } else if (this.mTransitionController.getTransitionPlayer() != null) {
          this.mTransitionChangeFlags |= 8;
        }
        activityRecord.postWindowRemoveStartingWindowCleanup();
        activityRecord.mVisibleSetFromTransferredStartingWindow = false;
        this.mWmService.updateFocusedWindowLocked(3, true);
        getDisplayContent().setLayoutNeeded();
        this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
        return true;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    if (activityRecord.mStartingData == null) {
      return false;
    }
    if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
          -443173857,
          0,
          "Moving pending starting from %s to %s",
          new Object[] {String.valueOf(activityRecord), String.valueOf(this)});
    }
    this.mStartingData = activityRecord.mStartingData;
    activityRecord.mStartingData = null;
    activityRecord.startingMoved = true;
    scheduleAddStartingWindow();
    return true;
  }

  public void transferStartingWindowFromHiddenAboveTokenIfNeeded() {
    WindowState findMainWindow = findMainWindow(false);
    if (findMainWindow == null || !findMainWindow.mWinAnimator.getShown()) {
      this.task.forAllActivities(
          new Predicate() { // from class:
                            // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$transferStartingWindowFromHiddenAboveTokenIfNeeded$8;
              lambda$transferStartingWindowFromHiddenAboveTokenIfNeeded$8 =
                  ActivityRecord.this.lambda$transferStartingWindowFromHiddenAboveTokenIfNeeded$8(
                      (ActivityRecord) obj);
              return lambda$transferStartingWindowFromHiddenAboveTokenIfNeeded$8;
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$transferStartingWindowFromHiddenAboveTokenIfNeeded$8(
      ActivityRecord activityRecord) {
    if (activityRecord == this) {
      return true;
    }
    return !activityRecord.isVisibleRequested() && transferStartingWindow(activityRecord);
  }

  public boolean isKeyguardLocked() {
    DisplayContent displayContent = this.mDisplayContent;
    return displayContent != null
        ? displayContent.isKeyguardLocked()
        : this.mRootWindowContainer.getDefaultDisplay().isKeyguardLocked();
  }

  public void checkKeyguardFlagsChanged() {
    boolean containsDismissKeyguardWindow = containsDismissKeyguardWindow();
    boolean containsShowWhenLockedWindow = containsShowWhenLockedWindow();
    if (containsDismissKeyguardWindow != this.mLastContainsDismissKeyguardWindow
        || containsShowWhenLockedWindow != this.mLastContainsShowWhenLockedWindow) {
      this.mDisplayContent.notifyKeyguardFlagsChanged();
    }
    this.mLastContainsDismissKeyguardWindow = containsDismissKeyguardWindow;
    this.mLastContainsShowWhenLockedWindow = containsShowWhenLockedWindow;
    this.mLastContainsTurnScreenOnWindow = containsTurnScreenOnWindow();
  }

  public boolean containsDismissKeyguardWindow() {
    if (isRelaunching()) {
      return this.mLastContainsDismissKeyguardWindow;
    }
    for (int size = this.mChildren.size() - 1; size >= 0; size--) {
      if ((((WindowState) this.mChildren.get(size)).mAttrs.flags & 4194304) != 0) {
        return true;
      }
    }
    return false;
  }

  public boolean containsShowWhenLockedWindow() {
    if (isRelaunching()) {
      return this.mLastContainsShowWhenLockedWindow;
    }
    for (int size = this.mChildren.size() - 1; size >= 0; size--) {
      if ((((WindowState) this.mChildren.get(size)).mAttrs.flags & 524288) != 0
          && (this.mChildren.size() <= 1
              || ((WindowState) this.mChildren.get(size)).mAttrs.type != 3)) {
        return true;
      }
    }
    return false;
  }

  public void setShowWhenLocked(boolean z) {
    this.mShowWhenLocked = z;
    this.mAtmService.mRootWindowContainer.ensureActivitiesVisible(null, 0, false);
  }

  public void setInheritShowWhenLocked(boolean z) {
    this.mInheritShownWhenLocked = z;
    this.mAtmService.mRootWindowContainer.ensureActivitiesVisible(null, 0, false);
  }

  public static boolean canShowWhenLocked(ActivityRecord activityRecord) {
    ActivityRecord activityBelow;
    if (activityRecord == null || activityRecord.getTaskFragment() == null) {
      return false;
    }
    if (!activityRecord.inPinnedWindowingMode()
        && (activityRecord.mShowWhenLocked || activityRecord.containsShowWhenLockedWindow())) {
      return true;
    }
    if (!activityRecord.mInheritShownWhenLocked
        || (activityBelow = activityRecord.getTaskFragment().getActivityBelow(activityRecord))
            == null
        || activityBelow.inPinnedWindowingMode()) {
      return false;
    }
    return activityBelow.mShowWhenLocked || activityBelow.containsShowWhenLockedWindow();
  }

  public boolean canShowWhenLocked() {
    TaskFragment taskFragment = getTaskFragment();
    if (taskFragment == null
        || taskFragment.getAdjacentTaskFragment() == null
        || !taskFragment.isEmbedded()) {
      return canShowWhenLocked(this);
    }
    return canShowWhenLocked(this)
        && canShowWhenLocked(taskFragment.getAdjacentTaskFragment().getTopNonFinishingActivity());
  }

  public boolean canShowWindows() {
    return this.mTransitionController.isShellTransitionsEnabled()
        ? this.mSyncState != 1
        : this.allDrawn;
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean forAllActivities(Predicate predicate, boolean z) {
    return predicate.test(this);
  }

  @Override // com.android.server.wm.WindowContainer
  public void forAllActivities(Consumer consumer, boolean z) {
    consumer.accept(this);
  }

  @Override // com.android.server.wm.WindowContainer
  public ActivityRecord getActivity(Predicate predicate, boolean z, ActivityRecord activityRecord) {
    if (predicate.test(this)) {
      return this;
    }
    return null;
  }

  public void logStartActivity(int i, Task task, String str) {
    Uri data = this.intent.getData();
    String safeString = data != null ? data.toSafeString() : null;
    if (safeString != null && safeString.startsWith("nfc://secure")) {
      safeString = "nfc://secure:it should not be shown";
    }
    EventLog.writeEvent(
        i,
        Integer.valueOf(this.mUserId),
        Integer.valueOf(System.identityHashCode(this)),
        Integer.valueOf(task.mTaskId),
        this.shortComponentName,
        this.intent.getAction(),
        this.intent.getType(),
        safeString,
        Integer.valueOf(this.intent.getFlags()),
        str);
  }

  public UriPermissionOwner getUriPermissionsLocked() {
    if (this.uriPermissions == null) {
      this.uriPermissions = new UriPermissionOwner(this.mAtmService.mUgmInternal, this);
    }
    return this.uriPermissions;
  }

  public void addResultLocked(
      ActivityRecord activityRecord, String str, int i, int i2, Intent intent) {
    ActivityResult activityResult = new ActivityResult(activityRecord, str, i, i2, intent);
    if (this.results == null) {
      this.results = new ArrayList();
    }
    this.results.add(activityResult);
  }

  /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
  /* JADX WARN: Removed duplicated region for block: B:15:0x0031 A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void removeResultsLocked(ActivityRecord activityRecord, String str, int i) {
    ArrayList arrayList = this.results;
    if (arrayList != null) {
      for (int size = arrayList.size() - 1; size >= 0; size--) {
        ActivityResult activityResult = (ActivityResult) this.results.get(size);
        if (activityResult.mFrom == activityRecord) {
          String str2 = ((ResultInfo) activityResult).mResultWho;
          if (str2 != null) {
            if (!str2.equals(str)) {}
            if (((ResultInfo) activityResult).mRequestCode != i) {}
          } else {
            if (str != null) {}
            if (((ResultInfo) activityResult).mRequestCode != i) {
              this.results.remove(size);
            }
          }
        }
      }
    }
  }

  public void sendResult(
      int i, String str, int i2, int i3, Intent intent, NeededUriGrants neededUriGrants) {
    sendResult(i, str, i2, i3, intent, neededUriGrants, false);
  }

  public void sendResult(
      int i,
      String str,
      int i2,
      int i3,
      Intent intent,
      NeededUriGrants neededUriGrants,
      boolean z) {
    if (i > 0) {
      this.mAtmService.mUgmInternal.grantUriPermissionUncheckedFromIntent(
          neededUriGrants, getUriPermissionsLocked());
    }
    if (isState(State.RESUMED) && attachedToProcess()) {
      try {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ResultInfo(str, i2, i3, intent));
        this.mAtmService
            .getLifecycleManager()
            .scheduleTransaction(
                this.app.getThread(),
                this.token,
                (ClientTransactionItem) ActivityResultItem.obtain(arrayList));
        return;
      } catch (Exception e) {
        Slog.w("ActivityTaskManager", "Exception thrown sending result to " + this, e);
      }
    }
    if (z
        && attachedToProcess()
        && isState(State.STARTED, State.PAUSING, State.PAUSED, State.STOPPING, State.STOPPED)) {
      ClientTransaction obtain = ClientTransaction.obtain(this.app.getThread(), this.token);
      obtain.addCallback(ActivityResultItem.obtain(List.of(new ResultInfo(str, i2, i3, intent))));
      ActivityLifecycleItem lifecycleItemForCurrentStateForResult =
          getLifecycleItemForCurrentStateForResult();
      if (lifecycleItemForCurrentStateForResult != null) {
        obtain.setLifecycleStateRequest(lifecycleItemForCurrentStateForResult);
      } else {
        Slog.w(
            "ActivityTaskManager",
            "Unable to get the lifecycle item for state "
                + this.mState
                + " so couldn't immediately send result");
      }
      try {
        this.mAtmService.getLifecycleManager().scheduleTransaction(obtain);
        return;
      } catch (RemoteException e2) {
        Slog.w("ActivityTaskManager", "Exception thrown sending result to " + this, e2);
        return;
      }
    }
    addResultLocked(null, str, i2, i3, intent);
  }

  public final ActivityLifecycleItem getLifecycleItemForCurrentStateForResult() {
    int i =
        AbstractC28617.$SwitchMap$com$android$server$wm$ActivityRecord$State[this.mState.ordinal()];
    if (i == 3 || i == 4) {
      return PauseActivityItem.obtain();
    }
    if (i != 5) {
      if (i == 6) {
        return StartActivityItem.obtain((ActivityOptions) null);
      }
      if (i != 7) {
        return null;
      }
    }
    return StopActivityItem.obtain(this.configChangeFlags);
  }

  public final void addNewIntentLocked(ReferrerIntent referrerIntent) {
    if (this.newIntents == null) {
      this.newIntents = new ArrayList();
    }
    this.newIntents.add(referrerIntent);
  }

  public final boolean isSleeping() {
    Task rootTask = getRootTask();
    return rootTask != null
        ? rootTask.shouldSleepActivities()
        : this.mAtmService.isSleepingLocked();
  }

  public final void deliverNewIntentLocked(
      int i, Intent intent, NeededUriGrants neededUriGrants, String str) {
    this.mAtmService.mUgmInternal.grantUriPermissionUncheckedFromIntent(
        neededUriGrants, getUriPermissionsLocked());
    boolean z = true;
    if (CoreRune.FW_APPLOCK) {
      try {
        Intent intent2 = new Intent(intent);
        if (intent2.getIntExtra("LAUNCH_FROM_NOTIFICATION", -1) == 1
            || (intent2.getFlags() & 67108864) != 0) {
          setLaunchingRequestFromNotification(true);
        }
      } catch (Exception e) {
        Slog.d("ActivityTaskManager", "Exception while parsing intent but ignorable, was : " + e);
      }
    }
    ReferrerIntent referrerIntent = new ReferrerIntent(intent, getFilteredReferrer(str));
    boolean z2 = isTopRunningActivity() && isSleeping();
    State state = this.mState;
    State state2 = State.RESUMED;
    if ((state == state2 || state == State.PAUSED || z2) && attachedToProcess()) {
      try {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(referrerIntent);
        this.mAtmService
            .getLifecycleManager()
            .scheduleTransaction(
                this.app.getThread(),
                this.token,
                (ClientTransactionItem) NewIntentItem.obtain(arrayList, this.mState == state2));
        z = false;
      } catch (RemoteException e2) {
        Slog.w("ActivityTaskManager", "Exception thrown sending new intent to " + this, e2);
      } catch (NullPointerException e3) {
        Slog.w("ActivityTaskManager", "Exception thrown sending new intent to " + this, e3);
      }
    }
    if (z) {
      addNewIntentLocked(referrerIntent);
    }
  }

  public void updateOptionsLocked(ActivityOptions activityOptions) {
    if (activityOptions != null) {
      ActivityOptions activityOptions2 = this.mPendingOptions;
      if (activityOptions2 != null) {
        activityOptions2.abort();
      }
      setOptions(activityOptions);
    }
  }

  public boolean getLaunchedFromBubble() {
    return this.mLaunchedFromBubble;
  }

  public final void setOptions(ActivityOptions activityOptions) {
    this.mLaunchedFromBubble = activityOptions.getLaunchedFromBubble();
    this.mPendingOptions = activityOptions;
    if (activityOptions.getAnimationType() == 13) {
      this.mPendingRemoteAnimation = activityOptions.getRemoteAnimationAdapter();
    }
    this.mPendingRemoteTransition = activityOptions.getRemoteTransition();
  }

  public void applyOptionsAnimation() {
    RemoteAnimationAdapter remoteAnimationAdapter = this.mPendingRemoteAnimation;
    if (remoteAnimationAdapter != null) {
      this.mDisplayContent.mAppTransition.overridePendingAppTransitionRemote(
          remoteAnimationAdapter);
      this.mTransitionController.setStatusBarTransitionDelay(
          this.mPendingRemoteAnimation.getStatusBarTransitionDelay());
    } else {
      ActivityOptions activityOptions = this.mPendingOptions;
      if (activityOptions == null) {
        return;
      }
      if (activityOptions.getAnimationType() == 5) {
        this.mTransitionController.setOverrideAnimation(
            TransitionInfo.AnimationOptions.makeSceneTransitionAnimOptions(), null, null);
        return;
      }
      applyOptionsAnimation(this.mPendingOptions, this.intent);
    }
    clearOptionsAnimationForSiblings();
  }

  /* JADX WARN: Removed duplicated region for block: B:22:0x0214  */
  /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void applyOptionsAnimation(ActivityOptions activityOptions, Intent intent) {
    IRemoteCallback iRemoteCallback;
    IRemoteCallback iRemoteCallback2;
    TransitionInfo.AnimationOptions makeScaleUpAnimOptions;
    int animationType = activityOptions.getAnimationType();
    DisplayContent displayContent = getDisplayContent();
    TransitionInfo.AnimationOptions animationOptions = null;
    if (animationType != -1 && animationType != 0) {
      if (animationType != 1) {
        if (animationType == 2) {
          displayContent.mAppTransition.overridePendingAppTransitionScaleUp(
              activityOptions.getStartX(),
              activityOptions.getStartY(),
              activityOptions.getWidth(),
              activityOptions.getHeight());
          makeScaleUpAnimOptions =
              TransitionInfo.AnimationOptions.makeScaleUpAnimOptions(
                  activityOptions.getStartX(),
                  activityOptions.getStartY(),
                  activityOptions.getWidth(),
                  activityOptions.getHeight());
          if (intent.getSourceBounds() == null) {
            intent.setSourceBounds(
                new Rect(
                    activityOptions.getStartX(),
                    activityOptions.getStartY(),
                    activityOptions.getStartX() + activityOptions.getWidth(),
                    activityOptions.getStartY() + activityOptions.getHeight()));
          }
        } else if (animationType == 3 || animationType == 4) {
          boolean z = animationType == 3;
          HardwareBuffer thumbnail = activityOptions.getThumbnail();
          displayContent.mAppTransition.overridePendingAppTransitionThumb(
              thumbnail,
              activityOptions.getStartX(),
              activityOptions.getStartY(),
              activityOptions.getAnimationStartedListener(),
              z);
          TransitionInfo.AnimationOptions makeThumbnailAnimOptions =
              TransitionInfo.AnimationOptions.makeThumbnailAnimOptions(
                  thumbnail, activityOptions.getStartX(), activityOptions.getStartY(), z);
          IRemoteCallback animationStartedListener = activityOptions.getAnimationStartedListener();
          if (intent.getSourceBounds() == null && thumbnail != null) {
            intent.setSourceBounds(
                new Rect(
                    activityOptions.getStartX(),
                    activityOptions.getStartY(),
                    activityOptions.getStartX() + thumbnail.getWidth(),
                    activityOptions.getStartY() + thumbnail.getHeight()));
          }
          iRemoteCallback = animationStartedListener;
          iRemoteCallback2 = null;
          animationOptions = makeThumbnailAnimOptions;
        } else if (animationType == 8 || animationType == 9) {
          AppTransitionAnimationSpec[] animSpecs = activityOptions.getAnimSpecs();
          IAppTransitionAnimationSpecsFuture specsFuture = activityOptions.getSpecsFuture();
          if (specsFuture != null) {
            displayContent.mAppTransition.overridePendingAppTransitionMultiThumbFuture(
                specsFuture, activityOptions.getAnimationStartedListener(), animationType == 8);
          } else if (animationType == 9 && animSpecs != null) {
            displayContent.mAppTransition.overridePendingAppTransitionMultiThumb(
                animSpecs,
                activityOptions.getAnimationStartedListener(),
                activityOptions.getAnimationFinishedListener(),
                false);
          } else {
            displayContent.mAppTransition.overridePendingAppTransitionAspectScaledThumb(
                activityOptions.getThumbnail(),
                activityOptions.getStartX(),
                activityOptions.getStartY(),
                activityOptions.getWidth(),
                activityOptions.getHeight(),
                activityOptions.getAnimationStartedListener(),
                animationType == 8);
            if (intent.getSourceBounds() == null) {
              intent.setSourceBounds(
                  new Rect(
                      activityOptions.getStartX(),
                      activityOptions.getStartY(),
                      activityOptions.getStartX() + activityOptions.getWidth(),
                      activityOptions.getStartY() + activityOptions.getHeight()));
            }
          }
        } else if (animationType == 11) {
          displayContent.mAppTransition.overridePendingAppTransitionClipReveal(
              activityOptions.getStartX(),
              activityOptions.getStartY(),
              activityOptions.getWidth(),
              activityOptions.getHeight());
          makeScaleUpAnimOptions =
              TransitionInfo.AnimationOptions.makeClipRevealAnimOptions(
                  activityOptions.getStartX(),
                  activityOptions.getStartY(),
                  activityOptions.getWidth(),
                  activityOptions.getHeight());
          if (intent.getSourceBounds() == null) {
            intent.setSourceBounds(
                new Rect(
                    activityOptions.getStartX(),
                    activityOptions.getStartY(),
                    activityOptions.getStartX() + activityOptions.getWidth(),
                    activityOptions.getStartY() + activityOptions.getHeight()));
          }
        } else if (animationType == 12) {
          displayContent.mAppTransition.overridePendingAppTransitionStartCrossProfileApps();
          iRemoteCallback2 = null;
          animationOptions = TransitionInfo.AnimationOptions.makeCrossProfileAnimOptions();
          iRemoteCallback = null;
        } else {
          Slog.e(
              StartingSurfaceController.TAG,
              "applyOptionsLocked: Unknown animationType=" + animationType);
        }
        iRemoteCallback = null;
        animationOptions = makeScaleUpAnimOptions;
        iRemoteCallback2 = iRemoteCallback;
      } else {
        displayContent.mAppTransition.overridePendingAppTransition(
            activityOptions.getPackageName(),
            activityOptions.getCustomEnterResId(),
            activityOptions.getCustomExitResId(),
            activityOptions.getCustomBackgroundColor(),
            activityOptions.getAnimationStartedListener(),
            activityOptions.getAnimationFinishedListener(),
            activityOptions.getOverrideTaskTransition());
        animationOptions =
            TransitionInfo.AnimationOptions.makeCustomAnimOptions(
                activityOptions.getPackageName(),
                activityOptions.getCustomEnterResId(),
                activityOptions.getCustomExitResId(),
                activityOptions.getCustomBackgroundColor(),
                activityOptions.getOverrideTaskTransition());
        iRemoteCallback = activityOptions.getAnimationStartedListener();
        iRemoteCallback2 = activityOptions.getAnimationFinishedListener();
      }
      if (animationOptions == null) {
        this.mTransitionController.setOverrideAnimation(
            animationOptions, iRemoteCallback, iRemoteCallback2);
        return;
      }
      return;
    }
    iRemoteCallback = null;
    iRemoteCallback2 = iRemoteCallback;
    if (animationOptions == null) {}
  }

  public void clearAllDrawn() {
    this.allDrawn = false;
    this.mLastAllDrawn = false;
  }

  public final boolean allDrawnStatesConsidered() {
    for (int size = this.mChildren.size() - 1; size >= 0; size--) {
      WindowState windowState = (WindowState) this.mChildren.get(size);
      if (windowState.mightAffectAllDrawn() && !windowState.getDrawnStateEvaluated()) {
        return false;
      }
    }
    return true;
  }

  public void updateAllDrawn() {
    int i;
    if (this.allDrawn
        || (i = this.mNumInterestingWindows) <= 0
        || !allDrawnStatesConsidered()
        || this.mNumDrawnWindows < i
        || isRelaunching()) {
      return;
    }
    this.allDrawn = true;
    if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
      this.mAtmService.mChangeTransitController.onActivityAllDrawn(this);
    }
    DisplayContent displayContent = this.mDisplayContent;
    if (displayContent != null) {
      displayContent.setLayoutNeeded();
    }
    this.mWmService.f1749mH.obtainMessage(32, this).sendToTarget();
  }

  public void abortAndClearOptionsAnimation() {
    ActivityOptions activityOptions = this.mPendingOptions;
    if (activityOptions != null) {
      activityOptions.abort();
    }
    clearOptionsAnimation();
  }

  public void clearOptionsAnimation() {
    this.mPendingOptions = null;
    this.mPendingRemoteAnimation = null;
    this.mPendingRemoteTransition = null;
  }

  public void clearOptionsAnimationForSiblings() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task task = this.task;
        if (task == null) {
          clearOptionsAnimation();
        } else {
          task.forAllActivities(
              new Consumer() { // from class:
                               // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda20
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ((ActivityRecord) obj).clearOptionsAnimation();
                }
              });
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public ActivityOptions getOptions() {
    return this.mPendingOptions;
  }

  public ActivityOptions takeOptions() {
    ActivityOptions activityOptions = this.mPendingOptions;
    if (activityOptions == null) {
      return null;
    }
    this.mPendingOptions = null;
    activityOptions.setRemoteTransition(null);
    activityOptions.setRemoteAnimationAdapter(null);
    return activityOptions;
  }

  public RemoteTransition takeRemoteTransition() {
    RemoteTransition remoteTransition = this.mPendingRemoteTransition;
    this.mPendingRemoteTransition = null;
    if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX || (this.intent.getFlags() & 65536) == 0) {
      return remoteTransition;
    }
    return null;
  }

  public boolean allowMoveToFront() {
    ActivityOptions activityOptions = this.mPendingOptions;
    return activityOptions == null || !activityOptions.getAvoidMoveToFront();
  }

  public void removeUriPermissionsLocked() {
    UriPermissionOwner uriPermissionOwner = this.uriPermissions;
    if (uriPermissionOwner != null) {
      uriPermissionOwner.removeUriPermissions();
      this.uriPermissions = null;
    }
  }

  public void pauseKeyDispatchingLocked() {
    if (this.keysPaused) {
      return;
    }
    this.keysPaused = true;
    if (getDisplayContent() != null) {
      getDisplayContent().getInputMonitor().pauseDispatchingLw(this);
    }
  }

  public void resumeKeyDispatchingLocked() {
    if (this.keysPaused) {
      this.keysPaused = false;
      if (getDisplayContent() != null) {
        getDisplayContent().getInputMonitor().resumeDispatchingLw(this);
      }
    }
  }

  public final void updateTaskDescription(CharSequence charSequence) {
    this.task.lastDescription = charSequence;
  }

  public void setDeferHidingClient(boolean z) {
    if (this.mDeferHidingClient == z) {
      return;
    }
    this.mDeferHidingClient = z;
    if (z || this.mVisibleRequested) {
      return;
    }
    setVisibility(false);
  }

  public boolean canAffectSystemUiFlags() {
    Task task = this.task;
    return task != null
        && task.canAffectSystemUiFlags()
        && isVisible()
        && (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX
            || this.mDisplayContent.mTransitionController.isCollecting()
            || isVisibleRequested())
        && !inPinnedWindowingMode();
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean isVisible() {
    return this.mVisible;
  }

  public void setVisible(boolean z) {
    if (z != this.mVisible) {
      this.mVisible = z;
      WindowProcessController windowProcessController = this.app;
      if (windowProcessController != null) {
        this.mTaskSupervisor.onProcessActivityStateChanged(windowProcessController, false);
      }
      scheduleAnimation();
    }
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean setVisibleRequested(boolean z) {
    CompatDisplayInsets compatDisplayInsets;
    WindowState windowState;
    if (!super.setVisibleRequested(z)) {
      return false;
    }
    setInsetsFrozen(!z);
    updateVisibleForServiceConnection();
    WindowProcessController windowProcessController = this.app;
    if (windowProcessController != null) {
      this.mTaskSupervisor.onProcessActivityStateChanged(windowProcessController, false);
    }
    logAppCompatState();
    if (!z) {
      InputTarget imeInputTarget = this.mDisplayContent.getImeInputTarget();
      this.mLastImeShown =
          (imeInputTarget == null
                  || imeInputTarget.getWindowState() == null
                  || imeInputTarget.getWindowState().mActivityRecord != this
                  || (windowState = this.mDisplayContent.mInputMethodWindow) == null
                  || !windowState.isVisible())
              ? false
              : true;
      finishOrAbortReplacingWindow();
    }
    if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT
        && z
        && (compatDisplayInsets = this.mCompatDisplayInsets) != null
        && compatDisplayInsets.mWaitingForVisibleRequested) {
      compatDisplayInsets.mWaitingForVisibleRequested = false;
    }
    return true;
  }

  public void setVisibility(boolean z) {
    if (getParent() == null) {
      Slog.w(
          StartingSurfaceController.TAG,
          "Attempted to set visibility of non-existing app token: " + this.token);
      return;
    }
    if (z == this.mVisibleRequested
        && z == this.mVisible
        && z == isClientVisible()
        && this.mTransitionController.isShellTransitionsEnabled()) {
      return;
    }
    if (z) {
      this.mDeferHidingClient = false;
    }
    setVisibility(z, this.mDeferHidingClient);
    this.mAtmService.addWindowLayoutReasons(2);
    this.mTaskSupervisor.getActivityMetricsLogger().notifyVisibilityChanged(this);
    this.mTaskSupervisor.mAppVisibilitiesChangedSinceLastPause = true;
  }

  public final void setVisibility(boolean z, boolean z2) {
    boolean z3;
    boolean z4;
    boolean z5;
    AppTransition appTransition = getDisplayContent().mAppTransition;
    if (!z && !this.mVisibleRequested) {
      if (z2 || !this.mLastDeferHidingClient) {
        return;
      }
      this.mLastDeferHidingClient = z2;
      if (okToAnimate(true, canTurnScreenOn()) && inTransition()) {
        return;
      }
      setClientVisible(false);
      return;
    }
    if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
          -374767836,
          972,
          (String) null,
          new Object[] {
            String.valueOf(this.token),
            Boolean.valueOf(z),
            String.valueOf(appTransition),
            Boolean.valueOf(isVisible()),
            Boolean.valueOf(this.mVisibleRequested),
            String.valueOf(Debug.getCallers(6))
          });
    }
    if (this.mTransitionController.isShellTransitionsEnabled()) {
      z3 = this.mTransitionController.isCollecting();
      if (z3) {
        this.mTransitionController.collect(this);
        z4 = false;
      } else {
        z4 = this.mTransitionController.inFinishingTransition(this);
        if (!z4) {
          if (z) {
            if (!this.mDisplayContent.isSleeping() || canShowWhenLocked()) {
              this.mTransitionController.onVisibleWithoutCollectingTransition(
                  this, Debug.getCallers(1, 1));
            }
          } else if (!this.mDisplayContent.isSleeping()) {
            Slog.w("ActivityTaskManager", "Set invisible without transition " + this);
          }
        }
      }
    } else {
      z3 = false;
      z4 = false;
    }
    onChildVisibilityRequested(z);
    DisplayContent displayContent = getDisplayContent();
    displayContent.mOpeningApps.remove(this);
    displayContent.mClosingApps.remove(this);
    this.waitingToShow = false;
    boolean visibleRequested = setVisibleRequested(z);
    this.mLastDeferHidingClient = z2;
    if (!z) {
      if (this.mDisplayContent.mUnknownAppVisibilityController.isVisibilityUnknown(this)) {
        Task topRootTaskInWindowingMode =
            getTaskDisplayArea().getTopRootTaskInWindowingMode(getWindowingMode());
        ActivityRecord activityRecord =
            topRootTaskInWindowingMode != null
                ? topRootTaskInWindowingMode.topRunningActivity()
                : null;
        if (activityRecord != null && activityRecord != this) {
          z5 = true;
          if (!this.finishing || isState(State.STOPPED) || z5) {
            displayContent.mUnknownAppVisibilityController.appRemovedOrHidden(this);
          }
          if (this.startingMoved && !this.firstWindowDrawn && hasChild()) {
            setClientVisible(false);
          }
        }
      }
      z5 = false;
      if (!this.finishing) {}
      displayContent.mUnknownAppVisibilityController.appRemovedOrHidden(this);
      if (this.startingMoved) {
        setClientVisible(false);
      }
    } else {
      if (!appTransition.isTransitionSet() && appTransition.isReady()) {
        displayContent.mOpeningApps.add(this);
      }
      this.startingMoved = false;
      if (!isVisible() || this.mAppStopped) {
        clearAllDrawn();
        if (!isVisible()) {
          this.waitingToShow = true;
          if (!isClientVisible()) {
            forAllWindows(
                new Consumer() { // from class:
                                 // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda1
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    ActivityRecord.lambda$setVisibility$9((WindowState) obj);
                  }
                },
                true);
          }
        }
      }
      setClientVisible(true);
      requestUpdateWallpaperIfNeeded();
      if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
            1224184681,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      this.mAppStopped = false;
      transferStartingWindowFromHiddenAboveTokenIfNeeded();
    }
    if (!z3) {
      if (z4) {
        this.mTransitionController.mValidateCommitVis.add(this);
        return;
      } else {
        if (deferCommitVisibilityChange(z)) {
          return;
        }
        commitVisibility(z, true);
        updateReportedVisibilityLocked();
        return;
      }
    }
    if (!z && this.mTransitionController.inPlayingTransition(this)) {
      this.mTransitionChangeFlags |= 32768;
    }
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && !z && visibleRequested) {
      Slog.d(StartingSurfaceController.TAG, "VisibleRequested updated, r=" + this);
      this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
    }
  }

  public static /* synthetic */ void lambda$setVisibility$9(WindowState windowState) {
    WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
    if (windowStateAnimator.mDrawState == 4) {
      windowStateAnimator.resetDrawState();
      windowState.forceReportingResized();
    }
  }

  public final boolean deferCommitVisibilityChange(boolean z) {
    WindowState findFocusedWindow;
    ActivityRecord activityRecord;
    if (this.mTransitionController.isShellTransitionsEnabled()) {
      return false;
    }
    if (!this.mDisplayContent.mAppTransition.isTransitionSet()
        && (isActivityTypeHome() || !isAnimating(2, 8))) {
      return false;
    }
    if (this.mWaitForEnteringPinnedMode && this.mVisible == z) {
      return false;
    }
    if (!okToAnimate(
        true,
        canTurnScreenOn()
            || this.mTaskSupervisor
                .getKeyguardController()
                .isKeyguardGoingAway(this.mDisplayContent.mDisplayId))) {
      return false;
    }
    if (z) {
      this.mDisplayContent.mOpeningApps.add(this);
      this.mEnteringAnimation = true;
    } else if (this.mVisible) {
      this.mDisplayContent.mClosingApps.add(this);
      this.mEnteringAnimation = false;
    }
    if ((this.mDisplayContent.mAppTransition.getTransitFlags() & 32) != 0
        && (findFocusedWindow = this.mDisplayContent.findFocusedWindow()) != null
        && (activityRecord = findFocusedWindow.mActivityRecord) != null) {
      if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
            1810019902,
            0,
            (String) null,
            new Object[] {String.valueOf(activityRecord)});
      }
      this.mDisplayContent.mOpeningApps.add(activityRecord);
    }
    return true;
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean applyAnimation(
      WindowManager.LayoutParams layoutParams, int i, boolean z, boolean z2, ArrayList arrayList) {
    if ((this.mTransitionChangeFlags & 8) != 0) {
      return false;
    }
    this.mRequestForceTransition = false;
    return super.applyAnimation(layoutParams, i, z, z2, arrayList);
  }

  public void commitVisibility(boolean z, boolean z2, boolean z3) {
    this.mVisibleSetFromTransferredStartingWindow = false;
    if (z == isVisible()) {
      return;
    }
    int size = this.mChildren.size();
    boolean isAnimating = WindowManagerService.sEnableShellTransitions ? z : isAnimating(2, 1);
    for (int i = 0; i < size; i++) {
      ((WindowState) this.mChildren.get(i)).onAppVisibilityChanged(z, isAnimating);
    }
    setVisible(z);
    setVisibleRequested(z);
    if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
          -636553602,
          1020,
          (String) null,
          new Object[] {
            String.valueOf(this),
            Boolean.valueOf(isVisible()),
            Boolean.valueOf(this.mVisibleRequested),
            Boolean.valueOf(isInTransition()),
            Boolean.valueOf(isAnimating),
            String.valueOf(Debug.getCallers(5))
          });
    }
    if (!z) {
      stopFreezingScreen(true, true);
    } else {
      WindowState windowState = this.mStartingWindow;
      if (windowState != null
          && !windowState.isDrawn()
          && (this.firstWindowDrawn || this.allDrawn)) {
        this.mStartingWindow.clearPolicyVisibilityFlag(1);
        this.mStartingWindow.mLegacyPolicyVisibilityAfterAnim = false;
      }
      final WindowManagerService windowManagerService = this.mWmService;
      Objects.requireNonNull(windowManagerService);
      forAllWindows(
          new Consumer() { // from class:
                           // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              WindowManagerService.this.makeWindowFreezingScreenIfNeededLocked((WindowState) obj);
            }
          },
          true);
    }
    for (Task organizedTask = getOrganizedTask();
        organizedTask != null;
        organizedTask = organizedTask.getParent().asTask()) {
      organizedTask.dispatchTaskInfoChangedIfNeeded(false);
    }
    DisplayContent displayContent = getDisplayContent();
    displayContent.getInputMonitor().setUpdateInputWindowsNeededLw();
    if (z2) {
      this.mWmService.updateFocusedWindowLocked(3, false);
      this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
    }
    displayContent.getInputMonitor().updateInputWindowsLw(false);
    this.mTransitionChangeFlags = 0;
    postApplyAnimation(z, z3);
  }

  public void commitVisibility(boolean z, boolean z2) {
    commitVisibility(z, z2, false);
  }

  public void setNeedsLetterboxedAnimation(boolean z) {
    this.mNeedsLetterboxedAnimation = z;
  }

  public boolean isNeedsLetterboxedAnimation() {
    return this.mNeedsLetterboxedAnimation;
  }

  public boolean isInLetterboxAnimation() {
    return this.mNeedsLetterboxedAnimation && isAnimating();
  }

  public final void postApplyAnimation(boolean z, boolean z2) {
    boolean isShellTransitionsEnabled = this.mTransitionController.isShellTransitionsEnabled();
    boolean z3 = !isShellTransitionsEnabled && isAnimating(6, 25);
    if (!z3 && !isShellTransitionsEnabled) {
      onAnimationFinished(1, null);
      if (z) {
        this.mEnteringAnimation = true;
        this.mWmService.mActivityManagerAppTransitionNotifier.onAppTransitionFinishedLocked(
            this.token);
      }
    }
    if (z || isShellTransitionsEnabled || !isAnimating(2, 9)) {
      setClientVisible(z);
    }
    DisplayContent displayContent = getDisplayContent();
    if (!z) {
      this.mImeInsetsFrozenUntilStartInput = true;
    }
    if (!displayContent.mClosingApps.contains(this)
        && !displayContent.mOpeningApps.contains(this)
        && !z2) {
      this.mWmService.mSnapshotController.notifyAppVisibilityChanged(this, z);
    }
    if (isShellTransitionsEnabled
        || isVisible()
        || z3
        || displayContent.mAppTransition.isTransitionSet()) {
      return;
    }
    SurfaceControl.openTransaction();
    try {
      forAllWindows(
          new Consumer() { // from class:
                           // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda28
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ActivityRecord.lambda$postApplyAnimation$10((WindowState) obj);
            }
          },
          true);
    } finally {
      SurfaceControl.closeTransaction();
    }
  }

  public static /* synthetic */ void lambda$postApplyAnimation$10(WindowState windowState) {
    windowState.mWinAnimator.hide(SurfaceControl.getGlobalTransaction(), "immediately hidden");
  }

  public void commitFinishDrawing(SurfaceControl.Transaction transaction) {
    boolean z = false;
    for (int size = this.mChildren.size() - 1; size >= 0; size--) {
      z |= ((WindowState) this.mChildren.get(size)).commitFinishDrawing(transaction);
    }
    if (z) {
      requestUpdateWallpaperIfNeeded();
    }
  }

  public boolean shouldApplyAnimation(boolean z) {
    return isVisible() != z || this.mRequestForceTransition || (!isVisible() && this.mIsExiting);
  }

  public void setRecentsScreenshotEnabled(boolean z) {
    this.mEnableRecentsScreenshot = z;
  }

  public boolean shouldUseAppThemeSnapshot() {
    return !this.mEnableRecentsScreenshot
        || forAllWindows(
            new ToBooleanFunction() { // from class:
                                      // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda27
              public final boolean apply(Object obj) {
                return ((WindowState) obj).isSecureLocked();
              }
            },
            true);
  }

  public void setCurrentLaunchCanTurnScreenOn(boolean z) {
    this.mCurrentLaunchCanTurnScreenOn = z;
  }

  public boolean currentLaunchCanTurnScreenOn() {
    return this.mCurrentLaunchCanTurnScreenOn;
  }

  public void setState(State state, String str) {
    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STATES,
          1316533291,
          0,
          (String) null,
          new Object[] {
            String.valueOf(this),
            String.valueOf(getState()),
            String.valueOf(state),
            String.valueOf(str)
          });
    }
    if (state == this.mState) {
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STATES,
            -926231510,
            0,
            (String) null,
            new Object[] {String.valueOf(state)});
        return;
      }
      return;
    }
    this.mState = state;
    if (getTaskFragment() != null) {
      getTaskFragment().onActivityStateChanged(this, state, str);
    }
    if (state == State.STOPPING && !isSleeping() && getParent() == null) {
      Slog.w(
          StartingSurfaceController.TAG,
          "Attempted to notify stopping on non-existing app token: " + this.token);
      return;
    }
    updateVisibleForServiceConnection();
    WindowProcessController windowProcessController = this.app;
    if (windowProcessController != null) {
      this.mTaskSupervisor.onProcessActivityStateChanged(windowProcessController, false);
    }
    int i = AbstractC28617.$SwitchMap$com$android$server$wm$ActivityRecord$State[state.ordinal()];
    if (i != 2) {
      if (i == 4) {
        this.mAtmService.updateBatteryStats(this, false);
        this.mAtmService.updateActivityUsageStats(this, 2);
        return;
      }
      switch (i) {
        case 6:
          break;
        case 7:
          this.mAtmService.updateActivityUsageStats(this, 23);
          return;
        case 8:
          if (this.app != null && (this.mVisible || this.mVisibleRequested)) {
            this.mAtmService.updateBatteryStats(this, false);
          }
          this.mAtmService.updateActivityUsageStats(this, 24);
          break;
        case 9:
          break;
        default:
          return;
      }
      WindowProcessController windowProcessController2 = this.app;
      if (windowProcessController2 == null || windowProcessController2.hasActivities()) {
        return;
      }
      this.app.updateProcessInfo(true, false, true, false);
      return;
    }
    this.mAtmService.updateBatteryStats(this, true);
    this.mAtmService.updateActivityUsageStats(this, 1);
    WindowProcessController windowProcessController3 = this.app;
    if (windowProcessController3 != null) {
      windowProcessController3.updateProcessInfo(false, true, true, true);
    }
    ContentCaptureManagerInternal contentCaptureManagerInternal =
        (ContentCaptureManagerInternal)
            LocalServices.getService(ContentCaptureManagerInternal.class);
    if (contentCaptureManagerInternal != null) {
      contentCaptureManagerInternal.notifyActivityEvent(
          this.mUserId,
          this.mActivityComponent,
          10000,
          new ActivityId(getTask() != null ? getTask().mTaskId : -1, this.shareableActivityToken));
    }
  }

  public State getState() {
    return this.mState;
  }

  public boolean isState(State state) {
    return state == this.mState;
  }

  public boolean isState(State state, State state2) {
    State state3 = this.mState;
    return state == state3 || state2 == state3;
  }

  public boolean isState(State state, State state2, State state3, State state4) {
    State state5 = this.mState;
    return state == state5 || state2 == state5 || state3 == state5 || state4 == state5;
  }

  public boolean isState(State state, State state2, State state3, State state4, State state5) {
    State state6 = this.mState;
    return state == state6
        || state2 == state6
        || state3 == state6
        || state4 == state6
        || state5 == state6;
  }

  public void destroySurfaces() {
    destroySurfaces(false);
  }

  public final void destroySurfaces(boolean z) {
    ArrayList arrayList = new ArrayList(this.mChildren);
    boolean z2 = false;
    for (int size = arrayList.size() - 1; size >= 0; size--) {
      z2 |= ((WindowState) arrayList.get(size)).destroySurface(z, this.mAppStopped);
    }
    if (z2) {
      getDisplayContent().assignWindowLayers(true);
      updateLetterboxSurface(null);
    }
  }

  public void notifyAppResumed() {
    if (getParent() == null) {
      Slog.w(
          StartingSurfaceController.TAG,
          "Attempted to notify resumed of non-existing app token: " + this.token);
      return;
    }
    boolean z = this.mAppStopped;
    if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
          1364498663,
          3,
          (String) null,
          new Object[] {Boolean.valueOf(z), String.valueOf(this)});
    }
    this.mAppStopped = false;
    if (this.mAtmService.getActivityStartController().isInExecution()) {
      setCurrentLaunchCanTurnScreenOn(true);
    }
    if (z) {
      return;
    }
    destroySurfaces(true);
  }

  public void notifyUnknownVisibilityLaunchedForKeyguardTransition() {
    if (this.noDisplay || !isKeyguardLocked()) {
      return;
    }
    this.mDisplayContent.mUnknownAppVisibilityController.notifyLaunched(this);
  }

  public final boolean shouldBeVisible(boolean z, boolean z2) {
    updateVisibilityIgnoringKeyguard(z);
    if (z2) {
      return this.visibleIgnoringKeyguard;
    }
    return shouldBeVisibleUnchecked();
  }

  public boolean shouldBeVisibleUnchecked() {
    Task rootTask = getRootTask();
    if (rootTask == null || !this.visibleIgnoringKeyguard) {
      return false;
    }
    if ((inPinnedWindowingMode() && rootTask.isForceHidden())
        || hasOverlayOverUntrustedModeEmbedded()) {
      return false;
    }
    if (this.mDisplayContent.isSleeping()) {
      int displayId = getDisplayId();
      if (displayId == 2 && this.mAtmService.mDexController.getDexModeLocked() == 2) {
        return false;
      }
      if (this.mAtmService.mKeyguardController.isWakeAndUnlock(displayId)
          && getDisplayContent() != null
          && getDisplayContent().isOnTop()) {
        return true;
      }
      return canTurnScreenOn();
    }
    return this.mTaskSupervisor.getKeyguardController().checkKeyguardVisibility(this);
  }

  public boolean hasOverlayOverUntrustedModeEmbedded() {
    return (!isEmbeddedInUntrustedMode()
            || getTask() == null
            || getTask()
                    .getActivity(
                        new Predicate() { // from class:
                                          // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda4
                          @Override // java.util.function.Predicate
                          public final boolean test(Object obj) {
                            boolean lambda$hasOverlayOverUntrustedModeEmbedded$11;
                            lambda$hasOverlayOverUntrustedModeEmbedded$11 =
                                ActivityRecord.this.lambda$hasOverlayOverUntrustedModeEmbedded$11(
                                    (ActivityRecord) obj);
                            return lambda$hasOverlayOverUntrustedModeEmbedded$11;
                          }
                        },
                        this,
                        false,
                        false)
                == null)
        ? false
        : true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$hasOverlayOverUntrustedModeEmbedded$11(
      ActivityRecord activityRecord) {
    return (activityRecord.finishing || activityRecord.getUid() == getUid()) ? false : true;
  }

  public void updateVisibilityIgnoringKeyguard(boolean z) {
    this.visibleIgnoringKeyguard = (!z || this.mLaunchTaskBehind) && showToCurrentUser();
  }

  public boolean shouldBeVisible() {
    Task task = getTask();
    if (task == null) {
      return false;
    }
    return shouldBeVisible(
        (task.shouldBeVisible(null) && task.getOccludingActivityAbove(this) == null) ? false : true,
        false);
  }

  public void makeVisibleIfNeeded(ActivityRecord activityRecord, boolean z) {
    if ((this.mState == State.RESUMED && this.mVisibleRequested) || this == activityRecord) {
      return;
    }
    Task rootTask = getRootTask();
    try {
      if (rootTask.mTranslucentActivityWaiting != null) {
        updateOptionsLocked(this.returningOptions);
        rootTask.mUndrawnActivitiesBelowTopTranslucent.add(this);
      }
      setVisibility(true);
      this.app.postPendingUiCleanMsg(true);
      if (z) {
        this.mClientVisibilityDeferred = false;
        makeActiveIfNeeded(activityRecord);
      } else {
        this.mClientVisibilityDeferred = true;
      }
      this.mTaskSupervisor.mStoppingActivities.remove(this);
    } catch (Exception e) {
      Slog.w(
          "ActivityTaskManager",
          "Exception thrown making visible: " + this.intent.getComponent(),
          e);
    }
    handleAlreadyVisible();
  }

  public void makeInvisible() {
    if (this.mVisibleRequested) {
      try {
        boolean checkEnterPictureInPictureState =
            checkEnterPictureInPictureState("makeInvisible", true);
        setDeferHidingClient(
            checkEnterPictureInPictureState
                && !isState(State.STARTED, State.STOPPING, State.STOPPED, State.PAUSED));
        setVisibility(false);
        switch (AbstractC28617.$SwitchMap$com$android$server$wm$ActivityRecord$State[
            getState().ordinal()]) {
          case 1:
          case 2:
          case 3:
          case 4:
          case 6:
            addToStopping(true, checkEnterPictureInPictureState, "makeInvisible");
            break;
          case 5:
          case 7:
            this.supportsEnterPipOnTaskSwitch = false;
            break;
        }
      } catch (Exception e) {
        Slog.w(
            "ActivityTaskManager",
            "Exception thrown making hidden: " + this.intent.getComponent(),
            e);
      }
    }
  }

  public boolean makeActiveIfNeeded(ActivityRecord activityRecord) {
    if (shouldResumeActivity(activityRecord)) {
      return getRootTask().resumeTopActivityUncheckedLocked(activityRecord, null);
    }
    if (shouldPauseActivity(activityRecord)) {
      setState(State.PAUSING, "makeActiveIfNeeded");
      EventLogTags.writeWmPauseActivity(
          this.mUserId,
          System.identityHashCode(this),
          this.shortComponentName,
          "userLeaving=false",
          "make-active");
      try {
        this.mAtmService
            .getLifecycleManager()
            .scheduleTransaction(
                this.app.getThread(),
                this.token,
                (ActivityLifecycleItem)
                    PauseActivityItem.obtain(
                        this.finishing,
                        false,
                        this.configChangeFlags,
                        false,
                        this.mAutoEnteringPip));
      } catch (Exception e) {
        Slog.w(
            "ActivityTaskManager",
            "Exception thrown sending pause: " + this.intent.getComponent(),
            e);
      }
    } else if (shouldStartActivity()) {
      setState(State.STARTED, "makeActiveIfNeeded");
      try {
        this.mAtmService
            .getLifecycleManager()
            .scheduleTransaction(
                this.app.getThread(),
                this.token,
                (ActivityLifecycleItem) StartActivityItem.obtain(takeOptions()));
      } catch (Exception e2) {
        Slog.w(
            "ActivityTaskManager",
            "Exception thrown sending start: " + this.intent.getComponent(),
            e2);
      }
      this.mTaskSupervisor.mStoppingActivities.remove(this);
    }
    return false;
  }

  public boolean shouldPauseActivity(ActivityRecord activityRecord) {
    return ((getTask() != null && getTask().isFreeformStashed() && shouldMakeActive(activityRecord))
            || !shouldMakeActive(activityRecord)
            || isFocusable()
            || isState(State.PAUSING, State.PAUSED)
            || this.results != null)
        ? false
        : true;
  }

  public boolean shouldResumeActivity(ActivityRecord activityRecord) {
    return shouldBeResumed(activityRecord) && !isState(State.RESUMED);
  }

  public final boolean shouldBeResumed(ActivityRecord activityRecord) {
    return (getTask() == null || !getTask().isFreeformStashed())
        ? shouldMakeActive(activityRecord)
            && isFocusable()
            && getTaskFragment().getVisibility(activityRecord) == 0
            && canResumeByCompat()
        : shouldMakeActive(activityRecord)
            && getTaskFragment().getVisibility(activityRecord) == 0
            && canResumeByCompat();
  }

  public final boolean shouldStartActivity() {
    return this.mVisibleRequested && (isState(State.STOPPED) || isState(State.STOPPING));
  }

  public boolean shouldMakeActive(ActivityRecord activityRecord) {
    if (!isState(State.STARTED, State.RESUMED, State.PAUSED, State.STOPPED, State.STOPPING)
        || getRootTask().mTranslucentActivityWaiting != null
        || this == activityRecord
        || !this.mTaskSupervisor.readyToResume()
        || this.mLaunchTaskBehind) {
      return false;
    }
    if (this.task.hasChild(this)) {
      return getTaskFragment().topRunningActivity() == this;
    }
    throw new IllegalStateException("Activity not found in its task");
  }

  public void handleAlreadyVisible() {
    stopFreezingScreenLocked(false);
    try {
      if (this.returningOptions != null) {
        this.app
            .getThread()
            .scheduleOnNewActivityOptions(this.token, this.returningOptions.toBundle());
      }
    } catch (RemoteException unused) {
    }
  }

  public static void activityResumedLocked(IBinder iBinder, boolean z) {
    ActivityRecord forTokenLocked = forTokenLocked(iBinder);
    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_STATES,
          1364126018,
          0,
          (String) null,
          new Object[] {String.valueOf(forTokenLocked)});
    }
    if (forTokenLocked == null) {
      return;
    }
    forTokenLocked.setCustomizeSplashScreenExitAnimation(z);
    forTokenLocked.setSavedState(null);
    if (forTokenLocked.findMainWindow(false) == null) {
      forTokenLocked.mDisplayContent.mUnknownAppVisibilityController.appRemovedOrHidden(
          forTokenLocked);
    }
    forTokenLocked.mDisplayContent.handleActivitySizeCompatModeIfNeeded(forTokenLocked);
    forTokenLocked.mDisplayContent.mUnknownAppVisibilityController.notifyAppResumedFinished(
        forTokenLocked);
  }

  public static void activityRefreshedLocked(IBinder iBinder) {
    DisplayRotationCompatPolicy displayRotationCompatPolicy;
    ActivityRecord forTokenLocked = forTokenLocked(iBinder);
    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_STATES,
          -1480918485,
          0,
          (String) null,
          new Object[] {String.valueOf(forTokenLocked)});
    }
    if (forTokenLocked == null
        || (displayRotationCompatPolicy =
                forTokenLocked.mDisplayContent.mDisplayRotationCompatPolicy)
            == null) {
      return;
    }
    displayRotationCompatPolicy.lambda$onActivityConfigurationChanging$0(forTokenLocked);
  }

  public static void splashScreenAttachedLocked(IBinder iBinder) {
    ActivityRecord forTokenLocked = forTokenLocked(iBinder);
    if (forTokenLocked == null) {
      Slog.w("ActivityTaskManager", "splashScreenTransferredLocked cannot find activity");
    } else {
      forTokenLocked.onSplashScreenAttachComplete();
    }
  }

  public void completeResumeLocked() {
    boolean z = this.mVisibleRequested;
    setVisibility(true);
    if (!z) {
      this.mTaskSupervisor.mAppVisibilitiesChangedSinceLastPause = true;
    }
    this.idle = false;
    this.results = null;
    ArrayList arrayList = this.newIntents;
    if (arrayList != null && arrayList.size() > 0) {
      ArrayList arrayList2 = this.newIntents;
      this.mLastNewIntent = (Intent) arrayList2.get(arrayList2.size() - 1);
    }
    this.newIntents = null;
    if (isActivityTypeHome()) {
      this.mTaskSupervisor.updateHomeProcess(this.task.getBottomMostActivity().app);
    }
    if (this.nowVisible) {
      this.mTaskSupervisor.stopWaitingForActivityVisible(this);
    }
    this.mTaskSupervisor.scheduleIdleTimeout(this);
    this.mTaskSupervisor.reportResumedActivityLocked(this);
    resumeKeyDispatchingLocked();
    Task rootTask = getRootTask();
    this.mTaskSupervisor.mNoAnimActivities.clear();
    this.returningOptions = null;
    if (canTurnScreenOn()) {
      this.mTaskSupervisor.wakeUp("turnScreenOnFlag::" + this.packageName);
    } else {
      rootTask.checkReadyForSleep();
    }
    this.mAtmService.mExt.resetActivityKeepAliveLocked(this);
    if (CoreRune.FW_SUPPORT_OCCLUDES_PARENT_CHANGE_CALLBACK) {
      WindowManagerServiceExt windowManagerServiceExt = this.mWmService.mExt;
      DisplayContent displayContent = getDisplayContent();
      if (!isActivityTypeHomeOrRecents()) {
        this = null;
      }
      windowManagerServiceExt.updateOccludeTargetIfNeeded(displayContent, this);
    }
  }

  public void activityPaused(boolean z) {
    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STATES,
          1068803972,
          12,
          (String) null,
          new Object[] {String.valueOf(this.token), Boolean.valueOf(z)});
    }
    TaskFragment taskFragment = getTaskFragment();
    if (taskFragment != null) {
      removePauseTimeout();
      ActivityRecord pausingActivity = taskFragment.getPausingActivity();
      if (pausingActivity == this) {
        if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_STATES,
              397382873,
              0,
              (String) null,
              new Object[] {String.valueOf(this), z ? "(due to timeout)" : " (pause complete)"});
        }
        this.mAtmService.deferWindowLayout();
        try {
          taskFragment.completePause(true, null);
          return;
        } finally {
          this.mAtmService.continueWindowLayout();
        }
      }
      EventLogTags.writeWmFailedToPause(
          this.mUserId,
          System.identityHashCode(this),
          this.shortComponentName,
          pausingActivity != null ? pausingActivity.shortComponentName : "(none)");
      if (isState(State.PAUSING)) {
        setState(State.PAUSED, "activityPausedLocked");
        if (this.finishing) {
          if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
            ProtoLogImpl.v(
                ProtoLogGroup.WM_DEBUG_STATES,
                -937498525,
                0,
                (String) null,
                new Object[] {String.valueOf(this)});
          }
          completeFinishing("activityPausedLocked");
        }
      }
    }
    this.mDisplayContent.handleActivitySizeCompatModeIfNeeded(this);
    this.mRootWindowContainer.ensureActivitiesVisible(null, 0, false);
  }

  public void schedulePauseTimeout() {
    this.pauseTime = SystemClock.uptimeMillis();
    this.mAtmService.f1734mH.postDelayed(this.mPauseTimeoutRunnable, 500L);
    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
      ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STATES, -705939410, 0, (String) null, (Object[]) null);
    }
  }

  public final void removePauseTimeout() {
    this.mAtmService.f1734mH.removeCallbacks(this.mPauseTimeoutRunnable);
  }

  public final void removeDestroyTimeout() {
    this.mAtmService.f1734mH.removeCallbacks(this.mDestroyTimeoutRunnable);
  }

  public final void removeStopTimeout() {
    this.mAtmService.f1734mH.removeCallbacks(this.mStopTimeoutRunnable);
  }

  public void removeTimeouts() {
    this.mTaskSupervisor.removeIdleTimeoutForActivity(this);
    removePauseTimeout();
    removeStopTimeout();
    removeDestroyTimeout();
    finishLaunchTickingLocked();
  }

  public void stopIfPossible() {
    Task rootTask = getRootTask();
    if (isNoHistory() && !this.finishing) {
      if (!rootTask.shouldSleepActivities() && (!CoreRune.SYSFW_APP_PREL || !this.mIsPrelMode)) {
        if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
          ProtoLogImpl.d(
              ProtoLogGroup.WM_DEBUG_STATES,
              -1136139407,
              0,
              (String) null,
              new Object[] {String.valueOf(this)});
        }
        if (finishIfPossible("stop-no-history", false) != 0) {
          resumeKeyDispatchingLocked();
          return;
        }
      } else if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_STATES,
            485170982,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
    }
    if (attachedToProcess()) {
      resumeKeyDispatchingLocked();
      try {
        if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_STATES,
              189628502,
              0,
              (String) null,
              new Object[] {String.valueOf(this)});
        }
        setState(State.STOPPING, "stopIfPossible");
        EventLogTags.writeWmStopActivity(
            this.mUserId, System.identityHashCode(this), this.shortComponentName);
        this.mAtmService
            .getLifecycleManager()
            .scheduleTransaction(
                this.app.getThread(),
                this.token,
                (ActivityLifecycleItem) StopActivityItem.obtain(this.configChangeFlags));
        this.mAtmService.f1734mH.postDelayed(this.mStopTimeoutRunnable, 11000L);
      } catch (Exception e) {
        Slog.w("ActivityTaskManager", "Exception thrown during pause", e);
        this.mAppStopped = true;
        if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_STATES,
              306524472,
              0,
              (String) null,
              new Object[] {String.valueOf(this)});
        }
        setState(State.STOPPED, "stopIfPossible");
        if (this.deferRelaunchUntilPaused) {
          destroyImmediately("stop-except");
        }
      }
    }
  }

  public void activityStopped(
      Bundle bundle, PersistableBundle persistableBundle, CharSequence charSequence) {
    AppPrelaunchManagerService appPrelaunchManagerService;
    if (this.mDeferHidingClient) {
      Slog.w("ActivityTaskManager", "activityStopped: clear defer client, " + this);
      setDeferHidingClient(false);
    }
    removeStopTimeout();
    State state = this.mState;
    boolean z = state == State.STOPPING;
    if (!z && state != State.RESTARTING_PROCESS) {
      Slog.i(
          "ActivityTaskManager",
          "Activity reported stop, but no longer stopping: " + this + " " + this.mState);
      return;
    }
    if (persistableBundle != null) {
      this.mPersistentState = persistableBundle;
      this.mAtmService.notifyTaskPersisterLocked(this.task, false);
    }
    if (bundle != null) {
      setSavedState(bundle);
      this.launchCount = 0;
      updateTaskDescription(charSequence);
    }
    if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_STATES,
          -172326720,
          0,
          (String) null,
          new Object[] {String.valueOf(this), String.valueOf(this.mIcicle)});
    }
    if (z) {
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STATES,
            -1305791032,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      setState(State.STOPPED, "activityStopped");
    }
    this.mAppStopped = true;
    this.firstWindowDrawn = false;
    Task task = this.task;
    if (task.mLastRecentsAnimationTransaction != null) {
      task.clearLastRecentsAnimationTransaction(true);
    }
    this.mDisplayContent.mPinnedTaskController.onActivityHidden(this.mActivityComponent);
    this.mDisplayContent.mUnknownAppVisibilityController.appRemovedOrHidden(this);
    if (isClientVisible()) {
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX
          && isVisible()
          && !this.mTransitionController.inTransition(this)) {
        Slog.d(
            "ActivityTaskManager", "activityStopped, make sure activity is invisible, r=" + this);
        commitVisibility(false, true);
      } else {
        setClientVisible(false);
      }
    }
    destroySurfaces();
    removeStartingWindow();
    if (this.finishing) {
      abortAndClearOptionsAnimation();
    } else if (this.deferRelaunchUntilPaused) {
      destroyImmediately("stop-config");
      this.mRootWindowContainer.resumeFocusedTasksTopActivities();
    } else {
      this.mAtmService.updatePreviousProcess(this);
    }
    this.mTaskSupervisor.checkReadyForSleepLocked(true);
    if (CoreRune.SYSFW_APP_PREL
        && this.mShouldPrelNotify
        && (appPrelaunchManagerService = this.mAtmService.mAps) != null) {
      WindowProcessController windowProcessController = this.app;
      if (windowProcessController != null) {
        windowProcessController.mIsPrelScheduleGroupOverride = false;
      }
      this.mShouldPrelNotify = false;
      appPrelaunchManagerService.setTaskProcessedForPrelaunchedAppAsync(getUid());
    }
  }

  public void addToStopping(boolean z, boolean z2, String str) {
    if (!this.mTaskSupervisor.mStoppingActivities.contains(this)) {
      EventLogTags.writeWmAddToStopping(
          this.mUserId, System.identityHashCode(this), this.shortComponentName, str);
      this.mTaskSupervisor.mStoppingActivities.add(this);
    }
    Task rootTask = getRootTask();
    boolean z3 = true;
    if (this.mTaskSupervisor.mStoppingActivities.size() <= 3
        && (!isRootOfTask() || rootTask.getChildCount() > 1)) {
      z3 = false;
    }
    if (z || z3) {
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STATES,
            1126328412,
            15,
            (String) null,
            new Object[] {Boolean.valueOf(z3), Boolean.valueOf(!z2)});
      }
      if (!z2) {
        this.mTaskSupervisor.scheduleIdle();
        return;
      } else {
        this.mTaskSupervisor.scheduleIdleTimeout(this);
        return;
      }
    }
    rootTask.checkReadyForSleep();
  }

  public void startLaunchTickingLocked() {
    if (!Build.IS_USER && this.launchTickTime == 0) {
      this.launchTickTime = SystemClock.uptimeMillis();
      continueLaunchTicking();
    }
  }

  public final boolean continueLaunchTicking() {
    Task rootTask;
    if (this.launchTickTime == 0 || (rootTask = getRootTask()) == null) {
      return false;
    }
    rootTask.removeLaunchTickMessages();
    this.mAtmService.f1734mH.postDelayed(this.mLaunchTickRunnable, 500L);
    return true;
  }

  public void removeLaunchTickRunnable() {
    this.mAtmService.f1734mH.removeCallbacks(this.mLaunchTickRunnable);
  }

  public void finishLaunchTickingLocked() {
    this.launchTickTime = 0L;
    Task rootTask = getRootTask();
    if (rootTask == null) {
      return;
    }
    rootTask.removeLaunchTickMessages();
  }

  public boolean mayFreezeScreenLocked() {
    return mayFreezeScreenLocked(this.app);
  }

  public final boolean mayFreezeScreenLocked(WindowProcessController windowProcessController) {
    return (!hasProcess()
            || windowProcessController.isCrashing()
            || windowProcessController.isNotResponding())
        ? false
        : true;
  }

  public void startFreezingScreenLocked(int i) {
    startFreezingScreenLocked(this.app, i);
  }

  public void startFreezingScreenLocked(WindowProcessController windowProcessController, int i) {
    if (mayFreezeScreenLocked(windowProcessController)) {
      if (getParent() == null) {
        Slog.w(
            StartingSurfaceController.TAG,
            "Attempted to freeze screen with non-existing app token: " + this.token);
        return;
      }
      if (((-536870913) & i) == 0 && okToDisplay()) {
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_ORIENTATION,
              1836306327,
              0,
              (String) null,
              new Object[] {String.valueOf(this.token)});
          return;
        }
        return;
      }
      startFreezingScreen();
    }
  }

  public void startFreezingScreen() {
    startFreezingScreen(-1);
  }

  public void startFreezingScreen(int i) {
    if (this.mTransitionController.isShellTransitionsEnabled()) {
      return;
    }
    if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_ORIENTATION,
          1746778201,
          252,
          (String) null,
          new Object[] {
            String.valueOf(this.token),
            Boolean.valueOf(isVisible()),
            Boolean.valueOf(this.mFreezingScreen),
            Boolean.valueOf(this.mVisibleRequested),
            String.valueOf(new RuntimeException().fillInStackTrace())
          });
    }
    if (this.mVisibleRequested) {
      boolean z = i != -1;
      if (!this.mFreezingScreen) {
        this.mFreezingScreen = true;
        this.mWmService.registerAppFreezeListener(this);
        WindowManagerService windowManagerService = this.mWmService;
        int i2 = windowManagerService.mAppsFreezingScreen + 1;
        windowManagerService.mAppsFreezingScreen = i2;
        if (i2 == 1) {
          if (z) {
            this.mDisplayContent.getDisplayRotation().cancelSeamlessRotation();
          }
          this.mWmService.startFreezingDisplay(0, 0, this.mDisplayContent, i);
          this.mWmService.f1749mH.removeMessages(17);
          this.mWmService.f1749mH.sendEmptyMessageDelayed(17, 2000L);
        }
      }
      if (z) {
        return;
      }
      int size = this.mChildren.size();
      for (int i3 = 0; i3 < size; i3++) {
        ((WindowState) this.mChildren.get(i3)).onStartFreezingScreen();
      }
    }
  }

  public boolean isFreezingScreen() {
    return this.mFreezingScreen;
  }

  @Override // com.android.server.wm.WindowManagerService.AppFreezeListener
  public void onAppFreezeTimeout() {
    Slog.w(StartingSurfaceController.TAG, "Force clearing freeze: " + this);
    stopFreezingScreen(true, true);
  }

  public void stopFreezingScreenLocked(boolean z) {
    if (z || this.frozenBeforeDestroy) {
      this.frozenBeforeDestroy = false;
      if (getParent() == null) {
        return;
      }
      if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_ORIENTATION,
            466506262,
            60,
            (String) null,
            new Object[] {
              String.valueOf(this.token),
              Boolean.valueOf(isVisible()),
              Boolean.valueOf(isFreezingScreen())
            });
      }
      stopFreezingScreen(true, z);
    }
  }

  public void stopFreezingScreen(boolean z, boolean z2) {
    if (this.mFreezingScreen) {
      if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_ORIENTATION,
            539077569,
            12,
            (String) null,
            new Object[] {String.valueOf(this), Boolean.valueOf(z2)});
      }
      int size = this.mChildren.size();
      boolean z3 = false;
      for (int i = 0; i < size; i++) {
        z3 |= ((WindowState) this.mChildren.get(i)).onStopFreezingScreen();
      }
      if (z2 || z3) {
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_ORIENTATION,
              -251259736,
              0,
              (String) null,
              new Object[] {String.valueOf(this)});
        }
        this.mFreezingScreen = false;
        this.mWmService.unregisterAppFreezeListener(this);
        r8.mAppsFreezingScreen--;
        this.mWmService.mLastFinishedFreezeSource = this;
      }
      if (z) {
        if (z3) {
          this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
        }
        this.mWmService.stopFreezingDisplayLocked();
      }
    }
  }

  public void onFirstWindowDrawn(WindowState windowState) {
    ActivityRecord activityRecord;
    this.firstWindowDrawn = true;
    this.mSplashScreenStyleSolidColor = true;
    if (windowState.mAttrs.type != 1) {
      updateReportedVisibilityLocked();
      return;
    }
    if (this.mStartingWindow != null) {
      if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
            1677260366,
            0,
            "Finish starting %s: first real window is shown, no animation",
            new Object[] {String.valueOf(windowState.mToken)});
      }
      windowState.cancelAnimation();
    }
    Task task = this.task;
    if (task.mSharedStartingData == null) {
      task = null;
    }
    if (task == null) {
      removeStartingWindow();
    } else if (task.getActivity(
                new Predicate() { // from class:
                                  // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda32
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean lambda$onFirstWindowDrawn$13;
                    lambda$onFirstWindowDrawn$13 =
                        ActivityRecord.lambda$onFirstWindowDrawn$13((ActivityRecord) obj);
                    return lambda$onFirstWindowDrawn$13;
                  }
                })
            == null
        && (activityRecord = task.topActivityContainsStartingWindow()) != null) {
      activityRecord.removeStartingWindow();
    }
    updateReportedVisibilityLocked();
  }

  public static /* synthetic */ boolean lambda$onFirstWindowDrawn$13(
      ActivityRecord activityRecord) {
    return activityRecord.isVisibleRequested() && !activityRecord.firstWindowDrawn;
  }

  public final boolean setTaskHasBeenVisible() {
    if (this.task.getHasBeenVisible()) {
      return false;
    }
    if (inTransition()) {
      this.task.setDeferTaskAppear(true);
    }
    this.task.setHasBeenVisible(true);
    return true;
  }

  public void onStartingWindowDrawn() {
    boolean z;
    if (this.task != null) {
      this.mSplashScreenStyleSolidColor = true;
      z = !setTaskHasBeenVisible();
    } else {
      z = false;
    }
    if (z
        || this.mStartingData == null
        || this.finishing
        || this.mLaunchedFromBubble
        || !this.mVisibleRequested
        || this.mDisplayContent.mAppTransition.isReady()
        || this.mDisplayContent.mAppTransition.isRunning()
        || !this.mDisplayContent.isNextTransitionForward()) {
      return;
    }
    this.mStartingData.mIsTransitionForward = true;
    if (this != this.mDisplayContent.getLastOrientationSource()) {
      this.mDisplayContent.updateOrientation();
    }
    this.mDisplayContent.executeAppTransition();
  }

  public final void onWindowsDrawn() {
    ActivityMetricsLogger.TransitionInfoSnapshot notifyWindowsDrawn =
        this.mTaskSupervisor.getActivityMetricsLogger().notifyWindowsDrawn(this);
    boolean z = notifyWindowsDrawn != null;
    int i = z ? notifyWindowsDrawn.windowsDrawnDelayMs : -1;
    int launchState = z ? notifyWindowsDrawn.getLaunchState() : 0;
    if (SemGateConfig.isGateEnabled()) {
      Log.i("GATE", "<GATE-M>APP_FULLY_LOADED_" + this.packageName + "</GATE-M>");
      if ("com.android.vending/.AssetBrowserActivity".equals(this.shortComponentName)
          || "com.android.vending/com.google.android.finsky.activities.TosActivity"
              .equals(this.shortComponentName)) {
        Log.i("GATE", "<GATE-M> MARKET_LAUNCHED </GATE-M>");
      } else {
        Log.i("GATE", "<GATE-M> APP_OPENED </GATE-M>");
      }
    }
    if (z || this == getDisplayArea().topRunningActivity()) {
      this.mTaskSupervisor.reportActivityLaunched(false, this, i, launchState);
    }
    finishLaunchTickingLocked();
    if (this.task != null) {
      setTaskHasBeenVisible();
    }
    this.mLaunchRootTask = null;
  }

  public void onWindowsVisible() {
    this.mTaskSupervisor.stopWaitingForActivityVisible(this);
    if (!this.nowVisible) {
      this.nowVisible = true;
      this.lastVisibleTime = SystemClock.uptimeMillis();
      this.mAtmService.scheduleAppGcsLocked();
      this.mTaskSupervisor.scheduleProcessStoppingAndFinishingActivitiesIfNeeded();
      if (this.mImeInsetsFrozenUntilStartInput
          && getWindow(
                  new Predicate() { // from class:
                                    // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                      boolean lambda$onWindowsVisible$14;
                      lambda$onWindowsVisible$14 =
                          ActivityRecord.lambda$onWindowsVisible$14((WindowState) obj);
                      return lambda$onWindowsVisible$14;
                    }
                  })
              == null) {
        this.mImeInsetsFrozenUntilStartInput = false;
      }
    }
    ActivityManagerPerformance activityManagerPerformance = this.mAtmService.mAMBooster;
    if (activityManagerPerformance != null) {
      activityManagerPerformance.onActivityVisibleLocked(this);
    }
  }

  public static /* synthetic */ boolean lambda$onWindowsVisible$14(WindowState windowState) {
    return WindowManager.LayoutParams.mayUseInputMethod(windowState.mAttrs.flags);
  }

  public void onWindowsGone() {
    this.nowVisible = false;
  }

  @Override // com.android.server.wm.WindowContainer
  public void checkAppWindowsReadyToShow() {
    boolean z = this.allDrawn;
    if (z == this.mLastAllDrawn) {
      return;
    }
    this.mLastAllDrawn = z;
    if (z) {
      if (this.mFreezingScreen) {
        showAllWindowsLocked();
        stopFreezingScreen(false, true);
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
          ProtoLogImpl.i(
              ProtoLogGroup.WM_DEBUG_ORIENTATION,
              806891543,
              20,
              (String) null,
              new Object[] {
                String.valueOf(this),
                Long.valueOf(this.mNumInterestingWindows),
                Long.valueOf(this.mNumDrawnWindows)
              });
        }
        setAppLayoutChanges(4, "checkAppWindowsReadyToShow: freezingScreen");
        return;
      }
      setAppLayoutChanges(8, "checkAppWindowsReadyToShow");
      if (getDisplayContent().mOpeningApps.contains(this) || !canShowWindows()) {
        return;
      }
      showAllWindowsLocked();
    }
  }

  public void showAllWindowsLocked() {
    forAllWindows(
        new Consumer() { // from class:
                         // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda3
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((WindowState) obj).performShowLocked();
          }
        },
        false);
  }

  public void updateReportedVisibilityLocked() {
    int size = this.mChildren.size();
    this.mReportedVisibilityResults.reset();
    boolean z = false;
    for (int i = 0; i < size; i++) {
      ((WindowState) this.mChildren.get(i))
          .updateReportedVisibility(this.mReportedVisibilityResults);
    }
    WindowState.UpdateReportedVisibilityResults updateReportedVisibilityResults =
        this.mReportedVisibilityResults;
    int i2 = updateReportedVisibilityResults.numInteresting;
    int i3 = updateReportedVisibilityResults.numVisible;
    int i4 = updateReportedVisibilityResults.numDrawn;
    boolean z2 = updateReportedVisibilityResults.nowGone;
    boolean z3 = i2 > 0 && i4 >= i2;
    if (i2 > 0 && i3 >= i2 && isVisible()) {
      z = true;
    }
    if (!z2) {
      if (!z3) {
        z3 = this.mReportedDrawn;
      }
      if (!z) {
        z = this.reportedVisible;
      }
    }
    if (z3 != this.mReportedDrawn) {
      if (z3) {
        onWindowsDrawn();
      }
      this.mReportedDrawn = z3;
    }
    if (z != this.reportedVisible) {
      this.reportedVisible = z;
      if (z) {
        onWindowsVisible();
      } else {
        onWindowsGone();
      }
    }
  }

  public boolean isReportedDrawn() {
    return this.mReportedDrawn;
  }

  @Override // com.android.server.wm.WindowToken
  public void setClientVisible(boolean z) {
    if (z || !this.mDeferHidingClient) {
      super.setClientVisible(z);
    }
  }

  public boolean updateDrawnWindowStates(WindowState windowState) {
    windowState.setDrawnStateEvaluated(true);
    if (this.allDrawn && !this.mFreezingScreen) {
      return false;
    }
    long j = this.mLastTransactionSequence;
    int i = this.mWmService.mTransactionSequence;
    if (j != i) {
      this.mLastTransactionSequence = i;
      this.mNumDrawnWindows = 0;
      this.mNumInterestingWindows = findMainWindow(false) != null ? 1 : 0;
    }
    WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
    if (!this.allDrawn && windowState.mightAffectAllDrawn()) {
      if (ProtoLogGroup.WM_DEBUG_ORIENTATION.isLogToLogcat()) {
        boolean isAnimating = isAnimating(3, 1);
        Slog.v(
            "ActivityTaskManager",
            "Eval win "
                + windowState
                + ": isDrawn="
                + windowState.isDrawn()
                + ", isAnimationSet="
                + isAnimating);
        if (!windowState.isDrawn()) {
          Slog.v(
              "ActivityTaskManager",
              "Not displayed: s="
                  + windowStateAnimator.mSurfaceController
                  + " pv="
                  + windowState.isVisibleByPolicy()
                  + " mDrawState="
                  + windowStateAnimator.drawStateToString()
                  + " ph="
                  + windowState.isParentWindowHidden()
                  + " th="
                  + this.mVisibleRequested
                  + " a="
                  + isAnimating);
        }
      }
      WindowState windowState2 = this.mStartingWindow;
      if (windowState != windowState2) {
        if (windowState2 == null && windowState.mAttrs.type == 3) {
          Slog.d(
              "ActivityTaskManager",
              "updateDrawnWindowStates: this=" + this + " not interested in w=" + windowState);
        } else if (windowState.isInteresting()) {
          if (findMainWindow(false) != windowState) {
            this.mNumInterestingWindows++;
          }
          if (windowState.isDrawn()) {
            this.mNumDrawnWindows++;
            if (!ProtoLogGroup.WM_DEBUG_ORIENTATION.isLogToLogcat()) {
              return true;
            }
            Slog.v(
                "ActivityTaskManager",
                "tokenMayBeDrawn: "
                    + this
                    + " w="
                    + windowState
                    + " numInteresting="
                    + this.mNumInterestingWindows
                    + " freezingScreen="
                    + this.mFreezingScreen
                    + " mAppFreezing="
                    + windowState.mAppFreezing);
            return true;
          }
        }
      } else if (this.mStartingData != null && windowState.isDrawn()) {
        this.mStartingData.mIsDisplayed = true;
      }
    }
    return false;
  }

  public boolean inputDispatchingTimedOut(TimeoutRecord timeoutRecord, int i) {
    ActivityRecord waitingHistoryRecordLocked;
    WindowProcessController windowProcessController;
    boolean z;
    try {
      Trace.traceBegin(64L, "ActivityRecord#inputDispatchingTimedOut()");
      timeoutRecord.mLatencyTracker.waitingOnGlobalLockStarted();
      WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          timeoutRecord.mLatencyTracker.waitingOnGlobalLockEnded();
          waitingHistoryRecordLocked = getWaitingHistoryRecordLocked();
          windowProcessController = this.app;
          z = hasProcess() && (this.app.getPid() == i || i == -1);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      if (z) {
        return this.mAtmService.mAmInternal.inputDispatchingTimedOut(
            windowProcessController.mOwner,
            waitingHistoryRecordLocked.shortComponentName,
            waitingHistoryRecordLocked.info.applicationInfo,
            this.shortComponentName,
            this.app,
            false,
            timeoutRecord);
      }
      return this.mAtmService.mAmInternal.inputDispatchingTimedOut(i, false, timeoutRecord) <= 0;
    } finally {
      Trace.traceEnd(64L);
    }
  }

  public final ActivityRecord getWaitingHistoryRecordLocked() {
    Task topDisplayFocusedRootTask;
    if (!this.mAppStopped
        || (topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask())
            == null) {
      return this;
    }
    ActivityRecord topResumedActivity = topDisplayFocusedRootTask.getTopResumedActivity();
    if (topResumedActivity == null) {
      topResumedActivity = topDisplayFocusedRootTask.getTopPausingActivity();
    }
    return topResumedActivity != null ? topResumedActivity : this;
  }

  public boolean canBeTopRunning() {
    return !this.finishing && showToCurrentUser();
  }

  public boolean isInterestingToUserLocked() {
    State state;
    return this.mVisibleRequested
        || this.nowVisible
        || (state = this.mState) == State.PAUSING
        || state == State.RESUMED;
  }

  public static int getTaskForActivityLocked(IBinder iBinder, boolean z) {
    ActivityRecord forTokenLocked = forTokenLocked(iBinder);
    if (forTokenLocked == null || forTokenLocked.getParent() == null) {
      return -1;
    }
    return getTaskForActivityLocked(forTokenLocked, z);
  }

  public static int getTaskForActivityLocked(ActivityRecord activityRecord, boolean z) {
    Task task = activityRecord.task;
    if (!z || activityRecord.compareTo((WindowContainer) task.getRootActivity(false, true)) <= 0) {
      return task.mTaskId;
    }
    return -1;
  }

  public static ActivityRecord isInRootTaskLocked(IBinder iBinder) {
    ActivityRecord forTokenLocked = forTokenLocked(iBinder);
    if (forTokenLocked != null) {
      return forTokenLocked.getRootTask().isInTask(forTokenLocked);
    }
    return null;
  }

  public static Task getRootTask(IBinder iBinder) {
    ActivityRecord isInRootTaskLocked = isInRootTaskLocked(iBinder);
    if (isInRootTaskLocked != null) {
      return isInRootTaskLocked.getRootTask();
    }
    return null;
  }

  public static ActivityRecord isInAnyTask(IBinder iBinder) {
    ActivityRecord forTokenLocked = forTokenLocked(iBinder);
    if (forTokenLocked == null || !forTokenLocked.isAttached()) {
      return null;
    }
    return forTokenLocked;
  }

  public int getDisplayId() {
    DisplayContent displayContent;
    Task task = this.task;
    if (task == null || (displayContent = task.mDisplayContent) == null) {
      return -1;
    }
    return displayContent.mDisplayId;
  }

  public final boolean isDestroyable() {
    return (this.finishing
            || !hasProcess()
            || isState(State.RESUMED)
            || getRootTask() == null
            || this == getTaskFragment().getPausingActivity()
            || !this.mHaveState
            || !this.mAppStopped
            || this.mVisibleRequested)
        ? false
        : true;
  }

  public static String createImageFilename(long j, int i) {
    return String.valueOf(i) + "_activity_icon_" + j + ".png";
  }

  public void setTaskDescription(ActivityManager.TaskDescription taskDescription) {
    Bitmap icon;
    if (taskDescription.getIconFilename() == null && (icon = taskDescription.getIcon()) != null) {
      String absolutePath =
          new File(
                  TaskPersister.getUserImagesDir(this.task.mUserId),
                  createImageFilename(this.createTime, this.task.mTaskId))
              .getAbsolutePath();
      this.mAtmService.getRecentTasks().saveImage(icon, absolutePath);
      taskDescription.setIconFilename(absolutePath);
    }
    this.taskDescription = taskDescription;
    getTask().updateTaskDescription();
  }

  public void setLocusId(LocusId locusId) {
    if (Objects.equals(locusId, this.mLocusId)) {
      return;
    }
    this.mLocusId = locusId;
    if (getTask() != null) {
      getTask().dispatchTaskInfoChangedIfNeeded(false);
    }
  }

  public LocusId getLocusId() {
    return this.mLocusId;
  }

  public void reportScreenCaptured() {
    RemoteCallbackList remoteCallbackList = this.mCaptureCallbacks;
    if (remoteCallbackList != null) {
      int beginBroadcast = remoteCallbackList.beginBroadcast();
      for (int i = 0; i < beginBroadcast; i++) {
        try {
          this.mCaptureCallbacks.getBroadcastItem(i).onScreenCaptured();
        } catch (RemoteException unused) {
        }
      }
      this.mCaptureCallbacks.finishBroadcast();
    }
  }

  public void registerCaptureObserver(IScreenCaptureObserver iScreenCaptureObserver) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (this.mCaptureCallbacks == null) {
          this.mCaptureCallbacks = new RemoteCallbackList();
        }
        this.mCaptureCallbacks.register(iScreenCaptureObserver);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void unregisterCaptureObserver(IScreenCaptureObserver iScreenCaptureObserver) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        RemoteCallbackList remoteCallbackList = this.mCaptureCallbacks;
        if (remoteCallbackList != null) {
          remoteCallbackList.unregister(iScreenCaptureObserver);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean isRegisteredForScreenCaptureCallback() {
    RemoteCallbackList remoteCallbackList = this.mCaptureCallbacks;
    return remoteCallbackList != null && remoteCallbackList.getRegisteredCallbackCount() > 0;
  }

  public void setVoiceSessionLocked(IVoiceInteractionSession iVoiceInteractionSession) {
    this.voiceSession = iVoiceInteractionSession;
    this.pendingVoiceInteractionStart = false;
  }

  public void clearVoiceSessionLocked() {
    this.voiceSession = null;
    this.pendingVoiceInteractionStart = false;
  }

  public void showStartingWindow(boolean z) {
    showStartingWindow(null, false, z, false, null);
  }

  public final ActivityRecord searchCandidateLaunchingActivity() {
    ActivityRecord activityBelow = this.task.getActivityBelow(this);
    if (activityBelow == null) {
      activityBelow = this.task.getParent().getActivityBelow(this);
    }
    if (activityBelow == null || activityBelow.isActivityTypeHome()) {
      return null;
    }
    WindowProcessController windowProcessController = this.app;
    if (windowProcessController == null) {
      windowProcessController =
          (WindowProcessController)
              this.mAtmService.mProcessNames.get(this.processName, this.info.applicationInfo.uid);
    }
    WindowProcessController windowProcessController2 = activityBelow.app;
    if (windowProcessController2 == null) {
      windowProcessController2 =
          (WindowProcessController)
              this.mAtmService.mProcessNames.get(
                  activityBelow.processName, activityBelow.info.applicationInfo.uid);
    }
    if (windowProcessController2 == windowProcessController
        || this.mActivityComponent
            .getPackageName()
            .equals(activityBelow.mActivityComponent.getPackageName())) {
      return activityBelow;
    }
    return null;
  }

  public final boolean isIconStylePreferred(int i) {
    AttributeCache.Entry entry;
    return i != 0
        && (entry =
                AttributeCache.instance()
                    .get(
                        this.packageName,
                        i,
                        com.android.internal.R.styleable.Window,
                        this.mWmService.mCurrentUserId))
            != null
        && entry.array.hasValue(61)
        && entry.array.getInt(61, 0) == 1;
  }

  public final boolean shouldUseSolidColorSplashScreen(
      ActivityRecord activityRecord, boolean z, ActivityOptions activityOptions, int i) {
    int i2;
    if (activityRecord == null && !z && this.task.getActivityAbove(this) != null) {
      return true;
    }
    if (activityOptions != null) {
      int splashScreenStyle = activityOptions.getSplashScreenStyle();
      if (splashScreenStyle == 0) {
        return true;
      }
      if (splashScreenStyle == 1
          || isIconStylePreferred(i)
          || (i2 = this.mLaunchSourceType) == 2
          || this.launchedFromUid == 2000) {
        return false;
      }
      if (i2 == 3) {
        return true;
      }
    } else if (isIconStylePreferred(i)) {
      return false;
    }
    if (activityRecord == null) {
      activityRecord = searchCandidateLaunchingActivity();
    }
    if (activityRecord != null && !activityRecord.isActivityTypeHome()) {
      return activityRecord.mSplashScreenStyleSolidColor;
    }
    if (!z) {
      return true;
    }
    int i3 = this.mLaunchSourceType;
    return (i3 == 1 || i3 == 2 || this.launchedFromUid == 2000) ? false : true;
  }

  public final int getSplashscreenTheme(ActivityOptions activityOptions) {
    String splashScreenThemeResName =
        activityOptions != null ? activityOptions.getSplashScreenThemeResName() : null;
    if (splashScreenThemeResName == null || splashScreenThemeResName.isEmpty()) {
      try {
        splashScreenThemeResName =
            this.mAtmService
                .getPackageManager()
                .getSplashScreenTheme(this.packageName, this.mUserId);
      } catch (RemoteException unused) {
      }
    }
    if (splashScreenThemeResName == null || splashScreenThemeResName.isEmpty()) {
      return 0;
    }
    try {
      return this.mAtmService
          .mContext
          .createPackageContext(this.packageName, 0)
          .getResources()
          .getIdentifier(splashScreenThemeResName, null, null);
    } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused2) {
      return 0;
    }
  }

  public void showStartingWindow(
      ActivityRecord activityRecord,
      boolean z,
      boolean z2,
      boolean z3,
      ActivityRecord activityRecord2) {
    showStartingWindow(activityRecord, z, z2, isProcessRunning(), z3, activityRecord2, null);
  }

  public void showStartingWindow(
      ActivityRecord activityRecord,
      boolean z,
      boolean z2,
      boolean z3,
      boolean z4,
      ActivityRecord activityRecord2,
      ActivityOptions activityOptions) {
    showStartingWindow(activityRecord, z, z2, z3, z4, activityRecord2, activityOptions, false);
  }

  public void showStartingWindow(
      ActivityRecord activityRecord,
      boolean z,
      boolean z2,
      boolean z3,
      boolean z4,
      ActivityRecord activityRecord2,
      ActivityOptions activityOptions,
      boolean z5) {
    if (this.mTaskOverlay) {
      return;
    }
    ActivityOptions activityOptions2 =
        activityOptions != null ? activityOptions : this.mPendingOptions;
    if (activityOptions2 == null || activityOptions2.getAnimationType() != 5) {
      int evaluateStartingWindowTheme =
          evaluateStartingWindowTheme(
              activityRecord,
              this.packageName,
              this.theme,
              z4 ? getSplashscreenTheme(activityOptions2) : 0);
      this.mSplashScreenStyleSolidColor =
          shouldUseSolidColorSplashScreen(
              activityRecord2, z4, activityOptions2, evaluateStartingWindowTheme);
      boolean z6 =
          this.mState.ordinal() >= State.STARTED.ordinal()
              && this.mState.ordinal() <= State.STOPPED.ordinal();
      addStartingWindow(
          this.packageName,
          evaluateStartingWindowTheme,
          activityRecord,
          z
              || (!z
                  && !z6
                  && this.task.getActivity(
                          new Predicate() { // from class:
                                            // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda5
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                              boolean lambda$showStartingWindow$16;
                              lambda$showStartingWindow$16 =
                                  ActivityRecord.this.lambda$showStartingWindow$16(
                                      (ActivityRecord) obj);
                              return lambda$showStartingWindow$16;
                            }
                          })
                      == null),
          z2,
          z3,
          allowTaskSnapshot(),
          z6,
          this.mSplashScreenStyleSolidColor,
          this.allDrawn,
          z5 && !allowSplashScreenFromRecents());
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$showStartingWindow$16(ActivityRecord activityRecord) {
    return (activityRecord.finishing || activityRecord == this) ? false : true;
  }

  public final boolean allowSplashScreenFromRecents() {
    int i;
    AttributeCache.Entry entry =
        AttributeCache.instance()
            .get(
                this.packageName,
                this.theme,
                com.android.internal.R.styleable.Window,
                this.mWmService.mCurrentUserId);
    if (entry == null) {
      return false;
    }
    try {
      i = entry.array.getColor(56, 0);
    } catch (RuntimeException e) {
      Log.e("ActivityTaskManager", "allowSplashScreenFromRecents, Exception While get Color" + e);
      i = 0;
    }
    if (CoreRune.SAFE_DEBUG) {
      Slog.d(
          "ActivityTaskManager",
          "allowSplashScreenFromRecents, splashBackgroundColor=" + i + ", r=" + this);
    }
    return i != 0;
  }

  public void cancelInitializing() {
    if (this.mStartingData != null) {
      removeStartingWindowAnimation(false);
    }
    if (this.mDisplayContent.mUnknownAppVisibilityController.allResolved()) {
      return;
    }
    this.mDisplayContent.mUnknownAppVisibilityController.appRemovedOrHidden(this);
  }

  public void postWindowRemoveStartingWindowCleanup() {
    if (this.mChildren.size() == 0 && this.mVisibleSetFromTransferredStartingWindow) {
      setVisible(false);
    }
  }

  public void requestUpdateWallpaperIfNeeded() {
    for (int size = this.mChildren.size() - 1; size >= 0; size--) {
      ((WindowState) this.mChildren.get(size)).requestUpdateWallpaperIfNeeded();
    }
  }

  public WindowState getTopFullscreenOpaqueWindow() {
    for (int size = this.mChildren.size() - 1; size >= 0; size--) {
      WindowState windowState = (WindowState) this.mChildren.get(size);
      if (windowState != null
          && windowState.mAttrs.isFullscreen()
          && !windowState.isFullyTransparent()) {
        return windowState;
      }
    }
    return null;
  }

  public WindowState findMainWindow() {
    return findMainWindow(true);
  }

  public WindowState findMainWindow(boolean z) {
    WindowState windowState = null;
    for (int size = this.mChildren.size() - 1; size >= 0; size--) {
      WindowState windowState2 = (WindowState) this.mChildren.get(size);
      int i = windowState2.mAttrs.type;
      if (i == 1 || (z && i == 3)) {
        if (!windowState2.mAnimatingExit) {
          return windowState2;
        }
        windowState = windowState2;
      }
    }
    return windowState;
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean needsZBoost() {
    return this.mNeedsZBoost || super.needsZBoost();
  }

  @Override // com.android.server.wm.WindowContainer,
            // com.android.server.wm.SurfaceAnimator.Animatable
  public SurfaceControl getAnimationLeashParent() {
    if (inPinnedWindowingMode() || inFreeformWindowingMode()) {
      return getRootTask().getSurfaceControl();
    }
    return super.getAnimationLeashParent();
  }

  public boolean shouldAnimate() {
    Task task = this.task;
    return task == null || task.shouldAnimate();
  }

  public final SurfaceControl createAnimationBoundsLayer(SurfaceControl.Transaction transaction) {
    if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM,
          1720229827,
          0,
          (String) null,
          (Object[]) null);
    }
    SurfaceControl.Builder callsite =
        makeAnimationLeash()
            .setParent(getAnimationLeashParent())
            .setName(getSurfaceControl() + " - animation-bounds")
            .setCallsite("ActivityRecord.createAnimationBoundsLayer");
    if (this.mNeedsLetterboxedAnimation) {
      callsite.setEffectLayer();
    }
    SurfaceControl build = callsite.build();
    transaction.show(build);
    return build;
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public boolean shouldDeferAnimationFinish(Runnable runnable) {
    AnimatingActivityRegistry animatingActivityRegistry = this.mAnimatingActivityRegistry;
    return animatingActivityRegistry != null
        && animatingActivityRegistry.notifyAboutToFinish(this, runnable);
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean isWaitingForTransitionStart() {
    DisplayContent displayContent = getDisplayContent();
    return displayContent != null
        && displayContent.mAppTransition.isTransitionSet()
        && (displayContent.mOpeningApps.contains(this)
            || displayContent.mClosingApps.contains(this)
            || displayContent.mChangingContainers.contains(this));
  }

  public boolean isTransitionForward() {
    StartingData startingData = this.mStartingData;
    return (startingData != null && startingData.mIsTransitionForward)
        || this.mDisplayContent.isNextTransitionForward();
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public void onLeashAnimationStarting(
      SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
    AnimatingActivityRegistry animatingActivityRegistry = this.mAnimatingActivityRegistry;
    if (animatingActivityRegistry != null) {
      animatingActivityRegistry.notifyStarting(this);
    }
    if (this.mNeedsLetterboxedAnimation) {
      updateLetterboxSurface(findMainWindow(), transaction);
      this.mNeedsAnimationBoundsLayer = true;
    }
    if (this.mNeedsAnimationBoundsLayer) {
      ((WindowContainer) this).mTmpRect.setEmpty();
      if (getDisplayContent()
          .mAppTransitionController
          .isTransitWithinTask(getTransit(), this.task)) {
        this.task.getBounds(((WindowContainer) this).mTmpRect);
      } else {
        Task rootTask = getRootTask();
        if (rootTask == null) {
          return;
        } else {
          rootTask.getBounds(((WindowContainer) this).mTmpRect);
        }
      }
      this.mAnimationBoundsLayer = createAnimationBoundsLayer(transaction);
      transaction.setLayer(surfaceControl, 0);
      transaction.setLayer(this.mAnimationBoundsLayer, getLastLayer());
      if (this.mNeedsLetterboxedAnimation) {
        int roundedCornersRadius =
            this.mLetterboxUiController.getRoundedCornersRadius(findMainWindow());
        Rect rect = new Rect();
        getLetterboxInnerBounds(rect);
        transaction
            .setCornerRadius(this.mAnimationBoundsLayer, roundedCornersRadius)
            .setCrop(this.mAnimationBoundsLayer, rect);
      }
      transaction.reparent(surfaceControl, this.mAnimationBoundsLayer);
    }
  }

  @Override // com.android.server.wm.WindowContainer
  public void prepareSurfaces() {
    boolean z = isVisible() || isAnimating(2, 9);
    if (this.mSurfaceControl != null) {
      if (z && !this.mLastSurfaceShowing) {
        getSyncTransaction().show(this.mSurfaceControl);
      } else if (!z && this.mLastSurfaceShowing) {
        getSyncTransaction().hide(this.mSurfaceControl);
      }
      if (z && this.mSyncState == 0) {
        this.mActivityRecordInputSink.applyChangesToSurfaceIfChanged(getPendingTransaction());
      }
    }
    WindowContainerThumbnail windowContainerThumbnail = this.mThumbnail;
    if (windowContainerThumbnail != null) {
      windowContainerThumbnail.setShowing(getPendingTransaction(), z);
    }
    this.mLastSurfaceShowing = z;
    super.prepareSurfaces();
  }

  public boolean isSurfaceShowing() {
    return this.mLastSurfaceShowing;
  }

  public void attachThumbnailAnimation() {
    if (isAnimating(2, 1)) {
      HardwareBuffer appTransitionThumbnailHeader =
          getDisplayContent().mAppTransition.getAppTransitionThumbnailHeader(this.task);
      if (appTransitionThumbnailHeader == null) {
        if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_enabled) {
          ProtoLogImpl.d(
              ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
              1528528509,
              0,
              (String) null,
              new Object[] {String.valueOf(this.task)});
          return;
        }
        return;
      }
      clearThumbnail();
      SurfaceControl.Transaction pendingTransaction =
          getAnimatingContainer().getPendingTransaction();
      WindowContainerThumbnail windowContainerThumbnail =
          new WindowContainerThumbnail(
              pendingTransaction, getAnimatingContainer(), appTransitionThumbnailHeader);
      this.mThumbnail = windowContainerThumbnail;
      windowContainerThumbnail.startAnimation(
          pendingTransaction, loadThumbnailAnimation(appTransitionThumbnailHeader));
    }
  }

  public void attachCrossProfileAppsThumbnailAnimation() {
    Drawable drawable;
    if (isAnimating(2, 1)) {
      clearThumbnail();
      WindowState findMainWindow = findMainWindow();
      if (findMainWindow == null) {
        return;
      }
      Rect relativeFrame = findMainWindow.getRelativeFrame();
      if (this.task.mUserId == this.mWmService.mCurrentUserId) {
        drawable =
            this.mAtmService.getUiContext().getDrawable(R.drawable.grid_selector_background_focus);
      } else {
        drawable = this.mEnterpriseThumbnailDrawable;
      }
      HardwareBuffer createCrossProfileAppsThumbnail =
          getDisplayContent()
              .mAppTransition
              .createCrossProfileAppsThumbnail(drawable, relativeFrame);
      if (createCrossProfileAppsThumbnail == null) {
        return;
      }
      SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
      this.mThumbnail =
          new WindowContainerThumbnail(
              pendingTransaction, getTask(), createCrossProfileAppsThumbnail);
      this.mThumbnail.startAnimation(
          pendingTransaction,
          getDisplayContent()
              .mAppTransition
              .createCrossProfileAppsThumbnailAnimationLocked(relativeFrame),
          new Point(relativeFrame.left, relativeFrame.top));
    }
  }

  public final Animation loadThumbnailAnimation(HardwareBuffer hardwareBuffer) {
    Rect rect;
    Rect rect2;
    DisplayInfo displayInfo = this.mDisplayContent.getDisplayInfo();
    WindowState findMainWindow = findMainWindow();
    if (findMainWindow != null) {
      Rect rect3 =
          findMainWindow
              .getInsetsStateWithVisibilityOverride()
              .calculateInsets(findMainWindow.getFrame(), WindowInsets.Type.systemBars(), false)
              .toRect();
      Rect rect4 = new Rect(findMainWindow.getFrame());
      rect4.inset(rect3);
      rect = rect3;
      rect2 = rect4;
    } else {
      rect = null;
      rect2 = new Rect(0, 0, displayInfo.appWidth, displayInfo.appHeight);
    }
    return getDisplayContent()
        .mAppTransition
        .createThumbnailAspectScaleAnimationLocked(
            rect2,
            rect,
            hardwareBuffer,
            this.task,
            this.mDisplayContent.getConfiguration().orientation);
  }

  @Override // com.android.server.wm.WindowContainer,
            // com.android.server.wm.SurfaceAnimator.Animatable
  public void onAnimationLeashLost(SurfaceControl.Transaction transaction) {
    super.onAnimationLeashLost(transaction);
    SurfaceControl surfaceControl = this.mAnimationBoundsLayer;
    if (surfaceControl != null) {
      transaction.remove(surfaceControl);
      this.mAnimationBoundsLayer = null;
    }
    this.mNeedsAnimationBoundsLayer = false;
    if (this.mNeedsLetterboxedAnimation) {
      this.mNeedsLetterboxedAnimation = false;
      updateLetterboxSurface(findMainWindow(), transaction);
    }
    AnimatingActivityRegistry animatingActivityRegistry = this.mAnimatingActivityRegistry;
    if (animatingActivityRegistry != null) {
      animatingActivityRegistry.notifyFinished(this);
    }
  }

  @Override // com.android.server.wm.WindowContainer
  public void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
    WindowState window;
    super.onAnimationFinished(i, animationAdapter);
    Trace.traceBegin(32L, "AR#onAnimationFinished");
    this.mTransit = -1;
    this.mTransitFlags = 0;
    setAppLayoutChanges(12, "ActivityRecord");
    clearThumbnail();
    setClientVisible(isVisible() || this.mVisibleRequested);
    getDisplayContent().computeImeTargetIfNeeded(this);
    if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_ANIM,
          2010476671,
          1020,
          (String) null,
          new Object[] {
            String.valueOf(this),
            Boolean.valueOf(this.reportedVisible),
            Boolean.valueOf(okToDisplay()),
            Boolean.valueOf(okToAnimate()),
            Boolean.valueOf(isStartingWindowDisplayed())
          });
    }
    WindowContainerThumbnail windowContainerThumbnail = this.mThumbnail;
    if (windowContainerThumbnail != null) {
      windowContainerThumbnail.destroy();
      this.mThumbnail = null;
    }
    new ArrayList(this.mChildren)
        .forEach(
            new Consumer() { // from class:
                             // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda8
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((WindowState) obj).onExitAnimationDone();
              }
            });
    Task task = this.task;
    if (task != null
        && this.startingMoved
        && (window =
                task.getWindow(
                    new Predicate() { // from class:
                                      // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda9
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        boolean lambda$onAnimationFinished$17;
                        lambda$onAnimationFinished$17 =
                            ActivityRecord.lambda$onAnimationFinished$17((WindowState) obj);
                        return lambda$onAnimationFinished$17;
                      }
                    }))
            != null
        && window.mAnimatingExit
        && !window.isSelfAnimating(0, 16)) {
      window.onExitAnimationDone();
    }
    getDisplayContent().mAppTransition.notifyAppTransitionFinishedLocked(this.token);
    scheduleAnimation();
    this.mTaskSupervisor.scheduleProcessStoppingAndFinishingActivitiesIfNeeded();
    Trace.traceEnd(32L);
  }

  public static /* synthetic */ boolean lambda$onAnimationFinished$17(WindowState windowState) {
    return windowState.mAttrs.type == 3;
  }

  public void clearAnimatingFlags() {
    boolean z = false;
    for (int size = this.mChildren.size() - 1; size >= 0; size--) {
      z |= ((WindowState) this.mChildren.get(size)).clearAnimatingFlags();
    }
    if (z) {
      requestUpdateWallpaperIfNeeded();
    }
  }

  @Override // com.android.server.wm.WindowContainer
  public void cancelAnimation() {
    super.cancelAnimation();
    clearThumbnail();
  }

  public final void clearThumbnail() {
    WindowContainerThumbnail windowContainerThumbnail = this.mThumbnail;
    if (windowContainerThumbnail == null) {
      return;
    }
    windowContainerThumbnail.destroy();
    this.mThumbnail = null;
  }

  public int getTransit() {
    return this.mTransit;
  }

  public void registerRemoteAnimations(RemoteAnimationDefinition remoteAnimationDefinition) {
    this.mRemoteAnimationDefinition = remoteAnimationDefinition;
    if (remoteAnimationDefinition != null) {
      remoteAnimationDefinition.linkToDeath(
          new IBinder
              .DeathRecipient() { // from class:
                                  // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
              ActivityRecord.this.unregisterRemoteAnimations();
            }
          });
    }
  }

  public void unregisterRemoteAnimations() {
    this.mRemoteAnimationDefinition = null;
  }

  @Override // com.android.server.wm.WindowContainer
  public RemoteAnimationDefinition getRemoteAnimationDefinition() {
    return this.mRemoteAnimationDefinition;
  }

  @Override // com.android.server.wm.WindowToken
  public void applyFixedRotationTransform(
      DisplayInfo displayInfo, DisplayFrames displayFrames, Configuration configuration) {
    Slog.d(
        "ActivityTaskManager",
        "applyFixedRotationTransform, r=" + this + ", caller=" + Debug.getCallers(5));
    super.applyFixedRotationTransform(displayInfo, displayFrames, configuration);
    ensureActivityConfiguration(0, false);
  }

  @Override // com.android.server.wm.WindowContainer
  public int getRequestedConfigurationOrientation(boolean z) {
    return getRequestedConfigurationOrientation(z, getOverrideOrientation());
  }

  @Override // com.android.server.wm.WindowContainer
  public int getRequestedConfigurationOrientation(boolean z, int i) {
    ActivityRecord activity;
    if (this.mLetterboxUiController.hasInheritedOrientation()) {
      RootDisplayArea rootDisplayArea = getRootDisplayArea();
      if (z && rootDisplayArea != null && rootDisplayArea.isOrientationDifferentFromDisplay()) {
        return reverseConfigurationOrientation(
            this.mLetterboxUiController.getInheritedOrientation());
      }
      return this.mLetterboxUiController.getInheritedOrientation();
    }
    Task task = this.task;
    if (task != null
        && i == 3
        && (activity =
                task.getActivity(
                    new Predicate() { // from class:
                                      // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda7
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        boolean canDefineOrientationForActivitiesAbove;
                        canDefineOrientationForActivitiesAbove =
                            ((ActivityRecord) obj).canDefineOrientationForActivitiesAbove();
                        return canDefineOrientationForActivitiesAbove;
                      }
                    },
                    this,
                    false,
                    true))
            != null) {
      return activity.getRequestedConfigurationOrientation(z);
    }
    return super.getRequestedConfigurationOrientation(z, i);
  }

  public boolean canDefineOrientationForActivitiesAbove() {
    int overrideOrientation;
    return (this.finishing
            || (overrideOrientation = getOverrideOrientation()) == -2
            || overrideOrientation == 3)
        ? false
        : true;
  }

  @Override // com.android.server.wm.WindowToken
  public void onCancelFixedRotationTransform(int i) {
    if (this != this.mDisplayContent.getLastOrientationSource()) {
      return;
    }
    int requestedConfigurationOrientation = getRequestedConfigurationOrientation();
    if (requestedConfigurationOrientation == 0
        || requestedConfigurationOrientation
            == this.mDisplayContent.getConfiguration().orientation) {
      this.mDisplayContent.mPinnedTaskController.onCancelFixedRotationTransform();
      startFreezingScreen(i);
      ensureActivityConfiguration(0, false);
      if (this.mTransitionController.isCollecting(this)) {
        this.task.resetSurfaceControlTransforms();
      }
    }
  }

  public void setRequestedOrientation(int i) {
    CompatDisplayInsets compatDisplayInsets;
    if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_ORIENTATION,
          847468954,
          1,
          (String) null,
          new Object[] {Long.valueOf(i), String.valueOf(this)});
    }
    EventLogTags.writeWmRequestedOrientation(i, this.packageName);
    if (this.mLetterboxUiController.shouldIgnoreRequestedOrientation(i)) {
      return;
    }
    if (getRequestedConfigurationOrientation(false, i)
            != getRequestedConfigurationOrientation(false)
        && (!CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT
            || ((compatDisplayInsets = this.mCompatDisplayInsets) != null
                && !compatDisplayInsets.mCreatedByRotationCompat))) {
      clearSizeCompatModeAttributes();
    }
    if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_ORIENTATION,
          -1104347731,
          0,
          (String) null,
          new Object[] {
            String.valueOf(ActivityInfo.screenOrientationToString(i)), String.valueOf(this)
          });
    }
    setOrientation(i, this);
    if (CoreRune.MT_SUPPORT_SIZE_COMPAT) {
      SizeCompatPolicyManager.get().ensureConfiguration(getTask());
    }
    if (this.task.isDexCompatEnabled()) {
      if (this.mAtmService.mDexCompatController.isOrientationChangedLocked(this.task, this)) {
        this.mAtmService.mDexCompatController.rotateDexCompatTaskLocked(this);
      } else if (this.task.isDexCompatUiFullscreen()) {
        this.mAtmService.mDexCompatController.changeWindowingModeIfNeeded(
            this.task.getRootTask(), this.task, this);
      }
    }
    if (!getMergedOverrideConfiguration()
        .equals(this.mLastReportedConfiguration.getMergedConfiguration())) {
      ensureActivityConfiguration(0, false, false, true);
      if (this.mTransitionController.inPlayingTransition(this)) {
        this.mTransitionController.mValidateActivityCompat.add(this);
      }
    }
    this.mAtmService
        .getTaskChangeNotificationController()
        .notifyActivityRequestedOrientationChanged(this.task.mTaskId, i);
    this.mDisplayContent.getDisplayRotation().onSetRequestedOrientation();
  }

  public void reportDescendantOrientationChangeIfNeeded() {
    if (onDescendantOrientationChanged(this)) {
      this.task.dispatchTaskInfoChangedIfNeeded(true);
    }
  }

  public boolean shouldIgnoreOrientationRequests() {
    return this.mAppActivityEmbeddingSplitsEnabled
        && ActivityInfo.isFixedOrientationPortrait(getOverrideOrientation())
        && !this.task.inMultiWindowMode()
        && getTask().getConfiguration().smallestScreenWidthDp >= 600;
  }

  @Override // com.android.server.wm.WindowContainer
  public int getOrientation(int i) {
    if (shouldIgnoreOrientationRequests()) {
      return -2;
    }
    if (i == 3) {
      return getOverrideOrientation();
    }
    if (getDisplayContent().mClosingApps.contains(this)
        || !(isVisibleRequested() || getDisplayContent().mOpeningApps.contains(this))) {
      return -2;
    }
    return getOverrideOrientation();
  }

  @Override // com.android.server.wm.WindowContainer
  public int getOverrideOrientation() {
    return this.mLetterboxUiController.overrideOrientationIfNeeded(super.getOverrideOrientation());
  }

  public int getRequestedOrientation() {
    return super.getOverrideOrientation();
  }

  public void setLastReportedGlobalConfiguration(Configuration configuration) {
    this.mLastReportedConfiguration.setGlobalConfiguration(configuration);
  }

  public void setLastReportedConfiguration(MergedConfiguration mergedConfiguration) {
    setLastReportedConfiguration(
        mergedConfiguration.getGlobalConfiguration(),
        mergedConfiguration.getOverrideConfiguration());
  }

  public final void setLastReportedConfiguration(
      Configuration configuration, Configuration configuration2) {
    this.mLastReportedConfiguration.setConfiguration(configuration, configuration2);
  }

  public MergedConfiguration getLastReportedConfiguration() {
    return this.mLastReportedConfiguration;
  }

  public CompatDisplayInsets getCompatDisplayInsets() {
    if (this.mLetterboxUiController.hasInheritedLetterboxBehavior()) {
      return this.mLetterboxUiController.getInheritedCompatDisplayInsets();
    }
    return this.mCompatDisplayInsets;
  }

  public boolean hasCompatDisplayInsetsWithoutInheritance() {
    return this.mCompatDisplayInsets != null;
  }

  public boolean inSizeCompatMode() {
    WindowContainer parent;
    if (this.mInSizeCompatModeForBounds) {
      return true;
    }
    return (getCompatDisplayInsets() == null
            || !shouldCreateCompatDisplayInsets()
            || isFixedRotationTransforming()
            || getConfiguration().windowConfiguration.getAppBounds() == null
            || (parent = getParent()) == null
            || parent.getConfiguration().densityDpi == getConfiguration().densityDpi)
        ? false
        : true;
  }

  public boolean shouldCreateCompatDisplayInsets() {
    return shouldCreateCompatDisplayInsets(false);
  }

  public boolean shouldCreateCompatDisplayInsets(boolean z) {
    int supportsSizeChanges;
    Task task;
    if ((CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT
            && z
            && this.mAtmService.mExt.mOrientationController.shouldCreateCompatDisplayInsets(this))
        || (supportsSizeChanges = supportsSizeChanges()) == 1) {
      return true;
    }
    if (supportsSizeChanges == 2
        || supportsSizeChanges == 3
        || this.mPopOverState.isActivated()
        || isDexMode()
        || ((task = this.task) != null && task.isDexMode())) {
      return false;
    }
    DisplayContent displayContent = this.mDisplayContent;
    if (displayContent != null && displayContent.isRemoteAppDisplay()) {
      return false;
    }
    DisplayContent displayContent2 = this.mDisplayContent;
    if (displayContent2 != null && displayContent2.isAppCastingDisplay()) {
      return false;
    }
    if (this.task != null && (inMultiWindowMode() || this.task.inMultiWindowMode())) {
      return false;
    }
    if (CoreRune.FW_FIXED_ASPECT_RATIO_MODE && this.mCompatRecord.isFixedAspectRatioModeEnabled()) {
      return false;
    }
    if (CoreRune.FW_ORIENTATION_CONTROL && OrientationController.isEnabled(this.task)) {
      return false;
    }
    if (inMultiWindowMode() || getWindowConfiguration().hasWindowDecorCaption()) {
      Task task2 = this.task;
      ActivityRecord rootActivity = task2 != null ? task2.getRootActivity() : null;
      if (rootActivity != null
          && rootActivity != this
          && !rootActivity.shouldCreateCompatDisplayInsets()) {
        return false;
      }
    }
    return !isResizeable()
        && (this.info.isFixedOrientation() || hasFixedAspectRatio())
        && isActivityTypeStandardOrUndefined();
  }

  public int supportsSizeChanges() {
    if (this.mLetterboxUiController.shouldOverrideForceNonResizeApp()) {
      return 1;
    }
    if (this.info.supportsSizeChanges) {
      return 2;
    }
    return this.mLetterboxUiController.shouldOverrideForceResizeApp() ? 3 : 0;
  }

  @Override // com.android.server.wm.WindowToken
  public boolean hasSizeCompatBounds() {
    SizeCompatAttributes sizeCompatAttributes;
    return (CoreRune.MT_SUPPORT_SIZE_COMPAT
            && (sizeCompatAttributes = this.mSizeCompatAttributes) != null
            && sizeCompatAttributes.hasBounds())
        || this.mSizeCompatBounds != null;
  }

  public final void updateCompatDisplayInsets() {
    BoundsCompatRecord boundsCompatRecord;
    CompatDisplayInsets compatDisplayInsets;
    if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT_FOR_FOLD
        && (compatDisplayInsets =
                (boundsCompatRecord = this.mCompatRecord).mPendingCompatDisplayInsets)
            != null) {
      boundsCompatRecord.mPendingCompatDisplayInsets = null;
      if (this.mCompatDisplayInsets == null && !this.mAtmService.mWindowManager.isFolded()) {
        this.mCompatDisplayInsets = compatDisplayInsets;
        return;
      }
    }
    if (getCompatDisplayInsets() == null
        && shouldCreateCompatDisplayInsets(CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT)) {
      Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
      Configuration configuration = getConfiguration();
      requestedOverrideConfiguration.colorMode = configuration.colorMode;
      requestedOverrideConfiguration.densityDpi = configuration.densityDpi;
      requestedOverrideConfiguration.smallestScreenWidthDp = configuration.smallestScreenWidthDp;
      if (ActivityInfo.isFixedOrientation(getOverrideOrientation())) {
        requestedOverrideConfiguration.windowConfiguration.setRotation(
            configuration.windowConfiguration.getRotation());
      }
      this.mCompatDisplayInsets =
          new CompatDisplayInsets(
              this.mDisplayContent, this, this.mLetterboxBoundsForFixedOrientationAndAspectRatio);
      if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT) {
        BoundsCompatRecord boundsCompatRecord2 = this.mCompatRecord;
        if (boundsCompatRecord2.mIsTaskOrientationMismatched
            && boundsCompatRecord2.mCanRotationCompatMode) {
          boundsCompatRecord2.setController(boundsCompatRecord2.mCandidateController);
        }
      }
    }
  }

  public final void clearSizeCompatModeAttributes() {
    this.mInSizeCompatModeForBounds = false;
    this.mSizeCompatScale = 1.0f;
    this.mSizeCompatBounds = null;
    this.mCompatDisplayInsets = null;
    this.mLetterboxUiController.clearInheritedCompatDisplayInsets();
  }

  public void clearSizeCompatMode() {
    clearSizeCompatMode(false, true);
  }

  public void clearSizeCompatMode(boolean z, boolean z2) {
    float f = this.mSizeCompatScale;
    clearSizeCompatModeAttributes();
    if (this.mSizeCompatScale != f) {
      forAllWindows((Consumer) new ActivityRecord$$ExternalSyntheticLambda6(), false);
    }
    int activityType = getActivityType();
    Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
    if (z2) {
      requestedOverrideConfiguration.unset();
      requestedOverrideConfiguration.windowConfiguration.setActivityType(activityType);
    }
    if (z) {
      return;
    }
    onRequestedOverrideConfigurationChanged(requestedOverrideConfiguration);
  }

  @Override // com.android.server.wm.ConfigurationContainer
  public boolean matchParentBounds() {
    WindowContainer parent;
    Rect resolvedOverrideBounds = getResolvedOverrideBounds();
    return resolvedOverrideBounds.isEmpty()
        || (parent = getParent()) == null
        || parent.getBounds().equals(resolvedOverrideBounds);
  }

  @Override // com.android.server.wm.WindowToken
  public float getCompatScale() {
    SizeCompatAttributes sizeCompatAttributes;
    if (CoreRune.MT_SUPPORT_SIZE_COMPAT
        && (sizeCompatAttributes = this.mSizeCompatAttributes) != null
        && sizeCompatAttributes.hasBounds()) {
      return this.mSizeCompatAttributes.getScale();
    }
    return hasSizeCompatBounds() ? this.mSizeCompatScale : super.getCompatScale();
  }

  @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer,
            // com.android.server.wm.ConfigurationContainer
  public void resolveOverrideConfiguration(Configuration configuration) {
    Configuration requestedOverrideConfiguration;
    int i;
    Rect rect;
    float f;
    SizeCompatPolicy compatPolicy;
    Task task;
    Configuration requestedOverrideConfiguration2 = getRequestedOverrideConfiguration();
    int i2 = requestedOverrideConfiguration2.assetsSeq;
    int i3 = 0;
    if (i2 != 0 && configuration.assetsSeq > i2) {
      requestedOverrideConfiguration2.assetsSeq = 0;
    }
    if ((getDisplayId() == 1 || isDexMode())
        && attachedToProcess()
        && !this.app.isActivityConfigOverrideAllowed()
        && (i =
                (requestedOverrideConfiguration = this.app.getRequestedOverrideConfiguration())
                    .assetsSeq)
            != 0) {
      int i4 = configuration.assetsSeq - i;
      if (Math.abs(i4) <= 268435456 && i4 < 0) {
        requestedOverrideConfiguration2.assetsSeq = requestedOverrideConfiguration.assetsSeq;
      }
    }
    super.resolveOverrideConfiguration(configuration);
    Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
    applyLocaleOverrideIfNeeded(resolvedOverrideConfiguration);
    if (this.mCompatRecord.mCandidateController != null
        && (task = this.task) != null
        && task.inMultiWindowMode() != inMultiWindowMode()) {
      getConfiguration()
          .windowConfiguration
          .setWindowingMode(configuration.windowConfiguration.getWindowingMode());
    }
    if (isFixedRotationTransforming()) {
      this.mTmpConfig.setTo(configuration);
      this.mTmpConfig.updateFrom(resolvedOverrideConfiguration);
      configuration = this.mTmpConfig;
    }
    this.mIsAspectRatioApplied = false;
    this.mIsEligibleForFixedOrientationLetterbox = false;
    this.mLetterboxBoundsForFixedOrientationAndAspectRatio = null;
    if (CoreRune.MT_SUPPORT_SIZE_COMPAT) {
      this.mResolvedConfigChangeFlags =
          SizeCompatPolicyManager.get()
              .applySizeScaleCompatPolicy(this, configuration, this.mResolvedConfigChangeFlags);
    }
    int windowingMode = configuration.windowConfiguration.getWindowingMode();
    boolean z =
        windowingMode == 6
            || windowingMode == 1
            || (!this.mWaitForEnteringPinnedMode
                && windowingMode == 2
                && resolvedOverrideConfiguration.windowConfiguration.getWindowingMode() == 1);
    if (z) {
      resolveFixedOrientationConfiguration(configuration);
    }
    CompatDisplayInsets compatDisplayInsets = getCompatDisplayInsets();
    if (!CoreRune.MT_SUPPORT_SIZE_COMPAT || this.mResolvedConfigChangeFlags == 0) {
      if (compatDisplayInsets != null) {
        resolveSizeCompatModeConfiguration(configuration, compatDisplayInsets);
      } else if (inMultiWindowMode() && !z) {
        resolvedOverrideConfiguration.orientation = 0;
        if (!matchParentBounds()) {
          getTaskFragment()
              .computeConfigResourceOverrides(resolvedOverrideConfiguration, configuration);
        }
      } else if (!isLetterboxedForFixedOrientationAndAspectRatio()) {
        resolveAspectRatioRestriction(configuration);
      }
    }
    if (z || compatDisplayInsets != null || !inMultiWindowMode()) {
      updateResolvedBoundsPosition(configuration);
    }
    DisplayContent displayContent = this.mDisplayContent;
    boolean z2 = displayContent != null && displayContent.getIgnoreOrientationRequest();
    if (compatDisplayInsets == null
        && (this.mLetterboxBoundsForFixedOrientationAndAspectRatio != null
            || (z2 && this.mIsAspectRatioApplied))) {
      resolvedOverrideConfiguration.smallestScreenWidthDp =
          Math.min(
              resolvedOverrideConfiguration.screenWidthDp,
              resolvedOverrideConfiguration.screenHeightDp);
    }
    if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT) {
      if (this.mCompatDisplayInsets == null) {
        BoundsCompatRecord boundsCompatRecord = this.mCompatRecord;
        if (boundsCompatRecord.mCanRotationCompatMode
            && boundsCompatRecord.mIsTaskOrientationMismatched) {
          updateCompatDisplayInsets();
        }
      }
      CompatDisplayInsets compatDisplayInsets2 = this.mCompatDisplayInsets;
      if (compatDisplayInsets2 != null && compatDisplayInsets2.mConfigChangeNeeded) {
        compatDisplayInsets2.mConfigChangeNeeded = false;
        super.onConfigurationChanged(getParent().getConfiguration());
        return;
      }
    }
    this.mCompatRecord.resolve(configuration);
    this.mPopOverState.toggle();
    resolvedOverrideConfiguration.windowConfiguration.setPopOverState(
        this.mPopOverState.getState());
    if (this.mPopOverState.isActivated()) {
      resolvedOverrideConfiguration.windowConfiguration.setBounds(
          PopOverBoundsCalculator.getBounds(this));
      this.task.computeConfigResourceOverrides(resolvedOverrideConfiguration, configuration);
    }
    this.mAtmService.mExt.mDisplayCutoutController.adjustAppBoundsIfNeeded(this, configuration);
    if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX) {
      if (!CoreRune.MT_SUPPORT_SIZE_COMPAT
          || (compatPolicy = SizeCompatPolicyManager.get().getCompatPolicy(getTask())) == null) {
        rect = null;
        f = -1.0f;
      } else {
        i3 = 0 | compatPolicy.getCompatSandboxFlags(this);
        f = compatPolicy.getCompatSandboxScale(this, i3);
        rect = compatPolicy.getCompatSandboxBounds(this, i3);
      }
      if (i3 == 0 && this.mCompatRecord.isCompatModeEnabled()) {
        BoundsCompatController controller = this.mCompatRecord.getController();
        i3 |= 32;
        if (controller.shouldUseSandboxDisplay(this)) {
          i3 |= 2;
        }
        if (controller.shouldUseSandboxViewBoundsAndMotionEvent(this)) {
          i3 |= 12;
          f = this.mSizeCompatScale;
          rect = this.mSizeCompatBounds;
        }
      }
      if (i3 == 0
          && this.mLastReportedConfiguration
                  .getMergedConfiguration()
                  .windowConfiguration
                  .getCompatSandboxFlags()
              != 0) {
        rect = CompatSandbox.getEmptyRect();
        f = 1.0f;
        i3 = 1;
      }
      resolvedOverrideConfiguration.windowConfiguration.setCompatSandboxValues(i3, f, rect);
    }
    int i5 = this.mConfigurationSeq + 1;
    this.mConfigurationSeq = i5;
    this.mConfigurationSeq = Math.max(i5, 1);
    getResolvedOverrideConfiguration().seq = this.mConfigurationSeq;
    if (providesMaxBounds()) {
      this.mTmpBounds.set(resolvedOverrideConfiguration.windowConfiguration.getBounds());
      if (this.mTmpBounds.isEmpty()) {
        this.mTmpBounds.set(configuration.windowConfiguration.getBounds());
      }
      resolvedOverrideConfiguration.windowConfiguration.setMaxBounds(this.mTmpBounds);
    }
    logAppCompatState();
  }

  public int getOrientationForReachability() {
    if (this.mLetterboxUiController.hasInheritedLetterboxBehavior()) {
      return this.mLetterboxUiController.getInheritedOrientation();
    }
    return getRequestedConfigurationOrientation();
  }

  public boolean areBoundsLetterboxed() {
    return getAppCompatState(true) != 2;
  }

  public final void logAppCompatState() {
    this.mTaskSupervisor.getActivityMetricsLogger().logAppCompatState(this);
  }

  public int getAppCompatState() {
    return getAppCompatState(false);
  }

  public final int getAppCompatState(boolean z) {
    if (!z && !this.mVisibleRequested) {
      return 1;
    }
    if (this.mLetterboxUiController.hasInheritedLetterboxBehavior()) {
      return this.mLetterboxUiController.getInheritedAppCompatState();
    }
    if (this.mInSizeCompatModeForBounds) {
      return 3;
    }
    if (isLetterboxedForFixedOrientationAndAspectRatio()) {
      return 4;
    }
    return this.mIsAspectRatioApplied ? 5 : 2;
  }

  public final void updateResolvedBoundsPosition(Configuration configuration) {
    int i;
    if (!CoreRune.MT_SUPPORT_SIZE_COMPAT || this.mResolvedConfigChangeFlags == 0) {
      if (CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_CONTROL
          && this.mCompatRecord.isCompatModeEnabled()
          && this.mCompatRecord.getController().shouldUpdatePosition()) {
        return;
      }
      Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
      Rect bounds = resolvedOverrideConfiguration.windowConfiguration.getBounds();
      if (bounds.isEmpty()) {
        return;
      }
      Rect rect = this.mSizeCompatBounds;
      if (rect == null) {
        rect = bounds;
      }
      Rect appBounds = configuration.windowConfiguration.getAppBounds();
      Rect bounds2 = configuration.windowConfiguration.getBounds();
      float width = rect.width();
      float width2 = appBounds.width();
      int i2 = 0;
      if (bounds2.width() == width || width > width2) {
        i = 0;
      } else {
        i =
            Math.max(
                0,
                (((int)
                            Math.ceil(
                                (width2 - width)
                                    * this.mLetterboxUiController.getHorizontalPositionMultiplier(
                                        configuration)))
                        - rect.left)
                    + appBounds.left);
      }
      float height = appBounds.height();
      float height2 = bounds2.height();
      float height3 = rect.height();
      if (height2 != height3 && height3 <= height) {
        float verticalPositionMultiplier =
            this.mLetterboxUiController.getVerticalPositionMultiplier(configuration);
        this.mDisplayContent.getDisplayPolicy().isImmersiveMode();
        i2 =
            Math.max(
                0,
                (((int) Math.ceil((height - height3) * verticalPositionMultiplier)) - rect.top)
                    + appBounds.top);
      }
      Rect rect2 = this.mSizeCompatBounds;
      if (rect2 != null) {
        rect2.offset(i, i2);
        Rect rect3 = this.mSizeCompatBounds;
        offsetBounds(
            resolvedOverrideConfiguration, rect3.left - bounds.left, rect3.top - bounds.top);
      } else {
        offsetBounds(resolvedOverrideConfiguration, i, i2);
      }
      if (resolvedOverrideConfiguration.windowConfiguration.getAppBounds().top == appBounds.top) {
        resolvedOverrideConfiguration.windowConfiguration.getBounds().top = bounds2.top;
        Rect rect4 = this.mSizeCompatBounds;
        if (rect4 != null) {
          rect4.top = bounds2.top;
        }
      }
      getTaskFragment()
          .computeConfigResourceOverrides(resolvedOverrideConfiguration, configuration);
      float f = this.mSizeCompatScale;
      if (f != 1.0f) {
        int i3 = bounds.left;
        int i4 = bounds.top;
        offsetBounds(
            resolvedOverrideConfiguration,
            ((int) ((i3 / f) + 0.5f)) - i3,
            ((int) ((i4 / f) + 0.5f)) - i4);
      }
    }
  }

  public Rect getScreenResolvedBounds() {
    Rect bounds = getResolvedOverrideConfiguration().windowConfiguration.getBounds();
    Rect rect = this.mSizeCompatBounds;
    return rect != null ? rect : bounds;
  }

  public void recomputeConfiguration() {
    if (this.mLetterboxUiController.applyOnOpaqueActivityBelow(
        new Consumer() { // from class:
                         // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda23
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((ActivityRecord) obj).recomputeConfiguration();
          }
        })) {
      return;
    }
    onRequestedOverrideConfigurationChanged(getRequestedOverrideConfiguration());
  }

  public boolean isInTransition() {
    return inTransitionSelfOrParent();
  }

  public boolean isDisplaySleepingAndSwapping() {
    for (int size = this.mDisplayContent.mAllSleepTokens.size() - 1; size >= 0; size--) {
      if (((RootWindowContainer.SleepToken) this.mDisplayContent.mAllSleepTokens.get(size))
          .isDisplaySwapping()) {
        return true;
      }
    }
    return false;
  }

  public boolean isLetterboxedForFixedOrientationAndAspectRatio() {
    if (CoreRune.MT_SUPPORT_SIZE_COMPAT_COORDINATION) {
      BoundsCompatRecord boundsCompatRecord = this.mCompatRecord;
      if (boundsCompatRecord.mSupportsIgnoreOrientationRequest
          && boundsCompatRecord.mIsTaskOrientationMismatched
          && boundsCompatRecord.isCompatModeEnabled()) {
        return true;
      }
    }
    return this.mLetterboxBoundsForFixedOrientationAndAspectRatio != null;
  }

  public boolean isAspectRatioApplied() {
    return this.mIsAspectRatioApplied;
  }

  public boolean isEligibleForLetterboxEducation() {
    return this.mWmService.mLetterboxConfiguration.getIsEducationEnabled()
        && this.mIsEligibleForFixedOrientationLetterbox
        && getWindowingMode() == 1
        && getRequestedConfigurationOrientation() == 1
        && this.mStartingWindow == null;
  }

  public final boolean orientationRespectedWithInsets(Rect rect, Rect rect2) {
    int requestedConfigurationOrientation;
    DisplayInfo displayInfo;
    rect2.setEmpty();
    boolean z = true;
    if (this.mDisplayContent == null
        || (requestedConfigurationOrientation = getRequestedConfigurationOrientation()) == 0) {
      return true;
    }
    int i = rect.height() >= rect.width() ? 1 : 2;
    if (isFixedRotationTransforming()) {
      displayInfo = getFixedRotationTransformDisplayInfo();
    } else {
      displayInfo = this.mDisplayContent.getDisplayInfo();
    }
    getTask().calculateInsetFrames(this.mTmpBounds, rect2, rect, displayInfo);
    int i2 = rect2.height() >= rect2.width() ? 1 : 2;
    if (i != i2 && i2 != requestedConfigurationOrientation) {
      z = false;
    }
    if (z) {
      rect2.setEmpty();
    }
    return z;
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean handlesOrientationChangeFromDescendant(int i) {
    if (shouldIgnoreOrientationRequests()) {
      return false;
    }
    return super.handlesOrientationChangeFromDescendant(i);
  }

  public final void resolveFixedOrientationConfiguration(Configuration configuration) {
    TaskFragment organizedTaskFragment;
    if (!CoreRune.MT_SUPPORT_SIZE_COMPAT || this.mResolvedConfigChangeFlags == 0) {
      Task task = this.task;
      if ((task != null && (task.inMultiWindowMode() || inMultiWindowMode()))
          || this.mPopOverState.isActivated()
          || isActivityTypeHomeOrRecents()) {
        return;
      }
      Rect bounds = configuration.windowConfiguration.getBounds();
      Rect rect = new Rect();
      boolean orientationRespectedWithInsets = orientationRespectedWithInsets(bounds, rect);
      if (CoreRune.FW_ORIENTATION_CONTROL) {
        BoundsCompatRecord boundsCompatRecord = this.mCompatRecord;
        if (boundsCompatRecord.mIsTaskOrientationMismatched) {
          if (boundsCompatRecord.isFullScreen()) {
            return;
          }
          if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT
              && this.mCompatRecord.mCanRotationCompatMode) {
            return;
          }
          organizedTaskFragment = getOrganizedTaskFragment();
          if ((organizedTaskFragment != null || organizedTaskFragment.fillsParent())
              && !isDexMode()) {
            Rect bounds2 = getResolvedOverrideConfiguration().windowConfiguration.getBounds();
            int i = configuration.orientation;
            int requestedConfigurationOrientation = getRequestedConfigurationOrientation();
            boolean z =
                requestedConfigurationOrientation == 0 && requestedConfigurationOrientation != i;
            this.mIsEligibleForFixedOrientationLetterbox = z;
            if ((CoreRune.FW_ORIENTATION_CONTROL
                    || !this.mCompatRecord.mIsTaskOrientationMismatched)
                && !z
                && (requestedConfigurationOrientation == 0 || orientationRespectedWithInsets)) {
              return;
            }
            CompatDisplayInsets compatDisplayInsets = getCompatDisplayInsets();
            if (compatDisplayInsets == null || compatDisplayInsets.mIsInFixedOrientationLetterbox) {
              if (orientationRespectedWithInsets) {
                rect = configuration.windowConfiguration.getAppBounds();
              }
              Rect rect2 = new Rect();
              Rect rect3 = new Rect();
              if (CoreRune.FW_ORIENTATION_CONTROL
                  && this.mCompatRecord.mIsTaskOrientationMismatched) {
                rect2.set(bounds);
                rect3.set(rect);
              } else if (requestedConfigurationOrientation == 2) {
                int min = Math.min((rect.top + bounds.width()) - 1, rect.bottom);
                rect2.set(bounds.left, rect.top, bounds.right, min);
                rect3.set(rect.left, rect.top, rect.right, min);
              } else {
                int min2 = Math.min(rect.left + bounds.height(), rect.right);
                rect2.set(rect.left, bounds.top, min2, bounds.bottom);
                rect3.set(rect.left, rect.top, min2, rect.bottom);
              }
              Rect rect4 = new Rect(bounds2);
              bounds2.set(rect2);
              float fixedOrientationLetterboxAspectRatio =
                  this.mLetterboxUiController.getFixedOrientationLetterboxAspectRatio(
                      configuration);
              if (isDefaultMultiWindowLetterboxAspectRatioDesired(configuration)) {
                fixedOrientationLetterboxAspectRatio = 1.01f;
              } else if (fixedOrientationLetterboxAspectRatio <= 1.0f) {
                fixedOrientationLetterboxAspectRatio = computeAspectRatio(bounds);
              }
              this.mIsAspectRatioApplied =
                  applyAspectRatio(bounds2, rect3, rect2, fixedOrientationLetterboxAspectRatio);
              if (compatDisplayInsets != null) {
                compatDisplayInsets.getBoundsByRotation(
                    this.mTmpBounds, configuration.windowConfiguration.getRotation());
                if (bounds2.width() != this.mTmpBounds.width()
                    || bounds2.height() != this.mTmpBounds.height()) {
                  bounds2.set(rect4);
                  return;
                }
              }
              getTaskFragment()
                  .computeConfigResourceOverrides(
                      getResolvedOverrideConfiguration(), configuration, compatDisplayInsets);
              this.mLetterboxBoundsForFixedOrientationAndAspectRatio = new Rect(bounds2);
              return;
            }
            return;
          }
          return;
        }
      }
      if (orientationRespectedWithInsets
          && handlesOrientationChangeFromDescendant(getOverrideOrientation())) {
        return;
      }
      organizedTaskFragment = getOrganizedTaskFragment();
      if (organizedTaskFragment != null) {}
      Rect bounds22 = getResolvedOverrideConfiguration().windowConfiguration.getBounds();
      int i2 = configuration.orientation;
      int requestedConfigurationOrientation2 = getRequestedConfigurationOrientation();
      if (requestedConfigurationOrientation2 == 0) {}
      this.mIsEligibleForFixedOrientationLetterbox = z;
      if (CoreRune.FW_ORIENTATION_CONTROL) {}
    }
  }

  public final boolean isDefaultMultiWindowLetterboxAspectRatioDesired(
      Configuration configuration) {
    return (this.mDisplayContent == null
            || !WindowConfiguration.inMultiWindowMode(
                configuration.windowConfiguration.getWindowingMode())
            || this.mDisplayContent.getIgnoreOrientationRequest())
        ? false
        : true;
  }

  public final void resolveAspectRatioRestriction(Configuration configuration) {
    Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
    Rect appBounds = configuration.windowConfiguration.getAppBounds();
    Rect bounds = configuration.windowConfiguration.getBounds();
    Rect bounds2 = resolvedOverrideConfiguration.windowConfiguration.getBounds();
    this.mTmpBounds.setEmpty();
    this.mIsAspectRatioApplied = applyAspectRatio(this.mTmpBounds, appBounds, bounds);
    if (!this.mTmpBounds.isEmpty()) {
      bounds2.set(this.mTmpBounds);
    }
    if (!bounds2.isEmpty() && !bounds2.equals(bounds)) {
      getTaskFragment()
          .computeConfigResourceOverrides(
              resolvedOverrideConfiguration, configuration, getFixedRotationTransformDisplayInfo());
    }
    if (CoreRune.FW_FIXED_ASPECT_RATIO_MODE && this.mCompatRecord.isFixedAspectRatioModeEnabled()) {
      this.mAtmService.mExt.mFixedAspectRatioController.computeConfigResourceOverridesIfNeeded(
          this, resolvedOverrideConfiguration, bounds2, configuration);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:38:0x00e4  */
  /* JADX WARN: Removed duplicated region for block: B:41:0x0107  */
  /* JADX WARN: Removed duplicated region for block: B:44:0x0113  */
  /* JADX WARN: Removed duplicated region for block: B:47:0x0117  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void resolveSizeCompatModeConfiguration(
      Configuration configuration, CompatDisplayInsets compatDisplayInsets) {
    Rect bounds;
    Rect appBounds;
    int i;
    int i2;
    DisplayContent displayContent;
    Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
    Rect bounds2 = resolvedOverrideConfiguration.windowConfiguration.getBounds();
    if (isLetterboxedForFixedOrientationAndAspectRatio()) {
      bounds = new Rect(bounds2);
    } else {
      bounds = configuration.windowConfiguration.getBounds();
    }
    Rect rect = bounds;
    if (isLetterboxedForFixedOrientationAndAspectRatio()) {
      appBounds = new Rect(getResolvedOverrideConfiguration().windowConfiguration.getAppBounds());
    } else {
      appBounds = configuration.windowConfiguration.getAppBounds();
    }
    Rect rect2 = appBounds;
    int requestedConfigurationOrientation = getRequestedConfigurationOrientation();
    if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT) {
      BoundsCompatRecord boundsCompatRecord = this.mCompatRecord;
      if (boundsCompatRecord.mRotationCompatModeInherited
          && boundsCompatRecord.mIsTaskOrientationMismatched) {
        requestedConfigurationOrientation =
            this.mAtmService.mExt.mOrientationController.getPreferredConfigurationOrientation(
                this, this.task.getConfiguration().orientation);
      }
    }
    boolean z = requestedConfigurationOrientation != 0;
    if (z) {
      i = requestedConfigurationOrientation;
    } else {
      int i3 = compatDisplayInsets.mOriginalRequestedOrientation;
      if (i3 == 0) {
        i3 = configuration.orientation;
      }
      i = i3;
    }
    int rotation = configuration.windowConfiguration.getRotation();
    DisplayContent displayContent2 = this.mDisplayContent;
    boolean z2 =
        displayContent2 == null || displayContent2.getDisplayRotation().isFixedToUserRotation();
    if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT
        && compatDisplayInsets.mCanRotationCompatMode
        && this.mCompatRecord.mIsTaskOrientationMismatched
        && (displayContent = this.mDisplayContent) != null) {
      int compatRotationForOrientation =
          displayContent
              .getDisplayRotation()
              .getCompatRotationForOrientation(requestedConfigurationOrientation, rotation);
      if (compatRotationForOrientation != rotation) {
        resolvedOverrideConfiguration.windowConfiguration.setRotation(compatRotationForOrientation);
        rotation = compatRotationForOrientation;
      }
    } else if (!z2 && !compatDisplayInsets.mIsFloating) {
      resolvedOverrideConfiguration.windowConfiguration.setRotation(rotation);
    } else {
      int rotation2 = resolvedOverrideConfiguration.windowConfiguration.getRotation();
      if (rotation2 != -1) {
        i2 = rotation2;
        Rect rect3 = new Rect();
        Rect rect4 = this.mTmpBounds;
        compatDisplayInsets.getContainerBounds(rect3, rect4, i2, i, z, z2);
        bounds2.set(rect4);
        if (!compatDisplayInsets.mIsFloating) {
          this.mIsAspectRatioApplied = applyAspectRatio(bounds2, rect3, rect4);
        }
        getTaskFragment()
            .computeConfigResourceOverrides(
                resolvedOverrideConfiguration, configuration, compatDisplayInsets);
        resolvedOverrideConfiguration.screenLayout =
            WindowContainer.computeScreenLayout(
                getConfiguration().screenLayout,
                resolvedOverrideConfiguration.screenWidthDp,
                resolvedOverrideConfiguration.screenHeightDp);
        if (resolvedOverrideConfiguration.screenWidthDp
            == resolvedOverrideConfiguration.screenHeightDp) {
          resolvedOverrideConfiguration.orientation = configuration.orientation;
        }
        if (!this.mCompatRecord.isSizeCompatModeEnabled()) {
          resolveSizeCompatBounds(bounds2, rect2, configuration);
          return;
        }
        Rect appBounds2 = resolvedOverrideConfiguration.windowConfiguration.getAppBounds();
        float f = this.mSizeCompatScale;
        updateSizeCompatScale(appBounds2, rect2);
        int i4 = rect2.top - rect.top;
        boolean z3 = i4 != appBounds2.top - bounds2.top;
        if (this.mSizeCompatScale != 1.0f || z3) {
          if (this.mSizeCompatBounds == null) {
            this.mSizeCompatBounds = new Rect();
          }
          this.mSizeCompatBounds.set(appBounds2);
          this.mSizeCompatBounds.offsetTo(0, 0);
          this.mSizeCompatBounds.scale(this.mSizeCompatScale);
          this.mSizeCompatBounds.bottom += i4;
        } else {
          this.mSizeCompatBounds = null;
        }
        if (this.mSizeCompatScale != f) {
          forAllWindows((Consumer) new ActivityRecord$$ExternalSyntheticLambda6(), false);
        }
        boolean equals = bounds2.equals(rect4);
        int i5 = equals ? rect.left : rect2.left;
        int i6 = equals ? rect.top : rect2.top;
        if (i5 != 0 || i6 != 0) {
          Rect rect5 = this.mSizeCompatBounds;
          if (rect5 != null) {
            rect5.offset(i5, i6);
          }
          offsetBounds(resolvedOverrideConfiguration, i5 - bounds2.left, i6 - bounds2.top);
        }
        this.mInSizeCompatModeForBounds = isInSizeCompatModeForBounds(appBounds2, rect2);
        return;
      }
    }
    i2 = rotation;
    Rect rect32 = new Rect();
    Rect rect42 = this.mTmpBounds;
    compatDisplayInsets.getContainerBounds(rect32, rect42, i2, i, z, z2);
    bounds2.set(rect42);
    if (!compatDisplayInsets.mIsFloating) {}
    getTaskFragment()
        .computeConfigResourceOverrides(
            resolvedOverrideConfiguration, configuration, compatDisplayInsets);
    resolvedOverrideConfiguration.screenLayout =
        WindowContainer.computeScreenLayout(
            getConfiguration().screenLayout,
            resolvedOverrideConfiguration.screenWidthDp,
            resolvedOverrideConfiguration.screenHeightDp);
    if (resolvedOverrideConfiguration.screenWidthDp
        == resolvedOverrideConfiguration.screenHeightDp) {}
    if (!this.mCompatRecord.isSizeCompatModeEnabled()) {}
  }

  public void updateSizeCompatScale(final Rect rect, final Rect rect2) {
    this.mSizeCompatScale =
        ((Float)
                this.mLetterboxUiController
                    .findOpaqueNotFinishingActivityBelow()
                    .map(
                        new Function() { // from class:
                                         // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda33
                          @Override // java.util.function.Function
                          public final Object apply(Object obj) {
                            Float lambda$updateSizeCompatScale$19;
                            lambda$updateSizeCompatScale$19 =
                                ActivityRecord.lambda$updateSizeCompatScale$19(
                                    (ActivityRecord) obj);
                            return lambda$updateSizeCompatScale$19;
                          }
                        })
                    .orElseGet(
                        new Supplier() { // from class:
                                         // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda34
                          @Override // java.util.function.Supplier
                          public final Object get() {
                            Float lambda$updateSizeCompatScale$20;
                            lambda$updateSizeCompatScale$20 =
                                ActivityRecord.lambda$updateSizeCompatScale$20(rect, rect2);
                            return lambda$updateSizeCompatScale$20;
                          }
                        }))
            .floatValue();
  }

  public static /* synthetic */ Float lambda$updateSizeCompatScale$19(
      ActivityRecord activityRecord) {
    return Float.valueOf(activityRecord.mSizeCompatScale);
  }

  public static /* synthetic */ Float lambda$updateSizeCompatScale$20(Rect rect, Rect rect2) {
    int width = rect.width();
    int height = rect.height();
    int width2 = rect2.width();
    int height2 = rect2.height();
    return Float.valueOf(
        (width > width2 || height > height2) ? Math.min(width2 / width, height2 / height) : 1.0f);
  }

  public final boolean isInSizeCompatModeForBounds(Rect rect, Rect rect2) {
    if (this.mLetterboxUiController.hasInheritedLetterboxBehavior()) {
      return false;
    }
    int width = rect.width();
    int height = rect.height();
    int width2 = rect2.width();
    int height2 = rect2.height();
    if (width2 == width && height2 == height) {
      return false;
    }
    if ((width2 <= width || height2 <= height) && width2 >= width && height2 >= height) {
      float maxAspectRatio = getMaxAspectRatio();
      if (maxAspectRatio > DisplayPowerController2.RATE_FROM_DOZE_TO_ON
          && (Math.max(width, height) + 0.5f) / Math.min(width, height) >= maxAspectRatio) {
        return false;
      }
      float minAspectRatio = getMinAspectRatio();
      if (minAspectRatio > DisplayPowerController2.RATE_FROM_DOZE_TO_ON
          && (Math.max(width2, height2) + 0.5f) / Math.min(width2, height2) <= minAspectRatio) {
        return false;
      }
    }
    return true;
  }

  public static void offsetBounds(Configuration configuration, int i, int i2) {
    configuration.windowConfiguration.getBounds().offset(i, i2);
    configuration.windowConfiguration.getAppBounds().offset(i, i2);
  }

  @Override // com.android.server.wm.ConfigurationContainer
  public Rect getBounds() {
    SizeCompatAttributes sizeCompatAttributes;
    final Rect bounds = super.getBounds();
    if (CoreRune.MT_SUPPORT_SIZE_COMPAT
        && (sizeCompatAttributes = this.mSizeCompatAttributes) != null
        && sizeCompatAttributes.hasBounds()) {
      return this.mSizeCompatAttributes.getBounds();
    }
    return (Rect)
        this.mLetterboxUiController
            .findOpaqueNotFinishingActivityBelow()
            .map(
                new Function() { // from class:
                                 // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda11
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    return ((ActivityRecord) obj).getBounds();
                  }
                })
            .orElseGet(
                new Supplier() { // from class:
                                 // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda12
                  @Override // java.util.function.Supplier
                  public final Object get() {
                    Rect lambda$getBounds$21;
                    lambda$getBounds$21 = ActivityRecord.this.lambda$getBounds$21(bounds);
                    return lambda$getBounds$21;
                  }
                });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Rect lambda$getBounds$21(Rect rect) {
    if (this.mSizeCompatBounds == null) {
      return rect;
    }
    if (this.mReturnSizeCompatBounds == null) {
      this.mReturnSizeCompatBounds = new Rect();
    }
    this.mReturnSizeCompatBounds.set(this.mSizeCompatBounds);
    return this.mReturnSizeCompatBounds;
  }

  @Override // com.android.server.wm.ConfigurationContainer
  public boolean providesMaxBounds() {
    if (getUid() == 1000) {
      return false;
    }
    DisplayContent displayContent = this.mDisplayContent;
    if ((displayContent != null && !displayContent.sandboxDisplayApis())
        || this.info.neverSandboxDisplayApis(sConstrainDisplayApisConfig)) {
      return false;
    }
    if (this.info.alwaysSandboxDisplayApis(sConstrainDisplayApisConfig)
        || getCompatDisplayInsets() != null
        || shouldCreateCompatDisplayInsets()) {
      return true;
    }
    return CoreRune.MT_SUPPORT_SIZE_COMPAT && this.mResolvedConfigChangeFlags != 0;
  }

  @Override // com.android.server.wm.WindowContainer
  public Rect getAnimationBounds(int i) {
    TaskFragment taskFragment = getTaskFragment();
    return taskFragment != null ? taskFragment.getBounds() : getBounds();
  }

  @Override // com.android.server.wm.WindowContainer
  public void getAnimationPosition(Point point) {
    point.set(0, 0);
  }

  @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
  public void onConfigurationChanged(Configuration configuration) {
    int rotationForActivityInDifferentOrientation;
    int requestedOverrideWindowingMode;
    SizeCompatAttributes sizeCompatAttributes;
    boolean z = false;
    if (CoreRune.MT_SUPPORT_SIZE_COMPAT
        && (sizeCompatAttributes = this.mSizeCompatAttributes) != null) {
      sizeCompatAttributes.setEnabled(false);
    }
    resetSizeCompatModeIfNeeded();
    this.mCompatRecord.prepare();
    if (this.mTransitionController.isShellTransitionsEnabled()
        && isVisible()
        && isVisibleRequested()) {
      if (getRequestedOverrideWindowingMode() == 0) {
        requestedOverrideWindowingMode = configuration.windowConfiguration.getWindowingMode();
      } else {
        requestedOverrideWindowingMode = getRequestedOverrideWindowingMode();
      }
      if (getWindowingMode() != requestedOverrideWindowingMode) {
        this.mTransitionController.collect(this);
      }
    }
    if (getCompatDisplayInsets() != null) {
      Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
      boolean z2 = requestedOverrideConfiguration.windowConfiguration.getRotation() != -1;
      int requestedConfigurationOrientation = getRequestedConfigurationOrientation();
      if (requestedConfigurationOrientation != 0
          && requestedConfigurationOrientation != getConfiguration().orientation
          && requestedConfigurationOrientation == getParent().getConfiguration().orientation
          && requestedOverrideConfiguration.windowConfiguration.getRotation()
              != getParent().getWindowConfiguration().getRotation()) {
        requestedOverrideConfiguration.windowConfiguration.setRotation(
            getParent().getWindowConfiguration().getRotation());
        onRequestedOverrideConfigurationChanged(requestedOverrideConfiguration);
        return;
      } else if (z2
          && requestedConfigurationOrientation == 0
          && requestedOverrideConfiguration.windowConfiguration.getRotation() != -1) {
        requestedOverrideConfiguration.windowConfiguration.setRotation(-1);
        onRequestedOverrideConfigurationChanged(requestedOverrideConfiguration);
        return;
      }
    }
    boolean inPinnedWindowingMode = inPinnedWindowingMode();
    DisplayContent displayContent = this.mDisplayContent;
    int activityType = getActivityType();
    if (inPinnedWindowingMode && attachedToProcess() && displayContent != null) {
      try {
        this.app.pauseConfigurationDispatch();
        super.onConfigurationChanged(configuration);
        if (this.mVisibleRequested
            && !inMultiWindowMode()
            && (rotationForActivityInDifferentOrientation =
                    displayContent.rotationForActivityInDifferentOrientation(this))
                != -1) {
          this.app.resumeConfigurationDispatch();
          displayContent.setFixedRotationLaunchingApp(
              this, rotationForActivityInDifferentOrientation);
        }
      } finally {
        if (this.app.resumeConfigurationDispatch()) {
          WindowProcessController windowProcessController = this.app;
          windowProcessController.dispatchConfiguration(windowProcessController.getConfiguration());
        }
      }
    } else {
      super.onConfigurationChanged(configuration);
    }
    if (activityType != 0 && activityType != getActivityType()) {
      String str =
          "Can't change activity type once set: "
              + this
              + " activityType="
              + WindowConfiguration.activityTypeToString(getActivityType())
              + ", was "
              + WindowConfiguration.activityTypeToString(activityType);
      if (Build.IS_DEBUGGABLE) {
        throw new IllegalStateException(str);
      }
      Slog.w("ActivityTaskManager", str);
    }
    if (getMergedOverrideConfiguration().seq != getResolvedOverrideConfiguration().seq) {
      onMergedOverrideConfigurationChanged();
    }
    if (!inPinnedWindowingMode && inPinnedWindowingMode() && this.task != null) {
      if (CoreRune.MW_PIP_SHELL_TRANSITION) {
        setHiddenWhileEnteringPinnedMode(false, "pip_entered");
      }
      this.mWaitForEnteringPinnedMode = false;
      ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
      Task task = this.task;
      activityTaskSupervisor.scheduleUpdatePictureInPictureModeIfNeeded(task, task.getBounds());
    }
    if (displayContent == null) {
      return;
    }
    if (this.mVisibleRequested) {
      displayContent.handleActivitySizeCompatModeIfNeeded(this);
      return;
    }
    if (getCompatDisplayInsets() == null || this.visibleIgnoringKeyguard) {
      return;
    }
    WindowProcessController windowProcessController2 = this.app;
    if (windowProcessController2 == null || !windowProcessController2.hasVisibleActivities()) {
      if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT
          && this.mCompatDisplayInsets.mWaitingForVisibleRequested) {
        return;
      }
      int currentOverrideConfigurationChanges =
          displayContent.getCurrentOverrideConfigurationChanges();
      if (hasResizeChange(currentOverrideConfigurationChanges)
          && (currentOverrideConfigurationChanges & 536872064) != 536872064) {
        z = true;
      }
      if (z || (currentOverrideConfigurationChanges & IInstalld.FLAG_USE_QUOTA) != 0) {
        restartProcessIfVisible();
      }
    }
  }

  public final boolean applyAspectRatio(Rect rect, Rect rect2, Rect rect3) {
    return applyAspectRatio(rect, rect2, rect3, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
  }

  /* JADX WARN: Code restructure failed: missing block: B:91:0x00f7, code lost:

     if (r15 == 2) goto L89;
  */
  /* JADX WARN: Code restructure failed: missing block: B:92:0x00fa, code lost:

     if (r6 < r7) goto L89;
  */
  /* JADX WARN: Removed duplicated region for block: B:95:0x0102  */
  /* JADX WARN: Removed duplicated region for block: B:96:0x0105  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean applyAspectRatio(Rect rect, Rect rect2, Rect rect3, float f) {
    boolean z;
    float f2;
    float f3;
    int i;
    int i2;
    if ((this.mPopOverState.isActivated() && true) || this.mCompatRecord.isFullScreen()) {
      return false;
    }
    BoundsCompatController boundsCompatController = this.mCompatRecord.mCandidateController;
    float maxAspectRatio = getMaxAspectRatio();
    Task rootTask = getRootTask();
    float minAspectRatio = getMinAspectRatio();
    TaskFragment organizedTaskFragment = getOrganizedTaskFragment();
    if (this.task == null
        || rootTask == null
        || (((rootTask.inMultiWindowMode() || rootTask.isFullscreenRootForStageTask())
                && !this.mCompatRecord.mWaitForChangingPinnedMode)
            || ((maxAspectRatio < 1.0f
                    && minAspectRatio < 1.0f
                    && f < 1.0f
                    && boundsCompatController == null)
                || isInVrUiMode(getConfiguration())
                || !(organizedTaskFragment == null || organizedTaskFragment.fillsParent())))) {
      return false;
    }
    Task task = this.task;
    if (task != null && task.isDexMode()) {
      return false;
    }
    int width = rect2.width();
    int height = rect2.height();
    float computeAspectRatio = computeAspectRatio(rect2);
    if (f < 1.0f) {
      f = computeAspectRatio;
    }
    if (maxAspectRatio >= 1.0f && f > maxAspectRatio) {
      minAspectRatio = maxAspectRatio;
    } else if (minAspectRatio < 1.0f || f >= minAspectRatio) {
      minAspectRatio = f;
    }
    if (boundsCompatController != null) {
      minAspectRatio = this.mCompatRecord.getDesiredAspectRatio();
    }
    if (computeAspectRatio - minAspectRatio > 0.005f) {
      if (CoreRune.FW_FIXED_ASPECT_RATIO_MODE
          && this.mCompatRecord.isFixedAspectRatioController(boundsCompatController)) {
        return false;
      }
      if (width < height) {
        f2 = width * minAspectRatio;
        i2 = (int) (f2 + 0.5f);
        i = width;
      } else {
        f3 = height * minAspectRatio;
        i = (int) (f3 + 0.5f);
        i2 = height;
      }
    } else if (minAspectRatio - computeAspectRatio > 0.005f
        || (CoreRune.FW_ORIENTATION_CONTROL
            && boundsCompatController != null
            && this.mCompatRecord.mIsTaskOrientationMismatched)) {
      int requestedConfigurationOrientation = getRequestedConfigurationOrientation();
      if (requestedConfigurationOrientation != 1) {
        if (requestedConfigurationOrientation != 2) {
          if (CoreRune.FW_ORIENTATION_CONTROL && this.mCompatRecord.mIsTaskOrientationMismatched) {
            int i3 = this.task.getConfiguration().orientation;
            if (i3 != 1) {}
          }
        }
        z = false;
        if (z) {
          f2 = width / minAspectRatio;
          i2 = (int) (f2 + 0.5f);
          i = width;
        } else {
          f3 = height / minAspectRatio;
          i = (int) (f3 + 0.5f);
          i2 = height;
        }
      }
      z = true;
      if (z) {}
    } else {
      i = width;
      i2 = height;
    }
    if (width <= i && height <= i2) {
      return false;
    }
    int i4 = rect2.left;
    int i5 = i + i4;
    if (i5 >= rect2.right) {
      i5 = rect3.right;
      i4 = rect3.left;
    }
    int i6 = rect2.top;
    int i7 = i2 + i6;
    if (i7 >= rect2.bottom) {
      i7 = rect3.bottom;
      i6 = rect3.top;
    }
    rect.set(i4, i6, i5, i7);
    BoundsCompatController boundsCompatController2 = boundsCompatController;
    if (boundsCompatController == null) {
      boundsCompatController2 = boundsCompatController;
      boundsCompatController2 = boundsCompatController;
      if (!this.task.inMultiWindowMode() && maxAspectRatio >= 1.0f) {
        CustomAspectRatioController customAspectRatioController =
            this.mAtmService.mExt.mCustomAspectRatioController;
        customAspectRatioController.onAspectRatioApplied(this, maxAspectRatio);
        boundsCompatController2 = customAspectRatioController;
      }
    }
    if (boundsCompatController2 != null) {
      this.mCompatRecord.onApplyAspectRatio(
          boundsCompatController2, computeAspectRatio, minAspectRatio, rect);
    }
    return true;
  }

  public float getMinAspectRatio() {
    if (this.mLetterboxUiController.hasInheritedLetterboxBehavior()) {
      return this.mLetterboxUiController.getInheritedMinAspectRatio();
    }
    ActivityInfo activityInfo = this.info;
    if (activityInfo.applicationInfo == null) {
      return activityInfo.getMinAspectRatio();
    }
    if (!this.mLetterboxUiController.shouldOverrideMinAspectRatio()) {
      return this.info.getMinAspectRatio();
    }
    if (this.info.isChangeEnabled(203647190L)
        && !ActivityInfo.isFixedOrientationPortrait(getOverrideOrientation())) {
      return this.info.getMinAspectRatio();
    }
    if (this.info.isChangeEnabled(218959984L) && isParentFullscreenPortrait()) {
      return this.info.getMinAspectRatio();
    }
    if (this.info.isChangeEnabled(208648326L)) {
      return Math.max(
          this.mLetterboxUiController.getSplitScreenAspectRatio(), this.info.getMinAspectRatio());
    }
    if (this.info.isChangeEnabled(180326787L)) {
      return Math.max(1.7777778f, this.info.getMinAspectRatio());
    }
    if (this.info.isChangeEnabled(180326845L)) {
      return Math.max(1.5f, this.info.getMinAspectRatio());
    }
    return this.info.getMinAspectRatio();
  }

  public final boolean isParentFullscreenPortrait() {
    WindowContainer parent = getParent();
    return parent != null
        && parent.getConfiguration().orientation == 1
        && parent.getWindowConfiguration().getWindowingMode() == 1;
  }

  public float getMaxAspectRatio() {
    if (this.mLetterboxUiController.hasInheritedLetterboxBehavior()) {
      return this.mLetterboxUiController.getInheritedMaxAspectRatio();
    }
    return this.info.getMaxAspectRatio();
  }

  public final boolean hasFixedAspectRatio() {
    return (getMaxAspectRatio() == DisplayPowerController2.RATE_FROM_DOZE_TO_ON
            && getMinAspectRatio() == DisplayPowerController2.RATE_FROM_DOZE_TO_ON)
        ? false
        : true;
  }

  public static float computeAspectRatio(Rect rect) {
    return (rect.width() == 0 || rect.height() == 0)
        ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON
        : Math.max(r0, r2) / Math.min(r0, r2);
  }

  public boolean shouldUpdateConfigForDisplayChanged() {
    this.mAtmService.isPackageEnabledForCoverLauncher(this.packageName, this.mUserId);
    return this.mLastReportedDisplayId != getDisplayId();
  }

  public boolean ensureActivityConfiguration(int i, boolean z) {
    return ensureActivityConfiguration(i, z, false, false);
  }

  public boolean ensureActivityConfiguration(int i, boolean z, boolean z2) {
    return ensureActivityConfiguration(i, z, z2, false);
  }

  public boolean ensureActivityConfiguration(int i, boolean z, boolean z2, boolean z3) {
    int i2;
    int i3;
    CompatDisplayInsets compatDisplayInsets;
    State state;
    if (getRootTask().mConfigWillChange) {
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            -804217032,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      return true;
    }
    if (this.finishing) {
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            -846078709,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      stopFreezingScreenLocked(false);
      return true;
    }
    if (isState(State.DESTROYED)) {
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            1105210816,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      return true;
    }
    if (!z2
        && ((state = this.mState) == State.STOPPING
            || state == State.STOPPED
            || !shouldBeVisible())) {
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            1635062046,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      return true;
    }
    if (WindowConfiguration.isSplitScreenWindowingMode(getWindowConfiguration())
        && this.mAtmService.mMultiTaskingController.isEnsureConfigDeferred()) {
      return true;
    }
    if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_CONFIGURATION,
          -1791031393,
          0,
          (String) null,
          new Object[] {String.valueOf(this)});
    }
    int displayId = getDisplayId();
    boolean z4 = this.mLastReportedDisplayId != displayId;
    if (z4) {
      this.mLastReportedDisplayId = displayId;
    }
    if (this.mVisibleRequested) {
      updateCompatDisplayInsets();
      if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT
          && (compatDisplayInsets = this.mCompatDisplayInsets) != null
          && compatDisplayInsets.mConfigChangeNeeded) {
        compatDisplayInsets.mConfigChangeNeeded = false;
        onConfigurationChanged(getParent().getConfiguration());
      }
    }
    this.mTmpConfig.setTo(this.mLastReportedConfiguration.getMergedConfiguration());
    if (getConfiguration().equals(this.mTmpConfig) && !this.forceNewConfig && !z4) {
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            -1115019498,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      return true;
    }
    int configurationChanges = getConfigurationChanges(this.mTmpConfig);
    Configuration processGlobalConfiguration = getProcessGlobalConfiguration();
    if (isDexMode()) {
      boolean shouldBeApplyDexCompatConfigurationLocked =
          this.mAtmService.mDexCompatController.shouldBeApplyDexCompatConfigurationLocked(
              this, this.mProcessAppInfo, getDisplayId());
      if (shouldBeApplyDexCompatConfigurationLocked) {
        Configuration configuration = new Configuration(processGlobalConfiguration);
        this.mAtmService.mDexCompatController.applyDexCompatConfigurationLocked(
            this, this.mProcessAppInfo, configuration, "ensureActivityConfig[" + this + "]");
      }
      Task task = this.task;
      if (task != null) {
        shouldBeApplyDexCompatConfigurationLocked = task.isDexCompatEnabled();
      }
      setDexCompatMode(shouldBeApplyDexCompatConfigurationLocked);
    }
    Configuration configuration2 = new Configuration(getMergedOverrideConfiguration());
    if (CoreRune.MW_SA_LOGGING && isVisible() && getTopFullscreenOpaqueWindow() != null) {
      WindowConfiguration windowConfiguration =
          this.mLastReportedConfiguration.getOverrideConfiguration().windowConfiguration;
      i3 = windowConfiguration.getWindowingMode();
      i2 = windowConfiguration.getStageType();
    } else {
      i2 = 0;
      i3 = 0;
    }
    setLastReportedConfiguration(getProcessGlobalConfiguration(), configuration2);
    if (this.mState == State.INITIALIZING) {
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            -235225312,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      return true;
    }
    if (configurationChanges == 0 && !this.forceNewConfig) {
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            -743431900,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      if (z4) {
        scheduleActivityMovedToDisplay(displayId, configuration2);
      } else {
        scheduleConfigurationChanged(configuration2);
        if (CoreRune.MW_SA_LOGGING && i3 != 0) {
          logForMultiWindowModeChanged(i3, i2);
        }
      }
      notifyDisplayCompatPolicyAboutConfigurationChange(
          this.mLastReportedConfiguration.getMergedConfiguration(), this.mTmpConfig);
      return true;
    }
    if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_CONFIGURATION,
          -929676529,
          0,
          (String) null,
          new Object[] {
            String.valueOf(this),
            String.valueOf(Configuration.configurationDiffToString(configurationChanges))
          });
    }
    if (!attachedToProcess()) {
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            1679569477,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      stopFreezingScreenLocked(false);
      this.forceNewConfig = false;
      return true;
    }
    if (ProtoLogCache.WM_FORCE_DEBUG_CONFIGURATION_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_FORCE_DEBUG_CONFIGURATION,
          -613102676,
          0,
          "Checking to restart %s: changed=0x%s, handles=0x%s, mLastReportedConfiguration=%s",
          new Object[] {
            String.valueOf(this.info.name),
            String.valueOf(Integer.toHexString(configurationChanges)),
            String.valueOf(Integer.toHexString(this.info.getRealConfigChanged())),
            String.valueOf(this.mLastReportedConfiguration)
          });
    }
    if (shouldRelaunchLocked(configurationChanges, this.mTmpConfig) || this.forceNewConfig) {
      this.configChangeFlags |= configurationChanges;
      startFreezingScreenLocked(i);
      this.forceNewConfig = false;
      boolean z5 = z & (isResizeOnlyChange(configurationChanges) && !this.mFreezingScreen);
      if (hasResizeChange((~this.info.getRealConfigChanged()) & configurationChanges)) {
        this.mRelaunchReason = this.task.isDragResizing() ? 2 : 1;
      } else {
        this.mRelaunchReason = 0;
      }
      if (z3) {
        this.mLetterboxUiController.setRelaunchingAfterRequestedOrientationChanged(true);
      }
      if (this.mState == State.PAUSING) {
        if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_CONFIGURATION,
              -90559682,
              0,
              (String) null,
              new Object[] {String.valueOf(this)});
        }
        this.deferRelaunchUntilPaused = true;
        this.preserveWindowOnDeferredRelaunch = z5;
        return true;
      }
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            736692676,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      if (!this.mVisibleRequested && ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STATES,
            -1558137010,
            0,
            (String) null,
            new Object[] {String.valueOf(this), String.valueOf(Debug.getCallers(4))});
      }
      relaunchActivityLocked(z5);
      if (CoreRune.MW_SA_LOGGING && i3 != 0) {
        logForMultiWindowModeChanged(i3, i2);
      }
      return false;
    }
    if (z4) {
      scheduleActivityMovedToDisplay(displayId, configuration2);
    } else {
      scheduleConfigurationChanged(configuration2);
    }
    notifyDisplayCompatPolicyAboutConfigurationChange(
        this.mLastReportedConfiguration.getMergedConfiguration(), this.mTmpConfig);
    stopFreezingScreenLocked(false);
    if (CoreRune.MW_SA_LOGGING && i3 != 0) {
      logForMultiWindowModeChanged(i3, i2);
    }
    return true;
  }

  public final void notifyDisplayCompatPolicyAboutConfigurationChange(
      Configuration configuration, Configuration configuration2) {
    if (this.mDisplayContent.mDisplayRotationCompatPolicy == null || !shouldBeResumed(null)) {
      return;
    }
    this.mDisplayContent.mDisplayRotationCompatPolicy.onActivityConfigurationChanging(
        this, configuration, configuration2);
  }

  public Configuration getProcessGlobalConfiguration() {
    WindowProcessController windowProcessController = this.app;
    return windowProcessController != null
        ? windowProcessController.getConfiguration()
        : this.mAtmService.getGlobalConfiguration();
  }

  public final boolean shouldRelaunchLocked(int i, Configuration configuration) {
    int realConfigChanged = this.info.getRealConfigChanged();
    boolean onlyVrUiModeChanged = onlyVrUiModeChanged(i, configuration);
    if (this.info.applicationInfo.targetSdkVersion < 26
        && this.requestedVrComponent != null
        && onlyVrUiModeChanged) {
      realConfigChanged |= 512;
    }
    if (this.mWmService.mSkipActivityRelaunchWhenDocking
        && onlyDeskInUiModeChanged(configuration)
        && !hasDeskResources()) {
      realConfigChanged |= 512;
    }
    if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY) {
      UiModeManagerInternal uiModeManagerInternal =
          (UiModeManagerInternal) LocalServices.getService(UiModeManagerInternal.class);
      if (onlyDeskInUiModeChanged(configuration)
          && !hasDeskResources()
          && uiModeManagerInternal != null
          && uiModeManagerInternal.toggleNewDexMode()) {
        realConfigChanged |= 512;
      }
    }
    return ((~realConfigChanged) & i) != 0;
  }

  public final boolean onlyVrUiModeChanged(int i, Configuration configuration) {
    return i == 512 && isInVrUiMode(getConfiguration()) != isInVrUiMode(configuration);
  }

  public final boolean onlyDeskInUiModeChanged(Configuration configuration) {
    Configuration configuration2 = getConfiguration();
    return (isInDeskUiMode(configuration2) != isInDeskUiMode(configuration))
        && !((configuration2.uiMode & (-16)) != (configuration.uiMode & (-16)));
  }

  /* JADX WARN: Code restructure failed: missing block: B:14:0x0031, code lost:

     r4.mHasDeskResources = java.lang.Boolean.TRUE;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean hasDeskResources() {
    Boolean bool = this.mHasDeskResources;
    if (bool != null) {
      return bool.booleanValue();
    }
    this.mHasDeskResources = Boolean.FALSE;
    try {
      int i = 0;
      Configuration[] sizeAndUiModeConfigurations =
          this.mAtmService
              .mContext
              .createPackageContextAsUser(this.packageName, 0, UserHandle.of(this.mUserId))
              .getResources()
              .getSizeAndUiModeConfigurations();
      int length = sizeAndUiModeConfigurations.length;
      while (true) {
        if (i >= length) {
          break;
        }
        if (isInDeskUiMode(sizeAndUiModeConfigurations[i])) {
          break;
        }
        i++;
      }
    } catch (PackageManager.NameNotFoundException e) {
      Slog.w(
          "ActivityTaskManager", "Exception thrown during checking for desk resources " + this, e);
    }
    return this.mHasDeskResources.booleanValue();
  }

  public final int getConfigurationChanges(Configuration configuration) {
    int filterDiff =
        SizeConfigurationBuckets.filterDiff(
            configuration.diff(getConfiguration()),
            configuration,
            getConfiguration(),
            this.mSizeConfigurations);
    return (536870912 & filterDiff) != 0 ? filterDiff & (-536870913) : filterDiff;
  }

  public void relaunchActivityLocked(boolean z) {
    ArrayList arrayList;
    ArrayList arrayList2;
    ResumeActivityItem obtain;
    WindowState windowState;
    if (this.mAtmService.mSuppressResizeConfigChanges && z) {
      this.configChangeFlags = 0;
      return;
    }
    if (!z) {
      InputTarget imeInputTarget = this.mDisplayContent.getImeInputTarget();
      this.mLastImeShown =
          (imeInputTarget == null
                  || imeInputTarget.getWindowState() == null
                  || imeInputTarget.getWindowState().mActivityRecord != this
                  || (windowState = this.mDisplayContent.mInputMethodWindow) == null
                  || !windowState.isVisible())
              ? false
              : true;
    }
    Task rootTask = getRootTask();
    if (rootTask != null && rootTask.mTranslucentActivityWaiting == this) {
      rootTask.checkTranslucentActivityWaiting(null);
    }
    boolean shouldBeResumed = shouldBeResumed(null);
    if (shouldBeResumed) {
      arrayList = this.results;
      arrayList2 = this.newIntents;
    } else {
      arrayList = null;
      arrayList2 = null;
    }
    if (shouldBeResumed) {
      EventLogTags.writeWmRelaunchResumeActivity(
          this.mUserId,
          System.identityHashCode(this),
          this.task.mTaskId,
          this.shortComponentName,
          Integer.toHexString(this.configChangeFlags));
    } else {
      EventLogTags.writeWmRelaunchActivity(
          this.mUserId,
          System.identityHashCode(this),
          this.task.mTaskId,
          this.shortComponentName,
          Integer.toHexString(this.configChangeFlags));
    }
    startFreezingScreenLocked(0);
    Configuration configuration = new Configuration(getProcessGlobalConfiguration());
    new Configuration(getMergedOverrideConfiguration());
    if (isDexMode()) {
      this.mAtmService.mDexCompatController.resolveDexCompatConfigurationLocked(
          this,
          this.mProcessAppInfo,
          getDisplayId(),
          configuration,
          "relaunchActivity[" + this + "]");
    }
    try {
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.i(
            ProtoLogGroup.WM_DEBUG_STATES,
            -1016578046,
            0,
            (String) null,
            new Object[] {
              shouldBeResumed ? "RESUMED" : "PAUSED",
              String.valueOf(this),
              String.valueOf(Debug.getCallers(6))
            });
      }
      this.forceNewConfig = false;
      startRelaunching();
      ActivityRelaunchItem obtain2 =
          ActivityRelaunchItem.obtain(
              arrayList,
              arrayList2,
              this.configChangeFlags,
              new MergedConfiguration(
                  getProcessGlobalConfiguration(), getMergedOverrideConfiguration()),
              z);
      if (shouldBeResumed) {
        obtain = ResumeActivityItem.obtain(isTransitionForward(), shouldSendCompatFakeFocus());
      } else {
        obtain = PauseActivityItem.obtain();
      }
      ClientTransaction obtain3 = ClientTransaction.obtain(this.app.getThread(), this.token);
      obtain3.addCallback(obtain2);
      obtain3.setLifecycleStateRequest(obtain);
      this.mAtmService.getLifecycleManager().scheduleTransaction(obtain3);
    } catch (RemoteException e) {
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.i(
            ProtoLogGroup.WM_DEBUG_STATES,
            -262984451,
            0,
            (String) null,
            new Object[] {String.valueOf(e)});
      }
    }
    if (shouldBeResumed) {
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_STATES,
            1270792394,
            0,
            (String) null,
            new Object[] {String.valueOf(this)});
      }
      this.results = null;
      this.newIntents = null;
      this.mAtmService.getAppWarningsLocked().onResumeActivity(this);
    } else {
      removePauseTimeout();
      setState(State.PAUSED, "relaunchActivityLocked");
    }
    this.mTaskSupervisor.mStoppingActivities.remove(this);
    this.configChangeFlags = 0;
    this.deferRelaunchUntilPaused = false;
    this.preserveWindowOnDeferredRelaunch = false;
  }

  public void restartProcessIfVisible() {
    if (this.finishing) {
      return;
    }
    Slog.i("ActivityTaskManager", "Request to restart process of " + this);
    clearSizeCompatMode();
    if (attachedToProcess()) {
      setState(State.RESTARTING_PROCESS, "restartActivityProcess");
      if (!this.mVisibleRequested || this.mHaveState) {
        this.mAtmService.f1734mH.post(
            new Runnable() { // from class:
                             // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda17
              @Override // java.lang.Runnable
              public final void run() {
                ActivityRecord.this.lambda$restartProcessIfVisible$22();
              }
            });
      } else if (this.mTransitionController.isShellTransitionsEnabled()) {
        final Transition transition =
            new Transition(5, 0, this.mTransitionController, this.mWmService.mSyncEngine);
        this.mTransitionController.startCollectOrQueue(
            transition,
            new TransitionController
                .OnStartCollect() { // from class:
                                    // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda18
              @Override // com.android.server.wm.TransitionController.OnStartCollect
              public final void onCollectStarted(boolean z) {
                ActivityRecord.this.lambda$restartProcessIfVisible$23(transition, z);
              }
            });
      } else {
        startFreezingScreen();
        scheduleStopForRestartProcess();
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$restartProcessIfVisible$22() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (hasProcess() && this.app.getReportedProcState() > 6) {
          WindowProcessController windowProcessController = this.app;
          WindowManagerService.resetPriorityAfterLockedSection();
          this.mAtmService.mAmInternal.killProcess(
              windowProcessController.mName, windowProcessController.mUid, "resetConfig");
          return;
        }
        WindowManagerService.resetPriorityAfterLockedSection();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$restartProcessIfVisible$23(Transition transition, boolean z) {
    if (this.mState != State.RESTARTING_PROCESS || !attachedToProcess()) {
      transition.abort();
      return;
    }
    setVisibleRequested(false);
    transition.collect(this);
    this.mTransitionController.requestStartTransition(transition, this.task, null, null);
    scheduleStopForRestartProcess();
  }

  public final void scheduleStopForRestartProcess() {
    try {
      this.mAtmService
          .getLifecycleManager()
          .scheduleTransaction(
              this.app.getThread(), this.token, (ActivityLifecycleItem) StopActivityItem.obtain(0));
    } catch (RemoteException e) {
      Slog.w("ActivityTaskManager", "Exception thrown during restart " + this, e);
    }
    this.mTaskSupervisor.scheduleRestartTimeout(this);
  }

  public boolean isProcessRunning() {
    WindowProcessController windowProcessController = this.app;
    if (windowProcessController == null) {
      windowProcessController =
          (WindowProcessController)
              this.mAtmService.mProcessNames.get(this.processName, this.info.applicationInfo.uid);
    }
    return windowProcessController != null && windowProcessController.hasThread();
  }

  public final boolean allowTaskSnapshot() {
    if (this.mDisableSnapshot) {
      return false;
    }
    ArrayList arrayList = this.newIntents;
    if (arrayList == null) {
      return true;
    }
    for (int size = arrayList.size() - 1; size >= 0; size--) {
      Intent intent = (Intent) this.newIntents.get(size);
      if (intent != null && !isMainIntent(intent)) {
        Intent intent2 = this.mLastNewIntent;
        if (!(intent2 != null ? intent2.filterEquals(intent) : this.intent.filterEquals(intent))
            || intent.getExtras() != null) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean isNoHistory() {
    return ((this.intent.getFlags() & 1073741824) == 0 && (this.info.flags & 128) == 0)
        ? false
        : true;
  }

  public void saveToXml(TypedXmlSerializer typedXmlSerializer) {
    typedXmlSerializer.attributeLong((String) null, "id", this.createTime);
    typedXmlSerializer.attributeInt((String) null, "launched_from_uid", this.launchedFromUid);
    String str = this.launchedFromPackage;
    if (str != null) {
      typedXmlSerializer.attribute((String) null, "launched_from_package", str);
    }
    String str2 = this.launchedFromFeatureId;
    if (str2 != null) {
      typedXmlSerializer.attribute((String) null, "launched_from_feature", str2);
    }
    String str3 = this.resolvedType;
    if (str3 != null) {
      typedXmlSerializer.attribute((String) null, "resolved_type", str3);
    }
    typedXmlSerializer.attributeBoolean(
        (String) null, "component_specified", this.componentSpecified);
    typedXmlSerializer.attributeInt((String) null, "user_id", this.mUserId);
    ActivityManager.TaskDescription taskDescription = this.taskDescription;
    if (taskDescription != null) {
      taskDescription.saveToXml(typedXmlSerializer);
    }
    typedXmlSerializer.startTag((String) null, KnoxCustomManagerService.INTENT);
    this.intent.saveToXml(typedXmlSerializer);
    typedXmlSerializer.endTag((String) null, KnoxCustomManagerService.INTENT);
    if (!isPersistable() || this.mPersistentState == null) {
      return;
    }
    typedXmlSerializer.startTag((String) null, "persistable_bundle");
    this.mPersistentState.saveToXml(typedXmlSerializer);
    typedXmlSerializer.endTag((String) null, "persistable_bundle");
  }

  public static ActivityRecord restoreFromXml(
      TypedXmlPullParser typedXmlPullParser, ActivityTaskSupervisor activityTaskSupervisor) {
    Intent intent = null;
    int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "launched_from_uid", 0);
    String attributeValue =
        typedXmlPullParser.getAttributeValue((String) null, "launched_from_package");
    String attributeValue2 =
        typedXmlPullParser.getAttributeValue((String) null, "launched_from_feature");
    String attributeValue3 = typedXmlPullParser.getAttributeValue((String) null, "resolved_type");
    boolean attributeBoolean =
        typedXmlPullParser.getAttributeBoolean((String) null, "component_specified", false);
    int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "user_id", 0);
    long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "id", -1L);
    int depth = typedXmlPullParser.getDepth();
    ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription();
    taskDescription.restoreFromXml(typedXmlPullParser);
    PersistableBundle persistableBundle = null;
    while (true) {
      int next = typedXmlPullParser.next();
      if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() < depth)) {
        break;
      }
      if (next == 2) {
        String name = typedXmlPullParser.getName();
        if (KnoxCustomManagerService.INTENT.equals(name)) {
          intent = Intent.restoreFromXml(typedXmlPullParser);
        } else if ("persistable_bundle".equals(name)) {
          persistableBundle = PersistableBundle.restoreFromXml(typedXmlPullParser);
        } else {
          Slog.w("ActivityTaskManager", "restoreActivity: unexpected name=" + name);
          com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
        }
      }
    }
    if (intent == null) {
      throw new XmlPullParserException("restoreActivity error intent=" + intent);
    }
    ActivityTaskManagerService activityTaskManagerService = activityTaskSupervisor.mService;
    PersistableBundle persistableBundle2 = persistableBundle;
    ActivityInfo resolveActivity =
        activityTaskSupervisor.resolveActivity(
            intent, attributeValue3, 0, null, attributeInt2, Binder.getCallingUid(), 0);
    if (resolveActivity == null) {
      throw new XmlPullParserException(
          "restoreActivity resolver error. Intent=" + intent + " resolvedType=" + attributeValue3);
    }
    return new Builder(activityTaskManagerService)
        .setLaunchedFromUid(attributeInt)
        .setLaunchedFromPackage(attributeValue)
        .setLaunchedFromFeature(attributeValue2)
        .setIntent(intent)
        .setResolvedType(attributeValue3)
        .setActivityInfo(resolveActivity)
        .setComponentSpecified(attributeBoolean)
        .setPersistentState(persistableBundle2)
        .setTaskDescription(taskDescription)
        .setCreateTime(attributeLong)
        .build();
  }

  public static boolean isInVrUiMode(Configuration configuration) {
    return (configuration.uiMode & 15) == 7;
  }

  public static boolean isInDeskUiMode(Configuration configuration) {
    return (configuration.uiMode & 15) == 2;
  }

  public String getProcessName() {
    return this.info.applicationInfo.processName;
  }

  public int getUid() {
    return this.info.applicationInfo.uid;
  }

  public boolean isUid(int i) {
    return this.info.applicationInfo.uid == i;
  }

  public int getPid() {
    WindowProcessController windowProcessController = this.app;
    if (windowProcessController != null) {
      return windowProcessController.getPid();
    }
    return 0;
  }

  public String getFilteredReferrer(String str) {
    if (CoreRune.SYSFW_APP_SPEG && "com.samsung.speg".equals(str)) {
      Slog.d("SPEG", "Passing referrer value");
      return str;
    }
    if (str == null) {
      return null;
    }
    if (str.equals(this.packageName)
        || !this.mWmService.mPmInternal.filterAppAccess(
            str, this.info.applicationInfo.uid, this.mUserId)) {
      return str;
    }
    return null;
  }

  public boolean canTurnScreenOn() {
    if (!getTurnScreenOnFlag()) {
      return false;
    }
    Task rootTask = getRootTask();
    if (!this.mCurrentLaunchCanTurnScreenOn || rootTask == null) {
      return false;
    }
    return !this.mTaskSupervisor.getKeyguardController().isKeyguardShowing(getDisplayId())
        || this.mTaskSupervisor.getKeyguardController().checkKeyguardVisibility(this);
  }

  public void setTurnScreenOn(boolean z) {
    this.mTurnScreenOn = z;
  }

  public void setAllowCrossUidActivitySwitchFromBelow(boolean z) {
    this.mAllowCrossUidActivitySwitchFromBelow = z;
  }

  public Pair allowCrossUidActivitySwitchFromBelow(int i) {
    int i2 = this.info.applicationInfo.uid;
    if (i == i2) {
      Boolean bool = Boolean.TRUE;
      return new Pair(bool, bool);
    }
    if (this.mAllowCrossUidActivitySwitchFromBelow) {
      Boolean bool2 = Boolean.TRUE;
      return new Pair(bool2, bool2);
    }
    return new Pair(
        Boolean.valueOf(
            !(ActivitySecurityModelFeatureFlags.shouldRestrictActivitySwitch(i2)
                && ActivitySecurityModelFeatureFlags.shouldRestrictActivitySwitch(i))),
        Boolean.FALSE);
  }

  public boolean getTurnScreenOnFlag() {
    return this.mTurnScreenOn || containsTurnScreenOnWindow();
  }

  public final boolean containsTurnScreenOnWindow() {
    if (isRelaunching()) {
      return this.mLastContainsTurnScreenOnWindow;
    }
    for (int size = this.mChildren.size() - 1; size >= 0; size--) {
      if ((((WindowState) this.mChildren.get(size)).mAttrs.flags & 2097152) != 0) {
        return true;
      }
    }
    return false;
  }

  public boolean canResumeByCompat() {
    WindowProcessController windowProcessController = this.app;
    return windowProcessController == null
        || windowProcessController.updateTopResumingActivityInProcessIfNeeded(this);
  }

  public boolean isTopRunningActivity() {
    return this.mRootWindowContainer.topRunningActivity() == this;
  }

  public boolean isFocusedActivityOnDisplay() {
    return this.mDisplayContent.forAllTaskDisplayAreas(
        new Predicate() { // from class:
                          // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda19
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$isFocusedActivityOnDisplay$24;
            lambda$isFocusedActivityOnDisplay$24 =
                ActivityRecord.this.lambda$isFocusedActivityOnDisplay$24((TaskDisplayArea) obj);
            return lambda$isFocusedActivityOnDisplay$24;
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$isFocusedActivityOnDisplay$24(
      TaskDisplayArea taskDisplayArea) {
    return taskDisplayArea.getFocusedActivity() == this;
  }

  public boolean isRootOfTask() {
    Task task = this.task;
    return task != null && this == task.getRootActivity(true);
  }

  public void setTaskOverlay(boolean z) {
    this.mTaskOverlay = z;
    setAlwaysOnTop(z);
  }

  public boolean isTaskOverlay() {
    return this.mTaskOverlay;
  }

  @Override // com.android.server.wm.ConfigurationContainer
  public boolean isAlwaysOnTop() {
    if (CoreRune.FW_APPLOCK
        && this.mIsAppLockExceptionActivity
        && this.mNotToBeTopForAppLockException) {
      return false;
    }
    return this.mTaskOverlay || super.isAlwaysOnTop();
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean showToCurrentUser() {
    return this.mShowForAllUsers || this.mWmService.isUserVisible(this.mUserId);
  }

  public void setDexCompatMode(boolean z) {
    if (this.mIsDexCompatEnabled != z) {
      this.mIsDexCompatEnabled = z;
      if (DexCompatController.DEBUG_DEX_COMPAT) {
        Slog.d(StartingSurfaceController.TAG, "[DexCompat] setDexCompatMode: " + z);
      }
    }
  }

  @Override // com.android.server.wm.WindowToken
  public String toString() {
    if (this.stringName != null) {
      StringBuilder sb = new StringBuilder();
      sb.append(this.stringName);
      sb.append(" t");
      Task task = this.task;
      sb.append(task == null ? -1 : task.mTaskId);
      sb.append(this.finishing ? " f}" : "");
      sb.append(this.mIsExiting ? " isExiting" : "");
      sb.append("}");
      return sb.toString();
    }
    StringBuilder sb2 = new StringBuilder(128);
    sb2.append("ActivityRecord{");
    sb2.append(Integer.toHexString(System.identityHashCode(this)));
    sb2.append(" u");
    sb2.append(this.mUserId);
    sb2.append(' ');
    sb2.append(this.intent.getComponent().flattenToShortString());
    String sb3 = sb2.toString();
    this.stringName = sb3;
    return sb3;
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, int i) {
    writeNameToProto(protoOutputStream, 1138166333441L);
    super.dumpDebug(protoOutputStream, 1146756268034L, i);
    protoOutputStream.write(1133871366147L, this.mLastSurfaceShowing);
    protoOutputStream.write(1133871366148L, isWaitingForTransitionStart());
    protoOutputStream.write(1133871366149L, isAnimating(7, 17));
    WindowContainerThumbnail windowContainerThumbnail = this.mThumbnail;
    if (windowContainerThumbnail != null) {
      windowContainerThumbnail.dumpDebug(protoOutputStream, 1146756268038L);
    }
    protoOutputStream.write(1133871366151L, fillsParent());
    protoOutputStream.write(1133871366152L, this.mAppStopped);
    protoOutputStream.write(1133871366174L, !occludesParent());
    protoOutputStream.write(1133871366168L, this.mVisible);
    protoOutputStream.write(1133871366153L, this.mVisibleRequested);
    protoOutputStream.write(1133871366154L, isClientVisible());
    protoOutputStream.write(1133871366155L, this.mDeferHidingClient);
    protoOutputStream.write(1133871366156L, this.mReportedDrawn);
    protoOutputStream.write(1133871366157L, this.reportedVisible);
    protoOutputStream.write(1120986464270L, this.mNumInterestingWindows);
    protoOutputStream.write(1120986464271L, this.mNumDrawnWindows);
    protoOutputStream.write(1133871366160L, this.allDrawn);
    protoOutputStream.write(1133871366161L, this.mLastAllDrawn);
    WindowState windowState = this.mStartingWindow;
    if (windowState != null) {
      windowState.writeIdentifierToProto(protoOutputStream, 1146756268051L);
    }
    protoOutputStream.write(1133871366164L, isStartingWindowDisplayed());
    protoOutputStream.write(1133871366345L, this.startingMoved);
    protoOutputStream.write(1133871366166L, this.mVisibleSetFromTransferredStartingWindow);
    protoOutputStream.write(1138166333467L, this.mState.toString());
    protoOutputStream.write(1133871366172L, isRootOfTask());
    if (hasProcess()) {
      protoOutputStream.write(1120986464285L, this.app.getPid());
    }
    protoOutputStream.write(1133871366175L, this.pictureInPictureArgs.isAutoEnterEnabled());
    protoOutputStream.write(1133871366176L, inSizeCompatMode());
    protoOutputStream.write(1108101562401L, getMinAspectRatio());
    protoOutputStream.write(1133871366178L, providesMaxBounds());
    protoOutputStream.write(1133871366179L, this.mEnableRecentsScreenshot);
    protoOutputStream.write(1120986464292L, this.mLastDropInputMode);
    protoOutputStream.write(1120986464293L, getOverrideOrientation());
    protoOutputStream.write(1133871366182L, shouldSendCompatFakeFocus());
    protoOutputStream.write(
        1133871366183L, this.mLetterboxUiController.shouldForceRotateForCameraCompat());
    protoOutputStream.write(
        1133871366184L, this.mLetterboxUiController.shouldRefreshActivityForCameraCompat());
    protoOutputStream.write(
        1133871366185L, this.mLetterboxUiController.shouldRefreshActivityViaPauseForCameraCompat());
  }

  @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer,
            // com.android.server.wm.ConfigurationContainer
  public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
    if (i != 2 || isVisible()) {
      long start = protoOutputStream.start(j);
      dumpDebug(protoOutputStream, i);
      protoOutputStream.end(start);
    }
  }

  public void writeNameToProto(ProtoOutputStream protoOutputStream, long j) {
    protoOutputStream.write(j, this.shortComponentName);
  }

  @Override // com.android.server.wm.WindowContainer
  public void writeIdentifierToProto(ProtoOutputStream protoOutputStream, long j) {
    long start = protoOutputStream.start(j);
    protoOutputStream.write(1120986464257L, System.identityHashCode(this));
    protoOutputStream.write(1120986464258L, this.mUserId);
    protoOutputStream.write(1138166333443L, this.intent.getComponent().flattenToShortString());
    protoOutputStream.end(start);
  }

  public class CompatDisplayInsets {
    public boolean mCanRotationCompatMode;
    public boolean mConfigChangeNeeded;
    public boolean mCreatedByRotationCompat;
    public boolean mDisplayDeviceTypeChanged;
    public int mDisplayId;
    public final int mHeight;
    public final boolean mIsFloating;
    public final boolean mIsInFixedOrientationLetterbox;
    public final int mOriginalRequestedOrientation;
    public final int mOriginalRotation;
    public boolean mWaitingForVisibleRequested;
    public final int mWidth;
    public final Rect[] mNonDecorInsets = new Rect[4];
    public final Rect[] mStableInsets = new Rect[4];
    public final Configuration mOverrideConfig = new Configuration();
    public int mParentScreenLayout = 0;

    public CompatDisplayInsets(
        DisplayContent displayContent, ActivityRecord activityRecord, Rect rect) {
      int rotation;
      this.mDisplayId = 0;
      this.mDisplayId = displayContent.mDisplayId;
      if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT) {
        BoundsCompatRecord boundsCompatRecord = activityRecord.mCompatRecord;
        if (boundsCompatRecord.mCanRotationCompatMode) {
          this.mCreatedByRotationCompat = true;
          this.mCanRotationCompatMode = true;
          this.mConfigChangeNeeded = boundsCompatRecord.mIsTaskOrientationMismatched;
          this.mWaitingForVisibleRequested = !activityRecord.mVisibleRequested;
        }
      }
      this.mOriginalRotation = displayContent.getRotation();
      boolean tasksAreFloating = activityRecord.getWindowConfiguration().tasksAreFloating();
      this.mIsFloating = tasksAreFloating;
      this.mOriginalRequestedOrientation = activityRecord.getRequestedConfigurationOrientation();
      if (tasksAreFloating) {
        Rect bounds = activityRecord.getWindowConfiguration().getBounds();
        this.mWidth = bounds.width();
        this.mHeight = bounds.height();
        Rect rect2 = new Rect();
        for (int i = 0; i < 4; i++) {
          this.mNonDecorInsets[i] = rect2;
          this.mStableInsets[i] = rect2;
        }
        this.mIsInFixedOrientationLetterbox = false;
        return;
      }
      Task task = activityRecord.getTask();
      boolean z = rect != null;
      this.mIsInFixedOrientationLetterbox = z;
      rect = z ? rect : task != null ? task.getBounds() : displayContent.getBounds();
      if (task != null) {
        rotation = task.getConfiguration().windowConfiguration.getRotation();
      } else {
        rotation = displayContent.getConfiguration().windowConfiguration.getRotation();
      }
      Point rotationZeroDimensions = getRotationZeroDimensions(rect, rotation);
      this.mWidth = rotationZeroDimensions.x;
      this.mHeight = rotationZeroDimensions.y;
      Rect rect3 = rect.equals(displayContent.getBounds()) ? null : new Rect();
      DisplayPolicy displayPolicy = displayContent.getDisplayPolicy();
      int i2 = 0;
      while (i2 < 4) {
        this.mNonDecorInsets[i2] = new Rect();
        this.mStableInsets[i2] = new Rect();
        boolean z2 = i2 == 1 || i2 == 3;
        int i3 = z2 ? displayContent.mBaseDisplayHeight : displayContent.mBaseDisplayWidth;
        int i4 = z2 ? displayContent.mBaseDisplayWidth : displayContent.mBaseDisplayHeight;
        DisplayPolicy.DecorInsets.Info decorInsetsInfo =
            displayPolicy.getDecorInsetsInfo(i2, i3, i4);
        this.mNonDecorInsets[i2].set(decorInsetsInfo.mNonDecorInsets);
        this.mStableInsets[i2].set(decorInsetsInfo.mConfigInsets);
        if (rect3 != null) {
          rect3.set(rect);
          displayContent.rotateBounds(rotation, i2, rect3);
          updateInsetsForBounds(rect3, i3, i4, this.mNonDecorInsets[i2]);
          updateInsetsForBounds(rect3, i3, i4, this.mStableInsets[i2]);
        }
        i2++;
      }
    }

    public static Point getRotationZeroDimensions(Rect rect, int i) {
      boolean z = true;
      if (i != 1 && i != 3) {
        z = false;
      }
      int width = rect.width();
      int height = rect.height();
      return z ? new Point(height, width) : new Point(width, height);
    }

    public static void updateInsetsForBounds(Rect rect, int i, int i2, Rect rect2) {
      rect2.left = Math.max(0, rect2.left - rect.left);
      rect2.top = Math.max(0, rect2.top - rect.top);
      rect2.right = Math.max(0, (rect.right - i) + rect2.right);
      rect2.bottom = Math.max(0, (rect.bottom - i2) + rect2.bottom);
    }

    public void getBoundsByRotation(Rect rect, int i) {
      boolean z = true;
      if (i != 1 && i != 3) {
        z = false;
      }
      rect.set(0, 0, z ? this.mHeight : this.mWidth, z ? this.mWidth : this.mHeight);
    }

    public void getFrameByOrientation(Rect rect, int i) {
      int max = Math.max(this.mWidth, this.mHeight);
      int min = Math.min(this.mWidth, this.mHeight);
      boolean z = i == 2;
      int i2 = z ? max : min;
      if (z) {
        max = min;
      }
      rect.set(0, 0, i2, max);
    }

    public void getContainerBounds(Rect rect, Rect rect2, int i, int i2, boolean z, boolean z2) {
      getFrameByOrientation(rect2, i2);
      if (this.mIsFloating) {
        rect.set(rect2);
        return;
      }
      getBoundsByRotation(rect, i);
      int width = rect.width();
      int height = rect.height();
      boolean z3 = (rect2.width() > rect2.height()) != (width > height);
      if (z3 && z2 && z) {
        if (i2 == 2) {
          float f = width;
          rect2.bottom = (int) ((f * f) / height);
          rect2.right = width;
        } else {
          rect2.bottom = height;
          float f2 = height;
          rect2.right = (int) ((f2 * f2) / width);
        }
        rect2.offset(ActivityRecord.getCenterOffset(this.mWidth, rect2.width()), 0);
      }
      rect.set(rect2);
      if (z3) {
        Rect rect3 = this.mNonDecorInsets[i];
        rect2.offset(rect3.left, rect3.top);
        rect.offset(rect3.left, rect3.top);
      } else if (i != -1) {
        TaskFragment.intersectWithInsetsIfFits(rect, rect2, this.mNonDecorInsets[i]);
      }
    }

    public void dump(PrintWriter printWriter, String str) {
      String str2 = str + "  ";
      printWriter.print(str);
      printWriter.print("CompatDisplayInsets:");
      printWriter.print(" mWidth=" + this.mWidth);
      printWriter.print(", mHeight=" + this.mHeight);
      printWriter.print(", mDisplayId=" + this.mDisplayId);
      if (this.mIsFloating) {
        printWriter.print(", mIsFloating=true");
      }
      if (this.mIsInFixedOrientationLetterbox) {
        printWriter.print(", mIsInFixedOrientationLetterbox=true");
      }
      printWriter.println();
      if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT) {
        if (this.mCreatedByRotationCompat
            || this.mCanRotationCompatMode
            || this.mConfigChangeNeeded
            || this.mWaitingForVisibleRequested) {
          printWriter.print(str2);
          printWriter.print("mCreatedByRotationCompat=");
          printWriter.print(this.mCreatedByRotationCompat);
          printWriter.print(", mCanRotationCompatMode=");
          printWriter.print(this.mCanRotationCompatMode);
          if (this.mConfigChangeNeeded) {
            printWriter.print(", mConfigChangeNeeded=true");
          }
          if (this.mWaitingForVisibleRequested) {
            printWriter.print(", mWaitingForVisibleRequested=true");
          }
          printWriter.println();
        }
      }
    }
  }

  public final void resetSizeCompatModeIfNeeded() {
    if (this.mCompatDisplayInsets == null || this.task == null || !shouldResetSizeCompatMode()) {
      return;
    }
    clearSizeCompatMode(true, true);
  }

  public final boolean shouldResetSizeCompatMode() {
    if (BoundsCompatUtils.isSupportsBoundsCompat()
        && this.mAtmService.mExt.mAvoidCompatDisplayInsets
        && !this.task.inMultiWindowMode()) {
      return true;
    }
    if (isEmbedded() && !getTaskFragment().getResolvedOverrideBounds().isEmpty()) {
      return true;
    }
    if (CoreRune.FW_ORIENTATION_CONTROL
        && OrientationController.isEnabled(this.task)
        && !this.task.inMultiWindowMode()) {
      return (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT
              && this.mCompatRecord.mCanRotationCompatMode)
          ? false
          : true;
    }
    return false;
  }

  public Rect getSizeCompatBounds() {
    return this.mSizeCompatBounds;
  }

  public final void resolveSizeCompatBounds(Rect rect, Rect rect2, Configuration configuration) {
    boolean canHaveSizeCompatBounds =
        this.mCompatRecord.getController().canHaveSizeCompatBounds(this);
    this.mInSizeCompatModeForBounds = canHaveSizeCompatBounds;
    float f = this.mSizeCompatScale;
    if (canHaveSizeCompatBounds) {
      int i =
          this.mCompatDisplayInsets
              .mStableInsets[configuration.windowConfiguration.getRotation()]
              .top;
      int width = rect.width();
      int height = rect.height();
      int width2 = rect2.width();
      int height2 = rect2.height() - i;
      this.mSizeCompatScale =
          (width > width2 || height > height2) ? Math.min(width2 / width, height2 / height) : 1.0f;
    } else {
      this.mSizeCompatScale = 1.0f;
    }
    if (this.mSizeCompatScale != 1.0f) {
      if (this.mSizeCompatBounds == null) {
        this.mSizeCompatBounds = new Rect();
      }
      this.mSizeCompatBounds.set(rect);
      this.mSizeCompatBounds.offsetTo(0, 0);
      this.mSizeCompatBounds.scale(this.mSizeCompatScale);
    } else {
      this.mSizeCompatBounds = null;
    }
    if (this.mSizeCompatScale != f) {
      forAllWindows((Consumer) new ActivityRecord$$ExternalSyntheticLambda6(), false);
    }
  }

  public boolean isDisplayCompatModeAvailable() {
    Task task;
    return (this.mIgnoreDisplayCompatMode
            || (task = this.task) == null
            || task.mCompatDisplayInsets == null)
        ? false
        : true;
  }

  public boolean isDisplayCompatModeEnabled() {
    return isDisplayCompatModeAvailable()
        && this.task.mCompatDisplayInsets.mDisplayDeviceTypeChanged;
  }

  public class AppSaturationInfo {
    public float[] mMatrix;
    public float[] mTranslation;

    public AppSaturationInfo() {
      this.mMatrix = new float[9];
      this.mTranslation = new float[3];
    }

    public void setSaturation(float[] fArr, float[] fArr2) {
      float[] fArr3 = this.mMatrix;
      System.arraycopy(fArr, 0, fArr3, 0, fArr3.length);
      float[] fArr4 = this.mTranslation;
      System.arraycopy(fArr2, 0, fArr4, 0, fArr4.length);
    }
  }

  @Override // com.android.server.wm.WindowContainer
  public RemoteAnimationTarget createRemoteAnimationTarget(
      RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord) {
    WindowState findMainWindow = findMainWindow();
    if (this.task == null || findMainWindow == null) {
      return null;
    }
    boolean z = false;
    Rect rect =
        findMainWindow
            .getInsetsStateWithVisibilityOverride()
            .calculateInsets(this.task.getBounds(), WindowInsets.Type.systemBars(), false)
            .toRect();
    InsetUtils.addInsets(rect, getLetterboxInsets());
    int i = this.task.mTaskId;
    int mode = remoteAnimationRecord.getMode();
    SurfaceControl surfaceControl = remoteAnimationRecord.mAdapter.mCapturedLeash;
    boolean z2 =
        !fillsParent()
            || (CoreRune.FW_CUSTOM_LETTERBOX
                && CustomLetterboxConfiguration.hasWallpaperBackgroundForLetterbox(this)
                && remoteAnimationRecord.getMode() == 0);
    Rect rect2 = new Rect();
    int prefixOrderIndex = getPrefixOrderIndex();
    RemoteAnimationController.RemoteAnimationAdapterWrapper remoteAnimationAdapterWrapper =
        remoteAnimationRecord.mAdapter;
    Point point = remoteAnimationAdapterWrapper.mPosition;
    Rect rect3 = remoteAnimationAdapterWrapper.mLocalBounds;
    Rect rect4 = remoteAnimationAdapterWrapper.mEndBounds;
    WindowConfiguration windowConfiguration = this.task.getWindowConfiguration();
    RemoteAnimationController.RemoteAnimationAdapterWrapper remoteAnimationAdapterWrapper2 =
        remoteAnimationRecord.mThumbnailAdapter;
    RemoteAnimationTarget remoteAnimationTarget =
        new RemoteAnimationTarget(
            i,
            mode,
            surfaceControl,
            z2,
            rect2,
            rect,
            prefixOrderIndex,
            point,
            rect3,
            rect4,
            windowConfiguration,
            false,
            remoteAnimationAdapterWrapper2 != null
                ? remoteAnimationAdapterWrapper2.mCapturedLeash
                : null,
            remoteAnimationRecord.mStartBounds,
            this.task.getTaskInfo(),
            checkEnterPictureInPictureAppOpsState());
    remoteAnimationTarget.setShowBackdrop(remoteAnimationRecord.mShowBackdrop);
    StartingData startingData = this.mStartingData;
    if (startingData != null && startingData.hasImeSurface()) {
      z = true;
    }
    remoteAnimationTarget.setWillShowImeOnTarget(z);
    remoteAnimationTarget.hasAnimatingParent = remoteAnimationRecord.hasAnimatingParent();
    return remoteAnimationTarget;
  }

  @Override // com.android.server.wm.WindowContainer
  public void getAnimationFrames(Rect rect, Rect rect2, Rect rect3, Rect rect4) {
    WindowState findMainWindow = findMainWindow();
    if (findMainWindow == null) {
      return;
    }
    findMainWindow.getAnimationFrames(rect, rect2, rect3, rect4);
  }

  public void setPictureInPictureParams(PictureInPictureParams pictureInPictureParams) {
    this.pictureInPictureArgs.copyOnlySet(pictureInPictureParams);
    adjustPictureInPictureParamsIfNeeded(getBounds());
    getTask().getRootTask().onPictureInPictureParamsChanged();
  }

  public void setShouldDockBigOverlays(boolean z) {
    this.shouldDockBigOverlays = z;
    getTask().getRootTask().onShouldDockBigOverlaysChanged();
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean isSyncFinished(BLASTSyncEngine.SyncGroup syncGroup) {
    if (!super.isSyncFinished(syncGroup)) {
      return false;
    }
    DisplayContent displayContent = this.mDisplayContent;
    if (displayContent != null
        && displayContent.mUnknownAppVisibilityController.isVisibilityUnknown(this)) {
      return false;
    }
    if (!isVisibleRequested()) {
      return true;
    }
    if (this.mPendingRelaunchCount > 0 || !isAttached()) {
      return false;
    }
    if (CoreRune.MW_SHELL_CHANGE_TRANSITION
        && this.mAtmService.mChangeTransitController.isSyncDeferredForAllDrawn(this)) {
      if (CoreRune.SAFE_DEBUG) {
        Slog.d("ActivityTaskManager", "isSyncFinished: syncDeferredForAllDrawn, r=" + this);
      }
      return false;
    }
    for (int size = this.mChildren.size() - 1; size >= 0; size--) {
      if (((WindowState) this.mChildren.get(size)).isVisibleRequested()
          || ignoreCheckSyncFinishWithWindow((WindowState) this.mChildren.get(size))) {
        return true;
      }
    }
    return false;
  }

  @Override // com.android.server.wm.WindowContainer
  public void finishSync(
      SurfaceControl.Transaction transaction, BLASTSyncEngine.SyncGroup syncGroup, boolean z) {
    if (getSyncGroup() == null || syncGroup == getSyncGroup()) {
      this.mLastAllReadyAtSync = allSyncFinished();
      super.finishSync(transaction, syncGroup, z);
    }
  }

  public Point getMinDimensions() {
    ActivityInfo.WindowLayout windowLayout = this.info.windowLayout;
    if (windowLayout == null) {
      return null;
    }
    return new Point(windowLayout.minWidth, windowLayout.minHeight);
  }

  public final void adjustPictureInPictureParamsIfNeeded(Rect rect) {
    PictureInPictureParams pictureInPictureParams = this.pictureInPictureArgs;
    if (pictureInPictureParams == null || !pictureInPictureParams.hasSourceBoundsHint()) {
      return;
    }
    if (this.mCompatRecord.isCompatModeEnabled()) {
      this.pictureInPictureArgs.getSourceRectHint().setEmpty();
    } else {
      this.pictureInPictureArgs.getSourceRectHint().offset(rect.left, rect.top);
    }
  }

  public final void applyLocaleOverrideIfNeeded(Configuration configuration) {
    ActivityTaskManagerInternal.PackageConfig findPackageConfiguration;
    LocaleList localeList;
    ComponentName componentName;
    Task task;
    boolean z = false;
    if (isEmbedded() || ((task = this.task) != null && task.mAlignActivityLocaleWithTask)) {
      Task task2 = this.task;
      if (task2 != null
          && (componentName = task2.realActivity) != null
          && !componentName.getPackageName().equals(this.packageName)) {
        z = true;
      }
      if (!z
          || (findPackageConfiguration =
                  this.mAtmService.mPackageConfigPersister.findPackageConfiguration(
                      this.task.realActivity.getPackageName(), this.mUserId))
              == null
          || (localeList = findPackageConfiguration.mLocales) == null
          || localeList.isEmpty()) {
        return;
      }
      configuration.setLocales(findPackageConfiguration.mLocales);
    }
  }

  public boolean shouldSendCompatFakeFocus() {
    return this.mLetterboxUiController.shouldSendFakeFocus()
        && inMultiWindowMode()
        && !inPinnedWindowingMode()
        && !inFreeformWindowingMode();
  }

  public boolean canCaptureSnapshot() {
    if (!isSurfaceShowing() || findMainWindow() == null || this.mPopOverState.isActivated()) {
      return false;
    }
    return forAllWindows(
        new ToBooleanFunction() { // from class:
                                  // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda2
          public final boolean apply(Object obj) {
            boolean lambda$canCaptureSnapshot$25;
            lambda$canCaptureSnapshot$25 =
                ActivityRecord.lambda$canCaptureSnapshot$25((WindowState) obj);
            return lambda$canCaptureSnapshot$25;
          }
        },
        true);
  }

  public static /* synthetic */ boolean lambda$canCaptureSnapshot$25(WindowState windowState) {
    WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
    return windowStateAnimator != null
        && windowStateAnimator.getShown()
        && windowState.mWinAnimator.mLastAlpha > DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  }

  public void overrideCustomTransition(boolean z, int i, int i2, int i3) {
    CustomAppTransition customAnimation = getCustomAnimation(z);
    if (customAnimation == null) {
      customAnimation = new CustomAppTransition();
      if (z) {
        this.mCustomOpenTransition = customAnimation;
      } else {
        this.mCustomCloseTransition = customAnimation;
      }
    }
    customAnimation.mEnterAnim = i;
    customAnimation.mExitAnim = i2;
    customAnimation.mBackgroundColor = i3;
  }

  public void clearCustomTransition(boolean z) {
    if (z) {
      this.mCustomOpenTransition = null;
    } else {
      this.mCustomCloseTransition = null;
    }
  }

  public CustomAppTransition getCustomAnimation(boolean z) {
    return z ? this.mCustomOpenTransition : this.mCustomCloseTransition;
  }

  public class Builder {
    public ActivityInfo mActivityInfo;
    public final ActivityTaskManagerService mAtmService;
    public WindowProcessController mCallerApp;
    public boolean mComponentSpecified;
    public Configuration mConfiguration;
    public long mCreateTime;
    public Intent mIntent;
    public String mLaunchedFromFeature;
    public String mLaunchedFromPackage;
    public int mLaunchedFromPid;
    public int mLaunchedFromUid;
    public ActivityOptions mOptions;
    public PersistableBundle mPersistentState;
    public int mRequestCode;
    public String mResolvedType;
    public ActivityRecord mResultTo;
    public String mResultWho;
    public boolean mRootVoiceInteraction;
    public ActivityRecord mSourceRecord;
    public ActivityManager.TaskDescription mTaskDescription;

    public Builder(ActivityTaskManagerService activityTaskManagerService) {
      this.mAtmService = activityTaskManagerService;
    }

    public Builder setCaller(WindowProcessController windowProcessController) {
      this.mCallerApp = windowProcessController;
      return this;
    }

    public Builder setLaunchedFromPid(int i) {
      this.mLaunchedFromPid = i;
      return this;
    }

    public Builder setLaunchedFromUid(int i) {
      this.mLaunchedFromUid = i;
      return this;
    }

    public Builder setLaunchedFromPackage(String str) {
      this.mLaunchedFromPackage = str;
      return this;
    }

    public Builder setLaunchedFromFeature(String str) {
      this.mLaunchedFromFeature = str;
      return this;
    }

    public Builder setIntent(Intent intent) {
      this.mIntent = intent;
      return this;
    }

    public Builder setResolvedType(String str) {
      this.mResolvedType = str;
      return this;
    }

    public Builder setActivityInfo(ActivityInfo activityInfo) {
      this.mActivityInfo = activityInfo;
      return this;
    }

    public Builder setResultTo(ActivityRecord activityRecord) {
      this.mResultTo = activityRecord;
      return this;
    }

    public Builder setResultWho(String str) {
      this.mResultWho = str;
      return this;
    }

    public Builder setRequestCode(int i) {
      this.mRequestCode = i;
      return this;
    }

    public Builder setComponentSpecified(boolean z) {
      this.mComponentSpecified = z;
      return this;
    }

    public Builder setRootVoiceInteraction(boolean z) {
      this.mRootVoiceInteraction = z;
      return this;
    }

    public Builder setActivityOptions(ActivityOptions activityOptions) {
      this.mOptions = activityOptions;
      return this;
    }

    public Builder setConfiguration(Configuration configuration) {
      this.mConfiguration = configuration;
      return this;
    }

    public Builder setSourceRecord(ActivityRecord activityRecord) {
      this.mSourceRecord = activityRecord;
      return this;
    }

    public final Builder setPersistentState(PersistableBundle persistableBundle) {
      this.mPersistentState = persistableBundle;
      return this;
    }

    public final Builder setTaskDescription(ActivityManager.TaskDescription taskDescription) {
      this.mTaskDescription = taskDescription;
      return this;
    }

    public final Builder setCreateTime(long j) {
      this.mCreateTime = j;
      return this;
    }

    public ActivityRecord build() {
      if (this.mConfiguration == null) {
        this.mConfiguration = this.mAtmService.getConfiguration();
      }
      ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
      return new ActivityRecord(
          activityTaskManagerService,
          this.mCallerApp,
          this.mLaunchedFromPid,
          this.mLaunchedFromUid,
          this.mLaunchedFromPackage,
          this.mLaunchedFromFeature,
          this.mIntent,
          this.mResolvedType,
          this.mActivityInfo,
          this.mConfiguration,
          this.mResultTo,
          this.mResultWho,
          this.mRequestCode,
          this.mComponentSpecified,
          this.mRootVoiceInteraction,
          activityTaskManagerService.mTaskSupervisor,
          this.mOptions,
          this.mSourceRecord,
          this.mPersistentState,
          this.mTaskDescription,
          this.mCreateTime);
    }
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean allowEdgeExtension() {
    if ((getDisplayContent().getDisplayPolicy().mExt.isNavigationGestureMode()
            && !this.mWmService.mExt.mPolicyExt.isNavGetureHintEnabled())
        || this.mCompatRecord.isCompatModeEnabled()) {
      return false;
    }
    if ((CoreRune.MW_SPLIT_SHELL_TRANSITION && inSplitScreenWindowingMode())
        || getMergedOverrideConfiguration().dexCompatEnabled == 2
        || !occludesParent(true)) {
      return false;
    }
    return hasStartingWindow() || hasOpaqueMainWindow();
  }

  public final boolean hasOpaqueMainWindow() {
    WindowState findMainWindow = findMainWindow();
    return (findMainWindow == null || PixelFormat.formatHasAlpha(findMainWindow.getAttrs().format))
        ? false
        : true;
  }

  public boolean hasTransientBarShowingDelay() {
    return this.mTransientBarShowingDelayMillis >= 0;
  }

  public int getTransientBarShowingDelayMillis() {
    return this.mTransientBarShowingDelayMillis;
  }

  public boolean isHiddenWhileEnteringPinnedMode() {
    return this.mHiddenWhileEnteringPinnedMode;
  }

  public void setHiddenWhileEnteringPinnedMode(boolean z, String str) {
    if (this.mHiddenWhileEnteringPinnedMode == z) {
      return;
    }
    Slog.d(
        "ActivityTaskManager", "setHiddenWhileEnteringPinnedMode: " + z + ", " + str + ", " + this);
    this.mHiddenWhileEnteringPinnedMode = z;
    this.mAtmService.f1734mH.removeCallbacks(this.mHiddenWhileEnteringPinnedTimeoutRunnable);
    if (z) {
      this.mAtmService.f1734mH.postDelayed(this.mHiddenWhileEnteringPinnedTimeoutRunnable, 3000L);
    }
    if (this.mSurfaceControl != null) {
      SurfaceControl.Transaction transaction =
          (SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get();
      transaction.setAlpha(
          this.mSurfaceControl, z ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : 1.0f);
      transaction.apply();
    }
  }

  public void clearWaitForEnteringPinnedMode(String str) {
    if (this.mWaitForEnteringPinnedMode) {
      this.mWaitForEnteringPinnedMode = false;
      setWindowingMode(0);
      Slog.d(
          "ActivityTaskManager", "clearWaitForEnteringPinnedMode: r=" + this + ", reason=" + str);
    }
  }

  public final boolean isSnapshotSizeCompatible(TaskSnapshot taskSnapshot) {
    int width = this.task.getBounds().width();
    int height = this.task.getBounds().height();
    Point taskSize = taskSnapshot.getTaskSize();
    int i = taskSize.x;
    return (width == i && height == taskSize.y) || (width == taskSize.y && height == i);
  }

  public String getClassName() {
    ActivityInfo activityInfo = this.info;
    return activityInfo != null ? activityInfo.name : "";
  }

  public void setActivityReparentToEmbeddedTask(boolean z) {
    this.mIsActivityReparentToEmbeddedTask = z;
  }

  public boolean isActivityReparentToEmbeddedTask() {
    return this.mIsActivityReparentToEmbeddedTask;
  }

  public boolean isFullscreenEmbedded() {
    return isEmbedded() && getTaskFragment().matchParentBounds();
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean isSplitEmbedded() {
    return isEmbedded() && !getTaskFragment().matchParentBounds();
  }

  public boolean isLaunchAdjacent() {
    Intent intent = this.intent;
    return intent != null && (intent.getFlags() & 268439552) == 268439552;
  }

  public boolean isLaunchRequestedFromNotification() {
    return this.mLaunchingRequestedFromNotification;
  }

  public void setLaunchingRequestFromNotification(boolean z) {
    this.mLaunchingRequestedFromNotification = z;
  }

  public final void logForMultiWindowModeChanged(int i, int i2) {
    if (CoreRune.MW_SA_LOGGING) {
      WindowConfiguration windowConfiguration =
          this.mLastReportedConfiguration.getOverrideConfiguration().windowConfiguration;
      MultiWindowUtils.logForMultiWindowModeChange(
          i, windowConfiguration.getWindowingMode(), i2, windowConfiguration.getStageType());
    }
  }

  public final boolean ignoreCheckSyncFinishWithWindow(WindowState windowState) {
    if (windowState == null) {
      return false;
    }
    return !windowState.showToCurrentUser() || windowState.isHiddenByViewCover();
  }

  public boolean hasVisibleEmptySizeMainWindow() {
    WindowState findMainWindow = findMainWindow();
    return findMainWindow != null
        && findMainWindow.isVisible()
        && findMainWindow.getFrame().isEmpty();
  }
}
