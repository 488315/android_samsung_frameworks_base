package com.android.systemui.screenshot.ui.binder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.screenshot.FloatingWindowUtil;
import com.android.systemui.screenshot.ScreenshotEvent;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(Integer.valueOf(((ActionButtonViewModel) it.next()).id));
        }
        for (View view : SequencesKt___SequencesKt.toList(ConvenienceExtensionsKt.getChildren(linearLayout))) {
            if (!CollectionsKt___CollectionsKt.contains(arrayList2, view.getTag())) {
                linearLayout.removeView(view);
            }
        }
        Iterator it2 = arrayList.iterator();
        int i = 0;
        while (it2.hasNext()) {
            int i2 = i + 1;
            ActionButtonViewModel actionButtonViewModel2 = (ActionButtonViewModel) it2.next();
            View childAt = linearLayout.getChildAt(i);
            ActionButtonViewBinder actionButtonViewBinder = screenshotShelfViewBinder.buttonViewBinder;
            if (childAt != null) {
                int i3 = actionButtonViewModel2.id;
                Object tag = childAt.getTag();
                if ((tag instanceof Integer) && i3 == ((Number) tag).intValue()) {
                    actionButtonViewBinder.getClass();
                    ActionButtonViewBinder.bind(childAt, actionButtonViewModel2);
                    i = i2;
                }
            }
            View inflate = layoutInflater.inflate(R.layout.shelf_action_chip, (ViewGroup) linearLayout, false);
            linearLayout.addView(inflate, i);
            Intrinsics.checkNotNull(inflate);
            actionButtonViewBinder.getClass();
            ActionButtonViewBinder.bind(inflate, actionButtonViewModel2);
            i = i2;
        }
    }

    public final void bind(ScreenshotShelfView screenshotShelfView, ScreenshotViewModel screenshotViewModel, final ScreenshotAnimationController screenshotAnimationController, LayoutInflater layoutInflater, final Function2 function2, Function0 function0) {
        final SwipeGestureListener swipeGestureListener = new SwipeGestureListener(screenshotShelfView, new Function1() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$swipeGestureListener$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Function2.this.invoke(ScreenshotEvent.SCREENSHOT_SWIPE_DISMISSED, (Float) obj);
                return Unit.INSTANCE;
            }
        }, new Function0() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$swipeGestureListener$2
            {
                super(0);
            }

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
        });
        screenshotShelfView.onTouchInterceptListener = new Function1() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                boolean z;
                MotionEvent motionEvent = (MotionEvent) obj;
                SwipeGestureListener swipeGestureListener2 = SwipeGestureListener.this;
                motionEvent.offsetLocation(swipeGestureListener2.view.getTranslationX(), 0.0f);
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked != 0) {
                    z = true;
                    if (actionMasked == 1) {
                        swipeGestureListener2.velocityTracker.computeCurrentVelocity(1);
                        float xVelocity = swipeGestureListener2.velocityTracker.getXVelocity();
                        float abs = Math.abs(xVelocity);
                        float dpToPx = FloatingWindowUtil.dpToPx(swipeGestureListener2.displayMetrics, 0.8f);
                        Function1 function1 = swipeGestureListener2.onDismiss;
                        if (abs > dpToPx) {
                            function1.invoke(Float.valueOf(xVelocity));
                        } else if (Math.abs(swipeGestureListener2.view.getTranslationX()) > FloatingWindowUtil.dpToPx(swipeGestureListener2.displayMetrics, 80.0f)) {
                            function1.invoke(Float.valueOf(xVelocity));
                        } else {
                            swipeGestureListener2.velocityTracker.clear();
                            swipeGestureListener2.onCancel.invoke();
                        }
                        return Boolean.valueOf(z);
                    }
                    if (actionMasked == 2) {
                        swipeGestureListener2.velocityTracker.addMovement(motionEvent);
                        swipeGestureListener2.view.setTranslationX(motionEvent.getRawX() - swipeGestureListener2.startX);
                    }
                } else {
                    swipeGestureListener2.velocityTracker.addMovement(motionEvent);
                    swipeGestureListener2.startX = motionEvent.getRawX();
                }
                z = false;
                return Boolean.valueOf(z);
            }
        };
        screenshotShelfView.userInteractionCallback = function0;
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
        RepeatWhenAttachedKt.repeatWhenAttached(screenshotShelfView, MainDispatcherLoader.dispatcher.getImmediate(), new ScreenshotShelfViewBinder$bind$3(screenshotViewModel, this, imageView, imageView2, requireViewById, imageView3, imageView4, imageView5, linearLayout, screenshotShelfView, layoutInflater, null));
    }
}
