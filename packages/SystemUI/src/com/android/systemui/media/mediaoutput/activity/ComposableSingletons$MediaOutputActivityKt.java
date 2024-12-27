package com.android.systemui.media.mediaoutput.activity;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import com.android.systemui.media.mediaoutput.compose.PhoneScreenKt;
import com.samsung.sesl.compose.foundation.theme.ThemeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public final class ComposableSingletons$MediaOutputActivityKt {
    public static final ComposableSingletons$MediaOutputActivityKt INSTANCE = new ComposableSingletons$MediaOutputActivityKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f46lambda1 = new ComposableLambdaImpl(-969342623, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.activity.ComposableSingletons$MediaOutputActivityKt$lambda-1$1
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
            PhoneScreenKt.PhoneScreen(new Function1() { // from class: com.android.systemui.media.mediaoutput.activity.ComposableSingletons$MediaOutputActivityKt$lambda-1$1.1
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj3) {
                    return Unit.INSTANCE;
                }
            }, null, null, composer, 6, 6);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f47lambda2 = new ComposableLambdaImpl(-186312570, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.activity.ComposableSingletons$MediaOutputActivityKt$lambda-2$1
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
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            Color.Companion.getClass();
            long j = Color.Transparent;
            MaterialTheme.INSTANCE.getClass();
            long j2 = MaterialTheme.getColorScheme(composer).onSurfaceVariant;
            ComposableSingletons$MediaOutputActivityKt.INSTANCE.getClass();
            SurfaceKt.m248SurfaceT9BRK9s(fillMaxWidth, null, j, j2, 0.0f, 0.0f, null, ComposableSingletons$MediaOutputActivityKt.f46lambda1, composer, 12583302, 114);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f48lambda3 = new ComposableLambdaImpl(695626711, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.activity.ComposableSingletons$MediaOutputActivityKt$lambda-3$1
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
            ComposableSingletons$MediaOutputActivityKt.INSTANCE.getClass();
            ThemeKt.SeslTheme(false, null, ComposableSingletons$MediaOutputActivityKt.f47lambda2, composer, 384, 3);
            return Unit.INSTANCE;
        }
    });
}
