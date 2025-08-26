package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.style.TextOverflow;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$TitleScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final class ComposableSingletons$CoverScreenKt {
    public static final ComposableSingletons$CoverScreenKt INSTANCE = new ComposableSingletons$CoverScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f53lambda1 = new ComposableLambdaImpl(-1695326700, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CoverScreenKt$lambda-1$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
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
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CoverScreenKt.lambda-1.<anonymous> (CoverScreen.kt:88)");
                    }
                    String strStringResource = StringResources_androidKt.stringResource(R.string.media_output, composer);
                    TextOverflow.Companion.getClass();
                    TextKt.m317Text4IGK_g(strStringResource, null, 0L, 0L, null, null, null, 0L, null, null, 0L, TextOverflow.Ellipsis, false, 1, 0, null, TypeKt.TitleTextStyle(composer), composer, 0, 3120, 55294);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f54lambda2 = new ComposableLambdaImpl(1269423975, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CoverScreenKt$lambda-2$1
        /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            SeslTopAppBarTemplate$TitleScope seslTopAppBarTemplate$TitleScope = (SeslTopAppBarTemplate$TitleScope) obj;
            Composer composer = (Composer) obj2;
            int iIntValue = ((Number) obj3).intValue();
            if ((iIntValue & 6) == 0) {
                iIntValue |= (iIntValue & 8) == 0 ? ((ComposerImpl) composer).changed(seslTopAppBarTemplate$TitleScope) : ((ComposerImpl) composer).changedInstance(seslTopAppBarTemplate$TitleScope) ? 4 : 2;
            }
            if ((iIntValue & 19) == 18) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CoverScreenKt.lambda-2.<anonymous> (CoverScreen.kt:86)");
                    }
                    ComposableSingletons$CoverScreenKt.INSTANCE.getClass();
                    ComposableLambdaImpl composableLambdaImpl = ComposableSingletons$CoverScreenKt.f53lambda1;
                    SeslTopAppBarTemplate$TitleScope.Companion companion = SeslTopAppBarTemplate$TitleScope.Companion;
                    seslTopAppBarTemplate$TitleScope.Title(6 | ((iIntValue << 9) & 7168), composer, composableLambdaImpl, null);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f55lambda3 = new ComposableLambdaImpl(-1136574786, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CoverScreenKt$lambda-3$1
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
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CoverScreenKt.lambda-3.<anonymous> (CoverScreen.kt:101)");
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

    /* renamed from: lambda-4, reason: not valid java name */
    public static final ComposableLambdaImpl f56lambda4 = new ComposableLambdaImpl(634256645, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CoverScreenKt$lambda-4$1
        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            PaddingValues paddingValues = (PaddingValues) obj;
            Composer composer = (Composer) obj2;
            int iIntValue = ((Number) obj3).intValue();
            if ((iIntValue & 6) == 0) {
                iIntValue |= ((ComposerImpl) composer).changed(paddingValues) ? 4 : 2;
            }
            if ((iIntValue & 19) == 18) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$CoverScreenKt.lambda-4.<anonymous> (CoverScreen.kt:127)");
                    }
                    MediaCardKt.MediaCard(PaddingKt.m129paddingqDBjuR0$default(Modifier.Companion, 0.0f, paddingValues.mo113calculateTopPaddingD9Ej5fM(), 0.0f, 0.0f, 13), PaddingKt.m124PaddingValuesa9UjIt4$default(0.0f, 0.0f, 0.0f, paddingValues.mo110calculateBottomPaddingD9Ej5fM(), 7), composer, 0, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });
}
