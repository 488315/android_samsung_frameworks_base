package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Start$1;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScope;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.FlowLayoutKt;
import androidx.compose.foundation.layout.FlowRowScope;
import androidx.compose.foundation.layout.FlowRowScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.DividerKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SearchBarColors;
import androidx.compose.material3.SearchBarDefaults;
import androidx.compose.material3.SearchBar_androidKt;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.TopAppBarColors;
import androidx.compose.material3.TopAppBarDefaults;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.ui.composable.ShortcutKey;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class ShortcutHelperKt {
    public static final void CategoriesPanelSinglePane(final List list, Composer composer, final int i) {
        Shape shape;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1222734555);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(1462746304);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue = SnapshotStateKt.mutableStateOf(null, StructuralEqualityPolicy.INSTANCE);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final MutableState mutableState = (MutableState) rememberedValue;
        composerImpl.end(false);
        Arrangement arrangement = Arrangement.INSTANCE;
        Dp.Companion companion = Dp.Companion;
        arrangement.getClass();
        Arrangement.SpacedAligned m75spacedBy0680j_4 = Arrangement.m75spacedBy0680j_4(2);
        Modifier.Companion companion2 = Modifier.Companion;
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m75spacedBy0680j_4, Alignment.Companion.Start, composerImpl, 6);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion2);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        composerImpl.startReplaceGroup(1462746445);
        int size = list.size();
        for (int i3 = 0; i3 < size; i3++) {
            final ShortcutHelperCategory shortcutHelperCategory = (ShortcutHelperCategory) list.get(i3);
            final boolean areEqual = Intrinsics.areEqual((ShortcutHelperCategory) mutableState.getValue(), shortcutHelperCategory);
            if (i3 == 0) {
                ShortcutHelper$Shapes.INSTANCE.getClass();
                shape = ShortcutHelper$Shapes.singlePaneFirstCategory;
            } else if (i3 == CollectionsKt__CollectionsKt.getLastIndex(list)) {
                ShortcutHelper$Shapes.INSTANCE.getClass();
                shape = ShortcutHelper$Shapes.singlePaneLastCategory;
            } else {
                ShortcutHelper$Shapes.INSTANCE.getClass();
                shape = ShortcutHelper$Shapes.singlePaneCategory;
            }
            CategoryItemSinglePane(shortcutHelperCategory, areEqual, new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoriesPanelSinglePane$1$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    mutableState.setValue(areEqual ? null : shortcutHelperCategory);
                    return Unit.INSTANCE;
                }
            }, shape, composerImpl, 8);
        }
        composerImpl.end(false);
        composerImpl.end(true);
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoriesPanelSinglePane$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.CategoriesPanelSinglePane(list, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void CategoriesPanelTwoPane(final List list, final ShortcutHelperCategory shortcutHelperCategory, final Function1 function1, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-355344232);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier.Companion companion = Modifier.Companion;
        Arrangement.INSTANCE.getClass();
        Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        composerImpl.startReplaceGroup(-120464810);
        int size = list.size();
        for (int i3 = 0; i3 < size; i3++) {
            final ShortcutHelperCategory shortcutHelperCategory2 = (ShortcutHelperCategory) list.get(i3);
            CategoryItemTwoPane(StringResources_androidKt.stringResource(shortcutHelperCategory2.labelResId, composerImpl), shortcutHelperCategory2.icon, Intrinsics.areEqual(shortcutHelperCategory, shortcutHelperCategory2), new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoriesPanelTwoPane$1$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Function1.this.invoke(shortcutHelperCategory2);
                    return Unit.INSTANCE;
                }
            }, null, composerImpl, 0, 16);
        }
        composerImpl.end(false);
        composerImpl.end(true);
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoriesPanelTwoPane$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.CategoriesPanelTwoPane(list, shortcutHelperCategory, function1, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoryItemSinglePane$1, kotlin.jvm.internal.Lambda] */
    public static final void CategoryItemSinglePane(final ShortcutHelperCategory shortcutHelperCategory, final boolean z, final Function0 function0, final Shape shape, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1597101201);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        MaterialTheme.INSTANCE.getClass();
        SurfaceKt.m250Surfaceo_FOJdg(function0, null, false, shape, MaterialTheme.getColorScheme(composerImpl).surfaceBright, 0L, 0.0f, null, null, ComposableLambdaKt.rememberComposableLambda(-1716585596, composerImpl, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoryItemSinglePane$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Type inference failed for: r0v15, types: [com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoryItemSinglePane$1$1$2, kotlin.jvm.internal.Lambda] */
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
                boolean z2 = z;
                final ShortcutHelperCategory shortcutHelperCategory2 = shortcutHelperCategory;
                Modifier.Companion companion = Modifier.Companion;
                Arrangement.INSTANCE.getClass();
                Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                Alignment.Companion.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer2, 0);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                boolean z3 = composerImpl3.applier instanceof Applier;
                if (!z3) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl3.startReusableNode();
                if (composerImpl3.inserting) {
                    composerImpl3.createNode(function02);
                } else {
                    composerImpl3.useNode();
                }
                Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                Updater.m276setimpl(composer2, columnMeasurePolicy, function2);
                Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m276setimpl(composer2, currentCompositionLocalScope, function22);
                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                }
                Function2 function24 = ComposeUiNode.Companion.SetModifier;
                Updater.m276setimpl(composer2, materializeModifier, function24);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                Dp.Companion companion2 = Dp.Companion;
                float f = 16;
                Modifier m104paddingVpY3zN4$default = PaddingKt.m104paddingVpY3zN4$default(SizeKt.m110heightInVpY3zN4$default(SizeKt.fillMaxWidth(companion, 1.0f), 88, 0.0f, 2), f, 0.0f, 2);
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composer2, 48);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composer2, m104paddingVpY3zN4$default);
                if (!z3) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl3.startReusableNode();
                if (composerImpl3.inserting) {
                    composerImpl3.createNode(function02);
                } else {
                    composerImpl3.useNode();
                }
                Updater.m276setimpl(composer2, rowMeasurePolicy, function2);
                Updater.m276setimpl(composer2, currentCompositionLocalScope2, function22);
                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function23);
                }
                Updater.m276setimpl(composer2, materializeModifier2, function24);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                IconKt.m226Iconww6aTOc(shortcutHelperCategory2.icon, (String) null, (Modifier) null, 0L, composer2, 48, 12);
                SpacerKt.Spacer(composer2, SizeKt.m119width3ABfNKs(companion, f));
                TextKt.m257Text4IGK_g(StringResources_androidKt.stringResource(shortcutHelperCategory2.labelResId, composer2), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer2, 0, 0, 131070);
                SpacerKt.Spacer(composer2, rowScopeInstance.weight(companion, 1.0f, true));
                ShortcutHelperKt.access$RotatingExpandCollapseIcon(z2, composer2, 0);
                composerImpl3.end(true);
                AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance, z2, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-1667214474, composer2, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoryItemSinglePane$1$1$2
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj3, Object obj4, Object obj5) {
                        ((Number) obj5).intValue();
                        OpaqueKey opaqueKey3 = ComposerKt.invocation;
                        ShortcutHelperKt.access$ShortcutCategoryDetailsSinglePane(ShortcutHelperCategory.this, (Composer) obj4, 8);
                        return Unit.INSTANCE;
                    }
                }), composer2, 1572870, 30);
                composerImpl3.end(true);
                return Unit.INSTANCE;
            }
        }), composerImpl, ((i >> 6) & 14) | (i & 7168), 998);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoryItemSinglePane$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.CategoryItemSinglePane(ShortcutHelperCategory.this, z, function0, shape, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00bd, code lost:
    
        if ((r41 & 16) != 0) goto L68;
     */
    /* JADX WARN: Type inference failed for: r4v24, types: [com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoryItemTwoPane$2, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void CategoryItemTwoPane(final java.lang.String r34, final androidx.compose.ui.graphics.vector.ImageVector r35, final boolean r36, final kotlin.jvm.functions.Function0 r37, androidx.compose.material3.NavigationDrawerItemColors r38, androidx.compose.runtime.Composer r39, final int r40, final int r41) {
        /*
            Method dump skipped, instructions count: 388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.CategoryItemTwoPane(java.lang.String, androidx.compose.ui.graphics.vector.ImageVector, boolean, kotlin.jvm.functions.Function0, androidx.compose.material3.NavigationDrawerItemColors, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0023, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void EndSidePanel(final androidx.compose.ui.Modifier r12, final com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperCategory r13, androidx.compose.runtime.Composer r14, final int r15) {
        /*
            androidx.compose.runtime.ComposerImpl r14 = (androidx.compose.runtime.ComposerImpl) r14
            r0 = -98111080(0xfffffffffa26f198, float:-2.1670535E35)
            r14.startRestartGroup(r0)
            androidx.compose.runtime.OpaqueKey r0 = androidx.compose.runtime.ComposerKt.invocation
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.LocalView
            java.lang.Object r0 = r14.consume(r0)
            android.view.View r0 = (android.view.View) r0
            boolean r1 = r14.changed(r0)
            java.lang.Object r2 = r14.rememberedValue()
            if (r1 != 0) goto L25
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r1) goto L2d
        L25:
            androidx.compose.ui.platform.NestedScrollInteropConnection r2 = new androidx.compose.ui.platform.NestedScrollInteropConnection
            r2.<init>(r0)
            r14.updateRememberedValue(r2)
        L2d:
            androidx.compose.ui.platform.NestedScrollInteropConnection r2 = (androidx.compose.ui.platform.NestedScrollInteropConnection) r2
            r0 = 0
            androidx.compose.ui.Modifier r0 = androidx.compose.ui.input.nestedscroll.NestedScrollModifierKt.nestedScroll(r12, r2, r0)
            com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$EndSidePanel$1 r8 = new com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$EndSidePanel$1
            r8.<init>()
            r6 = 0
            r7 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r10 = 0
            r11 = 254(0xfe, float:3.56E-43)
            r9 = r14
            androidx.compose.foundation.lazy.LazyDslKt.LazyColumn(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            androidx.compose.runtime.RecomposeScopeImpl r14 = r14.endRestartGroup()
            if (r14 == 0) goto L54
            com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$EndSidePanel$2 r0 = new com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$EndSidePanel$2
            r0.<init>()
            r14.block = r0
        L54:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.EndSidePanel(androidx.compose.ui.Modifier, com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperCategory, androidx.compose.runtime.Composer, int):void");
    }

    public static final void KeyboardSettings(final Function0 function0, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1982364495);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Dp.Companion companion = Dp.Companion;
            RoundedCornerShape m151RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(24);
            Color.Companion.getClass();
            long j = Color.Transparent;
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(SemanticsModifierKt.semantics(Modifier.Companion, false, new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$KeyboardSettings$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Role.Companion.getClass();
                    SemanticsPropertiesKt.m623setRolekuIjeqM((SemanticsPropertyReceiver) obj, 0);
                    return Unit.INSTANCE;
                }
            }), 1.0f);
            ComposableSingletons$ShortcutHelperKt.INSTANCE.getClass();
            composerImpl = composerImpl2;
            SurfaceKt.m250Surfaceo_FOJdg(function0, fillMaxWidth, false, m151RoundedCornerShape0680j_4, j, 0L, 0.0f, null, null, ComposableSingletons$ShortcutHelperKt.f43lambda5, composerImpl2, (i2 & 14) | 24576, 996);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$KeyboardSettings$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.KeyboardSettings(Function0.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutDescriptionText(final int i, final int i2, Composer composer, Modifier modifier, final Shortcut shortcut) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1427939411);
        Modifier modifier2 = (i2 & 2) != 0 ? Modifier.Companion : modifier;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        String str = shortcut.label;
        MaterialTheme.INSTANCE.getClass();
        final Modifier modifier3 = modifier2;
        TextKt.m257Text4IGK_g(str, modifier2, MaterialTheme.getColorScheme(composerImpl).onSurface, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).bodyMedium, composerImpl, i & 112, 0, 65528);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutDescriptionText$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    Shortcut shortcut2 = Shortcut.this;
                    Modifier modifier4 = modifier3;
                    ShortcutHelperKt.ShortcutDescriptionText(RecomposeScopeImplKt.updateChangedFlags(i | 1), i2, (Composer) obj, modifier4, shortcut2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0061  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ShortcutHelper(final kotlin.jvm.functions.Function0 r14, androidx.compose.ui.Modifier r15, java.util.List r16, kotlin.jvm.functions.Function2 r17, androidx.compose.runtime.Composer r18, final int r19, final int r20) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.ShortcutHelper(kotlin.jvm.functions.Function0, androidx.compose.ui.Modifier, java.util.List, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void ShortcutHelperSinglePane(Modifier modifier, final List list, final Function0 function0, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(337956014);
        final Modifier modifier2 = (i2 & 1) != 0 ? Modifier.Companion : modifier;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier verticalScroll$default = ScrollKt.verticalScroll$default(modifier2.then(SizeKt.FillWholeMaxSize), ScrollKt.rememberScrollState(composerImpl), false, 14);
        float f = 16;
        Dp.Companion companion = Dp.Companion;
        Modifier m106paddingqDBjuR0$default = PaddingKt.m106paddingqDBjuR0$default(verticalScroll$default, f, 26, f, 0.0f, 8);
        Arrangement.INSTANCE.getClass();
        Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
        int i3 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m106paddingqDBjuR0$default);
        ComposeUiNode.Companion.getClass();
        Function0 function02 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function02);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        TitleBar(0, composerImpl);
        Modifier.Companion companion2 = Modifier.Companion;
        SpacerKt.Spacer(composerImpl, SizeKt.m108height3ABfNKs(companion2, 6));
        ShortcutsSearchBar(0, composerImpl);
        SpacerKt.Spacer(composerImpl, SizeKt.m108height3ABfNKs(companion2, f));
        CategoriesPanelSinglePane(list, composerImpl, 8);
        SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 1.0f, true));
        KeyboardSettings(function0, composerImpl, (i >> 6) & 14);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutHelperSinglePane$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.ShortcutHelperSinglePane(Modifier.this, list, function0, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutHelperTwoPane(Modifier modifier, final List list, final Function0 function0, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1643211822);
        Modifier modifier2 = (i2 & 1) != 0 ? Modifier.Companion : modifier;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(-2037308725);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = SnapshotStateKt.mutableStateOf(CollectionsKt___CollectionsKt.first(list), StructuralEqualityPolicy.INSTANCE);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final MutableState mutableState = (MutableState) rememberedValue;
        composerImpl.end(false);
        FillElement fillElement = SizeKt.FillWholeMaxSize;
        Modifier then = modifier2.then(fillElement);
        float f = 24;
        Dp.Companion companion = Dp.Companion;
        Modifier m106paddingqDBjuR0$default = PaddingKt.m106paddingqDBjuR0$default(then, f, 26, f, 0.0f, 8);
        Arrangement.INSTANCE.getClass();
        Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
        int i3 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m106paddingqDBjuR0$default);
        ComposeUiNode.Companion.getClass();
        Function0 function02 = ComposeUiNode.Companion.Constructor;
        boolean z = composerImpl.applier instanceof Applier;
        if (!z) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function02);
        } else {
            composerImpl.useNode();
        }
        Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
        Updater.m276setimpl(composerImpl, columnMeasurePolicy, function2);
        Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, function22);
        Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function23);
        }
        Function2 function24 = ComposeUiNode.Companion.SetModifier;
        Updater.m276setimpl(composerImpl, materializeModifier, function24);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        TitleBar(0, composerImpl);
        Modifier.Companion companion2 = Modifier.Companion;
        SpacerKt.Spacer(composerImpl, SizeKt.m108height3ABfNKs(companion2, 12));
        Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion2, 1.0f);
        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, Alignment.Companion.Top, composerImpl, 0);
        int i4 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, fillMaxWidth);
        if (!z) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function02);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, rowMeasurePolicy, function2);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope2, function22);
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i4))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i4, composerImpl, i4, function23);
        }
        Updater.m276setimpl(composerImpl, materializeModifier2, function24);
        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
        Modifier fillMaxWidth2 = SizeKt.fillMaxWidth(companion2, 0.32f);
        ShortcutHelperCategory shortcutHelperCategory = (ShortcutHelperCategory) mutableState.getValue();
        composerImpl.startReplaceGroup(642873964);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutHelperTwoPane$1$1$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    MutableState.this.setValue((ShortcutHelperCategory) obj);
                    return Unit.INSTANCE;
                }
            };
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        StartSidePanel(fillMaxWidth2, list, function0, shortcutHelperCategory, (Function1) rememberedValue2, composerImpl, (i & 896) | 28742);
        SpacerKt.Spacer(composerImpl, SizeKt.m119width3ABfNKs(companion2, f));
        EndSidePanel(fillElement, (ShortcutHelperCategory) mutableState.getValue(), composerImpl, 70);
        composerImpl.end(true);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier3 = modifier2;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutHelperTwoPane$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.ShortcutHelperTwoPane(Modifier.this, list, function0, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutKeyCombinations$1, kotlin.jvm.internal.Lambda] */
    public static final void ShortcutKeyCombinations(final int i, final int i2, Composer composer, final Modifier modifier, final Shortcut shortcut) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1686592245);
        if ((i2 & 1) != 0) {
            modifier = Modifier.Companion;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Arrangement arrangement = Arrangement.INSTANCE;
        Dp.Companion companion = Dp.Companion;
        arrangement.getClass();
        FlowLayoutKt.FlowRow(modifier, null, Arrangement.m75spacedBy0680j_4(8), 0, 0, null, ComposableLambdaKt.rememberComposableLambda(1830141616, composerImpl, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutKeyCombinations$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                FlowRowScope flowRowScope = (FlowRowScope) obj;
                Composer composer2 = (Composer) obj2;
                int intValue = ((Number) obj3).intValue();
                if ((intValue & 14) == 0) {
                    intValue |= ((ComposerImpl) composer2).changed(flowRowScope) ? 4 : 2;
                }
                if ((intValue & 91) == 18) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                int i3 = 0;
                for (Object obj4 : Shortcut.this.commands) {
                    int i4 = i3 + 1;
                    if (i3 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    ShortcutCommand shortcutCommand = (ShortcutCommand) obj4;
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    composerImpl3.startReplaceGroup(-101568143);
                    if (i3 > 0) {
                        Dp.Companion companion2 = Dp.Companion;
                        ShortcutHelperKt.m1944access$ShortcutOrSeparatorziNgDLE(flowRowScope, 16, composerImpl3, (intValue & 14) | 48);
                    }
                    composerImpl3.end(false);
                    ShortcutHelperKt.access$ShortcutCommand(shortcutCommand, composerImpl3, 8);
                    i3 = i4;
                }
                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                return Unit.INSTANCE;
            }
        }), composerImpl, (i & 14) | 1573248, 58);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutKeyCombinations$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    Modifier modifier2 = Modifier.this;
                    Shortcut shortcut2 = shortcut;
                    ShortcutHelperKt.ShortcutKeyCombinations(RecomposeScopeImplKt.updateChangedFlags(i | 1), i2, (Composer) obj, modifier2, shortcut2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutKeyContainer(final Function3 function3, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1258689008);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(function3) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Dp.Companion companion = Dp.Companion;
            Modifier m108height3ABfNKs = SizeKt.m108height3ABfNKs(Modifier.Companion, 36);
            MaterialTheme.INSTANCE.getClass();
            Modifier m24backgroundbw27NRU = BackgroundKt.m24backgroundbw27NRU(m108height3ABfNKs, MaterialTheme.getColorScheme(composerImpl).surfaceContainer, RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(12));
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int i3 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m24backgroundbw27NRU);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (!(composerImpl.applier instanceof Applier)) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m276setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function2);
            }
            Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            function3.invoke(BoxScopeInstance.INSTANCE, composerImpl, Integer.valueOf(((i2 << 3) & 112) | 6));
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutKeyContainer$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.ShortcutKeyContainer(Function3.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutSinglePane(final Shortcut shortcut, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(62563661);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier.Companion companion = Modifier.Companion;
        Dp.Companion companion2 = Dp.Companion;
        Modifier m104paddingVpY3zN4$default = PaddingKt.m104paddingVpY3zN4$default(companion, 0.0f, 24, 1);
        Arrangement.INSTANCE.getClass();
        Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m104paddingVpY3zN4$default);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        ShortcutDescriptionText(8, 2, composerImpl, null, shortcut);
        SpacerKt.Spacer(composerImpl, SizeKt.m108height3ABfNKs(companion, 12));
        ShortcutKeyCombinations(64, 1, composerImpl, null, shortcut);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutSinglePane$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.ShortcutSinglePane(Shortcut.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutSubCategorySinglePane(final SubCategory subCategory, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1808739865);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        SubCategoryTitle(subCategory.label, composerImpl, 0);
        List list = subCategory.shortcuts;
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            Shortcut shortcut = (Shortcut) list.get(i2);
            composerImpl.startReplaceGroup(1841887992);
            if (i2 > 0) {
                DividerKt.m218HorizontalDivider9IZ8Weo(null, 0.0f, 0L, composerImpl, 0, 7);
            }
            composerImpl.end(false);
            ShortcutSinglePane(shortcut, composerImpl, 8);
        }
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutSubCategorySinglePane$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.ShortcutSubCategorySinglePane(SubCategory.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutsSearchBar(final int i, Composer composer) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(181994011);
        if (i == 0 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl2.startReplaceGroup(-1958019155);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf("", StructuralEqualityPolicy.INSTANCE);
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            final MutableState mutableState = (MutableState) rememberedValue;
            composerImpl2.end(false);
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            SearchBarDefaults searchBarDefaults = SearchBarDefaults.INSTANCE;
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl2).surfaceBright;
            searchBarDefaults.getClass();
            SearchBarColors m234colorsdgg9oW8 = SearchBarDefaults.m234colorsdgg9oW8(j, composerImpl2, 0, 2);
            String str = (String) mutableState.getValue();
            composerImpl2.startReplaceGroup(-1958018863);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutsSearchBar$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        MutableState.this.setValue((String) obj);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(rememberedValue2);
            }
            composerImpl2.end(false);
            ShortcutHelperKt$ShortcutsSearchBar$2 shortcutHelperKt$ShortcutsSearchBar$2 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutsSearchBar$2
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return Unit.INSTANCE;
                }
            };
            ShortcutHelperKt$ShortcutsSearchBar$3 shortcutHelperKt$ShortcutsSearchBar$3 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutsSearchBar$3
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    ((Boolean) obj).booleanValue();
                    return Unit.INSTANCE;
                }
            };
            ComposableSingletons$ShortcutHelperKt.INSTANCE.getClass();
            composerImpl = composerImpl2;
            SearchBar_androidKt.m236SearchBarWuY5d9Q(str, (Function1) rememberedValue2, shortcutHelperKt$ShortcutsSearchBar$2, false, shortcutHelperKt$ShortcutsSearchBar$3, fillMaxWidth, false, ComposableSingletons$ShortcutHelperKt.f40lambda2, ComposableSingletons$ShortcutHelperKt.f41lambda3, null, null, m234colorsdgg9oW8, 0.0f, 0.0f, null, null, ComposableSingletons$ShortcutHelperKt.f42lambda4, composerImpl, 113470896, 1572864, 63040);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutsSearchBar$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.ShortcutsSearchBar(RecomposeScopeImplKt.updateChangedFlags(i | 1), (Composer) obj);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void StartSidePanel(final Modifier modifier, final List list, final Function0 function0, final ShortcutHelperCategory shortcutHelperCategory, final Function1 function1, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(195529692);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Arrangement.INSTANCE.getClass();
        Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
        ComposeUiNode.Companion.getClass();
        Function0 function02 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function02);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        ShortcutsSearchBar(0, composerImpl);
        Modifier.Companion companion = Modifier.Companion;
        Dp.Companion companion2 = Dp.Companion;
        SpacerKt.Spacer(composerImpl, SizeKt.m110heightInVpY3zN4$default(companion, 16, 0.0f, 2));
        int i3 = i >> 6;
        CategoriesPanelTwoPane(list, shortcutHelperCategory, function1, composerImpl, (i3 & 896) | 72);
        SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion, 1.0f, true));
        KeyboardSettings(function0, composerImpl, i3 & 14);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$StartSidePanel$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.StartSidePanel(Modifier.this, list, function0, shortcutHelperCategory, function1, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SubCategoryTitle(final String str, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(2013630406);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            TextKt.m257Text4IGK_g(str, null, MaterialTheme.getColorScheme(composerImpl2).primary, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl2).titleSmall, composerImpl, i2 & 14, 0, 65530);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$SubCategoryTitle$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.SubCategoryTitle(str, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void TitleBar(final int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2138769512);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            TopAppBarDefaults topAppBarDefaults = TopAppBarDefaults.INSTANCE;
            Color.Companion.getClass();
            long j = Color.Transparent;
            topAppBarDefaults.getClass();
            long j2 = Color.Unspecified;
            MaterialTheme.INSTANCE.getClass();
            TopAppBarColors defaultCenterAlignedTopAppBarColors$material3_release = TopAppBarDefaults.getDefaultCenterAlignedTopAppBarColors$material3_release(MaterialTheme.getColorScheme(composerImpl));
            if (j == 16) {
                j = defaultCenterAlignedTopAppBarColors$material3_release.containerColor;
            }
            long j3 = j;
            long j4 = j2 != 16 ? j2 : defaultCenterAlignedTopAppBarColors$material3_release.scrolledContainerColor;
            long j5 = j2 != 16 ? j2 : defaultCenterAlignedTopAppBarColors$material3_release.navigationIconContentColor;
            long j6 = j2 != 16 ? j2 : defaultCenterAlignedTopAppBarColors$material3_release.titleContentColor;
            if (j2 == 16) {
                j2 = defaultCenterAlignedTopAppBarColors$material3_release.actionIconContentColor;
            }
            TopAppBarColors topAppBarColors = new TopAppBarColors(j3, j4, j5, j6, j2, null);
            ComposableSingletons$ShortcutHelperKt.INSTANCE.getClass();
            AppBarKt.m205CenterAlignedTopAppBarGHTll3U(ComposableSingletons$ShortcutHelperKt.f39lambda1, null, null, null, 0.0f, null, topAppBarColors, null, composerImpl, 6, 190);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$TitleBar$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.TitleBar(RecomposeScopeImplKt.updateChangedFlags(i | 1), (Composer) obj);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$RotatingExpandCollapseIcon(final boolean r8, androidx.compose.runtime.Composer r9, final int r10) {
        /*
            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
            r0 = 1532057329(0x5b5156f1, float:5.8923863E16)
            r9.startRestartGroup(r0)
            r0 = r10 & 14
            r1 = 2
            if (r0 != 0) goto L18
            boolean r0 = r9.changed(r8)
            if (r0 == 0) goto L15
            r0 = 4
            goto L16
        L15:
            r0 = r1
        L16:
            r0 = r0 | r10
            goto L19
        L18:
            r0 = r10
        L19:
            r0 = r0 & 11
            if (r0 != r1) goto L29
            boolean r0 = r9.getSkipping()
            if (r0 != 0) goto L24
            goto L29
        L24:
            r9.skipToGroupEnd()
            goto Lb7
        L29:
            androidx.compose.runtime.OpaqueKey r0 = androidx.compose.runtime.ComposerKt.invocation
            if (r8 == 0) goto L30
            r0 = 1127481344(0x43340000, float:180.0)
            goto L31
        L30:
            r0 = 0
        L31:
            r5 = 3072(0xc00, float:4.305E-42)
            r6 = 22
            r1 = 0
            java.lang.String r2 = "Expand icon rotation animation"
            r3 = 0
            r4 = r9
            androidx.compose.runtime.State r0 = androidx.compose.animation.core.AnimateAsStateKt.animateFloatAsState(r0, r1, r2, r3, r4, r5, r6)
            androidx.compose.ui.Modifier$Companion r1 = androidx.compose.ui.Modifier.Companion
            androidx.compose.material3.MaterialTheme r2 = androidx.compose.material3.MaterialTheme.INSTANCE
            r2.getClass()
            androidx.compose.material3.ColorScheme r2 = androidx.compose.material3.MaterialTheme.getColorScheme(r9)
            long r2 = r2.surfaceContainerHigh
            androidx.compose.foundation.shape.RoundedCornerShape r4 = androidx.compose.foundation.shape.RoundedCornerShapeKt.CircleShape
            androidx.compose.ui.Modifier r1 = androidx.compose.foundation.BackgroundKt.m24backgroundbw27NRU(r1, r2, r4)
            r2 = -1399518786(0xffffffffac9509be, float:-4.2359163E-12)
            r9.startReplaceGroup(r2)
            boolean r2 = r9.changed(r0)
            java.lang.Object r3 = r9.rememberedValue()
            if (r2 != 0) goto L6a
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r2) goto L72
        L6a:
            com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$RotatingExpandCollapseIcon$1$1 r3 = new com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$RotatingExpandCollapseIcon$1$1
            r3.<init>()
            r9.updateRememberedValue(r3)
        L72:
            kotlin.jvm.functions.Function1 r3 = (kotlin.jvm.functions.Function1) r3
            r0 = 0
            r9.end(r0)
            androidx.compose.ui.Modifier r2 = androidx.compose.ui.graphics.GraphicsLayerModifierKt.graphicsLayer(r1, r3)
            androidx.compose.material.icons.Icons r1 = androidx.compose.material.icons.Icons.INSTANCE
            r1.getClass()
            androidx.compose.ui.graphics.vector.ImageVector r1 = androidx.compose.material.icons.filled.ExpandMoreKt.getExpandMore()
            if (r8 == 0) goto L98
            r3 = -1399518620(0xffffffffac950a64, float:-4.2359883E-12)
            r9.startReplaceGroup(r3)
            r3 = 2131956253(0x7f13121d, float:1.9549056E38)
            java.lang.String r3 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r3, r9)
            r9.end(r0)
            goto La8
        L98:
            r3 = -1399518508(0xffffffffac950ad4, float:-4.236037E-12)
            r9.startReplaceGroup(r3)
            r3 = 2131956254(0x7f13121e, float:1.9549059E38)
            java.lang.String r3 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r3, r9)
            r9.end(r0)
        La8:
            androidx.compose.material3.ColorScheme r0 = androidx.compose.material3.MaterialTheme.getColorScheme(r9)
            long r4 = r0.onSurface
            r6 = 0
            r7 = 0
            r0 = r1
            r1 = r3
            r3 = r4
            r5 = r9
            androidx.compose.material3.IconKt.m226Iconww6aTOc(r0, r1, r2, r3, r5, r6, r7)
        Lb7:
            androidx.compose.runtime.RecomposeScopeImpl r9 = r9.endRestartGroup()
            if (r9 == 0) goto Lc4
            com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$RotatingExpandCollapseIcon$2 r0 = new com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$RotatingExpandCollapseIcon$2
            r0.<init>()
            r9.block = r0
        Lc4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.access$RotatingExpandCollapseIcon(boolean, androidx.compose.runtime.Composer, int):void");
    }

    public static final void access$ShortcutCategoryDetailsSinglePane(final ShortcutHelperCategory shortcutHelperCategory, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-918732483);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Dp.Companion companion = Dp.Companion;
        Modifier m104paddingVpY3zN4$default = PaddingKt.m104paddingVpY3zN4$default(Modifier.Companion, 16, 0.0f, 2);
        Arrangement.INSTANCE.getClass();
        Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m104paddingVpY3zN4$default);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        composerImpl.startReplaceGroup(-56121520);
        List list = shortcutHelperCategory.subCategories;
        int size = list.size();
        for (int i3 = 0; i3 < size; i3++) {
            ShortcutSubCategorySinglePane((SubCategory) list.get(i3), composerImpl, 8);
        }
        composerImpl.end(false);
        composerImpl.end(true);
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutCategoryDetailsSinglePane$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.access$ShortcutCategoryDetailsSinglePane(ShortcutHelperCategory.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutCommand$1$1, kotlin.jvm.internal.Lambda] */
    public static final void access$ShortcutCommand(final ShortcutCommand shortcutCommand, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(833482787);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        int i2 = 0;
        for (Object obj : shortcutCommand.keys) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            final ShortcutKey shortcutKey = (ShortcutKey) obj;
            composerImpl.startReplaceGroup(1209369042);
            if (i2 > 0) {
                Dp.Companion companion = Dp.Companion;
                SpacerKt.Spacer(composerImpl, SizeKt.m119width3ABfNKs(Modifier.Companion, 4));
            }
            composerImpl.end(false);
            ShortcutKeyContainer(ComposableLambdaKt.rememberComposableLambda(2095829665, composerImpl, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutCommand$1$1
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj2, Object obj3, Object obj4) {
                    BoxScope boxScope = (BoxScope) obj2;
                    Composer composer2 = (Composer) obj3;
                    int intValue = ((Number) obj4).intValue();
                    if ((intValue & 14) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(boxScope) ? 4 : 2;
                    }
                    if ((intValue & 91) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    ShortcutKey shortcutKey2 = ShortcutKey.this;
                    if (shortcutKey2 instanceof ShortcutKey.Text) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        composerImpl3.startReplaceGroup(1106786101);
                        ShortcutHelperKt.access$ShortcutTextKey(boxScope, (ShortcutKey.Text) ShortcutKey.this, composerImpl3, intValue & 14);
                        composerImpl3.end(false);
                    } else if (shortcutKey2 instanceof ShortcutKey.Icon) {
                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                        composerImpl4.startReplaceGroup(1106786188);
                        ShortcutHelperKt.access$ShortcutIconKey(boxScope, (ShortcutKey.Icon) ShortcutKey.this, composerImpl4, intValue & 14);
                        composerImpl4.end(false);
                    } else {
                        ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                        composerImpl5.startReplaceGroup(1106786222);
                        composerImpl5.end(false);
                    }
                    return Unit.INSTANCE;
                }
            }), composerImpl, 6);
            i2 = i3;
        }
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutCommand$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    ShortcutHelperKt.access$ShortcutCommand(ShortcutCommand.this, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$ShortcutIconKey(final BoxScope boxScope, final ShortcutKey.Icon icon, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(450478271);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(boxScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(icon) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            ImageVector imageVector = icon.value;
            Modifier.Companion companion = Modifier.Companion;
            Alignment.Companion.getClass();
            Dp.Companion companion2 = Dp.Companion;
            IconKt.m226Iconww6aTOc(imageVector, (String) null, PaddingKt.m102padding3ABfNKs(boxScope.align(companion, Alignment.Companion.Center), 6), 0L, composerImpl, 48, 8);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutIconKey$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.access$ShortcutIconKey(BoxScope.this, icon, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: access$ShortcutOrSeparator-ziNgDLE, reason: not valid java name */
    public static final void m1944access$ShortcutOrSeparatorziNgDLE(final FlowRowScope flowRowScope, final float f, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(562023815);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(flowRowScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(f) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier.Companion companion = Modifier.Companion;
            SpacerKt.Spacer(composerImpl, SizeKt.m119width3ABfNKs(companion, f));
            String stringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_key_combinations_or_separator, composerImpl);
            Alignment.Companion.getClass();
            Modifier align = ((FlowRowScopeInstance) flowRowScope).$$delegate_0.align(companion, Alignment.Companion.CenterVertically);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m257Text4IGK_g(stringResource, align, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).titleSmall, composerImpl, 0, 0, 65532);
            composerImpl = composerImpl;
            SpacerKt.Spacer(composerImpl, SizeKt.m119width3ABfNKs(companion, f));
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutOrSeparator$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.m1944access$ShortcutOrSeparatorziNgDLE(FlowRowScope.this, f, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$ShortcutTextKey(final BoxScope boxScope, final ShortcutKey.Text text, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(2118702167);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(boxScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl2.changed(text) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            String str = text.value;
            Modifier.Companion companion = Modifier.Companion;
            Alignment.Companion.getClass();
            Dp.Companion companion2 = Dp.Companion;
            Modifier m104paddingVpY3zN4$default = PaddingKt.m104paddingVpY3zN4$default(boxScope.align(companion, Alignment.Companion.Center), 12, 0.0f, 2);
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            TextKt.m257Text4IGK_g(str, m104paddingVpY3zN4$default, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl2).titleSmall, composerImpl, 0, 0, 65532);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutTextKey$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.access$ShortcutTextKey(BoxScope.this, text, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$ShortcutViewDualPane(final Shortcut shortcut, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(426480262);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier.Companion companion = Modifier.Companion;
        Dp.Companion companion2 = Dp.Companion;
        Modifier m104paddingVpY3zN4$default = PaddingKt.m104paddingVpY3zN4$default(companion, 0.0f, 16, 1);
        Arrangement.INSTANCE.getClass();
        Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
        Alignment.Companion.getClass();
        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Start$1, Alignment.Companion.Top, composerImpl, 0);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m104paddingVpY3zN4$default);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
        ShortcutDescriptionText(8, 0, composerImpl, rowScopeInstance.align(rowScopeInstance.weight(companion, 0.25f, true), Alignment.Companion.CenterVertically), shortcut);
        ShortcutKeyCombinations(64, 0, composerImpl, rowScopeInstance.weight(companion, 0.75f, true), shortcut);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutViewDualPane$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.access$ShortcutViewDualPane(Shortcut.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$SubCategoryContainerDualPane$1, kotlin.jvm.internal.Lambda] */
    public static final void access$SubCategoryContainerDualPane(final SubCategory subCategory, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1182719252);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
        Dp.Companion companion = Dp.Companion;
        RoundedCornerShape m151RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(28);
        MaterialTheme.INSTANCE.getClass();
        SurfaceKt.m248SurfaceT9BRK9s(fillMaxWidth, m151RoundedCornerShape0680j_4, MaterialTheme.getColorScheme(composerImpl).surfaceBright, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-398428921, composerImpl, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$SubCategoryContainerDualPane$1
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
                Modifier.Companion companion2 = Modifier.Companion;
                Dp.Companion companion3 = Dp.Companion;
                float f = 24;
                Modifier m103paddingVpY3zN4 = PaddingKt.m103paddingVpY3zN4(companion2, 32, f);
                SubCategory subCategory2 = SubCategory.this;
                Arrangement.INSTANCE.getClass();
                Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                Alignment.Companion.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer2, 0);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, m103paddingVpY3zN4);
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
                ShortcutHelperKt.SubCategoryTitle(subCategory2.label, composer2, 0);
                SpacerKt.Spacer(composer2, SizeKt.m108height3ABfNKs(companion2, f));
                composerImpl3.startReplaceGroup(-2116586083);
                List list = subCategory2.shortcuts;
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    Shortcut shortcut = (Shortcut) list.get(i2);
                    composerImpl3.startReplaceGroup(1388741494);
                    if (i2 > 0) {
                        DividerKt.m218HorizontalDivider9IZ8Weo(null, 0.0f, 0L, composer2, 0, 7);
                    }
                    composerImpl3.end(false);
                    ShortcutHelperKt.access$ShortcutViewDualPane(shortcut, composer2, 8);
                }
                composerImpl3.end(false);
                composerImpl3.end(true);
                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                return Unit.INSTANCE;
            }
        }), composerImpl, 12582918, 120);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$SubCategoryContainerDualPane$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ShortcutHelperKt.access$SubCategoryContainerDualPane(SubCategory.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
