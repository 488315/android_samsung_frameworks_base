package com.android.p038wm.shell.transition;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.WindowConfiguration;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.HardwareBuffer;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.RotationUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.window.TransitionInfo;
import android.window.TransitionMetrics;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.policy.TransitionAnimation;
import com.android.p038wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.TransactionPool;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.splitscreen.StageCoordinator;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.p038wm.shell.transition.DefaultTransitionHandler;
import com.android.p038wm.shell.transition.Transitions;
import com.android.p038wm.shell.transition.change.ChangeTransitionProvider;
import com.android.p038wm.shell.util.TransitionUtil;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DefaultTransitionHandler implements Transitions.TransitionHandler {
    public final ShellExecutor mAnimExecutor;
    public CapturedBlurHelper mCapturedBlurHelper;
    public ChangeTransitionProvider mChangeTransitProvider;
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public DimTransitionProvider mDimTransitionProvider;
    public final DisplayController mDisplayController;
    public Drawable mEnterpriseThumbnailDrawable;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public long mMaxRotationAnimationDuration;
    public MultiTaskingTransitionProvider mMultiTaskingTransitProvider;
    public final RootTaskDisplayAreaOrganizer mRootTDAOrganizer;
    public boolean mSkipMergeAnimation;
    public final TransactionPool mTransactionPool;
    public final TransitionAnimation mTransitionAnimation;
    public final SurfaceSession mSurfaceSession = new SurfaceSession();
    public final ArrayMap mAnimations = new ArrayMap();
    public final CounterRotatorHelper mRotator = new CounterRotatorHelper();
    public final Rect mInsets = new Rect(0, 0, 0, 0);
    public float mTransitionAnimationScaleSetting = 1.0f;
    public final C41421 mEnterpriseResourceUpdatedReceiver = new BroadcastReceiver() { // from class: com.android.wm.shell.transition.DefaultTransitionHandler.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("android.app.extra.RESOURCE_TYPE", -1) != 1) {
                return;
            }
            DefaultTransitionHandler defaultTransitionHandler = DefaultTransitionHandler.this;
            defaultTransitionHandler.mEnterpriseThumbnailDrawable = defaultTransitionHandler.mDevicePolicyManager.getResources().getDrawable("WORK_PROFILE_ICON", "OUTLINE", "PROFILE_SWITCH_ANIMATION", new DefaultTransitionHandler$$ExternalSyntheticLambda1(defaultTransitionHandler));
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CapturedBlurHelper {
        public Thread mBrThread;

        public /* synthetic */ CapturedBlurHelper(DefaultTransitionHandler defaultTransitionHandler, int i) {
            this();
        }

        private CapturedBlurHelper() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.wm.shell.transition.DefaultTransitionHandler$1] */
    public DefaultTransitionHandler(Context context, ShellInit shellInit, DisplayController displayController, TransactionPool transactionPool, ShellExecutor shellExecutor, Handler handler, ShellExecutor shellExecutor2, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        this.mDisplayController = displayController;
        this.mTransactionPool = transactionPool;
        this.mContext = context;
        this.mMainHandler = handler;
        this.mMainExecutor = shellExecutor;
        this.mAnimExecutor = shellExecutor2;
        this.mTransitionAnimation = new TransitionAnimation(context, false, "ShellTransitions");
        UserHandle.myUserId();
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        shellInit.addInitCallback(new DefaultTransitionHandler$$ExternalSyntheticLambda0(this, 0), this);
        this.mRootTDAOrganizer = rootTaskDisplayAreaOrganizer;
    }

    public static void applyTransformation(long j, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Animation animation, Transformation transformation, float[] fArr, Point point, float f, Rect rect, float f2) {
        transformation.clear();
        animation.getTransformation(j, transformation);
        if (f2 != 0.0f) {
            transformation.getMatrix().postScale(f2, f2);
        }
        if (point != null) {
            transformation.getMatrix().postTranslate(point.x, point.y);
        }
        transaction.setMatrix(surfaceControl, transformation.getMatrix(), fArr);
        transaction.setAlpha(surfaceControl, transformation.getAlpha());
        Rect rect2 = rect == null ? null : new Rect(rect);
        Insets min = Insets.min(transformation.getInsets(), Insets.NONE);
        if (!min.equals(Insets.NONE) && rect2 != null && !rect2.isEmpty()) {
            rect2.inset(min);
            transaction.setCrop(surfaceControl, rect2);
        }
        if (animation.hasRoundedCorners() && f > 0.0f && rect2 != null) {
            transaction.setCrop(surfaceControl, rect2);
            transaction.setCornerRadius(surfaceControl, f);
        }
        transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        transaction.apply();
    }

    public static void buildSurfaceAnimation(ArrayList arrayList, Animation animation, SurfaceControl surfaceControl, Runnable runnable, TransactionPool transactionPool, ShellExecutor shellExecutor, Point point, float f, Rect rect) {
        buildSurfaceAnimation(arrayList, animation, surfaceControl, runnable, transactionPool, shellExecutor, point, f, rect, 0.0f);
    }

    public static int getRotationAnimationHint(TransitionInfo.Change change, TransitionInfo transitionInfo, DisplayController displayController) {
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -583774195, 0, "Display is changing, resolve the animation hint.", null);
        }
        if (change.getRotationAnimation() == 3) {
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1787276183, 0, "  display requests explicit seamless", null);
            }
            return 3;
        }
        int size = transitionInfo.getChanges().size();
        ActivityManager.RunningTaskInfo runningTaskInfo = null;
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
            if (change2.getMode() != 6) {
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && change2.getTaskInfo() != null && change2.getTaskInfo().isAllowedSeamlessRotation) {
                    if (TransitionUtil.isTopApp(transitionInfo, change2, true)) {
                        change2.setRotationAnimation(3);
                    }
                }
                i++;
            }
            if (change2.getEndRotation() != change2.getStartRotation()) {
                if ((change2.getFlags() & 32) != 0) {
                    if ((change2.getFlags() & 128) != 0) {
                        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 42311280, 0, "  display has system alert windows, so not seamless.", null);
                        }
                        z2 = true;
                    }
                } else if ((2 & change2.getFlags()) != 0) {
                    if (change2.getRotationAnimation() != 3 && (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX || runningTaskInfo == null || !runningTaskInfo.isAllowedSeamlessRotation)) {
                        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1015274864, 0, "  wallpaper is participating but isn't seamless.", null);
                        }
                        z2 = true;
                    }
                } else if (change2.getTaskInfo() != null) {
                    int rotationAnimation = change2.getRotationAnimation();
                    ActivityManager.RunningTaskInfo taskInfo = change2.getTaskInfo();
                    boolean z3 = runningTaskInfo == null;
                    if (z3) {
                        if (rotationAnimation != -1 && rotationAnimation != 3) {
                            i2 = rotationAnimation;
                        }
                        runningTaskInfo = taskInfo;
                    }
                    if (rotationAnimation != 3) {
                        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1915000700, 0, "  task %s isn't requesting seamless, so not seamless.", String.valueOf(taskInfo.taskId));
                        }
                        z = false;
                    } else if (z3) {
                        z = true;
                    }
                }
            }
            i++;
        }
        if (z && !z2) {
            if (runningTaskInfo.isAllowedSeamlessRotation) {
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 749199612, 0, "  top activity has meta data, so allows seamless.", null);
                }
                return 3;
            }
            DisplayLayout displayLayout = displayController.getDisplayLayout(runningTaskInfo.displayId);
            if (displayLayout.mAllowSeamlessRotationDespiteNavBarMoving) {
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 967922481, 0, "  nav bar allows seamless.", null);
                }
                return 3;
            }
            boolean z4 = displayLayout.mWidth > displayLayout.mHeight;
            if (displayLayout.mRotation % 2 != 0) {
                z4 = !z4;
            }
            int i3 = z4 ? displayLayout.mReverseDefaultRotation ? 3 : 1 : 2;
            if (change.getStartRotation() != i3 && change.getEndRotation() != i3) {
                if (displayLayout.mNavigationBarCanMove && displayLayout.mWidth != displayLayout.mHeight) {
                    if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1215677233, 0, "  Rotation IS seamless.", null);
                    }
                    return 3;
                }
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1167654715, 0, "  nav bar changes sides, so not seamless.", null);
                }
                return i2;
            }
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1167817788, 0, "  rotation involves upside-down portrait, so not seamless.", null);
            }
        }
        return i2;
    }

    public final void attachThumbnail(ArrayList arrayList, DefaultTransitionHandler$$ExternalSyntheticLambda5 defaultTransitionHandler$$ExternalSyntheticLambda5, TransitionInfo.Change change, TransitionInfo.AnimationOptions animationOptions, float f) {
        TransitionAnimation transitionAnimation;
        HardwareBuffer createCrossProfileAppsThumbnail;
        boolean isOpeningType = TransitionUtil.isOpeningType(change.getMode());
        boolean isClosingType = TransitionUtil.isClosingType(change.getMode());
        if (!isOpeningType) {
            if (isClosingType && animationOptions.getType() == 4) {
                attachThumbnailAnimation(arrayList, defaultTransitionHandler$$ExternalSyntheticLambda5, change, animationOptions, f);
                return;
            }
            return;
        }
        if (animationOptions.getType() != 12) {
            if (animationOptions.getType() == 3) {
                attachThumbnailAnimation(arrayList, defaultTransitionHandler$$ExternalSyntheticLambda5, change, animationOptions, f);
                return;
            }
            return;
        }
        Rect endAbsBounds = change.getEndAbsBounds();
        Drawable drawable = change.hasFlags(4096) ? this.mContext.getDrawable(R.drawable.grid_selector_background_focus) : change.hasFlags(8192) ? this.mEnterpriseThumbnailDrawable : null;
        if (drawable == null || (createCrossProfileAppsThumbnail = (transitionAnimation = this.mTransitionAnimation).createCrossProfileAppsThumbnail(drawable, endAbsBounds)) == null) {
            return;
        }
        SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
        WindowThumbnail createAndAttach = WindowThumbnail.createAndAttach(this.mSurfaceSession, change.getLeash(), createCrossProfileAppsThumbnail, acquire);
        Animation createCrossProfileAppsThumbnailAnimationLocked = transitionAnimation.createCrossProfileAppsThumbnailAnimationLocked(endAbsBounds);
        if (createCrossProfileAppsThumbnailAnimationLocked == null) {
            return;
        }
        DefaultTransitionHandler$$ExternalSyntheticLambda5 defaultTransitionHandler$$ExternalSyntheticLambda52 = new DefaultTransitionHandler$$ExternalSyntheticLambda5(this, createAndAttach, acquire, defaultTransitionHandler$$ExternalSyntheticLambda5, 1);
        createCrossProfileAppsThumbnailAnimationLocked.restrictDuration(3000L);
        createCrossProfileAppsThumbnailAnimationLocked.scaleCurrentDuration(this.mTransitionAnimationScaleSetting);
        buildSurfaceAnimation(arrayList, createCrossProfileAppsThumbnailAnimationLocked, createAndAttach.mSurfaceControl, defaultTransitionHandler$$ExternalSyntheticLambda52, this.mTransactionPool, this.mMainExecutor, change.getEndRelOffset(), f, change.getEndAbsBounds());
    }

    public final void attachThumbnailAnimation(ArrayList arrayList, DefaultTransitionHandler$$ExternalSyntheticLambda5 defaultTransitionHandler$$ExternalSyntheticLambda5, TransitionInfo.Change change, TransitionInfo.AnimationOptions animationOptions, float f) {
        SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
        WindowThumbnail createAndAttach = WindowThumbnail.createAndAttach(this.mSurfaceSession, change.getLeash(), animationOptions.getThumbnail(), acquire);
        Animation createThumbnailAspectScaleAnimationLocked = this.mTransitionAnimation.createThumbnailAspectScaleAnimationLocked(change.getEndAbsBounds(), this.mInsets, animationOptions.getThumbnail(), this.mContext.getResources().getConfiguration().orientation, (Rect) null, animationOptions.getTransitionBounds(), animationOptions.getType() == 3);
        DefaultTransitionHandler$$ExternalSyntheticLambda5 defaultTransitionHandler$$ExternalSyntheticLambda52 = new DefaultTransitionHandler$$ExternalSyntheticLambda5(this, createAndAttach, acquire, defaultTransitionHandler$$ExternalSyntheticLambda5, 0);
        createThumbnailAspectScaleAnimationLocked.restrictDuration(3000L);
        createThumbnailAspectScaleAnimationLocked.scaleCurrentDuration(this.mTransitionAnimationScaleSetting);
        buildSurfaceAnimation(arrayList, createThumbnailAspectScaleAnimationLocked, createAndAttach.mSurfaceControl, defaultTransitionHandler$$ExternalSyntheticLambda52, this.mTransactionPool, this.mMainExecutor, change.getEndRelOffset(), f, change.getEndAbsBounds());
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void beforeMergeAnimation(IBinder iBinder, Transitions.TransitionHandler transitionHandler) {
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && (transitionHandler instanceof StageCoordinator)) {
            this.mSkipMergeAnimation = ((StageCoordinator) transitionHandler).mSplitTransitions.isPendingResize(iBinder);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (CoreRune.MW_SHELL_TRANSITION && this.mSkipMergeAnimation) {
            this.mSkipMergeAnimation = false;
            return;
        }
        ArrayList arrayList = (ArrayList) this.mAnimations.get(iBinder2);
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Animator animator = (Animator) arrayList.get(size);
            Objects.requireNonNull(animator);
            ((HandlerExecutor) this.mAnimExecutor).execute(new DefaultTransitionHandler$$ExternalSyntheticLambda0(animator, 1));
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void setAnimScaleSetting(float f) {
        this.mTransitionAnimationScaleSetting = f;
        if (CoreRune.MW_SHELL_TRANSITION) {
            MultiTaskingTransitionProvider multiTaskingTransitionProvider = this.mMultiTaskingTransitProvider;
            if (multiTaskingTransitionProvider.mDurationScale != f) {
                Log.d("MultiTaskingTransitionProvider", "setAnimScaleSetting: " + multiTaskingTransitionProvider.mDurationScale + "->" + f);
                multiTaskingTransitionProvider.mDurationScale = f;
            }
        }
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            ChangeTransitionProvider changeTransitionProvider = this.mChangeTransitProvider;
            if (changeTransitionProvider.mDurationScale != f) {
                Log.d("ChangeTransitionProvider", "setAnimScaleSetting: " + changeTransitionProvider.mDurationScale + "->" + f);
                changeTransitionProvider.mDurationScale = f;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:161:0x02c2, code lost:
    
        if ((r2.isFadeInOutRotationNeeded() || ((com.samsung.android.rune.CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && (r2.getFlags() & com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE) != 0) || (com.samsung.android.rune.CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && r2.getStartRotation() != r2.getEndRotation()))) != false) goto L181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x035d, code lost:
    
        if (r40.mChangeTransitProvider.buildChangeTransitionAnimators(r15, r6, r14, r43, r42) != false) goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:241:0x04d6, code lost:
    
        if (r1 == false) goto L297;
     */
    /* JADX WARN: Code restructure failed: missing block: B:547:0x097f, code lost:
    
        if (r1 != false) goto L575;
     */
    /* JADX WARN: Removed duplicated region for block: B:168:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0346  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x053f  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x055b  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x058f  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x05a6  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0688  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x0876  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x087c  */
    /* JADX WARN: Removed duplicated region for block: B:417:0x08b1  */
    /* JADX WARN: Removed duplicated region for block: B:559:0x0b51  */
    /* JADX WARN: Removed duplicated region for block: B:561:0x05b3  */
    /* JADX WARN: Removed duplicated region for block: B:614:0x059c  */
    /* JADX WARN: Removed duplicated region for block: B:616:0x057f  */
    /* JADX WARN: Removed duplicated region for block: B:617:0x054f  */
    /* JADX WARN: Removed duplicated region for block: B:618:0x0542  */
    /* JADX WARN: Removed duplicated region for block: B:633:0x0486  */
    /* JADX WARN: Removed duplicated region for block: B:634:0x0361  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        boolean z;
        boolean z2;
        boolean z3;
        int i;
        boolean z4;
        boolean z5;
        int i2;
        boolean z6;
        int i3;
        CounterRotatorHelper counterRotatorHelper;
        SurfaceControl.Transaction transaction3;
        ArrayList arrayList;
        int i4;
        SurfaceControl.Transaction transaction4;
        boolean z7;
        boolean z8;
        ArrayList arrayList2;
        ArrayList arrayList3;
        boolean z9;
        DefaultTransitionHandler$$ExternalSyntheticLambda5 defaultTransitionHandler$$ExternalSyntheticLambda5;
        SurfaceControl.Transaction transaction5;
        SurfaceControl.Transaction transaction6;
        int i5;
        boolean z10;
        SurfaceControl.Transaction transaction7;
        TransitionInfo.Change change;
        DisplayController displayController;
        String str;
        TransitionInfo.Change change2;
        int i6;
        final TransitionInfo.Change change3;
        int i7;
        int mode;
        int i8;
        String str2;
        Rect endAbsBounds;
        boolean z11;
        CounterRotatorHelper counterRotatorHelper2;
        Animation createThumbnailEnterExitAnimationLocked;
        final Animation animation;
        final SurfaceControl.Transaction transaction8;
        SurfaceControl.Transaction transaction9;
        int i9;
        Context displayContext;
        CounterRotatorHelper counterRotatorHelper3;
        ArrayList arrayList4;
        Rect rect;
        Rect rect2;
        Rect rect3;
        DefaultTransitionHandler$$ExternalSyntheticLambda5 defaultTransitionHandler$$ExternalSyntheticLambda52;
        ArrayList arrayList5;
        TransitionInfo.Change change4;
        Animation loadDefaultAnimationRes;
        int i10;
        boolean z12;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        TransitionInfo.Change change5;
        boolean z13;
        TransitionInfo transitionInfo2 = transitionInfo;
        SurfaceControl.Transaction transaction10 = transaction;
        int i11 = 0;
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -146110597, 0, "start default transition animation, info = %s", String.valueOf(transitionInfo));
        }
        TransitionInfo.Change change6 = null;
        if (transitionInfo.getType() == 11 && !transitionInfo.isKeyguardGoingAway()) {
            transaction.apply();
            transitionFinishCallback.onTransitionFinished(null, null);
            return true;
        }
        boolean z14 = Transitions.ENABLE_SHELL_TRANSITIONS;
        if (!TransitionUtil.isClosingType(transitionInfo.getType())) {
            z = false;
            for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, 1); m136m >= 0; m136m--) {
                TransitionInfo.Change change7 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
                if (!TransitionUtil.isClosingType(change7.getMode())) {
                    if (change7.hasFlags(262144)) {
                        z = true;
                    }
                }
            }
            if (!z) {
                int m136m2 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, 1);
                while (true) {
                    if (m136m2 < 0) {
                        z2 = true;
                        break;
                    }
                    if (!TransitionUtil.isOrderOnly((TransitionInfo.Change) transitionInfo.getChanges().get(m136m2))) {
                        z2 = false;
                        break;
                    }
                    m136m2--;
                }
                if (!z2 && (transitionInfo.getFlags() & 1024) == 0 && (!CoreRune.MW_SPLIT_SHELL_TRANSITION || !Transitions.hasDuplicatedOpenTypeChanges(transitionInfo))) {
                    ArrayMap arrayMap = this.mAnimations;
                    if (arrayMap.containsKey(iBinder)) {
                        throw new IllegalStateException("Got a duplicate startAnimation call for " + iBinder);
                    }
                    ArrayList arrayList6 = new ArrayList();
                    arrayMap.put(iBinder, arrayList6);
                    DefaultTransitionHandler$$ExternalSyntheticLambda5 defaultTransitionHandler$$ExternalSyntheticLambda53 = new DefaultTransitionHandler$$ExternalSyntheticLambda5(this, arrayList6, iBinder, transitionFinishCallback, 2);
                    ArrayList arrayList7 = new ArrayList();
                    boolean z15 = false;
                    boolean z16 = false;
                    TransitionInfo.Change change8 = null;
                    for (int m136m3 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, 1); m136m3 >= 0; m136m3--) {
                        TransitionInfo.Change change9 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m3);
                        if ((change9.getFlags() & 1) != 0) {
                            if (TransitionUtil.isOpeningType(change9.getMode())) {
                                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && TransitionUtil.isTopApp(transitionInfo2, change9, true)) {
                                    change6 = change9;
                                }
                                z15 = true;
                            } else if (TransitionUtil.isClosingType(change9.getMode())) {
                                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && TransitionUtil.isTopApp(transitionInfo2, change9, false)) {
                                    change8 = change9;
                                }
                                z16 = true;
                            }
                        }
                    }
                    int i12 = (z15 && z16) ? TransitionUtil.isOpeningType(transitionInfo.getType()) ? 3 : 4 : (!z15 || (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && change6 == null)) ? (!z16 || (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && change8 == null)) ? 0 : 2 : 1;
                    int m136m4 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, 1);
                    while (true) {
                        if (m136m4 < 0) {
                            z3 = false;
                            break;
                        }
                        TransitionInfo.Change change10 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m4);
                        if (change10.getTaskInfo() != null && change10.getTaskInfo().topActivityType == 5) {
                            z3 = true;
                            break;
                        }
                        m136m4--;
                    }
                    int m136m5 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, 1);
                    int i13 = 0;
                    while (true) {
                        if (m136m5 >= 0) {
                            TransitionInfo.Change change11 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m5);
                            if (change11.getMode() != 6) {
                                i = 4;
                                if (!change11.hasFlags(4)) {
                                    break;
                                }
                                if (TransitionUtil.isOpeningType(change11.getMode())) {
                                    i11++;
                                } else {
                                    i13++;
                                }
                            }
                            m136m5--;
                        } else {
                            i = 4;
                            if (i11 + i13 > 0) {
                                z4 = true;
                            }
                        }
                    }
                    z4 = false;
                    boolean z17 = z4;
                    if (TransitionUtil.isOpeningType(transitionInfo.getType())) {
                        for (int m136m6 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, 1); m136m6 >= 0; m136m6--) {
                            TransitionInfo.Change change12 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m6);
                            if (TransitionUtil.isOpeningType(change12.getMode()) && (change12.getPopOverAnimationNeeded() || change12.getConfiguration().windowConfiguration.isPopOver())) {
                                i2 = 1;
                                z5 = true;
                                break;
                            }
                        }
                    }
                    z5 = false;
                    i2 = 1;
                    int m136m7 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, i2);
                    while (true) {
                        if (m136m7 < 0) {
                            z6 = false;
                            i3 = 1;
                            break;
                        }
                        if (TransitionUtil.isOpeningType(((TransitionInfo.Change) transitionInfo.getChanges().get(m136m7)).getMode())) {
                            i3 = 1;
                            z6 = true;
                            break;
                        }
                        m136m7--;
                    }
                    boolean z18 = false;
                    int i14 = 0;
                    boolean z19 = false;
                    TransitionInfo transitionInfo3 = transitionInfo2;
                    SurfaceControl.Transaction transaction11 = transaction10;
                    int i15 = 2;
                    int m136m8 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, i3);
                    SurfaceControl.Transaction transaction12 = transaction2;
                    while (true) {
                        counterRotatorHelper = this.mRotator;
                        if (m136m8 < 0) {
                            break;
                        }
                        SurfaceControl.Transaction transaction13 = transaction12;
                        TransitionInfo.Change change13 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m8);
                        boolean z20 = z3;
                        if (!change13.hasAllFlags(16896)) {
                            if (!change13.hasFlags(65794)) {
                                boolean z21 = change13.getTaskInfo() != null;
                                int mode2 = change13.getMode();
                                boolean z22 = CoreRune.MW_FREEFORM_SHELL_TRANSITION && z21 && change13.getTaskInfo().isFreeform();
                                DisplayController displayController2 = this.mDisplayController;
                                if (mode2 == 6 && change13.hasFlags(32)) {
                                    i5 = mode2;
                                    if (transitionInfo.getType() != 6) {
                                        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION) {
                                        }
                                        counterRotatorHelper.handleClosingChanges(transaction11, change13, transitionInfo3);
                                        transaction3 = transaction11;
                                    }
                                    int rotationAnimationHint = getRotationAnimationHint(change13, transitionInfo3, displayController2);
                                    boolean z23 = rotationAnimationHint == 3;
                                    z10 = z23;
                                    if (z23 || rotationAnimationHint == 2) {
                                        transaction3 = transaction11;
                                        arrayList = arrayList7;
                                        i4 = m136m8;
                                        transaction7 = transaction13;
                                        change = change13;
                                        z7 = z17;
                                        z8 = z20;
                                        if (CoreRune.MW_SHELL_TRANSITION) {
                                            this.mMultiTaskingTransitProvider.getClass();
                                            if (change.shouldSkipDefaultTransition()) {
                                                Log.d("MultiTaskingTransitionProvider", "canSkipDefaultTransition: c=" + change);
                                                z13 = true;
                                            } else {
                                                z13 = false;
                                            }
                                            if (z13) {
                                                transaction4 = transaction7;
                                                transaction6 = transaction4;
                                                i = 4;
                                                transaction5 = transaction;
                                                transaction11 = transaction3;
                                                transaction12 = transaction6;
                                                z9 = z8;
                                                defaultTransitionHandler$$ExternalSyntheticLambda5 = defaultTransitionHandler$$ExternalSyntheticLambda53;
                                                arrayList2 = arrayList6;
                                                arrayList3 = arrayList;
                                                transitionInfo2 = transitionInfo;
                                                i15 = 2;
                                                transaction10 = transaction5;
                                                defaultTransitionHandler$$ExternalSyntheticLambda53 = defaultTransitionHandler$$ExternalSyntheticLambda5;
                                                arrayList7 = arrayList3;
                                                arrayList6 = arrayList2;
                                                z17 = z7;
                                                m136m8 = i4 - 1;
                                                z3 = z9;
                                            }
                                        }
                                        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                                            displayController = displayController2;
                                            str = "MultiTaskingTransitionProvider";
                                            change2 = change;
                                            transaction4 = transaction7;
                                            i6 = i5;
                                        } else {
                                            transaction4 = transaction7;
                                            str = "MultiTaskingTransitionProvider";
                                            displayController = displayController2;
                                            i6 = i5;
                                            change2 = change;
                                        }
                                        if (i6 != 6) {
                                            if (z21 && change2.getParent() != null && transitionInfo3.getChange(change2.getParent()).getTaskInfo() != null) {
                                                Point point = change2.getTaskInfo().positionInParent;
                                                transaction3.setPosition(change2.getLeash(), point.x, point.y);
                                                if (!change2.getEndAbsBounds().equals(transitionInfo3.getChange(change2.getParent()).getEndAbsBounds())) {
                                                    transaction3.setWindowCrop(change2.getLeash(), change2.getEndAbsBounds().width(), change2.getEndAbsBounds().height());
                                                }
                                            } else if (!z21 || change2.getTaskInfo().configuration.windowConfiguration.getWindowingMode() != 2) {
                                                change3 = change2;
                                                if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION && change3.hasFlags(512) && !change3.hasFlags(1024)) {
                                                    z18 = true;
                                                } else {
                                                    int rootIndexFor = TransitionUtil.rootIndexFor(change3, transitionInfo3);
                                                    transaction3.setPosition(change3.getLeash(), change3.getEndAbsBounds().left - transitionInfo3.getRoot(rootIndexFor).getOffset().x, change3.getEndAbsBounds().top - transitionInfo3.getRoot(rootIndexFor).getOffset().y);
                                                }
                                                if (!z10) {
                                                    if ((z21 || (change3.hasFlags(512) && !change3.hasFlags(1024))) && (!CoreRune.MW_FREEFORM_SHELL_TRANSITION || !z22)) {
                                                        transaction3.setWindowCrop(change3.getLeash(), change3.getEndAbsBounds().width(), change3.getEndAbsBounds().height());
                                                    }
                                                    if (change3.getParent() != null || change3.getStartRotation() == change3.getEndRotation()) {
                                                        transaction12 = transaction4;
                                                    } else {
                                                        transaction6 = transaction4;
                                                        startRotationAnimation(transaction, change3, transitionInfo, 0, arrayList6, defaultTransitionHandler$$ExternalSyntheticLambda53);
                                                        i = 4;
                                                        transaction5 = transaction;
                                                        transaction11 = transaction3;
                                                        transaction12 = transaction6;
                                                        z9 = z8;
                                                        defaultTransitionHandler$$ExternalSyntheticLambda5 = defaultTransitionHandler$$ExternalSyntheticLambda53;
                                                        arrayList2 = arrayList6;
                                                        arrayList3 = arrayList;
                                                        transitionInfo2 = transitionInfo;
                                                        i15 = 2;
                                                        transaction10 = transaction5;
                                                        defaultTransitionHandler$$ExternalSyntheticLambda53 = defaultTransitionHandler$$ExternalSyntheticLambda5;
                                                        arrayList7 = arrayList3;
                                                        arrayList6 = arrayList2;
                                                        z17 = z7;
                                                        m136m8 = i4 - 1;
                                                        z3 = z9;
                                                    }
                                                }
                                            }
                                            transaction6 = transaction4;
                                            i = 4;
                                            transaction5 = transaction;
                                            transaction11 = transaction3;
                                            transaction12 = transaction6;
                                            z9 = z8;
                                            defaultTransitionHandler$$ExternalSyntheticLambda5 = defaultTransitionHandler$$ExternalSyntheticLambda53;
                                            arrayList2 = arrayList6;
                                            arrayList3 = arrayList;
                                            transitionInfo2 = transitionInfo;
                                            i15 = 2;
                                            transaction10 = transaction5;
                                            defaultTransitionHandler$$ExternalSyntheticLambda53 = defaultTransitionHandler$$ExternalSyntheticLambda5;
                                            arrayList7 = arrayList3;
                                            arrayList6 = arrayList2;
                                            z17 = z7;
                                            m136m8 = i4 - 1;
                                            z3 = z9;
                                        } else {
                                            transaction12 = transaction4;
                                            change3 = change2;
                                        }
                                        boolean z24 = z18;
                                        if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION || !change3.hasFlags(512) || z19) {
                                            i7 = 4;
                                        } else if (i6 == 6 || change3.hasFlags(1024)) {
                                            i7 = 4;
                                            if (i6 == 3 && change3.hasFlags(1024)) {
                                                transaction3.setWindowCrop(change3.getLeash(), -1, -1);
                                            }
                                        } else {
                                            if (TransitionUtil.isClosingType(i6) && z24) {
                                                transaction3.hide(change3.getLeash());
                                            }
                                            TransitionInfo.AnimationOptions animationOptions = transitionInfo.getAnimationOptions();
                                            if (animationOptions != null) {
                                                int type = animationOptions.getType();
                                                if (type == 1 || type == 2 || type == 3) {
                                                    i7 = 4;
                                                } else {
                                                    i7 = 4;
                                                    boolean z25 = type == 4 || type == 11 || type == 12;
                                                }
                                            } else {
                                                i7 = 4;
                                            }
                                            transaction5 = transaction;
                                            transaction9 = transaction3;
                                            z9 = z8;
                                            defaultTransitionHandler$$ExternalSyntheticLambda5 = defaultTransitionHandler$$ExternalSyntheticLambda53;
                                            arrayList2 = arrayList6;
                                            arrayList3 = arrayList;
                                            transitionInfo2 = transitionInfo;
                                            z18 = z24;
                                            int i16 = i7;
                                            transaction11 = transaction9;
                                            i = i16;
                                            i15 = 2;
                                            transaction10 = transaction5;
                                            defaultTransitionHandler$$ExternalSyntheticLambda53 = defaultTransitionHandler$$ExternalSyntheticLambda5;
                                            arrayList7 = arrayList3;
                                            arrayList6 = arrayList2;
                                            z17 = z7;
                                            m136m8 = i4 - 1;
                                            z3 = z9;
                                        }
                                        if (!z19 && TransitionUtil.isClosingType(i6)) {
                                            transaction3.hide(change3.getLeash());
                                        } else if (!change3.hasFlags(131072) && TransitionInfo.isIndependent(change3, transitionInfo3)) {
                                            int type2 = transitionInfo.getType();
                                            int flags = transitionInfo.getFlags();
                                            mode = change3.getMode();
                                            int flags2 = change3.getFlags();
                                            boolean isOpeningType = TransitionUtil.isOpeningType(type2);
                                            boolean isOpeningType2 = TransitionUtil.isOpeningType(mode);
                                            boolean z26 = change3.getTaskInfo() == null;
                                            TransitionInfo.AnimationOptions animationOptions2 = transitionInfo.getAnimationOptions();
                                            DefaultTransitionHandler$$ExternalSyntheticLambda5 defaultTransitionHandler$$ExternalSyntheticLambda54 = defaultTransitionHandler$$ExternalSyntheticLambda53;
                                            int type3 = animationOptions2 == null ? animationOptions2.getType() : 0;
                                            if (TransitionUtil.isClosingType(mode)) {
                                                i8 = i6;
                                                str2 = str;
                                                arrayList2 = arrayList6;
                                                endAbsBounds = change3.getEndAbsBounds();
                                            } else {
                                                arrayList2 = arrayList6;
                                                if (counterRotatorHelper.mLastRotationDelta == 0) {
                                                    endAbsBounds = change3.getEndAbsBounds();
                                                    i8 = i6;
                                                    str2 = str;
                                                } else {
                                                    i8 = i6;
                                                    endAbsBounds = new Rect(change3.getEndAbsBounds());
                                                    str2 = str;
                                                    RotationUtils.rotateBounds(endAbsBounds, counterRotatorHelper.mLastDisplayBounds, counterRotatorHelper.mLastRotationDelta);
                                                }
                                            }
                                            z11 = CoreRune.FW_LARGE_FLIP_CUSTOM_SHELL_TRANSITION;
                                            TransitionAnimation transitionAnimation = this.mTransitionAnimation;
                                            if (z11) {
                                                counterRotatorHelper2 = counterRotatorHelper;
                                            } else {
                                                counterRotatorHelper2 = counterRotatorHelper;
                                                if (change3.getEndDisplayId() == 1) {
                                                    transitionAnimation.overrideDisplayId(1);
                                                }
                                            }
                                            if (!transitionInfo.isKeyguardGoingAway()) {
                                                createThumbnailEnterExitAnimationLocked = transitionAnimation.loadKeyguardExitAnimation(flags, (flags2 & 1) != 0);
                                            } else if (type2 == 9) {
                                                createThumbnailEnterExitAnimationLocked = transitionAnimation.loadKeyguardUnoccludeAnimation();
                                            } else if ((flags2 & 16) != 0) {
                                                createThumbnailEnterExitAnimationLocked = isOpeningType ? transitionAnimation.loadVoiceActivityOpenAnimation(isOpeningType2) : transitionAnimation.loadVoiceActivityExitAnimation(isOpeningType2);
                                            } else if (mode == 6 && !(CoreRune.FW_CUSTOM_SHELL_TRANSITION_RESUMED_AFFORDANCE && change3.getResumedAffordance())) {
                                                createThumbnailEnterExitAnimationLocked = new AlphaAnimation(1.0f, 1.0f);
                                                createThumbnailEnterExitAnimationLocked.setDuration(336L);
                                            } else if (type2 == 5) {
                                                createThumbnailEnterExitAnimationLocked = transitionAnimation.createRelaunchAnimation(endAbsBounds, this.mInsets, endAbsBounds);
                                            } else if (type3 == 1 && (!z26 || animationOptions2.getOverrideTaskTransition())) {
                                                createThumbnailEnterExitAnimationLocked = transitionAnimation.loadAnimationRes(animationOptions2.getPackageName(), isOpeningType2 ? animationOptions2.getEnterResId() : animationOptions2.getExitResId());
                                            } else if (type3 == 12 && isOpeningType2) {
                                                createThumbnailEnterExitAnimationLocked = transitionAnimation.loadCrossProfileAppEnterAnimation();
                                            } else if (type3 == 11) {
                                                createThumbnailEnterExitAnimationLocked = transitionAnimation.createClipRevealAnimationLocked(type2, i12, isOpeningType2, endAbsBounds, endAbsBounds, animationOptions2.getTransitionBounds());
                                            } else if (type3 == 2) {
                                                createThumbnailEnterExitAnimationLocked = transitionAnimation.createScaleUpAnimationLocked(type2, i12, isOpeningType2, endAbsBounds, animationOptions2.getTransitionBounds());
                                            } else if (type3 == 3 || type3 == 4) {
                                                createThumbnailEnterExitAnimationLocked = transitionAnimation.createThumbnailEnterExitAnimationLocked(isOpeningType2, type3 == 3, endAbsBounds, type2, i12, animationOptions2.getThumbnail(), animationOptions2.getTransitionBounds());
                                            } else if (((flags2 & 8) == 0 || !isOpeningType) && type3 != 5) {
                                                createThumbnailEnterExitAnimationLocked = TransitionAnimationHelper.loadAttributeAnimation(transitionInfo3, change3, i12, transitionAnimation, z8);
                                            } else {
                                                createThumbnailEnterExitAnimationLocked = null;
                                                animation = createThumbnailEnterExitAnimationLocked;
                                                if (animation != null) {
                                                    if (z21) {
                                                        if ((change3.getFlags() & 4) != 0) {
                                                            i9 = i8;
                                                        } else {
                                                            i9 = i8;
                                                            if (i9 == 1 || i9 == 2 || i9 == 3 || i9 == 4) {
                                                                int type4 = transitionInfo.getType();
                                                                if ((type4 == 1 || type4 == 2 || type4 == 3 || type4 == 4) && i12 == 0) {
                                                                    i14 = ActivityThread.currentActivityThread().getSystemUiContext().getColor(R.color.ratingbar_background_material);
                                                                }
                                                            }
                                                        }
                                                        if (i12 == 1 && TransitionUtil.isOpeningType(transitionInfo.getType())) {
                                                            int size = transitionInfo.getChanges().size();
                                                            int i17 = size + 1;
                                                            if (TransitionUtil.isOpeningType(i9)) {
                                                                transaction5 = transaction;
                                                                transaction5.setLayer(change3.getLeash(), i17 - i4);
                                                            } else {
                                                                transaction5 = transaction;
                                                                if (TransitionUtil.isClosingType(i9)) {
                                                                    transaction5.setLayer(change3.getLeash(), (i17 + size) - i4);
                                                                }
                                                            }
                                                        } else {
                                                            transaction5 = transaction;
                                                            if ((z7 || (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && !z6)) && TransitionUtil.isOpeningType(transitionInfo.getType()) && TransitionUtil.isClosingType(i9)) {
                                                                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                                                                    transitionInfo2 = transitionInfo;
                                                                    int m136m9 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, 1);
                                                                    while (true) {
                                                                        if (m136m9 < 0) {
                                                                            z12 = false;
                                                                            break;
                                                                        }
                                                                        TransitionInfo.Change change14 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m9);
                                                                        if (change14.hasFlags(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) && TransitionUtil.isHomeOrRecents(change14)) {
                                                                            z12 = true;
                                                                            break;
                                                                        }
                                                                        m136m9--;
                                                                    }
                                                                } else {
                                                                    transitionInfo2 = transitionInfo;
                                                                }
                                                                int size2 = transitionInfo.getChanges().size();
                                                                transaction5.setLayer(change3.getLeash(), ((size2 + 1) + size2) - i4);
                                                            }
                                                        }
                                                        transitionInfo2 = transitionInfo;
                                                    } else {
                                                        transitionInfo2 = transitionInfo;
                                                        transaction5 = transaction;
                                                        i9 = i8;
                                                    }
                                                    int i18 = i14;
                                                    float roundedCornerRadius = (CoreRune.MW_SHELL_TRANSITION && animation.hasRoundedCornerRadius()) ? animation.getRoundedCornerRadius() : (animation.hasRoundedCorners() && z21 && (displayContext = displayController.getDisplayContext(change3.getTaskInfo().displayId)) != null) ? ScreenDecorationsUtils.getWindowCornerRadius(displayContext) : 0.0f;
                                                    i14 = TransitionAnimationHelper.getTransitionBackgroundColorIfSet(transitionInfo2, change3, animation, i18);
                                                    if ((z21 && (!CoreRune.FW_CUSTOM_SHELL_TRANSITION || animation.hasRoundedCorners())) || !animation.hasExtension()) {
                                                        transaction8 = transaction2;
                                                        arrayList4 = arrayList;
                                                        counterRotatorHelper3 = counterRotatorHelper2;
                                                    } else if (TransitionUtil.isOpeningType(i9)) {
                                                        transaction8 = transaction2;
                                                        counterRotatorHelper3 = counterRotatorHelper2;
                                                        arrayList4 = arrayList;
                                                        arrayList4.add(new Consumer() { // from class: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda2
                                                            @Override // java.util.function.Consumer
                                                            public final void accept(Object obj) {
                                                                TransitionAnimationHelper.edgeExtendWindow(change3, animation, (SurfaceControl.Transaction) obj, transaction8);
                                                            }
                                                        });
                                                    } else {
                                                        transaction8 = transaction2;
                                                        counterRotatorHelper3 = counterRotatorHelper2;
                                                        TransitionAnimationHelper.edgeExtendWindow(change3, animation, transaction5, transaction8);
                                                        arrayList4 = arrayList;
                                                    }
                                                    if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && z22) {
                                                        rect2 = null;
                                                    } else {
                                                        if (TransitionUtil.isClosingType(i9)) {
                                                            if (counterRotatorHelper3.mLastRotationDelta == 0) {
                                                                rect3 = change3.getEndAbsBounds();
                                                            } else {
                                                                Rect rect4 = new Rect(change3.getEndAbsBounds());
                                                                RotationUtils.rotateBounds(rect4, counterRotatorHelper3.mLastDisplayBounds, counterRotatorHelper3.mLastRotationDelta);
                                                                rect3 = rect4;
                                                            }
                                                            rect = new Rect(rect3);
                                                        } else {
                                                            rect = new Rect(change3.getEndAbsBounds());
                                                        }
                                                        rect.offsetTo(0, 0);
                                                        rect2 = rect;
                                                    }
                                                    if (change3.getFreeformStashScale() <= 0.0f || change3.getFreeformStashScale() >= 1.0f || animation.willChangeTransformationMatrix()) {
                                                        defaultTransitionHandler$$ExternalSyntheticLambda52 = defaultTransitionHandler$$ExternalSyntheticLambda54;
                                                        arrayList5 = arrayList2;
                                                        buildSurfaceAnimation(arrayList5, animation, change3.getLeash(), defaultTransitionHandler$$ExternalSyntheticLambda52, this.mTransactionPool, this.mMainExecutor, change3.getEndRelOffset(), roundedCornerRadius, rect2);
                                                    } else {
                                                        float freeformStashScale = change3.getFreeformStashScale();
                                                        Point endRelOffset = change3.getEndRelOffset();
                                                        if (z21 && change3.getTaskInfo() != null) {
                                                            endRelOffset = change3.getTaskInfo().positionInParent;
                                                        }
                                                        defaultTransitionHandler$$ExternalSyntheticLambda52 = defaultTransitionHandler$$ExternalSyntheticLambda54;
                                                        arrayList5 = arrayList2;
                                                        buildSurfaceAnimation(arrayList2, animation, change3.getLeash(), defaultTransitionHandler$$ExternalSyntheticLambda52, this.mTransactionPool, this.mMainExecutor, endRelOffset, roundedCornerRadius, rect2, freeformStashScale);
                                                    }
                                                    if (transitionInfo.getAnimationOptions() != null) {
                                                        arrayList2 = arrayList5;
                                                        z9 = z8;
                                                        defaultTransitionHandler$$ExternalSyntheticLambda5 = defaultTransitionHandler$$ExternalSyntheticLambda52;
                                                        arrayList3 = arrayList4;
                                                        change4 = change3;
                                                        attachThumbnail(arrayList5, defaultTransitionHandler$$ExternalSyntheticLambda52, change3, transitionInfo.getAnimationOptions(), roundedCornerRadius);
                                                        i7 = 4;
                                                    } else {
                                                        arrayList2 = arrayList5;
                                                        arrayList3 = arrayList4;
                                                        change4 = change3;
                                                        z9 = z8;
                                                        defaultTransitionHandler$$ExternalSyntheticLambda5 = defaultTransitionHandler$$ExternalSyntheticLambda52;
                                                        i7 = 4;
                                                    }
                                                    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_WITH_DIM && change4.isTransitionWithDim()) {
                                                        DimTransitionProvider dimTransitionProvider = this.mDimTransitionProvider;
                                                        float f = this.mTransitionAnimationScaleSetting;
                                                        dimTransitionProvider.getClass();
                                                        if (i12 != 0) {
                                                            loadDefaultAnimationRes = null;
                                                        } else {
                                                            loadDefaultAnimationRes = transitionAnimation.loadDefaultAnimationRes(R.anim.task_fragment_close_exit);
                                                            if (loadDefaultAnimationRes != null) {
                                                                loadDefaultAnimationRes.scaleCurrentDuration(f);
                                                            }
                                                        }
                                                        Animation animation2 = loadDefaultAnimationRes;
                                                        if (animation2 != null) {
                                                            int rootIndexFor2 = TransitionUtil.rootIndexFor(change4, transitionInfo2);
                                                            DimTransitionProvider dimTransitionProvider2 = this.mDimTransitionProvider;
                                                            SurfaceControl leash = transitionInfo2.getRoot(rootIndexFor2).getLeash();
                                                            SurfaceControl leash2 = change4.getLeash();
                                                            dimTransitionProvider2.getClass();
                                                            SurfaceControl attachDimTransitionSurface = DimTransitionProvider.attachDimTransitionSurface(this.mSurfaceSession, leash, leash2, transaction5, transaction8);
                                                            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                                                                i10 = 0;
                                                                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -531714563, 0, "attach dim transition, change=%s", String.valueOf(change4));
                                                            } else {
                                                                i10 = 0;
                                                            }
                                                            buildSurfaceAnimation(arrayList2, animation2, attachDimTransitionSurface, new DefaultTransitionHandler$$ExternalSyntheticLambda3(this, attachDimTransitionSurface, defaultTransitionHandler$$ExternalSyntheticLambda5, i10), this.mTransactionPool, this.mMainExecutor, null, 0.0f, null);
                                                        }
                                                    }
                                                } else {
                                                    transitionInfo2 = transitionInfo;
                                                    transaction5 = transaction;
                                                    transaction8 = transaction2;
                                                    z9 = z8;
                                                    arrayList3 = arrayList;
                                                    defaultTransitionHandler$$ExternalSyntheticLambda5 = defaultTransitionHandler$$ExternalSyntheticLambda54;
                                                    i7 = 4;
                                                }
                                                transaction9 = transaction5;
                                                transitionInfo3 = transitionInfo2;
                                                transaction12 = transaction8;
                                                z18 = z24;
                                                int i162 = i7;
                                                transaction11 = transaction9;
                                                i = i162;
                                                i15 = 2;
                                                transaction10 = transaction5;
                                                defaultTransitionHandler$$ExternalSyntheticLambda53 = defaultTransitionHandler$$ExternalSyntheticLambda5;
                                                arrayList7 = arrayList3;
                                                arrayList6 = arrayList2;
                                                z17 = z7;
                                                m136m8 = i4 - 1;
                                                z3 = z9;
                                            }
                                            if (CoreRune.MW_SHELL_TRANSITION) {
                                                MultiTaskingTransitionProvider multiTaskingTransitionProvider = this.mMultiTaskingTransitProvider;
                                                MultiTaskingTransitionState multiTaskingTransitionState = multiTaskingTransitionProvider.mState;
                                                multiTaskingTransitionState.getClass();
                                                multiTaskingTransitionState.mTransitionType = transitionInfo.getType();
                                                multiTaskingTransitionState.mIsEnter = TransitionUtil.isOpeningType(change3.getMode());
                                                Configuration configuration = change3.getConfiguration();
                                                Configuration configuration2 = multiTaskingTransitionState.mConfiguration;
                                                configuration2.setTo(configuration);
                                                multiTaskingTransitionState.mChange = change3;
                                                ActivityManager.RunningTaskInfo taskInfo = change3.getTaskInfo();
                                                multiTaskingTransitionState.mTaskInfo = taskInfo;
                                                multiTaskingTransitionState.mTaskId = taskInfo != null ? taskInfo.taskId : -1;
                                                multiTaskingTransitionState.mDisplayId = taskInfo != null ? taskInfo.getDisplayId() : 0;
                                                multiTaskingTransitionState.mWindowingMode = configuration2.windowConfiguration.getWindowingMode();
                                                multiTaskingTransitionState.mAnimationOptions = transitionInfo.getAnimationOptions();
                                                if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                                                    TransitionInfo.Change changeForAppsEdgeActivity = transitionInfo.getChangeForAppsEdgeActivity();
                                                    multiTaskingTransitionState.mOpeningAppsEdgeTaskInfo = (changeForAppsEdgeActivity == null || changeForAppsEdgeActivity.getMode() != 1) ? null : changeForAppsEdgeActivity.getTaskInfo();
                                                }
                                                if (CoreRune.MT_NEW_DEX_SHELL_TRANSITION && configuration2.isNewDexMode() && (runningTaskInfo = multiTaskingTransitionState.mTaskInfo) != null && runningTaskInfo.isFreeform() && multiTaskingTransitionState.mChange.getMode() == 4) {
                                                    int size3 = transitionInfo.getChanges().size();
                                                    while (true) {
                                                        size3--;
                                                        if (size3 < 0) {
                                                            runningTaskInfo2 = null;
                                                            break;
                                                        }
                                                        change5 = (TransitionInfo.Change) transitionInfo.getChanges().get(size3);
                                                        if (change5.getTaskInfo() == null || !change5.getTaskInfo().isSplitScreen() || (!change5.hasFlags(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) && !change5.getResumedAffordance())) {
                                                        }
                                                    }
                                                    runningTaskInfo2 = change5.getTaskInfo();
                                                    multiTaskingTransitionState.mIsFreeformMovingBehindSplitScreen = runningTaskInfo2 != null;
                                                }
                                                if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
                                                    multiTaskingTransitionState.mHasCustomDisplayChangeTransition = transitionInfo.hasCustomDisplayChangeTransition();
                                                    multiTaskingTransitionState.mSeparatedFromCustomDisplayChange = transitionInfo.isSeparatedFromCustomDisplayChange();
                                                }
                                                if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                                                    multiTaskingTransitionState.mMinimizeAnimState = change3.getMinimizeAnimState();
                                                    multiTaskingTransitionState.mMinimizePoint.set(change3.getMinimizePoint());
                                                }
                                                multiTaskingTransitionState.mIsPopOverAnimationNeeded = change3.getPopOverAnimationNeeded();
                                                if (multiTaskingTransitionState.mWindowingMode == 5) {
                                                    multiTaskingTransitionState.mFreeformStashScale = change3.getFreeformStashScale();
                                                }
                                                multiTaskingTransitionState.mForceHidingTransit = change3.getForceHidingTransit();
                                                try {
                                                    boolean resumedAffordance = change3.getResumedAffordance();
                                                    SparseArray sparseArray = multiTaskingTransitionProvider.mAnimationLoaderMap;
                                                    if (resumedAffordance) {
                                                        Log.d(str2, "loadAnimation: Use affordance" + multiTaskingTransitionState);
                                                        if ((multiTaskingTransitionState.mWindowingMode == 6 && WindowConfiguration.isSplitScreenWindowingMode(configuration2.windowConfiguration)) && createThumbnailEnterExitAnimationLocked != null) {
                                                            createThumbnailEnterExitAnimationLocked.setRoundedCornerRadius(((AnimationLoader) sparseArray.get(1)).getCornerRadius());
                                                        }
                                                        if ((multiTaskingTransitionState.mWindowingMode == 5) && !change3.getAffordanceTargetFreeformTask()) {
                                                            createThumbnailEnterExitAnimationLocked = null;
                                                        }
                                                    } else {
                                                        String str3 = str2;
                                                        TransitionInfo.AnimationOptions animationOptions3 = multiTaskingTransitionState.mAnimationOptions;
                                                        if ((animationOptions3 != null && animationOptions3.getAnimations() == 0 && multiTaskingTransitionState.mAnimationOptions.getEnterResId() == 0 && multiTaskingTransitionState.mAnimationOptions.getExitResId() == 0 && multiTaskingTransitionState.mAnimationOptions.getCustomActivityTransition(true) == null && multiTaskingTransitionState.mAnimationOptions.getCustomActivityTransition(false) == null) && change3.hasFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED)) {
                                                            Log.d(str3, "loadAnimation: has EMPTY custom animation" + multiTaskingTransitionState);
                                                        } else {
                                                            int size4 = sparseArray.size();
                                                            int i19 = 0;
                                                            while (true) {
                                                                if (i19 >= size4) {
                                                                    break;
                                                                }
                                                                AnimationLoader animationLoader = (AnimationLoader) sparseArray.valueAt(i19);
                                                                if (animationLoader.isAvailable()) {
                                                                    animationLoader.loadAnimationIfPossible();
                                                                    MultiTaskingTransitionState multiTaskingTransitionState2 = animationLoader.mState;
                                                                    if (multiTaskingTransitionState2.mAnimationLoaded) {
                                                                        createThumbnailEnterExitAnimationLocked = multiTaskingTransitionState2.mAnimation;
                                                                        if (createThumbnailEnterExitAnimationLocked == AnimationLoader.NO_ANIMATION) {
                                                                            createThumbnailEnterExitAnimationLocked = null;
                                                                        }
                                                                        Log.d(str3, "loadAnimation: " + animationLoader + multiTaskingTransitionState);
                                                                    }
                                                                }
                                                                i19++;
                                                            }
                                                        }
                                                    }
                                                } finally {
                                                    multiTaskingTransitionState.reset();
                                                }
                                            }
                                            if (CoreRune.FW_LARGE_FLIP_CUSTOM_SHELL_TRANSITION) {
                                                transitionAnimation.overrideDisplayId(-1);
                                            }
                                            if (createThumbnailEnterExitAnimationLocked != null) {
                                                if (!createThumbnailEnterExitAnimationLocked.isInitialized()) {
                                                    Rect startAbsBounds = TransitionUtil.isClosingType(mode) ? change3.getStartAbsBounds() : change3.getEndAbsBounds();
                                                    createThumbnailEnterExitAnimationLocked.initialize(startAbsBounds.width(), startAbsBounds.height(), endAbsBounds.width(), endAbsBounds.height());
                                                }
                                                createThumbnailEnterExitAnimationLocked.restrictDuration(3000L);
                                                createThumbnailEnterExitAnimationLocked.scaleCurrentDuration(this.mTransitionAnimationScaleSetting);
                                            }
                                            animation = createThumbnailEnterExitAnimationLocked;
                                            if (animation != null) {
                                            }
                                            transaction9 = transaction5;
                                            transitionInfo3 = transitionInfo2;
                                            transaction12 = transaction8;
                                            z18 = z24;
                                            int i1622 = i7;
                                            transaction11 = transaction9;
                                            i = i1622;
                                            i15 = 2;
                                            transaction10 = transaction5;
                                            defaultTransitionHandler$$ExternalSyntheticLambda53 = defaultTransitionHandler$$ExternalSyntheticLambda5;
                                            arrayList7 = arrayList3;
                                            arrayList6 = arrayList2;
                                            z17 = z7;
                                            m136m8 = i4 - 1;
                                            z3 = z9;
                                        }
                                        transaction5 = transaction;
                                        transaction9 = transaction3;
                                        z9 = z8;
                                        defaultTransitionHandler$$ExternalSyntheticLambda5 = defaultTransitionHandler$$ExternalSyntheticLambda53;
                                        arrayList2 = arrayList6;
                                        arrayList3 = arrayList;
                                        transitionInfo2 = transitionInfo;
                                        z18 = z24;
                                        int i16222 = i7;
                                        transaction11 = transaction9;
                                        i = i16222;
                                        i15 = 2;
                                        transaction10 = transaction5;
                                        defaultTransitionHandler$$ExternalSyntheticLambda53 = defaultTransitionHandler$$ExternalSyntheticLambda5;
                                        arrayList7 = arrayList3;
                                        arrayList6 = arrayList2;
                                        z17 = z7;
                                        m136m8 = i4 - 1;
                                        z3 = z9;
                                    } else {
                                        transaction3 = transaction11;
                                        z7 = z17;
                                        z8 = z20;
                                        arrayList = arrayList7;
                                        i4 = m136m8;
                                        startRotationAnimation(transaction, change13, transitionInfo, rotationAnimationHint, arrayList6, defaultTransitionHandler$$ExternalSyntheticLambda53);
                                        z19 = true;
                                        transaction6 = transaction13;
                                        i = 4;
                                        transaction5 = transaction;
                                        transaction11 = transaction3;
                                        transaction12 = transaction6;
                                        z9 = z8;
                                        defaultTransitionHandler$$ExternalSyntheticLambda5 = defaultTransitionHandler$$ExternalSyntheticLambda53;
                                        arrayList2 = arrayList6;
                                        arrayList3 = arrayList;
                                        transitionInfo2 = transitionInfo;
                                        i15 = 2;
                                        transaction10 = transaction5;
                                        defaultTransitionHandler$$ExternalSyntheticLambda53 = defaultTransitionHandler$$ExternalSyntheticLambda5;
                                        arrayList7 = arrayList3;
                                        arrayList6 = arrayList2;
                                        z17 = z7;
                                        m136m8 = i4 - 1;
                                        z3 = z9;
                                    }
                                } else {
                                    transaction3 = transaction11;
                                    i5 = mode2;
                                }
                                arrayList = arrayList7;
                                i4 = m136m8;
                                transaction7 = transaction13;
                                change = change13;
                                z7 = z17;
                                z8 = z20;
                                z10 = false;
                                if (CoreRune.MW_SHELL_TRANSITION) {
                                }
                                if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                                }
                                if (i6 != 6) {
                                }
                                boolean z242 = z18;
                                if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                                }
                                i7 = 4;
                                if (!z19) {
                                }
                                if (!change3.hasFlags(131072)) {
                                    int type22 = transitionInfo.getType();
                                    int flags3 = transitionInfo.getFlags();
                                    mode = change3.getMode();
                                    int flags22 = change3.getFlags();
                                    boolean isOpeningType3 = TransitionUtil.isOpeningType(type22);
                                    boolean isOpeningType22 = TransitionUtil.isOpeningType(mode);
                                    if (change3.getTaskInfo() == null) {
                                    }
                                    TransitionInfo.AnimationOptions animationOptions22 = transitionInfo.getAnimationOptions();
                                    DefaultTransitionHandler$$ExternalSyntheticLambda5 defaultTransitionHandler$$ExternalSyntheticLambda542 = defaultTransitionHandler$$ExternalSyntheticLambda53;
                                    int type32 = animationOptions22 == null ? animationOptions22.getType() : 0;
                                    if (TransitionUtil.isClosingType(mode)) {
                                    }
                                    z11 = CoreRune.FW_LARGE_FLIP_CUSTOM_SHELL_TRANSITION;
                                    TransitionAnimation transitionAnimation2 = this.mTransitionAnimation;
                                    if (z11) {
                                    }
                                    if (!transitionInfo.isKeyguardGoingAway()) {
                                    }
                                    if (CoreRune.MW_SHELL_TRANSITION) {
                                    }
                                    if (CoreRune.FW_LARGE_FLIP_CUSTOM_SHELL_TRANSITION) {
                                    }
                                    if (createThumbnailEnterExitAnimationLocked != null) {
                                    }
                                    animation = createThumbnailEnterExitAnimationLocked;
                                    if (animation != null) {
                                    }
                                    transaction9 = transaction5;
                                    transitionInfo3 = transitionInfo2;
                                    transaction12 = transaction8;
                                    z18 = z242;
                                    int i162222 = i7;
                                    transaction11 = transaction9;
                                    i = i162222;
                                    i15 = 2;
                                    transaction10 = transaction5;
                                    defaultTransitionHandler$$ExternalSyntheticLambda53 = defaultTransitionHandler$$ExternalSyntheticLambda5;
                                    arrayList7 = arrayList3;
                                    arrayList6 = arrayList2;
                                    z17 = z7;
                                    m136m8 = i4 - 1;
                                    z3 = z9;
                                }
                                transaction5 = transaction;
                                transaction9 = transaction3;
                                z9 = z8;
                                defaultTransitionHandler$$ExternalSyntheticLambda5 = defaultTransitionHandler$$ExternalSyntheticLambda53;
                                arrayList2 = arrayList6;
                                arrayList3 = arrayList;
                                transitionInfo2 = transitionInfo;
                                z18 = z242;
                                int i1622222 = i7;
                                transaction11 = transaction9;
                                i = i1622222;
                                i15 = 2;
                                transaction10 = transaction5;
                                defaultTransitionHandler$$ExternalSyntheticLambda53 = defaultTransitionHandler$$ExternalSyntheticLambda5;
                                arrayList7 = arrayList3;
                                arrayList6 = arrayList2;
                                z17 = z7;
                                m136m8 = i4 - 1;
                                z3 = z9;
                            } else if (z5 && change13.hasFlags(i15) && change13.getMode() == i) {
                                transaction11.hide(change13.getLeash());
                            }
                        }
                        transaction3 = transaction11;
                        arrayList = arrayList7;
                        i4 = m136m8;
                        transaction4 = transaction13;
                        z7 = z17;
                        z8 = z20;
                        transaction6 = transaction4;
                        i = 4;
                        transaction5 = transaction;
                        transaction11 = transaction3;
                        transaction12 = transaction6;
                        z9 = z8;
                        defaultTransitionHandler$$ExternalSyntheticLambda5 = defaultTransitionHandler$$ExternalSyntheticLambda53;
                        arrayList2 = arrayList6;
                        arrayList3 = arrayList;
                        transitionInfo2 = transitionInfo;
                        i15 = 2;
                        transaction10 = transaction5;
                        defaultTransitionHandler$$ExternalSyntheticLambda53 = defaultTransitionHandler$$ExternalSyntheticLambda5;
                        arrayList7 = arrayList3;
                        arrayList6 = arrayList2;
                        z17 = z7;
                        m136m8 = i4 - 1;
                        z3 = z9;
                    }
                    ArrayList arrayList8 = arrayList7;
                    DefaultTransitionHandler$$ExternalSyntheticLambda5 defaultTransitionHandler$$ExternalSyntheticLambda55 = defaultTransitionHandler$$ExternalSyntheticLambda53;
                    ArrayList arrayList9 = arrayList6;
                    SurfaceControl.Transaction transaction14 = transaction11;
                    if (i14 != 0) {
                        Color valueOf = Color.valueOf(i14);
                        float[] fArr = {valueOf.red(), valueOf.green(), valueOf.blue()};
                        for (int i20 = 0; i20 < transitionInfo.getRootCount(); i20++) {
                            int displayId = transitionInfo3.getRoot(i20).getDisplayId();
                            SurfaceControl.Builder colorLayer = new SurfaceControl.Builder().setName("animation-background").setCallsite("DefaultTransitionHandler").setColorLayer();
                            colorLayer.setParent((SurfaceControl) this.mRootTDAOrganizer.mLeashes.get(displayId));
                            SurfaceControl build = colorLayer.build();
                            transaction14.setColor(build, fArr).setLayer(build, -1).show(build);
                            transaction12.remove(build);
                        }
                    }
                    if (arrayList8.size() > 0) {
                        transaction14.apply(true);
                        Iterator it = arrayList8.iterator();
                        while (it.hasNext()) {
                            ((Consumer) it.next()).accept(transaction14);
                        }
                    }
                    transaction.apply();
                    if (CoreRune.FW_INFORM_SCREEN_ROTATION_ANIMATION_STARTED_FOR_CAPTURED_BLUR && transitionInfo.getType() == 6 && this.mCapturedBlurHelper != null) {
                        int m136m10 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, 1);
                        while (true) {
                            if (m136m10 < 0) {
                                break;
                            }
                            if (((TransitionInfo.Change) transitionInfo.getChanges().get(m136m10)).isRotationAnimation()) {
                                final CapturedBlurHelper capturedBlurHelper = this.mCapturedBlurHelper;
                                final long j = this.mMaxRotationAnimationDuration;
                                Thread thread = capturedBlurHelper.mBrThread;
                                if (thread == null || !thread.isAlive()) {
                                    Thread thread2 = new Thread(new Runnable() { // from class: com.android.wm.shell.transition.DefaultTransitionHandler$CapturedBlurHelper$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            DefaultTransitionHandler.CapturedBlurHelper capturedBlurHelper2 = DefaultTransitionHandler.CapturedBlurHelper.this;
                                            long j2 = j;
                                            capturedBlurHelper2.getClass();
                                            Intent intent = new Intent("com.samsung.android.action.SCREEN_ROTATION_ANIMATION_STARTED");
                                            intent.putExtra("now", System.currentTimeMillis());
                                            intent.putExtra("duration", j2 + 30);
                                            DefaultTransitionHandler.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.permisson.SCREEN_ROTATION_ANIMATION_STARTED");
                                        }
                                    }, "ScreenRotationBroadcast");
                                    capturedBlurHelper.mBrThread = thread2;
                                    thread2.start();
                                }
                            } else {
                                m136m10--;
                            }
                        }
                    }
                    ((HandlerExecutor) this.mAnimExecutor).execute(new DefaultTransitionHandler$$ExternalSyntheticLambda0(arrayList9, 2));
                    counterRotatorHelper.cleanUp(transaction12);
                    TransitionMetrics.getInstance().reportAnimationStart(iBinder);
                    defaultTransitionHandler$$ExternalSyntheticLambda55.run();
                    return true;
                }
            }
            transaction.apply();
            transaction2.apply();
            transitionFinishCallback.onTransitionFinished(null, null);
            return true;
        }
        z = false;
        if (!z) {
        }
        transaction.apply();
        transaction2.apply();
        transitionFinishCallback.onTransitionFinished(null, null);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v0, types: [com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda4] */
    public final void startRotationAnimation(SurfaceControl.Transaction transaction, TransitionInfo.Change change, TransitionInfo transitionInfo, int i, final ArrayList arrayList, final DefaultTransitionHandler$$ExternalSyntheticLambda5 defaultTransitionHandler$$ExternalSyntheticLambda5) {
        final ScreenRotationAnimation screenRotationAnimation = new ScreenRotationAnimation(this.mContext, this.mSurfaceSession, this.mTransactionPool, transaction, change, transitionInfo.getRoot(TransitionUtil.rootIndexFor(change, transitionInfo)).getLeash(), i);
        boolean z = false;
        if (CoreRune.FW_CUSTOM_SHELL_RECENTS_TRANSITION_WITH_DISPLAY_CHANGE && transitionInfo.getOverrideDisplayChangeBackColor() != -1) {
            SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
            int overrideDisplayChangeBackColor = transitionInfo.getOverrideDisplayChangeBackColor();
            SurfaceControl surfaceControl = screenRotationAnimation.mBackColorSurface;
            if (surfaceControl != null) {
                acquire.setColor(surfaceControl, new float[]{Color.red(overrideDisplayChangeBackColor) / 255.0f, Color.green(overrideDisplayChangeBackColor) / 255.0f, Color.blue(overrideDisplayChangeBackColor) / 255.0f});
            }
            acquire.apply();
        }
        final ArrayList arrayList2 = new ArrayList(3);
        final ArrayList arrayList3 = new ArrayList(3);
        ?? r14 = new Runnable() { // from class: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ArrayList arrayList4 = arrayList2;
                ScreenRotationAnimation screenRotationAnimation2 = screenRotationAnimation;
                ArrayList arrayList5 = arrayList;
                ArrayList arrayList6 = arrayList3;
                Runnable runnable = defaultTransitionHandler$$ExternalSyntheticLambda5;
                if (arrayList4.isEmpty()) {
                    TransactionPool transactionPool = screenRotationAnimation2.mTransactionPool;
                    SurfaceControl.Transaction acquire2 = transactionPool.acquire();
                    SurfaceControl surfaceControl2 = screenRotationAnimation2.mAnimLeash;
                    if (surfaceControl2.isValid()) {
                        acquire2.remove(surfaceControl2);
                    }
                    SurfaceControl surfaceControl3 = screenRotationAnimation2.mScreenshotLayer;
                    if (surfaceControl3 != null && surfaceControl3.isValid()) {
                        acquire2.remove(surfaceControl3);
                    }
                    SurfaceControl surfaceControl4 = screenRotationAnimation2.mBackColorSurface;
                    if (surfaceControl4 != null && surfaceControl4.isValid()) {
                        acquire2.remove(surfaceControl4);
                    }
                    screenRotationAnimation2.mFadeInOutAnimationNeeded = false;
                    acquire2.apply();
                    transactionPool.release(acquire2);
                    arrayList5.removeAll(arrayList6);
                    runnable.run();
                }
            }
        };
        if (!CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION || (change.getFlags() & QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE) == 0) {
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_DISPLAY_CHANGE) {
                if (transitionInfo.getAnimationOptions() != null && transitionInfo.getAnimationOptions().getType() == 15) {
                    z = true;
                }
                if (z) {
                    screenRotationAnimation.buildAnimation(arrayList2, r14, this.mTransitionAnimationScaleSetting, this.mMainExecutor, transitionInfo.getAnimationOptions().getExitResId(), transitionInfo.getAnimationOptions().getEnterResId());
                }
            }
            screenRotationAnimation.buildAnimation(arrayList2, r14, this.mTransitionAnimationScaleSetting, this.mMainExecutor, -1, -1);
        } else {
            boolean z2 = (change.getFlags() & QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT) != 0;
            int i2 = ChangeTransitionProvider.$r8$clinit;
            int[] iArr = new int[2];
            if (z2) {
                iArr[0] = com.android.systemui.R.anim.multi_window_display_change_fast_exit;
                iArr[1] = com.android.systemui.R.anim.multi_window_display_change_fast_enter;
            } else {
                iArr[0] = com.android.systemui.R.anim.multi_window_display_change_exit;
                iArr[1] = com.android.systemui.R.anim.multi_window_display_change_enter;
            }
            if (CoreRune.SAFE_DEBUG) {
                Slog.d("ChangeTransitionProvider", "selectDisplayChangeAnimationResID: fastAnimation=" + z2);
            }
            screenRotationAnimation.buildAnimation(arrayList2, r14, this.mTransitionAnimationScaleSetting, this.mMainExecutor, iArr[0], iArr[1]);
        }
        for (int size = arrayList2.size() - 1; size >= 0; size--) {
            Animator animator = (Animator) arrayList2.get(size);
            arrayList3.add(animator);
            arrayList.add(animator);
        }
        if (CoreRune.FW_INFORM_SCREEN_ROTATION_ANIMATION_STARTED_FOR_CAPTURED_BLUR) {
            this.mMaxRotationAnimationDuration = 0L;
            for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
                this.mMaxRotationAnimationDuration = Math.max(this.mMaxRotationAnimationDuration, ((Animator) arrayList2.get(size2)).getDuration());
            }
            change.setRotationAnimation(true);
        }
    }

    public static void buildSurfaceAnimation(final ArrayList arrayList, final Animation animation, final SurfaceControl surfaceControl, final Runnable runnable, final TransactionPool transactionPool, final ShellExecutor shellExecutor, final Point point, final float f, final Rect rect, final float f2) {
        final SurfaceControl.Transaction acquire = transactionPool.acquire();
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        final Transformation transformation = new Transformation();
        final float[] fArr = new float[9];
        ofFloat.overrideDurationScale(1.0f);
        ofFloat.setDuration(animation.computeDurationHint());
        final ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda6
            public final /* synthetic */ Rect f$10 = null;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ValueAnimator valueAnimator2 = ofFloat;
                DefaultTransitionHandler.applyTransformation(Math.min(valueAnimator2.getDuration(), valueAnimator2.getCurrentPlayTime()), acquire, surfaceControl, animation, transformation, fArr, point, f, rect, f2);
            }
        };
        ofFloat.addUpdateListener(animatorUpdateListener);
        final Runnable runnable2 = new Runnable() { // from class: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda7
            public final /* synthetic */ Rect f$10 = null;

            @Override // java.lang.Runnable
            public final void run() {
                ValueAnimator valueAnimator = ofFloat;
                SurfaceControl.Transaction transaction = acquire;
                SurfaceControl surfaceControl2 = surfaceControl;
                Animation animation2 = animation;
                Transformation transformation2 = transformation;
                float[] fArr2 = fArr;
                Point point2 = point;
                float f3 = f;
                Rect rect2 = rect;
                float f4 = f2;
                TransactionPool transactionPool2 = transactionPool;
                ShellExecutor shellExecutor2 = shellExecutor;
                ArrayList arrayList2 = arrayList;
                Runnable runnable3 = runnable;
                DefaultTransitionHandler.applyTransformation(valueAnimator.getDuration(), transaction, surfaceControl2, animation2, transformation2, fArr2, point2, f3, rect2, f4);
                transactionPool2.release(transaction);
                ((HandlerExecutor) shellExecutor2).execute(new DefaultTransitionHandler$$ExternalSyntheticLambda3(arrayList2, valueAnimator, runnable3, 1));
            }
        };
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.transition.DefaultTransitionHandler.2
            public boolean mFinished = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                if (this.mFinished) {
                    return;
                }
                this.mFinished = true;
                runnable2.run();
                ofFloat.removeUpdateListener(animatorUpdateListener);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (this.mFinished) {
                    return;
                }
                this.mFinished = true;
                runnable2.run();
                ofFloat.removeUpdateListener(animatorUpdateListener);
            }
        });
        arrayList.add(ofFloat);
    }
}
