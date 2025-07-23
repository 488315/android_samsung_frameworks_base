package androidx.constraintlayout.core.widgets.analyzer;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.HashSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DependencyGraph {
    public final ConstraintWidgetContainer mContainer;
    public final ArrayList mGroups;
    public final BasicMeasure.Measure mMeasure;
    public BasicMeasure.Measurer mMeasurer;
    public boolean mNeedBuildGraph = true;
    public boolean mNeedRedoMeasures = true;
    public final ArrayList mRuns = new ArrayList();
    public final ConstraintWidgetContainer mWidgetcontainer;

    public DependencyGraph(ConstraintWidgetContainer constraintWidgetContainer) {
        new ArrayList();
        this.mMeasurer = null;
        this.mMeasure = new BasicMeasure.Measure();
        this.mGroups = new ArrayList();
        this.mWidgetcontainer = constraintWidgetContainer;
        this.mContainer = constraintWidgetContainer;
    }

    public final void applyGroup(DependencyNode dependencyNode, int i, int i2, ArrayList arrayList, RunGroup runGroup) {
        DependencyGraph dependencyGraph;
        int i3;
        ArrayList arrayList2;
        WidgetRun widgetRun = dependencyNode.mRun;
        if (widgetRun.mRunGroup == null) {
            ConstraintWidgetContainer constraintWidgetContainer = this.mWidgetcontainer;
            if (widgetRun == constraintWidgetContainer.mHorizontalRun || widgetRun == constraintWidgetContainer.mVerticalRun) {
                return;
            }
            if (runGroup == null) {
                runGroup = new RunGroup(widgetRun, i2);
                arrayList.add(runGroup);
            }
            RunGroup runGroup2 = runGroup;
            widgetRun.mRunGroup = runGroup2;
            runGroup2.mRuns.add(widgetRun);
            DependencyNode dependencyNode2 = widgetRun.start;
            ArrayList arrayList3 = (ArrayList) dependencyNode2.mDependencies;
            int size = arrayList3.size();
            int i4 = 0;
            int i5 = 0;
            while (i5 < size) {
                int i6 = i5 + 1;
                Dependency dependency = (Dependency) arrayList3.get(i5);
                if (dependency instanceof DependencyNode) {
                    dependencyGraph = this;
                    i3 = i;
                    arrayList2 = arrayList;
                    dependencyGraph.applyGroup((DependencyNode) dependency, i3, 0, arrayList2, runGroup2);
                } else {
                    dependencyGraph = this;
                    i3 = i;
                    arrayList2 = arrayList;
                }
                this = dependencyGraph;
                i = i3;
                arrayList = arrayList2;
                i5 = i6;
            }
            DependencyGraph dependencyGraph2 = this;
            int i7 = i;
            ArrayList arrayList4 = arrayList;
            DependencyNode dependencyNode3 = widgetRun.end;
            ArrayList arrayList5 = (ArrayList) dependencyNode3.mDependencies;
            int size2 = arrayList5.size();
            int i8 = 0;
            while (i8 < size2) {
                Object obj = arrayList5.get(i8);
                i8++;
                Dependency dependency2 = (Dependency) obj;
                if (dependency2 instanceof DependencyNode) {
                    dependencyGraph2.applyGroup((DependencyNode) dependency2, i7, 1, arrayList4, runGroup2);
                }
            }
            if (i7 == 1 && (widgetRun instanceof VerticalWidgetRun)) {
                ArrayList arrayList6 = (ArrayList) ((VerticalWidgetRun) widgetRun).baseline.mDependencies;
                int size3 = arrayList6.size();
                int i9 = 0;
                while (i9 < size3) {
                    int i10 = i9 + 1;
                    Dependency dependency3 = (Dependency) arrayList6.get(i9);
                    if (dependency3 instanceof DependencyNode) {
                        dependencyGraph2.applyGroup((DependencyNode) dependency3, i7, 2, arrayList4, runGroup2);
                    }
                    i9 = i10;
                }
            }
            ArrayList arrayList7 = (ArrayList) dependencyNode2.mTargets;
            int size4 = arrayList7.size();
            int i11 = 0;
            while (i11 < size4) {
                Object obj2 = arrayList7.get(i11);
                i11++;
                dependencyGraph2.applyGroup((DependencyNode) obj2, i7, 0, arrayList4, runGroup2);
            }
            ArrayList arrayList8 = (ArrayList) dependencyNode3.mTargets;
            int size5 = arrayList8.size();
            int i12 = 0;
            while (i12 < size5) {
                Object obj3 = arrayList8.get(i12);
                i12++;
                dependencyGraph2.applyGroup((DependencyNode) obj3, i7, 1, arrayList4, runGroup2);
            }
            if (i7 == 1 && (widgetRun instanceof VerticalWidgetRun)) {
                ArrayList arrayList9 = (ArrayList) ((VerticalWidgetRun) widgetRun).baseline.mTargets;
                int size6 = arrayList9.size();
                while (i4 < size6) {
                    Object obj4 = arrayList9.get(i4);
                    i4++;
                    dependencyGraph2.applyGroup((DependencyNode) obj4, i7, 2, arrayList4, runGroup2);
                }
            }
        }
    }

    public final void basicMeasureWidgets(ConstraintWidgetContainer constraintWidgetContainer) {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        float f;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
        float f2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour4;
        ArrayList arrayList = constraintWidgetContainer.mChildren;
        int size = arrayList.size();
        char c = 0;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ConstraintWidget constraintWidget = (ConstraintWidget) obj;
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = dimensionBehaviourArr[c];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = dimensionBehaviourArr[1];
            if (constraintWidget.mVisibility == 8) {
                constraintWidget.measured = true;
            } else {
                float f3 = constraintWidget.mMatchConstraintPercentWidth;
                if (f3 < 1.0f && dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    constraintWidget.mMatchConstraintDefaultWidth = 2;
                }
                float f4 = constraintWidget.mMatchConstraintPercentHeight;
                if (f4 < 1.0f && dimensionBehaviour6 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    constraintWidget.mMatchConstraintDefaultHeight = 2;
                }
                if (constraintWidget.mDimensionRatio > 0.0f) {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    if (dimensionBehaviour5 == dimensionBehaviour7 && (dimensionBehaviour6 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour6 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                        constraintWidget.mMatchConstraintDefaultWidth = 3;
                    } else if (dimensionBehaviour6 == dimensionBehaviour7 && (dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                        constraintWidget.mMatchConstraintDefaultHeight = 3;
                    } else if (dimensionBehaviour5 == dimensionBehaviour7 && dimensionBehaviour6 == dimensionBehaviour7) {
                        if (constraintWidget.mMatchConstraintDefaultWidth == 0) {
                            constraintWidget.mMatchConstraintDefaultWidth = 3;
                        }
                        if (constraintWidget.mMatchConstraintDefaultHeight == 0) {
                            constraintWidget.mMatchConstraintDefaultHeight = 3;
                        }
                    }
                }
                ConstraintWidget.DimensionBehaviour dimensionBehaviour8 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                ConstraintAnchor constraintAnchor = constraintWidget.mRight;
                char c2 = c;
                ConstraintAnchor constraintAnchor2 = constraintWidget.mLeft;
                if (dimensionBehaviour5 == dimensionBehaviour8 && constraintWidget.mMatchConstraintDefaultWidth == 1 && (constraintAnchor2.mTarget == null || constraintAnchor.mTarget == null)) {
                    dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                }
                ConstraintAnchor constraintAnchor3 = constraintWidget.mBottom;
                ConstraintAnchor constraintAnchor4 = constraintWidget.mTop;
                if (dimensionBehaviour6 == dimensionBehaviour8 && constraintWidget.mMatchConstraintDefaultHeight == 1 && (constraintAnchor4.mTarget == null || constraintAnchor3.mTarget == null)) {
                    dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                }
                HorizontalWidgetRun horizontalWidgetRun = constraintWidget.mHorizontalRun;
                horizontalWidgetRun.mDimensionBehavior = dimensionBehaviour5;
                int i2 = constraintWidget.mMatchConstraintDefaultWidth;
                horizontalWidgetRun.matchConstraintsType = i2;
                VerticalWidgetRun verticalWidgetRun = constraintWidget.mVerticalRun;
                verticalWidgetRun.mDimensionBehavior = dimensionBehaviour6;
                ArrayList arrayList2 = arrayList;
                int i3 = constraintWidget.mMatchConstraintDefaultHeight;
                verticalWidgetRun.matchConstraintsType = i3;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour9 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
                int i4 = size;
                if ((dimensionBehaviour5 == dimensionBehaviour9 || dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (dimensionBehaviour6 == dimensionBehaviour9 || dimensionBehaviour6 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour6 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour10 = dimensionBehaviour5;
                    int width = constraintWidget.getWidth();
                    if (dimensionBehaviour10 == dimensionBehaviour9) {
                        width = (constraintWidgetContainer.getWidth() - constraintAnchor2.mMargin) - constraintAnchor.mMargin;
                        dimensionBehaviour10 = ConstraintWidget.DimensionBehaviour.FIXED;
                    }
                    int i5 = width;
                    int height = constraintWidget.getHeight();
                    if (dimensionBehaviour6 == dimensionBehaviour9) {
                        height = (constraintWidgetContainer.getHeight() - constraintAnchor4.mMargin) - constraintAnchor3.mMargin;
                        dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.FIXED;
                    }
                    measure(constraintWidget, dimensionBehaviour10, i5, dimensionBehaviour6, height);
                    constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                    constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                    constraintWidget.measured = true;
                } else {
                    ConstraintAnchor[] constraintAnchorArr = constraintWidget.mListAnchors;
                    if (dimensionBehaviour5 == dimensionBehaviour8) {
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour11 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                        if (dimensionBehaviour6 != dimensionBehaviour11 && dimensionBehaviour6 != ConstraintWidget.DimensionBehaviour.FIXED) {
                            f = f3;
                            dimensionBehaviour = dimensionBehaviour6;
                        } else if (i2 == 3) {
                            if (dimensionBehaviour6 == dimensionBehaviour11) {
                                measure(constraintWidget, dimensionBehaviour11, 0, dimensionBehaviour11, 0);
                            }
                            int height2 = constraintWidget.getHeight();
                            int i6 = (int) ((height2 * constraintWidget.mDimensionRatio) + 0.5f);
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour12 = ConstraintWidget.DimensionBehaviour.FIXED;
                            measure(constraintWidget, dimensionBehaviour12, i6, dimensionBehaviour12, height2);
                            constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                            constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                            constraintWidget.measured = true;
                        } else if (i2 == 1) {
                            measure(constraintWidget, dimensionBehaviour11, 0, dimensionBehaviour6, 0);
                            constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                        } else {
                            dimensionBehaviour = dimensionBehaviour6;
                            if (i2 == 2) {
                                ConstraintWidget.DimensionBehaviour dimensionBehaviour13 = constraintWidgetContainer.mListDimensionBehaviors[c2];
                                f = f3;
                                ConstraintWidget.DimensionBehaviour dimensionBehaviour14 = ConstraintWidget.DimensionBehaviour.FIXED;
                                if (dimensionBehaviour13 == dimensionBehaviour14 || dimensionBehaviour13 == dimensionBehaviour9) {
                                    measure(constraintWidget, dimensionBehaviour14, (int) ((constraintWidgetContainer.getWidth() * f) + 0.5f), dimensionBehaviour, constraintWidget.getHeight());
                                    constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                                    constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                                    constraintWidget.measured = true;
                                }
                            } else {
                                f = f3;
                                if (constraintAnchorArr[c2].mTarget == null || constraintAnchorArr[1].mTarget == null) {
                                    measure(constraintWidget, dimensionBehaviour11, 0, dimensionBehaviour, 0);
                                    constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                                    constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                                    constraintWidget.measured = true;
                                }
                            }
                        }
                    } else {
                        dimensionBehaviour = dimensionBehaviour6;
                        f = f3;
                    }
                    if (dimensionBehaviour != dimensionBehaviour8 || (dimensionBehaviour5 != (dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && dimensionBehaviour5 != ConstraintWidget.DimensionBehaviour.FIXED)) {
                        dimensionBehaviour2 = dimensionBehaviour5;
                        f2 = f4;
                        dimensionBehaviour3 = dimensionBehaviour;
                    } else if (i3 == 3) {
                        if (dimensionBehaviour5 == dimensionBehaviour4) {
                            measure(constraintWidget, dimensionBehaviour4, 0, dimensionBehaviour4, 0);
                        }
                        int width2 = constraintWidget.getWidth();
                        float f5 = constraintWidget.mDimensionRatio;
                        if (constraintWidget.mDimensionRatioSide == -1) {
                            f5 = 1.0f / f5;
                        }
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour15 = ConstraintWidget.DimensionBehaviour.FIXED;
                        measure(constraintWidget, dimensionBehaviour15, width2, dimensionBehaviour15, (int) ((width2 * f5) + 0.5f));
                        constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                        constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                        constraintWidget.measured = true;
                    } else {
                        dimensionBehaviour3 = dimensionBehaviour;
                        if (i3 == 1) {
                            measure(constraintWidget, dimensionBehaviour5, 0, dimensionBehaviour4, 0);
                            constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                        } else {
                            dimensionBehaviour2 = dimensionBehaviour5;
                            if (i3 == 2) {
                                ConstraintWidget.DimensionBehaviour dimensionBehaviour16 = constraintWidgetContainer.mListDimensionBehaviors[1];
                                ConstraintWidget.DimensionBehaviour dimensionBehaviour17 = ConstraintWidget.DimensionBehaviour.FIXED;
                                if (dimensionBehaviour16 == dimensionBehaviour17 || dimensionBehaviour16 == dimensionBehaviour9) {
                                    measure(constraintWidget, dimensionBehaviour2, constraintWidget.getWidth(), dimensionBehaviour17, (int) ((constraintWidgetContainer.getHeight() * f4) + 0.5f));
                                    constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                                    constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                                    constraintWidget.measured = true;
                                } else {
                                    f2 = f4;
                                }
                            } else {
                                f2 = f4;
                                if (constraintAnchorArr[2].mTarget == null || constraintAnchorArr[3].mTarget == null) {
                                    measure(constraintWidget, dimensionBehaviour4, 0, dimensionBehaviour3, 0);
                                    constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                                    constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                                    constraintWidget.measured = true;
                                }
                            }
                        }
                    }
                    if (dimensionBehaviour2 == dimensionBehaviour8 && dimensionBehaviour3 == dimensionBehaviour8) {
                        if (i2 == 1 || i3 == 1) {
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour18 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                            measure(constraintWidget, dimensionBehaviour18, 0, dimensionBehaviour18, 0);
                            constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                            constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                        } else if (i3 == 2 && i2 == 2) {
                            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = constraintWidgetContainer.mListDimensionBehaviors;
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour19 = dimensionBehaviourArr2[c2];
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour20 = ConstraintWidget.DimensionBehaviour.FIXED;
                            if (dimensionBehaviour19 == dimensionBehaviour20 && dimensionBehaviourArr2[1] == dimensionBehaviour20) {
                                measure(constraintWidget, dimensionBehaviour20, (int) ((constraintWidgetContainer.getWidth() * f) + 0.5f), dimensionBehaviour20, (int) ((constraintWidgetContainer.getHeight() * f2) + 0.5f));
                                constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                                constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                                constraintWidget.measured = true;
                            }
                        }
                    }
                }
                c = c2;
                arrayList = arrayList2;
                size = i4;
            }
        }
    }

    public final void buildGraph() {
        ArrayList arrayList = this.mRuns;
        arrayList.clear();
        ConstraintWidgetContainer constraintWidgetContainer = this.mContainer;
        constraintWidgetContainer.mHorizontalRun.clear();
        constraintWidgetContainer.mVerticalRun.clear();
        arrayList.add(constraintWidgetContainer.mHorizontalRun);
        arrayList.add(constraintWidgetContainer.mVerticalRun);
        ArrayList arrayList2 = constraintWidgetContainer.mChildren;
        int size = arrayList2.size();
        HashSet hashSet = null;
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ConstraintWidget constraintWidget = (ConstraintWidget) obj;
            if (constraintWidget instanceof Guideline) {
                arrayList.add(new GuidelineReference(constraintWidget));
            } else {
                if (constraintWidget.isInHorizontalChain()) {
                    if (constraintWidget.horizontalChainRun == null) {
                        constraintWidget.horizontalChainRun = new ChainRun(constraintWidget, 0);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(constraintWidget.horizontalChainRun);
                } else {
                    arrayList.add(constraintWidget.mHorizontalRun);
                }
                if (constraintWidget.isInVerticalChain()) {
                    if (constraintWidget.verticalChainRun == null) {
                        constraintWidget.verticalChainRun = new ChainRun(constraintWidget, 1);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(constraintWidget.verticalChainRun);
                } else {
                    arrayList.add(constraintWidget.mVerticalRun);
                }
                if (constraintWidget instanceof HelperWidget) {
                    arrayList.add(new HelperReferences(constraintWidget));
                }
            }
        }
        if (hashSet != null) {
            arrayList.addAll(hashSet);
        }
        int size2 = arrayList.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList.get(i2);
            i2++;
            ((WidgetRun) obj2).clear();
        }
        int size3 = arrayList.size();
        int i3 = 0;
        while (i3 < size3) {
            Object obj3 = arrayList.get(i3);
            i3++;
            WidgetRun widgetRun = (WidgetRun) obj3;
            if (widgetRun.mWidget != constraintWidgetContainer) {
                widgetRun.apply();
            }
        }
        this.mGroups.clear();
        ConstraintWidgetContainer constraintWidgetContainer2 = this.mWidgetcontainer;
        findGroup(constraintWidgetContainer2.mHorizontalRun, 0, this.mGroups);
        findGroup(constraintWidgetContainer2.mVerticalRun, 1, this.mGroups);
        this.mNeedBuildGraph = false;
    }

    public final int computeWrap(ConstraintWidgetContainer constraintWidgetContainer, int i) {
        int i2;
        long max;
        float f;
        DependencyGraph dependencyGraph = this;
        ConstraintWidgetContainer constraintWidgetContainer2 = constraintWidgetContainer;
        int size = dependencyGraph.mGroups.size();
        long j = 0;
        int i3 = 0;
        long j2 = 0;
        while (i3 < size) {
            WidgetRun widgetRun = ((RunGroup) dependencyGraph.mGroups.get(i3)).mFirstRun;
            if (!(widgetRun instanceof ChainRun) ? !(i != 0 ? (widgetRun instanceof VerticalWidgetRun) : (widgetRun instanceof HorizontalWidgetRun)) : ((ChainRun) widgetRun).orientation != i) {
                DependencyNode dependencyNode = (i == 0 ? constraintWidgetContainer2.mHorizontalRun : constraintWidgetContainer2.mVerticalRun).start;
                DependencyNode dependencyNode2 = (i == 0 ? constraintWidgetContainer2.mHorizontalRun : constraintWidgetContainer2.mVerticalRun).end;
                boolean contains = ((ArrayList) widgetRun.start.mTargets).contains(dependencyNode);
                DependencyNode dependencyNode3 = widgetRun.end;
                boolean contains2 = ((ArrayList) dependencyNode3.mTargets).contains(dependencyNode2);
                long wrapDimension = widgetRun.getWrapDimension();
                DependencyNode dependencyNode4 = widgetRun.start;
                if (contains && contains2) {
                    long traverseStart = RunGroup.traverseStart(dependencyNode4, j);
                    long traverseEnd = RunGroup.traverseEnd(dependencyNode3, j);
                    long j3 = traverseStart - wrapDimension;
                    int i4 = dependencyNode3.mMargin;
                    i2 = i3;
                    if (j3 >= (-i4)) {
                        j3 += i4;
                    }
                    long j4 = dependencyNode4.mMargin;
                    long j5 = ((-traverseEnd) - wrapDimension) - j4;
                    if (j5 >= j4) {
                        j5 -= j4;
                    }
                    ConstraintWidget constraintWidget = widgetRun.mWidget;
                    if (i == 0) {
                        f = constraintWidget.mHorizontalBiasPercent;
                    } else if (i == 1) {
                        f = constraintWidget.mVerticalBiasPercent;
                    } else {
                        constraintWidget.getClass();
                        f = -1.0f;
                    }
                    float f2 = f > 0.0f ? (long) ((j3 / (1.0f - f)) + (j5 / f)) : 0L;
                    max = (dependencyNode4.mMargin + ((((long) ((f2 * f) + 0.5f)) + wrapDimension) + ((long) DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(1.0f, f, f2, 0.5f)))) - dependencyNode3.mMargin;
                } else {
                    i2 = i3;
                    max = contains ? Math.max(RunGroup.traverseStart(dependencyNode4, dependencyNode4.mMargin), dependencyNode4.mMargin + wrapDimension) : contains2 ? Math.max(-RunGroup.traverseEnd(dependencyNode3, dependencyNode3.mMargin), (-dependencyNode3.mMargin) + wrapDimension) : (widgetRun.getWrapDimension() + dependencyNode4.mMargin) - dependencyNode3.mMargin;
                }
            } else {
                max = j;
                i2 = i3;
            }
            j2 = Math.max(j2, max);
            i3 = i2 + 1;
            dependencyGraph = this;
            constraintWidgetContainer2 = constraintWidgetContainer;
            j = 0;
        }
        return (int) j2;
    }

    public final void findGroup(WidgetRun widgetRun, int i, ArrayList arrayList) {
        DependencyNode dependencyNode;
        ArrayList arrayList2 = (ArrayList) widgetRun.start.mDependencies;
        int size = arrayList2.size();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            dependencyNode = widgetRun.end;
            if (i3 >= size) {
                break;
            }
            Object obj = arrayList2.get(i3);
            i3++;
            Dependency dependency = (Dependency) obj;
            if (dependency instanceof DependencyNode) {
                applyGroup((DependencyNode) dependency, i, 0, arrayList, null);
            } else if (dependency instanceof WidgetRun) {
                applyGroup(((WidgetRun) dependency).start, i, 0, arrayList, null);
            }
        }
        ArrayList arrayList3 = (ArrayList) dependencyNode.mDependencies;
        int size2 = arrayList3.size();
        int i4 = 0;
        while (i4 < size2) {
            Object obj2 = arrayList3.get(i4);
            i4++;
            Dependency dependency2 = (Dependency) obj2;
            if (dependency2 instanceof DependencyNode) {
                applyGroup((DependencyNode) dependency2, i, 1, arrayList, null);
            } else if (dependency2 instanceof WidgetRun) {
                applyGroup(((WidgetRun) dependency2).end, i, 1, arrayList, null);
            }
        }
        int i5 = i;
        if (i5 == 1) {
            ArrayList arrayList4 = (ArrayList) ((VerticalWidgetRun) widgetRun).baseline.mDependencies;
            int size3 = arrayList4.size();
            while (i2 < size3) {
                Object obj3 = arrayList4.get(i2);
                i2++;
                Dependency dependency3 = (Dependency) obj3;
                if (dependency3 instanceof DependencyNode) {
                    applyGroup((DependencyNode) dependency3, i5, 2, arrayList, null);
                }
                i5 = i;
            }
        }
    }

    public final void measure(ConstraintWidget constraintWidget, ConstraintWidget.DimensionBehaviour dimensionBehaviour, int i, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, int i2) {
        BasicMeasure.Measure measure = this.mMeasure;
        measure.horizontalBehavior = dimensionBehaviour;
        measure.verticalBehavior = dimensionBehaviour2;
        measure.horizontalDimension = i;
        measure.verticalDimension = i2;
        this.mMeasurer.measure(constraintWidget, measure);
        constraintWidget.setWidth(measure.measuredWidth);
        constraintWidget.setHeight(measure.measuredHeight);
        constraintWidget.mHasBaseline = measure.measuredHasBaseline;
        constraintWidget.setBaselineDistance(measure.measuredBaseline);
    }

    public final void measureWidgets() {
        DependencyGraph dependencyGraph;
        BaselineDimensionDependency baselineDimensionDependency;
        ArrayList arrayList = this.mWidgetcontainer.mChildren;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ConstraintWidget constraintWidget = (ConstraintWidget) obj;
            if (!constraintWidget.measured) {
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[1];
                int i2 = constraintWidget.mMatchConstraintDefaultWidth;
                int i3 = constraintWidget.mMatchConstraintDefaultHeight;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                boolean z = dimensionBehaviour == dimensionBehaviour3 || (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && i2 == 1);
                boolean z2 = dimensionBehaviour2 == dimensionBehaviour3 || (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && i3 == 1);
                DimensionDependency dimensionDependency = constraintWidget.mHorizontalRun.mDimension;
                boolean z3 = dimensionDependency.resolved;
                DimensionDependency dimensionDependency2 = constraintWidget.mVerticalRun.mDimension;
                boolean z4 = dimensionDependency2.resolved;
                if (z3 && z4) {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.FIXED;
                    dependencyGraph = this;
                    dependencyGraph.measure(constraintWidget, dimensionBehaviour4, dimensionDependency.value, dimensionBehaviour4, dimensionDependency2.value);
                    constraintWidget.measured = true;
                } else {
                    if (z3 && z2) {
                        this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, dimensionDependency.value, dimensionBehaviour3, dimensionDependency2.value);
                        if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            constraintWidget.mVerticalRun.mDimension.wrapValue = constraintWidget.getHeight();
                        } else {
                            constraintWidget.mVerticalRun.mDimension.resolve(constraintWidget.getHeight());
                            constraintWidget.measured = true;
                        }
                    } else if (z4 && z) {
                        dependencyGraph = this;
                        dependencyGraph.measure(constraintWidget, dimensionBehaviour3, dimensionDependency.value, ConstraintWidget.DimensionBehaviour.FIXED, dimensionDependency2.value);
                        if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            constraintWidget.mHorizontalRun.mDimension.wrapValue = constraintWidget.getWidth();
                        } else {
                            constraintWidget.mHorizontalRun.mDimension.resolve(constraintWidget.getWidth());
                            constraintWidget.measured = true;
                        }
                    }
                    dependencyGraph = this;
                }
                if (constraintWidget.measured && (baselineDimensionDependency = constraintWidget.mVerticalRun.mBaselineDimension) != null) {
                    baselineDimensionDependency.resolve(constraintWidget.mBaselineDistance);
                }
                this = dependencyGraph;
            }
        }
    }
}
