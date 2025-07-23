package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* renamed from: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$LabsHomeKt$lambda-9$1, reason: invalid class name */
/* loaded from: classes2.dex */
public final class ComposableSingletons$LabsHomeKt$lambda9$1 implements Function3 {
    public static final ComposableSingletons$LabsHomeKt$lambda9$1 INSTANCE = new ComposableSingletons$LabsHomeKt$lambda9$1();

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PaddingValues paddingValues = (PaddingValues) obj;
        Composer composer = (Composer) obj2;
        int intValue = ((Number) obj3).intValue();
        if ((intValue & 6) == 0) {
            intValue |= ((ComposerImpl) composer).changed(paddingValues) ? 4 : 2;
        }
        if ((intValue & 19) == 18) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            if (composerImpl.getSkipping()) {
                composerImpl.skipToGroupEnd();
                return Unit.INSTANCE;
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$LabsHomeKt.lambda-9.<anonymous> (LabsHome.kt:52)");
        }
        LayoutDirection layoutDirection = LayoutDirection.Ltr;
        PaddingValuesImpl m123PaddingValuesa9UjIt4$default = PaddingKt.m123PaddingValuesa9UjIt4$default(PaddingKt.calculateStartPadding(paddingValues, layoutDirection), 0.0f, PaddingKt.calculateEndPadding(paddingValues, layoutDirection), paddingValues.mo109calculateBottomPaddingD9Ej5fM(), 2);
        Modifier m128paddingqDBjuR0$default = PaddingKt.m128paddingqDBjuR0$default(SizeKt.fillMaxSize(Modifier.Companion, 1.0f), 0.0f, paddingValues.mo112calculateTopPaddingD9Ej5fM(), 0.0f, 0.0f, 13);
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startReplaceGroup(2079298121);
        Object rememberedValue = composerImpl2.rememberedValue();
        Composer.Companion.getClass();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue = new ComposableSingletons$LabsHomeKt$lambda9$1$$ExternalSyntheticLambda0();
            composerImpl2.updateRememberedValue(rememberedValue);
        }
        composerImpl2.end(false);
        LazyDslKt.LazyColumn(m128paddingqDBjuR0$default, null, m123PaddingValuesa9UjIt4$default, false, null, null, null, false, null, (Function1) rememberedValue, composerImpl2, 805306368, 506);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return Unit.INSTANCE;
    }
}
