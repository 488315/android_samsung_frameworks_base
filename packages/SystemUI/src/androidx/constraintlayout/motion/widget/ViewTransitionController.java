package androidx.constraintlayout.motion.widget;

import java.util.ArrayList;
import java.util.HashSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ViewTransitionController {
    public ArrayList mAnimations;
    public final MotionLayout mMotionLayout;
    public HashSet mRelatedViews;
    public final ArrayList mViewTransitions = new ArrayList();
    public final String mTAG = "ViewTransitionController";
    public final ArrayList mRemoveList = new ArrayList();

    public ViewTransitionController(MotionLayout motionLayout) {
        this.mMotionLayout = motionLayout;
    }
}
