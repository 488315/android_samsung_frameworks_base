package androidx.compose.material3;

import androidx.compose.animation.ColorVectorConverterKt;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.FocusInteraction$Unfocus;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.foundation.text.selection.TextSelectionColorsKt;
import androidx.compose.material3.tokens.FilledTextFieldTokens;
import androidx.compose.material3.tokens.MotionSchemeKeyTokens;
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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

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

    /* renamed from: androidx.compose.material3.IndicatorLineNode$invalidateIndicator$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return IndicatorLineNode.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                IndicatorLineNode indicatorLineNode = IndicatorLineNode.this;
                Animatable animatable = indicatorLineNode.colorAnimatable;
                if (animatable != null) {
                    TextFieldColors textFieldColorsDefaultTextFieldColors$material3_release = indicatorLineNode._colors;
                    if (textFieldColorsDefaultTextFieldColors$material3_release == null) {
                        TextFieldDefaults textFieldDefaults = TextFieldDefaults.INSTANCE;
                        ColorScheme colorScheme = (ColorScheme) CompositionLocalConsumerModifierNodeKt.currentValueOf(indicatorLineNode, ColorSchemeKt.LocalColorScheme);
                        TextSelectionColors textSelectionColors = (TextSelectionColors) CompositionLocalConsumerModifierNodeKt.currentValueOf(indicatorLineNode, TextSelectionColorsKt.LocalTextSelectionColors);
                        textFieldDefaults.getClass();
                        textFieldColorsDefaultTextFieldColors$material3_release = TextFieldDefaults.defaultTextFieldColors$material3_release(colorScheme, textSelectionColors);
                    }
                    IndicatorLineNode indicatorLineNode2 = IndicatorLineNode.this;
                    Color colorM456boximpl = Color.m456boximpl(textFieldColorsDefaultTextFieldColors$material3_release.m311indicatorColorXeAY9LY$material3_release(indicatorLineNode2.enabled, indicatorLineNode2.isError, indicatorLineNode2.focused));
                    IndicatorLineNode indicatorLineNode3 = IndicatorLineNode.this;
                    AnimationSpec animationSpecFromToken = indicatorLineNode3.enabled ? MotionSchemeKt.fromToken((MotionScheme) CompositionLocalConsumerModifierNodeKt.currentValueOf(indicatorLineNode3, MotionSchemeKt.LocalMotionScheme), MotionSchemeKeyTokens.FastEffects) : AnimationSpecKt.snap$default();
                    this.label = 1;
                    obj = Animatable.animateTo$default(animatable, colorM456boximpl, animationSpecFromToken, null, null, this, 12);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: androidx.compose.material3.IndicatorLineNode$invalidateIndicator$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return IndicatorLineNode.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                IndicatorLineNode indicatorLineNode = IndicatorLineNode.this;
                Animatable animatable = indicatorLineNode.widthAnimatable;
                Dp dpM837boximpl = Dp.m837boximpl((indicatorLineNode.focused && indicatorLineNode.enabled) ? indicatorLineNode.focusedIndicatorWidth : indicatorLineNode.unfocusedIndicatorWidth);
                IndicatorLineNode indicatorLineNode2 = IndicatorLineNode.this;
                AnimationSpec animationSpecFromToken = indicatorLineNode2.enabled ? MotionSchemeKt.fromToken((MotionScheme) CompositionLocalConsumerModifierNodeKt.currentValueOf(indicatorLineNode2, MotionSchemeKt.LocalMotionScheme), MotionSchemeKeyTokens.FastSpatial) : AnimationSpecKt.snap$default();
                this.label = 1;
                if (Animatable.animateTo$default(animatable, dpM837boximpl, animationSpecFromToken, null, null, this, 12) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: androidx.compose.material3.IndicatorLineNode$onAttach$1, reason: invalid class name and case insensitive filesystem */
    final class C07261 extends SuspendLambda implements Function2 {
        int label;

        public C07261(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return IndicatorLineNode.this.new C07261(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07261) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                IndicatorLineNode indicatorLineNode = IndicatorLineNode.this;
                this.label = 1;
                if (IndicatorLineNode.access$trackFocusState(indicatorLineNode, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public /* synthetic */ IndicatorLineNode(boolean z, boolean z2, InteractionSource interactionSource, TextFieldColors textFieldColors, Shape shape, float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, interactionSource, textFieldColors, shape, f, f2);
    }

    public static final Object access$trackFocusState(final IndicatorLineNode indicatorLineNode, SuspendLambda suspendLambda) throws Throwable {
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
        CoroutineSingletons coroutineSingletonsCollect$suspendImpl = SharedFlowImpl.collect$suspendImpl(interactions, flowCollector, suspendLambda);
        return coroutineSingletonsCollect$suspendImpl == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineSingletonsCollect$suspendImpl : Unit.INSTANCE;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    public final void invalidateIndicator() {
        BuildersKt.launch$default(getCoroutineScope(), null, null, new AnonymousClass1(null), 3);
        BuildersKt.launch$default(getCoroutineScope(), null, null, new AnonymousClass2(null), 3);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        this.trackFocusStateJob = BuildersKt.launch$default(getCoroutineScope(), null, null, new C07261(null), 3);
        if (this.colorAnimatable == null) {
            TextFieldColors textFieldColorsDefaultTextFieldColors$material3_release = this._colors;
            if (textFieldColorsDefaultTextFieldColors$material3_release == null) {
                TextFieldDefaults textFieldDefaults = TextFieldDefaults.INSTANCE;
                ColorScheme colorScheme = (ColorScheme) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, ColorSchemeKt.LocalColorScheme);
                TextSelectionColors textSelectionColors = (TextSelectionColors) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, TextSelectionColorsKt.LocalTextSelectionColors);
                textFieldDefaults.getClass();
                textFieldColorsDefaultTextFieldColors$material3_release = TextFieldDefaults.defaultTextFieldColors$material3_release(colorScheme, textSelectionColors);
            }
            long jM311indicatorColorXeAY9LY$material3_release = textFieldColorsDefaultTextFieldColors$material3_release.m311indicatorColorXeAY9LY$material3_release(this.enabled, this.isError, this.focused);
            this.colorAnimatable = new Animatable(Color.m456boximpl(jM311indicatorColorXeAY9LY$material3_release), (TwoWayConverter) ColorVectorConverterKt.ColorToVector.mo781invoke(Color.m461getColorSpaceimpl(jM311indicatorColorXeAY9LY$material3_release)), null, null, 12, null);
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
        Dp dpM837boximpl = Dp.m837boximpl((this.focused && z) ? f : f2);
        Dp.Companion companion = Dp.Companion;
        this.widthAnimatable = new Animatable(dpM837boximpl, VectorConvertersKt.DpToVector, null, null, 12, null);
        CacheDrawModifierNode CacheDrawModifierNode = DrawModifierKt.CacheDrawModifierNode(new Function1() { // from class: androidx.compose.material3.IndicatorLineNode$drawWithCacheModifierNode$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                CacheDrawScope cacheDrawScope = (CacheDrawScope) obj;
                float density = cacheDrawScope.getDensity() * ((Dp) this.this$0.widthAnimatable.internalState.getValue()).value;
                AndroidPath androidPathPath = AndroidPath_androidKt.Path();
                IndicatorLineNode indicatorLineNode = this.this$0;
                Shape shapeFromToken = indicatorLineNode._shape;
                if (shapeFromToken == null) {
                    Shapes shapes = (Shapes) CompositionLocalConsumerModifierNodeKt.currentValueOf(indicatorLineNode, ShapesKt.LocalShapes);
                    FilledTextFieldTokens.INSTANCE.getClass();
                    shapeFromToken = ShapesKt.fromToken(shapes, FilledTextFieldTokens.ContainerShape);
                }
                Outline outlineMo41createOutlinePq9zytI = shapeFromToken.mo41createOutlinePq9zytI(cacheDrawScope.cacheParams.mo361getSizeNHjbRc(), cacheDrawScope.cacheParams.getLayoutDirection(), cacheDrawScope);
                if (outlineMo41createOutlinePq9zytI instanceof Outline.Rectangle) {
                    Path.addRect$default(androidPathPath, ((Outline.Rectangle) outlineMo41createOutlinePq9zytI).rect);
                } else if (outlineMo41createOutlinePq9zytI instanceof Outline.Rounded) {
                    Path.addRoundRect$default(androidPathPath, ((Outline.Rounded) outlineMo41createOutlinePq9zytI).roundRect);
                } else {
                    if (!(outlineMo41createOutlinePq9zytI instanceof Outline.Generic)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    Path.m493addPathUv8p0NA$default(androidPathPath, ((Outline.Generic) outlineMo41createOutlinePq9zytI).path);
                }
                AndroidPath androidPathPath2 = AndroidPath_androidKt.Path();
                Path.addRect$default(androidPathPath2, new Rect(0.0f, Size.m417getHeightimpl(cacheDrawScope.cacheParams.mo361getSizeNHjbRc()) - density, Size.m419getWidthimpl(cacheDrawScope.cacheParams.mo361getSizeNHjbRc()), Size.m417getHeightimpl(cacheDrawScope.cacheParams.mo361getSizeNHjbRc())));
                final AndroidPath androidPathPath3 = AndroidPath_androidKt.Path();
                PathOperation.Companion.getClass();
                androidPathPath3.m445opN5in7k0(androidPathPath2, androidPathPath, PathOperation.Intersect);
                final IndicatorLineNode indicatorLineNode2 = this.this$0;
                return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.material3.IndicatorLineNode$drawWithCacheModifierNode$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj2);
                        layoutNodeDrawScope.drawContent();
                        Path path = androidPathPath3;
                        Animatable animatable = indicatorLineNode2.colorAnimatable;
                        animatable.getClass();
                        DrawScope.m538drawPathGBMwjPU$default(layoutNodeDrawScope, path, new SolidColor(((Color) animatable.internalState.getValue()).value, null), 0.0f, null, 60);
                        return Unit.INSTANCE;
                    }
                });
            }
        });
        delegate(CacheDrawModifierNode);
        this.drawWithCacheModifierNode = CacheDrawModifierNode;
    }
}
