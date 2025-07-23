package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.automirrored.filled.OpenInNewKt;
import androidx.compose.material.icons.filled.SearchKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$ShortcutHelperKt {
    public static final ComposableSingletons$ShortcutHelperKt INSTANCE = new ComposableSingletons$ShortcutHelperKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f44lambda1 = new ComposableLambdaImpl(229348019, false, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ComposableSingletons$ShortcutHelperKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ComposableSingletons$ShortcutHelperKt.lambda-1.<anonymous> (ShortcutHelper.kt:1054)");
            }
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.shortcut_helper_search_placeholder, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f45lambda2 = new ComposableLambdaImpl(1568111442, false, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ComposableSingletons$ShortcutHelperKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ComposableSingletons$ShortcutHelperKt.lambda-2.<anonymous> (ShortcutHelper.kt:1053)");
            }
            Icons.INSTANCE.getClass();
            ImageVector imageVector = SearchKt._search;
            if (imageVector == null) {
                Dp.Companion companion = Dp.Companion;
                ImageVector.Builder builder = new ImageVector.Builder("Filled.Search", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i = StrokeJoin.Bevel;
                PathBuilder pathBuilder = new PathBuilder();
                pathBuilder.moveTo(15.5f, 14.0f);
                pathBuilder.horizontalLineToRelative(-0.79f);
                pathBuilder.lineToRelative(-0.28f, -0.27f);
                pathBuilder.curveTo(15.41f, 12.59f, 16.0f, 11.11f, 16.0f, 9.5f);
                pathBuilder.curveTo(16.0f, 5.91f, 13.09f, 3.0f, 9.5f, 3.0f);
                pathBuilder.reflectiveCurveTo(3.0f, 5.91f, 3.0f, 9.5f);
                pathBuilder.reflectiveCurveTo(5.91f, 16.0f, 9.5f, 16.0f);
                pathBuilder.curveToRelative(1.61f, 0.0f, 3.09f, -0.59f, 4.23f, -1.57f);
                pathBuilder.lineToRelative(0.27f, 0.28f);
                pathBuilder.verticalLineToRelative(0.79f);
                pathBuilder.lineToRelative(5.0f, 4.99f);
                pathBuilder.lineTo(20.49f, 19.0f);
                pathBuilder.lineToRelative(-4.99f, -5.0f);
                pathBuilder.close();
                pathBuilder.moveTo(9.5f, 14.0f);
                pathBuilder.curveTo(7.01f, 14.0f, 5.0f, 11.99f, 5.0f, 9.5f);
                pathBuilder.reflectiveCurveTo(7.01f, 5.0f, 9.5f, 5.0f);
                pathBuilder.reflectiveCurveTo(14.0f, 7.01f, 14.0f, 9.5f);
                pathBuilder.reflectiveCurveTo(11.99f, 14.0f, 9.5f, 14.0f);
                pathBuilder.close();
                builder.m565addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVector = builder.build();
                SearchKt._search = imageVector;
            }
            IconKt.m270Iconww6aTOc(imageVector, (String) null, (Modifier) null, 0L, composer, 48, 12);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f46lambda3 = new ComposableLambdaImpl(1446783917, false, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ComposableSingletons$ShortcutHelperKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ComposableSingletons$ShortcutHelperKt.lambda-3.<anonymous> (ShortcutHelper.kt:1056)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-4, reason: not valid java name */
    public static final ComposableLambdaImpl f47lambda4 = new ComposableLambdaImpl(-1050218759, false, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ComposableSingletons$ShortcutHelperKt$lambda-4$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ComposableSingletons$ShortcutHelperKt.lambda-4.<anonymous> (ShortcutHelper.kt:1082)");
            }
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Modifier.Companion companion = Modifier.Companion;
            Arrangement.INSTANCE.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composer, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer);
            ComposerImpl composerImpl2 = (ComposerImpl) composer;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer, companion);
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
            Updater.m336setimpl(composer, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composer, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composer, materializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            String stringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_keyboard_settings_buttons_label, composer);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m316Text4IGK_g(stringResource, rowScopeInstance.weight(companion, 1.0f, true), MaterialTheme.getColorScheme(composer).onSurfaceVariant, TextUnitKt.getSp(16), null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer).titleSmall, composer, 3072, 0, 65520);
            Dp.Companion companion2 = Dp.Companion;
            SpacerKt.Spacer(composer, SizeKt.m143width3ABfNKs(companion, 8));
            Icons.AutoMirrored.INSTANCE.getClass();
            ImageVector imageVector = OpenInNewKt._openInNew;
            if (imageVector == null) {
                ImageVector.Builder builder = new ImageVector.Builder("AutoMirrored.Filled.OpenInNew", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, true, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i = StrokeJoin.Bevel;
                PathBuilder pathBuilder = new PathBuilder();
                pathBuilder.moveTo(19.0f, 19.0f);
                pathBuilder.horizontalLineTo(5.0f);
                pathBuilder.verticalLineTo(5.0f);
                pathBuilder.horizontalLineToRelative(7.0f);
                pathBuilder.verticalLineTo(3.0f);
                pathBuilder.horizontalLineTo(5.0f);
                pathBuilder.curveToRelative(-1.11f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f);
                pathBuilder.verticalLineToRelative(14.0f);
                pathBuilder.curveToRelative(0.0f, 1.1f, 0.89f, 2.0f, 2.0f, 2.0f);
                pathBuilder.horizontalLineToRelative(14.0f);
                pathBuilder.curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f);
                pathBuilder.verticalLineToRelative(-7.0f);
                pathBuilder.horizontalLineToRelative(-2.0f);
                pathBuilder.verticalLineToRelative(7.0f);
                pathBuilder.close();
                pathBuilder.moveTo(14.0f, 3.0f);
                pathBuilder.verticalLineToRelative(2.0f);
                pathBuilder.horizontalLineToRelative(3.59f);
                pathBuilder.lineToRelative(-9.83f, 9.83f);
                pathBuilder.lineToRelative(1.41f, 1.41f);
                pathBuilder.lineTo(19.0f, 6.41f);
                pathBuilder.verticalLineTo(10.0f);
                pathBuilder.horizontalLineToRelative(2.0f);
                pathBuilder.verticalLineTo(3.0f);
                pathBuilder.horizontalLineToRelative(-7.0f);
                pathBuilder.close();
                builder.m565addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVector = builder.build();
                OpenInNewKt._openInNew = imageVector;
            }
            IconKt.m270Iconww6aTOc(imageVector, (String) null, SizeKt.m139size3ABfNKs(companion, 24), MaterialTheme.getColorScheme(composer).onSurfaceVariant, composer, 432, 0);
            composerImpl2.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
