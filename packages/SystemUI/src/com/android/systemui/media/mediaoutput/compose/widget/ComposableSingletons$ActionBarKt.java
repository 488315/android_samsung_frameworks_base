package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.material3.IconKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.PainterResources_androidKt;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public final class ComposableSingletons$ActionBarKt {
    public static final ComposableSingletons$ActionBarKt INSTANCE = new ComposableSingletons$ActionBarKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f70lambda1 = new ComposableLambdaImpl(1099773802, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$ActionBarKt$lambda-1$1
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
            IconKt.m225Iconww6aTOc(PainterResources_androidKt.painterResource(R.drawable.ic_samsung_sysbar_back, composer, 0), "", (Modifier) null, ColorKt.mediaPrimaryColor(composer), composer, 56, 4);
            return Unit.INSTANCE;
        }
    });
}
