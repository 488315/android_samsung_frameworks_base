package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.material3.IconKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.res.PainterResources_androidKt;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.samsung.sesl.compose.template.SeslScaffoldTemplate$BackgroundScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;

/* loaded from: classes2.dex */
public final class ComposableSingletons$ActionBarKt {
    public static final ComposableSingletons$ActionBarKt INSTANCE = new ComposableSingletons$ActionBarKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f84lambda1 = new ComposableLambdaImpl(-1903735310, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$ActionBarKt$lambda-1$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
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
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$ActionBarKt.lambda-1.<anonymous> (ActionBar.kt:40)");
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f85lambda2 = new ComposableLambdaImpl(676554923, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$ActionBarKt$lambda-2$1
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
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$ActionBarKt.lambda-2.<anonymous> (ActionBar.kt:65)");
                    }
                    IconKt.m270Iconww6aTOc(PainterResources_androidKt.painterResource(R.drawable.ic_samsung_sysbar_back, composer, 0), "", (Modifier) null, ColorKt.mediaPrimaryColor(composer), composer, 48, 4);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f86lambda3 = new ComposableLambdaImpl(1684706399, false, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$ActionBarKt$lambda-3$1
        /* JADX WARN: Removed duplicated region for block: B:26:0x0055  */
        @Override // kotlin.jvm.functions.Function4
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            int i;
            SeslScaffoldTemplate$BackgroundScope seslScaffoldTemplate$BackgroundScope = (SeslScaffoldTemplate$BackgroundScope) obj;
            PaddingValues paddingValues = (PaddingValues) obj2;
            Composer composer = (Composer) obj3;
            int iIntValue = ((Number) obj4).intValue();
            if ((iIntValue & 6) == 0) {
                i = ((iIntValue & 8) == 0 ? ((ComposerImpl) composer).changed(seslScaffoldTemplate$BackgroundScope) : ((ComposerImpl) composer).changedInstance(seslScaffoldTemplate$BackgroundScope) ? 4 : 2) | iIntValue;
            } else {
                i = iIntValue;
            }
            if ((iIntValue & 48) == 0) {
                i |= ((ComposerImpl) composer).changed(paddingValues) ? 32 : 16;
            }
            if ((i & 147) == 146) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$ActionBarKt.lambda-3.<anonymous> (ActionBar.kt:79)");
                    }
                    Color.Companion.getClass();
                    long j = Color.Transparent;
                    SeslScaffoldTemplate$BackgroundScope.Companion companion = SeslScaffoldTemplate$BackgroundScope.Companion;
                    seslScaffoldTemplate$BackgroundScope.m3356BackgroundFNF3uiM(paddingValues, null, j, composer, ((i >> 3) & 14) | 384 | ((i << 9) & 7168), 2);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });
}
