package com.android.systemui.util;

import android.R;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.window.BackEvent;
import android.window.OnBackInvokedDispatcher;
import com.android.app.animation.Interpolators;
import com.android.systemui.animation.back.BackAnimationSpec;
import com.android.systemui.animation.back.BackAnimationSpecKt$createFloatingSurfaceAnimationSpec$1;
import com.android.systemui.animation.back.BackTransformation;
import com.android.systemui.animation.back.FlingOnBackAnimationCallback;
import com.android.systemui.animation.back.OnBackAnimationCallbackExtensionKt$$ExternalSyntheticLambda0;
import com.android.systemui.animation.back.OnBackAnimationCallbackExtensionKt$$ExternalSyntheticLambda1;
import com.android.systemui.animation.back.ScalePivotPosition;
import com.android.systemui.animation.view.LaunchableFrameLayout;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final class DialogKt {
    public static final Pair<LaunchableFrameLayout, View.OnLayoutChangeListener> maybeForceFullscreen(final Dialog dialog) {
        dialog.create();
        final Window window = dialog.getWindow();
        window.getClass();
        ViewGroup viewGroup = (ViewGroup) window.getDecorView();
        if (window.getAttributes().width == -1 && window.getAttributes().height == -1) {
            return null;
        }
        FrameLayout frameLayout = new FrameLayout(dialog.getContext());
        viewGroup.addView(frameLayout, 0, new FrameLayout.LayoutParams(-1, -1));
        final LaunchableFrameLayout launchableFrameLayout = new LaunchableFrameLayout(dialog.getContext());
        launchableFrameLayout.setBackground(viewGroup.getBackground());
        window.setBackgroundDrawableResource(R.color.transparent);
        frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.util.DialogKt.maybeForceFullscreen.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
        launchableFrameLayout.setClickable(true);
        frameLayout.setImportantForAccessibility(2);
        launchableFrameLayout.setImportantForAccessibility(2);
        frameLayout.addView(launchableFrameLayout, new FrameLayout.LayoutParams(window.getAttributes().width, window.getAttributes().height, window.getAttributes().gravity));
        int childCount = viewGroup.getChildCount();
        for (int i = 1; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(1);
            viewGroup.removeViewAt(1);
            launchableFrameLayout.addView(childAt);
        }
        window.setLayout(-1, -1);
        View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.util.DialogKt$maybeForceFullscreen$decorViewLayoutListener$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                if (window.getAttributes().width == -1 && window.getAttributes().height == -1) {
                    return;
                }
                ViewGroup.LayoutParams layoutParams = launchableFrameLayout.getLayoutParams();
                layoutParams.width = window.getAttributes().width;
                layoutParams.height = window.getAttributes().height;
                launchableFrameLayout.setLayoutParams(layoutParams);
                window.setLayout(-1, -1);
            }
        };
        viewGroup.addOnLayoutChangeListener(onLayoutChangeListener);
        return new Pair<>(launchableFrameLayout, onLayoutChangeListener);
    }

    public static final void registerAnimationOnBackInvoked(Dialog dialog, View view) {
        registerAnimationOnBackInvoked$default(dialog, view, null, 2, null);
    }

    public static void registerAnimationOnBackInvoked$default(Dialog dialog, View view, BackAnimationSpec backAnimationSpec, int i, Object obj) {
        if ((i & 2) != 0) {
            BackAnimationSpec.Companion companion = BackAnimationSpec.Companion;
            DialogKt$$ExternalSyntheticLambda1 dialogKt$$ExternalSyntheticLambda1 = new DialogKt$$ExternalSyntheticLambda1(view);
            Interpolator interpolator = Interpolators.BACK_GESTURE;
            backAnimationSpec = new BackAnimationSpecKt$createFloatingSurfaceAnimationSpec$1(dialogKt$$ExternalSyntheticLambda1, 8.0f, 8.0f, 0.9f, interpolator, Interpolators.LINEAR, interpolator);
        }
        registerAnimationOnBackInvoked(dialog, view, backAnimationSpec);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DisplayMetrics registerAnimationOnBackInvoked$lambda$0(View view) {
        return view.getResources().getDisplayMetrics();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit registerAnimationOnBackInvoked$lambda$1(View view, BackTransformation backTransformation) {
        float height;
        float f = backTransformation.translateX;
        if (!Float.isInfinite(f) && !Float.isNaN(f)) {
            view.setTranslationX(backTransformation.translateX);
        }
        float f2 = backTransformation.translateY;
        if (!Float.isInfinite(f2) && !Float.isNaN(f2)) {
            view.setTranslationY(backTransformation.translateY);
        }
        ScalePivotPosition scalePivotPosition = backTransformation.scalePivotPosition;
        if (scalePivotPosition != null) {
            int[] iArr = ScalePivotPosition.WhenMappings.$EnumSwitchMapping$0;
            int i = iArr[scalePivotPosition.ordinal()];
            if (i != 1 && i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            int width = view.getWidth();
            float f3 = width / 2.0f;
            int i2 = iArr[scalePivotPosition.ordinal()];
            if (i2 == 1) {
                height = view.getHeight() / 2.0f;
            } else {
                if (i2 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                height = view.getHeight();
            }
            view.setPivotX(f3);
            view.setPivotY(height);
        }
        float f4 = backTransformation.scale;
        if (!Float.isInfinite(f4) && !Float.isNaN(f4)) {
            view.setScaleX(backTransformation.scale);
            view.setScaleY(backTransformation.scale);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit registerAnimationOnBackInvoked$lambda$2(Dialog dialog) {
        dialog.dismiss();
        return Unit.INSTANCE;
    }

    public static final void registerAnimationOnBackInvoked(Dialog dialog, final View view, final BackAnimationSpec backAnimationSpec) {
        final OnBackInvokedDispatcher onBackInvokedDispatcher = dialog.getOnBackInvokedDispatcher();
        final DisplayMetrics displayMetrics = view.getResources().getDisplayMetrics();
        final Function1 function1 = new Function1() { // from class: com.android.systemui.util.DialogKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return DialogKt.registerAnimationOnBackInvoked$lambda$1(view, (BackTransformation) obj);
            }
        };
        final DialogKt$$ExternalSyntheticLambda1 dialogKt$$ExternalSyntheticLambda1 = new DialogKt$$ExternalSyntheticLambda1(dialog);
        final OnBackAnimationCallbackExtensionKt$$ExternalSyntheticLambda0 onBackAnimationCallbackExtensionKt$$ExternalSyntheticLambda0 = new OnBackAnimationCallbackExtensionKt$$ExternalSyntheticLambda0();
        final OnBackAnimationCallbackExtensionKt$$ExternalSyntheticLambda1 onBackAnimationCallbackExtensionKt$$ExternalSyntheticLambda1 = new OnBackAnimationCallbackExtensionKt$$ExternalSyntheticLambda1();
        final Interpolator interpolator = Interpolators.LINEAR;
        final FlingOnBackAnimationCallback flingOnBackAnimationCallback = new FlingOnBackAnimationCallback(displayMetrics, backAnimationSpec, function1, dialogKt$$ExternalSyntheticLambda1, onBackAnimationCallbackExtensionKt$$ExternalSyntheticLambda1, interpolator) { // from class: com.android.systemui.animation.back.OnBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$4
            public final /* synthetic */ BackAnimationSpec $backAnimationSpec;
            public final /* synthetic */ DisplayMetrics $displayMetrics;
            public final /* synthetic */ Function0 $onBackCancelled;
            public final /* synthetic */ Function0 $onBackInvoked;
            public final /* synthetic */ Function1 $onBackProgressed;
            public float initialY;
            public final BackTransformation lastTransformation;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(interpolator);
                interpolator.getClass();
                this.lastTransformation = new BackTransformation(0.0f, 0.0f, 0.0f, null, 15, null);
            }

            @Override // com.android.systemui.animation.back.FlingOnBackAnimationCallback
            public final void onBackCancelledCompat() {
                this.$onBackCancelled.invoke();
            }

            @Override // com.android.systemui.animation.back.FlingOnBackAnimationCallback
            public final void onBackInvokedCompat() {
                this.$onBackInvoked.invoke();
            }

            @Override // com.android.systemui.animation.back.FlingOnBackAnimationCallback
            public final void onBackProgressedCompat(BackEvent backEvent) {
                this.$backAnimationSpec.getBackTransformation(backEvent, (backEvent.getTouchY() - this.initialY) / this.$displayMetrics.heightPixels, this.lastTransformation);
                this.$onBackProgressed.mo781invoke(this.lastTransformation);
            }

            @Override // com.android.systemui.animation.back.FlingOnBackAnimationCallback
            public final void onBackStartedCompat(BackEvent backEvent) {
                this.initialY = backEvent.getTouchY();
                this.$onBackStarted.mo781invoke(backEvent);
            }
        };
        final int i = 0;
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.animation.back.OnBackAnimationCallbackExtensionKt$registerOnBackInvokedCallbackOnViewAttached$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view2) {
                onBackInvokedDispatcher.registerOnBackInvokedCallback(i, flingOnBackAnimationCallback);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view2) {
                view.removeOnAttachStateChangeListener(this);
                onBackInvokedDispatcher.unregisterOnBackInvokedCallback(flingOnBackAnimationCallback);
            }
        });
        if (view.isAttachedToWindow()) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(0, flingOnBackAnimationCallback);
        }
    }
}
