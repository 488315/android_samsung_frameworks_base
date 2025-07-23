package com.android.systemui.qs.external.ui.dialog;

import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$TileRequestDialogComposeDelegateKt {
    public static final ComposableSingletons$TileRequestDialogComposeDelegateKt INSTANCE = new ComposableSingletons$TileRequestDialogComposeDelegateKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f93lambda1 = new ComposableLambdaImpl(1309477677, false, new Function2() { // from class: com.android.systemui.qs.external.ui.dialog.ComposableSingletons$TileRequestDialogComposeDelegateKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.external.ui.dialog.ComposableSingletons$TileRequestDialogComposeDelegateKt.lambda-1.<anonymous> (TileRequestDialogComposeDelegate.kt:65)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f94lambda2 = new ComposableLambdaImpl(-2083949079, false, new Function3() { // from class: com.android.systemui.qs.external.ui.dialog.ComposableSingletons$TileRequestDialogComposeDelegateKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.external.ui.dialog.ComposableSingletons$TileRequestDialogComposeDelegateKt.lambda-2.<anonymous> (TileRequestDialogComposeDelegate.kt:104)");
            }
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.qs_tile_request_dialog_add, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f95lambda3 = new ComposableLambdaImpl(-1035788889, false, new Function3() { // from class: com.android.systemui.qs.external.ui.dialog.ComposableSingletons$TileRequestDialogComposeDelegateKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.external.ui.dialog.ComposableSingletons$TileRequestDialogComposeDelegateKt.lambda-3.<anonymous> (TileRequestDialogComposeDelegate.kt:114)");
            }
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.qs_tile_request_dialog_not_add, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
