package com.android.systemui.volume.panel.component.bottombar.ui;

import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
public final class ComposableSingletons$BottomBarComponentKt {
    public static final ComposableSingletons$BottomBarComponentKt INSTANCE = new ComposableSingletons$BottomBarComponentKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f113lambda1 = new ComposableLambdaImpl(714543523, false, new Function3() { // from class: com.android.systemui.volume.panel.component.bottombar.ui.ComposableSingletons$BottomBarComponentKt$lambda-1$1
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
                        ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.bottombar.ui.ComposableSingletons$BottomBarComponentKt.lambda-1.<anonymous> (BottomBarComponent.kt:52)");
                    }
                    TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.volume_panel_dialog_settings_button, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f114lambda2 = new ComposableLambdaImpl(3420420, false, new Function3() { // from class: com.android.systemui.volume.panel.component.bottombar.ui.ComposableSingletons$BottomBarComponentKt$lambda-2$1
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
                        ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.bottombar.ui.ComposableSingletons$BottomBarComponentKt.lambda-2.<anonymous> (BottomBarComponent.kt:55)");
                    }
                    TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.inline_done_button, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });
}
