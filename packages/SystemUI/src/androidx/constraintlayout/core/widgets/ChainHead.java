package androidx.constraintlayout.core.widgets;

import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ChainHead {
    public boolean mDefined;
    public final ConstraintWidget mFirst;
    public ConstraintWidget mFirstMatchConstraintWidget;
    public ConstraintWidget mFirstVisibleWidget;
    public boolean mHasComplexMatchWeights;
    public boolean mHasDefinedWeights;
    public boolean mHasUndefinedWeights;
    public ConstraintWidget mHead;
    public final boolean mIsRtl;
    public ConstraintWidget mLast;
    public ConstraintWidget mLastMatchConstraintWidget;
    public ConstraintWidget mLastVisibleWidget;
    public final int mOrientation;
    public float mTotalWeight = 0.0f;
    public ArrayList mWeightedMatchConstraintsWidgets;
    public int mWidgetsCount;
    public int mWidgetsMatchCount;

    public ChainHead(ConstraintWidget constraintWidget, int i, boolean z) {
        this.mFirst = constraintWidget;
        this.mOrientation = i;
        this.mIsRtl = z;
    }
}
