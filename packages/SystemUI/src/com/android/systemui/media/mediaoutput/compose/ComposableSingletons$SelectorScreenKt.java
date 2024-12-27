package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.android.systemui.util.DeviceType;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

public final class ComposableSingletons$SelectorScreenKt {
    public static final ComposableSingletons$SelectorScreenKt INSTANCE = new ComposableSingletons$SelectorScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f65lambda1 = new ComposableLambdaImpl(379551541, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SelectorScreenKt$lambda-1$1
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
            SelectorScreenKt.access$SelectorHeader(StringResources_androidKt.stringResource(DeviceType.isTablet() ? R.string.tablet_speaker : R.string.phone_speaker, composer), composer, 0);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f66lambda2 = new ComposableLambdaImpl(526461754, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SelectorScreenKt$lambda-2$1
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
            Dp.Companion companion = Dp.Companion;
            SpacerKt.Spacer(composer, SizeKt.m108height3ABfNKs(Modifier.Companion, 20));
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f67lambda3 = new ComposableLambdaImpl(364790899, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SelectorScreenKt$lambda-3$1
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
            SelectorScreenKt.access$SelectorHeader(StringResources_androidKt.stringResource(R.string.choose_a_device, composer), composer, 0);
            return Unit.INSTANCE;
        }
    });
}
