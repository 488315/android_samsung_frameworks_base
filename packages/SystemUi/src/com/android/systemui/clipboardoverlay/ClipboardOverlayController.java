package com.android.systemui.clipboardoverlay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Looper;
import android.util.MathUtils;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.LinearLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.screenshot.DraggableConstraintLayout;
import com.android.systemui.screenshot.FloatingWindowUtil;
import com.android.systemui.screenshot.TimeoutHandler;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ClipboardOverlayController {
    public final Executor mBgExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final ClipboardLogger mClipboardLogger;
    public final ClipboardOverlayUtils mClipboardUtils;
    public C11482 mCloseDialogsReceiver;
    public final Context mContext;
    public Animator mEnterAnimator;
    public Animator mExitAnimator;
    public final FeatureFlags mFeatureFlags;
    public C11515 mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public boolean mIsMinimized;
    public ClipboardOverlayController$$ExternalSyntheticLambda2 mOnPreviewTapped;
    public ClipboardOverlayController$$ExternalSyntheticLambda2 mOnRemoteCopyTapped;
    public C11493 mScreenshotReceiver;
    public final TimeoutHandler mTimeoutHandler;
    public final ClipboardOverlayView mView;
    public final ClipboardOverlayWindow mWindow;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.clipboardoverlay.ClipboardOverlayController$1 */
    public final class C11471 implements DraggableConstraintLayout.SwipeDismissCallbacks {
        public C11471() {
        }

        @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
        public final void onDismissComplete() {
            ClipboardOverlayController.this.hideImmediate();
        }

        @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
        public final void onInteraction() {
            ClipboardOverlayController.this.getClass();
        }

        @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
        public final void onSwipeDismissInitiated(Animator animator) {
            ClipboardOverlayController clipboardOverlayController = ClipboardOverlayController.this;
            clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_SWIPE_DISMISSED);
            clipboardOverlayController.mExitAnimator = animator;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.clipboardoverlay.ClipboardOverlayController$8 */
    public abstract /* synthetic */ class AbstractC11538 {

        /* renamed from: $SwitchMap$com$android$systemui$clipboardoverlay$ClipboardModel$Type */
        public static final /* synthetic */ int[] f238xc272202a;

        static {
            int[] iArr = new int[ClipboardModel$Type.values().length];
            f238xc272202a = iArr;
            try {
                iArr[ClipboardModel$Type.TEXT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f238xc272202a[ClipboardModel$Type.IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f238xc272202a[ClipboardModel$Type.URI.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f238xc272202a[ClipboardModel$Type.OTHER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ClipboardLogger {
        public boolean mGuarded = false;
        public final UiEventLogger mUiEventLogger;

        public ClipboardLogger(UiEventLogger uiEventLogger) {
            this.mUiEventLogger = uiEventLogger;
        }

        public final void logSessionComplete(ClipboardOverlayEvent clipboardOverlayEvent) {
            if (this.mGuarded) {
                return;
            }
            this.mGuarded = true;
            this.mUiEventLogger.log(clipboardOverlayEvent, 0, (String) null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v10, types: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$5] */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.content.BroadcastReceiver, com.android.systemui.clipboardoverlay.ClipboardOverlayController$2] */
    /* JADX WARN: Type inference failed for: r3v3, types: [android.content.BroadcastReceiver, com.android.systemui.clipboardoverlay.ClipboardOverlayController$3] */
    public ClipboardOverlayController(Context context, ClipboardOverlayView clipboardOverlayView, final ClipboardOverlayWindow clipboardOverlayWindow, BroadcastDispatcher broadcastDispatcher, BroadcastSender broadcastSender, TimeoutHandler timeoutHandler, FeatureFlags featureFlags, ClipboardOverlayUtils clipboardOverlayUtils, Executor executor, ClipboardImageLoader clipboardImageLoader, UiEventLogger uiEventLogger) {
        C11471 c11471 = new C11471();
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mClipboardLogger = new ClipboardLogger(uiEventLogger);
        this.mView = clipboardOverlayView;
        this.mWindow = clipboardOverlayWindow;
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ClipboardOverlayController.this.onInsetsChanged((WindowInsets) obj, ((Integer) obj2).intValue());
            }
        };
        final int i = 0;
        Runnable runnable = new Runnable(this) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda1
            public final /* synthetic */ ClipboardOverlayController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        ClipboardOverlayController clipboardOverlayController = this.f$0;
                        clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                        clipboardOverlayController.hideImmediate();
                        break;
                    case 1:
                        ClipboardOverlayController clipboardOverlayController2 = this.f$0;
                        ClipboardOverlayWindow clipboardOverlayWindow2 = clipboardOverlayController2.mWindow;
                        ClipboardOverlayView clipboardOverlayView2 = clipboardOverlayController2.mView;
                        clipboardOverlayWindow2.setContentView(clipboardOverlayView2);
                        clipboardOverlayView2.setInsets(clipboardOverlayWindow2.mWindowManager.getCurrentWindowMetrics().getWindowInsets(), clipboardOverlayController2.mContext.getResources().getConfiguration().orientation);
                        break;
                    case 2:
                        ClipboardOverlayController clipboardOverlayController3 = this.f$0;
                        clipboardOverlayController3.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_TIMED_OUT);
                        clipboardOverlayController3.animateOut();
                        break;
                    default:
                        ClipboardOverlayController clipboardOverlayController4 = this.f$0;
                        clipboardOverlayController4.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_ACTION_TAPPED);
                        clipboardOverlayController4.animateOut();
                        break;
                }
            }
        };
        clipboardOverlayWindow.mOnKeyboardChangeListener = biConsumer;
        clipboardOverlayWindow.mOnOrientationChangeListener = runnable;
        View decorView = clipboardOverlayWindow.getDecorView();
        if (!decorView.isAttachedToWindow()) {
            clipboardOverlayWindow.mWindowManager.addView(decorView, clipboardOverlayWindow.mWindowLayoutParams);
            decorView.requestApplyInsets();
        }
        Runnable runnable2 = new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final ClipboardOverlayWindow clipboardOverlayWindow2 = ClipboardOverlayWindow.this;
                clipboardOverlayWindow2.mKeyboardVisible = clipboardOverlayWindow2.mWindowManager.getCurrentWindowMetrics().getWindowInsets().isVisible(WindowInsets.Type.ime());
                clipboardOverlayWindow2.peekDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayWindow$$ExternalSyntheticLambda1
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        ClipboardOverlayWindow clipboardOverlayWindow3 = ClipboardOverlayWindow.this;
                        WindowInsets windowInsets = clipboardOverlayWindow3.mWindowManager.getCurrentWindowMetrics().getWindowInsets();
                        boolean isVisible = windowInsets.isVisible(WindowInsets.Type.ime());
                        if (isVisible != clipboardOverlayWindow3.mKeyboardVisible) {
                            clipboardOverlayWindow3.mKeyboardVisible = isVisible;
                            clipboardOverlayWindow3.mOnKeyboardChangeListener.accept(windowInsets, Integer.valueOf(clipboardOverlayWindow3.mOrientation));
                        }
                    }
                });
                clipboardOverlayWindow2.peekDecorView().getViewRootImpl().setActivityConfigCallback(clipboardOverlayWindow2);
            }
        };
        View decorView2 = clipboardOverlayWindow.getDecorView();
        if (decorView2.isAttachedToWindow()) {
            runnable2.run();
        } else {
            decorView2.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener(clipboardOverlayWindow, decorView2, runnable2) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayWindow.1
                public final /* synthetic */ Runnable val$action;
                public final /* synthetic */ View val$decorView;

                public ViewTreeObserverOnWindowAttachListenerC11551(final ClipboardOverlayWindow clipboardOverlayWindow2, View decorView22, Runnable runnable22) {
                    this.val$decorView = decorView22;
                    this.val$action = runnable22;
                }

                @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                public final void onWindowAttached() {
                    this.val$decorView.getViewTreeObserver().removeOnWindowAttachListener(this);
                    this.val$action.run();
                }

                @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                public final void onWindowDetached() {
                }
            });
        }
        this.mFeatureFlags = featureFlags;
        this.mTimeoutHandler = timeoutHandler;
        timeoutHandler.mDefaultTimeout = PluginEdgeLightingPlus.VERSION;
        this.mClipboardUtils = clipboardOverlayUtils;
        this.mBgExecutor = executor;
        clipboardOverlayView.setCallbacks(c11471);
        final int i2 = 1;
        Runnable runnable3 = new Runnable(this) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda1
            public final /* synthetic */ ClipboardOverlayController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        ClipboardOverlayController clipboardOverlayController = this.f$0;
                        clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                        clipboardOverlayController.hideImmediate();
                        break;
                    case 1:
                        ClipboardOverlayController clipboardOverlayController2 = this.f$0;
                        ClipboardOverlayWindow clipboardOverlayWindow2 = clipboardOverlayController2.mWindow;
                        ClipboardOverlayView clipboardOverlayView2 = clipboardOverlayController2.mView;
                        clipboardOverlayWindow2.setContentView(clipboardOverlayView2);
                        clipboardOverlayView2.setInsets(clipboardOverlayWindow2.mWindowManager.getCurrentWindowMetrics().getWindowInsets(), clipboardOverlayController2.mContext.getResources().getConfiguration().orientation);
                        break;
                    case 2:
                        ClipboardOverlayController clipboardOverlayController3 = this.f$0;
                        clipboardOverlayController3.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_TIMED_OUT);
                        clipboardOverlayController3.animateOut();
                        break;
                    default:
                        ClipboardOverlayController clipboardOverlayController4 = this.f$0;
                        clipboardOverlayController4.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_ACTION_TAPPED);
                        clipboardOverlayController4.animateOut();
                        break;
                }
            }
        };
        View decorView3 = clipboardOverlayWindow2.getDecorView();
        if (decorView3.isAttachedToWindow()) {
            runnable3.run();
        } else {
            decorView3.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener(clipboardOverlayWindow2, decorView3, runnable3) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayWindow.1
                public final /* synthetic */ Runnable val$action;
                public final /* synthetic */ View val$decorView;

                public ViewTreeObserverOnWindowAttachListenerC11551(final ClipboardOverlayWindow clipboardOverlayWindow2, View decorView32, Runnable runnable32) {
                    this.val$decorView = decorView32;
                    this.val$action = runnable32;
                }

                @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                public final void onWindowAttached() {
                    this.val$decorView.getViewTreeObserver().removeOnWindowAttachListener(this);
                    this.val$action.run();
                }

                @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                public final void onWindowDetached() {
                }
            });
        }
        final int i3 = 2;
        timeoutHandler.mOnTimeout = new Runnable(this) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda1
            public final /* synthetic */ ClipboardOverlayController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i3) {
                    case 0:
                        ClipboardOverlayController clipboardOverlayController = this.f$0;
                        clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                        clipboardOverlayController.hideImmediate();
                        break;
                    case 1:
                        ClipboardOverlayController clipboardOverlayController2 = this.f$0;
                        ClipboardOverlayWindow clipboardOverlayWindow2 = clipboardOverlayController2.mWindow;
                        ClipboardOverlayView clipboardOverlayView2 = clipboardOverlayController2.mView;
                        clipboardOverlayWindow2.setContentView(clipboardOverlayView2);
                        clipboardOverlayView2.setInsets(clipboardOverlayWindow2.mWindowManager.getCurrentWindowMetrics().getWindowInsets(), clipboardOverlayController2.mContext.getResources().getConfiguration().orientation);
                        break;
                    case 2:
                        ClipboardOverlayController clipboardOverlayController3 = this.f$0;
                        clipboardOverlayController3.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_TIMED_OUT);
                        clipboardOverlayController3.animateOut();
                        break;
                    default:
                        ClipboardOverlayController clipboardOverlayController4 = this.f$0;
                        clipboardOverlayController4.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_ACTION_TAPPED);
                        clipboardOverlayController4.animateOut();
                        break;
                }
            }
        };
        ?? r2 = new BroadcastReceiver() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                    ClipboardOverlayController.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                    ClipboardOverlayController.this.animateOut();
                }
            }
        };
        this.mCloseDialogsReceiver = r2;
        broadcastDispatcher.registerReceiver(new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), r2);
        ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.android.systemui.SCREENSHOT".equals(intent.getAction())) {
                    ClipboardOverlayController.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                    ClipboardOverlayController.this.animateOut();
                }
            }
        };
        this.mScreenshotReceiver = r3;
        broadcastDispatcher.registerReceiver(r3, new IntentFilter("com.android.systemui.SCREENSHOT"), null, null, 2, "com.android.systemui.permission.SELF");
        this.mInputMonitor = ((InputManager) context.getSystemService(InputManager.class)).monitorGestureInput("clipboard overlay", 0);
        this.mInputEventReceiver = new InputEventReceiver(this.mInputMonitor.getInputChannel(), Looper.getMainLooper()) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.5
            public final void onInputEvent(InputEvent inputEvent) {
                FeatureFlags featureFlags2 = ClipboardOverlayController.this.mFeatureFlags;
                Flags flags = Flags.INSTANCE;
                featureFlags2.getClass();
                if (inputEvent instanceof MotionEvent) {
                    MotionEvent motionEvent = (MotionEvent) inputEvent;
                    if (motionEvent.getActionMasked() == 0) {
                        ClipboardOverlayView clipboardOverlayView2 = ClipboardOverlayController.this.mView;
                        int rawX = (int) motionEvent.getRawX();
                        int rawY = (int) motionEvent.getRawY();
                        clipboardOverlayView2.getClass();
                        Region region = new Region();
                        Rect rect = new Rect();
                        clipboardOverlayView2.mPreviewBorder.getBoundsOnScreen(rect);
                        rect.inset((int) FloatingWindowUtil.dpToPx(clipboardOverlayView2.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(clipboardOverlayView2.mDisplayMetrics, -12.0f));
                        region.op(rect, Region.Op.UNION);
                        clipboardOverlayView2.mActionContainerBackground.getBoundsOnScreen(rect);
                        rect.inset((int) FloatingWindowUtil.dpToPx(clipboardOverlayView2.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(clipboardOverlayView2.mDisplayMetrics, -12.0f));
                        region.op(rect, Region.Op.UNION);
                        clipboardOverlayView2.mMinimizedPreview.getBoundsOnScreen(rect);
                        rect.inset((int) FloatingWindowUtil.dpToPx(clipboardOverlayView2.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(clipboardOverlayView2.mDisplayMetrics, -12.0f));
                        region.op(rect, Region.Op.UNION);
                        clipboardOverlayView2.mDismissButton.getBoundsOnScreen(rect);
                        region.op(rect, Region.Op.UNION);
                        if (!region.contains(rawX, rawY)) {
                            ClipboardOverlayController.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_TAP_OUTSIDE);
                            ClipboardOverlayController.this.animateOut();
                        }
                    }
                }
                finishInputEvent(inputEvent, true);
            }
        };
        Intent intent = new Intent("com.android.systemui.COPY");
        intent.setPackage(context.getPackageName());
        broadcastSender.sendBroadcast$1(intent);
    }

    public final void animateOut() {
        Animator animator = this.mExitAnimator;
        if (animator == null || !animator.isRunning()) {
            final ClipboardOverlayView clipboardOverlayView = this.mView;
            clipboardOverlayView.getClass();
            LinearInterpolator linearInterpolator = new LinearInterpolator();
            PathInterpolator pathInterpolator = new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
            AnimatorSet animatorSet = new AnimatorSet();
            final int i = 2;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setInterpolator(linearInterpolator);
            ofFloat.setDuration(100L);
            final int i2 = 0;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    switch (i2) {
                        case 0:
                            ClipboardOverlayView clipboardOverlayView2 = clipboardOverlayView;
                            int i3 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView2.getClass();
                            clipboardOverlayView2.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
                            break;
                        case 1:
                            ClipboardOverlayView clipboardOverlayView3 = clipboardOverlayView;
                            int i4 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView3.getClass();
                            float lerp = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView3.mMinimizedPreview.setScaleX(lerp);
                            clipboardOverlayView3.mMinimizedPreview.setScaleY(lerp);
                            clipboardOverlayView3.mClipboardPreview.setScaleX(lerp);
                            clipboardOverlayView3.mClipboardPreview.setScaleY(lerp);
                            clipboardOverlayView3.mPreviewBorder.setScaleX(lerp);
                            clipboardOverlayView3.mPreviewBorder.setScaleY(lerp);
                            float x = clipboardOverlayView3.mClipboardPreview.getX() + (clipboardOverlayView3.mClipboardPreview.getWidth() / 2.0f);
                            View view = clipboardOverlayView3.mActionContainerBackground;
                            view.setPivotX(x - view.getX());
                            LinearLayout linearLayout = clipboardOverlayView3.mActionContainer;
                            linearLayout.setPivotX(x - ((View) linearLayout.getParent()).getX());
                            float lerp2 = MathUtils.lerp(1.0f, 0.8f, valueAnimator.getAnimatedFraction());
                            float lerp3 = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView3.mActionContainer.setScaleX(lerp2);
                            clipboardOverlayView3.mActionContainer.setScaleY(lerp3);
                            clipboardOverlayView3.mActionContainerBackground.setScaleX(lerp2);
                            clipboardOverlayView3.mActionContainerBackground.setScaleY(lerp3);
                            break;
                        case 2:
                            ClipboardOverlayView clipboardOverlayView4 = clipboardOverlayView;
                            int i5 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView4.getClass();
                            float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
                            clipboardOverlayView4.mMinimizedPreview.setAlpha(animatedFraction);
                            clipboardOverlayView4.mClipboardPreview.setAlpha(animatedFraction);
                            clipboardOverlayView4.mPreviewBorder.setAlpha(animatedFraction);
                            clipboardOverlayView4.mDismissButton.setAlpha(animatedFraction);
                            clipboardOverlayView4.mActionContainer.setAlpha(animatedFraction);
                            break;
                        case 3:
                            ClipboardOverlayView clipboardOverlayView5 = clipboardOverlayView;
                            int i6 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView5.getClass();
                            clipboardOverlayView5.setAlpha(valueAnimator.getAnimatedFraction());
                            break;
                        case 4:
                            ClipboardOverlayView clipboardOverlayView6 = clipboardOverlayView;
                            int i7 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView6.getClass();
                            float lerp4 = MathUtils.lerp(0.9f, 1.0f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView6.mMinimizedPreview.setScaleX(lerp4);
                            clipboardOverlayView6.mMinimizedPreview.setScaleY(lerp4);
                            clipboardOverlayView6.mClipboardPreview.setScaleX(lerp4);
                            clipboardOverlayView6.mClipboardPreview.setScaleY(lerp4);
                            clipboardOverlayView6.mPreviewBorder.setScaleX(lerp4);
                            clipboardOverlayView6.mPreviewBorder.setScaleY(lerp4);
                            float x2 = clipboardOverlayView6.mClipboardPreview.getX() + (clipboardOverlayView6.mClipboardPreview.getWidth() / 2.0f);
                            View view2 = clipboardOverlayView6.mActionContainerBackground;
                            view2.setPivotX(x2 - view2.getX());
                            LinearLayout linearLayout2 = clipboardOverlayView6.mActionContainer;
                            linearLayout2.setPivotX(x2 - ((View) linearLayout2.getParent()).getX());
                            float lerp5 = MathUtils.lerp(0.7f, 1.0f, valueAnimator.getAnimatedFraction());
                            float lerp6 = MathUtils.lerp(0.9f, 1.0f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView6.mActionContainer.setScaleX(lerp5);
                            clipboardOverlayView6.mActionContainer.setScaleY(lerp6);
                            clipboardOverlayView6.mActionContainerBackground.setScaleX(lerp5);
                            clipboardOverlayView6.mActionContainerBackground.setScaleY(lerp6);
                            break;
                        default:
                            ClipboardOverlayView clipboardOverlayView7 = clipboardOverlayView;
                            int i8 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView7.getClass();
                            float animatedFraction2 = valueAnimator.getAnimatedFraction();
                            clipboardOverlayView7.mMinimizedPreview.setAlpha(animatedFraction2);
                            clipboardOverlayView7.mClipboardPreview.setAlpha(animatedFraction2);
                            clipboardOverlayView7.mPreviewBorder.setAlpha(animatedFraction2);
                            clipboardOverlayView7.mDismissButton.setAlpha(animatedFraction2);
                            clipboardOverlayView7.mActionContainer.setAlpha(animatedFraction2);
                            break;
                    }
                }
            });
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat2.setInterpolator(pathInterpolator);
            ofFloat2.setDuration(250L);
            final int i3 = 1;
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    switch (i3) {
                        case 0:
                            ClipboardOverlayView clipboardOverlayView2 = clipboardOverlayView;
                            int i32 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView2.getClass();
                            clipboardOverlayView2.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
                            break;
                        case 1:
                            ClipboardOverlayView clipboardOverlayView3 = clipboardOverlayView;
                            int i4 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView3.getClass();
                            float lerp = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView3.mMinimizedPreview.setScaleX(lerp);
                            clipboardOverlayView3.mMinimizedPreview.setScaleY(lerp);
                            clipboardOverlayView3.mClipboardPreview.setScaleX(lerp);
                            clipboardOverlayView3.mClipboardPreview.setScaleY(lerp);
                            clipboardOverlayView3.mPreviewBorder.setScaleX(lerp);
                            clipboardOverlayView3.mPreviewBorder.setScaleY(lerp);
                            float x = clipboardOverlayView3.mClipboardPreview.getX() + (clipboardOverlayView3.mClipboardPreview.getWidth() / 2.0f);
                            View view = clipboardOverlayView3.mActionContainerBackground;
                            view.setPivotX(x - view.getX());
                            LinearLayout linearLayout = clipboardOverlayView3.mActionContainer;
                            linearLayout.setPivotX(x - ((View) linearLayout.getParent()).getX());
                            float lerp2 = MathUtils.lerp(1.0f, 0.8f, valueAnimator.getAnimatedFraction());
                            float lerp3 = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView3.mActionContainer.setScaleX(lerp2);
                            clipboardOverlayView3.mActionContainer.setScaleY(lerp3);
                            clipboardOverlayView3.mActionContainerBackground.setScaleX(lerp2);
                            clipboardOverlayView3.mActionContainerBackground.setScaleY(lerp3);
                            break;
                        case 2:
                            ClipboardOverlayView clipboardOverlayView4 = clipboardOverlayView;
                            int i5 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView4.getClass();
                            float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
                            clipboardOverlayView4.mMinimizedPreview.setAlpha(animatedFraction);
                            clipboardOverlayView4.mClipboardPreview.setAlpha(animatedFraction);
                            clipboardOverlayView4.mPreviewBorder.setAlpha(animatedFraction);
                            clipboardOverlayView4.mDismissButton.setAlpha(animatedFraction);
                            clipboardOverlayView4.mActionContainer.setAlpha(animatedFraction);
                            break;
                        case 3:
                            ClipboardOverlayView clipboardOverlayView5 = clipboardOverlayView;
                            int i6 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView5.getClass();
                            clipboardOverlayView5.setAlpha(valueAnimator.getAnimatedFraction());
                            break;
                        case 4:
                            ClipboardOverlayView clipboardOverlayView6 = clipboardOverlayView;
                            int i7 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView6.getClass();
                            float lerp4 = MathUtils.lerp(0.9f, 1.0f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView6.mMinimizedPreview.setScaleX(lerp4);
                            clipboardOverlayView6.mMinimizedPreview.setScaleY(lerp4);
                            clipboardOverlayView6.mClipboardPreview.setScaleX(lerp4);
                            clipboardOverlayView6.mClipboardPreview.setScaleY(lerp4);
                            clipboardOverlayView6.mPreviewBorder.setScaleX(lerp4);
                            clipboardOverlayView6.mPreviewBorder.setScaleY(lerp4);
                            float x2 = clipboardOverlayView6.mClipboardPreview.getX() + (clipboardOverlayView6.mClipboardPreview.getWidth() / 2.0f);
                            View view2 = clipboardOverlayView6.mActionContainerBackground;
                            view2.setPivotX(x2 - view2.getX());
                            LinearLayout linearLayout2 = clipboardOverlayView6.mActionContainer;
                            linearLayout2.setPivotX(x2 - ((View) linearLayout2.getParent()).getX());
                            float lerp5 = MathUtils.lerp(0.7f, 1.0f, valueAnimator.getAnimatedFraction());
                            float lerp6 = MathUtils.lerp(0.9f, 1.0f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView6.mActionContainer.setScaleX(lerp5);
                            clipboardOverlayView6.mActionContainer.setScaleY(lerp6);
                            clipboardOverlayView6.mActionContainerBackground.setScaleX(lerp5);
                            clipboardOverlayView6.mActionContainerBackground.setScaleY(lerp6);
                            break;
                        default:
                            ClipboardOverlayView clipboardOverlayView7 = clipboardOverlayView;
                            int i8 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView7.getClass();
                            float animatedFraction2 = valueAnimator.getAnimatedFraction();
                            clipboardOverlayView7.mMinimizedPreview.setAlpha(animatedFraction2);
                            clipboardOverlayView7.mClipboardPreview.setAlpha(animatedFraction2);
                            clipboardOverlayView7.mPreviewBorder.setAlpha(animatedFraction2);
                            clipboardOverlayView7.mDismissButton.setAlpha(animatedFraction2);
                            clipboardOverlayView7.mActionContainer.setAlpha(animatedFraction2);
                            break;
                    }
                }
            });
            ValueAnimator ofFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat3.setInterpolator(linearInterpolator);
            ofFloat3.setDuration(166L);
            ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    switch (i) {
                        case 0:
                            ClipboardOverlayView clipboardOverlayView2 = clipboardOverlayView;
                            int i32 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView2.getClass();
                            clipboardOverlayView2.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
                            break;
                        case 1:
                            ClipboardOverlayView clipboardOverlayView3 = clipboardOverlayView;
                            int i4 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView3.getClass();
                            float lerp = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView3.mMinimizedPreview.setScaleX(lerp);
                            clipboardOverlayView3.mMinimizedPreview.setScaleY(lerp);
                            clipboardOverlayView3.mClipboardPreview.setScaleX(lerp);
                            clipboardOverlayView3.mClipboardPreview.setScaleY(lerp);
                            clipboardOverlayView3.mPreviewBorder.setScaleX(lerp);
                            clipboardOverlayView3.mPreviewBorder.setScaleY(lerp);
                            float x = clipboardOverlayView3.mClipboardPreview.getX() + (clipboardOverlayView3.mClipboardPreview.getWidth() / 2.0f);
                            View view = clipboardOverlayView3.mActionContainerBackground;
                            view.setPivotX(x - view.getX());
                            LinearLayout linearLayout = clipboardOverlayView3.mActionContainer;
                            linearLayout.setPivotX(x - ((View) linearLayout.getParent()).getX());
                            float lerp2 = MathUtils.lerp(1.0f, 0.8f, valueAnimator.getAnimatedFraction());
                            float lerp3 = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView3.mActionContainer.setScaleX(lerp2);
                            clipboardOverlayView3.mActionContainer.setScaleY(lerp3);
                            clipboardOverlayView3.mActionContainerBackground.setScaleX(lerp2);
                            clipboardOverlayView3.mActionContainerBackground.setScaleY(lerp3);
                            break;
                        case 2:
                            ClipboardOverlayView clipboardOverlayView4 = clipboardOverlayView;
                            int i5 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView4.getClass();
                            float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
                            clipboardOverlayView4.mMinimizedPreview.setAlpha(animatedFraction);
                            clipboardOverlayView4.mClipboardPreview.setAlpha(animatedFraction);
                            clipboardOverlayView4.mPreviewBorder.setAlpha(animatedFraction);
                            clipboardOverlayView4.mDismissButton.setAlpha(animatedFraction);
                            clipboardOverlayView4.mActionContainer.setAlpha(animatedFraction);
                            break;
                        case 3:
                            ClipboardOverlayView clipboardOverlayView5 = clipboardOverlayView;
                            int i6 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView5.getClass();
                            clipboardOverlayView5.setAlpha(valueAnimator.getAnimatedFraction());
                            break;
                        case 4:
                            ClipboardOverlayView clipboardOverlayView6 = clipboardOverlayView;
                            int i7 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView6.getClass();
                            float lerp4 = MathUtils.lerp(0.9f, 1.0f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView6.mMinimizedPreview.setScaleX(lerp4);
                            clipboardOverlayView6.mMinimizedPreview.setScaleY(lerp4);
                            clipboardOverlayView6.mClipboardPreview.setScaleX(lerp4);
                            clipboardOverlayView6.mClipboardPreview.setScaleY(lerp4);
                            clipboardOverlayView6.mPreviewBorder.setScaleX(lerp4);
                            clipboardOverlayView6.mPreviewBorder.setScaleY(lerp4);
                            float x2 = clipboardOverlayView6.mClipboardPreview.getX() + (clipboardOverlayView6.mClipboardPreview.getWidth() / 2.0f);
                            View view2 = clipboardOverlayView6.mActionContainerBackground;
                            view2.setPivotX(x2 - view2.getX());
                            LinearLayout linearLayout2 = clipboardOverlayView6.mActionContainer;
                            linearLayout2.setPivotX(x2 - ((View) linearLayout2.getParent()).getX());
                            float lerp5 = MathUtils.lerp(0.7f, 1.0f, valueAnimator.getAnimatedFraction());
                            float lerp6 = MathUtils.lerp(0.9f, 1.0f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView6.mActionContainer.setScaleX(lerp5);
                            clipboardOverlayView6.mActionContainer.setScaleY(lerp6);
                            clipboardOverlayView6.mActionContainerBackground.setScaleX(lerp5);
                            clipboardOverlayView6.mActionContainerBackground.setScaleY(lerp6);
                            break;
                        default:
                            ClipboardOverlayView clipboardOverlayView7 = clipboardOverlayView;
                            int i8 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView7.getClass();
                            float animatedFraction2 = valueAnimator.getAnimatedFraction();
                            clipboardOverlayView7.mMinimizedPreview.setAlpha(animatedFraction2);
                            clipboardOverlayView7.mClipboardPreview.setAlpha(animatedFraction2);
                            clipboardOverlayView7.mPreviewBorder.setAlpha(animatedFraction2);
                            clipboardOverlayView7.mDismissButton.setAlpha(animatedFraction2);
                            clipboardOverlayView7.mActionContainer.setAlpha(animatedFraction2);
                            break;
                    }
                }
            });
            animatorSet.play(ofFloat3).with(ofFloat2);
            animatorSet.play(ofFloat).after(150L).after(ofFloat3);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.7
                public boolean mCancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator2) {
                    super.onAnimationCancel(animator2);
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator2) {
                    super.onAnimationEnd(animator2);
                    if (this.mCancelled) {
                        return;
                    }
                    ClipboardOverlayController.this.hideImmediate();
                }
            });
            this.mExitAnimator = animatorSet;
            animatorSet.start();
        }
    }

    public final void hideImmediate() {
        this.mTimeoutHandler.removeMessages(2);
        ClipboardOverlayWindow clipboardOverlayWindow = this.mWindow;
        View peekDecorView = clipboardOverlayWindow.peekDecorView();
        if (peekDecorView != null && peekDecorView.isAttachedToWindow()) {
            clipboardOverlayWindow.mWindowManager.removeViewImmediate(peekDecorView);
        }
        C11482 c11482 = this.mCloseDialogsReceiver;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        if (c11482 != null) {
            broadcastDispatcher.unregisterReceiver(c11482);
            this.mCloseDialogsReceiver = null;
        }
        C11493 c11493 = this.mScreenshotReceiver;
        if (c11493 != null) {
            broadcastDispatcher.unregisterReceiver(c11493);
            this.mScreenshotReceiver = null;
        }
        C11515 c11515 = this.mInputEventReceiver;
        if (c11515 != null) {
            c11515.dispose();
            this.mInputEventReceiver = null;
        }
        InputMonitor inputMonitor = this.mInputMonitor;
        if (inputMonitor != null) {
            inputMonitor.dispose();
            this.mInputMonitor = null;
        }
    }

    public void onInsetsChanged(WindowInsets windowInsets, int i) {
        ClipboardOverlayView clipboardOverlayView = this.mView;
        clipboardOverlayView.setInsets(windowInsets, i);
        if (!(windowInsets.getInsets(WindowInsets.Type.ime()).bottom > 0) || this.mIsMinimized) {
            return;
        }
        this.mIsMinimized = true;
        clipboardOverlayView.setMinimized(true);
    }
}
