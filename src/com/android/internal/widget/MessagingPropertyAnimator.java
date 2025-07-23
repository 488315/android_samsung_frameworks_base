package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.IntProperty;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.ViewClippingUtil;
import com.samsung.android.wallpaperbackup.GenerateXML;

/* loaded from: classes6.dex */
public class MessagingPropertyAnimator implements View.OnLayoutChangeListener {
    private static final long APPEAR_ANIMATION_LENGTH = 210;
    private static final int TAG_ALPHA_ANIMATOR = 16909910;
    private static final int TAG_FIRST_LAYOUT = 16909911;
    private static final int TAG_LAYOUT_TOP = 16909913;
    private static final int TAG_TOP = 16909917;
    private static final int TAG_TOP_ANIMATOR = 16909916;
    public static final Interpolator ALPHA_IN = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    public static final Interpolator ALPHA_OUT = new PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
    private static final ViewClippingUtil.ClippingParameters CLIPPING_PARAMETERS = new ViewClippingUtil.ClippingParameters() { // from class: com.android.internal.widget.MessagingPropertyAnimator$$ExternalSyntheticLambda0
        @Override // com.android.internal.widget.ViewClippingUtil.ClippingParameters
        public final boolean shouldFinish(View view) {
            return MessagingPropertyAnimator.lambda$static$0(view);
        }
    };
    private static final IntProperty<View> TOP = new IntProperty<View>(GenerateXML.TOP) { // from class: com.android.internal.widget.MessagingPropertyAnimator.1
        @Override // android.util.IntProperty
        public void setValue(View view, int i) {
            MessagingPropertyAnimator.setTop(view, i);
        }

        @Override // android.util.Property
        public Integer get(View view) {
            return Integer.valueOf(MessagingPropertyAnimator.getTop(view));
        }
    };

    static /* synthetic */ boolean lambda$static$0(View view) {
        return view.getId() == 16909458;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        setLayoutTop(view, i2);
        if (isFirstLayout(view)) {
            setFirstLayout(view, false);
            setTop(view, i2);
        } else {
            startTopAnimation(view, getTop(view), i2, MessagingLayout.FAST_OUT_SLOW_IN);
        }
    }

    private static boolean isFirstLayout(View view) {
        Boolean bool = (Boolean) view.getTag(16909911);
        if (bool == null) {
            return true;
        }
        return bool.booleanValue();
    }

    public static void recycle(View view) {
        setFirstLayout(view, true);
    }

    private static void setFirstLayout(View view, boolean z) {
        view.setTagInternal(16909911, Boolean.valueOf(z));
    }

    private static void setLayoutTop(View view, int i) {
        view.setTagInternal(16909913, Integer.valueOf(i));
    }

    public static int getLayoutTop(View view) {
        Integer num = (Integer) view.getTag(16909913);
        if (num == null) {
            return getTop(view);
        }
        return num.intValue();
    }

    public static void startLocalTranslationFrom(View view, int i, Interpolator interpolator) {
        startTopAnimation(view, getTop(view) + i, getLayoutTop(view), interpolator);
    }

    public static void startLocalTranslationTo(View view, int i, Interpolator interpolator) {
        int top = getTop(view);
        startTopAnimation(view, top, i + top, interpolator);
    }

    public static int getTop(View view) {
        Integer num = (Integer) view.getTag(16909917);
        if (num == null) {
            return view.getTop();
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setTop(View view, int i) {
        view.setTagInternal(16909917, Integer.valueOf(i));
        updateTopAndBottom(view);
    }

    private static void updateTopAndBottom(View view) {
        int top = getTop(view);
        int height = view.getHeight();
        view.setTop(top);
        view.setBottom(height + top);
    }

    private static void startTopAnimation(final View view, int i, int i2, Interpolator interpolator) {
        ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(16909916);
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (!view.isShown() || i == i2 || (MessagingLinearLayout.isGone(view) && !isHidingAnimated(view))) {
            setTop(view, i2);
            return;
        }
        ObjectAnimator ofInt = ObjectAnimator.ofInt(view, TOP, i, i2);
        setTop(view, i);
        ofInt.setInterpolator(interpolator);
        ofInt.setDuration(APPEAR_ANIMATION_LENGTH);
        ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.widget.MessagingPropertyAnimator.2
            public boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                View.this.setTagInternal(16909916, null);
                MessagingPropertyAnimator.setClippingDeactivated(View.this, false);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
            }
        });
        setClippingDeactivated(view, true);
        view.setTagInternal(16909916, ofInt);
        ofInt.start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isHidingAnimated(View view) {
        if (view instanceof MessagingLinearLayout.MessagingChild) {
            return ((MessagingLinearLayout.MessagingChild) view).isHidingAnimated();
        }
        return false;
    }

    public static void fadeIn(final View view) {
        ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(16909910);
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (view.getVisibility() == 4) {
            view.setVisibility(0);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f);
        view.setAlpha(0.0f);
        ofFloat.setInterpolator(ALPHA_IN);
        ofFloat.setDuration(APPEAR_ANIMATION_LENGTH);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.widget.MessagingPropertyAnimator.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                View.this.setTagInternal(16909910, null);
                MessagingPropertyAnimator.updateLayerType(View.this, false);
            }
        });
        updateLayerType(view, true);
        view.setTagInternal(16909910, ofFloat);
        ofFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateLayerType(View view, boolean z) {
        if (view.hasOverlappingRendering() && z) {
            view.setLayerType(2, null);
        } else if (view.getLayerType() == 2) {
            view.setLayerType(0, null);
        }
    }

    public static void fadeOut(final View view, final Runnable runnable) {
        ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(16909910);
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (!view.isShown() || (MessagingLinearLayout.isGone(view) && !isHidingAnimated(view))) {
            view.setAlpha(0.0f);
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ALPHA, view.getAlpha(), 0.0f);
        ofFloat.setInterpolator(ALPHA_OUT);
        ofFloat.setDuration(APPEAR_ANIMATION_LENGTH);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.widget.MessagingPropertyAnimator.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                View.this.setTagInternal(16909910, null);
                MessagingPropertyAnimator.updateLayerType(View.this, false);
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        updateLayerType(view, true);
        view.setTagInternal(16909910, ofFloat);
        ofFloat.start();
    }

    public static void setClippingDeactivated(View view, boolean z) {
        ViewClippingUtil.setClippingDeactivated(view, z, CLIPPING_PARAMETERS);
    }

    public static boolean isAnimatingTranslation(View view) {
        return view.getTag(16909916) != null;
    }

    public static boolean isAnimatingAlpha(View view) {
        return view.getTag(16909910) != null;
    }

    public static void setToLaidOutPosition(View view) {
        setTop(view, getLayoutTop(view));
    }
}
