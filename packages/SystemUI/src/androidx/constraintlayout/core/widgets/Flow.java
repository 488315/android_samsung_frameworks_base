package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
            ConstraintWidget constraintWidget;
            boolean z3;
            char c;
            float f;
            float f2;
            int i3;
            float f3;
            float f4;
            int i4;
            int i5 = this.mCount;
            int i6 = 0;
            while (true) {
                flow = Flow.this;
                if (i6 >= i5 || (i4 = this.mStartIndex + i6) >= flow.mDisplayedWidgetsCount) {
                    break;
                }
                ConstraintWidget constraintWidget2 = flow.mDisplayedWidgets[i4];
                if (constraintWidget2 != null) {
                    constraintWidget2.resetAnchors();
                }
                i6++;
            }
            if (i5 == 0 || this.mBiggest == null) {
                return;
            }
            boolean z4 = z2 && i == 0;
            int i7 = -1;
            int i8 = -1;
            for (int i9 = 0; i9 < i5; i9++) {
                int i10 = this.mStartIndex + (z ? (i5 - 1) - i9 : i9);
                if (i10 >= flow.mDisplayedWidgetsCount) {
                    break;
                }
                ConstraintWidget constraintWidget3 = flow.mDisplayedWidgets[i10];
                if (constraintWidget3 != null && constraintWidget3.mVisibility == 0) {
                    if (i7 == -1) {
                        i7 = i9;
                    }
                    i8 = i9;
                }
            }
            if (this.mOrientation != 0) {
                boolean z5 = z4;
                ConstraintWidget constraintWidget4 = this.mBiggest;
                constraintWidget4.mHorizontalChainStyle = flow.mHorizontalStyle;
                int i11 = this.mPaddingLeft;
                if (i > 0) {
                    i11 += flow.mHorizontalGap;
                }
                ConstraintAnchor constraintAnchor = constraintWidget4.mLeft;
                ConstraintAnchor constraintAnchor2 = constraintWidget4.mRight;
                if (z) {
                    constraintAnchor2.connect(this.mRight, i11);
                    if (z2) {
                        constraintAnchor.connect(this.mLeft, this.mPaddingRight);
                    }
                    if (i > 0) {
                        this.mRight.mOwner.mLeft.connect(constraintAnchor2, 0);
                    }
                } else {
                    constraintAnchor.connect(this.mLeft, i11);
                    if (z2) {
                        constraintAnchor2.connect(this.mRight, this.mPaddingRight);
                    }
                    if (i > 0) {
                        this.mLeft.mOwner.mRight.connect(constraintAnchor, 0);
                    }
                }
                ConstraintWidget constraintWidget5 = null;
                for (int i12 = 0; i12 < i5; i12++) {
                    int i13 = this.mStartIndex + i12;
                    if (i13 >= flow.mDisplayedWidgetsCount) {
                        return;
                    }
                    ConstraintWidget constraintWidget6 = flow.mDisplayedWidgets[i13];
                    if (constraintWidget6 != null) {
                        ConstraintAnchor constraintAnchor3 = constraintWidget6.mTop;
                        if (i12 == 0) {
                            constraintWidget6.connect(constraintAnchor3, this.mTop, this.mPaddingTop);
                            int i14 = flow.mVerticalStyle;
                            float f5 = flow.mVerticalBias;
                            if (this.mStartIndex == 0) {
                                int i15 = flow.mFirstVerticalStyle;
                                i2 = -1;
                                if (i15 != -1) {
                                    f5 = flow.mFirstVerticalBias;
                                }
                                i14 = i15;
                                constraintWidget6.mVerticalChainStyle = i14;
                                constraintWidget6.mVerticalBiasPercent = f5;
                            } else {
                                i2 = -1;
                            }
                            if (z2 && (i15 = flow.mLastVerticalStyle) != i2) {
                                f5 = flow.mLastVerticalBias;
                                i14 = i15;
                            }
                            constraintWidget6.mVerticalChainStyle = i14;
                            constraintWidget6.mVerticalBiasPercent = f5;
                        }
                        if (i12 == i5 - 1) {
                            constraintWidget6.connect(constraintWidget6.mBottom, this.mBottom, this.mPaddingBottom);
                        }
                        if (constraintWidget5 != null) {
                            int i16 = flow.mVerticalGap;
                            ConstraintAnchor constraintAnchor4 = constraintWidget5.mBottom;
                            constraintAnchor3.connect(constraintAnchor4, i16);
                            if (i12 == i7) {
                                int i17 = this.mPaddingTop;
                                if (constraintAnchor3.isConnected()) {
                                    constraintAnchor3.mGoneMargin = i17;
                                }
                            }
                            constraintAnchor4.connect(constraintAnchor3, 0);
                            if (i12 == i8 + 1) {
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
                                constraintWidget5 = constraintWidget6;
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
                        } else {
                            constraintWidget5 = constraintWidget6;
                        }
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
            if (flow.mVerticalAlign != 3 || constraintWidget7.mHasBaseline) {
                constraintWidget = constraintWidget7;
            } else {
                for (int i22 = 0; i22 < i5; i22++) {
                    int i23 = this.mStartIndex + (z ? (i5 - 1) - i22 : i22);
                    if (i23 >= flow.mDisplayedWidgetsCount) {
                        break;
                    }
                    constraintWidget = flow.mDisplayedWidgets[i23];
                    if (constraintWidget.mHasBaseline) {
                        break;
                    }
                }
                constraintWidget = constraintWidget7;
            }
            int i24 = 0;
            ConstraintWidget constraintWidget8 = null;
            while (i24 < i5) {
                int i25 = z ? (i5 - 1) - i24 : i24;
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
                            i3 = flow.mFirstHorizontalStyle;
                            z3 = z4;
                            if (i3 != -1) {
                                if (z) {
                                    f4 = flow.mFirstHorizontalBias;
                                    f2 = f - f4;
                                } else {
                                    f3 = flow.mFirstHorizontalBias;
                                    f2 = f3;
                                }
                            }
                            constraintWidget9.mHorizontalChainStyle = i3;
                            constraintWidget9.mHorizontalBiasPercent = f2;
                        } else {
                            z3 = z4;
                        }
                        if (!z2 || (i3 = flow.mLastHorizontalStyle) == -1) {
                            i3 = i27;
                            constraintWidget9.mHorizontalChainStyle = i3;
                            constraintWidget9.mHorizontalBiasPercent = f2;
                        } else if (z) {
                            f4 = flow.mLastHorizontalBias;
                            f2 = f - f4;
                            constraintWidget9.mHorizontalChainStyle = i3;
                            constraintWidget9.mHorizontalBiasPercent = f2;
                        } else {
                            f3 = flow.mLastHorizontalBias;
                            f2 = f3;
                            constraintWidget9.mHorizontalChainStyle = i3;
                            constraintWidget9.mHorizontalBiasPercent = f2;
                        }
                    } else {
                        z3 = z4;
                    }
                    if (i24 == i5 - 1) {
                        constraintWidget9.connect(constraintWidget9.mRight, this.mRight, this.mPaddingRight);
                    }
                    if (constraintWidget8 != null) {
                        int i28 = flow.mHorizontalGap;
                        ConstraintAnchor constraintAnchor11 = constraintWidget8.mRight;
                        constraintAnchor10.connect(constraintAnchor11, i28);
                        if (i24 == i7) {
                            int i29 = this.mPaddingLeft;
                            if (constraintAnchor10.isConnected()) {
                                constraintAnchor10.mGoneMargin = i29;
                            }
                        }
                        constraintAnchor11.connect(constraintAnchor10, 0);
                        if (i24 == i8 + 1) {
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
    public final void copy(ConstraintWidget constraintWidget, HashMap map) {
        super.copy(constraintWidget, map);
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
    /* JADX WARN: Removed duplicated region for block: B:281:0x0466  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x0703  */
    /* JADX WARN: Removed duplicated region for block: B:396:0x0705  */
    /* JADX WARN: Removed duplicated region for block: B:402:0x0713  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x0715  */
    /* JADX WARN: Removed duplicated region for block: B:410:0x072f  */
    /* JADX WARN: Removed duplicated region for block: B:411:0x0732  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0103  */
    @Override // androidx.constraintlayout.core.widgets.VirtualLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void measure(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int[] iArr;
        int i8;
        int i9;
        WidgetsList widgetsList;
        char c;
        int i10;
        int i11;
        int i12;
        int iCeil;
        int iCeil2;
        Object obj;
        ConstraintWidget constraintWidget;
        int i13;
        ConstraintAnchor constraintAnchor;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        if (this.mWidgetsCount > 0) {
            ConstraintWidget constraintWidget2 = this.mParent;
            BasicMeasure.Measurer measurer = constraintWidget2 != null ? ((ConstraintWidgetContainer) constraintWidget2).mMeasurer : null;
            if (measurer == null) {
                this.mMeasuredWidth = 0;
                this.mMeasuredHeight = 0;
                this.mNeedsCallFromSolver = false;
                return;
            }
            for (int i19 = 0; i19 < this.mWidgetsCount; i19++) {
                ConstraintWidget constraintWidget3 = this.mWidgets[i19];
                if (constraintWidget3 != null && !(constraintWidget3 instanceof Guideline)) {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget3.getDimensionBehaviour(0);
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget3.getDimensionBehaviour(1);
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    if (dimensionBehaviour != dimensionBehaviour3 || constraintWidget3.mMatchConstraintDefaultWidth == 1 || dimensionBehaviour2 != dimensionBehaviour3 || constraintWidget3.mMatchConstraintDefaultHeight == 1) {
                        if (dimensionBehaviour == dimensionBehaviour3) {
                            dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                        }
                        if (dimensionBehaviour2 == dimensionBehaviour3) {
                            dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                        }
                        BasicMeasure.Measure measure = this.mMeasure;
                        measure.horizontalBehavior = dimensionBehaviour;
                        measure.verticalBehavior = dimensionBehaviour2;
                        measure.horizontalDimension = constraintWidget3.getWidth();
                        measure.verticalDimension = constraintWidget3.getHeight();
                        measurer.measure(constraintWidget3, measure);
                        constraintWidget3.setWidth(measure.measuredWidth);
                        constraintWidget3.setHeight(measure.measuredHeight);
                        constraintWidget3.setBaselineDistance(measure.measuredBaseline);
                    }
                }
            }
        }
        int i20 = this.mResolvedPaddingLeft;
        int i21 = this.mResolvedPaddingRight;
        int i22 = this.mPaddingTop;
        int i23 = this.mPaddingBottom;
        int[] iArr2 = new int[2];
        int i24 = (i2 - i20) - i21;
        int i25 = this.mOrientation;
        if (i25 == 1) {
            i24 = (i4 - i22) - i23;
        }
        int i26 = i24;
        if (i25 == 0) {
            if (this.mHorizontalStyle == -1) {
                this.mHorizontalStyle = 0;
            }
            if (this.mVerticalStyle == -1) {
                this.mVerticalStyle = 0;
            }
        } else {
            if (this.mHorizontalStyle == -1) {
                this.mHorizontalStyle = 0;
            }
            if (this.mVerticalStyle == -1) {
                this.mVerticalStyle = 0;
            }
        }
        ConstraintWidget[] constraintWidgetArr = this.mWidgets;
        int i27 = 0;
        int i28 = 0;
        int i29 = 0;
        while (true) {
            i5 = this.mWidgetsCount;
            if (i27 >= i5) {
                break;
            }
            if (this.mWidgets[i27].mVisibility == 8) {
                i28++;
            }
            i27++;
        }
        if (i28 > 0) {
            constraintWidgetArr = new ConstraintWidget[i5 - i28];
            i5 = 0;
            for (int i30 = 0; i30 < this.mWidgetsCount; i30++) {
                ConstraintWidget constraintWidget4 = this.mWidgets[i30];
                if (constraintWidget4.mVisibility != 8) {
                    constraintWidgetArr[i5] = constraintWidget4;
                    i5++;
                }
            }
        }
        int i31 = i5;
        ConstraintWidget[] constraintWidgetArr2 = constraintWidgetArr;
        this.mDisplayedWidgets = constraintWidgetArr2;
        this.mDisplayedWidgetsCount = i31;
        int i32 = this.mWrapMode;
        if (i32 == 0) {
            i6 = i22;
            i7 = i23;
            iArr = iArr2;
            i8 = i20;
            i9 = i21;
            int i33 = this.mOrientation;
            if (i31 != 0) {
                if (this.mChainList.size() == 0) {
                    widgetsList = new WidgetsList(i33, this.mLeft, this.mTop, this.mRight, this.mBottom, i26);
                    this.mChainList.add(widgetsList);
                } else {
                    WidgetsList widgetsList2 = (WidgetsList) this.mChainList.get(0);
                    widgetsList2.mBiggestDimension = 0;
                    widgetsList2.mBiggest = null;
                    widgetsList2.mWidth = 0;
                    widgetsList2.mHeight = 0;
                    widgetsList2.mStartIndex = 0;
                    widgetsList2.mCount = 0;
                    widgetsList2.mNbMatchConstraintsWidgets = 0;
                    widgetsList2.setup(i33, this.mLeft, this.mTop, this.mRight, this.mBottom, this.mResolvedPaddingLeft, this.mPaddingTop, this.mResolvedPaddingRight, this.mPaddingBottom, i26);
                    widgetsList = widgetsList2;
                }
                for (int i34 = 0; i34 < i31; i34++) {
                    widgetsList.add(constraintWidgetArr2[i34]);
                }
                i29 = 0;
                iArr[0] = widgetsList.getWidth();
                c = 1;
                iArr[1] = widgetsList.getHeight();
            }
            int iMin = iArr[i29] + i8 + i9;
            int iMin2 = iArr[c] + i6 + i7;
            if (i == 1073741824) {
            }
            if (i3 == 1073741824) {
            }
            this.mMeasuredWidth = iMin;
            this.mMeasuredHeight = iMin2;
            setWidth(iMin);
            setHeight(iMin2);
            this.mNeedsCallFromSolver = this.mWidgetsCount > 0 ? c : i29;
        }
        ConstraintAnchor constraintAnchor2 = this.mTop;
        ConstraintAnchor constraintAnchor3 = this.mLeft;
        ConstraintAnchor constraintAnchor4 = this.mRight;
        i8 = i20;
        ConstraintAnchor constraintAnchor5 = this.mBottom;
        if (i32 == 1) {
            i7 = i23;
            iArr = iArr2;
            i9 = i21;
            i6 = i22;
            int i35 = this.mOrientation;
            if (i31 != 0) {
                this.mChainList.clear();
                WidgetsList widgetsList3 = new WidgetsList(i35, this.mLeft, this.mTop, this.mRight, this.mBottom, i26);
                this.mChainList.add(widgetsList3);
                if (i35 == 0) {
                    i10 = 0;
                    int i36 = 0;
                    int i37 = 0;
                    while (i37 < i31) {
                        ConstraintWidget constraintWidget5 = constraintWidgetArr2[i37];
                        int widgetWidth = getWidgetWidth(constraintWidget5, i26);
                        if (constraintWidget5.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            i10++;
                        }
                        int i38 = i10;
                        boolean z = (i36 == i26 || (this.mHorizontalGap + i36) + widgetWidth > i26) && widgetsList3.mBiggest != null;
                        if (!z && i37 > 0 && (i12 = this.mMaxElementsWrap) > 0 && i37 % i12 == 0) {
                            z = true;
                        }
                        if (z) {
                            widgetsList3 = new WidgetsList(i35, this.mLeft, this.mTop, this.mRight, this.mBottom, i26);
                            widgetsList3.mStartIndex = i37;
                            this.mChainList.add(widgetsList3);
                        } else {
                            if (i37 > 0) {
                                i36 = this.mHorizontalGap + widgetWidth + i36;
                            }
                            widgetsList3.add(constraintWidget5);
                            i37++;
                            i10 = i38;
                        }
                        i36 = widgetWidth;
                        widgetsList3.add(constraintWidget5);
                        i37++;
                        i10 = i38;
                    }
                } else {
                    i10 = 0;
                    int i39 = 0;
                    int i40 = 0;
                    while (i40 < i31) {
                        ConstraintWidget constraintWidget6 = constraintWidgetArr2[i40];
                        int widgetHeight = getWidgetHeight(constraintWidget6, i26);
                        if (constraintWidget6.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            i10++;
                        }
                        int i41 = i10;
                        boolean z2 = (i39 == i26 || (this.mVerticalGap + i39) + widgetHeight > i26) && widgetsList3.mBiggest != null;
                        if (!z2 && i40 > 0 && (i11 = this.mMaxElementsWrap) > 0 && i40 % i11 == 0) {
                            z2 = true;
                        }
                        if (z2) {
                            widgetsList3 = new WidgetsList(i35, this.mLeft, this.mTop, this.mRight, this.mBottom, i26);
                            widgetsList3.mStartIndex = i40;
                            this.mChainList.add(widgetsList3);
                        } else {
                            if (i40 > 0) {
                                i39 = this.mVerticalGap + widgetHeight + i39;
                            }
                            widgetsList3.add(constraintWidget6);
                            i40++;
                            i10 = i41;
                        }
                        i39 = widgetHeight;
                        widgetsList3.add(constraintWidget6);
                        i40++;
                        i10 = i41;
                    }
                }
                int size = this.mChainList.size();
                int i42 = this.mResolvedPaddingLeft;
                int i43 = this.mPaddingTop;
                int i44 = this.mResolvedPaddingRight;
                int i45 = this.mPaddingBottom;
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = dimensionBehaviourArr[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                boolean z3 = dimensionBehaviour4 == dimensionBehaviour5 || dimensionBehaviourArr[1] == dimensionBehaviour5;
                if (i10 > 0 && z3) {
                    for (int i46 = 0; i46 < size; i46++) {
                        WidgetsList widgetsList4 = (WidgetsList) this.mChainList.get(i46);
                        if (i35 == 0) {
                            widgetsList4.measureMatchConstraints(i26 - widgetsList4.getWidth());
                        } else {
                            widgetsList4.measureMatchConstraints(i26 - widgetsList4.getHeight());
                        }
                    }
                }
                int i47 = i42;
                int i48 = i43;
                int i49 = i44;
                int i50 = i45;
                ConstraintAnchor constraintAnchor6 = constraintAnchor2;
                ConstraintAnchor constraintAnchor7 = constraintAnchor3;
                int iMax = 0;
                int i51 = 0;
                ConstraintAnchor constraintAnchor8 = constraintAnchor4;
                ConstraintAnchor constraintAnchor9 = constraintAnchor5;
                for (int i52 = 0; i52 < size; i52++) {
                    WidgetsList widgetsList5 = (WidgetsList) this.mChainList.get(i52);
                    if (i35 == 0) {
                        if (i52 < size - 1) {
                            constraintAnchor9 = ((WidgetsList) this.mChainList.get(i52 + 1)).mBiggest.mTop;
                            i50 = 0;
                        } else {
                            i50 = this.mPaddingBottom;
                            constraintAnchor9 = constraintAnchor5;
                        }
                        ConstraintAnchor constraintAnchor10 = widgetsList5.mBiggest.mBottom;
                        widgetsList5.setup(i35, constraintAnchor7, constraintAnchor6, constraintAnchor8, constraintAnchor9, i47, i48, i49, i50, i26);
                        iMax = Math.max(iMax, widgetsList5.getWidth());
                        int height = widgetsList5.getHeight() + i51;
                        if (i52 > 0) {
                            height += this.mVerticalGap;
                        }
                        i51 = height;
                        constraintAnchor6 = constraintAnchor10;
                        i48 = 0;
                    } else {
                        if (i52 < size - 1) {
                            constraintAnchor8 = ((WidgetsList) this.mChainList.get(i52 + 1)).mBiggest.mLeft;
                            i49 = 0;
                        } else {
                            i49 = this.mResolvedPaddingRight;
                            constraintAnchor8 = constraintAnchor4;
                        }
                        ConstraintAnchor constraintAnchor11 = widgetsList5.mBiggest.mRight;
                        widgetsList5.setup(i35, constraintAnchor7, constraintAnchor6, constraintAnchor8, constraintAnchor9, i47, i48, i49, i50, i26);
                        int width = widgetsList5.getWidth() + iMax;
                        int iMax2 = Math.max(i51, widgetsList5.getHeight());
                        if (i52 > 0) {
                            width += this.mHorizontalGap;
                        }
                        i51 = iMax2;
                        iMax = width;
                        constraintAnchor7 = constraintAnchor11;
                        i47 = 0;
                    }
                }
                iArr[0] = iMax;
                iArr[1] = i51;
            }
        } else {
            if (i32 != 2) {
                if (i32 != 3) {
                    i7 = i23;
                    iArr = iArr2;
                    i9 = i21;
                    i6 = i22;
                } else {
                    int i53 = this.mOrientation;
                    if (i31 != 0) {
                        this.mChainList.clear();
                        i7 = i23;
                        i6 = i22;
                        iArr = iArr2;
                        WidgetsList widgetsList6 = new WidgetsList(i53, this.mLeft, this.mTop, this.mRight, this.mBottom, i26);
                        this.mChainList.add(widgetsList6);
                        if (i53 == 0) {
                            int i54 = 0;
                            int i55 = 0;
                            i14 = 0;
                            int i56 = 0;
                            while (i54 < i31) {
                                i55++;
                                ConstraintAnchor constraintAnchor12 = constraintAnchor5;
                                ConstraintWidget constraintWidget7 = constraintWidgetArr2[i54];
                                int widgetWidth2 = getWidgetWidth(constraintWidget7, i26);
                                int i57 = i53;
                                int i58 = i54;
                                if (constraintWidget7.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    i14++;
                                }
                                int i59 = i14;
                                boolean z4 = (i56 == i26 || (this.mHorizontalGap + i56) + widgetWidth2 > i26) && widgetsList6.mBiggest != null;
                                if (!z4 && i58 > 0 && (i18 = this.mMaxElementsWrap) > 0 && i55 > i18) {
                                    z4 = true;
                                }
                                if (z4) {
                                    i16 = i21;
                                    i53 = i57;
                                    i17 = i58;
                                    widgetsList6 = new WidgetsList(i53, this.mLeft, this.mTop, this.mRight, this.mBottom, i26);
                                    widgetsList6.mStartIndex = i17;
                                    this.mChainList.add(widgetsList6);
                                    i56 = widgetWidth2;
                                    i55 = 1;
                                } else {
                                    i16 = i21;
                                    i53 = i57;
                                    i17 = i58;
                                    i56 = i17 > 0 ? this.mHorizontalGap + widgetWidth2 + i56 : widgetWidth2;
                                }
                                widgetsList6.add(constraintWidget7);
                                i54 = i17 + 1;
                                i14 = i59;
                                constraintAnchor5 = constraintAnchor12;
                                i21 = i16;
                            }
                            constraintAnchor = constraintAnchor5;
                            i9 = i21;
                        } else {
                            constraintAnchor = constraintAnchor5;
                            i9 = i21;
                            int i60 = 0;
                            int i61 = 0;
                            int i62 = 0;
                            int i63 = 0;
                            while (i63 < i31) {
                                i60++;
                                ConstraintWidget constraintWidget8 = constraintWidgetArr2[i63];
                                int widgetHeight2 = getWidgetHeight(constraintWidget8, i26);
                                int i64 = i53;
                                if (constraintWidget8.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    i61++;
                                }
                                int i65 = i61;
                                boolean z5 = (i62 == i26 || (this.mVerticalGap + i62) + widgetHeight2 > i26) && widgetsList6.mBiggest != null;
                                if (!z5 && i63 > 0 && (i15 = this.mMaxElementsWrap) > 0 && i60 > i15) {
                                    z5 = true;
                                }
                                if (z5) {
                                    i53 = i64;
                                    widgetsList6 = new WidgetsList(i53, this.mLeft, this.mTop, this.mRight, this.mBottom, i26);
                                    widgetsList6.mStartIndex = i63;
                                    this.mChainList.add(widgetsList6);
                                    i62 = widgetHeight2;
                                    i60 = 1;
                                } else {
                                    i53 = i64;
                                    i62 = i63 > 0 ? this.mVerticalGap + widgetHeight2 + i62 : widgetHeight2;
                                }
                                widgetsList6.add(constraintWidget8);
                                i63++;
                                i61 = i65;
                            }
                            i14 = i61;
                        }
                        int size2 = this.mChainList.size();
                        int i66 = this.mResolvedPaddingLeft;
                        int i67 = this.mPaddingTop;
                        int i68 = this.mResolvedPaddingRight;
                        int i69 = this.mPaddingBottom;
                        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = this.mListDimensionBehaviors;
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = dimensionBehaviourArr2[0];
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                        boolean z6 = dimensionBehaviour6 == dimensionBehaviour7 || dimensionBehaviourArr2[1] == dimensionBehaviour7;
                        if (i14 > 0 && z6) {
                            for (int i70 = 0; i70 < size2; i70++) {
                                WidgetsList widgetsList7 = (WidgetsList) this.mChainList.get(i70);
                                if (i53 == 0) {
                                    widgetsList7.measureMatchConstraints(i26 - widgetsList7.getWidth());
                                } else {
                                    widgetsList7.measureMatchConstraints(i26 - widgetsList7.getHeight());
                                }
                            }
                        }
                        int i71 = i66;
                        int i72 = i67;
                        int i73 = i68;
                        int i74 = i69;
                        ConstraintAnchor constraintAnchor13 = constraintAnchor2;
                        ConstraintAnchor constraintAnchor14 = constraintAnchor3;
                        int iMax3 = 0;
                        int i75 = 0;
                        ConstraintAnchor constraintAnchor15 = constraintAnchor4;
                        ConstraintAnchor constraintAnchor16 = constraintAnchor;
                        for (int i76 = 0; i76 < size2; i76++) {
                            WidgetsList widgetsList8 = (WidgetsList) this.mChainList.get(i76);
                            if (i53 == 0) {
                                if (i76 < size2 - 1) {
                                    constraintAnchor16 = ((WidgetsList) this.mChainList.get(i76 + 1)).mBiggest.mTop;
                                    i74 = 0;
                                } else {
                                    i74 = this.mPaddingBottom;
                                    constraintAnchor16 = constraintAnchor;
                                }
                                ConstraintAnchor constraintAnchor17 = widgetsList8.mBiggest.mBottom;
                                widgetsList8.setup(i53, constraintAnchor14, constraintAnchor13, constraintAnchor15, constraintAnchor16, i71, i72, i73, i74, i26);
                                iMax3 = Math.max(iMax3, widgetsList8.getWidth());
                                int height2 = widgetsList8.getHeight() + i75;
                                if (i76 > 0) {
                                    height2 += this.mVerticalGap;
                                }
                                i75 = height2;
                                constraintAnchor13 = constraintAnchor17;
                                i72 = 0;
                            } else {
                                if (i76 < size2 - 1) {
                                    constraintAnchor15 = ((WidgetsList) this.mChainList.get(i76 + 1)).mBiggest.mLeft;
                                    i73 = 0;
                                } else {
                                    i73 = this.mResolvedPaddingRight;
                                    constraintAnchor15 = constraintAnchor4;
                                }
                                ConstraintAnchor constraintAnchor18 = widgetsList8.mBiggest.mRight;
                                widgetsList8.setup(i53, constraintAnchor14, constraintAnchor13, constraintAnchor15, constraintAnchor16, i71, i72, i73, i74, i26);
                                int width2 = widgetsList8.getWidth() + iMax3;
                                int iMax4 = Math.max(i75, widgetsList8.getHeight());
                                if (i76 > 0) {
                                    width2 += this.mHorizontalGap;
                                }
                                i75 = iMax4;
                                iMax3 = width2;
                                constraintAnchor14 = constraintAnchor18;
                                i71 = 0;
                            }
                        }
                        iArr[0] = iMax3;
                        iArr[1] = i75;
                    }
                }
                int iMin3 = iArr[i29] + i8 + i9;
                int iMin22 = iArr[c] + i6 + i7;
                if (i == 1073741824) {
                    iMin3 = i2;
                } else if (i == Integer.MIN_VALUE) {
                    iMin3 = Math.min(iMin3, i2);
                } else if (i != 0) {
                    iMin3 = i29;
                }
                if (i3 == 1073741824) {
                    iMin22 = i4;
                } else if (i3 == Integer.MIN_VALUE) {
                    iMin22 = Math.min(iMin22, i4);
                } else if (i3 != 0) {
                    iMin22 = i29;
                }
                this.mMeasuredWidth = iMin3;
                this.mMeasuredHeight = iMin22;
                setWidth(iMin3);
                setHeight(iMin22);
                this.mNeedsCallFromSolver = this.mWidgetsCount > 0 ? c : i29;
            }
            i7 = i23;
            iArr = iArr2;
            i9 = i21;
            i6 = i22;
            int i77 = this.mOrientation;
            if (i77 == 0) {
                int i78 = this.mMaxElementsWrap;
                if (i78 <= 0) {
                    int i79 = 0;
                    iCeil2 = 0;
                    for (int i80 = 0; i80 < i31; i80++) {
                        if (i80 > 0) {
                            i79 += this.mHorizontalGap;
                        }
                        ConstraintWidget constraintWidget9 = constraintWidgetArr2[i80];
                        if (constraintWidget9 != null) {
                            int widgetWidth3 = getWidgetWidth(constraintWidget9, i26) + i79;
                            if (widgetWidth3 > i26) {
                                break;
                            }
                            iCeil2++;
                            i79 = widgetWidth3;
                        }
                    }
                } else {
                    iCeil2 = i78;
                }
                iCeil = 0;
            } else {
                iCeil = this.mMaxElementsWrap;
                if (iCeil <= 0) {
                    int i81 = 0;
                    int i82 = 0;
                    for (int i83 = 0; i83 < i31; i83++) {
                        if (i83 > 0) {
                            i81 += this.mVerticalGap;
                        }
                        ConstraintWidget constraintWidget10 = constraintWidgetArr2[i83];
                        if (constraintWidget10 != null) {
                            int widgetHeight3 = getWidgetHeight(constraintWidget10, i26) + i81;
                            if (widgetHeight3 > i26) {
                                break;
                            }
                            i82++;
                            i81 = widgetHeight3;
                        }
                    }
                    iCeil = i82;
                }
                iCeil2 = 0;
            }
            if (this.mAlignedDimensions == null) {
                this.mAlignedDimensions = new int[2];
            }
            boolean z7 = (iCeil == 0 && i77 == 1) || (iCeil2 == 0 && i77 == 0);
            while (!z7) {
                if (i77 == 0) {
                    iCeil = (int) Math.ceil(i31 / iCeil2);
                } else {
                    iCeil2 = (int) Math.ceil(i31 / iCeil);
                }
                ConstraintWidget[] constraintWidgetArr3 = this.mAlignedBiggestElementsInCols;
                if (constraintWidgetArr3 == null || constraintWidgetArr3.length < iCeil2) {
                    obj = null;
                    this.mAlignedBiggestElementsInCols = new ConstraintWidget[iCeil2];
                } else {
                    obj = null;
                    Arrays.fill(constraintWidgetArr3, (Object) null);
                }
                ConstraintWidget[] constraintWidgetArr4 = this.mAlignedBiggestElementsInRows;
                if (constraintWidgetArr4 == null || constraintWidgetArr4.length < iCeil) {
                    this.mAlignedBiggestElementsInRows = new ConstraintWidget[iCeil];
                } else {
                    Arrays.fill(constraintWidgetArr4, obj);
                }
                for (int i84 = 0; i84 < iCeil2; i84++) {
                    int i85 = 0;
                    while (i85 < iCeil) {
                        int i86 = (i85 * iCeil2) + i84;
                        if (i77 == 1) {
                            i86 = (i84 * iCeil) + i85;
                        }
                        if (i86 < constraintWidgetArr2.length && (constraintWidget = constraintWidgetArr2[i86]) != null) {
                            int widgetWidth4 = getWidgetWidth(constraintWidget, i26);
                            i13 = i77;
                            ConstraintWidget constraintWidget11 = this.mAlignedBiggestElementsInCols[i84];
                            if (constraintWidget11 == null || constraintWidget11.getWidth() < widgetWidth4) {
                                this.mAlignedBiggestElementsInCols[i84] = constraintWidget;
                            }
                            int widgetHeight4 = getWidgetHeight(constraintWidget, i26);
                            ConstraintWidget constraintWidget12 = this.mAlignedBiggestElementsInRows[i85];
                            if (constraintWidget12 == null || constraintWidget12.getHeight() < widgetHeight4) {
                                this.mAlignedBiggestElementsInRows[i85] = constraintWidget;
                            }
                        } else {
                            i13 = i77;
                        }
                        i85++;
                        i77 = i13;
                    }
                }
                int i87 = i77;
                int widgetWidth5 = 0;
                for (int i88 = 0; i88 < iCeil2; i88++) {
                    ConstraintWidget constraintWidget13 = this.mAlignedBiggestElementsInCols[i88];
                    if (constraintWidget13 != null) {
                        if (i88 > 0) {
                            widgetWidth5 += this.mHorizontalGap;
                        }
                        widgetWidth5 = getWidgetWidth(constraintWidget13, i26) + widgetWidth5;
                    }
                }
                int widgetHeight5 = 0;
                for (int i89 = 0; i89 < iCeil; i89++) {
                    ConstraintWidget constraintWidget14 = this.mAlignedBiggestElementsInRows[i89];
                    if (constraintWidget14 != null) {
                        if (i89 > 0) {
                            widgetHeight5 += this.mVerticalGap;
                        }
                        widgetHeight5 = getWidgetHeight(constraintWidget14, i26) + widgetHeight5;
                    }
                }
                iArr[0] = widgetWidth5;
                iArr[1] = widgetHeight5;
                if (i87 == 0) {
                    if (widgetWidth5 <= i26 || iCeil2 <= 1) {
                        z7 = true;
                    } else {
                        iCeil2--;
                    }
                } else if (widgetHeight5 > i26 && iCeil > 1) {
                    iCeil--;
                }
                i77 = i87;
            }
            int[] iArr3 = this.mAlignedDimensions;
            iArr3[0] = iCeil2;
            iArr3[1] = iCeil;
        }
        c = 1;
        int iMin32 = iArr[i29] + i8 + i9;
        int iMin222 = iArr[c] + i6 + i7;
        if (i == 1073741824) {
        }
        if (i3 == 1073741824) {
        }
        this.mMeasuredWidth = iMin32;
        this.mMeasuredHeight = iMin222;
        setWidth(iMin32);
        setHeight(iMin222);
        this.mNeedsCallFromSolver = this.mWidgetsCount > 0 ? c : i29;
    }
}
