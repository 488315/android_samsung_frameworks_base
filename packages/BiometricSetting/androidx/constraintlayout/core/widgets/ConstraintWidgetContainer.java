package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph;
import androidx.constraintlayout.core.widgets.analyzer.Direct;
import androidx.constraintlayout.core.widgets.analyzer.Grouping;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class ConstraintWidgetContainer extends WidgetContainer {
    int mPaddingLeft;
    int mPaddingTop;
    private int mPass;
    BasicMeasure mBasicMeasureSolver = new BasicMeasure(this);
    public DependencyGraph mDependencyGraph = new DependencyGraph(this);
    protected BasicMeasure.Measurer mMeasurer = null;
    private boolean mIsRtl = false;
    protected LinearSystem mSystem = new LinearSystem();
    public int mHorizontalChainsSize = 0;
    public int mVerticalChainsSize = 0;
    ChainHead[] mVerticalChainsArray = new ChainHead[4];
    ChainHead[] mHorizontalChainsArray = new ChainHead[4];
    private int mOptimizationLevel = 257;
    private boolean mWidthMeasuredTooSmall = false;
    private boolean mHeightMeasuredTooSmall = false;
    private WeakReference<ConstraintAnchor> mVerticalWrapMin = null;
    private WeakReference<ConstraintAnchor> mHorizontalWrapMin = null;
    private WeakReference<ConstraintAnchor> mVerticalWrapMax = null;
    private WeakReference<ConstraintAnchor> mHorizontalWrapMax = null;
    HashSet<ConstraintWidget> mWidgetsToAdd = new HashSet<>();
    public BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();

    final void addChain(int i, ConstraintWidget constraintWidget) {
        if (i == 0) {
            int i2 = this.mHorizontalChainsSize + 1;
            ChainHead[] chainHeadArr = this.mHorizontalChainsArray;
            if (i2 >= chainHeadArr.length) {
                this.mHorizontalChainsArray = (ChainHead[]) Arrays.copyOf(chainHeadArr, chainHeadArr.length * 2);
            }
            ChainHead[] chainHeadArr2 = this.mHorizontalChainsArray;
            int i3 = this.mHorizontalChainsSize;
            chainHeadArr2[i3] = new ChainHead(constraintWidget, 0, this.mIsRtl);
            this.mHorizontalChainsSize = i3 + 1;
            return;
        }
        if (i == 1) {
            int i4 = this.mVerticalChainsSize + 1;
            ChainHead[] chainHeadArr3 = this.mVerticalChainsArray;
            if (i4 >= chainHeadArr3.length) {
                this.mVerticalChainsArray = (ChainHead[]) Arrays.copyOf(chainHeadArr3, chainHeadArr3.length * 2);
            }
            ChainHead[] chainHeadArr4 = this.mVerticalChainsArray;
            int i5 = this.mVerticalChainsSize;
            chainHeadArr4[i5] = new ChainHead(constraintWidget, 1, this.mIsRtl);
            this.mVerticalChainsSize = i5 + 1;
        }
    }

    public final void addChildrenToSolver(LinearSystem linearSystem) {
        boolean z;
        boolean optimizeFor = optimizeFor(64);
        addToSolver(linearSystem, optimizeFor);
        int size = this.mChildren.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = this.mChildren.get(i);
            constraintWidget.setInBarrier(0, false);
            constraintWidget.setInBarrier(1, false);
            if (constraintWidget instanceof Barrier) {
                z2 = true;
            }
        }
        if (z2) {
            for (int i2 = 0; i2 < size; i2++) {
                ConstraintWidget constraintWidget2 = this.mChildren.get(i2);
                if (constraintWidget2 instanceof Barrier) {
                    ((Barrier) constraintWidget2).markWidgets();
                }
            }
        }
        this.mWidgetsToAdd.clear();
        for (int i3 = 0; i3 < size; i3++) {
            ConstraintWidget constraintWidget3 = this.mChildren.get(i3);
            constraintWidget3.getClass();
            if ((constraintWidget3 instanceof VirtualLayout) || (constraintWidget3 instanceof Guideline)) {
                if (constraintWidget3 instanceof VirtualLayout) {
                    this.mWidgetsToAdd.add(constraintWidget3);
                } else {
                    constraintWidget3.addToSolver(linearSystem, optimizeFor);
                }
            }
        }
        while (this.mWidgetsToAdd.size() > 0) {
            int size2 = this.mWidgetsToAdd.size();
            Iterator<ConstraintWidget> it = this.mWidgetsToAdd.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                VirtualLayout virtualLayout = (VirtualLayout) it.next();
                HashSet<ConstraintWidget> hashSet = this.mWidgetsToAdd;
                int i4 = 0;
                while (true) {
                    if (i4 >= virtualLayout.mWidgetsCount) {
                        z = false;
                        break;
                    } else {
                        if (hashSet.contains(virtualLayout.mWidgets[i4])) {
                            z = true;
                            break;
                        }
                        i4++;
                    }
                }
                if (z) {
                    virtualLayout.addToSolver(linearSystem, optimizeFor);
                    this.mWidgetsToAdd.remove(virtualLayout);
                    break;
                }
            }
            if (size2 == this.mWidgetsToAdd.size()) {
                Iterator<ConstraintWidget> it2 = this.mWidgetsToAdd.iterator();
                while (it2.hasNext()) {
                    it2.next().addToSolver(linearSystem, optimizeFor);
                }
                this.mWidgetsToAdd.clear();
            }
        }
        boolean z3 = LinearSystem.USE_DEPENDENCY_ORDERING;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (z3) {
            HashSet<ConstraintWidget> hashSet2 = new HashSet<>();
            for (int i5 = 0; i5 < size; i5++) {
                ConstraintWidget constraintWidget4 = this.mChildren.get(i5);
                constraintWidget4.getClass();
                if (!((constraintWidget4 instanceof VirtualLayout) || (constraintWidget4 instanceof Guideline))) {
                    hashSet2.add(constraintWidget4);
                }
            }
            addChildrenToSolverByDependency(this, linearSystem, hashSet2, this.mListDimensionBehaviors[0] == dimensionBehaviour ? 0 : 1, false);
            Iterator<ConstraintWidget> it3 = hashSet2.iterator();
            while (it3.hasNext()) {
                ConstraintWidget next = it3.next();
                Optimizer.checkMatchParent(this, linearSystem, next);
                next.addToSolver(linearSystem, optimizeFor);
            }
        } else {
            for (int i6 = 0; i6 < size; i6++) {
                ConstraintWidget constraintWidget5 = this.mChildren.get(i6);
                if (constraintWidget5 instanceof ConstraintWidgetContainer) {
                    ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget5.mListDimensionBehaviors;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[0];
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = dimensionBehaviourArr[1];
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.FIXED;
                    if (dimensionBehaviour2 == dimensionBehaviour) {
                        constraintWidget5.setHorizontalDimensionBehaviour(dimensionBehaviour4);
                    }
                    if (dimensionBehaviour3 == dimensionBehaviour) {
                        constraintWidget5.setVerticalDimensionBehaviour(dimensionBehaviour4);
                    }
                    constraintWidget5.addToSolver(linearSystem, optimizeFor);
                    if (dimensionBehaviour2 == dimensionBehaviour) {
                        constraintWidget5.setHorizontalDimensionBehaviour(dimensionBehaviour2);
                    }
                    if (dimensionBehaviour3 == dimensionBehaviour) {
                        constraintWidget5.setVerticalDimensionBehaviour(dimensionBehaviour3);
                    }
                } else {
                    Optimizer.checkMatchParent(this, linearSystem, constraintWidget5);
                    if (!((constraintWidget5 instanceof VirtualLayout) || (constraintWidget5 instanceof Guideline))) {
                        constraintWidget5.addToSolver(linearSystem, optimizeFor);
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

    public final void addHorizontalWrapMaxVariable(ConstraintAnchor constraintAnchor) {
        WeakReference<ConstraintAnchor> weakReference = this.mHorizontalWrapMax;
        if (weakReference == null || weakReference.get() == null || constraintAnchor.getFinalValue() > this.mHorizontalWrapMax.get().getFinalValue()) {
            this.mHorizontalWrapMax = new WeakReference<>(constraintAnchor);
        }
    }

    public final void addHorizontalWrapMinVariable(ConstraintAnchor constraintAnchor) {
        WeakReference<ConstraintAnchor> weakReference = this.mHorizontalWrapMin;
        if (weakReference == null || weakReference.get() == null || constraintAnchor.getFinalValue() > this.mHorizontalWrapMin.get().getFinalValue()) {
            this.mHorizontalWrapMin = new WeakReference<>(constraintAnchor);
        }
    }

    final void addVerticalWrapMaxVariable(ConstraintAnchor constraintAnchor) {
        WeakReference<ConstraintAnchor> weakReference = this.mVerticalWrapMax;
        if (weakReference == null || weakReference.get() == null || constraintAnchor.getFinalValue() > this.mVerticalWrapMax.get().getFinalValue()) {
            this.mVerticalWrapMax = new WeakReference<>(constraintAnchor);
        }
    }

    final void addVerticalWrapMinVariable(ConstraintAnchor constraintAnchor) {
        WeakReference<ConstraintAnchor> weakReference = this.mVerticalWrapMin;
        if (weakReference == null || weakReference.get() == null || constraintAnchor.getFinalValue() > this.mVerticalWrapMin.get().getFinalValue()) {
            this.mVerticalWrapMin = new WeakReference<>(constraintAnchor);
        }
    }

    public final void fillMetrics() {
        this.mSystem.getClass();
    }

    public final BasicMeasure.Measurer getMeasurer() {
        return this.mMeasurer;
    }

    public final int getOptimizationLevel() {
        return this.mOptimizationLevel;
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
        Iterator<ConstraintWidget> it = this.mChildren.iterator();
        while (it.hasNext()) {
            it.next().getSceneString(sb);
            sb.append(",\n");
        }
        sb.append("}");
    }

    public final LinearSystem getSystem() {
        return this.mSystem;
    }

    public final boolean isHeightMeasuredTooSmall() {
        return this.mHeightMeasuredTooSmall;
    }

    public final boolean isRtl() {
        return this.mIsRtl;
    }

    public final boolean isWidthMeasuredTooSmall() {
        return this.mWidthMeasuredTooSmall;
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x02f2  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x030d  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x031c  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0359  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x035b  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0259  */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v20 */
    @Override // androidx.constraintlayout.core.widgets.WidgetContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void layout() {
        int i;
        int i2;
        boolean z;
        boolean z2;
        boolean z3;
        int max;
        int width;
        int max2;
        ?? r2;
        boolean z4;
        int i3;
        int i4 = 0;
        this.f9mX = 0;
        this.f10mY = 0;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        int size = this.mChildren.size();
        int max3 = Math.max(0, getWidth());
        int max4 = Math.max(0, getHeight());
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[1];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[0];
        if (this.mPass == 0 && Optimizer.enabled(this.mOptimizationLevel, 1)) {
            Direct.solvingPass(this, this.mMeasurer);
            for (int i5 = 0; i5 < size; i5++) {
                ConstraintWidget constraintWidget = this.mChildren.get(i5);
                if (constraintWidget.isMeasureRequested() && !(constraintWidget instanceof Guideline) && !(constraintWidget instanceof Barrier) && !(constraintWidget instanceof VirtualLayout) && !constraintWidget.isInVirtualLayout()) {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = constraintWidget.getDimensionBehaviour(0);
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = constraintWidget.getDimensionBehaviour(1);
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    if (!(dimensionBehaviour3 == dimensionBehaviour5 && constraintWidget.mMatchConstraintDefaultWidth != 1 && dimensionBehaviour4 == dimensionBehaviour5 && constraintWidget.mMatchConstraintDefaultHeight != 1)) {
                        measure(constraintWidget, this.mMeasurer, new BasicMeasure.Measure());
                    }
                }
            }
        }
        ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (size <= 2 || !((dimensionBehaviour2 == dimensionBehaviour6 || dimensionBehaviour == dimensionBehaviour6) && Optimizer.enabled(this.mOptimizationLevel, 1024) && Grouping.simpleSolvingPass(this, this.mMeasurer))) {
            i = max4;
            i2 = max3;
            z = false;
        } else {
            if (dimensionBehaviour2 == dimensionBehaviour6) {
                if (max3 >= getWidth() || max3 <= 0) {
                    max3 = getWidth();
                } else {
                    setWidth(max3);
                    this.mWidthMeasuredTooSmall = true;
                }
            }
            if (dimensionBehaviour == dimensionBehaviour6) {
                if (max4 >= getHeight() || max4 <= 0) {
                    max4 = getHeight();
                } else {
                    setHeight(max4);
                    this.mHeightMeasuredTooSmall = true;
                }
            }
            i = max4;
            i2 = max3;
            z = true;
        }
        boolean z5 = optimizeFor(64) || optimizeFor(128);
        LinearSystem linearSystem = this.mSystem;
        linearSystem.getClass();
        linearSystem.newgraphOptimizer = false;
        if (this.mOptimizationLevel != 0 && z5) {
            linearSystem.newgraphOptimizer = true;
        }
        ArrayList<ConstraintWidget> arrayList = this.mChildren;
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = this.mListDimensionBehaviors;
        boolean z6 = dimensionBehaviourArr2[0] == dimensionBehaviour6 || dimensionBehaviourArr2[1] == dimensionBehaviour6;
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        for (int i6 = 0; i6 < size; i6++) {
            ConstraintWidget constraintWidget2 = this.mChildren.get(i6);
            if (constraintWidget2 instanceof WidgetContainer) {
                ((WidgetContainer) constraintWidget2).layout();
            }
        }
        boolean optimizeFor = optimizeFor(64);
        boolean z7 = z;
        int i7 = 0;
        boolean z8 = true;
        while (z8) {
            int i8 = i7 + 1;
            try {
                this.mSystem.reset();
                this.mHorizontalChainsSize = i4;
                this.mVerticalChainsSize = i4;
                createObjectVariables(this.mSystem);
                for (int i9 = i4; i9 < size; i9++) {
                    this.mChildren.get(i9).createObjectVariables(this.mSystem);
                }
                addChildrenToSolver(this.mSystem);
                try {
                    WeakReference<ConstraintAnchor> weakReference = this.mVerticalWrapMin;
                    if (weakReference == null || weakReference.get() == null) {
                        z2 = z7;
                    } else {
                        z2 = z7;
                        try {
                            this.mSystem.addGreaterThan(this.mSystem.createObjectVariable(this.mVerticalWrapMin.get()), this.mSystem.createObjectVariable(this.mTop), 0, 5);
                            this.mVerticalWrapMin = null;
                        } catch (Exception e) {
                            e = e;
                            z8 = true;
                            e.printStackTrace();
                            System.out.println("EXCEPTION : " + e);
                            boolean[] zArr = Optimizer.sFlags;
                            if (z8) {
                            }
                            if (z6) {
                            }
                            z7 = z2;
                            max = Math.max(this.mMinWidth, getWidth());
                            width = getWidth();
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = ConstraintWidget.DimensionBehaviour.FIXED;
                            if (max > width) {
                            }
                            max2 = Math.max(this.mMinHeight, getHeight());
                            if (max2 <= getHeight()) {
                            }
                            if (!z7) {
                            }
                            z4 = z3;
                            i3 = 8;
                            if (i8 > i3) {
                            }
                            i7 = i8;
                            i4 = 0;
                        }
                    }
                    WeakReference<ConstraintAnchor> weakReference2 = this.mVerticalWrapMax;
                    if (weakReference2 != null && weakReference2.get() != null) {
                        this.mSystem.addGreaterThan(this.mSystem.createObjectVariable(this.mBottom), this.mSystem.createObjectVariable(this.mVerticalWrapMax.get()), 0, 5);
                        this.mVerticalWrapMax = null;
                    }
                    WeakReference<ConstraintAnchor> weakReference3 = this.mHorizontalWrapMin;
                    if (weakReference3 != null && weakReference3.get() != null) {
                        this.mSystem.addGreaterThan(this.mSystem.createObjectVariable(this.mHorizontalWrapMin.get()), this.mSystem.createObjectVariable(this.mLeft), 0, 5);
                        this.mHorizontalWrapMin = null;
                    }
                    WeakReference<ConstraintAnchor> weakReference4 = this.mHorizontalWrapMax;
                    if (weakReference4 != null && weakReference4.get() != null) {
                        this.mSystem.addGreaterThan(this.mSystem.createObjectVariable(this.mRight), this.mSystem.createObjectVariable(this.mHorizontalWrapMax.get()), 0, 5);
                        this.mHorizontalWrapMax = null;
                    }
                    this.mSystem.minimize();
                    z8 = true;
                } catch (Exception e2) {
                    e = e2;
                    z2 = z7;
                }
            } catch (Exception e3) {
                e = e3;
                z2 = z7;
            }
            boolean[] zArr2 = Optimizer.sFlags;
            if (z8) {
                updateFromSolver(this.mSystem, optimizeFor);
                for (int i10 = 0; i10 < size; i10++) {
                    this.mChildren.get(i10).updateFromSolver(this.mSystem, optimizeFor);
                }
                z3 = false;
            } else {
                LinearSystem linearSystem2 = this.mSystem;
                zArr2[2] = false;
                boolean optimizeFor2 = optimizeFor(64);
                updateFromSolver(linearSystem2, optimizeFor2);
                int size2 = this.mChildren.size();
                int i11 = 0;
                z3 = false;
                while (i11 < size2) {
                    int i12 = size2;
                    ConstraintWidget constraintWidget3 = this.mChildren.get(i11);
                    constraintWidget3.updateFromSolver(linearSystem2, optimizeFor2);
                    if (constraintWidget3.hasDimensionOverride()) {
                        z3 = true;
                    }
                    i11++;
                    size2 = i12;
                }
            }
            if (z6 || i8 >= 8 || !zArr2[2]) {
                z7 = z2;
            } else {
                int i13 = 0;
                int i14 = 0;
                for (int i15 = 0; i15 < size; i15++) {
                    ConstraintWidget constraintWidget4 = this.mChildren.get(i15);
                    i13 = Math.max(i13, constraintWidget4.getWidth() + constraintWidget4.f9mX);
                    i14 = Math.max(i14, constraintWidget4.getHeight() + constraintWidget4.f10mY);
                }
                int max5 = Math.max(this.mMinWidth, i13);
                int max6 = Math.max(this.mMinHeight, i14);
                if (dimensionBehaviour2 != dimensionBehaviour6 || getWidth() >= max5) {
                    z7 = z2;
                } else {
                    setWidth(max5);
                    this.mListDimensionBehaviors[0] = dimensionBehaviour6;
                    z7 = true;
                    z3 = true;
                }
                if (dimensionBehaviour == dimensionBehaviour6 && getHeight() < max6) {
                    setHeight(max6);
                    this.mListDimensionBehaviors[1] = dimensionBehaviour6;
                    z7 = true;
                    z3 = true;
                }
            }
            max = Math.max(this.mMinWidth, getWidth());
            width = getWidth();
            ConstraintWidget.DimensionBehaviour dimensionBehaviour72 = ConstraintWidget.DimensionBehaviour.FIXED;
            if (max > width) {
                setWidth(max);
                this.mListDimensionBehaviors[0] = dimensionBehaviour72;
                z7 = true;
                z3 = true;
            }
            max2 = Math.max(this.mMinHeight, getHeight());
            if (max2 <= getHeight()) {
                setHeight(max2);
                r2 = 1;
                this.mListDimensionBehaviors[1] = dimensionBehaviour72;
                z7 = true;
                z3 = true;
            } else {
                r2 = 1;
            }
            if (!z7) {
                if (this.mListDimensionBehaviors[0] == dimensionBehaviour6 && i2 > 0 && getWidth() > i2) {
                    this.mWidthMeasuredTooSmall = r2;
                    this.mListDimensionBehaviors[0] = dimensionBehaviour72;
                    setWidth(i2);
                    z7 = r2;
                    z3 = z7;
                }
                if (this.mListDimensionBehaviors[r2] == dimensionBehaviour6 && i > 0 && getHeight() > i) {
                    this.mHeightMeasuredTooSmall = r2;
                    this.mListDimensionBehaviors[r2] = dimensionBehaviour72;
                    setHeight(i);
                    i3 = 8;
                    z4 = true;
                    z7 = true;
                    z8 = i8 > i3 ? false : z4;
                    i7 = i8;
                    i4 = 0;
                }
            }
            z4 = z3;
            i3 = 8;
            if (i8 > i3) {
            }
            i7 = i8;
            i4 = 0;
        }
        boolean z9 = z7;
        this.mChildren = arrayList;
        if (z9) {
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr3 = this.mListDimensionBehaviors;
            dimensionBehaviourArr3[0] = dimensionBehaviour2;
            dimensionBehaviourArr3[1] = dimensionBehaviour;
        }
        resetSolverVariables(this.mSystem.getCache());
    }

    public final void measure(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.mPaddingLeft = i8;
        this.mPaddingTop = i9;
        this.mBasicMeasureSolver.solverMeasure(this, i, i2, i3, i4, i5);
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

    public final void setMeasurer(BasicMeasure.Measurer measurer) {
        this.mMeasurer = measurer;
        this.mDependencyGraph.setMeasurer(measurer);
    }

    public final void setOptimizationLevel(int i) {
        this.mOptimizationLevel = i;
        LinearSystem.USE_DEPENDENCY_ORDERING = optimizeFor(512);
    }

    public final void setPass(int i) {
        this.mPass = i;
    }

    public final void setRtl(boolean z) {
        this.mIsRtl = z;
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final void updateFromRuns(boolean z, boolean z2) {
        super.updateFromRuns(z, z2);
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            this.mChildren.get(i).updateFromRuns(z, z2);
        }
    }

    public final void updateHierarchy() {
        this.mBasicMeasureSolver.updateHierarchy(this);
    }

    public static void measure(ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer, BasicMeasure.Measure measure) {
        int i;
        int i2;
        if (measurer == null) {
            return;
        }
        if (constraintWidget.getVisibility() != 8 && !(constraintWidget instanceof Guideline) && !(constraintWidget instanceof Barrier)) {
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
            ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.FIXED;
            if (z && constraintWidget.hasDanglingDimension(0) && constraintWidget.mMatchConstraintDefaultWidth == 0 && !z3) {
                measure.horizontalBehavior = dimensionBehaviour3;
                if (z2 && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                    measure.horizontalBehavior = dimensionBehaviour4;
                }
                z = false;
            }
            if (z2 && constraintWidget.hasDanglingDimension(1) && constraintWidget.mMatchConstraintDefaultHeight == 0 && !z4) {
                measure.verticalBehavior = dimensionBehaviour3;
                if (z && constraintWidget.mMatchConstraintDefaultWidth == 0) {
                    measure.verticalBehavior = dimensionBehaviour4;
                }
                z2 = false;
            }
            if (constraintWidget.isResolvedHorizontally()) {
                measure.horizontalBehavior = dimensionBehaviour4;
                z = false;
            }
            if (constraintWidget.isResolvedVertically()) {
                measure.verticalBehavior = dimensionBehaviour4;
                z2 = false;
            }
            if (z3) {
                if (constraintWidget.mResolvedMatchConstraintDefault[0] == 4) {
                    measure.horizontalBehavior = dimensionBehaviour4;
                } else if (!z2) {
                    if (measure.verticalBehavior == dimensionBehaviour4) {
                        i2 = measure.verticalDimension;
                    } else {
                        measure.horizontalBehavior = dimensionBehaviour3;
                        measurer.measure(constraintWidget, measure);
                        i2 = measure.measuredHeight;
                    }
                    measure.horizontalBehavior = dimensionBehaviour4;
                    measure.horizontalDimension = (int) (constraintWidget.mDimensionRatio * i2);
                }
            }
            if (z4) {
                if (constraintWidget.mResolvedMatchConstraintDefault[1] == 4) {
                    measure.verticalBehavior = dimensionBehaviour4;
                } else if (!z) {
                    if (measure.horizontalBehavior == dimensionBehaviour4) {
                        i = measure.horizontalDimension;
                    } else {
                        measure.verticalBehavior = dimensionBehaviour3;
                        measurer.measure(constraintWidget, measure);
                        i = measure.measuredWidth;
                    }
                    measure.verticalBehavior = dimensionBehaviour4;
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
            constraintWidget.setHasBaseline(measure.measuredHasBaseline);
            constraintWidget.setBaselineDistance(measure.measuredBaseline);
            measure.measureStrategy = 0;
            return;
        }
        measure.measuredWidth = 0;
        measure.measuredHeight = 0;
    }
}
