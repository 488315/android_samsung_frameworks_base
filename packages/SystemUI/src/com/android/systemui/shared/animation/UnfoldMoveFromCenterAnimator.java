package com.android.systemui.shared.animation;

import android.graphics.Point;
import android.view.View;
import android.view.WindowManager;
import com.android.systemui.statusbar.phone.StatusBarMoveFromCenterAnimationController;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class UnfoldMoveFromCenterAnimator implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public final AlphaProvider alphaProvider;
    public final List animatedViews;
    public boolean isVerticalFold;
    public float lastAnimationProgress;
    public final Point screenSize;
    public final TranslationApplier translationApplier;
    public final ViewCenterProvider viewCenterProvider;
    public final WindowManager windowManager;

    public interface AlphaProvider {
    }

    public interface TranslationApplier {
    }

    public interface ViewCenterProvider {
        default void getViewCenter(View view, Point point) {
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            point.x = (view.getWidth() / 2) + i;
            point.y = (view.getHeight() / 2) + i2;
        }
    }

    public UnfoldMoveFromCenterAnimator(WindowManager windowManager) {
        this(windowManager, null, null, null, 14, null);
    }

    public static void updateDisplayProperties$default(UnfoldMoveFromCenterAnimator unfoldMoveFromCenterAnimator) {
        int rotation = unfoldMoveFromCenterAnimator.windowManager.getDefaultDisplay().getRotation();
        unfoldMoveFromCenterAnimator.windowManager.getDefaultDisplay().getSize(unfoldMoveFromCenterAnimator.screenSize);
        unfoldMoveFromCenterAnimator.isVerticalFold = rotation == 0 || rotation == 2;
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionProgress(float f) {
        View view;
        for (AnimatedView animatedView : this.animatedViews) {
            View view2 = (View) animatedView.view.get();
            if (view2 != null) {
                float f2 = 1 - f;
                float f3 = animatedView.startTranslationX * f2;
                float f4 = animatedView.startTranslationY * f2;
                this.translationApplier.getClass();
                view2.setTranslationX(f3);
                view2.setTranslationY(f4);
            }
            AlphaProvider alphaProvider = this.alphaProvider;
            if (alphaProvider != null && (view = (View) animatedView.view.get()) != null) {
                view.setAlpha(Intrinsics.areEqual(((StatusBarMoveFromCenterAnimationController.StatusBarIconsAlphaProvider) alphaProvider).this$0.isOnHomeActivity, Boolean.TRUE) ? 1.0f : Math.max(0.0f, (f - 0.75f) / 0.25f));
            }
        }
        this.lastAnimationProgress = f;
    }

    public final void updateAnimatedView(AnimatedView animatedView, View view) {
        Point point = new Point();
        this.viewCenterProvider.getViewCenter(view, point);
        int i = point.x;
        int i2 = point.y;
        if (this.isVerticalFold) {
            animatedView.startTranslationX = ((this.screenSize.x / 2) - i) * 0.08f;
            animatedView.startTranslationY = 0.0f;
        } else {
            int i3 = (this.screenSize.y / 2) - i2;
            animatedView.startTranslationX = 0.0f;
            animatedView.startTranslationY = i3 * 0.08f;
        }
    }

    public UnfoldMoveFromCenterAnimator(WindowManager windowManager, TranslationApplier translationApplier) {
        this(windowManager, translationApplier, null, null, 12, null);
    }

    public UnfoldMoveFromCenterAnimator(WindowManager windowManager, TranslationApplier translationApplier, ViewCenterProvider viewCenterProvider) {
        this(windowManager, translationApplier, viewCenterProvider, null, 8, null);
    }

    public final class AnimatedView {
        public float startTranslationX;
        public float startTranslationY;
        public final WeakReference view;

        public AnimatedView(WeakReference<View> weakReference, float f, float f2) {
            this.view = weakReference;
            this.startTranslationX = f;
            this.startTranslationY = f2;
        }

        public /* synthetic */ AnimatedView(WeakReference weakReference, float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(weakReference, (i & 2) != 0 ? 0.0f : f, (i & 4) != 0 ? 0.0f : f2);
        }
    }

    public UnfoldMoveFromCenterAnimator(WindowManager windowManager, TranslationApplier translationApplier, ViewCenterProvider viewCenterProvider, AlphaProvider alphaProvider) {
        this.windowManager = windowManager;
        this.translationApplier = translationApplier;
        this.viewCenterProvider = viewCenterProvider;
        this.alphaProvider = alphaProvider;
        this.screenSize = new Point();
        this.animatedViews = new ArrayList();
        this.lastAnimationProgress = 1.0f;
    }

    public /* synthetic */ UnfoldMoveFromCenterAnimator(WindowManager windowManager, TranslationApplier translationApplier, ViewCenterProvider viewCenterProvider, AlphaProvider alphaProvider, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(windowManager, (i & 2) != 0 ? new TranslationApplier() { // from class: com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator.1
        } : translationApplier, (i & 4) != 0 ? new ViewCenterProvider() { // from class: com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator.2
        } : viewCenterProvider, (i & 8) != 0 ? null : alphaProvider);
    }
}
