package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.content.Context;
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
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ui.ScreenshotAnimationController;
import com.android.systemui.screenshot.ui.ScreenshotShelfView;
import com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder;
import com.android.systemui.screenshot.ui.viewmodel.AnimationState;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;

public final class ScreenshotShelfViewProxy implements ScreenshotViewProxy {
    public final ScreenshotAnimationController animationController;
    public ScreenshotController.AnonymousClass3 callbacks;
    public final int displayId;
    public InputChannelCompat$InputEventReceiver inputEventReceiver;
    public InputMonitorCompat inputMonitor;
    public boolean isDismissing;
    public final UiEventLogger logger;
    public String packageName;
    public final View screenshotPreview;
    public final ScreenshotShelfView view;
    public final ScreenshotViewModel viewModel;
    public final WindowManager windowManager;

    public ScreenshotShelfViewProxy(UiEventLogger uiEventLogger, ScreenshotViewModel screenshotViewModel, WindowManager windowManager, ScreenshotShelfViewBinder screenshotShelfViewBinder, ThumbnailObserver thumbnailObserver, Context context, int i) {
        this.logger = uiEventLogger;
        this.viewModel = screenshotViewModel;
        this.windowManager = windowManager;
        this.displayId = i;
        ScreenshotShelfView screenshotShelfView = (ScreenshotShelfView) LayoutInflater.from(context).inflate(R.layout.screenshot_shelf, (ViewGroup) null);
        this.view = screenshotShelfView;
        this.packageName = "";
        ScreenshotAnimationController screenshotAnimationController = new ScreenshotAnimationController(screenshotShelfView, screenshotViewModel);
        this.animationController = screenshotAnimationController;
        screenshotShelfViewBinder.bind(screenshotShelfView, screenshotViewModel, screenshotAnimationController, LayoutInflater.from(context), new Function2() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                ScreenshotShelfViewProxy.this.requestDismissal((ScreenshotEvent) obj, (Float) obj2);
                return Unit.INSTANCE;
            }
        }, new Function0() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy.2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ScreenshotController.AnonymousClass3 anonymousClass3 = ScreenshotShelfViewProxy.this.callbacks;
                if (anonymousClass3 != null) {
                    TimeoutHandler timeoutHandler = ScreenshotController.this.mScreenshotHandler;
                    timeoutHandler.removeMessages(2);
                    timeoutHandler.sendMessageDelayed(timeoutHandler.obtainMessage(2), ((AccessibilityManager) timeoutHandler.mContext.getSystemService("accessibility")).getRecommendedTimeoutMillis(timeoutHandler.mDefaultTimeout, 4));
                }
                return Unit.INSTANCE;
            }
        });
        screenshotShelfView.updateInsets(windowManager.getCurrentWindowMetrics().getWindowInsets());
        final Function1 function1 = new Function1() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy.3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ScreenshotShelfViewProxy.this.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER, null);
                return Unit.INSTANCE;
            }
        };
        final OnBackInvokedCallback onBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$addPredictiveBackListener$onBackInvokedCallback$1
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                int i2 = DebugLogger.$r8$clinit;
                Reflection.getOrCreateKotlinClass(ScreenshotShelfViewProxy.this.getClass()).getSimpleName();
                function1.invoke(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
            }
        };
        screenshotShelfView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$addPredictiveBackListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                int i2 = DebugLogger.$r8$clinit;
                Reflection.getOrCreateKotlinClass(ScreenshotShelfViewProxy$addPredictiveBackListener$1.class).getSimpleName();
                OnBackInvokedDispatcher findOnBackInvokedDispatcher = ScreenshotShelfViewProxy.this.view.findOnBackInvokedDispatcher();
                if (findOnBackInvokedDispatcher != null) {
                    findOnBackInvokedDispatcher.registerOnBackInvokedCallback(0, onBackInvokedCallback);
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                int i2 = DebugLogger.$r8$clinit;
                Reflection.getOrCreateKotlinClass(ScreenshotShelfViewProxy$addPredictiveBackListener$1.class).getSimpleName();
                OnBackInvokedDispatcher findOnBackInvokedDispatcher = view.findOnBackInvokedDispatcher();
                if (findOnBackInvokedDispatcher != null) {
                    findOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
                }
            }
        });
        final Function1 function12 = new Function1() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy.4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ScreenshotShelfViewProxy.this.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER, null);
                return Unit.INSTANCE;
            }
        };
        screenshotShelfView.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$setOnKeyListener$1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i2, KeyEvent keyEvent) {
                if (i2 != 4 && i2 != 111) {
                    return false;
                }
                int i3 = DebugLogger.$r8$clinit;
                Reflection.getOrCreateKotlinClass(ScreenshotShelfViewProxy$setOnKeyListener$1.class).getSimpleName();
                Function1.this.invoke(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
                return true;
            }
        });
        int i2 = DebugLogger.$r8$clinit;
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
        screenshotShelfView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy.7
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                final ScreenshotShelfViewProxy screenshotShelfViewProxy = ScreenshotShelfViewProxy.this;
                screenshotShelfViewProxy.stopInputListening();
                InputMonitorCompat inputMonitorCompat = new InputMonitorCompat("Screenshot", screenshotShelfViewProxy.displayId);
                screenshotShelfViewProxy.inputEventReceiver = inputMonitorCompat.getInputReceiver(Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$startInputListening$1$1
                    @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                    public final void onInputEvent(InputEvent inputEvent) {
                        ScreenshotController.AnonymousClass3 anonymousClass3;
                        if (inputEvent instanceof MotionEvent) {
                            MotionEvent motionEvent = (MotionEvent) inputEvent;
                            if (motionEvent.getActionMasked() == 0) {
                                ScreenshotShelfViewProxy screenshotShelfViewProxy2 = ScreenshotShelfViewProxy.this;
                                if (ScreenshotShelfViewProxy.access$getTouchRegion(screenshotShelfViewProxy2).contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY()) || (anonymousClass3 = screenshotShelfViewProxy2.callbacks) == null) {
                                    return;
                                }
                                anonymousClass3.onTouchOutside();
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
        int dpToPx = (int) FloatingWindowUtil.dpToPx(screenshotShelfView.displayMetrics, -12.0f);
        ImageView imageView = screenshotShelfView.screenshotPreview;
        if (imageView == null) {
            imageView = null;
        }
        screenshotShelfView.addInsetView(region, imageView, dpToPx);
        View view = screenshotShelfView.actionsContainerBackground;
        if (view == null) {
            view = null;
        }
        screenshotShelfView.addInsetView(region, view, dpToPx);
        View view2 = screenshotShelfView.dismissButton;
        screenshotShelfView.addInsetView(region, view2 != null ? view2 : null, dpToPx);
        View findViewById = screenshotShelfView.findViewById(R.id.screenshot_message_container);
        if (findViewById != null) {
            screenshotShelfView.addInsetView(region, findViewById, dpToPx);
        }
        Rect rect = new Rect(0, 0, insets.left, screenshotShelfView.displayMetrics.heightPixels);
        Region.Op op = Region.Op.UNION;
        region.op(rect, op);
        DisplayMetrics displayMetrics = screenshotShelfView.displayMetrics;
        int i = displayMetrics.widthPixels;
        rect.set(i - insets.right, 0, i, displayMetrics.heightPixels);
        region.op(rect, op);
        return region;
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void announceForAccessibility(String str) {
        this.view.announceForAccessibility(str);
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void fadeForSharedTransition() {
        final ScreenshotAnimationController screenshotAnimationController = this.animationController;
        Animator animator = screenshotAnimationController.animator;
        if (animator != null) {
            animator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$fadeForSharedTransition$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Iterator it = ScreenshotAnimationController.this.fadeUI.iterator();
                while (it.hasNext()) {
                    ((View) it.next()).setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            }
        });
        screenshotAnimationController.animator = ofFloat;
        ofFloat.start();
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final View getScreenshotPreview() {
        return this.screenshotPreview;
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final ViewGroup getView() {
        return this.view;
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final boolean isAttachedToWindow() {
        return this.view.isAttachedToWindow();
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final boolean isDismissing() {
        return this.isDismissing;
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void requestDismissal(ScreenshotEvent screenshotEvent) {
        requestDismissal(screenshotEvent, null);
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
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

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void setCallbacks(ScreenshotController.AnonymousClass3 anonymousClass3) {
        this.callbacks = anonymousClass3;
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void setPackageName(String str) {
        this.packageName = str;
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
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

    public final void requestDismissal(ScreenshotEvent screenshotEvent, Float f) {
        int i = DebugLogger.$r8$clinit;
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
        float f2 = 1.5f;
        if (f != null) {
            f2 = Math.max(1.5f, Math.abs(f.floatValue())) * Math.signum(f.floatValue());
        } else if (screenshotShelfView.getResources().getConfiguration().getLayoutDirection() == 0) {
            f2 = -1.5f;
        }
        float right = f2 < 0.0f ? screenshotAnimationController.actionContainer.getRight() * (-1.0f) : screenshotShelfView.getResources().getDisplayMetrics().widthPixels - screenshotAnimationController.actionContainer.getLeft();
        float translationX = right - screenshotShelfView.getTranslationX();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(screenshotShelfView.getTranslationX(), right);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getSwipeDismissAnimation$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScreenshotAnimationController.this.view.setTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                ScreenshotAnimationController.this.view.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
            }
        });
        ofFloat.setDuration((long) Math.abs(translationX / f2));
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getSwipeDismissAnimation$$inlined$doOnStart$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                ScreenshotViewModel screenshotViewModel = ScreenshotAnimationController.this.viewModel;
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
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getSwipeDismissAnimation$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                ScreenshotViewModel screenshotViewModel = ScreenshotAnimationController.this.viewModel;
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
        screenshotAnimationController.animator = ofFloat;
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$requestDismissal$4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                ScreenshotShelfViewProxy screenshotShelfViewProxy = ScreenshotShelfViewProxy.this;
                screenshotShelfViewProxy.isDismissing = false;
                ScreenshotController.AnonymousClass3 anonymousClass3 = screenshotShelfViewProxy.callbacks;
                if (anonymousClass3 != null) {
                    ScreenshotController.this.finishDismiss();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                ScreenshotShelfViewProxy.this.isDismissing = true;
            }
        });
        ofFloat.start();
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void addQuickShareChip(Notification.Action action) {
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void setChipIntents(ScreenshotController.SavedImageData savedImageData) {
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void updateOrientation(WindowInsets windowInsets) {
    }
}
