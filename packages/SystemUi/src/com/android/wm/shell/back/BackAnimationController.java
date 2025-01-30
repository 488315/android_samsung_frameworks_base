package com.android.wm.shell.back;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.content.ContentResolver;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseArray;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.KeyEvent;
import android.view.RemoteAnimationTarget;
import android.view.animation.Animation;
import android.window.BackAnimationAdapter;
import android.window.BackMotionEvent;
import android.window.BackNavigationInfo;
import android.window.IBackAnimationFinishedCallback;
import android.window.IBackAnimationRunner;
import android.window.IOnBackInvokedCallback;
import com.android.wm.shell.animation.FlingAnimationUtils;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.back.BackAnimationRunner;
import com.android.wm.shell.back.CustomizeActivityAnimation;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda5;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BackAnimationController implements RemoteCallable {
    public static final boolean IS_ENABLED;
    public static boolean IS_U_ANIMATION_ENABLED;
    public IOnBackInvokedCallback mActiveCallback;
    public final IActivityTaskManager mActivityTaskManager;
    public final BackAnimationBackground mAnimationBackground;
    public final SparseArray mAnimationDefinition;
    public final BackAnimationController$$ExternalSyntheticLambda1 mAnimationTimeoutRunnable;
    public final BackAnimationImpl mBackAnimation;
    BackAnimationAdapter mBackAnimationAdapter;
    public IBackAnimationFinishedCallback mBackAnimationFinishedCallback;
    public boolean mBackGestureStarted;
    public BackNavigationInfo mBackNavigationInfo;
    public final Handler mBgHandler;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public CustomizeActivityAnimation mCustomizeActivityAnimation;
    public EdgeBackGestureHandler$$ExternalSyntheticLambda5 mCustomizer;
    public CrossActivityAnimation mDefaultActivityAnimation;
    public final AtomicBoolean mEnableAnimations;
    public final FlingAnimationUtils mFlingAnimationUtils;
    final RemoteCallback mNavigationObserver;
    public boolean mPostCommitAnimationInProgress;
    public final ShellController mShellController;
    public final ShellExecutor mShellExecutor;
    public boolean mShouldStartOnNextMoveEvent;
    public final TouchTracker mTouchTracker;
    public boolean mTriggerBack;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.back.BackAnimationController$1 */
    public final class C37771 implements RemoteCallback.OnResultListener {
        public C37771() {
        }

        public final void onResult(Bundle bundle) {
            ((HandlerExecutor) BackAnimationController.this.mShellExecutor).execute(new BackAnimationController$4$$ExternalSyntheticLambda1(this, 2));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.back.BackAnimationController$4 */
    public final class C37804 extends IBackAnimationRunner.Stub {
        public C37804() {
        }

        public final void onAnimationCancelled() {
            ((HandlerExecutor) BackAnimationController.this.mShellExecutor).execute(new BackAnimationController$4$$ExternalSyntheticLambda1(this, 0));
        }

        public final void onAnimationStart(final RemoteAnimationTarget[] remoteAnimationTargetArr, final RemoteAnimationTarget[] remoteAnimationTargetArr2, final RemoteAnimationTarget[] remoteAnimationTargetArr3, final IBackAnimationFinishedCallback iBackAnimationFinishedCallback) {
            ((HandlerExecutor) BackAnimationController.this.mShellExecutor).execute(new Runnable() { // from class: com.android.wm.shell.back.BackAnimationController$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BackNavigationInfo.CustomAnimationInfo customAnimationInfo;
                    CustomizeActivityAnimation customizeActivityAnimation;
                    CustomizeActivityAnimation.AnimationLoadResult animationLoadResult;
                    boolean z;
                    Animation loadAnimation;
                    BackAnimationController.C37804 c37804 = BackAnimationController.C37804.this;
                    IBackAnimationFinishedCallback iBackAnimationFinishedCallback2 = iBackAnimationFinishedCallback;
                    RemoteAnimationTarget[] remoteAnimationTargetArr4 = remoteAnimationTargetArr;
                    RemoteAnimationTarget[] remoteAnimationTargetArr5 = remoteAnimationTargetArr2;
                    RemoteAnimationTarget[] remoteAnimationTargetArr6 = remoteAnimationTargetArr3;
                    BackNavigationInfo backNavigationInfo = BackAnimationController.this.mBackNavigationInfo;
                    if (backNavigationInfo == null) {
                        Log.e("ShellBackPreview", "Lack of navigation info to start animation.");
                        return;
                    }
                    int type = backNavigationInfo.getType();
                    BackAnimationController backAnimationController = BackAnimationController.this;
                    int type2 = backAnimationController.mBackNavigationInfo.getType();
                    SparseArray sparseArray = backAnimationController.mAnimationDefinition;
                    if (type2 == 2 && sparseArray.contains(type2) && (customAnimationInfo = backAnimationController.mBackNavigationInfo.getCustomAnimationInfo()) != null && (customizeActivityAnimation = backAnimationController.mCustomizeActivityAnimation) != null) {
                        CustomizeActivityAnimation.CustomAnimationLoader customAnimationLoader = customizeActivityAnimation.mCustomAnimationLoader;
                        customAnimationLoader.getClass();
                        if (customAnimationInfo.getPackageName().isEmpty() || (loadAnimation = customAnimationLoader.loadAnimation(customAnimationInfo, false)) == null) {
                            animationLoadResult = null;
                        } else {
                            Animation loadAnimation2 = customAnimationLoader.loadAnimation(customAnimationInfo, true);
                            animationLoadResult = new CustomizeActivityAnimation.AnimationLoadResult();
                            animationLoadResult.mCloseAnimation = loadAnimation;
                            animationLoadResult.mEnterAnimation = loadAnimation2;
                            animationLoadResult.mBackgroundColor = customAnimationInfo.getCustomBackground();
                        }
                        if (animationLoadResult != null) {
                            customizeActivityAnimation.mCloseAnimation = animationLoadResult.mCloseAnimation;
                            customizeActivityAnimation.mEnterAnimation = animationLoadResult.mEnterAnimation;
                            customizeActivityAnimation.mNextBackgroundColor = animationLoadResult.mBackgroundColor;
                            z = true;
                        } else {
                            z = false;
                        }
                        if (z) {
                            ((BackAnimationRunner) sparseArray.get(type2)).mWaitingAnimation = false;
                            sparseArray.set(2, backAnimationController.mCustomizeActivityAnimation.mBackAnimationRunner);
                        }
                    }
                    BackAnimationRunner backAnimationRunner = (BackAnimationRunner) sparseArray.get(type2);
                    if (backAnimationRunner == null) {
                        Log.e("ShellBackPreview", "Animation didn't be defined for type " + BackNavigationInfo.typeToString(type));
                        if (iBackAnimationFinishedCallback2 != null) {
                            try {
                                iBackAnimationFinishedCallback2.onAnimationFinished(false);
                                return;
                            } catch (RemoteException e) {
                                Log.w("ShellBackPreview", "Failed call IBackNaviAnimationController", e);
                                return;
                            }
                        }
                        return;
                    }
                    BackAnimationController backAnimationController2 = BackAnimationController.this;
                    backAnimationController2.mActiveCallback = backAnimationRunner.mCallback;
                    backAnimationController2.mBackAnimationFinishedCallback = iBackAnimationFinishedCallback2;
                    if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                        ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 717477794, 0, "BackAnimationController: startAnimation()", null);
                    }
                    BackAnimationRunner.C37811 c37811 = new IRemoteAnimationFinishedCallback.Stub(backAnimationRunner, new BackAnimationController$4$$ExternalSyntheticLambda1(c37804, 1)) { // from class: com.android.wm.shell.back.BackAnimationRunner.1
                        public final /* synthetic */ Runnable val$finishedCallback;

                        public C37811(BackAnimationRunner backAnimationRunner2, Runnable runnable) {
                            this.val$finishedCallback = runnable;
                        }

                        public final void onAnimationFinished() {
                            this.val$finishedCallback.run();
                        }
                    };
                    backAnimationRunner2.mWaitingAnimation = false;
                    try {
                        backAnimationRunner2.mRunner.onAnimationStart(-1, remoteAnimationTargetArr4, remoteAnimationTargetArr5, remoteAnimationTargetArr6, c37811);
                    } catch (RemoteException e2) {
                        Log.w("ShellBackPreview", "Failed call onAnimationStart", e2);
                    }
                    if (remoteAnimationTargetArr4.length >= 1) {
                        BackAnimationController backAnimationController3 = BackAnimationController.this;
                        IOnBackInvokedCallback iOnBackInvokedCallback = backAnimationController3.mActiveCallback;
                        RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr4[0];
                        TouchTracker touchTracker = backAnimationController3.mTouchTracker;
                        touchTracker.getClass();
                        BackMotionEvent backMotionEvent = new BackMotionEvent(touchTracker.mInitTouchX, touchTracker.mInitTouchY, 0.0f, 0.0f, 0.0f, touchTracker.mSwipeEdge, remoteAnimationTarget);
                        if (iOnBackInvokedCallback != null) {
                            try {
                                iOnBackInvokedCallback.onBackStarted(backMotionEvent);
                            } catch (RemoteException e3) {
                                Log.e("ShellBackPreview", "dispatchOnBackStarted error: ", e3);
                            }
                        }
                    }
                    BackMotionEvent createProgressEvent = BackAnimationController.this.mTouchTracker.createProgressEvent();
                    IOnBackInvokedCallback iOnBackInvokedCallback2 = BackAnimationController.this.mActiveCallback;
                    if (iOnBackInvokedCallback2 != null) {
                        try {
                            iOnBackInvokedCallback2.onBackProgressed(createProgressEvent);
                        } catch (RemoteException e4) {
                            Log.e("ShellBackPreview", "dispatchOnBackProgressed error: ", e4);
                        }
                    }
                    BackAnimationController backAnimationController4 = BackAnimationController.this;
                    if (backAnimationController4.mBackGestureStarted) {
                        return;
                    }
                    backAnimationController4.startPostCommitAnimation();
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BackAnimationImpl {
        public /* synthetic */ BackAnimationImpl(BackAnimationController backAnimationController, int i) {
            this();
        }

        private BackAnimationImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IBackAnimationImpl extends IBackAnimation$Stub implements ExternalInterfaceBinder {
        public BackAnimationController mController;

        public IBackAnimationImpl(BackAnimationController backAnimationController) {
            this.mController = backAnimationController;
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
        }
    }

    static {
        IS_ENABLED = SystemProperties.getInt("persist.wm.debug.predictive_back", 1) == 1;
        IS_U_ANIMATION_ENABLED = SystemProperties.getInt("persist.wm.debug.predictive_back_anim", 1) == 1;
    }

    public BackAnimationController(ShellInit shellInit, ShellController shellController, ShellExecutor shellExecutor, Handler handler, Context context, BackAnimationBackground backAnimationBackground) {
        this(shellInit, shellController, shellExecutor, handler, ActivityTaskManager.getService(), context, context.getContentResolver(), backAnimationBackground);
    }

    public static void dispatchOnBackInvoked(IOnBackInvokedCallback iOnBackInvokedCallback) {
        if (iOnBackInvokedCallback == null) {
            return;
        }
        try {
            Log.d("ShellBackPreview", "dispatchOnBackInvoked, caller=" + Debug.getCallers(3));
            iOnBackInvokedCallback.onBackInvoked();
        } catch (RemoteException e) {
            Log.e("ShellBackPreview", "dispatchOnBackInvoked error: ", e);
        }
    }

    public final void dispatchOrAnimateOnBackInvoked(final IOnBackInvokedCallback iOnBackInvokedCallback) {
        if (iOnBackInvokedCallback == null) {
            return;
        }
        BackNavigationInfo backNavigationInfo = this.mBackNavigationInfo;
        boolean z = false;
        if (backNavigationInfo != null && backNavigationInfo.isAnimationCallback()) {
            TouchTracker touchTracker = this.mTouchTracker;
            BackMotionEvent createProgressEvent = touchTracker.createProgressEvent();
            FlingAnimationUtils flingAnimationUtils = this.mFlingAnimationUtils;
            float f = flingAnimationUtils.mMinVelocityPxPerSecond;
            float f2 = touchTracker.mMaxDistance;
            float f3 = f2 * 0.3f;
            float touchX = createProgressEvent.getTouchX();
            float velocityX = createProgressEvent.getVelocityX();
            float f4 = flingAnimationUtils.mHighVelocityPxPerSecond;
            float constrain = MathUtils.constrain(velocityX, -f4, f4);
            float constrain2 = MathUtils.constrain(((constrain / f4) * f3) + touchX, 0.0f, f2);
            if (!Float.isNaN(constrain2) && touchX != constrain2 && Math.abs(constrain) >= f) {
                z = true;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(touchX, constrain2);
                this.mFlingAnimationUtils.apply(ofFloat, touchX, constrain2, constrain, f3);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.back.BackAnimationController$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        BackAnimationController backAnimationController = BackAnimationController.this;
                        backAnimationController.getClass();
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        TouchTracker touchTracker2 = backAnimationController.mTouchTracker;
                        BackMotionEvent backMotionEvent = new BackMotionEvent(touchTracker2.mLatestTouchX, touchTracker2.mLatestTouchY, touchTracker2.getProgress(floatValue), touchTracker2.mLatestVelocityX, touchTracker2.mLatestVelocityY, touchTracker2.mSwipeEdge, (RemoteAnimationTarget) null);
                        IOnBackInvokedCallback iOnBackInvokedCallback2 = backAnimationController.mActiveCallback;
                        if (iOnBackInvokedCallback2 == null) {
                            return;
                        }
                        try {
                            iOnBackInvokedCallback2.onBackProgressed(backMotionEvent);
                        } catch (RemoteException e) {
                            Log.e("ShellBackPreview", "dispatchOnBackProgressed error: ", e);
                        }
                    }
                });
                ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.back.BackAnimationController.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        BackAnimationController backAnimationController = BackAnimationController.this;
                        IOnBackInvokedCallback iOnBackInvokedCallback2 = iOnBackInvokedCallback;
                        boolean z2 = BackAnimationController.IS_ENABLED;
                        backAnimationController.getClass();
                        BackAnimationController.dispatchOnBackInvoked(iOnBackInvokedCallback2);
                    }
                });
                ofFloat.start();
            }
        }
        if (z) {
            return;
        }
        dispatchOnBackInvoked(iOnBackInvokedCallback);
    }

    public void finishBackNavigation() {
        if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -269346130, 0, "BackAnimationController: finishBackNavigation()", null);
        }
        this.mShouldStartOnNextMoveEvent = false;
        TouchTracker touchTracker = this.mTouchTracker;
        touchTracker.mInitTouchX = 0.0f;
        touchTracker.mInitTouchY = 0.0f;
        touchTracker.mStartThresholdX = 0.0f;
        touchTracker.mCancelled = false;
        touchTracker.mTriggerBack = false;
        touchTracker.mSwipeEdge = 0;
        this.mActiveCallback = null;
        if (this.mDefaultActivityAnimation != null) {
            SparseArray sparseArray = this.mAnimationDefinition;
            if (sparseArray.contains(2)) {
                sparseArray.set(2, this.mDefaultActivityAnimation.mBackAnimationRunner);
            }
        }
        BackNavigationInfo backNavigationInfo = this.mBackNavigationInfo;
        if (backNavigationInfo != null) {
            backNavigationInfo.onBackNavigationFinished(this.mTriggerBack);
            this.mBackNavigationInfo = null;
        }
        this.mTriggerBack = false;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mShellExecutor;
    }

    public final void invokeOrCancelBack() {
        IBackAnimationFinishedCallback iBackAnimationFinishedCallback = this.mBackAnimationFinishedCallback;
        if (iBackAnimationFinishedCallback != null) {
            try {
                iBackAnimationFinishedCallback.onAnimationFinished(this.mTriggerBack);
            } catch (RemoteException e) {
                Log.e("ShellBackPreview", "Failed call IBackAnimationFinishedCallback", e);
            }
            this.mBackAnimationFinishedCallback = null;
        }
        BackNavigationInfo backNavigationInfo = this.mBackNavigationInfo;
        if (backNavigationInfo != null) {
            IOnBackInvokedCallback onBackInvokedCallback = backNavigationInfo.getOnBackInvokedCallback();
            if (this.mTriggerBack) {
                dispatchOrAnimateOnBackInvoked(onBackInvokedCallback);
            } else if (onBackInvokedCallback != null) {
                try {
                    onBackInvokedCallback.onBackCancelled();
                } catch (RemoteException e2) {
                    Log.e("ShellBackPreview", "dispatchOnBackCancelled error: ", e2);
                }
            }
        }
        finishBackNavigation();
    }

    public void onBackAnimationFinished() {
        ((HandlerExecutor) this.mShellExecutor).removeCallbacks(this.mAnimationTimeoutRunnable);
        this.mPostCommitAnimationInProgress = false;
        if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 1155282524, 0, "BackAnimationController: onBackAnimationFinished()", null);
        }
        invokeOrCancelBack();
    }

    public final void onBackNavigationInfoReceived(BackNavigationInfo backNavigationInfo) {
        BackNavigationInfo backNavigationInfo2;
        if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -2134376374, 0, "Received backNavigationInfo:%s", String.valueOf(backNavigationInfo));
        }
        if (backNavigationInfo == null) {
            Log.e("ShellBackPreview", "Received BackNavigationInfo is null.");
            return;
        }
        int type = backNavigationInfo.getType();
        if (this.mEnableAnimations.get() && (backNavigationInfo2 = this.mBackNavigationInfo) != null && backNavigationInfo2.isPrepareRemoteAnimation()) {
            SparseArray sparseArray = this.mAnimationDefinition;
            if (!sparseArray.contains(type)) {
                this.mActiveCallback = null;
                return;
            }
            BackAnimationRunner backAnimationRunner = (BackAnimationRunner) sparseArray.get(type);
            backAnimationRunner.mWaitingAnimation = true;
            backAnimationRunner.mAnimationCancelled = false;
            return;
        }
        IOnBackInvokedCallback onBackInvokedCallback = this.mBackNavigationInfo.getOnBackInvokedCallback();
        this.mActiveCallback = onBackInvokedCallback;
        TouchTracker touchTracker = this.mTouchTracker;
        touchTracker.getClass();
        BackMotionEvent backMotionEvent = new BackMotionEvent(touchTracker.mInitTouchX, touchTracker.mInitTouchY, 0.0f, 0.0f, 0.0f, touchTracker.mSwipeEdge, (RemoteAnimationTarget) null);
        if (onBackInvokedCallback == null) {
            return;
        }
        try {
            onBackInvokedCallback.onBackStarted(backMotionEvent);
        } catch (RemoteException e) {
            Log.e("ShellBackPreview", "dispatchOnBackStarted error: ", e);
        }
    }

    public final void onGestureFinished(boolean z) {
        BackNavigationInfo backNavigationInfo;
        if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -14660627, 0, "onGestureFinished() mTriggerBack == %s", String.valueOf(this.mTriggerBack));
        }
        if (!this.mBackGestureStarted) {
            finishBackNavigation();
            return;
        }
        if (z) {
            this.mBackGestureStarted = false;
        }
        if (this.mPostCommitAnimationInProgress) {
            if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -1560368160, 0, "Animation is still running", null);
                return;
            }
            return;
        }
        BackNavigationInfo backNavigationInfo2 = this.mBackNavigationInfo;
        if (backNavigationInfo2 == null) {
            if (this.mTriggerBack) {
                sendBackEvent(0);
                sendBackEvent(1);
            }
            finishBackNavigation();
            return;
        }
        BackAnimationRunner backAnimationRunner = (BackAnimationRunner) this.mAnimationDefinition.get(backNavigationInfo2.getType());
        if (!(this.mEnableAnimations.get() && (backNavigationInfo = this.mBackNavigationInfo) != null && backNavigationInfo.isPrepareRemoteAnimation()) || backAnimationRunner == null) {
            invokeOrCancelBack();
            return;
        }
        if (backAnimationRunner.mWaitingAnimation) {
            if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -1691705141, 0, "Gesture released, but animation didn't ready.", null);
            }
            ((HandlerExecutor) this.mShellExecutor).executeDelayed(2000L, this.mAnimationTimeoutRunnable);
        } else if (backAnimationRunner.mAnimationCancelled) {
            invokeOrCancelBack();
        } else {
            startPostCommitAnimation();
        }
    }

    public final void sendBackEvent(int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, 72, 257);
        Context context = this.mContext;
        keyEvent.setDisplayId(context.getDisplay().getDisplayId());
        if (((InputManager) context.getSystemService(InputManager.class)).injectInputEvent(keyEvent, 0)) {
            return;
        }
        Log.e("ShellBackPreview", "Inject input event fail");
    }

    public void setEnableUAnimation(boolean z) {
        IS_U_ANIMATION_ENABLED = z;
    }

    public final void startPostCommitAnimation() {
        if (this.mPostCommitAnimationInProgress) {
            return;
        }
        HandlerExecutor handlerExecutor = (HandlerExecutor) this.mShellExecutor;
        BackAnimationController$$ExternalSyntheticLambda1 backAnimationController$$ExternalSyntheticLambda1 = this.mAnimationTimeoutRunnable;
        handlerExecutor.removeCallbacks(backAnimationController$$ExternalSyntheticLambda1);
        if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 740189675, 0, "BackAnimationController: startPostCommitAnimation()", null);
        }
        this.mPostCommitAnimationInProgress = true;
        handlerExecutor.executeDelayed(2000L, backAnimationController$$ExternalSyntheticLambda1);
        if (this.mTriggerBack) {
            dispatchOrAnimateOnBackInvoked(this.mActiveCallback);
            return;
        }
        IOnBackInvokedCallback iOnBackInvokedCallback = this.mActiveCallback;
        if (iOnBackInvokedCallback == null) {
            return;
        }
        try {
            iOnBackInvokedCallback.onBackCancelled();
        } catch (RemoteException e) {
            Log.e("ShellBackPreview", "dispatchOnBackCancelled error: ", e);
        }
    }

    public final void updateEnableAnimationFromSetting() {
        boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "enable_back_animation", 0) == 1;
        this.mEnableAnimations.set(z);
        if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 2142828447, 0, "Back animation enabled=%s", String.valueOf(z));
        }
    }

    public BackAnimationController(ShellInit shellInit, ShellController shellController, ShellExecutor shellExecutor, Handler handler, IActivityTaskManager iActivityTaskManager, Context context, ContentResolver contentResolver, BackAnimationBackground backAnimationBackground) {
        int i = 0;
        this.mEnableAnimations = new AtomicBoolean(false);
        this.mBackGestureStarted = false;
        this.mPostCommitAnimationInProgress = false;
        this.mShouldStartOnNextMoveEvent = false;
        this.mAnimationTimeoutRunnable = new BackAnimationController$$ExternalSyntheticLambda1(this, i);
        this.mTouchTracker = new TouchTracker();
        this.mAnimationDefinition = new SparseArray();
        this.mNavigationObserver = new RemoteCallback(new C37771());
        this.mBackAnimation = new BackAnimationImpl(this, i);
        this.mShellController = shellController;
        this.mShellExecutor = shellExecutor;
        this.mActivityTaskManager = iActivityTaskManager;
        this.mContext = context;
        this.mContentResolver = contentResolver;
        this.mBgHandler = handler;
        shellInit.addInitCallback(new BackAnimationController$$ExternalSyntheticLambda1(this, 1), this);
        this.mAnimationBackground = backAnimationBackground;
        FlingAnimationUtils.Builder builder = new FlingAnimationUtils.Builder(context.getResources().getDisplayMetrics());
        builder.mMaxLengthSeconds = 0.1f;
        builder.mSpeedUpFactor = 0.6f;
        this.mFlingAnimationUtils = builder.build();
    }
}
