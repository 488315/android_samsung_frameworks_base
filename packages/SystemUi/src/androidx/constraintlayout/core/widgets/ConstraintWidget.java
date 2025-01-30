package androidx.constraintlayout.core.widgets;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.Cache;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.state.WidgetFrame;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ConstraintWidget {
    public final boolean OPTIMIZE_WRAP_ON_RESOLVED;
    public boolean hasBaseline;
    public ChainRun horizontalChainRun;
    public int horizontalGroup;
    public HorizontalWidgetRun horizontalRun;
    public boolean horizontalSolvingPass;
    public boolean inPlaceholder;
    public final boolean[] isTerminalWidget;
    public final ArrayList mAnchors;
    public boolean mAnimated;
    public final ConstraintAnchor mBaseline;
    public int mBaselineDistance;
    public final ConstraintAnchor mBottom;
    public final ConstraintAnchor mCenter;
    public final ConstraintAnchor mCenterX;
    public final ConstraintAnchor mCenterY;
    public float mCircleConstraintAngle;
    public Object mCompanionWidget;
    public String mDebugName;
    public float mDimensionRatio;
    public int mDimensionRatioSide;
    public int mHeight;
    public int mHeightOverride;
    public float mHorizontalBiasPercent;
    public int mHorizontalChainStyle;
    public ConstraintWidget mHorizontalNextWidget;
    public int mHorizontalResolution;
    public boolean mInVirtualLayout;
    public final boolean[] mIsInBarrier;
    public int mLastHorizontalMeasureSpec;
    public int mLastVerticalMeasureSpec;
    public final ConstraintAnchor mLeft;
    public final ConstraintAnchor[] mListAnchors;
    public DimensionBehaviour[] mListDimensionBehaviors;
    public final ConstraintWidget[] mListNextMatchConstraintsWidget;
    public int mMatchConstraintDefaultHeight;
    public int mMatchConstraintDefaultWidth;
    public int mMatchConstraintMaxHeight;
    public int mMatchConstraintMaxWidth;
    public int mMatchConstraintMinHeight;
    public int mMatchConstraintMinWidth;
    public float mMatchConstraintPercentHeight;
    public float mMatchConstraintPercentWidth;
    public int[] mMaxDimension;
    public boolean mMeasureRequested;
    public int mMinHeight;
    public int mMinWidth;
    public final ConstraintWidget[] mNextChainWidget;
    public ConstraintWidget mParent;
    public float mResolvedDimensionRatio;
    public int mResolvedDimensionRatioSide;
    public final int[] mResolvedMatchConstraintDefault;
    public final ConstraintAnchor mRight;
    public final ConstraintAnchor mTop;
    public String mType;
    public float mVerticalBiasPercent;
    public int mVerticalChainStyle;
    public ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution;
    public int mVisibility;
    public final float[] mWeight;
    public int mWidth;
    public int mWidthOverride;
    public int mWrapBehaviorInParent;

    /* renamed from: mX */
    public int f15mX;

    /* renamed from: mY */
    public int f16mY;
    public boolean measured;
    public boolean resolvedHorizontal;
    public boolean resolvedVertical;
    public ChainRun verticalChainRun;
    public int verticalGroup;
    public VerticalWidgetRun verticalRun;
    public boolean verticalSolvingPass;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.constraintlayout.core.widgets.ConstraintWidget$1 */
    public abstract /* synthetic */ class AbstractC01171 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type */
        public static final /* synthetic */ int[] f17x6930e354;

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour */
        public static final /* synthetic */ int[] f18x6d00e4a2;

        static {
            int[] iArr = new int[DimensionBehaviour.values().length];
            f18x6d00e4a2 = iArr;
            try {
                iArr[DimensionBehaviour.FIXED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f18x6d00e4a2[DimensionBehaviour.WRAP_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f18x6d00e4a2[DimensionBehaviour.MATCH_PARENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f18x6d00e4a2[DimensionBehaviour.MATCH_CONSTRAINT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[ConstraintAnchor.Type.values().length];
            f17x6930e354 = iArr2;
            try {
                iArr2[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f17x6930e354[ConstraintAnchor.Type.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f17x6930e354[ConstraintAnchor.Type.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f17x6930e354[ConstraintAnchor.Type.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f17x6930e354[ConstraintAnchor.Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f17x6930e354[ConstraintAnchor.Type.CENTER.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f17x6930e354[ConstraintAnchor.Type.CENTER_X.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f17x6930e354[ConstraintAnchor.Type.CENTER_Y.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f17x6930e354[ConstraintAnchor.Type.NONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public ConstraintWidget() {
        this.measured = false;
        this.horizontalRun = null;
        this.verticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mMeasureRequested = true;
        this.OPTIMIZE_WRAP_ON_RESOLVED = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        new WidgetFrame(this);
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
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
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
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
        this.mAnchors = new ArrayList();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.f15mX = 0;
        this.f16mY = 0;
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
        addAnchors();
    }

    public final void addAnchors() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        ArrayList arrayList = this.mAnchors;
        arrayList.add(constraintAnchor);
        arrayList.add(this.mTop);
        arrayList.add(this.mRight);
        arrayList.add(this.mBottom);
        arrayList.add(this.mCenterX);
        arrayList.add(this.mCenterY);
        arrayList.add(this.mCenter);
        arrayList.add(this.mBaseline);
    }

    public final void addChildrenToSolverByDependency(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, HashSet hashSet, int i, boolean z) {
        if (z) {
            if (!hashSet.contains(this)) {
                return;
            }
            Optimizer.checkMatchParent(constraintWidgetContainer, linearSystem, this);
            hashSet.remove(this);
            addToSolver(linearSystem, constraintWidgetContainer.optimizeFor(64));
        }
        if (i == 0) {
            HashSet hashSet2 = this.mLeft.mDependents;
            if (hashSet2 != null) {
                Iterator it = hashSet2.iterator();
                while (it.hasNext()) {
                    ((ConstraintAnchor) it.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
                }
            }
            HashSet hashSet3 = this.mRight.mDependents;
            if (hashSet3 != null) {
                Iterator it2 = hashSet3.iterator();
                while (it2.hasNext()) {
                    ((ConstraintAnchor) it2.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
                }
                return;
            }
            return;
        }
        HashSet hashSet4 = this.mTop.mDependents;
        if (hashSet4 != null) {
            Iterator it3 = hashSet4.iterator();
            while (it3.hasNext()) {
                ((ConstraintAnchor) it3.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
        HashSet hashSet5 = this.mBottom.mDependents;
        if (hashSet5 != null) {
            Iterator it4 = hashSet5.iterator();
            while (it4.hasNext()) {
                ((ConstraintAnchor) it4.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
        HashSet hashSet6 = this.mBaseline.mDependents;
        if (hashSet6 != null) {
            Iterator it5 = hashSet6.iterator();
            while (it5.hasNext()) {
                ((ConstraintAnchor) it5.next()).mOwner.addChildrenToSolverByDependency(constraintWidgetContainer, linearSystem, hashSet, i, true);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:341:0x03f8, code lost:
    
        if (r7 != r11) goto L283;
     */
    /* JADX WARN: Code restructure failed: missing block: B:362:0x00dc, code lost:
    
        if (r0.getFinalValue() > ((androidx.constraintlayout.core.widgets.ConstraintAnchor) r3.horizontalWrapMin.get()).getFinalValue()) goto L66;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0423  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0432  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0447  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0450  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0483  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x0591  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0607  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x060c  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x0699  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x06e3  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x074c  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0742  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x069c  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0609  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x05f3  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x04cc  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x04dc  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x04eb  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x0514  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x0517  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x04f4  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x04e5  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x0453  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x042d  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0089 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0246  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void addToSolver(LinearSystem linearSystem, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        int i;
        boolean[] zArr;
        boolean z5;
        boolean[] zArr2;
        boolean z6;
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        ConstraintAnchor constraintAnchor;
        boolean z7;
        boolean z8;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        float f;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        boolean z9;
        int i13;
        boolean z10;
        DimensionBehaviour dimensionBehaviour;
        boolean z11;
        int i14;
        ConstraintAnchor constraintAnchor2;
        boolean z12;
        boolean z13;
        ConstraintAnchor constraintAnchor3;
        DimensionBehaviour dimensionBehaviour2;
        DimensionBehaviour dimensionBehaviour3;
        ConstraintAnchor constraintAnchor4;
        SolverVariable solverVariable;
        SolverVariable solverVariable2;
        SolverVariable solverVariable3;
        SolverVariable solverVariable4;
        SolverVariable solverVariable5;
        ConstraintAnchor constraintAnchor5;
        int i15;
        int i16;
        int i17;
        ConstraintWidget constraintWidget3;
        LinearSystem linearSystem2;
        SolverVariable solverVariable6;
        SolverVariable solverVariable7;
        SolverVariable solverVariable8;
        int i18;
        SolverVariable solverVariable9;
        SolverVariable solverVariable10;
        ConstraintWidget constraintWidget4;
        LinearSystem linearSystem3;
        boolean z14;
        HorizontalWidgetRun horizontalWidgetRun;
        DependencyNode dependencyNode;
        int i19;
        boolean isInHorizontalChain;
        boolean isInVerticalChain;
        boolean z15;
        HorizontalWidgetRun horizontalWidgetRun2;
        VerticalWidgetRun verticalWidgetRun;
        boolean z16;
        ArrayList arrayList;
        int size;
        int i20;
        boolean z17;
        ArrayList arrayList2;
        ConstraintAnchor constraintAnchor6 = this.mLeft;
        SolverVariable createObjectVariable = linearSystem.createObjectVariable(constraintAnchor6);
        ConstraintAnchor constraintAnchor7 = this.mRight;
        SolverVariable createObjectVariable2 = linearSystem.createObjectVariable(constraintAnchor7);
        ConstraintAnchor constraintAnchor8 = this.mTop;
        SolverVariable createObjectVariable3 = linearSystem.createObjectVariable(constraintAnchor8);
        ConstraintAnchor constraintAnchor9 = this.mBottom;
        SolverVariable createObjectVariable4 = linearSystem.createObjectVariable(constraintAnchor9);
        ConstraintAnchor constraintAnchor10 = this.mBaseline;
        SolverVariable createObjectVariable5 = linearSystem.createObjectVariable(constraintAnchor10);
        ConstraintWidget constraintWidget5 = this.mParent;
        if (constraintWidget5 != null) {
            DimensionBehaviour[] dimensionBehaviourArr = constraintWidget5.mListDimensionBehaviors;
            DimensionBehaviour dimensionBehaviour4 = dimensionBehaviourArr[0];
            DimensionBehaviour dimensionBehaviour5 = DimensionBehaviour.WRAP_CONTENT;
            boolean z18 = dimensionBehaviour4 == dimensionBehaviour5;
            z2 = dimensionBehaviourArr[1] == dimensionBehaviour5;
            int i21 = this.mWrapBehaviorInParent;
            if (i21 != 1) {
                if (i21 != 2) {
                    if (i21 != 3) {
                        z3 = z2;
                        z4 = z18;
                    }
                }
                z3 = z2;
                z4 = false;
            } else {
                z4 = z18;
                z3 = false;
            }
            i = this.mVisibility;
            zArr = this.mIsInBarrier;
            if (i == 8 && !this.mAnimated) {
                arrayList = this.mAnchors;
                size = arrayList.size();
                i20 = 0;
                while (true) {
                    if (i20 < size) {
                        z17 = false;
                        break;
                    }
                    arrayList2 = arrayList;
                    HashSet hashSet = ((ConstraintAnchor) arrayList.get(i20)).mDependents;
                    if (hashSet != null && hashSet.size() > 0) {
                        z17 = true;
                        break;
                    } else {
                        i20++;
                        arrayList = arrayList2;
                    }
                }
                if (!z17 && !zArr[0] && !zArr[1]) {
                    return;
                }
            }
            z5 = this.resolvedHorizontal;
            if (!z5 || this.resolvedVertical) {
                boolean z19 = this.OPTIMIZE_WRAP_ON_RESOLVED;
                if (z5) {
                    linearSystem.addEquality(createObjectVariable, this.f15mX);
                    linearSystem.addEquality(createObjectVariable2, this.f15mX + this.mWidth);
                    if (z4 && (constraintWidget2 = this.mParent) != null) {
                        if (z19) {
                            ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) constraintWidget2;
                            WeakReference weakReference = constraintWidgetContainer.horizontalWrapMin;
                            if (weakReference == null || weakReference.get() == null) {
                                zArr2 = zArr;
                            } else {
                                zArr2 = zArr;
                            }
                            constraintWidgetContainer.horizontalWrapMin = new WeakReference(constraintAnchor6);
                            WeakReference weakReference2 = constraintWidgetContainer.horizontalWrapMax;
                            if (weakReference2 == null || weakReference2.get() == null || constraintAnchor7.getFinalValue() > ((ConstraintAnchor) constraintWidgetContainer.horizontalWrapMax.get()).getFinalValue()) {
                                constraintWidgetContainer.horizontalWrapMax = new WeakReference(constraintAnchor7);
                            }
                        } else {
                            zArr2 = zArr;
                            linearSystem.addGreaterThan(linearSystem.createObjectVariable(constraintWidget2.mRight), createObjectVariable2, 0, 5);
                        }
                        if (this.resolvedVertical) {
                            linearSystem.addEquality(createObjectVariable3, this.f16mY);
                            linearSystem.addEquality(createObjectVariable4, this.f16mY + this.mHeight);
                            HashSet hashSet2 = constraintAnchor10.mDependents;
                            if (hashSet2 != null && hashSet2.size() > 0) {
                                linearSystem.addEquality(createObjectVariable5, this.f16mY + this.mBaselineDistance);
                            }
                            if (z3 && (constraintWidget = this.mParent) != null) {
                                if (z19) {
                                    ConstraintWidgetContainer constraintWidgetContainer2 = (ConstraintWidgetContainer) constraintWidget;
                                    WeakReference weakReference3 = constraintWidgetContainer2.verticalWrapMin;
                                    if (weakReference3 == null || weakReference3.get() == null || constraintAnchor8.getFinalValue() > ((ConstraintAnchor) constraintWidgetContainer2.verticalWrapMin.get()).getFinalValue()) {
                                        constraintWidgetContainer2.verticalWrapMin = new WeakReference(constraintAnchor8);
                                    }
                                    WeakReference weakReference4 = constraintWidgetContainer2.verticalWrapMax;
                                    if (weakReference4 == null || weakReference4.get() == null || constraintAnchor9.getFinalValue() > ((ConstraintAnchor) constraintWidgetContainer2.verticalWrapMax.get()).getFinalValue()) {
                                        constraintWidgetContainer2.verticalWrapMax = new WeakReference(constraintAnchor9);
                                    }
                                } else {
                                    z6 = false;
                                    linearSystem.addGreaterThan(linearSystem.createObjectVariable(constraintWidget.mBottom), createObjectVariable4, 0, 5);
                                    if (this.resolvedHorizontal && this.resolvedVertical) {
                                        this.resolvedHorizontal = z6;
                                        this.resolvedVertical = z6;
                                        return;
                                    }
                                }
                            }
                        }
                        z6 = false;
                        if (this.resolvedHorizontal) {
                            this.resolvedHorizontal = z6;
                            this.resolvedVertical = z6;
                            return;
                        }
                    }
                }
                zArr2 = zArr;
                if (this.resolvedVertical) {
                }
                z6 = false;
                if (this.resolvedHorizontal) {
                }
            } else {
                zArr2 = zArr;
            }
            boolean[] zArr3 = this.isTerminalWidget;
            if (z || (horizontalWidgetRun2 = this.horizontalRun) == null || (verticalWidgetRun = this.verticalRun) == null) {
                constraintAnchor = constraintAnchor10;
            } else {
                DependencyNode dependencyNode2 = horizontalWidgetRun2.start;
                constraintAnchor = constraintAnchor10;
                if (dependencyNode2.resolved && horizontalWidgetRun2.end.resolved && verticalWidgetRun.start.resolved && verticalWidgetRun.end.resolved) {
                    linearSystem.addEquality(createObjectVariable, dependencyNode2.value);
                    linearSystem.addEquality(createObjectVariable2, this.horizontalRun.end.value);
                    linearSystem.addEquality(createObjectVariable3, this.verticalRun.start.value);
                    linearSystem.addEquality(createObjectVariable4, this.verticalRun.end.value);
                    linearSystem.addEquality(createObjectVariable5, this.verticalRun.baseline.value);
                    if (this.mParent != null) {
                        if (z4 && zArr3[0] && !isInHorizontalChain()) {
                            linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mRight), createObjectVariable2, 0, 8);
                        }
                        if (z3 && zArr3[1] && !isInVerticalChain()) {
                            z16 = false;
                            linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mBottom), createObjectVariable4, 0, 8);
                            this.resolvedHorizontal = z16;
                            this.resolvedVertical = z16;
                            return;
                        }
                    }
                    z16 = false;
                    this.resolvedHorizontal = z16;
                    this.resolvedVertical = z16;
                    return;
                }
            }
            if (this.mParent == null) {
                if (isChainHead(0)) {
                    ((ConstraintWidgetContainer) this.mParent).addChain(this, 0);
                    isInHorizontalChain = true;
                } else {
                    isInHorizontalChain = isInHorizontalChain();
                }
                if (isChainHead(1)) {
                    ((ConstraintWidgetContainer) this.mParent).addChain(this, 1);
                    isInVerticalChain = true;
                } else {
                    isInVerticalChain = isInVerticalChain();
                }
                if (!isInHorizontalChain && z4 && this.mVisibility != 8 && constraintAnchor6.mTarget == null && constraintAnchor7.mTarget == null) {
                    z15 = isInHorizontalChain;
                    linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mRight), createObjectVariable2, 0, 1);
                } else {
                    z15 = isInHorizontalChain;
                }
                if (!isInVerticalChain && z3 && this.mVisibility != 8 && constraintAnchor8.mTarget == null && constraintAnchor9.mTarget == null && constraintAnchor == null) {
                    linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mBottom), createObjectVariable4, 0, 1);
                }
                z7 = isInVerticalChain;
                z8 = z15;
            } else {
                z7 = false;
                z8 = false;
            }
            i2 = this.mWidth;
            i3 = this.mMinWidth;
            if (i2 >= i3) {
                i3 = i2;
            }
            i4 = this.mHeight;
            i5 = this.mMinHeight;
            i6 = i3;
            if (i4 >= i5) {
                i5 = i4;
            }
            DimensionBehaviour[] dimensionBehaviourArr2 = this.mListDimensionBehaviors;
            DimensionBehaviour dimensionBehaviour6 = dimensionBehaviourArr2[0];
            DimensionBehaviour dimensionBehaviour7 = DimensionBehaviour.MATCH_CONSTRAINT;
            boolean z20 = dimensionBehaviour6 == dimensionBehaviour7;
            DimensionBehaviour dimensionBehaviour8 = dimensionBehaviourArr2[1];
            int i22 = i5;
            boolean z21 = dimensionBehaviour8 == dimensionBehaviour7;
            int i23 = this.mDimensionRatioSide;
            this.mResolvedDimensionRatioSide = i23;
            f = this.mDimensionRatio;
            this.mResolvedDimensionRatio = f;
            int i24 = this.mMatchConstraintDefaultWidth;
            int i25 = this.mMatchConstraintDefaultHeight;
            if (f <= 0.0f && this.mVisibility != 8) {
                if (dimensionBehaviour6 == dimensionBehaviour7 && i24 == 0) {
                    i24 = 3;
                }
                if (dimensionBehaviour8 == dimensionBehaviour7 && i25 == 0) {
                    i25 = 3;
                }
                if (dimensionBehaviour6 == dimensionBehaviour7 && dimensionBehaviour8 == dimensionBehaviour7 && i24 == 3 && i25 == 3) {
                    if (i23 == -1) {
                        if (z20 && !z21) {
                            this.mResolvedDimensionRatioSide = 0;
                        } else if (!z20 && z21) {
                            this.mResolvedDimensionRatioSide = 1;
                            if (i23 == -1) {
                                this.mResolvedDimensionRatio = 1.0f / f;
                            }
                        }
                    }
                    if (this.mResolvedDimensionRatioSide == 0 && (!constraintAnchor8.isConnected() || !constraintAnchor9.isConnected())) {
                        this.mResolvedDimensionRatioSide = 1;
                    } else if (this.mResolvedDimensionRatioSide == 1 && (!constraintAnchor6.isConnected() || !constraintAnchor7.isConnected())) {
                        this.mResolvedDimensionRatioSide = 0;
                    }
                    if (this.mResolvedDimensionRatioSide == -1 && (!constraintAnchor8.isConnected() || !constraintAnchor9.isConnected() || !constraintAnchor6.isConnected() || !constraintAnchor7.isConnected())) {
                        if (constraintAnchor8.isConnected() && constraintAnchor9.isConnected()) {
                            this.mResolvedDimensionRatioSide = 0;
                        } else if (constraintAnchor6.isConnected() && constraintAnchor7.isConnected()) {
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
                } else {
                    i9 = 4;
                    if (dimensionBehaviour6 == dimensionBehaviour7 && i24 == 3) {
                        this.mResolvedDimensionRatioSide = 0;
                        int i27 = (int) (f * i4);
                        if (dimensionBehaviour8 == dimensionBehaviour7) {
                            i7 = i27;
                            i8 = i22;
                            i11 = i24;
                            i12 = i8;
                            i10 = i25;
                            z9 = true;
                            int[] iArr = this.mResolvedMatchConstraintDefault;
                            iArr[0] = i11;
                            iArr[1] = i10;
                            if (z9) {
                                i13 = -1;
                            } else {
                                int i28 = this.mResolvedDimensionRatioSide;
                                i13 = -1;
                                if (i28 == 0 || i28 == -1) {
                                    z10 = true;
                                    boolean z22 = !z9 && ((i19 = this.mResolvedDimensionRatioSide) == 1 || i19 == i13);
                                    DimensionBehaviour dimensionBehaviour9 = this.mListDimensionBehaviors[0];
                                    dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
                                    z11 = dimensionBehaviour9 != dimensionBehaviour && (this instanceof ConstraintWidgetContainer);
                                    i14 = z11 ? 0 : i7;
                                    constraintAnchor2 = this.mCenter;
                                    z12 = !constraintAnchor2.isConnected();
                                    z13 = zArr2[0];
                                    boolean z23 = zArr2[1];
                                    if (this.mHorizontalResolution != 2 && !this.resolvedHorizontal) {
                                        if (z && (horizontalWidgetRun = this.horizontalRun) != null) {
                                            dependencyNode = horizontalWidgetRun.start;
                                            if (dependencyNode.resolved && horizontalWidgetRun.end.resolved) {
                                                if (!z) {
                                                    linearSystem.addEquality(createObjectVariable, dependencyNode.value);
                                                    linearSystem.addEquality(createObjectVariable2, this.horizontalRun.end.value);
                                                    if (this.mParent != null && z4 && zArr3[0] && !isInHorizontalChain()) {
                                                        linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mParent.mRight), createObjectVariable2, 0, 8);
                                                    }
                                                    solverVariable4 = createObjectVariable2;
                                                    solverVariable5 = createObjectVariable;
                                                    constraintAnchor3 = constraintAnchor9;
                                                    dimensionBehaviour2 = dimensionBehaviour7;
                                                    dimensionBehaviour3 = dimensionBehaviour;
                                                    constraintAnchor4 = constraintAnchor;
                                                    solverVariable = createObjectVariable5;
                                                    solverVariable2 = createObjectVariable3;
                                                    solverVariable3 = createObjectVariable4;
                                                    constraintAnchor5 = constraintAnchor2;
                                                    if (z) {
                                                        i15 = 8;
                                                        i16 = 0;
                                                        i17 = 1;
                                                        constraintWidget3 = this;
                                                        linearSystem2 = linearSystem;
                                                        solverVariable6 = solverVariable;
                                                        solverVariable7 = solverVariable3;
                                                        solverVariable8 = solverVariable2;
                                                    } else {
                                                        constraintWidget3 = this;
                                                        VerticalWidgetRun verticalWidgetRun2 = constraintWidget3.verticalRun;
                                                        if (verticalWidgetRun2 != null) {
                                                            DependencyNode dependencyNode3 = verticalWidgetRun2.start;
                                                            if (dependencyNode3.resolved && verticalWidgetRun2.end.resolved) {
                                                                linearSystem2 = linearSystem;
                                                                solverVariable8 = solverVariable2;
                                                                linearSystem2.addEquality(solverVariable8, dependencyNode3.value);
                                                                solverVariable7 = solverVariable3;
                                                                linearSystem2.addEquality(solverVariable7, constraintWidget3.verticalRun.end.value);
                                                                solverVariable6 = solverVariable;
                                                                linearSystem2.addEquality(solverVariable6, constraintWidget3.verticalRun.baseline.value);
                                                                ConstraintWidget constraintWidget6 = constraintWidget3.mParent;
                                                                if (constraintWidget6 == null || z7 || !z3) {
                                                                    i15 = 8;
                                                                    i16 = 0;
                                                                    i17 = 1;
                                                                } else {
                                                                    i17 = 1;
                                                                    if (zArr3[1]) {
                                                                        i15 = 8;
                                                                        i16 = 0;
                                                                        linearSystem2.addGreaterThan(linearSystem2.createObjectVariable(constraintWidget6.mBottom), solverVariable7, 0, 8);
                                                                    } else {
                                                                        i15 = 8;
                                                                        i16 = 0;
                                                                    }
                                                                }
                                                                i18 = i16;
                                                                if ((constraintWidget3.mVerticalResolution == 2 ? i16 : i18) != 0 || constraintWidget3.resolvedVertical) {
                                                                    solverVariable9 = solverVariable7;
                                                                    solverVariable10 = solverVariable8;
                                                                } else {
                                                                    boolean z24 = (constraintWidget3.mListDimensionBehaviors[i17] == dimensionBehaviour3 && (constraintWidget3 instanceof ConstraintWidgetContainer)) ? i17 : i16;
                                                                    if (z24) {
                                                                        i12 = i16;
                                                                    }
                                                                    ConstraintWidget constraintWidget7 = constraintWidget3.mParent;
                                                                    SolverVariable createObjectVariable6 = constraintWidget7 != null ? linearSystem2.createObjectVariable(constraintWidget7.mBottom) : null;
                                                                    ConstraintWidget constraintWidget8 = constraintWidget3.mParent;
                                                                    SolverVariable createObjectVariable7 = constraintWidget8 != null ? linearSystem2.createObjectVariable(constraintWidget8.mTop) : null;
                                                                    int i29 = constraintWidget3.mBaselineDistance;
                                                                    if (i29 > 0 || constraintWidget3.mVisibility == i15) {
                                                                        ConstraintAnchor constraintAnchor11 = constraintAnchor4;
                                                                        if (constraintAnchor11.mTarget != null) {
                                                                            linearSystem2.addEquality(solverVariable6, solverVariable8, i29, i15);
                                                                            linearSystem2.addEquality(solverVariable6, linearSystem2.createObjectVariable(constraintAnchor11.mTarget), constraintAnchor11.getMargin(), i15);
                                                                            if (z3) {
                                                                                linearSystem2.addGreaterThan(createObjectVariable6, linearSystem2.createObjectVariable(constraintAnchor3), i16, 5);
                                                                            }
                                                                            z14 = i16;
                                                                            boolean z25 = zArr3[i17];
                                                                            DimensionBehaviour[] dimensionBehaviourArr3 = constraintWidget3.mListDimensionBehaviors;
                                                                            solverVariable9 = solverVariable7;
                                                                            solverVariable10 = solverVariable8;
                                                                            applyConstraints(linearSystem, false, z3, z4, z25, createObjectVariable7, createObjectVariable6, dimensionBehaviourArr3[i17], z24, constraintWidget3.mTop, constraintWidget3.mBottom, constraintWidget3.f16mY, i12, constraintWidget3.mMinHeight, constraintWidget3.mMaxDimension[i17], constraintWidget3.mVerticalBiasPercent, z22, dimensionBehaviourArr3[0] != dimensionBehaviour2, z7, z8, z23, i10, i11, constraintWidget3.mMatchConstraintMinHeight, constraintWidget3.mMatchConstraintMaxHeight, constraintWidget3.mMatchConstraintPercentHeight, z14);
                                                                        } else if (constraintWidget3.mVisibility == i15) {
                                                                            linearSystem2.addEquality(solverVariable6, solverVariable8, constraintAnchor11.getMargin(), i15);
                                                                        } else {
                                                                            linearSystem2.addEquality(solverVariable6, solverVariable8, i29, i15);
                                                                        }
                                                                    }
                                                                    z14 = z12;
                                                                    boolean z252 = zArr3[i17];
                                                                    DimensionBehaviour[] dimensionBehaviourArr32 = constraintWidget3.mListDimensionBehaviors;
                                                                    solverVariable9 = solverVariable7;
                                                                    solverVariable10 = solverVariable8;
                                                                    applyConstraints(linearSystem, false, z3, z4, z252, createObjectVariable7, createObjectVariable6, dimensionBehaviourArr32[i17], z24, constraintWidget3.mTop, constraintWidget3.mBottom, constraintWidget3.f16mY, i12, constraintWidget3.mMinHeight, constraintWidget3.mMaxDimension[i17], constraintWidget3.mVerticalBiasPercent, z22, dimensionBehaviourArr32[0] != dimensionBehaviour2, z7, z8, z23, i10, i11, constraintWidget3.mMatchConstraintMinHeight, constraintWidget3.mMatchConstraintMaxHeight, constraintWidget3.mMatchConstraintPercentHeight, z14);
                                                                }
                                                                if (z9) {
                                                                    constraintWidget4 = this;
                                                                    if (constraintWidget4.mResolvedDimensionRatioSide == 1) {
                                                                        float f2 = constraintWidget4.mResolvedDimensionRatio;
                                                                        ArrayRow createRow = linearSystem.createRow();
                                                                        createRow.variables.put(solverVariable9, -1.0f);
                                                                        createRow.variables.put(solverVariable10, 1.0f);
                                                                        createRow.variables.put(solverVariable4, f2);
                                                                        createRow.variables.put(solverVariable5, -f2);
                                                                        linearSystem3 = linearSystem;
                                                                        linearSystem3.addConstraint(createRow);
                                                                    } else {
                                                                        linearSystem3 = linearSystem;
                                                                        float f3 = constraintWidget4.mResolvedDimensionRatio;
                                                                        ArrayRow createRow2 = linearSystem.createRow();
                                                                        createRow2.variables.put(solverVariable4, -1.0f);
                                                                        createRow2.variables.put(solverVariable5, 1.0f);
                                                                        createRow2.variables.put(solverVariable9, f3);
                                                                        createRow2.variables.put(solverVariable10, -f3);
                                                                        linearSystem3.addConstraint(createRow2);
                                                                    }
                                                                } else {
                                                                    constraintWidget4 = this;
                                                                    linearSystem3 = linearSystem;
                                                                }
                                                                if (constraintAnchor5.isConnected()) {
                                                                    ConstraintAnchor constraintAnchor12 = constraintAnchor5;
                                                                    ConstraintWidget constraintWidget9 = constraintAnchor12.mTarget.mOwner;
                                                                    float radians = (float) Math.toRadians(constraintWidget4.mCircleConstraintAngle + 90.0f);
                                                                    int margin = constraintAnchor12.getMargin();
                                                                    ConstraintAnchor.Type type = ConstraintAnchor.Type.LEFT;
                                                                    SolverVariable createObjectVariable8 = linearSystem3.createObjectVariable(constraintWidget4.getAnchor(type));
                                                                    ConstraintAnchor.Type type2 = ConstraintAnchor.Type.TOP;
                                                                    SolverVariable createObjectVariable9 = linearSystem3.createObjectVariable(constraintWidget4.getAnchor(type2));
                                                                    ConstraintAnchor.Type type3 = ConstraintAnchor.Type.RIGHT;
                                                                    SolverVariable createObjectVariable10 = linearSystem3.createObjectVariable(constraintWidget4.getAnchor(type3));
                                                                    ConstraintAnchor.Type type4 = ConstraintAnchor.Type.BOTTOM;
                                                                    SolverVariable createObjectVariable11 = linearSystem3.createObjectVariable(constraintWidget4.getAnchor(type4));
                                                                    SolverVariable createObjectVariable12 = linearSystem3.createObjectVariable(constraintWidget9.getAnchor(type));
                                                                    SolverVariable createObjectVariable13 = linearSystem3.createObjectVariable(constraintWidget9.getAnchor(type2));
                                                                    SolverVariable createObjectVariable14 = linearSystem3.createObjectVariable(constraintWidget9.getAnchor(type3));
                                                                    SolverVariable createObjectVariable15 = linearSystem3.createObjectVariable(constraintWidget9.getAnchor(type4));
                                                                    ArrayRow createRow3 = linearSystem.createRow();
                                                                    double d = radians;
                                                                    double d2 = margin;
                                                                    float sin = (float) (Math.sin(d) * d2);
                                                                    createRow3.variables.put(createObjectVariable13, 0.5f);
                                                                    createRow3.variables.put(createObjectVariable15, 0.5f);
                                                                    createRow3.variables.put(createObjectVariable9, -0.5f);
                                                                    createRow3.variables.put(createObjectVariable11, -0.5f);
                                                                    createRow3.constantValue = -sin;
                                                                    linearSystem3.addConstraint(createRow3);
                                                                    ArrayRow createRow4 = linearSystem.createRow();
                                                                    float cos = (float) (Math.cos(d) * d2);
                                                                    createRow4.variables.put(createObjectVariable12, 0.5f);
                                                                    createRow4.variables.put(createObjectVariable14, 0.5f);
                                                                    createRow4.variables.put(createObjectVariable8, -0.5f);
                                                                    createRow4.variables.put(createObjectVariable10, -0.5f);
                                                                    createRow4.constantValue = -cos;
                                                                    linearSystem3.addConstraint(createRow4);
                                                                }
                                                                constraintWidget4.resolvedHorizontal = false;
                                                                constraintWidget4.resolvedVertical = false;
                                                            }
                                                        }
                                                        linearSystem2 = linearSystem;
                                                        solverVariable6 = solverVariable;
                                                        solverVariable7 = solverVariable3;
                                                        solverVariable8 = solverVariable2;
                                                        i15 = 8;
                                                        i16 = 0;
                                                        i17 = 1;
                                                    }
                                                    i18 = i17;
                                                    if ((constraintWidget3.mVerticalResolution == 2 ? i16 : i18) != 0) {
                                                    }
                                                    solverVariable9 = solverVariable7;
                                                    solverVariable10 = solverVariable8;
                                                    if (z9) {
                                                    }
                                                    if (constraintAnchor5.isConnected()) {
                                                    }
                                                    constraintWidget4.resolvedHorizontal = false;
                                                    constraintWidget4.resolvedVertical = false;
                                                }
                                            }
                                        }
                                        ConstraintWidget constraintWidget10 = this.mParent;
                                        SolverVariable createObjectVariable16 = constraintWidget10 == null ? linearSystem.createObjectVariable(constraintWidget10.mRight) : null;
                                        ConstraintWidget constraintWidget11 = this.mParent;
                                        SolverVariable createObjectVariable17 = constraintWidget11 == null ? linearSystem.createObjectVariable(constraintWidget11.mLeft) : null;
                                        boolean z26 = zArr3[0];
                                        DimensionBehaviour[] dimensionBehaviourArr4 = this.mListDimensionBehaviors;
                                        solverVariable = createObjectVariable5;
                                        constraintAnchor4 = constraintAnchor;
                                        solverVariable3 = createObjectVariable4;
                                        constraintAnchor3 = constraintAnchor9;
                                        dimensionBehaviour2 = dimensionBehaviour7;
                                        solverVariable2 = createObjectVariable3;
                                        constraintAnchor5 = constraintAnchor2;
                                        solverVariable4 = createObjectVariable2;
                                        solverVariable5 = createObjectVariable;
                                        dimensionBehaviour3 = dimensionBehaviour;
                                        applyConstraints(linearSystem, true, z4, z3, z26, createObjectVariable17, createObjectVariable16, dimensionBehaviourArr4[0], z11, this.mLeft, this.mRight, this.f15mX, i14, this.mMinWidth, this.mMaxDimension[0], this.mHorizontalBiasPercent, z10, dimensionBehaviourArr4[1] != dimensionBehaviour7, z8, z7, z13, i11, i10, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth, this.mMatchConstraintPercentWidth, z12);
                                        if (z) {
                                        }
                                        i18 = i17;
                                        if ((constraintWidget3.mVerticalResolution == 2 ? i16 : i18) != 0) {
                                        }
                                        solverVariable9 = solverVariable7;
                                        solverVariable10 = solverVariable8;
                                        if (z9) {
                                        }
                                        if (constraintAnchor5.isConnected()) {
                                        }
                                        constraintWidget4.resolvedHorizontal = false;
                                        constraintWidget4.resolvedVertical = false;
                                    }
                                    constraintAnchor3 = constraintAnchor9;
                                    dimensionBehaviour2 = dimensionBehaviour7;
                                    dimensionBehaviour3 = dimensionBehaviour;
                                    constraintAnchor4 = constraintAnchor;
                                    solverVariable = createObjectVariable5;
                                    solverVariable2 = createObjectVariable3;
                                    solverVariable3 = createObjectVariable4;
                                    solverVariable4 = createObjectVariable2;
                                    solverVariable5 = createObjectVariable;
                                    constraintAnchor5 = constraintAnchor2;
                                    if (z) {
                                    }
                                    i18 = i17;
                                    if ((constraintWidget3.mVerticalResolution == 2 ? i16 : i18) != 0) {
                                    }
                                    solverVariable9 = solverVariable7;
                                    solverVariable10 = solverVariable8;
                                    if (z9) {
                                    }
                                    if (constraintAnchor5.isConnected()) {
                                    }
                                    constraintWidget4.resolvedHorizontal = false;
                                    constraintWidget4.resolvedVertical = false;
                                }
                            }
                            z10 = false;
                            if (z9) {
                            }
                            DimensionBehaviour dimensionBehaviour92 = this.mListDimensionBehaviors[0];
                            dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
                            if (dimensionBehaviour92 != dimensionBehaviour) {
                            }
                            if (z11) {
                            }
                            constraintAnchor2 = this.mCenter;
                            z12 = !constraintAnchor2.isConnected();
                            z13 = zArr2[0];
                            boolean z232 = zArr2[1];
                            if (this.mHorizontalResolution != 2) {
                                if (z) {
                                    dependencyNode = horizontalWidgetRun.start;
                                    if (dependencyNode.resolved) {
                                        if (!z) {
                                        }
                                    }
                                }
                                ConstraintWidget constraintWidget102 = this.mParent;
                                if (constraintWidget102 == null) {
                                }
                                ConstraintWidget constraintWidget112 = this.mParent;
                                if (constraintWidget112 == null) {
                                }
                                boolean z262 = zArr3[0];
                                DimensionBehaviour[] dimensionBehaviourArr42 = this.mListDimensionBehaviors;
                                solverVariable = createObjectVariable5;
                                constraintAnchor4 = constraintAnchor;
                                solverVariable3 = createObjectVariable4;
                                constraintAnchor3 = constraintAnchor9;
                                dimensionBehaviour2 = dimensionBehaviour7;
                                solverVariable2 = createObjectVariable3;
                                constraintAnchor5 = constraintAnchor2;
                                solverVariable4 = createObjectVariable2;
                                solverVariable5 = createObjectVariable;
                                dimensionBehaviour3 = dimensionBehaviour;
                                applyConstraints(linearSystem, true, z4, z3, z262, createObjectVariable17, createObjectVariable16, dimensionBehaviourArr42[0], z11, this.mLeft, this.mRight, this.f15mX, i14, this.mMinWidth, this.mMaxDimension[0], this.mHorizontalBiasPercent, z10, dimensionBehaviourArr42[1] != dimensionBehaviour7, z8, z7, z13, i11, i10, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth, this.mMatchConstraintPercentWidth, z12);
                                if (z) {
                                }
                                i18 = i17;
                                if ((constraintWidget3.mVerticalResolution == 2 ? i16 : i18) != 0) {
                                }
                                solverVariable9 = solverVariable7;
                                solverVariable10 = solverVariable8;
                                if (z9) {
                                }
                                if (constraintAnchor5.isConnected()) {
                                }
                                constraintWidget4.resolvedHorizontal = false;
                                constraintWidget4.resolvedVertical = false;
                            }
                            constraintAnchor3 = constraintAnchor9;
                            dimensionBehaviour2 = dimensionBehaviour7;
                            dimensionBehaviour3 = dimensionBehaviour;
                            constraintAnchor4 = constraintAnchor;
                            solverVariable = createObjectVariable5;
                            solverVariable2 = createObjectVariable3;
                            solverVariable3 = createObjectVariable4;
                            solverVariable4 = createObjectVariable2;
                            solverVariable5 = createObjectVariable;
                            constraintAnchor5 = constraintAnchor2;
                            if (z) {
                            }
                            i18 = i17;
                            if ((constraintWidget3.mVerticalResolution == 2 ? i16 : i18) != 0) {
                            }
                            solverVariable9 = solverVariable7;
                            solverVariable10 = solverVariable8;
                            if (z9) {
                            }
                            if (constraintAnchor5.isConnected()) {
                            }
                            constraintWidget4.resolvedHorizontal = false;
                            constraintWidget4.resolvedVertical = false;
                        }
                        i24 = 4;
                        i7 = i27;
                    } else if (dimensionBehaviour8 == dimensionBehaviour7 && i25 == 3) {
                        this.mResolvedDimensionRatioSide = 1;
                        if (i23 == -1) {
                            this.mResolvedDimensionRatio = 1.0f / f;
                        }
                        i8 = (int) (this.mResolvedDimensionRatio * i2);
                        i7 = i6;
                    }
                }
                i7 = i6;
                i8 = i22;
                i11 = i24;
                i12 = i8;
                i10 = i25;
                z9 = true;
                int[] iArr2 = this.mResolvedMatchConstraintDefault;
                iArr2[0] = i11;
                iArr2[1] = i10;
                if (z9) {
                }
                z10 = false;
                if (z9) {
                }
                DimensionBehaviour dimensionBehaviour922 = this.mListDimensionBehaviors[0];
                dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
                if (dimensionBehaviour922 != dimensionBehaviour) {
                }
                if (z11) {
                }
                constraintAnchor2 = this.mCenter;
                z12 = !constraintAnchor2.isConnected();
                z13 = zArr2[0];
                boolean z2322 = zArr2[1];
                if (this.mHorizontalResolution != 2) {
                }
                constraintAnchor3 = constraintAnchor9;
                dimensionBehaviour2 = dimensionBehaviour7;
                dimensionBehaviour3 = dimensionBehaviour;
                constraintAnchor4 = constraintAnchor;
                solverVariable = createObjectVariable5;
                solverVariable2 = createObjectVariable3;
                solverVariable3 = createObjectVariable4;
                solverVariable4 = createObjectVariable2;
                solverVariable5 = createObjectVariable;
                constraintAnchor5 = constraintAnchor2;
                if (z) {
                }
                i18 = i17;
                if ((constraintWidget3.mVerticalResolution == 2 ? i16 : i18) != 0) {
                }
                solverVariable9 = solverVariable7;
                solverVariable10 = solverVariable8;
                if (z9) {
                }
                if (constraintAnchor5.isConnected()) {
                }
                constraintWidget4.resolvedHorizontal = false;
                constraintWidget4.resolvedVertical = false;
            }
            i7 = i6;
            i9 = i25;
            i8 = i22;
            i10 = i9;
            i11 = i24;
            i12 = i8;
            z9 = false;
            int[] iArr22 = this.mResolvedMatchConstraintDefault;
            iArr22[0] = i11;
            iArr22[1] = i10;
            if (z9) {
            }
            z10 = false;
            if (z9) {
            }
            DimensionBehaviour dimensionBehaviour9222 = this.mListDimensionBehaviors[0];
            dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
            if (dimensionBehaviour9222 != dimensionBehaviour) {
            }
            if (z11) {
            }
            constraintAnchor2 = this.mCenter;
            z12 = !constraintAnchor2.isConnected();
            z13 = zArr2[0];
            boolean z23222 = zArr2[1];
            if (this.mHorizontalResolution != 2) {
            }
            constraintAnchor3 = constraintAnchor9;
            dimensionBehaviour2 = dimensionBehaviour7;
            dimensionBehaviour3 = dimensionBehaviour;
            constraintAnchor4 = constraintAnchor;
            solverVariable = createObjectVariable5;
            solverVariable2 = createObjectVariable3;
            solverVariable3 = createObjectVariable4;
            solverVariable4 = createObjectVariable2;
            solverVariable5 = createObjectVariable;
            constraintAnchor5 = constraintAnchor2;
            if (z) {
            }
            i18 = i17;
            if ((constraintWidget3.mVerticalResolution == 2 ? i16 : i18) != 0) {
            }
            solverVariable9 = solverVariable7;
            solverVariable10 = solverVariable8;
            if (z9) {
            }
            if (constraintAnchor5.isConnected()) {
            }
            constraintWidget4.resolvedHorizontal = false;
            constraintWidget4.resolvedVertical = false;
        }
        z2 = false;
        z3 = z2;
        z4 = false;
        i = this.mVisibility;
        zArr = this.mIsInBarrier;
        if (i == 8) {
            arrayList = this.mAnchors;
            size = arrayList.size();
            i20 = 0;
            while (true) {
                if (i20 < size) {
                }
                i20++;
                arrayList = arrayList2;
            }
            if (!z17) {
                return;
            }
        }
        z5 = this.resolvedHorizontal;
        if (z5) {
        }
        boolean z192 = this.OPTIMIZE_WRAP_ON_RESOLVED;
        if (z5) {
        }
        zArr2 = zArr;
        if (this.resolvedVertical) {
        }
        z6 = false;
        if (this.resolvedHorizontal) {
        }
        boolean[] zArr32 = this.isTerminalWidget;
        if (z) {
        }
        constraintAnchor = constraintAnchor10;
        if (this.mParent == null) {
        }
        i2 = this.mWidth;
        i3 = this.mMinWidth;
        if (i2 >= i3) {
        }
        i4 = this.mHeight;
        i5 = this.mMinHeight;
        i6 = i3;
        if (i4 >= i5) {
        }
        DimensionBehaviour[] dimensionBehaviourArr22 = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour62 = dimensionBehaviourArr22[0];
        DimensionBehaviour dimensionBehaviour72 = DimensionBehaviour.MATCH_CONSTRAINT;
        if (dimensionBehaviour62 == dimensionBehaviour72) {
        }
        DimensionBehaviour dimensionBehaviour82 = dimensionBehaviourArr22[1];
        int i222 = i5;
        if (dimensionBehaviour82 == dimensionBehaviour72) {
        }
        int i232 = this.mDimensionRatioSide;
        this.mResolvedDimensionRatioSide = i232;
        f = this.mDimensionRatio;
        this.mResolvedDimensionRatio = f;
        int i242 = this.mMatchConstraintDefaultWidth;
        int i252 = this.mMatchConstraintDefaultHeight;
        if (f <= 0.0f) {
        }
        i7 = i6;
        i9 = i252;
        i8 = i222;
        i10 = i9;
        i11 = i242;
        i12 = i8;
        z9 = false;
        int[] iArr222 = this.mResolvedMatchConstraintDefault;
        iArr222[0] = i11;
        iArr222[1] = i10;
        if (z9) {
        }
        z10 = false;
        if (z9) {
        }
        DimensionBehaviour dimensionBehaviour92222 = this.mListDimensionBehaviors[0];
        dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
        if (dimensionBehaviour92222 != dimensionBehaviour) {
        }
        if (z11) {
        }
        constraintAnchor2 = this.mCenter;
        z12 = !constraintAnchor2.isConnected();
        z13 = zArr2[0];
        boolean z232222 = zArr2[1];
        if (this.mHorizontalResolution != 2) {
        }
        constraintAnchor3 = constraintAnchor9;
        dimensionBehaviour2 = dimensionBehaviour72;
        dimensionBehaviour3 = dimensionBehaviour;
        constraintAnchor4 = constraintAnchor;
        solverVariable = createObjectVariable5;
        solverVariable2 = createObjectVariable3;
        solverVariable3 = createObjectVariable4;
        solverVariable4 = createObjectVariable2;
        solverVariable5 = createObjectVariable;
        constraintAnchor5 = constraintAnchor2;
        if (z) {
        }
        i18 = i17;
        if ((constraintWidget3.mVerticalResolution == 2 ? i16 : i18) != 0) {
        }
        solverVariable9 = solverVariable7;
        solverVariable10 = solverVariable8;
        if (z9) {
        }
        if (constraintAnchor5.isConnected()) {
        }
        constraintWidget4.resolvedHorizontal = false;
        constraintWidget4.resolvedVertical = false;
    }

    public boolean allowedInBarrier() {
        return this.mVisibility != 8;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x036d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x037c  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x03de  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x03f1  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x041b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0437  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0484  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0497 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0415  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x04dc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:297:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:299:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:354:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:355:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01bb A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void applyConstraints(LinearSystem linearSystem, boolean z, boolean z2, boolean z3, boolean z4, SolverVariable solverVariable, SolverVariable solverVariable2, DimensionBehaviour dimensionBehaviour, boolean z5, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i, int i2, int i3, int i4, float f, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, int i5, int i6, int i7, int i8, float f2, boolean z11) {
        int i9;
        boolean z12;
        int i10;
        int i11;
        int i12;
        boolean z13;
        int i13;
        int i14;
        int i15;
        int i16;
        boolean z14;
        SolverVariable createObjectVariable;
        SolverVariable createObjectVariable2;
        ConstraintAnchor constraintAnchor3;
        SolverVariable solverVariable3;
        int i17;
        SolverVariable solverVariable4;
        boolean z15;
        boolean z16;
        SolverVariable solverVariable5;
        int i18;
        boolean z17;
        boolean z18;
        int i19;
        int i20;
        int i21;
        boolean z19;
        boolean z20;
        boolean z21;
        ConstraintAnchor constraintAnchor4;
        SolverVariable solverVariable6;
        SolverVariable solverVariable7;
        boolean z22;
        int i22;
        ConstraintWidget constraintWidget;
        int i23;
        ConstraintWidget constraintWidget2;
        ConstraintWidget constraintWidget3;
        SolverVariable solverVariable8;
        SolverVariable solverVariable9;
        boolean z23;
        SolverVariable solverVariable10;
        SolverVariable solverVariable11;
        ConstraintWidget constraintWidget4;
        int i24;
        boolean z24;
        int i25;
        int i26;
        int i27;
        int i28;
        boolean z25;
        int i29;
        int i30;
        int i31;
        int i32;
        boolean z26;
        boolean z27;
        int i33;
        int i34;
        int i35;
        int i36;
        SolverVariable solverVariable12;
        int i37;
        char c;
        int i38 = i7;
        int i39 = i8;
        SolverVariable createObjectVariable3 = linearSystem.createObjectVariable(constraintAnchor);
        SolverVariable createObjectVariable4 = linearSystem.createObjectVariable(constraintAnchor2);
        SolverVariable createObjectVariable5 = linearSystem.createObjectVariable(constraintAnchor.mTarget);
        SolverVariable createObjectVariable6 = linearSystem.createObjectVariable(constraintAnchor2.mTarget);
        boolean isConnected = constraintAnchor.isConnected();
        boolean isConnected2 = constraintAnchor2.isConnected();
        boolean isConnected3 = this.mCenter.isConnected();
        int i40 = isConnected2 ? (isConnected ? 1 : 0) + 1 : isConnected ? 1 : 0;
        if (isConnected3) {
            i40++;
        }
        int i41 = i40;
        int i42 = z6 ? 3 : i5;
        int i43 = AbstractC01171.f18x6d00e4a2[dimensionBehaviour.ordinal()];
        if (i43 == 1 || i43 == 2 || i43 == 3) {
            i9 = i42;
        } else if (i43 != 4) {
            i9 = i42;
        } else {
            i9 = i42;
            if (i9 != 4) {
                z12 = true;
                i10 = this.mWidthOverride;
                if (i10 != -1 && z) {
                    this.mWidthOverride = -1;
                    i2 = i10;
                    z12 = false;
                }
                i11 = this.mHeightOverride;
                if (i11 != -1 || z) {
                    i11 = i2;
                } else {
                    this.mHeightOverride = -1;
                    z12 = false;
                }
                int i44 = i11;
                if (this.mVisibility != 8) {
                    i12 = 0;
                    z12 = false;
                } else {
                    i12 = i44;
                }
                if (z11) {
                    z13 = isConnected3;
                    i13 = 8;
                } else {
                    if (!isConnected && !isConnected2 && !isConnected3) {
                        linearSystem.addEquality(createObjectVariable3, i);
                    } else if (isConnected && !isConnected2) {
                        z13 = isConnected3;
                        i13 = 8;
                        linearSystem.addEquality(createObjectVariable3, createObjectVariable5, constraintAnchor.getMargin(), 8);
                    }
                    z13 = isConnected3;
                    i13 = 8;
                }
                if (z12) {
                    if (z5) {
                        c = 3;
                        linearSystem.addEquality(createObjectVariable4, createObjectVariable3, 0, 3);
                        if (i3 > 0) {
                            linearSystem.addGreaterThan(createObjectVariable4, createObjectVariable3, i3, i13);
                        }
                        if (i4 < Integer.MAX_VALUE) {
                            linearSystem.addLowerThan(createObjectVariable4, createObjectVariable3, i4, i13);
                        }
                    } else {
                        c = 3;
                        linearSystem.addEquality(createObjectVariable4, createObjectVariable3, i12, i13);
                    }
                } else if (i41 == 2 || z6 || !(i9 == 1 || i9 == 0)) {
                    if (i38 == -2) {
                        i38 = i12;
                    }
                    if (i39 == -2) {
                        i39 = i12;
                    }
                    if (i12 > 0 && i9 != 1) {
                        i12 = 0;
                    }
                    if (i38 > 0) {
                        linearSystem.addGreaterThan(createObjectVariable4, createObjectVariable3, i38, 8);
                        i12 = Math.max(i12, i38);
                    }
                    if (i39 > 0) {
                        if ((z2 && i9 == 1) ? false : true) {
                            i14 = 8;
                            linearSystem.addLowerThan(createObjectVariable4, createObjectVariable3, i39, 8);
                        } else {
                            i14 = 8;
                        }
                        i12 = Math.min(i12, i39);
                    } else {
                        i14 = 8;
                    }
                    if (i9 != 1) {
                        if (i9 != 2) {
                            i15 = i41;
                            i16 = i38;
                            z14 = true;
                            if (z11) {
                            }
                            boolean z28 = z14;
                            if (i15 >= 2) {
                                return;
                            } else {
                                return;
                            }
                        }
                        ConstraintAnchor.Type type = ConstraintAnchor.Type.TOP;
                        ConstraintAnchor.Type type2 = constraintAnchor.mType;
                        if (type2 == type || type2 == ConstraintAnchor.Type.BOTTOM) {
                            createObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(type));
                            createObjectVariable2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                        } else {
                            createObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                            createObjectVariable2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
                        }
                        ArrayRow createRow = linearSystem.createRow();
                        i15 = i41;
                        createRow.variables.put(createObjectVariable4, -1.0f);
                        createRow.variables.put(createObjectVariable3, 1.0f);
                        createRow.variables.put(createObjectVariable2, f2);
                        createRow.variables.put(createObjectVariable, -f2);
                        linearSystem.addConstraint(createRow);
                        if (z2) {
                            z12 = false;
                        }
                        z14 = z4;
                        i16 = i38;
                        if (z11 || z8) {
                            boolean z282 = z14;
                            if (i15 >= 2 && z2 && z282) {
                                linearSystem.addGreaterThan(createObjectVariable3, solverVariable, 0, 8);
                                ConstraintAnchor constraintAnchor5 = this.mBaseline;
                                boolean z29 = z || constraintAnchor5.mTarget == null;
                                if (!z && (constraintAnchor3 = constraintAnchor5.mTarget) != null) {
                                    ConstraintWidget constraintWidget5 = constraintAnchor3.mOwner;
                                    if (constraintWidget5.mDimensionRatio != 0.0f) {
                                        DimensionBehaviour[] dimensionBehaviourArr = constraintWidget5.mListDimensionBehaviors;
                                        DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[0];
                                        DimensionBehaviour dimensionBehaviour3 = DimensionBehaviour.MATCH_CONSTRAINT;
                                        if (dimensionBehaviour2 == dimensionBehaviour3 && dimensionBehaviourArr[1] == dimensionBehaviour3) {
                                            z29 = true;
                                        }
                                    }
                                    z29 = false;
                                }
                                if (z29) {
                                    linearSystem.addGreaterThan(solverVariable2, createObjectVariable4, 0, 8);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        if (isConnected || isConnected2 || z13) {
                            if (isConnected && !isConnected2) {
                                z23 = z2;
                                constraintAnchor4 = constraintAnchor2;
                                solverVariable11 = createObjectVariable4;
                                z22 = z14;
                                i26 = (z2 && (constraintAnchor.mTarget.mOwner instanceof Barrier)) ? 8 : 5;
                                solverVariable10 = createObjectVariable6;
                            } else if (isConnected || !isConnected2) {
                                solverVariable3 = createObjectVariable6;
                                if (isConnected && isConnected2) {
                                    ConstraintWidget constraintWidget6 = constraintAnchor.mTarget.mOwner;
                                    ConstraintWidget constraintWidget7 = constraintAnchor2.mTarget.mOwner;
                                    ConstraintWidget constraintWidget8 = this.mParent;
                                    int i45 = 6;
                                    if (z12) {
                                        if (i9 == 0) {
                                            if (i39 != 0 || i16 != 0) {
                                                i35 = 5;
                                                i36 = 5;
                                                z26 = true;
                                                z27 = false;
                                                z18 = true;
                                            } else if (createObjectVariable5.isFinalValue && solverVariable3.isFinalValue) {
                                                linearSystem.addEquality(createObjectVariable3, createObjectVariable5, constraintAnchor.getMargin(), 8);
                                                linearSystem.addEquality(createObjectVariable4, solverVariable3, -constraintAnchor2.getMargin(), 8);
                                                return;
                                            } else {
                                                i35 = 8;
                                                i36 = 8;
                                                z26 = false;
                                                z27 = true;
                                                z18 = false;
                                            }
                                            if ((constraintWidget6 instanceof Barrier) || (constraintWidget7 instanceof Barrier)) {
                                                i17 = i9;
                                                i31 = 4;
                                                i32 = i35;
                                            } else {
                                                i20 = i35;
                                                i21 = i36;
                                                z19 = z27;
                                                i17 = i9;
                                                z17 = z26;
                                                solverVariable4 = solverVariable2;
                                                i19 = 6;
                                                if (z18 || createObjectVariable5 != solverVariable3 || constraintWidget6 == constraintWidget8) {
                                                    z20 = z18;
                                                    z21 = true;
                                                } else {
                                                    z21 = false;
                                                    z20 = false;
                                                }
                                                if (z17) {
                                                    if (z12 || z7 || z9 || createObjectVariable5 != solverVariable || solverVariable3 != solverVariable4) {
                                                        i28 = i20;
                                                        z25 = z21;
                                                        i29 = i19;
                                                        z23 = z2;
                                                    } else {
                                                        z23 = false;
                                                        i28 = 8;
                                                        i29 = 8;
                                                        z25 = false;
                                                    }
                                                    i23 = i21;
                                                    constraintWidget2 = constraintWidget7;
                                                    constraintAnchor4 = constraintAnchor2;
                                                    z22 = z14;
                                                    constraintWidget3 = constraintWidget6;
                                                    i22 = i16;
                                                    solverVariable6 = solverVariable3;
                                                    solverVariable8 = createObjectVariable5;
                                                    constraintWidget = constraintWidget8;
                                                    solverVariable7 = createObjectVariable4;
                                                    solverVariable9 = createObjectVariable3;
                                                    linearSystem.addCentering(createObjectVariable3, createObjectVariable5, constraintAnchor.getMargin(), f, solverVariable3, createObjectVariable4, constraintAnchor2.getMargin(), i29);
                                                    i20 = i28;
                                                    z21 = z25;
                                                } else {
                                                    constraintAnchor4 = constraintAnchor2;
                                                    solverVariable6 = solverVariable3;
                                                    solverVariable7 = createObjectVariable4;
                                                    z22 = z14;
                                                    i22 = i16;
                                                    constraintWidget = constraintWidget8;
                                                    i23 = i21;
                                                    constraintWidget2 = constraintWidget7;
                                                    constraintWidget3 = constraintWidget6;
                                                    solverVariable8 = createObjectVariable5;
                                                    solverVariable9 = createObjectVariable3;
                                                    z23 = z2;
                                                }
                                                if (this.mVisibility == 8) {
                                                    HashSet hashSet = constraintAnchor4.mDependents;
                                                    if (!(hashSet != null && hashSet.size() > 0)) {
                                                        return;
                                                    }
                                                }
                                                if (z20) {
                                                    solverVariable10 = solverVariable6;
                                                    if (z23 && solverVariable8 != solverVariable10 && !z12 && ((constraintWidget3 instanceof Barrier) || (constraintWidget2 instanceof Barrier))) {
                                                        i20 = 6;
                                                    }
                                                    linearSystem.addGreaterThan(solverVariable9, solverVariable8, constraintAnchor.getMargin(), i20);
                                                    solverVariable11 = solverVariable7;
                                                    linearSystem.addLowerThan(solverVariable11, solverVariable10, -constraintAnchor2.getMargin(), i20);
                                                } else {
                                                    solverVariable10 = solverVariable6;
                                                    solverVariable11 = solverVariable7;
                                                }
                                                if (z23 || !z10 || (constraintWidget3 instanceof Barrier) || (constraintWidget2 instanceof Barrier)) {
                                                    constraintWidget4 = constraintWidget;
                                                } else {
                                                    constraintWidget4 = constraintWidget;
                                                    if (constraintWidget2 != constraintWidget4) {
                                                        i24 = 6;
                                                        i20 = 6;
                                                        z24 = true;
                                                        if (z24) {
                                                            if (z19 && (!z9 || z3)) {
                                                                if (constraintWidget3 != constraintWidget4 && constraintWidget2 != constraintWidget4) {
                                                                    i45 = i24;
                                                                }
                                                                if ((constraintWidget3 instanceof Guideline) || (constraintWidget2 instanceof Guideline)) {
                                                                    i45 = 5;
                                                                }
                                                                if ((constraintWidget3 instanceof Barrier) || (constraintWidget2 instanceof Barrier)) {
                                                                    i45 = 5;
                                                                }
                                                                i24 = Math.max(z9 ? 5 : i45, i24);
                                                            }
                                                            if (z23) {
                                                                i24 = Math.min(i20, i24);
                                                                if (z6 && !z9 && (constraintWidget3 == constraintWidget4 || constraintWidget2 == constraintWidget4)) {
                                                                    i27 = 4;
                                                                    linearSystem.addEquality(solverVariable9, solverVariable8, constraintAnchor.getMargin(), i27);
                                                                    linearSystem.addEquality(solverVariable11, solverVariable10, -constraintAnchor2.getMargin(), i27);
                                                                }
                                                            }
                                                            i27 = i24;
                                                            linearSystem.addEquality(solverVariable9, solverVariable8, constraintAnchor.getMargin(), i27);
                                                            linearSystem.addEquality(solverVariable11, solverVariable10, -constraintAnchor2.getMargin(), i27);
                                                        }
                                                        if (z23) {
                                                            SolverVariable solverVariable13 = solverVariable8;
                                                            int margin = solverVariable == solverVariable13 ? constraintAnchor.getMargin() : 0;
                                                            if (solverVariable13 != solverVariable) {
                                                                linearSystem.addGreaterThan(solverVariable9, solverVariable, margin, 5);
                                                            }
                                                        }
                                                        if (z23 || !z12 || i3 != 0 || i22 != 0) {
                                                            i25 = 5;
                                                        } else if (z12 && i17 == 3) {
                                                            linearSystem.addGreaterThan(solverVariable11, solverVariable9, 0, 8);
                                                            i26 = 5;
                                                        } else {
                                                            i25 = 5;
                                                            linearSystem.addGreaterThan(solverVariable11, solverVariable9, 0, 5);
                                                        }
                                                        i26 = i25;
                                                    }
                                                }
                                                i24 = i23;
                                                z24 = z21;
                                                if (z24) {
                                                }
                                                if (z23) {
                                                }
                                                if (z23) {
                                                }
                                                i25 = 5;
                                                i26 = i25;
                                            }
                                        } else {
                                            if (i9 == 2) {
                                                i33 = ((constraintWidget6 instanceof Barrier) || (constraintWidget7 instanceof Barrier)) ? 4 : 5;
                                                i34 = 5;
                                            } else if (i9 == 1) {
                                                i33 = 4;
                                                i34 = 8;
                                            } else if (i9 == 3) {
                                                i17 = i9;
                                                if (this.mResolvedDimensionRatioSide == -1) {
                                                    solverVariable4 = solverVariable2;
                                                    i19 = z9 ? z2 ? 5 : 4 : 8;
                                                    i20 = 8;
                                                    i21 = 5;
                                                } else if (z6) {
                                                    if (i6 == 2 || i6 == 1) {
                                                        i31 = 4;
                                                        i32 = 5;
                                                    } else {
                                                        i31 = 5;
                                                        i32 = 8;
                                                    }
                                                    z26 = true;
                                                    z27 = true;
                                                    z18 = true;
                                                } else {
                                                    if (i39 > 0) {
                                                        i30 = 5;
                                                    } else if (i39 != 0 || i16 != 0) {
                                                        i30 = 4;
                                                    } else if (z9) {
                                                        i20 = (constraintWidget6 == constraintWidget8 || constraintWidget7 == constraintWidget8) ? 5 : 4;
                                                        solverVariable4 = solverVariable2;
                                                        i19 = 6;
                                                        i21 = 4;
                                                    } else {
                                                        i30 = 8;
                                                    }
                                                    solverVariable4 = solverVariable2;
                                                    i21 = i30;
                                                    i19 = 6;
                                                    i20 = 5;
                                                }
                                                z17 = true;
                                                z18 = true;
                                                z19 = true;
                                                if (z18) {
                                                }
                                                z20 = z18;
                                                z21 = true;
                                                if (z17) {
                                                }
                                                if (this.mVisibility == 8) {
                                                }
                                                if (z20) {
                                                }
                                                if (z23) {
                                                }
                                                constraintWidget4 = constraintWidget;
                                                i24 = i23;
                                                z24 = z21;
                                                if (z24) {
                                                }
                                                if (z23) {
                                                }
                                                if (z23) {
                                                }
                                                i25 = 5;
                                                i26 = i25;
                                            } else {
                                                i17 = i9;
                                                solverVariable4 = solverVariable2;
                                                z15 = false;
                                                z16 = false;
                                            }
                                            i17 = i9;
                                            i19 = 6;
                                            z17 = true;
                                            z18 = true;
                                            z19 = false;
                                            solverVariable4 = solverVariable2;
                                            int i46 = i34;
                                            i21 = i33;
                                            i20 = i46;
                                            if (z18) {
                                            }
                                            z20 = z18;
                                            z21 = true;
                                            if (z17) {
                                            }
                                            if (this.mVisibility == 8) {
                                            }
                                            if (z20) {
                                            }
                                            if (z23) {
                                            }
                                            constraintWidget4 = constraintWidget;
                                            i24 = i23;
                                            z24 = z21;
                                            if (z24) {
                                            }
                                            if (z23) {
                                            }
                                            if (z23) {
                                            }
                                            i25 = 5;
                                            i26 = i25;
                                        }
                                        i21 = i31;
                                        i20 = i32;
                                        z19 = z27;
                                        solverVariable4 = solverVariable2;
                                        z17 = z26;
                                        i19 = 6;
                                        if (z18) {
                                        }
                                        z20 = z18;
                                        z21 = true;
                                        if (z17) {
                                        }
                                        if (this.mVisibility == 8) {
                                        }
                                        if (z20) {
                                        }
                                        if (z23) {
                                        }
                                        constraintWidget4 = constraintWidget;
                                        i24 = i23;
                                        z24 = z21;
                                        if (z24) {
                                        }
                                        if (z23) {
                                        }
                                        if (z23) {
                                        }
                                        i25 = 5;
                                        i26 = i25;
                                    } else {
                                        i17 = i9;
                                        if (createObjectVariable5.isFinalValue && solverVariable3.isFinalValue) {
                                            linearSystem.addCentering(createObjectVariable3, createObjectVariable5, constraintAnchor.getMargin(), f, solverVariable3, createObjectVariable4, constraintAnchor2.getMargin(), 8);
                                            if (z2 && z14) {
                                                if (constraintAnchor2.mTarget != null) {
                                                    i18 = constraintAnchor2.getMargin();
                                                    solverVariable5 = solverVariable2;
                                                } else {
                                                    solverVariable5 = solverVariable2;
                                                    i18 = 0;
                                                }
                                                if (solverVariable3 != solverVariable5) {
                                                    linearSystem.addGreaterThan(solverVariable5, createObjectVariable4, i18, 5);
                                                    return;
                                                }
                                                return;
                                            }
                                            return;
                                        }
                                        solverVariable4 = solverVariable2;
                                        z15 = true;
                                        z16 = true;
                                    }
                                    z17 = z15;
                                    z18 = z16;
                                    i19 = 6;
                                    i20 = 5;
                                    i21 = 4;
                                    z19 = false;
                                    if (z18) {
                                    }
                                    z20 = z18;
                                    z21 = true;
                                    if (z17) {
                                    }
                                    if (this.mVisibility == 8) {
                                    }
                                    if (z20) {
                                    }
                                    if (z23) {
                                    }
                                    constraintWidget4 = constraintWidget;
                                    i24 = i23;
                                    z24 = z21;
                                    if (z24) {
                                    }
                                    if (z23) {
                                    }
                                    if (z23) {
                                    }
                                    i25 = 5;
                                    i26 = i25;
                                }
                            } else {
                                solverVariable3 = createObjectVariable6;
                                linearSystem.addEquality(createObjectVariable4, solverVariable3, -constraintAnchor2.getMargin(), 8);
                                if (z2) {
                                    linearSystem.addGreaterThan(createObjectVariable3, solverVariable, 0, 5);
                                    constraintAnchor4 = constraintAnchor2;
                                    i25 = 5;
                                    solverVariable10 = solverVariable3;
                                    solverVariable11 = createObjectVariable4;
                                    z22 = z14;
                                    z23 = z2;
                                    i26 = i25;
                                }
                            }
                            if (z23 || !z22) {
                                return;
                            }
                            if (constraintAnchor4.mTarget != null) {
                                i37 = constraintAnchor2.getMargin();
                                solverVariable12 = solverVariable2;
                            } else {
                                solverVariable12 = solverVariable2;
                                i37 = 0;
                            }
                            if (solverVariable10 != solverVariable12) {
                                linearSystem.addGreaterThan(solverVariable12, solverVariable11, i37, i26);
                                return;
                            }
                            return;
                        }
                        solverVariable3 = createObjectVariable6;
                        constraintAnchor4 = constraintAnchor2;
                        solverVariable10 = solverVariable3;
                        solverVariable11 = createObjectVariable4;
                        z22 = z14;
                        i25 = 5;
                        z23 = z2;
                        i26 = i25;
                        if (z23) {
                            return;
                        } else {
                            return;
                        }
                    }
                    if (z2) {
                        linearSystem.addEquality(createObjectVariable4, createObjectVariable3, i12, i14);
                    } else if (z8) {
                        linearSystem.addEquality(createObjectVariable4, createObjectVariable3, i12, 5);
                        linearSystem.addLowerThan(createObjectVariable4, createObjectVariable3, i12, i14);
                    } else {
                        linearSystem.addEquality(createObjectVariable4, createObjectVariable3, i12, 5);
                        linearSystem.addLowerThan(createObjectVariable4, createObjectVariable3, i12, i14);
                    }
                } else {
                    int max = Math.max(i38, i12);
                    if (i39 > 0) {
                        max = Math.min(i39, max);
                    }
                    linearSystem.addEquality(createObjectVariable4, createObjectVariable3, max, 8);
                    z12 = false;
                }
                z14 = z4;
                i15 = i41;
                i16 = i38;
                if (z11) {
                }
                boolean z2822 = z14;
                if (i15 >= 2) {
                }
            }
        }
        z12 = false;
        i10 = this.mWidthOverride;
        if (i10 != -1) {
            this.mWidthOverride = -1;
            i2 = i10;
            z12 = false;
        }
        i11 = this.mHeightOverride;
        if (i11 != -1) {
        }
        i11 = i2;
        int i442 = i11;
        if (this.mVisibility != 8) {
        }
        if (z11) {
        }
        if (z12) {
        }
        z14 = z4;
        i15 = i41;
        i16 = i38;
        if (z11) {
        }
        boolean z28222 = z14;
        if (i15 >= 2) {
        }
    }

    public final void connect(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i) {
        if (constraintAnchor.mOwner == this) {
            connect(constraintAnchor.mType, constraintAnchor2.mOwner, constraintAnchor2.mType, i);
        }
    }

    public void copy(ConstraintWidget constraintWidget, HashMap hashMap) {
        this.mHorizontalResolution = constraintWidget.mHorizontalResolution;
        this.mVerticalResolution = constraintWidget.mVerticalResolution;
        this.mMatchConstraintDefaultWidth = constraintWidget.mMatchConstraintDefaultWidth;
        this.mMatchConstraintDefaultHeight = constraintWidget.mMatchConstraintDefaultHeight;
        int[] iArr = constraintWidget.mResolvedMatchConstraintDefault;
        int i = iArr[0];
        int[] iArr2 = this.mResolvedMatchConstraintDefault;
        iArr2[0] = i;
        iArr2[1] = iArr[1];
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
        this.hasBaseline = constraintWidget.hasBaseline;
        this.inPlaceholder = constraintWidget.inPlaceholder;
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mListDimensionBehaviors = (DimensionBehaviour[]) Arrays.copyOf(this.mListDimensionBehaviors, 2);
        this.mParent = this.mParent == null ? null : (ConstraintWidget) hashMap.get(constraintWidget.mParent);
        this.mWidth = constraintWidget.mWidth;
        this.mHeight = constraintWidget.mHeight;
        this.mDimensionRatio = constraintWidget.mDimensionRatio;
        this.mDimensionRatioSide = constraintWidget.mDimensionRatioSide;
        this.f15mX = constraintWidget.f15mX;
        this.f16mY = constraintWidget.f16mY;
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
        float[] fArr = constraintWidget.mWeight;
        float f = fArr[0];
        float[] fArr2 = this.mWeight;
        fArr2[0] = f;
        fArr2[1] = fArr[1];
        ConstraintWidget[] constraintWidgetArr = constraintWidget.mListNextMatchConstraintsWidget;
        ConstraintWidget constraintWidget2 = constraintWidgetArr[0];
        ConstraintWidget[] constraintWidgetArr2 = this.mListNextMatchConstraintsWidget;
        constraintWidgetArr2[0] = constraintWidget2;
        constraintWidgetArr2[1] = constraintWidgetArr[1];
        ConstraintWidget[] constraintWidgetArr3 = constraintWidget.mNextChainWidget;
        ConstraintWidget constraintWidget3 = constraintWidgetArr3[0];
        ConstraintWidget[] constraintWidgetArr4 = this.mNextChainWidget;
        constraintWidgetArr4[0] = constraintWidget3;
        constraintWidgetArr4[1] = constraintWidgetArr3[1];
        ConstraintWidget constraintWidget4 = constraintWidget.mHorizontalNextWidget;
        this.mHorizontalNextWidget = constraintWidget4 == null ? null : (ConstraintWidget) hashMap.get(constraintWidget4);
        ConstraintWidget constraintWidget5 = constraintWidget.mVerticalNextWidget;
        this.mVerticalNextWidget = constraintWidget5 != null ? (ConstraintWidget) hashMap.get(constraintWidget5) : null;
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
        if (this.horizontalRun == null) {
            this.horizontalRun = new HorizontalWidgetRun(this);
        }
        if (this.verticalRun == null) {
            this.verticalRun = new VerticalWidgetRun(this);
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        switch (AbstractC01171.f17x6930e354[type.ordinal()]) {
            case 1:
                return this.mLeft;
            case 2:
                return this.mTop;
            case 3:
                return this.mRight;
            case 4:
                return this.mBottom;
            case 5:
                return this.mBaseline;
            case 6:
                return this.mCenter;
            case 7:
                return this.mCenterX;
            case 8:
                return this.mCenterY;
            case 9:
                return null;
            default:
                throw new AssertionError(type.name());
        }
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

    public final int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
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

    public final int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public final int getX() {
        ConstraintWidget constraintWidget = this.mParent;
        return (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) ? this.f15mX : ((ConstraintWidgetContainer) constraintWidget).mPaddingLeft + this.f15mX;
    }

    public final int getY() {
        ConstraintWidget constraintWidget = this.mParent;
        return (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) ? this.f16mY : ((ConstraintWidgetContainer) constraintWidget).mPaddingTop + this.f16mY;
    }

    public final boolean hasDanglingDimension(int i) {
        if (i == 0) {
            return (this.mLeft.mTarget != null ? 1 : 0) + (this.mRight.mTarget != null ? 1 : 0) < 2;
        }
        return ((this.mTop.mTarget != null ? 1 : 0) + (this.mBottom.mTarget != null ? 1 : 0)) + (this.mBaseline.mTarget != null ? 1 : 0) < 2;
    }

    public final boolean hasResolvedTargets(int i, int i2) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        ConstraintAnchor constraintAnchor3;
        ConstraintAnchor constraintAnchor4;
        if (i == 0) {
            ConstraintAnchor constraintAnchor5 = this.mLeft;
            ConstraintAnchor constraintAnchor6 = constraintAnchor5.mTarget;
            if (constraintAnchor6 != null && constraintAnchor6.mHasFinalValue && (constraintAnchor4 = (constraintAnchor3 = this.mRight).mTarget) != null && constraintAnchor4.mHasFinalValue) {
                return (constraintAnchor4.getFinalValue() - constraintAnchor3.getMargin()) - (constraintAnchor5.getMargin() + constraintAnchor5.mTarget.getFinalValue()) >= i2;
            }
        } else {
            ConstraintAnchor constraintAnchor7 = this.mTop;
            ConstraintAnchor constraintAnchor8 = constraintAnchor7.mTarget;
            if (constraintAnchor8 != null && constraintAnchor8.mHasFinalValue && (constraintAnchor2 = (constraintAnchor = this.mBottom).mTarget) != null && constraintAnchor2.mHasFinalValue) {
                return (constraintAnchor2.getFinalValue() - constraintAnchor.getMargin()) - (constraintAnchor7.getMargin() + constraintAnchor7.mTarget.getFinalValue()) >= i2;
            }
        }
        return false;
    }

    public final void immediateConnect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i, int i2) {
        getAnchor(type).connect(constraintWidget.getAnchor(type2), i, i2, true);
    }

    public final boolean isChainHead(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        int i2 = i * 2;
        ConstraintAnchor[] constraintAnchorArr = this.mListAnchors;
        ConstraintAnchor constraintAnchor3 = constraintAnchorArr[i2];
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return (constraintAnchor4 == null || constraintAnchor4.mTarget == constraintAnchor3 || (constraintAnchor2 = (constraintAnchor = constraintAnchorArr[i2 + 1]).mTarget) == null || constraintAnchor2.mTarget != constraintAnchor) ? false : true;
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

    public final boolean isMeasureRequested() {
        return this.mMeasureRequested && this.mVisibility != 8;
    }

    public boolean isResolvedHorizontally() {
        return this.resolvedHorizontal || (this.mLeft.mHasFinalValue && this.mRight.mHasFinalValue);
    }

    public boolean isResolvedVertically() {
        return this.resolvedVertical || (this.mTop.mHasFinalValue && this.mBottom.mHasFinalValue);
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
        this.mCircleConstraintAngle = 0.0f;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.f15mX = 0;
        this.f16mY = 0;
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
        ArrayList arrayList = this.mAnchors;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((ConstraintAnchor) arrayList.get(i)).reset();
        }
    }

    public final void resetFinalResolution() {
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        ArrayList arrayList = this.mAnchors;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor) arrayList.get(i);
            constraintAnchor.mHasFinalValue = false;
            constraintAnchor.mFinalValue = 0;
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

    public final void setFinalHorizontal(int i, int i2) {
        if (this.resolvedHorizontal) {
            return;
        }
        this.mLeft.setFinalValue(i);
        this.mRight.setFinalValue(i2);
        this.f15mX = i;
        this.mWidth = i2 - i;
        this.resolvedHorizontal = true;
    }

    public final void setFinalVertical(int i, int i2) {
        if (this.resolvedVertical) {
            return;
        }
        this.mTop.setFinalValue(i);
        this.mBottom.setFinalValue(i2);
        this.f16mY = i;
        this.mHeight = i2 - i;
        if (this.hasBaseline) {
            this.mBaseline.setFinalValue(i + this.mBaselineDistance);
        }
        this.resolvedVertical = true;
    }

    public final void setHeight(int i) {
        this.mHeight = i;
        int i2 = this.mMinHeight;
        if (i < i2) {
            this.mHeight = i2;
        }
    }

    public final void setHorizontalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[0] = dimensionBehaviour;
    }

    public final void setVerticalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[1] = dimensionBehaviour;
    }

    public final void setWidth(int i) {
        this.mWidth = i;
        int i2 = this.mMinWidth;
        if (i < i2) {
            this.mWidth = i2;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mType != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("type: "), this.mType, " ") : "");
        sb.append(this.mDebugName != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("id: "), this.mDebugName, " ") : "");
        sb.append("(");
        sb.append(this.f15mX);
        sb.append(", ");
        sb.append(this.f16mY);
        sb.append(") - (");
        sb.append(this.mWidth);
        sb.append(" x ");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.mHeight, ")");
    }

    public void updateFromRuns(boolean z, boolean z2) {
        int i;
        int i2;
        HorizontalWidgetRun horizontalWidgetRun = this.horizontalRun;
        boolean z3 = z & horizontalWidgetRun.resolved;
        VerticalWidgetRun verticalWidgetRun = this.verticalRun;
        boolean z4 = z2 & verticalWidgetRun.resolved;
        int i3 = horizontalWidgetRun.start.value;
        int i4 = verticalWidgetRun.start.value;
        int i5 = horizontalWidgetRun.end.value;
        int i6 = verticalWidgetRun.end.value;
        int i7 = i6 - i4;
        if (i5 - i3 < 0 || i7 < 0 || i3 == Integer.MIN_VALUE || i3 == Integer.MAX_VALUE || i4 == Integer.MIN_VALUE || i4 == Integer.MAX_VALUE || i5 == Integer.MIN_VALUE || i5 == Integer.MAX_VALUE || i6 == Integer.MIN_VALUE || i6 == Integer.MAX_VALUE) {
            i5 = 0;
            i6 = 0;
            i3 = 0;
            i4 = 0;
        }
        int i8 = i5 - i3;
        int i9 = i6 - i4;
        if (z3) {
            this.f15mX = i3;
        }
        if (z4) {
            this.f16mY = i4;
        }
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (z3) {
            if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED && i8 < (i2 = this.mWidth)) {
                i8 = i2;
            }
            this.mWidth = i8;
            int i10 = this.mMinWidth;
            if (i8 < i10) {
                this.mWidth = i10;
            }
        }
        if (z4) {
            if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED && i9 < (i = this.mHeight)) {
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
        linearSystem.getClass();
        int objectVariableValue = LinearSystem.getObjectVariableValue(this.mLeft);
        int objectVariableValue2 = LinearSystem.getObjectVariableValue(this.mTop);
        int objectVariableValue3 = LinearSystem.getObjectVariableValue(this.mRight);
        int objectVariableValue4 = LinearSystem.getObjectVariableValue(this.mBottom);
        if (z && (horizontalWidgetRun = this.horizontalRun) != null) {
            DependencyNode dependencyNode = horizontalWidgetRun.start;
            if (dependencyNode.resolved) {
                DependencyNode dependencyNode2 = horizontalWidgetRun.end;
                if (dependencyNode2.resolved) {
                    objectVariableValue = dependencyNode.value;
                    objectVariableValue3 = dependencyNode2.value;
                }
            }
        }
        if (z && (verticalWidgetRun = this.verticalRun) != null) {
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
        this.f15mX = objectVariableValue;
        this.f16mY = objectVariableValue2;
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
        if (i8 > 0 && dimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mWidth = Math.min(this.mWidth, i8);
        }
        int i9 = this.mMatchConstraintMaxHeight;
        if (i9 > 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT) {
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
        ConstraintAnchor.Type type3;
        ConstraintAnchor.Type type4;
        boolean z;
        ConstraintAnchor.Type type5 = ConstraintAnchor.Type.CENTER;
        if (type == type5) {
            if (type2 == type5) {
                ConstraintAnchor.Type type6 = ConstraintAnchor.Type.LEFT;
                ConstraintAnchor anchor = getAnchor(type6);
                ConstraintAnchor.Type type7 = ConstraintAnchor.Type.RIGHT;
                ConstraintAnchor anchor2 = getAnchor(type7);
                ConstraintAnchor.Type type8 = ConstraintAnchor.Type.TOP;
                ConstraintAnchor anchor3 = getAnchor(type8);
                ConstraintAnchor.Type type9 = ConstraintAnchor.Type.BOTTOM;
                ConstraintAnchor anchor4 = getAnchor(type9);
                boolean z2 = true;
                if ((anchor == null || !anchor.isConnected()) && (anchor2 == null || !anchor2.isConnected())) {
                    connect(type6, constraintWidget, type6, 0);
                    connect(type7, constraintWidget, type7, 0);
                    z = true;
                } else {
                    z = false;
                }
                if ((anchor3 == null || !anchor3.isConnected()) && (anchor4 == null || !anchor4.isConnected())) {
                    connect(type8, constraintWidget, type8, 0);
                    connect(type9, constraintWidget, type9, 0);
                } else {
                    z2 = false;
                }
                if (z && z2) {
                    getAnchor(type5).connect(constraintWidget.getAnchor(type5), 0);
                    return;
                }
                if (z) {
                    ConstraintAnchor.Type type10 = ConstraintAnchor.Type.CENTER_X;
                    getAnchor(type10).connect(constraintWidget.getAnchor(type10), 0);
                    return;
                } else {
                    if (z2) {
                        ConstraintAnchor.Type type11 = ConstraintAnchor.Type.CENTER_Y;
                        getAnchor(type11).connect(constraintWidget.getAnchor(type11), 0);
                        return;
                    }
                    return;
                }
            }
            ConstraintAnchor.Type type12 = ConstraintAnchor.Type.LEFT;
            if (type2 != type12 && type2 != ConstraintAnchor.Type.RIGHT) {
                ConstraintAnchor.Type type13 = ConstraintAnchor.Type.TOP;
                if (type2 == type13 || type2 == ConstraintAnchor.Type.BOTTOM) {
                    connect(type13, constraintWidget, type2, 0);
                    connect(ConstraintAnchor.Type.BOTTOM, constraintWidget, type2, 0);
                    getAnchor(type5).connect(constraintWidget.getAnchor(type2), 0);
                    return;
                }
                return;
            }
            connect(type12, constraintWidget, type2, 0);
            connect(ConstraintAnchor.Type.RIGHT, constraintWidget, type2, 0);
            getAnchor(type5).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        ConstraintAnchor.Type type14 = ConstraintAnchor.Type.CENTER_X;
        if (type == type14 && (type2 == (type4 = ConstraintAnchor.Type.LEFT) || type2 == ConstraintAnchor.Type.RIGHT)) {
            ConstraintAnchor anchor5 = getAnchor(type4);
            ConstraintAnchor anchor6 = constraintWidget.getAnchor(type2);
            ConstraintAnchor anchor7 = getAnchor(ConstraintAnchor.Type.RIGHT);
            anchor5.connect(anchor6, 0);
            anchor7.connect(anchor6, 0);
            getAnchor(type14).connect(anchor6, 0);
            return;
        }
        ConstraintAnchor.Type type15 = ConstraintAnchor.Type.CENTER_Y;
        if (type == type15 && (type2 == (type3 = ConstraintAnchor.Type.TOP) || type2 == ConstraintAnchor.Type.BOTTOM)) {
            ConstraintAnchor anchor8 = constraintWidget.getAnchor(type2);
            getAnchor(type3).connect(anchor8, 0);
            getAnchor(ConstraintAnchor.Type.BOTTOM).connect(anchor8, 0);
            getAnchor(type15).connect(anchor8, 0);
            return;
        }
        if (type == type14 && type2 == type14) {
            ConstraintAnchor.Type type16 = ConstraintAnchor.Type.LEFT;
            getAnchor(type16).connect(constraintWidget.getAnchor(type16), 0);
            ConstraintAnchor.Type type17 = ConstraintAnchor.Type.RIGHT;
            getAnchor(type17).connect(constraintWidget.getAnchor(type17), 0);
            getAnchor(type14).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        if (type == type15 && type2 == type15) {
            ConstraintAnchor.Type type18 = ConstraintAnchor.Type.TOP;
            getAnchor(type18).connect(constraintWidget.getAnchor(type18), 0);
            ConstraintAnchor.Type type19 = ConstraintAnchor.Type.BOTTOM;
            getAnchor(type19).connect(constraintWidget.getAnchor(type19), 0);
            getAnchor(type15).connect(constraintWidget.getAnchor(type2), 0);
            return;
        }
        ConstraintAnchor anchor9 = getAnchor(type);
        ConstraintAnchor anchor10 = constraintWidget.getAnchor(type2);
        if (anchor9.isValidConnection(anchor10)) {
            ConstraintAnchor.Type type20 = ConstraintAnchor.Type.BASELINE;
            if (type == type20) {
                ConstraintAnchor anchor11 = getAnchor(ConstraintAnchor.Type.TOP);
                ConstraintAnchor anchor12 = getAnchor(ConstraintAnchor.Type.BOTTOM);
                if (anchor11 != null) {
                    anchor11.reset();
                }
                if (anchor12 != null) {
                    anchor12.reset();
                }
            } else if (type != ConstraintAnchor.Type.TOP && type != ConstraintAnchor.Type.BOTTOM) {
                if (type == ConstraintAnchor.Type.LEFT || type == ConstraintAnchor.Type.RIGHT) {
                    ConstraintAnchor anchor13 = getAnchor(type5);
                    if (anchor13.mTarget != anchor10) {
                        anchor13.reset();
                    }
                    ConstraintAnchor opposite = getAnchor(type).getOpposite();
                    ConstraintAnchor anchor14 = getAnchor(type14);
                    if (anchor14.isConnected()) {
                        opposite.reset();
                        anchor14.reset();
                    }
                }
            } else {
                ConstraintAnchor anchor15 = getAnchor(type20);
                if (anchor15 != null) {
                    anchor15.reset();
                }
                ConstraintAnchor anchor16 = getAnchor(type5);
                if (anchor16.mTarget != anchor10) {
                    anchor16.reset();
                }
                ConstraintAnchor opposite2 = getAnchor(type).getOpposite();
                ConstraintAnchor anchor17 = getAnchor(type15);
                if (anchor17.isConnected()) {
                    opposite2.reset();
                    anchor17.reset();
                }
            }
            anchor9.connect(anchor10, i);
        }
    }

    public ConstraintWidget(String str) {
        this.measured = false;
        this.horizontalRun = null;
        this.verticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mMeasureRequested = true;
        this.OPTIMIZE_WRAP_ON_RESOLVED = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        new WidgetFrame(this);
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
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
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
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
        this.mAnchors = new ArrayList();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.f15mX = 0;
        this.f16mY = 0;
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
        addAnchors();
        this.mDebugName = str;
    }

    public ConstraintWidget(int i, int i2, int i3, int i4) {
        this.measured = false;
        this.horizontalRun = null;
        this.verticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mMeasureRequested = true;
        this.OPTIMIZE_WRAP_ON_RESOLVED = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        new WidgetFrame(this);
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
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
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
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
        this.mAnchors = new ArrayList();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
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
        this.f15mX = i;
        this.f16mY = i2;
        this.mWidth = i3;
        this.mHeight = i4;
        addAnchors();
    }

    public ConstraintWidget(String str, int i, int i2, int i3, int i4) {
        this(i, i2, i3, i4);
        this.mDebugName = str;
    }

    public ConstraintWidget(int i, int i2) {
        this(0, 0, i, i2);
    }

    public ConstraintWidget(String str, int i, int i2) {
        this(i, i2);
        this.mDebugName = str;
    }
}
