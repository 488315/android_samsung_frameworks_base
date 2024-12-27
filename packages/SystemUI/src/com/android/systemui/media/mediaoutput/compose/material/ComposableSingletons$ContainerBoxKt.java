package com.android.systemui.media.mediaoutput.compose.material;

import androidx.compose.material3.IconKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.icons.Icons$Action;
import com.android.systemui.media.mediaoutput.icons.action.SettingsKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ComposableSingletons$ContainerBoxKt {
    public static final ComposableSingletons$ContainerBoxKt INSTANCE = new ComposableSingletons$ContainerBoxKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f69lambda1 = new ComposableLambdaImpl(-637601038, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.material.ComposableSingletons$ContainerBoxKt$lambda-1$1
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
            Icons$Action icons$Action = Icons$Action.INSTANCE;
            ImageVector imageVector = (ImageVector) SettingsKt.Settings$delegate.getValue();
            String stringResource = StringResources_androidKt.stringResource(R.string.sec_qs_media_player_settings, composer);
            Color.Companion.getClass();
            IconKt.m226Iconww6aTOc(imageVector, stringResource, (Modifier) null, Color.White, composer, 3072, 4);
            return Unit.INSTANCE;
        }
    });
}
