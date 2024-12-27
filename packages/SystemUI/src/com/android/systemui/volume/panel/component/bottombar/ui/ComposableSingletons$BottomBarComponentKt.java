package com.android.systemui.volume.panel.component.bottombar.ui;

import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

public final class ComposableSingletons$BottomBarComponentKt {
    public static final ComposableSingletons$BottomBarComponentKt INSTANCE = new ComposableSingletons$BottomBarComponentKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f80lambda1 = new ComposableLambdaImpl(1336189458, false, new Function3() { // from class: com.android.systemui.volume.panel.component.bottombar.ui.ComposableSingletons$BottomBarComponentKt$lambda-1$1
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
            TextKt.m257Text4IGK_g(StringResources_androidKt.stringResource(R.string.volume_panel_dialog_settings_button, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f81lambda2 = new ComposableLambdaImpl(968846292, false, new Function3() { // from class: com.android.systemui.volume.panel.component.bottombar.ui.ComposableSingletons$BottomBarComponentKt$lambda-2$1
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
            TextKt.m257Text4IGK_g(StringResources_androidKt.stringResource(R.string.inline_done_button, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            return Unit.INSTANCE;
        }
    });
}
