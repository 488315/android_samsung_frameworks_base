package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.SnackbarData;
import androidx.compose.material3.SnackbarKt;
import androidx.compose.material3.SnackbarVisuals;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.unit.Dp;
import com.samsung.sesl.compose.template.SeslScaffoldTemplate$BackgroundScope;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;

/* loaded from: classes2.dex */
public final class ComposableSingletons$SnackbarScaffoldKt {
    public static final ComposableSingletons$SnackbarScaffoldKt INSTANCE = new ComposableSingletons$SnackbarScaffoldKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f87lambda1 = new ComposableLambdaImpl(-339608691, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$SnackbarScaffoldKt$lambda-1$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001a  */
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
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$SnackbarScaffoldKt.lambda-1.<anonymous> (SnackbarScaffold.kt:27)");
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
    public static final ComposableLambdaImpl f88lambda2 = new ComposableLambdaImpl(442903327, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$SnackbarScaffoldKt$lambda-2$1
        /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            SnackbarData snackbarData = (SnackbarData) obj;
            Composer composer = (Composer) obj2;
            int iIntValue = ((Number) obj3).intValue();
            if ((iIntValue & 6) == 0) {
                iIntValue |= ((ComposerImpl) composer).changed(snackbarData) ? 4 : 2;
            }
            if ((iIntValue & 19) == 18) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$SnackbarScaffoldKt.lambda-2.<anonymous> (SnackbarScaffold.kt:39)");
                    }
                    SnackbarVisuals visuals = snackbarData.getVisuals();
                    if (visuals instanceof SnackbarDialogImpl) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer;
                        composerImpl2.startReplaceGroup(-2127408514);
                        SnackbarDialogKt.SnackbarDialog((SnackbarDialogImpl) visuals, snackbarData, composerImpl2, (iIntValue << 3) & 112);
                        composerImpl2.end(false);
                    } else {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer;
                        composerImpl3.startReplaceGroup(-1525051820);
                        Modifier.Companion companion = Modifier.Companion;
                        float f = VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE;
                        Dp.Companion companion2 = Dp.Companion;
                        SnackbarKt.m301SnackbarsDKtq54(snackbarData, SizeKt.m146widthInVpY3zN4$default(companion, 0.0f, f, 1), true, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(22), ColorKt.Color(4282861386L), ColorKt.Color(4294638335L), ColorKt.Color(4283928575L), 0L, 0L, composerImpl3, (iIntValue & 14) | 1794480, 384);
                        composerImpl3.end(false);
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f89lambda3 = new ComposableLambdaImpl(-1707356841, false, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$SnackbarScaffoldKt$lambda-3$1
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
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ComposableSingletons$SnackbarScaffoldKt.lambda-3.<anonymous> (SnackbarScaffold.kt:55)");
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
