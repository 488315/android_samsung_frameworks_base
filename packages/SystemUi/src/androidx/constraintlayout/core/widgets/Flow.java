package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Flow extends VirtualLayout {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WidgetsList {
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
        public ConstraintWidget biggest = null;
        public int biggestDimension = 0;
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
                if (this.biggest == null || this.biggestDimension < widgetHeight) {
                    this.biggest = constraintWidget;
                    this.biggestDimension = widgetHeight;
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
                if (this.biggest == null || this.biggestDimension < widgetWidth2) {
                    this.biggest = constraintWidget;
                    this.biggestDimension = widgetWidth2;
                    this.mWidth = widgetWidth2;
                }
            }
            this.mCount++;
        }

        public final void createConstraints(int i, boolean z, boolean z2) {
            Flow flow;
            int i2;
            int i3;
            int i4;
            ConstraintWidget constraintWidget;
            int i5;
            char c;
            int i6;
            float f;
            float f2;
            int i7;
            float f3;
            int i8;
            int i9 = this.mCount;
            int i10 = 0;
            while (true) {
                flow = Flow.this;
                if (i10 >= i9 || (i8 = this.mStartIndex + i10) >= flow.mDisplayedWidgetsCount) {
                    break;
                }
                ConstraintWidget constraintWidget2 = flow.mDisplayedWidgets[i8];
                if (constraintWidget2 != null) {
                    constraintWidget2.resetAnchors();
                }
                i10++;
            }
            if (i9 == 0 || this.biggest == null) {
                return;
            }
            boolean z3 = z2 && i == 0;
            int i11 = -1;
            int i12 = -1;
            for (int i13 = 0; i13 < i9; i13++) {
                int i14 = this.mStartIndex + (z ? (i9 - 1) - i13 : i13);
                if (i14 >= flow.mDisplayedWidgetsCount) {
                    break;
                }
                ConstraintWidget constraintWidget3 = flow.mDisplayedWidgets[i14];
                if (constraintWidget3 != null && constraintWidget3.mVisibility == 0) {
                    if (i11 == -1) {
                        i11 = i13;
                    }
                    i12 = i13;
                }
            }
            if (this.mOrientation != 0) {
                ConstraintWidget constraintWidget4 = this.biggest;
                constraintWidget4.mHorizontalChainStyle = flow.mHorizontalStyle;
                int i15 = this.mPaddingLeft;
                if (i > 0) {
                    i15 += flow.mHorizontalGap;
                }
                ConstraintAnchor constraintAnchor = constraintWidget4.mRight;
                ConstraintAnchor constraintAnchor2 = constraintWidget4.mLeft;
                if (z) {
                    constraintAnchor.connect(this.mRight, i15);
                    if (z2) {
                        constraintAnchor2.connect(this.mLeft, this.mPaddingRight);
                    }
                    if (i > 0) {
                        this.mRight.mOwner.mLeft.connect(constraintAnchor, 0);
                    }
                } else {
                    constraintAnchor2.connect(this.mLeft, i15);
                    if (z2) {
                        constraintAnchor.connect(this.mRight, this.mPaddingRight);
                    }
                    if (i > 0) {
                        this.mLeft.mOwner.mRight.connect(constraintAnchor2, 0);
                    }
                }
                int i16 = 0;
                ConstraintWidget constraintWidget5 = null;
                while (i16 < i9) {
                    int i17 = this.mStartIndex + i16;
                    if (i17 >= flow.mDisplayedWidgetsCount) {
                        return;
                    }
                    ConstraintWidget constraintWidget6 = flow.mDisplayedWidgets[i17];
                    if (constraintWidget6 == null) {
                        constraintWidget6 = constraintWidget5;
                    } else {
                        ConstraintAnchor constraintAnchor3 = constraintWidget6.mTop;
                        if (i16 == 0) {
                            constraintWidget6.connect(constraintAnchor3, this.mTop, this.mPaddingTop);
                            int i18 = flow.mVerticalStyle;
                            float f4 = flow.mVerticalBias;
                            if (this.mStartIndex == 0) {
                                i4 = flow.mFirstVerticalStyle;
                                i2 = i18;
                                i3 = -1;
                                if (i4 != -1) {
                                    f4 = flow.mFirstVerticalBias;
                                    constraintWidget6.mVerticalChainStyle = i4;
                                    constraintWidget6.mVerticalBiasPercent = f4;
                                }
                            } else {
                                i2 = i18;
                                i3 = -1;
                            }
                            if (!z2 || (i4 = flow.mLastVerticalStyle) == i3) {
                                i4 = i2;
                            } else {
                                f4 = flow.mLastVerticalBias;
                            }
                            constraintWidget6.mVerticalChainStyle = i4;
                            constraintWidget6.mVerticalBiasPercent = f4;
                        }
                        if (i16 == i9 - 1) {
                            constraintWidget6.connect(constraintWidget6.mBottom, this.mBottom, this.mPaddingBottom);
                        }
                        if (constraintWidget5 != null) {
                            int i19 = flow.mVerticalGap;
                            ConstraintAnchor constraintAnchor4 = constraintWidget5.mBottom;
                            constraintAnchor3.connect(constraintAnchor4, i19);
                            if (i16 == i11) {
                                int i20 = this.mPaddingTop;
                                if (constraintAnchor3.isConnected()) {
                                    constraintAnchor3.mGoneMargin = i20;
                                }
                            }
                            constraintAnchor4.connect(constraintAnchor3, 0);
                            if (i16 == i12 + 1) {
                                int i21 = this.mPaddingBottom;
                                if (constraintAnchor4.isConnected()) {
                                    constraintAnchor4.mGoneMargin = i21;
                                }
                            }
                        }
                        if (constraintWidget6 != constraintWidget4) {
                            ConstraintAnchor constraintAnchor5 = constraintWidget6.mRight;
                            ConstraintAnchor constraintAnchor6 = constraintWidget6.mLeft;
                            if (z) {
                                int i22 = flow.mHorizontalAlign;
                                if (i22 == 0) {
                                    constraintAnchor5.connect(constraintAnchor, 0);
                                } else if (i22 == 1) {
                                    constraintAnchor6.connect(constraintAnchor2, 0);
                                } else if (i22 == 2) {
                                    constraintAnchor6.connect(constraintAnchor2, 0);
                                    constraintAnchor5.connect(constraintAnchor, 0);
                                }
                                i16++;
                                constraintWidget5 = constraintWidget6;
                            } else {
                                int i23 = flow.mHorizontalAlign;
                                if (i23 == 0) {
                                    constraintAnchor6.connect(constraintAnchor2, 0);
                                } else if (i23 == 1) {
                                    constraintAnchor5.connect(constraintAnchor, 0);
                                } else if (i23 == 2) {
                                    if (z3) {
                                        constraintAnchor6.connect(this.mLeft, this.mPaddingLeft);
                                        constraintAnchor5.connect(this.mRight, this.mPaddingRight);
                                    } else {
                                        constraintAnchor6.connect(constraintAnchor2, 0);
                                        constraintAnchor5.connect(constraintAnchor, 0);
                                    }
                                }
                                i16++;
                                constraintWidget5 = constraintWidget6;
                            }
                        }
                    }
                    i16++;
                    constraintWidget5 = constraintWidget6;
                }
                return;
            }
            ConstraintWidget constraintWidget7 = this.biggest;
            constraintWidget7.mVerticalChainStyle = flow.mVerticalStyle;
            int i24 = this.mPaddingTop;
            if (i > 0) {
                i24 += flow.mVerticalGap;
            }
            ConstraintAnchor constraintAnchor7 = this.mTop;
            ConstraintAnchor constraintAnchor8 = constraintWidget7.mTop;
            constraintAnchor8.connect(constraintAnchor7, i24);
            ConstraintAnchor constraintAnchor9 = constraintWidget7.mBottom;
            if (z2) {
                constraintAnchor9.connect(this.mBottom, this.mPaddingBottom);
            }
            if (i > 0) {
                this.mTop.mOwner.mBottom.connect(constraintAnchor8, 0);
            }
            if (flow.mVerticalAlign == 3 && !constraintWidget7.hasBaseline) {
                for (int i25 = 0; i25 < i9; i25++) {
                    int i26 = this.mStartIndex + (z ? (i9 - 1) - i25 : i25);
                    if (i26 >= flow.mDisplayedWidgetsCount) {
                        break;
                    }
                    constraintWidget = flow.mDisplayedWidgets[i26];
                    if (constraintWidget.hasBaseline) {
                        break;
                    }
                }
            }
            constraintWidget = constraintWidget7;
            int i27 = 0;
            ConstraintWidget constraintWidget8 = null;
            while (i27 < i9) {
                int i28 = z ? (i9 - 1) - i27 : i27;
                int i29 = this.mStartIndex + i28;
                if (i29 >= flow.mDisplayedWidgetsCount) {
                    return;
                }
                ConstraintWidget constraintWidget9 = flow.mDisplayedWidgets[i29];
                if (constraintWidget9 == null) {
                    i5 = i9;
                    c = 3;
                } else {
                    ConstraintAnchor constraintAnchor10 = constraintWidget9.mLeft;
                    if (i27 == 0) {
                        constraintWidget9.connect(constraintAnchor10, this.mLeft, this.mPaddingLeft);
                    }
                    if (i28 == 0) {
                        int i30 = flow.mHorizontalStyle;
                        if (z) {
                            i6 = i30;
                            f = 1.0f - flow.mHorizontalBias;
                        } else {
                            i6 = i30;
                            f = flow.mHorizontalBias;
                        }
                        if (this.mStartIndex == 0) {
                            int i31 = flow.mFirstHorizontalStyle;
                            f2 = f;
                            if (i31 != -1) {
                                f3 = z ? 1.0f - flow.mFirstHorizontalBias : flow.mFirstHorizontalBias;
                                i7 = i31;
                                constraintWidget9.mHorizontalChainStyle = i7;
                                constraintWidget9.mHorizontalBiasPercent = f3;
                            }
                        } else {
                            f2 = f;
                        }
                        if (!z2 || (i7 = flow.mLastHorizontalStyle) == -1) {
                            i7 = i6;
                            f3 = f2;
                        } else {
                            f3 = z ? 1.0f - flow.mLastHorizontalBias : flow.mLastHorizontalBias;
                        }
                        constraintWidget9.mHorizontalChainStyle = i7;
                        constraintWidget9.mHorizontalBiasPercent = f3;
                    }
                    if (i27 == i9 - 1) {
                        i5 = i9;
                        constraintWidget9.connect(constraintWidget9.mRight, this.mRight, this.mPaddingRight);
                    } else {
                        i5 = i9;
                    }
                    if (constraintWidget8 != null) {
                        int i32 = flow.mHorizontalGap;
                        ConstraintAnchor constraintAnchor11 = constraintWidget8.mRight;
                        constraintAnchor10.connect(constraintAnchor11, i32);
                        if (i27 == i11) {
                            int i33 = this.mPaddingLeft;
                            if (constraintAnchor10.isConnected()) {
                                constraintAnchor10.mGoneMargin = i33;
                            }
                        }
                        constraintAnchor11.connect(constraintAnchor10, 0);
                        if (i27 == i12 + 1) {
                            int i34 = this.mPaddingRight;
                            if (constraintAnchor11.isConnected()) {
                                constraintAnchor11.mGoneMargin = i34;
                            }
                        }
                    }
                    if (constraintWidget9 != constraintWidget7) {
                        int i35 = flow.mVerticalAlign;
                        c = 3;
                        if (i35 == 3 && constraintWidget.hasBaseline && constraintWidget9 != constraintWidget && constraintWidget9.hasBaseline) {
                            constraintWidget9.mBaseline.connect(constraintWidget.mBaseline, 0);
                        } else {
                            ConstraintAnchor constraintAnchor12 = constraintWidget9.mTop;
                            if (i35 != 0) {
                                ConstraintAnchor constraintAnchor13 = constraintWidget9.mBottom;
                                if (i35 == 1) {
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
                i27++;
                i9 = i5;
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
                        flow.measure(constraintWidget, dimensionBehaviourArr2[0], constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i4);
                    }
                }
            }
            this.mWidth = 0;
            this.mHeight = 0;
            this.biggest = null;
            this.biggestDimension = 0;
            int i8 = this.mCount;
            for (int i9 = 0; i9 < i8; i9++) {
                int i10 = this.mStartIndex + i9;
                Flow flow2 = Flow.this;
                if (i10 >= flow2.mDisplayedWidgetsCount) {
                    return;
                }
                ConstraintWidget constraintWidget2 = flow2.mDisplayedWidgets[i10];
                if (this.mOrientation == 0) {
                    int width = constraintWidget2.getWidth();
                    int i11 = flow2.mHorizontalGap;
                    if (constraintWidget2.mVisibility == 8) {
                        i11 = 0;
                    }
                    this.mWidth = width + i11 + this.mWidth;
                    int widgetHeight = flow2.getWidgetHeight(constraintWidget2, this.mMax);
                    if (this.biggest == null || this.biggestDimension < widgetHeight) {
                        this.biggest = constraintWidget2;
                        this.biggestDimension = widgetHeight;
                        this.mHeight = widgetHeight;
                    }
                } else {
                    int widgetWidth = flow2.getWidgetWidth(constraintWidget2, this.mMax);
                    int widgetHeight2 = flow2.getWidgetHeight(constraintWidget2, this.mMax);
                    int i12 = flow2.mVerticalGap;
                    if (constraintWidget2.mVisibility == 8) {
                        i12 = 0;
                    }
                    this.mHeight = widgetHeight2 + i12 + this.mHeight;
                    if (this.biggest == null || this.biggestDimension < widgetWidth) {
                        this.biggest = constraintWidget2;
                        this.biggestDimension = widgetWidth;
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
        ArrayList arrayList = this.mChainList;
        if (i2 != 0) {
            if (i2 == 1) {
                int size = arrayList.size();
                int i3 = 0;
                while (i3 < size) {
                    ((WidgetsList) arrayList.get(i3)).createConstraints(i3, z2, i3 == size + (-1));
                    i3++;
                }
            } else if (i2 != 2) {
                if (i2 == 3) {
                    int size2 = arrayList.size();
                    int i4 = 0;
                    while (i4 < size2) {
                        ((WidgetsList) arrayList.get(i4)).createConstraints(i4, z2, i4 == size2 + (-1));
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
        } else if (arrayList.size() > 0) {
            ((WidgetsList) arrayList.get(0)).createConstraints(0, z2, true);
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
            if (i2 == 1) {
                return constraintWidget.getHeight();
            }
            if (i2 == 3) {
                return (int) ((constraintWidget.getWidth() * constraintWidget.mDimensionRatio) + 0.5f);
            }
        }
        return constraintWidget.getHeight();
    }

    public final int getWidgetWidth(ConstraintWidget constraintWidget, int i) {
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
            if (i2 == 1) {
                return constraintWidget.getWidth();
            }
            if (i2 == 3) {
                return (int) ((constraintWidget.getHeight() * constraintWidget.mDimensionRatio) + 0.5f);
            }
        }
        return constraintWidget.getWidth();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:165:0x081f  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0830  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x084d  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x084f  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0832  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0821  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x041e  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:276:0x0515 -> B:216:0x0523). Please report as a decompilation issue!!! */
    @Override // androidx.constraintlayout.core.widgets.VirtualLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void measure(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int[] iArr;
        WidgetsList widgetsList;
        int i10;
        char c;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int[] iArr2;
        int i19;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        ArrayList arrayList;
        int i25;
        int i26;
        int i27;
        int i28;
        int i29;
        int i30;
        int i31;
        int i32;
        Flow flow;
        ConstraintWidget[] constraintWidgetArr;
        boolean z;
        int i33;
        int i34;
        int i35;
        Flow flow2;
        int i36;
        boolean z2;
        int i37;
        Flow flow3;
        ConstraintWidget constraintWidget;
        int i38;
        int i39;
        int i40;
        int i41;
        int i42;
        int i43;
        ArrayList arrayList2;
        int i44;
        int i45;
        int i46;
        ConstraintWidget constraintWidget2;
        int i47;
        int i48;
        boolean z3;
        Flow flow4 = this;
        if (flow4.mWidgetsCount > 0) {
            ConstraintWidget constraintWidget3 = flow4.mParent;
            BasicMeasure.Measurer measurer = constraintWidget3 != null ? ((ConstraintWidgetContainer) constraintWidget3).mMeasurer : null;
            if (measurer == null) {
                z3 = false;
            } else {
                for (int i49 = 0; i49 < flow4.mWidgetsCount; i49++) {
                    ConstraintWidget constraintWidget4 = flow4.mWidgets[i49];
                    if (constraintWidget4 != null && !(constraintWidget4 instanceof Guideline)) {
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget4.getDimensionBehaviour(0);
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget4.getDimensionBehaviour(1);
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                        if (!(dimensionBehaviour == dimensionBehaviour3 && constraintWidget4.mMatchConstraintDefaultWidth != 1 && dimensionBehaviour2 == dimensionBehaviour3 && constraintWidget4.mMatchConstraintDefaultHeight != 1)) {
                            if (dimensionBehaviour == dimensionBehaviour3) {
                                dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                            }
                            if (dimensionBehaviour2 == dimensionBehaviour3) {
                                dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                            }
                            BasicMeasure.Measure measure = flow4.mMeasure;
                            measure.horizontalBehavior = dimensionBehaviour;
                            measure.verticalBehavior = dimensionBehaviour2;
                            measure.horizontalDimension = constraintWidget4.getWidth();
                            measure.verticalDimension = constraintWidget4.getHeight();
                            ((ConstraintLayout.Measurer) measurer).measure(constraintWidget4, measure);
                            constraintWidget4.setWidth(measure.measuredWidth);
                            constraintWidget4.setHeight(measure.measuredHeight);
                            int i50 = measure.measuredBaseline;
                            constraintWidget4.mBaselineDistance = i50;
                            constraintWidget4.hasBaseline = i50 > 0;
                        }
                    }
                }
                z3 = true;
            }
            if (!z3) {
                flow4.mMeasuredWidth = 0;
                flow4.mMeasuredHeight = 0;
                flow4.mNeedsCallFromSolver = false;
                return;
            }
        }
        int i51 = flow4.mResolvedPaddingLeft;
        int i52 = flow4.mResolvedPaddingRight;
        int i53 = flow4.mPaddingTop;
        int i54 = flow4.mPaddingBottom;
        int[] iArr3 = new int[2];
        int i55 = (i2 - i51) - i52;
        int i56 = flow4.mOrientation;
        if (i56 == 1) {
            i55 = (i4 - i53) - i54;
        }
        int i57 = i55;
        if (i56 == 0) {
            if (flow4.mHorizontalStyle == -1) {
                flow4.mHorizontalStyle = 0;
            }
            if (flow4.mVerticalStyle == -1) {
                flow4.mVerticalStyle = 0;
            }
        } else {
            if (flow4.mHorizontalStyle == -1) {
                flow4.mHorizontalStyle = 0;
            }
            if (flow4.mVerticalStyle == -1) {
                flow4.mVerticalStyle = 0;
            }
        }
        ConstraintWidget[] constraintWidgetArr2 = flow4.mWidgets;
        int i58 = 0;
        int i59 = 0;
        while (true) {
            i5 = flow4.mWidgetsCount;
            if (i58 >= i5) {
                break;
            }
            if (flow4.mWidgets[i58].mVisibility == 8) {
                i59++;
            }
            i58++;
        }
        if (i59 > 0) {
            constraintWidgetArr2 = new ConstraintWidget[i5 - i59];
            i5 = 0;
            for (int i60 = 0; i60 < flow4.mWidgetsCount; i60++) {
                ConstraintWidget constraintWidget5 = flow4.mWidgets[i60];
                if (constraintWidget5.mVisibility != 8) {
                    constraintWidgetArr2[i5] = constraintWidget5;
                    i5++;
                }
            }
        }
        ConstraintWidget[] constraintWidgetArr3 = constraintWidgetArr2;
        flow4.mDisplayedWidgets = constraintWidgetArr3;
        flow4.mDisplayedWidgetsCount = i5;
        int i61 = flow4.mWrapMode;
        ArrayList arrayList3 = flow4.mChainList;
        if (i61 != 0) {
            ConstraintAnchor constraintAnchor = flow4.mTop;
            ConstraintAnchor constraintAnchor2 = flow4.mLeft;
            ConstraintAnchor constraintAnchor3 = flow4.mRight;
            ConstraintAnchor constraintAnchor4 = flow4.mBottom;
            if (i61 == 1) {
                ArrayList arrayList4 = arrayList3;
                i6 = i51;
                i7 = i52;
                i8 = i53;
                i9 = i54;
                iArr = iArr3;
                int i62 = i5;
                int i63 = flow4.mOrientation;
                if (i62 != 0) {
                    arrayList4.clear();
                    WidgetsList widgetsList2 = new WidgetsList(i63, flow4.mLeft, flow4.mTop, flow4.mRight, flow4.mBottom, i57);
                    arrayList4.add(widgetsList2);
                    if (i63 == 0) {
                        i22 = 0;
                        int i64 = 0;
                        int i65 = 0;
                        while (i65 < i62) {
                            ConstraintWidget constraintWidget6 = constraintWidgetArr3[i65];
                            int widgetWidth = flow4.getWidgetWidth(constraintWidget6, i57);
                            if (constraintWidget6.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                i22++;
                            }
                            int i66 = i22;
                            boolean z4 = (i64 == i57 || (flow4.mHorizontalGap + i64) + widgetWidth > i57) && widgetsList2.biggest != null;
                            if (!z4 && i65 > 0 && (i26 = flow4.mMaxElementsWrap) > 0 && i65 % i26 == 0) {
                                z4 = true;
                            }
                            if (z4) {
                                widgetsList2 = new WidgetsList(i63, flow4.mLeft, flow4.mTop, flow4.mRight, flow4.mBottom, i57);
                                widgetsList2.mStartIndex = i65;
                                arrayList4.add(widgetsList2);
                            } else if (i65 > 0) {
                                i64 = flow4.mHorizontalGap + widgetWidth + i64;
                                widgetsList2.add(constraintWidget6);
                                i65++;
                                i22 = i66;
                            }
                            i64 = widgetWidth;
                            widgetsList2.add(constraintWidget6);
                            i65++;
                            i22 = i66;
                        }
                    } else {
                        i22 = 0;
                        int i67 = 0;
                        int i68 = 0;
                        while (i68 < i62) {
                            ConstraintWidget constraintWidget7 = constraintWidgetArr3[i68];
                            int widgetHeight = flow4.getWidgetHeight(constraintWidget7, i57);
                            if (constraintWidget7.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                i22++;
                            }
                            int i69 = i22;
                            boolean z5 = (i67 == i57 || (flow4.mVerticalGap + i67) + widgetHeight > i57) && widgetsList2.biggest != null;
                            if (!z5 && i68 > 0 && (i23 = flow4.mMaxElementsWrap) > 0 && i68 % i23 == 0) {
                                z5 = true;
                            }
                            if (z5) {
                                widgetsList2 = new WidgetsList(i63, flow4.mLeft, flow4.mTop, flow4.mRight, flow4.mBottom, i57);
                                widgetsList2.mStartIndex = i68;
                                arrayList4.add(widgetsList2);
                            } else if (i68 > 0) {
                                i67 = flow4.mVerticalGap + widgetHeight + i67;
                                widgetsList2.add(constraintWidget7);
                                i68++;
                                i22 = i69;
                            }
                            i67 = widgetHeight;
                            widgetsList2.add(constraintWidget7);
                            i68++;
                            i22 = i69;
                        }
                    }
                    int size = arrayList4.size();
                    int i70 = flow4.mResolvedPaddingLeft;
                    int i71 = flow4.mPaddingTop;
                    int i72 = flow4.mResolvedPaddingRight;
                    int i73 = flow4.mPaddingBottom;
                    ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = flow4.mListDimensionBehaviors;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = dimensionBehaviourArr[0];
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    boolean z6 = dimensionBehaviour4 == dimensionBehaviour5 || dimensionBehaviourArr[1] == dimensionBehaviour5;
                    if (i22 > 0 && z6) {
                        for (int i74 = 0; i74 < size; i74++) {
                            WidgetsList widgetsList3 = (WidgetsList) arrayList4.get(i74);
                            if (i63 == 0) {
                                widgetsList3.measureMatchConstraints(i57 - widgetsList3.getWidth());
                            } else {
                                widgetsList3.measureMatchConstraints(i57 - widgetsList3.getHeight());
                            }
                        }
                    }
                    int i75 = i71;
                    ConstraintAnchor constraintAnchor5 = constraintAnchor2;
                    ConstraintAnchor constraintAnchor6 = constraintAnchor3;
                    ConstraintAnchor constraintAnchor7 = constraintAnchor4;
                    int i76 = 0;
                    int i77 = 0;
                    int i78 = 0;
                    int i79 = i70;
                    ConstraintAnchor constraintAnchor8 = constraintAnchor;
                    while (i76 < size) {
                        int i80 = i73;
                        WidgetsList widgetsList4 = (WidgetsList) arrayList4.get(i76);
                        if (i63 == 0) {
                            if (i76 < size - 1) {
                                constraintAnchor7 = ((WidgetsList) arrayList4.get(i76 + 1)).biggest.mTop;
                                arrayList = arrayList4;
                                i25 = 0;
                            } else {
                                arrayList = arrayList4;
                                i25 = flow4.mPaddingBottom;
                                constraintAnchor7 = constraintAnchor4;
                            }
                            ConstraintAnchor constraintAnchor9 = widgetsList4.biggest.mBottom;
                            widgetsList4.setup(i63, constraintAnchor5, constraintAnchor8, constraintAnchor6, constraintAnchor7, i79, i75, i72, i25, i57);
                            int max = Math.max(i77, widgetsList4.getWidth());
                            int height = widgetsList4.getHeight() + i78;
                            if (i76 > 0) {
                                height += flow4.mVerticalGap;
                            }
                            i24 = size;
                            i77 = max;
                            i78 = height;
                            constraintAnchor8 = constraintAnchor9;
                            i73 = i25;
                            arrayList4 = arrayList;
                            i75 = 0;
                        } else {
                            ArrayList arrayList5 = arrayList4;
                            if (i76 < size - 1) {
                                arrayList4 = arrayList5;
                                i24 = size;
                                constraintAnchor6 = ((WidgetsList) arrayList4.get(i76 + 1)).biggest.mLeft;
                                i72 = 0;
                            } else {
                                arrayList4 = arrayList5;
                                i72 = flow4.mResolvedPaddingRight;
                                i24 = size;
                                constraintAnchor6 = constraintAnchor3;
                            }
                            ConstraintAnchor constraintAnchor10 = widgetsList4.biggest.mRight;
                            widgetsList4.setup(i63, constraintAnchor5, constraintAnchor8, constraintAnchor6, constraintAnchor7, i79, i75, i72, i80, i57);
                            int width = widgetsList4.getWidth() + i77;
                            int max2 = Math.max(i78, widgetsList4.getHeight());
                            if (i76 > 0) {
                                width += flow4.mHorizontalGap;
                            }
                            i77 = width;
                            constraintAnchor5 = constraintAnchor10;
                            i78 = max2;
                            i73 = i80;
                            i79 = 0;
                        }
                        i76++;
                        size = i24;
                    }
                    iArr[0] = i77;
                    iArr[1] = i78;
                }
            } else {
                if (i61 == 2) {
                    int i81 = i5;
                    ConstraintWidget[] constraintWidgetArr4 = constraintWidgetArr3;
                    int i82 = flow4.mOrientation;
                    if (i82 == 0) {
                        int i83 = flow4.mMaxElementsWrap;
                        if (i83 <= 0) {
                            int i84 = 0;
                            int i85 = 0;
                            for (int i86 = 0; i86 < i81; i86++) {
                                if (i86 > 0) {
                                    i84 += flow4.mHorizontalGap;
                                }
                                ConstraintWidget constraintWidget8 = constraintWidgetArr4[i86];
                                if (constraintWidget8 != null) {
                                    int widgetWidth2 = flow4.getWidgetWidth(constraintWidget8, i57) + i84;
                                    if (widgetWidth2 > i57) {
                                        break;
                                    }
                                    i85++;
                                    i84 = widgetWidth2;
                                }
                            }
                            i83 = i85;
                        }
                        i28 = i83;
                        i27 = 0;
                    } else {
                        i27 = flow4.mMaxElementsWrap;
                        if (i27 <= 0) {
                            int i87 = 0;
                            int i88 = 0;
                            for (int i89 = 0; i89 < i81; i89++) {
                                if (i89 > 0) {
                                    i87 += flow4.mVerticalGap;
                                }
                                ConstraintWidget constraintWidget9 = constraintWidgetArr4[i89];
                                if (constraintWidget9 != null) {
                                    int widgetHeight2 = flow4.getWidgetHeight(constraintWidget9, i57) + i87;
                                    if (widgetHeight2 > i57) {
                                        break;
                                    }
                                    i88++;
                                    i87 = widgetHeight2;
                                }
                            }
                            i27 = i88;
                        }
                        i28 = 0;
                    }
                    if (flow4.mAlignedDimensions == null) {
                        flow4.mAlignedDimensions = new int[2];
                    }
                    if ((i27 == 0 && i82 == 1) || (i28 == 0 && i82 == 0)) {
                        i29 = i4;
                        i30 = i82;
                        i31 = i27;
                        i32 = i28;
                        i37 = i81;
                        i15 = i54;
                        i16 = i51;
                        i17 = i52;
                        iArr2 = iArr3;
                        z2 = true;
                        i33 = i;
                        i34 = i2;
                        i35 = i3;
                        flow3 = flow4;
                        z = z2;
                        flow = flow4;
                        flow4 = flow3;
                        i81 = i37;
                        constraintWidgetArr = constraintWidgetArr4;
                        while (!z) {
                        }
                        int i90 = i33;
                        int[] iArr4 = flow4.mAlignedDimensions;
                        iArr4[0] = i32;
                        iArr4[1] = i31;
                        flow4 = flow;
                        i11 = i90;
                        i21 = i34;
                        i20 = i35;
                        i19 = i29;
                        i18 = i53;
                        i14 = i19;
                        i10 = 0;
                        i13 = i20;
                        i12 = i21;
                        c = 1;
                        int i91 = iArr2[i10] + i16 + i17;
                        int i92 = iArr2[c] + i18 + i15;
                        if (i11 != 1073741824) {
                        }
                        if (i13 != 1073741824) {
                        }
                        flow4.mMeasuredWidth = r0;
                        flow4.mMeasuredHeight = r2;
                        flow4.setWidth(r0);
                        flow4.setHeight(r2);
                        flow4.mNeedsCallFromSolver = flow4.mWidgetsCount <= 0 ? c : i10;
                    }
                    i29 = i4;
                    i30 = i82;
                    i31 = i27;
                    i32 = i28;
                    flow = flow4;
                    i15 = i54;
                    constraintWidgetArr = constraintWidgetArr4;
                    i16 = i51;
                    i17 = i52;
                    iArr2 = iArr3;
                    z = false;
                    i33 = i;
                    i34 = i2;
                    i35 = i3;
                    while (!z) {
                        if (i30 == 0) {
                            flow2 = flow;
                            i36 = i33;
                            i31 = (int) Math.ceil(i81 / i32);
                        } else {
                            flow2 = flow;
                            i36 = i33;
                            i32 = (int) Math.ceil(i81 / i31);
                        }
                        ConstraintWidget[] constraintWidgetArr5 = flow4.mAlignedBiggestElementsInCols;
                        if (constraintWidgetArr5 == null || constraintWidgetArr5.length < i32) {
                            flow4.mAlignedBiggestElementsInCols = new ConstraintWidget[i32];
                        } else {
                            Arrays.fill(constraintWidgetArr5, (Object) null);
                        }
                        ConstraintWidget[] constraintWidgetArr6 = flow4.mAlignedBiggestElementsInRows;
                        if (constraintWidgetArr6 == null || constraintWidgetArr6.length < i31) {
                            flow4.mAlignedBiggestElementsInRows = new ConstraintWidget[i31];
                        } else {
                            Arrays.fill(constraintWidgetArr6, (Object) null);
                        }
                        for (int i93 = 0; i93 < i32; i93++) {
                            int i94 = 0;
                            while (i94 < i31) {
                                int i95 = (i94 * i32) + i93;
                                int i96 = i34;
                                if (i30 == 1) {
                                    i95 = (i93 * i31) + i94;
                                }
                                int i97 = i35;
                                int i98 = i95;
                                if (i98 < constraintWidgetArr.length && (constraintWidget = constraintWidgetArr[i98]) != null) {
                                    int widgetWidth3 = flow4.getWidgetWidth(constraintWidget, i57);
                                    i38 = i29;
                                    ConstraintWidget constraintWidget10 = flow4.mAlignedBiggestElementsInCols[i93];
                                    if (constraintWidget10 == null || constraintWidget10.getWidth() < widgetWidth3) {
                                        flow4.mAlignedBiggestElementsInCols[i93] = constraintWidget;
                                    }
                                    int widgetHeight3 = flow4.getWidgetHeight(constraintWidget, i57);
                                    ConstraintWidget constraintWidget11 = flow4.mAlignedBiggestElementsInRows[i94];
                                    if (constraintWidget11 == null || constraintWidget11.getHeight() < widgetHeight3) {
                                        flow4.mAlignedBiggestElementsInRows[i94] = constraintWidget;
                                    }
                                } else {
                                    i38 = i29;
                                }
                                i94++;
                                i34 = i96;
                                i35 = i97;
                                i29 = i38;
                            }
                        }
                        int i99 = i34;
                        int i100 = i35;
                        int i101 = i29;
                        int i102 = 0;
                        for (int i103 = 0; i103 < i32; i103++) {
                            ConstraintWidget constraintWidget12 = flow4.mAlignedBiggestElementsInCols[i103];
                            if (constraintWidget12 != null) {
                                if (i103 > 0) {
                                    i102 += flow4.mHorizontalGap;
                                }
                                i102 = flow4.getWidgetWidth(constraintWidget12, i57) + i102;
                            }
                        }
                        int i104 = 0;
                        for (int i105 = 0; i105 < i31; i105++) {
                            ConstraintWidget constraintWidget13 = flow4.mAlignedBiggestElementsInRows[i105];
                            if (constraintWidget13 != null) {
                                if (i105 > 0) {
                                    i104 += flow4.mVerticalGap;
                                }
                                i104 = flow4.getWidgetHeight(constraintWidget13, i57) + i104;
                            }
                        }
                        iArr3[0] = i102;
                        z2 = true;
                        iArr3[1] = i104;
                        if (i30 != 0) {
                            if (i104 > i57 && i31 > 1) {
                                i31--;
                                flow = flow2;
                                i33 = i36;
                                i34 = i99;
                                i35 = i100;
                                i29 = i101;
                            }
                            i33 = i36;
                            i34 = i99;
                            i35 = i100;
                            i29 = i101;
                            constraintWidgetArr4 = constraintWidgetArr;
                            i37 = i81;
                            flow3 = flow4;
                            flow4 = flow2;
                        } else {
                            if (i102 > i57 && i32 > 1) {
                                i32--;
                                flow = flow2;
                                i33 = i36;
                                i34 = i99;
                                i35 = i100;
                                i29 = i101;
                            }
                            i33 = i36;
                            i34 = i99;
                            i35 = i100;
                            i29 = i101;
                            constraintWidgetArr4 = constraintWidgetArr;
                            i37 = i81;
                            flow3 = flow4;
                            flow4 = flow2;
                        }
                        z = z2;
                        flow = flow4;
                        flow4 = flow3;
                        i81 = i37;
                        constraintWidgetArr = constraintWidgetArr4;
                        while (!z) {
                        }
                    }
                    int i902 = i33;
                    int[] iArr42 = flow4.mAlignedDimensions;
                    iArr42[0] = i32;
                    iArr42[1] = i31;
                    flow4 = flow;
                    i11 = i902;
                    i21 = i34;
                    i20 = i35;
                    i19 = i29;
                    i18 = i53;
                    i14 = i19;
                    i10 = 0;
                    i13 = i20;
                    i12 = i21;
                    c = 1;
                    int i912 = iArr2[i10] + i16 + i17;
                    int i922 = iArr2[c] + i18 + i15;
                    int min = i11 != 1073741824 ? i12 : i11 == Integer.MIN_VALUE ? Math.min(i912, i12) : i11 == 0 ? i912 : i10;
                    int min2 = i13 != 1073741824 ? i14 : i13 == Integer.MIN_VALUE ? Math.min(i922, i14) : i13 == 0 ? i922 : i10;
                    flow4.mMeasuredWidth = min;
                    flow4.mMeasuredHeight = min2;
                    flow4.setWidth(min);
                    flow4.setHeight(min2);
                    flow4.mNeedsCallFromSolver = flow4.mWidgetsCount <= 0 ? c : i10;
                }
                if (i61 == 3) {
                    int i106 = flow4.mOrientation;
                    if (i5 != 0) {
                        arrayList3.clear();
                        int i107 = i5;
                        ArrayList arrayList6 = arrayList3;
                        int i108 = i57;
                        WidgetsList widgetsList5 = new WidgetsList(i106, flow4.mLeft, flow4.mTop, flow4.mRight, flow4.mBottom, i108);
                        arrayList6.add(widgetsList5);
                        if (i106 == 0) {
                            int i109 = 0;
                            i40 = 0;
                            int i110 = 0;
                            int i111 = 0;
                            while (i111 < i107) {
                                int i112 = i109 + 1;
                                ConstraintWidget constraintWidget14 = constraintWidgetArr3[i111];
                                int i113 = i108;
                                int widgetWidth4 = flow4.getWidgetWidth(constraintWidget14, i113);
                                if (constraintWidget14.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    i40++;
                                }
                                int i114 = i40;
                                boolean z7 = (i110 == i113 || (flow4.mHorizontalGap + i110) + widgetWidth4 > i113) && widgetsList5.biggest != null;
                                if (!z7 && i111 > 0 && (i48 = flow4.mMaxElementsWrap) > 0 && i112 > i48) {
                                    z7 = true;
                                }
                                if (z7) {
                                    i108 = i113;
                                    i44 = i51;
                                    i45 = i52;
                                    constraintWidget2 = constraintWidget14;
                                    i46 = i53;
                                    i47 = i111;
                                    WidgetsList widgetsList6 = new WidgetsList(i106, flow4.mLeft, flow4.mTop, flow4.mRight, flow4.mBottom, i108);
                                    widgetsList6.mStartIndex = i47;
                                    arrayList6.add(widgetsList6);
                                    widgetsList5 = widgetsList6;
                                    i110 = widgetWidth4;
                                    i109 = i112;
                                } else {
                                    i108 = i113;
                                    i44 = i51;
                                    i45 = i52;
                                    i46 = i53;
                                    constraintWidget2 = constraintWidget14;
                                    i47 = i111;
                                    if (i47 > 0) {
                                        widgetWidth4 = flow4.mHorizontalGap + widgetWidth4 + i110;
                                    }
                                    i110 = widgetWidth4;
                                    i109 = 0;
                                }
                                widgetsList5.add(constraintWidget2);
                                i111 = i47 + 1;
                                i40 = i114;
                                i51 = i44;
                                i52 = i45;
                                i53 = i46;
                            }
                            i6 = i51;
                            i7 = i52;
                            i8 = i53;
                            i39 = i108;
                            i9 = i54;
                        } else {
                            i6 = i51;
                            i7 = i52;
                            i8 = i53;
                            int i115 = 0;
                            int i116 = 0;
                            int i117 = 0;
                            while (i117 < i107) {
                                ConstraintWidget constraintWidget15 = constraintWidgetArr3[i117];
                                int i118 = i108;
                                int widgetHeight4 = flow4.getWidgetHeight(constraintWidget15, i118);
                                if (constraintWidget15.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    i115++;
                                }
                                int i119 = i115;
                                boolean z8 = (i116 == i118 || (flow4.mVerticalGap + i116) + widgetHeight4 > i118) && widgetsList5.biggest != null;
                                if (!z8 && i117 > 0 && (i42 = flow4.mMaxElementsWrap) > 0 && i42 < 0) {
                                    z8 = true;
                                }
                                if (z8) {
                                    i41 = i54;
                                    WidgetsList widgetsList7 = new WidgetsList(i106, flow4.mLeft, flow4.mTop, flow4.mRight, flow4.mBottom, i118);
                                    widgetsList7.mStartIndex = i117;
                                    arrayList6.add(widgetsList7);
                                    widgetsList5 = widgetsList7;
                                } else {
                                    i41 = i54;
                                    if (i117 > 0) {
                                        i116 = flow4.mVerticalGap + widgetHeight4 + i116;
                                        widgetsList5.add(constraintWidget15);
                                        i117++;
                                        i115 = i119;
                                        i54 = i41;
                                        i108 = i118;
                                    }
                                }
                                i116 = widgetHeight4;
                                widgetsList5.add(constraintWidget15);
                                i117++;
                                i115 = i119;
                                i54 = i41;
                                i108 = i118;
                            }
                            i39 = i108;
                            i9 = i54;
                            i40 = i115;
                        }
                        int size2 = arrayList6.size();
                        int i120 = flow4.mResolvedPaddingLeft;
                        int i121 = flow4.mPaddingTop;
                        int i122 = flow4.mResolvedPaddingRight;
                        int i123 = flow4.mPaddingBottom;
                        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = flow4.mListDimensionBehaviors;
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = dimensionBehaviourArr2[0];
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                        boolean z9 = dimensionBehaviour6 == dimensionBehaviour7 || dimensionBehaviourArr2[1] == dimensionBehaviour7;
                        if (i40 > 0 && z9) {
                            for (int i124 = 0; i124 < size2; i124++) {
                                WidgetsList widgetsList8 = (WidgetsList) arrayList6.get(i124);
                                if (i106 == 0) {
                                    widgetsList8.measureMatchConstraints(i39 - widgetsList8.getWidth());
                                } else {
                                    widgetsList8.measureMatchConstraints(i39 - widgetsList8.getHeight());
                                }
                            }
                        }
                        int i125 = i121;
                        ConstraintAnchor constraintAnchor11 = constraintAnchor;
                        ConstraintAnchor constraintAnchor12 = constraintAnchor3;
                        ConstraintAnchor constraintAnchor13 = constraintAnchor4;
                        int i126 = 0;
                        int i127 = 0;
                        int i128 = 0;
                        int i129 = i120;
                        ConstraintAnchor constraintAnchor14 = constraintAnchor2;
                        while (i126 < size2) {
                            int[] iArr5 = iArr3;
                            WidgetsList widgetsList9 = (WidgetsList) arrayList6.get(i126);
                            if (i106 == 0) {
                                if (i126 < size2 - 1) {
                                    constraintAnchor13 = ((WidgetsList) arrayList6.get(i126 + 1)).biggest.mTop;
                                    arrayList2 = arrayList6;
                                    i123 = 0;
                                } else {
                                    i123 = flow4.mPaddingBottom;
                                    arrayList2 = arrayList6;
                                    constraintAnchor13 = constraintAnchor4;
                                }
                                ConstraintAnchor constraintAnchor15 = widgetsList9.biggest.mBottom;
                                widgetsList9.setup(i106, constraintAnchor14, constraintAnchor11, constraintAnchor12, constraintAnchor13, i129, i125, i122, i123, i39);
                                int max3 = Math.max(i127, widgetsList9.getWidth());
                                int height2 = widgetsList9.getHeight() + i128;
                                if (i126 > 0) {
                                    height2 += flow4.mVerticalGap;
                                }
                                i43 = size2;
                                i127 = max3;
                                i128 = height2;
                                constraintAnchor11 = constraintAnchor15;
                                arrayList6 = arrayList2;
                                i125 = 0;
                            } else {
                                ArrayList arrayList7 = arrayList6;
                                if (i126 < size2 - 1) {
                                    arrayList6 = arrayList7;
                                    i43 = size2;
                                    constraintAnchor12 = ((WidgetsList) arrayList6.get(i126 + 1)).biggest.mLeft;
                                    i122 = 0;
                                } else {
                                    arrayList6 = arrayList7;
                                    i122 = flow4.mResolvedPaddingRight;
                                    i43 = size2;
                                    constraintAnchor12 = constraintAnchor3;
                                }
                                ConstraintAnchor constraintAnchor16 = widgetsList9.biggest.mRight;
                                widgetsList9.setup(i106, constraintAnchor14, constraintAnchor11, constraintAnchor12, constraintAnchor13, i129, i125, i122, i123, i39);
                                int width2 = widgetsList9.getWidth() + i127;
                                int max4 = Math.max(i128, widgetsList9.getHeight());
                                if (i126 > 0) {
                                    width2 += flow4.mHorizontalGap;
                                }
                                i127 = width2;
                                i128 = max4;
                                i129 = 0;
                                constraintAnchor14 = constraintAnchor16;
                            }
                            i126++;
                            size2 = i43;
                            iArr3 = iArr5;
                        }
                        iArr = iArr3;
                        iArr[0] = i127;
                        iArr[1] = i128;
                    }
                }
                i6 = i51;
                i7 = i52;
                i8 = i53;
                i9 = i54;
                iArr = iArr3;
            }
        } else {
            int i130 = i5;
            i6 = i51;
            i7 = i52;
            i8 = i53;
            i9 = i54;
            iArr = iArr3;
            int i131 = flow4.mOrientation;
            if (i130 != 0) {
                if (arrayList3.size() == 0) {
                    widgetsList = new WidgetsList(i131, flow4.mLeft, flow4.mTop, flow4.mRight, flow4.mBottom, i57);
                    arrayList3.add(widgetsList);
                } else {
                    widgetsList = (WidgetsList) arrayList3.get(0);
                    widgetsList.biggestDimension = 0;
                    widgetsList.biggest = null;
                    widgetsList.mWidth = 0;
                    widgetsList.mHeight = 0;
                    widgetsList.mStartIndex = 0;
                    widgetsList.mCount = 0;
                    widgetsList.mNbMatchConstraintsWidgets = 0;
                    widgetsList.setup(i131, flow4.mLeft, flow4.mTop, flow4.mRight, flow4.mBottom, flow4.mResolvedPaddingLeft, flow4.mPaddingTop, flow4.mResolvedPaddingRight, flow4.mPaddingBottom, i57);
                }
                for (int i132 = 0; i132 < i130; i132++) {
                    widgetsList.add(constraintWidgetArr3[i132]);
                }
                i10 = 0;
                iArr[0] = widgetsList.getWidth();
                c = 1;
                iArr[1] = widgetsList.getHeight();
                i11 = i;
                i12 = i2;
                i13 = i3;
                i14 = i4;
                i15 = i9;
                i16 = i6;
                i17 = i7;
                i18 = i8;
                iArr2 = iArr;
                int i9122 = iArr2[i10] + i16 + i17;
                int i9222 = iArr2[c] + i18 + i15;
                if (i11 != 1073741824) {
                }
                if (i13 != 1073741824) {
                }
                flow4.mMeasuredWidth = min;
                flow4.mMeasuredHeight = min2;
                flow4.setWidth(min);
                flow4.setHeight(min2);
                flow4.mNeedsCallFromSolver = flow4.mWidgetsCount <= 0 ? c : i10;
            }
        }
        i11 = i;
        i21 = i2;
        i20 = i3;
        i19 = i4;
        i15 = i9;
        i16 = i6;
        i17 = i7;
        i18 = i8;
        iArr2 = iArr;
        i14 = i19;
        i10 = 0;
        i13 = i20;
        i12 = i21;
        c = 1;
        int i91222 = iArr2[i10] + i16 + i17;
        int i92222 = iArr2[c] + i18 + i15;
        if (i11 != 1073741824) {
        }
        if (i13 != 1073741824) {
        }
        flow4.mMeasuredWidth = min;
        flow4.mMeasuredHeight = min2;
        flow4.setWidth(min);
        flow4.setHeight(min2);
        flow4.mNeedsCallFromSolver = flow4.mWidgetsCount <= 0 ? c : i10;
    }
}
