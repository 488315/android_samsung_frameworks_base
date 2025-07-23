package com.android.systemui.qs.panels.ui.dialog;

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
public final class ComposableSingletons$QSResetDialogDelegateKt {
    public static final ComposableSingletons$QSResetDialogDelegateKt INSTANCE = new ComposableSingletons$QSResetDialogDelegateKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f100lambda1 = new ComposableLambdaImpl(2017574447, false, new Function2() { // from class: com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt$lambda-1$1
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
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt.lambda-1.<anonymous> (QSResetDialogDelegate.kt:76)");
            }
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.qs_edit_mode_reset_dialog_title, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f101lambda2 = new ComposableLambdaImpl(-1974505458, false, new Function2() { // from class: com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt$lambda-2$1
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
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt.lambda-2.<anonymous> (QSResetDialogDelegate.kt:78)");
            }
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.qs_edit_mode_reset_dialog_content, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f102lambda3 = new ComposableLambdaImpl(-2137619221, false, new Function3() { // from class: com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt$lambda-3$1
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
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt.lambda-3.<anonymous> (QSResetDialogDelegate.kt:87)");
            }
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(android.R.string.ok, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-4, reason: not valid java name */
    public static final ComposableLambdaImpl f103lambda4 = new ComposableLambdaImpl(-1910124833, false, new Function3() { // from class: com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt$lambda-4$1
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
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.dialog.ComposableSingletons$QSResetDialogDelegateKt.lambda-4.<anonymous> (QSResetDialogDelegate.kt:92)");
            }
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(android.R.string.cancel, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
