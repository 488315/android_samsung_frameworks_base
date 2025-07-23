package androidx.activity.compose;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.ViewTreeOnBackPressedDispatcherOwner;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LocalOnBackPressedDispatcherOwner {
    public static final LocalOnBackPressedDispatcherOwner INSTANCE = new LocalOnBackPressedDispatcherOwner();
    public static final DynamicProvidableCompositionLocal LocalOnBackPressedDispatcherOwner = CompositionLocalKt.compositionLocalOf$default(new Function0() { // from class: androidx.activity.compose.LocalOnBackPressedDispatcherOwner$LocalOnBackPressedDispatcherOwner$1
        @Override // kotlin.jvm.functions.Function0
        public final /* bridge */ /* synthetic */ Object invoke() {
            return null;
        }
    });

    private LocalOnBackPressedDispatcherOwner() {
    }

    public static OnBackPressedDispatcherOwner getCurrent(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.activity.compose.LocalOnBackPressedDispatcherOwner.<get-current> (BackHandler.kt:50)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        OnBackPressedDispatcherOwner onBackPressedDispatcherOwner = (OnBackPressedDispatcherOwner) composerImpl.consume(LocalOnBackPressedDispatcherOwner);
        if (onBackPressedDispatcherOwner == null) {
            composerImpl.startReplaceGroup(544166745);
            onBackPressedDispatcherOwner = ViewTreeOnBackPressedDispatcherOwner.get((View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView));
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(544164296);
            composerImpl.end(false);
        }
        if (onBackPressedDispatcherOwner == null) {
            composerImpl.startReplaceGroup(544168748);
            Object obj = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            while (true) {
                if (!(obj instanceof ContextWrapper)) {
                    obj = null;
                    break;
                }
                if (obj instanceof OnBackPressedDispatcherOwner) {
                    break;
                }
                obj = ((ContextWrapper) obj).getBaseContext();
            }
            onBackPressedDispatcherOwner = (OnBackPressedDispatcherOwner) obj;
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(544164377);
            composerImpl.end(false);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return onBackPressedDispatcherOwner;
    }
}
