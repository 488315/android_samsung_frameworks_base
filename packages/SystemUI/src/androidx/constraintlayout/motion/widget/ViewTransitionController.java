package androidx.constraintlayout.motion.widget;

import java.util.ArrayList;
import java.util.HashSet;

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
