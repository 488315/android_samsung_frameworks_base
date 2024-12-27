package com.android.systemui.keyboard.stickykeys.ui.view;

import android.content.Context;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.CornerBasedShape;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.Shapes;
import androidx.compose.material3.ShapesKt;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.OpaqueKey;
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
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.ComposeView;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.keyboard.stickykeys.shared.model.Locked;
import com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey;
import com.android.systemui.keyboard.stickykeys.ui.viewmodel.StickyKeysIndicatorViewModel;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

public abstract class StickyKeysIndicatorKt {
    public static final void StickyKeysIndicator(final StickyKeysIndicatorViewModel stickyKeysIndicatorViewModel, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-322293386);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        StickyKeysIndicator((Map) FlowExtKt.collectAsStateWithLifecycle(stickyKeysIndicatorViewModel.indicatorContent, MapsKt__MapsKt.emptyMap(), composerImpl, 56).getValue(), null, composerImpl, 8, 2);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$StickyKeysIndicator$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    StickyKeysIndicatorKt.StickyKeysIndicator(StickyKeysIndicatorViewModel.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0075  */
    /* renamed from: access$StickyKeyText-GLLOLKI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m1946access$StickyKeyTextGLLOLKI(final com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey r32, final boolean r33, androidx.compose.ui.Modifier r34, androidx.compose.runtime.Composer r35, final int r36, final int r37) {
        /*
            Method dump skipped, instructions count: 257
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt.m1946access$StickyKeyTextGLLOLKI(com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey, boolean, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final ComposeView createStickyKeyIndicatorView(Context context, final StickyKeysIndicatorViewModel stickyKeysIndicatorViewModel) {
        ComposeView composeView = new ComposeView(context, null, 0, 6, null);
        composeView.setContent(new ComposableLambdaImpl(1769291460, true, new Function2() { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$createStickyKeyIndicatorView$1$1
            {
                super(2);
            }

            /* JADX WARN: Type inference failed for: r4v5, types: [com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$createStickyKeyIndicatorView$1$1$1, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey = ComposerKt.invocation;
                final StickyKeysIndicatorViewModel stickyKeysIndicatorViewModel2 = StickyKeysIndicatorViewModel.this;
                PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(474739514, composer, new Function2() { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$createStickyKeyIndicatorView$1$1.1
                    {
                        super(2);
                    }

                    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$createStickyKeyIndicatorView$1$1$1$1, kotlin.jvm.internal.Lambda] */
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        Composer composer2 = (Composer) obj3;
                        if ((((Number) obj4).intValue() & 11) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        OpaqueKey opaqueKey2 = ComposerKt.invocation;
                        MaterialTheme.INSTANCE.getClass();
                        ProvidedValue defaultProvidedValue$runtime_release = ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m383boximpl(MaterialTheme.getColorScheme(composer2).onSurfaceVariant));
                        final StickyKeysIndicatorViewModel stickyKeysIndicatorViewModel3 = StickyKeysIndicatorViewModel.this;
                        CompositionLocalKt.CompositionLocalProvider(defaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(2070702074, composer2, new Function2() { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt.createStickyKeyIndicatorView.1.1.1.1
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj5, Object obj6) {
                                Composer composer3 = (Composer) obj5;
                                if ((((Number) obj6).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                StickyKeysIndicatorKt.StickyKeysIndicator(StickyKeysIndicatorViewModel.this, composer3, 8);
                                return Unit.INSTANCE;
                            }
                        }), composer2, 56);
                        return Unit.INSTANCE;
                    }
                }), composer, 48, 1);
                return Unit.INSTANCE;
            }
        }));
        return composeView;
    }

    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$StickyKeysIndicator$2, kotlin.jvm.internal.Lambda] */
    public static final void StickyKeysIndicator(final Map map, Modifier modifier, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(371615851);
        Modifier modifier2 = (i2 & 2) != 0 ? Modifier.Companion : modifier;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        MaterialTheme.INSTANCE.getClass();
        long j = MaterialTheme.getColorScheme(composerImpl).inverseSurface;
        CornerBasedShape cornerBasedShape = ((Shapes) composerImpl.consume(ShapesKt.LocalShapes)).medium;
        Dp.Companion companion = Dp.Companion;
        final Modifier modifier3 = modifier2;
        SurfaceKt.m248SurfaceT9BRK9s(SizeKt.m119width3ABfNKs(SizeKt.m110heightInVpY3zN4$default(modifier2, 84, 0.0f, 2), 96), cornerBasedShape, j, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-1119523152, composerImpl, new Function2() { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$StickyKeysIndicator$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer2 = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                Alignment.Companion.getClass();
                BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                Arrangement.INSTANCE.getClass();
                Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
                Dp.Companion companion2 = Dp.Companion;
                Modifier m102padding3ABfNKs = PaddingKt.m102padding3ABfNKs(Modifier.Companion, 16);
                Map<ModifierKey, Locked> map2 = map;
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, horizontal, composer2, 54);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, m102padding3ABfNKs);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (!(composerImpl3.applier instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl3.startReusableNode();
                if (composerImpl3.inserting) {
                    composerImpl3.createNode(function0);
                } else {
                    composerImpl3.useNode();
                }
                Updater.m276setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m276setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                }
                Updater.m276setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                composerImpl3.startReplaceGroup(1398080713);
                for (Map.Entry<ModifierKey, Locked> entry : map2.entrySet()) {
                    ModifierKey key = entry.getKey();
                    boolean z = entry.getValue().locked;
                    composerImpl3.startMovableGroup(-1613251928, key);
                    StickyKeysIndicatorKt.m1946access$StickyKeyTextGLLOLKI(key, z, null, composer2, 0, 4);
                    composerImpl3.end(false);
                }
                composerImpl3.end(false);
                composerImpl3.end(true);
                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                return Unit.INSTANCE;
            }
        }), composerImpl, 12582912, 120);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$StickyKeysIndicator$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    StickyKeysIndicatorKt.StickyKeysIndicator(map, modifier3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
