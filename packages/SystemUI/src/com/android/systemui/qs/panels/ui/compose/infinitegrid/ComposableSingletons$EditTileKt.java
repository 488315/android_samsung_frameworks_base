package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.automirrored.filled.ArrowBackKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$EditTileKt {
    public static final ComposableSingletons$EditTileKt INSTANCE = new ComposableSingletons$EditTileKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f96lambda1 = new ComposableLambdaImpl(32821507, false, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.ComposableSingletons$EditTileKt$lambda-1$1
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
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.ComposableSingletons$EditTileKt.lambda-1.<anonymous> (EditTile.kt:188)");
            }
            String stringResource = StringResources_androidKt.stringResource(R.string.qs_edit_tiles, composer);
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composer).titleLargeEmphasized;
            Dp.Companion companion = Dp.Companion;
            TextKt.m316Text4IGK_g(stringResource, PaddingKt.m128paddingqDBjuR0$default(Modifier.Companion, 24, 0.0f, 0.0f, 0.0f, 14), 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyle, composer, 48, 0, 65532);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f97lambda2 = new ComposableLambdaImpl(589286499, false, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.ComposableSingletons$EditTileKt$lambda-2$1
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
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.ComposableSingletons$EditTileKt.lambda-2.<anonymous> (EditTile.kt:199)");
            }
            Icons.AutoMirrored.Filled filled = Icons.AutoMirrored.Filled.INSTANCE;
            ImageVector imageVector = ArrowBackKt._arrowBack;
            if (imageVector == null) {
                Dp.Companion companion = Dp.Companion;
                ImageVector.Builder builder = new ImageVector.Builder("AutoMirrored.Filled.ArrowBack", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, true, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i = StrokeJoin.Bevel;
                PathBuilder pathBuilder = new PathBuilder();
                pathBuilder.moveTo(20.0f, 11.0f);
                pathBuilder.horizontalLineTo(7.83f);
                pathBuilder.lineToRelative(5.59f, -5.59f);
                pathBuilder.lineTo(12.0f, 4.0f);
                pathBuilder.lineToRelative(-8.0f, 8.0f);
                pathBuilder.lineToRelative(8.0f, 8.0f);
                pathBuilder.lineToRelative(1.41f, -1.41f);
                pathBuilder.lineTo(7.83f, 13.0f);
                pathBuilder.horizontalLineTo(20.0f);
                pathBuilder.verticalLineToRelative(-2.0f);
                pathBuilder.close();
                builder.m565addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVector = builder.build();
                ArrowBackKt._arrowBack = imageVector;
            }
            MaterialTheme.INSTANCE.getClass();
            IconKt.m270Iconww6aTOc(imageVector, StringResources_androidKt.stringResource(android.R.string.bluetooth_a2dp_audio_route_id, composer), (Modifier) null, MaterialTheme.getColorScheme(composer).onSurface, composer, 0, 4);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f98lambda3 = new ComposableLambdaImpl(923467344, false, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.ComposableSingletons$EditTileKt$lambda-3$1
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
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.ComposableSingletons$EditTileKt.lambda-3.<anonymous> (EditTile.kt:217)");
            }
            String stringResource = StringResources_androidKt.stringResource(17042685, composer);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m316Text4IGK_g(stringResource, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer).labelLarge, composer, 0, 0, 65534);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
