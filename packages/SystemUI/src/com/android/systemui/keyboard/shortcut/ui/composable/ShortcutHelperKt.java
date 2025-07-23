package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.ScrollingLayoutElement;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.FocusInteractionKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$End$1;
import androidx.compose.foundation.layout.Arrangement$Start$1;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScope;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.FlowLayoutKt;
import androidx.compose.foundation.layout.FlowRowScope;
import androidx.compose.foundation.layout.FlowRowScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.foundation.selection.SelectableKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.CoreTextFieldKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.AddKt;
import androidx.compose.material.icons.filled.DeleteOutlineKt;
import androidx.compose.material.icons.filled.RefreshKt;
import androidx.compose.material.icons.filled.TuneKt;
import androidx.compose.material.icons.outlined.WidgetsKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.DividerKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.MinimumInteractiveModifier;
import androidx.compose.material3.NavigationDrawerItemColors;
import androidx.compose.material3.NavigationDrawerItemDefaults;
import androidx.compose.material3.SearchBarColors;
import androidx.compose.material3.SearchBarDefaults;
import androidx.compose.material3.SearchBarKt;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.TopAppBarColors;
import androidx.compose.material3.TopAppBarDefaults;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.ZIndexModifierKt;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.focus.FocusManager;
import androidx.compose.ui.focus.FocusOwnerImpl;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.KeyEvent;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.key.KeyInputModifierKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.PlatformSpanStyle;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.compose.ui.graphics.painter.DrawablePainterKt;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.shared.model.Shortcut;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCommand;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutSubCategory;
import com.android.systemui.keyboard.shortcut.ui.model.IconSource;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCategoryUi;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutsUiState;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.List;
import java.util.Locale;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ShortcutHelperKt {
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00c8, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0113, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0165, code lost:
    
        if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L79;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ActiveShortcutHelper(com.android.systemui.keyboard.shortcut.ui.model.ShortcutsUiState.Active r24, com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutHelper$1 r25, kotlin.jvm.functions.Function1 r26, kotlin.jvm.functions.Function1 r27, androidx.compose.ui.Modifier r28, kotlin.jvm.functions.Function0 r29, kotlin.jvm.functions.Function1 r30, androidx.compose.runtime.Composer r31, int r32) {
        /*
            Method dump skipped, instructions count: 461
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.ActiveShortcutHelper(com.android.systemui.keyboard.shortcut.ui.model.ShortcutsUiState$Active, com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutHelper$1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int):void");
    }

    public static final void AddShortcutButton(Function0 function0, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(838790044);
        int i2 = i | (composerImpl.changedInstance(function0) ? 4 : 2);
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.AddShortcutButton (ShortcutHelper.kt:739)");
            }
            Dp.Companion companion = Dp.Companion;
            Modifier m139size3ABfNKs = SizeKt.m139size3ABfNKs(Modifier.Companion, 32);
            Color.Companion.getClass();
            long j = Color.Transparent;
            Icons.INSTANCE.getClass();
            IconSource iconSource = new IconSource(AddKt.getAdd(), null, 2, null);
            MaterialTheme.INSTANCE.getClass();
            float f = 0;
            SurfacesKt.m2579ShortcutHelperButton01TuoB8(function0, MaterialTheme.getColorScheme(composerImpl).primary, j, m139size3ABfNKs, RoundedCornerShapeKt.CircleShape, iconSource, null, f, f, false, BorderStrokeKt.m31BorderStrokecXLIe8U(1, MaterialTheme.getColorScheme(composerImpl).outline), StringResources_androidKt.stringResource(R.string.shortcut_helper_add_shortcut_button_label, composerImpl), composerImpl, (i2 & 14) | 113249664, 576);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda18(i, 0, function0);
        }
    }

    public static final void CategoriesPanelSinglePane(final String str, final List list, final ShortcutCategoryType shortcutCategoryType, final Function1 function1, Composer composer, final int i) {
        String str2;
        int i2;
        Shape shape;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-721585285);
        if ((i & 6) == 0) {
            str2 = str;
            i2 = (composerImpl.changed(str2) ? 4 : 2) | i;
        } else {
            str2 = str;
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(list) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= (i & 512) == 0 ? composerImpl.changed(shortcutCategoryType) : composerImpl.changedInstance(shortcutCategoryType) ? 256 : 128;
        }
        int i3 = 2048;
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 2048 : 1024;
        }
        int i4 = i2;
        if ((i4 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.CategoriesPanelSinglePane (ShortcutHelper.kt:254)");
            }
            Arrangement arrangement = Arrangement.INSTANCE;
            Dp.Companion companion = Dp.Companion;
            arrangement.getClass();
            Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(2);
            Modifier.Companion companion2 = Modifier.Companion;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m91spacedBy0680j_4, Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion2);
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
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(-432889622);
            int size = list.size();
            int i5 = 0;
            while (i5 < size) {
                final ShortcutCategoryUi shortcutCategoryUi = (ShortcutCategoryUi) list.get(i5);
                final boolean areEqual = Intrinsics.areEqual(shortcutCategoryType, shortcutCategoryUi.type);
                if (list.size() == 1) {
                    ShortcutHelper$Shapes.INSTANCE.getClass();
                    shape = ShortcutHelper$Shapes.singlePaneSingleCategory;
                } else if (i5 == 0) {
                    ShortcutHelper$Shapes.INSTANCE.getClass();
                    shape = ShortcutHelper$Shapes.singlePaneFirstCategory;
                } else if (i5 == list.size() - 1) {
                    ShortcutHelper$Shapes.INSTANCE.getClass();
                    shape = ShortcutHelper$Shapes.singlePaneLastCategory;
                } else {
                    ShortcutHelper$Shapes.INSTANCE.getClass();
                    shape = ShortcutHelper$Shapes.singlePaneCategory;
                }
                composerImpl.startReplaceGroup(1171482448);
                boolean changed = ((i4 & 7168) == i3) | composerImpl.changed(areEqual) | composerImpl.changedInstance(shortcutCategoryUi);
                Object rememberedValue = composerImpl.rememberedValue();
                if (!changed) {
                    Composer.Companion.getClass();
                    if (rememberedValue != Composer.Companion.Empty) {
                        composerImpl.end(false);
                        CategoryItemSinglePane(str2, shortcutCategoryUi, areEqual, (Function0) rememberedValue, shape, composerImpl, i4 & 14);
                        i5++;
                        i3 = 2048;
                        str2 = str;
                    }
                }
                rememberedValue = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda38
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Function1.this.mo779invoke(areEqual ? null : shortcutCategoryUi.type);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue);
                composerImpl.end(false);
                CategoryItemSinglePane(str2, shortcutCategoryUi, areEqual, (Function0) rememberedValue, shape, composerImpl, i4 & 14);
                i5++;
                i3 = 2048;
                str2 = str;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda39
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    ShortcutHelperKt.CategoriesPanelSinglePane(str, list, shortcutCategoryType, function1, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void CategoriesPanelTwoPane(final List list, final ShortcutCategoryType shortcutCategoryType, final Function1 function1, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(390659852);
        int i2 = 256;
        int i3 = (composerImpl.changedInstance(list) ? 4 : 2) | i | (composerImpl.changed(shortcutCategoryType) ? 32 : 16) | (composerImpl.changedInstance(function1) ? 256 : 128);
        if ((i3 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.CategoriesPanelTwoPane (ShortcutHelper.kt:932)");
            }
            Modifier.Companion companion = Modifier.Companion;
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
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
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(380139923);
            int size = list.size();
            int i4 = 0;
            while (i4 < size) {
                ShortcutCategoryUi shortcutCategoryUi = (ShortcutCategoryUi) list.get(i4);
                int i5 = size;
                String str = shortcutCategoryUi.label;
                boolean areEqual = Intrinsics.areEqual(shortcutCategoryType, shortcutCategoryUi.type);
                composerImpl.startReplaceGroup(-558737014);
                boolean changedInstance = ((i3 & 896) == i2) | composerImpl.changedInstance(shortcutCategoryUi);
                Object rememberedValue = composerImpl.rememberedValue();
                if (!changedInstance) {
                    Composer.Companion.getClass();
                    if (rememberedValue != Composer.Companion.Empty) {
                        composerImpl.end(false);
                        CategoryItemTwoPane(str, shortcutCategoryUi.iconSource, areEqual, (Function0) rememberedValue, null, composerImpl, 0);
                        i4++;
                        size = i5;
                        i2 = 256;
                    }
                }
                rememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda10(function1, shortcutCategoryUi, 2);
                composerImpl.updateRememberedValue(rememberedValue);
                composerImpl.end(false);
                CategoryItemTwoPane(str, shortcutCategoryUi.iconSource, areEqual, (Function0) rememberedValue, null, composerImpl, 0);
                i4++;
                size = i5;
                i2 = 256;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(list, shortcutCategoryType, function1, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda58
                public final /* synthetic */ List f$0;
                public final /* synthetic */ ShortcutCategoryType f$1;
                public final /* synthetic */ Function1 f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    ShortcutHelperKt.CategoriesPanelTwoPane(this.f$0, this.f$1, this.f$2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void CategoryItemSinglePane(final String str, final ShortcutCategoryUi shortcutCategoryUi, final boolean z, Function0 function0, Shape shape, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(18236192);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changedInstance(shortcutCategoryUi) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changedInstance(function0) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(shape) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i2 & 9363) == 9362 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.CategoryItemSinglePane (ShortcutHelper.kt:294)");
            }
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            SurfaceKt.m304Surfaceo_FOJdg(function0, null, false, shape, MaterialTheme.getColorScheme(composerImpl2).surfaceBright, 0L, 0.0f, null, null, ComposableLambdaKt.rememberComposableLambda(986513131, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoryItemSinglePane$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.CategoryItemSinglePane.<anonymous> (ShortcutHelper.kt:296)");
                    }
                    Modifier.Companion companion = Modifier.Companion;
                    Arrangement.INSTANCE.getClass();
                    Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                    Alignment.Companion.getClass();
                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer2, 0);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
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
                    Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                    Updater.m336setimpl(composer2, columnMeasurePolicy, function2);
                    Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                    Updater.m336setimpl(composer2, currentCompositionLocalScope, function22);
                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function23);
                    }
                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                    Updater.m336setimpl(composer2, materializeModifier, function24);
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                    Dp.Companion companion2 = Dp.Companion;
                    float f = 16;
                    Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(SizeKt.m132heightInVpY3zN4$default(SizeKt.fillMaxWidth(companion, 1.0f), 88, 0.0f, 2), f, 0.0f, 2);
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composer2, 48);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl4.currentCompositionLocalScope();
                    Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composer2, m126paddingVpY3zN4$default);
                    composerImpl4.startReusableNode();
                    if (composerImpl4.inserting) {
                        composerImpl4.createNode(function02);
                    } else {
                        composerImpl4.useNode();
                    }
                    Updater.m336setimpl(composer2, rowMeasurePolicy, function2);
                    Updater.m336setimpl(composer2, currentCompositionLocalScope2, function22);
                    if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl4, currentCompositeKeyHash2, function23);
                    }
                    Updater.m336setimpl(composer2, materializeModifier2, function24);
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    Modifier m139size3ABfNKs = SizeKt.m139size3ABfNKs(companion, 24);
                    final ShortcutCategoryUi shortcutCategoryUi2 = shortcutCategoryUi;
                    ShortcutHelperKt.m2576ShortcutCategoryIconww6aTOc(shortcutCategoryUi2.iconSource, m139size3ABfNKs, 0L, composer2, 48, 12);
                    SpacerKt.Spacer(composer2, SizeKt.m143width3ABfNKs(companion, f));
                    TextKt.m316Text4IGK_g(shortcutCategoryUi2.label, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer2, 0, 0, 131070);
                    SpacerKt.Spacer(composer2, rowScopeInstance.weight(companion, 1.0f, true));
                    boolean z2 = z;
                    ShortcutHelperKt.RotatingExpandCollapseIcon(z2, composer2, 0);
                    composerImpl4.end(true);
                    final String str2 = str;
                    AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance, z2, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-1372173895, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoryItemSinglePane$1$1$2
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj3, Object obj4, Object obj5) {
                            Composer composer3 = (Composer) obj4;
                            ((Number) obj5).intValue();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.CategoryItemSinglePane.<anonymous>.<anonymous>.<anonymous> (ShortcutHelper.kt:308)");
                            }
                            ShortcutHelperKt.ShortcutCategoryDetailsSinglePane(str2, shortcutCategoryUi2, composer3, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composer2), composer2, 1572870, 30);
                    composerImpl4.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2), composerImpl, ((i2 >> 9) & 14) | ((i2 >> 3) & 7168), 998);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda16(str, shortcutCategoryUi, z, function0, shape, i, 1);
        }
    }

    public static final void CategoryItemTwoPane(final String str, final IconSource iconSource, final boolean z, final Function0 function0, NavigationDrawerItemColors navigationDrawerItemColors, Composer composer, final int i) {
        int i2;
        final NavigationDrawerItemColors m274colorsoq7We08;
        final NavigationDrawerItemColors navigationDrawerItemColors2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(322846695);
        int i3 = i | (composerImpl.changed(str) ? 4 : 2) | (composerImpl.changedInstance(iconSource) ? 32 : 16) | (composerImpl.changed(z) ? 256 : 128) | (composerImpl.changedInstance(function0) ? 2048 : 1024) | 8192;
        if ((i3 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            navigationDrawerItemColors2 = navigationDrawerItemColors;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                NavigationDrawerItemDefaults navigationDrawerItemDefaults = NavigationDrawerItemDefaults.INSTANCE;
                Color.Companion.getClass();
                long j = Color.Transparent;
                navigationDrawerItemDefaults.getClass();
                i2 = i3 & (-57345);
                m274colorsoq7We08 = NavigationDrawerItemDefaults.m274colorsoq7We08(j, composerImpl);
            } else {
                composerImpl.skipToGroupEnd();
                i2 = i3 & (-57345);
                m274colorsoq7We08 = navigationDrawerItemColors;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.CategoryItemTwoPane (ShortcutHelper.kt:953)");
            }
            Modifier.Companion companion = Modifier.Companion;
            composerImpl.startReplaceGroup(991854767);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda3(9);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            Dp.Companion companion2 = Dp.Companion;
            final Modifier fillMaxWidth = SizeKt.fillMaxWidth(SizeKt.m132heightInVpY3zN4$default(SemanticsModifierKt.semantics(companion, false, (Function1) rememberedValue), 64, 0.0f, 2), 1.0f);
            float f = 28;
            final RoundedCornerShape m186RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(f);
            final long j2 = ((Color) m274colorsoq7We08.containerColor(z, composerImpl, (i2 >> 6) & 14).getValue()).value;
            MaterialTheme.INSTANCE.getClass();
            final InteractionsConfig interactionsConfig = new InteractionsConfig(MaterialTheme.getColorScheme(composerImpl).onSurface, 0.11f, MaterialTheme.getColorScheme(composerImpl).onSurface, 0.15f, MaterialTheme.getColorScheme(composerImpl).secondary, 3, 2, f, 33, 0.0f, 0.0f, 1536, null);
            final ComposableLambdaImpl rememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-2084289772, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$CategoryItemTwoPane$2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.CategoryItemTwoPane.<anonymous> (ShortcutHelper.kt:973)");
                    }
                    Modifier.Companion companion3 = Modifier.Companion;
                    float f2 = 24;
                    Dp.Companion companion4 = Dp.Companion;
                    Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(companion3, f2, 0.0f, 2);
                    Alignment.Companion.getClass();
                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                    Arrangement.INSTANCE.getClass();
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composer2, 48);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, m126paddingVpY3zN4$default);
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
                    Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                    Updater.m336setimpl(composer2, rowMeasurePolicy, function2);
                    Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                    Updater.m336setimpl(composer2, currentCompositionLocalScope, function22);
                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                    }
                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                    Updater.m336setimpl(composer2, materializeModifier, function24);
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    Modifier m139size3ABfNKs = SizeKt.m139size3ABfNKs(companion3, f2);
                    NavigationDrawerItemColors navigationDrawerItemColors3 = NavigationDrawerItemColors.this;
                    boolean z2 = z;
                    ShortcutHelperKt.m2576ShortcutCategoryIconww6aTOc(iconSource, m139size3ABfNKs, ((Color) navigationDrawerItemColors3.iconColor(z2, composer2).getValue()).value, composer2, 432, 0);
                    SpacerKt.Spacer(composer2, SizeKt.m143width3ABfNKs(companion3, 12));
                    Modifier weight = rowScopeInstance.weight(companion3, 1.0f, true);
                    MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composer2, weight);
                    composerImpl3.startReusableNode();
                    if (composerImpl3.inserting) {
                        composerImpl3.createNode(function02);
                    } else {
                        composerImpl3.useNode();
                    }
                    Updater.m336setimpl(composer2, maybeCachedBoxMeasurePolicy, function2);
                    Updater.m336setimpl(composer2, currentCompositionLocalScope2, function22);
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function23);
                    }
                    Updater.m336setimpl(composer2, materializeModifier2, function24);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    long sp = TextUnitKt.getSp(18);
                    long j3 = ((Color) navigationDrawerItemColors3.textColor(z2, composer2).getValue()).value;
                    MaterialTheme.INSTANCE.getClass();
                    TextStyle textStyle = MaterialTheme.getTypography(composer2).titleSmall;
                    Hyphens.Companion.getClass();
                    TextKt.m316Text4IGK_g(str, null, j3, sp, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, TextStyle.m754copyp1EtxEg$default(textStyle, 0L, 0L, null, null, 0L, 0, 0L, null, null, Hyphens.Auto, 12582911), composer2, 3072, 0, 65522);
                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, true, true)) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl);
            composerImpl.startReplaceGroup(-612515667);
            long m258contentColorForek8zF_U = ColorSchemeKt.m258contentColorForek8zF_U(j2, composerImpl);
            final float f2 = 0;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.SelectableShortcutSurface (Surfaces.kt:105)");
            }
            composerImpl.startReplaceGroup(-1355790970);
            composerImpl.startReplaceGroup(-1355790319);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            final MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) rememberedValue2;
            composerImpl.end(false);
            composerImpl.end(false);
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = SurfaceKt.LocalAbsoluteTonalElevation;
            final float f3 = ((Dp) composerImpl.consume(dynamicProvidableCompositionLocal)).value + f2;
            ProvidedValue[] providedValueArr = {ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(m258contentColorForek8zF_U)), dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Dp.m835boximpl(f3))};
            NavigationDrawerItemColors navigationDrawerItemColors3 = m274colorsoq7We08;
            final BorderStroke borderStroke = null;
            final boolean z2 = true;
            CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableLambdaKt.rememberComposableLambda(247649261, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt$SelectableShortcutSurface$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.SelectableShortcutSurface.<anonymous> (Surfaces.kt:113)");
                    }
                    MutableState collectIsFocusedAsState = FocusInteractionKt.collectIsFocusedAsState(MutableInteractionSource.this, composer2, 0);
                    StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                    Modifier then = fillMaxWidth.then(MinimumInteractiveModifier.INSTANCE);
                    long m2582access$surfaceColorAtElevationCLU3JFs = SurfacesKt.m2582access$surfaceColorAtElevationCLU3JFs(j2, f3, composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    Modifier m181selectableO2vRcR0 = SelectableKt.m181selectableO2vRcR0(SurfacesKt.m2581access$surfaceXOJAsU(then, m186RoundedCornerShape0680j_4, m2582access$surfaceColorAtElevationCLU3JFs, borderStroke, ((Density) composerImpl3.consume(CompositionLocalsKt.LocalDensity)).mo57toPx0680j_4(f2)), z, MutableInteractionSource.this, new ShortcutHelperIndication(interactionsConfig), z2, null, function0);
                    if (((Boolean) collectIsFocusedAsState.getValue()).booleanValue()) {
                        m181selectableO2vRcR0 = m181selectableO2vRcR0.then(ZIndexModifierKt.zIndex(Modifier.Companion, 1.0f));
                    }
                    Alignment.Companion.getClass();
                    MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, true);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, m181selectableO2vRcR0);
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
                    Updater.m336setimpl(composerImpl3, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m336setimpl(composerImpl3, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                    }
                    Updater.m336setimpl(composerImpl3, materializeModifier, ComposeUiNode.Companion.SetModifier);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    rememberComposableLambda.invoke(composerImpl3, 0);
                    composerImpl3.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            navigationDrawerItemColors2 = navigationDrawerItemColors3;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(str, iconSource, z, function0, navigationDrawerItemColors2, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda64
                public final /* synthetic */ String f$0;
                public final /* synthetic */ IconSource f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ Function0 f$3;
                public final /* synthetic */ NavigationDrawerItemColors f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Function0 function02 = this.f$3;
                    NavigationDrawerItemColors navigationDrawerItemColors4 = this.f$4;
                    ShortcutHelperKt.CategoryItemTwoPane(this.f$0, this.f$1, this.f$2, function02, navigationDrawerItemColors4, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void CustomizationButtonsContainer(final boolean z, final boolean z2, final Function0 function0, final Function0 function02, final Modifier modifier, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(671928845);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(z2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i2 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.CustomizationButtonsContainer (ShortcutHelper.kt:452)");
            }
            Arrangement.INSTANCE.getClass();
            Arrangement$End$1 arrangement$End$1 = Arrangement.End;
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$End$1, Alignment.Companion.Top, composerImpl, 6);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
            ComposeUiNode.Companion.getClass();
            Function0 function03 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function03);
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
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            if (z) {
                composerImpl.startReplaceGroup(423370877);
                composerImpl.startReplaceGroup(13657489);
                if (z2) {
                    ResetButton(function02, composerImpl, (i2 >> 9) & 14);
                    Dp.Companion companion = Dp.Companion;
                    SpacerKt.Spacer(composerImpl, SizeKt.m143width3ABfNKs(Modifier.Companion, 8));
                }
                composerImpl.end(false);
                DoneButton(function0, composerImpl, (i2 >> 6) & 14);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(423588683);
                CustomizeButton(function0, composerImpl, (i2 >> 6) & 14);
                composerImpl.end(false);
            }
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda50
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Function0 function04 = function02;
                    Modifier modifier2 = modifier;
                    ShortcutHelperKt.CustomizationButtonsContainer(z, z2, function0, function04, modifier2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1, types: [androidx.compose.ui.graphics.painter.Painter, kotlin.jvm.internal.DefaultConstructorMarker] */
    /* JADX WARN: Type inference failed for: r8v11 */
    public static final void CustomizeButton(Function0 function0, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2030817376);
        int i3 = 2;
        if ((i & 6) == 0) {
            i2 = i | (composerImpl.changedInstance(function0) ? 4 : 2);
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.CustomizeButton (ShortcutHelper.kt:480)");
            }
            Dp.Companion companion = Dp.Companion;
            Modifier m132heightInVpY3zN4$default = SizeKt.m132heightInVpY3zN4$default(Modifier.Companion, 40, 0.0f, 2);
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
            Icons.INSTANCE.getClass();
            ImageVector imageVector = TuneKt._tune;
            ?? r8 = 0;
            if (imageVector == null) {
                ImageVector.Builder builder = new ImageVector.Builder("Filled.Tune", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i4 = StrokeJoin.Bevel;
                PathBuilder pathBuilder = new PathBuilder();
                pathBuilder.moveTo(3.0f, 17.0f);
                pathBuilder.verticalLineToRelative(2.0f);
                pathBuilder.horizontalLineToRelative(6.0f);
                pathBuilder.verticalLineToRelative(-2.0f);
                WidgetsKt$$ExternalSyntheticOutline0.m(pathBuilder, 3.0f, 17.0f, 3.0f, 5.0f);
                pathBuilder.verticalLineToRelative(2.0f);
                pathBuilder.horizontalLineToRelative(10.0f);
                pathBuilder.lineTo(13.0f, 5.0f);
                WidgetsKt$$ExternalSyntheticOutline0.m(pathBuilder, 3.0f, 5.0f, 13.0f, 21.0f);
                pathBuilder.verticalLineToRelative(-2.0f);
                pathBuilder.horizontalLineToRelative(8.0f);
                pathBuilder.verticalLineToRelative(-2.0f);
                pathBuilder.horizontalLineToRelative(-8.0f);
                pathBuilder.verticalLineToRelative(-2.0f);
                pathBuilder.horizontalLineToRelative(-2.0f);
                pathBuilder.verticalLineToRelative(6.0f);
                pathBuilder.horizontalLineToRelative(2.0f);
                pathBuilder.close();
                pathBuilder.moveTo(7.0f, 9.0f);
                pathBuilder.verticalLineToRelative(2.0f);
                pathBuilder.lineTo(3.0f, 11.0f);
                pathBuilder.verticalLineToRelative(2.0f);
                pathBuilder.horizontalLineToRelative(4.0f);
                pathBuilder.verticalLineToRelative(2.0f);
                pathBuilder.horizontalLineToRelative(2.0f);
                ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(pathBuilder, 9.0f, 9.0f, 7.0f, 9.0f);
                pathBuilder.moveTo(21.0f, 13.0f);
                pathBuilder.verticalLineToRelative(-2.0f);
                pathBuilder.lineTo(11.0f, 11.0f);
                pathBuilder.verticalLineToRelative(2.0f);
                pathBuilder.horizontalLineToRelative(10.0f);
                pathBuilder.close();
                pathBuilder.moveTo(15.0f, 9.0f);
                pathBuilder.horizontalLineToRelative(2.0f);
                pathBuilder.lineTo(17.0f, 7.0f);
                pathBuilder.horizontalLineToRelative(4.0f);
                pathBuilder.lineTo(21.0f, 5.0f);
                pathBuilder.horizontalLineToRelative(-4.0f);
                pathBuilder.lineTo(17.0f, 3.0f);
                pathBuilder.horizontalLineToRelative(-2.0f);
                pathBuilder.verticalLineToRelative(6.0f);
                pathBuilder.close();
                builder.m565addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i4, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVector = builder.build();
                TuneKt._tune = imageVector;
                i3 = 2;
                r8 = 0;
            }
            SurfacesKt.m2579ShortcutHelperButton01TuoB8(function0, MaterialTheme.getColorScheme(composerImpl).onSecondaryContainer, j, m132heightInVpY3zN4$default, null, new IconSource(imageVector, r8, i3, r8), StringResources_androidKt.stringResource(R.string.shortcut_helper_customize_button_text, composerImpl), 0.0f, 0.0f, false, null, null, composerImpl, (i2 & 14) | 3072, 3984);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda53(function0, i, 0);
        }
    }

    public static final void DeleteShortcutButton(Function0 function0, Composer composer, int i) {
        int i2;
        Function0 function02 = function0;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1098902198);
        int i3 = i | (composerImpl.changedInstance(function02) ? 4 : 2);
        if ((i3 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.DeleteShortcutButton (ShortcutHelper.kt:755)");
            }
            Dp.Companion companion = Dp.Companion;
            Modifier m139size3ABfNKs = SizeKt.m139size3ABfNKs(Modifier.Companion, 32);
            Color.Companion.getClass();
            long j = Color.Transparent;
            Icons.INSTANCE.getClass();
            ImageVector imageVector = DeleteOutlineKt._deleteOutline;
            if (imageVector != null) {
                i2 = 2;
            } else {
                ImageVector.Builder builder = new ImageVector.Builder("Filled.DeleteOutline", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i4 = StrokeJoin.Bevel;
                PathBuilder m = ShortcutHelperKt$$ExternalSyntheticOutline0.m(6.0f, 19.0f);
                m.curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f);
                m.horizontalLineToRelative(8.0f);
                m.curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f);
                m.lineTo(18.0f, 7.0f);
                m.lineTo(6.0f, 7.0f);
                m.verticalLineToRelative(12.0f);
                m.close();
                m.moveTo(8.0f, 9.0f);
                m.horizontalLineToRelative(8.0f);
                m.verticalLineToRelative(10.0f);
                ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(m, 8.0f, 19.0f, 8.0f, 9.0f);
                m.moveTo(15.5f, 4.0f);
                m.lineToRelative(-1.0f, -1.0f);
                m.horizontalLineToRelative(-5.0f);
                m.lineToRelative(-1.0f, 1.0f);
                m.lineTo(5.0f, 4.0f);
                m.verticalLineToRelative(2.0f);
                m.horizontalLineToRelative(14.0f);
                m.lineTo(19.0f, 4.0f);
                m.close();
                builder.m565addPathoIyEayM("", m._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i4, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVector = builder.build();
                DeleteOutlineKt._deleteOutline = imageVector;
                i2 = 2;
            }
            IconSource iconSource = new IconSource(imageVector, null, i2, null);
            MaterialTheme.INSTANCE.getClass();
            float f = 0;
            function02 = function0;
            SurfacesKt.m2579ShortcutHelperButton01TuoB8(function02, MaterialTheme.getColorScheme(composerImpl).primary, j, m139size3ABfNKs, RoundedCornerShapeKt.CircleShape, iconSource, null, f, f, false, BorderStrokeKt.m31BorderStrokecXLIe8U(1, MaterialTheme.getColorScheme(composerImpl).outline), StringResources_androidKt.stringResource(R.string.shortcut_helper_delete_shortcut_button_label, composerImpl), composerImpl, (i3 & 14) | 113249664, 576);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda18(i, 1, function02);
        }
    }

    public static final void DoneButton(Function0 function0, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1202686821);
        if ((i & 6) == 0) {
            i2 = i | (composerImpl.changedInstance(function0) ? 4 : 2);
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.DoneButton (ShortcutHelper.kt:492)");
            }
            Dp.Companion companion = Dp.Companion;
            Modifier m132heightInVpY3zN4$default = SizeKt.m132heightInVpY3zN4$default(Modifier.Companion, 40, 0.0f, 2);
            MaterialTheme.INSTANCE.getClass();
            SurfacesKt.m2579ShortcutHelperButton01TuoB8(function0, MaterialTheme.getColorScheme(composerImpl).onPrimary, MaterialTheme.getColorScheme(composerImpl).primary, m132heightInVpY3zN4$default, null, null, StringResources_androidKt.stringResource(R.string.shortcut_helper_done_button_text, composerImpl), 0.0f, 0.0f, false, null, null, composerImpl, (i2 & 14) | 3072, 4016);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda53(function0, i, 2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ab, code lost:
    
        if (r12 == androidx.compose.runtime.Composer.Companion.Empty) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0127, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L78;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void EndSidePanel(final java.lang.String r16, final androidx.compose.ui.Modifier r17, final com.android.systemui.keyboard.shortcut.ui.model.ShortcutCategoryUi r18, final boolean r19, final kotlin.jvm.functions.Function1 r20, androidx.compose.runtime.Composer r21, final int r22) {
        /*
            Method dump skipped, instructions count: 363
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.EndSidePanel(java.lang.String, androidx.compose.ui.Modifier, com.android.systemui.keyboard.shortcut.ui.model.ShortcutCategoryUi, boolean, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int):void");
    }

    /* renamed from: KeyboardSettings-ixp7dh8, reason: not valid java name */
    public static final void m2574KeyboardSettingsixp7dh8(final float f, final float f2, Function0 function0, Composer composer, final int i) {
        int i2;
        final Function0 function02 = function0;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-258302129);
        if ((i & 384) == 0) {
            i2 = (composerImpl.changedInstance(function02) ? 256 : 128) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 129) == 128 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.KeyboardSettings (ShortcutHelper.kt:1061)");
            }
            float f3 = 24;
            Dp.Companion companion = Dp.Companion;
            RoundedCornerShape m186RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(f3);
            Color.Companion.getClass();
            long j = Color.Transparent;
            Modifier.Companion companion2 = Modifier.Companion;
            composerImpl.startReplaceGroup(-657284153);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda3(7);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(SizeKt.fillMaxWidth(SemanticsModifierKt.semantics(companion2, false, (Function1) rememberedValue), 1.0f), 12, 0.0f, 2);
            MaterialTheme.INSTANCE.getClass();
            float f4 = 8;
            InteractionsConfig interactionsConfig = new InteractionsConfig(MaterialTheme.getColorScheme(composerImpl).onSurface, 0.11f, MaterialTheme.getColorScheme(composerImpl).onSurface, 0.15f, MaterialTheme.getColorScheme(composerImpl).secondary, 3, f4, f3, 28, f4, 0.0f, 1024, null);
            ComposableSingletons$ShortcutHelperKt.INSTANCE.getClass();
            function02 = function0;
            SurfacesKt.m2578ClickableShortcutSurface9FW6N_Y(function02, m126paddingVpY3zN4$default, false, m186RoundedCornerShape0680j_4, j, null, interactionsConfig, ComposableSingletons$ShortcutHelperKt.f47lambda4, composerImpl, ((i2 >> 6) & 14) | 24576, 996);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda49
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ShortcutHelperKt.m2574KeyboardSettingsixp7dh8(f, f2, function02, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: NoSearchResultsText-kHDZbjc, reason: not valid java name */
    public static final void m2575NoSearchResultsTextkHDZbjc(final float f, final boolean z, Composer composer, final int i) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1804910765);
        if ((i & 19) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.NoSearchResultsText (ShortcutHelper.kt:545)");
            }
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            if (z) {
                fillMaxWidth = fillMaxWidth.then(SizeKt.FillWholeMaxHeight);
            }
            String stringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_no_search_results, composerImpl2);
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).bodyMedium;
            long j = MaterialTheme.getColorScheme(composerImpl2).onSurface;
            Dp.Companion companion = Dp.Companion;
            composerImpl = composerImpl2;
            TextKt.m316Text4IGK_g(stringResource, PaddingKt.m125paddingVpY3zN4(BackgroundKt.m26backgroundbw27NRU(PaddingKt.m126paddingVpY3zN4$default(fillMaxWidth, 0.0f, 8, 1), MaterialTheme.getColorScheme(composerImpl2).surfaceBright, RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(28)), f, 24), j, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyle, composerImpl, 0, 0, 65528);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(f, z, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda52
                public final /* synthetic */ float f$0;
                public final /* synthetic */ boolean f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(55);
                    ShortcutHelperKt.m2575NoSearchResultsTextkHDZbjc(this.f$0, this.f$1, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ResetButton(Function0 function0, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1024795180);
        if ((i & 6) == 0) {
            i2 = i | (composerImpl.changedInstance(function0) ? 4 : 2);
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ResetButton (ShortcutHelper.kt:467)");
            }
            Dp.Companion companion = Dp.Companion;
            Modifier m132heightInVpY3zN4$default = SizeKt.m132heightInVpY3zN4$default(Modifier.Companion, 40, 0.0f, 2);
            Color.Companion.getClass();
            long j = Color.Transparent;
            Icons.INSTANCE.getClass();
            ImageVector imageVector = RefreshKt._refresh;
            if (imageVector == null) {
                ImageVector.Builder builder = new ImageVector.Builder("Filled.Refresh", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i3 = StrokeJoin.Bevel;
                PathBuilder m = ShortcutHelperKt$$ExternalSyntheticOutline0.m(17.65f, 6.35f);
                m.curveTo(16.2f, 4.9f, 14.21f, 4.0f, 12.0f, 4.0f);
                m.curveToRelative(-4.42f, 0.0f, -7.99f, 3.58f, -7.99f, 8.0f);
                m.reflectiveCurveToRelative(3.57f, 8.0f, 7.99f, 8.0f);
                m.curveToRelative(3.73f, 0.0f, 6.84f, -2.55f, 7.73f, -6.0f);
                m.horizontalLineToRelative(-2.08f);
                m.curveToRelative(-0.82f, 2.33f, -3.04f, 4.0f, -5.65f, 4.0f);
                m.curveToRelative(-3.31f, 0.0f, -6.0f, -2.69f, -6.0f, -6.0f);
                m.reflectiveCurveToRelative(2.69f, -6.0f, 6.0f, -6.0f);
                m.curveToRelative(1.66f, 0.0f, 3.14f, 0.69f, 4.22f, 1.78f);
                m.lineTo(13.0f, 11.0f);
                m.horizontalLineToRelative(7.0f);
                m.verticalLineTo(4.0f);
                m.lineToRelative(-2.35f, 2.35f);
                m.close();
                builder.m565addPathoIyEayM("", m._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i3, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVector = builder.build();
                RefreshKt._refresh = imageVector;
            }
            IconSource iconSource = new IconSource(imageVector, null, 2, null);
            String stringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_reset_button_text, composerImpl);
            MaterialTheme.INSTANCE.getClass();
            SurfacesKt.m2579ShortcutHelperButton01TuoB8(function0, MaterialTheme.getColorScheme(composerImpl).primary, j, m132heightInVpY3zN4$default, null, iconSource, stringResource, 0.0f, 0.0f, false, BorderStrokeKt.m31BorderStrokecXLIe8U(1, MaterialTheme.getColorScheme(composerImpl).outlineVariant), null, composerImpl, (i2 & 14) | 3456, 2960);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda53(function0, i, 1);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x006d, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void RotatingExpandCollapseIcon(final boolean r10, androidx.compose.runtime.Composer r11, final int r12) {
        /*
            r4 = r11
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            r11 = 1532057329(0x5b5156f1, float:5.8923863E16)
            r4.startRestartGroup(r11)
            boolean r11 = r4.changed(r10)
            r0 = 2
            if (r11 == 0) goto L12
            r11 = 4
            goto L13
        L12:
            r11 = r0
        L13:
            r11 = r11 | r12
            r11 = r11 & 3
            if (r11 != r0) goto L24
            boolean r11 = r4.getSkipping()
            if (r11 != 0) goto L1f
            goto L24
        L1f:
            r4.skipToGroupEnd()
            goto Lc6
        L24:
            boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r11 == 0) goto L2f
            java.lang.String r11 = "com.android.systemui.keyboard.shortcut.ui.composable.RotatingExpandCollapseIcon (ShortcutHelper.kt:329)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r11)
        L2f:
            if (r10 == 0) goto L35
            r11 = 1127481344(0x43340000, float:180.0)
        L33:
            r0 = r11
            goto L37
        L35:
            r11 = 0
            goto L33
        L37:
            java.lang.String r2 = "Expand icon rotation animation"
            r3 = 0
            r1 = 0
            r5 = 3072(0xc00, float:4.305E-42)
            r6 = 22
            androidx.compose.runtime.State r11 = androidx.compose.animation.core.AnimateAsStateKt.animateFloatAsState(r0, r1, r2, r3, r4, r5, r6)
            androidx.compose.ui.Modifier$Companion r0 = androidx.compose.ui.Modifier.Companion
            androidx.compose.material3.MaterialTheme r1 = androidx.compose.material3.MaterialTheme.INSTANCE
            r1.getClass()
            androidx.compose.material3.ColorScheme r1 = androidx.compose.material3.MaterialTheme.getColorScheme(r4)
            long r1 = r1.surfaceContainerHigh
            androidx.compose.foundation.shape.RoundedCornerShape r3 = androidx.compose.foundation.shape.RoundedCornerShapeKt.CircleShape
            androidx.compose.ui.Modifier r0 = androidx.compose.foundation.BackgroundKt.m26backgroundbw27NRU(r0, r1, r3)
            r1 = -435222328(0xffffffffe60f08c8, float:-1.688651E23)
            r4.startReplaceGroup(r1)
            boolean r1 = r4.changed(r11)
            java.lang.Object r2 = r4.rememberedValue()
            if (r1 != 0) goto L6f
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r1) goto L78
        L6f:
            com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda4 r2 = new com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda4
            r1 = 2
            r2.<init>(r11, r1)
            r4.updateRememberedValue(r2)
        L78:
            kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
            r11 = 0
            r4.end(r11)
            androidx.compose.ui.Modifier r2 = androidx.compose.ui.graphics.GraphicsLayerModifierKt.graphicsLayer(r0, r2)
            androidx.compose.material.icons.Icons r0 = androidx.compose.material.icons.Icons.INSTANCE
            r0.getClass()
            androidx.compose.ui.graphics.vector.ImageVector r0 = androidx.compose.material.icons.filled.ExpandMoreKt.getExpandMore()
            if (r10 == 0) goto L9e
            r1 = -606841447(0xffffffffdbd45599, float:-1.1953362E17)
            r4.startReplaceGroup(r1)
            r1 = 2131956746(0x7f13140a, float:1.9550056E38)
            java.lang.String r1 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r1, r4)
            r4.end(r11)
            goto Lae
        L9e:
            r1 = -606730405(0xffffffffdbd6075b, float:-1.20487465E17)
            r4.startReplaceGroup(r1)
            r1 = 2131956748(0x7f13140c, float:1.955006E38)
            java.lang.String r1 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r1, r4)
            r4.end(r11)
        Lae:
            androidx.compose.material3.ColorScheme r11 = androidx.compose.material3.MaterialTheme.getColorScheme(r4)
            long r5 = r11.onSurface
            r8 = r5
            r5 = r4
            r3 = r8
            r6 = 0
            r7 = 0
            androidx.compose.material3.IconKt.m270Iconww6aTOc(r0, r1, r2, r3, r5, r6, r7)
            r4 = r5
            boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r11 == 0) goto Lc6
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lc6:
            androidx.compose.runtime.RecomposeScopeImpl r11 = r4.endRestartGroup()
            if (r11 == 0) goto Ld3
            com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda61 r0 = new com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda61
            r0.<init>(r10, r12)
            r11.block = r0
        Ld3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.RotatingExpandCollapseIcon(boolean, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:123:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x012e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0170  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void Shortcut(final androidx.compose.ui.Modifier r20, final java.lang.String r21, final com.android.systemui.keyboard.shortcut.shared.model.Shortcut r22, boolean r23, kotlin.jvm.functions.Function1 r24, androidx.compose.runtime.Composer r25, final int r26, final int r27) {
        /*
            Method dump skipped, instructions count: 875
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.Shortcut(androidx.compose.ui.Modifier, java.lang.String, com.android.systemui.keyboard.shortcut.shared.model.Shortcut, boolean, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void ShortcutCategoryDetailsSinglePane(String str, ShortcutCategoryUi shortcutCategoryUi, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1744088556);
        int i2 = (composerImpl.changed(str) ? 4 : 2) | i | (composerImpl.changedInstance(shortcutCategoryUi) ? 32 : 16);
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCategoryDetailsSinglePane (ShortcutHelper.kt:359)");
            }
            Dp.Companion companion = Dp.Companion;
            Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(Modifier.Companion, 16, 0.0f, 2);
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m126paddingVpY3zN4$default);
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
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(-1190030896);
            List list = shortcutCategoryUi.subCategories;
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                ShortcutSubCategorySinglePane(str, (ShortcutSubCategory) list.get(i3), composerImpl, i2 & 14);
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda2(shortcutCategoryUi, i, 1, str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x006a, code lost:
    
        if ((r24 & 8) != 0) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0074  */
    /* renamed from: ShortcutCategoryIcon-ww6aTOc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m2576ShortcutCategoryIconww6aTOc(final com.android.systemui.keyboard.shortcut.ui.model.IconSource r18, final androidx.compose.ui.Modifier r19, long r20, androidx.compose.runtime.Composer r22, final int r23, final int r24) {
        /*
            Method dump skipped, instructions count: 252
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.m2576ShortcutCategoryIconww6aTOc(com.android.systemui.keyboard.shortcut.ui.model.IconSource, androidx.compose.ui.Modifier, long, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void ShortcutCommand(ShortcutCommand shortcutCommand, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(833482787);
        if ((((composerImpl.changedInstance(shortcutCommand) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCommand (ShortcutHelper.kt:790)");
            }
            Modifier.Companion companion = Modifier.Companion;
            Arrangement.INSTANCE.getClass();
            Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Start$1, Alignment.Companion.Top, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
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
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(842198973);
            int i2 = 0;
            for (Object obj : shortcutCommand.keys) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                final ShortcutKey shortcutKey = (ShortcutKey) obj;
                composerImpl.startReplaceGroup(842200139);
                if (i2 > 0) {
                    Dp.Companion companion2 = Dp.Companion;
                    SpacerKt.Spacer(composerImpl, SizeKt.m143width3ABfNKs(Modifier.Companion, 4));
                }
                composerImpl.end(false);
                ShortcutKeyContainer(ComposableLambdaKt.rememberComposableLambda(-867364547, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutCommand$1$1$1
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        BoxScope boxScope = (BoxScope) obj2;
                        Composer composer2 = (Composer) obj3;
                        int intValue = ((Number) obj4).intValue();
                        if ((intValue & 6) == 0) {
                            intValue |= ((ComposerImpl) composer2).changed(boxScope) ? 4 : 2;
                        }
                        if ((intValue & 19) == 18) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCommand.<anonymous>.<anonymous>.<anonymous> (ShortcutHelper.kt:797)");
                        }
                        ShortcutKey shortcutKey2 = ShortcutKey.this;
                        if (shortcutKey2 instanceof ShortcutKey.Text) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(2039231563);
                            ShortcutHelperKt.ShortcutTextKey(boxScope, (ShortcutKey.Text) shortcutKey2, composerImpl3, intValue & 14);
                            composerImpl3.end(false);
                        } else if (shortcutKey2 instanceof ShortcutKey.Icon) {
                            ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                            composerImpl4.startReplaceGroup(2039325803);
                            ShortcutHelperKt.ShortcutIconKey(boxScope, (ShortcutKey.Icon) shortcutKey2, composerImpl4, intValue & 14);
                            composerImpl4.end(false);
                        } else {
                            ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                            composerImpl5.startReplaceGroup(2039383463);
                            composerImpl5.end(false);
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 6);
                i2 = i3;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda1(shortcutCommand, i, 1);
        }
    }

    public static final void ShortcutCommandContainer(final boolean z, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1297463186);
        if ((((composerImpl.changed(z) ? 4 : 2) | i) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCommandContainer (ShortcutHelper.kt:771)");
            }
            if (z) {
                composerImpl.startReplaceGroup(1400926430);
                Modifier wrapContentSize$default = SizeKt.wrapContentSize$default(Modifier.Companion, null, 3);
                MaterialTheme.INSTANCE.getClass();
                long j = MaterialTheme.getColorScheme(composerImpl).outlineVariant;
                Dp.Companion companion = Dp.Companion;
                Modifier m124padding3ABfNKs = PaddingKt.m124padding3ABfNKs(BackgroundKt.m26backgroundbw27NRU(wrapContentSize$default, j, RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(16)), 4);
                Alignment.Companion.getClass();
                MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m124padding3ABfNKs);
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
                Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                composableLambdaImpl.invoke((Object) composerImpl, (Object) 6);
                composerImpl.end(true);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(1401272390);
                composableLambdaImpl.invoke((Object) composerImpl, (Object) 6);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(z, composableLambdaImpl, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda22
                public final /* synthetic */ boolean f$0;
                public final /* synthetic */ ComposableLambdaImpl f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                    ComposableLambdaImpl composableLambdaImpl2 = this.f$1;
                    ShortcutHelperKt.ShortcutCommandContainer(this.f$0, composableLambdaImpl2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutDescriptionText(final String str, final Shortcut shortcut, final Modifier modifier, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1915721858);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changedInstance(shortcut) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutDescriptionText (ShortcutHelper.kt:863)");
            }
            String str2 = shortcut.label;
            composerImpl2.startReplaceGroup(1744991708);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.textWithHighlightedSearchQuery (ShortcutHelper.kt:874)");
            }
            AnnotatedString.Builder builder = new AnnotatedString.Builder(0, 1, null);
            Locale locale = Locale.ROOT;
            int indexOf$default = StringsKt__StringsKt.indexOf$default(str2.toLowerCase(locale), StringsKt__StringsKt.trim(str).toString().toLowerCase(locale), 0, false, 6);
            int length = StringsKt__StringsKt.trim(str).toString().length() + indexOf$default;
            if (indexOf$default > 0) {
                builder.text.append(str2.substring(0, indexOf$default));
            }
            composerImpl2.startReplaceGroup(941808924);
            if (indexOf$default >= 0) {
                String substring = str2.substring(indexOf$default, length);
                MaterialTheme.INSTANCE.getClass();
                int pushStyle = builder.pushStyle(new SpanStyle(0L, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, MaterialTheme.getColorScheme(composerImpl2).primaryContainer, (TextDecoration) null, (Shadow) null, (PlatformSpanStyle) null, (DrawStyle) null, 63487, (DefaultConstructorMarker) null));
                try {
                    builder.text.append(substring);
                    Unit unit = Unit.INSTANCE;
                    builder.pop(pushStyle);
                    if (length < str2.length()) {
                        builder.text.append(str2.substring(length));
                    }
                } catch (Throwable th) {
                    builder.pop(pushStyle);
                    throw th;
                }
            } else {
                builder.text.append(str2);
            }
            composerImpl2.end(false);
            AnnotatedString annotatedString = builder.toAnnotatedString();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            TextKt.m317TextIbK3jfQ(annotatedString, modifier, MaterialTheme.getColorScheme(composerImpl2).onSurface, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, MaterialTheme.getTypography(composerImpl2).titleSmall, composerImpl, (i2 >> 3) & 112, 0, 131064);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda13
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Shortcut shortcut2 = shortcut;
                    Modifier modifier2 = modifier;
                    ShortcutHelperKt.ShortcutDescriptionText(str, shortcut2, modifier2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutHelper(final Function1 function1, final Function0 function0, final Modifier modifier, final ShortcutsUiState shortcutsUiState, ShortcutHelperKt$ShortcutHelper$1 shortcutHelperKt$ShortcutHelper$1, final Function1 function12, final Function1 function13, Composer composer, final int i) {
        int i2;
        ShortcutHelperKt$ShortcutHelper$1 shortcutHelperKt$ShortcutHelper$12;
        final ShortcutHelperKt$ShortcutHelper$1 shortcutHelperKt$ShortcutHelper$13;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1007329510);
        int i3 = i | (composerImpl.changedInstance(function1) ? 4 : 2) | (composerImpl.changedInstance(function0) ? 32 : 16) | (composerImpl.changed(modifier) ? 256 : 128) | (composerImpl.changed(shortcutsUiState) ? 2048 : 1024) | 8192 | (composerImpl.changedInstance(function12) ? 131072 : 65536) | (composerImpl.changedInstance(function13) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        if ((599187 & i3) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            shortcutHelperKt$ShortcutHelper$13 = shortcutHelperKt$ShortcutHelper$1;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                i2 = i3 & (-57345);
                shortcutHelperKt$ShortcutHelper$12 = ShortcutHelperKt$ShortcutHelper$1.INSTANCE;
            } else {
                composerImpl.skipToGroupEnd();
                i2 = i3 & (-57345);
                shortcutHelperKt$ShortcutHelper$12 = shortcutHelperKt$ShortcutHelper$1;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelper (ShortcutHelper.kt:142)");
            }
            if (shortcutsUiState instanceof ShortcutsUiState.Active) {
                int i4 = i2 << 6;
                ActiveShortcutHelper((ShortcutsUiState.Active) shortcutsUiState, shortcutHelperKt$ShortcutHelper$12, function1, function13, modifier, function0, function12, composerImpl, ((i2 >> 9) & 7168) | (i4 & 896) | (i4 & 57344) | ((i2 << 12) & 458752) | ((i2 << 3) & 3670016));
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            shortcutHelperKt$ShortcutHelper$13 = shortcutHelperKt$ShortcutHelper$12;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(function0, modifier, shortcutsUiState, shortcutHelperKt$ShortcutHelper$13, function12, function13, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda27
                public final /* synthetic */ Function0 f$1;
                public final /* synthetic */ Modifier f$2;
                public final /* synthetic */ ShortcutsUiState f$3;
                public final /* synthetic */ ShortcutHelperKt$ShortcutHelper$1 f$4;
                public final /* synthetic */ Function1 f$5;
                public final /* synthetic */ Function1 f$6;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Function1 function14 = this.f$5;
                    Function1 function15 = this.f$6;
                    ShortcutHelperKt.ShortcutHelper(Function1.this, this.f$1, this.f$2, this.f$3, this.f$4, function14, function15, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutHelperSinglePane(String str, Function1 function1, List list, ShortcutCategoryType shortcutCategoryType, Function1 function12, Function0 function0, Modifier modifier, Composer composer, int i) {
        int i2;
        List list2;
        Function1 function13;
        Modifier then;
        boolean z;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(157824801);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            list2 = list;
            i2 |= composerImpl.changedInstance(list2) ? 256 : 128;
        } else {
            list2 = list;
        }
        if ((i & 3072) == 0) {
            i2 |= (i & 4096) == 0 ? composerImpl.changed(shortcutCategoryType) : composerImpl.changedInstance(shortcutCategoryType) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            function13 = function12;
            i2 |= composerImpl.changedInstance(function13) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        } else {
            function13 = function12;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl.changed(modifier) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        int i3 = i2;
        if ((599187 & i3) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperSinglePane (ShortcutHelper.kt:215)");
            }
            then = ScrollingContainerKt.scrollingContainer(SizeKt.fillMaxSize(modifier, 1.0f), r12, Orientation.Vertical, (r14 & 2) != 0, false, null, r12.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(ScrollKt.rememberScrollState(composerImpl), false, true));
            float f = 16;
            Dp.Companion companion = Dp.Companion;
            Modifier m128paddingqDBjuR0$default = PaddingKt.m128paddingqDBjuR0$default(then, f, 26, f, 0.0f, 8);
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m128paddingqDBjuR0$default);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
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
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl, materializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            TitleBar(false, composerImpl, 0, 1);
            Modifier.Companion companion2 = Modifier.Companion;
            SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion2, 6));
            int i4 = i3 >> 3;
            ShortcutsSearchBar(function1, composerImpl, i4 & 14);
            SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion2, f));
            if (list2.isEmpty()) {
                composerImpl.startReplaceGroup(-423511973);
                Modifier weight = columnScopeInstance.weight(companion2, 1.0f, true);
                MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, weight);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function02);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, function2);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function22);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
                }
                Updater.m336setimpl(composerImpl, materializeModifier2, function24);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                z = true;
                m2575NoSearchResultsTextkHDZbjc(f, true, composerImpl, 54);
                composerImpl.end(true);
                composerImpl.end(false);
            } else {
                z = true;
                composerImpl.startReplaceGroup(-423347518);
                CategoriesPanelSinglePane(str, list2, shortcutCategoryType, function13, composerImpl, (i3 & 14) | (i4 & 112) | (i4 & 896) | (i4 & 7168));
                SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 1.0f, true));
                composerImpl.end(false);
            }
            m2574KeyboardSettingsixp7dh8(f, 32, function0, composerImpl, ((i3 >> 9) & 896) | 54);
            composerImpl.end(z);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda30(str, function1, list, shortcutCategoryType, function12, function0, modifier, i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:141:0x02b9, code lost:
    
        if (r12 == androidx.compose.runtime.Composer.Companion.Empty) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x02e3, code lost:
    
        if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L162;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v13, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v25 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ShortcutHelperTwoPane(final java.lang.String r30, final kotlin.jvm.functions.Function1 r31, final java.util.List r32, final com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType r33, kotlin.jvm.functions.Function1 r34, final kotlin.jvm.functions.Function0 r35, final boolean r36, final boolean r37, final boolean r38, final kotlin.jvm.functions.Function1 r39, final androidx.compose.ui.Modifier r40, final kotlin.jvm.functions.Function1 r41, androidx.compose.runtime.Composer r42, final int r43, final int r44) {
        /*
            Method dump skipped, instructions count: 1148
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.ShortcutHelperTwoPane(java.lang.String, kotlin.jvm.functions.Function1, java.util.List, com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function0, boolean, boolean, boolean, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0067, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ShortcutIcon(final com.android.systemui.keyboard.shortcut.shared.model.ShortcutIcon r10, final androidx.compose.ui.Modifier r11, androidx.compose.runtime.Composer r12, final int r13) {
        /*
            r7 = r12
            androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
            r12 = -1928877533(0xffffffff8d07aa23, float:-4.180488E-31)
            r7.startRestartGroup(r12)
            boolean r12 = r7.changed(r10)
            if (r12 == 0) goto L11
            r12 = 4
            goto L12
        L11:
            r12 = 2
        L12:
            r12 = r12 | r13
            boolean r0 = r7.changed(r11)
            if (r0 == 0) goto L1c
            r0 = 32
            goto L1e
        L1c:
            r0 = 16
        L1e:
            r12 = r12 | r0
            r12 = r12 | 384(0x180, float:5.38E-43)
            r0 = r12 & 147(0x93, float:2.06E-43)
            r1 = 146(0x92, float:2.05E-43)
            if (r0 != r1) goto L34
            boolean r0 = r7.getSkipping()
            if (r0 != 0) goto L2e
            goto L34
        L2e:
            r7.skipToGroupEnd()
            r2 = r11
            goto Lb6
        L34:
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L3f
            java.lang.String r0 = "com.android.systemui.keyboard.shortcut.ui.composable.ShortcutIcon (ShortcutHelper.kt:691)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L3f:
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.LocalContext
            java.lang.Object r0 = r7.consume(r0)
            android.content.Context r0 = (android.content.Context) r0
            java.lang.String r1 = r10.packageName
            r2 = 1919308570(0x7266531a, float:4.562049E30)
            r7.startReplaceGroup(r2)
            boolean r1 = r7.changed(r1)
            int r2 = r10.resourceId
            boolean r3 = r7.changed(r2)
            r1 = r1 | r3
            java.lang.Object r3 = r7.rememberedValue()
            if (r1 != 0) goto L69
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r1) goto L76
        L69:
            java.lang.String r1 = r10.packageName
            android.graphics.drawable.Icon r1 = android.graphics.drawable.Icon.createWithResource(r1, r2)
            android.graphics.drawable.Drawable r3 = r1.loadDrawable(r0)
            r7.updateRememberedValue(r3)
        L76:
            android.graphics.drawable.Drawable r3 = (android.graphics.drawable.Drawable) r3
            r0 = 0
            r7.end(r0)
            if (r3 != 0) goto L96
            boolean r12 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r12 == 0) goto L87
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L87:
            androidx.compose.runtime.RecomposeScopeImpl r12 = r7.endRestartGroup()
            if (r12 == 0) goto Lc4
            com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda14 r0 = new com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda14
            r1 = 0
            r0.<init>(r10, r11, r13, r1)
            r12.block = r0
            return
        L96:
            androidx.compose.ui.graphics.painter.Painter r0 = com.android.compose.ui.graphics.painter.DrawablePainterKt.rememberDrawablePainter(r3, r7)
            int r12 = r12 << 3
            r12 = r12 & 896(0x380, float:1.256E-42)
            r1 = 48
            r8 = r1 | r12
            r5 = 0
            r6 = 0
            r1 = 0
            r3 = 0
            r4 = 0
            r9 = 120(0x78, float:1.68E-43)
            r2 = r11
            androidx.compose.foundation.ImageKt.Image(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9)
            boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r11 == 0) goto Lb6
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lb6:
            androidx.compose.runtime.RecomposeScopeImpl r11 = r7.endRestartGroup()
            if (r11 == 0) goto Lc4
            com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda14 r12 = new com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda14
            r0 = 1
            r12.<init>(r10, r2, r13, r0)
            r11.block = r12
        Lc4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.ShortcutIcon(com.android.systemui.keyboard.shortcut.shared.model.ShortcutIcon, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    public static final void ShortcutIconKey(BoxScope boxScope, ShortcutKey.Icon icon, Composer composer, int i) {
        int i2;
        Painter rememberDrawablePainter;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(450478271);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(boxScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= (i & 64) == 0 ? composerImpl.changed(icon) : composerImpl.changedInstance(icon) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutIconKey (ShortcutHelper.kt:834)");
            }
            if (icon instanceof ShortcutKey.Icon.ResIdIcon) {
                composerImpl.startReplaceGroup(-1074242821);
                rememberDrawablePainter = PainterResources_androidKt.painterResource(((ShortcutKey.Icon.ResIdIcon) icon).drawableResId, composerImpl, 0);
                composerImpl.end(false);
            } else {
                if (!(icon instanceof ShortcutKey.Icon.DrawableIcon)) {
                    composerImpl.startReplaceGroup(-1074244628);
                    composerImpl.end(false);
                    throw new NoWhenBranchMatchedException();
                }
                composerImpl.startReplaceGroup(-1074240023);
                rememberDrawablePainter = DrawablePainterKt.rememberDrawablePainter(((ShortcutKey.Icon.DrawableIcon) icon).drawable, composerImpl);
                composerImpl.end(false);
            }
            Modifier.Companion companion = Modifier.Companion;
            Alignment.Companion.getClass();
            Dp.Companion companion2 = Dp.Companion;
            IconKt.m269Iconww6aTOc(rememberDrawablePainter, (String) null, PaddingKt.m124padding3ABfNKs(boxScope.align(companion, Alignment.Companion.Center), 6), 0L, composerImpl, 48, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda24(boxScope, icon, i, 1);
        }
    }

    public static final void ShortcutKeyCombinations(Modifier modifier, final Shortcut shortcut, final boolean z, final Function0 function0, final Function0 function02, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1041103873);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(shortcut) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i2 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutKeyCombinations (ShortcutHelper.kt:712)");
            }
            Arrangement arrangement = Arrangement.INSTANCE;
            Dp.Companion companion = Dp.Companion;
            arrangement.getClass();
            Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(8);
            Alignment.Companion.getClass();
            FlowLayoutKt.FlowRow(modifier, Arrangement.End, m91spacedBy0680j_4, Alignment.Companion.CenterVertically, 0, 0, null, ComposableLambdaKt.rememberComposableLambda(671972285, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutKeyCombinations$3
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    FlowRowScope flowRowScope = (FlowRowScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(flowRowScope) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutKeyCombinations.<anonymous> (ShortcutHelper.kt:719)");
                    }
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    composerImpl3.startReplaceGroup(1146876038);
                    final Shortcut shortcut2 = Shortcut.this;
                    int i3 = 0;
                    for (Object obj4 : shortcut2.commands) {
                        int i4 = i3 + 1;
                        if (i3 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                            throw null;
                        }
                        final ShortcutCommand shortcutCommand = (ShortcutCommand) obj4;
                        composerImpl3.startReplaceGroup(1146877384);
                        if (i3 > 0) {
                            Dp.Companion companion2 = Dp.Companion;
                            ShortcutHelperKt.m2577ShortcutOrSeparatorziNgDLE(flowRowScope, 16, composerImpl3, (intValue & 14) | 48);
                        }
                        composerImpl3.end(false);
                        ShortcutHelperKt.ShortcutCommandContainer(shortcutCommand.isCustom, ComposableLambdaKt.rememberComposableLambda(-1240949659, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutKeyCombinations$3$1$1
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj5, Object obj6) {
                                Composer composer3 = (Composer) obj5;
                                if ((((Number) obj6).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                    if (composerImpl4.getSkipping()) {
                                        composerImpl4.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutKeyCombinations.<anonymous>.<anonymous>.<anonymous> (ShortcutHelper.kt:723)");
                                }
                                ShortcutHelperKt.ShortcutCommand(ShortcutCommand.this, composer3, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        }, composerImpl3), composerImpl3, 48);
                        i3 = i4;
                    }
                    composerImpl3.end(false);
                    composerImpl3.startReplaceGroup(1146883888);
                    if (z) {
                        Dp.Companion companion3 = Dp.Companion;
                        SpacerKt.Spacer(composerImpl3, SizeKt.m143width3ABfNKs(Modifier.Companion, 16));
                    }
                    composerImpl3.end(false);
                    final Function0 function03 = function02;
                    final Function0 function04 = function0;
                    AnimatedVisibilityKt.AnimatedVisibility(flowRowScope, z, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-1725073435, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutKeyCombinations$3.2
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj5, Object obj6, Object obj7) {
                            Composer composer3 = (Composer) obj6;
                            ((Number) obj7).intValue();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutKeyCombinations.<anonymous>.<anonymous> (ShortcutHelper.kt:729)");
                            }
                            if (Shortcut.this.containsCustomShortcutCommands) {
                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                composerImpl4.startReplaceGroup(-156751585);
                                ShortcutHelperKt.DeleteShortcutButton(function03, composerImpl4, 0);
                                composerImpl4.end(false);
                            } else {
                                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                composerImpl5.startReplaceGroup(-156667451);
                                ShortcutHelperKt.AddShortcutButton(function04, composerImpl5, 0);
                                composerImpl5.end(false);
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl3), composerImpl3, (intValue & 14) | 1572864, 30);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, (i2 & 14) | 12586416, 112);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda16(modifier, shortcut, z, function0, function02, i, 0);
        }
    }

    public static final void ShortcutKeyContainer(ComposableLambdaImpl composableLambdaImpl, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1318201341);
        if ((i & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutKeyContainer (ShortcutHelper.kt:808)");
            }
            Dp.Companion companion = Dp.Companion;
            Modifier m130height3ABfNKs = SizeKt.m130height3ABfNKs(Modifier.Companion, 36);
            MaterialTheme.INSTANCE.getClass();
            Modifier m26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(m130height3ABfNKs, MaterialTheme.getColorScheme(composerImpl).surfaceContainer, RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(12));
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m26backgroundbw27NRU);
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
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            composableLambdaImpl.invoke(BoxScopeInstance.INSTANCE, (Object) composerImpl, (Object) 54);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda1(composableLambdaImpl, i, 2);
        }
    }

    /* renamed from: ShortcutOrSeparator-ziNgDLE, reason: not valid java name */
    public static final void m2577ShortcutOrSeparatorziNgDLE(final FlowRowScope flowRowScope, final float f, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(562023815);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(flowRowScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(f) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutOrSeparator (ShortcutHelper.kt:848)");
            }
            Modifier.Companion companion = Modifier.Companion;
            SpacerKt.Spacer(composerImpl, SizeKt.m143width3ABfNKs(companion, f));
            String stringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_key_combinations_or_separator, composerImpl);
            Alignment.Companion.getClass();
            Modifier align = ((FlowRowScopeInstance) flowRowScope).$$delegate_0.align(companion, Alignment.Companion.CenterVertically);
            composerImpl.startReplaceGroup(-278441072);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda3(1);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            Modifier semantics = SemanticsModifierKt.semantics(align, false, (Function1) rememberedValue);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m316Text4IGK_g(stringResource, semantics, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).titleSmall, composerImpl, 0, 0, 65532);
            composerImpl = composerImpl;
            SpacerKt.Spacer(composerImpl, SizeKt.m143width3ABfNKs(companion, f));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda20
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ShortcutHelperKt.m2577ShortcutOrSeparatorziNgDLE(FlowRowScope.this, f, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutSubCategorySinglePane(String str, ShortcutSubCategory shortcutSubCategory, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1515485004);
        int i2 = (composerImpl.changed(str) ? 4 : 2) | i | (composerImpl.changedInstance(shortcutSubCategory) ? 32 : 16);
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutSubCategorySinglePane (ShortcutHelper.kt:368)");
            }
            SubCategoryTitle(shortcutSubCategory.label, composerImpl, 0);
            List list = shortcutSubCategory.shortcuts;
            int size = list.size();
            int i3 = 0;
            while (i3 < size) {
                Shortcut shortcut = (Shortcut) list.get(i3);
                composerImpl.startReplaceGroup(1264142929);
                if (i3 > 0) {
                    MaterialTheme.INSTANCE.getClass();
                    DividerKt.m262HorizontalDivider9IZ8Weo(null, 0.0f, MaterialTheme.getColorScheme(composerImpl).surfaceContainerHigh, composerImpl, 0, 3);
                }
                composerImpl.end(false);
                Dp.Companion companion = Dp.Companion;
                Shortcut(PaddingKt.m126paddingVpY3zN4$default(Modifier.Companion, 0.0f, 24, 1), str, shortcut, false, null, composerImpl, ((i2 << 3) & 112) | 6, 24);
                i3++;
                size = size;
                list = list;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda2(shortcutSubCategory, i, 0, str);
        }
    }

    public static final void ShortcutTextKey(BoxScope boxScope, ShortcutKey.Text text, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(2118702167);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(boxScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(text) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutTextKey (ShortcutHelper.kt:822)");
            }
            String str = text.value;
            Modifier.Companion companion = Modifier.Companion;
            Alignment.Companion.getClass();
            Dp.Companion companion2 = Dp.Companion;
            Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(boxScope.align(companion, Alignment.Companion.Center), 12, 0.0f, 2);
            composerImpl2.startReplaceGroup(-721388036);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda3(2);
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            composerImpl2.end(false);
            Modifier semantics = SemanticsModifierKt.semantics(m126paddingVpY3zN4$default, false, (Function1) rememberedValue);
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            TextKt.m316Text4IGK_g(str, semantics, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl2).titleSmall, composerImpl, 0, 0, 65532);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda24(boxScope, text, i, 0);
        }
    }

    public static final void ShortcutsSearchBar(Function1 function1, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-2050278086);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changedInstance(function1) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutsSearchBar (ShortcutHelper.kt:1021)");
            }
            composerImpl2.startReplaceGroup(-568352796);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (rememberedValue == obj) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default("");
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            MutableState mutableState = (MutableState) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -568350974);
            if (m == obj) {
                m = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
            }
            FocusRequester focusRequester = (FocusRequester) m;
            composerImpl2.end(false);
            final FocusManager focusManager = (FocusManager) composerImpl2.consume(CompositionLocalsKt.LocalFocusManager);
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(-568347458);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (rememberedValue2 == obj) {
                rememberedValue2 = new ShortcutHelperKt$ShortcutsSearchBar$1$1(focusRequester, null);
                composerImpl2.updateRememberedValue(rememberedValue2);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, unit, (Function2) rememberedValue2);
            Modifier focusRequester2 = FocusRequesterModifierKt.focusRequester(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), focusRequester);
            composerImpl2.startReplaceGroup(-568336855);
            boolean changedInstance = composerImpl2.changedInstance(focusManager);
            Object rememberedValue3 = composerImpl2.rememberedValue();
            if (changedInstance || rememberedValue3 == obj) {
                rememberedValue3 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutsSearchBar$2$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        long m578getKeyZmokQxo = KeyEvent_androidKt.m578getKeyZmokQxo(((KeyEvent) obj2).nativeKeyEvent);
                        Key.Companion.getClass();
                        if (!Key.m576equalsimpl0(m578getKeyZmokQxo, Key.DirectionDown)) {
                            return Boolean.FALSE;
                        }
                        FocusDirection.Companion.getClass();
                        ((FocusOwnerImpl) FocusManager.this).m373moveFocus3ESFkO8(FocusDirection.Down);
                        return Boolean.TRUE;
                    }
                };
                composerImpl2.updateRememberedValue(rememberedValue3);
            }
            composerImpl2.end(false);
            Modifier onKeyEvent = KeyInputModifierKt.onKeyEvent(focusRequester2, (Function1) rememberedValue3);
            SearchBarDefaults searchBarDefaults = SearchBarDefaults.INSTANCE;
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl2).surfaceBright;
            searchBarDefaults.getClass();
            SearchBarColors m283colorsKlgxPg = SearchBarDefaults.m283colorsKlgxPg(j, composerImpl2, 0, 6);
            String str = (String) mutableState.getValue();
            float f = 0;
            Dp.Companion companion = Dp.Companion;
            WindowInsets m147WindowInsetsa9UjIt4 = WindowInsetsKt.m147WindowInsetsa9UjIt4(f, f, f, f);
            composerImpl2.startReplaceGroup(-568321971);
            boolean z = (i2 & 14) == 4;
            Object rememberedValue4 = composerImpl2.rememberedValue();
            if (z || rememberedValue4 == obj) {
                rememberedValue4 = new ShortcutHelperKt$$ExternalSyntheticLambda40(function1, mutableState, 0);
                composerImpl2.updateRememberedValue(rememberedValue4);
            }
            Function1 function12 = (Function1) rememberedValue4;
            Object m2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -568319065);
            if (m2 == obj) {
                m2 = new ShortcutHelperKt$$ExternalSyntheticLambda3(5);
                composerImpl2.updateRememberedValue(m2);
            }
            Function1 function13 = (Function1) m2;
            Object m3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -568322937);
            if (m3 == obj) {
                m3 = new ShortcutHelperKt$$ExternalSyntheticLambda3(6);
                composerImpl2.updateRememberedValue(m3);
            }
            composerImpl2.end(false);
            ComposableSingletons$ShortcutHelperKt.INSTANCE.getClass();
            composerImpl = composerImpl2;
            SearchBarKt.m285SearchBarWuY5d9Q(str, function12, function13, false, (Function1) m3, onKeyEvent, false, ComposableSingletons$ShortcutHelperKt.f44lambda1, ComposableSingletons$ShortcutHelperKt.f45lambda2, null, null, m283colorsKlgxPg, 0.0f, 0.0f, m147WindowInsetsa9UjIt4, null, ComposableSingletons$ShortcutHelperKt.f46lambda3, composerImpl, 113274240, 1572864, 46656);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda53(function1, i, 3);
        }
    }

    public static final void StartSidePanel(final Function1 function1, final Modifier modifier, final List list, final Function0 function0, final ShortcutCategoryType shortcutCategoryType, final Function1 function12, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-43673571);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function1) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(list) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= (32768 & i) == 0 ? composerImpl.changed(shortcutCategoryType) : composerImpl.changedInstance(shortcutCategoryType) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changedInstance(function12) ? 131072 : 65536;
        }
        if ((i2 & 74899) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.StartSidePanel (ShortcutHelper.kt:904)");
            }
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
            CompositionLocalKt.CompositionLocalProvider(staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(DensityKt.Density(((Density) composerImpl.consume(staticProvidableCompositionLocal)).getDensity(), RangesKt___RangesKt.coerceIn(((Density) composerImpl.consume(staticProvidableCompositionLocal)).getFontScale(), 1.0f, 1.5f))), ComposableLambdaKt.rememberComposableLambda(343378781, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$StartSidePanel$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.StartSidePanel.<anonymous> (ShortcutHelper.kt:913)");
                    }
                    List list2 = list;
                    Arrangement.INSTANCE.getClass();
                    Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                    Alignment.Companion.getClass();
                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer2, 0);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, Modifier.this);
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
                    Updater.m336setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                    }
                    Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    ShortcutHelperKt.ShortcutsSearchBar(function1, composer2, 0);
                    Modifier.Companion companion = Modifier.Companion;
                    Dp.Companion companion2 = Dp.Companion;
                    SpacerKt.Spacer(composer2, SizeKt.m132heightInVpY3zN4$default(companion, 8, 0.0f, 2));
                    ShortcutHelperKt.CategoriesPanelTwoPane(list2, shortcutCategoryType, function12, composer2, 0);
                    SpacerKt.Spacer(composer2, columnScopeInstance.weight(companion, 1.0f, true));
                    float f = 24;
                    ShortcutHelperKt.m2574KeyboardSettingsixp7dh8(f, f, function0, composer2, 54);
                    composerImpl3.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda47
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    ShortcutHelperKt.StartSidePanel(Function1.this, modifier, list, function0, shortcutCategoryType, function12, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SubCategoryContainerDualPane(final String str, final ShortcutSubCategory shortcutSubCategory, final boolean z, final Function1 function1, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1207587678);
        if (((i | (composerImpl.changed(str) ? 4 : 2) | (composerImpl.changedInstance(shortcutSubCategory) ? 32 : 16) | (composerImpl.changed(z) ? 256 : 128) | (composerImpl.changedInstance(function1) ? 2048 : 1024)) & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.SubCategoryContainerDualPane (ShortcutHelper.kt:568)");
            }
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            Dp.Companion companion = Dp.Companion;
            RoundedCornerShape m186RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(28);
            MaterialTheme.INSTANCE.getClass();
            SurfaceKt.m303SurfaceT9BRK9s(fillMaxWidth, m186RoundedCornerShape0680j_4, MaterialTheme.getColorScheme(composerImpl).surfaceBright, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-867766109, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$SubCategoryContainerDualPane$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i2;
                    Shortcut shortcut;
                    Composer composer2 = (Composer) obj;
                    int i3 = 2;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.SubCategoryContainerDualPane.<anonymous> (ShortcutHelper.kt:574)");
                    }
                    Modifier.Companion companion2 = Modifier.Companion;
                    Dp.Companion companion3 = Dp.Companion;
                    Modifier m124padding3ABfNKs = PaddingKt.m124padding3ABfNKs(companion2, 16);
                    Arrangement.INSTANCE.getClass();
                    Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                    Alignment.Companion.getClass();
                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer2, 0);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, m124padding3ABfNKs);
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
                    Updater.m336setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                    }
                    Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    ShortcutSubCategory shortcutSubCategory2 = ShortcutSubCategory.this;
                    ShortcutHelperKt.SubCategoryTitle(shortcutSubCategory2.label, composer2, 0);
                    float f = 8;
                    SpacerKt.Spacer(composer2, SizeKt.m130height3ABfNKs(companion2, f));
                    composerImpl3.startReplaceGroup(101693408);
                    List list = shortcutSubCategory2.shortcuts;
                    int size = list.size();
                    int i4 = 0;
                    while (i4 < size) {
                        Shortcut shortcut2 = (Shortcut) list.get(i4);
                        composerImpl3.startReplaceGroup(101693955);
                        if (i4 > 0) {
                            Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(Modifier.Companion, f, 0.0f, i3);
                            MaterialTheme.INSTANCE.getClass();
                            i2 = i4;
                            shortcut = shortcut2;
                            DividerKt.m262HorizontalDivider9IZ8Weo(m126paddingVpY3zN4$default, 0.0f, MaterialTheme.getColorScheme(composer2).surfaceContainerHigh, composer2, 6, 2);
                        } else {
                            i2 = i4;
                            shortcut = shortcut2;
                        }
                        composerImpl3.end(false);
                        Modifier m126paddingVpY3zN4$default2 = PaddingKt.m126paddingVpY3zN4$default(Modifier.Companion, 0.0f, f, 1);
                        boolean z2 = z && shortcut.isCustomizable;
                        composerImpl3.startReplaceGroup(-1053171083);
                        Function1 function12 = function1;
                        boolean changed = composerImpl3.changed(function12) | composerImpl3.changedInstance(shortcutSubCategory2);
                        Object rememberedValue = composerImpl3.rememberedValue();
                        if (!changed) {
                            Composer.Companion.getClass();
                            if (rememberedValue != Composer.Companion.Empty) {
                                Function1 function13 = (Function1) rememberedValue;
                                composerImpl3.end(false);
                                Composer composer3 = composer2;
                                ShortcutHelperKt.Shortcut(m126paddingVpY3zN4$default2, str, shortcut, z2, function13, composer3, 6, 0);
                                composer2 = composer3;
                                i4 = i2 + 1;
                                size = size;
                                i3 = 2;
                            }
                        }
                        rememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda40(function12, shortcutSubCategory2, 1);
                        composerImpl3.updateRememberedValue(rememberedValue);
                        Function1 function132 = (Function1) rememberedValue;
                        composerImpl3.end(false);
                        Composer composer32 = composer2;
                        ShortcutHelperKt.Shortcut(m126paddingVpY3zN4$default2, str, shortcut, z2, function132, composer32, 6, 0);
                        composer2 = composer32;
                        i4 = i2 + 1;
                        size = size;
                        i3 = 2;
                    }
                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, false, true)) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 12582918, 120);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(str, shortcutSubCategory, z, function1, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda54
                public final /* synthetic */ String f$0;
                public final /* synthetic */ ShortcutSubCategory f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ Function1 f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    boolean z2 = this.f$2;
                    Function1 function12 = this.f$3;
                    ShortcutHelperKt.SubCategoryContainerDualPane(this.f$0, this.f$1, z2, function12, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SubCategoryTitle(String str, Composer composer, int i) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(2013630406);
        int i2 = i | (composerImpl2.changed(str) ? 4 : 2);
        if ((i2 & 3) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.SubCategoryTitle (ShortcutHelper.kt:612)");
            }
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            TextKt.m316Text4IGK_g(str, null, MaterialTheme.getColorScheme(composerImpl2).primary, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl2).titleSmall, composerImpl, i2 & 14, 0, 65530);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda1(str, i, 0);
        }
    }

    public static final void TitleBar(boolean z, Composer composer, final int i, final int i2) {
        boolean z2;
        int i3;
        final boolean z3;
        final String stringResource;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1292962478);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
            z2 = z;
        } else if ((i & 6) == 0) {
            z2 = z;
            i3 = (composerImpl.changed(z2) ? 4 : 2) | i;
        } else {
            z2 = z;
            i3 = i;
        }
        if ((i3 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            z3 = z2;
        } else {
            z3 = i4 != 0 ? false : z2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.TitleBar (ShortcutHelper.kt:995)");
            }
            if (z3) {
                composerImpl.startReplaceGroup(-778237397);
                stringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_customize_mode_title, composerImpl);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-778147590);
                stringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_title, composerImpl);
                composerImpl.end(false);
            }
            TopAppBarDefaults topAppBarDefaults = TopAppBarDefaults.INSTANCE;
            Color.Companion.getClass();
            long j = Color.Transparent;
            topAppBarDefaults.getClass();
            long j2 = Color.Unspecified;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors (AppBar.kt:1633)");
            }
            MaterialTheme.INSTANCE.getClass();
            TopAppBarColors defaultTopAppBarColors$material3_release = TopAppBarDefaults.getDefaultTopAppBarColors$material3_release(MaterialTheme.getColorScheme(composerImpl));
            TopAppBarColors m318copytNS2XkQ = defaultTopAppBarColors$material3_release.m318copytNS2XkQ(j, j2, j2, j2, j2, defaultTopAppBarColors$material3_release.subtitleContentColor);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            float f = 0;
            Dp.Companion companion = Dp.Companion;
            AppBarKt.m245CenterAlignedTopAppBarGHTll3U(ComposableLambdaKt.rememberComposableLambda(2143059, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$TitleBar$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.TitleBar.<anonymous> (ShortcutHelper.kt:1005)");
                    }
                    MaterialTheme.INSTANCE.getClass();
                    long j3 = MaterialTheme.getColorScheme(composer2).onSurface;
                    TextStyle textStyle = MaterialTheme.getTypography(composer2).headlineSmall;
                    TextOverflow.Companion.getClass();
                    int i5 = TextOverflow.Ellipsis;
                    TextAlign.Companion.getClass();
                    TextKt.m316Text4IGK_g(stringResource, null, j3, 0L, null, null, null, 0L, null, TextAlign.m805boximpl(TextAlign.Center), 0L, i5, false, 2, 0, null, textStyle, composer2, 0, 3120, 54778);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), null, null, null, 64, WindowInsetsKt.m147WindowInsetsa9UjIt4(f, f, f, f), m318copytNS2XkQ, null, composerImpl, 24582, 142);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda51
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ShortcutHelperKt.TitleBar(z3, (Composer) obj, updateChangedFlags, i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
