package com.android.systemui.keyboard.shortcut.ui.composable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.ScrollState;
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
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.foundation.lazy.LazyListInterval;
import androidx.compose.foundation.lazy.LazyListIntervalContent;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.foundation.lazy.LazyListStateKt;
import androidx.compose.foundation.selection.SelectableKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.CoreTextFieldKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.AddKt;
import androidx.compose.material.icons.filled.DeleteOutlineKt;
import androidx.compose.material.icons.filled.ExpandMoreKt;
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
import androidx.compose.runtime.State;
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
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
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
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
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
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCustomizationRequestInfo;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutIcon;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutSubCategory;
import com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt;
import com.android.systemui.keyboard.shortcut.ui.model.IconSource;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCategoryUi;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutsUiState;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
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
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public abstract class ShortcutHelperKt {

    /* renamed from: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutHelper$1, reason: invalid class name and case insensitive filesystem */
    public final class C08851 implements Function2 {
        public static final C08851 INSTANCE = new C08851();

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ((Number) obj2).intValue();
            ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj);
            composerImpl.startReplaceGroup(802821849);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelper.<anonymous> (ShortcutHelper.kt:139)");
            }
            composerImpl.startReplaceGroup(310118171);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.shouldUseSinglePane (ShortcutHelper.kt:204)");
            }
            boolean zHasCompactWindowSize = ShortcutHelperUtilsKt.hasCompactWindowSize(composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            return Boolean.valueOf(zHasCompactWindowSize);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:66:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0167  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ActiveShortcutHelper(ShortcutsUiState.Active active, C08851 c08851, Function1 function1, Function1 function12, Modifier modifier, Function0 function0, Function1 function13, Composer composer, int i) {
        int i2;
        Function1 function14;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1793347834);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(active) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(c08851) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            function14 = function12;
            i2 |= composerImpl.changedInstance(function14) ? 2048 : 1024;
        } else {
            function14 = function12;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl.changedInstance(function13) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((599187 & i2) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ActiveShortcutHelper (ShortcutHelper.kt:171)");
            }
            Object obj = active.defaultSelectedCategory;
            composerImpl.startReplaceGroup(580174340);
            boolean zChanged = composerImpl.changed(obj);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = SnapshotStateKt.mutableStateOf$default(active.defaultSelectedCategory);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                final MutableState mutableState = (MutableState) objRememberedValue;
                composerImpl.end(false);
                int i3 = i2 >> 3;
                if (((Boolean) c08851.invoke(composerImpl, Integer.valueOf(i3 & 14))).booleanValue()) {
                    composerImpl.startReplaceGroup(805697426);
                    List list = active.shortcutCategories;
                    ShortcutCategoryType shortcutCategoryType = (ShortcutCategoryType) mutableState.getValue();
                    composerImpl.startReplaceGroup(580186558);
                    boolean zChanged2 = composerImpl.changed(mutableState);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChanged2) {
                        companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            final int i4 = 0;
                            objRememberedValue2 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda28
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    ShortcutCategoryType shortcutCategoryType2 = (ShortcutCategoryType) obj2;
                                    switch (i4) {
                                        case 0:
                                            mutableState.setValue(shortcutCategoryType2);
                                            break;
                                        default:
                                            mutableState.setValue(shortcutCategoryType2);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        composerImpl.end(false);
                        ShortcutHelperSinglePane(active.searchQuery, function1, list, shortcutCategoryType, (Function1) objRememberedValue2, function0, modifier, composerImpl, (i3 & 112) | (i2 & 458752) | ((i2 << 6) & 3670016));
                        composerImpl.end(false);
                    }
                } else {
                    composerImpl.startReplaceGroup(806043572);
                    List list2 = active.shortcutCategories;
                    ShortcutCategoryType shortcutCategoryType2 = (ShortcutCategoryType) mutableState.getValue();
                    composerImpl.startReplaceGroup(580197374);
                    boolean zChanged3 = composerImpl.changed(mutableState);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (!zChanged3) {
                        companion.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            final int i5 = 1;
                            objRememberedValue3 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda28
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    ShortcutCategoryType shortcutCategoryType22 = (ShortcutCategoryType) obj2;
                                    switch (i5) {
                                        case 0:
                                            mutableState.setValue(shortcutCategoryType22);
                                            break;
                                        default:
                                            mutableState.setValue(shortcutCategoryType22);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        composerImpl.end(false);
                        int i6 = (i3 & 112) | (i2 & 458752) | ((i2 << 18) & 1879048192);
                        int i7 = ((i2 >> 12) & 14) | ((i2 >> 15) & 112);
                        ShortcutHelperTwoPane(active.searchQuery, function1, list2, shortcutCategoryType2, (Function1) objRememberedValue3, function0, active.isShortcutCustomizerFlagEnabled, active.shouldShowResetButton, active.isCustomizationModeEnabled, function14, modifier, function13, composerImpl, i6, i7);
                        composerImpl.end(false);
                    }
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda30(active, c08851, function1, function12, modifier, function0, function13, i);
        }
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
            Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(Modifier.Companion, 32);
            Color.Companion.getClass();
            long j = Color.Transparent;
            Icons.INSTANCE.getClass();
            IconSource iconSource = new IconSource(AddKt.getAdd(), null, 2, null);
            MaterialTheme.INSTANCE.getClass();
            float f = 0;
            SurfacesKt.m2594ShortcutHelperButton01TuoB8(function0, MaterialTheme.getColorScheme(composerImpl).primary, j, modifierM140size3ABfNKs, RoundedCornerShapeKt.CircleShape, iconSource, null, f, f, false, BorderStrokeKt.m31BorderStrokecXLIe8U(1, MaterialTheme.getColorScheme(composerImpl).outline), StringResources_androidKt.stringResource(R.string.shortcut_helper_add_shortcut_button_label, composerImpl), composerImpl, (i2 & 14) | 113249664, 576);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda18(i, 0, function0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x015a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(2);
            Modifier.Companion companion2 = Modifier.Companion;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_4, Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion2);
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
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(-432889622);
            int size = list.size();
            int i5 = 0;
            while (i5 < size) {
                final ShortcutCategoryUi shortcutCategoryUi = (ShortcutCategoryUi) list.get(i5);
                final boolean zAreEqual = Intrinsics.areEqual(shortcutCategoryType, shortcutCategoryUi.type);
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
                boolean zChanged = ((i4 & 7168) == i3) | composerImpl.changed(zAreEqual) | composerImpl.changedInstance(shortcutCategoryUi);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChanged) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda38
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                function1.mo781invoke(zAreEqual ? null : shortcutCategoryUi.type);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                }
                composerImpl.end(false);
                CategoryItemSinglePane(str2, shortcutCategoryUi, zAreEqual, (Function0) objRememberedValue, shape, composerImpl, i4 & 14);
                i5++;
                i3 = 2048;
                str2 = str;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda39
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    ShortcutHelperKt.CategoriesPanelSinglePane(str, list, shortcutCategoryType, function1, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
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
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(380139923);
            int size = list.size();
            int i4 = 0;
            while (i4 < size) {
                ShortcutCategoryUi shortcutCategoryUi = (ShortcutCategoryUi) list.get(i4);
                int i5 = size;
                String str = shortcutCategoryUi.label;
                boolean zAreEqual = Intrinsics.areEqual(shortcutCategoryType, shortcutCategoryUi.type);
                composerImpl.startReplaceGroup(-558737014);
                boolean zChangedInstance = ((i3 & 896) == i2) | composerImpl.changedInstance(shortcutCategoryUi);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda10(function1, shortcutCategoryUi, 2);
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                }
                composerImpl.end(false);
                CategoryItemTwoPane(str, shortcutCategoryUi.iconSource, zAreEqual, (Function0) objRememberedValue, null, composerImpl, 0);
                i4++;
                size = i5;
                i2 = 256;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(list, shortcutCategoryType, function1, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda58
                public final /* synthetic */ List f$0;
                public final /* synthetic */ ShortcutCategoryType f$1;
                public final /* synthetic */ Function1 f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    ShortcutHelperKt.CategoriesPanelTwoPane(this.f$0, this.f$1, this.f$2, (Composer) obj, iUpdateChangedFlags);
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
            SurfaceKt.m305Surfaceo_FOJdg(function0, null, false, shape, MaterialTheme.getColorScheme(composerImpl2).surfaceBright, 0L, 0.0f, null, null, ComposableLambdaKt.rememberComposableLambda(986513131, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.CategoryItemSinglePane.1
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
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.CategoryItemSinglePane.<anonymous> (ShortcutHelper.kt:296)");
                            }
                            Modifier.Companion companion = Modifier.Companion;
                            Arrangement.INSTANCE.getClass();
                            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                            Alignment.Companion.getClass();
                            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer2, 0);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
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
                            Updater.m337setimpl(composer2, columnMeasurePolicy, function2);
                            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function23);
                            }
                            Function2 function24 = ComposeUiNode.Companion.SetModifier;
                            Updater.m337setimpl(composer2, modifierMaterializeModifier, function24);
                            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                            Dp.Companion companion2 = Dp.Companion;
                            float f = 16;
                            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(SizeKt.m133heightInVpY3zN4$default(SizeKt.fillMaxWidth(companion, 1.0f), 88, 0.0f, 2), f, 0.0f, 2);
                            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composer2, 48);
                            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl4.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer2, modifierM127paddingVpY3zN4$default);
                            composerImpl4.startReusableNode();
                            if (composerImpl4.inserting) {
                                composerImpl4.createNode(function02);
                            } else {
                                composerImpl4.useNode();
                            }
                            Updater.m337setimpl(composer2, rowMeasurePolicy, function2);
                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                            if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl4, currentCompositeKeyHash2, function23);
                            }
                            Updater.m337setimpl(composer2, modifierMaterializeModifier2, function24);
                            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                            Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(companion, 24);
                            final ShortcutCategoryUi shortcutCategoryUi2 = shortcutCategoryUi;
                            ShortcutHelperKt.m2591ShortcutCategoryIconww6aTOc(shortcutCategoryUi2.iconSource, modifierM140size3ABfNKs, 0L, composer2, 48, 12);
                            SpacerKt.Spacer(composer2, SizeKt.m144width3ABfNKs(companion, f));
                            TextKt.m317Text4IGK_g(shortcutCategoryUi2.label, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer2, 0, 0, 131070);
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
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2), composerImpl, ((i2 >> 9) & 14) | ((i2 >> 3) & 7168), 998);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda16(str, shortcutCategoryUi, z, function0, shape, i, 1);
        }
    }

    public static final void CategoryItemTwoPane(final String str, final IconSource iconSource, final boolean z, final Function0 function0, NavigationDrawerItemColors navigationDrawerItemColors, Composer composer, final int i) {
        int i2;
        final NavigationDrawerItemColors navigationDrawerItemColorsM275colorsoq7We08;
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
                navigationDrawerItemColorsM275colorsoq7We08 = NavigationDrawerItemDefaults.m275colorsoq7We08(j, composerImpl);
            } else {
                composerImpl.skipToGroupEnd();
                i2 = i3 & (-57345);
                navigationDrawerItemColorsM275colorsoq7We08 = navigationDrawerItemColors;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.CategoryItemTwoPane (ShortcutHelper.kt:953)");
            }
            Modifier.Companion companion = Modifier.Companion;
            composerImpl.startReplaceGroup(991854767);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda3(9);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            composerImpl.end(false);
            Dp.Companion companion2 = Dp.Companion;
            final Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(SizeKt.m133heightInVpY3zN4$default(SemanticsModifierKt.semantics(companion, false, (Function1) objRememberedValue), 64, 0.0f, 2), 1.0f);
            float f = 28;
            final RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(f);
            final long j2 = ((Color) navigationDrawerItemColorsM275colorsoq7We08.containerColor(z, composerImpl, (i2 >> 6) & 14).getValue()).value;
            MaterialTheme.INSTANCE.getClass();
            final InteractionsConfig interactionsConfig = new InteractionsConfig(MaterialTheme.getColorScheme(composerImpl).onSurface, 0.11f, MaterialTheme.getColorScheme(composerImpl).onSurface, 0.15f, MaterialTheme.getColorScheme(composerImpl).secondary, 3, 2, f, 33, 0.0f, 0.0f, 1536, null);
            final ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-2084289772, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.CategoryItemTwoPane.2
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
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
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.CategoryItemTwoPane.<anonymous> (ShortcutHelper.kt:973)");
                            }
                            Modifier.Companion companion3 = Modifier.Companion;
                            float f2 = 24;
                            Dp.Companion companion4 = Dp.Companion;
                            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(companion3, f2, 0.0f, 2);
                            Alignment.Companion.getClass();
                            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                            Arrangement.INSTANCE.getClass();
                            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composer2, 48);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierM127paddingVpY3zN4$default);
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
                            Updater.m337setimpl(composer2, rowMeasurePolicy, function2);
                            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                            }
                            Function2 function24 = ComposeUiNode.Companion.SetModifier;
                            Updater.m337setimpl(composer2, modifierMaterializeModifier, function24);
                            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                            Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(companion3, f2);
                            NavigationDrawerItemColors navigationDrawerItemColors3 = navigationDrawerItemColorsM275colorsoq7We08;
                            boolean z2 = z;
                            ShortcutHelperKt.m2591ShortcutCategoryIconww6aTOc(iconSource, modifierM140size3ABfNKs, ((Color) navigationDrawerItemColors3.iconColor(z2, composer2).getValue()).value, composer2, 432, 0);
                            SpacerKt.Spacer(composer2, SizeKt.m144width3ABfNKs(companion3, 12));
                            Modifier modifierWeight = rowScopeInstance.weight(companion3, 1.0f, true);
                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer2, modifierWeight);
                            composerImpl3.startReusableNode();
                            if (composerImpl3.inserting) {
                                composerImpl3.createNode(function02);
                            } else {
                                composerImpl3.useNode();
                            }
                            Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function23);
                            }
                            Updater.m337setimpl(composer2, modifierMaterializeModifier2, function24);
                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                            long sp = TextUnitKt.getSp(18);
                            long j3 = ((Color) navigationDrawerItemColors3.textColor(z2, composer2).getValue()).value;
                            MaterialTheme.INSTANCE.getClass();
                            TextStyle textStyle = MaterialTheme.getTypography(composer2).titleSmall;
                            Hyphens.Companion.getClass();
                            TextKt.m317Text4IGK_g(str, null, j3, sp, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, TextStyle.m756copyp1EtxEg$default(textStyle, 0L, 0L, null, null, 0L, 0, 0L, null, null, Hyphens.Auto, 12582911), composer2, 3072, 0, 65522);
                            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, true, true)) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl);
            composerImpl.startReplaceGroup(-612515667);
            long jM259contentColorForek8zF_U = ColorSchemeKt.m259contentColorForek8zF_U(j2, composerImpl);
            final float f2 = 0;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.SelectableShortcutSurface (Surfaces.kt:105)");
            }
            composerImpl.startReplaceGroup(-1355790970);
            composerImpl.startReplaceGroup(-1355790319);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            final MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) objRememberedValue2;
            composerImpl.end(false);
            composerImpl.end(false);
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = SurfaceKt.LocalAbsoluteTonalElevation;
            final float f3 = ((Dp) composerImpl.consume(dynamicProvidableCompositionLocal)).value + f2;
            ProvidedValue[] providedValueArr = {ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(jM259contentColorForek8zF_U)), dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Dp.m837boximpl(f3))};
            NavigationDrawerItemColors navigationDrawerItemColors3 = navigationDrawerItemColorsM275colorsoq7We08;
            final BorderStroke borderStroke = null;
            final boolean z2 = true;
            CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableLambdaKt.rememberComposableLambda(247649261, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt$SelectableShortcutSurface$1
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
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
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.SelectableShortcutSurface.<anonymous> (Surfaces.kt:113)");
                            }
                            MutableState mutableStateCollectIsFocusedAsState = FocusInteractionKt.collectIsFocusedAsState(mutableInteractionSource, composer2, 0);
                            StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                            Modifier modifierThen = modifierFillMaxWidth.then(MinimumInteractiveModifier.INSTANCE);
                            long jM2597access$surfaceColorAtElevationCLU3JFs = SurfacesKt.m2597access$surfaceColorAtElevationCLU3JFs(j2, f3, composer2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            Modifier modifierM182selectableO2vRcR0 = SelectableKt.m182selectableO2vRcR0(SurfacesKt.m2596access$surfaceXOJAsU(modifierThen, roundedCornerShapeM187RoundedCornerShape0680j_4, jM2597access$surfaceColorAtElevationCLU3JFs, borderStroke, ((Density) composerImpl3.consume(CompositionLocalsKt.LocalDensity)).mo58toPx0680j_4(f2)), z, mutableInteractionSource, new ShortcutHelperIndication(interactionsConfig), z2, null, function0);
                            if (((Boolean) mutableStateCollectIsFocusedAsState.getValue()).booleanValue()) {
                                modifierM182selectableO2vRcR0 = modifierM182selectableO2vRcR0.then(ZIndexModifierKt.zIndex(Modifier.Companion, 1.0f));
                            }
                            Alignment.Companion.getClass();
                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, true);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierM182selectableO2vRcR0);
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
                            Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                            }
                            Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                            composableLambdaImplRememberComposableLambda.invoke(composerImpl3, 0);
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
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            navigationDrawerItemColors2 = navigationDrawerItemColors3;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(str, iconSource, z, function0, navigationDrawerItemColors2, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda64
                public final /* synthetic */ String f$0;
                public final /* synthetic */ IconSource f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ Function0 f$3;
                public final /* synthetic */ NavigationDrawerItemColors f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Function0 function02 = this.f$3;
                    NavigationDrawerItemColors navigationDrawerItemColors4 = this.f$4;
                    ShortcutHelperKt.CategoryItemTwoPane(this.f$0, this.f$1, this.f$2, function02, navigationDrawerItemColors4, (Composer) obj, iUpdateChangedFlags);
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
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            if (z) {
                composerImpl.startReplaceGroup(423370877);
                composerImpl.startReplaceGroup(13657489);
                if (z2) {
                    ResetButton(function02, composerImpl, (i2 >> 9) & 14);
                    Dp.Companion companion = Dp.Companion;
                    SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(Modifier.Companion, 8));
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda50
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Function0 function04 = function02;
                    Modifier modifier2 = modifier;
                    ShortcutHelperKt.CustomizationButtonsContainer(z, z2, function0, function04, modifier2, (Composer) obj, iUpdateChangedFlags);
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
            Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(Modifier.Companion, 40, 0.0f, 2);
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
            Icons.INSTANCE.getClass();
            ImageVector imageVectorBuild = TuneKt._tune;
            ?? r8 = 0;
            if (imageVectorBuild == null) {
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
                builder.m567addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i4, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVectorBuild = builder.build();
                TuneKt._tune = imageVectorBuild;
                i3 = 2;
                r8 = 0;
            }
            SurfacesKt.m2594ShortcutHelperButton01TuoB8(function0, MaterialTheme.getColorScheme(composerImpl).onSecondaryContainer, j, modifierM133heightInVpY3zN4$default, null, new IconSource(imageVectorBuild, r8, i3, r8), StringResources_androidKt.stringResource(R.string.shortcut_helper_customize_button_text, composerImpl), 0.0f, 0.0f, false, null, null, composerImpl, (i2 & 14) | 3072, 3984);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda53(function0, i, 0);
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
            Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(Modifier.Companion, 32);
            Color.Companion.getClass();
            long j = Color.Transparent;
            Icons.INSTANCE.getClass();
            ImageVector imageVectorBuild = DeleteOutlineKt._deleteOutline;
            if (imageVectorBuild != null) {
                i2 = 2;
            } else {
                ImageVector.Builder builder = new ImageVector.Builder("Filled.DeleteOutline", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i4 = StrokeJoin.Bevel;
                PathBuilder pathBuilderM = ShortcutHelperKt$$ExternalSyntheticOutline0.m(6.0f, 19.0f);
                pathBuilderM.curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f);
                pathBuilderM.horizontalLineToRelative(8.0f);
                pathBuilderM.curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f);
                pathBuilderM.lineTo(18.0f, 7.0f);
                pathBuilderM.lineTo(6.0f, 7.0f);
                pathBuilderM.verticalLineToRelative(12.0f);
                pathBuilderM.close();
                pathBuilderM.moveTo(8.0f, 9.0f);
                pathBuilderM.horizontalLineToRelative(8.0f);
                pathBuilderM.verticalLineToRelative(10.0f);
                ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(pathBuilderM, 8.0f, 19.0f, 8.0f, 9.0f);
                pathBuilderM.moveTo(15.5f, 4.0f);
                pathBuilderM.lineToRelative(-1.0f, -1.0f);
                pathBuilderM.horizontalLineToRelative(-5.0f);
                pathBuilderM.lineToRelative(-1.0f, 1.0f);
                pathBuilderM.lineTo(5.0f, 4.0f);
                pathBuilderM.verticalLineToRelative(2.0f);
                pathBuilderM.horizontalLineToRelative(14.0f);
                pathBuilderM.lineTo(19.0f, 4.0f);
                pathBuilderM.close();
                builder.m567addPathoIyEayM("", pathBuilderM._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i4, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVectorBuild = builder.build();
                DeleteOutlineKt._deleteOutline = imageVectorBuild;
                i2 = 2;
            }
            IconSource iconSource = new IconSource(imageVectorBuild, null, i2, null);
            MaterialTheme.INSTANCE.getClass();
            float f = 0;
            function02 = function0;
            SurfacesKt.m2594ShortcutHelperButton01TuoB8(function02, MaterialTheme.getColorScheme(composerImpl).primary, j, modifierM140size3ABfNKs, RoundedCornerShapeKt.CircleShape, iconSource, null, f, f, false, BorderStrokeKt.m31BorderStrokecXLIe8U(1, MaterialTheme.getColorScheme(composerImpl).outline), StringResources_androidKt.stringResource(R.string.shortcut_helper_delete_shortcut_button_label, composerImpl), composerImpl, (i3 & 14) | 113249664, 576);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda18(i, 1, function02);
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
            Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(Modifier.Companion, 40, 0.0f, 2);
            MaterialTheme.INSTANCE.getClass();
            SurfacesKt.m2594ShortcutHelperButton01TuoB8(function0, MaterialTheme.getColorScheme(composerImpl).onPrimary, MaterialTheme.getColorScheme(composerImpl).primary, modifierM133heightInVpY3zN4$default, null, null, StringResources_androidKt.stringResource(R.string.shortcut_helper_done_button_text, composerImpl), 0.0f, 0.0f, false, null, null, composerImpl, (i2 & 14) | 3072, 4016);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda53(function0, i, 2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0129  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void EndSidePanel(final String str, final Modifier modifier, final ShortcutCategoryUi shortcutCategoryUi, final boolean z, final Function1 function1, Composer composer, final int i) {
        int i2;
        final String str2;
        final ShortcutCategoryUi shortcutCategoryUi2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-701615244);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(shortcutCategoryUi) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(z) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 16384 : 8192;
        }
        if ((i2 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            str2 = str;
            shortcutCategoryUi2 = shortcutCategoryUi;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.EndSidePanel (ShortcutHelper.kt:509)");
            }
            LazyListState lazyListStateRememberLazyListState = LazyListStateKt.rememberLazyListState(composerImpl);
            composerImpl.startReplaceGroup(-1556852045);
            boolean zChangedInstance = composerImpl.changedInstance(shortcutCategoryUi) | composerImpl.changed(lazyListStateRememberLazyListState);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChangedInstance) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new ShortcutHelperKt$EndSidePanel$2$1(shortcutCategoryUi, lazyListStateRememberLazyListState, null);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                EffectsKt.LaunchedEffect(composerImpl, shortcutCategoryUi, (Function2) objRememberedValue);
                composerImpl.startReplaceGroup(-1556849968);
                if (shortcutCategoryUi == null) {
                    Dp.Companion companion2 = Dp.Companion;
                    m2590NoSearchResultsTextkHDZbjc(24, false, composerImpl, 54);
                    composerImpl.end(false);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                        final int i3 = 0;
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda44
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                switch (i3) {
                                    case 0:
                                        ((Integer) obj2).getClass();
                                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                        boolean z2 = z;
                                        Function1 function12 = function1;
                                        ShortcutHelperKt.EndSidePanel(str, modifier, shortcutCategoryUi, z2, function12, (Composer) obj, iUpdateChangedFlags);
                                        break;
                                    default:
                                        ((Integer) obj2).getClass();
                                        int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                        boolean z3 = z;
                                        Function1 function13 = function1;
                                        ShortcutHelperKt.EndSidePanel(str, modifier, shortcutCategoryUi, z3, function13, (Composer) obj, iUpdateChangedFlags2);
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                str2 = str;
                shortcutCategoryUi2 = shortcutCategoryUi;
                composerImpl.end(false);
                composerImpl.startReplaceGroup(-1556843305);
                boolean zChangedInstance2 = ((i2 & 14) == 4) | composerImpl.changedInstance(shortcutCategoryUi2) | ((i2 & 7168) == 2048) | ((57344 & i2) == 16384);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda45
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                final ShortcutCategoryUi shortcutCategoryUi3 = shortcutCategoryUi2;
                                final List list = shortcutCategoryUi3.subCategories;
                                final ShortcutHelperKt$EndSidePanel$lambda$64$lambda$63$$inlined$items$default$1 shortcutHelperKt$EndSidePanel$lambda$64$lambda$63$$inlined$items$default$1 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$EndSidePanel$lambda$64$lambda$63$$inlined$items$default$1
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                                        return null;
                                    }
                                };
                                int size = list.size();
                                Function1 function12 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$EndSidePanel$lambda$64$lambda$63$$inlined$items$default$3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj2) {
                                        return shortcutHelperKt$EndSidePanel$lambda$64$lambda$63$$inlined$items$default$1.mo781invoke(list.get(((Number) obj2).intValue()));
                                    }
                                };
                                final boolean z2 = z;
                                final Function1 function13 = function1;
                                final String str3 = str2;
                                ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(-632812321, true, new Function4() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$EndSidePanel$lambda$64$lambda$63$$inlined$items$default$4
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(4);
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:30:0x0095  */
                                    @Override // kotlin.jvm.functions.Function4
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj2, Object obj3, Object obj4, Object obj5) {
                                        int i4;
                                        LazyItemScope lazyItemScope = (LazyItemScope) obj2;
                                        int iIntValue = ((Number) obj3).intValue();
                                        Composer composer2 = (Composer) obj4;
                                        int iIntValue2 = ((Number) obj5).intValue();
                                        if ((iIntValue2 & 6) == 0) {
                                            i4 = (((ComposerImpl) composer2).changed(lazyItemScope) ? 4 : 2) | iIntValue2;
                                        } else {
                                            i4 = iIntValue2;
                                        }
                                        if ((iIntValue2 & 48) == 0) {
                                            i4 |= ((ComposerImpl) composer2).changed(iIntValue) ? 32 : 16;
                                        }
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        if (composerImpl2.shouldExecute(i4 & 1, (i4 & 147) != 146)) {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:178)");
                                            }
                                            ShortcutSubCategory shortcutSubCategory = (ShortcutSubCategory) list.get(iIntValue);
                                            composerImpl2.startReplaceGroup(-463672124);
                                            String str4 = str3;
                                            boolean includeInCustomization = z2 & shortcutCategoryUi3.type.getIncludeInCustomization();
                                            composerImpl2.startReplaceGroup(400692668);
                                            boolean zChanged = composerImpl2.changed(function13) | composerImpl2.changedInstance(shortcutCategoryUi3);
                                            Object objRememberedValue3 = composerImpl2.rememberedValue();
                                            if (!zChanged) {
                                                Composer.Companion.getClass();
                                                if (objRememberedValue3 == Composer.Companion.Empty) {
                                                    final Function1 function14 = function13;
                                                    final ShortcutCategoryUi shortcutCategoryUi4 = shortcutCategoryUi3;
                                                    objRememberedValue3 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$EndSidePanel$4$1$1$1$1
                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj6) {
                                                            ShortcutCustomizationRequestInfo shortcutCustomizationRequestInfo = (ShortcutCustomizationRequestInfo) obj6;
                                                            boolean z3 = shortcutCustomizationRequestInfo instanceof ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Add;
                                                            ShortcutCategoryUi shortcutCategoryUi5 = shortcutCategoryUi4;
                                                            Function1 function15 = function14;
                                                            if (z3) {
                                                                function15.mo781invoke(ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Add.copy$default((ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Add) shortcutCustomizationRequestInfo, shortcutCategoryUi5.type, null, 13));
                                                            } else if (shortcutCustomizationRequestInfo instanceof ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Delete) {
                                                                function15.mo781invoke(ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Delete.copy$default((ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Delete) shortcutCustomizationRequestInfo, shortcutCategoryUi5.type, null, 29));
                                                            } else {
                                                                if (!Intrinsics.areEqual(shortcutCustomizationRequestInfo, ShortcutCustomizationRequestInfo.Reset.INSTANCE)) {
                                                                    throw new NoWhenBranchMatchedException();
                                                                }
                                                                function15.mo781invoke(shortcutCustomizationRequestInfo);
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    };
                                                    composerImpl2.updateRememberedValue(objRememberedValue3);
                                                }
                                                composerImpl2.end(false);
                                                ShortcutHelperKt.SubCategoryContainerDualPane(str4, shortcutSubCategory, includeInCustomization, (Function1) objRememberedValue3, composerImpl2, 0);
                                                Dp.Companion companion3 = Dp.Companion;
                                                SpacerKt.Spacer(composerImpl2, SizeKt.m131height3ABfNKs(Modifier.Companion, 8));
                                                composerImpl2.end(false);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        } else {
                                            composerImpl2.skipToGroupEnd();
                                        }
                                        return Unit.INSTANCE;
                                    }
                                });
                                LazyListIntervalContent lazyListIntervalContent = (LazyListIntervalContent) ((LazyListScope) obj);
                                lazyListIntervalContent.getClass();
                                lazyListIntervalContent.intervals.addInterval(size, new LazyListInterval(null, function12, composableLambdaImpl));
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    LazyDslKt.LazyColumn(modifier, lazyListStateRememberLazyListState, null, false, null, null, null, false, null, (Function1) objRememberedValue2, composerImpl, (i2 >> 3) & 14, 508);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            final int i4 = 1;
            final String str3 = str2;
            final ShortcutCategoryUi shortcutCategoryUi3 = shortcutCategoryUi2;
            recomposeScopeImplEndRestartGroup2.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda44
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    switch (i4) {
                        case 0:
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            boolean z2 = z;
                            Function1 function12 = function1;
                            ShortcutHelperKt.EndSidePanel(str3, modifier, shortcutCategoryUi3, z2, function12, (Composer) obj, iUpdateChangedFlags);
                            break;
                        default:
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            boolean z3 = z;
                            Function1 function13 = function1;
                            ShortcutHelperKt.EndSidePanel(str3, modifier, shortcutCategoryUi3, z3, function13, (Composer) obj, iUpdateChangedFlags2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: KeyboardSettings-ixp7dh8, reason: not valid java name */
    public static final void m2589KeyboardSettingsixp7dh8(final float f, final float f2, Function0 function0, Composer composer, final int i) {
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
            RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(f3);
            Color.Companion.getClass();
            long j = Color.Transparent;
            Modifier.Companion companion2 = Modifier.Companion;
            composerImpl.startReplaceGroup(-657284153);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda3(7);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            composerImpl.end(false);
            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(SizeKt.fillMaxWidth(SemanticsModifierKt.semantics(companion2, false, (Function1) objRememberedValue), 1.0f), 12, 0.0f, 2);
            MaterialTheme.INSTANCE.getClass();
            float f4 = 8;
            InteractionsConfig interactionsConfig = new InteractionsConfig(MaterialTheme.getColorScheme(composerImpl).onSurface, 0.11f, MaterialTheme.getColorScheme(composerImpl).onSurface, 0.15f, MaterialTheme.getColorScheme(composerImpl).secondary, 3, f4, f3, 28, f4, 0.0f, 1024, null);
            ComposableSingletons$ShortcutHelperKt.INSTANCE.getClass();
            function02 = function0;
            SurfacesKt.m2593ClickableShortcutSurface9FW6N_Y(function02, modifierM127paddingVpY3zN4$default, false, roundedCornerShapeM187RoundedCornerShape0680j_4, j, null, interactionsConfig, ComposableSingletons$ShortcutHelperKt.f47lambda4, composerImpl, ((i2 >> 6) & 14) | 24576, 996);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda49
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ShortcutHelperKt.m2589KeyboardSettingsixp7dh8(f, f2, function02, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: NoSearchResultsText-kHDZbjc, reason: not valid java name */
    public static final void m2590NoSearchResultsTextkHDZbjc(final float f, final boolean z, Composer composer, final int i) {
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
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            if (z) {
                modifierFillMaxWidth = modifierFillMaxWidth.then(SizeKt.FillWholeMaxHeight);
            }
            String strStringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_no_search_results, composerImpl2);
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).bodyMedium;
            long j = MaterialTheme.getColorScheme(composerImpl2).onSurface;
            Dp.Companion companion = Dp.Companion;
            composerImpl = composerImpl2;
            TextKt.m317Text4IGK_g(strStringResource, PaddingKt.m126paddingVpY3zN4(BackgroundKt.m26backgroundbw27NRU(PaddingKt.m127paddingVpY3zN4$default(modifierFillMaxWidth, 0.0f, 8, 1), MaterialTheme.getColorScheme(composerImpl2).surfaceBright, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(28)), f, 24), j, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyle, composerImpl, 0, 0, 65528);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(f, z, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda52
                public final /* synthetic */ float f$0;
                public final /* synthetic */ boolean f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(55);
                    ShortcutHelperKt.m2590NoSearchResultsTextkHDZbjc(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
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
            Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(Modifier.Companion, 40, 0.0f, 2);
            Color.Companion.getClass();
            long j = Color.Transparent;
            Icons.INSTANCE.getClass();
            ImageVector imageVectorBuild = RefreshKt._refresh;
            if (imageVectorBuild == null) {
                ImageVector.Builder builder = new ImageVector.Builder("Filled.Refresh", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i3 = StrokeJoin.Bevel;
                PathBuilder pathBuilderM = ShortcutHelperKt$$ExternalSyntheticOutline0.m(17.65f, 6.35f);
                pathBuilderM.curveTo(16.2f, 4.9f, 14.21f, 4.0f, 12.0f, 4.0f);
                pathBuilderM.curveToRelative(-4.42f, 0.0f, -7.99f, 3.58f, -7.99f, 8.0f);
                pathBuilderM.reflectiveCurveToRelative(3.57f, 8.0f, 7.99f, 8.0f);
                pathBuilderM.curveToRelative(3.73f, 0.0f, 6.84f, -2.55f, 7.73f, -6.0f);
                pathBuilderM.horizontalLineToRelative(-2.08f);
                pathBuilderM.curveToRelative(-0.82f, 2.33f, -3.04f, 4.0f, -5.65f, 4.0f);
                pathBuilderM.curveToRelative(-3.31f, 0.0f, -6.0f, -2.69f, -6.0f, -6.0f);
                pathBuilderM.reflectiveCurveToRelative(2.69f, -6.0f, 6.0f, -6.0f);
                pathBuilderM.curveToRelative(1.66f, 0.0f, 3.14f, 0.69f, 4.22f, 1.78f);
                pathBuilderM.lineTo(13.0f, 11.0f);
                pathBuilderM.horizontalLineToRelative(7.0f);
                pathBuilderM.verticalLineTo(4.0f);
                pathBuilderM.lineToRelative(-2.35f, 2.35f);
                pathBuilderM.close();
                builder.m567addPathoIyEayM("", pathBuilderM._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i3, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVectorBuild = builder.build();
                RefreshKt._refresh = imageVectorBuild;
            }
            IconSource iconSource = new IconSource(imageVectorBuild, null, 2, null);
            String strStringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_reset_button_text, composerImpl);
            MaterialTheme.INSTANCE.getClass();
            SurfacesKt.m2594ShortcutHelperButton01TuoB8(function0, MaterialTheme.getColorScheme(composerImpl).primary, j, modifierM133heightInVpY3zN4$default, null, iconSource, strStringResource, 0.0f, 0.0f, false, BorderStrokeKt.m31BorderStrokecXLIe8U(1, MaterialTheme.getColorScheme(composerImpl).outlineVariant), null, composerImpl, (i2 & 14) | 3456, 2960);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda53(function0, i, 1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void RotatingExpandCollapseIcon(final boolean z, Composer composer, final int i) {
        String strStringResource;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1532057329);
        if ((((composerImpl.changed(z) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.RotatingExpandCollapseIcon (ShortcutHelper.kt:329)");
            }
            State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(z ? 180.0f : 0.0f, null, "Expand icon rotation animation", null, composerImpl, 3072, 22);
            Modifier.Companion companion = Modifier.Companion;
            MaterialTheme.INSTANCE.getClass();
            Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(companion, MaterialTheme.getColorScheme(composerImpl).surfaceContainerHigh, RoundedCornerShapeKt.CircleShape);
            composerImpl.startReplaceGroup(-435222328);
            boolean zChanged = composerImpl.changed(stateAnimateFloatAsState);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda4(stateAnimateFloatAsState, 2);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifierM26backgroundbw27NRU, (Function1) objRememberedValue);
                Icons.INSTANCE.getClass();
                ImageVector expandMore = ExpandMoreKt.getExpandMore();
                if (z) {
                    composerImpl.startReplaceGroup(-606841447);
                    strStringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_content_description_collapse_icon, composerImpl);
                    composerImpl.end(false);
                } else {
                    composerImpl.startReplaceGroup(-606730405);
                    strStringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_content_description_expand_icon, composerImpl);
                    composerImpl.end(false);
                }
                IconKt.m271Iconww6aTOc(expandMore, strStringResource, modifierGraphicsLayer, MaterialTheme.getColorScheme(composerImpl).onSurface, composerImpl, 0, 0);
                composerImpl = composerImpl;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(z, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda61
                public final /* synthetic */ boolean f$0;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    ShortcutHelperKt.RotatingExpandCollapseIcon(this.f$0, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:143:0x0356  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:147:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0170  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Shortcut(final Modifier modifier, final String str, final Shortcut shortcut, boolean z, Function1 function1, Composer composer, final int i, final int i2) {
        int i3;
        boolean z2;
        int i4;
        Function1 function12;
        Function1 function13;
        Object objM;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        MutableState mutableStateCollectIsFocusedAsState;
        Modifier modifierThen;
        ComposerImpl composerImpl;
        boolean zChangedInstance;
        Object objRememberedValue;
        boolean z3;
        boolean z4;
        Function1 function14;
        boolean z5;
        boolean z6;
        final boolean z7;
        final Function1 function15;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1212601198);
        if ((i & 6) == 0) {
            i3 = (composerImpl2.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl2.changed(str) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl2.changedInstance(shortcut) ? 256 : 128;
        }
        int i5 = i2 & 8;
        if (i5 != 0) {
            i3 |= 3072;
        } else {
            if ((i & 3072) == 0) {
                z2 = z;
                i3 |= composerImpl2.changed(z2) ? 2048 : 1024;
            }
            i4 = i2 & 16;
            if (i4 != 0) {
                if ((i & 24576) == 0) {
                    function12 = function1;
                    i3 |= composerImpl2.changedInstance(function12) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                if ((i3 & 9363) == 9362 && composerImpl2.getSkipping()) {
                    composerImpl2.skipToGroupEnd();
                    composerImpl = composerImpl2;
                    function15 = function12;
                    z7 = z2;
                } else {
                    if (i5 != 0) {
                        z2 = false;
                    }
                    Composer.Companion companion = Composer.Companion;
                    if (i4 == 0) {
                        Object objM2 = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, -1781350129, companion);
                        if (objM2 == Composer.Companion.Empty) {
                            objM2 = new ShortcutHelperKt$$ExternalSyntheticLambda3(0);
                            composerImpl2.updateRememberedValue(objM2);
                        }
                        function13 = (Function1) objM2;
                        composerImpl2.end(false);
                    } else {
                        function13 = function12;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.Shortcut (ShortcutHelper.kt:627)");
                    }
                    objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, -1781348940, companion);
                    composer$Companion$Empty$1 = Composer.Companion.Empty;
                    if (objM == composer$Companion$Empty$1) {
                        objM = InteractionSourceKt.MutableInteractionSource();
                        composerImpl2.updateRememberedValue(objM);
                    }
                    MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) objM;
                    composerImpl2.end(false);
                    mutableStateCollectIsFocusedAsState = FocusInteractionKt.collectIsFocusedAsState(mutableInteractionSource, composerImpl2, 6);
                    MaterialTheme.INSTANCE.getClass();
                    long j = MaterialTheme.getColorScheme(composerImpl2).secondary;
                    if (((Boolean) mutableStateCollectIsFocusedAsState.getValue()).booleanValue()) {
                        modifierThen = modifier;
                    } else {
                        Dp.Companion companion2 = Dp.Companion;
                        modifierThen = modifier.then(BorderKt.m28borderxT4_qwU(Modifier.Companion, 3, j, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(16)));
                    }
                    Dp.Companion companion3 = Dp.Companion;
                    Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(FocusableKt.focusable$default(modifierThen, false, mutableInteractionSource, 1), 8);
                    composerImpl = composerImpl2;
                    composerImpl.startReplaceGroup(-1781333791);
                    zChangedInstance = composerImpl.changedInstance(shortcut);
                    objRememberedValue = composerImpl.rememberedValue();
                    if (!zChangedInstance || objRememberedValue == composer$Companion$Empty$1) {
                        z3 = false;
                        objRememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda4(shortcut, 0);
                        composerImpl.updateRememberedValue(objRememberedValue);
                    } else {
                        z3 = false;
                    }
                    composerImpl.end(z3);
                    Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM125padding3ABfNKs, true, (Function1) objRememberedValue);
                    Arrangement.INSTANCE.getClass();
                    Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
                    Alignment.Companion.getClass();
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Start$1, Alignment.Companion.Top, composerImpl, 0);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierSemantics);
                    ComposeUiNode.Companion.getClass();
                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl.applier != null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                    Updater.m337setimpl(composerImpl, rowMeasurePolicy, function2);
                    Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
                    }
                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    Modifier.Companion companion4 = Modifier.Companion;
                    Modifier modifierM144width3ABfNKs = SizeKt.m144width3ABfNKs(companion4, 128);
                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                    int i6 = i3;
                    boolean z8 = z2;
                    Modifier modifierWeight = rowScopeInstance.weight(rowScopeInstance.align(modifierM144width3ABfNKs, vertical), 0.333f, true);
                    composerImpl.startReplaceGroup(320475083);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (objRememberedValue2 == composer$Companion$Empty$1) {
                        objRememberedValue2 = new ShortcutHelperKt$$ExternalSyntheticLambda3(8);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    Modifier modifierSemantics2 = SemanticsModifierKt.semantics(modifierWeight, false, (Function1) objRememberedValue2);
                    RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(Arrangement.m92spacedBy0680j_4(16), vertical, composerImpl, 54);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierSemantics2);
                    composerImpl.startReusableNode();
                    Function1 function16 = function13;
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, rowMeasurePolicy2, function2);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
                    composerImpl.startReplaceGroup(312498979);
                    ShortcutIcon shortcutIcon = shortcut.icon;
                    if (shortcutIcon != null) {
                        Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(companion4, 24);
                        composerImpl.startReplaceGroup(312503782);
                        Object objRememberedValue3 = composerImpl.rememberedValue();
                        if (objRememberedValue3 == composer$Companion$Empty$1) {
                            objRememberedValue3 = new ShortcutHelperKt$$ExternalSyntheticLambda3(10);
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        z4 = false;
                        composerImpl.end(false);
                        ShortcutIcon(shortcutIcon, SemanticsModifierKt.semantics(modifierM140size3ABfNKs, false, (Function1) objRememberedValue3), composerImpl, 0);
                    } else {
                        z4 = false;
                    }
                    Object objM3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, z4, 312510950);
                    if (objM3 == composer$Companion$Empty$1) {
                        objM3 = new ShortcutHelperKt$$ExternalSyntheticLambda3(11);
                        composerImpl.updateRememberedValue(objM3);
                    }
                    composerImpl.end(false);
                    int i7 = i6 >> 3;
                    ShortcutDescriptionText(str, shortcut, SemanticsModifierKt.semantics(companion4, false, (Function1) objM3), composerImpl, i7 & 126);
                    composerImpl.end(true);
                    Modifier modifierM144width3ABfNKs2 = SizeKt.m144width3ABfNKs(companion4, 24);
                    composerImpl.startReplaceGroup(320497607);
                    Object objRememberedValue4 = composerImpl.rememberedValue();
                    if (objRememberedValue4 == composer$Companion$Empty$1) {
                        objRememberedValue4 = new ShortcutHelperKt$$ExternalSyntheticLambda3(12);
                        composerImpl.updateRememberedValue(objRememberedValue4);
                    }
                    composerImpl.end(false);
                    SpacerKt.Spacer(composerImpl, SemanticsModifierKt.semantics(modifierM144width3ABfNKs2, false, (Function1) objRememberedValue4));
                    Modifier modifierWeight2 = rowScopeInstance.weight(companion4, 0.666f, true);
                    composerImpl.startReplaceGroup(320501383);
                    Object objRememberedValue5 = composerImpl.rememberedValue();
                    if (objRememberedValue5 == composer$Companion$Empty$1) {
                        objRememberedValue5 = new ShortcutHelperKt$$ExternalSyntheticLambda3(13);
                        composerImpl.updateRememberedValue(objRememberedValue5);
                    }
                    composerImpl.end(false);
                    Modifier modifierSemantics3 = SemanticsModifierKt.semantics(modifierWeight2, false, (Function1) objRememberedValue5);
                    composerImpl.startReplaceGroup(320506236);
                    int i8 = i6 & 57344;
                    boolean zChangedInstance2 = (i8 == 16384) | composerImpl.changedInstance(shortcut);
                    Object objRememberedValue6 = composerImpl.rememberedValue();
                    if (zChangedInstance2 || objRememberedValue6 == composer$Companion$Empty$1) {
                        function14 = function16;
                        z5 = false;
                        objRememberedValue6 = new ShortcutHelperKt$$ExternalSyntheticLambda10(function14, shortcut, 0);
                        composerImpl.updateRememberedValue(objRememberedValue6);
                    } else {
                        function14 = function16;
                        z5 = false;
                    }
                    Function0 function02 = (Function0) objRememberedValue6;
                    composerImpl.end(z5);
                    composerImpl.startReplaceGroup(320518430);
                    boolean zChangedInstance3 = (i8 == 16384) | composerImpl.changedInstance(shortcut);
                    Object objRememberedValue7 = composerImpl.rememberedValue();
                    if (zChangedInstance3 || objRememberedValue7 == composer$Companion$Empty$1) {
                        z6 = true;
                        objRememberedValue7 = new ShortcutHelperKt$$ExternalSyntheticLambda10(function14, shortcut, 1);
                        composerImpl.updateRememberedValue(objRememberedValue7);
                    } else {
                        z6 = true;
                    }
                    composerImpl.end(false);
                    ShortcutKeyCombinations(modifierSemantics3, shortcut, z8, function02, (Function0) objRememberedValue7, composerImpl, i7 & EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS);
                    composerImpl.end(z6);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    z7 = z8;
                    function15 = function14;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda12
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            Function1 function17 = function15;
                            ShortcutHelperKt.Shortcut(modifier, str, shortcut, z7, function17, (Composer) obj, iUpdateChangedFlags, i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i3 |= 24576;
            function12 = function1;
            if ((i3 & 9363) == 9362) {
                if (i5 != 0) {
                }
                Composer.Companion companion5 = Composer.Companion;
                if (i4 == 0) {
                }
                if (ComposerKt.isTraceInProgress()) {
                }
                objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, -1781348940, companion5);
                composer$Companion$Empty$1 = Composer.Companion.Empty;
                if (objM == composer$Companion$Empty$1) {
                }
                MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) objM;
                composerImpl2.end(false);
                mutableStateCollectIsFocusedAsState = FocusInteractionKt.collectIsFocusedAsState(mutableInteractionSource2, composerImpl2, 6);
                MaterialTheme.INSTANCE.getClass();
                long j2 = MaterialTheme.getColorScheme(composerImpl2).secondary;
                if (((Boolean) mutableStateCollectIsFocusedAsState.getValue()).booleanValue()) {
                }
                Dp.Companion companion32 = Dp.Companion;
                Modifier modifierM125padding3ABfNKs2 = PaddingKt.m125padding3ABfNKs(FocusableKt.focusable$default(modifierThen, false, mutableInteractionSource2, 1), 8);
                composerImpl = composerImpl2;
                composerImpl.startReplaceGroup(-1781333791);
                zChangedInstance = composerImpl.changedInstance(shortcut);
                objRememberedValue = composerImpl.rememberedValue();
                if (zChangedInstance) {
                    z3 = false;
                    objRememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda4(shortcut, 0);
                    composerImpl.updateRememberedValue(objRememberedValue);
                    composerImpl.end(z3);
                    Modifier modifierSemantics4 = SemanticsModifierKt.semantics(modifierM125padding3ABfNKs2, true, (Function1) objRememberedValue);
                    Arrangement.INSTANCE.getClass();
                    Arrangement$Start$1 arrangement$Start$12 = Arrangement.Start;
                    Alignment.Companion.getClass();
                    RowMeasurePolicy rowMeasurePolicy3 = RowKt.rowMeasurePolicy(arrangement$Start$12, Alignment.Companion.Top, composerImpl, 0);
                    int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierSemantics4);
                    ComposeUiNode.Companion.getClass();
                    Function0 function03 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl.applier != null) {
                    }
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        z2 = z;
        i4 = i2 & 16;
        if (i4 != 0) {
        }
        function12 = function1;
        if ((i3 & 9363) == 9362) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
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
            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(Modifier.Companion, 16, 0.0f, 2);
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM127paddingVpY3zN4$default);
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
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda2(shortcutCategoryUi, i, 1, str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* renamed from: ShortcutCategoryIcon-ww6aTOc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m2591ShortcutCategoryIconww6aTOc(final IconSource iconSource, final Modifier modifier, long j, Composer composer, final int i, final int i2) {
        long j2;
        int i3;
        long j3;
        final long j4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1719048341);
        int i4 = (composerImpl.changedInstance(iconSource) ? 4 : 2) | i;
        if ((i2 & 4) != 0) {
            i4 |= 384;
        } else if ((i & 384) == 0) {
            i4 |= composerImpl.changed((Object) null) ? 256 : 128;
        }
        if ((i2 & 8) == 0) {
            j2 = j;
            int i5 = composerImpl.changed(j2) ? 2048 : 1024;
            i3 = i4 | i5;
            if ((i3 & 1171) == 1170 || !composerImpl.getSkipping()) {
                composerImpl.startDefaults();
                if ((i & 1) != 0 || composerImpl.getDefaultsInvalid()) {
                    if ((i2 & 8) != 0) {
                        j2 = ((Color) composerImpl.consume(ContentColorKt.LocalContentColor)).value;
                        i3 &= -7169;
                    }
                    long j5 = j2;
                    composerImpl.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCategoryIcon (ShortcutHelper.kt:320)");
                    }
                    if (iconSource.imageVector != null) {
                        composerImpl.startReplaceGroup(-423588430);
                        IconKt.m271Iconww6aTOc(iconSource.imageVector, (String) null, modifier, j5, composerImpl, ((i3 >> 3) & 112) | 384 | (i3 & 7168), 0);
                        j3 = j5;
                        composerImpl.end(false);
                    } else {
                        j3 = j5;
                        if (iconSource.painter != null) {
                            composerImpl.startReplaceGroup(-423479589);
                            ImageKt.Image(iconSource.painter, null, modifier, null, null, 0.0f, null, composerImpl, ((i3 >> 3) & 112) | 384, 120);
                            composerImpl = composerImpl;
                            composerImpl.end(false);
                        } else {
                            composerImpl.startReplaceGroup(-423415202);
                            composerImpl.end(false);
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    j4 = j3;
                } else {
                    composerImpl.skipToGroupEnd();
                    if ((i2 & 8) != 0) {
                        i3 &= -7169;
                    }
                    long j52 = j2;
                    composerImpl.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    if (iconSource.imageVector != null) {
                    }
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    j4 = j3;
                }
            } else {
                composerImpl.skipToGroupEnd();
                j4 = j2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda62
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        long j6 = j4;
                        ShortcutHelperKt.m2591ShortcutCategoryIconww6aTOc(iconSource, modifier, j6, (Composer) obj, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        j2 = j;
        i3 = i4 | i5;
        if ((i3 & 1171) == 1170) {
            composerImpl.startDefaults();
            if ((i & 1) != 0) {
                if ((i2 & 8) != 0) {
                }
                long j522 = j2;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                if (iconSource.imageVector != null) {
                }
                if (ComposerKt.isTraceInProgress()) {
                }
                j4 = j3;
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
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
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
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
            Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
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
                    SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(Modifier.Companion, 4));
                }
                composerImpl.end(false);
                ShortcutKeyContainer(ComposableLambdaKt.rememberComposableLambda(-867364547, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutCommand$1$1$1
                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        BoxScope boxScope = (BoxScope) obj2;
                        Composer composer2 = (Composer) obj3;
                        int iIntValue = ((Number) obj4).intValue();
                        if ((iIntValue & 6) == 0) {
                            iIntValue |= ((ComposerImpl) composer2).changed(boxScope) ? 4 : 2;
                        }
                        if ((iIntValue & 19) == 18) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCommand.<anonymous>.<anonymous>.<anonymous> (ShortcutHelper.kt:797)");
                                }
                                ShortcutKey shortcutKey2 = shortcutKey;
                                if (shortcutKey2 instanceof ShortcutKey.Text) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    composerImpl3.startReplaceGroup(2039231563);
                                    ShortcutHelperKt.ShortcutTextKey(boxScope, (ShortcutKey.Text) shortcutKey2, composerImpl3, iIntValue & 14);
                                    composerImpl3.end(false);
                                } else if (shortcutKey2 instanceof ShortcutKey.Icon) {
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    composerImpl4.startReplaceGroup(2039325803);
                                    ShortcutHelperKt.ShortcutIconKey(boxScope, (ShortcutKey.Icon) shortcutKey2, composerImpl4, iIntValue & 14);
                                    composerImpl4.end(false);
                                } else {
                                    ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                    composerImpl5.startReplaceGroup(2039383463);
                                    composerImpl5.end(false);
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda1(shortcutCommand, i, 1);
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
                Modifier modifierWrapContentSize$default = SizeKt.wrapContentSize$default(Modifier.Companion, null, 3);
                MaterialTheme.INSTANCE.getClass();
                long j = MaterialTheme.getColorScheme(composerImpl).outlineVariant;
                Dp.Companion companion = Dp.Companion;
                Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(BackgroundKt.m26backgroundbw27NRU(modifierWrapContentSize$default, j, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(16)), 4);
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM125padding3ABfNKs);
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
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(z, composableLambdaImpl, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda22
                public final /* synthetic */ boolean f$0;
                public final /* synthetic */ ComposableLambdaImpl f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                    ComposableLambdaImpl composableLambdaImpl2 = this.f$1;
                    ShortcutHelperKt.ShortcutCommandContainer(this.f$0, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags);
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
            int iIndexOf$default = StringsKt__StringsKt.indexOf$default(str2.toLowerCase(locale), StringsKt__StringsKt.trim(str).toString().toLowerCase(locale), 0, false, 6);
            int length = StringsKt__StringsKt.trim(str).toString().length() + iIndexOf$default;
            if (iIndexOf$default > 0) {
                builder.text.append(str2.substring(0, iIndexOf$default));
            }
            composerImpl2.startReplaceGroup(941808924);
            if (iIndexOf$default >= 0) {
                String strSubstring = str2.substring(iIndexOf$default, length);
                MaterialTheme.INSTANCE.getClass();
                int iPushStyle = builder.pushStyle(new SpanStyle(0L, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, MaterialTheme.getColorScheme(composerImpl2).primaryContainer, (TextDecoration) null, (Shadow) null, (PlatformSpanStyle) null, (DrawStyle) null, 63487, (DefaultConstructorMarker) null));
                try {
                    builder.text.append(strSubstring);
                    Unit unit = Unit.INSTANCE;
                    builder.pop(iPushStyle);
                    if (length < str2.length()) {
                        builder.text.append(str2.substring(length));
                    }
                } catch (Throwable th) {
                    builder.pop(iPushStyle);
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
            TextKt.m318TextIbK3jfQ(annotatedString, modifier, MaterialTheme.getColorScheme(composerImpl2).onSurface, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, MaterialTheme.getTypography(composerImpl2).titleSmall, composerImpl, (i2 >> 3) & 112, 0, 131064);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda13
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Shortcut shortcut2 = shortcut;
                    Modifier modifier2 = modifier;
                    ShortcutHelperKt.ShortcutDescriptionText(str, shortcut2, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutHelper(final Function1 function1, final Function0 function0, final Modifier modifier, final ShortcutsUiState shortcutsUiState, C08851 c08851, final Function1 function12, final Function1 function13, Composer composer, final int i) {
        int i2;
        C08851 c088512;
        final C08851 c088513;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1007329510);
        int i3 = i | (composerImpl.changedInstance(function1) ? 4 : 2) | (composerImpl.changedInstance(function0) ? 32 : 16) | (composerImpl.changed(modifier) ? 256 : 128) | (composerImpl.changed(shortcutsUiState) ? 2048 : 1024) | 8192 | (composerImpl.changedInstance(function12) ? 131072 : 65536) | (composerImpl.changedInstance(function13) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        if ((599187 & i3) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            c088513 = c08851;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                i2 = i3 & (-57345);
                c088512 = C08851.INSTANCE;
            } else {
                composerImpl.skipToGroupEnd();
                i2 = i3 & (-57345);
                c088512 = c08851;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelper (ShortcutHelper.kt:142)");
            }
            if (shortcutsUiState instanceof ShortcutsUiState.Active) {
                int i4 = i2 << 6;
                ActiveShortcutHelper((ShortcutsUiState.Active) shortcutsUiState, c088512, function1, function13, modifier, function0, function12, composerImpl, ((i2 >> 9) & 7168) | (i4 & 896) | (i4 & 57344) | ((i2 << 12) & 458752) | ((i2 << 3) & 3670016));
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            c088513 = c088512;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(function0, modifier, shortcutsUiState, c088513, function12, function13, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda27
                public final /* synthetic */ Function0 f$1;
                public final /* synthetic */ Modifier f$2;
                public final /* synthetic */ ShortcutsUiState f$3;
                public final /* synthetic */ ShortcutHelperKt.C08851 f$4;
                public final /* synthetic */ Function1 f$5;
                public final /* synthetic */ Function1 f$6;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Function1 function14 = this.f$5;
                    Function1 function15 = this.f$6;
                    ShortcutHelperKt.ShortcutHelper(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, function14, function15, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutHelperSinglePane(String str, Function1 function1, List list, ShortcutCategoryType shortcutCategoryType, Function1 function12, Function0 function0, Modifier modifier, Composer composer, int i) {
        int i2;
        List list2;
        Function1 function13;
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
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(modifier, 1.0f);
            ScrollState scrollStateRememberScrollState = ScrollKt.rememberScrollState(composerImpl);
            Modifier modifierThen = ScrollingContainerKt.scrollingContainer(modifierFillMaxSize, scrollStateRememberScrollState, Orientation.Vertical, (14 & 2) != 0, false, null, scrollStateRememberScrollState.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(scrollStateRememberScrollState, false, true));
            float f = 16;
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(modifierThen, f, 26, f, 0.0f, 8);
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default);
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
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            TitleBar(false, composerImpl, 0, 1);
            Modifier.Companion companion2 = Modifier.Companion;
            SpacerKt.Spacer(composerImpl, SizeKt.m131height3ABfNKs(companion2, 6));
            int i4 = i3 >> 3;
            ShortcutsSearchBar(function1, composerImpl, i4 & 14);
            SpacerKt.Spacer(composerImpl, SizeKt.m131height3ABfNKs(companion2, f));
            if (list2.isEmpty()) {
                composerImpl.startReplaceGroup(-423511973);
                Modifier modifierWeight = columnScopeInstance.weight(companion2, 1.0f, true);
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierWeight);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function02);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                z = true;
                m2590NoSearchResultsTextkHDZbjc(f, true, composerImpl, 54);
                composerImpl.end(true);
                composerImpl.end(false);
            } else {
                z = true;
                composerImpl.startReplaceGroup(-423347518);
                CategoriesPanelSinglePane(str, list2, shortcutCategoryType, function13, composerImpl, (i3 & 14) | (i4 & 112) | (i4 & 896) | (i4 & 7168));
                SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 1.0f, true));
                composerImpl.end(false);
            }
            m2589KeyboardSettingsixp7dh8(f, 32, function0, composerImpl, ((i3 >> 9) & 896) | 54);
            composerImpl.end(z);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda30(str, function1, list, shortcutCategoryType, function12, function0, modifier, i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:153:0x02bb  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x02e5  */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v13, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v25 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ShortcutHelperTwoPane(final String str, final Function1 function1, final List list, final ShortcutCategoryType shortcutCategoryType, Function1 function12, final Function0 function0, final boolean z, final boolean z2, final boolean z3, final Function1 function13, final Modifier modifier, final Function1 function14, Composer composer, final int i, final int i2) {
        int i3;
        int i4;
        Object obj;
        Function2 function2;
        Composer.Companion companion;
        ?? r8;
        ?? r13;
        Function1 function15;
        ComposerImpl composerImpl;
        List list2 = list;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(2106918421);
        if ((i & 6) == 0) {
            i3 = i | (composerImpl2.changed(str) ? 4 : 2);
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl2.changedInstance(function1) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl2.changedInstance(list2) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= (i & 4096) == 0 ? composerImpl2.changed(shortcutCategoryType) : composerImpl2.changedInstance(shortcutCategoryType) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i3 |= composerImpl2.changedInstance(function12) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i3 |= composerImpl2.changedInstance(function0) ? 131072 : 65536;
        }
        if ((i & 1572864) == 0) {
            i3 |= composerImpl2.changed(z) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((i & 12582912) == 0) {
            i3 |= composerImpl2.changed(z2) ? 8388608 : 4194304;
        }
        if ((i & 100663296) == 0) {
            i3 |= composerImpl2.changed(z3) ? 67108864 : 33554432;
        }
        if ((i & 805306368) == 0) {
            i3 |= composerImpl2.changedInstance(function13) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        }
        int i5 = i3;
        if ((i2 & 6) == 0) {
            i4 = i2 | (composerImpl2.changed(modifier) ? 4 : 2);
        } else {
            i4 = i2;
        }
        if ((i2 & 48) == 0) {
            i4 |= composerImpl2.changedInstance(function14) ? 32 : 16;
        }
        if ((i5 & 306783379) == 306783378 && (i4 & 19) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            function15 = function12;
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperTwoPane (ShortcutHelper.kt:393)");
            }
            int size = list2.size();
            int i6 = 0;
            while (true) {
                if (i6 >= size) {
                    obj = null;
                    break;
                }
                obj = list2.get(i6);
                if (Intrinsics.areEqual(((ShortcutCategoryUi) obj).type, shortcutCategoryType)) {
                    break;
                }
                i6++;
                list2 = list;
            }
            ShortcutCategoryUi shortcutCategoryUi = (ShortcutCategoryUi) obj;
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(modifier, 1.0f);
            float f = 24;
            Dp.Companion companion2 = Dp.Companion;
            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(modifierFillMaxSize, f, 0.0f, 2);
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl2, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierM127paddingVpY3zN4$default);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            if (composerImpl2.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function02);
            } else {
                composerImpl2.useNode();
            }
            Function2 function22 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl2, columnMeasurePolicy, function22);
            Function2 function23 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, function23);
            Function2 function24 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function24);
            }
            Function2 function25 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, function25);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Modifier.Companion companion3 = Modifier.Companion;
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion3, 1.0f);
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.SpaceBetween, Alignment.Companion.CenterVertically, composerImpl2, 54);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, modifierFillMaxWidth);
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function02);
            } else {
                composerImpl2.useNode();
            }
            Updater.m337setimpl(composerImpl2, rowMeasurePolicy, function22);
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function23);
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function24);
            }
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier2, function25);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            SpacerKt.Spacer(composerImpl2, rowScopeInstance.weight(companion3, 1.0f, true));
            Modifier modifierM144width3ABfNKs = SizeKt.m144width3ABfNKs(companion3, 412);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl2, modifierM144width3ABfNKs);
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function02);
            } else {
                composerImpl2.useNode();
            }
            Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, function22);
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope3, function23);
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl2, currentCompositeKeyHash3, function24);
            }
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier3, function25);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            int i7 = (i5 >> 24) & 14;
            TitleBar(z3, composerImpl2, i7, 0);
            composerImpl2.end(true);
            Composer.Companion companion4 = Composer.Companion;
            if (z) {
                composerImpl2.startReplaceGroup(-667024877);
                Modifier modifierWeight = rowScopeInstance.weight(companion3, 1.0f, true);
                composerImpl2.startReplaceGroup(-1545531234);
                boolean z4 = ((i5 & 1879048192) == 536870912) | ((i5 & 234881024) == 67108864);
                Object objRememberedValue = composerImpl2.rememberedValue();
                if (!z4) {
                    companion4.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda32
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                function13.mo781invoke(Boolean.valueOf(!z3));
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl2.updateRememberedValue(objRememberedValue);
                    }
                    Function0 function03 = (Function0) objRememberedValue;
                    composerImpl2.end(false);
                    composerImpl2.startReplaceGroup(-1545526897);
                    boolean z5 = (i4 & 112) == 32;
                    Object objRememberedValue2 = composerImpl2.rememberedValue();
                    if (!z5) {
                        companion4.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda33
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    function14.mo781invoke(ShortcutCustomizationRequestInfo.Reset.INSTANCE);
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue2);
                        }
                        composerImpl2.end(false);
                        int i8 = i7 | ((i5 >> 18) & 112);
                        function2 = function24;
                        companion = companion4;
                        r8 = 1;
                        CustomizationButtonsContainer(z3, z2, function03, (Function0) objRememberedValue2, modifierWeight, composerImpl2, i8);
                        composerImpl2.end(false);
                        r13 = 0;
                    }
                }
            } else {
                function2 = function24;
                companion = companion4;
                r8 = 1;
                r13 = 0;
                composerImpl2.startReplaceGroup(-666470907);
                SpacerKt.Spacer(composerImpl2, rowScopeInstance.weight(companion3, 1.0f, true));
                composerImpl2.end(false);
            }
            composerImpl2.end(r8);
            SpacerKt.Spacer(composerImpl2, SizeKt.m131height3ABfNKs(companion3, 12));
            Modifier modifierFillMaxWidth2 = SizeKt.fillMaxWidth(companion3, 1.0f);
            RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(Arrangement.Start, Alignment.Companion.Top, composerImpl2, r13);
            int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl2, modifierFillMaxWidth2);
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function02);
            } else {
                composerImpl2.useNode();
            }
            Updater.m337setimpl(composerImpl2, rowMeasurePolicy2, function22);
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope4, function23);
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl2, currentCompositeKeyHash4, function2);
            }
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier4, function25);
            Modifier modifierM144width3ABfNKs2 = SizeKt.m144width3ABfNKs(companion3, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
            Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, -1545509550, companion);
            Object obj2 = Composer.Companion.Empty;
            if (objM == obj2) {
                objM = new ShortcutHelperKt$$ExternalSyntheticLambda3(3);
                composerImpl2.updateRememberedValue(objM);
            }
            composerImpl2.end(r13);
            Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM144width3ABfNKs2, r13, (Function1) objM);
            composerImpl2.startReplaceGroup(-1545502058);
            boolean z6 = (i5 & 57344) == 16384 ? r8 : r13;
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            if (z6 || objRememberedValue3 == obj2) {
                function15 = function12;
                objRememberedValue3 = new ShortcutHelperKt$$ExternalSyntheticLambda4(function15, r8);
                composerImpl2.updateRememberedValue(objRememberedValue3);
            } else {
                function15 = function12;
            }
            composerImpl2.end(r13);
            StartSidePanel(function1, modifierSemantics, list, function0, shortcutCategoryType, (Function1) objRememberedValue3, composerImpl2, ((i5 >> 3) & 14) | (i5 & 896) | ((i5 >> 6) & 7168) | ((i5 << 3) & 57344));
            ComposerImpl composerImpl3 = composerImpl2;
            SpacerKt.Spacer(composerImpl3, SizeKt.m144width3ABfNKs(companion3, f));
            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(SizeKt.fillMaxSize(companion3, 1.0f), 0.0f, 8, 0.0f, 0.0f, 13);
            composerImpl3.startReplaceGroup(-1545494894);
            Object objRememberedValue4 = composerImpl3.rememberedValue();
            if (objRememberedValue4 == obj2) {
                objRememberedValue4 = new ShortcutHelperKt$$ExternalSyntheticLambda3(4);
                composerImpl3.updateRememberedValue(objRememberedValue4);
            }
            composerImpl3.end(r13);
            EndSidePanel(str, SemanticsModifierKt.semantics(modifierM129paddingqDBjuR0$default, r13, (Function1) objRememberedValue4), shortcutCategoryUi, z3, function14, composerImpl3, (i5 & 14) | ((i5 >> 15) & 7168) | ((i4 << 9) & 57344));
            boolean zM = AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, r8, r8);
            composerImpl = composerImpl3;
            if (zM) {
                ComposerKt.traceEventEnd();
                composerImpl = composerImpl3;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Function1 function16 = function15;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda37
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) {
                    ((Integer) obj4).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i2);
                    Modifier modifier2 = modifier;
                    Function1 function17 = function14;
                    ShortcutHelperKt.ShortcutHelperTwoPane(str, function1, list, shortcutCategoryType, function16, function0, z, z2, z3, function13, modifier2, function17, (Composer) obj3, iUpdateChangedFlags, iUpdateChangedFlags2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ShortcutIcon(final ShortcutIcon shortcutIcon, final Modifier modifier, Composer composer, final int i) {
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1928877533);
        int i2 = (composerImpl.changed(shortcutIcon) ? 4 : 2) | i | (composerImpl.changed(modifier) ? 32 : 16) | 384;
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutIcon (ShortcutHelper.kt:691)");
            }
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            String str = shortcutIcon.packageName;
            composerImpl.startReplaceGroup(1919308570);
            boolean zChanged = composerImpl.changed(str);
            int i3 = shortcutIcon.resourceId;
            boolean zChanged2 = zChanged | composerImpl.changed(i3);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged2) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = Icon.createWithResource(shortcutIcon.packageName, i3).loadDrawable(context);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                Drawable drawable = (Drawable) objRememberedValue;
                composerImpl.end(false);
                if (drawable == null) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                        final int i4 = 0;
                        recomposeScopeImplEndRestartGroup.block = new Function2(shortcutIcon, modifier, i, i4) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda14
                            public final /* synthetic */ int $r8$classId;
                            public final /* synthetic */ ShortcutIcon f$0;
                            public final /* synthetic */ Modifier f$1;

                            {
                                this.$r8$classId = i4;
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                int i5 = this.$r8$classId;
                                Composer composer2 = (Composer) obj;
                                ((Integer) obj2).getClass();
                                switch (i5) {
                                    case 0:
                                        ShortcutHelperKt.ShortcutIcon(this.f$0, this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(1));
                                        break;
                                    default:
                                        ShortcutHelperKt.ShortcutIcon(this.f$0, this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(1));
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                modifier2 = modifier;
                ImageKt.Image(DrawablePainterKt.rememberDrawablePainter(drawable, composerImpl), null, modifier2, null, null, 0.0f, null, composerImpl, 48 | ((i2 << 3) & 896), 120);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            final int i5 = 1;
            recomposeScopeImplEndRestartGroup2.block = new Function2(shortcutIcon, modifier2, i, i5) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda14
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ ShortcutIcon f$0;
                public final /* synthetic */ Modifier f$1;

                {
                    this.$r8$classId = i5;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i52 = this.$r8$classId;
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    switch (i52) {
                        case 0:
                            ShortcutHelperKt.ShortcutIcon(this.f$0, this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(1));
                            break;
                        default:
                            ShortcutHelperKt.ShortcutIcon(this.f$0, this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(1));
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutIconKey(BoxScope boxScope, ShortcutKey.Icon icon, Composer composer, int i) {
        int i2;
        Painter painterRememberDrawablePainter;
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
                painterRememberDrawablePainter = PainterResources_androidKt.painterResource(((ShortcutKey.Icon.ResIdIcon) icon).drawableResId, composerImpl, 0);
                composerImpl.end(false);
            } else {
                if (!(icon instanceof ShortcutKey.Icon.DrawableIcon)) {
                    composerImpl.startReplaceGroup(-1074244628);
                    composerImpl.end(false);
                    throw new NoWhenBranchMatchedException();
                }
                composerImpl.startReplaceGroup(-1074240023);
                painterRememberDrawablePainter = DrawablePainterKt.rememberDrawablePainter(((ShortcutKey.Icon.DrawableIcon) icon).drawable, composerImpl);
                composerImpl.end(false);
            }
            Modifier.Companion companion = Modifier.Companion;
            Alignment.Companion.getClass();
            Dp.Companion companion2 = Dp.Companion;
            IconKt.m270Iconww6aTOc(painterRememberDrawablePainter, (String) null, PaddingKt.m125padding3ABfNKs(boxScope.align(companion, Alignment.Companion.Center), 6), 0L, composerImpl, 48, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda24(boxScope, icon, i, 1);
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
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(8);
            Alignment.Companion.getClass();
            FlowLayoutKt.FlowRow(modifier, Arrangement.End, spacedAlignedM92spacedBy0680j_4, Alignment.Companion.CenterVertically, 0, 0, null, ComposableLambdaKt.rememberComposableLambda(671972285, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.ShortcutKeyCombinations.3
                /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    FlowRowScope flowRowScope = (FlowRowScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= ((ComposerImpl) composer2).changed(flowRowScope) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutKeyCombinations.<anonymous> (ShortcutHelper.kt:719)");
                            }
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(1146876038);
                            final Shortcut shortcut2 = shortcut;
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
                                    ShortcutHelperKt.m2592ShortcutOrSeparatorziNgDLE(flowRowScope, 16, composerImpl3, (iIntValue & 14) | 48);
                                }
                                composerImpl3.end(false);
                                ShortcutHelperKt.ShortcutCommandContainer(shortcutCommand.isCustom, ComposableLambdaKt.rememberComposableLambda(-1240949659, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutKeyCombinations$3$1$1
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj5, Object obj6) {
                                        Composer composer3 = (Composer) obj5;
                                        if ((((Number) obj6).intValue() & 3) == 2) {
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            if (composerImpl4.getSkipping()) {
                                                composerImpl4.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutKeyCombinations.<anonymous>.<anonymous>.<anonymous> (ShortcutHelper.kt:723)");
                                                }
                                                ShortcutHelperKt.ShortcutCommand(shortcutCommand, composer3, 0);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
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
                                SpacerKt.Spacer(composerImpl3, SizeKt.m144width3ABfNKs(Modifier.Companion, 16));
                            }
                            composerImpl3.end(false);
                            final Function0 function03 = function02;
                            final Function0 function04 = function0;
                            AnimatedVisibilityKt.AnimatedVisibility(flowRowScope, z, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-1725073435, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.ShortcutKeyCombinations.3.2
                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                    Composer composer3 = (Composer) obj6;
                                    ((Number) obj7).intValue();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutKeyCombinations.<anonymous>.<anonymous> (ShortcutHelper.kt:729)");
                                    }
                                    if (shortcut2.containsCustomShortcutCommands) {
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
                            }, composerImpl3), composerImpl3, (iIntValue & 14) | 1572864, 30);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, (i2 & 14) | 12586416, 112);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda16(modifier, shortcut, z, function0, function02, i, 0);
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
            Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(Modifier.Companion, 36);
            MaterialTheme.INSTANCE.getClass();
            Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(modifierM131height3ABfNKs, MaterialTheme.getColorScheme(composerImpl).surfaceContainer, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(12));
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM26backgroundbw27NRU);
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
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            composableLambdaImpl.invoke(BoxScopeInstance.INSTANCE, (Object) composerImpl, (Object) 54);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda1(composableLambdaImpl, i, 2);
        }
    }

    /* renamed from: ShortcutOrSeparator-ziNgDLE, reason: not valid java name */
    public static final void m2592ShortcutOrSeparatorziNgDLE(final FlowRowScope flowRowScope, final float f, Composer composer, final int i) {
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
            SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion, f));
            String strStringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_key_combinations_or_separator, composerImpl);
            Alignment.Companion.getClass();
            Modifier modifierAlign = ((FlowRowScopeInstance) flowRowScope).$$delegate_0.align(companion, Alignment.Companion.CenterVertically);
            composerImpl.startReplaceGroup(-278441072);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda3(1);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            composerImpl.end(false);
            Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierAlign, false, (Function1) objRememberedValue);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m317Text4IGK_g(strStringResource, modifierSemantics, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).titleSmall, composerImpl, 0, 0, 65532);
            composerImpl = composerImpl;
            SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion, f));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda20
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ShortcutHelperKt.m2592ShortcutOrSeparatorziNgDLE(flowRowScope, f, (Composer) obj, iUpdateChangedFlags);
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
                    DividerKt.m263HorizontalDivider9IZ8Weo(null, 0.0f, MaterialTheme.getColorScheme(composerImpl).surfaceContainerHigh, composerImpl, 0, 3);
                }
                composerImpl.end(false);
                Dp.Companion companion = Dp.Companion;
                Shortcut(PaddingKt.m127paddingVpY3zN4$default(Modifier.Companion, 0.0f, 24, 1), str, shortcut, false, null, composerImpl, ((i2 << 3) & 112) | 6, 24);
                i3++;
                size = size;
                list = list;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda2(shortcutSubCategory, i, 0, str);
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
            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(boxScope.align(companion, Alignment.Companion.Center), 12, 0.0f, 2);
            composerImpl2.startReplaceGroup(-721388036);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda3(2);
                composerImpl2.updateRememberedValue(objRememberedValue);
            }
            composerImpl2.end(false);
            Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM127paddingVpY3zN4$default, false, (Function1) objRememberedValue);
            MaterialTheme.INSTANCE.getClass();
            composerImpl = composerImpl2;
            TextKt.m317Text4IGK_g(str, modifierSemantics, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl2).titleSmall, composerImpl, 0, 0, 65532);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda24(boxScope, text, i, 0);
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
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default("");
                composerImpl2.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState = (MutableState) objRememberedValue;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -568350974);
            if (objM == obj) {
                objM = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
            }
            FocusRequester focusRequester = (FocusRequester) objM;
            composerImpl2.end(false);
            final FocusManager focusManager = (FocusManager) composerImpl2.consume(CompositionLocalsKt.LocalFocusManager);
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(-568347458);
            Object objRememberedValue2 = composerImpl2.rememberedValue();
            if (objRememberedValue2 == obj) {
                objRememberedValue2 = new ShortcutHelperKt$ShortcutsSearchBar$1$1(focusRequester, null);
                composerImpl2.updateRememberedValue(objRememberedValue2);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, unit, (Function2) objRememberedValue2);
            Modifier modifierFocusRequester = FocusRequesterModifierKt.focusRequester(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), focusRequester);
            composerImpl2.startReplaceGroup(-568336855);
            boolean zChangedInstance = composerImpl2.changedInstance(focusManager);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            if (zChangedInstance || objRememberedValue3 == obj) {
                objRememberedValue3 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$ShortcutsSearchBar$2$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        long jM580getKeyZmokQxo = KeyEvent_androidKt.m580getKeyZmokQxo(((KeyEvent) obj2).nativeKeyEvent);
                        Key.Companion.getClass();
                        if (!Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.DirectionDown)) {
                            return Boolean.FALSE;
                        }
                        FocusDirection.Companion.getClass();
                        ((FocusOwnerImpl) focusManager).m375moveFocus3ESFkO8(FocusDirection.Down);
                        return Boolean.TRUE;
                    }
                };
                composerImpl2.updateRememberedValue(objRememberedValue3);
            }
            composerImpl2.end(false);
            Modifier modifierOnKeyEvent = KeyInputModifierKt.onKeyEvent(modifierFocusRequester, (Function1) objRememberedValue3);
            SearchBarDefaults searchBarDefaults = SearchBarDefaults.INSTANCE;
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl2).surfaceBright;
            searchBarDefaults.getClass();
            SearchBarColors searchBarColorsM284colorsKlgxPg = SearchBarDefaults.m284colorsKlgxPg(j, composerImpl2, 0, 6);
            String str = (String) mutableState.getValue();
            float f = 0;
            Dp.Companion companion = Dp.Companion;
            WindowInsets windowInsetsM148WindowInsetsa9UjIt4 = WindowInsetsKt.m148WindowInsetsa9UjIt4(f, f, f, f);
            composerImpl2.startReplaceGroup(-568321971);
            boolean z = (i2 & 14) == 4;
            Object objRememberedValue4 = composerImpl2.rememberedValue();
            if (z || objRememberedValue4 == obj) {
                objRememberedValue4 = new ShortcutHelperKt$$ExternalSyntheticLambda40(function1, mutableState, 0);
                composerImpl2.updateRememberedValue(objRememberedValue4);
            }
            Function1 function12 = (Function1) objRememberedValue4;
            Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -568319065);
            if (objM2 == obj) {
                objM2 = new ShortcutHelperKt$$ExternalSyntheticLambda3(5);
                composerImpl2.updateRememberedValue(objM2);
            }
            Function1 function13 = (Function1) objM2;
            Object objM3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -568322937);
            if (objM3 == obj) {
                objM3 = new ShortcutHelperKt$$ExternalSyntheticLambda3(6);
                composerImpl2.updateRememberedValue(objM3);
            }
            composerImpl2.end(false);
            ComposableSingletons$ShortcutHelperKt.INSTANCE.getClass();
            composerImpl = composerImpl2;
            SearchBarKt.m286SearchBarWuY5d9Q(str, function12, function13, false, (Function1) objM3, modifierOnKeyEvent, false, ComposableSingletons$ShortcutHelperKt.f44lambda1, ComposableSingletons$ShortcutHelperKt.f45lambda2, null, null, searchBarColorsM284colorsKlgxPg, 0.0f, 0.0f, windowInsetsM148WindowInsetsa9UjIt4, null, ComposableSingletons$ShortcutHelperKt.f46lambda3, composerImpl, 113274240, 1572864, 46656);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda53(function1, i, 3);
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
            CompositionLocalKt.CompositionLocalProvider(staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(DensityKt.Density(((Density) composerImpl.consume(staticProvidableCompositionLocal)).getDensity(), RangesKt___RangesKt.coerceIn(((Density) composerImpl.consume(staticProvidableCompositionLocal)).getFontScale(), 1.0f, 1.5f))), ComposableLambdaKt.rememberComposableLambda(343378781, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.StartSidePanel.1
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
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.StartSidePanel.<anonymous> (ShortcutHelper.kt:913)");
                            }
                            List list2 = list;
                            Arrangement.INSTANCE.getClass();
                            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                            Alignment.Companion.getClass();
                            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer2, 0);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifier);
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
                            Updater.m337setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                            }
                            Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                            ShortcutHelperKt.ShortcutsSearchBar(function1, composer2, 0);
                            Modifier.Companion companion = Modifier.Companion;
                            Dp.Companion companion2 = Dp.Companion;
                            SpacerKt.Spacer(composer2, SizeKt.m133heightInVpY3zN4$default(companion, 8, 0.0f, 2));
                            ShortcutHelperKt.CategoriesPanelTwoPane(list2, shortcutCategoryType, function12, composer2, 0);
                            SpacerKt.Spacer(composer2, columnScopeInstance.weight(companion, 1.0f, true));
                            float f = 24;
                            ShortcutHelperKt.m2589KeyboardSettingsixp7dh8(f, f, function0, composer2, 54);
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda47
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    ShortcutHelperKt.StartSidePanel(function1, modifier, list, function0, shortcutCategoryType, function12, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
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
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            Dp.Companion companion = Dp.Companion;
            RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(28);
            MaterialTheme.INSTANCE.getClass();
            SurfaceKt.m304SurfaceT9BRK9s(modifierFillMaxWidth, roundedCornerShapeM187RoundedCornerShape0680j_4, MaterialTheme.getColorScheme(composerImpl).surfaceBright, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-867766109, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.SubCategoryContainerDualPane.1
                /* JADX WARN: Removed duplicated region for block: B:38:0x012d  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    int i2;
                    Shortcut shortcut;
                    Composer composer2 = (Composer) obj;
                    int i3 = 2;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.SubCategoryContainerDualPane.<anonymous> (ShortcutHelper.kt:574)");
                            }
                            Modifier.Companion companion2 = Modifier.Companion;
                            Dp.Companion companion3 = Dp.Companion;
                            Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(companion2, 16);
                            Arrangement.INSTANCE.getClass();
                            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                            Alignment.Companion.getClass();
                            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer2, 0);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierM125padding3ABfNKs);
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
                            Updater.m337setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                            }
                            Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                            ShortcutSubCategory shortcutSubCategory2 = shortcutSubCategory;
                            ShortcutHelperKt.SubCategoryTitle(shortcutSubCategory2.label, composer2, 0);
                            float f = 8;
                            SpacerKt.Spacer(composer2, SizeKt.m131height3ABfNKs(companion2, f));
                            composerImpl3.startReplaceGroup(101693408);
                            List list = shortcutSubCategory2.shortcuts;
                            int size = list.size();
                            int i4 = 0;
                            while (i4 < size) {
                                Shortcut shortcut2 = (Shortcut) list.get(i4);
                                composerImpl3.startReplaceGroup(101693955);
                                if (i4 > 0) {
                                    Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(Modifier.Companion, f, 0.0f, i3);
                                    MaterialTheme.INSTANCE.getClass();
                                    i2 = i4;
                                    shortcut = shortcut2;
                                    DividerKt.m263HorizontalDivider9IZ8Weo(modifierM127paddingVpY3zN4$default, 0.0f, MaterialTheme.getColorScheme(composer2).surfaceContainerHigh, composer2, 6, 2);
                                } else {
                                    i2 = i4;
                                    shortcut = shortcut2;
                                }
                                composerImpl3.end(false);
                                Modifier modifierM127paddingVpY3zN4$default2 = PaddingKt.m127paddingVpY3zN4$default(Modifier.Companion, 0.0f, f, 1);
                                boolean z2 = z && shortcut.isCustomizable;
                                composerImpl3.startReplaceGroup(-1053171083);
                                Function1 function12 = function1;
                                boolean zChanged = composerImpl3.changed(function12) | composerImpl3.changedInstance(shortcutSubCategory2);
                                Object objRememberedValue = composerImpl3.rememberedValue();
                                if (!zChanged) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue == Composer.Companion.Empty) {
                                        objRememberedValue = new ShortcutHelperKt$$ExternalSyntheticLambda40(function12, shortcutSubCategory2, 1);
                                        composerImpl3.updateRememberedValue(objRememberedValue);
                                    }
                                }
                                Function1 function13 = (Function1) objRememberedValue;
                                composerImpl3.end(false);
                                Composer composer3 = composer2;
                                ShortcutHelperKt.Shortcut(modifierM127paddingVpY3zN4$default2, str, shortcut, z2, function13, composer3, 6, 0);
                                composer2 = composer3;
                                i4 = i2 + 1;
                                size = size;
                                i3 = 2;
                            }
                            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, false, true)) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 12582918, 120);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(str, shortcutSubCategory, z, function1, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda54
                public final /* synthetic */ String f$0;
                public final /* synthetic */ ShortcutSubCategory f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ Function1 f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    boolean z2 = this.f$2;
                    Function1 function12 = this.f$3;
                    ShortcutHelperKt.SubCategoryContainerDualPane(this.f$0, this.f$1, z2, function12, (Composer) obj, iUpdateChangedFlags);
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
            TextKt.m317Text4IGK_g(str, null, MaterialTheme.getColorScheme(composerImpl2).primary, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl2).titleSmall, composerImpl, i2 & 14, 0, 65530);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutHelperKt$$ExternalSyntheticLambda1(str, i, 0);
        }
    }

    public static final void TitleBar(boolean z, Composer composer, final int i, final int i2) {
        boolean z2;
        int i3;
        final boolean z3;
        final String strStringResource;
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
                strStringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_customize_mode_title, composerImpl);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-778147590);
                strStringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_title, composerImpl);
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
            TopAppBarColors topAppBarColorsM319copytNS2XkQ = defaultTopAppBarColors$material3_release.m319copytNS2XkQ(j, j2, j2, j2, j2, defaultTopAppBarColors$material3_release.subtitleContentColor);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            float f = 0;
            Dp.Companion companion = Dp.Companion;
            AppBarKt.m246CenterAlignedTopAppBarGHTll3U(ComposableLambdaKt.rememberComposableLambda(2143059, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt.TitleBar.1
                /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
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
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.TitleBar.<anonymous> (ShortcutHelper.kt:1005)");
                            }
                            MaterialTheme.INSTANCE.getClass();
                            long j3 = MaterialTheme.getColorScheme(composer2).onSurface;
                            TextStyle textStyle = MaterialTheme.getTypography(composer2).headlineSmall;
                            TextOverflow.Companion.getClass();
                            int i5 = TextOverflow.Ellipsis;
                            TextAlign.Companion.getClass();
                            TextKt.m317Text4IGK_g(strStringResource, null, j3, 0L, null, null, null, 0L, null, TextAlign.m807boximpl(TextAlign.Center), 0L, i5, false, 2, 0, null, textStyle, composer2, 0, 3120, 54778);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), null, null, null, 64, WindowInsetsKt.m148WindowInsetsa9UjIt4(f, f, f, f), topAppBarColorsM319copytNS2XkQ, null, composerImpl, 24582, 142);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt$$ExternalSyntheticLambda51
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ShortcutHelperKt.TitleBar(z3, (Composer) obj, iUpdateChangedFlags, i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
