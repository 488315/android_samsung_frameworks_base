package androidx.constraintlayout.motion.widget;

import java.util.ArrayList;
import java.util.HashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewTransitionController {
    public ArrayList animations;
    public final MotionLayout mMotionLayout;
    public HashSet mRelatedViews;
    public final ArrayList viewTransitions = new ArrayList();
    public final String TAG = "ViewTransitionController";
    public final ArrayList removeList = new ArrayList();

    public ViewTransitionController(MotionLayout motionLayout) {
        this.mMotionLayout = motionLayout;
    }
}
