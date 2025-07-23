package androidx.compose.animation;

import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.animation.AnimatedContentTransitionScopeImpl;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationVector2D;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.Transition;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.ParentDataModifier;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AnimatedContentTransitionScopeImpl<S> implements AnimatedContentTransitionScope<S> {
    public Alignment contentAlignment;
    public final MutableState measuredSize$delegate;
    public final MutableScatterMap targetSizeMap;
    public final Transition transition;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class SizeModifierElement<S> extends ModifierNodeElement<SizeModifierNode<S>> {
        public final AnimatedContentTransitionScopeImpl scope;
        public final Transition.DeferredAnimation sizeAnimation;
        public final State sizeTransform;

        public SizeModifierElement(Transition.DeferredAnimation<IntSize, AnimationVector2D> deferredAnimation, State<? extends SizeTransform> state, AnimatedContentTransitionScopeImpl<S> animatedContentTransitionScopeImpl) {
            this.sizeAnimation = deferredAnimation;
            this.sizeTransform = state;
            this.scope = animatedContentTransitionScopeImpl;
        }

        @Override // androidx.compose.ui.node.ModifierNodeElement
        public final Modifier.Node create() {
            return new SizeModifierNode(this.sizeAnimation, this.sizeTransform, this.scope);
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof SizeModifierElement)) {
                return false;
            }
            SizeModifierElement sizeModifierElement = (SizeModifierElement) obj;
            return Intrinsics.areEqual(sizeModifierElement.sizeAnimation, this.sizeAnimation) && Intrinsics.areEqual(sizeModifierElement.sizeTransform, this.sizeTransform);
        }

        public final int hashCode() {
            int hashCode = this.scope.hashCode() * 31;
            Transition.DeferredAnimation deferredAnimation = this.sizeAnimation;
            return this.sizeTransform.hashCode() + ((hashCode + (deferredAnimation != null ? deferredAnimation.hashCode() : 0)) * 31);
        }

        @Override // androidx.compose.ui.node.ModifierNodeElement
        public final void update(Modifier.Node node) {
            SizeModifierNode sizeModifierNode = (SizeModifierNode) node;
            sizeModifierNode.sizeAnimation = this.sizeAnimation;
            sizeModifierNode.sizeTransform = this.sizeTransform;
            sizeModifierNode.scope = this.scope;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class SizeModifierNode<S> extends LayoutModifierNodeWithPassThroughIntrinsics {
        public long lastSize = AnimatedContentKt.UnspecifiedSize;
        public AnimatedContentTransitionScopeImpl scope;
        public Transition.DeferredAnimation sizeAnimation;
        public State sizeTransform;

        public SizeModifierNode(Transition.DeferredAnimation<IntSize, AnimationVector2D> deferredAnimation, State<? extends SizeTransform> state, AnimatedContentTransitionScopeImpl<S> animatedContentTransitionScopeImpl) {
            this.sizeAnimation = deferredAnimation;
            this.sizeTransform = state;
            this.scope = animatedContentTransitionScopeImpl;
        }

        @Override // androidx.compose.ui.node.LayoutModifierNode
        /* renamed from: measure-3p2s80s, reason: not valid java name */
        public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
            final long j2;
            MeasureResult layout$1;
            final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
            if (measureScope.isLookingAhead()) {
                j2 = (mo608measureBRTryo0.width << 32) | (mo608measureBRTryo0.height & 4294967295L);
                IntSize.Companion companion = IntSize.Companion;
            } else {
                Transition.DeferredAnimation deferredAnimation = this.sizeAnimation;
                if (deferredAnimation == null) {
                    j2 = (mo608measureBRTryo0.width << 32) | (mo608measureBRTryo0.height & 4294967295L);
                    IntSize.Companion companion2 = IntSize.Companion;
                    this.lastSize = j2;
                } else {
                    final long j3 = (mo608measureBRTryo0.height & 4294967295L) | (mo608measureBRTryo0.width << 32);
                    IntSize.Companion companion3 = IntSize.Companion;
                    Transition.DeferredAnimation.DeferredAnimationData animate = deferredAnimation.animate(new Function1(this) { // from class: androidx.compose.animation.AnimatedContentTransitionScopeImpl$SizeModifierNode$measure$size$1
                        final /* synthetic */ AnimatedContentTransitionScopeImpl.SizeModifierNode<Object> this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                            this.this$0 = this;
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            long j4;
                            Transition.Segment segment = (Transition.Segment) obj;
                            long j5 = 0;
                            if (Intrinsics.areEqual(segment.getInitialState(), this.this$0.scope.getInitialState())) {
                                AnimatedContentTransitionScopeImpl.SizeModifierNode<Object> sizeModifierNode = this.this$0;
                                j4 = j3;
                                if (!IntSize.m861equalsimpl0(sizeModifierNode.lastSize, AnimatedContentKt.UnspecifiedSize)) {
                                    j4 = sizeModifierNode.lastSize;
                                }
                            } else {
                                State state = (State) this.this$0.scope.targetSizeMap.get(segment.getInitialState());
                                if (state != null) {
                                    j4 = ((IntSize) state.getValue()).packedValue;
                                } else {
                                    IntSize.Companion.getClass();
                                    j4 = 0;
                                }
                            }
                            State state2 = (State) this.this$0.scope.targetSizeMap.get(segment.getTargetState());
                            if (state2 != null) {
                                j5 = ((IntSize) state2.getValue()).packedValue;
                            } else {
                                IntSize.Companion.getClass();
                            }
                            SizeTransform sizeTransform = (SizeTransform) this.this$0.sizeTransform.getValue();
                            if (sizeTransform != null) {
                                FiniteAnimationSpec finiteAnimationSpec = (FiniteAnimationSpec) ((SizeTransformImpl) sizeTransform).sizeAnimationSpec.invoke(IntSize.m859boximpl(j4), IntSize.m859boximpl(j5));
                                if (finiteAnimationSpec != null) {
                                    return finiteAnimationSpec;
                                }
                            }
                            return AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);
                        }
                    }, new Function1(this) { // from class: androidx.compose.animation.AnimatedContentTransitionScopeImpl$SizeModifierNode$measure$size$2
                        final /* synthetic */ AnimatedContentTransitionScopeImpl.SizeModifierNode<Object> this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                            this.this$0 = this;
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            long j4;
                            if (Intrinsics.areEqual(obj, this.this$0.scope.getInitialState())) {
                                AnimatedContentTransitionScopeImpl.SizeModifierNode<Object> sizeModifierNode = this.this$0;
                                j4 = j3;
                                if (!IntSize.m861equalsimpl0(sizeModifierNode.lastSize, AnimatedContentKt.UnspecifiedSize)) {
                                    j4 = sizeModifierNode.lastSize;
                                }
                            } else {
                                State state = (State) this.this$0.scope.targetSizeMap.get(obj);
                                if (state != null) {
                                    j4 = ((IntSize) state.getValue()).packedValue;
                                } else {
                                    IntSize.Companion.getClass();
                                    j4 = 0;
                                }
                            }
                            return IntSize.m859boximpl(j4);
                        }
                    });
                    this.scope.getClass();
                    j2 = ((IntSize) animate.getValue()).packedValue;
                    this.lastSize = ((IntSize) animate.getValue()).packedValue;
                }
            }
            layout$1 = measureScope.layout$1((int) (j2 >> 32), (int) (4294967295L & j2), MapsKt__MapsKt.emptyMap(), new Function1(this) { // from class: androidx.compose.animation.AnimatedContentTransitionScopeImpl$SizeModifierNode$measure$1
                final /* synthetic */ AnimatedContentTransitionScopeImpl.SizeModifierNode<Object> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    Alignment alignment = this.this$0.scope.contentAlignment;
                    Placeable placeable = mo608measureBRTryo0;
                    long j4 = (placeable.width << 32) | (placeable.height & 4294967295L);
                    IntSize.Companion companion4 = IntSize.Companion;
                    Placeable.PlacementScope.m626place70tqf50$default((Placeable.PlacementScope) obj, mo608measureBRTryo0, alignment.mo352alignKFBX0sM(j4, j2, LayoutDirection.Ltr));
                    return Unit.INSTANCE;
                }
            });
            return layout$1;
        }

        @Override // androidx.compose.ui.Modifier.Node
        public final void onReset() {
            this.lastSize = AnimatedContentKt.UnspecifiedSize;
        }
    }

    public AnimatedContentTransitionScopeImpl(Transition<S> transition, Alignment alignment, LayoutDirection layoutDirection) {
        this.transition = transition;
        this.contentAlignment = alignment;
        IntSize.Companion.getClass();
        this.measuredSize$delegate = SnapshotStateKt.mutableStateOf$default(IntSize.m859boximpl(0L));
        this.targetSizeMap = ScatterMapKt.mutableScatterMapOf();
    }

    @Override // androidx.compose.animation.core.Transition.Segment
    public final Object getInitialState() {
        return this.transition.getSegment().getInitialState();
    }

    @Override // androidx.compose.animation.core.Transition.Segment
    public final Object getTargetState() {
        return this.transition.getSegment().getTargetState();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ChildData implements ParentDataModifier {
        public final MutableState isTarget$delegate;

        public ChildData(boolean z) {
            this.isTarget$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(z));
        }

        @Override // androidx.compose.ui.layout.ParentDataModifier
        public final Object modifyParentData() {
            return this;
        }
    }
}
