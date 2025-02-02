package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class Chain {
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0067, code lost:
    
        if (r14 == r9) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0087, code lost:
    
        r14 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:333:0x0085, code lost:
    
        r14 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:342:0x0083, code lost:
    
        if (r14 == 2) goto L38;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x02b0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x055b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0567  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0574  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x057d  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0584  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0592  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0596 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x05b5 A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0580  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0577  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0569  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0329 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:226:0x03bd  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0431 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0444  */
    /* JADX WARN: Removed duplicated region for block: B:316:0x0517  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x054a A[ADDED_TO_REGION] */
    /* JADX WARN: Type inference failed for: r1v60, types: [androidx.constraintlayout.core.widgets.ConstraintWidget] */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4, types: [androidx.constraintlayout.core.widgets.ConstraintWidget] */
    /* JADX WARN: Type inference failed for: r9v42 */
    /* JADX WARN: Type inference failed for: r9v43 */
    /* JADX WARN: Type inference failed for: r9v48 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ArrayList<ConstraintWidget> arrayList, int i) {
        int i2;
        ChainHead[] chainHeadArr;
        int i3;
        int i4;
        boolean z;
        boolean z2;
        boolean z3;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        float f;
        SolverVariable solverVariable;
        int i5;
        ArrayList<ConstraintWidget> arrayList2;
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        ConstraintWidget constraintWidget3;
        int i6;
        ConstraintWidget constraintWidget4;
        ConstraintWidget constraintWidget5;
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        ConstraintWidget constraintWidget6;
        int i7;
        ConstraintWidget constraintWidget7;
        ConstraintWidget constraintWidget8;
        ConstraintAnchor constraintAnchor3;
        SolverVariable solverVariable2;
        SolverVariable solverVariable3;
        ConstraintWidget constraintWidget9;
        ConstraintAnchor constraintAnchor4;
        ConstraintWidget constraintWidget10;
        SolverVariable solverVariable4;
        ConstraintWidget constraintWidget11;
        ConstraintWidget constraintWidget12;
        ConstraintWidget constraintWidget13;
        ConstraintWidget constraintWidget14;
        ConstraintWidget constraintWidget15;
        SolverVariable solverVariable5;
        ConstraintWidget constraintWidget16;
        int size;
        int i8;
        ArrayList<ConstraintWidget> arrayList3;
        int i9;
        int i10;
        boolean z4;
        ConstraintWidget constraintWidget17;
        ChainHead chainHead;
        ConstraintWidget constraintWidget18;
        int i11;
        int i12;
        ConstraintWidgetContainer constraintWidgetContainer2 = constraintWidgetContainer;
        ArrayList<ConstraintWidget> arrayList4 = arrayList;
        int i13 = i;
        if (i13 == 0) {
            i2 = constraintWidgetContainer2.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer2.mHorizontalChainsArray;
            i3 = 0;
        } else {
            i2 = constraintWidgetContainer2.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer2.mVerticalChainsArray;
            i3 = 2;
        }
        int i14 = 0;
        while (i14 < i2) {
            ChainHead chainHead2 = chainHeadArr[i14];
            chainHead2.define();
            if (arrayList4 == null || arrayList4.contains(chainHead2.mFirst)) {
                ConstraintWidget constraintWidget19 = chainHead2.mFirst;
                ConstraintWidget constraintWidget20 = chainHead2.mLast;
                ConstraintWidget constraintWidget21 = chainHead2.mFirstVisibleWidget;
                ConstraintWidget constraintWidget22 = chainHead2.mLastVisibleWidget;
                ConstraintWidget constraintWidget23 = chainHead2.mHead;
                float f2 = chainHead2.mTotalWeight;
                boolean z5 = constraintWidgetContainer2.mListDimensionBehaviors[i13] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (i13 == 0) {
                    int i15 = constraintWidget23.mHorizontalChainStyle;
                    z = i15 == 0;
                    i4 = i14;
                    if (i15 == 1) {
                        z2 = true;
                        i12 = 2;
                    } else {
                        i12 = 2;
                        z2 = false;
                    }
                } else {
                    i4 = i14;
                    int i16 = constraintWidget23.mVerticalChainStyle;
                    z = i16 == 0;
                    z2 = i16 == 1;
                }
                ?? r9 = constraintWidget19;
                boolean z6 = false;
                while (true) {
                    dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    f = f2;
                    solverVariable = null;
                    if (z6) {
                        break;
                    }
                    ConstraintAnchor constraintAnchor5 = r9.mListAnchors[i3];
                    int i17 = z3 ? 1 : 4;
                    int margin = constraintAnchor5.getMargin();
                    if (r9.mListDimensionBehaviors[i13] == dimensionBehaviour && r9.mResolvedMatchConstraintDefault[i13] == 0) {
                        i10 = i2;
                        z4 = true;
                    } else {
                        i10 = i2;
                        z4 = false;
                    }
                    ConstraintAnchor constraintAnchor6 = constraintAnchor5.mTarget;
                    if (constraintAnchor6 != null && r9 != constraintWidget19) {
                        margin = constraintAnchor6.getMargin() + margin;
                    }
                    int i18 = margin;
                    if (!z3 || r9 == constraintWidget19 || r9 == constraintWidget21) {
                        constraintWidget17 = constraintWidget23;
                    } else {
                        constraintWidget17 = constraintWidget23;
                        i17 = 8;
                    }
                    ConstraintAnchor constraintAnchor7 = constraintAnchor5.mTarget;
                    if (constraintAnchor7 != null) {
                        if (r9 == constraintWidget21) {
                            constraintWidget18 = constraintWidget19;
                            chainHead = chainHead2;
                            linearSystem.addGreaterThan(constraintAnchor5.mSolverVariable, constraintAnchor7.mSolverVariable, i18, 6);
                        } else {
                            chainHead = chainHead2;
                            constraintWidget18 = constraintWidget19;
                            linearSystem.addGreaterThan(constraintAnchor5.mSolverVariable, constraintAnchor7.mSolverVariable, i18, 8);
                        }
                        if (z4 && !z3) {
                            i17 = 5;
                        }
                        linearSystem.addEquality(constraintAnchor5.mSolverVariable, constraintAnchor5.mTarget.mSolverVariable, i18, (r9 == constraintWidget21 && z3 && r9.isInBarrier(i13)) ? 5 : i17);
                    } else {
                        chainHead = chainHead2;
                        constraintWidget18 = constraintWidget19;
                    }
                    if (z5) {
                        if (r9.getVisibility() == 8 || r9.mListDimensionBehaviors[i13] != dimensionBehaviour) {
                            i11 = 0;
                        } else {
                            ConstraintAnchor[] constraintAnchorArr = r9.mListAnchors;
                            i11 = 0;
                            linearSystem.addGreaterThan(constraintAnchorArr[i3 + 1].mSolverVariable, constraintAnchorArr[i3].mSolverVariable, 0, 5);
                        }
                        linearSystem.addGreaterThan(r9.mListAnchors[i3].mSolverVariable, constraintWidgetContainer2.mListAnchors[i3].mSolverVariable, i11, 8);
                    }
                    ConstraintAnchor constraintAnchor8 = r9.mListAnchors[i3 + 1].mTarget;
                    if (constraintAnchor8 != null) {
                        ?? r1 = constraintAnchor8.mOwner;
                        ConstraintAnchor constraintAnchor9 = r1.mListAnchors[i3].mTarget;
                        if (constraintAnchor9 != null && constraintAnchor9.mOwner == r9) {
                            solverVariable = r1;
                        }
                    }
                    if (solverVariable != null) {
                        r9 = solverVariable;
                    } else {
                        z6 = true;
                    }
                    f2 = f;
                    constraintWidget23 = constraintWidget17;
                    i2 = i10;
                    constraintWidget19 = constraintWidget18;
                    chainHead2 = chainHead;
                    r9 = r9;
                }
                ChainHead chainHead3 = chainHead2;
                ConstraintWidget constraintWidget24 = constraintWidget23;
                ConstraintWidget constraintWidget25 = constraintWidget19;
                i5 = i2;
                if (constraintWidget22 != null) {
                    int i19 = i3 + 1;
                    if (constraintWidget20.mListAnchors[i19].mTarget != null) {
                        ConstraintAnchor constraintAnchor10 = constraintWidget22.mListAnchors[i19];
                        if ((constraintWidget22.mListDimensionBehaviors[i13] == dimensionBehaviour && constraintWidget22.mResolvedMatchConstraintDefault[i13] == 0) && !z3) {
                            ConstraintAnchor constraintAnchor11 = constraintAnchor10.mTarget;
                            if (constraintAnchor11.mOwner == constraintWidgetContainer2) {
                                linearSystem.addEquality(constraintAnchor10.mSolverVariable, constraintAnchor11.mSolverVariable, -constraintAnchor10.getMargin(), 5);
                                linearSystem.addLowerThan(constraintAnchor10.mSolverVariable, constraintWidget20.mListAnchors[i19].mTarget.mSolverVariable, -constraintAnchor10.getMargin(), 6);
                                if (z5) {
                                    int i20 = i3 + 1;
                                    SolverVariable solverVariable6 = constraintWidgetContainer2.mListAnchors[i20].mSolverVariable;
                                    ConstraintAnchor constraintAnchor12 = constraintWidget20.mListAnchors[i20];
                                    linearSystem.addGreaterThan(solverVariable6, constraintAnchor12.mSolverVariable, constraintAnchor12.getMargin(), 8);
                                }
                                arrayList2 = chainHead3.mWeightedMatchConstraintsWidgets;
                                if (arrayList2 != null && (size = arrayList2.size()) > 1) {
                                    float f3 = (chainHead3.mHasUndefinedWeights || chainHead3.mHasComplexMatchWeights) ? f : chainHead3.mWidgetsMatchCount;
                                    float f4 = 0.0f;
                                    float f5 = 0.0f;
                                    ConstraintWidget constraintWidget26 = null;
                                    i8 = 0;
                                    while (i8 < size) {
                                        ConstraintWidget constraintWidget27 = arrayList2.get(i8);
                                        float f6 = constraintWidget27.mWeight[i13];
                                        if (f6 < f4) {
                                            if (chainHead3.mHasComplexMatchWeights) {
                                                ConstraintAnchor[] constraintAnchorArr2 = constraintWidget27.mListAnchors;
                                                linearSystem.addEquality(constraintAnchorArr2[i3 + 1].mSolverVariable, constraintAnchorArr2[i3].mSolverVariable, 0, 4);
                                                arrayList3 = arrayList2;
                                                i9 = size;
                                                i8++;
                                                size = i9;
                                                arrayList2 = arrayList3;
                                                f4 = 0.0f;
                                            } else {
                                                f6 = 1.0f;
                                            }
                                        }
                                        if (f6 == 0.0f) {
                                            ConstraintAnchor[] constraintAnchorArr3 = constraintWidget27.mListAnchors;
                                            linearSystem.addEquality(constraintAnchorArr3[i3 + 1].mSolverVariable, constraintAnchorArr3[i3].mSolverVariable, 0, 8);
                                            arrayList3 = arrayList2;
                                            i9 = size;
                                            i8++;
                                            size = i9;
                                            arrayList2 = arrayList3;
                                            f4 = 0.0f;
                                        } else {
                                            if (constraintWidget26 != null) {
                                                ConstraintAnchor[] constraintAnchorArr4 = constraintWidget26.mListAnchors;
                                                SolverVariable solverVariable7 = constraintAnchorArr4[i3].mSolverVariable;
                                                int i21 = i3 + 1;
                                                SolverVariable solverVariable8 = constraintAnchorArr4[i21].mSolverVariable;
                                                ConstraintAnchor[] constraintAnchorArr5 = constraintWidget27.mListAnchors;
                                                arrayList3 = arrayList2;
                                                SolverVariable solverVariable9 = constraintAnchorArr5[i3].mSolverVariable;
                                                SolverVariable solverVariable10 = constraintAnchorArr5[i21].mSolverVariable;
                                                i9 = size;
                                                ArrayRow createRow = linearSystem.createRow();
                                                createRow.createRowEqualMatchDimensions(f5, f3, f6, solverVariable7, solverVariable8, solverVariable9, solverVariable10);
                                                linearSystem.addConstraint(createRow);
                                            } else {
                                                arrayList3 = arrayList2;
                                                i9 = size;
                                            }
                                            constraintWidget26 = constraintWidget27;
                                            f5 = f6;
                                            i8++;
                                            size = i9;
                                            arrayList2 = arrayList3;
                                            f4 = 0.0f;
                                        }
                                    }
                                }
                                if (constraintWidget21 == null && (constraintWidget21 == constraintWidget22 || z3)) {
                                    ConstraintAnchor constraintAnchor13 = constraintWidget25.mListAnchors[i3];
                                    int i22 = i3 + 1;
                                    ConstraintAnchor constraintAnchor14 = constraintWidget20.mListAnchors[i22];
                                    ConstraintAnchor constraintAnchor15 = constraintAnchor13.mTarget;
                                    SolverVariable solverVariable11 = constraintAnchor15 != null ? constraintAnchor15.mSolverVariable : null;
                                    ConstraintAnchor constraintAnchor16 = constraintAnchor14.mTarget;
                                    SolverVariable solverVariable12 = constraintAnchor16 != null ? constraintAnchor16.mSolverVariable : null;
                                    ConstraintAnchor constraintAnchor17 = constraintWidget21.mListAnchors[i3];
                                    if (constraintWidget22 != null) {
                                        constraintAnchor14 = constraintWidget22.mListAnchors[i22];
                                    }
                                    if (solverVariable11 == null || solverVariable12 == null) {
                                        constraintWidget = constraintWidget22;
                                        constraintWidget2 = constraintWidget21;
                                        constraintWidget16 = constraintWidget20;
                                        i6 = i4;
                                    } else {
                                        constraintWidget = constraintWidget22;
                                        SolverVariable solverVariable13 = solverVariable12;
                                        constraintWidget16 = constraintWidget20;
                                        constraintWidget2 = constraintWidget21;
                                        i6 = i4;
                                        linearSystem.addCentering(constraintAnchor17.mSolverVariable, solverVariable11, constraintAnchor17.getMargin(), i13 == 0 ? constraintWidget24.mHorizontalBiasPercent : constraintWidget24.mVerticalBiasPercent, solverVariable13, constraintAnchor14.mSolverVariable, constraintAnchor14.getMargin(), 7);
                                    }
                                    constraintWidget3 = constraintWidget16;
                                } else {
                                    constraintWidget = constraintWidget22;
                                    constraintWidget2 = constraintWidget21;
                                    constraintWidget3 = constraintWidget20;
                                    i6 = i4;
                                    constraintWidget4 = constraintWidget25;
                                    if (z || constraintWidget2 == null) {
                                        int i23 = 8;
                                        if (z2 && constraintWidget2 != null) {
                                            int i24 = chainHead3.mWidgetsMatchCount;
                                            boolean z7 = i24 <= 0 && chainHead3.mWidgetsCount == i24;
                                            constraintWidget5 = constraintWidget2;
                                            ConstraintWidget constraintWidget28 = constraintWidget5;
                                            while (constraintWidget5 != null) {
                                                ConstraintWidget constraintWidget29 = constraintWidget5.mNextChainWidget[i13];
                                                while (constraintWidget29 != null && constraintWidget29.getVisibility() == i23) {
                                                    constraintWidget29 = constraintWidget29.mNextChainWidget[i13];
                                                }
                                                if (constraintWidget5 == constraintWidget2 || constraintWidget5 == constraintWidget || constraintWidget29 == null) {
                                                    constraintWidget6 = constraintWidget28;
                                                    i7 = i23;
                                                    constraintWidget7 = constraintWidget29;
                                                } else {
                                                    ConstraintWidget constraintWidget30 = constraintWidget29 == constraintWidget ? null : constraintWidget29;
                                                    ConstraintAnchor constraintAnchor18 = constraintWidget5.mListAnchors[i3];
                                                    SolverVariable solverVariable14 = constraintAnchor18.mSolverVariable;
                                                    int i25 = i3 + 1;
                                                    SolverVariable solverVariable15 = constraintWidget28.mListAnchors[i25].mSolverVariable;
                                                    int margin2 = constraintAnchor18.getMargin();
                                                    int margin3 = constraintWidget5.mListAnchors[i25].getMargin();
                                                    if (constraintWidget30 != null) {
                                                        constraintAnchor3 = constraintWidget30.mListAnchors[i3];
                                                        solverVariable2 = constraintAnchor3.mSolverVariable;
                                                        constraintWidget8 = constraintWidget30;
                                                        ConstraintAnchor constraintAnchor19 = constraintAnchor3.mTarget;
                                                        solverVariable3 = constraintAnchor19 != null ? constraintAnchor19.mSolverVariable : null;
                                                    } else {
                                                        constraintWidget8 = constraintWidget30;
                                                        constraintAnchor3 = constraintWidget.mListAnchors[i3];
                                                        solverVariable2 = constraintAnchor3 != null ? constraintAnchor3.mSolverVariable : null;
                                                        solverVariable3 = constraintWidget5.mListAnchors[i25].mSolverVariable;
                                                    }
                                                    int margin4 = constraintAnchor3 != null ? constraintAnchor3.getMargin() + margin3 : margin3;
                                                    int margin5 = constraintWidget28.mListAnchors[i25].getMargin() + margin2;
                                                    int i26 = z7 ? 8 : 4;
                                                    if (solverVariable14 == null || solverVariable15 == null || solverVariable2 == null || solverVariable3 == null) {
                                                        constraintWidget6 = constraintWidget28;
                                                        i7 = 8;
                                                    } else {
                                                        constraintWidget6 = constraintWidget28;
                                                        i7 = 8;
                                                        linearSystem.addCentering(solverVariable14, solverVariable15, margin5, 0.5f, solverVariable2, solverVariable3, margin4, i26);
                                                    }
                                                    constraintWidget7 = constraintWidget8;
                                                }
                                                constraintWidget28 = constraintWidget5.getVisibility() != i7 ? constraintWidget5 : constraintWidget6;
                                                constraintWidget5 = constraintWidget7;
                                                i23 = i7;
                                                i13 = i;
                                            }
                                            ConstraintAnchor constraintAnchor20 = constraintWidget2.mListAnchors[i3];
                                            constraintAnchor = constraintWidget4.mListAnchors[i3].mTarget;
                                            int i27 = i3 + 1;
                                            ConstraintAnchor constraintAnchor21 = constraintWidget.mListAnchors[i27];
                                            ConstraintAnchor constraintAnchor22 = constraintWidget3.mListAnchors[i27].mTarget;
                                            if (constraintAnchor != null) {
                                                if (constraintWidget2 != constraintWidget) {
                                                    linearSystem.addEquality(constraintAnchor20.mSolverVariable, constraintAnchor.mSolverVariable, constraintAnchor20.getMargin(), 5);
                                                } else if (constraintAnchor22 != null) {
                                                    constraintAnchor2 = constraintAnchor22;
                                                    linearSystem.addCentering(constraintAnchor20.mSolverVariable, constraintAnchor.mSolverVariable, constraintAnchor20.getMargin(), 0.5f, constraintAnchor21.mSolverVariable, constraintAnchor22.mSolverVariable, constraintAnchor21.getMargin(), 5);
                                                    if (constraintAnchor2 != null && constraintWidget2 != constraintWidget) {
                                                        linearSystem.addEquality(constraintAnchor21.mSolverVariable, constraintAnchor2.mSolverVariable, -constraintAnchor21.getMargin(), 5);
                                                    }
                                                }
                                            }
                                            constraintAnchor2 = constraintAnchor22;
                                            if (constraintAnchor2 != null) {
                                                linearSystem.addEquality(constraintAnchor21.mSolverVariable, constraintAnchor2.mSolverVariable, -constraintAnchor21.getMargin(), 5);
                                            }
                                        }
                                    } else {
                                        int i28 = chainHead3.mWidgetsMatchCount;
                                        boolean z8 = i28 > 0 && chainHead3.mWidgetsCount == i28;
                                        ConstraintWidget constraintWidget31 = constraintWidget2;
                                        ConstraintWidget constraintWidget32 = constraintWidget31;
                                        while (constraintWidget32 != null) {
                                            ConstraintWidget constraintWidget33 = constraintWidget32.mNextChainWidget[i13];
                                            while (constraintWidget33 != null && constraintWidget33.getVisibility() == 8) {
                                                constraintWidget33 = constraintWidget33.mNextChainWidget[i13];
                                            }
                                            if (constraintWidget33 != null || constraintWidget32 == constraintWidget) {
                                                ConstraintAnchor constraintAnchor23 = constraintWidget32.mListAnchors[i3];
                                                SolverVariable solverVariable16 = constraintAnchor23.mSolverVariable;
                                                ConstraintAnchor constraintAnchor24 = constraintAnchor23.mTarget;
                                                SolverVariable solverVariable17 = constraintAnchor24 != null ? constraintAnchor24.mSolverVariable : null;
                                                if (constraintWidget31 != constraintWidget32) {
                                                    solverVariable17 = constraintWidget31.mListAnchors[i3 + 1].mSolverVariable;
                                                } else if (constraintWidget32 == constraintWidget2) {
                                                    ConstraintAnchor constraintAnchor25 = constraintWidget4.mListAnchors[i3].mTarget;
                                                    solverVariable17 = constraintAnchor25 != null ? constraintAnchor25.mSolverVariable : null;
                                                }
                                                int margin6 = constraintAnchor23.getMargin();
                                                int i29 = i3 + 1;
                                                int margin7 = constraintWidget32.mListAnchors[i29].getMargin();
                                                if (constraintWidget33 != null) {
                                                    constraintAnchor4 = constraintWidget33.mListAnchors[i3];
                                                    constraintWidget9 = constraintWidget4;
                                                    solverVariable4 = constraintAnchor4.mSolverVariable;
                                                } else {
                                                    constraintWidget9 = constraintWidget4;
                                                    constraintAnchor4 = constraintWidget3.mListAnchors[i29].mTarget;
                                                    if (constraintAnchor4 != null) {
                                                        solverVariable4 = constraintAnchor4.mSolverVariable;
                                                    } else {
                                                        constraintWidget10 = constraintWidget33;
                                                        solverVariable4 = null;
                                                        SolverVariable solverVariable18 = constraintWidget32.mListAnchors[i29].mSolverVariable;
                                                        if (constraintAnchor4 != null) {
                                                            margin7 += constraintAnchor4.getMargin();
                                                        }
                                                        int margin8 = constraintWidget31.mListAnchors[i29].getMargin() + margin6;
                                                        if (solverVariable16 != null || solverVariable17 == null || solverVariable4 == null || solverVariable18 == null) {
                                                            constraintWidget11 = constraintWidget32;
                                                            constraintWidget12 = constraintWidget10;
                                                            constraintWidget13 = constraintWidget9;
                                                            constraintWidget14 = constraintWidget31;
                                                        } else {
                                                            int margin9 = constraintWidget32 == constraintWidget2 ? constraintWidget2.mListAnchors[i3].getMargin() : margin8;
                                                            int margin10 = constraintWidget32 == constraintWidget ? constraintWidget.mListAnchors[i29].getMargin() : margin7;
                                                            SolverVariable solverVariable19 = solverVariable4;
                                                            constraintWidget12 = constraintWidget10;
                                                            constraintWidget14 = constraintWidget31;
                                                            constraintWidget13 = constraintWidget9;
                                                            constraintWidget11 = constraintWidget32;
                                                            linearSystem.addCentering(solverVariable16, solverVariable17, margin9, 0.5f, solverVariable19, solverVariable18, margin10, z8 ? 8 : 5);
                                                        }
                                                    }
                                                }
                                                constraintWidget10 = constraintWidget33;
                                                SolverVariable solverVariable182 = constraintWidget32.mListAnchors[i29].mSolverVariable;
                                                if (constraintAnchor4 != null) {
                                                }
                                                int margin82 = constraintWidget31.mListAnchors[i29].getMargin() + margin6;
                                                if (solverVariable16 != null) {
                                                }
                                                constraintWidget11 = constraintWidget32;
                                                constraintWidget12 = constraintWidget10;
                                                constraintWidget13 = constraintWidget9;
                                                constraintWidget14 = constraintWidget31;
                                            } else {
                                                constraintWidget13 = constraintWidget4;
                                                constraintWidget12 = constraintWidget33;
                                                constraintWidget14 = constraintWidget31;
                                                constraintWidget11 = constraintWidget32;
                                            }
                                            constraintWidget31 = constraintWidget11.getVisibility() != 8 ? constraintWidget11 : constraintWidget14;
                                            constraintWidget32 = constraintWidget12;
                                            constraintWidget4 = constraintWidget13;
                                        }
                                    }
                                }
                                if ((!z || z2) && constraintWidget2 != null && constraintWidget2 != constraintWidget) {
                                    ConstraintAnchor[] constraintAnchorArr6 = constraintWidget2.mListAnchors;
                                    ConstraintAnchor constraintAnchor26 = constraintAnchorArr6[i3];
                                    constraintWidget15 = constraintWidget == null ? constraintWidget2 : constraintWidget;
                                    int i30 = i3 + 1;
                                    ConstraintAnchor constraintAnchor27 = constraintWidget15.mListAnchors[i30];
                                    ConstraintAnchor constraintAnchor28 = constraintAnchor26.mTarget;
                                    solverVariable5 = constraintAnchor28 != null ? constraintAnchor28.mSolverVariable : null;
                                    ConstraintAnchor constraintAnchor29 = constraintAnchor27.mTarget;
                                    SolverVariable solverVariable20 = constraintAnchor29 != null ? constraintAnchor29.mSolverVariable : null;
                                    if (constraintWidget3 != constraintWidget15) {
                                        ConstraintAnchor constraintAnchor30 = constraintWidget3.mListAnchors[i30].mTarget;
                                        if (constraintAnchor30 != null) {
                                            solverVariable20 = constraintAnchor30.mSolverVariable;
                                        }
                                        if (constraintWidget2 == constraintWidget15) {
                                            constraintAnchor27 = constraintAnchorArr6[i30];
                                        }
                                        if (solverVariable5 != null && solverVariable != null) {
                                            linearSystem.addCentering(constraintAnchor26.mSolverVariable, solverVariable5, constraintAnchor26.getMargin(), 0.5f, solverVariable, constraintAnchor27.mSolverVariable, constraintWidget15.mListAnchors[i30].getMargin(), 5);
                                        }
                                    }
                                    solverVariable = solverVariable20;
                                    if (constraintWidget2 == constraintWidget15) {
                                    }
                                    if (solverVariable5 != null) {
                                        linearSystem.addCentering(constraintAnchor26.mSolverVariable, solverVariable5, constraintAnchor26.getMargin(), 0.5f, solverVariable, constraintAnchor27.mSolverVariable, constraintWidget15.mListAnchors[i30].getMargin(), 5);
                                    }
                                }
                            }
                        }
                        if (z3) {
                            ConstraintAnchor constraintAnchor31 = constraintAnchor10.mTarget;
                            if (constraintAnchor31.mOwner == constraintWidgetContainer2) {
                                linearSystem.addEquality(constraintAnchor10.mSolverVariable, constraintAnchor31.mSolverVariable, -constraintAnchor10.getMargin(), 4);
                            }
                        }
                        linearSystem.addLowerThan(constraintAnchor10.mSolverVariable, constraintWidget20.mListAnchors[i19].mTarget.mSolverVariable, -constraintAnchor10.getMargin(), 6);
                        if (z5) {
                        }
                        arrayList2 = chainHead3.mWeightedMatchConstraintsWidgets;
                        if (arrayList2 != null) {
                            if (chainHead3.mHasUndefinedWeights) {
                            }
                            float f42 = 0.0f;
                            float f52 = 0.0f;
                            ConstraintWidget constraintWidget262 = null;
                            i8 = 0;
                            while (i8 < size) {
                            }
                        }
                        if (constraintWidget21 == null) {
                        }
                        constraintWidget = constraintWidget22;
                        constraintWidget2 = constraintWidget21;
                        constraintWidget3 = constraintWidget20;
                        i6 = i4;
                        constraintWidget4 = constraintWidget25;
                        if (z) {
                        }
                        int i232 = 8;
                        if (z2) {
                            int i242 = chainHead3.mWidgetsMatchCount;
                            if (i242 <= 0) {
                            }
                            constraintWidget5 = constraintWidget2;
                            ConstraintWidget constraintWidget282 = constraintWidget5;
                            while (constraintWidget5 != null) {
                            }
                            ConstraintAnchor constraintAnchor202 = constraintWidget2.mListAnchors[i3];
                            constraintAnchor = constraintWidget4.mListAnchors[i3].mTarget;
                            int i272 = i3 + 1;
                            ConstraintAnchor constraintAnchor212 = constraintWidget.mListAnchors[i272];
                            ConstraintAnchor constraintAnchor222 = constraintWidget3.mListAnchors[i272].mTarget;
                            if (constraintAnchor != null) {
                            }
                            constraintAnchor2 = constraintAnchor222;
                            if (constraintAnchor2 != null) {
                            }
                        }
                        if (!z) {
                        }
                        ConstraintAnchor[] constraintAnchorArr62 = constraintWidget2.mListAnchors;
                        ConstraintAnchor constraintAnchor262 = constraintAnchorArr62[i3];
                        if (constraintWidget == null) {
                        }
                        int i302 = i3 + 1;
                        ConstraintAnchor constraintAnchor272 = constraintWidget15.mListAnchors[i302];
                        ConstraintAnchor constraintAnchor282 = constraintAnchor262.mTarget;
                        if (constraintAnchor282 != null) {
                        }
                        ConstraintAnchor constraintAnchor292 = constraintAnchor272.mTarget;
                        if (constraintAnchor292 != null) {
                        }
                        if (constraintWidget3 != constraintWidget15) {
                        }
                        solverVariable = solverVariable20;
                        if (constraintWidget2 == constraintWidget15) {
                        }
                        if (solverVariable5 != null) {
                        }
                    }
                }
                if (z5) {
                }
                arrayList2 = chainHead3.mWeightedMatchConstraintsWidgets;
                if (arrayList2 != null) {
                }
                if (constraintWidget21 == null) {
                }
                constraintWidget = constraintWidget22;
                constraintWidget2 = constraintWidget21;
                constraintWidget3 = constraintWidget20;
                i6 = i4;
                constraintWidget4 = constraintWidget25;
                if (z) {
                }
                int i2322 = 8;
                if (z2) {
                }
                if (!z) {
                }
                ConstraintAnchor[] constraintAnchorArr622 = constraintWidget2.mListAnchors;
                ConstraintAnchor constraintAnchor2622 = constraintAnchorArr622[i3];
                if (constraintWidget == null) {
                }
                int i3022 = i3 + 1;
                ConstraintAnchor constraintAnchor2722 = constraintWidget15.mListAnchors[i3022];
                ConstraintAnchor constraintAnchor2822 = constraintAnchor2622.mTarget;
                if (constraintAnchor2822 != null) {
                }
                ConstraintAnchor constraintAnchor2922 = constraintAnchor2722.mTarget;
                if (constraintAnchor2922 != null) {
                }
                if (constraintWidget3 != constraintWidget15) {
                }
                solverVariable = solverVariable20;
                if (constraintWidget2 == constraintWidget15) {
                }
                if (solverVariable5 != null) {
                }
            } else {
                i6 = i14;
                i5 = i2;
            }
            i14 = i6 + 1;
            constraintWidgetContainer2 = constraintWidgetContainer;
            arrayList4 = arrayList;
            i13 = i;
            i2 = i5;
        }
    }
}
