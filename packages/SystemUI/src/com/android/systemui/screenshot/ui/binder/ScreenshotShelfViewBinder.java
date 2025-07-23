package com.android.systemui.screenshot.ui.binder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.screenshot.ScreenshotEvent;
import com.android.systemui.screenshot.ScreenshotShelfViewProxy$$ExternalSyntheticLambda0;
import com.android.systemui.screenshot.ScreenshotShelfViewProxy$$ExternalSyntheticLambda1;
import com.android.systemui.screenshot.ui.ScreenshotAnimationController;
import com.android.systemui.screenshot.ui.ScreenshotShelfView;
import com.android.systemui.screenshot.ui.SwipeGestureListener;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonViewModel;
import com.android.systemui.screenshot.ui.viewmodel.AnimationState;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenshotShelfViewBinder {
    public final ActionButtonViewBinder buttonViewBinder;

    public ScreenshotShelfViewBinder(ActionButtonViewBinder actionButtonViewBinder) {
        this.buttonViewBinder = actionButtonViewBinder;
    }

    public static final void access$setScreenshotBitmap(ScreenshotShelfViewBinder screenshotShelfViewBinder, ImageView imageView, Bitmap bitmap) {
        screenshotShelfViewBinder.getClass();
        imageView.setImageBitmap(bitmap);
        boolean z = bitmap.getWidth() < bitmap.getHeight();
        int dimensionPixelSize = imageView.getResources().getDimensionPixelSize(R.dimen.overlay_x_scale);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        if (z) {
            layoutParams.width = dimensionPixelSize;
            layoutParams.height = -2;
            imageView.setScaleType(ImageView.ScaleType.FIT_START);
        } else {
            layoutParams.width = -2;
            layoutParams.height = dimensionPixelSize;
            imageView.setScaleType(ImageView.ScaleType.FIT_END);
        }
        imageView.setLayoutParams(layoutParams);
        imageView.requestLayout();
    }

    public static final void access$updateActions(ScreenshotShelfViewBinder screenshotShelfViewBinder, List list, AnimationState animationState, ScreenshotShelfView screenshotShelfView, LayoutInflater layoutInflater) {
        screenshotShelfViewBinder.getClass();
        LinearLayout linearLayout = (LinearLayout) screenshotShelfView.requireViewById(R.id.screenshot_actions);
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            ActionButtonViewModel actionButtonViewModel = (ActionButtonViewModel) obj;
            if (actionButtonViewModel.visible && (animationState == AnimationState.ENTRANCE_COMPLETE || animationState == AnimationState.ENTRANCE_REVEAL || actionButtonViewModel.showDuringEntrance)) {
                arrayList.add(obj);
            }
        }
        if (!arrayList.isEmpty()) {
            screenshotShelfView.requireViewById(R.id.actions_container_background).setVisibility(0);
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            arrayList2.add(Integer.valueOf(((ActionButtonViewModel) obj2).id));
        }
        for (View view : SequencesKt___SequencesKt.toList(ConvenienceExtensionsKt.getChildren(linearLayout))) {
            if (!CollectionsKt___CollectionsKt.contains(arrayList2, view.getTag())) {
                linearLayout.removeView(view);
            }
        }
        Iterator it = arrayList.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            int i3 = i2 + 1;
            ActionButtonViewModel actionButtonViewModel2 = (ActionButtonViewModel) it.next();
            View childAt = linearLayout.getChildAt(i2);
            ActionButtonViewBinder actionButtonViewBinder = screenshotShelfViewBinder.buttonViewBinder;
            if (childAt != null) {
                int i4 = actionButtonViewModel2.id;
                Object tag = childAt.getTag();
                if ((tag instanceof Integer) && i4 == ((Number) tag).intValue()) {
                    actionButtonViewBinder.getClass();
                    ActionButtonViewBinder.bind(childAt, actionButtonViewModel2);
                    i2 = i3;
                }
            }
            View inflate = layoutInflater.inflate(R.layout.shelf_action_chip, (ViewGroup) linearLayout, false);
            linearLayout.addView(inflate, i2);
            inflate.getClass();
            actionButtonViewBinder.getClass();
            ActionButtonViewBinder.bind(inflate, actionButtonViewModel2);
            i2 = i3;
        }
    }

    public final void bind(ScreenshotShelfView screenshotShelfView, ScreenshotViewModel screenshotViewModel, final ScreenshotAnimationController screenshotAnimationController, LayoutInflater layoutInflater, final ScreenshotShelfViewProxy$$ExternalSyntheticLambda0 screenshotShelfViewProxy$$ExternalSyntheticLambda0, ScreenshotShelfViewProxy$$ExternalSyntheticLambda1 screenshotShelfViewProxy$$ExternalSyntheticLambda1) {
        screenshotShelfView.onTouchInterceptListener = new ScreenshotShelfViewBinder$$ExternalSyntheticLambda0(new SwipeGestureListener(screenshotShelfView, new ScreenshotShelfViewBinder$$ExternalSyntheticLambda0(screenshotShelfViewProxy$$ExternalSyntheticLambda0, 0), new Function0() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final ScreenshotAnimationController screenshotAnimationController2 = ScreenshotAnimationController.this;
                Animator animator = screenshotAnimationController2.animator;
                if (animator != null) {
                    animator.cancel();
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(screenshotAnimationController2.view.getTranslationX(), 0.0f);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getSwipeReturnAnimation$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ScreenshotAnimationController.this.view.setTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
                screenshotAnimationController2.animator = ofFloat;
                ofFloat.start();
                return Unit.INSTANCE;
            }
        }), 1);
        screenshotShelfView.userInteractionCallback = screenshotShelfViewProxy$$ExternalSyntheticLambda1;
        ImageView imageView = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_preview);
        ImageView imageView2 = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_preview_blur);
        View requireViewById = screenshotShelfView.requireViewById(R.id.screenshot_preview_border);
        imageView.setClipToOutline(true);
        imageView2.setClipToOutline(true);
        LinearLayout linearLayout = (LinearLayout) screenshotShelfView.requireViewById(R.id.screenshot_actions);
        View requireViewById2 = screenshotShelfView.requireViewById(R.id.screenshot_dismiss_button);
        requireViewById2.setVisibility(screenshotViewModel.accessibilityManager.isEnabled() ? 0 : 8);
        requireViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Function2.this.invoke(ScreenshotEvent.SCREENSHOT_EXPLICIT_DISMISSAL, null);
            }
        });
        ImageView imageView3 = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_scrolling_scrim);
        ImageView imageView4 = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_scrollable_preview);
        ImageView imageView5 = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_badge);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        RepeatWhenAttachedKt.repeatWhenAttached(screenshotShelfView, MainDispatcherLoader.dispatcher.immediate, new ScreenshotShelfViewBinder$bind$3(screenshotViewModel, this, imageView, imageView2, requireViewById, imageView3, imageView4, imageView5, linearLayout, screenshotShelfView, layoutInflater, null));
    }
}
