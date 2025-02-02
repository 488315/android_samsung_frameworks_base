package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class DependencyGraph {
    private ConstraintWidgetContainer mContainer;
    ArrayList<RunGroup> mGroups;
    private BasicMeasure.Measure mMeasure;
    private BasicMeasure.Measurer mMeasurer;
    private boolean mNeedBuildGraph = true;
    private boolean mNeedRedoMeasures = true;
    private ArrayList<WidgetRun> mRuns = new ArrayList<>();
    private ConstraintWidgetContainer mWidgetcontainer;

    public DependencyGraph(ConstraintWidgetContainer constraintWidgetContainer) {
        new ArrayList();
        this.mMeasurer = null;
        this.mMeasure = new BasicMeasure.Measure();
        this.mGroups = new ArrayList<>();
        this.mWidgetcontainer = constraintWidgetContainer;
        this.mContainer = constraintWidgetContainer;
    }

    private void applyGroup(DependencyNode dependencyNode, int i, int i2, ArrayList arrayList, RunGroup runGroup) {
        WidgetRun widgetRun = dependencyNode.mRun;
        if (widgetRun.mRunGroup == null) {
            ConstraintWidgetContainer constraintWidgetContainer = this.mWidgetcontainer;
            if (widgetRun == constraintWidgetContainer.mHorizontalRun || widgetRun == constraintWidgetContainer.mVerticalRun) {
                return;
            }
            if (runGroup == null) {
                runGroup = new RunGroup(widgetRun);
                arrayList.add(runGroup);
            }
            widgetRun.mRunGroup = runGroup;
            runGroup.mRuns.add(widgetRun);
            Iterator it = ((ArrayList) widgetRun.start.mDependencies).iterator();
            while (it.hasNext()) {
                Dependency dependency = (Dependency) it.next();
                if (dependency instanceof DependencyNode) {
                    applyGroup((DependencyNode) dependency, i, 0, arrayList, runGroup);
                }
            }
            Iterator it2 = ((ArrayList) widgetRun.end.mDependencies).iterator();
            while (it2.hasNext()) {
                Dependency dependency2 = (Dependency) it2.next();
                if (dependency2 instanceof DependencyNode) {
                    applyGroup((DependencyNode) dependency2, i, 1, arrayList, runGroup);
                }
            }
            if (i == 1 && (widgetRun instanceof VerticalWidgetRun)) {
                Iterator it3 = ((ArrayList) ((VerticalWidgetRun) widgetRun).baseline.mDependencies).iterator();
                while (it3.hasNext()) {
                    Dependency dependency3 = (Dependency) it3.next();
                    if (dependency3 instanceof DependencyNode) {
                        applyGroup((DependencyNode) dependency3, i, 2, arrayList, runGroup);
                    }
                }
            }
            Iterator it4 = ((ArrayList) widgetRun.start.mTargets).iterator();
            while (it4.hasNext()) {
                applyGroup((DependencyNode) it4.next(), i, 0, arrayList, runGroup);
            }
            Iterator it5 = ((ArrayList) widgetRun.end.mTargets).iterator();
            while (it5.hasNext()) {
                applyGroup((DependencyNode) it5.next(), i, 1, arrayList, runGroup);
            }
            if (i == 1 && (widgetRun instanceof VerticalWidgetRun)) {
                Iterator it6 = ((ArrayList) ((VerticalWidgetRun) widgetRun).baseline.mTargets).iterator();
                while (it6.hasNext()) {
                    applyGroup((DependencyNode) it6.next(), i, 2, arrayList, runGroup);
                }
            }
        }
    }

    private void basicMeasureWidgets(ConstraintWidgetContainer constraintWidgetContainer) {
        int i;
        int i2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        Iterator<ConstraintWidget> it = constraintWidgetContainer.mChildren.iterator();
        while (it.hasNext()) {
            ConstraintWidget next = it.next();
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = next.mListDimensionBehaviors;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = dimensionBehaviourArr[1];
            if (next.getVisibility() == 8) {
                next.measured = true;
            } else {
                float f = next.mMatchConstraintPercentWidth;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (f < 1.0f && dimensionBehaviour2 == dimensionBehaviour4) {
                    next.mMatchConstraintDefaultWidth = 2;
                }
                float f2 = next.mMatchConstraintPercentHeight;
                if (f2 < 1.0f && dimensionBehaviour3 == dimensionBehaviour4) {
                    next.mMatchConstraintDefaultHeight = 2;
                }
                float f3 = next.mDimensionRatio;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.FIXED;
                if (f3 > 0.0f) {
                    if (dimensionBehaviour2 == dimensionBehaviour4 && (dimensionBehaviour3 == dimensionBehaviour5 || dimensionBehaviour3 == dimensionBehaviour6)) {
                        next.mMatchConstraintDefaultWidth = 3;
                    } else if (dimensionBehaviour3 == dimensionBehaviour4 && (dimensionBehaviour2 == dimensionBehaviour5 || dimensionBehaviour2 == dimensionBehaviour6)) {
                        next.mMatchConstraintDefaultHeight = 3;
                    } else if (dimensionBehaviour2 == dimensionBehaviour4 && dimensionBehaviour3 == dimensionBehaviour4) {
                        if (next.mMatchConstraintDefaultWidth == 0) {
                            next.mMatchConstraintDefaultWidth = 3;
                        }
                        if (next.mMatchConstraintDefaultHeight == 0) {
                            next.mMatchConstraintDefaultHeight = 3;
                        }
                    }
                }
                if (dimensionBehaviour2 == dimensionBehaviour4 && next.mMatchConstraintDefaultWidth == 1 && (next.mLeft.mTarget == null || next.mRight.mTarget == null)) {
                    dimensionBehaviour2 = dimensionBehaviour5;
                }
                ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = (dimensionBehaviour3 == dimensionBehaviour4 && next.mMatchConstraintDefaultHeight == 1 && (next.mTop.mTarget == null || next.mBottom.mTarget == null)) ? dimensionBehaviour5 : dimensionBehaviour3;
                HorizontalWidgetRun horizontalWidgetRun = next.mHorizontalRun;
                horizontalWidgetRun.mDimensionBehavior = dimensionBehaviour2;
                int i3 = next.mMatchConstraintDefaultWidth;
                horizontalWidgetRun.matchConstraintsType = i3;
                VerticalWidgetRun verticalWidgetRun = next.mVerticalRun;
                verticalWidgetRun.mDimensionBehavior = dimensionBehaviour7;
                int i4 = next.mMatchConstraintDefaultHeight;
                verticalWidgetRun.matchConstraintsType = i4;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour8 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
                if ((dimensionBehaviour2 == dimensionBehaviour8 || dimensionBehaviour2 == dimensionBehaviour6 || dimensionBehaviour2 == dimensionBehaviour5) && (dimensionBehaviour7 == dimensionBehaviour8 || dimensionBehaviour7 == dimensionBehaviour6 || dimensionBehaviour7 == dimensionBehaviour5)) {
                    int width = next.getWidth();
                    if (dimensionBehaviour2 == dimensionBehaviour8) {
                        i = (constraintWidgetContainer.getWidth() - next.mLeft.mMargin) - next.mRight.mMargin;
                        dimensionBehaviour2 = dimensionBehaviour6;
                    } else {
                        i = width;
                    }
                    int height = next.getHeight();
                    if (dimensionBehaviour7 == dimensionBehaviour8) {
                        i2 = (constraintWidgetContainer.getHeight() - next.mTop.mMargin) - next.mBottom.mMargin;
                        dimensionBehaviour = dimensionBehaviour6;
                    } else {
                        i2 = height;
                        dimensionBehaviour = dimensionBehaviour7;
                    }
                    measure(next, dimensionBehaviour2, i, dimensionBehaviour, i2);
                    next.mHorizontalRun.mDimension.resolve(next.getWidth());
                    next.mVerticalRun.mDimension.resolve(next.getHeight());
                    next.measured = true;
                } else {
                    if (dimensionBehaviour2 == dimensionBehaviour4 && (dimensionBehaviour7 == dimensionBehaviour5 || dimensionBehaviour7 == dimensionBehaviour6)) {
                        if (i3 == 3) {
                            if (dimensionBehaviour7 == dimensionBehaviour5) {
                                measure(next, dimensionBehaviour5, 0, dimensionBehaviour5, 0);
                            }
                            int height2 = next.getHeight();
                            measure(next, dimensionBehaviour6, (int) ((height2 * next.mDimensionRatio) + 0.5f), dimensionBehaviour6, height2);
                            next.mHorizontalRun.mDimension.resolve(next.getWidth());
                            next.mVerticalRun.mDimension.resolve(next.getHeight());
                            next.measured = true;
                        } else if (i3 == 1) {
                            measure(next, dimensionBehaviour5, 0, dimensionBehaviour7, 0);
                            next.mHorizontalRun.mDimension.wrapValue = next.getWidth();
                        } else if (i3 == 2) {
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour9 = constraintWidgetContainer.mListDimensionBehaviors[0];
                            if (dimensionBehaviour9 == dimensionBehaviour6 || dimensionBehaviour9 == dimensionBehaviour8) {
                                measure(next, dimensionBehaviour6, (int) ((f * constraintWidgetContainer.getWidth()) + 0.5f), dimensionBehaviour7, next.getHeight());
                                next.mHorizontalRun.mDimension.resolve(next.getWidth());
                                next.mVerticalRun.mDimension.resolve(next.getHeight());
                                next.measured = true;
                            }
                        } else {
                            ConstraintAnchor[] constraintAnchorArr = next.mListAnchors;
                            if (constraintAnchorArr[0].mTarget == null || constraintAnchorArr[1].mTarget == null) {
                                measure(next, dimensionBehaviour5, 0, dimensionBehaviour7, 0);
                                next.mHorizontalRun.mDimension.resolve(next.getWidth());
                                next.mVerticalRun.mDimension.resolve(next.getHeight());
                                next.measured = true;
                            }
                        }
                    }
                    if (dimensionBehaviour7 == dimensionBehaviour4 && (dimensionBehaviour2 == dimensionBehaviour5 || dimensionBehaviour2 == dimensionBehaviour6)) {
                        if (i4 == 3) {
                            if (dimensionBehaviour2 == dimensionBehaviour5) {
                                measure(next, dimensionBehaviour5, 0, dimensionBehaviour5, 0);
                            }
                            int width2 = next.getWidth();
                            float f4 = next.mDimensionRatio;
                            if (next.getDimensionRatioSide() == -1) {
                                f4 = 1.0f / f4;
                            }
                            measure(next, dimensionBehaviour6, width2, dimensionBehaviour6, (int) ((width2 * f4) + 0.5f));
                            next.mHorizontalRun.mDimension.resolve(next.getWidth());
                            next.mVerticalRun.mDimension.resolve(next.getHeight());
                            next.measured = true;
                        } else if (i4 == 1) {
                            measure(next, dimensionBehaviour2, 0, dimensionBehaviour5, 0);
                            next.mVerticalRun.mDimension.wrapValue = next.getHeight();
                        } else if (i4 == 2) {
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour10 = constraintWidgetContainer.mListDimensionBehaviors[1];
                            if (dimensionBehaviour10 == dimensionBehaviour6 || dimensionBehaviour10 == dimensionBehaviour8) {
                                measure(next, dimensionBehaviour2, next.getWidth(), dimensionBehaviour6, (int) ((f2 * constraintWidgetContainer.getHeight()) + 0.5f));
                                next.mHorizontalRun.mDimension.resolve(next.getWidth());
                                next.mVerticalRun.mDimension.resolve(next.getHeight());
                                next.measured = true;
                            }
                        } else {
                            ConstraintAnchor[] constraintAnchorArr2 = next.mListAnchors;
                            if (constraintAnchorArr2[2].mTarget == null || constraintAnchorArr2[3].mTarget == null) {
                                measure(next, dimensionBehaviour5, 0, dimensionBehaviour7, 0);
                                next.mHorizontalRun.mDimension.resolve(next.getWidth());
                                next.mVerticalRun.mDimension.resolve(next.getHeight());
                                next.measured = true;
                            }
                        }
                    }
                    if (dimensionBehaviour2 == dimensionBehaviour4 && dimensionBehaviour7 == dimensionBehaviour4) {
                        if (i3 == 1 || i4 == 1) {
                            measure(next, dimensionBehaviour5, 0, dimensionBehaviour5, 0);
                            next.mHorizontalRun.mDimension.wrapValue = next.getWidth();
                            next.mVerticalRun.mDimension.wrapValue = next.getHeight();
                        } else if (i4 == 2 && i3 == 2) {
                            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = constraintWidgetContainer.mListDimensionBehaviors;
                            if (dimensionBehaviourArr2[0] == dimensionBehaviour6 && dimensionBehaviourArr2[1] == dimensionBehaviour6) {
                                measure(next, dimensionBehaviour6, (int) ((f * constraintWidgetContainer.getWidth()) + 0.5f), dimensionBehaviour6, (int) ((f2 * constraintWidgetContainer.getHeight()) + 0.5f));
                                next.mHorizontalRun.mDimension.resolve(next.getWidth());
                                next.mVerticalRun.mDimension.resolve(next.getHeight());
                                next.measured = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private int computeWrap(ConstraintWidgetContainer constraintWidgetContainer, int i) {
        int size = this.mGroups.size();
        long j = 0;
        for (int i2 = 0; i2 < size; i2++) {
            j = Math.max(j, this.mGroups.get(i2).computeWrapSize(constraintWidgetContainer, i));
        }
        return (int) j;
    }

    private void findGroup(WidgetRun widgetRun, int i, ArrayList<RunGroup> arrayList) {
        Iterator it = ((ArrayList) widgetRun.start.mDependencies).iterator();
        while (it.hasNext()) {
            Dependency dependency = (Dependency) it.next();
            if (dependency instanceof DependencyNode) {
                applyGroup((DependencyNode) dependency, i, 0, arrayList, null);
            } else if (dependency instanceof WidgetRun) {
                applyGroup(((WidgetRun) dependency).start, i, 0, arrayList, null);
            }
        }
        Iterator it2 = ((ArrayList) widgetRun.end.mDependencies).iterator();
        while (it2.hasNext()) {
            Dependency dependency2 = (Dependency) it2.next();
            if (dependency2 instanceof DependencyNode) {
                applyGroup((DependencyNode) dependency2, i, 1, arrayList, null);
            } else if (dependency2 instanceof WidgetRun) {
                applyGroup(((WidgetRun) dependency2).end, i, 1, arrayList, null);
            }
        }
        if (i == 1) {
            Iterator it3 = ((ArrayList) ((VerticalWidgetRun) widgetRun).baseline.mDependencies).iterator();
            while (it3.hasNext()) {
                Dependency dependency3 = (Dependency) it3.next();
                if (dependency3 instanceof DependencyNode) {
                    applyGroup((DependencyNode) dependency3, i, 2, arrayList, null);
                }
            }
        }
    }

    private void measure(ConstraintWidget constraintWidget, ConstraintWidget.DimensionBehaviour dimensionBehaviour, int i, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, int i2) {
        BasicMeasure.Measure measure = this.mMeasure;
        measure.horizontalBehavior = dimensionBehaviour;
        measure.verticalBehavior = dimensionBehaviour2;
        measure.horizontalDimension = i;
        measure.verticalDimension = i2;
        this.mMeasurer.measure(constraintWidget, measure);
        constraintWidget.setWidth(this.mMeasure.measuredWidth);
        constraintWidget.setHeight(this.mMeasure.measuredHeight);
        constraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        constraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
    }

    public final void buildGraph() {
        ArrayList<WidgetRun> arrayList = this.mRuns;
        arrayList.clear();
        this.mContainer.mHorizontalRun.clear();
        this.mContainer.mVerticalRun.clear();
        arrayList.add(this.mContainer.mHorizontalRun);
        arrayList.add(this.mContainer.mVerticalRun);
        Iterator<ConstraintWidget> it = this.mContainer.mChildren.iterator();
        HashSet hashSet = null;
        while (it.hasNext()) {
            ConstraintWidget next = it.next();
            if (next instanceof Guideline) {
                arrayList.add(new GuidelineReference(next));
            } else {
                if (next.isInHorizontalChain()) {
                    if (next.horizontalChainRun == null) {
                        next.horizontalChainRun = new ChainRun(0, next);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(next.horizontalChainRun);
                } else {
                    arrayList.add(next.mHorizontalRun);
                }
                if (next.isInVerticalChain()) {
                    if (next.verticalChainRun == null) {
                        next.verticalChainRun = new ChainRun(1, next);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(next.verticalChainRun);
                } else {
                    arrayList.add(next.mVerticalRun);
                }
                if (next instanceof HelperWidget) {
                    arrayList.add(new HelperReferences(next));
                }
            }
        }
        if (hashSet != null) {
            arrayList.addAll(hashSet);
        }
        Iterator<WidgetRun> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            it2.next().clear();
        }
        Iterator<WidgetRun> it3 = arrayList.iterator();
        while (it3.hasNext()) {
            WidgetRun next2 = it3.next();
            if (next2.mWidget != this.mContainer) {
                next2.apply();
            }
        }
        this.mGroups.clear();
        findGroup(this.mWidgetcontainer.mHorizontalRun, 0, this.mGroups);
        findGroup(this.mWidgetcontainer.mVerticalRun, 1, this.mGroups);
        this.mNeedBuildGraph = false;
    }

    public final boolean directMeasure(boolean z) {
        boolean z2;
        boolean z3 = true;
        boolean z4 = z & true;
        if (this.mNeedBuildGraph || this.mNeedRedoMeasures) {
            Iterator<ConstraintWidget> it = this.mWidgetcontainer.mChildren.iterator();
            while (it.hasNext()) {
                ConstraintWidget next = it.next();
                next.ensureWidgetRuns();
                next.measured = false;
                next.mHorizontalRun.reset();
                next.mVerticalRun.reset();
            }
            this.mWidgetcontainer.ensureWidgetRuns();
            ConstraintWidgetContainer constraintWidgetContainer = this.mWidgetcontainer;
            constraintWidgetContainer.measured = false;
            constraintWidgetContainer.mHorizontalRun.reset();
            this.mWidgetcontainer.mVerticalRun.reset();
            this.mNeedRedoMeasures = false;
        }
        basicMeasureWidgets(this.mContainer);
        this.mWidgetcontainer.setX(0);
        this.mWidgetcontainer.setY(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.mWidgetcontainer.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.mWidgetcontainer.getDimensionBehaviour(1);
        if (this.mNeedBuildGraph) {
            buildGraph();
        }
        int x = this.mWidgetcontainer.getX();
        int y = this.mWidgetcontainer.getY();
        this.mWidgetcontainer.mHorizontalRun.start.resolve(x);
        this.mWidgetcontainer.mVerticalRun.start.resolve(y);
        measureWidgets();
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.FIXED;
        if (dimensionBehaviour == dimensionBehaviour3 || dimensionBehaviour2 == dimensionBehaviour3) {
            if (z4) {
                Iterator<WidgetRun> it2 = this.mRuns.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    if (!it2.next().supportsWrapComputation()) {
                        z4 = false;
                        break;
                    }
                }
            }
            if (z4 && dimensionBehaviour == dimensionBehaviour3) {
                this.mWidgetcontainer.setHorizontalDimensionBehaviour(dimensionBehaviour4);
                ConstraintWidgetContainer constraintWidgetContainer2 = this.mWidgetcontainer;
                constraintWidgetContainer2.setWidth(computeWrap(constraintWidgetContainer2, 0));
                ConstraintWidgetContainer constraintWidgetContainer3 = this.mWidgetcontainer;
                constraintWidgetContainer3.mHorizontalRun.mDimension.resolve(constraintWidgetContainer3.getWidth());
            }
            if (z4 && dimensionBehaviour2 == dimensionBehaviour3) {
                this.mWidgetcontainer.setVerticalDimensionBehaviour(dimensionBehaviour4);
                ConstraintWidgetContainer constraintWidgetContainer4 = this.mWidgetcontainer;
                constraintWidgetContainer4.setHeight(computeWrap(constraintWidgetContainer4, 1));
                ConstraintWidgetContainer constraintWidgetContainer5 = this.mWidgetcontainer;
                constraintWidgetContainer5.mVerticalRun.mDimension.resolve(constraintWidgetContainer5.getHeight());
            }
        }
        ConstraintWidgetContainer constraintWidgetContainer6 = this.mWidgetcontainer;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = constraintWidgetContainer6.mListDimensionBehaviors[0];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
        if (dimensionBehaviour5 == dimensionBehaviour4 || dimensionBehaviour5 == dimensionBehaviour6) {
            int width = constraintWidgetContainer6.getWidth() + x;
            this.mWidgetcontainer.mHorizontalRun.end.resolve(width);
            this.mWidgetcontainer.mHorizontalRun.mDimension.resolve(width - x);
            measureWidgets();
            ConstraintWidgetContainer constraintWidgetContainer7 = this.mWidgetcontainer;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = constraintWidgetContainer7.mListDimensionBehaviors[1];
            if (dimensionBehaviour7 == dimensionBehaviour4 || dimensionBehaviour7 == dimensionBehaviour6) {
                int height = constraintWidgetContainer7.getHeight() + y;
                this.mWidgetcontainer.mVerticalRun.end.resolve(height);
                this.mWidgetcontainer.mVerticalRun.mDimension.resolve(height - y);
            }
            measureWidgets();
            z2 = true;
        } else {
            z2 = false;
        }
        Iterator<WidgetRun> it3 = this.mRuns.iterator();
        while (it3.hasNext()) {
            WidgetRun next2 = it3.next();
            if (next2.mWidget != this.mWidgetcontainer || next2.mResolved) {
                next2.applyToWidget();
            }
        }
        Iterator<WidgetRun> it4 = this.mRuns.iterator();
        while (it4.hasNext()) {
            WidgetRun next3 = it4.next();
            if (z2 || next3.mWidget != this.mWidgetcontainer) {
                if (!next3.start.resolved || ((!next3.end.resolved && !(next3 instanceof GuidelineReference)) || (!next3.mDimension.resolved && !(next3 instanceof ChainRun) && !(next3 instanceof GuidelineReference)))) {
                    z3 = false;
                    break;
                }
            }
        }
        this.mWidgetcontainer.setHorizontalDimensionBehaviour(dimensionBehaviour);
        this.mWidgetcontainer.setVerticalDimensionBehaviour(dimensionBehaviour2);
        return z3;
    }

    public final void directMeasureSetup() {
        if (this.mNeedBuildGraph) {
            Iterator<ConstraintWidget> it = this.mWidgetcontainer.mChildren.iterator();
            while (it.hasNext()) {
                ConstraintWidget next = it.next();
                next.ensureWidgetRuns();
                next.measured = false;
                HorizontalWidgetRun horizontalWidgetRun = next.mHorizontalRun;
                horizontalWidgetRun.mDimension.resolved = false;
                horizontalWidgetRun.mResolved = false;
                horizontalWidgetRun.reset();
                VerticalWidgetRun verticalWidgetRun = next.mVerticalRun;
                verticalWidgetRun.mDimension.resolved = false;
                verticalWidgetRun.mResolved = false;
                verticalWidgetRun.reset();
            }
            this.mWidgetcontainer.ensureWidgetRuns();
            ConstraintWidgetContainer constraintWidgetContainer = this.mWidgetcontainer;
            constraintWidgetContainer.measured = false;
            HorizontalWidgetRun horizontalWidgetRun2 = constraintWidgetContainer.mHorizontalRun;
            horizontalWidgetRun2.mDimension.resolved = false;
            horizontalWidgetRun2.mResolved = false;
            horizontalWidgetRun2.reset();
            VerticalWidgetRun verticalWidgetRun2 = this.mWidgetcontainer.mVerticalRun;
            verticalWidgetRun2.mDimension.resolved = false;
            verticalWidgetRun2.mResolved = false;
            verticalWidgetRun2.reset();
            buildGraph();
        }
        basicMeasureWidgets(this.mContainer);
        this.mWidgetcontainer.setX(0);
        this.mWidgetcontainer.setY(0);
        this.mWidgetcontainer.mHorizontalRun.start.resolve(0);
        this.mWidgetcontainer.mVerticalRun.start.resolve(0);
    }

    public final boolean directMeasureWithOrientation(int i, boolean z) {
        boolean z2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        boolean z3 = true;
        boolean z4 = z & true;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.mWidgetcontainer.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = this.mWidgetcontainer.getDimensionBehaviour(1);
        int x = this.mWidgetcontainer.getX();
        int y = this.mWidgetcontainer.getY();
        ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.FIXED;
        if (z4 && (dimensionBehaviour2 == (dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || dimensionBehaviour3 == dimensionBehaviour)) {
            Iterator<WidgetRun> it = this.mRuns.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WidgetRun next = it.next();
                if (next.orientation == i && !next.supportsWrapComputation()) {
                    z4 = false;
                    break;
                }
            }
            if (i == 0) {
                if (z4 && dimensionBehaviour2 == dimensionBehaviour) {
                    this.mWidgetcontainer.setHorizontalDimensionBehaviour(dimensionBehaviour4);
                    ConstraintWidgetContainer constraintWidgetContainer = this.mWidgetcontainer;
                    constraintWidgetContainer.setWidth(computeWrap(constraintWidgetContainer, 0));
                    ConstraintWidgetContainer constraintWidgetContainer2 = this.mWidgetcontainer;
                    constraintWidgetContainer2.mHorizontalRun.mDimension.resolve(constraintWidgetContainer2.getWidth());
                }
            } else if (z4 && dimensionBehaviour3 == dimensionBehaviour) {
                this.mWidgetcontainer.setVerticalDimensionBehaviour(dimensionBehaviour4);
                ConstraintWidgetContainer constraintWidgetContainer3 = this.mWidgetcontainer;
                constraintWidgetContainer3.setHeight(computeWrap(constraintWidgetContainer3, 1));
                ConstraintWidgetContainer constraintWidgetContainer4 = this.mWidgetcontainer;
                constraintWidgetContainer4.mVerticalRun.mDimension.resolve(constraintWidgetContainer4.getHeight());
            }
        }
        ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
        if (i == 0) {
            ConstraintWidgetContainer constraintWidgetContainer5 = this.mWidgetcontainer;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = constraintWidgetContainer5.mListDimensionBehaviors[0];
            if (dimensionBehaviour6 == dimensionBehaviour4 || dimensionBehaviour6 == dimensionBehaviour5) {
                int width = constraintWidgetContainer5.getWidth() + x;
                this.mWidgetcontainer.mHorizontalRun.end.resolve(width);
                this.mWidgetcontainer.mHorizontalRun.mDimension.resolve(width - x);
                z2 = true;
            }
            z2 = false;
        } else {
            ConstraintWidgetContainer constraintWidgetContainer6 = this.mWidgetcontainer;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = constraintWidgetContainer6.mListDimensionBehaviors[1];
            if (dimensionBehaviour7 == dimensionBehaviour4 || dimensionBehaviour7 == dimensionBehaviour5) {
                int height = constraintWidgetContainer6.getHeight() + y;
                this.mWidgetcontainer.mVerticalRun.end.resolve(height);
                this.mWidgetcontainer.mVerticalRun.mDimension.resolve(height - y);
                z2 = true;
            }
            z2 = false;
        }
        measureWidgets();
        Iterator<WidgetRun> it2 = this.mRuns.iterator();
        while (it2.hasNext()) {
            WidgetRun next2 = it2.next();
            if (next2.orientation == i && (next2.mWidget != this.mWidgetcontainer || next2.mResolved)) {
                next2.applyToWidget();
            }
        }
        Iterator<WidgetRun> it3 = this.mRuns.iterator();
        while (it3.hasNext()) {
            WidgetRun next3 = it3.next();
            if (next3.orientation == i && (z2 || next3.mWidget != this.mWidgetcontainer)) {
                if (!next3.start.resolved || !next3.end.resolved || (!(next3 instanceof ChainRun) && !next3.mDimension.resolved)) {
                    z3 = false;
                    break;
                }
            }
        }
        this.mWidgetcontainer.setHorizontalDimensionBehaviour(dimensionBehaviour2);
        this.mWidgetcontainer.setVerticalDimensionBehaviour(dimensionBehaviour3);
        return z3;
    }

    public final void invalidateGraph() {
        this.mNeedBuildGraph = true;
    }

    public final void invalidateMeasures() {
        this.mNeedRedoMeasures = true;
    }

    public final void measureWidgets() {
        BaselineDimensionDependency baselineDimensionDependency;
        Iterator<ConstraintWidget> it = this.mWidgetcontainer.mChildren.iterator();
        while (it.hasNext()) {
            ConstraintWidget next = it.next();
            if (!next.measured) {
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = next.mListDimensionBehaviors;
                boolean z = false;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[1];
                int i = next.mMatchConstraintDefaultWidth;
                int i2 = next.mMatchConstraintDefaultHeight;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                boolean z2 = dimensionBehaviour == dimensionBehaviour3 || (dimensionBehaviour == dimensionBehaviour4 && i == 1);
                if (dimensionBehaviour2 == dimensionBehaviour3 || (dimensionBehaviour2 == dimensionBehaviour4 && i2 == 1)) {
                    z = true;
                }
                DimensionDependency dimensionDependency = next.mHorizontalRun.mDimension;
                boolean z3 = dimensionDependency.resolved;
                DimensionDependency dimensionDependency2 = next.mVerticalRun.mDimension;
                boolean z4 = dimensionDependency2.resolved;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.FIXED;
                if (z3 && z4) {
                    measure(next, dimensionBehaviour5, dimensionDependency.value, dimensionBehaviour5, dimensionDependency2.value);
                    next.measured = true;
                } else if (z3 && z) {
                    measure(next, dimensionBehaviour5, dimensionDependency.value, dimensionBehaviour3, dimensionDependency2.value);
                    if (dimensionBehaviour2 == dimensionBehaviour4) {
                        next.mVerticalRun.mDimension.wrapValue = next.getHeight();
                    } else {
                        next.mVerticalRun.mDimension.resolve(next.getHeight());
                        next.measured = true;
                    }
                } else if (z4 && z2) {
                    measure(next, dimensionBehaviour3, dimensionDependency.value, dimensionBehaviour5, dimensionDependency2.value);
                    if (dimensionBehaviour == dimensionBehaviour4) {
                        next.mHorizontalRun.mDimension.wrapValue = next.getWidth();
                    } else {
                        next.mHorizontalRun.mDimension.resolve(next.getWidth());
                        next.measured = true;
                    }
                }
                if (next.measured && (baselineDimensionDependency = next.mVerticalRun.mBaselineDimension) != null) {
                    baselineDimensionDependency.resolve(next.getBaselineDistance());
                }
            }
        }
    }

    public final void setMeasurer(BasicMeasure.Measurer measurer) {
        this.mMeasurer = measurer;
    }
}
