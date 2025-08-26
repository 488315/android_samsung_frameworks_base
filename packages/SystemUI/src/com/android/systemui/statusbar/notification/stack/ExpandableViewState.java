package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.PhysicsProperty;
import com.android.systemui.statusbar.notification.PhysicsPropertyAnimator;
import com.android.systemui.statusbar.notification.PropertyData;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;

/* loaded from: classes3.dex */
public class ExpandableViewState extends ViewState {
    public static final /* synthetic */ int $r8$clinit = 0;
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
    public void animateTo(final View view, AnimationProperties animationProperties) {
        AnimationProperties animationProperties2;
        boolean z;
        super.animateTo(view, animationProperties);
        if (view instanceof ExpandableView) {
            final ExpandableView expandableView = (ExpandableView) view;
            AnimationFilter animationFilter = animationProperties.getAnimationFilter();
            if (this.height != expandableView.mActualHeight) {
                DynamicAnimation.OnAnimationEndListener onAnimationEndListener = null;
                if (this.mUsePhysicsForMovement) {
                    boolean z2 = animationProperties.getAnimationFilter().animateHeight;
                    if (z2) {
                        expandableView.setActualHeightAnimating(true);
                    }
                    PhysicsProperty physicsProperty = ExpandableView.HEIGHT_PROPERTY;
                    Object tag = expandableView.getTag(physicsProperty.tag);
                    if (!(tag instanceof PropertyData) ? tag == null : ((PropertyData) tag).animator == null) {
                        onAnimationEndListener = new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState$$ExternalSyntheticLambda0
                            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z3, float f, float f2) {
                                ExpandableView expandableView2 = expandableView;
                                View view2 = view;
                                int i = ExpandableViewState.$r8$clinit;
                                expandableView2.setActualHeightAnimating(false);
                                if (z3 || !(view2 instanceof ExpandableNotificationRow)) {
                                    return;
                                }
                                ((ExpandableNotificationRow) view2).mGroupExpansionChanging = false;
                            }
                        };
                    }
                    DynamicAnimation.OnAnimationEndListener onAnimationEndListener2 = onAnimationEndListener;
                    float f = this.height;
                    PhysicsPropertyAnimator.Companion.getClass();
                    PhysicsPropertyAnimator.Companion.setProperty(view, physicsProperty, f, animationProperties, z2, onAnimationEndListener2);
                    animationProperties2 = animationProperties;
                } else {
                    animationProperties2 = animationProperties;
                    Integer num = (Integer) expandableView.getTag(R.id.height_animator_start_value_tag);
                    Integer num2 = (Integer) expandableView.getTag(R.id.height_animator_end_value_tag);
                    int i = this.height;
                    if (num2 == null || num2.intValue() != i) {
                        PhysicsProperty physicsProperty2 = ExpandableView.HEIGHT_PROPERTY;
                        ValueAnimator valueAnimator = (ValueAnimator) expandableView.getTag(R.id.height_animator_tag);
                        if (animationProperties2.getAnimationFilter().animateHeight) {
                            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(expandableView.mActualHeight, i);
                            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState.1
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                    expandableView.setActualHeight(((Integer) valueAnimator2.getAnimatedValue()).intValue(), false);
                                }
                            });
                            valueAnimatorOfInt.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                            valueAnimatorOfInt.setDuration(ViewState.cancelAnimatorAndGetNewDuration(animationProperties2.duration, valueAnimator));
                            if (animationProperties2.delay > 0 && (valueAnimator == null || valueAnimator.getAnimatedFraction() == 0.0f)) {
                                valueAnimatorOfInt.setStartDelay(animationProperties2.delay);
                            }
                            AnimatorListenerAdapter animationFinishListener = animationProperties2.getAnimationFinishListener(null);
                            if (animationFinishListener != null) {
                                valueAnimatorOfInt.addListener(animationFinishListener);
                            }
                            valueAnimatorOfInt.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState.2
                                public boolean mWasCancelled;

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationCancel(Animator animator) {
                                    this.mWasCancelled = true;
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    ExpandableView expandableView2 = expandableView;
                                    PhysicsProperty physicsProperty3 = ExpandableView.HEIGHT_PROPERTY;
                                    expandableView2.setTag(R.id.height_animator_tag, null);
                                    ExpandableView expandableView3 = expandableView;
                                    int i2 = ExpandableViewState.$r8$clinit;
                                    expandableView3.setTag(R.id.height_animator_start_value_tag, null);
                                    expandableView.setTag(R.id.height_animator_end_value_tag, null);
                                    expandableView.setActualHeightAnimating(false);
                                    if (this.mWasCancelled) {
                                        return;
                                    }
                                    ExpandableView expandableView4 = expandableView;
                                    if (expandableView4 instanceof ExpandableNotificationRow) {
                                        ((ExpandableNotificationRow) expandableView4).mGroupExpansionChanging = false;
                                    }
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator) {
                                    this.mWasCancelled = false;
                                }
                            });
                            ViewState.startAnimator(valueAnimatorOfInt, animationFinishListener);
                            expandableView.setTag(R.id.height_animator_tag, valueAnimatorOfInt);
                            expandableView.setTag(R.id.height_animator_start_value_tag, Integer.valueOf(expandableView.mActualHeight));
                            expandableView.setTag(R.id.height_animator_end_value_tag, Integer.valueOf(i));
                            z = true;
                            expandableView.setActualHeightAnimating(true);
                        } else if (valueAnimator != null) {
                            PropertyValuesHolder[] values = valueAnimator.getValues();
                            int iIntValue = num.intValue() + (i - num2.intValue());
                            values[0].setIntValues(iIntValue, i);
                            expandableView.setTag(R.id.height_animator_start_value_tag, Integer.valueOf(iIntValue));
                            expandableView.setTag(R.id.height_animator_end_value_tag, Integer.valueOf(i));
                            valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
                        } else {
                            expandableView.setActualHeight(i, false);
                        }
                    }
                }
                z = true;
            } else {
                animationProperties2 = animationProperties;
                z = true;
                PhysicsProperty physicsProperty3 = ExpandableView.HEIGHT_PROPERTY;
                ViewState.abortAnimation(view, R.id.height_animator_tag);
            }
            if (this.clipTopAmount != expandableView.mClipTopAmount) {
                startClipAnimation(expandableView, animationProperties2, z);
            } else {
                ViewState.abortAnimation(view, R.id.top_inset_animator_tag);
            }
            if (this.clipBottomAmount != expandableView.mClipBottomAmount) {
                startClipAnimation(expandableView, animationProperties2, false);
            } else {
                ViewState.abortAnimation(view, R.id.bottom_inset_animator_tag);
            }
            expandableView.setDimmed(this.dimmed);
            expandableView.setHideSensitive(this.hideSensitive, animationFilter.animateHideSensitive);
            if (animationProperties2.wasAdded(view) && !this.hidden) {
                expandableView.performAddAnimation(animationProperties2.delay, animationProperties2.duration);
            }
            if (!expandableView.mInShelf && this.inShelf) {
                expandableView.mTransformingInShelf = true;
            }
            expandableView.mInShelf = this.inShelf;
            if (this.headsUpIsVisible) {
                expandableView.markHeadsUpSeen();
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
                expandableView.setActualHeight(i2, true);
            }
            expandableView.setDimmed(this.dimmed);
            expandableView.setHideSensitive(this.hideSensitive, false);
            float f = expandableView.mClipTopAmount;
            int i3 = this.clipTopAmount;
            if (f != i3) {
                expandableView.setClipTopAmount(i3);
            }
            float f2 = expandableView.mClipBottomAmount;
            int i4 = this.clipBottomAmount;
            if (f2 != i4) {
                expandableView.setClipBottomAmount(i4);
            }
            expandableView.mTransformingInShelf = false;
            expandableView.mInShelf = this.inShelf;
            if (this.headsUpIsVisible) {
                expandableView.markHeadsUpSeen();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.stack.ViewState
    public final void cancelAnimations(View view) {
        super.cancelAnimations(view);
        PhysicsProperty physicsProperty = ExpandableView.HEIGHT_PROPERTY;
        ViewState.abortAnimation(view, R.id.height_animator_tag);
        ViewState.abortAnimation(view, R.id.top_inset_animator_tag);
    }

    @Override // com.android.systemui.statusbar.notification.stack.ViewState
    public void copyFrom(ViewState viewState) {
        super.copyFrom(viewState);
        if (viewState instanceof ExpandableViewState) {
            ExpandableViewState expandableViewState = (ExpandableViewState) viewState;
            this.height = expandableViewState.height;
            this.hideSensitive = expandableViewState.hideSensitive;
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
                int iIntValue = num.intValue() + (i2 - num2.intValue());
                values[0].setIntValues(iIntValue, i2);
                expandableView.setTag(z ? R.id.top_inset_animator_start_value_tag : R.id.bottom_inset_animator_start_value_tag, Integer.valueOf(iIntValue));
                if (z) {
                    i = R.id.top_inset_animator_end_value_tag;
                }
                expandableView.setTag(i, Integer.valueOf(i2));
                valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
                return;
            }
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(z ? expandableView.mClipTopAmount : expandableView.mClipBottomAmount, i2);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    boolean z2 = z;
                    ExpandableView expandableView2 = expandableView;
                    int i4 = ExpandableViewState.$r8$clinit;
                    if (z2) {
                        expandableView2.setClipTopAmount(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                    } else {
                        expandableView2.setClipBottomAmount(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                    }
                }
            });
            valueAnimatorOfInt.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            long jCancelAnimatorAndGetNewDuration = ViewState.cancelAnimatorAndGetNewDuration(animationProperties.duration, valueAnimator);
            if (num3 != null) {
                jCancelAnimatorAndGetNewDuration = num3.intValue();
            }
            valueAnimatorOfInt.setDuration(jCancelAnimatorAndGetNewDuration);
            if (animationProperties.delay > 0 && (valueAnimator == null || valueAnimator.getAnimatedFraction() == 0.0f)) {
                valueAnimatorOfInt.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(null);
            if (animationFinishListener != null) {
                valueAnimatorOfInt.addListener(animationFinishListener);
            }
            valueAnimatorOfInt.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState.3
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
            ViewState.startAnimator(valueAnimatorOfInt, animationFinishListener);
            if (z) {
                i3 = R.id.top_inset_animator_tag;
            }
            expandableView.setTag(i3, valueAnimatorOfInt);
            expandableView.setTag(z ? R.id.top_inset_animator_start_value_tag : R.id.bottom_inset_animator_start_value_tag, Integer.valueOf(z ? expandableView.mClipTopAmount : expandableView.mClipBottomAmount));
            if (z) {
                i = R.id.top_inset_animator_end_value_tag;
            }
            expandableView.setTag(i, Integer.valueOf(i2));
        }
    }
}
