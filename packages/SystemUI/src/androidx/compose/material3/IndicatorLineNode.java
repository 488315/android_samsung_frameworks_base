package androidx.compose.material3;

import androidx.compose.animation.ColorVectorConverterKt;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.FocusInteraction$Unfocus;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.foundation.text.selection.TextSelectionColorsKt;
import androidx.compose.material3.tokens.FilledTextFieldTokens;
import androidx.compose.ui.draw.CacheDrawModifierNode;
import androidx.compose.ui.draw.CacheDrawScope;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.PathOperation;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.Dp;
import java.util.ArrayList;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class IndicatorLineNode extends DelegatingNode implements CompositionLocalConsumerModifierNode {
    public TextFieldColors _colors;
    public Shape _shape;
    public Animatable colorAnimatable;
    public final CacheDrawModifierNode drawWithCacheModifierNode;
    public boolean enabled;
    public boolean focused;
    public float focusedIndicatorWidth;
    public InteractionSource interactionSource;
    public boolean isError;
    public StandaloneCoroutine trackFocusStateJob;
    public float unfocusedIndicatorWidth;
    public final Animatable widthAnimatable;

    public /* synthetic */ IndicatorLineNode(boolean z, boolean z2, InteractionSource interactionSource, TextFieldColors textFieldColors, Shape shape, float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, interactionSource, textFieldColors, shape, f, f2);
    }

    public static final Object access$trackFocusState(final IndicatorLineNode indicatorLineNode, SuspendLambda suspendLambda) {
        indicatorLineNode.focused = false;
        final ArrayList arrayList = new ArrayList();
        SharedFlowImpl interactions = indicatorLineNode.interactionSource.getInteractions();
        FlowCollector flowCollector = new FlowCollector() { // from class: androidx.compose.material3.IndicatorLineNode$trackFocusState$2
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation) {
                Interaction interaction = (Interaction) obj;
                if (interaction instanceof FocusInteraction$Focus) {
                    arrayList.add(interaction);
                } else if (interaction instanceof FocusInteraction$Unfocus) {
                    arrayList.remove(((FocusInteraction$Unfocus) interaction).focus);
                }
                boolean z = !arrayList.isEmpty();
                IndicatorLineNode indicatorLineNode2 = indicatorLineNode;
                if (z != indicatorLineNode2.focused) {
                    indicatorLineNode2.focused = z;
                    indicatorLineNode2.invalidateIndicator();
                }
                return Unit.INSTANCE;
            }
        };
        interactions.getClass();
        CoroutineSingletons collect$suspendImpl = SharedFlowImpl.collect$suspendImpl(interactions, flowCollector, suspendLambda);
        return collect$suspendImpl == CoroutineSingletons.COROUTINE_SUSPENDED ? collect$suspendImpl : Unit.INSTANCE;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    public final void invalidateIndicator() {
        BuildersKt.launch$default(getCoroutineScope(), null, null, new IndicatorLineNode$invalidateIndicator$1(this, null), 3);
        BuildersKt.launch$default(getCoroutineScope(), null, null, new IndicatorLineNode$invalidateIndicator$2(this, null), 3);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        this.trackFocusStateJob = BuildersKt.launch$default(getCoroutineScope(), null, null, new IndicatorLineNode$onAttach$1(this, null), 3);
        if (this.colorAnimatable == null) {
            TextFieldColors textFieldColors = this._colors;
            if (textFieldColors == null) {
                TextFieldDefaults textFieldDefaults = TextFieldDefaults.INSTANCE;
                ColorScheme colorScheme = (ColorScheme) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, ColorSchemeKt.LocalColorScheme);
                TextSelectionColors textSelectionColors = (TextSelectionColors) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, TextSelectionColorsKt.LocalTextSelectionColors);
                textFieldDefaults.getClass();
                textFieldColors = TextFieldDefaults.defaultTextFieldColors$material3_release(colorScheme, textSelectionColors);
            }
            long m310indicatorColorXeAY9LY$material3_release = textFieldColors.m310indicatorColorXeAY9LY$material3_release(this.enabled, this.isError, this.focused);
            this.colorAnimatable = new Animatable(Color.m454boximpl(m310indicatorColorXeAY9LY$material3_release), (TwoWayConverter) ColorVectorConverterKt.ColorToVector.mo779invoke(Color.m459getColorSpaceimpl(m310indicatorColorXeAY9LY$material3_release)), null, null, 12, null);
        }
    }

    private IndicatorLineNode(boolean z, boolean z2, InteractionSource interactionSource, TextFieldColors textFieldColors, Shape shape, float f, float f2) {
        this.enabled = z;
        this.isError = z2;
        this.interactionSource = interactionSource;
        this.focusedIndicatorWidth = f;
        this.unfocusedIndicatorWidth = f2;
        this._colors = textFieldColors;
        this._shape = shape;
        Dp m835boximpl = Dp.m835boximpl((this.focused && z) ? f : f2);
        Dp.Companion companion = Dp.Companion;
        this.widthAnimatable = new Animatable(m835boximpl, VectorConvertersKt.DpToVector, null, null, 12, null);
        CacheDrawModifierNode CacheDrawModifierNode = DrawModifierKt.CacheDrawModifierNode(new Function1() { // from class: androidx.compose.material3.IndicatorLineNode$drawWithCacheModifierNode$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                CacheDrawScope cacheDrawScope = (CacheDrawScope) obj;
                float density = cacheDrawScope.getDensity() * ((Dp) IndicatorLineNode.this.widthAnimatable.internalState.getValue()).value;
                AndroidPath Path = AndroidPath_androidKt.Path();
                IndicatorLineNode indicatorLineNode = IndicatorLineNode.this;
                Shape shape2 = indicatorLineNode._shape;
                if (shape2 == null) {
                    Shapes shapes = (Shapes) CompositionLocalConsumerModifierNodeKt.currentValueOf(indicatorLineNode, ShapesKt.LocalShapes);
                    FilledTextFieldTokens.INSTANCE.getClass();
                    shape2 = ShapesKt.fromToken(shapes, FilledTextFieldTokens.ContainerShape);
                }
                Outline mo40createOutlinePq9zytI = shape2.mo40createOutlinePq9zytI(cacheDrawScope.cacheParams.mo360getSizeNHjbRc(), cacheDrawScope.cacheParams.getLayoutDirection(), cacheDrawScope);
                if (mo40createOutlinePq9zytI instanceof Outline.Rectangle) {
                    Path.addRect$default(Path, ((Outline.Rectangle) mo40createOutlinePq9zytI).rect);
                } else if (mo40createOutlinePq9zytI instanceof Outline.Rounded) {
                    Path.addRoundRect$default(Path, ((Outline.Rounded) mo40createOutlinePq9zytI).roundRect);
                } else {
                    if (!(mo40createOutlinePq9zytI instanceof Outline.Generic)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    Path.m491addPathUv8p0NA$default(Path, ((Outline.Generic) mo40createOutlinePq9zytI).path);
                }
                AndroidPath Path2 = AndroidPath_androidKt.Path();
                Path.addRect$default(Path2, new Rect(0.0f, Size.m415getHeightimpl(cacheDrawScope.cacheParams.mo360getSizeNHjbRc()) - density, Size.m417getWidthimpl(cacheDrawScope.cacheParams.mo360getSizeNHjbRc()), Size.m415getHeightimpl(cacheDrawScope.cacheParams.mo360getSizeNHjbRc())));
                final AndroidPath Path3 = AndroidPath_androidKt.Path();
                PathOperation.Companion.getClass();
                Path3.m443opN5in7k0(Path2, Path, PathOperation.Intersect);
                final IndicatorLineNode indicatorLineNode2 = IndicatorLineNode.this;
                return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.material3.IndicatorLineNode$drawWithCacheModifierNode$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj2);
                        layoutNodeDrawScope.drawContent();
                        Path path = Path.this;
                        Animatable animatable = indicatorLineNode2.colorAnimatable;
                        animatable.getClass();
                        DrawScope.m536drawPathGBMwjPU$default(layoutNodeDrawScope, path, new SolidColor(((Color) animatable.internalState.getValue()).value, null), 0.0f, null, 60);
                        return Unit.INSTANCE;
                    }
                });
            }
        });
        delegate(CacheDrawModifierNode);
        this.drawWithCacheModifierNode = CacheDrawModifierNode;
    }
}
