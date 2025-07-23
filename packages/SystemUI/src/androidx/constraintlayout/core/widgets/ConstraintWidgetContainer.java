package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.Metrics;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        boolean optimizeFor = optimizeFor(64);
        addToSolver(linearSystem, optimizeFor);
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
                    constraintWidget4.addToSolver(linearSystem, optimizeFor);
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
                        virtualLayout.addToSolver(linearSystem, optimizeFor);
                        this.mWidgetsToAdd.remove(virtualLayout);
                        break;
                    }
                }
            }
            if (size2 == this.mWidgetsToAdd.size()) {
                Iterator it2 = this.mWidgetsToAdd.iterator();
                while (it2.hasNext()) {
                    ((ConstraintWidget) it2.next()).addToSolver(linearSystem, optimizeFor);
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
                constraintWidget6.addToSolver(linearSystem2, optimizeFor);
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
                    constraintWidget7.addToSolver(linearSystem2, optimizeFor);
                    if (dimensionBehaviour == dimensionBehaviour3) {
                        constraintWidget7.setHorizontalDimensionBehaviour(dimensionBehaviour);
                    }
                    if (dimensionBehaviour2 == dimensionBehaviour3) {
                        constraintWidget7.setVerticalDimensionBehaviour(dimensionBehaviour2);
                    }
                } else {
                    Optimizer.checkMatchParent(constraintWidgetContainer, linearSystem2, constraintWidget7);
                    if (!(constraintWidget7 instanceof VirtualLayout) && !(constraintWidget7 instanceof Guideline)) {
                        constraintWidget7.addToSolver(linearSystem2, optimizeFor);
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
                if (!widgetRun3.start.resolved) {
                    break;
                }
                if (!widgetRun3.end.resolved) {
                    break;
                }
                if (!(widgetRun3 instanceof ChainRun) && !widgetRun3.mDimension.resolved) {
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

    /* JADX WARN: Removed duplicated region for block: B:197:0x068a  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x06a0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:206:0x06b1  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x06c2  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x06e4  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x07e4  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x084a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:293:0x08c8  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x08e6  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x08f7  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x093b  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x093c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:316:0x08f4  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x082a  */
    /* JADX WARN: Removed duplicated region for block: B:357:0x094d  */
    /* JADX WARN: Removed duplicated region for block: B:571:0x0607  */
    /* JADX WARN: Removed duplicated region for block: B:587:0x0634 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:590:0x0646  */
    /* JADX WARN: Removed duplicated region for block: B:597:0x0664  */
    /* JADX WARN: Removed duplicated region for block: B:604:0x067a  */
    /* JADX WARN: Removed duplicated region for block: B:606:0x065e  */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v13 */
    @Override // androidx.constraintlayout.core.widgets.WidgetContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void layout() {
        /*
            Method dump skipped, instructions count: 2397
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.ConstraintWidgetContainer.layout():void");
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
