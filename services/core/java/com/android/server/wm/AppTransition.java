package com.android.server.wm;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.EventLog;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.AppTransitionAnimationSpec;
import android.view.IAppTransitionAnimationSpecsFuture;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.WindowManager;
import android.view.animation.Animation;
import com.android.internal.R;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.protolog.common.LogLevel;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.am.BroadcastStats$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.wm.RemoteAnimationController;
import com.android.server.wm.SurfaceAnimator;
import com.android.server.wm.WindowManagerInternal;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppTransition implements DumpUtils.Dump {
    public static final ArrayList sFlagToString;
    public IRemoteCallback mAnimationFinishedCallback;
    public AppTransitionAnimationSpec mDefaultNextAppTransitionAnimationSpec;
    public final int mDefaultWindowAnimationStyleResId;
    public final DisplayContent mDisplayContent;
    public final Handler mHandler;
    public String mLastChangingApp;
    public String mLastClosingApp;
    public String mLastOpeningApp;
    public IAppTransitionAnimationSpecsFuture mNextAppTransitionAnimationsSpecsFuture;
    public boolean mNextAppTransitionAnimationsSpecsPending;
    public int mNextAppTransitionBackgroundColor;
    public IRemoteCallback mNextAppTransitionCallback;
    public int mNextAppTransitionEnter;
    public int mNextAppTransitionExit;
    public IRemoteCallback mNextAppTransitionFutureCallback;
    public boolean mNextAppTransitionIsSync;
    public boolean mNextAppTransitionOverrideRequested;
    public String mNextAppTransitionPackage;
    public boolean mNextAppTransitionScaleUp;
    public boolean mOverrideTaskTransition;
    public RemoteAnimationController mRemoteAnimationController;
    public final WindowManagerService mService;
    final TransitionAnimation mTransitionAnimation;
    public int mNextAppTransitionFlags = 0;
    public final ArrayList mNextAppTransitionRequests = new ArrayList();
    public int mLastUsedAppTransition = -1;
    public int mNextAppTransitionType = 0;
    public final SparseArray mNextAppTransitionAnimationsSpecs = new SparseArray();
    public final Rect mTmpRect = new Rect();
    public int mAppTransitionState = 0;
    public final ArrayList mListeners = new ArrayList();
    public final ExecutorService mDefaultExecutor = Executors.newSingleThreadExecutor();
    public final AppTransition$$ExternalSyntheticLambda2 mHandleAppTransitionTimeoutRunnable = new Runnable() { // from class: com.android.server.wm.AppTransition$$ExternalSyntheticLambda2
        @Override // java.lang.Runnable
        public final void run() {
            AppTransition appTransition = AppTransition.this;
            WindowManagerGlobalLock windowManagerGlobalLock = appTransition.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = appTransition.mDisplayContent;
                    if (displayContent == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    for (int i = 0; i < appTransition.mListeners.size(); i++) {
                        ((WindowManagerInternal.AppTransitionListener) appTransition.mListeners.get(i)).onAppTransitionTimeoutLocked();
                    }
                    if (appTransition.isTransitionSet() || !displayContent.mOpeningApps.isEmpty() || !displayContent.mClosingApps.isEmpty() || !displayContent.mChangingContainers.isEmpty()) {
                        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled[1]) {
                            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 5233255302148535928L, 349, null, Long.valueOf(displayContent.mDisplayId), Boolean.valueOf(displayContent.mAppTransition.isTransitionSet()), Long.valueOf(displayContent.mOpeningApps.size()), Long.valueOf(displayContent.mClosingApps.size()), Long.valueOf(displayContent.mChangingContainers.size()));
                        }
                        appTransition.mAppTransitionState = 3;
                        appTransition.updateBooster();
                        appTransition.mService.mWindowPlacerLocked.performSurfacePlacement(false);
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    };

    static {
        ArrayList arrayList = new ArrayList();
        sFlagToString = arrayList;
        arrayList.add(new Pair(1, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_TO_SHADE"));
        arrayList.add(new Pair(2, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_NO_ANIMATION"));
        arrayList.add(new Pair(4, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_WITH_WALLPAPER"));
        arrayList.add(new Pair(8, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_SUBTLE_ANIMATION"));
        arrayList.add(new Pair(512, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_TO_LAUNCHER_WITH_IN_WINDOW_ANIMATIONS"));
        arrayList.add(new Pair(16, "TRANSIT_FLAG_APP_CRASHED"));
        arrayList.add(new Pair(32, "TRANSIT_FLAG_OPEN_BEHIND"));
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.server.wm.AppTransition$$ExternalSyntheticLambda2] */
    public AppTransition(Context context, WindowManagerService windowManagerService, DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mHandler = new Handler(windowManagerService.mH.getLooper());
        this.mDisplayContent = displayContent;
        this.mTransitionAnimation = new TransitionAnimation(context, ProtoLogImpl_54989576.isEnabled(ProtoLogGroup.WM_DEBUG_ANIM, LogLevel.DEBUG), "WindowManager");
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(R.styleable.Window);
        this.mDefaultWindowAnimationStyleResId = obtainStyledAttributes.getResourceId(8, 0);
        obtainStyledAttributes.recycle();
    }

    public static String appTransitionOldToString(int i) {
        switch (i) {
            case -1:
                return "TRANSIT_OLD_UNSET";
            case 0:
                return "TRANSIT_OLD_NONE";
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 17:
            case 19:
            case 27:
            default:
                return BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "<UNKNOWN: ", ">");
            case 6:
                return "TRANSIT_OLD_ACTIVITY_OPEN";
            case 7:
                return "TRANSIT_OLD_ACTIVITY_CLOSE";
            case 8:
                return "TRANSIT_OLD_TASK_OPEN";
            case 9:
                return "TRANSIT_OLD_TASK_CLOSE";
            case 10:
                return "TRANSIT_OLD_TASK_TO_FRONT";
            case 11:
                return "TRANSIT_OLD_TASK_TO_BACK";
            case 12:
                return "TRANSIT_OLD_WALLPAPER_CLOSE";
            case 13:
                return "TRANSIT_OLD_WALLPAPER_OPEN";
            case 14:
                return "TRANSIT_OLD_WALLPAPER_INTRA_OPEN";
            case 15:
                return "TRANSIT_OLD_WALLPAPER_INTRA_CLOSE";
            case 16:
                return "TRANSIT_OLD_TASK_OPEN_BEHIND";
            case 18:
                return "TRANSIT_OLD_ACTIVITY_RELAUNCH";
            case 20:
                return "TRANSIT_OLD_KEYGUARD_GOING_AWAY";
            case 21:
                return "TRANSIT_OLD_KEYGUARD_GOING_AWAY_ON_WALLPAPER";
            case 22:
                return "TRANSIT_OLD_KEYGUARD_OCCLUDE";
            case 23:
                return "TRANSIT_OLD_KEYGUARD_UNOCCLUDE";
            case 24:
                return "TRANSIT_OLD_TRANSLUCENT_ACTIVITY_OPEN";
            case 25:
                return "TRANSIT_OLD_TRANSLUCENT_ACTIVITY_CLOSE";
            case 26:
                return "TRANSIT_OLD_CRASHING_ACTIVITY_CLOSE";
            case 28:
                return "TRANSIT_OLD_TASK_FRAGMENT_OPEN";
            case 29:
                return "TRANSIT_OLD_TASK_FRAGMENT_CLOSE";
            case 30:
                return "TRANSIT_OLD_TASK_FRAGMENT_CHANGE";
            case 31:
                return "TRANSIT_OLD_DREAM_ACTIVITY_OPEN";
            case 32:
                return "TRANSIT_OLD_DREAM_ACTIVITY_CLOSE";
            case 33:
                return "TRANSIT_OLD_KEYGUARD_OCCLUDE_BY_DREAM";
        }
    }

    public static boolean isKeyguardGoingAwayTransitOld(int i) {
        return i == 20 || i == 21;
    }

    public final boolean canOverridePendingAppTransition() {
        return isTransitionSet() && this.mNextAppTransitionType != 10;
    }

    public final boolean canSkipFirstFrame() {
        int i = this.mNextAppTransitionType;
        return (i == 1 || this.mNextAppTransitionOverrideRequested || i == 7 || i == 8 || this.mNextAppTransitionRequests.contains(7)) ? false : true;
    }

    public final void clear(boolean z) {
        this.mNextAppTransitionType = 0;
        this.mNextAppTransitionOverrideRequested = false;
        this.mNextAppTransitionAnimationsSpecs.clear();
        this.mRemoteAnimationController = null;
        this.mNextAppTransitionAnimationsSpecsFuture = null;
        this.mDefaultNextAppTransitionAnimationSpec = null;
        this.mAnimationFinishedCallback = null;
        this.mOverrideTaskTransition = false;
        this.mNextAppTransitionIsSync = false;
        if (z) {
            this.mNextAppTransitionPackage = null;
            this.mNextAppTransitionEnter = 0;
            this.mNextAppTransitionExit = 0;
            this.mNextAppTransitionBackgroundColor = 0;
        }
    }

    public final boolean containsTransitRequest(int i) {
        return this.mNextAppTransitionRequests.contains(Integer.valueOf(i));
    }

    public final void dump(PrintWriter printWriter, String str) {
        String str2;
        Rect rect;
        String str3;
        printWriter.print(str);
        printWriter.println(this);
        printWriter.print(str);
        printWriter.print("mAppTransitionState=");
        int i = this.mAppTransitionState;
        if (i == 0) {
            str2 = "APP_STATE_IDLE";
        } else if (i == 1) {
            str2 = "APP_STATE_READY";
        } else if (i == 2) {
            str2 = "APP_STATE_RUNNING";
        } else if (i != 3) {
            str2 = "unknown state=" + this.mAppTransitionState;
        } else {
            str2 = "APP_STATE_TIMEOUT";
        }
        printWriter.println(str2);
        if (this.mNextAppTransitionType != 0) {
            printWriter.print(str);
            printWriter.print("mNextAppTransitionType=");
            switch (this.mNextAppTransitionType) {
                case 0:
                    str3 = "NEXT_TRANSIT_TYPE_NONE";
                    break;
                case 1:
                    str3 = "NEXT_TRANSIT_TYPE_CUSTOM";
                    break;
                case 2:
                    str3 = "NEXT_TRANSIT_TYPE_SCALE_UP";
                    break;
                case 3:
                    str3 = "NEXT_TRANSIT_TYPE_THUMBNAIL_SCALE_UP";
                    break;
                case 4:
                    str3 = "NEXT_TRANSIT_TYPE_THUMBNAIL_SCALE_DOWN";
                    break;
                case 5:
                    str3 = "NEXT_TRANSIT_TYPE_THUMBNAIL_ASPECT_SCALE_UP";
                    break;
                case 6:
                    str3 = "NEXT_TRANSIT_TYPE_THUMBNAIL_ASPECT_SCALE_DOWN";
                    break;
                case 7:
                    str3 = "NEXT_TRANSIT_TYPE_CUSTOM_IN_PLACE";
                    break;
                case 8:
                default:
                    str3 = "unknown type=" + this.mNextAppTransitionType;
                    break;
                case 9:
                    str3 = "NEXT_TRANSIT_TYPE_OPEN_CROSS_PROFILE_APPS";
                    break;
            }
            printWriter.println(str3);
        }
        if (this.mNextAppTransitionOverrideRequested || this.mNextAppTransitionType == 1) {
            printWriter.print(str);
            printWriter.print("mNextAppTransitionPackage=");
            ProcessList$$ExternalSyntheticOutline0.m(printWriter, this.mNextAppTransitionPackage, str, "mNextAppTransitionEnter=0x");
            printWriter.print(Integer.toHexString(this.mNextAppTransitionEnter));
            printWriter.print(" mNextAppTransitionExit=0x");
            printWriter.println(Integer.toHexString(this.mNextAppTransitionExit));
            printWriter.print(" mNextAppTransitionBackgroundColor=0x");
            printWriter.println(Integer.toHexString(this.mNextAppTransitionBackgroundColor));
        }
        switch (this.mNextAppTransitionType) {
            case 2:
                Rect rect2 = this.mTmpRect;
                AppTransitionAnimationSpec appTransitionAnimationSpec = this.mDefaultNextAppTransitionAnimationSpec;
                if (appTransitionAnimationSpec == null || (rect = appTransitionAnimationSpec.rect) == null) {
                    Slog.e("WindowManager", "Starting rect for app requested, but none available", new Throwable());
                    rect2.setEmpty();
                } else {
                    rect2.set(rect);
                }
                printWriter.print(str);
                printWriter.print("mNextAppTransitionStartX=");
                printWriter.print(this.mTmpRect.left);
                printWriter.print(" mNextAppTransitionStartY=");
                BroadcastStats$$ExternalSyntheticOutline0.m(this.mTmpRect.top, printWriter, str, "mNextAppTransitionStartWidth=");
                printWriter.print(this.mTmpRect.width());
                printWriter.print(" mNextAppTransitionStartHeight=");
                printWriter.println(this.mTmpRect.height());
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                printWriter.print(str);
                printWriter.print("mDefaultNextAppTransitionAnimationSpec=");
                printWriter.println(this.mDefaultNextAppTransitionAnimationSpec);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionAnimationsSpecs=");
                printWriter.println(this.mNextAppTransitionAnimationsSpecs);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionScaleUp=");
                printWriter.println(this.mNextAppTransitionScaleUp);
                break;
            case 7:
                printWriter.print(str);
                printWriter.print("mNextAppTransitionPackage=");
                printWriter.println(this.mNextAppTransitionPackage);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionInPlace=0x");
                printWriter.print(Integer.toHexString(0));
                break;
        }
        if (this.mNextAppTransitionCallback != null) {
            printWriter.print(str);
            printWriter.print("mNextAppTransitionCallback=");
            printWriter.println(this.mNextAppTransitionCallback);
        }
        if (this.mLastUsedAppTransition != 0) {
            printWriter.print(str);
            printWriter.print("mLastUsedAppTransition=");
            printWriter.println(appTransitionOldToString(this.mLastUsedAppTransition));
            printWriter.print(str);
            printWriter.print("mLastOpeningApp=");
            ProcessList$$ExternalSyntheticOutline0.m(printWriter, this.mLastOpeningApp, str, "mLastClosingApp=");
            ProcessList$$ExternalSyntheticOutline0.m(printWriter, this.mLastClosingApp, str, "mLastChangingApp=");
            printWriter.println(this.mLastChangingApp);
        }
    }

    public int getAnimationStyleResId(WindowManager.LayoutParams layoutParams) {
        return this.mTransitionAnimation.getAnimationStyleResId(layoutParams);
    }

    public int getDefaultWindowAnimationStyleResId() {
        return this.mDefaultWindowAnimationStyleResId;
    }

    public final int getFirstAppTransition() {
        for (int i = 0; i < this.mNextAppTransitionRequests.size(); i++) {
            int intValue = ((Integer) this.mNextAppTransitionRequests.get(i)).intValue();
            if (intValue != 0 && intValue != 7 && intValue != 8 && intValue != 9) {
                return intValue;
            }
        }
        return 0;
    }

    public final int getKeyguardTransition() {
        if (this.mNextAppTransitionRequests.indexOf(7) != -1) {
            return 7;
        }
        int indexOf = this.mNextAppTransitionRequests.indexOf(9);
        int indexOf2 = this.mNextAppTransitionRequests.indexOf(8);
        if (indexOf == -1 && indexOf2 == -1) {
            return 0;
        }
        if (indexOf == -1 || indexOf >= indexOf2) {
            return indexOf != -1 ? 9 : 8;
        }
        return 0;
    }

    public final int goodToGo(final int i, ActivityRecord activityRecord) {
        final ArrayList arrayList;
        RemoteAnimationTarget remoteAnimationTarget;
        SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback;
        SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback2;
        this.mNextAppTransitionFlags = 0;
        this.mNextAppTransitionRequests.clear();
        this.mAppTransitionState = 2;
        updateBooster();
        WindowContainer animatingContainer = activityRecord != null ? activityRecord.getAnimatingContainer() : null;
        AnimationAdapter animation = animatingContainer != null ? animatingContainer.getAnimation() : null;
        long statusBarTransitionsStartTime = animation != null ? animation.getStatusBarTransitionsStartTime() : SystemClock.uptimeMillis();
        for (int i2 = 0; i2 < this.mListeners.size(); i2++) {
            ((WindowManagerInternal.AppTransitionListener) this.mListeners.get(i2)).onAppTransitionStartingLocked(statusBarTransitionsStartTime);
        }
        boolean z = animatingContainer != null && animatingContainer.isDexMode();
        final RemoteAnimationController remoteAnimationController = this.mRemoteAnimationController;
        if (remoteAnimationController != null) {
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled;
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 6986037643494242400L, 0, null, null);
            }
            if (remoteAnimationController.mCanceled) {
                if (zArr[0]) {
                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -1902984034737899928L, 0, null, null);
                }
                remoteAnimationController.onAnimationFinished();
                remoteAnimationController.invokeAnimationCancelled("already_cancelled");
            } else {
                remoteAnimationController.mHandler.postDelayed(remoteAnimationController.mTimeoutRunnable, (long) (remoteAnimationController.mService.getCurrentAnimatorScale() * 10000.0f));
                RemoteAnimationController.FinishedCallback finishedCallback = new RemoteAnimationController.FinishedCallback();
                finishedCallback.mOuter = remoteAnimationController;
                remoteAnimationController.mFinishedCallback = finishedCallback;
                if (zArr[0]) {
                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 7094394833775573933L, 0, null, null);
                }
                ArrayList arrayList2 = new ArrayList();
                for (int size = remoteAnimationController.mPendingAnimations.size() - 1; size >= 0; size--) {
                    RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord = (RemoteAnimationController.RemoteAnimationRecord) remoteAnimationController.mPendingAnimations.get(size);
                    RemoteAnimationController.RemoteAnimationAdapterWrapper remoteAnimationAdapterWrapper = remoteAnimationRecord.mAdapter;
                    WindowContainer windowContainer = remoteAnimationRecord.mWindowContainer;
                    if (remoteAnimationAdapterWrapper == null || remoteAnimationAdapterWrapper.mCapturedFinishCallback == null || remoteAnimationAdapterWrapper.mCapturedLeash == null) {
                        remoteAnimationTarget = null;
                    } else {
                        remoteAnimationTarget = windowContainer.createRemoteAnimationTarget(remoteAnimationRecord);
                        remoteAnimationRecord.mTarget = remoteAnimationTarget;
                    }
                    if (remoteAnimationTarget != null) {
                        if (zArr[0]) {
                            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -4411070227420990074L, 0, null, String.valueOf(windowContainer));
                        }
                        arrayList2.add(remoteAnimationTarget);
                    } else {
                        if (zArr[0]) {
                            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -4411631520586057580L, 0, null, String.valueOf(windowContainer));
                        }
                        RemoteAnimationController.RemoteAnimationAdapterWrapper remoteAnimationAdapterWrapper2 = remoteAnimationRecord.mAdapter;
                        if (remoteAnimationAdapterWrapper2 != null && (onAnimationFinishedCallback2 = remoteAnimationAdapterWrapper2.mCapturedFinishCallback) != null) {
                            onAnimationFinishedCallback2.onAnimationFinished(remoteAnimationAdapterWrapper2.mAnimationType, remoteAnimationAdapterWrapper2);
                        }
                        RemoteAnimationController.RemoteAnimationAdapterWrapper remoteAnimationAdapterWrapper3 = remoteAnimationRecord.mThumbnailAdapter;
                        if (remoteAnimationAdapterWrapper3 != null && (onAnimationFinishedCallback = remoteAnimationAdapterWrapper3.mCapturedFinishCallback) != null) {
                            onAnimationFinishedCallback.onAnimationFinished(remoteAnimationAdapterWrapper3.mAnimationType, remoteAnimationAdapterWrapper3);
                        }
                        remoteAnimationController.mPendingAnimations.remove(size);
                    }
                }
                final RemoteAnimationTarget[] remoteAnimationTargetArr = (RemoteAnimationTarget[]) arrayList2.toArray(new RemoteAnimationTarget[arrayList2.size()]);
                if (remoteAnimationTargetArr.length != 0 || i == 22 || i == 33 || i == 23) {
                    Runnable runnable = remoteAnimationController.mOnRemoteAnimationReady;
                    if (runnable != null) {
                        runnable.run();
                        remoteAnimationController.mOnRemoteAnimationReady = null;
                    }
                    if (zArr[0]) {
                        ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -7002230949892506736L, 0, null, null);
                    }
                    final RemoteAnimationTarget[] startWallpaperAnimations = WallpaperAnimationAdapter.startWallpaperAnimations(remoteAnimationController.mDisplayContent, remoteAnimationController.mRemoteAnimationAdapter.getDuration(), remoteAnimationController.mRemoteAnimationAdapter.getStatusBarTransitionDelay(), new Consumer() { // from class: com.android.server.wm.RemoteAnimationController$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            RemoteAnimationController remoteAnimationController2 = RemoteAnimationController.this;
                            WallpaperAnimationAdapter wallpaperAnimationAdapter = (WallpaperAnimationAdapter) obj;
                            WindowManagerGlobalLock windowManagerGlobalLock = remoteAnimationController2.mService.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock) {
                                try {
                                    remoteAnimationController2.mPendingWallpaperAnimations.remove(wallpaperAnimationAdapter);
                                } catch (Throwable th) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    throw th;
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }, remoteAnimationController.mPendingWallpaperAnimations);
                    if (zArr[0]) {
                        ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 8743612568733301175L, 0, null, null);
                    }
                    final WindowManagerService windowManagerService = remoteAnimationController.mService;
                    DisplayContent displayContent = remoteAnimationController.mDisplayContent;
                    final long duration = remoteAnimationController.mRemoteAnimationAdapter.getDuration();
                    final long statusBarTransitionDelay = remoteAnimationController.mRemoteAnimationAdapter.getStatusBarTransitionDelay();
                    final ArrayList arrayList3 = remoteAnimationController.mPendingNonAppAnimations;
                    ArrayList arrayList4 = new ArrayList();
                    if (i == 20 || i == 21) {
                        arrayList = arrayList4;
                        WindowManagerPolicy windowManagerPolicy = windowManagerService.mPolicy;
                        windowManagerService.mRoot.forAllWindows(new Consumer() { // from class: com.android.server.wm.NonAppWindowAnimationAdapter$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                WindowManagerService windowManagerService2 = WindowManagerService.this;
                                long j = duration;
                                long j2 = statusBarTransitionDelay;
                                ArrayList arrayList5 = arrayList3;
                                ArrayList arrayList6 = arrayList;
                                WindowState windowState = (WindowState) obj;
                                if (windowState.mActivityRecord == null && windowState.canBeHiddenByKeyguard() && windowState.wouldBeVisibleIfPolicyIgnored() && !windowState.isVisible() && windowState != windowManagerService2.mRoot.getCurrentInputMethodWindow()) {
                                    NonAppWindowAnimationAdapter nonAppWindowAnimationAdapter = new NonAppWindowAnimationAdapter(windowState, j, j2);
                                    arrayList5.add(nonAppWindowAnimationAdapter);
                                    windowState.startAnimation(windowState.getPendingTransaction(), nonAppWindowAnimationAdapter, false, 16);
                                    arrayList6.add(nonAppWindowAnimationAdapter.createRemoteAnimationTarget());
                                }
                            }
                        }, true);
                    } else if (NonAppWindowAnimationAdapter.shouldAttachNavBarToApp(windowManagerService, displayContent, i)) {
                        WindowState windowState = displayContent.mDisplayPolicy.mNavigationBar;
                        arrayList = arrayList4;
                        NonAppWindowAnimationAdapter nonAppWindowAnimationAdapter = new NonAppWindowAnimationAdapter(windowState.mToken, duration, statusBarTransitionDelay);
                        arrayList3.add(nonAppWindowAnimationAdapter);
                        WindowToken windowToken = windowState.mToken;
                        windowToken.startAnimation(windowToken.getPendingTransaction(), nonAppWindowAnimationAdapter, false, 16);
                        arrayList.add(nonAppWindowAnimationAdapter.createRemoteAnimationTarget());
                    } else {
                        arrayList = arrayList4;
                    }
                    final RemoteAnimationTarget[] remoteAnimationTargetArr2 = (RemoteAnimationTarget[]) arrayList.toArray(new RemoteAnimationTarget[arrayList.size()]);
                    remoteAnimationController.mService.mAnimator.addAfterPrepareSurfacesRunnable(new Runnable() { // from class: com.android.server.wm.RemoteAnimationController$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteAnimationController remoteAnimationController2 = RemoteAnimationController.this;
                            int i3 = i;
                            RemoteAnimationTarget[] remoteAnimationTargetArr3 = remoteAnimationTargetArr;
                            RemoteAnimationTarget[] remoteAnimationTargetArr4 = startWallpaperAnimations;
                            RemoteAnimationTarget[] remoteAnimationTargetArr5 = remoteAnimationTargetArr2;
                            remoteAnimationController2.getClass();
                            boolean[] zArr2 = ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled;
                            try {
                                if (!remoteAnimationController2.mLinkedToDeathOfRunner) {
                                    remoteAnimationController2.mRemoteAnimationAdapter.getRunner().asBinder().linkToDeath(remoteAnimationController2, 0);
                                    remoteAnimationController2.mLinkedToDeathOfRunner = true;
                                }
                                if (zArr2[0]) {
                                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -2525509826755873433L, 84, null, String.valueOf(AppTransition.appTransitionOldToString(i3)), Long.valueOf(remoteAnimationTargetArr3.length), Long.valueOf(remoteAnimationTargetArr4.length), Long.valueOf(remoteAnimationTargetArr5.length));
                                }
                                ArrayList arrayList5 = AppTransition.sFlagToString;
                                if (i3 == 22 || i3 == 33 || i3 == 23) {
                                    EventLog.writeEvent(31008, Integer.valueOf(i3 == 23 ? 0 : 1), 1, Integer.valueOf(i3), "onAnimationStart");
                                }
                                remoteAnimationController2.mRemoteAnimationAdapter.getRunner().onAnimationStart(i3, remoteAnimationTargetArr3, remoteAnimationTargetArr4, remoteAnimationTargetArr5, remoteAnimationController2.mFinishedCallback);
                            } catch (RemoteException e) {
                                Slog.e("WindowManager", "Failed to start remote animation", e);
                                remoteAnimationController2.onAnimationFinished();
                            }
                            ProtoLogGroup protoLogGroup = ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS;
                            if (ProtoLogImpl_54989576.isEnabled(protoLogGroup, LogLevel.DEBUG)) {
                                if (zArr2[0]) {
                                    ProtoLogImpl_54989576.d(protoLogGroup, -1148281153370899511L, 0, null, null);
                                }
                                if (zArr2[2]) {
                                    ProtoLogImpl_54989576.i(protoLogGroup, -1424368765415574722L, 0, null, null);
                                }
                                StringWriter stringWriter = new StringWriter();
                                PrintWriter fastPrintWriter = new FastPrintWriter(stringWriter);
                                for (int size2 = remoteAnimationController2.mPendingAnimations.size() - 1; size2 >= 0; size2--) {
                                    ((RemoteAnimationController.RemoteAnimationRecord) remoteAnimationController2.mPendingAnimations.get(size2)).mAdapter.dump(fastPrintWriter, "");
                                }
                                fastPrintWriter.close();
                                if (zArr2[2]) {
                                    ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -2676700429940607853L, 0, null, String.valueOf(stringWriter.toString()));
                                }
                            }
                        }
                    });
                    remoteAnimationController.setRunningRemoteAnimation(true);
                } else {
                    if (zArr[0]) {
                        ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 6727618365838540075L, 1, null, Long.valueOf(remoteAnimationController.mPendingAnimations.size()));
                    }
                    remoteAnimationController.onAnimationFinished();
                    remoteAnimationController.invokeAnimationCancelled("no_app_targets");
                }
            }
        } else if ((i == 8 || i == 16 || i == 10 || i == 12) && animation != null && !z && this.mDisplayContent.mDisplayPolicy.shouldAttachNavBarToAppDuringTransition() && this.mService.mRecentsAnimationController == null) {
            new NavBarFadeAnimationController(this.mDisplayContent).fadeOutAndInSequentially(animation.getDurationHint(), activityRecord.mSurfaceControl);
        }
        return 0;
    }

    public boolean isNextAppTransitionOverrideRequested() {
        return this.mNextAppTransitionOverrideRequested;
    }

    public final boolean isReady() {
        int i = this.mAppTransitionState;
        return i == 1 || i == 3;
    }

    public final boolean isTransitionSet() {
        return !this.mNextAppTransitionRequests.isEmpty();
    }

    public Animation loadAnimationSafely(Context context, int i) {
        return TransitionAnimation.loadAnimationSafely(context, i, "WindowManager");
    }

    public final void notifyAppTransitionFinishedLocked(IBinder iBinder) {
        for (int i = 0; i < this.mListeners.size(); i++) {
            ((WindowManagerInternal.AppTransitionListener) this.mListeners.get(i)).onAppTransitionFinishedLocked(iBinder);
        }
    }

    public final void overridePendingAppTransition(String str, int i, int i2, int i3, IRemoteCallback iRemoteCallback, IRemoteCallback iRemoteCallback2, boolean z) {
        if (canOverridePendingAppTransition()) {
            clear(true);
            this.mNextAppTransitionOverrideRequested = true;
            this.mNextAppTransitionPackage = str;
            this.mNextAppTransitionEnter = i;
            this.mNextAppTransitionExit = i2;
            this.mNextAppTransitionBackgroundColor = i3;
            postAnimationCallback();
            this.mNextAppTransitionCallback = iRemoteCallback;
            this.mAnimationFinishedCallback = iRemoteCallback2;
            this.mOverrideTaskTransition = z;
        }
    }

    public final void overridePendingAppTransitionMultiThumb(AppTransitionAnimationSpec[] appTransitionAnimationSpecArr, IRemoteCallback iRemoteCallback, IRemoteCallback iRemoteCallback2, boolean z) {
        if (canOverridePendingAppTransition()) {
            clear(true);
            this.mNextAppTransitionType = z ? 5 : 6;
            this.mNextAppTransitionScaleUp = z;
            if (appTransitionAnimationSpecArr != null) {
                for (int i = 0; i < appTransitionAnimationSpecArr.length; i++) {
                    AppTransitionAnimationSpec appTransitionAnimationSpec = appTransitionAnimationSpecArr[i];
                    if (appTransitionAnimationSpec != null) {
                        Predicate obtainPredicate = PooledLambda.obtainPredicate(new AppTransition$$ExternalSyntheticLambda1(), PooledLambda.__(Task.class), Integer.valueOf(appTransitionAnimationSpec.taskId));
                        Task task = this.mDisplayContent.getTask(obtainPredicate);
                        obtainPredicate.recycle();
                        if (task != null) {
                            this.mNextAppTransitionAnimationsSpecs.put(task.hashCode(), appTransitionAnimationSpec);
                            if (i == 0) {
                                Rect rect = appTransitionAnimationSpec.rect;
                                putDefaultNextAppTransitionCoordinates(rect.left, rect.top, rect.width(), rect.height(), appTransitionAnimationSpec.buffer);
                            }
                        }
                    }
                }
            }
            postAnimationCallback();
            this.mNextAppTransitionCallback = iRemoteCallback;
            this.mAnimationFinishedCallback = iRemoteCallback2;
        }
    }

    public final void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, IRemoteCallback iRemoteCallback, boolean z) {
        IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture2;
        if (canOverridePendingAppTransition()) {
            clear(true);
            this.mNextAppTransitionType = z ? 5 : 6;
            this.mNextAppTransitionAnimationsSpecsFuture = iAppTransitionAnimationSpecsFuture;
            this.mNextAppTransitionScaleUp = z;
            this.mNextAppTransitionFutureCallback = iRemoteCallback;
            if (!isReady() || (iAppTransitionAnimationSpecsFuture2 = this.mNextAppTransitionAnimationsSpecsFuture) == null) {
                return;
            }
            this.mNextAppTransitionAnimationsSpecsPending = true;
            this.mNextAppTransitionAnimationsSpecsFuture = null;
            this.mDefaultExecutor.execute(new AppTransition$$ExternalSyntheticLambda0(this, iAppTransitionAnimationSpecsFuture2));
        }
    }

    public final void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, boolean z, boolean z2) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled[2]) {
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 6217525691846442213L, 3, null, Boolean.valueOf(isTransitionSet()), String.valueOf(remoteAnimationAdapter));
        }
        if (!isTransitionSet() || this.mNextAppTransitionIsSync) {
            return;
        }
        clear(!z2);
        this.mNextAppTransitionType = 10;
        this.mRemoteAnimationController = new RemoteAnimationController(this.mService, this.mDisplayContent, remoteAnimationAdapter, this.mHandler, z2);
        this.mNextAppTransitionIsSync = z;
    }

    public final void postAnimationCallback() {
        IRemoteCallback iRemoteCallback = this.mNextAppTransitionCallback;
        if (iRemoteCallback != null) {
            this.mHandler.sendMessage(PooledLambda.obtainMessage(new AppTransition$$ExternalSyntheticLambda3(0), iRemoteCallback));
            this.mNextAppTransitionCallback = null;
        }
    }

    public final boolean prepare() {
        if (this.mAppTransitionState == 2) {
            return false;
        }
        this.mAppTransitionState = 0;
        updateBooster();
        for (int i = 0; i < this.mListeners.size(); i++) {
            ((WindowManagerInternal.AppTransitionListener) this.mListeners.get(i)).onAppTransitionPendingLocked();
        }
        return true;
    }

    public final void putDefaultNextAppTransitionCoordinates(int i, int i2, int i3, int i4, HardwareBuffer hardwareBuffer) {
        this.mDefaultNextAppTransitionAnimationSpec = new AppTransitionAnimationSpec(-1, hardwareBuffer, new Rect(i, i2, i3 + i, i4 + i2));
    }

    public final void registerListenerLocked(WindowManagerInternal.AppTransitionListener appTransitionListener) {
        this.mListeners.add(appTransitionListener);
    }

    public final void setLastAppTransition(int i, ActivityRecord activityRecord, ActivityRecord activityRecord2, ActivityRecord activityRecord3) {
        this.mLastUsedAppTransition = i;
        this.mLastOpeningApp = "" + activityRecord;
        this.mLastClosingApp = "" + activityRecord2;
        this.mLastChangingApp = "" + activityRecord3;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("mNextAppTransitionRequests=[");
        Iterator it = this.mNextAppTransitionRequests.iterator();
        boolean z = false;
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (z) {
                sb.append(", ");
            }
            int intValue = num.intValue();
            switch (intValue) {
                case 0:
                    str = "TRANSIT_NONE";
                    break;
                case 1:
                    str = "TRANSIT_OPEN";
                    break;
                case 2:
                    str = "TRANSIT_CLOSE";
                    break;
                case 3:
                    str = "TRANSIT_TO_FRONT";
                    break;
                case 4:
                    str = "TRANSIT_TO_BACK";
                    break;
                case 5:
                    str = "TRANSIT_RELAUNCH";
                    break;
                case 6:
                    str = "TRANSIT_CHANGE";
                    break;
                case 7:
                    str = "TRANSIT_KEYGUARD_GOING_AWAY";
                    break;
                case 8:
                    str = "TRANSIT_KEYGUARD_OCCLUDE";
                    break;
                case 9:
                    str = "TRANSIT_KEYGUARD_UNOCCLUDE";
                    break;
                default:
                    str = BinaryTransparencyService$$ExternalSyntheticOutline0.m(intValue, "<UNKNOWN: ", ">");
                    break;
            }
            sb.append(str);
            z = true;
        }
        sb.append("]");
        StringBuilder sb2 = new StringBuilder(", mNextAppTransitionFlags=");
        int i = this.mNextAppTransitionFlags;
        StringBuilder sb3 = new StringBuilder();
        Iterator it2 = sFlagToString.iterator();
        String str2 = "";
        while (it2.hasNext()) {
            Pair pair = (Pair) it2.next();
            if ((((Integer) pair.first).intValue() & i) != 0) {
                sb3.append(str2);
                sb3.append((String) pair.second);
                str2 = " | ";
            }
        }
        sb2.append(sb3.toString());
        sb.append(sb2.toString());
        return sb.toString();
    }

    public final void updateBooster() {
        int i;
        WindowManagerThreadPriorityBooster windowManagerThreadPriorityBooster = WindowManagerService.sThreadPriorityBooster;
        boolean z = !this.mNextAppTransitionRequests.isEmpty() || (i = this.mAppTransitionState) == 1 || i == 2 || (this.mService.mRecentsAnimationController != null);
        synchronized (windowManagerThreadPriorityBooster.mLock) {
            try {
                if (windowManagerThreadPriorityBooster.mAppTransitionRunning != z) {
                    windowManagerThreadPriorityBooster.mAppTransitionRunning = z;
                    int i2 = !z ? -4 : -10;
                    windowManagerThreadPriorityBooster.setBoostToPriority(i2);
                    Process.setThreadPriority(windowManagerThreadPriorityBooster.mAnimationThreadId, i2);
                    Process.setThreadPriority(windowManagerThreadPriorityBooster.mSurfaceAnimationThreadId, i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
