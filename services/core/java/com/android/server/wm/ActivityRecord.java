package com.android.server.wm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.app.IApplicationThread;
import android.app.IScreenCaptureObserver;
import android.app.PictureInPictureParams;
import android.app.WindowConfiguration;
import android.app.servertransaction.ActivityConfigurationChangeItem;
import android.app.servertransaction.DestroyActivityItem;
import android.app.servertransaction.MoveToDisplayItem;
import android.app.servertransaction.PauseActivityItem;
import android.app.servertransaction.StartActivityItem;
import android.app.servertransaction.StopActivityItem;
import android.app.servertransaction.TopResumedActivityChangeItem;
import android.app.servertransaction.TransferSplashScreenViewStateItem;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Intent;
import android.content.LocusId;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConstrainDisplayApisConfig;
import android.content.res.Configuration;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.InputConstants;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.service.voice.IVoiceInteractionSession;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.MergedConfiguration;
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
import android.window.ActivityWindowInfo;
import android.window.DisplayWindowPolicyController;
import android.window.ITaskFragmentOrganizer;
import android.window.ITaskOrganizer;
import android.window.RemoteTransition;
import android.window.SizeConfigurationBuckets;
import android.window.SplashScreenView;
import android.window.TaskSnapshot;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import com.android.internal.R;
import com.android.internal.app.ResolverActivity;
import com.android.internal.os.TransferPipe;
import com.android.internal.policy.AttributeCache;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import com.android.server.am.AppTimeTracker;
import com.android.server.am.BroadcastStats$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentRecord;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;
import com.android.server.display.color.ColorDisplayService;
import com.android.server.media.MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0;
import com.android.server.uri.GrantUri;
import com.android.server.uri.NeededUriGrants;
import com.android.server.uri.UriGrantsManagerService;
import com.android.server.uri.UriPermissionOwner;
import com.android.server.wm.ActivityCallerState;
import com.android.server.wm.ActivityMetricsLogger;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityTaskSupervisor;
import com.android.server.wm.AppCompatLetterboxPolicy;
import com.android.server.wm.AppCompatOrientationOverrides;
import com.android.server.wm.BLASTSyncEngine;
import com.android.server.wm.DisplayContent;
import com.android.server.wm.RemoteAnimationController;
import com.android.server.wm.SizeCompatPolicyManager;
import com.android.server.wm.StartingSurfaceController;
import com.android.server.wm.Task;
import com.android.server.wm.TaskFragment;
import com.android.server.wm.TaskPersister;
import com.android.server.wm.TransitionController;
import com.android.server.wm.WindowState;
import com.android.server.wm.utils.InsetUtils;
import com.android.server.wm.utils.OptPropFactory;
import com.android.window.flags.Flags;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.feature.SemGateConfig;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
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
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ActivityRecord extends WindowToken {
    public static ConstrainDisplayApisConfig sConstrainDisplayApisConfig;
    public boolean allDrawn;
    public WindowProcessController app;
    public AppTimeTracker appTimeTracker;
    public final Binder assistToken;
    public final boolean componentSpecified;
    public final long createTime;
    public boolean delayedResume;
    public boolean finishing;
    public boolean firstWindowDrawn;
    public boolean hasBeenLaunched;
    public boolean idle;
    public boolean immersive;
    public volatile boolean inHistory;
    public final ActivityInfo info;
    public final IBinder initialCallerInfoAccessToken;
    public final Intent intent;
    public boolean keysPaused;
    public long lastLaunchTime;
    public long lastVisibleTime;
    public int launchCount;
    public boolean launchFailed;
    public final int launchMode;
    public long launchTickTime;
    public final String launchedFromFeatureId;
    public final String launchedFromPackage;
    public final int launchedFromPid;
    public final int launchedFromUid;
    public final int lockTaskLaunchMode;
    public final ComponentName mActivityComponent;
    public final ActivityRecordInputSink mActivityRecordInputSink;
    public boolean mActivityRecordInputSinkEnabled;
    public final boolean mAliasChild;
    public boolean mAllowCrossUidActivitySwitchFromBelow;
    public final boolean mAllowUntrustedEmbeddingStateSharing;
    public int mAllowedTouchUid;
    public AnimatingActivityRegistry mAnimatingActivityRegistry;
    public final boolean mAppActivityEmbeddingSplitsEnabled;
    public final AppCompatController mAppCompatController;
    public boolean mAppStopped;
    public final ActivityTaskManagerService mAtmService;
    public boolean mAutoEnteringPip;
    public final ActivityCallerState mCallerState;
    public RemoteCallbackList mCaptureCallbacks;
    public boolean mClientVisibilityDeferred;
    public final ColorDisplayService.ColorTransformController mColorTransformController;
    public CompatSandboxPolicy mCompatSandboxPolicy;
    public int mConfigurationSeq;
    public boolean mCurrentLaunchCanTurnScreenOn;
    public CustomAppTransition mCustomCloseTransition;
    public CustomAppTransition mCustomOpenTransition;
    public boolean mDeferHidingClient;
    public final AnonymousClass1 mDestroyTimeoutRunnable;
    public final boolean mDisableSnapshot;
    public boolean mDismissKeyguardIfInsecure;
    public boolean mEnableRecentsScreenshot;
    public boolean mEnteringAnimation;
    public boolean mForceSendResultForMediaProjection;
    public boolean mFreezingScreen;
    public boolean mHandleExitSplashScreen;
    int mHandoverLaunchDisplayId;
    TaskDisplayArea mHandoverTaskDisplayArea;
    public Boolean mHasDeskResources;
    public final boolean mHasSceneTransition;
    public boolean mHaveState;
    public boolean mHiddenWhileEnteringPinnedMode;
    public final AnonymousClass1 mHiddenWhileEnteringPinnedTimeoutRunnable;
    public Bundle mIcicle;
    public boolean mIgnoreDevSettingForNonResizable;
    public boolean mImeInsetsFrozenUntilStartInput;
    public boolean mInheritShownWhenLocked;
    public InputApplicationHandle mInputApplicationHandle;
    public long mInputDispatchingTimeoutMillis;
    public boolean mIsActivityReparentToEmbeddedTask;
    public boolean mIsAliasActivity;
    public final boolean mIsAllowedSeamlessRotation;
    public boolean mIsAppLockExceptionActivity;
    public boolean mIsEligibleForFixedOrientationLetterbox;
    public boolean mIsEnteringPipFromSplit;
    public boolean mIsExiting;
    public final boolean mIsFlexPanel;
    public boolean mIsInputDroppedForAnimation;
    public final boolean mIsUnhandledDropLaunch;
    public final boolean mIsUserAlwaysVisible;
    public final boolean mKeepSnapshotCache;
    public boolean mLastAllDrawn;
    public boolean mLastAllReadyAtSync;
    public AppSaturationInfo mLastAppSaturationInfo;
    public boolean mLastContainsDismissKeyguardWindow;
    public boolean mLastContainsShowWhenLockedWindow;
    public boolean mLastContainsTurnScreenOnWindow;
    public boolean mLastDeferHidingClient;
    public int mLastDropInputMode;
    public IBinder mLastEmbeddedParentTfTokenBeforePip;
    public boolean mLastImeShown;
    public Intent mLastNewIntent;
    public Task mLastParentBeforePip;
    public final ActivityWindowInfo mLastReportedActivityWindowInfo;
    public final MergedConfiguration mLastReportedConfiguration;
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
    public final AnonymousClass1 mLaunchTickRunnable;
    public boolean mLaunchedFromBubble;
    public boolean mLaunchingRequestedFromNotification;
    public LocusId mLocusId;
    public boolean mNeedsLetterboxedAnimation;
    public final boolean mNotToBeTopForAppLockException;
    public int mNumDrawnWindows;
    public int mNumInterestingWindows;
    public boolean mOccludesParent;
    public final boolean mOptInOnBackInvoked;
    public final boolean mOptOutEdgeToEdge;
    public boolean mOverrideTaskTransition;
    public int mPauseConfigurationDispatchCount;
    public boolean mPauseSchedulePendingForPip;
    public final AnonymousClass1 mPauseTimeoutRunnable;
    public ActivityOptions mPendingOptions;
    public int mPendingRelaunchCount;
    public RemoteAnimationAdapter mPendingRemoteAnimation;
    public RemoteTransition mPendingRemoteTransition;
    public PersistableBundle mPersistentState;
    public final PopOverState mPopOverState;
    public ApplicationInfo mProcessAppInfo;
    public int mProcessAppLaunchPolicy;
    public int mRelaunchReason;
    public long mRelaunchStartTime;
    public RemoteAnimationDefinition mRemoteAnimationDefinition;
    public boolean mRemovingFromDisplay;
    public boolean mReportedDrawn;
    public final WindowState.UpdateReportedVisibilityResults mReportedVisibilityResults;
    public boolean mRequestForceTransition;
    public boolean mRequestFreeformForceHiding;
    public IBinder mRequestedLaunchingTaskFragmentToken;
    final TaskFragment.ConfigOverrideHint mResolveConfigHint;
    public int mResolvedConfigChangeFlags;
    public final RootWindowContainer mRootWindowContainer;
    public final int mRotationAnimationHint;
    public ActivityServiceConnectionsHolder mServiceConnectionsHolder;
    public final boolean mShareIdentity;
    public boolean mShouldShowPackageNightModeDialog;
    public final boolean mShowForAllUsers;
    public boolean mShowWhenLocked;
    public SizeCompatAttributes mSizeCompatAttributes;
    public SizeConfigurationBuckets mSizeConfigurations;
    public boolean mSplashScreenStyleSolidColor;
    public StartingData mStartingData;
    public StartingSurfaceController.StartingSurface mStartingSurface;
    public WindowState mStartingWindow;
    public State mState;
    public final AnonymousClass1 mStopTimeoutRunnable;
    public final boolean mStyleFillsParent;
    public final boolean mStyleFloating;
    public final int mTargetSdk;
    public boolean mTaskOverlay;
    public final ActivityTaskSupervisor mTaskSupervisor;
    public final ActivityWindowInfo mTmpActivityWindowInfo;
    public final Rect mTmpBounds;
    public final Configuration mTmpConfig;
    public final AnonymousClass1 mTransferSplashScreenTimeoutRunnable;
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
    public boolean nowVisible;
    public final String packageName;
    public long pauseTime;
    public HashSet pendingResults;
    public boolean pendingVoiceInteractionStart;
    public final PictureInPictureParams pictureInPictureArgs;
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
    public ActivityManager.TaskDescription taskDescription;
    public final int theme;
    public long topResumedStateLossTime;
    public UriPermissionOwner uriPermissions;
    public boolean visibleIgnoringKeyguard;
    public IVoiceInteractionSession voiceSession;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppSaturationInfo {
        public float[] mMatrix;
        public float[] mTranslation;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CustomAppTransition {
        public int mBackgroundColor;
        public int mEnterAnim;
        public int mExitAnim;
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class State {
        public static final /* synthetic */ State[] $VALUES;
        public static final State DESTROYED;
        public static final State DESTROYING;
        public static final State FINISHING;
        public static final State INITIALIZING;
        public static final State PAUSED;
        public static final State PAUSING;
        public static final State RESTARTING_PROCESS;
        public static final State RESUMED;
        public static final State STARTED;
        public static final State STOPPED;
        public static final State STOPPING;

        static {
            State state = new State("INITIALIZING", 0);
            INITIALIZING = state;
            State state2 = new State("STARTED", 1);
            STARTED = state2;
            State state3 = new State("RESUMED", 2);
            RESUMED = state3;
            State state4 = new State("PAUSING", 3);
            PAUSING = state4;
            State state5 = new State("PAUSED", 4);
            PAUSED = state5;
            State state6 = new State("STOPPING", 5);
            STOPPING = state6;
            State state7 = new State("STOPPED", 6);
            STOPPED = state7;
            State state8 = new State("FINISHING", 7);
            FINISHING = state8;
            State state9 = new State("DESTROYING", 8);
            DESTROYING = state9;
            State state10 = new State("DESTROYED", 9);
            DESTROYED = state10;
            State state11 = new State("RESTARTING_PROCESS", 10);
            RESTARTING_PROCESS = state11;
            $VALUES = new State[]{state, state2, state3, state4, state5, state6, state7, state8, state9, state10, state11};
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Token extends Binder {
        public WeakReference mActivityRef;

        public final String toString() {
            return "Token{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.mActivityRef.get() + "}";
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:207:0x0722, code lost:
    
        if (r1.getBoolean("com.samsung.android.disableSnapshot", false) == false) goto L308;
     */
    /* JADX WARN: Removed duplicated region for block: B:206:0x071b  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x072e  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0725  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x06e6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0228  */
    /* JADX WARN: Type inference failed for: r5v10, types: [com.android.server.wm.ActivityRecord$1] */
    /* JADX WARN: Type inference failed for: r5v11, types: [com.android.server.wm.ActivityRecord$1] */
    /* JADX WARN: Type inference failed for: r5v12, types: [com.android.server.wm.ActivityRecord$1] */
    /* JADX WARN: Type inference failed for: r5v13, types: [com.android.server.wm.ActivityRecord$1] */
    /* JADX WARN: Type inference failed for: r5v8, types: [com.android.server.wm.ActivityRecord$1] */
    /* JADX WARN: Type inference failed for: r5v9, types: [com.android.server.wm.ActivityRecord$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ActivityRecord(com.android.server.wm.ActivityTaskManagerService r23, com.android.server.wm.WindowProcessController r24, int r25, int r26, java.lang.String r27, java.lang.String r28, android.content.Intent r29, java.lang.String r30, android.content.pm.ActivityInfo r31, android.content.res.Configuration r32, com.android.server.wm.ActivityRecord r33, java.lang.String r34, int r35, boolean r36, boolean r37, com.android.server.wm.ActivityTaskSupervisor r38, android.app.ActivityOptions r39, com.android.server.wm.ActivityRecord r40, android.os.PersistableBundle r41, android.app.ActivityManager.TaskDescription r42, long r43) {
        /*
            Method dump skipped, instructions count: 1861
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.<init>(com.android.server.wm.ActivityTaskManagerService, com.android.server.wm.WindowProcessController, int, int, java.lang.String, java.lang.String, android.content.Intent, java.lang.String, android.content.pm.ActivityInfo, android.content.res.Configuration, com.android.server.wm.ActivityRecord, java.lang.String, int, boolean, boolean, com.android.server.wm.ActivityTaskSupervisor, android.app.ActivityOptions, com.android.server.wm.ActivityRecord, android.os.PersistableBundle, android.app.ActivityManager$TaskDescription, long):void");
    }

    public static void activityResumedLocked(IBinder iBinder, boolean z) {
        Integer num;
        ActivityRecord forTokenLocked = forTokenLocked(iBinder);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[2]) {
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_STATES, 1734586111478674085L, 0, null, String.valueOf(forTokenLocked));
        }
        if (forTokenLocked == null) {
            return;
        }
        if (forTokenLocked.mHandleExitSplashScreen != z) {
            forTokenLocked.mHandleExitSplashScreen = z;
        }
        forTokenLocked.mIcicle = null;
        forTokenLocked.mHaveState = false;
        if (forTokenLocked.findMainWindow(false) == null) {
            forTokenLocked.mDisplayContent.mUnknownAppVisibilityController.appRemovedOrHidden(forTokenLocked);
        }
        forTokenLocked.mDisplayContent.handleActivitySizeCompatModeIfNeeded(forTokenLocked);
        UnknownAppVisibilityController unknownAppVisibilityController = forTokenLocked.mDisplayContent.mUnknownAppVisibilityController;
        if (unknownAppVisibilityController.mUnknownApps.isEmpty() || (num = (Integer) unknownAppVisibilityController.mUnknownApps.get(forTokenLocked)) == null || num.intValue() != 1) {
            return;
        }
        WindowState findMainWindow = forTokenLocked.findMainWindow(false);
        if (findMainWindow == null || findMainWindow.mViewVisibility != 0) {
            unknownAppVisibilityController.mUnknownApps.put(forTokenLocked, 2);
            return;
        }
        Slog.d("WindowManager", "notifyAppResumedFinished, activity has already visible window, activity=" + forTokenLocked);
        unknownAppVisibilityController.mUnknownApps.put(forTokenLocked, 3);
        unknownAppVisibilityController.mDisplayContent.notifyKeyguardFlagsChanged();
        unknownAppVisibilityController.notifyVisibilitiesUpdated();
    }

    public static boolean canShowWhenLocked(ActivityRecord activityRecord) {
        ActivityRecord activityBelow;
        if (activityRecord == null || activityRecord.getTaskFragment() == null) {
            return false;
        }
        if (!activityRecord.inPinnedWindowingMode() && (activityRecord.mShowWhenLocked || activityRecord.containsShowWhenLockedWindow() || activityRecord.mIsUserAlwaysVisible)) {
            return true;
        }
        if (!activityRecord.mInheritShownWhenLocked || (activityBelow = activityRecord.getTaskFragment().getActivityBelow(activityRecord)) == null || activityBelow.inPinnedWindowingMode()) {
            return false;
        }
        return activityBelow.mShowWhenLocked || activityBelow.containsShowWhenLockedWindow() || activityBelow.mIsUserAlwaysVisible;
    }

    public static String computeTaskAffinity(int i, String str) {
        String num = Integer.toString(i);
        return (str == null || str.startsWith(num)) ? str : AnyMotionDetector$$ExternalSyntheticOutline0.m(num, ":", str);
    }

    public static void dumpActivity(FileDescriptor fileDescriptor, PrintWriter printWriter, int i, ActivityRecord activityRecord, String str, String str2, boolean z, boolean z2, boolean z3, String str3, boolean z4, Runnable runnable, Task task) {
        if (str3 == null || str3.equals(activityRecord.packageName)) {
            boolean z5 = !z2 && (z || !activityRecord.inHistory);
            if (z4) {
                printWriter.println("");
            }
            if (runnable != null) {
                runnable.run();
            }
            String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  ");
            String[] strArr = new String[0];
            Task task2 = activityRecord.task;
            if (task != task2) {
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
                activityRecord.dump(printWriter, m$1, true);
            } else if (z) {
                printWriter.print(m$1);
                printWriter.println(activityRecord.intent.toInsecureString());
                if (activityRecord.app != null) {
                    printWriter.print(m$1);
                    printWriter.println(activityRecord.app);
                }
            }
            if (z3 && activityRecord.attachedToProcess()) {
                printWriter.flush();
                try {
                    TransferPipe transferPipe = new TransferPipe();
                    try {
                        activityRecord.app.mThread.dumpActivity(transferPipe.getWriteFd(), activityRecord.token, m$1, strArr);
                        transferPipe.go(fileDescriptor, 2000L);
                        transferPipe.kill();
                    } catch (Throwable th) {
                        transferPipe.kill();
                        throw th;
                    }
                } catch (RemoteException unused) {
                    printWriter.println(m$1 + "Got a RemoteException while dumping the activity");
                } catch (IOException e) {
                    printWriter.println(m$1 + "Failure while dumping the activity: " + e);
                }
            }
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

    public static int getLockTaskLaunchMode(ActivityInfo activityInfo, ActivityOptions activityOptions) {
        int i = activityInfo.lockTaskLaunchMode;
        if (!activityInfo.applicationInfo.isPrivilegedApp() && (i == 2 || i == 1)) {
            i = 0;
        }
        if (activityOptions != null && activityOptions.getLockTaskMode() && i == 0) {
            return 3;
        }
        return i;
    }

    public static boolean isHomeIntent(Intent intent) {
        return "android.intent.action.MAIN".equals(intent.getAction()) && (intent.hasCategory("android.intent.category.HOME") || intent.hasCategory("android.intent.category.SECONDARY_HOME")) && intent.getCategories().size() == 1 && intent.getData() == null && intent.getType() == null;
    }

    public static ActivityRecord isInAnyTask(IBinder iBinder) {
        ActivityRecord forTokenLocked = forTokenLocked(iBinder);
        if (forTokenLocked == null || !forTokenLocked.isAttached()) {
            return null;
        }
        return forTokenLocked;
    }

    public static ActivityRecord isInRootTaskLocked(IBinder iBinder) {
        ActivityRecord forTokenLocked = forTokenLocked(iBinder);
        if (forTokenLocked == null) {
            return null;
        }
        Task rootTask = forTokenLocked.getRootTask();
        rootTask.getClass();
        if (!forTokenLocked.isDescendantOf(rootTask)) {
            forTokenLocked = null;
        }
        return forTokenLocked;
    }

    public static boolean isMainIntent(Intent intent) {
        return "android.intent.action.MAIN".equals(intent.getAction()) && intent.hasCategory("android.intent.category.LAUNCHER") && intent.getCategories().size() == 1 && intent.getData() == null && intent.getType() == null;
    }

    public final void abortAndClearOptionsAnimation() {
        ActivityOptions activityOptions = this.mPendingOptions;
        if (activityOptions != null) {
            activityOptions.abort();
        }
        this.mPendingOptions = null;
        this.mPendingRemoteAnimation = null;
        this.mPendingRemoteTransition = null;
    }

    public final void activityPaused(boolean z) {
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled;
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 1256300416726217367L, 12, null, String.valueOf(this.token), Boolean.valueOf(z));
        }
        TaskFragment taskFragment = getTaskFragment();
        if (taskFragment != null) {
            this.mAtmService.mH.removeCallbacks(this.mPauseTimeoutRunnable);
            ActivityRecord activityRecord = taskFragment.mPausingActivity;
            if (activityRecord == this) {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 6879640870754727133L, 0, null, String.valueOf(this), z ? "(due to timeout)" : " (pause complete)");
                }
                this.mAtmService.deferWindowLayout();
                try {
                    taskFragment.completePause(true, null);
                    return;
                } finally {
                    this.mAtmService.continueWindowLayout();
                }
            }
            EventLog.writeEvent(30012, Integer.valueOf(this.mUserId), Integer.valueOf(System.identityHashCode(this)), this.shortComponentName, activityRecord != null ? activityRecord.shortComponentName : "(none)");
            if (isState(State.PAUSING)) {
                setState(State.PAUSED, "activityPausedLocked");
                if (this.finishing) {
                    if (zArr[1]) {
                        ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 2737811012914917932L, 0, null, String.valueOf(this));
                    }
                    completeFinishing("activityPausedLocked", true);
                }
            }
        }
        this.mDisplayContent.handleActivitySizeCompatModeIfNeeded(this);
        this.mRootWindowContainer.ensureActivitiesVisible();
    }

    public final void activityStopped(Bundle bundle, PersistableBundle persistableBundle, CharSequence charSequence) {
        this.mAtmService.mH.removeCallbacks(this.mStopTimeoutRunnable);
        State state = this.mState;
        boolean z = state == State.STOPPING;
        if (!z && state != State.RESTARTING_PROCESS) {
            Slog.i("ActivityTaskManager", "Activity reported stop, but no longer stopping: " + this + " " + this.mState);
            return;
        }
        if (persistableBundle != null) {
            this.mPersistentState = persistableBundle;
            this.mAtmService.notifyTaskPersisterLocked(this.task, false);
        }
        if (bundle != null) {
            this.mIcicle = bundle;
            this.mHaveState = true;
            this.launchCount = 0;
            this.task.lastDescription = charSequence;
        }
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled;
        if (zArr[2]) {
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_STATES, -4913512058893421188L, 0, null, String.valueOf(this), String.valueOf(this.mIcicle));
        }
        if (z) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 7613353074402340933L, 0, null, String.valueOf(this));
            }
            setState(State.STOPPED, "activityStopped");
        }
        this.mAppStopped = true;
        this.firstWindowDrawn = false;
        Task task = this.task;
        if (task.mLastRecentsAnimationTransaction != null) {
            if (task.mLastRecentsAnimationOverlay != null) {
                task.getPendingTransaction().remove(task.mLastRecentsAnimationOverlay);
            }
            task.mLastRecentsAnimationTransaction = null;
            task.mLastRecentsAnimationOverlay = null;
            task.resetSurfaceControlTransforms();
        }
        if (this.mClientVisible) {
            setClientVisible(false);
        }
        destroySurfaces(false);
        removeStartingWindow();
        if (this.finishing) {
            abortAndClearOptionsAnimation();
        } else {
            ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
            activityTaskManagerService.getClass();
            if (this.app != null && activityTaskManagerService.mTopApp != null && this.app != activityTaskManagerService.mTopApp && this.lastVisibleTime > activityTaskManagerService.mPreviousProcessVisibleTime && this.app != activityTaskManagerService.mHomeProcess) {
                activityTaskManagerService.mPreviousProcess = this.app;
                activityTaskManagerService.mPreviousProcessVisibleTime = this.lastVisibleTime;
            }
        }
        this.mTaskSupervisor.checkReadyForSleepLocked(true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:172:0x0108, code lost:
    
        if (r8 == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x010d, code lost:
    
        if (r8 == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x00da, code lost:
    
        if (isSplitEmbedded() != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x00e5, code lost:
    
        if (r3 != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0080, code lost:
    
        if (r17.mDisplayContent.mDisplayRotation.rotationForOrientation(getOverrideOrientation(), r17.mDisplayContent.mDisplayRotation.mRotation) == r4.getRotation()) goto L31;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x028a  */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v29 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v30 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean addStartingWindow(java.lang.String r18, int r19, com.android.server.wm.ActivityRecord r20, boolean r21, boolean r22, boolean r23, boolean r24, boolean r25, boolean r26, boolean r27) {
        /*
            Method dump skipped, instructions count: 736
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.addStartingWindow(java.lang.String, int, com.android.server.wm.ActivityRecord, boolean, boolean, boolean, boolean, boolean, boolean, boolean):boolean");
    }

    public boolean addToFinishingAndWaitForIdle() {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 3169053633576517098L, 0, null, String.valueOf(this));
        }
        setState(State.FINISHING, "addToFinishingAndWaitForIdle");
        if (!this.mTaskSupervisor.mFinishingActivities.contains(this)) {
            this.mTaskSupervisor.mFinishingActivities.add(this);
        }
        resumeKeyDispatchingLocked();
        return this.mRootWindowContainer.resumeFocusedTasksTopActivities(null, null, null, false);
    }

    public final void addToStopping(String str, boolean z, boolean z2) {
        if (!this.mTaskSupervisor.mStoppingActivities.contains(this)) {
            EventLog.writeEvent(30066, Integer.valueOf(this.mUserId), Integer.valueOf(System.identityHashCode(this)), this.shortComponentName, str);
            this.mTaskSupervisor.mStoppingActivities.add(this);
        }
        Task rootTask = getRootTask();
        boolean z3 = this.mTaskSupervisor.mStoppingActivities.size() > 3 || (isRootOfTask() && rootTask.getChildCount() <= 1);
        if (!z && !z3) {
            rootTask.checkReadyForSleep();
            return;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 3981777934616509782L, 15, null, Boolean.valueOf(z3), Boolean.valueOf(!z2));
        }
        if (!z2) {
            this.mTaskSupervisor.scheduleIdle();
        } else {
            ActivityTaskSupervisor.ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mTaskSupervisor.mHandler;
            activityTaskSupervisorHandler.sendMessageDelayed(activityTaskSupervisorHandler.obtainMessage(200, this), ActivityTaskSupervisor.IDLE_TIMEOUT);
        }
    }

    @Override // com.android.server.wm.WindowToken
    public final void addWindow(WindowState windowState) {
        super.addWindow(windowState);
        if (windowState.mAttrs.type == 1 && windowState.isSecureLocked()) {
            MultiTaskingController multiTaskingController = this.mAtmService.mMultiTaskingController;
            multiTaskingController.getClass();
            Task task = windowState.getTask();
            DisplayContent displayContent = windowState.getDisplayContent();
            if (task != null && displayContent != null && displayContent.isRemoteAppDisplay()) {
                multiTaskingController.mH.sendMessage(multiTaskingController.mH.obtainMessage(1, task.mTaskId, 1, windowState.mAttrs.packageName));
            }
        }
        checkKeyguardFlagsChanged();
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean allowEdgeExtension() {
        WindowState findMainWindow;
        if (inMultiWindowMode() || !occludesParent(true)) {
            return false;
        }
        return hasStartingWindow() || !((findMainWindow = findMainWindow(true)) == null || PixelFormat.formatHasAlpha(findMainWindow.mAttrs.format));
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean applyAnimation(WindowManager.LayoutParams layoutParams, int i, boolean z, boolean z2, ArrayList arrayList) {
        if ((this.mTransitionChangeFlags & 8) != 0) {
            return false;
        }
        this.mRequestForceTransition = false;
        return super.applyAnimation(layoutParams, i, z, z2, arrayList);
    }

    @Override // com.android.server.wm.WindowToken
    public final void applyFixedRotationTransform(DisplayInfo displayInfo, DisplayFrames displayFrames, Configuration configuration) {
        StringBuilder sb = new StringBuilder("applyFixedRotationTransform, r=");
        sb.append(this);
        sb.append(", caller=");
        ActivityManagerService$$ExternalSyntheticOutline0.m(5, sb, "ActivityTaskManager");
        super.applyFixedRotationTransform(displayInfo, displayFrames, configuration);
        ensureActivityConfiguration(false);
    }

    public final void applyOptionsAnimation$1() {
        TransitionInfo.AnimationOptions animationOptions;
        IRemoteCallback iRemoteCallback;
        IRemoteCallback iRemoteCallback2;
        Transition transition;
        TransitionInfo.AnimationOptions makeScaleUpAnimOptions;
        boolean z;
        RemoteAnimationAdapter remoteAnimationAdapter = this.mPendingRemoteAnimation;
        if (remoteAnimationAdapter != null) {
            this.mDisplayContent.mAppTransition.overridePendingAppTransitionRemote(remoteAnimationAdapter, false, false);
            TransitionController transitionController = this.mTransitionController;
            long statusBarTransitionDelay = this.mPendingRemoteAnimation.getStatusBarTransitionDelay();
            Transition transition2 = transitionController.mCollectingTransition;
            if (transition2 != null) {
                transition2.mStatusBarTransitionDelay = statusBarTransitionDelay;
            }
        } else {
            ActivityOptions activityOptions = this.mPendingOptions;
            if (activityOptions == null) {
                return;
            }
            if (activityOptions.getAnimationType() == 5) {
                TransitionController transitionController2 = this.mTransitionController;
                TransitionInfo.AnimationOptions makeSceneTransitionAnimOptions = TransitionInfo.AnimationOptions.makeSceneTransitionAnimOptions();
                Transition transition3 = transitionController2.mCollectingTransition;
                if (transition3 == null) {
                    return;
                }
                transition3.setOverrideAnimation(makeSceneTransitionAnimOptions, null, null);
                return;
            }
            ActivityOptions activityOptions2 = this.mPendingOptions;
            Intent intent = this.intent;
            int animationType = activityOptions2.getAnimationType();
            DisplayContent displayContent = getDisplayContent();
            if (animationType != -1 && animationType != 0) {
                if (animationType != 1) {
                    if (animationType != 2) {
                        if (animationType == 3 || animationType == 4) {
                            z = animationType == 3;
                            HardwareBuffer thumbnail = activityOptions2.getThumbnail();
                            AppTransition appTransition = displayContent.mAppTransition;
                            int startX = activityOptions2.getStartX();
                            int startY = activityOptions2.getStartY();
                            IRemoteCallback animationStartedListener = activityOptions2.getAnimationStartedListener();
                            if (appTransition.canOverridePendingAppTransition()) {
                                appTransition.clear(true);
                                appTransition.mNextAppTransitionType = z ? 3 : 4;
                                appTransition.mNextAppTransitionScaleUp = z;
                                appTransition.putDefaultNextAppTransitionCoordinates(startX, startY, 0, 0, thumbnail);
                                appTransition.postAnimationCallback();
                                appTransition.mNextAppTransitionCallback = animationStartedListener;
                            }
                            TransitionInfo.AnimationOptions makeThumbnailAnimOptions = TransitionInfo.AnimationOptions.makeThumbnailAnimOptions(thumbnail, activityOptions2.getStartX(), activityOptions2.getStartY(), z);
                            iRemoteCallback2 = activityOptions2.getAnimationStartedListener();
                            if (intent.getSourceBounds() == null && thumbnail != null) {
                                intent.setSourceBounds(new Rect(activityOptions2.getStartX(), activityOptions2.getStartY(), thumbnail.getWidth() + activityOptions2.getStartX(), thumbnail.getHeight() + activityOptions2.getStartY()));
                            }
                            animationOptions = makeThumbnailAnimOptions;
                            iRemoteCallback = null;
                        } else if (animationType == 8 || animationType == 9) {
                            AppTransitionAnimationSpec[] animSpecs = activityOptions2.getAnimSpecs();
                            IAppTransitionAnimationSpecsFuture specsFuture = activityOptions2.getSpecsFuture();
                            if (specsFuture != null) {
                                displayContent.mAppTransition.overridePendingAppTransitionMultiThumbFuture(specsFuture, activityOptions2.getAnimationStartedListener(), animationType == 8);
                            } else if (animationType != 9 || animSpecs == null) {
                                AppTransition appTransition2 = displayContent.mAppTransition;
                                HardwareBuffer thumbnail2 = activityOptions2.getThumbnail();
                                int startX2 = activityOptions2.getStartX();
                                int startY2 = activityOptions2.getStartY();
                                int width = activityOptions2.getWidth();
                                int height = activityOptions2.getHeight();
                                IRemoteCallback animationStartedListener2 = activityOptions2.getAnimationStartedListener();
                                z = animationType == 8;
                                if (appTransition2.canOverridePendingAppTransition()) {
                                    appTransition2.clear(true);
                                    appTransition2.mNextAppTransitionType = z ? 5 : 6;
                                    appTransition2.mNextAppTransitionScaleUp = z;
                                    appTransition2.putDefaultNextAppTransitionCoordinates(startX2, startY2, width, height, thumbnail2);
                                    appTransition2.postAnimationCallback();
                                    appTransition2.mNextAppTransitionCallback = animationStartedListener2;
                                }
                                if (intent.getSourceBounds() == null) {
                                    intent.setSourceBounds(new Rect(activityOptions2.getStartX(), activityOptions2.getStartY(), activityOptions2.getWidth() + activityOptions2.getStartX(), activityOptions2.getHeight() + activityOptions2.getStartY()));
                                }
                            } else {
                                displayContent.mAppTransition.overridePendingAppTransitionMultiThumb(animSpecs, activityOptions2.getAnimationStartedListener(), activityOptions2.getAnimationFinishedListener(), false);
                            }
                        } else if (animationType == 11) {
                            AppTransition appTransition3 = displayContent.mAppTransition;
                            int startX3 = activityOptions2.getStartX();
                            int startY3 = activityOptions2.getStartY();
                            int width2 = activityOptions2.getWidth();
                            int height2 = activityOptions2.getHeight();
                            if (appTransition3.canOverridePendingAppTransition()) {
                                appTransition3.clear(true);
                                appTransition3.mNextAppTransitionType = 8;
                                appTransition3.putDefaultNextAppTransitionCoordinates(startX3, startY3, width2, height2, null);
                                appTransition3.postAnimationCallback();
                            }
                            makeScaleUpAnimOptions = TransitionInfo.AnimationOptions.makeClipRevealAnimOptions(activityOptions2.getStartX(), activityOptions2.getStartY(), activityOptions2.getWidth(), activityOptions2.getHeight());
                            if (intent.getSourceBounds() == null) {
                                intent.setSourceBounds(new Rect(activityOptions2.getStartX(), activityOptions2.getStartY(), activityOptions2.getWidth() + activityOptions2.getStartX(), activityOptions2.getHeight() + activityOptions2.getStartY()));
                            }
                        } else if (animationType != 12) {
                            NandswapManager$$ExternalSyntheticOutline0.m(animationType, "applyOptionsLocked: Unknown animationType=", "WindowManager");
                        } else {
                            AppTransition appTransition4 = displayContent.mAppTransition;
                            if (appTransition4.canOverridePendingAppTransition()) {
                                appTransition4.clear(true);
                                appTransition4.mNextAppTransitionType = 9;
                                appTransition4.postAnimationCallback();
                            }
                            animationOptions = TransitionInfo.AnimationOptions.makeCrossProfileAnimOptions();
                            iRemoteCallback = null;
                            iRemoteCallback2 = iRemoteCallback;
                        }
                    } else {
                        AppTransition appTransition5 = displayContent.mAppTransition;
                        int startX4 = activityOptions2.getStartX();
                        int startY4 = activityOptions2.getStartY();
                        int width3 = activityOptions2.getWidth();
                        int height3 = activityOptions2.getHeight();
                        if (appTransition5.canOverridePendingAppTransition()) {
                            appTransition5.clear(true);
                            appTransition5.mNextAppTransitionType = 2;
                            appTransition5.putDefaultNextAppTransitionCoordinates(startX4, startY4, width3, height3, null);
                            appTransition5.postAnimationCallback();
                        }
                        makeScaleUpAnimOptions = TransitionInfo.AnimationOptions.makeScaleUpAnimOptions(activityOptions2.getStartX(), activityOptions2.getStartY(), activityOptions2.getWidth(), activityOptions2.getHeight());
                        if (intent.getSourceBounds() == null) {
                            intent.setSourceBounds(new Rect(activityOptions2.getStartX(), activityOptions2.getStartY(), activityOptions2.getWidth() + activityOptions2.getStartX(), activityOptions2.getHeight() + activityOptions2.getStartY()));
                        }
                    }
                    iRemoteCallback2 = null;
                    animationOptions = makeScaleUpAnimOptions;
                    iRemoteCallback = null;
                } else {
                    displayContent.mAppTransition.overridePendingAppTransition(activityOptions2.getPackageName(), activityOptions2.getCustomEnterResId(), activityOptions2.getCustomExitResId(), activityOptions2.getCustomBackgroundColor(), activityOptions2.getAnimationStartedListener(), activityOptions2.getAnimationFinishedListener(), activityOptions2.getOverrideTaskTransition());
                    TransitionInfo.AnimationOptions makeCustomAnimOptions = TransitionInfo.AnimationOptions.makeCustomAnimOptions(activityOptions2.getPackageName(), activityOptions2.getCustomEnterResId(), activityOptions2.getCustomExitResId(), activityOptions2.getCustomBackgroundColor(), activityOptions2.getOverrideTaskTransition());
                    iRemoteCallback2 = activityOptions2.getAnimationStartedListener();
                    iRemoteCallback = activityOptions2.getAnimationFinishedListener();
                    animationOptions = makeCustomAnimOptions;
                }
                if (animationOptions != null && (transition = this.mTransitionController.mCollectingTransition) != null) {
                    transition.setOverrideAnimation(animationOptions, iRemoteCallback2, iRemoteCallback);
                }
            }
            animationOptions = null;
            iRemoteCallback = null;
            iRemoteCallback2 = iRemoteCallback;
            if (animationOptions != null) {
                transition.setOverrideAnimation(animationOptions, iRemoteCallback2, iRemoteCallback);
            }
        }
        Task task = this.task;
        if (task != null) {
            task.forAllActivities(new ActivityRecord$$ExternalSyntheticLambda3(4));
            return;
        }
        this.mPendingOptions = null;
        this.mPendingRemoteAnimation = null;
        this.mPendingRemoteTransition = null;
    }

    public final boolean areBoundsLetterboxed() {
        return getAppCompatState(true) != 2;
    }

    @Override // com.android.server.wm.WindowContainer
    public final ActivityRecord asActivityRecord() {
        return this;
    }

    public final void associateStartingWindowWithTaskIfNeeded() {
        StartingData startingData;
        if (this.mStartingWindow == null || (startingData = this.mStartingData) == null || startingData.mAssociatedTask != null) {
            return;
        }
        if (!CoreRune.MW_EMBED_ACTIVITY || isSplitEmbedded()) {
            StartingData startingData2 = this.mStartingData;
            Task task = this.task;
            startingData2.mAssociatedTask = task;
            task.mSharedStartingData = startingData2;
            attachStartingSurfaceToAssociatedTask();
        }
    }

    public final void attachStartingSurfaceToAssociatedTask() {
        if (this.mSyncState == 0 && isEmbedded()) {
            this.mTransitionController.collect(this);
        }
        WindowContainer.overrideConfigurationPropagation(this.mStartingWindow, this.mStartingData.mAssociatedTask);
        getSyncTransaction().reparent(this.mStartingWindow.mSurfaceControl, this.mStartingData.mAssociatedTask.mSurfaceControl);
    }

    public final void attachStartingWindow(WindowState windowState) {
        StartingData startingData = this.mStartingData;
        windowState.mStartingData = startingData;
        this.mStartingWindow = windowState;
        if (startingData != null) {
            if (startingData.mAssociatedTask != null) {
                if (!hasFixedRotationTransform()) {
                    attachStartingSurfaceToAssociatedTask();
                }
            } else if (isEmbedded()) {
                associateStartingWindowWithTaskIfNeeded();
            }
            if (this.mTransitionController.isCollecting()) {
                this.mStartingData.mTransitionId = this.mTransitionController.getCollectingTransitionId();
            }
        }
    }

    public final void attachThumbnailAnimation() {
        Rect rect;
        Rect rect2;
        if (isAnimating(2, 1)) {
            AppTransition appTransition = getDisplayContent().mAppTransition;
            AppTransitionAnimationSpec appTransitionAnimationSpec = (AppTransitionAnimationSpec) appTransition.mNextAppTransitionAnimationsSpecs.get(this.task.hashCode());
            if (appTransitionAnimationSpec == null) {
                appTransitionAnimationSpec = appTransition.mDefaultNextAppTransitionAnimationSpec;
            }
            HardwareBuffer hardwareBuffer = appTransitionAnimationSpec != null ? appTransitionAnimationSpec.buffer : null;
            if (hardwareBuffer == null) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled[0]) {
                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -1836789237982086339L, 0, null, String.valueOf(this.task));
                    return;
                }
                return;
            }
            clearThumbnail();
            SurfaceControl.Transaction pendingTransaction = getAnimatingContainer().getPendingTransaction();
            WindowContainerThumbnail windowContainerThumbnail = new WindowContainerThumbnail(pendingTransaction, getAnimatingContainer(), hardwareBuffer);
            this.mThumbnail = windowContainerThumbnail;
            DisplayInfo displayInfo = this.mDisplayContent.mDisplayInfo;
            WindowState findMainWindow = findMainWindow(true);
            if (findMainWindow != null) {
                Rect rect3 = findMainWindow.getInsetsStateWithVisibilityOverride().calculateInsets(findMainWindow.mWindowFrames.mFrame, WindowInsets.Type.systemBars(), false).toRect();
                Rect rect4 = new Rect(findMainWindow.mWindowFrames.mFrame);
                rect4.inset(rect3);
                rect = rect3;
                rect2 = rect4;
            } else {
                rect = null;
                rect2 = new Rect(0, 0, displayInfo.appWidth, displayInfo.appHeight);
            }
            Configuration configuration = this.mDisplayContent.getConfiguration();
            AppTransition appTransition2 = getDisplayContent().mAppTransition;
            Task task = this.task;
            int i = configuration.orientation;
            AppTransitionAnimationSpec appTransitionAnimationSpec2 = (AppTransitionAnimationSpec) appTransition2.mNextAppTransitionAnimationsSpecs.get(task.hashCode());
            TransitionAnimation transitionAnimation = appTransition2.mTransitionAnimation;
            Rect rect5 = appTransitionAnimationSpec2 != null ? appTransitionAnimationSpec2.rect : null;
            AppTransitionAnimationSpec appTransitionAnimationSpec3 = appTransition2.mDefaultNextAppTransitionAnimationSpec;
            windowContainerThumbnail.startAnimation(pendingTransaction, transitionAnimation.createThumbnailAspectScaleAnimationLocked(rect2, rect, hardwareBuffer, i, rect5, appTransitionAnimationSpec3 != null ? appTransitionAnimationSpec3.rect : null, appTransition2.mNextAppTransitionScaleUp), null);
        }
    }

    public final boolean attachedToProcess() {
        return hasProcess() && this.app.hasThread();
    }

    public final boolean canBeTopRunning() {
        return !this.finishing && showToCurrentUser();
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean canCustomizeAppTransition() {
        return true;
    }

    public final boolean canForceResizeNonResizable(int i) {
        int i2;
        if (i == 2 && this.info.supportsPictureInPicture()) {
            return false;
        }
        Task task = this.task;
        return ((WindowConfiguration.inMultiWindowMode(i) && (task != null ? task.supportsMultiWindow() || supportsMultiWindowInDisplayArea((TaskDisplayArea) super.getDisplayArea()) : supportsMultiWindowInDisplayArea((TaskDisplayArea) super.getDisplayArea())) && !this.mAtmService.mForceResizableActivities) || (i2 = this.info.resizeMode) == 2 || (1048576 & i2) != 0 || i2 == 1) ? false : true;
    }

    public boolean canLaunchHomeActivity(int i, ActivityRecord activityRecord) {
        if (i == 1000 || i == 0) {
            return true;
        }
        RecentTasks recentTasks = this.mTaskSupervisor.mService.mRecentTasks;
        if (recentTasks == null || !recentTasks.isCallerRecents(i)) {
            return activityRecord != null && activityRecord.isResolverOrDelegateActivity();
        }
        return true;
    }

    public final boolean canResumeByCompat() {
        boolean z;
        TaskFragment taskFragment;
        ActivityRecord activity;
        WindowProcessController windowProcessController = this.app;
        if (windowProcessController == null) {
            return true;
        }
        if (windowProcessController.mInfo.targetSdkVersion >= 29 || windowProcessController.mPreQTopResumedActivity == this) {
            z = true;
        } else if (isAttached()) {
            ActivityRecord activityRecord = windowProcessController.mPreQTopResumedActivity;
            DisplayContent displayContent = (activityRecord == null || !activityRecord.isAttached()) ? null : windowProcessController.mPreQTopResumedActivity.mDisplayContent;
            z = (displayContent != null && windowProcessController.mPreQTopResumedActivity.isVisibleRequested() && windowProcessController.mPreQTopResumedActivity.isFocusable()) ? false : true;
            DisplayContent displayContent2 = this.mDisplayContent;
            if (!z && displayContent.compareTo((WindowContainer) displayContent2) < 0) {
                z = true;
            }
            if (!z && (activity = displayContent.getActivity(new Predicate() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda11
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((ActivityRecord) obj) == ActivityRecord.this;
                }
            }, true, windowProcessController.mPreQTopResumedActivity)) != null && activity != windowProcessController.mPreQTopResumedActivity) {
                z = true;
            }
            if (z) {
                ActivityRecord activityRecord2 = windowProcessController.mPreQTopResumedActivity;
                if (activityRecord2 != null && activityRecord2.isState(State.RESUMED) && (taskFragment = windowProcessController.mPreQTopResumedActivity.getTaskFragment()) != null) {
                    taskFragment.startPausing$1(this, "top-resumed-changed", taskFragment.shouldBeVisible(null), false);
                }
                windowProcessController.mPreQTopResumedActivity = this;
            }
        } else {
            z = false;
        }
        return z;
    }

    public final boolean canShowWhenLocked() {
        TaskFragment taskFragment;
        TaskFragment taskFragment2 = getTaskFragment();
        if (taskFragment2 == null || (taskFragment = taskFragment2.mAdjacentTaskFragment) == null || !taskFragment2.mIsEmbedded) {
            return canShowWhenLocked(this);
        }
        return canShowWhenLocked(this) && canShowWhenLocked(taskFragment.getTopNonFinishingActivity(true, true));
    }

    public final boolean canShowWindows() {
        return this.mTransitionController.isShellTransitionsEnabled() ? this.mSyncState != 1 : this.allDrawn;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean canStartChangeTransition() {
        Task task = this.task;
        return (task == null || task.mDragResizing || !super.canStartChangeTransition()) ? false : true;
    }

    public final boolean canTurnScreenOn() {
        if (this.mTurnScreenOn || containsTurnScreenOnWindow()) {
            return this.mCurrentLaunchCanTurnScreenOn && (!this.mTaskSupervisor.mKeyguardController.isKeyguardShowing(getDisplayId()) || this.mTaskSupervisor.mKeyguardController.checkKeyguardVisibility(this));
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void cancelAnimation() {
        super.cancelAnimation();
        clearThumbnail();
    }

    @Override // com.android.server.wm.WindowContainer
    public final void checkAppWindowsReadyToShow() {
        boolean z = this.allDrawn;
        if (z == this.mLastAllDrawn) {
            return;
        }
        this.mLastAllDrawn = z;
        if (z) {
            if (!this.mFreezingScreen) {
                setAppLayoutChanges(8);
                if (getDisplayContent().mOpeningApps.contains(this) || !canShowWindows()) {
                    return;
                }
                forAllWindows((Consumer) new ActivityRecord$$ExternalSyntheticLambda3(0), false);
                return;
            }
            forAllWindows((Consumer) new ActivityRecord$$ExternalSyntheticLambda3(0), false);
            stopFreezingScreen(false);
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[2]) {
                ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_ORIENTATION, 3235691043029201724L, 20, null, String.valueOf(this), Long.valueOf(this.mNumInterestingWindows), Long.valueOf(this.mNumDrawnWindows));
            }
            setAppLayoutChanges(4);
        }
    }

    public final boolean checkContentUriPermission(IBinder iBinder, GrantUri grantUri, int i) {
        ActivityCallerState activityCallerState = this.mCallerState;
        activityCallerState.getClass();
        if (!Intent.isAccessUriMode(i)) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Mode flags are not access URI mode flags: "));
        }
        ActivityCallerState.CallerInfo callerInfo = (ActivityCallerState.CallerInfo) activityCallerState.mCallerTokenInfoMap.getOrDefault(iBinder, null);
        if (callerInfo == null) {
            Slog.e("ActivityTaskManager", "Caller not found for checkContentUriPermission of: " + grantUri.uri.toSafeString());
            return false;
        }
        if (callerInfo.mInaccessibleContentUris.contains(grantUri)) {
            return false;
        }
        boolean contains = callerInfo.mReadableContentUris.contains(grantUri);
        boolean contains2 = callerInfo.mWritableContentUris.contains(grantUri);
        if (!contains && !contains2) {
            throw new IllegalArgumentException("The supplied URI wasn't passed at launch in #getData, #EXTRA_STREAM, nor #getClipData: " + grantUri.uri.toSafeString());
        }
        if ((i & 1) == 0 || contains) {
            return (i & 2) == 0 || contains2;
        }
        return false;
    }

    public final boolean checkEnterPictureInPictureAppOpsState() {
        return this.mAtmService.getAppOpsManager().checkOpNoThrow(67, this.info.applicationInfo.uid, this.packageName) == 0;
    }

    public final boolean checkEnterPictureInPictureState(String str, boolean z, boolean z2) {
        if (!supportsPictureInPicture() || !checkEnterPictureInPictureAppOpsState() || this.mAtmService.mVrController.mVrState != 0) {
            return false;
        }
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null) {
            DisplayWindowPolicyControllerHelper displayWindowPolicyControllerHelper = displayContent.mDwpcHelper;
            int uid = getUid();
            DisplayWindowPolicyController displayWindowPolicyController = displayWindowPolicyControllerHelper.mDisplayWindowPolicyController;
            if (!(displayWindowPolicyController == null ? true : displayWindowPolicyController.isEnteringPipAllowed(uid))) {
                Slog.w("ActivityTaskManager", "Display " + this.mDisplayContent.mDisplayId + " doesn't support enter picture-in-picture mode. caller = " + str);
                return false;
            }
        }
        if (isDexMode()) {
            return false;
        }
        if ((getDisplayContent() != null && getDisplayContent().isMultiTaskingDisplay()) || this.mWmService.mSwitchingUser) {
            return false;
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION && this.finishing) {
            Slog.w("ActivityTaskManager", "checkEnterPictureInPictureState: failed, reason=finishing");
            return false;
        }
        boolean z3 = this.mAtmService.mLockTaskController.mLockTaskModeState != 0;
        TaskDisplayArea taskDisplayArea = (TaskDisplayArea) super.getDisplayArea();
        boolean z4 = taskDisplayArea != null && taskDisplayArea.hasPinnedTask();
        boolean z5 = (isKeyguardLocked() || z3) ? false : true;
        if (z && z4) {
            return false;
        }
        int ordinal = this.mState.ordinal();
        if (ordinal == 0) {
            return z2;
        }
        if (ordinal != 2) {
            return (ordinal == 3 || ordinal == 4) ? z5 && !z4 && this.supportsEnterPipOnTaskSwitch : ordinal == 5 && this.supportsEnterPipOnTaskSwitch && z5 && !z4;
        }
        if (z3) {
            return false;
        }
        return this.supportsEnterPipOnTaskSwitch || !z;
    }

    public final void checkKeyguardFlagsChanged() {
        boolean containsDismissKeyguardWindow = containsDismissKeyguardWindow();
        boolean containsShowWhenLockedWindow = containsShowWhenLockedWindow();
        if (containsDismissKeyguardWindow != this.mLastContainsDismissKeyguardWindow || containsShowWhenLockedWindow != this.mLastContainsShowWhenLockedWindow) {
            this.mDisplayContent.notifyKeyguardFlagsChanged();
        }
        this.mLastContainsDismissKeyguardWindow = containsDismissKeyguardWindow;
        this.mLastContainsShowWhenLockedWindow = containsShowWhenLockedWindow;
        this.mLastContainsTurnScreenOnWindow = containsTurnScreenOnWindow();
    }

    public final void cleanUp(boolean z, boolean z2) {
        HashSet hashSet;
        int i;
        ActivityRecord activityRecord;
        getTaskFragment().cleanUpActivityReferences(this);
        Task task = this.mLastParentBeforePip;
        if (task != null) {
            task.mChildPipActivity = null;
            this.mLastParentBeforePip = null;
        }
        this.mLaunchIntoPipHostActivity = null;
        this.mLastTaskFragmentOrganizerBeforePip = null;
        this.mLastEmbeddedParentTfTokenBeforePip = null;
        Task rootTask = getRootTask();
        if (rootTask != null && (this == (activityRecord = rootTask.mTranslucentActivityWaiting) || this == rootTask.mPendingConvertFromTranslucentActivity)) {
            if (activityRecord != null) {
                if (!activityRecord.finishing) {
                    activityRecord.setOccludesParent(true, false);
                }
                rootTask.mTranslucentActivityWaiting = null;
            }
            ActivityRecord activityRecord2 = rootTask.mPendingConvertFromTranslucentActivity;
            if (activityRecord2 != null) {
                if (!activityRecord2.finishing) {
                    activityRecord2.setOccludesParent(true, false);
                }
                rootTask.mPendingConvertFromTranslucentActivity = null;
            }
            rootTask.mUndrawnActivitiesBelowTopTranslucent.clear();
            rootTask.mHandler.removeMessages(101);
        }
        if (this.mHandleExitSplashScreen && !this.startingMoved && ((i = this.mTransferringSplashScreenState) == 3 || i == 0)) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STARTING_WINDOW_enabled[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -1298801500610545721L, 0, "Cleaning splash screen token=%s", String.valueOf(this));
            }
            TaskOrganizerController taskOrganizerController = this.mAtmService.mTaskOrganizerController;
            Task task2 = this.task;
            StartingSurfaceController.StartingSurface startingSurface = this.mStartingSurface;
            ITaskOrganizer iTaskOrganizer = startingSurface != null ? startingSurface.mTaskOrganizer : null;
            taskOrganizerController.getClass();
            if (task2.getRootTask() != null) {
                if (iTaskOrganizer == null) {
                    iTaskOrganizer = taskOrganizerController.getTaskOrganizer();
                }
                if (iTaskOrganizer != null) {
                    try {
                        iTaskOrganizer.onAppSplashScreenViewRemoved(task2.mTaskId);
                    } catch (RemoteException e) {
                        Slog.e("TaskOrganizerController", "Exception sending onAppSplashScreenViewRemoved callback", e);
                    }
                }
            }
        }
        if (z2) {
            setState(State.DESTROYED, "cleanUp");
            detachFromProcess();
        }
        ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
        activityTaskSupervisor.mFinishingActivities.remove(this);
        activityTaskSupervisor.reportActivityLaunched(false, this, -1L, 0);
        if (this.finishing && (hashSet = this.pendingResults) != null) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                PendingIntentRecord pendingIntentRecord = (PendingIntentRecord) ((WeakReference) it.next()).get();
                if (pendingIntentRecord != null) {
                    this.mAtmService.mPendingIntentController.cancelIntentSender(pendingIntentRecord, false, 16);
                }
            }
            this.pendingResults = null;
        }
        if (z) {
            cleanUpActivityServices();
        }
        removeTimeouts();
        if (this.mPendingRelaunchCount == 0) {
            return;
        }
        this.mPendingRelaunchCount = 0;
        this.mRelaunchStartTime = 0L;
        DisplayPolicy displayPolicy = this.mDisplayContent.mDisplayPolicy;
        if (displayPolicy.mRelaunchingSystemBarColorApps.remove(this) && displayPolicy.mRelaunchingSystemBarColorApps.isEmpty()) {
            displayPolicy.updateSystemBarAttributes();
        }
    }

    public final void cleanUpActivityServices() {
        synchronized (this) {
            try {
                final ActivityServiceConnectionsHolder activityServiceConnectionsHolder = this.mServiceConnectionsHolder;
                if (activityServiceConnectionsHolder == null) {
                    return;
                }
                ArraySet arraySet = activityServiceConnectionsHolder.mConnections;
                if (arraySet != null && !arraySet.isEmpty() && !activityServiceConnectionsHolder.mIsDisconnecting) {
                    activityServiceConnectionsHolder.mIsDisconnecting = true;
                    activityServiceConnectionsHolder.mActivity.mAtmService.mH.post(new Runnable() { // from class: com.android.server.wm.ActivityServiceConnectionsHolder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ActivityServiceConnectionsHolder activityServiceConnectionsHolder2 = ActivityServiceConnectionsHolder.this;
                            activityServiceConnectionsHolder2.mActivity.mAtmService.mAmInternal.disconnectActivityFromServices(activityServiceConnectionsHolder2);
                            activityServiceConnectionsHolder2.mIsDisconnecting = false;
                        }
                    });
                }
                this.mServiceConnectionsHolder = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void clearAnimatingFlags() {
        boolean z = false;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z |= ((WindowState) this.mChildren.get(size)).clearAnimatingFlags();
        }
        if (z) {
            requestUpdateWallpaperIfNeeded();
        }
    }

    public final void clearThumbnail() {
        WindowContainerThumbnail windowContainerThumbnail = this.mThumbnail;
        if (windowContainerThumbnail == null) {
            return;
        }
        windowContainerThumbnail.destroy();
        this.mThumbnail = null;
    }

    public final void clearWaitForEnteringPinnedMode(String str) {
        if (this.mWaitForEnteringPinnedMode) {
            this.mWaitForEnteringPinnedMode = false;
            setWindowingMode(0);
            StringBuilder sb = new StringBuilder("clearWaitForEnteringPinnedMode: r=");
            sb.append(this);
            sb.append(", reason=");
            BootReceiver$$ExternalSyntheticOutline0.m(sb, str, "ActivityTaskManager");
        }
        setEnteringPipFromSplit(str, false);
    }

    public final void commitVisibility(boolean z, boolean z2, boolean z3) {
        WindowState windowState;
        this.mVisibleSetFromTransferredStartingWindow = false;
        if (z == this.mVisible) {
            return;
        }
        int size = this.mChildren.size();
        boolean isAnimating = WindowManagerService.sEnableShellTransitions ? z : isAnimating(2, 1);
        for (int i = 0; i < size; i++) {
            ((WindowState) this.mChildren.get(i)).onAppVisibilityChanged(z, isAnimating);
        }
        setVisible(z);
        setVisibleRequested(z);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -477271988506706928L, 1020, null, String.valueOf(this), Boolean.valueOf(this.mVisible), Boolean.valueOf(this.mVisibleRequested), Boolean.valueOf(inTransitionSelfOrParent()), Boolean.valueOf(isAnimating), String.valueOf(Debug.getCallers(5)));
        }
        if (z) {
            WindowState windowState2 = this.mStartingWindow;
            if (windowState2 != null && !windowState2.isDrawn() && (this.firstWindowDrawn || this.allDrawn)) {
                this.mStartingWindow.clearPolicyVisibilityFlag(1);
                this.mStartingWindow.mLegacyPolicyVisibilityAfterAnim = false;
            }
            final WindowManagerService windowManagerService = this.mWmService;
            Objects.requireNonNull(windowManagerService);
            final int i2 = 0;
            forAllWindows(new Consumer() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i3 = i2;
                    Object obj2 = windowManagerService;
                    switch (i3) {
                        case 0:
                            ((WindowManagerService) obj2).makeWindowFreezingScreenIfNeededLocked((WindowState) obj);
                            break;
                        default:
                            ActivityRecord activityRecord = (ActivityRecord) obj2;
                            activityRecord.getClass();
                            ((WindowState) obj).mWinAnimator.hide(activityRecord.getPendingTransaction(), "immediately hidden");
                            break;
                    }
                }
            }, true);
        } else {
            stopFreezingScreen(true);
        }
        Task task = this.task;
        for (Task organizedTask = task != null ? task.getOrganizedTask() : null; organizedTask != null; organizedTask = organizedTask.getParent().asTask()) {
            organizedTask.dispatchTaskInfoChangedIfNeeded(false);
        }
        DisplayContent displayContent = getDisplayContent();
        displayContent.mInputMonitor.mUpdateInputWindowsNeeded = true;
        if (z2) {
            this.mWmService.updateFocusedWindowLocked(3, false);
            this.mWmService.mWindowPlacerLocked.performSurfacePlacement(false);
        }
        displayContent.mInputMonitor.updateInputWindowsLw(false);
        this.mTransitionChangeFlags = 0;
        boolean isShellTransitionsEnabled = this.mTransitionController.isShellTransitionsEnabled();
        boolean z4 = !isShellTransitionsEnabled && isAnimating(6, 25);
        if (!z4 && !isShellTransitionsEnabled) {
            onAnimationFinished(1, null);
            if (z) {
                this.mEnteringAnimation = true;
                this.mWmService.mActivityManagerAppTransitionNotifier.onAppTransitionFinishedLocked(this.token);
            }
        }
        if (z || isShellTransitionsEnabled || !isAnimating(2, 9)) {
            setClientVisible(z);
        }
        DisplayContent displayContent2 = getDisplayContent();
        if (!z) {
            this.mImeInsetsFrozenUntilStartInput = true;
            if (CoreRune.MW_SHELL_TRANSITION && isShellTransitionsEnabled) {
                InsetsControlTarget insetsControlTarget = displayContent2.mInsetsStateController.getImeSourceProvider().mControlTarget;
                DisplayContent.RemoteInsetsControlTarget remoteInsetsControlTarget = displayContent2.mRemoteInsetsControlTarget;
                if (remoteInsetsControlTarget != null && remoteInsetsControlTarget == insetsControlTarget && (windowState = displayContent2.mImeLayeringTarget) != null && windowState.mActivityRecord == this) {
                    displayContent2.computeImeTarget(true);
                }
            }
        }
        if (!displayContent2.mClosingApps.contains(this) && !displayContent2.mOpeningApps.contains(this) && !z3) {
            ActivitySnapshotController activitySnapshotController = this.mWmService.mSnapshotController.mActivitySnapshotController;
            if (!activitySnapshotController.shouldDisableSnapshots() && this.task != null && !z) {
                activitySnapshotController.resetTmpFields();
                activitySnapshotController.addBelowActivityIfExist(this, activitySnapshotController.mPendingRemoveActivity, false);
                activitySnapshotController.postProcess();
            }
        }
        if (isShellTransitionsEnabled || this.mVisible || z4 || displayContent2.mAppTransition.isTransitionSet()) {
            return;
        }
        final int i3 = 1;
        forAllWindows(new Consumer() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i32 = i3;
                Object obj2 = this;
                switch (i32) {
                    case 0:
                        ((WindowManagerService) obj2).makeWindowFreezingScreenIfNeededLocked((WindowState) obj);
                        break;
                    default:
                        ActivityRecord activityRecord = (ActivityRecord) obj2;
                        activityRecord.getClass();
                        ((WindowState) obj).mWinAnimator.hide(activityRecord.getPendingTransaction(), "immediately hidden");
                        break;
                }
            }
        }, true);
        scheduleAnimation();
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0052, code lost:
    
        if (r8.getDisplayState(getDisplayId()).mTopOccludesActivity == r6) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.wm.ActivityRecord completeFinishing(java.lang.String r7, boolean r8) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.completeFinishing(java.lang.String, boolean):com.android.server.wm.ActivityRecord");
    }

    public final void completeResumeLocked() {
        this.idle = false;
        this.results = null;
        ArrayList arrayList = this.newIntents;
        if (arrayList != null && arrayList.size() > 0) {
            ArrayList arrayList2 = this.newIntents;
            this.mLastNewIntent = (Intent) arrayList2.get(arrayList2.size() - 1);
        }
        this.newIntents = null;
        this.mTaskSupervisor.updateHomeProcessIfNeeded(this);
        if (this.nowVisible) {
            this.mTaskSupervisor.reportActivityLaunched(false, this, -1L, 0);
        }
        ActivityTaskSupervisor.ActivityTaskSupervisorHandler activityTaskSupervisorHandler = this.mTaskSupervisor.mHandler;
        activityTaskSupervisorHandler.sendMessageDelayed(activityTaskSupervisorHandler.obtainMessage(200, this), ActivityTaskSupervisor.IDLE_TIMEOUT);
        this.mTaskSupervisor.mStoppingActivities.remove(this);
        if (((TaskDisplayArea) super.getDisplayArea()).allResumedActivitiesComplete()) {
            this.mAppCompatController.mAppCompatSizeCompatModePolicy.updateAppCompatDisplayInsets();
            RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
            for (int childCount = rootWindowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
                ((DisplayContent) rootWindowContainer.getChildAt(childCount)).mDisplayContent.executeAppTransition();
            }
        }
        resumeKeyDispatchingLocked();
        Task rootTask = getRootTask();
        this.mTaskSupervisor.mNoAnimActivities.clear();
        this.returningOptions = null;
        if (canTurnScreenOn()) {
            this.mTaskSupervisor.wakeUp("turnScreenOnFlag::" + this.packageName);
        } else {
            rootTask.checkReadyForSleep();
        }
        ActivityTaskManagerServiceExt activityTaskManagerServiceExt = this.mAtmService.mExt;
        activityTaskManagerServiceExt.mKeepAliveActivities.remove(this);
        if (activityTaskManagerServiceExt.mKeepAliveActivities.size() == 0) {
            activityTaskManagerServiceExt.mHasActivitiesKeptAlive.compareAndSet(true, false);
        }
        if (CoreRune.FW_FLEX_PANEL && this.mIsFlexPanel && inSplitScreenWindowingMode() && this.task.getAdjacentTask() != null) {
            final Task adjacentTask = this.task.getAdjacentTask();
            adjacentTask.getClass();
            this.mAtmService.mH.post(new Runnable() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.mAtmService.setFocusedTask(adjacentTask.mTaskId);
                }
            });
        }
    }

    public final void computeCallerInfo(IBinder iBinder, Intent intent, int i, String str, boolean z) {
        boolean z2;
        boolean z3;
        ActivityCallerState activityCallerState = this.mCallerState;
        activityCallerState.getClass();
        ActivityCallerState.CallerInfo callerInfo = new ActivityCallerState.CallerInfo(i, str, z);
        activityCallerState.mCallerTokenInfoMap.put(iBinder, callerInfo);
        ArraySet contentUrisFromIntent = ActivityCallerState.getContentUrisFromIntent(intent);
        for (int size = contentUrisFromIntent.size() - 1; size >= 0; size--) {
            Uri uri = (Uri) contentUrisFromIntent.valueAt(size);
            ArraySet arraySet = callerInfo.mReadableContentUris;
            GrantUri grantUri = new GrantUri(ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)), 1, ContentProvider.getUriWithoutUserId(uri));
            ActivityTaskManagerService activityTaskManagerService = activityCallerState.mAtmService;
            if (((UriGrantsManagerService.LocalService) activityTaskManagerService.mUgmInternal).checkUriPermission(grantUri, i, 1, true)) {
                arraySet.add(grantUri);
                z2 = true;
            } else {
                z2 = false;
            }
            ArraySet arraySet2 = callerInfo.mWritableContentUris;
            GrantUri grantUri2 = new GrantUri(ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)), 2, ContentProvider.getUriWithoutUserId(uri));
            if (((UriGrantsManagerService.LocalService) activityTaskManagerService.mUgmInternal).checkUriPermission(grantUri2, i, 2, true)) {
                arraySet2.add(grantUri2);
                z3 = true;
            } else {
                z3 = false;
            }
            if (!z2 && !z3) {
                callerInfo.mInaccessibleContentUris.add(new GrantUri(ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)), 0, ContentProvider.getUriWithoutUserId(uri)));
            }
        }
    }

    public final void computeConfigByResolveHint(Configuration configuration, Configuration configuration2) {
        this.task.computeConfigResourceOverrides(configuration, configuration2, this.mResolveConfigHint, null);
        TaskFragment.ConfigOverrideHint configOverrideHint = this.mResolveConfigHint;
        configOverrideHint.mTmpCompatInsets = null;
        configOverrideHint.mTmpOverrideDisplayInfo = null;
    }

    public final boolean containsDismissKeyguardWindow() {
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

    public final boolean containsShowWhenLockedWindow() {
        if (isRelaunching()) {
            return this.mLastContainsShowWhenLockedWindow;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if ((((WindowState) this.mChildren.get(size)).mAttrs.flags & 524288) != 0 && (this.mChildren.size() <= 1 || ((WindowState) this.mChildren.get(size)).mAttrs.type != 3)) {
                return true;
            }
        }
        return false;
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

    public final boolean continueLaunchTicking() {
        Task rootTask;
        if (this.launchTickTime == 0 || (rootTask = getRootTask()) == null) {
            return false;
        }
        rootTask.forAllActivities(new Task$$ExternalSyntheticLambda6(5));
        this.mAtmService.mH.postDelayed(this.mLaunchTickRunnable, 500L);
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final RemoteAnimationTarget createRemoteAnimationTarget(RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord) {
        boolean z = true;
        WindowState findMainWindow = findMainWindow(true);
        if (this.task == null || findMainWindow == null) {
            return null;
        }
        Rect rect = findMainWindow.getInsetsStateWithVisibilityOverride().calculateInsets(this.task.getBounds(), WindowInsets.Type.systemBars(), false).toRect();
        InsetUtils.addInsets(rect, getLetterboxInsets());
        int i = this.task.mTaskId;
        int i2 = remoteAnimationRecord.mMode;
        SurfaceControl surfaceControl = remoteAnimationRecord.mAdapter.mCapturedLeash;
        boolean z2 = !occludesParent(true);
        Rect rect2 = new Rect();
        int prefixOrderIndex = getPrefixOrderIndex();
        RemoteAnimationController.RemoteAnimationAdapterWrapper remoteAnimationAdapterWrapper = remoteAnimationRecord.mAdapter;
        Point point = remoteAnimationAdapterWrapper.mPosition;
        Rect rect3 = remoteAnimationAdapterWrapper.mLocalBounds;
        Rect rect4 = remoteAnimationAdapterWrapper.mEndBounds;
        WindowConfiguration windowConfiguration = this.task.getWindowConfiguration();
        RemoteAnimationController.RemoteAnimationAdapterWrapper remoteAnimationAdapterWrapper2 = remoteAnimationRecord.mThumbnailAdapter;
        RemoteAnimationTarget remoteAnimationTarget = new RemoteAnimationTarget(i, i2, surfaceControl, z2, rect2, rect, prefixOrderIndex, point, rect3, rect4, windowConfiguration, false, remoteAnimationAdapterWrapper2 != null ? remoteAnimationAdapterWrapper2.mCapturedLeash : null, remoteAnimationRecord.mStartBounds, this.task.getTaskInfo(), checkEnterPictureInPictureAppOpsState());
        remoteAnimationTarget.setShowBackdrop(remoteAnimationRecord.mShowBackdrop);
        StartingData startingData = this.mStartingData;
        remoteAnimationTarget.setWillShowImeOnTarget(startingData != null && startingData.hasImeSurface());
        RemoteAnimationController remoteAnimationController = RemoteAnimationController.this;
        int size = remoteAnimationController.mDisplayContent.mChangingContainers.size() - 1;
        while (true) {
            if (size < 0) {
                z = false;
                break;
            }
            if (remoteAnimationRecord.mWindowContainer.isDescendantOf((WindowContainer) remoteAnimationController.mDisplayContent.mChangingContainers.valueAt(size))) {
                break;
            }
            size--;
        }
        remoteAnimationTarget.hasAnimatingParent = z;
        return remoteAnimationTarget;
    }

    public final boolean destroyIfPossible(String str) {
        setState(State.FINISHING, "destroyIfPossible");
        this.mTaskSupervisor.mStoppingActivities.remove(this);
        Task rootTask = getRootTask();
        TaskDisplayArea taskDisplayArea = (TaskDisplayArea) super.getDisplayArea();
        if (CoreRune.SYSFW_APP_SPEG && (taskDisplayArea == null || rootTask == null)) {
            Slog.w("SPEG", "Impossible to destroy activity: " + this + ", task is null");
        }
        ActivityRecord activityRecord = taskDisplayArea.topRunningActivity(false);
        if (activityRecord == null && rootTask.isFocusedRootTaskOnDisplay() && taskDisplayArea.getOrCreateRootHomeTask(false) != null && getDisplayId() != 4) {
            addToFinishingAndWaitForIdle();
            return false;
        }
        makeFinishingLocked();
        boolean destroyImmediately = destroyImmediately("finish-imm:" + str);
        if (activityRecord == null) {
            this.mRootWindowContainer.ensureVisibilityAndConfig(null, this.mDisplayContent, true);
            if (this.mDisplayContent.topRunningActivity(false) == null) {
                this.mTransitionController.setReady(this.mDisplayContent, true);
            }
        }
        if (destroyImmediately) {
            this.mRootWindowContainer.resumeFocusedTasksTopActivities();
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTAINERS_enabled[0]) {
            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_CONTAINERS, -2989211291975863399L, 0, null, String.valueOf(this), String.valueOf(destroyImmediately));
        }
        return destroyImmediately;
    }

    public final boolean destroyImmediately(String str) {
        boolean z;
        State state = State.DESTROYING;
        State state2 = State.DESTROYED;
        boolean isState = isState(state, state2);
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled;
        if (isState) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 9050478058743283018L, 0, null, String.valueOf(this), String.valueOf(str));
            }
            return false;
        }
        EventLog.writeEvent(30018, Integer.valueOf(this.mUserId), Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(this.task.mTaskId), this.shortComponentName, str);
        cleanUp(false, false);
        setVisibleRequested(false);
        if (!hasProcess()) {
            if (this.finishing) {
                removeFromHistory(str + " hadNoApp");
                return true;
            }
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 3282063745558462269L, 0, null, String.valueOf(this));
            }
            setState(state2, "destroyActivityLocked. not finishing and had no app");
            return false;
        }
        this.app.removeActivity(true, this);
        if (!this.app.mHasActivities) {
            this.mAtmService.clearHeavyWeightProcessIfEquals(this.app);
        }
        try {
            this.mAtmService.mLifecycleManager.scheduleTransactionItem(this.app.mThread, DestroyActivityItem.obtain(this.token, this.finishing));
        } catch (Exception unused) {
            if (this.finishing) {
                removeFromHistory(str + " exceptionInScheduleDestroy");
                z = true;
            }
        }
        z = false;
        boolean z2 = z;
        this.nowVisible = false;
        if (!this.finishing || z) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, -1834399855266808961L, 0, null, String.valueOf(this));
            }
            setState(state2, "destroyActivityLocked. not finishing or skipping destroy");
            detachFromProcess();
        } else {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 5672598223877126839L, 0, null, String.valueOf(this));
            }
            setState(state, "destroyActivityLocked. finishing and not skipping destroy");
            this.mAtmService.mH.postDelayed(this.mDestroyTimeoutRunnable, 10000L);
        }
        return z2;
    }

    public final void destroySurfaces(boolean z) {
        ArrayList arrayList = new ArrayList(this.mChildren);
        boolean z2 = false;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            z2 |= ((WindowState) arrayList.get(size)).destroySurface(z, this.mAppStopped);
        }
        if (z2) {
            getDisplayContent().assignWindowLayers(true);
            updateLetterboxSurfaceIfNeeded(null);
        }
    }

    public final void destroyed(String str) {
        this.mAtmService.mH.removeCallbacks(this.mDestroyTimeoutRunnable);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTAINERS_enabled[0]) {
            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_CONTAINERS, -8001673213497887656L, 0, null, String.valueOf(this));
        }
        if (!isState(State.DESTROYING, State.DESTROYED)) {
            throw new IllegalStateException("Reported destroyed for activity that is not destroying: r=" + this);
        }
        ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
        Task task = this.task;
        activityTaskSupervisor.getClass();
        if (task != null && task.mKillProcessesOnDestroyed) {
            int[] iArr = new int[1];
            task.forAllActivities(new ActivityTaskSupervisor$$ExternalSyntheticLambda6(1, iArr));
            if (iArr[0] <= 1) {
                activityTaskSupervisor.mHandler.removeMessages(206, task);
                activityTaskSupervisor.killTaskProcessesIfPossible(task);
            }
        }
        if (isInRootTaskLocked()) {
            cleanUp(true, false);
            removeFromHistory(str);
        }
        this.mRootWindowContainer.resumeFocusedTasksTopActivities();
    }

    public final void detachFromProcess() {
        WindowProcessController windowProcessController = this.app;
        if (windowProcessController != null) {
            windowProcessController.removeActivity(false, this);
        }
        this.app = null;
        this.mInputDispatchingTimeoutMillis = InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final void dispatchConfigurationToChild(ConfigurationContainer configurationContainer, Configuration configuration) {
        WindowState windowState = (WindowState) configurationContainer;
        if (isConfigurationDispatchPaused()) {
            return;
        }
        super.dispatchConfigurationToChild(windowState, configuration);
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    @NeverCompile
    public final void dump(PrintWriter printWriter, String str, boolean z) {
        String str2;
        SizeCompatAttributes sizeCompatAttributes;
        long uptimeMillis = SystemClock.uptimeMillis();
        printWriter.print(str);
        printWriter.print("packageName=");
        printWriter.print(this.packageName);
        printWriter.print(" processName=");
        ProcessList$$ExternalSyntheticOutline0.m(printWriter, this.processName, str, "launchedFromUid=");
        printWriter.print(this.launchedFromUid);
        printWriter.print(" launchedFromPackage=");
        printWriter.print(this.launchedFromPackage);
        printWriter.print(" launchedFromFeature=");
        printWriter.print(this.launchedFromFeatureId);
        printWriter.print(" userId=");
        BroadcastStats$$ExternalSyntheticOutline0.m(this.mUserId, printWriter, str, "app=");
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
        ProcessList$$ExternalSyntheticOutline0.m(printWriter, this.taskAffinity, str, "mActivityComponent=");
        printWriter.println(this.mActivityComponent.flattenToShortString());
        ApplicationInfo applicationInfo = this.info.applicationInfo;
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
        printWriter.print(this.mAtmService.mCompatModePackages.compatibilityInfoForPackageLocked(this.info.applicationInfo));
        printWriter.print(" theme=0x");
        printWriter.println(Integer.toHexString(this.theme));
        printWriter.println(str + "mLastReportedConfigurations:");
        this.mLastReportedConfiguration.dump(printWriter, str + "  ");
        if (Flags.activityWindowInfoFlag()) {
            printWriter.print(str);
            printWriter.print("mLastReportedActivityWindowInfo=");
            printWriter.println(this.mLastReportedActivityWindowInfo);
        }
        printWriter.print(str);
        printWriter.print("CurrentConfiguration=");
        printWriter.println(getConfiguration());
        if (!getRequestedOverrideConfiguration().equals(Configuration.EMPTY)) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "RequestedOverrideConfiguration=");
            m.append(getRequestedOverrideConfiguration());
            printWriter.println(m.toString());
        }
        if (!getResolvedOverrideConfiguration().equals(getRequestedOverrideConfiguration())) {
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str, "ResolvedOverrideConfiguration=");
            m2.append(getResolvedOverrideConfiguration());
            printWriter.println(m2.toString());
        }
        if (!matchParentBounds()) {
            StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(str, "bounds=");
            m3.append(getBounds());
            printWriter.println(m3.toString());
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
        if (taskDescription != null && (taskDescription.getIconFilename() != null || this.taskDescription.getLabel() != null || this.taskDescription.getPrimaryColor() != 0)) {
            printWriter.print(str);
            printWriter.print("taskDescription:");
            printWriter.print(" label=\"");
            printWriter.print(this.taskDescription.getLabel());
            printWriter.print("\"");
            printWriter.print(" icon=");
            printWriter.print(this.taskDescription.getInMemoryIcon() != null ? this.taskDescription.getInMemoryIcon().getByteCount() + " bytes" : "null");
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
                PendingIntentRecord pendingIntentRecord = weakReference != null ? (PendingIntentRecord) weakReference.get() : null;
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
        PopOverState popOverState = this.mPopOverState;
        if (popOverState.mOptions != null) {
            StringBuilder m4 = MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(Preconditions$$ExternalSyntheticOutline0.m(str, "PopOver="), popOverState.mIsActivated, printWriter, str, " size=land(");
            m4.append(popOverState.mOptions.mPopOverWidthDp[0]);
            m4.append(",");
            m4.append(popOverState.mOptions.mPopOverHeightDp[0]);
            m4.append(")/port(");
            m4.append(popOverState.mOptions.mPopOverWidthDp[1]);
            m4.append(",");
            m4.append(popOverState.mOptions.mPopOverHeightDp[1]);
            m4.append(")");
            printWriter.println(m4.toString());
            printWriter.println(str + " margin=land(" + popOverState.mOptions.mPopOverAnchorMarginDp[0] + ")/port(" + popOverState.mOptions.mPopOverAnchorMarginDp[1] + ")");
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" position=land(0x");
            BatteryService$$ExternalSyntheticOutline0.m(popOverState.mOptions.mPopOverAnchorPosition[0], sb, ")/port(0x");
            sb.append(Integer.toHexString(popOverState.mOptions.mPopOverAnchorPosition[1]));
            sb.append(")");
            printWriter.println(sb.toString());
            if (popOverState.mIsActivated) {
                StringBuilder m5 = Preconditions$$ExternalSyntheticOutline0.m(str, " inherit=");
                m5.append(popOverState.mOptions.mPopOverInheritOptions);
                m5.append(", mLastOccludesParent=");
                m5.append(popOverState.mLastOccludesParent);
                m5.append(", isBelowAnotherOpaquePopOver=");
                ActivityRecord activityRecord = popOverState.mActivityRecord;
                Rect bounds = activityRecord.getBounds();
                Task task = activityRecord.task;
                m5.append((task == null || task.getActivity(new PopOverState$$ExternalSyntheticLambda0(1, bounds), activityRecord, false, false) == null) ? false : true);
                m5.append(", isAboveAnotherOpaquePopOver=");
                m5.append(popOverState.isAboveAnotherOpaquePopOver());
                printWriter.println(m5.toString());
            }
        }
        if (this.mPendingRemoteAnimation != null) {
            printWriter.print(str);
            printWriter.print("pendingRemoteAnimationCallingPid=");
            printWriter.println(this.mPendingRemoteAnimation.getCallingPid());
        }
        if (this.mPendingRemoteTransition != null) {
            StringBuilder m6 = Preconditions$$ExternalSyntheticOutline0.m(str, " pendingRemoteTransition=");
            m6.append(this.mPendingRemoteTransition.getRemoteTransition());
            printWriter.print(m6.toString());
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
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, str, "keysPaused=", this.finishing);
        printWriter.print(this.keysPaused);
        printWriter.print(" inHistory=");
        printWriter.print(this.inHistory);
        printWriter.print(" idle=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, str, "occludesParent=", this.idle);
        printWriter.print(occludesParent(false));
        printWriter.print(" noDisplay=");
        printWriter.print(this.noDisplay);
        printWriter.print(" immersive=");
        printWriter.print(this.immersive);
        printWriter.print(" launchMode=");
        printWriter.println(this.launchMode);
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
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, str, "mStyleFillsParent=", this.mOccludesParent);
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, str, "mStyleFloating=", this.mStyleFillsParent);
        printWriter.println(this.mStyleFloating);
        printWriter.print(str);
        printWriter.print("overrideOrientation=");
        printWriter.println(ActivityInfo.screenOrientationToString(getOverrideOrientation()));
        printWriter.print(str);
        printWriter.print("requestedOrientation=");
        printWriter.println(ActivityInfo.screenOrientationToString(super.getOverrideOrientation()));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("mVisibleRequested=");
        sb2.append(this.mVisibleRequested);
        sb2.append(" mVisible=");
        sb2.append(this.mVisible);
        sb2.append(" mClientVisible=");
        sb2.append(this.mClientVisible);
        sb2.append(this.mDeferHidingClient ? " mDeferHidingClient=" + this.mDeferHidingClient : "");
        sb2.append(" reportedDrawn=");
        sb2.append(this.mReportedDrawn);
        sb2.append(" reportedVisible=");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb2, this.reportedVisible, printWriter);
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
        if (this.mNumInterestingWindows != 0 || this.mNumDrawnWindows != 0 || this.allDrawn || this.mLastAllDrawn) {
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
        if (this.mStartingWindow != null || this.mStartingData != null || this.mStartingSurface != null || this.startingMoved || this.mVisibleSetFromTransferredStartingWindow) {
            printWriter.print(str);
            printWriter.print("startingWindow=");
            printWriter.print(this.mStartingWindow);
            printWriter.print(" startingSurface=");
            printWriter.print(this.mStartingSurface);
            printWriter.print(" startingDisplayed=");
            printWriter.print(isStartingWindowDisplayed());
            printWriter.print(" startingMoved=");
            printWriter.print(this.startingMoved);
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder(" mVisibleSetFromTransferredStartingWindow="), this.mVisibleSetFromTransferredStartingWindow, printWriter);
        }
        if (this.mPendingRelaunchCount != 0) {
            printWriter.print(str);
            printWriter.print("mPendingRelaunchCount=");
            printWriter.println(this.mPendingRelaunchCount);
        }
        if (this.mRemovingFromDisplay) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(Preconditions$$ExternalSyntheticOutline0.m(str, "mRemovingFromDisplay="), this.mRemovingFromDisplay, printWriter);
        }
        if (CoreRune.MT_SIZE_COMPAT_POLICY && (sizeCompatAttributes = this.mSizeCompatAttributes) != null && sizeCompatAttributes.hasBounds()) {
            SizeCompatAttributes sizeCompatAttributes2 = this.mSizeCompatAttributes;
            sizeCompatAttributes2.getClass();
            printWriter.print(str);
            printWriter.print("SizeCompatAttributes: ");
            printWriter.print("mScale=" + sizeCompatAttributes2.mScale);
            printWriter.print(", mBounds=");
            printWriter.print("(" + sizeCompatAttributes2.mBounds.left + "," + sizeCompatAttributes2.mBounds.top + ")");
            printWriter.print("(" + sizeCompatAttributes2.mBounds.width() + "x" + sizeCompatAttributes2.mBounds.height() + ")");
            StringBuilder sb3 = new StringBuilder(", mReason=");
            sizeCompatAttributes2.mReason.getClass();
            sb3.append(SizeCompatInfo.sizeCompatModeToString(1));
            printWriter.print(sb3.toString());
            printWriter.println();
        }
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
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(Preconditions$$ExternalSyntheticOutline0.m(str, "mDeferHidingClient="), this.mDeferHidingClient, printWriter);
        }
        if (this.mServiceConnectionsHolder != null) {
            printWriter.print(str);
            printWriter.print("connections=");
            printWriter.println(this.mServiceConnectionsHolder);
        }
        if (this.info != null) {
            StringBuilder m7 = Preconditions$$ExternalSyntheticOutline0.m(str, "resizeMode=");
            m7.append(ActivityInfo.resizeModeToString(this.info.resizeMode));
            printWriter.println(m7.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append("mLastReportedMultiWindowMode=");
            sb4.append(this.mLastReportedMultiWindowMode);
            sb4.append(" mLastReportedPictureInPictureMode=");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb4, this.mLastReportedPictureInPictureMode, printWriter);
            if (this.info.supportsPictureInPicture()) {
                StringBuilder m8 = Preconditions$$ExternalSyntheticOutline0.m(str, "supportsPictureInPicture=");
                m8.append(this.info.supportsPictureInPicture());
                printWriter.println(m8.toString());
                StringBuilder sb5 = new StringBuilder();
                sb5.append(str);
                sb5.append("supportsEnterPipOnTaskSwitch: ");
                StringBuilder m9 = MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(sb5, this.supportsEnterPipOnTaskSwitch, printWriter, str, "mPauseSchedulePendingForPip="), this.mPauseSchedulePendingForPip, printWriter, str, "supportPictureInPictureAppOps=");
                m9.append(checkEnterPictureInPictureAppOpsState());
                printWriter.println(m9.toString());
            }
            if (getMaxAspectRatio() != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                StringBuilder m10 = Preconditions$$ExternalSyntheticOutline0.m(str, "maxAspectRatio=");
                m10.append(getMaxAspectRatio());
                printWriter.println(m10.toString());
            }
            float minAspectRatio = getMinAspectRatio();
            if (minAspectRatio != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                printWriter.println(str + "minAspectRatio=" + minAspectRatio);
            }
            if (minAspectRatio != this.info.getManifestMinAspectRatio()) {
                StringBuilder m11 = Preconditions$$ExternalSyntheticOutline0.m(str, "manifestMinAspectRatio=");
                m11.append(this.info.getManifestMinAspectRatio());
                printWriter.println(m11.toString());
            }
            if (CoreRune.MT_APP_COMPAT_ASPECT_RATIO_POLICY && this.mAppCompatController.mAppCompatAspectRatioPolicy.isUserOrSystemMinAspectRatioApplied()) {
                float f = this.mAppCompatController.mAppCompatAspectRatioPolicy.mUserOrSystemMinAspectRatio;
                if (f != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    printWriter.println(str + "userOrSystemMinAspectRatio=" + f);
                }
            }
            StringBuilder m12 = Preconditions$$ExternalSyntheticOutline0.m(str, "supportsSizeChanges=");
            m12.append(ActivityInfo.sizeChangesSupportModeToString(supportsSizeChanges()));
            printWriter.println(m12.toString());
            if (this.info.configChanges != 0) {
                StringBuilder m13 = Preconditions$$ExternalSyntheticOutline0.m(str, "configChanges=0x");
                m13.append(Integer.toHexString(this.info.configChanges));
                printWriter.println(m13.toString());
            }
            StringBuilder m14 = Preconditions$$ExternalSyntheticOutline0.m(str, "neverSandboxDisplayApis=");
            m14.append(this.info.neverSandboxDisplayApis(sConstrainDisplayApisConfig));
            printWriter.println(m14.toString());
            printWriter.println(str + "alwaysSandboxDisplayApis=" + this.info.alwaysSandboxDisplayApis(sConstrainDisplayApisConfig));
            if (this.mIgnoreDevSettingForNonResizable) {
                printWriter.println(str + "mIgnoreDevSettingForNonResizable=true");
            }
            if (this.mIsAllowedSeamlessRotation) {
                printWriter.println(str + "mIsAllowedSeamlessRotation=true");
            }
            if (this.info.metaData != null) {
                StringBuilder m15 = Preconditions$$ExternalSyntheticOutline0.m(str, "activityMetaData=");
                m15.append(this.info.metaData);
                printWriter.println(m15.toString());
            }
        }
        if (this.mLastParentBeforePip != null) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(Preconditions$$ExternalSyntheticOutline0.m(str, "lastParentTaskIdBeforePip="), this.mLastParentBeforePip.mTaskId, printWriter);
        }
        if (this.mLaunchIntoPipHostActivity != null) {
            StringBuilder m16 = Preconditions$$ExternalSyntheticOutline0.m(str, "launchIntoPipHostActivity=");
            m16.append(this.mLaunchIntoPipHostActivity);
            printWriter.println(m16.toString());
        }
        if (this.mWaitForEnteringPinnedMode) {
            printWriter.print(str);
            printWriter.println("mWaitForEnteringPinnedMode=true");
        }
        AppCompatController appCompatController = this.mAppCompatController;
        TransparentPolicy transparentPolicy = appCompatController.mTransparentPolicy;
        transparentPolicy.getClass();
        printWriter.println(str + "isTransparentPolicyRunning=" + transparentPolicy.mTransparentPolicyState.isRunning());
        AppCompatLetterboxPolicy appCompatLetterboxPolicy = appCompatController.mAppCompatLetterboxPolicy;
        ActivityRecord activityRecord2 = appCompatLetterboxPolicy.mActivityRecord;
        WindowState findMainWindow = activityRecord2.findMainWindow(true);
        if (findMainWindow != null) {
            boolean areAppWindowBoundsLetterboxed = findMainWindow.areAppWindowBoundsLetterboxed();
            printWriter.println(str + "areBoundsLetterboxed=" + areAppWindowBoundsLetterboxed);
            printWriter.println(str + "isLetterboxRunning=" + appCompatLetterboxPolicy.mLetterboxPolicyState.isRunning());
            if (areAppWindowBoundsLetterboxed) {
                StringBuilder m17 = Preconditions$$ExternalSyntheticOutline0.m(str, "  letterboxReason=");
                if (activityRecord2.inSizeCompatMode()) {
                    str2 = "SIZE_COMPAT_MODE";
                } else {
                    AppCompatAspectRatioPolicy appCompatAspectRatioPolicy = activityRecord2.mAppCompatController.mAppCompatAspectRatioPolicy;
                    str2 = appCompatAspectRatioPolicy.isLetterboxedForFixedOrientationAndAspectRatio() ? "FIXED_ORIENTATION" : findMainWindow.isLetterboxedForDisplayCutout() ? "DISPLAY_CUTOUT" : appCompatAspectRatioPolicy.mAppCompatAspectRatioState.mLetterboxBoundsForAspectRatio != null ? "ASPECT_RATIO" : "UNKNOWN_REASON";
                }
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(m17, str2, printWriter);
                ActivityRecord activityRecord3 = activityRecord2.mAppCompatController.mAppCompatReachabilityPolicy.mActivityRecord;
                AppCompatReachabilityOverrides appCompatReachabilityOverrides = activityRecord3.mAppCompatController.mAppCompatOverrides.mAppCompatReachabilityOverrides;
                StringBuilder m18 = Preconditions$$ExternalSyntheticOutline0.m(str, "  isVerticalThinLetterboxed=");
                m18.append(appCompatReachabilityOverrides.isVerticalThinLetterboxed());
                printWriter.println(m18.toString());
                printWriter.println(str + "  isHorizontalThinLetterboxed=" + appCompatReachabilityOverrides.isHorizontalThinLetterboxed());
                printWriter.println(str + "  isHorizontalReachabilityEnabled=" + appCompatReachabilityOverrides.isHorizontalReachabilityEnabled(appCompatReachabilityOverrides.mActivityRecord.getParent().getConfiguration()));
                printWriter.println(str + "  isVerticalReachabilityEnabled=" + appCompatReachabilityOverrides.isVerticalReachabilityEnabled(appCompatReachabilityOverrides.mActivityRecord.getParent().getConfiguration()));
                printWriter.println(str + "  letterboxHorizontalPositionMultiplier=" + appCompatReachabilityOverrides.getHorizontalPositionMultiplier(activityRecord3.getParent().getConfiguration()));
                StringBuilder sb6 = new StringBuilder();
                sb6.append(str);
                sb6.append("  letterboxVerticalPositionMultiplier=");
                Configuration configuration = activityRecord3.getParent().getConfiguration();
                boolean isDisplayFullScreenAndInPosture = appCompatReachabilityOverrides.mAppCompatDeviceStateQuery.isDisplayFullScreenAndInPosture(true);
                boolean isVerticalReachabilityEnabled = appCompatReachabilityOverrides.isVerticalReachabilityEnabled(configuration);
                AppCompatConfiguration appCompatConfiguration = appCompatReachabilityOverrides.mAppCompatConfiguration;
                AggressivePolicyHandler$$ExternalSyntheticOutline0.m(sb6, isVerticalReachabilityEnabled ? appCompatConfiguration.getVerticalMultiplierForReachability(isDisplayFullScreenAndInPosture) : isDisplayFullScreenAndInPosture ? appCompatConfiguration.mLetterboxTabletopModePositionMultiplier : appCompatConfiguration.mLetterboxVerticalPositionMultiplier, printWriter);
                AppCompatLetterboxOverrides appCompatLetterboxOverrides = activityRecord2.mAppCompatController.mAppCompatOverrides.mAppCompatLetterboxOverrides;
                StringBuilder m19 = Preconditions$$ExternalSyntheticOutline0.m(str, "  letterboxBackgroundColor=");
                m19.append(Integer.toHexString(appCompatLetterboxOverrides.getLetterboxBackgroundColor().toArgb()));
                printWriter.println(m19.toString());
                printWriter.println(str + "  letterboxBackgroundType=" + AppCompatConfiguration.letterboxBackgroundTypeToString(appCompatLetterboxOverrides.getLetterboxBackgroundType()));
                printWriter.println(str + "  letterboxCornerRadius=" + appCompatLetterboxPolicy.mAppCompatRoundedCorners.getRoundedCornersRadius(findMainWindow));
                if (appCompatLetterboxOverrides.getLetterboxBackgroundType() == 3) {
                    StringBuilder m20 = Preconditions$$ExternalSyntheticOutline0.m(str, "  isLetterboxWallpaperBlurSupported=");
                    m20.append(appCompatLetterboxOverrides.isLetterboxWallpaperBlurSupported());
                    printWriter.println(m20.toString());
                    printWriter.println(str + "  letterboxBackgroundWallpaperDarkScrimAlpha=" + appCompatLetterboxOverrides.getLetterboxWallpaperDarkScrimAlpha());
                    printWriter.println(str + "  letterboxBackgroundWallpaperBlurRadius=" + appCompatLetterboxOverrides.getLetterboxWallpaperBlurRadiusPx());
                }
                AppCompatConfiguration appCompatConfiguration2 = appCompatLetterboxPolicy.mAppCompatConfiguration;
                appCompatConfiguration2.getClass();
                StringBuilder sb7 = new StringBuilder();
                sb7.append(str);
                sb7.append("  letterboxPositionForHorizontalReachability=");
                AppCompatConfigurationPersister appCompatConfigurationPersister = appCompatConfiguration2.mAppCompatConfigurationPersister;
                sb7.append(AppCompatConfiguration.letterboxHorizontalReachabilityPositionToString(appCompatConfigurationPersister.mLetterboxPositionForHorizontalReachability));
                printWriter.println(sb7.toString());
                printWriter.println(str + "  letterboxPositionForVerticalReachability=" + AppCompatConfiguration.letterboxVerticalReachabilityPositionToString(appCompatConfigurationPersister.mLetterboxPositionForVerticalReachability));
                printWriter.println(str + "  fixedOrientationLetterboxAspectRatio=" + appCompatConfiguration2.mFixedOrientationLetterboxAspectRatio);
                printWriter.println(str + "  defaultMinAspectRatioForUnresizableApps=" + appCompatConfiguration2.mDefaultMinAspectRatioForUnresizableApps);
                StringBuilder sb8 = new StringBuilder();
                sb8.append(str);
                sb8.append("  isSplitScreenAspectRatioForUnresizableAppsEnabled=");
                StringBuilder m21 = MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(sb8, appCompatConfiguration2.mIsSplitScreenAspectRatioForUnresizableAppsEnabled, printWriter, str, "  isDisplayAspectRatioEnabledForFixedOrientationLetterbox=");
                m21.append(appCompatConfiguration2.mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox);
                printWriter.println(m21.toString());
                if (appCompatLetterboxOverrides.shouldHideLetterboxSurface(findMainWindow)) {
                    printWriter.println(str + "  shouldHideLetterboxSurface=true");
                }
            }
        }
        AppCompatSizeCompatModePolicy appCompatSizeCompatModePolicy = appCompatController.mAppCompatSizeCompatModePolicy;
        if (appCompatSizeCompatModePolicy.mSizeCompatScale != 1.0f || appCompatSizeCompatModePolicy.hasSizeCompatBounds()) {
            StringBuilder m22 = Preconditions$$ExternalSyntheticOutline0.m(str, "mSizeCompatScale=");
            m22.append(appCompatSizeCompatModePolicy.mSizeCompatScale);
            m22.append(" mSizeCompatBounds=");
            m22.append(appCompatSizeCompatModePolicy.mSizeCompatBounds);
            printWriter.println(m22.toString());
        }
        if (this.mWaitForEnteringPinnedMode) {
            printWriter.println(str + "mWaitForEnteringPinnedMode=true");
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION) {
            if (this.mIsEnteringPipFromSplit) {
                printWriter.println(str + "mIsEnteringPipFromSplit=true");
            }
            if (this.mHiddenWhileEnteringPinnedMode) {
                printWriter.println(str + "mHiddenWhileEnteringPinnedMode=true");
            }
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(Preconditions$$ExternalSyntheticOutline0.m(str, "mLastSurfaceShowing="), this.mLastSurfaceShowing, printWriter);
        if (this.mAtmService.mExt.mKeepAliveActivities.get(this) != null) {
            printWriter.println(str + "keepAlive=true");
        }
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public final void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        if (i != 2 || this.mVisible) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1138166333441L, this.shortComponentName);
            super.dumpDebug(protoOutputStream, 1146756268034L, i);
            protoOutputStream.write(1133871366147L, this.mLastSurfaceShowing);
            protoOutputStream.write(1133871366148L, isWaitingForTransitionStart());
            protoOutputStream.write(1133871366149L, isAnimating(7, 17));
            WindowContainerThumbnail windowContainerThumbnail = this.mThumbnail;
            if (windowContainerThumbnail != null) {
                long start2 = protoOutputStream.start(1146756268038L);
                protoOutputStream.write(1120986464257L, windowContainerThumbnail.mWidth);
                protoOutputStream.write(1120986464258L, windowContainerThumbnail.mHeight);
                SurfaceAnimator surfaceAnimator = windowContainerThumbnail.mSurfaceAnimator;
                if (surfaceAnimator.isAnimating()) {
                    surfaceAnimator.dumpDebug(protoOutputStream, 1146756268035L);
                }
                protoOutputStream.end(start2);
            }
            protoOutputStream.write(1133871366151L, occludesParent(true));
            protoOutputStream.write(1133871366152L, this.mAppStopped);
            protoOutputStream.write(1133871366174L, !occludesParent(false));
            protoOutputStream.write(1133871366168L, this.mVisible);
            protoOutputStream.write(1133871366153L, this.mVisibleRequested);
            protoOutputStream.write(1133871366154L, this.mClientVisible);
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
                protoOutputStream.write(1120986464285L, this.app.mPid);
            }
            protoOutputStream.write(1133871366175L, this.pictureInPictureArgs.isAutoEnterEnabled());
            protoOutputStream.write(1133871366176L, inSizeCompatMode());
            protoOutputStream.write(1108101562401L, getMinAspectRatio());
            protoOutputStream.write(1133871366178L, providesMaxBounds());
            protoOutputStream.write(1133871366179L, this.mEnableRecentsScreenshot);
            protoOutputStream.write(1120986464292L, this.mLastDropInputMode);
            protoOutputStream.write(1120986464293L, getOverrideOrientation());
            protoOutputStream.write(1133871366182L, shouldSendCompatFakeFocus());
            protoOutputStream.write(1133871366183L, this.mAppCompatController.mAppCompatOverrides.mAppCompatCameraOverrides.shouldForceRotateForCameraCompat());
            AppCompatCameraOverrides appCompatCameraOverrides = this.mAppCompatController.mAppCompatOverrides.mAppCompatCameraOverrides;
            boolean isChangeEnabled = appCompatCameraOverrides.mActivityRecord.info.isChangeEnabled(264304459L);
            OptPropFactory.OptProp optProp = appCompatCameraOverrides.mCameraCompatAllowRefreshOptProp;
            protoOutputStream.write(1133871366184L, (!optProp.mCondition.getAsBoolean() || optProp.getValue() == 0 || isChangeEnabled) ? false : true);
            AppCompatCameraOverrides appCompatCameraOverrides2 = this.mAppCompatController.mAppCompatOverrides.mAppCompatCameraOverrides;
            protoOutputStream.write(1133871366185L, appCompatCameraOverrides2.mCameraCompatEnableRefreshViaPauseOptProp.shouldEnableWithOverrideAndProperty(appCompatCameraOverrides2.mActivityRecord.info.isChangeEnabled(264301586L)));
            AppCompatAspectRatioOverrides appCompatAspectRatioOverrides = this.mAppCompatController.mAppCompatOverrides.mAppCompatAspectRatioOverrides;
            protoOutputStream.write(1133871366186L, appCompatAspectRatioOverrides.mAllowMinAspectRatioOverrideOptProp.shouldEnableWithOptInOverrideAndOptOutProperty(appCompatAspectRatioOverrides.mActivityRecord.info.isChangeEnabled(174042980L)));
            protoOutputStream.write(1133871366187L, this.mAppCompatController.mAppCompatOverrides.mAppCompatOrientationOverrides.shouldIgnoreOrientationRequestLoop());
            AppCompatResizeOverrides appCompatResizeOverrides = this.mAppCompatController.mAppCompatOverrides.mAppCompatResizeOverrides;
            protoOutputStream.write(1133871366188L, appCompatResizeOverrides.mAllowForceResizeOverrideOptProp.shouldEnableWithOptInOverrideAndOptOutProperty(appCompatResizeOverrides.mActivityRecord.info.isChangeEnabled(174042936L)));
            protoOutputStream.write(1133871366189L, this.mAppCompatController.mAppCompatOverrides.mAppCompatAspectRatioOverrides.shouldEnableUserAspectRatioSettings());
            AppCompatAspectRatioOverrides appCompatAspectRatioOverrides2 = this.mAppCompatController.mAppCompatOverrides.mAppCompatAspectRatioOverrides;
            protoOutputStream.write(1133871366190L, (appCompatAspectRatioOverrides2.mAllowUserAspectRatioOverrideOptProp.isFalse() || appCompatAspectRatioOverrides2.mAllowUserAspectRatioFullscreenOverrideOptProp.isFalse() || !appCompatAspectRatioOverrides2.mAppCompatConfiguration.isUserAppAspectRatioFullscreenEnabled()) ? false : true);
            protoOutputStream.end(start);
        }
    }

    public final boolean ensureActivityConfiguration(boolean z) {
        State state;
        boolean z2 = getRootTask().mConfigWillChange;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_CONFIGURATION_enabled;
        if (z2) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_CONFIGURATION, -8630021188868292872L, 0, null, String.valueOf(this));
            }
            return true;
        }
        if (this.finishing) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_CONFIGURATION, -3976984054291875926L, 0, null, String.valueOf(this));
            }
            return true;
        }
        if (isState(State.DESTROYED)) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_CONFIGURATION, -1036762753077003128L, 0, null, String.valueOf(this));
            }
            return true;
        }
        if (z || !((state = this.mState) == State.STOPPING || state == State.STOPPED || !shouldBeVisible(false))) {
            if (isConfigurationDispatchPaused()) {
                return true;
            }
            return updateReportedConfigurationAndSend();
        }
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_CONFIGURATION, -6543078196636665108L, 0, null, String.valueOf(this));
        }
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean fillsParent() {
        return occludesParent(true);
    }

    public final WindowState findMainWindow(boolean z) {
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

    public final void finishActivityResults(final int i, final Intent intent, final NeededUriGrants neededUriGrants) {
        ActivityRecord activityRecord = this.resultTo;
        if (activityRecord != null) {
            int i2 = activityRecord.mUserId;
            int i3 = this.mUserId;
            if (i2 != i3 && intent != null) {
                intent.prepareToLeaveUser(i3);
            }
            if (this.info.applicationInfo.uid > 0) {
                ((UriGrantsManagerService.LocalService) this.mAtmService.mUgmInternal).grantUriPermissionUncheckedFromIntent(neededUriGrants, this.resultTo.getUriPermissionsLocked());
            }
            final Binder binder = new Binder();
            if (android.security.Flags.contentUriPermissionApis()) {
                try {
                    ActivityRecord activityRecord2 = this.resultTo;
                    int uid = getUid();
                    this.mAtmService.getClass();
                    activityRecord2.computeCallerInfo(binder, intent, uid, AppGlobals.getPackageManager().getNameForUid(getUid()), false);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if (this.mForceSendResultForMediaProjection || this.resultTo.isState(State.RESUMED)) {
                final ActivityRecord activityRecord3 = this.resultTo;
                this.mAtmService.mH.post(new Runnable() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityRecord activityRecord4 = ActivityRecord.this;
                        ActivityRecord activityRecord5 = activityRecord3;
                        int i4 = i;
                        Intent intent2 = intent;
                        IBinder iBinder = binder;
                        NeededUriGrants neededUriGrants2 = neededUriGrants;
                        WindowManagerGlobalLock windowManagerGlobalLock = activityRecord4.mAtmService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                activityRecord5.sendResult(activityRecord4.getUid(), activityRecord4.resultWho, activityRecord4.requestCode, i4, intent2, iBinder, neededUriGrants2, activityRecord4.mForceSendResultForMediaProjection);
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                });
            } else {
                ActivityRecord activityRecord4 = this.resultTo;
                String str = this.resultWho;
                int i4 = this.requestCode;
                activityRecord4.getClass();
                ActivityResult activityResult = new ActivityResult(this, str, i4, i, intent, binder);
                if (activityRecord4.results == null) {
                    activityRecord4.results = new ArrayList();
                }
                activityRecord4.results.add(activityResult);
            }
            this.resultTo = null;
        }
        this.results = null;
        this.pendingResults = null;
        this.newIntents = null;
        this.mIcicle = null;
        this.mHaveState = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x02da A[Catch: all -> 0x00de, TryCatch #0 {all -> 0x00de, blocks: (B:26:0x009c, B:28:0x00cd, B:30:0x00d8, B:31:0x00e1, B:33:0x00e6, B:35:0x00ec, B:36:0x00f2, B:38:0x00fb, B:43:0x0107, B:45:0x0115, B:47:0x011d, B:52:0x012b, B:53:0x0137, B:55:0x013b, B:57:0x0146, B:58:0x0159, B:60:0x015e, B:63:0x0164, B:65:0x016a, B:67:0x0170, B:69:0x0176, B:70:0x017b, B:72:0x017f, B:74:0x0183, B:76:0x0189, B:77:0x018e, B:79:0x0192, B:81:0x0198, B:83:0x01a1, B:85:0x01af, B:89:0x01b8, B:91:0x01be, B:93:0x01ca, B:95:0x01d0, B:97:0x01d6, B:99:0x01dc, B:101:0x01e0, B:104:0x01c7, B:107:0x01fb, B:110:0x0204, B:111:0x021c, B:113:0x022a, B:115:0x0230, B:116:0x024a, B:118:0x0252, B:120:0x025a, B:122:0x0260, B:123:0x0262, B:127:0x0291, B:129:0x029a, B:131:0x02a4, B:134:0x02aa, B:136:0x02b4, B:137:0x02b7, B:139:0x02bb, B:141:0x02bf, B:144:0x02c7, B:147:0x02d2, B:149:0x02d6, B:152:0x026b, B:154:0x026f, B:155:0x0285, B:156:0x02da, B:158:0x02e2, B:160:0x02e6, B:162:0x02ee, B:164:0x02f4, B:165:0x02fa, B:166:0x0306, B:170:0x0314, B:172:0x031c, B:173:0x0321, B:175:0x0328, B:179:0x033a, B:181:0x033e, B:182:0x014a, B:184:0x0152), top: B:25:0x009c }] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x014a A[Catch: all -> 0x00de, TryCatch #0 {all -> 0x00de, blocks: (B:26:0x009c, B:28:0x00cd, B:30:0x00d8, B:31:0x00e1, B:33:0x00e6, B:35:0x00ec, B:36:0x00f2, B:38:0x00fb, B:43:0x0107, B:45:0x0115, B:47:0x011d, B:52:0x012b, B:53:0x0137, B:55:0x013b, B:57:0x0146, B:58:0x0159, B:60:0x015e, B:63:0x0164, B:65:0x016a, B:67:0x0170, B:69:0x0176, B:70:0x017b, B:72:0x017f, B:74:0x0183, B:76:0x0189, B:77:0x018e, B:79:0x0192, B:81:0x0198, B:83:0x01a1, B:85:0x01af, B:89:0x01b8, B:91:0x01be, B:93:0x01ca, B:95:0x01d0, B:97:0x01d6, B:99:0x01dc, B:101:0x01e0, B:104:0x01c7, B:107:0x01fb, B:110:0x0204, B:111:0x021c, B:113:0x022a, B:115:0x0230, B:116:0x024a, B:118:0x0252, B:120:0x025a, B:122:0x0260, B:123:0x0262, B:127:0x0291, B:129:0x029a, B:131:0x02a4, B:134:0x02aa, B:136:0x02b4, B:137:0x02b7, B:139:0x02bb, B:141:0x02bf, B:144:0x02c7, B:147:0x02d2, B:149:0x02d6, B:152:0x026b, B:154:0x026f, B:155:0x0285, B:156:0x02da, B:158:0x02e2, B:160:0x02e6, B:162:0x02ee, B:164:0x02f4, B:165:0x02fa, B:166:0x0306, B:170:0x0314, B:172:0x031c, B:173:0x0321, B:175:0x0328, B:179:0x033a, B:181:0x033e, B:182:0x014a, B:184:0x0152), top: B:25:0x009c }] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x012b A[Catch: all -> 0x00de, TryCatch #0 {all -> 0x00de, blocks: (B:26:0x009c, B:28:0x00cd, B:30:0x00d8, B:31:0x00e1, B:33:0x00e6, B:35:0x00ec, B:36:0x00f2, B:38:0x00fb, B:43:0x0107, B:45:0x0115, B:47:0x011d, B:52:0x012b, B:53:0x0137, B:55:0x013b, B:57:0x0146, B:58:0x0159, B:60:0x015e, B:63:0x0164, B:65:0x016a, B:67:0x0170, B:69:0x0176, B:70:0x017b, B:72:0x017f, B:74:0x0183, B:76:0x0189, B:77:0x018e, B:79:0x0192, B:81:0x0198, B:83:0x01a1, B:85:0x01af, B:89:0x01b8, B:91:0x01be, B:93:0x01ca, B:95:0x01d0, B:97:0x01d6, B:99:0x01dc, B:101:0x01e0, B:104:0x01c7, B:107:0x01fb, B:110:0x0204, B:111:0x021c, B:113:0x022a, B:115:0x0230, B:116:0x024a, B:118:0x0252, B:120:0x025a, B:122:0x0260, B:123:0x0262, B:127:0x0291, B:129:0x029a, B:131:0x02a4, B:134:0x02aa, B:136:0x02b4, B:137:0x02b7, B:139:0x02bb, B:141:0x02bf, B:144:0x02c7, B:147:0x02d2, B:149:0x02d6, B:152:0x026b, B:154:0x026f, B:155:0x0285, B:156:0x02da, B:158:0x02e2, B:160:0x02e6, B:162:0x02ee, B:164:0x02f4, B:165:0x02fa, B:166:0x0306, B:170:0x0314, B:172:0x031c, B:173:0x0321, B:175:0x0328, B:179:0x033a, B:181:0x033e, B:182:0x014a, B:184:0x0152), top: B:25:0x009c }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x013b A[Catch: all -> 0x00de, TryCatch #0 {all -> 0x00de, blocks: (B:26:0x009c, B:28:0x00cd, B:30:0x00d8, B:31:0x00e1, B:33:0x00e6, B:35:0x00ec, B:36:0x00f2, B:38:0x00fb, B:43:0x0107, B:45:0x0115, B:47:0x011d, B:52:0x012b, B:53:0x0137, B:55:0x013b, B:57:0x0146, B:58:0x0159, B:60:0x015e, B:63:0x0164, B:65:0x016a, B:67:0x0170, B:69:0x0176, B:70:0x017b, B:72:0x017f, B:74:0x0183, B:76:0x0189, B:77:0x018e, B:79:0x0192, B:81:0x0198, B:83:0x01a1, B:85:0x01af, B:89:0x01b8, B:91:0x01be, B:93:0x01ca, B:95:0x01d0, B:97:0x01d6, B:99:0x01dc, B:101:0x01e0, B:104:0x01c7, B:107:0x01fb, B:110:0x0204, B:111:0x021c, B:113:0x022a, B:115:0x0230, B:116:0x024a, B:118:0x0252, B:120:0x025a, B:122:0x0260, B:123:0x0262, B:127:0x0291, B:129:0x029a, B:131:0x02a4, B:134:0x02aa, B:136:0x02b4, B:137:0x02b7, B:139:0x02bb, B:141:0x02bf, B:144:0x02c7, B:147:0x02d2, B:149:0x02d6, B:152:0x026b, B:154:0x026f, B:155:0x0285, B:156:0x02da, B:158:0x02e2, B:160:0x02e6, B:162:0x02ee, B:164:0x02f4, B:165:0x02fa, B:166:0x0306, B:170:0x0314, B:172:0x031c, B:173:0x0321, B:175:0x0328, B:179:0x033a, B:181:0x033e, B:182:0x014a, B:184:0x0152), top: B:25:0x009c }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0146 A[Catch: all -> 0x00de, TryCatch #0 {all -> 0x00de, blocks: (B:26:0x009c, B:28:0x00cd, B:30:0x00d8, B:31:0x00e1, B:33:0x00e6, B:35:0x00ec, B:36:0x00f2, B:38:0x00fb, B:43:0x0107, B:45:0x0115, B:47:0x011d, B:52:0x012b, B:53:0x0137, B:55:0x013b, B:57:0x0146, B:58:0x0159, B:60:0x015e, B:63:0x0164, B:65:0x016a, B:67:0x0170, B:69:0x0176, B:70:0x017b, B:72:0x017f, B:74:0x0183, B:76:0x0189, B:77:0x018e, B:79:0x0192, B:81:0x0198, B:83:0x01a1, B:85:0x01af, B:89:0x01b8, B:91:0x01be, B:93:0x01ca, B:95:0x01d0, B:97:0x01d6, B:99:0x01dc, B:101:0x01e0, B:104:0x01c7, B:107:0x01fb, B:110:0x0204, B:111:0x021c, B:113:0x022a, B:115:0x0230, B:116:0x024a, B:118:0x0252, B:120:0x025a, B:122:0x0260, B:123:0x0262, B:127:0x0291, B:129:0x029a, B:131:0x02a4, B:134:0x02aa, B:136:0x02b4, B:137:0x02b7, B:139:0x02bb, B:141:0x02bf, B:144:0x02c7, B:147:0x02d2, B:149:0x02d6, B:152:0x026b, B:154:0x026f, B:155:0x0285, B:156:0x02da, B:158:0x02e2, B:160:0x02e6, B:162:0x02ee, B:164:0x02f4, B:165:0x02fa, B:166:0x0306, B:170:0x0314, B:172:0x031c, B:173:0x0321, B:175:0x0328, B:179:0x033a, B:181:0x033e, B:182:0x014a, B:184:0x0152), top: B:25:0x009c }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x015e A[Catch: all -> 0x00de, TryCatch #0 {all -> 0x00de, blocks: (B:26:0x009c, B:28:0x00cd, B:30:0x00d8, B:31:0x00e1, B:33:0x00e6, B:35:0x00ec, B:36:0x00f2, B:38:0x00fb, B:43:0x0107, B:45:0x0115, B:47:0x011d, B:52:0x012b, B:53:0x0137, B:55:0x013b, B:57:0x0146, B:58:0x0159, B:60:0x015e, B:63:0x0164, B:65:0x016a, B:67:0x0170, B:69:0x0176, B:70:0x017b, B:72:0x017f, B:74:0x0183, B:76:0x0189, B:77:0x018e, B:79:0x0192, B:81:0x0198, B:83:0x01a1, B:85:0x01af, B:89:0x01b8, B:91:0x01be, B:93:0x01ca, B:95:0x01d0, B:97:0x01d6, B:99:0x01dc, B:101:0x01e0, B:104:0x01c7, B:107:0x01fb, B:110:0x0204, B:111:0x021c, B:113:0x022a, B:115:0x0230, B:116:0x024a, B:118:0x0252, B:120:0x025a, B:122:0x0260, B:123:0x0262, B:127:0x0291, B:129:0x029a, B:131:0x02a4, B:134:0x02aa, B:136:0x02b4, B:137:0x02b7, B:139:0x02bb, B:141:0x02bf, B:144:0x02c7, B:147:0x02d2, B:149:0x02d6, B:152:0x026b, B:154:0x026f, B:155:0x0285, B:156:0x02da, B:158:0x02e2, B:160:0x02e6, B:162:0x02ee, B:164:0x02f4, B:165:0x02fa, B:166:0x0306, B:170:0x0314, B:172:0x031c, B:173:0x0321, B:175:0x0328, B:179:0x033a, B:181:0x033e, B:182:0x014a, B:184:0x0152), top: B:25:0x009c }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x017f A[Catch: all -> 0x00de, TryCatch #0 {all -> 0x00de, blocks: (B:26:0x009c, B:28:0x00cd, B:30:0x00d8, B:31:0x00e1, B:33:0x00e6, B:35:0x00ec, B:36:0x00f2, B:38:0x00fb, B:43:0x0107, B:45:0x0115, B:47:0x011d, B:52:0x012b, B:53:0x0137, B:55:0x013b, B:57:0x0146, B:58:0x0159, B:60:0x015e, B:63:0x0164, B:65:0x016a, B:67:0x0170, B:69:0x0176, B:70:0x017b, B:72:0x017f, B:74:0x0183, B:76:0x0189, B:77:0x018e, B:79:0x0192, B:81:0x0198, B:83:0x01a1, B:85:0x01af, B:89:0x01b8, B:91:0x01be, B:93:0x01ca, B:95:0x01d0, B:97:0x01d6, B:99:0x01dc, B:101:0x01e0, B:104:0x01c7, B:107:0x01fb, B:110:0x0204, B:111:0x021c, B:113:0x022a, B:115:0x0230, B:116:0x024a, B:118:0x0252, B:120:0x025a, B:122:0x0260, B:123:0x0262, B:127:0x0291, B:129:0x029a, B:131:0x02a4, B:134:0x02aa, B:136:0x02b4, B:137:0x02b7, B:139:0x02bb, B:141:0x02bf, B:144:0x02c7, B:147:0x02d2, B:149:0x02d6, B:152:0x026b, B:154:0x026f, B:155:0x0285, B:156:0x02da, B:158:0x02e2, B:160:0x02e6, B:162:0x02ee, B:164:0x02f4, B:165:0x02fa, B:166:0x0306, B:170:0x0314, B:172:0x031c, B:173:0x0321, B:175:0x0328, B:179:0x033a, B:181:0x033e, B:182:0x014a, B:184:0x0152), top: B:25:0x009c }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0192 A[Catch: all -> 0x00de, TryCatch #0 {all -> 0x00de, blocks: (B:26:0x009c, B:28:0x00cd, B:30:0x00d8, B:31:0x00e1, B:33:0x00e6, B:35:0x00ec, B:36:0x00f2, B:38:0x00fb, B:43:0x0107, B:45:0x0115, B:47:0x011d, B:52:0x012b, B:53:0x0137, B:55:0x013b, B:57:0x0146, B:58:0x0159, B:60:0x015e, B:63:0x0164, B:65:0x016a, B:67:0x0170, B:69:0x0176, B:70:0x017b, B:72:0x017f, B:74:0x0183, B:76:0x0189, B:77:0x018e, B:79:0x0192, B:81:0x0198, B:83:0x01a1, B:85:0x01af, B:89:0x01b8, B:91:0x01be, B:93:0x01ca, B:95:0x01d0, B:97:0x01d6, B:99:0x01dc, B:101:0x01e0, B:104:0x01c7, B:107:0x01fb, B:110:0x0204, B:111:0x021c, B:113:0x022a, B:115:0x0230, B:116:0x024a, B:118:0x0252, B:120:0x025a, B:122:0x0260, B:123:0x0262, B:127:0x0291, B:129:0x029a, B:131:0x02a4, B:134:0x02aa, B:136:0x02b4, B:137:0x02b7, B:139:0x02bb, B:141:0x02bf, B:144:0x02c7, B:147:0x02d2, B:149:0x02d6, B:152:0x026b, B:154:0x026f, B:155:0x0285, B:156:0x02da, B:158:0x02e2, B:160:0x02e6, B:162:0x02ee, B:164:0x02f4, B:165:0x02fa, B:166:0x0306, B:170:0x0314, B:172:0x031c, B:173:0x0321, B:175:0x0328, B:179:0x033a, B:181:0x033e, B:182:0x014a, B:184:0x0152), top: B:25:0x009c }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01b8 A[Catch: all -> 0x00de, TryCatch #0 {all -> 0x00de, blocks: (B:26:0x009c, B:28:0x00cd, B:30:0x00d8, B:31:0x00e1, B:33:0x00e6, B:35:0x00ec, B:36:0x00f2, B:38:0x00fb, B:43:0x0107, B:45:0x0115, B:47:0x011d, B:52:0x012b, B:53:0x0137, B:55:0x013b, B:57:0x0146, B:58:0x0159, B:60:0x015e, B:63:0x0164, B:65:0x016a, B:67:0x0170, B:69:0x0176, B:70:0x017b, B:72:0x017f, B:74:0x0183, B:76:0x0189, B:77:0x018e, B:79:0x0192, B:81:0x0198, B:83:0x01a1, B:85:0x01af, B:89:0x01b8, B:91:0x01be, B:93:0x01ca, B:95:0x01d0, B:97:0x01d6, B:99:0x01dc, B:101:0x01e0, B:104:0x01c7, B:107:0x01fb, B:110:0x0204, B:111:0x021c, B:113:0x022a, B:115:0x0230, B:116:0x024a, B:118:0x0252, B:120:0x025a, B:122:0x0260, B:123:0x0262, B:127:0x0291, B:129:0x029a, B:131:0x02a4, B:134:0x02aa, B:136:0x02b4, B:137:0x02b7, B:139:0x02bb, B:141:0x02bf, B:144:0x02c7, B:147:0x02d2, B:149:0x02d6, B:152:0x026b, B:154:0x026f, B:155:0x0285, B:156:0x02da, B:158:0x02e2, B:160:0x02e6, B:162:0x02ee, B:164:0x02f4, B:165:0x02fa, B:166:0x0306, B:170:0x0314, B:172:0x031c, B:173:0x0321, B:175:0x0328, B:179:0x033a, B:181:0x033e, B:182:0x014a, B:184:0x0152), top: B:25:0x009c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int finishIfPossible(int r24, android.content.Intent r25, com.android.server.uri.NeededUriGrants r26, java.lang.String r27, boolean r28) {
        /*
            Method dump skipped, instructions count: 857
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.finishIfPossible(int, android.content.Intent, com.android.server.uri.NeededUriGrants, java.lang.String, boolean):int");
    }

    public final int finishIfPossible(String str, boolean z) {
        return finishIfPossible(0, null, null, str, z);
    }

    public final void finishRelaunching() {
        this.mAppCompatController.mAppCompatOverrides.mAppCompatOrientationOverrides.mOrientationOverridesState.mIsRelaunchingAfterRequestedOrientationChanged = false;
        ActivityMetricsLogger.TransitionInfo activeTransitionInfo = this.mTaskSupervisor.mActivityMetricsLogger.getActiveTransitionInfo(this);
        if (activeTransitionInfo != null) {
            activeTransitionInfo.mRelaunched = true;
        }
        int i = this.mPendingRelaunchCount;
        if (i > 0) {
            int i2 = i - 1;
            this.mPendingRelaunchCount = i2;
            if (i2 == 0 && !this.mClientVisible) {
                this.mRelaunchStartTime = 0L;
                DisplayPolicy displayPolicy = this.mDisplayContent.mDisplayPolicy;
                if (displayPolicy.mRelaunchingSystemBarColorApps.remove(this) & displayPolicy.mRelaunchingSystemBarColorApps.isEmpty()) {
                    displayPolicy.updateSystemBarAttributes();
                }
            }
        } else {
            checkKeyguardFlagsChanged();
        }
        Task rootTask = getRootTask();
        if (rootTask == null || !rootTask.shouldSleepOrShutDownActivities()) {
            return;
        }
        rootTask.ensureActivitiesVisible(true, null);
    }

    @Override // com.android.server.wm.WindowContainer
    public final void finishSync(SurfaceControl.Transaction transaction, BLASTSyncEngine.SyncGroup syncGroup, boolean z) {
        if (isDifferentSyncGroup(syncGroup)) {
            return;
        }
        this.mLastAllReadyAtSync = allSyncFinished();
        super.finishSync(transaction, syncGroup, z);
    }

    @Override // com.android.server.wm.WindowContainer
    public final void forAllActivities(Consumer consumer, boolean z) {
        consumer.accept(this);
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean forAllActivities(Predicate predicate, boolean z) {
        return predicate.test(this);
    }

    @Override // com.android.server.wm.WindowContainer
    public final ActivityRecord getActivity(Predicate predicate, boolean z, ActivityRecord activityRecord) {
        if (predicate.test(this)) {
            return this;
        }
        return null;
    }

    public final ActivityWindowInfo getActivityWindowInfo() {
        if (!Flags.activityWindowInfoFlag() || !isAttached()) {
            return this.mTmpActivityWindowInfo;
        }
        boolean z = false;
        if (isFixedRotationTransforming()) {
            Rect bounds = getBounds();
            this.mTmpActivityWindowInfo.set(false, bounds, bounds);
        } else {
            ActivityWindowInfo activityWindowInfo = this.mTmpActivityWindowInfo;
            TaskFragment organizedTaskFragment = getOrganizedTaskFragment();
            if (organizedTaskFragment != null && organizedTaskFragment.isEmbeddedWithBoundsOverride()) {
                z = true;
            }
            activityWindowInfo.set(z, this.task.getBounds(), getTaskFragment().getBounds());
        }
        return this.mTmpActivityWindowInfo;
    }

    @Override // com.android.server.wm.WindowContainer
    public Rect getAnimationBounds(int i) {
        TaskFragment taskFragment = getTaskFragment();
        return taskFragment != null ? taskFragment.getBounds() : getBounds();
    }

    @Override // com.android.server.wm.WindowContainer
    public final void getAnimationFrames(Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        WindowState findMainWindow = findMainWindow(true);
        if (findMainWindow == null) {
            return;
        }
        findMainWindow.getAnimationFrames(rect, rect2, rect3, rect4);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl getAnimationLeashParent() {
        return (inPinnedWindowingMode() || inFreeformWindowingMode()) ? getRootTask().mSurfaceControl : getParentSurfaceControl();
    }

    @Override // com.android.server.wm.WindowContainer
    public final void getAnimationPosition(Point point) {
        point.set(0, 0);
    }

    public final AppCompatDisplayInsets getAppCompatDisplayInsets() {
        AppCompatSizeCompatModePolicy appCompatSizeCompatModePolicy = this.mAppCompatController.mAppCompatSizeCompatModePolicy;
        TransparentPolicy transparentPolicy = appCompatSizeCompatModePolicy.mActivityRecord.mAppCompatController.mTransparentPolicy;
        AppCompatDisplayInsets appCompatDisplayInsets = transparentPolicy.mTransparentPolicyState.isRunning() ? transparentPolicy.mTransparentPolicyState.mInheritedAppCompatDisplayInsets : appCompatSizeCompatModePolicy.mAppCompatDisplayInsets;
        return appCompatDisplayInsets != null ? appCompatDisplayInsets : appCompatSizeCompatModePolicy.mPreCreatedAppCompatDisplayInsets;
    }

    public final int getAppCompatState(boolean z) {
        if (!z && !this.mVisibleRequested) {
            return 1;
        }
        if (this.mAppCompatController.mTransparentPolicy.mTransparentPolicyState.isRunning()) {
            return this.mAppCompatController.mTransparentPolicy.mTransparentPolicyState.mInheritedAppCompatState;
        }
        AppCompatController appCompatController = this.mAppCompatController;
        if (appCompatController.mAppCompatSizeCompatModePolicy.mInSizeCompatModeForBounds) {
            return 3;
        }
        if (appCompatController.mAppCompatAspectRatioPolicy.isLetterboxedForFixedOrientationAndAspectRatio()) {
            return 4;
        }
        return this.mAppCompatController.mAppCompatAspectRatioPolicy.mAppCompatAspectRatioState.mLetterboxBoundsForAspectRatio != null ? 5 : 2;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final Rect getBounds() {
        SizeCompatAttributes sizeCompatAttributes;
        final Rect bounds = super.getBounds();
        if (CoreRune.MT_SIZE_COMPAT_POLICY && (sizeCompatAttributes = this.mSizeCompatAttributes) != null && sizeCompatAttributes.hasBounds()) {
            return this.mSizeCompatAttributes.getBounds();
        }
        AppCompatController appCompatController = this.mAppCompatController;
        final AppCompatSizeCompatModePolicy appCompatSizeCompatModePolicy = appCompatController.mAppCompatSizeCompatModePolicy;
        return (Rect) appCompatController.mTransparentPolicy.mTransparentPolicyState.findOpaqueNotFinishingActivityBelow().map(new ActivityRecord$$ExternalSyntheticLambda16()).orElseGet(new Supplier() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda17
            @Override // java.util.function.Supplier
            public final Object get() {
                AppCompatSizeCompatModePolicy appCompatSizeCompatModePolicy2 = AppCompatSizeCompatModePolicy.this;
                Rect rect = bounds;
                if (!appCompatSizeCompatModePolicy2.hasSizeCompatBounds()) {
                    return appCompatSizeCompatModePolicy2.hasSizeCompatBounds() ? appCompatSizeCompatModePolicy2.mSizeCompatBounds : rect;
                }
                if (appCompatSizeCompatModePolicy2.mReturnSizeCompatBounds == null) {
                    appCompatSizeCompatModePolicy2.mReturnSizeCompatBounds = new Rect();
                }
                appCompatSizeCompatModePolicy2.mReturnSizeCompatBounds.set(appCompatSizeCompatModePolicy2.mSizeCompatBounds);
                return appCompatSizeCompatModePolicy2.mReturnSizeCompatBounds;
            }
        });
    }

    @Override // com.android.server.wm.WindowToken
    public final float getCompatScale() {
        SizeCompatAttributes sizeCompatAttributes;
        if (CoreRune.MT_SIZE_COMPAT_POLICY && (sizeCompatAttributes = this.mSizeCompatAttributes) != null && sizeCompatAttributes.hasBounds()) {
            return this.mSizeCompatAttributes.mScale;
        }
        AppCompatSizeCompatModePolicy appCompatSizeCompatModePolicy = this.mAppCompatController.mAppCompatSizeCompatModePolicy;
        return appCompatSizeCompatModePolicy.hasSizeCompatBounds() ? appCompatSizeCompatModePolicy.mSizeCompatScale : (float) new DoubleSupplier() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda19
            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                return ActivityRecord.this.mDisplayContent.mCompatibleScreenScale;
            }
        }.getAsDouble();
    }

    @Override // com.android.server.wm.WindowContainer
    public final TaskDisplayArea getDisplayArea() {
        return (TaskDisplayArea) super.getDisplayArea();
    }

    public final int getDisplayId() {
        DisplayContent displayContent;
        Task task = this.task;
        if (task == null || (displayContent = task.mDisplayContent) == null) {
            return -1;
        }
        return displayContent.mDisplayId;
    }

    public final String getFilteredReferrer(String str) {
        if (CoreRune.SYSFW_APP_SPEG && "com.samsung.speg".equals(str)) {
            Slog.d("SPEG", "Passing referrer value");
            return str;
        }
        if (str == null) {
            return null;
        }
        if (str.equals(this.packageName) || !this.mWmService.mPmInternal.filterAppAccess(this.info.applicationInfo.uid, this.mUserId, str, true)) {
            return str;
        }
        return null;
    }

    public final InputApplicationHandle getInputApplicationHandle(boolean z) {
        if (this.mInputApplicationHandle == null) {
            this.mInputApplicationHandle = new InputApplicationHandle(this.token, toString(), this.mInputDispatchingTimeoutMillis);
        } else if (z) {
            String activityRecord = toString();
            long j = this.mInputDispatchingTimeoutMillis;
            InputApplicationHandle inputApplicationHandle = this.mInputApplicationHandle;
            if (j != inputApplicationHandle.dispatchingTimeoutMillis || !activityRecord.equals(inputApplicationHandle.name)) {
                this.mInputApplicationHandle = new InputApplicationHandle(this.token, activityRecord, this.mInputDispatchingTimeoutMillis);
            }
        }
        return this.mInputApplicationHandle;
    }

    public final Rect getLetterboxInsets() {
        AppCompatLetterboxPolicy.LetterboxPolicyState letterboxPolicyState = this.mAppCompatController.mAppCompatLetterboxPolicy.mLetterboxPolicyState;
        if (!letterboxPolicyState.isRunning()) {
            return new Rect();
        }
        Letterbox letterbox = letterboxPolicyState.mLetterbox;
        letterbox.getClass();
        return new Rect(Math.max(0, letterbox.mLeft.mLayoutFrameGlobal.width()), Math.max(0, letterbox.mTop.mLayoutFrameGlobal.height()), Math.max(0, letterbox.mRight.mLayoutFrameGlobal.width()), Math.max(0, letterbox.mBottom.mLayoutFrameGlobal.height()));
    }

    public final float getMaxAspectRatio() {
        AppCompatAspectRatioPolicy appCompatAspectRatioPolicy = this.mAppCompatController.mAppCompatAspectRatioPolicy;
        TransparentPolicy transparentPolicy = appCompatAspectRatioPolicy.mTransparentPolicy;
        return transparentPolicy.mTransparentPolicyState.isRunning() ? transparentPolicy.mTransparentPolicyState.mInheritedMaxAspectRatio : appCompatAspectRatioPolicy.mActivityRecord.info.getMaxAspectRatio();
    }

    public final float getMinAspectRatio() {
        return this.mAppCompatController.mAppCompatAspectRatioPolicy.getMinAspectRatio();
    }

    public final Point getMinDimensions() {
        ActivityInfo.WindowLayout windowLayout = this.info.windowLayout;
        if (windowLayout == null) {
            return null;
        }
        return new Point(windowLayout.minWidth, windowLayout.minHeight);
    }

    public final TaskFragment getOrganizedTaskFragment() {
        TaskFragment taskFragment = getTaskFragment();
        if (taskFragment != null) {
            return taskFragment.getOrganizedTaskFragment();
        }
        return null;
    }

    @Override // com.android.server.wm.WindowContainer
    public final int getOrientation(int i) {
        if (this.finishing || shouldIgnoreOrientationRequests() || !(i == 3 || isVisibleRequested())) {
            return -2;
        }
        return getOverrideOrientation();
    }

    @Override // com.android.server.wm.WindowContainer
    public final int getOverrideOrientation() {
        DisplayRotationCompatPolicy displayRotationCompatPolicy;
        AppCompatOrientationPolicy appCompatOrientationPolicy = this.mAppCompatController.mOrientationPolicy;
        int overrideOrientation = super.getOverrideOrientation();
        ActivityRecord activityRecord = appCompatOrientationPolicy.mActivityRecord;
        DisplayContent displayContent = activityRecord.mDisplayContent;
        boolean z = displayContent != null && displayContent.getIgnoreOrientationRequest();
        AppCompatOverrides appCompatOverrides = appCompatOrientationPolicy.mAppCompatOverrides;
        boolean shouldApplyUserFullscreenOverride = appCompatOverrides.mAppCompatAspectRatioOverrides.shouldApplyUserFullscreenOverride();
        AppCompatCameraOverrides appCompatCameraOverrides = appCompatOverrides.mAppCompatCameraOverrides;
        if (!shouldApplyUserFullscreenOverride || !z || appCompatCameraOverrides.isCameraActive()) {
            WindowManagerService windowManagerService = activityRecord.mWmService;
            if (windowManagerService.mIsIgnoreOrientationRequestDisabled) {
                overrideOrientation = windowManagerService.mOrientationMapping.get(overrideOrientation, overrideOrientation);
            }
            AppCompatAspectRatioOverrides appCompatAspectRatioOverrides = appCompatOverrides.mAppCompatAspectRatioOverrides;
            boolean shouldApplyUserMinAspectRatioOverride = appCompatAspectRatioOverrides.shouldApplyUserMinAspectRatioOverride();
            if (!(CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY && displayContent != null && displayContent.mSetIgnoreOrientationRequestOverride) && shouldApplyUserMinAspectRatioOverride && (!ActivityInfo.isFixedOrientation(overrideOrientation) || overrideOrientation == 14)) {
                Slog.v("ActivityTaskManager", "Requested orientation " + ActivityInfo.screenOrientationToString(overrideOrientation) + " for " + activityRecord + " is overridden to " + ActivityInfo.screenOrientationToString(1) + " by user aspect ratio settings.");
                return 1;
            }
            AppCompatOrientationOverrides appCompatOrientationOverrides = appCompatOverrides.mAppCompatOrientationOverrides;
            if (!appCompatOrientationOverrides.mAllowOrientationOverrideOptProp.isFalse() && (displayContent == null || !appCompatCameraOverrides.mActivityRecord.info.isChangeEnabled(265456536L) || ((displayRotationCompatPolicy = displayContent.mAppCompatCameraPolicy.mDisplayRotationCompatPolicy) != null && displayRotationCompatPolicy.isTreatmentEnabledForDisplay() && displayRotationCompatPolicy.isCameraActive(true, activityRecord) && activityRecord.mAppCompatController.mAppCompatOverrides.mAppCompatCameraOverrides.shouldForceRotateForCameraCompat()))) {
                if (appCompatAspectRatioOverrides.isSystemOverrideToFullscreenEnabled() && z && !appCompatCameraOverrides.isCameraActive()) {
                    Slog.v("ActivityTaskManager", "Requested orientation  " + ActivityInfo.screenOrientationToString(overrideOrientation) + " for " + activityRecord + " is overridden to " + ActivityInfo.screenOrientationToString(2));
                } else {
                    AppCompatOrientationOverrides.OrientationOverridesState orientationOverridesState = appCompatOrientationOverrides.mOrientationOverridesState;
                    boolean z2 = orientationOverridesState.mIsOverrideToReverseLandscapeOrientationEnabled;
                    boolean z3 = orientationOverridesState.mIsOverrideAnyOrientationEnabled;
                    if (z2 && (ActivityInfo.isFixedOrientationLandscape(overrideOrientation) || z3)) {
                        Slog.w("ActivityTaskManager", "Requested orientation  " + ActivityInfo.screenOrientationToString(overrideOrientation) + " for " + activityRecord + " is overridden to " + ActivityInfo.screenOrientationToString(8));
                        return 8;
                    }
                    if (z3 || !ActivityInfo.isFixedOrientation(overrideOrientation)) {
                        if (orientationOverridesState.mIsOverrideToPortraitOrientationEnabled) {
                            Slog.w("ActivityTaskManager", "Requested orientation  " + ActivityInfo.screenOrientationToString(overrideOrientation) + " for " + activityRecord + " is overridden to " + ActivityInfo.screenOrientationToString(1));
                            return 1;
                        }
                        if (orientationOverridesState.mIsOverrideToNosensorOrientationEnabled) {
                            Slog.w("ActivityTaskManager", "Requested orientation  " + ActivityInfo.screenOrientationToString(overrideOrientation) + " for " + activityRecord + " is overridden to " + ActivityInfo.screenOrientationToString(5));
                            return 5;
                        }
                    }
                }
            }
            return overrideOrientation;
        }
        Slog.v("ActivityTaskManager", "Requested orientation " + ActivityInfo.screenOrientationToString(overrideOrientation) + " for " + activityRecord + " is overridden to " + ActivityInfo.screenOrientationToString(2) + " by user aspect ratio settings.");
        return 2;
    }

    public final int getPid() {
        WindowProcessController windowProcessController = this.app;
        if (windowProcessController != null) {
            return windowProcessController.mPid;
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.res.Configuration getProcessGlobalConfiguration() {
        /*
            Method dump skipped, instructions count: 234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.getProcessGlobalConfiguration():android.content.res.Configuration");
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    public final long getProtoFieldId() {
        return 1146756268038L;
    }

    @Override // com.android.server.wm.WindowContainer
    public final RemoteAnimationDefinition getRemoteAnimationDefinition() {
        return this.mRemoteAnimationDefinition;
    }

    @Override // com.android.server.wm.WindowContainer
    public final int getRequestedConfigurationOrientation(boolean z) {
        return getRequestedConfigurationOrientation(z, getOverrideOrientation());
    }

    @Override // com.android.server.wm.WindowContainer
    public final int getRequestedConfigurationOrientation(boolean z, int i) {
        ActivityRecord activity;
        TransparentPolicy transparentPolicy = this.mAppCompatController.mTransparentPolicy;
        if (!transparentPolicy.mTransparentPolicyState.isRunning() || transparentPolicy.mActivityRecord.getOverrideOrientation() == -1) {
            Task task = this.task;
            return (task == null || i != 3 || (activity = task.getActivity(new ActivityRecord$$ExternalSyntheticLambda4(4), this, false, true)) == null) ? super.getRequestedConfigurationOrientation(z, i) : activity.getRequestedConfigurationOrientation(z);
        }
        RootDisplayArea rootDisplayArea = getRootDisplayArea();
        if (!z || rootDisplayArea == null || !rootDisplayArea.isOrientationDifferentFromDisplay()) {
            return this.mAppCompatController.mTransparentPolicy.mTransparentPolicyState.mInheritedOrientation;
        }
        int i2 = this.mAppCompatController.mTransparentPolicy.mTransparentPolicyState.mInheritedOrientation;
        if (i2 == 1) {
            return 2;
        }
        if (i2 != 2) {
            return i2;
        }
        return 1;
    }

    public final int getRequestedOrientation() {
        return super.getOverrideOrientation();
    }

    public final Task getRootTask() {
        Task task = this.task;
        if (task != null) {
            return task.getRootTask();
        }
        return null;
    }

    public final Rect getScreenResolvedBounds() {
        Rect bounds = getResolvedOverrideConfiguration().windowConfiguration.getBounds();
        AppCompatSizeCompatModePolicy appCompatSizeCompatModePolicy = this.mAppCompatController.mAppCompatSizeCompatModePolicy;
        return appCompatSizeCompatModePolicy.hasSizeCompatBounds() ? appCompatSizeCompatModePolicy.mSizeCompatBounds : bounds;
    }

    public final Task getTask() {
        return this.task;
    }

    public final TaskFragment getTaskFragment() {
        WindowContainer parent = getParent();
        if (parent != null) {
            return parent.asTaskFragment();
        }
        return null;
    }

    public final int getUid() {
        return this.info.applicationInfo.uid;
    }

    public final UriPermissionOwner getUriPermissionsLocked() {
        if (this.uriPermissions == null) {
            this.uriPermissions = new UriPermissionOwner(this.mAtmService.mUgmInternal, this);
        }
        return this.uriPermissions;
    }

    public final void handleAlreadyVisible() {
        try {
            ActivityOptions activityOptions = this.returningOptions;
            if (activityOptions == null || activityOptions.getAnimationType() != 5 || this.returningOptions.getSceneTransitionInfo() == null) {
                return;
            }
            this.app.mThread.scheduleOnNewSceneTransitionInfo(this.token, this.returningOptions.getSceneTransitionInfo());
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean handleCompleteDeferredRemoval() {
        if (this.mIsExiting) {
            removeIfPossible();
        }
        return super.handleCompleteDeferredRemoval();
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean handlesOrientationChangeFromDescendant(int i) {
        if (shouldIgnoreOrientationRequests()) {
            return false;
        }
        return super.handlesOrientationChangeFromDescendant(i);
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean hasActivity() {
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0032, code lost:
    
        r5.mHasDeskResources = java.lang.Boolean.TRUE;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean hasDeskResources() {
        /*
            r5 = this;
            java.lang.Boolean r0 = r5.mHasDeskResources
            if (r0 == 0) goto L9
            boolean r5 = r0.booleanValue()
            return r5
        L9:
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            r5.mHasDeskResources = r0
            com.android.server.wm.ActivityTaskManagerService r0 = r5.mAtmService     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
            android.content.Context r0 = r0.mContext     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
            java.lang.String r1 = r5.packageName     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
            int r2 = r5.mUserId     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
            android.os.UserHandle r2 = android.os.UserHandle.of(r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
            r3 = 0
            android.content.Context r0 = r0.createPackageContextAsUser(r1, r3, r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
            android.content.res.Resources r0 = r0.getResources()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
            android.content.res.Configuration[] r0 = r0.getSizeAndUiModeConfigurations()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
            int r1 = r0.length     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
        L27:
            if (r3 >= r1) goto L4f
            r2 = r0[r3]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
            int r2 = r2.uiMode     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
            r2 = r2 & 15
            r4 = 2
            if (r2 != r4) goto L39
            java.lang.Boolean r0 = java.lang.Boolean.TRUE     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
            r5.mHasDeskResources = r0     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L37
            goto L4f
        L37:
            r0 = move-exception
            goto L3c
        L39:
            int r3 = r3 + 1
            goto L27
        L3c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Exception thrown during checking for desk resources "
            r1.<init>(r2)
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "ActivityTaskManager"
            android.util.Slog.w(r2, r1, r0)
        L4f:
            java.lang.Boolean r5 = r5.mHasDeskResources
            boolean r5 = r5.booleanValue()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.hasDeskResources():boolean");
    }

    public final boolean hasProcess() {
        return this.app != null;
    }

    @Override // com.android.server.wm.WindowToken
    public final boolean hasSizeCompatBounds() {
        SizeCompatAttributes sizeCompatAttributes;
        if (CoreRune.MT_SIZE_COMPAT_POLICY && (sizeCompatAttributes = this.mSizeCompatAttributes) != null && sizeCompatAttributes.hasBounds()) {
            return true;
        }
        return this.mAppCompatController.mAppCompatSizeCompatModePolicy.hasSizeCompatBounds();
    }

    public final boolean hasStartingWindow() {
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

    public final boolean inSizeCompatMode() {
        WindowContainer parent;
        if (this.mAppCompatController.mAppCompatSizeCompatModePolicy.mInSizeCompatModeForBounds) {
            return true;
        }
        return (getAppCompatDisplayInsets() == null || !shouldCreateAppCompatDisplayInsets() || isFixedRotationTransforming() || getConfiguration().windowConfiguration.getAppBounds() == null || (parent = getParent()) == null || parent.getConfiguration().densityDpi == getConfiguration().densityDpi) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0031, code lost:
    
        if (r4 != null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void inputDispatchingTimedOut(int r14, com.android.internal.os.TimeoutRecord r15) {
        /*
            r13 = this;
            r0 = 64
            java.lang.String r2 = "ActivityRecord#inputDispatchingTimedOut()"
            android.os.Trace.traceBegin(r0, r2)     // Catch: java.lang.Throwable -> L69
            com.android.internal.os.anr.AnrLatencyTracker r2 = r15.mLatencyTracker     // Catch: java.lang.Throwable -> L69
            r2.waitingOnGlobalLockStarted()     // Catch: java.lang.Throwable -> L69
            com.android.server.wm.ActivityTaskManagerService r2 = r13.mAtmService     // Catch: java.lang.Throwable -> L69
            com.android.server.wm.WindowManagerGlobalLock r2 = r2.mGlobalLock     // Catch: java.lang.Throwable -> L69
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()     // Catch: java.lang.Throwable -> L69
            monitor-enter(r2)     // Catch: java.lang.Throwable -> L69
            com.android.internal.os.anr.AnrLatencyTracker r3 = r15.mLatencyTracker     // Catch: java.lang.Throwable -> L47
            r3.waitingOnGlobalLockEnded()     // Catch: java.lang.Throwable -> L47
            boolean r3 = r13.mAppStopped     // Catch: java.lang.Throwable -> L47
            if (r3 == 0) goto L25
            com.android.server.wm.RootWindowContainer r3 = r13.mRootWindowContainer     // Catch: java.lang.Throwable -> L47
            com.android.server.wm.Task r3 = r3.getTopDisplayFocusedRootTask()     // Catch: java.lang.Throwable -> L47
            if (r3 != 0) goto L27
        L25:
            r4 = r13
            goto L33
        L27:
            com.android.server.wm.ActivityRecord r4 = r3.getTopResumedActivity()     // Catch: java.lang.Throwable -> L47
            if (r4 != 0) goto L31
            com.android.server.wm.ActivityRecord r4 = r3.getTopPausingActivity()     // Catch: java.lang.Throwable -> L47
        L31:
            if (r4 == 0) goto L25
        L33:
            com.android.server.wm.WindowProcessController r3 = r13.app     // Catch: java.lang.Throwable -> L47
            boolean r5 = r13.hasProcess()     // Catch: java.lang.Throwable -> L47
            r6 = 0
            if (r5 == 0) goto L49
            com.android.server.wm.WindowProcessController r5 = r13.app     // Catch: java.lang.Throwable -> L47
            int r5 = r5.mPid     // Catch: java.lang.Throwable -> L47
            if (r5 == r14) goto L45
            r5 = -1
            if (r14 != r5) goto L49
        L45:
            r5 = 1
            goto L4a
        L47:
            r13 = move-exception
            goto L76
        L49:
            r5 = r6
        L4a:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L47
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()     // Catch: java.lang.Throwable -> L69
            if (r5 == 0) goto L6b
            com.android.server.wm.ActivityTaskManagerService r14 = r13.mAtmService     // Catch: java.lang.Throwable -> L69
            android.app.ActivityManagerInternal r5 = r14.mAmInternal     // Catch: java.lang.Throwable -> L69
            java.lang.Object r6 = r3.mOwner     // Catch: java.lang.Throwable -> L69
            java.lang.String r7 = r4.shortComponentName     // Catch: java.lang.Throwable -> L69
            android.content.pm.ActivityInfo r14 = r4.info     // Catch: java.lang.Throwable -> L69
            android.content.pm.ApplicationInfo r8 = r14.applicationInfo     // Catch: java.lang.Throwable -> L69
            java.lang.String r9 = r13.shortComponentName     // Catch: java.lang.Throwable -> L69
            com.android.server.wm.WindowProcessController r10 = r13.app     // Catch: java.lang.Throwable -> L69
            r11 = 0
            r12 = r15
            r5.inputDispatchingTimedOut(r6, r7, r8, r9, r10, r11, r12)     // Catch: java.lang.Throwable -> L69
            android.os.Trace.traceEnd(r0)
            return
        L69:
            r13 = move-exception
            goto L7b
        L6b:
            com.android.server.wm.ActivityTaskManagerService r13 = r13.mAtmService     // Catch: java.lang.Throwable -> L69
            android.app.ActivityManagerInternal r13 = r13.mAmInternal     // Catch: java.lang.Throwable -> L69
            r13.inputDispatchingTimedOut(r14, r6, r15)     // Catch: java.lang.Throwable -> L69
            android.os.Trace.traceEnd(r0)
            return
        L76:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L47
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()     // Catch: java.lang.Throwable -> L69
            throw r13     // Catch: java.lang.Throwable -> L69
        L7b:
            android.os.Trace.traceEnd(r0)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.inputDispatchingTimedOut(int, com.android.internal.os.TimeoutRecord):void");
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final boolean isAlwaysOnTop() {
        if (CoreRune.FW_APPLOCK && this.mIsAppLockExceptionActivity && this.mNotToBeTopForAppLockException) {
            return false;
        }
        return this.mTaskOverlay || super.isAlwaysOnTop();
    }

    public final boolean isConfigurationDispatchPaused() {
        return this.mPauseConfigurationDispatchCount > 0;
    }

    public final boolean isDestroyable() {
        return (this.finishing || !hasProcess() || isState(State.RESUMED) || getRootTask() == null || this == getTaskFragment().mPausingActivity || !this.mHaveState || !this.mAppStopped || this.mVisibleRequested) ? false : true;
    }

    public final boolean isEligibleForLetterboxEducation() {
        return this.mWmService.mAppCompatConfiguration.mIsEducationEnabled && this.mIsEligibleForFixedOrientationLetterbox && getWindowingMode() == 1 && getRequestedConfigurationOrientation() == 1 && this.mStartingWindow == null;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isEmbedded() {
        TaskFragment taskFragment = getTaskFragment();
        return taskFragment != null && taskFragment.mIsEmbedded;
    }

    @Override // com.android.server.wm.WindowToken
    public final boolean isFirstChildWindowGreaterThanSecond(WindowState windowState, WindowState windowState2) {
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

    @Override // com.android.server.wm.WindowContainer
    public final boolean isFocusable() {
        Task task;
        return super.isFocusable() && ((getWindowConfiguration().canReceiveKeys() && !this.mWaitForEnteringPinnedMode && ((task = this.task) == null || !task.isFreeformStashed())) || (this.info.flags & 262144) != 0);
    }

    public final boolean isIconStylePreferred(int i) {
        AttributeCache.Entry entry;
        return i != 0 && (entry = AttributeCache.instance().get(this.packageName, i, R.styleable.Window, this.mWmService.mCurrentUserId)) != null && entry.array.hasValue(61) && entry.array.getInt(61, 0) == 1;
    }

    public final boolean isInLetterboxAnimation() {
        return this.mNeedsLetterboxedAnimation && isAnimating();
    }

    public final boolean isInRootTaskLocked() {
        Task rootTask = getRootTask();
        if (rootTask != null) {
            if (!isDescendantOf(rootTask)) {
                this = null;
            }
            if (this != null) {
                return true;
            }
        }
        return false;
    }

    public final boolean isInterestingToUserLocked() {
        State state;
        return this.mVisibleRequested || this.nowVisible || (state = this.mState) == State.PAUSING || state == State.RESUMED;
    }

    public final boolean isKeyguardLocked() {
        DisplayContent displayContent = this.mDisplayContent;
        return displayContent != null ? displayContent.isKeyguardLocked() : this.mRootWindowContainer.mDefaultDisplay.isKeyguardLocked();
    }

    public final boolean isLaunchAdjacent() {
        Intent intent = this.intent;
        return intent != null && (intent.getFlags() & 268439552) == 268439552;
    }

    public final boolean isPersistable() {
        Intent intent;
        int i = this.info.persistableMode;
        return (i == 0 || i == 2) && ((intent = this.intent) == null || (intent.getFlags() & 8388608) == 0);
    }

    public final boolean isProcessRunning() {
        WindowProcessController windowProcessController = this.app;
        if (windowProcessController == null) {
            windowProcessController = (WindowProcessController) this.mAtmService.mProcessNames.get(this.processName, this.info.applicationInfo.uid);
        }
        return windowProcessController != null && windowProcessController.hasThread();
    }

    public final boolean isRelaunching() {
        return this.mPendingRelaunchCount > 0;
    }

    public final boolean isResizeable(boolean z) {
        int i = this.info.resizeMode;
        if (i == 10) {
            return false;
        }
        return this.mAtmService.mForceResizableActivities || ActivityInfo.isResizeableMode(i) || (this.info.supportsPictureInPicture() && z) || isEmbedded();
    }

    public final boolean isResolverOrChildActivity() {
        if (!"android".equals(this.packageName)) {
            return false;
        }
        try {
            return ResolverActivity.class.isAssignableFrom(Object.class.getClassLoader().loadClass(this.mActivityComponent.getClassName()));
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public final boolean isResolverOrDelegateActivity() {
        if (!ResolverActivity.class.getName().equals(this.mActivityComponent.getClassName())) {
            ComponentName componentName = this.mActivityComponent;
            ActivityTaskSupervisor activityTaskSupervisor = this.mAtmService.mTaskSupervisor;
            if (activityTaskSupervisor.mSystemChooserActivity == null) {
                activityTaskSupervisor.mSystemChooserActivity = ComponentName.unflattenFromString(activityTaskSupervisor.mService.mContext.getResources().getString(android.R.string.crossSimFormat_spn_cross_sim_calling));
            }
            if (!Objects.equals(componentName, activityTaskSupervisor.mSystemChooserActivity)) {
                return false;
            }
        }
        return true;
    }

    public final boolean isRootOfTask() {
        Task task = this.task;
        return task != null && this == task.getRootActivity(false, true);
    }

    public boolean isSnapshotCompatible(TaskSnapshot taskSnapshot) {
        if (taskSnapshot == null) {
            return false;
        }
        int width = this.task.getBounds().width();
        int height = this.task.getBounds().height();
        Point taskSize = taskSnapshot.getTaskSize();
        int i = taskSize.x;
        if ((width != i || height != taskSize.y) && (width != taskSize.y || height != i)) {
            return false;
        }
        if (!hasFixedRotationTransform() || !taskSnapshot.hasImeSurface()) {
            return !this.mDisableSnapshot && taskSnapshot.getTopActivityComponent().equals(this.mActivityComponent) && isSnapshotOrientationCompatible(taskSnapshot);
        }
        Slog.d("ActivityTaskManager", "IME will be hidden by async rotation cause fixed launching.So, snapshot which include ime is not compatible.");
        return false;
    }

    public final boolean isSnapshotOrientationCompatible(TaskSnapshot taskSnapshot) {
        int rotationForActivityInDifferentOrientation = this.mDisplayContent.rotationForActivityInDifferentOrientation(this);
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
        return Math.abs((((float) taskSize.x) / ((float) Math.max(taskSize.y, 1))) - (((float) width) / ((float) Math.max(height, 1)))) <= 0.01f;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isSplitEmbedded() {
        return isEmbedded() && !getTaskFragment().matchParentBounds();
    }

    public final boolean isStartingWindowDisplayed() {
        StartingData startingData = this.mStartingData;
        if (startingData == null) {
            Task task = this.task;
            startingData = task != null ? task.mSharedStartingData : null;
        }
        return startingData != null && startingData.mIsDisplayed;
    }

    public final boolean isState(State state) {
        return state == this.mState;
    }

    public final boolean isState(State state, State state2) {
        State state3 = this.mState;
        return state == state3 || state2 == state3;
    }

    public final boolean isState(State state, State state2, State state3, State state4) {
        State state5 = this.mState;
        return state == state5 || state2 == state5 || state3 == state5 || state4 == state5;
    }

    public final boolean isState$1(State state, State state2, State state3, State state4) {
        State state5 = State.STARTED;
        State state6 = this.mState;
        return state5 == state6 || state == state6 || state2 == state6 || state3 == state6 || state4 == state6;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isSyncFinished(BLASTSyncEngine.SyncGroup syncGroup) {
        WindowState window;
        Task task = this.task;
        if (task != null && task.mSharedStartingData != null && (window = task.getWindow(new Task$$ExternalSyntheticLambda0(6))) != null && window.mSyncState == 2 && this.mDisplayContent.mUnknownAppVisibilityController.mUnknownApps.isEmpty()) {
            return true;
        }
        if (!super.isSyncFinished(syncGroup)) {
            return false;
        }
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null) {
            UnknownAppVisibilityController unknownAppVisibilityController = displayContent.mUnknownAppVisibilityController;
            if (unknownAppVisibilityController.mUnknownApps.isEmpty() ? false : unknownAppVisibilityController.mUnknownApps.containsKey(this)) {
                return false;
            }
        }
        if (!isVisibleRequested()) {
            return true;
        }
        if (this.mPendingRelaunchCount > 0 || !isAttached()) {
            return false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowState) this.mChildren.get(size)).isVisibleRequested()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isTopRunningActivity() {
        return this.mRootWindowContainer.topRunningActivity() == this;
    }

    public final boolean isUid(int i) {
        return this.info.applicationInfo.uid == i;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isVisible() {
        return this.mVisible;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isWaitingForTransitionStart() {
        DisplayContent displayContent = getDisplayContent();
        return displayContent != null && displayContent.mAppTransition.isTransitionSet() && (displayContent.mOpeningApps.contains(this) || displayContent.mClosingApps.contains(this) || displayContent.mChangingContainers.contains(this));
    }

    public final void logAppCompatState() {
        ActivityMetricsLogger activityMetricsLogger = this.mTaskSupervisor.mActivityMetricsLogger;
        activityMetricsLogger.getClass();
        int i = this.info.applicationInfo.uid;
        int appCompatState = getAppCompatState(false);
        if (!activityMetricsLogger.mPackageUidToCompatStateInfo.contains(i)) {
            activityMetricsLogger.mPackageUidToCompatStateInfo.put(i, new ActivityMetricsLogger.PackageCompatStateInfo());
        }
        ActivityMetricsLogger.PackageCompatStateInfo packageCompatStateInfo = (ActivityMetricsLogger.PackageCompatStateInfo) activityMetricsLogger.mPackageUidToCompatStateInfo.get(i);
        int i2 = packageCompatStateInfo.mLastLoggedState;
        ActivityRecord activityRecord = packageCompatStateInfo.mLastLoggedActivity;
        boolean z = appCompatState != 1;
        ArrayList arrayList = packageCompatStateInfo.mVisibleActivities;
        if (z && !arrayList.contains(this)) {
            arrayList.add(this);
        } else if (!z) {
            arrayList.remove(this);
            if (arrayList.isEmpty()) {
                activityMetricsLogger.mPackageUidToCompatStateInfo.remove(i);
            }
        }
        if (appCompatState == i2) {
            return;
        }
        if (z || arrayList.isEmpty()) {
            if (activityRecord == null || this == activityRecord || i2 == 1 || i2 == 2) {
                ActivityMetricsLogger.logAppCompatStateInternal(this, appCompatState, packageCompatStateInfo);
                return;
            }
            return;
        }
        if (activityRecord == null || this == activityRecord) {
            ArrayList arrayList2 = packageCompatStateInfo.mVisibleActivities;
            int i3 = packageCompatStateInfo.mLastLoggedState;
            ActivityRecord activityRecord2 = null;
            int i4 = 1;
            for (int i5 = 0; i5 < arrayList2.size(); i5++) {
                ActivityRecord activityRecord3 = (ActivityRecord) arrayList2.get(i5);
                int appCompatState2 = activityRecord3.getAppCompatState(false);
                if (appCompatState2 == i3) {
                    packageCompatStateInfo.mLastLoggedActivity = activityRecord3;
                    return;
                }
                if (appCompatState2 == 1) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Visible activity with NOT_VISIBLE App Compat state for package UID: ", "ActivityTaskManager");
                } else if (i4 == 1 || (i4 == 2 && appCompatState2 != 2)) {
                    activityRecord2 = activityRecord3;
                    i4 = appCompatState2;
                }
            }
            if (activityRecord2 == null || i4 == 1) {
                return;
            }
            ActivityMetricsLogger.logAppCompatStateInternal(activityRecord2, i4, packageCompatStateInfo);
        }
    }

    public final void logForMultiWindowModeChanged(int i, int i2) {
        if (CoreRune.MW_SA_LOGGING) {
            WindowConfiguration windowConfiguration = this.mLastReportedConfiguration.getOverrideConfiguration().windowConfiguration;
            MultiWindowUtils.logForMultiWindowModeChange(i, windowConfiguration.getWindowingMode(), i2, windowConfiguration.getStageType());
        }
    }

    public final void logStartActivity(int i, Task task, String str) {
        Uri data = this.intent.getData();
        String safeString = data != null ? data.toSafeString() : null;
        if (safeString != null && safeString.startsWith("nfc://secure")) {
            safeString = "nfc://secure:it should not be shown";
        }
        EventLog.writeEvent(i, Integer.valueOf(this.mUserId), Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(task.mTaskId), this.shortComponentName, this.intent.getAction(), this.intent.getType(), safeString, Integer.valueOf(this.intent.getFlags()), str);
    }

    public final boolean makeActiveIfNeeded(ActivityRecord activityRecord) {
        ActivityOptions.SceneTransitionInfo sceneTransitionInfo = null;
        if (shouldResumeActivity(activityRecord)) {
            return getRootTask().resumeTopActivityUncheckedLocked(activityRecord, null, false);
        }
        if (shouldPauseActivity(activityRecord)) {
            setState(State.PAUSING, "makeActiveIfNeeded");
            EventLog.writeEvent(30013, Integer.valueOf(this.mUserId), Integer.valueOf(System.identityHashCode(this)), this.shortComponentName, "userLeaving=false", "make-active");
            try {
                this.mAtmService.mLifecycleManager.scheduleTransactionItem(this.app.mThread, PauseActivityItem.obtain(this.token, this.finishing, false, false, this.mAutoEnteringPip));
            } catch (Exception e) {
                Slog.w("ActivityTaskManager", "Exception thrown sending pause: " + this.intent.getComponent(), e);
            }
        } else if (this.mVisibleRequested && (isState(State.STOPPED) || isState(State.STOPPING))) {
            setState(State.STARTED, "makeActiveIfNeeded");
            try {
                ClientLifecycleManager clientLifecycleManager = this.mAtmService.mLifecycleManager;
                IApplicationThread iApplicationThread = this.app.mThread;
                IBinder iBinder = this.token;
                ActivityOptions activityOptions = this.mPendingOptions;
                if (activityOptions != null) {
                    this.mPendingOptions = null;
                    sceneTransitionInfo = activityOptions.getSceneTransitionInfo();
                }
                clientLifecycleManager.scheduleTransactionItem(iApplicationThread, StartActivityItem.obtain(iBinder, sceneTransitionInfo));
            } catch (Exception e2) {
                Slog.w("ActivityTaskManager", "Exception thrown sending start: " + this.intent.getComponent(), e2);
            }
            this.mTaskSupervisor.mStoppingActivities.remove(this);
        }
        return false;
    }

    public final void makeFinishingLocked() {
        Task task;
        ActivityRecord activity;
        if (this.finishing) {
            return;
        }
        this.finishing = true;
        if (this.mLaunchCookie != null && this.mState != State.RESUMED && (task = this.task) != null && !task.mInRemoveTask && !task.mReuseTask && (activity = task.getActivity(new ActivityRecord$$ExternalSyntheticLambda1(3, this), this, false, false)) != null) {
            activity.mLaunchCookie = this.mLaunchCookie;
            this.mLaunchCookie = null;
        }
        TaskFragment taskFragment = getTaskFragment();
        if (taskFragment != null) {
            Task task2 = taskFragment.getTask();
            if (task2 != null && task2.mReuseTask && taskFragment.getTopNonFinishingActivity(true, true) == null) {
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

    public final void makeInvisible() {
        if (this.mVisibleRequested) {
            try {
                boolean checkEnterPictureInPictureState = checkEnterPictureInPictureState("makeInvisible", true, false);
                setDeferHidingClient(checkEnterPictureInPictureState && !isState(State.STARTED, State.STOPPING, State.STOPPED, State.PAUSED));
                setVisibility(false);
                switch (this.mState.ordinal()) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        addToStopping("makeInvisible", true, checkEnterPictureInPictureState);
                        break;
                    case 5:
                    case 6:
                        this.supportsEnterPipOnTaskSwitch = false;
                        break;
                }
            } catch (Exception e) {
                Slog.w("ActivityTaskManager", "Exception thrown making hidden: " + this.intent.getComponent(), e);
            }
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final boolean matchParentBounds() {
        WindowContainer parent;
        Rect resolvedOverrideBounds = getResolvedOverrideBounds();
        return resolvedOverrideBounds.isEmpty() || (parent = getParent()) == null || parent.getBounds().equals(resolvedOverrideBounds);
    }

    public final boolean moveFocusableActivityToTop(String str) {
        boolean isFocusable = isFocusable();
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_enabled;
        if (!isFocusable) {
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_FOCUS, -9024836052864189016L, 0, null, String.valueOf(this));
            }
            return false;
        }
        Task rootTask = getRootTask();
        if (rootTask == null) {
            Slog.w("ActivityTaskManager", "moveFocusableActivityToTop: invalid root task: activity=" + this + " task=" + this.task);
            return false;
        }
        ActivityRecord activityRecord = this.mDisplayContent.mFocusedApp;
        int i = this.mRootWindowContainer.getTopFocusedDisplayContent() != null ? this.mRootWindowContainer.getTopFocusedDisplayContent().mDisplayId : -1;
        State state = State.RESUMED;
        if (activityRecord != null && activityRecord.task == this.task) {
            DisplayContent displayContent = this.mDisplayContent;
            if (i == displayContent.mDisplayId) {
                if (this.task == displayContent.getTask(new ActivityRecord$$ExternalSyntheticLambda4(3), true)) {
                    if (activityRecord != this) {
                        if (zArr[0]) {
                            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_FOCUS, -1058622321669556178L, 0, null, String.valueOf(this));
                        }
                        this.mDisplayContent.setFocusedApp(this);
                        this.mAtmService.mWindowManager.updateFocusedWindowLocked(0, true);
                    } else if (zArr[0]) {
                        ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_FOCUS, 134255351804410010L, 0, null, String.valueOf(this));
                    }
                    DisplayContent displayContent2 = getDisplayContent();
                    ActivityRecord activityRecord2 = this.mAtmService.mLastResumedActivity;
                    DisplayContent displayContent3 = activityRecord2 != null ? activityRecord2.getDisplayContent() : null;
                    if (displayContent3 != null && displayContent2 != null && displayContent3 != displayContent2 && (displayContent3.isMultiTaskingDisplay() || displayContent2.isMultiTaskingDisplay())) {
                        this.mAtmService.setLastResumedActivityUncheckLocked(this, str);
                    }
                    return !isState(state);
                }
            }
        }
        if (zArr[0]) {
            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_FOCUS, 731006689098152100L, 0, null, String.valueOf(this));
        }
        rootTask.moveToFront(str, this.task);
        if (this.mState == state && this.mRootWindowContainer.getTopResumedActivity() == this) {
            this.mAtmService.setLastResumedActivityUncheckLocked(this, str);
        }
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean needsZBoost() {
        return this.mNeedsZBoost || super.needsZBoost();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyActivityRefresherAboutConfigurationChange(final android.content.res.Configuration r9, final android.content.res.Configuration r10) {
        /*
            r8 = this;
            r0 = 0
            boolean r0 = r8.shouldBeResumed(r0)
            if (r0 != 0) goto L8
            return
        L8:
            com.android.server.wm.DisplayContent r0 = r8.mDisplayContent
            com.android.server.wm.AppCompatCameraPolicy r0 = r0.mAppCompatCameraPolicy
            com.android.server.wm.ActivityRefresher r0 = r0.mActivityRefresher
            if (r0 == 0) goto Lc9
            com.android.server.wm.WindowManagerService r1 = r0.mWmService
            com.android.server.wm.AppCompatConfiguration r2 = r1.mAppCompatConfiguration
            boolean r2 = r2.mIsCameraCompatTreatmentRefreshEnabled
            if (r2 == 0) goto Lc9
            com.android.server.wm.AppCompatController r2 = r8.mAppCompatController
            com.android.server.wm.AppCompatOverrides r2 = r2.mAppCompatOverrides
            com.android.server.wm.AppCompatCameraOverrides r2 = r2.mAppCompatCameraOverrides
            com.android.server.wm.ActivityRecord r3 = r2.mActivityRecord
            android.content.pm.ActivityInfo r3 = r3.info
            r4 = 264304459(0xfc0f74b, double:1.30583753E-315)
            boolean r3 = r3.isChangeEnabled(r4)
            com.android.server.wm.utils.OptPropFactory$OptProp r2 = r2.mCameraCompatAllowRefreshOptProp
            java.util.function.BooleanSupplier r4 = r2.mCondition
            boolean r4 = r4.getAsBoolean()
            if (r4 != 0) goto L35
            goto Lc9
        L35:
            int r2 = r2.getValue()
            if (r2 == 0) goto Lc9
            if (r3 != 0) goto Lc9
            java.util.ArrayList r2 = r0.mEvaluators
            java.lang.Object[] r2 = r2.toArray()
            com.android.server.wm.ActivityRefresher$$ExternalSyntheticLambda1 r3 = new com.android.server.wm.ActivityRefresher$$ExternalSyntheticLambda1
            r3.<init>()
            java.lang.Object r9 = com.android.internal.util.ArrayUtils.find(r2, r3)
            if (r9 == 0) goto Lc9
            com.android.server.wm.AppCompatConfiguration r9 = r1.mAppCompatConfiguration
            boolean r9 = r9.mIsCameraCompatRefreshCycleThroughStopEnabled
            r10 = 1
            r1 = 0
            if (r9 == 0) goto L71
            com.android.server.wm.AppCompatController r9 = r8.mAppCompatController
            com.android.server.wm.AppCompatOverrides r9 = r9.mAppCompatOverrides
            com.android.server.wm.AppCompatCameraOverrides r9 = r9.mAppCompatCameraOverrides
            com.android.server.wm.ActivityRecord r2 = r9.mActivityRecord
            android.content.pm.ActivityInfo r2 = r2.info
            r3 = 264301586(0xfc0ec12, double:1.30582334E-315)
            boolean r2 = r2.isChangeEnabled(r3)
            com.android.server.wm.utils.OptPropFactory$OptProp r9 = r9.mCameraCompatEnableRefreshViaPauseOptProp
            boolean r9 = r9.shouldEnableWithOverrideAndProperty(r2)
            if (r9 != 0) goto L71
            r9 = r10
            goto L72
        L71:
            r9 = r1
        L72:
            com.android.server.wm.AppCompatController r2 = r8.mAppCompatController
            com.android.server.wm.AppCompatOverrides r2 = r2.mAppCompatOverrides
            com.android.server.wm.AppCompatCameraOverrides r2 = r2.mAppCompatCameraOverrides
            com.android.server.wm.AppCompatCameraOverrides$AppCompatCameraOverridesState r2 = r2.mAppCompatCameraOverridesState
            r2.mIsRefreshRequested = r10
            boolean[] r2 = com.android.internal.protolog.ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled
            boolean r10 = r2[r10]
            if (r10 == 0) goto L96
            java.lang.String r10 = java.lang.String.valueOf(r8)
            com.android.internal.protolog.ProtoLogGroup r2 = com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES
            java.lang.Object[] r7 = new java.lang.Object[]{r10}
            r6 = 0
            r3 = 7211222997110112110(0x6413691bf2e03f6e, double:1.200206803860267E174)
            r5 = 0
            com.android.internal.protolog.ProtoLogImpl_54989576.v(r2, r3, r5, r6, r7)
        L96:
            android.os.IBinder r10 = r8.token
            if (r9 == 0) goto L9c
            r9 = 5
            goto L9d
        L9c:
            r9 = 4
        L9d:
            android.app.servertransaction.RefreshCallbackItem r9 = android.app.servertransaction.RefreshCallbackItem.obtain(r10, r9)
            android.os.IBinder r10 = r8.token
            android.app.servertransaction.ResumeActivityItem r10 = android.app.servertransaction.ResumeActivityItem.obtain(r10, r1, r1)
            com.android.server.wm.ActivityTaskManagerService r2 = r8.mAtmService     // Catch: android.os.RemoteException -> Lbf
            com.android.server.wm.ClientLifecycleManager r2 = r2.mLifecycleManager     // Catch: android.os.RemoteException -> Lbf
            com.android.server.wm.WindowProcessController r3 = r8.app     // Catch: android.os.RemoteException -> Lbf
            android.app.IApplicationThread r3 = r3.mThread     // Catch: android.os.RemoteException -> Lbf
            r2.scheduleTransactionAndLifecycleItems(r3, r9, r10, r1)     // Catch: android.os.RemoteException -> Lbf
            android.os.Handler r9 = r0.mHandler     // Catch: android.os.RemoteException -> Lbf
            com.android.server.wm.ActivityRefresher$$ExternalSyntheticLambda0 r10 = new com.android.server.wm.ActivityRefresher$$ExternalSyntheticLambda0     // Catch: android.os.RemoteException -> Lbf
            r10.<init>()     // Catch: android.os.RemoteException -> Lbf
            r2 = 2000(0x7d0, double:9.88E-321)
            r9.postDelayed(r10, r2)     // Catch: android.os.RemoteException -> Lbf
            goto Lc9
        Lbf:
            com.android.server.wm.AppCompatController r8 = r8.mAppCompatController
            com.android.server.wm.AppCompatOverrides r8 = r8.mAppCompatOverrides
            com.android.server.wm.AppCompatCameraOverrides r8 = r8.mAppCompatCameraOverrides
            com.android.server.wm.AppCompatCameraOverrides$AppCompatCameraOverridesState r8 = r8.mAppCompatCameraOverridesState
            r8.mIsRefreshRequested = r1
        Lc9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.notifyActivityRefresherAboutConfigurationChange(android.content.res.Configuration, android.content.res.Configuration):void");
    }

    public final void notifyAppResumed() {
        if (getParent() == null) {
            Slog.w("WindowManager", "Attempted to notify resumed of non-existing app token: " + this.token);
            return;
        }
        boolean z = this.mAppStopped;
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 926038819327785799L, 3, null, Boolean.valueOf(z), String.valueOf(this));
        }
        this.mAppStopped = false;
        if (this.mAtmService.mActivityStartController.mInExecution) {
            this.mCurrentLaunchCanTurnScreenOn = true;
        }
        if (z) {
            return;
        }
        destroySurfaces(true);
    }

    public final void notifyUnknownVisibilityLaunchedForKeyguardTransition() {
        if (this.noDisplay || !isKeyguardLocked()) {
            return;
        }
        UnknownAppVisibilityController unknownAppVisibilityController = this.mDisplayContent.mUnknownAppVisibilityController;
        unknownAppVisibilityController.getClass();
        if (this.mLaunchTaskBehind) {
            unknownAppVisibilityController.mUnknownApps.put(this, 2);
        } else {
            unknownAppVisibilityController.mUnknownApps.put(this, 1);
        }
        StringBuilder sb = new StringBuilder("App launched activity=");
        sb.append(this);
        sb.append(", state=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, this.mLaunchTaskBehind ? 2 : 1, "WindowManager");
    }

    public boolean occludesParent(boolean z) {
        if (z || !this.finishing) {
            return this.mOccludesParent || showWallpaper();
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
        WindowState window;
        super.onAnimationFinished(i, animationAdapter);
        Trace.traceBegin(32L, "AR#onAnimationFinished");
        this.mTransit = -1;
        this.mTransitFlags = 0;
        setAppLayoutChanges(12);
        clearThumbnail();
        setClientVisible(this.mVisible || this.mVisibleRequested);
        DisplayContent displayContent = getDisplayContent();
        WindowState windowState = displayContent.mImeLayeringTarget;
        if (windowState != null && windowState.mActivityRecord == this) {
            displayContent.computeImeTarget(true);
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ANIM, -8809523216004991008L, 1020, null, String.valueOf(this), Boolean.valueOf(this.reportedVisible), Boolean.valueOf(okToDisplay()), Boolean.valueOf(okToAnimate()), Boolean.valueOf(isStartingWindowDisplayed()));
        }
        WindowContainerThumbnail windowContainerThumbnail = this.mThumbnail;
        if (windowContainerThumbnail != null) {
            windowContainerThumbnail.destroy();
            this.mThumbnail = null;
        }
        new ArrayList(this.mChildren).forEach(new ActivityRecord$$ExternalSyntheticLambda3(3));
        Task task = this.task;
        if (task != null && this.startingMoved && (window = task.getWindow(new ActivityRecord$$ExternalSyntheticLambda4(2))) != null && window.mAnimatingExit && !window.isSelfAnimating(0, 16)) {
            window.onExitAnimationDone();
        }
        getDisplayContent().mAppTransition.notifyAppTransitionFinishedLocked(this.token);
        scheduleAnimation();
        this.mTaskSupervisor.scheduleProcessStoppingAndFinishingActivitiesIfNeeded();
        Trace.traceEnd(32L);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public final void onAnimationLeashLost(SurfaceControl.Transaction transaction) {
        super.onAnimationLeashLost(transaction);
        SurfaceControl surfaceControl = this.mAnimationBoundsLayer;
        if (surfaceControl != null) {
            transaction.remove(surfaceControl);
            this.mAnimationBoundsLayer = null;
        }
        this.mNeedsAnimationBoundsLayer = false;
        if (this.mNeedsLetterboxedAnimation) {
            this.mNeedsLetterboxedAnimation = false;
            WindowState findMainWindow = findMainWindow(true);
            AppCompatLetterboxPolicy appCompatLetterboxPolicy = this.mAppCompatController.mAppCompatLetterboxPolicy;
            appCompatLetterboxPolicy.mLetterboxPolicyState.updateLetterboxSurfaceIfNeeded(findMainWindow, transaction, getPendingTransaction());
        }
        AnimatingActivityRegistry animatingActivityRegistry = this.mAnimatingActivityRegistry;
        if (animatingActivityRegistry != null) {
            animatingActivityRegistry.mAnimatingActivities.remove(this);
            animatingActivityRegistry.mFinishedTokens.remove(this);
            if (animatingActivityRegistry.mAnimatingActivities.isEmpty()) {
                animatingActivityRegistry.endDeferringFinished();
            }
        }
    }

    @Override // com.android.server.wm.WindowToken
    public final void onCancelFixedRotationTransform(int i) {
        if (this != this.mDisplayContent.getLastOrientationSource()) {
            return;
        }
        int requestedConfigurationOrientation = getRequestedConfigurationOrientation();
        if (requestedConfigurationOrientation == 0 || requestedConfigurationOrientation == this.mDisplayContent.getConfiguration().orientation) {
            PinnedTaskController pinnedTaskController = this.mDisplayContent.mPinnedTaskController;
            pinnedTaskController.mFreezingTaskConfig = false;
            pinnedTaskController.mDeferOrientationChanging = false;
            pinnedTaskController.mDestRotatedBounds = null;
            pinnedTaskController.mPipTransaction = null;
            startFreezingScreen(i);
            ensureActivityConfiguration(false);
            if (this.mTransitionController.isCollecting(this)) {
                this.task.resetSurfaceControlTransforms();
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean onChildVisibleRequestedChanged(WindowContainer windowContainer) {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public final void onConfigurationChanged(Configuration configuration) {
        int rotationForActivityInDifferentOrientation;
        AppCompatDisplayInsets appCompatDisplayInsets;
        SizeCompatAttributes sizeCompatAttributes;
        if (CoreRune.MT_SIZE_COMPAT_POLICY && (sizeCompatAttributes = this.mSizeCompatAttributes) != null) {
            sizeCompatAttributes.mEnabled = false;
        }
        AppCompatSizeCompatModePolicy appCompatSizeCompatModePolicy = this.mAppCompatController.mAppCompatSizeCompatModePolicy;
        Task task = this.task;
        appCompatSizeCompatModePolicy.mPreCreatedAppCompatDisplayInsets = null;
        if (task != null && (appCompatDisplayInsets = appCompatSizeCompatModePolicy.mAppCompatDisplayInsets) != null) {
            ActivityRecord activityRecord = appCompatSizeCompatModePolicy.mActivityRecord;
            if (activityRecord.mAtmService.mMultiTaskingAppCompatController.mSizeCompatModePolicy.mAvoidAppCompatDisplayInsets || ((activityRecord.isEmbedded() && !activityRecord.getTaskFragment().getResolvedOverrideBounds().isEmpty()) || (CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY && task.mRespectOrientationRequestOverride != -1 && (!CoreRune.MT_APP_COMPAT_ROTATION_COMPAT_MODE || !appCompatDisplayInsets.mIsRotationCompatMode)))) {
                appCompatSizeCompatModePolicy.clearSizeCompatMode(true, true);
            }
        }
        if (this.mTransitionController.isShellTransitionsEnabled() && this.mVisible && isVisibleRequested()) {
            if (getWindowingMode() != (getRequestedOverrideWindowingMode() == 0 ? configuration.windowConfiguration.getWindowingMode() : getRequestedOverrideWindowingMode()) && (!this.mWaitForEnteringPinnedMode || !this.mTransitionController.inFinishingTransition(this))) {
                this.mTransitionController.collect(this);
            }
        }
        if (getAppCompatDisplayInsets() != null) {
            Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
            boolean z = requestedOverrideConfiguration.windowConfiguration.getRotation() != -1;
            int requestedConfigurationOrientation = getRequestedConfigurationOrientation();
            if (requestedConfigurationOrientation != 0 && requestedConfigurationOrientation != getConfiguration().orientation && requestedConfigurationOrientation == getParent().getConfiguration().orientation && requestedOverrideConfiguration.windowConfiguration.getRotation() != getParent().getWindowConfiguration().getRotation()) {
                requestedOverrideConfiguration.windowConfiguration.setRotation(getParent().getWindowConfiguration().getRotation());
                onRequestedOverrideConfigurationChanged(requestedOverrideConfiguration);
                return;
            } else if (z && requestedConfigurationOrientation == 0 && requestedOverrideConfiguration.windowConfiguration.getRotation() != -1) {
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
                this.app.mPauseConfigurationDispatchCount++;
                super.onConfigurationChanged(configuration);
                if (this.mVisibleRequested && !inMultiWindowMode() && (rotationForActivityInDifferentOrientation = displayContent.rotationForActivityInDifferentOrientation(this)) != -1) {
                    this.app.resumeConfigurationDispatch();
                    displayContent.setFixedRotationLaunchingApp(rotationForActivityInDifferentOrientation, this);
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
            String str = "Can't change activity type once set: " + this + " activityType=" + WindowConfiguration.activityTypeToString(getActivityType()) + ", was " + WindowConfiguration.activityTypeToString(activityType);
            if (Build.IS_DEBUGGABLE) {
                throw new IllegalStateException(str);
            }
            Slog.w("ActivityTaskManager", str);
        }
        if (!inPinnedWindowingMode && inPinnedWindowingMode() && this.task != null) {
            if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                setEnteringPipFromSplit("pip_entered", false);
                setHiddenWhileEnteringPinnedMode("pip_entered", false);
            }
            this.mWaitForEnteringPinnedMode = false;
            ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
            Task task2 = this.task;
            Rect bounds = task2.getBounds();
            activityTaskSupervisor.getClass();
            task2.forAllActivities(new ActivityTaskSupervisor$$ExternalSyntheticLambda1(activityTaskSupervisor, 1));
            activityTaskSupervisor.mPipModeChangedTargetRootTaskBounds = bounds;
            ActivityTaskSupervisor.ActivityTaskSupervisorHandler activityTaskSupervisorHandler = activityTaskSupervisor.mHandler;
            if (!activityTaskSupervisorHandler.hasMessages(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_ROLE_HOLDER_UPDATE_FAILED)) {
                activityTaskSupervisorHandler.sendEmptyMessage(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_ROLE_HOLDER_UPDATE_FAILED);
            }
        }
        if (displayContent == null) {
            return;
        }
        if (this.mVisibleRequested) {
            displayContent.handleActivitySizeCompatModeIfNeeded(this);
            return;
        }
        if (getAppCompatDisplayInsets() == null || this.visibleIgnoringKeyguard) {
            return;
        }
        WindowProcessController windowProcessController2 = this.app;
        if ((windowProcessController2 == null || (windowProcessController2.mActivityStateFlags & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) == 0) && this.mAppCompatController.mAppCompatSizeCompatModePolicy.mPreCreatedAppCompatDisplayInsets == null) {
            int i = displayContent.mCurrentOverrideConfigurationChanges;
            if (((i & 3456) == 0 || (i & 536872064) == 536872064) && (i & 4096) == 0) {
                return;
            }
            restartProcessIfVisible();
        }
    }

    public final void onCopySplashScreenFinish(SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) {
        WindowState windowState;
        this.mAtmService.mH.removeCallbacks(this.mTransferSplashScreenTimeoutRunnable);
        SurfaceControl applyStartingWindowAnimation = (splashScreenViewParcelable == null || this.mTransferringSplashScreenState != 1 || (windowState = this.mStartingWindow) == null || windowState.mRemoved || this.finishing) ? null : TaskOrganizerController.applyStartingWindowAnimation(windowState);
        if (applyStartingWindowAnimation == null) {
            if (splashScreenViewParcelable != null) {
                splashScreenViewParcelable.clearIfNeeded();
            }
            this.mTransferringSplashScreenState = 3;
            removeStartingWindow();
            return;
        }
        try {
            this.mTransferringSplashScreenState = 2;
            this.mAtmService.mLifecycleManager.scheduleTransactionItem(this.app.mThread, TransferSplashScreenViewStateItem.obtain(this.token, splashScreenViewParcelable, applyStartingWindowAnimation));
            this.mAtmService.mH.postDelayed(this.mTransferSplashScreenTimeoutRunnable, 2000L);
        } catch (Exception unused) {
            Slog.w("ActivityTaskManager", "onCopySplashScreenComplete fail: " + this);
            this.mStartingWindow.cancelAnimation();
            splashScreenViewParcelable.clearIfNeeded();
            this.mTransferringSplashScreenState = 3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0098, code lost:
    
        if (r5 != false) goto L45;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00d4  */
    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onDisplayChanged(com.android.server.wm.DisplayContent r11) {
        /*
            Method dump skipped, instructions count: 360
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.onDisplayChanged(com.android.server.wm.DisplayContent):void");
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final void onLeashAnimationStarting(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        AnimatingActivityRegistry animatingActivityRegistry = this.mAnimatingActivityRegistry;
        if (animatingActivityRegistry != null) {
            animatingActivityRegistry.mAnimatingActivities.add(this);
        }
        if (this.mNeedsLetterboxedAnimation) {
            this.mAppCompatController.mAppCompatLetterboxPolicy.mLetterboxPolicyState.updateLetterboxSurfaceIfNeeded(findMainWindow(true), transaction, getPendingTransaction());
            this.mNeedsAnimationBoundsLayer = true;
        }
        if (this.mNeedsAnimationBoundsLayer) {
            ((WindowContainer) this).mTmpRect.setEmpty();
            if (getDisplayContent().mAppTransitionController.isTransitWithinTask(this.mTransit, this.task)) {
                this.task.getBounds(((WindowContainer) this).mTmpRect);
            } else {
                Task rootTask = getRootTask();
                if (rootTask == null) {
                    return;
                } else {
                    rootTask.getBounds(((WindowContainer) this).mTmpRect);
                }
            }
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled[2]) {
                ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 5991628884266137609L, 0, null, null);
            }
            SurfaceControl.Builder callsite = makeAnimationLeash().setParent(getAnimationLeashParent()).setName(this.mSurfaceControl + " - animation-bounds").setCallsite("ActivityRecord.createAnimationBoundsLayer");
            if (this.mNeedsLetterboxedAnimation) {
                callsite.setEffectLayer();
            }
            SurfaceControl build = callsite.build();
            transaction.show(build);
            this.mAnimationBoundsLayer = build;
            boolean z = CoreRune.FW_FLEXIBLE_TENT_MODE;
            transaction.setLayer(surfaceControl, 0);
            transaction.setLayer(this.mAnimationBoundsLayer, getLastLayer());
            if (this.mNeedsLetterboxedAnimation) {
                int roundedCornersRadius = this.mAppCompatController.mAppCompatLetterboxPolicy.mAppCompatRoundedCorners.getRoundedCornersRadius(findMainWindow(true));
                Rect rect = new Rect();
                this.mAppCompatController.mAppCompatLetterboxPolicy.mLetterboxPolicyState.getLetterboxInnerBounds(rect);
                transaction.setCornerRadius(this.mAnimationBoundsLayer, roundedCornersRadius).setCrop(this.mAnimationBoundsLayer, rect);
            }
            transaction.reparent(surfaceControl, this.mAnimationBoundsLayer);
        }
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public final void onParentChanged(ConfigurationContainer configurationContainer, ConfigurationContainer configurationContainer2) {
        WindowState windowState;
        Task task;
        Task task2;
        TaskFragment taskFragment = (TaskFragment) configurationContainer2;
        TaskFragment taskFragment2 = (TaskFragment) configurationContainer;
        Task task3 = taskFragment != null ? taskFragment.getTask() : null;
        Task task4 = taskFragment2 != null ? taskFragment2.getTask() : null;
        this.task = task4;
        PopOverState popOverState = this.mPopOverState;
        ActivityOptions activityOptions = popOverState.mOptionsInherited;
        if (activityOptions != null && (task2 = popOverState.mActivityRecord.task) != null) {
            int i = task2.mTaskId;
            int i2 = popOverState.mOriginTaskIdInherited;
            if (i == i2) {
                popOverState.mOptions = activityOptions;
                popOverState.mOptionsInherited = null;
                popOverState.mOriginTaskId = i2;
                popOverState.mOriginTaskIdInherited = -1;
            }
        }
        popOverState.toggle();
        if (CoreRune.MW_SPLIT_FLEX_PANEL_LAUNCH_POLICY && (task = this.task) != null && this.mIsFlexPanel) {
            task.autoRemoveRecents = true;
        }
        if (taskFragment2 != null && taskFragment != null && canStartChangeTransition()) {
            boolean z = CoreRune.MW_EMBED_ACTIVITY && this.mStartingData != null && (((windowState = this.mStartingWindow) != null && taskFragment2.mIsEmbedded && windowState.matchParentBounds()) || this.mStartingData.mAssociatedTask != null);
            boolean z2 = ActivityTaskManagerService.isPip2ExperimentEnabled() && inPinnedWindowingMode();
            if ((taskFragment2.isOrganizedTaskFragment() || z2) && !taskFragment2.getBounds().equals(taskFragment.getBounds()) && !z) {
                if (this.mTransitionController.isShellTransitionsEnabled()) {
                    initializeChangeTransition(getBounds());
                } else {
                    taskFragment2.initializeChangeTransition(getBounds(), this.mSurfaceControl);
                }
            }
        }
        super.onParentChanged(taskFragment2, taskFragment);
        if (isPersistable()) {
            if (task3 != null) {
                this.mAtmService.notifyTaskPersisterLocked(task3, false);
            }
            if (task4 != null) {
                this.mAtmService.notifyTaskPersisterLocked(task4, false);
            }
        }
        if (taskFragment == null && taskFragment2 != null) {
            this.mVoiceInteraction = task4.voiceSession != null;
            task4.updateOverrideConfigurationFromLaunchBounds();
            this.mLastReportedMultiWindowMode = inMultiWindowMode();
            this.mLastReportedPictureInPictureMode = inPinnedWindowingMode();
            Bundle bundle = this.info.metaData;
            this.mRequestFreeformForceHiding = bundle != null && bundle.getBoolean("com.samsung.android.sdk.multiwindow.force_hide_floating_multiwindow", false);
        }
        if (this.task == null && getDisplayContent() != null) {
            getDisplayContent().mClosingApps.remove(this);
        }
        Task rootTask = getRootTask();
        Task rootTask2 = getRootTask();
        AnimatingActivityRegistry animatingActivityRegistry = rootTask2 != null ? rootTask2.mAnimatingActivityRegistry : null;
        AnimatingActivityRegistry animatingActivityRegistry2 = this.mAnimatingActivityRegistry;
        if (animatingActivityRegistry2 != null && animatingActivityRegistry2 != animatingActivityRegistry) {
            animatingActivityRegistry2.mAnimatingActivities.remove(this);
            animatingActivityRegistry2.mFinishedTokens.remove(this);
            if (animatingActivityRegistry2.mAnimatingActivities.isEmpty()) {
                animatingActivityRegistry2.endDeferringFinished();
            }
        }
        this.mAnimatingActivityRegistry = animatingActivityRegistry;
        Task task5 = this.task;
        if (task5 == this.mLastParentBeforePip && task5 != null) {
            this.mAtmService.mWindowOrganizerController.mTaskFragmentOrganizerController.onActivityReparentedToTask(this);
            Task task6 = this.mLastParentBeforePip;
            if (task6 != null) {
                task6.mChildPipActivity = null;
                this.mLastParentBeforePip = null;
            }
            this.mLaunchIntoPipHostActivity = null;
            this.mLastTaskFragmentOrganizerBeforePip = null;
            this.mLastEmbeddedParentTfTokenBeforePip = null;
            setWindowingMode(0);
        }
        if (CoreRune.MW_EMBED_ACTIVITY && this.mIsActivityReparentToEmbeddedTask && this.task != null) {
            this.mAtmService.mWindowOrganizerController.mTaskFragmentOrganizerController.onActivityReparentedToTask(this);
            this.mIsActivityReparentToEmbeddedTask = false;
        }
        if (this.mSurfaceControl != null && this.mLastAppSaturationInfo != null) {
            SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
            SurfaceControl surfaceControl = this.mSurfaceControl;
            AppSaturationInfo appSaturationInfo = this.mLastAppSaturationInfo;
            pendingTransaction.setColorTransform(surfaceControl, appSaturationInfo.mMatrix, appSaturationInfo.mTranslation);
            this.mWmService.scheduleAnimationLocked();
        }
        if (taskFragment != null) {
            taskFragment.cleanUpActivityReferences(this);
            this.mRequestedLaunchingTaskFragmentToken = null;
        }
        if (taskFragment2 != null) {
            if (isState(State.RESUMED)) {
                taskFragment2.setResumedActivity(this, "onParentChanged");
            }
            this.mAppCompatController.mTransparentPolicy.start();
        }
        if (rootTask != null && rootTask.topRunningActivity(false) == this && this.firstWindowDrawn) {
            rootTask.setHasBeenVisible();
        }
        updateUntrustedEmbeddingInputProtection();
    }

    /* JADX WARN: Code restructure failed: missing block: B:81:0x0135, code lost:
    
        if (r14.mTransitionController.inTransition() != false) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onRemovedFromDisplay() {
        /*
            Method dump skipped, instructions count: 562
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.onRemovedFromDisplay():void");
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onSyncTransactionCommitted(SurfaceControl.Transaction transaction) {
        super.onSyncTransactionCommitted(transaction);
        StartingData startingData = this.mStartingData;
        if (startingData == null) {
            return;
        }
        startingData.mWaitForSyncTransactionCommit = false;
        int i = startingData.mRemoveAfterTransaction;
        if (i == 1) {
            removeStartingWindowAnimation(startingData.mPrepareRemoveAnimation);
        } else if (i == 2) {
            removeStartingWindow();
        }
    }

    public final boolean onlyDeskInUiModeChanged(Configuration configuration) {
        int i = getConfiguration().uiMode;
        boolean z = (i & 15) == 2;
        int i2 = configuration.uiMode;
        return (z != ((i2 & 15) == 2)) && !((i & (-16)) != (i2 & (-16)));
    }

    public final void pauseKeyDispatchingLocked() {
        if (this.keysPaused) {
            return;
        }
        this.keysPaused = true;
        if (getDisplayContent() != null) {
            InputMonitor inputMonitor = getDisplayContent().mInputMonitor;
            inputMonitor.getClass();
            if (this.paused) {
                return;
            }
            this.paused = true;
            inputMonitor.updateInputWindowsLw(true);
        }
    }

    public final void postWindowRemoveStartingWindowCleanup(WindowState windowState) {
        if (this.mStartingWindow == windowState) {
            this.mStartingWindow = null;
            this.mStartingData = null;
            this.mStartingSurface = null;
        }
        if (this.mChildren.size() == 0 && this.mVisibleSetFromTransferredStartingWindow) {
            setVisible(false);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void prepareSurfaces() {
        Task.DecorSurfaceContainer decorSurfaceContainer;
        Task task = this.task;
        boolean z = (this.mVisible && !(task != null && (decorSurfaceContainer = task.mDecorSurfaceContainer) != null && decorSurfaceContainer.mIsBoosted)) || isAnimating(2, FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED);
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
            SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
            if (z) {
                pendingTransaction.show(windowContainerThumbnail.mSurfaceControl);
            } else {
                pendingTransaction.hide(windowContainerThumbnail.mSurfaceControl);
            }
        }
        this.mLastSurfaceShowing = z;
        super.prepareSurfaces();
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final boolean providesMaxBounds() {
        if (getUid() == 1000) {
            return false;
        }
        DisplayContent displayContent = this.mDisplayContent;
        if ((displayContent != null && !displayContent.mSandboxDisplayApis) || this.info.neverSandboxDisplayApis(sConstrainDisplayApisConfig)) {
            return false;
        }
        if (this.info.alwaysSandboxDisplayApis(sConstrainDisplayApisConfig) || getAppCompatDisplayInsets() != null || shouldCreateAppCompatDisplayInsets()) {
            return true;
        }
        return CoreRune.MT_SIZE_COMPAT_POLICY && this.mResolvedConfigChangeFlags != 0;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean providesOrientation() {
        return this.mStyleFillsParent || this.mOccludesParent;
    }

    public final void recomputeConfiguration() {
        TransparentPolicy transparentPolicy = this.mAppCompatController.mTransparentPolicy;
        final ActivityRecord$$ExternalSyntheticLambda3 activityRecord$$ExternalSyntheticLambda3 = new ActivityRecord$$ExternalSyntheticLambda3(2);
        if (((Boolean) transparentPolicy.mTransparentPolicyState.findOpaqueNotFinishingActivityBelow().map(new Function() { // from class: com.android.server.wm.TransparentPolicy$TransparentPolicyState$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                activityRecord$$ExternalSyntheticLambda3.accept((ActivityRecord) obj);
                return Boolean.TRUE;
            }
        }).orElse(Boolean.FALSE)).booleanValue()) {
            return;
        }
        onRequestedOverrideConfigurationChanged(getRequestedOverrideConfiguration());
    }

    public final void registerCaptureObserver(IScreenCaptureObserver iScreenCaptureObserver) {
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

    @Override // com.android.server.wm.WindowContainer
    public final void removeChild(WindowContainer windowContainer) {
        WindowState windowState = (WindowState) windowContainer;
        if (this.mChildren.contains(windowState)) {
            if (windowState.mAttrs.type == 1 && windowState.isSecureLocked()) {
                MultiTaskingController multiTaskingController = this.mAtmService.mMultiTaskingController;
                multiTaskingController.getClass();
                Task task = windowState.getTask();
                DisplayContent displayContent = windowState.getDisplayContent();
                if (task != null && displayContent != null && displayContent.isRemoteAppDisplay()) {
                    multiTaskingController.mH.sendMessage(multiTaskingController.mH.obtainMessage(1, task.mTaskId, 0, windowState.mAttrs.packageName));
                }
            }
            super.removeChild(windowState);
            checkKeyguardFlagsChanged();
            updateLetterboxSurfaceIfNeeded(windowState);
        }
    }

    public final void removeFromHistory(String str) {
        finishActivityResults(0, null, null);
        makeFinishingLocked();
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[2]) {
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 8836546031252812807L, 0, null, String.valueOf(this), String.valueOf(str), String.valueOf(Debug.getCallers(5)));
        }
        if (this.inHistory) {
            this.inHistory = false;
            if (this.task != null && !this.finishing) {
                this.task = null;
            }
            abortAndClearOptionsAnimation();
        }
        removeTimeouts();
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 8348126473928520781L, 0, null, String.valueOf(this));
        }
        setState(State.DESTROYED, "removeFromHistory");
        detachFromProcess();
        resumeKeyDispatchingLocked();
        DisplayContent displayContent = this.mDisplayContent;
        IBinder iBinder = this.token;
        WindowToken removeWindowToken = displayContent.removeWindowToken(iBinder, true);
        if (removeWindowToken == null) {
            Slog.w("WindowManager", "removeAppToken: Attempted to remove non-existing token: " + iBinder);
        } else {
            ActivityRecord asActivityRecord = removeWindowToken.asActivityRecord();
            if (asActivityRecord == null) {
                Slog.w("WindowManager", "Attempted to remove non-App token: " + iBinder + " token=" + removeWindowToken);
            } else {
                asActivityRecord.onRemovedFromDisplay();
                if (asActivityRecord == displayContent.mFixedRotationLaunchingApp) {
                    asActivityRecord.finishFixedRotationTransform(null);
                    displayContent.setFixedRotationLaunchingAppUnchecked(-1, null);
                }
            }
        }
        cleanUpActivityServices();
        UriPermissionOwner uriPermissionOwner = this.uriPermissions;
        if (uriPermissionOwner != null) {
            uriPermissionOwner.removeUriPermission(3, -1, null, null);
            this.uriPermissions = null;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void removeIfPossible() {
        this.mIsExiting = false;
        removeAllWindowsIfPossible();
        removeImmediately();
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    public final void removeImmediately() {
        if (this.mState != State.DESTROYED) {
            Slog.w("ActivityTaskManager", "Force remove immediately " + this + " state=" + this.mState);
            destroyImmediately("removeImmediately");
            destroyed("removeImmediately");
        } else {
            onRemovedFromDisplay();
        }
        ActivityRecordInputSink activityRecordInputSink = this.mActivityRecordInputSink;
        SurfaceControl surfaceControl = activityRecordInputSink.mSurfaceControl;
        if (surfaceControl != null) {
            surfaceControl.release();
            activityRecordInputSink.mSurfaceControl = null;
        }
        super.removeImmediately();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0031 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void removeResultsLocked(com.android.server.wm.ActivityRecord r4, java.lang.String r5, int r6) {
        /*
            r3 = this;
            java.util.ArrayList r0 = r3.results
            if (r0 == 0) goto L34
            int r0 = r0.size()
            int r0 = r0 + (-1)
        La:
            if (r0 < 0) goto L34
            java.util.ArrayList r1 = r3.results
            java.lang.Object r1 = r1.get(r0)
            com.android.server.wm.ActivityResult r1 = (com.android.server.wm.ActivityResult) r1
            com.android.server.wm.ActivityRecord r2 = r1.mFrom
            if (r2 == r4) goto L19
            goto L31
        L19:
            java.lang.String r2 = r1.mResultWho
            if (r2 != 0) goto L20
            if (r5 == 0) goto L27
            goto L31
        L20:
            boolean r2 = r2.equals(r5)
            if (r2 != 0) goto L27
            goto L31
        L27:
            int r1 = r1.mRequestCode
            if (r1 == r6) goto L2c
            goto L31
        L2c:
            java.util.ArrayList r1 = r3.results
            r1.remove(r0)
        L31:
            int r0 = r0 + (-1)
            goto La
        L34:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.removeResultsLocked(com.android.server.wm.ActivityRecord, java.lang.String, int):void");
    }

    public final void removeStartingWindow() {
        StartingSurfaceController.StartingSurface startingSurface;
        int i;
        StartingData startingData;
        int i2;
        boolean isEligibleForLetterboxEducation = isEligibleForLetterboxEducation();
        if (!this.finishing && this.mHandleExitSplashScreen && (startingSurface = this.mStartingSurface) != null && this.mStartingWindow != null && (i = this.mTransferringSplashScreenState) != 3 && ((startingData = this.mStartingData) == null || !startingData.mResizedFromTransfer)) {
            if (i == 2 || i == 1) {
                return;
            }
            if (startingData != null && startingData.mWaitForSyncTransactionCommit) {
                startingData.mRemoveAfterTransaction = 2;
                return;
            }
            this.mTransferringSplashScreenState = 1;
            if (startingSurface != null) {
                TaskOrganizerController taskOrganizerController = this.mAtmService.mTaskOrganizerController;
                Task task = this.task;
                ITaskOrganizer iTaskOrganizer = startingSurface.mTaskOrganizer;
                taskOrganizerController.getClass();
                if (task.getRootTask() != null) {
                    if (iTaskOrganizer == null) {
                        iTaskOrganizer = taskOrganizerController.getTaskOrganizer();
                    }
                    if (iTaskOrganizer != null) {
                        try {
                            iTaskOrganizer.copySplashScreenView(task.mTaskId);
                        } catch (RemoteException e) {
                            Slog.e("TaskOrganizerController", "Exception sending copyStartingWindowView callback", e);
                        }
                        this.mAtmService.mH.postDelayed(this.mTransferSplashScreenTimeoutRunnable, 2000L);
                        i2 = this.mTransferringSplashScreenState;
                        if (i2 != 2 || i2 == 1) {
                            return;
                        }
                    }
                }
            }
            this.mTransferringSplashScreenState = 3;
            removeStartingWindow();
            this.mAtmService.mH.postDelayed(this.mTransferSplashScreenTimeoutRunnable, 2000L);
            i2 = this.mTransferringSplashScreenState;
            if (i2 != 2) {
                return;
            } else {
                return;
            }
        }
        removeStartingWindowAnimation(true);
        Task task2 = this.task;
        if (isEligibleForLetterboxEducation == isEligibleForLetterboxEducation() || task2 == null) {
            return;
        }
        task2.dispatchTaskInfoChangedIfNeeded(true);
    }

    public final void removeStartingWindowAnimation(boolean z) {
        Task task;
        boolean z2 = false;
        this.mTransferringSplashScreenState = 0;
        StartingData startingData = this.mStartingData;
        if (startingData != null && (task = this.task) != null) {
            task.mSharedStartingData = null;
        }
        if (this.mStartingWindow == null) {
            if (startingData != null) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STARTING_WINDOW_enabled[1]) {
                    ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -1948849214526113495L, 0, "Clearing startingData for token=%s", String.valueOf(this));
                }
                this.mStartingData = null;
                this.mStartingSurface = null;
                return;
            }
            return;
        }
        if (startingData == null) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STARTING_WINDOW_enabled[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -2178757341169633804L, 0, "Tried to remove starting window but startingWindow was null: %s", String.valueOf(this));
                return;
            }
            return;
        }
        if (startingData.mWaitForSyncTransactionCommit || this.mTransitionController.isCollecting(this)) {
            StartingData startingData2 = this.mStartingData;
            startingData2.mRemoveAfterTransaction = 1;
            startingData2.mPrepareRemoveAnimation = z;
            return;
        }
        if (z && this.mStartingData.needRevealAnimation() && this.mStartingWindow.isVisibleByPolicy()) {
            z2 = true;
        }
        if (z2) {
            this.mStartingWindow.mSkipExitAnimation = true;
        }
        boolean hasImeSurface = this.mStartingData.hasImeSurface();
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_STARTING_WINDOW_enabled;
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 5545923784327902026L, 48, "Schedule remove starting %s startingWindow=%s animate=%b Callers=%s", String.valueOf(this), String.valueOf(this.mStartingWindow), Boolean.valueOf(z2), String.valueOf(Debug.getCallers(5)));
        }
        StartingSurfaceController.StartingSurface startingSurface = this.mStartingSurface;
        this.mStartingData = null;
        this.mStartingSurface = null;
        this.mStartingWindow = null;
        this.mTransitionChangeFlags &= -9;
        if (startingSurface == null) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -5150982660941074218L, 0, "startingWindow was set but startingSurface==null, couldn't remove", null);
                return;
            }
            return;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = StartingSurfaceController.this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                StartingSurfaceController.this.mService.mAtmService.mTaskOrganizerController.removeStartingWindow(startingSurface.mTask, startingSurface.mTaskOrganizer, z2, hasImeSurface);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void removeTimeouts() {
        this.mTaskSupervisor.mHandler.removeMessages(200, this);
        this.mAtmService.mH.removeCallbacks(this.mPauseTimeoutRunnable);
        this.mAtmService.mH.removeCallbacks(this.mStopTimeoutRunnable);
        this.mAtmService.mH.removeCallbacks(this.mDestroyTimeoutRunnable);
        this.launchTickTime = 0L;
        Task rootTask = getRootTask();
        if (rootTask == null) {
            return;
        }
        rootTask.forAllActivities(new Task$$ExternalSyntheticLambda6(5));
    }

    public final void reparent(TaskFragment taskFragment, int i, String str) {
        if (getParent() == null) {
            Slog.w("ActivityTaskManager", "reparent: Attempted to reparent non-existing app token: " + this.token);
        } else {
            if (getTaskFragment() != taskFragment) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[2]) {
                    ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 5521236266092347335L, 20, null, String.valueOf(this), Long.valueOf(this.task.mTaskId), Long.valueOf(i));
                }
                reparent(taskFragment, i);
                return;
            }
            throw new IllegalArgumentException(str + ": task fragment =" + taskFragment + " is already the parent of r=" + this);
        }
    }

    public final void requestUpdateWallpaperIfNeeded() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowState) this.mChildren.get(size)).requestUpdateWallpaperIfNeeded();
        }
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    public final void resetSurfacePositionForAnimationLeash(SurfaceControl.Transaction transaction) {
    }

    public final void resolveAspectRatioRestriction(Configuration configuration) {
        if (!CoreRune.MT_SIZE_COMPAT_POLICY || this.mResolvedConfigChangeFlags == 0) {
            Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
            Rect rect = this.mResolveConfigHint.mParentAppBoundsOverride;
            Rect bounds = configuration.windowConfiguration.getBounds();
            Rect bounds2 = resolvedOverrideConfiguration.windowConfiguration.getBounds();
            this.mTmpBounds.setEmpty();
            AppCompatAspectRatioPolicy appCompatAspectRatioPolicy = this.mAppCompatController.mAppCompatAspectRatioPolicy;
            appCompatAspectRatioPolicy.mAppCompatAspectRatioState.mIsAspectRatioApplied = appCompatAspectRatioPolicy.applyAspectRatio(this.mTmpBounds, rect, bounds, FullScreenMagnificationGestureHandler.MAX_SCALE);
            if (!this.mTmpBounds.isEmpty()) {
                bounds2.set(this.mTmpBounds);
            }
            if (bounds2.isEmpty() || bounds2.equals(bounds)) {
                return;
            }
            this.mResolveConfigHint.mTmpOverrideDisplayInfo = getFixedRotationTransformDisplayInfo();
            computeConfigByResolveHint(resolvedOverrideConfiguration, configuration);
            this.mAppCompatController.mAppCompatAspectRatioPolicy.mAppCompatAspectRatioState.mLetterboxBoundsForAspectRatio = new Rect(bounds2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x03ac, code lost:
    
        if (r13 == 7) goto L376;
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x04cf, code lost:
    
        if (getAppCompatDisplayInsets() != null) goto L376;
     */
    /* JADX WARN: Code restructure failed: missing block: B:471:0x0986, code lost:
    
        if (((java.lang.Math.max(r4, r6) + 0.5f) / java.lang.Math.min(r4, r6)) <= r0) goto L550;
     */
    /* JADX WARN: Code restructure failed: missing block: B:553:0x059b, code lost:
    
        if ((r1 != null ? r1.isTreatmentEnabledForActivity(true, r8) : false) != false) goto L342;
     */
    /* JADX WARN: Removed duplicated region for block: B:219:0x05e7  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x05f9  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0651  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0673  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0680  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x09b8  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x09de  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x09e3  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x09f6  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0a05  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0a32  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0a46  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0a5f  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x0a98  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x0aaf  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x0ac4  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x0ad9  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x0af1  */
    /* JADX WARN: Removed duplicated region for block: B:327:0x0b0e  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0b19  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x0b23  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x0b36  */
    /* JADX WARN: Removed duplicated region for block: B:341:0x0b59  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x0b90  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x0b92  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x0a34  */
    /* JADX WARN: Removed duplicated region for block: B:359:0x068c  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x076b  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x0772  */
    /* JADX WARN: Removed duplicated region for block: B:408:0x0779  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x078b  */
    /* JADX WARN: Removed duplicated region for block: B:415:0x0810  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x083c  */
    /* JADX WARN: Removed duplicated region for block: B:436:0x08ac  */
    /* JADX WARN: Removed duplicated region for block: B:443:0x08f0  */
    /* JADX WARN: Removed duplicated region for block: B:446:0x08ff  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x0906  */
    /* JADX WARN: Removed duplicated region for block: B:456:0x0931  */
    /* JADX WARN: Removed duplicated region for block: B:474:0x0915  */
    /* JADX WARN: Removed duplicated region for block: B:476:0x0909  */
    /* JADX WARN: Removed duplicated region for block: B:477:0x0902  */
    /* JADX WARN: Removed duplicated region for block: B:480:0x08c2  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x08cb  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x08d1  */
    /* JADX WARN: Removed duplicated region for block: B:485:0x08ae  */
    /* JADX WARN: Removed duplicated region for block: B:488:0x0795  */
    /* JADX WARN: Removed duplicated region for block: B:513:0x077f  */
    /* JADX WARN: Removed duplicated region for block: B:514:0x0775  */
    /* JADX WARN: Removed duplicated region for block: B:515:0x076d  */
    /* JADX WARN: Removed duplicated region for block: B:527:0x0990  */
    /* JADX WARN: Removed duplicated region for block: B:538:0x0626  */
    /* JADX WARN: Removed duplicated region for block: B:539:0x062a  */
    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void resolveOverrideConfiguration(android.content.res.Configuration r28) {
        /*
            Method dump skipped, instructions count: 2984
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.resolveOverrideConfiguration(android.content.res.Configuration):void");
    }

    public final void restartProcessIfVisible() {
        if (this.finishing) {
            return;
        }
        Slog.i("ActivityTaskManager", "Request to restart process of " + this);
        this.mAppCompatController.mAppCompatSizeCompatModePolicy.clearSizeCompatMode(false, true);
        if (attachedToProcess()) {
            setState(State.RESTARTING_PROCESS, "restartActivityProcess");
            if (!this.mVisibleRequested || this.mHaveState) {
                this.mAtmService.mH.post(new ActivityRecord$$ExternalSyntheticLambda7(0, this));
            } else if (this.mTransitionController.isShellTransitionsEnabled()) {
                final Transition transition = new Transition(5, 0, this.mTransitionController, this.mWmService.mSyncEngine);
                this.mTransitionController.startCollectOrQueue(transition, new TransitionController.OnStartCollect() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda8
                    @Override // com.android.server.wm.TransitionController.OnStartCollect
                    public final void onCollectStarted(boolean z) {
                        ActivityRecord activityRecord = ActivityRecord.this;
                        ActivityRecord.State state = activityRecord.mState;
                        ActivityRecord.State state2 = ActivityRecord.State.RESTARTING_PROCESS;
                        Transition transition2 = transition;
                        if (state != state2 || !activityRecord.attachedToProcess()) {
                            transition2.abort();
                            return;
                        }
                        activityRecord.setVisibleRequested(false);
                        transition2.collect(activityRecord, false);
                        activityRecord.mTransitionController.requestStartTransition(transition2, activityRecord.task, null, null);
                        activityRecord.scheduleStopForRestartProcess();
                    }
                });
            } else {
                startFreezingScreen(-1);
                scheduleStopForRestartProcess();
            }
        }
    }

    public final boolean resumeConfigurationDispatch() {
        int i = this.mPauseConfigurationDispatchCount - 1;
        this.mPauseConfigurationDispatchCount = i;
        if (i > 0) {
            return false;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, 5153784493059555057L, 0, "Resuming configuration dispatch for %s", String.valueOf(this));
        }
        if (this.mPauseConfigurationDispatchCount < 0) {
            Slog.wtf("ActivityTaskManager", "Trying to resume non-paused configuration dispatch");
            this.mPauseConfigurationDispatchCount = 0;
            return false;
        }
        if (this.mLastReportedDisplayId == getDisplayId() && getConfiguration().equals(this.mLastReportedConfiguration.getMergedConfiguration())) {
            return false;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            WindowState windowState = (WindowState) getChildAt(childCount);
            Configuration configuration = getConfiguration();
            if (!isConfigurationDispatchPaused()) {
                super.dispatchConfigurationToChild(windowState, configuration);
            }
        }
        updateReportedConfigurationAndSend();
        return true;
    }

    public final void resumeKeyDispatchingLocked() {
        if (this.keysPaused) {
            this.keysPaused = false;
            if (getDisplayContent() != null) {
                InputMonitor inputMonitor = getDisplayContent().mInputMonitor;
                inputMonitor.getClass();
                if (this.paused) {
                    this.paused = false;
                    inputMonitor.updateInputWindowsLw(true);
                }
            }
        }
    }

    public final void saveToXml(TypedXmlSerializer typedXmlSerializer) {
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
        typedXmlSerializer.attributeBoolean((String) null, "component_specified", this.componentSpecified);
        typedXmlSerializer.attributeInt((String) null, "user_id", this.mUserId);
        ActivityManager.TaskDescription taskDescription = this.taskDescription;
        if (taskDescription != null) {
            taskDescription.saveToXml(typedXmlSerializer);
        }
        typedXmlSerializer.startTag((String) null, KnoxCustomManagerService.INTENT);
        this.intent.saveToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((String) null, KnoxCustomManagerService.INTENT);
        if (isPersistable() && this.mPersistentState != null) {
            typedXmlSerializer.startTag((String) null, "persistable_bundle");
            this.mPersistentState.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((String) null, "persistable_bundle");
        }
        if (android.security.Flags.contentUriPermissionApis()) {
            ActivityCallerState.CallerInfo callerInfo = (ActivityCallerState.CallerInfo) this.mCallerState.mCallerTokenInfoMap.getOrDefault(this.initialCallerInfoAccessToken, null);
            if (callerInfo != null) {
                typedXmlSerializer.startTag((String) null, "initial_caller_info");
                typedXmlSerializer.attributeInt((String) null, "caller_uid", callerInfo.mUid);
                String str4 = callerInfo.mPackageName;
                if (str4 != null) {
                    typedXmlSerializer.attribute((String) null, "caller_package", str4);
                }
                typedXmlSerializer.attributeBoolean((String) null, "caller_is_share_enabled", callerInfo.mIsShareIdentityEnabled);
                for (int size = callerInfo.mReadableContentUris.size() - 1; size >= 0; size--) {
                    ActivityCallerState.CallerInfo.saveGrantUriToXml(typedXmlSerializer, (GrantUri) callerInfo.mReadableContentUris.valueAt(size), "readable_content_uri");
                }
                for (int size2 = callerInfo.mWritableContentUris.size() - 1; size2 >= 0; size2--) {
                    ActivityCallerState.CallerInfo.saveGrantUriToXml(typedXmlSerializer, (GrantUri) callerInfo.mWritableContentUris.valueAt(size2), "writable_content_uri");
                }
                for (int size3 = callerInfo.mInaccessibleContentUris.size() - 1; size3 >= 0; size3--) {
                    ActivityCallerState.CallerInfo.saveGrantUriToXml(typedXmlSerializer, (GrantUri) callerInfo.mInaccessibleContentUris.valueAt(size3), "inaccessible_content_uri");
                }
                typedXmlSerializer.endTag((String) null, "initial_caller_info");
            }
        }
    }

    public final void scheduleActivityMovedToDisplay(int i, Configuration configuration, ActivityWindowInfo activityWindowInfo) {
        boolean attachedToProcess = attachedToProcess();
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_SWITCH_enabled;
        if (!attachedToProcess) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_SWITCH, -6509265758887333864L, 4, null, String.valueOf(this), Long.valueOf(i));
                return;
            }
            return;
        }
        try {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_SWITCH, -4183059578873561863L, 4, null, String.valueOf(this), Long.valueOf(i), String.valueOf(configuration));
            }
            this.mAtmService.mLifecycleManager.scheduleTransactionItem(this.app.mThread, MoveToDisplayItem.obtain(this.token, i, configuration, activityWindowInfo));
        } catch (RemoteException unused) {
        }
    }

    public final void scheduleAddStartingWindow() {
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_STARTING_WINDOW_enabled;
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 5659016061937922595L, 0, "Add starting %s: startingData=%s", String.valueOf(this), String.valueOf(this.mStartingData));
        }
        StartingSurfaceController.StartingSurface createStartingSurface = this.mStartingData.createStartingSurface(this);
        this.mStartingSurface = createStartingSurface;
        if (createStartingSurface == null) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 1048048288756547220L, 0, "Surface returned was null: %s", String.valueOf(this));
                return;
            }
            return;
        }
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 7506106334102501360L, 0, "Added starting %s: startingWindow=%s startingView=%s", String.valueOf(this), String.valueOf(this.mStartingWindow), String.valueOf(this.mStartingSurface));
        }
    }

    public final void scheduleConfigurationChanged(Configuration configuration, ActivityWindowInfo activityWindowInfo) {
        boolean attachedToProcess = attachedToProcess();
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_CONFIGURATION_enabled;
        if (!attachedToProcess) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_CONFIGURATION, 7435279034964784633L, 0, null, String.valueOf(this));
                return;
            }
            return;
        }
        try {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_CONFIGURATION, -7418876140361338495L, 0, null, String.valueOf(this), String.valueOf(configuration));
            }
            Slog.d("ActivityTaskManager", "scheduleConfigurationChanged: " + this + " state=" + this.mState + " config=" + configuration + " caller=" + Debug.getCallers(3));
            this.mAtmService.mLifecycleManager.scheduleTransactionItem(this.app.mThread, ActivityConfigurationChangeItem.obtain(this.token, configuration, activityWindowInfo));
        } catch (RemoteException unused) {
        }
    }

    public final void scheduleStopForRestartProcess() {
        try {
            this.mAtmService.mLifecycleManager.scheduleTransactionItem(this.app.mThread, StopActivityItem.obtain(this.token));
        } catch (RemoteException e) {
            Slog.w("ActivityTaskManager", "Exception thrown during restart " + this, e);
        }
        ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
        activityTaskSupervisor.mHandler.removeMessages(213, this);
        ActivityTaskSupervisor.ActivityTaskSupervisorHandler activityTaskSupervisorHandler = activityTaskSupervisor.mHandler;
        activityTaskSupervisorHandler.sendMessageDelayed(activityTaskSupervisorHandler.obtainMessage(213, this), 2000L);
    }

    public final boolean scheduleTopResumedActivityChanged(boolean z) {
        boolean attachedToProcess = attachedToProcess();
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled;
        if (!attachedToProcess) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_STATES, -4284934398288119962L, 0, null, String.valueOf(this));
            }
            return false;
        }
        if (z) {
            this.app.addToPendingTop();
        }
        try {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 7244227111034368231L, 12, null, String.valueOf(this), Boolean.valueOf(z));
            }
            Slog.d("ActivityTaskManager", "scheduleTopResumedActivityChanged, onTop=" + z + ", r=" + this + ", caller=" + Debug.getCallers(6));
            this.mAtmService.mLifecycleManager.scheduleTransactionItem(this.app.mThread, TopResumedActivityChangeItem.obtain(this.token, z));
            return true;
        } catch (RemoteException e) {
            Slog.w("ActivityTaskManager", "Failed to send top-resumed=" + z + " to " + this, e);
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0041, code lost:
    
        ((com.android.server.uri.UriGrantsManagerService.LocalService) r17.mAtmService.mUgmInternal).grantUriPermissionUncheckedFromIntent(r24, getUriPermissionsLocked());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendResult(int r18, java.lang.String r19, int r20, int r21, android.content.Intent r22, android.os.IBinder r23, com.android.server.uri.NeededUriGrants r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 347
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.sendResult(int, java.lang.String, int, int, android.content.Intent, android.os.IBinder, com.android.server.uri.NeededUriGrants, boolean):void");
    }

    public final void setAppLayoutChanges(int i) {
        if (this.mChildren.isEmpty()) {
            return;
        }
        DisplayContent displayContent = getDisplayContent();
        displayContent.pendingLayoutChanges = i | displayContent.pendingLayoutChanges;
    }

    @Override // com.android.server.wm.WindowToken
    public final void setClientVisible(boolean z) {
        if (z || !this.mDeferHidingClient) {
            super.setClientVisible(z);
        }
    }

    public final void setDeferHidingClient(boolean z) {
        if (this.mDeferHidingClient == z) {
            return;
        }
        this.mDeferHidingClient = z;
        if (z || this.mVisibleRequested) {
            return;
        }
        setVisibility(false);
    }

    public void setDropInputMode(int i) {
        if (this.mLastDropInputMode != i) {
            this.mLastDropInputMode = i;
            ((SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get()).setDropInputMode(this.mSurfaceControl, i).apply();
        }
    }

    public final void setEnteringPipFromSplit(String str, boolean z) {
        if (this.mIsEnteringPipFromSplit != z) {
            this.mIsEnteringPipFromSplit = z;
            Slog.d("ActivityTaskManager", "setEnteringPipFromSplit: " + z + ", " + str + ", " + this);
        }
    }

    public final void setHiddenWhileEnteringPinnedMode(String str, boolean z) {
        if (this.mHiddenWhileEnteringPinnedMode == z) {
            return;
        }
        Slog.d("ActivityTaskManager", "setHiddenWhileEnteringPinnedMode: " + z + ", " + str + ", " + this);
        this.mHiddenWhileEnteringPinnedMode = z;
        this.mAtmService.mH.removeCallbacks(this.mHiddenWhileEnteringPinnedTimeoutRunnable);
        if (z) {
            this.mAtmService.mH.postDelayed(this.mHiddenWhileEnteringPinnedTimeoutRunnable, 3000L);
        }
        if (this.mSurfaceControl != null) {
            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get();
            transaction.setAlpha(this.mSurfaceControl, z ? FullScreenMagnificationGestureHandler.MAX_SCALE : 1.0f);
            transaction.apply();
        }
    }

    public final boolean setOccludesParent(boolean z, boolean z2) {
        WindowState findMainWindow;
        PopOverState popOverState = this.mPopOverState;
        char c = 3;
        char c2 = !popOverState.mIsActivated ? (char) 2 : popOverState.shouldRemoveOutlineEffect() ? (char) 3 : (char) 1;
        PopOverState popOverState2 = this.mPopOverState;
        popOverState2.mLastOccludesParent = z;
        boolean z3 = popOverState2.mIsActivated;
        if (z3) {
            if (!z3) {
                c = 2;
            } else if (!popOverState2.shouldRemoveOutlineEffect()) {
                c = 1;
            }
            if (c != c2) {
                recomputeConfiguration();
            }
            return false;
        }
        boolean z4 = z != this.mOccludesParent;
        this.mOccludesParent = z;
        if (!z2 && (findMainWindow = findMainWindow(true)) != null) {
            findMainWindow.mWinAnimator.setOpaqueLocked((!PixelFormat.formatHasAlpha(findMainWindow.mAttrs.format)) & z);
        }
        if (z4 && this.task != null) {
            if (z) {
                Task rootTask = getRootTask();
                if (this != rootTask.mPendingConvertFromTranslucentActivity) {
                    Slog.e("ActivityTaskManager", "convertFromTranslucent expects " + rootTask.mPendingConvertFromTranslucentActivity + " but is " + this);
                }
                rootTask.mPendingConvertFromTranslucentActivity = null;
            } else {
                Task rootTask2 = getRootTask();
                rootTask2.mTranslucentActivityWaiting = this;
                rootTask2.mPendingConvertFromTranslucentActivity = this;
                rootTask2.mUndrawnActivitiesBelowTopTranslucent.clear();
                rootTask2.mHandler.sendEmptyMessageDelayed(101, 2000L);
            }
        }
        if (z4 || !z) {
            this.mRootWindowContainer.ensureActivitiesVisible();
        }
        return z4;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final boolean setOverrideGender(Configuration configuration, int i) {
        return WindowProcessController.applyConfigGenderOverride(configuration, i, this.mAtmService.mGrammaticalManagerInternal, getUid());
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setProcess(final com.android.server.wm.WindowProcessController r12) {
        /*
            Method dump skipped, instructions count: 325
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.setProcess(com.android.server.wm.WindowProcessController):void");
    }

    public final void setRequestedOrientation(int i) {
        int i2;
        AppCompatOrientationPolicy appCompatOrientationPolicy = this.mAppCompatController.mOrientationPolicy;
        AppCompatOrientationOverrides appCompatOrientationOverrides = appCompatOrientationPolicy.mAppCompatOverrides.mAppCompatOrientationOverrides;
        boolean shouldEnableWithOverrideAndProperty = appCompatOrientationOverrides.mIgnoreRequestedOrientationOptProp.shouldEnableWithOverrideAndProperty(appCompatOrientationOverrides.mActivityRecord.info.isChangeEnabled(254631730L));
        ActivityRecord activityRecord = appCompatOrientationPolicy.mActivityRecord;
        if (shouldEnableWithOverrideAndProperty) {
            if (appCompatOrientationOverrides.mOrientationOverridesState.mIsRelaunchingAfterRequestedOrientationChanged) {
                Slog.w("ActivityTaskManager", "Ignoring orientation update to " + ActivityInfo.screenOrientationToString(i) + " due to relaunching after setRequestedOrientation for " + activityRecord);
                return;
            }
            DisplayContent displayContent = activityRecord.mAppCompatController.mActivityRecord.mDisplayContent;
            AppCompatCameraPolicy appCompatCameraPolicy = displayContent != null ? displayContent.mAppCompatCameraPolicy : null;
            if (appCompatCameraPolicy != null) {
                DisplayRotationCompatPolicy displayRotationCompatPolicy = appCompatCameraPolicy.mDisplayRotationCompatPolicy;
                if (displayRotationCompatPolicy != null ? displayRotationCompatPolicy.isTreatmentEnabledForActivity(true, activityRecord) : false) {
                    Slog.w("ActivityTaskManager", "Ignoring orientation update to " + ActivityInfo.screenOrientationToString(i) + " due to camera compat treatment for " + activityRecord);
                    return;
                }
            }
        }
        if (appCompatOrientationOverrides.shouldIgnoreOrientationRequestLoop()) {
            Slog.w("ActivityTaskManager", "Ignoring orientation update to " + ActivityInfo.screenOrientationToString(i) + " as orientation request loop was detected for " + activityRecord);
            return;
        }
        int i3 = this.mPendingRelaunchCount;
        if ((!CoreRune.MT_APP_COMPAT_ROTATION_COMPAT_MODE || getAppCompatDisplayInsets() == null || !getAppCompatDisplayInsets().mIsRotationCompatMode) && getRequestedConfigurationOrientation(false, i) != getRequestedConfigurationOrientation(false)) {
            this.mAppCompatController.mAppCompatSizeCompatModePolicy.clearSizeCompatModeAttributes();
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -9178011226407552682L, 0, null, String.valueOf(ActivityInfo.screenOrientationToString(i)), String.valueOf(this));
        }
        setOrientation(i, this);
        if (CoreRune.MT_SIZE_COMPAT_POLICY) {
            SizeCompatPolicyManager sizeCompatPolicyManager = SizeCompatPolicyManager.LazyHolder.sManager;
            Task task = this.task;
            sizeCompatPolicyManager.getClass();
            SizeCompatPolicyManager.ensureConfiguration(task);
        }
        if (this.mAtmService.mDexCompatController.isOrientationChanged(this)) {
            this.mAtmService.mDexCompatController.rotateDexCompatTaskLocked(this);
        } else {
            Task task2 = this.task;
            if (task2.mDexCompatUiMode == 3) {
                this.mAtmService.mDexCompatController.changeWindowingModeIfNeeded(task2, this);
            }
        }
        if (!getMergedOverrideConfiguration().equals(this.mLastReportedConfiguration.getMergedConfiguration())) {
            ensureActivityConfiguration(false);
            if (this.mPendingRelaunchCount > i3) {
                this.mAppCompatController.mAppCompatOverrides.mAppCompatOrientationOverrides.mOrientationOverridesState.mIsRelaunchingAfterRequestedOrientationChanged = true;
            }
            if (this.mTransitionController.inPlayingTransition(this)) {
                this.mTransitionController.mValidateActivityCompat.add(this);
            }
        }
        TaskChangeNotificationController taskChangeNotificationController = this.mAtmService.mTaskChangeNotificationController;
        Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(12, this.task.mTaskId, i);
        taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyActivityRequestedOrientationChanged, obtainMessage);
        obtainMessage.sendToTarget();
        DisplayRotation displayRotation = this.mDisplayContent.mDisplayRotation;
        if (displayRotation.mCompatPolicyForImmersiveApps == null || (i2 = displayRotation.mRotationChoiceShownToUserForConfirmation) == -1) {
            return;
        }
        displayRotation.mOrientationListener.onProposedRotationChanged(i2);
    }

    public final void setState(State state, String str) {
        State state2;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled;
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, -6873410057142191118L, 0, null, String.valueOf(this), String.valueOf(this.mState), String.valueOf(state), str);
        }
        if (state == this.mState) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 4437231720834282527L, 0, null, String.valueOf(state));
                return;
            }
            return;
        }
        this.mState = state;
        TaskFragment taskFragment = getTaskFragment();
        State state3 = State.RESUMED;
        if (taskFragment != null) {
            TaskFragment taskFragment2 = getTaskFragment();
            if (this == taskFragment2.mResumedActivity && state != state3) {
                taskFragment2.setResumedActivity(null, str.concat(" - onActivityStateChanged"));
            }
            if (state == state3) {
                taskFragment2.setResumedActivity(this, str.concat(" - onActivityStateChanged"));
                taskFragment2.mTaskSupervisor.mRecentTasks.add(this.task);
            }
            WindowProcessController organizerProcessIfDifferent = taskFragment2.getOrganizerProcessIfDifferent(this);
            if (organizerProcessIfDifferent != null) {
                taskFragment2.mTaskSupervisor.onProcessActivityStateChanged(organizerProcessIfDifferent, false);
                organizerProcessIfDifferent.updateProcessInfo(false, true, true, false);
            }
        }
        if (state == State.STOPPING) {
            Task rootTask = getRootTask();
            if (!(rootTask != null ? rootTask.shouldSleepActivities() : this.mAtmService.mSleeping) && getParent() == null) {
                Slog.w("WindowManager", "Attempted to notify stopping on non-existing app token: " + this.token);
                return;
            }
        }
        this.mVisibleForServiceConnection = this.mVisibleRequested || (state2 = this.mState) == state3 || state2 == State.PAUSING;
        WindowProcessController windowProcessController = this.app;
        if (windowProcessController != null) {
            this.mTaskSupervisor.onProcessActivityStateChanged(windowProcessController, false);
        }
        int ordinal = state.ordinal();
        if (ordinal != 1) {
            if (ordinal != 2) {
                if (ordinal == 4) {
                    this.mAtmService.updateBatteryStats(false, this);
                    this.mAtmService.updateActivityUsageStats(2, this);
                    return;
                }
                if (ordinal == 6) {
                    this.mAtmService.updateActivityUsageStats(23, this);
                    DisplayContent displayContent = this.mDisplayContent;
                    if (displayContent != null) {
                        displayContent.mUnknownAppVisibilityController.appRemovedOrHidden(this);
                        return;
                    }
                    return;
                }
                if (ordinal != 8) {
                    if (ordinal != 9) {
                        return;
                    }
                    if (this.app != null && (this.mVisible || this.mVisibleRequested)) {
                        this.mAtmService.updateBatteryStats(false, this);
                    }
                    this.mAtmService.updateActivityUsageStats(24, this);
                }
                WindowProcessController windowProcessController2 = this.app;
                if (windowProcessController2 == null || windowProcessController2.mHasActivities) {
                    return;
                }
                this.app.updateProcessInfo(true, false, true, false);
                return;
            }
            this.mAtmService.updateBatteryStats(true, this);
            this.mAtmService.updateActivityUsageStats(1, this);
        }
        WindowProcessController windowProcessController3 = this.app;
        if (windowProcessController3 != null) {
            windowProcessController3.updateProcessInfo(false, true, true, true);
        }
        this.mAtmService.mH.post(new ActivityRecord$$ExternalSyntheticLambda7(1, this));
    }

    @Override // com.android.server.wm.WindowContainer
    public final void setSurfaceControl(SurfaceControl surfaceControl) {
        super.setSurfaceControl(surfaceControl);
        if (surfaceControl != null) {
            this.mLastDropInputMode = 0;
            updateUntrustedEmbeddingInputProtection();
        }
    }

    public final void setTaskDescription(ActivityManager.TaskDescription taskDescription) {
        Bitmap icon;
        if (taskDescription.getIconFilename() == null && (icon = taskDescription.getIcon()) != null) {
            String absolutePath = new File(new File(Environment.getDataSystemCeDirectory(this.task.mUserId), "recent_images"), String.valueOf(this.task.mTaskId) + "_activity_icon_" + this.createTime + ".png").getAbsolutePath();
            TaskPersister taskPersister = this.mAtmService.mRecentTasks.mTaskPersister;
            taskPersister.getClass();
            taskPersister.mPersisterQueue.updateLastOrAddItem(new TaskPersister.ImageWriteQueueItem(icon, absolutePath));
            taskDescription.setIconFilename(absolutePath);
        }
        this.taskDescription = taskDescription;
        this.task.updateTaskDescription$1();
    }

    public final void setTaskToAffiliateWith(Task task) {
        int i = this.launchMode;
        if (i == 3 || i == 2) {
            return;
        }
        Task task2 = this.task;
        Task task3 = task2.mPrevAffiliate;
        if (task3 != null) {
            task3.setNextAffiliate(task2.mNextAffiliate);
        }
        Task task4 = task2.mNextAffiliate;
        if (task4 != null) {
            task4.setPrevAffiliate(task2.mPrevAffiliate);
        }
        task2.setPrevAffiliate(null);
        task2.setNextAffiliate(null);
        task2.mAffiliatedTaskId = task.mAffiliatedTaskId;
        while (true) {
            Task task5 = task.mNextAffiliate;
            if (task5 == null) {
                break;
            }
            if (task5.mAffiliatedTaskId != task2.mAffiliatedTaskId) {
                StringBuilder sb = new StringBuilder("setTaskToAffiliateWith: nextRecents=");
                sb.append(task5);
                sb.append(" affilTaskId=");
                sb.append(task5.mAffiliatedTaskId);
                sb.append(" should be ");
                VaultKeeperService$$ExternalSyntheticOutline0.m(sb, task2.mAffiliatedTaskId, "ActivityTaskManager");
                if (task5.mPrevAffiliate == task) {
                    task5.setPrevAffiliate(null);
                }
                task.setNextAffiliate(null);
            } else {
                task = task5;
            }
        }
        task.setNextAffiliate(task2);
        task2.setPrevAffiliate(task);
        task2.setNextAffiliate(null);
    }

    public final void setVisibility(boolean z) {
        boolean z2;
        boolean z3;
        WindowState findFocusedWindow;
        ActivityRecord activityRecord;
        if (getParent() == null) {
            Slog.w("WindowManager", "Attempted to set visibility of non-existing app token: " + this.token);
            return;
        }
        if (z == this.mVisibleRequested && z == this.mVisible && z == this.mClientVisible && this.mTransitionController.isShellTransitionsEnabled()) {
            return;
        }
        if (z) {
            this.mDeferHidingClient = false;
        }
        boolean z4 = this.mDeferHidingClient;
        AppTransition appTransition = getDisplayContent().mAppTransition;
        if (z || this.mVisibleRequested) {
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled;
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -3452055378690362514L, 972, null, String.valueOf(this.token), Boolean.valueOf(z), String.valueOf(appTransition), Boolean.valueOf(this.mVisible), Boolean.valueOf(this.mVisibleRequested), String.valueOf(Debug.getCallers(6)));
            }
            if (this.mTransitionController.isShellTransitionsEnabled()) {
                z2 = this.mTransitionController.isCollecting();
                if (z2) {
                    this.mTransitionController.collect(this);
                    z3 = false;
                } else {
                    z3 = this.mTransitionController.inFinishingTransition(this);
                    if (!z3) {
                        if (z) {
                            if (!this.mDisplayContent.mSleeping || canShowWhenLocked()) {
                                TransitionController transitionController = this.mTransitionController;
                                String callers = Debug.getCallers(1, 1);
                                boolean z5 = !transitionController.mPlayingTransitions.isEmpty();
                                StringBuilder sb = new StringBuilder("Set visible without transition ");
                                sb.append(this);
                                sb.append(" playing=");
                                sb.append(z5);
                                sb.append(" caller=");
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(sb, callers, "TransitionController");
                                if (z5) {
                                    transitionController.mStateValidators.add(new TransitionController$$ExternalSyntheticLambda4(1, this));
                                } else {
                                    WindowContainer.enforceSurfaceVisible(this);
                                }
                            }
                        } else if (!this.mDisplayContent.mSleeping) {
                            Slog.w("ActivityTaskManager", "Set invisible without transition " + this);
                        }
                    }
                }
            } else {
                z2 = false;
                z3 = false;
            }
            onChildVisibilityRequested(z);
            DisplayContent displayContent = getDisplayContent();
            displayContent.mOpeningApps.remove(this);
            displayContent.mClosingApps.remove(this);
            setVisibleRequested(z);
            this.mLastDeferHidingClient = z4;
            if (z) {
                if (!appTransition.isTransitionSet() && appTransition.isReady()) {
                    displayContent.mOpeningApps.add(this);
                }
                this.startingMoved = false;
                boolean z6 = this.mVisible;
                if (!z6 || this.mAppStopped) {
                    this.allDrawn = false;
                    this.mLastAllDrawn = false;
                    if (!z6 && !this.mClientVisible) {
                        forAllWindows((Consumer) new ActivityRecord$$ExternalSyntheticLambda3(5), true);
                    }
                }
                super.setClientVisible(true);
                requestUpdateWallpaperIfNeeded();
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[1]) {
                    ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 1728033820691545386L, 0, null, String.valueOf(this));
                }
                this.mAppStopped = false;
                WindowState findMainWindow = findMainWindow(false);
                if (findMainWindow == null || !findMainWindow.mWinAnimator.getShown()) {
                    this.task.forAllActivities(new ActivityRecord$$ExternalSyntheticLambda1(1, this));
                }
            } else if (this.startingMoved && !this.firstWindowDrawn && hasChild()) {
                setClientVisible(false);
            }
            if (z2) {
                if (z) {
                    this.mTransitionChangeFlags &= -32769;
                } else if (this.mTransitionController.inPlayingTransition(this)) {
                    this.mTransitionChangeFlags |= 32768;
                } else if (this.mTransitionController.inFinishingTransition(this)) {
                    this.mTransitionChangeFlags |= 294912;
                }
            } else if (z3) {
                this.mTransitionController.mValidateCommitVis.add(this);
            } else {
                if (!this.mTransitionController.isShellTransitionsEnabled() && ((this.mDisplayContent.mAppTransition.isTransitionSet() || (!isActivityTypeHome() && isAnimating(2, 8))) && (!this.mWaitForEnteringPinnedMode || this.mVisible != z))) {
                    if (okToAnimate(true, canTurnScreenOn() || this.mTaskSupervisor.mKeyguardController.isKeyguardGoingAway(this.mDisplayContent.mDisplayId))) {
                        if (z) {
                            this.mDisplayContent.mOpeningApps.add(this);
                            this.mEnteringAnimation = true;
                        } else if (this.mVisible) {
                            this.mDisplayContent.mClosingApps.add(this);
                            this.mEnteringAnimation = false;
                        }
                        DisplayContent displayContent2 = this.mDisplayContent;
                        if ((displayContent2.mAppTransition.mNextAppTransitionFlags & 32) != 0 && (findFocusedWindow = displayContent2.findFocusedWindow()) != null && (activityRecord = findFocusedWindow.mActivityRecord) != null) {
                            if (zArr[0]) {
                                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 5062176994575790703L, 0, null, String.valueOf(activityRecord));
                            }
                            this.mDisplayContent.mOpeningApps.add(activityRecord);
                        }
                    }
                }
                commitVisibility(z, true, false);
                updateReportedVisibilityLocked();
            }
        } else if (!z4 && this.mLastDeferHidingClient) {
            this.mLastDeferHidingClient = z4;
            if (!CoreRune.FW_SHELL_TRANSITION_BUG_FIX || !inTransition() || !okToAnimate(true, canTurnScreenOn())) {
                setClientVisible(false);
            }
        }
        this.mAtmService.mLayoutReasons |= 2;
        ActivityMetricsLogger activityMetricsLogger = this.mTaskSupervisor.mActivityMetricsLogger;
        if (activityMetricsLogger.getActiveTransitionInfo(this) != null && ((!isState(State.RESUMED) || !this.mDisplayContent.mSleeping) && (!isVisibleRequested() || this.finishing))) {
            activityMetricsLogger.scheduleCheckActivityToBeDrawn(this, 0L);
        }
        this.mTaskSupervisor.mAppVisibilitiesChangedSinceLastPause = true;
    }

    public final void setVisible(boolean z) {
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
    public final boolean setVisibleRequested(boolean z) {
        WindowState windowState;
        State state;
        boolean z2 = false;
        if (!super.setVisibleRequested(z)) {
            return false;
        }
        forAllWindows((Consumer) new WindowToken$$ExternalSyntheticLambda1(this, !z), true);
        this.mVisibleForServiceConnection = this.mVisibleRequested || (state = this.mState) == State.RESUMED || state == State.PAUSING;
        WindowProcessController windowProcessController = this.app;
        if (windowProcessController != null) {
            this.mTaskSupervisor.onProcessActivityStateChanged(windowProcessController, false);
        }
        logAppCompatState();
        if (!z) {
            InputTarget inputTarget = this.mDisplayContent.mImeInputTarget;
            if (inputTarget != null && inputTarget.getWindowState() != null && inputTarget.getWindowState().mActivityRecord == this && (windowState = this.mDisplayContent.mInputMethodWindow) != null && windowState.isVisible()) {
                z2 = true;
            }
            this.mLastImeShown = z2;
            this.mRelaunchStartTime = 0L;
            DisplayPolicy displayPolicy = this.mDisplayContent.mDisplayPolicy;
            if (displayPolicy.mRelaunchingSystemBarColorApps.remove(this) & displayPolicy.mRelaunchingSystemBarColorApps.isEmpty()) {
                displayPolicy.updateSystemBarAttributes();
            }
        }
        return true;
    }

    public boolean shouldAnimate() {
        RecentsAnimationController recentsAnimationController;
        Task task = this.task;
        return task == null || !(task.isOrganized() || ((recentsAnimationController = task.mWmService.mRecentsAnimationController) != null && recentsAnimationController.isAnimatingTask(task) && recentsAnimationController.mRequestDeferCancelUntilNextTransition));
    }

    public final boolean shouldBeResumed(ActivityRecord activityRecord) {
        Task task = this.task;
        return (task == null || !task.isFreeformStashed()) ? shouldMakeActive(activityRecord) && isFocusable() && getTaskFragment().getVisibility(activityRecord) == 0 && canResumeByCompat() : shouldMakeActive(activityRecord) && getTaskFragment().getVisibility(activityRecord) == 0 && canResumeByCompat();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001a, code lost:
    
        if ((r0 != r5 ? r0 : null) != null) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0024, code lost:
    
        if (showToCurrentUser() == false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0026, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0027, code lost:
    
        r5.visibleIgnoringKeyguard = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0029, code lost:
    
        if (r6 == false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0030, code lost:
    
        return shouldBeVisibleUnchecked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:?, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x001e, code lost:
    
        if (r5.mLaunchTaskBehind != false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldBeVisible(boolean r6) {
        /*
            r5 = this;
            com.android.server.wm.Task r0 = r5.task
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            r2 = 0
            boolean r3 = r0.shouldBeVisible(r2)
            if (r3 == 0) goto L1c
            com.android.server.wm.Task$$ExternalSyntheticLambda19 r3 = new com.android.server.wm.Task$$ExternalSyntheticLambda19
            r4 = 1
            r3.<init>(r4, r5)
            com.android.server.wm.ActivityRecord r0 = r0.getActivity(r3)
            if (r0 == r5) goto L1a
            r2 = r0
        L1a:
            if (r2 == 0) goto L20
        L1c:
            boolean r0 = r5.mLaunchTaskBehind
            if (r0 == 0) goto L27
        L20:
            boolean r0 = r5.showToCurrentUser()
            if (r0 == 0) goto L27
            r1 = 1
        L27:
            r5.visibleIgnoringKeyguard = r1
            if (r6 == 0) goto L2c
            goto L30
        L2c:
            boolean r1 = r5.shouldBeVisibleUnchecked()
        L30:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.shouldBeVisible(boolean):boolean");
    }

    public final boolean shouldBeVisibleUnchecked() {
        Task task;
        Task rootTask = getRootTask();
        if (rootTask == null || !this.visibleIgnoringKeyguard) {
            return false;
        }
        if (inPinnedWindowingMode() && rootTask.isForceHidden()) {
            return false;
        }
        if ((getOrganizedTaskFragment() == null ? false : !r0.isAllowedToEmbedActivityInTrustedMode(r0.mTaskFragmentOrganizerUid, this)) && (task = this.task) != null && task.getActivity(new ActivityRecord$$ExternalSyntheticLambda1(4, this), this, false, false) != null) {
            return false;
        }
        if (!this.mDisplayContent.mSleeping) {
            return this.mTaskSupervisor.mKeyguardController.checkKeyguardVisibility(this);
        }
        int displayId = getDisplayId();
        if (displayId == 2 && this.mAtmService.mDexController.getDexModeLocked() == 2) {
            return false;
        }
        if (this.mAtmService.mKeyguardController.getDisplayState(displayId).mWakeAndUnlock && getDisplayContent() != null && getDisplayContent().isOnTop()) {
            return true;
        }
        return canTurnScreenOn();
    }

    public final boolean shouldCreateAppCompatDisplayInsets() {
        Task task;
        Task task2;
        AppCompatAspectRatioOverrides appCompatAspectRatioOverrides = this.mAppCompatController.mAppCompatOverrides.mAppCompatAspectRatioOverrides;
        if (appCompatAspectRatioOverrides.shouldApplyUserFullscreenOverride() || appCompatAspectRatioOverrides.isSystemOverrideToFullscreenEnabled()) {
            return false;
        }
        int supportsSizeChanges = supportsSizeChanges();
        if (supportsSizeChanges == 1) {
            return true;
        }
        if (supportsSizeChanges == 2 || supportsSizeChanges == 3 || this.mPopOverState.mIsActivated || isDexMode() || ((task = this.task) != null && task.isDexMode())) {
            return false;
        }
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null && displayContent.isRemoteAppDisplay()) {
            return false;
        }
        DisplayContent displayContent2 = this.mDisplayContent;
        if ((displayContent2 != null && displayContent2.isAppCastingDisplay()) || !MultiTaskingAppCompatController.inAllowedWindowingMode(this)) {
            return false;
        }
        this.mAtmService.mMultiTaskingAppCompatController.mSizeCompatModePolicy.getClass();
        char c = (CoreRune.MT_APP_COMPAT_ROTATION_COMPAT_MODE && this.mAtmService.mMultiTaskingAppCompatController.mOrientationPolicy.shouldCreateAppCompatDisplayInsetsForRotationCompat(this)) ? (char) 1 : (!(CoreRune.MT_APP_COMPAT_ASPECT_RATIO_POLICY && this.mAppCompatController.mAppCompatAspectRatioPolicy.isUserOrSystemMinAspectRatioApplied()) && (!CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY || (task2 = this.task) == null || task2.mRespectOrientationRequestOverride == -1)) ? (char) 0 : (char) 3;
        if (c == 1) {
            return true;
        }
        if (c == 2 || c == 3) {
            return false;
        }
        TaskDisplayArea taskDisplayArea = getTaskDisplayArea();
        if (inMultiWindowMode() || (taskDisplayArea != null && taskDisplayArea.inFreeformWindowingMode())) {
            Task task3 = this.task;
            ActivityRecord rootActivity = task3 != null ? task3.getRootActivity(true, false) : null;
            if (rootActivity != null && rootActivity != this && !rootActivity.shouldCreateAppCompatDisplayInsets()) {
                return false;
            }
        }
        return !isResizeable(true) && !(!this.info.isFixedOrientation() && getMaxAspectRatio() == FullScreenMagnificationGestureHandler.MAX_SCALE && getMinAspectRatio() == FullScreenMagnificationGestureHandler.MAX_SCALE) && isActivityTypeStandardOrUndefined();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final boolean shouldDeferAnimationFinish(Runnable runnable) {
        AnimatingActivityRegistry animatingActivityRegistry = this.mAnimatingActivityRegistry;
        if (animatingActivityRegistry != null && animatingActivityRegistry.mAnimatingActivities.remove(this)) {
            if (!animatingActivityRegistry.mAnimatingActivities.isEmpty()) {
                animatingActivityRegistry.mFinishedTokens.put(this, runnable);
                return true;
            }
            animatingActivityRegistry.endDeferringFinished();
        }
        return false;
    }

    public boolean shouldIgnoreOrientationRequests() {
        return this.mAppActivityEmbeddingSplitsEnabled && ActivityInfo.isFixedOrientationPortrait(getOverrideOrientation()) && !this.task.inMultiWindowMode() && this.task.getConfiguration().smallestScreenWidthDp >= 600;
    }

    public boolean shouldMakeActive(ActivityRecord activityRecord) {
        if (!isState$1(State.RESUMED, State.PAUSED, State.STOPPED, State.STOPPING) || getRootTask().mTranslucentActivityWaiting != null || this == activityRecord || this.mTaskSupervisor.mDeferResumeCount != 0 || this.mLaunchTaskBehind) {
            return false;
        }
        if (this.task.hasChild(this)) {
            return getTaskFragment().topRunningActivity(false) == this;
        }
        throw new IllegalStateException("Activity not found in its task");
    }

    public boolean shouldPauseActivity(ActivityRecord activityRecord) {
        Task task = this.task;
        return ((task != null && task.isFreeformStashed() && shouldMakeActive(activityRecord)) || !shouldMakeActive(activityRecord) || isFocusable() || isState(State.PAUSING, State.PAUSED) || this.results != null) ? false : true;
    }

    public boolean shouldResumeActivity(ActivityRecord activityRecord) {
        return shouldBeResumed(activityRecord) && !isState(State.RESUMED);
    }

    public final boolean shouldSendCompatFakeFocus() {
        AppCompatFocusOverrides appCompatFocusOverrides = this.mAppCompatController.mAppCompatOverrides.mAppCompatFocusOverrides;
        ActivityRecord activityRecord = appCompatFocusOverrides.mActivityRecord;
        return appCompatFocusOverrides.mFakeFocusOptProp.shouldEnableWithOverrideAndProperty(activityRecord.info.isChangeEnabled(263259275L)) && activityRecord.inMultiWindowMode() && !activityRecord.inPinnedWindowingMode() && !activityRecord.inFreeformWindowingMode();
    }

    /* JADX WARN: Code restructure failed: missing block: B:132:0x0176, code lost:
    
        if (r18 != false) goto L112;
     */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0193 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01aa A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void showStartingWindow(com.android.server.wm.ActivityRecord r14, boolean r15, boolean r16, boolean r17, boolean r18, com.android.server.wm.ActivityRecord r19, android.app.ActivityOptions r20) {
        /*
            Method dump skipped, instructions count: 505
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.showStartingWindow(com.android.server.wm.ActivityRecord, boolean, boolean, boolean, boolean, com.android.server.wm.ActivityRecord, android.app.ActivityOptions):void");
    }

    public final void showStartingWindow(boolean z) {
        showStartingWindow(this.task.getActivity(new ActivityRecord$$ExternalSyntheticLambda1(0, this)), false, z, isProcessRunning(), false, null, null);
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean showSurfaceOnCreation() {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean showToCurrentUser() {
        if (!this.mShowForAllUsers) {
            WindowManagerService windowManagerService = this.mWmService;
            if (!windowManagerService.mUmInternal.isUserVisible(this.mUserId)) {
                return false;
            }
        }
        return true;
    }

    public final void startFreezingScreen(int i) {
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            return;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[2]) {
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_ORIENTATION, 3713860954819212080L, 252, null, String.valueOf(this.token), Boolean.valueOf(this.mVisible), Boolean.valueOf(this.mFreezingScreen), Boolean.valueOf(this.mVisibleRequested), String.valueOf(new RuntimeException().fillInStackTrace()));
        }
        if (this.mVisibleRequested) {
            boolean z = i != -1;
            if (!this.mFreezingScreen) {
                this.mFreezingScreen = true;
                WindowManagerService windowManagerService = this.mWmService;
                if (!windowManagerService.mAppFreezeListeners.contains(this)) {
                    windowManagerService.mAppFreezeListeners.add(this);
                }
                WindowManagerService windowManagerService2 = this.mWmService;
                int i2 = windowManagerService2.mAppsFreezingScreen + 1;
                windowManagerService2.mAppsFreezingScreen = i2;
                if (i2 == 1) {
                    if (z) {
                        this.mDisplayContent.mDisplayRotation.cancelSeamlessRotation();
                    }
                    this.mWmService.startFreezingDisplay(0, 0, i, this.mDisplayContent);
                    this.mWmService.mH.removeMessages(17);
                    this.mWmService.mH.sendEmptyMessageDelayed(17, 2000L);
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

    public void startRelaunching() {
        if (this.mPendingRelaunchCount == 0) {
            this.mRelaunchStartTime = SystemClock.elapsedRealtime();
            if (this.mVisibleRequested) {
                DisplayPolicy displayPolicy = this.mDisplayContent.mDisplayPolicy;
                if (displayPolicy.mSystemBarColorApps.contains(this) && !hasStartingWindow()) {
                    displayPolicy.mRelaunchingSystemBarColorApps.add(this);
                }
            }
        }
        this.allDrawn = false;
        this.mLastAllDrawn = false;
        this.mPendingRelaunchCount++;
    }

    public final void stopFreezingScreen(boolean z) {
        if (this.mFreezingScreen) {
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled;
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 7696002120820208745L, 12, null, String.valueOf(this), Boolean.TRUE);
            }
            int size = this.mChildren.size();
            boolean z2 = false;
            for (int i = 0; i < size; i++) {
                z2 |= ((WindowState) this.mChildren.get(i)).onStopFreezingScreen();
            }
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -8387262166329116492L, 0, null, String.valueOf(this));
            }
            this.mFreezingScreen = false;
            this.mWmService.mAppFreezeListeners.remove(this);
            WindowManagerService windowManagerService = this.mWmService;
            windowManagerService.mAppsFreezingScreen--;
            windowManagerService.mLastFinishedFreezeSource = this;
            if (z) {
                if (z2) {
                    windowManagerService.mWindowPlacerLocked.performSurfacePlacement(false);
                }
                this.mWmService.stopFreezingDisplayLocked();
            }
        }
    }

    public final void stopIfPossible() {
        if (this.finishing) {
            Slog.e("ActivityTaskManager", "Request to stop a finishing activity: " + this);
            destroyIfPossible("stopIfPossible-finishing");
            return;
        }
        boolean z = ((this.intent.getFlags() & 1073741824) == 0 && (this.info.flags & 128) == 0) ? false : true;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled;
        if (z) {
            if (!this.task.shouldSleepActivities()) {
                if (zArr[0]) {
                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_STATES, 7498807658620137882L, 0, null, String.valueOf(this));
                }
                if (finishIfPossible("stop-no-history", false) != 0) {
                    resumeKeyDispatchingLocked();
                    return;
                }
            } else if (zArr[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_STATES, 3207149655622038378L, 0, null, String.valueOf(this));
            }
        }
        if (attachedToProcess()) {
            resumeKeyDispatchingLocked();
            try {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, -2530718588485487045L, 0, null, String.valueOf(this));
                }
                setState(State.STOPPING, "stopIfPossible");
                EventLog.writeEvent(30048, Integer.valueOf(this.mUserId), Integer.valueOf(System.identityHashCode(this)), this.shortComponentName);
                this.mAtmService.mLifecycleManager.scheduleTransactionItem(this.app.mThread, StopActivityItem.obtain(this.token));
                this.mAtmService.mH.postDelayed(this.mStopTimeoutRunnable, 11000L);
            } catch (Exception e) {
                Slog.w("ActivityTaskManager", "Exception thrown during pause", e);
                this.mAppStopped = true;
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, -8424334454318351870L, 0, null, String.valueOf(this));
                }
                setState(State.STOPPED, "stopIfPossible");
            }
        }
    }

    public final boolean supportsFreeform() {
        return supportsFreeformInDisplayArea((TaskDisplayArea) super.getDisplayArea());
    }

    public final boolean supportsFreeformInDisplayArea(TaskDisplayArea taskDisplayArea) {
        return this.mAtmService.mSupportsFreeformWindowManagement && supportsMultiWindowInDisplayArea(taskDisplayArea);
    }

    public final boolean supportsMultiWindowInDisplayArea(TaskDisplayArea taskDisplayArea) {
        if (isActivityTypeHome()) {
            return false;
        }
        ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
        if (activityTaskManagerService.mSupportsMultiWindow && taskDisplayArea != null) {
            return activityTaskManagerService.mMwSupportPolicyController.supportsMultiWindowInDisplayArea(taskDisplayArea, this.info.resizeMode, isResizeable(true), this.mIgnoreDevSettingForNonResizable);
        }
        return false;
    }

    public final boolean supportsPictureInPicture() {
        return this.mAtmService.mSupportsPictureInPicture && isActivityTypeStandardOrUndefined() && this.info.supportsPictureInPicture();
    }

    public final int supportsSizeChanges() {
        AppCompatResizeOverrides appCompatResizeOverrides = this.mAppCompatController.mAppCompatOverrides.mAppCompatResizeOverrides;
        if (appCompatResizeOverrides.mAllowForceResizeOverrideOptProp.shouldEnableWithOptInOverrideAndOptOutProperty(appCompatResizeOverrides.mActivityRecord.info.isChangeEnabled(181136395L))) {
            return 1;
        }
        if (this.info.supportsSizeChanges) {
            return 2;
        }
        AppCompatResizeOverrides appCompatResizeOverrides2 = this.mAppCompatController.mAppCompatOverrides.mAppCompatResizeOverrides;
        return appCompatResizeOverrides2.mAllowForceResizeOverrideOptProp.shouldEnableWithOptInOverrideAndOptOutProperty(appCompatResizeOverrides2.mActivityRecord.info.isChangeEnabled(174042936L)) ? 3 : 0;
    }

    @Override // com.android.server.wm.WindowToken
    public final String toString() {
        if (this.stringName != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.stringName);
            sb.append(" t");
            Task task = this.task;
            sb.append(task == null ? -1 : task.mTaskId);
            sb.append(this.finishing ? " f}" : "");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.mIsExiting ? " isExiting" : "", "}");
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "ActivityRecord{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(" u");
        m.append(this.mUserId);
        m.append(' ');
        m.append(this.intent.getComponent().flattenToShortString());
        String sb2 = m.toString();
        this.stringName = sb2;
        return sb2;
    }

    public final boolean transferStartingWindow(ActivityRecord activityRecord) {
        if (this.mPopOverState.mIsActivated != activityRecord.mPopOverState.mIsActivated) {
            return false;
        }
        WindowState windowState = activityRecord.mStartingWindow;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_STARTING_WINDOW_enabled;
        if (windowState == null || activityRecord.mStartingSurface == null) {
            if (activityRecord.mStartingData == null) {
                return false;
            }
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 8639603536400037285L, 0, "Moving pending starting from %s to %s", String.valueOf(activityRecord), String.valueOf(this));
            }
            this.mStartingData = activityRecord.mStartingData;
            activityRecord.mStartingData = null;
            activityRecord.startingMoved = true;
            scheduleAddStartingWindow();
            return true;
        }
        if (windowState.getParent() == null) {
            return false;
        }
        if (activityRecord.getRequestedConfigurationOrientation() != getRequestedConfigurationOrientation() && (!CoreRune.FW_SHELL_TRANSITION_BUG_FIX || activityRecord.getRequestedConfigurationOrientation() != 0)) {
            return false;
        }
        if (activityRecord.mVisible) {
            this.mDisplayContent.mSkipAppTransitionAnimation = true;
        }
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 8361394136152947990L, 0, "Moving existing starting %s from %s to %s", String.valueOf(windowState), String.valueOf(activityRecord), String.valueOf(this));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (activityRecord.hasFixedRotationTransform()) {
                this.mDisplayContent.handleTopActivityLaunchingInDifferentOrientation(this, this, false);
            } else if (!this.mWmService.mFlags.mRespectNonTopVisibleFixedOrientation && this.mDisplayContent.rotationForActivityInDifferentOrientation(this) != -1) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
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
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -3450064502566932331L, 0, null, String.valueOf(windowState), String.valueOf(activityRecord));
            }
            this.mTransitionController.collect(windowState);
            windowState.reparent(this, Integer.MAX_VALUE);
            windowState.mFrozenInsetsState = null;
            if (activityRecord.allDrawn) {
                this.allDrawn = true;
            }
            if (activityRecord.firstWindowDrawn) {
                this.firstWindowDrawn = true;
            }
            if (activityRecord.mVisible) {
                setVisible(true);
                setVisibleRequested(true);
                this.mVisibleSetFromTransferredStartingWindow = true;
            }
            setClientVisible(activityRecord.mClientVisible);
            if (activityRecord.isAnimating()) {
                transferAnimation(activityRecord);
                this.mTransitionChangeFlags |= 8;
            } else if (this.mTransitionController.getTransitionPlayer() != null) {
                this.mTransitionChangeFlags |= 8;
            }
            activityRecord.postWindowRemoveStartingWindowCleanup(windowState);
            activityRecord.mVisibleSetFromTransferredStartingWindow = false;
            this.mWmService.updateFocusedWindowLocked(3, true);
            getDisplayContent().setLayoutNeeded();
            this.mWmService.mWindowPlacerLocked.performSurfacePlacement(false);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void updateLetterboxSurfaceIfNeeded(WindowState windowState) {
        AppCompatLetterboxPolicy appCompatLetterboxPolicy = this.mAppCompatController.mAppCompatLetterboxPolicy;
        ActivityRecord activityRecord = appCompatLetterboxPolicy.mActivityRecord;
        appCompatLetterboxPolicy.mLetterboxPolicyState.updateLetterboxSurfaceIfNeeded(windowState, activityRecord.getSyncTransaction(), activityRecord.getPendingTransaction());
    }

    public final void updateOptionsLocked(ActivityOptions activityOptions) {
        if (activityOptions != null) {
            ActivityOptions activityOptions2 = this.mPendingOptions;
            if (activityOptions2 != null) {
                activityOptions2.abort();
            }
            this.mLaunchedFromBubble = activityOptions.getLaunchedFromBubble();
            this.mPendingOptions = activityOptions;
            if (activityOptions.getAnimationType() == 13) {
                this.mPendingRemoteAnimation = activityOptions.getRemoteAnimationAdapter();
            }
            this.mPendingRemoteTransition = activityOptions.getRemoteTransition();
        }
    }

    public final void updatePictureInPictureMode(Rect rect) {
        Task task = this.task;
        if (task == null || task.getRootTask() == null || !attachedToProcess()) {
            return;
        }
        boolean z = inPinnedWindowingMode() && rect != null;
        if (z == this.mLastReportedPictureInPictureMode) {
            return;
        }
        this.mLastReportedPictureInPictureMode = z;
        this.mLastReportedMultiWindowMode = z;
        ensureActivityConfiguration(true);
        if (z && findMainWindow(true) == null && this.task.topRunningActivity(false) == this) {
            EventLog.writeEvent(1397638484, "265293293", -1, "");
            removeImmediately();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x04dd  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0502  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x0525  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0271  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x013c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateReportedConfigurationAndSend() {
        /*
            Method dump skipped, instructions count: 1348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityRecord.updateReportedConfigurationAndSend():boolean");
    }

    public final void updateReportedVisibilityLocked() {
        int i;
        WindowContainerToken windowContainerToken;
        ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot;
        int i2;
        ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot2;
        int size = this.mChildren.size();
        WindowState.UpdateReportedVisibilityResults updateReportedVisibilityResults = this.mReportedVisibilityResults;
        updateReportedVisibilityResults.numInteresting = 0;
        updateReportedVisibilityResults.numVisible = 0;
        updateReportedVisibilityResults.numDrawn = 0;
        updateReportedVisibilityResults.nowGone = true;
        for (int i3 = 0; i3 < size; i3++) {
            ((WindowState) this.mChildren.get(i3)).updateReportedVisibility(this.mReportedVisibilityResults);
        }
        WindowState.UpdateReportedVisibilityResults updateReportedVisibilityResults2 = this.mReportedVisibilityResults;
        int i4 = updateReportedVisibilityResults2.numInteresting;
        int i5 = updateReportedVisibilityResults2.numVisible;
        int i6 = updateReportedVisibilityResults2.numDrawn;
        boolean z = updateReportedVisibilityResults2.nowGone;
        boolean z2 = i4 > 0 && i6 >= i4;
        boolean z3 = i4 > 0 && i5 >= i4 && this.mVisible;
        if (!z) {
            if (!z2) {
                z2 = this.mReportedDrawn;
            }
            if (!z3) {
                z3 = this.reportedVisible;
            }
        }
        boolean z4 = z3;
        boolean z5 = z2;
        if (z5 != this.mReportedDrawn) {
            if (z5) {
                final ActivityMetricsLogger activityMetricsLogger = this.mTaskSupervisor.mActivityMetricsLogger;
                activityMetricsLogger.getClass();
                final long uptimeNanos = SystemClock.uptimeNanos();
                final ActivityMetricsLogger.TransitionInfo activeTransitionInfo = activityMetricsLogger.getActiveTransitionInfo(this);
                if (activeTransitionInfo == null || activeTransitionInfo.mIsDrawn) {
                    i = -1;
                    windowContainerToken = null;
                    transitionInfoSnapshot = null;
                } else {
                    activeTransitionInfo.mWindowsDrawnDelayMs = (int) TimeUnit.NANOSECONDS.toMillis(uptimeNanos - activeTransitionInfo.mLaunchingState.mStartUptimeNs);
                    activeTransitionInfo.mIsDrawn = true;
                    ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot3 = new ActivityMetricsLogger.TransitionInfoSnapshot(activeTransitionInfo, activeTransitionInfo.mLastLaunchedActivity, -1);
                    if (activeTransitionInfo.mLoggedTransitionStarting || !(this.mDisplayContent.mOpeningApps.contains(this) || this.mTransitionController.isCollecting(this))) {
                        transitionInfoSnapshot2 = transitionInfoSnapshot3;
                        activityMetricsLogger.done(false, activeTransitionInfo, "notifyWindowsDrawn", uptimeNanos);
                    } else {
                        transitionInfoSnapshot2 = transitionInfoSnapshot3;
                    }
                    if (android.app.Flags.appStartInfoTimestamps()) {
                        i = -1;
                        windowContainerToken = null;
                        activityMetricsLogger.mLoggerHandler.post(new Runnable() { // from class: com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                ActivityMetricsLogger activityMetricsLogger2 = ActivityMetricsLogger.this;
                                long j = uptimeNanos;
                                ActivityRecord activityRecord = this;
                                activityMetricsLogger2.mSupervisor.mService.mWindowManager.mAmInternal.addStartInfoTimestamp(4, j, activityRecord.getUid(), activityRecord.getPid(), activeTransitionInfo.mLastLaunchedActivity.mUserId);
                            }
                        });
                    } else {
                        i = -1;
                        windowContainerToken = null;
                    }
                    transitionInfoSnapshot = transitionInfoSnapshot2;
                }
                boolean z6 = transitionInfoSnapshot != null;
                int i7 = z6 ? transitionInfoSnapshot.windowsDrawnDelayMs : i;
                if (z6) {
                    int i8 = transitionInfoSnapshot.type;
                    i2 = i8 != 7 ? i8 != 8 ? i8 != 9 ? i : transitionInfoSnapshot.relaunched ? 4 : 3 : 2 : 1;
                } else {
                    i2 = 0;
                }
                if (SemGateConfig.isGateEnabled()) {
                    Log.i("GATE", "<GATE-M>APP_FULLY_LOADED_" + this.packageName + "</GATE-M>");
                    if ("com.android.vending/.AssetBrowserActivity".equals(this.shortComponentName) || "com.android.vending/com.google.android.finsky.activities.TosActivity".equals(this.shortComponentName)) {
                        Log.i("GATE", "<GATE-M> MARKET_LAUNCHED </GATE-M>");
                    } else {
                        Log.i("GATE", "<GATE-M> APP_OPENED </GATE-M>");
                    }
                }
                if (z6 || this == ((TaskDisplayArea) super.getDisplayArea()).topRunningActivity(false)) {
                    this.mTaskSupervisor.reportActivityLaunched(false, this, i7, i2);
                }
                this.launchTickTime = 0L;
                Task rootTask = getRootTask();
                if (rootTask != null) {
                    rootTask.forAllActivities(new Task$$ExternalSyntheticLambda6(5));
                }
                Task task = this.task;
                if (task != null && !task.mHasBeenVisible) {
                    if (inTransition()) {
                        this.task.setDeferTaskAppear(true);
                    }
                    this.task.setHasBeenVisible();
                }
                this.mLaunchRootTask = windowContainerToken;
            }
            this.mReportedDrawn = z5;
        }
        if (z4 != this.reportedVisible) {
            this.reportedVisible = z4;
            if (!z4) {
                this.nowVisible = false;
                return;
            }
            this.mTaskSupervisor.reportActivityLaunched(false, this, -1L, 0);
            if (!this.nowVisible) {
                this.nowVisible = true;
                this.lastVisibleTime = SystemClock.uptimeMillis();
                ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
                activityTaskManagerService.mH.post(new ActivityTaskManagerService$$ExternalSyntheticLambda14(1, activityTaskManagerService));
                this.mTaskSupervisor.scheduleProcessStoppingAndFinishingActivitiesIfNeeded();
                if (this.mImeInsetsFrozenUntilStartInput && getWindow(new ActivityRecord$$ExternalSyntheticLambda4(0)) == null) {
                    this.mImeInsetsFrozenUntilStartInput = false;
                }
            }
            ActivityManagerPerformance activityManagerPerformance = this.mAtmService.mAMBooster;
            if (activityManagerPerformance != null) {
                activityManagerPerformance.onActivityVisibleLocked(this);
            }
        }
    }

    public final void updateResolvedBoundsPosition(Configuration configuration) {
        if (!CoreRune.MT_SIZE_COMPAT_POLICY || this.mResolvedConfigChangeFlags == 0) {
            Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
            Rect bounds = resolvedOverrideConfiguration.windowConfiguration.getBounds();
            if (bounds.isEmpty()) {
                return;
            }
            AppCompatSizeCompatModePolicy appCompatSizeCompatModePolicy = this.mAppCompatController.mAppCompatSizeCompatModePolicy;
            Rect rect = appCompatSizeCompatModePolicy.hasSizeCompatBounds() ? appCompatSizeCompatModePolicy.mSizeCompatBounds : bounds;
            Rect rect2 = this.mResolveConfigHint.mParentAppBoundsOverride;
            Rect bounds2 = configuration.windowConfiguration.getBounds();
            float width = rect.width();
            float width2 = rect2.width();
            if (Flags.immersiveAppRepositioning() && !this.mResolveConfigHint.mUseOverrideInsetsForConfig) {
                boolean z = this.mWmService.mFlags.mInsetsDecoupledConfiguration;
            }
            Insets insets = Insets.NONE;
            AppCompatReachabilityOverrides appCompatReachabilityOverrides = this.mAppCompatController.mAppCompatOverrides.mAppCompatReachabilityOverrides;
            int i = 0;
            int max = (((float) bounds2.width()) == width || width > width2) ? 0 : Math.max(0, (((int) Math.ceil((((int) (width2 + insets.right)) - width) * appCompatReachabilityOverrides.getHorizontalPositionMultiplier(configuration))) - rect.left) + rect2.left);
            float height = rect2.height();
            float height2 = bounds2.height();
            float height3 = rect.height();
            if (height2 != height3 && height3 <= height) {
                boolean isDisplayFullScreenAndInPosture = appCompatReachabilityOverrides.mAppCompatDeviceStateQuery.isDisplayFullScreenAndInPosture(true);
                boolean isVerticalReachabilityEnabled = appCompatReachabilityOverrides.isVerticalReachabilityEnabled(configuration);
                AppCompatConfiguration appCompatConfiguration = appCompatReachabilityOverrides.mAppCompatConfiguration;
                i = Math.max(0, (((int) Math.ceil((((int) (height + insets.bottom)) - height3) * (isVerticalReachabilityEnabled ? appCompatConfiguration.getVerticalMultiplierForReachability(isDisplayFullScreenAndInPosture) : isDisplayFullScreenAndInPosture ? appCompatConfiguration.mLetterboxTabletopModePositionMultiplier : appCompatConfiguration.mLetterboxVerticalPositionMultiplier))) - rect.top) + rect2.top);
                int i2 = this.mAppCompatController.mAppCompatSizeCompatModePolicy.mViewportStableTop;
                if (i2 > 0 && i2 > i) {
                    MultiTaskingAppCompatSizeCompatModePolicy multiTaskingAppCompatSizeCompatModePolicy = this.mAtmService.mMultiTaskingAppCompatController.mSizeCompatModePolicy;
                    AppCompatDisplayInsets appCompatDisplayInsets = getAppCompatDisplayInsets();
                    multiTaskingAppCompatSizeCompatModePolicy.getClass();
                    if (appCompatDisplayInsets != null && CoreRune.MT_APP_COMPAT_ROTATION_COMPAT_MODE && appCompatDisplayInsets.mIsRotationCompatMode) {
                        i = i2;
                    }
                }
            }
            if (appCompatSizeCompatModePolicy.hasSizeCompatBounds()) {
                appCompatSizeCompatModePolicy.mSizeCompatBounds.offset(max, i);
                Rect rect3 = appCompatSizeCompatModePolicy.mSizeCompatBounds;
                AppCompatUtils.offsetBounds(resolvedOverrideConfiguration, rect3.left - bounds.left, rect3.top - bounds.top);
            } else {
                AppCompatUtils.offsetBounds(resolvedOverrideConfiguration, max, i);
            }
            if (resolvedOverrideConfiguration.windowConfiguration.getAppBounds().top == rect2.top) {
                resolvedOverrideConfiguration.windowConfiguration.getBounds().top = bounds2.top;
                if (appCompatSizeCompatModePolicy.hasSizeCompatBounds()) {
                    appCompatSizeCompatModePolicy.mSizeCompatBounds.top = bounds2.top;
                }
            }
            computeConfigByResolveHint(resolvedOverrideConfiguration, configuration);
            float f = appCompatSizeCompatModePolicy.mSizeCompatScale;
            if (f != 1.0f) {
                int i3 = bounds.left;
                int i4 = bounds.top;
                AppCompatUtils.offsetBounds(resolvedOverrideConfiguration, ((int) ((i3 / f) + 0.5f)) - i3, ((int) ((i4 / f) + 0.5f)) - i4);
            }
        }
    }

    public final void updateUntrustedEmbeddingInputProtection() {
        if (this.mSurfaceControl == null) {
            return;
        }
        if (this.mIsInputDroppedForAnimation) {
            setDropInputMode(1);
            return;
        }
        if (getOrganizedTaskFragment() == null ? false : !r0.isAllowedToEmbedActivityInTrustedMode(r0.mTaskFragmentOrganizerUid, this)) {
            setDropInputMode(2);
        } else {
            setDropInputMode(0);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean updateUseForceLayoutInUdcCutoutIfNeeded() {
        throw null;
    }

    public final boolean validateStartingWindowTheme(ActivityRecord activityRecord, String str, int i) {
        AttributeCache.Entry entry;
        int i2;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_STARTING_WINDOW_enabled;
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 338586566486930495L, 1, "Checking theme of starting window: 0x%x", Long.valueOf(i));
        }
        if (i == 0 || (entry = AttributeCache.instance().get(str, i, R.styleable.Window, this.mWmService.mCurrentUserId)) == null) {
            return false;
        }
        boolean z = entry.array.getBoolean(5, false);
        boolean z2 = entry.array.getBoolean(4, false);
        boolean z3 = entry.array.getBoolean(14, false);
        boolean z4 = entry.array.getBoolean(12, false);
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -2561793317091789573L, 0, "Translucent=%s Floating=%s ShowWallpaper=%s Disable=%s", String.valueOf(z), String.valueOf(z2), String.valueOf(z3), String.valueOf(z4));
        }
        if (z || z2) {
            return false;
        }
        if (z3 && getDisplayContent().mWallpaperController.mWallpaperTarget != null) {
            return false;
        }
        if (!z4 || (i2 = this.mLaunchSourceType) == 1 || i2 == 2 || i2 == 3) {
            return true;
        }
        if (activityRecord != null && activityRecord.getActivityType() == 1 && activityRecord.mTransferringSplashScreenState == 0) {
            if (activityRecord.mStartingData != null) {
                return true;
            }
            if (activityRecord.mStartingWindow != null && activityRecord.mStartingSurface != null) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void waitForSyncTransactionCommit(ArraySet arraySet) {
        super.waitForSyncTransactionCommit(arraySet);
        StartingData startingData = this.mStartingData;
        if (startingData != null) {
            startingData.mWaitForSyncTransactionCommit = true;
        }
    }

    public final boolean windowsAreFocusable(boolean z) {
        Task task;
        if (!z && this.mTargetSdk < 29) {
            ActivityRecord activityRecord = (ActivityRecord) this.mWmService.mRoot.mTopFocusedAppByProcess.get(Integer.valueOf(getPid()));
            if (activityRecord != null && activityRecord != this) {
                return false;
            }
        }
        return ((getWindowConfiguration().canReceiveKeys() && !this.mWaitForEnteringPinnedMode && ((task = this.task) == null || !task.isFreeformStashed())) || (this.info.flags & 262144) != 0) && isAttached();
    }

    @Override // com.android.server.wm.WindowContainer
    public final void writeIdentifierToProto(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, System.identityHashCode(this));
        protoOutputStream.write(1120986464258L, this.mUserId);
        protoOutputStream.write(1138166333443L, this.intent.getComponent().flattenToShortString());
        protoOutputStream.end(start);
    }
}
