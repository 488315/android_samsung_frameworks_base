package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.Transition;
import com.android.systemui.R;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Visibility extends Transition {
    public static final String[] sTransitionProperties = {"android:visibility:visibility", "android:visibility:parent"};
    public int mMode;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DisappearListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        public boolean mCanceled = false;
        public final int mFinalVisibility;
        public boolean mLayoutSuppressed;
        public final ViewGroup mParent;
        public final boolean mSuppressLayout;
        public final View mView;

        public DisappearListener(View view, int i, boolean z) {
            this.mView = view;
            this.mFinalVisibility = i;
            this.mParent = (ViewGroup) view.getParent();
            this.mSuppressLayout = z;
            suppressLayout(true);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            this.mCanceled = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
                ViewGroup viewGroup = this.mParent;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            suppressLayout(false);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionPause() {
            suppressLayout(false);
            if (this.mCanceled) {
                return;
            }
            ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionResume() {
            suppressLayout(true);
            if (this.mCanceled) {
                return;
            }
            ViewUtils.setTransitionVisibility(this.mView, 0);
        }

        public final void suppressLayout(boolean z) {
            ViewGroup viewGroup;
            if (!this.mSuppressLayout || this.mLayoutSuppressed == z || (viewGroup = this.mParent) == null) {
                return;
            }
            this.mLayoutSuppressed = z;
            ViewGroupUtils$Api29Impl.suppressLayout(viewGroup, z);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator, boolean z) {
            if (z) {
                ViewUtils.setTransitionVisibility(this.mView, 0);
                ViewGroup viewGroup = this.mParent;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator, boolean z) {
            if (z) {
                return;
            }
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
                ViewGroup viewGroup = this.mParent;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            suppressLayout(false);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionCancel(Transition transition) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionStart(Transition transition) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class OverlayListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        public boolean mHasOverlay = true;
        public final ViewGroup mOverlayHost;
        public final View mOverlayView;
        public final View mStartView;

        public OverlayListener(ViewGroup viewGroup, View view, View view2) {
            this.mOverlayHost = viewGroup;
            this.mOverlayView = view;
            this.mStartView = view2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            removeFromOverlay();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
        public final void onAnimationPause(Animator animator) {
            this.mOverlayHost.getOverlay().remove(this.mOverlayView);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
        public final void onAnimationResume(Animator animator) {
            if (this.mOverlayView.getParent() == null) {
                this.mOverlayHost.getOverlay().add(this.mOverlayView);
            } else {
                Visibility.this.cancel();
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator, boolean z) {
            if (z) {
                this.mStartView.setTag(R.id.save_overlay_view, this.mOverlayView);
                this.mOverlayHost.getOverlay().add(this.mOverlayView);
                this.mHasOverlay = true;
            }
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionCancel(Transition transition) {
            if (this.mHasOverlay) {
                removeFromOverlay();
            }
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
        }

        public final void removeFromOverlay() {
            this.mStartView.setTag(R.id.save_overlay_view, null);
            this.mOverlayHost.getOverlay().remove(this.mOverlayView);
            this.mHasOverlay = false;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator, boolean z) {
            if (z) {
                return;
            }
            removeFromOverlay();
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionPause() {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionResume() {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public final void onTransitionStart(Transition transition) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class VisibilityInfo {
        public ViewGroup mEndParent;
        public int mEndVisibility;
        public boolean mFadeIn;
        public ViewGroup mStartParent;
        public int mStartVisibility;
        public boolean mVisibilityChange;
    }

    public Visibility() {
        this.mMode = 3;
    }

    public static void captureValues$1(TransitionValues transitionValues) {
        int visibility = transitionValues.view.getVisibility();
        ((HashMap) transitionValues.values).put("android:visibility:visibility", Integer.valueOf(visibility));
        ((HashMap) transitionValues.values).put("android:visibility:parent", transitionValues.view.getParent());
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        ((HashMap) transitionValues.values).put("android:visibility:screenLocation", iArr);
    }

    public static VisibilityInfo getVisibilityChangeInfo(TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = new VisibilityInfo();
        visibilityInfo.mVisibilityChange = false;
        visibilityInfo.mFadeIn = false;
        if (transitionValues == null || !((HashMap) transitionValues.values).containsKey("android:visibility:visibility")) {
            visibilityInfo.mStartVisibility = -1;
            visibilityInfo.mStartParent = null;
        } else {
            visibilityInfo.mStartVisibility = ((Integer) ((HashMap) transitionValues.values).get("android:visibility:visibility")).intValue();
            visibilityInfo.mStartParent = (ViewGroup) ((HashMap) transitionValues.values).get("android:visibility:parent");
        }
        if (transitionValues2 == null || !((HashMap) transitionValues2.values).containsKey("android:visibility:visibility")) {
            visibilityInfo.mEndVisibility = -1;
            visibilityInfo.mEndParent = null;
        } else {
            visibilityInfo.mEndVisibility = ((Integer) ((HashMap) transitionValues2.values).get("android:visibility:visibility")).intValue();
            visibilityInfo.mEndParent = (ViewGroup) ((HashMap) transitionValues2.values).get("android:visibility:parent");
        }
        if (transitionValues != null && transitionValues2 != null) {
            int i = visibilityInfo.mStartVisibility;
            int i2 = visibilityInfo.mEndVisibility;
            if (i != i2 || visibilityInfo.mStartParent != visibilityInfo.mEndParent) {
                if (i != i2) {
                    if (i == 0) {
                        visibilityInfo.mFadeIn = false;
                        visibilityInfo.mVisibilityChange = true;
                        return visibilityInfo;
                    }
                    if (i2 == 0) {
                        visibilityInfo.mFadeIn = true;
                        visibilityInfo.mVisibilityChange = true;
                        return visibilityInfo;
                    }
                } else {
                    if (visibilityInfo.mEndParent == null) {
                        visibilityInfo.mFadeIn = false;
                        visibilityInfo.mVisibilityChange = true;
                        return visibilityInfo;
                    }
                    if (visibilityInfo.mStartParent == null) {
                        visibilityInfo.mFadeIn = true;
                        visibilityInfo.mVisibilityChange = true;
                        return visibilityInfo;
                    }
                }
            }
        } else {
            if (transitionValues == null && visibilityInfo.mEndVisibility == 0) {
                visibilityInfo.mFadeIn = true;
                visibilityInfo.mVisibilityChange = true;
                return visibilityInfo;
            }
            if (transitionValues2 == null && visibilityInfo.mStartVisibility == 0) {
                visibilityInfo.mFadeIn = false;
                visibilityInfo.mVisibilityChange = true;
            }
        }
        return visibilityInfo;
    }

    @Override // androidx.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues$1(transitionValues);
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues$1(transitionValues);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0043, code lost:
    
        if (getVisibilityChangeInfo(getMatchedTransitionValues(r1, false), getTransitionValues(r1, false)).mVisibilityChange != false) goto L9;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01be  */
    /* JADX WARN: Type inference failed for: r17v5 */
    /* JADX WARN: Type inference failed for: r17v6 */
    /* JADX WARN: Type inference failed for: r17v7 */
    /* JADX WARN: Type inference failed for: r17v8 */
    @Override // androidx.transition.Transition
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.animation.Animator createAnimator(android.view.ViewGroup r24, androidx.transition.TransitionValues r25, androidx.transition.TransitionValues r26) {
        /*
            Method dump skipped, instructions count: 660
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.Visibility.createAnimator(android.view.ViewGroup, androidx.transition.TransitionValues, androidx.transition.TransitionValues):android.animation.Animator");
    }

    @Override // androidx.transition.Transition
    public final String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Override // androidx.transition.Transition
    public final boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null && transitionValues2 == null) {
            return false;
        }
        if (transitionValues != null && transitionValues2 != null && ((HashMap) transitionValues2.values).containsKey("android:visibility:visibility") != ((HashMap) transitionValues.values).containsKey("android:visibility:visibility")) {
            return false;
        }
        VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (visibilityChangeInfo.mVisibilityChange) {
            return visibilityChangeInfo.mStartVisibility == 0 || visibilityChangeInfo.mEndVisibility == 0;
        }
        return false;
    }

    public Animator onAppear(View view, TransitionValues transitionValues) {
        return null;
    }

    public Animator onDisappear(View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public final void setMode(int i) {
        if ((i & (-4)) != 0) {
            throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
        }
        this.mMode = i;
    }

    public Visibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMode = 3;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.VISIBILITY_TRANSITION);
        int namedInt = TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlResourceParser) attributeSet, "transitionVisibilityMode", 0, 0);
        obtainStyledAttributes.recycle();
        if (namedInt != 0) {
            setMode(namedInt);
        }
    }
}
