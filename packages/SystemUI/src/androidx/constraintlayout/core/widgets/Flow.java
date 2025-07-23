package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Flow extends VirtualLayout {
    public ConstraintWidget[] mDisplayedWidgets;
    public int mHorizontalStyle = -1;
    public int mVerticalStyle = -1;
    public int mFirstHorizontalStyle = -1;
    public int mFirstVerticalStyle = -1;
    public int mLastHorizontalStyle = -1;
    public int mLastVerticalStyle = -1;
    public float mHorizontalBias = 0.5f;
    public float mVerticalBias = 0.5f;
    public float mFirstHorizontalBias = 0.5f;
    public float mFirstVerticalBias = 0.5f;
    public float mLastHorizontalBias = 0.5f;
    public float mLastVerticalBias = 0.5f;
    public int mHorizontalGap = 0;
    public int mVerticalGap = 0;
    public int mHorizontalAlign = 2;
    public int mVerticalAlign = 2;
    public int mWrapMode = 0;
    public int mMaxElementsWrap = -1;
    public int mOrientation = 0;
    public final ArrayList mChainList = new ArrayList();
    public ConstraintWidget[] mAlignedBiggestElementsInRows = null;
    public ConstraintWidget[] mAlignedBiggestElementsInCols = null;
    public int[] mAlignedDimensions = null;
    public int mDisplayedWidgetsCount = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class WidgetsList {
        public ConstraintAnchor mBottom;
        public ConstraintAnchor mLeft;
        public int mMax;
        public int mOrientation;
        public int mPaddingBottom;
        public int mPaddingLeft;
        public int mPaddingRight;
        public int mPaddingTop;
        public ConstraintAnchor mRight;
        public ConstraintAnchor mTop;
        public ConstraintWidget mBiggest = null;
        public int mBiggestDimension = 0;
        public int mWidth = 0;
        public int mHeight = 0;
        public int mStartIndex = 0;
        public int mCount = 0;
        public int mNbMatchConstraintsWidgets = 0;

        public WidgetsList(int i, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i2) {
            this.mPaddingLeft = 0;
            this.mPaddingTop = 0;
            this.mPaddingRight = 0;
            this.mPaddingBottom = 0;
            this.mMax = 0;
            this.mOrientation = i;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = Flow.this.mResolvedPaddingLeft;
            this.mPaddingTop = Flow.this.mPaddingTop;
            this.mPaddingRight = Flow.this.mResolvedPaddingRight;
            this.mPaddingBottom = Flow.this.mPaddingBottom;
            this.mMax = i2;
        }

        public final void add(ConstraintWidget constraintWidget) {
            int i = this.mOrientation;
            Flow flow = Flow.this;
            if (i == 0) {
                int widgetWidth = flow.getWidgetWidth(constraintWidget, this.mMax);
                if (constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mNbMatchConstraintsWidgets++;
                    widgetWidth = 0;
                }
                this.mWidth = widgetWidth + (constraintWidget.mVisibility != 8 ? flow.mHorizontalGap : 0) + this.mWidth;
                int widgetHeight = flow.getWidgetHeight(constraintWidget, this.mMax);
                if (this.mBiggest == null || this.mBiggestDimension < widgetHeight) {
                    this.mBiggest = constraintWidget;
                    this.mBiggestDimension = widgetHeight;
                    this.mHeight = widgetHeight;
                }
            } else {
                int widgetWidth2 = flow.getWidgetWidth(constraintWidget, this.mMax);
                int widgetHeight2 = flow.getWidgetHeight(constraintWidget, this.mMax);
                if (constraintWidget.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mNbMatchConstraintsWidgets++;
                    widgetHeight2 = 0;
                }
                this.mHeight = widgetHeight2 + (constraintWidget.mVisibility != 8 ? flow.mVerticalGap : 0) + this.mHeight;
                if (this.mBiggest == null || this.mBiggestDimension < widgetWidth2) {
                    this.mBiggest = constraintWidget;
                    this.mBiggestDimension = widgetWidth2;
                    this.mWidth = widgetWidth2;
                }
            }
            this.mCount++;
        }

        public final void createConstraints(int i, boolean z, boolean z2) {
            Flow flow;
            int i2;
            int i3;
            ConstraintWidget constraintWidget;
            boolean z3;
            char c;
            float f;
            float f2;
            int i4;
            float f3;
            float f4;
            int i5;
            int i6 = this.mCount;
            int i7 = 0;
            while (true) {
                flow = Flow.this;
                if (i7 >= i6 || (i5 = this.mStartIndex + i7) >= flow.mDisplayedWidgetsCount) {
                    break;
                }
                ConstraintWidget constraintWidget2 = flow.mDisplayedWidgets[i5];
                if (constraintWidget2 != null) {
                    constraintWidget2.resetAnchors();
                }
                i7++;
            }
            if (i6 == 0 || this.mBiggest == null) {
                return;
            }
            boolean z4 = z2 && i == 0;
            int i8 = -1;
            int i9 = -1;
            for (int i10 = 0; i10 < i6; i10++) {
                int i11 = this.mStartIndex + (z ? (i6 - 1) - i10 : i10);
                if (i11 >= flow.mDisplayedWidgetsCount) {
                    break;
                }
                ConstraintWidget constraintWidget3 = flow.mDisplayedWidgets[i11];
                if (constraintWidget3 != null && constraintWidget3.mVisibility == 0) {
                    if (i8 == -1) {
                        i8 = i10;
                    }
                    i9 = i10;
                }
            }
            if (this.mOrientation != 0) {
                boolean z5 = z4;
                ConstraintWidget constraintWidget4 = this.mBiggest;
                constraintWidget4.mHorizontalChainStyle = flow.mHorizontalStyle;
                int i12 = this.mPaddingLeft;
                if (i > 0) {
                    i12 += flow.mHorizontalGap;
                }
                ConstraintAnchor constraintAnchor = constraintWidget4.mLeft;
                ConstraintAnchor constraintAnchor2 = constraintWidget4.mRight;
                if (z) {
                    constraintAnchor2.connect(this.mRight, i12);
                    if (z2) {
                        constraintAnchor.connect(this.mLeft, this.mPaddingRight);
                    }
                    if (i > 0) {
                        this.mRight.mOwner.mLeft.connect(constraintAnchor2, 0);
                    }
                } else {
                    constraintAnchor.connect(this.mLeft, i12);
                    if (z2) {
                        constraintAnchor2.connect(this.mRight, this.mPaddingRight);
                    }
                    if (i > 0) {
                        this.mLeft.mOwner.mRight.connect(constraintAnchor, 0);
                    }
                }
                ConstraintWidget constraintWidget5 = null;
                for (int i13 = 0; i13 < i6; i13++) {
                    int i14 = this.mStartIndex + i13;
                    if (i14 >= flow.mDisplayedWidgetsCount) {
                        return;
                    }
                    ConstraintWidget constraintWidget6 = flow.mDisplayedWidgets[i14];
                    if (constraintWidget6 != null) {
                        ConstraintAnchor constraintAnchor3 = constraintWidget6.mTop;
                        if (i13 == 0) {
                            constraintWidget6.connect(constraintAnchor3, this.mTop, this.mPaddingTop);
                            int i15 = flow.mVerticalStyle;
                            float f5 = flow.mVerticalBias;
                            if (this.mStartIndex == 0) {
                                i3 = flow.mFirstVerticalStyle;
                                i2 = -1;
                                if (i3 != -1) {
                                    f5 = flow.mFirstVerticalBias;
                                    i15 = i3;
                                    constraintWidget6.mVerticalChainStyle = i15;
                                    constraintWidget6.mVerticalBiasPercent = f5;
                                }
                            } else {
                                i2 = -1;
                            }
                            if (z2 && (i3 = flow.mLastVerticalStyle) != i2) {
                                f5 = flow.mLastVerticalBias;
                                i15 = i3;
                            }
                            constraintWidget6.mVerticalChainStyle = i15;
                            constraintWidget6.mVerticalBiasPercent = f5;
                        }
                        if (i13 == i6 - 1) {
                            constraintWidget6.connect(constraintWidget6.mBottom, this.mBottom, this.mPaddingBottom);
                        }
                        if (constraintWidget5 != null) {
                            int i16 = flow.mVerticalGap;
                            ConstraintAnchor constraintAnchor4 = constraintWidget5.mBottom;
                            constraintAnchor3.connect(constraintAnchor4, i16);
                            if (i13 == i8) {
                                int i17 = this.mPaddingTop;
                                if (constraintAnchor3.isConnected()) {
                                    constraintAnchor3.mGoneMargin = i17;
                                }
                            }
                            constraintAnchor4.connect(constraintAnchor3, 0);
                            if (i13 == i9 + 1) {
                                int i18 = this.mPaddingBottom;
                                if (constraintAnchor4.isConnected()) {
                                    constraintAnchor4.mGoneMargin = i18;
                                }
                            }
                        }
                        if (constraintWidget6 != constraintWidget4) {
                            ConstraintAnchor constraintAnchor5 = constraintWidget6.mRight;
                            ConstraintAnchor constraintAnchor6 = constraintWidget6.mLeft;
                            if (z) {
                                int i19 = flow.mHorizontalAlign;
                                if (i19 == 0) {
                                    constraintAnchor5.connect(constraintAnchor2, 0);
                                } else if (i19 == 1) {
                                    constraintAnchor6.connect(constraintAnchor, 0);
                                } else if (i19 == 2) {
                                    constraintAnchor6.connect(constraintAnchor, 0);
                                    constraintAnchor5.connect(constraintAnchor2, 0);
                                }
                            } else {
                                int i20 = flow.mHorizontalAlign;
                                if (i20 == 0) {
                                    constraintAnchor6.connect(constraintAnchor, 0);
                                } else if (i20 == 1) {
                                    constraintAnchor5.connect(constraintAnchor2, 0);
                                } else if (i20 == 2) {
                                    if (z5) {
                                        constraintAnchor6.connect(this.mLeft, this.mPaddingLeft);
                                        constraintAnchor5.connect(this.mRight, this.mPaddingRight);
                                    } else {
                                        constraintAnchor6.connect(constraintAnchor, 0);
                                        constraintAnchor5.connect(constraintAnchor2, 0);
                                    }
                                }
                                constraintWidget5 = constraintWidget6;
                            }
                        }
                        constraintWidget5 = constraintWidget6;
                    }
                }
                return;
            }
            ConstraintWidget constraintWidget7 = this.mBiggest;
            constraintWidget7.mVerticalChainStyle = flow.mVerticalStyle;
            int i21 = this.mPaddingTop;
            if (i > 0) {
                i21 += flow.mVerticalGap;
            }
            ConstraintAnchor constraintAnchor7 = this.mTop;
            ConstraintAnchor constraintAnchor8 = constraintWidget7.mTop;
            constraintAnchor8.connect(constraintAnchor7, i21);
            ConstraintAnchor constraintAnchor9 = constraintWidget7.mBottom;
            if (z2) {
                constraintAnchor9.connect(this.mBottom, this.mPaddingBottom);
            }
            if (i > 0) {
                this.mTop.mOwner.mBottom.connect(constraintAnchor8, 0);
            }
            if (flow.mVerticalAlign == 3 && !constraintWidget7.mHasBaseline) {
                for (int i22 = 0; i22 < i6; i22++) {
                    int i23 = this.mStartIndex + (z ? (i6 - 1) - i22 : i22);
                    if (i23 >= flow.mDisplayedWidgetsCount) {
                        break;
                    }
                    constraintWidget = flow.mDisplayedWidgets[i23];
                    if (constraintWidget.mHasBaseline) {
                        break;
                    }
                }
            }
            constraintWidget = constraintWidget7;
            int i24 = 0;
            ConstraintWidget constraintWidget8 = null;
            while (i24 < i6) {
                int i25 = z ? (i6 - 1) - i24 : i24;
                int i26 = this.mStartIndex + i25;
                if (i26 >= flow.mDisplayedWidgetsCount) {
                    return;
                }
                ConstraintWidget constraintWidget9 = flow.mDisplayedWidgets[i26];
                if (constraintWidget9 == null) {
                    z3 = z4;
                    c = 3;
                } else {
                    ConstraintAnchor constraintAnchor10 = constraintWidget9.mLeft;
                    if (i24 == 0) {
                        constraintWidget9.connect(constraintAnchor10, this.mLeft, this.mPaddingLeft);
                    }
                    if (i25 == 0) {
                        int i27 = flow.mHorizontalStyle;
                        if (z) {
                            f = 1.0f;
                            f2 = 1.0f - flow.mHorizontalBias;
                        } else {
                            f = 1.0f;
                            f2 = flow.mHorizontalBias;
                        }
                        if (this.mStartIndex == 0) {
                            i4 = flow.mFirstHorizontalStyle;
                            z3 = z4;
                            if (i4 != -1) {
                                if (z) {
                                    f4 = flow.mFirstHorizontalBias;
                                    f2 = f - f4;
                                    constraintWidget9.mHorizontalChainStyle = i4;
                                    constraintWidget9.mHorizontalBiasPercent = f2;
                                } else {
                                    f3 = flow.mFirstHorizontalBias;
                                    f2 = f3;
                                    constraintWidget9.mHorizontalChainStyle = i4;
                                    constraintWidget9.mHorizontalBiasPercent = f2;
                                }
                            }
                        } else {
                            z3 = z4;
                        }
                        if (!z2 || (i4 = flow.mLastHorizontalStyle) == -1) {
                            i4 = i27;
                            constraintWidget9.mHorizontalChainStyle = i4;
                            constraintWidget9.mHorizontalBiasPercent = f2;
                        } else if (z) {
                            f4 = flow.mLastHorizontalBias;
                            f2 = f - f4;
                            constraintWidget9.mHorizontalChainStyle = i4;
                            constraintWidget9.mHorizontalBiasPercent = f2;
                        } else {
                            f3 = flow.mLastHorizontalBias;
                            f2 = f3;
                            constraintWidget9.mHorizontalChainStyle = i4;
                            constraintWidget9.mHorizontalBiasPercent = f2;
                        }
                    } else {
                        z3 = z4;
                    }
                    if (i24 == i6 - 1) {
                        constraintWidget9.connect(constraintWidget9.mRight, this.mRight, this.mPaddingRight);
                    }
                    if (constraintWidget8 != null) {
                        int i28 = flow.mHorizontalGap;
                        ConstraintAnchor constraintAnchor11 = constraintWidget8.mRight;
                        constraintAnchor10.connect(constraintAnchor11, i28);
                        if (i24 == i8) {
                            int i29 = this.mPaddingLeft;
                            if (constraintAnchor10.isConnected()) {
                                constraintAnchor10.mGoneMargin = i29;
                            }
                        }
                        constraintAnchor11.connect(constraintAnchor10, 0);
                        if (i24 == i9 + 1) {
                            int i30 = this.mPaddingRight;
                            if (constraintAnchor11.isConnected()) {
                                constraintAnchor11.mGoneMargin = i30;
                            }
                        }
                    }
                    if (constraintWidget9 != constraintWidget7) {
                        int i31 = flow.mVerticalAlign;
                        c = 3;
                        if (i31 == 3 && constraintWidget.mHasBaseline && constraintWidget9 != constraintWidget && constraintWidget9.mHasBaseline) {
                            constraintWidget9.mBaseline.connect(constraintWidget.mBaseline, 0);
                        } else {
                            ConstraintAnchor constraintAnchor12 = constraintWidget9.mTop;
                            if (i31 != 0) {
                                ConstraintAnchor constraintAnchor13 = constraintWidget9.mBottom;
                                if (i31 == 1) {
                                    constraintAnchor13.connect(constraintAnchor9, 0);
                                } else if (z3) {
                                    constraintAnchor12.connect(this.mTop, this.mPaddingTop);
                                    constraintAnchor13.connect(this.mBottom, this.mPaddingBottom);
                                } else {
                                    constraintAnchor12.connect(constraintAnchor8, 0);
                                    constraintAnchor13.connect(constraintAnchor9, 0);
                                }
                            } else {
                                constraintAnchor12.connect(constraintAnchor8, 0);
                            }
                        }
                    } else {
                        c = 3;
                    }
                    constraintWidget8 = constraintWidget9;
                }
                i24++;
                z4 = z3;
            }
        }

        public final int getHeight() {
            return this.mOrientation == 1 ? this.mHeight - Flow.this.mVerticalGap : this.mHeight;
        }

        public final int getWidth() {
            return this.mOrientation == 0 ? this.mWidth - Flow.this.mHorizontalGap : this.mWidth;
        }

        public final void measureMatchConstraints(int i) {
            int i2 = this.mNbMatchConstraintsWidgets;
            if (i2 == 0) {
                return;
            }
            int i3 = this.mCount;
            int i4 = i / i2;
            for (int i5 = 0; i5 < i3; i5++) {
                int i6 = this.mStartIndex;
                int i7 = i6 + i5;
                Flow flow = Flow.this;
                if (i7 >= flow.mDisplayedWidgetsCount) {
                    break;
                }
                ConstraintWidget constraintWidget = flow.mDisplayedWidgets[i6 + i5];
                if (this.mOrientation == 0) {
                    if (constraintWidget != null) {
                        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
                        if (dimensionBehaviourArr[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0) {
                            flow.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i4, dimensionBehaviourArr[1], constraintWidget.getHeight());
                        }
                    }
                } else if (constraintWidget != null) {
                    ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = constraintWidget.mListDimensionBehaviors;
                    if (dimensionBehaviourArr2[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                        int i8 = i4;
                        flow.measure(constraintWidget, dimensionBehaviourArr2[0], constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i8);
                        i4 = i8;
                    }
                }
            }
            this.mWidth = 0;
            this.mHeight = 0;
            this.mBiggest = null;
            this.mBiggestDimension = 0;
            int i9 = this.mCount;
            for (int i10 = 0; i10 < i9; i10++) {
                int i11 = this.mStartIndex + i10;
                Flow flow2 = Flow.this;
                if (i11 >= flow2.mDisplayedWidgetsCount) {
                    return;
                }
                ConstraintWidget constraintWidget2 = flow2.mDisplayedWidgets[i11];
                if (this.mOrientation == 0) {
                    int width = constraintWidget2.getWidth();
                    int i12 = flow2.mHorizontalGap;
                    if (constraintWidget2.mVisibility == 8) {
                        i12 = 0;
                    }
                    this.mWidth = width + i12 + this.mWidth;
                    int widgetHeight = flow2.getWidgetHeight(constraintWidget2, this.mMax);
                    if (this.mBiggest == null || this.mBiggestDimension < widgetHeight) {
                        this.mBiggest = constraintWidget2;
                        this.mBiggestDimension = widgetHeight;
                        this.mHeight = widgetHeight;
                    }
                } else {
                    int widgetWidth = flow2.getWidgetWidth(constraintWidget2, this.mMax);
                    int widgetHeight2 = flow2.getWidgetHeight(constraintWidget2, this.mMax);
                    int i13 = flow2.mVerticalGap;
                    if (constraintWidget2.mVisibility == 8) {
                        i13 = 0;
                    }
                    this.mHeight = widgetHeight2 + i13 + this.mHeight;
                    if (this.mBiggest == null || this.mBiggestDimension < widgetWidth) {
                        this.mBiggest = constraintWidget2;
                        this.mBiggestDimension = widgetWidth;
                        this.mWidth = widgetWidth;
                    }
                }
            }
        }

        public final void setup(int i, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i2, int i3, int i4, int i5, int i6) {
            this.mOrientation = i;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = i2;
            this.mPaddingTop = i3;
            this.mPaddingRight = i4;
            this.mPaddingBottom = i5;
            this.mMax = i6;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final void addToSolver(LinearSystem linearSystem, boolean z) {
        ConstraintWidget constraintWidget;
        float f;
        int i;
        super.addToSolver(linearSystem, z);
        ConstraintWidget constraintWidget2 = this.mParent;
        boolean z2 = constraintWidget2 != null && ((ConstraintWidgetContainer) constraintWidget2).mIsRtl;
        int i2 = this.mWrapMode;
        if (i2 != 0) {
            if (i2 == 1) {
                int size = this.mChainList.size();
                int i3 = 0;
                while (i3 < size) {
                    ((WidgetsList) this.mChainList.get(i3)).createConstraints(i3, z2, i3 == size + (-1));
                    i3++;
                }
            } else if (i2 != 2) {
                if (i2 == 3) {
                    int size2 = this.mChainList.size();
                    int i4 = 0;
                    while (i4 < size2) {
                        ((WidgetsList) this.mChainList.get(i4)).createConstraints(i4, z2, i4 == size2 + (-1));
                        i4++;
                    }
                }
            } else if (this.mAlignedDimensions != null && this.mAlignedBiggestElementsInCols != null && this.mAlignedBiggestElementsInRows != null) {
                for (int i5 = 0; i5 < this.mDisplayedWidgetsCount; i5++) {
                    this.mDisplayedWidgets[i5].resetAnchors();
                }
                int[] iArr = this.mAlignedDimensions;
                int i6 = iArr[0];
                int i7 = iArr[1];
                float f2 = this.mHorizontalBias;
                ConstraintWidget constraintWidget3 = null;
                int i8 = 0;
                while (i8 < i6) {
                    if (z2) {
                        i = (i6 - i8) - 1;
                        f = 1.0f - this.mHorizontalBias;
                    } else {
                        f = f2;
                        i = i8;
                    }
                    ConstraintWidget constraintWidget4 = this.mAlignedBiggestElementsInCols[i];
                    if (constraintWidget4 != null && constraintWidget4.mVisibility != 8) {
                        ConstraintAnchor constraintAnchor = constraintWidget4.mLeft;
                        if (i8 == 0) {
                            constraintWidget4.connect(constraintAnchor, this.mLeft, this.mResolvedPaddingLeft);
                            constraintWidget4.mHorizontalChainStyle = this.mHorizontalStyle;
                            constraintWidget4.mHorizontalBiasPercent = f;
                        }
                        if (i8 == i6 - 1) {
                            constraintWidget4.connect(constraintWidget4.mRight, this.mRight, this.mResolvedPaddingRight);
                        }
                        if (i8 > 0 && constraintWidget3 != null) {
                            int i9 = this.mHorizontalGap;
                            ConstraintAnchor constraintAnchor2 = constraintWidget3.mRight;
                            constraintWidget4.connect(constraintAnchor, constraintAnchor2, i9);
                            constraintWidget3.connect(constraintAnchor2, constraintAnchor, 0);
                        }
                        constraintWidget3 = constraintWidget4;
                    }
                    i8++;
                    f2 = f;
                }
                for (int i10 = 0; i10 < i7; i10++) {
                    ConstraintWidget constraintWidget5 = this.mAlignedBiggestElementsInRows[i10];
                    if (constraintWidget5 != null && constraintWidget5.mVisibility != 8) {
                        ConstraintAnchor constraintAnchor3 = constraintWidget5.mTop;
                        if (i10 == 0) {
                            constraintWidget5.connect(constraintAnchor3, this.mTop, this.mPaddingTop);
                            constraintWidget5.mVerticalChainStyle = this.mVerticalStyle;
                            constraintWidget5.mVerticalBiasPercent = this.mVerticalBias;
                        }
                        if (i10 == i7 - 1) {
                            constraintWidget5.connect(constraintWidget5.mBottom, this.mBottom, this.mPaddingBottom);
                        }
                        if (i10 > 0 && constraintWidget3 != null) {
                            int i11 = this.mVerticalGap;
                            ConstraintAnchor constraintAnchor4 = constraintWidget3.mBottom;
                            constraintWidget5.connect(constraintAnchor3, constraintAnchor4, i11);
                            constraintWidget3.connect(constraintAnchor4, constraintAnchor3, 0);
                        }
                        constraintWidget3 = constraintWidget5;
                    }
                }
                for (int i12 = 0; i12 < i6; i12++) {
                    for (int i13 = 0; i13 < i7; i13++) {
                        int i14 = (i13 * i6) + i12;
                        if (this.mOrientation == 1) {
                            i14 = (i12 * i7) + i13;
                        }
                        ConstraintWidget[] constraintWidgetArr = this.mDisplayedWidgets;
                        if (i14 < constraintWidgetArr.length && (constraintWidget = constraintWidgetArr[i14]) != null && constraintWidget.mVisibility != 8) {
                            ConstraintWidget constraintWidget6 = this.mAlignedBiggestElementsInCols[i12];
                            ConstraintWidget constraintWidget7 = this.mAlignedBiggestElementsInRows[i13];
                            if (constraintWidget != constraintWidget6) {
                                constraintWidget.connect(constraintWidget.mLeft, constraintWidget6.mLeft, 0);
                                constraintWidget.connect(constraintWidget.mRight, constraintWidget6.mRight, 0);
                            }
                            if (constraintWidget != constraintWidget7) {
                                constraintWidget.connect(constraintWidget.mTop, constraintWidget7.mTop, 0);
                                constraintWidget.connect(constraintWidget.mBottom, constraintWidget7.mBottom, 0);
                            }
                        }
                    }
                }
            }
        } else if (this.mChainList.size() > 0) {
            ((WidgetsList) this.mChainList.get(0)).createConstraints(0, z2, true);
        }
        this.mNeedsCallFromSolver = false;
    }

    @Override // androidx.constraintlayout.core.widgets.HelperWidget, androidx.constraintlayout.core.widgets.ConstraintWidget
    public final void copy(ConstraintWidget constraintWidget, HashMap hashMap) {
        super.copy(constraintWidget, hashMap);
        Flow flow = (Flow) constraintWidget;
        this.mHorizontalStyle = flow.mHorizontalStyle;
        this.mVerticalStyle = flow.mVerticalStyle;
        this.mFirstHorizontalStyle = flow.mFirstHorizontalStyle;
        this.mFirstVerticalStyle = flow.mFirstVerticalStyle;
        this.mLastHorizontalStyle = flow.mLastHorizontalStyle;
        this.mLastVerticalStyle = flow.mLastVerticalStyle;
        this.mHorizontalBias = flow.mHorizontalBias;
        this.mVerticalBias = flow.mVerticalBias;
        this.mFirstHorizontalBias = flow.mFirstHorizontalBias;
        this.mFirstVerticalBias = flow.mFirstVerticalBias;
        this.mLastHorizontalBias = flow.mLastHorizontalBias;
        this.mLastVerticalBias = flow.mLastVerticalBias;
        this.mHorizontalGap = flow.mHorizontalGap;
        this.mVerticalGap = flow.mVerticalGap;
        this.mHorizontalAlign = flow.mHorizontalAlign;
        this.mVerticalAlign = flow.mVerticalAlign;
        this.mWrapMode = flow.mWrapMode;
        this.mMaxElementsWrap = flow.mMaxElementsWrap;
        this.mOrientation = flow.mOrientation;
    }

    public final int getWidgetHeight(ConstraintWidget constraintWidget, int i) {
        ConstraintWidget constraintWidget2;
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int i2 = constraintWidget.mMatchConstraintDefaultHeight;
            if (i2 == 0) {
                return 0;
            }
            if (i2 == 2) {
                int i3 = (int) (constraintWidget.mMatchConstraintPercentHeight * i);
                if (i3 != constraintWidget.getHeight()) {
                    constraintWidget.mMeasureRequested = true;
                    measure(constraintWidget, constraintWidget.mListDimensionBehaviors[0], constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i3);
                }
                return i3;
            }
            constraintWidget2 = constraintWidget;
            if (i2 == 1) {
                return constraintWidget2.getHeight();
            }
            if (i2 == 3) {
                return (int) ((constraintWidget2.getWidth() * constraintWidget2.mDimensionRatio) + 0.5f);
            }
        } else {
            constraintWidget2 = constraintWidget;
        }
        return constraintWidget2.getHeight();
    }

    public final int getWidgetWidth(ConstraintWidget constraintWidget, int i) {
        ConstraintWidget constraintWidget2;
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int i2 = constraintWidget.mMatchConstraintDefaultWidth;
            if (i2 == 0) {
                return 0;
            }
            if (i2 == 2) {
                int i3 = (int) (constraintWidget.mMatchConstraintPercentWidth * i);
                if (i3 != constraintWidget.getWidth()) {
                    constraintWidget.mMeasureRequested = true;
                    measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i3, constraintWidget.mListDimensionBehaviors[1], constraintWidget.getHeight());
                }
                return i3;
            }
            constraintWidget2 = constraintWidget;
            if (i2 == 1) {
                return constraintWidget2.getWidth();
            }
            if (i2 == 3) {
                return (int) ((constraintWidget2.getHeight() * constraintWidget2.mDimensionRatio) + 0.5f);
            }
        } else {
            constraintWidget2 = constraintWidget;
        }
        return constraintWidget2.getWidth();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0703  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0713  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x072f  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0732  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0715  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0705  */
    @Override // androidx.constraintlayout.core.widgets.VirtualLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void measure(int r37, int r38, int r39, int r40) {
        /*
            Method dump skipped, instructions count: 1847
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.Flow.measure(int, int, int, int):void");
    }
}
