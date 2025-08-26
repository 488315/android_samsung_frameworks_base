package androidx.compose.material3;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.material3.internal.ChildSemanticsNodeElement;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import kotlin.ResultKt;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

/* loaded from: classes.dex */
public abstract class SurfaceKt {
    public static final DynamicProvidableCompositionLocal LocalAbsoluteTonalElevation = CompositionLocalKt.compositionLocalOf$default(new Function0() { // from class: androidx.compose.material3.SurfaceKt$LocalAbsoluteTonalElevation$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Dp.m837boximpl(0);
        }
    });

    /* renamed from: Surface-T9BRK9s, reason: not valid java name */
    public static final void m304SurfaceT9BRK9s(Modifier modifier, Shape shape, final long j, long j2, float f, float f2, BorderStroke borderStroke, final ComposableLambdaImpl composableLambdaImpl, Composer composer, int i, int i2) {
        if ((i2 & 1) != 0) {
            modifier = Modifier.Companion;
        }
        if ((i2 & 2) != 0) {
            shape = RectangleShapeKt.RectangleShape;
        }
        if ((i2 & 8) != 0) {
            j2 = ColorSchemeKt.m259contentColorForek8zF_U(j, composer);
        }
        if ((i2 & 16) != 0) {
            f = 0;
            Dp.Companion companion = Dp.Companion;
        }
        if ((i2 & 32) != 0) {
            f2 = 0;
            Dp.Companion companion2 = Dp.Companion;
        }
        if ((i2 & 64) != 0) {
            borderStroke = null;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.Surface (Surface.kt:104)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = LocalAbsoluteTonalElevation;
        final float f3 = f + ((Dp) composerImpl.consume(dynamicProvidableCompositionLocal)).value;
        final Shape shape2 = shape;
        final BorderStroke borderStroke2 = borderStroke;
        final float f4 = f2;
        final Modifier modifier2 = modifier;
        CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(j2)), dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Dp.m837boximpl(f3))}, ComposableLambdaKt.rememberComposableLambda(-70914509, new Function2() { // from class: androidx.compose.material3.SurfaceKt$Surface$1

            /* renamed from: androidx.compose.material3.SurfaceKt$Surface$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                int label;

                public AnonymousClass3(Continuation continuation) {
                    super(2, continuation);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return new AnonymousClass3((Continuation) obj2).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj, Object obj2) {
                Composer composer2 = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                    } else {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.Surface.<anonymous> (Surface.kt:110)");
                        }
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        Modifier modifierThen = SemanticsModifierKt.semantics(SurfaceKt.m306access$surfaceXOJAsU(modifier2, shape2, SurfaceKt.m307access$surfaceColorAtElevationCLU3JFs(j, f3, composer2), borderStroke2, ((Density) composerImpl3.consume(CompositionLocalsKt.LocalDensity)).mo58toPx0680j_4(f4)), false, new Function1() { // from class: androidx.compose.material3.SurfaceKt$Surface$1.2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj3) {
                                KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                                SemanticsProperties.INSTANCE.getClass();
                                SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.IsContainer;
                                KProperty kProperty = SemanticsPropertiesKt.$$delegatedProperties[5];
                                semanticsPropertyKey.setValue((SemanticsPropertyReceiver) obj3, Boolean.TRUE);
                                return Unit.INSTANCE;
                            }
                        }).then(new SuspendPointerInputElement(Unit.INSTANCE, null, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0(new AnonymousClass3(null)), 6, null));
                        Function2 function2 = composableLambdaImpl;
                        Alignment.Companion.getClass();
                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, true);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierThen);
                        ComposeUiNode.Companion.getClass();
                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                        if (composerImpl3.applier == null) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl3.startReusableNode();
                        if (composerImpl3.inserting) {
                            composerImpl3.createNode(function0);
                        } else {
                            composerImpl3.useNode();
                        }
                        Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function22);
                        }
                        Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                        function2.invoke(composerImpl3, 0);
                        composerImpl3.end(true);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }, composerImpl), composerImpl, 56);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    /* renamed from: Surface-o_FOJdg, reason: not valid java name */
    public static final void m305Surfaceo_FOJdg(final Function0 function0, Modifier modifier, boolean z, final Shape shape, final long j, long j2, float f, BorderStroke borderStroke, MutableInteractionSource mutableInteractionSource, final ComposableLambdaImpl composableLambdaImpl, Composer composer, int i, int i2) {
        final Modifier modifier2 = (i2 & 2) != 0 ? Modifier.Companion : modifier;
        final boolean z2 = (i2 & 4) != 0 ? true : z;
        long jM259contentColorForek8zF_U = (i2 & 32) != 0 ? ColorSchemeKt.m259contentColorForek8zF_U(j, composer) : j2;
        float f2 = 0;
        Dp.Companion companion = Dp.Companion;
        final float f3 = (i2 & 128) != 0 ? 0 : f;
        final BorderStroke borderStroke2 = (i2 & 256) != 0 ? null : borderStroke;
        final MutableInteractionSource mutableInteractionSource2 = (i2 & 512) == 0 ? mutableInteractionSource : null;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.Surface (Surface.kt:207)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        if (mutableInteractionSource2 == null) {
            composerImpl.startReplaceGroup(-549915119);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            mutableInteractionSource2 = (MutableInteractionSource) objRememberedValue;
        } else {
            composerImpl.startReplaceGroup(-1680307834);
        }
        composerImpl.end(false);
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = LocalAbsoluteTonalElevation;
        final float f4 = f2 + ((Dp) composerImpl2.consume(dynamicProvidableCompositionLocal)).value;
        CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(jM259contentColorForek8zF_U)), dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Dp.m837boximpl(f4))}, ComposableLambdaKt.rememberComposableLambda(1279702876, new Function2() { // from class: androidx.compose.material3.SurfaceKt$Surface$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj, Object obj2) {
                Composer composer2 = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    if (composerImpl3.getSkipping()) {
                        composerImpl3.skipToGroupEnd();
                    } else {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.Surface.<anonymous> (Surface.kt:215)");
                        }
                        Modifier modifier3 = modifier2;
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                        Modifier modifierThen = ClickableKt.m34clickableO2vRcR0$default(SurfaceKt.m306access$surfaceXOJAsU(modifier3.then(MinimumInteractiveModifier.INSTANCE), shape, SurfaceKt.m307access$surfaceColorAtElevationCLU3JFs(j, f4, composer2), borderStroke2, ((Density) composerImpl4.consume(CompositionLocalsKt.LocalDensity)).mo58toPx0680j_4(f3)), mutableInteractionSource2, RippleKt.m281rippleH2RKhps$default(0.0f, false, 7), z2, null, null, function0, 24).then(new ChildSemanticsNodeElement(new Function1() { // from class: androidx.compose.material3.internal.ChildParentSemanticsKt$childSemantics$1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj3) {
                                return Unit.INSTANCE;
                            }
                        }));
                        MutableInteractionSource MutableInteractionSource = mutableInteractionSource2;
                        if (MutableInteractionSource == null) {
                            MutableInteractionSource = InteractionSourceKt.MutableInteractionSource();
                        }
                        Modifier modifierThen2 = modifierThen.then(new InteractionSourceModifierElement(MutableInteractionSource));
                        Function2 function2 = composableLambdaImpl;
                        Alignment.Companion.getClass();
                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, true);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, modifierThen2);
                        ComposeUiNode.Companion.getClass();
                        Function0 function02 = ComposeUiNode.Companion.Constructor;
                        if (composerImpl4.applier == null) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl4.startReusableNode();
                        if (composerImpl4.inserting) {
                            composerImpl4.createNode(function02);
                        } else {
                            composerImpl4.useNode();
                        }
                        Updater.m337setimpl(composerImpl4, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function22);
                        }
                        Updater.m337setimpl(composerImpl4, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                        function2.invoke(composerImpl4, 0);
                        composerImpl4.end(true);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }, composerImpl2), composerImpl2, 56);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    /* renamed from: access$surface-XO-JAsU, reason: not valid java name */
    public static final Modifier m306access$surfaceXOJAsU(Modifier modifier, Shape shape, long j, BorderStroke borderStroke, float f) {
        Shape shape2;
        Modifier modifierM478graphicsLayerAp8cVGQ$default;
        if (f > 0.0f) {
            shape2 = shape;
            modifierM478graphicsLayerAp8cVGQ$default = GraphicsLayerModifierKt.m478graphicsLayerAp8cVGQ$default(Modifier.Companion, 0.0f, 0.0f, 0.0f, f, shape2, 124895);
        } else {
            shape2 = shape;
            modifierM478graphicsLayerAp8cVGQ$default = Modifier.Companion;
        }
        Modifier modifierThen = modifier.then(modifierM478graphicsLayerAp8cVGQ$default);
        Modifier modifierM29borderziNgDLE = Modifier.Companion;
        if (borderStroke != null) {
            modifierM29borderziNgDLE = BorderKt.m29borderziNgDLE(modifierM29borderziNgDLE, borderStroke.width, borderStroke.brush, shape2);
        }
        return ClipKt.clip(BackgroundKt.m26backgroundbw27NRU(modifierThen.then(modifierM29borderziNgDLE), j, shape2), shape2);
    }

    /* renamed from: access$surfaceColorAtElevation-CLU3JFs, reason: not valid java name */
    public static final long m307access$surfaceColorAtElevationCLU3JFs(long j, float f, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.surfaceColorAtElevation (Surface.kt:481)");
        }
        MaterialTheme.INSTANCE.getClass();
        ColorScheme colorScheme = MaterialTheme.getColorScheme(composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.applyTonalElevation (ColorScheme.kt:907)");
        }
        boolean zBooleanValue = ((Boolean) ((ComposerImpl) composer).consume(ColorSchemeKt.LocalTonalElevationEnabled)).booleanValue();
        long j2 = colorScheme.surface;
        Color.Companion companion = Color.Companion;
        if (ULong.m3447equalsimpl0(j, j2) && zBooleanValue) {
            j = ColorSchemeKt.m261surfaceColorAtElevation3ABfNKs(colorScheme, f);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return j;
    }
}
