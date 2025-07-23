package androidx.lifecycle.viewmodel.compose;

import android.view.View;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LocalViewModelStoreOwner {
    public static final LocalViewModelStoreOwner INSTANCE = new LocalViewModelStoreOwner();
    public static final DynamicProvidableCompositionLocal LocalViewModelStoreOwner = CompositionLocalKt.compositionLocalOf$default(new Function0() { // from class: androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner$LocalViewModelStoreOwner$1
        @Override // kotlin.jvm.functions.Function0
        public final /* bridge */ /* synthetic */ Object invoke() {
            return null;
        }
    });

    private LocalViewModelStoreOwner() {
    }

    public static ViewModelStoreOwner getCurrent(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceableGroup(-584162872);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner.<get-current> (LocalViewModelStoreOwner.kt:34)");
        }
        ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) composerImpl.consume(LocalViewModelStoreOwner);
        if (viewModelStoreOwner == null) {
            composerImpl.startReplaceableGroup(-163523515);
            composerImpl.startReplaceableGroup(1382572291);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.lifecycle.viewmodel.compose.findViewTreeViewModelStoreOwner (LocalViewModelStoreOwner.android.kt:25)");
            }
            viewModelStoreOwner = ViewTreeViewModelStoreOwner.get((View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceableGroup(-163524631);
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return viewModelStoreOwner;
    }
}
