package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.widget.ListsKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ComposableSingletons$CastSettingScreenKt {
    public static final ComposableSingletons$CastSettingScreenKt INSTANCE = new ComposableSingletons$CastSettingScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f49lambda1 = new ComposableLambdaImpl(1281500196, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CastSettingScreenKt$lambda-1$1
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
            ListsKt.SecSubHeader(StringResources_androidKt.stringResource(R.string.cast_setting_apps_header, composer), composer, 0);
            return Unit.INSTANCE;
        }
    });
}
