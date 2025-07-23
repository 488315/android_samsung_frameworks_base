package com.android.systemui.communal.ui.compose.section;

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
import androidx.compose.material.icons.outlined.TouchAppKt;
import androidx.compose.material3.ColorScheme;
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
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$CommunalPopupSectionKt {
    public static final ComposableSingletons$CommunalPopupSectionKt INSTANCE = new ComposableSingletons$CommunalPopupSectionKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f38lambda1 = new ComposableLambdaImpl(-900706228, false, new Function2() { // from class: com.android.systemui.communal.ui.compose.section.ComposableSingletons$CommunalPopupSectionKt$lambda-1$1
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
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.ComposableSingletons$CommunalPopupSectionKt.lambda-1.<anonymous> (CommunalPopupSection.kt:183)");
            }
            MaterialTheme.INSTANCE.getClass();
            ColorScheme colorScheme = MaterialTheme.getColorScheme(composer);
            Modifier.Companion companion = Modifier.Companion;
            Dp.Companion companion2 = Dp.Companion;
            Modifier m124padding3ABfNKs = PaddingKt.m124padding3ABfNKs(BackgroundKt.m26backgroundbw27NRU(SizeKt.m130height3ABfNKs(companion, 56), colorScheme.secondary, RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(50)), 16);
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterVertically, composer, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer);
            ComposerImpl composerImpl2 = (ComposerImpl) composer;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer, m124padding3ABfNKs);
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
            Icons.Outlined outlined = Icons.Outlined.INSTANCE;
            ImageVector imageVector = TouchAppKt._touchApp;
            if (imageVector == null) {
                ImageVector.Builder builder = new ImageVector.Builder("Outlined.TouchApp", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i = StrokeJoin.Bevel;
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
                builder.m565addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVector = builder.build();
                TouchAppKt._touchApp = imageVector;
            }
            IconKt.m270Iconww6aTOc(imageVector, StringResources_androidKt.stringResource(R.string.popup_on_dismiss_cta_tile_text, composer), SizeKt.m139size3ABfNKs(companion, 20), colorScheme.onSecondary, composer, 384, 0);
            SpacerKt.Spacer(composer, SizeKt.m139size3ABfNKs(companion, 8));
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.popup_on_dismiss_cta_tile_text, composer), null, colorScheme.onSecondary, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer).titleSmall, composer, 0, 0, 65530);
            composerImpl2.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
