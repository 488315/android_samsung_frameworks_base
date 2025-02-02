package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class ChainHead {
    private boolean mDefined;
    protected ConstraintWidget mFirst;
    protected ConstraintWidget mFirstMatchConstraintWidget;
    protected ConstraintWidget mFirstVisibleWidget;
    protected boolean mHasComplexMatchWeights;
    protected boolean mHasDefinedWeights;
    protected boolean mHasUndefinedWeights;
    protected ConstraintWidget mHead;
    private boolean mIsRtl;
    protected ConstraintWidget mLast;
    protected ConstraintWidget mLastMatchConstraintWidget;
    protected ConstraintWidget mLastVisibleWidget;
    private int mOrientation;
    protected float mTotalWeight = 0.0f;
    protected ArrayList<ConstraintWidget> mWeightedMatchConstraintsWidgets;
    protected int mWidgetsCount;
    protected int mWidgetsMatchCount;

    public ChainHead(ConstraintWidget constraintWidget, int i, boolean z) {
        this.mFirst = constraintWidget;
        this.mOrientation = i;
        this.mIsRtl = z;
    }

    public final void define() {
        int i;
        int i2;
        if (!this.mDefined) {
            int i3 = this.mOrientation * 2;
            ConstraintWidget constraintWidget = this.mFirst;
            boolean z = false;
            ConstraintWidget constraintWidget2 = constraintWidget;
            boolean z2 = false;
            while (!z2) {
                this.mWidgetsCount++;
                ConstraintWidget[] constraintWidgetArr = constraintWidget.mNextChainWidget;
                int i4 = this.mOrientation;
                ConstraintWidget constraintWidget3 = null;
                constraintWidgetArr[i4] = null;
                constraintWidget.mListNextMatchConstraintsWidget[i4] = null;
                if (constraintWidget.getVisibility() != 8) {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.getDimensionBehaviour(this.mOrientation);
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    if (dimensionBehaviour != dimensionBehaviour2) {
                        int i5 = this.mOrientation;
                    }
                    constraintWidget.mListAnchors[i3].getMargin();
                    int i6 = i3 + 1;
                    constraintWidget.mListAnchors[i6].getMargin();
                    constraintWidget.mListAnchors[i3].getMargin();
                    constraintWidget.mListAnchors[i6].getMargin();
                    if (this.mFirstVisibleWidget == null) {
                        this.mFirstVisibleWidget = constraintWidget;
                    }
                    this.mLastVisibleWidget = constraintWidget;
                    ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
                    int i7 = this.mOrientation;
                    if (dimensionBehaviourArr[i7] == dimensionBehaviour2 && ((i = constraintWidget.mResolvedMatchConstraintDefault[i7]) == 0 || i == 3 || i == 2)) {
                        this.mWidgetsMatchCount++;
                        float f = constraintWidget.mWeight[i7];
                        if (f > 0.0f) {
                            this.mTotalWeight += f;
                        }
                        if (constraintWidget.getVisibility() != 8 && constraintWidget.mListDimensionBehaviors[i7] == dimensionBehaviour2 && ((i2 = constraintWidget.mResolvedMatchConstraintDefault[i7]) == 0 || i2 == 3)) {
                            if (f < 0.0f) {
                                this.mHasUndefinedWeights = true;
                            } else {
                                this.mHasDefinedWeights = true;
                            }
                            if (this.mWeightedMatchConstraintsWidgets == null) {
                                this.mWeightedMatchConstraintsWidgets = new ArrayList<>();
                            }
                            this.mWeightedMatchConstraintsWidgets.add(constraintWidget);
                        }
                        if (this.mFirstMatchConstraintWidget == null) {
                            this.mFirstMatchConstraintWidget = constraintWidget;
                        }
                        ConstraintWidget constraintWidget4 = this.mLastMatchConstraintWidget;
                        if (constraintWidget4 != null) {
                            constraintWidget4.mListNextMatchConstraintsWidget[this.mOrientation] = constraintWidget;
                        }
                        this.mLastMatchConstraintWidget = constraintWidget;
                    }
                }
                if (constraintWidget2 != constraintWidget) {
                    constraintWidget2.mNextChainWidget[this.mOrientation] = constraintWidget;
                }
                ConstraintAnchor constraintAnchor = constraintWidget.mListAnchors[i3 + 1].mTarget;
                if (constraintAnchor != null) {
                    ConstraintWidget constraintWidget5 = constraintAnchor.mOwner;
                    ConstraintAnchor constraintAnchor2 = constraintWidget5.mListAnchors[i3].mTarget;
                    if (constraintAnchor2 != null && constraintAnchor2.mOwner == constraintWidget) {
                        constraintWidget3 = constraintWidget5;
                    }
                }
                if (constraintWidget3 == null) {
                    z2 = true;
                    constraintWidget3 = constraintWidget;
                }
                constraintWidget2 = constraintWidget;
                constraintWidget = constraintWidget3;
            }
            ConstraintWidget constraintWidget6 = this.mFirstVisibleWidget;
            if (constraintWidget6 != null) {
                constraintWidget6.mListAnchors[i3].getMargin();
            }
            ConstraintWidget constraintWidget7 = this.mLastVisibleWidget;
            if (constraintWidget7 != null) {
                constraintWidget7.mListAnchors[i3 + 1].getMargin();
            }
            this.mLast = constraintWidget;
            if (this.mOrientation == 0 && this.mIsRtl) {
                this.mHead = constraintWidget;
            } else {
                this.mHead = this.mFirst;
            }
            if (this.mHasDefinedWeights && this.mHasUndefinedWeights) {
                z = true;
            }
            this.mHasComplexMatchWeights = z;
        }
        this.mDefined = true;
    }
}
