package com.android.systemui.volume.panel.component.popup.ui.composable;

import androidx.compose.material3.IconKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ComposableSingletons$VolumePanelPopupKt {
    public static final ComposableSingletons$VolumePanelPopupKt INSTANCE = new ComposableSingletons$VolumePanelPopupKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f84lambda1 = new ComposableLambdaImpl(-507644778, false, new Function2() { // from class: com.android.systemui.volume.panel.component.popup.ui.composable.ComposableSingletons$VolumePanelPopupKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 11) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            IconKt.m225Iconww6aTOc(PainterResources_androidKt.painterResource(R.drawable.ic_close, composer, 0), StringResources_androidKt.stringResource(R.string.accessibility_desc_close, composer), (Modifier) null, 0L, composer, 8, 12);
            return Unit.INSTANCE;
        }
    });
}
