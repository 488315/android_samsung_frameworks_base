package com.android.systemui.statusbar.phone.ongoingactivity.animation;

import android.animation.TimeInterpolator;
import android.content.res.Resources;
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
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes3.dex */
public final class ViewPropertyCapture extends Transition {
    public final View targetView;
    public ViewPropertyObserver viewPropertyObserver;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
            final OngoingCardController ongoingCardController = OngoingCardController.this;
            ongoingCardController.getClass();
            Log.d("{OngoingExpandedPipController}", "postCollapseAnimation");
            ongoingCardController.mainUIHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$postCollapseAnimation$1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r4v10 */
                /* JADX WARN: Type inference failed for: r4v4 */
                /* JADX WARN: Type inference failed for: r4v5, types: [boolean, int] */
                @Override // java.lang.Runnable
                public final void run() throws Exception {
                    IndicatorScaleGardener.ScaleModel latestScaleModel;
                    int iAlpha;
                    Log.d("{OngoingExpandedPipController}", "mainUIHandler.post ");
                    final OngoingCardController ongoingCardController2 = ongoingCardController;
                    final CardStackView cardStackView = ongoingCardController2.mCardStackView;
                    final float f3 = f;
                    final float f4 = f2;
                    final int i3 = right;
                    final Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$postCollapseAnimation$1.1
                        @Override // java.lang.Runnable
                        public final void run() throws Resources.NotFoundException {
                            Log.d("{OngoingExpandedPipController}", "collapseAnimation.startRunnable");
                            final OngoingCardController ongoingCardController3 = ongoingCardController2;
                            ongoingCardController3.mainUIHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController.postCollapseAnimation.1.1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ongoingCardController3.onChangeCapsuleVisibility(0, null);
                                    ongoingCardController3.mCardStackView.setVisibility(4);
                                }
                            }, 600L);
                            OngoingCardController ongoingCardController4 = ongoingCardController2;
                            float f5 = f3;
                            float f6 = f4;
                            float f7 = i3;
                            View viewFindViewById = ongoingCardController4.mExpandedView.findViewById(R.id.ongoing_card_background);
                            viewFindViewById.getClass();
                            OngoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0 ongoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0 = new OngoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0(ongoingCardController2, 1);
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
                            ongoingCardController4.startAnimation(viewFindViewById, f8, f9, f10, f11, CardStackView.collapseRootInterpolator, 600L, ongoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0);
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
                    final OngoingCardController ongoingCardController3 = ongoingCardController;
                    final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$postCollapseAnimation$1.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ongoingCardController3.mExpandedView.setOnTouchListener(null);
                            ongoingCardController3.mExpandedView.setOnKeyListener(null);
                            ongoingCardController3.onAllowStateChanged(false);
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
                        if (cardStackView.getChildCount() > i6 && i8 == cardStackView.getTopViewIndex() && i11 != 0 && (iAlpha = Color.alpha(chipBg)) < 255) {
                            chipBg = Color.argb((int) (iAlpha * 0.75f), Color.red(chipBg), Color.green(chipBg), Color.blue(chipBg));
                        }
                        cardStackView.colorTransition(chipBg, i8, r4);
                        final Ref$IntRef ref$IntRef = new Ref$IntRef();
                        ref$IntRef.element = cardStackView.pendingWidth;
                        if (cardStackView.getChildCount() > i6) {
                            IndicatorScaleGardener indicatorScaleGardener = cardStackView.indicatorScaleGardener;
                            ref$IntRef.element = cardStackView.pendingWidth - ((int) Math.ceil(cardStackView.getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_layer_offset) * ((indicatorScaleGardener == null || (latestScaleModel = indicatorScaleGardener.getLatestScaleModel(cardStackView.getContext())) == null) ? 1.0f : latestScaleModel.ratio)));
                        }
                        ViewGroup sceneRoot = scene.getSceneRoot();
                        if (sceneRoot != null && i8 != cardStackView.getTopViewIndex()) {
                            float cardInitialScale = CardStackView.getCardInitialScale(r4);
                            sceneRoot.setScaleY(cardInitialScale);
                            sceneRoot.setScaleX(cardInitialScale);
                            sceneRoot.setX(0.0f);
                            sceneRoot.setY(0.0f);
                        }
                        View viewFindViewById = scene.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                        if (viewFindViewById != null && (viewFindViewById instanceof ViewGroup)) {
                            View viewFindViewById2 = viewFindViewById.findViewById(R.id.dummy_capsule_item_noti_expanded_info);
                            viewFindViewById2.getClass();
                            FrameLayout frameLayout = (FrameLayout) viewFindViewById2;
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
                        View viewFindViewById3 = scene.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                        viewFindViewById3.getClass();
                        CardStackView.changeLayoutSize(viewFindViewById3, ref$IntRef.element, cardStackView.pendingHeight);
                        scene.setEnterAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$collapseAnimation$2$3
                            @Override // java.lang.Runnable
                            public final void run() throws Resources.NotFoundException {
                                IndicatorScaleGardener.ScaleModel latestScaleModel2;
                                ViewPropertyAnimator viewPropertyAnimatorTranslationY;
                                ViewPropertyAnimator viewPropertyAnimatorScaleX;
                                ViewPropertyAnimator viewPropertyAnimatorScaleY;
                                ViewPropertyAnimator viewPropertyAnimatorAlpha;
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
                                        ViewPropertyAnimator viewPropertyAnimatorAnimate = viewGroup3.animate();
                                        if (viewPropertyAnimatorAnimate != null && (viewPropertyAnimatorTranslationY = viewPropertyAnimatorAnimate.translationY(0.0f)) != null && (viewPropertyAnimatorScaleX = viewPropertyAnimatorTranslationY.scaleX(1.0f)) != null && (viewPropertyAnimatorScaleY = viewPropertyAnimatorScaleX.scaleY(1.0f)) != null && (viewPropertyAnimatorAlpha = viewPropertyAnimatorScaleY.alpha(0.4f)) != null && (duration = viewPropertyAnimatorAlpha.setDuration(300L)) != null) {
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
                                View viewFindViewById4 = scene.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                                viewFindViewById4.getClass();
                                int i13 = i8;
                                CardStackView cardStackView3 = cardStackView;
                                CardStackView.Companion companion2 = CardStackView.Companion;
                                if (i13 == cardStackView3.getTopViewIndex()) {
                                    viewFindViewById4.setVisibility(0);
                                } else {
                                    viewFindViewById4.setVisibility(4);
                                }
                                SpringAnimation springAnimation = new SpringAnimation(viewFindViewById4, DynamicAnimation.SCALE_X, 1.0f);
                                springAnimation.mSpring.setStiffness(200.0f);
                                springAnimation.mSpring.setDampingRatio(0.8131728f);
                                springAnimation.start();
                                SpringAnimation springAnimation2 = new SpringAnimation(viewFindViewById4, DynamicAnimation.SCALE_Y, 1.0f);
                                springAnimation2.mSpring.setStiffness(200.0f);
                                springAnimation2.mSpring.setDampingRatio(0.8131728f);
                                springAnimation2.start();
                                CardStackView.changeLayoutMargin(viewFindViewById4, 0);
                                View viewFindViewById5 = scene.getSceneRoot().findViewById(R.id.dummy_capsule_item_top_layout);
                                viewFindViewById5.getClass();
                                View viewFindViewById6 = scene.getSceneRoot().findViewById(R.id.dummy_capsule_item_noti_expanded_info);
                                viewFindViewById6.getClass();
                                View viewFindViewById7 = scene.getSceneRoot().findViewById(R.id.dummy_capsule_remote_container);
                                viewFindViewById7.getClass();
                                View viewFindViewById8 = scene.getSceneRoot().findViewById(R.id.pip_dummy_chip_layout);
                                viewFindViewById8.getClass();
                                CardStackView cardStackView4 = cardStackView;
                                IndicatorScaleGardener indicatorScaleGardener2 = cardStackView4.indicatorScaleGardener;
                                if (ref$IntRef.element <= MathKt__MathJVMKt.roundToInt(cardStackView.getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_chip_min_width) * ((indicatorScaleGardener2 == null || (latestScaleModel2 = indicatorScaleGardener2.getLatestScaleModel(cardStackView4.getContext())) == null) ? 1 : Float.valueOf(latestScaleModel2.ratio)).floatValue())) {
                                    int dimensionPixelSize = cardStackView.getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_activity_chip_padding_side);
                                    viewFindViewById5.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
                                    FrameLayout frameLayout2 = (FrameLayout) viewFindViewById6;
                                    frameLayout2.removeAllViews();
                                    FrameLayout frameLayout3 = (FrameLayout) viewFindViewById7;
                                    if (frameLayout3.getChildCount() > 0) {
                                        frameLayout3.removeAllViews();
                                        viewFindViewById5.setVisibility(0);
                                    }
                                    frameLayout2.setVisibility(8);
                                }
                                ViewPropertyAnimator viewPropertyAnimatorAlpha2 = viewFindViewById8.animate().alpha(1.0f);
                                CardStackView.Companion.getClass();
                                viewPropertyAnimatorAlpha2.setInterpolator(CardStackView.alphaInterpolator).setDuration(200L).start();
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
