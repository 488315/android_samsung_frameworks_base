package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class ExpandableViewState extends ViewState {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean belowSpeedBump;
    public int clipBottomAmount;
    public int clipTopAmount;
    public boolean dimmed;
    public boolean hasGradient;
    public boolean headsUpIsVisible;
    public int height;
    public boolean hideSensitive;
    public boolean inShelf;
    public int location;
    public int notGoneIndex;

    @Override // com.android.systemui.statusbar.notification.stack.ViewState
    public void animateTo(View view, AnimationProperties animationProperties) {
        super.animateTo(view, animationProperties);
        if (view instanceof ExpandableView) {
            final ExpandableView expandableView = (ExpandableView) view;
            AnimationFilter animationFilter = animationProperties.getAnimationFilter();
            if (this.height != expandableView.mActualHeight) {
                Integer num = (Integer) expandableView.getTag(R.id.height_animator_start_value_tag);
                Integer num2 = (Integer) expandableView.getTag(R.id.height_animator_end_value_tag);
                int i = this.height;
                if (num2 == null || num2.intValue() != i) {
                    ValueAnimator valueAnimator = (ValueAnimator) expandableView.getTag(R.id.height_animator_tag);
                    if (animationProperties.getAnimationFilter().animateHeight) {
                        ValueAnimator ofInt = ValueAnimator.ofInt(expandableView.mActualHeight, i);
                        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState.1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                expandableView.setActualHeight(((Integer) valueAnimator2.getAnimatedValue()).intValue(), false);
                            }
                        });
                        ofInt.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                        ofInt.setDuration(ViewState.cancelAnimatorAndGetNewDuration(animationProperties.duration, valueAnimator));
                        if (animationProperties.delay > 0 && (valueAnimator == null || valueAnimator.getAnimatedFraction() == 0.0f)) {
                            ofInt.setStartDelay(animationProperties.delay);
                        }
                        AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(null);
                        if (animationFinishListener != null) {
                            ofInt.addListener(animationFinishListener);
                        }
                        ofInt.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState.2
                            public boolean mWasCancelled;

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationCancel(Animator animator) {
                                this.mWasCancelled = true;
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                ExpandableView expandableView2 = expandableView;
                                int i2 = ExpandableViewState.$r8$clinit;
                                expandableView2.setTag(R.id.height_animator_tag, null);
                                expandableView.setTag(R.id.height_animator_start_value_tag, null);
                                expandableView.setTag(R.id.height_animator_end_value_tag, null);
                                expandableView.setActualHeightAnimating(false);
                                if (this.mWasCancelled) {
                                    return;
                                }
                                ExpandableView expandableView3 = expandableView;
                                if (expandableView3 instanceof ExpandableNotificationRow) {
                                    ((ExpandableNotificationRow) expandableView3).mGroupExpansionChanging = false;
                                }
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                                this.mWasCancelled = false;
                            }
                        });
                        ViewState.startAnimator(ofInt, animationFinishListener);
                        expandableView.setTag(R.id.height_animator_tag, ofInt);
                        expandableView.setTag(R.id.height_animator_start_value_tag, Integer.valueOf(expandableView.mActualHeight));
                        expandableView.setTag(R.id.height_animator_end_value_tag, Integer.valueOf(i));
                        expandableView.setActualHeightAnimating(true);
                    } else if (valueAnimator != null) {
                        PropertyValuesHolder[] values = valueAnimator.getValues();
                        int intValue = num.intValue() + (i - num2.intValue());
                        values[0].setIntValues(intValue, i);
                        expandableView.setTag(R.id.height_animator_start_value_tag, Integer.valueOf(intValue));
                        expandableView.setTag(R.id.height_animator_end_value_tag, Integer.valueOf(i));
                        valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
                    } else {
                        expandableView.setActualHeight(i, false);
                    }
                }
            } else {
                ViewState.abortAnimation(view, R.id.height_animator_tag);
            }
            if (this.clipTopAmount != expandableView.mClipTopAmount) {
                startClipAnimation(expandableView, animationProperties, true);
            } else {
                ViewState.abortAnimation(view, R.id.top_inset_animator_tag);
            }
            if (this.clipBottomAmount != expandableView.mClipBottomAmount) {
                startClipAnimation(expandableView, animationProperties, false);
            } else {
                ViewState.abortAnimation(view, R.id.bottom_inset_animator_tag);
            }
            expandableView.setDimmed(this.dimmed);
            int i2 = NotificationIconContainerRefactor.$r8$clinit;
            expandableView.setBelowSpeedBump(this.belowSpeedBump);
            expandableView.setHideSensitive(this.hideSensitive, animationFilter.animateHideSensitive);
            if (animationProperties.wasAdded(view) && !this.hidden) {
                expandableView.performAddAnimation(animationProperties.delay, animationProperties.duration);
            }
            if (!expandableView.mInShelf && this.inShelf) {
                expandableView.mTransformingInShelf = true;
            }
            expandableView.mInShelf = this.inShelf;
            if (this.headsUpIsVisible) {
                expandableView.setHeadsUpIsVisible();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.stack.ViewState
    public void applyToView(View view) {
        super.applyToView(view);
        if (view instanceof ExpandableView) {
            ExpandableView expandableView = (ExpandableView) view;
            int i = expandableView.mActualHeight;
            int i2 = this.height;
            if (i != i2) {
                expandableView.setActualHeight(i2, false);
            }
            expandableView.setDimmed(this.dimmed);
            expandableView.setHideSensitive(this.hideSensitive, false);
            int i3 = NotificationIconContainerRefactor.$r8$clinit;
            expandableView.setBelowSpeedBump(this.belowSpeedBump);
            float f = expandableView.mClipTopAmount;
            int i4 = this.clipTopAmount;
            if (f != i4) {
                expandableView.setClipTopAmount(i4);
            }
            float f2 = expandableView.mClipBottomAmount;
            int i5 = this.clipBottomAmount;
            if (f2 != i5) {
                expandableView.setClipBottomAmount(i5);
            }
            expandableView.mTransformingInShelf = false;
            expandableView.mInShelf = this.inShelf;
            if (this.headsUpIsVisible) {
                expandableView.setHeadsUpIsVisible();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.stack.ViewState
    public final void cancelAnimations(View view) {
        super.cancelAnimations(view);
        Animator animator = (Animator) view.getTag(R.id.height_animator_tag);
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = (Animator) view.getTag(R.id.top_inset_animator_tag);
        if (animator2 != null) {
            animator2.cancel();
        }
    }

    @Override // com.android.systemui.statusbar.notification.stack.ViewState
    public void copyFrom(ViewState viewState) {
        super.copyFrom(viewState);
        if (viewState instanceof ExpandableViewState) {
            ExpandableViewState expandableViewState = (ExpandableViewState) viewState;
            this.height = expandableViewState.height;
            this.hideSensitive = expandableViewState.hideSensitive;
            this.belowSpeedBump = expandableViewState.belowSpeedBump;
            this.clipTopAmount = expandableViewState.clipTopAmount;
            this.notGoneIndex = expandableViewState.notGoneIndex;
            this.dimmed = expandableViewState.dimmed;
            this.location = expandableViewState.location;
            this.headsUpIsVisible = expandableViewState.headsUpIsVisible;
            this.hasGradient = expandableViewState.hasGradient;
        }
    }

    public final void startClipAnimation(final ExpandableView expandableView, AnimationProperties animationProperties, final boolean z) {
        Integer num = (Integer) expandableView.getTag(z ? R.id.top_inset_animator_start_value_tag : R.id.bottom_inset_animator_start_value_tag);
        int i = R.id.bottom_inset_animator_end_value_tag;
        Integer num2 = (Integer) expandableView.getTag(z ? R.id.top_inset_animator_end_value_tag : R.id.bottom_inset_animator_end_value_tag);
        Integer num3 = (Integer) expandableView.getTag(R.id.group_children_clip_top_duration_value_tag);
        int i2 = z ? this.clipTopAmount : this.clipBottomAmount;
        if (num2 == null || num2.intValue() != i2) {
            int i3 = R.id.bottom_inset_animator_tag;
            ValueAnimator valueAnimator = (ValueAnimator) expandableView.getTag(z ? R.id.top_inset_animator_tag : R.id.bottom_inset_animator_tag);
            AnimationFilter animationFilter = animationProperties.getAnimationFilter();
            if ((z && !animationFilter.animateTopInset) || !z) {
                if (valueAnimator == null) {
                    if (z) {
                        expandableView.setClipTopAmount(i2);
                        return;
                    } else {
                        expandableView.setClipBottomAmount(i2);
                        return;
                    }
                }
                PropertyValuesHolder[] values = valueAnimator.getValues();
                int intValue = num.intValue() + (i2 - num2.intValue());
                values[0].setIntValues(intValue, i2);
                expandableView.setTag(z ? R.id.top_inset_animator_start_value_tag : R.id.bottom_inset_animator_start_value_tag, Integer.valueOf(intValue));
                if (z) {
                    i = R.id.top_inset_animator_end_value_tag;
                }
                expandableView.setTag(i, Integer.valueOf(i2));
                valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
                return;
            }
            ValueAnimator ofInt = ValueAnimator.ofInt(z ? expandableView.mClipTopAmount : expandableView.mClipBottomAmount, i2);
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    boolean z2 = z;
                    ExpandableView expandableView2 = expandableView;
                    if (z2) {
                        expandableView2.setClipTopAmount(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                    } else {
                        expandableView2.setClipBottomAmount(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                    }
                }
            });
            ofInt.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            long cancelAnimatorAndGetNewDuration = ViewState.cancelAnimatorAndGetNewDuration(animationProperties.duration, valueAnimator);
            if (num3 != null) {
                cancelAnimatorAndGetNewDuration = num3.intValue();
            }
            ofInt.setDuration(cancelAnimatorAndGetNewDuration);
            if (animationProperties.delay > 0 && (valueAnimator == null || valueAnimator.getAnimatedFraction() == 0.0f)) {
                ofInt.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(null);
            if (animationFinishListener != null) {
                ofInt.addListener(animationFinishListener);
            }
            ofInt.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    int i4;
                    ExpandableView expandableView2 = expandableView;
                    if (z) {
                        int i5 = ExpandableViewState.$r8$clinit;
                        i4 = R.id.top_inset_animator_tag;
                    } else {
                        int i6 = ExpandableViewState.$r8$clinit;
                        i4 = R.id.bottom_inset_animator_tag;
                    }
                    expandableView2.setTag(i4, null);
                    expandableView.setTag(z ? R.id.top_inset_animator_start_value_tag : R.id.bottom_inset_animator_start_value_tag, null);
                    expandableView.setTag(z ? R.id.top_inset_animator_end_value_tag : R.id.bottom_inset_animator_end_value_tag, null);
                }
            });
            ViewState.startAnimator(ofInt, animationFinishListener);
            if (z) {
                i3 = R.id.top_inset_animator_tag;
            }
            expandableView.setTag(i3, ofInt);
            expandableView.setTag(z ? R.id.top_inset_animator_start_value_tag : R.id.bottom_inset_animator_start_value_tag, Integer.valueOf(z ? expandableView.mClipTopAmount : expandableView.mClipBottomAmount));
            if (z) {
                i = R.id.top_inset_animator_end_value_tag;
            }
            expandableView.setTag(i, Integer.valueOf(i2));
        }
    }
}
