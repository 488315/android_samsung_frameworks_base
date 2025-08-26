package com.android.systemui.volume.panel.component.popup.ui.composable;

import androidx.compose.material3.IconKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final class ComposableSingletons$VolumePanelPopupKt {
    public static final ComposableSingletons$VolumePanelPopupKt INSTANCE = new ComposableSingletons$VolumePanelPopupKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f117lambda1 = new ComposableLambdaImpl(-198461839, false, new Function2() { // from class: com.android.systemui.volume.panel.component.popup.ui.composable.ComposableSingletons$VolumePanelPopupKt$lambda-1$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
        @Override // kotlin.jvm.functions.Function2
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.popup.ui.composable.ComposableSingletons$VolumePanelPopupKt.lambda-1.<anonymous> (VolumePanelPopup.kt:121)");
                    }
                    IconKt.m270Iconww6aTOc(PainterResources_androidKt.painterResource(R.drawable.ic_close, composer, 0), StringResources_androidKt.stringResource(R.string.accessibility_desc_close, composer), (Modifier) null, 0L, composer, 0, 12);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });
}
