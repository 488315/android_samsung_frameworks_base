package com.android.systemui.statusbar.policy.ui.dialog;

import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.TestTagKt;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
public final class ComposableSingletons$ModesDialogDelegateKt {
    public static final ComposableSingletons$ModesDialogDelegateKt INSTANCE = new ComposableSingletons$ModesDialogDelegateKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f109lambda1 = new ComposableLambdaImpl(1888397592, false, new Function2() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ComposableSingletons$ModesDialogDelegateKt$lambda-1$1
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
                        ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.ComposableSingletons$ModesDialogDelegateKt.lambda-1.<anonymous> (ModesDialogDelegate.kt:130)");
                    }
                    TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.zen_modes_dialog_title, composer), TestTagKt.testTag(Modifier.Companion, "modes_title"), 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 48, 0, 131068);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f110lambda2 = new ComposableLambdaImpl(-1945132068, false, new Function3() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ComposableSingletons$ModesDialogDelegateKt$lambda-2$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
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
                        ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.ComposableSingletons$ModesDialogDelegateKt.lambda-2.<anonymous> (ModesDialogDelegate.kt:143)");
                    }
                    TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.zen_modes_dialog_done, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f111lambda3 = new ComposableLambdaImpl(-737282209, false, new Function3() { // from class: com.android.systemui.statusbar.policy.ui.dialog.ComposableSingletons$ModesDialogDelegateKt$lambda-3$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
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
                        ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.ComposableSingletons$ModesDialogDelegateKt.lambda-3.<anonymous> (ModesDialogDelegate.kt:138)");
                    }
                    TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.zen_modes_dialog_settings, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });
}
