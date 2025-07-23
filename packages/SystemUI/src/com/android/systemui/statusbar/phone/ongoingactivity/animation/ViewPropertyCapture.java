package com.android.systemui.statusbar.phone.ongoingactivity.animation;

import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.graphics.PointF;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$collapseAnimation$2;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ViewPropertyCapture extends Transition {
    public final View targetView;
    public ViewPropertyObserver viewPropertyObserver;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ViewPropertyObserver {
    }

    static {
        new Companion(null);
    }

    public ViewPropertyCapture(View view, ViewPropertyObserver viewPropertyObserver) {
        this.targetView = view;
        this.viewPropertyObserver = viewPropertyObserver;
    }

    @Override // android.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        View view;
        if (transitionValues == null || (view = transitionValues.view) == null || !view.equals(this.targetView)) {
            return;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(view.getLeft(), view.getRight(), "captureEndValues() left:", " right:", " top:"), view.getTop(), " bottom:", view.getBottom(), "ViewPropertyCapture");
        ViewPropertyObserver viewPropertyObserver = this.viewPropertyObserver;
        if (viewPropertyObserver != null) {
            int i = iArr[0];
            int i2 = iArr[1];
            final int right = ((view.getRight() - view.getLeft()) + i) - i;
            final int bottom = ((view.getBottom() - view.getTop()) + iArr[1]) - i2;
            Log.d("{OngoingExpandedPipController}", "onReceiveEndScreenViewBound");
            final float f = i;
            final float f2 = i2;
            final OngoingCardController ongoingCardController = ((OngoingCardController$collapseAnimation$2) viewPropertyObserver).this$0;
            ongoingCardController.getClass();
            Log.d("{OngoingExpandedPipController}", "postCollapseAnimation");
            ongoingCardController.mainUIHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$postCollapseAnimation$1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r4v10 */
                /* JADX WARN: Type inference failed for: r4v4 */
                /* JADX WARN: Type inference failed for: r4v5, types: [boolean, int] */
                @Override // java.lang.Runnable
                public final void run() {
                    int alpha;
                    Log.d("{OngoingExpandedPipController}", "mainUIHandler.post ");
                    final OngoingCardController ongoingCardController2 = OngoingCardController.this;
                    final CardStackView cardStackView = ongoingCardController2.mCardStackView;
                    final float f3 = f;
                    final float f4 = f2;
                    final int i3 = right;
                    final Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$postCollapseAnimation$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Log.d("{OngoingExpandedPipController}", "collapseAnimation.startRunnable");
                            final OngoingCardController ongoingCardController3 = OngoingCardController.this;
                            ongoingCardController3.mainUIHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.postCollapseAnimation.1.1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    OngoingCardController.this.onChangeCapsuleVisibility(0, null);
                                    OngoingCardController.this.mCardStackView.setVisibility(4);
                                }
                            }, 600L);
                            OngoingCardController ongoingCardController4 = OngoingCardController.this;
                            float f5 = f3;
                            float f6 = f4;
                            float f7 = i3;
                            View findViewById = ongoingCardController4.mExpandedView.findViewById(R.id.ongoing_card_background);
                            findViewById.getClass();
                            OngoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0 ongoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0 = new OngoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0(OngoingCardController.this, 1);
                            PointF cardStackLocationOnScreen = ongoingCardController4.getCardStackLocationOnScreen();
                            PointF pointF = new PointF();
                            pointF.x = f5;
                            pointF.y = f6;
                            if (ongoingCardController4.statusBarVisibility != 0) {
                                pointF.y = f6 - ongoingCardController4.indicatorGardenPresenter.cachedGardenModel.totalHeight;
                            }
                            int i4 = 0;
                            if (ongoingCardController4.isOrientationChanged) {
                                ongoingCardController4.isOrientationChanged = false;
                                ongoingCardController4.mCardStackView.setTranslationY(0.0f);
                            }
                            if (ongoingCardController4.mCardStackView.getLayoutDirection() == 1) {
                                cardStackLocationOnScreen.x = -cardStackLocationOnScreen.x;
                                pointF.x = (pointF.x - ongoingCardController4.mCardStackView.getWidth()) + f7;
                            }
                            float f8 = cardStackLocationOnScreen.x;
                            float f9 = cardStackLocationOnScreen.y + ongoingCardController4.indicatorGardenPresenter.cachedGardenModel.totalHeight;
                            float f10 = pointF.x;
                            float f11 = pointF.y;
                            CardStackView.Companion.getClass();
                            ongoingCardController4.startAnimation(findViewById, f8, f9, f10, f11, CardStackView.collapseRootInterpolator, 600L, ongoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0);
                            ArrayList arrayList = ongoingCardController4.onStateEventListeners;
                            int size = arrayList.size();
                            while (i4 < size) {
                                Object obj = arrayList.get(i4);
                                i4++;
                                CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl = ((OngoingActivityController$createCardController$2) obj).this$0.ongoingActivityListener;
                                if (ongoingActivityListenerImpl != null) {
                                    ongoingActivityListenerImpl.onNudgeClockRequired();
                                }
                            }
                        }
                    };
                    final OngoingCardController ongoingCardController3 = OngoingCardController.this;
                    final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$postCollapseAnimation$1.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            OngoingCardController.this.mExpandedView.setOnTouchListener(null);
                            OngoingCardController.this.mExpandedView.setOnKeyListener(null);
                            OngoingCardController.this.onAllowStateChanged(false);
                        }
                    };
                    int i4 = right;
                    int i5 = bottom;
                    int i6 = 1;
                    if (cardStackView.gutsDisplay) {
                        cardStackView.disableTopCardGuts(true);
                    }
                    cardStackView.stopLongPressChecker();
                    cardStackView.pendingWidth = i4;
                    cardStackView.pendingHeight = i5;
                    TransitionSet transitionSet = new TransitionSet();
                    transitionSet.addTransition(new ChangeBounds());
                    ?? r4 = 0;
                    transitionSet.setOrdering(0);
                    transitionSet.setDuration(600L);
                    transitionSet.setInterpolator((TimeInterpolator) CardStackView.collapseRootInterpolator);
                    transitionSet.addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$collapseAnimation$1
                        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                        public final void onTransitionCancel(Transition transition) {
                            runnable2.run();
                            super.onTransitionCancel(transition);
                        }

                        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                        public final void onTransitionEnd(Transition transition) {
                            runnable2.run();
                            cardStackView.isRunningCollapseAnimation = false;
                        }

                        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                        public final void onTransitionStart(Transition transition) {
                            Log.d("{OngoingActivityCardStackView}", "onTransitionStart ");
                            runnable.run();
                            cardStackView.isRunningCollapseAnimation = true;
                        }
                    });
                    cardStackView.sceneList.clear();
                    int childCount = cardStackView.getChildCount();
                    for (int i7 = 0; i7 < childCount; i7++) {
                        ViewGroup viewGroup = (ViewGroup) cardStackView.getChildAt(i7);
                        cardStackView.sceneList.add(new Pair(new Scene(viewGroup, viewGroup.findViewById(R.id.stack_pip_layout)), new Scene(viewGroup, viewGroup.findViewById(R.id.stack_pip_layout))));
                    }
                    ArrayList arrayList = cardStackView.sceneList;
                    int size = arrayList.size();
                    final int i8 = 0;
                    int i9 = 0;
                    while (i9 < size) {
                        Object obj = arrayList.get(i9);
                        i9++;
                        int i10 = i8 + 1;
                        if (i8 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                            throw null;
                        }
                        final Scene scene = (Scene) ((Pair) obj).getFirst();
                        cardStackView.getCardBg();
                        int chipBg = CardStackView.getChipBg(cardStackView.getTopViewIndex() - i6);
                        OngoingActivityDataHelper.INSTANCE.getClass();
                        CopyOnWriteArrayList copyOnWriteArrayList = OngoingActivityDataHelper.mOngoingActivityLists;
                        int i11 = (copyOnWriteArrayList.size() == 0 || OngoingActivityDataHelper.getDataByIndex(r4).mExpandedChipView == null) ? r4 : i6;
                        if (cardStackView.getChildCount() > i6 && i8 == cardStackView.getTopViewIndex() && i11 != 0 && (alpha = Color.alpha(chipBg)) < 255) {
                            chipBg = Color.argb((int) (alpha * 0.75f), Color.red(chipBg), Color.green(chipBg), Color.blue(chipBg));
                        }
                        cardStackView.colorTransition(chipBg, i8, r4);
                        final Ref$IntRef ref$IntRef = new Ref$IntRef();
                        ref$IntRef.element = cardStackView.pendingWidth;
                        if (cardStackView.getChildCount() > i6) {
                            ref$IntRef.element = cardStackView.pendingWidth - cardStackView.getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_layer_offset);
                        }
                        ViewGroup sceneRoot = scene.getSceneRoot();
                        if (sceneRoot != null && i8 != cardStackView.getTopViewIndex()) {
                            float cardInitialScale = CardStackView.getCardInitialScale(r4);
                            sceneRoot.setScaleY(cardInitialScale);
                            sceneRoot.setScaleX(cardInitialScale);
                            sceneRoot.setX(0.0f);
                            sceneRoot.setY(0.0f);
                        }
                        View findViewById = scene.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                        if (findViewById != null && (findViewById instanceof ViewGroup)) {
                            View findViewById2 = findViewById.findViewById(R.id.dummy_capsule_item_noti_expanded_info);
                            findViewById2.getClass();
                            FrameLayout frameLayout = (FrameLayout) findViewById2;
                            if (frameLayout.getChildCount() > 0) {
                                View childAt = frameLayout.getChildAt(r4);
                                if (!(childAt instanceof Chronometer) && (childAt instanceof TextView)) {
                                    OngoingActivityData dataByIndex = copyOnWriteArrayList.size() == 0 ? null : OngoingActivityDataHelper.getDataByIndex(r4);
                                    if (dataByIndex != null && dataByIndex.mIsMediaOngoingData) {
                                        CharSequence charSequence = dataByIndex.mExpandedChipText;
                                        if (!(charSequence != null ? charSequence.equals(((TextView) childAt).getText()) : false)) {
                                            TextView textView = (TextView) childAt;
                                            Log.d("{OngoingActivityCardStackView}", " change chip text " + ((Object) textView.getText()) + " -> " + ((Object) dataByIndex.mExpandedChipText));
                                            textView.setText(dataByIndex.mExpandedChipText);
                                        }
                                    }
                                }
                            }
                        }
                        View findViewById3 = scene.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                        findViewById3.getClass();
                        CardStackView.changeLayoutSize(findViewById3, ref$IntRef.element, cardStackView.pendingHeight);
                        scene.setEnterAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$collapseAnimation$2$3
                            @Override // java.lang.Runnable
                            public final void run() {
                                IndicatorScaleGardener.ScaleModel latestScaleModel;
                                ViewPropertyAnimator translationY;
                                ViewPropertyAnimator scaleX;
                                ViewPropertyAnimator scaleY;
                                ViewPropertyAnimator alpha2;
                                ViewPropertyAnimator duration;
                                ViewGroup viewGroup2 = (ViewGroup) scene.getSceneRoot().findViewById(R.id.stack_expand_contents);
                                if (viewGroup2 != null) {
                                    viewGroup2.setVisibility(4);
                                }
                                ViewGroup viewGroup3 = (ViewGroup) scene.getSceneRoot().findViewById(R.id.stack_pip_layout);
                                if (viewGroup3 != null) {
                                    int i12 = i8;
                                    CardStackView cardStackView2 = cardStackView;
                                    Ref$IntRef ref$IntRef2 = ref$IntRef;
                                    CardStackView.Companion companion = CardStackView.Companion;
                                    if (i12 != cardStackView2.getTopViewIndex()) {
                                        ViewPropertyAnimator animate = viewGroup3.animate();
                                        if (animate != null && (translationY = animate.translationY(0.0f)) != null && (scaleX = translationY.scaleX(1.0f)) != null && (scaleY = scaleX.scaleY(1.0f)) != null && (alpha2 = scaleY.alpha(0.4f)) != null && (duration = alpha2.setDuration(300L)) != null) {
                                            CardStackView.Companion.getClass();
                                            ViewPropertyAnimator interpolator = duration.setInterpolator(CardStackView.expandContentsAlphaInterpolator);
                                            if (interpolator != null) {
                                                interpolator.start();
                                            }
                                        }
                                        CardStackView.changeLayoutSize(viewGroup3, cardStackView2.pendingWidth, cardStackView2.pendingHeight);
                                    } else {
                                        CardStackView.changeLayoutSize(viewGroup3, ref$IntRef2.element, cardStackView2.pendingHeight);
                                    }
                                    viewGroup3.setElevation(0.0f);
                                }
                                View findViewById4 = scene.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                                findViewById4.getClass();
                                int i13 = i8;
                                CardStackView cardStackView3 = cardStackView;
                                CardStackView.Companion companion2 = CardStackView.Companion;
                                if (i13 == cardStackView3.getTopViewIndex()) {
                                    findViewById4.setVisibility(0);
                                } else {
                                    findViewById4.setVisibility(4);
                                }
                                SpringAnimation springAnimation = new SpringAnimation(findViewById4, DynamicAnimation.SCALE_X, 1.0f);
                                springAnimation.mSpring.setStiffness(200.0f);
                                springAnimation.mSpring.setDampingRatio(0.8131728f);
                                springAnimation.start();
                                SpringAnimation springAnimation2 = new SpringAnimation(findViewById4, DynamicAnimation.SCALE_Y, 1.0f);
                                springAnimation2.mSpring.setStiffness(200.0f);
                                springAnimation2.mSpring.setDampingRatio(0.8131728f);
                                springAnimation2.start();
                                CardStackView.changeLayoutMargin(findViewById4, 0);
                                View findViewById5 = scene.getSceneRoot().findViewById(R.id.dummy_capsule_item_top_layout);
                                findViewById5.getClass();
                                View findViewById6 = scene.getSceneRoot().findViewById(R.id.dummy_capsule_item_noti_expanded_info);
                                findViewById6.getClass();
                                View findViewById7 = scene.getSceneRoot().findViewById(R.id.dummy_capsule_remote_container);
                                findViewById7.getClass();
                                View findViewById8 = scene.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                                findViewById8.getClass();
                                CardStackView cardStackView4 = cardStackView;
                                IndicatorScaleGardener indicatorScaleGardener = cardStackView4.indicatorScaleGardener;
                                if (ref$IntRef.element <= MathKt__MathJVMKt.roundToInt(cardStackView.getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_min_width) * ((indicatorScaleGardener == null || (latestScaleModel = indicatorScaleGardener.getLatestScaleModel(cardStackView4.getContext())) == null) ? 1 : Float.valueOf(latestScaleModel.ratio)).floatValue())) {
                                    int dimensionPixelSize = cardStackView.getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_padding_side);
                                    findViewById5.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
                                    FrameLayout frameLayout2 = (FrameLayout) findViewById6;
                                    frameLayout2.removeAllViews();
                                    FrameLayout frameLayout3 = (FrameLayout) findViewById7;
                                    if (frameLayout3.getChildCount() > 0) {
                                        frameLayout3.removeAllViews();
                                        findViewById5.setVisibility(0);
                                    }
                                    frameLayout2.setVisibility(8);
                                }
                                ViewPropertyAnimator alpha3 = findViewById8.animate().alpha(1.0f);
                                CardStackView.Companion.getClass();
                                alpha3.setInterpolator(CardStackView.alphaInterpolator).setDuration(200L).start();
                            }
                        });
                        cardStackView.isRunningCollapseAnimation = true;
                        TransitionManager.go(scene, transitionSet);
                        Log.d("{OngoingActivityCardStackView}", "TransitionManager.go(sceneCollapse, transition) ");
                        i6 = 1;
                        i8 = i10;
                        r4 = 0;
                    }
                }
            });
        }
        this.viewPropertyObserver = null;
    }

    @Override // android.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        View view;
        if (transitionValues == null || (view = transitionValues.view) == null || !view.equals(this.targetView)) {
            return;
        }
        view.getLocationOnScreen(new int[2]);
        KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(view.getLeft(), view.getRight(), "captureStartValues() left:", " right:", " top:"), view.getTop(), " bottom:", view.getBottom(), "ViewPropertyCapture");
        if (this.viewPropertyObserver != null) {
            view.getRight();
            view.getLeft();
            view.getBottom();
            view.getTop();
        }
    }
}
