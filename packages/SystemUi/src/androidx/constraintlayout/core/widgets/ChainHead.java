package androidx.constraintlayout.core.widgets;

import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ChainHead {
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
        this.mIsRtl = false;
        this.mFirst = constraintWidget;
        this.mOrientation = i;
        this.mIsRtl = z;
    }
}
