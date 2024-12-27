package com.android.systemui.util;

import android.R;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.window.OnBackInvokedDispatcher;
import com.android.app.animation.Interpolators;
import com.android.systemui.animation.back.BackAnimationSpec;
import com.android.systemui.animation.back.BackAnimationSpecKt$createFloatingSurfaceAnimationSpec$1;
import com.android.systemui.animation.back.BackTransformation;
import com.android.systemui.animation.back.OnBackAnimationCallbackExtensionKt;
import com.android.systemui.animation.back.OnBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$4;
import com.android.systemui.animation.back.ScalePivotPosition;
import com.android.systemui.animation.view.LaunchableFrameLayout;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class DialogKt {
    public static final Pair<LaunchableFrameLayout, View.OnLayoutChangeListener> maybeForceFullscreen(final Dialog dialog) {
        dialog.create();
        final Window window = dialog.getWindow();
        Intrinsics.checkNotNull(window);
        ViewGroup viewGroup = (ViewGroup) window.getDecorView();
        if (window.getAttributes().width == -1 && window.getAttributes().height == -1) {
            return null;
        }
        FrameLayout frameLayout = new FrameLayout(dialog.getContext());
        viewGroup.addView(frameLayout, 0, new FrameLayout.LayoutParams(-1, -1));
        final LaunchableFrameLayout launchableFrameLayout = new LaunchableFrameLayout(dialog.getContext());
        launchableFrameLayout.setBackground(viewGroup.getBackground());
        window.setBackgroundDrawableResource(R.color.transparent);
        frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.util.DialogKt$maybeForceFullscreen$1
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

    public static void registerAnimationOnBackInvoked$default(Dialog dialog, final View view, BackAnimationSpec backAnimationSpec, int i, Object obj) {
        if ((i & 2) != 0) {
            BackAnimationSpec.Companion companion = BackAnimationSpec.Companion;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.DialogKt$registerAnimationOnBackInvoked$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final DisplayMetrics invoke() {
                    return view.getResources().getDisplayMetrics();
                }
            };
            Interpolator interpolator = Interpolators.BACK_GESTURE;
            backAnimationSpec = new BackAnimationSpecKt$createFloatingSurfaceAnimationSpec$1(function0, 8.0f, 8.0f, 0.9f, interpolator, Interpolators.LINEAR, interpolator);
        }
        registerAnimationOnBackInvoked(dialog, view, backAnimationSpec);
    }

    public static final void registerAnimationOnBackInvoked(final Dialog dialog, final View view, BackAnimationSpec backAnimationSpec) {
        final OnBackInvokedDispatcher onBackInvokedDispatcher = dialog.getOnBackInvokedDispatcher();
        final OnBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$4 onBackAnimationCallbackFrom$default = OnBackAnimationCallbackExtensionKt.onBackAnimationCallbackFrom$default(backAnimationSpec, view.getResources().getDisplayMetrics(), new Function1() { // from class: com.android.systemui.util.DialogKt$registerAnimationOnBackInvoked$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((BackTransformation) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(BackTransformation backTransformation) {
                int width;
                float height;
                View view2 = view;
                float f = backTransformation.translateX;
                if (!Float.isInfinite(f) && !Float.isNaN(f)) {
                    view2.setTranslationX(backTransformation.translateX);
                }
                float f2 = backTransformation.translateY;
                if (!Float.isInfinite(f2) && !Float.isNaN(f2)) {
                    view2.setTranslationY(backTransformation.translateY);
                }
                ScalePivotPosition scalePivotPosition = backTransformation.scalePivotPosition;
                if (scalePivotPosition != null) {
                    int[] iArr = ScalePivotPosition.WhenMappings.$EnumSwitchMapping$0;
                    int i = iArr[scalePivotPosition.ordinal()];
                    if (i == 1) {
                        width = view2.getWidth();
                    } else {
                        if (i != 2) {
                            throw new NoWhenBranchMatchedException();
                        }
                        width = view2.getWidth();
                    }
                    float f3 = width / 2.0f;
                    int i2 = iArr[scalePivotPosition.ordinal()];
                    if (i2 == 1) {
                        height = view2.getHeight() / 2.0f;
                    } else {
                        if (i2 != 2) {
                            throw new NoWhenBranchMatchedException();
                        }
                        height = view2.getHeight();
                    }
                    view2.setPivotX(f3);
                    view2.setPivotY(height);
                }
                float f4 = backTransformation.scale;
                if (Float.isInfinite(f4) || Float.isNaN(f4)) {
                    return;
                }
                view2.setScaleX(backTransformation.scale);
                view2.setScaleY(backTransformation.scale);
            }
        }, new Function0() { // from class: com.android.systemui.util.DialogKt$registerAnimationOnBackInvoked$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Object invoke() {
                m2283invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: collision with other method in class */
            public final void m2283invoke() {
                dialog.dismiss();
            }
        });
        final int i = 0;
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.animation.back.OnBackAnimationCallbackExtensionKt$registerOnBackInvokedCallbackOnViewAttached$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view2) {
                onBackInvokedDispatcher.registerOnBackInvokedCallback(i, onBackAnimationCallbackFrom$default);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view2) {
                view.removeOnAttachStateChangeListener(this);
                onBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackAnimationCallbackFrom$default);
            }
        });
        if (view.isAttachedToWindow()) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(0, onBackAnimationCallbackFrom$default);
        }
    }
}
