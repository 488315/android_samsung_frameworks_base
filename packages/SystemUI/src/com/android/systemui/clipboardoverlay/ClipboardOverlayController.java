package com.android.systemui.clipboardoverlay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.input.InputManager;
import android.os.Looper;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import androidx.emoji2.text.FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.broadcast.BroadcastSender$$ExternalSyntheticLambda0;
import com.android.systemui.clipboardoverlay.ClipboardOverlayView;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.screenshot.TimeoutHandler;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class ClipboardOverlayController implements ClipboardOverlayView.ClipboardOverlayCallbacks {
    public final Executor mBgExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final AnonymousClass1 mClipboardCallbacks = new ClipboardOverlayView.ClipboardOverlayCallbacks() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.1
        @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
        public final void onDismissButtonTapped() {
            final ClipboardOverlayController clipboardOverlayController = ClipboardOverlayController.this;
            clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISS_TAPPED);
            Animator animator = clipboardOverlayController.mExitAnimator;
            if (animator == null || !animator.isRunning()) {
                Animator exitAnimation = clipboardOverlayController.mView.getExitAnimation();
                clipboardOverlayController.mExitAnimator = exitAnimation;
                exitAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.8
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
                clipboardOverlayController.mExitAnimator.start();
            }
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
    public final ClipboardLogger mClipboardLogger;
    public AnonymousClass3 mCloseDialogsReceiver;
    public final Context mContext;
    public Animator mEnterAnimator;
    public Animator mExitAnimator;
    public AnonymousClass6 mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public final IntentCreator mIntentCreator;
    public boolean mIsMinimized;
    public final FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1 mOnPreviewTapped;
    public final FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1 mOnRemoteCopyTapped;
    public AnonymousClass4 mScreenshotReceiver;
    public final TimeoutHandler mTimeoutHandler;
    public final ClipboardTransitionExecutor mTransitionExecutor;
    public final ClipboardOverlayView mView;
    public final ClipboardOverlayWindow mWindow;

    /* renamed from: com.android.systemui.clipboardoverlay.ClipboardOverlayController$10, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass10 {
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

    public class ClipboardLogger {
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
    /* JADX WARN: Type inference failed for: r2v3, types: [android.content.BroadcastReceiver, com.android.systemui.clipboardoverlay.ClipboardOverlayController$3] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$6] */
    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$1] */
    /* JADX WARN: Type inference failed for: r8v6, types: [android.content.BroadcastReceiver, com.android.systemui.clipboardoverlay.ClipboardOverlayController$4] */
    public ClipboardOverlayController(Context context, ClipboardOverlayView clipboardOverlayView, final ClipboardOverlayWindow clipboardOverlayWindow, BroadcastDispatcher broadcastDispatcher, BroadcastSender broadcastSender, TimeoutHandler timeoutHandler, ClipboardOverlayUtils clipboardOverlayUtils, Executor executor, ClipboardImageLoader clipboardImageLoader, ClipboardTransitionExecutor clipboardTransitionExecutor, ClipboardIndicationProvider clipboardIndicationProvider, UiEventLogger uiEventLogger, IntentCreator intentCreator) {
        new Object(this) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.2
        };
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mTransitionExecutor = clipboardTransitionExecutor;
        this.mClipboardLogger = new ClipboardLogger(uiEventLogger);
        this.mIntentCreator = intentCreator;
        this.mView = clipboardOverlayView;
        this.mWindow = clipboardOverlayWindow;
        ClipboardOverlayController$$ExternalSyntheticLambda0 clipboardOverlayController$$ExternalSyntheticLambda0 = new ClipboardOverlayController$$ExternalSyntheticLambda0(this);
        ClipboardOverlayController$$ExternalSyntheticLambda1 clipboardOverlayController$$ExternalSyntheticLambda1 = new ClipboardOverlayController$$ExternalSyntheticLambda1(this, 0);
        clipboardOverlayWindow.mOnKeyboardChangeListener = clipboardOverlayController$$ExternalSyntheticLambda0;
        clipboardOverlayWindow.mOnOrientationChangeListener = clipboardOverlayController$$ExternalSyntheticLambda1;
        View decorView = clipboardOverlayWindow.getDecorView();
        if (!decorView.isAttachedToWindow()) {
            clipboardOverlayWindow.mWindowManager.addView(decorView, clipboardOverlayWindow.mWindowLayoutParams);
            decorView.requestApplyInsets();
        }
        Runnable runnable = new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final ClipboardOverlayWindow clipboardOverlayWindow2 = clipboardOverlayWindow;
                clipboardOverlayWindow2.mKeyboardVisible = clipboardOverlayWindow2.mWindowManager.getCurrentWindowMetrics().getWindowInsets().isVisible(WindowInsets.Type.ime());
                clipboardOverlayWindow2.peekDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayWindow$$ExternalSyntheticLambda1
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        ClipboardOverlayWindow clipboardOverlayWindow3 = clipboardOverlayWindow2;
                        WindowInsets windowInsets = clipboardOverlayWindow3.mWindowManager.getCurrentWindowMetrics().getWindowInsets();
                        boolean zIsVisible = windowInsets.isVisible(WindowInsets.Type.ime());
                        if (zIsVisible != clipboardOverlayWindow3.mKeyboardVisible) {
                            clipboardOverlayWindow3.mKeyboardVisible = zIsVisible;
                            clipboardOverlayWindow3.mOnKeyboardChangeListener.accept(windowInsets, Integer.valueOf(clipboardOverlayWindow3.mOrientation));
                        }
                    }
                });
                clipboardOverlayWindow2.peekDecorView().getViewRootImpl().setActivityConfigCallback(clipboardOverlayWindow2);
            }
        };
        View decorView2 = clipboardOverlayWindow.getDecorView();
        if (decorView2.isAttachedToWindow()) {
            runnable.run();
        } else {
            decorView2.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener(clipboardOverlayWindow, decorView2, runnable) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayWindow.1
                public final /* synthetic */ Runnable val$action;
                public final /* synthetic */ View val$decorView;

                public AnonymousClass1(final ClipboardOverlayWindow clipboardOverlayWindow2, View decorView22, Runnable runnable2) {
                    this.val$decorView = decorView22;
                    this.val$action = runnable2;
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
        this.mTimeoutHandler = timeoutHandler;
        timeoutHandler.mDefaultTimeout = PluginEdgeLightingPlus.VERSION;
        this.mBgExecutor = executor;
        clipboardOverlayView.mCallbacks = this;
        final int i = 0;
        clipboardOverlayView.mDismissButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                ClipboardOverlayView.ClipboardOverlayCallbacks clipboardOverlayCallbacks = this;
                switch (i2) {
                    case 0:
                        int i3 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks.onDismissButtonTapped();
                        break;
                    case 1:
                        int i4 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks.onPreviewTapped();
                        break;
                    default:
                        int i5 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks.onMinimizedViewTapped();
                        break;
                }
            }
        });
        final int i2 = 1;
        clipboardOverlayView.mClipboardPreview.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                ClipboardOverlayView.ClipboardOverlayCallbacks clipboardOverlayCallbacks = this;
                switch (i22) {
                    case 0:
                        int i3 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks.onDismissButtonTapped();
                        break;
                    case 1:
                        int i4 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks.onPreviewTapped();
                        break;
                    default:
                        int i5 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks.onMinimizedViewTapped();
                        break;
                }
            }
        });
        final int i3 = 2;
        clipboardOverlayView.mMinimizedPreview.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i3;
                ClipboardOverlayView.ClipboardOverlayCallbacks clipboardOverlayCallbacks = this;
                switch (i22) {
                    case 0:
                        int i32 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks.onDismissButtonTapped();
                        break;
                    case 1:
                        int i4 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks.onPreviewTapped();
                        break;
                    default:
                        int i5 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks.onMinimizedViewTapped();
                        break;
                }
            }
        });
        clipboardOverlayView.mClipboardCallbacks = this;
        ClipboardOverlayController$$ExternalSyntheticLambda1 clipboardOverlayController$$ExternalSyntheticLambda12 = new ClipboardOverlayController$$ExternalSyntheticLambda1(this, 1);
        View decorView3 = clipboardOverlayWindow2.getDecorView();
        if (decorView3.isAttachedToWindow()) {
            clipboardOverlayController$$ExternalSyntheticLambda12.run();
        } else {
            decorView3.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener(clipboardOverlayWindow2, decorView3, clipboardOverlayController$$ExternalSyntheticLambda12) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayWindow.1
                public final /* synthetic */ Runnable val$action;
                public final /* synthetic */ View val$decorView;

                public AnonymousClass1(final ClipboardOverlayWindow clipboardOverlayWindow2, View decorView32, Runnable clipboardOverlayController$$ExternalSyntheticLambda122) {
                    this.val$decorView = decorView32;
                    this.val$action = clipboardOverlayController$$ExternalSyntheticLambda122;
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
        timeoutHandler.mOnTimeout = new ClipboardOverlayController$$ExternalSyntheticLambda1(this, 2);
        ?? r2 = new BroadcastReceiver() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    ClipboardOverlayController.this.finish(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                }
            }
        };
        this.mCloseDialogsReceiver = r2;
        broadcastDispatcher.registerReceiver(new IntentFilter(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), r2);
        ?? r8 = new BroadcastReceiver() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.android.systemui.SCREENSHOT".equals(intent.getAction())) {
                    ClipboardOverlayController.this.finish(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                }
            }
        };
        this.mScreenshotReceiver = r8;
        broadcastDispatcher.registerReceiver(r8, new IntentFilter("com.android.systemui.SCREENSHOT"), null, null, 2, "com.android.systemui.permission.SELF");
        this.mInputMonitor = ((InputManager) context.getSystemService(InputManager.class)).monitorGestureInput("clipboard overlay", 0);
        this.mInputEventReceiver = new InputEventReceiver(this.mInputMonitor.getInputChannel(), Looper.getMainLooper()) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.6
            public final void onInputEvent(InputEvent inputEvent) {
                ClipboardOverlayController.this.getClass();
                finishInputEvent(inputEvent, true);
            }
        };
        Intent intent = new Intent("com.android.systemui.COPY");
        intent.setPackage(context.getPackageName());
        broadcastSender.getClass();
        broadcastSender.sendInBackground(String.valueOf(intent), new BroadcastSender$$ExternalSyntheticLambda0(broadcastSender, intent, 0));
    }

    public final void animateFromMinimized() {
        Animator animator = this.mEnterAnimator;
        if (animator != null && animator.isRunning()) {
            this.mEnterAnimator.cancel();
        }
        ClipboardOverlayView clipboardOverlayView = this.mView;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(clipboardOverlayView.mMinimizedPreview, "alpha", 1.0f, 0.0f);
        objectAnimatorOfFloat.setDuration(66L);
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView.3
            public AnonymousClass3() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                super.onAnimationEnd(animator2);
                ClipboardOverlayView.this.mMinimizedPreview.setVisibility(8);
                ClipboardOverlayView.this.mMinimizedPreview.setAlpha(1.0f);
            }
        });
        this.mEnterAnimator = objectAnimatorOfFloat;
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                super.onAnimationEnd(animator2);
                ClipboardOverlayController clipboardOverlayController = ClipboardOverlayController.this;
                if (clipboardOverlayController.mIsMinimized) {
                    clipboardOverlayController.mClipboardLogger.mUiEventLogger.log(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_EXPANDED_FROM_MINIMIZED, 0, (String) null);
                    ClipboardOverlayController.this.mIsMinimized = false;
                }
                ClipboardOverlayController.this.mView.setMinimized(false);
                int[] iArr = AnonymousClass10.$SwitchMap$com$android$systemui$clipboardoverlay$ClipboardModel$Type;
                throw null;
            }
        });
        this.mEnterAnimator.start();
    }

    public final void finish(final ClipboardOverlayEvent clipboardOverlayEvent) {
        Animator animator = this.mExitAnimator;
        if (animator == null || !animator.isRunning()) {
            Animator exitAnimation = this.mView.getExitAnimation();
            this.mExitAnimator = exitAnimation;
            final Intent intent = null;
            exitAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.9
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
                    ClipboardOverlayController.this.mClipboardLogger.logSessionComplete(clipboardOverlayEvent);
                    Intent intent2 = intent;
                    if (intent2 != null) {
                        ClipboardOverlayController.this.mContext.startActivity(intent2);
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
        View viewPeekDecorView = clipboardOverlayWindow.peekDecorView();
        if (viewPeekDecorView != null && viewPeekDecorView.isAttachedToWindow()) {
            clipboardOverlayWindow.mWindowManager.removeViewImmediate(viewPeekDecorView);
        }
        AnonymousClass3 anonymousClass3 = this.mCloseDialogsReceiver;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        if (anonymousClass3 != null) {
            broadcastDispatcher.unregisterReceiver(anonymousClass3);
            this.mCloseDialogsReceiver = null;
        }
        AnonymousClass4 anonymousClass4 = this.mScreenshotReceiver;
        if (anonymousClass4 != null) {
            broadcastDispatcher.unregisterReceiver(anonymousClass4);
            this.mScreenshotReceiver = null;
        }
        AnonymousClass6 anonymousClass6 = this.mInputEventReceiver;
        if (anonymousClass6 != null) {
            anonymousClass6.dispose();
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
        finish(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISS_TAPPED);
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
        int[] iArr = AnonymousClass10.$SwitchMap$com$android$systemui$clipboardoverlay$ClipboardModel$Type;
        throw null;
    }

    @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
    public final void onRemoteCopyButtonTapped() {
        ClipboardOverlayEvent clipboardOverlayEvent = ClipboardOverlayEvent.CLIPBOARD_OVERLAY_EDIT_TAPPED;
        throw null;
    }

    @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
    public final void onShareButtonTapped() {
        throw null;
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
