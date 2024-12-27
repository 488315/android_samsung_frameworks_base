package com.android.systemui.clipboardoverlay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import androidx.emoji2.text.FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.clipboardoverlay.ClipboardOverlayView;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.screenshot.FloatingWindowUtil;
import com.android.systemui.screenshot.TimeoutHandler;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

public final class ClipboardOverlayController implements ClipboardOverlayView.ClipboardOverlayCallbacks {
    public final Executor mBgExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final AnonymousClass1 mClipboardCallbacks;
    public final ClipboardLogger mClipboardLogger;
    public AnonymousClass2 mCloseDialogsReceiver;
    public final Context mContext;
    public Animator mEnterAnimator;
    public Animator mExitAnimator;
    public final FeatureFlags mFeatureFlags;
    public AnonymousClass5 mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public boolean mIsMinimized;
    public final FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1 mOnPreviewTapped;
    public final FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1 mOnRemoteCopyTapped;
    public AnonymousClass3 mScreenshotReceiver;
    public final TimeoutHandler mTimeoutHandler;
    public final ClipboardOverlayView mView;
    public final ClipboardOverlayWindow mWindow;

    /* renamed from: com.android.systemui.clipboardoverlay.ClipboardOverlayController$9, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass9 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$clipboardoverlay$ClipboardModel$Type;

        static {
            int[] iArr = new int[ClipboardModel$Type.values().length];
            $SwitchMap$com$android$systemui$clipboardoverlay$ClipboardModel$Type = iArr;
            try {
                iArr[ClipboardModel$Type.TEXT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$clipboardoverlay$ClipboardModel$Type[ClipboardModel$Type.IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$clipboardoverlay$ClipboardModel$Type[ClipboardModel$Type.URI.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$clipboardoverlay$ClipboardModel$Type[ClipboardModel$Type.OTHER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public final class ClipboardLogger {
        public boolean mGuarded = false;
        public final UiEventLogger mUiEventLogger;

        public ClipboardLogger(UiEventLogger uiEventLogger) {
            this.mUiEventLogger = uiEventLogger;
        }

        public final void logSessionComplete(UiEventLogger.UiEventEnum uiEventEnum) {
            if (this.mGuarded) {
                return;
            }
            this.mGuarded = true;
            this.mUiEventLogger.log(uiEventEnum, 0, (String) null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.content.BroadcastReceiver, com.android.systemui.clipboardoverlay.ClipboardOverlayController$2] */
    /* JADX WARN: Type inference failed for: r2v4, types: [android.content.BroadcastReceiver, com.android.systemui.clipboardoverlay.ClipboardOverlayController$3] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$5] */
    public ClipboardOverlayController(Context context, ClipboardOverlayView clipboardOverlayView, final ClipboardOverlayWindow clipboardOverlayWindow, BroadcastDispatcher broadcastDispatcher, BroadcastSender broadcastSender, TimeoutHandler timeoutHandler, FeatureFlags featureFlags, ClipboardOverlayUtils clipboardOverlayUtils, Executor executor, ClipboardImageLoader clipboardImageLoader, ClipboardTransitionExecutor clipboardTransitionExecutor, UiEventLogger uiEventLogger) {
        ClipboardOverlayView.ClipboardOverlayCallbacks clipboardOverlayCallbacks = new ClipboardOverlayView.ClipboardOverlayCallbacks() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.1
            @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
            public final void onDismissButtonTapped() {
                ClipboardOverlayController clipboardOverlayController = ClipboardOverlayController.this;
                clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISS_TAPPED);
                clipboardOverlayController.animateOut();
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public final void onDismissComplete() {
                ClipboardOverlayController.this.hideImmediate();
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public final void onInteraction() {
                ClipboardOverlayController.this.getClass();
            }

            @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
            public final void onMinimizedViewTapped() {
                ClipboardOverlayController.this.animateFromMinimized();
            }

            @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
            public final void onPreviewTapped() {
                FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1 fontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1 = ClipboardOverlayController.this.mOnPreviewTapped;
            }

            @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
            public final void onRemoteCopyButtonTapped() {
                FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1 fontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1 = ClipboardOverlayController.this.mOnRemoteCopyTapped;
            }

            @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
            public final void onShareButtonTapped() {
                ClipboardOverlayController.this.getClass();
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public final void onSwipeDismissInitiated(Animator animator) {
                ClipboardOverlayController clipboardOverlayController = ClipboardOverlayController.this;
                clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_SWIPE_DISMISSED);
                clipboardOverlayController.mExitAnimator = animator;
            }
        };
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
                int i2 = i;
                ClipboardOverlayController clipboardOverlayController = this.f$0;
                switch (i2) {
                    case 0:
                        clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                        clipboardOverlayController.hideImmediate();
                        break;
                    case 1:
                        ClipboardOverlayWindow clipboardOverlayWindow2 = clipboardOverlayController.mWindow;
                        ClipboardOverlayView clipboardOverlayView2 = clipboardOverlayController.mView;
                        clipboardOverlayWindow2.setContentView(clipboardOverlayView2);
                        clipboardOverlayView2.setInsets(clipboardOverlayWindow2.mWindowManager.getCurrentWindowMetrics().getWindowInsets(), clipboardOverlayController.mContext.getResources().getConfiguration().orientation);
                        break;
                    default:
                        clipboardOverlayController.getClass();
                        Flags flags = Flags.INSTANCE;
                        clipboardOverlayController.mFeatureFlags.getClass();
                        clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_TIMED_OUT);
                        clipboardOverlayController.animateOut();
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

                public AnonymousClass1(final ClipboardOverlayWindow clipboardOverlayWindow2, View decorView22, Runnable runnable22) {
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
        this.mBgExecutor = executor;
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        clipboardOverlayView.setCallbacks(clipboardOverlayCallbacks);
        final int i2 = 1;
        Runnable runnable3 = new Runnable(this) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda1
            public final /* synthetic */ ClipboardOverlayController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i22 = i2;
                ClipboardOverlayController clipboardOverlayController = this.f$0;
                switch (i22) {
                    case 0:
                        clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                        clipboardOverlayController.hideImmediate();
                        break;
                    case 1:
                        ClipboardOverlayWindow clipboardOverlayWindow2 = clipboardOverlayController.mWindow;
                        ClipboardOverlayView clipboardOverlayView2 = clipboardOverlayController.mView;
                        clipboardOverlayWindow2.setContentView(clipboardOverlayView2);
                        clipboardOverlayView2.setInsets(clipboardOverlayWindow2.mWindowManager.getCurrentWindowMetrics().getWindowInsets(), clipboardOverlayController.mContext.getResources().getConfiguration().orientation);
                        break;
                    default:
                        clipboardOverlayController.getClass();
                        Flags flags2 = Flags.INSTANCE;
                        clipboardOverlayController.mFeatureFlags.getClass();
                        clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_TIMED_OUT);
                        clipboardOverlayController.animateOut();
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

                public AnonymousClass1(final ClipboardOverlayWindow clipboardOverlayWindow2, View decorView32, Runnable runnable32) {
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
                int i22 = i3;
                ClipboardOverlayController clipboardOverlayController = this.f$0;
                switch (i22) {
                    case 0:
                        clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                        clipboardOverlayController.hideImmediate();
                        break;
                    case 1:
                        ClipboardOverlayWindow clipboardOverlayWindow2 = clipboardOverlayController.mWindow;
                        ClipboardOverlayView clipboardOverlayView2 = clipboardOverlayController.mView;
                        clipboardOverlayWindow2.setContentView(clipboardOverlayView2);
                        clipboardOverlayView2.setInsets(clipboardOverlayWindow2.mWindowManager.getCurrentWindowMetrics().getWindowInsets(), clipboardOverlayController.mContext.getResources().getConfiguration().orientation);
                        break;
                    default:
                        clipboardOverlayController.getClass();
                        Flags flags2 = Flags.INSTANCE;
                        clipboardOverlayController.mFeatureFlags.getClass();
                        clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_TIMED_OUT);
                        clipboardOverlayController.animateOut();
                        break;
                }
            }
        };
        ?? r2 = new BroadcastReceiver() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    FeatureFlags featureFlags2 = ClipboardOverlayController.this.mFeatureFlags;
                    Flags flags2 = Flags.INSTANCE;
                    featureFlags2.getClass();
                    ClipboardOverlayController.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                    ClipboardOverlayController.this.animateOut();
                }
            }
        };
        this.mCloseDialogsReceiver = r2;
        broadcastDispatcher.registerReceiver(new IntentFilter(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), r2);
        ?? r22 = new BroadcastReceiver() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.android.systemui.SCREENSHOT".equals(intent.getAction())) {
                    FeatureFlags featureFlags2 = ClipboardOverlayController.this.mFeatureFlags;
                    Flags flags2 = Flags.INSTANCE;
                    featureFlags2.getClass();
                    ClipboardOverlayController.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                    ClipboardOverlayController.this.animateOut();
                }
            }
        };
        this.mScreenshotReceiver = r22;
        broadcastDispatcher.registerReceiver(r22, new IntentFilter("com.android.systemui.SCREENSHOT"), null, null, 2, "com.android.systemui.permission.SELF");
        this.mInputMonitor = ((InputManager) context.getSystemService(InputManager.class)).monitorGestureInput("clipboard overlay", 0);
        this.mInputEventReceiver = new InputEventReceiver(this.mInputMonitor.getInputChannel(), Looper.getMainLooper()) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.5
            public final void onInputEvent(InputEvent inputEvent) {
                FeatureFlags featureFlags2 = ClipboardOverlayController.this.mFeatureFlags;
                Flags flags2 = Flags.INSTANCE;
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
                        Region.Op op = Region.Op.UNION;
                        region.op(rect, op);
                        clipboardOverlayView2.mActionContainerBackground.getBoundsOnScreen(rect);
                        rect.inset((int) FloatingWindowUtil.dpToPx(clipboardOverlayView2.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(clipboardOverlayView2.mDisplayMetrics, -12.0f));
                        region.op(rect, op);
                        clipboardOverlayView2.mMinimizedPreview.getBoundsOnScreen(rect);
                        rect.inset((int) FloatingWindowUtil.dpToPx(clipboardOverlayView2.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(clipboardOverlayView2.mDisplayMetrics, -12.0f));
                        region.op(rect, op);
                        clipboardOverlayView2.mDismissButton.getBoundsOnScreen(rect);
                        region.op(rect, op);
                        if (!region.contains(rawX, rawY)) {
                            FeatureFlags featureFlags3 = ClipboardOverlayController.this.mFeatureFlags;
                            Flags flags3 = Flags.INSTANCE;
                            featureFlags3.getClass();
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

    public final void animateFromMinimized() {
        Animator animator = this.mEnterAnimator;
        if (animator != null && animator.isRunning()) {
            this.mEnterAnimator.cancel();
        }
        ClipboardOverlayView clipboardOverlayView = this.mView;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(clipboardOverlayView.mMinimizedPreview, "alpha", 1.0f, 0.0f);
        ofFloat.setDuration(66L);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView.3
            public AnonymousClass3() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                super.onAnimationEnd(animator2);
                ClipboardOverlayView.this.mMinimizedPreview.setVisibility(8);
                ClipboardOverlayView.this.mMinimizedPreview.setAlpha(1.0f);
            }
        });
        this.mEnterAnimator = ofFloat;
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                super.onAnimationEnd(animator2);
                ClipboardOverlayController clipboardOverlayController = ClipboardOverlayController.this;
                if (clipboardOverlayController.mIsMinimized) {
                    clipboardOverlayController.mClipboardLogger.mUiEventLogger.log(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_EXPANDED_FROM_MINIMIZED, 0, (String) null);
                    ClipboardOverlayController.this.mIsMinimized = false;
                }
                FeatureFlags featureFlags = ClipboardOverlayController.this.mFeatureFlags;
                Flags flags = Flags.INSTANCE;
                featureFlags.getClass();
                ClipboardOverlayController.this.mView.setMinimized(false);
                int[] iArr = AnonymousClass9.$SwitchMap$com$android$systemui$clipboardoverlay$ClipboardModel$Type;
                throw null;
            }
        });
        this.mEnterAnimator.start();
    }

    public final void animateOut() {
        final int i = 2;
        Animator animator = this.mExitAnimator;
        if (animator == null || !animator.isRunning()) {
            final ClipboardOverlayView clipboardOverlayView = this.mView;
            clipboardOverlayView.getClass();
            LinearInterpolator linearInterpolator = new LinearInterpolator();
            PathInterpolator pathInterpolator = new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
            AnimatorSet animatorSet = new AnimatorSet();
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setInterpolator(linearInterpolator);
            ofFloat.setDuration(100L);
            final int i2 = 0;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int i3 = i2;
                    ClipboardOverlayView clipboardOverlayView2 = clipboardOverlayView;
                    switch (i3) {
                        case 0:
                            int i4 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView2.getClass();
                            clipboardOverlayView2.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
                            break;
                        case 1:
                            int i5 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView2.getClass();
                            float lerp = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView2.mMinimizedPreview.setScaleX(lerp);
                            clipboardOverlayView2.mMinimizedPreview.setScaleY(lerp);
                            clipboardOverlayView2.mClipboardPreview.setScaleX(lerp);
                            clipboardOverlayView2.mClipboardPreview.setScaleY(lerp);
                            clipboardOverlayView2.mPreviewBorder.setScaleX(lerp);
                            clipboardOverlayView2.mPreviewBorder.setScaleY(lerp);
                            float x = clipboardOverlayView2.mClipboardPreview.getX() + (clipboardOverlayView2.mClipboardPreview.getWidth() / 2.0f);
                            View view = clipboardOverlayView2.mActionContainerBackground;
                            view.setPivotX(x - view.getX());
                            LinearLayout linearLayout = clipboardOverlayView2.mActionContainer;
                            linearLayout.setPivotX(x - ((View) linearLayout.getParent()).getX());
                            float lerp2 = MathUtils.lerp(1.0f, 0.8f, valueAnimator.getAnimatedFraction());
                            float lerp3 = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView2.mActionContainer.setScaleX(lerp2);
                            clipboardOverlayView2.mActionContainer.setScaleY(lerp3);
                            clipboardOverlayView2.mActionContainerBackground.setScaleX(lerp2);
                            clipboardOverlayView2.mActionContainerBackground.setScaleY(lerp3);
                            break;
                        default:
                            int i6 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView2.getClass();
                            float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
                            clipboardOverlayView2.mMinimizedPreview.setAlpha(animatedFraction);
                            clipboardOverlayView2.mClipboardPreview.setAlpha(animatedFraction);
                            clipboardOverlayView2.mPreviewBorder.setAlpha(animatedFraction);
                            clipboardOverlayView2.mDismissButton.setAlpha(animatedFraction);
                            clipboardOverlayView2.mActionContainer.setAlpha(animatedFraction);
                            break;
                    }
                }
            });
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat2.setInterpolator(pathInterpolator);
            ofFloat2.setDuration(250L);
            final int i3 = 1;
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int i32 = i3;
                    ClipboardOverlayView clipboardOverlayView2 = clipboardOverlayView;
                    switch (i32) {
                        case 0:
                            int i4 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView2.getClass();
                            clipboardOverlayView2.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
                            break;
                        case 1:
                            int i5 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView2.getClass();
                            float lerp = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView2.mMinimizedPreview.setScaleX(lerp);
                            clipboardOverlayView2.mMinimizedPreview.setScaleY(lerp);
                            clipboardOverlayView2.mClipboardPreview.setScaleX(lerp);
                            clipboardOverlayView2.mClipboardPreview.setScaleY(lerp);
                            clipboardOverlayView2.mPreviewBorder.setScaleX(lerp);
                            clipboardOverlayView2.mPreviewBorder.setScaleY(lerp);
                            float x = clipboardOverlayView2.mClipboardPreview.getX() + (clipboardOverlayView2.mClipboardPreview.getWidth() / 2.0f);
                            View view = clipboardOverlayView2.mActionContainerBackground;
                            view.setPivotX(x - view.getX());
                            LinearLayout linearLayout = clipboardOverlayView2.mActionContainer;
                            linearLayout.setPivotX(x - ((View) linearLayout.getParent()).getX());
                            float lerp2 = MathUtils.lerp(1.0f, 0.8f, valueAnimator.getAnimatedFraction());
                            float lerp3 = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView2.mActionContainer.setScaleX(lerp2);
                            clipboardOverlayView2.mActionContainer.setScaleY(lerp3);
                            clipboardOverlayView2.mActionContainerBackground.setScaleX(lerp2);
                            clipboardOverlayView2.mActionContainerBackground.setScaleY(lerp3);
                            break;
                        default:
                            int i6 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView2.getClass();
                            float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
                            clipboardOverlayView2.mMinimizedPreview.setAlpha(animatedFraction);
                            clipboardOverlayView2.mClipboardPreview.setAlpha(animatedFraction);
                            clipboardOverlayView2.mPreviewBorder.setAlpha(animatedFraction);
                            clipboardOverlayView2.mDismissButton.setAlpha(animatedFraction);
                            clipboardOverlayView2.mActionContainer.setAlpha(animatedFraction);
                            break;
                    }
                }
            });
            ValueAnimator ofFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat3.setInterpolator(linearInterpolator);
            ofFloat3.setDuration(166L);
            ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int i32 = i;
                    ClipboardOverlayView clipboardOverlayView2 = clipboardOverlayView;
                    switch (i32) {
                        case 0:
                            int i4 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView2.getClass();
                            clipboardOverlayView2.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
                            break;
                        case 1:
                            int i5 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView2.getClass();
                            float lerp = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView2.mMinimizedPreview.setScaleX(lerp);
                            clipboardOverlayView2.mMinimizedPreview.setScaleY(lerp);
                            clipboardOverlayView2.mClipboardPreview.setScaleX(lerp);
                            clipboardOverlayView2.mClipboardPreview.setScaleY(lerp);
                            clipboardOverlayView2.mPreviewBorder.setScaleX(lerp);
                            clipboardOverlayView2.mPreviewBorder.setScaleY(lerp);
                            float x = clipboardOverlayView2.mClipboardPreview.getX() + (clipboardOverlayView2.mClipboardPreview.getWidth() / 2.0f);
                            View view = clipboardOverlayView2.mActionContainerBackground;
                            view.setPivotX(x - view.getX());
                            LinearLayout linearLayout = clipboardOverlayView2.mActionContainer;
                            linearLayout.setPivotX(x - ((View) linearLayout.getParent()).getX());
                            float lerp2 = MathUtils.lerp(1.0f, 0.8f, valueAnimator.getAnimatedFraction());
                            float lerp3 = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                            clipboardOverlayView2.mActionContainer.setScaleX(lerp2);
                            clipboardOverlayView2.mActionContainer.setScaleY(lerp3);
                            clipboardOverlayView2.mActionContainerBackground.setScaleX(lerp2);
                            clipboardOverlayView2.mActionContainerBackground.setScaleY(lerp3);
                            break;
                        default:
                            int i6 = ClipboardOverlayView.$r8$clinit;
                            clipboardOverlayView2.getClass();
                            float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
                            clipboardOverlayView2.mMinimizedPreview.setAlpha(animatedFraction);
                            clipboardOverlayView2.mClipboardPreview.setAlpha(animatedFraction);
                            clipboardOverlayView2.mPreviewBorder.setAlpha(animatedFraction);
                            clipboardOverlayView2.mDismissButton.setAlpha(animatedFraction);
                            clipboardOverlayView2.mActionContainer.setAlpha(animatedFraction);
                            break;
                    }
                }
            });
            animatorSet.play(ofFloat3).with(ofFloat2);
            animatorSet.play(ofFloat).after(150L).after(ofFloat3);
            this.mExitAnimator = animatorSet;
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
            this.mExitAnimator.start();
        }
    }

    public final void hideImmediate() {
        this.mTimeoutHandler.removeMessages(2);
        ClipboardOverlayWindow clipboardOverlayWindow = this.mWindow;
        View peekDecorView = clipboardOverlayWindow.peekDecorView();
        if (peekDecorView != null && peekDecorView.isAttachedToWindow()) {
            clipboardOverlayWindow.mWindowManager.removeViewImmediate(peekDecorView);
        }
        AnonymousClass2 anonymousClass2 = this.mCloseDialogsReceiver;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        if (anonymousClass2 != null) {
            broadcastDispatcher.unregisterReceiver(anonymousClass2);
            this.mCloseDialogsReceiver = null;
        }
        AnonymousClass3 anonymousClass3 = this.mScreenshotReceiver;
        if (anonymousClass3 != null) {
            broadcastDispatcher.unregisterReceiver(anonymousClass3);
            this.mScreenshotReceiver = null;
        }
        AnonymousClass5 anonymousClass5 = this.mInputEventReceiver;
        if (anonymousClass5 != null) {
            anonymousClass5.dispose();
            this.mInputEventReceiver = null;
        }
        InputMonitor inputMonitor = this.mInputMonitor;
        if (inputMonitor != null) {
            inputMonitor.dispose();
            this.mInputMonitor = null;
        }
    }

    @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
    public final void onDismissButtonTapped() {
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
    }

    @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
    public final void onDismissComplete() {
        hideImmediate();
    }

    public void onInsetsChanged(WindowInsets windowInsets, int i) {
        ClipboardOverlayView clipboardOverlayView = this.mView;
        clipboardOverlayView.setInsets(windowInsets, i);
        if (windowInsets.getInsets(WindowInsets.Type.ime()).bottom <= 0 || this.mIsMinimized) {
            return;
        }
        this.mIsMinimized = true;
        clipboardOverlayView.setMinimized(true);
    }

    @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
    public final void onInteraction() {
        throw null;
    }

    @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
    public final void onMinimizedViewTapped() {
        animateFromMinimized();
    }

    @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
    public final void onPreviewTapped() {
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
    }

    @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
    public final void onRemoteCopyButtonTapped() {
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
    }

    @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
    public final void onShareButtonTapped() {
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
    }

    @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
    public final void onSwipeDismissInitiated(Animator animator) {
        Animator animator2 = this.mExitAnimator;
        if (animator2 != null && animator2.isRunning()) {
            this.mExitAnimator.cancel();
        }
        this.mExitAnimator = animator;
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_SWIPE_DISMISSED);
    }
}
