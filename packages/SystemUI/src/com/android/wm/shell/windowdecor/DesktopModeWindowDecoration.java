package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Property;
import android.util.Size;
import android.view.Choreographer;
import android.view.DisplayCutout;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.InsetsState;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.WindowlessWindowManager;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.window.DesktopModeFlags;
import android.window.InputTransferToken;
import android.window.WindowContainerTransaction;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.compose.material3.ColorScheme;
import androidx.compose.ui.graphics.ColorKt;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.apptoweb.AppToWebGenericLinksParser;
import com.android.wm.shell.apptoweb.AssistContentRequester;
import com.android.wm.shell.apptoweb.OpenByDefaultDialog;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopImmersiveController;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeUtils;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopRepository$removeExclusionRegion$1;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0;
import com.android.wm.shell.desktopmode.WindowDecorCaptionHandleRepository;
import com.android.wm.shell.freeform.FreeformAdjustImeController;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.DragResizeInputListener;
import com.android.wm.shell.windowdecor.DragResizeWindowGeometry;
import com.android.wm.shell.windowdecor.HandleMenu;
import com.android.wm.shell.windowdecor.MaximizeMenu;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewContainer;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewHostViewContainer;
import com.android.wm.shell.windowdecor.common.ThemeUtilsKt;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier;
import com.android.wm.shell.windowdecor.extension.TaskInfoKt;
import com.android.wm.shell.windowdecor.policy.CaptionButtonPolicy;
import com.android.wm.shell.windowdecor.policy.PopupButtonPolicy;
import com.android.wm.shell.windowdecor.viewholder.AppHandleViewHolder;
import com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder;
import com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder;
import com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder;
import com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder;
import com.android.wm.shell.windowdecor.widget.OutlineView;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DesktopModeWindowDecoration extends WindowDecoration {
    static final long CLOSE_MAXIMIZE_MENU_DELAY_MS = 150;
    public final AppHandleViewHolder.Factory mAppHandleViewHolderFactory;
    public final AppHeaderViewHolder.Factory mAppHeaderViewHolderFactory;
    public final AssistContentRequester mAssistContentRequester;
    public final ShellExecutor mBgExecutor;
    public final CoroutineScope mBgScope;
    public int mCaptionType;
    public CapturedLink mCapturedLink;
    public int mCenterCutoutTopInset;
    public final Choreographer mChoreographer;
    public final DesktopModeWindowDecoration$$ExternalSyntheticLambda0 mCloseMaximizeWindowRunnable;
    public final DesktopConfig mDesktopConfig;
    public final DesktopImmersiveController mDesktopImmersiveController;
    public final DesktopModeCompatPolicy mDesktopModeCompatPolicy;
    public final DesktopModeUiEventLogger mDesktopModeUiEventLogger;
    public final DesktopState mDesktopState;
    public final DesktopUserRepositories mDesktopUserRepositories;
    public DragResizeWindowGeometry.DisabledEdge mDisabledResizingEdge;
    public DisplayCutout mDisplayCutout;
    public TaskPositioner mDragPositioningCallback;
    public DragResizeInputListener mDragResizeListener;
    public DesktopModeWindowDecorViewModel.ExclusionRegionListenerImpl mExclusionRegionListener;
    public final FreeformAdjustImeController mFreeformAdjustImeController;
    public FreeformOutline mFreeformOutline;
    public FreeformDimInputListener mFreeformStashDimInputListener;
    public final FreeformStashState mFreeformStashState;
    public Uri mGenericLink;
    public final AppToWebGenericLinksParser mGenericLinksParser;
    public HandleMenu mHandleMenu;
    public final HandleMenuFactory mHandleMenuFactory;
    public final HandleMenuHelpController mHandleMenuHelpController;
    public final Handler mHandler;
    public final MultiTaskingHeaderViewHolder.Factory mHeaderViewHolderFactory;
    public boolean mInDesktopWindowing;
    public DecorationInputEventReceiver mInputEventReceiver;
    public boolean mIsAppHeaderMaximizeButtonHovered;
    public boolean mIsCutoutLocatedInCenter;
    public boolean mIsDesktopModeSupportedOnDisplay;
    public boolean mIsDragging;
    public boolean mIsFreeformCaptionTypeChanged;
    public boolean mIsHandleOverlappedSystemBar;
    public boolean mIsKeyguardShowing;
    public boolean mIsMaximizeMenuHovered;
    public boolean mIsRecentsTransitionRunning;
    public boolean mIsTaskMaximized;
    public final Rect mLastStableBounds;
    public DesktopModeWindowDecoration$$ExternalSyntheticLambda1 mLoadAppInfoRunnable;
    public final MainCoroutineDispatcher mMainDispatcher;
    public final ShellExecutor mMainExecutor;
    public ManageWindowsViewContainer mManageWindowsMenu;
    public MaximizeMenu mMaximizeMenu;
    public final MaximizeMenuFactory mMaximizeMenuFactory;
    public boolean mMinimumInstancesFound;
    public final MultiInstanceHelper mMultiInstanceHelper;
    public DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener mOnCaptionButtonClickListener;
    public DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener mOnCaptionGenericMotionListener;
    public DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener mOnCaptionLongClickListener;
    public DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener mOnCaptionTouchListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11 mOnChangeAspectRatioClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11 mOnImmersiveOrRestoreClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 mOnLeftSnapClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11 mOnManageWindowsClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 mOnMaximizeHoverListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 mOnMaximizeOrRestoreClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11 mOnNewWindowClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11 mOnRestartClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 mOnRightSnapClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda17 mOnToDesktopClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11 mOnToFloatClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11 mOnToFullscreenClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11 mOnToSplitscreenClickListener;
    public OpenByDefaultDialog mOpenByDefaultDialog;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda17 mOpenInBrowserClickListener;
    public final Point mPositionInParent;
    public final WindowDecoration.RelayoutParams mRelayoutParams;
    public ResizeVeil mResizeVeil;
    public final WindowDecoration.RelayoutResult mResult;
    public final RootTaskDisplayAreaOrganizer mRootTaskDisplayAreaOrganizer;
    public DesktopModeWindowDecoration$$ExternalSyntheticLambda23 mSetAppInfoRunnable;
    public final SplitScreenController mSplitScreenController;
    public final SyncTransactionQueue mSyncQueue;
    public final Point mTaskPosition;
    public TaskPositioner mTaskPositioner;
    public final WindowDecorTaskResourceLoader mTaskResourceLoader;
    public final Rect mTmpRect;
    public final Rect mTmpRect2;
    public int mTopDisplayCutoutInset;
    public Uri mWebUri;
    public final WindowDecorCaptionHandleRepository mWindowDecorCaptionHandleRepository;
    public WindowDecorationViewHolder mWindowDecorViewHolder;
    public final WindowManagerWrapper mWindowManagerWrapper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$2, reason: invalid class name */
    public class AnonymousClass2 implements OpenByDefaultDialog.DialogLifecycleListener {
        public AnonymousClass2() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class CapturedLink {
        public final long mTimeStamp;
        public final Uri mUri;
        public boolean mUsed;

        public CapturedLink(Uri uri, long j) {
            this.mUri = uri;
            this.mTimeStamp = j;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DecorationInputEventReceiver extends InputEventReceiver {
        public InputMonitor mInputMonitor;
        public final boolean mIsCaptionTypeHandle;

        public DecorationInputEventReceiver(InputMonitor inputMonitor, InputChannel inputChannel, Looper looper, boolean z) {
            super(inputChannel, looper);
            this.mInputMonitor = inputMonitor;
            this.mIsCaptionTypeHandle = z;
        }

        public final void dispose() {
            InputMonitor inputMonitor = this.mInputMonitor;
            if (inputMonitor != null) {
                inputMonitor.dispose();
                this.mInputMonitor = null;
            }
            super.dispose();
        }

        public final void onInputEvent(InputEvent inputEvent) {
            int toolType;
            MultiTaskingHandleViewHolder asMultiTaskingAppHandle;
            boolean z = false;
            if (inputEvent instanceof MotionEvent) {
                if (this.mIsCaptionTypeHandle) {
                    DesktopModeWindowDecoration desktopModeWindowDecoration = DesktopModeWindowDecoration.this;
                    MotionEvent motionEvent = (MotionEvent) inputEvent;
                    Rect bounds = desktopModeWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds();
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();
                    if (motionEvent.getAction() == 0 && bounds.contains(x, y) && (asMultiTaskingAppHandle = DesktopModeWindowDecoration.asMultiTaskingAppHandle(desktopModeWindowDecoration.mWindowDecorViewHolder)) != null && (!asMultiTaskingAppHandle.handleInputBounds.contains(x, y) || !asMultiTaskingAppHandle.handleHideAnimator.mIsHandleViewVisible)) {
                        HandleHideAnimator handleHideAnimator = asMultiTaskingAppHandle.handleHideAnimator;
                        if (!handleHideAnimator.mIsHandleMenuActive) {
                            handleHideAnimator.cancelAllHandleAnim();
                            if (handleHideAnimator.mIsHandleViewVisible) {
                                handleHideAnimator.mHandler.removeCallbacks(handleHideAnimator.mHideRunnable);
                                handleHideAnimator.delayedHide();
                            } else {
                                handleHideAnimator.show(new HandleHideAnimator$$ExternalSyntheticLambda0(handleHideAnimator, 1), false);
                            }
                        }
                    }
                } else {
                    DesktopModeWindowDecoration desktopModeWindowDecoration2 = DesktopModeWindowDecoration.this;
                    MotionEvent motionEvent2 = (MotionEvent) inputEvent;
                    MultiTaskingHeaderViewHolder asMultiTaskingAppHeader = DesktopModeWindowDecoration.asMultiTaskingAppHeader(desktopModeWindowDecoration2.mWindowDecorViewHolder);
                    if (asMultiTaskingAppHeader != null) {
                        WindowDecoration.RelayoutResult relayoutResult = desktopModeWindowDecoration2.mResult;
                        int i = relayoutResult.mCaptionX;
                        int i2 = relayoutResult.mCaptionY;
                        PointF pointF = new PointF(motionEvent2.getX(), motionEvent2.getY());
                        pointF.offset(-i, -i2);
                        Point point = desktopModeWindowDecoration2.mTaskInfo.positionInParent;
                        pointF.offset(-point.x, -point.y);
                        Rect bounds2 = desktopModeWindowDecoration2.mTaskInfo.configuration.windowConfiguration.getBounds();
                        float f = pointF.x;
                        if (f >= 0.0f && f <= bounds2.width()) {
                            float f2 = pointF.y;
                            if (f2 >= 0.0f && f2 <= bounds2.height()) {
                                int i3 = (int) pointF.y;
                                CaptionButtonPolicy captionButtonPolicy = asMultiTaskingAppHeader.captionButtonPolicy;
                                if (captionButtonPolicy.mImmersiveAnimator != null) {
                                    if (motionEvent2.isHoverEvent()) {
                                        DesktopImmersiveCaptionAnimator desktopImmersiveCaptionAnimator = captionButtonPolicy.mImmersiveAnimator;
                                        desktopImmersiveCaptionAnimator.getClass();
                                        boolean z2 = motionEvent2.getToolType(0) == 2;
                                        boolean z3 = (67108864 & motionEvent2.getFlags()) != 0;
                                        if (!z2 || z3 || motionEvent2.getSource() == 8194) {
                                            int actionMasked = motionEvent2.getActionMasked();
                                            Handler handler = desktopImmersiveCaptionAnimator.mHandler;
                                            DesktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0 desktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0 = desktopImmersiveCaptionAnimator.mHideRunnable;
                                            int i4 = desktopImmersiveCaptionAnimator.mCaptionHeight;
                                            if (actionMasked != 7) {
                                                if (actionMasked == 9) {
                                                    desktopImmersiveCaptionAnimator.mPositionToShow = (z2 && z3) ? i4 / 2 : 1;
                                                } else if (actionMasked == 10 && desktopImmersiveCaptionAnimator.mIsShowing && !handler.hasCallbacks(desktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0)) {
                                                    handler.postDelayed(desktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0, 1000L);
                                                }
                                            } else if (desktopImmersiveCaptionAnimator.mIsShowing) {
                                                if (i3 > i4) {
                                                    handler.postDelayed(desktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0, 1000L);
                                                } else if (handler.hasCallbacks(desktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0)) {
                                                    handler.removeCallbacks(desktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0);
                                                }
                                            } else if (i3 <= desktopImmersiveCaptionAnimator.mPositionToShow) {
                                                DesktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0 desktopImmersiveCaptionAnimator$$ExternalSyntheticLambda02 = desktopImmersiveCaptionAnimator.mShowRunnable;
                                                if (!handler.hasCallbacks(desktopImmersiveCaptionAnimator$$ExternalSyntheticLambda02)) {
                                                    desktopImmersiveCaptionAnimator.mShownByTouch = false;
                                                    handler.postDelayed(desktopImmersiveCaptionAnimator$$ExternalSyntheticLambda02, 500);
                                                }
                                            }
                                        }
                                    } else if (motionEvent2.isTouchEvent()) {
                                        DesktopImmersiveCaptionAnimator desktopImmersiveCaptionAnimator2 = captionButtonPolicy.mImmersiveAnimator;
                                        desktopImmersiveCaptionAnimator2.getClass();
                                        if ((motionEvent2.getFlags() & 67108864) == 0 && ((toolType = motionEvent2.getToolType(0)) == 1 || toolType == 2 || toolType == 3)) {
                                            int action = motionEvent2.getAction();
                                            if (action == 0) {
                                                desktopImmersiveCaptionAnimator2.mDownY = i3;
                                                desktopImmersiveCaptionAnimator2.mDownTime = motionEvent2.getEventTime();
                                            } else if (action == 2) {
                                                DesktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0 desktopImmersiveCaptionAnimator$$ExternalSyntheticLambda03 = desktopImmersiveCaptionAnimator2.mShowRunnable;
                                                Handler handler2 = desktopImmersiveCaptionAnimator2.mHandler;
                                                if (!handler2.hasCallbacks(desktopImmersiveCaptionAnimator$$ExternalSyntheticLambda03)) {
                                                    float f3 = i3;
                                                    long eventTime = motionEvent2.getEventTime() - desktopImmersiveCaptionAnimator2.mDownTime;
                                                    float f4 = desktopImmersiveCaptionAnimator2.mDownY;
                                                    float f5 = desktopImmersiveCaptionAnimator2.mCaptionHeight;
                                                    if (f4 <= f5 && f3 > f4 && f3 > f5 && eventTime < 500) {
                                                        desktopImmersiveCaptionAnimator2.mShownByTouch = true;
                                                        handler2.postDelayed(desktopImmersiveCaptionAnimator$$ExternalSyntheticLambda03, 0);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                z = true;
            }
            finishInputEvent(inputEvent, z);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Factory {
    }

    public DesktopModeWindowDecoration(Context context, Context context2, DisplayController displayController, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, SplitScreenController splitScreenController, DesktopUserRepositories desktopUserRepositories, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Handler handler, ShellExecutor shellExecutor, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, ShellExecutor shellExecutor2, Choreographer choreographer, SyncTransactionQueue syncTransactionQueue, AppHeaderViewHolder.Factory factory, AppHandleViewHolder.Factory factory2, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, AppToWebGenericLinksParser appToWebGenericLinksParser, AssistContentRequester assistContentRequester, WindowDecorViewHostSupplier windowDecorViewHostSupplier, MultiInstanceHelper multiInstanceHelper, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, DesktopModeEventLogger desktopModeEventLogger, DesktopModeUiEventLogger desktopModeUiEventLogger, DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopState desktopState, DesktopConfig desktopConfig, MultiTaskingHeaderViewHolder.Factory factory3, HandleMenuHelpController handleMenuHelpController, DesktopImmersiveController desktopImmersiveController) {
        this(context, context2, displayController, windowDecorTaskResourceLoader, splitScreenController, desktopUserRepositories, shellTaskOrganizer, runningTaskInfo, surfaceControl, handler, shellExecutor, mainCoroutineDispatcher, coroutineScope, shellExecutor2, choreographer, syncTransactionQueue, factory, factory2, rootTaskDisplayAreaOrganizer, appToWebGenericLinksParser, assistContentRequester, new DesktopModeWindowDecoration$$ExternalSyntheticLambda4(0), new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0(), new DesktopModeWindowDecoration$$ExternalSyntheticLambda4(1), new DesktopModeWindowDecoration$$ExternalSyntheticLambda4(2), new WindowManagerWrapper((WindowManager) context.getSystemService(WindowManager.class)), new WindowDecoration.SurfaceControlViewHostFactory() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration.1
        }, windowDecorViewHostSupplier, DefaultMaximizeMenuFactory.INSTANCE, DefaultHandleMenuFactory.INSTANCE, multiInstanceHelper, windowDecorCaptionHandleRepository, desktopModeEventLogger, desktopModeUiEventLogger, desktopModeCompatPolicy, desktopState, desktopConfig, factory3, handleMenuHelpController, desktopImmersiveController);
    }

    public static AppHeaderViewHolder asAppHeader(WindowDecorationViewHolder windowDecorationViewHolder) {
        if (windowDecorationViewHolder instanceof AppHeaderViewHolder) {
            return (AppHeaderViewHolder) windowDecorationViewHolder;
        }
        return null;
    }

    public static MultiTaskingHandleViewHolder asMultiTaskingAppHandle(WindowDecorationViewHolder windowDecorationViewHolder) {
        if (windowDecorationViewHolder instanceof MultiTaskingHandleViewHolder) {
            return (MultiTaskingHandleViewHolder) windowDecorationViewHolder;
        }
        return null;
    }

    public static MultiTaskingHeaderViewHolder asMultiTaskingAppHeader(WindowDecorationViewHolder windowDecorationViewHolder) {
        if (windowDecorationViewHolder instanceof MultiTaskingHeaderViewHolder) {
            return (MultiTaskingHeaderViewHolder) windowDecorationViewHolder;
        }
        return null;
    }

    public static boolean isAppHandle(WindowDecorationViewHolder windowDecorationViewHolder) {
        if (windowDecorationViewHolder instanceof AppHandleViewHolder) {
            return true;
        }
        return CoreRune.MW_CAPTION && (windowDecorationViewHolder instanceof MultiTaskingHandleViewHolder);
    }

    public static boolean isAppHeader(WindowDecorationViewHolder windowDecorationViewHolder) {
        if (windowDecorationViewHolder instanceof AppHeaderViewHolder) {
            return true;
        }
        return CoreRune.MW_CAPTION && (windowDecorationViewHolder instanceof MultiTaskingHeaderViewHolder);
    }

    public static boolean isDragResizable(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        if (z || runningTaskInfo.isForceHidden) {
            return false;
        }
        return DesktopModeFlags.ENABLE_WINDOWING_SCALED_RESIZING.isTrue() ? runningTaskInfo.isFreeform() : runningTaskInfo.isFreeform() && runningTaskInfo.isResizeable;
    }

    /* JADX WARN: Code restructure failed: missing block: B:120:0x034c, code lost:
    
        if ((r14.isFreeform() && (!r0.isVeiledResizeEnabled || android.window.DesktopModeFlags.ENABLE_OPAQUE_BACKGROUND_FOR_TRANSPARENT_WINDOWS.isTrue())) != false) goto L239;
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x004b, code lost:
    
        if (com.samsung.android.rune.CoreRune.IS_TABLET_DEVICE != false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x004d, code lost:
    
        r6 = com.android.systemui.R.layout.mw_handle_view_split_bottom_layout_tablet;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x004f, code lost:
    
        r6 = com.android.systemui.R.layout.mw_handle_view_split_bottom_layout;
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x0053, code lost:
    
        if (com.samsung.android.rune.CoreRune.IS_TABLET_DEVICE != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x0055, code lost:
    
        r6 = com.android.systemui.R.layout.mw_handle_view_layout_tablet;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x0057, code lost:
    
        r6 = com.android.systemui.R.layout.mw_handle_view_layout;
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x006d, code lost:
    
        if (com.samsung.android.rune.CoreRune.IS_TABLET_DEVICE != false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x0072, code lost:
    
        if (com.samsung.android.rune.CoreRune.IS_TABLET_DEVICE != false) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void updateRelayoutParams(com.android.wm.shell.windowdecor.WindowDecoration.RelayoutParams r12, android.content.Context r13, android.app.ActivityManager.RunningTaskInfo r14, com.android.wm.shell.splitscreen.SplitScreenController r15, boolean r16, boolean r17, boolean r18, boolean r19, boolean r20, boolean r21, android.view.InsetsState r22, boolean r23, android.graphics.Region r24, boolean r25, boolean r26, com.android.wm.shell.shared.desktopmode.DesktopConfig r27, int r28, boolean r29, boolean r30, boolean r31) {
        /*
            Method dump skipped, instructions count: 851
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration.updateRelayoutParams(com.android.wm.shell.windowdecor.WindowDecoration$RelayoutParams, android.content.Context, android.app.ActivityManager$RunningTaskInfo, com.android.wm.shell.splitscreen.SplitScreenController, boolean, boolean, boolean, boolean, boolean, boolean, android.view.InsetsState, boolean, android.graphics.Region, boolean, boolean, com.android.wm.shell.shared.desktopmode.DesktopConfig, int, boolean, boolean, boolean):void");
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final int calculateCaptionPositionX(int i, int i2) {
        TaskPositioner taskPositioner;
        MultiTaskingHandleViewHolder asMultiTaskingAppHandle;
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId);
        int i3 = (i - i2) / 2;
        if (isDecorCaptionState$1() || !this.mTaskInfo.isFreeform() || displayLayout == null || (!((taskPositioner = this.mTaskPositioner) == null || taskPositioner.isAllowTouches()) || ((CoreRune.MW_CAPTION_FREEFORM_STASH && this.mFreeformStashState.isStashed()) || (asMultiTaskingAppHandle = asMultiTaskingAppHandle(this.mWindowDecorViewHolder)) == null))) {
            return i3;
        }
        Rect bounds = this.mTaskInfo.getConfiguration().windowConfiguration.getBounds();
        int i4 = displayLayout.mWidth;
        ImageButton imageButton = asMultiTaskingAppHandle.captionHandle;
        if (imageButton instanceof HandleImageButton) {
            HandleImageButton handleImageButton = (HandleImageButton) imageButton;
            if (handleImageButton.isPaddingAdjusted) {
                handleImageButton.setPadding(handleImageButton.initHorizontalPadding, handleImageButton.getPaddingTop(), handleImageButton.initHorizontalPadding, handleImageButton.getPaddingBottom());
                handleImageButton.isPaddingAdjusted = false;
            }
        }
        int width = (bounds.width() - i2) / 2;
        int i5 = bounds.left;
        int i6 = width + i5;
        if (i6 <= 0 || i6 + i2 >= i4) {
            HandleImageButton handleImageButton2 = (HandleImageButton) asMultiTaskingAppHandle.captionHandle;
            if (i5 < 0) {
                int abs = Math.abs(i5);
                int i7 = bounds.right;
                if (i2 <= i7) {
                    return AbsActionBarView$$ExternalSyntheticOutline0.m(i7, i2, 2, abs);
                }
                handleImageButton2.adjustHorizontalHandlePadding(i7);
                return Math.abs(bounds.left);
            }
            if (bounds.right > i4) {
                int i8 = i4 - i5;
                if (i2 <= i8) {
                    return (i8 - i2) / 2;
                }
                handleImageButton2.adjustHorizontalHandlePadding(i8);
                return 0;
            }
        }
        return width;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final int calculateCaptionPositionY() {
        if (this.mTaskInfo.isFreeform()) {
            return 0;
        }
        Resources resources = this.mContext.getResources();
        if (CoreRune.MW_CAPTION_SPLIT_PARALLEL && this.mSplitScreenController.isParallelMultiSplit()) {
            if (this.mTaskInfo.configuration.windowConfiguration.getBounds().top != 0) {
                return WindowDecoration.loadDimensionPixelSize(resources, R.dimen.mw_handle_split_bottom_inset);
            }
            if (!CoreRune.MW_CAPTION_INSETS || this.mIsHandleOverlappedSystemBar) {
                return 0;
            }
            return this.mRelayoutParams.mDisplayTopInset;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        if (runningTaskInfo.isSplitScreen() && (runningTaskInfo.configuration.windowConfiguration.getStagePosition() & 64) != 0) {
            return WindowDecoration.loadDimensionPixelSize(resources, R.dimen.mw_handle_split_bottom_inset);
        }
        if (CoreRune.MW_CAPTION_INSETS && !this.mIsHandleOverlappedSystemBar) {
            return this.mRelayoutParams.mDisplayTopInset;
        }
        if (this.mTaskInfo.getWindowingMode() != 1) {
            return 0;
        }
        if (!this.mIsDesktopModeSupportedOnDisplay) {
            return WindowDecoration.loadDimensionPixelSize(resources, R.dimen.mw_handle_full_screen_top_inset);
        }
        if (CoreRune.MW_CAPTION_FULL_SCREEN_CUTOUT && this.mIsCutoutLocatedInCenter) {
            return this.mCenterCutoutTopInset;
        }
        int i = this.mRelayoutParams.mDisplayTopInset;
        int i2 = this.mResult.mCaptionHeight;
        if (i > i2) {
            return (i - i2) / 2;
        }
        return 0;
    }

    public final Rect calculateValidDragArea() {
        int loadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(this.mContext.getResources(), R.dimen.desktop_mode_app_details_width_minus_text) + (CoreRune.MW_CAPTION ? 0 : ((AppHeaderViewHolder) this.mWindowDecorViewHolder).appNameTextView.getWidth());
        int loadDimensionPixelSize2 = WindowDecoration.loadDimensionPixelSize(this.mContext.getResources(), R.dimen.freeform_required_visible_empty_space_in_header);
        int loadDimensionPixelSize3 = WindowDecoration.loadDimensionPixelSize(this.mContext.getResources(), R.dimen.desktop_mode_right_edge_buttons_width);
        int width = this.mTaskInfo.configuration.windowConfiguration.getBounds().width();
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId);
        int i = displayLayout.mWidth;
        Rect rect = new Rect();
        displayLayout.getStableBounds(rect, false);
        int i2 = loadDimensionPixelSize + loadDimensionPixelSize3 + loadDimensionPixelSize2;
        return new Rect(i2 <= width ? (-width) + loadDimensionPixelSize2 + loadDimensionPixelSize3 : 0, rect.top, i2 > width ? i - width : (i - loadDimensionPixelSize2) - loadDimensionPixelSize, rect.bottom - loadDimensionPixelSize2);
    }

    public final boolean canOpenMaximizeMenu(boolean z) {
        if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue()) {
            return (z || (this.mDesktopUserRepositories.getProfile(this.mTaskInfo.userId).isTaskInFullImmersiveState(this.mTaskInfo.taskId) && (((TaskInfo) this.mTaskInfo).requestedVisibleTypes & WindowInsets.Type.statusBars()) == 0)) ? false : true;
        }
        return !z;
    }

    public final void checkTouchEvent(MotionEvent motionEvent) {
        if (this.mResult.mRootView != null) {
            DesktopModeFlags desktopModeFlags = DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX;
            if (desktopModeFlags.isTrue()) {
                return;
            }
            View findViewById = ((WindowDecorLinearLayout) this.mResult.mRootView).findViewById(R.id.desktop_mode_caption).findViewById(R.id.caption_handle);
            boolean z = !isHandleMenuActive() && checkTouchEventInFocusedCaptionHandle(motionEvent);
            if (motionEvent.getActionMasked() == 1 && z) {
                findViewById.performClick();
            }
            if (isHandleMenuActive() && isAppHandle(this.mWindowDecorViewHolder) && !desktopModeFlags.isTrue()) {
                this.mHandleMenu.checkMotionEvent(motionEvent);
                closeHandleMenuIfNeeded(motionEvent);
            }
        }
    }

    public final boolean checkTouchEventInCaption(MotionEvent motionEvent) {
        PointF offsetCaptionLocation = offsetCaptionLocation(motionEvent);
        if (CoreRune.MW_CAPTION_HANDLE && this.mTaskInfo.isFreeform() && isDecorHandleState()) {
            float f = offsetCaptionLocation.x;
            if (f >= this.mResult.mCaptionX && f <= r4 + r6.mCaptionWidth) {
                float f2 = offsetCaptionLocation.y;
                if (f2 >= 0.0f && f2 <= r6.mCaptionTouchableHeight) {
                    return true;
                }
            }
            return false;
        }
        float f3 = offsetCaptionLocation.x;
        if (f3 >= this.mResult.mCaptionX && f3 <= r4 + r6.mCaptionWidth) {
            float f4 = offsetCaptionLocation.y;
            if (f4 >= 0.0f && f4 <= r6.mCaptionHeight) {
                return true;
            }
        }
        return false;
    }

    public final boolean checkTouchEventInFocusedCaptionHandle(MotionEvent motionEvent) {
        if (isHandleMenuActive() || !isAppHandle(this.mWindowDecorViewHolder)) {
            return false;
        }
        DesktopModeFlags desktopModeFlags = DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX;
        if (desktopModeFlags.isTrue()) {
            return false;
        }
        if (isAppHandle(this.mWindowDecorViewHolder) && desktopModeFlags.isTrue() && isCaptionVisible()) {
            return true;
        }
        return checkTouchEventInCaption(motionEvent);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration, java.lang.AutoCloseable
    public final void close() {
        FreeformOutline freeformOutline;
        DecorationInputEventReceiver decorationInputEventReceiver;
        DesktopModeWindowDecoration$$ExternalSyntheticLambda1 desktopModeWindowDecoration$$ExternalSyntheticLambda1 = this.mLoadAppInfoRunnable;
        if (desktopModeWindowDecoration$$ExternalSyntheticLambda1 != null) {
            ((HandlerExecutor) this.mBgExecutor).removeCallbacks(desktopModeWindowDecoration$$ExternalSyntheticLambda1);
        }
        DesktopModeWindowDecoration$$ExternalSyntheticLambda23 desktopModeWindowDecoration$$ExternalSyntheticLambda23 = this.mSetAppInfoRunnable;
        if (desktopModeWindowDecoration$$ExternalSyntheticLambda23 != null) {
            ((HandlerExecutor) this.mMainExecutor).removeCallbacks(desktopModeWindowDecoration$$ExternalSyntheticLambda23);
        }
        WindowDecorTaskResourceLoader windowDecorTaskResourceLoader = this.mTaskResourceLoader;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        windowDecorTaskResourceLoader.existingTasks.remove(Integer.valueOf(runningTaskInfo.taskId));
        windowDecorTaskResourceLoader.taskToResourceCache.remove(Integer.valueOf(runningTaskInfo.taskId));
        windowDecorTaskResourceLoader.localeListOnCache.remove(Integer.valueOf(runningTaskInfo.taskId));
        DragResizeInputListener dragResizeInputListener = this.mDragResizeListener;
        if (dragResizeInputListener != null) {
            dragResizeInputListener.close();
            this.mDragResizeListener = null;
        }
        closeHandleMenu();
        closeManageWindowsMenu();
        DesktopModeWindowDecorViewModel.ExclusionRegionListenerImpl exclusionRegionListenerImpl = this.mExclusionRegionListener;
        int i = this.mTaskInfo.taskId;
        DesktopRepository desktopRepository = DesktopModeWindowDecorViewModel.this.mDesktopTasksController.taskRepository;
        desktopRepository.desktopExclusionRegions.delete(i);
        Executor executor = desktopRepository.desktopGestureExclusionExecutor;
        if (executor != null) {
            executor.execute(new DesktopRepository$removeExclusionRegion$1(desktopRepository));
        }
        ResizeVeil resizeVeil = this.mResizeVeil;
        if (resizeVeil != null) {
            resizeVeil.dispose();
            this.mResizeVeil = null;
        }
        disposeStatusBarInputLayer();
        WindowDecorationViewHolder windowDecorationViewHolder = this.mWindowDecorViewHolder;
        if (windowDecorationViewHolder != null) {
            windowDecorationViewHolder.close();
            this.mWindowDecorViewHolder = null;
        }
        boolean z = ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode;
        if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
            closeFreeformDimInputListener();
        }
        boolean z2 = CoreRune.MW_CAPTION_FREEFORM_MOTION;
        if (z2) {
            this.mDragPositioningCallback = null;
            if (z2) {
                this.mTaskPositioner = null;
            }
        }
        if (CoreRune.MW_CAPTION_TYPE) {
            this.mOnCaptionButtonClickListener = null;
            this.mOnCaptionTouchListener = null;
            this.mOnCaptionLongClickListener = null;
            this.mOnCaptionGenericMotionListener = null;
        }
        if (CoreRune.MW_CAPTION_HANDLE_ANIM && (decorationInputEventReceiver = this.mInputEventReceiver) != null) {
            decorationInputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
        if (CoreRune.MW_CAPTION_FREEFORM && (freeformOutline = this.mFreeformOutline) != null) {
            AdditionalViewHostViewContainer additionalViewHostViewContainer = freeformOutline.mFreeformOutline;
            if (additionalViewHostViewContainer != null) {
                additionalViewHostViewContainer.releaseView();
                freeformOutline.mFreeformOutline = null;
            }
            this.mFreeformOutline = null;
        }
        super.close();
    }

    public final void closeFreeformDimInputListener() {
        FreeformDimInputListener freeformDimInputListener = this.mFreeformStashDimInputListener;
        if (freeformDimInputListener == null) {
            return;
        }
        freeformDimInputListener.close();
        this.mFreeformStashDimInputListener = null;
    }

    public final void closeHandleMenu() {
        int i = 1;
        int i2 = 0;
        if (isHandleMenuActive()) {
            this.mWindowDecorViewHolder.onHandleMenuClosed();
            HandleMenu handleMenu = this.mHandleMenu;
            StandaloneCoroutine standaloneCoroutine = handleMenu.loadAppInfoJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            if (CoreRune.MW_CAPTION_POPUP) {
                PopupButtonPolicy popupButtonPolicy = handleMenu.buttonPolicy;
                if (popupButtonPolicy == null) {
                    popupButtonPolicy = null;
                }
                HandleMenu$$ExternalSyntheticLambda0 handleMenu$$ExternalSyntheticLambda0 = new HandleMenu$$ExternalSyntheticLambda0(handleMenu, i2);
                MenuPopupAnimator menuPopupAnimator = popupButtonPolicy.mMenuPopupAnimator;
                if (menuPopupAnimator != null) {
                    List list = menuPopupAnimator.animators;
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(menuPopupAnimator.menuPopupView, (Property<View, Float>) View.ALPHA, 0.0f);
                    ofFloat.setDuration(100L);
                    ((ArrayList) list).add(ofFloat);
                    List list2 = menuPopupAnimator.animators;
                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(menuPopupAnimator.menuPopupView, (Property<View, Float>) View.SCALE_X, 0.0f);
                    ofFloat2.setDuration(350L);
                    ((ArrayList) list2).add(ofFloat2);
                    List list3 = menuPopupAnimator.animators;
                    ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(menuPopupAnimator.menuPopupView, (Property<View, Float>) View.SCALE_Y, 0.0f);
                    ofFloat3.setDuration(350L);
                    ((ArrayList) list3).add(ofFloat3);
                    float f = (-menuPopupAnimator.captionHeight) / 2;
                    List list4 = menuPopupAnimator.animators;
                    ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(menuPopupAnimator.menuPopupView, (Property<View, Float>) View.TRANSLATION_Y, f);
                    ofFloat4.setDuration(100L);
                    ((ArrayList) list4).add(ofFloat4);
                    menuPopupAnimator.runAnimations(handleMenu$$ExternalSyntheticLambda0);
                }
            } else {
                HandleMenu.HandleMenuView handleMenuView = handleMenu.handleMenuView;
                if (handleMenuView != null) {
                    HandleMenu$$ExternalSyntheticLambda0 handleMenu$$ExternalSyntheticLambda02 = new HandleMenu$$ExternalSyntheticLambda0(handleMenu, i);
                    ActivityManager.RunningTaskInfo runningTaskInfo = handleMenuView.taskInfo;
                    if (runningTaskInfo == null) {
                        runningTaskInfo = null;
                    }
                    boolean isFullscreen = TaskInfoKt.isFullscreen(runningTaskInfo);
                    HandleMenuAnimator handleMenuAnimator = handleMenuView.animator;
                    if (!isFullscreen) {
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = handleMenuView.taskInfo;
                        if (runningTaskInfo2 == null) {
                            runningTaskInfo2 = null;
                        }
                        if (!TaskInfoKt.isMultiWindow(runningTaskInfo2)) {
                            List list5 = handleMenuAnimator.animators;
                            ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.SCALE_X, 0.0f);
                            ofFloat5.setStartDelay(20L);
                            ofFloat5.setDuration(50L);
                            ((ArrayList) list5).add(ofFloat5);
                            List list6 = handleMenuAnimator.animators;
                            ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.SCALE_Y, 0.0f);
                            ofFloat6.setStartDelay(20L);
                            ofFloat6.setDuration(50L);
                            ((ArrayList) list6).add(ofFloat6);
                            handleMenuAnimator.animateAppInfoPillFadeOut();
                            handleMenuAnimator.windowingPillClose();
                            handleMenuAnimator.moreActionsPillClose();
                            handleMenuAnimator.openInAppOrBrowserPillClose();
                            handleMenuAnimator.runAnimations(handleMenu$$ExternalSyntheticLambda02);
                        }
                    }
                    List list7 = handleMenuAnimator.animators;
                    ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.SCALE_X, 0.6f);
                    ofFloat7.setStartDelay(20L);
                    ofFloat7.setDuration(50L);
                    ((ArrayList) list7).add(ofFloat7);
                    List list8 = handleMenuAnimator.animators;
                    ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.SCALE_Y, 0.05f);
                    ofFloat8.setStartDelay(20L);
                    ofFloat8.setDuration(50L);
                    ((ArrayList) list8).add(ofFloat8);
                    float f2 = (-handleMenuAnimator.captionHeight) / 2;
                    List list9 = handleMenuAnimator.animators;
                    ObjectAnimator ofFloat9 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.TRANSLATION_Y, f2);
                    ofFloat9.setStartDelay(20L);
                    ofFloat9.setDuration(50L);
                    ((ArrayList) list9).add(ofFloat9);
                    handleMenuAnimator.animateAppInfoPillFadeOut();
                    handleMenuAnimator.windowingPillClose();
                    handleMenuAnimator.moreActionsPillClose();
                    handleMenuAnimator.openInAppOrBrowserPillClose();
                    handleMenuAnimator.runAnimations(handleMenu$$ExternalSyntheticLambda02);
                }
            }
            this.mHandleMenu = null;
            boolean z = ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode;
        }
    }

    public final void closeHandleMenuIfNeeded(MotionEvent motionEvent) {
        View view;
        if (isHandleMenuActive()) {
            PointF offsetCaptionLocation = offsetCaptionLocation(motionEvent);
            View findViewById = ((WindowDecorLinearLayout) this.mResult.mRootView).findViewById(R.id.open_menu_button);
            float f = offsetCaptionLocation.x;
            float f2 = offsetCaptionLocation.y;
            boolean z = true;
            boolean z2 = false;
            boolean z3 = findViewById != null && ((float) findViewById.getLeft()) <= f && ((float) findViewById.getRight()) >= f && ((float) findViewById.getTop()) <= f2 && ((float) findViewById.getBottom()) >= f2;
            HandleMenu handleMenu = this.mHandleMenu;
            AdditionalViewContainer additionalViewContainer = handleMenu.handleMenuViewContainer;
            if ((additionalViewContainer == null || (view = additionalViewContainer.getView()) == null) ? false : view.isLaidOut()) {
                if (DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue() && !handleMenu.taskInfo.isFreeform()) {
                    z2 = true;
                }
                if (z2) {
                    float f3 = offsetCaptionLocation.x;
                    Point point = handleMenu.globalMenuPosition;
                    PointF pointF = new PointF(f3 - point.x, offsetCaptionLocation.y - point.y);
                    int i = handleMenu.taskInfo.taskId;
                    SplitScreenController splitScreenController = handleMenu.splitScreenController;
                    if (splitScreenController.getSplitPosition(i) == 1) {
                        splitScreenController.getStageBounds(new Rect(), new Rect());
                        pointF.x += r9.width();
                    }
                    AdditionalViewContainer additionalViewContainer2 = handleMenu.handleMenuViewContainer;
                    z = HandleMenu.pointInView(additionalViewContainer2 != null ? additionalViewContainer2.getView() : null, pointF.x, pointF.y);
                } else {
                    AdditionalViewContainer additionalViewContainer3 = handleMenu.handleMenuViewContainer;
                    View view2 = additionalViewContainer3 != null ? additionalViewContainer3.getView() : null;
                    float f4 = offsetCaptionLocation.x;
                    PointF pointF2 = handleMenu.handleMenuPosition;
                    z = HandleMenu.pointInView(view2, f4 - pointF2.x, offsetCaptionLocation.y - pointF2.y);
                }
            }
            if (z || z3) {
                return;
            }
            closeHandleMenu();
        }
    }

    public final void closeManageWindowsMenu() {
        final ManageWindowsViewContainer manageWindowsViewContainer = this.mManageWindowsMenu;
        if (manageWindowsViewContainer != null) {
            ManageWindowsViewContainer.ManageWindowsView manageWindowsView = manageWindowsViewContainer.menuView;
            ManageWindowsViewContainer.ManageWindowsView manageWindowsView2 = manageWindowsView != null ? manageWindowsView : null;
            final Function0 function0 = new Function0() { // from class: com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    int i = ManageWindowsViewContainer.$r8$clinit;
                    ManageWindowsViewContainer.this.removeFromContainer();
                    return Unit.INSTANCE;
                }
            };
            manageWindowsView2.animateView(manageWindowsView2.rootView, 1.0f, 0.8f, 1.0f, 0.0f);
            ArrayList arrayList = (ArrayList) manageWindowsView2.iconViews;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                manageWindowsView2.animateView((SurfaceView) arrayList.get(i), 1.0f, 0.8f, 1.0f, 0.0f);
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(manageWindowsView2.animators);
            ((ArrayList) manageWindowsView2.animators).clear();
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer$ManageWindowsView$animateClose$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    Function0.this.invoke();
                }
            });
            animatorSet.start();
        }
        this.mManageWindowsMenu = null;
    }

    public final void closeMaximizeMenu() {
        int i = 0;
        if (isMaximizeMenuActive()) {
            MaximizeMenu maximizeMenu = this.mMaximizeMenu;
            final DesktopModeWindowDecoration$$ExternalSyntheticLambda8 desktopModeWindowDecoration$$ExternalSyntheticLambda8 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda8(this, i);
            final MaximizeMenu.MaximizeMenuView maximizeMenuView = maximizeMenu.maximizeMenuView;
            final AdditionalViewHostViewContainer additionalViewHostViewContainer = maximizeMenu.maximizeMenu;
            if (maximizeMenuView != null) {
                final Function0 function0 = new Function0() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        int i2 = MaximizeMenu.$r8$clinit;
                        AdditionalViewHostViewContainer additionalViewHostViewContainer2 = AdditionalViewHostViewContainer.this;
                        if (additionalViewHostViewContainer2 != null) {
                            additionalViewHostViewContainer2.releaseView();
                        }
                        desktopModeWindowDecoration$$ExternalSyntheticLambda8.invoke();
                        return Unit.INSTANCE;
                    }
                };
                maximizeMenuView.sizeToggleButton.setLayerType(2, null);
                maximizeMenuView.sizeToggleButtonText.setLayerType(2, null);
                maximizeMenuView.immersiveToggleButton.setLayerType(2, null);
                maximizeMenuView.immersiveToggleButtonText.setLayerType(2, null);
                AnimatorSet animatorSet = maximizeMenuView.menuAnimatorSet;
                if (animatorSet != null) {
                    animatorSet.cancel();
                }
                AnimatorSet animatorSet2 = new AnimatorSet();
                maximizeMenuView.menuAnimatorSet = animatorSet2;
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(maximizeMenuView.rootView, (Property<ViewGroup, Float>) View.SCALE_Y, 1.0f, 0.8f);
                ofFloat.setDuration(200L);
                Interpolator interpolator = Interpolators.FAST_OUT_LINEAR_IN;
                ofFloat.setInterpolator(interpolator);
                Unit unit = Unit.INSTANCE;
                final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.8f);
                ofFloat2.setDuration(200L);
                ofFloat2.setInterpolator(interpolator);
                ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateCloseMenu$2$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) ofFloat2.getAnimatedValue()).floatValue();
                        int measureHeight = maximizeMenuView.menuPadding - ((int) ((1 - floatValue) * r0.measureHeight()));
                        MaximizeMenu.MaximizeMenuView maximizeMenuView2 = maximizeMenuView;
                        View view = maximizeMenuView2.container;
                        int i2 = maximizeMenuView2.menuPadding;
                        view.setPadding(i2, measureHeight, i2, i2);
                    }
                });
                final ValueAnimator ofFloat3 = ValueAnimator.ofFloat(1.0f, 1.25f);
                ofFloat3.setDuration(200L);
                ofFloat3.setInterpolator(interpolator);
                ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateCloseMenu$3$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) ofFloat3.getAnimatedValue()).floatValue();
                        maximizeMenuView.sizeToggleButton.setScaleY(floatValue);
                        maximizeMenuView.immersiveToggleButton.setScaleY(floatValue);
                        maximizeMenuView.snapButtonsLayout.setScaleY(floatValue);
                        maximizeMenuView.sizeToggleButtonText.setScaleY(floatValue);
                        maximizeMenuView.immersiveToggleButtonText.setScaleY(floatValue);
                        maximizeMenuView.snapWindowText.setScaleY(floatValue);
                    }
                });
                ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(maximizeMenuView.rootView, (Property<ViewGroup, Float>) View.TRANSLATION_Y, 0.0f, maximizeMenuView.measureHeight() * (-0.19999999f));
                ofFloat4.setDuration(200L);
                ofFloat4.setInterpolator(interpolator);
                ObjectAnimator ofInt = ObjectAnimator.ofInt(maximizeMenuView.rootView.getBackground(), "alpha", 255, 0);
                ofInt.setStartDelay(33L);
                ofInt.setDuration(50L);
                final ValueAnimator ofFloat5 = ValueAnimator.ofFloat(1.0f, 0.0f);
                ofFloat5.setDuration(50L);
                ofFloat5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateCloseMenu$6$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) ofFloat5.getAnimatedValue()).floatValue();
                        maximizeMenuView.sizeToggleButton.setAlpha(floatValue);
                        maximizeMenuView.immersiveToggleButton.setAlpha(floatValue);
                        maximizeMenuView.snapButtonsLayout.setAlpha(floatValue);
                        maximizeMenuView.sizeToggleButtonText.setAlpha(floatValue);
                        maximizeMenuView.immersiveToggleButtonText.setAlpha(floatValue);
                        maximizeMenuView.snapWindowText.setAlpha(floatValue);
                    }
                });
                ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(maximizeMenuView.rootView, (Property<ViewGroup, Float>) View.TRANSLATION_Z, 1.0f, 0.0f);
                ofFloat6.setDuration(50L);
                animatorSet2.playTogether(ofFloat, ofFloat2, ofFloat3, ofFloat4, ofInt, ofFloat5, ofFloat6);
                AnimatorSet animatorSet3 = maximizeMenuView.menuAnimatorSet;
                if (animatorSet3 != null) {
                    animatorSet3.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateCloseMenu$$inlined$addListener$default$1
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            MaximizeMenu.MaximizeMenuView.this.sizeToggleButton.setLayerType(1, null);
                            MaximizeMenu.MaximizeMenuView.this.sizeToggleButtonText.setLayerType(1, null);
                            MaximizeMenu.MaximizeMenuView.this.immersiveToggleButton.setLayerType(1, null);
                            MaximizeMenu.MaximizeMenuView.this.immersiveToggleButtonText.setLayerType(1, null);
                            Function0 function02 = function0;
                            if (function02 != null) {
                                function02.invoke();
                            }
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationRepeat(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                        }
                    });
                }
                AnimatorSet animatorSet4 = maximizeMenuView.menuAnimatorSet;
                if (animatorSet4 != null) {
                    animatorSet4.start();
                }
            } else if (additionalViewHostViewContainer != null) {
                additionalViewHostViewContainer.releaseView();
            }
            maximizeMenu.maximizeMenu = null;
            maximizeMenu.maximizeMenuView = null;
            this.mMaximizeMenu = null;
        }
    }

    public final void createMaximizeMenu() {
        MaximizeMenu.MaximizeMenuView.ImmersiveConfig immersiveConfig;
        Rect rect;
        int i = 5;
        int i2 = 1;
        MaximizeMenuFactory maximizeMenuFactory = this.mMaximizeMenuFactory;
        SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
        RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer = this.mRootTaskDisplayAreaOrganizer;
        DisplayController displayController = this.mDisplayController;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        Context context = this.mContext;
        Function2 function2 = new Function2() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int desktopViewAppHeaderHeightId;
                DesktopModeWindowDecoration desktopModeWindowDecoration = DesktopModeWindowDecoration.this;
                desktopModeWindowDecoration.getClass();
                int intValue = ((Integer) obj).intValue();
                int intValue2 = ((Integer) obj2).intValue();
                Point point = new Point();
                DisplayLayout displayLayout = desktopModeWindowDecoration.mDisplayController.getDisplayLayout(desktopModeWindowDecoration.mTaskInfo.displayId);
                if (displayLayout == null) {
                    return point;
                }
                int i3 = displayLayout.mWidth;
                int i4 = displayLayout.mHeight;
                int windowingMode = desktopModeWindowDecoration.mTaskInfo.getWindowingMode();
                Resources resources = desktopModeWindowDecoration.mContext.getResources();
                boolean z = CoreRune.MW_CAPTION;
                if (z) {
                    desktopViewAppHeaderHeightId = desktopModeWindowDecoration.mCaptionType == 0 ? desktopModeWindowDecoration.mTaskInfo.isFreeform() ? R.dimen.mw_handle_freeform_inset : R.dimen.mw_handle_height : SystemBarUtils.getDesktopViewAppHeaderHeightId();
                } else {
                    desktopViewAppHeaderHeightId = windowingMode == 1 ? 17106379 : SystemBarUtils.getDesktopViewAppHeaderHeightId();
                }
                int loadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(resources, desktopViewAppHeaderHeightId);
                ImageButton imageButton = z ? (ImageButton) ((WindowDecorLinearLayout) desktopModeWindowDecoration.mResult.mRootView).findViewById(R.id.toggle_freeform_window) : (ImageButton) ((WindowDecorLinearLayout) desktopModeWindowDecoration.mResult.mRootView).findViewById(R.id.maximize_window);
                int[] iArr = new int[2];
                imageButton.getLocationInWindow(iArr);
                int width = (desktopModeWindowDecoration.mPositionInParent.x + iArr[0]) - ((intValue - imageButton.getWidth()) / 2);
                int i5 = desktopModeWindowDecoration.mPositionInParent.y + loadDimensionPixelSize;
                int i6 = i5 + intValue2;
                int i7 = width >= 0 ? width + intValue > i3 ? i3 - intValue : width : 0;
                if (i6 > i4) {
                    i5 = i4 - intValue2;
                }
                return new Point(i7, i5);
            }
        };
        Supplier supplier = this.mSurfaceControlTransactionSupplier;
        DesktopModeUiEventLogger desktopModeUiEventLogger = this.mDesktopModeUiEventLogger;
        ((DefaultMaximizeMenuFactory) maximizeMenuFactory).getClass();
        MaximizeMenu maximizeMenu = new MaximizeMenu(syncTransactionQueue, rootTaskDisplayAreaOrganizer, displayController, runningTaskInfo, context, function2, supplier, desktopModeUiEventLogger);
        this.mMaximizeMenu = maximizeMenu;
        DesktopModeFlags desktopModeFlags = DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP;
        boolean z = desktopModeFlags.isTrue() && this.mDesktopUserRepositories.getProfile(this.mTaskInfo.userId).isTaskInFullImmersiveState(this.mTaskInfo.taskId);
        boolean z2 = desktopModeFlags.isTrue() && this.mTaskInfo.isFreeform();
        boolean z3 = this.mTaskInfo.isResizeable;
        DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 = this.mOnMaximizeOrRestoreClickListener;
        DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda11 = this.mOnImmersiveOrRestoreClickListener;
        DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda92 = this.mOnLeftSnapClickListener;
        DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda93 = this.mOnRightSnapClickListener;
        DesktopModeWindowDecoration$$ExternalSyntheticLambda11 desktopModeWindowDecoration$$ExternalSyntheticLambda11 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda11(this, i2);
        DesktopModeWindowDecoration$$ExternalSyntheticLambda8 desktopModeWindowDecoration$$ExternalSyntheticLambda8 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda8(this, i);
        if (maximizeMenu.maximizeMenu != null) {
            return;
        }
        final SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) maximizeMenu.transactionSupplier.get();
        SurfaceControl.Builder builder = new SurfaceControl.Builder();
        maximizeMenu.rootTdaOrganizer.attachToDisplayArea(maximizeMenu.taskInfo.displayId, builder);
        maximizeMenu.leash = builder.setName("Maximize Menu").setContainerLayer().build();
        Configuration configuration = maximizeMenu.taskInfo.configuration;
        SurfaceControl surfaceControl = maximizeMenu.leash;
        if (surfaceControl == null) {
            surfaceControl = null;
        }
        WindowlessWindowManager windowlessWindowManager = new WindowlessWindowManager(configuration, surfaceControl, (InputTransferToken) null);
        Context context2 = maximizeMenu.decorWindowContext;
        int i3 = maximizeMenu.taskInfo.displayId;
        DisplayController displayController2 = maximizeMenu.displayController;
        boolean z4 = z2;
        maximizeMenu.viewHost = new SurfaceControlViewHost(context2, displayController2.mDisplayManager.getDisplay(i3), windowlessWindowManager, "MaximizeMenu");
        Context context3 = maximizeMenu.decorWindowContext;
        MaximizeMenu.MaximizeMenuView.SizeToggleDirection sizeToggleDirection = DesktopModeUtils.isTaskMaximized(maximizeMenu.taskInfo, displayController2) ? MaximizeMenu.MaximizeMenuView.SizeToggleDirection.RESTORE : MaximizeMenu.MaximizeMenuView.SizeToggleDirection.MAXIMIZE;
        if (z4) {
            immersiveConfig = new MaximizeMenu.MaximizeMenuView.ImmersiveConfig.Visible(z ? MaximizeMenu.MaximizeMenuView.ImmersiveToggleDirection.EXIT : MaximizeMenu.MaximizeMenuView.ImmersiveToggleDirection.ENTER);
        } else {
            immersiveConfig = MaximizeMenu.MaximizeMenuView.ImmersiveConfig.Hidden.INSTANCE;
        }
        MaximizeMenu.MaximizeMenuView maximizeMenuView = new MaximizeMenu.MaximizeMenuView(context3, maximizeMenu.desktopModeUiEventLogger, sizeToggleDirection, immersiveConfig, z3, maximizeMenu.menuPadding);
        ActivityManager.RunningTaskInfo runningTaskInfo2 = maximizeMenu.taskInfo;
        maximizeMenuView.taskInfo = runningTaskInfo2;
        ColorScheme colorScheme = maximizeMenuView.decorThemeUtil.getColorScheme(runningTaskInfo2);
        int m467toArgb8_81llA = ColorKt.m467toArgb8_81llA(colorScheme.surfaceContainerLow);
        int m467toArgb8_81llA2 = ColorKt.m467toArgb8_81llA(colorScheme.onSurface);
        int i4 = MaximizeMenu.MaximizeMenuView.WhenMappings.$EnumSwitchMapping$2[maximizeMenuView.sizeToggleDirection.ordinal()];
        if (i4 == 1) {
            rect = maximizeMenuView.maximizeFillPaddingRect;
        } else {
            if (i4 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            rect = maximizeMenuView.maximizeRestoreFillPaddingRect;
        }
        MaximizeMenu.MaximizeMenuView.MenuStyle.MaximizeOption maximizeOption = new MaximizeMenu.MaximizeMenuView.MenuStyle.MaximizeOption(maximizeMenuView.createMaximizeOrImmersiveDrawable(m467toArgb8_81llA, colorScheme, rect));
        MaximizeMenu.MaximizeMenuView.MenuStyle.ImmersiveOption immersiveOption = new MaximizeMenu.MaximizeMenuView.MenuStyle.ImmersiveOption(maximizeMenuView.createMaximizeOrImmersiveDrawable(m467toArgb8_81llA, colorScheme, maximizeMenuView.immersiveFillPaddingRect));
        long j = colorScheme.outlineVariant;
        int m467toArgb8_81llA3 = ColorKt.m467toArgb8_81llA(j);
        long j2 = colorScheme.primary;
        maximizeMenuView.style = new MaximizeMenu.MaximizeMenuView.MenuStyle(m467toArgb8_81llA, m467toArgb8_81llA2, maximizeOption, immersiveOption, new MaximizeMenu.MaximizeMenuView.MenuStyle.SnapOptions(m467toArgb8_81llA3, ThemeUtilsKt.withAlpha(ColorKt.m467toArgb8_81llA(j2), 102), ColorKt.m467toArgb8_81llA(j2), ThemeUtilsKt.withAlpha(ColorKt.m467toArgb8_81llA(j), 153), ColorKt.m467toArgb8_81llA(j2), m467toArgb8_81llA, ThemeUtilsKt.withAlpha(ColorKt.m467toArgb8_81llA(j2), 31)));
        Drawable background = maximizeMenuView.rootView.getBackground();
        MaximizeMenu.MaximizeMenuView.MenuStyle menuStyle = maximizeMenuView.style;
        if (menuStyle == null) {
            menuStyle = null;
        }
        background.setTint(menuStyle.backgroundColor);
        Button button = maximizeMenuView.sizeToggleButton;
        MaximizeMenu.MaximizeMenuView.MenuStyle menuStyle2 = maximizeMenuView.style;
        if (menuStyle2 == null) {
            menuStyle2 = null;
        }
        button.setBackground(menuStyle2.maximizeOption.drawable);
        TextView textView = maximizeMenuView.sizeToggleButtonText;
        MaximizeMenu.MaximizeMenuView.MenuStyle menuStyle3 = maximizeMenuView.style;
        if (menuStyle3 == null) {
            menuStyle3 = null;
        }
        textView.setTextColor(menuStyle3.textColor);
        Button button2 = maximizeMenuView.immersiveToggleButton;
        MaximizeMenu.MaximizeMenuView.MenuStyle menuStyle4 = maximizeMenuView.style;
        if (menuStyle4 == null) {
            menuStyle4 = null;
        }
        button2.setBackground(menuStyle4.immersiveOption.drawable);
        TextView textView2 = maximizeMenuView.immersiveToggleButtonText;
        MaximizeMenu.MaximizeMenuView.MenuStyle menuStyle5 = maximizeMenuView.style;
        if (menuStyle5 == null) {
            menuStyle5 = null;
        }
        textView2.setTextColor(menuStyle5.textColor);
        TextView textView3 = maximizeMenuView.snapWindowText;
        MaximizeMenu.MaximizeMenuView.MenuStyle menuStyle6 = maximizeMenuView.style;
        if (menuStyle6 == null) {
            menuStyle6 = null;
        }
        textView3.setTextColor(menuStyle6.textColor);
        maximizeMenuView.updateSplitSnapSelection(MaximizeMenu.MaximizeMenuView.SnapToHalfSelection.NONE);
        maximizeMenuView.onMaximizeClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9;
        maximizeMenuView.onImmersiveOrRestoreClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda11;
        maximizeMenuView.onLeftSnapClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda92;
        maximizeMenuView.onRightSnapClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda93;
        maximizeMenuView.onMenuHoverListener = desktopModeWindowDecoration$$ExternalSyntheticLambda11;
        maximizeMenuView.onOutsideTouchListener = desktopModeWindowDecoration$$ExternalSyntheticLambda8;
        maximizeMenuView.rootView.measure(0, 0);
        int measuredWidth = maximizeMenuView.rootView.getMeasuredWidth();
        int measureHeight = maximizeMenuView.measureHeight();
        maximizeMenu.menuPosition = (Point) maximizeMenu.positionSupplier.invoke(Integer.valueOf(measuredWidth), Integer.valueOf(measureHeight));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(measuredWidth, measureHeight, 2, 262152, -2);
        layoutParams.setTitle("Maximize Menu for Task=" + maximizeMenu.taskInfo.taskId);
        layoutParams.setTrustedOverlay();
        SurfaceControlViewHost surfaceControlViewHost = maximizeMenu.viewHost;
        if (surfaceControlViewHost == null) {
            surfaceControlViewHost = null;
        }
        surfaceControlViewHost.setView(maximizeMenuView.rootView, layoutParams);
        maximizeMenu.maximizeMenuView = maximizeMenuView;
        SurfaceControl surfaceControl2 = maximizeMenu.leash;
        if (surfaceControl2 == null) {
            surfaceControl2 = null;
        }
        SurfaceControl.Transaction layer = transaction.setLayer(surfaceControl2, 70000);
        SurfaceControl surfaceControl3 = maximizeMenu.leash;
        if (surfaceControl3 == null) {
            surfaceControl3 = null;
        }
        Point point = maximizeMenu.menuPosition;
        float f = (point == null ? null : point).x;
        if (point == null) {
            point = null;
        }
        SurfaceControl.Transaction position = layer.setPosition(surfaceControl3, f, point.y);
        SurfaceControl surfaceControl4 = maximizeMenu.leash;
        if (surfaceControl4 == null) {
            surfaceControl4 = null;
        }
        SurfaceControl.Transaction cornerRadius = position.setCornerRadius(surfaceControl4, maximizeMenu.cornerRadius);
        SurfaceControl surfaceControl5 = maximizeMenu.leash;
        if (surfaceControl5 == null) {
            surfaceControl5 = null;
        }
        cornerRadius.show(surfaceControl5);
        SurfaceControl surfaceControl6 = maximizeMenu.leash;
        if (surfaceControl6 == null) {
            surfaceControl6 = null;
        }
        SurfaceControlViewHost surfaceControlViewHost2 = maximizeMenu.viewHost;
        if (surfaceControlViewHost2 == null) {
            surfaceControlViewHost2 = null;
        }
        maximizeMenu.maximizeMenu = new AdditionalViewHostViewContainer(surfaceControl6, surfaceControlViewHost2, maximizeMenu.transactionSupplier);
        maximizeMenu.syncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$createMaximizeMenu$2
            @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
            public final void runWithTransaction(SurfaceControl.Transaction transaction2) {
                transaction2.merge(transaction);
                transaction.close();
            }
        });
        final MaximizeMenu.MaximizeMenuView maximizeMenuView2 = maximizeMenu.maximizeMenuView;
        if (maximizeMenuView2 != null) {
            final Function0 function0 = new Function0() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    int i5 = MaximizeMenu.$r8$clinit;
                    final MaximizeMenu.MaximizeMenuView maximizeMenuView3 = MaximizeMenu.MaximizeMenuView.this;
                    if (maximizeMenuView3.immersiveToggleButton.getVisibility() == 0) {
                        maximizeMenuView3.immersiveToggleButton.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$requestAccessibilityFocus$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                MaximizeMenu.MaximizeMenuView.this.immersiveToggleButton.sendAccessibilityEvent(8);
                            }
                        });
                    } else {
                        maximizeMenuView3.sizeToggleButton.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$requestAccessibilityFocus$2
                            @Override // java.lang.Runnable
                            public final void run() {
                                MaximizeMenu.MaximizeMenuView.this.sizeToggleButton.sendAccessibilityEvent(8);
                            }
                        });
                    }
                    return Unit.INSTANCE;
                }
            };
            maximizeMenuView2.sizeToggleButton.setLayerType(2, null);
            maximizeMenuView2.sizeToggleButtonText.setLayerType(2, null);
            maximizeMenuView2.immersiveToggleButton.setLayerType(2, null);
            maximizeMenuView2.immersiveToggleButtonText.setLayerType(2, null);
            AnimatorSet animatorSet = new AnimatorSet();
            maximizeMenuView2.menuAnimatorSet = animatorSet;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(maximizeMenuView2.rootView, (Property<ViewGroup, Float>) View.SCALE_Y, 0.8f, 1.0f);
            ofFloat.setDuration(300L);
            Interpolator interpolator = Interpolators.EMPHASIZED_DECELERATE;
            ofFloat.setInterpolator(interpolator);
            Unit unit = Unit.INSTANCE;
            final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.8f, 1.0f);
            ofFloat2.setDuration(300L);
            ofFloat2.setInterpolator(interpolator);
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateOpenMenu$2$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) ofFloat2.getAnimatedValue()).floatValue();
                    int measureHeight2 = maximizeMenuView2.menuPadding - ((int) ((1 - floatValue) * r0.measureHeight()));
                    MaximizeMenu.MaximizeMenuView maximizeMenuView3 = maximizeMenuView2;
                    View view = maximizeMenuView3.container;
                    int i5 = maximizeMenuView3.menuPadding;
                    view.setPadding(i5, measureHeight2, i5, i5);
                }
            });
            final ValueAnimator ofFloat3 = ValueAnimator.ofFloat(1.25f, 1.0f);
            ofFloat3.setDuration(300L);
            ofFloat3.setInterpolator(interpolator);
            ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateOpenMenu$3$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) ofFloat3.getAnimatedValue()).floatValue();
                    maximizeMenuView2.sizeToggleButton.setScaleY(floatValue);
                    maximizeMenuView2.immersiveToggleButton.setScaleY(floatValue);
                    maximizeMenuView2.snapButtonsLayout.setScaleY(floatValue);
                    maximizeMenuView2.sizeToggleButtonText.setScaleY(floatValue);
                    maximizeMenuView2.immersiveToggleButtonText.setScaleY(floatValue);
                    maximizeMenuView2.snapWindowText.setScaleY(floatValue);
                }
            });
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(maximizeMenuView2.rootView, (Property<ViewGroup, Float>) View.TRANSLATION_Y, maximizeMenuView2.measureHeight() * (-0.19999999f), 0.0f);
            ofFloat4.setDuration(300L);
            ofFloat4.setInterpolator(interpolator);
            ObjectAnimator ofInt = ObjectAnimator.ofInt(maximizeMenuView2.rootView.getBackground(), "alpha", 255);
            ofInt.setDuration(50L);
            final ValueAnimator ofFloat5 = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat5.setDuration(50L);
            ofFloat5.setStartDelay(33L);
            ofFloat5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateOpenMenu$6$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) ofFloat5.getAnimatedValue()).floatValue();
                    maximizeMenuView2.sizeToggleButton.setAlpha(floatValue);
                    maximizeMenuView2.immersiveToggleButton.setAlpha(floatValue);
                    maximizeMenuView2.snapButtonsLayout.setAlpha(floatValue);
                    maximizeMenuView2.sizeToggleButtonText.setAlpha(floatValue);
                    maximizeMenuView2.immersiveToggleButtonText.setAlpha(floatValue);
                    maximizeMenuView2.snapWindowText.setAlpha(floatValue);
                }
            });
            ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(maximizeMenuView2.rootView, (Property<ViewGroup, Float>) View.TRANSLATION_Z, 1.0f);
            ofFloat6.setDuration(50L);
            ofFloat6.setStartDelay(33L);
            animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3, ofFloat4, ofInt, ofFloat5, ofFloat6);
            AnimatorSet animatorSet2 = maximizeMenuView2.menuAnimatorSet;
            if (animatorSet2 != null) {
                animatorSet2.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateOpenMenu$$inlined$addListener$default$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        MaximizeMenu.MaximizeMenuView.this.sizeToggleButton.setLayerType(1, null);
                        MaximizeMenu.MaximizeMenuView.this.sizeToggleButtonText.setLayerType(1, null);
                        MaximizeMenu.MaximizeMenuView.this.immersiveToggleButton.setLayerType(1, null);
                        MaximizeMenu.MaximizeMenuView.this.immersiveToggleButtonText.setLayerType(1, null);
                        function0.invoke();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                    }
                });
            }
            AnimatorSet animatorSet3 = maximizeMenuView2.menuAnimatorSet;
            if (animatorSet3 != null) {
                animatorSet3.start();
            }
        }
    }

    public final void disposeStatusBarInputLayer() {
        if (isAppHandle(this.mWindowDecorViewHolder) && DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) {
            if (CoreRune.MW_CAPTION) {
                asMultiTaskingAppHandle(this.mWindowDecorViewHolder).disposeStatusBarInputLayer();
            } else {
                WindowDecorationViewHolder windowDecorationViewHolder = this.mWindowDecorViewHolder;
                (windowDecorationViewHolder instanceof AppHandleViewHolder ? (AppHandleViewHolder) windowDecorationViewHolder : null).disposeStatusBarInputLayer();
            }
        }
    }

    public final int getCaptionType(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (this.mInDesktopWindowing) {
            return 1;
        }
        if (runningTaskInfo.isFreeform()) {
            return this.mTaskOrganizer.getFreeformCaptionType(runningTaskInfo);
        }
        return 0;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final int getCaptionViewId() {
        return R.id.desktop_mode_caption;
    }

    public final Insets getDisplayInsetsState(int i) {
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId);
        InsetsState insetsState = this.mDisplayController.getInsetsState(this.mTaskInfo.displayId);
        if (displayLayout == null || insetsState == null) {
            return null;
        }
        displayLayout.getDisplayBounds(this.mTmpRect);
        return insetsState.calculateInsets(this.mTmpRect, i, true);
    }

    public final View getHandleRootView() {
        MultiTaskingHandleViewHolder asMultiTaskingAppHandle = asMultiTaskingAppHandle(this.mWindowDecorViewHolder);
        if (asMultiTaskingAppHandle != null) {
            return asMultiTaskingAppHandle.captionView;
        }
        return null;
    }

    public final int getOutlineCaptionHeight() {
        if (this.mInDesktopWindowing || TaskInfoKt.isTransparentCaptionBarAppearance(this.mTaskInfo)) {
            return 0;
        }
        return this.mResult.mCaptionHeight;
    }

    public final boolean isCaptionVisible() {
        return CoreRune.MW_CAPTION ? this.mTaskInfo.isVisible : this.mTaskInfo.isVisible && this.mIsCaptionVisible;
    }

    public final boolean isDecorCaptionState$1() {
        return this.mCaptionType == 1;
    }

    public final boolean isDecorHandleState() {
        return this.mCaptionType == 0;
    }

    public final boolean isHandleMenuActive() {
        return this.mHandleMenu != null;
    }

    public final boolean isMaximizeMenuActive() {
        return this.mMaximizeMenu != null;
    }

    public final void loadTaskNameAndIconInBackground(BiConsumer biConsumer) {
        WindowDecorationViewHolder windowDecorationViewHolder = this.mWindowDecorViewHolder;
        if (windowDecorationViewHolder == null) {
            return;
        }
        if (CoreRune.MW_CAPTION) {
            if (asMultiTaskingAppHeader(windowDecorationViewHolder) == null) {
                return;
            }
        } else if (asAppHeader(windowDecorationViewHolder) == null) {
            return;
        }
        DesktopModeWindowDecoration$$ExternalSyntheticLambda1 desktopModeWindowDecoration$$ExternalSyntheticLambda1 = this.mLoadAppInfoRunnable;
        if (desktopModeWindowDecoration$$ExternalSyntheticLambda1 != null) {
            ((HandlerExecutor) this.mBgExecutor).removeCallbacks(desktopModeWindowDecoration$$ExternalSyntheticLambda1);
        }
        DesktopModeWindowDecoration$$ExternalSyntheticLambda23 desktopModeWindowDecoration$$ExternalSyntheticLambda23 = this.mSetAppInfoRunnable;
        if (desktopModeWindowDecoration$$ExternalSyntheticLambda23 != null) {
            ((HandlerExecutor) this.mMainExecutor).removeCallbacks(desktopModeWindowDecoration$$ExternalSyntheticLambda23);
        }
        DesktopModeWindowDecoration$$ExternalSyntheticLambda1 desktopModeWindowDecoration$$ExternalSyntheticLambda12 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda1(this, biConsumer, 2);
        this.mLoadAppInfoRunnable = desktopModeWindowDecoration$$ExternalSyntheticLambda12;
        this.mBgExecutor.execute(desktopModeWindowDecoration$$ExternalSyntheticLambda12);
    }

    public final void notifyNoCaptionHandle() {
        boolean z = ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode;
    }

    public final PointF offsetCaptionLocation(MotionEvent motionEvent) {
        PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskOrganizer.getRunningTaskInfo(this.mTaskInfo.taskId);
        if (runningTaskInfo == null) {
            return pointF;
        }
        Point point = runningTaskInfo.positionInParent;
        pointF.offset(-point.x, -point.y);
        return pointF;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x03cb  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x03d9  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0421  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0328  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0424  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x02eb  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0305  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0376  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x039a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onAssistContentReceived(android.app.assist.AssistContent r51) {
        /*
            Method dump skipped, instructions count: 2532
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration.onAssistContentReceived(android.app.assist.AssistContent):void");
    }

    public final void onKeyguardStateChanged(boolean z, boolean z2) {
        if (!CoreRune.MW_CAPTION_KEYGUARD) {
            boolean z3 = this.mIsKeyguardVisibleAndOccluded;
            boolean z4 = z && z2;
            this.mIsKeyguardVisibleAndOccluded = z4;
            if (z3 != z4) {
                relayout(this.mTaskInfo, this.mHasGlobalFocus, this.mExclusionRegion);
            }
        }
        if (z != this.mIsKeyguardShowing) {
            this.mIsKeyguardShowing = z;
            relayout(this.mTaskInfo, this.mHasGlobalFocus, this.mExclusionRegion);
        }
    }

    public final void onMaximizeHoverStateChanged() {
        if (this.mIsMaximizeMenuHovered || this.mIsAppHeaderMaximizeButtonHovered) {
            this.mHandler.removeCallbacks(this.mCloseMaximizeWindowRunnable);
        } else if (isMaximizeMenuActive()) {
            this.mHandler.postDelayed(this.mCloseMaximizeWindowRunnable, 150L);
        }
    }

    public final void onTaskClosing() {
        MultiTaskingHeaderViewHolder asMultiTaskingAppHeader;
        DesktopImmersiveCaptionAnimator desktopImmersiveCaptionAnimator;
        TaskPositioner taskPositioner;
        OutlineView outlineView;
        FreeformOutline freeformOutline = this.mFreeformOutline;
        if (freeformOutline != null && (outlineView = freeformOutline.getOutlineView()) != null) {
            outlineView.mIsClosing = true;
            outlineView.invalidate();
        }
        if (CoreRune.MW_DND_FREEFORM_DISMISS_VIEW && (taskPositioner = this.mTaskPositioner) != null) {
            taskPositioner.onDragPositioningCancel();
        }
        if (!CoreRune.MW_CAPTION_DESKTOP_IMMERSIVE || !this.mInDesktopWindowing || (asMultiTaskingAppHeader = asMultiTaskingAppHeader(this.mWindowDecorViewHolder)) == null || (desktopImmersiveCaptionAnimator = asMultiTaskingAppHeader.captionButtonPolicy.mImmersiveAnimator) == null) {
            return;
        }
        desktopImmersiveCaptionAnimator.setPaused();
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, Region region) {
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mSurfaceControlTransactionSupplier.get();
        boolean z2 = !((DesktopConfigImpl) this.mDesktopConfig).isVeiledResizeEnabled && this.mTaskDragResizer.isResizingOrAnimating();
        boolean z3 = runningTaskInfo.isFreeform() && CoreRune.MW_CAPTION && getCaptionType(runningTaskInfo) == 1;
        relayout(runningTaskInfo, transaction, transaction, z3, z2, z, region);
        if (z3) {
            return;
        }
        transaction.apply();
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final void releaseViews(WindowContainerTransaction windowContainerTransaction) {
        FreeformOutline freeformOutline;
        closeHandleMenu();
        closeManageWindowsMenu();
        closeMaximizeMenu();
        if (CoreRune.MW_CAPTION_FREEFORM && !this.mIsFreeformCaptionTypeChanged && (freeformOutline = this.mFreeformOutline) != null) {
            AdditionalViewHostViewContainer additionalViewHostViewContainer = freeformOutline.mFreeformOutline;
            if (additionalViewHostViewContainer != null) {
                additionalViewHostViewContainer.releaseView();
                freeformOutline.mFreeformOutline = null;
            }
            this.mFreeformOutline = null;
        }
        if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
            this.mFreeformStashState.destroyStashDimOverlay();
        }
        super.releaseViews(windowContainerTransaction);
    }

    public final void setAnimatingTaskResizeOrReposition(boolean z) {
        if (CoreRune.MW_CAPTION || this.mRelayoutParams.mLayoutResId == R.layout.desktop_mode_app_handle) {
            return;
        }
        boolean isTaskInFullImmersiveState = this.mDesktopUserRepositories.getProfile(this.mTaskInfo.userId).isTaskInFullImmersiveState(this.mTaskInfo.taskId);
        AppHeaderViewHolder asAppHeader = asAppHeader(this.mWindowDecorViewHolder);
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        asAppHeader.bindData(new AppHeaderViewHolder.HeaderData(runningTaskInfo, DesktopModeUtils.isTaskMaximized(runningTaskInfo, this.mDisplayController), isTaskInFullImmersiveState, this.mHasGlobalFocus, canOpenMaximizeMenu(z), isCaptionVisible()));
    }

    public final boolean supportStatusBarInputLayer() {
        if (!this.mIsHandleOverlappedSystemBar) {
            return false;
        }
        if (CoreRune.MW_CAPTION_FULL_SCREEN) {
            if (this.mTaskInfo.getWindowingMode() != 1) {
                return false;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
            if (!runningTaskInfo.isTopFullScreenWindow && !runningTaskInfo.isFocused) {
                return false;
            }
        }
        return true;
    }

    public final String toString() {
        if (!CoreRune.MW_CAPTION) {
            StringBuilder sb = new StringBuilder("{mPositionInParent=");
            sb.append(this.mPositionInParent);
            sb.append(", taskId=");
            sb.append(this.mTaskInfo.taskId);
            sb.append(", windowingMode=");
            sb.append(WindowConfiguration.windowingModeToString(this.mTaskInfo.getWindowingMode()));
            sb.append(", isFocused=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.mHasGlobalFocus, "}");
        }
        StringBuilder sb2 = new StringBuilder("DesktopModeWindowDecoration{");
        sb2.append(Integer.toHexString(System.identityHashCode(this)));
        sb2.append(", p=");
        sb2.append(this.mPositionInParent);
        sb2.append(", d=");
        sb2.append(this.mTaskInfo.displayId);
        sb2.append(", tid=");
        sb2.append(this.mTaskInfo.taskId);
        sb2.append(", w=");
        sb2.append(WindowConfiguration.windowingModeToString(this.mTaskInfo.getWindowingMode()));
        sb2.append(", focused=");
        sb2.append(this.mHasGlobalFocus);
        sb2.append(", vh=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb2, this.mWindowDecorViewHolder != null, "}");
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final void updateCaptionContainerSurface(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, WindowDecoration.RelayoutResult relayoutResult) {
        SurfaceControl.Transaction reparent = transaction.reparent(surfaceControl, this.mDecorationContainerSurface);
        float f = relayoutResult.mCaptionX;
        boolean z = CoreRune.MW_CAPTION;
        reparent.setPosition(surfaceControl, f, z ? relayoutResult.mCaptionY : 0.0f).setMetadata(surfaceControl, 30, 3).setWindowCrop(surfaceControl, z ? -1 : relayoutResult.mCaptionWidth, z ? -1 : relayoutResult.mCaptionHeight).setLayer(surfaceControl, -1).show(surfaceControl);
    }

    public final void updateDisabledResizingEdge(DragResizeWindowGeometry.DisabledEdge disabledEdge, boolean z) {
        this.mDisabledResizingEdge = disabledEdge;
        boolean isTaskInFullImmersiveState = this.mDesktopUserRepositories.getCurrent().isTaskInFullImmersiveState(this.mTaskInfo.taskId);
        if (z) {
            return;
        }
        updateDragResizeListenerIfNeeded(this.mDecorationContainerSurface, isTaskInFullImmersiveState);
    }

    public final void updateDragResizeListener(SurfaceControl surfaceControl, final Consumer consumer) {
        boolean z = true;
        boolean z2 = surfaceControl != this.mDecorationContainerSurface;
        DragResizeInputListener dragResizeInputListener = this.mDragResizeListener;
        boolean z3 = dragResizeInputListener == null;
        if (!z2 && !z3) {
            z = false;
        }
        if (z2 && dragResizeInputListener != null) {
            dragResizeInputListener.close();
            this.mDragResizeListener = null;
        }
        if (CoreRune.MW_CAPTION_BUG_FIX) {
            if (this.mResult.mRootView == null) {
                Log.w("DesktopModeWindowDecoration", "updateDragResizeListener: failed, reason=no_root_view, " + this);
                return;
            } else if (this.mDecorationContainerSurface == null && z) {
                Log.w("DesktopModeWindowDecoration", "updateDragResizeListener: failed, reason=no_decor_surface, " + this);
                return;
            }
        }
        if (z) {
            this.mDragResizeListener = new DragResizeInputListener(this.mContext, WindowManagerGlobal.getWindowSession(), this.mMainExecutor, DesktopModeFlags.ENABLE_DRAG_RESIZE_SET_UP_IN_BG_THREAD.isTrue() ? this.mBgExecutor : this.mMainExecutor, this.mTaskInfo, this.mHandler, this.mChoreographer, this.mDisplay.getDisplayId(), this.mDecorationContainerSurface, this.mDragPositioningCallback, this.mSurfaceControlBuilderSupplier, this.mSurfaceControlTransactionSupplier, this.mDisplayController, this.mDesktopModeEventLogger);
        }
        final DragResizeInputListener dragResizeInputListener2 = this.mDragResizeListener;
        final int scaledTouchSlop = ViewConfiguration.get(((WindowDecorLinearLayout) this.mResult.mRootView).getContext()).getScaledTouchSlop();
        DisplayMetrics displayMetrics = ((WindowDecorLinearLayout) this.mResult.mRootView).getResources().getDisplayMetrics();
        int i = isDecorHandleState() ? this.mResult.mCaptionWidth : 0;
        int dipToPixel = MultiWindowUtils.dipToPixel(10, displayMetrics);
        int dipToPixel2 = MultiWindowUtils.dipToPixel(4, displayMetrics);
        int i2 = this.mRelayoutParams.mCornerRadius;
        WindowDecoration.RelayoutResult relayoutResult = this.mResult;
        final DragResizeWindowGeometry dragResizeWindowGeometry = new DragResizeWindowGeometry(i2, new Size(relayoutResult.mWidth, relayoutResult.mHeight), 48, dipToPixel2, dipToPixel, 48, this.mDisabledResizingEdge, i, this.mResult.mCaptionX);
        Runnable runnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(Boolean.valueOf(dragResizeInputListener2.setGeometry(dragResizeWindowGeometry, scaledTouchSlop, !DesktopModeWindowDecoration.this.mTaskInfo.isForceHidden)));
            }
        };
        if (dragResizeInputListener2.mInputEventReceiver != null) {
            runnable.run();
        } else {
            ((ArrayList) dragResizeInputListener2.mOnInitializedCallbacks).add(runnable);
        }
    }

    public final void updateDragResizeListenerIfNeeded(SurfaceControl surfaceControl, boolean z) {
        TaskPositioner taskPositioner;
        boolean z2;
        boolean equals = this.mTaskInfo.positionInParent.equals(this.mPositionInParent);
        boolean z3 = !equals;
        if (isDragResizable(this.mTaskInfo, z) && (!(z2 = CoreRune.MW_CAPTION_FREEFORM_STASH) || !this.mFreeformStashState.isStashed())) {
            if (z2) {
                closeFreeformDimInputListener();
            }
            updateDragResizeListener(surfaceControl, new DesktopModeWindowDecoration$$ExternalSyntheticLambda7(this, z3, z, 0));
            return;
        }
        if (!equals) {
            updateExclusionRegion(z);
        }
        DragResizeInputListener dragResizeInputListener = this.mDragResizeListener;
        if (dragResizeInputListener != null) {
            dragResizeInputListener.close();
            this.mDragResizeListener = null;
        }
        if (!CoreRune.MW_CAPTION_FREEFORM_RESIZE_GUIDE || (taskPositioner = this.mTaskPositioner) == null) {
            return;
        }
        taskPositioner.closeFreeformResizeGuide();
    }

    public final void updateExclusionRegion(boolean z) {
        Region region;
        int desktopViewAppHeaderHeightId;
        this.mPositionInParent.set(this.mTaskInfo.positionInParent);
        DesktopModeWindowDecorViewModel.ExclusionRegionListenerImpl exclusionRegionListenerImpl = this.mExclusionRegionListener;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        int i = runningTaskInfo.taskId;
        if (this.mDragResizeListener == null || !isDragResizable(runningTaskInfo, z)) {
            region = new Region();
        } else {
            DragResizeInputListener.TaskResizeInputEventReceiver taskResizeInputEventReceiver = this.mDragResizeListener.mInputEventReceiver;
            taskResizeInputEventReceiver.getClass();
            region = new Region();
            DragResizeWindowGeometry dragResizeWindowGeometry = taskResizeInputEventReceiver.mDragResizeWindowGeometry;
            dragResizeWindowGeometry.mTaskEdges.union(region);
            dragResizeWindowGeometry.mLargeTaskCorners.union(region);
        }
        if (!z) {
            int i2 = this.mResult.mWidth;
            int windowingMode = this.mTaskInfo.getWindowingMode();
            Resources resources = this.mContext.getResources();
            if (CoreRune.MW_CAPTION) {
                desktopViewAppHeaderHeightId = this.mCaptionType == 0 ? this.mTaskInfo.isFreeform() ? R.dimen.mw_handle_freeform_inset : R.dimen.mw_handle_height : SystemBarUtils.getDesktopViewAppHeaderHeightId();
            } else {
                desktopViewAppHeaderHeightId = windowingMode == 1 ? 17106379 : SystemBarUtils.getDesktopViewAppHeaderHeightId();
            }
            region.union(new Rect(0, 0, i2, WindowDecoration.loadDimensionPixelSize(resources, desktopViewAppHeaderHeightId)));
            Point point = this.mPositionInParent;
            region.translate(point.x, point.y);
        }
        final DesktopRepository desktopRepository = DesktopModeWindowDecorViewModel.this.mDesktopTasksController.taskRepository;
        desktopRepository.desktopExclusionRegions.put(i, region);
        Executor executor = desktopRepository.desktopGestureExclusionExecutor;
        if (executor != null) {
            executor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$updateTaskExclusionRegions$1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopRepository desktopRepository2 = DesktopRepository.this;
                    Consumer consumer = desktopRepository2.desktopGestureExclusionListener;
                    if (consumer != null) {
                        consumer.accept(DesktopRepository.access$calculateDesktopExclusionRegion(desktopRepository2));
                    }
                }
            });
        }
    }

    public final void updateHoverAndPressStatus(MotionEvent motionEvent) {
        if (this.mResult.mRootView == null || DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) {
            return;
        }
        View findViewById = ((WindowDecorLinearLayout) this.mResult.mRootView).findViewById(R.id.caption_handle);
        boolean z = false;
        boolean z2 = !isHandleMenuActive() && checkTouchEventInFocusedCaptionHandle(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        findViewById.setHovered(z2 && actionMasked != 1);
        if ((z2 && actionMasked == 0) || (findViewById.isPressed() && actionMasked != 1 && actionMasked != 3)) {
            z = true;
        }
        findViewById.setPressed(z);
        if (isHandleMenuActive()) {
            this.mHandleMenu.checkMotionEvent(motionEvent);
        }
    }

    public final void updateMaximizeMenu(SurfaceControl.Transaction transaction, boolean z) {
        int i;
        if (isDragResizable(this.mTaskInfo, z) && isMaximizeMenuActive()) {
            if (!this.mTaskInfo.isVisible()) {
                closeMaximizeMenu();
                return;
            }
            MaximizeMenu maximizeMenu = this.mMaximizeMenu;
            MaximizeMenu.MaximizeMenuView maximizeMenuView = maximizeMenu.maximizeMenuView;
            if (maximizeMenuView != null) {
                maximizeMenuView.rootView.measure(0, 0);
                i = maximizeMenuView.rootView.getMeasuredWidth();
            } else {
                i = 0;
            }
            Integer valueOf = Integer.valueOf(i);
            MaximizeMenu.MaximizeMenuView maximizeMenuView2 = maximizeMenu.maximizeMenuView;
            Point point = (Point) maximizeMenu.positionSupplier.invoke(valueOf, Integer.valueOf(maximizeMenuView2 != null ? maximizeMenuView2.measureHeight() : 0));
            maximizeMenu.menuPosition = point;
            SurfaceControl surfaceControl = maximizeMenu.leash;
            if (surfaceControl == null) {
                surfaceControl = null;
            }
            float f = (point == null ? null : point).x;
            if (point == null) {
                point = null;
            }
            transaction.setPosition(surfaceControl, f, point.y);
        }
    }

    public final void updateResizeVeil(Rect rect) {
        ResizeVeil resizeVeil = this.mResizeVeil;
        if (resizeVeil.isVisible) {
            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) resizeVeil.surfaceControlTransactionSupplier.get();
            if (resizeVeil.isVisible) {
                ValueAnimator valueAnimator = resizeVeil.veilAnimator;
                if (valueAnimator != null && valueAnimator.isStarted()) {
                    valueAnimator.removeAllUpdateListeners();
                    valueAnimator.end();
                }
                resizeVeil.relayout(rect, transaction);
            }
            transaction.apply();
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final void updateTaskSurface(WindowDecoration.RelayoutParams relayoutParams, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, WindowDecoration.RelayoutResult relayoutResult) {
        if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
            if (this.mFreeformStashState.isStashed()) {
                return;
            }
            if (this.mTaskPositioner.getTaskMotionController() != null && this.mTaskPositioner.getTaskMotionController().isBoundsAnimating()) {
                return;
            }
        }
        Point point = this.mTaskInfo.positionInParent;
        boolean equals = this.mTaskPosition.equals(point);
        boolean z = this.mFreeformAdjustImeController.mAnimating;
        boolean z2 = false;
        if (CoreRune.MW_CAPTION_DESKTOP_IMMERSIVE) {
            DesktopImmersiveController desktopImmersiveController = this.mDesktopImmersiveController;
            int i = this.mTaskInfo.taskId;
            List list = desktopImmersiveController.pendingImmersiveTransitions;
            if (list == null || !((ArrayList) list).isEmpty()) {
                ArrayList arrayList = (ArrayList) list;
                int size = arrayList.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    Object obj = arrayList.get(i2);
                    i2++;
                    if (((DesktopImmersiveController.PendingTransition) obj).taskId == i) {
                        z2 = true;
                        break;
                    }
                }
            }
        }
        if (!z && !z2 && !equals) {
            transaction2.setPosition(this.mTaskSurface, point.x, point.y);
            this.mTaskPosition.set(point);
        }
        super.updateTaskSurface(relayoutParams, transaction, transaction2, relayoutResult);
    }

    public DesktopModeWindowDecoration(Context context, Context context2, DisplayController displayController, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, SplitScreenController splitScreenController, DesktopUserRepositories desktopUserRepositories, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Handler handler, ShellExecutor shellExecutor, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, ShellExecutor shellExecutor2, Choreographer choreographer, SyncTransactionQueue syncTransactionQueue, AppHeaderViewHolder.Factory factory, AppHandleViewHolder.Factory factory2, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, AppToWebGenericLinksParser appToWebGenericLinksParser, AssistContentRequester assistContentRequester, Supplier<SurfaceControl.Builder> supplier, Supplier<SurfaceControl.Transaction> supplier2, Supplier<WindowContainerTransaction> supplier3, Supplier<SurfaceControl> supplier4, WindowManagerWrapper windowManagerWrapper, WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory, WindowDecorViewHostSupplier windowDecorViewHostSupplier, MaximizeMenuFactory maximizeMenuFactory, HandleMenuFactory handleMenuFactory, MultiInstanceHelper multiInstanceHelper, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, DesktopModeEventLogger desktopModeEventLogger, DesktopModeUiEventLogger desktopModeUiEventLogger, DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopState desktopState, DesktopConfig desktopConfig, MultiTaskingHeaderViewHolder.Factory factory3, HandleMenuHelpController handleMenuHelpController, DesktopImmersiveController desktopImmersiveController) {
        super(context, context2, displayController, shellTaskOrganizer, runningTaskInfo, surfaceControl, supplier, supplier2, supplier3, supplier4, surfaceControlViewHostFactory, windowDecorViewHostSupplier, desktopModeEventLogger);
        this.mRelayoutParams = new WindowDecoration.RelayoutParams();
        this.mDisabledResizingEdge = DragResizeWindowGeometry.DisabledEdge.NONE;
        this.mResult = new WindowDecoration.RelayoutResult();
        this.mPositionInParent = new Point();
        this.mIsAppHeaderMaximizeButtonHovered = false;
        this.mIsMaximizeMenuHovered = false;
        this.mCloseMaximizeWindowRunnable = new DesktopModeWindowDecoration$$ExternalSyntheticLambda0(this, 0);
        this.mIsRecentsTransitionRunning = false;
        this.mIsDragging = false;
        this.mCaptionType = -1;
        FreeformStashState freeformStashState = new FreeformStashState();
        this.mFreeformStashState = freeformStashState;
        this.mLastStableBounds = new Rect();
        this.mTmpRect = new Rect();
        this.mTmpRect2 = new Rect();
        this.mTaskPosition = new Point();
        this.mIsFreeformCaptionTypeChanged = false;
        this.mSplitScreenController = splitScreenController;
        this.mHandler = handler;
        this.mMainExecutor = shellExecutor;
        this.mMainDispatcher = mainCoroutineDispatcher;
        this.mBgScope = coroutineScope;
        this.mBgExecutor = shellExecutor2;
        this.mChoreographer = choreographer;
        this.mSyncQueue = syncTransactionQueue;
        this.mAppHeaderViewHolderFactory = factory;
        this.mAppHandleViewHolderFactory = factory2;
        this.mRootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.mGenericLinksParser = appToWebGenericLinksParser;
        this.mAssistContentRequester = assistContentRequester;
        this.mMaximizeMenuFactory = maximizeMenuFactory;
        this.mHandleMenuFactory = handleMenuFactory;
        this.mMultiInstanceHelper = multiInstanceHelper;
        this.mWindowManagerWrapper = windowManagerWrapper;
        this.mWindowDecorCaptionHandleRepository = windowDecorCaptionHandleRepository;
        this.mDesktopUserRepositories = desktopUserRepositories;
        this.mTaskResourceLoader = windowDecorTaskResourceLoader;
        windowDecorTaskResourceLoader.existingTasks.add(Integer.valueOf(runningTaskInfo.taskId));
        this.mDesktopModeCompatPolicy = desktopModeCompatPolicy;
        this.mDesktopModeUiEventLogger = desktopModeUiEventLogger;
        this.mDesktopState = desktopState;
        this.mDesktopConfig = desktopConfig;
        int i = runningTaskInfo.displayId;
        DesktopStateImpl.Companion.getClass();
        this.mInDesktopWindowing = DesktopStateImpl.Companion.inDesktopWindowing(i);
        if (CoreRune.MW_CAPTION_TYPE) {
            this.mCaptionType = getCaptionType(runningTaskInfo);
        }
        this.mHeaderViewHolderFactory = factory3;
        this.mFreeformAdjustImeController = new FreeformAdjustImeController(this, this.mDisplayController, this.mTaskOrganizer, this.mTaskSurface);
        if (CoreRune.MW_CAPTION_FREEFORM_STASH && !this.mInDesktopWindowing) {
            freeformStashState.mAnimType = -1;
            freeformStashState.mAnimating = false;
            freeformStashState.setStashed(-1);
        }
        if (CoreRune.MW_CAPTION_HELP_POPUP) {
            this.mHandleMenuHelpController = handleMenuHelpController;
        }
        this.mIsTaskMaximized = DesktopModeUtils.isTaskMaximized(runningTaskInfo, this.mDisplayController);
        if (CoreRune.MW_CAPTION_DESKTOP_IMMERSIVE) {
            this.mDesktopImmersiveController = desktopImmersiveController;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:257:0x0776, code lost:
    
        if (r2 < (r3 + r0)) goto L342;
     */
    /* JADX WARN: Code restructure failed: missing block: B:258:0x0778, code lost:
    
        r0 = r2 - r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x0793, code lost:
    
        if (r2 < (r3 + r0)) goto L342;
     */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x03c1  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x040f  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0439  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0554  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0754  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0817  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x0851  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0873  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0886  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x089a  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x090f  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x08b8  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x08f6  */
    /* JADX WARN: Removed duplicated region for block: B:355:0x08a1  */
    /* JADX WARN: Removed duplicated region for block: B:383:0x0ba9  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x0bf0  */
    /* JADX WARN: Removed duplicated region for block: B:422:0x0a16  */
    /* JADX WARN: Removed duplicated region for block: B:532:0x0739  */
    /* JADX WARN: Removed duplicated region for block: B:553:0x0293  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void relayout(android.app.ActivityManager.RunningTaskInfo r42, android.view.SurfaceControl.Transaction r43, android.view.SurfaceControl.Transaction r44, boolean r45, boolean r46, boolean r47, android.graphics.Region r48) {
        /*
            Method dump skipped, instructions count: 3104
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration.relayout(android.app.ActivityManager$RunningTaskInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, boolean, boolean, boolean, android.graphics.Region):void");
    }
}
