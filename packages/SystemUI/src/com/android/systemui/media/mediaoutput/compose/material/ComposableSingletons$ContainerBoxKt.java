package com.android.systemui.media.mediaoutput.compose.material;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.IconKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.action.OutputSwitcherKt;
import com.android.systemui.media.mediaoutput.icons.action.SettingsKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$ContainerBoxKt {
    public static final ComposableSingletons$ContainerBoxKt INSTANCE = new ComposableSingletons$ContainerBoxKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f82lambda1 = new ComposableLambdaImpl(1710062333, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.material.ComposableSingletons$ContainerBoxKt$lambda-1$1
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
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.ComposableSingletons$ContainerBoxKt.lambda-1.<anonymous> (ContainerBox.kt:217)");
            }
            Icons.Action action = Icons.Action.INSTANCE;
            ImageVector imageVector = (ImageVector) OutputSwitcherKt.OutputSwitcher$delegate.getValue();
            Dp.Companion companion = Dp.Companion;
            Modifier m139size3ABfNKs = SizeKt.m139size3ABfNKs(Modifier.Companion, 24);
            Color.Companion.getClass();
            IconKt.m270Iconww6aTOc(imageVector, "", m139size3ABfNKs, Color.White, composer, 3504, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f83lambda2 = new ComposableLambdaImpl(-1737623006, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.material.ComposableSingletons$ContainerBoxKt$lambda-2$1
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
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.ComposableSingletons$ContainerBoxKt.lambda-2.<anonymous> (ContainerBox.kt:233)");
            }
            Icons.Action action = Icons.Action.INSTANCE;
            ImageVector imageVector = (ImageVector) SettingsKt.Settings$delegate.getValue();
            String stringResource = StringResources_androidKt.stringResource(R.string.sec_qs_media_player_settings, composer);
            Color.Companion.getClass();
            IconKt.m270Iconww6aTOc(imageVector, stringResource, (Modifier) null, Color.White, composer, 3072, 4);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
