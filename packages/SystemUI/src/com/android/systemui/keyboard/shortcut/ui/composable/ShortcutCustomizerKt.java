package com.android.systemui.keyboard.shortcut.ui.composable;

import android.view.KeyEvent;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.CoreTextFieldKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.AddKt;
import androidx.compose.material.icons.filled.ErrorOutlineKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.OutlinedTextFieldDefaults;
import androidx.compose.material3.OutlinedTextFieldKt;
import androidx.compose.material3.TextFieldColors;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.focus.FocusManager;
import androidx.compose.ui.focus.FocusOwnerImpl;
import androidx.compose.ui.focus.FocusPropertiesKt;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.KeyEventType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.key.KeyInputModifierKt;
import androidx.compose.ui.input.key.Key_androidKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import com.android.compose.ui.graphics.painter.DrawablePainterKt;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class ShortcutCustomizerKt {
    public static final void ActionKeyContainer(ShortcutKey.Icon.ResIdIcon resIdIcon, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1727261028);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(resIdIcon) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ActionKeyContainer (ShortcutCustomizer.kt:445)");
            }
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM143sizeInqDBjuR0$default = SizeKt.m143sizeInqDBjuR0$default(Modifier.Companion, 105, 48, 0.0f, 0.0f, 12);
            MaterialTheme.INSTANCE.getClass();
            Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(BackgroundKt.m26backgroundbw27NRU(modifierM143sizeInqDBjuR0$default, MaterialTheme.getColorScheme(composerImpl).surface, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(16)), 12);
            Arrangement.INSTANCE.getClass();
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(8);
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_4, Alignment.Companion.CenterVertically, composerImpl, 54);
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
            Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            ActionKeyIcon(resIdIcon, composerImpl, i2 & 14);
            ActionKeyText(0, composerImpl);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda22(resIdIcon, i, 0);
        }
    }

    public static final void ActionKeyIcon(ShortcutKey.Icon.ResIdIcon resIdIcon, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(651355030);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(resIdIcon) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ActionKeyIcon (ShortcutCustomizer.kt:475)");
            }
            Painter painterPainterResource = PainterResources_androidKt.painterResource(resIdIcon.drawableResId, composerImpl, 0);
            String strStringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_content_description_meta_key, composerImpl);
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(Modifier.Companion, 24);
            Alignment.Companion.getClass();
            IconKt.m270Iconww6aTOc(painterPainterResource, strStringResource, SizeKt.wrapContentSize$default(modifierM140size3ABfNKs, Alignment.Companion.Center, 2), 0L, composerImpl, 384, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda22(resIdIcon, i, 1);
        }
    }

    public static final void ActionKeyText(int i, Composer composer) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1191875402);
        if (i == 0 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ActionKeyText (ShortcutCustomizer.kt:463)");
            }
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).titleMedium;
            long sp = TextUnitKt.getSp(16);
            long sp2 = TextUnitKt.getSp(24);
            Modifier.Companion companion = Modifier.Companion;
            Alignment.Companion.getClass();
            composerImpl = composerImpl2;
            TextKt.m317Text4IGK_g("Action", SizeKt.wrapContentSize$default(companion, Alignment.Companion.Center, 2), MaterialTheme.getColorScheme(composerImpl2).onSurface, sp, null, null, null, 0L, null, null, sp2, 0, false, 0, 0, null, textStyle, composerImpl, 3126, 6, 64496);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda20(i, 3);
        }
    }

    public static final void AddShortcutDialog(Modifier modifier, ShortcutCustomizationUiState.AddShortcutDialog addShortcutDialog, Function1 function1, Function0 function0, Function0 function02, Function0 function03, Composer composer, int i) {
        int i2;
        Function0 function04;
        boolean z;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1139858084);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(addShortcutDialog) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            function04 = function0;
            i2 |= composerImpl.changedInstance(function04) ? 2048 : 1024;
        } else {
            function04 = function0;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changedInstance(function03) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.AddShortcutDialog (ShortcutCustomizer.kt:116)");
            }
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
            Arrangement.INSTANCE.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, horizontal, composerImpl, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
            ComposeUiNode.Companion.getClass();
            Function0 function05 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function05);
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
            Title(addShortcutDialog.shortcutLabel, composerImpl, 0);
            Description(StringResources_androidKt.stringResource(R.string.shortcut_customize_mode_add_shortcut_description, composerImpl), composerImpl, 0);
            Dp.Companion companion = Dp.Companion;
            PromptShortcutModifier(SizeKt.m143sizeInqDBjuR0$default(PaddingKt.m129paddingqDBjuR0$default(Modifier.Companion, 0.0f, 24, 0.0f, 0.0f, 13), 131, 48, 0.0f, 0.0f, 12), addShortcutDialog.defaultCustomShortcutModifierKey, composerImpl, 6);
            String str = addShortcutDialog.errorMessage;
            boolean z2 = true;
            if (str.length() > 0) {
                z = true;
            } else {
                z = true;
                z2 = false;
            }
            SelectedKeyCombinationContainer(z2, function1, addShortcutDialog.pressedKeys, addShortcutDialog.pressedKeysDescription, function02, function03, composerImpl, ((i2 >> 3) & 112) | (57344 & i2) | (458752 & i2));
            composerImpl = composerImpl;
            ErrorMessageContainer(str, composerImpl, 0);
            DialogButtons(function04, !addShortcutDialog.pressedKeys.isEmpty(), function02, StringResources_androidKt.stringResource(R.string.shortcut_helper_customize_dialog_set_shortcut_button_label, composerImpl), composerImpl, ((i2 >> 9) & 14) | ((i2 >> 6) & 896), 0);
            composerImpl.end(z);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda3(modifier, addShortcutDialog, function1, function0, function02, function03, i);
        }
    }

    public static final void ConfirmationDialog(Modifier modifier, String str, String str2, String str3, Function0 function0, Function0 function02, Composer composer, int i) {
        int i2;
        Function0 function03;
        Function0 function04;
        String str4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(589201461);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(str) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(str2) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(str3) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function03 = function02;
            function04 = function0;
            str4 = str3;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ConfirmationDialog (ShortcutCustomizer.kt:189)");
            }
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
            ComposeUiNode.Companion.getClass();
            Function0 function05 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function05);
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
            Title(str, composerImpl, (i2 >> 3) & 14);
            int i3 = i2 >> 6;
            Description(str2, composerImpl, i3 & 14);
            DialogButtons(function02, false, function0, str3, composerImpl, (i3 & 896) | ((i2 >> 15) & 14) | (i2 & 7168), 2);
            function03 = function02;
            function04 = function0;
            str4 = str3;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda3(modifier, str, str2, str4, function04, function03, i);
        }
    }

    public static final void DeleteShortcutDialog(Modifier modifier, Function0 function0, Function0 function02, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(577732870);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.DeleteShortcutDialog (ShortcutCustomizer.kt:150)");
            }
            ConfirmationDialog(modifier, StringResources_androidKt.stringResource(R.string.shortcut_customize_mode_remove_shortcut_dialog_title, composerImpl), StringResources_androidKt.stringResource(R.string.shortcut_customize_mode_remove_shortcut_description, composerImpl), StringResources_androidKt.stringResource(R.string.shortcut_helper_customize_dialog_remove_button_label, composerImpl), function02, function0, composerImpl, (i2 & 14) | (57344 & (i2 << 6)) | ((i2 << 12) & 458752));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda1(modifier, function0, function02, i, 0);
        }
    }

    public static final void Description(String str, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(2071251211);
        if ((i & 6) == 0) {
            i2 = i | (composerImpl2.changed(str) ? 4 : 2);
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.Description (ShortcutCustomizer.kt:416)");
            }
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).bodyMedium;
            Modifier.Companion companion = Modifier.Companion;
            float f = 24;
            Dp.Companion companion2 = Dp.Companion;
            Modifier modifierM144width3ABfNKs = SizeKt.m144width3ABfNKs(PaddingKt.m129paddingqDBjuR0$default(companion, f, f, f, 0.0f, 8), 316);
            Alignment.Companion.getClass();
            Modifier modifierWrapContentSize$default = SizeKt.wrapContentSize$default(modifierM144width3ABfNKs, Alignment.Companion.Center, 2);
            long j = MaterialTheme.getColorScheme(composerImpl2).onSurfaceVariant;
            TextAlign.Companion.getClass();
            composerImpl = composerImpl2;
            TextKt.m317Text4IGK_g(str, modifierWrapContentSize$default, j, 0L, null, null, null, 0L, null, TextAlign.m807boximpl(TextAlign.Center), 0L, 0, false, 0, 0, null, textStyle, composerImpl, i2 & 14, 0, 65016);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda8(str, i, 0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0233  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:80:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void DialogButtons(final Function0 function0, boolean z, final Function0 function02, final String str, Composer composer, final int i, final int i2) {
        int i3;
        boolean z2;
        Object objRememberedValue;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        Object objRememberedValue2;
        boolean z3;
        ComposerImpl composerImpl;
        final boolean z4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(311494300);
        if ((i & 6) == 0) {
            i3 = (composerImpl2.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = i2 & 2;
        if (i4 == 0) {
            if ((i & 48) == 0) {
                z2 = z;
                i3 |= composerImpl2.changed(z2) ? 32 : 16;
            }
            if ((i & 384) == 0) {
                i3 |= composerImpl2.changedInstance(function02) ? 256 : 128;
            }
            if ((i & 3072) == 0) {
                i3 |= composerImpl2.changed(str) ? 2048 : 1024;
            }
            if ((i3 & 1171) == 1170 || !composerImpl2.getSkipping()) {
                boolean z5 = i4 == 0 ? true : z2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.DialogButtons (ShortcutCustomizer.kt:207)");
                }
                composerImpl2.startReplaceGroup(68457551);
                objRememberedValue = composerImpl2.rememberedValue();
                Composer.Companion.getClass();
                composer$Companion$Empty$1 = Composer.Companion.Empty;
                if (objRememberedValue == composer$Companion$Empty$1) {
                    objRememberedValue = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
                }
                FocusRequester focusRequester = (FocusRequester) objRememberedValue;
                composerImpl2.end(false);
                Unit unit = Unit.INSTANCE;
                composerImpl2.startReplaceGroup(68459315);
                objRememberedValue2 = composerImpl2.rememberedValue();
                if (objRememberedValue2 == composer$Companion$Empty$1) {
                    objRememberedValue2 = new ShortcutCustomizerKt$DialogButtons$1$1(focusRequester, null);
                    composerImpl2.updateRememberedValue(objRememberedValue2);
                }
                composerImpl2.end(false);
                EffectsKt.LaunchedEffect(composerImpl2, unit, (Function2) objRememberedValue2);
                Modifier.Companion companion = Modifier.Companion;
                float f = 24;
                Dp.Companion companion2 = Dp.Companion;
                Modifier modifierM143sizeInqDBjuR0$default = SizeKt.m143sizeInqDBjuR0$default(PaddingKt.m129paddingqDBjuR0$default(companion, f, f, f, 0.0f, 8), 316, 48, 0.0f, 0.0f, 12);
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.Bottom;
                Arrangement.INSTANCE.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.End, vertical, composerImpl2, 54);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierM143sizeInqDBjuR0$default);
                ComposeUiNode.Companion.getClass();
                Function0 function03 = ComposeUiNode.Companion.Constructor;
                if (composerImpl2.applier != null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                    composerImpl2.createNode(function03);
                } else {
                    composerImpl2.useNode();
                }
                Updater.m337setimpl(composerImpl2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(50);
                Color.Companion.getClass();
                long j = Color.Transparent;
                float f2 = 40;
                Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(companion, f2, 0.0f, 2);
                MaterialTheme.INSTANCE.getClass();
                SurfacesKt.m2596ShortcutHelperButton01TuoB8(function0, MaterialTheme.getColorScheme(composerImpl2).primary, j, modifierM133heightInVpY3zN4$default, roundedCornerShapeM187RoundedCornerShape0680j_4, null, StringResources_androidKt.stringResource(R.string.shortcut_helper_customize_dialog_cancel_button_label, composerImpl2), 0.0f, 0.0f, false, BorderStrokeKt.m31BorderStrokecXLIe8U(1, MaterialTheme.getColorScheme(composerImpl2).outlineVariant), null, composerImpl2, (i3 & 14) | 3456, 2976);
                SpacerKt.Spacer(composerImpl2, SizeKt.m144width3ABfNKs(companion, 8));
                Modifier modifierFocusRequester = FocusRequesterModifierKt.focusRequester(SizeKt.m133heightInVpY3zN4$default(companion, f2, 0.0f, 2), focusRequester);
                composerImpl2.startReplaceGroup(-1909155448);
                Object objRememberedValue3 = composerImpl2.rememberedValue();
                if (objRememberedValue3 == composer$Companion$Empty$1) {
                    z3 = false;
                    objRememberedValue3 = new ShortcutCustomizerKt$$ExternalSyntheticLambda6(0);
                    composerImpl2.updateRememberedValue(objRememberedValue3);
                } else {
                    z3 = false;
                }
                composerImpl2.end(z3);
                boolean z6 = z5;
                SurfacesKt.m2596ShortcutHelperButton01TuoB8(function02, MaterialTheme.getColorScheme(composerImpl2).onPrimary, MaterialTheme.getColorScheme(composerImpl2).primary, FocusPropertiesKt.focusProperties(modifierFocusRequester, (Function1) objRememberedValue3), null, null, str, 0.0f, 0.0f, z6, null, null, composerImpl2, ((i3 >> 6) & 14) | ((i3 << 9) & 3670016) | (1879048192 & (i3 << 24)), 3504);
                composerImpl = composerImpl2;
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                z4 = z6;
            } else {
                composerImpl2.skipToGroupEnd();
                composerImpl = composerImpl2;
                z4 = z2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCustomizerKt$$ExternalSyntheticLambda7
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        String str2 = str;
                        ShortcutCustomizerKt.DialogButtons(function0, z4, function02, str2, (Composer) obj, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 48;
        z2 = z;
        if ((i & 384) == 0) {
        }
        if ((i & 3072) == 0) {
        }
        if ((i3 & 1171) == 1170) {
            if (i4 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            composerImpl2.startReplaceGroup(68457551);
            objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
            }
            FocusRequester focusRequester2 = (FocusRequester) objRememberedValue;
            composerImpl2.end(false);
            Unit unit2 = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(68459315);
            objRememberedValue2 = composerImpl2.rememberedValue();
            if (objRememberedValue2 == composer$Companion$Empty$1) {
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, unit2, (Function2) objRememberedValue2);
            Modifier.Companion companion3 = Modifier.Companion;
            float f3 = 24;
            Dp.Companion companion22 = Dp.Companion;
            Modifier modifierM143sizeInqDBjuR0$default2 = SizeKt.m143sizeInqDBjuR0$default(PaddingKt.m129paddingqDBjuR0$default(companion3, f3, f3, f3, 0.0f, 8), 316, 48, 0.0f, 0.0f, 12);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical2 = Alignment.Companion.Bottom;
            Arrangement.INSTANCE.getClass();
            RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(Arrangement.End, vertical2, composerImpl2, 54);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, modifierM143sizeInqDBjuR0$default2);
            ComposeUiNode.Companion.getClass();
            Function0 function032 = ComposeUiNode.Companion.Constructor;
            if (composerImpl2.applier != null) {
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    public static final void ErrorIcon(final boolean z, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(135979139);
        if ((((composerImpl.changed(z) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ErrorIcon (ShortcutCustomizer.kt:323)");
            }
            if (z) {
                Icons.INSTANCE.getClass();
                ImageVector imageVectorBuild = ErrorOutlineKt._errorOutline;
                if (imageVectorBuild == null) {
                    Dp.Companion companion = Dp.Companion;
                    ImageVector.Builder builder = new ImageVector.Builder("Filled.ErrorOutline", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                    EmptyList emptyList = VectorKt.EmptyPath;
                    Color.Companion.getClass();
                    SolidColor solidColor = new SolidColor(Color.Black, null);
                    StrokeCap.Companion.getClass();
                    StrokeJoin.Companion.getClass();
                    int i2 = StrokeJoin.Bevel;
                    PathBuilder pathBuilder = new PathBuilder();
                    pathBuilder.moveTo(11.0f, 15.0f);
                    pathBuilder.horizontalLineToRelative(2.0f);
                    pathBuilder.verticalLineToRelative(2.0f);
                    pathBuilder.horizontalLineToRelative(-2.0f);
                    pathBuilder.close();
                    pathBuilder.moveTo(11.0f, 7.0f);
                    pathBuilder.horizontalLineToRelative(2.0f);
                    pathBuilder.verticalLineToRelative(6.0f);
                    pathBuilder.horizontalLineToRelative(-2.0f);
                    pathBuilder.close();
                    pathBuilder.moveTo(11.99f, 2.0f);
                    pathBuilder.curveTo(6.47f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f);
                    pathBuilder.reflectiveCurveToRelative(4.47f, 10.0f, 9.99f, 10.0f);
                    pathBuilder.curveTo(17.52f, 22.0f, 22.0f, 17.52f, 22.0f, 12.0f);
                    pathBuilder.reflectiveCurveTo(17.52f, 2.0f, 11.99f, 2.0f);
                    pathBuilder.close();
                    pathBuilder.moveTo(12.0f, 20.0f);
                    pathBuilder.curveToRelative(-4.42f, 0.0f, -8.0f, -3.58f, -8.0f, -8.0f);
                    pathBuilder.reflectiveCurveToRelative(3.58f, -8.0f, 8.0f, -8.0f);
                    pathBuilder.reflectiveCurveToRelative(8.0f, 3.58f, 8.0f, 8.0f);
                    pathBuilder.reflectiveCurveToRelative(-3.58f, 8.0f, -8.0f, 8.0f);
                    pathBuilder.close();
                    builder.m567addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i2, 1.0f, 0.0f, 1.0f, 0.0f);
                    imageVectorBuild = builder.build();
                    ErrorOutlineKt._errorOutline = imageVectorBuild;
                }
                Dp.Companion companion2 = Dp.Companion;
                Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(Modifier.Companion, 20);
                MaterialTheme.INSTANCE.getClass();
                IconKt.m271Iconww6aTOc(imageVectorBuild, (String) null, modifierM140size3ABfNKs, MaterialTheme.getColorScheme(composerImpl).error, composerImpl, 432, 0);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(z, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCustomizerKt$$ExternalSyntheticLambda19
                public final /* synthetic */ boolean f$0;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    ShortcutCustomizerKt.ErrorIcon(this.f$0, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0105  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ErrorMessageContainer(String str, Composer composer, int i) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-615483151);
        int i2 = i | (composerImpl2.changed(str) ? 4 : 2);
        if ((i2 & 3) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ErrorMessageContainer (ShortcutCustomizer.kt:243)");
            }
            if (str.length() > 0) {
                Modifier.Companion companion = Modifier.Companion;
                Dp.Companion companion2 = Dp.Companion;
                Modifier modifierM143sizeInqDBjuR0$default = SizeKt.m143sizeInqDBjuR0$default(PaddingKt.m127paddingVpY3zN4$default(companion, 16, 0.0f, 2), CustomDeviceManager.DESTINATION_ADDRESS, 40, 0.0f, 0.0f, 12);
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierM143sizeInqDBjuR0$default);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (composerImpl2.applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                    composerImpl2.createNode(function0);
                } else {
                    composerImpl2.useNode();
                }
                Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                MaterialTheme.INSTANCE.getClass();
                TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).bodyMedium;
                long sp = TextUnitKt.getSp(14);
                long sp2 = TextUnitKt.getSp(20);
                FontWeight.Companion.getClass();
                FontWeight fontWeight = FontWeight.W500;
                long j = MaterialTheme.getColorScheme(composerImpl2).error;
                Modifier modifierM144width3ABfNKs = SizeKt.m144width3ABfNKs(PaddingKt.m129paddingqDBjuR0$default(companion, 24, 0.0f, 0.0f, 0.0f, 14), IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
                composerImpl2.startReplaceGroup(287107718);
                int i3 = i2 & 14;
                boolean z = i3 == 4;
                Object objRememberedValue = composerImpl2.rememberedValue();
                if (!z) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new ShortcutCustomizerKt$$ExternalSyntheticLambda4(str, 0);
                        composerImpl2.updateRememberedValue(objRememberedValue);
                    }
                    composerImpl2.end(false);
                    TextKt.m317Text4IGK_g(str, SemanticsModifierKt.semantics(modifierM144width3ABfNKs, false, (Function1) objRememberedValue), j, sp, null, fontWeight, null, 0L, null, null, sp2, 0, false, 0, 0, null, textStyle, composerImpl2, i3 | 199680, 6, 64464);
                    composerImpl = composerImpl2;
                    composerImpl.end(true);
                }
            } else {
                composerImpl = composerImpl2;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda5(str, i, 0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x00c7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void OutlinedInputField(ComposableLambdaImpl composableLambdaImpl, ComposableLambdaImpl composableLambdaImpl2, ComposableLambdaImpl composableLambdaImpl3, boolean z, Modifier modifier, String str, Composer composer, int i) {
        int i2;
        ComposableLambdaImpl composableLambdaImpl4;
        ComposableLambdaImpl composableLambdaImpl5;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-430768929);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changedInstance(composableLambdaImpl) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            composableLambdaImpl4 = composableLambdaImpl2;
            i2 |= composerImpl2.changedInstance(composableLambdaImpl4) ? 32 : 16;
        } else {
            composableLambdaImpl4 = composableLambdaImpl2;
        }
        if ((i & 384) == 0) {
            composableLambdaImpl5 = composableLambdaImpl3;
            i2 |= composerImpl2.changedInstance(composableLambdaImpl5) ? 256 : 128;
        } else {
            composableLambdaImpl5 = composableLambdaImpl3;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changed(z) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(str) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.OutlinedInputField (ShortcutCustomizer.kt:502)");
            }
            ComposableLambdaImpl composableLambdaImpl6 = composableLambdaImpl == null ? composableLambdaImpl4 : null;
            composerImpl2.startReplaceGroup(603857253);
            boolean z2 = (458752 & i2) == 131072;
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!z2) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new ShortcutCustomizerKt$$ExternalSyntheticLambda4(str, 1);
                    composerImpl2.updateRememberedValue(objRememberedValue);
                }
                composerImpl2.end(false);
                Modifier modifierSemantics = SemanticsModifierKt.semantics(modifier, true, (Function1) objRememberedValue);
                OutlinedTextFieldDefaults.INSTANCE.getClass();
                TextFieldColors textFieldColorsColors = OutlinedTextFieldDefaults.colors(6, composerImpl2);
                MaterialTheme.INSTANCE.getClass();
                TextFieldColors textFieldColorsM310copyejIjP34 = textFieldColorsColors.m310copyejIjP34(textFieldColorsColors.focusedTextColor, textFieldColorsColors.unfocusedTextColor, textFieldColorsColors.disabledTextColor, textFieldColorsColors.errorTextColor, textFieldColorsColors.focusedContainerColor, textFieldColorsColors.unfocusedContainerColor, textFieldColorsColors.disabledContainerColor, textFieldColorsColors.errorContainerColor, textFieldColorsColors.cursorColor, textFieldColorsColors.errorCursorColor, (KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN & 1024) != 0 ? textFieldColorsColors.textSelectionColors : null, (KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN & 2048) != 0 ? textFieldColorsColors.focusedIndicatorColor : MaterialTheme.getColorScheme(composerImpl2).primary, (KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN & 4096) != 0 ? textFieldColorsColors.unfocusedIndicatorColor : MaterialTheme.getColorScheme(composerImpl2).outline, textFieldColorsColors.disabledIndicatorColor, (KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? textFieldColorsColors.errorIndicatorColor : MaterialTheme.getColorScheme(composerImpl2).error, textFieldColorsColors.focusedLeadingIconColor, textFieldColorsColors.unfocusedLeadingIconColor, textFieldColorsColors.disabledLeadingIconColor, textFieldColorsColors.errorLeadingIconColor, textFieldColorsColors.focusedTrailingIconColor, textFieldColorsColors.unfocusedTrailingIconColor, textFieldColorsColors.disabledTrailingIconColor, textFieldColorsColors.errorTrailingIconColor, textFieldColorsColors.focusedLabelColor, textFieldColorsColors.unfocusedLabelColor, textFieldColorsColors.disabledLabelColor, textFieldColorsColors.errorLabelColor, textFieldColorsColors.focusedPlaceholderColor, textFieldColorsColors.unfocusedPlaceholderColor, textFieldColorsColors.disabledPlaceholderColor, textFieldColorsColors.errorPlaceholderColor, textFieldColorsColors.focusedSupportingTextColor, textFieldColorsColors.unfocusedSupportingTextColor, textFieldColorsColors.disabledSupportingTextColor, textFieldColorsColors.errorSupportingTextColor, textFieldColorsColors.focusedPrefixColor, textFieldColorsColors.unfocusedPrefixColor, textFieldColorsColors.disabledPrefixColor, textFieldColorsColors.errorPrefixColor, textFieldColorsColors.focusedSuffixColor, textFieldColorsColors.unfocusedSuffixColor, textFieldColorsColors.disabledSuffixColor, textFieldColorsColors.errorSuffixColor);
                Dp.Companion companion2 = Dp.Companion;
                RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(50);
                Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, 603850843, companion);
                if (objM == Composer.Companion.Empty) {
                    objM = new ShortcutCustomizerKt$$ExternalSyntheticLambda6(2);
                    composerImpl2.updateRememberedValue(objM);
                }
                composerImpl2.end(false);
                composerImpl = composerImpl2;
                OutlinedTextFieldKt.OutlinedTextField("", (Function1) objM, modifierSemantics, false, false, null, null, composableLambdaImpl6, null, composableLambdaImpl5, composableLambdaImpl, null, null, z, null, null, null, true, 0, 0, null, roundedCornerShapeM187RoundedCornerShape0680j_4, textFieldColorsM310copyejIjP34, composerImpl, ((i2 << 21) & 1879048192) | 54, (i2 & 14) | 12582912 | (i2 & 7168), 0, 1956216);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda11(composableLambdaImpl, composableLambdaImpl2, composableLambdaImpl3, z, modifier, str, i);
        }
    }

    public static final void PlusIconContainer(int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(228065582);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.PlusIconContainer (ShortcutCustomizer.kt:484)");
            }
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl).onSurface;
            Icons.INSTANCE.getClass();
            ImageVector add = AddKt.getAdd();
            String strStringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_content_description_plus_icon, composerImpl);
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(PaddingKt.m127paddingVpY3zN4$default(Modifier.Companion, 0.0f, 12, 1), 24);
            Alignment.Companion.getClass();
            IconKt.m271Iconww6aTOc(add, strStringResource, SizeKt.wrapContentSize$default(modifierM140size3ABfNKs, Alignment.Companion.Center, 2), j, composerImpl, 384, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda20(i, 1);
        }
    }

    public static final void PressKeyPrompt(int i, Composer composer) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-2922020);
        if (i == 0 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.PressKeyPrompt (ShortcutCustomizer.kt:379)");
            }
            String strStringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_add_shortcut_dialog_placeholder, composerImpl2);
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).titleSmall;
            composerImpl = composerImpl2;
            TextKt.m317Text4IGK_g(strStringResource, null, MaterialTheme.getColorScheme(composerImpl2).onSurfaceVariant, TextUnitKt.getSp(16), null, null, null, 0L, null, null, TextUnitKt.getSp(24), 0, false, 0, 0, null, textStyle, composerImpl, 3072, 6, 64498);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda20(i, 0);
        }
    }

    public static final void PressedKeysTextContainer(List list, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-210540932);
        if ((((composerImpl.changedInstance(list) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.PressedKeysTextContainer (ShortcutCustomizer.kt:335)");
            }
            Modifier.Companion companion = Modifier.Companion;
            composerImpl.startReplaceGroup(742661728);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new ShortcutCustomizerKt$$ExternalSyntheticLambda6(3);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            composerImpl.end(false);
            Modifier modifierSemantics = SemanticsModifierKt.semantics(companion, false, (Function1) objRememberedValue);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Arrangement.INSTANCE.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierSemantics);
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
            composerImpl.startReplaceGroup(-1072631783);
            int i2 = 0;
            for (Object obj : list) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                ShortcutKey shortcutKey = (ShortcutKey) obj;
                composerImpl.startReplaceGroup(-1072630548);
                if (i2 > 0) {
                    ShortcutKeySeparator(0, composerImpl);
                }
                composerImpl.end(false);
                if (shortcutKey instanceof ShortcutKey.Text) {
                    composerImpl.startReplaceGroup(1502929867);
                    ShortcutTextKey((ShortcutKey.Text) shortcutKey, composerImpl, 0);
                    composerImpl.end(false);
                } else if (shortcutKey instanceof ShortcutKey.Icon) {
                    composerImpl.startReplaceGroup(1503016171);
                    ShortcutIconKey((ShortcutKey.Icon) shortcutKey, composerImpl, 0);
                    composerImpl.end(false);
                } else {
                    composerImpl.startReplaceGroup(1503066143);
                    composerImpl.end(false);
                }
                i2 = i3;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda5(list, i, 1);
        }
    }

    public static final void PromptShortcutModifier(final Modifier modifier, final ShortcutKey.Icon.ResIdIcon resIdIcon, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1394548862);
        int i2 = (composerImpl.changed(resIdIcon) ? 32 : 16) | i;
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.PromptShortcutModifier (ShortcutCustomizer.kt:433)");
            }
            Arrangement arrangement = Arrangement.INSTANCE;
            Dp.Companion companion = Dp.Companion;
            arrangement.getClass();
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(2);
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_4, Alignment.Companion.CenterVertically, composerImpl, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            ActionKeyContainer(resIdIcon, composerImpl, (i2 >> 3) & 14);
            PlusIconContainer(0, composerImpl);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(resIdIcon, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCustomizerKt$$ExternalSyntheticLambda12
                public final /* synthetic */ ShortcutKey.Icon.ResIdIcon f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(7);
                    ShortcutCustomizerKt.PromptShortcutModifier(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ResetShortcutDialog(Modifier modifier, Function0 function0, Function0 function02, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1746108786);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ResetShortcutDialog (ShortcutCustomizer.kt:168)");
            }
            ConfirmationDialog(modifier, StringResources_androidKt.stringResource(R.string.shortcut_customize_mode_reset_shortcut_dialog_title, composerImpl), StringResources_androidKt.stringResource(R.string.shortcut_customize_mode_reset_shortcut_description, composerImpl), StringResources_androidKt.stringResource(R.string.shortcut_helper_customize_dialog_reset_button_label, composerImpl), function02, function0, composerImpl, (i2 & 14) | (57344 & (i2 << 6)) | ((i2 << 12) & 458752));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda1(modifier, function0, function02, i, 1);
        }
    }

    public static final void SelectedKeyCombinationContainer(final boolean z, final Function1 function1, final List list, String str, final Function0 function0, final Function0 function02, Composer composer, int i) {
        int i2;
        String str2;
        boolean z2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1913513578);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(list) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            str2 = str;
            i2 |= composerImpl.changed(str2) ? 2048 : 1024;
        } else {
            str2 = str;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.SelectedKeyCombinationContainer (ShortcutCustomizer.kt:274)");
            }
            composerImpl.startReplaceGroup(1515869036);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl);
            }
            FocusRequester focusRequester = (FocusRequester) objRememberedValue;
            composerImpl.end(false);
            final FocusManager focusManager = (FocusManager) composerImpl.consume(CompositionLocalsKt.LocalFocusManager);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(1515872368);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new ShortcutCustomizerKt$SelectedKeyCombinationContainer$1$1(focusRequester, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue2);
            Dp.Companion companion = Dp.Companion;
            Modifier modifierFocusRequester = FocusRequesterModifierKt.focusRequester(SizeKt.m143sizeInqDBjuR0$default(PaddingKt.m125padding3ABfNKs(Modifier.Companion, 16), CustomDeviceManager.DESTINATION_ADDRESS, 56, 0.0f, 0.0f, 12), focusRequester);
            composerImpl.startReplaceGroup(1515880770);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (objRememberedValue3 == composer$Companion$Empty$1) {
                z2 = true;
                objRememberedValue3 = new ShortcutCustomizerKt$$ExternalSyntheticLambda6(1);
                composerImpl.updateRememberedValue(objRememberedValue3);
            } else {
                z2 = true;
            }
            composerImpl.end(false);
            Modifier modifierFocusProperties = FocusPropertiesKt.focusProperties(modifierFocusRequester, (Function1) objRememberedValue3);
            composerImpl.startReplaceGroup(1515883669);
            boolean zChangedInstance = ((i2 & 112) == 32 ? z2 : false) | ((57344 & i2) == 16384) | ((i2 & 458752) == 131072) | composerImpl.changedInstance(focusManager);
            Object objRememberedValue4 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue4 == composer$Companion$Empty$1) {
                objRememberedValue4 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCustomizerKt$SelectedKeyCombinationContainer$3$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        boolean z3;
                        KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj).nativeKeyEvent;
                        if (((Boolean) function1.mo781invoke(androidx.compose.ui.input.key.KeyEvent.m579boximpl(keyEvent))).booleanValue()) {
                            z3 = true;
                        } else {
                            int iM581getTypeZmokQxo = KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent);
                            KeyEventType.Companion.getClass();
                            if (iM581getTypeZmokQxo == KeyEventType.KeyUp) {
                                long jKey = Key_androidKt.Key(keyEvent.getKeyCode());
                                Key.Companion.getClass();
                                if (Key.m578equalsimpl0(jKey, Key.Enter)) {
                                    function0.invoke();
                                    return Boolean.TRUE;
                                }
                                if (Key.m578equalsimpl0(jKey, Key.Backspace)) {
                                    function02.invoke();
                                    return Boolean.TRUE;
                                }
                                if (!Key.m578equalsimpl0(jKey, Key.DirectionDown)) {
                                    return Boolean.FALSE;
                                }
                                FocusDirection.Companion.getClass();
                                ((FocusOwnerImpl) focusManager).m375moveFocus3ESFkO8(FocusDirection.Down);
                                return Boolean.TRUE;
                            }
                            z3 = false;
                        }
                        return Boolean.valueOf(z3);
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue4);
            }
            composerImpl.end(false);
            Modifier modifierOnPreviewKeyEvent = KeyInputModifierKt.onPreviewKeyEvent(modifierFocusProperties, (Function1) objRememberedValue4);
            composerImpl.startReplaceGroup(1515925024);
            ComposableLambdaImpl composableLambdaImplRememberComposableLambda = !list.isEmpty() ? ComposableLambdaKt.rememberComposableLambda(338194307, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCustomizerKt.SelectedKeyCombinationContainer.4
                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
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
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.SelectedKeyCombinationContainer.<anonymous> (ShortcutCustomizer.kt:314)");
                            }
                            ShortcutCustomizerKt.PressedKeysTextContainer(list, composer2, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl) : null;
            composerImpl.end(false);
            ComposableSingletons$ShortcutCustomizerKt.INSTANCE.getClass();
            OutlinedInputField(composableLambdaImplRememberComposableLambda, ComposableSingletons$ShortcutCustomizerKt.f43lambda1, ComposableLambdaKt.rememberComposableLambda(1258478485, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCustomizerKt.SelectedKeyCombinationContainer.5
                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
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
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.SelectedKeyCombinationContainer.<anonymous> (ShortcutCustomizer.kt:309)");
                            }
                            ShortcutCustomizerKt.ErrorIcon(z, composer2, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), z, modifierOnPreviewKeyEvent, str2, composerImpl, ((i2 << 6) & 458752) | ((i2 << 9) & 7168) | 432);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda11(z, function1, list, str, function0, function02, i);
        }
    }

    public static final void ShortcutCustomizationDialog(final ShortcutCustomizationUiState shortcutCustomizationUiState, final Modifier modifier, final Function1 function1, final Function0 function0, final Function0 function02, final Function0 function03, final Function0 function04, final Function0 function05, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-396663173);
        int i2 = i | (composerImpl.changed(shortcutCustomizationUiState) ? 4 : 2) | (composerImpl.changedInstance(function1) ? 256 : 128) | (composerImpl.changedInstance(function0) ? 2048 : 1024) | (composerImpl.changedInstance(function02) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl.changedInstance(function03) ? 131072 : 65536) | (composerImpl.changedInstance(function04) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) | (composerImpl.changedInstance(function05) ? 8388608 : 4194304);
        if ((4793491 & i2) == 4793490 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCustomizationDialog (ShortcutCustomizer.kt:84)");
            }
            if (shortcutCustomizationUiState instanceof ShortcutCustomizationUiState.AddShortcutDialog) {
                composerImpl.startReplaceGroup(-596518040);
                AddShortcutDialog(modifier, (ShortcutCustomizationUiState.AddShortcutDialog) shortcutCustomizationUiState, function1, function0, function02, function05, composerImpl, (i2 & 896) | 6 | (i2 & 7168) | (57344 & i2) | ((i2 >> 6) & 458752));
                composerImpl.end(false);
            } else if (shortcutCustomizationUiState instanceof ShortcutCustomizationUiState.DeleteShortcutDialog) {
                composerImpl.startReplaceGroup(-596193253);
                DeleteShortcutDialog(modifier, function0, function03, composerImpl, ((i2 >> 9) & 896) | 6 | ((i2 >> 6) & 112));
                composerImpl.end(false);
            } else if (shortcutCustomizationUiState instanceof ShortcutCustomizationUiState.ResetShortcutDialog) {
                composerImpl.startReplaceGroup(-596041539);
                ResetShortcutDialog(modifier, function0, function04, composerImpl, ((i2 >> 12) & 896) | 6 | ((i2 >> 6) & 112));
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-595939983);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(modifier, function1, function0, function02, function03, function04, function05, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCustomizerKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Modifier f$1;
                public final /* synthetic */ Function1 f$2;
                public final /* synthetic */ Function0 f$3;
                public final /* synthetic */ Function0 f$4;
                public final /* synthetic */ Function0 f$5;
                public final /* synthetic */ Function0 f$6;
                public final /* synthetic */ Function0 f$7;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                    Function0 function06 = this.f$6;
                    Function0 function07 = this.f$7;
                    ShortcutCustomizerKt.ShortcutCustomizationDialog(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, function06, function07, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShortcutIconKey(ShortcutKey.Icon icon, Composer composer, int i) {
        Painter painterRememberDrawablePainter;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(593238823);
        if ((((composerImpl.changed(icon) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutIconKey (ShortcutCustomizer.kt:365)");
            }
            if (icon instanceof ShortcutKey.Icon.ResIdIcon) {
                composerImpl.startReplaceGroup(-1074827973);
                painterRememberDrawablePainter = PainterResources_androidKt.painterResource(((ShortcutKey.Icon.ResIdIcon) icon).drawableResId, composerImpl, 0);
                composerImpl.end(false);
            } else {
                if (!(icon instanceof ShortcutKey.Icon.DrawableIcon)) {
                    composerImpl.startReplaceGroup(-1074829780);
                    composerImpl.end(false);
                    throw new NoWhenBranchMatchedException();
                }
                composerImpl.startReplaceGroup(-1074825175);
                painterRememberDrawablePainter = DrawablePainterKt.rememberDrawablePainter(((ShortcutKey.Icon.DrawableIcon) icon).drawable, composerImpl);
                composerImpl.end(false);
            }
            Painter painter = painterRememberDrawablePainter;
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(Modifier.Companion, 24);
            MaterialTheme.INSTANCE.getClass();
            IconKt.m270Iconww6aTOc(painter, (String) null, modifierM131height3ABfNKs, MaterialTheme.getColorScheme(composerImpl).onSurfaceVariant, composerImpl, 432, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda5(icon, i, 3);
        }
    }

    public static final void ShortcutKeySeparator(int i, Composer composer) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(878409576);
        if (i == 0 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutKeySeparator (ShortcutCustomizer.kt:354)");
            }
            String strStringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_plus_symbol, composerImpl2);
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).titleSmall;
            composerImpl = composerImpl2;
            TextKt.m317Text4IGK_g(strStringResource, null, MaterialTheme.getColorScheme(composerImpl2).onSurfaceVariant, TextUnitKt.getSp(16), null, null, null, 0L, null, null, TextUnitKt.getSp(24), 0, false, 0, 0, null, textStyle, composerImpl, 3072, 6, 64498);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda20(i, 2);
        }
    }

    public static final void ShortcutTextKey(ShortcutKey.Text text, Composer composer, int i) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1265083071);
        if ((((composerImpl2.changed(text) ? 4 : 2) | i) & 3) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutTextKey (ShortcutCustomizer.kt:390)");
            }
            composerImpl = composerImpl2;
            String str = text.value;
            MaterialTheme.INSTANCE.getClass();
            TextKt.m317Text4IGK_g(str, null, MaterialTheme.getColorScheme(composerImpl).onSurfaceVariant, TextUnitKt.getSp(16), null, null, null, 0L, null, null, TextUnitKt.getSp(24), 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).titleSmall, composerImpl, 3072, 6, 64498);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda5(text, i, 2);
        }
    }

    public static final void Title(String str, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1510501607);
        if ((i & 6) == 0) {
            i2 = i | (composerImpl2.changed(str) ? 4 : 2);
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.Title (ShortcutCustomizer.kt:401)");
            }
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).headlineSmall;
            long sp = TextUnitKt.getSp(24);
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM144width3ABfNKs = SizeKt.m144width3ABfNKs(PaddingKt.m127paddingVpY3zN4$default(Modifier.Companion, 24, 0.0f, 2), 316);
            Alignment.Companion.getClass();
            Modifier modifierWrapContentSize$default = SizeKt.wrapContentSize$default(modifierM144width3ABfNKs, Alignment.Companion.Center, 2);
            long j = MaterialTheme.getColorScheme(composerImpl2).onSurface;
            long sp2 = TextUnitKt.getSp(32);
            FontWeight.Companion.getClass();
            FontWeight fontWeight = FontWeight.W400;
            TextAlign.Companion.getClass();
            composerImpl = composerImpl2;
            TextKt.m317Text4IGK_g(str, modifierWrapContentSize$default, j, sp, null, fontWeight, null, 0L, null, TextAlign.m807boximpl(TextAlign.Center), sp2, 0, false, 0, 0, null, textStyle, composerImpl, (i2 & 14) | 199728, 6, 63952);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ShortcutCustomizerKt$$ExternalSyntheticLambda8(str, i, 1);
        }
    }
}
