package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Choreographer;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ImageView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.screenshot.ScreenshotShelfViewProxy;
import com.android.systemui.screenshot.ui.ScreenshotAnimationController;
import com.android.systemui.screenshot.ui.ScreenshotShelfView;
import com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder;
import com.android.systemui.screenshot.ui.viewmodel.AnimationState;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class ScreenshotShelfViewProxy {
    public final ScreenshotAnimationController animationController;
    public ScreenshotViewCallback callbacks;
    public final Context context;
    public final int displayId;
    public InputChannelCompat$InputEventReceiver inputEventReceiver;
    public InputMonitorCompat inputMonitor;
    public boolean isDismissing;
    public final UiEventLogger logger;
    public String packageName;
    public final View screenshotPreview;
    public final ThumbnailObserver thumbnailObserver;
    public final ScreenshotShelfView view;
    public final ScreenshotViewModel viewModel;
    public final WindowManager windowManager;

    public interface Factory {
        ScreenshotShelfViewProxy getProxy(Context context, int i);
    }

    public interface ScreenshotViewCallback {
        void onDismiss();

        void onTouchOutside();

        void onUserInteraction();
    }

    public ScreenshotShelfViewProxy(UiEventLogger uiEventLogger, ScreenshotViewModel screenshotViewModel, WindowManager windowManager, ScreenshotShelfViewBinder screenshotShelfViewBinder, ThumbnailObserver thumbnailObserver, Context context, int i) throws Resources.NotFoundException {
        this.logger = uiEventLogger;
        this.viewModel = screenshotViewModel;
        this.windowManager = windowManager;
        this.thumbnailObserver = thumbnailObserver;
        this.context = context;
        this.displayId = i;
        ScreenshotShelfView screenshotShelfView = (ScreenshotShelfView) LayoutInflater.from(context).inflate(R.layout.screenshot_shelf, (ViewGroup) null);
        this.view = screenshotShelfView;
        this.packageName = "";
        ScreenshotAnimationController screenshotAnimationController = new ScreenshotAnimationController(screenshotShelfView, screenshotViewModel);
        this.animationController = screenshotAnimationController;
        final int i2 = 0;
        screenshotShelfViewBinder.bind(screenshotShelfView, screenshotViewModel, screenshotAnimationController, LayoutInflater.from(context), new ScreenshotShelfViewProxy$$ExternalSyntheticLambda0(this), new ScreenshotShelfViewProxy$$ExternalSyntheticLambda1(this, i2));
        screenshotShelfView.updateInsets(windowManager.getCurrentWindowMetrics().getWindowInsets());
        final Function1 function1 = new Function1(this) { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$$ExternalSyntheticLambda2
            public final /* synthetic */ ScreenshotShelfViewProxy f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i2) {
                    case 0:
                        this.f$0.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER, null);
                        break;
                    default:
                        this.f$0.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER, null);
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final OnBackInvokedCallback onBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$addPredictiveBackListener$onBackInvokedCallback$1
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                DebugLogger debugLogger = DebugLogger.INSTANCE;
                Reflection.getOrCreateKotlinClass(this.this$0.getClass()).getSimpleName();
                function1.mo781invoke(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
            }
        };
        screenshotShelfView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$addPredictiveBackListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                DebugLogger debugLogger = DebugLogger.INSTANCE;
                Reflection.getOrCreateKotlinClass(ScreenshotShelfViewProxy$addPredictiveBackListener$1.class).getSimpleName();
                OnBackInvokedDispatcher onBackInvokedDispatcherFindOnBackInvokedDispatcher = this.this$0.view.findOnBackInvokedDispatcher();
                if (onBackInvokedDispatcherFindOnBackInvokedDispatcher != null) {
                    onBackInvokedDispatcherFindOnBackInvokedDispatcher.registerOnBackInvokedCallback(0, onBackInvokedCallback);
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                DebugLogger debugLogger = DebugLogger.INSTANCE;
                Reflection.getOrCreateKotlinClass(ScreenshotShelfViewProxy$addPredictiveBackListener$1.class).getSimpleName();
                OnBackInvokedDispatcher onBackInvokedDispatcherFindOnBackInvokedDispatcher = view.findOnBackInvokedDispatcher();
                if (onBackInvokedDispatcherFindOnBackInvokedDispatcher != null) {
                    onBackInvokedDispatcherFindOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
                }
            }
        });
        final int i3 = 1;
        final Function1 function12 = new Function1(this) { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$$ExternalSyntheticLambda2
            public final /* synthetic */ ScreenshotShelfViewProxy f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i3) {
                    case 0:
                        this.f$0.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER, null);
                        break;
                    default:
                        this.f$0.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER, null);
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        screenshotShelfView.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$setOnKeyListener$1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i4, KeyEvent keyEvent) {
                if (i4 != 4 && i4 != 111) {
                    return false;
                }
                DebugLogger debugLogger = DebugLogger.INSTANCE;
                Reflection.getOrCreateKotlinClass(ScreenshotShelfViewProxy$setOnKeyListener$1.class).getSimpleName();
                function12.mo781invoke(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
                return true;
            }
        });
        DebugLogger debugLogger = DebugLogger.INSTANCE;
        Reflection.getOrCreateKotlinClass(ScreenshotShelfViewProxy.class).getSimpleName();
        screenshotShelfView.getViewTreeObserver().addOnComputeInternalInsetsListener(new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy.6
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                internalInsetsInfo.setTouchableInsets(3);
                internalInsetsInfo.touchableRegion.set(ScreenshotShelfViewProxy.access$getTouchRegion(ScreenshotShelfViewProxy.this));
            }
        });
        ImageView imageView = screenshotShelfView.screenshotPreview;
        this.screenshotPreview = imageView != null ? imageView : null;
        screenshotShelfView.requireViewById(R.id.screenshot_preview_border);
        thumbnailObserver.getClass();
        screenshotShelfView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy.7
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                final ScreenshotShelfViewProxy screenshotShelfViewProxy = ScreenshotShelfViewProxy.this;
                screenshotShelfViewProxy.stopInputListening();
                InputMonitorCompat inputMonitorCompat = new InputMonitorCompat("Screenshot", screenshotShelfViewProxy.displayId);
                screenshotShelfViewProxy.inputEventReceiver = inputMonitorCompat.getInputReceiver(Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$startInputListening$1$1
                    @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                    public final void onInputEvent(InputEvent inputEvent) {
                        ScreenshotShelfViewProxy.ScreenshotViewCallback screenshotViewCallback;
                        if (inputEvent instanceof MotionEvent) {
                            MotionEvent motionEvent = (MotionEvent) inputEvent;
                            if (motionEvent.getActionMasked() == 0) {
                                ScreenshotShelfViewProxy screenshotShelfViewProxy2 = screenshotShelfViewProxy;
                                if (ScreenshotShelfViewProxy.access$getTouchRegion(screenshotShelfViewProxy2).contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY()) || (screenshotViewCallback = screenshotShelfViewProxy2.callbacks) == null) {
                                    return;
                                }
                                screenshotViewCallback.onTouchOutside();
                            }
                        }
                    }
                });
                screenshotShelfViewProxy.inputMonitor = inputMonitorCompat;
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                ScreenshotShelfViewProxy.this.stopInputListening();
            }
        });
    }

    public static final Region access$getTouchRegion(ScreenshotShelfViewProxy screenshotShelfViewProxy) {
        Insets insets = screenshotShelfViewProxy.windowManager.getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.systemGestures());
        ScreenshotShelfView screenshotShelfView = screenshotShelfViewProxy.view;
        screenshotShelfView.getClass();
        Region region = new Region();
        int iDpToPx = (int) FloatingWindowUtil.dpToPx(screenshotShelfView.displayMetrics, -12.0f);
        ImageView imageView = screenshotShelfView.screenshotPreview;
        if (imageView == null) {
            imageView = null;
        }
        screenshotShelfView.addInsetView(region, imageView, iDpToPx);
        View view = screenshotShelfView.actionsContainerBackground;
        if (view == null) {
            view = null;
        }
        screenshotShelfView.addInsetView(region, view, iDpToPx);
        View view2 = screenshotShelfView.dismissButton;
        screenshotShelfView.addInsetView(region, view2 != null ? view2 : null, iDpToPx);
        View viewFindViewById = screenshotShelfView.findViewById(R.id.screenshot_message_container);
        if (viewFindViewById != null) {
            screenshotShelfView.addInsetView(region, viewFindViewById, iDpToPx);
        }
        if (screenshotShelfView.getResources().getInteger(android.R.integer.config_screenTimeoutOverride) == 2) {
            Rect rect = new Rect(0, 0, insets.left, screenshotShelfView.displayMetrics.heightPixels);
            Region.Op op = Region.Op.UNION;
            region.op(rect, op);
            DisplayMetrics displayMetrics = screenshotShelfView.displayMetrics;
            int i = displayMetrics.widthPixels;
            rect.set(i - insets.right, 0, i, displayMetrics.heightPixels);
            region.op(rect, op);
        }
        return region;
    }

    public final void requestDismissal(ScreenshotEvent screenshotEvent, Float f) {
        DebugLogger debugLogger = DebugLogger.INSTANCE;
        Reflection.getOrCreateKotlinClass(ScreenshotShelfViewProxy.class).getSimpleName();
        if (this.isDismissing) {
            Reflection.getOrCreateKotlinClass(ScreenshotShelfViewProxy.class).getSimpleName();
            return;
        }
        if (screenshotEvent != null) {
            this.logger.log(screenshotEvent, 0, this.packageName);
        }
        final ScreenshotAnimationController screenshotAnimationController = this.animationController;
        Animator animator = screenshotAnimationController.animator;
        if (animator != null) {
            animator.cancel();
        }
        ScreenshotShelfView screenshotShelfView = screenshotAnimationController.view;
        float fMax = 1.5f;
        if (f != null && Math.abs(f.floatValue()) >= 0.005f) {
            fMax = Math.max(1.5f, Math.abs(f.floatValue())) * Math.signum(f.floatValue());
        } else if (screenshotShelfView.getResources().getConfiguration().getLayoutDirection() == 0) {
            fMax = -1.5f;
        }
        float right = fMax < 0.0f ? screenshotAnimationController.actionContainer.getRight() * (-1.0f) : screenshotShelfView.getResources().getDisplayMetrics().widthPixels - screenshotAnimationController.actionContainer.getLeft();
        float translationX = right - screenshotShelfView.getTranslationX();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(screenshotShelfView.getTranslationX(), right);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getSwipeDismissAnimation$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                screenshotAnimationController.view.setTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                screenshotAnimationController.view.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
            }
        });
        valueAnimatorOfFloat.setDuration((long) Math.abs(translationX / fMax));
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getSwipeDismissAnimation$$inlined$doOnStart$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                ScreenshotViewModel screenshotViewModel = screenshotAnimationController.viewModel;
                screenshotViewModel._isAnimating.updateState(null, Boolean.TRUE);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator2) {
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getSwipeDismissAnimation$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                ScreenshotViewModel screenshotViewModel = screenshotAnimationController.viewModel;
                screenshotViewModel._isAnimating.updateState(null, Boolean.FALSE);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
            }
        });
        screenshotAnimationController.animator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy.requestDismissal.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                ScreenshotShelfViewProxy screenshotShelfViewProxy = ScreenshotShelfViewProxy.this;
                screenshotShelfViewProxy.isDismissing = false;
                ScreenshotViewCallback screenshotViewCallback = screenshotShelfViewProxy.callbacks;
                if (screenshotViewCallback != null) {
                    screenshotViewCallback.onDismiss();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                ScreenshotShelfViewProxy.this.isDismissing = true;
            }
        });
        valueAnimatorOfFloat.start();
    }

    public final void reset() {
        Animator animator = this.animationController.animator;
        if (animator != null) {
            animator.cancel();
        }
        ScreenshotViewModel screenshotViewModel = this.viewModel;
        screenshotViewModel._preview.setValue(null);
        screenshotViewModel._scrollingScrim.setValue(null);
        screenshotViewModel._badge.setValue(null);
        screenshotViewModel._previewAction.setValue(null);
        screenshotViewModel._actions.setValue(EmptyList.INSTANCE);
        screenshotViewModel._animationState.setValue(AnimationState.NOT_STARTED);
        screenshotViewModel._isAnimating.updateState(null, Boolean.FALSE);
        screenshotViewModel._scrollableRect.setValue(null);
    }

    public final void stopInputListening() {
        InputMonitorCompat inputMonitorCompat = this.inputMonitor;
        if (inputMonitorCompat != null) {
            inputMonitorCompat.dispose();
        }
        this.inputMonitor = null;
        InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = this.inputEventReceiver;
        if (inputChannelCompat$InputEventReceiver != null) {
            inputChannelCompat$InputEventReceiver.dispose();
        }
        this.inputEventReceiver = null;
    }
}
