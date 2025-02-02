package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.motion.widget.ViewTransition;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.SharedValues;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class ViewTransitionController {
    ArrayList<ViewTransition.Animate> mAnimations;
    private final MotionLayout mMotionLayout;
    private HashSet<View> mRelatedViews;
    private ArrayList<ViewTransition> mViewTransitions = new ArrayList<>();
    private String mTAG = "ViewTransitionController";
    ArrayList<ViewTransition.Animate> mRemoveList = new ArrayList<>();

    /* renamed from: androidx.constraintlayout.motion.widget.ViewTransitionController$1 */
    final class C00851 implements SharedValues.SharedValuesListener {
    }

    public ViewTransitionController(MotionLayout motionLayout) {
        this.mMotionLayout = motionLayout;
    }

    private static void listenForSharedVariable(ViewTransition viewTransition, boolean z) {
        ConstraintLayout.getSharedValues().addListener(viewTransition.getSharedValueID(), new C00851());
    }

    public final void add(ViewTransition viewTransition) {
        this.mViewTransitions.add(viewTransition);
        this.mRelatedViews = null;
        if (viewTransition.getStateTransition() == 4) {
            listenForSharedVariable(viewTransition, true);
        } else if (viewTransition.getStateTransition() == 5) {
            listenForSharedVariable(viewTransition, false);
        }
    }

    final boolean applyViewTransition(int i, MotionController motionController) {
        Iterator<ViewTransition> it = this.mViewTransitions.iterator();
        while (it.hasNext()) {
            ViewTransition next = it.next();
            if (next.getId() == i) {
                next.mKeyFrames.addAllFrames(motionController);
                return true;
            }
        }
        return false;
    }

    final void invalidate() {
        this.mMotionLayout.invalidate();
    }

    final void touchEvent(MotionEvent motionEvent) {
        MotionLayout motionLayout = this.mMotionLayout;
        int currentState = motionLayout.getCurrentState();
        if (currentState == -1) {
            return;
        }
        if (this.mRelatedViews == null) {
            this.mRelatedViews = new HashSet<>();
            Iterator<ViewTransition> it = this.mViewTransitions.iterator();
            while (it.hasNext()) {
                ViewTransition next = it.next();
                int childCount = motionLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = motionLayout.getChildAt(i);
                    if (next.matchesView(childAt)) {
                        childAt.getId();
                        this.mRelatedViews.add(childAt);
                    }
                }
            }
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        Rect rect = new Rect();
        int action = motionEvent.getAction();
        ArrayList<ViewTransition.Animate> arrayList = this.mAnimations;
        if (arrayList != null && !arrayList.isEmpty()) {
            Iterator<ViewTransition.Animate> it2 = this.mAnimations.iterator();
            while (it2.hasNext()) {
                ViewTransition.Animate next2 = it2.next();
                if (action != 1) {
                    if (action != 2) {
                        next2.getClass();
                    } else {
                        next2.mMC.mView.getHitRect(next2.mTempRec);
                        if (!next2.mTempRec.contains((int) x, (int) y) && !next2.mReverse) {
                            next2.reverse();
                        }
                    }
                } else if (!next2.mReverse) {
                    next2.reverse();
                }
            }
        }
        if (action == 0 || action == 1) {
            ConstraintSet constraintSet = motionLayout.getConstraintSet(currentState);
            Iterator<ViewTransition> it3 = this.mViewTransitions.iterator();
            while (it3.hasNext()) {
                ViewTransition next3 = it3.next();
                if (next3.supports(action)) {
                    Iterator<View> it4 = this.mRelatedViews.iterator();
                    while (it4.hasNext()) {
                        View next4 = it4.next();
                        if (next3.matchesView(next4)) {
                            next4.getHitRect(rect);
                            if (rect.contains((int) x, (int) y)) {
                                next3.applyTransition(this, this.mMotionLayout, currentState, constraintSet, next4);
                            }
                        }
                    }
                }
            }
        }
    }

    final void viewTransition(int i, View... viewArr) {
        ArrayList arrayList = new ArrayList();
        Iterator<ViewTransition> it = this.mViewTransitions.iterator();
        ViewTransition viewTransition = null;
        while (it.hasNext()) {
            ViewTransition next = it.next();
            if (next.getId() == i) {
                for (View view : viewArr) {
                    if (next.checkTags(view)) {
                        arrayList.add(view);
                    }
                }
                if (!arrayList.isEmpty()) {
                    View[] viewArr2 = (View[]) arrayList.toArray(new View[0]);
                    MotionLayout motionLayout = this.mMotionLayout;
                    int currentState = motionLayout.getCurrentState();
                    if (next.mViewTransitionMode == 2) {
                        next.applyTransition(this, this.mMotionLayout, currentState, null, viewArr2);
                    } else if (currentState == -1) {
                        Log.w(this.mTAG, "No support for ViewTransition within transition yet. Currently: " + motionLayout.toString());
                    } else {
                        ConstraintSet constraintSet = motionLayout.getConstraintSet(currentState);
                        if (constraintSet != null) {
                            next.applyTransition(this, this.mMotionLayout, currentState, constraintSet, viewArr2);
                        }
                    }
                    arrayList.clear();
                }
                viewTransition = next;
            }
        }
        if (viewTransition == null) {
            Log.e(this.mTAG, " Could not find ViewTransition");
        }
    }
}
