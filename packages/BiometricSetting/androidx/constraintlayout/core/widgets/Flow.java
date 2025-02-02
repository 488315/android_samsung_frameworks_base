package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class Flow extends VirtualLayout {
    private ConstraintWidget[] mDisplayedWidgets;
    private int mHorizontalStyle = -1;
    private int mVerticalStyle = -1;
    private int mFirstHorizontalStyle = -1;
    private int mFirstVerticalStyle = -1;
    private int mLastHorizontalStyle = -1;
    private int mLastVerticalStyle = -1;
    private float mHorizontalBias = 0.5f;
    private float mVerticalBias = 0.5f;
    private float mFirstHorizontalBias = 0.5f;
    private float mFirstVerticalBias = 0.5f;
    private float mLastHorizontalBias = 0.5f;
    private float mLastVerticalBias = 0.5f;
    private int mHorizontalGap = 0;
    private int mVerticalGap = 0;
    private int mHorizontalAlign = 2;
    private int mVerticalAlign = 2;
    private int mWrapMode = 0;
    private int mMaxElementsWrap = -1;
    private int mOrientation = 0;
    private ArrayList<WidgetsList> mChainList = new ArrayList<>();
    private ConstraintWidget[] mAlignedBiggestElementsInRows = null;
    private ConstraintWidget[] mAlignedBiggestElementsInCols = null;
    private int[] mAlignedDimensions = null;
    private int mDisplayedWidgetsCount = 0;

    private class WidgetsList {
        private ConstraintAnchor mBottom;
        private ConstraintAnchor mLeft;
        private int mMax;
        private int mOrientation;
        private int mPaddingBottom;
        private int mPaddingLeft;
        private int mPaddingRight;
        private int mPaddingTop;
        private ConstraintAnchor mRight;
        private ConstraintAnchor mTop;
        private ConstraintWidget mBiggest = null;
        int mBiggestDimension = 0;
        private int mWidth = 0;
        private int mHeight = 0;
        private int mStartIndex = 0;
        private int mCount = 0;
        private int mNbMatchConstraintsWidgets = 0;

        WidgetsList(int i, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i2) {
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
            this.mPaddingLeft = Flow.this.getPaddingLeft();
            this.mPaddingTop = Flow.this.getPaddingTop();
            this.mPaddingRight = Flow.this.getPaddingRight();
            this.mPaddingBottom = Flow.this.getPaddingBottom();
            this.mMax = i2;
        }

        public final void add(ConstraintWidget constraintWidget) {
            int i = this.mOrientation;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
            Flow flow = Flow.this;
            if (i == 0) {
                int access$200 = Flow.access$200(flow, constraintWidget, this.mMax);
                if (constraintWidget.mListDimensionBehaviors[0] == dimensionBehaviour) {
                    this.mNbMatchConstraintsWidgets++;
                    access$200 = 0;
                }
                this.mWidth = access$200 + (constraintWidget.getVisibility() != 8 ? flow.mHorizontalGap : 0) + this.mWidth;
                int access$300 = Flow.access$300(flow, constraintWidget, this.mMax);
                if (this.mBiggest == null || this.mBiggestDimension < access$300) {
                    this.mBiggest = constraintWidget;
                    this.mBiggestDimension = access$300;
                    this.mHeight = access$300;
                }
            } else {
                int access$2002 = Flow.access$200(flow, constraintWidget, this.mMax);
                int access$3002 = Flow.access$300(flow, constraintWidget, this.mMax);
                if (constraintWidget.mListDimensionBehaviors[1] == dimensionBehaviour) {
                    this.mNbMatchConstraintsWidgets++;
                    access$3002 = 0;
                }
                this.mHeight = access$3002 + (constraintWidget.getVisibility() != 8 ? flow.mVerticalGap : 0) + this.mHeight;
                if (this.mBiggest == null || this.mBiggestDimension < access$2002) {
                    this.mBiggest = constraintWidget;
                    this.mBiggestDimension = access$2002;
                    this.mWidth = access$2002;
                }
            }
            this.mCount++;
        }

        public final void clear() {
            this.mBiggestDimension = 0;
            this.mBiggest = null;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mStartIndex = 0;
            this.mCount = 0;
            this.mNbMatchConstraintsWidgets = 0;
        }

        public final void createConstraints(int i, boolean z, boolean z2) {
            Flow flow;
            boolean z3;
            ConstraintWidget constraintWidget;
            char c;
            float f;
            int i2 = this.mCount;
            int i3 = 0;
            while (true) {
                flow = Flow.this;
                if (i3 >= i2 || this.mStartIndex + i3 >= flow.mDisplayedWidgetsCount) {
                    break;
                }
                ConstraintWidget constraintWidget2 = flow.mDisplayedWidgets[this.mStartIndex + i3];
                if (constraintWidget2 != null) {
                    constraintWidget2.resetAnchors();
                }
                i3++;
            }
            if (i2 == 0 || this.mBiggest == null) {
                return;
            }
            boolean z4 = z2 && i == 0;
            int i4 = -1;
            int i5 = -1;
            for (int i6 = 0; i6 < i2; i6++) {
                int i7 = z ? (i2 - 1) - i6 : i6;
                if (this.mStartIndex + i7 >= flow.mDisplayedWidgetsCount) {
                    break;
                }
                ConstraintWidget constraintWidget3 = flow.mDisplayedWidgets[this.mStartIndex + i7];
                if (constraintWidget3 != null && constraintWidget3.getVisibility() == 0) {
                    if (i4 == -1) {
                        i4 = i6;
                    }
                    i5 = i6;
                }
            }
            ConstraintWidget constraintWidget4 = null;
            if (this.mOrientation != 0) {
                ConstraintWidget constraintWidget5 = this.mBiggest;
                constraintWidget5.mHorizontalChainStyle = flow.mHorizontalStyle;
                int i8 = this.mPaddingLeft;
                if (i > 0) {
                    i8 += flow.mHorizontalGap;
                }
                if (z) {
                    constraintWidget5.mRight.connect(this.mRight, i8);
                    if (z2) {
                        constraintWidget5.mLeft.connect(this.mLeft, this.mPaddingRight);
                    }
                    if (i > 0) {
                        this.mRight.mOwner.mLeft.connect(constraintWidget5.mRight, 0);
                    }
                } else {
                    constraintWidget5.mLeft.connect(this.mLeft, i8);
                    if (z2) {
                        constraintWidget5.mRight.connect(this.mRight, this.mPaddingRight);
                    }
                    if (i > 0) {
                        this.mLeft.mOwner.mRight.connect(constraintWidget5.mLeft, 0);
                    }
                }
                for (int i9 = 0; i9 < i2 && this.mStartIndex + i9 < flow.mDisplayedWidgetsCount; i9++) {
                    ConstraintWidget constraintWidget6 = flow.mDisplayedWidgets[this.mStartIndex + i9];
                    if (constraintWidget6 != null) {
                        if (i9 == 0) {
                            constraintWidget6.connect(constraintWidget6.mTop, this.mTop, this.mPaddingTop);
                            int i10 = flow.mVerticalStyle;
                            float f2 = flow.mVerticalBias;
                            if (this.mStartIndex == 0 && flow.mFirstVerticalStyle != -1) {
                                i10 = flow.mFirstVerticalStyle;
                                f2 = flow.mFirstVerticalBias;
                            } else if (z2 && flow.mLastVerticalStyle != -1) {
                                i10 = flow.mLastVerticalStyle;
                                f2 = flow.mLastVerticalBias;
                            }
                            constraintWidget6.mVerticalChainStyle = i10;
                            constraintWidget6.mVerticalBiasPercent = f2;
                        }
                        if (i9 == i2 - 1) {
                            constraintWidget6.connect(constraintWidget6.mBottom, this.mBottom, this.mPaddingBottom);
                        }
                        if (constraintWidget4 != null) {
                            constraintWidget6.mTop.connect(constraintWidget4.mBottom, flow.mVerticalGap);
                            if (i9 == i4) {
                                ConstraintAnchor constraintAnchor = constraintWidget6.mTop;
                                int i11 = this.mPaddingTop;
                                if (constraintAnchor.isConnected()) {
                                    constraintAnchor.mGoneMargin = i11;
                                }
                            }
                            constraintWidget4.mBottom.connect(constraintWidget6.mTop, 0);
                            if (i9 == i5 + 1) {
                                ConstraintAnchor constraintAnchor2 = constraintWidget4.mBottom;
                                int i12 = this.mPaddingBottom;
                                if (constraintAnchor2.isConnected()) {
                                    constraintAnchor2.mGoneMargin = i12;
                                }
                            }
                        }
                        if (constraintWidget6 != constraintWidget5) {
                            if (z) {
                                int i13 = flow.mHorizontalAlign;
                                if (i13 == 0) {
                                    z3 = false;
                                    constraintWidget6.mRight.connect(constraintWidget5.mRight, 0);
                                } else if (i13 == 1) {
                                    z3 = false;
                                    constraintWidget6.mLeft.connect(constraintWidget5.mLeft, 0);
                                } else if (i13 == 2) {
                                    z3 = false;
                                    constraintWidget6.mLeft.connect(constraintWidget5.mLeft, 0);
                                    constraintWidget6.mRight.connect(constraintWidget5.mRight, 0);
                                }
                            } else {
                                int i14 = flow.mHorizontalAlign;
                                if (i14 == 0) {
                                    constraintWidget6.mLeft.connect(constraintWidget5.mLeft, 0);
                                } else if (i14 == 1) {
                                    constraintWidget6.mRight.connect(constraintWidget5.mRight, 0);
                                } else if (i14 == 2) {
                                    if (z4) {
                                        constraintWidget6.mLeft.connect(this.mLeft, this.mPaddingLeft);
                                        constraintWidget6.mRight.connect(this.mRight, this.mPaddingRight);
                                    } else {
                                        constraintWidget6.mLeft.connect(constraintWidget5.mLeft, 0);
                                        constraintWidget6.mRight.connect(constraintWidget5.mRight, 0);
                                    }
                                }
                            }
                            constraintWidget4 = constraintWidget6;
                        }
                        constraintWidget4 = constraintWidget6;
                    }
                }
                return;
            }
            ConstraintWidget constraintWidget7 = this.mBiggest;
            constraintWidget7.mVerticalChainStyle = flow.mVerticalStyle;
            int i15 = this.mPaddingTop;
            if (i > 0) {
                i15 += flow.mVerticalGap;
            }
            constraintWidget7.mTop.connect(this.mTop, i15);
            if (z2) {
                constraintWidget7.mBottom.connect(this.mBottom, this.mPaddingBottom);
            }
            if (i > 0) {
                this.mTop.mOwner.mBottom.connect(constraintWidget7.mTop, 0);
            }
            if (flow.mVerticalAlign == 3 && !constraintWidget7.hasBaseline()) {
                for (int i16 = 0; i16 < i2; i16++) {
                    int i17 = z ? (i2 - 1) - i16 : i16;
                    if (this.mStartIndex + i17 >= flow.mDisplayedWidgetsCount) {
                        break;
                    }
                    constraintWidget = flow.mDisplayedWidgets[this.mStartIndex + i17];
                    if (constraintWidget.hasBaseline()) {
                        break;
                    }
                }
            }
            constraintWidget = constraintWidget7;
            for (int i18 = 0; i18 < i2; i18++) {
                int i19 = z ? (i2 - 1) - i18 : i18;
                if (this.mStartIndex + i19 >= flow.mDisplayedWidgetsCount) {
                    return;
                }
                ConstraintWidget constraintWidget8 = flow.mDisplayedWidgets[this.mStartIndex + i19];
                if (constraintWidget8 == null) {
                    c = 3;
                } else {
                    if (i18 == 0) {
                        constraintWidget8.connect(constraintWidget8.mLeft, this.mLeft, this.mPaddingLeft);
                    }
                    if (i19 == 0) {
                        int i20 = flow.mHorizontalStyle;
                        float f3 = flow.mHorizontalBias;
                        if (z) {
                            f3 = 1.0f - f3;
                        }
                        if (this.mStartIndex != 0 || flow.mFirstHorizontalStyle == -1) {
                            if (z2 && flow.mLastHorizontalStyle != -1) {
                                i20 = flow.mLastHorizontalStyle;
                                if (z) {
                                    f = flow.mLastHorizontalBias;
                                    f3 = 1.0f - f;
                                } else {
                                    f3 = flow.mLastHorizontalBias;
                                }
                            }
                            constraintWidget8.mHorizontalChainStyle = i20;
                            constraintWidget8.mHorizontalBiasPercent = f3;
                        } else {
                            i20 = flow.mFirstHorizontalStyle;
                            if (z) {
                                f = flow.mFirstHorizontalBias;
                                f3 = 1.0f - f;
                                constraintWidget8.mHorizontalChainStyle = i20;
                                constraintWidget8.mHorizontalBiasPercent = f3;
                            } else {
                                f3 = flow.mFirstHorizontalBias;
                                constraintWidget8.mHorizontalChainStyle = i20;
                                constraintWidget8.mHorizontalBiasPercent = f3;
                            }
                        }
                    }
                    if (i18 == i2 - 1) {
                        constraintWidget8.connect(constraintWidget8.mRight, this.mRight, this.mPaddingRight);
                    }
                    if (constraintWidget4 != null) {
                        constraintWidget8.mLeft.connect(constraintWidget4.mRight, flow.mHorizontalGap);
                        if (i18 == i4) {
                            ConstraintAnchor constraintAnchor3 = constraintWidget8.mLeft;
                            int i21 = this.mPaddingLeft;
                            if (constraintAnchor3.isConnected()) {
                                constraintAnchor3.mGoneMargin = i21;
                            }
                        }
                        constraintWidget4.mRight.connect(constraintWidget8.mLeft, 0);
                        if (i18 == i5 + 1) {
                            ConstraintAnchor constraintAnchor4 = constraintWidget4.mRight;
                            int i22 = this.mPaddingRight;
                            if (constraintAnchor4.isConnected()) {
                                constraintAnchor4.mGoneMargin = i22;
                            }
                        }
                    }
                    if (constraintWidget8 != constraintWidget7) {
                        c = 3;
                        if (flow.mVerticalAlign == 3 && constraintWidget.hasBaseline() && constraintWidget8 != constraintWidget && constraintWidget8.hasBaseline()) {
                            constraintWidget8.mBaseline.connect(constraintWidget.mBaseline, 0);
                        } else {
                            int i23 = flow.mVerticalAlign;
                            if (i23 == 0) {
                                constraintWidget8.mTop.connect(constraintWidget7.mTop, 0);
                            } else if (i23 == 1) {
                                constraintWidget8.mBottom.connect(constraintWidget7.mBottom, 0);
                            } else if (z4) {
                                constraintWidget8.mTop.connect(this.mTop, this.mPaddingTop);
                                constraintWidget8.mBottom.connect(this.mBottom, this.mPaddingBottom);
                            } else {
                                constraintWidget8.mTop.connect(constraintWidget7.mTop, 0);
                                constraintWidget8.mBottom.connect(constraintWidget7.mBottom, 0);
                            }
                        }
                    } else {
                        c = 3;
                    }
                    constraintWidget4 = constraintWidget8;
                }
            }
        }

        public final int getHeight() {
            return this.mOrientation == 1 ? this.mHeight - Flow.this.mVerticalGap : this.mHeight;
        }

        public final int getWidth() {
            return this.mOrientation == 0 ? this.mWidth - Flow.this.mHorizontalGap : this.mWidth;
        }

        public final void measureMatchConstraints(int i) {
            Flow flow;
            int i2 = this.mNbMatchConstraintsWidgets;
            if (i2 == 0) {
                return;
            }
            int i3 = this.mCount;
            int i4 = i / i2;
            int i5 = 0;
            while (true) {
                flow = Flow.this;
                if (i5 >= i3 || this.mStartIndex + i5 >= flow.mDisplayedWidgetsCount) {
                    break;
                }
                ConstraintWidget constraintWidget = flow.mDisplayedWidgets[this.mStartIndex + i5];
                int i6 = this.mOrientation;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (i6 == 0) {
                    if (constraintWidget != null) {
                        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
                        if (dimensionBehaviourArr[0] == dimensionBehaviour2 && constraintWidget.mMatchConstraintDefaultWidth == 0) {
                            Flow.this.measure(constraintWidget, dimensionBehaviour, i4, dimensionBehaviourArr[1], constraintWidget.getHeight());
                        }
                    }
                } else if (constraintWidget != null) {
                    ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = constraintWidget.mListDimensionBehaviors;
                    if (dimensionBehaviourArr2[1] == dimensionBehaviour2 && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                        Flow.this.measure(constraintWidget, dimensionBehaviourArr2[0], constraintWidget.getWidth(), dimensionBehaviour, i4);
                    }
                }
                i5++;
            }
            this.mWidth = 0;
            this.mHeight = 0;
            this.mBiggest = null;
            this.mBiggestDimension = 0;
            int i7 = this.mCount;
            for (int i8 = 0; i8 < i7 && this.mStartIndex + i8 < flow.mDisplayedWidgetsCount; i8++) {
                ConstraintWidget constraintWidget2 = flow.mDisplayedWidgets[this.mStartIndex + i8];
                if (this.mOrientation == 0) {
                    int width = constraintWidget2.getWidth();
                    int i9 = flow.mHorizontalGap;
                    if (constraintWidget2.getVisibility() == 8) {
                        i9 = 0;
                    }
                    this.mWidth = width + i9 + this.mWidth;
                    int access$300 = Flow.access$300(flow, constraintWidget2, this.mMax);
                    if (this.mBiggest == null || this.mBiggestDimension < access$300) {
                        this.mBiggest = constraintWidget2;
                        this.mBiggestDimension = access$300;
                        this.mHeight = access$300;
                    }
                } else {
                    int access$200 = Flow.access$200(flow, constraintWidget2, this.mMax);
                    int access$3002 = Flow.access$300(flow, constraintWidget2, this.mMax);
                    int i10 = flow.mVerticalGap;
                    if (constraintWidget2.getVisibility() == 8) {
                        i10 = 0;
                    }
                    this.mHeight = access$3002 + i10 + this.mHeight;
                    if (this.mBiggest == null || this.mBiggestDimension < access$200) {
                        this.mBiggest = constraintWidget2;
                        this.mBiggestDimension = access$200;
                        this.mWidth = access$200;
                    }
                }
            }
        }

        public final void setStartIndex(int i) {
            this.mStartIndex = i;
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

    static /* synthetic */ int access$200(Flow flow, ConstraintWidget constraintWidget, int i) {
        return flow.getWidgetWidth(i, constraintWidget);
    }

    static /* synthetic */ int access$300(Flow flow, ConstraintWidget constraintWidget, int i) {
        return flow.getWidgetHeight(i, constraintWidget);
    }

    private int getWidgetHeight(int i, ConstraintWidget constraintWidget) {
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
                    constraintWidget.setMeasureRequested();
                    measure(constraintWidget, constraintWidget.mListDimensionBehaviors[0], constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i3);
                }
                return i3;
            }
            if (i2 == 1) {
                return constraintWidget.getHeight();
            }
            if (i2 == 3) {
                return (int) ((constraintWidget.getWidth() * constraintWidget.mDimensionRatio) + 0.5f);
            }
        }
        return constraintWidget.getHeight();
    }

    private int getWidgetWidth(int i, ConstraintWidget constraintWidget) {
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
                    constraintWidget.setMeasureRequested();
                    measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i3, constraintWidget.mListDimensionBehaviors[1], constraintWidget.getHeight());
                }
                return i3;
            }
            if (i2 == 1) {
                return constraintWidget.getWidth();
            }
            if (i2 == 3) {
                return (int) ((constraintWidget.getHeight() * constraintWidget.mDimensionRatio) + 0.5f);
            }
        }
        return constraintWidget.getWidth();
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final void addToSolver(LinearSystem linearSystem, boolean z) {
        ConstraintWidget constraintWidget;
        float f;
        int i;
        super.addToSolver(linearSystem, z);
        ConstraintWidget constraintWidget2 = this.mParent;
        boolean z2 = constraintWidget2 != null && ((ConstraintWidgetContainer) constraintWidget2).isRtl();
        int i2 = this.mWrapMode;
        if (i2 != 0) {
            if (i2 == 1) {
                int size = this.mChainList.size();
                int i3 = 0;
                while (i3 < size) {
                    this.mChainList.get(i3).createConstraints(i3, z2, i3 == size + (-1));
                    i3++;
                }
            } else if (i2 != 2) {
                if (i2 == 3) {
                    int size2 = this.mChainList.size();
                    int i4 = 0;
                    while (i4 < size2) {
                        this.mChainList.get(i4).createConstraints(i4, z2, i4 == size2 + (-1));
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
                    if (constraintWidget4 != null && constraintWidget4.getVisibility() != 8) {
                        if (i8 == 0) {
                            constraintWidget4.connect(constraintWidget4.mLeft, this.mLeft, getPaddingLeft());
                            constraintWidget4.mHorizontalChainStyle = this.mHorizontalStyle;
                            constraintWidget4.mHorizontalBiasPercent = f;
                        }
                        if (i8 == i6 - 1) {
                            constraintWidget4.connect(constraintWidget4.mRight, this.mRight, getPaddingRight());
                        }
                        if (i8 > 0 && constraintWidget3 != null) {
                            constraintWidget4.connect(constraintWidget4.mLeft, constraintWidget3.mRight, this.mHorizontalGap);
                            constraintWidget3.connect(constraintWidget3.mRight, constraintWidget4.mLeft, 0);
                        }
                        constraintWidget3 = constraintWidget4;
                    }
                    i8++;
                    f2 = f;
                }
                for (int i9 = 0; i9 < i7; i9++) {
                    ConstraintWidget constraintWidget5 = this.mAlignedBiggestElementsInRows[i9];
                    if (constraintWidget5 != null && constraintWidget5.getVisibility() != 8) {
                        if (i9 == 0) {
                            constraintWidget5.connect(constraintWidget5.mTop, this.mTop, getPaddingTop());
                            constraintWidget5.mVerticalChainStyle = this.mVerticalStyle;
                            constraintWidget5.mVerticalBiasPercent = this.mVerticalBias;
                        }
                        if (i9 == i7 - 1) {
                            constraintWidget5.connect(constraintWidget5.mBottom, this.mBottom, getPaddingBottom());
                        }
                        if (i9 > 0 && constraintWidget3 != null) {
                            constraintWidget5.connect(constraintWidget5.mTop, constraintWidget3.mBottom, this.mVerticalGap);
                            constraintWidget3.connect(constraintWidget3.mBottom, constraintWidget5.mTop, 0);
                        }
                        constraintWidget3 = constraintWidget5;
                    }
                }
                for (int i10 = 0; i10 < i6; i10++) {
                    for (int i11 = 0; i11 < i7; i11++) {
                        int i12 = (i11 * i6) + i10;
                        if (this.mOrientation == 1) {
                            i12 = (i10 * i7) + i11;
                        }
                        ConstraintWidget[] constraintWidgetArr = this.mDisplayedWidgets;
                        if (i12 < constraintWidgetArr.length && (constraintWidget = constraintWidgetArr[i12]) != null && constraintWidget.getVisibility() != 8) {
                            ConstraintWidget constraintWidget6 = this.mAlignedBiggestElementsInCols[i10];
                            ConstraintWidget constraintWidget7 = this.mAlignedBiggestElementsInRows[i11];
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
            this.mChainList.get(0).createConstraints(0, z2, true);
        }
        needsCallbackFromSolver(false);
    }

    @Override // androidx.constraintlayout.core.widgets.HelperWidget, androidx.constraintlayout.core.widgets.ConstraintWidget
    public final void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:241:0x03d5  */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v76 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:264:0x0488 -> B:209:0x0498). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:265:0x048a -> B:209:0x0498). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:267:0x0490 -> B:209:0x0498). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:268:0x0492 -> B:209:0x0498). Please report as a decompilation issue!!! */
    @Override // androidx.constraintlayout.core.widgets.VirtualLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void measure(int i, int i2, int i3, int i4) {
        int i5;
        int[] iArr;
        WidgetsList widgetsList;
        int i6;
        ?? r2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        int i7;
        int i8;
        int i9;
        ConstraintAnchor constraintAnchor;
        int paddingRight;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
        ConstraintWidget constraintWidget;
        int i10;
        int i11;
        int i12;
        Flow flow;
        int i13;
        ConstraintWidget[] constraintWidgetArr;
        int[] iArr2;
        boolean z;
        Object obj;
        boolean z2;
        ConstraintWidget constraintWidget2;
        int i14;
        int i15;
        ConstraintWidget constraintWidget3;
        int i16;
        boolean z3;
        int i17 = this.mWidgetsCount;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (i17 > 0) {
            ConstraintWidget constraintWidget4 = this.mParent;
            BasicMeasure.Measurer measurer = constraintWidget4 != null ? ((ConstraintWidgetContainer) constraintWidget4).mMeasurer : null;
            if (measurer == null) {
                z3 = false;
            } else {
                for (int i18 = 0; i18 < this.mWidgetsCount; i18++) {
                    ConstraintWidget constraintWidget5 = this.mWidgets[i18];
                    if (constraintWidget5 != null && !(constraintWidget5 instanceof Guideline)) {
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = constraintWidget5.getDimensionBehaviour(0);
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = constraintWidget5.getDimensionBehaviour(1);
                        if (!(dimensionBehaviour5 == dimensionBehaviour4 && constraintWidget5.mMatchConstraintDefaultWidth != 1 && dimensionBehaviour6 == dimensionBehaviour4 && constraintWidget5.mMatchConstraintDefaultHeight != 1)) {
                            if (dimensionBehaviour5 == dimensionBehaviour4) {
                                dimensionBehaviour5 = dimensionBehaviour3;
                            }
                            if (dimensionBehaviour6 == dimensionBehaviour4) {
                                dimensionBehaviour6 = dimensionBehaviour3;
                            }
                            BasicMeasure.Measure measure = this.mMeasure;
                            measure.horizontalBehavior = dimensionBehaviour5;
                            measure.verticalBehavior = dimensionBehaviour6;
                            measure.horizontalDimension = constraintWidget5.getWidth();
                            this.mMeasure.verticalDimension = constraintWidget5.getHeight();
                            measurer.measure(constraintWidget5, this.mMeasure);
                            constraintWidget5.setWidth(this.mMeasure.measuredWidth);
                            constraintWidget5.setHeight(this.mMeasure.measuredHeight);
                            constraintWidget5.setBaselineDistance(this.mMeasure.measuredBaseline);
                        }
                    }
                }
                z3 = true;
            }
            if (!z3) {
                setMeasure(0, 0);
                needsCallbackFromSolver(false);
                return;
            }
        }
        int paddingLeft = getPaddingLeft();
        int paddingRight2 = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int[] iArr3 = new int[2];
        int i19 = (i2 - paddingLeft) - paddingRight2;
        int i20 = this.mOrientation;
        if (i20 == 1) {
            i19 = (i4 - paddingTop) - paddingBottom;
        }
        int i21 = i19;
        if (i20 == 0) {
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
        ConstraintWidget[] constraintWidgetArr2 = this.mWidgets;
        int i22 = 0;
        int i23 = 0;
        while (true) {
            i5 = this.mWidgetsCount;
            if (i22 >= i5) {
                break;
            }
            if (this.mWidgets[i22].getVisibility() == 8) {
                i23++;
            }
            i22++;
        }
        if (i23 > 0) {
            constraintWidgetArr2 = new ConstraintWidget[i5 - i23];
            i5 = 0;
            for (int i24 = 0; i24 < this.mWidgetsCount; i24++) {
                ConstraintWidget constraintWidget6 = this.mWidgets[i24];
                if (constraintWidget6.getVisibility() != 8) {
                    constraintWidgetArr2[i5] = constraintWidget6;
                    i5++;
                }
            }
        }
        int i25 = i5;
        ConstraintWidget[] constraintWidgetArr3 = constraintWidgetArr2;
        this.mDisplayedWidgets = constraintWidgetArr3;
        this.mDisplayedWidgetsCount = i25;
        int i26 = this.mWrapMode;
        if (i26 != 0) {
            if (i26 == 1) {
                iArr = iArr3;
                int i27 = this.mOrientation;
                if (i25 != 0) {
                    this.mChainList.clear();
                    WidgetsList widgetsList2 = new WidgetsList(i27, this.mLeft, this.mTop, this.mRight, this.mBottom, i21);
                    this.mChainList.add(widgetsList2);
                    if (i27 == 0) {
                        i7 = 0;
                        int i28 = 0;
                        int i29 = 0;
                        while (i29 < i25) {
                            ConstraintWidget constraintWidget7 = constraintWidgetArr3[i29];
                            int widgetWidth = getWidgetWidth(i21, constraintWidget7);
                            if (constraintWidget7.mListDimensionBehaviors[0] == dimensionBehaviour4) {
                                i7++;
                            }
                            int i30 = i7;
                            boolean z4 = (i28 == i21 || (this.mHorizontalGap + i28) + widgetWidth > i21) && widgetsList2.mBiggest != null;
                            if (!z4 && i29 > 0 && (i10 = this.mMaxElementsWrap) > 0 && i29 % i10 == 0) {
                                z4 = true;
                            }
                            if (z4) {
                                dimensionBehaviour2 = dimensionBehaviour3;
                                constraintWidget = constraintWidget7;
                                widgetsList2 = new WidgetsList(i27, this.mLeft, this.mTop, this.mRight, this.mBottom, i21);
                                widgetsList2.setStartIndex(i29);
                                this.mChainList.add(widgetsList2);
                            } else {
                                dimensionBehaviour2 = dimensionBehaviour3;
                                constraintWidget = constraintWidget7;
                                if (i29 > 0) {
                                    i28 = this.mHorizontalGap + widgetWidth + i28;
                                    widgetsList2.add(constraintWidget);
                                    i29++;
                                    i7 = i30;
                                    dimensionBehaviour3 = dimensionBehaviour2;
                                }
                            }
                            i28 = widgetWidth;
                            widgetsList2.add(constraintWidget);
                            i29++;
                            i7 = i30;
                            dimensionBehaviour3 = dimensionBehaviour2;
                        }
                        dimensionBehaviour = dimensionBehaviour3;
                    } else {
                        dimensionBehaviour = dimensionBehaviour3;
                        i7 = 0;
                        int i31 = 0;
                        int i32 = 0;
                        while (i32 < i25) {
                            ConstraintWidget constraintWidget8 = constraintWidgetArr3[i32];
                            int widgetHeight = getWidgetHeight(i21, constraintWidget8);
                            if (constraintWidget8.mListDimensionBehaviors[1] == dimensionBehaviour4) {
                                i7++;
                            }
                            int i33 = i7;
                            boolean z5 = (i31 == i21 || (this.mVerticalGap + i31) + widgetHeight > i21) && widgetsList2.mBiggest != null;
                            if (!z5 && i32 > 0 && (i8 = this.mMaxElementsWrap) > 0 && i32 % i8 == 0) {
                                z5 = true;
                            }
                            if (z5) {
                                widgetsList2 = new WidgetsList(i27, this.mLeft, this.mTop, this.mRight, this.mBottom, i21);
                                widgetsList2.setStartIndex(i32);
                                this.mChainList.add(widgetsList2);
                            } else if (i32 > 0) {
                                i31 = this.mVerticalGap + widgetHeight + i31;
                                widgetsList2.add(constraintWidget8);
                                i32++;
                                i7 = i33;
                            }
                            i31 = widgetHeight;
                            widgetsList2.add(constraintWidget8);
                            i32++;
                            i7 = i33;
                        }
                    }
                    int size = this.mChainList.size();
                    ConstraintAnchor constraintAnchor2 = this.mLeft;
                    ConstraintAnchor constraintAnchor3 = this.mTop;
                    ConstraintAnchor constraintAnchor4 = this.mRight;
                    ConstraintAnchor constraintAnchor5 = this.mBottom;
                    int paddingLeft2 = getPaddingLeft();
                    int paddingTop2 = getPaddingTop();
                    int paddingRight3 = getPaddingRight();
                    int paddingBottom2 = getPaddingBottom();
                    ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = dimensionBehaviour;
                    boolean z6 = dimensionBehaviourArr[0] == dimensionBehaviour7 || dimensionBehaviourArr[1] == dimensionBehaviour7;
                    if (i7 > 0 && z6) {
                        for (int i34 = 0; i34 < size; i34++) {
                            WidgetsList widgetsList3 = this.mChainList.get(i34);
                            if (i27 == 0) {
                                widgetsList3.measureMatchConstraints(i21 - widgetsList3.getWidth());
                            } else {
                                widgetsList3.measureMatchConstraints(i21 - widgetsList3.getHeight());
                            }
                        }
                    }
                    int i35 = 0;
                    int i36 = 0;
                    int i37 = 0;
                    while (i35 < size) {
                        WidgetsList widgetsList4 = this.mChainList.get(i35);
                        if (i27 == 0) {
                            if (i35 < size - 1) {
                                constraintAnchor5 = this.mChainList.get(i35 + 1).mBiggest.mTop;
                                i9 = size;
                                paddingBottom2 = 0;
                            } else {
                                constraintAnchor5 = this.mBottom;
                                paddingBottom2 = getPaddingBottom();
                                i9 = size;
                            }
                            ConstraintAnchor constraintAnchor6 = widgetsList4.mBiggest.mBottom;
                            widgetsList4.setup(i27, constraintAnchor2, constraintAnchor3, constraintAnchor4, constraintAnchor5, paddingLeft2, paddingTop2, paddingRight3, paddingBottom2, i21);
                            int max = Math.max(i37, widgetsList4.getWidth());
                            i36 += widgetsList4.getHeight();
                            if (i35 > 0) {
                                i36 += this.mVerticalGap;
                            }
                            i37 = max;
                            paddingTop2 = 0;
                            constraintAnchor3 = constraintAnchor6;
                        } else {
                            i9 = size;
                            if (i35 < i9 - 1) {
                                constraintAnchor = this.mChainList.get(i35 + 1).mBiggest.mLeft;
                                paddingRight = 0;
                            } else {
                                constraintAnchor = this.mRight;
                                paddingRight = getPaddingRight();
                            }
                            ConstraintAnchor constraintAnchor7 = widgetsList4.mBiggest.mRight;
                            widgetsList4.setup(i27, constraintAnchor2, constraintAnchor3, constraintAnchor, constraintAnchor5, paddingLeft2, paddingTop2, paddingRight, paddingBottom2, i21);
                            i37 += widgetsList4.getWidth();
                            int max2 = Math.max(i36, widgetsList4.getHeight());
                            if (i35 > 0) {
                                i37 += this.mHorizontalGap;
                            }
                            i36 = max2;
                            constraintAnchor2 = constraintAnchor7;
                            paddingLeft2 = 0;
                            paddingRight3 = paddingRight;
                            constraintAnchor4 = constraintAnchor;
                        }
                        i35++;
                        size = i9;
                    }
                    iArr[0] = i37;
                    iArr[1] = i36;
                }
            } else if (i26 == 2) {
                iArr = iArr3;
                int i38 = this.mOrientation;
                if (i38 == 0) {
                    int i39 = this.mMaxElementsWrap;
                    if (i39 <= 0) {
                        int i40 = 0;
                        int i41 = 0;
                        for (int i42 = 0; i42 < i25; i42++) {
                            if (i42 > 0) {
                                i40 += this.mHorizontalGap;
                            }
                            ConstraintWidget constraintWidget9 = constraintWidgetArr3[i42];
                            if (constraintWidget9 != null) {
                                i40 += getWidgetWidth(i21, constraintWidget9);
                                if (i40 > i21) {
                                    break;
                                } else {
                                    i41++;
                                }
                            }
                        }
                        i39 = i41;
                    }
                    i12 = i39;
                    i11 = 0;
                } else {
                    i11 = this.mMaxElementsWrap;
                    if (i11 <= 0) {
                        int i43 = 0;
                        int i44 = 0;
                        for (int i45 = 0; i45 < i25; i45++) {
                            if (i45 > 0) {
                                i43 += this.mVerticalGap;
                            }
                            ConstraintWidget constraintWidget10 = constraintWidgetArr3[i45];
                            if (constraintWidget10 != null) {
                                i43 += getWidgetHeight(i21, constraintWidget10);
                                if (i43 > i21) {
                                    break;
                                } else {
                                    i44++;
                                }
                            }
                        }
                        i11 = i44;
                    }
                    i12 = 0;
                }
                if (this.mAlignedDimensions == null) {
                    this.mAlignedDimensions = new int[2];
                }
                if ((i11 == 0 && i38 == 1) || (i12 == 0 && i38 == 0)) {
                    flow = this;
                    i13 = i21;
                    constraintWidgetArr = constraintWidgetArr3;
                    iArr2 = iArr;
                    z2 = true;
                    z = z2;
                    while (!z) {
                    }
                    int[] iArr4 = flow.mAlignedDimensions;
                    iArr4[0] = i12;
                    iArr4[1] = i11;
                } else {
                    flow = this;
                    i13 = i21;
                    constraintWidgetArr = constraintWidgetArr3;
                    iArr2 = iArr;
                    z = false;
                    while (!z) {
                        if (i38 == 0) {
                            i11 = (int) Math.ceil(i25 / i12);
                        } else {
                            i12 = (int) Math.ceil(i25 / i11);
                        }
                        ConstraintWidget[] constraintWidgetArr4 = flow.mAlignedBiggestElementsInCols;
                        if (constraintWidgetArr4 == null || constraintWidgetArr4.length < i12) {
                            obj = null;
                            flow.mAlignedBiggestElementsInCols = new ConstraintWidget[i12];
                        } else {
                            obj = null;
                            Arrays.fill(constraintWidgetArr4, (Object) null);
                        }
                        ConstraintWidget[] constraintWidgetArr5 = flow.mAlignedBiggestElementsInRows;
                        if (constraintWidgetArr5 == null || constraintWidgetArr5.length < i11) {
                            flow.mAlignedBiggestElementsInRows = new ConstraintWidget[i11];
                        } else {
                            Arrays.fill(constraintWidgetArr5, obj);
                        }
                        for (int i46 = 0; i46 < i12; i46++) {
                            for (int i47 = 0; i47 < i11; i47++) {
                                int i48 = (i47 * i12) + i46;
                                if (i38 == 1) {
                                    i48 = (i46 * i11) + i47;
                                }
                                if (i48 < constraintWidgetArr.length && (constraintWidget2 = constraintWidgetArr[i48]) != null) {
                                    int widgetWidth2 = flow.getWidgetWidth(i13, constraintWidget2);
                                    ConstraintWidget constraintWidget11 = flow.mAlignedBiggestElementsInCols[i46];
                                    if (constraintWidget11 == null || constraintWidget11.getWidth() < widgetWidth2) {
                                        flow.mAlignedBiggestElementsInCols[i46] = constraintWidget2;
                                    }
                                    int widgetHeight2 = flow.getWidgetHeight(i13, constraintWidget2);
                                    ConstraintWidget constraintWidget12 = flow.mAlignedBiggestElementsInRows[i47];
                                    if (constraintWidget12 == null || constraintWidget12.getHeight() < widgetHeight2) {
                                        flow.mAlignedBiggestElementsInRows[i47] = constraintWidget2;
                                    }
                                }
                            }
                        }
                        int i49 = 0;
                        for (int i50 = 0; i50 < i12; i50++) {
                            ConstraintWidget constraintWidget13 = flow.mAlignedBiggestElementsInCols[i50];
                            if (constraintWidget13 != null) {
                                if (i50 > 0) {
                                    i49 += flow.mHorizontalGap;
                                }
                                i49 += flow.getWidgetWidth(i13, constraintWidget13);
                            }
                        }
                        int i51 = 0;
                        for (int i52 = 0; i52 < i11; i52++) {
                            ConstraintWidget constraintWidget14 = flow.mAlignedBiggestElementsInRows[i52];
                            if (constraintWidget14 != null) {
                                if (i52 > 0) {
                                    i51 += flow.mVerticalGap;
                                }
                                i51 += flow.getWidgetHeight(i13, constraintWidget14);
                            }
                        }
                        iArr2[0] = i49;
                        z2 = true;
                        iArr2[1] = i51;
                        if (i38 != 0) {
                            if (i51 > i13 && i11 > 1) {
                                i11--;
                            }
                            z = z2;
                        } else {
                            if (i49 > i13 && i12 > 1) {
                                i12--;
                            }
                            z = z2;
                        }
                        while (!z) {
                        }
                    }
                    int[] iArr42 = flow.mAlignedDimensions;
                    iArr42[0] = i12;
                    iArr42[1] = i11;
                }
            } else if (i26 != 3) {
                iArr = iArr3;
                r2 = 1;
                i6 = 0;
            } else {
                int i53 = this.mOrientation;
                if (i25 == 0) {
                    iArr = iArr3;
                } else {
                    this.mChainList.clear();
                    int i54 = i21;
                    iArr = iArr3;
                    boolean z7 = true;
                    WidgetsList widgetsList5 = new WidgetsList(i53, this.mLeft, this.mTop, this.mRight, this.mBottom, i54);
                    this.mChainList.add(widgetsList5);
                    if (i53 == 0) {
                        WidgetsList widgetsList6 = widgetsList5;
                        int i55 = 0;
                        i14 = 0;
                        int i56 = 0;
                        int i57 = 0;
                        while (i57 < i25) {
                            int i58 = i55 + 1;
                            ConstraintWidget constraintWidget15 = constraintWidgetArr3[i57];
                            int i59 = i54;
                            int widgetWidth3 = getWidgetWidth(i59, constraintWidget15);
                            if (constraintWidget15.mListDimensionBehaviors[0] == dimensionBehaviour4) {
                                i14++;
                            }
                            int i60 = i14;
                            boolean z8 = ((i56 == i59 || (this.mHorizontalGap + i56) + widgetWidth3 > i59) && widgetsList6.mBiggest != null) ? z7 : false;
                            if (!z8 && i57 > 0 && (i16 = this.mMaxElementsWrap) > 0 && i58 > i16) {
                                z8 = z7;
                            }
                            if (z8) {
                                i54 = i59;
                                constraintWidget3 = constraintWidget15;
                                WidgetsList widgetsList7 = new WidgetsList(i53, this.mLeft, this.mTop, this.mRight, this.mBottom, i54);
                                widgetsList7.setStartIndex(i57);
                                this.mChainList.add(widgetsList7);
                                widgetsList6 = widgetsList7;
                                i56 = widgetWidth3;
                                i55 = 1;
                            } else {
                                i54 = i59;
                                constraintWidget3 = constraintWidget15;
                                if (i57 > 0) {
                                    i56 = this.mHorizontalGap + widgetWidth3 + i56;
                                    i55 = i58;
                                } else {
                                    i55 = i58;
                                    i56 = widgetWidth3;
                                }
                            }
                            widgetsList6.add(constraintWidget3);
                            i57++;
                            i14 = i60;
                            z7 = true;
                        }
                    } else {
                        WidgetsList widgetsList8 = widgetsList5;
                        int i61 = 0;
                        i14 = 0;
                        int i62 = 0;
                        int i63 = 0;
                        while (i63 < i25) {
                            int i64 = i61 + 1;
                            ConstraintWidget constraintWidget16 = constraintWidgetArr3[i63];
                            int i65 = i54;
                            int widgetHeight3 = getWidgetHeight(i65, constraintWidget16);
                            if (constraintWidget16.mListDimensionBehaviors[1] == dimensionBehaviour4) {
                                i14++;
                            }
                            int i66 = i14;
                            boolean z9 = (i62 == i65 || (this.mVerticalGap + i62) + widgetHeight3 > i65) && widgetsList8.mBiggest != null;
                            if (!z9 && i63 > 0 && (i15 = this.mMaxElementsWrap) > 0 && i64 > i15) {
                                z9 = true;
                            }
                            if (z9) {
                                WidgetsList widgetsList9 = new WidgetsList(i53, this.mLeft, this.mTop, this.mRight, this.mBottom, i65);
                                widgetsList9.setStartIndex(i63);
                                this.mChainList.add(widgetsList9);
                                widgetsList8 = widgetsList9;
                                i62 = widgetHeight3;
                                i61 = 1;
                            } else if (i63 > 0) {
                                i62 = this.mVerticalGap + widgetHeight3 + i62;
                                i61 = i64;
                            } else {
                                i61 = i64;
                                i62 = widgetHeight3;
                            }
                            widgetsList8.add(constraintWidget16);
                            i63++;
                            i54 = i65;
                            i14 = i66;
                        }
                    }
                    int i67 = i54;
                    int size2 = this.mChainList.size();
                    ConstraintAnchor constraintAnchor8 = this.mLeft;
                    ConstraintAnchor constraintAnchor9 = this.mTop;
                    ConstraintAnchor constraintAnchor10 = this.mRight;
                    ConstraintAnchor constraintAnchor11 = this.mBottom;
                    int paddingLeft3 = getPaddingLeft();
                    int paddingTop3 = getPaddingTop();
                    int paddingRight4 = getPaddingRight();
                    int paddingBottom3 = getPaddingBottom();
                    ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = this.mListDimensionBehaviors;
                    boolean z10 = dimensionBehaviourArr2[0] == dimensionBehaviour3 || dimensionBehaviourArr2[1] == dimensionBehaviour3;
                    if (i14 > 0 && z10) {
                        for (int i68 = 0; i68 < size2; i68++) {
                            WidgetsList widgetsList10 = this.mChainList.get(i68);
                            if (i53 == 0) {
                                widgetsList10.measureMatchConstraints(i67 - widgetsList10.getWidth());
                            } else {
                                widgetsList10.measureMatchConstraints(i67 - widgetsList10.getHeight());
                            }
                        }
                    }
                    int i69 = 0;
                    int i70 = 0;
                    for (int i71 = 0; i71 < size2; i71++) {
                        WidgetsList widgetsList11 = this.mChainList.get(i71);
                        if (i53 == 0) {
                            if (i71 < size2 - 1) {
                                constraintAnchor11 = this.mChainList.get(i71 + 1).mBiggest.mTop;
                                paddingBottom3 = 0;
                            } else {
                                constraintAnchor11 = this.mBottom;
                                paddingBottom3 = getPaddingBottom();
                            }
                            ConstraintAnchor constraintAnchor12 = widgetsList11.mBiggest.mBottom;
                            widgetsList11.setup(i53, constraintAnchor8, constraintAnchor9, constraintAnchor10, constraintAnchor11, paddingLeft3, paddingTop3, paddingRight4, paddingBottom3, i67);
                            int max3 = Math.max(i70, widgetsList11.getWidth());
                            i69 += widgetsList11.getHeight();
                            if (i71 > 0) {
                                i69 += this.mVerticalGap;
                            }
                            i70 = max3;
                            constraintAnchor9 = constraintAnchor12;
                            paddingTop3 = 0;
                        } else {
                            if (i71 < size2 - 1) {
                                constraintAnchor10 = this.mChainList.get(i71 + 1).mBiggest.mLeft;
                                paddingRight4 = 0;
                            } else {
                                constraintAnchor10 = this.mRight;
                                paddingRight4 = getPaddingRight();
                            }
                            ConstraintAnchor constraintAnchor13 = widgetsList11.mBiggest.mRight;
                            widgetsList11.setup(i53, constraintAnchor8, constraintAnchor9, constraintAnchor10, constraintAnchor11, paddingLeft3, paddingTop3, paddingRight4, paddingBottom3, i67);
                            i70 += widgetsList11.getWidth();
                            int max4 = Math.max(i69, widgetsList11.getHeight());
                            if (i71 > 0) {
                                i70 += this.mHorizontalGap;
                            }
                            i69 = max4;
                            constraintAnchor8 = constraintAnchor13;
                            paddingLeft3 = 0;
                        }
                    }
                    iArr[0] = i70;
                    iArr[1] = i69;
                }
            }
            i6 = 0;
            r2 = 1;
        } else {
            iArr = iArr3;
            int i72 = this.mOrientation;
            if (i25 != 0) {
                if (this.mChainList.size() == 0) {
                    widgetsList = new WidgetsList(i72, this.mLeft, this.mTop, this.mRight, this.mBottom, i21);
                    this.mChainList.add(widgetsList);
                } else {
                    widgetsList = this.mChainList.get(0);
                    widgetsList.clear();
                    widgetsList.setup(i72, this.mLeft, this.mTop, this.mRight, this.mBottom, getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom(), i21);
                }
                for (int i73 = 0; i73 < i25; i73++) {
                    widgetsList.add(constraintWidgetArr3[i73]);
                }
                i6 = 0;
                iArr[0] = widgetsList.getWidth();
                r2 = 1;
                iArr[1] = widgetsList.getHeight();
            }
            i6 = 0;
            r2 = 1;
        }
        int i74 = iArr[i6] + paddingLeft + paddingRight2;
        int i75 = iArr[r2] + paddingTop + paddingBottom;
        if (i == 1073741824) {
            i74 = i2;
        } else if (i == Integer.MIN_VALUE) {
            i74 = Math.min(i74, i2);
        } else if (i != 0) {
            i74 = i6;
        }
        boolean z11 = r2;
        int min = i3 == 1073741824 ? i4 : i3 == Integer.MIN_VALUE ? Math.min(i75, i4) : i3 == 0 ? i75 : i6;
        setMeasure(i74, min);
        setWidth(i74);
        setHeight(min);
        needsCallbackFromSolver(this.mWidgetsCount > 0 ? z11 : i6);
    }

    public final void setFirstHorizontalBias(float f) {
        this.mFirstHorizontalBias = f;
    }

    public final void setFirstHorizontalStyle(int i) {
        this.mFirstHorizontalStyle = i;
    }

    public final void setFirstVerticalBias(float f) {
        this.mFirstVerticalBias = f;
    }

    public final void setFirstVerticalStyle(int i) {
        this.mFirstVerticalStyle = i;
    }

    public final void setHorizontalAlign(int i) {
        this.mHorizontalAlign = i;
    }

    public final void setHorizontalBias(float f) {
        this.mHorizontalBias = f;
    }

    public final void setHorizontalGap(int i) {
        this.mHorizontalGap = i;
    }

    public final void setHorizontalStyle(int i) {
        this.mHorizontalStyle = i;
    }

    public final void setLastHorizontalBias(float f) {
        this.mLastHorizontalBias = f;
    }

    public final void setLastHorizontalStyle(int i) {
        this.mLastHorizontalStyle = i;
    }

    public final void setLastVerticalBias(float f) {
        this.mLastVerticalBias = f;
    }

    public final void setLastVerticalStyle(int i) {
        this.mLastVerticalStyle = i;
    }

    public final void setMaxElementsWrap(int i) {
        this.mMaxElementsWrap = i;
    }

    public final void setOrientation(int i) {
        this.mOrientation = i;
    }

    public final void setVerticalAlign(int i) {
        this.mVerticalAlign = i;
    }

    public final void setVerticalBias(float f) {
        this.mVerticalBias = f;
    }

    public final void setVerticalGap(int i) {
        this.mVerticalGap = i;
    }

    public final void setVerticalStyle(int i) {
        this.mVerticalStyle = i;
    }

    public final void setWrapMode(int i) {
        this.mWrapMode = i;
    }
}
