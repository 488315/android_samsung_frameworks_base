package android.transition;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public abstract class Visibility extends Transition {
    public static final int MODE_IN = 1;
    public static final int MODE_OUT = 2;
    private static final String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";
    private int mMode;
    private boolean mSuppressLayout;
    static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
    private static final String PROPNAME_PARENT = "android:visibility:parent";
    private static final String[] sTransitionProperties = {PROPNAME_VISIBILITY, PROPNAME_PARENT};

    @Retention(RetentionPolicy.SOURCE)
    @interface VisibilityMode {
    }

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    private static class VisibilityInfo {
        ViewGroup endParent;
        int endVisibility;
        boolean fadeIn;
        ViewGroup startParent;
        int startVisibility;
        boolean visibilityChange;

        private VisibilityInfo() {
        }
    }

    public Visibility() {
        this.mMode = 3;
        this.mSuppressLayout = true;
    }

    public Visibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMode = 3;
        this.mSuppressLayout = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.VisibilityTransition);
        int i = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
        if (i != 0) {
            setMode(i);
        }
    }

    public void setSuppressLayout(boolean z) {
        this.mSuppressLayout = z;
    }

    public void setMode(int i) {
        if ((i & (-4)) != 0) {
            throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
        }
        this.mMode = i;
    }

    public int getMode() {
        return this.mMode;
    }

    @Override // android.transition.Transition
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_VISIBILITY, Integer.valueOf(transitionValues.view.getVisibility()));
        transitionValues.values.put(PROPNAME_PARENT, transitionValues.view.getParent());
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put(PROPNAME_SCREEN_LOCATION, iArr);
    }

    @Override // android.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public boolean isVisible(TransitionValues transitionValues) {
        if (transitionValues == null) {
            return false;
        }
        return ((Integer) transitionValues.values.get(PROPNAME_VISIBILITY)).intValue() == 0 && ((View) transitionValues.values.get(PROPNAME_PARENT)) != null;
    }

    private static VisibilityInfo getVisibilityChangeInfo(TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = new VisibilityInfo();
        visibilityInfo.visibilityChange = false;
        visibilityInfo.fadeIn = false;
        if (transitionValues != null && transitionValues.values.containsKey(PROPNAME_VISIBILITY)) {
            visibilityInfo.startVisibility = ((Integer) transitionValues.values.get(PROPNAME_VISIBILITY)).intValue();
            visibilityInfo.startParent = (ViewGroup) transitionValues.values.get(PROPNAME_PARENT);
        } else {
            visibilityInfo.startVisibility = -1;
            visibilityInfo.startParent = null;
        }
        if (transitionValues2 != null && transitionValues2.values.containsKey(PROPNAME_VISIBILITY)) {
            visibilityInfo.endVisibility = ((Integer) transitionValues2.values.get(PROPNAME_VISIBILITY)).intValue();
            visibilityInfo.endParent = (ViewGroup) transitionValues2.values.get(PROPNAME_PARENT);
        } else {
            visibilityInfo.endVisibility = -1;
            visibilityInfo.endParent = null;
        }
        if (transitionValues != null && transitionValues2 != null) {
            if (visibilityInfo.startVisibility != visibilityInfo.endVisibility || visibilityInfo.startParent != visibilityInfo.endParent) {
                if (visibilityInfo.startVisibility != visibilityInfo.endVisibility) {
                    if (visibilityInfo.startVisibility == 0) {
                        visibilityInfo.fadeIn = false;
                        visibilityInfo.visibilityChange = true;
                        return visibilityInfo;
                    }
                    if (visibilityInfo.endVisibility == 0) {
                        visibilityInfo.fadeIn = true;
                        visibilityInfo.visibilityChange = true;
                        return visibilityInfo;
                    }
                } else if (visibilityInfo.startParent != visibilityInfo.endParent) {
                    if (visibilityInfo.endParent == null) {
                        visibilityInfo.fadeIn = false;
                        visibilityInfo.visibilityChange = true;
                        return visibilityInfo;
                    }
                    if (visibilityInfo.startParent == null) {
                        visibilityInfo.fadeIn = true;
                        visibilityInfo.visibilityChange = true;
                        return visibilityInfo;
                    }
                }
            }
        } else {
            if (transitionValues == null && visibilityInfo.endVisibility == 0) {
                visibilityInfo.fadeIn = true;
                visibilityInfo.visibilityChange = true;
                return visibilityInfo;
            }
            if (transitionValues2 == null && visibilityInfo.startVisibility == 0) {
                visibilityInfo.fadeIn = false;
                visibilityInfo.visibilityChange = true;
            }
        }
        return visibilityInfo;
    }

    @Override // android.transition.Transition
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (!visibilityChangeInfo.visibilityChange) {
            return null;
        }
        if (visibilityChangeInfo.startParent == null && visibilityChangeInfo.endParent == null) {
            return null;
        }
        if (visibilityChangeInfo.fadeIn) {
            return onAppear(viewGroup, transitionValues, visibilityChangeInfo.startVisibility, transitionValues2, visibilityChangeInfo.endVisibility);
        }
        return onDisappear(viewGroup, transitionValues, visibilityChangeInfo.startVisibility, transitionValues2, visibilityChangeInfo.endVisibility);
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        if ((this.mMode & 1) != 1 || transitionValues2 == null) {
            return null;
        }
        if (transitionValues == null) {
            View view = (View) transitionValues2.view.getParent();
            if (getVisibilityChangeInfo(getMatchedTransitionValues(view, false), getTransitionValues(view, false)).visibilityChange) {
                return null;
            }
        }
        return onAppear(viewGroup, transitionValues2.view, transitionValues, transitionValues2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x0080, code lost:
    
        if (r10.mCanRemoveViews != false) goto L43;
     */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.animation.Animator onDisappear(android.view.ViewGroup r11, android.transition.TransitionValues r12, int r13, android.transition.TransitionValues r14, int r15) {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.transition.Visibility.onDisappear(android.view.ViewGroup, android.transition.TransitionValues, int, android.transition.TransitionValues, int):android.animation.Animator");
    }

    @Override // android.transition.Transition
    public boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null && transitionValues2 == null) {
            return false;
        }
        if (transitionValues != null && transitionValues2 != null && transitionValues2.values.containsKey(PROPNAME_VISIBILITY) != transitionValues.values.containsKey(PROPNAME_VISIBILITY)) {
            return false;
        }
        VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (visibilityChangeInfo.visibilityChange) {
            return visibilityChangeInfo.startVisibility == 0 || visibilityChangeInfo.endVisibility == 0;
        }
        return false;
    }

    private static class DisappearListener extends TransitionListenerAdapter implements Animator.AnimatorListener, Animator.AnimatorPauseListener {
        boolean mCanceled = false;
        private final int mFinalVisibility;
        private boolean mLayoutSuppressed;
        private final ViewGroup mParent;
        private final boolean mSuppressLayout;
        private final View mView;

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        public DisappearListener(View view, int i, boolean z) {
            this.mView = view;
            this.mFinalVisibility = i;
            this.mParent = (ViewGroup) view.getParent();
            this.mSuppressLayout = z;
            suppressLayout(true);
        }

        @Override // android.animation.Animator.AnimatorPauseListener
        public void onAnimationPause(Animator animator) {
            if (this.mCanceled) {
                return;
            }
            this.mView.setTransitionVisibility(this.mFinalVisibility);
        }

        @Override // android.animation.Animator.AnimatorPauseListener
        public void onAnimationResume(Animator animator) {
            if (this.mCanceled) {
                return;
            }
            this.mView.setTransitionVisibility(0);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            this.mCanceled = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            hideViewWhenNotCanceled();
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionEnd(Transition transition) {
            hideViewWhenNotCanceled();
            transition.removeListener(this);
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionPause(Transition transition) {
            suppressLayout(false);
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionResume(Transition transition) {
            suppressLayout(true);
        }

        private void hideViewWhenNotCanceled() {
            if (!this.mCanceled) {
                this.mView.setTransitionVisibility(this.mFinalVisibility);
                ViewGroup viewGroup = this.mParent;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            suppressLayout(false);
        }

        private void suppressLayout(boolean z) {
            ViewGroup viewGroup;
            if (!this.mSuppressLayout || this.mLayoutSuppressed == z || (viewGroup = this.mParent) == null) {
                return;
            }
            this.mLayoutSuppressed = z;
            viewGroup.suppressLayout(z);
        }
    }
}
