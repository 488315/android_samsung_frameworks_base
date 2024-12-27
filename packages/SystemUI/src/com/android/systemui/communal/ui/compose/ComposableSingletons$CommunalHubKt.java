package com.android.systemui.communal.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.AddKt;
import androidx.compose.material.icons.filled.CheckKt;
import androidx.compose.material.icons.filled.CloseKt;
import androidx.compose.material.icons.filled.CloseKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.outlined.EditKt;
import androidx.compose.material.icons.outlined.TouchAppKt;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
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
import com.android.compose.theme.AndroidColorScheme;
import com.android.compose.theme.AndroidColorSchemeKt;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ComposableSingletons$CommunalHubKt {
    public static final ComposableSingletons$CommunalHubKt INSTANCE = new ComposableSingletons$CommunalHubKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f25lambda1 = new ComposableLambdaImpl(1931407786, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Icons.INSTANCE.getClass();
            ImageVector add = AddKt.getAdd();
            String stringResource = StringResources_androidKt.stringResource(R.string.label_for_button_in_empty_state_cta, composer);
            Modifier.Companion companion = Modifier.Companion;
            Dp.Companion companion2 = Dp.Companion;
            IconKt.m226Iconww6aTOc(add, stringResource, SizeKt.m115size3ABfNKs(companion, 24), 0L, composer, 384, 8);
            ButtonDefaults.INSTANCE.getClass();
            SpacerKt.Spacer(composer, SizeKt.m119width3ABfNKs(companion, ButtonDefaults.IconSpacing));
            String stringResource2 = StringResources_androidKt.stringResource(R.string.label_for_button_in_empty_state_cta, composer);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m257Text4IGK_g(stringResource2, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer).titleSmall, composer, 0, 0, 65534);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f27lambda2 = new ComposableLambdaImpl(-763302154, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Icons.INSTANCE.getClass();
            IconKt.m226Iconww6aTOc(AddKt.getAdd(), StringResources_androidKt.stringResource(R.string.hub_mode_add_widget_button_text, composer), (Modifier) null, 0L, composer, 0, 12);
            TextKt.m257Text4IGK_g(StringResources_androidKt.stringResource(R.string.hub_mode_add_widget_button_text, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f28lambda3 = new ComposableLambdaImpl(1260877735, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Arrangement arrangement = Arrangement.INSTANCE;
            ButtonDefaults.INSTANCE.getClass();
            float f = ButtonDefaults.IconSpacing;
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
            arrangement.getClass();
            Arrangement.SpacedAligned m76spacedByD5KLDUw = Arrangement.m76spacedByD5KLDUw(f, horizontal);
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Modifier.Companion companion = Modifier.Companion;
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(m76spacedByD5KLDUw, vertical, composer, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer);
            ComposerImpl composerImpl2 = (ComposerImpl) composer;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer, companion);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (!(composerImpl2.applier instanceof Applier)) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function0);
            } else {
                composerImpl2.useNode();
            }
            Updater.m276setimpl(composer, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m276setimpl(composer, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
            }
            Updater.m276setimpl(composer, materializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            Icons.INSTANCE.getClass();
            IconKt.m226Iconww6aTOc(CloseKt.getClose(), StringResources_androidKt.stringResource(R.string.button_to_remove_widget, composer), (Modifier) null, 0L, composer, 0, 12);
            TextKt.m257Text4IGK_g(StringResources_androidKt.stringResource(R.string.button_to_remove_widget, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            composerImpl2.end(true);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-4, reason: not valid java name */
    public static final ComposableLambdaImpl f29lambda4 = new ComposableLambdaImpl(707199469, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-4$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Icons.INSTANCE.getClass();
            ImageVector imageVector = CheckKt._check;
            if (imageVector == null) {
                Dp.Companion companion = Dp.Companion;
                ImageVector.Builder builder = new ImageVector.Builder("Filled.Check", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i = StrokeJoin.Bevel;
                PathBuilder m = CloseKt$$ExternalSyntheticOutline0.m(9.0f, 16.17f, 4.83f, 12.0f);
                m.lineToRelative(-1.42f, 1.41f);
                m.lineTo(9.0f, 19.0f);
                m.lineTo(21.0f, 7.0f);
                m.lineToRelative(-1.41f, -1.41f);
                m.close();
                ImageVector.Builder.m491addPathoIyEayM$default(builder, m._nodes, 0, solidColor, null, 1.0f, 0, i, 1.0f);
                imageVector = builder.build();
                CheckKt._check = imageVector;
            }
            IconKt.m226Iconww6aTOc(imageVector, StringResources_androidKt.stringResource(R.string.hub_mode_editing_exit_button_text, composer), (Modifier) null, 0L, composer, 0, 12);
            TextKt.m257Text4IGK_g(StringResources_androidKt.stringResource(R.string.hub_mode_editing_exit_button_text, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-5, reason: not valid java name */
    public static final ComposableLambdaImpl f30lambda5 = new ComposableLambdaImpl(-4450612, false, new Function2() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-5$1
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
            ComposerImpl composerImpl2 = (ComposerImpl) composer;
            AndroidColorScheme androidColorScheme = (AndroidColorScheme) composerImpl2.consume(AndroidColorSchemeKt.LocalAndroidColorScheme);
            Modifier.Companion companion = Modifier.Companion;
            Dp.Companion companion2 = Dp.Companion;
            Modifier m102padding3ABfNKs = PaddingKt.m102padding3ABfNKs(BackgroundKt.m24backgroundbw27NRU(SizeKt.m108height3ABfNKs(companion, 56), androidColorScheme.secondary, RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(50)), 16);
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterVertically, composerImpl2, 54);
            int i = composerImpl2.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, m102padding3ABfNKs);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (!(composerImpl2.applier instanceof Applier)) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function0);
            } else {
                composerImpl2.useNode();
            }
            Updater.m276setimpl(composerImpl2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m276setimpl(composerImpl2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(i))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i, composerImpl2, i, function2);
            }
            Updater.m276setimpl(composerImpl2, materializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            Icons.Outlined outlined = Icons.Outlined.INSTANCE;
            ImageVector imageVector = TouchAppKt._touchApp;
            if (imageVector == null) {
                ImageVector.Builder builder = new ImageVector.Builder("Outlined.TouchApp", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i2 = StrokeJoin.Bevel;
                PathBuilder pathBuilder = new PathBuilder();
                pathBuilder.moveTo(18.19f, 12.44f);
                pathBuilder.lineToRelative(-3.24f, -1.62f);
                pathBuilder.curveToRelative(1.29f, -1.0f, 2.12f, -2.56f, 2.12f, -4.32f);
                pathBuilder.curveToRelative(0.0f, -3.03f, -2.47f, -5.5f, -5.5f, -5.5f);
                pathBuilder.reflectiveCurveToRelative(-5.5f, 2.47f, -5.5f, 5.5f);
                pathBuilder.curveToRelative(0.0f, 2.13f, 1.22f, 3.98f, 3.0f, 4.89f);
                pathBuilder.verticalLineToRelative(3.26f);
                pathBuilder.curveToRelative(-2.15f, -0.46f, -2.02f, -0.44f, -2.26f, -0.44f);
                pathBuilder.curveToRelative(-0.53f, 0.0f, -1.03f, 0.21f, -1.41f, 0.59f);
                pathBuilder.lineTo(4.0f, 16.22f);
                pathBuilder.lineToRelative(5.09f, 5.09f);
                pathBuilder.curveTo(9.52f, 21.75f, 10.12f, 22.0f, 10.74f, 22.0f);
                pathBuilder.horizontalLineToRelative(6.3f);
                pathBuilder.curveToRelative(0.98f, 0.0f, 1.81f, -0.7f, 1.97f, -1.67f);
                pathBuilder.lineToRelative(0.8f, -4.71f);
                pathBuilder.curveTo(20.03f, 14.32f, 19.38f, 13.04f, 18.19f, 12.44f);
                pathBuilder.close();
                pathBuilder.moveTo(17.84f, 15.29f);
                pathBuilder.lineTo(17.04f, 20.0f);
                pathBuilder.horizontalLineToRelative(-6.3f);
                pathBuilder.curveToRelative(-0.09f, 0.0f, -0.17f, -0.04f, -0.24f, -0.1f);
                pathBuilder.lineToRelative(-3.68f, -3.68f);
                pathBuilder.lineToRelative(4.25f, 0.89f);
                pathBuilder.verticalLineTo(6.5f);
                pathBuilder.curveToRelative(0.0f, -0.28f, 0.22f, -0.5f, 0.5f, -0.5f);
                pathBuilder.curveToRelative(0.28f, 0.0f, 0.5f, 0.22f, 0.5f, 0.5f);
                pathBuilder.verticalLineToRelative(6.0f);
                pathBuilder.horizontalLineToRelative(1.76f);
                pathBuilder.lineToRelative(3.46f, 1.73f);
                pathBuilder.curveTo(17.69f, 14.43f, 17.91f, 14.86f, 17.84f, 15.29f);
                pathBuilder.close();
                pathBuilder.moveTo(8.07f, 6.5f);
                pathBuilder.curveToRelative(0.0f, -1.93f, 1.57f, -3.5f, 3.5f, -3.5f);
                pathBuilder.reflectiveCurveToRelative(3.5f, 1.57f, 3.5f, 3.5f);
                pathBuilder.curveToRelative(0.0f, 0.95f, -0.38f, 1.81f, -1.0f, 2.44f);
                pathBuilder.verticalLineTo(6.5f);
                pathBuilder.curveToRelative(0.0f, -1.38f, -1.12f, -2.5f, -2.5f, -2.5f);
                pathBuilder.curveToRelative(-1.38f, 0.0f, -2.5f, 1.12f, -2.5f, 2.5f);
                pathBuilder.verticalLineToRelative(2.44f);
                pathBuilder.curveTo(8.45f, 8.31f, 8.07f, 7.45f, 8.07f, 6.5f);
                pathBuilder.close();
                ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, null, 1.0f, 0, i2, 1.0f);
                ImageVector build = builder.build();
                TouchAppKt._touchApp = build;
                imageVector = build;
            }
            IconKt.m226Iconww6aTOc(imageVector, StringResources_androidKt.stringResource(R.string.popup_on_dismiss_cta_tile_text, composerImpl2), SizeKt.m115size3ABfNKs(companion, 20), androidColorScheme.onSecondary, composerImpl2, 384, 0);
            SpacerKt.Spacer(composerImpl2, SizeKt.m115size3ABfNKs(companion, 8));
            String stringResource = StringResources_androidKt.stringResource(R.string.popup_on_dismiss_cta_tile_text, composerImpl2);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m257Text4IGK_g(stringResource, null, androidColorScheme.onSecondary, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl2).titleSmall, composerImpl2, 0, 0, 65530);
            composerImpl2.end(true);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-6, reason: not valid java name */
    public static final ComposableLambdaImpl f31lambda6 = new ComposableLambdaImpl(-217317501, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-6$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-7, reason: not valid java name */
    public static final ComposableLambdaImpl f32lambda7 = new ComposableLambdaImpl(487610104, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-7$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            TextKt.m257Text4IGK_g(StringResources_androidKt.stringResource(R.string.cta_tile_button_to_dismiss, composer), null, 0L, TextUnitKt.getSp(12), null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 3072, 0, 131062);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-8, reason: not valid java name */
    public static final ComposableLambdaImpl f33lambda8 = new ComposableLambdaImpl(-1994627466, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-8$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            TextKt.m257Text4IGK_g(StringResources_androidKt.stringResource(R.string.cta_tile_button_to_open_widget_editor, composer), null, 0L, TextUnitKt.getSp(12), null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 3072, 0, 131062);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-9, reason: not valid java name */
    public static final ComposableLambdaImpl f34lambda9 = new ComposableLambdaImpl(-1962818462, false, new Function2() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-9$1
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
            Icons.Outlined outlined = Icons.Outlined.INSTANCE;
            ImageVector imageVector = EditKt._edit;
            if (imageVector == null) {
                Dp.Companion companion = Dp.Companion;
                ImageVector.Builder builder = new ImageVector.Builder("Outlined.Edit", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i = StrokeJoin.Bevel;
                PathBuilder pathBuilder = new PathBuilder();
                pathBuilder.moveTo(14.06f, 9.02f);
                pathBuilder.lineToRelative(0.92f, 0.92f);
                pathBuilder.lineTo(5.92f, 19.0f);
                pathBuilder.lineTo(5.0f, 19.0f);
                pathBuilder.verticalLineToRelative(-0.92f);
                pathBuilder.lineToRelative(9.06f, -9.06f);
                pathBuilder.moveTo(17.66f, 3.0f);
                pathBuilder.curveToRelative(-0.25f, 0.0f, -0.51f, 0.1f, -0.7f, 0.29f);
                pathBuilder.lineToRelative(-1.83f, 1.83f);
                pathBuilder.lineToRelative(3.75f, 3.75f);
                pathBuilder.lineToRelative(1.83f, -1.83f);
                pathBuilder.curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f);
                pathBuilder.lineToRelative(-2.34f, -2.34f);
                pathBuilder.curveToRelative(-0.2f, -0.2f, -0.45f, -0.29f, -0.71f, -0.29f);
                pathBuilder.close();
                pathBuilder.moveTo(14.06f, 6.19f);
                pathBuilder.lineTo(3.0f, 17.25f);
                pathBuilder.lineTo(3.0f, 21.0f);
                pathBuilder.horizontalLineToRelative(3.75f);
                pathBuilder.lineTo(17.81f, 9.94f);
                pathBuilder.lineToRelative(-3.75f, -3.75f);
                pathBuilder.close();
                ImageVector.Builder.m491addPathoIyEayM$default(builder, pathBuilder._nodes, 0, solidColor, null, 1.0f, 0, i, 1.0f);
                imageVector = builder.build();
                EditKt._edit = imageVector;
            }
            String stringResource = StringResources_androidKt.stringResource(R.string.edit_widget, composer);
            Dp.Companion companion2 = Dp.Companion;
            IconKt.m226Iconww6aTOc(imageVector, stringResource, PaddingKt.m102padding3ABfNKs(Modifier.Companion, 12), 0L, composer, 384, 8);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-10, reason: not valid java name */
    public static final ComposableLambdaImpl f26lambda10 = new ComposableLambdaImpl(-1769929298, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-10$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            return Unit.INSTANCE;
        }
    });
}
