package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.app.assist.AssistContent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.Trace;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Property;
import android.util.Size;
import android.view.Choreographer;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.IWindowSession;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.InsetsState;
import android.view.LayoutInflater;
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
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import android.window.InputTransferToken;
import android.window.SurfaceSyncGroup;
import android.window.WindowContainerTransaction;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.compose.material3.ColorScheme;
import androidx.compose.ui.graphics.ColorKt;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.internal.policy.DesktopModeCompatUtils;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.apptoweb.AppToWebGenericLinksParser;
import com.android.wm.shell.apptoweb.AppToWebUtils;
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
import com.android.wm.shell.shared.bubbles.ContextUtils;
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
import com.android.wm.shell.windowdecor.WindowManagerWrapper;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalSystemViewContainer;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewContainer;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewHostViewContainer;
import com.android.wm.shell.windowdecor.common.ButtonBackgroundDrawableUtilsKt;
import com.android.wm.shell.windowdecor.common.DrawableInsets;
import com.android.wm.shell.windowdecor.common.ThemeUtilsKt;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier;
import com.android.wm.shell.windowdecor.extension.InsetsStateKt;
import com.android.wm.shell.windowdecor.extension.TaskInfoKt;
import com.android.wm.shell.windowdecor.policy.CaptionButtonPolicy;
import com.android.wm.shell.windowdecor.policy.CaptionPopupButtonPolicy;
import com.android.wm.shell.windowdecor.policy.DexFreeformPopupButtonPolicy;
import com.android.wm.shell.windowdecor.policy.FreeformPopupButtonPolicy;
import com.android.wm.shell.windowdecor.policy.FreeformScrollPopupButtonPolicy;
import com.android.wm.shell.windowdecor.policy.FullScreenPopupButtonPolicy;
import com.android.wm.shell.windowdecor.policy.PopupButtonPolicy;
import com.android.wm.shell.windowdecor.policy.SplitPopupButtonPolicy;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingDecorViewModel;
import com.android.wm.shell.windowdecor.viewholder.AppHandleViewHolder;
import com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder;
import com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder;
import com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder;
import com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder;
import com.android.wm.shell.windowdecor.widget.CaptionButton;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.StandaloneCoroutine;

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
    public final DesktopModeWindowDecoration$$ExternalSyntheticLambda0 mCloseHelpPopupRunnable;
    public final DesktopModeWindowDecoration$$ExternalSyntheticLambda0 mCloseMaximizeWindowRunnable;
    public final DesktopConfig mDesktopConfig;
    public final DesktopImmersiveController mDesktopImmersiveController;
    public final DesktopModeCompatPolicy mDesktopModeCompatPolicy;
    public final DesktopModeUiEventLogger mDesktopModeUiEventLogger;
    public final DesktopState mDesktopState;
    public final DesktopTilingDecorViewModel mDesktopTilingDecorViewModel;
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
    public boolean mIsCutoutLocatedInCenterChanged;
    public boolean mIsDesktopModeSupportedOnDisplay;
    public boolean mIsDragging;
    public boolean mIsFreeformCaptionTypeChanged;
    public boolean mIsHandleOverlappedSystemBar;
    public boolean mIsKeyguardShowing;
    public boolean mIsMaximizeMenuHovered;
    public boolean mIsRecentsTransitionRunning;
    public boolean mIsTaskMaximized;
    public boolean mIsTaskResizing;
    public final Rect mLastDisplayBounds;
    public final Rect mLastStableBounds;
    public final Rect mLeftSideHintFrame;
    public DesktopModeWindowDecoration$$ExternalSyntheticLambda2 mLoadAppInfoRunnable;
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
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 mOnChangeAspectRatioClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 mOnImmersiveOrRestoreClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 mOnLeftSnapClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 mOnManageWindowsClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 mOnMaximizeHoverListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 mOnMaximizeOrRestoreClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 mOnNewWindowClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 mOnRestartClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 mOnRightSnapClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda16 mOnToDesktopClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 mOnToFloatClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 mOnToFullscreenClickListener;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 mOnToSplitscreenClickListener;
    public OpenByDefaultDialog mOpenByDefaultDialog;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda16 mOpenInBrowserClickListener;
    public final Point mPositionInParent;
    public final WindowDecoration.RelayoutParams mRelayoutParams;
    public ResizeVeil mResizeVeil;
    public final WindowDecoration.RelayoutResult mResult;
    public final Rect mRightSideHintFrame;
    public final RootTaskDisplayAreaOrganizer mRootTaskDisplayAreaOrganizer;
    public DesktopModeWindowDecoration$$ExternalSyntheticLambda19 mSetAppInfoRunnable;
    public boolean mShouldShowRestartNotification;
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

    /* renamed from: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$2, reason: invalid class name */
    public class AnonymousClass2 implements OpenByDefaultDialog.DialogLifecycleListener {
        public AnonymousClass2() {
        }
    }

    class CapturedLink {
        public final long mTimeStamp;
        public final Uri mUri;
        public boolean mUsed;

        public CapturedLink(Uri uri, long j) {
            this.mUri = uri;
            this.mTimeStamp = j;
        }
    }

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
            MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle;
            boolean z = false;
            if (inputEvent instanceof MotionEvent) {
                if (this.mIsCaptionTypeHandle) {
                    DesktopModeWindowDecoration desktopModeWindowDecoration = DesktopModeWindowDecoration.this;
                    MotionEvent motionEvent = (MotionEvent) inputEvent;
                    Rect bounds = desktopModeWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds();
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();
                    if (motionEvent.getAction() == 0 && bounds.contains(x, y) && (multiTaskingHandleViewHolderAsMultiTaskingAppHandle = DesktopModeWindowDecoration.asMultiTaskingAppHandle(desktopModeWindowDecoration.mWindowDecorViewHolder)) != null && (!multiTaskingHandleViewHolderAsMultiTaskingAppHandle.handleInputBounds.contains(x, y) || !multiTaskingHandleViewHolderAsMultiTaskingAppHandle.handleHideAnimator.mIsHandleViewVisible)) {
                        HandleHideAnimator handleHideAnimator = multiTaskingHandleViewHolderAsMultiTaskingAppHandle.handleHideAnimator;
                        if (!handleHideAnimator.mIsHandleMenuActive && !handleHideAnimator.mIsHandleTouching) {
                            handleHideAnimator.cancelAllHandleAnim();
                            if (handleHideAnimator.mIsHandleViewVisible) {
                                handleHideAnimator.mHandler.removeCallbacks(handleHideAnimator.mHideRunnable);
                                handleHideAnimator.delayedHide(true);
                            } else {
                                handleHideAnimator.show(handleHideAnimator.mDelayedHideFromTouchRunnable, false, true, false);
                            }
                        }
                    }
                } else {
                    DesktopModeWindowDecoration desktopModeWindowDecoration2 = DesktopModeWindowDecoration.this;
                    MotionEvent motionEvent2 = (MotionEvent) inputEvent;
                    MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolderAsMultiTaskingAppHeader = DesktopModeWindowDecoration.asMultiTaskingAppHeader(desktopModeWindowDecoration2.mWindowDecorViewHolder);
                    if (multiTaskingHeaderViewHolderAsMultiTaskingAppHeader != null) {
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
                                CaptionButtonPolicy captionButtonPolicy = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader.captionButtonPolicy;
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

    public class Factory {
    }

    public DesktopModeWindowDecoration(Context context, Context context2, DisplayController displayController, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, SplitScreenController splitScreenController, DesktopUserRepositories desktopUserRepositories, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Handler handler, ShellExecutor shellExecutor, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, ShellExecutor shellExecutor2, Choreographer choreographer, SyncTransactionQueue syncTransactionQueue, AppHeaderViewHolder.Factory factory, AppHandleViewHolder.Factory factory2, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, AppToWebGenericLinksParser appToWebGenericLinksParser, AssistContentRequester assistContentRequester, WindowDecorViewHostSupplier windowDecorViewHostSupplier, MultiInstanceHelper multiInstanceHelper, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, DesktopModeEventLogger desktopModeEventLogger, DesktopModeUiEventLogger desktopModeUiEventLogger, DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopState desktopState, DesktopConfig desktopConfig, MultiTaskingHeaderViewHolder.Factory factory3, HandleMenuHelpController handleMenuHelpController, DesktopImmersiveController desktopImmersiveController, DesktopTilingDecorViewModel desktopTilingDecorViewModel, DesktopModeWindowDecorViewModel.DecorViewModelState decorViewModelState) {
        this(context, context2, displayController, windowDecorTaskResourceLoader, splitScreenController, desktopUserRepositories, shellTaskOrganizer, runningTaskInfo, surfaceControl, handler, shellExecutor, mainCoroutineDispatcher, coroutineScope, shellExecutor2, choreographer, syncTransactionQueue, factory, factory2, rootTaskDisplayAreaOrganizer, appToWebGenericLinksParser, assistContentRequester, new DesktopModeWindowDecoration$$ExternalSyntheticLambda7(0), new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0(), new DesktopModeWindowDecoration$$ExternalSyntheticLambda7(1), new DesktopModeWindowDecoration$$ExternalSyntheticLambda7(2), new WindowManagerWrapper((WindowManager) context.getSystemService(WindowManager.class)), new WindowDecoration.SurfaceControlViewHostFactory() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration.1
        }, windowDecorViewHostSupplier, DefaultMaximizeMenuFactory.INSTANCE, DefaultHandleMenuFactory.INSTANCE, multiInstanceHelper, windowDecorCaptionHandleRepository, desktopModeEventLogger, desktopModeUiEventLogger, desktopModeCompatPolicy, desktopState, desktopConfig, factory3, handleMenuHelpController, desktopImmersiveController, desktopTilingDecorViewModel, decorViewModelState);
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

    public static int getMultitaskingCaptionHeightIdStatic(int i, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        return i == 0 ? runningTaskInfo.isFreeform() ? R.dimen.mw_handle_freeform_inset : (CoreRune.IS_TABLET_DEVICE && runningTaskInfo.getWindowingMode() == 1 && z) ? R.dimen.mw_handle_full_screen_height_tablet : R.dimen.mw_handle_height : SystemBarUtils.getDesktopViewAppHeaderHeightId();
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

    /* JADX WARN: Removed duplicated region for block: B:182:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x02f9  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0351  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void updateRelayoutParams(WindowDecoration.RelayoutParams relayoutParams, Context context, ActivityManager.RunningTaskInfo runningTaskInfo, SplitScreenController splitScreenController, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, InsetsState insetsState, boolean z7, Region region, boolean z8, boolean z9, DesktopConfig desktopConfig, int i, boolean z10, boolean z11, boolean z12) {
        int i2;
        int i3;
        boolean z13;
        boolean z14 = CoreRune.MW_CAPTION;
        if (z14) {
            boolean zIsParallelMultiSplit = splitScreenController.isParallelMultiSplit();
            if (i != 0) {
                i2 = z10 ? R.layout.mw_dex_caption_menu : R.layout.mw_caption_menu;
            } else if (runningTaskInfo.getWindowingMode() == 1 && z12) {
                i2 = CoreRune.IS_TABLET_DEVICE ? R.layout.mw_handle_view_full_screen_desktop_tablet_layout : R.layout.mw_handle_view_full_screen_desktop_layout;
            } else if (!CoreRune.MW_CAPTION_SPLIT_PARALLEL || !zIsParallelMultiSplit) {
                i2 = (!runningTaskInfo.isSplitScreen() || (runningTaskInfo.configuration.windowConfiguration.getStagePosition() & 64) == 0) ? CoreRune.IS_TABLET_DEVICE ? R.layout.mw_handle_view_layout_tablet : R.layout.mw_handle_view_layout : CoreRune.IS_TABLET_DEVICE ? R.layout.mw_handle_view_split_bottom_layout_tablet : R.layout.mw_handle_view_split_bottom_layout;
            } else if (runningTaskInfo.configuration.windowConfiguration.getBounds().top == 0) {
                if (CoreRune.IS_TABLET_DEVICE) {
                }
            } else if (CoreRune.IS_TABLET_DEVICE) {
            }
        } else {
            i2 = runningTaskInfo.getWindowingMode() == 5 ? R.layout.desktop_mode_app_header : R.layout.desktop_mode_app_handle;
        }
        boolean z15 = CoreRune.MW_CAPTION_TYPE;
        boolean z16 = !z15 ? i2 != R.layout.desktop_mode_app_header : i == 0;
        boolean z17 = !z15 ? i2 != R.layout.desktop_mode_app_handle : i != 0;
        relayoutParams.reset();
        relayoutParams.mRunningTaskInfo = runningTaskInfo;
        relayoutParams.mLayoutResId = i2;
        relayoutParams.mCaptionHeightId = z15 ? getMultitaskingCaptionHeightIdStatic(i, runningTaskInfo, z12) : runningTaskInfo.getWindowingMode() == 1 ? 17106380 : SystemBarUtils.getDesktopViewAppHeaderHeightId();
        if (z15) {
            i3 = i == 0 ? CoreRune.IS_TABLET_DEVICE ? R.dimen.mw_handle_width_tablet : (runningTaskInfo.getWindowingMode() == 1 && z12) ? R.dimen.mw_handle_full_screen_width : R.dimen.mw_handle_width : 0;
        } else if (relayoutParams.mLayoutResId == R.layout.desktop_mode_app_handle) {
            i3 = R.dimen.desktop_mode_fullscreen_decor_caption_width;
        }
        relayoutParams.mCaptionWidthId = i3;
        relayoutParams.mHasGlobalFocus = z7;
        relayoutParams.mDisplayExclusionRegion.set(region);
        relayoutParams.mAsyncViewHost = z17;
        if (z15) {
            relayoutParams.mCaptionType = i;
        }
        relayoutParams.mIsCaptionVisible = z14 ? !z4 : (DesktopModeFlags.ENABLE_DESKTOP_IMMERSIVE_DRAG_BUGFIX.isTrue() && z6) || (!DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue() ? !(runningTaskInfo.isFreeform() || (z3 && !z4)) : !(!z5 ? !(runningTaskInfo.isFreeform() || (z3 && !z4)) : !(z3 && !z4)));
        relayoutParams.mIsInsetSource = !z14 ? (!z16 || z5) && !(!splitScreenController.isLeftRightSplit() && splitScreenController.getSplitPosition(runningTaskInfo.taskId) == 1) : !runningTaskInfo.isFreeform() || z5;
        if (z16) {
            if (TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo)) {
                if (DesktopModeFlags.ENABLE_ACCESSIBLE_CUSTOM_HEADERS.isTrue()) {
                    relayoutParams.mLimitTouchRegionToSystemAreas = true;
                } else {
                    relayoutParams.mInputFeatures |= 4;
                }
            } else if (DesktopModeFlags.ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION.isTrue()) {
                if (z9) {
                    relayoutParams.mShouldSetAppBounds = true;
                } else {
                    relayoutParams.mInsetSourceFlags |= 4;
                }
            }
            if (DesktopModeFlags.ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION_ALWAYS.isTrue()) {
                if (z9) {
                    relayoutParams.mShouldSetAppBounds = true;
                } else {
                    relayoutParams.mInsetSourceFlags |= 16;
                }
            }
            if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue() && z5) {
                relayoutParams.mCaptionTopPadding = insetsState.calculateInsets(runningTaskInfo.getConfiguration().windowConfiguration.getBounds(), WindowInsets.Type.systemBars() & (~WindowInsets.Type.captionBar()), false).top;
            }
            WindowDecoration.RelayoutParams.OccludingCaptionElement occludingCaptionElement = new WindowDecoration.RelayoutParams.OccludingCaptionElement();
            occludingCaptionElement.mWidthResId = R.dimen.desktop_mode_customizable_caption_margin_start;
            occludingCaptionElement.mAlignment = WindowDecoration.RelayoutParams.OccludingCaptionElement.Alignment.START;
            ((ArrayList) relayoutParams.mOccludingCaptionElements).add(occludingCaptionElement);
            WindowDecoration.RelayoutParams.OccludingCaptionElement occludingCaptionElement2 = new WindowDecoration.RelayoutParams.OccludingCaptionElement();
            occludingCaptionElement2.mWidthResId = R.dimen.desktop_mode_customizable_caption_margin_end;
            if (DesktopModeFlags.ENABLE_MINIMIZE_BUTTON.isTrue()) {
                occludingCaptionElement2.mWidthResId = R.dimen.desktop_mode_customizable_caption_with_minimize_button_margin_end;
            }
            occludingCaptionElement2.mAlignment = WindowDecoration.RelayoutParams.OccludingCaptionElement.Alignment.END;
            ((ArrayList) relayoutParams.mOccludingCaptionElements).add(occludingCaptionElement2);
        } else if (z17 && !DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue() && CoreRune.MW_CAPTION_HANDLE_SYSTEM_INPUT) {
            relayoutParams.mInputFeatures |= 1;
        }
        int iLoadDimensionPixelSize = -1;
        if (z14) {
            if (runningTaskInfo.isFreeform()) {
                if (z17) {
                    relayoutParams.mInsetSourceFlags |= 80;
                    if (!TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo)) {
                        relayoutParams.mInsetSourceFlags |= 4;
                    }
                } else {
                    relayoutParams.mInsetSourceFlags |= 32;
                }
            }
            relayoutParams.mShadowRadius = runningTaskInfo.isFreeform() ? context.getResources().getDimensionPixelSize(R.dimen.mw_freeform_shadow) : -1;
        } else if (z16) {
            ((DesktopConfigImpl) desktopConfig).getClass();
            if (z7 ? DesktopConfigImpl.USE_WINDOW_SHADOWS_FOCUSED_WINDOW : DesktopConfigImpl.USE_WINDOW_SHADOWS) {
                boolean zIsTrue = DesktopExperienceFlags.ENABLE_DYNAMIC_RADIUS_COMPUTATION_BUGFIX.isTrue();
                int i4 = R.dimen.freeform_decor_shadow_unfocused_thickness;
                if (zIsTrue) {
                    if (z7) {
                        i4 = R.dimen.freeform_decor_shadow_focused_thickness;
                    }
                    relayoutParams.mShadowRadiusId = i4;
                } else {
                    relayoutParams.mShadowRadius = z7 ? context.getResources().getDimensionPixelSize(R.dimen.freeform_decor_shadow_focused_thickness) : context.getResources().getDimensionPixelSize(R.dimen.freeform_decor_shadow_unfocused_thickness);
                }
            } else if (DesktopExperienceFlags.ENABLE_DYNAMIC_RADIUS_COMPUTATION_BUGFIX.isTrue()) {
                relayoutParams.mShadowRadiusId = 0;
            } else {
                relayoutParams.mShadowRadius = -1;
            }
        }
        relayoutParams.mApplyStartTransactionOnDraw = z;
        relayoutParams.mSetTaskVisibilityPositionAndCrop = z2;
        Configuration configuration = new Configuration();
        if (!(DesktopModeFlags.ENABLE_APP_HEADER_WITH_TASK_DENSITY.isTrue() && z16) && ((DesktopConfigImpl) desktopConfig).useDesktopOverrideDensity) {
            configuration.setTo(context.getResources().getConfiguration());
        } else {
            configuration.setTo(runningTaskInfo.configuration);
        }
        relayoutParams.mWindowDecorConfig = configuration;
        DesktopConfigImpl desktopConfigImpl = (DesktopConfigImpl) desktopConfig;
        if (desktopConfigImpl.useRoundedCorners) {
            boolean zIsTrue2 = DesktopExperienceFlags.ENABLE_DYNAMIC_RADIUS_COMPUTATION_BUGFIX.isTrue();
            int i5 = R.dimen.desktop_windowing_freeform_rounded_corner_radius;
            if (zIsTrue2) {
                if (z8 || relayoutParams.mLayoutResId != R.layout.desktop_mode_app_header) {
                    i5 = 0;
                }
                relayoutParams.mCornerRadiusId = i5;
            } else if (CoreRune.MW_CAPTION_FREEFORM) {
                if (runningTaskInfo.isFreeform() && !z8) {
                    if (z5) {
                        iLoadDimensionPixelSize = 0;
                    } else {
                        int i6 = runningTaskInfo.displayId;
                        DesktopStateImpl.Companion.getClass();
                        if (!DesktopStateImpl.Companion.inDesktopWindowing(i6) || i6 == 0) {
                            iLoadDimensionPixelSize = (int) MultiWindowUtils.getFreeformRoundedCornerRadius(context);
                        } else if (!z11) {
                            iLoadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(context.getResources(), R.dimen.mw_desktop_external_display_corner_radius);
                        }
                    }
                }
                relayoutParams.mCornerRadius = iLoadDimensionPixelSize;
            } else {
                if (!z8 && relayoutParams.mLayoutResId == R.layout.desktop_mode_app_header) {
                    iLoadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(context.getResources(), R.dimen.desktop_windowing_freeform_rounded_corner_radius);
                }
                relayoutParams.mCornerRadius = iLoadDimensionPixelSize;
            }
        }
        if (!CoreRune.MW_CAPTION_FREEFORM) {
            desktopConfigImpl.getClass();
            z13 = runningTaskInfo.isFreeform() && (!desktopConfigImpl.isVeiledResizeEnabled || DesktopModeFlags.ENABLE_OPAQUE_BACKGROUND_FOR_TRANSPARENT_WINDOWS.isTrue());
        }
        relayoutParams.mShouldSetBackground = z13;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final int calculateCaptionPositionX(int i, int i2) {
        TaskPositioner taskPositioner;
        MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle;
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId);
        int i3 = (i - i2) / 2;
        if (isDecorCaptionState$1() || !this.mTaskInfo.isFreeform() || displayLayout == null || (!((taskPositioner = this.mTaskPositioner) == null || taskPositioner.isAllowTouches()) || ((CoreRune.MW_CAPTION_FREEFORM_STASH && this.mFreeformStashState.isStashed()) || (multiTaskingHandleViewHolderAsMultiTaskingAppHandle = asMultiTaskingAppHandle(this.mWindowDecorViewHolder)) == null))) {
            return i3;
        }
        Rect bounds = this.mTaskInfo.getConfiguration().windowConfiguration.getBounds();
        int i4 = displayLayout.mWidth;
        ImageButton imageButton = multiTaskingHandleViewHolderAsMultiTaskingAppHandle.captionHandle;
        if (imageButton instanceof HandleImageButton) {
            HandleImageButton handleImageButton = (HandleImageButton) imageButton;
            if (handleImageButton.isPaddingAdjusted) {
                handleImageButton.setPadding(handleImageButton.initHorizontalPadding, handleImageButton.getPaddingTop(), handleImageButton.initHorizontalPadding, handleImageButton.getPaddingBottom());
                handleImageButton.isPaddingAdjusted = false;
            }
        }
        int iWidth = (bounds.width() - i2) / 2;
        int i5 = bounds.left;
        int i6 = iWidth + i5;
        if (i6 <= 0 || i6 + i2 >= i4) {
            HandleImageButton handleImageButton2 = (HandleImageButton) multiTaskingHandleViewHolderAsMultiTaskingAppHandle.captionHandle;
            if (i5 < 0) {
                int iAbs = Math.abs(i5);
                int i7 = bounds.right;
                if (i2 <= i7) {
                    return AbsActionBarView$$ExternalSyntheticOutline0.m(i7, i2, 2, iAbs);
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
        return iWidth;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final int calculateCaptionPositionY() {
        if (this.mTaskInfo.isFreeform()) {
            return 0;
        }
        Resources resources = this.mContext.getResources();
        if (CoreRune.MW_CAPTION_SPLIT_PARALLEL && this.mTaskInfo.isSplitScreen() && this.mSplitScreenController.isParallelMultiSplit()) {
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

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final int calculateScreenBurnOffset(int i, int i2) {
        if (CaptionGlobalState.FULLSCREEN_HANDLER_ENABLED && this.mTaskInfo.getWindowingMode() == 1) {
            return new Random().nextInt((i2 - i) + 1) + i;
        }
        return 0;
    }

    public final Rect calculateValidDragArea() {
        int iLoadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(this.mContext.getResources(), R.dimen.desktop_mode_app_details_width_minus_text) + (CoreRune.MW_CAPTION ? 0 : ((AppHeaderViewHolder) this.mWindowDecorViewHolder).appNameTextView.getWidth());
        int iLoadDimensionPixelSize2 = WindowDecoration.loadDimensionPixelSize(this.mContext.getResources(), R.dimen.freeform_required_visible_empty_space_in_header);
        int iLoadDimensionPixelSize3 = WindowDecoration.loadDimensionPixelSize(this.mContext.getResources(), R.dimen.desktop_mode_right_edge_buttons_width);
        int iWidth = this.mTaskInfo.configuration.windowConfiguration.getBounds().width();
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId);
        int i = displayLayout.mWidth;
        Rect rect = new Rect();
        displayLayout.getStableBounds(rect, false);
        int i2 = iLoadDimensionPixelSize + iLoadDimensionPixelSize3 + iLoadDimensionPixelSize2;
        return new Rect(i2 <= iWidth ? (-iWidth) + iLoadDimensionPixelSize2 + iLoadDimensionPixelSize3 : 0, rect.top, i2 > iWidth ? i - iWidth : (i - iLoadDimensionPixelSize2) - iLoadDimensionPixelSize, rect.bottom - iLoadDimensionPixelSize2);
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
            View viewFindViewById = ((WindowDecorLinearLayout) this.mResult.mRootView).findViewById(R.id.desktop_mode_caption).findViewById(R.id.caption_handle);
            boolean z = !isHandleMenuActive() && checkTouchEventInFocusedCaptionHandle(motionEvent);
            if (motionEvent.getActionMasked() == 1 && z) {
                viewFindViewById.performClick();
            }
            if (isHandleMenuActive() && isAppHandle(this.mWindowDecorViewHolder) && !desktopModeFlags.isTrue()) {
                this.mHandleMenu.checkMotionEvent(motionEvent);
                closeHandleMenuIfNeeded(motionEvent);
            }
        }
    }

    public final boolean checkTouchEventInCaption(MotionEvent motionEvent) {
        PointF pointFOffsetCaptionLocation = offsetCaptionLocation(motionEvent);
        if (CoreRune.MW_CAPTION_HANDLE && this.mTaskInfo.isFreeform() && isDecorHandleState()) {
            float f = pointFOffsetCaptionLocation.x;
            if (f >= this.mResult.mCaptionX && f <= r4 + r6.mCaptionWidth) {
                float f2 = pointFOffsetCaptionLocation.y;
                if (f2 >= 0.0f && f2 <= r6.mCaptionTouchableHeight) {
                    return true;
                }
            }
            return false;
        }
        float f3 = pointFOffsetCaptionLocation.x;
        if (f3 >= this.mResult.mCaptionX && f3 <= r4 + r6.mCaptionWidth) {
            float f4 = pointFOffsetCaptionLocation.y;
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
        DesktopModeWindowDecoration$$ExternalSyntheticLambda2 desktopModeWindowDecoration$$ExternalSyntheticLambda2 = this.mLoadAppInfoRunnable;
        if (desktopModeWindowDecoration$$ExternalSyntheticLambda2 != null) {
            ((HandlerExecutor) this.mBgExecutor).removeCallbacks(desktopModeWindowDecoration$$ExternalSyntheticLambda2);
        }
        DesktopModeWindowDecoration$$ExternalSyntheticLambda19 desktopModeWindowDecoration$$ExternalSyntheticLambda19 = this.mSetAppInfoRunnable;
        if (desktopModeWindowDecoration$$ExternalSyntheticLambda19 != null) {
            ((HandlerExecutor) this.mMainExecutor).removeCallbacks(desktopModeWindowDecoration$$ExternalSyntheticLambda19);
        }
        WindowDecorTaskResourceLoader windowDecorTaskResourceLoader = this.mTaskResourceLoader;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        windowDecorTaskResourceLoader.existingTasks.remove(Integer.valueOf(runningTaskInfo.taskId));
        windowDecorTaskResourceLoader.taskToResourceCache.remove(Integer.valueOf(runningTaskInfo.taskId));
        windowDecorTaskResourceLoader.localeListOnCache.remove(Integer.valueOf(runningTaskInfo.taskId));
        if (CoreRune.MW_CAPTION_BUG_FIX) {
            windowDecorTaskResourceLoader.densityListOnCache.remove(Integer.valueOf(runningTaskInfo.taskId));
        }
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

    /* JADX WARN: Removed duplicated region for block: B:31:0x00fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void closeHandleMenu() {
        final int i = 1;
        final int i2 = 0;
        if (isHandleMenuActive()) {
            this.mWindowDecorViewHolder.onHandleMenuClosed();
            final HandleMenu handleMenu = this.mHandleMenu;
            StandaloneCoroutine standaloneCoroutine = handleMenu.loadAppInfoJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            if (CoreRune.MW_CAPTION_POPUP) {
                PopupButtonPolicy popupButtonPolicy = handleMenu.buttonPolicy;
                if (popupButtonPolicy == null) {
                    popupButtonPolicy = null;
                }
                Function0 function0 = new Function0() { // from class: com.android.wm.shell.windowdecor.HandleMenu$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        HandleMenu handleMenu2 = handleMenu;
                        switch (i2) {
                            case 0:
                                HandleMenu.Companion companion = HandleMenu.Companion;
                                handleMenu2.closeMenuPopupImmediately();
                                break;
                            default:
                                AdditionalViewContainer additionalViewContainer = handleMenu2.handleMenuViewContainer;
                                if (additionalViewContainer != null) {
                                    additionalViewContainer.releaseView();
                                }
                                handleMenu2.handleMenuViewContainer = null;
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
                MenuPopupAnimator menuPopupAnimator = popupButtonPolicy.mMenuPopupAnimator;
                if (menuPopupAnimator != null) {
                    List list = menuPopupAnimator.animators;
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(menuPopupAnimator.menuPopupView, (Property<View, Float>) View.ALPHA, 0.0f);
                    objectAnimatorOfFloat.setDuration(100L);
                    ((ArrayList) list).add(objectAnimatorOfFloat);
                    List list2 = menuPopupAnimator.animators;
                    ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(menuPopupAnimator.menuPopupView, (Property<View, Float>) View.SCALE_X, 0.0f);
                    objectAnimatorOfFloat2.setDuration(350L);
                    ((ArrayList) list2).add(objectAnimatorOfFloat2);
                    List list3 = menuPopupAnimator.animators;
                    ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(menuPopupAnimator.menuPopupView, (Property<View, Float>) View.SCALE_Y, 0.0f);
                    objectAnimatorOfFloat3.setDuration(350L);
                    ((ArrayList) list3).add(objectAnimatorOfFloat3);
                    float f = (-menuPopupAnimator.captionHeight) / 2;
                    List list4 = menuPopupAnimator.animators;
                    ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(menuPopupAnimator.menuPopupView, (Property<View, Float>) View.TRANSLATION_Y, f);
                    objectAnimatorOfFloat4.setDuration(100L);
                    ((ArrayList) list4).add(objectAnimatorOfFloat4);
                    menuPopupAnimator.runAnimations(function0);
                }
            } else {
                HandleMenu.HandleMenuView handleMenuView = handleMenu.handleMenuView;
                if (handleMenuView != null) {
                    Function0 function02 = new Function0() { // from class: com.android.wm.shell.windowdecor.HandleMenu$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            HandleMenu handleMenu2 = handleMenu;
                            switch (i) {
                                case 0:
                                    HandleMenu.Companion companion = HandleMenu.Companion;
                                    handleMenu2.closeMenuPopupImmediately();
                                    break;
                                default:
                                    AdditionalViewContainer additionalViewContainer = handleMenu2.handleMenuViewContainer;
                                    if (additionalViewContainer != null) {
                                        additionalViewContainer.releaseView();
                                    }
                                    handleMenu2.handleMenuViewContainer = null;
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    ActivityManager.RunningTaskInfo runningTaskInfo = handleMenuView.taskInfo;
                    if (runningTaskInfo == null) {
                        runningTaskInfo = null;
                    }
                    boolean zIsFullscreen = TaskInfoKt.isFullscreen(runningTaskInfo);
                    HandleMenuAnimator handleMenuAnimator = handleMenuView.animator;
                    if (zIsFullscreen) {
                        List list5 = handleMenuAnimator.animators;
                        ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.SCALE_X, 0.6f);
                        objectAnimatorOfFloat5.setStartDelay(20L);
                        objectAnimatorOfFloat5.setDuration(50L);
                        ((ArrayList) list5).add(objectAnimatorOfFloat5);
                        List list6 = handleMenuAnimator.animators;
                        ObjectAnimator objectAnimatorOfFloat6 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.SCALE_Y, 0.05f);
                        objectAnimatorOfFloat6.setStartDelay(20L);
                        objectAnimatorOfFloat6.setDuration(50L);
                        ((ArrayList) list6).add(objectAnimatorOfFloat6);
                        float f2 = (-handleMenuAnimator.captionHeight) / 2;
                        List list7 = handleMenuAnimator.animators;
                        ObjectAnimator objectAnimatorOfFloat7 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.TRANSLATION_Y, f2);
                        objectAnimatorOfFloat7.setStartDelay(20L);
                        objectAnimatorOfFloat7.setDuration(50L);
                        ((ArrayList) list7).add(objectAnimatorOfFloat7);
                        handleMenuAnimator.animateAppInfoPillFadeOut();
                        handleMenuAnimator.windowingPillClose();
                        handleMenuAnimator.moreActionsPillClose();
                        handleMenuAnimator.openInAppOrBrowserPillClose();
                        handleMenuAnimator.runAnimations(function02);
                    } else {
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = handleMenuView.taskInfo;
                        if (runningTaskInfo2 == null) {
                            runningTaskInfo2 = null;
                        }
                        if (!TaskInfoKt.isMultiWindow(runningTaskInfo2)) {
                            List list8 = handleMenuAnimator.animators;
                            ObjectAnimator objectAnimatorOfFloat8 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.SCALE_X, 0.0f);
                            objectAnimatorOfFloat8.setStartDelay(20L);
                            objectAnimatorOfFloat8.setDuration(50L);
                            ((ArrayList) list8).add(objectAnimatorOfFloat8);
                            List list9 = handleMenuAnimator.animators;
                            ObjectAnimator objectAnimatorOfFloat9 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.SCALE_Y, 0.0f);
                            objectAnimatorOfFloat9.setStartDelay(20L);
                            objectAnimatorOfFloat9.setDuration(50L);
                            ((ArrayList) list9).add(objectAnimatorOfFloat9);
                            handleMenuAnimator.animateAppInfoPillFadeOut();
                            handleMenuAnimator.windowingPillClose();
                            handleMenuAnimator.moreActionsPillClose();
                            handleMenuAnimator.openInAppOrBrowserPillClose();
                            handleMenuAnimator.runAnimations(function02);
                        }
                    }
                }
            }
            this.mHandleMenu = null;
            boolean z = ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode;
            if (CoreRune.MW_CAPTION_DESKTOP_RESTART) {
                this.mShouldShowRestartNotification = false;
            }
            if (CoreRune.MW_CAPTION_HELP_POPUP && this.mHandler.hasCallbacks(this.mCloseHelpPopupRunnable)) {
                this.mHandler.removeCallbacks(this.mCloseHelpPopupRunnable);
            }
        }
    }

    public final void closeHandleMenuIfNeeded(MotionEvent motionEvent) {
        View view;
        if (isHandleMenuActive()) {
            PointF pointFOffsetCaptionLocation = offsetCaptionLocation(motionEvent);
            View viewFindViewById = ((WindowDecorLinearLayout) this.mResult.mRootView).findViewById(R.id.open_menu_button);
            float f = pointFOffsetCaptionLocation.x;
            float f2 = pointFOffsetCaptionLocation.y;
            boolean zPointInView = true;
            boolean z = false;
            boolean z2 = viewFindViewById != null && ((float) viewFindViewById.getLeft()) <= f && ((float) viewFindViewById.getRight()) >= f && ((float) viewFindViewById.getTop()) <= f2 && ((float) viewFindViewById.getBottom()) >= f2;
            HandleMenu handleMenu = this.mHandleMenu;
            AdditionalViewContainer additionalViewContainer = handleMenu.handleMenuViewContainer;
            if ((additionalViewContainer == null || (view = additionalViewContainer.getView()) == null) ? false : view.isLaidOut()) {
                if (DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue() && !handleMenu.taskInfo.isFreeform()) {
                    z = true;
                }
                if (z) {
                    float f3 = pointFOffsetCaptionLocation.x;
                    Point point = handleMenu.globalMenuPosition;
                    PointF pointF = new PointF(f3 - point.x, pointFOffsetCaptionLocation.y - point.y);
                    int i = handleMenu.taskInfo.taskId;
                    SplitScreenController splitScreenController = handleMenu.splitScreenController;
                    if (splitScreenController.getSplitPosition(i) == 1) {
                        splitScreenController.getStageBounds(new Rect(), new Rect());
                        pointF.x += r9.width();
                    }
                    AdditionalViewContainer additionalViewContainer2 = handleMenu.handleMenuViewContainer;
                    zPointInView = HandleMenu.pointInView(additionalViewContainer2 != null ? additionalViewContainer2.getView() : null, pointF.x, pointF.y);
                } else {
                    AdditionalViewContainer additionalViewContainer3 = handleMenu.handleMenuViewContainer;
                    View view2 = additionalViewContainer3 != null ? additionalViewContainer3.getView() : null;
                    float f4 = pointFOffsetCaptionLocation.x;
                    PointF pointF2 = handleMenu.handleMenuPosition;
                    zPointInView = HandleMenu.pointInView(view2, f4 - pointF2.x, pointFOffsetCaptionLocation.y - pointF2.y);
                }
            }
            if (zPointInView || z2) {
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
                    manageWindowsViewContainer.removeFromContainer();
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
                    function0.invoke();
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
            final DesktopModeWindowDecoration$$ExternalSyntheticLambda6 desktopModeWindowDecoration$$ExternalSyntheticLambda6 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda6(this, i);
            final MaximizeMenu.MaximizeMenuView maximizeMenuView = maximizeMenu.maximizeMenuView;
            final AdditionalViewHostViewContainer additionalViewHostViewContainer = maximizeMenu.maximizeMenu;
            if (maximizeMenuView != null) {
                final Function0 function0 = new Function0() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        int i2 = MaximizeMenu.$r8$clinit;
                        AdditionalViewHostViewContainer additionalViewHostViewContainer2 = additionalViewHostViewContainer;
                        if (additionalViewHostViewContainer2 != null) {
                            additionalViewHostViewContainer2.releaseView();
                        }
                        desktopModeWindowDecoration$$ExternalSyntheticLambda6.invoke();
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
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(maximizeMenuView.rootView, (Property<ViewGroup, Float>) View.SCALE_Y, 1.0f, 0.8f);
                objectAnimatorOfFloat.setDuration(200L);
                Interpolator interpolator = Interpolators.FAST_OUT_LINEAR_IN;
                objectAnimatorOfFloat.setInterpolator(interpolator);
                Unit unit = Unit.INSTANCE;
                final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.8f);
                valueAnimatorOfFloat.setDuration(200L);
                valueAnimatorOfFloat.setInterpolator(interpolator);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateCloseMenu$2$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float fFloatValue = ((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue();
                        int iMeasureHeight = maximizeMenuView.menuPadding - ((int) ((1 - fFloatValue) * r0.measureHeight()));
                        MaximizeMenu.MaximizeMenuView maximizeMenuView2 = maximizeMenuView;
                        View view = maximizeMenuView2.container;
                        int i2 = maximizeMenuView2.menuPadding;
                        view.setPadding(i2, iMeasureHeight, i2, i2);
                    }
                });
                final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.0f, 1.25f);
                valueAnimatorOfFloat2.setDuration(200L);
                valueAnimatorOfFloat2.setInterpolator(interpolator);
                valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateCloseMenu$3$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float fFloatValue = ((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue();
                        maximizeMenuView.sizeToggleButton.setScaleY(fFloatValue);
                        maximizeMenuView.immersiveToggleButton.setScaleY(fFloatValue);
                        maximizeMenuView.snapButtonsLayout.setScaleY(fFloatValue);
                        maximizeMenuView.sizeToggleButtonText.setScaleY(fFloatValue);
                        maximizeMenuView.immersiveToggleButtonText.setScaleY(fFloatValue);
                        maximizeMenuView.snapWindowText.setScaleY(fFloatValue);
                    }
                });
                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(maximizeMenuView.rootView, (Property<ViewGroup, Float>) View.TRANSLATION_Y, 0.0f, maximizeMenuView.measureHeight() * (-0.19999999f));
                objectAnimatorOfFloat2.setDuration(200L);
                objectAnimatorOfFloat2.setInterpolator(interpolator);
                ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(maximizeMenuView.rootView.getBackground(), "alpha", 255, 0);
                objectAnimatorOfInt.setStartDelay(33L);
                objectAnimatorOfInt.setDuration(50L);
                final ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(1.0f, 0.0f);
                valueAnimatorOfFloat3.setDuration(50L);
                valueAnimatorOfFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateCloseMenu$6$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float fFloatValue = ((Float) valueAnimatorOfFloat3.getAnimatedValue()).floatValue();
                        maximizeMenuView.sizeToggleButton.setAlpha(fFloatValue);
                        maximizeMenuView.immersiveToggleButton.setAlpha(fFloatValue);
                        maximizeMenuView.snapButtonsLayout.setAlpha(fFloatValue);
                        maximizeMenuView.sizeToggleButtonText.setAlpha(fFloatValue);
                        maximizeMenuView.immersiveToggleButtonText.setAlpha(fFloatValue);
                        maximizeMenuView.snapWindowText.setAlpha(fFloatValue);
                    }
                });
                ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(maximizeMenuView.rootView, (Property<ViewGroup, Float>) View.TRANSLATION_Z, 1.0f, 0.0f);
                objectAnimatorOfFloat3.setDuration(50L);
                animatorSet2.playTogether(objectAnimatorOfFloat, valueAnimatorOfFloat, valueAnimatorOfFloat2, objectAnimatorOfFloat2, objectAnimatorOfInt, valueAnimatorOfFloat3, objectAnimatorOfFloat3);
                AnimatorSet animatorSet3 = maximizeMenuView.menuAnimatorSet;
                if (animatorSet3 != null) {
                    animatorSet3.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateCloseMenu$$inlined$addListener$default$1
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            maximizeMenuView.sizeToggleButton.setLayerType(1, null);
                            maximizeMenuView.sizeToggleButtonText.setLayerType(1, null);
                            maximizeMenuView.immersiveToggleButton.setLayerType(1, null);
                            maximizeMenuView.immersiveToggleButtonText.setLayerType(1, null);
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
        MaximizeMenu.MaximizeMenuView.ImmersiveConfig visible;
        Rect rect;
        int i = 6;
        int i2 = 1;
        MaximizeMenuFactory maximizeMenuFactory = this.mMaximizeMenuFactory;
        SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
        RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer = this.mRootTaskDisplayAreaOrganizer;
        DisplayController displayController = this.mDisplayController;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        Context context = this.mContext;
        Function2 function2 = new Function2() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int i3;
                DesktopModeWindowDecoration desktopModeWindowDecoration = this.f$0;
                desktopModeWindowDecoration.getClass();
                int iIntValue = ((Integer) obj).intValue();
                int iIntValue2 = ((Integer) obj2).intValue();
                Point point = new Point();
                DisplayLayout displayLayout = desktopModeWindowDecoration.mDisplayController.getDisplayLayout(desktopModeWindowDecoration.mTaskInfo.displayId);
                if (displayLayout == null) {
                    return point;
                }
                int i4 = displayLayout.mWidth;
                int i5 = displayLayout.mHeight;
                int windowingMode = desktopModeWindowDecoration.mTaskInfo.getWindowingMode();
                Resources resources = desktopModeWindowDecoration.mContext.getResources();
                boolean z = CoreRune.MW_CAPTION;
                int iLoadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(resources, z ? DesktopModeWindowDecoration.getMultitaskingCaptionHeightIdStatic(desktopModeWindowDecoration.mCaptionType, desktopModeWindowDecoration.mTaskInfo, desktopModeWindowDecoration.mIsDesktopModeSupportedOnDisplay) : windowingMode == 1 ? 17106380 : SystemBarUtils.getDesktopViewAppHeaderHeightId());
                ImageButton imageButton = z ? (ImageButton) ((WindowDecorLinearLayout) desktopModeWindowDecoration.mResult.mRootView).findViewById(R.id.toggle_freeform_window) : (ImageButton) ((WindowDecorLinearLayout) desktopModeWindowDecoration.mResult.mRootView).findViewById(R.id.maximize_window);
                int[] iArr = new int[2];
                imageButton.getLocationInWindow(iArr);
                int width = (desktopModeWindowDecoration.mPositionInParent.x + iArr[0]) - ((iIntValue - imageButton.getWidth()) / 2);
                int i6 = desktopModeWindowDecoration.mPositionInParent.y + iLoadDimensionPixelSize;
                int i7 = width + iIntValue;
                int i8 = i6 + iIntValue2;
                int i9 = width >= 0 ? i7 > i4 ? i4 - iIntValue : width : 0;
                if (i8 > i5) {
                    i6 = i5 - iIntValue2;
                }
                Rect bounds = desktopModeWindowDecoration.mTaskInfo.getConfiguration().windowConfiguration.getBounds();
                if (CoreRune.MW_CAPTION_DESKTOP && i7 > (i3 = bounds.right)) {
                    i9 = (i3 - bounds.left) - iIntValue;
                }
                return new Point(i9, i6);
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
        DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 = this.mOnMaximizeOrRestoreClickListener;
        DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 = this.mOnImmersiveOrRestoreClickListener;
        DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda82 = this.mOnLeftSnapClickListener;
        DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda83 = this.mOnRightSnapClickListener;
        DesktopModeWindowDecoration$$ExternalSyntheticLambda12 desktopModeWindowDecoration$$ExternalSyntheticLambda12 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda12(this, i2);
        DesktopModeWindowDecoration$$ExternalSyntheticLambda6 desktopModeWindowDecoration$$ExternalSyntheticLambda6 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda6(this, i);
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
            visible = new MaximizeMenu.MaximizeMenuView.ImmersiveConfig.Visible(z ? MaximizeMenu.MaximizeMenuView.ImmersiveToggleDirection.EXIT : MaximizeMenu.MaximizeMenuView.ImmersiveToggleDirection.ENTER);
        } else {
            visible = MaximizeMenu.MaximizeMenuView.ImmersiveConfig.Hidden.INSTANCE;
        }
        MaximizeMenu.MaximizeMenuView maximizeMenuView = new MaximizeMenu.MaximizeMenuView(context3, maximizeMenu.desktopModeUiEventLogger, sizeToggleDirection, visible, z3, maximizeMenu.menuPadding);
        ActivityManager.RunningTaskInfo runningTaskInfo2 = maximizeMenu.taskInfo;
        maximizeMenuView.taskInfo = runningTaskInfo2;
        ColorScheme colorScheme = maximizeMenuView.decorThemeUtil.getColorScheme(runningTaskInfo2);
        int iM469toArgb8_81llA = ColorKt.m469toArgb8_81llA(colorScheme.surfaceContainerLow);
        int iM469toArgb8_81llA2 = ColorKt.m469toArgb8_81llA(colorScheme.onSurface);
        int i4 = MaximizeMenu.MaximizeMenuView.WhenMappings.$EnumSwitchMapping$2[maximizeMenuView.sizeToggleDirection.ordinal()];
        if (i4 == 1) {
            rect = maximizeMenuView.maximizeFillPaddingRect;
        } else {
            if (i4 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            rect = maximizeMenuView.maximizeRestoreFillPaddingRect;
        }
        MaximizeMenu.MaximizeMenuView.MenuStyle.MaximizeOption maximizeOption = new MaximizeMenu.MaximizeMenuView.MenuStyle.MaximizeOption(maximizeMenuView.createMaximizeOrImmersiveDrawable(iM469toArgb8_81llA, colorScheme, rect));
        MaximizeMenu.MaximizeMenuView.MenuStyle.ImmersiveOption immersiveOption = new MaximizeMenu.MaximizeMenuView.MenuStyle.ImmersiveOption(maximizeMenuView.createMaximizeOrImmersiveDrawable(iM469toArgb8_81llA, colorScheme, maximizeMenuView.immersiveFillPaddingRect));
        long j = colorScheme.outlineVariant;
        int iM469toArgb8_81llA3 = ColorKt.m469toArgb8_81llA(j);
        long j2 = colorScheme.primary;
        maximizeMenuView.style = new MaximizeMenu.MaximizeMenuView.MenuStyle(iM469toArgb8_81llA, iM469toArgb8_81llA2, maximizeOption, immersiveOption, new MaximizeMenu.MaximizeMenuView.MenuStyle.SnapOptions(iM469toArgb8_81llA3, ThemeUtilsKt.withAlpha(ColorKt.m469toArgb8_81llA(j2), 102), ColorKt.m469toArgb8_81llA(j2), ThemeUtilsKt.withAlpha(ColorKt.m469toArgb8_81llA(j), 153), ColorKt.m469toArgb8_81llA(j2), iM469toArgb8_81llA, ThemeUtilsKt.withAlpha(ColorKt.m469toArgb8_81llA(j2), 31)));
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
        maximizeMenuView.onMaximizeClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8;
        maximizeMenuView.onImmersiveOrRestoreClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9;
        maximizeMenuView.onLeftSnapClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda82;
        maximizeMenuView.onRightSnapClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda83;
        maximizeMenuView.onMenuHoverListener = desktopModeWindowDecoration$$ExternalSyntheticLambda12;
        maximizeMenuView.onOutsideTouchListener = desktopModeWindowDecoration$$ExternalSyntheticLambda6;
        maximizeMenuView.rootView.measure(0, 0);
        int measuredWidth = maximizeMenuView.rootView.getMeasuredWidth();
        int iMeasureHeight = maximizeMenuView.measureHeight();
        maximizeMenu.menuPosition = (Point) maximizeMenu.positionSupplier.invoke(Integer.valueOf(measuredWidth), Integer.valueOf(iMeasureHeight));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(measuredWidth, iMeasureHeight, 2, 262152, -2);
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
                    final MaximizeMenu.MaximizeMenuView maximizeMenuView3 = maximizeMenuView2;
                    if (maximizeMenuView3.immersiveToggleButton.getVisibility() == 0) {
                        maximizeMenuView3.immersiveToggleButton.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$requestAccessibilityFocus$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                maximizeMenuView3.immersiveToggleButton.sendAccessibilityEvent(8);
                            }
                        });
                    } else {
                        maximizeMenuView3.sizeToggleButton.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$requestAccessibilityFocus$2
                            @Override // java.lang.Runnable
                            public final void run() {
                                maximizeMenuView3.sizeToggleButton.sendAccessibilityEvent(8);
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
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(maximizeMenuView2.rootView, (Property<ViewGroup, Float>) View.SCALE_Y, 0.8f, 1.0f);
            objectAnimatorOfFloat.setDuration(300L);
            Interpolator interpolator = Interpolators.EMPHASIZED_DECELERATE;
            objectAnimatorOfFloat.setInterpolator(interpolator);
            Unit unit = Unit.INSTANCE;
            final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.8f, 1.0f);
            valueAnimatorOfFloat.setDuration(300L);
            valueAnimatorOfFloat.setInterpolator(interpolator);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateOpenMenu$2$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float fFloatValue = ((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue();
                    int iMeasureHeight2 = maximizeMenuView2.menuPadding - ((int) ((1 - fFloatValue) * r0.measureHeight()));
                    MaximizeMenu.MaximizeMenuView maximizeMenuView3 = maximizeMenuView2;
                    View view = maximizeMenuView3.container;
                    int i5 = maximizeMenuView3.menuPadding;
                    view.setPadding(i5, iMeasureHeight2, i5, i5);
                }
            });
            final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.25f, 1.0f);
            valueAnimatorOfFloat2.setDuration(300L);
            valueAnimatorOfFloat2.setInterpolator(interpolator);
            valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateOpenMenu$3$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float fFloatValue = ((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue();
                    maximizeMenuView2.sizeToggleButton.setScaleY(fFloatValue);
                    maximizeMenuView2.immersiveToggleButton.setScaleY(fFloatValue);
                    maximizeMenuView2.snapButtonsLayout.setScaleY(fFloatValue);
                    maximizeMenuView2.sizeToggleButtonText.setScaleY(fFloatValue);
                    maximizeMenuView2.immersiveToggleButtonText.setScaleY(fFloatValue);
                    maximizeMenuView2.snapWindowText.setScaleY(fFloatValue);
                }
            });
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(maximizeMenuView2.rootView, (Property<ViewGroup, Float>) View.TRANSLATION_Y, maximizeMenuView2.measureHeight() * (-0.19999999f), 0.0f);
            objectAnimatorOfFloat2.setDuration(300L);
            objectAnimatorOfFloat2.setInterpolator(interpolator);
            ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(maximizeMenuView2.rootView.getBackground(), "alpha", 255);
            objectAnimatorOfInt.setDuration(50L);
            final ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat3.setDuration(50L);
            valueAnimatorOfFloat3.setStartDelay(33L);
            valueAnimatorOfFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateOpenMenu$6$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float fFloatValue = ((Float) valueAnimatorOfFloat3.getAnimatedValue()).floatValue();
                    maximizeMenuView2.sizeToggleButton.setAlpha(fFloatValue);
                    maximizeMenuView2.immersiveToggleButton.setAlpha(fFloatValue);
                    maximizeMenuView2.snapButtonsLayout.setAlpha(fFloatValue);
                    maximizeMenuView2.sizeToggleButtonText.setAlpha(fFloatValue);
                    maximizeMenuView2.immersiveToggleButtonText.setAlpha(fFloatValue);
                    maximizeMenuView2.snapWindowText.setAlpha(fFloatValue);
                }
            });
            ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(maximizeMenuView2.rootView, (Property<ViewGroup, Float>) View.TRANSLATION_Z, 1.0f);
            objectAnimatorOfFloat3.setDuration(50L);
            objectAnimatorOfFloat3.setStartDelay(33L);
            animatorSet.playTogether(objectAnimatorOfFloat, valueAnimatorOfFloat, valueAnimatorOfFloat2, objectAnimatorOfFloat2, objectAnimatorOfInt, valueAnimatorOfFloat3, objectAnimatorOfFloat3);
            AnimatorSet animatorSet2 = maximizeMenuView2.menuAnimatorSet;
            if (animatorSet2 != null) {
                animatorSet2.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu$MaximizeMenuView$animateOpenMenu$$inlined$addListener$default$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        maximizeMenuView2.sizeToggleButton.setLayerType(1, null);
                        maximizeMenuView2.sizeToggleButtonText.setLayerType(1, null);
                        maximizeMenuView2.immersiveToggleButton.setLayerType(1, null);
                        maximizeMenuView2.immersiveToggleButtonText.setLayerType(1, null);
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
        MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle = asMultiTaskingAppHandle(this.mWindowDecorViewHolder);
        if (multiTaskingHandleViewHolderAsMultiTaskingAppHandle != null) {
            return multiTaskingHandleViewHolderAsMultiTaskingAppHandle.captionView;
        }
        return null;
    }

    public final int getOutlineCaptionHeight() {
        if (this.mInDesktopWindowing || TaskInfoKt.isTransparentCaptionBarAppearance(this.mTaskInfo)) {
            return 0;
        }
        return this.mResult.mCaptionHeight;
    }

    public final Rect getTaskBounds() {
        return new Rect(this.mTaskInfo.configuration.windowConfiguration.getBounds());
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
        DesktopModeWindowDecoration$$ExternalSyntheticLambda2 desktopModeWindowDecoration$$ExternalSyntheticLambda2 = this.mLoadAppInfoRunnable;
        if (desktopModeWindowDecoration$$ExternalSyntheticLambda2 != null) {
            ((HandlerExecutor) this.mBgExecutor).removeCallbacks(desktopModeWindowDecoration$$ExternalSyntheticLambda2);
        }
        DesktopModeWindowDecoration$$ExternalSyntheticLambda19 desktopModeWindowDecoration$$ExternalSyntheticLambda19 = this.mSetAppInfoRunnable;
        if (desktopModeWindowDecoration$$ExternalSyntheticLambda19 != null) {
            ((HandlerExecutor) this.mMainExecutor).removeCallbacks(desktopModeWindowDecoration$$ExternalSyntheticLambda19);
        }
        DesktopModeWindowDecoration$$ExternalSyntheticLambda2 desktopModeWindowDecoration$$ExternalSyntheticLambda22 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda2(this, biConsumer, 2);
        this.mLoadAppInfoRunnable = desktopModeWindowDecoration$$ExternalSyntheticLambda22;
        this.mBgExecutor.execute(desktopModeWindowDecoration$$ExternalSyntheticLambda22);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0475  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0232  */
    /* JADX WARN: Type inference failed for: r28v5, types: [com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalSystemViewContainer] */
    /* JADX WARN: Type inference failed for: r3v60 */
    /* JADX WARN: Type inference failed for: r3v61 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onAssistContentReceived(AssistContent assistContent) {
        Uri sessionTransferUri;
        Intent intent;
        boolean z;
        Intent intent2;
        boolean z2;
        Intent intent3;
        boolean z3;
        boolean z4;
        HandleMenuFactory handleMenuFactory;
        MainCoroutineDispatcher mainCoroutineDispatcher;
        CoroutineScope coroutineScope;
        Intent intentAddFlags;
        int i;
        DrawableInsets drawableInsets;
        DrawableInsets drawableInsets2;
        float f;
        SurfaceControl.Transaction transaction;
        SurfaceSyncGroup surfaceSyncGroup;
        AdditionalViewHostViewContainer additionalSystemViewContainer;
        boolean z5;
        DesktopModeWindowDecoration desktopModeWindowDecoration;
        Pair pair;
        Pair pair2;
        Pair[] pairArr;
        float f2;
        int i2;
        int i3;
        PopupButtonPolicy fullScreenPopupButtonPolicy;
        int i4;
        PopupButtonPolicy freeformScrollPopupButtonPolicy;
        AdditionalViewContainer additionalSystemViewContainer2;
        SurfaceSyncGroup surfaceSyncGroup2;
        boolean z6;
        HandleMenu handleMenu;
        DesktopRepository.Desk desk;
        Integer num;
        if (CoreRune.MW_CAPTION_POPUP && this.mWindowDecorViewHolder == null) {
            Log.e("DesktopModeWindowDecoration", "onAssistContentReceived: failed, " + this + ", callers=" + Debug.getCallers(7));
            return;
        }
        if (assistContent == null) {
            sessionTransferUri = null;
        } else {
            Intent intent4 = AppToWebUtils.GenericBrowserIntent;
            sessionTransferUri = assistContent.getSessionTransferUri();
            if (sessionTransferUri == null) {
                sessionTransferUri = assistContent.getWebUri();
            }
        }
        this.mWebUri = sessionTransferUri;
        ComponentName componentName = this.mTaskInfo.baseActivity;
        if (componentName != null) {
            String str = (String) ((LinkedHashMap) this.mGenericLinksParser.genericLinksMap).get(componentName.getPackageName());
            this.mGenericLink = str == null ? null : Uri.parse(str);
        }
        MultiInstanceHelper multiInstanceHelper = this.mMultiInstanceHelper;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        if (multiInstanceHelper.supportsMultiInstanceSplit(runningTaskInfo.userId, runningTaskInfo.baseActivity) && DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_MULTI_INSTANCE_FEATURES.isTrue()) {
            intent = null;
            z = true;
        } else {
            intent = null;
            z = false;
        }
        if (z && this.mMinimumInstancesFound) {
            intent2 = intent;
            z2 = true;
        } else {
            intent2 = intent;
            z2 = false;
        }
        HandleMenu.Companion companion = HandleMenu.Companion;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
        companion.getClass();
        if (runningTaskInfo2.appCompatTaskInfo.eligibleForUserAspectRatioButton() && runningTaskInfo2.getWindowingMode() == 1) {
            intent3 = intent2;
            z3 = true;
        } else {
            intent3 = intent2;
            z3 = false;
        }
        boolean zIsRestartMenuEnabledForDisplayMove = this.mTaskInfo.appCompatTaskInfo.isRestartMenuEnabledForDisplayMove();
        DesktopRepository profile = this.mDesktopUserRepositories.getProfile(this.mTaskInfo.userId);
        ActivityManager.RunningTaskInfo runningTaskInfo3 = this.mTaskInfo;
        int i5 = runningTaskInfo3.displayId;
        int i6 = runningTaskInfo3.taskId;
        DesktopRepository.DesktopData desktopData = profile.desktopData;
        DesktopRepository.Desk activeDesk = desktopData.getActiveDesk(i5);
        boolean z7 = (activeDesk == null || (desk = desktopData.getDesk(activeDesk.deskId)) == null || (num = desk.fullImmersiveTaskId) == null || num.intValue() != i6) ? false : true;
        ComponentName componentName2 = this.mTaskInfo.baseActivity;
        if (componentName2 != null) {
            Context context = this.mContext;
            String packageName = componentName2.getPackageName();
            int userId = this.mUserContext.getUserId();
            Intent intent5 = AppToWebUtils.GenericBrowserIntent;
            intent5.setPackage(packageName);
            for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivitiesAsUser(intent5, 131072, userId)) {
                if (resolveInfo.activityInfo != null && resolveInfo.handleAllWebDataURI) {
                    z4 = true;
                    break;
                }
            }
            z4 = false;
        } else {
            z4 = false;
        }
        boolean z8 = CoreRune.MW_CAPTION_POPUP;
        if (z8 && (handleMenu = this.mHandleMenu) != null) {
            handleMenu.closeMenuPopupImmediately();
        }
        HandleMenuFactory handleMenuFactory2 = this.mHandleMenuFactory;
        MainCoroutineDispatcher mainCoroutineDispatcher2 = this.mMainDispatcher;
        CoroutineScope coroutineScope2 = this.mBgScope;
        WindowManagerWrapper windowManagerWrapper = this.mWindowManagerWrapper;
        WindowDecorTaskResourceLoader windowDecorTaskResourceLoader = this.mTaskResourceLoader;
        int i7 = this.mRelayoutParams.mLayoutResId;
        SplitScreenController splitScreenController = this.mSplitScreenController;
        boolean zCanEnterDesktopModeOrShowAppHandle = this.mDesktopState.canEnterDesktopModeOrShowAppHandle();
        Intent intent6 = intent3;
        boolean z9 = this.mIsDesktopModeSupportedOnDisplay;
        if (z4) {
            Uri uri = this.mWebUri;
            if (uri == null) {
                handleMenuFactory = handleMenuFactory2;
                mainCoroutineDispatcher = mainCoroutineDispatcher2;
                coroutineScope = coroutineScope2;
            } else {
                PackageManager packageManager = this.mContext.getPackageManager();
                handleMenuFactory = handleMenuFactory2;
                int userId2 = this.mUserContext.getUserId();
                Intent intent7 = AppToWebUtils.GenericBrowserIntent;
                mainCoroutineDispatcher = mainCoroutineDispatcher2;
                coroutineScope = coroutineScope2;
                intentAddFlags = new Intent("android.intent.action.VIEW", uri).addFlags(268435456);
                ResolveInfo resolveInfoResolveActivityAsUser = packageManager.resolveActivityAsUser(intentAddFlags, 0, userId2);
                if (resolveInfoResolveActivityAsUser != null && resolveInfoResolveActivityAsUser.activityInfo != null && !resolveInfoResolveActivityAsUser.handleAllWebDataURI) {
                    intentAddFlags.setComponent(resolveInfoResolveActivityAsUser.getComponentInfo().getComponentName());
                }
            }
            intentAddFlags = intent6;
        } else {
            handleMenuFactory = handleMenuFactory2;
            mainCoroutineDispatcher = mainCoroutineDispatcher2;
            coroutineScope = coroutineScope2;
            Uri uri2 = this.mWebUri;
            if (uri2 == null) {
                CapturedLink capturedLink = this.mCapturedLink;
                uri2 = (capturedLink == null || capturedLink.mUsed) ? this.mGenericLink : capturedLink.mUri;
            }
            if (uri2 != null) {
                PackageManager packageManager2 = this.mContext.getPackageManager();
                int userId3 = this.mUserContext.getUserId();
                Intent intent8 = AppToWebUtils.GenericBrowserIntent;
                Intent intentAddFlags2 = Intent.makeMainSelectorActivity("android.intent.action.MAIN", "android.intent.category.APP_BROWSER").setData(uri2).addFlags(268435456);
                ResolveInfo resolveInfoResolveActivityAsUser2 = packageManager2.resolveActivityAsUser(intentAddFlags2, 0, userId3);
                if (resolveInfoResolveActivityAsUser2 == null) {
                    intentAddFlags = intent6;
                } else {
                    intentAddFlags2.setComponent(resolveInfoResolveActivityAsUser2.getComponentInfo().getComponentName());
                    intentAddFlags = intentAddFlags2;
                }
                Intent intent9 = intentAddFlags;
                DesktopModeUiEventLogger desktopModeUiEventLogger = this.mDesktopModeUiEventLogger;
                WindowDecoration.RelayoutResult relayoutResult = this.mResult;
                int i8 = relayoutResult.mCaptionWidth;
                int i9 = relayoutResult.mCaptionHeight;
                int i10 = relayoutResult.mCaptionX;
                int i11 = relayoutResult.mCaptionY + relayoutResult.mCaptionTopPadding;
                DisplayController displayController = this.mDisplayController;
                boolean z10 = this.mIsDesktopModeSupportedOnDisplay;
                boolean z11 = this.mShouldShowRestartNotification;
                ((DefaultHandleMenuFactory) handleMenuFactory).getClass();
                this.mHandleMenu = new HandleMenu(mainCoroutineDispatcher, coroutineScope, this, windowManagerWrapper, windowDecorTaskResourceLoader, i7, splitScreenController, zCanEnterDesktopModeOrShowAppHandle, z, z2, z3, z9, zIsRestartMenuEnabledForDisplayMove, z4, intent9, desktopModeUiEventLogger, i8, i9, i10, i11, displayController, z10, z11);
                this.mWindowDecorViewHolder.onHandleMenuOpened();
                if (z8) {
                    HandleMenu handleMenu2 = this.mHandleMenu;
                    DesktopModeWindowDecoration$$ExternalSyntheticLambda6 desktopModeWindowDecoration$$ExternalSyntheticLambda6 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda6(this, 2);
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 = this.mOnToFullscreenClickListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda92 = this.mOnToSplitscreenClickListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda93 = this.mOnToFloatClickListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda94 = this.mOnNewWindowClickListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda95 = this.mOnManageWindowsClickListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda96 = this.mOnChangeAspectRatioClickListener;
                    DesktopModeWindowDecoration$$ExternalSyntheticLambda12 desktopModeWindowDecoration$$ExternalSyntheticLambda12 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda12(this, 0);
                    DesktopModeWindowDecoration$$ExternalSyntheticLambda6 desktopModeWindowDecoration$$ExternalSyntheticLambda62 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda6(this, 3);
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda97 = this.mOnRestartClickListener;
                    int i12 = 3;
                    DesktopModeWindowDecoration$$ExternalSyntheticLambda6 desktopModeWindowDecoration$$ExternalSyntheticLambda63 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda6(this, 4);
                    DesktopModeWindowDecoration$$ExternalSyntheticLambda6 desktopModeWindowDecoration$$ExternalSyntheticLambda64 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda6(this, 5);
                    handleMenu2.getClass();
                    SurfaceSyncGroup surfaceSyncGroup3 = new SurfaceSyncGroup("HandleMenu");
                    SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                    HandleMenu.HandleMenuView handleMenuView = new HandleMenu.HandleMenuView(handleMenu2.context, handleMenu2.desktopModeUiEventLogger, handleMenu2.menuWidth, handleMenu2.captionHeight, handleMenu2.shouldShowWindowingPill, handleMenu2.openInAppOrBrowserIntent != null, handleMenu2.shouldShowNewWindowButton, handleMenu2.shouldShowManageWindowsButton, handleMenu2.shouldShowChangeAspectRatioButton, handleMenu2.shouldShowDesktopModeButton, handleMenu2.shouldShowRestartButton, handleMenu2.isBrowserApp);
                    ActivityManager.RunningTaskInfo runningTaskInfo4 = handleMenu2.taskInfo;
                    boolean z12 = handleMenu2.shouldShowNewWindowButton || handleMenu2.shouldShowManageWindowsButton || handleMenu2.shouldShowChangeAspectRatioButton || handleMenu2.shouldShowRestartButton;
                    handleMenuView.taskInfo = runningTaskInfo4;
                    boolean z13 = z12;
                    ColorScheme colorScheme = handleMenuView.decorThemeUtil.getColorScheme(runningTaskInfo4);
                    int iM469toArgb8_81llA = ColorKt.m469toArgb8_81llA(colorScheme.surfaceBright);
                    long j = colorScheme.onSurface;
                    HandleMenu.HandleMenuView.MenuStyle menuStyle = new HandleMenu.HandleMenuView.MenuStyle(iM469toArgb8_81llA, ColorKt.m469toArgb8_81llA(j), new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}, new int[]{android.R.attr.state_focused}, new int[]{android.R.attr.state_selected}, new int[0]}, new int[]{ColorKt.m469toArgb8_81llA(j), ColorKt.m469toArgb8_81llA(j), ColorKt.m469toArgb8_81llA(colorScheme.primary), ColorKt.m469toArgb8_81llA(j)}));
                    handleMenuView.style = menuStyle;
                    handleMenuView.appInfoPill.getBackground().setTint(menuStyle.backgroundColor);
                    int i13 = menuStyle.textColor;
                    ColorStateList colorStateListValueOf = ColorStateList.valueOf(i13);
                    HandleMenuImageButton handleMenuImageButton = handleMenuView.collapseMenuButton;
                    handleMenuImageButton.setImageTintList(colorStateListValueOf);
                    ActivityManager.RunningTaskInfo runningTaskInfo5 = handleMenuView.taskInfo;
                    if (runningTaskInfo5 == null) {
                        runningTaskInfo5 = null;
                    }
                    handleMenuImageButton.taskInfo = runningTaskInfo5;
                    int i14 = handleMenuView.iconButtonRippleRadius;
                    DrawableInsets drawableInsets3 = handleMenuView.iconButtonDrawableInsetsBase;
                    handleMenuImageButton.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i13, i14, drawableInsets3));
                    MarqueedTextView marqueedTextView = handleMenuView.appNameView;
                    marqueedTextView.setTextColor(i13);
                    marqueedTextView.startMarquee();
                    DrawableInsets drawableInsets4 = handleMenuView.iconButtonDrawableInsetsLeft;
                    DrawableInsets drawableInsets5 = handleMenuView.iconButtonDrawableInsetsRight;
                    if (handleMenuView.shouldShowWindowingPill) {
                        HandleMenu.HandleMenuView.MenuStyle menuStyle2 = handleMenuView.style;
                        if (menuStyle2 == null) {
                            menuStyle2 = null;
                        }
                        handleMenuView.windowingPill.getBackground().setTint(menuStyle2.backgroundColor);
                        i = 8;
                        handleMenuView.floatingBtn.setVisibility(8);
                        handleMenuView.floatingBtnSpace.setVisibility(8);
                        ImageButton imageButton = handleMenuView.fullscreenBtn;
                        ActivityManager.RunningTaskInfo runningTaskInfo6 = handleMenuView.taskInfo;
                        if (runningTaskInfo6 == null) {
                            runningTaskInfo6 = null;
                        }
                        imageButton.setSelected(TaskInfoKt.isFullscreen(runningTaskInfo6));
                        ImageButton imageButton2 = handleMenuView.fullscreenBtn;
                        ActivityManager.RunningTaskInfo runningTaskInfo7 = handleMenuView.taskInfo;
                        if (runningTaskInfo7 == null) {
                            runningTaskInfo7 = null;
                        }
                        imageButton2.setEnabled(!TaskInfoKt.isFullscreen(runningTaskInfo7));
                        handleMenuView.fullscreenBtn.setImageTintList(menuStyle2.windowingButtonColor);
                        ImageButton imageButton3 = handleMenuView.splitscreenBtn;
                        ActivityManager.RunningTaskInfo runningTaskInfo8 = handleMenuView.taskInfo;
                        if (runningTaskInfo8 == null) {
                            runningTaskInfo8 = null;
                        }
                        imageButton3.setSelected(TaskInfoKt.isMultiWindow(runningTaskInfo8));
                        ImageButton imageButton4 = handleMenuView.splitscreenBtn;
                        ActivityManager.RunningTaskInfo runningTaskInfo9 = handleMenuView.taskInfo;
                        if (runningTaskInfo9 == null) {
                            runningTaskInfo9 = null;
                        }
                        imageButton4.setEnabled(!TaskInfoKt.isMultiWindow(runningTaskInfo9));
                        handleMenuView.splitscreenBtn.setImageTintList(menuStyle2.windowingButtonColor);
                        ImageButton imageButton5 = handleMenuView.floatingBtn;
                        ActivityManager.RunningTaskInfo runningTaskInfo10 = handleMenuView.taskInfo;
                        if (runningTaskInfo10 == null) {
                            runningTaskInfo10 = null;
                        }
                        imageButton5.setSelected(runningTaskInfo10.getWindowingMode() == 2);
                        ImageButton imageButton6 = handleMenuView.floatingBtn;
                        ActivityManager.RunningTaskInfo runningTaskInfo11 = handleMenuView.taskInfo;
                        if (runningTaskInfo11 == null) {
                            runningTaskInfo11 = null;
                        }
                        imageButton6.setEnabled(!(runningTaskInfo11.getWindowingMode() == 2));
                        handleMenuView.floatingBtn.setImageTintList(menuStyle2.windowingButtonColor);
                        ImageButton imageButton7 = handleMenuView.desktopBtn;
                        boolean z14 = handleMenuView.shouldShowDesktopModeButton;
                        imageButton7.setVisibility(!z14 ? 8 : 0);
                        handleMenuView.desktopBtnSpace.setVisibility(!z14 ? 8 : 0);
                        ImageButton imageButton8 = handleMenuView.desktopBtn;
                        ActivityManager.RunningTaskInfo runningTaskInfo12 = handleMenuView.taskInfo;
                        if (runningTaskInfo12 == null) {
                            runningTaskInfo12 = null;
                        }
                        imageButton8.setSelected(runningTaskInfo12.isFreeform());
                        ImageButton imageButton9 = handleMenuView.desktopBtn;
                        ActivityManager.RunningTaskInfo runningTaskInfo13 = handleMenuView.taskInfo;
                        if (runningTaskInfo13 == null) {
                            runningTaskInfo13 = null;
                        }
                        imageButton9.setEnabled(!runningTaskInfo13.isFreeform());
                        handleMenuView.desktopBtn.setImageTintList(menuStyle2.windowingButtonColor);
                        ImageButton imageButton10 = handleMenuView.fullscreenBtn;
                        Context context2 = handleMenuView.context;
                        int i15 = ContextUtils.$r8$clinit;
                        DrawableInsets drawableInsets6 = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context2) == 1 ? drawableInsets5 : drawableInsets4;
                        int i16 = menuStyle2.textColor;
                        imageButton10.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i16, i14, drawableInsets6));
                        handleMenuView.splitscreenBtn.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i16, i14, drawableInsets3));
                        handleMenuView.floatingBtn.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i16, i14, drawableInsets3));
                        handleMenuView.desktopBtn.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i16, i14, MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(handleMenuView.context) == 1 ? drawableInsets4 : drawableInsets5));
                    } else {
                        i = 8;
                    }
                    handleMenuView.moreActionsPill.setVisibility(!z13 ? i : 0);
                    int i17 = handleMenuView.handleMenuCornerRadius;
                    if (z13) {
                        HandleMenu.HandleMenuView.MenuStyle menuStyle3 = handleMenuView.style;
                        if (menuStyle3 == null) {
                            menuStyle3 = null;
                        }
                        handleMenuView.moreActionsPill.getBackground().setTint(menuStyle3.backgroundColor);
                        f = 0.0f;
                        drawableInsets = drawableInsets4;
                        drawableInsets2 = drawableInsets5;
                        Pair[] pairArr2 = {new Pair(handleMenuView.screenshotBtn, Boolean.FALSE), new Pair(handleMenuView.newWindowBtn, Boolean.valueOf(handleMenuView.shouldShowNewWindowButton)), new Pair(handleMenuView.manageWindowBtn, Boolean.valueOf(handleMenuView.shouldShowManageWindowsButton)), new Pair(handleMenuView.changeAspectRatioBtn, Boolean.valueOf(handleMenuView.shouldShowChangeAspectRatioButton)), new Pair(handleMenuView.restartBtn, Boolean.valueOf(handleMenuView.shouldShowRestartButton))};
                        int i18 = 0;
                        while (true) {
                            if (i18 >= 5) {
                                pair = null;
                                break;
                            }
                            pair = pairArr2[i18];
                            if (((Boolean) pair.getSecond()).booleanValue()) {
                                break;
                            } else {
                                i18++;
                            }
                        }
                        HandleMenuActionButton handleMenuActionButton = pair != null ? (HandleMenuActionButton) pair.getFirst() : null;
                        int i19 = 4;
                        while (true) {
                            int i20 = i19 - 1;
                            pair2 = pairArr2[i19];
                            if (((Boolean) pair2.getSecond()).booleanValue()) {
                                break;
                            }
                            if (i20 < 0) {
                                pair2 = null;
                                break;
                            } else {
                                i19 = i20;
                                i12 = 3;
                            }
                        }
                        HandleMenuActionButton handleMenuActionButton2 = pair2 != null ? (HandleMenuActionButton) pair2.getFirst() : null;
                        int i21 = 0;
                        int i22 = 5;
                        while (i21 < i22) {
                            Pair pair3 = pairArr2[i21];
                            HandleMenuActionButton handleMenuActionButton3 = (HandleMenuActionButton) pair3.component1();
                            boolean zBooleanValue = ((Boolean) pair3.component2()).booleanValue();
                            float f3 = Intrinsics.areEqual(handleMenuActionButton3, handleMenuActionButton) ? i17 : 0.0f;
                            if (Intrinsics.areEqual(handleMenuActionButton3, handleMenuActionButton2)) {
                                pairArr = pairArr2;
                                f2 = i17;
                            } else {
                                pairArr = pairArr2;
                                f2 = 0.0f;
                            }
                            handleMenuActionButton3.setVisibility(!zBooleanValue ? 8 : 0);
                            MarqueedTextView marqueedTextView2 = handleMenuActionButton3.textView;
                            float f4 = f2;
                            int i23 = menuStyle3.textColor;
                            marqueedTextView2.setTextColor(i23);
                            marqueedTextView2.startMarquee();
                            handleMenuActionButton3.iconView.setImageTintList(ColorStateList.valueOf(i23));
                            float[] fArr = new float[8];
                            fArr[0] = f3;
                            fArr[1] = f3;
                            fArr[2] = f3;
                            fArr[i12] = f3;
                            fArr[4] = f4;
                            fArr[5] = f4;
                            fArr[6] = f4;
                            fArr[7] = f4;
                            handleMenuActionButton3.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i23, fArr, new DrawableInsets(0, 0, i12, (DefaultConstructorMarker) null)));
                            i21++;
                            i22 = 5;
                            pairArr2 = pairArr;
                            handleMenuActionButton2 = handleMenuActionButton2;
                            handleMenuActionButton = handleMenuActionButton;
                            menuStyle3 = menuStyle3;
                            i12 = 3;
                        }
                    } else {
                        drawableInsets = drawableInsets4;
                        drawableInsets2 = drawableInsets5;
                        f = 0.0f;
                    }
                    HandleMenu.HandleMenuView.MenuStyle menuStyle4 = handleMenuView.style;
                    if (menuStyle4 == null) {
                        menuStyle4 = null;
                    }
                    View view = handleMenuView.openInAppOrBrowserPill;
                    view.setVisibility(!handleMenuView.shouldShowBrowserPill ? 8 : 0);
                    view.getBackground().setTint(menuStyle4.backgroundColor);
                    boolean z15 = handleMenuView.isBrowserApp;
                    String string = z15 ? handleMenuView.context.getResources().getString(R.string.open_in_app_text) : handleMenuView.context.getResources().getString(R.string.open_in_browser_text);
                    HandleMenuActionButton handleMenuActionButton4 = handleMenuView.openInAppOrBrowserBtn;
                    handleMenuActionButton4.setContentDescription(string);
                    DrawableInsets drawableInsets7 = new DrawableInsets(0, 0, 3, (DefaultConstructorMarker) null);
                    int i24 = menuStyle4.textColor;
                    handleMenuActionButton4.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i24, i17, drawableInsets7));
                    MarqueedTextView marqueedTextView3 = handleMenuActionButton4.textView;
                    marqueedTextView3.setText(string);
                    marqueedTextView3.setTextColor(i24);
                    marqueedTextView3.startMarquee();
                    handleMenuActionButton4.iconView.setImageTintList(ColorStateList.valueOf(i24));
                    ImageButton imageButton11 = handleMenuView.openByDefaultBtn;
                    imageButton11.setVisibility(z15 ? 8 : 0);
                    imageButton11.setImageTintList(ColorStateList.valueOf(i24));
                    Context context3 = handleMenuView.context;
                    int i25 = ContextUtils.$r8$clinit;
                    imageButton11.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i24, i14, MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context3) == 1 ? drawableInsets : drawableInsets2));
                    handleMenuView.onToDesktopClickListener = desktopModeWindowDecoration$$ExternalSyntheticLambda6;
                    handleMenuView.onToFullscreenClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9;
                    handleMenuView.onToSplitScreenClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda92;
                    handleMenuView.onToFloatClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda93;
                    handleMenuView.onNewWindowClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda94;
                    handleMenuView.onManageWindowsClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda95;
                    handleMenuView.onChangeAspectRatioClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda96;
                    handleMenuView.onOpenInAppOrBrowserClickListener = new HandleMenu$$ExternalSyntheticLambda2(desktopModeWindowDecoration$$ExternalSyntheticLambda12, handleMenu2);
                    handleMenuView.onRestartClickListener = desktopModeWindowDecorViewModel$$ExternalSyntheticLambda97;
                    handleMenuView.onOpenByDefaultClickListener = desktopModeWindowDecoration$$ExternalSyntheticLambda62;
                    handleMenuView.onCloseMenuClickListener = desktopModeWindowDecoration$$ExternalSyntheticLambda63;
                    handleMenuView.onOutsideTouchListener = desktopModeWindowDecoration$$ExternalSyntheticLambda64;
                    handleMenu2.loadAppInfoJob = BuildersKt.launch$default(handleMenu2.bgScope, null, null, new HandleMenu$createHandleMenu$1(handleMenu2, handleMenuView, null), 3);
                    PointF pointF = handleMenu2.handleMenuPosition;
                    int i26 = (int) pointF.x;
                    int i27 = (int) pointF.y;
                    if ((handleMenu2.taskInfo.isFreeform() || !DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) && !z7) {
                        AdditionalViewHostViewContainer additionalViewHostViewContainerAddWindow = handleMenu2.parentDecor.addWindow(handleMenuView.rootView, transaction2, surfaceSyncGroup3, i26, i27, handleMenu2.menuWidth, handleMenu2.menuHeight, false);
                        transaction = transaction2;
                        surfaceSyncGroup = surfaceSyncGroup3;
                        additionalSystemViewContainer = additionalViewHostViewContainerAddWindow;
                    } else {
                        additionalSystemViewContainer = new AdditionalSystemViewContainer(handleMenu2.windowManagerWrapper, handleMenu2.taskInfo.taskId, i26, i27, handleMenu2.menuWidth, handleMenu2.menuHeight, 262152, z7 ? WindowInsets.Type.systemBars() : 0, false, handleMenuView.rootView);
                        transaction = transaction2;
                        surfaceSyncGroup = surfaceSyncGroup3;
                    }
                    handleMenu2.handleMenuViewContainer = additionalSystemViewContainer;
                    handleMenu2.handleMenuView = handleMenuView;
                    surfaceSyncGroup.addTransaction(transaction);
                    surfaceSyncGroup.markSyncReady();
                    HandleMenu.HandleMenuView handleMenuView2 = handleMenu2.handleMenuView;
                    if (handleMenuView2 == null) {
                        z5 = false;
                        desktopModeWindowDecoration = this;
                    } else {
                        ActivityManager.RunningTaskInfo runningTaskInfo14 = handleMenuView2.taskInfo;
                        if (runningTaskInfo14 == null) {
                            runningTaskInfo14 = null;
                        }
                        boolean zIsFullscreen = TaskInfoKt.isFullscreen(runningTaskInfo14);
                        final HandleMenuAnimator handleMenuAnimator = handleMenuView2.animator;
                        if (!zIsFullscreen) {
                            ActivityManager.RunningTaskInfo runningTaskInfo15 = handleMenuView2.taskInfo;
                            if (runningTaskInfo15 == null) {
                                runningTaskInfo15 = null;
                            }
                            if (!TaskInfoKt.isMultiWindow(runningTaskInfo15)) {
                                handleMenuAnimator.prepareMenuForAnimation();
                                List list = handleMenuAnimator.animators;
                                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.SCALE_X, 0.5f, 1.0f);
                                objectAnimatorOfFloat.setDuration(217L);
                                ((ArrayList) list).add(objectAnimatorOfFloat);
                                List list2 = handleMenuAnimator.animators;
                                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.SCALE_Y, 0.5f, 1.0f);
                                objectAnimatorOfFloat2.setDuration(217L);
                                ((ArrayList) list2).add(objectAnimatorOfFloat2);
                                handleMenuAnimator.animateAppInfoPillOpen();
                                handleMenuAnimator.animateWindowingPillOpen();
                                handleMenuAnimator.animateMoreActionsPillOpen();
                                handleMenuAnimator.animateOpenInAppOrBrowserPill();
                                final int i28 = 1;
                                handleMenuAnimator.runAnimations(new Function0() { // from class: com.android.wm.shell.windowdecor.HandleMenuAnimator$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        switch (i28) {
                                            case 0:
                                                final HandleMenuAnimator handleMenuAnimator2 = handleMenuAnimator;
                                                handleMenuAnimator2.appInfoPill.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.HandleMenuAnimator$animateCaptionHandleExpandToOpen$1$1
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        handleMenuAnimator2.appInfoPill.requireViewById(R.id.collapse_menu_button).sendAccessibilityEvent(8);
                                                    }
                                                });
                                                break;
                                            default:
                                                final HandleMenuAnimator handleMenuAnimator3 = handleMenuAnimator;
                                                handleMenuAnimator3.appInfoPill.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.HandleMenuAnimator$animateOpen$1$1
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        handleMenuAnimator3.appInfoPill.requireViewById(R.id.collapse_menu_button).sendAccessibilityEvent(8);
                                                    }
                                                });
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                });
                                z5 = false;
                                desktopModeWindowDecoration = this;
                            }
                        }
                        handleMenuAnimator.prepareMenuForAnimation();
                        List list3 = handleMenuAnimator.animators;
                        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.SCALE_X, 0.6f, 1.0f);
                        objectAnimatorOfFloat3.setDuration(150L);
                        ((ArrayList) list3).add(objectAnimatorOfFloat3);
                        List list4 = handleMenuAnimator.animators;
                        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(handleMenuAnimator.appInfoPill, (Property<ViewGroup, Float>) View.SCALE_Y, 0.05f, 1.0f);
                        objectAnimatorOfFloat4.setDuration(150L);
                        ((ArrayList) list4).add(objectAnimatorOfFloat4);
                        float f5 = (-handleMenuAnimator.captionHeight) / 2;
                        List list5 = handleMenuAnimator.animators;
                        ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(handleMenuAnimator.handleMenu, (Property<View, Float>) View.TRANSLATION_Y, f5, f);
                        objectAnimatorOfFloat5.setDuration(150L);
                        ((ArrayList) list5).add(objectAnimatorOfFloat5);
                        handleMenuAnimator.animateAppInfoPillOpen();
                        handleMenuAnimator.animateWindowingPillOpen();
                        handleMenuAnimator.animateMoreActionsPillOpen();
                        handleMenuAnimator.animateOpenInAppOrBrowserPill();
                        z5 = false;
                        final ?? r3 = 0 == true ? 1 : 0;
                        handleMenuAnimator.runAnimations(new Function0() { // from class: com.android.wm.shell.windowdecor.HandleMenuAnimator$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                switch (r3) {
                                    case 0:
                                        final HandleMenuAnimator handleMenuAnimator2 = handleMenuAnimator;
                                        handleMenuAnimator2.appInfoPill.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.HandleMenuAnimator$animateCaptionHandleExpandToOpen$1$1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                handleMenuAnimator2.appInfoPill.requireViewById(R.id.collapse_menu_button).sendAccessibilityEvent(8);
                                            }
                                        });
                                        break;
                                    default:
                                        final HandleMenuAnimator handleMenuAnimator3 = handleMenuAnimator;
                                        handleMenuAnimator3.appInfoPill.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.HandleMenuAnimator$animateOpen$1$1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                handleMenuAnimator3.appInfoPill.requireViewById(R.id.collapse_menu_button).sendAccessibilityEvent(8);
                                            }
                                        });
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        });
                        desktopModeWindowDecoration = this;
                    }
                } else {
                    HandleMenu handleMenu3 = this.mHandleMenu;
                    boolean z16 = this.mIsKeyguardShowing;
                    DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener = this.mOnCaptionTouchListener;
                    DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener2 = this.mOnCaptionButtonClickListener;
                    final DesktopModeWindowDecoration$$ExternalSyntheticLambda6 desktopModeWindowDecoration$$ExternalSyntheticLambda65 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda6(this, 1);
                    WindowDecoration.RelayoutResult relayoutResult2 = this.mResult;
                    int i29 = relayoutResult2.mCaptionX;
                    int i30 = relayoutResult2.mCaptionY;
                    handleMenu3.getClass();
                    SurfaceSyncGroup surfaceSyncGroup4 = new SurfaceSyncGroup("HandleMenu");
                    SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
                    int windowingMode = handleMenu3.taskInfo.getWindowingMode();
                    ActivityManager.RunningTaskInfo runningTaskInfo16 = handleMenu3.taskInfo;
                    DesktopModeWindowDecoration desktopModeWindowDecoration2 = handleMenu3.parentDecor;
                    boolean zIsDecorCaptionState$1 = desktopModeWindowDecoration2.isDecorCaptionState$1();
                    boolean z17 = desktopModeWindowDecoration2.mInDesktopWindowing;
                    Context context4 = handleMenu3.context;
                    boolean zIsSplitStashed = handleMenu3.splitScreenController.mSplitState.isSplitStashed();
                    DisplayController displayController2 = handleMenu3.displayController;
                    if (zIsDecorCaptionState$1) {
                        fullScreenPopupButtonPolicy = z17 ? new DexFreeformPopupButtonPolicy(runningTaskInfo16, context4, displayController2, handleMenu3.shouldShowRestartNotification) : new CaptionPopupButtonPolicy(runningTaskInfo16, context4, displayController2, zIsSplitStashed);
                        i2 = 5;
                        i3 = 6;
                    } else {
                        i2 = 5;
                        if (windowingMode == 5) {
                            Resources resources = context4.getResources();
                            i3 = 6;
                            if (runningTaskInfo16.configuration.windowConfiguration.getBounds().width() > (resources.getDimensionPixelSize(R.dimen.mw_caption_button_width) * 6) + (resources.getDimensionPixelSize(R.dimen.mw_caption_button_padding) * 2) + resources.getDimensionPixelSize(R.dimen.mw_caption_divider_size)) {
                                i4 = 5;
                                freeformScrollPopupButtonPolicy = new FreeformPopupButtonPolicy(runningTaskInfo16, context4, displayController2, zIsSplitStashed, z16);
                            } else {
                                i4 = 5;
                                freeformScrollPopupButtonPolicy = new FreeformScrollPopupButtonPolicy(runningTaskInfo16, context4, displayController2, zIsSplitStashed, z16);
                            }
                            fullScreenPopupButtonPolicy = freeformScrollPopupButtonPolicy;
                            desktopModeWindowDecoration2 = desktopModeWindowDecoration2;
                            i2 = i4;
                        } else {
                            i3 = 6;
                            fullScreenPopupButtonPolicy = (CoreRune.MW_CAPTION_FULL_SCREEN && windowingMode == 1) ? new FullScreenPopupButtonPolicy(runningTaskInfo16, context4, displayController2) : windowingMode == 6 ? new SplitPopupButtonPolicy(runningTaskInfo16, context4, displayController2, handleMenu3.isDesktopModeSupportedOnDisplay) : null;
                        }
                    }
                    handleMenu3.buttonPolicy = fullScreenPopupButtonPolicy;
                    LayoutInflater layoutInflaterFrom = LayoutInflater.from(handleMenu3.context);
                    PopupButtonPolicy popupButtonPolicy = handleMenu3.buttonPolicy;
                    if (popupButtonPolicy == null) {
                        popupButtonPolicy = null;
                    }
                    View viewInflate = layoutInflaterFrom.inflate(popupButtonPolicy.getLayoutResId(), (ViewGroup) null);
                    PopupButtonPolicy popupButtonPolicy2 = handleMenu3.buttonPolicy;
                    if (popupButtonPolicy2 == null) {
                        popupButtonPolicy2 = null;
                    }
                    popupButtonPolicy2.setupRootView(handleMenu3.context, viewInflate, desktopModeTouchEventListener, desktopModeTouchEventListener2);
                    PopupButtonPolicy popupButtonPolicy3 = handleMenu3.buttonPolicy;
                    if (popupButtonPolicy3 == null) {
                        popupButtonPolicy3 = null;
                    }
                    ActivityManager.RunningTaskInfo runningTaskInfo17 = handleMenu3.taskInfo;
                    popupButtonPolicy3.mTaskInfo = runningTaskInfo17;
                    popupButtonPolicy3.setupSplitButtonImage(runningTaskInfo17);
                    CaptionButton captionButton = popupButtonPolicy3.mFreeformButton;
                    if (captionButton != null) {
                        boolean z18 = runningTaskInfo17 != null && runningTaskInfo17.supportsMultiWindow && MultiWindowCoreState.MW_ENABLED;
                        if (captionButton.isEnabled() != z18) {
                            popupButtonPolicy3.mFreeformButton.setEnabled(z18);
                        }
                    }
                    handleMenu3.updateHandleMenuPillPositions(i29, i30);
                    viewInflate.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu$createMenuPopup$1
                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view2, MotionEvent motionEvent) {
                            if (motionEvent.getActionMasked() != 4) {
                                return true;
                            }
                            desktopModeWindowDecoration$$ExternalSyntheticLambda65.invoke();
                            return false;
                        }
                    });
                    PopupButtonPolicy popupButtonPolicy4 = handleMenu3.buttonPolicy;
                    int i31 = (popupButtonPolicy4 == null ? null : popupButtonPolicy4).mPopupWidth;
                    if (popupButtonPolicy4 == null) {
                        popupButtonPolicy4 = null;
                    }
                    int i32 = popupButtonPolicy4.mPopupHeight;
                    PointF pointF2 = handleMenu3.handleMenuPosition;
                    int i33 = (int) pointF2.x;
                    int i34 = (int) pointF2.y;
                    if (TaskInfoKt.isFullscreen(handleMenu3.taskInfo) || z7) {
                        additionalSystemViewContainer2 = new AdditionalSystemViewContainer(handleMenu3.windowManagerWrapper, handleMenu3.taskInfo.taskId, i33, i34, i31, i32, 8650760, z7 ? WindowInsets.Type.systemBars() : 0, false, viewInflate, 256, (DefaultConstructorMarker) null);
                        surfaceSyncGroup2 = surfaceSyncGroup4;
                    } else {
                        additionalSystemViewContainer2 = handleMenu3.parentDecor.addWindow(viewInflate, transaction3, surfaceSyncGroup4, i33, i34, i31, i32, true);
                        surfaceSyncGroup2 = surfaceSyncGroup4;
                    }
                    AdditionalViewContainer additionalViewContainer = additionalSystemViewContainer2;
                    handleMenu3.handleMenuViewContainer = additionalViewContainer;
                    if (CoreRune.MW_CAPTION_TOOLTIP && (additionalViewContainer instanceof AdditionalSystemViewContainer)) {
                        AdditionalSystemViewContainer additionalSystemViewContainer3 = (AdditionalSystemViewContainer) additionalViewContainer;
                        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) additionalSystemViewContainer3.view.getLayoutParams();
                        layoutParams.multiWindowFlags = 2;
                        additionalSystemViewContainer3.windowManagerWrapper.windowManager.updateViewLayout(additionalSystemViewContainer3.view, layoutParams);
                    }
                    surfaceSyncGroup2.addTransaction(transaction3);
                    surfaceSyncGroup2.markSyncReady();
                    if (CoreRune.MW_CAPTION_HANDLE) {
                        MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle = asMultiTaskingAppHandle(desktopModeWindowDecoration2.mWindowDecorViewHolder);
                        if (multiTaskingHandleViewHolderAsMultiTaskingAppHandle != null) {
                            z6 = false;
                            multiTaskingHandleViewHolderAsMultiTaskingAppHandle.handleTouchEnabled = false;
                        } else {
                            z6 = false;
                        }
                        InputMethodManager inputMethodManager = (InputMethodManager) desktopModeWindowDecoration2.mContext.getSystemService(InputMethodManager.class);
                        if (inputMethodManager != null && inputMethodManager.isInputMethodShown()) {
                            desktopModeWindowDecoration2.mHandler.post(new DesktopModeWindowDecoration$$ExternalSyntheticLambda0(inputMethodManager, 2));
                        }
                    } else {
                        z6 = false;
                    }
                    PopupButtonPolicy popupButtonPolicy5 = handleMenu3.buttonPolicy;
                    if (popupButtonPolicy5 == null) {
                        popupButtonPolicy5 = null;
                    }
                    popupButtonPolicy5.animateOpenMenu();
                    if (CoreRune.MW_CAPTION_HELP_POPUP && this.mHandleMenuHelpController != null) {
                        int windowingMode2 = this.mTaskInfo.getWindowingMode();
                        if (windowingMode2 == i3 ? HandleMenuHelpController.SPLIT_HANDLER_HELP_POPUP_ENABLED : windowingMode2 == i2 ? HandleMenuHelpController.FREEFORM_HANDLER_HELP_POPUP_ENABLED : z6) {
                            HandleMenuHelpController handleMenuHelpController = this.mHandleMenuHelpController;
                            int windowingMode3 = this.mTaskInfo.getWindowingMode();
                            if (windowingMode3 == i3) {
                                Settings.Global.putInt(handleMenuHelpController.mContext.getContentResolver(), "multi_split_quick_options_help_count", 1);
                                HandleMenuHelpController.SPLIT_HANDLER_HELP_POPUP_ENABLED = z6;
                            } else if (windowingMode3 == i2) {
                                Settings.Global.putInt(handleMenuHelpController.mContext.getContentResolver(), "freeform_handler_help_popup_count", 1);
                                HandleMenuHelpController.FREEFORM_HANDLER_HELP_POPUP_ENABLED = z6;
                            } else {
                                handleMenuHelpController.getClass();
                            }
                            if (this.mHandler.hasCallbacks(this.mCloseHelpPopupRunnable)) {
                                this.mHandler.removeCallbacks(this.mCloseHelpPopupRunnable);
                            }
                            this.mHandler.postDelayed(this.mCloseHelpPopupRunnable, 3000L);
                        }
                    }
                    desktopModeWindowDecoration = this;
                    z5 = z6;
                }
                boolean z19 = ((DesktopStateImpl) desktopModeWindowDecoration.mDesktopState).canEnterDesktopMode;
                desktopModeWindowDecoration.mMinimumInstancesFound = z5;
            }
            intentAddFlags = intent6;
        }
        Intent intent92 = intentAddFlags;
        DesktopModeUiEventLogger desktopModeUiEventLogger2 = this.mDesktopModeUiEventLogger;
        WindowDecoration.RelayoutResult relayoutResult3 = this.mResult;
        int i82 = relayoutResult3.mCaptionWidth;
        int i92 = relayoutResult3.mCaptionHeight;
        int i102 = relayoutResult3.mCaptionX;
        int i112 = relayoutResult3.mCaptionY + relayoutResult3.mCaptionTopPadding;
        DisplayController displayController3 = this.mDisplayController;
        boolean z102 = this.mIsDesktopModeSupportedOnDisplay;
        boolean z112 = this.mShouldShowRestartNotification;
        ((DefaultHandleMenuFactory) handleMenuFactory).getClass();
        this.mHandleMenu = new HandleMenu(mainCoroutineDispatcher, coroutineScope, this, windowManagerWrapper, windowDecorTaskResourceLoader, i7, splitScreenController, zCanEnterDesktopModeOrShowAppHandle, z, z2, z3, z9, zIsRestartMenuEnabledForDisplayMove, z4, intent92, desktopModeUiEventLogger2, i82, i92, i102, i112, displayController3, z102, z112);
        this.mWindowDecorViewHolder.onHandleMenuOpened();
        if (z8) {
        }
        boolean z192 = ((DesktopStateImpl) desktopModeWindowDecoration.mDesktopState).canEnterDesktopMode;
        desktopModeWindowDecoration.mMinimumInstancesFound = z5;
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
        boolean zIsTaskInFullImmersiveState = this.mDesktopUserRepositories.getProfile(this.mTaskInfo.userId).isTaskInFullImmersiveState(this.mTaskInfo.taskId);
        AppHeaderViewHolder appHeaderViewHolderAsAppHeader = asAppHeader(this.mWindowDecorViewHolder);
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        appHeaderViewHolderAsAppHeader.bindData(new AppHeaderViewHolder.HeaderData(runningTaskInfo, DesktopModeUtils.isTaskMaximized(runningTaskInfo, this.mDisplayController), zIsTaskInFullImmersiveState, this.mHasGlobalFocus, canOpenMaximizeMenu(z), isCaptionVisible()));
    }

    public final void setHandleOnTouchedState(boolean z) {
        ObjectAnimator objectAnimator;
        MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle = asMultiTaskingAppHandle(this.mWindowDecorViewHolder);
        if (multiTaskingHandleViewHolderAsMultiTaskingAppHandle != null) {
            multiTaskingHandleViewHolderAsMultiTaskingAppHandle.isHandleTouching = z;
            HandleHideAnimator handleHideAnimator = multiTaskingHandleViewHolderAsMultiTaskingAppHandle.handleHideAnimator;
            handleHideAnimator.mIsHandleTouching = z;
            handleHideAnimator.mHandler.removeCallbacks(handleHideAnimator.mHideRunnable);
            if (!z || (objectAnimator = handleHideAnimator.mHideHandleAnim) == null) {
                return;
            }
            objectAnimator.cancel();
        }
    }

    public final void showResizeVeil(Rect rect) {
        if (this.mResizeVeil == null) {
            this.mResizeVeil = new ResizeVeil(this.mContext, this.mDisplayController, this.mTaskResourceLoader, this.mMainDispatcher, this.mBgScope, this.mTaskSurface, this.mSurfaceControlTransactionSupplier, this.mTaskInfo);
        }
        ResizeVeil resizeVeil = this.mResizeVeil;
        SurfaceControl surfaceControl = this.mTaskSurface;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        if (resizeVeil.viewHost == null || resizeVeil.isVisible) {
            return;
        }
        resizeVeil.showVeil((SurfaceControl.Transaction) resizeVeil.surfaceControlTransactionSupplier.get(), surfaceControl, rect, runningTaskInfo, true);
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
            if (!runningTaskInfo.isFullSizeWindow || runningTaskInfo.isGameToolsOverlayVisible) {
                return false;
            }
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

    public final void updateAppHandleViewHolder() {
        if (isAppHandle(this.mWindowDecorViewHolder)) {
            WindowDecorationViewHolder windowDecorationViewHolder = this.mWindowDecorViewHolder;
            final AppHandleViewHolder appHandleViewHolder = windowDecorationViewHolder instanceof AppHandleViewHolder ? (AppHandleViewHolder) windowDecorationViewHolder : null;
            ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
            Point point = new Point(this.mResult.mCaptionX, 0);
            if (this.mSplitScreenController.getSplitPosition(this.mTaskInfo.taskId) == 1) {
                if (this.mSplitScreenController.isLeftRightSplit()) {
                    Rect rect = new Rect();
                    this.mSplitScreenController.getStageBounds(rect, new Rect());
                    point.x = rect.width() + point.x;
                } else {
                    Rect rect2 = new Rect();
                    this.mSplitScreenController.getRefStageBounds(new Rect(), rect2);
                    point.y += rect2.top;
                }
            }
            WindowDecoration.RelayoutResult relayoutResult = this.mResult;
            AppHandleViewHolder.HandleData handleData = new AppHandleViewHolder.HandleData(runningTaskInfo, point, relayoutResult.mCaptionWidth, relayoutResult.mCaptionHeight, !DesktopModeFlags.ENABLE_INPUT_LAYER_TRANSITION_FIX.isTrue() ? isCaptionVisible() : isCaptionVisible() && !this.mIsRecentsTransitionRunning, isCaptionVisible());
            appHandleViewHolder.getClass();
            ActivityManager.RunningTaskInfo runningTaskInfo2 = handleData.taskInfo;
            final Point point2 = handleData.position;
            int i = handleData.isCaptionVisible ? 0 : 8;
            if (appHandleViewHolder.captionView.getVisibility() != i && DesktopModeFlags.ENABLE_DESKTOP_APP_HANDLE_ANIMATION.isTrue()) {
                appHandleViewHolder.animator.animateVisibilityChange(i);
            }
            ImageButton imageButton = appHandleViewHolder.captionHandle;
            ActivityManager.TaskDescription taskDescription = runningTaskInfo2.taskDescription;
            imageButton.setImageTintList(ColorStateList.valueOf((taskDescription == null || (Color.alpha(taskDescription.getStatusBarColor()) == 0 || runningTaskInfo2.getWindowingMode() != 5 ? (8 & taskDescription.getSystemBarsAppearance()) != 0 : ((double) Color.valueOf(taskDescription.getStatusBarColor()).luminance()) >= 0.5d)) ? appHandleViewHolder.context.getColor(R.color.desktop_mode_caption_handle_bar_dark) : appHandleViewHolder.context.getColor(R.color.desktop_mode_caption_handle_bar_light)));
            appHandleViewHolder.taskInfo = runningTaskInfo2;
            if (point2.y >= SystemBarUtils.getStatusBarHeight(appHandleViewHolder.context) || !handleData.showInputLayer) {
                appHandleViewHolder.disposeStatusBarInputLayer();
                return;
            }
            if (appHandleViewHolder.statusBarInputLayerExists) {
                appHandleViewHolder.handler.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.viewholder.AppHandleViewHolder$bindData$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppHandleViewHolder appHandleViewHolder2 = appHandleViewHolder;
                        Point point3 = point2;
                        AdditionalSystemViewContainer additionalSystemViewContainer = appHandleViewHolder2.statusBarInputLayer;
                        if (additionalSystemViewContainer != null) {
                            additionalSystemViewContainer.setPosition(new SurfaceControl.Transaction(), point3.x, point3.y);
                        }
                    }
                });
                return;
            }
            appHandleViewHolder.statusBarInputLayerExists = true;
            Handler handler = appHandleViewHolder.handler;
            final int i2 = handleData.width;
            final int i3 = handleData.height;
            handler.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.viewholder.AppHandleViewHolder$bindData$2
                @Override // java.lang.Runnable
                public final void run() {
                    final AppHandleViewHolder appHandleViewHolder2 = appHandleViewHolder;
                    Point point3 = point2;
                    int i4 = i2;
                    int i5 = i3;
                    appHandleViewHolder2.getClass();
                    if (DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) {
                        Context context = appHandleViewHolder2.context;
                        WindowManagerWrapper windowManagerWrapper = appHandleViewHolder2.windowManagerWrapper;
                        ActivityManager.RunningTaskInfo runningTaskInfo3 = appHandleViewHolder2.taskInfo;
                        if (runningTaskInfo3 == null) {
                            runningTaskInfo3 = null;
                        }
                        AdditionalSystemViewContainer additionalSystemViewContainer = new AdditionalSystemViewContainer(context, windowManagerWrapper, runningTaskInfo3.taskId, point3.x, point3.y, i4, i5, 8, false);
                        appHandleViewHolder2.statusBarInputLayer = additionalSystemViewContainer;
                        View view = additionalSystemViewContainer.view;
                        if (view == null) {
                            throw new IllegalStateException("Unable to find statusBarInputLayer View");
                        }
                        WindowManager.LayoutParams layoutParams = additionalSystemViewContainer.lp;
                        if (layoutParams == null) {
                            throw new IllegalStateException("Unable to find statusBarInputLayer LayoutParams");
                        }
                        ActivityManager.RunningTaskInfo runningTaskInfo4 = appHandleViewHolder2.taskInfo;
                        if (runningTaskInfo4 == null) {
                            runningTaskInfo4 = null;
                        }
                        layoutParams.setTitle("Handle Input Layer of task " + runningTaskInfo4.taskId);
                        layoutParams.setTrustedOverlay();
                        layoutParams.inputFeatures = 4;
                        view.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.wm.shell.windowdecor.viewholder.AppHandleViewHolder$createStatusBarInputLayer$1
                            @Override // android.view.View.OnHoverListener
                            public final boolean onHover(View view2, MotionEvent motionEvent) {
                                return appHandleViewHolder2.captionHandle.onHoverEvent(motionEvent);
                            }
                        });
                        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.windowdecor.viewholder.AppHandleViewHolder$createStatusBarInputLayer$2
                            @Override // android.view.View.OnTouchListener
                            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                                if (motionEvent.getActionMasked() == 0) {
                                    appHandleViewHolder2.inputManager.pilferPointers(view2.getViewRootImpl().getInputToken());
                                }
                                appHandleViewHolder2.captionHandle.dispatchTouchEvent(motionEvent);
                                return true;
                            }
                        });
                        view.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.viewholder.AppHandleViewHolder$setupAppHandleA11y$1
                            @Override // android.view.View.AccessibilityDelegate
                            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                                view2.setClickable(true);
                            }

                            @Override // android.view.View.AccessibilityDelegate
                            public final void onPopulateAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
                                super.onPopulateAccessibilityEvent(view2, accessibilityEvent);
                                if (accessibilityEvent.getEventType() == 32768) {
                                    accessibilityEvent.getText().add(appHandleViewHolder2.captionHandle.getContentDescription());
                                }
                            }

                            @Override // android.view.View.AccessibilityDelegate
                            public final boolean performAccessibilityAction(View view2, int i6, Bundle bundle) {
                                if (i6 == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                                    AppHandleViewHolder appHandleViewHolder3 = appHandleViewHolder2;
                                    DesktopModeUiEventLogger desktopModeUiEventLogger = appHandleViewHolder3.desktopModeUiEventLogger;
                                    ActivityManager.RunningTaskInfo runningTaskInfo5 = appHandleViewHolder3.taskInfo;
                                    if (runningTaskInfo5 == null) {
                                        runningTaskInfo5 = null;
                                    }
                                    desktopModeUiEventLogger.log(runningTaskInfo5, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_APP_HANDLE_MENU_OPENED);
                                    appHandleViewHolder2.captionHandle.performClick();
                                }
                                return super.performAccessibilityAction(view2, i6, bundle);
                            }
                        });
                        ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, appHandleViewHolder2.context.getString(R.string.app_handle_chip_accessibility_announce), null);
                        appHandleViewHolder2.windowManagerWrapper.windowManager.updateViewLayout(view, layoutParams);
                    }
                }
            });
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final void updateCaptionContainerSurface(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, WindowDecoration.RelayoutResult relayoutResult) {
        SurfaceControl.Transaction transactionReparent = transaction.reparent(surfaceControl, this.mDecorationContainerSurface);
        float f = relayoutResult.mCaptionX;
        boolean z = CoreRune.MW_CAPTION;
        transactionReparent.setPosition(surfaceControl, f, z ? relayoutResult.mCaptionY : 0.0f).setMetadata(surfaceControl, 30, 3).setWindowCrop(surfaceControl, z ? -1 : relayoutResult.mCaptionWidth, z ? -1 : relayoutResult.mCaptionHeight).setLayer(surfaceControl, -1).show(surfaceControl);
    }

    public final void updateDisabledResizingEdge(DragResizeWindowGeometry.DisabledEdge disabledEdge, boolean z) {
        this.mDisabledResizingEdge = disabledEdge;
        boolean zIsTaskInFullImmersiveState = this.mDesktopUserRepositories.getCurrent().isTaskInFullImmersiveState(this.mTaskInfo.taskId);
        if (z) {
            return;
        }
        updateDragResizeListenerIfNeeded(this.mDecorationContainerSurface, zIsTaskInFullImmersiveState);
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
        int iDipToPixel = MultiWindowUtils.dipToPixel(10, displayMetrics);
        int iDipToPixel2 = MultiWindowUtils.dipToPixel(4, displayMetrics);
        int i2 = this.mRelayoutParams.mCornerRadius;
        WindowDecoration.RelayoutResult relayoutResult = this.mResult;
        final DragResizeWindowGeometry dragResizeWindowGeometry = new DragResizeWindowGeometry(i2, new Size(relayoutResult.mWidth, relayoutResult.mHeight), 48, iDipToPixel2, iDipToPixel, 48, this.mDisabledResizingEdge, i, this.mResult.mCaptionX, this.mLeftSideHintFrame, this.mRightSideHintFrame, this.mTaskInfo.positionInParent);
        Runnable runnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(Boolean.valueOf(dragResizeInputListener2.setGeometry(dragResizeWindowGeometry, scaledTouchSlop, !this.f$0.mTaskInfo.isForceHidden)));
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
        boolean zEquals = this.mTaskInfo.positionInParent.equals(this.mPositionInParent);
        boolean z3 = !zEquals;
        if (isDragResizable(this.mTaskInfo, z) && (!(z2 = CoreRune.MW_CAPTION_FREEFORM_STASH) || !this.mFreeformStashState.isStashed())) {
            if (z2) {
                closeFreeformDimInputListener();
            }
            updateDragResizeListener(surfaceControl, new DesktopModeWindowDecoration$$ExternalSyntheticLambda5(this, z3, z, 0));
            return;
        }
        if (!zEquals) {
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
            region.union(new Rect(0, 0, this.mResult.mWidth, WindowDecoration.loadDimensionPixelSize(this.mContext.getResources(), CoreRune.MW_CAPTION ? getMultitaskingCaptionHeightIdStatic(this.mCaptionType, this.mTaskInfo, this.mIsDesktopModeSupportedOnDisplay) : this.mTaskInfo.getWindowingMode() == 1 ? 17106380 : SystemBarUtils.getDesktopViewAppHeaderHeightId())));
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
                    DesktopRepository desktopRepository2 = desktopRepository;
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
        View viewFindViewById = ((WindowDecorLinearLayout) this.mResult.mRootView).findViewById(R.id.caption_handle);
        boolean z = false;
        boolean z2 = !isHandleMenuActive() && checkTouchEventInFocusedCaptionHandle(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        viewFindViewById.setHovered(z2 && actionMasked != 1);
        if ((z2 && actionMasked == 0) || (viewFindViewById.isPressed() && actionMasked != 1 && actionMasked != 3)) {
            z = true;
        }
        viewFindViewById.setPressed(z);
        if (isHandleMenuActive()) {
            this.mHandleMenu.checkMotionEvent(motionEvent);
        }
    }

    public final void updateMaximizeMenu(SurfaceControl.Transaction transaction, boolean z) {
        int measuredWidth;
        if (isDragResizable(this.mTaskInfo, z) && isMaximizeMenuActive()) {
            if (!this.mTaskInfo.isVisible()) {
                closeMaximizeMenu();
                return;
            }
            MaximizeMenu maximizeMenu = this.mMaximizeMenu;
            MaximizeMenu.MaximizeMenuView maximizeMenuView = maximizeMenu.maximizeMenuView;
            if (maximizeMenuView != null) {
                maximizeMenuView.rootView.measure(0, 0);
                measuredWidth = maximizeMenuView.rootView.getMeasuredWidth();
            } else {
                measuredWidth = 0;
            }
            Integer numValueOf = Integer.valueOf(measuredWidth);
            MaximizeMenu.MaximizeMenuView maximizeMenuView2 = maximizeMenu.maximizeMenuView;
            Point point = (Point) maximizeMenu.positionSupplier.invoke(numValueOf, Integer.valueOf(maximizeMenuView2 != null ? maximizeMenuView2.measureHeight() : 0));
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

    public final void updateMultiTaskingHeaderViewHolder(boolean z, boolean z2) {
        if (isAppHeader(this.mWindowDecorViewHolder)) {
            MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolderAsMultiTaskingAppHeader = asMultiTaskingAppHeader(this.mWindowDecorViewHolder);
            ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
            boolean z3 = this.mIsTaskMaximized;
            boolean zCanOpenMaximizeMenu = canOpenMaximizeMenu(false);
            boolean zIsCaptionVisible = isCaptionVisible();
            boolean z4 = this.mIsKeyguardShowing;
            boolean z5 = this.mIsStatusBarVisible;
            WindowDecoration.RelayoutResult relayoutResult = this.mResult;
            multiTaskingHeaderViewHolderAsMultiTaskingAppHeader.bindData(new MultiTaskingHeaderViewHolder.HeaderData(runningTaskInfo, z3, z, z2, zCanOpenMaximizeMenu, zIsCaptionVisible, z4, z5, relayoutResult.mCaptionHeight + relayoutResult.mCaptionTopPadding));
            if (!z) {
                DecorationInputEventReceiver decorationInputEventReceiver = this.mInputEventReceiver;
                if (decorationInputEventReceiver != null) {
                    decorationInputEventReceiver.dispose();
                    this.mInputEventReceiver = null;
                    return;
                }
                return;
            }
            if (this.mInputEventReceiver != null || this.mTaskInfo.displayId == 0) {
                return;
            }
            InputMonitor inputMonitorMonitorGestureInput = InputManager.getInstance().monitorGestureInput("handle-Show", this.mTaskInfo.getDisplayId());
            try {
                IWindowSession windowSession = WindowManagerGlobal.getWindowSession();
                IBinder token = inputMonitorMonitorGestureInput.getInputChannel().getToken();
                int i = this.mTaskInfo.displayId;
                SurfaceControl surface = inputMonitorMonitorGestureInput.getSurface();
                WindowDecoration.RelayoutResult relayoutResult2 = this.mResult;
                windowSession.updateInputChannel(token, i, surface, 0, 0, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, new Region(0, 0, relayoutResult2.mCaptionWidth, relayoutResult2.mCaptionHeight));
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            this.mInputEventReceiver = new DecorationInputEventReceiver(inputMonitorMonitorGestureInput, inputMonitorMonitorGestureInput.getInputChannel(), Looper.myLooper(), false);
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
            TaskPositioner taskPositioner = this.mTaskPositioner;
            if (taskPositioner != null && taskPositioner.getTaskMotionController() != null && this.mTaskPositioner.getTaskMotionController().isBoundsAnimating()) {
                return;
            }
        }
        Point point = this.mTaskInfo.positionInParent;
        boolean zEquals = this.mTaskPosition.equals(point);
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
        if (!z && !z2 && !zEquals) {
            transaction2.setPosition(this.mTaskSurface, point.x, point.y);
            this.mTaskPosition.set(point);
        }
        super.updateTaskSurface(relayoutParams, transaction, transaction2, relayoutResult);
    }

    public DesktopModeWindowDecoration(Context context, Context context2, DisplayController displayController, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, SplitScreenController splitScreenController, DesktopUserRepositories desktopUserRepositories, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Handler handler, ShellExecutor shellExecutor, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, ShellExecutor shellExecutor2, Choreographer choreographer, SyncTransactionQueue syncTransactionQueue, AppHeaderViewHolder.Factory factory, AppHandleViewHolder.Factory factory2, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, AppToWebGenericLinksParser appToWebGenericLinksParser, AssistContentRequester assistContentRequester, Supplier<SurfaceControl.Builder> supplier, Supplier<SurfaceControl.Transaction> supplier2, Supplier<WindowContainerTransaction> supplier3, Supplier<SurfaceControl> supplier4, WindowManagerWrapper windowManagerWrapper, WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory, WindowDecorViewHostSupplier windowDecorViewHostSupplier, MaximizeMenuFactory maximizeMenuFactory, HandleMenuFactory handleMenuFactory, MultiInstanceHelper multiInstanceHelper, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, DesktopModeEventLogger desktopModeEventLogger, DesktopModeUiEventLogger desktopModeUiEventLogger, DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopState desktopState, DesktopConfig desktopConfig, MultiTaskingHeaderViewHolder.Factory factory3, HandleMenuHelpController handleMenuHelpController, DesktopImmersiveController desktopImmersiveController, DesktopTilingDecorViewModel desktopTilingDecorViewModel, DesktopModeWindowDecorViewModel.DecorViewModelState decorViewModelState) {
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
        this.mCloseHelpPopupRunnable = new DesktopModeWindowDecoration$$ExternalSyntheticLambda0(this, 1);
        this.mLastDisplayBounds = new Rect();
        this.mIsFreeformCaptionTypeChanged = false;
        this.mLeftSideHintFrame = new Rect();
        this.mRightSideHintFrame = new Rect();
        this.mIsTaskResizing = false;
        this.mShouldShowRestartNotification = true;
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
        this.mFreeformAdjustImeController = new FreeformAdjustImeController(this, this.mDisplayController, this.mTaskOrganizer, this.mTaskSurface, decorViewModelState);
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
        this.mDesktopTilingDecorViewModel = desktopTilingDecorViewModel;
    }

    /* JADX WARN: Removed duplicated region for block: B:157:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x037f  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x0618  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0800  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x0825  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x083e A[PHI: r2 r3
      0x083e: PHI (r2v34 int) = (r2v23 int), (r2v37 int) binds: [B:395:0x085a, B:387:0x083c] A[DONT_GENERATE, DONT_INLINE]
      0x083e: PHI (r3v54 int) = (r3v13 int), (r3v55 int) binds: [B:395:0x085a, B:387:0x083c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:390:0x0843  */
    /* JADX WARN: Removed duplicated region for block: B:415:0x08c4  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x08e8  */
    /* JADX WARN: Removed duplicated region for block: B:430:0x091d  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x091f  */
    /* JADX WARN: Removed duplicated region for block: B:434:0x0924  */
    /* JADX WARN: Removed duplicated region for block: B:477:0x09b1  */
    /* JADX WARN: Removed duplicated region for block: B:480:0x09bd  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x09c4  */
    /* JADX WARN: Removed duplicated region for block: B:486:0x09d2  */
    /* JADX WARN: Removed duplicated region for block: B:526:0x0a54  */
    /* JADX WARN: Removed duplicated region for block: B:529:0x0a67  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, boolean z, boolean z2, boolean z3, Region region) {
        boolean z4;
        Context context;
        boolean z5;
        boolean zShouldExcludeCaptionFromAppBounds;
        int i;
        boolean z6;
        SurfaceControl surfaceControl;
        DesktopModeWindowDecoration desktopModeWindowDecoration;
        FreeformDimInputListener freeformDimInputListener;
        int i2;
        int i3;
        boolean z7;
        boolean z8;
        boolean z9;
        View view;
        View view2;
        View view3;
        MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle;
        boolean zIsCaptionVisible;
        WindowDecorationViewHolder appHeaderViewHolder;
        WindowDecorationViewHolder multiTaskingHandleViewHolder;
        boolean z10;
        boolean z11;
        MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle2;
        String string;
        PackageManager packageManager;
        FreeformOutline freeformOutline;
        MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolderAsMultiTaskingAppHeader;
        DisplayCutout displayCutout;
        ComponentName componentName;
        Display display;
        int iHeight;
        DisplayCutout displayCutout2;
        boolean zIntersect;
        int captionType;
        Context displayContext;
        CapturedLink capturedLink;
        Trace.beginSection("DesktopModeWindowDecoration#relayout");
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_APP_TO_WEB.isTrue()) {
            Uri uri = runningTaskInfo.capturedLink;
            long j = runningTaskInfo.capturedLinkTimestamp;
            if (uri != null && ((capturedLink = this.mCapturedLink) == null || capturedLink.mTimeStamp != j)) {
                this.mCapturedLink = new CapturedLink(uri, j);
            }
        }
        if (DesktopExperienceFlags.ENABLE_BUG_FIXES_FOR_SECONDARY_DISPLAY.isTrue() && (displayContext = this.mDisplayController.getDisplayContext(runningTaskInfo.displayId)) != null) {
            this.mWindowManagerWrapper.windowManager = (WindowManager) displayContext.getSystemService(WindowManager.class);
        }
        if (isHandleMenuActive()) {
            HandleMenu handleMenu = this.mHandleMenu;
            WindowDecoration.RelayoutResult relayoutResult = this.mResult;
            int i4 = relayoutResult.mCaptionX;
            int i5 = relayoutResult.mCaptionY + relayoutResult.mCaptionTopPadding;
            AdditionalViewContainer additionalViewContainer = handleMenu.handleMenuViewContainer;
            if (additionalViewContainer != null) {
                handleMenu.updateHandleMenuPillPositions(i4, i5);
                PointF pointF = handleMenu.handleMenuPosition;
                additionalViewContainer.setPosition(transaction, pointF.x, pointF.y);
            }
        }
        OpenByDefaultDialog openByDefaultDialog = this.mOpenByDefaultDialog;
        if (openByDefaultDialog != null) {
            SurfaceControl.Transaction transaction3 = (SurfaceControl.Transaction) openByDefaultDialog.surfaceControlTransactionSupplier.get();
            Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
            SurfaceControl surfaceControl2 = openByDefaultDialog.dialogSurfaceControl;
            if (surfaceControl2 == null) {
                surfaceControl2 = null;
            }
            transaction3.setWindowCrop(surfaceControl2, bounds.width(), bounds.height());
            SurfaceControlViewHost surfaceControlViewHost = openByDefaultDialog.viewHost;
            if (surfaceControlViewHost == null) {
                surfaceControlViewHost = null;
            }
            surfaceControlViewHost.getRootSurfaceControl().applyTransactionOnDraw(transaction3);
            SurfaceControlViewHost surfaceControlViewHost2 = openByDefaultDialog.viewHost;
            if (surfaceControlViewHost2 == null) {
                surfaceControlViewHost2 = null;
            }
            surfaceControlViewHost2.relayout(bounds.width(), bounds.height());
        }
        boolean zIsTaskInFullImmersiveState = this.mDesktopUserRepositories.getProfile(runningTaskInfo.userId).isTaskInFullImmersiveState(runningTaskInfo.taskId);
        if (zIsTaskInFullImmersiveState || this.mIsTaskMaximized) {
            this.mFreeformAdjustImeController.resetStateIfNeeded("ImmersiveOrMaximize");
        }
        if (CoreRune.MW_CAPTION_POPUP && isHandleMenuActive()) {
            PopupButtonPolicy popupButtonPolicy = this.mHandleMenu.buttonPolicy;
            if (popupButtonPolicy == null) {
                popupButtonPolicy = null;
            }
            MenuPopupAnimator menuPopupAnimator = popupButtonPolicy.mMenuPopupAnimator;
            if ((menuPopupAnimator == null || menuPopupAnimator.runningAnimation == null) && !runningTaskInfo.getConfiguration().windowConfiguration.getBounds().equals(this.mTaskInfo.getConfiguration().windowConfiguration.getBounds()) && isHandleMenuActive()) {
                WindowDecorationViewHolder windowDecorationViewHolder = this.mWindowDecorViewHolder;
                if (windowDecorationViewHolder != null) {
                    windowDecorationViewHolder.onHandleMenuClosed();
                }
                this.mHandleMenu.closeMenuPopupImmediately();
                this.mHandleMenu = null;
                boolean z12 = ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode;
            }
        }
        boolean z13 = CoreRune.MW_CAPTION;
        if (z13) {
            int displayId = this.mContext.getDisplayId();
            int i6 = runningTaskInfo.displayId;
            if (displayId != i6 && this.mDisplayController.getDisplayContext(i6) != null) {
                this.mContext = this.mDisplayController.getDisplayContext(runningTaskInfo.displayId);
            }
            InsetsState insetsState = this.mDisplayController.getInsetsState(runningTaskInfo.displayId);
            this.mIsStatusBarVisible = insetsState != null && InsetsStateKt.isVisible(WindowInsets.Type.statusBars(), insetsState);
            if (insetsState != null && runningTaskInfo.displayId == 0) {
                Rect rect = new Rect(insetsState.getLeftSideHintFrame());
                Rect rect2 = new Rect(insetsState.getRightSideHintFrame());
                if (!rect.equals(this.mLeftSideHintFrame) || !rect2.equals(this.mRightSideHintFrame)) {
                    this.mLeftSideHintFrame.set(rect);
                    this.mRightSideHintFrame.set(rect2);
                    updateDragResizeListenerIfNeeded(this.mDecorationContainerSurface, zIsTaskInFullImmersiveState);
                }
            } else if (!this.mLeftSideHintFrame.isEmpty() || !this.mRightSideHintFrame.isEmpty()) {
                this.mLeftSideHintFrame.setEmpty();
                this.mRightSideHintFrame.setEmpty();
                updateDragResizeListenerIfNeeded(this.mDecorationContainerSurface, zIsTaskInFullImmersiveState);
            }
        }
        this.mIsFreeformCaptionTypeChanged = false;
        boolean z14 = CoreRune.MW_CAPTION_TYPE;
        if (z14 && (captionType = getCaptionType(runningTaskInfo)) != this.mCaptionType) {
            this.mCaptionType = captionType;
            if (!this.mInDesktopWindowing && runningTaskInfo.isFreeform() && this.mTaskInfo.isFreeform()) {
                this.mIsFreeformCaptionTypeChanged = true;
            }
        }
        int i7 = this.mTaskInfo.displayId;
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(i7);
        if (displayLayout != null) {
            displayLayout.getDisplayBounds(this.mLastDisplayBounds);
            displayLayout.getStableBounds(this.mLastStableBounds, false);
            this.mDisplayCutout = displayLayout.mCutout;
            if (CoreRune.MW_CAPTION_FULL_SCREEN_CUTOUT) {
                if (runningTaskInfo.getWindowingMode() == 1 && (displayCutout2 = this.mDisplayCutout) != null) {
                    Rect boundingRectTop = displayCutout2.getBoundingRectTop();
                    displayLayout.getDisplayBounds(this.mTmpRect);
                    Rect rect3 = this.mTmpRect;
                    Resources resources = this.mContext.getResources();
                    int iLoadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(resources, R.dimen.mw_handle_width_tablet);
                    int iLoadDimensionPixelSize2 = WindowDecoration.loadDimensionPixelSize(resources, R.dimen.mw_handle_height);
                    int iWidth = (rect3.width() - iLoadDimensionPixelSize) / 2;
                    zIntersect = new Rect(iWidth, 0, iLoadDimensionPixelSize + iWidth, iLoadDimensionPixelSize2).intersect(boundingRectTop);
                } else {
                    zIntersect = false;
                }
                if (zIntersect != this.mIsCutoutLocatedInCenter) {
                    this.mIsCutoutLocatedInCenter = zIntersect;
                    this.mIsCutoutLocatedInCenterChanged = true;
                }
            }
            boolean zIsTaskMaximized = DesktopModeUtils.isTaskMaximized(runningTaskInfo, this.mDisplayController);
            if (this.mIsTaskMaximized != zIsTaskMaximized) {
                this.mIsTaskMaximized = zIsTaskMaximized;
            }
        }
        boolean z15 = CoreRune.MW_CAPTION_INSETS;
        if (z15) {
            DisplayCutout displayCutout3 = this.mDisplayCutout;
            if (displayCutout3 != null) {
                iHeight = displayCutout3.getBoundingRectTop().height();
            } else {
                Rect rect4 = runningTaskInfo.safeCutoutInsets;
                iHeight = rect4 != null ? rect4.top : 0;
            }
            this.mTopDisplayCutoutInset = iHeight;
        }
        if (CoreRune.MW_CAPTION_DESKTOP && (display = this.mDisplay) != null) {
            this.mIsDesktopModeSupportedOnDisplay = ((DesktopStateImpl) this.mDesktopState).isDesktopModeSupportedOnDisplay(display);
        }
        boolean z16 = CoreRune.MW_CAPTION_HANDLE_SYSTEM_INPUT;
        if (z16) {
            this.mIsHandleOverlappedSystemBar = runningTaskInfo.getWindowingMode() == 1 && runningTaskInfo.configuration.windowConfiguration.getBounds().top == 0 && (this.mIsDesktopModeSupportedOnDisplay || this.mTopDisplayCutoutInset != 0);
        }
        boolean z17 = CoreRune.MW_CAPTION_DESKTOP_IMMERSIVE;
        if (z17 && zIsTaskInFullImmersiveState && displayLayout != null) {
            displayLayout.getDisplayBounds(this.mTmpRect);
            if (!this.mTmpRect.equals(runningTaskInfo.configuration.windowConfiguration.getBounds()) || !this.mInDesktopWindowing) {
                this.mDesktopUserRepositories.getProfile(runningTaskInfo.userId).setTaskInFullImmersiveState(runningTaskInfo.displayId, runningTaskInfo.taskId, false);
                z4 = false;
            }
        } else {
            z4 = zIsTaskInFullImmersiveState;
        }
        boolean z18 = CoreRune.MW_CAPTION_FREEFORM;
        if (!z18 || (context = this.mDecorWindowContext) == null) {
            context = this.mContext;
        }
        WindowDecoration.RelayoutParams relayoutParams = this.mRelayoutParams;
        SplitScreenController splitScreenController = this.mSplitScreenController;
        boolean z19 = this.mIsStatusBarVisible;
        boolean z20 = this.mIsKeyguardVisibleAndOccluded;
        boolean z21 = true;
        Context context2 = context;
        boolean z22 = this.mIsDragging;
        InsetsState insetsState2 = this.mDisplayController.getInsetsState(runningTaskInfo.displayId);
        if (!this.mIsRecentsTransitionRunning || !DesktopModeFlags.ENABLE_DESKTOP_RECENTS_TRANSITIONS_CORNERS_BUGFIX.isTrue()) {
            z21 = false;
        }
        this.mDesktopModeCompatPolicy.getClass();
        ActivityInfo activityInfo = ((TaskInfo) runningTaskInfo).topActivityInfo;
        if (activityInfo != null) {
            z5 = z18;
            zShouldExcludeCaptionFromAppBounds = DesktopModeCompatUtils.shouldExcludeCaptionFromAppBounds(activityInfo, ((TaskInfo) runningTaskInfo).isResizeable, ((TaskInfo) runningTaskInfo).appCompatTaskInfo.hasOptOutEdgeToEdge());
        } else {
            z5 = z18;
            zShouldExcludeCaptionFromAppBounds = false;
        }
        updateRelayoutParams(relayoutParams, context2, runningTaskInfo, splitScreenController, z, z2, z19, z20, z4, z22, insetsState2, z3, region, z21, zShouldExcludeCaptionFromAppBounds, this.mDesktopConfig, this.mCaptionType, this.mInDesktopWindowing, this.mIsTaskMaximized, this.mIsDesktopModeSupportedOnDisplay);
        ActivityManager.RunningTaskInfo runningTaskInfo2 = runningTaskInfo;
        if (z15) {
            Insets displayInsetsState = getDisplayInsetsState(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
            if (displayInsetsState != null) {
                this.mRelayoutParams.mDisplayTopInset = displayInsetsState.top;
                runningTaskInfo2.isSplitScreen();
            }
            if (CoreRune.MW_CAPTION_FULL_SCREEN_CUTOUT && this.mIsCutoutLocatedInCenter) {
                Insets displayInsetsState2 = getDisplayInsetsState(WindowInsets.Type.displayCutout());
                if (displayInsetsState2 != null && displayInsetsState2.top != 0) {
                    this.mCenterCutoutTopInset = WindowDecoration.loadDimensionPixelSize(this.mContext.getResources(), R.dimen.mw_handle_desktop_full_screen_tablet_cutout_margin_top);
                    i = 0;
                } else {
                    i = 0;
                    this.mCenterCutoutTopInset = 0;
                }
            }
        } else {
            i = 0;
        }
        if (z16 && !DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) {
            if (isDecorHandleState() && !this.mTaskInfo.isFreeform() && supportStatusBarInputLayer()) {
                this.mRelayoutParams.mInputFeatures |= 1;
            } else {
                this.mRelayoutParams.mInputFeatures &= -2;
            }
        }
        if (z17 && this.mInDesktopWindowing && z4 && displayLayout != null) {
            this.mRelayoutParams.mCaptionTopPadding = displayLayout.mStableInsets.top;
        }
        boolean z23 = CoreRune.MW_CAPTION_FREEFORM_STASH;
        if (z23 && !this.mInDesktopWindowing && this.mTaskPositioner != null) {
            Rect bounds2 = runningTaskInfo2.configuration.windowConfiguration.getBounds();
            if (this.mTaskPositioner.getTaskMotionController() != null) {
                float fHeight = r3.mScaledFreeformHeight / bounds2.height();
                FreeformStashState freeformStashState = this.mFreeformStashState;
                if (fHeight != freeformStashState.mScale) {
                    FreeformColorOverlay freeformColorOverlay = freeformStashState.mStashDimOverlay;
                    if (freeformColorOverlay != null) {
                        synchronized (freeformColorOverlay.mLock) {
                            try {
                                if (freeformColorOverlay.isLeashValidLocked()) {
                                    freeformColorOverlay.mTransaction.setPosition(freeformColorOverlay.mLeash, 0.0f, 0.0f);
                                    freeformColorOverlay.mCropRect.set(i, i, bounds2.width(), bounds2.height());
                                    freeformColorOverlay.mTransaction.setCrop(freeformColorOverlay.mLeash, freeformColorOverlay.mCropRect).apply();
                                }
                            } finally {
                            }
                        }
                    }
                    this.mFreeformStashState.mScale = fHeight;
                }
            }
        }
        WindowDecorLinearLayout windowDecorLinearLayout = (WindowDecorLinearLayout) this.mResult.mRootView;
        SurfaceControl surfaceControl3 = this.mDecorationContainerSurface;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        relayout(this.mRelayoutParams, transaction, transaction2, windowContainerTransaction, windowDecorLinearLayout, this.mResult);
        if (z5 && this.mTaskInfo.isFreeform() && ((componentName = this.mTaskInfo.realActivity) == null || !MultiWindowUtils.isWindowManagerCtsPackage(componentName.getPackageName()))) {
            if (this.mFreeformOutline == null) {
                this.mFreeformOutline = new FreeformOutline(this);
            }
            boolean z24 = z4;
            surfaceControl = surfaceControl3;
            this.mFreeformOutline.relayout(this.mTaskInfo, this.mDecorWindowContext, this.mRelayoutParams.mCornerRadius, true, z24);
            z6 = z24;
        } else {
            z6 = z4;
            surfaceControl = surfaceControl3;
        }
        Trace.beginSection("DesktopModeWindowDecoration#relayout-applyWCT");
        this.mBgExecutor.execute(new DesktopModeWindowDecoration$$ExternalSyntheticLambda2(this, windowContainerTransaction, 0));
        Trace.endSection();
        if (z23 && !this.mInDesktopWindowing && (displayCutout = this.mDisplayCutout) != null) {
            Rect safeInsets = displayCutout.getSafeInsets();
            Rect rect5 = this.mLastStableBounds;
            rect5.set(rect5.left - safeInsets.left, rect5.top, rect5.right + safeInsets.right, rect5.bottom);
        }
        if (this.mResult.mRootView == null) {
            boolean z25 = ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode;
            DesktopModeWindowDecorViewModel.ExclusionRegionListenerImpl exclusionRegionListenerImpl = this.mExclusionRegionListener;
            int i8 = this.mTaskInfo.taskId;
            DesktopRepository desktopRepository = DesktopModeWindowDecorViewModel.this.mDesktopTasksController.taskRepository;
            desktopRepository.desktopExclusionRegions.delete(i8);
            Executor executor = desktopRepository.desktopGestureExclusionExecutor;
            if (executor != null) {
                executor.execute(new DesktopRepository$removeExclusionRegion$1(desktopRepository));
            }
            disposeStatusBarInputLayer();
            Trace.endSection();
            return;
        }
        if (!DesktopModeFlags.SKIP_DECOR_VIEW_RELAYOUT_WHEN_CLOSING_BUGFIX.isTrue() ? windowDecorLinearLayout != this.mResult.mRootView : !(windowDecorLinearLayout == this.mResult.mRootView || (!runningTaskInfo2.isVisibleRequested && ((!z14 || !this.mIsFreeformCaptionTypeChanged) && (!CoreRune.MW_CAPTION_BUG_FIX || ((!this.mInDesktopWindowing || asMultiTaskingAppHeader(this.mWindowDecorViewHolder) != null) && i7 == this.mTaskInfo.displayId)))))) {
            disposeStatusBarInputLayer();
            if (z13) {
                DecorationInputEventReceiver decorationInputEventReceiver = this.mInputEventReceiver;
                if (decorationInputEventReceiver != null) {
                    decorationInputEventReceiver.dispose();
                    this.mInputEventReceiver = null;
                }
                if (this.mCaptionType == 0) {
                    multiTaskingHandleViewHolder = new MultiTaskingHandleViewHolder(this.mResult.mRootView, this.mOnCaptionTouchListener, this.mOnCaptionButtonClickListener, this.mWindowManagerWrapper, this.mHandler, this.mDesktopModeUiEventLogger);
                    this.mWindowDecorViewHolder = multiTaskingHandleViewHolder;
                    if (!z13) {
                        if (isDecorCaptionState$1()) {
                            final int i9 = 0;
                            loadTaskNameAndIconInBackground(new BiConsumer(this) { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$$ExternalSyntheticLambda3
                                public final /* synthetic */ DesktopModeWindowDecoration f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) throws Resources.NotFoundException {
                                    Paint paint;
                                    Bitmap bitmap;
                                    Object obj3;
                                    Bitmap bitmap2;
                                    int i10 = i9;
                                    DesktopModeWindowDecoration desktopModeWindowDecoration2 = this.f$0;
                                    switch (i10) {
                                        case 0:
                                            CharSequence charSequence = (CharSequence) obj;
                                            Bitmap bitmap3 = (Bitmap) obj2;
                                            MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2 = DesktopModeWindowDecoration.asMultiTaskingAppHeader(desktopModeWindowDecoration2.mWindowDecorViewHolder);
                                            if (multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2 != null) {
                                                boolean z26 = CoreRune.MW_CAPTION_DESKTOP;
                                                if (!z26) {
                                                    TextView textView = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.appNameTextView;
                                                    if (textView != null) {
                                                        textView.setText(charSequence);
                                                    }
                                                    View view4 = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.openMenuButton;
                                                    if (view4 != null) {
                                                        view4.setContentDescription(multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getString(R.string.desktop_mode_app_header_chip_text, charSequence));
                                                    }
                                                    multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.closeWindowButton.setContentDescription(multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getString(R.string.close_button_text, charSequence));
                                                    ImageButton imageButton = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.minimizeWindowButton;
                                                    if (imageButton != null) {
                                                        imageButton.setContentDescription(multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getString(R.string.minimize_button_text, charSequence));
                                                    }
                                                    multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.a11yTextMaximize = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getString(R.string.maximize_button_text, charSequence);
                                                    multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.a11yTextRestore = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getString(R.string.restore_button_text, charSequence);
                                                    multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.updateMaximizeButtonContentDescription$1();
                                                }
                                                if (!z26) {
                                                    ImageView imageView = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.appIconImageView;
                                                    if (imageView != null) {
                                                        imageView.setImageBitmap(bitmap3);
                                                        break;
                                                    }
                                                } else {
                                                    int color = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getResources().getColor(R.color.app_icon_stroke_color, null);
                                                    int iCeil = (int) Math.ceil(multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getResources().getDimension(R.dimen.mw_desktop_open_menu_icon_stroke));
                                                    if (bitmap3 == null || bitmap3.isRecycled()) {
                                                        paint = null;
                                                        Log.e("DesktopModeAppControlsWindowDecorationViewHolder", "Input originalBitmap is null or recycled.");
                                                        bitmap = null;
                                                    } else {
                                                        int i11 = iCeil * 2;
                                                        int width = bitmap3.getWidth() + i11;
                                                        int height = bitmap3.getHeight() + i11;
                                                        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                                                        Canvas canvas = new Canvas(bitmapCreateBitmap);
                                                        float width2 = (width - bitmap3.getWidth()) / 2.0f;
                                                        float height2 = (height - bitmap3.getHeight()) / 2.0f;
                                                        if (iCeil > 0) {
                                                            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
                                                            Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
                                                            Paint paint2 = new Paint(1);
                                                            paint2.setColor(-1);
                                                            Rect rect6 = new Rect(0, 0, bitmap3.getWidth(), bitmap3.getHeight());
                                                            float f = iCeil;
                                                            bitmap2 = bitmapCreateBitmap;
                                                            canvas2.drawBitmap(bitmap3, rect6, new RectF(width2 - f, height2 - f, bitmap3.getWidth() + width2 + f, bitmap3.getHeight() + height2 + f), paint2);
                                                            Paint paint3 = new Paint(1);
                                                            paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                                                            canvas2.drawBitmap(bitmap3, rect6, new RectF(width2, height2, bitmap3.getWidth() + width2, bitmap3.getHeight() + height2), paint3);
                                                            Paint paint4 = new Paint(1);
                                                            paint4.setColor(color);
                                                            canvas.drawBitmap(bitmapCreateBitmap2, 0.0f, 0.0f, paint4);
                                                            bitmapCreateBitmap2.recycle();
                                                            paint = null;
                                                        } else {
                                                            bitmap2 = bitmapCreateBitmap;
                                                            paint = null;
                                                        }
                                                        canvas.drawBitmap(bitmap3, width2, height2, paint);
                                                        bitmap = bitmap2;
                                                    }
                                                    if (bitmap != null) {
                                                        ImageView imageView2 = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.appIconImageView;
                                                        if (imageView2 != null) {
                                                            imageView2.setImageBitmap(bitmap);
                                                            obj3 = Unit.INSTANCE;
                                                        } else {
                                                            obj3 = paint;
                                                        }
                                                        if (obj3 != null) {
                                                        }
                                                    }
                                                    Log.e("DesktopModeAppControlsWindowDecorationViewHolder", "Failed to apply effects, displaying original bitmap as fallback.");
                                                    ImageView imageView3 = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.appIconImageView;
                                                    if (imageView3 != null) {
                                                        imageView3.setImageBitmap(bitmap3);
                                                        Unit unit = Unit.INSTANCE;
                                                        break;
                                                    }
                                                }
                                            }
                                            break;
                                        default:
                                            CharSequence charSequence2 = (CharSequence) obj;
                                            Bitmap bitmap4 = (Bitmap) obj2;
                                            AppHeaderViewHolder appHeaderViewHolderAsAppHeader = DesktopModeWindowDecoration.asAppHeader(desktopModeWindowDecoration2.mWindowDecorViewHolder);
                                            if (appHeaderViewHolderAsAppHeader != null) {
                                                appHeaderViewHolderAsAppHeader.appNameTextView.setText(charSequence2);
                                                appHeaderViewHolderAsAppHeader.openMenuButton.setContentDescription(appHeaderViewHolderAsAppHeader.context.getString(R.string.desktop_mode_app_header_chip_text, charSequence2));
                                                appHeaderViewHolderAsAppHeader.closeWindowButton.setContentDescription(appHeaderViewHolderAsAppHeader.context.getString(R.string.close_button_text, charSequence2));
                                                appHeaderViewHolderAsAppHeader.minimizeWindowButton.setContentDescription(appHeaderViewHolderAsAppHeader.context.getString(R.string.minimize_button_text, charSequence2));
                                                appHeaderViewHolderAsAppHeader.a11yTextMaximize = appHeaderViewHolderAsAppHeader.context.getString(R.string.maximize_button_text, charSequence2);
                                                appHeaderViewHolderAsAppHeader.a11yTextRestore = appHeaderViewHolderAsAppHeader.context.getString(R.string.restore_button_text, charSequence2);
                                                appHeaderViewHolderAsAppHeader.updateMaximizeButtonContentDescription();
                                                appHeaderViewHolderAsAppHeader.appIconImageView.setImageBitmap(bitmap4);
                                            }
                                            boolean z27 = ((DesktopStateImpl) desktopModeWindowDecoration2.mDesktopState).canEnterDesktopMode;
                                            break;
                                    }
                                }
                            });
                            if (CoreRune.MW_CAPTION_DESKTOP_RESTART && this.mInDesktopWindowing && runningTaskInfo2.appCompatTaskInfo.isRestartMenuEnabledForDisplayMove() && (multiTaskingHeaderViewHolderAsMultiTaskingAppHeader = asMultiTaskingAppHeader(this.mWindowDecorViewHolder)) != null) {
                                ImageView imageView = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader.captionButtonPolicy.mDesktopNotification;
                                if (imageView != null) {
                                    imageView.setVisibility(0);
                                }
                                this.mShouldShowRestartNotification = true;
                            }
                        } else {
                            MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle3 = asMultiTaskingAppHandle(this.mWindowDecorViewHolder);
                            if (multiTaskingHandleViewHolderAsMultiTaskingAppHandle3 != null) {
                                if (CoreRune.MW_CAPTION_HANDLE) {
                                    multiTaskingHandleViewHolderAsMultiTaskingAppHandle3.setupHandleVerticalPadding(runningTaskInfo2, this.mSplitScreenController.isParallelMultiSplit(), this.mDesktopState, this.mIsCutoutLocatedInCenter);
                                    ComponentName componentName2 = runningTaskInfo2.realActivity;
                                    if (componentName2 != null) {
                                        String packageName = componentName2.getPackageName();
                                        ImageButton imageButton = multiTaskingHandleViewHolderAsMultiTaskingAppHandle3.captionHandle;
                                        if (imageButton instanceof HandleImageButton) {
                                            HandleImageButton handleImageButton = (HandleImageButton) imageButton;
                                            StringBuilder sb = new StringBuilder();
                                            if (handleImageButton.appName.length() > 0) {
                                                string = handleImageButton.appName;
                                            } else {
                                                try {
                                                } catch (Exception e) {
                                                    Log.w("HandleImageButton", "getAppName: error ", e);
                                                }
                                                if (packageName.length() > 0 && (packageManager = handleImageButton.pm) != null) {
                                                    string = handleImageButton.pm.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(0L))).toString();
                                                    handleImageButton.appName = string;
                                                } else {
                                                    string = "";
                                                }
                                            }
                                            sb.append((CharSequence) string);
                                            sb.append(" ");
                                            sb.append(handleImageButton.getContext().getString(R.string.sec_decor_window_handle));
                                            handleImageButton.setContentDescription(sb.toString());
                                        }
                                    }
                                }
                                if (CoreRune.MW_CAPTION_HELP_POPUP && this.mHandleMenuHelpController != null) {
                                    int windowingMode = runningTaskInfo2.getWindowingMode();
                                    if (windowingMode == 6) {
                                        z10 = HandleMenuHelpController.SPLIT_HANDLER_HELP_POPUP_ENABLED;
                                    } else {
                                        z10 = windowingMode == 5 ? HandleMenuHelpController.FREEFORM_HANDLER_HELP_POPUP_ENABLED : false;
                                    }
                                    if (z10) {
                                        HandleMenuHelpController handleMenuHelpController = this.mHandleMenuHelpController;
                                        int windowingMode2 = this.mTaskInfo.getWindowingMode();
                                        handleMenuHelpController.getClass();
                                        if (windowingMode2 == 6) {
                                            z11 = HandleMenuHelpController.SPLIT_HANDLER_HELP_POPUP_ENABLED;
                                        } else {
                                            z11 = windowingMode2 == 5 ? HandleMenuHelpController.FREEFORM_HANDLER_HELP_POPUP_ENABLED : false;
                                        }
                                        if (z11 && (multiTaskingHandleViewHolderAsMultiTaskingAppHandle2 = asMultiTaskingAppHandle(this.mWindowDecorViewHolder)) != null) {
                                            View view4 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle2.captionView;
                                            if (view4.getVisibility() == 0) {
                                                view4.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration.3
                                                    @Override // android.view.View.OnAttachStateChangeListener
                                                    public final void onViewAttachedToWindow(View view5) {
                                                        DesktopModeWindowDecoration desktopModeWindowDecoration2 = DesktopModeWindowDecoration.this;
                                                        if (desktopModeWindowDecoration2.mWindowDecorViewHolder != null) {
                                                            desktopModeWindowDecoration2.onAssistContentReceived(null);
                                                        }
                                                        view5.removeOnAttachStateChangeListener(this);
                                                    }

                                                    @Override // android.view.View.OnAttachStateChangeListener
                                                    public final void onViewDetachedFromWindow(View view5) {
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (CoreRune.MW_CAPTION_FREEFORM && this.mTaskInfo.isFreeform() && (freeformOutline = this.mFreeformOutline) != null) {
                            SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
                            final SurfaceControl.Transaction transaction4 = new SurfaceControl.Transaction();
                            if (freeformOutline.mFreeformOutline == null) {
                                Rect bounds3 = freeformOutline.mTaskInfo.configuration.windowConfiguration.getBounds();
                                View viewInflate = LayoutInflater.from(freeformOutline.mContext).inflate(R.layout.mw_freeform_outline, (ViewGroup) null);
                                int iWidth2 = bounds3.width();
                                int iHeight2 = bounds3.height();
                                DesktopModeWindowDecoration desktopModeWindowDecoration2 = freeformOutline.mDecoration;
                                SurfaceControl surfaceControlBuild = ((SurfaceControl.Builder) desktopModeWindowDecoration2.mSurfaceControlBuilderSupplier.get()).setName("Freeform Outline of Task=" + desktopModeWindowDecoration2.mTaskInfo.taskId).setContainerLayer().setParent(desktopModeWindowDecoration2.mTaskSurface).build();
                                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(iWidth2, iHeight2, 2038, 24, -2);
                                layoutParams.setTitle("Additional window of Task=" + desktopModeWindowDecoration2.mTaskInfo.taskId + " (Freeform Outline)");
                                layoutParams.setTrustedOverlay();
                                WindowlessWindowManager windowlessWindowManager = new WindowlessWindowManager(desktopModeWindowDecoration2.mTaskInfo.configuration, surfaceControlBuild, (InputTransferToken) null);
                                WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory = desktopModeWindowDecoration2.mSurfaceControlViewHostFactory;
                                Context context3 = desktopModeWindowDecoration2.mDecorWindowContext;
                                Display display2 = desktopModeWindowDecoration2.mDisplay;
                                surfaceControlViewHostFactory.getClass();
                                SurfaceControlViewHost surfaceControlViewHost3 = new SurfaceControlViewHost(context3, display2, windowlessWindowManager, "WindowDecoration");
                                float f = 0;
                                transaction4.setPosition(surfaceControlBuild, f, f).show(surfaceControlBuild);
                                surfaceControlViewHost3.setView(viewInflate, layoutParams);
                                freeformOutline.mFreeformOutline = new AdditionalViewHostViewContainer(surfaceControlBuild, surfaceControlViewHost3, desktopModeWindowDecoration2.mSurfaceControlTransactionSupplier);
                            }
                            transaction4.setLayer(freeformOutline.mFreeformOutline.windowSurface, 29999);
                            syncTransactionQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.windowdecor.FreeformOutline$$ExternalSyntheticLambda0
                                @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                                public final void runWithTransaction(SurfaceControl.Transaction transaction5) {
                                    SurfaceControl.Transaction transaction6 = transaction4;
                                    transaction5.merge(transaction6);
                                    transaction6.close();
                                }
                            });
                            this.mFreeformOutline.relayout(runningTaskInfo, this.mDecorWindowContext, this.mRelayoutParams.mCornerRadius, false, z6);
                            runningTaskInfo2 = runningTaskInfo;
                        }
                    } else {
                        final int i10 = 1;
                        loadTaskNameAndIconInBackground(new BiConsumer(this) { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$$ExternalSyntheticLambda3
                            public final /* synthetic */ DesktopModeWindowDecoration f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) throws Resources.NotFoundException {
                                Paint paint;
                                Bitmap bitmap;
                                Object obj3;
                                Bitmap bitmap2;
                                int i102 = i10;
                                DesktopModeWindowDecoration desktopModeWindowDecoration22 = this.f$0;
                                switch (i102) {
                                    case 0:
                                        CharSequence charSequence = (CharSequence) obj;
                                        Bitmap bitmap3 = (Bitmap) obj2;
                                        MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2 = DesktopModeWindowDecoration.asMultiTaskingAppHeader(desktopModeWindowDecoration22.mWindowDecorViewHolder);
                                        if (multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2 != null) {
                                            boolean z26 = CoreRune.MW_CAPTION_DESKTOP;
                                            if (!z26) {
                                                TextView textView = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.appNameTextView;
                                                if (textView != null) {
                                                    textView.setText(charSequence);
                                                }
                                                View view42 = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.openMenuButton;
                                                if (view42 != null) {
                                                    view42.setContentDescription(multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getString(R.string.desktop_mode_app_header_chip_text, charSequence));
                                                }
                                                multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.closeWindowButton.setContentDescription(multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getString(R.string.close_button_text, charSequence));
                                                ImageButton imageButton2 = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.minimizeWindowButton;
                                                if (imageButton2 != null) {
                                                    imageButton2.setContentDescription(multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getString(R.string.minimize_button_text, charSequence));
                                                }
                                                multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.a11yTextMaximize = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getString(R.string.maximize_button_text, charSequence);
                                                multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.a11yTextRestore = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getString(R.string.restore_button_text, charSequence);
                                                multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.updateMaximizeButtonContentDescription$1();
                                            }
                                            if (!z26) {
                                                ImageView imageView2 = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.appIconImageView;
                                                if (imageView2 != null) {
                                                    imageView2.setImageBitmap(bitmap3);
                                                    break;
                                                }
                                            } else {
                                                int color = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getResources().getColor(R.color.app_icon_stroke_color, null);
                                                int iCeil = (int) Math.ceil(multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.context.getResources().getDimension(R.dimen.mw_desktop_open_menu_icon_stroke));
                                                if (bitmap3 == null || bitmap3.isRecycled()) {
                                                    paint = null;
                                                    Log.e("DesktopModeAppControlsWindowDecorationViewHolder", "Input originalBitmap is null or recycled.");
                                                    bitmap = null;
                                                } else {
                                                    int i11 = iCeil * 2;
                                                    int width = bitmap3.getWidth() + i11;
                                                    int height = bitmap3.getHeight() + i11;
                                                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                                                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                                                    float width2 = (width - bitmap3.getWidth()) / 2.0f;
                                                    float height2 = (height - bitmap3.getHeight()) / 2.0f;
                                                    if (iCeil > 0) {
                                                        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
                                                        Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
                                                        Paint paint2 = new Paint(1);
                                                        paint2.setColor(-1);
                                                        Rect rect6 = new Rect(0, 0, bitmap3.getWidth(), bitmap3.getHeight());
                                                        float f2 = iCeil;
                                                        bitmap2 = bitmapCreateBitmap;
                                                        canvas2.drawBitmap(bitmap3, rect6, new RectF(width2 - f2, height2 - f2, bitmap3.getWidth() + width2 + f2, bitmap3.getHeight() + height2 + f2), paint2);
                                                        Paint paint3 = new Paint(1);
                                                        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                                                        canvas2.drawBitmap(bitmap3, rect6, new RectF(width2, height2, bitmap3.getWidth() + width2, bitmap3.getHeight() + height2), paint3);
                                                        Paint paint4 = new Paint(1);
                                                        paint4.setColor(color);
                                                        canvas.drawBitmap(bitmapCreateBitmap2, 0.0f, 0.0f, paint4);
                                                        bitmapCreateBitmap2.recycle();
                                                        paint = null;
                                                    } else {
                                                        bitmap2 = bitmapCreateBitmap;
                                                        paint = null;
                                                    }
                                                    canvas.drawBitmap(bitmap3, width2, height2, paint);
                                                    bitmap = bitmap2;
                                                }
                                                if (bitmap != null) {
                                                    ImageView imageView22 = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.appIconImageView;
                                                    if (imageView22 != null) {
                                                        imageView22.setImageBitmap(bitmap);
                                                        obj3 = Unit.INSTANCE;
                                                    } else {
                                                        obj3 = paint;
                                                    }
                                                    if (obj3 != null) {
                                                    }
                                                }
                                                Log.e("DesktopModeAppControlsWindowDecorationViewHolder", "Failed to apply effects, displaying original bitmap as fallback.");
                                                ImageView imageView3 = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader2.appIconImageView;
                                                if (imageView3 != null) {
                                                    imageView3.setImageBitmap(bitmap3);
                                                    Unit unit = Unit.INSTANCE;
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    default:
                                        CharSequence charSequence2 = (CharSequence) obj;
                                        Bitmap bitmap4 = (Bitmap) obj2;
                                        AppHeaderViewHolder appHeaderViewHolderAsAppHeader = DesktopModeWindowDecoration.asAppHeader(desktopModeWindowDecoration22.mWindowDecorViewHolder);
                                        if (appHeaderViewHolderAsAppHeader != null) {
                                            appHeaderViewHolderAsAppHeader.appNameTextView.setText(charSequence2);
                                            appHeaderViewHolderAsAppHeader.openMenuButton.setContentDescription(appHeaderViewHolderAsAppHeader.context.getString(R.string.desktop_mode_app_header_chip_text, charSequence2));
                                            appHeaderViewHolderAsAppHeader.closeWindowButton.setContentDescription(appHeaderViewHolderAsAppHeader.context.getString(R.string.close_button_text, charSequence2));
                                            appHeaderViewHolderAsAppHeader.minimizeWindowButton.setContentDescription(appHeaderViewHolderAsAppHeader.context.getString(R.string.minimize_button_text, charSequence2));
                                            appHeaderViewHolderAsAppHeader.a11yTextMaximize = appHeaderViewHolderAsAppHeader.context.getString(R.string.maximize_button_text, charSequence2);
                                            appHeaderViewHolderAsAppHeader.a11yTextRestore = appHeaderViewHolderAsAppHeader.context.getString(R.string.restore_button_text, charSequence2);
                                            appHeaderViewHolderAsAppHeader.updateMaximizeButtonContentDescription();
                                            appHeaderViewHolderAsAppHeader.appIconImageView.setImageBitmap(bitmap4);
                                        }
                                        boolean z27 = ((DesktopStateImpl) desktopModeWindowDecoration22.mDesktopState).canEnterDesktopMode;
                                        break;
                                }
                            }
                        });
                    }
                } else if (isDecorCaptionState$1()) {
                    MultiTaskingHeaderViewHolder.Factory factory = this.mHeaderViewHolderFactory;
                    View view5 = this.mResult.mRootView;
                    DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener = this.mOnCaptionTouchListener;
                    DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener2 = this.mOnCaptionButtonClickListener;
                    DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener3 = this.mOnCaptionLongClickListener;
                    DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener4 = this.mOnCaptionGenericMotionListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 = this.mOnLeftSnapClickListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda82 = this.mOnRightSnapClickListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda83 = this.mOnMaximizeOrRestoreClickListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda84 = this.mOnMaximizeHoverListener;
                    DesktopModeUiEventLogger desktopModeUiEventLogger = this.mDesktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo3 = this.mTaskInfo;
                    DisplayController displayController = this.mDisplayController;
                    Handler handler = this.mHandler;
                    factory.getClass();
                    appHeaderViewHolder = new MultiTaskingHeaderViewHolder(view5, desktopModeTouchEventListener, desktopModeTouchEventListener2, desktopModeTouchEventListener3, desktopModeTouchEventListener4, desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8, desktopModeWindowDecorViewModel$$ExternalSyntheticLambda82, desktopModeWindowDecorViewModel$$ExternalSyntheticLambda83, desktopModeWindowDecorViewModel$$ExternalSyntheticLambda84, desktopModeUiEventLogger, runningTaskInfo3, displayController, handler);
                } else {
                    throw new IllegalArgumentException("Unexpected layout resource id");
                }
            } else {
                int i11 = this.mRelayoutParams.mLayoutResId;
                if (i11 == R.layout.desktop_mode_app_handle) {
                    AppHandleViewHolder.Factory factory2 = this.mAppHandleViewHolderFactory;
                    View view6 = this.mResult.mRootView;
                    DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener5 = this.mOnCaptionTouchListener;
                    DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener6 = this.mOnCaptionButtonClickListener;
                    WindowManagerWrapper windowManagerWrapper = this.mWindowManagerWrapper;
                    Handler handler2 = this.mHandler;
                    DesktopModeUiEventLogger desktopModeUiEventLogger2 = this.mDesktopModeUiEventLogger;
                    factory2.getClass();
                    appHeaderViewHolder = new AppHandleViewHolder(view6, desktopModeTouchEventListener5, desktopModeTouchEventListener6, windowManagerWrapper, handler2, desktopModeUiEventLogger2);
                } else if (i11 == R.layout.desktop_mode_app_header) {
                    AppHeaderViewHolder.Factory factory3 = this.mAppHeaderViewHolderFactory;
                    View view7 = this.mResult.mRootView;
                    DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener7 = this.mOnCaptionTouchListener;
                    DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener8 = this.mOnCaptionButtonClickListener;
                    DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener9 = this.mOnCaptionLongClickListener;
                    DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener10 = this.mOnCaptionGenericMotionListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda85 = this.mOnLeftSnapClickListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda86 = this.mOnRightSnapClickListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda87 = this.mOnMaximizeOrRestoreClickListener;
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda88 = this.mOnMaximizeHoverListener;
                    DesktopModeUiEventLogger desktopModeUiEventLogger3 = this.mDesktopModeUiEventLogger;
                    factory3.getClass();
                    appHeaderViewHolder = new AppHeaderViewHolder(view7, desktopModeTouchEventListener7, desktopModeTouchEventListener8, desktopModeTouchEventListener9, desktopModeTouchEventListener10, desktopModeWindowDecorViewModel$$ExternalSyntheticLambda85, desktopModeWindowDecorViewModel$$ExternalSyntheticLambda86, desktopModeWindowDecorViewModel$$ExternalSyntheticLambda87, desktopModeWindowDecorViewModel$$ExternalSyntheticLambda88, desktopModeUiEventLogger3);
                } else {
                    throw new IllegalArgumentException("Unexpected layout resource id");
                }
            }
            multiTaskingHandleViewHolder = appHeaderViewHolder;
            this.mWindowDecorViewHolder = multiTaskingHandleViewHolder;
            if (!z13) {
            }
        }
        Trace.beginSection("DesktopModeWindowDecoration#relayout-bindData");
        boolean z26 = CoreRune.MW_CAPTION;
        if (z26) {
            if (this.mCaptionType == 0) {
                if (isAppHandle(this.mWindowDecorViewHolder)) {
                    int i12 = this.mResult.mCaptionHeight;
                    boolean z27 = CoreRune.MW_CAPTION_FULL_SCREEN_CUTOUT;
                    if (z27 && this.mIsCutoutLocatedInCenter) {
                        i2 = this.mRelayoutParams.mDisplayTopInset;
                        i3 = this.mCenterCutoutTopInset;
                        if (i2 < i3 + i12) {
                        }
                    } else {
                        if (CoreRune.MW_CAPTION_FULL_SCREEN && this.mTaskInfo.getWindowingMode() == 1) {
                            i2 = this.mRelayoutParams.mDisplayTopInset;
                            i3 = this.mResult.mCaptionY;
                            if (i2 < i3 + i12) {
                                i12 = i2 - i3;
                            }
                        }
                        int i13 = i12;
                        final MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle4 = asMultiTaskingAppHandle(this.mWindowDecorViewHolder);
                        ActivityManager.RunningTaskInfo runningTaskInfo4 = this.mTaskInfo;
                        WindowDecoration.RelayoutResult relayoutResult2 = this.mResult;
                        Point point = new Point(relayoutResult2.mCaptionX, relayoutResult2.mCaptionY);
                        if (this.mTaskInfo.isSplitScreen() && (this.mTaskInfo.configuration.windowConfiguration.getStagePosition() & 32) != 0) {
                            point.x += this.mTaskInfo.getConfiguration().windowConfiguration.getBounds().left;
                        }
                        int i14 = this.mResult.mCaptionWidth;
                        if (supportStatusBarInputLayer()) {
                            if (!DesktopModeFlags.ENABLE_INPUT_LAYER_TRANSITION_FIX.isTrue()) {
                                zIsCaptionVisible = isCaptionVisible();
                            } else {
                                zIsCaptionVisible = isCaptionVisible() && !this.mIsRecentsTransitionRunning;
                            }
                            if (zIsCaptionVisible) {
                                z7 = true;
                            }
                            MultiTaskingHandleViewHolder.HandleData handleData = new MultiTaskingHandleViewHolder.HandleData(runningTaskInfo4, point, i14, i13, z7, isCaptionVisible(), this.mIsStatusBarVisible, this.mIsDesktopModeSupportedOnDisplay);
                            multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.getClass();
                            ActivityManager.RunningTaskInfo runningTaskInfo5 = handleData.taskInfo;
                            final Point point2 = handleData.position;
                            z8 = CoreRune.MW_CAPTION_HANDLE_ANIM;
                            if (!z8) {
                            }
                            ImageButton imageButton2 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.captionHandle;
                            boolean z28 = handleData.isDesktopModeSupportedOnDisplay;
                            imageButton2.setImageTintList(ColorStateList.valueOf(multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.getCaptionHandleColor(runningTaskInfo5, z28)));
                            multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.taskInfo = runningTaskInfo5;
                            boolean z29 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarVisible;
                            boolean z30 = handleData.isStatusBarVisible;
                            if (z29 == z30) {
                            }
                            multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarVisible = z30;
                            if (z8) {
                            }
                            z9 = CoreRune.MW_CAPTION_HANDLE_SYSTEM_INPUT;
                            boolean z31 = handleData.showInputLayer;
                            if (z9) {
                            }
                        } else {
                            z7 = false;
                            MultiTaskingHandleViewHolder.HandleData handleData2 = new MultiTaskingHandleViewHolder.HandleData(runningTaskInfo4, point, i14, i13, z7, isCaptionVisible(), this.mIsStatusBarVisible, this.mIsDesktopModeSupportedOnDisplay);
                            multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.getClass();
                            ActivityManager.RunningTaskInfo runningTaskInfo52 = handleData2.taskInfo;
                            final Point point22 = handleData2.position;
                            z8 = CoreRune.MW_CAPTION_HANDLE_ANIM;
                            if (!z8) {
                                int i15 = handleData2.isCaptionVisible ? 0 : 8;
                                if (multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.captionView.getVisibility() != i15 && DesktopModeFlags.ENABLE_DESKTOP_APP_HANDLE_ANIMATION.isTrue()) {
                                    multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.animator.animateVisibilityChange(i15);
                                }
                            }
                            ImageButton imageButton22 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.captionHandle;
                            boolean z282 = handleData2.isDesktopModeSupportedOnDisplay;
                            imageButton22.setImageTintList(ColorStateList.valueOf(multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.getCaptionHandleColor(runningTaskInfo52, z282)));
                            multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.taskInfo = runningTaskInfo52;
                            boolean z292 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarVisible;
                            boolean z302 = handleData2.isStatusBarVisible;
                            boolean z32 = z292 == z302;
                            multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarVisible = z302;
                            if (z8) {
                                if (CoreRune.MW_CAPTION_HANDLE_KEEP_SCREEN_ON) {
                                    boolean z33 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.isKeepScreenOn;
                                    boolean z34 = runningTaskInfo52.isKeepScreenOn;
                                    if (z33 != z34) {
                                        multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.isKeepScreenOn = z34;
                                    }
                                }
                                HandleHideAnimator handleHideAnimator = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.handleHideAnimator;
                                handleHideAnimator.taskInfo = runningTaskInfo52;
                                handleHideAnimator.mIsStatusBarVisible = z302;
                                if (runningTaskInfo52.getWindowingMode() == 1) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo6 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.taskInfo;
                                    if (runningTaskInfo6 == null) {
                                        runningTaskInfo6 = null;
                                    }
                                    if (!runningTaskInfo6.isFullSizeWindow) {
                                        HandleHideAnimator handleHideAnimator2 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.handleHideAnimator;
                                        if (handleHideAnimator2.mIsHandleViewVisible) {
                                            handleHideAnimator2.cancelAllHandleAnim();
                                            handleHideAnimator2.hide(false);
                                        }
                                    } else if (!z32 && multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarVisible) {
                                        if (multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.isKeepScreenOn) {
                                            multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.handleHideAnimator.setState(1);
                                        } else if (z282) {
                                            multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.handleHideAnimator.setState(2);
                                        } else {
                                            multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.handleHideAnimator.setState(0);
                                        }
                                    } else {
                                        if (!multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarVisible) {
                                            multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.handleHideAnimator.setState(0);
                                        }
                                        HandleHideAnimator handleHideAnimator3 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.handleHideAnimator;
                                        if (multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarVisible) {
                                            handleHideAnimator3.cancelAllHandleAnim();
                                            handleHideAnimator3.show(null, true, false, false);
                                        } else {
                                            handleHideAnimator3.cancelAllHandleAnim();
                                            handleHideAnimator3.hide(false);
                                        }
                                    }
                                } else if (multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.isKeepScreenOn) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo7 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.taskInfo;
                                    if (runningTaskInfo7 == null) {
                                        runningTaskInfo7 = null;
                                    }
                                    if (!runningTaskInfo7.isFreeform()) {
                                        multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.handleHideAnimator.setState(1);
                                    }
                                } else {
                                    multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.handleHideAnimator.setState(0);
                                }
                            }
                            z9 = CoreRune.MW_CAPTION_HANDLE_SYSTEM_INPUT;
                            boolean z312 = handleData2.showInputLayer;
                            if (z9) {
                                if (point22.y >= SystemBarUtils.getStatusBarHeight(multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.context) || !z312) {
                                    multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.disposeStatusBarInputLayer();
                                }
                                if (z27) {
                                    this.mIsCutoutLocatedInCenterChanged = false;
                                    multiTaskingHandleViewHolderAsMultiTaskingAppHandle = asMultiTaskingAppHandle(this.mWindowDecorViewHolder);
                                    if (multiTaskingHandleViewHolderAsMultiTaskingAppHandle != null) {
                                    }
                                }
                                if (!CoreRune.MW_CAPTION_HANDLE_KEEP_SCREEN_ON) {
                                }
                            } else {
                                if (!z312) {
                                    multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.disposeStatusBarInputLayer();
                                } else {
                                    boolean z35 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarInputLayerExists;
                                    final int i16 = handleData2.width;
                                    final int i17 = handleData2.height;
                                    if (z35) {
                                        AdditionalSystemViewContainer additionalSystemViewContainer = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarInputLayer;
                                        boolean z36 = (additionalSystemViewContainer == null || (view3 = additionalSystemViewContainer.view) == null || view3.getVisibility() != 0) ? false : true;
                                        if (z9 && (z32 || z36 != multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarVisible)) {
                                            if (!multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarVisible && !multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.isHandleTouching) {
                                                AdditionalSystemViewContainer additionalSystemViewContainer2 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarInputLayer;
                                                if (additionalSystemViewContainer2 != null && (view2 = additionalSystemViewContainer2.view) != null) {
                                                    view2.setVisibility(8);
                                                }
                                            } else {
                                                AdditionalSystemViewContainer additionalSystemViewContainer3 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarInputLayer;
                                                if (additionalSystemViewContainer3 != null && (view = additionalSystemViewContainer3.view) != null) {
                                                    view.setVisibility(0);
                                                }
                                            }
                                            if (multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.updateHandleInputBounds(point22, i16, i17)) {
                                                multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.handler.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder$bindData$1
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        MultiTaskingHandleViewHolder multiTaskingHandleViewHolder2 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4;
                                                        Point point3 = point22;
                                                        AdditionalSystemViewContainer additionalSystemViewContainer4 = multiTaskingHandleViewHolder2.statusBarInputLayer;
                                                        if (additionalSystemViewContainer4 != null) {
                                                            additionalSystemViewContainer4.setPosition(new SurfaceControl.Transaction(), point3.x, point3.y);
                                                        }
                                                    }
                                                });
                                            }
                                        } else {
                                            multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.handler.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder$bindData$2
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    MultiTaskingHandleViewHolder multiTaskingHandleViewHolder2 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4;
                                                    Point point3 = point22;
                                                    AdditionalSystemViewContainer additionalSystemViewContainer4 = multiTaskingHandleViewHolder2.statusBarInputLayer;
                                                    if (additionalSystemViewContainer4 != null) {
                                                        additionalSystemViewContainer4.setPosition(new SurfaceControl.Transaction(), point3.x, point3.y);
                                                    }
                                                }
                                            });
                                        }
                                    } else {
                                        multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.statusBarInputLayerExists = true;
                                        multiTaskingHandleViewHolderAsMultiTaskingAppHandle4.handler.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder$bindData$3
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                View view8;
                                                View view9;
                                                final MultiTaskingHandleViewHolder multiTaskingHandleViewHolder2 = multiTaskingHandleViewHolderAsMultiTaskingAppHandle4;
                                                Point point3 = point22;
                                                int i18 = i16;
                                                int i19 = i17;
                                                multiTaskingHandleViewHolder2.getClass();
                                                if (DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) {
                                                    boolean z37 = CoreRune.MW_CAPTION_HANDLE_SYSTEM_INPUT;
                                                    Context contextThemeWrapper = z37 ? new ContextThemeWrapper(multiTaskingHandleViewHolder2.context, android.R.style.Theme.DeviceDefault.DayNight) : multiTaskingHandleViewHolder2.context;
                                                    WindowManagerWrapper windowManagerWrapper2 = multiTaskingHandleViewHolder2.windowManagerWrapper;
                                                    ActivityManager.RunningTaskInfo runningTaskInfo8 = multiTaskingHandleViewHolder2.taskInfo;
                                                    if (runningTaskInfo8 == null) {
                                                        runningTaskInfo8 = null;
                                                    }
                                                    int i20 = runningTaskInfo8.taskId;
                                                    int i21 = point3.x;
                                                    int i22 = point3.y;
                                                    boolean z38 = CoreRune.MW_CAPTION_HANDLE;
                                                    AdditionalSystemViewContainer additionalSystemViewContainer4 = new AdditionalSystemViewContainer(contextThemeWrapper, windowManagerWrapper2, i20, i21, i22, i18, i19, 8, z38);
                                                    multiTaskingHandleViewHolder2.statusBarInputLayer = additionalSystemViewContainer4;
                                                    View view10 = additionalSystemViewContainer4.view;
                                                    if (view10 == null) {
                                                        throw new IllegalStateException("Unable to find statusBarInputLayer View");
                                                    }
                                                    WindowManager.LayoutParams layoutParams2 = additionalSystemViewContainer4.lp;
                                                    if (layoutParams2 == null) {
                                                        throw new IllegalStateException("Unable to find statusBarInputLayer LayoutParams");
                                                    }
                                                    ActivityManager.RunningTaskInfo runningTaskInfo9 = multiTaskingHandleViewHolder2.taskInfo;
                                                    if (runningTaskInfo9 == null) {
                                                        runningTaskInfo9 = null;
                                                    }
                                                    layoutParams2.setTitle("Handle Input Layer of task " + runningTaskInfo9.taskId);
                                                    layoutParams2.setTrustedOverlay();
                                                    layoutParams2.inputFeatures = 4;
                                                    if (z38) {
                                                        view10.setTooltipText(multiTaskingHandleViewHolder2.captionHandle.getTooltipText());
                                                    }
                                                    view10.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder$createStatusBarInputLayer$1
                                                        @Override // android.view.View.OnHoverListener
                                                        public final boolean onHover(View view11, MotionEvent motionEvent) {
                                                            return multiTaskingHandleViewHolder2.captionHandle.onHoverEvent(motionEvent);
                                                        }
                                                    });
                                                    view10.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder$createStatusBarInputLayer$2
                                                        @Override // android.view.View.OnTouchListener
                                                        public final boolean onTouch(View view11, MotionEvent motionEvent) {
                                                            if (motionEvent.getActionMasked() == 0) {
                                                                multiTaskingHandleViewHolder2.inputManager.pilferPointers(view11.getViewRootImpl().getInputToken());
                                                            }
                                                            multiTaskingHandleViewHolder2.captionHandle.dispatchTouchEvent(motionEvent);
                                                            return true;
                                                        }
                                                    });
                                                    view10.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder$setupAppHandleA11y$1
                                                        @Override // android.view.View.AccessibilityDelegate
                                                        public final void onInitializeAccessibilityNodeInfo(View view11, AccessibilityNodeInfo accessibilityNodeInfo) {
                                                            super.onInitializeAccessibilityNodeInfo(view11, accessibilityNodeInfo);
                                                            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                                                            view11.setClickable(true);
                                                        }

                                                        @Override // android.view.View.AccessibilityDelegate
                                                        public final void onPopulateAccessibilityEvent(View view11, AccessibilityEvent accessibilityEvent) {
                                                            super.onPopulateAccessibilityEvent(view11, accessibilityEvent);
                                                            if (accessibilityEvent.getEventType() == 32768) {
                                                                accessibilityEvent.getText().add(multiTaskingHandleViewHolder2.captionHandle.getContentDescription());
                                                            }
                                                        }

                                                        @Override // android.view.View.AccessibilityDelegate
                                                        public final boolean performAccessibilityAction(View view11, int i23, Bundle bundle) {
                                                            if (i23 == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                                                                MultiTaskingHandleViewHolder multiTaskingHandleViewHolder3 = multiTaskingHandleViewHolder2;
                                                                DesktopModeUiEventLogger desktopModeUiEventLogger4 = multiTaskingHandleViewHolder3.desktopModeUiEventLogger;
                                                                ActivityManager.RunningTaskInfo runningTaskInfo10 = multiTaskingHandleViewHolder3.taskInfo;
                                                                if (runningTaskInfo10 == null) {
                                                                    runningTaskInfo10 = null;
                                                                }
                                                                desktopModeUiEventLogger4.log(runningTaskInfo10, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_APP_HANDLE_MENU_OPENED);
                                                                multiTaskingHandleViewHolder2.captionHandle.performClick();
                                                            }
                                                            return super.performAccessibilityAction(view11, i23, bundle);
                                                        }
                                                    });
                                                    ViewCompat.replaceAccessibilityAction(view10, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, multiTaskingHandleViewHolder2.context.getString(R.string.app_handle_chip_accessibility_announce), null);
                                                    multiTaskingHandleViewHolder2.windowManagerWrapper.windowManager.updateViewLayout(view10, layoutParams2);
                                                    if (z37) {
                                                        if (multiTaskingHandleViewHolder2.statusBarVisible) {
                                                            AdditionalSystemViewContainer additionalSystemViewContainer5 = multiTaskingHandleViewHolder2.statusBarInputLayer;
                                                            if (additionalSystemViewContainer5 != null && (view9 = additionalSystemViewContainer5.view) != null) {
                                                                view9.setVisibility(0);
                                                            }
                                                        } else {
                                                            AdditionalSystemViewContainer additionalSystemViewContainer6 = multiTaskingHandleViewHolder2.statusBarInputLayer;
                                                            if (additionalSystemViewContainer6 != null && (view8 = additionalSystemViewContainer6.view) != null) {
                                                                view8.setVisibility(8);
                                                            }
                                                        }
                                                        multiTaskingHandleViewHolder2.updateHandleInputBounds(point3, i18, i19);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                                if (z27 && this.mIsCutoutLocatedInCenterChanged) {
                                    this.mIsCutoutLocatedInCenterChanged = false;
                                    multiTaskingHandleViewHolderAsMultiTaskingAppHandle = asMultiTaskingAppHandle(this.mWindowDecorViewHolder);
                                    if (multiTaskingHandleViewHolderAsMultiTaskingAppHandle != null) {
                                        multiTaskingHandleViewHolderAsMultiTaskingAppHandle.setupHandleVerticalPadding(this.mTaskInfo, this.mSplitScreenController.isParallelMultiSplit(), this.mDesktopState, this.mIsCutoutLocatedInCenter);
                                    }
                                }
                                if (!CoreRune.MW_CAPTION_HANDLE_KEEP_SCREEN_ON) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo8 = this.mTaskInfo;
                                    if (runningTaskInfo8.isKeepScreenOn && runningTaskInfo8.isVisible && !runningTaskInfo8.isFreeform()) {
                                        if (this.mInputEventReceiver == null && this.mTaskInfo.displayId == 0) {
                                            InputMonitor inputMonitorMonitorGestureInput = InputManager.getInstance().monitorGestureInput("handle-Show", this.mTaskInfo.getDisplayId());
                                            desktopModeWindowDecoration = this;
                                            desktopModeWindowDecoration.mInputEventReceiver = desktopModeWindowDecoration.new DecorationInputEventReceiver(inputMonitorMonitorGestureInput, inputMonitorMonitorGestureInput.getInputChannel(), Looper.myLooper(), true);
                                        }
                                    } else {
                                        desktopModeWindowDecoration = this;
                                        DecorationInputEventReceiver decorationInputEventReceiver2 = desktopModeWindowDecoration.mInputEventReceiver;
                                        if (decorationInputEventReceiver2 != null) {
                                            decorationInputEventReceiver2.dispose();
                                            desktopModeWindowDecoration.mInputEventReceiver = null;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    desktopModeWindowDecoration = this;
                }
            } else {
                desktopModeWindowDecoration = this;
                if (desktopModeWindowDecoration.isDecorCaptionState$1()) {
                    desktopModeWindowDecoration.updateMultiTaskingHeaderViewHolder(z6, runningTaskInfo2.isFocused);
                }
            }
        } else {
            desktopModeWindowDecoration = this;
            if (isAppHandle(desktopModeWindowDecoration.mWindowDecorViewHolder)) {
                desktopModeWindowDecoration.updateAppHandleViewHolder();
            } else if (isAppHeader(desktopModeWindowDecoration.mWindowDecorViewHolder)) {
                AppHeaderViewHolder appHeaderViewHolderAsAppHeader = asAppHeader(desktopModeWindowDecoration.mWindowDecorViewHolder);
                ActivityManager.RunningTaskInfo runningTaskInfo9 = desktopModeWindowDecoration.mTaskInfo;
                appHeaderViewHolderAsAppHeader.bindData(new AppHeaderViewHolder.HeaderData(runningTaskInfo9, DesktopModeUtils.isTaskMaximized(runningTaskInfo9, desktopModeWindowDecoration.mDisplayController), z6, z3, desktopModeWindowDecoration.canOpenMaximizeMenu(false), desktopModeWindowDecoration.isCaptionVisible()));
            }
        }
        Trace.endSection();
        boolean z37 = CoreRune.MW_CAPTION_FREEFORM_STASH;
        if (z37 && desktopModeWindowDecoration.mFreeformStashState.isStashed() && (surfaceControl != desktopModeWindowDecoration.mDecorationContainerSurface || desktopModeWindowDecoration.mFreeformStashDimInputListener == null)) {
            desktopModeWindowDecoration.closeFreeformDimInputListener();
            if (desktopModeWindowDecoration.mDecorationContainerSurface != null) {
                desktopModeWindowDecoration.mFreeformStashDimInputListener = new FreeformDimInputListener(desktopModeWindowDecoration.mContext, desktopModeWindowDecoration.mHandler, desktopModeWindowDecoration.mChoreographer, desktopModeWindowDecoration.mDisplay.getDisplayId(), desktopModeWindowDecoration.mDecorationContainerSurface, desktopModeWindowDecoration.mTaskPositioner, runningTaskInfo2.taskId, null);
                desktopModeWindowDecoration.mFreeformStashState.createStashDimOverlay(desktopModeWindowDecoration.mTaskSurface, desktopModeWindowDecoration.mContext, desktopModeWindowDecoration.mTaskInfo);
                desktopModeWindowDecoration.mFreeformStashState.setDimOverlayAlpha(MultiWindowUtils.isNightMode(desktopModeWindowDecoration.mTaskInfo) ? 0.4f : 0.2f);
            } else {
                Log.w("DesktopModeWindowDecoration", "mDragResizeInputSurface has already been removed.");
            }
        }
        if (z37 && (freeformDimInputListener = desktopModeWindowDecoration.mFreeformStashDimInputListener) != null) {
            freeformDimInputListener.updateTouchableState(!runningTaskInfo2.isForceHidden);
        }
        if (!z26 && !z3) {
            desktopModeWindowDecoration.closeHandleMenu();
            desktopModeWindowDecoration.closeManageWindowsMenu();
            desktopModeWindowDecoration.closeMaximizeMenu();
            boolean z38 = ((DesktopStateImpl) desktopModeWindowDecoration.mDesktopState).canEnterDesktopMode;
        }
        desktopModeWindowDecoration.updateDragResizeListenerIfNeeded(surfaceControl, z6);
        desktopModeWindowDecoration.updateMaximizeMenu(transaction, z6);
        Trace.endSection();
    }
}
