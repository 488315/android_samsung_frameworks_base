package com.android.systemui.communal.ui.compose;

import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$EnableWidgetDialogKt {
    public static final ComposableSingletons$EnableWidgetDialogKt INSTANCE = new ComposableSingletons$EnableWidgetDialogKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f37lambda1 = new ComposableLambdaImpl(-929515193, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$EnableWidgetDialogKt$lambda-1$1
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
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ComposableSingletons$EnableWidgetDialogKt.lambda-1.<anonymous> (EnableWidgetDialog.kt:125)");
            }
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.cancel, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
