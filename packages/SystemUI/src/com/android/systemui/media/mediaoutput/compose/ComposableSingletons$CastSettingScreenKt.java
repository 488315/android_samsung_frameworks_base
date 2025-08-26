package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.widget.ListsKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final class ComposableSingletons$CastSettingScreenKt {
    public static final ComposableSingletons$CastSettingScreenKt INSTANCE = new ComposableSingletons$CastSettingScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f51lambda1 = new ComposableLambdaImpl(-912141838, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CastSettingScreenKt$lambda-1$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CastSettingScreenKt.lambda-1.<anonymous> (CastSettingScreen.kt:107)");
                    }
                    ListsKt.SecSubHeader(StringResources_androidKt.stringResource(R.string.cast_setting_apps_header, composer), composer, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f52lambda2 = new ComposableLambdaImpl(1380713716, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CastSettingScreenKt$lambda-2$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CastSettingScreenKt.lambda-2.<anonymous> (CastSettingScreen.kt:130)");
                    }
                    Dp.Companion companion = Dp.Companion;
                    SpacerKt.Spacer(composer, SizeKt.m131height3ABfNKs(Modifier.Companion, 10));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });
}
