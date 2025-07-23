package androidx.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.foundation.MarqueeSpacing;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusEventModifierNode;
import androidx.compose.ui.focus.FocusStateImpl;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.graphics.layer.GraphicsLayerKt;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.node.LookaheadCapablePlaceable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MarqueeModifierNode extends Modifier.Node implements LayoutModifierNode, DrawModifierNode, FocusEventModifierNode {
    public StandaloneCoroutine animationJob;
    public final MutableState animationMode$delegate;
    public final MutableIntState containerWidth$delegate;
    public final MutableIntState contentWidth$delegate;
    public int delayMillis;
    public final MutableState hasFocus$delegate;
    public int initialDelayMillis;
    public int iterations;
    public GraphicsLayer marqueeLayer;
    public final Animatable offset;
    public final MutableState spacing$delegate;
    public final State spacingPx$delegate;
    public float velocity;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LayoutDirection.values().length];
            try {
                iArr[LayoutDirection.Ltr.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutDirection.Rtl.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ MarqueeModifierNode(int i, int i2, int i3, int i4, MarqueeSpacing marqueeSpacing, float f, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, i3, i4, marqueeSpacing, f);
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(final LayoutNodeDrawScope layoutNodeDrawScope) {
        Animatable animatable = this.offset;
        float floatValue = ((Number) animatable.internalState.getValue()).floatValue() * getDirection();
        float direction = getDirection();
        AnimationState animationState = animatable.internalState;
        boolean z = direction != 1.0f ? ((Number) animationState.getValue()).floatValue() < ((float) getContainerWidth()) : ((Number) animationState.getValue()).floatValue() < ((float) getContentWidth());
        boolean z2 = getDirection() != 1.0f ? ((Number) animationState.getValue()).floatValue() > ((float) getSpacingPx()) : ((Number) animationState.getValue()).floatValue() > ((float) ((getContentWidth() + getSpacingPx()) - getContainerWidth()));
        float contentWidth = getDirection() == 1.0f ? getContentWidth() + getSpacingPx() : (-getContentWidth()) - getSpacingPx();
        CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
        float intBitsToFloat = Float.intBitsToFloat((int) (canvasDrawScope.mo545getSizeNHjbRc() & 4294967295L));
        GraphicsLayer graphicsLayer = this.marqueeLayer;
        if (graphicsLayer != null) {
            long contentWidth2 = (getContentWidth() << 32) | (MathKt__MathJVMKt.roundToInt(intBitsToFloat) & 4294967295L);
            IntSize.Companion companion = IntSize.Companion;
            layoutNodeDrawScope.m646recordJVtK1S4(contentWidth2, graphicsLayer, new Function1() { // from class: androidx.compose.foundation.MarqueeModifierNode$draw$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ((LayoutNodeDrawScope) ContentDrawScope.this).drawContent();
                    return Unit.INSTANCE;
                }
            });
        }
        float containerWidth = floatValue + getContainerWidth();
        float intBitsToFloat2 = Float.intBitsToFloat((int) (4294967295L & canvasDrawScope.mo545getSizeNHjbRc()));
        ClipOp.Companion.getClass();
        int i = ClipOp.Intersect;
        CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
        long m526getSizeNHjbRc = canvasDrawScope$drawContext$1.m526getSizeNHjbRc();
        canvasDrawScope$drawContext$1.getCanvas().save();
        try {
            canvasDrawScope$drawContext$1.transform.m528clipRectN_I0leg(floatValue, 0.0f, containerWidth, intBitsToFloat2, i);
            GraphicsLayer graphicsLayer2 = this.marqueeLayer;
            if (graphicsLayer2 != null) {
                if (z) {
                    GraphicsLayerKt.drawLayer(layoutNodeDrawScope, graphicsLayer2);
                }
                if (z2) {
                    canvasDrawScope.drawContext.transform.translate(contentWidth, 0.0f);
                    try {
                        GraphicsLayerKt.drawLayer(layoutNodeDrawScope, graphicsLayer2);
                        canvasDrawScope.drawContext.transform.translate(-contentWidth, -0.0f);
                    } finally {
                    }
                }
                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, m526getSizeNHjbRc);
            }
            if (z) {
                layoutNodeDrawScope.drawContent();
            }
            if (z2) {
                canvasDrawScope.drawContext.transform.translate(contentWidth, 0.0f);
                try {
                    layoutNodeDrawScope.drawContent();
                    canvasDrawScope.drawContext.transform.translate(-contentWidth, -0.0f);
                } finally {
                }
            }
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, m526getSizeNHjbRc);
        } catch (Throwable th) {
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, m526getSizeNHjbRc);
            throw th;
        }
    }

    public final int getContainerWidth() {
        return ((SnapshotMutableIntStateImpl) this.containerWidth$delegate).getIntValue();
    }

    public final int getContentWidth() {
        return ((SnapshotMutableIntStateImpl) this.contentWidth$delegate).getIntValue();
    }

    public final float getDirection() {
        float signum = Math.signum(this.velocity);
        int i = WhenMappings.$EnumSwitchMapping$0[DelegatableNodeKt.requireLayoutNode(this).layoutDirection.ordinal()];
        int i2 = 1;
        if (i != 1) {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            i2 = -1;
        }
        return signum * i2;
    }

    public final int getSpacingPx() {
        return ((Number) this.spacingPx$delegate.getValue()).intValue();
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return intrinsicMeasurable.maxIntrinsicHeight(Integer.MAX_VALUE);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return intrinsicMeasurable.maxIntrinsicWidth(i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(Constraints.m814copyZbe2FdA$default(j, 0, Integer.MAX_VALUE, 0, 0, 13));
        ((SnapshotMutableIntStateImpl) this.containerWidth$delegate).setIntValue(ConstraintsKt.m832constrainWidthK40F9xA(mo608measureBRTryo0.width, j));
        ((SnapshotMutableIntStateImpl) this.contentWidth$delegate).setIntValue(mo608measureBRTryo0.width);
        layout$1 = measureScope.layout$1(getContainerWidth(), mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.MarqueeModifierNode$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope.placeWithLayer$default((Placeable.PlacementScope) obj, Placeable.this, MathKt__MathJVMKt.roundToInt((-((Number) this.offset.internalState.getValue()).floatValue()) * this.getDirection()), 0, null, 12);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return intrinsicMeasurable.minIntrinsicHeight(Integer.MAX_VALUE);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return 0;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        GraphicsLayer graphicsLayer = this.marqueeLayer;
        GraphicsContext requireGraphicsContext = DelegatableNodeKt.requireGraphicsContext(this);
        if (graphicsLayer != null) {
            requireGraphicsContext.releaseGraphicsLayer(graphicsLayer);
        }
        this.marqueeLayer = requireGraphicsContext.createGraphicsLayer();
        restartAnimation();
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        StandaloneCoroutine standaloneCoroutine = this.animationJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.animationJob = null;
        GraphicsLayer graphicsLayer = this.marqueeLayer;
        if (graphicsLayer != null) {
            DelegatableNodeKt.requireGraphicsContext(this).releaseGraphicsLayer(graphicsLayer);
            this.marqueeLayer = null;
        }
    }

    @Override // androidx.compose.ui.focus.FocusEventModifierNode
    public final void onFocusEvent(FocusStateImpl focusStateImpl) {
        boolean hasFocus = focusStateImpl.getHasFocus();
        ((SnapshotMutableStateImpl) this.hasFocus$delegate).setValue(Boolean.valueOf(hasFocus));
    }

    public final void restartAnimation() {
        StandaloneCoroutine standaloneCoroutine = this.animationJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        if (this.isAttached) {
            this.animationJob = BuildersKt.launch$default(getCoroutineScope(), null, null, new MarqueeModifierNode$restartAnimation$1(standaloneCoroutine, this, null), 3);
        }
    }

    private MarqueeModifierNode(int i, int i2, int i3, int i4, final MarqueeSpacing marqueeSpacing, float f) {
        this.iterations = i;
        this.delayMillis = i3;
        this.initialDelayMillis = i4;
        this.velocity = f;
        this.contentWidth$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
        this.containerWidth$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
        this.hasFocus$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
        this.spacing$delegate = SnapshotStateKt.mutableStateOf$default(marqueeSpacing);
        this.animationMode$delegate = SnapshotStateKt.mutableStateOf$default(MarqueeAnimationMode.m43boximpl(i2));
        this.offset = AnimatableKt.Animatable(0.0f, 0.01f);
        this.spacingPx$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.foundation.MarqueeModifierNode$spacingPx$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MarqueeSpacing marqueeSpacing2 = MarqueeSpacing.this;
                MarqueeModifierNode marqueeModifierNode = this;
                Density density = DelegatableNodeKt.requireLayoutNode(marqueeModifierNode).density;
                marqueeModifierNode.getContentWidth();
                int containerWidth = marqueeModifierNode.getContainerWidth();
                ((MarqueeSpacing$Companion$$ExternalSyntheticLambda0) marqueeSpacing2).getClass();
                MarqueeSpacing.Companion companion = MarqueeSpacing.Companion.$$INSTANCE;
                return Integer.valueOf(MathKt__MathJVMKt.roundToInt(0.33333334f * containerWidth));
            }
        });
    }
}
