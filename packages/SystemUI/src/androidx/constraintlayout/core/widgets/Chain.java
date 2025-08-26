package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Chain {
    /* JADX WARN: Removed duplicated region for block: B:189:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0383  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:401:0x068d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:409:0x06a9  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x06b4  */
    /* JADX WARN: Removed duplicated region for block: B:413:0x06b7  */
    /* JADX WARN: Removed duplicated region for block: B:416:0x06bd  */
    /* JADX WARN: Removed duplicated region for block: B:417:0x06c0  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x06c4  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x06d4  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x06d8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:436:0x06f4 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0115  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ArrayList arrayList, int i) {
        int i2;
        ChainHead[] chainHeadArr;
        int i3;
        int i4;
        float f;
        float f2;
        boolean z;
        ConstraintWidget constraintWidget;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        ConstraintAnchor[] constraintAnchorArr;
        int i5;
        ChainHead[] chainHeadArr2;
        ConstraintWidget constraintWidget2;
        LinearSystem linearSystem2;
        SolverVariable solverVariable;
        ConstraintAnchor constraintAnchor;
        SolverVariable solverVariable2;
        ConstraintWidget constraintWidget3;
        int i6;
        ConstraintAnchor constraintAnchor2;
        SolverVariable solverVariable3;
        ConstraintWidget constraintWidget4;
        ConstraintWidget constraintWidget5;
        int i7;
        SolverVariable solverVariable4;
        int size;
        ArrayList arrayList2;
        int i8;
        ConstraintWidget constraintWidget6;
        int i9;
        float f3;
        float f4;
        boolean z6;
        int i10;
        ChainHead[] chainHeadArr3;
        ConstraintWidget constraintWidget7;
        int i11;
        int i12;
        int i13;
        int i14;
        ConstraintWidget constraintWidget8;
        float f5;
        ConstraintWidgetContainer constraintWidgetContainer2 = constraintWidgetContainer;
        LinearSystem linearSystem3 = linearSystem;
        ArrayList arrayList3 = arrayList;
        if (i == 0) {
            i2 = constraintWidgetContainer2.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer2.mHorizontalChainsArray;
            i3 = 0;
        } else {
            i2 = constraintWidgetContainer2.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer2.mVerticalChainsArray;
            i3 = 2;
        }
        int i15 = i2;
        ChainHead[] chainHeadArr4 = chainHeadArr;
        int i16 = 0;
        while (i16 < i15) {
            ChainHead chainHead = chainHeadArr4[i16];
            boolean z7 = chainHead.mDefined;
            ConstraintWidget constraintWidget9 = chainHead.mFirst;
            int i17 = 8;
            if (z7) {
                i4 = i16;
                f = 0.0f;
            } else {
                int i18 = chainHead.mOrientation;
                int i19 = i18 * 2;
                ConstraintWidget constraintWidget10 = constraintWidget9;
                ConstraintWidget constraintWidget11 = constraintWidget10;
                boolean z8 = false;
                f = 0.0f;
                while (!z8) {
                    chainHead.mWidgetsCount++;
                    constraintWidget10.mNextChainWidget[i18] = null;
                    constraintWidget10.mListNextMatchConstraintsWidget[i18] = null;
                    int i20 = constraintWidget10.mVisibility;
                    ConstraintAnchor[] constraintAnchorArr2 = constraintWidget10.mListAnchors;
                    if (i20 != i17) {
                        constraintWidget10.getDimensionBehaviour(i18);
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                        constraintAnchorArr2[i19].getMargin();
                        int i21 = i19 + 1;
                        constraintAnchorArr2[i21].getMargin();
                        constraintAnchorArr2[i19].getMargin();
                        constraintAnchorArr2[i21].getMargin();
                        if (chainHead.mFirstVisibleWidget == null) {
                            chainHead.mFirstVisibleWidget = constraintWidget10;
                        }
                        chainHead.mLastVisibleWidget = constraintWidget10;
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget10.mListDimensionBehaviors[i18];
                        if (dimensionBehaviour2 == dimensionBehaviour) {
                            i12 = i16;
                            int i22 = constraintWidget10.mResolvedMatchConstraintDefault[i18];
                            i13 = i18;
                            if (i22 == 0 || i22 == 3 || i22 == 2) {
                                chainHead.mWidgetsMatchCount++;
                                float f6 = constraintWidget10.mWeight[i13];
                                if (f6 > 0.0f) {
                                    f5 = f6;
                                    chainHead.mTotalWeight += f5;
                                } else {
                                    f5 = f6;
                                }
                                i14 = i19;
                                if (constraintWidget10.mVisibility != 8 && dimensionBehaviour2 == dimensionBehaviour && (i22 == 0 || i22 == 3)) {
                                    if (f5 < 0.0f) {
                                        chainHead.mHasUndefinedWeights = true;
                                    } else {
                                        chainHead.mHasDefinedWeights = true;
                                    }
                                    if (chainHead.mWeightedMatchConstraintsWidgets == null) {
                                        chainHead.mWeightedMatchConstraintsWidgets = new ArrayList();
                                    }
                                    chainHead.mWeightedMatchConstraintsWidgets.add(constraintWidget10);
                                }
                                if (chainHead.mFirstMatchConstraintWidget == null) {
                                    chainHead.mFirstMatchConstraintWidget = constraintWidget10;
                                }
                                ConstraintWidget constraintWidget12 = chainHead.mLastMatchConstraintWidget;
                                if (constraintWidget12 != null) {
                                    constraintWidget12.mListNextMatchConstraintsWidget[i13] = constraintWidget10;
                                }
                                chainHead.mLastMatchConstraintWidget = constraintWidget10;
                            } else {
                                i14 = i19;
                            }
                            if (i13 == 0) {
                                if (constraintWidget10.mMatchConstraintDefaultWidth == 0 && constraintWidget10.mMatchConstraintMinWidth == 0) {
                                    int i23 = constraintWidget10.mMatchConstraintMaxWidth;
                                }
                            } else if (constraintWidget10.mMatchConstraintDefaultHeight == 0 && constraintWidget10.mMatchConstraintMinHeight == 0) {
                                int i24 = constraintWidget10.mMatchConstraintMaxHeight;
                            }
                        } else {
                            i12 = i16;
                            i13 = i18;
                            i14 = i19;
                        }
                    }
                    if (constraintWidget11 != constraintWidget10) {
                        constraintWidget11.mNextChainWidget[i13] = constraintWidget10;
                    }
                    ConstraintAnchor constraintAnchor3 = constraintAnchorArr2[i14 + 1].mTarget;
                    if (constraintAnchor3 != null) {
                        constraintWidget8 = constraintAnchor3.mOwner;
                        ConstraintAnchor constraintAnchor4 = constraintWidget8.mListAnchors[i14].mTarget;
                        if (constraintAnchor4 == null || constraintAnchor4.mOwner != constraintWidget10) {
                            constraintWidget8 = null;
                        }
                    }
                    if (constraintWidget8 == null) {
                        constraintWidget8 = constraintWidget10;
                        z8 = true;
                    }
                    constraintWidget11 = constraintWidget10;
                    i18 = i13;
                    i19 = i14;
                    i17 = 8;
                    constraintWidget10 = constraintWidget8;
                    i16 = i12;
                }
                i4 = i16;
                int i25 = i18;
                int i26 = i19;
                ConstraintWidget constraintWidget13 = chainHead.mFirstVisibleWidget;
                if (constraintWidget13 != null) {
                    constraintWidget13.mListAnchors[i26].getMargin();
                }
                ConstraintWidget constraintWidget14 = chainHead.mLastVisibleWidget;
                if (constraintWidget14 != null) {
                    constraintWidget14.mListAnchors[i26 + 1].getMargin();
                }
                chainHead.mLast = constraintWidget10;
                if (i25 == 0 && chainHead.mIsRtl) {
                    chainHead.mHead = constraintWidget10;
                } else {
                    chainHead.mHead = constraintWidget9;
                }
                chainHead.mHasComplexMatchWeights = chainHead.mHasDefinedWeights && chainHead.mHasUndefinedWeights;
            }
            chainHead.mDefined = true;
            if (arrayList3 == null || arrayList3.contains(constraintWidget9)) {
                ConstraintWidget constraintWidget15 = chainHead.mLast;
                ConstraintWidget constraintWidget16 = chainHead.mFirstVisibleWidget;
                ConstraintWidget constraintWidget17 = chainHead.mLastVisibleWidget;
                ConstraintWidget constraintWidget18 = chainHead.mHead;
                float f7 = chainHead.mTotalWeight;
                boolean z9 = constraintWidgetContainer2.mListDimensionBehaviors[i] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (i == 0) {
                    int i27 = constraintWidget18.mHorizontalChainStyle;
                    boolean z10 = i27 == 0;
                    boolean z11 = i27 == 1;
                    z = i27 == 2;
                    z2 = z9;
                    z4 = z11;
                    z3 = z10;
                    z5 = false;
                    f2 = f7;
                    constraintWidget = constraintWidget9;
                } else {
                    int i28 = constraintWidget18.mVerticalChainStyle;
                    boolean z12 = i28 == 0;
                    boolean z13 = i28 == 1;
                    f2 = f7;
                    z = i28 == 2;
                    constraintWidget = constraintWidget9;
                    z2 = z9;
                    z3 = z12;
                    z4 = z13;
                    z5 = false;
                }
                while (true) {
                    constraintAnchorArr = constraintWidgetContainer2.mListAnchors;
                    if (z5) {
                        break;
                    }
                    ConstraintAnchor constraintAnchor5 = constraintWidget.mListAnchors[i3];
                    int i29 = z ? 1 : 4;
                    int margin = constraintAnchor5.getMargin();
                    boolean z14 = z;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = constraintWidget.mListDimensionBehaviors[i];
                    boolean z15 = z5;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    boolean z16 = dimensionBehaviour3 == dimensionBehaviour4 && constraintWidget.mResolvedMatchConstraintDefault[i] == 0;
                    ConstraintAnchor constraintAnchor6 = constraintAnchor5.mTarget;
                    if (constraintAnchor6 != null && constraintWidget != constraintWidget9) {
                        margin = constraintAnchor6.getMargin() + margin;
                    }
                    int i30 = margin;
                    if (z14 && constraintWidget != constraintWidget9 && constraintWidget != constraintWidget16) {
                        i29 = 8;
                    }
                    ConstraintAnchor constraintAnchor7 = constraintAnchor5.mTarget;
                    if (constraintAnchor7 != null) {
                        if (constraintWidget == constraintWidget16) {
                            i10 = i15;
                            chainHeadArr3 = chainHeadArr4;
                            linearSystem3.addGreaterThan(constraintAnchor5.mSolverVariable, constraintAnchor7.mSolverVariable, i30, 6);
                        } else {
                            i10 = i15;
                            chainHeadArr3 = chainHeadArr4;
                            linearSystem3.addGreaterThan(constraintAnchor5.mSolverVariable, constraintAnchor7.mSolverVariable, i30, 8);
                        }
                        if (z16 && !z14) {
                            i29 = 5;
                        }
                        linearSystem3.addEquality(constraintAnchor5.mSolverVariable, constraintAnchor5.mTarget.mSolverVariable, i30, (constraintWidget == constraintWidget16 && z14 && constraintWidget.mIsInBarrier[i]) ? 5 : i29);
                    } else {
                        i10 = i15;
                        chainHeadArr3 = chainHeadArr4;
                    }
                    ConstraintAnchor[] constraintAnchorArr3 = constraintWidget.mListAnchors;
                    if (z2) {
                        if (constraintWidget.mVisibility == 8 || constraintWidget.mListDimensionBehaviors[i] != dimensionBehaviour4) {
                            i11 = 0;
                        } else {
                            i11 = 0;
                            linearSystem3.addGreaterThan(constraintAnchorArr3[i3 + 1].mSolverVariable, constraintAnchorArr3[i3].mSolverVariable, 0, 5);
                        }
                        linearSystem3.addGreaterThan(constraintAnchorArr3[i3].mSolverVariable, constraintAnchorArr[i3].mSolverVariable, i11, 8);
                    }
                    ConstraintAnchor constraintAnchor8 = constraintAnchorArr3[i3 + 1].mTarget;
                    if (constraintAnchor8 != null) {
                        constraintWidget7 = constraintAnchor8.mOwner;
                        ConstraintAnchor constraintAnchor9 = constraintWidget7.mListAnchors[i3].mTarget;
                        if (constraintAnchor9 == null || constraintAnchor9.mOwner != constraintWidget) {
                            constraintWidget7 = null;
                        }
                    }
                    if (constraintWidget7 != null) {
                        constraintWidget = constraintWidget7;
                        z5 = z15;
                    } else {
                        z5 = true;
                    }
                    i15 = i10;
                    z = z14;
                    chainHeadArr4 = chainHeadArr3;
                }
                boolean z17 = z;
                i5 = i15;
                chainHeadArr2 = chainHeadArr4;
                if (constraintWidget17 != null) {
                    int i31 = i3 + 1;
                    if (constraintWidget15.mListAnchors[i31].mTarget != null) {
                        ConstraintAnchor constraintAnchor10 = constraintWidget17.mListAnchors[i31];
                        if (constraintWidget17.mListDimensionBehaviors[i] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget17.mResolvedMatchConstraintDefault[i] == 0 && !z17) {
                            ConstraintAnchor constraintAnchor11 = constraintAnchor10.mTarget;
                            if (constraintAnchor11.mOwner == constraintWidgetContainer2) {
                                linearSystem3.addEquality(constraintAnchor10.mSolverVariable, constraintAnchor11.mSolverVariable, -constraintAnchor10.getMargin(), 5);
                            }
                            linearSystem3.addLowerThan(constraintAnchor10.mSolverVariable, constraintWidget15.mListAnchors[i31].mTarget.mSolverVariable, -constraintAnchor10.getMargin(), 6);
                        } else {
                            if (z17) {
                                ConstraintAnchor constraintAnchor12 = constraintAnchor10.mTarget;
                                if (constraintAnchor12.mOwner == constraintWidgetContainer2) {
                                    linearSystem3.addEquality(constraintAnchor10.mSolverVariable, constraintAnchor12.mSolverVariable, -constraintAnchor10.getMargin(), 4);
                                }
                            }
                            linearSystem3.addLowerThan(constraintAnchor10.mSolverVariable, constraintWidget15.mListAnchors[i31].mTarget.mSolverVariable, -constraintAnchor10.getMargin(), 6);
                        }
                    }
                    if (z2) {
                        int i32 = i3 + 1;
                        SolverVariable solverVariable5 = constraintAnchorArr[i32].mSolverVariable;
                        ConstraintAnchor constraintAnchor13 = constraintWidget15.mListAnchors[i32];
                        linearSystem3.addGreaterThan(solverVariable5, constraintAnchor13.mSolverVariable, constraintAnchor13.getMargin(), 8);
                    }
                    ArrayList arrayList4 = chainHead.mWeightedMatchConstraintsWidgets;
                    if (arrayList4 != null && (size = arrayList4.size()) > 1) {
                        if (chainHead.mHasUndefinedWeights && !chainHead.mHasComplexMatchWeights) {
                            f2 = chainHead.mWidgetsMatchCount;
                        }
                        ConstraintWidget constraintWidget19 = null;
                        float f8 = f;
                        int i33 = 0;
                        while (i33 < size) {
                            ConstraintWidget constraintWidget20 = (ConstraintWidget) arrayList4.get(i33);
                            float f9 = constraintWidget20.mWeight[i];
                            ConstraintAnchor[] constraintAnchorArr4 = constraintWidget20.mListAnchors;
                            if (f9 >= f) {
                                arrayList2 = arrayList4;
                                if (f9 != f) {
                                    z6 = false;
                                    linearSystem3.addEquality(constraintAnchorArr4[i3 + 1].mSolverVariable, constraintAnchorArr4[i3].mSolverVariable, 0, 8);
                                    i8 = size;
                                    i9 = i33;
                                    f3 = f;
                                } else {
                                    if (constraintWidget19 != null) {
                                        ConstraintAnchor[] constraintAnchorArr5 = constraintWidget19.mListAnchors;
                                        SolverVariable solverVariable6 = constraintAnchorArr5[i3].mSolverVariable;
                                        int i34 = i3 + 1;
                                        SolverVariable solverVariable7 = constraintAnchorArr5[i34].mSolverVariable;
                                        SolverVariable solverVariable8 = constraintAnchorArr4[i3].mSolverVariable;
                                        i8 = size;
                                        SolverVariable solverVariable9 = constraintAnchorArr4[i34].mSolverVariable;
                                        constraintWidget6 = constraintWidget20;
                                        ArrayRow arrayRowCreateRow = linearSystem3.createRow();
                                        i9 = i33;
                                        float f10 = f;
                                        arrayRowCreateRow.mConstantValue = f10;
                                        f3 = f10;
                                        if (f2 == f10 || f8 == f9) {
                                            f4 = f9;
                                            arrayRowCreateRow.variables.put(solverVariable6, 1.0f);
                                            arrayRowCreateRow.variables.put(solverVariable7, -1.0f);
                                            arrayRowCreateRow.variables.put(solverVariable9, 1.0f);
                                            arrayRowCreateRow.variables.put(solverVariable8, -1.0f);
                                        } else {
                                            if (f8 == f3) {
                                                arrayRowCreateRow.variables.put(solverVariable6, 1.0f);
                                                arrayRowCreateRow.variables.put(solverVariable7, -1.0f);
                                            } else if (f9 == f) {
                                                arrayRowCreateRow.variables.put(solverVariable8, 1.0f);
                                                arrayRowCreateRow.variables.put(solverVariable9, -1.0f);
                                            } else {
                                                float f11 = (f8 / f2) / (f9 / f2);
                                                f4 = f9;
                                                arrayRowCreateRow.variables.put(solverVariable6, 1.0f);
                                                arrayRowCreateRow.variables.put(solverVariable7, -1.0f);
                                                arrayRowCreateRow.variables.put(solverVariable9, f11);
                                                arrayRowCreateRow.variables.put(solverVariable8, -f11);
                                            }
                                            f4 = f9;
                                        }
                                        linearSystem3.addConstraint(arrayRowCreateRow);
                                    } else {
                                        i8 = size;
                                        constraintWidget6 = constraintWidget20;
                                        i9 = i33;
                                        f3 = f;
                                        f4 = f9;
                                    }
                                    f8 = f4;
                                    constraintWidget19 = constraintWidget6;
                                }
                            } else if (chainHead.mHasComplexMatchWeights) {
                                arrayList2 = arrayList4;
                                z6 = false;
                                linearSystem3.addEquality(constraintAnchorArr4[i3 + 1].mSolverVariable, constraintAnchorArr4[i3].mSolverVariable, 0, 4);
                                i8 = size;
                                i9 = i33;
                                f3 = f;
                            } else {
                                f9 = 1.0f;
                                arrayList2 = arrayList4;
                                if (f9 != f) {
                                }
                            }
                            i33 = i9 + 1;
                            arrayList4 = arrayList2;
                            size = i8;
                            f = f3;
                        }
                    }
                    if (constraintWidget16 == null || !(constraintWidget16 == constraintWidget17 || z17)) {
                        constraintWidget2 = constraintWidget17;
                        if (!z3 || constraintWidget16 == null) {
                            if (z4 && constraintWidget16 != null) {
                                int i35 = chainHead.mWidgetsMatchCount;
                                boolean z18 = i35 > 0 && chainHead.mWidgetsCount == i35;
                                ConstraintWidget constraintWidget21 = constraintWidget16;
                                ConstraintWidget constraintWidget22 = constraintWidget21;
                                while (constraintWidget21 != null) {
                                    ConstraintWidget constraintWidget23 = constraintWidget21.mNextChainWidget[i];
                                    while (constraintWidget23 != null && constraintWidget23.mVisibility == 8) {
                                        constraintWidget23 = constraintWidget23.mNextChainWidget[i];
                                    }
                                    if (constraintWidget21 != constraintWidget16 && constraintWidget21 != constraintWidget2 && constraintWidget23 != null) {
                                        if (constraintWidget23 == constraintWidget2) {
                                            constraintWidget23 = null;
                                        }
                                        ConstraintAnchor[] constraintAnchorArr6 = constraintWidget21.mListAnchors;
                                        ConstraintAnchor constraintAnchor14 = constraintAnchorArr6[i3];
                                        SolverVariable solverVariable10 = constraintAnchor14.mSolverVariable;
                                        int i36 = i3 + 1;
                                        SolverVariable solverVariable11 = constraintWidget22.mListAnchors[i36].mSolverVariable;
                                        int margin2 = constraintAnchor14.getMargin();
                                        int margin3 = constraintAnchorArr6[i36].getMargin();
                                        if (constraintWidget23 != null) {
                                            constraintAnchor = constraintWidget23.mListAnchors[i3];
                                            solverVariable2 = constraintAnchor.mSolverVariable;
                                            ConstraintAnchor constraintAnchor15 = constraintAnchor.mTarget;
                                            solverVariable = constraintAnchor15 != null ? constraintAnchor15.mSolverVariable : null;
                                        } else {
                                            ConstraintAnchor constraintAnchor16 = constraintWidget2.mListAnchors[i3];
                                            SolverVariable solverVariable12 = constraintAnchor16 != null ? constraintAnchor16.mSolverVariable : null;
                                            solverVariable = constraintAnchorArr6[i36].mSolverVariable;
                                            constraintAnchor = constraintAnchor16;
                                            solverVariable2 = solverVariable12;
                                        }
                                        if (constraintAnchor != null) {
                                            margin3 += constraintAnchor.getMargin();
                                        }
                                        int margin4 = constraintWidget22.mListAnchors[i36].getMargin() + margin2;
                                        SolverVariable solverVariable13 = solverVariable2;
                                        int i37 = margin3;
                                        SolverVariable solverVariable14 = solverVariable;
                                        int i38 = z18 ? 8 : 4;
                                        if (solverVariable10 == null || solverVariable11 == null || solverVariable13 == null || solverVariable14 == null) {
                                            constraintWidget3 = constraintWidget23;
                                        } else {
                                            constraintWidget3 = constraintWidget23;
                                            linearSystem.addCentering(solverVariable10, solverVariable11, margin4, 0.5f, solverVariable13, solverVariable14, i37, i38);
                                        }
                                        constraintWidget23 = constraintWidget3;
                                    }
                                    if (constraintWidget21.mVisibility != 8) {
                                        constraintWidget22 = constraintWidget21;
                                    }
                                    constraintWidget21 = constraintWidget23;
                                }
                                ConstraintAnchor constraintAnchor17 = constraintWidget16.mListAnchors[i3];
                                ConstraintAnchor constraintAnchor18 = constraintWidget9.mListAnchors[i3].mTarget;
                                int i39 = i3 + 1;
                                ConstraintAnchor constraintAnchor19 = constraintWidget2.mListAnchors[i39];
                                ConstraintAnchor constraintAnchor20 = constraintWidget15.mListAnchors[i39].mTarget;
                                if (constraintAnchor18 == null) {
                                    linearSystem2 = linearSystem;
                                    if (constraintAnchor20 != null && constraintWidget16 != constraintWidget2) {
                                        linearSystem2.addEquality(constraintAnchor19.mSolverVariable, constraintAnchor20.mSolverVariable, -constraintAnchor19.getMargin(), 5);
                                    }
                                } else {
                                    if (constraintWidget16 != constraintWidget2) {
                                        linearSystem.addEquality(constraintAnchor17.mSolverVariable, constraintAnchor18.mSolverVariable, constraintAnchor17.getMargin(), 5);
                                    } else {
                                        if (constraintAnchor20 != null) {
                                            linearSystem2 = linearSystem;
                                            linearSystem2.addCentering(constraintAnchor17.mSolverVariable, constraintAnchor18.mSolverVariable, constraintAnchor17.getMargin(), 0.5f, constraintAnchor19.mSolverVariable, constraintAnchor20.mSolverVariable, constraintAnchor19.getMargin(), 5);
                                        }
                                        if (constraintAnchor20 != null) {
                                            linearSystem2.addEquality(constraintAnchor19.mSolverVariable, constraintAnchor20.mSolverVariable, -constraintAnchor19.getMargin(), 5);
                                        }
                                    }
                                    linearSystem2 = linearSystem;
                                    if (constraintAnchor20 != null) {
                                    }
                                }
                            }
                            if ((z3 || z4) && constraintWidget16 != null && constraintWidget16 != constraintWidget2) {
                                ConstraintAnchor[] constraintAnchorArr7 = constraintWidget16.mListAnchors;
                                ConstraintAnchor constraintAnchor21 = constraintAnchorArr7[i3];
                                if (constraintWidget2 == null) {
                                    constraintWidget2 = constraintWidget16;
                                }
                                int i40 = i3 + 1;
                                ConstraintAnchor[] constraintAnchorArr8 = constraintWidget2.mListAnchors;
                                ConstraintAnchor constraintAnchor22 = constraintAnchorArr8[i40];
                                ConstraintAnchor constraintAnchor23 = constraintAnchor21.mTarget;
                                solverVariable4 = constraintAnchor23 != null ? constraintAnchor23.mSolverVariable : null;
                                ConstraintAnchor constraintAnchor24 = constraintAnchor22.mTarget;
                                SolverVariable solverVariable15 = constraintAnchor24 != null ? constraintAnchor24.mSolverVariable : null;
                                if (constraintWidget15 != constraintWidget2) {
                                    ConstraintAnchor constraintAnchor25 = constraintWidget15.mListAnchors[i40].mTarget;
                                    solverVariable15 = constraintAnchor25 != null ? constraintAnchor25.mSolverVariable : null;
                                }
                                if (constraintWidget16 == constraintWidget2) {
                                    constraintAnchor22 = constraintAnchorArr7[i40];
                                }
                                if (solverVariable4 != null && solverVariable15 != null) {
                                    linearSystem2.addCentering(constraintAnchor21.mSolverVariable, solverVariable4, constraintAnchor21.getMargin(), 0.5f, solverVariable15, constraintAnchor22.mSolverVariable, constraintAnchorArr8[i40].getMargin(), 5);
                                }
                            }
                        } else {
                            int i41 = chainHead.mWidgetsMatchCount;
                            boolean z19 = i41 > 0 && chainHead.mWidgetsCount == i41;
                            ConstraintWidget constraintWidget24 = constraintWidget16;
                            ConstraintWidget constraintWidget25 = constraintWidget24;
                            while (constraintWidget24 != null) {
                                ConstraintWidget constraintWidget26 = constraintWidget24.mNextChainWidget[i];
                                while (true) {
                                    if (constraintWidget26 == null) {
                                        i6 = 8;
                                        break;
                                    }
                                    i6 = 8;
                                    if (constraintWidget26.mVisibility != 8) {
                                        break;
                                    } else {
                                        constraintWidget26 = constraintWidget26.mNextChainWidget[i];
                                    }
                                }
                                if (constraintWidget26 != null || constraintWidget24 == constraintWidget2) {
                                    ConstraintAnchor[] constraintAnchorArr9 = constraintWidget24.mListAnchors;
                                    ConstraintAnchor constraintAnchor26 = constraintAnchorArr9[i3];
                                    SolverVariable solverVariable16 = constraintAnchor26.mSolverVariable;
                                    ConstraintAnchor constraintAnchor27 = constraintAnchor26.mTarget;
                                    SolverVariable solverVariable17 = constraintAnchor27 != null ? constraintAnchor27.mSolverVariable : null;
                                    if (constraintWidget25 != constraintWidget24) {
                                        solverVariable17 = constraintWidget25.mListAnchors[i3 + 1].mSolverVariable;
                                    } else if (constraintWidget24 == constraintWidget16) {
                                        ConstraintAnchor constraintAnchor28 = constraintWidget9.mListAnchors[i3].mTarget;
                                        solverVariable17 = constraintAnchor28 != null ? constraintAnchor28.mSolverVariable : null;
                                    }
                                    int margin5 = constraintAnchor26.getMargin();
                                    int i42 = i3 + 1;
                                    int margin6 = constraintAnchorArr9[i42].getMargin();
                                    if (constraintWidget26 != null) {
                                        constraintAnchor2 = constraintWidget26.mListAnchors[i3];
                                        solverVariable3 = constraintAnchor2.mSolverVariable;
                                    } else {
                                        constraintAnchor2 = constraintWidget15.mListAnchors[i42].mTarget;
                                        solverVariable3 = constraintAnchor2 != null ? constraintAnchor2.mSolverVariable : null;
                                    }
                                    SolverVariable solverVariable18 = constraintAnchorArr9[i42].mSolverVariable;
                                    if (constraintAnchor2 != null) {
                                        margin6 += constraintAnchor2.getMargin();
                                    }
                                    int margin7 = constraintWidget25.mListAnchors[i42].getMargin() + margin5;
                                    if (solverVariable16 == null || solverVariable17 == null || solverVariable3 == null || solverVariable18 == null) {
                                        constraintWidget4 = constraintWidget9;
                                        constraintWidget5 = constraintWidget25;
                                        i7 = 8;
                                    } else {
                                        if (constraintWidget24 == constraintWidget16) {
                                            margin7 = constraintWidget16.mListAnchors[i3].getMargin();
                                        }
                                        if (constraintWidget24 == constraintWidget2) {
                                            margin6 = constraintWidget2.mListAnchors[i42].getMargin();
                                        }
                                        constraintWidget4 = constraintWidget9;
                                        constraintWidget5 = constraintWidget25;
                                        i7 = 8;
                                        linearSystem.addCentering(solverVariable16, solverVariable17, margin7, 0.5f, solverVariable3, solverVariable18, margin6, z19 ? 8 : 5);
                                    }
                                } else {
                                    constraintWidget4 = constraintWidget9;
                                    constraintWidget5 = constraintWidget25;
                                    i7 = i6;
                                }
                                if (constraintWidget24.mVisibility != i7) {
                                    constraintWidget5 = constraintWidget24;
                                }
                                constraintWidget24 = constraintWidget26;
                                constraintWidget25 = constraintWidget5;
                                constraintWidget9 = constraintWidget4;
                            }
                        }
                    } else {
                        ConstraintAnchor constraintAnchor29 = constraintWidget9.mListAnchors[i3];
                        int i43 = i3 + 1;
                        ConstraintAnchor constraintAnchor30 = constraintWidget15.mListAnchors[i43];
                        ConstraintAnchor constraintAnchor31 = constraintAnchor29.mTarget;
                        SolverVariable solverVariable19 = constraintAnchor31 != null ? constraintAnchor31.mSolverVariable : null;
                        ConstraintAnchor constraintAnchor32 = constraintAnchor30.mTarget;
                        SolverVariable solverVariable20 = constraintAnchor32 != null ? constraintAnchor32.mSolverVariable : null;
                        ConstraintAnchor constraintAnchor33 = constraintWidget16.mListAnchors[i3];
                        if (constraintWidget17 != null) {
                            constraintAnchor30 = constraintWidget17.mListAnchors[i43];
                        }
                        if (solverVariable19 == null || solverVariable20 == null) {
                            constraintWidget2 = constraintWidget17;
                        } else {
                            float f12 = i == 0 ? constraintWidget18.mHorizontalBiasPercent : constraintWidget18.mVerticalBiasPercent;
                            int margin8 = constraintAnchor33.getMargin();
                            int margin9 = constraintAnchor30.getMargin();
                            SolverVariable solverVariable21 = constraintAnchor33.mSolverVariable;
                            SolverVariable solverVariable22 = constraintAnchor30.mSolverVariable;
                            SolverVariable solverVariable23 = solverVariable19;
                            constraintWidget2 = constraintWidget17;
                            linearSystem3.addCentering(solverVariable21, solverVariable23, margin8, f12, solverVariable20, solverVariable22, margin9, 7);
                        }
                    }
                    linearSystem2 = linearSystem;
                    if (z3) {
                        ConstraintAnchor[] constraintAnchorArr72 = constraintWidget16.mListAnchors;
                        ConstraintAnchor constraintAnchor212 = constraintAnchorArr72[i3];
                        if (constraintWidget2 == null) {
                        }
                        int i402 = i3 + 1;
                        ConstraintAnchor[] constraintAnchorArr82 = constraintWidget2.mListAnchors;
                        ConstraintAnchor constraintAnchor222 = constraintAnchorArr82[i402];
                        ConstraintAnchor constraintAnchor232 = constraintAnchor212.mTarget;
                        if (constraintAnchor232 != null) {
                        }
                        ConstraintAnchor constraintAnchor242 = constraintAnchor222.mTarget;
                        if (constraintAnchor242 != null) {
                        }
                        if (constraintWidget15 != constraintWidget2) {
                        }
                        if (constraintWidget16 == constraintWidget2) {
                        }
                        if (solverVariable4 != null) {
                        }
                    } else {
                        ConstraintAnchor[] constraintAnchorArr722 = constraintWidget16.mListAnchors;
                        ConstraintAnchor constraintAnchor2122 = constraintAnchorArr722[i3];
                        if (constraintWidget2 == null) {
                        }
                        int i4022 = i3 + 1;
                        ConstraintAnchor[] constraintAnchorArr822 = constraintWidget2.mListAnchors;
                        ConstraintAnchor constraintAnchor2222 = constraintAnchorArr822[i4022];
                        ConstraintAnchor constraintAnchor2322 = constraintAnchor2122.mTarget;
                        if (constraintAnchor2322 != null) {
                        }
                        ConstraintAnchor constraintAnchor2422 = constraintAnchor2222.mTarget;
                        if (constraintAnchor2422 != null) {
                        }
                        if (constraintWidget15 != constraintWidget2) {
                        }
                        if (constraintWidget16 == constraintWidget2) {
                        }
                        if (solverVariable4 != null) {
                        }
                    }
                }
            } else {
                i5 = i15;
                chainHeadArr2 = chainHeadArr4;
            }
            i16 = i4 + 1;
            constraintWidgetContainer2 = constraintWidgetContainer;
            linearSystem3 = linearSystem;
            arrayList3 = arrayList;
            i15 = i5;
            chainHeadArr4 = chainHeadArr2;
        }
    }
}
