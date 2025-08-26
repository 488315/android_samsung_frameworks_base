package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.Easing;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.RepeatMode;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.animation.core.TransitionState;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.graphics.vector.PathNode;
import androidx.compose.ui.util.MathHelpersKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class PathPropertyValues extends PropertyValues<List<? extends PathNode>> {
    public PathPropertyValues() {
        super(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0067  */
    @Override // androidx.compose.animation.graphics.vector.PropertyValues
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final State createState(final Transition transition, String str, final int i, Composer composer, int i2) {
        Object currentState;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(119461169);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.PathPropertyValues.createState (Animator.kt:228)");
        }
        Function3 function3 = new Function3() { // from class: androidx.compose.animation.graphics.vector.PathPropertyValues$createState$timeState$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Transition.Segment segment = (Transition.Segment) obj;
                ((Number) obj3).intValue();
                ComposerImpl composerImpl2 = (ComposerImpl) ((Composer) obj2);
                composerImpl2.startReplaceGroup(2115989621);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.PathPropertyValues.createState.<anonymous> (Animator.kt:232)");
                }
                FiniteAnimationSpec finiteAnimationSpecTween$default = AnimationSpecKt.tween$default(i, 0, EasingKt.LinearEasing, 2);
                if (!((Boolean) segment.getTargetState()).booleanValue()) {
                    finiteAnimationSpecTween$default = new ReversedSpec(finiteAnimationSpecTween$default, i);
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
                return finiteAnimationSpecTween$default;
            }
        };
        int i3 = (i2 & 14) | ((i2 << 3) & 896);
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        TwoWayConverter twoWayConverter = VectorConvertersKt.FloatToVector;
        int i4 = ((i3 << 3) & 7168) | (i3 & 14);
        boolean zIsSeeking = transition.isSeeking();
        Composer.Companion companion = Composer.Companion;
        boolean z = true;
        TransitionState transitionState = transition.transitionState;
        if (zIsSeeking) {
            composerImpl.startReplaceGroup(1888611722);
            composerImpl.end(false);
            currentState = transitionState.getCurrentState();
        } else {
            composerImpl.startReplaceGroup(1888357677);
            boolean z2 = (((i4 & 14) ^ 6) > 4 && composerImpl.changed(transition)) || (i4 & 6) == 4;
            currentState = composerImpl.rememberedValue();
            if (!z2) {
                companion.getClass();
                if (currentState == Composer.Companion.Empty) {
                    Snapshot.Companion.getClass();
                    Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                    Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
                    Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                    try {
                        Object currentState2 = transitionState.getCurrentState();
                        Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                        composerImpl.updateRememberedValue(currentState2);
                        currentState = currentState2;
                    } catch (Throwable th) {
                        Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                        throw th;
                    }
                }
                composerImpl.end(false);
            }
        }
        boolean zBooleanValue = ((Boolean) currentState).booleanValue();
        composerImpl.startReplaceGroup(-1210845840);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.PathPropertyValues.createState.<anonymous> (Animator.kt:237)");
        }
        float f = zBooleanValue ? i : 0.0f;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        Float fValueOf = Float.valueOf(f);
        Object objRememberedValue = composerImpl.rememberedValue();
        companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.animation.graphics.vector.PathPropertyValues$createState$$inlined$animateFloat$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return ((SnapshotMutableStateImpl) transition.targetState$delegate).getValue();
                }
            });
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        boolean zBooleanValue2 = ((Boolean) ((State) objRememberedValue).getValue()).booleanValue();
        composerImpl.startReplaceGroup(-1210845840);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.PathPropertyValues.createState.<anonymous> (Animator.kt:237)");
        }
        float f2 = zBooleanValue2 ? i : 0.0f;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        Float fValueOf2 = Float.valueOf(f2);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.animation.graphics.vector.PathPropertyValues$createState$$inlined$animateFloat$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return transition.getSegment();
                }
            });
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation = TransitionKt.createTransitionAnimation(transition, fValueOf, fValueOf2, (FiniteAnimationSpec) function3.invoke(((State) objRememberedValue2).getValue(), composerImpl, 0), twoWayConverter, str, composerImpl, ((i4 << 6) & 458752) | (i4 & 14));
        if ((((i2 & 7168) ^ 3072) <= 2048 || !composerImpl.changed(this)) && (i2 & 3072) != 2048) {
            z = false;
        }
        boolean zChanged = composerImpl.changed(transitionAnimationStateCreateTransitionAnimation) | z;
        Object objRememberedValue3 = composerImpl.rememberedValue();
        if (zChanged || objRememberedValue3 == composer$Companion$Empty$1) {
            objRememberedValue3 = new Function0() { // from class: androidx.compose.animation.graphics.vector.PathPropertyValues$createState$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Object objPrevious;
                    PathNode reflectiveQuadTo;
                    PathNode arcTo;
                    PathPropertyValues pathPropertyValues = this.this$0;
                    float fFloatValue = ((Number) transitionAnimationStateCreateTransitionAnimation.getValue()).floatValue();
                    ArrayList arrayList = (ArrayList) pathPropertyValues.timestamps;
                    ListIterator listIterator = arrayList.listIterator(arrayList.size());
                    while (true) {
                        if (!listIterator.hasPrevious()) {
                            objPrevious = null;
                            break;
                        }
                        objPrevious = listIterator.previous();
                        if (((Timestamp) objPrevious).timeMillis <= fFloatValue) {
                            break;
                        }
                    }
                    Timestamp timestamp = (Timestamp) objPrevious;
                    if (timestamp == null) {
                        timestamp = (Timestamp) CollectionsKt___CollectionsKt.first(pathPropertyValues.timestamps);
                    }
                    float f3 = (fFloatValue - timestamp.timeMillis) / timestamp.durationMillis;
                    if (timestamp.repeatCount != 0) {
                        int i5 = 0;
                        while (f3 > 1.0f) {
                            f3 -= 1.0f;
                            i5++;
                        }
                        if (timestamp.repeatMode == RepeatMode.Reverse && i5 % 2 != 0) {
                            f3 = 1.0f - f3;
                        }
                    }
                    PropertyValuesHolderPath propertyValuesHolderPath = (PropertyValuesHolderPath) timestamp.holder;
                    Iterator it = propertyValuesHolderPath.animatorKeyframes.iterator();
                    int i6 = 0;
                    while (true) {
                        if (!it.hasNext()) {
                            i6 = -1;
                            break;
                        }
                        if (((Keyframe) it.next()).fraction >= f3) {
                            break;
                        }
                        i6++;
                    }
                    int i7 = i6 - 1;
                    if (i7 < 0) {
                        i7 = 0;
                    }
                    int i8 = i7 + 1;
                    Easing easing = ((Keyframe) propertyValuesHolderPath.animatorKeyframes.get(i8)).interpolator;
                    float f4 = (f3 - ((Keyframe) propertyValuesHolderPath.animatorKeyframes.get(i7)).fraction) / (((Keyframe) propertyValuesHolderPath.animatorKeyframes.get(i8)).fraction - ((Keyframe) propertyValuesHolderPath.animatorKeyframes.get(i7)).fraction);
                    if (f4 < 0.0f) {
                        f4 = 0.0f;
                    }
                    float fTransform = easing.transform(f4 <= 1.0f ? f4 : 1.0f);
                    List list = (List) ((Keyframe) propertyValuesHolderPath.animatorKeyframes.get(i7)).value;
                    List list2 = (List) ((Keyframe) propertyValuesHolderPath.animatorKeyframes.get(i8)).value;
                    int iMin = Math.min(list.size(), list2.size());
                    ArrayList arrayList2 = new ArrayList(iMin);
                    for (int i9 = 0; i9 < iMin; i9++) {
                        Object obj = list.get(i9);
                        PathNode pathNode = (PathNode) list2.get(i9);
                        PathNode pathNode2 = (PathNode) obj;
                        if (pathNode2 instanceof PathNode.RelativeMoveTo) {
                            if (!(pathNode instanceof PathNode.RelativeMoveTo)) {
                                throw new IllegalArgumentException("start and stop path nodes have different types");
                            }
                            PathNode.RelativeMoveTo relativeMoveTo = (PathNode.RelativeMoveTo) pathNode2;
                            PathNode.RelativeMoveTo relativeMoveTo2 = (PathNode.RelativeMoveTo) pathNode;
                            reflectiveQuadTo = new PathNode.RelativeMoveTo(MathHelpersKt.lerp(relativeMoveTo.dx, relativeMoveTo2.dx, fTransform), MathHelpersKt.lerp(relativeMoveTo.dy, relativeMoveTo2.dy, fTransform));
                        } else if (pathNode2 instanceof PathNode.MoveTo) {
                            if (!(pathNode instanceof PathNode.MoveTo)) {
                                throw new IllegalArgumentException("start and stop path nodes have different types");
                            }
                            PathNode.MoveTo moveTo = (PathNode.MoveTo) pathNode2;
                            PathNode.MoveTo moveTo2 = (PathNode.MoveTo) pathNode;
                            reflectiveQuadTo = new PathNode.MoveTo(MathHelpersKt.lerp(moveTo.x, moveTo2.x, fTransform), MathHelpersKt.lerp(moveTo.y, moveTo2.y, fTransform));
                        } else if (pathNode2 instanceof PathNode.RelativeLineTo) {
                            if (!(pathNode instanceof PathNode.RelativeLineTo)) {
                                throw new IllegalArgumentException("start and stop path nodes have different types");
                            }
                            PathNode.RelativeLineTo relativeLineTo = (PathNode.RelativeLineTo) pathNode2;
                            PathNode.RelativeLineTo relativeLineTo2 = (PathNode.RelativeLineTo) pathNode;
                            reflectiveQuadTo = new PathNode.RelativeLineTo(MathHelpersKt.lerp(relativeLineTo.dx, relativeLineTo2.dx, fTransform), MathHelpersKt.lerp(relativeLineTo.dy, relativeLineTo2.dy, fTransform));
                        } else if (pathNode2 instanceof PathNode.LineTo) {
                            if (!(pathNode instanceof PathNode.LineTo)) {
                                throw new IllegalArgumentException("start and stop path nodes have different types");
                            }
                            PathNode.LineTo lineTo = (PathNode.LineTo) pathNode2;
                            PathNode.LineTo lineTo2 = (PathNode.LineTo) pathNode;
                            reflectiveQuadTo = new PathNode.LineTo(MathHelpersKt.lerp(lineTo.x, lineTo2.x, fTransform), MathHelpersKt.lerp(lineTo.y, lineTo2.y, fTransform));
                        } else if (pathNode2 instanceof PathNode.RelativeHorizontalTo) {
                            if (!(pathNode instanceof PathNode.RelativeHorizontalTo)) {
                                throw new IllegalArgumentException("start and stop path nodes have different types");
                            }
                            reflectiveQuadTo = new PathNode.RelativeHorizontalTo(MathHelpersKt.lerp(((PathNode.RelativeHorizontalTo) pathNode2).dx, ((PathNode.RelativeHorizontalTo) pathNode).dx, fTransform));
                        } else if (pathNode2 instanceof PathNode.HorizontalTo) {
                            if (!(pathNode instanceof PathNode.HorizontalTo)) {
                                throw new IllegalArgumentException("start and stop path nodes have different types");
                            }
                            reflectiveQuadTo = new PathNode.HorizontalTo(MathHelpersKt.lerp(((PathNode.HorizontalTo) pathNode2).x, ((PathNode.HorizontalTo) pathNode).x, fTransform));
                        } else if (pathNode2 instanceof PathNode.RelativeVerticalTo) {
                            if (!(pathNode instanceof PathNode.RelativeVerticalTo)) {
                                throw new IllegalArgumentException("start and stop path nodes have different types");
                            }
                            reflectiveQuadTo = new PathNode.RelativeVerticalTo(MathHelpersKt.lerp(((PathNode.RelativeVerticalTo) pathNode2).dy, ((PathNode.RelativeVerticalTo) pathNode).dy, fTransform));
                        } else if (!(pathNode2 instanceof PathNode.VerticalTo)) {
                            if (pathNode2 instanceof PathNode.RelativeCurveTo) {
                                if (!(pathNode instanceof PathNode.RelativeCurveTo)) {
                                    throw new IllegalArgumentException("start and stop path nodes have different types");
                                }
                                PathNode.RelativeCurveTo relativeCurveTo = (PathNode.RelativeCurveTo) pathNode2;
                                PathNode.RelativeCurveTo relativeCurveTo2 = (PathNode.RelativeCurveTo) pathNode;
                                arcTo = new PathNode.RelativeCurveTo(MathHelpersKt.lerp(relativeCurveTo.dx1, relativeCurveTo2.dx1, fTransform), MathHelpersKt.lerp(relativeCurveTo.dy1, relativeCurveTo2.dy1, fTransform), MathHelpersKt.lerp(relativeCurveTo.dx2, relativeCurveTo2.dx2, fTransform), MathHelpersKt.lerp(relativeCurveTo.dy2, relativeCurveTo2.dy2, fTransform), MathHelpersKt.lerp(relativeCurveTo.dx3, relativeCurveTo2.dx3, fTransform), MathHelpersKt.lerp(relativeCurveTo.dy3, relativeCurveTo2.dy3, fTransform));
                            } else if (pathNode2 instanceof PathNode.CurveTo) {
                                if (!(pathNode instanceof PathNode.CurveTo)) {
                                    throw new IllegalArgumentException("start and stop path nodes have different types");
                                }
                                PathNode.CurveTo curveTo = (PathNode.CurveTo) pathNode2;
                                PathNode.CurveTo curveTo2 = (PathNode.CurveTo) pathNode;
                                arcTo = new PathNode.CurveTo(MathHelpersKt.lerp(curveTo.x1, curveTo2.x1, fTransform), MathHelpersKt.lerp(curveTo.y1, curveTo2.y1, fTransform), MathHelpersKt.lerp(curveTo.x2, curveTo2.x2, fTransform), MathHelpersKt.lerp(curveTo.y2, curveTo2.y2, fTransform), MathHelpersKt.lerp(curveTo.x3, curveTo2.x3, fTransform), MathHelpersKt.lerp(curveTo.y3, curveTo2.y3, fTransform));
                            } else if (pathNode2 instanceof PathNode.RelativeReflectiveCurveTo) {
                                if (!(pathNode instanceof PathNode.RelativeReflectiveCurveTo)) {
                                    throw new IllegalArgumentException("start and stop path nodes have different types");
                                }
                                PathNode.RelativeReflectiveCurveTo relativeReflectiveCurveTo = (PathNode.RelativeReflectiveCurveTo) pathNode2;
                                PathNode.RelativeReflectiveCurveTo relativeReflectiveCurveTo2 = (PathNode.RelativeReflectiveCurveTo) pathNode;
                                reflectiveQuadTo = new PathNode.RelativeReflectiveCurveTo(MathHelpersKt.lerp(relativeReflectiveCurveTo.dx1, relativeReflectiveCurveTo2.dx1, fTransform), MathHelpersKt.lerp(relativeReflectiveCurveTo.dy1, relativeReflectiveCurveTo2.dy1, fTransform), MathHelpersKt.lerp(relativeReflectiveCurveTo.dx2, relativeReflectiveCurveTo2.dx2, fTransform), MathHelpersKt.lerp(relativeReflectiveCurveTo.dy2, relativeReflectiveCurveTo2.dy2, fTransform));
                            } else if (pathNode2 instanceof PathNode.ReflectiveCurveTo) {
                                if (!(pathNode instanceof PathNode.ReflectiveCurveTo)) {
                                    throw new IllegalArgumentException("start and stop path nodes have different types");
                                }
                                PathNode.ReflectiveCurveTo reflectiveCurveTo = (PathNode.ReflectiveCurveTo) pathNode2;
                                PathNode.ReflectiveCurveTo reflectiveCurveTo2 = (PathNode.ReflectiveCurveTo) pathNode;
                                reflectiveQuadTo = new PathNode.ReflectiveCurveTo(MathHelpersKt.lerp(reflectiveCurveTo.x1, reflectiveCurveTo2.x1, fTransform), MathHelpersKt.lerp(reflectiveCurveTo.y1, reflectiveCurveTo2.y1, fTransform), MathHelpersKt.lerp(reflectiveCurveTo.x2, reflectiveCurveTo2.x2, fTransform), MathHelpersKt.lerp(reflectiveCurveTo.y2, reflectiveCurveTo2.y2, fTransform));
                            } else if (pathNode2 instanceof PathNode.RelativeQuadTo) {
                                if (!(pathNode instanceof PathNode.RelativeQuadTo)) {
                                    throw new IllegalArgumentException("start and stop path nodes have different types");
                                }
                                PathNode.RelativeQuadTo relativeQuadTo = (PathNode.RelativeQuadTo) pathNode2;
                                PathNode.RelativeQuadTo relativeQuadTo2 = (PathNode.RelativeQuadTo) pathNode;
                                reflectiveQuadTo = new PathNode.RelativeQuadTo(MathHelpersKt.lerp(relativeQuadTo.dx1, relativeQuadTo2.dx1, fTransform), MathHelpersKt.lerp(relativeQuadTo.dy1, relativeQuadTo2.dy1, fTransform), MathHelpersKt.lerp(relativeQuadTo.dx2, relativeQuadTo2.dx2, fTransform), MathHelpersKt.lerp(relativeQuadTo.dy2, relativeQuadTo2.dy2, fTransform));
                            } else if (pathNode2 instanceof PathNode.QuadTo) {
                                if (!(pathNode instanceof PathNode.QuadTo)) {
                                    throw new IllegalArgumentException("start and stop path nodes have different types");
                                }
                                PathNode.QuadTo quadTo = (PathNode.QuadTo) pathNode2;
                                PathNode.QuadTo quadTo2 = (PathNode.QuadTo) pathNode;
                                reflectiveQuadTo = new PathNode.QuadTo(MathHelpersKt.lerp(quadTo.x1, quadTo2.x1, fTransform), MathHelpersKt.lerp(quadTo.y1, quadTo2.y1, fTransform), MathHelpersKt.lerp(quadTo.x2, quadTo2.x2, fTransform), MathHelpersKt.lerp(quadTo.y2, quadTo2.y2, fTransform));
                            } else if (pathNode2 instanceof PathNode.RelativeReflectiveQuadTo) {
                                if (!(pathNode instanceof PathNode.RelativeReflectiveQuadTo)) {
                                    throw new IllegalArgumentException("start and stop path nodes have different types");
                                }
                                PathNode.RelativeReflectiveQuadTo relativeReflectiveQuadTo = (PathNode.RelativeReflectiveQuadTo) pathNode2;
                                PathNode.RelativeReflectiveQuadTo relativeReflectiveQuadTo2 = (PathNode.RelativeReflectiveQuadTo) pathNode;
                                reflectiveQuadTo = new PathNode.RelativeReflectiveQuadTo(MathHelpersKt.lerp(relativeReflectiveQuadTo.dx, relativeReflectiveQuadTo2.dx, fTransform), MathHelpersKt.lerp(relativeReflectiveQuadTo.dy, relativeReflectiveQuadTo2.dy, fTransform));
                            } else if (pathNode2 instanceof PathNode.ReflectiveQuadTo) {
                                if (!(pathNode instanceof PathNode.ReflectiveQuadTo)) {
                                    throw new IllegalArgumentException("start and stop path nodes have different types");
                                }
                                PathNode.ReflectiveQuadTo reflectiveQuadTo2 = (PathNode.ReflectiveQuadTo) pathNode2;
                                PathNode.ReflectiveQuadTo reflectiveQuadTo3 = (PathNode.ReflectiveQuadTo) pathNode;
                                reflectiveQuadTo = new PathNode.ReflectiveQuadTo(MathHelpersKt.lerp(reflectiveQuadTo2.x, reflectiveQuadTo3.x, fTransform), MathHelpersKt.lerp(reflectiveQuadTo2.y, reflectiveQuadTo3.y, fTransform));
                            } else if (pathNode2 instanceof PathNode.RelativeArcTo) {
                                if (!(pathNode instanceof PathNode.RelativeArcTo)) {
                                    throw new IllegalArgumentException("start and stop path nodes have different types");
                                }
                                PathNode.RelativeArcTo relativeArcTo = (PathNode.RelativeArcTo) pathNode2;
                                PathNode.RelativeArcTo relativeArcTo2 = (PathNode.RelativeArcTo) pathNode;
                                arcTo = new PathNode.RelativeArcTo(MathHelpersKt.lerp(relativeArcTo.horizontalEllipseRadius, relativeArcTo2.horizontalEllipseRadius, fTransform), MathHelpersKt.lerp(relativeArcTo.verticalEllipseRadius, relativeArcTo2.verticalEllipseRadius, fTransform), MathHelpersKt.lerp(relativeArcTo.theta, relativeArcTo2.theta, fTransform), relativeArcTo.isMoreThanHalf, relativeArcTo.isPositiveArc, MathHelpersKt.lerp(relativeArcTo.arcStartDx, relativeArcTo2.arcStartDx, fTransform), MathHelpersKt.lerp(relativeArcTo.arcStartDy, relativeArcTo2.arcStartDy, fTransform));
                            } else if (!(pathNode2 instanceof PathNode.ArcTo)) {
                                reflectiveQuadTo = PathNode.Close.INSTANCE;
                                if (!Intrinsics.areEqual(pathNode2, reflectiveQuadTo)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                            } else {
                                if (!(pathNode instanceof PathNode.ArcTo)) {
                                    throw new IllegalArgumentException("start and stop path nodes have different types");
                                }
                                PathNode.ArcTo arcTo2 = (PathNode.ArcTo) pathNode2;
                                PathNode.ArcTo arcTo3 = (PathNode.ArcTo) pathNode;
                                arcTo = new PathNode.ArcTo(MathHelpersKt.lerp(arcTo2.horizontalEllipseRadius, arcTo3.horizontalEllipseRadius, fTransform), MathHelpersKt.lerp(arcTo2.verticalEllipseRadius, arcTo3.verticalEllipseRadius, fTransform), MathHelpersKt.lerp(arcTo2.theta, arcTo3.theta, fTransform), arcTo2.isMoreThanHalf, arcTo2.isPositiveArc, MathHelpersKt.lerp(arcTo2.arcStartX, arcTo3.arcStartX, fTransform), MathHelpersKt.lerp(arcTo2.arcStartY, arcTo3.arcStartY, fTransform));
                            }
                            reflectiveQuadTo = arcTo;
                        } else {
                            if (!(pathNode instanceof PathNode.VerticalTo)) {
                                throw new IllegalArgumentException("start and stop path nodes have different types");
                            }
                            reflectiveQuadTo = new PathNode.VerticalTo(MathHelpersKt.lerp(((PathNode.VerticalTo) pathNode2).y, ((PathNode.VerticalTo) pathNode).y, fTransform));
                        }
                        arrayList2.add(reflectiveQuadTo);
                    }
                    return arrayList2;
                }
            };
            composerImpl.updateRememberedValue(objRememberedValue3);
        }
        State stateDerivedStateOf = SnapshotStateKt.derivedStateOf((Function0) objRememberedValue3);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return stateDerivedStateOf;
    }
}
