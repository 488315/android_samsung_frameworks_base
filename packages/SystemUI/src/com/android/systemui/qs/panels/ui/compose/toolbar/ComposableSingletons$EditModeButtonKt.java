package com.android.systemui.qs.panels.ui.compose.toolbar;

import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.EditKt;
import androidx.compose.material3.IconKt;
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
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$EditModeButtonKt {
    public static final ComposableSingletons$EditModeButtonKt INSTANCE = new ComposableSingletons$EditModeButtonKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f99lambda1 = new ComposableLambdaImpl(-1255721607, false, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.toolbar.ComposableSingletons$EditModeButtonKt$lambda-1$1
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
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.toolbar.ComposableSingletons$EditModeButtonKt.lambda-1.<anonymous> (EditModeButton.kt:49)");
            }
            Icons.INSTANCE.getClass();
            ImageVector imageVector = EditKt._edit;
            if (imageVector == null) {
                Dp.Companion companion = Dp.Companion;
                ImageVector.Builder builder = new ImageVector.Builder("Filled.Edit", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i = StrokeJoin.Bevel;
                PathBuilder pathBuilder = new PathBuilder();
                pathBuilder.moveTo(3.0f, 17.25f);
                pathBuilder.verticalLineTo(21.0f);
                pathBuilder.horizontalLineToRelative(3.75f);
                pathBuilder.lineTo(17.81f, 9.94f);
                pathBuilder.lineToRelative(-3.75f, -3.75f);
                pathBuilder.lineTo(3.0f, 17.25f);
                pathBuilder.close();
                pathBuilder.moveTo(20.71f, 7.04f);
                pathBuilder.curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f);
                pathBuilder.lineToRelative(-2.34f, -2.34f);
                pathBuilder.curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.41f, 0.0f);
                pathBuilder.lineToRelative(-1.83f, 1.83f);
                pathBuilder.lineToRelative(3.75f, 3.75f);
                pathBuilder.lineToRelative(1.83f, -1.83f);
                pathBuilder.close();
                builder.m565addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVector = builder.build();
                EditKt._edit = imageVector;
            }
            IconKt.m270Iconww6aTOc(imageVector, StringResources_androidKt.stringResource(R.string.accessibility_quick_settings_edit, composer), (Modifier) null, 0L, composer, 0, 12);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
