package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.Cache;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ConstraintWidget {
    public ChainRun horizontalChainRun;
    public int horizontalGroup;
    protected ArrayList<ConstraintAnchor> mAnchors;
    private boolean mAnimated;
    public ConstraintAnchor mBaseline;
    int mBaselineDistance;
    public ConstraintAnchor mBottom;
    public ConstraintAnchor mCenter;
    ConstraintAnchor mCenterX;
    ConstraintAnchor mCenterY;
    public float mCircleConstraintAngle;
    private Object mCompanionWidget;
    private String mDebugName;
    public float mDimensionRatio;
    protected int mDimensionRatioSide;
    private boolean mHasBaseline;
    int mHeight;
    float mHorizontalBiasPercent;
    int mHorizontalChainStyle;
    ConstraintWidget mHorizontalNextWidget;
    public int mHorizontalResolution;
    private boolean mHorizontalSolvingPass;
    private boolean mInPlaceholder;
    private boolean mInVirtualLayout;
    private boolean[] mIsInBarrier;
    private int mLastHorizontalMeasureSpec;
    private int mLastVerticalMeasureSpec;
    public ConstraintAnchor mLeft;
    public ConstraintAnchor[] mListAnchors;
    public DimensionBehaviour[] mListDimensionBehaviors;
    protected ConstraintWidget[] mListNextMatchConstraintsWidget;
    public int mMatchConstraintDefaultHeight;
    public int mMatchConstraintDefaultWidth;
    public int mMatchConstraintMaxHeight;
    public int mMatchConstraintMaxWidth;
    public int mMatchConstraintMinHeight;
    public int mMatchConstraintMinWidth;
    public float mMatchConstraintPercentHeight;
    public float mMatchConstraintPercentWidth;
    private int[] mMaxDimension;
    protected int mMinHeight;
    protected int mMinWidth;
    protected ConstraintWidget[] mNextChainWidget;
    public ConstraintWidget mParent;
    float mResolvedDimensionRatio;
    int mResolvedDimensionRatioSide;
    private boolean mResolvedHorizontal;
    public int[] mResolvedMatchConstraintDefault;
    private boolean mResolvedVertical;
    public ConstraintAnchor mRight;
    public ConstraintAnchor mTop;
    private String mType;
    float mVerticalBiasPercent;
    int mVerticalChainStyle;
    ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution;
    private boolean mVerticalSolvingPass;
    private int mVisibility;
    public float[] mWeight;
    int mWidth;
    private int mWrapBehaviorInParent;

    /* renamed from: mX */
    protected int f9mX;

    /* renamed from: mY */
    protected int f10mY;
    public String stringId;
    public ChainRun verticalChainRun;
    public int verticalGroup;
    public boolean measured = false;
    public HorizontalWidgetRun mHorizontalRun = null;
    public VerticalWidgetRun mVerticalRun = null;
    public boolean[] isTerminalWidget = {true, true};
    private boolean mMeasureRequested = true;
    private boolean mOptimizeWrapOnResolved = true;
    private int mWidthOverride = -1;
    private int mHeightOverride = -1;

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT;

        DimensionBehaviour() {
        }
    }

    public ConstraintWidget() {
        new HashMap();
        this.mResolvedHorizontal = false;
        this.mResolvedVertical = false;
        this.mHorizontalSolvingPass = false;
        this.mVerticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = Float.NaN;
        this.mHasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mLeft = constraintAnchor;
        ConstraintAnchor constraintAnchor2 = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mTop = constraintAnchor2;
        ConstraintAnchor constraintAnchor3 = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mRight = constraintAnchor3;
        ConstraintAnchor constraintAnchor4 = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBottom = constraintAnchor4;
        ConstraintAnchor constraintAnchor5 = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mBaseline = constraintAnchor5;
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor6 = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor6;
        this.mListAnchors = new ConstraintAnchor[]{constraintAnchor, constraintAnchor3, constraintAnchor2, constraintAnchor4, constraintAnchor5, constraintAnchor6};
        ArrayList<ConstraintAnchor> arrayList = new ArrayList<>();
        this.mAnchors = arrayList;
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.f9mX = 0;
        this.f10mY = 0;
        this.mBaselineDistance = 0;
        this.mHorizontalBiasPercent = 0.5f;
        this.mVerticalBiasPercent = 0.5f;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        arrayList.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mCenter);
        this.mAnchors.add(this.mBaseline);
    }

    /* JADX WARN: Removed duplicated region for block: B:136:0x03a8  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0418  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x04b1  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0445  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x02e6  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x04fa  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x050c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void applyConstraints(LinearSystem linearSystem, boolean z, boolean z2, boolean z3, boolean z4, SolverVariable solverVariable, SolverVariable solverVariable2, DimensionBehaviour dimensionBehaviour, boolean z5, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i, int i2, int i3, int i4, float f, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, int i5, int i6, int i7, int i8, float f2, boolean z11) {
        boolean z12;
        int i9;
        boolean z13;
        int i10;
        int i11;
        boolean z14;
        int i12;
        int i13;
        int i14;
        boolean z15;
        boolean z16;
        SolverVariable createObjectVariable;
        SolverVariable createObjectVariable2;
        SolverVariable solverVariable3;
        SolverVariable solverVariable4;
        SolverVariable solverVariable5;
        SolverVariable solverVariable6;
        boolean z17;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        ConstraintAnchor constraintAnchor3;
        SolverVariable solverVariable7;
        int i20;
        int i21;
        boolean z18;
        int i22;
        SolverVariable solverVariable8;
        int i23;
        boolean z19;
        boolean z20;
        SolverVariable solverVariable9;
        int i24;
        int i25;
        boolean z21;
        boolean z22;
        int i26;
        boolean z23;
        int i27;
        boolean z24;
        boolean z25;
        ConstraintWidget constraintWidget;
        SolverVariable solverVariable10;
        SolverVariable solverVariable11;
        SolverVariable solverVariable12;
        SolverVariable solverVariable13;
        boolean z26;
        int i28;
        boolean z27;
        int i29;
        int i30;
        SolverVariable solverVariable14;
        ConstraintWidget constraintWidget2;
        boolean z28;
        boolean z29;
        SolverVariable solverVariable15;
        SolverVariable solverVariable16;
        SolverVariable solverVariable17;
        SolverVariable solverVariable18;
        int i31;
        int i32;
        int i33;
        int i34;
        int i35;
        int i36;
        int i37;
        int i38;
        boolean z30;
        boolean z31;
        int i39;
        int i40;
        int i41;
        int i42;
        int i43;
        int i44;
        SolverVariable solverVariable19;
        int i45;
        char c;
        int i46 = i7;
        int i47 = i8;
        SolverVariable createObjectVariable3 = linearSystem.createObjectVariable(constraintAnchor);
        SolverVariable createObjectVariable4 = linearSystem.createObjectVariable(constraintAnchor2);
        SolverVariable createObjectVariable5 = linearSystem.createObjectVariable(constraintAnchor.mTarget);
        SolverVariable createObjectVariable6 = linearSystem.createObjectVariable(constraintAnchor2.mTarget);
        boolean isConnected = constraintAnchor.isConnected();
        boolean isConnected2 = constraintAnchor2.isConnected();
        boolean isConnected3 = this.mCenter.isConnected();
        int i48 = isConnected2 ? (isConnected ? 1 : 0) + 1 : isConnected ? 1 : 0;
        if (isConnected3) {
            i48++;
        }
        int i49 = i48;
        int i50 = z6 ? 3 : i5;
        int ordinal = dimensionBehaviour.ordinal();
        if (ordinal != 0 && ordinal != 1 && ordinal == 2) {
            if (i50 != 4) {
                z12 = true;
                i9 = this.mWidthOverride;
                z13 = z12;
                if (i9 != -1 && z) {
                    this.mWidthOverride = -1;
                    i2 = i9;
                    z13 = false;
                }
                i10 = this.mHeightOverride;
                if (i10 != -1 || z) {
                    i10 = i2;
                } else {
                    this.mHeightOverride = -1;
                    z13 = false;
                }
                int i51 = i10;
                if (this.mVisibility != 8) {
                    i11 = 0;
                    z13 = false;
                } else {
                    i11 = i51;
                }
                if (z11) {
                    z14 = isConnected3;
                    i12 = 8;
                } else {
                    if (!isConnected && !isConnected2 && !isConnected3) {
                        linearSystem.addEquality(createObjectVariable3, i);
                    } else if (isConnected && !isConnected2) {
                        z14 = isConnected3;
                        i12 = 8;
                        linearSystem.addEquality(createObjectVariable3, createObjectVariable5, constraintAnchor.getMargin(), 8);
                    }
                    z14 = isConnected3;
                    i12 = 8;
                }
                if (z13) {
                    if (z5) {
                        c = 3;
                        linearSystem.addEquality(createObjectVariable4, createObjectVariable3, 0, 3);
                        if (i3 > 0) {
                            linearSystem.addGreaterThan(createObjectVariable4, createObjectVariable3, i3, i12);
                        }
                        if (i4 < Integer.MAX_VALUE) {
                            linearSystem.addLowerThan(createObjectVariable4, createObjectVariable3, i4, i12);
                        }
                    } else {
                        c = 3;
                        linearSystem.addEquality(createObjectVariable4, createObjectVariable3, i11, i12);
                    }
                } else if (i49 == 2 || z6 || !(i50 == 1 || i50 == 0)) {
                    if (i46 == -2) {
                        i46 = i11;
                    }
                    if (i47 == -2) {
                        i47 = i11;
                    }
                    if (i11 > 0 && i50 != 1) {
                        i11 = 0;
                    }
                    if (i46 > 0) {
                        linearSystem.addGreaterThan(createObjectVariable4, createObjectVariable3, i46, 8);
                        i11 = Math.max(i11, i46);
                    }
                    if (i47 > 0) {
                        if ((z2 && i50 == 1) ? false : true) {
                            i13 = 8;
                            linearSystem.addLowerThan(createObjectVariable4, createObjectVariable3, i47, 8);
                        } else {
                            i13 = 8;
                        }
                        i11 = Math.min(i11, i47);
                    } else {
                        i13 = 8;
                    }
                    if (i50 != 1) {
                        if (i50 == 2) {
                            ConstraintAnchor.Type type = ConstraintAnchor.Type.TOP;
                            ConstraintAnchor.Type type2 = ConstraintAnchor.Type.BOTTOM;
                            ConstraintAnchor.Type type3 = constraintAnchor.mType;
                            if (type3 == type || type3 == type2) {
                                createObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(type));
                                createObjectVariable2 = linearSystem.createObjectVariable(this.mParent.getAnchor(type2));
                            } else {
                                createObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                                createObjectVariable2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
                            }
                            ArrayRow createRow = linearSystem.createRow();
                            i14 = i49;
                            createRow.variables.put(createObjectVariable4, -1.0f);
                            createRow.variables.put(createObjectVariable3, 1.0f);
                            createRow.variables.put(createObjectVariable2, f2);
                            createRow.variables.put(createObjectVariable, -f2);
                            linearSystem.addConstraint(createRow);
                            if (z2) {
                                z13 = false;
                            }
                            z15 = z4;
                        } else {
                            i14 = i49;
                            z15 = true;
                        }
                        z16 = z15;
                        int i52 = i46;
                        if (!z11) {
                            solverVariable3 = solverVariable;
                            solverVariable4 = solverVariable2;
                            solverVariable5 = createObjectVariable4;
                            solverVariable6 = createObjectVariable3;
                            z17 = z16;
                            i15 = i14;
                            i16 = 0;
                            i17 = 8;
                            i18 = 1;
                            i19 = 2;
                        } else {
                            if (!z8) {
                                if (isConnected || isConnected2 || z14) {
                                    if (isConnected && !isConnected2) {
                                        i35 = (z2 && (constraintAnchor.mTarget.mOwner instanceof Barrier)) ? 8 : 5;
                                        z28 = z2;
                                        solverVariable17 = createObjectVariable4;
                                        z26 = z16;
                                        solverVariable15 = createObjectVariable6;
                                        i34 = 0;
                                    } else if (isConnected || !isConnected2) {
                                        solverVariable7 = createObjectVariable6;
                                        i20 = 0;
                                        if (isConnected && isConnected2) {
                                            ConstraintWidget constraintWidget3 = constraintAnchor.mTarget.mOwner;
                                            ConstraintWidget constraintWidget4 = constraintAnchor2.mTarget.mOwner;
                                            ConstraintWidget constraintWidget5 = this.mParent;
                                            if (z13) {
                                                if (i50 == 0) {
                                                    if (i47 != 0 || i52 != 0) {
                                                        i43 = 5;
                                                        i44 = 5;
                                                        z30 = false;
                                                        z21 = true;
                                                        z22 = true;
                                                    } else if (createObjectVariable5.isFinalValue && solverVariable7.isFinalValue) {
                                                        linearSystem.addEquality(createObjectVariable3, createObjectVariable5, constraintAnchor.getMargin(), 8);
                                                        linearSystem.addEquality(createObjectVariable4, solverVariable7, -constraintAnchor2.getMargin(), 8);
                                                        return;
                                                    } else {
                                                        i43 = 8;
                                                        i44 = 8;
                                                        z30 = true;
                                                        z21 = false;
                                                        z22 = false;
                                                    }
                                                    if ((constraintWidget3 instanceof Barrier) || (constraintWidget4 instanceof Barrier)) {
                                                        i21 = i50;
                                                        i38 = i43;
                                                        z18 = true;
                                                        i22 = 8;
                                                        solverVariable8 = solverVariable2;
                                                        i25 = i38;
                                                        i27 = 6;
                                                        z23 = z30;
                                                        i23 = 5;
                                                        i26 = 4;
                                                    } else {
                                                        i27 = 6;
                                                        i25 = i43;
                                                        z23 = z30;
                                                        i23 = 5;
                                                        z18 = true;
                                                        i22 = 8;
                                                        i26 = i44;
                                                        i21 = i50;
                                                        solverVariable8 = solverVariable2;
                                                    }
                                                } else {
                                                    i22 = 8;
                                                    if (i50 == 2) {
                                                        i42 = ((constraintWidget3 instanceof Barrier) || (constraintWidget4 instanceof Barrier)) ? 4 : 5;
                                                        i41 = 5;
                                                    } else if (i50 == 1) {
                                                        i41 = 8;
                                                        i42 = 4;
                                                    } else if (i50 == 3) {
                                                        i21 = i50;
                                                        if (this.mResolvedDimensionRatioSide == -1) {
                                                            solverVariable8 = solverVariable2;
                                                            i27 = z9 ? z2 ? 5 : 4 : 8;
                                                            i25 = 8;
                                                            i23 = 5;
                                                            z18 = true;
                                                            i26 = 5;
                                                            z21 = true;
                                                            z22 = true;
                                                            z23 = true;
                                                        } else if (z6) {
                                                            if (i6 != 2) {
                                                                z18 = true;
                                                                if (i6 != 1) {
                                                                    z31 = false;
                                                                    if (z31) {
                                                                        i40 = 8;
                                                                        i39 = 5;
                                                                    } else {
                                                                        i39 = 4;
                                                                        i40 = 5;
                                                                    }
                                                                    i26 = i39;
                                                                    i25 = i40;
                                                                    z21 = z18;
                                                                    z22 = z21;
                                                                    z23 = z22;
                                                                    i27 = 6;
                                                                    i23 = 5;
                                                                    solverVariable8 = solverVariable2;
                                                                }
                                                            } else {
                                                                z18 = true;
                                                            }
                                                            z31 = z18;
                                                            if (z31) {
                                                            }
                                                            i26 = i39;
                                                            i25 = i40;
                                                            z21 = z18;
                                                            z22 = z21;
                                                            z23 = z22;
                                                            i27 = 6;
                                                            i23 = 5;
                                                            solverVariable8 = solverVariable2;
                                                        } else {
                                                            z18 = true;
                                                            if (i47 > 0) {
                                                                i37 = 5;
                                                            } else if (i47 != 0 || i52 != 0) {
                                                                i37 = 4;
                                                            } else if (z9) {
                                                                i38 = (constraintWidget3 == constraintWidget5 || constraintWidget4 == constraintWidget5) ? 5 : 4;
                                                                z30 = true;
                                                                z21 = true;
                                                                z22 = true;
                                                                solverVariable8 = solverVariable2;
                                                                i25 = i38;
                                                                i27 = 6;
                                                                z23 = z30;
                                                                i23 = 5;
                                                                i26 = 4;
                                                            } else {
                                                                i37 = 8;
                                                            }
                                                            solverVariable8 = solverVariable2;
                                                            i26 = i37;
                                                            z21 = true;
                                                            z22 = true;
                                                            z23 = true;
                                                            i27 = 6;
                                                            i23 = 5;
                                                            i25 = 5;
                                                        }
                                                    } else {
                                                        i21 = i50;
                                                        z18 = true;
                                                        solverVariable8 = solverVariable2;
                                                        i23 = 5;
                                                        z19 = false;
                                                        z20 = false;
                                                    }
                                                    i26 = i42;
                                                    i21 = i50;
                                                    i25 = i41;
                                                    i27 = 6;
                                                    i23 = 5;
                                                    z18 = true;
                                                    z21 = true;
                                                    z22 = true;
                                                    z23 = false;
                                                    solverVariable8 = solverVariable2;
                                                }
                                                if (z21 || createObjectVariable5 != solverVariable7 || constraintWidget3 == constraintWidget5) {
                                                    z24 = z21;
                                                    z25 = z18;
                                                } else {
                                                    z25 = false;
                                                    z24 = false;
                                                }
                                                if (z22) {
                                                    constraintWidget = constraintWidget3;
                                                    solverVariable10 = solverVariable7;
                                                    solverVariable11 = createObjectVariable5;
                                                    solverVariable12 = createObjectVariable4;
                                                    solverVariable13 = createObjectVariable3;
                                                    z26 = z16;
                                                    i28 = i52;
                                                    z27 = z18;
                                                    i29 = i22;
                                                    i30 = 4;
                                                    solverVariable14 = solverVariable;
                                                    constraintWidget2 = constraintWidget4;
                                                    z28 = z2;
                                                } else {
                                                    if (z13 || z7 || z9 || createObjectVariable5 != solverVariable || solverVariable7 != solverVariable8) {
                                                        i36 = i27;
                                                        z28 = z2;
                                                    } else {
                                                        i36 = i22;
                                                        i25 = i36;
                                                        z28 = false;
                                                        z25 = false;
                                                    }
                                                    constraintWidget = constraintWidget3;
                                                    solverVariable14 = solverVariable;
                                                    z26 = z16;
                                                    constraintWidget2 = constraintWidget4;
                                                    solverVariable10 = solverVariable7;
                                                    i28 = i52;
                                                    i29 = i22;
                                                    i30 = 4;
                                                    z27 = true;
                                                    solverVariable11 = createObjectVariable5;
                                                    solverVariable12 = createObjectVariable4;
                                                    solverVariable13 = createObjectVariable3;
                                                    linearSystem.addCentering(createObjectVariable3, createObjectVariable5, constraintAnchor.getMargin(), f, solverVariable7, createObjectVariable4, constraintAnchor2.getMargin(), i36);
                                                }
                                                z29 = z25;
                                                if (this.mVisibility != i29 && !constraintAnchor2.hasDependents()) {
                                                    return;
                                                }
                                                if (z24) {
                                                    solverVariable15 = solverVariable10;
                                                    solverVariable16 = solverVariable11;
                                                    solverVariable17 = solverVariable12;
                                                    solverVariable18 = solverVariable13;
                                                } else {
                                                    solverVariable15 = solverVariable10;
                                                    solverVariable16 = solverVariable11;
                                                    int i53 = (!z28 || solverVariable16 == solverVariable15 || z13 || !((constraintWidget instanceof Barrier) || (constraintWidget2 instanceof Barrier))) ? i25 : 6;
                                                    solverVariable18 = solverVariable13;
                                                    linearSystem.addGreaterThan(solverVariable18, solverVariable16, constraintAnchor.getMargin(), i53);
                                                    solverVariable17 = solverVariable12;
                                                    linearSystem.addLowerThan(solverVariable17, solverVariable15, -constraintAnchor2.getMargin(), i53);
                                                    i25 = i53;
                                                }
                                                if (z28 || !z10 || (constraintWidget instanceof Barrier) || (constraintWidget2 instanceof Barrier) || constraintWidget2 == constraintWidget5) {
                                                    i31 = i26;
                                                    i32 = i25;
                                                } else {
                                                    i31 = 6;
                                                    i32 = 6;
                                                    z29 = z27;
                                                }
                                                if (z29) {
                                                    if (z23 && (!z9 || z3)) {
                                                        int i54 = (constraintWidget == constraintWidget5 || constraintWidget2 == constraintWidget5) ? 6 : i31;
                                                        if ((constraintWidget instanceof Guideline) || (constraintWidget2 instanceof Guideline)) {
                                                            i54 = 5;
                                                        }
                                                        if ((constraintWidget instanceof Barrier) || (constraintWidget2 instanceof Barrier)) {
                                                            i54 = 5;
                                                        }
                                                        if (z9) {
                                                            i54 = 5;
                                                        }
                                                        i31 = Math.max(i54, i31);
                                                    }
                                                    int i55 = i31;
                                                    if (z28) {
                                                        i55 = Math.min(i32, i55);
                                                        if (z6 && !z9 && (constraintWidget == constraintWidget5 || constraintWidget2 == constraintWidget5)) {
                                                            i55 = i30;
                                                        }
                                                    }
                                                    linearSystem.addEquality(solverVariable18, solverVariable16, constraintAnchor.getMargin(), i55);
                                                    linearSystem.addEquality(solverVariable17, solverVariable15, -constraintAnchor2.getMargin(), i55);
                                                }
                                                if (z28) {
                                                    int margin = solverVariable14 == solverVariable16 ? constraintAnchor.getMargin() : 0;
                                                    if (solverVariable16 != solverVariable14) {
                                                        linearSystem.addGreaterThan(solverVariable18, solverVariable14, margin, 5);
                                                    }
                                                }
                                                if (z28 || !z13 || i3 != 0 || i28 != 0) {
                                                    i33 = 5;
                                                    i34 = 0;
                                                } else if (z13 && i21 == 3) {
                                                    i34 = 0;
                                                    linearSystem.addGreaterThan(solverVariable17, solverVariable18, 0, i29);
                                                    i35 = 5;
                                                } else {
                                                    i34 = 0;
                                                    i33 = 5;
                                                    linearSystem.addGreaterThan(solverVariable17, solverVariable18, 0, 5);
                                                }
                                                i35 = i33;
                                            } else {
                                                i21 = i50;
                                                z18 = true;
                                                i22 = 8;
                                                if (createObjectVariable5.isFinalValue && solverVariable7.isFinalValue) {
                                                    linearSystem.addCentering(createObjectVariable3, createObjectVariable5, constraintAnchor.getMargin(), f, solverVariable7, createObjectVariable4, constraintAnchor2.getMargin(), 8);
                                                    if (z2 && z16) {
                                                        if (constraintAnchor2.mTarget != null) {
                                                            i24 = constraintAnchor2.getMargin();
                                                            solverVariable9 = solverVariable2;
                                                        } else {
                                                            solverVariable9 = solverVariable2;
                                                            i24 = 0;
                                                        }
                                                        if (solverVariable7 != solverVariable9) {
                                                            linearSystem.addGreaterThan(solverVariable9, createObjectVariable4, i24, 5);
                                                            return;
                                                        }
                                                        return;
                                                    }
                                                    return;
                                                }
                                                solverVariable8 = solverVariable2;
                                                i23 = 5;
                                                z19 = true;
                                                z20 = true;
                                            }
                                            i25 = i23;
                                            z21 = z19;
                                            z22 = z20;
                                            i26 = 4;
                                            z23 = false;
                                            i27 = 6;
                                            if (z21) {
                                            }
                                            z24 = z21;
                                            z25 = z18;
                                            if (z22) {
                                            }
                                            z29 = z25;
                                            if (this.mVisibility != i29) {
                                            }
                                            if (z24) {
                                            }
                                            if (z28) {
                                            }
                                            i31 = i26;
                                            i32 = i25;
                                            if (z29) {
                                            }
                                            if (z28) {
                                            }
                                            if (z28) {
                                            }
                                            i33 = 5;
                                            i34 = 0;
                                            i35 = i33;
                                        }
                                        i34 = i20;
                                        solverVariable15 = solverVariable7;
                                        solverVariable17 = createObjectVariable4;
                                        z26 = z16;
                                        i33 = 5;
                                        z28 = z2;
                                        i35 = i33;
                                    } else {
                                        solverVariable7 = createObjectVariable6;
                                        linearSystem.addEquality(createObjectVariable4, solverVariable7, -constraintAnchor2.getMargin(), 8);
                                        if (z2) {
                                            i33 = 5;
                                            linearSystem.addGreaterThan(createObjectVariable3, solverVariable, 0, 5);
                                            i34 = 0;
                                            solverVariable15 = solverVariable7;
                                            solverVariable17 = createObjectVariable4;
                                            z26 = z16;
                                            z28 = z2;
                                            i35 = i33;
                                        }
                                    }
                                    if (z28 || !z26) {
                                        return;
                                    }
                                    if (constraintAnchor2.mTarget != null) {
                                        i45 = constraintAnchor2.getMargin();
                                        solverVariable19 = solverVariable2;
                                    } else {
                                        solverVariable19 = solverVariable2;
                                        i45 = i34;
                                    }
                                    if (solverVariable15 != solverVariable19) {
                                        linearSystem.addGreaterThan(solverVariable19, solverVariable17, i45, i35);
                                        return;
                                    }
                                    return;
                                }
                                solverVariable7 = createObjectVariable6;
                                i20 = 0;
                                i34 = i20;
                                solverVariable15 = solverVariable7;
                                solverVariable17 = createObjectVariable4;
                                z26 = z16;
                                i33 = 5;
                                z28 = z2;
                                i35 = i33;
                                if (z28) {
                                    return;
                                } else {
                                    return;
                                }
                            }
                            solverVariable3 = solverVariable;
                            solverVariable4 = solverVariable2;
                            solverVariable5 = createObjectVariable4;
                            solverVariable6 = createObjectVariable3;
                            z17 = z16;
                            i15 = i14;
                            i16 = 0;
                            i19 = 2;
                            i17 = 8;
                            i18 = 1;
                        }
                        if (i15 < i19 && z2 && z17) {
                            linearSystem.addGreaterThan(solverVariable6, solverVariable3, i16, i17);
                            int i56 = (z || this.mBaseline.mTarget == null) ? i18 : i16;
                            if (!z && (constraintAnchor3 = this.mBaseline.mTarget) != null) {
                                ConstraintWidget constraintWidget6 = constraintAnchor3.mOwner;
                                if (constraintWidget6.mDimensionRatio != 0.0f) {
                                    DimensionBehaviour[] dimensionBehaviourArr = constraintWidget6.mListDimensionBehaviors;
                                    DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[i16];
                                    DimensionBehaviour dimensionBehaviour3 = DimensionBehaviour.MATCH_CONSTRAINT;
                                    if (dimensionBehaviour2 == dimensionBehaviour3 && dimensionBehaviourArr[i18] == dimensionBehaviour3) {
                                        i56 = i18;
                                    }
                                }
                                i56 = i16;
                            }
                            if (i56 != 0) {
                                linearSystem.addGreaterThan(solverVariable4, solverVariable5, i16, i17);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    if (z2) {
                        linearSystem.addEquality(createObjectVariable4, createObjectVariable3, i11, i13);
                    } else if (z8) {
                        linearSystem.addEquality(createObjectVariable4, createObjectVariable3, i11, 5);
                        linearSystem.addLowerThan(createObjectVariable4, createObjectVariable3, i11, i13);
                    } else {
                        linearSystem.addEquality(createObjectVariable4, createObjectVariable3, i11, 5);
                        linearSystem.addLowerThan(createObjectVariable4, createObjectVariable3, i11, i13);
                    }
                } else {
                    int max = Math.max(i46, i11);
                    if (i47 > 0) {
                        max = Math.min(i47, max);
                    }
                    linearSystem.addEquality(createObjectVariable4, createObjectVariable3, max, 8);
                    z13 = false;
                }
                z16 = z4;
                i14 = i49;
                int i522 = i46;
                if (!z11) {
                }
                if (i15 < i19) {
                    return;
                } else {
                    return;
                }
            }
        }
        z12 = false;
        i9 = this.mWidthOverride;
        z13 = z12;
        if (i9 != -1) {
            this.mWidthOverride = -1;
            i2 = i9;
            z13 = false;
        }
        i10 = this.mHeightOverride;
        if (i10 != -1) {
        }
        i10 = i2;
        int i512 = i10;
        if (this.mVisibility != 8) {
        }
        if (z11) {
        }
        if (z13) {
        }
        z16 = z4;
        i14 = i49;
        int i5222 = i46;
        if (!z11) {
        }
        if (i15 < i19) {
        }
    }

    private boolean isChainHead(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        int i2 = i * 2;
        ConstraintAnchor[] constraintAnchorArr = this.mListAnchors;
        ConstraintAnchor constraintAnchor3 = constraintAnchorArr[i2];
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return (constraintAnchor4 == null || constraintAnchor4.mTarget == constraintAnchor3 || (constraintAnchor2 = (constraintAnchor = constraintAnchorArr[i2 + 1]).mTarget) == null || constraintAnchor2.mTarget != constraintAnchor) ? false : true;
    }

    private static void serializeAttribute(StringBuilder sb, String str, float f, float f2) {
        if (f == f2) {
            return;
        }
        sb.append(str);
        sb.append(" :   ");
        sb.append(f);
        sb.append(",\n");
    }

    public final void addChildrenToSolverByDependency(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, HashSet<ConstraintWidget> hashSet, int i, boolean z) {
        if (z) {
            if (!hashSet.contains(this)) {
                return;
            }
            Optimizer.checkMatchParent(constraintWidgetContainer, linearSystem, this);
            hashSet.remove(this);
            addToSolver(linearSystem, constraintWidgetContainer.optimizeFor(64));
        }
        if (i == 0) {
            HashSet<ConstraintAnchor> dependents = this.mLeft.getDependents();
            if (dependents != null) {
                Iterator<ConstraintAnchor> it = dependents.iterator();
                while (it.hasNext()) {
                    it.next().mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
                }
            }
            HashSet<ConstraintAnchor> dependents2 = this.mRight.getDependents();
            if (dependents2 != null) {
                Iterator<ConstraintAnchor> it2 = dependents2.iterator();
                while (it2.hasNext()) {
                    it2.next().mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
                }
                return;
            }
            return;
        }
        HashSet<ConstraintAnchor> dependents3 = this.mTop.getDependents();
        if (dependents3 != null) {
            Iterator<ConstraintAnchor> it3 = dependents3.iterator();
            while (it3.hasNext()) {
                it3.next().mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
        HashSet<ConstraintAnchor> dependents4 = this.mBottom.getDependents();
        if (dependents4 != null) {
            Iterator<ConstraintAnchor> it4 = dependents4.iterator();
            while (it4.hasNext()) {
                it4.next().mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
        HashSet<ConstraintAnchor> dependents5 = this.mBaseline.getDependents();
        if (dependents5 != null) {
            Iterator<ConstraintAnchor> it5 = dependents5.iterator();
            while (it5.hasNext()) {
                it5.next().mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x024c  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x038e  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x03b0  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x03ee  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0424  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0432  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x045b  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x04c0  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0538  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x053d  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x05d0  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x061a  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0685  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0679  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x05d3  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x053a  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x0524  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x045e  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x043a  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x042c  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x03bc  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0376  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x0208  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0213  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void addToSolver(LinearSystem linearSystem, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        boolean z5;
        boolean z6;
        int i;
        int i2;
        int i3;
        int i4;
        float f;
        SolverVariable solverVariable;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z7;
        int i9;
        boolean z8;
        boolean z9;
        int i10;
        boolean z10;
        boolean z11;
        DimensionBehaviour dimensionBehaviour;
        DimensionBehaviour dimensionBehaviour2;
        SolverVariable solverVariable2;
        SolverVariable solverVariable3;
        SolverVariable solverVariable4;
        SolverVariable solverVariable5;
        SolverVariable solverVariable6;
        int i11;
        int i12;
        int i13;
        ConstraintWidget constraintWidget3;
        LinearSystem linearSystem2;
        SolverVariable solverVariable7;
        SolverVariable solverVariable8;
        SolverVariable solverVariable9;
        int i14;
        SolverVariable solverVariable10;
        SolverVariable solverVariable11;
        ConstraintWidget constraintWidget4;
        LinearSystem linearSystem3;
        boolean z12;
        HorizontalWidgetRun horizontalWidgetRun;
        DependencyNode dependencyNode;
        int i15;
        boolean isInHorizontalChain;
        boolean isInVerticalChain;
        HorizontalWidgetRun horizontalWidgetRun2;
        VerticalWidgetRun verticalWidgetRun;
        DependencyNode dependencyNode2;
        int size;
        int i16;
        boolean z13;
        SolverVariable createObjectVariable = linearSystem.createObjectVariable(this.mLeft);
        SolverVariable createObjectVariable2 = linearSystem.createObjectVariable(this.mRight);
        SolverVariable createObjectVariable3 = linearSystem.createObjectVariable(this.mTop);
        SolverVariable createObjectVariable4 = linearSystem.createObjectVariable(this.mBottom);
        SolverVariable createObjectVariable5 = linearSystem.createObjectVariable(this.mBaseline);
        ConstraintWidget constraintWidget5 = this.mParent;
        DimensionBehaviour dimensionBehaviour3 = DimensionBehaviour.WRAP_CONTENT;
        if (constraintWidget5 != null) {
            DimensionBehaviour[] dimensionBehaviourArr = constraintWidget5.mListDimensionBehaviors;
            boolean z14 = dimensionBehaviourArr[0] == dimensionBehaviour3;
            boolean z15 = dimensionBehaviourArr[1] == dimensionBehaviour3;
            int i17 = this.mWrapBehaviorInParent;
            if (i17 == 1) {
                z3 = z14;
                z2 = false;
            } else if (i17 == 2) {
                z2 = z15;
                z3 = false;
            } else if (i17 != 3) {
                z2 = z15;
                z3 = z14;
            }
            if (this.mVisibility == 8 && !this.mAnimated) {
                size = this.mAnchors.size();
                i16 = 0;
                while (true) {
                    if (i16 < size) {
                        z13 = false;
                        break;
                    } else {
                        if (this.mAnchors.get(i16).hasDependents()) {
                            z13 = true;
                            break;
                        }
                        i16++;
                    }
                }
                if (!z13) {
                    boolean[] zArr = this.mIsInBarrier;
                    if (!zArr[0] && !zArr[1]) {
                        return;
                    }
                }
            }
            z4 = this.mResolvedHorizontal;
            if (!z4 || this.mResolvedVertical) {
                if (z4) {
                    linearSystem.addEquality(createObjectVariable, this.f9mX);
                    linearSystem.addEquality(createObjectVariable2, this.f9mX + this.mWidth);
                    if (z3 && (constraintWidget2 = this.mParent) != null) {
                        if (this.mOptimizeWrapOnResolved) {
                            ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) constraintWidget2;
                            constraintWidgetContainer.addHorizontalWrapMinVariable(this.mLeft);
                            constraintWidgetContainer.addHorizontalWrapMaxVariable(this.mRight);
                        } else {
                            linearSystem.addGreaterThan(linearSystem.createObjectVariable(constraintWidget2.mRight), createObjectVariable2, 0, 5);
                        }
                    }
                }
                if (this.mResolvedVertical) {
                    linearSystem.addEquality(createObjectVariable3, this.f10mY);
                    linearSystem.addEquality(createObjectVariable4, this.f10mY + this.mHeight);
                    if (this.mBaseline.hasDependents()) {
                        linearSystem.addEquality(createObjectVariable5, this.f10mY + this.mBaselineDistance);
                    }
                    if (z2 && (constraintWidget = this.mParent) != null) {
                        if (this.mOptimizeWrapOnResolved) {
                            ConstraintWidgetContainer constraintWidgetContainer2 = (ConstraintWidgetContainer) constraintWidget;
                            constraintWidgetContainer2.addVerticalWrapMinVariable(this.mTop);
                            constraintWidgetContainer2.addVerticalWrapMaxVariable(this.mBottom);
                        } else {
                            linearSystem.addGreaterThan(linearSystem.createObjectVariable(constraintWidget.mBottom), createObjectVariable4, 0, 5);
                        }
                    }
                }
                if (this.mResolvedHorizontal && this.mResolvedVertical) {
                    this.mResolvedHorizontal = false;
                    this.mResolvedVertical = false;
                    return;
                }
            }
            if (z && (horizontalWidgetRun2 = this.mHorizontalRun) != null && (verticalWidgetRun = this.mVerticalRun) != null) {
                dependencyNode2 = horizontalWidgetRun2.start;
                if (dependencyNode2.resolved && horizontalWidgetRun2.end.resolved && verticalWidgetRun.start.resolved && verticalWidgetRun.end.resolved) {
                    linearSystem.addEquality(createObjectVariable, dependencyNode2.value);
                    linearSystem.addEquality(createObjectVariable2, this.mHorizontalRun.end.value);
                    linearSystem.addEquality(createObjectVariable3, this.mVerticalRun.start.value);
                    linearSystem.addEquality(createObjectVariable4, this.mVerticalRun.end.value);
                    linearSystem.addEquality(createObjectVariable5, this.mVerticalRun.baseline.value);
                    if (this.mParent != null) {
                        if (z3 && this.isTerminalWidget[0] && !isInHorizontalChain()) {
                            linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mRight), createObjectVariable2, 0, 8);
                        }
                        if (z2 && this.isTerminalWidget[1] && !isInVerticalChain()) {
                            linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mBottom), createObjectVariable4, 0, 8);
                        }
                    }
                    this.mResolvedHorizontal = false;
                    this.mResolvedVertical = false;
                    return;
                }
            }
            if (this.mParent == null) {
                if (isChainHead(0)) {
                    ((ConstraintWidgetContainer) this.mParent).addChain(0, this);
                    isInHorizontalChain = true;
                } else {
                    isInHorizontalChain = isInHorizontalChain();
                }
                if (isChainHead(1)) {
                    ((ConstraintWidgetContainer) this.mParent).addChain(1, this);
                    isInVerticalChain = true;
                } else {
                    isInVerticalChain = isInVerticalChain();
                }
                if (!isInHorizontalChain && z3 && this.mVisibility != 8 && this.mLeft.mTarget == null && this.mRight.mTarget == null) {
                    linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mRight), createObjectVariable2, 0, 1);
                }
                if (!isInVerticalChain && z2 && this.mVisibility != 8 && this.mTop.mTarget == null && this.mBottom.mTarget == null && this.mBaseline == null) {
                    linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mBottom), createObjectVariable4, 0, 1);
                }
                z6 = isInHorizontalChain;
                z5 = isInVerticalChain;
            } else {
                z5 = false;
                z6 = false;
            }
            i = this.mWidth;
            i2 = this.mMinWidth;
            if (i >= i2) {
                i2 = i;
            }
            i3 = this.mHeight;
            i4 = this.mMinHeight;
            if (i3 >= i4) {
                i4 = i3;
            }
            DimensionBehaviour[] dimensionBehaviourArr2 = this.mListDimensionBehaviors;
            DimensionBehaviour dimensionBehaviour4 = dimensionBehaviourArr2[0];
            int i18 = i2;
            DimensionBehaviour dimensionBehaviour5 = DimensionBehaviour.MATCH_CONSTRAINT;
            boolean z16 = dimensionBehaviour4 == dimensionBehaviour5;
            DimensionBehaviour dimensionBehaviour6 = dimensionBehaviourArr2[1];
            boolean z17 = dimensionBehaviour6 == dimensionBehaviour5;
            int i19 = this.mDimensionRatioSide;
            this.mResolvedDimensionRatioSide = i19;
            f = this.mDimensionRatio;
            this.mResolvedDimensionRatio = f;
            int i20 = i4;
            int i21 = this.mMatchConstraintDefaultWidth;
            int i22 = this.mMatchConstraintDefaultHeight;
            if (f <= 0.0f) {
                solverVariable = createObjectVariable4;
                if (this.mVisibility != 8) {
                    if (dimensionBehaviour4 == dimensionBehaviour5 && i21 == 0) {
                        i21 = 3;
                    }
                    if (dimensionBehaviour6 == dimensionBehaviour5 && i22 == 0) {
                        i22 = 3;
                    }
                    if (dimensionBehaviour4 != dimensionBehaviour5 || dimensionBehaviour6 != dimensionBehaviour5 || i21 != 3 || i22 != 3) {
                        if (dimensionBehaviour4 == dimensionBehaviour5 && i21 == 3) {
                            this.mResolvedDimensionRatioSide = 0;
                            i7 = (int) (f * i3);
                            if (dimensionBehaviour6 != dimensionBehaviour5) {
                                i6 = i22;
                                i8 = i20;
                                z7 = false;
                                i5 = 4;
                            } else {
                                i5 = i21;
                                i6 = i22;
                                i8 = i20;
                                z7 = true;
                            }
                        } else if (dimensionBehaviour6 == dimensionBehaviour5 && i22 == 3) {
                            this.mResolvedDimensionRatioSide = 1;
                            if (i19 == -1) {
                                this.mResolvedDimensionRatio = 1.0f / f;
                            }
                            int i23 = (int) (this.mResolvedDimensionRatio * i);
                            if (dimensionBehaviour4 != dimensionBehaviour5) {
                                i8 = i23;
                                i5 = i21;
                                i7 = i18;
                                z7 = false;
                                i6 = 4;
                            } else {
                                i20 = i23;
                                i7 = i18;
                                i5 = i21;
                                i6 = i22;
                                i8 = i20;
                                z7 = true;
                            }
                        }
                        int[] iArr = this.mResolvedMatchConstraintDefault;
                        iArr[0] = i5;
                        iArr[1] = i6;
                        if (z7) {
                            i9 = -1;
                        } else {
                            int i24 = this.mResolvedDimensionRatioSide;
                            i9 = -1;
                            if (i24 == 0 || i24 == -1) {
                                z8 = true;
                                boolean z18 = !z7 && ((i15 = this.mResolvedDimensionRatioSide) == 1 || i15 == i9);
                                z9 = this.mListDimensionBehaviors[0] != dimensionBehaviour3 && (this instanceof ConstraintWidgetContainer);
                                i10 = z9 ? 0 : i7;
                                z10 = !this.mCenter.isConnected();
                                boolean[] zArr2 = this.mIsInBarrier;
                                z11 = zArr2[0];
                                boolean z19 = zArr2[1];
                                if (this.mHorizontalResolution != 2 && !this.mResolvedHorizontal) {
                                    if (z && (horizontalWidgetRun = this.mHorizontalRun) != null) {
                                        dependencyNode = horizontalWidgetRun.start;
                                        if (dependencyNode.resolved && horizontalWidgetRun.end.resolved) {
                                            if (z) {
                                                linearSystem.addEquality(createObjectVariable, dependencyNode.value);
                                                linearSystem.addEquality(createObjectVariable2, this.mHorizontalRun.end.value);
                                                if (this.mParent != null && z3 && this.isTerminalWidget[0] && !isInHorizontalChain()) {
                                                    linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mRight), createObjectVariable2, 0, 8);
                                                }
                                            }
                                        }
                                    }
                                    ConstraintWidget constraintWidget6 = this.mParent;
                                    SolverVariable createObjectVariable6 = constraintWidget6 == null ? linearSystem.createObjectVariable(constraintWidget6.mRight) : null;
                                    ConstraintWidget constraintWidget7 = this.mParent;
                                    SolverVariable createObjectVariable7 = constraintWidget7 == null ? linearSystem.createObjectVariable(constraintWidget7.mLeft) : null;
                                    boolean z20 = this.isTerminalWidget[0];
                                    DimensionBehaviour[] dimensionBehaviourArr3 = this.mListDimensionBehaviors;
                                    dimensionBehaviour = dimensionBehaviour5;
                                    solverVariable4 = createObjectVariable5;
                                    dimensionBehaviour2 = dimensionBehaviour3;
                                    solverVariable6 = solverVariable;
                                    solverVariable5 = createObjectVariable3;
                                    solverVariable2 = createObjectVariable2;
                                    solverVariable3 = createObjectVariable;
                                    applyConstraints(linearSystem, true, z3, z2, z20, createObjectVariable7, createObjectVariable6, dimensionBehaviourArr3[0], z9, this.mLeft, this.mRight, this.f9mX, i10, this.mMinWidth, this.mMaxDimension[0], this.mHorizontalBiasPercent, z8, dimensionBehaviourArr3[1] != dimensionBehaviour5, z6, z5, z11, i5, i6, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth, this.mMatchConstraintPercentWidth, z10);
                                    if (z) {
                                        constraintWidget3 = this;
                                        VerticalWidgetRun verticalWidgetRun2 = constraintWidget3.mVerticalRun;
                                        if (verticalWidgetRun2 != null) {
                                            DependencyNode dependencyNode3 = verticalWidgetRun2.start;
                                            if (dependencyNode3.resolved && verticalWidgetRun2.end.resolved) {
                                                linearSystem2 = linearSystem;
                                                solverVariable9 = solverVariable5;
                                                linearSystem2.addEquality(solverVariable9, dependencyNode3.value);
                                                solverVariable8 = solverVariable6;
                                                linearSystem2.addEquality(solverVariable8, constraintWidget3.mVerticalRun.end.value);
                                                solverVariable7 = solverVariable4;
                                                linearSystem2.addEquality(solverVariable7, constraintWidget3.mVerticalRun.baseline.value);
                                                ConstraintWidget constraintWidget8 = constraintWidget3.mParent;
                                                if (constraintWidget8 == null || z5 || !z2) {
                                                    i11 = 8;
                                                    i12 = 0;
                                                    i13 = 1;
                                                } else {
                                                    i13 = 1;
                                                    if (constraintWidget3.isTerminalWidget[1]) {
                                                        i11 = 8;
                                                        i12 = 0;
                                                        linearSystem2.addGreaterThan(linearSystem2.createObjectVariable(constraintWidget8.mBottom), solverVariable8, 0, 8);
                                                    } else {
                                                        i11 = 8;
                                                        i12 = 0;
                                                    }
                                                }
                                                i14 = i12;
                                                if ((constraintWidget3.mVerticalResolution != 2 ? i12 : i14) != 0 || constraintWidget3.mResolvedVertical) {
                                                    solverVariable10 = solverVariable8;
                                                    solverVariable11 = solverVariable9;
                                                } else {
                                                    boolean z21 = (constraintWidget3.mListDimensionBehaviors[i13] == dimensionBehaviour2 && (constraintWidget3 instanceof ConstraintWidgetContainer)) ? i13 : i12;
                                                    if (z21) {
                                                        i8 = i12;
                                                    }
                                                    ConstraintWidget constraintWidget9 = constraintWidget3.mParent;
                                                    SolverVariable createObjectVariable8 = constraintWidget9 != null ? linearSystem2.createObjectVariable(constraintWidget9.mBottom) : null;
                                                    ConstraintWidget constraintWidget10 = constraintWidget3.mParent;
                                                    SolverVariable createObjectVariable9 = constraintWidget10 != null ? linearSystem2.createObjectVariable(constraintWidget10.mTop) : null;
                                                    int i25 = constraintWidget3.mBaselineDistance;
                                                    if (i25 > 0 || constraintWidget3.mVisibility == i11) {
                                                        ConstraintAnchor constraintAnchor = constraintWidget3.mBaseline;
                                                        if (constraintAnchor.mTarget != null) {
                                                            linearSystem2.addEquality(solverVariable7, solverVariable9, i25, i11);
                                                            linearSystem2.addEquality(solverVariable7, linearSystem2.createObjectVariable(constraintWidget3.mBaseline.mTarget), constraintWidget3.mBaseline.getMargin(), i11);
                                                            if (z2) {
                                                                linearSystem2.addGreaterThan(createObjectVariable8, linearSystem2.createObjectVariable(constraintWidget3.mBottom), i12, 5);
                                                            }
                                                            z12 = i12;
                                                            boolean z22 = constraintWidget3.isTerminalWidget[i13];
                                                            DimensionBehaviour[] dimensionBehaviourArr4 = constraintWidget3.mListDimensionBehaviors;
                                                            solverVariable10 = solverVariable8;
                                                            solverVariable11 = solverVariable9;
                                                            applyConstraints(linearSystem, false, z2, z3, z22, createObjectVariable9, createObjectVariable8, dimensionBehaviourArr4[i13], z21, constraintWidget3.mTop, constraintWidget3.mBottom, constraintWidget3.f10mY, i8, constraintWidget3.mMinHeight, constraintWidget3.mMaxDimension[i13], constraintWidget3.mVerticalBiasPercent, z18, dimensionBehaviourArr4[0] != dimensionBehaviour, z5, z6, z19, i6, i5, constraintWidget3.mMatchConstraintMinHeight, constraintWidget3.mMatchConstraintMaxHeight, constraintWidget3.mMatchConstraintPercentHeight, z12);
                                                        } else if (constraintWidget3.mVisibility == i11) {
                                                            linearSystem2.addEquality(solverVariable7, solverVariable9, constraintAnchor.getMargin(), i11);
                                                        } else {
                                                            linearSystem2.addEquality(solverVariable7, solverVariable9, i25, i11);
                                                        }
                                                    }
                                                    z12 = z10;
                                                    boolean z222 = constraintWidget3.isTerminalWidget[i13];
                                                    DimensionBehaviour[] dimensionBehaviourArr42 = constraintWidget3.mListDimensionBehaviors;
                                                    solverVariable10 = solverVariable8;
                                                    solverVariable11 = solverVariable9;
                                                    applyConstraints(linearSystem, false, z2, z3, z222, createObjectVariable9, createObjectVariable8, dimensionBehaviourArr42[i13], z21, constraintWidget3.mTop, constraintWidget3.mBottom, constraintWidget3.f10mY, i8, constraintWidget3.mMinHeight, constraintWidget3.mMaxDimension[i13], constraintWidget3.mVerticalBiasPercent, z18, dimensionBehaviourArr42[0] != dimensionBehaviour, z5, z6, z19, i6, i5, constraintWidget3.mMatchConstraintMinHeight, constraintWidget3.mMatchConstraintMaxHeight, constraintWidget3.mMatchConstraintPercentHeight, z12);
                                                }
                                                if (z7) {
                                                    constraintWidget4 = this;
                                                    linearSystem3 = linearSystem;
                                                } else {
                                                    constraintWidget4 = this;
                                                    if (constraintWidget4.mResolvedDimensionRatioSide == 1) {
                                                        float f2 = constraintWidget4.mResolvedDimensionRatio;
                                                        ArrayRow createRow = linearSystem.createRow();
                                                        createRow.variables.put(solverVariable10, -1.0f);
                                                        createRow.variables.put(solverVariable11, 1.0f);
                                                        createRow.variables.put(solverVariable2, f2);
                                                        createRow.variables.put(solverVariable3, -f2);
                                                        linearSystem3 = linearSystem;
                                                        linearSystem3.addConstraint(createRow);
                                                    } else {
                                                        linearSystem3 = linearSystem;
                                                        float f3 = constraintWidget4.mResolvedDimensionRatio;
                                                        ArrayRow createRow2 = linearSystem.createRow();
                                                        createRow2.variables.put(solverVariable2, -1.0f);
                                                        createRow2.variables.put(solverVariable3, 1.0f);
                                                        createRow2.variables.put(solverVariable10, f3);
                                                        createRow2.variables.put(solverVariable11, -f3);
                                                        linearSystem3.addConstraint(createRow2);
                                                    }
                                                }
                                                if (constraintWidget4.mCenter.isConnected()) {
                                                    ConstraintWidget constraintWidget11 = constraintWidget4.mCenter.mTarget.mOwner;
                                                    float radians = (float) Math.toRadians(constraintWidget4.mCircleConstraintAngle + 90.0f);
                                                    int margin = constraintWidget4.mCenter.getMargin();
                                                    ConstraintAnchor.Type type = ConstraintAnchor.Type.LEFT;
                                                    SolverVariable createObjectVariable10 = linearSystem3.createObjectVariable(constraintWidget4.getAnchor(type));
                                                    ConstraintAnchor.Type type2 = ConstraintAnchor.Type.TOP;
                                                    SolverVariable createObjectVariable11 = linearSystem3.createObjectVariable(constraintWidget4.getAnchor(type2));
                                                    ConstraintAnchor.Type type3 = ConstraintAnchor.Type.RIGHT;
                                                    SolverVariable createObjectVariable12 = linearSystem3.createObjectVariable(constraintWidget4.getAnchor(type3));
                                                    ConstraintAnchor.Type type4 = ConstraintAnchor.Type.BOTTOM;
                                                    SolverVariable createObjectVariable13 = linearSystem3.createObjectVariable(constraintWidget4.getAnchor(type4));
                                                    SolverVariable createObjectVariable14 = linearSystem3.createObjectVariable(constraintWidget11.getAnchor(type));
                                                    SolverVariable createObjectVariable15 = linearSystem3.createObjectVariable(constraintWidget11.getAnchor(type2));
                                                    SolverVariable createObjectVariable16 = linearSystem3.createObjectVariable(constraintWidget11.getAnchor(type3));
                                                    SolverVariable createObjectVariable17 = linearSystem3.createObjectVariable(constraintWidget11.getAnchor(type4));
                                                    ArrayRow createRow3 = linearSystem.createRow();
                                                    double d = radians;
                                                    double d2 = margin;
                                                    createRow3.createRowWithAngle(createObjectVariable11, createObjectVariable13, createObjectVariable15, createObjectVariable17, (float) (Math.sin(d) * d2));
                                                    linearSystem3.addConstraint(createRow3);
                                                    ArrayRow createRow4 = linearSystem.createRow();
                                                    createRow4.createRowWithAngle(createObjectVariable10, createObjectVariable12, createObjectVariable14, createObjectVariable16, (float) (Math.cos(d) * d2));
                                                    linearSystem3.addConstraint(createRow4);
                                                }
                                                constraintWidget4.mResolvedHorizontal = false;
                                                constraintWidget4.mResolvedVertical = false;
                                            }
                                        }
                                        linearSystem2 = linearSystem;
                                        solverVariable7 = solverVariable4;
                                        solverVariable8 = solverVariable6;
                                        solverVariable9 = solverVariable5;
                                        i11 = 8;
                                        i12 = 0;
                                        i13 = 1;
                                    } else {
                                        i11 = 8;
                                        i12 = 0;
                                        i13 = 1;
                                        constraintWidget3 = this;
                                        linearSystem2 = linearSystem;
                                        solverVariable7 = solverVariable4;
                                        solverVariable8 = solverVariable6;
                                        solverVariable9 = solverVariable5;
                                    }
                                    i14 = i13;
                                    if ((constraintWidget3.mVerticalResolution != 2 ? i12 : i14) != 0) {
                                    }
                                    solverVariable10 = solverVariable8;
                                    solverVariable11 = solverVariable9;
                                    if (z7) {
                                    }
                                    if (constraintWidget4.mCenter.isConnected()) {
                                    }
                                    constraintWidget4.mResolvedHorizontal = false;
                                    constraintWidget4.mResolvedVertical = false;
                                }
                                dimensionBehaviour = dimensionBehaviour5;
                                dimensionBehaviour2 = dimensionBehaviour3;
                                solverVariable2 = createObjectVariable2;
                                solverVariable3 = createObjectVariable;
                                solverVariable4 = createObjectVariable5;
                                solverVariable5 = createObjectVariable3;
                                solverVariable6 = solverVariable;
                                if (z) {
                                }
                                i14 = i13;
                                if ((constraintWidget3.mVerticalResolution != 2 ? i12 : i14) != 0) {
                                }
                                solverVariable10 = solverVariable8;
                                solverVariable11 = solverVariable9;
                                if (z7) {
                                }
                                if (constraintWidget4.mCenter.isConnected()) {
                                }
                                constraintWidget4.mResolvedHorizontal = false;
                                constraintWidget4.mResolvedVertical = false;
                            }
                        }
                        z8 = false;
                        if (z7) {
                        }
                        if (this.mListDimensionBehaviors[0] != dimensionBehaviour3) {
                        }
                        if (z9) {
                        }
                        z10 = !this.mCenter.isConnected();
                        boolean[] zArr22 = this.mIsInBarrier;
                        z11 = zArr22[0];
                        boolean z192 = zArr22[1];
                        if (this.mHorizontalResolution != 2) {
                            if (z) {
                                dependencyNode = horizontalWidgetRun.start;
                                if (dependencyNode.resolved) {
                                    if (z) {
                                    }
                                }
                            }
                            ConstraintWidget constraintWidget62 = this.mParent;
                            if (constraintWidget62 == null) {
                            }
                            ConstraintWidget constraintWidget72 = this.mParent;
                            if (constraintWidget72 == null) {
                            }
                            boolean z202 = this.isTerminalWidget[0];
                            DimensionBehaviour[] dimensionBehaviourArr32 = this.mListDimensionBehaviors;
                            dimensionBehaviour = dimensionBehaviour5;
                            solverVariable4 = createObjectVariable5;
                            dimensionBehaviour2 = dimensionBehaviour3;
                            solverVariable6 = solverVariable;
                            solverVariable5 = createObjectVariable3;
                            solverVariable2 = createObjectVariable2;
                            solverVariable3 = createObjectVariable;
                            applyConstraints(linearSystem, true, z3, z2, z202, createObjectVariable7, createObjectVariable6, dimensionBehaviourArr32[0], z9, this.mLeft, this.mRight, this.f9mX, i10, this.mMinWidth, this.mMaxDimension[0], this.mHorizontalBiasPercent, z8, dimensionBehaviourArr32[1] != dimensionBehaviour5, z6, z5, z11, i5, i6, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth, this.mMatchConstraintPercentWidth, z10);
                            if (z) {
                            }
                            i14 = i13;
                            if ((constraintWidget3.mVerticalResolution != 2 ? i12 : i14) != 0) {
                            }
                            solverVariable10 = solverVariable8;
                            solverVariable11 = solverVariable9;
                            if (z7) {
                            }
                            if (constraintWidget4.mCenter.isConnected()) {
                            }
                            constraintWidget4.mResolvedHorizontal = false;
                            constraintWidget4.mResolvedVertical = false;
                        }
                        dimensionBehaviour = dimensionBehaviour5;
                        dimensionBehaviour2 = dimensionBehaviour3;
                        solverVariable2 = createObjectVariable2;
                        solverVariable3 = createObjectVariable;
                        solverVariable4 = createObjectVariable5;
                        solverVariable5 = createObjectVariable3;
                        solverVariable6 = solverVariable;
                        if (z) {
                        }
                        i14 = i13;
                        if ((constraintWidget3.mVerticalResolution != 2 ? i12 : i14) != 0) {
                        }
                        solverVariable10 = solverVariable8;
                        solverVariable11 = solverVariable9;
                        if (z7) {
                        }
                        if (constraintWidget4.mCenter.isConnected()) {
                        }
                        constraintWidget4.mResolvedHorizontal = false;
                        constraintWidget4.mResolvedVertical = false;
                    }
                    if (i19 == -1) {
                        if (z16 && !z17) {
                            this.mResolvedDimensionRatioSide = 0;
                        } else if (!z16 && z17) {
                            this.mResolvedDimensionRatioSide = 1;
                            if (i19 == -1) {
                                this.mResolvedDimensionRatio = 1.0f / f;
                            }
                        }
                    }
                    if (this.mResolvedDimensionRatioSide == 0 && (!this.mTop.isConnected() || !this.mBottom.isConnected())) {
                        this.mResolvedDimensionRatioSide = 1;
                    } else if (this.mResolvedDimensionRatioSide == 1 && (!this.mLeft.isConnected() || !this.mRight.isConnected())) {
                        this.mResolvedDimensionRatioSide = 0;
                    }
                    if (this.mResolvedDimensionRatioSide == -1 && (!this.mTop.isConnected() || !this.mBottom.isConnected() || !this.mLeft.isConnected() || !this.mRight.isConnected())) {
                        if (this.mTop.isConnected() && this.mBottom.isConnected()) {
                            this.mResolvedDimensionRatioSide = 0;
                        } else if (this.mLeft.isConnected() && this.mRight.isConnected()) {
                            this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                            this.mResolvedDimensionRatioSide = 1;
                        }
                    }
                    if (this.mResolvedDimensionRatioSide == -1) {
                        int i26 = this.mMatchConstraintMinWidth;
                        if (i26 > 0 && this.mMatchConstraintMinHeight == 0) {
                            this.mResolvedDimensionRatioSide = 0;
                        } else if (i26 == 0 && this.mMatchConstraintMinHeight > 0) {
                            this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                            this.mResolvedDimensionRatioSide = 1;
                        }
                    }
                    i7 = i18;
                    i5 = i21;
                    i6 = i22;
                    i8 = i20;
                    z7 = true;
                    int[] iArr2 = this.mResolvedMatchConstraintDefault;
                    iArr2[0] = i5;
                    iArr2[1] = i6;
                    if (z7) {
                    }
                    z8 = false;
                    if (z7) {
                    }
                    if (this.mListDimensionBehaviors[0] != dimensionBehaviour3) {
                    }
                    if (z9) {
                    }
                    z10 = !this.mCenter.isConnected();
                    boolean[] zArr222 = this.mIsInBarrier;
                    z11 = zArr222[0];
                    boolean z1922 = zArr222[1];
                    if (this.mHorizontalResolution != 2) {
                    }
                    dimensionBehaviour = dimensionBehaviour5;
                    dimensionBehaviour2 = dimensionBehaviour3;
                    solverVariable2 = createObjectVariable2;
                    solverVariable3 = createObjectVariable;
                    solverVariable4 = createObjectVariable5;
                    solverVariable5 = createObjectVariable3;
                    solverVariable6 = solverVariable;
                    if (z) {
                    }
                    i14 = i13;
                    if ((constraintWidget3.mVerticalResolution != 2 ? i12 : i14) != 0) {
                    }
                    solverVariable10 = solverVariable8;
                    solverVariable11 = solverVariable9;
                    if (z7) {
                    }
                    if (constraintWidget4.mCenter.isConnected()) {
                    }
                    constraintWidget4.mResolvedHorizontal = false;
                    constraintWidget4.mResolvedVertical = false;
                }
            } else {
                solverVariable = createObjectVariable4;
            }
            i5 = i21;
            i6 = i22;
            i7 = i18;
            i8 = i20;
            z7 = false;
            int[] iArr22 = this.mResolvedMatchConstraintDefault;
            iArr22[0] = i5;
            iArr22[1] = i6;
            if (z7) {
            }
            z8 = false;
            if (z7) {
            }
            if (this.mListDimensionBehaviors[0] != dimensionBehaviour3) {
            }
            if (z9) {
            }
            z10 = !this.mCenter.isConnected();
            boolean[] zArr2222 = this.mIsInBarrier;
            z11 = zArr2222[0];
            boolean z19222 = zArr2222[1];
            if (this.mHorizontalResolution != 2) {
            }
            dimensionBehaviour = dimensionBehaviour5;
            dimensionBehaviour2 = dimensionBehaviour3;
            solverVariable2 = createObjectVariable2;
            solverVariable3 = createObjectVariable;
            solverVariable4 = createObjectVariable5;
            solverVariable5 = createObjectVariable3;
            solverVariable6 = solverVariable;
            if (z) {
            }
            i14 = i13;
            if ((constraintWidget3.mVerticalResolution != 2 ? i12 : i14) != 0) {
            }
            solverVariable10 = solverVariable8;
            solverVariable11 = solverVariable9;
            if (z7) {
            }
            if (constraintWidget4.mCenter.isConnected()) {
            }
            constraintWidget4.mResolvedHorizontal = false;
            constraintWidget4.mResolvedVertical = false;
        }
        z2 = false;
        z3 = false;
        if (this.mVisibility == 8) {
            size = this.mAnchors.size();
            i16 = 0;
            while (true) {
                if (i16 < size) {
                }
                i16++;
            }
            if (!z13) {
            }
        }
        z4 = this.mResolvedHorizontal;
        if (!z4) {
        }
        if (z4) {
        }
        if (this.mResolvedVertical) {
        }
        if (this.mResolvedHorizontal) {
            this.mResolvedHorizontal = false;
            this.mResolvedVertical = false;
            return;
        }
        if (z) {
            dependencyNode2 = horizontalWidgetRun2.start;
            if (dependencyNode2.resolved) {
                linearSystem.addEquality(createObjectVariable, dependencyNode2.value);
                linearSystem.addEquality(createObjectVariable2, this.mHorizontalRun.end.value);
                linearSystem.addEquality(createObjectVariable3, this.mVerticalRun.start.value);
                linearSystem.addEquality(createObjectVariable4, this.mVerticalRun.end.value);
                linearSystem.addEquality(createObjectVariable5, this.mVerticalRun.baseline.value);
                if (this.mParent != null) {
                }
                this.mResolvedHorizontal = false;
                this.mResolvedVertical = false;
                return;
            }
        }
        if (this.mParent == null) {
        }
        i = this.mWidth;
        i2 = this.mMinWidth;
        if (i >= i2) {
        }
        i3 = this.mHeight;
        i4 = this.mMinHeight;
        if (i3 >= i4) {
        }
        DimensionBehaviour[] dimensionBehaviourArr22 = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour42 = dimensionBehaviourArr22[0];
        int i182 = i2;
        DimensionBehaviour dimensionBehaviour52 = DimensionBehaviour.MATCH_CONSTRAINT;
        if (dimensionBehaviour42 == dimensionBehaviour52) {
        }
        DimensionBehaviour dimensionBehaviour62 = dimensionBehaviourArr22[1];
        if (dimensionBehaviour62 == dimensionBehaviour52) {
        }
        int i192 = this.mDimensionRatioSide;
        this.mResolvedDimensionRatioSide = i192;
        f = this.mDimensionRatio;
        this.mResolvedDimensionRatio = f;
        int i202 = i4;
        int i212 = this.mMatchConstraintDefaultWidth;
        int i222 = this.mMatchConstraintDefaultHeight;
        if (f <= 0.0f) {
        }
        i5 = i212;
        i6 = i222;
        i7 = i182;
        i8 = i202;
        z7 = false;
        int[] iArr222 = this.mResolvedMatchConstraintDefault;
        iArr222[0] = i5;
        iArr222[1] = i6;
        if (z7) {
        }
        z8 = false;
        if (z7) {
        }
        if (this.mListDimensionBehaviors[0] != dimensionBehaviour3) {
        }
        if (z9) {
        }
        z10 = !this.mCenter.isConnected();
        boolean[] zArr22222 = this.mIsInBarrier;
        z11 = zArr22222[0];
        boolean z192222 = zArr22222[1];
        if (this.mHorizontalResolution != 2) {
        }
        dimensionBehaviour = dimensionBehaviour52;
        dimensionBehaviour2 = dimensionBehaviour3;
        solverVariable2 = createObjectVariable2;
        solverVariable3 = createObjectVariable;
        solverVariable4 = createObjectVariable5;
        solverVariable5 = createObjectVariable3;
        solverVariable6 = solverVariable;
        if (z) {
        }
        i14 = i13;
        if ((constraintWidget3.mVerticalResolution != 2 ? i12 : i14) != 0) {
        }
        solverVariable10 = solverVariable8;
        solverVariable11 = solverVariable9;
        if (z7) {
        }
        if (constraintWidget4.mCenter.isConnected()) {
        }
        constraintWidget4.mResolvedHorizontal = false;
        constraintWidget4.mResolvedVertical = false;
    }

    public boolean allowedInBarrier() {
        return this.mVisibility != 8;
    }

    public final void connect(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i) {
        if (constraintAnchor.mOwner == this) {
            connect(constraintAnchor.mType, constraintAnchor2.mOwner, constraintAnchor2.mType, i);
        }
    }

    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        this.mHorizontalResolution = constraintWidget.mHorizontalResolution;
        this.mVerticalResolution = constraintWidget.mVerticalResolution;
        this.mMatchConstraintDefaultWidth = constraintWidget.mMatchConstraintDefaultWidth;
        this.mMatchConstraintDefaultHeight = constraintWidget.mMatchConstraintDefaultHeight;
        int[] iArr = this.mResolvedMatchConstraintDefault;
        int[] iArr2 = constraintWidget.mResolvedMatchConstraintDefault;
        iArr[0] = iArr2[0];
        iArr[1] = iArr2[1];
        this.mMatchConstraintMinWidth = constraintWidget.mMatchConstraintMinWidth;
        this.mMatchConstraintMaxWidth = constraintWidget.mMatchConstraintMaxWidth;
        this.mMatchConstraintMinHeight = constraintWidget.mMatchConstraintMinHeight;
        this.mMatchConstraintMaxHeight = constraintWidget.mMatchConstraintMaxHeight;
        this.mMatchConstraintPercentHeight = constraintWidget.mMatchConstraintPercentHeight;
        this.mResolvedDimensionRatioSide = constraintWidget.mResolvedDimensionRatioSide;
        this.mResolvedDimensionRatio = constraintWidget.mResolvedDimensionRatio;
        int[] iArr3 = constraintWidget.mMaxDimension;
        this.mMaxDimension = Arrays.copyOf(iArr3, iArr3.length);
        this.mCircleConstraintAngle = constraintWidget.mCircleConstraintAngle;
        this.mHasBaseline = constraintWidget.mHasBaseline;
        this.mInPlaceholder = constraintWidget.mInPlaceholder;
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mListDimensionBehaviors = (DimensionBehaviour[]) Arrays.copyOf(this.mListDimensionBehaviors, 2);
        this.mParent = this.mParent == null ? null : hashMap.get(constraintWidget.mParent);
        this.mWidth = constraintWidget.mWidth;
        this.mHeight = constraintWidget.mHeight;
        this.mDimensionRatio = constraintWidget.mDimensionRatio;
        this.mDimensionRatioSide = constraintWidget.mDimensionRatioSide;
        this.f9mX = constraintWidget.f9mX;
        this.f10mY = constraintWidget.f10mY;
        this.mBaselineDistance = constraintWidget.mBaselineDistance;
        this.mMinWidth = constraintWidget.mMinWidth;
        this.mMinHeight = constraintWidget.mMinHeight;
        this.mHorizontalBiasPercent = constraintWidget.mHorizontalBiasPercent;
        this.mVerticalBiasPercent = constraintWidget.mVerticalBiasPercent;
        this.mCompanionWidget = constraintWidget.mCompanionWidget;
        this.mVisibility = constraintWidget.mVisibility;
        this.mAnimated = constraintWidget.mAnimated;
        this.mDebugName = constraintWidget.mDebugName;
        this.mType = constraintWidget.mType;
        this.mHorizontalChainStyle = constraintWidget.mHorizontalChainStyle;
        this.mVerticalChainStyle = constraintWidget.mVerticalChainStyle;
        float[] fArr = this.mWeight;
        float[] fArr2 = constraintWidget.mWeight;
        fArr[0] = fArr2[0];
        fArr[1] = fArr2[1];
        ConstraintWidget[] constraintWidgetArr = this.mListNextMatchConstraintsWidget;
        ConstraintWidget[] constraintWidgetArr2 = constraintWidget.mListNextMatchConstraintsWidget;
        constraintWidgetArr[0] = constraintWidgetArr2[0];
        constraintWidgetArr[1] = constraintWidgetArr2[1];
        ConstraintWidget[] constraintWidgetArr3 = this.mNextChainWidget;
        ConstraintWidget[] constraintWidgetArr4 = constraintWidget.mNextChainWidget;
        constraintWidgetArr3[0] = constraintWidgetArr4[0];
        constraintWidgetArr3[1] = constraintWidgetArr4[1];
        ConstraintWidget constraintWidget2 = constraintWidget.mHorizontalNextWidget;
        this.mHorizontalNextWidget = constraintWidget2 == null ? null : hashMap.get(constraintWidget2);
        ConstraintWidget constraintWidget3 = constraintWidget.mVerticalNextWidget;
        this.mVerticalNextWidget = constraintWidget3 != null ? hashMap.get(constraintWidget3) : null;
    }

    public final void createObjectVariables(LinearSystem linearSystem) {
        linearSystem.createObjectVariable(this.mLeft);
        linearSystem.createObjectVariable(this.mTop);
        linearSystem.createObjectVariable(this.mRight);
        linearSystem.createObjectVariable(this.mBottom);
        if (this.mBaselineDistance > 0) {
            linearSystem.createObjectVariable(this.mBaseline);
        }
    }

    public final void ensureWidgetRuns() {
        if (this.mHorizontalRun == null) {
            this.mHorizontalRun = new HorizontalWidgetRun(this);
        }
        if (this.mVerticalRun == null) {
            this.mVerticalRun = new VerticalWidgetRun(this);
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        switch (type) {
            case EF0:
                return null;
            case LEFT:
                return this.mLeft;
            case TOP:
                return this.mTop;
            case RIGHT:
                return this.mRight;
            case BOTTOM:
                return this.mBottom;
            case BASELINE:
                return this.mBaseline;
            case CENTER:
                return this.mCenter;
            case CENTER_X:
                return this.mCenterX;
            case CENTER_Y:
                return this.mCenterY;
            default:
                throw new AssertionError(type.name());
        }
    }

    public final int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public final float getBiasPercent(int i) {
        if (i == 0) {
            return this.mHorizontalBiasPercent;
        }
        if (i == 1) {
            return this.mVerticalBiasPercent;
        }
        return -1.0f;
    }

    public final int getBottom() {
        return getY() + this.mHeight;
    }

    public final Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public final String getDebugName() {
        return this.mDebugName;
    }

    public final DimensionBehaviour getDimensionBehaviour(int i) {
        if (i == 0) {
            return this.mListDimensionBehaviors[0];
        }
        if (i == 1) {
            return this.mListDimensionBehaviors[1];
        }
        return null;
    }

    public final int getDimensionRatioSide() {
        return this.mDimensionRatioSide;
    }

    public final int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public final float getHorizontalBiasPercent() {
        return this.mHorizontalBiasPercent;
    }

    public final int getHorizontalChainStyle() {
        return this.mHorizontalChainStyle;
    }

    public final int getLastHorizontalMeasureSpec() {
        return this.mLastHorizontalMeasureSpec;
    }

    public final int getLastVerticalMeasureSpec() {
        return this.mLastVerticalMeasureSpec;
    }

    public final int getMaxHeight() {
        return this.mMaxDimension[1];
    }

    public final int getMaxWidth() {
        return this.mMaxDimension[0];
    }

    public final int getMinHeight() {
        return this.mMinHeight;
    }

    public final int getMinWidth() {
        return this.mMinWidth;
    }

    public final ConstraintWidget getNextChainMember(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i != 0) {
            if (i == 1 && (constraintAnchor2 = (constraintAnchor = this.mBottom).mTarget) != null && constraintAnchor2.mTarget == constraintAnchor) {
                return constraintAnchor2.mOwner;
            }
            return null;
        }
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return null;
        }
        return constraintAnchor4.mOwner;
    }

    public final ConstraintWidget getPreviousChainMember(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i != 0) {
            if (i == 1 && (constraintAnchor2 = (constraintAnchor = this.mTop).mTarget) != null && constraintAnchor2.mTarget == constraintAnchor) {
                return constraintAnchor2.mOwner;
            }
            return null;
        }
        ConstraintAnchor constraintAnchor3 = this.mLeft;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return null;
        }
        return constraintAnchor4.mOwner;
    }

    public final int getRight() {
        return getX() + this.mWidth;
    }

    public void getSceneString(StringBuilder sb) {
        sb.append("  " + this.stringId + ":{\n");
        StringBuilder sb2 = new StringBuilder("    actualWidth:");
        sb2.append(this.mWidth);
        sb.append(sb2.toString());
        sb.append("\n");
        sb.append("    actualHeight:" + this.mHeight);
        sb.append("\n");
        sb.append("    actualLeft:" + this.f9mX);
        sb.append("\n");
        sb.append("    actualTop:" + this.f10mY);
        sb.append("\n");
        getSceneString(sb, "left", this.mLeft);
        getSceneString(sb, "top", this.mTop);
        getSceneString(sb, "right", this.mRight);
        getSceneString(sb, "bottom", this.mBottom);
        getSceneString(sb, "baseline", this.mBaseline);
        getSceneString(sb, "centerX", this.mCenterX);
        getSceneString(sb, "centerY", this.mCenterY);
        int i = this.mWidth;
        int i2 = this.mMinWidth;
        int i3 = this.mMaxDimension[0];
        int i4 = this.mMatchConstraintMinWidth;
        int i5 = this.mMatchConstraintDefaultWidth;
        float f = this.mMatchConstraintPercentWidth;
        DimensionBehaviour dimensionBehaviour = this.mListDimensionBehaviors[0];
        float f2 = this.mWeight[0];
        getSceneString(sb, "    width", i, i2, i3, i4, i5, f, dimensionBehaviour);
        int i6 = this.mHeight;
        int i7 = this.mMinHeight;
        int i8 = this.mMaxDimension[1];
        int i9 = this.mMatchConstraintMinHeight;
        int i10 = this.mMatchConstraintDefaultHeight;
        float f3 = this.mMatchConstraintPercentHeight;
        DimensionBehaviour dimensionBehaviour2 = this.mListDimensionBehaviors[1];
        float f4 = this.mWeight[1];
        getSceneString(sb, "    height", i6, i7, i8, i9, i10, f3, dimensionBehaviour2);
        float f5 = this.mDimensionRatio;
        int i11 = this.mDimensionRatioSide;
        if (f5 != 0.0f) {
            sb.append("    dimensionRatio");
            sb.append(" :  [");
            sb.append(f5);
            sb.append(",");
            sb.append(i11);
            sb.append("");
            sb.append("],\n");
        }
        serializeAttribute(sb, "    horizontalBias", this.mHorizontalBiasPercent, 0.5f);
        serializeAttribute(sb, "    verticalBias", this.mVerticalBiasPercent, 0.5f);
        serializeAttribute(this.mHorizontalChainStyle, 0, "    horizontalChainStyle", sb);
        serializeAttribute(this.mVerticalChainStyle, 0, "    verticalChainStyle", sb);
        sb.append("  }");
    }

    public final float getVerticalBiasPercent() {
        return this.mVerticalBiasPercent;
    }

    public final int getVerticalChainStyle() {
        return this.mVerticalChainStyle;
    }

    public final int getVisibility() {
        return this.mVisibility;
    }

    public final int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public final int getX() {
        ConstraintWidget constraintWidget = this.mParent;
        return (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) ? this.f9mX : ((ConstraintWidgetContainer) constraintWidget).mPaddingLeft + this.f9mX;
    }

    public final int getY() {
        ConstraintWidget constraintWidget = this.mParent;
        return (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) ? this.f10mY : ((ConstraintWidgetContainer) constraintWidget).mPaddingTop + this.f10mY;
    }

    public final boolean hasBaseline() {
        return this.mHasBaseline;
    }

    public final boolean hasDanglingDimension(int i) {
        if (i == 0) {
            return (this.mLeft.mTarget != null ? 1 : 0) + (this.mRight.mTarget != null ? 1 : 0) < 2;
        }
        return ((this.mTop.mTarget != null ? 1 : 0) + (this.mBottom.mTarget != null ? 1 : 0)) + (this.mBaseline.mTarget != null ? 1 : 0) < 2;
    }

    public final boolean hasDimensionOverride() {
        return (this.mWidthOverride == -1 && this.mHeightOverride == -1) ? false : true;
    }

    public final boolean hasResolvedTargets(int i, int i2) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i == 0) {
            ConstraintAnchor constraintAnchor3 = this.mLeft.mTarget;
            if (constraintAnchor3 != null && constraintAnchor3.hasFinalValue() && (constraintAnchor2 = this.mRight.mTarget) != null && constraintAnchor2.hasFinalValue()) {
                return (this.mRight.mTarget.getFinalValue() - this.mRight.getMargin()) - (this.mLeft.getMargin() + this.mLeft.mTarget.getFinalValue()) >= i2;
            }
        } else {
            ConstraintAnchor constraintAnchor4 = this.mTop.mTarget;
            if (constraintAnchor4 != null && constraintAnchor4.hasFinalValue() && (constraintAnchor = this.mBottom.mTarget) != null && constraintAnchor.hasFinalValue()) {
                return (this.mBottom.mTarget.getFinalValue() - this.mBottom.getMargin()) - (this.mTop.getMargin() + this.mTop.mTarget.getFinalValue()) >= i2;
            }
        }
        return false;
    }

    public final void immediateConnect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i, int i2) {
        getAnchor(type).connect(constraintWidget.getAnchor(type2), i, i2, true);
    }

    public final boolean isHorizontalSolvingPassDone() {
        return this.mHorizontalSolvingPass;
    }

    public final boolean isInBarrier(int i) {
        return this.mIsInBarrier[i];
    }

    public final boolean isInHorizontalChain() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return constraintAnchor4 != null && constraintAnchor4.mTarget == constraintAnchor3;
    }

    public final boolean isInPlaceholder() {
        return this.mInPlaceholder;
    }

    public final boolean isInVerticalChain() {
        ConstraintAnchor constraintAnchor = this.mTop;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mBottom;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return constraintAnchor4 != null && constraintAnchor4.mTarget == constraintAnchor3;
    }

    public final boolean isInVirtualLayout() {
        return this.mInVirtualLayout;
    }

    public final boolean isMeasureRequested() {
        return this.mMeasureRequested && this.mVisibility != 8;
    }

    public boolean isResolvedHorizontally() {
        return this.mResolvedHorizontal || (this.mLeft.hasFinalValue() && this.mRight.hasFinalValue());
    }

    public boolean isResolvedVertically() {
        return this.mResolvedVertical || (this.mTop.hasFinalValue() && this.mBottom.hasFinalValue());
    }

    public final boolean isVerticalSolvingPassDone() {
        return this.mVerticalSolvingPass;
    }

    public final void markHorizontalSolvingPassDone() {
        this.mHorizontalSolvingPass = true;
    }

    public final void markVerticalSolvingPassDone() {
        this.mVerticalSolvingPass = true;
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mCircleConstraintAngle = Float.NaN;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.f9mX = 0;
        this.f10mY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mHorizontalBiasPercent = 0.5f;
        this.mVerticalBiasPercent = 0.5f;
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        dimensionBehaviourArr[0] = dimensionBehaviour;
        dimensionBehaviourArr[1] = dimensionBehaviour;
        this.mCompanionWidget = null;
        this.mVisibility = 0;
        this.mType = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        float[] fArr = this.mWeight;
        fArr[0] = -1.0f;
        fArr[1] = -1.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        int[] iArr = this.mMaxDimension;
        iArr[0] = Integer.MAX_VALUE;
        iArr[1] = Integer.MAX_VALUE;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
        this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        boolean[] zArr = this.isTerminalWidget;
        zArr[0] = true;
        zArr[1] = true;
        this.mInVirtualLayout = false;
        boolean[] zArr2 = this.mIsInBarrier;
        zArr2[0] = false;
        zArr2[1] = false;
        this.mMeasureRequested = true;
        int[] iArr2 = this.mResolvedMatchConstraintDefault;
        iArr2[0] = 0;
        iArr2[1] = 0;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
    }

    public final void resetAnchors() {
        ConstraintWidget constraintWidget = this.mParent;
        if (constraintWidget != null && (constraintWidget instanceof ConstraintWidgetContainer)) {
            ((ConstraintWidgetContainer) constraintWidget).getClass();
        }
        int size = this.mAnchors.size();
        for (int i = 0; i < size; i++) {
            this.mAnchors.get(i).reset();
        }
    }

    public final void resetFinalResolution() {
        this.mResolvedHorizontal = false;
        this.mResolvedVertical = false;
        this.mHorizontalSolvingPass = false;
        this.mVerticalSolvingPass = false;
        int size = this.mAnchors.size();
        for (int i = 0; i < size; i++) {
            this.mAnchors.get(i).resetFinalResolution();
        }
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable();
        this.mTop.resetSolverVariable();
        this.mRight.resetSolverVariable();
        this.mBottom.resetSolverVariable();
        this.mBaseline.resetSolverVariable();
        this.mCenter.resetSolverVariable();
        this.mCenterX.resetSolverVariable();
        this.mCenterY.resetSolverVariable();
    }

    public final void setAnimated() {
        this.mAnimated = true;
    }

    public final void setBaselineDistance(int i) {
        this.mBaselineDistance = i;
        this.mHasBaseline = i > 0;
    }

    public final void setCompanionWidget(Object obj) {
        this.mCompanionWidget = obj;
    }

    public final void setDebugName(String str) {
        this.mDebugName = str;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x0086 -> B:31:0x0087). Please report as a decompilation issue!!! */
    public final void setDimensionRatio(String str) {
        float f;
        int i = 0;
        if (str == null || str.length() == 0) {
            this.mDimensionRatio = 0.0f;
            return;
        }
        int length = str.length();
        int indexOf = str.indexOf(44);
        int i2 = -1;
        if (indexOf > 0 && indexOf < length - 1) {
            String substring = str.substring(0, indexOf);
            i2 = substring.equalsIgnoreCase("W") ? 0 : substring.equalsIgnoreCase("H") ? 1 : -1;
            r3 = indexOf + 1;
        }
        int indexOf2 = str.indexOf(58);
        if (indexOf2 < 0 || indexOf2 >= length - 1) {
            String substring2 = str.substring(r3);
            if (substring2.length() > 0) {
                f = Float.parseFloat(substring2);
            }
            f = i;
        } else {
            String substring3 = str.substring(r3, indexOf2);
            String substring4 = str.substring(indexOf2 + 1);
            if (substring3.length() > 0 && substring4.length() > 0) {
                float parseFloat = Float.parseFloat(substring3);
                float parseFloat2 = Float.parseFloat(substring4);
                if (parseFloat > 0.0f && parseFloat2 > 0.0f) {
                    f = i2 == 1 ? Math.abs(parseFloat2 / parseFloat) : Math.abs(parseFloat / parseFloat2);
                }
            }
            f = i;
        }
        i = (f > i ? 1 : (f == i ? 0 : -1));
        if (i > 0) {
            this.mDimensionRatio = f;
            this.mDimensionRatioSide = i2;
        }
    }

    public final void setFinalBaseline(int i) {
        if (this.mHasBaseline) {
            int i2 = i - this.mBaselineDistance;
            int i3 = this.mHeight + i2;
            this.f10mY = i2;
            this.mTop.setFinalValue(i2);
            this.mBottom.setFinalValue(i3);
            this.mBaseline.setFinalValue(i);
            this.mResolvedVertical = true;
        }
    }

    public final void setFinalHorizontal(int i, int i2) {
        if (this.mResolvedHorizontal) {
            return;
        }
        this.mLeft.setFinalValue(i);
        this.mRight.setFinalValue(i2);
        this.f9mX = i;
        this.mWidth = i2 - i;
        this.mResolvedHorizontal = true;
    }

    public final void setFinalLeft() {
        this.mLeft.setFinalValue(0);
        this.f9mX = 0;
    }

    public final void setFinalTop() {
        this.mTop.setFinalValue(0);
        this.f10mY = 0;
    }

    public final void setFinalVertical(int i, int i2) {
        if (this.mResolvedVertical) {
            return;
        }
        this.mTop.setFinalValue(i);
        this.mBottom.setFinalValue(i2);
        this.f10mY = i;
        this.mHeight = i2 - i;
        if (this.mHasBaseline) {
            this.mBaseline.setFinalValue(i + this.mBaselineDistance);
        }
        this.mResolvedVertical = true;
    }

    public final void setHasBaseline(boolean z) {
        this.mHasBaseline = z;
    }

    public final void setHeight(int i) {
        this.mHeight = i;
        int i2 = this.mMinHeight;
        if (i < i2) {
            this.mHeight = i2;
        }
    }

    public final void setHorizontalBiasPercent(float f) {
        this.mHorizontalBiasPercent = f;
    }

    public final void setHorizontalChainStyle(int i) {
        this.mHorizontalChainStyle = i;
    }

    public final void setHorizontalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[0] = dimensionBehaviour;
    }

    protected final void setInBarrier(int i, boolean z) {
        this.mIsInBarrier[i] = z;
    }

    public final void setInPlaceholder() {
        this.mInPlaceholder = true;
    }

    public final void setInVirtualLayout() {
        this.mInVirtualLayout = true;
    }

    public final void setLastMeasureSpec(int i, int i2) {
        this.mLastHorizontalMeasureSpec = i;
        this.mLastVerticalMeasureSpec = i2;
        this.mMeasureRequested = false;
    }

    public final void setMaxHeight(int i) {
        this.mMaxDimension[1] = i;
    }

    public final void setMaxWidth(int i) {
        this.mMaxDimension[0] = i;
    }

    public final void setMeasureRequested() {
        this.mMeasureRequested = true;
    }

    public final void setMinHeight(int i) {
        if (i < 0) {
            this.mMinHeight = 0;
        } else {
            this.mMinHeight = i;
        }
    }

    public final void setMinWidth(int i) {
        if (i < 0) {
            this.mMinWidth = 0;
        } else {
            this.mMinWidth = i;
        }
    }

    public final void setOrigin(int i, int i2) {
        this.f9mX = i;
        this.f10mY = i2;
    }

    public final void setVerticalBiasPercent(float f) {
        this.mVerticalBiasPercent = f;
    }

    public final void setVerticalChainStyle(int i) {
        this.mVerticalChainStyle = i;
    }

    public final void setVerticalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[1] = dimensionBehaviour;
    }

    public final void setVisibility(int i) {
        this.mVisibility = i;
    }

    public final void setWidth(int i) {
        this.mWidth = i;
        int i2 = this.mMinWidth;
        if (i < i2) {
            this.mWidth = i2;
        }
    }

    public final void setWrapBehaviorInParent(int i) {
        if (i < 0 || i > 3) {
            return;
        }
        this.mWrapBehaviorInParent = i;
    }

    public final void setX(int i) {
        this.f9mX = i;
    }

    public final void setY(int i) {
        this.f10mY = i;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (this.mType != null) {
            str = "type: " + this.mType + " ";
        } else {
            str = "";
        }
        sb.append(str);
        if (this.mDebugName != null) {
            str2 = "id: " + this.mDebugName + " ";
        }
        sb.append(str2);
        sb.append("(");
        sb.append(this.f9mX);
        sb.append(", ");
        sb.append(this.f10mY);
        sb.append(") - (");
        sb.append(this.mWidth);
        sb.append(" x ");
        sb.append(this.mHeight);
        sb.append(")");
        return sb.toString();
    }

    public void updateFromRuns(boolean z, boolean z2) {
        int i;
        int i2;
        boolean isResolved = z & this.mHorizontalRun.isResolved();
        boolean isResolved2 = z2 & this.mVerticalRun.isResolved();
        HorizontalWidgetRun horizontalWidgetRun = this.mHorizontalRun;
        int i3 = horizontalWidgetRun.start.value;
        VerticalWidgetRun verticalWidgetRun = this.mVerticalRun;
        int i4 = verticalWidgetRun.start.value;
        int i5 = horizontalWidgetRun.end.value;
        int i6 = verticalWidgetRun.end.value;
        int i7 = i6 - i4;
        if (i5 - i3 < 0 || i7 < 0 || i3 == Integer.MIN_VALUE || i3 == Integer.MAX_VALUE || i4 == Integer.MIN_VALUE || i4 == Integer.MAX_VALUE || i5 == Integer.MIN_VALUE || i5 == Integer.MAX_VALUE || i6 == Integer.MIN_VALUE || i6 == Integer.MAX_VALUE) {
            i5 = 0;
            i3 = 0;
            i6 = 0;
            i4 = 0;
        }
        int i8 = i5 - i3;
        int i9 = i6 - i4;
        if (isResolved) {
            this.f9mX = i3;
        }
        if (isResolved2) {
            this.f10mY = i4;
        }
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        if (isResolved) {
            if (this.mListDimensionBehaviors[0] == dimensionBehaviour && i8 < (i2 = this.mWidth)) {
                i8 = i2;
            }
            this.mWidth = i8;
            int i10 = this.mMinWidth;
            if (i8 < i10) {
                this.mWidth = i10;
            }
        }
        if (isResolved2) {
            if (this.mListDimensionBehaviors[1] == dimensionBehaviour && i9 < (i = this.mHeight)) {
                i9 = i;
            }
            this.mHeight = i9;
            int i11 = this.mMinHeight;
            if (i9 < i11) {
                this.mHeight = i11;
            }
        }
    }

    public void updateFromSolver(LinearSystem linearSystem, boolean z) {
        int i;
        int i2;
        VerticalWidgetRun verticalWidgetRun;
        HorizontalWidgetRun horizontalWidgetRun;
        ConstraintAnchor constraintAnchor = this.mLeft;
        linearSystem.getClass();
        int objectVariableValue = LinearSystem.getObjectVariableValue(constraintAnchor);
        int objectVariableValue2 = LinearSystem.getObjectVariableValue(this.mTop);
        int objectVariableValue3 = LinearSystem.getObjectVariableValue(this.mRight);
        int objectVariableValue4 = LinearSystem.getObjectVariableValue(this.mBottom);
        if (z && (horizontalWidgetRun = this.mHorizontalRun) != null) {
            DependencyNode dependencyNode = horizontalWidgetRun.start;
            if (dependencyNode.resolved) {
                DependencyNode dependencyNode2 = horizontalWidgetRun.end;
                if (dependencyNode2.resolved) {
                    objectVariableValue = dependencyNode.value;
                    objectVariableValue3 = dependencyNode2.value;
                }
            }
        }
        if (z && (verticalWidgetRun = this.mVerticalRun) != null) {
            DependencyNode dependencyNode3 = verticalWidgetRun.start;
            if (dependencyNode3.resolved) {
                DependencyNode dependencyNode4 = verticalWidgetRun.end;
                if (dependencyNode4.resolved) {
                    objectVariableValue2 = dependencyNode3.value;
                    objectVariableValue4 = dependencyNode4.value;
                }
            }
        }
        int i3 = objectVariableValue4 - objectVariableValue2;
        if (objectVariableValue3 - objectVariableValue < 0 || i3 < 0 || objectVariableValue == Integer.MIN_VALUE || objectVariableValue == Integer.MAX_VALUE || objectVariableValue2 == Integer.MIN_VALUE || objectVariableValue2 == Integer.MAX_VALUE || objectVariableValue3 == Integer.MIN_VALUE || objectVariableValue3 == Integer.MAX_VALUE || objectVariableValue4 == Integer.MIN_VALUE || objectVariableValue4 == Integer.MAX_VALUE) {
            objectVariableValue = 0;
            objectVariableValue2 = 0;
            objectVariableValue3 = 0;
            objectVariableValue4 = 0;
        }
        int i4 = objectVariableValue3 - objectVariableValue;
        int i5 = objectVariableValue4 - objectVariableValue2;
        this.f9mX = objectVariableValue;
        this.f10mY = objectVariableValue2;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.FIXED;
        if (dimensionBehaviour == dimensionBehaviour2 && i4 < (i2 = this.mWidth)) {
            i4 = i2;
        }
        if (dimensionBehaviourArr[1] == dimensionBehaviour2 && i5 < (i = this.mHeight)) {
            i5 = i;
        }
        this.mWidth = i4;
        this.mHeight = i5;
        int i6 = this.mMinHeight;
        if (i5 < i6) {
            this.mHeight = i6;
        }
        int i7 = this.mMinWidth;
        if (i4 < i7) {
            this.mWidth = i7;
        }
        int i8 = this.mMatchConstraintMaxWidth;
        DimensionBehaviour dimensionBehaviour3 = DimensionBehaviour.MATCH_CONSTRAINT;
        if (i8 > 0 && dimensionBehaviour == dimensionBehaviour3) {
            this.mWidth = Math.min(this.mWidth, i8);
        }
        int i9 = this.mMatchConstraintMaxHeight;
        if (i9 > 0 && this.mListDimensionBehaviors[1] == dimensionBehaviour3) {
            this.mHeight = Math.min(this.mHeight, i9);
        }
        int i10 = this.mWidth;
        if (i4 != i10) {
            this.mWidthOverride = i10;
        }
        int i11 = this.mHeight;
        if (i5 != i11) {
            this.mHeightOverride = i11;
        }
    }

    public final void connect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i) {
        boolean z;
        ConstraintAnchor.Type type3 = ConstraintAnchor.Type.CENTER;
        ConstraintAnchor.Type type4 = ConstraintAnchor.Type.CENTER_Y;
        ConstraintAnchor.Type type5 = ConstraintAnchor.Type.CENTER_X;
        ConstraintAnchor.Type type6 = ConstraintAnchor.Type.LEFT;
        ConstraintAnchor.Type type7 = ConstraintAnchor.Type.TOP;
        ConstraintAnchor.Type type8 = ConstraintAnchor.Type.RIGHT;
        ConstraintAnchor.Type type9 = ConstraintAnchor.Type.BOTTOM;
        if (type == type3) {
            if (type2 != type3) {
                if (type2 == type6 || type2 == type8) {
                    connect(type6, constraintWidget, type2, 0);
                    connect(type8, constraintWidget, type2, 0);
                    getAnchor(type3).connect(constraintWidget.getAnchor(type2), 0);
                    return;
                } else {
                    if (type2 == type7 || type2 == type9) {
                        connect(type7, constraintWidget, type2, 0);
                        connect(type9, constraintWidget, type2, 0);
                        getAnchor(type3).connect(constraintWidget.getAnchor(type2), 0);
                        return;
                    }
                    return;
                }
            }
            ConstraintAnchor anchor = getAnchor(type6);
            ConstraintAnchor anchor2 = getAnchor(type8);
            ConstraintAnchor anchor3 = getAnchor(type7);
            ConstraintAnchor anchor4 = getAnchor(type9);
            boolean z2 = true;
            if ((anchor == null || !anchor.isConnected()) && (anchor2 == null || !anchor2.isConnected())) {
                connect(type6, constraintWidget, type6, 0);
                connect(type8, constraintWidget, type8, 0);
                z = true;
            } else {
                z = false;
            }
            if ((anchor3 == null || !anchor3.isConnected()) && (anchor4 == null || !anchor4.isConnected())) {
                connect(type7, constraintWidget, type7, 0);
                connect(type9, constraintWidget, type9, 0);
            } else {
                z2 = false;
            }
            if (z && z2) {
                getAnchor(type3).connect(constraintWidget.getAnchor(type3), 0);
                return;
            } else if (z) {
                getAnchor(type5).connect(constraintWidget.getAnchor(type5), 0);
                return;
            } else {
                if (z2) {
                    getAnchor(type4).connect(constraintWidget.getAnchor(type4), 0);
                    return;
                }
                return;
            }
        }
        if (type == type5 && (type2 == type6 || type2 == type8)) {
            ConstraintAnchor anchor5 = getAnchor(type6);
            ConstraintAnchor anchor6 = constraintWidget.getAnchor(type2);
            ConstraintAnchor anchor7 = getAnchor(type8);
            anchor5.connect(anchor6, 0);
            anchor7.connect(anchor6, 0);
            getAnchor(type5).connect(anchor6, 0);
            return;
        }
        if (type == type4 && (type2 == type7 || type2 == type9)) {
            ConstraintAnchor anchor8 = constraintWidget.getAnchor(type2);
            getAnchor(type7).connect(anchor8, 0);
            getAnchor(type9).connect(anchor8, 0);
            getAnchor(type4).connect(anchor8, 0);
            return;
        }
        if (type == type5 && type2 == type5) {
            getAnchor(type6).connect(constraintWidget.getAnchor(type6), 0);
            getAnchor(type8).connect(constraintWidget.getAnchor(type8), 0);
            getAnchor(type5).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        if (type == type4 && type2 == type4) {
            getAnchor(type7).connect(constraintWidget.getAnchor(type7), 0);
            getAnchor(type9).connect(constraintWidget.getAnchor(type9), 0);
            getAnchor(type4).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        ConstraintAnchor anchor9 = getAnchor(type);
        ConstraintAnchor anchor10 = constraintWidget.getAnchor(type2);
        if (anchor9.isValidConnection(anchor10)) {
            ConstraintAnchor.Type type10 = ConstraintAnchor.Type.BASELINE;
            if (type == type10) {
                ConstraintAnchor anchor11 = getAnchor(type7);
                ConstraintAnchor anchor12 = getAnchor(type9);
                if (anchor11 != null) {
                    anchor11.reset();
                }
                if (anchor12 != null) {
                    anchor12.reset();
                }
            } else if (type == type7 || type == type9) {
                ConstraintAnchor anchor13 = getAnchor(type10);
                if (anchor13 != null) {
                    anchor13.reset();
                }
                ConstraintAnchor anchor14 = getAnchor(type3);
                if (anchor14.mTarget != anchor10) {
                    anchor14.reset();
                }
                ConstraintAnchor opposite = getAnchor(type).getOpposite();
                ConstraintAnchor anchor15 = getAnchor(type4);
                if (anchor15.isConnected()) {
                    opposite.reset();
                    anchor15.reset();
                }
            } else if (type == type6 || type == type8) {
                ConstraintAnchor anchor16 = getAnchor(type3);
                if (anchor16.mTarget != anchor10) {
                    anchor16.reset();
                }
                ConstraintAnchor opposite2 = getAnchor(type).getOpposite();
                ConstraintAnchor anchor17 = getAnchor(type5);
                if (anchor17.isConnected()) {
                    opposite2.reset();
                    anchor17.reset();
                }
            }
            anchor9.connect(anchor10, i);
        }
    }

    private static void serializeAttribute(int i, int i2, String str, StringBuilder sb) {
        if (i == i2) {
            return;
        }
        sb.append(str);
        sb.append(" :   ");
        sb.append(i);
        sb.append(",\n");
    }

    private static void getSceneString(StringBuilder sb, String str, int i, int i2, int i3, int i4, int i5, float f, DimensionBehaviour dimensionBehaviour) {
        sb.append(str);
        sb.append(" :  {\n");
        String str2 = dimensionBehaviour.toString();
        if (!"FIXED".equals(str2)) {
            sb.append("      behavior");
            sb.append(" :   ");
            sb.append(str2);
            sb.append(",\n");
        }
        serializeAttribute(i, 0, "      size", sb);
        serializeAttribute(i2, 0, "      min", sb);
        serializeAttribute(i3, Integer.MAX_VALUE, "      max", sb);
        serializeAttribute(i4, 0, "      matchMin", sb);
        serializeAttribute(i5, 0, "      matchDef", sb);
        serializeAttribute(sb, "      matchPercent", f, 1.0f);
        sb.append("    },\n");
    }

    private static void getSceneString(StringBuilder sb, String str, ConstraintAnchor constraintAnchor) {
        if (constraintAnchor.mTarget == null) {
            return;
        }
        sb.append("    ");
        sb.append(str);
        sb.append(" : [ '");
        sb.append(constraintAnchor.mTarget);
        sb.append("'");
        if (constraintAnchor.mGoneMargin != Integer.MIN_VALUE || constraintAnchor.mMargin != 0) {
            sb.append(",");
            sb.append(constraintAnchor.mMargin);
            if (constraintAnchor.mGoneMargin != Integer.MIN_VALUE) {
                sb.append(",");
                sb.append(constraintAnchor.mGoneMargin);
                sb.append(",");
            }
        }
        sb.append(" ] ,\n");
    }
}
