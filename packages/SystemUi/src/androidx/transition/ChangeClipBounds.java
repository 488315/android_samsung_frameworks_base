package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import java.util.HashMap;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ChangeClipBounds extends Transition {
    public static final String[] sTransitionProperties = {"android:clipBounds:clip"};

    public ChangeClipBounds() {
    }

    @Override // androidx.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public final void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() == 8) {
            return;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        Rect clipBounds = ViewCompat.Api18Impl.getClipBounds(view);
        HashMap hashMap = (HashMap) transitionValues.values;
        hashMap.put("android:clipBounds:clip", clipBounds);
        if (clipBounds == null) {
            hashMap.put("android:clipBounds:bounds", new Rect(0, 0, view.getWidth(), view.getHeight()));
        }
    }

    @Override // androidx.transition.Transition
    public final Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues != null && transitionValues2 != null) {
            HashMap hashMap = (HashMap) transitionValues.values;
            if (hashMap.containsKey("android:clipBounds:clip")) {
                HashMap hashMap2 = (HashMap) transitionValues2.values;
                if (hashMap2.containsKey("android:clipBounds:clip")) {
                    Rect rect = (Rect) hashMap.get("android:clipBounds:clip");
                    Rect rect2 = (Rect) hashMap2.get("android:clipBounds:clip");
                    boolean z = rect2 == null;
                    if (rect == null && rect2 == null) {
                        return null;
                    }
                    if (rect == null) {
                        rect = (Rect) hashMap.get("android:clipBounds:bounds");
                    } else if (rect2 == null) {
                        rect2 = (Rect) hashMap2.get("android:clipBounds:bounds");
                    }
                    if (rect.equals(rect2)) {
                        return null;
                    }
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    final View view = transitionValues2.view;
                    ViewCompat.Api18Impl.setClipBounds(view, rect);
                    ObjectAnimator ofObject = ObjectAnimator.ofObject(view, ViewUtils.CLIP_BOUNDS, new RectEvaluator(new Rect()), rect, rect2);
                    if (z) {
                        ofObject.addListener(new AnimatorListenerAdapter(this) { // from class: androidx.transition.ChangeClipBounds.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                View view2 = view;
                                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                ViewCompat.Api18Impl.setClipBounds(view2, null);
                            }
                        });
                    }
                    return ofObject;
                }
            }
        }
        return null;
    }

    @Override // androidx.transition.Transition
    public final String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public ChangeClipBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
