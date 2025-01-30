package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph;
import androidx.constraintlayout.core.widgets.analyzer.Direct;
import androidx.constraintlayout.core.widgets.analyzer.Grouping;
import androidx.constraintlayout.core.widgets.analyzer.WidgetGroup;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ConstraintWidgetContainer extends WidgetContainer {
    public WeakReference horizontalWrapMax;
    public WeakReference horizontalWrapMin;
    public final BasicMeasure mBasicMeasureSolver;
    public final DependencyGraph mDependencyGraph;
    public boolean mHeightMeasuredTooSmall;
    public ChainHead[] mHorizontalChainsArray;
    public int mHorizontalChainsSize;
    public boolean mIsRtl;
    public final BasicMeasure.Measure mMeasure;
    public BasicMeasure.Measurer mMeasurer;
    public int mOptimizationLevel;
    public int mPaddingLeft;
    public int mPaddingTop;
    public final LinearSystem mSystem;
    public ChainHead[] mVerticalChainsArray;
    public int mVerticalChainsSize;
    public boolean mWidthMeasuredTooSmall;
    public int pass;
    public WeakReference verticalWrapMax;
    public WeakReference verticalWrapMin;
    public final HashSet widgetsToAdd;

    public ConstraintWidgetContainer() {
        this.mBasicMeasureSolver = new BasicMeasure(this);
        this.mDependencyGraph = new DependencyGraph(this);
        this.mMeasurer = null;
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mOptimizationLevel = 257;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.verticalWrapMin = null;
        this.horizontalWrapMin = null;
        this.verticalWrapMax = null;
        this.horizontalWrapMax = null;
        this.widgetsToAdd = new HashSet();
        this.mMeasure = new BasicMeasure.Measure();
    }

    public static void measure(ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer, BasicMeasure.Measure measure) {
        int i;
        int i2;
        if (measurer == null) {
            return;
        }
        if (constraintWidget.mVisibility == 8 || (constraintWidget instanceof Guideline) || (constraintWidget instanceof Barrier)) {
            measure.measuredWidth = 0;
            measure.measuredHeight = 0;
            return;
        }
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
        measure.horizontalBehavior = dimensionBehaviourArr[0];
        measure.verticalBehavior = dimensionBehaviourArr[1];
        measure.horizontalDimension = constraintWidget.getWidth();
        measure.verticalDimension = constraintWidget.getHeight();
        measure.measuredNeedsSolverPass = false;
        measure.measureStrategy = 0;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = measure.horizontalBehavior;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean z = dimensionBehaviour == dimensionBehaviour2;
        boolean z2 = measure.verticalBehavior == dimensionBehaviour2;
        boolean z3 = z && constraintWidget.mDimensionRatio > 0.0f;
        boolean z4 = z2 && constraintWidget.mDimensionRatio > 0.0f;
        if (z && constraintWidget.hasDanglingDimension(0) && constraintWidget.mMatchConstraintDefaultWidth == 0 && !z3) {
            measure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (z2 && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                measure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
            }
            z = false;
        }
        if (z2 && constraintWidget.hasDanglingDimension(1) && constraintWidget.mMatchConstraintDefaultHeight == 0 && !z4) {
            measure.verticalBehavior = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (z && constraintWidget.mMatchConstraintDefaultWidth == 0) {
                measure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
            }
            z2 = false;
        }
        if (constraintWidget.isResolvedHorizontally()) {
            measure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
            z = false;
        }
        if (constraintWidget.isResolvedVertically()) {
            measure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
            z2 = false;
        }
        int[] iArr = constraintWidget.mResolvedMatchConstraintDefault;
        if (z3) {
            if (iArr[0] == 4) {
                measure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
            } else if (!z2) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = measure.verticalBehavior;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.FIXED;
                if (dimensionBehaviour3 == dimensionBehaviour4) {
                    i2 = measure.verticalDimension;
                } else {
                    measure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    ((ConstraintLayout.Measurer) measurer).measure(constraintWidget, measure);
                    i2 = measure.measuredHeight;
                }
                measure.horizontalBehavior = dimensionBehaviour4;
                measure.horizontalDimension = (int) (constraintWidget.mDimensionRatio * i2);
            }
        }
        if (z4) {
            if (iArr[1] == 4) {
                measure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
            } else if (!z) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = measure.horizontalBehavior;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.FIXED;
                if (dimensionBehaviour5 == dimensionBehaviour6) {
                    i = measure.horizontalDimension;
                } else {
                    measure.verticalBehavior = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    ((ConstraintLayout.Measurer) measurer).measure(constraintWidget, measure);
                    i = measure.measuredWidth;
                }
                measure.verticalBehavior = dimensionBehaviour6;
                if (constraintWidget.mDimensionRatioSide == -1) {
                    measure.verticalDimension = (int) (i / constraintWidget.mDimensionRatio);
                } else {
                    measure.verticalDimension = (int) (constraintWidget.mDimensionRatio * i);
                }
            }
        }
        ((ConstraintLayout.Measurer) measurer).measure(constraintWidget, measure);
        constraintWidget.setWidth(measure.measuredWidth);
        constraintWidget.setHeight(measure.measuredHeight);
        constraintWidget.hasBaseline = measure.measuredHasBaseline;
        int i3 = measure.measuredBaseline;
        constraintWidget.mBaselineDistance = i3;
        constraintWidget.hasBaseline = i3 > 0;
        measure.measureStrategy = 0;
    }

    public final void addChain(ConstraintWidget constraintWidget, int i) {
        if (i == 0) {
            int i2 = this.mHorizontalChainsSize + 1;
            ChainHead[] chainHeadArr = this.mHorizontalChainsArray;
            if (i2 >= chainHeadArr.length) {
                this.mHorizontalChainsArray = (ChainHead[]) Arrays.copyOf(chainHeadArr, chainHeadArr.length * 2);
            }
            this.mHorizontalChainsArray[this.mHorizontalChainsSize] = new ChainHead(constraintWidget, 0, this.mIsRtl);
            this.mHorizontalChainsSize++;
            return;
        }
        if (i == 1) {
            int i3 = this.mVerticalChainsSize + 1;
            ChainHead[] chainHeadArr2 = this.mVerticalChainsArray;
            if (i3 >= chainHeadArr2.length) {
                this.mVerticalChainsArray = (ChainHead[]) Arrays.copyOf(chainHeadArr2, chainHeadArr2.length * 2);
            }
            this.mVerticalChainsArray[this.mVerticalChainsSize] = new ChainHead(constraintWidget, 1, this.mIsRtl);
            this.mVerticalChainsSize++;
        }
    }

    public final void addChildrenToSolver(LinearSystem linearSystem) {
        boolean z;
        boolean optimizeFor = optimizeFor(64);
        addToSolver(linearSystem, optimizeFor);
        int size = this.mChildren.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = (ConstraintWidget) this.mChildren.get(i);
            boolean[] zArr = constraintWidget.mIsInBarrier;
            zArr[0] = false;
            zArr[1] = false;
            if (constraintWidget instanceof Barrier) {
                z2 = true;
            }
        }
        if (z2) {
            for (int i2 = 0; i2 < size; i2++) {
                ConstraintWidget constraintWidget2 = (ConstraintWidget) this.mChildren.get(i2);
                if (constraintWidget2 instanceof Barrier) {
                    Barrier barrier = (Barrier) constraintWidget2;
                    for (int i3 = 0; i3 < barrier.mWidgetsCount; i3++) {
                        ConstraintWidget constraintWidget3 = barrier.mWidgets[i3];
                        if (barrier.mAllowsGoneWidget || constraintWidget3.allowedInBarrier()) {
                            int i4 = barrier.mBarrierType;
                            if (i4 == 0 || i4 == 1) {
                                constraintWidget3.mIsInBarrier[0] = true;
                            } else if (i4 == 2 || i4 == 3) {
                                constraintWidget3.mIsInBarrier[1] = true;
                            }
                        }
                    }
                }
            }
        }
        HashSet hashSet = this.widgetsToAdd;
        hashSet.clear();
        for (int i5 = 0; i5 < size; i5++) {
            ConstraintWidget constraintWidget4 = (ConstraintWidget) this.mChildren.get(i5);
            constraintWidget4.getClass();
            if ((constraintWidget4 instanceof VirtualLayout) || (constraintWidget4 instanceof Guideline)) {
                if (constraintWidget4 instanceof VirtualLayout) {
                    hashSet.add(constraintWidget4);
                } else {
                    constraintWidget4.addToSolver(linearSystem, optimizeFor);
                }
            }
        }
        while (hashSet.size() > 0) {
            int size2 = hashSet.size();
            Iterator it = hashSet.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                VirtualLayout virtualLayout = (VirtualLayout) ((ConstraintWidget) it.next());
                int i6 = 0;
                while (true) {
                    if (i6 >= virtualLayout.mWidgetsCount) {
                        z = false;
                        break;
                    } else {
                        if (hashSet.contains(virtualLayout.mWidgets[i6])) {
                            z = true;
                            break;
                        }
                        i6++;
                    }
                }
                if (z) {
                    virtualLayout.addToSolver(linearSystem, optimizeFor);
                    hashSet.remove(virtualLayout);
                    break;
                }
            }
            if (size2 == hashSet.size()) {
                Iterator it2 = hashSet.iterator();
                while (it2.hasNext()) {
                    ((ConstraintWidget) it2.next()).addToSolver(linearSystem, optimizeFor);
                }
                hashSet.clear();
            }
        }
        if (LinearSystem.USE_DEPENDENCY_ORDERING) {
            HashSet hashSet2 = new HashSet();
            for (int i7 = 0; i7 < size; i7++) {
                ConstraintWidget constraintWidget5 = (ConstraintWidget) this.mChildren.get(i7);
                constraintWidget5.getClass();
                if (!((constraintWidget5 instanceof VirtualLayout) || (constraintWidget5 instanceof Guideline))) {
                    hashSet2.add(constraintWidget5);
                }
            }
            addChildrenToSolverByDependency(this, linearSystem, hashSet2, this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? 0 : 1, false);
            Iterator it3 = hashSet2.iterator();
            while (it3.hasNext()) {
                ConstraintWidget constraintWidget6 = (ConstraintWidget) it3.next();
                Optimizer.checkMatchParent(this, linearSystem, constraintWidget6);
                constraintWidget6.addToSolver(linearSystem, optimizeFor);
            }
        } else {
            for (int i8 = 0; i8 < size; i8++) {
                ConstraintWidget constraintWidget7 = (ConstraintWidget) this.mChildren.get(i8);
                if (constraintWidget7 instanceof ConstraintWidgetContainer) {
                    ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget7.mListDimensionBehaviors;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[1];
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    if (dimensionBehaviour == dimensionBehaviour3) {
                        constraintWidget7.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                    }
                    if (dimensionBehaviour2 == dimensionBehaviour3) {
                        constraintWidget7.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                    }
                    constraintWidget7.addToSolver(linearSystem, optimizeFor);
                    if (dimensionBehaviour == dimensionBehaviour3) {
                        constraintWidget7.setHorizontalDimensionBehaviour(dimensionBehaviour);
                    }
                    if (dimensionBehaviour2 == dimensionBehaviour3) {
                        constraintWidget7.setVerticalDimensionBehaviour(dimensionBehaviour2);
                    }
                } else {
                    Optimizer.checkMatchParent(this, linearSystem, constraintWidget7);
                    if (!((constraintWidget7 instanceof VirtualLayout) || (constraintWidget7 instanceof Guideline))) {
                        constraintWidget7.addToSolver(linearSystem, optimizeFor);
                    }
                }
            }
        }
        if (this.mHorizontalChainsSize > 0) {
            Chain.applyChainConstraints(this, linearSystem, null, 0);
        }
        if (this.mVerticalChainsSize > 0) {
            Chain.applyChainConstraints(this, linearSystem, null, 1);
        }
    }

    public final boolean directMeasureWithOrientation(int i, boolean z) {
        boolean z2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        boolean z3 = true;
        boolean z4 = z & true;
        DependencyGraph dependencyGraph = this.mDependencyGraph;
        ConstraintWidgetContainer constraintWidgetContainer = dependencyGraph.container;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidgetContainer.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = constraintWidgetContainer.getDimensionBehaviour(1);
        int x = constraintWidgetContainer.getX();
        int y = constraintWidgetContainer.getY();
        ArrayList arrayList = dependencyGraph.mRuns;
        if (z4 && (dimensionBehaviour2 == (dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || dimensionBehaviour3 == dimensionBehaviour)) {
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WidgetRun widgetRun = (WidgetRun) it.next();
                if (widgetRun.orientation == i && !widgetRun.supportsWrapComputation()) {
                    z4 = false;
                    break;
                }
            }
            if (i == 0) {
                if (z4 && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidgetContainer.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                    constraintWidgetContainer.setWidth(dependencyGraph.computeWrap(constraintWidgetContainer, 0));
                    constraintWidgetContainer.horizontalRun.dimension.resolve(constraintWidgetContainer.getWidth());
                }
            } else if (z4 && dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                constraintWidgetContainer.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                constraintWidgetContainer.setHeight(dependencyGraph.computeWrap(constraintWidgetContainer, 1));
                constraintWidgetContainer.verticalRun.dimension.resolve(constraintWidgetContainer.getHeight());
            }
        }
        if (i == 0) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = constraintWidgetContainer.mListDimensionBehaviors[0];
            if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int width = constraintWidgetContainer.getWidth() + x;
                constraintWidgetContainer.horizontalRun.end.resolve(width);
                constraintWidgetContainer.horizontalRun.dimension.resolve(width - x);
                z2 = true;
            }
            z2 = false;
        } else {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = constraintWidgetContainer.mListDimensionBehaviors[1];
            if (dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int height = constraintWidgetContainer.getHeight() + y;
                constraintWidgetContainer.verticalRun.end.resolve(height);
                constraintWidgetContainer.verticalRun.dimension.resolve(height - y);
                z2 = true;
            }
            z2 = false;
        }
        dependencyGraph.measureWidgets();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            WidgetRun widgetRun2 = (WidgetRun) it2.next();
            if (widgetRun2.orientation == i && (widgetRun2.widget != constraintWidgetContainer || widgetRun2.resolved)) {
                widgetRun2.applyToWidget();
            }
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            WidgetRun widgetRun3 = (WidgetRun) it3.next();
            if (widgetRun3.orientation == i && (z2 || widgetRun3.widget != constraintWidgetContainer)) {
                if (!widgetRun3.start.resolved || !widgetRun3.end.resolved || (!(widgetRun3 instanceof ChainRun) && !widgetRun3.dimension.resolved)) {
                    z3 = false;
                    break;
                }
            }
        }
        constraintWidgetContainer.setHorizontalDimensionBehaviour(dimensionBehaviour2);
        constraintWidgetContainer.setVerticalDimensionBehaviour(dimensionBehaviour3);
        return z3;
    }

    /* JADX WARN: Removed duplicated region for block: B:207:0x0626  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0649  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x0682  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0698 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:233:0x06a9  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x06ba  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x06d7  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x07c0  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x0824 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0830 A[LOOP:14: B:294:0x082e->B:295:0x0830, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:308:0x089b  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x08b9  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x08cb  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x0910  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x0912  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x08c8  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0807  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x091f  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x065f  */
    /* JADX WARN: Removed duplicated region for block: B:391:0x0664  */
    /* JADX WARN: Removed duplicated region for block: B:614:0x05ef  */
    /* JADX WARN: Removed duplicated region for block: B:632:0x061e A[ADDED_TO_REGION] */
    /* JADX WARN: Type inference failed for: r12v4 */
    /* JADX WARN: Type inference failed for: r12v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r12v9 */
    @Override // androidx.constraintlayout.core.widgets.WidgetContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void layout() {
        int i;
        ConstraintAnchor constraintAnchor;
        int i2;
        LinearSystem linearSystem;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
        ConstraintAnchor constraintAnchor2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z;
        char c;
        int i7;
        int i8;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        int max;
        int max2;
        ?? r12;
        int i9;
        int i10;
        int max3;
        int max4;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour4;
        int i11;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour5;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour6;
        WidgetGroup widgetGroup;
        WidgetGroup widgetGroup2;
        boolean z7;
        int measureWrap;
        LinearSystem linearSystem2;
        WidgetGroup widgetGroup3;
        WidgetGroup widgetGroup4;
        int i12;
        int i13;
        int i14;
        this.f15mX = 0;
        this.f16mY = 0;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        int size = this.mChildren.size();
        int max5 = Math.max(0, getWidth());
        int max6 = Math.max(0, getHeight());
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = dimensionBehaviourArr[1];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour8 = dimensionBehaviourArr[0];
        int i15 = this.pass;
        ConstraintAnchor constraintAnchor3 = this.mTop;
        ConstraintAnchor constraintAnchor4 = this.mLeft;
        if (i15 == 0 && Optimizer.enabled(this.mOptimizationLevel, 1)) {
            BasicMeasure.Measurer measurer = this.mMeasurer;
            BasicMeasure.Measure measure = Direct.measure;
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = this.mListDimensionBehaviors;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour9 = dimensionBehaviourArr2[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour10 = dimensionBehaviourArr2[1];
            resetFinalResolution();
            ArrayList arrayList = this.mChildren;
            int size2 = arrayList.size();
            for (int i16 = 0; i16 < size2; i16++) {
                ((ConstraintWidget) arrayList.get(i16)).resetFinalResolution();
            }
            boolean z8 = this.mIsRtl;
            if (dimensionBehaviour9 == ConstraintWidget.DimensionBehaviour.FIXED) {
                setFinalHorizontal(0, getWidth());
            } else {
                constraintAnchor4.setFinalValue(0);
                this.f15mX = 0;
            }
            boolean z9 = false;
            int i17 = 0;
            boolean z10 = false;
            while (i17 < size2) {
                ConstraintWidget constraintWidget = (ConstraintWidget) arrayList.get(i17);
                ConstraintAnchor constraintAnchor5 = constraintAnchor4;
                if (constraintWidget instanceof Guideline) {
                    Guideline guideline = (Guideline) constraintWidget;
                    i14 = max6;
                    if (guideline.mOrientation == 1) {
                        int i18 = guideline.mRelativeBegin;
                        if (i18 != -1) {
                            guideline.setFinalValue(i18);
                        } else if (guideline.mRelativeEnd != -1 && isResolvedHorizontally()) {
                            guideline.setFinalValue(getWidth() - guideline.mRelativeEnd);
                        } else if (isResolvedHorizontally()) {
                            guideline.setFinalValue((int) ((guideline.mRelativePercent * getWidth()) + 0.5f));
                        }
                        z9 = true;
                    }
                } else {
                    i14 = max6;
                    if ((constraintWidget instanceof Barrier) && ((Barrier) constraintWidget).getOrientation() == 0) {
                        z10 = true;
                    }
                }
                i17++;
                constraintAnchor4 = constraintAnchor5;
                max6 = i14;
            }
            i = max6;
            constraintAnchor = constraintAnchor4;
            if (z9) {
                for (int i19 = 0; i19 < size2; i19++) {
                    ConstraintWidget constraintWidget2 = (ConstraintWidget) arrayList.get(i19);
                    if (constraintWidget2 instanceof Guideline) {
                        Guideline guideline2 = (Guideline) constraintWidget2;
                        if (guideline2.mOrientation == 1) {
                            Direct.horizontalSolvingPass(0, guideline2, measurer, z8);
                        }
                    }
                }
            }
            Direct.horizontalSolvingPass(0, this, measurer, z8);
            if (z10) {
                for (int i20 = 0; i20 < size2; i20++) {
                    ConstraintWidget constraintWidget3 = (ConstraintWidget) arrayList.get(i20);
                    if (constraintWidget3 instanceof Barrier) {
                        Barrier barrier = (Barrier) constraintWidget3;
                        if (barrier.getOrientation() == 0 && barrier.allSolved()) {
                            Direct.horizontalSolvingPass(1, barrier, measurer, z8);
                        }
                    }
                }
            }
            if (dimensionBehaviour10 == ConstraintWidget.DimensionBehaviour.FIXED) {
                setFinalVertical(0, getHeight());
            } else {
                constraintAnchor3.setFinalValue(0);
                this.f16mY = 0;
            }
            boolean z11 = false;
            boolean z12 = false;
            for (int i21 = 0; i21 < size2; i21++) {
                ConstraintWidget constraintWidget4 = (ConstraintWidget) arrayList.get(i21);
                if (constraintWidget4 instanceof Guideline) {
                    Guideline guideline3 = (Guideline) constraintWidget4;
                    if (guideline3.mOrientation == 0) {
                        int i22 = guideline3.mRelativeBegin;
                        if (i22 != -1) {
                            guideline3.setFinalValue(i22);
                        } else if (guideline3.mRelativeEnd != -1 && isResolvedVertically()) {
                            guideline3.setFinalValue(getHeight() - guideline3.mRelativeEnd);
                        } else if (isResolvedVertically()) {
                            guideline3.setFinalValue((int) ((guideline3.mRelativePercent * getHeight()) + 0.5f));
                        }
                        z11 = true;
                    }
                } else if ((constraintWidget4 instanceof Barrier) && ((Barrier) constraintWidget4).getOrientation() == 1) {
                    z12 = true;
                }
            }
            if (z11) {
                for (int i23 = 0; i23 < size2; i23++) {
                    ConstraintWidget constraintWidget5 = (ConstraintWidget) arrayList.get(i23);
                    if (constraintWidget5 instanceof Guideline) {
                        Guideline guideline4 = (Guideline) constraintWidget5;
                        if (guideline4.mOrientation == 0) {
                            Direct.verticalSolvingPass(1, guideline4, measurer);
                        }
                    }
                }
            }
            Direct.verticalSolvingPass(0, this, measurer);
            if (z12) {
                for (int i24 = 0; i24 < size2; i24++) {
                    ConstraintWidget constraintWidget6 = (ConstraintWidget) arrayList.get(i24);
                    if (constraintWidget6 instanceof Barrier) {
                        Barrier barrier2 = (Barrier) constraintWidget6;
                        if (barrier2.getOrientation() == 1 && barrier2.allSolved()) {
                            Direct.verticalSolvingPass(1, barrier2, measurer);
                        }
                    }
                }
            }
            for (int i25 = 0; i25 < size2; i25++) {
                ConstraintWidget constraintWidget7 = (ConstraintWidget) arrayList.get(i25);
                if (constraintWidget7.isMeasureRequested() && Direct.canMeasure(constraintWidget7)) {
                    measure(constraintWidget7, measurer, Direct.measure);
                    if (!(constraintWidget7 instanceof Guideline)) {
                        Direct.horizontalSolvingPass(0, constraintWidget7, measurer, z8);
                        Direct.verticalSolvingPass(0, constraintWidget7, measurer);
                    } else if (((Guideline) constraintWidget7).mOrientation == 0) {
                        Direct.verticalSolvingPass(0, constraintWidget7, measurer);
                    } else {
                        Direct.horizontalSolvingPass(0, constraintWidget7, measurer, z8);
                    }
                }
            }
            for (int i26 = 0; i26 < size; i26++) {
                ConstraintWidget constraintWidget8 = (ConstraintWidget) this.mChildren.get(i26);
                if (constraintWidget8.isMeasureRequested() && !(constraintWidget8 instanceof Guideline) && !(constraintWidget8 instanceof Barrier) && !(constraintWidget8 instanceof VirtualLayout) && !constraintWidget8.mInVirtualLayout) {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour11 = constraintWidget8.getDimensionBehaviour(0);
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour12 = constraintWidget8.getDimensionBehaviour(1);
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour13 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    if (!(dimensionBehaviour11 == dimensionBehaviour13 && constraintWidget8.mMatchConstraintDefaultWidth != 1 && dimensionBehaviour12 == dimensionBehaviour13 && constraintWidget8.mMatchConstraintDefaultHeight != 1)) {
                        measure(constraintWidget8, this.mMeasurer, new BasicMeasure.Measure());
                    }
                }
            }
        } else {
            i = max6;
            constraintAnchor = constraintAnchor4;
        }
        LinearSystem linearSystem3 = this.mSystem;
        if (size <= 2 || !((dimensionBehaviour8 == (dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || dimensionBehaviour7 == dimensionBehaviour4) && Optimizer.enabled(this.mOptimizationLevel, 1024))) {
            i2 = size;
            linearSystem = linearSystem3;
            dimensionBehaviour = dimensionBehaviour8;
            dimensionBehaviour2 = dimensionBehaviour7;
            constraintAnchor2 = constraintAnchor3;
            i3 = i;
            i4 = max5;
        } else {
            BasicMeasure.Measurer measurer2 = this.mMeasurer;
            ArrayList arrayList2 = this.mChildren;
            int size3 = arrayList2.size();
            int i27 = 0;
            while (i27 < size3) {
                ConstraintWidget constraintWidget9 = (ConstraintWidget) arrayList2.get(i27);
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr3 = this.mListDimensionBehaviors;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour14 = dimensionBehaviourArr3[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour15 = dimensionBehaviourArr3[1];
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr4 = constraintWidget9.mListDimensionBehaviors;
                constraintAnchor2 = constraintAnchor3;
                if (!Grouping.validInGroup(dimensionBehaviour14, dimensionBehaviour15, dimensionBehaviourArr4[0], dimensionBehaviourArr4[1]) || (constraintWidget9 instanceof Flow)) {
                    i11 = max5;
                    i2 = size;
                    linearSystem = linearSystem3;
                    dimensionBehaviour5 = dimensionBehaviour8;
                    dimensionBehaviour6 = dimensionBehaviour7;
                    break;
                }
                i27++;
                constraintAnchor3 = constraintAnchor2;
            }
            constraintAnchor2 = constraintAnchor3;
            ArrayList arrayList3 = null;
            ArrayList arrayList4 = null;
            ArrayList arrayList5 = null;
            ArrayList arrayList6 = null;
            ArrayList arrayList7 = null;
            ArrayList arrayList8 = null;
            int i28 = 0;
            while (i28 < size3) {
                int i29 = size;
                ConstraintWidget constraintWidget10 = (ConstraintWidget) arrayList2.get(i28);
                ConstraintWidget.DimensionBehaviour dimensionBehaviour16 = dimensionBehaviour7;
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr5 = this.mListDimensionBehaviors;
                int i30 = max5;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour17 = dimensionBehaviourArr5[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour18 = dimensionBehaviourArr5[1];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour19 = dimensionBehaviour8;
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr6 = constraintWidget10.mListDimensionBehaviors;
                LinearSystem linearSystem4 = linearSystem3;
                if (!Grouping.validInGroup(dimensionBehaviour17, dimensionBehaviour18, dimensionBehaviourArr6[0], dimensionBehaviourArr6[1])) {
                    measure(constraintWidget10, measurer2, this.mMeasure);
                }
                boolean z13 = constraintWidget10 instanceof Guideline;
                if (z13) {
                    Guideline guideline5 = (Guideline) constraintWidget10;
                    if (guideline5.mOrientation == 0) {
                        if (arrayList5 == null) {
                            arrayList5 = new ArrayList();
                        }
                        arrayList5.add(guideline5);
                    }
                    if (guideline5.mOrientation == 1) {
                        if (arrayList3 == null) {
                            arrayList3 = new ArrayList();
                        }
                        arrayList3.add(guideline5);
                    }
                }
                if (constraintWidget10 instanceof HelperWidget) {
                    if (constraintWidget10 instanceof Barrier) {
                        Barrier barrier3 = (Barrier) constraintWidget10;
                        if (barrier3.getOrientation() == 0) {
                            if (arrayList4 == null) {
                                arrayList4 = new ArrayList();
                            }
                            arrayList4.add(barrier3);
                        }
                        if (barrier3.getOrientation() == 1) {
                            if (arrayList6 == null) {
                                arrayList6 = new ArrayList();
                            }
                            arrayList6.add(barrier3);
                        }
                    } else {
                        HelperWidget helperWidget = (HelperWidget) constraintWidget10;
                        if (arrayList4 == null) {
                            arrayList4 = new ArrayList();
                        }
                        arrayList4.add(helperWidget);
                        if (arrayList6 == null) {
                            arrayList6 = new ArrayList();
                        }
                        arrayList6.add(helperWidget);
                    }
                }
                if (constraintWidget10.mLeft.mTarget == null && constraintWidget10.mRight.mTarget == null && !z13 && !(constraintWidget10 instanceof Barrier)) {
                    if (arrayList7 == null) {
                        arrayList7 = new ArrayList();
                    }
                    arrayList7.add(constraintWidget10);
                }
                if (constraintWidget10.mTop.mTarget == null && constraintWidget10.mBottom.mTarget == null && constraintWidget10.mBaseline.mTarget == null && !z13 && !(constraintWidget10 instanceof Barrier)) {
                    if (arrayList8 == null) {
                        arrayList8 = new ArrayList();
                    }
                    arrayList8.add(constraintWidget10);
                }
                i28++;
                dimensionBehaviour7 = dimensionBehaviour16;
                size = i29;
                max5 = i30;
                dimensionBehaviour8 = dimensionBehaviour19;
                linearSystem3 = linearSystem4;
            }
            i11 = max5;
            i2 = size;
            LinearSystem linearSystem5 = linearSystem3;
            dimensionBehaviour5 = dimensionBehaviour8;
            dimensionBehaviour6 = dimensionBehaviour7;
            ArrayList arrayList9 = new ArrayList();
            if (arrayList3 != null) {
                Iterator it = arrayList3.iterator();
                while (it.hasNext()) {
                    Grouping.findDependents((Guideline) it.next(), 0, arrayList9, null);
                }
            }
            WidgetGroup widgetGroup5 = null;
            int i31 = 0;
            if (arrayList4 != null) {
                Iterator it2 = arrayList4.iterator();
                while (it2.hasNext()) {
                    HelperWidget helperWidget2 = (HelperWidget) it2.next();
                    WidgetGroup findDependents = Grouping.findDependents(helperWidget2, i31, arrayList9, widgetGroup5);
                    helperWidget2.addDependents(i31, findDependents, arrayList9);
                    findDependents.cleanup(arrayList9);
                    widgetGroup5 = null;
                    i31 = 0;
                }
            }
            HashSet hashSet = getAnchor(ConstraintAnchor.Type.LEFT).mDependents;
            if (hashSet != null) {
                Iterator it3 = hashSet.iterator();
                while (it3.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it3.next()).mOwner, 0, arrayList9, null);
                }
            }
            HashSet hashSet2 = getAnchor(ConstraintAnchor.Type.RIGHT).mDependents;
            if (hashSet2 != null) {
                Iterator it4 = hashSet2.iterator();
                while (it4.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it4.next()).mOwner, 0, arrayList9, null);
                }
            }
            HashSet hashSet3 = getAnchor(ConstraintAnchor.Type.CENTER).mDependents;
            if (hashSet3 != null) {
                Iterator it5 = hashSet3.iterator();
                while (it5.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it5.next()).mOwner, 0, arrayList9, null);
                }
            }
            WidgetGroup widgetGroup6 = null;
            if (arrayList7 != null) {
                Iterator it6 = arrayList7.iterator();
                while (it6.hasNext()) {
                    Grouping.findDependents((ConstraintWidget) it6.next(), 0, arrayList9, null);
                }
            }
            if (arrayList5 != null) {
                Iterator it7 = arrayList5.iterator();
                while (it7.hasNext()) {
                    Grouping.findDependents((Guideline) it7.next(), 1, arrayList9, null);
                }
            }
            int i32 = 1;
            if (arrayList6 != null) {
                Iterator it8 = arrayList6.iterator();
                while (it8.hasNext()) {
                    HelperWidget helperWidget3 = (HelperWidget) it8.next();
                    WidgetGroup findDependents2 = Grouping.findDependents(helperWidget3, i32, arrayList9, widgetGroup6);
                    helperWidget3.addDependents(i32, findDependents2, arrayList9);
                    findDependents2.cleanup(arrayList9);
                    widgetGroup6 = null;
                    i32 = 1;
                }
            }
            HashSet hashSet4 = getAnchor(ConstraintAnchor.Type.TOP).mDependents;
            if (hashSet4 != null) {
                Iterator it9 = hashSet4.iterator();
                while (it9.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it9.next()).mOwner, 1, arrayList9, null);
                }
            }
            HashSet hashSet5 = getAnchor(ConstraintAnchor.Type.BASELINE).mDependents;
            if (hashSet5 != null) {
                Iterator it10 = hashSet5.iterator();
                while (it10.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it10.next()).mOwner, 1, arrayList9, null);
                }
            }
            HashSet hashSet6 = getAnchor(ConstraintAnchor.Type.BOTTOM).mDependents;
            if (hashSet6 != null) {
                Iterator it11 = hashSet6.iterator();
                while (it11.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it11.next()).mOwner, 1, arrayList9, null);
                }
            }
            HashSet hashSet7 = getAnchor(ConstraintAnchor.Type.CENTER).mDependents;
            if (hashSet7 != null) {
                Iterator it12 = hashSet7.iterator();
                while (it12.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it12.next()).mOwner, 1, arrayList9, null);
                }
            }
            if (arrayList8 != null) {
                Iterator it13 = arrayList8.iterator();
                while (it13.hasNext()) {
                    Grouping.findDependents((ConstraintWidget) it13.next(), 1, arrayList9, null);
                }
            }
            for (int i33 = 0; i33 < size3; i33++) {
                ConstraintWidget constraintWidget11 = (ConstraintWidget) arrayList2.get(i33);
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr7 = constraintWidget11.mListDimensionBehaviors;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour20 = dimensionBehaviourArr7[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour21 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour20 == dimensionBehaviour21 && dimensionBehaviourArr7[1] == dimensionBehaviour21) {
                    int i34 = constraintWidget11.horizontalGroup;
                    int size4 = arrayList9.size();
                    int i35 = 0;
                    while (true) {
                        if (i35 >= size4) {
                            widgetGroup3 = null;
                            break;
                        }
                        widgetGroup3 = (WidgetGroup) arrayList9.get(i35);
                        if (i34 == widgetGroup3.f22id) {
                            break;
                        } else {
                            i35++;
                        }
                    }
                    int i36 = constraintWidget11.verticalGroup;
                    int size5 = arrayList9.size();
                    int i37 = 0;
                    while (true) {
                        if (i37 >= size5) {
                            widgetGroup4 = null;
                            break;
                        }
                        widgetGroup4 = (WidgetGroup) arrayList9.get(i37);
                        if (i36 == widgetGroup4.f22id) {
                            break;
                        } else {
                            i37++;
                        }
                    }
                    if (widgetGroup3 != null && widgetGroup4 != null) {
                        widgetGroup3.moveTo(0, widgetGroup4);
                        widgetGroup4.orientation = 2;
                        arrayList9.remove(widgetGroup3);
                    }
                }
            }
            if (arrayList9.size() <= 1) {
                linearSystem = linearSystem5;
            } else {
                if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    Iterator it14 = arrayList9.iterator();
                    widgetGroup = null;
                    int i38 = 0;
                    while (it14.hasNext()) {
                        WidgetGroup widgetGroup7 = (WidgetGroup) it14.next();
                        if (widgetGroup7.orientation == 1) {
                            linearSystem2 = linearSystem5;
                        } else {
                            linearSystem2 = linearSystem5;
                            int measureWrap2 = widgetGroup7.measureWrap(linearSystem2, 0);
                            if (measureWrap2 > i38) {
                                widgetGroup = widgetGroup7;
                                linearSystem5 = linearSystem2;
                                i38 = measureWrap2;
                            }
                        }
                        linearSystem5 = linearSystem2;
                    }
                    linearSystem = linearSystem5;
                    if (widgetGroup != null) {
                        setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                        setWidth(i38);
                        if (this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                            Iterator it15 = arrayList9.iterator();
                            widgetGroup2 = null;
                            int i39 = 0;
                            while (it15.hasNext()) {
                                WidgetGroup widgetGroup8 = (WidgetGroup) it15.next();
                                if (widgetGroup8.orientation != 0 && (measureWrap = widgetGroup8.measureWrap(linearSystem, 1)) > i39) {
                                    widgetGroup2 = widgetGroup8;
                                    i39 = measureWrap;
                                }
                            }
                            if (widgetGroup2 != null) {
                                setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                                setHeight(i39);
                                if (widgetGroup == null || widgetGroup2 != null) {
                                    z7 = true;
                                    if (!z7) {
                                        ConstraintWidget.DimensionBehaviour dimensionBehaviour22 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                        dimensionBehaviour = dimensionBehaviour5;
                                        if (dimensionBehaviour == dimensionBehaviour22) {
                                            i12 = i11;
                                            if (i12 >= getWidth() || i12 <= 0) {
                                                i5 = getWidth();
                                                dimensionBehaviour2 = dimensionBehaviour6;
                                                if (dimensionBehaviour2 != dimensionBehaviour22) {
                                                    i13 = i;
                                                    if (i13 >= getHeight() || i13 <= 0) {
                                                        i6 = getHeight();
                                                        z = true;
                                                        boolean z14 = !optimizeFor(64) || optimizeFor(128);
                                                        linearSystem.getClass();
                                                        linearSystem.newgraphOptimizer = false;
                                                        if (this.mOptimizationLevel == 0 && z14) {
                                                            c = 1;
                                                            linearSystem.newgraphOptimizer = true;
                                                        } else {
                                                            c = 1;
                                                        }
                                                        ArrayList arrayList10 = this.mChildren;
                                                        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr8 = this.mListDimensionBehaviors;
                                                        ConstraintWidget.DimensionBehaviour dimensionBehaviour23 = dimensionBehaviourArr8[0];
                                                        ConstraintWidget.DimensionBehaviour dimensionBehaviour24 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                                        boolean z15 = dimensionBehaviour23 != dimensionBehaviour24 || dimensionBehaviourArr8[c] == dimensionBehaviour24;
                                                        this.mHorizontalChainsSize = 0;
                                                        this.mVerticalChainsSize = 0;
                                                        i7 = i2;
                                                        for (i8 = 0; i8 < i7; i8++) {
                                                            ConstraintWidget constraintWidget12 = (ConstraintWidget) this.mChildren.get(i8);
                                                            if (constraintWidget12 instanceof WidgetContainer) {
                                                                ((WidgetContainer) constraintWidget12).layout();
                                                            }
                                                        }
                                                        boolean optimizeFor = optimizeFor(64);
                                                        boolean z16 = z;
                                                        int i40 = 0;
                                                        z2 = true;
                                                        while (z2) {
                                                            int i41 = i40 + 1;
                                                            try {
                                                                linearSystem.reset();
                                                                this.mHorizontalChainsSize = 0;
                                                                this.mVerticalChainsSize = 0;
                                                                createObjectVariables(linearSystem);
                                                                for (int i42 = 0; i42 < i7; i42++) {
                                                                    ((ConstraintWidget) this.mChildren.get(i42)).createObjectVariables(linearSystem);
                                                                }
                                                                addChildrenToSolver(linearSystem);
                                                                try {
                                                                    WeakReference weakReference = this.verticalWrapMin;
                                                                    if (weakReference != null && weakReference.get() != null) {
                                                                        ConstraintAnchor constraintAnchor6 = constraintAnchor2;
                                                                        try {
                                                                            constraintAnchor2 = constraintAnchor6;
                                                                            linearSystem.addGreaterThan(linearSystem.createObjectVariable((ConstraintAnchor) this.verticalWrapMin.get()), linearSystem.createObjectVariable(constraintAnchor6), 0, 5);
                                                                            this.verticalWrapMin = null;
                                                                        } catch (Exception e) {
                                                                            e = e;
                                                                            constraintAnchor2 = constraintAnchor6;
                                                                            z2 = true;
                                                                            e.printStackTrace();
                                                                            z4 = z2;
                                                                            System.out.println("EXCEPTION : " + e);
                                                                            boolean[] zArr = Optimizer.flags;
                                                                            if (z4) {
                                                                            }
                                                                            if (z15) {
                                                                                int i43 = 0;
                                                                                int i44 = 0;
                                                                                while (i10 < i7) {
                                                                                }
                                                                                max3 = Math.max(this.mMinWidth, i44);
                                                                                max4 = Math.max(this.mMinHeight, i43);
                                                                                dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                                                                if (dimensionBehaviour == dimensionBehaviour3) {
                                                                                    setWidth(max3);
                                                                                    this.mListDimensionBehaviors[0] = dimensionBehaviour3;
                                                                                    z6 = true;
                                                                                    z5 = true;
                                                                                }
                                                                                if (dimensionBehaviour2 == dimensionBehaviour3) {
                                                                                    setHeight(max4);
                                                                                    this.mListDimensionBehaviors[1] = dimensionBehaviour3;
                                                                                    z6 = true;
                                                                                    z5 = true;
                                                                                }
                                                                            }
                                                                            max = Math.max(this.mMinWidth, getWidth());
                                                                            if (max > getWidth()) {
                                                                            }
                                                                            max2 = Math.max(this.mMinHeight, getHeight());
                                                                            if (max2 <= getHeight()) {
                                                                            }
                                                                            if (!z5) {
                                                                            }
                                                                            z16 = z5;
                                                                            i9 = 8;
                                                                            if (i41 > i9) {
                                                                            }
                                                                            i40 = i41;
                                                                        }
                                                                    }
                                                                    WeakReference weakReference2 = this.verticalWrapMax;
                                                                    if (weakReference2 != null && weakReference2.get() != null) {
                                                                        linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mBottom), linearSystem.createObjectVariable((ConstraintAnchor) this.verticalWrapMax.get()), 0, 5);
                                                                        this.verticalWrapMax = null;
                                                                    }
                                                                    WeakReference weakReference3 = this.horizontalWrapMin;
                                                                    if (weakReference3 != null && weakReference3.get() != null) {
                                                                        ConstraintAnchor constraintAnchor7 = constraintAnchor;
                                                                        try {
                                                                            constraintAnchor = constraintAnchor7;
                                                                            linearSystem.addGreaterThan(linearSystem.createObjectVariable((ConstraintAnchor) this.horizontalWrapMin.get()), linearSystem.createObjectVariable(constraintAnchor7), 0, 5);
                                                                            try {
                                                                                this.horizontalWrapMin = null;
                                                                            } catch (Exception e2) {
                                                                                e = e2;
                                                                                z2 = true;
                                                                                e.printStackTrace();
                                                                                z4 = z2;
                                                                                System.out.println("EXCEPTION : " + e);
                                                                                boolean[] zArr2 = Optimizer.flags;
                                                                                if (z4) {
                                                                                }
                                                                                if (z15) {
                                                                                }
                                                                                max = Math.max(this.mMinWidth, getWidth());
                                                                                if (max > getWidth()) {
                                                                                }
                                                                                max2 = Math.max(this.mMinHeight, getHeight());
                                                                                if (max2 <= getHeight()) {
                                                                                }
                                                                                if (!z5) {
                                                                                }
                                                                                z16 = z5;
                                                                                i9 = 8;
                                                                                if (i41 > i9) {
                                                                                }
                                                                                i40 = i41;
                                                                            }
                                                                        } catch (Exception e3) {
                                                                            e = e3;
                                                                            constraintAnchor = constraintAnchor7;
                                                                            z2 = true;
                                                                            e.printStackTrace();
                                                                            z4 = z2;
                                                                            System.out.println("EXCEPTION : " + e);
                                                                            boolean[] zArr22 = Optimizer.flags;
                                                                            if (z4) {
                                                                            }
                                                                            if (z15) {
                                                                            }
                                                                            max = Math.max(this.mMinWidth, getWidth());
                                                                            if (max > getWidth()) {
                                                                            }
                                                                            max2 = Math.max(this.mMinHeight, getHeight());
                                                                            if (max2 <= getHeight()) {
                                                                            }
                                                                            if (!z5) {
                                                                            }
                                                                            z16 = z5;
                                                                            i9 = 8;
                                                                            if (i41 > i9) {
                                                                            }
                                                                            i40 = i41;
                                                                        }
                                                                    }
                                                                    WeakReference weakReference4 = this.horizontalWrapMax;
                                                                    if (weakReference4 != null && weakReference4.get() != null) {
                                                                        linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mRight), linearSystem.createObjectVariable((ConstraintAnchor) this.horizontalWrapMax.get()), 0, 5);
                                                                        this.horizontalWrapMax = null;
                                                                    }
                                                                    linearSystem.minimize();
                                                                    z4 = true;
                                                                } catch (Exception e4) {
                                                                    e = e4;
                                                                }
                                                            } catch (Exception e5) {
                                                                e = e5;
                                                            }
                                                            boolean[] zArr222 = Optimizer.flags;
                                                            if (z4) {
                                                                z5 = z16;
                                                                updateFromSolver(linearSystem, optimizeFor);
                                                                for (int i45 = 0; i45 < i7; i45++) {
                                                                    ((ConstraintWidget) this.mChildren.get(i45)).updateFromSolver(linearSystem, optimizeFor);
                                                                }
                                                                z6 = false;
                                                            } else {
                                                                zArr222[2] = false;
                                                                boolean optimizeFor2 = optimizeFor(64);
                                                                updateFromSolver(linearSystem, optimizeFor2);
                                                                int size6 = this.mChildren.size();
                                                                int i46 = 0;
                                                                z6 = false;
                                                                while (i46 < size6) {
                                                                    int i47 = size6;
                                                                    ConstraintWidget constraintWidget13 = (ConstraintWidget) this.mChildren.get(i46);
                                                                    constraintWidget13.updateFromSolver(linearSystem, optimizeFor2);
                                                                    boolean z17 = optimizeFor2;
                                                                    boolean z18 = z16;
                                                                    if ((constraintWidget13.mWidthOverride == -1 && constraintWidget13.mHeightOverride == -1) ? false : true) {
                                                                        z6 = true;
                                                                    }
                                                                    i46++;
                                                                    size6 = i47;
                                                                    optimizeFor2 = z17;
                                                                    z16 = z18;
                                                                }
                                                                z5 = z16;
                                                            }
                                                            if (z15 && i41 < 8 && zArr222[2]) {
                                                                int i432 = 0;
                                                                int i442 = 0;
                                                                for (i10 = 0; i10 < i7; i10++) {
                                                                    ConstraintWidget constraintWidget14 = (ConstraintWidget) this.mChildren.get(i10);
                                                                    i442 = Math.max(i442, constraintWidget14.getWidth() + constraintWidget14.f15mX);
                                                                    i432 = Math.max(i432, constraintWidget14.getHeight() + constraintWidget14.f16mY);
                                                                }
                                                                max3 = Math.max(this.mMinWidth, i442);
                                                                max4 = Math.max(this.mMinHeight, i432);
                                                                dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                                                if (dimensionBehaviour == dimensionBehaviour3 && getWidth() < max3) {
                                                                    setWidth(max3);
                                                                    this.mListDimensionBehaviors[0] = dimensionBehaviour3;
                                                                    z6 = true;
                                                                    z5 = true;
                                                                }
                                                                if (dimensionBehaviour2 == dimensionBehaviour3 && getHeight() < max4) {
                                                                    setHeight(max4);
                                                                    this.mListDimensionBehaviors[1] = dimensionBehaviour3;
                                                                    z6 = true;
                                                                    z5 = true;
                                                                }
                                                            }
                                                            max = Math.max(this.mMinWidth, getWidth());
                                                            if (max > getWidth()) {
                                                                setWidth(max);
                                                                this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
                                                                z6 = true;
                                                                z5 = true;
                                                            }
                                                            max2 = Math.max(this.mMinHeight, getHeight());
                                                            if (max2 <= getHeight()) {
                                                                setHeight(max2);
                                                                r12 = 1;
                                                                this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
                                                                z6 = true;
                                                                z5 = true;
                                                            } else {
                                                                r12 = 1;
                                                            }
                                                            if (!z5) {
                                                                ConstraintWidget.DimensionBehaviour dimensionBehaviour25 = this.mListDimensionBehaviors[0];
                                                                ConstraintWidget.DimensionBehaviour dimensionBehaviour26 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                                                if (dimensionBehaviour25 == dimensionBehaviour26 && i5 > 0 && getWidth() > i5) {
                                                                    this.mWidthMeasuredTooSmall = r12;
                                                                    this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
                                                                    setWidth(i5);
                                                                    z6 = r12;
                                                                    z5 = z6;
                                                                }
                                                                if (this.mListDimensionBehaviors[r12] == dimensionBehaviour26 && i6 > 0 && getHeight() > i6) {
                                                                    this.mHeightMeasuredTooSmall = r12;
                                                                    this.mListDimensionBehaviors[r12] = ConstraintWidget.DimensionBehaviour.FIXED;
                                                                    setHeight(i6);
                                                                    i9 = 8;
                                                                    z16 = true;
                                                                    z6 = true;
                                                                    z2 = i41 > i9 ? false : z6;
                                                                    i40 = i41;
                                                                }
                                                            }
                                                            z16 = z5;
                                                            i9 = 8;
                                                            if (i41 > i9) {
                                                            }
                                                            i40 = i41;
                                                        }
                                                        z3 = z16;
                                                        this.mChildren = arrayList10;
                                                        if (z3) {
                                                            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr9 = this.mListDimensionBehaviors;
                                                            dimensionBehaviourArr9[0] = dimensionBehaviour;
                                                            dimensionBehaviourArr9[1] = dimensionBehaviour2;
                                                        }
                                                        resetSolverVariables(linearSystem.mCache);
                                                    }
                                                    setHeight(i13);
                                                    this.mHeightMeasuredTooSmall = true;
                                                } else {
                                                    i13 = i;
                                                }
                                                i6 = i13;
                                                z = true;
                                                if (optimizeFor(64)) {
                                                }
                                                linearSystem.getClass();
                                                linearSystem.newgraphOptimizer = false;
                                                if (this.mOptimizationLevel == 0) {
                                                }
                                                c = 1;
                                                ArrayList arrayList102 = this.mChildren;
                                                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr82 = this.mListDimensionBehaviors;
                                                ConstraintWidget.DimensionBehaviour dimensionBehaviour232 = dimensionBehaviourArr82[0];
                                                ConstraintWidget.DimensionBehaviour dimensionBehaviour242 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                                if (dimensionBehaviour232 != dimensionBehaviour242) {
                                                }
                                                this.mHorizontalChainsSize = 0;
                                                this.mVerticalChainsSize = 0;
                                                i7 = i2;
                                                while (i8 < i7) {
                                                }
                                                boolean optimizeFor3 = optimizeFor(64);
                                                boolean z162 = z;
                                                int i402 = 0;
                                                z2 = true;
                                                while (z2) {
                                                }
                                                z3 = z162;
                                                this.mChildren = arrayList102;
                                                if (z3) {
                                                }
                                                resetSolverVariables(linearSystem.mCache);
                                            }
                                            setWidth(i12);
                                            this.mWidthMeasuredTooSmall = true;
                                        } else {
                                            i12 = i11;
                                        }
                                        i5 = i12;
                                        dimensionBehaviour2 = dimensionBehaviour6;
                                        if (dimensionBehaviour2 != dimensionBehaviour22) {
                                        }
                                        i6 = i13;
                                        z = true;
                                        if (optimizeFor(64)) {
                                        }
                                        linearSystem.getClass();
                                        linearSystem.newgraphOptimizer = false;
                                        if (this.mOptimizationLevel == 0) {
                                        }
                                        c = 1;
                                        ArrayList arrayList1022 = this.mChildren;
                                        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr822 = this.mListDimensionBehaviors;
                                        ConstraintWidget.DimensionBehaviour dimensionBehaviour2322 = dimensionBehaviourArr822[0];
                                        ConstraintWidget.DimensionBehaviour dimensionBehaviour2422 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                        if (dimensionBehaviour2322 != dimensionBehaviour2422) {
                                        }
                                        this.mHorizontalChainsSize = 0;
                                        this.mVerticalChainsSize = 0;
                                        i7 = i2;
                                        while (i8 < i7) {
                                        }
                                        boolean optimizeFor32 = optimizeFor(64);
                                        boolean z1622 = z;
                                        int i4022 = 0;
                                        z2 = true;
                                        while (z2) {
                                        }
                                        z3 = z1622;
                                        this.mChildren = arrayList1022;
                                        if (z3) {
                                        }
                                        resetSolverVariables(linearSystem.mCache);
                                    }
                                    i3 = i;
                                    dimensionBehaviour2 = dimensionBehaviour6;
                                    i4 = i11;
                                    dimensionBehaviour = dimensionBehaviour5;
                                }
                            }
                        }
                        widgetGroup2 = null;
                        if (widgetGroup == null) {
                        }
                        z7 = true;
                        if (!z7) {
                        }
                    }
                } else {
                    linearSystem = linearSystem5;
                }
                widgetGroup = null;
                if (this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                }
                widgetGroup2 = null;
                if (widgetGroup == null) {
                }
                z7 = true;
                if (!z7) {
                }
            }
            z7 = false;
            if (!z7) {
            }
        }
        i5 = i4;
        i6 = i3;
        z = false;
        if (optimizeFor(64)) {
        }
        linearSystem.getClass();
        linearSystem.newgraphOptimizer = false;
        if (this.mOptimizationLevel == 0) {
        }
        c = 1;
        ArrayList arrayList10222 = this.mChildren;
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr8222 = this.mListDimensionBehaviors;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour23222 = dimensionBehaviourArr8222[0];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour24222 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (dimensionBehaviour23222 != dimensionBehaviour24222) {
        }
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        i7 = i2;
        while (i8 < i7) {
        }
        boolean optimizeFor322 = optimizeFor(64);
        boolean z16222 = z;
        int i40222 = 0;
        z2 = true;
        while (z2) {
        }
        z3 = z16222;
        this.mChildren = arrayList10222;
        if (z3) {
        }
        resetSolverVariables(linearSystem.mCache);
    }

    public final boolean optimizeFor(int i) {
        return (this.mOptimizationLevel & i) == i;
    }

    @Override // androidx.constraintlayout.core.widgets.WidgetContainer, androidx.constraintlayout.core.widgets.ConstraintWidget
    public final void reset() {
        this.mSystem.reset();
        this.mPaddingLeft = 0;
        this.mPaddingTop = 0;
        super.reset();
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final void updateFromRuns(boolean z, boolean z2) {
        super.updateFromRuns(z, z2);
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            ((ConstraintWidget) this.mChildren.get(i)).updateFromRuns(z, z2);
        }
    }

    public ConstraintWidgetContainer(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
        this.mBasicMeasureSolver = new BasicMeasure(this);
        this.mDependencyGraph = new DependencyGraph(this);
        this.mMeasurer = null;
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mOptimizationLevel = 257;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.verticalWrapMin = null;
        this.horizontalWrapMin = null;
        this.verticalWrapMax = null;
        this.horizontalWrapMax = null;
        this.widgetsToAdd = new HashSet();
        this.mMeasure = new BasicMeasure.Measure();
    }

    public ConstraintWidgetContainer(int i, int i2) {
        super(i, i2);
        this.mBasicMeasureSolver = new BasicMeasure(this);
        this.mDependencyGraph = new DependencyGraph(this);
        this.mMeasurer = null;
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mOptimizationLevel = 257;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.verticalWrapMin = null;
        this.horizontalWrapMin = null;
        this.verticalWrapMax = null;
        this.horizontalWrapMax = null;
        this.widgetsToAdd = new HashSet();
        this.mMeasure = new BasicMeasure.Measure();
    }

    public ConstraintWidgetContainer(String str, int i, int i2) {
        super(i, i2);
        this.mBasicMeasureSolver = new BasicMeasure(this);
        this.mDependencyGraph = new DependencyGraph(this);
        this.mMeasurer = null;
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mOptimizationLevel = 257;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.verticalWrapMin = null;
        this.horizontalWrapMin = null;
        this.verticalWrapMax = null;
        this.horizontalWrapMax = null;
        this.widgetsToAdd = new HashSet();
        this.mMeasure = new BasicMeasure.Measure();
        this.mDebugName = str;
    }
}
