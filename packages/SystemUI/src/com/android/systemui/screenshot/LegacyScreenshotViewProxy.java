package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import android.app.Notification;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.screenshot.DraggableConstraintLayout;
import com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissHandler.AnonymousClass1;
import com.android.systemui.screenshot.ScreenshotController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LegacyScreenshotViewProxy implements ScreenshotViewProxy {
    public final UiEventLogger logger;
    public String packageName;
    public final View screenshotPreview;
    public final ScreenshotView view;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public LegacyScreenshotViewProxy(UiEventLogger uiEventLogger, FeatureFlags featureFlags, Context context, int i) {
        this.logger = uiEventLogger;
        ScreenshotView screenshotView = (ScreenshotView) LayoutInflater.from(context).inflate(R.layout.screenshot, (ViewGroup) null);
        this.view = screenshotView;
        this.packageName = "";
        screenshotView.mUiEventLogger = uiEventLogger;
        screenshotView.mDefaultDisplay = i;
        final Function1 function1 = new Function1() { // from class: com.android.systemui.screenshot.LegacyScreenshotViewProxy.1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LegacyScreenshotViewProxy.this.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
                return Unit.INSTANCE;
            }
        };
        final OnBackInvokedCallback onBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.screenshot.LegacyScreenshotViewProxy$addPredictiveBackListener$onBackInvokedCallback$1
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                Function1.this.invoke(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
            }
        };
        screenshotView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.screenshot.LegacyScreenshotViewProxy$addPredictiveBackListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                OnBackInvokedDispatcher findOnBackInvokedDispatcher = LegacyScreenshotViewProxy.this.view.findOnBackInvokedDispatcher();
                if (findOnBackInvokedDispatcher != null) {
                    findOnBackInvokedDispatcher.registerOnBackInvokedCallback(0, onBackInvokedCallback);
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                OnBackInvokedDispatcher findOnBackInvokedDispatcher = view.findOnBackInvokedDispatcher();
                if (findOnBackInvokedDispatcher != null) {
                    findOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
                }
            }
        });
        final Function1 function12 = new Function1() { // from class: com.android.systemui.screenshot.LegacyScreenshotViewProxy.2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LegacyScreenshotViewProxy.this.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
                return Unit.INSTANCE;
            }
        };
        screenshotView.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.screenshot.LegacyScreenshotViewProxy$setOnKeyListener$1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i2, KeyEvent keyEvent) {
                if (i2 != 4 && i2 != 111) {
                    return false;
                }
                Function1.this.invoke(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
                return true;
            }
        });
        screenshotView.getViewTreeObserver().addOnComputeInternalInsetsListener(screenshotView);
        this.screenshotPreview = screenshotView.mScreenshotPreview;
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void addQuickShareChip(Notification.Action action) {
        this.view.addQuickShareChip(action);
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void announceForAccessibility(String str) {
        this.view.announceForAccessibility(str);
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
        ValueAnimator valueAnimator = this.view.mScreenshotStatic.mSwipeDismissHandler.mDismissAnimation;
        return valueAnimator != null && valueAnimator.isRunning();
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void requestDismissal(ScreenshotEvent screenshotEvent) {
        ScreenshotView screenshotView = this.view;
        ValueAnimator valueAnimator = screenshotView.mScreenshotStatic.mSwipeDismissHandler.mDismissAnimation;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            if (screenshotEvent != null) {
                this.logger.log(screenshotEvent, 0, this.packageName);
            }
            DraggableConstraintLayout.SwipeDismissHandler swipeDismissHandler = screenshotView.mScreenshotStatic.mSwipeDismissHandler;
            ValueAnimator createSwipeDismissAnimation = swipeDismissHandler.createSwipeDismissAnimation(FloatingWindowUtil.dpToPx(swipeDismissHandler.mDisplayMetrics, 1.0f));
            swipeDismissHandler.mDismissAnimation = createSwipeDismissAnimation;
            createSwipeDismissAnimation.addListener(swipeDismissHandler.new AnonymousClass1());
            swipeDismissHandler.mDismissAnimation.start();
        }
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void reset() {
        this.view.reset();
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void setCallbacks(ScreenshotController.AnonymousClass3 anonymousClass3) {
        this.view.mCallbacks = anonymousClass3;
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void setChipIntents(ScreenshotController.SavedImageData savedImageData) {
        this.view.setChipIntents(savedImageData);
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void setPackageName(String str) {
        this.packageName = str;
        this.view.mPackageName = str;
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void stopInputListening() {
        this.view.stopInputListening();
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void updateOrientation(WindowInsets windowInsets) {
        this.view.updateOrientation(windowInsets);
    }

    @Override // com.android.systemui.screenshot.ScreenshotViewProxy
    public final void fadeForSharedTransition() {
    }
}
