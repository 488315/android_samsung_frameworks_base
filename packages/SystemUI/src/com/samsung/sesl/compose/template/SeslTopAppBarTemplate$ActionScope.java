package com.samsung.sesl.compose.template;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import com.samsung.sesl.compose.component.tokens.SeslAppBarColorSchemeKeyTokens;
import com.samsung.sesl.compose.foundation.theme.BasicColorSchemeKt;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$ActionScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslTopAppBarTemplate$ActionScope {
    public static final float ActionSlotInsetEnd;
    public static final Companion Companion = new Companion(null);
    public static final SeslTopAppBarTemplate$ActionScope instance = new SeslTopAppBarTemplate$ActionScope();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Dp.Companion companion = Dp.Companion;
        ActionSlotInsetEnd = 18;
    }

    private SeslTopAppBarTemplate$ActionScope() {
    }

    public final void SeslTopAppBarActionLayout(final ComposableLambdaImpl composableLambdaImpl, final ComposableLambdaImpl composableLambdaImpl2, final ComposableLambdaImpl composableLambdaImpl3, Modifier modifier, Composer composer, final int i) {
        int i2;
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-633337351);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(composableLambdaImpl) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(composableLambdaImpl2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(composableLambdaImpl3) ? 256 : 128;
        }
        int i3 = i2 | 3072;
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            modifier2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.template.SeslTopAppBarTemplate.ActionScope.SeslTopAppBarActionLayout (AppBarTemplate.kt:310)");
            }
            long color = BasicColorSchemeKt.toColor(SeslAppBarColorSchemeKeyTokens.TopAppBarMenuTextColor, composerImpl);
            Arrangement arrangement = Arrangement.INSTANCE;
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.End;
            arrangement.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.aligned(horizontal), Alignment.Companion.CenterVertically, composerImpl, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier2);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            Object obj = RowScopeInstance.INSTANCE;
            SeslTopAppBarTemplate$ActionItemScope.Companion.getClass();
            composableLambdaImpl.invoke(obj, SeslTopAppBarTemplate$ActionItemScope.instance, composerImpl, Integer.valueOf(((i3 << 6) & 896) | 6));
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = TextKt.LocalTextStyle;
            CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(TextStyle.m754copyp1EtxEg$default((TextStyle) composerImpl.consume(dynamicProvidableCompositionLocal), color, 0L, null, null, 0L, 0, 0L, null, null, 0, 16777214)), ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(color))}, ComposableLambdaKt.rememberComposableLambda(1081040213, new Function2() { // from class: com.samsung.sesl.compose.template.SeslTopAppBarTemplate$ActionScope$SeslTopAppBarActionLayout$4$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.template.SeslTopAppBarTemplate.ActionScope.SeslTopAppBarActionLayout.<anonymous>.<anonymous> (AppBarTemplate.kt:323)");
                    }
                    Modifier.Companion companion = Modifier.Companion;
                    Alignment.Companion.getClass();
                    MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composer2, companion);
                    ComposeUiNode.Companion.getClass();
                    Function0 function02 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl3.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl3.startReusableNode();
                    if (composerImpl3.inserting) {
                        composerImpl3.createNode(function02);
                    } else {
                        composerImpl3.useNode();
                    }
                    Updater.m336setimpl(composer2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m336setimpl(composer2, currentCompositionLocalScope2, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function22);
                    }
                    Updater.m336setimpl(composer2, materializeModifier2, ComposeUiNode.Companion.SetModifier);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    SeslTopAppBarTemplate$ActionItemScope.Companion.getClass();
                    Function3.this.invoke(SeslTopAppBarTemplate$ActionItemScope.instance, composer2, 0);
                    composableLambdaImpl3.invoke(composer2, 0);
                    composerImpl3.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            SpacerKt.Spacer(composerImpl, SizeKt.m143width3ABfNKs(modifier2, ActionSlotInsetEnd));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.template.SeslTopAppBarTemplate$ActionScope$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Integer) obj3).getClass();
                    SeslTopAppBarTemplate$ActionScope.Companion companion = SeslTopAppBarTemplate$ActionScope.Companion;
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslTopAppBarTemplate$ActionScope.this.SeslTopAppBarActionLayout(composableLambdaImpl, composableLambdaImpl2, composableLambdaImpl3, modifier2, composer2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
