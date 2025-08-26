package com.android.systemui.communal.ui.compose.section;

import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final class ComposableSingletons$HubOnboardingSectionKt {
    public static final ComposableSingletons$HubOnboardingSectionKt INSTANCE = new ComposableSingletons$HubOnboardingSectionKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f39lambda1 = new ComposableLambdaImpl(-450580366, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.section.ComposableSingletons$HubOnboardingSectionKt$lambda-1$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
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
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.ComposableSingletons$HubOnboardingSectionKt.lambda-1.<anonymous> (HubOnboardingSection.kt:173)");
                    }
                    String strStringResource = StringResources_androidKt.stringResource(R.string.hub_onboarding_bottom_sheet_action_button, composer);
                    MaterialTheme.INSTANCE.getClass();
                    TextKt.m317Text4IGK_g(strStringResource, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer).labelLarge, composer, 0, 0, 65534);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });
}
