package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Chain {
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0280, code lost:
    
        if (r2.mOwner == r8) goto L178;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0100, code lost:
    
        if (r4.mOwner == r13) goto L65;
     */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0324  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x033e  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x0440 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:248:0x06b9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:254:0x06c5  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x06d0  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x06d9  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x06e0  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x06f2  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x06f6 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0712 A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:274:0x06ef  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x06dc  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x06d3  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x04a3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0585  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x0587  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x0599 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:358:0x05ac  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x067d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ArrayList arrayList, int i) {
        int i2;
        ChainHead[] chainHeadArr;
        int i3;
        int i4;
        int i5;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        ConstraintAnchor[] constraintAnchorArr;
        ChainHead[] chainHeadArr2;
        ArrayList arrayList2;
        int i6;
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        ConstraintAnchor constraintAnchor3;
        int i7;
        int i8;
        ConstraintWidget constraintWidget3;
        SolverVariable solverVariable;
        ConstraintAnchor constraintAnchor4;
        SolverVariable solverVariable2;
        ConstraintWidget constraintWidget4;
        ConstraintAnchor constraintAnchor5;
        SolverVariable solverVariable3;
        ConstraintWidget constraintWidget5;
        ConstraintWidget constraintWidget6;
        ConstraintWidget constraintWidget7;
        ConstraintWidget constraintWidget8;
        int i9;
        ConstraintAnchor constraintAnchor6;
        int i10;
        ConstraintAnchor constraintAnchor7;
        SolverVariable solverVariable4;
        SolverVariable solverVariable5;
        int size;
        int i11;
        float f;
        ArrayList arrayList3;
        int i12;
        ConstraintWidget constraintWidget9;
        boolean z8;
        ChainHead[] chainHeadArr3;
        boolean z9;
        ConstraintWidget constraintWidget10;
        ChainHead chainHead;
        ConstraintWidget constraintWidget11;
        ConstraintWidget constraintWidget12;
        int i13;
        boolean z10;
        int i14;
        int i15;
        ConstraintWidget constraintWidget13;
        ConstraintAnchor constraintAnchor8;
        ConstraintWidget constraintWidget14;
        ConstraintWidgetContainer constraintWidgetContainer2 = constraintWidgetContainer;
        ArrayList arrayList4 = arrayList;
        if (i == 0) {
            i2 = constraintWidgetContainer2.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer2.mHorizontalChainsArray;
            i3 = 0;
        } else {
            i2 = constraintWidgetContainer2.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer2.mVerticalChainsArray;
            i3 = 2;
        }
        int i16 = 0;
        while (i16 < i2) {
            ChainHead chainHead2 = chainHeadArr[i16];
            boolean z11 = chainHead2.mDefined;
            int i17 = 8;
            int i18 = 1;
            ConstraintWidget constraintWidget15 = chainHead2.mFirst;
            if (z11) {
                i4 = i16;
                i5 = i2;
                z = true;
            } else {
                int i19 = chainHead2.mOrientation;
                int i20 = i19 * 2;
                ConstraintWidget constraintWidget16 = constraintWidget15;
                ConstraintWidget constraintWidget17 = constraintWidget16;
                boolean z12 = false;
                while (!z12) {
                    chainHead2.mWidgetsCount += i18;
                    constraintWidget16.mNextChainWidget[i19] = null;
                    constraintWidget16.mListNextMatchConstraintsWidget[i19] = null;
                    int i21 = constraintWidget16.mVisibility;
                    ConstraintAnchor[] constraintAnchorArr2 = constraintWidget16.mListAnchors;
                    if (i21 != i17) {
                        constraintWidget16.getDimensionBehaviour(i19);
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                        constraintAnchorArr2[i20].getMargin();
                        int i22 = i20 + 1;
                        constraintAnchorArr2[i22].getMargin();
                        constraintAnchorArr2[i20].getMargin();
                        constraintAnchorArr2[i22].getMargin();
                        if (chainHead2.mFirstVisibleWidget == null) {
                            chainHead2.mFirstVisibleWidget = constraintWidget16;
                        }
                        chainHead2.mLastVisibleWidget = constraintWidget16;
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget16.mListDimensionBehaviors[i19];
                        if (dimensionBehaviour2 == dimensionBehaviour) {
                            int i23 = constraintWidget16.mResolvedMatchConstraintDefault[i19];
                            z10 = z12;
                            if (i23 == 0 || i23 == 3 || i23 == 2) {
                                chainHead2.mWidgetsMatchCount++;
                                float f2 = constraintWidget16.mWeight[i19];
                                if (f2 > 0.0f) {
                                    i15 = i16;
                                    chainHead2.mTotalWeight += f2;
                                } else {
                                    i15 = i16;
                                }
                                i14 = i2;
                                if (constraintWidget16.mVisibility != 8 && dimensionBehaviour2 == dimensionBehaviour && (i23 == 0 || i23 == 3)) {
                                    if (f2 < 0.0f) {
                                        chainHead2.mHasUndefinedWeights = true;
                                    } else {
                                        chainHead2.mHasDefinedWeights = true;
                                    }
                                    if (chainHead2.mWeightedMatchConstraintsWidgets == null) {
                                        chainHead2.mWeightedMatchConstraintsWidgets = new ArrayList();
                                    }
                                    chainHead2.mWeightedMatchConstraintsWidgets.add(constraintWidget16);
                                }
                                if (chainHead2.mFirstMatchConstraintWidget == null) {
                                    chainHead2.mFirstMatchConstraintWidget = constraintWidget16;
                                }
                                ConstraintWidget constraintWidget18 = chainHead2.mLastMatchConstraintWidget;
                                if (constraintWidget18 != null) {
                                    constraintWidget18.mListNextMatchConstraintsWidget[i19] = constraintWidget16;
                                }
                                chainHead2.mLastMatchConstraintWidget = constraintWidget16;
                                constraintWidget13 = constraintWidget17;
                                if (constraintWidget13 != constraintWidget16) {
                                    constraintWidget13.mNextChainWidget[i19] = constraintWidget16;
                                }
                                constraintAnchor8 = constraintAnchorArr2[i20 + 1].mTarget;
                                if (constraintAnchor8 != null) {
                                    constraintWidget14 = constraintAnchor8.mOwner;
                                    ConstraintAnchor constraintAnchor9 = constraintWidget14.mListAnchors[i20].mTarget;
                                    if (constraintAnchor9 != null) {
                                    }
                                }
                                constraintWidget14 = null;
                                if (constraintWidget14 != null) {
                                    z12 = z10;
                                } else {
                                    constraintWidget14 = constraintWidget16;
                                    z12 = true;
                                }
                                constraintWidget17 = constraintWidget16;
                                i16 = i15;
                                i2 = i14;
                                i18 = 1;
                                i17 = 8;
                                constraintWidget16 = constraintWidget14;
                            }
                            i15 = i16;
                            i14 = i2;
                            constraintWidget13 = constraintWidget17;
                            if (constraintWidget13 != constraintWidget16) {
                            }
                            constraintAnchor8 = constraintAnchorArr2[i20 + 1].mTarget;
                            if (constraintAnchor8 != null) {
                            }
                            constraintWidget14 = null;
                            if (constraintWidget14 != null) {
                            }
                            constraintWidget17 = constraintWidget16;
                            i16 = i15;
                            i2 = i14;
                            i18 = 1;
                            i17 = 8;
                            constraintWidget16 = constraintWidget14;
                        }
                    }
                    z10 = z12;
                    i15 = i16;
                    i14 = i2;
                    constraintWidget13 = constraintWidget17;
                    if (constraintWidget13 != constraintWidget16) {
                    }
                    constraintAnchor8 = constraintAnchorArr2[i20 + 1].mTarget;
                    if (constraintAnchor8 != null) {
                    }
                    constraintWidget14 = null;
                    if (constraintWidget14 != null) {
                    }
                    constraintWidget17 = constraintWidget16;
                    i16 = i15;
                    i2 = i14;
                    i18 = 1;
                    i17 = 8;
                    constraintWidget16 = constraintWidget14;
                }
                i4 = i16;
                i5 = i2;
                ConstraintWidget constraintWidget19 = chainHead2.mFirstVisibleWidget;
                if (constraintWidget19 != null) {
                    constraintWidget19.mListAnchors[i20].getMargin();
                }
                ConstraintWidget constraintWidget20 = chainHead2.mLastVisibleWidget;
                if (constraintWidget20 != null) {
                    constraintWidget20.mListAnchors[i20 + 1].getMargin();
                }
                chainHead2.mLast = constraintWidget16;
                if (i19 == 0 && chainHead2.mIsRtl) {
                    chainHead2.mHead = constraintWidget16;
                } else {
                    chainHead2.mHead = constraintWidget15;
                }
                chainHead2.mHasComplexMatchWeights = chainHead2.mHasDefinedWeights && chainHead2.mHasUndefinedWeights;
                z = true;
            }
            chainHead2.mDefined = z;
            if (arrayList4 == null || arrayList4.contains(constraintWidget15)) {
                ConstraintWidget constraintWidget21 = chainHead2.mLast;
                ConstraintWidget constraintWidget22 = chainHead2.mFirstVisibleWidget;
                ConstraintWidget constraintWidget23 = chainHead2.mLastVisibleWidget;
                ConstraintWidget constraintWidget24 = chainHead2.mHead;
                float f3 = chainHead2.mTotalWeight;
                boolean z13 = constraintWidgetContainer2.mListDimensionBehaviors[i] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (i == 0) {
                    int i24 = constraintWidget24.mHorizontalChainStyle;
                    boolean z14 = i24 == 0;
                    z3 = i24 == 1;
                    if (i24 == 2) {
                        z4 = z14;
                        z5 = z4;
                        z6 = z3;
                        z7 = true;
                    } else {
                        z2 = z14;
                        z5 = z2;
                        z6 = z3;
                        z7 = false;
                    }
                } else {
                    int i25 = constraintWidget24.mVerticalChainStyle;
                    boolean z15 = i25 == 0;
                    boolean z16 = i25 == 1;
                    if (i25 == 2) {
                        z4 = z15;
                        z3 = z16;
                        z5 = z4;
                        z6 = z3;
                        z7 = true;
                    } else {
                        z2 = z15;
                        z3 = z16;
                        z5 = z2;
                        z6 = z3;
                        z7 = false;
                    }
                }
                ConstraintWidget constraintWidget25 = constraintWidget15;
                boolean z17 = false;
                while (true) {
                    constraintAnchorArr = constraintWidgetContainer2.mListAnchors;
                    if (z17) {
                        break;
                    }
                    float f4 = f3;
                    ConstraintAnchor constraintAnchor10 = constraintWidget25.mListAnchors[i3];
                    int i26 = z7 ? 1 : 4;
                    int margin = constraintAnchor10.getMargin();
                    boolean z18 = z17;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = constraintWidget25.mListDimensionBehaviors[i];
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    if (dimensionBehaviour3 == dimensionBehaviour4 && constraintWidget25.mResolvedMatchConstraintDefault[i] == 0) {
                        chainHeadArr3 = chainHeadArr;
                        z9 = true;
                    } else {
                        chainHeadArr3 = chainHeadArr;
                        z9 = false;
                    }
                    ConstraintAnchor constraintAnchor11 = constraintAnchor10.mTarget;
                    if (constraintAnchor11 != null && constraintWidget25 != constraintWidget15) {
                        margin = constraintAnchor11.getMargin() + margin;
                    }
                    int i27 = margin;
                    if (!z7 || constraintWidget25 == constraintWidget15 || constraintWidget25 == constraintWidget22) {
                        constraintWidget10 = constraintWidget24;
                    } else {
                        constraintWidget10 = constraintWidget24;
                        i26 = 8;
                    }
                    ConstraintAnchor constraintAnchor12 = constraintAnchor10.mTarget;
                    if (constraintAnchor12 != null) {
                        if (constraintWidget25 == constraintWidget22) {
                            constraintWidget11 = constraintWidget15;
                            chainHead = chainHead2;
                            linearSystem.addGreaterThan(constraintAnchor10.mSolverVariable, constraintAnchor12.mSolverVariable, i27, 6);
                        } else {
                            chainHead = chainHead2;
                            constraintWidget11 = constraintWidget15;
                            linearSystem.addGreaterThan(constraintAnchor10.mSolverVariable, constraintAnchor12.mSolverVariable, i27, 8);
                        }
                        if (z9 && !z7) {
                            i26 = 5;
                        }
                        linearSystem.addEquality(constraintAnchor10.mSolverVariable, constraintAnchor10.mTarget.mSolverVariable, i27, (constraintWidget25 == constraintWidget22 && z7 && constraintWidget25.mIsInBarrier[i]) ? 5 : i26);
                    } else {
                        chainHead = chainHead2;
                        constraintWidget11 = constraintWidget15;
                    }
                    ConstraintAnchor[] constraintAnchorArr3 = constraintWidget25.mListAnchors;
                    if (z13) {
                        if (constraintWidget25.mVisibility == 8 || constraintWidget25.mListDimensionBehaviors[i] != dimensionBehaviour4) {
                            i13 = 0;
                        } else {
                            i13 = 0;
                            linearSystem.addGreaterThan(constraintAnchorArr3[i3 + 1].mSolverVariable, constraintAnchorArr3[i3].mSolverVariable, 0, 5);
                        }
                        linearSystem.addGreaterThan(constraintAnchorArr3[i3].mSolverVariable, constraintAnchorArr[i3].mSolverVariable, i13, 8);
                    }
                    ConstraintAnchor constraintAnchor13 = constraintAnchorArr3[i3 + 1].mTarget;
                    if (constraintAnchor13 != null) {
                        constraintWidget12 = constraintAnchor13.mOwner;
                        ConstraintAnchor constraintAnchor14 = constraintWidget12.mListAnchors[i3].mTarget;
                        if (constraintAnchor14 != null) {
                        }
                    }
                    constraintWidget12 = null;
                    if (constraintWidget12 != null) {
                        constraintWidget25 = constraintWidget12;
                        z17 = z18;
                    } else {
                        z17 = true;
                    }
                    f3 = f4;
                    constraintWidget24 = constraintWidget10;
                    chainHeadArr = chainHeadArr3;
                    constraintWidget15 = constraintWidget11;
                    chainHead2 = chainHead;
                }
                ChainHead chainHead3 = chainHead2;
                ConstraintWidget constraintWidget26 = constraintWidget24;
                float f5 = f3;
                ConstraintWidget constraintWidget27 = constraintWidget15;
                chainHeadArr2 = chainHeadArr;
                if (constraintWidget23 != null) {
                    int i28 = i3 + 1;
                    if (constraintWidget21.mListAnchors[i28].mTarget != null) {
                        ConstraintAnchor constraintAnchor15 = constraintWidget23.mListAnchors[i28];
                        if ((constraintWidget23.mListDimensionBehaviors[i] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget23.mResolvedMatchConstraintDefault[i] == 0) && !z7) {
                            ConstraintAnchor constraintAnchor16 = constraintAnchor15.mTarget;
                            if (constraintAnchor16.mOwner == constraintWidgetContainer2) {
                                linearSystem.addEquality(constraintAnchor15.mSolverVariable, constraintAnchor16.mSolverVariable, -constraintAnchor15.getMargin(), 5);
                                linearSystem.addLowerThan(constraintAnchor15.mSolverVariable, constraintWidget21.mListAnchors[i28].mTarget.mSolverVariable, -constraintAnchor15.getMargin(), 6);
                                if (z13) {
                                    int i29 = i3 + 1;
                                    SolverVariable solverVariable6 = constraintAnchorArr[i29].mSolverVariable;
                                    ConstraintAnchor constraintAnchor17 = constraintWidget21.mListAnchors[i29];
                                    linearSystem.addGreaterThan(solverVariable6, constraintAnchor17.mSolverVariable, constraintAnchor17.getMargin(), 8);
                                }
                                arrayList2 = chainHead3.mWeightedMatchConstraintsWidgets;
                                if (arrayList2 != null && (size = arrayList2.size()) > 1) {
                                    if (chainHead3.mHasUndefinedWeights && !chainHead3.mHasComplexMatchWeights) {
                                        f5 = chainHead3.mWidgetsMatchCount;
                                    }
                                    ConstraintWidget constraintWidget28 = null;
                                    float f6 = 0.0f;
                                    i11 = 0;
                                    while (i11 < size) {
                                        ConstraintWidget constraintWidget29 = (ConstraintWidget) arrayList2.get(i11);
                                        float f7 = constraintWidget29.mWeight[i];
                                        ConstraintAnchor[] constraintAnchorArr4 = constraintWidget29.mListAnchors;
                                        if (f7 >= 0.0f) {
                                            f = 0.0f;
                                        } else if (chainHead3.mHasComplexMatchWeights) {
                                            linearSystem.addEquality(constraintAnchorArr4[i3 + 1].mSolverVariable, constraintAnchorArr4[i3].mSolverVariable, 0, 4);
                                            z8 = false;
                                            arrayList3 = arrayList2;
                                            i12 = size;
                                            i11++;
                                            arrayList2 = arrayList3;
                                            size = i12;
                                        } else {
                                            f7 = 1.0f;
                                            f = 0.0f;
                                        }
                                        if (f7 == f) {
                                            z8 = false;
                                            linearSystem.addEquality(constraintAnchorArr4[i3 + 1].mSolverVariable, constraintAnchorArr4[i3].mSolverVariable, 0, 8);
                                            arrayList3 = arrayList2;
                                            i12 = size;
                                            i11++;
                                            arrayList2 = arrayList3;
                                            size = i12;
                                        } else {
                                            if (constraintWidget28 != null) {
                                                ConstraintAnchor[] constraintAnchorArr5 = constraintWidget28.mListAnchors;
                                                SolverVariable solverVariable7 = constraintAnchorArr5[i3].mSolverVariable;
                                                int i30 = i3 + 1;
                                                SolverVariable solverVariable8 = constraintAnchorArr5[i30].mSolverVariable;
                                                SolverVariable solverVariable9 = constraintAnchorArr4[i3].mSolverVariable;
                                                SolverVariable solverVariable10 = constraintAnchorArr4[i30].mSolverVariable;
                                                arrayList3 = arrayList2;
                                                ArrayRow createRow = linearSystem.createRow();
                                                i12 = size;
                                                createRow.constantValue = 0.0f;
                                                if (f5 == 0.0f || f6 == f7) {
                                                    constraintWidget9 = constraintWidget29;
                                                    createRow.variables.put(solverVariable7, 1.0f);
                                                    createRow.variables.put(solverVariable8, -1.0f);
                                                    createRow.variables.put(solverVariable10, 1.0f);
                                                    createRow.variables.put(solverVariable9, -1.0f);
                                                } else {
                                                    if (f6 == 0.0f) {
                                                        createRow.variables.put(solverVariable7, 1.0f);
                                                        createRow.variables.put(solverVariable8, -1.0f);
                                                    } else if (f7 == f) {
                                                        createRow.variables.put(solverVariable9, 1.0f);
                                                        createRow.variables.put(solverVariable10, -1.0f);
                                                    } else {
                                                        float f8 = (f6 / f5) / (f7 / f5);
                                                        constraintWidget9 = constraintWidget29;
                                                        createRow.variables.put(solverVariable7, 1.0f);
                                                        createRow.variables.put(solverVariable8, -1.0f);
                                                        createRow.variables.put(solverVariable10, f8);
                                                        createRow.variables.put(solverVariable9, -f8);
                                                    }
                                                    constraintWidget9 = constraintWidget29;
                                                }
                                                linearSystem.addConstraint(createRow);
                                            } else {
                                                arrayList3 = arrayList2;
                                                i12 = size;
                                                constraintWidget9 = constraintWidget29;
                                            }
                                            f6 = f7;
                                            constraintWidget28 = constraintWidget9;
                                            i11++;
                                            arrayList2 = arrayList3;
                                            size = i12;
                                        }
                                    }
                                }
                                int i31 = 4;
                                if (constraintWidget22 == null && (constraintWidget22 == constraintWidget23 || z7)) {
                                    ConstraintAnchor constraintAnchor18 = constraintWidget27.mListAnchors[i3];
                                    int i32 = i3 + 1;
                                    ConstraintAnchor constraintAnchor19 = constraintWidget21.mListAnchors[i32];
                                    ConstraintAnchor constraintAnchor20 = constraintAnchor18.mTarget;
                                    SolverVariable solverVariable11 = constraintAnchor20 != null ? constraintAnchor20.mSolverVariable : null;
                                    ConstraintAnchor constraintAnchor21 = constraintAnchor19.mTarget;
                                    SolverVariable solverVariable12 = constraintAnchor21 != null ? constraintAnchor21.mSolverVariable : null;
                                    ConstraintAnchor constraintAnchor22 = constraintWidget22.mListAnchors[i3];
                                    if (constraintWidget23 != null) {
                                        constraintAnchor19 = constraintWidget23.mListAnchors[i32];
                                    }
                                    if (solverVariable11 == null || solverVariable12 == null) {
                                        i6 = i4;
                                        i4 = i6;
                                        if (!z5) {
                                        }
                                        ConstraintAnchor[] constraintAnchorArr6 = constraintWidget22.mListAnchors;
                                        constraintAnchor6 = constraintAnchorArr6[i3];
                                        if (constraintWidget23 == null) {
                                        }
                                        i10 = i3 + 1;
                                        constraintAnchor7 = constraintWidget23.mListAnchors[i10];
                                        ConstraintAnchor constraintAnchor23 = constraintAnchor6.mTarget;
                                        if (constraintAnchor23 == null) {
                                        }
                                        ConstraintAnchor constraintAnchor24 = constraintAnchor7.mTarget;
                                        if (constraintAnchor24 == null) {
                                        }
                                        if (constraintWidget21 == constraintWidget23) {
                                        }
                                        if (constraintWidget22 == constraintWidget23) {
                                        }
                                        if (solverVariable4 != null) {
                                        }
                                    } else {
                                        linearSystem.addCentering(constraintAnchor22.mSolverVariable, solverVariable11, constraintAnchor22.getMargin(), i == 0 ? constraintWidget26.mHorizontalBiasPercent : constraintWidget26.mVerticalBiasPercent, solverVariable12, constraintAnchor19.mSolverVariable, constraintAnchor19.getMargin(), 7);
                                        if (!z5) {
                                        }
                                        ConstraintAnchor[] constraintAnchorArr62 = constraintWidget22.mListAnchors;
                                        constraintAnchor6 = constraintAnchorArr62[i3];
                                        if (constraintWidget23 == null) {
                                        }
                                        i10 = i3 + 1;
                                        constraintAnchor7 = constraintWidget23.mListAnchors[i10];
                                        ConstraintAnchor constraintAnchor232 = constraintAnchor6.mTarget;
                                        if (constraintAnchor232 == null) {
                                        }
                                        ConstraintAnchor constraintAnchor242 = constraintAnchor7.mTarget;
                                        if (constraintAnchor242 == null) {
                                        }
                                        if (constraintWidget21 == constraintWidget23) {
                                        }
                                        if (constraintWidget22 == constraintWidget23) {
                                        }
                                        if (solverVariable4 != null) {
                                        }
                                    }
                                } else {
                                    i6 = i4;
                                    constraintWidget = constraintWidget27;
                                    if (z5 || constraintWidget22 == null) {
                                        i4 = i6;
                                        int i33 = 8;
                                        if (z6 && constraintWidget22 != null) {
                                            int i34 = chainHead3.mWidgetsMatchCount;
                                            boolean z19 = i34 <= 0 && chainHead3.mWidgetsCount == i34;
                                            constraintWidget2 = constraintWidget22;
                                            ConstraintWidget constraintWidget30 = constraintWidget2;
                                            while (constraintWidget2 != null) {
                                                ConstraintWidget constraintWidget31 = constraintWidget2.mNextChainWidget[i];
                                                while (constraintWidget31 != null && constraintWidget31.mVisibility == i33) {
                                                    constraintWidget31 = constraintWidget31.mNextChainWidget[i];
                                                }
                                                if (constraintWidget2 == constraintWidget22 || constraintWidget2 == constraintWidget23 || constraintWidget31 == null) {
                                                    i7 = i33;
                                                    i8 = i31;
                                                    constraintWidget3 = constraintWidget30;
                                                } else {
                                                    ConstraintWidget constraintWidget32 = constraintWidget31 == constraintWidget23 ? null : constraintWidget31;
                                                    ConstraintAnchor[] constraintAnchorArr7 = constraintWidget2.mListAnchors;
                                                    ConstraintAnchor constraintAnchor25 = constraintAnchorArr7[i3];
                                                    SolverVariable solverVariable13 = constraintAnchor25.mSolverVariable;
                                                    int i35 = i3 + 1;
                                                    SolverVariable solverVariable14 = constraintWidget30.mListAnchors[i35].mSolverVariable;
                                                    int margin2 = constraintAnchor25.getMargin();
                                                    int margin3 = constraintAnchorArr7[i35].getMargin();
                                                    if (constraintWidget32 != null) {
                                                        constraintAnchor4 = constraintWidget32.mListAnchors[i3];
                                                        solverVariable2 = constraintAnchor4.mSolverVariable;
                                                        ConstraintAnchor constraintAnchor26 = constraintAnchor4.mTarget;
                                                        solverVariable = constraintAnchor26 != null ? constraintAnchor26.mSolverVariable : null;
                                                    } else {
                                                        ConstraintAnchor constraintAnchor27 = constraintWidget23.mListAnchors[i3];
                                                        SolverVariable solverVariable15 = constraintAnchor27 != null ? constraintAnchor27.mSolverVariable : null;
                                                        solverVariable = constraintAnchorArr7[i35].mSolverVariable;
                                                        constraintAnchor4 = constraintAnchor27;
                                                        solverVariable2 = solverVariable15;
                                                    }
                                                    int margin4 = constraintAnchor4 != null ? constraintAnchor4.getMargin() + margin3 : margin3;
                                                    int margin5 = constraintWidget30.mListAnchors[i35].getMargin() + margin2;
                                                    int i36 = z19 ? 8 : i31;
                                                    if (solverVariable13 == null || solverVariable14 == null || solverVariable2 == null || solverVariable == null) {
                                                        constraintWidget4 = constraintWidget32;
                                                        i8 = i31;
                                                        i7 = 8;
                                                        constraintWidget3 = constraintWidget30;
                                                    } else {
                                                        constraintWidget4 = constraintWidget32;
                                                        SolverVariable solverVariable16 = solverVariable;
                                                        ConstraintWidget constraintWidget33 = constraintWidget30;
                                                        int i37 = margin4;
                                                        i8 = i31;
                                                        constraintWidget3 = constraintWidget33;
                                                        i7 = 8;
                                                        linearSystem.addCentering(solverVariable13, solverVariable14, margin5, 0.5f, solverVariable2, solverVariable16, i37, i36);
                                                    }
                                                    constraintWidget31 = constraintWidget4;
                                                }
                                                constraintWidget30 = constraintWidget2.mVisibility != i7 ? constraintWidget2 : constraintWidget3;
                                                constraintWidget2 = constraintWidget31;
                                                i33 = i7;
                                                i31 = i8;
                                            }
                                            ConstraintAnchor constraintAnchor28 = constraintWidget22.mListAnchors[i3];
                                            constraintAnchor = constraintWidget.mListAnchors[i3].mTarget;
                                            int i38 = i3 + 1;
                                            constraintAnchor2 = constraintWidget23.mListAnchors[i38];
                                            constraintAnchor3 = constraintWidget21.mListAnchors[i38].mTarget;
                                            if (constraintAnchor != null) {
                                                if (constraintWidget22 != constraintWidget23) {
                                                    linearSystem.addEquality(constraintAnchor28.mSolverVariable, constraintAnchor.mSolverVariable, constraintAnchor28.getMargin(), 5);
                                                } else if (constraintAnchor3 != null) {
                                                    linearSystem.addCentering(constraintAnchor28.mSolverVariable, constraintAnchor.mSolverVariable, constraintAnchor28.getMargin(), 0.5f, constraintAnchor2.mSolverVariable, constraintAnchor3.mSolverVariable, constraintAnchor2.getMargin(), 5);
                                                }
                                            }
                                            if (constraintAnchor3 != null && constraintWidget22 != constraintWidget23) {
                                                linearSystem.addEquality(constraintAnchor2.mSolverVariable, constraintAnchor3.mSolverVariable, -constraintAnchor2.getMargin(), 5);
                                            }
                                        }
                                        if ((!z5 || z6) && constraintWidget22 != null && constraintWidget22 != constraintWidget23) {
                                            ConstraintAnchor[] constraintAnchorArr622 = constraintWidget22.mListAnchors;
                                            constraintAnchor6 = constraintAnchorArr622[i3];
                                            if (constraintWidget23 == null) {
                                                constraintWidget23 = constraintWidget22;
                                            }
                                            i10 = i3 + 1;
                                            constraintAnchor7 = constraintWidget23.mListAnchors[i10];
                                            ConstraintAnchor constraintAnchor2322 = constraintAnchor6.mTarget;
                                            solverVariable4 = constraintAnchor2322 == null ? constraintAnchor2322.mSolverVariable : null;
                                            ConstraintAnchor constraintAnchor2422 = constraintAnchor7.mTarget;
                                            SolverVariable solverVariable17 = constraintAnchor2422 == null ? constraintAnchor2422.mSolverVariable : null;
                                            if (constraintWidget21 == constraintWidget23) {
                                                ConstraintAnchor constraintAnchor29 = constraintWidget21.mListAnchors[i10].mTarget;
                                                solverVariable5 = constraintAnchor29 != null ? constraintAnchor29.mSolverVariable : null;
                                            } else {
                                                solverVariable5 = solverVariable17;
                                            }
                                            if (constraintWidget22 == constraintWidget23) {
                                                constraintAnchor7 = constraintAnchorArr622[i10];
                                            }
                                            if (solverVariable4 != null && solverVariable5 != null) {
                                                linearSystem.addCentering(constraintAnchor6.mSolverVariable, solverVariable4, constraintAnchor6.getMargin(), 0.5f, solverVariable5, constraintAnchor7.mSolverVariable, constraintWidget23.mListAnchors[i10].getMargin(), 5);
                                            }
                                        }
                                    } else {
                                        int i39 = chainHead3.mWidgetsMatchCount;
                                        boolean z20 = i39 > 0 && chainHead3.mWidgetsCount == i39;
                                        ConstraintWidget constraintWidget34 = constraintWidget22;
                                        ConstraintWidget constraintWidget35 = constraintWidget34;
                                        while (constraintWidget35 != null) {
                                            ConstraintWidget constraintWidget36 = constraintWidget35.mNextChainWidget[i];
                                            while (constraintWidget36 != null && constraintWidget36.mVisibility == 8) {
                                                constraintWidget36 = constraintWidget36.mNextChainWidget[i];
                                            }
                                            if (constraintWidget36 != null || constraintWidget35 == constraintWidget23) {
                                                ConstraintAnchor[] constraintAnchorArr8 = constraintWidget35.mListAnchors;
                                                ConstraintAnchor constraintAnchor30 = constraintAnchorArr8[i3];
                                                SolverVariable solverVariable18 = constraintAnchor30.mSolverVariable;
                                                ConstraintAnchor constraintAnchor31 = constraintAnchor30.mTarget;
                                                SolverVariable solverVariable19 = constraintAnchor31 != null ? constraintAnchor31.mSolverVariable : null;
                                                if (constraintWidget34 != constraintWidget35) {
                                                    solverVariable19 = constraintWidget34.mListAnchors[i3 + 1].mSolverVariable;
                                                } else if (constraintWidget35 == constraintWidget22) {
                                                    ConstraintAnchor constraintAnchor32 = constraintWidget.mListAnchors[i3].mTarget;
                                                    solverVariable19 = constraintAnchor32 != null ? constraintAnchor32.mSolverVariable : null;
                                                }
                                                int margin6 = constraintAnchor30.getMargin();
                                                int i40 = i3 + 1;
                                                int margin7 = constraintAnchorArr8[i40].getMargin();
                                                if (constraintWidget36 != null) {
                                                    constraintAnchor5 = constraintWidget36.mListAnchors[i3];
                                                    solverVariable3 = constraintAnchor5.mSolverVariable;
                                                } else {
                                                    constraintAnchor5 = constraintWidget21.mListAnchors[i40].mTarget;
                                                    solverVariable3 = constraintAnchor5 != null ? constraintAnchor5.mSolverVariable : null;
                                                }
                                                SolverVariable solverVariable20 = constraintAnchorArr8[i40].mSolverVariable;
                                                if (constraintAnchor5 != null) {
                                                    margin7 = constraintAnchor5.getMargin() + margin7;
                                                }
                                                int margin8 = constraintWidget34.mListAnchors[i40].getMargin() + margin6;
                                                if (solverVariable18 != null && solverVariable19 != null && solverVariable3 != null && solverVariable20 != null) {
                                                    if (constraintWidget35 == constraintWidget22) {
                                                        margin8 = constraintWidget22.mListAnchors[i3].getMargin();
                                                    }
                                                    if (constraintWidget35 == constraintWidget23) {
                                                        margin7 = constraintWidget23.mListAnchors[i40].getMargin();
                                                    }
                                                    SolverVariable solverVariable21 = solverVariable19;
                                                    int i41 = margin8;
                                                    SolverVariable solverVariable22 = solverVariable3;
                                                    constraintWidget5 = constraintWidget;
                                                    constraintWidget6 = constraintWidget34;
                                                    int i42 = margin7;
                                                    constraintWidget7 = constraintWidget36;
                                                    constraintWidget8 = constraintWidget35;
                                                    i9 = i6;
                                                    linearSystem.addCentering(solverVariable18, solverVariable21, i41, 0.5f, solverVariable22, solverVariable20, i42, z20 ? 8 : 5);
                                                    constraintWidget34 = constraintWidget8.mVisibility == 8 ? constraintWidget8 : constraintWidget6;
                                                    constraintWidget = constraintWidget5;
                                                    constraintWidget35 = constraintWidget7;
                                                    i6 = i9;
                                                }
                                            }
                                            constraintWidget5 = constraintWidget;
                                            constraintWidget7 = constraintWidget36;
                                            constraintWidget6 = constraintWidget34;
                                            constraintWidget8 = constraintWidget35;
                                            i9 = i6;
                                            if (constraintWidget8.mVisibility == 8) {
                                            }
                                            constraintWidget = constraintWidget5;
                                            constraintWidget35 = constraintWidget7;
                                            i6 = i9;
                                        }
                                        i4 = i6;
                                        if (!z5) {
                                        }
                                        ConstraintAnchor[] constraintAnchorArr6222 = constraintWidget22.mListAnchors;
                                        constraintAnchor6 = constraintAnchorArr6222[i3];
                                        if (constraintWidget23 == null) {
                                        }
                                        i10 = i3 + 1;
                                        constraintAnchor7 = constraintWidget23.mListAnchors[i10];
                                        ConstraintAnchor constraintAnchor23222 = constraintAnchor6.mTarget;
                                        if (constraintAnchor23222 == null) {
                                        }
                                        ConstraintAnchor constraintAnchor24222 = constraintAnchor7.mTarget;
                                        if (constraintAnchor24222 == null) {
                                        }
                                        if (constraintWidget21 == constraintWidget23) {
                                        }
                                        if (constraintWidget22 == constraintWidget23) {
                                        }
                                        if (solverVariable4 != null) {
                                            linearSystem.addCentering(constraintAnchor6.mSolverVariable, solverVariable4, constraintAnchor6.getMargin(), 0.5f, solverVariable5, constraintAnchor7.mSolverVariable, constraintWidget23.mListAnchors[i10].getMargin(), 5);
                                        }
                                    }
                                }
                            }
                        }
                        if (z7) {
                            ConstraintAnchor constraintAnchor33 = constraintAnchor15.mTarget;
                            if (constraintAnchor33.mOwner == constraintWidgetContainer2) {
                                linearSystem.addEquality(constraintAnchor15.mSolverVariable, constraintAnchor33.mSolverVariable, -constraintAnchor15.getMargin(), 4);
                            }
                        }
                        linearSystem.addLowerThan(constraintAnchor15.mSolverVariable, constraintWidget21.mListAnchors[i28].mTarget.mSolverVariable, -constraintAnchor15.getMargin(), 6);
                        if (z13) {
                        }
                        arrayList2 = chainHead3.mWeightedMatchConstraintsWidgets;
                        if (arrayList2 != null) {
                            if (chainHead3.mHasUndefinedWeights) {
                                f5 = chainHead3.mWidgetsMatchCount;
                            }
                            ConstraintWidget constraintWidget282 = null;
                            float f62 = 0.0f;
                            i11 = 0;
                            while (i11 < size) {
                            }
                        }
                        int i312 = 4;
                        if (constraintWidget22 == null) {
                        }
                        i6 = i4;
                        constraintWidget = constraintWidget27;
                        if (z5) {
                        }
                        i4 = i6;
                        int i332 = 8;
                        if (z6) {
                            int i342 = chainHead3.mWidgetsMatchCount;
                            if (i342 <= 0) {
                            }
                            constraintWidget2 = constraintWidget22;
                            ConstraintWidget constraintWidget302 = constraintWidget2;
                            while (constraintWidget2 != null) {
                            }
                            ConstraintAnchor constraintAnchor282 = constraintWidget22.mListAnchors[i3];
                            constraintAnchor = constraintWidget.mListAnchors[i3].mTarget;
                            int i382 = i3 + 1;
                            constraintAnchor2 = constraintWidget23.mListAnchors[i382];
                            constraintAnchor3 = constraintWidget21.mListAnchors[i382].mTarget;
                            if (constraintAnchor != null) {
                            }
                            if (constraintAnchor3 != null) {
                                linearSystem.addEquality(constraintAnchor2.mSolverVariable, constraintAnchor3.mSolverVariable, -constraintAnchor2.getMargin(), 5);
                            }
                        }
                        if (!z5) {
                        }
                        ConstraintAnchor[] constraintAnchorArr62222 = constraintWidget22.mListAnchors;
                        constraintAnchor6 = constraintAnchorArr62222[i3];
                        if (constraintWidget23 == null) {
                        }
                        i10 = i3 + 1;
                        constraintAnchor7 = constraintWidget23.mListAnchors[i10];
                        ConstraintAnchor constraintAnchor232222 = constraintAnchor6.mTarget;
                        if (constraintAnchor232222 == null) {
                        }
                        ConstraintAnchor constraintAnchor242222 = constraintAnchor7.mTarget;
                        if (constraintAnchor242222 == null) {
                        }
                        if (constraintWidget21 == constraintWidget23) {
                        }
                        if (constraintWidget22 == constraintWidget23) {
                        }
                        if (solverVariable4 != null) {
                        }
                    }
                }
                if (z13) {
                }
                arrayList2 = chainHead3.mWeightedMatchConstraintsWidgets;
                if (arrayList2 != null) {
                }
                int i3122 = 4;
                if (constraintWidget22 == null) {
                }
                i6 = i4;
                constraintWidget = constraintWidget27;
                if (z5) {
                }
                i4 = i6;
                int i3322 = 8;
                if (z6) {
                }
                if (!z5) {
                }
                ConstraintAnchor[] constraintAnchorArr622222 = constraintWidget22.mListAnchors;
                constraintAnchor6 = constraintAnchorArr622222[i3];
                if (constraintWidget23 == null) {
                }
                i10 = i3 + 1;
                constraintAnchor7 = constraintWidget23.mListAnchors[i10];
                ConstraintAnchor constraintAnchor2322222 = constraintAnchor6.mTarget;
                if (constraintAnchor2322222 == null) {
                }
                ConstraintAnchor constraintAnchor2422222 = constraintAnchor7.mTarget;
                if (constraintAnchor2422222 == null) {
                }
                if (constraintWidget21 == constraintWidget23) {
                }
                if (constraintWidget22 == constraintWidget23) {
                }
                if (solverVariable4 != null) {
                }
            } else {
                chainHeadArr2 = chainHeadArr;
            }
            i16 = i4 + 1;
            constraintWidgetContainer2 = constraintWidgetContainer;
            arrayList4 = arrayList;
            i2 = i5;
            chainHeadArr = chainHeadArr2;
        }
    }
}
