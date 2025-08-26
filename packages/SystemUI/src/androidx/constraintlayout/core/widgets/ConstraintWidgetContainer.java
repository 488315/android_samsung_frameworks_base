package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.Metrics;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph;
import androidx.constraintlayout.core.widgets.analyzer.Direct;
import androidx.constraintlayout.core.widgets.analyzer.Grouping;
import androidx.constraintlayout.core.widgets.analyzer.WidgetGroup;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ConstraintWidgetContainer extends WidgetContainer {
    public final BasicMeasure mBasicMeasureSolver;
    public final DependencyGraph mDependencyGraph;
    public boolean mHeightMeasuredTooSmall;
    public ChainHead[] mHorizontalChainsArray;
    public int mHorizontalChainsSize;
    public WeakReference mHorizontalWrapMax;
    public WeakReference mHorizontalWrapMin;
    public boolean mIsRtl;
    public final BasicMeasure.Measure mMeasure;
    public BasicMeasure.Measurer mMeasurer;
    public Metrics mMetrics;
    public int mOptimizationLevel;
    public int mPaddingLeft;
    public int mPaddingTop;
    public int mPass;
    public final LinearSystem mSystem;
    public ChainHead[] mVerticalChainsArray;
    public int mVerticalChainsSize;
    public WeakReference mVerticalWrapMax;
    public WeakReference mVerticalWrapMin;
    public final HashSet mWidgetsToAdd;
    public boolean mWidthMeasuredTooSmall;

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
        this.mVerticalWrapMin = null;
        this.mHorizontalWrapMin = null;
        this.mVerticalWrapMax = null;
        this.mHorizontalWrapMax = null;
        this.mWidgetsToAdd = new HashSet();
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
                    measurer.measure(constraintWidget, measure);
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
                    measurer.measure(constraintWidget, measure);
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
        measurer.measure(constraintWidget, measure);
        constraintWidget.setWidth(measure.measuredWidth);
        constraintWidget.setHeight(measure.measuredHeight);
        constraintWidget.mHasBaseline = measure.measuredHasBaseline;
        constraintWidget.setBaselineDistance(measure.measuredBaseline);
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
        ConstraintWidgetContainer constraintWidgetContainer;
        LinearSystem linearSystem2;
        boolean zOptimizeFor = optimizeFor(64);
        addToSolver(linearSystem, zOptimizeFor);
        int size = this.mChildren.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = (ConstraintWidget) this.mChildren.get(i);
            boolean[] zArr = constraintWidget.mIsInBarrier;
            zArr[0] = false;
            zArr[1] = false;
            if (constraintWidget instanceof Barrier) {
                z = true;
            }
        }
        if (z) {
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
        this.mWidgetsToAdd.clear();
        for (int i5 = 0; i5 < size; i5++) {
            ConstraintWidget constraintWidget4 = (ConstraintWidget) this.mChildren.get(i5);
            constraintWidget4.getClass();
            boolean z2 = constraintWidget4 instanceof VirtualLayout;
            if (z2 || (constraintWidget4 instanceof Guideline)) {
                if (z2) {
                    this.mWidgetsToAdd.add(constraintWidget4);
                } else {
                    constraintWidget4.addToSolver(linearSystem, zOptimizeFor);
                }
            }
        }
        while (this.mWidgetsToAdd.size() > 0) {
            int size2 = this.mWidgetsToAdd.size();
            Iterator it = this.mWidgetsToAdd.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                VirtualLayout virtualLayout = (VirtualLayout) ((ConstraintWidget) it.next());
                HashSet hashSet = this.mWidgetsToAdd;
                for (int i6 = 0; i6 < virtualLayout.mWidgetsCount; i6++) {
                    if (hashSet.contains(virtualLayout.mWidgets[i6])) {
                        virtualLayout.addToSolver(linearSystem, zOptimizeFor);
                        this.mWidgetsToAdd.remove(virtualLayout);
                        break;
                    }
                }
            }
            if (size2 == this.mWidgetsToAdd.size()) {
                Iterator it2 = this.mWidgetsToAdd.iterator();
                while (it2.hasNext()) {
                    ((ConstraintWidget) it2.next()).addToSolver(linearSystem, zOptimizeFor);
                }
                this.mWidgetsToAdd.clear();
            }
        }
        if (LinearSystem.USE_DEPENDENCY_ORDERING) {
            HashSet hashSet2 = new HashSet();
            for (int i7 = 0; i7 < size; i7++) {
                ConstraintWidget constraintWidget5 = (ConstraintWidget) this.mChildren.get(i7);
                constraintWidget5.getClass();
                if (!(constraintWidget5 instanceof VirtualLayout) && !(constraintWidget5 instanceof Guideline)) {
                    hashSet2.add(constraintWidget5);
                }
            }
            constraintWidgetContainer = this;
            linearSystem2 = linearSystem;
            constraintWidgetContainer.addChildrenToSolverByDependency(this, linearSystem2, hashSet2, this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? 0 : 1, false);
            Iterator it3 = hashSet2.iterator();
            while (it3.hasNext()) {
                ConstraintWidget constraintWidget6 = (ConstraintWidget) it3.next();
                Optimizer.checkMatchParent(constraintWidgetContainer, linearSystem2, constraintWidget6);
                constraintWidget6.addToSolver(linearSystem2, zOptimizeFor);
            }
        } else {
            constraintWidgetContainer = this;
            linearSystem2 = linearSystem;
            for (int i8 = 0; i8 < size; i8++) {
                ConstraintWidget constraintWidget7 = (ConstraintWidget) constraintWidgetContainer.mChildren.get(i8);
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
                    constraintWidget7.addToSolver(linearSystem2, zOptimizeFor);
                    if (dimensionBehaviour == dimensionBehaviour3) {
                        constraintWidget7.setHorizontalDimensionBehaviour(dimensionBehaviour);
                    }
                    if (dimensionBehaviour2 == dimensionBehaviour3) {
                        constraintWidget7.setVerticalDimensionBehaviour(dimensionBehaviour2);
                    }
                } else {
                    Optimizer.checkMatchParent(constraintWidgetContainer, linearSystem2, constraintWidget7);
                    if (!(constraintWidget7 instanceof VirtualLayout) && !(constraintWidget7 instanceof Guideline)) {
                        constraintWidget7.addToSolver(linearSystem2, zOptimizeFor);
                    }
                }
            }
        }
        if (constraintWidgetContainer.mHorizontalChainsSize > 0) {
            Chain.applyChainConstraints(constraintWidgetContainer, linearSystem2, null, 0);
        }
        if (constraintWidgetContainer.mVerticalChainsSize > 0) {
            Chain.applyChainConstraints(constraintWidgetContainer, linearSystem2, null, 1);
        }
    }

    public final boolean directMeasureWithOrientation(int i, boolean z) {
        boolean z2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        DependencyGraph dependencyGraph = this.mDependencyGraph;
        ConstraintWidgetContainer constraintWidgetContainer = dependencyGraph.mWidgetcontainer;
        boolean z3 = false;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidgetContainer.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = constraintWidgetContainer.getDimensionBehaviour(1);
        int x = constraintWidgetContainer.getX();
        int y = constraintWidgetContainer.getY();
        if (z && (dimensionBehaviour2 == (dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || dimensionBehaviour3 == dimensionBehaviour)) {
            ArrayList arrayList = dependencyGraph.mRuns;
            int size = arrayList.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    break;
                }
                Object obj = arrayList.get(i2);
                i2++;
                WidgetRun widgetRun = (WidgetRun) obj;
                if (widgetRun.orientation == i && !widgetRun.supportsWrapComputation()) {
                    z = false;
                    break;
                }
            }
            if (i == 0) {
                if (z && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidgetContainer.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                    constraintWidgetContainer.setWidth(dependencyGraph.computeWrap(constraintWidgetContainer, 0));
                    constraintWidgetContainer.mHorizontalRun.mDimension.resolve(constraintWidgetContainer.getWidth());
                }
            } else if (z && dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                constraintWidgetContainer.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                constraintWidgetContainer.setHeight(dependencyGraph.computeWrap(constraintWidgetContainer, 1));
                constraintWidgetContainer.mVerticalRun.mDimension.resolve(constraintWidgetContainer.getHeight());
            }
        }
        if (i == 0) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = constraintWidgetContainer.mListDimensionBehaviors[0];
            if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int width = constraintWidgetContainer.getWidth() + x;
                constraintWidgetContainer.mHorizontalRun.end.resolve(width);
                constraintWidgetContainer.mHorizontalRun.mDimension.resolve(width - x);
                z2 = true;
            }
            z2 = false;
        } else {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = constraintWidgetContainer.mListDimensionBehaviors[1];
            if (dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int height = constraintWidgetContainer.getHeight() + y;
                constraintWidgetContainer.mVerticalRun.end.resolve(height);
                constraintWidgetContainer.mVerticalRun.mDimension.resolve(height - y);
                z2 = true;
            }
            z2 = false;
        }
        dependencyGraph.measureWidgets();
        ArrayList arrayList2 = dependencyGraph.mRuns;
        int size2 = arrayList2.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList2.get(i3);
            i3++;
            WidgetRun widgetRun2 = (WidgetRun) obj2;
            if (widgetRun2.orientation == i && (widgetRun2.mWidget != constraintWidgetContainer || widgetRun2.mResolved)) {
                widgetRun2.applyToWidget();
            }
        }
        ArrayList arrayList3 = dependencyGraph.mRuns;
        int size3 = arrayList3.size();
        int i4 = 0;
        while (true) {
            if (i4 >= size3) {
                z3 = true;
                break;
            }
            Object obj3 = arrayList3.get(i4);
            i4++;
            WidgetRun widgetRun3 = (WidgetRun) obj3;
            if (widgetRun3.orientation == i && (z2 || widgetRun3.mWidget != constraintWidgetContainer)) {
                if (!widgetRun3.start.resolved || !widgetRun3.end.resolved || (!(widgetRun3 instanceof ChainRun) && !widgetRun3.mDimension.resolved)) {
                    break;
                }
            }
        }
        constraintWidgetContainer.setHorizontalDimensionBehaviour(dimensionBehaviour2);
        constraintWidgetContainer.setVerticalDimensionBehaviour(dimensionBehaviour3);
        return z3;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final void getSceneString(StringBuilder sb) {
        sb.append(this.stringId + ":{\n");
        StringBuilder sb2 = new StringBuilder("  actualWidth:");
        sb2.append(this.mWidth);
        sb.append(sb2.toString());
        sb.append("\n");
        sb.append("  actualHeight:" + this.mHeight);
        sb.append("\n");
        ArrayList arrayList = this.mChildren;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((ConstraintWidget) obj).getSceneString(sb);
            sb.append(",\n");
        }
        sb.append("}");
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x05fd  */
    /* JADX WARN: Removed duplicated region for block: B:359:0x0631  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x0664  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x067a  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x0695  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x06a6  */
    /* JADX WARN: Removed duplicated region for block: B:402:0x06b8  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x06c2  */
    /* JADX WARN: Removed duplicated region for block: B:411:0x06e4  */
    /* JADX WARN: Removed duplicated region for block: B:463:0x07c0  */
    /* JADX WARN: Removed duplicated region for block: B:469:0x07e4  */
    /* JADX WARN: Removed duplicated region for block: B:480:0x082a  */
    /* JADX WARN: Removed duplicated region for block: B:486:0x084a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:501:0x08b6  */
    /* JADX WARN: Removed duplicated region for block: B:504:0x08c8  */
    /* JADX WARN: Removed duplicated region for block: B:507:0x08e6  */
    /* JADX WARN: Removed duplicated region for block: B:508:0x08f4  */
    /* JADX WARN: Removed duplicated region for block: B:510:0x08f7  */
    /* JADX WARN: Removed duplicated region for block: B:522:0x0935 A[PHI: r13 r23
      0x0935: PHI (r13v8 boolean) = (r13v7 boolean), (r13v12 boolean), (r13v12 boolean), (r13v12 boolean) binds: [B:509:0x08f5, B:517:0x091b, B:518:0x091d, B:520:0x0923] A[DONT_GENERATE, DONT_INLINE]
      0x0935: PHI (r23v5 boolean) = (r23v4 boolean), (r23v6 boolean), (r23v6 boolean), (r23v6 boolean) binds: [B:509:0x08f5, B:517:0x091b, B:518:0x091d, B:520:0x0923] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:524:0x093b  */
    /* JADX WARN: Removed duplicated region for block: B:528:0x094d  */
    /* JADX WARN: Removed duplicated region for block: B:579:0x093c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x013a  */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v13 */
    @Override // androidx.constraintlayout.core.widgets.WidgetContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void layout() {
        ConstraintAnchor constraintAnchor;
        int i;
        int i2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
        ConstraintAnchor constraintAnchor2;
        int i3;
        int height;
        int width;
        boolean z;
        char c;
        int i4;
        int i5;
        boolean z2;
        boolean z3;
        boolean z4;
        ConstraintAnchor constraintAnchor3;
        boolean z5;
        boolean[] zArr;
        int iMax;
        int iMax2;
        ?? r8;
        int i6;
        WeakReference weakReference;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3;
        int i7;
        int i8;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour4;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour5;
        int i9;
        int i10;
        WidgetGroup widgetGroup;
        WidgetGroup widgetGroup2;
        int i11;
        int i12;
        int i13;
        int i14;
        ConstraintAnchor constraintAnchor4;
        LinearSystem linearSystem = this.mSystem;
        this.mX = 0;
        this.mY = 0;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        int size = this.mChildren.size();
        int iMax3 = Math.max(0, getWidth());
        int iMax4 = Math.max(0, getHeight());
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = dimensionBehaviourArr[1];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = dimensionBehaviourArr[0];
        int i15 = this.mPass;
        ConstraintAnchor constraintAnchor5 = this.mTop;
        ConstraintAnchor constraintAnchor6 = this.mLeft;
        if (i15 == 0 && Optimizer.enabled(this.mOptimizationLevel, 1)) {
            BasicMeasure.Measurer measurer = this.mMeasurer;
            BasicMeasure.Measure measure = Direct.sMeasure;
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = this.mListDimensionBehaviors;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour8 = dimensionBehaviourArr2[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour9 = dimensionBehaviourArr2[1];
            resetFinalResolution();
            ArrayList arrayList = this.mChildren;
            int size2 = arrayList.size();
            int i16 = 1;
            for (int i17 = 0; i17 < size2; i17++) {
                ((ConstraintWidget) arrayList.get(i17)).resetFinalResolution();
            }
            boolean z6 = this.mIsRtl;
            if (dimensionBehaviour8 == ConstraintWidget.DimensionBehaviour.FIXED) {
                setFinalHorizontal(0, getWidth());
            } else {
                constraintAnchor6.setFinalValue(0);
                this.mX = 0;
            }
            int i18 = 0;
            boolean z7 = false;
            boolean z8 = false;
            while (i18 < size2) {
                int i19 = i18;
                ConstraintWidget constraintWidget = (ConstraintWidget) arrayList.get(i18);
                boolean z9 = z7;
                if (constraintWidget instanceof Guideline) {
                    Guideline guideline = (Guideline) constraintWidget;
                    constraintAnchor4 = constraintAnchor6;
                    if (guideline.mOrientation == i16) {
                        int i20 = guideline.mRelativeBegin;
                        if (i20 != -1) {
                            guideline.setFinalValue(i20);
                        } else if (guideline.mRelativeEnd != -1 && isResolvedHorizontally()) {
                            guideline.setFinalValue(getWidth() - guideline.mRelativeEnd);
                        } else if (isResolvedHorizontally()) {
                            guideline.setFinalValue((int) ((guideline.mRelativePercent * getWidth()) + 0.5f));
                        }
                        z9 = true;
                    }
                } else {
                    constraintAnchor4 = constraintAnchor6;
                    if ((constraintWidget instanceof Barrier) && ((Barrier) constraintWidget).getOrientation() == 0) {
                        z7 = z9;
                        z8 = true;
                    }
                    i18 = i19 + 1;
                    constraintAnchor6 = constraintAnchor4;
                    i16 = 1;
                }
                z7 = z9;
                i18 = i19 + 1;
                constraintAnchor6 = constraintAnchor4;
                i16 = 1;
            }
            constraintAnchor = constraintAnchor6;
            if (z7) {
                for (int i21 = 0; i21 < size2; i21 = i14 + 1) {
                    ConstraintWidget constraintWidget2 = (ConstraintWidget) arrayList.get(i21);
                    if (constraintWidget2 instanceof Guideline) {
                        Guideline guideline2 = (Guideline) constraintWidget2;
                        i14 = i21;
                        if (guideline2.mOrientation == 1) {
                            Direct.horizontalSolvingPass(0, guideline2, measurer, z6);
                        }
                    } else {
                        i14 = i21;
                    }
                }
            }
            Direct.horizontalSolvingPass(0, this, measurer, z6);
            if (z8) {
                int i22 = 0;
                while (i22 < size2) {
                    ConstraintWidget constraintWidget3 = (ConstraintWidget) arrayList.get(i22);
                    if (constraintWidget3 instanceof Barrier) {
                        Barrier barrier = (Barrier) constraintWidget3;
                        if (barrier.getOrientation() == 0 && barrier.allSolved()) {
                            i13 = 1;
                            Direct.horizontalSolvingPass(1, barrier, measurer, z6);
                        } else {
                            i13 = 1;
                        }
                    }
                    i22 += i13;
                }
            }
            if (dimensionBehaviour9 == ConstraintWidget.DimensionBehaviour.FIXED) {
                setFinalVertical(0, getHeight());
            } else {
                constraintAnchor5.setFinalValue(0);
                this.mY = 0;
            }
            int i23 = 0;
            boolean z10 = false;
            boolean z11 = false;
            while (i23 < size2) {
                ConstraintWidget constraintWidget4 = (ConstraintWidget) arrayList.get(i23);
                int i24 = i23;
                if (constraintWidget4 instanceof Guideline) {
                    Guideline guideline3 = (Guideline) constraintWidget4;
                    if (guideline3.mOrientation == 0) {
                        int i25 = guideline3.mRelativeBegin;
                        if (i25 != -1) {
                            guideline3.setFinalValue(i25);
                        } else if (guideline3.mRelativeEnd != -1 && isResolvedVertically()) {
                            guideline3.setFinalValue(getHeight() - guideline3.mRelativeEnd);
                        } else if (isResolvedVertically()) {
                            guideline3.setFinalValue((int) ((guideline3.mRelativePercent * getHeight()) + 0.5f));
                        }
                        z10 = true;
                    }
                } else {
                    if (constraintWidget4 instanceof Barrier) {
                        if (((Barrier) constraintWidget4).getOrientation() == 1) {
                            z11 = true;
                        }
                    }
                    i23 = i24 + 1;
                }
                i23 = i24 + 1;
            }
            if (z10) {
                int i26 = 0;
                while (i26 < size2) {
                    ConstraintWidget constraintWidget5 = (ConstraintWidget) arrayList.get(i26);
                    if (constraintWidget5 instanceof Guideline) {
                        Guideline guideline4 = (Guideline) constraintWidget5;
                        if (guideline4.mOrientation == 0) {
                            i12 = 1;
                            Direct.verticalSolvingPass(1, guideline4, measurer);
                        } else {
                            i12 = 1;
                        }
                    }
                    i26 += i12;
                }
            }
            Direct.verticalSolvingPass(0, this, measurer);
            if (z11) {
                int i27 = 0;
                while (i27 < size2) {
                    ConstraintWidget constraintWidget6 = (ConstraintWidget) arrayList.get(i27);
                    if (constraintWidget6 instanceof Barrier) {
                        Barrier barrier2 = (Barrier) constraintWidget6;
                        i11 = 1;
                        if (barrier2.getOrientation() == 1 && barrier2.allSolved()) {
                            Direct.verticalSolvingPass(1, barrier2, measurer);
                        }
                    } else {
                        i11 = 1;
                    }
                    i27 += i11;
                }
            }
            for (int i28 = 0; i28 < size2; i28++) {
                ConstraintWidget constraintWidget7 = (ConstraintWidget) arrayList.get(i28);
                if (constraintWidget7.isMeasureRequested() && Direct.canMeasure(constraintWidget7)) {
                    measure(constraintWidget7, measurer, Direct.sMeasure);
                    if (!(constraintWidget7 instanceof Guideline)) {
                        Direct.horizontalSolvingPass(0, constraintWidget7, measurer, z6);
                        Direct.verticalSolvingPass(0, constraintWidget7, measurer);
                    } else if (((Guideline) constraintWidget7).mOrientation == 0) {
                        Direct.verticalSolvingPass(0, constraintWidget7, measurer);
                    } else {
                        Direct.horizontalSolvingPass(0, constraintWidget7, measurer, z6);
                    }
                }
            }
            for (int i29 = 0; i29 < size; i29++) {
                ConstraintWidget constraintWidget8 = (ConstraintWidget) this.mChildren.get(i29);
                if (constraintWidget8.isMeasureRequested() && !(constraintWidget8 instanceof Guideline) && !(constraintWidget8 instanceof Barrier) && !(constraintWidget8 instanceof VirtualLayout) && !constraintWidget8.mInVirtualLayout) {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour10 = constraintWidget8.getDimensionBehaviour(0);
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour11 = constraintWidget8.getDimensionBehaviour(1);
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour12 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    if (dimensionBehaviour10 != dimensionBehaviour12 || constraintWidget8.mMatchConstraintDefaultWidth == 1 || dimensionBehaviour11 != dimensionBehaviour12 || constraintWidget8.mMatchConstraintDefaultHeight == 1) {
                        measure(constraintWidget8, this.mMeasurer, new BasicMeasure.Measure());
                    }
                }
            }
        } else {
            constraintAnchor = constraintAnchor6;
        }
        if (size <= 2 || !((dimensionBehaviour7 == (dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || dimensionBehaviour6 == dimensionBehaviour3) && Optimizer.enabled(this.mOptimizationLevel, 1024))) {
            i = size;
            i2 = iMax4;
            dimensionBehaviour = dimensionBehaviour7;
            dimensionBehaviour2 = dimensionBehaviour6;
            constraintAnchor2 = constraintAnchor5;
            i3 = iMax3;
        } else {
            BasicMeasure.Measurer measurer2 = this.mMeasurer;
            ArrayList arrayList2 = this.mChildren;
            int size3 = arrayList2.size();
            int i30 = 0;
            while (i30 < size3) {
                ConstraintWidget constraintWidget9 = (ConstraintWidget) arrayList2.get(i30);
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr3 = this.mListDimensionBehaviors;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour13 = dimensionBehaviourArr3[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour14 = dimensionBehaviourArr3[1];
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr4 = constraintWidget9.mListDimensionBehaviors;
                int i31 = i30;
                if (!Grouping.validInGroup(dimensionBehaviour13, dimensionBehaviour14, dimensionBehaviourArr4[0], dimensionBehaviourArr4[1]) || (constraintWidget9 instanceof Flow)) {
                    i7 = iMax3;
                    i = size;
                    i8 = iMax4;
                    dimensionBehaviour4 = dimensionBehaviour7;
                    dimensionBehaviour5 = dimensionBehaviour6;
                    constraintAnchor2 = constraintAnchor5;
                    break;
                }
                i30 = i31 + 1;
            }
            char c2 = 1;
            i = size;
            constraintAnchor2 = constraintAnchor5;
            int i32 = 0;
            ArrayList arrayList3 = null;
            ArrayList arrayList4 = null;
            ArrayList arrayList5 = null;
            ArrayList arrayList6 = null;
            ArrayList arrayList7 = null;
            ArrayList arrayList8 = null;
            while (i32 < size3) {
                int i33 = i32;
                ConstraintWidget constraintWidget10 = (ConstraintWidget) arrayList2.get(i32);
                int i34 = iMax4;
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr5 = this.mListDimensionBehaviors;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour15 = dimensionBehaviourArr5[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour16 = dimensionBehaviour6;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour17 = dimensionBehaviourArr5[c2];
                int i35 = iMax3;
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr6 = constraintWidget10.mListDimensionBehaviors;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour18 = dimensionBehaviour7;
                if (!Grouping.validInGroup(dimensionBehaviour15, dimensionBehaviour17, dimensionBehaviourArr6[0], dimensionBehaviourArr6[c2])) {
                    measure(constraintWidget10, measurer2, this.mMeasure);
                }
                boolean z12 = constraintWidget10 instanceof Guideline;
                if (z12) {
                    Guideline guideline5 = (Guideline) constraintWidget10;
                    if (guideline5.mOrientation == 0) {
                        if (arrayList7 == null) {
                            arrayList7 = new ArrayList();
                        }
                        arrayList7.add(guideline5);
                    }
                    if (guideline5.mOrientation == 1) {
                        if (arrayList4 == null) {
                            arrayList4 = new ArrayList();
                        }
                        arrayList4.add(guideline5);
                    }
                }
                if (constraintWidget10 instanceof HelperWidget) {
                    if (constraintWidget10 instanceof Barrier) {
                        Barrier barrier3 = (Barrier) constraintWidget10;
                        if (barrier3.getOrientation() == 0) {
                            if (arrayList6 == null) {
                                arrayList6 = new ArrayList();
                            }
                            arrayList6.add(barrier3);
                        }
                        if (barrier3.getOrientation() == 1) {
                            if (arrayList8 == null) {
                                arrayList8 = new ArrayList();
                            }
                            arrayList8.add(barrier3);
                        }
                    } else {
                        HelperWidget helperWidget = (HelperWidget) constraintWidget10;
                        if (arrayList6 == null) {
                            arrayList6 = new ArrayList();
                        }
                        arrayList6.add(helperWidget);
                        if (arrayList8 == null) {
                            arrayList8 = new ArrayList();
                        }
                        arrayList8.add(helperWidget);
                    }
                }
                if (constraintWidget10.mLeft.mTarget == null && constraintWidget10.mRight.mTarget == null && !z12 && !(constraintWidget10 instanceof Barrier)) {
                    if (arrayList5 == null) {
                        arrayList5 = new ArrayList();
                    }
                    arrayList5.add(constraintWidget10);
                }
                if (constraintWidget10.mTop.mTarget == null && constraintWidget10.mBottom.mTarget == null && constraintWidget10.mBaseline.mTarget == null && !z12 && !(constraintWidget10 instanceof Barrier)) {
                    if (arrayList3 == null) {
                        arrayList3 = new ArrayList();
                    }
                    arrayList3.add(constraintWidget10);
                }
                c2 = 1;
                i32 = i33 + 1;
                iMax4 = i34;
                iMax3 = i35;
                dimensionBehaviour6 = dimensionBehaviour16;
                dimensionBehaviour7 = dimensionBehaviour18;
            }
            i7 = iMax3;
            i8 = iMax4;
            dimensionBehaviour4 = dimensionBehaviour7;
            dimensionBehaviour5 = dimensionBehaviour6;
            ArrayList arrayList9 = new ArrayList();
            if (arrayList4 != null) {
                int size4 = arrayList4.size();
                int i36 = 0;
                while (i36 < size4) {
                    Object obj = arrayList4.get(i36);
                    i36++;
                    Grouping.findDependents((Guideline) obj, 0, arrayList9, null);
                }
            }
            int i37 = 0;
            WidgetGroup widgetGroup3 = null;
            if (arrayList6 != null) {
                int size5 = arrayList6.size();
                int i38 = 0;
                while (i38 < size5) {
                    Object obj2 = arrayList6.get(i38);
                    i38++;
                    HelperWidget helperWidget2 = (HelperWidget) obj2;
                    WidgetGroup widgetGroupFindDependents = Grouping.findDependents(helperWidget2, i37, arrayList9, widgetGroup3);
                    helperWidget2.addDependents(i37, widgetGroupFindDependents, arrayList9);
                    widgetGroupFindDependents.cleanup(arrayList9);
                    i37 = 0;
                    widgetGroup3 = null;
                }
            }
            HashSet hashSet = getAnchor(ConstraintAnchor.Type.LEFT).mDependents;
            if (hashSet != null) {
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it.next()).mOwner, 0, arrayList9, null);
                }
            }
            HashSet hashSet2 = getAnchor(ConstraintAnchor.Type.RIGHT).mDependents;
            if (hashSet2 != null) {
                Iterator it2 = hashSet2.iterator();
                while (it2.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it2.next()).mOwner, 0, arrayList9, null);
                }
            }
            HashSet hashSet3 = getAnchor(ConstraintAnchor.Type.CENTER).mDependents;
            if (hashSet3 != null) {
                Iterator it3 = hashSet3.iterator();
                while (it3.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it3.next()).mOwner, 0, arrayList9, null);
                }
            }
            WidgetGroup widgetGroup4 = null;
            if (arrayList5 != null) {
                int size6 = arrayList5.size();
                int i39 = 0;
                while (i39 < size6) {
                    Object obj3 = arrayList5.get(i39);
                    i39++;
                    Grouping.findDependents((ConstraintWidget) obj3, 0, arrayList9, null);
                }
            }
            int i40 = 1;
            if (arrayList7 != null) {
                int size7 = arrayList7.size();
                int i41 = 0;
                while (i41 < size7) {
                    Object obj4 = arrayList7.get(i41);
                    i41++;
                    Grouping.findDependents((Guideline) obj4, 1, arrayList9, null);
                }
            }
            if (arrayList8 != null) {
                int size8 = arrayList8.size();
                int i42 = 0;
                while (i42 < size8) {
                    Object obj5 = arrayList8.get(i42);
                    i42 += i40;
                    HelperWidget helperWidget3 = (HelperWidget) obj5;
                    WidgetGroup widgetGroupFindDependents2 = Grouping.findDependents(helperWidget3, i40, arrayList9, widgetGroup4);
                    helperWidget3.addDependents(i40, widgetGroupFindDependents2, arrayList9);
                    widgetGroupFindDependents2.cleanup(arrayList9);
                    i40 = 1;
                    widgetGroup4 = null;
                }
            }
            HashSet hashSet4 = getAnchor(ConstraintAnchor.Type.TOP).mDependents;
            if (hashSet4 != null) {
                Iterator it4 = hashSet4.iterator();
                while (it4.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it4.next()).mOwner, 1, arrayList9, null);
                }
            }
            HashSet hashSet5 = getAnchor(ConstraintAnchor.Type.BASELINE).mDependents;
            if (hashSet5 != null) {
                Iterator it5 = hashSet5.iterator();
                while (it5.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it5.next()).mOwner, 1, arrayList9, null);
                }
            }
            HashSet hashSet6 = getAnchor(ConstraintAnchor.Type.BOTTOM).mDependents;
            if (hashSet6 != null) {
                Iterator it6 = hashSet6.iterator();
                while (it6.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it6.next()).mOwner, 1, arrayList9, null);
                }
            }
            HashSet hashSet7 = getAnchor(ConstraintAnchor.Type.CENTER).mDependents;
            if (hashSet7 != null) {
                Iterator it7 = hashSet7.iterator();
                while (it7.hasNext()) {
                    Grouping.findDependents(((ConstraintAnchor) it7.next()).mOwner, 1, arrayList9, null);
                }
            }
            if (arrayList3 != null) {
                int size9 = arrayList3.size();
                int i43 = 0;
                while (i43 < size9) {
                    Object obj6 = arrayList3.get(i43);
                    i43++;
                    Grouping.findDependents((ConstraintWidget) obj6, 1, arrayList9, null);
                }
            }
            for (int i44 = 0; i44 < size3; i44++) {
                ConstraintWidget constraintWidget11 = (ConstraintWidget) arrayList2.get(i44);
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr7 = constraintWidget11.mListDimensionBehaviors;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour19 = dimensionBehaviourArr7[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour20 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour19 == dimensionBehaviour20 && dimensionBehaviourArr7[1] == dimensionBehaviour20) {
                    int i45 = constraintWidget11.horizontalGroup;
                    int size10 = arrayList9.size();
                    int i46 = 0;
                    while (true) {
                        if (i46 >= size10) {
                            widgetGroup = null;
                            break;
                        }
                        widgetGroup = (WidgetGroup) arrayList9.get(i46);
                        if (i45 == widgetGroup.mId) {
                            break;
                        } else {
                            i46++;
                        }
                    }
                    int i47 = constraintWidget11.verticalGroup;
                    int size11 = arrayList9.size();
                    int i48 = 0;
                    while (true) {
                        if (i48 >= size11) {
                            widgetGroup2 = null;
                            break;
                        }
                        widgetGroup2 = (WidgetGroup) arrayList9.get(i48);
                        if (i47 == widgetGroup2.mId) {
                            break;
                        } else {
                            i48++;
                        }
                    }
                    if (widgetGroup != null && widgetGroup2 != null) {
                        widgetGroup.moveTo(0, widgetGroup2);
                        widgetGroup2.mOrientation = 2;
                        arrayList9.remove(widgetGroup);
                    }
                }
            }
            int i49 = 1;
            if (arrayList9.size() > 1) {
                if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    int size12 = arrayList9.size();
                    int i50 = 0;
                    int i51 = 0;
                    WidgetGroup widgetGroup5 = null;
                    while (i51 < size12) {
                        Object obj7 = arrayList9.get(i51);
                        i51 += i49;
                        WidgetGroup widgetGroup6 = (WidgetGroup) obj7;
                        if (widgetGroup6.mOrientation != i49) {
                            int iMeasureWrap = widgetGroup6.measureWrap(linearSystem, 0);
                            if (iMeasureWrap > i50) {
                                widgetGroup5 = widgetGroup6;
                                i50 = iMeasureWrap;
                            }
                            i49 = 1;
                        }
                    }
                    if (widgetGroup5 != null) {
                        setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                        setWidth(i50);
                    } else {
                        widgetGroup5 = null;
                    }
                    int i52 = 1;
                    if (this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                        int size13 = arrayList9.size();
                        int i53 = 0;
                        int i54 = 0;
                        WidgetGroup widgetGroup7 = null;
                        while (i54 < size13) {
                            Object obj8 = arrayList9.get(i54);
                            i54 += i52;
                            WidgetGroup widgetGroup8 = (WidgetGroup) obj8;
                            if (widgetGroup8.mOrientation != 0) {
                                int iMeasureWrap2 = widgetGroup8.measureWrap(linearSystem, i52);
                                if (iMeasureWrap2 > i53) {
                                    widgetGroup7 = widgetGroup8;
                                    i53 = iMeasureWrap2;
                                }
                                i52 = 1;
                            }
                        }
                        if (widgetGroup7 != null) {
                            setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                            setHeight(i53);
                        } else {
                            widgetGroup7 = null;
                        }
                        if (widgetGroup5 != null || widgetGroup7 != null) {
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour21 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                            dimensionBehaviour = dimensionBehaviour4;
                            if (dimensionBehaviour == dimensionBehaviour21) {
                                i9 = i7;
                                if (i9 >= getWidth() || i9 <= 0) {
                                    width = getWidth();
                                    dimensionBehaviour2 = dimensionBehaviour5;
                                    if (dimensionBehaviour2 != dimensionBehaviour21) {
                                        i10 = i8;
                                        if (i10 >= getHeight() || i10 <= 0) {
                                            height = getHeight();
                                            z = true;
                                        } else {
                                            setHeight(i10);
                                            this.mHeightMeasuredTooSmall = true;
                                        }
                                    } else {
                                        i10 = i8;
                                    }
                                    height = i10;
                                    z = true;
                                } else {
                                    setWidth(i9);
                                    this.mWidthMeasuredTooSmall = true;
                                }
                            } else {
                                i9 = i7;
                            }
                            width = i9;
                            dimensionBehaviour2 = dimensionBehaviour5;
                            if (dimensionBehaviour2 != dimensionBehaviour21) {
                            }
                            height = i10;
                            z = true;
                        }
                    }
                }
                boolean z13 = !optimizeFor(64) || optimizeFor(128);
                linearSystem.getClass();
                linearSystem.newgraphOptimizer = false;
                if (this.mOptimizationLevel == 0 && z13) {
                    c = 1;
                    linearSystem.newgraphOptimizer = true;
                } else {
                    c = 1;
                }
                ArrayList arrayList10 = this.mChildren;
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr8 = this.mListDimensionBehaviors;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour22 = dimensionBehaviourArr8[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour23 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                boolean z14 = dimensionBehaviour22 != dimensionBehaviour23 || dimensionBehaviourArr8[c] == dimensionBehaviour23;
                this.mHorizontalChainsSize = 0;
                this.mVerticalChainsSize = 0;
                i4 = i;
                for (i5 = 0; i5 < i4; i5++) {
                    ConstraintWidget constraintWidget12 = (ConstraintWidget) this.mChildren.get(i5);
                    if (constraintWidget12 instanceof WidgetContainer) {
                        ((WidgetContainer) constraintWidget12).layout();
                    }
                }
                boolean zOptimizeFor = optimizeFor(64);
                boolean z15 = z;
                z2 = true;
                int i55 = 0;
                while (z2) {
                    int i56 = i55 + 1;
                    try {
                        linearSystem.reset();
                        this.mHorizontalChainsSize = 0;
                        this.mVerticalChainsSize = 0;
                        createObjectVariables(linearSystem);
                        for (int i57 = 0; i57 < i4; i57++) {
                            ((ConstraintWidget) this.mChildren.get(i57)).createObjectVariables(linearSystem);
                        }
                        addChildrenToSolver(linearSystem);
                        try {
                            WeakReference weakReference2 = this.mVerticalWrapMin;
                            if (weakReference2 == null || weakReference2.get() == null) {
                                z4 = z14;
                                constraintAnchor3 = constraintAnchor2;
                            } else {
                                constraintAnchor3 = constraintAnchor2;
                                try {
                                    z4 = z14;
                                } catch (Exception e) {
                                    e = e;
                                    z4 = z14;
                                    z2 = true;
                                    e.printStackTrace();
                                    z5 = z15;
                                    System.out.println("EXCEPTION : " + e);
                                    boolean[] zArr2 = Optimizer.sFlags;
                                    if (z2) {
                                    }
                                    if (!z4) {
                                    }
                                    iMax = Math.max(this.mMinWidth, getWidth());
                                    if (iMax > getWidth()) {
                                    }
                                    iMax2 = Math.max(this.mMinHeight, getHeight());
                                    if (iMax2 <= getHeight()) {
                                    }
                                    if (z5) {
                                    }
                                    if (i56 <= i6) {
                                    }
                                    i55 = i56;
                                    constraintAnchor2 = constraintAnchor3;
                                    z14 = z4;
                                }
                                try {
                                    linearSystem.addGreaterThan(linearSystem.createObjectVariable((ConstraintAnchor) this.mVerticalWrapMin.get()), linearSystem.createObjectVariable(constraintAnchor3), 0, 5);
                                    this.mVerticalWrapMin = null;
                                } catch (Exception e2) {
                                    e = e2;
                                    z2 = true;
                                    e.printStackTrace();
                                    z5 = z15;
                                    System.out.println("EXCEPTION : " + e);
                                    boolean[] zArr22 = Optimizer.sFlags;
                                    if (z2) {
                                    }
                                    if (!z4) {
                                    }
                                    iMax = Math.max(this.mMinWidth, getWidth());
                                    if (iMax > getWidth()) {
                                    }
                                    iMax2 = Math.max(this.mMinHeight, getHeight());
                                    if (iMax2 <= getHeight()) {
                                    }
                                    if (z5) {
                                    }
                                    if (i56 <= i6) {
                                    }
                                    i55 = i56;
                                    constraintAnchor2 = constraintAnchor3;
                                    z14 = z4;
                                }
                            }
                            WeakReference weakReference3 = this.mVerticalWrapMax;
                            if (weakReference3 != null && weakReference3.get() != null) {
                                linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mBottom), linearSystem.createObjectVariable((ConstraintAnchor) this.mVerticalWrapMax.get()), 0, 5);
                                this.mVerticalWrapMax = null;
                            }
                            WeakReference weakReference4 = this.mHorizontalWrapMin;
                            if (weakReference4 == null || weakReference4.get() == null) {
                                weakReference = this.mHorizontalWrapMax;
                                if (weakReference == null && weakReference.get() != null) {
                                    try {
                                        linearSystem.addGreaterThan(linearSystem.createObjectVariable(this.mRight), linearSystem.createObjectVariable((ConstraintAnchor) this.mHorizontalWrapMax.get()), 0, 5);
                                    } catch (Exception e3) {
                                        e = e3;
                                        z2 = true;
                                        e.printStackTrace();
                                        z5 = z15;
                                        System.out.println("EXCEPTION : " + e);
                                        boolean[] zArr222 = Optimizer.sFlags;
                                        if (z2) {
                                        }
                                        if (!z4) {
                                        }
                                        iMax = Math.max(this.mMinWidth, getWidth());
                                        if (iMax > getWidth()) {
                                        }
                                        iMax2 = Math.max(this.mMinHeight, getHeight());
                                        if (iMax2 <= getHeight()) {
                                        }
                                        if (z5) {
                                        }
                                        if (i56 <= i6) {
                                        }
                                        i55 = i56;
                                        constraintAnchor2 = constraintAnchor3;
                                        z14 = z4;
                                    }
                                    try {
                                        this.mHorizontalWrapMax = null;
                                    } catch (Exception e4) {
                                        e = e4;
                                        z2 = true;
                                        e.printStackTrace();
                                        z5 = z15;
                                        System.out.println("EXCEPTION : " + e);
                                        boolean[] zArr2222 = Optimizer.sFlags;
                                        if (z2) {
                                        }
                                        if (!z4) {
                                        }
                                        iMax = Math.max(this.mMinWidth, getWidth());
                                        if (iMax > getWidth()) {
                                        }
                                        iMax2 = Math.max(this.mMinHeight, getHeight());
                                        if (iMax2 <= getHeight()) {
                                        }
                                        if (z5) {
                                        }
                                        if (i56 <= i6) {
                                        }
                                        i55 = i56;
                                        constraintAnchor2 = constraintAnchor3;
                                        z14 = z4;
                                    }
                                }
                                linearSystem.minimize();
                                z5 = z15;
                                z2 = true;
                            } else {
                                ConstraintAnchor constraintAnchor7 = constraintAnchor;
                                try {
                                    constraintAnchor = constraintAnchor7;
                                    linearSystem.addGreaterThan(linearSystem.createObjectVariable((ConstraintAnchor) this.mHorizontalWrapMin.get()), linearSystem.createObjectVariable(constraintAnchor7), 0, 5);
                                    this.mHorizontalWrapMin = null;
                                    weakReference = this.mHorizontalWrapMax;
                                    if (weakReference == null) {
                                        linearSystem.minimize();
                                        z5 = z15;
                                        z2 = true;
                                    }
                                } catch (Exception e5) {
                                    e = e5;
                                    constraintAnchor = constraintAnchor7;
                                    z2 = true;
                                    e.printStackTrace();
                                    z5 = z15;
                                    System.out.println("EXCEPTION : " + e);
                                    boolean[] zArr22222 = Optimizer.sFlags;
                                    if (z2) {
                                    }
                                    if (!z4) {
                                    }
                                    iMax = Math.max(this.mMinWidth, getWidth());
                                    if (iMax > getWidth()) {
                                    }
                                    iMax2 = Math.max(this.mMinHeight, getHeight());
                                    if (iMax2 <= getHeight()) {
                                    }
                                    if (z5) {
                                    }
                                    if (i56 <= i6) {
                                    }
                                    i55 = i56;
                                    constraintAnchor2 = constraintAnchor3;
                                    z14 = z4;
                                }
                            }
                        } catch (Exception e6) {
                            e = e6;
                            z4 = z14;
                            constraintAnchor3 = constraintAnchor2;
                        }
                    } catch (Exception e7) {
                        e = e7;
                        z4 = z14;
                        constraintAnchor3 = constraintAnchor2;
                    }
                    boolean[] zArr222222 = Optimizer.sFlags;
                    if (z2) {
                        zArr = zArr222222;
                        updateFromSolver(linearSystem, zOptimizeFor);
                        for (int i58 = 0; i58 < i4; i58++) {
                            ((ConstraintWidget) this.mChildren.get(i58)).updateFromSolver(linearSystem, zOptimizeFor);
                        }
                        z2 = false;
                    } else {
                        zArr222222[2] = false;
                        boolean zOptimizeFor2 = optimizeFor(64);
                        updateFromSolver(linearSystem, zOptimizeFor2);
                        int size14 = this.mChildren.size();
                        int i59 = 0;
                        z2 = false;
                        while (i59 < size14) {
                            boolean[] zArr3 = zArr222222;
                            ConstraintWidget constraintWidget13 = (ConstraintWidget) this.mChildren.get(i59);
                            constraintWidget13.updateFromSolver(linearSystem, zOptimizeFor2);
                            int i60 = i59;
                            boolean z16 = zOptimizeFor2;
                            if (constraintWidget13.mWidthOverride != -1 || constraintWidget13.mHeightOverride != -1) {
                                z2 = true;
                            }
                            i59 = i60 + 1;
                            zArr222222 = zArr3;
                            zOptimizeFor2 = z16;
                        }
                        zArr = zArr222222;
                    }
                    if (!z4 && i56 < 8) {
                        if (zArr[2]) {
                            int iMax5 = 0;
                            int iMax6 = 0;
                            for (int i61 = 0; i61 < i4; i61++) {
                                ConstraintWidget constraintWidget14 = (ConstraintWidget) this.mChildren.get(i61);
                                iMax6 = Math.max(iMax6, constraintWidget14.getWidth() + constraintWidget14.mX);
                                iMax5 = Math.max(iMax5, constraintWidget14.getHeight() + constraintWidget14.mY);
                            }
                            int iMax7 = Math.max(this.mMinWidth, iMax6);
                            int iMax8 = Math.max(this.mMinHeight, iMax5);
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour24 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                            if (dimensionBehaviour == dimensionBehaviour24 && getWidth() < iMax7) {
                                setWidth(iMax7);
                                this.mListDimensionBehaviors[0] = dimensionBehaviour24;
                                z2 = true;
                                z5 = true;
                            }
                            if (dimensionBehaviour2 == dimensionBehaviour24 && getHeight() < iMax8) {
                                setHeight(iMax8);
                                this.mListDimensionBehaviors[1] = dimensionBehaviour24;
                                z2 = true;
                                z5 = true;
                            }
                        }
                    }
                    iMax = Math.max(this.mMinWidth, getWidth());
                    if (iMax > getWidth()) {
                        setWidth(iMax);
                        this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
                        z2 = true;
                        z5 = true;
                    }
                    iMax2 = Math.max(this.mMinHeight, getHeight());
                    if (iMax2 <= getHeight()) {
                        setHeight(iMax2);
                        r8 = 1;
                        this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
                        z2 = true;
                        z5 = true;
                    } else {
                        r8 = 1;
                    }
                    if (z5) {
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour25 = this.mListDimensionBehaviors[0];
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour26 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                        if (dimensionBehaviour25 == dimensionBehaviour26 && width > 0 && getWidth() > width) {
                            this.mWidthMeasuredTooSmall = r8;
                            this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
                            setWidth(width);
                            z2 = r8;
                            z5 = z2;
                        }
                        if (this.mListDimensionBehaviors[r8] != dimensionBehaviour26 || height <= 0 || getHeight() <= height) {
                            z15 = z5;
                            i6 = 8;
                        } else {
                            this.mHeightMeasuredTooSmall = r8;
                            this.mListDimensionBehaviors[r8] = ConstraintWidget.DimensionBehaviour.FIXED;
                            setHeight(height);
                            i6 = 8;
                            z15 = true;
                            z2 = true;
                        }
                    }
                    if (i56 <= i6) {
                        z2 = false;
                    }
                    i55 = i56;
                    constraintAnchor2 = constraintAnchor3;
                    z14 = z4;
                }
                z3 = z15;
                this.mChildren = arrayList10;
                if (z3) {
                    ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr9 = this.mListDimensionBehaviors;
                    dimensionBehaviourArr9[0] = dimensionBehaviour;
                    dimensionBehaviourArr9[1] = dimensionBehaviour2;
                }
                resetSolverVariables(linearSystem.mCache);
            }
            i2 = i8;
            i3 = i7;
            dimensionBehaviour2 = dimensionBehaviour5;
            dimensionBehaviour = dimensionBehaviour4;
        }
        width = i3;
        height = i2;
        z = false;
        if (optimizeFor(64)) {
        }
        linearSystem.getClass();
        linearSystem.newgraphOptimizer = false;
        if (this.mOptimizationLevel == 0) {
            c = 1;
        }
        ArrayList arrayList102 = this.mChildren;
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr82 = this.mListDimensionBehaviors;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour222 = dimensionBehaviourArr82[0];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour232 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (dimensionBehaviour222 != dimensionBehaviour232) {
        }
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        i4 = i;
        while (i5 < i4) {
        }
        boolean zOptimizeFor3 = optimizeFor(64);
        boolean z152 = z;
        z2 = true;
        int i552 = 0;
        while (z2) {
        }
        z3 = z152;
        this.mChildren = arrayList102;
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
        this.mVerticalWrapMin = null;
        this.mHorizontalWrapMin = null;
        this.mVerticalWrapMax = null;
        this.mHorizontalWrapMax = null;
        this.mWidgetsToAdd = new HashSet();
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
        this.mVerticalWrapMin = null;
        this.mHorizontalWrapMin = null;
        this.mVerticalWrapMax = null;
        this.mHorizontalWrapMax = null;
        this.mWidgetsToAdd = new HashSet();
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
        this.mVerticalWrapMin = null;
        this.mHorizontalWrapMin = null;
        this.mVerticalWrapMax = null;
        this.mHorizontalWrapMax = null;
        this.mWidgetsToAdd = new HashSet();
        this.mMeasure = new BasicMeasure.Measure();
        this.mDebugName = str;
    }
}
